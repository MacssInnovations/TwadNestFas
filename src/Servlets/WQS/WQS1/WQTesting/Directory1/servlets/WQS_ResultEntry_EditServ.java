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

public class WQS_ResultEntry_EditServ extends HttpServlet {
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
        String xml = null, result = null;
        Calendar c;
        Date rdate = null;
        int lab = 0, ino = 0, sno = 0, count = 0;
        String updatedby = null, flag = null, cnc = null, pnp = null, reason =
            null, test_purpose = "", purpose_id = "";
        String test_purpose_id = "", pid = "";
        String percent[] = null;
        String param = "";
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
                    "select distinct invoice_number from wqs_watersample_result where lab_code=? and invoice_number=? and process_flow_status_id in ('CR','MD')";
                System.out.println(sql);
                ps = con.prepareStatement(sql);
                ps.setInt(1, lab);
                ps.setInt(2, ino);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml = xml + "<flag>Success</flag>";
                } else {
                    xml = xml + "<flag>Failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("Err in commit:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("changeSample")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            ino = Integer.parseInt(request.getParameter("invoice"));
            sno = Integer.parseInt(request.getParameter("sample"));
            xml = xml + "<command>changeSample</command>";
            try {
                String sql =
                    ("select distinct a.lab_code,a.invoice_number,b.collection_time,a.sample_number,date_of_collection,b.district_code,district_name,location_type,local_body_code,block_code,panchayat_code,habitation_code,d.scheme_type_name,e.water_source_type,f.description,g.programme_desc,b.sampling_point,b.location,\n" +
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
                     "(select lab_code,invoice_number,sample_number from wqs_watersample_result where lab_code=? and invoice_number=? and sample_number=? and process_flow_status_id in('CR','MD')\n" +
                     ")a left outer join\n" +
                     "(select lab_code,invoice_number,sample_number,date_of_collection,district_code,location_type,local_body_code,block_code,panchayat_code,habitation_code,scheme_type,source_type,source_code,programme,sampling_point,location,collection_time from wqs_sample_entry\n" +
                     ")b on a.lab_code=b.lab_code and a.invoice_number=b.invoice_number and a.sample_number=b.sample_number left outer join\n" +
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
                rs = ps.executeQuery();
                if (rs.next()) {
                    test_purpose = "";
                    test_purpose_id = "";
                    Date f = rs.getDate("date_of_collection");
                    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String cdate = formatter.format(f);
                    xml = xml + "<cdate>" + cdate + "</cdate>";
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
        } else if (cmd.equalsIgnoreCase("load_values")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            ino = Integer.parseInt(request.getParameter("invoice"));
            sno = Integer.parseInt(request.getParameter("sample"));
            purpose_id = request.getParameter("test_purpose_id");
            xml = xml + "<command>load_values</command>";
            try {
                rs1 =
 st.executeQuery("select date_of_receipt,receipt_time,cnc,pnp,reason from wqs_watersample_result a left outer join wqs_sample_entry b on a.lab_code=b.lab_code and a.invoice_number=b.invoice_number and a.sample_number=b.sample_number and a.test_purpose_id=b.test_purpose_id where a.lab_code=" +
                 lab + " and a.invoice_number=" + ino +
                 " and a.sample_number=" + sno + " and a.test_purpose_id='" +
                 purpose_id + "'");
                if (rs1.next()) {
                    Date f = rs1.getDate("date_of_receipt");
                    Format formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String cdate = formatter.format(f);
                    xml = xml + "<rdate>" + cdate + "</rdate>";
                    xml =
 xml + "<rtime>" + rs1.getString("receipt_time") + "</rtime>";
                    if (rs1.getString("cnc") != null)
                        xml = xml + "<cnc>" + rs1.getString("cnc") + "</cnc>";
                    else
                        xml = xml + "<cnc>-</cnc>";
                    if (rs1.getString("pnp") != null)
                        xml = xml + "<pnp>" + rs1.getString("pnp") + "</pnp>";
                    else
                        xml = xml + "<pnp>-</pnp>";
                    if (rs1.getString("reason") != null)
                        xml =
 xml + "<reason>" + rs1.getString("reason") + "</reason>";
                    else
                        xml = xml + "<reason>-</reason>";
                    xml = xml + "<flag>success</flag>";
                } else
                    xml = xml + "<flag>failure</flag>";
            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("Exception in load_values:" +
                                   e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("UpdateParameter")) {
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
 val + "' onclick='checkparam(" + k + ")' checked></td>";
                            html = html + "<td>" + val + "</td>";
                            html =
html + "<td><input type='text' name='paramval' id='paramval' style='white' value='" +
 result + "' onchange='checkPotability(" + k + ")' maxlength=20></td>";
                        }
                    } else {
                        html =
html + "<td><input type='checkbox' name='chkparameter' id='chkparameter' value='" +
 val + "' onclick='checkparam(" + k + ")'></td>";
                        html = html + "<td>" + val + "</td>";
                        html =
html + "<td><input type='text' name='paramval' id='paramval' disabled='disabled' style='background-color:rgb(214,214,214)' onchange='checkPotability(" +
 k + ")' maxlength=20></td>";
                    }
                    k++;
                }
                rs1.close();
                rs.close();
                html = html + "</tr>";
                System.out.println("welcome to bio");
                int z = 0;
                rs =
  st.executeQuery("select * from wqs_watersample_result where lab_code=" +
                  lab + " and invoice_number=" + ino + " and sample_number=" +
                  sno + " and test_purpose_id='" + purpose_id + "'");
                while (rs.next()) {
                    System.out.println("inside bio ");
                    System.out.println(rs.getString("test_purpose_id").equals("BIO") &&
                                       rs.getString("result") == null);
                    if (rs.getString("test_purpose_id").equals("BIO") &&
                        rs.getString("result") == null) {
                        System.out.println("k===========>" + k +
                                           " Parameter:========>" +
                                           rs.getString("parameter"));
                        param += rs.getString("parameter") + "//";
                        System.out.println("parameter:" + param);
                        z++;
                    }
                }
                if (z > 0) {
                    param = param.substring(0, param.length() - 2);
                    System.out.println("After filtering:" + param);
                    html = html + "<tr><td colspan=9></td></tr>";
                    html =
html + "<tr><td colspan=9><a id='addalgea' name='addalgea' href='javascript:AddAlgae()'>Add Algae</a></td>";
                    html =
html + "<tr><td colspan=9><div id='algaeDiv' width='100%' style='display:none'>";
                    html =
html + "<input type='text' name='algae' id='algae' value='" + param +
 "' size=25 ></td>";
                    html = html + "</div></td></tr>";
                }
                html = html + "</table>";
                System.out.println("html:" + html);
            } catch (Exception e) {
                System.out.println("Region selection error " + e);
                System.out.println("html:" + html);
            }
            out.print(html);
        }
        /*else if(cmd.equalsIgnoreCase("Add"))
        {

            lab=Integer.parseInt(request.getParameter("lab"));
            ino=Integer.parseInt(request.getParameter("invoice_no"));
            sno=Integer.parseInt(request.getParameter("sample_no"));
            System.out.println(lab+" "+ino+" "+sno);
            String[] od=request.getParameter("rdate").split("/");
            c=new GregorianCalendar(Integer.parseInt(od[2]),Integer.parseInt(od[1])-1,Integer.parseInt(od[0]));
            java.util.Date d=c.getTime();
            rdate=new Date(d.getTime());
            System.out.println(rdate);
            String eval[]=request.getParameter("e_val").split(",");
            String eres[]=request.getParameter("e_result").split(",");
            System.out.println("length:"+eval.length);
            cnc=request.getParameter("cnc");
            pnp=request.getParameter("pnp");
            reason=request.getParameter("reason");
            String rtime=request.getParameter("rtime");
            xml=xml+"<command>Add</command>";
            try
            {
                con.setAutoCommit(false);
                for(int i=0;i<eval.length;i++)
                {
                    ps=con.prepareStatement("insert into wqs_watersample_result(lab_code,invoice_number,sample_number,date_of_receipt,parameter,result,updated_date,updated_by_user_id,receipt_time,process_flow_status_id) values(?,?,?,?,?,?,?,?,?,?)");
                    ps.setInt(1,lab);
                    ps.setInt(2,ino);
                    ps.setInt(3,sno);
                    ps.setDate(4,rdate);
                    ps.setString(5,eval[i]);
                    ps.setString(6,eres[i]);
                    ps.setTimestamp(7,ts);
                    ps.setString(8,updatedby);
                    ps.setString(9,rtime);
                    ps.setString(10,"CR");
                    int gh=ps.executeUpdate();
                    if(gh>0)
                        count++;
                    System.out.println("count:"+count);
                }
                   if(count==eval.length)
                   {
                       System.out.println("inserted successfully");
                       flag="success";
                   }
                   if(flag.equalsIgnoreCase("success"))
                   {
                        ps1=con.prepareStatement("update wqs_sample_entry set cnc=?,pnp=?,reason=? where lab_code=? and invoice_number=? and sample_number=?");
                        ps1.setString(1,cnc);
                        ps1.setString(2,pnp);
                        ps1.setString(3,reason);
                        ps1.setInt(4,lab);
                        ps1.setInt(5,ino);
                        ps1.setInt(6,sno);
                        int upd=ps1.executeUpdate();
                        if(upd>0)
                        {
                            con.commit();
                            xml=xml+"<flag>"+flag+"</flag>";
                        }
                        else
                        {
                            con.rollback();
                            xml=xml+"<flag>failure</flag>";
                        }
                   }
                   else
                   {
                        con.rollback();
                        xml=xml+"<flag>failure</flag>";
                   }

            }
            catch(Exception e)
            {
                xml=xml+"<flag>exception</flag>";
                xml=xml+"<exception>"+e.getMessage()+"</exception>";
                System.out.println("Err in commit:"+e.getMessage());
            }
        }*/
        else if (cmd.equalsIgnoreCase("Update")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            ino = Integer.parseInt(request.getParameter("invoice_no"));
            sno = Integer.parseInt(request.getParameter("sample_no"));
            String[] od = request.getParameter("rdate").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(od[2]), Integer.parseInt(od[1]) - 1,
                         Integer.parseInt(od[0]));
            java.util.Date d = c.getTime();
            rdate = new Date(d.getTime());
            System.out.println(rdate);
            String eval[] = request.getParameter("e_val").split(",");
            String eres[] = request.getParameter("e_result").split(",");
            cnc = request.getParameter("cnc");
            pnp = request.getParameter("pnp");
            reason = request.getParameter("reason");
            String rtime = request.getParameter("rtime");
            purpose_id = request.getParameter("test_purpose");
            percent = request.getParameter("percent").split(",");
            System.out.println("Test Purpose Id:" + purpose_id);
            xml = xml + "<command>Update</command>";
            try {
                con.setAutoCommit(false);
                ps1 =
 con.prepareStatement("delete from wqs_watersample_result where lab_code=? and invoice_number=? and sample_number=? and test_purpose_id=?");
                ps1.setInt(1, lab);
                ps1.setInt(2, ino);
                ps1.setInt(3, sno);
                ps1.setString(4, purpose_id);
                ps1.executeUpdate();
                for (int i = 0; i < eval.length; i++) {
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
                }
                if (count == eval.length) {
                    System.out.println("inserted successfully");
                    flag = "success";
                }
                if (flag.equalsIgnoreCase("success")) {
                    ps1 =
 con.prepareStatement("update wqs_sample_entry set cnc=?,pnp=?,reason=? where lab_code=? and invoice_number=? and sample_number=?");
                    ps1.setString(1, cnc);
                    ps1.setString(2, pnp);
                    ps1.setString(3, reason);
                    ps1.setInt(4, lab);
                    ps1.setInt(5, ino);
                    ps1.setInt(6, sno);
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
        } else if (cmd.equalsIgnoreCase("Del")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            ino = Integer.parseInt(request.getParameter("invoice_no"));
            sno = Integer.parseInt(request.getParameter("sample_no"));
            purpose_id = request.getParameter("test_purpose");
            xml = xml + "<command>Del</command>";
            try {

                ps =
  con.prepareStatement("delete from wqs_watersample_result where lab_code=? and invoice_number=? and sample_number=? and test_purpose_id=?");
                ps.setInt(1, lab);
                ps.setInt(2, ino);
                ps.setInt(3, sno);
                ps.setString(4, purpose_id);
                ps.executeUpdate();
                System.out.println("Deleted successfully");
                xml = xml + "<flag>success</flag>";
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
                    "select distinct lab_code,invoice_number,sample_number from wqs_watersample_result " +
                    "where lab_code=? and process_flow_status_id in('CR','MD') order by invoice_number,sample_number";
                ps = con.prepareStatement(sql);
                ps.setInt(1, labcode);
                rs = ps.executeQuery();
                while (rs.next()) {
                    test_purpose = "";
                    ino = rs.getInt("invoice_number");
                    sno = rs.getInt("sample_number");
                    System.out.println("invoice======>" + ino);
                    System.out.println("sample======>" + sno);
                    sql =
 "select distinct a.test_purpose_id,b.test_purpose from wqs_watersample_result a left outer join wqs_test_purpose b on a.test_purpose_id=b.test_purpose_id where a.lab_code=? and a.invoice_number=? and a.sample_number=? and process_flow_status_id in('CR','MD')";
                    ps1 = con.prepareStatement(sql);
                    ps1.setInt(1, labcode);
                    ps1.setInt(2, ino);
                    ps1.setInt(3, sno);
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
                    xml = xml + "<sno>" + sno + "</sno>";
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
