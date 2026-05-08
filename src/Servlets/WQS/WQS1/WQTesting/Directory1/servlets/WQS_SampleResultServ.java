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

import java.text.Format;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class WQS_SampleResultServ extends HttpServlet {
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
        response.setHeader("cache-control", "no-cache");
        Connection con = null;
        Statement st = null;
        PreparedStatement ps = null, ps1 = null;
        ResultSet rs = null, rs1 = null;
        String flag = "";
        String xml;
        Calendar c, c1;
        try {
            /*Class.forName("oracle.jdbc.OracleDriver");
                  con=DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe","wqlabs","wqlabs123");*/
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
        xml = "<response>";
        String cmd = request.getParameter("command");
        System.out.println(cmd);
        if (cmd.equalsIgnoreCase("changeCid")) {
            String cid = request.getParameter("custid");
            xml = xml + "<command>changeCid</command>";
            try {
                ps =
  con.prepareStatement("select customer_type,name from wqs_customer where customer_id=?");
                ps.setString(1, cid);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml = xml + "<cname>" + rs.getString("name") + "</cname>";
                    xml =
 xml + "<ctype>" + rs.getString("customer_type") + "</ctype>";
                    xml = xml + "<flag>Success</flag>";
                } else {
                    xml = xml + "<flag>Failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("Err in commit:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("changeReference")) {
            String rno = request.getParameter("rno");
            String cid = request.getParameter("cust_id");
            int lcode = Integer.parseInt(request.getParameter("lcode"));
            System.out.println("reg no:" + rno + "  lab code:" + lcode);
            xml = xml + "<command>changeReference</command>";
            try {
                String qry =
                    "select a.LOCATION,a.DATE_ENTRY,b.PROGRAMME_DESC,b.PROGRAMME_CODE,c.DISTRICTNAME,c.DISTRICT_CODE,d.BLOCKNAME,d.BLOCKCODE,e.PANCHAYATNAME,e.PANCH_CODE,f.NAME,f.HAB_CODE,g.SCHEME_TYPE_NAME,g.SCHEME_TYPE_ID,h.WATER_SOURCE_TYPE,h.WATER_SOURCE_TYPE_ID,i.SOURCE_CODE,i.DESCRIPTION";
                qry =
 qry + " " + "from(select PROGRAMME_CODE,RESURVEY_DIST_CODE,RESURVEY_BLK_CODE,RESURVEY_PAN_CODE,RESURVEY_HAB_CODE,SCM_TYPE,SOURCE_TYPE,SOURCE_CODE,LOCATION,DATE_ENTRY from PMS_SAMPLE where LAB_CODE=? and CUSTOMER_ID=? and CUSTOMER_REF_NO=?)a";
                qry =
 qry + " " + "left outer join(select PROGRAMME_CODE,PROGRAMME_DESC from MODE_OF_PROGRAMME)b on a.PROGRAMME_CODE=b.PROGRAMME_CODE";
                qry =
 qry + " " + "left outer join(select DISTRICT_CODE,DISTRICTNAME from COM_MST_DISTRICTS)c on a.RESURVEY_DIST_CODE=c.DISTRICT_CODE";
                qry =
 qry + " " + "left outer join(select DISTRICT_CODE,BLOCKCODE,BLOCKNAME from COM_MST_BLOCKS)d on a.RESURVEY_DIST_CODE=d.DISTRICT_CODE and a.RESURVEY_BLK_CODE=d.BLOCKCODE";
                qry =
 qry + " " + "left outer join(select DISTRICT_CODE,BLOCK_CODE,PANCH_CODE,PANCHAYATNAME from COM_MST_PANCHAYATS)e on a.RESURVEY_DIST_CODE=e.DISTRICT_CODE and a.RESURVEY_BLK_CODE=e.BLOCK_CODE and a.RESURVEY_PAN_CODE=e.PANCH_CODE";
                qry =
 qry + " " + "left outer join(select DISTRICT_CODE,BLOCK_CODE,PANCH_CODE,HAB_CODE,NAME from COM_MST_HABITATIONS)f on a.RESURVEY_DIST_CODE=f.DISTRICT_CODE and a.RESURVEY_BLK_CODE=f.BLOCK_CODE and a.RESURVEY_PAN_CODE=f.PANCH_CODE and a.RESURVEY_HAB_CODE=f.HAB_CODE";
                qry =
 qry + " " + "left outer join(select SCHEME_TYPE_ID,SCHEME_TYPE_NAME from RWS_MST_SCHEME_TYPES)g on a.SCM_TYPE=g.SCHEME_TYPE_ID";
                qry =
 qry + " " + "left outer join(select WATER_SOURCE_TYPE_ID,WATER_SOURCE_TYPE from RWS_MST_WATER_SOURCE_TYPE)h on a.SOURCE_TYPE=h.WATER_SOURCE_TYPE_ID";
                qry =
 qry + " " + "left outer join(select SOURCE_TYPE,SOURCE_CODE,DESCRIPTION from PMS_SOURCE_CODE)i on a.SOURCE_TYPE=i.SOURCE_TYPE and a.SOURCE_CODE=i.SOURCE_CODE";
                System.out.println("Query:" + qry);
                ps = con.prepareStatement(qry);
                ps.setInt(1, lcode);
                ps.setString(2, cid);
                ps.setString(3, rno);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml =
 xml + "<location>" + rs.getString("LOCATION") + "</location>";
                    Date tdate = rs.getDate("DATE_ENTRY");
                    Format formatter;
                    formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String f = formatter.format(tdate);

                    xml = xml + "<entrydate>" + f + "</entrydate>";
                    xml =
 xml + "<pdesc>" + rs.getString("PROGRAMME_DESC") + "</pdesc>";
                    xml =
 xml + "<pc>" + rs.getString("PROGRAMME_CODE") + "</pc>";
                    xml =
 xml + "<dname>" + rs.getString("DISTRICTNAME") + "</dname>";
                    xml =
 xml + "<dc>" + rs.getString("DISTRICT_CODE") + "</dc>";
                    xml =
 xml + "<bname>" + rs.getString("BLOCKNAME") + "</bname>";
                    xml = xml + "<bc>" + rs.getString("BLOCKCODE") + "</bc>";
                    xml =
 xml + "<pname>" + rs.getString("PANCHAYATNAME") + "</pname>";
                    xml =
 xml + "<pac>" + rs.getString("PANCH_CODE") + "</pac>";
                    xml = xml + "<hname>" + rs.getString("NAME") + "</hname>";
                    xml = xml + "<hc>" + rs.getString("HAB_CODE") + "</hc>";
                    xml =
 xml + "<sname>" + rs.getString("SCHEME_TYPE_NAME") + "</sname>";
                    xml =
 xml + "<sti>" + rs.getString("SCHEME_TYPE_ID") + "</sti>";
                    xml =
 xml + "<stype>" + rs.getString("WATER_SOURCE_TYPE") + "</stype>";
                    xml =
 xml + "<wst>" + rs.getString("WATER_SOURCE_TYPE_ID") + "</wst>";
                    xml =
 xml + "<srctype>" + rs.getString("DESCRIPTION") + "</srctype>";
                    xml =
 xml + "<sco>" + rs.getString("SOURCE_CODE") + "</sco>";
                    xml = xml + "<flag>Success</flag>";
                } else {
                    xml = xml + "<flag>Failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("Err in changeReference:" + e.getMessage());
            } finally {
                try {
                    ps.close();
                    rs.close();
                } catch (Exception e) {

                }
            }
        } else if (cmd.equalsIgnoreCase("changeSample")) {
            int lcode = Integer.parseInt(request.getParameter("LabCode"));
            System.out.println(lcode);
            String cid = request.getParameter("cid");
            String rno = request.getParameter("rno");
            int sno = Integer.parseInt(request.getParameter("sno"));
            System.out.println(sno);
            xml = xml + "<command>changeDate</command>";
            try {
                String qry =
                    "select SAMPLE_COLLECTION_DATE,SAMPLE_RECEIPT_DATE,CUSTOMER_TYPE,RESURVEY_DIST_CODE,RESURVEY_BLK_CODE,RESURVEY_PAN_CODE,RESURVEY_HAB_CODE,PROGRAMME_CODE,SCM_TYPE,SOURCE_TYPE,SOURCE_CODE,SOURCE_DIST_NAME,SOURCE_PAN_NAME,SOURCE_HAB_NAME,LOCATION,APPEAR,COLOR,ODOUR,TURB,TOTAL_SOLIDS,TOTAL_SUSPENDED_SOLIDS,TOTAL_DISSOLVED_SOLIDS,ELECTRICAL_CONDUCTIVITY,PH,ACIDITY,PHALK,TALK,TOTAL_HARDNESS,CALCIUM,MAGNESIUM,SODIUM,POTASSIUM,IRON,MANGANESE,AMMONIA,NITRITE,NITRATE,CHLORIDE,FLUORIDE,SULPHATE,PHOSPHATE,TIDYS,SILICA,TOTAL_KJELDHAL_NITROGEN,DO,BOD,COD,OIL_GREASE,RESIDUAL_CHLORINE,CYANIDE,ARSENIC,CADMIUM,COPPER,LEAD,CHROMIUM,ZINC,ALUMINIUM,SPC,TC,FC,FSC,CNC,PNP,REASON from WQS_SAMPLERESULT1 where LAB_CODE=" +
                    lcode + " and CUSTOMER_ID='" + cid +
                    "' and CUSTOMER_REF_NO='" + rno + "' and SAMPLE_NO=" + sno;
                System.out.println(qry);
                ps =
  con.prepareStatement("select SAMPLE_COLLECTION_DATE,SAMPLE_RECEIPT_DATE,CUSTOMER_TYPE,RESURVEY_DIST_CODE,RESURVEY_BLK_CODE,RESURVEY_PAN_CODE,RESURVEY_HAB_CODE,PROGRAMME_CODE,SCM_TYPE,SOURCE_TYPE,SOURCE_CODE,SOURCE_DIST_NAME,SOURCE_PAN_NAME,SOURCE_HAB_NAME,LOCATION,APPEAR,COLOR,ODOUR,TURB,TOTAL_SOLIDS,TOTAL_SUSPENDED_SOLIDS,TOTAL_DISSOLVED_SOLIDS,ELECTRICAL_CONDUCTIVITY,PH,ACIDITY,PHALK,TALK,TOTAL_HARDNESS,CALCIUM,MAGNESIUM,SODIUM,POTASSIUM,IRON,MANGANESE,AMMONIA,NITRITE,NITRATE,CHLORIDE,FLUORIDE,SULPHATE,PHOSPHATE,TIDYS,SILICA,TOTAL_KJELDHAL_NITROGEN,DO,BOD,COD,OIL_GREASE,RESIDUAL_CHLORINE,CYANIDE,ARSENIC,CADMIUM,COPPER,LEAD,CHROMIUM,ZINC,ALUMINIUM,SPC,TC,FC,FSC,CNC,PNP,REASON from WQS_SAMPLERESULT1 where LAB_CODE=? and CUSTOMER_ID=? and CUSTOMER_REF_NO=? and SAMPLE_NO=?");
                ps.setInt(1, lcode);
                System.out.println("lcode---->" + lcode);
                ps.setString(2, cid);
                System.out.println("cid---->" + cid);
                ps.setString(3, rno);
                System.out.println("rno---->" + rno);
                ps.setInt(4, sno);
                System.out.println("sno---->" + sno);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml =
 xml + "<CUSTOMER_TYPE>" + rs.getString("CUSTOMER_TYPE") + "</CUSTOMER_TYPE>";
                    Date sdate = rs.getDate("SAMPLE_COLLECTION_DATE");
                    Date rdate = rs.getDate("SAMPLE_RECEIPT_DATE");
                    Format formatter;
                    formatter = new SimpleDateFormat("dd/MM/yyyy");
                    String f = formatter.format(sdate);
                    String r = formatter.format(rdate);
                    xml =
 xml + "<SAMPLE_COLLECTION_DATE>" + f + "</SAMPLE_COLLECTION_DATE>";
                    xml =
 xml + "<SAMPLE_RECEIPT_DATE>" + r + "</SAMPLE_RECEIPT_DATE>";
                    String ctype = rs.getString("CUSTOMER_TYPE");
                    if (ctype.equalsIgnoreCase("Twad")) {
                        String pc = rs.getString("PROGRAMME_CODE");
                        System.out.println(pc);
                        try {
                            System.out.println("inside programme code");
                            System.out.println("select PROGRAMME_CODE,PROGRAMME_DESC from MODE_OF_PROGRAMME where PROGRAMME_CODE='" +
                                               pc + "'");
                            ps1 =
 con.prepareStatement("select PROGRAMME_CODE,PROGRAMME_DESC from MODE_OF_PROGRAMME where PROGRAMME_CODE=?");
                            ps1.setString(1, pc);
                            rs1 = ps1.executeQuery();
                            if (rs1.next()) {
                                System.out.println("pdesc:" +
                                                   rs1.getString("PROGRAMME_DESC"));
                                xml =
 xml + "<PROGRAMME_DESC>" + rs1.getString("PROGRAMME_DESC") +
   "</PROGRAMME_DESC>";
                            }
                        } catch (Exception e) {
                            System.out.println("Err in pgm code:" +
                                               e.getMessage());
                        }
                        xml =
 xml + "<PROGRAMME_CODE>" + rs.getString("PROGRAMME_CODE") +
   "</PROGRAMME_CODE>";
                        System.out.println("^^^^^^^^^:" +
                                           rs.getString("RESURVEY_DIST_CODE"));
                        String dc = rs.getString("RESURVEY_DIST_CODE");

                        try {
                            System.out.println("select DISTRICT_CODE,DISTRICTNAME from COM_MST_DISTRICTS where DISTRICT_CODE='" +
                                               dc + "'");
                            ps1 =
 con.prepareStatement("select DISTRICT_CODE,DISTRICTNAME from COM_MST_DISTRICTS where DISTRICT_CODE=?");
                            ps1.setString(1, dc);
                            rs1 = ps1.executeQuery();
                            if (rs1.next()) {
                                System.out.println("dname:" +
                                                   rs1.getString("DISTRICTNAME"));
                                xml =
 xml + "<DISTRICT_NAME>" + rs1.getString("DISTRICTNAME") + "</DISTRICT_NAME>";
                            }
                        } catch (SQLException e) {
                            System.out.println("Err in pgm code:" +
                                               e.getMessage());
                        }

                        xml =
 xml + "<RESURVEY_DIST_CODE>" + rs.getString("RESURVEY_DIST_CODE") +
   "</RESURVEY_DIST_CODE>";
                        String bc = rs.getString("RESURVEY_BLK_CODE");
                        try {
                            ps1 =
 con.prepareStatement("select DISTRICT_CODE,BLOCKCODE,BLOCKNAME from COM_MST_BLOCKS where DISTRICT_CODE=? and BLOCKCODE=?");
                            ps1.setString(1, dc);
                            ps1.setString(2, bc);
                            rs1 = ps1.executeQuery();
                            if (rs1.next()) {
                                System.out.println("bname:" +
                                                   rs1.getString("BLOCKNAME"));
                                xml =
 xml + "<BLOCK_NAME>" + rs1.getString("BLOCKNAME") + "</BLOCK_NAME>";
                            }
                        } catch (Exception e) {
                            System.out.println("Err in pgm code:" +
                                               e.getMessage());
                        }
                        xml =
 xml + "<RESURVEY_BLK_CODE>" + rs.getString("RESURVEY_BLK_CODE") +
   "</RESURVEY_BLK_CODE>";
                        String pac = rs.getString("RESURVEY_PAN_CODE");
                        try {
                            ps1 =
 con.prepareStatement("select DISTRICT_CODE,BLOCK_CODE,PANCH_CODE,PANCHAYATNAME from COM_MST_PANCHAYATS where DISTRICT_CODE=? and BLOCK_CODE=? and PANCH_CODE=?");
                            ps1.setString(1, dc);
                            ps1.setString(2, bc);
                            ps1.setString(3, pac);
                            rs1 = ps1.executeQuery();
                            if (rs1.next()) {
                                System.out.println("pan name:" +
                                                   rs1.getString("PANCHAYATNAME"));
                                xml =
 xml + "<PANCHAYAT_NAME>" + rs1.getString("PANCHAYATNAME") +
   "</PANCHAYAT_NAME>";
                            }
                        } catch (Exception e) {
                            System.out.println("Err in pgm code:" +
                                               e.getMessage());
                        }
                        xml =
 xml + "<RESURVEY_PAN_CODE>" + rs.getString("RESURVEY_PAN_CODE") +
   "</RESURVEY_PAN_CODE>";
                        String hc = rs.getString("RESURVEY_HAB_CODE");
                        try {
                            System.out.println("select DISTRICT_CODE,BLOCK_CODE,PANCH_CODE,HAB_CODE,NAME from COM_MST_HABITATIONS where DISTRICT_CODE=" +
                                               dc + " and BLOCK_CODE=" + bc +
                                               " and PANCH_CODE=" + pac +
                                               " and HAB_CODE=" + hc);
                            ps1 =
 con.prepareStatement("select DISTRICT_CODE,BLOCK_CODE,PANCH_CODE,HAB_CODE,NAME from COM_MST_HABITATIONS where DISTRICT_CODE=? and BLOCK_CODE=? and PANCH_CODE=? and HAB_CODE=?");
                            ps1.setString(1, dc);
                            ps1.setString(2, bc);
                            ps1.setString(3, pac);
                            ps1.setString(4, hc);
                            rs1 = ps1.executeQuery();
                            if (rs1.next()) {
                                System.out.println("hab name:" +
                                                   rs1.getString("NAME"));
                                xml =
 xml + "<HABITATION_NAME>" + rs1.getString("NAME") + "</HABITATION_NAME>";
                            }
                        } catch (Exception e) {
                            System.out.println("Err in pgm code:" +
                                               e.getMessage());
                        }
                        xml =
 xml + "<RESURVEY_HAB_CODE>" + rs.getString("RESURVEY_HAB_CODE") +
   "</RESURVEY_HAB_CODE>";
                        String sti = rs.getString("SCM_TYPE");
                        System.out.println("Scheme type id--------->" + sti);
                        try {
                            System.out.println("Scheme type query ========>select SCHEME_TYPE_ID,SCHEME_TYPE_NAME from RWS_MST_SCHEME_TYPES where SCHEME_TYPE_ID='" +
                                               sti + "'");
                            ps1 =
 con.prepareStatement("select SCHEME_TYPE_ID,SCHEME_TYPE_NAME from RWS_MST_SCHEME_TYPES where SCHEME_TYPE_ID=?");
                            ps1.setString(1, sti);
                            rs1 = ps1.executeQuery();
                            if (rs1.next()) {
                                xml =
 xml + "<SCHEME_TYPE_NAME>" + rs1.getString("SCHEME_TYPE_NAME") +
   "</SCHEME_TYPE_NAME>";
                            }
                        } catch (Exception e) {
                            System.out.println("Err in pgm code:" +
                                               e.getMessage());
                        }
                        xml =
 xml + "<SCM_TYPE>" + rs.getString("SCM_TYPE") + "</SCM_TYPE>";
                        String wst = rs.getString("SOURCE_TYPE");
                        String sco = rs.getString("SOURCE_CODE");
                        try {
                            ps1 =
 con.prepareStatement("select SOURCE_TYPE,SOURCE_CODE,DESCRIPTION from PMS_SOURCE_CODE where SOURCE_TYPE=? and SOURCE_CODE=?");
                            ps1.setString(1, wst);
                            ps1.setString(2, sco);
                            rs1 = ps1.executeQuery();
                            if (rs1.next()) {
                                xml =
 xml + "<SOURCE_TYPE1>" + rs1.getString("DESCRIPTION") + "</SOURCE_TYPE1>";
                            }
                        } catch (Exception e) {
                            System.out.println("Err in pgm code:" +
                                               e.getMessage());
                        }
                        xml =
 xml + "<SOURCE_CODE>" + rs.getString("SOURCE_CODE") + "</SOURCE_CODE>";
                        xml = xml + "<pc>true</pc>";
                    } else
                        xml = xml + "<pc>false</pc>";

                    String wst1 = rs.getString("SOURCE_TYPE");
                    try {
                        System.out.println("inside water source type");
                        ps1 =
 con.prepareStatement("select WATER_SOURCE_TYPE_ID,WATER_SOURCE_TYPE from RWS_MST_WATER_SOURCE_TYPE where WATER_SOURCE_TYPE_ID=?");
                        System.out.println("Water Source Type=======)>select WATER_SOURCE_TYPE_ID,WATER_SOURCE_TYPE from RWS_MST_WATER_SOURCE_TYPE where WATER_SOURCE_TYPE_ID='" +
                                           wst1 + "'");
                        ps1.setString(1, wst1);
                        rs1 = ps1.executeQuery();
                        if (rs1.next()) {
                            xml =
 xml + "<WATER_SOURCE_TYPE>" + rs1.getString("WATER_SOURCE_TYPE") +
   "</WATER_SOURCE_TYPE>";
                        }
                    } catch (Exception e) {
                        System.out.println("Err in pgm code:" +
                                           e.getMessage());
                    }
                    xml =
 xml + "<SOURCE_TYPE>" + rs.getString("SOURCE_TYPE") + "</SOURCE_TYPE>";
                    xml =
 xml + "<LOCATION>" + rs.getString("LOCATION") + "</LOCATION>";
                    xml =
 xml + "<SOURCE_DIST_NAME>" + rs.getString("SOURCE_DIST_NAME") +
   "</SOURCE_DIST_NAME>";
                    xml =
 xml + "<SOURCE_PAN_NAME>" + rs.getString("SOURCE_PAN_NAME") +
   "</SOURCE_PAN_NAME>";
                    xml =
 xml + "<SOURCE_HAB_NAME>" + rs.getString("SOURCE_HAB_NAME") +
   "</SOURCE_HAB_NAME>";
                    String appear = rs.getString("APPEAR");
                    String app = appear.replace("&", "*");
                    xml = xml + "<APPEAR>" + app + "</APPEAR>";
                    xml = xml + "<COLOR>" + rs.getString("COLOR") + "</COLOR>";
                    xml = xml + "<ODOUR>" + rs.getString("ODOUR") + "</ODOUR>";
                    xml = xml + "<TURB>" + rs.getString("TURB") + "</TURB>";
                    xml =
 xml + "<TOTAL_SOLIDS>" + rs.getString("TOTAL_SOLIDS") + "</TOTAL_SOLIDS>";
                    xml =
 xml + "<TOTAL_SUSPENDED_SOLIDS>" + rs.getString("TOTAL_SUSPENDED_SOLIDS") +
   "</TOTAL_SUSPENDED_SOLIDS>";
                    xml =
 xml + "<TOTAL_DISSOLVED_SOLIDS>" + rs.getString("TOTAL_DISSOLVED_SOLIDS") +
   "</TOTAL_DISSOLVED_SOLIDS>";
                    xml =
 xml + "<ELECTRICAL_CONDUCTIVITY>" + rs.getString("ELECTRICAL_CONDUCTIVITY") +
   "</ELECTRICAL_CONDUCTIVITY>";
                    xml = xml + "<PH>" + rs.getString("PH") + "</PH>";

                    xml =
 xml + "<ACIDITY>" + rs.getString("ACIDITY") + "</ACIDITY>";
                    xml =
 xml + "<PHALK>" + rs.getString("PHALK") + "</PHALK >";
                    xml = xml + "<TALK>" + rs.getString("TALK") + "</TALK >";
                    xml =
 xml + "<TOTAL_HARDNESS>" + rs.getString("TOTAL_HARDNESS") +
   "</TOTAL_HARDNESS>";
                    xml =
 xml + "<CALCIUM>" + rs.getString("CALCIUM") + "</CALCIUM>";
                    xml =
 xml + "<MAGNESIUM>" + rs.getString("MAGNESIUM") + "</MAGNESIUM>";
                    xml =
 xml + "<SODIUM>" + rs.getString("SODIUM") + "</SODIUM>";
                    xml =
 xml + "<POTASSIUM>" + rs.getString("POTASSIUM") + "</POTASSIUM>";

                    xml = xml + "<IRON>" + rs.getString("IRON") + "</IRON>";
                    xml =
 xml + "<MANGANESE>" + rs.getString("MANGANESE") + "</MANGANESE>";
                    xml =
 xml + "<AMMONIA>" + rs.getString("AMMONIA") + "</AMMONIA>";
                    xml =
 xml + "<NITRITE>" + rs.getString("NITRITE") + "</NITRITE>";
                    xml =
 xml + "<NITRATE>" + rs.getString("NITRATE") + "</NITRATE>";
                    xml =
 xml + "<CHLORIDE>" + rs.getString("CHLORIDE") + "</CHLORIDE>";
                    xml =
 xml + "<FLUORIDE>" + rs.getString("FLUORIDE") + "</FLUORIDE>";
                    xml =
 xml + "<SULPHATE>" + rs.getString("SULPHATE") + "</SULPHATE>";
                    xml =
 xml + "<PHOSPHATE>" + rs.getString("PHOSPHATE") + "</PHOSPHATE>";
                    xml = xml + "<TIDYS>" + rs.getString("TIDYS") + "</TIDYS>";

                    xml =
 xml + "<SILICA>" + rs.getString("SILICA") + "</SILICA>";
                    xml =
 xml + "<TOTAL_KJELDHAL_NITROGEN>" + rs.getString("TOTAL_KJELDHAL_NITROGEN") +
   "</TOTAL_KJELDHAL_NITROGEN>";
                    xml = xml + "<DO>" + rs.getString("DO") + "</DO>";
                    xml = xml + "<BOD>" + rs.getString("BOD") + "</BOD>";
                    xml = xml + "<COD>" + rs.getString("COD") + "</COD>";
                    xml =
 xml + "<OIL_GREASE>" + rs.getString("OIL_GREASE") + "</OIL_GREASE>";
                    xml =
 xml + "<RESIDUAL_CHLORINE>" + rs.getString("RESIDUAL_CHLORINE") +
   "</RESIDUAL_CHLORINE>";
                    xml =
 xml + "<CYANIDE>" + rs.getString("CYANIDE") + "</CYANIDE>";
                    xml =
 xml + "<ARSENIC>" + rs.getString("ARSENIC") + "</ARSENIC>";

                    xml =
 xml + "<CADMIUM>" + rs.getString("CADMIUM") + "</CADMIUM>";
                    xml =
 xml + "<COPPER>" + rs.getString("COPPER") + "</COPPER>";
                    xml = xml + "<LEAD>" + rs.getString("LEAD") + "</LEAD>";

                    xml =
 xml + "<CHROMIUM>" + rs.getString("CHROMIUM") + "</CHROMIUM>";

                    xml = xml + "<ZINC>" + rs.getString("ZINC") + "</ZINC >";

                    xml =
 xml + "<ALUMINIUM>" + rs.getString("ALUMINIUM") + "</ALUMINIUM>";
                    xml = xml + "<SPC>" + rs.getString("SPC") + "</SPC>";
                    xml = xml + "<TC>" + rs.getString("TC") + "</TC>";
                    xml = xml + "<FC>" + rs.getString("FC") + "</FC>";
                    xml = xml + "<FSC>" + rs.getString("FSC") + "</FSC>";
                    xml = xml + "<CNC>" + rs.getString("CNC") + "</CNC>";
                    xml = xml + "<PNP>" + rs.getString("PNP") + "</PNP>";
                    xml =
 xml + "<REASON>" + rs.getString("REASON") + "</REASON>";
                    xml = xml + "<flag>Success</flag>";
                } else {
                    xml = xml + "<flag>Failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("Err in changeDate:" + e.getMessage());
            } finally {
                try {
                    ps.close();
                    rs.close();
                } catch (Exception e) {

                }
            }
        } else if (cmd.equalsIgnoreCase("Del")) {
            System.out.println("delete");
            int lcode = Integer.parseInt(request.getParameter("LabCode"));
            System.out.println(lcode);
            String cid = request.getParameter("cid");
            System.out.println(cid);
            String rno = request.getParameter("rno");
            System.out.println(rno);
            String sno = request.getParameter("sno");
            System.out.println(sno);
            xml = xml + "<command>Del</command>";
            try {
                ps =
  con.prepareStatement("delete from WQS_SAMPLERESULT1 where LAB_CODE=? and CUSTOMER_ID=? and CUSTOMER_REF_NO=? and SAMPLE_NO=?");
                ps.setInt(1, lcode);
                ps.setString(2, cid);
                ps.setString(3, rno);
                ps.setString(4, sno);
                ps.executeUpdate();
                xml = xml + "<flag>Success</flag>";
            } catch (Exception e) {
                System.out.println("Err in deletion:" + e.getMessage());
            } finally {
                try {
                    ps.close();
                    rs.close();
                } catch (Exception e) {

                }
            }
        } else {
            if (cmd.equalsIgnoreCase("Update")) {
                System.out.println("inside upd");
                String val1[] = request.getParameter("val1").split("//");
                System.out.println(val1.length);
                for (int i = 0; i < val1.length; i++) {
                    if (val1[i].equalsIgnoreCase("-"))
                        val1[i] = "";
                }
                if (val1[4].equalsIgnoreCase("null"))
                    val1[4] = "-";
                System.out.println(val1[0] + " " + val1[1] + " " + val1[2] +
                                   " " + val1[3] + " " + val1[4] + " " +
                                   val1[5] + " " + val1[6] + " " + val1[7] +
                                   " " + val1[8] + " " + val1[9]);
                String val2[] = request.getParameter("val2").split("//");
                val2[9] = val2[9].replace("*", "&");
                for (int i = 0; i < val2.length; i++) {
                    if (val2[i].equalsIgnoreCase("-"))
                        val2[i] = "";
                }
                String val3[] = request.getParameter("val3").split("//");
                for (int i = 0; i < val3.length; i++) {
                    if (val3[i].equalsIgnoreCase("-"))
                        val3[i] = "";
                }
                String val4[] = request.getParameter("val4").split("//");
                for (int i = 0; i < val4.length; i++) {
                    if (val4[i].equalsIgnoreCase("-"))
                        val4[i] = "";
                }
                String val5[] = request.getParameter("val5").split("//");
                for (int i = 0; i < val5.length; i++) {
                    if (val5[i].equalsIgnoreCase("-"))
                        val5[i] = "";
                }
                String val6[] = request.getParameter("val6").split("//");
                for (int i = 0; i < val6.length; i++) {
                    if (val6[i].equalsIgnoreCase("-"))
                        val6[i] = "";
                }
                String val7[] = request.getParameter("val7").split("//");
                for (int i = 0; i < val7.length; i++) {
                    if (val7[i].equalsIgnoreCase("-"))
                        val7[i] = "";
                }
                System.out.println(val1[0] + " " + val1[1] + " " + val1[2] +
                                   " " + val1[3] + " " + val1[4] + " " +
                                   val1[5] + " " + val1[6] + " " + val1[7] +
                                   " " + val1[8] + " " + val1[9]);
                System.out.println(val2[0] + " " + val2[1] + " " + val2[2] +
                                   " " + val2[3] + " " + val2[4] + " " +
                                   val2[5] + " " + val2[6] + " " + val2[7] +
                                   " " + val2[8] + " " + val2[9]);
                System.out.println(val3[0] + " " + val3[1] + " " + val3[2] +
                                   " " + val3[3] + " " + val3[4] + " " +
                                   val3[5] + " " + val3[6] + " " + val3[7] +
                                   " " + val3[8] + " " + val3[9]);
                System.out.println(val4[0] + " " + val4[1] + " " + val4[2] +
                                   " " + val4[3] + " " + val4[4] + " " +
                                   val4[5] + " " + val4[6] + " " + val4[7] +
                                   " " + val4[8] + " " + val4[9]);
                System.out.println(val5[0] + " " + val5[1] + " " + val5[2] +
                                   " " + val5[3] + " " + val5[4] + " " +
                                   val5[5] + " " + val5[6] + " " + val5[7] +
                                   " " + val5[8] + " " + val5[9]);
                System.out.println(val6[0] + " " + val6[1] + " " + val6[2] +
                                   " " + val6[3] + " " + val6[4] + " " +
                                   val6[5] + " " + val6[6] + " " + val6[7] +
                                   " " + val6[8] + " " + val6[9]);
                System.out.println(val7[0] + " " + val7[1] + " " + val7[2] +
                                   " " + val7[3] + " " + val7[4] + " " +
                                   val7[5] + " " + val7[6] + " " + val7[7]);
                xml = xml + "<command>Update</command>";
                try {
                    String query =
                        "update WQS_SAMPLERESULT1 set SAMPLE_COLLECTION_DATE=?,SAMPLE_RECEIPT_DATE=?,CUSTOMER_TYPE=?,RESURVEY_DIST_CODE=?,RESURVEY_BLK_CODE=?,RESURVEY_PAN_CODE=?,RESURVEY_HAB_CODE=?,PROGRAMME_CODE=?,SCM_TYPE=?,SOURCE_TYPE=?,SOURCE_CODE=?,SOURCE_DIST_NAME=?,SOURCE_PAN_NAME=?,SOURCE_HAB_NAME=?,LOCATION=?,APPEAR=?,COLOR=?,ODOUR=?,TURB=?,TOTAL_SOLIDS=?,TOTAL_SUSPENDED_SOLIDS=?,TOTAL_DISSOLVED_SOLIDS=?,ELECTRICAL_CONDUCTIVITY=?,PH=?,ACIDITY=?,PHALK=?,TALK=?,TOTAL_HARDNESS=?,CALCIUM=?,MAGNESIUM=?,SODIUM=?,POTASSIUM=?,IRON=?,MANGANESE=?,AMMONIA=?,NITRITE=?,NITRATE=?,CHLORIDE=?,FLUORIDE=?,SULPHATE=?,PHOSPHATE=?,TIDYS=?,SILICA=?,TOTAL_KJELDHAL_NITROGEN=?,DO=?,BOD=?,COD=?,OIL_GREASE=?,RESIDUAL_CHLORINE=?,CYANIDE=?,ARSENIC=?,CADMIUM=?,COPPER=?,LEAD=?,CHROMIUM=?,ZINC=?,ALUMINIUM=?,SPC=?,TC=?,FC=?,FSC=?,CNC=?,PNP=?,REASON=? where LAB_CODE=? and CUSTOMER_ID=? and CUSTOMER_REF_NO=? and SAMPLE_NO=?";
                    ps = con.prepareStatement(query);
                    try {

                        Date rdate = null, sdate = null;
                        String[] od = val1[4].split("/");
                        c =
   new GregorianCalendar(Integer.parseInt(od[2]), Integer.parseInt(od[1]) - 1,
                         Integer.parseInt(od[0]));
                        java.util.Date d = c.getTime();
                        rdate = new Date(d.getTime());
                        System.out.println("Collection Date = " + rdate);
                        String[] od1 = val1[5].split("/");
                        c1 =
  new GregorianCalendar(Integer.parseInt(od1[2]), Integer.parseInt(od1[1]) - 1,
                        Integer.parseInt(od1[0]));
                        java.util.Date d1 = c1.getTime();
                        sdate = new Date(d1.getTime());
                        System.out.println("Receipt Date = " + sdate);
                        ps.setDate(1, rdate);
                        ps.setDate(2, sdate);
                        int intval1 = Integer.parseInt(val1[0]);
                        System.out.println("The Value 1 = " + intval1);
                        int intval2 = Integer.parseInt(val1[3]);
                        System.out.println("update WQS_SAMPLERESULT1 set SAMPLE_COLLECTION_DATE='" +
                                           rdate + "',SAMPLE_RECEIPT_DATE='" +
                                           sdate + "',CUSTOMER_TYPE='" +
                                           val1[6] + "',RESURVEY_DIST_CODE='" +
                                           val1[7] + "',RESURVEY_BLK_CODE='" +
                                           val1[8] + "',RESURVEY_PAN_CODE='" +
                                           val1[9] + "',RESURVEY_HAB_CODE='" +
                                           val2[0] + "',PROGRAMME_CODE='" +
                                           val2[1] + "',SCM_TYPE='" + val2[2] +
                                           "',SOURCE_TYPE='" + val2[3] +
                                           "',SOURCE_CODE='" + val2[4] +
                                           "',SOURCE_DIST_NAME='" + val2[5] +
                                           "',SOURCE_PAN_NAME='" + val2[6] +
                                           "',SOURCE_HAB_NAME='" + val2[7] +
                                           "',LOCATION='" + val2[8] +
                                           "',APPEAR='" + val2[9] +
                                           "',COLOR='" + val3[0] +
                                           "',ODOUR='" + val3[1] + "',TURB='" +
                                           val3[2] + "',TOTAL_SOLIDS='" +
                                           val3[3] +
                                           "',TOTAL_SUSPENDED_SOLIDS='" +
                                           val3[4] +
                                           "',TOTAL_DISSOLVED_SOLIDS='" +
                                           val3[5] +
                                           "',ELECTRICAL_CONDUCTIVITY='" +
                                           val3[6] + "',PH='" + val3[7] +
                                           "',ACIDITY='" + val3[8] +
                                           "',PHALK='" + val3[9] + "',TALK='" +
                                           val4[0] + "',TOTAL_HARDNESS='" +
                                           val4[1] + "',CALCIUM='" + val4[2] +
                                           "',MAGNESIUM='" + val4[3] +
                                           "',SODIUM='" + val4[4] +
                                           "',POTASSIUM='" + val4[5] +
                                           "',IRON='" + val4[6] +
                                           "',MANGANESE='" + val4[7] +
                                           "',AMMONIA='" + val4[8] +
                                           "',NITRITE='" + val4[9] +
                                           "',NITRATE='" + val5[0] +
                                           "',CHLORIDE='" + val5[1] +
                                           "',FLUORIDE='" + val5[2] +
                                           "',SULPHATE='" + val5[3] +
                                           "',PHOSPHATE='" + val5[4] +
                                           "',TIDYS='" + val5[5] +
                                           "',SILICA='" + val5[6] +
                                           "',TOTAL_KJELDHAL_NITROGEN='" +
                                           val5[7] + "',DO='" + val5[8] +
                                           "',BOD='" + val5[9] + "',COD='" +
                                           val6[0] + "',OIL_GREASE='" +
                                           val6[1] + "',RESIDUAL_CHLORINE='" +
                                           val6[2] + "',CYANIDE='" + val6[3] +
                                           "',ARSENIC='" + val6[4] +
                                           "',CADMIUM='" + val6[5] +
                                           "',COPPER='" + val6[6] +
                                           "',LEAD='" + val6[7] +
                                           "',CHROMIUM='" + val6[8] +
                                           "',ZINC='" + val6[9] +
                                           "',ALUMINIUM='" + val7[0] +
                                           "',SPC='" + val7[1] + "',TC='" +
                                           val7[2] + "',FC='" + val7[3] +
                                           "',FSC='" + val7[4] + "',CNC='" +
                                           val7[5] + "',PNP='" + val7[6] +
                                           "',REASON='" + val7[7] +
                                           "' where LAB_CODE=" + intval1 +
                                           ",CUSTOMER_ID='" + val1[1] +
                                           "',CUSTOMER_REF_NO='" + val1[2] +
                                           "',SAMPLE_NO=" + intval2);
                        ps.setString(3, val1[6]);
                        System.out.println("CUSTOMER_TYPE = " + val1[6]);
                        ps.setString(4, val1[7]);
                        System.out.println("RESURVEY_DIST_CODE = " + val1[7]);
                        ps.setString(5, val1[8]);
                        System.out.println("RESURVEY_BLK_CODE = " + val1[8]);
                        ps.setString(6, val1[9]);
                        System.out.println("RESURVEY_PAN_CODE = " + val1[9]);

                        ps.setString(7, val2[0]);
                        System.out.println("RESURVEY_HAB_CODE = " + val2[0]);
                        ps.setString(8, val2[1]);
                        System.out.println("PROGRAMME_CODE = " + val2[1]);
                        ps.setString(9, val2[2]);
                        System.out.println("SCM_TYPE = " + val2[2]);
                        ps.setString(10, val2[3]);
                        System.out.println("SOURCE_TYPE = " + val2[3]);
                        ps.setString(11, val2[4]);
                        System.out.println("SOURCE_CODE = " + val2[4]);
                        ps.setString(12, val2[5]);
                        System.out.println("SOURCE_DIST_NAME = " + val2[5]);
                        ps.setString(13, val2[6]);
                        System.out.println("SOURCE_PAN_NAME = " + val2[6]);
                        ps.setString(14, val2[7]);
                        System.out.println("SOURCE_HAB_NAME = " + val2[7]);
                        ps.setString(15, val2[8]);
                        System.out.println("LOCATION = " + val2[8]);
                        ps.setString(16, val2[9]);
                        System.out.println("APPEAR = " + val2[9]);

                        ps.setString(17, val3[0]);
                        System.out.println("COLOR = " + val3[0]);
                        ps.setString(18, val3[1]);
                        System.out.println("ODOUR = " + val3[1]);
                        ps.setString(19, val3[2]);
                        System.out.println("TURB = " + val3[2]);
                        ps.setString(20, val3[3]);
                        System.out.println("TOTAL_SOLIDS = " + val3[3]);
                        ps.setString(21, val3[4]);
                        System.out.println("TOTAL_SUSPENDED_SOLIDS = " +
                                           val3[4]);
                        ps.setString(22, val3[5]);
                        System.out.println("TOTAL_DISSOLVED_SOLIDS = " +
                                           val3[5]);
                        ps.setString(23, val3[6]);
                        System.out.println("ELECTRICAL_CONDUCTIVITY = " +
                                           val3[6]);
                        ps.setString(24, val3[7]);
                        System.out.println("PH = " + val3[7]);
                        ps.setString(25, val3[8]);
                        System.out.println("ACIDITY = " + val3[8]);
                        ps.setString(26, val3[9]);
                        System.out.println("PHALK = " + val3[9]);

                        ps.setString(27, val4[0]);
                        System.out.println("TALK = " + val4[0]);
                        ps.setString(28, val4[1]);
                        System.out.println("TOTAL_HARDNESS = " + val4[1]);
                        ps.setString(29, val4[2]);
                        System.out.println("CALCIUM = " + val4[2]);
                        ps.setString(30, val4[3]);
                        System.out.println("MAGNESIUM = " + val4[3]);
                        ps.setString(31, val4[4]);
                        System.out.println("SODIUM = " + val4[4]);
                        ps.setString(32, val4[5]);
                        System.out.println("POTASSIUM = " + val4[5]);
                        ps.setString(33, val4[6]);
                        System.out.println("IRON = " + val4[6]);
                        ps.setString(34, val4[7]);
                        System.out.println("MANGANESE = " + val4[7]);
                        ps.setString(35, val4[8]);
                        System.out.println("AMMONIA = " + val4[8]);
                        ps.setString(36, val4[9]);
                        System.out.println("NITRITE = " + val4[9]);

                        ps.setString(37, val5[0]);
                        System.out.println("NITRATE = " + val5[0]);
                        ps.setString(38, val5[1]);
                        System.out.println("CHLORIDE = " + val5[1]);
                        ps.setString(39, val5[2]);
                        System.out.println("FLUORIDE = " + val5[2]);
                        ps.setString(40, val5[3]);
                        System.out.println("SULPHATE = " + val5[3]);
                        ps.setString(41, val5[4]);
                        System.out.println("PHOSPHATE = " + val5[4]);
                        ps.setString(42, val5[5]);
                        System.out.println("TIDYS = " + val5[5]);
                        ps.setString(43, val5[6]);
                        System.out.println("SILICA = " + val5[6]);
                        ps.setString(44, val5[7]);
                        System.out.println("TOTAL_KJELDHAL_NITROGEN = " +
                                           val5[7]);
                        ps.setString(45, val5[8]);
                        System.out.println("DO = " + val5[8]);
                        ps.setString(46, val5[9]);
                        System.out.println("BOD = " + val5[9]);

                        ps.setString(47, val6[0]);
                        System.out.println("COD = " + val6[0]);
                        ps.setString(48, val6[1]);
                        System.out.println("OIL_GREASE = " + val6[1]);
                        ps.setString(49, val6[2]);
                        System.out.println("RESIDUAL_CHLORINE = " + val6[2]);
                        ps.setString(50, val6[3]);
                        System.out.println("CYANIDE = " + val6[3]);
                        ps.setString(51, val6[4]);
                        System.out.println("ARSENIC = " + val6[4]);
                        ps.setString(52, val6[5]);
                        System.out.println("CADMIUM = " + val6[5]);
                        ps.setString(53, val6[6]);
                        System.out.println("COPPER = " + val6[6]);
                        ps.setString(54, val6[7]);
                        System.out.println("LEAD = " + val6[7]);
                        ps.setString(55, val6[8]);
                        System.out.println("CHROMIUM = " + val6[8]);
                        ps.setString(56, val6[9]);
                        System.out.println("ZINC = " + val6[9]);

                        ps.setString(57, val7[0]);
                        System.out.println("ALUMINIUM = " + val7[0]);
                        ps.setString(58, val7[1]);
                        System.out.println("SPC = " + val7[1]);
                        ps.setString(59, val7[2]);
                        System.out.println("TC = " + val7[2]);
                        ps.setString(60, val7[3]);
                        System.out.println("FC = " + val7[3]);
                        ps.setString(61, val7[4]);
                        System.out.println("FSC = " + val7[4]);
                        ps.setString(62, val7[5]);
                        System.out.println("CNC=" + val7[5]);
                        ps.setString(63, val7[6]);
                        System.out.println("PNP = " + val7[6]);
                        ps.setString(64, val7[7]);
                        System.out.println("REASON=" + val7[7]);

                        /*  int intval1 = Integer.parseInt(val1[0]);
                        System.out.println("The Value 1 = "+intval1);*/
                        ps.setInt(65, intval1);
                        System.out.println("LAB_CODE = " + intval1);
                        ps.setString(66, val1[1]);
                        System.out.println("CUSTOMER_ID = " + val1[1]);
                        ps.setString(67, val1[2]);
                        System.out.println("CUSTOMER_REF_NO = " + val1[2]);
                        /*int intval2 = Integer.parseInt(val1[3]);*/
                        ps.setInt(68, intval2);
                        System.out.println("SAMPLE_NO = " + intval2);
                        ps.executeUpdate();
                        System.out.println("Successfully Updated into the Table");
                        xml = xml + "<flag>Success</flag>";
                    } catch (Exception e) {
                        System.out.println("Exception in connection:" + e);
                        // con.rollback();
                        String msg = "<br><br>" + e;
                        /*  sendMessage(response,msg,"ok");*/
                        System.out.println("Exception " + e);
                    }
                } catch (Exception e) {
                    System.out.println("Err in Update:" + e.getMessage());
                } finally {
                    try {
                        ps.close();
                        rs.close();
                    } catch (Exception e) {

                    }
                }
            } else if (cmd.equalsIgnoreCase("Add")) {
                System.out.println("inside add");
                String val1[] = request.getParameter("val1").split("//");
                System.out.println(val1.length);
                for (int i = 0; i < val1.length; i++) {
                    if (val1[i].equalsIgnoreCase("-"))
                        val1[i] = "";
                }
                if (val1[4].equalsIgnoreCase("null"))
                    val1[4] = "-";
                System.out.println(val1[0] + " " + val1[1] + " " + val1[2] +
                                   " " + val1[3] + " " + val1[4] + " " +
                                   val1[5] + " " + val1[6] + " " + val1[7] +
                                   " " + val1[8] + " " + val1[9]);
                String val2[] = request.getParameter("val2").split("//");
                val2[9] = val2[9].replace("*", "&");
                for (int i = 0; i < val2.length; i++) {
                    if (val2[i].equalsIgnoreCase("-"))
                        val2[i] = "";
                }
                String val3[] = request.getParameter("val3").split("//");
                for (int i = 0; i < val3.length; i++) {
                    if (val3[i].equalsIgnoreCase("-"))
                        val3[i] = "";
                }
                String val4[] = request.getParameter("val4").split("//");
                for (int i = 0; i < val4.length; i++) {
                    if (val4[i].equalsIgnoreCase("-"))
                        val4[i] = "";
                }
                String val5[] = request.getParameter("val5").split("//");
                for (int i = 0; i < val5.length; i++) {
                    if (val5[i].equalsIgnoreCase("-"))
                        val5[i] = "";
                }
                String val6[] = request.getParameter("val6").split("//");
                for (int i = 0; i < val6.length; i++) {
                    if (val6[i].equalsIgnoreCase("-"))
                        val6[i] = "";
                }
                String val7[] = request.getParameter("val7").split("//");
                for (int i = 0; i < val7.length; i++) {
                    if (val7[i].equalsIgnoreCase("-"))
                        val7[i] = "";
                }
                System.out.println(val1[0] + " " + val1[1] + " " + val1[2] +
                                   " " + val1[3] + " " + val1[4] + " " +
                                   val1[5] + " " + val1[6] + " " + val1[7] +
                                   " " + val1[8] + " " + val1[9]);
                System.out.println(val2[0] + " " + val2[1] + " " + val2[2] +
                                   " " + val2[3] + " " + val2[4] + " " +
                                   val2[5] + " " + val2[6] + " " + val2[7] +
                                   " " + val2[8] + " " + val2[9]);
                System.out.println(val3[0] + " " + val3[1] + " " + val3[2] +
                                   " " + val3[3] + " " + val3[4] + " " +
                                   val3[5] + " " + val3[6] + " " + val3[7] +
                                   " " + val3[8] + " " + val3[9]);
                System.out.println(val4[0] + " " + val4[1] + " " + val4[2] +
                                   " " + val4[3] + " " + val4[4] + " " +
                                   val4[5] + " " + val4[6] + " " + val4[7] +
                                   " " + val4[8] + " " + val4[9]);
                System.out.println(val5[0] + " " + val5[1] + " " + val5[2] +
                                   " " + val5[3] + " " + val5[4] + " " +
                                   val5[5] + " " + val5[6] + " " + val5[7] +
                                   " " + val5[8] + " " + val5[9]);
                System.out.println(val6[0] + " " + val6[1] + " " + val6[2] +
                                   " " + val6[3] + " " + val6[4] + " " +
                                   val6[5] + " " + val6[6] + " " + val6[7] +
                                   " " + val6[8] + " " + val6[9]);
                System.out.println(val7[0] + " " + val7[1] + " " + val7[2] +
                                   " " + val7[3] + " " + val7[4] + " " +
                                   val7[5] + " " + val7[6] + " " + val7[7]);
                xml = xml + "<command>Add</command>";
                try {
                    con.setAutoCommit(false);
                    String query =
                        "Insert into WQS_SAMPLERESULT1(LAB_CODE,CUSTOMER_ID,CUSTOMER_REF_NO,SAMPLE_NO,SAMPLE_COLLECTION_DATE,SAMPLE_RECEIPT_DATE,CUSTOMER_TYPE,RESURVEY_DIST_CODE,RESURVEY_BLK_CODE,RESURVEY_PAN_CODE,RESURVEY_HAB_CODE,PROGRAMME_CODE,SCM_TYPE,SOURCE_TYPE,SOURCE_CODE,SOURCE_DIST_NAME,SOURCE_PAN_NAME,SOURCE_HAB_NAME,LOCATION,APPEAR,COLOR,ODOUR,TURB,TOTAL_SOLIDS,TOTAL_SUSPENDED_SOLIDS,TOTAL_DISSOLVED_SOLIDS,ELECTRICAL_CONDUCTIVITY,PH,ACIDITY,PHALK,TALK,TOTAL_HARDNESS,CALCIUM,MAGNESIUM,SODIUM,POTASSIUM,IRON,MANGANESE,AMMONIA,NITRITE,NITRATE,CHLORIDE,FLUORIDE,SULPHATE,PHOSPHATE,TIDYS,SILICA,TOTAL_KJELDHAL_NITROGEN,DO,BOD,COD,OIL_GREASE,RESIDUAL_CHLORINE,CYANIDE,ARSENIC,CADMIUM,COPPER,LEAD,CHROMIUM,ZINC,ALUMINIUM,SPC,TC,FC,FSC,CNC,PNP,REASON) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    System.out.println("Insert into WQS_SAMPLERESULT1(LAB_CODE,CUSTOMER_ID,CUSTOMER_REF_NO,SAMPLE_NO,SAMPLE_COLLECTION_DATE,SAMPLE_RECEIPT_DATE,CUSTOMER_TYPE,RESURVEY_DIST_CODE,RESURVEY_BLK_CODE,RESURVEY_PAN_CODE,RESURVEY_HAB_CODE,PROGRAMME_CODE,SCM_TYPE,SOURCE_TYPE,SOURCE_CODE,SOURCE_DIST_NAME,SOURCE_PAN_NAME,SOURCE_HAB_NAME,LOCATION,APPEAR,COLOR,ODOUR,TURB,TOTAL_SOLIDS,TOTAL_SUSPENDED_SOLIDS,TOTAL_DISSOLVED_SOLIDS,ELECTRICAL_CONDUCTIVITY,PH,ACIDITY,PHALK,TALK,TOTAL_HARDNESS,CALCIUM,MAGNESIUM,SODIUM,POTASSIUM,IRON,MANGANESE,AMMONIA,NITRITE,NITRATE,CHLORIDE,FLUORIDE,SULPHATE,PHOSPHATE,TIDYS,SILICA,TOTAL_KJELDHAL_NITROGEN,DO,BOD,COD,OIL_GREASE,RESIDUAL_CHLORINE,CYANIDE,ARSENIC,CADMIUM,COPPER,LEAD,CHROMIUM,ZINC,ALUMINIUM,SPC,TC,FC,FSC,CNC,PNP,REASON) values(" +
                                       val1[0] + "," + val1[1] + ",'" +
                                       val1[2] + "','" + val1[3] + "','" +
                                       val1[4] + "','" + val1[5] + "','" +
                                       val1[6] + "','" + val1[7] + "','" +
                                       val1[8] + "','" + val1[9] + "','" +
                                       val2[0] + "','" + val2[1] + "','" +
                                       val2[2] + "','" + val2[3] + "','" +
                                       val2[4] + "','" + val2[5] + "','" +
                                       val2[6] + "','" + val2[7] + "','" +
                                       val2[8] + "','" + val2[9] + "','" +
                                       val3[0] + "','" + val3[1] + "','" +
                                       val3[2] + "','" + val3[3] + "','" +
                                       val3[4] + "','" + val3[5] + "','" +
                                       val3[6] + "','" + val3[7] + "','" +
                                       val4[0] + "','" + val4[1] + "','" +
                                       val4[2] + "','" + val4[3] + "','" +
                                       val4[4] + "','" + val4[5] + "','" +
                                       val4[6] + "','" + val4[7] + "','" +
                                       val4[8] + "','" + val4[9] + "','" +
                                       val5[0] + "','" + val5[1] + "','" +
                                       val5[2] + "','" + val5[3] + "','" +
                                       val5[4] + "','" + val5[5] + "','" +
                                       val5[6] + "','" + val5[7] + "','" +
                                       val5[8] + "','" + val5[9] + "','" +
                                       val6[0] + "','" + val6[1] + "','" +
                                       val6[2] + "','" + val6[3] + "','" +
                                       val6[4] + "','" + val6[5] + "','" +
                                       val6[6] + "','" + val6[7] + "')");
                    ps = con.prepareStatement(query);
                    try {
                        int intval1 = Integer.parseInt(val1[0]);
                        System.out.println("The Value 1 = " + intval1);
                        ps.setInt(1, intval1);
                        System.out.println("LAB_CODE = " + intval1);
                        ps.setString(2, val1[1]);
                        System.out.println("CUSTOMER_ID = " + val1[1]);
                        ps.setString(3, val1[2]);
                        System.out.println("CUSTOMER_REF_NO = " + val1[2]);
                        int intval2 = Integer.parseInt(val1[3]);
                        ps.setInt(4, intval2);
                        System.out.println("SAMPLE_NO = " + intval2);
                        Date rdate = null, sdate = null;
                        String[] od = val1[4].split("/");
                        c =
   new GregorianCalendar(Integer.parseInt(od[2]), Integer.parseInt(od[1]) - 1,
                         Integer.parseInt(od[0]));
                        java.util.Date d = c.getTime();
                        rdate = new Date(d.getTime());
                        System.out.println("Collection Date = " + rdate);
                        String[] od1 = val1[5].split("/");
                        c1 =
  new GregorianCalendar(Integer.parseInt(od1[2]), Integer.parseInt(od1[1]) - 1,
                        Integer.parseInt(od1[0]));
                        java.util.Date d1 = c1.getTime();
                        sdate = new Date(d1.getTime());
                        System.out.println("Receipt Date = " + sdate);
                        ps.setDate(5, rdate);
                        ps.setDate(6, sdate);
                        ps.setString(7, val1[6]);
                        System.out.println("CUSTOMER_TYPE = " + val1[6]);
                        ps.setString(8, val1[7]);
                        System.out.println("RESURVEY_DIST_CODE = " + val1[7]);
                        ps.setString(9, val1[8]);
                        System.out.println("RESURVEY_BLK_CODE = " + val1[8]);
                        ps.setString(10, val1[9]);
                        System.out.println("RESURVEY_PAN_CODE = " + val1[9]);

                        ps.setString(11, val2[0]);
                        System.out.println("RESURVEY_HAB_CODE = " + val2[0]);
                        ps.setString(12, val2[1]);
                        System.out.println("PROGRAMME_CODE = " + val2[1]);
                        ps.setString(13, val2[2]);
                        System.out.println("SCM_TYPE = " + val2[2]);
                        ps.setString(14, val2[3]);
                        System.out.println("SOURCE_TYPE = " + val2[3]);
                        ps.setString(15, val2[4]);
                        System.out.println("SOURCE_CODE = " + val2[4]);
                        ps.setString(16, val2[5]);
                        System.out.println("LOCATION = " + val2[5]);
                        ps.setString(17, val2[6]);
                        System.out.println("SOURCE_DIST_NAME = " + val2[6]);
                        ps.setString(18, val2[7]);
                        System.out.println("SOURCE_PAN_NAME = " + val2[7]);
                        ps.setString(19, val2[8]);
                        System.out.println("SOURCE_HAB_NAME = " + val2[8]);
                        ps.setString(20, val2[9]);
                        System.out.println("APPEAR = " + val2[9]);

                        ps.setString(21, val3[0]);
                        System.out.println("COLOR = " + val3[0]);
                        ps.setString(22, val3[1]);
                        System.out.println("ODOUR = " + val3[1]);
                        ps.setString(23, val3[2]);
                        System.out.println("TURB = " + val3[2]);
                        ps.setString(24, val3[3]);
                        System.out.println("TOTAL_SOLIDS = " + val3[3]);
                        ps.setString(25, val3[4]);
                        System.out.println("TOTAL_SUSPENDED_SOLIDS = " +
                                           val3[4]);
                        ps.setString(26, val3[5]);
                        System.out.println("TOTAL_DISSOLVED_SOLIDS = " +
                                           val3[5]);
                        ps.setString(27, val3[6]);
                        System.out.println("ELECTRICAL_CONDUCTIVITY = " +
                                           val3[6]);
                        ps.setString(28, val3[7]);
                        System.out.println("PH = " + val3[7]);
                        ps.setString(29, val3[8]);
                        System.out.println("ACIDITY = " + val3[8]);
                        ps.setString(30, val3[9]);
                        System.out.println("PHALK = " + val3[9]);

                        ps.setString(31, val4[0]);
                        System.out.println("TALK = " + val4[0]);
                        ps.setString(32, val4[1]);
                        System.out.println("TOTAL_HARDNESS = " + val4[1]);
                        ps.setString(33, val4[2]);
                        System.out.println("CALCIUM = " + val4[2]);
                        ps.setString(34, val4[3]);
                        System.out.println("MAGNESIUM = " + val4[3]);
                        ps.setString(35, val4[4]);
                        System.out.println("SODIUM = " + val4[4]);
                        ps.setString(36, val4[5]);
                        System.out.println("POTASSIUM = " + val4[5]);
                        ps.setString(37, val4[6]);
                        System.out.println("IRON = " + val4[6]);
                        ps.setString(38, val4[7]);
                        System.out.println("MANGANESE = " + val4[7]);
                        ps.setString(39, val4[8]);
                        System.out.println("AMMONIA = " + val4[8]);
                        ps.setString(40, val4[9]);
                        System.out.println("NITRITE = " + val4[9]);

                        ps.setString(41, val5[0]);
                        System.out.println("NITRATE = " + val5[0]);
                        ps.setString(42, val5[1]);
                        System.out.println("CHLORIDE = " + val5[1]);
                        ps.setString(43, val5[2]);
                        System.out.println("FLUORIDE = " + val5[2]);
                        ps.setString(44, val5[3]);
                        System.out.println("SULPHATE = " + val5[3]);
                        ps.setString(45, val5[4]);
                        System.out.println("PHOSPHATE = " + val5[4]);
                        ps.setString(46, val5[5]);
                        System.out.println("TIDYS = " + val5[5]);
                        ps.setString(47, val5[6]);
                        System.out.println("SILICA = " + val5[6]);
                        ps.setString(48, val5[7]);
                        System.out.println("TOTAL_KJELDHAL_NITROGEN = " +
                                           val5[7]);
                        ps.setString(49, val5[8]);
                        System.out.println("DO = " + val5[8]);
                        ps.setString(50, val5[9]);
                        System.out.println("BOD = " + val5[9]);

                        ps.setString(51, val6[0]);
                        System.out.println("COD = " + val6[0]);
                        ps.setString(52, val6[1]);
                        System.out.println("OIL_GREASE = " + val6[1]);
                        ps.setString(53, val6[2]);
                        System.out.println("RESIDUAL_CHLORINE = " + val6[2]);
                        ps.setString(54, val6[3]);
                        System.out.println("CYANIDE = " + val6[3]);
                        ps.setString(55, val6[4]);
                        System.out.println("ARSENIC = " + val6[4]);
                        ps.setString(56, val6[5]);
                        System.out.println("CADMIUM = " + val6[5]);
                        ps.setString(57, val6[6]);
                        System.out.println("COPPER = " + val6[6]);
                        ps.setString(58, val6[7]);
                        System.out.println("LEAD = " + val6[7]);
                        ps.setString(59, val6[8]);
                        System.out.println("CHROMIUM = " + val6[8]);
                        ps.setString(60, val6[9]);
                        System.out.println("ZINC = " + val6[9]);

                        ps.setString(61, val7[0]);
                        System.out.println("ALUMINIUM = " + val7[0]);
                        ps.setString(62, val7[1]);
                        System.out.println("SPC = " + val7[1]);
                        ps.setString(63, val7[2]);
                        System.out.println("TC = " + val7[2]);
                        ps.setString(64, val7[3]);
                        System.out.println("FC = " + val7[3]);
                        ps.setString(65, val7[4]);
                        System.out.println("FSC = " + val7[4]);
                        ps.setString(66, val7[5]);
                        System.out.println("CNC=" + val7[5]);
                        ps.setString(67, val7[6]);
                        System.out.println("PNP = " + val7[6]);
                        ps.setString(68, val7[7]);
                        System.out.println("REASON=" + val7[7]);
                        ps.executeUpdate();
                        con.commit();
                        flag = "Success";
                        System.out.println("Successfully Inserted into the Table");
                        xml = xml + "<flag>Success</flag>";
                    } catch (Exception e) {
                        System.out.println("Exception in connection:" + e);
                        xml = xml + "<flag>Failure</flag>";
                        flag = "Failure";
                    }
                    /*  if(flag.equalsIgnoreCase("Success"))
                    {
                        try
                        {
                            ps1=con.prepareStatement("update PMS_SAMPLE set STATUS_FLAG=? where LAB_CODE=? and CUSTOMER_REF_NO=?");
                            ps1.setString(1,"C");
                            int intval1 = Integer.parseInt(val1[0]);
                            ps1.setInt(2,intval1);
                            ps1.setString(3,val1[4]);
                            ps1.executeUpdate();
                            xml=xml+"<flag1>Success</flag1>";
                            con.commit();
                        }
                        catch(Exception e)
                        {
                            System.out.println("Err in flag updation");
                            xml=xml+"<flag1>Failure</flag1>";
                            con.rollback();
                        }
                    }*/
                } catch (Exception e) {
                    System.out.println("Err in insertion:" + e.getMessage());
                } finally {
                    try {
                        ps.close();
                        rs.close();
                    } catch (Exception e) {

                    }
                }

            }
        }
        xml = xml + "</response>";
        System.out.println("xml is:" + xml);
        out.println(xml);
        out.close();
    }
}
