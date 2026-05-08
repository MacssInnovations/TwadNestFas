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

public class Daily_Status_Edit extends HttpServlet {
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
            // System.out.println("updated by:"+updatedby);
            long l = System.currentTimeMillis();
            java.sql.Timestamp ts = new java.sql.Timestamp(l);
            //Date d=new Date(request.getParameter("date_of_report"));
            String date1 = request.getParameter("reportdate");
            SimpleDateFormat dateFormat = new SimpleDateFormat("dd/mm/yy");
            Date convertedDate = dateFormat.parse(date1);
            // java.sql.Date mdate=new java.sql.Date(convertedDate.getTime());
            java.sql.Date mdate = new java.sql.Date(convertedDate.getTime());
            System.out.println("updated date:" + mdate);
            String myParam = request.getParameter("myParam");
            Statement st = connection.createStatement();
            ResultSet rs =
                st.executeQuery("select employee_id from sec_mst_users where user_id='" +
                                updatedby + "'");
            if (rs.next()) {
                project_leader_id = rs.getInt(1);
            }

            if (myParam != null && myParam.equalsIgnoreCase("update")) {
                PreparedStatement ps =
                    connection.prepareStatement("update com_mst_daily_status set UPDATED_DATE=?,UPDATED_BY=?,ACTIVITIES_CARRIED_OUT=? where DATE_OF_REPORT=? and PROJECT_LEADER_ID=?");

                ps.setTimestamp(1, ts);
                ps.setString(2, updatedby);
                ps.setString(3, request.getParameter("aco"));
                ps.setDate(4, mdate);
                ps.setInt(5, project_leader_id);
                System.out.println(request.getParameter("aco"));
                System.out.println(updatedby);
                System.out.println(mdate);
                System.out.println(project_leader_id);
                System.out.println(ts);
                ps.execute();
                ps.close();
                System.out.println("success");

                out.write("success");
                System.out.println("ok");
            }
            if (myParam != null && myParam.equalsIgnoreCase("delete")) {
                //st=connection.createStatement();
                PreparedStatement ps =
                    connection.prepareStatement("delete from com_mst_daily_status where DATE_OF_REPORT=? and PROJECT_LEADER_ID=?");
                ps.setDate(1, mdate);
                ps.setInt(2, project_leader_id);
                //int row=st.executeUpdate("delete from com_mst_daily_status where DATE_OF_REPORT='"+mdate+"' and PROJECT_LEADER_ID="+project_leader_id);
                int row = ps.executeUpdate();
                if (row <= 0) {
                    out.write("Unsuccess");
                    System.out.println("Not ok");

                } else {
                    out.write("success");
                    System.out.println("ok");
                }
            }


        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
            success = "Unsuccesful !! ";
            out.write(success);
        }
        /* response.sendRedirect("daily_status_report.jsp?val="+success == null ?
                success:"Yes");                */

        /*out.println(success == null ?
                success:"Successfully Inserted!");*/

        out.close();
    }
}
