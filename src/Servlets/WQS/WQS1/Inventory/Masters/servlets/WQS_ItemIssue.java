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

public class WQS_ItemIssue extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        String CONTENT_TYPE = "text/xml";
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        String xml = null, ucode = null, bcode = null;
        Calendar c;
        int avail = 0, c1 = 0;
        String updatedby = null;
        Timestamp ts = null;
        System.out.println("Welcome to servlet");
        response.setHeader("Cache-Control", "no-cache");
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
  con.prepareStatement("select * from(Select distinct LAB_CODE,CHEMICAL_CODE from WQS_CHEMICAL_MASTER)a inner join(select CHEMICAL_CODE,CHE_SPECIFICATION  from WQS_MST_INV_CHEMICAL)b on a.CHEMICAL_CODE=b.CHEMICAL_CODE where a.LAB_CODE=?");
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
        } else {
            int labcode = Integer.parseInt(request.getParameter("LabCode"));
            System.out.println("Lab Code is:" + labcode);
            String catcode = request.getParameter("CatCode");
            String catdesc = request.getParameter("CatDesc");
            String itemcode = request.getParameter("ItemCode");
            String[] od = request.getParameter("Rdate").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(od[2]), Integer.parseInt(od[1]) - 1,
                         Integer.parseInt(od[0]));
            java.util.Date d = c.getTime();
            Date rdate = new Date(d.getTime());

            int qty = Integer.parseInt(request.getParameter("QtyIssued"));
            String uom = request.getParameter("uom");
            String remarks = request.getParameter("Remarks");
            System.out.println(labcode);
            System.out.println(catcode);
            xml = xml + "<command>Submit</command>";
            try {
                System.out.println("selection");
                if (catdesc.equalsIgnoreCase("Chemical")) {
                    System.out.println("chemical");
                    try {
                        PreparedStatement pst =
                            con.prepareStatement("Select QTY_AVAILABLE from WQS_CHEMICAL_MASTER where LAB_CODE=? and CHEMICAL_CODE=?");
                        pst.setInt(1, labcode);
                        pst.setString(2, itemcode);
                        ResultSet rs1 = pst.executeQuery();
                        if (rs1.next()) {
                            avail =
Integer.parseInt(rs1.getString("QTY_AVAILABLE"));
                        }
                    } catch (Exception e) {
                        System.out.println("Err in submit Chemical:" +
                                           e.getMessage());
                    }
                } else if (catdesc.equalsIgnoreCase("Glassware")) {
                    System.out.println("Glassware");
                    try {
                        PreparedStatement pst =
                            con.prepareStatement("Select NO_AVAILABLE from WQS_GLASSWARE_MASTER where LAB_CODE=? and GLASSWARE_CODE=?");
                        pst.setInt(1, labcode);
                        pst.setString(2, itemcode);
                        ResultSet rs1 = pst.executeQuery();
                        if (rs1.next()) {
                            avail =
Integer.parseInt(rs1.getString("NO_AVAILABLE"));
                        }
                    } catch (Exception e) {
                        System.out.println("Err in submit Glassware:" +
                                           e.getMessage());
                    }
                } else {
                    System.out.println("Miscellaneous");
                    try {
                        PreparedStatement pst =
                            con.prepareStatement("Select QTY_AVAILABLE from WQS_MISCELLANEOUS_MASTER where LAB_CODE=? and MISCELLANEOUS_CODE=?");
                        pst.setInt(1, labcode);
                        pst.setString(2, itemcode);
                        ResultSet rs1 = pst.executeQuery();
                        if (rs1.next()) {
                            avail =
Integer.parseInt(rs1.getString("QTY_AVAILABLE"));
                        }
                    } catch (Exception e) {
                        System.out.println("Err in submit Miscelaneous:" +
                                           e.getMessage());
                    }
                }
                xml = xml + "<stock>" + avail + "</stock>";
                int qaty = avail - qty;
                xml = xml + "<rstock>" + qaty + "</rstock>";
                System.out.println(qaty);
                if (avail >= qty) {
                    System.out.println("greaterthen or equal");
                    try {
                        PreparedStatement ps1 =
                            con.prepareStatement("select UOM_CODE from WQS_MST_UOM where UOM_DESC=?");
                        ps1.setString(1, uom);
                        rs = ps1.executeQuery();
                        while (rs.next()) {
                            ucode = rs.getString("UOM_CODE");
                        }
                    } catch (Exception e) {
                        System.out.println("Err in UOM select:" +
                                           e.getMessage());
                    }
                    try {
                        System.out.println("updation");
                        if (catdesc.equalsIgnoreCase("Chemical")) {
                            PreparedStatement p =
                                con.prepareStatement("update WQS_CHEMICAL_MASTER set QTY_AVAILABLE=? where LAB_CODE=? and CHEMICAL_CODE=?");
                            p.setInt(1, qaty);
                            p.setInt(2, labcode);
                            p.setString(3, itemcode);
                            p.executeUpdate();
                        } else if (catdesc.equalsIgnoreCase("Glassware")) {
                            PreparedStatement p =
                                con.prepareStatement("update WQS_GLASSWARE_MASTER set NO_AVAILABLE=? where LAB_CODE=? and GLASSWARE_CODE=?");
                            p.setInt(1, qaty);
                            p.setInt(2, labcode);
                            p.setString(3, itemcode);
                            p.executeUpdate();
                        } else {
                            PreparedStatement p =
                                con.prepareStatement("update WQS_MISCELLANEOUS_MASTER set QTY_AVAILABLE=? where LAB_CODE=? and MISCELLANEOUS_CODE=?");
                            p.setInt(1, qaty);
                            p.setInt(2, labcode);
                            p.setString(3, itemcode);
                            p.executeUpdate();
                        }
                    } catch (Exception e) {
                        System.out.println("Err in insertion & updation:" +
                                           e.getMessage());
                    }
                    try {
                        System.out.println("insert");
                        PreparedStatement pm =
                            con.prepareStatement("insert into WQS_ITEM_ISSUE(LAB_CODE,MAJOR_CATEGORY_CODE,ITEM_CODE,DATE_OF_ISSUE,QTY_ISSUED,REMARKS,UPDATED_DATE,UPDATED_BY_USER_ID,UOM_CODE)values(?,?,?,?,?,?,?,?,?)");
                        pm.setInt(1, labcode);
                        pm.setString(2, catcode);
                        pm.setString(3, itemcode);
                        pm.setDate(4, rdate);
                        pm.setInt(5, qty);
                        pm.setString(6, remarks);
                        pm.setTimestamp(7, ts);
                        pm.setString(8, updatedby);
                        pm.setString(9, ucode);
                        pm.executeUpdate();
                        xml = xml + "<flag>Success</flag>";
                    } catch (Exception e) {
                        System.out.println("Err in insertion & updation:" +
                                           e.getMessage());
                    }
                } else {
                    xml = xml + "<flag>Failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("Err in insertion :" + e.getMessage());
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
        ResultSet rs = null;
        PreparedStatement ps = null;
        Calendar c1;
        String updatedby = null, ucode = null, ouom = null;
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

        System.out.println("begining");
        String lb1 = request.getParameter("lab");
        System.out.println("lab:" + lb1);
        String[] lb = request.getParameter("lab").split("--");
        System.out.println("lab:" + lb);
        int labcode = Integer.parseInt(lb[0]);
        System.out.println("labcode :" + labcode);
        String[] cat = request.getParameter("category").split("--");
        System.out.println("Lab : " + labcode + " Category : " + cat);
        String catcode = cat[0];
        String catdesc = cat[1];
        String[] item = request.getParameter("item").split("--");
        String itemcode = item[0];
        System.out.println(labcode + " " + catcode + " " + catdesc + " " +
                           itemcode);
        String bcode = request.getParameter("bcode");

        String[] od = request.getParameter("rdate").split("/");
        c1 =
  new GregorianCalendar(Integer.parseInt(od[2]), Integer.parseInt(od[1]) - 1,
                        Integer.parseInt(od[0]));
        java.util.Date d = c1.getTime();
        Date rdate = new Date(d.getTime());
        System.out.println(rdate);

        int qty = Integer.parseInt(request.getParameter("issued"));
        String uom = request.getParameter("uom");
        System.out.println("unit of measurement:" + uom);
        String remarks = request.getParameter("remarks");
        System.out.println("received:" + qty);
        try {
            System.out.println("selection");
            if (catdesc.equalsIgnoreCase("Chemical")) {
                System.out.println("chemical");
                try {
                    PreparedStatement pst =
                        con.prepareStatement("Select QTY_AVAILABLE from WQS_CHEMICAL_MASTER where LAB_CODE=? and CHEMICAL_CODE=? and BRAND_CODE=?");
                    pst.setInt(1, labcode);
                    pst.setString(2, itemcode);
                    pst.setString(3, bcode);
                    ResultSet rs1 = pst.executeQuery();
                    if (rs1.next()) {
                        avail =
Integer.parseInt(rs1.getString("QTY_AVAILABLE"));
                    }
                } catch (Exception e) {
                    System.out.println("Err in submit Chemical:" +
                                       e.getMessage());
                }
            } else if (catdesc.equalsIgnoreCase("Glassware")) {
                System.out.println("Glassware");
                try {
                    PreparedStatement pst =
                        con.prepareStatement("Select NO_AVAILABLE from WQS_GLASSWARE_MASTER where LAB_CODE=? and GLASSWARE_CODE=?");
                    pst.setInt(1, labcode);
                    pst.setString(2, itemcode);
                    ResultSet rs1 = pst.executeQuery();
                    if (rs1.next()) {
                        avail =
Integer.parseInt(rs1.getString("NO_AVAILABLE"));
                    }
                } catch (Exception e) {
                    System.out.println("Err in submit Glassware:" +
                                       e.getMessage());
                }
            } else {
                System.out.println("Miscellaneous");
                try {
                    PreparedStatement pst =
                        con.prepareStatement("Select QTY_AVAILABLE from WQS_MISCELLANEOUS_MASTER where LAB_CODE=? and MISCELLANEOUS_CODE=?");
                    pst.setInt(1, labcode);
                    pst.setString(2, itemcode);
                    ResultSet rs1 = pst.executeQuery();
                    if (rs1.next()) {
                        avail =
Integer.parseInt(rs1.getString("QTY_AVAILABLE"));
                    }
                } catch (Exception e) {
                    System.out.println("Err in submit Miscelaneous:" +
                                       e.getMessage());
                }
            }
            int qaty = avail - qty;
            System.out.println(qaty);
            if (avail >= qty) {
                System.out.println("greaterthen or equal");
                try {
                    PreparedStatement ps1 =
                        con.prepareStatement("select UOM_CODE from WQS_MST_UOM where UOM_DESC=?");
                    ps1.setString(1, uom);
                    rs = ps1.executeQuery();
                    while (rs.next()) {
                        ucode = rs.getString("UOM_CODE");
                    }
                } catch (Exception e) {
                    System.out.println("Err in UOM select:" + e.getMessage());
                }

                try {
                    System.out.println("updation");
                    if (catdesc.equalsIgnoreCase("Chemical")) {
                        PreparedStatement p =
                            con.prepareStatement("update WQS_CHEMICAL_MASTER set QTY_AVAILABLE=? where LAB_CODE=? and CHEMICAL_CODE=? and BRAND_CODE=?");
                        p.setInt(1, qaty);
                        p.setInt(2, labcode);
                        p.setString(3, itemcode);
                        p.setString(4, bcode);
                        p.executeUpdate();
                    } else if (catdesc.equalsIgnoreCase("Glassware")) {
                        PreparedStatement p =
                            con.prepareStatement("update WQS_GLASSWARE_MASTER set NO_AVAILABLE=? where LAB_CODE=? and GLASSWARE_CODE=?");
                        p.setInt(1, qaty);
                        p.setInt(2, labcode);
                        p.setString(3, itemcode);
                        p.executeUpdate();
                    } else {
                        PreparedStatement p =
                            con.prepareStatement("update WQS_MISCELLANEOUS_MASTER set QTY_AVAILABLE=? where LAB_CODE=? and MISCELLANEOUS_CODE=?");
                        p.setInt(1, qaty);
                        p.setInt(2, labcode);
                        p.setString(3, itemcode);
                        p.executeUpdate();
                    }
                } catch (Exception e) {
                    System.out.println("Err in insertion & updation:" +
                                       e.getMessage());
                }
                try {

                    System.out.println("insert");
                    PreparedStatement pm =
                        con.prepareStatement("insert into WQS_ITEM_ISSUE(LAB_CODE,MAJOR_CATEGORY_CODE,ITEM_CODE,DATE_OF_ISSUE,QTY_ISSUED,REMARKS,UPDATED_DATE,UPDATED_BY_USER_ID,UOM_CODE)values(?,?,?,?,?,?,?,?,?)");
                    pm.setInt(1, labcode);
                    pm.setString(2, catcode);
                    pm.setString(3, itemcode);
                    pm.setDate(4, rdate);
                    pm.setInt(5, qty);
                    pm.setString(6, remarks);
                    pm.setTimestamp(7, ts);
                    pm.setString(8, updatedby);
                    pm.setString(9, uom);
                    pm.executeUpdate();
                } catch (Exception e) {
                    System.out.println("Exception in connection:" + e);
                    con.rollback();
                    String msg = "<br><br>" + e;
                    sendMessage(response, msg, "ok");
                }
                String msg = "Issue Details Has been Successfully Inserted";
                msg = "<br><br><p><b>" + msg + "</b></p>";
                sendMessage(response, msg, "ok");
            } else {
                String msg =
                    "Out of stock value. Availble stock is:" + avail + " " +
                    uom;
                msg = "<br><br><p><b>" + msg + "</b></p>";
                sendMessage(response, msg, "cancel");
            }
        } catch (Exception e) {
            System.out.println("Err in insertion :" + e.getMessage());
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


