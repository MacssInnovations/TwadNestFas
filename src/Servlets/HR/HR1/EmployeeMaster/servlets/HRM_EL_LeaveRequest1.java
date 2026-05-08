package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Calendar;
import java.util.GregorianCalendar;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class HRM_EL_LeaveRequest1 extends HttpServlet {
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
        int did = 0, offid = 0, typeid = 0, count = 0, c1 = 0;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        Calendar c;
        Date df = null, dt = null, da = null;
        String xml = null;

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
        System.out.println("welcomeserv");
        int eid = Integer.parseInt(request.getParameter("cmdid"));
        int balance = Integer.parseInt(request.getParameter("bal"));
        int yr = Integer.parseInt(request.getParameter("year"));
        //int rslno=Integer.parseInt(request.getParameter("slno"));
        String type1 = request.getParameter("ltype");
        String mc1 = request.getParameter("mc");
        System.out.println(mc1);

        String[] sd = request.getParameter("dateFrom").split("/");
        c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
        java.util.Date d = c.getTime();
        df = new Date(d.getTime());
        sd = request.getParameter("dateTo").split("/");
        c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
        d = c.getTime();
        dt = new Date(d.getTime());

        int prefixDay = Integer.parseInt(request.getParameter("ph"));
        int suffixDay = Integer.parseInt(request.getParameter("sh"));
        int ldays = Integer.parseInt(request.getParameter("leaveDays"));
        String add = request.getParameter("address");

        sd = request.getParameter("dateApplied").split("/");
        c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
        d = c.getTime();
        da = new Date(d.getTime());

        String purpose1 = request.getParameter("purpose");
        System.out.println(purpose1);
        int bal = balance - ldays;
        xml = "<response1><command>insertValue</command>";
        try {
            ps =
  con.prepareStatement("select OFFICE_ID,DESIGNATION_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
            ps.setInt(1, eid);
            rs = ps.executeQuery();
            while (rs.next()) {
                did = Integer.parseInt(rs.getString("OFFICE_ID"));
                offid = Integer.parseInt(rs.getString("DESIGNATION_ID"));
                System.out.println(did);
                System.out.println(offid);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            ps =
  con.prepareStatement("select LEAVE_TYPE_ID from HRM_LEAVE_TYPES where LEAVE_TYPE_DESC_SHORT=?");
            ps.setString(1, type1);
            rs = ps.executeQuery();
            while (rs.next()) {
                typeid = Integer.parseInt(rs.getString("LEAVE_TYPE_ID"));
                System.out.println(typeid);
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            ps =
  con.prepareStatement("select EMPLOYEE_ID from HRM_LEAVE_REQUESTS where EMPLOYEE_ID=?");
            ps.setInt(1, eid);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = count + 1;

            }
            c1 = count + 1;
            System.out.println(c1);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println("insert");
            ps =
  con.prepareStatement("insert into HRM_LEAVE_REQUESTS(EMPLOYEE_ID,REQUEST_YEAR,REQUEST_SLNO,DESIGNATION_ID,OFFICE_ID,LEAVE_TYPE_ID,LEAVE_FROM_DATE,LEAVE_TO_DATE,NO_PREFIX_HOLIDAYS,NO_SUFFIX_HOLIDAYS,NO_LEAVE_DAYS_REQUESTED,ADDRESS_IN_LEAVE_PERIOD,LEAVE_PURPOSE,MC_ATTACHED,LEAVE_REQUEST_DATE)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
            System.out.println("data");
            ps.setInt(1, eid);
            ps.setInt(2, yr);
            ps.setInt(3, c1);
            ps.setInt(4, did);
            ps.setInt(5, offid);
            ps.setInt(6, typeid);
            ps.setDate(7, df);
            ps.setDate(8, dt);
            ps.setInt(9, prefixDay);
            ps.setInt(10, suffixDay);
            ps.setInt(11, ldays);
            ps.setString(12, add);
            ps.setString(13, purpose1);
            ps.setString(14, mc1);
            ps.setDate(15, da);
            ps.executeUpdate();
            xml = xml + "<flag1>Success</flag1>";
            xml = xml + "<rid>" + c1 + "</rid>";
            try {
                ps =
  con.prepareStatement("Update HRM_LEAVE_BAL_CURRENT set CURRENT_BALANCE_NO_OF_DAYS=? where EMPLOYEE_ID=? and LEAVE_TYPE_ID=?");
                ps.setInt(1, bal);
                ps.setInt(2, eid);
                ps.setInt(3, typeid);
                ps.executeUpdate();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }

        xml = xml + "</response1>";
        System.out.println(xml);
        out.println(xml);
        out.close();

    }
}

