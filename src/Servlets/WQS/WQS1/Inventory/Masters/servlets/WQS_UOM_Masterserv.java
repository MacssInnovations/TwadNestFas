package Servlets.WQS.WQS1.Inventory.Masters.servlets;

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

public class WQS_UOM_Masterserv extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    Connection con;
    Statement st;
    PreparedStatement ps;
    ResultSet rs;
    String cmd;
    String xml;
    String i;
    String nm;
    int dd, mm, yy;
    String updatedby = null;
    Timestamp ts = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        response.setHeader("cache-control", "no-cache");
        System.out.println("Welcome to servlet");
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

            ConnectionString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
            try {
                st = con.createStatement();
                con.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in opening connection:" + e);
        }

        response.setContentType(CONTENT_TYPE);
        HttpSession session = request.getSession(false);
        try {
            if (session == null) {
                xml =
 "<response><command>sessionout</command><flag>sessionout</flag></response>";
                out.println(xml);
                System.out.println(xml);
                out.close();
                return;

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        xml = "<response>";
        cmd = request.getParameter("command");
        if (cmd.equalsIgnoreCase("load")) {
            xml = xml + "<command>load</command>";
            try {
                String sql = "select * from WQS_MST_UOM order by UOM_CODE asc";
                st = con.createStatement();
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
        }

        if (cmd.equalsIgnoreCase("add")) {
            i = request.getParameter("id");
            nm = request.getParameter("desc");
            System.out.println(i);

            session = request.getSession(false);
            updatedby = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            ts = new Timestamp(l);
            System.out.println(updatedby);
            System.out.println(ts);

            xml = xml + "<command>add</command>";
            try {
                String sql = "select * from WQS_MST_UOM order by UOM_CODE asc";
                st = con.createStatement();
                System.out.println("before insert statement");
                String ss =
                    "insert into WQS_MST_UOM(UOM_CODE,UOM_DESC,UPDATED_DATE,UPDATED_BY_USER_ID) values(?,?,?,?)";
                System.out.println("afetr insert statement");
                ps = con.prepareStatement(ss);
                ps.setString(1, i);
                ps.setString(2, nm);
                ps.setTimestamp(3, ts);
                ps.setString(4, updatedby);
                System.out.println("second insert statement");
                ps.executeUpdate();
                xml = xml + "<added>";
                xml = xml + "<id>" + i + "</id>";
                xml = xml + "<desc>" + nm + "</desc>";
                xml = xml + "</added>";

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
        } else if (cmd.equalsIgnoreCase("edit")) {
            i = request.getParameter("id");
            System.out.println(i);
            xml = xml + "<command>edit</command>";
            try {
                String sql = "select * from WQS_MST_UOM where UOM_CODE=?";
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

            session = request.getSession(false);
            updatedby = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            ts = new Timestamp(l);
            System.out.println(updatedby);
            System.out.println(ts);

            xml = xml + "<command>upd</command>";
            try {
                String ss =
                    "update WQS_MST_UOM set UOM_DESC=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? where UOM_CODE=?";
                ps = con.prepareStatement(ss);
                ps.setString(1, nm);
                ps.setTimestamp(2, ts);
                ps.setString(3, updatedby);
                ps.setString(4, i);
                ps.executeUpdate();
                xml = xml + "<updated>";
                xml = xml + "<id>" + i + "</id>";
                xml = xml + "<desc>" + nm + "</desc>";
                xml = xml + "</updated>";

                String sql = "select * from WQS_MST_UOM order by UOM_CODE asc";
                st = con.createStatement();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    xml = xml + "<display>";
                    xml = xml + "<id>" + rs.getString(1) + "</id>";
                    xml = xml + "<desc>" + rs.getString(2) + "</desc>";
                    xml = xml + "</display>";
                }
                xml = xml + "<flag>success</flag>";
            } catch (Exception e) {
                System.out.println("Err in update:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("del")) {
            i = request.getParameter("id");
            System.out.println(i);
            xml = xml + "<command>del</command>";
            try {
                String sql = "select * from WQS_MST_UOM order by UOM_CODE asc";
                st = con.createStatement();

                String ss = "delete from WQS_MST_UOM where UOM_CODE=?";
                ps = con.prepareStatement(ss);
                ps.setString(1, i);
                ps.executeUpdate();
                xml = xml + "<deleted>";
                xml = xml + "<id>" + i + "</id>";
                xml = xml + "</deleted>";

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
        } else {
            i = request.getParameter("id");
            System.out.println(i);
            xml = xml + "<command>duplicate</command>";
            try {
                PreparedStatement pstmt =
                    con.prepareStatement("select UOM_DESC from WQS_MST_UOM where UOM_CODE=?");
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
        out.println(xml);
        System.out.println(xml);
        out.close();
    }
}
