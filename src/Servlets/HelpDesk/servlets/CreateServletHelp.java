package Servlets.HelpDesk.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

public class CreateServletHelp extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    private Connection connection = null;
    private PreparedStatement ps = null;
    private PreparedStatement ps1 = null;
    private ResultSet res1 = null;
    private ResultSet res = null;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        System.out.println("inside post");
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
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
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());

        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }

        String txtHUserName = request.getParameter("txtHUserName");
        String majorsystem = request.getParameter("cmbMajor");
        String minorsystem = request.getParameter("cmbMinor");
        String priority = request.getParameter("cmbPriority");
        String txtSubject = request.getParameter("txtSubject");
        String txtDesc = request.getParameter("txtdesc");
        int maxno = 0;
        System.out.println("username" + txtHUserName);
        System.out.println("majorsystem" + majorsystem);
        System.out.println("minorsystem" + minorsystem);
        System.out.println("priority" + priority);
        System.out.println("txtsubject" + txtSubject);
        System.out.println("txtdesc" + txtDesc);
        java.util.Calendar g = new java.util.GregorianCalendar();
        java.util.Date d1 = g.getTime();
        java.sql.Date today = new java.sql.Date(d1.getTime());
        System.out.println("date is" + today);
        try {
            String sql1 = "select max(ISSUE_ID) from hlp_issue_requests";
            ps = connection.prepareStatement(sql1);
            res = ps.executeQuery();
            if (res.next()) {
                maxno = res.getInt(1);
                maxno = maxno + 1;
                System.out.println("maxno is" + maxno);

            }

            HttpSession session = request.getSession(false);
            UserProfile up = null;
            up = (UserProfile)session.getAttribute("UserProfile");
            int empid = 0;
            empid = up.getEmployeeId();
            System.out.println(up);
            System.out.println("Employee id:" + empid);
            int officeid = 0;
            String sql =
                "select office_id from hrm_emp_current_posting where employee_id=?";

            ps1 = connection.prepareStatement(sql);
            ps1.setInt(1, empid);
            res = ps1.executeQuery();

            if (res.next()) {
                officeid = res.getInt("office_id");
            }

            System.out.println("office id:" + officeid);
            String Userid = (String)session.getAttribute("UserId");
            sql =
 "insert into HLP_ISSUE_REQUESTS(Issue_id,MAJOR_SYSTEM_ID,MINOR_SYSTEM_ID,ISSUE_TITLE,ISSUE_DESC,ISSUE_REPORTED_DATE,ISSUE_STATUS,REPORTED_BY_USER_ID,ISSUE_PRIORITY,OFFICE_ID) values(?,?,?,?,?,?,?,?,?,?)";
            ps1 = connection.prepareStatement(sql);
            ps1.setInt(1, maxno);
            ps1.setString(2, majorsystem);
            ps1.setString(3, minorsystem);
            ps1.setString(4, txtSubject.replace("&", " and "));
            ps1.setString(5, txtDesc.replace("&", " and "));
            ps1.setDate(6, today);
            ps1.setString(7, "O");
            ps1.setString(8, Userid);
            ps1.setString(9, priority);
            ps1.setInt(10, officeid);
            System.out.println("test1");
            ps1.executeUpdate();
            System.out.println("test2");
            String msg = "Your Issue Has been Posted Successfully. Issue Id: "+maxno;
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");


        } catch (Exception e) {
            System.out.println("Exception in insert" + e);
        }

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
