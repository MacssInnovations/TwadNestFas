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

public class WQS_StandardResultServ extends HttpServlet {
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
        PreparedStatement ps = null;
        ResultSet rs;
        String cmd;
        String xml, updatedby = null, sql = "";
        Timestamp ts = null;
        int count = 0;
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
        String parameter = request.getParameter("parameter");
        String es = request.getParameter("es");
        String scode = request.getParameter("scode");
        String dv = request.getParameter("dv");
        String mv = request.getParameter("mv");
        String perm_limit = request.getParameter("perm_limit");
        String grade1 = request.getParameter("grade1");
        String grade2 = request.getParameter("grade2");
        String grade3 = request.getParameter("grade3");
        if (cmd.equalsIgnoreCase("add")) {
            xml = xml + "<command>add</command>";
            try {
                if (test_purpose.equalsIgnoreCase("DRI")) {
                    xml = xml + "<purpose>Drinking</purpose>";
                    String ss = "";
                    if (parameter.equalsIgnoreCase("Std")) {
                        sql =
 "select * from(select ELEMENT_SYMBOL,STANDARD_CODE,DESIRABLE_VALUE,MAXIMUM_VALUE from WQS_STD_RESULT)a left outer join(select TEST_PURPOSE_ID,ELEMENT_SYMBOL,SNO from WQS_ELEMENT_SYMBOL)b on a.ELEMENT_SYMBOL=b.ELEMENT_SYMBOL where b.TEST_PURPOSE_ID='" +
   test_purpose + "' order by b.SNO asc";
                        st = con.createStatement();
                        ss =
  "insert into WQS_STD_RESULT(ELEMENT_SYMBOL,STANDARD_CODE,DESIRABLE_VALUE,MAXIMUM_VALUE,UPDATED_DATE,UPDATED_BY_USER_ID)values(?,?,?,?,?,?)";
                    } else {
                        sql =
 "select * from(select ELEMENT_SYMBOL,STANDARD_CODE,DESIRABLE_VALUE,MAXIMUM_VALUE from WQS_NONSTD_RESULT)a left outer join(select TEST_PURPOSE_ID,ELEMENT_SYMBOL,SNO from WQS_ELEMENT_SYMBOL)b on a.ELEMENT_SYMBOL=b.ELEMENT_SYMBOL where b.TEST_PURPOSE_ID='" +
   test_purpose + "' order by b.SNO asc";
                        st = con.createStatement();
                        ss =
  "insert into WQS_NONSTD_RESULT(ELEMENT_SYMBOL,STANDARD_CODE,DESIRABLE_VALUE,MAXIMUM_VALUE,UPDATED_DATE,UPDATED_BY_USER_ID)values(?,?,?,?,?,?)";
                    }
                    ps = con.prepareStatement(ss);
                    ps.setString(1, es);
                    ps.setString(2, scode);
                    ps.setString(3, dv);
                    ps.setString(4, mv);
                    ps.setTimestamp(5, ts);
                    ps.setString(6, updatedby);
                    count = ps.executeUpdate();
                    if (count > 0)
                        xml = xml + "<flag>success</flag>";
                    else
                        xml = xml + "<flag>failure</flag>";
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
                } else if (test_purpose.equalsIgnoreCase("CON")) {
                    xml = xml + "<purpose>Construction</purpose>";
                    sql =
 "select * from(select ELEMENT_SYMBOL,PERMISSIBLE_LIMIT from WQS_CONSTRUCTION_STANDARDS)a left outer join(select TEST_PURPOSE_ID,ELEMENT_SYMBOL,SNO from WQS_ELEMENT_SYMBOL)b on a.ELEMENT_SYMBOL=b.ELEMENT_SYMBOL where b.TEST_PURPOSE_ID='" +
   test_purpose + "' order by b.SNO asc";
                    st = con.createStatement();
                    String ss =
                        "insert into WQS_CONSTRUCTION_STANDARDS(ELEMENT_SYMBOL,PERMISSIBLE_LIMIT,UPDATED_DATE,UPDATED_BY_USER_ID)values(?,?,?,?)";
                    ps = con.prepareStatement(ss);
                    ps.setString(1, es);
                    ps.setString(2, perm_limit);
                    ps.setTimestamp(3, ts);
                    ps.setString(4, updatedby);
                    count = ps.executeUpdate();
                    if (count > 0)
                        xml = xml + "<flag>success</flag>";
                    else
                        xml = xml + "<flag>failure</flag>";
                    rs = st.executeQuery(sql);
                    while (rs.next()) {
                        xml = xml + "<display>";
                        xml =
 xml + "<es>" + rs.getString("ELEMENT_SYMBOL") + "</es>";
                        xml =
 xml + "<plimit>" + rs.getString("PERMISSIBLE_LIMIT") + "</plimit>";
                        xml = xml + "</display>";
                    }
                } else if (test_purpose.equalsIgnoreCase("ALUM")) {
                    xml = xml + "<purpose>Alum</purpose>";
                    sql =
 "select * from(select ELEMENT_SYMBOL,GRADE1,GRADE2,GRADE3 from WQS_ALUM_STANDARDS)a left outer join(select TEST_PURPOSE_ID,ELEMENT_SYMBOL,SNO from WQS_ELEMENT_SYMBOL)b on a.ELEMENT_SYMBOL=b.ELEMENT_SYMBOL where b.TEST_PURPOSE_ID='" +
   test_purpose + "' order by b.SNO asc";
                    st = con.createStatement();
                    String ss =
                        "insert into WQS_ALUM_STANDARDS(ELEMENT_SYMBOL,GRADE1,GRADE2,GRADE3,UPDATED_DATE,UPDATED_BY_USER_ID)values(?,?,?,?,?,?)";
                    ps = con.prepareStatement(ss);
                    ps.setString(1, es);
                    ps.setString(2, grade1);
                    ps.setString(3, grade2);
                    ps.setString(4, grade3);
                    ps.setTimestamp(5, ts);
                    ps.setString(6, updatedby);
                    count = ps.executeUpdate();
                    if (count > 0)
                        xml = xml + "<flag>success</flag>";
                    else
                        xml = xml + "<flag>failure</flag>";
                    rs = st.executeQuery(sql);
                    while (rs.next()) {
                        xml = xml + "<display>";
                        xml =
 xml + "<es>" + rs.getString("ELEMENT_SYMBOL") + "</es>";
                        xml =
 xml + "<grade1>" + rs.getString("GRADE1") + "</grade1>";
                        xml =
 xml + "<grade2>" + rs.getString("GRADE2") + "</grade2>";
                        xml =
 xml + "<grade3>" + rs.getString("GRADE3") + "</grade3>";
                        xml = xml + "</display>";
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";
            }
        } else if (cmd.equalsIgnoreCase("loadParameter")) {
            xml = xml + "<command>loadParameter</command>";
            try {
                if (parameter.equalsIgnoreCase("NonStd"))
                    sql =
 "select element_symbol from wqs_element_symbol where test_purpose_id='" +
   test_purpose + "' and std_nonstd='N' order by sno";
                else
                    sql =
 "select element_symbol from wqs_element_symbol where test_purpose_id='" +
   test_purpose + "' and std_nonstd='S' order by sno";
                System.out.println("sql:" + sql);
                st = con.createStatement();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    count++;
                    xml = xml + "<display>";
                    xml =
 xml + "<es>" + rs.getString("element_symbol") + "</es>";
                    xml = xml + "</display>";
                }
                if (count > 0)
                    xml = xml + "<flag>success</flag>";
                else
                    xml = xml + "<flag>failure</flag>";
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (cmd.equalsIgnoreCase("load")) {
            xml = xml + "<command>load</command>";
            try {
                if (test_purpose.equalsIgnoreCase("DRI")) {
                    xml = xml + "<purpose>Drinking</purpose>";
                    if (parameter.equalsIgnoreCase("Std")) {
                        sql =
 "select * from(select ELEMENT_SYMBOL,STANDARD_CODE,DESIRABLE_VALUE,MAXIMUM_VALUE from WQS_STD_RESULT)a left outer join(select TEST_PURPOSE_ID,ELEMENT_SYMBOL,SNO from WQS_ELEMENT_SYMBOL)b on a.ELEMENT_SYMBOL=b.ELEMENT_SYMBOL where b.TEST_PURPOSE_ID='" +
   test_purpose + "' order by b.SNO asc";
                    } else {
                        sql =
 "select * from(select ELEMENT_SYMBOL,STANDARD_CODE,DESIRABLE_VALUE,MAXIMUM_VALUE from WQS_NONSTD_RESULT)a left outer join(select TEST_PURPOSE_ID,ELEMENT_SYMBOL,SNO from WQS_ELEMENT_SYMBOL)b on a.ELEMENT_SYMBOL=b.ELEMENT_SYMBOL where b.TEST_PURPOSE_ID='" +
   test_purpose + "' order by b.SNO asc";
                    }
                    System.out.println("SQL:::" + sql);
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
                } else if (test_purpose.equalsIgnoreCase("CON")) {
                    xml = xml + "<purpose>Construction</purpose>";
                    sql =
 "select * from(select ELEMENT_SYMBOL,PERMISSIBLE_LIMIT from WQS_CONSTRUCTION_STANDARDS)a left outer join(select TEST_PURPOSE_ID,ELEMENT_SYMBOL,SNO from WQS_ELEMENT_SYMBOL)b on a.ELEMENT_SYMBOL=b.ELEMENT_SYMBOL where b.TEST_PURPOSE_ID='" +
   test_purpose + "' order by b.SNO asc";
                    System.out.println("SQL:::" + sql);
                    st = con.createStatement();
                    rs = st.executeQuery(sql);
                    while (rs.next()) {
                        xml = xml + "<display>";
                        xml =
 xml + "<es>" + rs.getString("ELEMENT_SYMBOL") + "</es>";
                        xml =
 xml + "<plimit>" + rs.getString("PERMISSIBLE_LIMIT") + "</plimit>";
                        xml = xml + "</display>";
                    }
                } else if (test_purpose.equalsIgnoreCase("ALUM")) {
                    xml = xml + "<purpose>Alum</purpose>";
                    sql =
 "select * from(select ELEMENT_SYMBOL,GRADE1,GRADE2,GRADE3 from WQS_ALUM_STANDARDS)a left outer join(select TEST_PURPOSE_ID,ELEMENT_SYMBOL,SNO from WQS_ELEMENT_SYMBOL)b on a.ELEMENT_SYMBOL=b.ELEMENT_SYMBOL where b.TEST_PURPOSE_ID='" +
   test_purpose + "' order by b.SNO asc";
                    System.out.println("SQL:::" + sql);
                    st = con.createStatement();
                    rs = st.executeQuery(sql);
                    while (rs.next()) {
                        xml = xml + "<display>";
                        xml =
 xml + "<es>" + rs.getString("ELEMENT_SYMBOL") + "</es>";
                        xml =
 xml + "<grade1>" + rs.getString("GRADE1") + "</grade1>";
                        xml =
 xml + "<grade2>" + rs.getString("GRADE2") + "</grade2>";
                        xml =
 xml + "<grade3>" + rs.getString("GRADE3") + "</grade3>";
                        xml = xml + "</display>";
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (cmd.equalsIgnoreCase("upd")) {
            xml = xml + "<command>upd</command>";
            try {
                if (test_purpose.equalsIgnoreCase("DRI")) {
                    xml = xml + "<purpose>Drinking</purpose>";
                    String ss = "";
                    if (parameter.equalsIgnoreCase("Std")) {
                        sql =
 "select * from(select ELEMENT_SYMBOL,STANDARD_CODE,DESIRABLE_VALUE,MAXIMUM_VALUE from WQS_STD_RESULT)a left outer join(select TEST_PURPOSE_ID,ELEMENT_SYMBOL,SNO from WQS_ELEMENT_SYMBOL)b on a.ELEMENT_SYMBOL=b.ELEMENT_SYMBOL where b.TEST_PURPOSE_ID='" +
   test_purpose + "' order by b.SNO asc";
                        st = con.createStatement();

                        ss =
  "update WQS_STD_RESULT set STANDARD_CODE=?,DESIRABLE_VALUE=?,MAXIMUM_VALUE=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? where ELEMENT_SYMBOL=?";
                    } else {
                        sql =
 "select * from(select ELEMENT_SYMBOL,STANDARD_CODE,DESIRABLE_VALUE,MAXIMUM_VALUE from WQS_NONSTD_RESULT)a left outer join(select TEST_PURPOSE_ID,ELEMENT_SYMBOL,SNO from WQS_ELEMENT_SYMBOL)b on a.ELEMENT_SYMBOL=b.ELEMENT_SYMBOL where b.TEST_PURPOSE_ID='" +
   test_purpose + "' order by b.SNO asc";
                        st = con.createStatement();

                        ss =
  "update WQS_NONSTD_RESULT set STANDARD_CODE=?,DESIRABLE_VALUE=?,MAXIMUM_VALUE=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? where ELEMENT_SYMBOL=?";
                    }
                    ps = con.prepareStatement(ss);
                    ps.setString(1, scode);
                    ps.setString(2, dv);
                    ps.setString(3, mv);
                    ps.setTimestamp(4, ts);
                    ps.setString(5, updatedby);
                    ps.setString(6, es);

                    count = ps.executeUpdate();
                    if (count > 0)
                        xml = xml + "<flag>success</flag>";
                    else
                        xml = xml + "<flag>failure</flag>";
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
                } else if (test_purpose.equalsIgnoreCase("CON")) {
                    xml = xml + "<purpose>Construction</purpose>";
                    sql =
 "select * from(select ELEMENT_SYMBOL,PERMISSIBLE_LIMIT from WQS_CONSTRUCTION_STANDARDS)a left outer join(select TEST_PURPOSE_ID,ELEMENT_SYMBOL,SNO from WQS_ELEMENT_SYMBOL)b on a.ELEMENT_SYMBOL=b.ELEMENT_SYMBOL where b.TEST_PURPOSE_ID='" +
   test_purpose + "' order by b.SNO asc";
                    st = con.createStatement();

                    String ss =
                        "update WQS_CONSTRUCTION_STANDARDS set PERMISSIBLE_LIMIT=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? where ELEMENT_SYMBOL=?";
                    ps = con.prepareStatement(ss);
                    ps.setString(1, perm_limit);
                    ps.setTimestamp(2, ts);
                    ps.setString(3, updatedby);
                    ps.setString(4, es);
                    count = ps.executeUpdate();
                    if (count > 0)
                        xml = xml + "<flag>success</flag>";
                    else
                        xml = xml + "<flag>failure</flag>";
                    rs = st.executeQuery(sql);
                    while (rs.next()) {
                        xml = xml + "<display>";
                        xml =
 xml + "<es>" + rs.getString("ELEMENT_SYMBOL") + "</es>";
                        xml =
 xml + "<plimit>" + rs.getString("PERMISSIBLE_LIMIT") + "</plimit>";
                        xml = xml + "</display>";
                    }
                } else if (test_purpose.equalsIgnoreCase("ALUM")) {
                    xml = xml + "<purpose>Alum</purpose>";
                    sql =
 "select * from(select ELEMENT_SYMBOL,GRADE1,GRADE2,GRADE3 from WQS_ALUM_STANDARDS)a left outer join(select TEST_PURPOSE_ID,ELEMENT_SYMBOL,SNO from WQS_ELEMENT_SYMBOL)b on a.ELEMENT_SYMBOL=b.ELEMENT_SYMBOL where b.TEST_PURPOSE_ID='" +
   test_purpose + "' order by b.SNO asc";
                    st = con.createStatement();

                    String ss =
                        "update WQS_ALUM_STANDARDS set GRADE1=?,GRADE2=?,GRADE3=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? where ELEMENT_SYMBOL=?";
                    ps = con.prepareStatement(ss);
                    ps.setString(1, grade1);
                    ps.setString(2, grade2);
                    ps.setString(3, grade3);
                    ps.setTimestamp(4, ts);
                    ps.setString(5, updatedby);
                    ps.setString(6, es);
                    count = ps.executeUpdate();
                    if (count > 0)
                        xml = xml + "<flag>success</flag>";
                    else
                        xml = xml + "<flag>failure</flag>";
                    rs = st.executeQuery(sql);
                    while (rs.next()) {
                        xml = xml + "<display>";
                        xml =
 xml + "<es>" + rs.getString("ELEMENT_SYMBOL") + "</es>";
                        xml =
 xml + "<grade1>" + rs.getString("GRADE1") + "</grade1>";
                        xml =
 xml + "<grade2>" + rs.getString("GRADE2") + "</grade2>";
                        xml =
 xml + "<grade3>" + rs.getString("GRADE3") + "</grade3>";
                        xml = xml + "</display>";
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";
            }
        } else if (cmd.equalsIgnoreCase("del")) {
            xml = xml + "<command>del</command>";
            try {
                if (test_purpose.equalsIgnoreCase("DRI")) {
                    xml = xml + "<purpose>Drinking</purpose>";
                    String ss = "";
                    if (parameter.equalsIgnoreCase("Std")) {
                        sql =
 "select * from(select ELEMENT_SYMBOL,STANDARD_CODE,DESIRABLE_VALUE,MAXIMUM_VALUE from WQS_STD_RESULT)a left outer join(select TEST_PURPOSE_ID,ELEMENT_SYMBOL,SNO from WQS_ELEMENT_SYMBOL)b on a.ELEMENT_SYMBOL=b.ELEMENT_SYMBOL where b.TEST_PURPOSE_ID='" +
   test_purpose + "' order by b.SNO asc";
                        st = con.createStatement();

                        ss =
  "delete from WQS_STD_RESULT where ELEMENT_SYMBOL=?";
                    } else {
                        sql =
 "select * from(select ELEMENT_SYMBOL,STANDARD_CODE,DESIRABLE_VALUE,MAXIMUM_VALUE from WQS_NONSTD_RESULT)a left outer join(select TEST_PURPOSE_ID,ELEMENT_SYMBOL,SNO from WQS_ELEMENT_SYMBOL)b on a.ELEMENT_SYMBOL=b.ELEMENT_SYMBOL where b.TEST_PURPOSE_ID='" +
   test_purpose + "' order by b.SNO asc";
                        st = con.createStatement();

                        ss =
  "delete from WQS_NONSTD_RESULT where ELEMENT_SYMBOL=?";
                    }
                    ps = con.prepareStatement(ss);
                    ps.setString(1, es);
                    count = ps.executeUpdate();
                    if (count > 0)
                        xml = xml + "<flag>success</flag>";
                    else
                        xml = xml + "<flag>failure</flag>";
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
                } else if (test_purpose.equalsIgnoreCase("CON")) {
                    xml = xml + "<purpose>Construction</purpose>";
                    sql =
 "select * from(select ELEMENT_SYMBOL,PERMISSIBLE_LIMIT from WQS_CONSTRUCTION_STANDARDS)a left outer join(select TEST_PURPOSE_ID,ELEMENT_SYMBOL,SNO from WQS_ELEMENT_SYMBOL)b on a.ELEMENT_SYMBOL=b.ELEMENT_SYMBOL where b.TEST_PURPOSE_ID='" +
   test_purpose + "' order by b.SNO asc";
                    st = con.createStatement();

                    String ss =
                        "delete from WQS_CONSTRUCTION_STANDARDS where ELEMENT_SYMBOL=?";
                    ps = con.prepareStatement(ss);
                    ps.setString(1, es);
                    count = ps.executeUpdate();
                    if (count > 0)
                        xml = xml + "<flag>success</flag>";
                    else
                        xml = xml + "<flag>failure</flag>";
                    rs = st.executeQuery(sql);
                    while (rs.next()) {
                        xml = xml + "<display>";
                        xml =
 xml + "<es>" + rs.getString("ELEMENT_SYMBOL") + "</es>";
                        xml =
 xml + "<plimit>" + rs.getString("PERMISSIBLE_LIMIT") + "</plimit>";
                        xml = xml + "</display>";
                    }
                } else if (test_purpose.equalsIgnoreCase("ALUM")) {
                    xml = xml + "<purpose>Alum</purpose>";
                    sql =
 "select * from(select ELEMENT_SYMBOL,GRADE1,GRADE2,GRADE3 from WQS_ALUM_STANDARDS)a left outer join(select TEST_PURPOSE_ID,ELEMENT_SYMBOL,SNO from WQS_ELEMENT_SYMBOL)b on a.ELEMENT_SYMBOL=b.ELEMENT_SYMBOL where b.TEST_PURPOSE_ID='" +
   test_purpose + "' order by b.SNO asc";
                    st = con.createStatement();

                    String ss =
                        "delete from WQS_ALUM_STANDARDS where ELEMENT_SYMBOL=?";
                    ps = con.prepareStatement(ss);
                    ps.setString(1, es);
                    count = ps.executeUpdate();
                    if (count > 0)
                        xml = xml + "<flag>success</flag>";
                    else
                        xml = xml + "<flag>failure</flag>";
                    rs = st.executeQuery(sql);
                    while (rs.next()) {
                        xml = xml + "<display>";
                        xml =
 xml + "<es>" + rs.getString("ELEMENT_SYMBOL") + "</es>";
                        xml =
 xml + "<grade1>" + rs.getString("GRADE1") + "</grade1>";
                        xml =
 xml + "<grade2>" + rs.getString("GRADE2") + "</grade2>";
                        xml =
 xml + "<grade3>" + rs.getString("GRADE3") + "</grade3>";
                        xml = xml + "</display>";
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";
            }
        } else if (cmd.equalsIgnoreCase("checkAvail")) {
            xml = xml + "<command>checkAvail</command>";
            try {
                if (test_purpose.equalsIgnoreCase("DRI")) {
                    xml = xml + "<purpose>Drinking</purpose>";
                    if (parameter.equalsIgnoreCase("Std"))
                        ps =
  con.prepareStatement("select ELEMENT_SYMBOL from WQS_STD_RESULT where ELEMENT_SYMBOL=?");
                    else
                        ps =
  con.prepareStatement("select ELEMENT_SYMBOL from WQS_NONSTD_RESULT where ELEMENT_SYMBOL=?");
                } else if (test_purpose.equalsIgnoreCase("CON")) {
                    xml = xml + "<purpose>Construction</purpose>";
                    ps =
  con.prepareStatement("select ELEMENT_SYMBOL from WQS_CONSTRUCTION_STANDARDS where ELEMENT_SYMBOL=?");
                } else if (test_purpose.equalsIgnoreCase("ALUM")) {
                    xml = xml + "<purpose>Alum</purpose>";
                    ps =
  con.prepareStatement("select ELEMENT_SYMBOL from WQS_ALUM_STANDARDS where ELEMENT_SYMBOL=?");
                }
                ps.setString(1, es);
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
