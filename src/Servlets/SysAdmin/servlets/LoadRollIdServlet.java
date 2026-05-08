package Servlets.SysAdmin.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class LoadRollIdServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    private PreparedStatement ps = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
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
//
//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in opening connection:" + e);
        }
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        response.setContentType("text/xml");
        response.setHeader("cache-control", "no-cache");
        String s = "";
        String xml = "";
        try {
            results =
                    statement.executeQuery("SELECT Role_Id FROM SEC_MST_ROLES GROUP BY Role_Id HAVING Role_Id =(select max(Role_Id) from SEC_MST_ROLES)");
            System.out.println("after results");
            System.out.println("within while*****before while****");

            xml = "<response><command>Load</command>";

            while (results.next()) {
                System.out.println("within while*****");

                int RoleId = results.getInt("Role_Id");
                RoleId = RoleId + 1;
                System.out.println("after fetching" + RoleId);
                xml = xml + "<flag>success</flag>";
                xml = xml + "<Role_Id>" + RoleId + "</Role_Id>";
            }
            results.close();
            response.setHeader("cache-control", "no-cache");


        } catch (Exception e1) {
            System.out.println("Exception is" + e1);
            xml = xml + "<flag>failure</flag>";
        }
        xml = xml + "</response>";
        System.out.println(xml);
        out.write(xml);
    }
}
