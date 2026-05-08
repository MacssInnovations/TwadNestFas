package Servlets.HelpDesk.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.text.SimpleDateFormat;

import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Daily_status_update extends HttpServlet {
    // private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        // response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        Connection connection = null;
        String success = null;

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
        } catch (Exception ex) {
            String connectMsg =
                "Could not create the connection" + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }
        //response.setContentType(CONTENT_TYPE);
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);
            int project_leader_id = 0;
            String updatedby = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            java.sql.Timestamp ts = new java.sql.Timestamp(l);
            //Date d=new Date(request.getParameter("date_of_report"));
            String date1 = request.getParameter("date_of_report");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yy");
            Date convertedDate = dateFormat.parse(date1);
            java.sql.Date mdate = new java.sql.Date(convertedDate.getTime());


            Statement st = connection.createStatement();
            ResultSet rs =
                st.executeQuery("select employee_id from sec_mst_users where user_id='" +
                                updatedby + "'");
            if (rs.next()) {
                project_leader_id = rs.getInt(1);
            }
            PreparedStatement ps =
                connection.prepareStatement("insert into com_mst_daily_status values(?,?,?,?,?)");
            ps.setInt(1, project_leader_id);
            ps.setTimestamp(2, ts);
            ps.setString(3, updatedby);

            ps.setDate(4, mdate);
            ps.setString(5, request.getParameter("activities_carried_out"));
            ps.execute();
            System.out.println("success");

            out.write("Successfully Inserted!");
            System.out.println("ok");

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
            success = "Unsuccesful !Already Report Exists for this date! ";
            out.write(success);
        }
        /* response.sendRedirect("daily_status_report.jsp?val="+success == null ?
                success:"Yes");                */

        /*out.println(success == null ?
                success:"Successfully Inserted!");*/

        out.close();
    }
}
