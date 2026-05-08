package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

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

public class Bill_Receipt_Register extends HttpServlet {
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
//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
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
        int cmbAcc_UnitCode = 0, cmbOffice_code = 0, cmbMas_SL_type =
            0, cmbMas_SL_Code = 0, txtcash_book_year = 0, txtcash_book_month =
            0, txtbill_no = 0;
        int txtCash_year = 0;
        Date txtbill_date = null, txtreceipt_date = null;
        String txtreference_book = "", txtRemarks = "", txtbill_description =
            "", txtbill_type = "";
        double txtbill_Amount = 0;
        int txtCash_Month_hid = 0;
        Calendar c;
        String update_user = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);


        /**
         * Get Parameters
         */

        /** Get Accounting Unit ID */
        try {
            cmbAcc_UnitCode =
                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (Exception e) {
            System.out.println("Exception to catch Accounting Unit Code ");
        }

        /** Get Accounting Office ID */
        try {
            cmbOffice_code =
                    Integer.parseInt(request.getParameter("cmbOffice_code"));
        } catch (Exception e) {
            System.out.println("Exception to catch Accounting Office Code");
        }

        /** Get Sub Ledger Type */
        try {
            cmbMas_SL_type =
                    Integer.parseInt(request.getParameter("cmbMas_SL_type"));
        } catch (Exception e) {
            System.out.println("Exception to catch Sub-Ledger Type");
        }

        /** Get Sub Ledger Code */
        try {
            cmbMas_SL_Code =
                    Integer.parseInt(request.getParameter("cmbMas_SL_Code"));
        } catch (Exception e) {
            System.out.println("Exception to catch Sub-Ledger Code");
        }


        /** Get Receipt Date */
        try {
            if (!(request.getParameter("txtreceipt_date")).equalsIgnoreCase("")) {
                String sd[] =
                    request.getParameter("txtreceipt_date").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                java.util.Date d = c.getTime();
                txtreceipt_date = new Date(d.getTime());
                System.out.println("txtreceipt_date " + txtreceipt_date);

            }
        } catch (Exception e) {
            System.out.println("Exception to catch Receipt Date");
        }


        /** Find Cashbook Month and Year from Receipt Date */
        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

        /** Get Receipt Creation Date */
        String Receipt_Creation_Date = request.getParameter("txtreceipt_date");

        /** Call Com_CashBook Servlet for Calculating Cash Book Month and Year */
        Com_CashBook1 cb = new Com_CashBook1();

        /** Assign Cashbook Year and Month to year_month Variable */
        String year_month = cb.cb_date(Receipt_Creation_Date).toString();

        /** Split Cash Book Year and Month */
        String[] ym = year_month.split("/");

        /** Assign Year and Month */
        txtCash_year = Integer.parseInt(ym[0]);
        txtCash_Month_hid = Integer.parseInt(ym[1]);

        //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~


        txtcash_book_year = txtCash_year;
        txtcash_book_month = txtCash_Month_hid;

        /** Get Bill Number */
        try {
            txtbill_no = Integer.parseInt(request.getParameter("txtbill_no"));
        } catch (Exception e) {
            System.out.println("Exception to catch Bill Number");
        }


        /** Get Bill Date */
        try {
            if (!(request.getParameter("txtbill_date")).equalsIgnoreCase("")) {
                String sd[] = request.getParameter("txtbill_date").split("/");
                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                java.util.Date d = c.getTime();
                txtbill_date = new Date(d.getTime());
                System.out.println("txtbill_date " + txtbill_date);
            }
        } catch (Exception e) {
            System.out.println("Exception to catch txtOpening_date ");
        }

        /** Get Bill Type */
        txtbill_type = request.getParameter("txtbill_type");

        /** Get Bill Description */
        txtbill_description = request.getParameter("txtbill_description");

        /** Get Bill Amount */
        try {
            txtbill_Amount =
                    Double.parseDouble(request.getParameter("txtbill_Amount"));
        } catch (Exception e) {
            System.out.println("Exception to catch Bill Amount ");
        }

        /** Get Reference Book */
        txtreference_book = request.getParameter("txtreference_book");

        /** Get Remark */
        txtRemarks = request.getParameter("txtRemarks");


        System.out.println("after getting parameters");


        //~~~~~~~~~~~~~~~~~~~~~~~~ Here It does some Database Operations such as add , delete , update, etc., ~~~~~~~~~~~~~~~~~~~~~~

        if (strCommand.equalsIgnoreCase("Add")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Add</command>";
            System.out.println("add");
            System.out.println(txtRemarks);
            try {
                ps =
  con.prepareStatement("insert into FAS_BILL_MOVEMENT_REGISTER(ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, BILL_NO, CASHBOOK_YEAR , CASHBOOK_MONTH , " +
                       "BILL_TYPE, BILL_DATE, UPDATED_BY_USER_ID, UPDATED_DATE, RECEIPT_DATE, BILL_DESCRIPTION , BILL_AMOUNT ,MBOOK_REFERENCE , REMARKS ) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");


                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, cmbMas_SL_type);
                ps.setInt(4, cmbMas_SL_Code);

                ps.setInt(5, txtbill_no);
                ps.setInt(6, txtcash_book_year);
                ps.setInt(7, txtcash_book_month);
                ps.setString(8, txtbill_type);
                ps.setDate(9, txtbill_date);
                ps.setString(10, update_user);
                ps.setTimestamp(11, ts);
                ps.setDate(12, txtreceipt_date);
                ps.setString(13, txtbill_description);
                ps.setDouble(14, txtbill_Amount);
                ps.setString(15, txtreference_book);
                ps.setString(16, txtRemarks);

                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("Update")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Update</command>";

            try {

                ps =
  con.prepareStatement("update FAS_BILL_MOVEMENT_REGISTER set CASHBOOK_YEAR=? , CASHBOOK_MONTH=? , BILL_TYPE=? , BILL_DATE=? , UPDATED_BY_USER_ID=? , UPDATED_DATE=? , RECEIPT_DATE=? , BILL_DESCRIPTION=? , BILL_AMOUNT=? , MBOOK_REFERENCE=? , REMARKS=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and SUB_LEDGER_TYPE_CODE=? and SUB_LEDGER_CODE=? and BILL_NO=? ");

                ps.setInt(1, txtcash_book_year);
                ps.setInt(2, txtcash_book_month);
                ps.setString(3, txtbill_type);
                ps.setDate(4, txtbill_date);
                ps.setString(5, update_user);
                ps.setTimestamp(6, ts);
                ps.setDate(7, txtreceipt_date);
                ps.setString(8, txtbill_description);
                ps.setDouble(9, txtbill_Amount);
                ps.setString(10, txtreference_book);
                ps.setString(11, txtRemarks);

                ps.setInt(12, cmbAcc_UnitCode);
                ps.setInt(13, cmbOffice_code);
                ps.setInt(14, cmbMas_SL_type);
                ps.setInt(15, cmbMas_SL_Code);
                ps.setInt(16, txtbill_no);

                ps.executeUpdate();

                xml = xml + "<flag>success</flag>";
            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("Delete")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Delete</command>";

            try {
                ps =
  con.prepareStatement("delete from  FAS_BILL_MOVEMENT_REGISTER " +
                       " where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and SUB_LEDGER_TYPE_CODE=? and SUB_LEDGER_CODE=? and BILL_NO=?");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, cmbMas_SL_type);
                ps.setInt(4, cmbMas_SL_Code);
                ps.setInt(5, txtbill_no);

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

