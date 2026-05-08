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

import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class MasterEntry extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";
    Connection con = null;
    Statement st;
    PreparedStatement ps;
    ResultSet rs;
    String cmd = null;
    String xml = null;
    int LabCode = 0, qty = 0, reorder = 0;
    String LabName = null, ChemCode = null, ChemName = null;
    String remarks, uom, ucode = null;
    Calendar c1;
    Date sdate = null;
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

        System.out.println("user id::" + empProfile.getEmployeeId());
        int user_id = empProfile.getEmployeeId();

        out.println("<html>");
        out.println("<head><title>Master Entry(Chemicals)</title></head>");
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
        System.out.println(cmd);
        LabCode = Integer.parseInt(request.getParameter("LabCode"));
        System.out.println(LabCode);
        ChemName = request.getParameter("ChemCode");
        String bcode = request.getParameter("bcode");
        try {
            String sss =
                "select * from wqs_mst_inv_chemical where che_specification=?";
            ps = con.prepareStatement(sss);
            ps.setString(1, ChemName);
            rs = ps.executeQuery();
            while (rs.next()) {
                ChemCode = rs.getString(1);
            }
        } catch (Exception e) {
            System.out.println(e);
        }
        System.out.println(ChemCode);
        if (cmd.equalsIgnoreCase("check")) {
            System.out.println("brand :=========>" + bcode);
            System.out.println(LabCode);
            xml = xml + "<command>check</command>";
            try {
                System.out.println("before sql");
                String sql =
                    "select * from(select LAB_CODE,CHEMICAL_CODE,QTY_AVAILABLE,REORDER_LEVEL,REMARKS,UOM_CODE,STOCK_AS_ON from WQS_CHEMICAL_MASTER where LAB_CODE=? and CHEMICAL_CODE=? and BRAND_CODE=?)a left outer join(select UOM_CODE,UOM_DESC from WQS_MST_UOM)b on a.UOM_CODE=b.UOM_CODE";
                System.out.println("after sql");
                ps = con.prepareStatement(sql);
                ps.setInt(1, LabCode);
                ps.setString(2, ChemCode);
                ps.setString(3, bcode);
                rs = ps.executeQuery();
                System.out.println(LabCode);
                while (rs.next()) {
                    xml = xml + "<available>";
                    xml =
 xml + "<qty>" + Integer.parseInt(rs.getString("QTY_AVAILABLE")) + "</qty>";
                    xml = xml + "<uom>" + rs.getString("UOM_CODE") + "</uom>";
                    xml =
 xml + "<udesc>" + rs.getString("UOM_DESC") + "</udesc>";
                    sdate = rs.getDate("STOCK_AS_ON");
                    System.out.println("date:" + sdate);

                    xml = xml + "<sdate>" + sdate + "</sdate>";
                    xml =
 xml + "<reorder>" + rs.getString("REORDER_LEVEL") + "</reorder>";
                    xml =
 xml + "<remarks>" + rs.getString("REMARKS") + "</remarks>";
                    xml = xml + "</available>";
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        if (cmd.equalsIgnoreCase("add")) {
            qty = Integer.parseInt(request.getParameter("qty"));
            uom = request.getParameter("uom");

            String[] od = request.getParameter("sdate").split("/");
            c1 =
  new GregorianCalendar(Integer.parseInt(od[2]), Integer.parseInt(od[1]) - 1,
                        Integer.parseInt(od[0]));
            java.util.Date d = c1.getTime();
            Date sdate = new Date(d.getTime());
            System.out.println(sdate);

            reorder = Integer.parseInt(request.getParameter("Reorder"));
            remarks = request.getParameter("remarks");

            System.out.println("add");
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
                    "insert into WQS_CHEMICAL_MASTER(LAB_CODE,CHEMICAL_CODE,BRAND_CODE,QTY_AVAILABLE,REORDER_LEVEL,REMARKS,UPDATE_BY_USER_ID,UPDATE_DATE,UOM_CODE,STOCK_AS_ON) values(?,?,?,?,?,?,?,?,?,?)";
                ps = con.prepareStatement(ss);
                ps.setInt(1, LabCode);
                ps.setString(2, ChemCode);
                ps.setString(3, bcode);
                ps.setInt(4, qty);
                ps.setInt(5, reorder);
                ps.setString(6, remarks);
                ps.setInt(7, user_id);
                ps.setTimestamp(8, ts);
                ps.setString(9, ucode);
                ps.setDate(10, sdate);

                ps.executeUpdate();
                xml = xml + "<added>";
                xml = xml + "<LabCode>" + LabCode + "</LabCode>";
                xml = xml + "<ChemCode>" + ChemCode + "</ChemCode>";
                xml = xml + "<BrandCode>" + bcode + "</BrandCode>";
                xml = xml + "</added>";
                xml = xml + "<flag>success</flag>";
            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";
            }

        } else if (cmd.equalsIgnoreCase("upd")) {
            System.out.println(LabCode);
            qty = Integer.parseInt(request.getParameter("qty"));
            uom = request.getParameter("uom");

            String[] od = request.getParameter("sdate").split("/");
            c1 =
  new GregorianCalendar(Integer.parseInt(od[2]), Integer.parseInt(od[1]) - 1,
                        Integer.parseInt(od[0]));
            java.util.Date d = c1.getTime();
            Date sd = new Date(d.getTime());
            System.out.println(sd);

            reorder = Integer.parseInt(request.getParameter("Reorder"));
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
                String ss =
                    "update WQS_CHEMICAL_MASTER set QTY_AVAILABLE=?,REORDER_LEVEL=?,REMARKS=?,UPDATE_BY_USER_ID=?,UPDATE_DATE=?,UOM_CODE=?,STOCK_AS_ON=? where LAB_CODE=? and CHEMICAL_CODE=? and BRAND_CODE=?";
                ps = con.prepareStatement(ss);
                ps.setInt(1, qty);
                ps.setInt(2, reorder);
                ps.setString(3, remarks);
                ps.setInt(4, user_id);
                ps.setTimestamp(5, ts);
                ps.setString(6, ucode);
                ps.setDate(7, sd);
                ps.setInt(8, LabCode);
                ps.setString(9, ChemCode);
                ps.setString(10, bcode);
                ps.executeUpdate();
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
                    "delete from WQS_CHEMICAL_MASTER where LAB_CODE=? and CHEMICAL_CODE=? and BRAND_CODE=?";
                ps = con.prepareStatement(ss);
                ps.setInt(1, LabCode);
                ps.setString(2, ChemCode);
                ps.setString(3, bcode);
                ps.executeUpdate();
                xml = xml + "<deleted>";
                xml = xml + "<LabCode>" + LabCode + "</LabCode>";
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
