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

public class ServletLoadDefaultCadre extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        System.out.println("servlet called");
        String level = request.getParameter("level");
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
                    "select OFFICE_HEAD_CADRE_ID  from  COM_MST_OFFICE_LEVELS where OFFICE_LEVEL_ID=?";
                ps = connection.prepareStatement(sql);
                ps.setString(1, level);
                results = ps.executeQuery();
                if (results.next()) {
                    String xml =
                        "<response><flag>success</flag><level>" + results.getString("OFFICE_HEAD_CADRE_ID") +
                        "</level></response>";
                    response.setContentType(CONTENT_TYPE);
                    response.setHeader("cache-control", "no-cache");
                    PrintWriter out = response.getWriter();
                    out.write(xml);
                    out.close();
                    return;
                }
                results.close();
                ps.close();
                connection.close();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }
        String xml = "<response><flag>failure</flag></response>";
        response.setContentType(CONTENT_TYPE);
        response.setHeader("cache-control", "no-cache");
        PrintWriter out = response.getWriter();
        out.write(xml);
        out.close();

    }
}
