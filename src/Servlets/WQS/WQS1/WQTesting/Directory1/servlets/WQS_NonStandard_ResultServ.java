package Servlets.WQS.WQS1.WQTesting.Directory1.servlets;

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

public class WQS_NonStandard_ResultServ extends HttpServlet {
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
        response.setHeader("cache-control", "no-cache");
        Connection con = null;
        Statement st;
        PreparedStatement ps;
        ResultSet rs;
        String cmd;
        String xml, updatedby = null;
        Timestamp ts = null;
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
        session = request.getSession(false);
        updatedby = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        ts = new Timestamp(l);
        System.out.println(updatedby);
        System.out.println(ts);

        xml = "<response>";
        cmd = request.getParameter("command");
        String es = request.getParameter("es");
        String scode = request.getParameter("scode");
        System.out.println("scode:" + scode);
        String dv = request.getParameter("dv");
        String mv = request.getParameter("mv");
        if (cmd.equalsIgnoreCase("add")) {
            xml = xml + "<command>add</command>";
            try {
                String sql =
                    "select * from(select ELEMENT_SYMBOL,STANDARD_CODE,DESIRABLE_VALUE,MAXIMUM_VALUE from WQS_NONSTD_RESULT)a left outer join(select ELEMENT_SYMBOL,SNO from WQS_ELEMENT_SYMBOL)b on a.ELEMENT_SYMBOL=b.ELEMENT_SYMBOL order by b.SNO asc";
                st = con.createStatement();
                String ss =
                    "insert into WQS_NONSTD_RESULT(ELEMENT_SYMBOL,STANDARD_CODE,DESIRABLE_VALUE,MAXIMUM_VALUE,UPDATED_DATE,UPDATED_BY_USER_ID)values(?,?,?,?,?,?)";
                ps = con.prepareStatement(ss);
                ps.setString(1, es);
                ps.setString(2, scode);
                ps.setString(3, dv);
                ps.setString(4, mv);
                ps.setTimestamp(5, ts);
                ps.setString(6, updatedby);
                ps.executeUpdate();
                xml = xml + "<added>";
                xml = xml + "<es>" + es + "</es>";
                xml = xml + "<scode>" + scode + "</scode>";
                xml = xml + "<dv>" + dv + "</dv>";
                xml = xml + "<mv>" + mv + "</mv>";
                xml = xml + "</added>";
                xml = xml + "<flag>success</flag>";
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    xml = xml + "<display>";
                    xml =
 xml + "<es>" + rs.getString("ELEMENT_SYMBOL") + "</es>";
                    xml =
 xml + "<scode>" + rs.getString("STANDARD_CODE") + "</scode>";
                    xml =
 xml + "<dv>" + rs.getString("DESIRABLE_VALUE") + "</dv>";
                    xml =
 xml + "<mv>" + rs.getString("MAXIMUM_VALUE") + "</mv>";
                    xml = xml + "</display>";
                }
            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";
            }
        }
        if (cmd.equalsIgnoreCase("load")) {
            xml = xml + "<command>load</command>";
            try {
                String sql =
                    "select * from(select ELEMENT_SYMBOL,STANDARD_CODE,DESIRABLE_VALUE,MAXIMUM_VALUE from WQS_NONSTD_RESULT)a left outer join(select ELEMENT_SYMBOL,SNO from WQS_ELEMENT_SYMBOL)b on a.ELEMENT_SYMBOL=b.ELEMENT_SYMBOL order by b.SNO asc";
                st = con.createStatement();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    xml = xml + "<display>";
                    xml =
 xml + "<es>" + rs.getString("ELEMENT_SYMBOL") + "</es>";
                    xml =
 xml + "<scode>" + rs.getString("STANDARD_CODE") + "</scode>";
                    xml =
 xml + "<dv>" + rs.getString("DESIRABLE_VALUE") + "</dv>";
                    xml =
 xml + "<mv>" + rs.getString("MAXIMUM_VALUE") + "</mv>";
                    xml = xml + "</display>";
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (cmd.equalsIgnoreCase("upd")) {
            xml = xml + "<command>upd</command>";
            try {
                String sql =
                    "select * from(select ELEMENT_SYMBOL,STANDARD_CODE,DESIRABLE_VALUE,MAXIMUM_VALUE from WQS_NONSTD_RESULT)a left outer join(select ELEMENT_SYMBOL,SNO from WQS_ELEMENT_SYMBOL)b on a.ELEMENT_SYMBOL=b.ELEMENT_SYMBOL order by b.SNO asc";
                st = con.createStatement();
                String ss =
                    "update WQS_NONSTD_RESULT set DESIRABLE_VALUE=?,MAXIMUM_VALUE=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? where ELEMENT_SYMBOL=? and STANDARD_CODE=?";
                ps = con.prepareStatement(ss);
                ps.setString(1, dv);
                ps.setString(2, mv);
                ps.setTimestamp(3, ts);
                ps.setString(4, updatedby);
                ps.setString(5, es);
                ps.setString(6, scode);
                ps.executeUpdate();

                xml = xml + "<updated>";
                xml = xml + "<es>" + es + "</es>";
                xml = xml + "<scode>" + scode + "</scode>";
                xml = xml + "<dv>" + dv + "</dv>";
                xml = xml + "<mv>" + mv + "</mv>";
                xml = xml + "</updated>";

                rs = st.executeQuery(sql);
                while (rs.next()) {
                    xml = xml + "<display>";
                    xml =
 xml + "<es>" + rs.getString("ELEMENT_SYMBOL") + "</es>";
                    xml =
 xml + "<scode>" + rs.getString("STANDARD_CODE") + "</scode>";
                    xml =
 xml + "<dv>" + rs.getString("DESIRABLE_VALUE") + "</dv>";
                    xml =
 xml + "<mv>" + rs.getString("MAXIMUM_VALUE") + "</mv>";
                    xml = xml + "</display>";
                }
                xml = xml + "<flag>success</flag>";
            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";
            }
        } else if (cmd.equalsIgnoreCase("del")) {
            xml = xml + "<command>del</command>";
            try {
                String sql =
                    "select * from(select ELEMENT_SYMBOL,STANDARD_CODE,DESIRABLE_VALUE,MAXIMUM_VALUE from WQS_NONSTD_RESULT)a left outer join(select ELEMENT_SYMBOL,SNO from WQS_ELEMENT_SYMBOL)b on a.ELEMENT_SYMBOL=b.ELEMENT_SYMBOL order by b.SNO asc";
                st = con.createStatement();

                String ss =
                    "delete from WQS_NONSTD_RESULT where ELEMENT_SYMBOL=? and STANDARD_CODE=?";
                ps = con.prepareStatement(ss);
                ps.setString(1, es);
                ps.setString(2, scode);
                ps.executeUpdate();
                xml = xml + "<deleted>";
                xml = xml + "<es>" + es + "</es>";
                xml = xml + "<scode>" + scode + "</scode>";
                xml = xml + "<dv>" + dv + "</dv>";
                xml = xml + "<mv>" + mv + "</mv>";
                xml = xml + "</deleted>";
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    xml = xml + "<display>";
                    xml =
 xml + "<es>" + rs.getString("ELEMENT_SYMBOL") + "</es>";
                    xml =
 xml + "<scode>" + rs.getString("STANDARD_CODE") + "</scode>";
                    xml =
 xml + "<dv>" + rs.getString("DESIRABLE_VALUE") + "</dv>";
                    xml =
 xml + "<mv>" + rs.getString("MAXIMUM_VALUE") + "</mv>";
                    xml = xml + "</display>";
                }
                xml = xml + "<flag>success</flag>";

            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";
            }
        }
        if (cmd.equalsIgnoreCase("checkAvail")) {
            System.out.println("inside checkavail");
            xml = xml + "<command>checkAvail</command>";
            try {
                ps =
  con.prepareStatement("select ELEMENT_SYMBOL,STANDARD_CODE from WQS_NONSTD_RESULT where ELEMENT_SYMBOL=? and STANDARD_CODE=?");
                ps.setString(1, es);
                ps.setString(2, scode);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml = xml + "<flag>Success</flag>";
                } else
                    xml = xml + "<flag>Failure</flag>";
            } catch (Exception e) {
                System.out.println(e);
            }
        }
        xml = xml + "</response>";
        System.out.println("xml is:" + xml);
        out.println(xml);
        out.close();
    }
}
