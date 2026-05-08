package Servlets.FAS.FAS1.SelfChequeSystem.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

import java.io.IOException;


import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import java.util.Vector;

import javax.servlet.*;
import javax.servlet.http.*;

public class SelfCheque_Edit extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        String strCommand = "";
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null, cs1 = null;
        PreparedStatement ps = null;
        String xml = "";

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

            ///********************* drawl date ***********************
            Date drawlDate = null;

            String vou_numbers =
                ""; //******************  to display info about Voucher numbers after payment made thru sendMessage()
            String Real_acq_display =
                ""; //******************  to display info about Acq numbers after Acq.Transaction updated

            Calendar c;
            int txtAcc_HeadCode = 0, cmbAcc_UnitCode = 0, cmbOffice_code =
                0, txtCash_Month_hid = 0, txtCash_year = 0, txtReceipt_No = 0;
            int txtCash_Acc_code = 820101, Total_TRN_Rec = 0;
            double txtAmount = 0;
            String txtReceipt_type = "C", txtCR_DB = "DR", txtRecei_from = "";
            Date txtCrea_date = null, txtRef_date = null;
            String txtRef_no = "", txtRemarks = "";

            int cmbMas_SL_type = 0, cmbMas_SL_Code = 0; // changes here
            String update_user = (String)session.getAttribute("UserId");
            long l = System.currentTimeMillis();
            //Timestamp ts = new Timestamp(l);

            //For Banking Purpose
            String txtRec_Vou_type = "", txtMode_of_creat =
                "A", txtCreat_By_Module = "SC";
            int txtJournal_code = 0;
            Date txtCha_Date = null, txtRec_Vou_date = null;
            int txtBankId = 0, txtBranchId = 0, txtNo_of_pay_voucher =
                0, txtCha_No = 0, txtRec_Vou_No = 0;
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


            try {
                txtReceipt_No =
                        Integer.parseInt(request.getParameter("txtReceipt_No"));
            } catch (NumberFormatException e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtReceipt_No " + txtReceipt_No);

            //  assigning Receipt date to drwal date  ****************************
            drawlDate = txtCrea_date;


            // -- start T ( even it's a cash receipt , we getting txtBankId,txtBranchId, txtBankAccountNo)
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
            //-  -- end T

            try {
                txtAmount =
                        Double.parseDouble(request.getParameter("txtAmount"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtAmount " + txtAmount);

            txtRemarks = request.getParameter("txtRemarks");
            System.out.println("txtRemarks " + txtRemarks);


            String paywise_vouNo_Master_Grid[] =
                request.getParameterValues("paywise_vouNo");
            txtNo_of_pay_voucher = paywise_vouNo_Master_Grid.length;

            System.out.println("txtNo_of_pay_voucher.." +
                               txtNo_of_pay_voucher);

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
                         System.out.println("year and month.."+txtCash_year+".."+txtCash_Month_hid);
             }

             catch(Exception e)
             {
                 sendMessage(response,"The Cash Book period not found   ","ok");
                 System.out.println("exception"+e);
                 return;
             }

           */

            /** Remittance in  Current Month  */
            String rem_in_curr_month = "Y";

            try {
                con.clearWarnings();
                con.setAutoCommit(false);
                System.out.println("inside proc");

                Total_TRN_Rec = 1; // Note only one record for transaction here
                SimpleDateFormat obj = new SimpleDateFormat("yyyy-MM-dd");  
    			SimpleDateFormat objTs = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
                //java.sql.Date txtCrea_date = java.sql.Date.valueOf( "txtCrea_date" );
                //long epoch = objTs.parse("ts").getTime();    
               
    			   c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
    					                         Integer.parseInt(sd[0]));
    					            d = c.getTime();
    					            txtCrea_date = new Date(d.getTime());
    			
    			Timestamp ts=new Timestamp(l); 

                System.out.println(Total_TRN_Rec + "... Total_TRN_Rec");
                cs =
  con.prepareCall("call FAS_RECEIPT_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                cs.setInt(1, cmbAcc_UnitCode);
                cs.setInt(2, txtCash_year);
                cs.setInt(3, txtCash_Month_hid);
                cs.setInt(4, txtReceipt_No);
                cs.setInt(5, cmbOffice_code);
                cs.setDate(6, txtCrea_date);
                cs.setString(7, txtReceipt_type);
                cs.setInt(8, txtCash_Acc_code);
                cs.setString(9, txtCR_DB);
                cs.setInt(10, txtBankId);
                cs.setInt(11, txtBranchId);
                cs.setLong(12, txtBankAccountNo);
                cs.setString(13, txtRecei_from);
                cs.setString(14, txtRef_no);
                cs.setDate(15, txtRef_date);
                cs.setInt(16, txtNo_of_pay_voucher);
                cs.setInt(17, txtCha_No);
                cs.setDate(18, txtCha_Date);
                cs.setObject(19, txtAmount,java.sql.Types.NUMERIC);
                cs.setInt(20, Total_TRN_Rec);
                cs.setString(21, txtRec_Vou_type);
                cs.setInt(22, txtRec_Vou_No);
                cs.setDate(23, txtRec_Vou_date);
                cs.setString(24, String.valueOf(txtJournal_code));
                cs.setString(25, txtRemarks);
                cs.setString(26, txtMode_of_creat);
                cs.setString(27, txtCreat_By_Module);
                cs.setString(28, "update");
                cs.registerOutParameter(4, java.sql.Types.NUMERIC);
                cs.registerOutParameter(29, java.sql.Types.NUMERIC);
                cs.setNull(4, java.sql.Types.NUMERIC);
                cs.setNull(29, java.sql.Types.INTEGER);
                cs.setInt(30, cmbMas_SL_type);
                cs.setInt(31, cmbMas_SL_Code);
                cs.setString(32, update_user);
                cs.setTimestamp(33, ts);
                cs.setString(34, rem_in_curr_month);
                System.out.println("b4 exe ");
                cs.execute();
                txtReceipt_No =cs.getBigDecimal(4).intValue();
                int errcode = cs.getBigDecimal(29).intValue();
                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    System.out.println("redirect receipt..");
                    sendMessage(response,
                                "The self-cheque modification  Failed ", "ok");
                    return;
                } else {
                    String sql_del =
                        "delete from FAS_RECEIPT_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?" +
                        "and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=?  and RECEIPT_NO=?  ";
                    ps = con.prepareStatement(sql_del);
                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    ps.setInt(3, txtCash_year);
                    ps.setInt(4, txtCash_Month_hid);
                    ps.setInt(5, txtReceipt_No);
                    ps.executeUpdate(); // deletion from transaction table
                    ps.close();

                    String sql =
                        "insert into FAS_RECEIPT_TRANSACTION(ACCOUNTING_UNIT_ID, " +
                        "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, RECEIPT_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
                        "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, RECEIVED_FROM," +
                        "CHEQUE_OR_DD ,CHEQUE_DD_NO, CHEQUE_DD_DATE, BANK_NAME, DRAWEE_BRANCH, " +
                        "BANK_MICR_CODE, AMOUNT, PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE ) " +
                        "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                    int SL_NO = 1, cmbSL_type = 0, cmbSL_Code = 0;
                    String rad_sub_CR_DR = "CR", txtParticular =
                        "", txtCheque_DD = "C", txtCheque_DD_NO =
                        "", txtBank_Name = "", txtDraw_BR =
                        "", txtBank_M_Code = "", txtsub_Recei_from = "";
                    Date txtCheque_DD_date = null;
                    double txtsub_Amount = 0;

                    // -- start A (used to find the bank name and branch name and MICR code )
                    int BkId = 0, tBrId = 0;
                    txtAcc_HeadCode =
                            Integer.parseInt(request.getParameter("txtCash_Acc_code")); // here the (Operation Head as madam Said)Account head of Chjeque Drawl(first TAB) passed
                    System.out.println("txtAcc_HeadCode.." + txtAcc_HeadCode);

                    try {
                        BkId =
Integer.parseInt(request.getParameter("txtBankId"));
                    } catch (NumberFormatException e) {
                        System.out.println("exception" + e);
                    }
                    System.out.println("txtBankId " + txtBankId);

                    try {
                        tBrId =
Integer.parseInt(request.getParameter("txtBranchId"));
                    } catch (NumberFormatException e) {
                        System.out.println("exception" + e);
                    }
                    System.out.println("txtBranchId " + txtBranchId);

                    // -- end  A

                    txtCheque_DD_NO = request.getParameter("txtCheque_DD_NO");

                    sd = request.getParameter("txtCheque_DD_date").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    d = c.getTime();
                    txtCheque_DD_date = new Date(d.getTime());
                    System.out.println("txtCheque_DD_date " +
                                       txtCheque_DD_date);

                    txtsub_Amount = txtAmount;
                    txtParticular =
                            "Self-Cheque NO:" + txtCheque_DD_NO + "  Date :" +
                            request.getParameter("txtCheque_DD_date") +
                            "  Remarks:" + txtRemarks;

                    // String sql_bank="select bk.BANK_NAME,br.BRANCH_NAME ||'-' || br.CITY_TOWN_NAME as bk_br_city ,br.MICR_CODE "+
                    //               " from FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where br.BANK_ID=bk.BANK_ID and br.BANK_ID=? and br.BRANCH_ID=? ";

                    String sql_bank =
                        "select bk.BANK_NAME,br.BRANCH_NAME as bk_br_city ,br.MICR_CODE " +
                        " from FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where br.BANK_ID=bk.BANK_ID and br.BANK_ID=? and br.BRANCH_ID=? ";

                    ps = con.prepareStatement(sql_bank);
                    ps.setInt(1, BkId);
                    ps.setInt(2, tBrId);
                    rs = ps.executeQuery();
                    if (rs.next()) {
                        txtBank_Name = rs.getString("BANK_NAME");
                        txtDraw_BR = rs.getString("bk_br_city");
                        txtBank_M_Code = rs.getString("MICR_CODE");
                    }
                    ps.close();
                    rs.close();
                    ps = con.prepareStatement(sql);

                    ps.setInt(1, cmbAcc_UnitCode);
                    ps.setInt(2, cmbOffice_code);
                    ps.setInt(3, txtCash_year);
                    ps.setInt(4, txtCash_Month_hid);
                    ps.setInt(5, txtReceipt_No);
                    ps.setInt(6, SL_NO);
                    ps.setInt(7, txtAcc_HeadCode);
                    ps.setString(8, rad_sub_CR_DR);
                    ps.setInt(9, cmbSL_type);
                    ps.setInt(10, cmbSL_Code);
                    ps.setString(11, txtsub_Recei_from);
                    //ps.setString(12,DPN_deptId);
                    //ps.setInt(13,DPN_offId);
                    ps.setString(12, txtCheque_DD);
                    ps.setString(13, txtCheque_DD_NO);
                    ps.setDate(14, txtCheque_DD_date);
                    ps.setString(15, txtBank_Name);
                    ps.setString(16, txtDraw_BR);
                    ps.setString(17, txtBank_M_Code);
                    ps.setDouble(18, txtsub_Amount);
                    ps.setString(19, txtParticular);
                    ps.setString(20, update_user);
                    ps.setTimestamp(21, ts);
                    SL_NO++;
                    ps.executeUpdate();

                    ps.close();
                    System.out.println("Receipt done well..");

                    //****

                    //********88

                    //  payment part starting , from here cash book year and month are based on Payment vr. part (Voucher details tab in jsp)
                    // Date also taken from that part . so receipt date and payment date vary,,.. so cash book year and month vary
                    // cash book year and month here again calculated...


                    // ------------------------- Payment part started here ------------------------
                    try {
                        //  General details
                        // here again variables are made to value of 'ZERO' for neccessary fields..
                        // and again gathering those by request.getParameter();

                        // cmbAcc_UnitCode=cmbOffice_code=txtCash_Month_hid=txtCash_year=0;  // same as above values

                        int txtVoucher_No = 0;
                        txtCash_Acc_code = 820101;
                        txtBankId =
                                txtBranchId = Total_TRN_Rec = cmbMas_SL_Code =
                                        cmbMas_SL_type = 0;
                        int txtchallan_No = 0;

                        txtBankAccountNo = 0;
                        txtAmount = 0;
                        double txtPart_Amount = 0;
                        String txtPayment_type = "C";
                        txtCR_DB = "CR";
                        String txtPaid_to = "", txtPay_Vou_type = "";
                        txtJournal_code = 0;
                        String radPart_Amt = "";
                        txtRemarks = "";
                        txtMode_of_creat = "M";
                        txtCreat_By_Module = "SC";

                        int Real_acq_value =
                            0; // Here this one specifies wheather Voucher has Acquittance or Not ( if '0' means acq.roll not available , else if '1' means it has Acq.roll number

                        //txtCrea_date=null;     --- same date(Receipt date is used for payment too)

                        // same as  Receipt start B
                        try {
                            cmbAcc_UnitCode =
                                    Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                        } catch (NumberFormatException e) {
                            System.out.println("exception" + e);
                        }
                        System.out.println("cmbAcc_UnitCode " +
                                           cmbAcc_UnitCode);

                        try {
                            cmbOffice_code =
                                    Integer.parseInt(request.getParameter("cmbOffice_code"));
                        } catch (NumberFormatException e) {
                            System.out.println("exception" + e);
                        }
                        System.out.println("cmbOffice_code " + cmbOffice_code);

                        // same as  Receipt end B
                        System.out.println("txtCR_DB " + txtCR_DB);

                        System.out.println("cmbMas_SL and cmbMas_SL_Code " +
                                           cmbMas_SL_type + " " +
                                           cmbMas_SL_Code); //+" "+cmbMas_offid);

                        System.out.println("txtPaid_to " + txtPaid_to);


                        //   from here we have to concentrate on (General part of Voucher Details  tab) , because more than one voucher occur


                        paywise_vouNo_Master_Grid =
                                request.getParameterValues("paywise_vouNo");

                        System.out.println("paywise_vouNo_Master_Grid.. length.." +
                                           paywise_vouNo_Master_Grid.length);

                        //String paywise_vouNo_payemnt[]=request.getParameterValues("paywise_vouNo");

                        // to get from grid
                        String txtAmount_grid[] =
                            request.getParameterValues("paywise_Amount");
                        String txtRemarks_grid[] =
                            request.getParameterValues("paywise_remark");
                        String paywise_AcqNumber[] =
                            request.getParameterValues("paywise_AcqNumber");
                        //  Start AZ -- here

                        Vector acq_existness = new Vector();
                        for (int z = 0; z < paywise_AcqNumber.length;
                             z++) // START of "for()" loop      // this is especially for acq. roll !=0
                        {
                            int StrAcq_number =
                                Integer.parseInt(paywise_AcqNumber[z]);
                            //if(StrAcq_number>0)
                            if (StrAcq_number >
                                0) // START -- if ACq. roll applicable
                            {
                                if (acq_existness.contains(StrAcq_number)) // This is used to avoid acquittance number should not repeat more than one time within this if() condition
                                {
                                    System.out.println("continue loop:" +
                                                       StrAcq_number);
                                    continue;
                                }
                                acq_existness.add(StrAcq_number); // we need to store the acq.. number in this vector

                                /* Real_acq_value=0;       // again intilized to "ZERO" for finding new Acq. roll number by max(ACQ_ROLL_NO)
                                            PreparedStatement ps_acq=con.prepareStatement("select max(ACQ_ROLL_NO) as ACQ_ROLL_NO_alias from FAS_ACQ_ROLL_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=?  ");
                                            ps_acq.setInt(1,cmbAcc_UnitCode);
                                            ps_acq.setInt(2,cmbOffice_code);
                                            ps_acq.setInt(3,txtCash_year);
                                            ResultSet rs_acq=ps_acq.executeQuery();
                                            if(rs_acq.next())
                                                Real_acq_value=rs_acq.getInt("ACQ_ROLL_NO_alias")+1;
                                            else
                                                Real_acq_value=1;
                                           */


                                Real_acq_value =
                                        StrAcq_number; //****************  This is special here.. bcoz original Acq. roll got from request.. So no need of finding max(),


                                for (int t = 0;
                                     t < paywise_vouNo_Master_Grid.length;
                                     t++) // this is START of FOR loop for paywise voucher number
                                {
                                    // here i check the voucher numbers which has the same Acq. roll number , because one acq.. may have multiple vouchers
                                    // if more than one voucher occurs for the same Acq.. we create the Real voucher number for all that vouchers
                                    // and insserting into ACQ_.. TRANSACTION table for the combination of acq.no && voucher no
                                    if (Integer.parseInt(paywise_AcqNumber[z]) ==
                                        Integer.parseInt(paywise_AcqNumber[t])) // checking  with same acq. number  -- START
                                    {

                                        int Master_paywise_vouNo =
                                            Integer.parseInt(paywise_vouNo_Master_Grid[t]);
                                        txtVoucher_No = Master_paywise_vouNo;
                                        txtAmount =
                                                Double.parseDouble(txtAmount_grid[t]);
                                        txtRemarks = txtRemarks_grid[t];

                                        System.out.println("Master_paywise_vouNo.." +
                                                           Master_paywise_vouNo);
                                        System.out.println("txtAmount " +
                                                           txtAmount);
                                        System.out.println("txtRemarks " +
                                                           txtRemarks);
                                        System.out.println("Real_acq_value " +
                                                           Real_acq_value);
                                        System.out.println(" payment proc start");

                                        String paywise_vouNo_Trans_Grid[] =
                                            request.getParameterValues("paywiseDETAILS_vouNo"); // Note:***** finding no.of trans records
                                        int count_trs_records = 0;
                                        for (int j = 0;
                                             j < paywise_vouNo_Trans_Grid.length;
                                             j++) {
                                            if (Integer.parseInt(paywise_vouNo_Trans_Grid[j]) ==
                                                Integer.parseInt(paywise_vouNo_Master_Grid[t]))
                                                count_trs_records++;
                                        }

                                        Total_TRN_Rec =
                                                count_trs_records; // Note*********

                                        System.out.println(Total_TRN_Rec +
                                                           " ..paywise_vouNo_Trans_Grid.length");
                                        cs =
  //con.prepareCall("{call FAS_PAYMENT_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
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
                                        cs.setInt(24, Real_acq_value);
                                        cs.setString(25, txtMode_of_creat);
                                        cs.setString(26, txtCreat_By_Module);
                                        cs.setString(27, "update");
                                        cs.registerOutParameter(7,
                                                                java.sql.Types.NUMERIC);
                                        cs.registerOutParameter(28,
                                                                java.sql.Types.NUMERIC);
                                        cs.setString(29, update_user);
                                        cs.setTimestamp(30, ts);
                                        cs.setInt(31, txtReceipt_No);
                                        System.out.println("b4 exe ");
                                        cs.execute();
                                        txtVoucher_No = cs.getInt(7);
                                        errcode = cs.getInt(28);
                                        System.out.println("SQLCODE::: from  payment" +
                                                           errcode);
                                        if (errcode != 0) {
                                            System.out.println("redirect");
                                            sendMessage(response,
                                                        "The self-cheque modification  Failed ",
                                                        "ok");
                                            return;
                                        } else {

                                            vou_numbers =
                                                    vou_numbers + "," + txtVoucher_No;


                                            Master_paywise_vouNo =
                                                    Integer.parseInt(paywise_vouNo_Master_Grid[t]);

                                            paywise_vouNo_Trans_Grid =
                                                    request.getParameterValues("paywiseDETAILS_vouNo");
                                            String Grid_H_code[] =
                                                request.getParameterValues("paywiseDETAILS_AccHEAD");
                                            String Grid_SL_type[] =
                                                request.getParameterValues("SL_type_DETAILS");
                                            String Grid_SL_code[] =
                                                request.getParameterValues("SL_code_DETAILS");
                                            String Grid_sl_amt[] =
                                                request.getParameterValues("paywiseDETAILS_Amount");
                                            String Grid_particular[] =
                                                request.getParameterValues("paywiseDETAILS_parti");

                                            SL_NO = 1;
                                            txtAcc_HeadCode =
                                                    cmbSL_Code = cmbSL_type =
                                                            0;
                                            int txtPay_Vou_No = 0;
                                            Date txtBill_Date =
                                                null, txtAgree_Date = null;
                                            txtCheque_DD_date = null;
                                            Date txtPay_Vou_date = null;
                                            txtsub_Amount = 0;
                                            rad_sub_CR_DR = "";
                                            String txtBill_no =
                                                "", txtBill_Type =
                                                "", txtAgree_No = "";
                                            txtParticular = "";
                                            txtCheque_DD =
                                                    txtCheque_DD_NO = "";
                                            String txtsub_Paid_to =
                                                "", txtReference_No = "";

                                            sql_del =
                                                    "delete from FAS_PAYMENT_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and " +
                                                    "CASHBOOK_MONTH =? and VOUCHER_NO=?";

                                            ps = con.prepareStatement(sql_del);
                                            ps.setInt(1, cmbAcc_UnitCode);
                                            ps.setInt(2, cmbOffice_code);
                                            ps.setInt(3, txtCash_year);
                                            ps.setInt(4, txtCash_Month_hid);
                                            ps.setInt(5, txtVoucher_No);
                                            int dele =
                                                ps.executeUpdate(); // deletion from transaction table
                                            ps.close();
                                            System.out.println("deleted pay:" +
                                                               dele);
                                            for (int k = 0;
                                                 k < paywise_vouNo_Trans_Grid.length;
                                                 k++) {
                                                if (Integer.parseInt(paywise_vouNo_Trans_Grid[k]) ==
                                                    Integer.parseInt(paywise_vouNo_Master_Grid[t])) {
                                                    try {
                                                        txtAcc_HeadCode =
                                                                Integer.parseInt(Grid_H_code[k]);
                                                    } catch (Exception e) {
                                                        System.out.println("exception in trans " +
                                                                           e);
                                                    }
                                                    rad_sub_CR_DR = "DR";

                                                    try {
                                                        cmbSL_type =
                                                                Integer.parseInt(Grid_SL_type[k]);
                                                    } catch (Exception e) {
                                                        System.out.println("exception in trans " +
                                                                           e);
                                                    }
                                                    try {
                                                        cmbSL_Code =
                                                                Integer.parseInt(Grid_SL_code[k]);
                                                    } catch (Exception e) {
                                                        System.out.println("exception in trans " +
                                                                           e);
                                                    }


                                                    txtsub_Amount =
                                                            Double.parseDouble(Grid_sl_amt[k]);
                                                    txtParticular =
                                                            Grid_particular[k];

                                                    System.out.println("Grid_H_code[k] " +
                                                                       Grid_H_code[k]);
                                                    System.out.println("Grid_SL_type[k]" +
                                                                       Grid_SL_type[k]);
                                                    System.out.println("Grid_SL_code[k]" +
                                                                       Grid_SL_code[k] +
                                                                       "from here" +
                                                                       cmbSL_Code);
                                                    System.out.println("Grid_sl_amt[k] " +
                                                                       Grid_sl_amt[k]);
                                                    System.out.println("Grid_particular[k] " +
                                                                       Grid_particular[k]);


                                                    String sql_pay_trans =
                                                        "insert into FAS_PAYMENT_TRANSACTION(ACCOUNTING_UNIT_ID, " +
                                                        "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
                                                        "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, BILL_NO,BILL_TYPE,AGREEMENT_NO,AGREEMENT_DATE," +
                                                        "BILL_DATE,BANK_ID,BRANCH_ID,ACCOUNT_NO,CHEQUE_OR_DD ,CHEQUE_DD_NO, CHEQUE_DD_DATE, PAID_TO, " +
                                                        "AMOUNT, PARTICULARS, PAYABLE_VOUCHER_NO,PAYABLE_VOUCHER_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,REFERENCE_NO) " +
                                                        "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                                                    ps =
  con.prepareStatement(sql_pay_trans);

                                                    ps.setInt(1,
                                                              cmbAcc_UnitCode);
                                                    ps.setInt(2,
                                                              cmbOffice_code);
                                                    ps.setInt(3, txtCash_year);
                                                    ps.setInt(4,
                                                              txtCash_Month_hid);
                                                    ps.setInt(5,
                                                              txtVoucher_No);
                                                    ps.setInt(6, SL_NO);
                                                    ps.setInt(7,
                                                              txtAcc_HeadCode);
                                                    ps.setString(8,
                                                                 rad_sub_CR_DR);
                                                    ps.setInt(9, cmbSL_type);
                                                    ps.setInt(10, cmbSL_Code);
                                                    ps.setString(11,
                                                                 txtBill_no);
                                                    ps.setString(12,
                                                                 txtBill_Type);
                                                    ps.setString(13,
                                                                 txtAgree_No);
                                                    ps.setDate(14,
                                                               txtAgree_Date);
                                                    ps.setDate(15,
                                                               txtBill_Date);
                                                    ps.setInt(16, txtBankId);
                                                    ps.setInt(17, txtBranchId);
                                                    ps.setLong(18,
                                                               txtBankAccountNo);
                                                    ps.setString(19,
                                                                 txtCheque_DD);
                                                    ps.setString(20,
                                                                 txtCheque_DD_NO);
                                                    ps.setDate(21,
                                                               txtCheque_DD_date);
                                                    ps.setString(22,
                                                                 txtsub_Paid_to);
                                                    ps.setDouble(23,
                                                                 txtsub_Amount);
                                                    ps.setString(24,
                                                                 txtParticular);
                                                    ps.setInt(25,
                                                              txtPay_Vou_No);
                                                    ps.setDate(26,
                                                               txtPay_Vou_date);
                                                    ps.setString(27,
                                                                 update_user);
                                                    ps.setTimestamp(28, ts);
                                                    ps.setString(29,
                                                                 txtReference_No);
                                                    SL_NO++;
                                                    ps.executeUpdate();
                                                }
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
                                                txtParticular = "";
                                            }
                                            ps.close();

                                            String Grid_Acq_vouNo[] =
                                                request.getParameterValues("Acq_vouNo");
                                            String Grid_Acq_rollNo[] =
                                                request.getParameterValues("Acq_rollNo");
                                            String Grid_Acq_offID[] =
                                                request.getParameterValues("Acq_offID");
                                            String Grid_Acq_empID[] =
                                                request.getParameterValues("Acq_empID");
                                            String Grid_Acq_Amount[] =
                                                request.getParameterValues("Acq_Amount");
                                            String Grid_Acq_particular[] =
                                                request.getParameterValues("Acq_particular");
                                            // Here i deleting existing details of this voucher
                                            String del_acq_trans =
                                                "delete from FAS_ACQ_ROLL_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and " +
                                                " CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? ";

                                            PreparedStatement del_ps =
                                                con.prepareStatement(del_acq_trans);
                                            del_ps.setInt(1, cmbAcc_UnitCode);
                                            del_ps.setInt(2, cmbOffice_code);
                                            del_ps.setInt(3, txtCash_year);
                                            del_ps.setInt(4,
                                                          txtCash_Month_hid);
                                            del_ps.setInt(5, txtVoucher_No);
                                            int deleted =
                                                del_ps.executeUpdate();
                                            System.out.println("deleted voucher" +
                                                               deleted);

                                            String sql_FAS_ACQ_ROLL_TRANS =
                                                "insert into FAS_ACQ_ROLL_TRANSACTION" +
                                                "(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," +
                                                "CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,ACQ_ROLL_NO,SL_NO,DISBURSING_OFFICE_CODE," +
                                                "EMPLOYEE_CODE,TOTAL_AMOUNT,AMOUNT_DISBURSED_YES_OR_NO,DATE_OF_DISBURSEMENT,REMARKS," +
                                                "UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

                                            String sql_max =
                                                "select max(SL_NO) as max_slno from FAS_ACQ_ROLL_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and " +
                                                " CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and ACQ_ROLL_NO=?";

                                            int SL_NO_Acq = 1;
                                            for (int y = 0;
                                                 y < Grid_Acq_vouNo.length;
                                                 y++) {

                                                if (Integer.parseInt(Grid_Acq_vouNo[y]) ==
                                                    Integer.parseInt(paywise_vouNo_Master_Grid[t]) &&
                                                    Integer.parseInt(Grid_Acq_rollNo[y]) ==
                                                    Integer.parseInt(paywise_AcqNumber[t])) {


                                                    int Acq_rollNo =
                                                        Integer.parseInt(Grid_Acq_rollNo[y]);
                                                    int Acq_offID =
                                                        Integer.parseInt(Grid_Acq_offID[y]);
                                                    int Acq_empID =
                                                        Integer.parseInt(Grid_Acq_empID[y]);
                                                    double Acq_Amount =
                                                        Double.parseDouble(Grid_Acq_Amount[y]);
                                                    String Acq_particular =
                                                        Grid_Acq_particular[y];

                                                    System.out.println("Grid_Acq_vouNo.." +
                                                                       Grid_Acq_vouNo[y]);
                                                    System.out.println("Acq_rollNo.." +
                                                                       Acq_rollNo);
                                                    System.out.println("Acq_offID.." +
                                                                       Acq_offID);
                                                    System.out.println("Acq_empID.." +
                                                                       Acq_empID);
                                                    System.out.println("Acq_Amount.." +
                                                                       Acq_Amount);
                                                    System.out.println("Acq_particular.." +
                                                                       Acq_particular);

                                                    PreparedStatement ps6 =
                                                        con.prepareStatement(sql_max);
                                                    ResultSet rs6 = null;

                                                    ps6.setInt(1,
                                                               cmbAcc_UnitCode);
                                                    ps6.setInt(2,
                                                               cmbOffice_code);
                                                    ps6.setInt(3,
                                                               txtCash_year);
                                                    ps6.setInt(4,
                                                               txtCash_Month_hid);
                                                    ps6.setInt(5,
                                                               txtVoucher_No);
                                                    ps6.setInt(6,
                                                               Real_acq_value); // Real Acq. roll number has stored here
                                                    rs6 = ps6.executeQuery();
                                                    if (rs6.next()) {
                                                        if (rs6.getInt("max_slno") >
                                                            0)
                                                            SL_NO_Acq =
                                                                    rs6.getInt("max_slno") +
                                                                    1;
                                                        else
                                                            SL_NO_Acq = 1;
                                                    }
                                                    rs6.close();
                                                    ps6.close();

                                                    System.out.println("SL_NO_Acq..after max_slno..." +
                                                                       SL_NO_Acq);

                                                    PreparedStatement ps5 =
                                                        con.prepareStatement(sql_FAS_ACQ_ROLL_TRANS);

                                                    ps5.setInt(1,
                                                               cmbAcc_UnitCode);
                                                    ps5.setInt(2,
                                                               cmbOffice_code);
                                                    ps5.setInt(3,
                                                               txtCash_year);
                                                    ps5.setInt(4,
                                                               txtCash_Month_hid);
                                                    ps5.setInt(5,
                                                               txtVoucher_No);
                                                    ps5.setInt(6,
                                                               Real_acq_value); // Real Acq.Roll number has stored
                                                    ps5.setInt(7, SL_NO_Acq);
                                                    ps5.setInt(8, Acq_offID);
                                                    ps5.setInt(9, Acq_empID);
                                                    ps5.setDouble(10,
                                                                  Acq_Amount);
                                                    ps5.setString(11, "N");
                                                    ps5.setDate(12, null);
                                                    ps5.setString(13,
                                                                  Acq_particular);
                                                    ps5.setString(14,
                                                                  update_user);
                                                    ps5.setTimestamp(15, ts);
                                                    ps5.executeUpdate();
                                                    ps5.close();
                                                }
                                            }

                                        } // End of else part of payment voucher

                                    } // checking  with same acq. number  -- END


                                } // this is END of FOR loop for paywise voucher number
                                //  here FAS_ACQ_ROLL_DETAILS started after finishing the voucher generation of same acq.roll number

                                // The following delete statement based on that acq.. roll number

                                String del_acq_details =
                                    "delete from FAS_ACQ_ROLL_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACQ_ROLL_NO=?";
                                PreparedStatement ps_del =
                                    con.prepareStatement(del_acq_details);
                                ps_del.setInt(1, cmbAcc_UnitCode);
                                ps_del.setInt(2, cmbOffice_code);
                                ps_del.setInt(3, txtCash_year);
                                ps_del.setInt(4, txtCash_Month_hid);
                                ps_del.setInt(5, Real_acq_value);
                                int y = ps_del.executeUpdate();
                                System.out.println("deleted acq:" + y);

                                // The following INSERT statement having subquery.... here i group the transaction based on that acq.. roll number

                                String sql_ins_details_ins =
                                    "insert into FAS_ACQ_ROLL_DETAILS(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR," +
                                    " CASHBOOK_MONTH,VOUCHER_NO,ACQ_ROLL_NO,SEND_TO_OFFICE_CODE,TOTAL_EMPLOYEES,TOTAL_AMOUNT,ACQ_ROLL_SENT_DATE,ACQ_ROLL_RETURN_DATE," +
                                    " REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE) " +
                                    "(select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,ACQ_ROLL_NO,DISBURSING_OFFICE_CODE,total_emp,sum_amt,null,null,null,?,? from" +
                                    " (" +
                                    " select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH," +
                                    " VOUCHER_NO,ACQ_ROLL_NO,DISBURSING_OFFICE_CODE,count(EMPLOYEE_CODE) as total_emp,SUM(TOTAL_AMOUNT) as sum_amt from FAS_ACQ_ROLL_TRANSACTION" +
                                    " where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACQ_ROLL_NO=?" +
                                    " group by ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ACQ_ROLL_NO,VOUCHER_NO,DISBURSING_OFFICE_CODE" +
                                    " )" + " )";

                                PreparedStatement ps7 =
                                    con.prepareStatement(sql_ins_details_ins);
                                ps7.setString(1, update_user);
                                ps7.setTimestamp(2, ts);
                                ps7.setInt(3, cmbAcc_UnitCode);
                                ps7.setInt(4, cmbOffice_code);
                                ps7.setInt(5, txtCash_year);
                                ps7.setInt(6, txtCash_Month_hid);
                                ps7.setInt(7, Real_acq_value);
                                ps7.executeUpdate();
                                ps7.close();

                                Real_acq_display =
                                        Real_acq_display + "," + Real_acq_value; // ******** DISPLAY purpose

                            } // END -- if ACq. roll applicable


                        } // END of "for()" loop

                        // End AZ
                        for (int z = 0; z < paywise_AcqNumber.length;
                             z++) // START of "WHILE()" loop      // this is especially for acq. roll !=0
                        {
                            int StrAcq_number =
                                Integer.parseInt(paywise_AcqNumber[z]);

                            if (StrAcq_number ==
                                0) // START -- if ACq. roll not applicable
                            {
                                Real_acq_value = 0;
                                int Master_paywise_vouNo =
                                    Integer.parseInt(paywise_vouNo_Master_Grid[z]);
                                txtVoucher_No = Master_paywise_vouNo;
                                txtAmount =
                                        Double.parseDouble(txtAmount_grid[z]);
                                txtRemarks = txtRemarks_grid[z];

                                System.out.println("Master_paywise_vouNo.." +
                                                   Master_paywise_vouNo);
                                System.out.println("txtAmount " + txtAmount);
                                System.out.println("txtRemarks " + txtRemarks);
                                System.out.println("Real_acq_value " +
                                                   Real_acq_value);

                                System.out.println(" payment proc start");

                                String paywise_vouNo_Trans_Grid[] =
                                    request.getParameterValues("paywiseDETAILS_vouNo"); // Note:***** finding no.of trans records
                                int count_trs_records = 0;
                                for (int j = 0;
                                     j < paywise_vouNo_Trans_Grid.length;
                                     j++) {
                                    if (Integer.parseInt(paywise_vouNo_Trans_Grid[j]) ==
                                        Integer.parseInt(paywise_vouNo_Master_Grid[z]))
                                        count_trs_records++;
                                }

                                Total_TRN_Rec =
                                        count_trs_records; // Note*********

                                System.out.println(Total_TRN_Rec +
                                                   " ..paywise_vouNo_Trans_Grid.length");
                                cs =
  //con.prepareCall("{call FAS_PAYMENT_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
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
                                cs.setInt(24, Real_acq_value);
                                cs.setString(25, txtMode_of_creat);
                                cs.setString(26, txtCreat_By_Module);
                                cs.setString(27, "update");
                                cs.registerOutParameter(7,
                                                        java.sql.Types.NUMERIC);
                                cs.registerOutParameter(28,
                                                        java.sql.Types.NUMERIC);
                                cs.setNull(7, java.sql.Types.NUMERIC);
                                cs.setNull(28, java.sql.Types.NUMERIC);
                                cs.setString(29, update_user);
                                cs.setTimestamp(30, ts);
                                cs.setInt(31, txtReceipt_No);
                                System.out.println("b4 exe ");
                                cs.execute();
//                                txtVoucher_No = cs.getInt(7);
//                                errcode = cs.getInt(28);
                                txtVoucher_No =cs.getBigDecimal(7).intValue();// to be stored in jour5nal master as CB_REF_NO              
                                System.out.println("Voucher no sss::::"+txtVoucher_No);
                                		                 //int errcode = cs.getInt(28);
                                errcode = cs.getBigDecimal(28).intValue();
                                System.out.println("SQLCODE::: from  payment" +
                                                   errcode);
                                if (errcode != 0) {
                                    System.out.println("redirect");
                                    sendMessage(response,
                                                "The self-cheque modification  Failed ",
                                                "ok");
                                    return;
                                } else {

                                    vou_numbers =
                                            vou_numbers + "," + txtVoucher_No;

                                    Master_paywise_vouNo =
                                            Integer.parseInt(paywise_vouNo_Master_Grid[z]);

                                    paywise_vouNo_Trans_Grid =
                                            request.getParameterValues("paywiseDETAILS_vouNo");
                                    String Grid_H_code[] =
                                        request.getParameterValues("paywiseDETAILS_AccHEAD");
                                    String Grid_SL_type[] =
                                        request.getParameterValues("SL_type_DETAILS");
                                    String Grid_SL_code[] =
                                        request.getParameterValues("SL_code_DETAILS");
                                    String Grid_sl_amt[] =
                                        request.getParameterValues("paywiseDETAILS_Amount");
                                    String Grid_particular[] =
                                        request.getParameterValues("paywiseDETAILS_parti");

                                    SL_NO = 1;
                                    txtAcc_HeadCode =
                                            cmbSL_Code = cmbSL_type = 0;
                                    int txtPay_Vou_No = 0;
                                    Date txtBill_Date = null, txtAgree_Date =
                                        null;
                                    txtCheque_DD_date = null;
                                    Date txtPay_Vou_date = null;
                                    txtsub_Amount = 0;
                                    rad_sub_CR_DR = "";
                                    String txtBill_no = "", txtBill_Type =
                                        "", txtAgree_No = "";
                                    txtParticular = "";
                                    txtCheque_DD = txtCheque_DD_NO = "";
                                    String txtsub_Paid_to =
                                        "", txtReference_No = "";

                                    // deleting details from Transaction table for this voucher
                                    sql_del =
                                            "delete from FAS_PAYMENT_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and " +
                                            "CASHBOOK_MONTH =? and VOUCHER_NO=?";

                                    ps = con.prepareStatement(sql_del);
                                    ps.setInt(1, cmbAcc_UnitCode);
                                    ps.setInt(2, cmbOffice_code);
                                    ps.setInt(3, txtCash_year);
                                    ps.setInt(4, txtCash_Month_hid);
                                    ps.setInt(5, txtVoucher_No);
                                    ps.executeUpdate(); // deletion from transaction table
                                    ps.close();


                                    for (int k = 0;
                                         k < paywise_vouNo_Trans_Grid.length;
                                         k++) {
                                        if (Integer.parseInt(paywise_vouNo_Trans_Grid[k]) ==
                                            Integer.parseInt(paywise_vouNo_Master_Grid[z])) {
                                            try {
                                                txtAcc_HeadCode =
                                                        Integer.parseInt(Grid_H_code[k]);
                                            } catch (Exception e) {
                                                System.out.println("exception in trans " +
                                                                   e);
                                            }
                                            rad_sub_CR_DR = "DR";

                                            try {
                                                cmbSL_type =
                                                        Integer.parseInt(Grid_SL_type[k]);
                                            } catch (Exception e) {
                                                System.out.println("exception in trans " +
                                                                   e);
                                            }
                                            try {
                                                cmbSL_Code =
                                                        Integer.parseInt(Grid_SL_code[k]);
                                            } catch (Exception e) {
                                                System.out.println("exception in trans " +
                                                                   e);
                                            }


                                            txtsub_Amount =
                                                    Double.parseDouble(Grid_sl_amt[k]);
                                            txtParticular = Grid_particular[k];

                                            System.out.println("Grid_H_code[k] " +
                                                               Grid_H_code[k]);
                                            System.out.println("Grid_SL_type[k]" +
                                                               Grid_SL_type[k]);
                                            System.out.println("Grid_SL_code[k]" +
                                                               Grid_SL_code[k] +
                                                               "from here" +
                                                               cmbSL_Code);
                                            System.out.println("Grid_sl_amt[k] " +
                                                               Grid_sl_amt[k]);
                                            System.out.println("Grid_particular[k] " +
                                                               Grid_particular[k]);


                                            String sql_pay_trans =
                                                "insert into FAS_PAYMENT_TRANSACTION(ACCOUNTING_UNIT_ID, " +
                                                "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
                                                "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, BILL_NO,BILL_TYPE,AGREEMENT_NO,AGREEMENT_DATE," +
                                                "BILL_DATE,BANK_ID,BRANCH_ID,ACCOUNT_NO,CHEQUE_OR_DD ,CHEQUE_DD_NO, CHEQUE_DD_DATE, PAID_TO, " +
                                                "AMOUNT, PARTICULARS, PAYABLE_VOUCHER_NO,PAYABLE_VOUCHER_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,REFERENCE_NO) " +
                                                "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


                                            ps =
  con.prepareStatement(sql_pay_trans);
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
                                        }
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
                                        txtParticular = "";
                                    }
                                    ps.close();
                                }

                            }
                        }

                    } // Payment part try ending

                    catch (Exception e) {
                        try {
                            con.rollback();
                        } catch (SQLException sqle) {
                        }
                        sendMessage(response,
                                    "The self-cheque modification  Failed ",
                                    "ok");
                        System.out.println("Exception occur due to " + e);
                        return;
                    }

                } // End of else part of receipt tranaction part
                System.out.println("b4 commit");
                String txtReferNO_edit = "", txtRemak_edit = "", txtRefdate =
                    ""; // for cross reference
                Date txtReferDate_edit = null;
                String radAuth_MC = "";
                int txtAuth_By = 0;

                cs1 =
 con.prepareCall("call FAS_CROSS_REFERENCE_PROC(?::NUMERIC,?,?,?,?,?,?,?,?,?,?::VARCHAR,?,?,?,?,?)");
                cs1.setInt(1, cmbAcc_UnitCode);
                cs1.setInt(2, txtCash_year);
                cs1.setInt(3, txtCash_Month_hid);
                cs1.setInt(4, txtReceipt_No);
                cs1.setInt(5, cmbOffice_code);
                cs1.setDate(6, txtCrea_date);
                cs1.setString(7, "SC");
                cs1.setString(8, txtReferNO_edit);
                cs1.setDate(9, txtReferDate_edit);
                cs1.setString(10, txtRemak_edit);
                cs1.setInt(11, txtAuth_By);
                cs1.setString(12, "insert");
                cs1.registerOutParameter(13, java.sql.Types.NUMERIC);
                cs1.setNull(13,java.sql.Types.NUMERIC);
                cs1.setString(14, update_user);
                cs1.setTimestamp(15, ts);
                cs1.setString(16, radAuth_MC);
                cs1.execute(); // insertion into cross reference table
                errcode = cs1.getInt(13);
                System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);
                System.out.println("cmbOffice_code " + cmbOffice_code);
                System.out.println("txtCrea_date " + txtCrea_date);
                System.out.println("txtCash_year " + txtCash_year);
                System.out.println("txtCash_Month_hid " + txtCash_Month_hid);
                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    con.rollback();
                    sendMessage(response,
                                "The Self Cheque Modification Failed ", "ok");
                    return;
                }

                con.commit();
                if (!Real_acq_display.equalsIgnoreCase(""))
                    sendMessage(response,
                                "The Self Cheque has been successfully Modified..<br>  Actual Receipt No...'" +
                                txtReceipt_No +
                                ".  <br> Actual Payment Vr.No(s)..." +
                                vou_numbers +
                                ".  <br> Actual Acquittance No(s)..  " +
                                Real_acq_display + " ", "ok");
                else
                    sendMessage(response,
                                "The Self Cheque has been successfully Modified..<br>  Actual Receipt No...'" +
                                txtReceipt_No +
                                "'.  <br> Actual Payment Vr.No(s)..." +
                                vou_numbers + " ", "ok");
            } // End of Receipt "try" block

            catch (Exception e) {
                try {
                    con.rollback();
                } catch (SQLException sqle) {
                    System.out.println("exception in rollback " + sqle);
                }
                sendMessage(response, "The self-cheque Modification Failed ",
                            "ok");
                System.out.println("Exception occurred due to " + e);
                return;
            } finally {
                System.out.println("done");
                try {
                    con.setAutoCommit(true);
                } catch (SQLException sqle) {
                    System.out.println("SQl exec raissed");
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
            System.out.println("error in send msg");
        }
    }
}
