package Servlets.WQS.WQS1.Inventory.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class WQS_SupplierMasterServ extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=UTF-8";

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
        Statement stmt = null;
        ResultSet rs = null, rs2 = null;
        PreparedStatement ps = null;
        String xml = null, updatedby = null;
        Timestamp ts = null;
        int code = 0, no = 0, lcode = 0;
        String Mail = null, m2 = null;
        System.out.println("welcome");

        try {
            ResourceBundle rs1 =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs1.getString("Config.DSN");
            String strhostname = rs1.getString("Config.HOST_NAME");
            String strportno = rs1.getString("Config.PORT_NUMBER");
            String strsid = rs1.getString("Config.SID");
            String strdbusername = rs1.getString("Config.USER_NAME");
            String strdbpassword = rs1.getString("Config.PASSWORD");
            ConnectionString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
            try {
                stmt = con.createStatement();
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

        String strCommand = request.getParameter("command");
        System.out.println(strCommand);

        if (strCommand.equalsIgnoreCase("LoadSC")) {
            System.out.println("Inside:" + strCommand);
            xml = "<response><command>LoadSC</command>";
            try {
                stmt = con.createStatement();
                rs =
  stmt.executeQuery("SELECT SUPPLIER_CODE FROM WQS_MST_INV_SUPPLIER GROUP BY SUPPLIER_CODE HAVING SUPPLIER_CODE=(select max(SUPPLIER_CODE) from WQS_MST_INV_SUPPLIER)");
                System.out.print("rs");
                if (rs.next()) {

                    System.out.println("SUPPLIER_CODE");
                    no = Integer.parseInt(rs.getString("SUPPLIER_CODE"));
                    System.out.println(no);
                    System.out.println("ok");
                    no = no + 1;
                    xml = xml + "<flag>Success</flag>";
                    xml = xml + "<code>" + no + "</code>";
                } else {
                    no = no + 1;
                    System.out.println(no);
                    xml = xml + "<flag>Success</flag>";
                    xml = xml + "<code>" + no + "</code>";
                }
                rs.close();
            } catch (Exception e) {
                System.out.println("ERR" + e);
                xml = xml + "<flag>failure</flag>";
            }
        } else if (strCommand.equalsIgnoreCase("Get")) {
            System.out.println("out try");

            xml = "<response><command>Get</command>";
            try {
                System.out.println("in try");
                lcode = Integer.parseInt(request.getParameter("lab"));
                stmt = con.createStatement();
                rs =
  stmt.executeQuery("select * from(select * from WQS_MST_INV_SUPPLIER where LAB_CODE=" +
                    lcode +
                    " order by SUPPLIER_CODE)a left outer join(select * from COM_MST_DISTRICTS)b on a.SOURCE_LOC_DISTRICT_CODE=b.DISTRICT_CODE");
                try {
                    while (rs.next()) {
                        xml = xml + "<count>";
                        xml =
 xml + "<labcode>" + Integer.parseInt(rs.getString("LAB_CODE")) + "</labcode>";
                        xml =
 xml + "<code>" + Integer.parseInt(rs.getString("SUPPLIER_CODE")) + "</code>";
                        xml =
 xml + "<sname>" + rs.getString("SUPPLIER_NAME") + "</sname>";
                        xml =
 xml + "<add1>" + rs.getString("ADDRESS1") + "</add1>";
                        xml =
 xml + "<add2>" + rs.getString("ADDRESS2") + "</add2>";
                        xml =
 xml + "<add3>" + rs.getString("ADDRESS3") + "</add3>";
                        xml =
 xml + "<pincode>" + rs.getString("PINCODE") + "</pincode>";
                        xml =
 xml + "<distname>" + rs.getString("DISTRICT_NAME") + "</distname>";
                        xml =
 xml + "<phone1>" + rs.getString("CONTACT_PHONE1") + "</phone1>";
                        xml =
 xml + "<phone2>" + rs.getString("CONTACT_PHONE2") + "</phone2>";
                        xml = xml + "<fax>" + rs.getString("FAX") + "</fax>";
                        xml =
 xml + "<mail>" + rs.getString("EMAIL") + "</mail>";
                        System.out.println("Email add is:" +
                                           rs.getString("EMAIL"));
                        xml =
 xml + "<cstatus>" + rs.getString("CURRENT_STATUS") + "</cstatus>";
                        xml =
 xml + "<reference>" + rs.getString("FILE_REFERENCE") + "</reference>";
                        xml =
 xml + "<remarks>" + rs.getString("REMARKS") + "</remarks>";
                        xml = xml + "</count>";
                    }
                    xml = xml + "<flag>Success</flag>";
                } catch (Exception aee) {
                    System.out.println("Exception in the getting values OF GET in first try: " +
                                       aee);
                    xml = xml + "<flag>failure</flag>";
                }
                try {
                    stmt = con.createStatement();
                    rs =
  stmt.executeQuery("select DISTRICT_NAME from COM_MST_DISTRICTS");
                    while (rs.next()) {
                        xml = xml + "<cou>";
                        xml =
 xml + "<desc>" + rs.getString("DISTRICT_NAME") + "</desc>";
                        xml = xml + "</cou>";
                    }
                    xml = xml + "<flag1>Success</flag1>";
                } catch (Exception aee) {
                    System.out.println("Exception in the getting values OF GET in second try: " +
                                       aee);
                    xml = xml + "<flag1>failure</flag1>";
                }
                rs.close();
                response.setHeader("cache-control", "no-cache");
            } catch (Exception e1) {
                System.out.println("Exception is in Get" + e1);
                xml = xml + "<flag>failure</flag>";
            }

        } else if (strCommand.equalsIgnoreCase("Update")) {
            lcode = Integer.parseInt(request.getParameter("lab"));
            int SupplierCode =
                Integer.parseInt(request.getParameter("SupplierId"));
            String SupplierName = request.getParameter("SupplierName");
            String Address1 = request.getParameter("Address1");
            String Address2 = request.getParameter("Address2");
            String Address3 = request.getParameter("Address3");
            String Pin = request.getParameter("Pin");
            String dname = request.getParameter("dname");
            String Phone1 = request.getParameter("Phone1");
            String Phone2 = request.getParameter("Phone2");
            String Fax = request.getParameter("Fax");
            Mail = request.getParameter("Mail");
            System.out.println("update mail:" + Mail);
            System.out.println("m2 is:" + m2);
            String ref = request.getParameter("ref");
            String status = request.getParameter("status");
            String remarks = request.getParameter("remarks");
            System.out.println("Mail is :" + Mail);

            xml = "<response><command>Update</command>";
            try {
                System.out.println(dname);
                session = request.getSession(false);
                updatedby = (String)session.getAttribute("UserId");
                long l = System.currentTimeMillis();
                ts = new Timestamp(l);
                ps =
  con.prepareStatement("select DISTRICT_CODE from COM_MST_DISTRICTS where DISTRICT_NAME=?");
                ps.setString(1, dname);
                rs = ps.executeQuery();
                if (rs.next()) {
                    code = Integer.parseInt(rs.getString("DISTRICT_CODE"));
                    System.out.println(code);
                }
            } catch (Exception e1) {
                System.out.println("Exception is in Get" + e1);
            }
            try {
                PreparedStatement pstmt =
                    con.prepareStatement("update WQS_MST_INV_SUPPLIER set SUPPLIER_NAME=?,ADDRESS1=?,ADDRESS2=?,ADDRESS3=?,PINCODE=?,SOURCE_LOC_DISTRICT_CODE=?,CONTACT_PHONE1=?,CONTACT_PHONE2=?,FAX=?,EMAIL=?,CURRENT_STATUS=?,FILE_REFERENCE=?,REMARKS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?  where LAB_CODE=? and SUPPLIER_CODE=?");

                pstmt.setString(1, SupplierName);
                pstmt.setString(2, Address1);
                pstmt.setString(3, Address2);
                if (Address3.equalsIgnoreCase("-")) {
                    String add = "";
                    pstmt.setString(4, add);
                } else
                    pstmt.setString(4, Address3);
                pstmt.setString(5, Pin);
                pstmt.setInt(6, code);
                if (Phone1.equalsIgnoreCase("-")) {
                    String ph1 = "";
                    pstmt.setString(7, ph1);
                } else
                    pstmt.setString(7, Phone1);
                if (Phone2.equalsIgnoreCase("-")) {
                    String ph2 = "";
                    pstmt.setString(8, ph2);
                } else
                    pstmt.setString(8, Phone2);
                if (Fax.equalsIgnoreCase("-")) {
                    String fx = "";
                    pstmt.setString(9, fx);
                } else
                    pstmt.setString(9, Fax);
                if (Mail.equalsIgnoreCase("-")) {
                    String eid = "";
                    pstmt.setString(10, eid);
                } else
                    pstmt.setString(10, Mail);
                pstmt.setString(11, status);
                if (ref.equalsIgnoreCase("-")) {
                    String rn = "";
                    pstmt.setString(12, rn);
                } else
                    pstmt.setString(12, ref);
                if (remarks.equalsIgnoreCase("-")) {
                    String rem = "";
                    pstmt.setString(13, rem);
                } else
                    pstmt.setString(13, remarks);
                pstmt.setString(14, updatedby);
                pstmt.setTimestamp(15, ts);
                pstmt.setInt(16, lcode);
                pstmt.setInt(17, SupplierCode);
                pstmt.executeUpdate();
                xml = xml + "<LabCode>" + lcode + "</LabCode>";
                xml =
 xml + "<SupplierCode>" + SupplierCode + "</SupplierCode>";
                xml =
 xml + "<SupplierName>" + SupplierName + "</SupplierName>";
                xml = xml + "<Address1>" + Address1 + "</Address1>";
                xml = xml + "<Address2>" + Address2 + "</Address2>";
                xml = xml + "<Address3>" + Address3 + "</Address3>";
                xml = xml + "<DistName>" + dname + "</DistName>";

                xml = xml + "<Pin>" + Pin + "</Pin>";
                xml = xml + "<Phone1>" + Phone1 + "</Phone1>";
                xml = xml + "<Phone2>" + Phone2 + "</Phone2>";
                xml = xml + "<Fax>" + Fax + "</Fax>";
                if (Mail.equalsIgnoreCase("")) {
                    System.out.println("inside if");
                    Mail = "null";
                    xml = xml + "<Mail>" + Mail + "</Mail>";
                } else {
                    System.out.println("inside else");
                    xml = xml + "<Mail>" + Mail + "</Mail>";
                }
                xml = xml + "<Ref>" + ref + "</Ref>";
                xml = xml + "<Status>" + status + "</Status>";
                xml = xml + "<Remarks>" + remarks + "</Remarks>";

                xml = xml + "<flag>success</flag>";
                pstmt.close();
            } catch (SQLException e) {
                System.out.println("Exception is in Get" + e);
                xml = xml + "<flag>failure</flag>";
            }

        } else if (strCommand.equalsIgnoreCase("Add")) {
            lcode = Integer.parseInt(request.getParameter("lab"));
            String SupplierName = request.getParameter("SupplierName");
            String Address1 = request.getParameter("Address1");
            String Address2 = request.getParameter("Address2");
            String Address3 = request.getParameter("Address3");
            String Pin = request.getParameter("Pin");
            String dname = request.getParameter("dname");
            String Phone1 = request.getParameter("Phone1");
            String Phone2 = request.getParameter("Phone2");
            String Fax = request.getParameter("Fax");
            Mail = request.getParameter("Mail");
            String ref = request.getParameter("ref");
            String status = request.getParameter("status");
            String remarks = request.getParameter("remarks");
            System.out.println("Mail is :" + Mail);
            int scode = 0;

            xml = "<response><command>Add</command>";
            try {
                System.out.println("Add");
                session = request.getSession(false);
                updatedby = (String)session.getAttribute("UserId");
                long l = System.currentTimeMillis();
                ts = new Timestamp(l);
                System.out.println(updatedby);
                System.out.println(ts);

                ps =
  con.prepareStatement("select DISTRICT_CODE from COM_MST_DISTRICTS where DISTRICT_NAME=?");
                ps.setString(1, dname);
                rs = ps.executeQuery();
                if (rs.next()) {
                    code = Integer.parseInt(rs.getString("DISTRICT_CODE"));
                    System.out.println(code);
                }
            } catch (Exception e1) {
                System.out.println("Exception is in Get" + e1);
            }
            try {
                stmt = con.createStatement();
                rs2 =
 stmt.executeQuery("SELECT SUPPLIER_CODE FROM WQS_MST_INV_SUPPLIER GROUP BY SUPPLIER_CODE HAVING SUPPLIER_CODE =(select max(SUPPLIER_CODE) from WQS_MST_INV_SUPPLIER where LAB_CODE=" +
                   lcode + ")");
                while (rs2.next()) {
                    scode = rs2.getInt(1);

                }
                scode = scode + 1;
                xml = xml + "<code>" + scode + "</code>";
                System.out.println(scode);
            } catch (Exception e) {
                System.out.println("Err in AutoIncrement:" + e.getMessage());
            }
            try {
                PreparedStatement pstmt =
                    con.prepareStatement("insert into WQS_MST_INV_SUPPLIER(LAB_CODE,SUPPLIER_CODE,SUPPLIER_NAME,ADDRESS1,ADDRESS2,ADDRESS3,PINCODE,SOURCE_LOC_DISTRICT_CODE,CONTACT_PHONE1,CONTACT_PHONE2,FAX,EMAIL,CURRENT_STATUS,FILE_REFERENCE,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                System.out.println("Query--------->insert into WQS_MST_INV_SUPPLIER(LAB_CODE,SUPPLIER_CODE,SUPPLIER_NAME,ADDRESS1,ADDRESS2,ADDRESS3,PINCODE,SOURCE_LOC_DISTRICT_CODE,CONTACT_PHONE1,CONTACT_PHONE2,FAX,EMAIL,CURRENT_STATUS,FILE_REFERENCE,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE)values(" +
                                   lcode + "," + scode + ",'" + SupplierName +
                                   "','" + Address1 + "','" + Address2 +
                                   "','" + Address3 + "','" + Pin + "'," +
                                   code + ",'" + Phone1 + "','" + Phone2 +
                                   "','" + Fax + "','" + Mail + "','" +
                                   status + "','" + ref + "','" + remarks +
                                   "','" + updatedby + "','" + ts + "')");
                System.out.println("add");
                pstmt.setInt(1, lcode);
                pstmt.setInt(2, scode);
                System.out.println("scode---->" + scode);
                pstmt.setString(3, SupplierName);
                System.out.println("SupplierName---->" + SupplierName);
                pstmt.setString(4, Address1);
                System.out.println("Address1---->" + Address1);
                pstmt.setString(5, Address2);
                System.out.println("Address2---->" + Address2);
                if (Address3.equalsIgnoreCase("-")) {
                    String add = "";
                    pstmt.setString(6, add);
                    System.out.println("Address3---->" + add);
                } else {
                    pstmt.setString(6, Address3);
                    System.out.println("Address3---->" + Address3);
                }
                pstmt.setString(7, Pin);
                System.out.println("Pin---->" + Pin);
                pstmt.setInt(8, code);
                System.out.println("code---->" + code);
                if (Phone1.equalsIgnoreCase("-")) {
                    String ph1 = "";
                    pstmt.setString(9, ph1);
                    System.out.println("Phone1---->" + ph1);
                } else {
                    pstmt.setString(9, Phone1);
                    System.out.println("Phone1---->" + Phone1);
                }
                if (Phone2.equalsIgnoreCase("-")) {
                    String ph2 = "";
                    pstmt.setString(10, ph2);
                    System.out.println("Phone2---->" + ph2);
                } else {
                    pstmt.setString(10, Phone2);
                    System.out.println("Phone2---->" + Phone2);
                }
                if (Fax.equalsIgnoreCase("-")) {
                    String fx = "";
                    pstmt.setString(11, fx);
                } else {
                    pstmt.setString(11, Fax);
                    System.out.println("Fax---->" + Fax);
                }
                if (Mail.equalsIgnoreCase("-")) {
                    String eid = "";
                    pstmt.setString(12, eid);
                    System.out.println("Mail---->" + eid);
                } else {
                    pstmt.setString(12, Mail);
                    System.out.println("Mail---->" + Mail);
                }
                pstmt.setString(13, status);
                System.out.println("status---->" + status);
                if (ref.equalsIgnoreCase("-")) {
                    String rn = "";
                    pstmt.setString(14, rn);
                } else {
                    pstmt.setString(14, ref);
                    System.out.println("ref---->" + ref);
                }
                if (remarks.equalsIgnoreCase("-")) {
                    String rem = "";
                    pstmt.setString(15, rem);
                    System.out.println("remarks---->" + rem);
                } else {
                    pstmt.setString(15, remarks);
                    System.out.println("remarks---->" + remarks);
                }
                pstmt.setString(16, updatedby);
                System.out.println("updatedby---->" + updatedby);
                pstmt.setTimestamp(17, ts);
                System.out.println("ts---->" + ts);
                pstmt.executeUpdate();

                xml = xml + "<LabCode>" + lcode + "</LabCode>";
                xml = xml + "<SupplierCode>" + scode + "</SupplierCode>";
                xml =
 xml + "<SupplierName>" + SupplierName + "</SupplierName>";
                xml = xml + "<Address1>" + Address1 + "</Address1>";
                xml = xml + "<Address2>" + Address2 + "</Address2>";
                xml = xml + "<Address3>" + Address3 + "</Address3>";
                xml = xml + "<DistName>" + dname + "</DistName>";

                xml = xml + "<Pin>" + Pin + "</Pin>";
                xml = xml + "<Phone1>" + Phone1 + "</Phone1>";
                xml = xml + "<Phone2>" + Phone2 + "</Phone2>";
                xml = xml + "<Fax>" + Fax + "</Fax>";
                xml = xml + "<Mail>" + Mail + "</Mail>";
                xml = xml + "<Ref>" + ref + "</Ref>";
                xml = xml + "<Status>" + status + "</Status>";
                xml = xml + "<Remarks>" + remarks + "</Remarks>";
                xml = xml + "<flag>success</flag>";
                pstmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                xml = xml + "<flag>failure</flag>";

            }
        } else if (strCommand.equalsIgnoreCase("Delete")) {
            lcode = Integer.parseInt(request.getParameter("lab"));
            int SupplierCode =
                Integer.parseInt(request.getParameter("SupplierId"));
            xml = "<response><command>Delete</command>";
            try {
                ps =
  con.prepareStatement("select * from WQS_MST_INV_SUP_ITEM where LAB_CODE=? and SUPPLIER_CODE=?");
                ps.setInt(1, lcode);
                ps.setInt(2, SupplierCode);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml = xml + "<flag>FoundData</flag>";
                } else {
                    PreparedStatement pstmt =
                        con.prepareStatement("delete from WQS_MST_INV_SUPPLIER where LAB_CODE=? and SUPPLIER_CODE=?");
                    System.out.println(pstmt);
                    pstmt.setInt(1, lcode);
                    pstmt.setInt(2, SupplierCode);

                    pstmt.executeUpdate();
                    xml = xml + "<flag>success</flag>";
                    pstmt.close();
                }
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }
        } else {
            int SupplierCode =
                Integer.parseInt(request.getParameter("SupplierId"));
            xml = "<response><command>CheckDuplicate</command>";
            try {
                PreparedStatement pstmt =
                    con.prepareStatement("select supplier_code from wqs_mst_inv_supplier where supplier_code=?");
                System.out.println(pstmt);
                pstmt.setInt(1, SupplierCode);

                rs = pstmt.executeQuery();
                if (rs.next()) {
                    System.out.println("Duplication");
                    xml = xml + "<flag>failure</flag>";
                } else {
                    System.out.println("No Redundancy");
                    xml = xml + "<flag>success</flag>";
                }
                pstmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
            }
        }
        xml = xml + "</response>";
        out.println(xml);
        System.out.println(xml);
        out.close();
    }

}
