package Servlets.HR.HR1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Designation_Servlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    private ResultSet results1 = null;
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
        int ret_code;
        int strDesigId = 0;
        String strDesigDesc = "";
        int strDesigsDesc = 0;
        String strPayScale = "";
        int strCadreId = 0;
        int strSerId = 0;
        String strRemarks = "";
        response.setContentType("text/xml");
        PrintWriter pw = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {

            strDesigDesc = request.getParameter("DesigDesc");
            strDesigsDesc =
                    Integer.parseInt(request.getParameter("DesigsDesc"));
            strPayScale = request.getParameter("PayId");
            strSerId = Integer.parseInt(request.getParameter("SerId"));
            strRemarks = request.getParameter("Remarks");

        } catch (Exception e) {
            System.out.println("in getting values in desc**** " + e);
        }
        try {
            strCadreId = Integer.parseInt(request.getParameter("CadreId"));

        } catch (Exception e) {
            System.out.println("in getting valuesin cadre **** " + e);
        }
        try {
            strDesigId = Integer.parseInt(request.getParameter("DesigId"));
            System.out.println("this is desig id" + strDesigId);
        } catch (Exception e) {
            System.out.println("in getting values in the id **** " + e);
        }
        if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";
            try {
                CallableStatement pstmt =
                    connection.prepareCall("{call DESGDEL(?)}");
                System.out.println(pstmt);

                pstmt.setInt(1, strDesigId);
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

        else if (strCommand.equalsIgnoreCase("Update")) {
            xml = "<response><command>Update</command>";
            try {
                CallableStatement pstmt =
                    connection.prepareCall("{call DESGUPDATE(?,?,?,?,?,?)}");
                System.out.println(pstmt);
                pstmt.setInt(1, strDesigId);
                pstmt.setString(2, strDesigDesc);
                pstmt.setInt(3, strCadreId);
                pstmt.setInt(4, strSerId);
                pstmt.setInt(5, strDesigsDesc);
                pstmt.setString(6, strRemarks);

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
                    connection.prepareCall("{call DESGID(?,?,?,?,?,?)}");
                System.out.println(pstmt);

                pstmt.setString(2, strDesigDesc);
                pstmt.setInt(3, strCadreId);
                pstmt.setInt(4, strSerId);
                pstmt.setInt(5, strDesigsDesc);
                pstmt.setString(6, strRemarks);
                pstmt.registerOutParameter(1, Types.INTEGER);
                pstmt.executeUpdate();
                xml = xml + "<flag>success</flag>";
                int Desigid = pstmt.getInt(1);
                xml = xml + "<DesigId>" + Desigid + "</DesigId>";
                pstmt.close();
            } catch (SQLException e) {
                ret_code = e.getErrorCode();
                System.err.println(ret_code + e.getMessage());
                xml = xml + "<flag>failure</flag>";

            }

            xml = xml + "</response>";
        }

        else if (strCommand.equals("Get")) {
            xml = "<response><command>Get</command>";
            try {
                results2 =
                        statement.executeQuery("SELECT HRM_MST_CADRE.CADRE_ID, HRM_MST_CADRE.CADRE_NAME, HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID, HRM_MST_SERVICE_GROUP.SERVICE_GROUP_NAME, HRM_MST_DESIGNATIONS.DESIGNATION_ID, HRM_MST_DESIGNATIONS.DESIGNATION,  HRM_MST_DESIGNATIONS.CADRE_ID, HRM_MST_DESIGNATIONS.SERVICE_GROUP_ID,HRM_MST_DESIGNATIONS.ORDERING_SEQUENCE_NO ,HRM_MST_DESIGNATIONS.REMARKS FROM HRM_MST_CADRE, HRM_MST_SERVICE_GROUP, HRM_MST_DESIGNATIONS WHERE HRM_MST_DESIGNATIONS.CADRE_ID=HRM_MST_CADRE.CADRE_ID And HRM_MST_DESIGNATIONS.SERVICE_GROUP_ID=HRM_MST_SERVICE_GROUP.SERVICE_GROUP_ID  ORDER BY HRM_MST_DESIGNATIONS.DESIGNATION_ID");
                try {
                    xml = xml + "<flag>success</flag>";
                    while (results2.next()) {
                        System.out.println("1");
                        int DesigId = results2.getInt("DESIGNATION_ID");
                        System.out.println("2");
                        String DesigDesc = results2.getString("DESIGNATION");
                        System.out.println("3");
                        int DesigsDesc =
                            results2.getInt("ORDERING_SEQUENCE_NO");
                        System.out.println("4");
                        //String PayId=results2.getString("PAY_SCALE_ID");
                        System.out.println("5");
                        // String PayName=results2.getString("PAY_SCALE");
                        System.out.println("6");
                        int CadreId = results2.getInt("CADRE_ID");
                        System.out.println("7");
                        String CadreName = results2.getString("CADRE_NAME");
                        System.out.println("18");
                        int ServId = results2.getInt("SERVICE_GROUP_ID");
                        System.out.println("9");
                        String ServName =
                            results2.getString("SERVICE_GROUP_NAME");
                        System.out.println("10");
                        String Remarks = results2.getString("REMARKS");
                        System.out.println("11");
                        if (Remarks == null) {
                            Remarks = "UnDefined Record Found";
                        } else {
                            Remarks = results2.getString("REMARKS");
                        }

                        xml =
 xml + "<DesigId>" + DesigId + "</DesigId><DesigDesc>" + DesigDesc +
   "</DesigDesc><DesigsDesc>" + DesigsDesc + "</DesigsDesc><CadreId>" +
   CadreId + "</CadreId><CadreName>" + CadreName + "</CadreName><ServId>" +
   ServId + "</ServId><ServName>" + ServName + "</ServName><Remarks>" +
   Remarks + "</Remarks>";
                    }
                } catch (Exception aee) {
                    System.out.println("Exception in the getting values IN get : " +
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


        else if (strCommand.equals("Pay")) {
            xml = "<response><command>Pay</command>";
            try {
                String sql =
                    ("SELECT HRM_MST_CADRE.CADRE_ID,HRM_MST_CADRE.PAY_SCALE_ID,HRM_MST_PAYSCALES.PAY_SCALE_ID, HRM_MST_PAYSCALES.PAY_SCALE FROM HRM_MST_CADRE,HRM_MST_PAYSCALES WHERE HRM_MST_CADRE.CADRE_ID=? and HRM_MST_CADRE.PAY_SCALE_ID=HRM_MST_PAYSCALES.PAY_SCALE_ID ORDER BY HRM_MST_CADRE.CADRE_ID");
                ps = connection.prepareStatement(sql);
                ps.setInt(1, strCadreId);
                results1 = ps.executeQuery();
                String PayName = "";
                int i = 0;
                try {

                    while (results1.next()) {
                        String PayId = results1.getString("PAY_SCALE_ID");
                        PayName = results1.getString("PAY_SCALE");
                        int CadreId = results1.getInt("CADRE_ID");
                        i = i + 1;
                    }
                    if (i == 0) {
                        xml = xml + "<flag>failure</flag>";

                    } else {

                        xml = xml + "<flag>success</flag>";
                        xml = xml + "<PayName>" + PayName + "</PayName>";
                    }

                } catch (Exception aee) {
                    System.out.println("Exception in the getting values: " +
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

