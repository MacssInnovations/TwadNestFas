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

public class JobPopupServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    private Connection con = null;
    private ResultSet rs = null;
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

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
        }
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String xml = "";
        String strCommand = "";

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);

        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        try {
            String sql = "";

            if (strCommand.equalsIgnoreCase("OLevel")) {

                System.out.println("Command::" + strCommand);
                sql =
 "select  REGION_OFFICE_ID,REGION_OFFICE_NAME from COM_MST_REGIONS_HVIEW ";


                xml = "<response>";


                ps = con.prepareStatement(sql);

                rs = ps.executeQuery();
                int count = 0;
                xml = xml + "<flag>success</flag>";
                while (rs.next()) {

                    xml =
 xml + "<option><id>" + rs.getInt("REGION_OFFICE_ID") + "</id><name>" +
   rs.getString("REGION_OFFICE_NAME") + "</name></option>";
                    count++;
                }
                System.out.println("count::" + count);
                if (count == 0)
                    xml = "<response><flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";

            } else if (strCommand.equalsIgnoreCase("Region")) {
                int cid = 0;
                System.out.println("cmbregion::" +
                                   request.getParameter("cmbregion"));
                cid = Integer.parseInt(request.getParameter("cmbregion"));
                System.out.println("Command::" + strCommand);
                sql =
 "select  CIRCLE_OFFICE_ID,CIRCLE_OFFICE_NAME from COM_MST_CIRCLES_HVIEW where REGION_OFFICE_ID=?";


                xml = "<response>";


                ps = con.prepareStatement(sql);
                ps.setInt(1, cid);
                rs = ps.executeQuery();
                int count = 0;
                xml = xml + "<flag>success</flag>";
                while (rs.next()) {

                    xml =
 xml + "<option><id>" + rs.getInt("CIRCLE_OFFICE_ID") + "</id><name>" +
   rs.getString("CIRCLE_OFFICE_NAME") + "</name></option>";
                    count++;
                }
                System.out.println("count::" + count);
                if (count == 0)
                    xml = "<response><flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";

            } else if (strCommand.equalsIgnoreCase("Division")) {
                int rid = 0, cid = 0;
                //System.out.println("cmbregion::"+request.getParameter("cmbcircle"));
                rid = Integer.parseInt(request.getParameter("cmbregion"));
                cid = Integer.parseInt(request.getParameter("cmbcircle"));
                System.out.println("Command::" + strCommand);
                sql =
 "select  DIVISION_OFFICE_ID,DIVISION_OOFICE_NAME from COM_MST_DIVISIONS_HVIEW where REGION_OFFICE_ID=? and CIRCLE_OFFICE_ID=?";


                xml = "<response>";


                ps = con.prepareStatement(sql);
                ps.setInt(1, rid);
                ps.setInt(2, cid);
                rs = ps.executeQuery();
                int count = 0;
                xml = xml + "<flag>success</flag>";
                while (rs.next()) {

                    xml =
 xml + "<option><id>" + rs.getInt("DIVISION_OFFICE_ID") + "</id><name>" +
   rs.getString("DIVISION_OOFICE_NAME") + "</name></option>";
                    count++;
                }
                System.out.println("count::" + count);
                if (count == 0)
                    xml = "<response><flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";

            } else if (strCommand.equalsIgnoreCase("SubDivision")) {
                int rid = 0, cid = 0, did = 0;
                //System.out.println("cmbregion::"+request.getParameter("cmbcircle"));
                rid = Integer.parseInt(request.getParameter("cmbregion"));
                cid = Integer.parseInt(request.getParameter("cmbcircle"));
                did = Integer.parseInt(request.getParameter("cmbdivision"));
                System.out.println("Command::" + strCommand);
                sql =
 "select  SUBDIVISION_OFFICE_ID,SUBDIVISION_OFFICE_NAME from COM_MST_SUBDIVISIONS_HVIEW where REGION_OFFICE_ID=? and CIRCLE_OFFICE_ID=? and DIVISION_OFFICE_ID=?";


                xml = "<response>";


                ps = con.prepareStatement(sql);
                ps.setInt(1, rid);
                ps.setInt(2, cid);
                ps.setInt(3, did);
                rs = ps.executeQuery();
                int count = 0;
                xml = xml + "<flag>success</flag>";
                while (rs.next()) {

                    xml =
 xml + "<option><id>" + rs.getInt("SUBDIVISION_OFFICE_ID") + "</id><name>" +
   rs.getString("SUBDIVISION_OFFICE_NAME") + "</name></option>";
                    count++;
                }
                System.out.println("count::" + count);
                if (count == 0)
                    xml = "<response><flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";

            } else if (strCommand.equalsIgnoreCase("Section")) {
                int rid = 0, cid = 0, did = 0, sec = 0;
                //System.out.println("cmbregion::"+request.getParameter("cmbcircle"));
                rid = Integer.parseInt(request.getParameter("cmbregion"));
                cid = Integer.parseInt(request.getParameter("cmbcircle"));
                did = Integer.parseInt(request.getParameter("cmbdivision"));
                sec = Integer.parseInt(request.getParameter("cmbsubdiv"));
                System.out.println("Command::" + strCommand);
                sql =
 "select  SECTION_OFFICE_ID,SECTION_OFFICE_NAME from COM_MST_SECTIONS_HVIEW where REGION_OFFICE_ID=? and CIRCLE_OFFICE_ID=? and DIVISION_OFFICE_ID=? and SUBDIVISION_OFFICE_ID=?";


                xml = "<response>";


                ps = con.prepareStatement(sql);
                ps.setInt(1, rid);
                ps.setInt(2, cid);
                ps.setInt(3, did);
                rs = ps.executeQuery();
                int count = 0;
                xml = xml + "<flag>success</flag>";
                while (rs.next()) {

                    xml =
 xml + "<option><id>" + rs.getInt("SECTION_OFFICE_ID") + "</id><name>" +
   rs.getString("SECTION_OFFICE_NAME") + "</name></option>";
                    count++;
                }
                System.out.println("count::" + count);
                if (count == 0)
                    xml = "<response><flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";

            }


        } catch (Exception e) {

            System.out.println("catch........" + e);
            xml = "<response><flag>failure</flag>";
        }

        xml = xml + "</response>";


        out.println(xml);
        System.out.println(xml);

    }


}
