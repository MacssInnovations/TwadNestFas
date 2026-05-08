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

public class ServletOfficeFilter extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        String strCriteria = "";
        int Office_Id = 0;
        Office_Id = Integer.parseInt(request.getParameter("OfficeId"));
        int found = 0;
        String xml = "";
        Connection connection = null;
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

                String sql =
                    "select office_id,office_short_name,Office_address1,office_address2,CITY_TOWN_NAME,OFFICE_PIN_CODE,DISTRICT_CODE,OFFICE_PHONE_NO,ADDL_PHONE_NOS,OFFICE_EMAIL_ID,ADDL_EMAIL_IDS,OFFICE_FAX_NO,ADDL_FAX_NOS,OFFICE_STD_CODE from com_mst_offices where Office_Id=?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setInt(1, Office_Id);
                connection.clearWarnings();
                try {
                    ResultSet results = statement.executeQuery();
                    try {
                        xml = "<response><flag>success</flag>";
                        while (results.next()) {
                            //xml=xml+"<options><id>"+results.getInt("office_id") + "</id><name>" + results.getString("office_name") + "</name></options>";
                            xml =
 xml + "<options><id>" + results.getInt("OFFICE_ID") + "</id><name>" +
   results.getString("OFFICE_short_NAME") + "</name><officeAddress1>" +
   results.getString("OFFICE_ADDRESS1") + "</officeAddress1><officeAddress2>" +
   results.getString("OFFICE_ADDRESS2") + "</officeAddress2><officeAddress3>" +
   results.getString("CITY_TOWN_NAME") + "</officeAddress3><pincode>" +
   results.getInt("OFFICE_PIN_CODE") + "</pincode><District>" +
   results.getInt("DISTRICT_CODE") + "</District><PhoneNo>" +
   results.getString("OFFICE_PHONE_NO") + "</PhoneNo><AddPhone>" +
   results.getString("ADDL_PHONE_NOS") + "</AddPhone><EmailId>" +
   results.getString("OFFICE_EMAIL_ID") + "</EmailId><AddEmail>" +
   results.getString("ADDL_EMAIL_IDS") + "</AddEmail><FaxNo>" +
   results.getString("OFFICE_FAX_NO") + "</FaxNo><AddFaxNo>" +
   results.getString("ADDL_FAX_NOS") + "</AddFaxNo><StdCode>" +
   results.getString("OFFICE_STD_CODE") + "</StdCode></options>";
                            found++;
                        }
                        if (found == 0)
                            xml = "<response><flag>failure</flag>";

                        xml = xml + "</response>";
                    } catch (SQLException e) {

                    } finally {
                        results.close();
                    }
                } catch (SQLException e) {
                    System.out.println("Exception in statement:" + e);
                } finally {
                    statement.close();
                }
            } catch (SQLException e) {
                System.out.println("Exception in connection:" + e);
            } finally {
                connection.close();
            }
        } catch (Exception e) {
            System.out.println("Exception :" + e);
        }

        System.out.println("Xml is : " + xml);
        response.setContentType("text/xml");
        response.setHeader("cache-control", "no-cache");
        PrintWriter out = response.getWriter();

        out.println(xml);
        out.close();
    }
}
