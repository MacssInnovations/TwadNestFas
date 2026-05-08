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

public class ServletLoadOfficeDetails extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        System.out.println("\nServletLoadOfficeDetails.java\n");
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        response.setHeader("cache-control", "no-cache");
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        System.out.println("servlet called load");
        System.out.println("11");
        boolean ErrorOccured = false;
        String ErrorMessage = "", cmd = "", deptid = "", xml = "";
        int IntID = 0;
        cmd = request.getParameter("command");
        System.out.println("Command:" + cmd);
        try {
            if (cmd == null)
                cmd = "TwadOffice";
            IntID = Integer.parseInt(request.getParameter("ID"));
            deptid = request.getParameter("DeptId");
        } catch (NumberFormatException nfe) {
            ErrorMessage = "Invalid Office ID(Should be Numeric)";
        }
        PrintWriter out = response.getWriter();
        if (!ErrorOccured) {
            Connection connection = null;
            PreparedStatement ps = null, ps2 = null;
            ResultSet results = null, results2 = null;

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
                System.out.println("Command:=================>" + cmd);
                try {
                    connection.clearWarnings();
                    if (cmd.equalsIgnoreCase("OtherOffice")) {
                        System.out.println("Deportment id=" + deptid);
                        try {
                            connection.clearWarnings();
                            String sql =
                                "select other_dept_office_name from hrm_mst_other_dept_offices where other_dept_id=? and other_dept_office_id=?";
                            ps = connection.prepareStatement(sql);
                            ps.setString(1, deptid);
                            ps.setInt(2, IntID);
                            results = ps.executeQuery();
                            if (results.next()) {
                                xml =
 "<response><flag>success</flag><name>" + results.getString("other_dept_office_name") +
   "</name>";
                                xml = xml + "</response>";
                                // return;
                            } else {
                                ErrorOccured = true;
                                ErrorMessage = "Invalid ID,Record not found.";
                            }
                        } catch (SQLException e) {
                            System.out.println("Exception in creating statement:" +
                                               e);
                            ErrorOccured = true;
                            ErrorMessage = e.getMessage();
                        }
                    } else {
                        System.out.println("inside twad office");
                        String sql =
                            "select a.office_short_name,a.office_address1,a.office_address2,a.city_town_name,a.Office_pin_code,c.Work_Nature_Desc,b.Office_Level_Name, d.district_name from com_mst_offices a left outer join COM_MST_OFFICE_LEVELS b on a.office_level_id=b.office_level_id left outer join COM_MST_WORK_NATURE c on a.primary_work_id=c.work_nature_id left outer join com_mst_districts d on d.district_code=a.district_code where office_id=?";
                        ps = connection.prepareStatement(sql);
                        ps.setInt(1, IntID);
                        results = ps.executeQuery();
                        if (results.next()) {
                            System.out.println("inside results");
                            xml =
 "<response><flag>success</flag><name>" + results.getString("Office_short_Name") +
   "</name><address1>" + results.getString("office_address1") +
   "</address1><address2>" + results.getString("office_address2") +
   "</address2>";
                            xml =
 xml + "<address>" + results.getString("CITY_TOWN_NAME") +
   "</address><district>" + results.getString("district_name") + "</district>";
                            xml =
 xml + "<level>" + results.getString("Office_Level_Name") + "</level><type>" +
   results.getString("Work_Nature_Desc") + "</type><pincode>" +
   results.getInt("Office_pin_code") + "</pincode>";
                            //return;
                        } else {
                            ErrorOccured = true;
                            ErrorMessage = "Invalid ID,Record not found.";
                        }


                        //START - ADDED PART

                        /* To check if the Office is an Accounting Office Unit  -- [This is checked so as to disable 'Accounts Trasfer to' combo if it's NOT an Accounting unit] */
                        String sql2 =
                            "select accounting_unit_office_id from FAS_MST_ACCT_UNITS where accounting_unit_office_id=?";
                        ps2 = connection.prepareStatement(sql2);
                        ps2.setInt(1, IntID);
                        System.out.println("\n\nSQL2: " + sql2 + IntID +
                                           "\n\n");
                        results2 = ps2.executeQuery();
                        if (results2.next()) {
                            xml = xml + "<accounts>enable</accounts>";
                        } else {
                            xml = xml + "<accounts>disable</accounts>";
                        }
                        //END - ADDED PART


                        xml = xml + "</response>";
                    }
                    out.write(xml);
                    System.out.println(xml);
                    results.close();
                    ps.close();
                    connection.close();
                    System.out.println("xml:" + xml);
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

        } else {
            xml =
 "<response><flag>failed1</flag><message>" + ErrorMessage + "</message></response>";
            out.write(xml);
            System.out.println("xml is : " + xml);
            out.close();
        }
    }
}
