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

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class WQS_PurchaseOrder_Create extends HttpServlet {
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
        System.out.println("Welcome to Servlet create");
        response.setHeader("cache-control", "no-cache");
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        String xml = null, catcode = null;
        Calendar c;
        String updatedby = null, ucode = null;
        Timestamp ts = null;
        String rec1[] = null;
        int qty = 0, cnt = 0;

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
        if (cmd.equalsIgnoreCase("submit")) {
            System.out.println("Submit");
            xml = xml + "<command>" + cmd + "</command>";
            int lcode = Integer.parseInt(request.getParameter("lcode"));
            String ono = request.getParameter("ono");

            String[] od = request.getParameter("odate").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(od[2]), Integer.parseInt(od[1]) - 1,
                         Integer.parseInt(od[0]));
            java.util.Date d = c.getTime();
            Date orderdate = new Date(d.getTime());

            String scode = request.getParameter("scode");
            String remarks = request.getParameter("remarks");
            String record = request.getParameter("record");
            System.out.println(lcode + " " + ono + " " + orderdate + " " +
                               scode + " " + remarks);
            String rec[] = record.split(",,");
            System.out.println(rec.length);
            String flag = null;


            int received = 0;
            String cstatus = "A";
            try {
                con.setAutoCommit(false);
                System.out.println("inside try");
                ps =
  con.prepareStatement("select * from WQS_ITEM_PUR_ORDER_MASTER where LAB_CODE=? and PURCHASE_ORDER_NO=?");
                ps.setInt(1, lcode);
                ps.setString(2, ono);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml = xml + "<flag>Failure</flag>";
                } else {
                    PreparedStatement pstmt =
                        con.prepareStatement("insert into WQS_ITEM_PUR_ORDER_MASTER(LAB_CODE,PURCHASE_ORDER_NO,PURCHASE_ORDER_DATE,SUPPLIER_CODE,CURRENT_STATUS,REMARKS,UPDATED_DATE,UPDATED_BY_USER_ID)values(?,?,?,?,?,?,?,?)");
                    System.out.println("add");
                    pstmt.setInt(1, lcode);
                    System.out.println("Lab Code-----" + lcode);
                    pstmt.setString(2, ono);
                    System.out.println("ono-----" + ono);
                    pstmt.setDate(3, orderdate);
                    System.out.println("orderdate-----" + orderdate);
                    pstmt.setString(4, scode);
                    System.out.println("scode-----" + scode);
                    pstmt.setString(5, cstatus);
                    System.out.println("status-----" + cstatus);
                    pstmt.setString(6, remarks);
                    System.out.println("remarks-----" + remarks);
                    pstmt.setTimestamp(7, ts);
                    pstmt.setString(8, updatedby);
                    pstmt.executeUpdate();
                    flag = "Success";
                    xml = xml + "<flag>Success</flag>";

                }
            } catch (SQLException e) {
                System.out.println("Err in Insert1:" + e.getMessage());
                xml = xml + "<flag>Failure</flag>";
            }
            if (flag.equalsIgnoreCase("Success")) {
                try {
                    for (int i = 0; i < rec.length; i++) {
                        System.out.println("rec2:" + rec[i]);
                        String a1 = rec[i];
                        System.out.println(a1);
                        rec1 = a1.split("//");
                        System.out.println("rec before=" + rec1[3]);
                        qty = Integer.parseInt(rec1[3]);
                        System.out.println("rer:" + qty);

                        try {
                            ps =
  con.prepareStatement("select MAJOR_CATEGORY_CODE from WQS_MST_INV_CATEGORY where MAJOR_CATEGORY_DESC=?");
                            ps.setString(1, rec1[0]);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                catcode = rs.getString("MAJOR_CATEGORY_CODE");
                                System.out.println("Category:" + catcode);
                            }
                        } catch (Exception e) {
                            System.out.println("Err in category code selection:" +
                                               e.getMessage());
                        }
                        try {
                            System.out.println("purchase details");
                            ps =
  con.prepareStatement("select * from WQS_ITEM_PUR_ORDER_DETAILS where LAB_CODE=? and PURCHASE_ORDER_NO=? and MAJOR_CATEGORY_CODE=? and ITEM_CODE=?");
                            ps.setInt(1, lcode);
                            ps.setString(2, ono);
                            ps.setString(3, catcode);
                            ps.setString(4, rec1[1]);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                xml = xml + "<fg>Failure</fg>";
                            } else {
                                System.out.println("uom desc:" + rec1[4]);
                                PreparedStatement pstmt =
                                    con.prepareStatement("insert into WQS_ITEM_PUR_ORDER_DETAILS(LAB_CODE,PURCHASE_ORDER_NO,MAJOR_CATEGORY_CODE,ITEM_CODE,QTY_ORDERED,QTY_RECEIVED_SOFAR,CURRENT_STATUS,REMARKS,UPDATED_DATE,UPDATED_BY_USER_ID,ORDERED_UOM_CODE,RECEIVED_UOM_CODE,PROCESS_FLOW_STATUS_ID,BONUS_QTY_SOFAR)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                                pstmt.setInt(1, lcode);
                                System.out.println("Lab Code-----" + lcode);
                                pstmt.setString(2, ono);
                                System.out.println("ono-----" + ono);
                                pstmt.setString(3, catcode);
                                System.out.println("catcode-----" + catcode);
                                pstmt.setString(4, rec1[1]);
                                System.out.println("rec1[1]-----" + rec1[1]);
                                pstmt.setInt(5, qty);
                                System.out.println("qty-----" + qty);
                                pstmt.setInt(6, received);
                                System.out.println("received-----" + received);
                                pstmt.setString(7, cstatus);
                                System.out.println("cstatus-----" + cstatus);
                                pstmt.setString(8, rec1[5]);
                                System.out.println("rec1[4]-----" + rec1[5]);
                                pstmt.setTimestamp(9, ts);
                                pstmt.setString(10, updatedby);
                                pstmt.setString(11, rec1[4]);
                                pstmt.setString(12, rec1[4]);
                                pstmt.setString(13, "CR");
                                pstmt.setInt(14, 0);
                                pstmt.executeUpdate();
                                con.commit();
                                System.out.println(catcode + " " + rec1[1] +
                                                   " " + qty + " " + rec1[4]);
                                xml = xml + "<fg>Success</fg>";
                                System.out.println(xml);
                            }
                        } catch (Exception e) {
                            System.out.println("Err10:" + e.getMessage());
                            con.rollback();
                        }
                    }
                } catch (Exception e) {
                    System.out.println("err in for loop try:" +
                                       e.getMessage());
                    // con.rollback();
                }
            }
        } else {
            int lcode = Integer.parseInt(request.getParameter("LabCode"));
            String orderno = request.getParameter("OrderNo");

            xml = xml + "<command>checkAvail</command>";
            try {
                PreparedStatement pstmt =
                    con.prepareStatement("select * from WQS_ITEM_PUR_ORDER_MASTER where LAB_CODE=? and PURCHASE_ORDER_NO=?");
                pstmt.setInt(1, lcode);
                pstmt.setString(2, orderno);

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
        System.out.println(xml);
        out.println(xml);
        out.close();
    }
}
