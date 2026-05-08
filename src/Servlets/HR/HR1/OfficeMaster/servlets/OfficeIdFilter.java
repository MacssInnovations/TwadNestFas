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

public class OfficeIdFilter extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        System.out.println("hai");
        response.setContentType(CONTENT_TYPE);
        response.setContentType("text/xml");
        PrintWriter out = response.getWriter();
        int OfficeId = 0;
        String OfficeCity = request.getParameter("OfficeCity");
        System.out.println(OfficeCity);
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
                    "select office_id,office_name from com_mst_offices where OFFICE_ADDRESS3 like ?";
                PreparedStatement statement = connection.prepareStatement(sql);
                statement.setString(1, OfficeCity + "%");
                connection.clearWarnings();
                try {
                    ResultSet results = statement.executeQuery();
                    try {
                        xml = "<response><flag>success</flag>";
                        while (results.next()) {
                            xml =
 xml + "<options><id>" + results.getInt("office_id") + "</id><name>" +
   results.getString("office_name") + "</name></options>";
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

        out.println(xml);
        out.close();
    }
}
