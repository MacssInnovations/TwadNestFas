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

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class WQS_SampleEntry_EditServ extends HttpServlet {
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
        System.out.println("welcome to servlet");
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null, rs2 = null;
        PreparedStatement ps = null;
        String cmd = null, xml = null, name = null, flag = null, updatedby =
            null;
        int code = 0;
        String test_purpose = "";
        Timestamp ts = null;
        int lab = 0, gcount = 0;
        try {
            ResourceBundle rst =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rst.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rst.getString("Config.DSN");
            String strhostname = rst.getString("Config.HOST_NAME");
            String strportno = rst.getString("Config.PORT_NUMBER");
            String strsid = rst.getString("Config.SID");
            String strdbusername = rst.getString("Config.USER_NAME");
            String strdbpassword = rst.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
            stmt = con.createStatement();

            try {
                stmt = con.createStatement();
                con.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }
        response.setHeader("cache-control", "no-cache");
        HttpSession session = request.getSession(false);
        session = request.getSession(false);
        updatedby = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        ts = new Timestamp(l);
        System.out.println(updatedby);
        System.out.println(ts);
        cmd = request.getParameter("option");
        xml = "<response>";
        if (cmd.equalsIgnoreCase("load_labcode")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            xml = xml + "<command>load_labcode</command>";
            try {
                ps =
  con.prepareStatement("select b.DISTRICT_NAME,b.DISTRICT_CODE from(select OFFICE_ID,DISTRICT_CODE from COM_MST_OFFICES where OFFICE_ID=?)a inner join(select DISTRICT_CODE,DISTRICT_NAME from COM_MST_DISTRICTS)b on a.DISTRICT_CODE=b.DISTRICT_CODE");
                ps.setInt(1, lab);
                rs = ps.executeQuery();
                if (rs.next()) {
                    code = rs.getInt("DISTRICT_CODE");
                    name = rs.getString("DISTRICT_NAME");
                    System.out.println("District Name :" + name);
                    rs2 =
 stmt.executeQuery("select DISTRICT_CODE,DISTRICT_NAME from COM_MST_DISTRICTS");
                    while (rs2.next()) {
                        gcount++;
                        xml = xml + "<count>";
                        xml =
 xml + "<district_name>" + rs2.getString("DISTRICT_NAME") + "</district_name>";
                        xml =
 xml + "<district_code>" + rs2.getInt("DISTRICT_CODE") + "</district_code>";
                        xml = xml + "</count>";

                    }
                    if (gcount > 0)
                        xml = xml + "<dflag>success</dflag>";
                    else
                        xml = xml + "<dflag>failure</dflag>";
                }
            } catch (Exception e) {
                System.out.println("Err in lab selection:" + e.getMessage());
                xml = xml + "<dflag>failure</dflag>";
            }
            try {
                // schemetype
                System.out.println("scheme type selection");
                ps =
  con.prepareStatement("select scheme_type_id,scheme_type_name from rws_mst_scheme_types");
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<scount>";
                    xml =
 xml + "<stype_id>" + rs.getString("scheme_type_id") + "</stype_id>";
                    System.out.println("Scheme type id--------------->" +
                                       rs.getString("scheme_type_id"));
                    xml =
 xml + "<stype_name>" + rs.getString("scheme_type_name") + "</stype_name>";
                    System.out.println("Scheme type id--------------->" +
                                       rs.getString("scheme_type_name"));
                    xml = xml + "</scount>";
                }
                xml = xml + "<sflag>success</sflag>";
                ps.close();
            } catch (Exception e) {
                xml = xml + "<sflag>failure</sflag>";
                System.out.println("Err in scm selection:" + e.getMessage());
            }
            try {
                ps =
  con.prepareStatement("select water_source_type_id,water_source_type from rws_mst_water_source_type");
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<s_count>";
                    xml =
 xml + "<sourcetype_id>" + rs.getString("water_source_type_id") +
   "</sourcetype_id>";
                    System.out.println("source type id----------->" +
                                       rs.getString("water_source_type_id"));
                    xml =
 xml + "<sourcetype>" + rs.getString("water_source_type") + "</sourcetype>";
                    System.out.println("source type----------->" +
                                       rs.getString("water_source_type"));
                    xml = xml + "</s_count>";
                }
                xml = xml + "<s_flag>success</s_flag>";
                ps.close();
            } catch (Exception e) {
                xml = xml + "<s_flag>failure</s_flag>";
                System.out.println("Err in source type selection:" +
                                   e.getMessage());
            }
            try {
                ps =
  con.prepareStatement("select programme_code,programme_desc from mode_of_programme");
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<pcount>";
                    xml =
 xml + "<programmecode>" + rs.getString("programme_code") + "</programmecode>";
                    System.out.println("Program code------------->" +
                                       rs.getString("programme_code"));
                    xml =
 xml + "<programmedesc>" + rs.getString("programme_desc") + "</programmedesc>";
                    System.out.println("programme desc------------->" +
                                       rs.getString("programme_desc"));
                    xml = xml + "</pcount>";
                }
                xml = xml + "<pflag>success</pflag>";
                ps.close();
            } catch (Exception e) {
                xml = xml + "<pflag>failure</pflag>";
                System.out.println("Err in programme code selection:" +
                                   e.getMessage());
            }
            try {
                System.out.println("sampling Point");
                ps =
  con.prepareStatement("select sampling_point from wqs_sampling_point");
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<samp_count>";
                    xml =
 xml + "<sampling_point>" + rs.getString("sampling_point") +
   "</sampling_point>";
                    System.out.println("sampling_point------------->" +
                                       rs.getString("sampling_point"));
                    xml = xml + "</samp_count>";
                }
                xml = xml + "<samp_flag>success</samp_flag>";
                ps.close();
            } catch (Exception e) {
                xml = xml + "<sampflag>failure</sampflag>";
                System.out.println("Err in programme code selection:" +
                                   e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("load_invoice")) {
            int labcode = Integer.parseInt(request.getParameter("lab"));
            xml = xml + "<command>load_invoice</command>";
            try {
                String sql =
                    "select a1.INVOICE_NUMBER,CUSTOMER_TYPE,INVOICE_DATE,INVOICE_AMOUNT from " +
                    "(select distinct LAB_CODE,INVOICE_NUMBER from WQS_SAMPLE_ENTRY " +
                    ")a1 left outer join" +
                    "(select LAB_CODE,INVOICE_NUMBER,INVOICE_DATE,INVOICE_AMOUNT,CUSTOMER_TYPE,PROCESS_FLOW_STATUS_ID from WQS_INVOICE_DETAILS" +
                    ")a on a1.LAB_CODE=a.LAB_CODE and a1.INVOICE_NUMBER=a.INVOICE_NUMBER " +
                    "where a1.LAB_CODE=" + labcode +
                    " and a.PROCESS_FLOW_STATUS_ID='MD' order by a.INVOICE_NUMBER";
                System.out.println("Sql:" + sql);
                rs2 = stmt.executeQuery(sql);
                while (rs2.next()) {
                    System.out.println("Inside while");
                    xml = xml + "<count>";
                    xml =
 xml + "<ino>" + rs2.getInt("INVOICE_NUMBER") + "</ino>";
                    Date fdate = rs2.getDate("INVOICE_DATE");
                    Format formatter;
                    formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String f = formatter.format(fdate);
                    xml = xml + "<idate>" + f + "</idate>";
                    xml =
 xml + "<amt>" + rs2.getString("INVOICE_AMOUNT") + "</amt>";
                    xml =
 xml + "<ctype>" + rs2.getString("CUSTOMER_TYPE") + "</ctype>";
                    xml = xml + "</count>";
                    gcount++;
                }
                if (gcount > 0)
                    xml = xml + "<flag>success</flag>";
                else
                    xml = xml + "<flag>failure</flag>";
            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println(e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("changeInvoice")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            int ino = Integer.parseInt(request.getParameter("invoice_number"));
            String test_purpose_id = "";
            xml = xml + "<command>changeInvoice</command>";
            int cnt = 0;
            try {
                String query =
                    "select distinct a.sample_number,a.date_of_collection,a.district_code,aa.process_flow_status_id,a1.district_name,a.location_type,a.local_body_code,a.block_code,a.panchayat_code,a.habitation_code,aa.customer_type, \n" +
                    "(case when a.location_type!='VP' then \n" +
                    "(case when a.location_type='Corporation' then (select corporation_eng from pms_corporation where district_code=a.district_code and corp_code=a.local_body_code) \n" +
                    "when a.location_type='Municipality' then (select municipality_eng from pms_mst_municipality where district_code=a.district_code and mucode=a.local_body_code) \n" +
                    "when a.location_type='UTP' then (select tpname from pms_mst_town_panchayats where dcode=a.district_code and tpcode=a.local_body_code and type='U') \n" +
                    "when a.location_type='RTP' then (select tpname from pms_mst_town_panchayats where dcode=a.district_code and tpcode=a.local_body_code and type='R') \n" +
                    "else '-' end \n" + ") \n" + "else '-' end \n" +
                    ")as lbody, \n" +
                    "(case when a.block_code is not null then \n" +
                    "(select blockname from com_mst_blocks where district_code=a.district_code and block_code=a.block_code)  else '-' end \n" +
                    ")bname, \n" +
                    "(case when a.panchayat_code is not null then \n" +
                    "(select panchayatname from com_mst_panchayats where district_code=a.district_code and block_code=a.block_code and panch_code=a.panchayat_code) else '-' end \n" +
                    ")pname, \n" +
                    "(case when a.habitation_code is not null then \n" +
                    "(select hname from com_mst_habitations where district_code=a.district_code and block_code=a.block_code and panch_code=a.panchayat_code and hab_code=a.habitation_code) else '-' end \n" +
                    ")hname, \n" +
                    "a.scheme_type,a.source_type,a.source_code,a.programme,a.sampling_point,a.location,a.collection_time, \n" +
                    "b.scheme_type_name,c.water_source_type,d.description,e.programme_desc,total_samples,sample_type from \n" +
                    "(select lab_code,invoice_number,sample_number,date_of_collection,district_code,location_type,local_body_code,block_code,panchayat_code,habitation_code,scheme_type,source_type,source_code,programme,sampling_point,location,collection_time,sample_type from wqs_sample_entry \n" +
                    ")a left outer join \n" +
                    "(select lab_code,invoice_number,customer_type,total_samples,process_flow_status_id from wqs_invoice_details \n" +
                    ")aa on a.lab_code=aa.lab_code and a.invoice_number=aa.invoice_number left outer join \n" +
                    "(select district_code,district_name from com_mst_districts \n" +
                    ")a1 on a.district_code=a1.district_code left outer join \n" +
                    "(select scheme_type_id,scheme_type_name from rws_mst_scheme_types \n" +
                    ")b on a.scheme_type=b.scheme_type_id left outer join \n" +
                    "(select water_source_type_id,water_source_type from rws_mst_water_source_type \n" +
                    ")c on a.source_type=c.water_source_type_id left outer join \n" +
                    "(select source_type,source_code,description from pms_source_code \n" +
                    ")d on a.source_type=d.source_type and a.source_code=d.source_code left outer join \n" +
                    "(select programme_code,programme_desc from mode_of_programme \n" +
                    ")e on a.programme=e.programme_code \n" +
                    "where a.lab_code=? and a.invoice_number=? and aa.process_flow_status_id='MD' order by a.sample_number ";
                System.out.println(query);
                ps = con.prepareStatement(query);
                ps.setInt(1, lab);
                ps.setInt(2, ino);
                rs = ps.executeQuery();
                while (rs.next()) {
                    test_purpose = "";
                    test_purpose_id = "";
                    cnt++;
                    xml = xml + "<row_count>";
                    int sno = rs.getInt("sample_number");
                    xml = xml + "<sample_number>" + sno + "</sample_number>";
                    Date fdate = rs.getDate("date_of_collection");
                    Format formatter;
                    formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String f = formatter.format(fdate);
                    xml =
 xml + "<date_of_collection>" + f + "</date_of_collection>";
                    xml =
 xml + "<collection_time>" + rs.getString("collection_time") +
   "</collection_time>";
                    xml =
 xml + "<district_code>" + rs.getInt("district_code") + "</district_code>";
                    xml =
 xml + "<district_name>" + rs.getString("district_name") + "</district_name>";
                    xml = xml + "<bname>" + rs.getString("bname") + "</bname>";
                    xml = xml + "<lbody>" + rs.getString("lbody") + "</lbody>";
                    xml = xml + "<pname>" + rs.getString("pname") + "</pname>";
                    xml =
 xml + "<ctype>" + rs.getString("customer_type") + "</ctype>";
                    xml =
 xml + "<hname><![CDATA[" + rs.getString("hname") + "]]></hname>";

                    if (rs.getString("location_type") == null)
                        xml = xml + "<location_type>-</location_type>";
                    else
                        xml =
 xml + "<location_type>" + rs.getString("location_type") + "</location_type>";
                    if (rs.getString("local_body_code") == null)
                        xml = xml + "<local_body_code>-</local_body_code>";
                    else
                        xml =
 xml + "<local_body_code>" + rs.getInt("local_body_code") +
   "</local_body_code>";
                    if (rs.getString("block_code") == null)
                        xml = xml + "<block_code>-</block_code>";
                    else
                        xml =
 xml + "<block_code>" + rs.getInt("block_code") + "</block_code>";
                    if (rs.getString("panchayat_code") == null)
                        xml = xml + "<panchayat_code>-</panchayat_code>";
                    else
                        xml =
 xml + "<panchayat_code>" + rs.getInt("panchayat_code") + "</panchayat_code>";
                    if (rs.getString("habitation_code") == null)
                        xml = xml + "<habitation_code>-</habitation_code>";
                    else
                        xml =
 xml + "<habitation_code>" + rs.getInt("habitation_code") +
   "</habitation_code>";
                    if (rs.getString("scheme_type") == null)
                        xml = xml + "<scheme_type>-</scheme_type>";
                    else
                        xml =
 xml + "<scheme_type>" + rs.getString("scheme_type") + "</scheme_type>";
                    if (rs.getString("scheme_type_name") == null)
                        xml = xml + "<scheme_type_name>-</scheme_type_name>";
                    else
                        xml =
 xml + "<scheme_type_name>" + rs.getString("scheme_type_name") +
   "</scheme_type_name>";
                    if (rs.getString("source_type") == null)
                        xml = xml + "<source_type>-</source_type>";
                    else
                        xml =
 xml + "<source_type>" + rs.getString("source_type") + "</source_type>";
                    if (rs.getString("water_source_type") == null)
                        xml = xml + "<water_source_type>-</water_source_type>";
                    else
                        xml =
 xml + "<water_source_type>" + rs.getString("water_source_type") +
   "</water_source_type>";
                    if (rs.getString("source_code") == null)
                        xml = xml + "<source_code>-</source_code>";
                    else
                        xml =
 xml + "<source_code>" + rs.getString("source_code") + "</source_code>";
                    if (rs.getString("description") == null)
                        xml = xml + "<description>-</description>";
                    else
                        xml =
 xml + "<description>" + rs.getString("description") + "</description>";
                    if (rs.getString("programme") == null)
                        xml = xml + "<programme>-</programme>";
                    else
                        xml =
 xml + "<programme>" + rs.getString("programme") + "</programme>";
                    if (rs.getString("programme_desc") == null)
                        xml = xml + "<programme_desc>-</programme_desc>";
                    else
                        xml =
 xml + "<programme_desc>" + rs.getString("programme_desc") +
   "</programme_desc>";
                    if (rs.getString("sampling_point") == null)
                        xml = xml + "<sampling_point>-</sampling_point>";
                    else
                        xml =
 xml + "<sampling_point>" + rs.getString("sampling_point") +
   "</sampling_point>";
                    xml =
 xml + "<location>" + rs.getString("location") + "</location>";
                    xml =
 xml + "<total_samples>" + rs.getString("total_samples") + "</total_samples>";
                    xml =
 xml + "<sample_type>" + rs.getString("sample_type") + "</sample_type>";
                    rs2 =
 stmt.executeQuery("select a.test_purpose_id,b.test_purpose from wqs_sample_entry a left outer join wqs_test_purpose b on a.test_purpose_id=b.test_purpose_id where lab_code=" +
                   lab + " and invoice_number=" + ino + " and sample_number=" +
                   sno);
                    while (rs2.next()) {
                        test_purpose_id +=
                                rs2.getString("test_purpose_id") + ",";
                        test_purpose += rs2.getString("test_purpose") + ",";
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
                if (cnt > 0)
                    xml = xml + "<flag>success</flag>";
                else
                    xml = xml + "<flag>failure</flag>";
            } catch (SQLException e) {
                System.out.println("error is:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("loadblock")) {
            int dist_code =
                Integer.parseInt(request.getParameter("dist_code"));
            xml = xml + "<command>loadblock</command>";
            try {
                String query =
                    "select block_code,blockname from com_mst_blocks where district_code=" +
                    dist_code + " order by block_code";
                System.out.println(query);
                ps = con.prepareStatement(query);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<bcount>";
                    xml =
 xml + "<block_code>" + rs.getInt("block_code") + "</block_code>";
                    xml =
 xml + "<block_name>" + rs.getString("blockname") + "</block_name>";
                    xml = xml + "</bcount>";
                    gcount++;
                }
                xml = xml + "<flag>success</flag>";
                xml = xml + "<blockcount>" + gcount + "</blockcount>";

            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("error is:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("loadpanchayat")) {
            int dist_code =
                Integer.parseInt(request.getParameter("dist_code"));
            int block_code =
                Integer.parseInt(request.getParameter("block_code"));
            xml = xml + "<command>loadpanchayat</command>";
            try {
                String query =
                    "select panch_code,panchayatname from com_mst_panchayats where district_code=" +
                    dist_code + " and block_code=" + block_code +
                    " order by panch_code";
                System.out.println(query);
                ps = con.prepareStatement(query);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<pcount>";
                    xml =
 xml + "<panchayat_code>" + rs.getInt("panch_code") + "</panchayat_code>";
                    xml =
 xml + "<panchayat_name>" + rs.getString("panchayatname") +
   "</panchayat_name>";
                    xml = xml + "</pcount>";
                    gcount++;
                }
                xml = xml + "<flag>success</flag>";
                xml = xml + "<panchayatcount>" + gcount + "</panchayatcount>";
            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("error is:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("loadhabitation")) {
            int dist_code =
                Integer.parseInt(request.getParameter("dist_code"));
            int block_code =
                Integer.parseInt(request.getParameter("block_code"));
            int panchayat_code =
                Integer.parseInt(request.getParameter("panchayat_code"));

            xml = xml + "<command>loadhabitation</command>";
            try {
                String query =
                    "select hab_code,hname from com_mst_habitations where district_code=" +
                    dist_code + " and block_code=" + block_code +
                    " and panch_code=" + panchayat_code + " order by hab_code";
                System.out.println(query);
                ps = con.prepareStatement(query);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<hcount>";
                    xml =
 xml + "<habitation_code>" + rs.getInt("hab_code") + "</habitation_code>";
                    xml =
 xml + "<habitation_name><![CDATA[" + rs.getString("hname") +
   "]]></habitation_name>";
                    xml = xml + "</hcount>";
                    gcount++;
                }
                xml = xml + "<flag>success</flag>";
                xml =
 xml + "<habitationcount>" + gcount + "</habitationcount>";
            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("error is:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("loadlocalbody")) {
            int dist_code =
                Integer.parseInt(request.getParameter("dist_code"));
            String ltype = request.getParameter("ltype");
            xml = xml + "<command>loadlocalbody</command>";
            if (ltype.equalsIgnoreCase("Corporation")) {
                System.out.println("Location Type=============>" + ltype);
                try {
                    String query =
                        "select district_code,corp_code,corporation_eng from pms_corporation where district_code=" +
                        dist_code + " order by corporation_eng";
                    System.out.println(query);
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        xml = xml + "<count>";
                        xml =
 xml + "<code>" + rs.getInt("corp_code") + "</code>";
                        xml =
 xml + "<name>" + rs.getString("corporation_eng") + "</name>";
                        xml = xml + "</count>";
                        gcount++;
                    }
                    xml = xml + "<flag>success</flag>";
                    xml = xml + "<tcount>" + gcount + "</tcount>";
                } catch (SQLException e) {
                    xml = xml + "<flag>failure</flag>";
                    System.out.println("error is:" + e.getMessage());
                }
            } else if (ltype.equalsIgnoreCase("Municipality")) {
                System.out.println("Location Type=============>" + ltype);
                try {
                    String query =
                        "select district_code,mucode,municipality_eng from pms_mst_municipality where district_code=" +
                        dist_code + " order by municipality_eng";
                    System.out.println(query);
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        xml = xml + "<count>";
                        xml = xml + "<code>" + rs.getInt("mucode") + "</code>";
                        xml =
 xml + "<name>" + rs.getString("municipality_eng") + "</name>";
                        xml = xml + "</count>";
                        gcount++;
                    }
                    xml = xml + "<flag>success</flag>";
                    xml = xml + "<tcount>" + gcount + "</tcount>";
                } catch (SQLException e) {
                    xml = xml + "<flag>failure</flag>";
                    System.out.println("error is:" + e.getMessage());
                }
            } else if (ltype.equalsIgnoreCase("UTP")) {
                System.out.println("Location Type=============>" + ltype);
                try {
                    String query =
                        "select dcode,tpcode,tpname from pms_mst_town_panchayats where dcode=" +
                        dist_code + " and type='U' order by tpname";
                    System.out.println(query);
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        xml = xml + "<count>";
                        xml = xml + "<code>" + rs.getInt("tpcode") + "</code>";
                        xml =
 xml + "<name>" + rs.getString("tpname") + "</name>";
                        xml = xml + "</count>";
                        gcount++;
                    }
                    xml = xml + "<flag>success</flag>";
                    xml = xml + "<tcount>" + gcount + "</tcount>";
                } catch (SQLException e) {
                    xml = xml + "<flag>failure</flag>";
                    System.out.println("error is:" + e.getMessage());
                }
            }
            if (ltype.equalsIgnoreCase("RTP")) {
                System.out.println("Location Type=============>" + ltype);
                try {
                    String query =
                        "select dcode,tpcode,tpname from pms_mst_town_panchayats where dcode=" +
                        dist_code + " and type='R' order by tpname";
                    System.out.println(query);
                    ps = con.prepareStatement(query);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        xml = xml + "<count>";
                        xml = xml + "<code>" + rs.getInt("tpcode") + "</code>";
                        xml =
 xml + "<name>" + rs.getString("tpname") + "</name>";
                        xml = xml + "</count>";
                        gcount++;
                    }
                    xml = xml + "<flag>success</flag>";
                    xml = xml + "<tcount>" + gcount + "</tcount>";
                } catch (SQLException e) {
                    xml = xml + "<flag>failure</flag>";
                    System.out.println("error is:" + e.getMessage());
                }
            }
        } else if (cmd.equalsIgnoreCase("changeType")) {
            String type = request.getParameter("Type");
            xml = xml + "<command>changeType</command>";
            try {
                String query =
                    "select SOURCE_CODE,DESCRIPTION from PMS_SOURCE_CODE where SOURCE_TYPE='" +
                    type + "'";
                System.out.println(query);
                ps = con.prepareStatement(query);
                rs = ps.executeQuery();
                while (rs.next()) {
                    xml = xml + "<source_count>";
                    xml =
 xml + "<sourcecode>" + rs.getString("SOURCE_CODE") + "</sourcecode>";
                    xml =
 xml + "<sourcetype>" + rs.getString("DESCRIPTION") + "</sourcetype>";
                    xml = xml + "</source_count>";
                    gcount++;
                }
                xml =
 xml + "<sourcecodecount>" + gcount + "</sourcecodecount>";
                xml = xml + "<gflag>success</gflag>";
            } catch (SQLException e) {
                xml = xml + "<gflag>failure</gflag>";
                System.out.println("error is:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("LoadRecord")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            int ino = Integer.parseInt(request.getParameter("ino"));
            int sample = Integer.parseInt(request.getParameter("sample"));
            String type = request.getParameter("type");
            System.out.println("type==============>" + type);
            int dist_code = Integer.parseInt(request.getParameter("distcode"));
            String sourcetype = request.getParameter("sourcetype");
            try {
                xml = xml + "<command>LoadRecord</command>";
                try {
                    System.out.println("inside recordchecking");
                    ps =
  con.prepareStatement("select * from wqs_watersample_result where lab_code=? and invoice_number=? and sample_number=?");
                    ps.setInt(1, lab);
                    ps.setInt(2, ino);
                    ps.setInt(3, sample);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        xml = xml + "<record>found</record>";
                    } else
                        xml = xml + "<record>notfound</record>";

                } catch (Exception e) {
                    System.out.println("Err in EditRecord:" + e.getMessage());
                }
                if (type.equalsIgnoreCase("others")) {
                    xml = xml + "<type>others</type>";
                    String ltype = request.getParameter("ltype");
                    System.out.println(ltype);
                    if (ltype.equalsIgnoreCase("Corporation")) {
                        System.out.println("Location Type=============>" +
                                           ltype);
                        try {
                            String query =
                                "select district_code,corp_code,corporation_eng from pms_corporation where district_code=" +
                                dist_code + " order by corporation_eng";
                            System.out.println(query);
                            ps = con.prepareStatement(query);
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                xml = xml + "<count>";
                                xml =
 xml + "<code>" + rs.getString("corp_code") + "</code>";
                                xml =
 xml + "<name>" + rs.getString("corporation_eng") + "</name>";
                                xml = xml + "</count>";
                                gcount++;
                            }
                            xml = xml + "<flag>success</flag>";
                            xml = xml + "<tcount>" + gcount + "</tcount>";
                        } catch (SQLException e) {
                            xml = xml + "<flag>failure</flag>";
                            System.out.println("error is:" + e.getMessage());
                        }
                    } else if (ltype.equalsIgnoreCase("Municipality")) {
                        System.out.println("Location Type=============>" +
                                           ltype);
                        try {
                            String query =
                                "select district_code,mucode,municipality_eng from pms_mst_municipality where district_code=" +
                                dist_code + " order by municipality_eng";
                            System.out.println(query);
                            ps = con.prepareStatement(query);
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                xml = xml + "<count>";
                                xml =
 xml + "<code>" + rs.getString("mucode") + "</code>";
                                xml =
 xml + "<name>" + rs.getString("municipality_eng") + "</name>";
                                xml = xml + "</count>";
                                gcount++;
                            }
                            xml = xml + "<flag>success</flag>";
                            xml = xml + "<tcount>" + gcount + "</tcount>";
                        } catch (SQLException e) {
                            xml = xml + "<flag>failure</flag>";
                            System.out.println("error is:" + e.getMessage());
                        }
                    } else if (ltype.equalsIgnoreCase("UTP")) {
                        System.out.println("Location Type=============>" +
                                           ltype);
                        try {
                            String query =
                                "select dcode,tpcode,tpname from pms_mst_town_panchayats where dcode=" +
                                dist_code + " and type='U' order by tpname";
                            System.out.println(query);
                            ps = con.prepareStatement(query);
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                xml = xml + "<count>";
                                xml =
 xml + "<code>" + rs.getString("tpcode") + "</code>";
                                xml =
 xml + "<name>" + rs.getString("tpname") + "</name>";
                                xml = xml + "</count>";
                                gcount++;
                            }
                            xml = xml + "<flag>success</flag>";
                            xml = xml + "<tcount>" + gcount + "</tcount>";
                        } catch (SQLException e) {
                            xml = xml + "<flag>failure</flag>";
                            System.out.println("error is:" + e.getMessage());
                        }
                    } else if (ltype.equalsIgnoreCase("RTP")) {
                        System.out.println("Location Type=============>" +
                                           ltype);
                        try {
                            String query =
                                "select dcode,tpcode,tpname from pms_mst_town_panchayats where dcode=" +
                                dist_code + " and type='R' order by tpname";
                            System.out.println(query);
                            ps = con.prepareStatement(query);
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                xml = xml + "<count>";
                                xml =
 xml + "<code>" + rs.getString("tpcode") + "</code>";
                                xml =
 xml + "<name>" + rs.getString("tpname") + "</name>";
                                xml = xml + "</count>";
                                gcount++;
                            }
                            xml = xml + "<flag>success</flag>";
                            xml = xml + "<tcount>" + gcount + "</tcount>";
                        } catch (SQLException e) {
                            xml = xml + "<flag>failure</flag>";
                            System.out.println("error is:" + e.getMessage());
                        }
                    }

                } else if (type.equalsIgnoreCase("VP")) {
                    xml = xml + "<type>VP</type>";
                    String block_code = request.getParameter("blockcode");
                    String panchayat_code = request.getParameter("pancode");
                    try {
                        String query =
                            "select block_code,blockname from com_mst_blocks where district_code=" +
                            dist_code + " order by block_code";
                        System.out.println(query);
                        ps = con.prepareStatement(query);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            xml = xml + "<bcount>";
                            xml =
 xml + "<block_code>" + rs.getString("block_code") + "</block_code>";
                            xml =
 xml + "<block_name>" + rs.getString("blockname") + "</block_name>";
                            xml = xml + "</bcount>";
                            gcount++;
                        }
                        xml = xml + "<flag1>success</flag1>";
                        xml = xml + "<blockcount>" + gcount + "</blockcount>";

                    } catch (SQLException e) {
                        xml = xml + "<flag1>failure</flag1>";
                        System.out.println("error is:" + e.getMessage());
                    }

                    try {
                        if (!block_code.equalsIgnoreCase("")) {
                            String query =
                                "select panch_code,panchayatname from com_mst_panchayats where district_code=" +
                                dist_code + " and block_code=" +
                                Integer.parseInt(block_code) +
                                " order by panch_code";
                            System.out.println(query);
                            ps = con.prepareStatement(query);
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                xml = xml + "<pcount>";
                                xml =
 xml + "<panchayat_code>" + rs.getString("panch_code") + "</panchayat_code>";
                                xml =
 xml + "<panchayat_name>" + rs.getString("panchayatname") +
   "</panchayat_name>";
                                xml = xml + "</pcount>";
                                gcount++;
                            }
                            xml = xml + "<flag2>success</flag2>";
                            xml =
 xml + "<panchayatcount>" + gcount + "</panchayatcount>";
                        } else
                            xml = xml + "<flag2>empty</flag2>";
                    } catch (SQLException e) {
                        xml = xml + "<flag2>failure</flag2>";
                        System.out.println("error is:" + e.getMessage());
                    }
                    try {
                        if (!panchayat_code.equalsIgnoreCase("")) {
                            String query =
                                "select hab_code,hname from com_mst_habitations where district_code=" +
                                dist_code + " and block_code=" +
                                Integer.parseInt(block_code) +
                                " and panch_code=" +
                                Integer.parseInt(panchayat_code) +
                                " order by hab_code";
                            System.out.println(query);
                            ps = con.prepareStatement(query);
                            rs = ps.executeQuery();
                            while (rs.next()) {
                                xml = xml + "<hcount>";
                                xml =
 xml + "<habitation_code>" + rs.getString("hab_code") + "</habitation_code>";
                                xml =
 xml + "<habitation_name><![CDATA[" + rs.getString("hname") +
   "]]></habitation_name>";
                                xml = xml + "</hcount>";
                                gcount++;
                            }
                            xml = xml + "<flag3>success</flag3>";
                            xml =
 xml + "<habitationcount>" + gcount + "</habitationcount>";
                        } else
                            xml = xml + "<flag3>empty</flag3>";
                    } catch (SQLException e) {
                        xml = xml + "<flag3>failure</flag3>";
                        System.out.println("error is:" + e.getMessage());
                    }

                } else {
                    xml = xml + "<type>empty</type>";
                }
                try {
                    if (!sourcetype.equalsIgnoreCase("")) {
                        System.out.println("source type not null");
                        String query =
                            "select SOURCE_CODE,DESCRIPTION from PMS_SOURCE_CODE where SOURCE_TYPE='" +
                            sourcetype + "'";
                        System.out.println(query);
                        ps = con.prepareStatement(query);
                        rs = ps.executeQuery();
                        while (rs.next()) {
                            xml = xml + "<source_count>";
                            xml =
 xml + "<sourcecode>" + rs.getString("SOURCE_CODE") + "</sourcecode>";
                            xml =
 xml + "<sourcetype>" + rs.getString("DESCRIPTION") + "</sourcetype>";
                            xml = xml + "</source_count>";
                            gcount++;
                        }
                        xml =
 xml + "<sourcecodecount>" + gcount + "</sourcecodecount>";
                        xml = xml + "<gflag>success</gflag>";
                    } else {
                        System.out.println("source type null");
                        xml = xml + "<gflag>failure</gflag>";
                    }
                } catch (SQLException e) {
                    xml = xml + "<gflag>failure</gflag>";
                    System.out.println("error is:" + e.getMessage());
                }
            } catch (Exception e) {
                System.out.println("error is:" + e.getMessage());
            }
        }
        /*else if(cmd.equalsIgnoreCase("EditRecord"))
        {
            xml = xml+"<command>EditRecord</command>";
            lab =Integer.parseInt(request.getParameter("lab"));
            int ino = Integer.parseInt(request.getParameter("ino"));
            int sample = Integer.parseInt(request.getParameter("sample"));
            try
            {
                ps=con.prepareStatement("select * from wqs_watersample_result where lab_code=? and invoice_number=? and sample_number=?");
                ps.setInt(1,lab);
                ps.setInt(2,ino);
                ps.setInt(3,sample);
                rs=ps.executeQuery();
                if(rs.next())
                {
                   xml=xml+"<record>found</record>";
                }
                else
                   xml=xml+"<record>notfound</record>";

            }
            catch(Exception e)
            {
                System.out.println("Err in EditRecord:"+e.getMessage());
            }
        }*/
        else if (cmd.equalsIgnoreCase("Update")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            int ino = Integer.parseInt(request.getParameter("ino"));
            int sample = Integer.parseInt(request.getParameter("sample"));
            String sample_type = request.getParameter("sample_type");
            test_purpose = request.getParameter("test_purpose");
            test_purpose =
                    test_purpose.substring(0, test_purpose.length() - 1);
            String test_purpose_id[] = test_purpose.split(",");
            String cdate = request.getParameter("cdate");
            String ctime = request.getParameter("ctime");
            String dcode = request.getParameter("dcode");
            String ltype = request.getParameter("ltype");
            String lbody = request.getParameter("lbody");
            String bcode = request.getParameter("bcode");
            String pcode = request.getParameter("pcode");
            String hcode = request.getParameter("hcode");
            String sctype = request.getParameter("sctype");
            String stype = request.getParameter("stype");
            String scode = request.getParameter("scode");
            String pgcode = request.getParameter("pgcode");
            String spoint = request.getParameter("spoint");
            String loc = request.getParameter("loc");
            xml = xml + "<command>Update</command>";
            try {
                for (int j = 0; j < test_purpose_id.length; j++) {
                    String query =
                        "update wqs_sample_entry set date_of_collection=to_date(?,'dd/mm/yyyy'),district_code=?,location_type=?,local_body_code=?,block_code=?,panchayat_code=?,habitation_code=?,scheme_type=?,source_type=?,source_code=?,programme=?,sampling_point=?,location=?,collection_time=?,updated_date=?,updated_by_user_id=?,sample_type=? where lab_code=? and invoice_number=? and sample_number=? and test_purpose_id=?";
                    System.out.println(query);
                    ps = con.prepareStatement(query);
                    ps.setString(1, cdate);
                    ps.setString(2, dcode);
                    ps.setString(3, ltype);
                    if (!lbody.equals(""))
                        ps.setInt(4, Integer.parseInt(lbody));
                    else
                        ps.setNull(4, Types.INTEGER);
                    if (!bcode.equals(""))
                        ps.setInt(5, Integer.parseInt(bcode));
                    else
                        ps.setNull(5, Types.INTEGER);
                    if (!pcode.equals(""))
                        ps.setInt(6, Integer.parseInt(pcode));
                    else
                        ps.setString(6, pcode);
                    if (!hcode.equals(""))
                        ps.setInt(7, Integer.parseInt(hcode));
                    else
                        ps.setString(7, hcode);
                    ps.setString(8, sctype);
                    ps.setString(9, stype);
                    ps.setString(10, scode);
                    ps.setString(11, pgcode);
                    ps.setString(12, spoint);
                    ps.setString(13, loc);
                    ps.setString(14, ctime);
                    ps.setTimestamp(15, ts);
                    ps.setString(16, updatedby);
                    ps.setString(17, sample_type);
                    ps.setInt(18, lab);
                    ps.setInt(19, ino);
                    ps.setInt(20, sample);
                    ps.setString(21, test_purpose_id[j]);
                    int i = ps.executeUpdate();
                    if (i > 0)
                        gcount++;
                }
                if (gcount == test_purpose_id.length) {
                    flag = "success";
                    xml = xml + "<flag>success</flag>";
                } else {
                    flag = "failure";
                    xml = xml + "<flag>failure</flag>";
                }
                ps.close();
            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("error is:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("Delete")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            int ino = Integer.parseInt(request.getParameter("ino"));
            int sample = Integer.parseInt(request.getParameter("sample"));
            xml = xml + "<command>Delete</command>";
            try {
                String query =
                    "delete from wqs_sample_entry where lab_code=? and invoice_number=? and sample_number=?";
                System.out.println(query);
                ps = con.prepareStatement(query);
                ps.setInt(1, lab);
                ps.setInt(2, ino);
                ps.setInt(3, sample);
                int i = ps.executeUpdate();
                if (i > 0)
                    xml = xml + "<flag>success</flag>";
                else
                    xml = xml + "<flag>failure</flag>";
            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("error is:" + e.getMessage());
            }
        }
        xml = xml + "</response>";
        System.out.println("xml=======>" + xml);
        out.println(xml);
        out.close();
    }
}
