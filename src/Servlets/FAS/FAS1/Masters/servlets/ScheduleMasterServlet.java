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

public class ScheduleMasterServlet extends HttpServlet {
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
        String txtScheduleId = "";
        String radCrDrInd = "";
        int accountheadcode = 0;
        txtaccountheadcode = request.getParameter("txtAcc_Head_code");
        txtScheduleId = request.getParameter("txtScheduleId");
        radCrDrInd = request.getParameter("radCrDrInd");
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        try {

            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand + txtScheduleId);

        } catch (Exception ae) {
            System.out.println("first exception...." + ae);
        }

        try {
            accountheadcode = Integer.parseInt(txtaccountheadcode);

        } catch (Exception e) {
            System.out.println("Exception in accounthead:" + e);
            accountheadcode = 0;
        }

        if (strCommand.equalsIgnoreCase("Add")) {
            xml = "<response><command>Add</command>";
            try {
                ps1 =
 con.prepareStatement("select account_head_code from fas_schedulemaster where SCHEDULE_ID=? AND CR_DR_INDICATOR=? and ACCOUNT_HEAD_CODE=?");
                ps1.setString(1, txtScheduleId);
                ps1.setString(2, radCrDrInd);
                ps1.setInt(3, accountheadcode);
                rs1 = ps1.executeQuery();
                if (!rs1.next()) {
                    System.out.println("this i sinside the if loop");


                    try {
                        ps =
  con.prepareStatement("insert into FAS_SCHEDULEMASTER (ACCOUNT_HEAD_CODE,SCHEDULE_ID,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?)");
                        // System.out.println(ps);
                        ps.setInt(1, accountheadcode);
                        ps.setString(2, txtScheduleId);
                        //ps.setString(3,radCrDrInd);
                        ps.setString(3, userid);
                        long l = System.currentTimeMillis();
                        Timestamp ts = new Timestamp(l);
                        ps.setTimestamp(4, ts);
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
                    "Update FAS_SCHEDULEMASTER set ACCOUNT_HEAD_CODE=? where SCHEDULE_ID=? and account_head_code=?";
                ps = con.prepareStatement(sql);
                ps.setInt(1, accountheadcode);
                //ps.setString(2,radCrDrInd);
                ps.setString(2, txtScheduleId);
                ps.setInt(3, accountheadcode);

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
                    "delete from FAS_SCHEDULEMASTER where SCHEDULE_ID='" +
                    txtScheduleId + "' and account_head_code='" +
                    txtaccountheadcode + "'";
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
            String scheduleid = "";
            String ind = "";
            String sxml = "";
            try {
                ps =
  con.prepareStatement("select a.ACCOUNT_HEAD_CODE,a.SCHEDULE_ID,a.CR_DR_INDICATOR,b.ACCOUNT_HEAD_DESC from FAS_SCHEDULEMASTER a, " +
                       " com_mst_account_heads b where a.account_head_code=b.account_head_code and " +
                       " SCHEDULE_ID=?");
                ps.setString(1, txtScheduleId);
                rs = ps.executeQuery();
                xml = "<response><command>List</command><flag>success</flag>";
                while (rs.next()) {
                    acchead = rs.getInt("ACCOUNT_HEAD_CODE");
                    scheduleid = rs.getString("SCHEDULE_ID");
                    ind = rs.getString("CR_DR_INDICATOR");
                    sxml =
sxml + "<acchead>" + acchead + "</acchead><schedule>" + scheduleid +
 "</schedule><Indicator>" + ind + "</Indicator><accheadname>" +
 rs.getString("ACCOUNT_HEAD_DESC") + "</accheadname>";
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
