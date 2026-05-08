package Servlets.SysAdmin.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import java.util.ResourceBundle;

public class Servlet1 extends HttpServlet {
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
//
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

                connection.setAutoCommit(false);
                ps =
  connection.prepareStatement("insert into SEC_MST_LOGINS(Login_Id,Login_Password,Employee_Id) values(?,?,?)");
                ps.setString(1, LoginId);
                ps.setString(2, txtConfirmPassword);
                ps.setInt(3, txtEmployeeId);
                ps.executeUpdate();
                ps.close();
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
