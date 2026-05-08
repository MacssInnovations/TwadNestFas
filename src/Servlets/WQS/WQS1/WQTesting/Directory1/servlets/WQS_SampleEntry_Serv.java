package Servlets.WQS.WQS1.WQTesting.Directory1.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Connection;
import java.sql.Date;
import java.sql.Statement;
import java.sql.ResultSet;
import java.sql.PreparedStatement;

import java.sql.Timestamp;

import java.sql.Types;

import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class WQS_SampleEntry_Serv extends HttpServlet {
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
        PreparedStatement ps = null, ps3 = null;
        String cmd = null, xml = null, name = null, updatedby = null;
        int code = 0;
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
                System.out.println("inside district load");
                ps =
  con.prepareStatement("select a.district_code,decode(district_name,null,office_name,district_name)as district from" +
                       "(select office_id,office_name,district_code from com_mst_offices where office_id=?" +
                       ")a left outer join" +
                       "(select district_code,district_name from com_mst_districts" +
                       ")b on a.district_code=b.district_code");
                ps.setInt(1, lab);
                rs = ps.executeQuery();
                if (rs.next()) {
                    code = rs.getInt("district_code");
                    if (rs.getString("district").equalsIgnoreCase("-")) {
                        String distname[] =
                            rs.getString("off_name").split(",");
                        System.out.println(distname.length);
                        System.out.println("dname from off_name:" +
                                           distname[distname.length -
                                           1].trim());
                        name = distname[distname.length - 1].trim();
                    } else {
                        name = rs.getString("district");
                        System.out.println("dname from district name:" + name);
                    }
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
        } else if (cmd.equalsIgnoreCase("changeInvoice")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            int ino = Integer.parseInt(request.getParameter("invoice_number"));
            xml = xml + "<command>changeInvoice</command>";
            try {
                String query =
                    "select invoice_number,customer_type,total_samples from wqs_invoice_details where lab_code=? and invoice_number=? and process_flow_status_id in('CR','MD')";
                System.out.println(query);
                ps = con.prepareStatement(query);
                ps.setInt(1, lab);
                ps.setInt(2, ino);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml =
 xml + "<ctype>" + rs.getString("customer_type") + "</ctype>";
                    int tot = rs.getInt("total_samples");
                    xml = xml + "<tot_samples>" + tot + "</tot_samples>";
                    xml = xml + "<flag>success</flag>";
                    ps3 =
 con.prepareStatement("select count(distinct(sample_number))as totsample from wqs_sample_entry where lab_code=? and invoice_number=?");
                    ps3.setInt(1, lab);
                    ps3.setInt(2, ino);
                    rs2 = ps3.executeQuery();
                    if (rs2.next()) {
                        System.out.println("total samples:" +
                                           rs2.getString("totsample"));
                        xml =
 xml + "<count>" + rs2.getString("totsample") + "</count>";
                    }
                } else
                    xml = xml + "<flag>failure</flag>";

            } catch (SQLException e) {
                System.out.println("error is:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("changeSample")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            int ino = Integer.parseInt(request.getParameter("invoice_number"));
            int sno = Integer.parseInt(request.getParameter("sample_no"));
            System.out.println("lab:" + lab + "invoice:" + ino + "sample:" +
                               sno);
            xml = xml + "<command>changeSample</command>";
            try {
                String query =
                    "select sample_number from wqs_sample_entry where lab_code=? and invoice_number=? and sample_number=?";
                System.out.println(query);
                ps = con.prepareStatement(query);
                ps.setInt(1, lab);
                ps.setInt(2, ino);
                ps.setInt(3, sno);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml = xml + "<flag>failure</flag>";
                } else
                    xml = xml + "<flag>success</flag>";

            } catch (SQLException e) {
                System.out.println("error is:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("LoadRecord")) {
            String type = request.getParameter("type");
            int dist_code = Integer.parseInt(request.getParameter("distcode"));
            String sourcetype = request.getParameter("sourcetype");
            try {
                xml = xml + "<command>LoadRecord</command>";
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
 xml + "<code>" + rs.getInt("mucode") + "</code>";
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
 xml + "<code>" + rs.getInt("tpcode") + "</code>";
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
 xml + "<code>" + rs.getInt("tpcode") + "</code>";
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
                    System.out.println("block code:" + block_code);
                    String panchayat_code = request.getParameter("pancode");
                    System.out.println("pancode");
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
 xml + "<panchayat_code>" + rs.getInt("panch_code") + "</panchayat_code>";
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
 xml + "<habitation_code>" + rs.getInt("hab_code") + "</habitation_code>";
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
        } else if (cmd.equalsIgnoreCase("load")) {
            int labcode = Integer.parseInt(request.getParameter("lab"));
            xml = xml + "<command>load</command>";
            try {
                rs2 =
 stmt.executeQuery("select LAB_CODE,INVOICE_NUMBER,SAMPLE_NUMBER,DATE_OF_COLLECTION,DISTRICT_CODE,LOCATION_TYPE,LOCAL_BODY_CODE,BLOCK_CODE,PANCHAYAT_CODE,HABITATION_CODE,SCHEME_TYPE,SOURCE_TYPE,SOURCE_CODE,PROGRAMME,SAMPLING_POINT,LOCATION from WQS_SAMPLE_ENTRY where LAB_CODE=" +
                   labcode + "");
                while (rs2.next()) {
                    xml = xml + "<gcount>";
                    xml = xml + "<lab>" + rs2.getString("LAB_CODE") + "</lab>";
                    xml =
 xml + "<ino>" + rs2.getString("INVOICE_NUMBER") + "</ino>";
                    xml =
 xml + "<sno>" + rs2.getString("SAMPLE_NUMBER") + "</sno>";
                    xml =
 xml + "<cdate>" + rs2.getString("DATE_OF_COLLECTION") + "</cdate>";
                    xml =
 xml + "<dcode>" + rs2.getString("DISTRICT_CODE") + "</dcode>";
                    xml =
 xml + "<ltype>" + rs2.getString("LOCATION_TYPE") + "</ltype>";
                    xml =
 xml + "<lcode>" + rs2.getString("LOCAL_BODY_CODE") + "</lcode>";
                    xml =
 xml + "<bcode>" + rs2.getString("BLOCK_CODE") + "</bcode>";
                    xml =
 xml + "<pcode>" + rs2.getString("PANCHAYAT_CODE") + "</pcode>";
                    xml =
 xml + "<hcode>" + rs2.getString("HABITATION_CODE") + "</hcode>";
                    xml =
 xml + "<stype>" + rs2.getString("SCHEME_TYPE") + "</stype>";
                    xml =
 xml + "<source_type>" + rs2.getString("SOURCE_TYPE") + "</source_type>";
                    xml =
 xml + "<scode>" + rs2.getString("SOURCE_CODE") + "</scode>";
                    xml =
 xml + "<programme>" + rs2.getString("PROGRAMME") + "</programme>";
                    xml =
 xml + "<spoint>" + rs2.getString("SAMPLING_POINT") + "</spoint>";
                    xml =
 xml + "<location>" + rs2.getString("LOCATION") + "</location>";
                    xml = xml + "</gcount>";
                    gcount++;
                }
                xml = xml + "<grid_count>" + gcount + "</grid_count>";
                xml = xml + "<gflag>success</gflag>";
            } catch (SQLException e) {
                xml = xml + "<gflag>failure</gflag>";
                System.out.println(e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("load_invoice")) {
            int labcode = Integer.parseInt(request.getParameter("lab"));
            xml = xml + "<command>load_invoice</command>";
            try {
                String sql =
                    "select a.lab_code,a.invoice_number,a.invoice_date,a.invoice_amount,a.total_samples,a.customer_type,c.lab_code,c.scount from" +
                    "(select lab_code,invoice_number,invoice_date,invoice_amount,total_samples,customer_type,process_flow_status_id from wqs_invoice_details where lab_code=" +
                    labcode + " and process_flow_status_id in('CR','MD')" +
                    ")a left outer join" +
                    "(select count(distinct(sample_number)) as scount,invoice_number,lab_code from wqs_sample_entry group by lab_code,invoice_number" +
                    ")c on a.lab_code=c.lab_code and a.invoice_number=c.invoice_number order by a.invoice_number";
                rs2 = stmt.executeQuery(sql);
                while (rs2.next()) {
                    int tot_sample = rs2.getInt("total_samples");
                    int scount = rs2.getInt("scount");
                    if (scount < tot_sample) {
                        xml = xml + "<count>";
                        xml =
 xml + "<ino>" + rs2.getString("invoice_number") + "</ino>";
                        Date fdate = rs2.getDate("invoice_date");
                        Format formatter;
                        formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String f = formatter.format(fdate);
                        xml = xml + "<idate>" + f + "</idate>";
                        xml =
 xml + "<amt>" + rs2.getString("invoice_amount") + "</amt>";
                        xml =
 xml + "<ctype>" + rs2.getString("customer_type") + "</ctype>";
                        xml = xml + "</count>";
                        gcount++;
                    }
                }
                xml = xml + "<flag>success</flag>";
            } catch (SQLException e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println(e.getMessage());
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
        }
        xml = xml + "</response>";
        System.out.println("xml=======>" + xml);
        out.println(xml);
        out.close();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        Connection con = null;
        PreparedStatement ps = null, ps3 = null;
        ResultSet rs = null;
        String xml = "", cmd = null;
        String test_purpose_id[] = null;
        System.out.println("Welcome to servlet Post");
        /*
         * Check the User Session Information
         * If the Session is Closed then Print the Session Out Information
         */
        HttpSession session = request.getSession(false);
        try {
            if (session == null) {
                System.out.println("session out");
                return;
            }
        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        /*
         * Get the Updated User Information from the Session Attribute
         */
        String updatedby = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
        /*
         * Get the Database Connection Information from Config.properties File
         * In the Config.properties File we get the Driver Class and Connectivity Informations
         */
        try {
            ResourceBundle rss =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";
            String strDriver = rss.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rss.getString("Config.DSN");
            String strhostname = rss.getString("Config.HOST_NAME");
            String strportno = rss.getString("Config.PORT_NUMBER");
            String strsid = rss.getString("Config.SID");
            String strdbusername = rss.getString("Config.USER_NAME");
            String strdbpassword = rss.getString("Config.PASSWORD");
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (ClassNotFoundException e) {
            System.out.println("Connection err");
        } catch (SQLException e) {
            System.out.println("Sql err");
        }

        cmd = request.getParameter("option");
        xml = "<response>";
        if (cmd.equalsIgnoreCase("submit")) {
            System.out.println("inside Add");
            String labcode = request.getParameter("lcode");
            int ino = Integer.parseInt(request.getParameter("ino"));
            int labcodeno = Integer.parseInt(labcode);
            String record[] = request.getParameter("record").split(",,");
            xml = xml + "<command>submit</command>";
            int count = 0, i = 0;
            try {
                for (i = 0; i < record.length; i++) {
                    int count1 = 0;
                    con.setAutoCommit(false);
                    String a1 = record[i];
                    System.out.println(a1);
                    String[] rec1 = a1.split("~");
                    test_purpose_id = rec1[1].split(",");
                    for (int j = 0; j < test_purpose_id.length; j++) {
                        ps =
  con.prepareStatement("insert into wqs_sample_entry(lab_code,invoice_number,sample_number,test_purpose_id,sample_type,date_of_collection,district_code,location_type,local_body_code,block_code,panchayat_code,habitation_code,scheme_type,source_type,source_code,programme,sampling_point,location,collection_time,updated_date,updated_by_user_id) values(?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                        ps.setInt(1, labcodeno);
                        System.out.println("labcodeno---->" + labcodeno);
                        ps.setInt(2, ino);
                        System.out.println("ino---->" + ino);
                        ps.setInt(3, Integer.parseInt(rec1[0]));
                        System.out.println("sample---->" +
                                           Integer.parseInt(rec1[0]));
                        ps.setString(4, test_purpose_id[j]);
                        System.out.println("Test Purpose----->" +
                                           test_purpose_id[j]);
                        ps.setString(5, rec1[2]);
                        System.out.println("Sample Type----->" + rec1[2]);
                        ps.setString(6, rec1[3]);
                        System.out.println("cdate---->" + rec1[3]);
                        ps.setInt(7, Integer.parseInt(rec1[4]));
                        System.out.println("distcode---->" + rec1[4]);
                        ps.setString(8, rec1[5]);
                        System.out.println("ltype---->" + rec1[5]);
                        if (!rec1[6].equals(""))
                            ps.setInt(9, Integer.parseInt(rec1[6]));
                        else
                            ps.setNull(9, Types.INTEGER);
                        System.out.println("lbody---->" + rec1[6]);
                        if (!rec1[7].equals(""))
                            ps.setInt(10, Integer.parseInt(rec1[7]));
                        else
                            ps.setNull(10, Types.INTEGER);
                        System.out.println("blockcode---->" + rec1[7]);
                        if (!rec1[8].equals(""))
                            ps.setInt(11, Integer.parseInt(rec1[8]));
                        else
                            ps.setNull(11, Types.INTEGER);
                        System.out.println("pancode---->" + rec1[8]);
                        if (!rec1[9].equals(""))
                            ps.setString(12, rec1[9]);
                        else
                            ps.setNull(12, Types.INTEGER);
                        System.out.println("habitationcode---->" + rec1[9]);
                        ps.setString(13, rec1[10]);
                        System.out.println("schemetype---->" + rec1[10]);
                        ps.setString(14, rec1[11]);
                        System.out.println("sourcetype---->" + rec1[11]);
                        ps.setString(15, rec1[12]);
                        System.out.println("sourcecode---->" + rec1[12]);
                        ps.setString(16, rec1[13]);
                        System.out.println("programmecode---->" + rec1[13]);
                        ps.setString(17, rec1[14]);
                        System.out.println("spoint---->" + rec1[14]);
                        ps.setString(18, rec1[15]);
                        System.out.println("location---->" + rec1[15]);
                        ps.setString(19, rec1[16]);
                        System.out.println("time---->" + rec1[16]);
                        ps.setTimestamp(20, ts);
                        ps.setString(21, updatedby);
                        int cnt = ps.executeUpdate();
                        if (cnt > 0)
                            count1++;
                    }
                    if (test_purpose_id.length == count1)
                        count++;
                }
                if (count == record.length) {
                    ps3 =
 con.prepareStatement("update wqs_invoice_details set process_flow_status_id=? where lab_code=? and invoice_number=?");
                    ps3.setString(1, "MD");
                    ps3.setInt(2, labcodeno);
                    System.out.println("labcodeno---->" + labcodeno);
                    ps3.setInt(3, ino);
                    int j = ps3.executeUpdate();
                    if (j > 0) {
                        con.commit();
                        xml = xml + "<flag>success</flag>";
                    } else {
                        con.rollback();
                        xml = xml + "<flag>failure</flag>";
                    }
                } else {
                    con.rollback();
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }
            try {
                ps.close();
                ps3.close();
                con.close();
            } catch (Exception e) {
                System.out.println("Object Closed Exception " + e);
            }
        }
        xml += "</response>";
        xml = xml.trim();
        /*
         * Send the Response to the End User
         */
        System.out.println(xml);
        out.println(xml);
        out.close();
    }
}
