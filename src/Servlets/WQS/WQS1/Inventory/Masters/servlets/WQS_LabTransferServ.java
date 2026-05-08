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

public class WQS_LabTransferServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String CONTENT_TYPE = "text/xml";
        response.setContentType(CONTENT_TYPE);
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        String xml = null, ucode = null, bcode = null;
        Calendar c;
        int avail = 0, c1 = 0;
        String updatedby = null;
        Timestamp ts = null;
        response.setHeader("Cache-Control", "no-cache");
        System.out.println("Welcome to servlet");
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
        if (cmd.equalsIgnoreCase("changeCat")) {
            String catdesc = request.getParameter("CatDesc");
            int labcode = Integer.parseInt(request.getParameter("LabCode"));
            xml = xml + "<command>changeCat</command>";
            if (catdesc.equalsIgnoreCase("Chemical")) {
                System.out.println("chemical");
                try {
                    ps =
  con.prepareStatement("select * from(Select distinct CHEMICAL_CODE from WQS_CHEMICAL_MASTER where LAB_CODE=?)a inner join(select CHEMICAL_CODE,CHE_SPECIFICATION  from WQS_MST_INV_CHEMICAL)b on a.CHEMICAL_CODE=b.CHEMICAL_CODE");
                    ps.setInt(1, labcode);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        xml = xml + "<count>";
                        xml =
 xml + "<ItemCode>" + rs.getString("CHEMICAL_CODE") + "</ItemCode>";
                        xml =
 xml + "<ItemDesc>" + rs.getString("CHE_SPECIFICATION") + "</ItemDesc>";
                        xml = xml + "</count>";
                        c1 = c1 + 1;
                    }
                    if (c1 > 0) {
                        xml = xml + "<flag>Success</flag>";
                    } else {
                        xml = xml + "<flag>failure</flag>";
                    }
                } catch (Exception e) {
                    System.out.println("Err1 in chemical:" + e.getMessage());
                }
            } else if (catdesc.equalsIgnoreCase("Glassware")) {
                System.out.println("Glassware");
                try {
                    ps =
  con.prepareStatement("select * from(Select LAB_CODE,GLASSWARE_CODE from WQS_GLASSWARE_MASTER)a inner join(select GLASSWARE_CODE,GLASSWARE_SPEC from WQS_MST_INV_GLASSWARE)b on a.GLASSWARE_CODE=b.GLASSWARE_CODE where a.LAB_CODE=?");
                    ps.setInt(1, labcode);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        xml = xml + "<count>";
                        xml =
 xml + "<ItemCode>" + rs.getString("GLASSWARE_CODE") + "</ItemCode>";
                        xml =
 xml + "<ItemDesc>" + rs.getString("GLASSWARE_SPEC") + "</ItemDesc>";
                        xml = xml + "</count>";
                        c1 = c1 + 1;
                    }
                    if (c1 > 0) {
                        xml = xml + "<flag>Success</flag>";
                    } else {
                        xml = xml + "<flag>failure</flag>";
                    }

                } catch (Exception e) {
                    System.out.println("Err IN Glassware:" + e.getMessage());
                }
            } else {
                System.out.println("Miscellaneous");
                try {
                    ps =
  con.prepareStatement("select * from(Select LAB_CODE,MISCELLANEOUS_CODE from WQS_MISCELLANEOUS_MASTER)a inner join(select MISCELLANEOUS_CODE,MISCELLANEOUS_SPEC from WQS_MST_INV_MISCELLANEOUS)b on a.MISCELLANEOUS_CODE=b.MISCELLANEOUS_CODE where a.LAB_CODE=?");
                    ps.setInt(1, labcode);
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        xml = xml + "<count>";
                        xml =
 xml + "<ItemCode>" + rs.getString("MISCELLANEOUS_CODE") + "</ItemCode>";
                        xml =
 xml + "<ItemDesc>" + rs.getString("MISCELLANEOUS_SPEC") + "</ItemDesc>";
                        xml = xml + "</count>";
                        c1 = c1 + 1;
                    }
                    if (c1 > 0) {
                        xml = xml + "<flag>Success</flag>";
                    } else {
                        xml = xml + "<flag>failure</flag>";
                    }
                } catch (Exception e) {
                    System.out.println("Err in Miscellaneous:" +
                                       e.getMessage());
                }
            }
        } else if (cmd.equalsIgnoreCase("changeItem")) {
            String subcmd = request.getParameter("subcmd");
            System.out.println("subcmd Code================>" + subcmd);
            int labcode = Integer.parseInt(request.getParameter("LabCode"));
            System.out.println("Lab Code is:" + labcode);
            String catcode = request.getParameter("CatCode");
            String catdesc = request.getParameter("CatDesc");
            String itemcode = request.getParameter("ItemCode");
            System.out.println(catcode + " " + catdesc + " " + itemcode);
            xml = xml + "<command>changeItem</command>";
            if (subcmd.equalsIgnoreCase("item")) {
                if (catdesc.equalsIgnoreCase("Glassware")) {
                    System.out.println("Glassware");
                    try {
                        PreparedStatement pst =
                            con.prepareStatement("Select NO_AVAILABLE,UOM_CODE from WQS_GLASSWARE_MASTER where LAB_CODE=? and GLASSWARE_CODE=?");
                        pst.setInt(1, labcode);
                        pst.setString(2, itemcode);
                        System.out.println("sdasd");
                        ResultSet rs1 = pst.executeQuery();
                        if (rs1.next()) {
                            avail =
Integer.parseInt(rs1.getString("NO_AVAILABLE"));
                            ucode = rs1.getString("UOM_CODE");
                        }
                    } catch (Exception e) {
                        System.out.println("Err in submit Glassware:" +
                                           e.getMessage());
                    }
                } else {
                    System.out.println("Miscellaneous");
                    try {
                        PreparedStatement pst =
                            con.prepareStatement("Select QTY_AVAILABLE,UOM_CODE from WQS_MISCELLANEOUS_MASTER where LAB_CODE=? and MISCELLANEOUS_CODE=?");
                        pst.setInt(1, labcode);
                        pst.setString(2, itemcode);
                        ResultSet rs1 = pst.executeQuery();
                        if (rs1.next()) {
                            avail =
Integer.parseInt(rs1.getString("QTY_AVAILABLE"));
                            ucode = rs1.getString("UOM_CODE");
                        }
                    } catch (Exception e) {
                        System.out.println("Err in submit Miscelaneous:" +
                                           e.getMessage());
                    }
                }
            } else if (subcmd.equalsIgnoreCase("brand")) {
                bcode = request.getParameter("bcode");
                System.out.println("chemical");
                try {
                    PreparedStatement pst =
                        con.prepareStatement("Select QTY_AVAILABLE,UOM_CODE from WQS_CHEMICAL_MASTER where LAB_CODE=? and CHEMICAL_CODE=? and BRAND_CODE=?");
                    pst.setInt(1, labcode);
                    pst.setString(2, itemcode);
                    pst.setString(3, bcode);
                    ResultSet rs1 = pst.executeQuery();
                    if (rs1.next()) {
                        avail =
Integer.parseInt(rs1.getString("QTY_AVAILABLE"));
                        ucode = rs1.getString("UOM_CODE");
                    }
                } catch (Exception e) {
                    System.out.println("Err in submit Chemical:" +
                                       e.getMessage());
                }
            }
            System.out.println("Available " + catdesc + " Code " + itemcode +
                               " stock is : " + avail);
            xml = xml + "<flag>Success</flag>";
            xml = xml + "<stock>" + avail + "</stock>";
            xml = xml + "<uom>" + ucode + "</uom>";
        } else if (cmd.equalsIgnoreCase("validate")) {
            int labcode = Integer.parseInt(request.getParameter("LabCode"));
            System.out.println("Lab Code is:" + labcode);
            int tlabcode = Integer.parseInt(request.getParameter("tlab"));
            String catcode = request.getParameter("CatCode");
            String itemcode = request.getParameter("ItemCode");

            String[] od = request.getParameter("rdate").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(od[2]), Integer.parseInt(od[1]) - 1,
                         Integer.parseInt(od[0]));
            java.util.Date d = c.getTime();
            Date rdate = new Date(d.getTime());
            System.out.println(rdate);
            xml = xml + "<command>validate</command>";
            try {
                ps =
  con.prepareStatement("select qty_issued from wqs_lab_transfer where from_lab_code=? and to_lab_code=? and major_category_code=? and item_code=? and date_of_issue=?");
                ps.setInt(1, labcode);
                ps.setInt(2, tlabcode);
                ps.setString(3, catcode);
                ps.setString(4, itemcode);
                ps.setDate(5, rdate);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml =
 xml + "<qty>" + rs.getString("qty_issued") + "</qty>";
                    xml = xml + "<flag>Avail</flag>";
                    System.out.println("Already Available");
                } else
                    xml = xml + "<flag>NotAvail</flag>";
            } catch (Exception e) {
                System.out.println("Err in validation:" + e.getMessage());
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
        PreparedStatement ps = null;
        Calendar c1;
        String updatedby = null, flag = null;
        Timestamp ts = null;
        int avail = 0, transfer_id = 0;
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
        System.out.println("command is:" + cmd);
        String[] lb1 = request.getParameter("flab").split("--");
        int labcode1 = Integer.parseInt(lb1[0]);
        String[] lb2 = request.getParameter("tlab").split("--");
        int labcode2 = Integer.parseInt(lb2[0]);
        String[] cat = request.getParameter("category").split("--");
        String catcode = cat[0];
        String catdesc = cat[1];
        String[] item = request.getParameter("item").split("--");
        String itemcode = item[0];

        System.out.println(labcode1 + " " + labcode2 + " " + catcode + " " +
                           catdesc + " " + itemcode);
        String bcode = request.getParameter("bcode");

        String[] od = request.getParameter("rdate").split("/");
        c1 =
  new GregorianCalendar(Integer.parseInt(od[2]), Integer.parseInt(od[1]) - 1,
                        Integer.parseInt(od[0]));
        java.util.Date d = c1.getTime();
        Date rdate = new Date(d.getTime());
        System.out.println(rdate);
        int qty = Integer.parseInt(request.getParameter("issued"));
        avail = Integer.parseInt(request.getParameter("avail"));
        int qaty = avail - qty;
        String uom = request.getParameter("uom");
        System.out.println("after updation available qty:" + qaty);

        if (cmd.equalsIgnoreCase("Update")) {
            int stock = Integer.parseInt(request.getParameter("stock"));
            if (avail >= qty) {
                try {
                    ps =
  con.prepareStatement("update wqs_lab_transfer set qty_issued=? where from_lab_code=? and to_lab_code=? and major_category_code=? and item_code=? and date_of_issue=?");
                    ps.setInt(1, stock);
                    ps.setInt(2, labcode1);
                    ps.setInt(3, labcode2);
                    ps.setString(4, catcode);
                    ps.setString(5, itemcode);
                    ps.setDate(6, rdate);
                    int upd = ps.executeUpdate();
                    if (upd > 0) {
                        String msg =
                            "Lab transfer stock has been updated successfully";
                        msg = msg + "<br><br>";
                        sendMessage(response, msg, "ok");
                    } else {
                        String msg = "Failed to update stock values";
                        msg = msg + "<br><br>";
                        sendMessage(response, msg, "ok");
                    }
                } catch (Exception e) {
                    System.out.println("Exception in connection:" + e);
                    String msg = "<br><br>" + e;
                    sendMessage(response, msg, "ok");
                }
            } else {
                String msg =
                    "Out of stock value. Availble stock is:" + avail + " " +
                    uom;
                msg = "<br><br><p><b>" + msg + "</b></p>";
                sendMessage(response, msg, "cancel");
            }
        } else if (cmd.equalsIgnoreCase("submit")) {
            System.out.println("unit of measurement:" + uom);
            String remarks = request.getParameter("remarks");
            System.out.println("received:" + qty);
            try {
                if (avail >= qty) {
                    System.out.println("greaterthen or equal");
                    try {

                        System.out.println("insert");
                        PreparedStatement pm =
                            con.prepareStatement("insert into WQS_LAB_TRANSFER(FROM_LAB_CODE,TO_LAB_CODE,MAJOR_CATEGORY_CODE,ITEM_CODE,DATE_OF_ISSUE,QTY_ISSUED,REMARKS,UPDATED_DATE,UPDATED_BY_USER_ID,UOM_CODE,STATUS)values(?,?,?,?,?,?,?,?,?,?,?)");
                        pm.setInt(1, labcode1);
                        pm.setInt(2, labcode2);
                        pm.setString(3, catcode);
                        pm.setString(4, itemcode);
                        pm.setDate(5, rdate);
                        pm.setInt(6, qty);
                        pm.setString(7, remarks);
                        pm.setTimestamp(8, ts);
                        pm.setString(9, updatedby);
                        pm.setString(10, uom);
                        pm.setString(11, "Pending");
                        int i = pm.executeUpdate();
                        pm.close();
                        if (i >= 1) {
                            String query =
                                "select LAB_STOCK_TRANSFER_ID from WQS_LAB_TRANSFER where UPDATED_DATE=?";
                            try {
                                pm = con.prepareStatement(query);
                                pm.setTimestamp(1, ts);
                                ResultSet rss = pm.executeQuery();
                                if (rss.next()) {
                                    transfer_id =
                                            rss.getInt("LAB_STOCK_TRANSFER_ID");
                                    System.out.println("Transfer id========>" +
                                                       transfer_id);
                                }
                                rss.close();
                                pm.close();
                            } catch (Exception e) {
                                System.out.println("Err in tranfer_id selection:" +
                                                   e.getMessage());
                            }
                            String msg =
                                "Lab transfer has been created successfully";
                            msg = msg + "<br><br>";
                            sendMessage(response, msg, "ok");
                        } else {
                            sendMessage(response,
                                        "Failed to insert values due to unknown reason",
                                        "back");
                            con.rollback();
                        }
                    } catch (Exception e) {
                        System.out.println("Exception in connection:" + e);
                        con.rollback();
                        String msg = "<br><br>" + e;
                        sendMessage(response, msg, "ok");
                    }
                } else {
                    String msg =
                        "Out of stock value. Availble stock is:" + avail +
                        " " + uom;
                    msg = "<br><br><p><b>" + msg + "</b></p>";
                    sendMessage(response, msg, "cancel");
                }
            } catch (Exception e) {
                System.out.println("Err in insertion :" + e.getMessage());
            }
        }
        /* if(flag.equalsIgnoreCase("Success"))
        {
            try
            {
                System.out.println("updation");
                if(catdesc.equalsIgnoreCase("Chemical"))
                {
                        PreparedStatement p=con.prepareStatement("update WQS_CHEMICAL_MASTER set QTY_AVAILABLE=? where LAB_CODE=? and CHEMICAL_CODE=? and BRAND_CODE=?");
                        p.setInt(1,qaty);
                        p.setInt(2,labcode1);
                        p.setString(3,itemcode);
                        p.setString(4,bcode);
                        p.executeUpdate();
                        con.commit();
                }
                else if(catdesc.equalsIgnoreCase("Glassware"))
                {
                    PreparedStatement p=con.prepareStatement("update WQS_GLASSWARE_MASTER set NO_AVAILABLE=? where LAB_CODE=? and GLASSWARE_CODE=?");
                    p.setInt(1,qaty);
                    p.setInt(2,labcode1);
                    p.setString(3,itemcode);
                    p.executeUpdate();
                    con.commit();
                }
                else
                {
                    PreparedStatement p=con.prepareStatement("update WQS_MISCELLANEOUS_MASTER set QTY_AVAILABLE=? where LAB_CODE=? and MISCELLANEOUS_CODE=?");
                    p.setInt(1,qaty);
                    p.setInt(2,labcode1);
                    p.setString(3,itemcode);
                    p.executeUpdate();
                    con.commit();
                }
                if(cmd.equalsIgnoreCase("submit"))
                {
                    String msg="Lab transfer has been created successfully";
                    msg=msg+"<br><br>";
                    sendMessage(response,msg,"ok");
                }
                else
                {
                    String msg="Lab transfer stock has been updated successfully";
                    msg=msg+"<br><br>";
                    sendMessage(response,msg,"ok");
                }
            }
            catch(Exception e)
            {
                System.out.println("Err in insertion & updation:"+e.getMessage());
            }
        }*/
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
