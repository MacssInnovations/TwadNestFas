package Servlets.SysAdmin.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import java.util.ResourceBundle;

public class CreateNewUser extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        PreparedStatement ps = null;
        System.out.println("hai");
        int txtEmployeeId =
            Integer.parseInt(request.getParameter("txtEmployeeId"));
        String LoginId = request.getParameter("LoginId");
        System.out.println(LoginId);
        String txtConfirmPassword = request.getParameter("txtConfirmPassword");
        String temppass = request.getParameter("txtConfirmPassword");
        String changePass = null;
        /*//sk This code is added to change to actual password into MD5 password */
        byte[] b = txtConfirmPassword.getBytes();
        try {
            MessageDigest algorithm = MessageDigest.getInstance("MD5");
            algorithm.reset();
            algorithm.update(b);
            byte messageDigest[] = algorithm.digest();
            System.out.println("actual encrypt::" + messageDigest);
            StringBuffer hexString = new StringBuffer();
            for (int i = 0; i < messageDigest.length; i++) {
                hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
            }
            txtConfirmPassword = new String(hexString);
        } catch (NoSuchAlgorithmException nsae) {
            System.out.println(nsae);
        }

        /* //sk  ----------------------------------------------------------*/

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

//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            Connection connection =
                DriverManager.getConnection(ConnectionString,
                                            strdbusername.trim(),
                                            strdbpassword.trim());
            try {

                HttpSession session = request.getSession(false);
                String updatedby = (String)session.getAttribute("UserId");
                long l = System.currentTimeMillis();
                Timestamp ts = new Timestamp(l);

                connection.setAutoCommit(false);
                ps =
  connection.prepareStatement("insert into SEC_MST_USERS(USER_ID,USER_PASSWORD,EMPLOYEE_ID,LOGIN_ENABLED,UPDATED_BY_USER_ID,UPDATED_DATE,USER_CATEGORY_ID,CHANGE_PASSWORD) values(?,?,?,'1',?,?,1,?)");
                ps.setString(1, LoginId);
                ps.setString(2, txtConfirmPassword);
                ps.setInt(3, txtEmployeeId);

                ps.setString(4, updatedby);
                ps.setTimestamp(5, ts);
                if (LoginId.equals(temppass)) {
                    ps.setString(6, "1");
                } else
                    ps.setString(6, "0");
                ps.executeUpdate();
                ps.close();


                ps =
  connection.prepareStatement("insert into SEC_MST_USER_ROLES(EMPLOYEE_ID,ROLE_ID,UPDATED_BY_USER_ID,UPDATED_DATE,LIST_SEQ_NO) values(?,?,?,?,?)");
                ps.setInt(1, txtEmployeeId);
                ps.setInt(2, 22);
                ps.setString(3, updatedby);
                ps.setTimestamp(4, ts);
                ps.setInt(5, 999);
                ps.executeUpdate();
                ps.close();
                System.out.println("help role is added");


                connection.commit();

            } catch (Exception e) {
                System.out.println("The Exception in Query:" + e);
                connection.rollback();
            }
        } catch (Exception e) {
            System.out.println("The Exception is:" + e);

        }
        //out.println("New Login Created Successfully");
        String message = "New User Login Created Successfully.<br>";
        message = message + "Login Id is : " + LoginId + ".<br>";
        String url =
            "org/Library/jsps/Messenger.jsp?message=" + message + "&button=ok";
        response.sendRedirect(url);
        out.close();
    }
}
