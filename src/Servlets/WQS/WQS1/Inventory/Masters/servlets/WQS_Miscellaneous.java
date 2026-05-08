package Servlets.WQS.WQS1.Inventory.Masters.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Timestamp;

import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class WQS_Miscellaneous extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        String xml = null;
        Calendar c1;
        System.out.println("welcome");

        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
        HttpSession session = request.getSession(false);
        UserProfile empProfile =
            (UserProfile)session.getAttribute("UserProfile");

        System.out.println("user LabCode::" + empProfile.getEmployeeId());
        int user_id = empProfile.getEmployeeId();


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
        try {
            con.setAutoCommit(true);
        } catch (Exception e) {
            System.out.println("Err in commit:" + e.getMessage());
        }

        String cmd = request.getParameter("command");
        System.out.println(cmd);
        String miscode = request.getParameter("MisCode");
        String misdesc = request.getParameter("MisDesc");
        xml = "<response>";
        if (cmd.equalsIgnoreCase("Add")) {

            xml = xml + "<command>Add</command>";
            try {
                PreparedStatement pstmt =
                    con.prepareStatement("insert into WQS_MST_INV_MISCELLANEOUS(MISCELLANEOUS_CODE,MISCELLANEOUS_SPEC,UPDATED_DATE,UPDATED_BY_USER_ID) values(?,?,?,?)");
                System.out.println("add");
                pstmt.setString(1, miscode);
                pstmt.setString(2, misdesc);
                pstmt.setTimestamp(3, ts);
                pstmt.setInt(4, user_id);

                pstmt.executeUpdate();
                con.commit();
                xml = xml + "<flag>Success</flag>";

                xml = xml + "<MisCode>" + miscode + "</MisCode>";
                xml = xml + "<MisDesc>" + misdesc + "</MisDesc>";
            } catch (Exception e) {
                System.out.println("Err in addition:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("Get")) {
            xml = xml + "<command>Get</command>";
            try {
                stmt = con.createStatement();
                rs =
  stmt.executeQuery("select MISCELLANEOUS_CODE,MISCELLANEOUS_SPEC from WQS_MST_INV_MISCELLANEOUS order by MISCELLANEOUS_CODE");
                try {
                    while (rs.next()) {
                        xml = xml + "<count>";
                        xml =
 xml + "<MisCode>" + rs.getString("MISCELLANEOUS_CODE") + "</MisCode>";
                        xml =
 xml + "<MisDesc>" + rs.getString("MISCELLANEOUS_SPEC") + "</MisDesc>";
                        xml = xml + "</count>";
                    }
                    xml = xml + "<flag>Success</flag>";
                } catch (Exception e) {
                    System.out.println("Err in getting values:" +
                                       e.getMessage());
                    xml = xml + "<flag>Failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("Err in getting values2:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";
            try {
                ps =
  con.prepareStatement("select * from WQS_MST_INV_SUP_ITEM where ITEM_CODE=? and MAJOR_CATEGORY_CODE in('MIS')");
                ps.setString(1, miscode);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml = xml + "<flag>FoundData</flag>";
                } else {

                    PreparedStatement pstmt =
                        con.prepareStatement("delete from WQS_MST_INV_MISCELLANEOUS where MISCELLANEOUS_CODE=?");
                    System.out.println(pstmt);
                    pstmt.setString(1, miscode);
                    pstmt.executeUpdate();
                    con.commit();
                    xml = xml + "<flag>Success</flag>";
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }
        } else if (cmd.equalsIgnoreCase("Update")) {
            xml = "<response><command>Update</command>";
            try {
                PreparedStatement pstmt =
                    con.prepareStatement("update WQS_MST_INV_MISCELLANEOUS set MISCELLANEOUS_SPEC=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? where MISCELLANEOUS_CODE=?");

                pstmt.setString(1, misdesc);
                pstmt.setTimestamp(2, ts);
                pstmt.setInt(3, user_id);
                pstmt.setString(4, miscode);
                pstmt.executeUpdate();
                con.commit();
                xml = xml + "<flag>Success</flag>";
                pstmt.close();
            } catch (SQLException e) {
                System.out.println("Exception is in Get:" + e);
                xml = xml + "<flag>failure</flag>";
            }

        } else {
            String i = request.getParameter("id");
            System.out.println(i);
            xml = xml + "<command>duplicate</command>";
            try {
                PreparedStatement pstmt =
                    con.prepareStatement("select MISCELLANEOUS_CODE from WQS_MST_INV_MISCELLANEOUS where MISCELLANEOUS_CODE=?");
                System.out.println(pstmt);
                pstmt.setString(1, i);

                rs = pstmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Duplication");
                    xml = xml + "<flag>failure</flag>";
                } else {
                    System.out.println("No Redundancy");
                    xml = xml + "<flag>success</flag>";
                }
                pstmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        xml = xml + "</response>";
        System.out.println(xml);
        out.println(xml);
        out.close();
    }
}
