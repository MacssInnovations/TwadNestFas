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

import java.sql.Types;

import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class WQS_ResultEntry_ValidateServ extends HttpServlet {
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
        String xml = null, result = null, test_purpose = "", purpose_id =
            "", param = "", test_purpose_id = "", ctype = "";
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
                    "select distinct a.lab_code,a.invoice_number,a.sample_number,a.test_purpose_id,test_purpose,b.customer_type,b.total_samples,invoice_amount,district_name,location,date_of_receipt from" +
                    "(select lab_code,invoice_number,sample_number,test_purpose_id,date_of_receipt from wqs_watersample_result where lab_code=? and invoice_number=? and process_flow_status_id in('MD','CR') order by invoice_number" +
                    ")a left outer join" +
                    "(select lab_code,invoice_number,customer_type,total_samples,invoice_amount from wqs_invoice_details" +
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
 xml + "<total_samples>" + rs.getString("total_samples") + "</total_samples>";
                    xml =
 xml + "<amt>" + rs.getString("invoice_amount") + "</amt>";
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
        } else if (cmd.equalsIgnoreCase("changeSample")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            ino = Integer.parseInt(request.getParameter("invoice"));
            sno = Integer.parseInt(request.getParameter("sample"));
            purpose_id = request.getParameter("test_purpose");
            xml = xml + "<command>changeSample</command>";
            try {
                String sql =
                    ("select distinct a.lab_code,a.invoice_number,b.collection_time,a.sample_number,date_of_collection,date_of_receipt,receipt_time,b.district_code,district_name,location_type,local_body_code,block_code,panchayat_code,habitation_code,d.scheme_type_name,e.water_source_type,f.description,g.programme_desc,b.sampling_point,b.location,cnc,pnp,reason,\n" +
                     "(case when block_code is not null then (select blockname from com_mst_blocks where district_code=b.district_code and block_code=b.block_code) else '-' end)as b_name,\n" +
                     "(case when panchayat_code is not null then (select panchayatname from com_mst_panchayats where district_code=b.district_code and block_code=b.block_code and panch_code=b.panchayat_code) else '-' end)as p_name,\n" +
                     "(case when habitation_code is not null then (select hname from com_mst_habitations where district_code=b.district_code and block_code=b.block_code and panch_code=b.panchayat_code and hab_code=b.habitation_code) else '-' end)as h_name,\n" +
                     "(case when location_type!='VP' and local_body_code is not null then\n" +
                     "      (case when location_type='Municipality' then (select municipality_eng from pms_mst_municipality where district_code=b.district_code and mucode=b.local_body_code)\n" +
                     "            when location_type='Corporation' then (select corporation_eng from pms_corporation where district_code=b.district_code and corp_code=b.local_body_code)\n" +
                     "            when location_type='UTP' then (select tpname from pms_mst_town_panchayats where dcode=b.district_code and tpcode=b.local_body_code and type='U')\n" +
                     "            when location_type='RTP' then (select tpname from pms_mst_town_panchayats where dcode=b.district_code and tpcode=b.local_body_code and type='R') \n" +
                     "            when location_type is null then '-' end\n" +
                     "      )else '-' end\n" + ")as l_name\n" + "from\n" +
                     "(select lab_code,invoice_number,sample_number,test_purpose_id,date_of_receipt,receipt_time from wqs_watersample_result where lab_code=? and invoice_number=? and sample_number=? and test_purpose_id=? and process_flow_status_id in('CR','MD')\n" +
                     ")a left outer join\n" +
                     "(select lab_code,invoice_number,sample_number,test_purpose_id,date_of_collection,district_code,location_type,local_body_code,block_code,panchayat_code,habitation_code,scheme_type,source_type,source_code,programme,sampling_point,location,collection_time,cnc,pnp,reason from wqs_sample_entry\n" +
                     ")b on a.lab_code=b.lab_code and a.invoice_number=b.invoice_number and a.sample_number=b.sample_number and a.test_purpose_id=b.test_purpose_id left outer join\n" +
                     "(select scheme_type_id,scheme_type_name from rws_mst_scheme_types\n" +
                     ")d on b.scheme_type=d.scheme_type_id left outer join\n" +
                     "(select water_source_type_id,water_source_type from rws_mst_water_source_type\n" +
                     ")e on b.source_type=e.water_source_type_id left outer join\n" +
                     "(select source_type,source_code,description from pms_source_code\n" +
                     ")f on b.source_type=f.source_type and b.source_code=f.source_code left outer join\n" +
                     "(select programme_code,programme_desc from mode_of_programme\n" +
                     ")g on b.programme=g.programme_code left outer join\n" +
                     "(select district_code,district_name from com_mst_districts\n" +
                     ")h on b.district_code=h.district_code");
                System.out.println(sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, lab);
                ps.setInt(2, ino);
                ps.setInt(3, sno);
                ps.setString(4, purpose_id);
                rs = ps.executeQuery();
                if (rs.next()) {
                    test_purpose = "";
                    test_purpose_id = "";
                    Date f = rs.getDate("date_of_collection");
                    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String cdate = formatter.format(f);
                    Date r = rs.getDate("date_of_receipt");
                    String rdate = formatter.format(r);
                    xml = xml + "<rdate>" + cdate + "</rdate>";
                    xml = xml + "<cdate>" + cdate + "</cdate>";
                    xml =
 xml + "<rtime>" + rs.getString("receipt_time") + "</rtime>";
                    xml =
 xml + "<ctime>" + rs.getString("collection_time") + "</ctime>";
                    xml =
 xml + "<dcode>" + rs.getString("district_name") + "</dcode>";
                    if (rs.getString("location_type") != null)
                        xml =
 xml + "<ltype>" + rs.getString("location_type") + "</ltype>";
                    else
                        xml = xml + "<ltype>-</ltype>";
                    xml =
 xml + "<lcode>" + rs.getString("l_name") + "</lcode>";
                    xml =
 xml + "<bcode>" + rs.getString("b_name") + "</bcode>";
                    xml =
 xml + "<pcode>" + rs.getString("p_name") + "</pcode>";
                    xml =
 xml + "<hcode><![CDATA[" + rs.getString("h_name") + "]]></hcode>";

                    if (rs.getString("scheme_type_name") != null)
                        xml =
 xml + "<stype>" + rs.getString("scheme_type_name") + "</stype>";
                    else
                        xml = xml + "<stype>-</stype>";
                    if (rs.getString("water_source_type") != null)
                        xml =
 xml + "<s_type>" + rs.getString("water_source_type") + "</s_type>";
                    else
                        xml = xml + "<s_type>-</s_type>";
                    if (rs.getString("description") != null)
                        xml =
 xml + "<scode>" + rs.getString("description") + "</scode>";
                    else
                        xml = xml + "<scode>-</scode>";
                    if (rs.getString("programme_desc") != null)
                        xml =
 xml + "<programme>" + rs.getString("programme_desc") + "</programme>";
                    else
                        xml = xml + "<programme>-</programme>";
                    System.out.println("sampling point:===========>" +
                                       rs.getString("sampling_point"));
                    if (rs.getString("sampling_point") != null)
                        xml =
 xml + "<spoint>" + rs.getString("sampling_point") + "</spoint>";
                    else
                        xml = xml + "<spoint>-</spoint>";
                    if (rs.getString("cnc") != null)
                        xml = xml + "<cnc>" + rs.getString("cnc") + "</cnc>";
                    else
                        xml = xml + "<cnc>-</cnc>";
                    if (rs.getString("pnp") != null)
                        xml = xml + "<pnp>" + rs.getString("pnp") + "</pnp>";
                    else
                        xml = xml + "<pnp>-</pnp>";
                    if (rs.getString("reason") != null)
                        xml =
 xml + "<reason>" + rs.getString("reason") + "</reason>";
                    else
                        xml = xml + "<reason>-</reason>";
                    xml =
 xml + "<location>" + rs.getString("location") + "</location>";
                    rs1 =
 st.executeQuery("select distinct a.test_purpose_id,test_purpose from wqs_watersample_result a left outer join wqs_test_purpose b on a.test_purpose_id=b.test_purpose_id where lab_code=" +
                 lab + " and invoice_number=" + ino + " and sample_number=" +
                 sno + " and process_flow_status_id in('MD','CR')");
                    System.out.println("QRY: select distinct a.test_purpose_id,test_purpose from wqs_watersample_result a left outer join wqs_test_purpose b on a.test_purpose_id=b.test_purpose_id where lab_code=" +
                                       lab + " and invoice_number=" + ino +
                                       " and sample_number=" + sno +
                                       " and process_flow_status_id in('MD','CR')");
                    while (rs1.next()) {
                        test_purpose_id +=
                                rs1.getString("test_purpose_id") + ",";
                        test_purpose += rs1.getString("test_purpose") + ",";
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
                    xml = xml + "<flag>success</flag>";
                } else
                    xml = xml + "<flag>failure</flag>";
            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("Err in commit:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("UpdateParameter")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            ino = Integer.parseInt(request.getParameter("invoice"));
            sno = Integer.parseInt(request.getParameter("sample"));
            purpose_id = request.getParameter("test_purpose_id");
            test_purpose = request.getParameter("test_purpose");
            CONTENT_TYPE = "text/html";
            String val = null, pid = "";
            response.setContentType(CONTENT_TYPE);
            String html = "";
            try {
                st = con.createStatement();
                if (purpose_id.equalsIgnoreCase("SEW") ||
                    purpose_id.equalsIgnoreCase("EFF"))
                    pid = "DRI";
                else
                    pid = purpose_id;
                rs =
  st.executeQuery("Select ELEMENT_SYMBOL from WQS_ELEMENT_SYMBOL where TEST_PURPOSE_ID='" +
                  pid + "' order by SNO");
                int i = 0, k = 0;
                html =
"<table cellpadding=0 cellspacing=0 border=0 width='110%'>";
                html = html + "<tr><td colspan=9 class='tdH'>" + test_purpose;
                html = html + "</td></tr>";
                html = html + "<tr>";
                while (rs.next()) {
                    val = rs.getString("ELEMENT_SYMBOL");
                    System.out.println("Parameter========>" + val);
                    ps =
  con.prepareStatement("select parameter,(case when result_in_percentage='Y' then result||'%' else result end)as result from wqs_watersample_result where lab_code=? and invoice_number=? and sample_number=? and test_purpose_id=? and parameter=?");
                    ps.setInt(1, lab);
                    ps.setInt(2, ino);
                    ps.setInt(3, sno);
                    ps.setString(4, purpose_id);
                    ps.setString(5, val);
                    rs1 = ps.executeQuery();
                    if (k != 0 && k % 3 == 0) {
                        html = html + "</tr><tr>";
                        System.out.println("New Row");
                    }
                    if (rs1.next()) {
                        result = rs1.getString("result");
                        System.out.println("Result:" + result);
                        if (result != null) {
                            html =
html + "<td><input type='checkbox' name='chkparameter' id='chkparameter' value='" +
 val + "' disabled='true' checked></td>";
                            html = html + "<td>" + val + "</td>";
                            html =
html + "<td><input type='text' name='paramval' id='paramval' style='white' value='" +
 result + "' maxlength=20 disabled='true'></td>";
                        }
                    } else {
                        html =
html + "<td><input type='checkbox' name='chkparameter' id='chkparameter' value='" +
 val + "' onclick='checkparam(" + k + ")' disabled='true'></td>";
                        html = html + "<td>" + val + "</td>";
                        html =
html + "<td><input type='text' name='paramval' id='paramval' disabled='disabled' style='background-color:rgb(214,214,214)' maxlength=20 disabled='true'></td>";
                    }
                    k++;
                }
                rs.close();
                rs1.close();
                html = html + "</tr>";
                System.out.println("welcome to bio");
                int z = 0;
                rs1 =
 st.executeQuery("select * from wqs_watersample_result where lab_code=" + lab +
                 " and invoice_number=" + ino + " and sample_number=" + sno +
                 " and test_purpose_id='" + purpose_id + "'");
                while (rs1.next()) {
                    System.out.println("inside bio ");
                    System.out.println(rs1.getString("test_purpose_id").equals("BIO") &&
                                       rs1.getString("result") == null);
                    if (rs1.getString("test_purpose_id").equals("BIO") &&
                        rs1.getString("result") == null) {
                        System.out.println("k===========>" + k +
                                           " Parameter:========>" +
                                           rs1.getString("parameter"));
                        param += rs1.getString("parameter") + "//";
                        System.out.println("parameter:" + param);
                        z++;
                    }
                }
                if (z > 0) {
                    param = param.substring(0, param.length() - 2);
                    System.out.println("After filtering:" + param);
                    html = html + "<tr><td colspan=9></td></tr>";
                    html =
html + "<tr><td colspan=9><a id='addalgea' name='addalgea' disabled='true'><u>Algae</u></a></td>";
                    html =
html + "<tr><td colspan=9><div id='algaeDiv' width='100%' style='display:none'>";
                    html =
html + "<input type='text' name='algae' id='algae' value='" + param +
 "' size=25 disabled='true'></td>";
                    html = html + "</div></td></tr>";
                }
                html = html + "</table>";
                System.out.println("html:" + html);
            } catch (Exception e) {
                System.out.println("Region selection error " + e);
                System.out.println("html:" + html);
            }
            out.print(html);
        } else if (cmd.equalsIgnoreCase("Validate")) {
            System.out.println("inside validate");
            lab = Integer.parseInt(request.getParameter("lab"));
            ino = Integer.parseInt(request.getParameter("invoice_no"));
            String sample_no[] = request.getParameter("sample_no").split(",");
            xml = xml + "<command>Validate</command>";
            try {
                con.setAutoCommit(false);
                for (int i = 0; i < sample_no.length; i++) {
                    ps =
  con.prepareStatement("update wqs_watersample_result set process_flow_status_id='FR' where lab_code=? and invoice_number=? and sample_number=?");
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
                    System.out.println("Record Validated successfully");
                    for (int i = 0; i < sample_no.length; i++) {
                        ps =
  con.prepareStatement("insert into wqs_watersample_result_log(lab_code,invoice_number,sample_number,date_of_freezed,time_of_freezed)values(?,?,?,(select to_char(now(),'dd-mon-yyyy') ),(select to_char(now(),'HH24:MI:SS') ))");
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
                    System.out.println("Err in Record Validation");
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
            xml = xml + "<command>load_invoice</command>";
            try {
                String sql =
                    "select distinct a.lab_code,a.invoice_number,customer_type from wqs_watersample_result a left outer join wqs_invoice_details b on a.lab_code=b.lab_code and a.invoice_number=b.invoice_number " +
                    "where a.lab_code=? and a.process_flow_status_id in('CR','MD') order by invoice_number";
                ps = con.prepareStatement(sql);
                ps.setInt(1, labcode);
                rs = ps.executeQuery();
                while (rs.next()) {
                    test_purpose = "";
                    ino = rs.getInt("invoice_number");
                    ctype = rs.getString("customer_type");
                    System.out.println("invoice======>" + ino);
                    //System.out.println("sample======>"+sno);
                    sql =
 "select distinct a.test_purpose_id,b.test_purpose from wqs_watersample_result a left outer join wqs_test_purpose b on a.test_purpose_id=b.test_purpose_id where a.lab_code=? and a.invoice_number=? and process_flow_status_id in('CR','MD')";
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
