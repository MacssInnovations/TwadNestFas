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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class WQS_ResultEntry_Serv extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        String CONTENT_TYPE = "text/xml";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        response.setHeader("cache-control", "no-cache");
        Connection con = null;
        Statement st = null;
        PreparedStatement ps = null, ps1 = null;
        ResultSet rs = null, rs1 = null;
        String xml = null, test_purpose = "";
        String test_purpose_id = "";
        Calendar c;
        Date rdate = null;
        int lab = 0, ino = 0, sno = 0, count = 0, records = 0;
        String updatedby = null, flag = null, cnc = null, pnp = null, reason =
            null, purpose_id = null;
        String percent[] = null;
        Timestamp ts = null;
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
                    "select distinct a.sample_number,total_invoice from   \n" +
                    "(select lab_code,invoice_number,sample_number,test_purpose_id from wqs_sample_entry   \n" +
                    ")a inner join   \n" +
                    "(select lab_code,invoice_number,process_flow_status_id from wqs_invoice_details  \n" +
                    ")b on a.lab_code=b.lab_code and a.invoice_number=b.invoice_number left outer join\n" +
                    "(select count(invoice_number)as total_invoice,test_purpose_id,sample_number,invoice_number,lab_code from wqs_watersample_result group by lab_code,invoice_number,sample_number,test_purpose_id\n" +
                    ")c on a.lab_code=c.lab_code and a.invoice_number=c.invoice_number and a.sample_number=c.sample_number and a.test_purpose_id=c.test_purpose_id \n" +
                    "where a.lab_code=? and a.invoice_number=? and b.process_flow_status_id=? and total_invoice is null ";
                System.out.println(sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, lab);
                ps.setInt(2, ino);
                ps.setString(3, "FR");
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml = xml + "<flag>Success</flag>";
                } else
                    xml = xml + "<flag>Failure</flag>";
            } catch (Exception e) {
                System.out.println("Err in commit:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("changeSample")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            ino = Integer.parseInt(request.getParameter("invoice"));
            sno = Integer.parseInt(request.getParameter("sample"));
            xml = xml + "<command>changeSample</command>";
            try {
                String qry =
                    "select distinct a.lab_code,a.invoice_number,a.sample_number,a.date_of_collection,a.district_code,a.location_type,a.local_body_code,a.block_code,a.panchayat_code,a.habitation_code \n" +
                    ",c.scheme_type_name,d.water_source_type,e.description,f.programme_desc,sampling_point,a.location,a.collection_time,\n" +
                    "(case when district_code is not null then (select district_name from com_mst_districts where district_code=a.district_code) else '-' end)as d_name,\n" +
                    "(case when block_code is not null then (select blockname from com_mst_blocks where district_code=a.district_code and block_code=a.block_code) else '-' end)as b_name,\n" +
                    "(case when panchayat_code is not null then (select panchayatname from com_mst_panchayats where district_code=a.district_code and block_code=a.block_code and panch_code=a.panchayat_code) else '-' end)as p_name,\n" +
                    "(case when habitation_code is not null then (select hname from com_mst_habitations where district_code=a.district_code and block_code=a.block_code and panch_code=a.panchayat_code and hab_code=a.habitation_code) else '-' end)as h_name,\n" +
                    "(case when location_type!='VP' and local_body_code is not null then \n" +
                    "      (case when location_type='Municipality' then (select municipality_eng from pms_mst_municipality where district_code=a.district_code and mucode=a.local_body_code) \n" +
                    "            when location_type='Corporation' then (select corporation_eng from pms_corporation where district_code=a.district_code and corp_code=a.local_body_code) \n" +
                    "            when location_type='UTP' then (select tpname from pms_mst_town_panchayats where dcode=a.district_code and tpcode=a.local_body_code and type='U') \n" +
                    "            when location_type='RTP' then (select tpname from pms_mst_town_panchayats where dcode=a.district_code and tpcode=a.local_body_code and type='R') \n" +
                    "            when location_type is null then '-' end\n" +
                    ")else '-' end)as l_name from \n" +
                    "(select lab_code,invoice_number,sample_number,test_purpose_id,date_of_collection,district_code,location_type,local_body_code,block_code,panchayat_code,habitation_code,scheme_type,source_type,source_code,programme,sampling_point,location,collection_time from wqs_sample_entry \n" +
                    ")a left outer join \n" +
                    "(select count(sample_number)as total_sample,test_purpose_id,sample_number,invoice_number,lab_code from wqs_watersample_result group by lab_code,invoice_number,sample_number,test_purpose_id\n" +
                    ")a1 on a.lab_code=a1.lab_code and a.invoice_number=a1.invoice_number and a.sample_number=a1.sample_number and a.test_purpose_id=a1.test_purpose_id left outer join\n" +
                    "(select scheme_type_id,scheme_type_name from rws_mst_scheme_types \n" +
                    ")c on a.scheme_type=c.scheme_type_id left outer join \n" +
                    "(select water_source_type_id,water_source_type from rws_mst_water_source_type \n" +
                    ")d on a.source_type=water_source_type_id left outer join \n" +
                    "(select source_type,source_code,description from pms_source_code \n" +
                    ")e on a.source_type=e.source_type and a.source_code=e.source_code left outer join \n" +
                    "(select programme_code,programme_desc from mode_of_programme \n" +
                    ")f on a.programme=f.programme_code where a.lab_code=? and a.invoice_number=? and a.sample_number=? and total_sample is null";
                System.out.println("qry is===>" + qry);
                ps1 = con.prepareStatement(qry);
                ps1.setInt(1, lab);
                ps1.setInt(2, ino);
                ps1.setInt(3, sno);
                rs1 = ps1.executeQuery();
                if (rs1.next()) {
                    test_purpose = "";
                    records = 0;
                    test_purpose_id = "";
                    Date f = rs1.getDate("date_of_collection");
                    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String cdate = formatter.format(f);
                    xml = xml + "<cdate>" + cdate + "</cdate>";
                    xml =
 xml + "<ctime>" + rs1.getString("collection_time") + "</ctime>";
                    //xml=xml+"<type>"+rs1.getString("sample_type")+"</type>";
                    xml =
 xml + "<dcode>" + rs1.getString("d_name") + "</dcode>";
                    if (rs1.getString("location_type") != null)
                        xml =
 xml + "<ltype>" + rs1.getString("location_type") + "</ltype>";
                    else
                        xml = xml + "<ltype>-</ltype>";
                    xml =
 xml + "<lcode>" + rs1.getString("l_name") + "</lcode>";
                    xml =
 xml + "<bcode>" + rs1.getString("b_name") + "</bcode>";
                    xml =
 xml + "<pcode>" + rs1.getString("p_name") + "</pcode>";

                    xml =
 xml + "<hcode><![CDATA[" + rs1.getString("h_name") + "]]></hcode>";
                    if (rs1.getString("scheme_type_name") != null)
                        xml =
 xml + "<stype>" + rs1.getString("scheme_type_name") + "</stype>";
                    else
                        xml = xml + "<stype>-</stype>";
                    if (rs1.getString("water_source_type") != null)
                        xml =
 xml + "<s_type>" + rs1.getString("water_source_type") + "</s_type>";
                    else
                        xml = xml + "<s_type>-</s_type>";
                    if (rs1.getString("description") != null)
                        xml =
 xml + "<scode>" + rs1.getString("description") + "</scode>";
                    else
                        xml = xml + "<scode>-</scode>";
                    if (rs1.getString("programme_desc") != null)
                        xml =
 xml + "<programme>" + rs1.getString("programme_desc") + "</programme>";
                    else
                        xml = xml + "<programme>-</programme>";
                    System.out.println("sampling point:===========>" +
                                       rs1.getString("sampling_point"));
                    if (rs1.getString("sampling_point") != null)
                        xml =
 xml + "<spoint>" + rs1.getString("sampling_point") + "</spoint>";
                    else
                        xml = xml + "<spoint>-</spoint>";
                    xml =
 xml + "<location>" + rs1.getString("location") + "</location>";
                    rs =
  st.executeQuery("select test_purpose_id,test_purpose from\n" + "(\n" +
                  "  select a.test_purpose_id,c.test_purpose,count(b.sample_number)as total_rec from wqs_sample_entry a left outer join\n" +
                  "  wqs_watersample_result b on a.lab_code=b.lab_code and a.invoice_number=b.invoice_number and a.sample_number=b.sample_number and a.test_purpose_id=b.test_purpose_id left outer join\n" +
                  "  wqs_test_purpose c on a.test_purpose_id=c.test_purpose_id where a.lab_code=" +
                  lab + " and a.invoice_number=" + ino +
                  " and a.sample_number=" + sno +
                  "group by a.test_purpose_id,c.test_purpose \n" +
                  ")where total_rec is null or total_rec=0");
                    while (rs.next()) {
                        test_purpose_id +=
                                rs.getString("test_purpose_id") + ",";
                        test_purpose += rs.getString("test_purpose") + ",";
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
                } else {
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("Err in commit:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("load_Parameter")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            ino = Integer.parseInt(request.getParameter("invoice"));
            sno = Integer.parseInt(request.getParameter("sample"));
            purpose_id = request.getParameter("test_purpose_id");
            test_purpose = request.getParameter("test_purpose");
            CONTENT_TYPE = "text/html";
            String val = null;
            response.setContentType(CONTENT_TYPE);
            String html = "";
            try {
                rs1 =
 st.executeQuery("select * from wqs_watersample_result where lab_code=" + lab +
                 " and invoice_number=" + ino + " and sample_number=" + sno +
                 " and test_purpose_id='" + purpose_id + "'");
                if (!rs1.next()) {
                    html =
"<table cellpadding=0 cellspacing=0 border=0 width='110%'>";
                    html =
html + "<tr><td colspan=9 class='tdH'>" + test_purpose;
                    html = html + "</td></tr>";
                    if (purpose_id.equalsIgnoreCase("SEW") ||
                        purpose_id.equalsIgnoreCase("EFF"))
                        purpose_id = "DRI";
                    rs =
  st.executeQuery("Select ELEMENT_SYMBOL,STD_NONSTD from WQS_ELEMENT_SYMBOL where TEST_PURPOSE_ID='" +
                  purpose_id + "' order by SNO");
                    int k = 0;
                    html = html + "<tr>";
                    while (rs.next()) {
                        val = rs.getString("ELEMENT_SYMBOL");
                        String std_res = rs.getString("STD_NONSTD");
                        System.out.println("Parameter========>" + val);
                        if (k != 0 && k % 3 == 0) {
                            html = html + "</tr><tr>";
                            System.out.println("New Row");
                        }
                        if (std_res.equalsIgnoreCase("S") &&
                            (purpose_id.equalsIgnoreCase("DRI") ||
                             purpose_id.equalsIgnoreCase("SEW"))) {
                            html =
html + "<td><input type='checkbox' name='chkparameter' id='chkparameter' value='" +
 val + "' checked='checked' onclick='checkparam(" + k + ")'></td>";
                            html = html + "<td>" + val + "</td>";
                            html =
html + "<td><input type='text' name='paramval' id='paramval' style='background-color:white' maxlength=20></td>";
                        } else {
                            html =
html + "<td><input type='checkbox' name='chkparameter' id='chkparameter' value='" +
 val + "' onclick='checkparam(" + k + ")'></td>";
                            html = html + "<td>" + val + "</td>";
                            html =
html + "<td><input type='text' name='paramval' id='paramval' disabled='disabled' style='background-color:rgb(214,214,214)' maxlength=20></td>";
                        }

                        k++;
                    }
                    html = html + "</tr>";
                    if (purpose_id.equalsIgnoreCase("BIO")) {
                        html = html + "<tr><td colspan=9></td></tr>";
                        html =
html + "<tr><td colspan=9><a id='addalgea' name='addalgea' href='javascript:AddAlgae()'>Add Algae</a></td>";
                        html =
html + "<tr><td colspan=9><div id='algaeDiv' width='100%'></div></td></tr>";
                    }
                    html = html + "</table>";
                    System.out.println("html:" + html);
                } else {
                    html = "Records Available";
                }
            } catch (Exception e) {
                System.out.println("Region selection error " + e);
                System.out.println("html:" + html);
            }
            out.print(html);
        } else if (cmd.equalsIgnoreCase("Add")) {

            lab = Integer.parseInt(request.getParameter("lab"));
            ino = Integer.parseInt(request.getParameter("invoice_no"));
            sno = Integer.parseInt(request.getParameter("sample_no"));
            System.out.println(lab + " " + ino + " " + sno);
            String[] od = request.getParameter("rdate").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(od[2]), Integer.parseInt(od[1]) - 1,
                         Integer.parseInt(od[0]));
            java.util.Date d = c.getTime();
            rdate = new Date(d.getTime());
            System.out.println(rdate);
            String eval[] = request.getParameter("e_val").split(",");
            String eres[] = request.getParameter("e_result").split(",");
            System.out.println("length:" + eval.length);
            cnc = request.getParameter("cnc");
            pnp = request.getParameter("pnp");
            reason = request.getParameter("reason");
            String rtime = request.getParameter("rtime");
            purpose_id = request.getParameter("test_purpose");
            percent = request.getParameter("percent").split(",");
            System.out.println("percentage length:" + percent.length);
            System.out.println("Test Purpose Id:" + purpose_id);
            xml = xml + "<command>Add</command>";
            try {
                con.setAutoCommit(false);
                for (int i = 0; i < eval.length; i++) {
                    try {
                        ps =
  con.prepareStatement("insert into wqs_watersample_result(lab_code,invoice_number,sample_number,test_purpose_id,parameter,result,result_in_percentage,date_of_receipt,updated_date,updated_by_user_id,receipt_time,process_flow_status_id) values(?,?,?,?,?,?,?,?,?,?,?,?)");
                        ps.setInt(1, lab);
                        ps.setInt(2, ino);
                        ps.setInt(3, sno);
                        ps.setString(4, purpose_id);
                        ps.setString(5, eval[i]);
                        if (eres[i].equalsIgnoreCase("-"))
                            ps.setString(6, "");
                        else
                            ps.setString(6, eres[i]);
                        if (percent[i].equalsIgnoreCase("-"))
                            ps.setString(7, "");
                        else
                            ps.setString(7, percent[i]);
                        ps.setDate(8, rdate);
                        ps.setTimestamp(9, ts);
                        ps.setString(10, updatedby);
                        ps.setString(11, rtime);
                        ps.setString(12, "CR");
                        int gh = ps.executeUpdate();
                        if (gh > 0)
                            count++;
                        System.out.println("count:" + count);
                    } catch (Exception e) {
                        System.out.println("Err in inner try:" +
                                           e.getMessage());
                    }
                }
                if (count == eval.length) {
                    System.out.println("inserted successfully");
                    flag = "success";
                }
                if (flag.equalsIgnoreCase("success")) {
                    ps1 =
 con.prepareStatement("update wqs_sample_entry set cnc=?,pnp=?,reason=? where lab_code=? and invoice_number=? and sample_number=? and test_purpose_id=?");
                    ps1.setString(1, cnc);
                    ps1.setString(2, pnp);
                    ps1.setString(3, reason);
                    ps1.setInt(4, lab);
                    ps1.setInt(5, ino);
                    ps1.setInt(6, sno);
                    ps1.setString(7, purpose_id);
                    int upd = ps1.executeUpdate();
                    if (upd > 0) {
                        con.commit();
                        xml = xml + "<flag>" + flag + "</flag>";
                    } else {
                        con.rollback();
                        xml = xml + "<flag>failure</flag>";
                    }
                } else {
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
                    "select distinct a.invoice_number,a.sample_number,a.date_of_collection from\n" +
                    "(select distinct lab_code,invoice_number,sample_number,date_of_collection,test_purpose_id from wqs_sample_entry\n" +
                    ")a left outer join\n" +
                    "(select lab_code,invoice_number,process_flow_status_id from wqs_invoice_details\n" +
                    ")b on a.lab_code=b.lab_code and a.invoice_number=b.invoice_number left outer join\n" +
                    "(select count(sample_number)as total_sample,lab_code,invoice_number,sample_number,test_purpose_id from wqs_watersample_result group by lab_code,invoice_number,sample_number,test_purpose_id\n" +
                    ")c on a.lab_code=c.lab_code and a.invoice_number=c.invoice_number and a.sample_number=c.sample_number and a.test_purpose_id=c.test_purpose_id\n" +
                    "where a.lab_code=? and b.process_flow_status_id=? and total_sample is null order by invoice_number,sample_number\n";
                System.out.println("SQL:" + sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, labcode);
                ps.setString(2, "FR");
                rs = ps.executeQuery();
                while (rs.next()) {
                    System.out.println("inside while");
                    test_purpose = "";
                    records = 0;
                    ino = rs.getInt("invoice_number");
                    sno = rs.getInt("sample_number");
                    count++;
                    xml = xml + "<count>";
                    xml = xml + "<ino>" + ino + "</ino>";
                    xml = xml + "<sno>" + sno + "</sno>";
                    Date fdate = rs.getDate("date_of_collection");
                    Format formatter;
                    formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String f = formatter.format(fdate);
                    xml = xml + "<cdate>" + f + "</cdate>";
                    sql =
 ("select * from \n" + "(select b.test_purpose,count(c.sample_number)as total_sample from wqs_sample_entry a left outer join \n" +
  "wqs_test_purpose b on a.test_purpose_id=b.test_purpose_id left outer join wqs_watersample_result c on a.lab_code=c.lab_code and a.invoice_number=c.invoice_number and a.sample_number=c.sample_number and a.test_purpose_id=c.test_purpose_id \n" +
  "where a.lab_code=" + labcode + " and a.invoice_number=" + ino +
  " and a.sample_number=" + sno + " group by test_purpose,sample_number \n" +
  ")where total_sample=0 or total_sample is null");
                    System.out.println("test_purpose selection:" + sql);
                    rs1 = st.executeQuery(sql);
                    while (rs1.next()) {
                        test_purpose += rs1.getString("test_purpose") + ",";
                    }
                    test_purpose =
                            test_purpose.substring(0, test_purpose.length() -
                                                   1);
                    System.out.println("test_purpose:" + test_purpose);
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
