package Servlets.WQS.WQS1.Inventory.Masters.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class StockEntryGlass extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";
    Connection con;
    Statement st;
    PreparedStatement ps;
    ResultSet rs;
    String cmd;
    String xml;
    int LabCode;
    String GlassName;
    String GlassCode;
    String remarks, uom = null, ucode = null;
    Date sdate;
    int nos;
    int dd, mm, yy;
    Calendar c1;
    //int exists=0;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
        HttpSession session = request.getSession(false);
        UserProfile empProfile =
            (UserProfile)session.getAttribute("UserProfile");

        System.out.println("user LabCode::" + empProfile.getEmployeeId());
        int user_id = empProfile.getEmployeeId();

        out.println("<html>");
        out.println("<head><title>customerType</title></head>");
        out.println("<body>");
        try {
            ResourceBundle rb =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rb.getString("Config.DSN");
            String strhostname = rb.getString("Config.HOST_NAME");
            String strportno = rb.getString("Config.PORT_NUMBER");
            String strsid = rb.getString("Config.SID");
            String strdbusername = rb.getString("Config.USER_NAME");
            String strdbpassword = rb.getString("Config.PASSWORD");

            ConnectionString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
            System.out.println("connected");
            session = request.getSession(false);
        } catch (Exception e) {
            System.out.println(e);
        }
        xml = "<response>";
        cmd = request.getParameter("command");
        LabCode = Integer.parseInt(request.getParameter("LabCode"));
        System.out.println(LabCode);
        GlassName = request.getParameter("GlassCode");

        try {
            String sss =
                "select * from wqs_mst_inv_glassware where GLASSWARE_SPEC=?";
            ps = con.prepareStatement(sss);
            ps.setString(1, GlassName);
            rs = ps.executeQuery();
            while (rs.next()) {
                GlassCode = rs.getString(1);
                System.out.println("category:" + GlassCode);
            }
        } catch (Exception e) {
            System.out.println(e);
        }

        if (cmd.equalsIgnoreCase("getNos")) {
            xml = xml + "<command>getNos</command>";
            try {
                String sql =
                    "select * from(select LAB_CODE,GLASSWARE_CODE,NO_AVAILABLE,REMARKS,UOM_CODE,STOCK_AS_ON from WQS_GLASSWARE_MASTER where LAB_CODE=? and GLASSWARE_CODE=?)a left outer join(select UOM_CODE,UOM_DESC from WQS_MST_UOM)b on a.UOM_CODE=b.UOM_CODE";
                ps = con.prepareStatement(sql);
                ps.setInt(1, LabCode);
                ps.setString(2, GlassCode);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<available>";
                    xml =
 xml + "<LabCode>" + Integer.parseInt(rs.getString("LAB_CODE")) + "</LabCode>";
                    xml =
 xml + "<GlassCode>" + rs.getString("GLASSWARE_CODE") + "</GlassCode>";
                    xml =
 xml + "<Numbers>" + Integer.parseInt(rs.getString("NO_AVAILABLE")) +
   "</Numbers>";
                    xml = xml + "<uom>" + rs.getString("UOM_CODE") + "</uom>";
                    xml =
 xml + "<udesc>" + rs.getString("UOM_DESC") + "</udesc>";
                    sdate = rs.getDate("STOCK_AS_ON");
                    System.out.println("date:" + sdate);
                    xml = xml + "<sdate>" + sdate + "</sdate>";
                    xml =
 xml + "<Remarks>" + rs.getString("REMARKS") + "</Remarks>";
                    xml = xml + "</available>";
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }

        if (cmd.equalsIgnoreCase("add")) {
            nos = Integer.parseInt(request.getParameter("nos"));
            uom = request.getParameter("uom");
            String[] od = request.getParameter("sdate").split("/");
            c1 =
  new GregorianCalendar(Integer.parseInt(od[2]), Integer.parseInt(od[1]) - 1,
                        Integer.parseInt(od[0]));
            java.util.Date d = c1.getTime();
            Date sdate = new Date(d.getTime());
            System.out.println(sdate);

            remarks = request.getParameter("remarks");
            System.out.println(LabCode);
            xml = xml + "<command>add</command>";
            try {
                PreparedStatement ps1 =
                    con.prepareStatement("select UOM_CODE from WQS_MST_UOM where UOM_DESC=?");
                ps1.setString(1, uom);
                rs = ps1.executeQuery();
                while (rs.next()) {
                    ucode = rs.getString("UOM_CODE");
                }

                String ss =
                    "insert into WQS_GLASSWARE_MASTER(LAB_CODE,GLASSWARE_CODE,NO_AVAILABLE,REMARKS,UPDATE_BY_USER_ID,UPDATE_DATE,UOM_CODE,STOCK_AS_ON) values(?,?,?,?,?,?,?,?)";
                ps = con.prepareStatement(ss);
                ps.setInt(1, LabCode);
                ps.setString(2, GlassCode);
                ps.setInt(3, nos);
                ps.setString(4, remarks);
                ps.setInt(5, user_id);
                ps.setTimestamp(6, ts);
                ps.setString(7, ucode);
                ps.setDate(8, sdate);
                ps.executeUpdate();
                xml = xml + "<added>";
                xml = xml + "<LabCode>" + LabCode + "</LabCode>";
                xml = xml + "<GlassCode>" + GlassCode + "</GlassCode>";
                xml = xml + "<Numbers>" + ucode + "</Numbers>";
                xml = xml + "<Remarks>" + remarks + "</Remarks>";
                xml = xml + "</added>";
                xml = xml + "<flag>success</flag>";
            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";
            }

        }


        else if (cmd.equalsIgnoreCase("upd")) {
            System.out.println("update");
            nos = Integer.parseInt(request.getParameter("nos"));
            uom = request.getParameter("uom");
            System.out.println(nos + " " + uom);
            String[] od = request.getParameter("sdate").split("/");
            c1 =
  new GregorianCalendar(Integer.parseInt(od[2]), Integer.parseInt(od[1]) - 1,
                        Integer.parseInt(od[0]));
            java.util.Date d = c1.getTime();
            Date sd = new Date(d.getTime());
            System.out.println(sd);

            System.out.println(LabCode);
            remarks = request.getParameter("remarks");
            xml = xml + "<command>upd</command>";
            try {
                PreparedStatement ps1 =
                    con.prepareStatement("select UOM_CODE from WQS_MST_UOM where UOM_DESC=?");
                ps1.setString(1, uom);
                rs = ps1.executeQuery();
                while (rs.next()) {
                    ucode = rs.getString("UOM_CODE");
                }
                String sql =
                    "select * from WQS_GLASSWARE_MASTER where LAB_CODE=? and GLASSWARE_CODE=?";
                st = con.createStatement();

                String ss =
                    "update WQS_GLASSWARE_MASTER set NO_AVAILABLE=?,REMARKS=?,UPDATE_BY_USER_ID=?,UPDATE_DATE=?,UOM_CODE=?,STOCK_AS_ON=? where LAB_CODE=? and GLASSWARE_CODE=?";
                ps = con.prepareStatement(ss);
                ps.setInt(1, nos);
                ps.setString(2, remarks);
                ps.setInt(3, user_id);
                ps.setTimestamp(4, ts);
                ps.setString(5, ucode);
                ps.setDate(6, sd);
                ps.setInt(7, LabCode);
                ps.setString(8, GlassCode);
                ps.executeUpdate();


                xml = xml + "<updated>";
                xml = xml + "<LabCode>" + LabCode + "</LabCode>";
                xml = xml + "<GlassCode>" + GlassCode + "</GlassCode>";
                xml = xml + "<Numbers>" + nos + "</Numbers>";
                xml = xml + "<Remarks>" + remarks + "</Remarks>";
                xml = xml + "</updated>";

                xml = xml + "<flag>success</flag>";
            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";
            }

        }

        else if (cmd.equalsIgnoreCase("del")) {
            System.out.println(LabCode);
            xml = xml + "<command>del</command>";
            try {
                String ss =
                    "delete from WQS_GLASSWARE_MASTER where LAB_CODE=? and GLASSWARE_CODE=?";
                ps = con.prepareStatement(ss);
                ps.setInt(1, LabCode);
                ps.setString(2, GlassCode);
                ps.executeUpdate();
                xml = xml + "<deleted>";
                xml = xml + "<LabCode>" + LabCode + "</LabCode>";
                xml = xml + "<GlassCode>" + GlassCode + "</GlassCode>";
                xml = xml + "</deleted>";

                xml = xml + "<flag>success</flag>";
            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";
            }

        }
        xml = xml + "</response>";
        System.out.println(xml);
        out.println(xml);

        out.println("</body></html>");
        out.close();
    }
}
