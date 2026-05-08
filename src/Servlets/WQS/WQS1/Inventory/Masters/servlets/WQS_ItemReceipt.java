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

public class WQS_ItemReceipt extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        System.out.println("Welcome to servlet");
        response.setHeader("Cache-Control", "no-cache");
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        String xml = null, bcode = null, ouom = null, ruom = null;
        int tot = 0, avail = 0, ode = 0;
        Calendar c1;
        String updatedby = null;
        Timestamp ts = null;


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
        if (cmd.equalsIgnoreCase("changeOrder")) {
            int labcode = Integer.parseInt(request.getParameter("LabCode"));
            String order = request.getParameter("OrderNo");
            xml = xml + "<command>changeOrder</command>";
            try {
                ps =
  con.prepareStatement("select * from(select distinct(MAJOR_CATEGORY_CODE),PURCHASE_ORDER_NO,CURRENT_STATUS,LAB_CODE,PROCESS_FLOW_STATUS_ID from wqs_item_pur_order_details)a inner join(select MAJOR_CATEGORY_CODE,MAJOR_CATEGORY_DESC from WQS_MST_INV_CATEGORY)b on a.MAJOR_CATEGORY_CODE=b.MAJOR_CATEGORY_CODE where a.PURCHASE_ORDER_NO=? and a.LAB_CODE=? and a.CURRENT_STATUS='A' and a.PROCESS_FLOW_STATUS_ID in('FR')");
                ps.setString(1, order);
                ps.setInt(2, labcode);
                rs = ps.executeQuery();
                try {
                    while (rs.next()) {
                        String code = rs.getString("MAJOR_CATEGORY_CODE");
                        String desc = rs.getString("MAJOR_CATEGORY_DESC");
                        System.out.println(code + " " + desc);
                        if (!((desc.equalsIgnoreCase("instrument")) ||
                              (code.equalsIgnoreCase("ins")))) {
                            xml = xml + "<count>";
                            xml = xml + "<CatCode>" + code + "</CatCode>";
                            xml = xml + "<CatDesc>" + desc + "</CatDesc>";
                            xml = xml + "</count>";
                        }
                    }

                    xml = xml + "<flag>Success</flag>";
                } catch (Exception e) {
                    xml = xml + "<flag>failure</flag>";
                    System.out.println("Err in changeOrder1:" +
                                       e.getMessage());
                }

            } catch (Exception e) {
                System.out.println("Err in changeOrder2:" + e.getMessage());
            }
        } else if (cmd.equalsIgnoreCase("changeCat")) {
            int labcode = Integer.parseInt(request.getParameter("LabCode"));
            String order = request.getParameter("OrderNo");
            String catcode = request.getParameter("CatCode");
            String catdesc = request.getParameter("CatDesc");
            System.out.println(catcode);
            System.out.println(catdesc);
            xml = xml + "<command>changeCat</command>";
            try {
                ps =
  con.prepareStatement("select ITEM_CODE from wqs_item_pur_order_details where MAJOR_CATEGORY_CODE=? and PURCHASE_ORDER_NO=? and LAB_CODE=? and CURRENT_STATUS='A' and PROCESS_FLOW_STATUS_ID in('FR')");
                ps.setString(1, catcode);
                ps.setString(2, order);
                ps.setInt(3, labcode);
                rs = ps.executeQuery();
                try {
                    while (rs.next()) {
                        xml = xml + "<count>";
                        String itemcode = rs.getString("ITEM_CODE");
                        System.out.println("ItemCode:" + itemcode);
                        xml = xml + "<ItemCode>" + itemcode + "</ItemCode>";

                        if (catdesc.equalsIgnoreCase("Chemical")) {
                            System.out.println("chemical");
                            try {
                                PreparedStatement pst =
                                    con.prepareStatement("Select CHE_SPECIFICATION from WQS_MST_INV_CHEMICAL where CHEMICAL_CODE=?");
                                pst.setString(1, itemcode);
                                ResultSet rs1 = pst.executeQuery();
                                if (rs1.next()) {
                                    xml =
 xml + "<ItemDesc>" + rs1.getString("CHE_SPECIFICATION") + "</ItemDesc>";
                                }
                                xml = xml + "<flag>Success</flag>";
                            } catch (Exception e) {
                                System.out.println("Err in changeCat Chemical:" +
                                                   e.getMessage());
                                xml = xml + "<flag>failure</flag>";
                            }
                        } else if (catdesc.equalsIgnoreCase("Glassware")) {
                            System.out.println("Glassware");
                            try {
                                PreparedStatement pst =
                                    con.prepareStatement("Select GLASSWARE_SPEC from WQS_MST_INV_GLASSWARE where GLASSWARE_CODE=?");
                                pst.setString(1, itemcode);
                                ResultSet rs1 = pst.executeQuery();
                                while (rs1.next()) {
                                    xml =
 xml + "<ItemDesc>" + rs1.getString("GLASSWARE_SPEC") + "</ItemDesc>";
                                }
                                xml = xml + "<flag>Success</flag>";
                            } catch (Exception e) {
                                System.out.println("Err in changeCat Glassware:" +
                                                   e.getMessage());
                                xml = xml + "<flag>failure</flag>";
                            }
                        } else {
                            System.out.println("Miscellaneous");
                            try {
                                PreparedStatement pst =
                                    con.prepareStatement("Select MISCELLANEOUS_CODE,MISCELLANEOUS_SPEC from WQS_MST_INV_MISCELLANEOUS where MISCELLANEOUS_CODE=?");
                                pst.setString(1, itemcode);
                                rs = pst.executeQuery();
                                while (rs.next()) {
                                    xml =
 xml + "<ItemDesc>" + rs.getString("MISCELLANEOUS_SPEC") + "</ItemDesc>";
                                }
                                xml = xml + "<flag>Success</flag>";
                            } catch (Exception e) {
                                System.out.println("Err in changeCat Miscelaneous:" +
                                                   e.getMessage());
                                xml = xml + "<flag>failure</flag>";
                            }
                        }
                        xml = xml + "</count>";
                    }

                } catch (Exception e) {
                    System.out.println("Err in changeCat1:" + e.getMessage());
                }

            } catch (Exception e) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("Err in changeCat2:" + e.getMessage());
            }

        } else if (cmd.equalsIgnoreCase("changeItem")) {
            System.out.println("Inside changeItem");
            String subcmd = request.getParameter("subcmd");
            System.out.println("subcmd Code================>" + subcmd);
            int labcode = Integer.parseInt(request.getParameter("LabCode"));
            String order = request.getParameter("OrderNo");
            String catcode = request.getParameter("CatCode");
            String catdesc = request.getParameter("CatDesc");
            String itemcode = request.getParameter("ItemCode");
            xml = xml + "<command>changeItem</command>";
            try {
                ps =
  con.prepareStatement("select QTY_ORDERED,QTY_RECEIVED_SOFAR,ORDERED_UOM_CODE,RECEIVED_UOM_CODE from wqs_item_pur_order_details where LAB_CODE=? AND PURCHASE_ORDER_NO=? AND MAJOR_CATEGORY_CODE=? AND ITEM_CODE=?");
                ps.setInt(1, labcode);
                ps.setString(2, order);
                ps.setString(3, catcode);
                ps.setString(4, itemcode);
                rs = ps.executeQuery();
                if (rs.next()) {
                    ode = Integer.parseInt(rs.getString("QTY_ORDERED"));
                    System.out.println("ordered:" + ode);
                    tot = Integer.parseInt(rs.getString("QTY_RECEIVED_SOFAR"));
                    ouom = rs.getString("ORDERED_UOM_CODE");
                    ruom = rs.getString("RECEIVED_UOM_CODE");
                    System.out.println("Received so far:" + tot);
                    xml = xml + "<QTY_ORDERED>" + ode + "</QTY_ORDERED>";
                    xml =
 xml + "<QTY_RECEIVED_SOFAR>" + tot + "</QTY_RECEIVED_SOFAR>";
                    xml =
 xml + "<ORDERED_UOM_CODE>" + ouom + "</ORDERED_UOM_CODE>";
                    xml =
 xml + "<RECEIVED_UOM_CODE>" + ruom + "</RECEIVED_UOM_CODE>";
                }
            } catch (Exception e) {
                System.out.println("Err in changeItem:" + e.getMessage());
            }
            if (subcmd.equalsIgnoreCase("item")) {
                if (catdesc.equalsIgnoreCase("Glassware")) {
                    System.out.println("Glassware");
                    try {
                        System.out.println("inside Glassware");
                        PreparedStatement pst =
                            con.prepareStatement("Select NO_AVAILABLE from WQS_GLASSWARE_MASTER where LAB_CODE=? and GLASSWARE_CODE=?");
                        pst.setInt(1, labcode);
                        pst.setString(2, itemcode);
                        ResultSet rs1 = pst.executeQuery();
                        if (rs1.next()) {
                            avail =
Integer.parseInt(rs1.getString("NO_AVAILABLE"));
                            xml = xml + "<avail>" + avail + "</avail>";
                            xml = xml + "<flag>Success</flag>";
                        } else {
                            xml = xml + "<flag>failure</flag>";
                        }
                    } catch (Exception e) {
                        System.out.println("Err in Glassware stock availability check:" +
                                           e.getMessage());
                    }
                } else {
                    System.out.println("Miscellaneous");
                    try {
                        System.out.println("inside miscellaneous");
                        PreparedStatement pst =
                            con.prepareStatement("Select QTY_AVAILABLE from WQS_MISCELLANEOUS_MASTER where LAB_CODE=? and MISCELLANEOUS_CODE=?");
                        pst.setInt(1, labcode);
                        pst.setString(2, itemcode);
                        ResultSet rs1 = pst.executeQuery();
                        if (rs1.next()) {
                            avail =
Integer.parseInt(rs1.getString("QTY_AVAILABLE"));
                            xml = xml + "<avail>" + avail + "</avail>";
                            xml = xml + "<flag>Success</flag>";
                        } else {
                            xml = xml + "<flag>failure</flag>";
                        }
                    } catch (Exception e) {
                        System.out.println("Err in Miscelaneous stock availability check:" +
                                           e.getMessage());
                    }
                }
            } else if (subcmd.equalsIgnoreCase("brand")) {
                bcode = request.getParameter("bcode");
                System.out.println("chemical");
                try {
                    System.out.println("inside chemical");
                    PreparedStatement pst =
                        con.prepareStatement("Select QTY_AVAILABLE from WQS_CHEMICAL_MASTER where LAB_CODE=? and CHEMICAL_CODE=? and BRAND_CODE=?");
                    pst.setInt(1, labcode);
                    pst.setString(2, itemcode);
                    pst.setString(3, bcode);
                    ResultSet rs1 = pst.executeQuery();
                    if (rs1.next()) {
                        avail =
Integer.parseInt(rs1.getString("QTY_AVAILABLE"));
                        xml = xml + "<avail>" + avail + "</avail>";
                        xml = xml + "<flag>Success</flag>";
                    } else {
                        xml = xml + "<flag>failure</flag>";
                    }
                } catch (Exception e) {
                    System.out.println("Err in Chemical stock availability check:" +
                                       e.getMessage());
                }
            }
        }
        xml = xml + "</response>";
        System.out.println(xml);
        System.out.println("end of the program");
        out.println(xml);
        out.close();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
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
        PreparedStatement ps = null;
        Calendar c1;
        String updatedby = null, ouom = null;
        Timestamp ts = null;
        int tot = 0, total = 0, ode = 0, bqty = 0, btotal = 0, bonus = 0;
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

        System.out.println("begining");
        String lb1 = request.getParameter("lab");
        System.out.println("lab:" + lb1);
        String[] lb = request.getParameter("lab").split("--");
        System.out.println("lab:" + lb);
        int labcode = Integer.parseInt(lb[0]);
        System.out.println("labcode :" + labcode);
        String order = request.getParameter("orderno");
        String[] cat = request.getParameter("category").split("--");
        System.out.println("Lab : " + labcode + " Category : " + cat);
        String catcode = cat[0];
        String catdesc = cat[1];
        String[] item = request.getParameter("item").split("--");
        String itemcode = item[0];
        String itemdesc = item[1];
        System.out.println(labcode + " " + order + " " + catcode + " " +
                           catdesc + " " + itemcode);
        String bcode = request.getParameter("bcode");

        String[] od = request.getParameter("rdate").split("/");
        c1 =
  new GregorianCalendar(Integer.parseInt(od[2]), Integer.parseInt(od[1]) - 1,
                        Integer.parseInt(od[0]));
        java.util.Date d = c1.getTime();
        Date rdate = new Date(d.getTime());
        System.out.println(rdate);

        int qty = Integer.parseInt(request.getParameter("received"));
        int avail = Integer.parseInt(request.getParameter("astock"));
        String bonusst = request.getParameter("bonus");
        System.out.println("bonus:" + bonusst);
        if (!bonusst.equalsIgnoreCase("")) {
            System.out.println("Bonus is not null");
            bonus = Integer.parseInt(bonusst);
        }
        System.out.println("Bonus Quantity:" + bonus);
        String remarks = request.getParameter("remarks");
        System.out.println("received:" + qty);

        try {
            con.setAutoCommit(false);
            try {
                ps =
  con.prepareStatement("select QTY_ORDERED,QTY_RECEIVED_SOFAR,ORDERED_UOM_CODE,BONUS_QTY_SOFAR from wqs_item_pur_order_details where LAB_CODE=? AND PURCHASE_ORDER_NO=? AND MAJOR_CATEGORY_CODE=? AND ITEM_CODE=?");
                ps.setInt(1, labcode);
                ps.setString(2, order);
                ps.setString(3, catcode);
                ps.setString(4, itemcode);
                rs = ps.executeQuery();
                if (rs.next()) {
                    ode = Integer.parseInt(rs.getString("QTY_ORDERED"));
                    System.out.println("ordered:" + ode);
                    tot = Integer.parseInt(rs.getString("QTY_RECEIVED_SOFAR"));
                    ouom = rs.getString("ORDERED_UOM_CODE");
                    System.out.println("Received so far:" + tot);
                    String bty = rs.getString("BONUS_QTY_SOFAR");
                    if (bty == null) {
                        System.out.println("column is empty");
                        bqty = 0;
                    } else
                        bqty = Integer.parseInt(bty);
                    total = tot + qty;
                    System.out.println("Received Sofar Total:" + total);
                    btotal = bqty + bonus;
                    if (total <= ode) {
                        if (total == ode) {
                            try {
                                String status = "T";
                                PreparedStatement ps1 =
                                    con.prepareStatement("update wqs_item_pur_order_details set QTY_RECEIVED_SOFAR=?,CURRENT_STATUS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,RECEIVED_UOM_CODE=?,BONUS_QTY_SOFAR=? where LAB_CODE=? and PURCHASE_ORDER_NO=? and MAJOR_CATEGORY_CODE=? and ITEM_CODE=?");
                                ps1.setInt(1, total);
                                ps1.setString(2, status);
                                ps1.setString(3, updatedby);
                                ps1.setTimestamp(4, ts);
                                ps1.setString(5, ouom);
                                ps1.setInt(6, btotal);
                                ps1.setInt(7, labcode);
                                ps1.setString(8, order);
                                ps1.setString(9, catcode);
                                ps1.setString(10, itemcode);
                                ps1.executeUpdate();
                            } catch (Exception e) {
                                System.out.println("Err in submit equal updation:" +
                                                   e.getMessage());
                                con.rollback();
                            }
                        } else {
                            try {
                                PreparedStatement ps1 =
                                    con.prepareStatement("update wqs_item_pur_order_details set QTY_RECEIVED_SOFAR=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,RECEIVED_UOM_CODE=?,BONUS_QTY_SOFAR=? where LAB_CODE=? AND PURCHASE_ORDER_NO=? AND MAJOR_CATEGORY_CODE=? AND ITEM_CODE=?");
                                ps1.setInt(1, total);
                                ps1.setString(2, updatedby);
                                ps1.setTimestamp(3, ts);
                                ps1.setString(4, ouom);
                                ps1.setInt(5, btotal);
                                ps1.setInt(6, labcode);
                                ps1.setString(7, order);
                                ps1.setString(8, catcode);
                                ps1.setString(9, itemcode);
                                ps1.executeUpdate();
                            } catch (Exception e) {
                                System.out.println("Err in submit less updation:" +
                                                   e.getMessage());
                                con.rollback();
                            }
                        }
                        try {
                            System.out.println("inside insert");
                            PreparedStatement pstmt =
                                con.prepareStatement("insert into WQS_ITEM_RECEIPT(LAB_CODE,PURCHASE_ORDER_NO,MAJOR_CATEGORY_CODE,ITEM_CODE,DATE_OF_RECEIPT,QTY_RECEIVED,REMARKS,UPDATED_DATE,UPDATED_BY_USER_ID,UOM_CODE,BONUS_QTY)values(?,?,?,?,?,?,?,?,?,?,?)");
                            pstmt.setInt(1, labcode);
                            System.out.println("lab code: " + labcode);
                            pstmt.setString(2, order);
                            System.out.println("order: " + order);
                            pstmt.setString(3, catcode);
                            System.out.println("catcode: " + catcode);
                            pstmt.setString(4, itemcode);
                            System.out.println("itemcode: " + itemcode);
                            pstmt.setDate(5, rdate);
                            System.out.println("rdate: " + rdate);
                            pstmt.setInt(6, qty);
                            System.out.println("qty: " + qty);
                            pstmt.setString(7, remarks);
                            System.out.println("remarks: " + remarks);
                            pstmt.setTimestamp(8, ts);
                            System.out.println("ts: " + ts);
                            pstmt.setString(9, updatedby);
                            System.out.println("updatedby: " + updatedby);
                            pstmt.setString(10, ouom);
                            System.out.println("ucode: " + ouom);
                            pstmt.setInt(11, bonus);
                            pstmt.executeUpdate();

                        } catch (Exception e) {
                            System.out.println("Exception in connection:" + e);
                            con.rollback();
                            String msg = "<br><br>" + e;
                            sendMessage(response, msg, "ok");
                        }
                        if (catdesc.equalsIgnoreCase("Chemical")) {
                            System.out.println("chemical");
                            /*  try
                                              {
                                                  System.out.println("inside chemical");
                                                  PreparedStatement pst=con.prepareStatement("Select QTY_AVAILABLE from WQS_CHEMICAL_MASTER where LAB_CODE=? and CHEMICAL_CODE=? and BRAND_CODE=?");
                                                  pst.setInt(1,labcode);
                                                  pst.setString(2,itemcode);
                                                  pst.setString(3,bcode);
                                                  ResultSet rs1=pst.executeQuery();
                                                  if(rs1.next())
                                                  {
                                                      int avail=Integer.parseInt(rs1.getString("QTY_AVAILABLE"));*/
                            System.out.println("Available in Chemical Master:" +
                                               avail);
                            int qaty = avail + qty + bonus;
                            System.out.println("Updation in Chemical Master:" +
                                               qaty);
                            try {
                                PreparedStatement p =
                                    con.prepareStatement("update WQS_CHEMICAL_MASTER set QTY_AVAILABLE=?,UPDATE_BY_USER_ID=?,UPDATE_DATE=? where LAB_CODE=? and CHEMICAL_CODE=? and BRAND_CODE=?");
                                p.setInt(1, qaty);
                                p.setString(2, updatedby);
                                p.setTimestamp(3, ts);
                                p.setInt(4, labcode);
                                p.setString(5, itemcode);
                                p.setString(6, bcode);
                                p.executeUpdate();
                            } catch (Exception e) {
                                System.out.println("Err in chemical master:" +
                                                   e.getMessage());
                                con.rollback();
                            }
                            /* }
                                              }
                                              catch(Exception e)
                                              {
                                                  System.out.println("Err in submit Chemical:"+e.getMessage());
                                                  con.rollback();
                                              }*/
                        } else if (catdesc.equalsIgnoreCase("Glassware")) {
                            System.out.println("Glassware");
                            /*try
                                              {
                                                  System.out.println("inside Glassware");
                                                  PreparedStatement pst=con.prepareStatement("Select NO_AVAILABLE from WQS_GLASSWARE_MASTER where LAB_CODE=? and GLASSWARE_CODE=?");
                                                  pst.setInt(1,labcode);
                                                  pst.setString(2,itemcode);
                                                  ResultSet rs1=pst.executeQuery();
                                                  try
                                                  {
                                                      if(rs1.next())
                                                      {
                                                          int avail=Integer.parseInt(rs1.getString("NO_AVAILABLE"));*/
                            int qaty = avail + qty + bonus;
                            System.out.println("Available in Master:" + avail);
                            System.out.println("updation in Master:" + qaty);
                            try {
                                PreparedStatement p =
                                    con.prepareStatement("update WQS_GLASSWARE_MASTER set NO_AVAILABLE=?,UPDATE_BY_USER_ID=?,UPDATE_DATE=? where LAB_CODE=? and GLASSWARE_CODE=?");
                                p.setInt(1, qaty);
                                p.setString(2, updatedby);
                                p.setTimestamp(3, ts);
                                p.setInt(4, labcode);
                                p.setString(5, itemcode);
                                p.executeUpdate();
                            } catch (Exception e) {

                                System.out.println("Err in Glassware:" +
                                                   e.getMessage());
                                con.rollback();
                            }
                            /*   }
                                                 }catch(Exception e){
                                                      System.out.println("Err in glassware:"+e.getMessage());
                                                      con.rollback();
                                                  }
                                              }
                                              catch(Exception e)
                                              {
                                                  System.out.println("Err in submit Glassware:"+e.getMessage());
                                                  con.rollback();
                                              }*/
                        } else {
                            System.out.println("Miscellaneous");
                            /*try
                                              {
                                                  System.out.println("inside miscellaneous");
                                                  PreparedStatement pst=con.prepareStatement("Select QTY_AVAILABLE from WQS_MISCELLANEOUS_MASTER where LAB_CODE=? and MISCELLANEOUS_CODE=?");
                                                  pst.setInt(1,labcode);
                                                  pst.setString(2,itemcode);
                                                  ResultSet rs1=pst.executeQuery();
                                                  try
                                                  {
                                                      if(rs1.next())
                                                      {
                                                          int avail=Integer.parseInt(rs1.getString("QTY_AVAILABLE"));*/
                            int qaty = avail + qty + bonus;
                            System.out.println("Available in Miscellaneous Master:" +
                                               avail);
                            System.out.println("updation in Miscellaneous Master:" +
                                               qaty);
                            try {
                                PreparedStatement p =
                                    con.prepareStatement("update WQS_MISCELLANEOUS_MASTER set QTY_AVAILABLE=?,UPDATE_BY_USER_ID=?,UPDATE_DATE=? where LAB_CODE=? and MISCELLANEOUS_CODE=?");
                                p.setInt(1, qaty);
                                p.setString(2, updatedby);
                                p.setTimestamp(3, ts);
                                p.setInt(4, labcode);
                                p.setString(5, itemcode);
                                p.executeUpdate();
                            } catch (Exception e) {
                                System.out.println("Err in miscellaneous");
                            }
                            /*  }

                                                  }
                                                  catch(Exception e)
                                                  {
                                                        System.out.println(e);
                                                        con.rollback();
                                                  }
                                              }
                                              catch(Exception e)
                                              {
                                                  System.out.println("Err in submit Miscelaneous:"+e.getMessage());
                                                  con.rollback();
                                              }*/
                        }
                        String msg =
                            "Receipt Details Has been Successfully Inserted";
                        msg = "<br><br><p><b>" + msg + "</b></p>";
                        sendMessage(response, msg, "ok");
                        con.commit();
                    } else {
                        String msg =
                            "Failed to insert values. Received Quantity is higher than the Ordered Quantity.<br><br> Ordered Quantity is:" +
                            ode + " " + ouom + " only";
                        msg = "<br><br><p><b>" + msg + "</b></p>";
                        sendMessage(response, msg, "cancel");
                        con.rollback();
                    }
                }
            } catch (Exception e) {
                System.out.println("Err in submit select:" + e.getMessage());
                con.rollback();
            }
        } catch (Exception e) {
            System.out.println("Err in Any insertion statement:" +
                               e.getMessage());
        }

    }

    private void sendMessage(HttpServletResponse response, String msg,
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
