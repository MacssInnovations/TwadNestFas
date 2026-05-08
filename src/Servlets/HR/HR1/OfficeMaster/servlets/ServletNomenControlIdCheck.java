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

public class ServletNomenControlIdCheck extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        System.out.println("servlet called");
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
            PreparedStatement ps1 = null;
            ResultSet results = null;
            ResultSet results1 = null;
            String level = "";
            String xml = "";
            int cnt = 0;

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
                        "select CONTROLLING_OFFICE_ID from COM_OFFICE_CONTROL where CONTROLLING_OFFICE_ID=?";
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, IntID);

                    results = ps.executeQuery();
                    if (results.next()) {
                        System.out.println("innerooooooooooooo-------->" +
                                           IntID);
                        ps1 =
 connection.prepareStatement("select office_level_id from com_mst_offices where office_id=?");
                        ps1.setInt(1, IntID);
                        results1 = ps1.executeQuery();
                        if (results1.next()) {
                            level = results1.getString("office_level_id");
                            //level=level.trim();
                            System.out.println("level" + level);
                            if (level.equalsIgnoreCase("RN") ||
                                level.equalsIgnoreCase("CL") ||
                                level.equalsIgnoreCase("HO")) {
                                System.out.println("innerlevel" + level);
                                String sql2 =
                                    "select count(*) as cnt from COM_MST_ALL_OFFICES_VIEW";
                                if (level.equalsIgnoreCase("RN"))
                                    sql2 += "region_office_id=?";
                                else if (level.equalsIgnoreCase("CL"))
                                    sql2 += "circle_office_id=?";
                                ResultSet results2 = ps1.executeQuery();
                                if (results.next()) {
                                    cnt = results.getInt("cnt");
                                }
                                if (cnt > 1)
                                    xml =
 "<response><flag>failure</flag></response>";
                                else
                                    xml =
 "<response><flag>success</flag></response>";

                            } else {
                                System.out.println("else--------------------------------------------------------------------------------------------------->");

                                String sql2 =
                                    "select OFFICE_ID from COM_OFFICE_CONTROL where CONTROLLING_OFFICE_ID=?";
                                PreparedStatement ps2 =
                                    connection.prepareStatement(sql2);
                                ps2.setInt(1, IntID);
                                ResultSet results2 = ps2.executeQuery();
                                xml = "<response><flag>success</flag>";
                                while (results2.next()) {
                                    int officeid =
                                        results2.getInt("OFFICE_ID");
                                    String sql3 =
                                        "select office_id,OFFICE_NAME,OFFICE_ADDRESS1,OFFICE_ADDRESS2,CITY_TOWN_NAME from com_mst_offices where office_id=?";
                                    String sql4 =
                                        "select a.office_id,a.office_name,a.office_address1,a.office_address2,a.city_town_name,a.Office_pin_code,c.Work_Nature_Desc,b.Office_Level_Name, d.district_name from com_mst_offices a left outer join COM_MST_OFFICE_LEVELS b on a.office_level_id=b.office_level_id left outer join COM_MST_WORK_NATURE c on a.primary_work_id=c.work_nature_id left outer join com_mst_districts d on d.district_code=a.district_code where office_id=? and a.office_status_id not in ('RD','NC','CL')";
                                    PreparedStatement ps3 =
                                        connection.prepareStatement(sql4);
                                    ps3.setInt(1, officeid);
                                    ResultSet results3 = ps3.executeQuery();
                                    while (results3.next()) {
                                        xml =
 xml + "<officeid>" + results3.getInt("office_id") +
   "</officeid><officename>" + results3.getString("OFFICE_NAME") +
   "</officename><officeaddress1>" + results3.getString("OFFICE_ADDRESS1") +
   "</officeaddress1><officeaddress2>" +
   results3.getString("OFFICE_ADDRESS2") + "</officeaddress2><city>" +
   results3.getString("city_town_name") + "</city><district>" +
   results3.getString("district_name") + "</district><pincode>" +
   results3.getString("Office_pin_code") + "</pincode>";
                                    }

                                }

                                xml = xml + "</response>";
                            }

                        }


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
            "<response><flag>failedcontrol</flag><message>" + ErrorMessage +
            "</message></response>";
        response.setContentType(CONTENT_TYPE);
        response.setHeader("cache-control", "no-cache");
        PrintWriter out = response.getWriter();
        out.write(xml);
        System.out.println("xml is : " + xml);
        out.close();
    }
}
