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

public class ServletLoadOfficeDetails_Validate extends HttpServlet {
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
        System.out.println("servlet called load");
        boolean ErrorOccured = false;
        String ErrorMessage = "";
        int IntID = 0;
        try {
            IntID = Integer.parseInt(request.getParameter("ID"));
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
                    // String sql="select office_name,office_address1,office_address2,city_town_name,district_code,Work_Nature_Desc,Office_Level_Name from com_mst_offices,COM_MST_OFFICE_LEVELS,COM_MST_WORK_NATURE  where com_mst_offices.Office_Level_Id=COM_MST_OFFICE_LEVELS.Office_Level_Id and com_mst_offices.Primary_Work_Id=COM_MST_WORK_NATURE.Work_Nature_Id and Office_Id=?";
                    //String sql="select office_name,office_address1,office_address2,city_town_name,district_code,Work_Nature_Desc,Office_Level_Name from com_mst_offices a left outer join COM_MST_OFFICE_LEVELS b on a.office_level_id=b.office_level_id left outer join COM_MST_WORK_NATURE c on a.primary_work_id=c.work_nature_id where office_id=?";
                    //  String sql="select a.office_short_name,a.office_address1,a.office_address2,a.city_town_name,a.Office_pin_code,c.Work_Nature_Desc,b.Office_Level_Name, d.district_name from com_mst_offices a left outer join COM_MST_OFFICE_LEVELS b on a.office_level_id=b.office_level_id left outer join COM_MST_WORK_NATURE c on a.primary_work_id=c.work_nature_id left outer join com_mst_districts d on d.district_code=a.district_code where office_id=?";


                    String sql =
                        "select  office_id,office_short_name,office_address1,office_address2, " +
                        " city_town_name,Office_pin_code,Work_Nature_Desc,Office_Level_Name,district_name,PROCESS_FLOW_STATUS_ID from" +
                        " (" +
                        " select a.office_id,a.office_short_name,a.office_address1,a.office_address2," +
                        " a.city_town_name,a.Office_pin_code,c.Work_Nature_Desc," +
                        " b.Office_Level_Name, d.district_name from com_mst_offices a " +
                        " left outer join COM_MST_OFFICE_LEVELS b on a.office_level_id=b.office_level_id " +
                        " left outer join COM_MST_WORK_NATURE c on a.primary_work_id=c.work_nature_id " +
                        " left outer join com_mst_districts d on d.district_code=a.district_code " +
                        " where office_id=?" + " )m" + " left join" + " (" +
                        " select attached_office_id,controlling_OFFICE_ID,ATTACHEMENT_SL_NO,PROCESS_FLOW_STATUS_ID,max(ATTACHEMENT_SL_NO) from COM_OFFICE_ATTACHMENTS" +
                        " group by attached_office_id,controlling_OFFICE_ID,ATTACHEMENT_SL_NO,PROCESS_FLOW_STATUS_ID  having attached_office_id=? " +
                        " )n" + " on m.office_id=n.attached_office_id" +
                        " order by ATTACHEMENT_SL_NO desc";


                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, IntID);
                    ps.setInt(2, IntID);
                    results = ps.executeQuery();
                    if (results.next()) {
                        System.out.println("Status id========================== " +
                                           results.getString("PROCESS_FLOW_STATUS_ID"));
                        String xml =
                            "<response><flag>success</flag><name>" + results.getString("Office_short_Name") +
                            "</name><address1>" +
                            results.getString("office_address1") +
                            "</address1><address2>" +
                            results.getString("office_address2") +
                            "</address2>";
                        xml =
 xml + "<address>" + results.getString("CITY_TOWN_NAME") +
   "</address><district>" + results.getString("district_name") + "</district>";
                        xml =
 xml + "<level>" + results.getString("Office_Level_Name") + "</level><type>" +
   results.getString("Work_Nature_Desc") + "</type><pincode>" +
   results.getInt("Office_pin_code") + "</pincode></response>";
                        response.setContentType(CONTENT_TYPE);
                        response.setHeader("cache-control", "no-cache");
                        PrintWriter out = response.getWriter();
                        out.write(xml);
                        System.out.println("xml is : " + xml);
                        out.close();
                        results.close();
                        ps.close();
                        connection.close();
                        return;
                    } else {
                        ErrorOccured = true;
                        ErrorMessage = "Invalid ID,Record not found.";
                    }
                    results.close();
                    ps.close();
                    connection.close();
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
            System.out.println("No error");
        }
        String xml =
            "<response><flag>failed1</flag><message>" + ErrorMessage + "</message></response>";
        response.setContentType(CONTENT_TYPE);
        response.setHeader("cache-control", "no-cache");
        PrintWriter out = response.getWriter();
        out.write(xml);
        System.out.println("my  xml is : " + xml);
        out.close();
    }
}
