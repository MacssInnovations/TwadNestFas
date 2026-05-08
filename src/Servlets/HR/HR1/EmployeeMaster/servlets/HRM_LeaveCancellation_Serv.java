package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class HRM_LeaveCancellation_Serv extends HttpServlet {
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
        int did = 0, count = 0, tid = 0, year = 0;
        Date fdate = null, tdate = null, rdate = null;
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        Calendar c;
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
        } else {
            System.out.println("inside insert");
            int eid = Integer.parseInt(request.getParameter("id"));
            int bal = Integer.parseInt(request.getParameter("balance"));
            int rno = Integer.parseInt(request.getParameter("rslno"));
            String[] sd = request.getParameter("dateFrom").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            fdate = new Date(d.getTime());

            sd = request.getParameter("dateTo").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            d = c.getTime();
            tdate = new Date(d.getTime());

            int ldays = Integer.parseInt(request.getParameter("leaveDays"));
            String rsn = request.getParameter("reason");

            sd = request.getParameter("dateApplied").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            d = c.getTime();
            rdate = new Date(d.getTime());

            xml = "<response1><command>insertion</command>";
            try {
                ps =
  con.prepareStatement("Select REQUEST_YEAR,LEAVE_TYPE_ID from HRM_LEAVE_REQUESTS Where EMPLOYEE_ID=? and REQUEST_SLNO=?");
                ps.setInt(1, eid);
                ps.setInt(2, rno);
                rs = ps.executeQuery();
                if (rs.next()) {
                    year = Integer.parseInt(rs.getString("REQUEST_YEAR"));
                    tid = Integer.parseInt(rs.getString("LEAVE_TYPE_ID"));
                }

            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
            try {
                ps =
  con.prepareStatement("Insert into HRM_LEAVE_CANCEL_REQUESTS(EMPLOYEE_ID,REQUEST_YEAR,REQUEST_SLNO,CANCEL_REQUEST_DATE,CANCEL_FROM_DATE,CANCEL_TO_DATE,CANCEL_LEAVE_DAYS,CANCEL_REASON)values(?,?,?,?,?,?,?,?)");
                System.out.println("fdgfdg");
                ps.setInt(1, eid);
                ps.setInt(2, year);
                ps.setInt(3, rno);
                ps.setDate(4, rdate);
                ps.setDate(5, fdate);
                ps.setDate(6, tdate);
                ps.setInt(7, ldays);
                ps.setString(8, rsn);
                ps.executeUpdate();
                xml = xml + "<flag>Success</flag>";
                int b = bal + ldays;
                System.out.println(b);
                try {

                    ps =
  con.prepareStatement("update HRM_LEAVE_BAL_CURRENT set CURRENT_BALANCE_NO_OF_DAYS=? where EMPLOYEE_ID=? and LEAVE_TYPE_ID=?");
                    ps.setInt(1, b);
                    ps.setInt(2, eid);
                    ps.setInt(3, tid);
                    System.out.println("Inside query");
                    ps.executeUpdate();

                } catch (SQLException e) {
                    System.out.println(e.getMessage());
                }
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
