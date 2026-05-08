package Servlets.HR.HR1.EmployeeMaster.Reports;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;


import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class DesigCadRankServ extends HttpServlet {


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
            ConnectionString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
        }
        ResultSet rs = null;
        PreparedStatement ps = null;
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        /*try
    {
           if(session==null)
            {
                   String xml="<response><command>sessionout</command><flag>sessionout</flag></response>";
                    out.println(xml);
                    System.out.println(xml);
                    out.close();
                    return;

                }
                System.out.println(session);

    }catch(Exception e)
    {
            System.out.println("Redirect Error :"+e);
    }
*/


        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");

        String xml = "";
        String strCommand = "";
        int sgroup = 0;
        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);
            sgroup = Integer.parseInt(request.getParameter("cmbsgroup"));
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }


        if (strCommand.equalsIgnoreCase("Desig")) {
            xml = "<response>";
            try {
                System.out.println("Desig" + strCommand);
                System.out.println("sgroup::" + sgroup);
                ps =
  con.prepareStatement("select DESIGNATION_ID,DESIGNATION from HRM_MST_DESIGNATIONS  where SERVICE_GROUP_ID=? order by DESIGNATION");
                ps.setInt(1, sgroup);
                rs = ps.executeQuery();
                int count = 0;
                xml = xml + "<flag>success</flag>";
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

            out.println(xml);
            System.out.println(xml);


        }

        else if (strCommand.equalsIgnoreCase("Rank")) {
            xml = "<response>";
            try {
                //int sgroup=Integer.parseInt(request.getParameter("cmbsgroup"));
                //System.out.println("sgroup::"+sgroup);
                ps =
  con.prepareStatement("select POST_RANK_ID,POST_RANK_NAME from HRM_MST_POST_RANKS  where SERVICE_GROUP_ID=? order by POST_RANK_NAME");
                ps.setInt(1, sgroup);
                rs = ps.executeQuery();
                int count = 0;
                xml = xml + "<flag>success</flag>";
                while (rs.next()) {

                    xml =
 xml + "<option><id>" + rs.getInt("POST_RANK_ID") + "</id><name>" +
   rs.getString("POST_RANK_NAME") + "</name></option>";
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

            out.println(xml);
            System.out.println(xml);


        }


        else if (strCommand.equalsIgnoreCase("Cadre")) {
            xml = "<response>";
            try {
                //int sgroup=Integer.parseInt(request.getParameter("cmbsgroup"));
                //System.out.println("sgroup::"+sgroup);
                ps =
  con.prepareStatement("select distinct a.service_group_id,b.cadre_id,b.cadre_name from" +
                       " (" +
                       " select service_group_id,cadre_id from hrm_mst_designations" +
                       " ) a" + " left outer join" + " (" +
                       " select cadre_id,cadre_name from hrm_mst_cadre" +
                       " ) b" + " on a.cadre_id=b.cadre_id" +
                       " where a.service_group_id=? order by b.cadre_name");

                ps.setInt(1, sgroup);
                rs = ps.executeQuery();
                int count = 0;
                xml = xml + "<flag>success</flag>";

                while (rs.next()) {

                    xml =
 xml + "<option><id>" + rs.getInt("cadre_id") + "</id><name>" +
   rs.getString("cadre_name") + "</name></option>";
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

            out.println(xml);
            System.out.println(xml);


        }


    }

}

