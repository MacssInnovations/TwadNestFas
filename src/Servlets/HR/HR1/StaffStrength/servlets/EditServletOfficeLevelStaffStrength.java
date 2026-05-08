package Servlets.HR.HR1.StaffStrength.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.sql.Statement;

import java.util.ResourceBundle;

import java.util.logging.Level;

import javax.servlet.*;
import javax.servlet.http.*;

public class EditServletOfficeLevelStaffStrength extends HttpServlet {
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
        System.out.println("servlet called Office Level Staff Strength");
        boolean ErrorOccured = false;
        String ErrorMessage = "";
        String Level = "";
        int IntID = 0;
        try {
            IntID = Integer.parseInt(request.getParameter("OfficeId"));

        } catch (NumberFormatException nfe) {
            ErrorMessage = "Invalid Office ID(Should be Numeric)";
        }

        if (!ErrorOccured) {

            Connection connection = null;
            PreparedStatement ps = null;
            PreparedStatement ps1 = null;
            PreparedStatement ps2 = null;
            ResultSet results1 = null;
            ResultSet results = null;
            ResultSet results2 = null;
            Statement statement = null;
            String value = "";
            String value1 = "";
            String xml = "";
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

                ConnectionString =
                        strdsn.trim() + "@" + strhostname.trim() + ":" +
                        strportno.trim() + ":" + strsid.trim();

                Class.forName(strDriver.trim());
                connection =
                        DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                    strdbpassword.trim());

                try {
                    System.out.println(IntID);
                    connection.clearWarnings();
                    int level_id = 0;
                    int level_hierarchy = 0;
                    String sql =
                        "select OFFICE_LEVEL_ID from COM_MST_OFFICEs where office_id=?";

                    try {
                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, IntID);
                        results = ps.executeQuery();
                        if (results.next()) {
                            Level = results.getString("OFFICE_LEVEL_ID");
                            System.out.println("Level in" + Level);
                        }
                        //ps.close();
                        //results.close();
                    } catch (Exception e) {
                        System.out.println("Exception in Hireach:" + e);
                    }
                    if (Level.equals("HO")) {
                        System.out.println("inner ho");
                        String sql4 =
                            "select office_level_id,office_level_name from com_mst_office_levels order by office_level_name";
                        ps = connection.prepareStatement(sql4);
                        results = ps.executeQuery();
                        System.out.println("results is:" + results);
                        xml = "<response><flag>success</flag>";
                        while (results.next()) {
                            xml =
 xml + "<options><id>" + results.getString("office_level_id") +
   "</id><value>" + results.getString("office_level_name") +
   "</value></options>";
                        }
                        xml = xml + "</response>";
                        PrintWriter out = response.getWriter();
                        response.setContentType("text/xml");
                        System.out.println("xml is:" + xml);
                        out.write(xml);

                    } else {
                        try {
                            System.out.println("Level After" + Level);
                            System.out.println("select HIERARCHICAL_SEQUENCE from COM_MST_OFFICE_LEVELS where OFFICE_LEVEL_ID='" +
                                               Level + "'");
                            String sql1 =
                                "select HIERARCHICAL_SEQUENCE from COM_MST_OFFICE_LEVELS where OFFICE_LEVEL_ID=?";
                            ps1 = connection.prepareStatement(sql1);
                            ps1.setString(1, Level);
                            results1 = ps1.executeQuery();

                            System.out.println("Before Hirech");
                            if (results1.next()) {
                                level_id =
                                        results1.getInt("HIERARCHICAL_SEQUENCE");
                                System.out.println("level id value is------>  " +
                                                   level_id);
                            }

                            level_hierarchy = level_id / 10;
                            System.out.println("level hierarchy id-------> ....." +
                                               level_hierarchy);
                            //results1.close();


                        } catch (Exception e) {
                            System.out.println("Exception in first:" + e);
                        }
                        try {
                            xml = "<response><flag>success</flag>";
                            System.out.println("Level id:" + Level);
                            System.out.println("Lhireac:" + level_hierarchy);
                            try {
                                statement = connection.createStatement();
                            } catch (Exception e) {
                                System.out.println("Exception in statement" +
                                                   e);
                            }
                            System.out.println(" the NEW querry is..............**      " +
                                               "select * from com_mst_office_levels where HIERARCHICAL_SEQUENCE>" +
                                               level_id +
                                               " and HIERARCHICAL_SEQUENCE  not like '" +
                                               level_hierarchy + "%'");
                            //and HIERARCHICAL_SEQUENCE like '"+level_hierarchy+"%'"
                            results2 =
                                    statement.executeQuery("select OFFICE_LEVEL_ID,OFFICE_LEVEL_NAME from com_mst_office_levels where HIERARCHICAL_SEQUENCE>=" +
                                                           level_id + "");
                            while (results2.next()) {
                                value1 = results2.getString("OFFICE_LEVEL_ID");
                                value =
results2.getString("OFFICE_LEVEL_NAME");
                                System.out.println("value is" + value);
                                xml =
 xml + "<options><id>" + value1 + "</id><value>" + value +
   "</value></options>";
                            }
                            //results2.close();
                            xml = xml + "</response>";

                        } catch (Exception e) {
                            System.out.println("Exception in New Query:" + e);
                        }


                        response.setContentType(CONTENT_TYPE);
                        response.setHeader("cache-control", "no-cache");
                        PrintWriter out = response.getWriter();
                        out.write(xml);
                        System.out.println("xml is : " + xml);
                    }


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


    }
}
