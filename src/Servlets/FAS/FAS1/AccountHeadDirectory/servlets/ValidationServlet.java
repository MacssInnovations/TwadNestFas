package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

import java.util.ResourceBundle;

public class ValidationServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";
    //private static final String DOC_TYPE;

    private Connection connection = null;
    private Statement statement = null;
    private ResultSet results = null;
    private PreparedStatement ps = null;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        // opening connection to the database
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
            System.out.println("Exception in openeing connection:" + e);
        }


    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String command = request.getParameter("command");
        System.out.println("Servlet Called");


        if (command.equals("verify")) {
            String xml = "";
            xml = "<response><command>verify</command>";
            String code = request.getParameter("code");


            int ahid = -1;
            try {
                ahid = Integer.parseInt(code);
                System.out.println(ahid);
            } catch (NumberFormatException ne) {
                // error in casting
            }
            try {
                System.out.println("code: " + code + " int : " + ahid);
                String sql2 =
                    "select * from FAS_ACCOUNT_HEAD_MASTER where ACCOUNT_HEAD_CODE=?";
                ps = connection.prepareStatement(sql2);
                ps.setInt(1, ahid);

                ResultSet rs = ps.executeQuery();
                if (rs.next()) {


                    xml = xml + "<flag>success</flag>";
                    xml = xml + "<value>";
                    xml = xml + "<AHCode>" + ahid + "</AHCode>";
                    xml = xml + "</value>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }


                ps.close();
            } catch (Exception e) {
                System.out.println("exce ****2 vv" + e);
                // cannot insert values
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

            out.print(xml);
            System.out.println("xml generated is:" + xml);

            out.close();
        }
    }
}
