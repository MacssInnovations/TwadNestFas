package Servlets.FAS.FAS1.CommonControls.servlets;


//import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook_for_Journal;
import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;


import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class TB_Checking_for_Journal extends HttpServlet {
    private String CONTENT_TYPE = "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {


        /**
     * Session Checking
     */

        try {
            HttpSession session = request.getSession(false);
            if (session == null) {

                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }

        Connection con = null;
        ResultSet rs = null, rs2 = null, rs3 = null, rsbank = null;
        PreparedStatement ps = null, ps2 = null, ps3 = null, psbank = null;
        //String xml="";
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
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }


        /**
         * Get Command Parameter
         */

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);

        } catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }


        /**
         * Trial Balance Checking for Journal System
         */

        if (strCommand.equalsIgnoreCase("check_TB")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            Calendar c;
            String xml = "";
            Date txtCrea_date = null;
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
                0, txtCash_year = 0;
            System.out.println("check_TB if condi");
            xml = "<response><command>check_TB</command>";

            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbOffice_code " + cmbOffice_code);

            String[] sd =
                request.getParameter("TB_date").split("/"); // *** seee here getting TB_date date not " txtCrea_date " ***
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("TB_date " + txtCrea_date);


            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            /** Get Receipt Creation Date */
            String Receipt_Creation_Date = request.getParameter("TB_date");

            /** Call Com_CashBook Servlet for Calculating Cash Book Month and Year */
            Com_CashBook1 cb = new Com_CashBook1();

            /** Assign Cashbook Year and Month to year_month Variable */
            String year_month = cb.cb_date(Receipt_Creation_Date).toString();

            /** Split Cash Book Year and Month */
            String[] ym = year_month.split("/");

            /** Assign Year and Month */
            txtCash_year = Integer.parseInt(ym[0]);
            txtCash_Month_hid = Integer.parseInt(ym[1]);
            int check_financeyear = Integer.parseInt(ym[2]);


            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


            System.out.println("check financ year is " + check_financeyear);

            try {
                if (check_financeyear == 0) {
                    xml =
 "<response><command>check_TB</command><flag>finyear</flag></response>"; // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date
                    out.println(xml);
                    return;
                }

                System.out.println("checking.." + txtCash_year);
                System.out.println("checking.." + txtCash_Month_hid);
                ps =
  con.prepareStatement("select TB_STATUS from FAS_TRIAL_BALANCE_STATUS where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=? ");
                ps.setInt(1, cmbAcc_UnitCode);
                //ps.setInt(2,cmbOffice_code);
                ps.setInt(2, txtCash_year);
                ps.setInt(3, txtCash_Month_hid);
                rs = ps.executeQuery();
                //System.out.println(rs.next());
                if (rs.next()) {
                    if (rs.getString("TB_STATUS").equalsIgnoreCase("N"))
                        xml = xml + "<flag>success</flag>";
                    else
                        xml = xml + "<flag>failure</flag>";
                } else
                    xml = xml + "<flag>success</flag>";

            } catch (Exception e) {
                System.out.println("catch..HERE.in TB_date " + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }
        
        if (strCommand.equalsIgnoreCase("check_TB_SJV")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            Calendar c;
            String xml = "";
            Date txtCrea_date = null;
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
                0, txtCash_year = 0;
            System.out.println("check_TB if condi");
            xml = "<response><command>check_TB</command>";

            try {
                cmbAcc_UnitCode =
                        Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

            try {
                cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("cmbOffice_code " + cmbOffice_code);

            String[] sd =
                request.getParameter("TB_date").split("/"); // *** seee here getting TB_date date not " txtCrea_date " ***
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("TB_date " + txtCrea_date);


            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            /** Get Receipt Creation Date */
            String Receipt_Creation_Date = request.getParameter("TB_date");

            /** Call Com_CashBook Servlet for Calculating Cash Book Month and Year */
            Com_CashBook1 cb = new Com_CashBook1();

            /** Assign Cashbook Year and Month to year_month Variable */
            String year_month = cb.cb_date(Receipt_Creation_Date).toString();

            /** Split Cash Book Year and Month */
            String[] ym = year_month.split("/");

            /** Assign Year and Month */
            txtCash_year = Integer.parseInt(ym[0]);
            txtCash_Month_hid = Integer.parseInt(ym[1]);
            int check_financeyear = Integer.parseInt(ym[2]);


            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


            System.out.println("check financ year is " + check_financeyear);

            try {
                if (check_financeyear == 0) {
                    xml =
 "<response><command>check_TB</command><flag>finyear</flag></response>"; // This statement get executed when financial year ( Cash Book Control Details ) has not found for the given date
                    out.println(xml);
                    return;
                }

                System.out.println("checking.." + txtCash_year);
                System.out.println("checking.." + txtCash_Month_hid);
                ps =
  con.prepareStatement("select TB_STATUS from FAS_TRIAL_BALANCE_STATUS_SJV where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=? ");
                ps.setInt(1, cmbAcc_UnitCode);
                //ps.setInt(2,cmbOffice_code);
                ps.setInt(2, txtCash_year);
                ps.setInt(3, txtCash_Month_hid);
                rs = ps.executeQuery();
                //System.out.println(rs.next());
                if (rs.next()) {
                    if (rs.getString("TB_STATUS").equalsIgnoreCase("N"))
                        xml = xml + "<flag>success</flag>";
                    else
                        xml = xml + "<flag>failure</flag>";
                } else
                    xml = xml + "<flag>success</flag>";

            } catch (Exception e) {
                System.out.println("catch..HERE.in TB_date " + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }
        
    }
}
