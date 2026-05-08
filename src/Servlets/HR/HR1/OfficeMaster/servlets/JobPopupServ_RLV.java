package Servlets.HR.HR1.OfficeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class JobPopupServ_RLV extends HttpServlet {


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

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);

        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        try {
            String sql = "";

            if (strCommand.equalsIgnoreCase("OHOLevel")) {

                System.out.println("Command::" + strCommand);
                String cmbolevel = request.getParameter("cmbolevel");
                System.out.println("cmbolevel::" + cmbolevel);
                sql =
 "select  OFFICE_ID from COM_MST_OFFICES where OFFICE_LEVEL_ID = ? order by  OFFICE_ID";


                xml = "<response>";


                ps = con.prepareStatement(sql);
                ps.setString(1, cmbolevel);

                rs = ps.executeQuery();
                int count = 0;
                xml = xml + "<flag>success</flag>";
                if (rs.next()) {

                    xml = xml + "<id>" + rs.getInt("OFFICE_ID") + "</id>";
                    count++;
                }
                System.out.println("count::" + count);
                if (count == 0)
                    xml = "<response><flag>failure</flag>";


            }

            else if (strCommand.equalsIgnoreCase("OLevel")) {

                System.out.println("Command::" + strCommand);
                int offid = Integer.parseInt(request.getParameter("offid"));
                System.out.println("the office id is" + offid);
                sql =
 "select  REGION_OFFICE_ID,REGION_OFFICE_NAME from COM_MST_REGIONS_HVIEW";


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

            }

            else if (strCommand.equalsIgnoreCase("Audit")) {
                int cid = 0;
                System.out.println("cmbregion::" +
                                   request.getParameter("cmbreg"));
                cid = Integer.parseInt(request.getParameter("cmbreg"));
                System.out.println("Command::" + strCommand);
                sql =
 "select  AUDITWING_OFFICE_ID,AUDITWING_OFFICE_NAME from COM_MST_AUDIT_WING_HVIEW where REGION_OFFICE_ID=?";


                xml = "<response>";


                ps = con.prepareStatement(sql);
                ps.setInt(1, cid);
                rs = ps.executeQuery();
                int count = 0;
                xml = xml + "<flag>success</flag>";
                while (rs.next()) {

                    xml =
 xml + "<option><id>" + rs.getInt("AUDITWING_OFFICE_ID") + "</id><name>" +
   rs.getString("AUDITWING_OFFICE_NAME") + "</name></option>";
                    count++;
                }
                System.out.println("count::" + count);
                if (count == 0)
                    xml = "<response><flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";

            }

            else if (strCommand.equalsIgnoreCase("Division")) {
                int rid = 0, cid = 0;
                //System.out.println("cmbregion::"+request.getParameter("cmbcircle"));
                rid = Integer.parseInt(request.getParameter("cmbregion"));
                cid = Integer.parseInt(request.getParameter("cmbcircle"));
                System.out.println("Command::" + strCommand);
                sql =
 "select  DIVISION_OFFICE_ID,DIVISION_OFFICE_NAME from COM_MST_DIVISIONS_HVIEW where REGION_OFFICE_ID=? and CIRCLE_OFFICE_ID=?";


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
   rs.getString("DIVISION_OFFICE_NAME") + "</name></option>";
                    count++;
                }
                System.out.println("count::" + count);
                if (count == 0)
                    xml = "<response><flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";

            }

            else if (strCommand.equalsIgnoreCase("Lab")) {
                int rid = 0, cid = 0;
                //System.out.println("cmbregion::"+request.getParameter("cmbcircle"));
                rid = Integer.parseInt(request.getParameter("cmbregion"));
                cid = Integer.parseInt(request.getParameter("cmbcircle"));
                System.out.println("Command::" + strCommand);
                sql =
 "select  LAB_OFFICE_ID,LAB_OFFICE_NAME from COM_MST_LABS_HVIEW where REGION_OFFICE_ID=? and CIRCLE_OFFICE_ID=?";


                xml = "<response>";


                ps = con.prepareStatement(sql);
                ps.setInt(1, rid);
                ps.setInt(2, cid);
                rs = ps.executeQuery();
                int count = 0;
                xml = xml + "<flag>success</flag>";
                while (rs.next()) {

                    xml =
 xml + "<option><id>" + rs.getInt("LAB_OFFICE_ID") + "</id><name>" +
   rs.getString("LAB_OFFICE_NAME") + "</name></option>";
                    count++;
                }
                System.out.println("count::" + count);
                if (count == 0)
                    xml = "<response><flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";

            }

            else if (strCommand.equalsIgnoreCase("SubDivision")) {
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

            else if (strCommand.equalsIgnoreCase("CityOffice")) {
                String strcity = request.getParameter("txtCity").toUpperCase();
                System.out.println("Command::" + strCommand);
                sql =
 "select  OFFICE_ID,OFFICE_NAME from COM_MST_OFFICES where upper(CITY_TOWN_NAME) like ? order by OFFICE_NAME";
                xml = "<response>";
                ps = con.prepareStatement(sql);
                ps.setString(1, "%" + strcity + "%");
                rs = ps.executeQuery();
                int count = 0;
                xml = xml + "<flag>success</flag>";
                while (rs.next()) {

                    xml =
 xml + "<option><id>" + rs.getInt("OFFICE_ID") + "</id><name>" +
   rs.getString("OFFICE_NAME") + "</name></option>";
                    count++;
                }
                System.out.println("count::" + count);
                if (count == 0)
                    xml = "<response><flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";

            }

            else if (strCommand.equalsIgnoreCase("OtherOffice")) {
                String strod = request.getParameter("cmbOName");
                System.out.println("Command::" + strCommand);
                System.out.println("hai");
                sql =
 "select  OTHER_DEPT_OFFICE_ID,OTHER_DEPT_OFFICE_NAME from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_ID = ?  order by OTHER_DEPT_OFFICE_NAME";
                xml = "<response>";
                ps = con.prepareStatement(sql);
                ps.setString(1, strod);
                rs = ps.executeQuery();
                int count = 0;
                xml = xml + "<flag>success</flag>";
                while (rs.next()) {

                    xml =
 xml + "<option><id>" + rs.getInt("OTHER_DEPT_OFFICE_ID") + "</id><name>" +
   rs.getString("OTHER_DEPT_OFFICE_NAME") + "</name></option>";
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
