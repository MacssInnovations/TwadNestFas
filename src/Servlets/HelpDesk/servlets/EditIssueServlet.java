package Servlets.HelpDesk.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.DriverManager;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

public class EditIssueServlet extends HttpServlet {
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
        PreparedStatement ps1 = null;
        ResultSet res1 = null;
        ResultSet res = null;
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
        String issue = request.getParameter("cmbissue_id");
        String txtHUserName = request.getParameter("txtHUserName");
        String majorsystem = request.getParameter("cmbMajor");
        String minorsystem = request.getParameter("cmbMinor");
        String priority = request.getParameter("cmbPriority");
        String txtSubject = request.getParameter("txtSubject");
        String txtDesc = request.getParameter("txtdesc");
        int maxno = 0;
        int issueid = 0;
        try {
            issueid = Integer.parseInt(issue);
        } catch (Exception e) {
            System.out.println("NumberFormatException:" + e);
        }
        System.out.println("issue id is:" + issueid);
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
            /* String sql1="select max(ISSUE_ID) from hlp_issue_requests";
            ps=connection.prepareStatement(sql1);
            res=ps.executeQuery();
            if(res.next())
            {
                maxno=res.getInt(1);
                maxno=maxno+1;
                System.out.println("maxno is"+maxno);

            }*/
            HttpSession session = request.getSession(false);
            String Userid = (String)session.getAttribute("UserId");
            String sql =
                "update HLP_ISSUE_REQUESTS set MAJOR_SYSTEM_ID=?,MINOR_SYSTEM_ID=?,ISSUE_TITLE=?,ISSUE_DESC=?,ISSUE_REPORTED_DATE=?,ISSUE_STATUS=?,REPORTED_BY_USER_ID=?,ISSUE_PRIORITY=? where issue_id=?";
            ps1 = connection.prepareStatement(sql);
            //ps1.setInt(1,maxno);
            ps1.setString(1, majorsystem);
            ps1.setString(2, minorsystem);
            ps1.setString(3, txtSubject);
            ps1.setString(4, txtDesc);
            ps1.setDate(5, today);
            ps1.setString(6, "O");
            ps1.setString(7, Userid);
            ps1.setString(8, priority);
            ps1.setInt(9, issueid);
            ps1.executeUpdate();

            String msg = "Your Issue Has been Posted Successfully";
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
