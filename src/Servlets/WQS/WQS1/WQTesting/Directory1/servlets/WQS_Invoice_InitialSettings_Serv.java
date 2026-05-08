package Servlets.WQS.WQS1.WQTesting.Directory1.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class WQS_Invoice_InitialSettings_Serv extends HttpServlet {
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
        Statement st = null, st1 = null;
        PreparedStatement ps = null;
        ResultSet rs = null, rs1 = null;
        String cmd = null;
        String xml = null, updatedby = null, flag = "";
        Timestamp ts = null;
        int lab = 0, invoice = 0, count = 0;
        response.setHeader("Cache-Control", "no-cache");
        HttpSession session = request.getSession(false);
        session = request.getSession(false);
        updatedby = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        ts = new Timestamp(l);
        System.out.println(updatedby);
        System.out.println(ts);

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

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
            System.out.println("connected");
            session = request.getSession(false);
            st = con.createStatement();
            st1 = con.createStatement();
        } catch (Exception e) {
            System.out.println(e);
        }
        xml = "<response>";
        cmd = request.getParameter("command");
        if (cmd.equalsIgnoreCase("load")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            xml = xml + "<command>load</command>";
            try {
                String sql =
                    "select lab_code,invoice_number from wqs_invoice_number_settings where lab_code=" +
                    lab;
                st = con.createStatement();
                rs = st.executeQuery(sql);
                if (rs.next()) {
                    xml = xml + "<lab>" + rs.getString("lab_code") + "</lab>";
                    xml =
 xml + "<invoice>" + rs.getString("invoice_number") + "</invoice>";
                    xml = xml + "<flag>success</flag>";
                } else
                    xml = xml + "<flag>failure</flag>";

            } catch (Exception e) {
                System.out.println(e);
            }
        } else if (cmd.equalsIgnoreCase("add")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            invoice = Integer.parseInt(request.getParameter("invoice"));
            xml = xml + "<command>add</command>";
            try {
                String sql =
                    "select lab_code,invoice_number from wqs_invoice_number_settings where lab_code=" +
                    lab;
                st = con.createStatement();

                String ss =
                    "insert into wqs_invoice_number_settings(lab_code,invoice_number,updated_date,updated_by_user_id) values(?,?,?,?)";
                ps = con.prepareStatement(ss);
                ps.setInt(1, lab);
                ps.setInt(2, invoice);
                ps.setTimestamp(3, ts);
                ps.setString(4, updatedby);
                int i = ps.executeUpdate();
                if (i > 0) {
                    xml = xml + "<flag>success</flag>";
                    flag = "success";
                } else {
                    xml = xml + "<flag>failure</flag>";
                    flag = "failure";
                }
                System.out.println(flag);
                if (flag.equalsIgnoreCase("success")) {
                    System.out.println("flag:" + flag);
                    System.out.println(sql);
                    rs = st.executeQuery(sql);
                    if (rs.next()) {
                        xml =
 xml + "<lab>" + rs.getString("lab_code") + "</lab>";
                        xml =
 xml + "<invoice>" + rs.getString("invoice_number") + "</invoice>";
                    }
                }

            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";
            }
            //exists=0;
        } else if (cmd.equalsIgnoreCase("update")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            invoice = Integer.parseInt(request.getParameter("invoice"));
            xml = xml + "<command>update</command>";
            try {
                String sql =
                    "select lab_code,invoice_number from wqs_invoice_number_settings where lab_code=" +
                    lab;
                st = con.createStatement();
                String avail =
                    "select lab_code,invoice_number from wqs_invoice_details where lab_code=" +
                    lab;
                rs1 = st1.executeQuery(avail);
                if (!rs1.next()) {
                    xml = xml + "<avail>notavailable</avail>";
                    String ss =
                        "update wqs_invoice_number_settings set invoice_number=?,updated_date=?,updated_by_user_id=? where lab_code=?";
                    ps = con.prepareStatement(ss);
                    ps.setInt(1, invoice);
                    ps.setTimestamp(2, ts);
                    ps.setString(3, updatedby);
                    ps.setInt(4, lab);
                    int i = ps.executeUpdate();
                    if (i > 0) {
                        xml = xml + "<flag>success</flag>";
                        flag = "success";
                    } else {
                        xml = xml + "<flag>failure</flag>";
                        flag = "failure";
                    }
                    if (flag.equalsIgnoreCase("success")) {
                        rs = st.executeQuery(sql);
                        if (rs.next()) {
                            xml =
 xml + "<lab>" + rs.getString("lab_code") + "</lab>";
                            xml =
 xml + "<invoice>" + rs.getString("invoice_number") + "</invoice>";
                        }
                    }
                } else
                    xml = xml + "<avail>available</avail>";
            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";
            }
        } else if (cmd.equalsIgnoreCase("delete")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            xml = xml + "<command>delete</command>";
            try {
                String avail =
                    "select lab_code,invoice_number from wqs_invoice_details where lab_code=" +
                    lab;
                System.out.println(avail);
                rs1 = st1.executeQuery(avail);
                if (rs1.next()) {
                    xml = xml + "<avail>available</avail>";
                } else {
                    xml = xml + "<avail>notavailable</avail>";
                    String ss =
                        "delete from wqs_invoice_number_settings where lab_code=?";
                    ps = con.prepareStatement(ss);
                    ps.setInt(1, lab);
                    int i = ps.executeUpdate();
                    if (i > 0) {
                        xml = xml + "<flag>success</flag>";
                        flag = "success";
                    } else {
                        xml = xml + "<flag>failure</flag>";
                        flag = "failure";
                    }
                    System.out.println(flag);
                    if (flag.equalsIgnoreCase("success")) {
                        String sql =
                            "select lab_code,invoice_number from wqs_invoice_number_settings where lab_code=" +
                            lab;
                        rs = st.executeQuery(sql);
                        if (rs.next()) {
                            count++;
                            xml =
 xml + "<lab>" + rs.getString("lab_code") + "</lab>";
                            xml =
 xml + "<invoice>" + rs.getString("invoice_number") + "</invoice>";
                        }
                        xml = xml + "<count>" + count + "</count>";
                    }
                }
            } catch (Exception e) {
                System.out.println(e);
                xml = xml + "<flag>failure</flag>";
            }
        }
        xml = xml + "</response>";
        System.out.println(xml);
        out.println(xml);
        out.close();
    }
}
