package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.text.DecimalFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class OtherUserProfileUpdateServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        PreparedStatement ps = null;
        System.out.println("hai");
        String userid = request.getParameter("Userid");
        System.out.println(userid);
        String xml = "";

        Connection connection = null;
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


        } catch (Exception e) {
            System.out.println("The Exception is:" + e);

        }
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        String strCommand = request.getParameter("command");
        try {

            if (strCommand.equalsIgnoreCase("Exist")) {
                xml = "<response><command>Exist</command>";
                try {
                    int strcat =
                        Integer.parseInt(request.getParameter("usercat"));

                    ps =
  connection.prepareStatement("select USER_ID from SEC_MST_OTHER_USERS_PROFILE  where USER_CATEGORY_ID=?");
                    ps.setInt(1, strcat);
                    ResultSet rs = ps.executeQuery();
                    int i = 0;
                    while (rs.next()) {
                        xml =
 xml + "<option><id>" + rs.getString("USER_ID") + "</id><name>" +
   rs.getString("USER_ID") + "</name></option>";
                        i++;
                    }
                    if (i != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else {
                        xml = xml + "<flag>failure</flag>";
                    }


                }

                catch (Exception e) {

                    System.out.println("catch........" + e);
                    xml = xml + "<flag>failure</flag>";
                }
                xml = xml + "</response>";


            }

            else if (strCommand.equalsIgnoreCase("ExistRecord")) {
                xml = "<response><command>ExistRecord</command>";
                try {
                    int strcat =
                        Integer.parseInt(request.getParameter("usercat"));
                    String struid = request.getParameter("userid");

                    ps =
  connection.prepareStatement("select USER_ID,USER_NAME,USER_INITIAL,USER_PREFIX,OFFICE_NAME,OFFICE_ADDRESS,DESIGNATION,EMAIL,OFFICE_ID,WING from SEC_MST_OTHER_USERS_PROFILE  where USER_CATEGORY_ID=? and USER_ID=?");
                    ps.setInt(1, strcat);
                    ps.setString(2, struid);
                    ResultSet rs = ps.executeQuery();

                    if (rs.next()) {

                        xml =
 xml + "<uid>" + rs.getString("USER_ID") + "</uid><name>" +
   rs.getString("USER_NAME") + "</name>";
                        xml =
 xml + "<initial>" + rs.getString("USER_INITIAL") + "</initial><prefix>" +
   rs.getString("USER_PREFIX") + "</prefix>";
                        xml =
 xml + "<office>" + rs.getString("OFFICE_NAME") + "</office><off_address>" +
   rs.getString("OFFICE_ADDRESS") + "</off_address>";
                        xml =
 xml + "<designation>" + rs.getString("DESIGNATION") +
   "</designation><email>" + rs.getString("EMAIL") + "</email>";
                        xml =
 xml + "<offid>" + rs.getInt("OFFICE_ID") + "</offid><wing>" +
   rs.getInt("WING") + "</wing>";
                        xml = xml + "<flag>success</flag>";
                    }

                    else {
                        xml = xml + "<flag>failure</flag>";
                    }


                }

                catch (Exception e) {

                    System.out.println("catch........" + e);
                    xml = xml + "<flag>failure</flag>";
                }
                xml = xml + "</response>";


            }

        } catch (Exception e) {
            System.out.println("error in user id :" + e);
        }


        out.write(xml);

        System.out.println(xml);
        out.close();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);

        response.setContentType("text/html");
        PreparedStatement ps = null;
        System.out.println("hai");
        Connection connection = null;

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


            String userid = request.getParameter("txtuserid");
            System.out.println("userid:" + userid);
            String empprefix = request.getParameter("Employee_Prefix");

            System.out.println("empprefix:" + empprefix);
            String empinitial = request.getParameter("Employee_Initial");
            if (empinitial != null)
                empinitial = empinitial.toUpperCase();
            String empname = request.getParameter("Employee_Name");

            String officename = request.getParameter("txtofficename");
            String officeadd = request.getParameter("txtofficeaddress");
            String designation = request.getParameter("txtDesignation");
            String email = request.getParameter("txtemail");
            int usercategory =
                Integer.parseInt(request.getParameter("txtusercategory"));
            System.out.println("usercategory:" + usercategory);
            int offid = Integer.parseInt(request.getParameter("txtoffid"));
            System.out.println("office id" + offid);
            connection.setAutoCommit(false);

            int wing = Integer.parseInt(request.getParameter("txtwing"));
            System.out.println("wing..." + wing);

            System.out.println("ok");
            HttpSession session = request.getSession(false);
            String updatedby = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);

            System.out.println("test1");
            ps =
  connection.prepareStatement("update SEC_MST_OTHER_USERS_PROFILE set USER_ID=?,USER_NAME=?,USER_INITIAL=?,USER_PREFIX=?,USER_CATEGORY_ID=?,OFFICE_NAME=?,OFFICE_ADDRESS=?,DESIGNATION=?,EMAIL=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,OFFICE_ID=?,WING=? where USER_ID=?  and  USER_CATEGORY_ID=? ");

            ps.setString(1, userid);
            ps.setString(2, empname);
            ps.setString(3, empinitial);
            ps.setString(4, empprefix);
            ps.setInt(5, usercategory);
            ps.setString(6, officename);
            ps.setString(7, officeadd);
            ps.setString(8, designation);
            ps.setString(9, email);
            ps.setString(10, updatedby);
            ps.setTimestamp(11, ts);
            ps.setInt(12, offid);
            ps.setInt(13, wing);

            ps.setString(14, userid);
            ps.setInt(15, usercategory);
            ps.executeUpdate();
            System.out.println("test2");

            ps.close();
            connection.commit();
            System.out.println("test3");


        } catch (Exception e) {
            System.out.println("The Exception is:" + e);
            try {
                connection.rollback();
            } catch (Exception e1) {
                System.out.println(e1);
            }
            sendMessage(response, "Record is not Updated",
                        "OtherUserProfileUpdateJSP.jsp");
        }


        sendMessage(response, "Record is Updated Successfully",
                    "OtherUserProfileUpdateJSP.jsp");

    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/CommonMessengerJSP.jsp?message=" + msg +
                "&button=" + bType;
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println(e);
        }
    }
}
