package Servlets.HR.HR1.OfficeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.DriverManager;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

public class ServletWingOfficeLevel extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        Connection connection = null;
        PreparedStatement ps = null;
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
        } catch (Exception e) {
            System.out.println("Exception in Connection :" + e);
        }

        System.out.println("Servlet Called Wing Office level");
        String officeid = request.getParameter("OfficeId");
        int Office_Id = 0;
        if (officeid != "null") {
            Office_Id = Integer.parseInt(officeid);
            System.out.println("Office Id is:" + Office_Id);
        }

        try {
            String sql =
                "select office_level_id from com_mst_offices where office_id=?";
            ps = connection.prepareStatement(sql);
            ps.setInt(1, Office_Id);
            ResultSet rs = ps.executeQuery();
            String xml = "";
            if (rs.next()) {
                xml =
 "<response><flag>success</flag><levelid>" + rs.getString("office_level_id").trim() +
   "</levelid></response>";
            } else {
                xml = "<response><flag>failure</flag></response>";

            }
            response.setContentType("text/xml");
            out.write(xml);
            System.out.println("Xml is:" + xml);
        } catch (Exception e) {
            System.out.println("Exception in Creating Xml:" + e);
        }

        out.close();
    }
}
