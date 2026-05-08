package Servlets.FAS.FAS1.CommonControls.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Com_Schedule_Account_Heads extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();

        /**
         * Variables Declaration
         */
        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;


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
            System.out.println("Exception in connection...." + e);
        }


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
         * GET Command Parameter
         */

        String Schedule_ID = "";
        Schedule_ID = request.getParameter("COMMAND");


        /**
         * Fetch Account Head Code and its Desciption
         */


        String xml = "<response><command>Schedule_Account_Heads</command>";

        xml = xml + "<option>";
        xml = xml + "<account_head_code>0</account_head_code>";
        xml =
 xml + "<account_head_code_desc>-- All --</account_head_code_desc>";
        xml = xml + "</option>";

        try {
            String sql =
                "  select account_head_code, account_head_desc from com_mst_account_heads    \n" +
                "  where account_head_code in (                     \n" +
                "  select account_head_code from fas_schedulemaster \n" +
                "  where schedule_id=?                              \n" +
                "  )        order by      account_head_code           \n";
            ps = con.prepareStatement(sql);
            ps.setString(1, Schedule_ID);
            rs = ps.executeQuery();
            while (rs.next()) {
                xml = xml + "<option>";
                xml =
 xml + "<account_head_code>" + rs.getInt("account_head_code") +
   "</account_head_code>";
                xml =
 xml + "<account_head_code_desc>" + rs.getString("account_head_desc") +
   "</account_head_code_desc>";
                xml = xml + "</option>";
            }
            xml = xml + "<flag>success</flag>";

        } catch (Exception e) {
            System.out.println("Error -->" + e);
            xml = xml + "<flag>failure</flag>";
        }

        xml = xml + "</response>";
        out.println(xml);
        out.close();


    }
}
