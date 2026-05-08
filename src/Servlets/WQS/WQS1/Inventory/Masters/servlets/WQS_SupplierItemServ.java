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

public class WQS_SupplierItemServ extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=UTF-8";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        String xml = null, catcd = null, itemcd = null;
        int c = 0, lcode = 0;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
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


        String cmd = request.getParameter("command");
        String supcode = request.getParameter("urlSupCode");
        String supname = request.getParameter("urlSupName");
        String catcode = request.getParameter("urlCatCode");
        String catdesc = request.getParameter("urlCatDesc");
        String itemcode = request.getParameter("urlItemCode");
        String itemdesc = request.getParameter("urlItemDesc");
        System.out.println(cmd);
        System.out.println(supcode);
        System.out.println(supname);
        System.out.println(catcode);
        System.out.println(itemcode);
        System.out.println(itemdesc);
        if (cmd.equalsIgnoreCase("changeCat")) {
            xml = "<response><command>changeCat</command>";
            if (catdesc.equalsIgnoreCase("Chemical")) {
                System.out.println("chemical");
                try {
                    stmt = con.createStatement();
                    rs =
  stmt.executeQuery("Select CHEMICAL_CODE,CHE_SPECIFICATION from WQS_MST_INV_CHEMICAL");
                    while (rs.next()) {
                        xml = xml + "<count>";
                        xml =
 xml + "<ItemCode>" + rs.getString("CHEMICAL_CODE") + "</ItemCode>";
                        xml =
 xml + "<ItemDesc>" + rs.getString("CHE_SPECIFICATION") + "</ItemDesc>";
                        xml = xml + "</count>";
                    }
                    xml = xml + "<flag>Success</flag>";
                } catch (Exception e) {
                    System.out.println("Err1:" + e.getMessage());
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (catdesc.equalsIgnoreCase("Glassware")) {
                System.out.println("Glassware");
                try {
                    stmt = con.createStatement();
                    rs =
  stmt.executeQuery("Select GLASSWARE_CODE,GLASSWARE_SPEC from WQS_MST_INV_GLASSWARE");
                    System.out.println("outside while");
                    while (rs.next()) {
                        xml = xml + "<count>";
                        xml =
 xml + "<ItemCode>" + rs.getString("GLASSWARE_CODE") + "</ItemCode>";
                        xml =
 xml + "<ItemDesc>" + rs.getString("GLASSWARE_SPEC") + "</ItemDesc>";
                        xml = xml + "</count>";
                    }
                    xml = xml + "<flag>Success</flag>";
                } catch (Exception e) {
                    System.out.println("Err2:" + e.getMessage());
                    xml = xml + "<flag>failure</flag>";
                }
            } else if (catdesc.equalsIgnoreCase("Instrument")) {
                System.out.println("Instrument");
                try {
                    stmt = con.createStatement();
                    rs =
  stmt.executeQuery("Select INST_CATEGORY,INST_CATEGORY_SPEC from WQS_MST_INV_INSTRUMENT");
                    while (rs.next()) {
                        xml = xml + "<count>";
                        xml =
 xml + "<ItemCode>" + rs.getString("INST_CATEGORY") + "</ItemCode>";
                        xml =
 xml + "<ItemDesc>" + rs.getString("INST_CATEGORY_SPEC") + "</ItemDesc>";
                        xml = xml + "</count>";
                    }
                    xml = xml + "<flag>Success</flag>";
                } catch (Exception e) {
                    System.out.println("Err3:" + e.getMessage());
                    xml = xml + "<flag>failure</flag>";
                }
            } else {
                System.out.println("Miscellaneous");
                try {
                    stmt = con.createStatement();
                    rs =
  stmt.executeQuery("Select MISCELLANEOUS_CODE,MISCELLANEOUS_SPEC from WQS_MST_INV_MISCELLANEOUS");
                    while (rs.next()) {
                        xml = xml + "<count>";
                        xml =
 xml + "<ItemCode>" + rs.getString("MISCELLANEOUS_CODE") + "</ItemCode>";
                        xml =
 xml + "<ItemDesc>" + rs.getString("MISCELLANEOUS_SPEC") + "</ItemDesc>";
                        xml = xml + "</count>";
                    }
                    xml = xml + "<flag>Success</flag>";
                } catch (Exception e) {
                    System.out.println("Err4:" + e.getMessage());
                    xml = xml + "<flag>failure</flag>";
                }
            }
        } else if (cmd.equalsIgnoreCase("checkAvail")) {
            lcode = Integer.parseInt(request.getParameter("lab"));
            xml = "<response><command>checkAvail</command>";
            {
                try {
                    PreparedStatement pstmt =
                        con.prepareStatement("select * from WQS_MST_INV_SUP_ITEM where LAB_CODE=? and SUPPLIER_CODE=? and MAJOR_CATEGORY_CODE=? and ITEM_CODE=?");
                    pstmt.setInt(1, lcode);
                    pstmt.setString(2, supcode);
                    pstmt.setString(3, catcode);
                    pstmt.setString(4, itemcode);
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        xml = xml + "<flag>failure</flag>";
                    } else {
                        xml = xml + "<flag>Success</flag>";
                    }
                } catch (Exception e) {
                    System.out.println("Err19:" + e.getMessage());
                }
            }

        } else if (cmd.equalsIgnoreCase("Add")) {
            System.out.println("add");
            session = request.getSession(false);
            updatedby = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            ts = new Timestamp(l);
            System.out.println(updatedby);
            System.out.println(ts);
            lcode = Integer.parseInt(request.getParameter("lab"));
            xml = "<response><command>Add</command>";
            try {
                PreparedStatement pstmt =
                    con.prepareStatement("insert into WQS_MST_INV_SUP_ITEM(LAB_CODE,SUPPLIER_CODE,MAJOR_CATEGORY_CODE,ITEM_CODE,UPDATED_BY_USER_ID,UPDATED_DATE)values(?,?,?,?,?,?)");
                pstmt.setInt(1, lcode);
                pstmt.setString(2, supcode);
                pstmt.setString(3, catcode);
                pstmt.setString(4, itemcode);
                pstmt.setString(5, updatedby);
                pstmt.setTimestamp(6, ts);
                pstmt.executeUpdate();
                xml = xml + "<LabCode>" + lcode + "</LabCode>";
                xml = xml + "<SupplierCode>" + supcode + "</SupplierCode>";
                xml = xml + "<SupplierName>" + supname + "</SupplierName>";
                xml = xml + "<CatCode>" + catcode + "</CatCode>";
                xml = xml + "<CatDesc>" + catdesc + "</CatDesc>";
                xml = xml + "<ItemCode>" + itemcode + "</ItemCode>";
                xml = xml + "<ItemDesc>" + itemdesc + "</ItemDesc>";
                xml = xml + "<flag>Success</flag>";
                pstmt.close();
            } catch (SQLException e) {
                System.out.println("Err10:" + e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }
        } else if (cmd.equalsIgnoreCase("Get")) {
            lcode = Integer.parseInt(request.getParameter("lab"));
            xml = "<response><command>Get</command>";
            try {
                stmt = con.createStatement();
                rs =
  stmt.executeQuery("select * from(select * from WQS_MST_INV_SUP_ITEM where LAB_CODE=" +
                    lcode +
                    ")a inner join(select MAJOR_CATEGORY_CODE,MAJOR_CATEGORY_DESC from WQS_MST_INV_CATEGORY)b on a.MAJOR_CATEGORY_CODE=b.MAJOR_CATEGORY_CODE left outer join(select LAB_CODE,SUPPLIER_CODE,SUPPLIER_NAME from WQS_MST_INV_SUPPLIER)c on a.SUPPLIER_CODE=c.SUPPLIER_CODE and a.LAB_CODE=c.LAB_CODE");
                while (rs.next()) {
                    xml = xml + "<getcount>";
                    xml =
 xml + "<LabCode>" + rs.getString("LAB_CODE") + "</LabCode>";
                    xml =
 xml + "<SupplierCode>" + rs.getString("SUPPLIER_CODE") + "</SupplierCode>";
                    xml =
 xml + "<SupplierName>" + rs.getString("SUPPLIER_NAME") + "</SupplierName>";
                    catcd = rs.getString("MAJOR_CATEGORY_DESC");
                    xml = xml + "<CatDesc>" + catcd + "</CatDesc>";
                    itemcd = rs.getString("ITEM_CODE");
                    xml = xml + "<ItemCode>" + itemcd + "</ItemCode>";
                    c = c + 1;
                    if (catcd.equalsIgnoreCase("Chemical")) {
                        try {
                            PreparedStatement ps1 =
                                con.prepareStatement("select CHE_SPECIFICATION from WQS_MST_INV_CHEMICAL where CHEMICAL_CODE=?");
                            ps1.setString(1, itemcd);
                            ResultSet rs1 = ps1.executeQuery();
                            if (rs1.next()) {
                                itemdesc = rs1.getString("CHE_SPECIFICATION");
                                xml =
 xml + "<ItemDesc>" + itemdesc + "</ItemDesc>";
                            }
                        } catch (Exception e) {
                            System.out.println("Err13:" + e.getMessage());
                        }
                    } else if (catcd.equalsIgnoreCase("Glassware")) {
                        try {
                            PreparedStatement ps1 =
                                con.prepareStatement("select GLASSWARE_SPEC from WQS_MST_INV_GLASSWARE where GLASSWARE_CODE=?");
                            ps1.setString(1, itemcd);
                            ResultSet rs1 = ps1.executeQuery();
                            if (rs1.next()) {
                                itemdesc = rs1.getString("GLASSWARE_SPEC");
                                xml =
 xml + "<ItemDesc>" + itemdesc + "</ItemDesc>";
                            }
                        } catch (Exception e) {
                            System.out.println("Err14:" + e.getMessage());
                        }
                    } else if (catcd.equalsIgnoreCase("Instrument")) {
                        try {
                            PreparedStatement ps1 =
                                con.prepareStatement("select INST_CATEGORY_SPEC from WQS_MST_INV_INSTRUMENT where INST_CATEGORY=?");
                            ps1.setString(1, itemcd);
                            ResultSet rs1 = ps1.executeQuery();
                            if (rs1.next()) {
                                itemdesc = rs1.getString("INST_CATEGORY_SPEC");
                                xml =
 xml + "<ItemDesc>" + itemdesc + "</ItemDesc>";
                            }
                        } catch (Exception e) {
                            System.out.println("Err15:" + e.getMessage());
                        }
                    } else //if(catcd.equalsIgnoreCase("Miscellaneous"))
                    {
                        try {
                            PreparedStatement ps1 =
                                con.prepareStatement("select MISCELLANEOUS_SPEC from WQS_MST_INV_MISCELLANEOUS where MISCELLANEOUS_CODE=?");
                            ps1.setString(1, itemcd);
                            ResultSet rs1 = ps1.executeQuery();
                            if (rs1.next()) {
                                itemdesc = rs1.getString("MISCELLANEOUS_SPEC");
                                xml =
 xml + "<ItemDesc>" + itemdesc + "</ItemDesc>";
                            }
                        } catch (Exception e) {
                            System.out.println("Err16:" + e.getMessage());
                        }
                    }
                    System.out.println(catcd + " " + itemcd + "   " +
                                       itemdesc);
                    xml = xml + "</getcount>";
                }
                xml = xml + "<flag>Success</flag>";
            } catch (Exception e) {
                System.out.println("Err17:" + e.getMessage());
            }
        } else {
            lcode = Integer.parseInt(request.getParameter("lab"));
            System.out.println("delete");
            xml = "<response><command>Delete</command>";
            try {
                PreparedStatement ps1 =
                    con.prepareStatement("select * from(select LAB_CODE,PURCHASE_ORDER_NO,SUPPLIER_CODE from WQS_ITEM_PUR_ORDER_MASTER)a inner join (select LAB_CODE,PURCHASE_ORDER_NO,MAJOR_CATEGORY_CODE,ITEM_CODE,CURRENT_STATUS from WQS_ITEM_PUR_ORDER_DETAILS)b on a.LAB_CODE=b.LAB_CODE and a.PURCHASE_ORDER_NO=b.PURCHASE_ORDER_NO where a.LAB_CODE=? and a.SUPPLIER_CODE=? and b.MAJOR_CATEGORY_CODE=? and b.ITEM_CODE=? and b.CURRENT_STATUS=?");
                ps1.setInt(1, lcode);
                ps1.setString(2, supcode);
                ps1.setString(3, catcode);
                ps1.setString(4, itemcode);
                ps1.setString(5, "A");
                rs = ps1.executeQuery();
                if (rs.next()) {
                    xml = xml + "<flag>FoundData</flag>";
                } else {
                    ps =
  con.prepareStatement("delete from WQS_MST_INV_SUP_ITEM where LAB_CODE=? and SUPPLIER_CODE=? and MAJOR_CATEGORY_CODE=? and ITEM_CODE=?");
                    ps.setInt(1, lcode);
                    ps.setString(2, supcode);
                    ps.setString(3, catcode);
                    ps.setString(4, itemcode);
                    ps.executeUpdate();
                    xml = xml + "<LabCode>" + lcode + "</LabCode>";
                    xml = xml + "<SupplierCode>" + supcode + "</SupplierCode>";
                    xml = xml + "<SupplierName>" + supname + "</SupplierName>";
                    xml = xml + "<CatCode>" + catcode + "</CatCode>";
                    xml = xml + "<CatDesc>" + catcode + "</CatDesc>";
                    xml = xml + "<ItemCode>" + itemcode + "</ItemCode>";
                    xml = xml + "<ItemDesc>" + itemdesc + "</ItemDesc>";
                    xml = xml + "<flag>Success</flag>";
                }
            } catch (Exception e) {
                System.out.println("Err18:" + e.getMessage());
                xml = xml + "<flag>err in getting value</flag>";
            }
        }
        xml = xml + "</response>";
        System.out.println(xml);
        out.println(xml);
        out.close();
    }
}
