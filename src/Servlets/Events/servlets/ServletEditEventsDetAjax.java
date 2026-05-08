package Servlets.Events.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;

import java.util.ResourceBundle;

import java.sql.*;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServletEditEventsDetAjax extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    private Connection connection = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        ResultSet results = null;

        try {
            System.out.println("@@@called Servlet");
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
            String xml = "";
            String str_status = "", str_event_desc = "", str_event_venue =
                "", str_event_date = "", str_target_url = "";
            int event_id = 0;


            java.sql.Date DateOfFormation = null;
            String DateToBeDisplayed = "", ControllingOfficeName = "";
            event_id = Integer.parseInt(request.getParameter("eventId"));
            System.out.println("id is " + event_id);
            try {
                String sql = "select * from com_mst_events where event_id=?";
                PreparedStatement ps = connection.prepareStatement(sql);
                ps.setInt(1, event_id);
                results = ps.executeQuery();
                if (results.next()) {
                    //event_id=results.getInt(event_id);
                    str_event_desc = results.getString("event_desc");
                    //System.out.println("desc :"+str_event_desc);
                    str_event_venue = results.getString("event_venue");
                    //System.out.println("desc :"+str_event_venue);
                    DateOfFormation = results.getDate("event_date");
                    //System.out.println("desc :"+str_event_date);

                    if (DateOfFormation == null) {
                        DateToBeDisplayed = "null";
                    } else {
                        try {
                            java.text.SimpleDateFormat sdf =
                                new java.text.SimpleDateFormat("dd/MM/yyyy");
                            DateToBeDisplayed = sdf.format(DateOfFormation);
                        } catch (Exception e) {
                            System.out.println("error while formatting date : " +
                                               e);
                        }
                    }
                    System.out.println("date aftr: " + DateToBeDisplayed);


                    str_target_url = results.getString("target_url");
                    //System.out.println("desc :"+str_target_url);
                    str_status = results.getString("event_status_id");
                    //System.out.println("desc :"+str_status);

                    System.out.println("values retrived sucessfully");
                    xml =
 "<response><flag>success</flag><EventId>" + event_id +
   "</EventId><EventDesc>" + str_event_desc + "</EventDesc><EventVenue>" +
   str_event_venue + "</EventVenue><EventDate>" + DateToBeDisplayed +
   "</EventDate><TargetUrl>" + str_target_url + "</TargetUrl><EventStatus>" +
   str_status + "</EventStatus></response>";
                    response.setContentType(CONTENT_TYPE);
                    response.setHeader("cache-control", "no-cache");
                    System.out.println("xml is:" + xml);


                } else {
                    System.out.println("in else part");
                    xml = "<response><flag>failure</flag></response>";
                    System.out.println("xml is:" + xml);
                }
            } catch (Exception e) {
                xml = "<response><flag>failure</flag></response>";
                System.out.println("Exception in Query:" + e);
                System.out.println("xml is:" + xml);
            }
            out.write(xml);

        } catch (Exception e) {
            System.out.println("Exception in Connection:" + e);

        }
        out.close();

    }
}
