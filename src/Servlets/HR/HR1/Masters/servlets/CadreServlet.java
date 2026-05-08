package Servlets.HR.HR1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class CadreServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    private ResultSet results2 = null;
    private PreparedStatement ps = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try {
            ResourceBundle rs =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs.getString("Config.DSN");
            String strhostname = rs.getString("Config.HOST_NAME");
            String strportno = rs.getString("Config.PORT_NUMBER");
            String strsid = rs.getString("Config.SID");
            String strdbusername = rs.getString("Config.USER_NAME");
            String strdbpassword = rs.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        String strCommand = "";
        String xml = "";
        int ret_code = 0;
        int strCadreId = 0;
        String strCadreName = "";
        String strCadresName = "";
        String strPayId = "";
        String strCadreRemarks = "";
        response.setContentType("text/xml");
        PrintWriter pw = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            strCadreName = request.getParameter("CadreName");
            strCadresName = request.getParameter("CadresName");
            strPayId = request.getParameter("PayId");
            strCadreRemarks = request.getParameter("CadreRemarks");
        }

        catch (Exception e) {
            System.out.println("in getting values in all other values **** " +
                               e);
        }

        try {
            strCadreId = Integer.parseInt(request.getParameter("CadreId"));

        } catch (Exception e) {
            System.out.println("in getting values in cadre id**** " + e);
        }
        if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";
            try {
                CallableStatement pstmt =
                    connection.prepareCall("{call CADDEL(?)}");
                System.out.println(pstmt);

                pstmt.setInt(1, strCadreId);
                pstmt.executeUpdate();
                xml =
 xml + "<flag>success</flag><CadreId>" + strCadreId + "</CadreId>";
                pstmt.close();
            } catch (SQLException e) {
                ret_code = e.getErrorCode();
                System.err.println(ret_code + e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }

            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Update")) {
            xml = "<response><command>Update</command>";
            try {
                CallableStatement pstmt =
                    connection.prepareCall("{call CADUPDATE(?,?,?,?,?)}");
                System.out.println(pstmt);
                pstmt.setInt(1, strCadreId);
                pstmt.setString(2, strCadreName);
                pstmt.setString(3, strCadresName);
                pstmt.setString(4, strPayId);
                pstmt.setString(5, strCadreRemarks);

                pstmt.executeUpdate();
                xml = xml + "<flag>success</flag>";
                pstmt.close();
            } catch (SQLException e) {
                ret_code = e.getErrorCode();
                System.err.println(ret_code + e.getMessage());
                xml = xml + "<flag>failure</flag>";

            }

            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Add")) {
            xml = "<response><command>Add</command>";
            try {
                CallableStatement pstmt =
                    connection.prepareCall("{call CADID(?,?,?,?,?)}");

                pstmt.setString(2, strCadreName);
                pstmt.setString(3, strCadresName);
                pstmt.setString(4, strPayId);
                pstmt.setString(5, strCadreRemarks);
                pstmt.registerOutParameter(1, Types.INTEGER);
                pstmt.executeUpdate();
                xml = xml + "<flag>success</flag>";
                int CadreId = pstmt.getInt(1);
                System.out.println(CadreId);
                xml = xml + "<CadreId>" + CadreId + "</CadreId>";
                pstmt.close();
            } catch (SQLException e) {
                System.out.println("error is" + e);
                ret_code = e.getErrorCode();
                System.err.println(ret_code + e.getMessage());
                xml = xml + "<flag>failure</flag>";

            }

            xml = xml + "</response>";
        }

        else if (strCommand.equals("Get")) {
            xml = "<response><command>Get</command>";
            try {
                System.out.println("bef res");

                //     results2 = statement.executeQuery("SELECT a.CADRE_ID, a.CADRE_NAME, a.CADRE_SHORT_NAME, a.PAY_SCALE_ID, a.REMARKS, b.PAY_SCALE  FROM HRM_MST_CADRE a ,HRM_MST_PAYSCALES WHERE a.PAY_SCALE_ID=b.PAY_SCALE_ID ORDER BY CADRE_ID");
                results2 =
                        statement.executeQuery("select * from HRM_MST_CADRE order by CADRE_ID ");
                System.out.println("aft res");
                try {
                    xml = xml + "<flag>success</flag>";
                    while (results2.next()) {

                        String PayId = results2.getString("PAY_SCALE_ID");
                        if (PayId == null) {
                            PayId = "NoVal";
                        } else
                            PayId = PayId;
                        //String PayName=results2.getString("PAY_SCALE");
                        int CadreId = results2.getInt("CADRE_ID");
                        String CadreName = results2.getString("CADRE_NAME");

                        String CadresName =
                            results2.getString("CADRE_SHORT_NAME");
                        String Remarks = results2.getString("REMARKS");
                        if (Remarks == null) {
                            Remarks = "UnDefined Record Found";
                        } else {
                            Remarks = results2.getString("REMARKS");
                        }
                        //<PayName>" + PayName + "</PayName>;
                        xml =
 xml + "<CadreId>" + CadreId + "</CadreId><CadreName>" + CadreName +
   "</CadreName><CadresName>" + CadresName + "</CadresName><PayId>" + PayId +
   "</PayId><Remarks>" + Remarks + "</Remarks>";
                    }
                } catch (Exception aee) {
                    System.out.println("Exception in the getting values OF GET: " +
                                       aee);
                }
                results2.close();
                response.setHeader("cache-control", "no-cache");
            } catch (Exception e1) {
                System.out.println("Exception is in Get" + e1);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();

    }
}
