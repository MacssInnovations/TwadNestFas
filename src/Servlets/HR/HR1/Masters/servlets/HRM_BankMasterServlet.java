package Servlets.HR.HR1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class HRM_BankMasterServlet extends HttpServlet {
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results2 = null;
    private ResultSet results3 = null;
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


        /**
         * Variables Declaration
         */
        String strCommand = "";
        String xml = "";
        int ret_code = 0;
        int strCadreId = 0;
        String strCadreName = "";
        String strCadresName = "";
        String strCadreRemarks = "";

        /**
         * Set Content Type
         */
        response.setContentType("text/xml");
        PrintWriter pw = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");


        /**
         * Session Checking
         */
        HttpSession session = request.getSession(false);
        try {
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        String userid = (String)session.getAttribute("UserId");
        System.out.println("session id is:" + userid);


        /**
         * Command Parameter
         */
        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            /** Get Bank ID */
            strCadreId = Integer.parseInt(request.getParameter("CadreId"));
        } catch (Exception e) {
            System.out.println(e);
        }

        try {
            /** Get Bank Name */
            strCadreName = request.getParameter("CadreName").toUpperCase();
            System.out.println("Bank Name -->" + strCadreName);

            /** Get Bank Short Description Name */
            strCadresName = request.getParameter("CadresName").toUpperCase();
            System.out.println("Bank short Desc -->" + strCadresName);

            /** Get Bank Remarks */
            strCadreRemarks = request.getParameter("CadreRemarks");
        } catch (Exception e) {
            System.out.println("in getting values in all other values **** " +
                               e);
        }


        /**
         *  Delete Operation
         */
        if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";
            try {
                strCadreId = Integer.parseInt(request.getParameter("CadreId"));
            } catch (Exception e) {
                System.out.println("in getting values in cadre id**** " + e);
            }


            try {

                PreparedStatement pstmt =
                    connection.prepareStatement("delete from HRM_MST_BANKS where BANK_ID=?");
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

        /**
             * Updation Operation
             */

        else if (strCommand.equalsIgnoreCase("Update")) {
            xml = "<response><command>Update</command>";
            try {
                CallableStatement pstmt =
                    connection.prepareCall("{call HRM_MST_BANKMASTERPROCEDURE(?,?,?,?,?,?,?,?)}");

                pstmt.setInt(1, strCadreId);
                pstmt.setString(2, strCadreName);
                pstmt.setString(3, strCadresName);
                pstmt.setString(4, strCadreRemarks);
                pstmt.registerOutParameter(5, Types.NUMERIC);
                pstmt.setString(6, "update");
                pstmt.setString(7, userid);

                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);
                pstmt.setTimestamp(8, ts);
                pstmt.execute();

                int errcode = pstmt.getInt(5);
                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    xml = xml + "<flag>failure</flag>";
                } else
                    xml = xml + "<flag>success</flag>";
                pstmt.close();

            } catch (SQLException e) {
                ret_code = e.getErrorCode();
                System.err.println(ret_code + e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        /**
         * Record Adding
         */
        else if (strCommand.equalsIgnoreCase("Add")) {
            xml = "<response><command>Add</command>";
            int cnt = 0;

            try {
                String sql =
                    "select  count(*) as cnt from hrm_mst_banks where BANK_NAME like '%" +
                    strCadreName + "%'";
                System.out.println(sql);
                ps = connection.prepareStatement(sql);
                results3 = ps.executeQuery();
                while (results3.next()) {
                    cnt = results3.getInt("cnt");
                }

                if (cnt == 0) {
                    CallableStatement pstmt =
                        connection.prepareCall("{call HRM_MST_BANKMASTERPROCEDURE(?,?,?,?,?,?,?,?)}");
                    pstmt.registerOutParameter(1, Types.INTEGER);
                    pstmt.setString(2, strCadreName);
                    pstmt.setString(3, strCadresName);
                    pstmt.setString(4, strCadreRemarks);
                    pstmt.registerOutParameter(5, Types.NUMERIC);
                    pstmt.setString(6, "insert");
                    pstmt.setString(7, userid);

                    long l = System.currentTimeMillis();
                    Timestamp ts = new Timestamp(l);
                    pstmt.setTimestamp(8, ts);

                    pstmt.executeUpdate();
                    int CadreId = pstmt.getInt(1);

                    System.out.println("value is:" + pstmt.getInt(1));
                    int errcode = pstmt.getInt(5);
                    System.out.println("SQLCODE:::" + errcode);
                    if (errcode != 0) {
                        xml = xml + "<flag>failure</flag>";
                    } else
                        xml = xml + "<flag>success</flag>";
                    xml = xml + "<CadreId>" + CadreId + "</CadreId>";
                    pstmt.close();
                } else {
                    xml = xml + "<flag>Already</flag>";
                }

            } catch (SQLException e) {
                ret_code = e.getErrorCode();
                System.err.println(ret_code + e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }

            xml = xml + "</response>";

        }


        /**
         * Fetch All Records from DB
         */
        else if (strCommand.equals("Get")) {
            xml = "<response><command>Get</command>";
            try {
                System.out.println("bef res");


                results2 =
                        statement.executeQuery("select * from HRM_MST_BANKS order by BANK_ID ");
                System.out.println("aft res");
                try {
                    xml = xml + "<flag>success</flag>";
                    while (results2.next()) {

                        int CadreId = results2.getInt("BANK_ID");
                        String CadreName = results2.getString("BANK_NAME");

                        String CadresName =
                            results2.getString("BANK_SHORT_NAME");
                        String Remarks = results2.getString("REMARKS");
                        if (Remarks == null) {
                            Remarks = "UnDefined Record Found";
                        } else {
                            Remarks = results2.getString("REMARKS");
                        }

                        xml =
 xml + "<CadreId>" + CadreId + "</CadreId><CadreName>" + CadreName +
   "</CadreName><CadresName>" + CadresName + "</CadresName><Remarks>" +
   Remarks + "</Remarks>";
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
