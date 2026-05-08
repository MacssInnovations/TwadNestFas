package Servlets.WQS.WQS1.WQTesting.Directory1.servlets;

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

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class WQS_SampleEntry_ReValidateServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        String CONTENT_TYPE = "text/xml";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        response.setHeader("cache-control", "no-cache");
        Connection con = null;
        Statement st = null;
        PreparedStatement ps = null;
        ResultSet rs = null, rs3 = null;
        String xml = null, test_purpose = "";
        int lab = 0, ino = 0, count = 0, sno = 0;
        String updatedby = null;
        Timestamp ts = null;
        String test_purpose_id = "";
        System.out.println("welcome to servlet");
        try {
            ResourceBundle rs2 =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs2.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs2.getString("Config.DSN");
            String strhostname = rs2.getString("Config.HOST_NAME");
            String strportno = rs2.getString("Config.PORT_NUMBER");
            String strsid = rs2.getString("Config.SID");
            String strdbusername = rs2.getString("Config.USER_NAME");
            String strdbpassword = rs2.getString("Config.PASSWORD");

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
        String cmd = request.getParameter("command");
        System.out.println(cmd);
        if (cmd.equalsIgnoreCase("changeInvoice")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            ino = Integer.parseInt(request.getParameter("invoice"));
            xml = xml + "<command>changeInvoice</command>";
            try {
                String sql =
                    "select distinct a.invoice_number,customer_type,sample_number,date_of_collection,district_name,location from" +
                    "(select lab_code,invoice_number,customer_type from wqs_invoice_details where lab_code=? and invoice_number=? and process_flow_status_id='FR'" +
                    ")a inner join" +
                    "(select lab_code,invoice_number,sample_number,date_of_collection,district_code,location from wqs_sample_entry" +
                    ")b on a.lab_code=b.lab_code and a.invoice_number=b.invoice_number left outer join" +
                    "(select district_code,district_name from com_mst_districts" +
                    ")b2 on b.district_code=b2.district_code";
                System.out.println(sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, lab);
                ps.setInt(2, ino);
                rs = ps.executeQuery();
                while (rs.next()) {
                    test_purpose = "";
                    test_purpose_id = "";
                    count++;
                    xml = xml + "<row_count>";
                    xml =
 xml + "<ino>" + rs.getString("invoice_number") + "</ino>";
                    xml =
 xml + "<ctype>" + rs.getString("customer_type") + "</ctype>";
                    sno = rs.getInt("sample_number");
                    xml =
 xml + "<sample>" + rs.getString("sample_number") + "</sample>";
                    Date f = rs.getDate("date_of_collection");
                    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String rd = formatter.format(f);
                    xml = xml + "<collectiondate>" + rd + "</collectiondate>";
                    xml =
 xml + "<district>" + rs.getString("district_name") + "</district>";
                    xml =
 xml + "<location>" + rs.getString("location") + "</location>";
                    rs3 =
 st.executeQuery("select a.test_purpose_id,b.test_purpose from wqs_sample_entry a left outer join wqs_test_purpose b on a.test_purpose_id=b.test_purpose_id where lab_code=" +
                 lab + " and invoice_number=" + ino + " and sample_number=" +
                 sno);
                    while (rs3.next()) {
                        test_purpose_id +=
                                rs3.getString("test_purpose_id") + ",";
                        test_purpose += rs3.getString("test_purpose") + ",";
                    }
                    test_purpose_id =
                            test_purpose_id.substring(0, test_purpose_id.length() -
                                                      1);
                    test_purpose =
                            test_purpose.substring(0, test_purpose.length() -
                                                   1);
                    xml =
 xml + "<test_purpose_id>" + test_purpose_id + "</test_purpose_id>";
                    xml =
 xml + "<test_purpose>" + test_purpose + "</test_purpose>";
                    xml = xml + "</row_count>";
                }
                if (count > 0)
                    xml = xml + "<flag>Success</flag>";
                else
                    xml = xml + "<flag>Failure</flag>";
            } catch (Exception e) {
                System.out.println("Err in commit:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("ReValidate")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            ino = Integer.parseInt(request.getParameter("invoice_no"));
            xml = xml + "<command>ReValidate</command>";
            try {
                con.setAutoCommit(false);
                ps =
  con.prepareStatement("update wqs_invoice_details set process_flow_status_id='MD' where lab_code=? and invoice_number=?");
                ps.setInt(1, lab);
                ps.setInt(2, ino);
                int upd = ps.executeUpdate();
                if (upd > 0) {
                    ps.close();
                    System.out.println("Record UnFreezed successfully");

                    ps =
  con.prepareStatement("insert into wqs_sample_entry_log(lab_code,invoice_number,date_of_unfreezed,time_of_unfreezed)values(?,?,(select to_char(now(),'dd-mon-yyyy') ),(select to_char(now(),'HH24:MI:SS') ))");
                    ps.setInt(1, lab);
                    ps.setInt(2, ino);
                    int ins = ps.executeUpdate();
                    if (ins > 0) {
                        xml = xml + "<flag>success</flag>";
                        con.commit();
                    } else {
                        con.rollback();
                        xml = xml + "<flag>failure</flag>";
                    }
                } else {
                    System.out.println("Err in Record ReValidation");
                    con.rollback();
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                xml = xml + "<flag>exception</flag>";
                xml = xml + "<exception>" + e.getMessage() + "</exception>";
                System.out.println("Err in commit:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("load_invoice")) {
            int labcode = Integer.parseInt(request.getParameter("lab"));
            System.out.println("Lab code:" + labcode);
            xml = xml + "<command>load_invoice</command>";
            try {
                String sql =
                    "select lab_code,invoice_number,customer_type,total_samples,invoice_date,invoice_amount from wqs_invoice_details  where lab_code=? and process_flow_status_id='FR' order by invoice_number";
                System.out.println(sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, labcode);
                rs = ps.executeQuery();
                while (rs.next()) {
                    System.out.println("inside record printing");
                    count++;
                    xml = xml + "<count>";
                    xml =
 xml + "<ino>" + rs.getString("invoice_number") + "</ino>";
                    xml =
 xml + "<ctype>" + rs.getString("customer_type") + "</ctype>";
                    Date fdate = rs.getDate("invoice_date");
                    Format formatter;
                    formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String f = formatter.format(fdate);
                    xml = xml + "<idate>" + f + "</idate>";
                    xml =
 xml + "<amt>" + rs.getString("invoice_amount") + "</amt>";
                    xml = xml + "</count>";
                }
                if (count > 0)
                    xml = xml + "<flag>success</flag>";
                else
                    xml = xml + "<flag>failure</flag>";
            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println(e.getMessage());
            }
        }
        xml = xml + "</response>";
        System.out.println("xml is:" + xml);
        out.println(xml);
        out.close();
    }
}
