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

public class WQS_PurchaseOrder_EditServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        String CONTENT_TYPE = "text/xml";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        response.setHeader("Cache-Control", "no-cache");
        System.out.println("welcome to servlet");
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null, rs2 = null;
        PreparedStatement ps = null, ps1 = null;
        String xml = null;
        String updatedby = null;
        Timestamp ts = null;
        Calendar c1;
        int c = 0;
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
        String supid = request.getParameter("supid");
        System.out.println("Supplier id=  " + supid);
        xml = "<response>";
        if (cmd.equalsIgnoreCase("callSup")) {
            xml = xml + "<command>callSup</command>";
            try {
                int lab = Integer.parseInt(request.getParameter("lab"));
                ps =
  con.prepareStatement("select SUPPLIER_NAME from WQS_MST_INV_SUPPLIER where LAB_CODE=? and SUPPLIER_CODE=?");
                ps.setInt(1, lab);
                ps.setString(2, supid);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml =
 xml + "<supname>" + rs.getString("SUPPLIER_NAME") + "</supname>";
                    xml = xml + "<flag>Success</flag>";
                } else {
                    xml = xml + "<flag>Failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("Err in callSup:" + e.getMessage());
            }
        } else {
            int lab = Integer.parseInt(request.getParameter("lab"));
            String pno = request.getParameter("pno");
            System.out.println(cmd + "   " + lab + "   " + pno);
            String catcode = request.getParameter("catcode");
            String catdesc = request.getParameter("catdesc");
            System.out.println(catcode + " " + catdesc);

            if (cmd.equalsIgnoreCase("checkAvail")) {
                xml = xml + "<command>checkAvail</command>";
                try {
                    ps =
  con.prepareStatement("select * from(select LAB_CODE,PURCHASE_ORDER_DATE,SUPPLIER_CODE from WQS_ITEM_PUR_ORDER_MASTER where LAB_CODE=? and PURCHASE_ORDER_NO=?)a inner join(select LAB_CODE,SUPPLIER_CODE,SUPPLIER_NAME from WQS_MST_INV_SUPPLIER)b on a.LAB_CODE=b.LAB_CODE and a.SUPPLIER_CODE=b.SUPPLIER_CODE");
                    ps.setInt(1, lab);
                    ps.setString(2, pno);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        Date fdate = rs.getDate("PURCHASE_ORDER_DATE");
                        Format formatter;
                        formatter = new SimpleDateFormat("dd/MM/yyyy");
                        String f = formatter.format(fdate);
                        xml = xml + "<odate>" + f + "</odate>";
                        xml =
 xml + "<scode>" + rs.getString("SUPPLIER_CODE") + "</scode>";
                        xml =
 xml + "<sname>" + rs.getString("SUPPLIER_NAME") + "</sname>";
                        xml = xml + "<fg>Success</fg>";
                    } else {
                        xml = xml + "<fg>Failure</fg>";
                    }
                    ps.close();
                } catch (Exception e) {
                    System.out.println("Err in checkAvail:" + e.getMessage());
                }
                try {
                    ps =
  con.prepareStatement("select * from(select distinct MAJOR_CATEGORY_CODE from WQS_ITEM_PUR_ORDER_DETAILS where LAB_CODE=? and PURCHASE_ORDER_NO=?)a inner join(select MAJOR_CATEGORY_CODE,MAJOR_CATEGORY_DESC from WQS_MST_INV_CATEGORY)b on a.MAJOR_CATEGORY_CODE=b.MAJOR_CATEGORY_CODE");
                    ps.setInt(1, lab);
                    ps.setString(2, pno);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        xml = xml + "<count>";
                        xml =
 xml + "<catcode>" + rs.getString("MAJOR_CATEGORY_CODE") + "</catcode>";
                        xml =
 xml + "<catdesc>" + rs.getString("MAJOR_CATEGORY_DESC") + "</catdesc>";
                        xml = xml + "</count>";
                        c = c + 1;
                    }
                    if (c > 0)
                        xml = xml + "<flag>Success</flag>";
                    else
                        xml = xml + "<flag>Failure</flag>";
                    ps.close();
                } catch (Exception e) {
                    System.out.println("Err in check category:" +
                                       e.getMessage());
                }
            } else if (cmd.equalsIgnoreCase("changeCat")) {

                String idesc = null;
                int y = 0;
                xml = xml + "<command>changeCat</command>";
                try {
                    ps1 =
 con.prepareStatement("select ITEM_CODE from WQS_ITEM_PUR_ORDER_DETAILS where LAB_CODE=? and PURCHASE_ORDER_NO=? and MAJOR_CATEGORY_CODE=?");
                    ps1.setInt(1, lab);
                    ps1.setString(2, pno);
                    ps1.setString(3, catcode);
                    rs2 = ps1.executeQuery();
                    while (rs2.next()) {
                        String icode = rs2.getString("ITEM_CODE");
                        System.out.println(icode);

                        xml = xml + "<count>";
                        xml = xml + "<icode>" + icode + "</icode>";
                        if (catdesc.equalsIgnoreCase("chemical")) {
                            System.out.println("chemical");
                            ps =
  con.prepareStatement("select CHEMICAL_CODE,CHE_SPECIFICATION from WQS_MST_INV_CHEMICAL where CHEMICAL_CODE=?");
                            ps.setString(1, icode);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                xml =
 xml + "<idesc>" + rs.getString("CHE_SPECIFICATION") + "</idesc>";
                            }
                        } else if (catdesc.equalsIgnoreCase("glassware")) {
                            System.out.println("chemical");
                            ps1 =
 con.prepareStatement("select GLASSWARE_CODE,GLASSWARE_SPEC from WQS_MST_INV_GLASSWARE where GLASSWARE_CODE=?");
                            ps1.setString(1, icode);
                            rs = ps1.executeQuery();
                            if (rs.next()) {
                                xml =
 xml + "<idesc>" + rs.getString("GLASSWARE_SPEC") + "</idesc>";
                            }
                        } else if (catdesc.equalsIgnoreCase("instrument")) {
                            System.out.println("instrument");
                            ps1 =
 con.prepareStatement("select INST_CATEGORY,INST_CATEGORY_SPEC from WQS_MST_INV_INSTRUMENT where INST_CATEGORY=?");
                            ps1.setString(1, icode);
                            rs = ps1.executeQuery();
                            if (rs.next()) {
                                xml =
 xml + "<idesc>" + rs.getString("INST_CATEGORY_SPEC") + "</idesc>";
                            }
                        } else {
                            System.out.println(catdesc);
                            ps1 =
 con.prepareStatement("select MISCELLANEOUS_CODE,MISCELLANEOUS_SPEC from WQS_MST_INV_MISCELLANEOUS where MISCELLANEOUS_CODE=?");
                            ps1.setString(1, icode);
                            rs = ps1.executeQuery();
                            if (rs.next()) {
                                xml =
 xml + "<idesc>" + rs.getString("MISCELLANEOUS_SPEC") + "</idesc>";
                            }
                        }
                        xml = xml + "</count>";
                    }
                    xml = xml + "<flag>Success</flag>";
                } catch (Exception e) {
                    System.out.println("Err in changeCat:" + e.getMessage());
                    xml = xml + "<flag>Failure</flag>";
                }
            } else if (cmd.equalsIgnoreCase("changeItem")) {
                String icode = request.getParameter("icode");
                String idesc = request.getParameter("idesc");
                System.out.println(catcode + " " + catdesc);
                xml = xml + "<command>changeItem</command>";
                try {
                    ps =
  con.prepareStatement("select QTY_ORDERED,CURRENT_STATUS,REMARKS,ORDERED_UOM_CODE,PROCESS_FLOW_STATUS_ID from WQS_ITEM_PUR_ORDER_DETAILS where LAB_CODE=? and PURCHASE_ORDER_NO=? and MAJOR_CATEGORY_CODE=? and ITEM_CODE=?");
                    ps.setInt(1, lab);
                    ps.setString(2, pno);
                    ps.setString(3, catcode);
                    ps.setString(4, icode);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        xml =
 xml + "<qtyordered>" + rs.getString("QTY_ORDERED") + "</qtyordered>";
                        xml =
 xml + "<uom>" + rs.getString("ORDERED_UOM_CODE") + "</uom>";
                        xml =
 xml + "<statusid>" + rs.getString("PROCESS_FLOW_STATUS_ID") + "</statusid>";
                        xml =
 xml + "<cstatus>" + rs.getString("CURRENT_STATUS") + "</cstatus>";
                        xml =
 xml + "<remarks>" + rs.getString("REMARKS") + "</remarks>";
                        xml = xml + "<flag>Success</flag>";
                    }

                } catch (Exception e) {
                    System.out.println("Err in changeItem:" + e.getMessage());
                }
            }
        }
        xml = xml + "</response>";
        out.println(xml);
        System.out.println(xml);
        out.close();

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        try {
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        System.out.println("Inside submit");
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement ps = null, ps1 = null;
        Calendar c1;
        String updatedby = null;
        Timestamp ts = null;
        int avail = 0;
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
        session = request.getSession(false);
        updatedby = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        ts = new Timestamp(l);
        System.out.println(updatedby);
        System.out.println(ts);

        String[] lb = request.getParameter("lab").split("--");
        int lab = Integer.parseInt(lb[0]);
        String pno = request.getParameter("orderno");
        System.out.println(lab + "   " + pno);
        String supid = request.getParameter("sid");
        System.out.println("Supplier id=  " + supid);
        String category[] = request.getParameter("cat").split("--");
        ;
        String catcode = category[0];
        System.out.println(catcode);

        String[] od = request.getParameter("orderdate").split("/");
        c1 =
  new GregorianCalendar(Integer.parseInt(od[2]), Integer.parseInt(od[1]) - 1,
                        Integer.parseInt(od[0]));
        java.util.Date d = c1.getTime();
        Date rdate = new Date(d.getTime());
        System.out.println("Ordered Date:" + rdate);

        String item[] = request.getParameter("item").split("--");
        String icode = item[0];
        int oqty = Integer.parseInt(request.getParameter("oqty"));
        String uom = request.getParameter("uom");
        String rem = request.getParameter("rem");
        try {

            ps =
  con.prepareStatement("update  WQS_ITEM_PUR_ORDER_MASTER set PURCHASE_ORDER_DATE=?,SUPPLIER_CODE=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? where LAB_CODE=? and PURCHASE_ORDER_NO=?");
            ps.setDate(1, rdate);
            ps.setString(2, supid);
            ps.setTimestamp(3, ts);
            ps.setString(4, updatedby);
            ps.setInt(5, lab);
            ps.setString(6, pno);
            ps.executeUpdate();
            ps.close();
            ps1 =
 con.prepareStatement("update  WQS_ITEM_PUR_ORDER_DETAILS set QTY_ORDERED=?,REMARKS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,ORDERED_UOM_CODE=?,RECEIVED_UOM_CODE=?,PROCESS_FLOW_STATUS_ID=? where LAB_CODE=? and PURCHASE_ORDER_NO=? and MAJOR_CATEGORY_CODE=? and ITEM_CODE=?");
            ps1.setInt(1, oqty);
            ps1.setString(2, rem);
            ps1.setString(3, updatedby);
            ps1.setTimestamp(4, ts);
            ps1.setString(5, uom);
            ps1.setString(6, uom);
            ps1.setString(7, "MD");
            ps1.setInt(8, lab);
            ps1.setString(9, pno);
            ps1.setString(10, catcode);
            ps1.setString(11, icode);
            ps1.executeUpdate();
            ps1.close();
            //con.commit();
            String msg = "Record is Updated Successfully";
            sendMSG(response, msg, "ok");
        } catch (Exception e) {
            System.out.println("Err in updation:" + e.getMessage());
            // con.rollback();
            String msg = "<br><br>" + e.getMessage();
            sendMSG(response, msg, "Cancel");
        }
    }

    private void sendMSG(HttpServletResponse response, String msg,
                         String bType) {
        try {
            String url =
                "org/Library/jsps/MessengerOkBack.jsp?message=" + msg +
                "&button=" + bType;
            response.sendRedirect(url);
        } catch (IOException e) {
        }
    }
}
