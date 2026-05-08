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

public class WQS_PurchaseOrder_Popup extends HttpServlet {
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
        String xml = null, catcd = null, itemcd = null;
        int lab = 0;
        Connection con = null;
        Statement stmt = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
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

        String cmd = request.getParameter("command");
        System.out.println(cmd);

        xml = "<response>";
        if (cmd.equalsIgnoreCase("changeCat")) {
            lab = Integer.parseInt(request.getParameter("lab"));
            System.out.println("lab===================>" + lab);
            String scode = request.getParameter("Supcode");
            System.out.println(scode);
            String catdesc = request.getParameter("CatDesc");
            xml = xml + "<command>changeCat</command>";
            if (catdesc.equalsIgnoreCase("Chemical")) {
                System.out.println("chemical");
                try {
                    System.out.println("before");
                    ps =
  con.prepareStatement("select * from(select LAB_CODE,SUPPLIER_CODE,MAJOR_CATEGORY_CODE,ITEM_CODE from WQS_MST_INV_SUP_ITEM)a inner join(select CHEMICAL_CODE,CHE_SPECIFICATION from WQS_MST_INV_CHEMICAL)b on a.ITEM_CODE=b.CHEMICAL_CODE inner join(select MAJOR_CATEGORY_CODE,MAJOR_CATEGORY_DESC from WQS_MST_INV_CATEGORY)c on a.MAJOR_CATEGORY_CODE=c.MAJOR_CATEGORY_CODE where c.MAJOR_CATEGORY_DESC=? and a.LAB_CODE=? and a.SUPPLIER_CODE=?");
                    System.out.println("after");
                    ps.setString(1, catdesc);
                    ps.setInt(2, lab);
                    ps.setString(3, scode);
                    rs = ps.executeQuery();
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
                    ps =
  con.prepareStatement("select * from(select LAB_CODE,SUPPLIER_CODE,MAJOR_CATEGORY_CODE,ITEM_CODE from WQS_MST_INV_SUP_ITEM)a inner join(select GLASSWARE_CODE,GLASSWARE_SPEC from WQS_MST_INV_GLASSWARE)b on a.ITEM_CODE=b.GLASSWARE_CODE inner join(select MAJOR_CATEGORY_CODE,MAJOR_CATEGORY_DESC from WQS_MST_INV_CATEGORY)c on a.MAJOR_CATEGORY_CODE=c.MAJOR_CATEGORY_CODE where c.MAJOR_CATEGORY_DESC=? and a.LAB_CODE=? and a.SUPPLIER_CODE=?");
                    ps.setString(1, catdesc);
                    ps.setInt(2, lab);
                    ps.setString(3, scode);
                    rs = ps.executeQuery();
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
                    ps =
  con.prepareStatement("select * from(select LAB_CODE,SUPPLIER_CODE,MAJOR_CATEGORY_CODE,ITEM_CODE from WQS_MST_INV_SUP_ITEM)a inner join(select INST_CATEGORY,INST_CATEGORY_SPEC from WQS_MST_INV_INSTRUMENT)b on a.ITEM_CODE=b.INST_CATEGORY inner join(select MAJOR_CATEGORY_CODE,MAJOR_CATEGORY_DESC from WQS_MST_INV_CATEGORY)c on a.MAJOR_CATEGORY_CODE=c.MAJOR_CATEGORY_CODE where c.MAJOR_CATEGORY_DESC=? and a.LAB_CODE=? and a.SUPPLIER_CODE=?");
                    ps.setString(1, catdesc);
                    ps.setInt(2, lab);
                    ps.setString(3, scode);
                    rs = ps.executeQuery();
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
                    ps =
  con.prepareStatement("select * from(select LAB_CODE,SUPPLIER_CODE,MAJOR_CATEGORY_CODE,ITEM_CODE from WQS_MST_INV_SUP_ITEM)a inner join(select MISCELLANEOUS_CODE,MISCELLANEOUS_SPEC from WQS_MST_INV_MISCELLANEOUS)b on a.ITEM_CODE=b.MISCELLANEOUS_CODE inner join(select MAJOR_CATEGORY_CODE,MAJOR_CATEGORY_DESC from WQS_MST_INV_CATEGORY)c on a.MAJOR_CATEGORY_CODE=c.MAJOR_CATEGORY_CODE where c.MAJOR_CATEGORY_DESC=? and a.LAB_CODE=? and a.SUPPLIER_CODE=?");
                    ps.setString(1, catdesc);
                    ps.setInt(2, lab);
                    ps.setString(3, scode);
                    rs = ps.executeQuery();
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
        } else if (cmd.equalsIgnoreCase("checkuom")) {
            String catdesc = request.getParameter("Category");
            String item = request.getParameter("Item");
            xml = xml + "<command>checkuom</command>";
            if (catdesc.equalsIgnoreCase("Chemical")) {
                System.out.println("chemical");
                try {
                    PreparedStatement pstmt =
                        con.prepareStatement("select * from(Select UOM_CODE,CHEMICAL_CODE from WQS_CHEMICAL_MASTER)a left outer join(select CHEMICAL_CODE,CHE_SPECIFICATION from WQS_MST_INV_CHEMICAL)b on a.CHEMICAL_CODE=b.CHEMICAL_CODE where b.CHE_SPECIFICATION=?");
                    pstmt.setString(1, item);
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        xml =
 xml + "<uom>" + rs.getString("UOM_CODE") + "</uom>";
                        System.out.println(rs.getString("UOM_CODE"));
                    }
                    xml = xml + "<flag>Success</flag>";
                } catch (Exception e) {
                    xml = xml + "<flag>failure</flag>";
                    System.out.println("Err1:" + e.getMessage());
                }
            } else if (catdesc.equalsIgnoreCase("Glassware")) {
                System.out.println("Glassware");
                try {
                    PreparedStatement pstmt =
                        con.prepareStatement("select * from(Select UOM_CODE,GLASSWARE_CODE from WQS_GLASSWARE_MASTER)a left outer join(select GLASSWARE_CODE,GLASSWARE_SPEC from WQS_MST_INV_GLASSWARE)b on a.GLASSWARE_CODE=b.GLASSWARE_CODE where b.GLASSWARE_SPEC=?");
                    pstmt.setString(1, item);
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        xml =
 xml + "<uom>" + rs.getString("UOM_CODE") + "</uom>";
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
  stmt.executeQuery("select * from WQS_MST_UOM where UOM_DESC='Numbers' or UOM_DESC='numbers'");
                    if (rs.next()) {
                        xml =
 xml + "<uom>" + rs.getString("UOM_CODE") + "</uom>";
                    }
                    xml = xml + "<flag>Success</flag>";
                } catch (Exception e) {
                    System.out.println("Err3:" + e.getMessage());
                    xml = xml + "<flag>failure</flag>";
                }
            } else {
                System.out.println("Miscellaneous");
                try {
                    PreparedStatement pstmt =
                        con.prepareStatement("select * from(Select UOM_CODE,MISCELLANEOUS_CODE from WQS_MISCELLANEOUS_MASTER)a left outer join(select MISCELLANEOUS_CODE,MISCELLANEOUS_SPEC from WQS_MST_INV_MISCELLANEOUS)b on a.MISCELLANEOUS_CODE=b.MISCELLANEOUS_CODE where b.MISCELLANEOUS_SPEC=?");
                    pstmt.setString(1, item);
                    rs = pstmt.executeQuery();
                    if (rs.next()) {
                        xml =
 xml + "<uom>" + rs.getString("UOM_CODE") + "</uom>";
                    }
                    xml = xml + "<flag>Success</flag>";
                } catch (Exception e) {
                    System.out.println("Err4:" + e.getMessage());
                    xml = xml + "<flag>failure</flag>";
                }
            }

        } else {
            int lcode = Integer.parseInt(request.getParameter("LabCode"));
            String orderno = request.getParameter("OrderNo");
            String catcode = request.getParameter("CatCode");
            String itemcode = request.getParameter("ItemCode");
            xml = xml + "<command>checkAvail</command>";
            try {
                PreparedStatement pstmt =
                    con.prepareStatement("select LAB_CODE,PURCHASE_ORDER_NO,MAJOR_CATEGORY_CODE,ITEM_CODE from WQS_ITEM_PUR_ORDER_DETAILS where LAB_CODE=? and PURCHASE_ORDER_NO=? and MAJOR_CATEGORY_CODE=? and ITEM_CODE=?");
                pstmt.setInt(1, lcode);
                pstmt.setString(2, orderno);
                pstmt.setString(3, catcode);
                pstmt.setString(4, itemcode);

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


