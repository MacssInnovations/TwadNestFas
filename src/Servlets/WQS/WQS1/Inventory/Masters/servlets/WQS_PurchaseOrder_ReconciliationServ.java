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
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class WQS_PurchaseOrder_ReconciliationServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        PrintWriter out = response.getWriter();
        String CONTENT_TYPE = "text/xml";
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        System.out.println("welcome to servlet");
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null, rs2;
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
  con.prepareStatement("select * from(select distinct MAJOR_CATEGORY_CODE from WQS_ITEM_PUR_ORDER_DETAILS where LAB_CODE=? and PURCHASE_ORDER_NO=? and PROCESS_FLOW_STATUS_ID=? and CURRENT_STATUS=?)a inner join(select MAJOR_CATEGORY_CODE,MAJOR_CATEGORY_DESC from WQS_MST_INV_CATEGORY)b on a.MAJOR_CATEGORY_CODE=b.MAJOR_CATEGORY_CODE");
                ps.setInt(1, lab);
                ps.setString(2, pno);
                ps.setString(3, "FR");
                ps.setString(4, "A");
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
                System.out.println("Err in check category:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("changeCat")) {

            String idesc = null;
            xml = xml + "<command>changeCat</command>";
            try {
                ps =
  con.prepareStatement("select ITEM_CODE from WQS_ITEM_PUR_ORDER_DETAILS where LAB_CODE=? and PURCHASE_ORDER_NO=? and MAJOR_CATEGORY_CODE=? and PROCESS_FLOW_STATUS_ID=? and CURRENT_STATUS=?");
                ps.setInt(1, lab);
                ps.setString(2, pno);
                ps.setString(3, catcode);
                ps.setString(4, "FR");
                ps.setString(5, "A");
                rs2 = ps.executeQuery();
                // ps.close();
                while (rs2.next()) {
                    String icode = rs2.getString("ITEM_CODE");
                    System.out.println(icode);
                    if (catdesc.equalsIgnoreCase("chemical")) {
                        System.out.println("chemical");
                        ps1 =
 con.prepareStatement("select CHEMICAL_CODE,CHE_SPECIFICATION from WQS_MST_INV_CHEMICAL where CHEMICAL_CODE=?");
                        ps1.setString(1, icode);
                        rs = ps1.executeQuery();
                        if (rs.next()) {
                            idesc = rs.getString("CHE_SPECIFICATION");
                        }
                    } else if (catdesc.equalsIgnoreCase("glassware")) {
                        System.out.println("chemical");
                        ps1 =
 con.prepareStatement("select GLASSWARE_CODE,GLASSWARE_SPEC from WQS_MST_INV_GLASSWARE where GLASSWARE_CODE=?");
                        ps1.setString(1, icode);
                        rs = ps1.executeQuery();
                        if (rs.next()) {
                            idesc = rs.getString("GLASSWARE_SPEC");
                        }
                    } else if (catdesc.equalsIgnoreCase("instrument")) {
                        System.out.println("instrument");
                        ps1 =
 con.prepareStatement("select INST_CATEGORY,INST_CATEGORY_SPEC from WQS_MST_INV_INSTRUMENT where INST_CATEGORY=?");
                        ps1.setString(1, icode);
                        rs = ps1.executeQuery();
                        if (rs.next()) {
                            idesc = rs.getString("INST_CATEGORY_SPEC");
                        }
                    } else {
                        System.out.println("Miscellaneous");
                        ps1 =
 con.prepareStatement("select MISCELLANEOUS_CODE,MISCELLANEOUS_SPEC from WQS_MST_INV_MISCELLANEOUS where MISCELLANEOUS_CODE=?");
                        ps1.setString(1, icode);
                        rs = ps1.executeQuery();
                        if (rs.next()) {
                            idesc = rs.getString("MISCELLANEOUS_SPEC");
                        }
                    }
                    xml = xml + "<count>";
                    xml = xml + "<icode>" + icode + "</icode>";
                    xml = xml + "<idesc>" + idesc + "</idesc>";
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
  con.prepareStatement("select QTY_ORDERED,QTY_RECEIVED_SOFAR,CURRENT_STATUS,ORDERED_UOM_CODE,PROCESS_FLOW_STATUS_ID from WQS_ITEM_PUR_ORDER_DETAILS where LAB_CODE=? and PURCHASE_ORDER_NO=? and MAJOR_CATEGORY_CODE=? and ITEM_CODE=? and PROCESS_FLOW_STATUS_ID=? and CURRENT_STATUS=?");
                ps.setInt(1, lab);
                ps.setString(2, pno);
                ps.setString(3, catcode);
                ps.setString(4, icode);
                ps.setString(5, "FR");
                ps.setString(6, "A");
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml =
 xml + "<qtyordered>" + rs.getString("QTY_ORDERED") + "</qtyordered>";
                    xml =
 xml + "<qtyreceived>" + rs.getString("QTY_RECEIVED_SOFAR") + "</qtyreceived>";
                    xml =
 xml + "<uom>" + rs.getString("ORDERED_UOM_CODE") + "</uom>";
                    xml =
 xml + "<statusid>" + rs.getString("PROCESS_FLOW_STATUS_ID") + "</statusid>";
                    xml =
 xml + "<cstatus>" + rs.getString("CURRENT_STATUS") + "</cstatus>";
                    // xml=xml+"<remarks>"+rs.getString("REMARKS")+"</remarks>";
                    xml = xml + "<flag>Success</flag>";
                }

            } catch (Exception e) {
                System.out.println("Err in changeItem:" + e.getMessage());
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
        PreparedStatement ps1 = null, ps = null;
        String updatedby = null, catcode = null, icode = null, fid = null;
        Timestamp ts = null;
        int ordered_qty = 0, received_qty = 0, rem_qty = 0;
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
        String cmd = request.getParameter("command");
        String[] lb = request.getParameter("lab").split("--");
        int lab = Integer.parseInt(lb[0]);
        String pno = request.getParameter("orderno");
        System.out.println(lab + "   " + pno);
        String rem = request.getParameter("remarks");
        if (cmd.equalsIgnoreCase("item")) {
            String category[] = request.getParameter("cat").split("--");
            ;
            catcode = category[0];
            System.out.println(catcode);
            String item[] = request.getParameter("item").split("--");
            icode = item[0];
            ordered_qty = Integer.parseInt(request.getParameter("oqty"));
            received_qty = Integer.parseInt(request.getParameter("rqty"));
            rem_qty = ordered_qty - received_qty;
            try {
                ps1 =
 con.prepareStatement("update  WQS_ITEM_PUR_ORDER_DETAILS set CURRENT_STATUS=?,RECONCILIATION_QTY=?,RECONCILIATION_REMARKS=? where LAB_CODE=? and PURCHASE_ORDER_NO=? and MAJOR_CATEGORY_CODE=? and ITEM_CODE=? and PROCESS_FLOW_STATUS_ID=? and CURRENT_STATUS=?");
                ps1.setString(1, "T");
                ps1.setInt(2, rem_qty);
                System.out.println("Rem qty=========>" + rem_qty);
                ps1.setString(3, rem);
                System.out.println("Remarks=========>" + rem);
                ps1.setInt(4, lab);
                System.out.println("lab=========>" + lab);
                ps1.setString(5, pno);
                System.out.println("Purchase Order Number=========>" + pno);
                ps1.setString(6, catcode);
                System.out.println("catcode=========>" + catcode);
                ps1.setString(7, icode);
                System.out.println("icode=========>" + icode);
                ps1.setString(8, "FR");
                ps1.setString(9, "A");
                ps1.executeUpdate();
                ps1.close();
                String msg = "Record is Cancelled Successfully";
                sendMSG(response, msg, "ok1");
            } catch (Exception e) {
                System.out.println("Err in validation:" + e.getMessage());
                // con.rollback();
                String msg = "<br><br>" + e.getMessage();
                sendMSG(response, msg, "Cancel");
            }
        } else if (cmd.equalsIgnoreCase("purchaseorder")) {
            try {
                ps =
  con.prepareStatement("select major_category_code,item_code,qty_ordered,qty_received_sofar,process_flow_status_id from wqs_item_pur_order_details where lab_code=? and purchase_order_no=? and current_status=? and process_flow_status_id not in('CL')");
                ps.setInt(1, lab);
                ps.setString(2, pno);
                ps.setString(3, "A");
                rs = ps.executeQuery();
                while (rs.next()) {
                    catcode = rs.getString("major_category_code");
                    icode = rs.getString("item_code");
                    ordered_qty =
                            Integer.parseInt(rs.getString("qty_ordered"));
                    received_qty =
                            Integer.parseInt(rs.getString("qty_received_sofar"));
                    rem_qty = ordered_qty - received_qty;
                    fid = rs.getString("process_flow_status_id");
                    ps1 =
 con.prepareStatement("update  WQS_ITEM_PUR_ORDER_DETAILS set CURRENT_STATUS=?,RECONCILIATION_QTY=?,RECONCILIATION_REMARKS=? where LAB_CODE=? and PURCHASE_ORDER_NO=? and MAJOR_CATEGORY_CODE=? and ITEM_CODE=? and PROCESS_FLOW_STATUS_ID=? and CURRENT_STATUS=?");
                    ps1.setString(1, "T");
                    ps1.setInt(2, rem_qty);
                    System.out.println("Rem qty=========>" + rem_qty);
                    ps1.setString(3, rem);
                    System.out.println("Remarks=========>" + rem);
                    ps1.setInt(4, lab);
                    System.out.println("lab=========>" + lab);
                    ps1.setString(5, pno);
                    System.out.println("Purchase Order Number=========>" +
                                       pno);
                    ps1.setString(6, catcode);
                    System.out.println("catcode=========>" + catcode);
                    ps1.setString(7, icode);
                    System.out.println("icode=========>" + icode);
                    ps1.setString(8, fid);
                    System.out.println("Flow Id=========>" + fid);
                    ps1.setString(9, "A");
                    ps1.executeUpdate();
                    ps1.close();
                }
                String msg = "Record is Cancelled Successfully";
                sendMSG(response, msg, "ok1");
            } catch (Exception e) {
                System.out.println("Err in submit purchaseorder wise cancellation=" +
                                   e.getMessage());
                String msg = "<br><br>" + e.getMessage();
                sendMSG(response, msg, "Cancel");
            }
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
