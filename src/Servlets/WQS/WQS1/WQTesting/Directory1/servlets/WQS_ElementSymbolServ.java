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

public class WQS_ElementSymbolServ extends HttpServlet {
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
        String xml;
        String i;
        String nm, updatedby = null;
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
        String test_purpose = request.getParameter("test_purpose");
        String es = request.getParameter("es");
        String desc = request.getParameter("desc");
        System.out.println("desc:" + desc);
        String sn = request.getParameter("sn");
        String tr = request.getParameter("val");
        System.out.println("Test Result:" + tr);
        if (cmd.equalsIgnoreCase("add")) {
            System.out.println(es + " " + desc);
            int no = 0;
            try {
                st = con.createStatement();
                rs =
  st.executeQuery("select sno from wqs_element_symbol group by sno having sno =(select max(sno) from wqs_element_symbol where test_purpose_id='" +
                  test_purpose + "')");
                while (rs.next()) {
                    no = rs.getInt(1);
                }
                no = no + 1;
                xml = xml + "<code>" + no + "</code>";
                System.out.println(no);
            } catch (Exception e) {
                System.out.println("Err in AutoIncrement:" + e.getMessage());
            }
            xml = xml + "<command>add</command>";
            try {
                String sql =
                    "select SNO,ELEMENT_SYMBOL,DESCRIPTION,STD_NONSTD,TEST_RESULT from WQS_ELEMENT_SYMBOL where TEST_PURPOSE_ID='" +
                    test_purpose + "' order by SNO";
                st = con.createStatement();
                String ss =
                    "insert into WQS_ELEMENT_SYMBOL(TEST_PURPOSE_ID,SNO,ELEMENT_SYMBOL,DESCRIPTION,STD_NONSTD,TEST_RESULT,UPDATED_DATE,UPDATED_BY_USER_ID)values(?,?,?,?,?,?,?,?)";
                ps = con.prepareStatement(ss);
                ps.setString(1, test_purpose);
                ps.setInt(2, no);
                ps.setString(3, es);
                ps.setString(4, desc);
                ps.setString(5, sn);
                ps.setString(6, tr);
                ps.setTimestamp(7, ts);
                ps.setString(8, updatedby);
                int jj = ps.executeUpdate();
                System.out.println(jj);
                xml = xml + "<flag>success</flag>";
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    xml = xml + "<display>";
                    xml = xml + "<sno>" + rs.getString("SNO") + "</sno>";
                    xml =
 xml + "<es>" + rs.getString("ELEMENT_SYMBOL") + "</es>";
                    xml =
 xml + "<desc>" + rs.getString("DESCRIPTION") + "</desc>";
                    xml = xml + "<sn>" + rs.getString("STD_NONSTD") + "</sn>";
                    xml = xml + "<tr>" + rs.getString("TEST_RESULT") + "</tr>";
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
                    "select TEST_PURPOSE_ID,SNO,ELEMENT_SYMBOL,DESCRIPTION,STD_NONSTD,TEST_RESULT from WQS_ELEMENT_SYMBOL where TEST_PURPOSE_ID='" +
                    test_purpose + "' order by SNO";
                st = con.createStatement();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    xml = xml + "<display>";
                    xml = xml + "<sno>" + rs.getString("SNO") + "</sno>";
                    xml =
 xml + "<es>" + rs.getString("ELEMENT_SYMBOL") + "</es>";
                    xml =
 xml + "<desc>" + rs.getString("DESCRIPTION") + "</desc>";
                    xml = xml + "<sn>" + rs.getString("STD_NONSTD") + "</sn>";
                    xml = xml + "<tr>" + rs.getString("TEST_RESULT") + "</tr>";
                    xml = xml + "</display>";
                }

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        if (cmd.equalsIgnoreCase("checkParam")) {
            xml = xml + "<command>checkParam</command>";
            try {
                String sql =
                    "select ELEMENT_SYMBOL from WQS_ELEMENT_SYMBOL where TEST_PURPOSE_ID='" +
                    test_purpose + "' and ELEMENT_SYMBOL='" + es + "'";
                st = con.createStatement();
                rs = st.executeQuery(sql);
                if (rs.next()) {
                    xml = xml + "<flag>Failure</flag>";
                } else
                    xml = xml + "<flag>Success</flag>";
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (cmd.equalsIgnoreCase("del")) {
            System.out.println(es);
            int sno = Integer.parseInt(request.getParameter("sno"));
            xml = xml + "<command>del</command>";
            try {
                String sql =
                    "select SNO,ELEMENT_SYMBOL,DESCRIPTION,STD_NONSTD,TEST_RESULT from WQS_ELEMENT_SYMBOL where TEST_PURPOSE_ID='" +
                    test_purpose + "' order by SNO";
                st = con.createStatement();

                String ss =
                    "delete from WQS_ELEMENT_SYMBOL where TEST_PURPOSE_ID=? and SNO=?";
                ps = con.prepareStatement(ss);
                ps.setString(1, test_purpose);
                ps.setInt(2, sno);
                ps.executeUpdate();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    xml = xml + "<display>";
                    xml = xml + "<sno>" + rs.getString("SNO") + "</sno>";
                    xml =
 xml + "<es>" + rs.getString("ELEMENT_SYMBOL") + "</es>";
                    xml =
 xml + "<desc>" + rs.getString("DESCRIPTION") + "</desc>";
                    xml = xml + "<sn>" + rs.getString("STD_NONSTD") + "</sn>";
                    xml = xml + "<tr>" + rs.getString("TEST_RESULT") + "</tr>";
                    xml = xml + "</display>";
                }
                xml = xml + "<flag>success</flag>";

            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";
            }
        } else if (cmd.equalsIgnoreCase("upd")) {
            int sno = Integer.parseInt(request.getParameter("sno"));
            xml = xml + "<command>upd</command>";
            try {
                String sql =
                    "select SNO,ELEMENT_SYMBOL,DESCRIPTION,STD_NONSTD,TEST_RESULT from WQS_ELEMENT_SYMBOL where TEST_PURPOSE_ID='" +
                    test_purpose + "' order by SNO";
                st = con.createStatement();
                ps =
  con.prepareStatement("update WQS_ELEMENT_SYMBOL set ELEMENT_SYMBOL=?,DESCRIPTION=?,STD_NONSTD=?,TEST_RESULT=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? where TEST_PURPOSE_ID=? and SNO=?");
                System.out.println("After Update Query");
                ps.setString(1, es);
                System.out.println(es);
                ps.setString(2, desc);
                System.out.println(desc);
                ps.setString(3, sn);
                System.out.println(sn);
                ps.setString(4, tr);
                System.out.println(tr);
                ps.setTimestamp(5, ts);
                ps.setString(6, updatedby);
                ps.setString(7, test_purpose);
                ps.setInt(8, sno);
                System.out.println(sno);
                int ii = ps.executeUpdate();
                if (ii > 0)
                    System.out.println("record updated");
                else
                    System.out.println("record remains same");
                System.out.println(ii);
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    xml = xml + "<display>";
                    xml = xml + "<sno>" + rs.getString("SNO") + "</sno>";
                    xml =
 xml + "<es>" + rs.getString("ELEMENT_SYMBOL") + "</es>";
                    xml =
 xml + "<desc>" + rs.getString("DESCRIPTION") + "</desc>";
                    xml = xml + "<sn>" + rs.getString("STD_NONSTD") + "</sn>";
                    xml = xml + "<tr>" + rs.getString("TEST_RESULT") + "</tr>";
                    xml = xml + "</display>";
                }
                xml = xml + "<flag>success</flag>";
            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";
            }
        }
        xml = xml + "</response>";
        System.out.println("xml is:" + xml);
        out.println(xml);
        out.close();
    }
}
