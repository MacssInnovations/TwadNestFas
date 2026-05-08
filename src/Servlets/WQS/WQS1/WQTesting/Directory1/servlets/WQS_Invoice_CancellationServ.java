package Servlets.WQS.WQS1.WQTesting.Directory1.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class WQS_Invoice_CancellationServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        String CONTENT_TYPE = "text/xml";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        response.setHeader("cache-control", "no-cache");
        Connection con = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String xml = null;
        String updatedby = null;
        Timestamp ts = null;
        try {
            ResourceBundle rs2 =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs2.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs2.getString("Config.DSN");
            String strhostname = rs2.getString("Config.HOST_NAME");
            String strportno = rs2.getString("Config.PORT_NUMBER");
            String strsid = rs2.getString("Config.SID");
            String strdbusername = rs2.getString("Config.USER_NAME");
            String strdbpassword = rs2.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
            try {
                st = con.createStatement();
                con.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in opening connection:" + e);
        }

        response.setContentType(CONTENT_TYPE);
        HttpSession session = request.getSession(false);
        try {
            if (session == null) {
                xml =
 "<response><command>sessionout</command><flag>sessionout</flag></response>";
                out.println(xml);
                System.out.println(xml);
                out.close();
                return;

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        session = request.getSession(false);
        updatedby = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        ts = new Timestamp(l);
        System.out.println(updatedby);
        System.out.println(ts);

        xml = "<response>";
        String cmd = request.getParameter("command");
        System.out.println(cmd);
        int lab = Integer.parseInt(request.getParameter("lab_code"));
        if (cmd.equalsIgnoreCase("load_invoice")) {
            xml = xml + "<command>load_invoice</command>";
            try {
                ps = con.prepareStatement("select ");
            } catch (Exception e) {
                System.out.println("Err in load_invoice:" + e.getMessage());
            }
        }
        out.close();
    }
}
