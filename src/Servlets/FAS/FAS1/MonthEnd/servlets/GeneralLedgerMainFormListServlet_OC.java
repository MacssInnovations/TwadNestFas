package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class GeneralLedgerMainFormListServlet_OC extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);


    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        /**
              *  Variables Declaration
              */

        Connection con = null;
        ResultSet rss = null;
        PreparedStatement pss = null;


        /**
              * Database Connection
              */
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

        /**
             * Set Content Type
             */
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();


        /**
             * Session Checking
             */
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        int acOffId = 0;
        int AccId = 0;
        int OffCode = 0;
        String FinanYr = "";
        String finanYR = "";

        String MONTH_CLOSING_BALANCE = "";
        String MONTH_CLOSING_BAL_DR_CR_IND = "";

        int CashbookYear = 0;
        int CashbookMonth = 0;

        int straccountHeadCode = 0;
        String strCommand = "";
        String xml = "";
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");

        try {
            strCommand = request.getParameter("command");
        } catch (Exception ae) {
            System.out.println("first exception...." + ae);
        }

        try {
            try {
                straccountHeadCode =
                        Integer.parseInt(request.getParameter("accountHeadcode"));
            } catch (Exception e) {
                System.out.println("Exception in checkcode");
            }
        } catch (Exception ae) {
            System.out.println("first exception...." + ae);
        }

        try {
            AccId = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (Exception que) {
            System.out.println("Exception in assigning values in add command...." +
                               que);
        }


        try {
            OffCode = Integer.parseInt(request.getParameter("comOffCode"));
            System.out.println(OffCode);
        } catch (Exception que) {
            System.out.println("Exception in assigning values in add command...." +
                               que);
        }

        try {
            CashbookYear =
                    Integer.parseInt(request.getParameter("CashbookYear"));
            System.out.println(CashbookYear);
        } catch (Exception que) {
            System.out.println("Exception in assigning values in CashbookYear...." +
                               que);
        }


        try {
            CashbookMonth =
                    Integer.parseInt(request.getParameter("CashbookMonth"));
            System.out.println(CashbookMonth);
        } catch (Exception que) {
            System.out.println("Exception in assigning values in CashbookMonth...." +
                               que);
        }

        FinanYr = request.getParameter("txtFinanYr");


        if (strCommand.equalsIgnoreCase("fetch")) {

            xml = "<response><command>fetch</command>";
            System.out.println("inside fetch command");
            try {
                pss =
 con.prepareStatement("select ACCOUNTING_UNIT_ID ,ACCOUNTING_FOR_OFFICE_ID ,FINANCIAL_YEAR ,ACCOUNT_HEAD_CODE,MONTH_CLOSING_BALANCE,MONTH_CLOSING_BAL_DR_CR_IND  from FAS_GENERAL_LEDGER_CB where ACCOUNT_HEAD_CODE=? and ACCOUNTING_UNIT_ID=? and  ACCOUNTING_FOR_OFFICE_ID=? and year=? and month=?");
                pss.setInt(1, straccountHeadCode);
                pss.setInt(2, AccId);
                pss.setInt(3, OffCode);
                pss.setInt(4, CashbookYear);
                pss.setInt(5, CashbookMonth);
                rss = pss.executeQuery();
                if (rss.next()) {

                    acOffId = rss.getInt("ACCOUNTING_FOR_OFFICE_ID");
                    straccountHeadCode = rss.getInt("ACCOUNT_HEAD_CODE");
                    MONTH_CLOSING_BALANCE =
                            rss.getString("MONTH_CLOSING_BALANCE");
                    MONTH_CLOSING_BAL_DR_CR_IND =
                            rss.getString("MONTH_CLOSING_BAL_DR_CR_IND");

                }
                xml =
 xml + "<flag>success</flag><straccountHeadCode>" + straccountHeadCode +
   "</straccountHeadCode><finanYR>" + finanYR + "</finanYR><acOffId>" +
   acOffId + "</acOffId><CloseBal>" + MONTH_CLOSING_BALANCE +
   "</CloseBal><CloseBalInd>" + MONTH_CLOSING_BAL_DR_CR_IND + "</CloseBalInd>";

            } catch (Exception que) {
                xml = xml + "<flag>failure</flag>";
                System.out.println("exception in fetching fas_opening balance details");
            }
            xml = xml + "</response>";
        }


        System.out.println("xml is:" + xml);
        out.write(xml);
        out.flush();
        out.close();

    }


}

