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

import java.lang.String.*;

import java.sql.Timestamp;
import java.sql.Types;

import java.text.Format;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class wqs_invoice_details extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter pw = response.getWriter();

        response.setContentType(CONTENT_TYPE);
        String strCommand = "", flag = "";
        Calendar c;
        String xml = "", duedate = null, updatedby = null;
        Timestamp ts = null;
        Connection con = null;
        Statement st = null;
        ResultSet rs, rs1 = null;
        PreparedStatement ps = null;
        int ino = 0;
        try {
            ResourceBundle res =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = res.getString("Config.DATA_BASE_DRIVER");
            String strdsn = res.getString("Config.DSN");
            String strhostname = res.getString("Config.HOST_NAME");
            String strportno = res.getString("Config.PORT_NUMBER");
            String strsid = res.getString("Config.SID");
            String strdbusername = res.getString("Config.USER_NAME");
            String strdbpassword = res.getString("Config.PASSWORD");

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
            System.out.println("Exception in openeing connection:" + e);
        }

        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        HttpSession session = request.getSession(false);
        session = request.getSession(false);
        updatedby = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        ts = new Timestamp(l);
        System.out.println(updatedby);
        System.out.println(ts);


        strCommand = request.getParameter("command");
        System.out.println("command====================>" + strCommand);
        int labCode = Integer.parseInt(request.getParameter("lcode"));
        String ctype = request.getParameter("type");
        String cid = request.getParameter("cid");
        String invoice_date = request.getParameter("invoice_date");
        String amt = request.getParameter("amt");
        String paymode = request.getParameter("paymode");
        String che_num = request.getParameter("che_num");
        String che_date = request.getParameter("che_date");
        String bank_name = request.getParameter("bank_name");
        String branch = request.getParameter("branch");
        String rno = request.getParameter("rno");
        String due_date = request.getParameter("due_date");
        String tot_samples = request.getParameter("tot_samples");
        String rem = request.getParameter("rem");

        xml = "<response>";
        if (strCommand.equalsIgnoreCase("loadRecord")) {
            System.out.println("inside loadRecord");
            xml = xml + "<command>loadRecord</command>";
            try {
                String sql =
                    "select * from(" + "select a.LAB_CODE,a.INVOICE_NUMBER,a.CUSTOMER_TYPE,a.CUSTOMER_ID,a.INVOICE_DATE,a.INVOICE_AMOUNT,a.PAYMENT_MODE,a.CHEQUE_NUMBER,a.CHEQUE_DATE,a.BANK_NAME,a.DRAWEE_BRANCH,a.CUSTOMER_REF_NO,a.REPORT_DUE_DATE,a.TOTAL_SAMPLES,a.REMARKS,b.NAME from" +
                    "(select LAB_CODE,INVOICE_NUMBER,CUSTOMER_TYPE,CUSTOMER_ID,INVOICE_DATE,INVOICE_AMOUNT,PAYMENT_MODE,CHEQUE_NUMBER,CHEQUE_DATE,BANK_NAME,DRAWEE_BRANCH,CUSTOMER_REF_NO,REPORT_DUE_DATE,TOTAL_SAMPLES,REMARKS FROM WQS_INVOICE_DETAILS WHERE LAB_CODE=?" +
                    ")a left outer join" +
                    "(select LAB_CODE,CUSTOMER_TYPE,CUSTOMER_ID,NAME from WQS_CUSTOMER" +
                    ")b on a.LAB_CODE=b.LAB_CODE and a.CUSTOMER_TYPE=b.CUSTOMER_TYPE and a.CUSTOMER_ID=b.CUSTOMER_ID order by a.INVOICE_NUMBER" +
                    ")aa left outer join" +
                    "(select LAB_CODE,count(*)as TOTAL from WQS_INVOICE_DETAILS GROUP BY LAB_CODE" +
                    ")bb on aa.LAB_CODE=bb.LAB_CODE";
                System.out.println("SQL:" + sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, labCode);
                rs = ps.executeQuery();
                int i = 0;
                while (rs.next()) {
                    i++;
                    xml = xml + "<count>" + rs.getInt("TOTAL") + "</count>";
                    xml = xml + "<display>";
                    xml = xml + "<lcode>" + rs.getInt("LAB_CODE") + "</lcode>";
                    xml =
 xml + "<ino>" + rs.getInt("INVOICE_NUMBER") + "</ino>";
                    System.out.println("customer type:" +
                                       rs.getString("CUSTOMER_TYPE"));
                    xml =
 xml + "<ctype>" + rs.getString("CUSTOMER_TYPE") + "</ctype>";
                    System.out.println("customer id:" +
                                       rs.getString("CUSTOMER_ID"));
                    xml =
 xml + "<cid>" + rs.getString("CUSTOMER_ID") + "</cid>";
                    Date idate = rs.getDate("INVOICE_DATE");
                    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String indate = formatter.format(idate);
                    xml = xml + "<date>" + indate + "</date>";
                    xml =
 xml + "<amt>" + rs.getString("INVOICE_AMOUNT") + "</amt>";
                    xml =
 xml + "<mode>" + rs.getString("PAYMENT_MODE") + "</mode>";
                    xml =
 xml + "<cnum>" + rs.getString("CHEQUE_NUMBER") + "</cnum>";
                    Date cdate = rs.getDate("CHEQUE_DATE");
                    String chq_date = null;
                    if (cdate == null)
                        chq_date = "-";
                    else
                        chq_date = formatter.format(cdate);
                    xml = xml + "<chqdate>" + chq_date + "</chqdate>";
                    xml =
 xml + "<bank>" + rs.getString("BANK_NAME") + "</bank>";
                    xml =
 xml + "<branch>" + rs.getString("DRAWEE_BRANCH") + "</branch>";
                    xml =
 xml + "<rno>" + rs.getString("CUSTOMER_REF_NO") + "</rno>";
                    Date idate1 = rs.getDate("REPORT_DUE_DATE");
                    if (idate1 == null)
                        duedate = "-";
                    else
                        duedate = formatter.format(idate1);
                    xml = xml + "<ddate>" + duedate + "</ddate>";
                    xml =
 xml + "<samples>" + rs.getString("TOTAL_SAMPLES") + "</samples>";
                    xml = xml + "<rem>" + rs.getString("REMARKS") + "</rem>";
                    xml = xml + "<name>" + rs.getString("NAME") + "</name>";
                    xml = xml + "</display>";
                }
                if (i > 0)
                    xml = xml + "<flag>Success</flag>";
                else
                    xml = xml + "<flag>Failure</flag>";
            } catch (Exception se) {
                xml = xml + "<flag>Failure</flag>";
            }
        } else if (strCommand.equalsIgnoreCase("changeId")) {
            xml = xml + "<command>changeId</command>";
            try {
                String sql =
                    "select name from wqs_customer where lab_code=? and customer_type=? and customer_id=?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, labCode);
                ps.setString(2, ctype);
                ps.setString(3, cid);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml = xml + "<cname>" + rs.getString("name") + "</cname>";
                    xml = xml + "<flag>Success</flag>";
                } else
                    xml = xml + "<flag>Failure</flag>";
            } catch (Exception se) {
                System.out.println("not get" + se);
                xml = xml + "<flag>Failure</flag>";
            }
        } else if (strCommand.equalsIgnoreCase("Add")) {
            xml = xml + "<command>Add</command>";
            System.out.println("inside add");
            try {
                rs =
  st.executeQuery("SELECT INVOICE_NUMBER FROM WQS_INVOICE_DETAILS GROUP BY INVOICE_NUMBER HAVING INVOICE_NUMBER =(select max(INVOICE_NUMBER) from WQS_INVOICE_DETAILS where LAB_CODE=" +
                  labCode + ")");
                if (rs.next()) {
                    ino = rs.getInt(1);
                    ino = ino + 1;
                    xml = xml + "<invoice>available</invoice>";
                    flag = "success";
                } else {
                    rs1 =
 st.executeQuery("select invoice_number from wqs_invoice_number_settings where lab_code=" +
                 labCode);
                    if (rs1.next()) {
                        ino = rs1.getInt(1);
                        xml = xml + "<invoice>available</invoice>";
                        flag = "success";
                    } else {
                        xml = xml + "<invoice>not_available</invoice>";
                        flag = "failure";
                    }
                }
                System.out.println("invoice number==========>" + ino);
                if (flag.equalsIgnoreCase("success")) {
                    String sql =
                        "insert into WQS_INVOICE_DETAILS(LAB_CODE,INVOICE_NUMBER,CUSTOMER_TYPE,CUSTOMER_ID,INVOICE_DATE,INVOICE_AMOUNT,PAYMENT_MODE,CHEQUE_NUMBER,CHEQUE_DATE,BANK_NAME,DRAWEE_BRANCH,CUSTOMER_REF_NO,REPORT_DUE_DATE,TOTAL_SAMPLES,REMARKS,PROCESS_FLOW_STATUS_ID,UPDATED_DATE,UPDATED_BY_USER_ID) values(?,?,?,?,to_date(?,'dd/MM/yyyy'),?,?,?,?,?,?,?,to_date(?,'dd/MM/yyyy'),?,?,?,?,?)";
                    ps = con.prepareStatement(sql);
                    ps.setInt(1, labCode);
                    ps.setInt(2, ino);
                    ps.setString(3, ctype);
                    ps.setString(4, cid);
                    ps.setString(5, invoice_date);
                    ps.setInt(6, Integer.parseInt(amt));
                    ps.setString(7, paymode);
                    ps.setString(8, che_num);
                    if (che_date == null || che_date.equalsIgnoreCase("")) {
                        System.out.println("date is null");
                        ps.setNull(9, Types.DATE);
                    } else {
                        String[] od = che_date.split("/");
                        c =
   new GregorianCalendar(Integer.parseInt(od[2]), Integer.parseInt(od[1]) - 1,
                         Integer.parseInt(od[0]));
                        java.util.Date d = c.getTime();
                        Date cdate = new Date(d.getTime());
                        ps.setDate(9, cdate);
                    }
                    ps.setString(10, bank_name);
                    ps.setString(11, branch);
                    ps.setString(12, rno);
                    ps.setString(13, due_date);
                    ps.setInt(14, Integer.parseInt(tot_samples));
                    ps.setString(15, rem);
                    ps.setString(16, "CR");
                    ps.setTimestamp(17, ts);
                    ps.setString(18, updatedby);
                    int num = ps.executeUpdate();
                    if (num > 0) {
                        System.out.println("Inserted Successfully");
                        xml = xml + "<invoice_no>" + ino + "</invoice_no>";
                        xml = xml + "<flag>Success</flag>";
                    } else {
                        System.out.println("insertion failure");
                        xml = xml + "<flag>failure</flag>";
                    }
                }

            } catch (Exception e) {
                System.out.println("Err in add==========>" + e.getMessage());
            }
        } else if (strCommand.equalsIgnoreCase("Update")) {
            xml = xml + "<command>Update</command>";
            ino = Integer.parseInt(request.getParameter("ino"));
            try {
                ps =
  con.prepareStatement("update wqs_invoice_details set customer_type=?,customer_id=?,invoice_date=to_date(?,'dd/MM/yyyy'),invoice_amount=?,payment_mode=?,cheque_number=?,cheque_date=?,bank_name=?,drawee_branch=?,customer_ref_no=?,report_due_date=to_date(?,'dd/MM/yyyy'),total_samples=?,remarks=?,updated_date=?,updated_by_user_id=? where lab_code=? and invoice_number=?");
                ps.setString(1, ctype);
                ps.setString(2, cid);
                ps.setString(3, invoice_date);
                ps.setInt(4, Integer.parseInt(amt));
                ps.setString(5, paymode);
                ps.setString(6, che_num);
                if (che_date == null || che_date.equalsIgnoreCase("")) {
                    System.out.println("date is null");
                    ps.setNull(7, Types.DATE);
                } else {
                    String[] od = che_date.split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(od[2]), Integer.parseInt(od[1]) - 1,
                         Integer.parseInt(od[0]));
                    java.util.Date d = c.getTime();
                    Date cdate = new Date(d.getTime());
                    ps.setDate(7, cdate);
                }
                ps.setString(8, bank_name);
                ps.setString(9, branch);
                ps.setString(10, rno);
                if (due_date == null || due_date.equalsIgnoreCase("")) {
                    System.out.println("due_date is null");
                    ps.setNull(11, Types.DATE);
                } else {
                    String[] od1 = due_date.split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(od1[2]), Integer.parseInt(od1[1]) -
                         1, Integer.parseInt(od1[0]));
                    java.util.Date d1 = c.getTime();
                    Date ddate = new Date(d1.getTime());
                    ps.setDate(11, ddate);
                }
                ps.setInt(12, Integer.parseInt(tot_samples));
                ps.setString(13, rem);
                ps.setTimestamp(14, ts);
                ps.setString(15, updatedby);
                ps.setInt(16, labCode);
                ps.setInt(17, ino);
                int num = ps.executeUpdate();
                if (num > 0) {
                    System.out.println("Updated Successfully");
                    xml = xml + "<flag>Success</flag>";
                } else {
                    xml = xml + "<flag>Failure</flag>";
                }

            } catch (Exception e) {
                System.out.println("Err in updation==========>" +
                                   e.getMessage());
            }
        } else if (strCommand.equalsIgnoreCase("checkDelete")) {
            xml = xml + "<command>checkDelete</command>";

            ino = Integer.parseInt(request.getParameter("ino"));
            try {
                String qry =
                    "select a.invoice_number,total from" + "(select lab_code,invoice_number from wqs_invoice_details where lab_code=? and invoice_number>?" +
                    ")a full join" +
                    "(select lab_code,invoice_number,count(sample_number)as total from wqs_sample_entry where invoice_number=? group by lab_code,invoice_number" +
                    ")b on a.lab_code=b.lab_code";
                ps = con.prepareStatement(qry);
                ps.setInt(1, labCode);
                ps.setInt(2, ino);
                ps.setInt(3, ino);
                rs = ps.executeQuery();
                if (rs.next()) {
                    System.out.println("total:" + rs.getString("total"));
                    if (rs.getInt("total") == 0 ||
                        rs.getString("total") == null) {
                        System.out.println("invoice:" +
                                           rs.getString("invoice_number"));
                        if (rs.getString("invoice_number") == null)
                            xml = xml + "<flag>Possible</flag>";
                        else
                            xml = xml + "<flag>NextRecordAvailble</flag>";
                    } else
                        xml = xml + "<flag>NotPossible</flag>";
                } else
                    xml = xml + "<flag>Possible</flag>";
            } catch (Exception e) {
                System.out.println("Err in updation==========>" +
                                   e.getMessage());
            }
        } else if (strCommand.equalsIgnoreCase("Delete")) {
            xml = xml + "<command>Delete</command>";

            ino = Integer.parseInt(request.getParameter("ino"));
            try {
                System.out.println("Inside delete");
                ps =
  con.prepareStatement("delete from wqs_invoice_details where lab_code=? and invoice_number=? and process_flow_status_id in('CR','MD')");
                ps.setInt(1, labCode);
                ps.setInt(2, ino);
                int num = ps.executeUpdate();
                if (num > 0) {
                    System.out.println("Deleted Successfully");
                    xml = xml + "<flag>Success</flag>";
                } else {
                    System.out.println("Err in Deletion");
                    xml = xml + "<flag>Failure</flag>";
                }

            } catch (Exception e) {
                System.out.println("Err in updation==========>" +
                                   e.getMessage());
            }
        } else if (strCommand.equalsIgnoreCase("load_customer")) {
            xml = xml + "<command>load_customer</command>";
            int i = 0;
            try {
                String sql =
                    "select lab_code,customer_id,customer_type," + "(case when customer_type not in('Twad','Twad Staff','Twad Student','Local Body') then to_number(substr(trim(customer_id),(codelength+1),length(trim(customer_id))))" +
                    "      else to_number(customer_id) end" +
                    ")as code,name,address from" +
                    "(select lab_code,customer_id,customer_type,name,address from wqs_customer where lab_code=? and customer_type=?" +
                    ")a inner join" +
                    "(select type_name,length(trim(customer_code)) as codelength from wqs_customer_type" +
                    ")b on a.customer_type=b.type_name order by customer_type,code";
                System.out.println("load qry: " + sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, labCode);
                ps.setString(2, ctype);
                rs = ps.executeQuery();
                while (rs.next()) {
                    i++;
                    xml = xml + "<count>";
                    xml =
 xml + "<cid>" + rs.getString("customer_id") + "</cid>";
                    xml = xml + "<name>" + rs.getString("name") + "</name>";
                    xml =
 xml + "<address>" + rs.getString("address") + "</address>";
                    xml = xml + "</count>";
                }
                if (i > 0)
                    xml = xml + "<flag>success</flag>";
                else
                    xml = xml + "<flag>failure</flag>";
            } catch (Exception e) {
                System.out.println("Err in load customer:" + e.getMessage());
            }
        } else if (strCommand.equalsIgnoreCase("checkAvail")) {
            xml = xml + "<command>checkAvail</command>";
            ino = Integer.parseInt(request.getParameter("invoice"));
            try {
                rs =
  st.executeQuery("select a.lab_code,a.process_flow_status_id,b.invoice_number from" +
                  "(select * from wqs_invoice_details where lab_code=" +
                  labCode + " and invoice_number=" + ino +
                  " and process_flow_status_id!='CR'" + ")a left outer join" +
                  "(select distinct lab_code,invoice_number from wqs_watersample_result" +
                  ")b on a.lab_code=b.lab_code and a.invoice_number=b.invoice_number");
                if (rs.next()) {
                    xml = xml + "<avail>yes</avail>";
                    rno = rs.getString("invoice_number");
                    System.out.println("invoice number:" + rno);
                    if (rno == null)
                        xml = xml + "<entry>no</entry>";
                    else {
                        xml = xml + "<entry>yes</entry>";
                        if (rs.getString("process_flow_status_id").equalsIgnoreCase("MD"))
                            xml = xml + "<unfreezed>yes</unfreezed>";
                        else
                            xml = xml + "<unfreezed>no</unfreezed>";
                    }
                } else
                    xml = xml + "<avail>no</avail>";
            } catch (Exception e) {
                System.out.println("Err in checkAvail:" + e.getMessage());
            }
        }
        xml = xml + "</response>";
        System.out.println("xml: " + xml);
        pw.println(xml);

    }
}
