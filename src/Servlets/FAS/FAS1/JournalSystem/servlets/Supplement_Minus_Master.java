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

public class Supplement_Minus_Master extends HttpServlet {
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

        //  int txtSupplement_No=0;
        int txtCashbook_Year = 0;
        int txtCashbook_Month = 0;
        String txtStatus_SJV = null;
        String txtStatus_GJV = null;
        // Date Suppl_date=null;
        String txtRemarks = null;
        //Date txtDate=null;

        Calendar c;
        String update_user = (String)session.getAttribute("UserId");
        // String update_user="twad10099";
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);


        /**
         * Get Parameters
         */

        /** Get Supplement Number */
        /* try{
        txtSupplement_No=Integer.parseInt(request.getParameter("txtSupplement_No"));
        }catch(Exception e){System.out.println("Exception to Catch Supplement Number ");}


        System.out.println("txtSupplement_No"+txtSupplement_No);*/

        /** Get Date */
        /*   try{
                if(!(request.getParameter("txtDate")).equalsIgnoreCase(""))
                {
                    String sd[]=request.getParameter("txtDate").split("/");
                    c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                    java.util.Date d=c.getTime();
                    txtDate=new Date(d.getTime());
                    System.out.println("txtDate "+txtDate);

                }
        }
        catch(Exception e){System.out.println("Exception to catch Receipt Date");}   */


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
        /*    try{
                if(!(request.getParameter("txtSuppl_Closure_date")).equalsIgnoreCase(""))
                {
                    String sd[]=request.getParameter("txtSuppl_Closure_date").split("/");
                    c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                    java.util.Date d=c.getTime();
                    Suppl_date=new Date(d.getTime());
                    System.out.println("txtreceipt_date "+Suppl_date);

                }
        }
        catch(Exception e){System.out.println("Exception to catch Receipt Date");}*/


        /** Get Status */
        try {
            txtStatus_SJV = request.getParameter("txtStatus_SJV");
        } catch (Exception e) {
            System.out.println("Exception to catch txtStatus_SJV");
        }

        System.out.println("txtStatus" + txtStatus_SJV);
        try {
            txtStatus_GJV = request.getParameter("txtStatus_GJV");
        } catch (Exception e) {
            System.out.println("Exception to catch txtStatus_GJV");
        }

        System.out.println("txtStatus" + txtStatus_GJV);

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
  con.prepareStatement("insert into FAS_SUPPLE_MINUS_MST ( CASHBOOK_YEAR, CASHBOOK_MONTH, GJV_MINUS, SJV_MINUS, REMARKS,  UPDATED_BY_USER_ID, UPDATED_DATE ) values (?,?,?,?,?,?,?)");

                //   ps.setInt(1,txtSupplement_No);
                //  ps.setDate(2,txtDate);
                ps.setInt(1, txtCashbook_Year);
                ps.setInt(2, txtCashbook_Month);
                // ps.setDate(5,Suppl_date);
                ps.setString(3, txtStatus_GJV);
                ps.setString(4, txtStatus_SJV);
                ps.setString(5, txtRemarks);
                ps.setString(6, update_user);
                ps.setTimestamp(7, ts);

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


        else if (strCommand.equalsIgnoreCase("update")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>update</command>";

            try {

                ps =
  con.prepareStatement("update FAS_SUPPLE_MINUS_MST set GJV_MINUS=?, SJV_MINUS=?, REMARKS=?,UPDATED_BY_USER_ID=? , UPDATED_DATE=?  where CASHBOOK_YEAR=? and CASHBOOK_MONTH=? ");

                ps.setString(1, txtStatus_GJV);
                ps.setString(2, txtStatus_SJV);
                ps.setString(3, txtRemarks);
                ps.setString(4, update_user);
                ps.setTimestamp(5, ts);
                ps.setInt(6, txtCashbook_Year);
                ps.setInt(7, txtCashbook_Month);
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

        else if (strCommand.equalsIgnoreCase("delete")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>delete</command>";

            try {
                ps =
  con.prepareStatement("delete from FAS_SUPPLE_MINUS_MST where CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");

                ps.setInt(1, txtCashbook_Year);
                ps.setInt(2, txtCashbook_Month);
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


    }


}


