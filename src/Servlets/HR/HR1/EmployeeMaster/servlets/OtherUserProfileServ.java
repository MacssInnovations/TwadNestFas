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

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class OtherUserProfileServ extends HttpServlet {
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
            Connection connection =
                DriverManager.getConnection(ConnectionString,
                                            strdbusername.trim(),
                                            strdbpassword.trim());


            ps =
  connection.prepareStatement("select USER_ID from SEC_MST_USERS  where USER_ID=?");
            ps.setString(1, userid);

            ResultSet result = ps.executeQuery();

            if (result.next()) {
                xml =
 "<response><command>Add</command><flag>ExistRec</flag></response>";
            } else {
                xml =
 "<response><command>Add</command><flag>success</flag></response>";
            }
        } catch (Exception e) {
            System.out.println("The Exception is:" + e);
            xml =
 "<response><command>Add</command><flag>success</flag></response>";
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
            System.out.println(userid);
            String empprefix = request.getParameter("Employee_Prefix");
            String empinitial = request.getParameter("Employee_Initial");
            if (empinitial != null)
                empinitial = empinitial.toUpperCase();
            String empname = request.getParameter("Employee_Name");
            String usercategory = request.getParameter("txtusercategory");
            String officename = request.getParameter("txtofficename");
            String officeadd = request.getParameter("txtofficeaddress");
            String designation = request.getParameter("txtDesignation");
            String email = request.getParameter("txtemail");
            String password = request.getParameter("txtpassword");
            int offid = Integer.parseInt(request.getParameter("txtoffid"));
            int wing = Integer.parseInt(request.getParameter("txtwing"));
            System.out.println("wing" + wing);

            System.out.println("office id..." + offid);

            connection.setAutoCommit(false);

            System.out.println("ok");
            HttpSession session = request.getSession(false);
            String updatedby = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);

            System.out.println("test1");
            ps =
  connection.prepareStatement("insert into SEC_MST_OTHER_USERS_PROFILE(USER_ID,USER_NAME,USER_INITIAL,USER_PREFIX,USER_CATEGORY_ID,OFFICE_NAME,OFFICE_ADDRESS,DESIGNATION,EMAIL,UPDATED_BY_USER_ID,UPDATED_DATE,OFFICE_ID,WING) values(?,?,?,?,?,?,?,?,?,?,?,?,?) ");

            ps.setString(1, userid);
            ps.setString(2, empname);
            ps.setString(3, empinitial);
            ps.setString(4, empprefix);
            ps.setString(5, usercategory);
            ps.setString(6, officename);
            ps.setString(7, officeadd);
            ps.setString(8, designation);
            ps.setString(9, email);
            ps.setString(10, updatedby);
            ps.setTimestamp(11, ts);
            ps.setInt(12, offid);
            ps.setInt(13, wing);
            ps.executeUpdate();
            System.out.println("test2");


            byte[] b = password.getBytes();
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
                password = new String(hexString);
                System.out.println("test3");
            } catch (NoSuchAlgorithmException nsae) {
                System.out.println(nsae);
            }


            System.out.println("test4");
            ps =
  connection.prepareStatement("insert into SEC_MST_USERS(USER_ID,USER_PASSWORD,LOGIN_ENABLED,UPDATED_BY_USER_ID,UPDATED_DATE,USER_CATEGORY_ID) values(?,?,'1',?,?,?)");
            ps.setString(1, userid);
            ps.setString(2, password);

            ps.setString(3, updatedby);
            ps.setTimestamp(4, ts);
            ps.setString(5, usercategory);
            ps.executeUpdate();
            ps.close();
            System.out.println("test5");

            ps =
  connection.prepareStatement("insert into SEC_MST_OTHER_USER_ROLES(USER_ID,ROLE_ID,LIST_SEQ_NO,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,22,999,?,?)");
            ps.setString(1, userid);

            ps.setString(2, updatedby);
            ps.setTimestamp(3, ts);
            ps.executeUpdate();
            ps.close();
            connection.commit();
            System.out.println("test6");


        } catch (Exception e) {
            System.out.println("The Exception is:" + e);
            try {
                connection.rollback();
            } catch (Exception e1) {
                System.out.println(e1);
            }
            sendMessage(response, "Record is not Added",
                        "OtherUserProfileJSP.jsp");
        }


        sendMessage(response, "Record is Updated Successfully",
                    "OtherUserProfileJSP.jsp");

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
