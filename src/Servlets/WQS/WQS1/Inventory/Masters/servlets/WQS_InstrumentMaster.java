package Servlets.WQS.WQS1.Inventory.Masters.servlets;

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

public class WQS_InstrumentMaster extends HttpServlet {
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
        ResultSet rs = null;
        PreparedStatement ps = null;
        String xml = null;
        Calendar c1;
        String updatedby = null;
        Timestamp ts = null;
        System.out.println("welcome");
        float cost = 0;

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

        session = request.getSession(false);
        updatedby = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        ts = new Timestamp(l);
        System.out.println(updatedby);
        System.out.println(ts);

        String cmd = request.getParameter("command");
        System.out.println(cmd);
        xml = "<response>";
        if (cmd.equalsIgnoreCase("Add")) {
            int labcode = Integer.parseInt(request.getParameter("LabCode"));
            String catcode = request.getParameter("CatCode");
            //int icode=Integer.parseInt(request.getParameter("Icode"));
            String brand = request.getParameter("Brand");
            System.out.println(labcode + " " + catcode + " " + brand);
            String type = request.getParameter("Type");
            String sno = request.getParameter("Sno");
            String make = request.getParameter("Make");
            System.out.println(type + " " + sno + " " + make);
            String model = request.getParameter("Model");
            cost = Float.parseFloat(request.getParameter("Cost"));
            System.out.println(model + " " + cost);
            String[] od = request.getParameter("Adate").split("/");
            c1 =
  new GregorianCalendar(Integer.parseInt(od[2]), Integer.parseInt(od[1]) - 1,
                        Integer.parseInt(od[0]));
            java.util.Date d = c1.getTime();
            Date adate = new Date(d.getTime());

            String rc = request.getParameter("Rcode");
            String supplied = request.getParameter("Supplied");
            String cstatus = request.getParameter("Cstatus");
            String remarks = request.getParameter("Remarks");
            String rno = request.getParameter("Rno");
            int no = 0;
            xml = xml + "<command>Add</command>";
            try {
                stmt = con.createStatement();
                rs =
  stmt.executeQuery("SELECT INSTRUMENT_CODE FROM WQS_INSTRUMENT_MASTER GROUP BY INSTRUMENT_CODE HAVING INSTRUMENT_CODE =(select max(INSTRUMENT_CODE) from WQS_INSTRUMENT_MASTER)");
                while (rs.next()) {
                    no = rs.getInt(1);
                }
                no = no + 1;
                xml = xml + "<code>" + no + "</code>";
                System.out.println(no);
            } catch (Exception e) {
                System.out.println("Err in AutoIncrement:" + e.getMessage());
            }
            try {
                System.out.println("brand==================>" + brand);
                PreparedStatement pstmt =
                    con.prepareStatement("insert into WQS_INSTRUMENT_MASTER(LAB_CODE,INST_CATEGORY,INSTRUMENT_CODE,BRAND,TYPE,SL_NO,MAKE,MODEL,COST,ACQUIRED_DATE,CURRENT_STATUS,REMARKS,FILE_REF_NO,UPDATED_BY_USER_ID,UPDATED_DATE,REFERENCE_CODE,SUPPLIED_BY)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                System.out.println("add");
                pstmt.setInt(1, labcode);
                System.out.println("labcode:" + labcode);
                pstmt.setString(2, catcode);
                System.out.println("catcode:" + catcode);
                pstmt.setInt(3, no);
                System.out.println("no:" + no);
                pstmt.setString(4, brand);
                System.out.println("brand:" + brand);
                pstmt.setString(5, type);
                System.out.println("type:" + type);
                pstmt.setString(6, sno);
                System.out.println("sno:" + sno);
                pstmt.setString(7, make);
                System.out.println("make:" + make);
                pstmt.setString(8, model);
                System.out.println("model:" + model);
                pstmt.setFloat(9, cost);
                System.out.println("cost:" + cost);
                pstmt.setDate(10, adate);
                System.out.println("adate:" + adate);
                pstmt.setString(11, cstatus);
                System.out.println("cstatus:" + cstatus);
                if (remarks.equalsIgnoreCase("-")) {
                    String rm = "";
                    pstmt.setString(12, rm);
                    System.out.println("remarks:" + rm);
                } else {
                    pstmt.setString(12, remarks);
                    System.out.println("remarks:" + remarks);
                }
                pstmt.setString(13, rno);
                System.out.println("rno:" + rno);
                pstmt.setString(14, updatedby);
                System.out.println("updatedby:" + updatedby);
                pstmt.setTimestamp(15, ts);
                System.out.println("ts:" + ts);
                if (rc.equalsIgnoreCase("-")) {
                    pstmt.setString(16, "");
                    System.out.println("rcode:" + rc);
                } else {
                    pstmt.setString(16, rc);
                    System.out.println("rcode:" + rc);
                }
                if (supplied.equalsIgnoreCase("-")) {
                    pstmt.setString(17, "");
                    System.out.println("supplied:" + "");
                } else {
                    pstmt.setString(17, supplied);
                    System.out.println("supplied:" + supplied);
                }
                pstmt.executeUpdate();
                xml = xml + "<flag>Success</flag>";

                xml = xml + "<LabCode>" + labcode + "</LabCode>";
                xml = xml + "<CatCode>" + catcode + "</CatCode>";
                xml = xml + "<Num>" + no + "</Num>";
                if (brand.equalsIgnoreCase(""))
                    xml = xml + "<Brand>-</Brand>";
                else
                    xml = xml + "<Brand>" + brand + "</Brand>";
                xml = xml + "<Type>" + type + "</Type>";
                xml = xml + "<Sno>" + sno + "</Sno>";

                xml = xml + "<Make>" + make + "</Make>";
                xml = xml + "<Model>" + model + "</Model>";
                xml = xml + "<Cost>" + cost + "</Cost>";
                Format formatter;
                formatter = new SimpleDateFormat("dd/MM/yyyy");
                String f = formatter.format(adate);
                xml = xml + "<Adate>" + f + "</Adate>";
                xml = xml + "<Rcode>" + rc + "</Rcode>";
                xml = xml + "<Supplied>" + supplied + "</Supplied>";
                xml = xml + "<Cstatus>" + cstatus + "</Cstatus>";
                xml = xml + "<Remarks>" + remarks + "</Remarks>";

            } catch (Exception e) {
                System.out.println("Err in addition:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("Get")) {
            int labcode = Integer.parseInt(request.getParameter("LabCode"));
            xml = xml + "<command>Get</command>";
            try {
                System.out.println("inside get command");
                PreparedStatement pstmt =
                    con.prepareStatement("select LAB_CODE,INST_CATEGORY,INSTRUMENT_CODE,BRAND,TYPE,SL_NO,MAKE,MODEL,COST,ACQUIRED_DATE,CURRENT_STATUS,REMARKS,FILE_REF_NO,REFERENCE_CODE,SUPPLIED_BY from WQS_INSTRUMENT_MASTER where LAB_CODE=? order by INSTRUMENT_CODE");
                pstmt.setInt(1, labcode);
                System.out.println(labcode);
                rs = pstmt.executeQuery();
                try {
                    while (rs.next()) {
                        xml = xml + "<count>";
                        xml =
 xml + "<LabCode>" + Integer.parseInt(rs.getString("LAB_CODE")) + "</LabCode>";
                        xml =
 xml + "<CatCode>" + rs.getString("INST_CATEGORY") + "</CatCode>";
                        xml =
 xml + "<Num>" + Integer.parseInt(rs.getString("INSTRUMENT_CODE")) + "</Num>";
                        xml =
 xml + "<Brand>" + rs.getString("BRAND") + "</Brand>";
                        xml =
 xml + "<Type>" + rs.getString("TYPE") + "</Type>";
                        xml = xml + "<Sno>" + rs.getString("SL_NO") + "</Sno>";

                        xml =
 xml + "<Make>" + rs.getString("MAKE") + "</Make>";
                        xml =
 xml + "<Model>" + rs.getString("MODEL") + "</Model>";
                        xml =
 xml + "<Cost>" + Float.parseFloat(rs.getString("COST")) + "</Cost>";

                        Date tdate = rs.getDate("ACQUIRED_DATE");

                        Format formatter;
                        formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String f = formatter.format(tdate);

                        xml = xml + "<Adate>" + f + "</Adate>";
                        xml =
 xml + "<Rcode>" + rs.getString("REFERENCE_CODE") + "</Rcode>";
                        xml =
 xml + "<Supplied>" + rs.getString("SUPPLIED_BY") + "</Supplied>";
                        xml =
 xml + "<Cstatus>" + rs.getString("CURRENT_STATUS") + "</Cstatus>";
                        xml =
 xml + "<Remarks>" + rs.getString("REMARKS") + "</Remarks>";
                        xml =
 xml + "<Rno>" + rs.getString("FILE_REF_NO") + "</Rno>";
                        xml = xml + "</count>";
                    }
                    xml = xml + "<flag>Success</flag>";
                } catch (Exception e) {
                    System.out.println("Err in getting values:" +
                                       e.getMessage());
                    xml = xml + "<flag>Failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("Err in getting values2:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("Delete")) {
            int labcode = Integer.parseInt(request.getParameter("LabCode"));
            String catcode = request.getParameter("CatCode");
            int icode = Integer.parseInt(request.getParameter("Icode"));
            xml = "<response><command>Delete</command>";
            try {
                PreparedStatement pstmt =
                    con.prepareStatement("delete from WQS_INSTRUMENT_MASTER where LAB_CODE=? and INST_CATEGORY=? and INSTRUMENT_CODE=?");
                System.out.println(pstmt);
                pstmt.setInt(1, labcode);
                pstmt.setString(2, catcode);
                pstmt.setInt(3, icode);

                pstmt.executeUpdate();
                xml = xml + "<flag>Success</flag>";
                pstmt.close();
            } catch (SQLException e) {
                System.out.println(e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }
        } else {
            System.out.println("inside update");
            int labcode = Integer.parseInt(request.getParameter("LabCode"));
            String catcode = request.getParameter("CatCode");
            int icode = Integer.parseInt(request.getParameter("Icode"));
            String brand = request.getParameter("Brand");
            System.out.println(labcode + " " + catcode + " " + brand);
            String type = request.getParameter("Type");
            String sno = request.getParameter("Sno");
            String make = request.getParameter("Make");
            System.out.println(type + " " + sno + " " + make);
            String model = request.getParameter("Model");
            cost = Float.parseFloat(request.getParameter("Cost"));
            System.out.println(model + " " + cost);
            String[] od = request.getParameter("Adate").split("/");
            c1 =
  new GregorianCalendar(Integer.parseInt(od[2]), Integer.parseInt(od[1]) - 1,
                        Integer.parseInt(od[0]));
            java.util.Date d = c1.getTime();
            Date adate = new Date(d.getTime());
            System.out.println(adate);

            String rc = request.getParameter("Rcode");
            String supplied = request.getParameter("Supplied");
            String cstatus = request.getParameter("Cstatus");
            String remarks = request.getParameter("Remarks");
            String rno = request.getParameter("Rno");
            System.out.println(cstatus + " " + remarks + " " + rno);
            xml = "<response><command>Update</command>";
            try {
                PreparedStatement pstmt =
                    con.prepareStatement("update WQS_INSTRUMENT_MASTER set BRAND=?,TYPE=?,SL_NO=?,MAKE=?,MODEL=?,COST=?,ACQUIRED_DATE=?,CURRENT_STATUS=?,REMARKS=?,FILE_REF_NO=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,REFERENCE_CODE=?,SUPPLIED_BY=?  where LAB_CODE=? and INST_CATEGORY=? and INSTRUMENT_CODE=?");

                pstmt.setString(1, brand);
                pstmt.setString(2, type);
                pstmt.setString(3, sno);
                pstmt.setString(4, make);
                pstmt.setString(5, model);
                pstmt.setFloat(6, cost);
                pstmt.setDate(7, adate);
                pstmt.setString(8, cstatus);
                if (remarks.equalsIgnoreCase("-")) {
                    String rm = "";
                    pstmt.setString(9, rm);
                    System.out.println("remarks:" + rm);
                } else {
                    pstmt.setString(9, remarks);
                    System.out.println("remarks:" + remarks);
                }
                pstmt.setString(10, rno);
                pstmt.setString(11, updatedby);
                pstmt.setTimestamp(12, ts);
                if (rc.equalsIgnoreCase("-")) {
                    pstmt.setString(13, "");
                } else {
                    pstmt.setString(13, rc);
                    System.out.println("rcode:" + rc);
                }
                if (supplied.equalsIgnoreCase("-")) {
                    String st = "";
                    pstmt.setString(14, st);
                    System.out.println("supplied:" + st);
                } else {
                    pstmt.setString(14, supplied);
                    System.out.println("supplied:" + supplied);
                }
                pstmt.setInt(15, labcode);
                pstmt.setString(16, catcode);
                pstmt.setInt(17, icode);
                pstmt.executeUpdate();
                xml = xml + "<flag>Success</flag>";

                xml = xml + "<LabCode>" + labcode + "</LabCode>";
                xml = xml + "<CatCode>" + catcode + "</CatCode>";
                xml = xml + "<Num>" + icode + "</Num>";
                xml = xml + "<Brand>" + brand + "</Brand>";
                xml = xml + "<Type>" + type + "</Type>";
                xml = xml + "<Sno>" + sno + "</Sno>";

                xml = xml + "<Make>" + make + "</Make>";
                xml = xml + "<Model>" + model + "</Model>";
                xml = xml + "<Cost>" + cost + "</Cost>";
                xml = xml + "<Adate>" + adate + "</Adate>";
                xml = xml + "<Rcode>" + rc + "</Rcode>";
                xml = xml + "<Supplied>" + supplied + "</Supplied>";
                xml = xml + "<Cstatus>" + cstatus + "</Cstatus>";
                xml = xml + "<Remarks>" + remarks + "</Remarks>";
                xml = xml + "<Rno>" + rno + "</Rno>";

                pstmt.close();
            } catch (SQLException e) {
                System.out.println("Exception is in Get:" + e);
                xml = xml + "<flag>failure</flag>";
            }

        }
        xml = xml + "</response>";
        System.out.println(xml);
        out.println(xml);
        out.close();
    }
}
