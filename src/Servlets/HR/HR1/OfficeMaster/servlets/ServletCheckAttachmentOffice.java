package Servlets.HR.HR1.OfficeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ServletCheckAttachmentOffice extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                /* response.setContentType("text/xml");
                response.setHeader("Cache-Control","no-cache");
               String xml="<response><command>session</command><flag>failure</flag><flag>Session already closed.</flag></response>";
               System.out.println(xml);
                out.println(xml);
                out.close();
                return;*/
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        System.out.println("servlet called check");
        System.out.println("3333333");
        boolean ErrorOccured = false;
        String ErrorMessage = "";

        int IntID = 0;
        try {
            IntID = Integer.parseInt(request.getParameter("OfficeId"));
        } catch (NumberFormatException nfe) {
            ErrorMessage = "Invalid Office ID(Should be Numeric)";
        }

        if (!ErrorOccured) {

            Connection connection = null;
            PreparedStatement ps = null;
            ResultSet results = null;

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

                try {
                    connection.clearWarnings();
                    //String sql="select CONTROLLING_OFFICE_ID,DATE_EFFECTIVE_FROm from com_office_control where OFFICE_ID=?";
                    /*  String sql="select closed_office_id from com_office_closure where CLOSED_OFFICE_ID=?";
                     ps=connection.prepareStatement(sql);
                     ps.setInt(1,IntID);
                     results=ps.executeQuery();
                    System.out.println("b4 if"+IntID);
                     if(results.next())
                     {
                        String xml="<response><flag>success</flag><id>"+results.getInt("closed_office_id")+"</id></response>";
                         response.setContentType(CONTENT_TYPE);
                         response.setHeader("cache-control","no-cache");
                         PrintWriter out = response.getWriter();
                         out.write(xml);
                         System.out.println("xml is : " + xml);
                         out.close();
                         results.close();
                         ps.close();
                         //connection.close();
                         return;
                     }*/
                    // else
                    //{
                    System.out.println("inner else" + IntID);
                    String sql2 =
                        "select office_status_id from com_mst_offices where office_id=?";
                    ps = connection.prepareStatement(sql2);
                    ps.setInt(1, IntID);
                    results = ps.executeQuery();
                    String status = "";
                    if (results.next()) {
                        status = results.getString("office_status_id");
                        System.out.println("status is:" + status);
                        if ((status.equalsIgnoreCase("NC")) ||
                            (status.equalsIgnoreCase("RD")) ||
                            (status.equalsIgnoreCase("CL"))) {
                            String xml =
                                "<response><flag>success</flag></response>";
                            response.setContentType(CONTENT_TYPE);
                            response.setHeader("cache-control", "no-cache");
                            PrintWriter out = response.getWriter();
                            out.write(xml);
                            System.out.println("xml in else is : " + xml);
                            out.close();
                            results.close();
                            ps.close();
                        } else {

                        }
                    } else {

                    }

                    //}

                    results.close();
                    ps.close();
                    //connection.close();

                } catch (SQLException e) {
                    System.out.println("Exception in creating statement:" + e);
                    ErrorOccured = true;
                    ErrorMessage = e.getMessage();
                }


            } catch (Exception e) {
                System.out.println("Exception in openeing connection:" + e);
                ErrorOccured = true;
                ErrorMessage = e.getMessage();
            }

        }
        String xml =
            "<response><flag>failed</flag><message>" + ErrorMessage + "</message></response>";
        response.setContentType(CONTENT_TYPE);
        response.setHeader("cache-control", "no-cache");
        PrintWriter out = response.getWriter();
        out.write(xml);
        System.out.println("xml is : " + xml);
        out.close();
    }
}
