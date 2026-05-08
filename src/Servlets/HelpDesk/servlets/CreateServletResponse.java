package Servlets.HelpDesk.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.DriverManager;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;


public class CreateServletResponse extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet res = null;
        response.setContentType(CONTENT_TYPE);
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
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());

        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }
        String userid = (String)session.getAttribute("UserId");
        System.out.println("session id is:" + userid);
        int issue = 0;
        try {
            issue = Integer.parseInt(request.getParameter("txt_iss_id"));
            System.out.println("issue id is:" + issue);
        } catch (Exception e) {
            System.out.println("NumberFormatException" + e);
        }
        int ii = 0;
        String solution = request.getParameter("txtsolution");
        System.out.println("solution is" + solution);
        String status = request.getParameter("cmbstatus");
        System.out.println("status is:" + status);
        java.util.Calendar g = new java.util.GregorianCalendar();
        java.util.Date d1 = g.getTime();
        java.sql.Date today = new java.sql.Date(d1.getTime());
        System.out.println("date is" + today);
        try {
            String sql =
                "update HLP_ISSUE_REQUESTS set ISSUE_SOLUTION=?,SOLUTION_BY_USER_ID=?,LAST_UPDATED_DATE=?,ISSUE_STATUS=? where issue_id=?";
            ps = connection.prepareStatement(sql);
            ps.setString(1, solution);
            ps.setString(2, userid);
            ps.setDate(3, today);
            ps.setString(4, status);
            ps.setInt(5, issue);
            ii = ps.executeUpdate();
            String msg = "Your Response Has been Successfully Updated";
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");


        } catch (Exception e) {
            System.out.println("Exception in try" + e);
        }
        out.close();
    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
                bType;
            response.sendRedirect(url);
        } catch (IOException e) {
        }
    }
}
