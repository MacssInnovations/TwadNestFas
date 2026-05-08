package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class JournalMasterServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);


    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet results = null;
        ResultSet results2 = null;
        PreparedStatement ps = null;

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

        response.setContentType(CONTENT_TYPE);
        String strCommand = "",display="";
        String xml = "";
        int ret_code = 0;
        int JournalTypeCode = 0;
        String JournalTypeDesc = "";
        String Category = "";
        String remark="",usageRes="";

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


        response.setContentType("text/xml");
        PrintWriter pw = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        try {
            strCommand = request.getParameter("command");
        } catch (Exception e) {
            e.printStackTrace();
        }
        try {

            JournalTypeDesc = request.getParameter("JournalTypeDesc");
            Category = request.getParameter("Category");
            display = request.getParameter("display");
            remark = request.getParameter("remark");
            usageRes = request.getParameter("usageRes");
            
            System.out.println("journal desc:" + JournalTypeDesc);
            System.out.println("category is:" + Category);
        }

        catch (Exception e) {
            System.out.println("in getting values in all other values **** " +
                               e);
        }

        try {
            JournalTypeCode =
                    Integer.parseInt(request.getParameter("JournalTypeCode"));

        } catch (Exception e) {
            System.out.println("in getting values in cadre id**** " + e);
        }
        if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";
            try {
                PreparedStatement pstmt =
                    connection.prepareStatement("delete from fas_mst_journal_type where journal_type_code=?");
                System.out.println(pstmt);
                pstmt.setInt(1, JournalTypeCode);
                pstmt.executeUpdate();
                xml =
 xml + "<flag>success</flag><JournalTypeCode>" + JournalTypeCode +
   "</JournalTypeCode>";
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
//            	CallableStatement pstmt =
//                connection.prepareCall("{call FAS_JOURNALMASTERPROCEDURE(?,?,?,?,?,?,?,?)}");
            	
            	CallableStatement pstmt =
                    connection.prepareCall("call FAS_JOURNALMASTERPROCEDURE_NEW(?::numeric,?,?,?,?,?::numeric,?,?,?,?)");
            	
            	
                    System.out.println(pstmt);
                    pstmt.setInt(1, JournalTypeCode);
                    pstmt.setString(2, JournalTypeDesc);
                    pstmt.setString(3, display);
                    pstmt.setString(4, remark);
                    pstmt.setString(5, usageRes);
                    pstmt.registerOutParameter(6, Types.NUMERIC);
                    pstmt.setNull(6,java.sql.Types.NUMERIC);
                    pstmt.setString(7, "update");
                    pstmt.setString(8, Category);
                    pstmt.setString(9, userid);
                    
                    
                    long l = System.currentTimeMillis();
                    Timestamp ts = new Timestamp(l);
                    pstmt.setTimestamp(10, ts);
                    
                    
                   
                    pstmt.execute();
                    //int errcode = pstmt.getInt(6);
                    int errcode= pstmt.getBigDecimal(6).intValue();
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

        else if (strCommand.equalsIgnoreCase("Add")) {
            xml = "<response><command>Add</command>";
            try {
//                CallableStatement pstmt =
//                    connection.prepareCall("{call FAS_JOURNALMASTERPROCEDURE(?,?,?,?,?,?,?,?)}"); changed on 05-01-2018 for KT Changes
            	
            	CallableStatement pstmt =
                        connection.prepareCall("call FAS_JOURNALMASTERPROCEDURE_NEW(?::numeric,?,?,?,?,?::numeric,?,?,?,?)");

                pstmt.setInt(1, JournalTypeCode);
                pstmt.setString(2, JournalTypeDesc);
                pstmt.setString(3, display);
                pstmt.setString(4, remark);
                pstmt.setString(5, usageRes);
                pstmt.registerOutParameter(6, Types.INTEGER);
                pstmt.setNull(6,java.sql.Types.NUMERIC);
                pstmt.setString(7, "insert");
                pstmt.registerOutParameter(1, Types.INTEGER);
                pstmt.setNull(1,java.sql.Types.NUMERIC);
                pstmt.setString(8, Category);
                pstmt.setString(9, userid);
                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);
                pstmt.setTimestamp(10, ts);
                
                pstmt.execute();
                int SubLedgerCode = pstmt.getInt(1);
                //int errcode = pstmt.getInt(6);
                int errcode= pstmt.getBigDecimal(6).intValue();
                System.out.println("SQLCODE:::>>>>>>>>" + errcode);
                if (errcode != 0) {
                    xml = xml + "<flag>failure</flag>";
                } else
                    xml = xml + "<flag>success</flag>";

                System.out.println(SubLedgerCode);
                xml =xml + "<JournalTypeCode>" + SubLedgerCode + "</JournalTypeCode><JournalTypeDesc>" +JournalTypeDesc + "</JournalTypeDesc><Category>" + Category + "</Category><display>"+display+"</display><remark>"+remark+"</remark><Usage>"+usageRes+"</Usage>";
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

                //  results2 = statement.executeQuery("SELECT HRM_MST_CADRE.CADRE_ID, HRM_MST_CADRE.CADRE_NAME,HRM_MST_CADRE.CADRE_SHORT_NAME,HRM_MST_CADRE.PAY_SCALE_ID,HRM_MST_CADRE.REMARKS,HRM_MST_PAYSCALES.PAY_SCALE_ID, HRM_MST_PAYSCALES.PAY_SCALE FROM HRM_MST_CADRE,HRM_MST_PAYSCALES WHERE HRM_MST_CADRE.PAY_SCALE_ID=HRM_MST_PAYSCALES.PAY_SCALE_ID ORDER BY HRM_MST_CADRE.CADRE_ID");
                results2 =
                        statement.executeQuery("select journal_type_code,journal_type_desc,category,DISPLAY_RESTRICTED,REMARKS,USAGE_RESTRICTED from fas_mst_journal_type order by journal_type_code");
                System.out.println("aft res");
                try {
                    xml = xml + "<flag>success</flag>";
                    while (results2.next()) {

                        int JournalTypeCode1 =
                            results2.getInt("journal_type_code");
                        String JournalTypeDesc1 =
                            results2.getString("journal_type_desc");
                        String Category1 = results2.getString("category");
                        String display1 = results2.getString("DISPLAY_RESTRICTED");
                        String remarks = results2.getString("REMARKS");
                        String USAGE_RESTRICTED = results2.getString("USAGE_RESTRICTED");
                        
                        
                        
                        
                        
                        //<PayName>" + PayName + "</PayName>;
                        xml =
 xml + "<JournalTypeCode>" + JournalTypeCode1 + "</JournalTypeCode><JournalTypeDesc>" +
   JournalTypeDesc1 + "</JournalTypeDesc><Category>" + Category1 +
   "</Category><display>"+display1+"</display><remarks>"+remarks+"</remarks><usage>"+USAGE_RESTRICTED+"</usage>";
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

        else if (strCommand.equals("Load")) {
            xml = "<response><command>Load</command>";
            try {
                System.out.println("bef load");

                //  results2 = statement.executeQuery("SELECT HRM_MST_CADRE.CADRE_ID, HRM_MST_CADRE.CADRE_NAME,HRM_MST_CADRE.CADRE_SHORT_NAME,HRM_MST_CADRE.PAY_SCALE_ID,HRM_MST_CADRE.REMARKS,HRM_MST_PAYSCALES.PAY_SCALE_ID, HRM_MST_PAYSCALES.PAY_SCALE FROM HRM_MST_CADRE,HRM_MST_PAYSCALES WHERE HRM_MST_CADRE.PAY_SCALE_ID=HRM_MST_PAYSCALES.PAY_SCALE_ID ORDER BY HRM_MST_CADRE.CADRE_ID");
                results2 =
                        statement.executeQuery("select journal_type_code,journal_type_desc,category,DISPLAY_RESTRICTED,REMARKS,USAGE_RESTRICTED from fas_mst_journal_type where category='" +
                                               Category +
                                               "' order by journal_type_code");
                System.out.println("aft res");
                try {
                    xml = xml + "<flag>success</flag>";
                    while (results2.next()) {

                        int JournalTypeCode1 =
                            results2.getInt("journal_type_code");
                        String JournalTypeDesc1 =
                            results2.getString("journal_type_desc");
                        String Category1 = results2.getString("category");
                        String display1 = results2.getString("DISPLAY_RESTRICTED");
                        String remarks = results2.getString("REMARKS");
                        String USAGE_RESTRICTED = results2.getString("USAGE_RESTRICTED");

                        //<PayName>" + PayName + "</PayName>;
                        xml =xml + "<JournalTypeCode>" + JournalTypeCode1 + "</JournalTypeCode><JournalTypeDesc>" +JournalTypeDesc1 + "</JournalTypeDesc><Category>" + Category1 +"</Category><display>"+display1+"</display><remarks>"+remarks+"</remarks><usage>"+USAGE_RESTRICTED+"</usage>";
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
