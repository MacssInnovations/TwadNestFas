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

public class GroupNameLoading extends HttpServlet {

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
        PreparedStatement ps2 = null;
        ResultSet rs2 = null;


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


        int txtSectionId = 0;
        String xml = null;


        /** Get Accouting UNit ID */
        try {
            txtSectionId =
                    Integer.parseInt(request.getParameter("txtSectionId"));
        } catch (Exception e) {
            System.out.println(e);
        }


        String sql =
            " SELECT GROUP_ID,     \n" + "  GROUP_NAME          \n" + " FROM FAS_MST_GROUPS  \n" +
            " WHERE section_id=?   \n" + " ORDER BY group_name    ";

        try {
            ps2 = con.prepareStatement(sql);
            ps2.setInt(1, txtSectionId);
            rs2 = ps2.executeQuery();
            int count = 0;

            xml = "<response><command>LoadGroupName</command>";

            /** Count How many Records are available  */
            while (rs2.next()) {
                xml =
 xml + "<group_id>" + rs2.getString("group_id") + "</group_id>";
                xml =
 xml + "<group_name>" + rs2.getString("group_name") + "</group_name>";
                count++;
            }

            if (count == 0) {
                xml = xml + "<flag>NoData</flag></response>";
            } else {
                xml = xml + "<flag>success</flag></response>";
            }
        } catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
            xml = xml + "<flag>failure</flag></response>";
        }

        System.out.println(xml);
        out.println(xml);
        out.close();
    }
}
