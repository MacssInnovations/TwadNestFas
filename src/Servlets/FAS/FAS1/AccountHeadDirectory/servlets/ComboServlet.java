package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import javax.servlet.*;
import javax.servlet.http.*;

import java.io.PrintWriter;
import java.io.IOException;

import java.sql.*;

import java.util.ResourceBundle;

public class ComboServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        System.out.println("combo servlet called");
        PrintWriter out = response.getWriter();
        String str = "";
        try {
            str = request.getParameter("mgc");

        } catch (Exception e) {
            e.printStackTrace();
        }
        String s = "";
        try {
            ResourceBundle rs1 =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs1.getString("Config.DSN");
            String strhostname = rs1.getString("Config.HOST_NAME");
            String strportno = rs1.getString("Config.PORT_NUMBER");
            String strsid = rs1.getString("Config.SID");
            String strdbusername = rs1.getString("Config.USER_NAME");
            String strdbpassword = rs1.getString("Config.PASSWORD");

//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


            Class.forName(strDriver.trim());
            Connection con =
                DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                            strdbpassword.trim());


            String query =
                "select * from FAS_MINOR_ACCOUNT_HEAD where MAJOR_HEAD_CODE=?";
            PreparedStatement ps = con.prepareStatement(query);
            ResultSet rs = null;
            ps.setString(1, str);
            rs = ps.executeQuery();
            while (rs.next()) {
                s =
   s + "<option><code>" + rs.getString("MINOR_HEAD_CODE") + "</code>";
                s =
   s + "<desc>" + rs.getString("MINOR_HEAD_DESC") + "</desc></option>";
            }

            response.setHeader("Cache-control", "no-cache");
            out.write("<select>" + s + "</select>");
            System.out.println("returning" + str);
            System.out.println("xml is" + s);

        } catch (Exception e1) {
            System.out.println("Exception is" + e1);
        }
    }
}
