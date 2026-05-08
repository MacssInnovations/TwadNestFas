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
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class BankPay_NilPayment_Create extends HttpServlet {
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
        ResultSet rs = null;
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


        if (strCommand.equalsIgnoreCase("Add")) {
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
                "", txtPay_Vou_type = "";
            int txtJournal_code = 0;
            String radPart_Amt = "", txtRemarks = "", txtMode_of_creat =
                "M", txtCreat_By_Module = "NP";
            Date txtCrea_date = null;
            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);

            //For Banking Purpose and details


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

            /*  try{txtAmount=Double.parseDouble(request.getParameter("txtAmount"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtAmount "+txtAmount);*/

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

            /*
            System.out.println("b4 getting month and year");
            try{txtCash_year=Integer.parseInt(sd[2]);}
                        catch(Exception e){System.out.println("exception"+e );}
                        System.out.println("txtCash_year "+txtCash_year);

                        try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
                        catch(Exception e){System.out.println("exception"+e );}
                        System.out.println("txtCash_Month_hid "+txtCash_Month_hid);


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
            }
            ps.close();
            rs.close();
            }
            catch(Exception e)
            {
            sendMessage(response,"The Cash Book Period Not Found ","ok");
            System.out.println("exception"+e);
            }

            */


            try {
                con.clearWarnings();
                con.setAutoCommit(false);
                System.out.println("inside proc");
                String No_TRN_Rec[] = request.getParameterValues("H_code");
                //int NTR=No_TRN_Rec.length;
                //System.out.println(Total_TRN_Rec+" Total_TRN_Rec"+No_TRN_Rec.length);
                Total_TRN_Rec =
                        No_TRN_Rec.length; //Integer.parseInt(No_TRN_Rec);
                System.out.println(Total_TRN_Rec + " Total_TRN_Rec");
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
//                cs.setInt(17, txtJournal_code);
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
//                System.out.println("b4 exe ");
//                cs.execute();
//                txtVoucher_No = cs.getInt(7);
//                int errcode = cs.getInt(28);
                
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
                		                cs.setInt(17, txtJournal_code);
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
                		                cs.execute();
                		                //txtVoucher_No = cs.getInt(7);
//                		                int errcode = cs.getInt(28);
                		                txtVoucher_No =cs.getBigDecimal(7).intValue();
                		                int errcode = cs.getBigDecimal(28).intValue();                
                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    System.out.println("redirect");
                    sendMessage(response, "The Bank Payment Creation Failed ",
                                "ok");
                    xml = xml + "<flag>failure</flag>";
                } else {
                    String Grid_H_code[] =
                        request.getParameterValues("H_code");
                    String Grid_CR_DR_type[] =
                        request.getParameterValues("CR_DR_type");
                    String Grid_SL_type[] =
                        request.getParameterValues("SL_type");
                    String Grid_SL_code[] =
                        request.getParameterValues("SL_code");
                    String Grid_Bill_No[] =
                        request.getParameterValues("Bill_NO");
                    String Grid_Bill_date[] =
                        request.getParameterValues("Bill_date");
                    String Grid_Bill_type[] =
                        request.getParameterValues("Bill_type");
                    // String Grid_paid_to[]=request.getParameterValues("sub_paid");
                    String Grid_referenceNo[] =
                        request.getParameterValues("referenceNo");
                    String Grid_particular[] =
                        request.getParameterValues("particular");

                    String sql =
                        "insert into FAS_PAYMENT_TRANSACTION(ACCOUNTING_UNIT_ID, " +
                        "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
                        "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, BILL_NO,BILL_TYPE,AGREEMENT_NO,AGREEMENT_DATE," +
                        "BILL_DATE,BANK_ID,BRANCH_ID,ACCOUNT_NO,CHEQUE_OR_DD ,CHEQUE_DD_NO, CHEQUE_DD_DATE, PAID_TO, " +
                        "AMOUNT, PARTICULARS, PAYABLE_VOUCHER_NO,PAYABLE_VOUCHER_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,REFERENCE_NO) " +
                        "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


                    int SL_NO = 1, txtAcc_HeadCode = 0, cmbSL_Code =
                        0, cmbSL_type = 0, txtPay_Vou_No = 0;
                    Date txtBill_Date = null, txtAgree_Date =
                        null, txtCheque_DD_date = null, txtPay_Vou_date = null;
                    double txtsub_Amount = 0;
                    String rad_sub_CR_DR = "", txtBill_no = "", txtBill_Type =
                        "", txtAgree_No = "", txtParticular = "";
                    String txtCheque_DD = "", txtCheque_DD_NO =
                        "", txtsub_Paid_to = "", txtReference_No = "";

                    ps = con.prepareStatement(sql);
                    for (int k = 0; k < Grid_H_code.length; k++) {
                        try {
                            txtAcc_HeadCode = Integer.parseInt(Grid_H_code[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans " + e);
                        }
                        rad_sub_CR_DR = Grid_CR_DR_type[k];

                        try {
                            cmbSL_type = Integer.parseInt(Grid_SL_type[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans " + e);
                        }
                        try {
                            cmbSL_Code = Integer.parseInt(Grid_SL_code[k]);
                        } catch (Exception e) {
                            System.out.println("exception in trans " + e);
                        }

                        txtBill_no = Grid_Bill_No[k];

                        if (!Grid_Bill_date[k].equalsIgnoreCase("")) {
                            sd = Grid_Bill_date[k].split("/");
                            c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                            d = c.getTime();
                            txtBill_Date = new Date(d.getTime());
                        }
                        txtBill_Type = Grid_Bill_type[k];

                        // txtsub_Paid_to=Grid_paid_to[k];
                        txtReference_No = Grid_referenceNo[k];
                        txtParticular = Grid_particular[k];

                        System.out.println("Grid_H_code[k] " + Grid_H_code[k]);
                        System.out.println("Grid_CR_DR_type[k] " +
                                           Grid_CR_DR_type[k]);
                        System.out.println("Grid_SL_type[k]" +
                                           Grid_SL_type[k]);
                        System.out.println("Grid_SL_code[k]" +
                                           Grid_SL_code[k] + "from here" +
                                           cmbSL_Code);
                        System.out.println("txtBill_no..." + txtBill_no);
                        System.out.println("txtBill_Type..." + txtBill_Type);
                        System.out.println("txtBill_Date..." + txtBill_Date);
                        System.out.println("txtReference_No..." +
                                           txtReference_No);
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
                        ps.setDate(26, txtPay_Vou_date);
                        ps.setString(27, update_user);
                        ps.setTimestamp(28, ts);
                        ps.setString(29, txtReference_No);
                        SL_NO++;
                        ps.executeUpdate();

                        txtAcc_HeadCode = 0;
                        rad_sub_CR_DR = "";
                        cmbSL_type = 0;
                        cmbSL_Code = 0;
                        txtCheque_DD = "";
                        txtCheque_DD_NO = "";
                        txtCheque_DD_date = null;
                        txtAgree_No = "";
                        txtAgree_Date = null;
                        txtsub_Paid_to = "";
                        txtsub_Amount = 0;
                        txtReference_No = "";
                        txtParticular = "";
                    }
                    ps.close();
                    System.out.println("b4 commit");
                    con.commit();
                    sendMessage(response,
                                "The Bank Payment Voucher Number '" + txtVoucher_No +
                                "' has been Created Successfully ", "ok");
                }

            }

            catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                }
                sendMessage(response, "The Bank Payment Creation Failed ",
                            "ok");
                System.out.println("Exception occur due to " + e);
            } finally {
                System.out.println("done");
                try {
                    con.setAutoCommit(true);
                } catch (SQLException sqle) {
                }
            }

        }
    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/MessengerOkBack.jsp?message=" + msg +
                "&button=" + bType;
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println("error in send message");
        }
    }

}
