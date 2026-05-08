package Servlets.WQS.WQS1.WQTesting.Reports.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.Security.classes.UserProfile;

public class StandardTest_Servlet extends HttpServlet {
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
            System.out.println("Exception in opening connection:" + e);
        }

        response.setContentType(CONTENT_TYPE);
        String strCommand = "";
        String xml = "";
        String SampleCode = "";
        String PurposeCode = "";
        String StandardCode = "";
        String Element = "";
        String TestName = "";
        String TestType = "";
        String Cphe = "";
        String Bis = "";
        String Who = "";
        String Prac = "";
        int Print = 0;

        HttpSession session = request.getSession(false);
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
        UserProfile empProfile =
            (UserProfile)session.getAttribute("UserProfile");

        System.out.println("user id::" + empProfile.getEmployeeId());
        int empid = empProfile.getEmployeeId();
        System.out.println("Time is" + ts);


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

            SampleCode = request.getParameter("SampleId");
            StandardCode = request.getParameter("StandardId");
            PurposeCode = request.getParameter("PurposeId");
            Element = request.getParameter("Element");
            TestName = request.getParameter("TestName");
            TestType = request.getParameter("TestType");
            Cphe = request.getParameter("Cphe");
            Bis = request.getParameter("Bis");
            Who = request.getParameter("Who");
            Prac = request.getParameter("Prac");
            Print = Integer.parseInt(request.getParameter("Print"));
            System.out.println("Sample Code is" + SampleCode);
            System.out.println("Standard Code is" + StandardCode);
            System.out.println("Test Type is" + TestType);
        }

        catch (Exception e) {
            System.out.println("in getting values in all other values **** " +
                               e);
        }

        /*try
       {
         CustomerSubCode= Integer.parseInt(request.getParameter("SubId"));
         System.out.println("Customer Sub Code is"+CustomerSubCode);
           ConcessionCode=Integer.parseInt(request.getParameter("ConcessionId"));
           //ConcessionCode=Integer.parseInt(request.getParameter("ConcessionId"));
           System.out.println("Concession Code is"+ConcessionCode);
           //Rate=Integer.parseInt(request.getParameter("Rate"));
           Rate=Float.parseFloat(request.getParameter("Rate"));
           System.out.println("Rate is"+Rate);
           Actual=Float.parseFloat(request.getParameter("Actual"));
           System.out.println("Actual Concession is"+Actual);
       }
       catch(Exception e)
       {
           System.out.println("in getting values in cadre id**** "+ e);
       }*/
        if (strCommand.equals("Get")) {
            System.out.println("Get method invoked");
            xml = "<response><command>Get</command>";
            try {
                System.out.println("bef res");

                //  results2 = statement.executeQuery("SELECT HRM_MST_CADRE.CADRE_ID, HRM_MST_CADRE.CADRE_NAME,HRM_MST_CADRE.CADRE_SHORT_NAME,HRM_MST_CADRE.PAY_SCALE_ID,HRM_MST_CADRE.REMARKS,HRM_MST_PAYSCALES.PAY_SCALE_ID, HRM_MST_PAYSCALES.PAY_SCALE FROM HRM_MST_CADRE,HRM_MST_PAYSCALES WHERE HRM_MST_CADRE.PAY_SCALE_ID=HRM_MST_PAYSCALES.PAY_SCALE_ID ORDER BY HRM_MST_CADRE.CADRE_ID");
                results2 =
                        statement.executeQuery("select * from WQS_MST_STD_TEST order by STD_CODE");
                System.out.println("aft res");
                try {
                    xml = xml + "<flag>success</flag>";
                    while (results2.next()) {

                        String StandardCode1 = results2.getString("STD_CODE");
                        String SampleCode1 =
                            results2.getString("SAMPLE_TYPE_CODE");
                        String PurposeCode1 =
                            results2.getString("TEST_PURPOSE_CODE");
                        String Element1 = results2.getString("ELEMENT_SYMBOL");
                        String TestName1 = results2.getString("TEST_NAME");
                        String TestType1 = results2.getString("TEST_TYPE");
                        String Cphe1 = results2.getString("CPHE_STD");
                        String Bis1 = results2.getString("BIS_STD");
                        String Who1 = results2.getString("WHO_STD");
                        String Prac1 = results2.getString("PRAC_STD");
                        int Print1 = results2.getInt("PRINT_PRIORITY");

                        //<PayName>" + PayName + "</PayName>;
                        xml =
 xml + "<SampleCode>" + SampleCode1 + "</SampleCode>";
                        xml =
 xml + "<StandardCode>" + StandardCode1 + "</StandardCode>";
                        xml =
 xml + "<PurposeCode>" + PurposeCode1 + "</PurposeCode>";
                        xml = xml + "<Element>" + Element1 + "</Element>";
                        xml = xml + "<TestName>" + TestName1 + "</TestName>";
                        xml = xml + "<TestType>" + TestType1 + "</TestType>";
                        xml = xml + "<Cphe>" + Cphe1 + "</Cphe>";
                        xml = xml + "<Bis>" + Bis1 + "</Bis>";
                        xml = xml + "<Who>" + Who1 + "</Who>";
                        xml = xml + "<Prac>" + Prac1 + "</Prac>";
                        xml = xml + "<Print>" + Print1 + "</Print>";
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
        } else if (strCommand.equalsIgnoreCase("Update")) {
            xml = "<response><command>Update</command>";
            try {
                PreparedStatement pstmt =
                    connection.prepareStatement("update WQS_MST_STD_TEST set ELEMENT_SYMBOL=?,TEST_NAME=?,TEST_TYPE=?,CPHE_STD=?,BIS_STD=?,WHO_STD=?,PRAC_STD=?,PRINT_PRIORITY=? where STD_CODE=? and SAMPLE_TYPE_CODE=? and TEST_PURPOSE_CODE=?");
                System.out.println(pstmt);
                System.out.println("Sample Code" + SampleCode);
                pstmt.setString(1, Element);
                pstmt.setString(2, TestName);
                pstmt.setString(3, TestType);
                pstmt.setString(4, Cphe);
                pstmt.setString(5, Bis);
                pstmt.setString(6, Who);
                pstmt.setString(7, Prac);
                pstmt.setInt(8, Print);
                pstmt.setString(9, StandardCode);
                pstmt.setString(10, SampleCode);
                pstmt.setString(11, PurposeCode);

                pstmt.executeUpdate();

                xml = xml + "<flag>success</flag>";
                pstmt.close();
            } catch (SQLException e) {
                //ret_code = e.getErrorCode();
                // System.err.println(ret_code + e.getMessage());
                xml = xml + "<flag>failure</flag>";

            }

            xml = xml + "</response>";
        } else if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";
            try {
                PreparedStatement pstmt =
                    connection.prepareStatement("delete from WQS_MST_STD_TEST where STD_CODE=? and SAMPLE_TYPE_CODE=? and TEST_PURPOSE_CODE=?");
                System.out.println(pstmt);
                pstmt.setString(1, StandardCode);
                pstmt.setString(2, SampleCode);
                pstmt.setString(3, PurposeCode);
                //System.out.println(CustomerSubCode);
                pstmt.executeUpdate();
                xml = xml + "<flag>success</flag>";
                xml = xml + "<SampleCode>" + SampleCode + "</SampleCode>";
                xml =
 xml + "<StandardCode>" + StandardCode + "</StandardCode>";
                xml = xml + "<PurposeCode>" + PurposeCode + "</PurposeCode>";


                pstmt.close();
            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
            }

            xml = xml + "</response>";
        }


        else if (strCommand.equalsIgnoreCase("Add")) {
            xml = "<response><command>Add</command>";
            try {
                PreparedStatement pstmt =
                    connection.prepareStatement("insert into WQS_MST_STD_TEST(STD_CODE,SAMPLE_TYPE_CODE,TEST_PURPOSE_CODE,ELEMENT_SYMBOL,TEST_NAME,TEST_TYPE,CPHE_STD,BIS_STD,WHO_STD,PRAC_STD,PRINT_PRIORITY)values(?,?,?,?,?,?,?,?,?,?,?)");
                pstmt.setString(1, StandardCode);
                pstmt.setString(2, SampleCode);
                pstmt.setString(3, PurposeCode);
                pstmt.setString(4, Element);
                pstmt.setString(5, TestName);
                pstmt.setString(6, TestType);
                pstmt.setString(7, Cphe);
                pstmt.setString(8, Bis);
                pstmt.setString(9, Who);
                pstmt.setString(10, Prac);
                pstmt.setInt(11, Print);


                pstmt.executeUpdate();

                xml = xml + "<flag>success</flag>";
                xml = xml + "<SampleCode>" + SampleCode + "</SampleCode>";
                xml =
 xml + "<StandardCode>" + StandardCode + "</StandardCode>";
                xml = xml + "<PurposeCode>" + PurposeCode + "</PurposeCode>";
                xml = xml + "<Element>" + Element + "</Element>";
                xml = xml + "<TestName>" + TestName + "</TestName>";
                xml = xml + "<TestType>" + TestType + "</TestType>";
                xml = xml + "<Cphe>" + Cphe + "</Cphe>";
                xml = xml + "<Bis>" + Bis + "</Bis>";
                xml = xml + "<Who>" + Who + "</Who>";
                xml = xml + "<Prac>" + Prac + "</Prac>";
                xml = xml + "<Print>" + Print + "</Print>";

                pstmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
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
