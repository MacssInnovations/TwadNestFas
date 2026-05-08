package Servlets.WQS.WQS1.Inventory.Masters.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class WQS_GlasswareDirectoryServ extends HttpServlet {
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
        Connection con = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String cmd = null;
        String xml = null;
        String i = null;
        String nm = null;
        int dd = 0, mm = 0, yy = 0;
        response.setHeader("Cache-Control", "no-cache");
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
        HttpSession session = request.getSession(false);
        UserProfile empProfile =
            (UserProfile)session.getAttribute("UserProfile");

        System.out.println("user id::" + empProfile.getEmployeeId());
        int user_id = empProfile.getEmployeeId();


        try {
            //Class.forName("oracle.jdbc.driver.OracleDriver");
            //con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","test","test");
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
        if (cmd.equalsIgnoreCase("load")) {
            xml = xml + "<command>load</command>";
            try {
                String sql =
                    "select * from WQS_MST_INV_GLASSWARE order by GLASSWARE_CODE asc";
                st = con.createStatement();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    xml = xml + "<display>";
                    xml = xml + "<id>" + rs.getString(1) + "</id>";
                    xml = xml + "<desc>" + rs.getString(2) + "</desc>";
                    xml = xml + "</display>";
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }

        if (cmd.equalsIgnoreCase("add")) {
            i = request.getParameter("id");
            nm = request.getParameter("desc");
            System.out.println(i);
            xml = xml + "<command>add</command>";
            try {
                String sql =
                    "select * from WQS_MST_INV_GLASSWARE order by GLASSWARE_CODE asc";
                st = con.createStatement();

                String ss =
                    "insert into WQS_MST_INV_GLASSWARE(GLASSWARE_CODE,GLASSWARE_SPEC,UPDATED_DATE,UPDATED_BY_USER_ID) values(?,?,?,?)";
                ps = con.prepareStatement(ss);
                ps.setString(1, i);
                ps.setString(2, nm);
                ps.setTimestamp(3, ts);
                ps.setInt(4, user_id);
                ps.executeUpdate();
                xml = xml + "<added>";
                xml = xml + "<id>" + i + "</id>";
                xml = xml + "<desc>" + nm + "</desc>";
                xml = xml + "</added>";
                //}
                xml = xml + "<flag>success</flag>";
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    xml = xml + "<display>";
                    xml = xml + "<id>" + rs.getString(1) + "</id>";
                    xml = xml + "<desc>" + rs.getString(2) + "</desc>";
                    xml = xml + "</display>";
                }

            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";
            }
            //exists=0;
        } else if (cmd.equalsIgnoreCase("edit")) {
            i = request.getParameter("id");
            System.out.println(i);
            xml = xml + "<command>edit</command>";
            try {
                String sql =
                    "select * from WQS_MST_INV_GLASSWARE where GLASSWARE_CODE=?";
                ps = con.prepareStatement(sql);
                ps.setString(1, i);
                rs = ps.executeQuery();
                System.out.println(i);
                while (rs.next()) {

                    xml = xml + "<edit>";
                    xml = xml + "<id>" + rs.getString(1) + "</id>";
                    xml = xml + "<desc>" + rs.getString(2) + "</desc>";
                    xml = xml + "</edit>";

                }
            } catch (Exception e) {
                System.out.println(e);
            }

        }

        else if (cmd.equalsIgnoreCase("upd")) {
            i = request.getParameter("id");
            nm = request.getParameter("desc");
            System.out.println(i);
            xml = xml + "<command>upd</command>";
            try {
                String sql =
                    "select * from WQS_MST_INV_GLASSWARE order by GLASSWARE_CODE asc";
                st = con.createStatement();

                String ss =
                    "update WQS_MST_INV_GLASSWARE set GLASSWARE_SPEC=? where GLASSWARE_CODE=?";
                ps = con.prepareStatement(ss);
                ps.setString(1, nm);
                ps.setString(2, i);
                ps.executeUpdate();
                ss =
  "update WQS_MST_INV_GLASSWARE set UPDATED_DATE=? where GLASSWARE_CODE=?";
                ps = con.prepareStatement(ss);
                ps.setTimestamp(1, ts);
                ps.setString(2, i);
                ps.executeUpdate();
                System.out.println(ts);
                ss =
  "update WQS_MST_INV_GLASSWARE set UPDATED_BY_USER_ID=? where GLASSWARE_CODE=?";
                ps = con.prepareStatement(ss);
                ps.setInt(1, user_id);
                ps.setString(2, i);
                ps.executeUpdate();
                xml = xml + "<updated>";
                xml = xml + "<id>" + i + "</id>";
                xml = xml + "<desc>" + nm + "</desc>";
                xml = xml + "</updated>";
                //}

                rs = st.executeQuery(sql);
                while (rs.next()) {
                    xml = xml + "<display>";
                    xml = xml + "<id>" + rs.getString(1) + "</id>";
                    xml = xml + "<desc>" + rs.getString(2) + "</desc>";
                    xml = xml + "</display>";
                }
                xml = xml + "<flag>success</flag>";
            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";
            }
            //exists=0;
        }

        else if (cmd.equalsIgnoreCase("del")) {
            i = request.getParameter("id");
            System.out.println(i);
            xml = xml + "<command>del</command>";
            try {
                ps =
  con.prepareStatement("select * from WQS_MST_INV_SUP_ITEM where ITEM_CODE=? and MAJOR_CATEGORY_CODE in('GLA')");
                ps.setString(1, i);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml = xml + "<flag>FoundData</flag>";
                } else {
                    String sql =
                        "select * from WQS_MST_INV_GLASSWARE order by GLASSWARE_CODE asc";
                    st = con.createStatement();

                    String ss =
                        "delete from WQS_MST_INV_GLASSWARE where GLASSWARE_CODE=?";
                    ps = con.prepareStatement(ss);
                    ps.setString(1, i);
                    ps.executeUpdate();
                    xml = xml + "<deleted>";
                    xml = xml + "<id>" + i + "</id>";
                    xml = xml + "</deleted>";
                    //}
                    rs = st.executeQuery(sql);
                    while (rs.next()) {
                        xml = xml + "<display>";
                        xml = xml + "<id>" + rs.getString(1) + "</id>";
                        xml = xml + "<desc>" + rs.getString(2) + "</desc>";
                        xml = xml + "</display>";
                    }
                    xml = xml + "<flag>success</flag>";
                }
            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";
            }

        } else {
            i = request.getParameter("id");
            System.out.println(i);
            xml = xml + "<command>duplicate</command>";
            try {
                PreparedStatement pstmt =
                    con.prepareStatement("select GLASSWARE_CODE from WQS_MST_INV_GLASSWARE where GLASSWARE_CODE=?");
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
