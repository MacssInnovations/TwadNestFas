package Servlets.HR.HR1.EmployeeMaster.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

import java.util.ResourceBundle;

public class ServletTaluk extends HttpServlet {
    //private static final String CONTENT_TYPE = "text/html; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

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


        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }

        // Statement statement=null;
        ResultSet results = null;
        PreparedStatement ps = null;
        PrintWriter pw = response.getWriter();
        response.setContentType("text/xml");
        response.setHeader("cache-control", "no-cache");
        HttpSession session = request.getSession(false);
        try {
            if (session == null) {
                String xml =
                    "<response><command>sessionout</command><flag>sessionout</flag></response>";
                pw.println(xml);
                System.out.println(xml);
                pw.close();
                return;

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        String txtSysId = request.getParameter("txtSysId");
        System.out.println("txtSysId" + txtSysId);
        //response.setContentType(CONTENT_TYPE);
        //PrintWriter out = response.getWriter();
        String xml = "";
        xml = "<response>";
        try {
            ps =
  connection.prepareStatement("select * from COM_MST_TALUKS where District_Code=?");
            ps.setString(1, txtSysId);

            results = ps.executeQuery();
            xml = xml + "<flag>success</flag>";
            while (results.next()) {
                xml =
 xml + "<id>" + results.getInt("TALUK_CODE") + "</id><value>" +
   results.getString("TALUK_NAME") + "</value>";
                ;
            }

        } catch (Exception e) {
            xml = xml + "<flag>failure</flag>";
        }
        xml = xml + "</response>";
        System.out.println("with in get xml :" + xml);
        pw.write(xml);
        pw.flush();
        pw.close();


    }
}
