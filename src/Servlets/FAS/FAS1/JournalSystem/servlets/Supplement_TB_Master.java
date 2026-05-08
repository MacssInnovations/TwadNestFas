package Servlets.FAS.FAS1.JournalSystem.servlets;


import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import java.util.StringTokenizer;

import java.text.*;

import sun.util.calendar.Gregorian;

public class Supplement_TB_Master extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {


        /**
       * Session Checking
       */

        HttpSession session = request.getSession(false);
        try {

            if (session == null) {

                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        /**
         * Variables Declaration
         */
        Connection con = null;
        ResultSet rs = null;
        PreparedStatement ps = null;
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String strCommand = "";


        /**
         * Database Connection
         */

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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);

        }


        /**
         * Get Command Parameter
         */

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }


        /**
         * Variables Declaration
         */

        int txtSupplement_No = 0;
        int txtCashbook_Year = 0;
        int txtCashbook_Month = 0;
        String txtStatus = null;
        Date Suppl_date = null;
        String txtRemarks = null;
        Date txtDate = null;

        Calendar c;
        String update_user = (String)session.getAttribute("UserId");
        // String update_user="twad10099";
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);


        /**
         * Get Parameters
         */

        /** Get Supplement Number */
        try {
            txtSupplement_No =
                    Integer.parseInt(request.getParameter("txtSupplement_No"));
        } catch (Exception e) {
            System.out.println("Exception to Catch Supplement Number ");
        }


        System.out.println("txtSupplement_No" + txtSupplement_No);

        /** Get Date */
        try {
            if (!(request.getParameter("txtDate")).equalsIgnoreCase("")) {
                String sd[] = request.getParameter("txtDate").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                java.util.Date d = c.getTime();
                txtDate = new Date(d.getTime());
                System.out.println("txtDate " + txtDate);

            }
        } catch (Exception e) {
            System.out.println("Exception to catch Receipt Date");
        }


        /** Get Cashbook Year  */
        try {
            txtCashbook_Year =
                    Integer.parseInt(request.getParameter("txtCashbook_Year"));
        } catch (Exception e) {
            System.out.println("Exception to Catch Cashbook Year");
        }


        System.out.println("txtCashbook_Year" + txtCashbook_Year);

        /** Get Cashbook Month  */
        try {
            txtCashbook_Month =
                    Integer.parseInt(request.getParameter("txtCashbook_Month"));
        } catch (Exception e) {
            System.out.println("Exception to catch txtCashbook_Month");
        }

        System.out.println("txtCashbook_Month" + txtCashbook_Month);

        /** Get Supplement Closure Date */
        try {
            if (!(request.getParameter("txtSuppl_Closure_date")).equalsIgnoreCase("")) {
                String sd[] =
                    request.getParameter("txtSuppl_Closure_date").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                java.util.Date d = c.getTime();
                Suppl_date = new Date(d.getTime());
                System.out.println("txtreceipt_date " + Suppl_date);

            }
        } catch (Exception e) {
            System.out.println("Exception to catch Receipt Date");
        }


        /** Get Status */
        try {
            txtStatus = request.getParameter("txtStatus");
        } catch (Exception e) {
            System.out.println("Exception to catch txtStatus");
        }

        System.out.println("txtStatus" + txtStatus);

        /** Get Remarks */
        try {
            txtRemarks = request.getParameter("txtRemarks");
        } catch (Exception e) {
            System.out.println("Exception to catch txtRemarks");
        }

        System.out.println("txtRemarks" + txtRemarks);

        System.out.println("after getting parameters");


        //~~~~~~~~~~~~~~~~~~~~~~~~ Here It does some Database Operations such as add , delete , update, etc., ~~~~~~~~~~~~~~~~~~~~~~

        if (strCommand.equalsIgnoreCase("Add")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Add</command>";
            System.out.println(xml);
            System.out.println(txtRemarks);
            try {

                ps =
  con.prepareStatement("insert into FAS_SUPPLEMENT_GJV ( SUPPLEMENT_NO, SUPPL_DATE, CASHBOOK_YEAR, CASHBOOK_MONTH, SUPPL_CLOSURE_DATE, STATUS , REMARKS , UPDATED_BY_USER_ID, UPDATED_DATE ) values (?,?,?,?,?,?,?,?,?)");

                ps.setInt(1, txtSupplement_No);
                ps.setDate(2, txtDate);
                ps.setInt(3, txtCashbook_Year);
                ps.setInt(4, txtCashbook_Month);
                ps.setDate(5, Suppl_date);
                ps.setString(6, txtStatus);
                ps.setString(7, txtRemarks);
                ps.setString(8, update_user);
                ps.setTimestamp(9, ts);

                ps.executeUpdate();


                xml = xml + "<flag>success</flag>";
            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }


        else if (strCommand.equalsIgnoreCase("Freeze")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Freeze</command>";

            try {

                ps =
  con.prepareStatement("update FAS_SUPPLEMENT_GJV set SUPPL_CLOSURE_DATE=? , STATUS=? , REMARKS=? , UPDATED_BY_USER_ID=? , UPDATED_DATE=?  where SUPPLEMENT_NO=? and cashbook_year = ? and cashbook_month = ?");

                ps.setDate(1, Suppl_date);
                ps.setString(2, "C");
                ps.setString(3, txtRemarks);
                ps.setString(4, update_user);
                ps.setTimestamp(5, ts);
                ps.setInt(6, txtSupplement_No);
                ps.setInt(7, txtCashbook_Year);
                ps.setInt(8, txtCashbook_Month);
                ps.executeUpdate();

                xml = xml + "<flag>success</flag>";

            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }

        else if (strCommand.equalsIgnoreCase("Unfreeze")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Unfreeze</command>";

            try {
                ps =
  con.prepareStatement("update FAS_SUPPLEMENT_GJV set SUPPL_CLOSURE_DATE=? , STATUS=? , REMARKS=? , UPDATED_BY_USER_ID=? , UPDATED_DATE=?  where SUPPLEMENT_NO=? and cashbook_year = ? and cashbook_month = ?");

                ps.setDate(1, Suppl_date);
                ps.setString(2, "L");
                ps.setString(3, txtRemarks);
                ps.setString(4, update_user);
                ps.setTimestamp(5, ts);
                ps.setInt(6, txtSupplement_No);
                ps.setInt(7, txtCashbook_Year);
                ps.setInt(8, txtCashbook_Month);
                ps.executeUpdate();

                xml = xml + "<flag>success</flag>";
            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }


        else if (strCommand.equalsIgnoreCase("Load_Supplement_No")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Load_Supplement_No</command>";

            try {
                ps =
  con.prepareStatement("select max(SUPPLEMENT_NO) as suppl_no from FAS_SUPPLEMENT_GJV where cashbook_year = ? and cashbook_month = ? ");
                ps.setInt(1, txtCashbook_Year);
                ps.setInt(2, txtCashbook_Month);
                rs = ps.executeQuery();
                int suppl_no = 0;
                while (rs.next()) {
                    suppl_no = rs.getInt("suppl_no") + 1;
                }
                xml = xml + "<flag>success</flag>";
                xml = xml + "<Supplement_No>" + suppl_no + "</Supplement_No>";

            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }


    }
}

