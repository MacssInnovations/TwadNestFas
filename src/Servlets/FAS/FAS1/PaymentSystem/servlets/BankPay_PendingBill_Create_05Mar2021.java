package Servlets.FAS.FAS1.PaymentSystem.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class BankPay_PendingBill_Create_05Mar2021 extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
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

        Connection con = null;
        ResultSet rs = null,rset=null;
        CallableStatement cs = null;
        PreparedStatement ps = null;
        String xml = "";
        String strCommand = "";
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
        if (strCommand.equalsIgnoreCase("Add")) /// ********** txtPay_Vou_type='J'         ***
        {
            String CONTENT_TYPE = "text/html; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>Add</command>";
            Calendar c;
            //  General details
            int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid =
                0, txtCash_year = 0, txtVoucher_No = 0;
            int txtCash_Acc_code = 0, txtBankId = 0, txtBranchId =
                0, Total_TRN_Rec = 0, cmbMas_SL_Code = 0, cmbMas_SL_type =
                0; //,cmbMas_offid=0;
            int txtchallan_No = 0, txtNo_Acq_rolls = 0;
            long txtBankAccountNo = 0;
            double txtAmount = 0, txtPart_Amount = 0;
            String txtPayment_type = "B", txtCR_DB = "", txtPaid_to =
                "", txtPay_Vou_type = "J";
            int txtJournal_type_code = 0;
            String radPart_Amt = "", txtRemarks = "", txtMode_of_creat =
                "M", txtCreat_By_Module = "BPP";
            Date txtCrea_date = null;
            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);


            radPart_Amt = request.getParameter("radPart_Amt");
            System.out.println("radPart_Amt " + radPart_Amt);

            try {
                txtPart_Amount =
                        Double.parseDouble(request.getParameter("txtPart_Amount"));
            } catch (Exception e) {
                System.out.println("exception in part amount.." + e);
            }
            System.out.println("txtPart_Amount " + txtPart_Amount);

            //For Banking Purpose and details
          String hid=request.getParameter("hid");
          String hid_flag=request.getParameter("hid_flag");
          System.out.println("hid_flag >> "+hid_flag);
          
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

            try {
                txtCash_Acc_code =
                        Integer.parseInt(request.getParameter("txtCash_Acc_code"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtCash_Acc_code " + txtCash_Acc_code);

            String[] sd = request.getParameter("txtCrea_date").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
            java.util.Date d = c.getTime();
            txtCrea_date = new Date(d.getTime());
            System.out.println("txtCrea_date " + txtCrea_date);


            try {
                txtVoucher_No =
                        Integer.parseInt(request.getParameter("txtVoucher_No"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtVoucher_No " + txtVoucher_No);

            try {
                txtAmount =
                        Double.parseDouble(request.getParameter("txtAmount"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtAmount " + txtAmount);

            txtCR_DB = request.getParameter("txtCR_DB");
            System.out.println("txtCR_DB " + txtCR_DB);

            try {
                cmbMas_SL_type =
                        Integer.parseInt(request.getParameter("cmbMas_SL_type"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }

            try {
                cmbMas_SL_Code =
                        Integer.parseInt(request.getParameter("cmbMas_SL_Code"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }


            System.out.println("cmbMas_SL and cmbMas_SL_Code " +
                               cmbMas_SL_type + " " +
                               cmbMas_SL_Code); //+" "+cmbMas_offid);


            txtPaid_to = request.getParameter("txtPaid_to");
            System.out.println("txtPaid_to " + txtPaid_to);

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

            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
           

            try {
                con.clearWarnings();
                con.setAutoCommit(false);
                System.out.println("inside proc");
                Boolean AlreadyExist_partpayVouNO = false;
                String No_TRN_Rec[] = request.getParameterValues("H_code");


                //try{txtJournal_type_code=Integer.parseInt(journal_typeCode[0]);}            // to get journal type code
                //catch(Exception e){System.out.println("exception in trans "+e);}

                //int NTR=No_TRN_Rec.length;
                //System.out.println(Total_TRN_Rec+" Total_TRN_Rec"+No_TRN_Rec.length);
                Total_TRN_Rec =
                        No_TRN_Rec.length; //Integer.parseInt(No_TRN_Rec);
                System.out.println(Total_TRN_Rec + " Total_TRN_Rec");
                if(Total_TRN_Rec ==1)
                {
                	hid_flag = "Single";
                }
                else
                {
                	hid_flag = "Multiple";
                }
                System.out.println("hid_flag  sathya>> "+hid_flag);
                //  if(cmbMas_SL_type==9)          //if other departments has chosen for Income Tax for all.. in that case journal type code is 9, but for other cases txtJournal_type_code get from the transaction ( changed as below on 24th apr 07)
             
              /*  // joan add this change bec if no of rec is not mean flag is  Multipe ,if select multiple  or single rec and  any one jrnl amt chnge in multiple payment inthis case only the flag is Multipe otherwise is a Single flag
                 hid_flag=request.getParameter("hid_flag");
                System.out.println(" Important  hid_flag >> "+hid_flag);*/
                txtJournal_type_code = cmbMas_SL_type;
                
                
                String Grid_H_code[] =
                        request.getParameterValues("H_code");
                
                String Grid_vou_type[] =
                        request.getParameterValues("vou_type");
                
                for (int k = 0; k < Grid_H_code.length; k++) {
                	
                	if (Grid_vou_type[k].equalsIgnoreCase("R"))
                    {                		
                		txtPay_Vou_type="R";
                    }
                	else
                	{
                		txtPay_Vou_type="J";
                	}
                	
                }
                
//
//                cs =
//  con.prepareCall("{call FAS_PAYMENT_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
//                cs.setInt(1, cmbAcc_UnitCode);
//                cs.setInt(2, cmbOffice_code);
//                cs.setDate(3, txtCrea_date);
//                cs.setInt(4, txtCash_year);
//                cs.setInt(5, txtCash_Month_hid);
//                cs.setString(6, txtPayment_type);
//                cs.setInt(7, txtVoucher_No);
//                cs.setInt(8, txtCash_Acc_code);
//                cs.setInt(9, txtBankId);
//                cs.setInt(10, txtBranchId);
//                cs.setLong(11, txtBankAccountNo);
//                cs.setInt(12, cmbMas_SL_type);
//                cs.setInt(13, cmbMas_SL_Code);
//                //cs.setInt(14,cmbMas_offid);
//                cs.setString(14, txtCR_DB);
//                cs.setString(15, txtPaid_to);
//                cs.setString(16, txtPay_Vou_type);
//                // cs.setInt(17,txtPay_Vou_No);
//                //cs.setDate(18,txtPay_Vou_date);
//                cs.setInt(17, txtJournal_type_code);
//                cs.setString(18, txtRemarks);
//                cs.setString(19, radPart_Amt);
//                cs.setDouble(20, txtPart_Amount);
//                cs.setInt(21, txtchallan_No);
//                cs.setInt(22, Total_TRN_Rec);
//                cs.setDouble(23, txtAmount);
//                cs.setInt(24, txtNo_Acq_rolls);
//                cs.setString(25, txtMode_of_creat);
//                cs.setString(26, txtCreat_By_Module);
//                cs.setString(27, "insert");
//                cs.registerOutParameter(7, java.sql.Types.NUMERIC);
//                cs.registerOutParameter(28, java.sql.Types.NUMERIC);
//                cs.setString(29, update_user);
//                cs.setTimestamp(30, ts);
//                cs.setInt(31, 0);
//               // cs.setString(32, hid);
//                System.out.println("message b4 exe ");
//               // cs.execute();
                
                cs =
                		  con.prepareCall("call FAS_PAYMENT_MASTER_PROC(?::numeric,?::numeric,?,?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?,?::numeric,?,?,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?,?,?,?,?::numeric)");
                		                cs.setInt(1, cmbAcc_UnitCode);
                		                cs.setInt(2, cmbOffice_code);
                		                cs.setDate(3, txtCrea_date);
                		                cs.setInt(4, txtCash_year);
                		                cs.setInt(5, txtCash_Month_hid);
                		                cs.setString(6, txtPayment_type);
                		                cs.setInt(7, txtVoucher_No);
                		                cs.setInt(8, txtCash_Acc_code);
                		                cs.setInt(9, txtBankId);
                		                cs.setInt(10, txtBranchId);
                		                cs.setLong(11, txtBankAccountNo);
                		                cs.setInt(12, cmbMas_SL_type);
                		                cs.setInt(13, cmbMas_SL_Code);
                		                //cs.setInt(14,cmbMas_offid);
                		                cs.setString(14, txtCR_DB);
                		                cs.setString(15, txtPaid_to);
                		                cs.setString(16, txtPay_Vou_type);
                		                // cs.setInt(17,txtPay_Vou_No);
                		                //cs.setDate(18,txtPay_Vou_date);
                		                cs.setInt(17, txtJournal_type_code);
                		                cs.setString(18, txtRemarks);
                		                cs.setString(19, radPart_Amt);
                		                cs.setDouble(20, txtPart_Amount);
                		                cs.setInt(21, txtchallan_No);
                		                cs.setInt(22, Total_TRN_Rec);
                		                cs.setDouble(23, txtAmount);
                		                cs.setInt(24, txtNo_Acq_rolls);
                		                cs.setString(25, txtMode_of_creat);
                		                cs.setString(26, txtCreat_By_Module);
                		                cs.setString(27, "insert");
                		                cs.registerOutParameter(7, java.sql.Types.NUMERIC);
                		                cs.registerOutParameter(28, java.sql.Types.NUMERIC);
                		                cs.setNull(7, java.sql.Types.NUMERIC);
                		                cs.setNull(28, java.sql.Types.NUMERIC);
                		                cs.setString(29, update_user);
                		                cs.setTimestamp(30, ts);
                		                cs.setInt(31, 0);
                		                System.out.println("b4 exe ");
                		                //cs.execute();
                		                //txtVoucher_No = cs.getInt(7);
//                		                int errcode = cs.getInt(28);
                		                    
               try{
                cs.execute();
                 }catch(Exception e){
            	   System.out.println("b44 exe : error in procedure execute  ");
            	   e.printStackTrace();
               }
               txtVoucher_No =cs.getBigDecimal(7).intValue();
               int errcode = cs.getBigDecimal(28).intValue();      
//                txtVoucher_No = cs.getInt(7); // to be stored in jour5nal master as CB_REF_NO
//                System.out.println("Voucher no sss::::"+txtVoucher_No);
//                int errcode = cs.getInt(28);
                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    System.out.println("redirect");
                    sendMessage(response, "The Bank Payment Creation Failed ",
                                "ok");
                    xml = xml + "<flag>failure</flag>";
                    return;
                } else {
                    /*
                      ****************************************
                     */
                    // To insert into Journal Master details
                    PreparedStatement ps5 = null, ps6 = null, ps7 = null,ps_st=null;
                    ResultSet rs5 = null, rs6 = null;
                    //int yr_journal_trs=0,mon_journal_trs=0;

                    String Grid_pay_vou_number[] =
                        request.getParameterValues("Voucher_no");
                    String Grid_pay_vou_date[] =
                        request.getParameterValues("vou_date");
                    //String Grid_pay_vou_typeCode[]=request.getParameterValues("journal_type_code");

//                    String Grid_vou_type[] =
//                        request.getParameterValues("vou_type"); // Used to find wheather 'R' or 'J' (receipt or journal)
                    String Grid_vouSL_NO_new[] =
                        request.getParameterValues("vouSL_NO_new"); // Used to find the Serial no to be updated in transaction


//                    String Grid_H_code[] =
//                        request.getParameterValues("H_code");
                    String Grid_CR_DR_type[] =
                        request.getParameterValues("CR_DR_type");
                    String Grid_SL_type[] =
                        request.getParameterValues("SL_type");
                    String Grid_SL_code[] =
                        request.getParameterValues("SL_code");

                    // String Grid_Cheque_DD[]=request.getParameterValues("Cheque_DD");
                    //String Grid_Cheque_DD_NO[]=request.getParameterValues("Cheque_DD_NO");
                    //String Grid_Cheque_DD_date[]=request.getParameterValues("Cheque_DD_date");

                    String Grid_Bill_No[] =
                        request.getParameterValues("Bill_NO");
                    String Grid_Bill_date[] =
                        request.getParameterValues("Bill_date");
                    String Grid_Bill_type[] =
                        request.getParameterValues("Bill_type");
                    String Grid_Agree_No[] =
                        request.getParameterValues("Agree_No");
                    String Grid_Agree_date[] =
                        request.getParameterValues("Agree_date");
                    //String Grid_paid_to[]=request.getParameterValues("sub_paid");
                    String Grid_sl_amt[] =
                        request.getParameterValues("sl_amt");
                    String Grid_particular[] =
                        request.getParameterValues("particular");
                    String PVR_DETAILS[] =
                            request.getParameterValues("PVR_DETAILS");
                    String MVR_no[]= request.getParameterValues("MVR_no");
                    System.out.println("MVR_no"+MVR_no);
                    /*@NK included on 1707019*/
                    for(int i=0;i<Grid_sl_amt.length;i++)
                    {
                    	if(Double.parseDouble(Grid_sl_amt[i])<=0)
                    	{
                    		 System.out.println("redirect");
                    		 sendMessage(response, "The Bank Payment Creation Failed ",
                    		                                "ok");
                    		 xml = xml + "<flag>failure</flag>";
                    		 return;
                    	}
                    }
                    
                    /*@NK included on 1707019*/
                    
                    if(hid_flag.equalsIgnoreCase("Single")){
                    	
                    	
                 
                  int  txtAcc_HeadCode = 0, cmbSL_Code =
                          0, cmbSL_type = 0, txtPay_Vou_No = 0;
                      Date txtBill_Date = null, txtAgree_Date =
                          null, txtCheque_DD_date = null, txtPay_Vou_date = null;
                      double txtsub_Amount = 0;
                      String rad_sub_CR_DR = "", txtBill_no = "", txtBill_Type =
                          "", txtAgree_No = "", txtParticular = "";
                      String txtsub_Paid_to = "", txtReference_No = "",pvr_Det="";

                    
                    try{
                     ps_st=con.prepareStatement( "insert into FAS_PAYMENT_TRANSACTION(ACCOUNTING_UNIT_ID, " +
                        "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
                        "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, BILL_NO,BILL_TYPE,AGREEMENT_NO,AGREEMENT_DATE," +
                        "BILL_DATE,BANK_ID,BRANCH_ID,ACCOUNT_NO,CHEQUE_OR_DD ,CHEQUE_DD_NO, CHEQUE_DD_DATE, PAID_TO, " +
                        "AMOUNT, PARTICULARS, PAYABLE_VOUCHER_NO,PAYABLE_VOUCHER_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,REFERENCE_NO,PAYABLE_VOUCHER_SLNO) " +
                        "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                    
                  
                    ps_st.setInt(1, cmbAcc_UnitCode);
                    ps_st.setInt(2, cmbOffice_code);
                    ps_st.setInt(3, txtCash_year);
                    ps_st.setInt(4, txtCash_Month_hid);
                    ps_st.setInt(5, txtVoucher_No);
                    ps_st.setInt(6, 1);
                    ps_st.setInt(7, Integer.parseInt(Grid_H_code[0]));
                    ps_st.setString(8, "DR");
                    ps_st.setInt(9, Integer.parseInt(Grid_SL_type[0]));
                    ps_st.setInt(10, Integer.parseInt(Grid_SL_code[0]));
                    ps_st.setString(11, Grid_Bill_No[0]);
                    ps_st.setString(12, Grid_Bill_type[0]);
                    ps_st.setString(13, Grid_Agree_No[0]);
                    Date txtAgree_Date1=null;
                    if (!Grid_Agree_date[0].equalsIgnoreCase("")) {
                        sd = Grid_Agree_date[0].split("/");
                        c =
new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                     Integer.parseInt(sd[0]));
                        d = c.getTime();
                        txtAgree_Date1 = new Date(d.getTime());
                    }
                    ps_st.setDate(14, txtAgree_Date1);
                    Date txtBill_Date1=null;
                    if (!Grid_Bill_date[0].equalsIgnoreCase("")) {
                        sd = Grid_Bill_date[0].split("/");
                        c =
new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                     Integer.parseInt(sd[0]));
                        d = c.getTime();
                        txtBill_Date1 = new Date(d.getTime());
                    }
                    ps_st.setDate(15, txtBill_Date1);
                    ps_st.setInt(16, txtBankId);
                    ps_st.setInt(17, txtBranchId);
                    ps_st.setLong(18, txtBankAccountNo);
                    ps_st.setString(19, request.getParameter("txtCheque_DD"));
                    ps_st.setString(20, request.getParameter("txtCheque_DD_NO"));
                    Date txtCheque_DD_date1=null;
                    if (!request.getParameter("txtCheque_DD_date").equalsIgnoreCase("")) {
                        sd =
  request.getParameter("txtCheque_DD_date").split("/");
                        c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                        d = c.getTime();
                        txtCheque_DD_date1 = new Date(d.getTime());
                    }

                    ps_st.setDate(21, txtCheque_DD_date1);
                    ps_st.setString(22, "");
                    System.out.println("txtAmount >> > "+txtAmount);
                    ps_st.setDouble(23,txtAmount);
                    ps_st.setString(24, Grid_particular[0]);
                    ps_st.setInt(25, Integer.parseInt(Grid_pay_vou_number[0]));
                    Date txtPay_Vou_date1=null;
                    if (!Grid_pay_vou_date[0].equalsIgnoreCase("")) {
                        sd = Grid_pay_vou_date[0].split("/");
                        c =
new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                     Integer.parseInt(sd[0]));
                        d = c.getTime();
                        txtPay_Vou_date1 = new Date(d.getTime());

                    }
                    System.out.println("Date Befor check");
                    System.out.println(txtPay_Vou_date1);
                    System.out.println(txtCrea_date);
                    if(txtPay_Vou_date1.after(txtCrea_date))
                    {
                    	  System.out.println("redirect");
                    	  con.rollback();
                          sendMessage(response, "The Bank Payment Date should be greater than selected journal date ...  ",
                                      "ok");
                          xml = xml + "<flag>failure</flag>";
                          return;
                    }else{
                    ps_st.setDate(26, txtPay_Vou_date1);
                    }
                    ps_st.setString(27, update_user);
                    ps_st.setTimestamp(28, ts);
                    ps_st.setString(29, ""); 
                    ps_st.setInt(30,Integer.parseInt(Grid_vouSL_NO_new[0]) ); //included by Sathya on 24/12/2016...
                  
                  ps_st.executeUpdate();

                    System.out.println("inserted into payment transaction  "+Grid_H_code.length);

               
                    for (int k = 0; k < Grid_H_code.length; k++) {
                    
                    System.out.println("... vou_type" + Grid_vou_type[k]);
                    System.out.println("... Grid_vouSL_NO_new" +
                                       Grid_vouSL_NO_new[k]);
                   
                    if (!Grid_pay_vou_date[k].equalsIgnoreCase("")) {
                        sd = Grid_pay_vou_date[k].split("/");
                        c =
new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                     Integer.parseInt(sd[0]));
                        d = c.getTime();
                        txtPay_Vou_date = new Date(d.getTime());

                    }
                    int vouSL_NO_new =
                        Integer.parseInt(Grid_vouSL_NO_new[k]);
                    if (Grid_vou_type[k].equalsIgnoreCase("TJV"))
                    {

                        System.out.println("inside journal");

                        String sql_joyrnal =
                            // here note that  SL_NO also  taken into the account to update
                            " update FAS_JOURNAL_TRANSACTION  " +
                            " set CHEQUE_OR_DD=? ,CHEQUE_DD_NO=?,CHEQUE_DD_DATE=? , CB_REF_NO=?, CB_REF_DATE=? , MULTIPLE_PVRS=? , MULTIPLE_PVR_DETAILS=?" +
                            " where  SL_NO=? and ACCOUNT_HEAD_CODE=?   " +
                            " and (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,voucher_NO)= " +
                            "    ( " +
                            "      select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,voucher_NO " +
                            "      from FAS_JOURNAL_MASTER        " +
                            "      where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and voucher_date=? and voucher_NO=? and journal_status!='C'" +
                            "     )";
                        //" and trs.CASHBOOK_YEAR =? and trs.ACCOUNT_HEAD_CODE=? and  trs.SUB_LEDGER_TYPE_CODE=? and trs.SUB_LEDGER_CODE=?";

                        ps5 = con.prepareStatement(sql_joyrnal);
                        ps5.setString(1, request.getParameter("txtCheque_DD"));
                        ps5.setString(2, request.getParameter("txtCheque_DD_NO"));
                       
                        ps5.setDate(3, txtCheque_DD_date1);
                        //ps5.setString(4,"P");
                        ps5.setInt(4, txtVoucher_No);
                        ps5.setDate(5, txtCrea_date);
                     if(MVR_no[k].equalsIgnoreCase("s")){
                        ps5.setString(6, "N");
                        ps5.setString(7, PVR_DETAILS[k]+" - "+txtVoucher_No+"/"+request.getParameter("txtCrea_date"));
                     }else if(MVR_no[k].equalsIgnoreCase("T")){
                         ps5.setString(6, "Y");
                         ps5.setString(7, PVR_DETAILS[k]+" - "+txtVoucher_No+"/"+request.getParameter("txtCrea_date"));
                      }

                        ps5.setInt(8, vouSL_NO_new);
                        ps5.setInt(9, Integer.parseInt(Grid_H_code[k]));
                        ps5.setInt(10, cmbAcc_UnitCode);
                        ps5.setInt(11, cmbOffice_code);
                       
                        ps5.setDate(12, txtPay_Vou_date);
                        ps5.setInt(13, Integer.parseInt(Grid_pay_vou_number[k]));
                        //ps5.setInt(9,mon_journal_trs);
                        //ps5.setInt(10,yr_journal_trs);

                        //ps5.setInt(12,cmbSL_type);
                        // ps5.setInt(13,cmbSL_Code);
                      int result=  ps5.executeUpdate();
                        ps5.close();
                        System.out.println("insert into journal trans over");

                  String sql_jrlMaster =
                            "update FAS_JOURNAL_MASTER mas set mas.CB_REF_TYPE='P' where" +
                            "  mas.ACCOUNTING_UNIT_ID=? and mas.ACCOUNTING_FOR_OFFICE_ID=? and mas.VOUCHER_NO=? and mas.VOUCHER_DATE=?";

                        ps5 = con.prepareStatement(sql_jrlMaster);
                        ps5.setInt(1, cmbAcc_UnitCode);
                        ps5.setInt(2, cmbOffice_code);
                        ps5.setInt(3, Integer.parseInt(Grid_pay_vou_number[k]));
                        ps5.setDate(4, txtPay_Vou_date);
                        ps5.executeUpdate();
                        ps5.close();
                  
                        /*---------------*/
                        System.out.println("loop ending..");
                        //yr_journal_trs=0;
                        //mon_journal_trs=0;
                        txtAcc_HeadCode = 0;
                        rad_sub_CR_DR = "";
                        cmbSL_type = 0;
                        cmbSL_Code = 0;
                        txtBill_no = "";
                        txtBill_Type = "";
                        txtBill_Date = null;
                        //txtCheque_DD="";
                        //txtCheque_DD_NO="";
                        //txtCheque_DD_date=null;
                        txtAgree_No = "";
                        txtAgree_Date = null;
                        txtsub_Paid_to = "";
                        txtsub_Amount = 0;
                        txtParticular = "";
                        txtPay_Vou_No = 0;
                        txtPay_Vou_date = null;
                    
                    }else if (Grid_vou_type[k].equalsIgnoreCase("TPJ"))
                    {

                        System.out.println("inside journal");

                        String sql_joyrnal =
                            // here note that  SL_NO also  taken into the account to update
                            " update FAS_JOURNAL_TRANSACTION  " +
                            " set CHEQUE_OR_DD=? ,CHEQUE_DD_NO=?,CHEQUE_DD_DATE=? , CB_REF_NO=?, CB_REF_DATE=? , MULTIPLE_PVRS=? , MULTIPLE_PVR_DETAILS=?" +
                            " where  SL_NO=? and ACCOUNT_HEAD_CODE=?   " +
                            " and (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,voucher_NO)= " +
                            "    ( " +
                            "      select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,voucher_NO " +
                            "      from FAS_JOURNAL_MASTER        " +
                            "      where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and voucher_date=? and voucher_NO=? and journal_status!='C'" +
                            "     )";
                        //" and trs.CASHBOOK_YEAR =? and trs.ACCOUNT_HEAD_CODE=? and  trs.SUB_LEDGER_TYPE_CODE=? and trs.SUB_LEDGER_CODE=?";

                        ps5 = con.prepareStatement(sql_joyrnal);
                        ps5.setString(1, request.getParameter("txtCheque_DD"));
                        ps5.setString(2, request.getParameter("txtCheque_DD_NO"));
                       
                        ps5.setDate(3, txtCheque_DD_date1);
                        //ps5.setString(4,"P");
                        ps5.setInt(4, txtVoucher_No);
                        ps5.setDate(5, txtCrea_date);
                     if(MVR_no[k].equalsIgnoreCase("s")){
                        ps5.setString(6, "N");
                        ps5.setString(7, PVR_DETAILS[k]+" - "+txtVoucher_No+"/"+request.getParameter("txtCrea_date"));
                     }else if(MVR_no[k].equalsIgnoreCase("T")){
                         ps5.setString(6, "Y");
                         ps5.setString(7, PVR_DETAILS[k]+" - "+txtVoucher_No+"/"+request.getParameter("txtCrea_date"));
                      }

                        ps5.setInt(8, vouSL_NO_new);
                        ps5.setInt(9, Integer.parseInt(Grid_H_code[k]));
                        ps5.setInt(10, cmbAcc_UnitCode);
                        ps5.setInt(11, cmbOffice_code);
                       
                        ps5.setDate(12, txtPay_Vou_date);
                        ps5.setInt(13, Integer.parseInt(Grid_pay_vou_number[k]));
                        //ps5.setInt(9,mon_journal_trs);
                        //ps5.setInt(10,yr_journal_trs);

                        //ps5.setInt(12,cmbSL_type);
                        // ps5.setInt(13,cmbSL_Code);
                      int result=  ps5.executeUpdate();
                        ps5.close();
                        System.out.println("insert into journal trans over");

                  String sql_jrlMaster =
                            "update FAS_JOURNAL_MASTER mas set mas.CB_REF_TYPE='P' where" +
                            "  mas.ACCOUNTING_UNIT_ID=? and mas.ACCOUNTING_FOR_OFFICE_ID=? and mas.VOUCHER_NO=? and mas.VOUCHER_DATE=?";

                        ps5 = con.prepareStatement(sql_jrlMaster);
                        ps5.setInt(1, cmbAcc_UnitCode);
                        ps5.setInt(2, cmbOffice_code);
                        ps5.setInt(3, Integer.parseInt(Grid_pay_vou_number[k]));
                        ps5.setDate(4, txtPay_Vou_date);
                        ps5.executeUpdate();
                        ps5.close();
                  
                        /*---------------*/
                        System.out.println("loop ending..");
                        //yr_journal_trs=0;
                        //mon_journal_trs=0;
                        txtAcc_HeadCode = 0;
                        rad_sub_CR_DR = "";
                        cmbSL_type = 0;
                        cmbSL_Code = 0;
                        txtBill_no = "";
                        txtBill_Type = "";
                        txtBill_Date = null;
                        //txtCheque_DD="";
                        //txtCheque_DD_NO="";
                        //txtCheque_DD_date=null;
                        txtAgree_No = "";
                        txtAgree_Date = null;
                        txtsub_Paid_to = "";
                        txtsub_Amount = 0;
                        txtParticular = "";
                        txtPay_Vou_No = 0;
                        txtPay_Vou_date = null;
                    
                    }
                    else if (Grid_vou_type[k].equalsIgnoreCase("LJV") ||
                        Grid_vou_type[k].equalsIgnoreCase("GJV")) {
                        System.out.println("inside journal");

                        String sql_joyrnal =
                            // here note that  SL_NO also  taken into the account to update
                            " update FAS_JOURNAL_TRANSACTION  " +
                            " set CHEQUE_OR_DD=? ,CHEQUE_DD_NO=?,CHEQUE_DD_DATE=? , CB_REF_NO=?, CB_REF_DATE=? , MULTIPLE_PVRS=? , MULTIPLE_PVR_DETAILS=?" +
                            " where  SL_NO=? and ACCOUNT_HEAD_CODE=?   " +
                            " and (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,voucher_NO)= " +
                            "    ( " +
                            "      select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,voucher_NO " +
                            "      from FAS_JOURNAL_MASTER        " +
                            "      where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and voucher_date=? and voucher_NO=? and journal_status!='C'" +
                            "     )";
                        //" and trs.CASHBOOK_YEAR =? and trs.ACCOUNT_HEAD_CODE=? and  trs.SUB_LEDGER_TYPE_CODE=? and trs.SUB_LEDGER_CODE=?";

                        ps5 = con.prepareStatement(sql_joyrnal);
                        ps5.setString(1, request.getParameter("txtCheque_DD"));
                        ps5.setString(2, request.getParameter("txtCheque_DD_NO"));
                       
                        ps5.setDate(3, txtCheque_DD_date1);
                        //ps5.setString(4,"P");
                        ps5.setInt(4, txtVoucher_No);
                        ps5.setDate(5, txtCrea_date);
                     if(MVR_no[k].equalsIgnoreCase("s")){
                        ps5.setString(6, "N");
                        ps5.setString(7, PVR_DETAILS[k]+" - "+txtVoucher_No+"/"+request.getParameter("txtCrea_date"));
                     }else if(MVR_no[k].equalsIgnoreCase("T")){
                         ps5.setString(6, "Y");
                         ps5.setString(7, PVR_DETAILS[k]+" - "+txtVoucher_No+"/"+request.getParameter("txtCrea_date"));
                      }

                        ps5.setInt(8, vouSL_NO_new);
                        ps5.setInt(9, Integer.parseInt(Grid_H_code[k]));
                        ps5.setInt(10, cmbAcc_UnitCode);
                        ps5.setInt(11, cmbOffice_code);
                       
                        ps5.setDate(12, txtPay_Vou_date);
                        ps5.setInt(13, Integer.parseInt(Grid_pay_vou_number[k]));
                        //ps5.setInt(9,mon_journal_trs);
                        //ps5.setInt(10,yr_journal_trs);

                        //ps5.setInt(12,cmbSL_type);
                        // ps5.setInt(13,cmbSL_Code);
                      int result=  ps5.executeUpdate();
                        ps5.close();
                        System.out.println("insert into journal trans over");

                  String sql_jrlMaster =
                            "update FAS_JOURNAL_MASTER mas set mas.CB_REF_TYPE='P' where" +
                            "  mas.ACCOUNTING_UNIT_ID=? and mas.ACCOUNTING_FOR_OFFICE_ID=? and mas.VOUCHER_NO=? and mas.VOUCHER_DATE=?";

                        ps5 = con.prepareStatement(sql_jrlMaster);
                        ps5.setInt(1, cmbAcc_UnitCode);
                        ps5.setInt(2, cmbOffice_code);
                        ps5.setInt(3, Integer.parseInt(Grid_pay_vou_number[k]));
                        ps5.setDate(4, txtPay_Vou_date);
                        ps5.executeUpdate();
                        ps5.close();
                  
                        /*---------------*/
                        System.out.println("loop ending..");
                        //yr_journal_trs=0;
                        //mon_journal_trs=0;
                        txtAcc_HeadCode = 0;
                        rad_sub_CR_DR = "";
                        cmbSL_type = 0;
                        cmbSL_Code = 0;
                        txtBill_no = "";
                        txtBill_Type = "";
                        txtBill_Date = null;
                        //txtCheque_DD="";
                        //txtCheque_DD_NO="";
                        //txtCheque_DD_date=null;
                        txtAgree_No = "";
                        txtAgree_Date = null;
                        txtsub_Paid_to = "";
                        txtsub_Amount = 0;
                        txtParticular = "";
                        txtPay_Vou_No = 0;
                        txtPay_Vou_date = null;
                    } else if (Grid_vou_type[k].equalsIgnoreCase("R")) {
                        System.out.println("inside receipt _single");
                        String sql_receipt =
                            // here note that  SL_NO also  taken into the account to update
                            // here you should not update CHEQUE_OR_DD=? ,CHEQUE_DD_NO=?,CHEQUE_DD_DATE=?
                            " update FAS_RECEIPT_TRANSACTION  " 
                           //not update check_no for receipt trn table  2016-03-09 " set CHEQUE_OR_DD=? ,CHEQUE_DD_NO=?,"
                            + " set CB_REF_NO=?, CB_REF_DATE=?, MULTIPLE_PVRS=? , MULTIPLE_PVR_DETAILS=? " +
                            " where  SL_NO=? and ACCOUNT_HEAD_CODE=?   " +
                            " and (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,RECEIPT_NO)= " +
                            "    ( " +
                            "      select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,RECEIPT_NO " +
                            "      from FAS_RECEIPT_MASTER        " +
                            "      where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_NO=? and RECEIPT_STATUS!='C'" +
                            "     )";
                        ps5 = con.prepareStatement(sql_receipt);
System.out.println(" txtVoucher_No  "+txtVoucher_No+" txtCrea_date "+txtCrea_date+" vouSL_NO_new "+vouSL_NO_new+" txtAcc_HeadCode  "+txtAcc_HeadCode );
System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode +" cmbOffice_code  "+cmbOffice_code+" txtPay_Vou_date "+txtPay_Vou_date+" txtPay_Vou_No "+Integer.parseInt(Grid_pay_vou_number[k]));
                  //  ps5.setString(1, request.getParameter("txtCheque_DD"));
              // ps5.setString(2, request.getParameter("txtCheque_DD_NO"));                     
                  ps5.setInt(1, txtVoucher_No); 
                        ps5.setDate(2, txtCrea_date);
                        if(MVR_no[k].equalsIgnoreCase("s")){
                            ps5.setString(3, "N");
                            ps5.setString(4, PVR_DETAILS[k]+" - "+txtVoucher_No+"/"+request.getParameter("txtCrea_date"));
                         }else if(MVR_no[k].equalsIgnoreCase("T")){
                             ps5.setString(3, "Y");
                             ps5.setString(4, PVR_DETAILS[k]+" - "+txtVoucher_No+"/"+request.getParameter("txtCrea_date"));
                          }
                        ps5.setInt(5, vouSL_NO_new);
                        ps5.setInt(6, Integer.parseInt(Grid_H_code[k]));
                        ps5.setInt(7, cmbAcc_UnitCode);
                        ps5.setInt(8, cmbOffice_code);
                        ps5.setDate(9, txtPay_Vou_date);
                        ps5.setInt(10, Integer.parseInt(Grid_pay_vou_number[k]));
                       
                     int result=   ps5.executeUpdate();
                        ps5.close();
                        if(result > 0){
                        String sql_recMaster =
                            "update FAS_RECEIPT_MASTER mas set mas.CB_REF_TYPE='P' where" +
                            "  mas.ACCOUNTING_UNIT_ID=? and mas.ACCOUNTING_FOR_OFFICE_ID=? and mas.RECEIPT_NO=? and mas.RECEIPT_DATE=?";
System.out.println("sql_recMaster  +"+sql_recMaster);
                        ps5 = con.prepareStatement(sql_recMaster);
                        ps5.setInt(1, cmbAcc_UnitCode);
                        ps5.setInt(2, cmbOffice_code);
                        ps5.setInt(3, Integer.parseInt(Grid_pay_vou_number[k]));
                        ps5.setDate(4, txtPay_Vou_date);
                        ps5.executeUpdate();
                        ps5.close();
                        }
                    }
                    }
                  
               ps_st.close();
                System.out.println("b4 commit");
                con.commit();
                sendMessage(response,
                            "The Bank Payment Voucher Number '" + txtVoucher_No +
                            "' has been Created Successfully ", "ok");
                return;
            
                 
                    } catch(Exception e){
                    	System.out.println("::: Teeeeeest");
                    	e.printStackTrace();
                    }
                    }
                    else if(hid_flag.equalsIgnoreCase("Multiple")){
                    String sql =
                        "insert into FAS_PAYMENT_TRANSACTION(ACCOUNTING_UNIT_ID, " +
                        "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
                        "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, BILL_NO,BILL_TYPE,AGREEMENT_NO,AGREEMENT_DATE," +
                        "BILL_DATE,BANK_ID,BRANCH_ID,ACCOUNT_NO,CHEQUE_OR_DD ,CHEQUE_DD_NO, CHEQUE_DD_DATE, PAID_TO, " +
                        "AMOUNT, PARTICULARS, PAYABLE_VOUCHER_NO,PAYABLE_VOUCHER_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,REFERENCE_NO,PAYABLE_VOUCHER_SLNO) " +
                        "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
                    
                 int SL_NO = 1;

                    int  txtAcc_HeadCode = 0, cmbSL_Code =
                        0, cmbSL_type = 0, txtPay_Vou_No = 0,txtPay_Vou_SlNo=0;
                    Date txtBill_Date = null, txtAgree_Date =
                        null, txtCheque_DD_date = null, txtPay_Vou_date = null;
                    double txtsub_Amount = 0;
                    String rad_sub_CR_DR = "", txtBill_no = "", txtBill_Type =
                        "", txtAgree_No = "", txtParticular = "";
                    String txtCheque_DD = "", txtCheque_DD_NO =
                        "", txtsub_Paid_to = "", txtReference_No = "",pvr_Det="";

                    ps = con.prepareStatement(sql);


                    // Cheque and DD details.......................

                    try {
                        txtCheque_DD = request.getParameter("txtCheque_DD");
                    } catch (Exception e) {
                        System.out.println(e);
                    }

                    try {
                        txtCheque_DD_NO =
                                request.getParameter("txtCheque_DD_NO");
                    } catch (Exception e) {
                        System.out.println(e);
                    }


                    //Grid_Cheque_DD_NO[k];
                    if (!request.getParameter("txtCheque_DD_date").equalsIgnoreCase("")) {
                        sd =
  request.getParameter("txtCheque_DD_date").split("/");
                        c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                        d = c.getTime();
                        txtCheque_DD_date = new Date(d.getTime());
                    }

                    for (int k = 0; k < Grid_H_code.length; k++) {

                        /** Insert Journal Details into Payment Transaction */

                        /** Get Voucher Number */
                        try {
                            txtPay_Vou_No =
                                    Integer.parseInt(Grid_pay_vou_number[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans txtPay_Vou_No" +
                                               e);
                        }

                        try {
                            txtPay_Vou_SlNo =
                                    Integer.parseInt(Grid_vouSL_NO_new[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans txtPay_Vou_No" +
                                               e);
                        }
                        

                        /** Get Voucher Date */
                        if (!Grid_pay_vou_date[k].equalsIgnoreCase("")) {
                            sd = Grid_pay_vou_date[k].split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                            d = c.getTime();
                            txtPay_Vou_date = new Date(d.getTime());

                        }


                        /** Get Account Head Code */
                        try {
                            txtAcc_HeadCode = Integer.parseInt(Grid_H_code[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans " + e);
                        }


                        try {
                            rad_sub_CR_DR = Grid_CR_DR_type[k];

                        } catch (Exception e) {
                            System.out.println("exception in getting cr dr type " +
                                               e);
                        }

                        //   rad_sub_CR_DR="DR" ;     // Grid_CR_DR_type[k];      //**********************  important note this


                        /** Get SubLedger Type */
                        try {
                            cmbSL_type = Integer.parseInt(Grid_SL_type[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans " + e);
                        }


                        /** Get Sub Ledger Code */
                        try {
                            cmbSL_Code = Integer.parseInt(Grid_SL_code[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans " + e);
                        }

                        /** Get Bill Number */
                        txtBill_no = Grid_Bill_No[k];

                        /** Get Bill Type */
                        txtBill_Type = Grid_Bill_type[k];

                        /** Get Bill Date */
                        if (!Grid_Bill_date[k].equalsIgnoreCase("")) {
                            sd = Grid_Bill_date[k].split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                            d = c.getTime();
                            txtBill_Date = new Date(d.getTime());
                        }


                        /** Get Agreement Number */
                        txtAgree_No = Grid_Agree_No[k];
                        if (!Grid_Agree_date[k].equalsIgnoreCase("")) {
                            sd = Grid_Agree_date[k].split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                            d = c.getTime();
                            txtAgree_Date = new Date(d.getTime());
                        }


                        /** Get Amount */
                        //txtsub_Paid_to=Grid_paid_to[k];
                        txtsub_Amount = Double.parseDouble(Grid_sl_amt[k]);
                        /*@NK include on 17072019*/
                        if(txtsub_Amount<=0)
                        {
                        	System.out.println("redirect");
                        	 sendMessage(response, "The Bank Payment Creation Failed ",
                        	                                "ok");
                        	 xml = xml + "<flag>failure</flag>";
                        	 return;
	
                        }
                        /*@NK include on 17072019*/

                        /** Get Particulars */
                        txtParticular = Grid_particular[k];
pvr_Det=PVR_DETAILS[k];
                        System.out.println("txtBill_no..." + txtBill_no);
                        System.out.println("txtBill_Type..." + txtBill_Type);
                        System.out.println("txtBill_Date..." + txtBill_Date);
                        System.out.println("Grid_H_code[k] " + Grid_H_code[k]);
                        System.out.println("Grid_SL_type[k]" +
                                           Grid_SL_type[k]);
                        System.out.println("Grid_SL_code[k]" +
                                           Grid_SL_code[k] + "from here" +
                                           cmbSL_Code);
                        System.out.println(txtCheque_DD + " " +
                                           txtCheque_DD_NO + "  " +
                                           txtCheque_DD_date);
                        System.out.println("amount");
                        System.out.println("Grid_sl_amt[k] " + Grid_sl_amt[k]);
                        System.out.println("Grid_particular[k] " +
                                           Grid_particular[k]);

                        ps.setInt(1, cmbAcc_UnitCode);
                        ps.setInt(2, cmbOffice_code);
                        ps.setInt(3, txtCash_year);
                        ps.setInt(4, txtCash_Month_hid);
                        ps.setInt(5, txtVoucher_No);
                        ps.setInt(6, SL_NO);
                        ps.setInt(7, txtAcc_HeadCode);
                        ps.setString(8, rad_sub_CR_DR);
                        ps.setInt(9, cmbSL_type);
                        ps.setInt(10, cmbSL_Code);
                        ps.setString(11, txtBill_no);
                        ps.setString(12, txtBill_Type);
                        ps.setString(13, txtAgree_No);
                        ps.setDate(14, txtAgree_Date);
                        ps.setDate(15, txtBill_Date);
                        ps.setInt(16, txtBankId);
                        ps.setInt(17, txtBranchId);
                        ps.setLong(18, txtBankAccountNo);
                        ps.setString(19, txtCheque_DD);
                        ps.setString(20, txtCheque_DD_NO);
                        ps.setDate(21, txtCheque_DD_date);
                        ps.setString(22, txtsub_Paid_to);
                        ps.setDouble(23, txtsub_Amount);
                        ps.setString(24, txtParticular);
                        ps.setInt(25, txtPay_Vou_No);
                        if(txtPay_Vou_date.after(txtCrea_date)){

                      	  System.out.println("redirect11111");
                      	  con.rollback();
                            sendMessage(response, "The Bank Payment Date should be greater than selected journal date ...  ",
                                        "ok");
                            xml = xml + "<flag>failure</flag>";
                            return;
                         
                            
                          
                      
                        }else{
                        ps.setDate(26, txtPay_Vou_date);
                        }
                        ps.setString(27, update_user);
                        ps.setTimestamp(28, ts);
                        ps.setString(29, txtReference_No);
                        //ps.setInt(30,txtPay_Vou_SlNo );
                        ps.setInt(30,Integer.parseInt(Grid_vouSL_NO_new[k]) );

                        SL_NO++;
                        ps.executeUpdate();

                        System.out.println("inserted into payment transaction");

                        System.out.println("... vou_type" + Grid_vou_type[k]);
                        System.out.println("... Grid_vouSL_NO_new" +
                                           Grid_vouSL_NO_new[k]);

                        int vouSL_NO_new =
                            Integer.parseInt(Grid_vouSL_NO_new[k]);
                        if (Grid_vou_type[k].equalsIgnoreCase("TJV"))
                        {

                            System.out.println("inside journal");

                            String sql_joyrnal =
                                // here note that  SL_NO also  taken into the account to update
                                " update FAS_JOURNAL_TRANSACTION  " +
                                " set CHEQUE_OR_DD=? ,CHEQUE_DD_NO=?,CHEQUE_DD_DATE=? , CB_REF_NO=?, CB_REF_DATE=? , MULTIPLE_PVRS=? , MULTIPLE_PVR_DETAILS=?" +
                                " where  SL_NO=? and ACCOUNT_HEAD_CODE=?   " +
                                " and (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,voucher_NO)= " +
                                "    ( " +
                                "      select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,voucher_NO " +
                                "      from FAS_JOURNAL_MASTER        " +
                                "      where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and voucher_date=? and voucher_NO=? and journal_status!='C'" +
                                "     )";
                            //" and trs.CASHBOOK_YEAR =? and trs.ACCOUNT_HEAD_CODE=? and  trs.SUB_LEDGER_TYPE_CODE=? and trs.SUB_LEDGER_CODE=?";

                            ps5 = con.prepareStatement(sql_joyrnal);
                            ps5.setString(1, txtCheque_DD);
                            ps5.setString(2, txtCheque_DD_NO);
                            ps5.setDate(3, txtCheque_DD_date);
                            //ps5.setString(4,"P");
                            ps5.setInt(4, txtVoucher_No);
                            ps5.setDate(5, txtCrea_date);
                            // joan Add on 13 oct 2014
                            if(MVR_no[k].equalsIgnoreCase("s")){
                                ps5.setString(6, "N");
                                ps5.setString(7, pvr_Det+" - "+txtVoucher_No+"/"+request.getParameter("txtCrea_date"));}
                            else  if(MVR_no[k].equalsIgnoreCase("T")){
                           ps5.setString(6, "Y");
                            ps5.setString(7, pvr_Det+" - "+txtVoucher_No+"/"+request.getParameter("txtCrea_date"));
                            }
                            ps5.setInt(8, vouSL_NO_new);
                            ps5.setInt(9, txtAcc_HeadCode);
                            ps5.setInt(10, cmbAcc_UnitCode);
                            ps5.setInt(11, cmbOffice_code);
                            ps5.setDate(12, txtPay_Vou_date);
                            ps5.setInt(13, txtPay_Vou_No);
                            //ps5.setInt(9,mon_journal_trs);
                            //ps5.setInt(10,yr_journal_trs);

                            //ps5.setInt(12,cmbSL_type);
                            // ps5.setInt(13,cmbSL_Code);
                            ps5.executeUpdate();
                            ps5.close();
                            System.out.println("insert into journal trans over");

                            String sql_jrlMaster =
                                "update FAS_JOURNAL_MASTER mas set mas.CB_REF_TYPE='P' where" +
                                "  mas.ACCOUNTING_UNIT_ID=? and mas.ACCOUNTING_FOR_OFFICE_ID=? and mas.VOUCHER_NO=? and mas.VOUCHER_DATE=?";

                            ps5 = con.prepareStatement(sql_jrlMaster);
                            ps5.setInt(1, cmbAcc_UnitCode);
                            ps5.setInt(2, cmbOffice_code);
                            ps5.setInt(3, txtPay_Vou_No);
                            ps5.setDate(4, txtPay_Vou_date);
                            ps5.executeUpdate();
                            ps5.close();
                            /*---------------*/
                            System.out.println("loop ending..");
                            //yr_journal_trs=0;
                            //mon_journal_trs=0;
                            txtAcc_HeadCode = 0;
                            rad_sub_CR_DR = "";
                            cmbSL_type = 0;
                            cmbSL_Code = 0;
                            txtBill_no = "";
                            txtBill_Type = "";
                            txtBill_Date = null;
                            //txtCheque_DD="";
                            //txtCheque_DD_NO="";
                            //txtCheque_DD_date=null;
                            txtAgree_No = "";
                            txtAgree_Date = null;
                            txtsub_Paid_to = "";
                            txtsub_Amount = 0;
                            txtParticular = "";
                            txtPay_Vou_No = 0;
                            txtPay_Vou_date = null;
                        
                        }else if (Grid_vou_type[k].equalsIgnoreCase("TPJ"))
                        {

                            System.out.println("inside journal");

                            String sql_joyrnal =
                                // here note that  SL_NO also  taken into the account to update
                                " update FAS_JOURNAL_TRANSACTION  " +
                                " set CHEQUE_OR_DD=? ,CHEQUE_DD_NO=?,CHEQUE_DD_DATE=? , CB_REF_NO=?, CB_REF_DATE=? , MULTIPLE_PVRS=? , MULTIPLE_PVR_DETAILS=?" +
                                " where  SL_NO=? and ACCOUNT_HEAD_CODE=?   " +
                                " and (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,voucher_NO)= " +
                                "    ( " +
                                "      select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,voucher_NO " +
                                "      from FAS_JOURNAL_MASTER        " +
                                "      where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and voucher_date=? and voucher_NO=? and journal_status!='C'" +
                                "     )";
                            //" and trs.CASHBOOK_YEAR =? and trs.ACCOUNT_HEAD_CODE=? and  trs.SUB_LEDGER_TYPE_CODE=? and trs.SUB_LEDGER_CODE=?";

                            ps5 = con.prepareStatement(sql_joyrnal);
                            ps5.setString(1, txtCheque_DD);
                            ps5.setString(2, txtCheque_DD_NO);
                            ps5.setDate(3, txtCheque_DD_date);
                            //ps5.setString(4,"P");
                            ps5.setInt(4, txtVoucher_No);
                            ps5.setDate(5, txtCrea_date);
                            // joan Add on 13 oct 2014
                            if(MVR_no[k].equalsIgnoreCase("s")){
                                ps5.setString(6, "N");
                                ps5.setString(7, pvr_Det+" - "+txtVoucher_No+"/"+request.getParameter("txtCrea_date"));}
                            else  if(MVR_no[k].equalsIgnoreCase("T")){
                           ps5.setString(6, "Y");
                            ps5.setString(7, pvr_Det+" - "+txtVoucher_No+"/"+request.getParameter("txtCrea_date"));
                            }
                            ps5.setInt(8, vouSL_NO_new);
                            ps5.setInt(9, txtAcc_HeadCode);
                            ps5.setInt(10, cmbAcc_UnitCode);
                            ps5.setInt(11, cmbOffice_code);
                            ps5.setDate(12, txtPay_Vou_date);
                            ps5.setInt(13, txtPay_Vou_No);
                            //ps5.setInt(9,mon_journal_trs);
                            //ps5.setInt(10,yr_journal_trs);

                            //ps5.setInt(12,cmbSL_type);
                            // ps5.setInt(13,cmbSL_Code);
                            ps5.executeUpdate();
                            ps5.close();
                            System.out.println("insert into journal trans over");

                            String sql_jrlMaster =
                                "update FAS_JOURNAL_MASTER mas set mas.CB_REF_TYPE='P' where" +
                                "  mas.ACCOUNTING_UNIT_ID=? and mas.ACCOUNTING_FOR_OFFICE_ID=? and mas.VOUCHER_NO=? and mas.VOUCHER_DATE=?";

                            ps5 = con.prepareStatement(sql_jrlMaster);
                            ps5.setInt(1, cmbAcc_UnitCode);
                            ps5.setInt(2, cmbOffice_code);
                            ps5.setInt(3, txtPay_Vou_No);
                            ps5.setDate(4, txtPay_Vou_date);
                            ps5.executeUpdate();
                            ps5.close();
                            /*---------------*/
                            System.out.println("loop ending..");
                            //yr_journal_trs=0;
                            //mon_journal_trs=0;
                            txtAcc_HeadCode = 0;
                            rad_sub_CR_DR = "";
                            cmbSL_type = 0;
                            cmbSL_Code = 0;
                            txtBill_no = "";
                            txtBill_Type = "";
                            txtBill_Date = null;
                            //txtCheque_DD="";
                            //txtCheque_DD_NO="";
                            //txtCheque_DD_date=null;
                            txtAgree_No = "";
                            txtAgree_Date = null;
                            txtsub_Paid_to = "";
                            txtsub_Amount = 0;
                            txtParticular = "";
                            txtPay_Vou_No = 0;
                            txtPay_Vou_date = null;
                        
                        }
                        else  if (Grid_vou_type[k].equalsIgnoreCase("LJV") ||
                            Grid_vou_type[k].equalsIgnoreCase("GJV")) {
                            System.out.println("inside journal");

                            String sql_joyrnal =
                                // here note that  SL_NO also  taken into the account to update
                                " update FAS_JOURNAL_TRANSACTION  " +
                                "set CHEQUE_OR_DD=? ,CHEQUE_DD_NO=?,CHEQUE_DD_DATE=? , CB_REF_NO=?, CB_REF_DATE=? , MULTIPLE_PVRS=? , MULTIPLE_PVR_DETAILS=?" +
                                "where  SL_NO=? and ACCOUNT_HEAD_CODE=?   " +
                                "and (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,voucher_NO)= " +
                                "    ( " +
                                "      select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,voucher_NO " +
                                "      from FAS_JOURNAL_MASTER        " +
                                "      where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and voucher_date=? and voucher_NO=? and journal_status!='C'" +
                                "     )";
                            //" and trs.CASHBOOK_YEAR =? and trs.ACCOUNT_HEAD_CODE=? and  trs.SUB_LEDGER_TYPE_CODE=? and trs.SUB_LEDGER_CODE=?";

                            ps5 = con.prepareStatement(sql_joyrnal);
                            ps5.setString(1, txtCheque_DD);
                            ps5.setString(2, txtCheque_DD_NO);
                            ps5.setDate(3, txtCheque_DD_date);
                            //ps5.setString(4,"P");
                            ps5.setInt(4, txtVoucher_No);
                            ps5.setDate(5, txtCrea_date);
                            // joan Add on 13 oct 2014
                            if(MVR_no[k].equalsIgnoreCase("s")){
                                ps5.setString(6, "N");
                                ps5.setString(7, pvr_Det+" - "+txtVoucher_No+"/"+request.getParameter("txtCrea_date"));}
                            else  if(MVR_no[k].equalsIgnoreCase("T")){
                           ps5.setString(6, "Y");
                            ps5.setString(7, pvr_Det+" - "+txtVoucher_No+"/"+request.getParameter("txtCrea_date"));
                            }
                            ps5.setInt(8, vouSL_NO_new);
                            ps5.setInt(9, txtAcc_HeadCode);
                            ps5.setInt(10, cmbAcc_UnitCode);
                            ps5.setInt(11, cmbOffice_code);
                            ps5.setDate(12, txtPay_Vou_date);
                            ps5.setInt(13, txtPay_Vou_No);
                            //ps5.setInt(9,mon_journal_trs);
                            //ps5.setInt(10,yr_journal_trs);

                            //ps5.setInt(12,cmbSL_type);
                            // ps5.setInt(13,cmbSL_Code);
                            ps5.executeUpdate();
                            ps5.close();
                            System.out.println("insert into journal trans over");

                            String sql_jrlMaster =
                                "update FAS_JOURNAL_MASTER mas set mas.CB_REF_TYPE='P' where" +
                                "  mas.ACCOUNTING_UNIT_ID=? and mas.ACCOUNTING_FOR_OFFICE_ID=? and mas.VOUCHER_NO=? and mas.VOUCHER_DATE=?";

                            ps5 = con.prepareStatement(sql_jrlMaster);
                            ps5.setInt(1, cmbAcc_UnitCode);
                            ps5.setInt(2, cmbOffice_code);
                            ps5.setInt(3, txtPay_Vou_No);
                            ps5.setDate(4, txtPay_Vou_date);
                            ps5.executeUpdate();
                            ps5.close();
                            /*---------------*/
                            System.out.println("loop ending..");
                            //yr_journal_trs=0;
                            //mon_journal_trs=0;
                            txtAcc_HeadCode = 0;
                            rad_sub_CR_DR = "";
                            cmbSL_type = 0;
                            cmbSL_Code = 0;
                            txtBill_no = "";
                            txtBill_Type = "";
                            txtBill_Date = null;
                            //txtCheque_DD="";
                            //txtCheque_DD_NO="";
                            //txtCheque_DD_date=null;
                            txtAgree_No = "";
                            txtAgree_Date = null;
                            txtsub_Paid_to = "";
                            txtsub_Amount = 0;
                            txtParticular = "";
                            txtPay_Vou_No = 0;
                            txtPay_Vou_date = null;
                        } else if (Grid_vou_type[k].equalsIgnoreCase("R")) {
                            System.out.println("inside receipt");
                            
                            try {
								
							
                            	 String sql_receipt =
                                // here note that  SL_NO also  taken into the account to update
                                // here you should not update CHEQUE_OR_DD=? ,CHEQUE_DD_NO=?,CHEQUE_DD_DATE=?
                                " update FAS_RECEIPT_TRANSACTION  " +
                                "set CB_REF_NO=?, CB_REF_DATE=? , MULTIPLE_PVRS=? , MULTIPLE_PVR_DETAILS=? "+
                              // not update in receipt trn table 2016-03-09     + "CHEQUE_OR_DD=? ,CHEQUE_DD_NO=? " +
                                "where  SL_NO=? and ACCOUNT_HEAD_CODE=?   " +
                                "and (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,RECEIPT_NO)= " +
                                "    ( " +
                                "      select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,RECEIPT_NO " +
                                "      from FAS_RECEIPT_MASTER        " +
                                "      where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_NO=? and RECEIPT_STATUS!='C'" +
                                "     )";
                            ps5 = con.prepareStatement(sql_receipt);
                   System.out.println("sql_receipt"+sql_receipt);
                   
                            ps5.setInt(1, txtVoucher_No);
                            ps5.setDate(2, txtCrea_date);
                            if(MVR_no[k].equalsIgnoreCase("s")){
                            	System.out.println("NN");
                                ps5.setString(3, "N");
                                ps5.setString(4, pvr_Det+" - "+txtVoucher_No+"/"+request.getParameter("txtCrea_date"));}
                            else  if(MVR_no[k].equalsIgnoreCase("T")){
                           ps5.setString(3, "Y");
                            ps5.setString(4, pvr_Det+" - "+txtVoucher_No+"/"+request.getParameter("txtCrea_date"));
                            }
                           // ps5.setString(5, txtCheque_DD);
                           // ps5.setString(6, txtCheque_DD_NO);
                            ps5.setInt(5, vouSL_NO_new);
                            ps5.setInt(6, Integer.parseInt(Grid_H_code[k]));
                            ps5.setInt(7, cmbAcc_UnitCode);
                            ps5.setInt(8, cmbOffice_code);
                            ps5.setDate(9, txtPay_Vou_date);
                            ps5.setInt(10, Integer.parseInt(Grid_pay_vou_number[k]));
                            ps5.executeUpdate();
                            System.out.println("sql_receipt Recsult..."+ps5.executeUpdate());
                            ps5.close();
                            String sql_recMaster =
                                "update FAS_RECEIPT_MASTER mas set mas.CB_REF_TYPE='P' where" +
                                "  mas.ACCOUNTING_UNIT_ID=? and mas.ACCOUNTING_FOR_OFFICE_ID=? and mas.RECEIPT_NO=? and mas.RECEIPT_DATE=?";

                            ps5 = con.prepareStatement(sql_recMaster);
                            ps5.setInt(1, cmbAcc_UnitCode);
                            ps5.setInt(2, cmbOffice_code);
                            ps5.setInt(3, Integer.parseInt(Grid_pay_vou_number[k]));
                            ps5.setDate(4, txtPay_Vou_date);
                            ps5.executeUpdate();
                            ps5.close();
                            } catch (Exception e) {
								// TODO: handle exception
                            	System.out.println("Exception e"+e);
							}
     
                        }
                    }
                    ps.close();
                    System.out.println("b4 commit");
                    con.commit();
                    sendMessage(response,
                                "The Bank Payment Voucher Number '" + txtVoucher_No +
                                "' has been Created Successfully ", "ok");
                    return;
                    }
                    
                  
                }///// ELSE PART END

            }

            catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                    System.out.println("finally");
                }
                sendMessage(response, "The Bank Payment Creation Failed ",
                            "ok");
                              
                System.out.println("Exception occur due to " + e);
                return;
            } finally {
                System.out.println("done");
                try {
                    con.setAutoCommit(true);
                } catch (SQLException sqle) {
                    System.out.println("finally");
                }
            }

        }
        
        
        
        
        
        if(strCommand.equalsIgnoreCase("chequeRange")){        	
        	response.setContentType("text/xml");
        	PrintWriter out = response.getWriter();        	
        	response.setHeader("Cache-Control", "no-cache");
        	int chequeNo = Integer.parseInt(request.getParameter("chequeNo"));
        	int accunitId = Integer.parseInt(request.getParameter("accunitId"));
        //	int officeId = Integer.parseInt(request.getParameter("officeId"));
        	long accountNo = Long.parseLong(request.getParameter("accountNo"));
        	
        	//System.out.println("cheq no =="+chequeNo);
        	//System.out.println(accountNo);
        	//System.out.println(chequeNo);
        	//System.out.println(chequeNo);
        	boolean range = false;
        	xml = "";
        	try {
        		String sql = "SELECT ACCOUNTING_UNIT_ID, " +
        		//"  ACCOUNTING_FOR_OFFICE_ID, " +
        		"  ACCOUNT_NO, " +
        		"  START_LEAF_NO, " +
        		"  END_LEAF_NO, " +
        		"  STATUS " +
        		"FROM COM_MST_CHEQUE_BOOKS_SL " +
        		"WHERE ACCOUNTING_UNIT_ID    = ? " +
        		//"AND ACCOUNTING_FOR_OFFICE_ID= ? " +
        		"AND ACCOUNT_NO              = ? " +
        		"AND START_LEAF_NO          <= ? " +
        		"AND END_LEAF_NO            >= ? and STATUS='L'";
                        System.out.println("sql:::::"+sql);
				PreparedStatement preparedStatement = con.prepareStatement(sql);
				preparedStatement.setInt(1, accunitId);
			//	preparedStatement.setInt(2, officeId);				
				preparedStatement.setLong(2, accountNo);
				preparedStatement.setInt(3, chequeNo);
				preparedStatement.setInt(4, chequeNo);
				rs = preparedStatement.executeQuery();
				while(rs.next()){
					range = true;
				}
				if(range){
					xml = "<response><flag>success</flag></response>";
				}else{
					xml = "<response><flag>fail</flag></response>";
				}
			} catch (SQLException e) {				
				e.printStackTrace();
				System.out.println("sql except");
				xml = "<response><flag>fail</flag></response>";
			} catch (Exception e) {				
				System.out.println("exception");
				xml = "<response><flag>fail</flag></response>";
			} 
			System.out.println(" xml "+xml);
        	out.write(xml);
        	out.flush();
        	out.close();
        }
        if(strCommand.equalsIgnoreCase("bankBalUpdate"))
        {
        	
        	response.setContentType("text/xml");
        	PrintWriter out = response.getWriter();        	
        	response.setHeader("Cache-Control", "no-cache");
        	int counted_bank_bal=1;
        	int accunitId = Integer.parseInt(request.getParameter("accunitId"));
        
        	boolean range = false;
        	 xml="<response>";
        	try {
        		 xml=xml+"<command>loadTransferUnit</command>";
        		String bk = "SELECT a.brs_update,  "+
						"  b.bank_bal_count, "+
						  " CASE "+
						  "   WHEN brs_update>=bank_bal_count "+
						  " THEN 'Complete' "+
						  " WHEN brs_update<bank_bal_count "+
						  " THEN 'InComplete' "+
						  " END AS count_tally "+
						  " FROM "+
						  " (SELECT COUNT(*)AS brs_update, "+
						  " ACCOUNTING_UNIT_ID "+
						  " FROM brs_bank_balance_update "+
						  " WHERE ACCOUNTING_UNIT_ID=  "+accunitId+
						  " AND PS_YEAR             = 2013 "+
						  " AND PS_MONTH            =2 "+
						  " GROUP BY ACCOUNTING_UNIT_ID "+
						  " )a "+
						  " INNER JOIN "+
						  " (SELECT COUNT(*)AS bank_bal_count, "+
						  " accounting_unit_id "+
						  " FROM FAS_MST_BANK_BALANCE "+
						  " WHERE ACCOUNTING_UNIT_ID= "+accunitId+
						  "  AND STATUS              ='Y' "+
						  "  GROUP BY ACCOUNTING_UNIT_ID "+
						  "  )b "+
						  " ON a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID";
        		System.out.println("bk:::"+bk);
        		PreparedStatement pres = con.prepareStatement(bk);
			
        		rset = pres.executeQuery();
				while(rset.next()){
					range = true;
					String count_tally=rset.getString("count_tally");
            		if(count_tally.equals("InComplete"))
            		{
            			
            			counted_bank_bal=rset.getInt("brs_update");
            			xml+= "<counted_bank_bal>"+ counted_bank_bal+"</counted_bank_bal>";
            		}
            		else if(count_tally.equals("Complete"))
            		{
            			counted_bank_bal=0;
            			xml+= "<counted_bank_bal>"+ counted_bank_bal+"</counted_bank_bal>";
            		}
				}
				if(range){
					xml += "<flag>success</flag></response>";
				}else{
					xml += "<flag>fail</flag></response>";
				}
			} catch (SQLException e) {				
				e.printStackTrace();
				System.out.println("sql except");
				xml += "<flag>fail</flag></response>";
			} catch (Exception e) {				
				System.out.println("exception");
				xml += "<flag>fail</flag></response>";
			} 
			System.out.println("xml "+xml);
        	out.write(xml);
        	out.flush();
        	out.close();
        }
        if(strCommand.equalsIgnoreCase("a52_verify"))
        {

        	
        	response.setContentType("text/xml");
        	PrintWriter out = response.getWriter();        	
        	response.setHeader("Cache-Control", "no-cache");
        	int counted_bank_bal=1;
        	int accunitId = Integer.parseInt(request.getParameter("accunitId"));
        	int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
        	int asset_cleared=0;
        	
        
        	boolean range = false;
        	 xml="<response>";
        	try {
        		 xml=xml+"<command>a52_verify</command>";
        		String reg = "select f_val,f_qty,hlp_ct from "+
					" (SELECT COUNT(A52_STATUS_VAL)AS f_val,COUNT(A52_STATUS_QTY)     AS f_qty,ACCOUNTING_UNIT_ID as unit_id "+
        		" FROM FAS_A52_AA52_VERIFY_STATUS "+
        		" WHERE ACCOUNTING_UNIT_ID="+accunitId+" group by ACCOUNTING_UNIT_ID)a "+
        		" full outer join "+
        		" (SELECT count(issue_status)as hlp_ct,OFFICE_ID, "+
        		" (select u.accounting_unit_id from fas_mst_acct_units u" +
        		" where u.accounting_unit_office_id=OFFICE_ID)as unitid "+
        		" FROM hlp_issue_requests "+
        		" WHERE OFFICE_ID    = "+cmbOffice_code+
        		" AND (ISSUE_STATUS  ='P' "+
        		" OR ISSUE_STATUS    ='N') "+
        		" AND MAJOR_SYSTEM_ID='FAS' "+
        		" AND MINOR_SYSTEM_ID='FAS11' "+
        		" and issue_reported_date>='26-Aug-2013' "+
        		" and OFFICE_ID!=5000 "+
        		" group by OFFICE_ID)b "+
        		" on a.unit_id=b.unitid";
        		System.out.println("reg:::"+reg);
        		PreparedStatement pres = con.prepareStatement(reg);
        		rset = pres.executeQuery();
				while(rset.next()){ 
					range = true;
					int f_val=rset.getInt("f_val");
					int f_qty=rset.getInt("f_qty");
					int hlp_ct=rset.getInt("hlp_ct");
					System.out.println("f_qty:::"+f_qty);
            		if(f_qty>0)
            		{
            			
            			asset_cleared++;
            			xml+= "<asset_cleared>"+ asset_cleared+"</asset_cleared>";
            			
            			
            		}
            		else if(hlp_ct>0)
            		{
            			asset_cleared=0;
            			xml+= "<asset_cleared>"+ asset_cleared+"</asset_cleared>";
            			
            		}
				}
				if(range){
					
					xml += "<flag>success</flag></response>";
					System.out.println("xml"+xml);
				}else{
					xml += "<flag>fail</flag></response>";
					System.out.println("xml"+xml);
				}
			} catch (SQLException e) {				
				e.printStackTrace();
				System.out.println("sql except");
				xml += "<flag>fail</flag></response>";
			} catch (Exception e) {				
				System.out.println("exception");
				xml += "<flag>fail</flag></response>";
			}       	
        	out.write(xml);
        	out.flush();
        	out.close();
        
        }
    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/MessengerOkBack.jsp?message=" + msg +
                "&button=" + bType;
            response.sendRedirect(url);
            return;
        } catch (IOException e) {
            System.out.println("exception in send message");
        }
       
    }
}

