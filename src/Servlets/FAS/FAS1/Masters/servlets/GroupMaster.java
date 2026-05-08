package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class GroupMaster extends HttpServlet {
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
        String strCommand = "";
        String xml = "";
        int ret_code = 0;
        int ReasonCode = 0;
        String ReasonDesc = "";
        String section = "";
        int sectioncode = 0;
        PrintWriter out = response.getWriter();

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

        if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";
            try {
                ReasonCode =
                        Integer.parseInt(request.getParameter("txtGroupId"));

            } catch (Exception e) {
                System.out.println("in getting values in cadre id**** " + e);
            }
            try {

                PreparedStatement pstmt =
                    connection.prepareStatement("delete from FAS_MST_GROUPS where GROUP_ID=?");
                System.out.println(pstmt);
                pstmt.setInt(1, ReasonCode);
                pstmt.executeUpdate();
                xml =
 xml + "<flag>success</flag><ReasonCode>" + ReasonCode + "</ReasonCode>";
                pstmt.close();
            } catch (SQLException e) {
                ret_code = e.getErrorCode();
                System.err.println(ret_code + e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }

            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }

        else if (strCommand.equalsIgnoreCase("Update")) {
            xml = "<response><command>Update</command>";
            try {

                ReasonDesc = request.getParameter("txtGroupName");
                // ReasonAbbr=request.getParameter("txtGroup");
                System.out.println("Class desc:" + ReasonDesc);
                //System.out.println("ReasonAbbr is:"+ReasonAbbr);
            }

            catch (Exception e) {
                System.out.println("in getting values in all other values **** " +
                                   e);
            }

            try {
                ReasonCode =
                        Integer.parseInt(request.getParameter("txtGroupId"));

            } catch (Exception e) {
                System.out.println("in getting values in cadre id**** " + e);
            }
            try {
                sectioncode =
                        Integer.parseInt(request.getParameter("cmbsection"));
                System.out.println("sectioncode:" + sectioncode);
            }

            catch (Exception e) {
                System.out.println("in getting values in all other values **** " +
                                   e);
            }
            try {
                CallableStatement pstmt =
                    connection.prepareCall("call FAS_MST_GROUPPROCEDURE(?::numeric,?::numeric,?,?::numeric,?,?,?)");
                System.out.println(pstmt);
                pstmt.setInt(1, ReasonCode);
                pstmt.setInt(2, sectioncode);
                pstmt.setString(3, ReasonDesc);
                pstmt.registerOutParameter(4, Types.NUMERIC);
                pstmt.setNull(4,java.sql.Types.NUMERIC);
                pstmt.setString(5, "update");

                //pstmt.setString(3,ReasonAbbr);
                pstmt.setString(6, userid);
                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);
                pstmt.setTimestamp(7, ts);

                pstmt.execute();
                //int errcode = pstmt.getInt(4);
                int errcode= pstmt.getBigDecimal(4).intValue();
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
            System.out.println(xml);
            out.println(xml);
        }

        else if (strCommand.equalsIgnoreCase("Add")) {
            xml = "<response><command>Add</command>";
            try {

                ReasonDesc = request.getParameter("txtGroupName");
                section = request.getParameter("cmbsection");
                System.out.println("section:" + section);
                // System.out.println("ReasonAbbr is:"+ReasonAbbr);
            }

            catch (Exception e) {
                System.out.println("in getting values in all other values **** " +
                                   e);
            }

            try {

                // ReasonDesc = request.getParameter("txtGroupName");
                sectioncode =
                        Integer.parseInt(request.getParameter("cmbsection"));
                System.out.println("sectioncode:" + sectioncode);
                // System.out.println("ReasonAbbr is:"+ReasonAbbr);
            }

            catch (Exception e) {
                System.out.println("in getting values in all other values **** " +
                                   e);
            }

            /*   try
            {
              ReasonCode= Integer.parseInt(request.getParameter("ReasonCode"));

            }
            catch(Exception e)
            {
                System.out.println("in getting values in cadre id**** "+ e);
            }*/
            try {
                CallableStatement pstmt =
                    connection.prepareCall("call FAS_MST_GROUPPROCEDURE(?::numeric,?::numeric,?,?::numeric,?,?,?)");

                pstmt.setInt(1, ReasonCode);
                pstmt.setInt(2, sectioncode);
                pstmt.setString(3, ReasonDesc);

                pstmt.registerOutParameter(4, Types.INTEGER);
                pstmt.setNull(4,java.sql.Types.NUMERIC);
                pstmt.setString(5, "insert");

                pstmt.registerOutParameter(1, Types.INTEGER);

                pstmt.setString(6, userid);
                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);
                pstmt.setTimestamp(7, ts);
                pstmt.executeUpdate();
                int brsreasoncode1 = pstmt.getInt(1);
                System.out.println("value is:" + pstmt.getInt(1));
                //int errcode = pstmt.getInt(4);
                int errcode= pstmt.getBigDecimal(4).intValue();
                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    xml = xml + "<flag>failure</flag>";
                } else
                    xml = xml + "<flag>success</flag>";

                System.out.println(brsreasoncode1);
                // xml=xml+"<ReasonCode>"+brsreasoncode1+"</ReasonCode><ReasonDesc>"+ReasonDesc+"</ReasonDesc><ReasonAbbr>"+ReasonAbbr+"</ReasonAbbr>";
                xml =
 xml + "<txtGroupId>" + brsreasoncode1 + "</txtGroupId><txtGroupName>" +
   ReasonDesc + "</txtGroupName><cmbsection>" + sectioncode + "</cmbsection>";
                pstmt.close();
            } catch (SQLException e) {
                System.out.println("error is" + e);
                ret_code = e.getErrorCode();
                System.err.println(ret_code + e.getMessage());
                xml = xml + "<flag>failure</flag>";

            }

            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }

        else if (strCommand.equals("Get")) {
            xml = "<response><command>Get</command>";
            int AssetClassCode1 = 0, SectionCode = 0;
            String AssetClassDesc1 = "", Sectname = "";
            try {
                System.out.println("bef res");

                //  results2 = statement.executeQuery("SELECT HRM_MST_CADRE.CADRE_ID, HRM_MST_CADRE.CADRE_NAME,HRM_MST_CADRE.CADRE_SHORT_NAME,HRM_MST_CADRE.PAY_SCALE_ID,HRM_MST_CADRE.REMARKS,HRM_MST_PAYSCALES.PAY_SCALE_ID, HRM_MST_PAYSCALES.PAY_SCALE FROM HRM_MST_CADRE,HRM_MST_PAYSCALES WHERE HRM_MST_CADRE.PAY_SCALE_ID=HRM_MST_PAYSCALES.PAY_SCALE_ID ORDER BY HRM_MST_CADRE.CADRE_ID");
                results2 =
                        statement.executeQuery("select a.SECTION_ID,a.GROUP_ID,a.GROUP_NAME,b.SECTION_NAME from FAS_MST_GROUPS a," +
                                               "FAS_MST_SECTIONS b where a.SECTION_ID=b.SECTION_ID order by SECTION_ID ");
                System.out.println("aft res");
                try {
                    xml = xml + "<flag>success</flag>";
                    while (results2.next()) {

                        AssetClassCode1 = results2.getInt("GROUP_ID");
                        AssetClassDesc1 = results2.getString("GROUP_NAME");
                        SectionCode = results2.getInt("SECTION_ID");
                        Sectname = results2.getString("SECTION_NAME");
                        System.out.println("groupid" + AssetClassCode1);
                        System.out.println("groupname" + AssetClassCode1);

                        xml =
 xml + "<txtGroupId>" + AssetClassCode1 + "</txtGroupId><txtGroupName>" +
   AssetClassDesc1 + "</txtGroupName><sectioncode>" + SectionCode +
   "</sectioncode><cmbsection>" + Sectname + "</cmbsection>";
                    }
                } catch (Exception aee) {
                    System.out.println("Exception in the getting values OF GET: " +
                                       aee);
                }
                results2.close();
            } catch (Exception e1) {
                System.out.println("Exception is in Get" + e1);
                xml = xml + "<flag>failure</flag>";
            }
            //xml=xml+"<txtGroupId>"+AssetClassCode1+"</txtGroupId><txtGroupName>"+AssetClassDesc1+"</txtGroupName><sectioncode>"+SectionCode+"</sectioncode><cmbsection>"+Sectname+"</cmbsection>";
            xml = xml + "</response>";
            //System.out.println(xml);
            out.println(xml);
        }


        else if (strCommand.equals("Load")) {
            xml = "<response><command>Get</command>";
            int AssetClassCode1 = 0, SectionCode = 0;
            String AssetClassDesc1 = "", Sectname = "";
            try {
                System.out.println("bef res");

                //  results2 = statement.executeQuery("SELECT HRM_MST_CADRE.CADRE_ID, HRM_MST_CADRE.CADRE_NAME,HRM_MST_CADRE.CADRE_SHORT_NAME,HRM_MST_CADRE.PAY_SCALE_ID,HRM_MST_CADRE.REMARKS,HRM_MST_PAYSCALES.PAY_SCALE_ID, HRM_MST_PAYSCALES.PAY_SCALE FROM HRM_MST_CADRE,HRM_MST_PAYSCALES WHERE HRM_MST_CADRE.PAY_SCALE_ID=HRM_MST_PAYSCALES.PAY_SCALE_ID ORDER BY HRM_MST_CADRE.CADRE_ID");
                results2 =
                        statement.executeQuery("select a.SECTION_ID,a.GROUP_ID,a.GROUP_NAME,b.SECTION_NAME from FAS_MST_GROUPS a," +
                                               "FAS_MST_SECTIONS b where a.SECTION_ID=b.SECTION_ID order by SECTION_ID");
                System.out.println("aft res");
                try {
                    xml = xml + "<flag>success</flag>";
                    while (results2.next()) {

                        AssetClassCode1 = results2.getInt("GROUP_ID");
                        AssetClassDesc1 = results2.getString("GROUP_NAME");
                        SectionCode = results2.getInt("SECTION_ID");
                        Sectname = results2.getString("SECTION_NAME");
                        System.out.println("groupid" + AssetClassCode1);
                        System.out.println("groupname" + AssetClassCode1);

                        xml =
 xml + "<txtGroupId>" + AssetClassCode1 + "</txtGroupId><txtGroupName>" +
   AssetClassDesc1 + "</txtGroupName><sectioncode>" + SectionCode +
   "</sectioncode><cmbsection>" + Sectname + "</cmbsection>";
                    }
                } catch (Exception aee) {
                    System.out.println("Exception in the getting values OF GET: " +
                                       aee);
                }
                results2.close();
            } catch (Exception e1) {
                System.out.println("Exception is in Get" + e1);
                xml = xml + "<flag>failure</flag>";
            }
            // xml=xml+"<txtGroupId>"+AssetClassCode1+"</txtGroupId><txtGroupName>"+AssetClassDesc1+"</txtGroupName><sectioncode>"+SectionCode+"</sectioncode><cmbsection>"+Sectname+"</cmbsection>";
            xml = xml + "</response>";
            //  System.out.println(xml);
            out.println(xml);
        }

        System.out.println("xml is : " + xml);
        //  pw.write(xml);
        pw.flush();
        pw.close();

    }
}
