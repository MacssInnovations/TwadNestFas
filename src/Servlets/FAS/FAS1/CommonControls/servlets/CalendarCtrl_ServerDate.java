package Servlets.FAS.FAS1.CommonControls.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Calendar;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class CalendarCtrl_ServerDate extends HttpServlet {
    //  private static final String CONTENT_TYPE = "xml/text; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        /**
         * Set Content Type
         */
        PrintWriter out = response.getWriter();
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);


        /**
         * Session Checking
         */
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        /**
         * Variables Declaration
         */

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        int year = 0;
        int month = 0;
        int day = 0;

        /**
         * Database Connection
         */
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
        }

        String sql =
            " SELECT \n" + "  to_char(now(),   'DD') AS DAYS,\n" + "  to_char(now(),   'MM') AS mon,\n" +
            "  to_char(now(),   'YYYY') AS yr\n" + " ";
        try {
            ps = con.prepareStatement(sql);
            rs = ps.executeQuery();
            while (rs.next()) {
                year = rs.getInt("yr");
                month = rs.getInt("mon");
                day = rs.getInt("DAYS");
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        /*
               Calendar toDay = Calendar.getInstance();
               int year = toDay.get(Calendar.YEAR);
               int month = toDay.get(Calendar.MONTH);
               int day = toDay.get(Calendar.DATE);
               */

        System.out.println("Year is " + year);
        System.out.println("Month is " + month);
        System.out.println("Day is " + day);

        String xml =
            "<response><command>ServerDate</command><flag>success</flag><Ser_Day>" +
            day + "</Ser_Day><Ser_Mon>" + month + "</Ser_Mon><Ser_Year>" +
            year + "</Ser_Year></response>";

        System.out.println(xml);
        out.println(xml);
        out.close();
    }
}
