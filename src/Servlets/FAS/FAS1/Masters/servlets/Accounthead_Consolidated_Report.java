package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Statement;
import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Accounthead_Consolidated_Report extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        ResultSet rss = null;
        ResultSet res = null;
        PreparedStatement pss = null;
        ResultSet rs1 = null;
        PreparedStatement ps1 = null;


        try {

            ResourceBundle rb =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rb.getString("Config.DSN");
            String strhostname = rb.getString("Config.HOST_NAME");
            String strportno = rb.getString("Config.PORT_NUMBER");
            String strsid = rb.getString("Config.SID");
            String strdbusername = rb.getString("Config.USER_NAME");
            String strdbpassword = rb.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection...." + e);
        }

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
        String userid = (String)session.getAttribute("UserId");
        System.out.println("User Id is:" + userid);
        response.setContentType(CONTENT_TYPE);
        String xml = "";
        String strCommand = "";
        String txtaccountheadcode = "";
        String txtaccountheadname = "";
        String txtSectionId = "";
        String txtGroupId = "";
        String officeid = "";
        //String txtGroup="";
        int accountheadcode = 0, offid = 0, sectid = 0, groupid = 0;
        txtaccountheadcode = request.getParameter("txtaccountheadcode");
        txtSectionId = request.getParameter("txtSectionId");
        txtGroupId = request.getParameter("txtGroupId");
        officeid = request.getParameter("officeid");
        /*  txtGroup=request.getParameter("txtGroup");
        System.out.println("txtGroup"+txtGroup);*/
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        try {

            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand + txtSectionId);

        } catch (Exception ae) {
            System.out.println("first exception...." + ae);
        }


        try {
            sectid = Integer.parseInt(txtSectionId);
            System.out.println("sectid" + sectid);

        } catch (Exception e) {
            System.out.println("Exception in sectid:" + e);
            sectid = 0;
        }

        try {
            groupid = Integer.parseInt(txtGroupId);
            System.out.println("groupid" + groupid);

        } catch (Exception e) {
            System.out.println("Exception in sectid:" + e);
            groupid = 0;
        }
        try {
            accountheadcode = Integer.parseInt(txtaccountheadcode);
            System.out.println("accountheadcode" + accountheadcode);

        } catch (Exception e) {
            System.out.println("Exception in accountheadcode:" + e);
            accountheadcode = 0;
        }

        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
        if (strCommand.equalsIgnoreCase("Load_Group_Code")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>Load_Group_Code</command>";
            int y = 0;
            System.out.println("insideloadcode");
            int secid = 0;
            // int subcode=0;

            try {
                secid = Integer.parseInt(request.getParameter("txtSectionId"));
                System.out.println("secid:" + secid);
            } catch (Exception que) {
                System.out.println("Exception in assigning values in load command secid...." +
                                   que);
            }


            try {
                //String secname="";
                ps =
  con.prepareStatement("select GROUP_ID,GROUP_NAME from FAS_MST_GROUPS WHERE SECTION_ID=?");
                ps.setInt(1, secid);

                res = ps.executeQuery();
                if (res.next()) {
                    xml =
 xml + "<cid>" + res.getInt("GROUP_ID") + "</cid><cname>" +
   res.getString("GROUP_NAME") + "</cname>";
                    //y++;

                    xml = xml + "<flag>success</flag>";
                } else
                    xml = xml + "<flag>failure</flag>";

                ps.close();
                res.close();
            }


            catch (Exception e) {
                System.out.println("Finding groupcode failed due to exception" +
                                   e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
            return;
        }

        else if (strCommand.equalsIgnoreCase("Add")) {
            xml = "<response><command>Add</command>";
            try {
                ps1 =
 con.prepareStatement("select account_head_code from FAS_MST_SECTIONS_GROUPS_HEADS where SECTION_ID=? and ACCOUNT_HEAD_CODE=?");
                ps1.setInt(1, sectid);
               // ps1.setInt(2, groupid);
                ps1.setInt(2, accountheadcode);
                rs1 = ps1.executeQuery();
                if (!rs1.next()) {
                    System.out.println("this i sinside the if loop");
                    System.out.println(accountheadcode);

                    try {
                        ps =
  con.prepareStatement("insert into FAS_MST_SECTIONS_GROUPS_HEADS (ACCOUNT_HEAD_CODE,SECTION_ID,GROUP_ID,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?)");
                        // System.out.println(ps);
                        ps.setInt(1, accountheadcode);
                        ps.setInt(2, sectid);
                        ps.setInt(3, groupid);
                        // ps.setString(3,officeid);
                        //  ps.setString(4,txtGroup);
                        ps.setString(4, userid);
                        ps.setTimestamp(5, ts);
                        ps.executeUpdate();
                        xml = xml + "<flag>success</flag>";


                    } catch (Exception e) {

                        System.out.println("catch. in  adding...." + e);
                        xml = xml + "<flag>failure</flag>";
                    }
                } else {
                    System.out.println("This is Else Loop");
                    xml = xml + "<flag>AlreadyExist</flag>";
                }

                xml = xml + "</response>";
            } catch (Exception e) {
                System.out.println("Exception in select:" + e);
            }
        } else if (strCommand.equalsIgnoreCase("Update")) {
            xml = "<response><command>Update</command>";


            try {
                String sql =
                    "Update FAS_MST_SECTIONS_GROUPS_HEADS set ACCOUNT_HEAD_CODE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where SECTION_ID=? and GROUP_ID=?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, accountheadcode);
                //    ps.setString(2,txtGroup);
                ps.setString(2, userid);
                ps.setTimestamp(3, ts);
                ps.setInt(4, sectid);
                ps.setInt(5, groupid);

                int ii = ps.executeUpdate();
                if (ii >= 1) {
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }


            } catch (Exception e) {
                System.out.println("Exception in Update:" + e);
            }
            xml = xml + "</response>";
        } else if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";
            try {

                Statement st = con.createStatement();
                String sql =
                    "delete from FAS_MST_SECTIONS_GROUPS_HEADS where SECTION_ID=" +
                    sectid + " and GROUP_ID=" + groupid +
                    " and account_head_code=" + accountheadcode + "";
                System.out.println(sql);
                int ij = st.executeUpdate(sql);

                if (ij > 0) {
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("catch...." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("List")) {
            int acchead = 0;
            int sectionid = 0;
            String grp = "";
            String secname = "";
            String sxml = "";
            try {
                ps =
  con.prepareStatement("select a.ACCOUNT_HEAD_CODE,a.SECTION_ID,b.ACCOUNT_HEAD_DESC,c.SECTION_NAME from FAS_MST_SECTIONS_GROUPS_HEADS a, " +
                       " com_mst_account_heads b,FAS_MST_SECTIONS c,FAS_MST_GROUPS d where a.account_head_code=b.account_head_code and a.SECTION_ID=c.SECTION_ID  " +
                       " and a.group_id=d.group_id and a.SECTION_ID=? and a.group_id  ");
                ps.setInt(1, sectid);
                // ps.setInt(2,offid);
                rs = ps.executeQuery();
                xml = "<response><command>List</command><flag>success</flag>";
                while (rs.next()) {
                    acchead = rs.getInt("ACCOUNT_HEAD_CODE");
                    sectionid = rs.getInt("SCHEDULE_ID");
                    secname = rs.getString("SECTION_NAME");

                    sxml =
sxml + "<acchead>" + acchead + "</acchead><sectionid>" + sectionid +
 "</sectionid><accheadname>" + rs.getString("ACCOUNT_HEAD_DESC") +
 "</accheadname>";
                }
                xml = xml + sxml + "</response>";
            } catch (Exception e) {
                System.out.println("Exception in List:" + e);
            }


        }

        System.out.println("xml is:" + xml);
        out.write(xml);
        out.flush();
        out.close();
    }
}
