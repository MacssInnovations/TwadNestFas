package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class A42ScheduleServlet_New_SingleAH extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        Connection connection = null;
        Statement statement = null;
        ResultSet results = null;
        ResultSet results2 = null;
        ResultSet results3 = null;
        ResultSet results4 = null;
        ResultSet results5 = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps3 = null;
        PreparedStatement ps6 = null;
        double month_ob = 0.0;
        String month_opening_bal_dr_cr_ind = "";
        String whom = "";
        double DRAmount1 = 0.0;
        double CRAmount = 0.0;

        Date vdate = null;
        try {
            ResourceBundle rs =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs.getString("Config.DSN");
            String strhostname = rs.getString("Config.HOST_NAME");
            String strportno = rs.getString("Config.PORT_NUMBER");
            String strsid = rs.getString("Config.SID");
            String strdbusername = rs.getString("Config.USER_NAME");
            String strdbpassword = rs.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }

        response.setContentType(CONTENT_TYPE);


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
        System.out.println("session id is:" + userid);

        String Account_unit_Code = request.getParameter("cmbAcc_UnitCode");
        String Office_Code = request.getParameter("cmbOffice_code");
        String CashBook_Year = request.getParameter("txtCB_Year");
        String CashBook_Month = request.getParameter("txtCB_Month");
        String AccHead_Code = request.getParameter("txtAcc_HeadCode");
        String Schedule = request.getParameter("cmbScheduleid");
        Schedule = "A42";
        if (Schedule != null) {
            Schedule = "A42";
        }

        System.out.println("Account_Unit_Code is:" + Account_unit_Code);
        System.out.println("Office_Code is:" + Office_Code);
        System.out.println("CashBook_Year is:" + CashBook_Year);
        System.out.println("CashBook Month is:" + CashBook_Month);
        System.out.println("Account Head is:" + AccHead_Code);
        System.out.println("Schedule Id is:" + Schedule);
        int AccountUnitCode = 0;
        int OfficeCode = 0;
        int CashBookYear = 0;
        int CashBookMonth = 0;
        int AccountHeadCode = 0;
        int recpno = 0;
        String doctype = "";
        double amt = 0;
        java.sql.Date ReceiptDate = null;
        java.sql.Date VoucherDate = null;
        int achcode = 0;
        int acchcode = 0;
        String remarks = "";
        try {
            AccountUnitCode = Integer.parseInt(Account_unit_Code);
            OfficeCode = Integer.parseInt(Office_Code);
            CashBookYear = Integer.parseInt(CashBook_Year);
            CashBookMonth = Integer.parseInt(CashBook_Month);
            AccountHeadCode = Integer.parseInt(AccHead_Code);

            System.out.println("Account_Unit_Code After is:" +
                               AccountUnitCode);
            System.out.println("Office_Code After is:" + OfficeCode);
            System.out.println("CashBook_Year After is:" + CashBookYear);
            System.out.println("CashBook Month After is:" + CashBookMonth);
            //System.out.println("Account Head Code is:"+AccountHeadCode);;

        } catch (Exception e) {
            System.out.println("Exception in Converting Integer:" + e);
        }
        String monthInWords = "";
        if (CashBookMonth == 1)
            monthInWords = "January";
        else if (CashBookMonth == 2)
            monthInWords = "February";
        else if (CashBookMonth == 3)
            monthInWords = "March";
        else if (CashBookMonth == 4)
            monthInWords = "April";
        else if (CashBookMonth == 5)
            monthInWords = "May";
        else if (CashBookMonth == 6)
            monthInWords = "June";
        else if (CashBookMonth == 7)
            monthInWords = "July";
        else if (CashBookMonth == 8)
            monthInWords = "August";
        else if (CashBookMonth == 9)
            monthInWords = "September";
        else if (CashBookMonth == 10)
            monthInWords = "October";
        else if (CashBookMonth == 11)
            monthInWords = "November";
        else if (CashBookMonth == 12)
            monthInWords = "December";
        try {
            connection.setAutoCommit(false);
            String sqlquery5 =
                "delete from FAS_A35SCHEDULEREPORT_TMP where schedule_id=? ";

            ps2 = connection.prepareStatement(sqlquery5);
            ps2.setString(1, Schedule);
            /*ps2.setInt(2,AccountUnitCode);
            ps2.setInt(3,OfficeCode);
            ps2.setInt(4,CashBookYear);
            ps2.setInt(5,CashBookMonth);*/
            ps2.executeUpdate();
            ps2.close();
        } catch (Exception e) {
            System.out.println("Excepiton in schedule:" + e);
        }
        try {
            String sqlquery =
                "select ACCOUNT_HEAD_CODE from FAS_SCHEDULEMASTER where SCHEDULE_ID=? and ACCOUNT_HEAD_CODE=? ";
            ps = connection.prepareStatement(sqlquery);
            ps.setString(1, Schedule);
            ps.setInt(2, AccountHeadCode);

            results = ps.executeQuery();
            while (results.next()) //Start while(results.next())
            {
                achcode = results.getInt("ACCOUNT_HEAD_CODE");
                System.out.println("achcode is:" + achcode);

                try {
                    String sqlquery1 =
                        "select MONTH_OPENING_BALANCE,MONTH_OPENING_BAL_DR_CR_IND from FAS_GENERAL_LEDGER where " +
                        " ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and YEAR=? and MONTH=? and ACCOUNT_HEAD_CODE=?";
                    ps1 = connection.prepareStatement(sqlquery1);
                    ps1.setInt(1, AccountUnitCode);
                    ps1.setInt(2, OfficeCode);
                    ps1.setInt(3, CashBookYear);
                    ps1.setInt(4, CashBookMonth);
                    ps1.setInt(5, achcode);
                    results2 = ps1.executeQuery();

                    month_ob = 0.0;
                    month_opening_bal_dr_cr_ind = "";
                    if (results2.next()) {
                        month_ob = results2.getDouble("MONTH_OPENING_BALANCE");
                        month_opening_bal_dr_cr_ind =
                                results2.getString("MONTH_OPENING_BAL_DR_CR_IND");
                        System.out.println("month opening balance is:" +
                                           month_ob);
                        System.out.println("Month Opening Ind is:" +
                                           month_opening_bal_dr_cr_ind);

                    }
                } catch (Exception e) {
                    System.out.println("err");
                }
                results2.close();
                ps1.close();

                /*   String sqlquery2=" select (a.amount) as amount1  from fas_receipt_transaction a,fas_receipt_master b where " +
             " a.accounting_unit_id=b.accounting_unit_id and a.accounting_for_office_id=b.accounting_for_office_id " +
             " and a.cashbook_year=b.cashbook_year and a.cashbook_month=b.cashbook_month and " +
             " a.receipt_no=b.receipt_no and b.receipt_status!='C' and " +
             " a.account_head_code=? and " +
             " a.cr_dr_indicator=? and a.cashbook_year=? and a.cashbook_month=? and a.accounting_unit_id=? and a.accounting_for_office_id=? " +
             " union " +
             " select (a.amount) as amount1 from fas_journal_transaction a,fas_journal_master b where " +
             " a.accounting_unit_id=b.accounting_unit_id and a.accounting_for_office_id=b.accounting_for_office_id " +
             " and a.cashbook_year=b.cashbook_year and a.cashbook_month=b.cashbook_month and " +
             " a.voucher_no=b.voucher_no and b.journal_status!='C' and " +
             " a.account_head_code=? and a.cr_dr_indicator=? and " +
             " a.cashbook_year=? and a.cashbook_month=? and a.accounting_unit_id=? and a.accounting_for_office_id=? ";
            ps2=connection.prepareStatement(sqlquery2);
            ps2.setInt(1,achcode);
            ps2.setString(2,"CR");
            ps2.setInt(3,CashBookYear);
            ps2.setInt(4,CashBookMonth);
            ps2.setInt(5,AccountUnitCode);
            ps2.setInt(6,OfficeCode);
            ps2.setInt(7,achcode);
            ps2.setString(8,"CR");
            ps2.setInt(9,CashBookYear);
            ps2.setInt(10,CashBookMonth);
            ps2.setInt(11,AccountUnitCode);
            ps2.setInt(12,OfficeCode);
            results3=ps2.executeQuery();
            if(results3.next()) //Start while(results2.next())
            {
                CRAmount=results3.getDouble("amount1");

                System.out.println("Current Amount is:"+CRAmount);


            }//End while(results2.next())
            results3.close();
            ps2.close();
            String sqlquery3=" select (a.amount)  as amount2 from fas_payment_transaction a,fas_payment_master b where " +
                 " a.accounting_unit_id=b.accounting_unit_id and a.cashbook_year=b.cashbook_year and " +
                 " a.cashbook_month=b.cashbook_month and a.accounting_for_office_id=b.accounting_for_office_id and " +
                 " a.voucher_no=b.voucher_no and b.payment_status!='C' and " +
                 " a.account_head_code=? " +
                 " and a.cr_dr_indicator=? and a.cashbook_year=? and a.cashbook_month=? and a.accounting_unit_id=? " +
                 " and a.accounting_for_office_id=? " +
                 " union " +
                 " select (a.amount) as amount2 from fas_journal_transaction a,fas_journal_master b where " +
                 " a.accounting_unit_id=b.accounting_unit_id and a.accounting_for_office_id=b.accounting_for_office_id " +
                 " and a.cashbook_year=b.cashbook_year and a.cashbook_month=b.cashbook_month and " +
                 " a.voucher_no=b.voucher_no and b.journal_status!='C' and " +
                 " a.account_head_code=? " +
                 " and a.cr_dr_indicator=? and a.cashbook_year=? and a.cashbook_month=? and a.accounting_unit_id=? and a.accounting_for_office_id=? ";
            ps3=connection.prepareStatement(sqlquery3);
            ps3.setInt(1,achcode);
            ps3.setString(2,"DR");
            ps3.setInt(3,CashBookYear);
            ps3.setInt(4,CashBookMonth);
            ps3.setInt(5,AccountUnitCode);
            ps3.setInt(6,OfficeCode);
            ps3.setInt(7,achcode);
            ps3.setString(8,"DR");
            ps3.setInt(9,CashBookYear);
            ps3.setInt(10,CashBookMonth);
            ps3.setInt(11,AccountUnitCode);
            ps3.setInt(12,OfficeCode);
            results4=ps3.executeQuery();
            if(results4.next()) //Start results4.next())
            {
                System.out.println("hai");

                System.out.println("results4 is:"+results4.getDouble("amount2"));
                DRAmount1=results4.getDouble("amount2");

                System.out.println("Dramount is:"+DRAmount1);

            }//End while results4.next()
            results4.close();
            ps3.close();*/


                String sqlquery8 =
                    "select b.receipt_date as vdate,a.received_from as whom,a.particulars as remarks,a.receipt_no as recpno,'R' as doctype,a.amount as amt  from fas_receipt_transaction a,fas_receipt_master b where " +
                    " a.accounting_unit_id=b.accounting_unit_id and a.accounting_for_office_id=b.accounting_for_office_id " +
                    " and a.cashbook_year=b.cashbook_year and a.cashbook_month=b.cashbook_month and " +
                    " a.receipt_no=b.receipt_no and b.receipt_status!='C' and " +
                    " a.account_head_code=? and " +
                    " a.cr_dr_indicator=? and a.cashbook_year=? and a.cashbook_month=? and a.accounting_unit_id=? and a.accounting_for_office_id=? " +
                    " union " +
                    " select  b.voucher_date as vdate,'' as whom,a.particulars as remarks,a.voucher_no as recpno,'J' as doctype,a.amount as amt from fas_journal_transaction a,fas_journal_master b where " +
                    " a.accounting_unit_id=b.accounting_unit_id and a.accounting_for_office_id=b.accounting_for_office_id " +
                    " and a.cashbook_year=b.cashbook_year and a.cashbook_month=b.cashbook_month and " +
                    " a.voucher_no=b.voucher_no and b.journal_status!='C' and " +
                    " a.account_head_code=? and a.cr_dr_indicator=? and " +
                    " a.cashbook_year=? and a.cashbook_month=? and a.accounting_unit_id=? and a.accounting_for_office_id=? " +
                    " union " +
                    " select b.payment_date as vdate,'' as whom,a.particulars as remarks,a.voucher_no as recpno,'P' as doctype,a.amount as amt from fas_payment_transaction a,fas_payment_master b where " +
                    " a.accounting_unit_id=b.accounting_unit_id and a.cashbook_year=b.cashbook_year and " +
                    " a.cashbook_month=b.cashbook_month and a.accounting_for_office_id=b.accounting_for_office_id and " +
                    " a.voucher_no=b.voucher_no and b.payment_status!='C' and " +
                    " a.account_head_code=? " +
                    " and a.cr_dr_indicator=? and a.cashbook_year=? and a.cashbook_month=? and a.accounting_unit_id=? " +
                    " and a.accounting_for_office_id=? " + " union " +
                    " select b.voucher_date as vdate,'' as whom,a.particulars as remarks,a.voucher_no as recpno,'J' as doctype,a.amount as amt from fas_journal_transaction a,fas_journal_master b where " +
                    " a.accounting_unit_id=b.accounting_unit_id and a.accounting_for_office_id=b.accounting_for_office_id " +
                    " and a.cashbook_year=b.cashbook_year and a.cashbook_month=b.cashbook_month and " +
                    " a.voucher_no=b.voucher_no and b.journal_status!='C' and " +
                    " a.account_head_code=? " +
                    " and a.cr_dr_indicator=? and a.cashbook_year=? and a.cashbook_month=? and a.accounting_unit_id=? and a.accounting_for_office_id=? ";
                ps2 = connection.prepareStatement(sqlquery8);
                ps2.setInt(1, achcode);
                ps2.setString(2, "CR");
                ps2.setInt(3, CashBookYear);
                ps2.setInt(4, CashBookMonth);
                ps2.setInt(5, AccountUnitCode);
                ps2.setInt(6, OfficeCode);
                ps2.setInt(7, achcode);
                ps2.setString(8, "CR");
                ps2.setInt(9, CashBookYear);
                ps2.setInt(10, CashBookMonth);
                ps2.setInt(11, AccountUnitCode);
                ps2.setInt(12, OfficeCode);
                ps2.setInt(13, achcode);
                ps2.setString(14, "DR");
                ps2.setInt(15, CashBookYear);
                ps2.setInt(16, CashBookMonth);
                ps2.setInt(17, AccountUnitCode);
                ps2.setInt(18, OfficeCode);
                ps2.setInt(19, achcode);
                ps2.setString(20, "DR");
                ps2.setInt(21, CashBookYear);
                ps2.setInt(22, CashBookMonth);
                ps2.setInt(23, AccountUnitCode);
                ps2.setInt(24, OfficeCode);
                results3 = ps2.executeQuery();

                remarks = "";
                recpno = 0;
                doctype = "";
                amt = 0.0;
                whom = "";
                vdate = null;
                while (results3.next()) {
                    remarks = results3.getString("remarks");
                    System.out.println("remarks is:" + remarks);
                    recpno = results3.getInt("recpno");
                    System.out.println("receipt no is:" + recpno);
                    doctype = results3.getString("doctype");
                    System.out.println("doctype is:" + doctype);
                    amt = results3.getDouble("amt");
                    System.out.println("amt is:" + amt);

                    whom = results3.getString("whom");
                    System.out.println("Recieved from:" + whom);
                    vdate = results3.getDate("vdate");
                    System.out.println("date:" + vdate);


                    String sqlquery2 =
                        " select (a.amount) as amount1,a.receipt_no as rno  from fas_receipt_transaction a,fas_receipt_master b where " +
                        " a.accounting_unit_id=b.accounting_unit_id and a.accounting_for_office_id=b.accounting_for_office_id " +
                        " and a.cashbook_year=b.cashbook_year and a.cashbook_month=b.cashbook_month and " +
                        " a.receipt_no=b.receipt_no and b.receipt_status!='C' and " +
                        " a.account_head_code=? and " +
                        " a.cr_dr_indicator=? and a.cashbook_year=? and a.cashbook_month=? and a.accounting_unit_id=? and a.accounting_for_office_id=?" +
                        " and a.receipt_no=?" + " union " +
                        " select (a.amount) as amount1,a.voucher_no as rno from fas_journal_transaction a,fas_journal_master b where " +
                        " a.accounting_unit_id=b.accounting_unit_id and a.accounting_for_office_id=b.accounting_for_office_id " +
                        " and a.cashbook_year=b.cashbook_year and a.cashbook_month=b.cashbook_month and " +
                        " a.voucher_no=b.voucher_no and b.journal_status!='C' and " +
                        " a.account_head_code=? and a.cr_dr_indicator=? and " +
                        " a.cashbook_year=? and a.cashbook_month=? and a.accounting_unit_id=? and a.accounting_for_office_id=? " +
                        " and a.voucher_no=?";
                    ps2 = connection.prepareStatement(sqlquery2);
                    ps2.setInt(1, achcode);
                    ps2.setString(2, "CR");
                    ps2.setInt(3, CashBookYear);
                    ps2.setInt(4, CashBookMonth);
                    ps2.setInt(5, AccountUnitCode);
                    ps2.setInt(6, OfficeCode);
                    ps2.setInt(7, recpno);
                    ps2.setInt(8, achcode);
                    ps2.setString(9, "CR");
                    ps2.setInt(10, CashBookYear);
                    ps2.setInt(11, CashBookMonth);
                    ps2.setInt(12, AccountUnitCode);
                    ps2.setInt(13, OfficeCode);
                    ps2.setInt(14, recpno);
                    results5 = ps2.executeQuery();

                    CRAmount = 0.0;

                    if (results5.next()) //Start while(results2.next())
                    {
                        CRAmount = results5.getDouble("amount1");

                        System.out.println("Current Amount is:" + CRAmount);


                    } //End while(results2.next())
                    results5.close();
                    ps2.close();


                    String sqlquery3 =
                        " select (a.amount)  as amount2,a.voucher_no as vno from fas_payment_transaction a,fas_payment_master b where " +
                        " a.accounting_unit_id=b.accounting_unit_id and a.cashbook_year=b.cashbook_year and " +
                        " a.cashbook_month=b.cashbook_month and a.accounting_for_office_id=b.accounting_for_office_id and " +
                        " a.voucher_no=b.voucher_no and b.payment_status!='C' and " +
                        " a.account_head_code=? " +
                        " and a.cr_dr_indicator=? and a.cashbook_year=? and a.cashbook_month=? and a.accounting_unit_id=? " +
                        " and a.accounting_for_office_id=? " +
                        " and a.voucher_no=?" + " union " +
                        " select (a.amount) as amount2,a.voucher_no as vno from fas_journal_transaction a,fas_journal_master b where " +
                        " a.accounting_unit_id=b.accounting_unit_id and a.accounting_for_office_id=b.accounting_for_office_id " +
                        " and a.cashbook_year=b.cashbook_year and a.cashbook_month=b.cashbook_month and " +
                        " a.voucher_no=b.voucher_no and b.journal_status!='C' and " +
                        " a.account_head_code=? " +
                        " and a.cr_dr_indicator=? and a.cashbook_year=? and a.cashbook_month=? and a.accounting_unit_id=? and a.accounting_for_office_id=? " +
                        " and a.voucher_no=?";
                    ps3 = connection.prepareStatement(sqlquery3);
                    ps3.setInt(1, achcode);
                    ps3.setString(2, "DR");
                    ps3.setInt(3, CashBookYear);
                    ps3.setInt(4, CashBookMonth);
                    ps3.setInt(5, AccountUnitCode);
                    ps3.setInt(6, OfficeCode);
                    ps3.setInt(7, recpno);
                    ps3.setInt(8, achcode);
                    ps3.setString(9, "DR");
                    ps3.setInt(10, CashBookYear);
                    ps3.setInt(11, CashBookMonth);
                    ps3.setInt(12, AccountUnitCode);
                    ps3.setInt(13, OfficeCode);
                    ps3.setInt(14, recpno);
                    results4 = ps3.executeQuery();

                    DRAmount1 = 0.0;

                    if (results4.next()) //Start results4.next())
                    {
                        System.out.println("hai");

                        System.out.println("results4 is:" +
                                           results4.getDouble("amount2"));
                        DRAmount1 = results4.getDouble("amount2");

                        System.out.println("Dramount is:" + DRAmount1);

                    } //End while results4.next()
                    results4.close();
                    ps3.close();


                    String sqlquery6 =
                        "insert into FAS_A35SCHEDULEREPORT_TMP(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID " +
                        ",CASHBOOK_YEAR,CASHBOOK_MONTH,ACCOUNT_HEAD_CODE,SCHEDULE_ID,PARTICULARS,VOUCHER_NO,DOCUMENT_TYPE,AMOUNT,DOC_DATE,Received_or_Paid,PAmount) " +
                        " values(?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    ps6 = connection.prepareStatement(sqlquery6);
                    ps6.setInt(1, AccountUnitCode);
                    ps6.setInt(2, OfficeCode);
                    ps6.setInt(3, CashBookYear);
                    ps6.setInt(4, CashBookMonth);
                    ps6.setInt(5, achcode);
                    ps6.setString(6, Schedule);
                    ps6.setString(7, remarks);
                    ps6.setInt(8, recpno);
                    ps6.setString(9, doctype);
                    ps6.setDouble(10, CRAmount);
                    ps6.setDate(11, vdate);
                    ps6.setString(12, whom);
                    ps6.setDouble(13, DRAmount1);
                    int ii = ps6.executeUpdate();
                    System.out.println("ii is:" + ii);

                    remarks = "";
                }
                results3.close();
                ps2.close();
            } //End Main While(results.next())
            connection.commit();

            File reportFile = null;
            try {
                System.out.println("calling servlet..." + monthInWords);
                reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/A42SheduleReport_New.jasper"));

                if (!reportFile.exists())
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                System.out.println("from ...");
                JasperReport jasperReport =
                    (JasperReport)JRLoader.loadObject(reportFile.getPath());
                Map map = null;
                map = new HashMap();
                map.put("achcode", achcode);
                map.put("cashbookyear", CashBookYear);
                map.put("cashbookmonth", CashBookMonth);
                map.put("accountingunitid", AccountUnitCode);
                map.put("accountofficeid", OfficeCode);
                map.put("monthvalue", monthInWords);
                map.put("schedule", Schedule);
                map.put("month_ob", month_ob);
                map.put("CRAmount", CRAmount);
                map.put("DRAmount1", DRAmount1);
                System.out.println("from .............");

                JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperReport, map,
                                                 connection);

                System.out.println("upto");
                String rtype = "PDF"; // request.getParameter("cmbReportType");
                System.out.println(rtype);
                /*if (rtype.equalsIgnoreCase("HTML"))
            {
                        response.setContentType("text/html");

                        response.setHeader ("Content-Disposition", "attachment;filename=\"Challan.html\"");
                        PrintWriter out = response.getWriter();
                        JRHtmlExporter exporter = new JRHtmlExporter();
                        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
                        exporter.exportReport();
                        out.flush();
                        out.close();
            }*/
                if (rtype.equalsIgnoreCase("PDF")) {
                    System.out.println(rtype + "...inside PDF");
                    byte buf[] =
                        JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    //response.setHeader ("Content-Disposition", "attachment;filename=\"Challan.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
                }
                /*else if (rtype.equalsIgnoreCase("EXCEL"))
            {

                    response.setContentType("application/vnd.ms-excel");
                     response.setHeader ("Content-Disposition", "attachment;filename=\"Challan.xls\"");
                     JRXlsExporter exporterXLS = new JRXlsExporter();
                     exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);

                    ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
                     exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport);
                     exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
                     exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
                     exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
                     exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
                     exporterXLS.exportReport();
                     byte []bytes;
                    bytes = xlsReport.toByteArray();
                    ServletOutputStream ouputStream = response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();

            }
            else if (rtype.equalsIgnoreCase("TXT"))
            {

                    response.setContentType("text/plain");
                     response.setHeader ("Content-Disposition", "attachment;filename=\"Challan.txt\"");

                JRTextExporter exporter = new JRTextExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,txtReport);
                exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Integer(200));
                exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Integer(50));
                exporter.exportReport();

                     byte []bytes;
                    bytes = txtReport.toByteArray();
                    ServletOutputStream ouputStream = response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();

            }*/

            } catch (Exception ex) {
                String connectMsg =
                    "Could not create the report " + ex.getMessage(); //+ " uu " +  ex.getLocalizedMessage();
                System.out.println(connectMsg);
                //sendMessage(response,"The Challan Report Creation failed","ok");
            }
            //////////////////---------------------------- End -----------------
            System.out.println("here after PDF");
            //sendMessage(response,"The Trial Balance done successfully","ok");
            System.out.println("after send message");


        } catch (Exception e) {
            try {
                connection.rollback();
            } catch (Exception e1) {
                System.out.println("Exception in e:" + e);
            }
            System.out.println("Exception in AccHead from Schedule" + e);
        }


    }
}
