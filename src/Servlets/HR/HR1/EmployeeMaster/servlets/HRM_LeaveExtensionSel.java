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

public class HRM_LeaveExtensionSel extends HttpServlet {
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
        Calendar c;
        Date df = null, dt = null, da = null;
        int b = 0, count = 0, cou = 0;
        String xml = "";
        Connection con = null;
        Statement stmt = null;
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
        int eid = Integer.parseInt(request.getParameter("id"));
        int bal = Integer.parseInt(request.getParameter("balance"));
        int year = Integer.parseInt(request.getParameter("year"));
        int rno = Integer.parseInt(request.getParameter("rslno"));
        int tid = Integer.parseInt(request.getParameter("typeid"));
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

        int sh = Integer.parseInt(request.getParameter("suffix"));
        int ldays = Integer.parseInt(request.getParameter("leaveDays"));
        String rsn = request.getParameter("reason");
        String mc = request.getParameter("mc");
        System.out.println(ldays);

        sd = request.getParameter("dateApplied").split("/");
        c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
        d = c.getTime();
        da = new Date(d.getTime());
        xml = "<response1><command>insertion</command>";
        try {
            ps =
  con.prepareStatement("Select EMPLOYEE_ID from HRM_LEAVE_EXTN_REQUESTS Where EMPLOYEE_ID=?");
            ps.setInt(1, eid);
            rs = ps.executeQuery();
            while (rs.next()) {
                count = count + 1;
            }
            cou = count + 1;
            System.out.println(cou);
        } catch (SQLException e) {
            System.out.println(e.getMessage());
        }
        try {
            System.out.println(cou);
            ps =
  con.prepareStatement("Insert into HRM_LEAVE_EXTN_REQUESTS(EMPLOYEE_ID,REQUEST_YEAR,REQUEST_SLNO,EXTENSION_SLNO,EXTENSION_LEAVE_TYPE_ID,EXTENSION_FROM_DATE,EXTENSION_TO_DATE,NO_SUFFIX_HOLIDAYS,NO_LEAVE_DAYS_EXTENDED,EXTENSION_REASON,MC_ATTACHED,EXTENSION_REQUEST_DATE)values(?,?,?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, eid);
            ps.setInt(2, year);
            ps.setInt(3, rno);
            ps.setInt(4, cou);
            ps.setInt(5, tid);
            ps.setDate(6, df);
            ps.setDate(7, dt);
            ps.setInt(8, sh);
            ps.setInt(9, ldays);
            ps.setString(10, rsn);
            ps.setString(11, mc);
            ps.setDate(12, da);
            ps.executeUpdate();
            xml = xml + "<flag>Success</flag>";
            b = bal - ldays;
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
        xml =
 xml + "<eid>" + eid + "</eid><year>" + year + "</year><rno>" + rno +
   "</rno><tid>" + tid + "</tid><df>" + df + "</df><dt>" + dt + "</dt><sh>" +
   sh + "</sh><ldays>" + ldays + "</ldays><rsn>" + rsn + "</rsn><mc>" + mc +
   "</mc><da>" + da + "</da>";
        xml = xml + "<bal>" + bal + "</bal>";
        xml = xml + "</response1>";
        System.out.println(xml);
        out.println(xml);
        out.close();
    }
}
