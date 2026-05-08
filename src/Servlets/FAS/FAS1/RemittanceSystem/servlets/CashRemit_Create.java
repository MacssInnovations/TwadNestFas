package Servlets.FAS.FAS1.RemittanceSystem.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;

import java.sql.PreparedStatement;

import java.sql.ResultSet;

import java.sql.SQLException;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
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

public class CashRemit_Create extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        String strCommand = "";
        Connection con = null;
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
        try {

            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
        if (strCommand.equalsIgnoreCase("Add")) {
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            CallableStatement cs = null;
            PreparedStatement ps = null;
            ResultSet rs = null;
            Calendar c;
            // common to All transaction
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
                0, txtCash_year = 0;
            double txtAmount = 0;
            Date txtCrea_date = null;
            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);
            ////////

            ////  For the purpose of remittance
            int txtCash_Acc_code = 0;
            int txtChallan_No = 0;
            Date txtChallan_Date = null;
            String txtCR_DB = "", txtRemarks = "", txtVerified =
                "N", Remit_type = "C";
            int txtVerified_Auth = 0;
            Date verify_Date = null;

            int txtVoucher_No = 0;
            int txtBankId = 0, txtBranchId = 0;
            long txtBankAccountNo = 0;


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

            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);

            System.out.println("b4 getting month and year");
            try {
                txtCash_year = Integer.parseInt(sd[2]);
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_year " + txtCash_year);

            try {
                txtCash_Month_hid = Integer.parseInt(sd[1]);
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_Month_hid " + txtCash_Month_hid);


            txtChallan_Date =
                    txtCrea_date; // Challan date and Remittance date is same date as creation date only


            try {
                txtCash_Acc_code =
                        Integer.parseInt(request.getParameter("txtCash_Acc_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_Acc_code " + txtCash_Acc_code);
if(txtCash_Acc_code==0)  {
	  sendMessage(response,
              "Head Code Not valid " +txtCash_Acc_code, "ok");
}
	txtCR_DB = request.getParameter("txtCR_DB");
            System.out.println("txtCR_DB " + txtCR_DB);

            try {
                txtBankId =
                        Integer.parseInt(request.getParameter("txtBankId"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtBankId " + txtBankId);

            try {
                txtBranchId =
                        Integer.parseInt(request.getParameter("txtBranchId"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtBranchId " + txtBranchId);

            try {
                txtBankAccountNo =
                        Long.parseLong(request.getParameter("txtBankAccountNo"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtBankAccountNo " + txtBankAccountNo);

            try {
                txtAmount =
                        Double.parseDouble(request.getParameter("txtAmount"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtAmount " + txtAmount);

            txtRemarks = request.getParameter("txtRemarks");
            System.out.println("txtRemarks " + txtRemarks);


            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            /** Get Receipt Creation Date */
            String Receipt_Creation_Date =
                request.getParameter("txtCrea_date");

            /** Call Com_CashBook Servlet for Calculating Cash Book Month and Year */
            Com_CashBook1 cb = new Com_CashBook1();

            /** Assign Cashbook Year and Month to year_month Variable */
            String year_month = cb.cb_date(Receipt_Creation_Date).toString();

            /** Split Cash Book Year and Month */
            String[] ym = year_month.split("/");

            /** Assign Year and Month */
            txtCash_year = Integer.parseInt(ym[0]);
            txtCash_Month_hid = Integer.parseInt(ym[1]);

           

            try {
                System.out.println("start");
                con.clearWarnings();
                con.setAutoCommit(false);
               // cs =
//  con.prepareCall("{call FAS_REMITTANCE_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
//                cs.setInt(1, cmbAcc_UnitCode);
//                cs.setInt(2, cmbOffice_code);
//                cs.setInt(3, txtCash_Month_hid);
//                cs.setInt(4, txtCash_year);
//                cs.setDate(5, txtCrea_date);
//                cs.setString(6, Remit_type);
//                cs.setInt(7, txtChallan_No);
//                cs.setDate(8, txtChallan_Date);
//                cs.setDouble(9, txtAmount);
//                cs.setString(10, txtVerified);
//                cs.setDate(11, verify_Date);
//                cs.setInt(12, txtVerified_Auth);
//                cs.setString(13, txtRemarks);
//                cs.setString(14, update_user);
//                cs.setTimestamp(15, ts);
//                cs.setLong(16, txtBankAccountNo);
//                
//               // if
//                cs.setString(17, "insert");
//                cs.registerOutParameter(7, java.sql.Types.NUMERIC);
//                cs.registerOutParameter(18, java.sql.Types.NUMERIC);
//                cs.execute();
//                System.out.println("reach..1");
//                txtChallan_No = cs.getInt(7);
//                int errcode = cs.getInt(18);
                		 cs =
                		  con.prepareCall("call FAS_REMITTANCE_PROC(?::numeric,?::numeric,?::numeric,?::numeric,?,?,?::numeric,?,?::numeric,?,?,?::numeric,?,?,?,?::numeric,?,?::numeric)");
                		                cs.setInt(1, cmbAcc_UnitCode);
                		                cs.setInt(2, cmbOffice_code);
                		                cs.setInt(3, txtCash_Month_hid);
                		                cs.setInt(4, txtCash_year);
                		                cs.setDate(5, txtCrea_date);
                		                cs.setString(6, Remit_type);
                		                cs.setInt(7, txtChallan_No);
                		                cs.setDate(8, txtChallan_Date);
                		                cs.setDouble(9, txtAmount);
                		                cs.setString(10, txtVerified);
                		                cs.setDate(11, verify_Date);
                		                cs.setInt(12, txtVerified_Auth);
                		                cs.setString(13, txtRemarks);
                		                cs.setString(14, update_user);
                		                cs.setTimestamp(15, ts);
                		                cs.setLong(16, txtBankAccountNo);
                		                
                		                cs.setString(17, "insert");
                		                cs.registerOutParameter(7, java.sql.Types.NUMERIC);
                		                cs.registerOutParameter(18, java.sql.Types.NUMERIC);
                		                //cs.setNull(7, java.sql.Types.NUMERIC);
                		                cs.setNull(18, java.sql.Types.NUMERIC);
                		                cs.execute();

                		                //txtChallan_No = cs.getInt(7);
                		                //int errcode = cs.getInt(18);
                		                 txtChallan_No = cs.getBigDecimal(7).intValue();
                		                int errcode = cs.getBigDecimal(18).intValue();
                cs.close();
                System.out.println("SQLCODE from remittance:::" + errcode);
                System.out.println("challan number..." + txtChallan_No);
                if (errcode != 0) {
                    con.rollback();
                    System.out.println("The Cash Remittance failed to INSERT values in remit table");
                    sendMessage(response, "The Cash Remittance failed ", "ok");

                    return;
                } else {
                    int txtAcc_headOf_CashReceipt = 0;
                    String txtCR_DB_CashReceipt = "";
                    try {
                        txtAcc_headOf_CashReceipt =
                                Integer.parseInt(request.getParameter("txtAcc_headOf_CashReceipt"));
                    } catch (NumberFormatException e) {
                        System.out.println("exception" + e);
                    }
                    System.out.println("txtAcc_headOf_CashReceipt " +
                                       txtAcc_headOf_CashReceipt);

                    txtCR_DB_CashReceipt =
                            request.getParameter("txtCR_DB_CashReceipt");
                    System.out.println("txtCR_DB_CashReceipt " +
                                       txtCR_DB_CashReceipt);

                    System.out.println("reach..2");
//                    cs =
//  con.prepareCall("{call FAS_PAYMENT_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
//                    cs.setInt(1, cmbAcc_UnitCode); // Same  as Remittance
//                    cs.setInt(2, cmbOffice_code); // Same  as Remittance
//                    cs.setDate(3, txtCrea_date); // Same  as Remittance
//                    cs.setInt(4, txtCash_year); // Same  as Remittance
//                    cs.setInt(5, txtCash_Month_hid); // Same  as Remittance
//                    cs.setString(6, "C");
//                    cs.setInt(7, txtVoucher_No);
//                    cs.setInt(8,
//                              txtAcc_headOf_CashReceipt); // It refers Cash Receipt system "ACCOUNT_HEAD_CODE" field
//                    cs.setInt(9, txtBankId); // it comes from form
//                    cs.setInt(10, txtBranchId); // it comes from form
//                    cs.setLong(11, txtBankAccountNo); // it comes from form
//                    cs.setInt(12, 0);
//                    cs.setInt(13, 0);
//                    cs.setString(14, txtCR_DB_CashReceipt); //txtCR_DB
//                    cs.setString(15, ""); // paid_to
//                    cs.setString(16, "R");
//                    //cs.setInt(17,0);
//                    //cs.setDate(18,null);
//                    cs.setInt(17, 0);
//                    cs.setString(18, "");
//                    cs.setString(19, "");
//                    cs.setDouble(20, 0);
//                    cs.setInt(21,
//                              txtChallan_No); // from Remittance table after procedure successfully executed
//                    cs.setInt(22, 1); // No. of transaction recoeds
//                    cs.setDouble(23, txtAmount); // Same  as Remittance amount
//                    cs.setInt(24, 0);
//                    cs.setString(25,
//                                 "A"); // txtMode_of_creat  ...This via Remittance Creation so this set to "Automatic mode"
//                    cs.setString(26,
//                                 "CRM"); // Because it is Cash Remittance type
//                    cs.setString(27, "insert");
//                    cs.registerOutParameter(7, java.sql.Types.NUMERIC);
//                    cs.registerOutParameter(28, java.sql.Types.NUMERIC);
//                    cs.setString(29, update_user);
//                    cs.setTimestamp(30, ts);
//                    cs.setInt(31, 0);
                    cs =
                    		  con.prepareCall("call FAS_PAYMENT_MASTER_PROC(?::numeric,?::numeric,?,?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?,?::numeric,?,?,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?,?,?,?,?::numeric)");
                    		                cs.setInt(1, cmbAcc_UnitCode);
                    		                cs.setInt(2, cmbOffice_code);
                    		                cs.setDate(3, txtCrea_date);
                    		                cs.setInt(4, txtCash_year);
                    		                cs.setInt(5, txtCash_Month_hid);
                    		                cs.setString(6, "C");
                    		                cs.setInt(7, txtVoucher_No);
                    		                cs.setInt(8, txtAcc_headOf_CashReceipt);
                    		                cs.setInt(9, txtBankId);
                    		                cs.setInt(10, txtBranchId);
                    		                cs.setLong(11, txtBankAccountNo);
                    		                cs.setInt(12, 0);
                    		                cs.setInt(13, 0);
                    		                //cs.setInt(14,cmbMas_offid);
                    		                cs.setString(14, txtCR_DB_CashReceipt);
                    		                cs.setString(15, "");
                    		                cs.setString(16, "R");
                    		                // cs.setInt(17,txtPay_Vou_No);
                    		                //cs.setDate(18,txtPay_Vou_date);
                    		                cs.setInt(17, 0);
                    		                cs.setString(18, "");
                    		                cs.setString(19, "");
                    		                cs.setDouble(20, 0);
                    		                cs.setInt(21, txtChallan_No);
                    		                cs.setInt(22, 1);
                    		                cs.setDouble(23, txtAmount);
                    		                cs.setInt(24, 0);
                    		                cs.setString(25, "A");
                    		                cs.setString(26, "CRM");
                    		                cs.setString(27, "insert");
                    		                cs.registerOutParameter(7, java.sql.Types.NUMERIC);
                    		                cs.registerOutParameter(28, java.sql.Types.NUMERIC);
                    		                cs.setNull(7, java.sql.Types.NUMERIC);
                    		                cs.setNull(28, java.sql.Types.NUMERIC);
                    		                cs.setString(29, update_user);
                    		                cs.setTimestamp(30, ts);
                    		                cs.setInt(31, 0);
                    System.out.println("b4 exe ");
                    cs.execute();
                    System.out.println("reach..3");
//                    txtVoucher_No = cs.getInt(7);
//                    int errcode_pay = cs.getInt(28);
                    txtVoucher_No =cs.getBigDecimal(7).intValue();// to be stored in jour5nal master as CB_REF_NO              
                    System.out.println("Voucher no sss::::"+txtVoucher_No);
                     //int errcode = cs.getInt(28);
                    int errcode_pay = cs.getBigDecimal(28).intValue();
                    System.out.println("SQLCODE::: from pay" + errcode_pay);
                    cs.close();
                    if (errcode_pay != 0) {
                        con.rollback();
                        System.out.println("The Cash Remittance failed to INSERT  in payment master");
                        sendMessage(response, " The Cash Remittance failed ",
                                    "ok");
                        return;
                    } else {
                        System.out.println("reach..4");
                        String sql =
                            "insert into FAS_PAYMENT_TRANSACTION(ACCOUNTING_UNIT_ID, " +
                            "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
                            "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, BILL_NO,BILL_TYPE,AGREEMENT_NO,AGREEMENT_DATE," +
                            "BILL_DATE,BANK_ID,BRANCH_ID,ACCOUNT_NO,CHEQUE_OR_DD ,CHEQUE_DD_NO, CHEQUE_DD_DATE, PAID_TO, " +
                            "AMOUNT, PARTICULARS, PAYABLE_VOUCHER_NO,PAYABLE_VOUCHER_DATE,UPDATED_BY_USER_ID,UPDATED_DATE ) " +
                            "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                        int SL_NO = 1, txtAcc_HeadCode = 0, cmbSL_Code =
                            0, cmbSL_type = 0, txtPay_Vou_No = 0;
                        Date txtBill_Date = null, txtAgree_Date =
                            null, txtCheque_DD_date = null, txtPay_Vou_date =
                            null;
                        double txtsub_Amount = 0;
                        String rad_sub_CR_DR = "", txtBill_no =
                            "", txtBill_Type = "", txtAgree_No =
                            "", txtParticular = "";
                        String txtCheque_DD = "", txtCheque_DD_NO =
                            "", txtsub_Paid_to = "";


                        //int txtAcc_headOf_Receipt=0; // it comes from Hidden field of the form "CashRemit_Create.jsp"
                        // try{txtAcc_headOf_Receipt=Integer.parseInt(request.getParameter("txtAcc_headOf_Receipt"));}catch(Exception e){System.out.println("exception in txtAcc_headOf_Receipt getting "+e);}

                        ps = con.prepareStatement(sql); //
                        ps.setInt(1, cmbAcc_UnitCode);
                        ps.setInt(2, cmbOffice_code);
                        ps.setInt(3, txtCash_year);
                        ps.setInt(4, txtCash_Month_hid);
                        ps.setInt(5, txtVoucher_No);
                        ps.setInt(6, SL_NO);
                        ps.setInt(7,
                                  txtCash_Acc_code); // HERE txtCash_Acc_code PASSED
                        ps.setString(8, txtCR_DB); //
                        ps.setInt(9, cmbSL_type);
                        ps.setInt(10, cmbSL_Code);
                        ps.setString(11, txtBill_no);
                        ps.setString(12, txtBill_Type);
                        ps.setString(13, txtAgree_No);
                        ps.setDate(14, txtAgree_Date);
                        ps.setDate(15, txtBill_Date);
                        ps.setInt(16, txtBankId); // it comes from form
                        ps.setInt(17, txtBranchId); // it comes from form
                        ps.setLong(18, txtBankAccountNo); // it comes from form
                        ps.setString(19, txtCheque_DD);
                        ps.setString(20, txtCheque_DD_NO);
                        ps.setDate(21, txtCheque_DD_date);
                        ps.setString(22, txtsub_Paid_to);
                        ps.setDouble(23,
                                     txtAmount); // Same  as Remittance amount
                        ps.setString(24, txtParticular);
                        ps.setInt(25, txtPay_Vou_No);
                        ps.setDate(26, txtPay_Vou_date);
                        ps.setString(27, update_user); // Same  as Remittance
                        ps.setTimestamp(28, ts); // Same  as Remittance
                        SL_NO++;
                        ps.executeUpdate();
                        ps.close();
                        System.out.println("reach..5");

                        String sql_update =
                            "update FAS_RECEIPT_MASTER set CHALLAN_NO=?, CHALLAN_DATE=?, REMITTANCE_STATUS='Y',ACCOUNT_NO=?,BANK_ID=?,BRANCH_ID=? where RECEIPT_TYPE='C' and REMITTANCE_STATUS='N' and CREATED_BY_MODULE='CR' and ACCOUNTING_UNIT_ID=? and  ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_NO=? and RECEIPT_DATE=?";

                        String sel[] = request.getParameterValues("sel");
                        String Rec_No[] = request.getParameterValues("Rec_No");
                        String Rec_Date[] =
                            request.getParameterValues("Rec_Date");
                        int txtRec_No = 0;
                        Date txtRec_Date = null;

                        ps = con.prepareStatement(sql_update);

                        for (int k = 0; k < sel.length; k++) {

                            try {
                                txtRec_No = Integer.parseInt(Rec_No[k]);
                            } catch (Exception e) {
                                System.out.println("exception in trans " + e);
                            }

                            if (!Rec_Date[k].equalsIgnoreCase("")) {
                                sd = Rec_Date[k].split("/");
                                c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                                d = c.getTime();
                                txtRec_Date = new Date(d.getTime());
                            }
                            ps.setInt(1, txtChallan_No);
                            ps.setDate(2, txtChallan_Date);
                            ps.setLong(3,txtBankAccountNo);System.out.println("txtBranchId > > "+txtBranchId);
                            ps.setInt(4,txtBankId);System.out.println("txtBankId > > "+txtBankId);
                            ps.setInt(5,txtBranchId);System.out.println("txtBranchId > > "+txtBranchId);
                            ps.setInt(6, cmbAcc_UnitCode);
                            ps.setInt(7, cmbOffice_code);
                            ps.setInt(8, txtRec_No);
                            ps.setDate(9, txtRec_Date);
                            ps.executeUpdate();
                            txtRec_No = 0;
                            txtRec_Date = null;
                        }
                        ps.close();
                    }
                    System.out.println("last ");
                    con.commit();


                    /////////////////////-------------------------- Print Challan------------------
                    /*
                     File reportFile=null;
                     try
                     {
                     System.out.println("calling servlet...");
                     reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/RemittanceSystem/jasper/CashChallanPrint.jasper"));
                     if (!reportFile.exists())
                     throw new JRRuntimeException("File J not found. The report design must be compiled first.");

                     JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
                     Map map=null;
                     map = new HashMap();
                     CallableStatement cs6=null;
                     PreparedStatement ps6=null;
                     ResultSet rs6=null;
                     String officeName="",bankName="",branchName="",SpellAmount="";        // passed to ireport to generate challan
                     String challanDate="";
                     challanDate=request.getParameter("txtCrea_date");
                        // map.put("acc_unit_id",cmbAcc_UnitCode);
                     ps6=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? ");        // find office name
                     ps6.setInt(1,cmbOffice_code);
                     rs6=ps6.executeQuery();
                     if(rs6.next())
                     officeName =rs6.getString("OFFICE_NAME");
                     ps6.close();
                     rs6.close();
                         ps6=con.prepareStatement("select bk.BANK_NAME,br.BRANCH_NAME from FAS_MST_BANKS bk,FAS_MST_BANK_BRANCHES br where " +
                                                    "bk.BANK_ID=br.BANK_ID and bk.BANK_ID=? and br.BRANCH_ID=? ");    // find bank name and branch name
                         ps6.setInt(1,txtBankId);
                         ps6.setInt(2,txtBranchId);
                         rs6=ps6.executeQuery();
                         if(rs6.next())
                         bankName =rs6.getString("BANK_NAME");
                         branchName=rs6.getString("BRANCH_NAME");
                         ps6.close();
                         rs6.close();
                         System.out.println("upto here..1");
                         // Call a function with one IN parameter; the function returns a VARCHAR
                                cs6= con.prepareCall("{? = call numtochar(?)}");
                                cs6.registerOutParameter(1, java.sql.Types.LONGVARCHAR);
                                cs6.setDouble(2,txtAmount);
                                cs6.execute();
                                SpellAmount = cs6.getString(1);
                        System.out.println("upto here..2");
                        System.out.println(SpellAmount);
                         map.put("bankName",bankName);
                         map.put("branchName",branchName);
                         map.put("txtChallan_No",txtChallan_No);
                         map.put("challanDate",challanDate);
                         map.put("txtBankAccountNo",txtBankAccountNo);
                         map.put("officeName",officeName);
                         map.put("txtAmount",txtAmount);
                         map.put("SpellAmount",SpellAmount);


                         JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map,con);

                         System.out.println("upto");
                     String rtype="PDF";// request.getParameter("cmbReportType");
                     System.out.println(rtype);
                     if (rtype.equalsIgnoreCase("HTML"))
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
                     }
                     else if (rtype.equalsIgnoreCase("PDF"))
                     {       System.out.println(rtype+"...inside PDF");
                                 byte buf[] =
                                   JasperExportManager.exportReportToPdf(jasperPrint);
                                 response.setContentType("application/pdf");
                                 response.setContentLength(buf.length);
                                 //response.setHeader ("Content-Disposition", "attachment;filename=\"Challan.pdf\"");
                                 OutputStream out = response.getOutputStream();
                                 out.write(buf, 0, buf.length);
                                 out.close();
                     }
                     else if (rtype.equalsIgnoreCase("EXCEL"))
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

                     }

                     }
                     catch (Exception ex)
                     {
                     String connectMsg = "Could not create the report " + ex.getMessage();//+ " uu " +  ex.getLocalizedMessage();
                     System.out.println(connectMsg);
                     //sendMessage(response,"The Challan Report Creation failed","ok");
                     }*/
                    //////////////////---------------------------- End -----------------
                    System.out.println("here after PDF");
                    sendMessage(response,
                                "The Cash Remittance done successfully with Challan number " +
                                txtChallan_No, "ok");
                    System.out.println("after send message");
                }

            } catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                    System.out.println("Excep" + sqle);
                }
                sendMessage(response, "The Cash Remittance failed ", "ok");
                System.out.println("Exception occur due to " + e);
            }

            finally {
                System.out.println("done here");
                try {
                    con.setAutoCommit(true);
                } catch (SQLException sqle) {
                    System.out.println("Excep" + sqle);
                }
            }
        }

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection con = null;
        PreparedStatement ps = null,ps2=null;
        ResultSet rs = null,rs2=null;
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

        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String strCommand = "";
        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
            0, txtCash_year = 0;
        Date txtCrea_date = null;

        if (strCommand.equalsIgnoreCase("PendingReceipts")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            Calendar c;
            String xml = "";
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
            xml = "<response><command>PendingReceipts</command>";

            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);

            try {
                txtCash_year = Integer.parseInt(sd[2]);
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_year " + txtCash_year);

            try {
                txtCash_Month_hid = Integer.parseInt(sd[1]);
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_Month_hid " + txtCash_Month_hid);


            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            /** Get Receipt Creation Date */
            String Receipt_Creation_Date =
                request.getParameter("txtCrea_date");

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

            /*
             String[] sp=request.getParameter("txtCrea_date").split("/");
             System.out.println(sp[0]+" "+sp[1]+" "+sp[2]);
             int check_year=Integer.parseInt(sp[2]);                 // to check in while loop
             int check_day=Integer.parseInt(sp[0]);                    // to check in while loop
                         System.out.println(Integer.parseInt(sp[2]));
                         System.out.println("here"+check_year);

              String check_date=request.getParameter("txtCrea_date");
              sp=request.getParameter("txtCrea_date").split("/");
              check_date=sp[2]+"/"+sp[1]+"/"+sp[0];

              System.out.println(check_date); // to check in while loop with d/b date it converted to yyyy/mm/dd form
              try
              {
                  String sql1="select FINANCIAL_YEAR," +
                  "to_char(CB_FROM_DATE_FOR_MARCH,'YYYY/MM/DD') as mar_beg,to_char(CB_TO_DATE_FOR_MARCH,'YYYY/MM/DD') as mar_end ," +
                  "to_char(CB_FROM_DATE_FOR_APRIL,'YYYY/MM/DD') as apr_beg ," +
                  "to_char(CB_TO_DATE_FOR_APRIL,'YYYY/MM/DD') as apr_end ,CB_FROM_DATE_FOR_OTH ,CB_TO_DATE_FOR_OTH  " +
                  "from CASH_BOOK_CONTROL order by FINANCIAL_YEAR";

               // date is taken as string from database in above format for checking with receipt date variable ( check_date is string type)
               // checking of dates performed in form of String checking
               ps=con.prepareStatement(sql1);
               rs=ps.executeQuery();
               int Begin_yr,End_yr;
              while(rs.next())
              {
                  String[] yr=rs.getString("FINANCIAL_YEAR").split("-");
                   Begin_yr=Integer.parseInt(yr[0]);
                   End_yr=Integer.parseInt(yr[1]);
                                   System.out.println("while");
                                   System.out.println(Begin_yr+ " "+End_yr);
                                   System.out.println(rs.getString("mar_beg")+" "+rs.getString("mar_end"));

                  if(check_year==Begin_yr)          //   to check which financial year it belongs
                  {
                      if(txtCash_Month_hid>=4 && txtCash_Month_hid<=12)
                      {
                               System.out.println("if 4");
                               if((check_date.compareToIgnoreCase(rs.getString("mar_beg"))>=0 ) && ( check_date.compareToIgnoreCase(rs.getString("mar_end"))<=0) )
                               {
                                   txtCash_Month_hid=03;
                               System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"mar"+txtCash_Month_hid);
                               }
                               else if((check_date.compareToIgnoreCase(rs.getString("apr_beg"))>=0 ) && (  check_date.compareToIgnoreCase(rs.getString("apr_end"))<=0 ) )
                               {
                                   txtCash_Month_hid=04;
                               System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"apr"+txtCash_Month_hid);
                               }
                               else if(check_day>=rs.getInt("CB_FROM_DATE_FOR_OTH"))
                               {
                                   txtCash_Month_hid=txtCash_Month_hid+1;
                                   if(txtCash_Month_hid>12)
                                       {
                                       txtCash_Month_hid=1;
                                       txtCash_year=txtCash_year+1;
                                       System.out.println("hello"+txtCash_year);
                                       }
                                   System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth1 "+txtCash_Month_hid);
                               }
                               else if(check_day<=rs.getInt("CB_TO_DATE_FOR_OTH"))
                               {
                                  //txtCash_Month_hid=txtCash_Month_hid;
                                  System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth2 "+txtCash_Month_hid);
                               }
                      }

                  }
                  else  if(check_year==End_yr)
                  {
                      if(txtCash_Month_hid>=1 && txtCash_Month_hid<=3)
                      {
                          txtCash_year=End_yr;System.out.println("if 3");
                          if((check_date.compareToIgnoreCase(rs.getString("mar_beg"))>=0 ) && ( check_date.compareToIgnoreCase(rs.getString("mar_end"))<=0) )
                           {
                               txtCash_Month_hid=03;
                           System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"mar"+txtCash_Month_hid);
                           }
                           else if((check_date.compareToIgnoreCase(rs.getString("apr_beg"))>=0 ) && (  check_date.compareToIgnoreCase(rs.getString("apr_end"))<=0 ) )
                           {
                               txtCash_Month_hid=04;
                           System.out.println(check_date.compareToIgnoreCase(rs.getString("mar_beg"))+"apr"+txtCash_Month_hid);
                           }
                           else if(check_day>=rs.getInt("CB_FROM_DATE_FOR_OTH"))
                           {
                               txtCash_Month_hid=txtCash_Month_hid+1;
                               if(txtCash_Month_hid>12)            // No chance for this condition
                               {
                               txtCash_Month_hid=1;
                               txtCash_year=txtCash_year+1;
                               System.out.println("hello"+txtCash_year);
                               }
                               System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth1 "+txtCash_Month_hid);
                           }
                          else if(check_day<=rs.getInt("CB_TO_DATE_FOR_OTH"))
                          {
                              //txtCash_Month_hid=txtCash_Month_hid;
                              System.out.println(rs.getInt("CB_FROM_DATE_FOR_OTH")+"oth2 "+txtCash_Month_hid);
                          }
                      }
                  }
              }
              ps.close();
              rs.close();
              }
              catch(Exception e)
              {
              sendMessage(response," Finding Cash book month failed","ok");
              System.out.println("exception"+e);
              return;
              }

           */


            try {
                /* *
                    ps=con.prepareStatement("select ACCOUNT_HEAD_CODE,RECEIPT_NO,to_char(RECEIPT_DATE,'DD/MM/YYYY') as rec_date," +
                                            "RECEIVED_FROM,trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT from FAS_RECEIPT_MASTER where RECEIPT_TYPE='C' " +
                                            "and REMITTANCE_STATUS='N' and CREATED_BY_MODULE='CR' " +
                                            " and ACCOUNTING_UNIT_ID=? and  ACCOUNTING_FOR_OFFICE_ID=? and  RECEIPT_DATE<=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and RECEIPT_STATUS!='C' order by RECEIPT_NO");
                 */
                ps =
  con.prepareStatement("   select * from \n" + "                    (\n" +
                       "                    select\n" +
                       "                            ACCOUNT_HEAD_CODE,\n" +
                       "                            RECEIPT_NO,\n" +
                       "                            to_char(RECEIPT_DATE,'DD/MM/YYYY') as rec_date,\n" +
                       "                            RECEIVED_FROM,\n" +
                       "                            trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT , \n" +
                       "                            REMITTANCE_IN_CURR_MONTH \n" +
                       "                    from  \n" +
                       "                           FAS_RECEIPT_MASTER   \n" +
                       "                    where  \n" +
                       "                           RECEIPT_TYPE='C'  \n" +
                       "                       and REMITTANCE_STATUS='N'  \n" +
                       "                       and CREATED_BY_MODULE='CR'  \n" +
                       "                       and ACCOUNTING_UNIT_ID= ?   \n" +
                       "                       and ACCOUNTING_FOR_OFFICE_ID= ? \n" +
                       "                       and RECEIPT_DATE<= ?  \n" +
                       "                       and CASHBOOK_YEAR= ?  \n" +
                       "                       and CASHBOOK_MONTH= ?  \n" +
                       "                       and RECEIPT_STATUS!= 'C' \n" +
                       "                       and REMITTANCE_IN_CURR_MONTH='Y' \n" +
                       "                    union all   \n" +
                       "                     \n" +
                       "                    select \n" +
                       "                            ACCOUNT_HEAD_CODE, \n" +
                       "                            RECEIPT_NO, \n" +
                       "                            to_char(RECEIPT_DATE,'DD/MM/YYYY') as rec_date, \n" +
                       "                            RECEIVED_FROM, \n" +
                       "                            trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT ,\n" +
                       "                            REMITTANCE_IN_CURR_MONTH\n" +
                       "                    from \n" +
                       "                           FAS_RECEIPT_MASTER  \n" +
                       "                    where \n" +
                       "                           RECEIPT_TYPE='C'  \n" +
                       "                       and REMITTANCE_STATUS='N'  \n" +
                       "                       and CREATED_BY_MODULE='CR'  \n" +
                       "                       and ACCOUNTING_UNIT_ID= ?  \n" +
                       "                       and ACCOUNTING_FOR_OFFICE_ID= ? \n" +
                       "                       and RECEIPT_DATE<= ?  \n" +
                     //  "                       and CASHBOOK_MONTH < ? \n" +
                       "                       and CASHBOOK_YEAR <= ? \n" +
                       "                       and RECEIPT_STATUS !='C'  \n" +
                       "                       and REMITTANCE_IN_CURR_MONTH ='N'  \n" +
                       "                    )as ps \n" +
                       "                    order by RECEIPT_NO  ");

                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setDate(3, txtCrea_date);
                ps.setInt(4, txtCash_year);
                ps.setInt(5, txtCash_Month_hid);

                ps.setInt(6, cmbAcc_UnitCode);
                ps.setInt(7, cmbOffice_code);
                ps.setDate(8, txtCrea_date);
                //ps.setInt(9, txtCash_Month_hid);
                ps.setInt(9, txtCash_year);


                rs = ps.executeQuery();
                int cnt = 0;
                double amt = 0.0;
                xml = xml + "<flag>success</flag>";
                while (rs.next()) {
                    xml =
 xml + "<Rec_No>" + rs.getInt("RECEIPT_NO") + "</Rec_No>" + "<Rec_Date>" +
   rs.getString("rec_date") + "</Rec_Date>" + "<Rec_From><![CDATA[" +
   rs.getString("RECEIVED_FROM") + "]]></Rec_From>" + "<Total_amt>" +
   rs.getString("TOTAL_AMOUNT") + "</Total_amt>";
                    //amt=amt+rs.getDouble("TOTAL_AMOUNT");
                    cnt++;
                }

                // xml=xml+"<Remitted_amount>"+amt+"</Remitted_amount>";
                System.out.println("count" + cnt);
                if (cnt == 0)
                    xml =
 "<response><command>PendingReceipts</command>" + "<flag>failure</flag>";
                //else
                // xml=xml+"<Acc_headOf_Receipt>"+rs.getInt("ACCOUNT_HEAD_CODE")+"</Acc_headOf_Receipt>"+"<Remitted_amount>"+amt+"</Remitted_amount>";
                // use of ACCOUNT_HEAD_CODE is , used to insert into payment transaction table as ACCOUNT_HEAD_CODE taken from receipt system,
                // after insert into remittance followed by pay master table..
            } catch (Exception e) {
                System.out.println("catch..HERE.in failure to retrieve." + e);
                xml =
 "<response><command>PendingReceipts</command>" + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }
        //added to check the REMITTANCE DATE is A HOLIDAY or NOT.............
        else if (strCommand.equalsIgnoreCase("CheckHoliday")) 
        {
            System.out.println("inside CheckHoliday");
        	String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            Calendar c;
            String xml = "";
            Date remit_date1=null;
            xml = "<response><command>CheckHoliday</command>";
//            String remit_date=request.getParameter("remitdate");
           int cmbAcc_UnitCode1 = 0, cmbOffice_code1 = 0;
            String[] sd = request.getParameter("remitdate").split("/");
            c =
        new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            remit_date1 = new Date(d.getTime());
            System.out.println("Remittace Date **** " + remit_date1);
           try
           {
               cmbAcc_UnitCode1=Integer.parseInt(request.getParameter("acc_unitid"));
           }
           catch(Exception e)
           {
               System.out.println("Error in getting acc unit id**"+e);
           }
           try {
               cmbOffice_code1=Integer.parseInt(request.getParameter("acc_officeid"));
           }
           catch(Exception e)
           {
               System.out.println("Error in getting Office id**"+e);
           }

            try {
                txtCash_year = Integer.parseInt(sd[2]);
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_year " + txtCash_year);

            try {
                txtCash_Month_hid = Integer.parseInt(sd[1]);
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_Month_hid " + txtCash_Month_hid);


            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

            /** Get Receipt Creation Date */
//            String Remit_Creation_Date =
//                request.getParameter("remitdate");

            /** Call Com_CashBook Servlet for Calculating Cash Book Month and Year */
//            Com_CashBook1 cb = new Com_CashBook1();

            /** Assign Cashbook Year and Month to year_month Variable */
           // String year_month = cb.cb_date(Remit_Creation_Date).toString();

            /** Split Cash Book Year and Month */
           // String[] ym = year_month.split("/");

            /** Assign Year and Month */
//            txtCash_year = Integer.parseInt(ym[0]);
//            txtCash_Month_hid = Integer.parseInt(ym[1]);

            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
             xml = xml + "<flag>success</flag>";
             System.out.println("Before Checking the Holidays");
            try {
                ps =con.prepareStatement("select to_char(HOLIDAY_DATE,'DD/MM/YYYY') as HOLIDAY_DATE,REASON from fas_common_holidays_list where cashbook_year=? " + 
                    "and cashbook_month =? and HOLIDAY_DATE=?");
                ps.setInt(1, txtCash_year);
                ps.setInt(2, txtCash_Month_hid);
                ps.setDate(3, remit_date1);
                rs = ps.executeQuery();
                int cnt = 0;
                
                if (rs.next()) 
                {
                                xml =
                                       xml +  "<Holiday_Date>" +rs.getString("HOLIDAY_DATE") + "</Holiday_Date>" + "<Reason>" +
                                       rs.getString("REASON") + "</Reason>";
                                
                   cnt++;
                }
                System.out.println("count of common holidays" + cnt);
                if (cnt == 0)
                {
                    ps2 =con.prepareStatement("select to_char(HOLIDAY_DATE,'DD/MM/YYYY') as HOLIDAY_DATE,REASON from fas_holidays_list where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and cashbook_year=? " + 
                        "and cashbook_month =? and HOLIDAY_DATE=?");
                    ps2.setInt(1, cmbAcc_UnitCode1);
                    ps2.setInt(2, cmbOffice_code1);
                    ps2.setInt(3, txtCash_year);
                    ps2.setInt(4, txtCash_Month_hid);
                    ps2.setDate(5, remit_date1);
                    rs2 = ps2.executeQuery();
                    int localholidayscount=0;
                    if (rs2.next()) 
                    {
                                    xml =
                                           xml + "<Holiday_Date>" +rs2.getString("HOLIDAY_DATE") + "</Holiday_Date>" + "<Reason>" +
                                           rs2.getString("REASON") + "</Reason>";
                        localholidayscount++;
                    }
                    System.out.println("Count of local Holidays***"+localholidayscount);
                }
                   
                   // xml ="<response><command>CheckHoliday</command>" + "<flag>failure</flag>";
                //else
                // xml=xml+"<Acc_headOf_Receipt>"+rs.getInt("ACCOUNT_HEAD_CODE")+"</Acc_headOf_Receipt>"+"<Remitted_amount>"+amt+"</Remitted_amount>";
                // use of ACCOUNT_HEAD_CODE is , used to insert into payment transaction table as ACCOUNT_HEAD_CODE taken from receipt system,
                // after insert into remittance followed by pay master table..
            } catch (Exception e) 
            {
                System.out.println("catch..HERE.in failure to retrieve." + e);
                xml ="<response><command>CheckHoliday</command>" + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }


    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            System.out.println("inside send msg");
            String url =
                "org/Library/jsps/MessengerOkBack.jsp?message=" + msg +
                "&button=" + bType;
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println("Excep" + e);
        }
    }
}
