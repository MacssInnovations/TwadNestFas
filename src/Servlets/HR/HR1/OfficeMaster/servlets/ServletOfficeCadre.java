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

public class ServletOfficeCadre extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        System.out.println("servlet called check");
        boolean ErrorOccured = false;
        String ErrorMessage = "";
        String level = request.getParameter("level");
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
                    String sql =
                        "select a.cadre_id,a.cadre_name from hrm_mst_cadre a left outer join com_mst_office_levels b on b.office_head_cadre_id=a.cadre_id where b.office_level_id=?";
                    ps = connection.prepareStatement(sql);
                    ps.setString(1, level);
                    results = ps.executeQuery();
                    if (results.next()) {
                        /*java.sql.Date DateOfFormation = results.getDate("DATE_CLOSED");
                         String DateToBeDisplayed="";
                         if(DateOfFormation==null)
                         {
                             DateToBeDisplayed="Not Specified";
                         }
                         else
                         {
                             try
                             {
                                 java.text.SimpleDateFormat sdf=new java.text.SimpleDateFormat("dd/MM/yyyy");
                                 DateToBeDisplayed=sdf.format(DateOfFormation);
                             }
                             catch(Exception e)
                             {
                                 System.out.println("error while formatting date : " + e);
                                 DateToBeDisplayed="Not Specified";
                             }
                         }   */
                        String xml =
                            "<response><flag>success</flag><cadreid>" +
                            results.getInt("cadre_id") +
                            "</cadreid><cadrename>" +
                            results.getString("cadre_name") +
                            "</cadrename></response>";
                        response.setContentType(CONTENT_TYPE);
                        response.setHeader("cache-control", "no-cache");
                        PrintWriter out = response.getWriter();
                        out.write(xml);
                        System.out.println("xml is : " + xml);
                        out.close();
                        results.close();
                        ps.close();
                        //connection.close();
                        return;
                    } else {
                        ErrorOccured = true;
                        ErrorMessage = "Invalid ID,Record not found.";
                    }
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
