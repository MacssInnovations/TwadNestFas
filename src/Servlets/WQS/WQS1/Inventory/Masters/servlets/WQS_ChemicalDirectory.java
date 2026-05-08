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

public class WQS_ChemicalDirectory extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=UTF-8";
    Connection con;
    Statement st;
    PreparedStatement ps;
    ResultSet rs;
    String cmd;
    String xml;
    String i;
    String nm, fmla, lev, flag;
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
        response.setHeader("Cache-Control", "no-cache");

        out.println("<html>");
        out.println("<head><title>ChemicalDirectory</title></head>");
        out.println("<body>");
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

        session = request.getSession(false);
        updatedby = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        ts = new Timestamp(l);
        System.out.println(updatedby);
        System.out.println(ts);
        xml = "<response>";
        cmd = request.getParameter("command");
        if (cmd.equalsIgnoreCase("load")) {
            xml = xml + "<command>load</command>";
            try {
                String sql =
                    "select * from WQS_MST_INV_CHEMICAL order by CHEMICAL_CODE";
                st = con.createStatement();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    xml = xml + "<display>";
                    xml = xml + "<id>" + rs.getString(1) + "</id>";
                    xml = xml + "<desc>" + rs.getString(2) + "</desc>";
                    xml = xml + "<frmla>" + rs.getString(3) + "</frmla>";
                    xml = xml + "<pflag>" + rs.getString(4) + "</pflag>";
                    xml = xml + "</display>";
                    xml = xml + "<flag>success</flag>";
                }
            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";
            }
        }

        if (cmd.equalsIgnoreCase("add")) {
            i = request.getParameter("id");
            nm = request.getParameter("desc");
            fmla = request.getParameter("frmla");
            flag = request.getParameter("pflag");
            System.out.println(i);
            xml = xml + "<command>add</command>";
            try {
                String sql;
                sql =
 "select * from WQS_MST_INV_CHEMICAL order by CHEMICAL_CODE asc";
                st = con.createStatement();
                String ss =
                    "insert into WQS_MST_INV_CHEMICAL(CHEMICAL_CODE,CHE_SPECIFICATION,CHE_FORMULA,PRIORITY_FLAG,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?)";
                ps = con.prepareStatement(ss);
                ps.setString(1, i);
                ps.setString(2, nm);
                ps.setString(3, fmla);
                ps.setString(4, flag);
                ps.setString(5, updatedby);
                ps.setTimestamp(6, ts);
                ps.executeUpdate();
                xml = xml + "<added>";
                xml = xml + "<id>" + i + "</id>";
                xml = xml + "<desc>" + nm + "</desc>";
                xml = xml + "<frmla>" + fmla + "</frmla>";
                xml = xml + "<pflag>" + flag + "</pflag>";
                xml = xml + "</added>";
                //}
                xml = xml + "<flag>success</flag>";
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    xml = xml + "<display>";
                    xml = xml + "<id>" + rs.getString(1) + "</id>";
                    xml = xml + "<desc>" + rs.getString(2) + "</desc>";
                    xml = xml + "<frmla>" + rs.getString(3) + "</frmla>";
                    xml = xml + "<pflag>" + rs.getString(4) + "</pflag>";
                    xml = xml + "</display>";
                }

            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";
            }

        } else if (cmd.equalsIgnoreCase("upd")) {
            i = request.getParameter("id");
            nm = request.getParameter("desc");
            fmla = request.getParameter("frmla");
            flag = request.getParameter("pflag");
            System.out.println(i);
            xml = xml + "<command>upd</command>";
            try {
                String ss =
                    "update WQS_MST_INV_CHEMICAL set CHE_SPECIFICATION=?,CHE_FORMULA=?,PRIORITY_FLAG=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where CHEMICAL_CODE=?";
                ps = con.prepareStatement(ss);
                ps.setString(1, nm);
                ps.setString(2, fmla);
                ps.setString(3, flag);
                ps.setString(4, updatedby);
                ps.setTimestamp(5, ts);
                ps.setString(6, i);
                ps.executeUpdate();
                xml = xml + "<updated>";
                xml = xml + "<id>" + i + "</id>";
                xml = xml + "<desc>" + nm + "</desc>";
                xml = xml + "<frmla>" + fmla + "</frmla>";
                xml = xml + "<pflag>" + flag + "</pflag>";
                xml = xml + "</updated>";

                String sql =
                    "select * from WQS_MST_INV_CHEMICAL order by CHEMICAL_CODE asc";
                st = con.createStatement();
                rs = st.executeQuery(sql);
                while (rs.next()) {
                    xml = xml + "<display>";
                    xml = xml + "<id>" + rs.getString(1) + "</id>";
                    xml = xml + "<desc>" + rs.getString(2) + "</desc>";
                    xml = xml + "<frmla>" + rs.getString(3) + "</frmla>";
                    xml = xml + "<pflag>" + rs.getString(4) + "</pflag>";
                    xml = xml + "</display>";
                }
                xml = xml + "<flag>success</flag>";
            } catch (Exception e) {
                System.out.println("Err in update:" + e.getMessage());
            }
        }

        else if (cmd.equalsIgnoreCase("del")) {
            i = request.getParameter("id");
            System.out.println(i);
            xml = xml + "<command>del</command>";
            try {

                ps =
  con.prepareStatement("select * from WQS_MST_INV_SUP_ITEM where ITEM_CODE=? and MAJOR_CATEGORY_CODE in('CHE')");
                ps.setString(1, i);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml = xml + "<flag>FoundData</flag>";
                } else {
                    String sql =
                        "select * from WQS_MST_INV_CHEMICAL order by CHEMICAL_CODE asc";
                    st = con.createStatement();

                    String ss =
                        "delete from WQS_MST_INV_CHEMICAL where CHEMICAL_CODE=?";
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
                        xml = xml + "<frmla>" + rs.getString(3) + "</frmla>";
                        xml = xml + "<pflag>" + rs.getString(4) + "</pflag>";
                        xml = xml + "</display>";
                    }
                    xml = xml + "<flag>success</flag>";
                }
            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";
            }
            //exists=0;
        } else {
            i = request.getParameter("id");
            System.out.println(i);
            xml = xml + "<command>duplicate</command>";
            try {
                PreparedStatement pstmt =
                    con.prepareStatement("select CHEMICAL_CODE from WQS_MST_INV_CHEMICAL where CHEMICAL_CODE=?");
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
        //out.println("<p>The servlet has received a GET. This is the reply.</p>");
        out.println("</body></html>");
        out.close();
    }
}
