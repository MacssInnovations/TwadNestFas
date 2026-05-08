package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Date;

import java.sql.ResultSetMetaData;

import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.Calendar;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class HRM_LeaveExtensionServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String xml = "", name = "", designation = "";
        int did = 0, count = 0, tid = 0;
        Date fdate = null, tdate = null, rdate = null;
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {

            ResourceBundle rsb =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rsb.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rsb.getString("Config.DSN");
            String strhostname = rsb.getString("Config.HOST_NAME");
            String strportno = rsb.getString("Config.PORT_NUMBER");
            String strsid = rsb.getString("Config.SID");
            String strdbusername = rsb.getString("Config.USER_NAME");
            String strdbpassword = rsb.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
        }
        String cmd = request.getParameter("command");
        System.out.println(cmd);
        if (cmd.equalsIgnoreCase("changeid")) {
            int eid = Integer.parseInt(request.getParameter("cmdid"));
            xml = "<response1><command>changeid</command>";
            try {
                ps =
  con.prepareStatement("select * from(select EMPLOYEE_ID,EMPLOYEE_NAME from HRM_MST_EMPLOYEES)a inner join(select DESIGNATION_ID,EMPLOYEE_ID from HRM_EMP_CURRENT_POSTING)b on a.EMPLOYEE_ID=b.EMPLOYEE_ID left outer join(select DESIGNATION_ID,DESIGNATION from HRM_MST_DESIGNATIONS)c on b.DESIGNATION_ID=c.DESIGNATION_ID where a.EMPLOYEE_ID=?");
                System.out.println("hai");
                ps.setInt(1, eid);
                rs = ps.executeQuery();
                if (rs.next()) {
                    name = rs.getString("EMPLOYEE_NAME");
                    did = Integer.parseInt(rs.getString("DESIGNATION_ID"));
                    designation = rs.getString("DESIGNATION");
                    System.out.println(name);
                    System.out.println(did);
                    System.out.println(designation);
                    xml =
 xml + "<name>" + name + "</name><did>" + did + "</did>";
                    xml =
 xml + "<designation>" + designation + "</designation>";
                    xml = xml + "<flag1>Success</flag1>";
                } else {
                    xml = xml + "<flag1>error in try</flag1>";
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
                System.out.println("err in load");
            }

        } else if (cmd.equalsIgnoreCase("changerid")) {
            int rid = Integer.parseInt(request.getParameter("cmdrid"));
            System.out.println(rid);
            int eid = Integer.parseInt(request.getParameter("cmdid"));
            System.out.println("cmdid");
            xml = "<response1><command>changerid</command>";
            try {
                ps =
  con.prepareStatement("select * from(select LEAVE_TYPE_ID,EMPLOYEE_ID,REQUEST_SLNO,LEAVE_FROM_DATE,LEAVE_TO_DATE,NO_PREFIX_HOLIDAYS,NO_SUFFIX_HOLIDAYS,NO_LEAVE_DAYS_REQUESTED,ADDRESS_IN_LEAVE_PERIOD,LEAVE_REQUEST_DATE from HRM_LEAVE_REQUESTS)a inner join (select LEAVE_TYPE_ID,LEAVE_TYPE_DESC_SHORT from HRM_LEAVE_TYPES)b on b.LEAVE_TYPE_ID=a.LEAVE_TYPE_ID where a.EMPLOYEE_ID=? and a.REQUEST_SLNO=?");
                ps.setInt(1, eid);
                ps.setInt(2, rid);
                rs = ps.executeQuery();
                if (rs.next()) {
                    tid = Integer.parseInt(rs.getString("LEAVE_TYPE_ID"));
                    fdate = rs.getDate("LEAVE_FROM_DATE");
                    tdate = rs.getDate("LEAVE_TO_DATE");

                    Format formatter;
                    formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String f = formatter.format(fdate);
                    String t = formatter.format(tdate);
                    xml = xml + "<tid>" + tid + "</tid>";
                    xml =
 xml + "<rname>" + rs.getString("LEAVE_TYPE_DESC_SHORT") + "</rname>";
                    xml =
 xml + "<fdate>" + f + "</fdate><tdate>" + t + "</tdate>";
                    xml =
 xml + "<pday>" + Integer.parseInt(rs.getString("NO_PREFIX_HOLIDAYS")) +
   "</pday>";
                    xml =
 xml + "<sday>" + Integer.parseInt(rs.getString("NO_SUFFIX_HOLIDAYS")) +
   "</sday>";
                    xml =
 xml + "<tleave>" + Integer.parseInt(rs.getString("NO_LEAVE_DAYS_REQUESTED")) +
   "</tleave>";
                    xml =
 xml + "<address>" + rs.getString("ADDRESS_IN_LEAVE_PERIOD") + "</address>";
                    rdate = rs.getDate("LEAVE_REQUEST_DATE");

                    String r = formatter.format(rdate);
                    xml = xml + "<rdate>" + r + "</rdate>";
                    xml = xml + "<flag1>Success</flag1>";
                } else {
                    xml = xml + "<flag1>error in select</flag1>";
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            try {

                ps =
  con.prepareStatement("select CURRENT_BALANCE_NO_OF_DAYS from HRM_LEAVE_BAL_CURRENT where EMPLOYEE_ID=? and LEAVE_TYPE_ID=?");
                ps.setInt(1, eid);
                ps.setInt(2, tid);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml =
 xml + "<leave_balance>" + Integer.parseInt(rs.getString("CURRENT_BALANCE_NO_OF_DAYS")) +
   "</leave_balance>";
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("radio")) {
            int rid = Integer.parseInt(request.getParameter("cmdrid"));
            System.out.println(rid);
            int eid = Integer.parseInt(request.getParameter("cmdid"));
            System.out.println("cmdid");
            xml = "<response1><command>radio</command>";
            try {
                ps =
  con.prepareStatement("select LEAVE_TYPE_ID,LEAVE_FROM_DATE,LEAVE_TO_DATE,NO_PREFIX_HOLIDAYS,NO_SUFFIX_HOLIDAYS,NO_LEAVE_DAYS_REQUESTED,ADDRESS_IN_LEAVE_PERIOD,LEAVE_REQUEST_DATE from HRM_LEAVE_REQUESTS where EMPLOYEE_ID=? and REQUEST_SLNO=?");
                ps.setInt(1, eid);
                ps.setInt(2, rid);
                rs = ps.executeQuery();
                if (rs.next()) {
                    tid = Integer.parseInt(rs.getString("LEAVE_TYPE_ID"));
                    fdate = rs.getDate("LEAVE_FROM_DATE");
                    tdate = rs.getDate("LEAVE_TO_DATE");

                    Format formatter;
                    formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String f = formatter.format(fdate);
                    String t = formatter.format(tdate);
                    xml = xml + "<rno>" + tid + "</rno>";
                    xml =
 xml + "<fdate>" + f + "</fdate><tdate>" + t + "</tdate>";
                    xml =
 xml + "<pday>" + Integer.parseInt(rs.getString("NO_PREFIX_HOLIDAYS")) +
   "</pday>";
                    xml =
 xml + "<sday>" + Integer.parseInt(rs.getString("NO_SUFFIX_HOLIDAYS")) +
   "</sday>";
                    xml =
 xml + "<tleave>" + Integer.parseInt(rs.getString("NO_LEAVE_DAYS_REQUESTED")) +
   "</tleave>";
                    xml =
 xml + "<address>" + rs.getString("ADDRESS_IN_LEAVE_PERIOD") + "</address>";
                    rdate = rs.getDate("LEAVE_REQUEST_DATE");

                    String r = formatter.format(rdate);
                    xml = xml + "<rdate>" + r + "</rdate>";
                    xml = xml + "<rid>" + rid + "</rid>";
                    xml = xml + "<flag1>Success</flag1>";
                } else {
                    xml = xml + "<flag1>error in select</flag1>";
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            try {

                ps =
  con.prepareStatement("select CURRENT_BALANCE_NO_OF_DAYS from HRM_LEAVE_BAL_CURRENT where EMPLOYEE_ID=? and LEAVE_TYPE_ID=?");
                ps.setInt(1, eid);
                ps.setInt(2, tid);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml =
 xml + "<leave_balance>" + Integer.parseInt(rs.getString("CURRENT_BALANCE_NO_OF_DAYS")) +
   "</leave_balance>";
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } else {
            int id = Integer.parseInt(request.getParameter("empid"));
            System.out.println(id);
            xml = "<response1><command>selection</command>";
            try {
                ps =
  con.prepareStatement("select * from(select EMPLOYEE_ID,EMPLOYEE_NAME from HRM_MST_EMPLOYEES)a inner join(select EMPLOYEE_ID,REQUEST_SLNO,LEAVE_FROM_DATE,LEAVE_TO_DATE,LEAVE_TYPE_ID,LEAVE_REQUEST_DATE from HRM_LEAVE_REQUESTS)b on a.EMPLOYEE_ID=b.EMPLOYEE_ID left outer join(select LEAVE_TYPE_ID,LEAVE_TYPE_DESC_SHORT from HRM_LEAVE_TYPES)c on b.LEAVE_TYPE_ID=c.LEAVE_TYPE_ID where a.EMPLOYEE_ID=?");
                ps.setInt(1, id);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<count>";
                    xml =
 xml + "<EMPLOYEE_NAME>" + rs.getString("EMPLOYEE_NAME") + "</EMPLOYEE_NAME>";
                    xml =
 xml + "<SELNO>" + rs.getInt("REQUEST_SLNO") + "</SELNO>";
                    xml =
 xml + "<REQUEST_SLNO>" + rs.getInt("REQUEST_SLNO") + "</REQUEST_SLNO>";
                    xml =
 xml + "<LEAVE_FROM_DATE>" + rs.getDate("LEAVE_FROM_DATE") +
   "</LEAVE_FROM_DATE>";
                    xml =
 xml + "<LEAVE_TO_DATE>" + rs.getDate("LEAVE_TO_DATE") + "</LEAVE_TO_DATE>";
                    xml =
 xml + "<LEAVE_REQUEST_DATE>" + rs.getDate("LEAVE_REQUEST_DATE") +
   "</LEAVE_REQUEST_DATE>";
                    xml =
 xml + "<LEAVE_TYPE_DESC_SHORT>" + rs.getString("LEAVE_TYPE_DESC_SHORT") +
   "</LEAVE_TYPE_DESC_SHORT>";
                    xml = xml + "</count>";
                    count = count + 1;
                }
                xml = xml + "<count1>" + count + "</count1>";
                xml = xml + "<flag1>Success</flag1>";
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }

        xml = xml + "</response1>";
        out.println(xml);
        System.out.println(xml);
        System.out.println("end of the java");
        out.close();
    }
}


