package Servlets.HR.HR1.EmployeeMaster.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ChangePasswordServ extends HttpServlet {


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection con = null;
        try {

            File f = new File("");
            f.deleteOnExit();


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
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
        }
        ResultSet rs = null;
        PreparedStatement ps = null;
        PrintWriter out = response.getWriter();
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        String oldpass = null, newpass = "", strCommand = null;
        String strPassword = null;
        int empid = 0;
        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);
            oldpass = request.getParameter("txtoldpass");
            System.out.println("assign..... old pass::" + oldpass);
            newpass = request.getParameter("txtnewpass");
            System.out.println("assign..... new pass::" + newpass);


            HttpSession session = request.getSession(false);
            session.setMaxInactiveInterval(3600);
            System.out.println("session:" + session);
            UserProfile empProfile =
                (UserProfile)session.getAttribute("UserProfile");

            System.out.println("user id::" + empProfile.getEmployeeId());
            empid = empProfile.getEmployeeId();


        } catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
        String xml = null;
        if (strCommand.equalsIgnoreCase("test")) {
            xml = "<response><command>test</command>";
            try {

                byte[] b = oldpass.getBytes();
                try {
                    MessageDigest algorithm = MessageDigest.getInstance("MD5");
                    algorithm.reset();
                    algorithm.update(b);
                    byte messageDigest[] = algorithm.digest();
                    System.out.println("actual encrypt::" + messageDigest);
                    StringBuffer hexString = new StringBuffer();
                    for (int i = 0; i < messageDigest.length; i++) {
                        hexString.append(Integer.toHexString(0xFF &
                                                             messageDigest[i]));
                    }

                    strPassword = new String(hexString);
                } catch (NoSuchAlgorithmException nsae) {
                    System.out.println("first MD5::" + nsae);
                }
                HttpSession session = request.getSession(false);
                String userid = (String)session.getAttribute("UserId");
                ps =
  con.prepareStatement("select USER_ID from SEC_MST_USERS  where USER_PASSWORD=? and USER_ID=? ");
                ps.setString(1, strPassword);
                ps.setString(2, userid);
                rs = ps.executeQuery();
                System.out.println("first check is ok");
                if (rs.next()) {
                    String username = rs.getString("USER_ID");
                    rs.close();
                    ps.close();

                    b = newpass.getBytes();
                    try {
                        MessageDigest algorithm =
                            MessageDigest.getInstance("MD5");
                        algorithm.reset();
                        algorithm.update(b);
                        byte messageDigest[] = algorithm.digest();
                        System.out.println("actual encrypt::" + messageDigest);
                        StringBuffer hexString = new StringBuffer();
                        for (int i = 0; i < messageDigest.length; i++) {
                            hexString.append(Integer.toHexString(0xFF &
                                                                 messageDigest[i]));
                        }

                        strPassword = new String(hexString);
                    } catch (NoSuchAlgorithmException nsae) {
                        System.out.println("Second MD5::" + nsae);
                    }

                    ps =
  con.prepareStatement("update  SEC_MST_USERS set USER_PASSWORD=?,CHANGE_PASSWORD='0'  where USER_ID=?");
                    ps.setString(1, strPassword);
                    ps.setString(2, username);
                    ps.executeUpdate();
                    System.out.println("second check ok");
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
            out.println(xml);
            System.out.println(xml);

        }


    }
}
