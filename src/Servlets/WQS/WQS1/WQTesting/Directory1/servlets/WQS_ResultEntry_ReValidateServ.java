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

public class WQS_ResultEntry_ReValidateServ extends HttpServlet {
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
        PreparedStatement ps = null, ps1 = null;
        ResultSet rs = null, rs1 = null;
        String xml = null, test_purpose = "", ctype = "";
        int lab = 0, ino = 0, sno = 0, count = 0;
        String updatedby = null;
        Timestamp ts = null;
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
                    "select distinct a.lab_code,a.invoice_number,sample_number,a.test_purpose_id,test_purpose,date_of_receipt,b.customer_type,district_name,location from" +
                    "(select lab_code,invoice_number,sample_number,test_purpose_id,date_of_receipt from wqs_watersample_result where lab_code=? and invoice_number=? and process_flow_status_id='FR' order by invoice_number" +
                    ")a left outer join" +
                    "(select lab_code,invoice_number,customer_type from wqs_invoice_details" +
                    ")b on a.lab_code=b.lab_code and a.invoice_number=b.invoice_number left outer join" +
                    "(select lab_code,invoice_number,sample_number,district_code,location from wqs_sample_entry" +
                    ")b1 on a.lab_code=b1.lab_code and a.invoice_number=b1.invoice_number and a.sample_number=b1.sample_number left outer join" +
                    "(select district_code,district_name from com_mst_districts" +
                    ")b2 on b1.district_code=b2.district_code left outer join" +
                    "(select test_purpose_id,test_purpose from wqs_test_purpose" +
                    ")c on a.test_purpose_id=c.test_purpose_id order by sample_number,test_purpose_id";
                System.out.println(sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, lab);
                ps.setInt(2, ino);
                rs = ps.executeQuery();
                while (rs.next()) {
                    count++;
                    xml = xml + "<row_count>";
                    xml =
 xml + "<ino>" + rs.getString("invoice_number") + "</ino>";
                    xml =
 xml + "<ctype>" + rs.getString("customer_type") + "</ctype>";
                    xml =
 xml + "<test_purpose_id>" + rs.getString("test_purpose_id") +
   "</test_purpose_id>";
                    xml =
 xml + "<test_purpose>" + rs.getString("test_purpose") + "</test_purpose>";
                    xml =
 xml + "<sample>" + rs.getString("sample_number") + "</sample>";
                    Date f = rs.getDate("date_of_receipt");
                    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String rd = formatter.format(f);
                    xml = xml + "<receiptdate>" + rd + "</receiptdate>";
                    xml =
 xml + "<district>" + rs.getString("district_name") + "</district>";
                    xml =
 xml + "<location>" + rs.getString("location") + "</location>";
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
            String sample_no[] = request.getParameter("sample_no").split(",");
            xml = xml + "<command>ReValidate</command>";
            try {
                con.setAutoCommit(false);
                for (int i = 0; i < sample_no.length; i++) {
                    ps =
  con.prepareStatement("update wqs_watersample_result set process_flow_status_id='MD' where lab_code=? and invoice_number=? and sample_number=?");
                    ps.setInt(1, lab);
                    ps.setInt(2, ino);
                    ps.setInt(3, Integer.parseInt(sample_no[i]));
                    int upd = ps.executeUpdate();
                    if (upd > 0)
                        count++;
                }
                if (count == sample_no.length) {
                    ps.close();
                    count = 0;
                    System.out.println("Record UnFreezed successfully");
                    for (int i = 0; i < sample_no.length; i++) {
                        ps =
  con.prepareStatement("insert into wqs_watersample_result_log(lab_code,invoice_number,sample_number,date_of_unfreezed,time_of_unfreezed)values(?,?,?,(select to_char(now(),'dd-mon-yyyy') ),(select to_char(now(),'HH24:MI:SS') ))");
                        ps.setInt(1, lab);
                        ps.setInt(2, ino);
                        ps.setInt(3, Integer.parseInt(sample_no[i]));
                        int ins = ps.executeUpdate();
                        if (ins > 0)
                            count++;
                    }
                    if (count == sample_no.length) {
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
                    "select distinct a.lab_code,a.invoice_number,customer_type from wqs_watersample_result a left outer join wqs_invoice_details b on a.lab_code=b.lab_code and a.invoice_number=b.invoice_number " +
                    "where a.lab_code=? and a.process_flow_status_id='FR' order by invoice_number";
                ps = con.prepareStatement(sql);
                ps.setInt(1, labcode);
                rs = ps.executeQuery();
                while (rs.next()) {
                    test_purpose = "";
                    ino = rs.getInt("invoice_number");
                    ctype = rs.getString("customer_type");
                    System.out.println("invoice======>" + ino);
                    System.out.println("ctype======>" + ctype);
                    sql =
 "select distinct a.test_purpose_id,b.test_purpose from wqs_watersample_result a left outer join wqs_test_purpose b on a.test_purpose_id=b.test_purpose_id where a.lab_code=? and a.invoice_number=? and process_flow_status_id='FR'";
                    ps1 = con.prepareStatement(sql);
                    ps1.setInt(1, labcode);
                    ps1.setInt(2, ino);
                    rs1 = ps1.executeQuery();
                    while (rs1.next()) {
                        test_purpose += rs1.getString("test_purpose") + ",";
                    }
                    rs1.close();
                    test_purpose =
                            test_purpose.substring(0, test_purpose.length() -
                                                   1);
                    count++;
                    xml = xml + "<count>";
                    xml = xml + "<ino>" + ino + "</ino>";
                    xml = xml + "<ctype>" + ctype + "</ctype>";
                    xml =
 xml + "<test_purpose>" + test_purpose + "</test_purpose>";
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
