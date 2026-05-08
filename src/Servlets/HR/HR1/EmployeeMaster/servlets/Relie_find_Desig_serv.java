package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Relie_find_Desig_serv extends HttpServlet {


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection con = null;
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
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
        }
        ResultSet rs = null;
        PreparedStatement ps = null;

        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String xml = "";
        String strCommand = "";
        int sgroup = 0;
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

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);
            sgroup = Integer.parseInt(request.getParameter("cmbsgroup"));
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }


        if (strCommand.equalsIgnoreCase("SGroup")) {
            xml = "<response>";
            try {
                System.out.println("sgroup::" + sgroup);
                ps =
  con.prepareStatement("select DESIGNATION_ID,DESIGNATION from HRM_MST_DESIGNATIONS  where SERVICE_GROUP_ID=?");
                ps.setInt(1, sgroup);
                rs = ps.executeQuery();
                int count = 0;
                while (rs.next()) {

                    xml =
 xml + "<option><id>" + rs.getInt("DESIGNATION_ID") + "</id><name>" +
   rs.getString("DESIGNATION") + "</name></option>";
                    count++;
                }
                System.out.println("count::" + count);

                if (count == 0)
                    xml = "<response><flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";

            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = "<response><flag>failure</flag>";
            }

            xml = xml + "</response>";
        }

        out.println(xml);
        System.out.println(xml);
    }

}
