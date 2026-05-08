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

public class WQS_LabTransfer_InServ extends HttpServlet {
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
        String xml = null, ucode = null, fdat = null;
        Calendar c;
        int qty = 0, c1 = 0;
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
            int tlabcode = Integer.parseInt(request.getParameter("tlab"));
            int flabcode = Integer.parseInt(request.getParameter("flab"));
            System.out.println(tlabcode + " " + flabcode);
            xml = xml + "<command>changeCat</command>";
            if (catdesc.equalsIgnoreCase("Chemical")) {
                System.out.println("chemical");
                try {
                    ps =
  con.prepareStatement("select * from(Select distinct ITEM_CODE from WQS_LAB_TRANSFER where TO_LAB_CODE=? and FROM_LAB_CODE=? and MAJOR_CATEGORY_CODE=? and STATUS=?)a inner join(select CHEMICAL_CODE,CHE_SPECIFICATION  from WQS_MST_INV_CHEMICAL)b on a.ITEM_CODE=b.CHEMICAL_CODE");
                    ps.setInt(1, tlabcode);
                    ps.setInt(2, flabcode);
                    ps.setString(3, "CHE");
                    ps.setString(4, "Pending");
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        xml = xml + "<count>";
                        xml =
 xml + "<ItemCode>" + rs.getString("ITEM_CODE") + "</ItemCode>";
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
  con.prepareStatement("select * from(Select distinct ITEM_CODE from WQS_LAB_TRANSFER where TO_LAB_CODE=? and FROM_LAB_CODE=? and MAJOR_CATEGORY_CODE=? and STATUS=?)a inner join(select GLASSWARE_CODE,GLASSWARE_SPEC from WQS_MST_INV_GLASSWARE)b on a.ITEM_CODE=b.GLASSWARE_CODE");
                    ps.setInt(1, tlabcode);
                    ps.setInt(2, flabcode);
                    ps.setString(3, "GLA");
                    ps.setString(4, "Pending");
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        xml = xml + "<count>";
                        xml =
 xml + "<ItemCode>" + rs.getString("ITEM_CODE") + "</ItemCode>";
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
  con.prepareStatement("select * from(Select distinct ITEM_CODE from WQS_LAB_TRANSFER where TO_LAB_CODE=? and FROM_LAB_CODE=? and MAJOR_CATEGORY_CODE=? and STATUS=?)a inner join(select MISCELLANEOUS_CODE,MISCELLANEOUS_SPEC from WQS_MST_INV_MISCELLANEOUS)b on a.ITEM_CODE=b.MISCELLANEOUS_CODE");
                    ps.setInt(1, tlabcode);
                    ps.setInt(2, flabcode);
                    ps.setString(3, "MIS");
                    ps.setString(4, "Pending");
                    rs = ps.executeQuery();
                    while (rs.next()) {
                        xml = xml + "<count>";
                        xml =
 xml + "<ItemCode>" + rs.getString("ITEM_CODE") + "</ItemCode>";
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
        } else if (cmd.equalsIgnoreCase("changeLab")) {
            int tlab = Integer.parseInt(request.getParameter("tlab"));
            int flab = Integer.parseInt(request.getParameter("flab"));
            xml = xml + "<command>changeLab</command>";
            try {
                PreparedStatement pst =
                    con.prepareStatement("select * from(Select distinct MAJOR_CATEGORY_CODE from WQS_LAB_TRANSFER where TO_LAB_CODE=? and FROM_LAB_CODE=? and STATUS=?)a left outer join(select MAJOR_CATEGORY_CODE,MAJOR_CATEGORY_DESC from WQS_MST_INV_CATEGORY)b on a.MAJOR_CATEGORY_CODE=b.MAJOR_CATEGORY_CODE");
                pst.setInt(1, tlab);
                pst.setInt(2, flab);
                pst.setString(3, "Pending");
                ResultSet rs1 = pst.executeQuery();
                while (rs1.next()) {
                    xml = xml + "<count>";
                    xml =
 xml + "<CatCode>" + rs1.getString("MAJOR_CATEGORY_CODE") + "</CatCode>";
                    xml =
 xml + "<CatDesc>" + rs1.getString("MAJOR_CATEGORY_DESC") + "</CatDesc>";
                    xml = xml + "</count>";
                }
                xml = xml + "<flag>Success</flag>";
            } catch (Exception e) {
                System.out.println("Err in changeLab:" + e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }
        } else if (cmd.equalsIgnoreCase("changeItem")) {
            int tlabcode = Integer.parseInt(request.getParameter("tlab"));
            int flabcode = Integer.parseInt(request.getParameter("flab"));
            System.out.println("Lab Code is:" + tlabcode + " " + flabcode);
            String catcode = request.getParameter("CatCode");
            String catdesc = request.getParameter("CatDesc");
            String itemcode = request.getParameter("ItemCode");
            System.out.println(catcode + " " + catdesc + " " + itemcode);
            xml = xml + "<command>changeItem</command>";
            try {
                System.out.println("inside changeItem try");
                PreparedStatement pst =
                    con.prepareStatement("select qty_issued,date_of_issue,uom_code from wqs_lab_transfer where to_lab_code=? and from_lab_code=? and major_category_code=? and item_code=? and status=?");
                System.out.println("tlabcode=========>" + tlabcode);
                pst.setInt(1, tlabcode);
                System.out.println("flabcode=========>" + flabcode);
                pst.setInt(2, flabcode);
                System.out.println("catcode=========>" + catcode);
                pst.setString(3, catcode);
                System.out.println("itemcode=========>" + itemcode);
                pst.setString(4, itemcode);
                System.out.println("Pending=========>Pending");
                pst.setString(5, "Pending");
                ResultSet rs1 = pst.executeQuery();
                if (rs1.next()) {
                    qty = Integer.parseInt(rs1.getString("qty_issued"));
                    ucode = rs1.getString("uom_code");
                    Date fdate = rs1.getDate("date_of_issue");
                    Format formatter;
                    formatter = new SimpleDateFormat("dd/MM/yyyy");
                    fdat = formatter.format(fdate);
                }
                System.out.println("Available " + catdesc + ":" + itemcode +
                                   " stock is : " + qty);
                xml = xml + "<flag>Success</flag>";
                xml = xml + "<stock>" + qty + "</stock>";
                xml = xml + "<sdate>" + fdat + "</sdate>";
                xml = xml + "<uom>" + ucode + "</uom>";
            } catch (Exception e) {
                System.out.println("Err in submit Chemical:" + e.getMessage());
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
        Calendar c1, c2;
        String updatedby = null;
        Timestamp ts = null;
        int qty = 0, avail = 0, qaty = 0;
        String flag = null;
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
        String[] lb1 = request.getParameter("flab").split("--");
        int labcode1 = Integer.parseInt(lb1[0]);
        String[] lb2 = request.getParameter("tlab").split("--");
        int labcode2 = Integer.parseInt(lb2[0]);
        String[] cat = request.getParameter("category").split("--");
        String catcode = cat[0];
        String catdesc = cat[1];
        String[] item = request.getParameter("item").split("--");
        String itemcode = item[0];
        String itemdesc = item[1];
        System.out.println(labcode1 + " " + labcode2 + " " + catcode + " " +
                           catdesc + " " + itemcode);

        String[] od = request.getParameter("rdate").split("/");
        c1 =
  new GregorianCalendar(Integer.parseInt(od[2]), Integer.parseInt(od[1]) - 1,
                        Integer.parseInt(od[0]));
        java.util.Date d = c1.getTime();
        Date rdate = new Date(d.getTime());
        System.out.println(rdate);

        String[] odt = request.getParameter("adate").split("/");
        c2 =
  new GregorianCalendar(Integer.parseInt(odt[2]), Integer.parseInt(odt[1]) - 1,
                        Integer.parseInt(odt[0]));
        java.util.Date d2 = c2.getTime();
        Date adate = new Date(d2.getTime());
        System.out.println(adate);

        String uom = request.getParameter("uom");
        System.out.println("unit of measurement:" + uom);
        qty = Integer.parseInt(request.getParameter("qty"));
        try {
            con.setAutoCommit(false);
            try {
                System.out.println("insert");
                ps =
  con.prepareStatement("update WQS_LAB_TRANSFER set QTY_ISSUED=?,DATE_OF_RECEIPT=?,STATUS=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? where FROM_LAB_CODE=? and TO_LAB_CODE=? and MAJOR_CATEGORY_CODE=? and ITEM_CODE=? and DATE_OF_ISSUE=?");
                ps.setInt(1, qty);
                ps.setDate(2, adate);
                ps.setString(3, "Accept");
                ps.setTimestamp(4, ts);
                ps.setString(5, updatedby);
                ps.setInt(6, labcode1);
                System.out.println("flabcode=========>" + labcode1);
                ps.setInt(7, labcode2);
                System.out.println("labcode2=========>" + labcode2);
                ps.setString(8, catcode);
                System.out.println("itemcode=========>" + itemcode);
                ps.setString(9, itemcode);
                ps.setDate(10, rdate);
                int i = ps.executeUpdate();
                if (i > 0)
                    flag = "success";
                else
                    flag = "failure";
            } catch (Exception e) {
                System.out.println("Err in update===>" + e.getMessage());
            }
            if (flag.equalsIgnoreCase("success")) {
                try {
                    System.out.println("updation");
                    if (catdesc.equalsIgnoreCase("Chemical")) {
                        PreparedStatement p =
                            con.prepareStatement("select QTY_AVAILABLE from WQS_CHEMICAL_MASTER where LAB_CODE=? and CHEMICAL_CODE=? and BRAND_CODE=?");
                        p.setInt(1, labcode1);
                        p.setString(2, itemcode);
                        p.setString(3, "GR");
                        rs = p.executeQuery();
                        while (rs.next()) {
                            avail =
Integer.parseInt(rs.getString("QTY_AVAILABLE"));
                        }
                    } else if (catdesc.equalsIgnoreCase("Glassware")) {
                        PreparedStatement p =
                            con.prepareStatement("select NO_AVAILABLE from WQS_GLASSWARE_MASTER where LAB_CODE=? and GLASSWARE_CODE=?");
                        p.setInt(1, labcode1);
                        p.setString(2, itemcode);
                        rs = p.executeQuery();
                        while (rs.next()) {
                            avail =
Integer.parseInt(rs.getString("NO_AVAILABLE"));
                        }
                    } else {
                        PreparedStatement p =
                            con.prepareStatement("select QTY_AVAILABLE from WQS_MISCELLANEOUS_MASTER where LAB_CODE=? and MISCELLANEOUS_CODE=?");
                        p.setInt(1, labcode1);
                        p.setString(2, itemcode);
                        rs = p.executeQuery();
                        while (rs.next()) {
                            avail =
Integer.parseInt(rs.getString("QTY_AVAILABLE"));
                        }
                    }
                } catch (Exception e) {
                    System.out.println("Err in insertion & updation:" +
                                       e.getMessage());
                }
                qaty = avail - qty;
                System.out.println("Quantity:=================>" + qaty);
                try {
                    System.out.println("updation");
                    if (catdesc.equalsIgnoreCase("Chemical")) {
                        PreparedStatement p =
                            con.prepareStatement("update WQS_CHEMICAL_MASTER set QTY_AVAILABLE=? where LAB_CODE=? and CHEMICAL_CODE=? and BRAND_CODE=?");
                        p.setInt(1, qaty);
                        p.setInt(2, labcode1);
                        p.setString(3, itemcode);
                        p.setString(4, "GR");
                        p.executeUpdate();
                        // con.commit();
                    } else if (catdesc.equalsIgnoreCase("Glassware")) {
                        PreparedStatement p =
                            con.prepareStatement("update WQS_GLASSWARE_MASTER set NO_AVAILABLE=? where LAB_CODE=? and GLASSWARE_CODE=?");
                        p.setInt(1, qaty);
                        p.setInt(2, labcode1);
                        p.setString(3, itemcode);
                        p.executeUpdate();
                        // con.commit();
                    } else {
                        PreparedStatement p =
                            con.prepareStatement("update WQS_MISCELLANEOUS_MASTER set QTY_AVAILABLE=? where LAB_CODE=? and MISCELLANEOUS_CODE=?");
                        p.setInt(1, qaty);
                        p.setInt(2, labcode1);
                        p.setString(3, itemcode);
                        p.executeUpdate();
                        // con.commit();
                    }
                    con.commit();
                    con.setAutoCommit(true);
                    String msg = "Lab transfer has been Accepted successfully";
                    msg = msg + "<br><br>";
                    sendMessage(response, msg, "ok");
                } catch (Exception e) {
                    System.out.println("Err in insertion & updation:" +
                                       e.getMessage());
                    sendMessage(response,
                                "Failed to Accept values due to unknown reason",
                                "back");
                    con.rollback();
                    con.setAutoCommit(true);
                }
            }
            /*
                            try
                            {

                                System.out.println("insert");
                                PreparedStatement pm=con.prepareStatement("insert into WQS_LAB_TRANSFER(FROM_LAB_CODE,TO_LAB_CODE,MAJOR_CATEGORY_CODE,ITEM_CODE,DATE_OF_ISSUE,QTY_ISSUED,REMARKS,UPDATED_DATE,UPDATED_BY_USER_ID,UOM_CODE)values(?,?,?,?,?,?,?,?,?,?)");
                                pm.setInt(1,labcode1);
                                pm.setInt(2,labcode2);
                                pm.setString(3,catcode);
                                pm.setString(4,itemcode);
                                pm.setDate(5,rdate);
                                pm.setInt(6,qty);
                                pm.setString(7,remarks);
                                pm.setTimestamp(8,ts);
                                pm.setString(9,updatedby);
                                pm.setString(10,uom);
                                int i=pm.executeUpdate();
                                pm.close();
                                if(i>=1)
                                {
                                        String query="select LAB_STOCK_TRANSFER_ID from WQS_LAB_TRANSFER where UPDATED_DATE=?";
                                        try
                                        {
                                          pm=con.prepareStatement(query);
                                          pm.setTimestamp(1,ts);
                                          ResultSet rss=pm.executeQuery();
                                          if(rss.next())
                                          {
                                            transfer_id=rss.getInt("LAB_STOCK_TRANSFER_ID");
                                          }
                                          rss.close();
                                          pm.close();
                                        }
                                        catch(Exception e)
                                        {
                                        }
                                      try
                                        {
                                            System.out.println("updation");
                                            if(catdesc.equalsIgnoreCase("Chemical"))
                                            {
                                                    PreparedStatement pst=con.prepareStatement("Select QTY_AVAILABLE from WQS_CHEMICAL_MASTER where LAB_CODE=? and CHEMICAL_CODE=?");
                                                    pst.setInt(1,labcode2);
                                                    pst.setString(2,itemcode);
                                                    ResultSet rs1=pst.executeQuery();
                                                    if(rs1.next())
                                                    {
                                                        avail1=Integer.parseInt(rs1.getString("QTY_AVAILABLE"));
                                                    }
                                                    else
                                                    {
                                                        System.out.println("no entry found");
                                                    }
                                                    pst.close();
                                                    qaty=avail1+qty;
                                                    PreparedStatement p=con.prepareStatement("update WQS_CHEMICAL_MASTER set QTY_AVAILABLE=? where LAB_CODE=? and CHEMICAL_CODE=?");
                                                    p.setInt(1,qaty);
                                                    p.setInt(2,labcode2);
                                                    p.setString(3,itemcode);
                                                    p.executeUpdate();

                                            }
                                            else if(catdesc.equalsIgnoreCase("Glassware"))
                                            {

                                                PreparedStatement pst=con.prepareStatement("Select NO_AVAILABLE from WQS_GLASSWARE_MASTER where LAB_CODE=? and GLASSWARE_CODE=?");
                                                pst.setInt(1,labcode2);
                                                pst.setString(2,itemcode);
                                                ResultSet rs1=pst.executeQuery();
                                                if(rs1.next())
                                                {
                                                    avail1=Integer.parseInt(rs1.getString("NO_AVAILABLE"));
                                                }
                                                else
                                                {
                                                    System.out.println("no entry found");
                                                }
                                                pst.close();
                                                qaty=avail1+qty;
                                                PreparedStatement p=con.prepareStatement("update WQS_GLASSWARE_MASTER set NO_AVAILABLE=? where LAB_CODE=? and GLASSWARE_CODE=?");
                                                p.setInt(1,qaty);
                                                p.setInt(2,labcode2);
                                                p.setString(3,itemcode);
                                                p.executeUpdate();
                                            }
                                            else
                                            {
                                                PreparedStatement pst=con.prepareStatement("Select QTY_AVAILABLE from WQS_MISCELLANEOUS_MASTER where LAB_CODE=? and MISCELLANEOUS_CODE=?");
                                                pst.setInt(1,labcode2);
                                                pst.setString(2,itemcode);
                                                ResultSet rs1=pst.executeQuery();
                                                if(rs1.next())
                                                {
                                                    avail1=Integer.parseInt(rs1.getString("QTY_AVAILABLE"));
                                                }
                                                else
                                                {
                                                    System.out.println("no entry found");
                                                }
                                                pst.close();
                                                qaty=avail1+qty;
                                                PreparedStatement p=con.prepareStatement("update WQS_MISCELLANEOUS_MASTER set QTY_AVAILABLE=? where LAB_CODE=? and MISCELLANEOUS_CODE=?");
                                                p.setInt(1,qaty);
                                                p.setInt(2,labcode2);
                                                p.setString(3,itemcode);
                                                p.executeUpdate();
                                            }
                                        }
                                        catch(Exception e)
                                        {
                                            System.out.println("Err in lab  updation:"+e.getMessage());
                                        }

                                    con.commit();
                                    con.setAutoCommit(true);
                                    String msg="Lab transfer has been created successfully";
                                    msg=msg+"<br><br>";
                                    sendMessage(response,msg,"ok");
                                }
                                else
                                {
                                    // record not inserted
                                    sendMessage(response,"Failed to insert values due to unknown reason","back");
                                    con.rollback();
                                    con.setAutoCommit(true);
                                    return;
                                }
                            }
                            catch(Exception e)
                            {
                              System.out.println("Exception in connection:"+e);
                              con.rollback();
                              String msg="<br><br>"+e;
                              sendMessage(response,msg,"ok");
                            }*/
        }

        catch (Exception e) {
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
