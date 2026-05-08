package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.MathContext;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;
import java.util.HashMap;
import java.util.Map;

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

public class TrialBalanceServlet extends HttpServlet {

    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        /**
         * Variables Declarations
         */
        Connection connection = null;
        Statement statement = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null,ps2=null,ps3=null,ps22=null,ps223=null;
        PreparedStatement pret=null,pret1=null,ps_units=null,pres_aset=null;
        ResultSet rs22=null,result_tda=null,rs223=null,res_unit=null,rset_aset=null,result_adj=null;
        ResultSet rest=null,rest1=null;
        CallableStatement cs = null;
        CallableStatement cs1 = null;

        Boolean flag3 = false;
        int rec=0,tpa_count=0,rsflag=1,brs_update_flag=1,rsflag_adj=1;
        int pastyear=0,pastmonth=0,diff_error=0,diff_error_Adj=0;
        int counted_bank_bal=1;
        int pvyear=0,pvmonth=0,closedunit=0,closedmn=0,closedyr=0;
        int cl_unit_yes=0,office_id=0;
        int asset_cleared=0;
        String count_tally="",closedunitname="";
        /**
         * DataBase Connection
         */
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


        /**
         * Session Checking
         */
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


        /**
         * Get User ID
         */
        String userid = (String)session.getAttribute("UserId");
        System.out.println("session id is:" + userid);

        /**
         * Get Parameters
         */
        String Account_unit_Code = request.getParameter("cmbAcc_UnitCode");
        String CashBook_Year = request.getParameter("txtCB_Year");
        String CashBook_Month = request.getParameter("txtCB_Month");

        System.out.println("Account_Unit_Code is:" + Account_unit_Code);
        System.out.println("CashBook_Year is:" + CashBook_Year);
        System.out.println("CashBook Month is:" + CashBook_Month);
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
        String radTB_status = request.getParameter("radTB_status");
        System.out.println("radTB_status..." + radTB_status);

        int AccountUnitCode = 0;
        int OfficeCode = 0,trial_count=0;
        int CashBookYear = 0;
        int CashBookMonth = 0,nov_count=0,nov_count_inc=0;
        String update_user = (String)session.getAttribute("UserId");
        try {
            AccountUnitCode = Integer.parseInt(Account_unit_Code);
            CashBookYear = Integer.parseInt(CashBook_Year);
            CashBookMonth = Integer.parseInt(CashBook_Month);
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


        /**
           *  Check Wheather TB has been Frozen already or not
           */
        try {

            ps =
  connection.prepareStatement("  select 'X'                  \n" +
                              "  from                        \n" +
                              "    FAS_TRIAL_BALANCE_STATUS  \n" +
                              "  WHERE                       \n" +
                              "     ACCOUNTING_UNIT_ID=?     \n" +
                              "  AND CASHBOOK_YEAR=?      \n" +
                              "  AND CASHBOOK_MONTH=?");
            ps.setInt(1, AccountUnitCode);
            ps.setInt(2, CashBookYear);
            ps.setInt(3, CashBookMonth);
            ResultSet res = ps.executeQuery();
            if (res.next()) // if the row doesn't return by the query
            {
                sendMessage(response, "Trial Balance Already Froze", "ok");
                return;
            }
            res.close();
            ps.close();
        } catch (Exception e) {
            System.out.println(e);
        }
if(AccountUnitCode!=5){
      //this is for checking previous month trialbalance status closed or not
	int open_year=0,open_month=0;
        if(CashBookMonth>1)
        {
        	try {
				PreparedStatement ps_chk=connection.prepareStatement("select to_char(DATE_OF_OPENING,'yyyy')as  year, to_char(DATE_OF_OPENING,'mm') as month from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID="+AccountUnitCode);
			ResultSet rs_chk=ps_chk.executeQuery();
			if(rs_chk.next())
			{
			open_year=rs_chk.getInt(1)	;open_month=rs_chk.getInt(2)	;
			}
			System.out.println("open_year :: "+open_year);
			System.out.println("open_month :: "+open_month);
        	} catch (SQLException e1) {
				// TODO Auto-generated catch block
				e1.printStackTrace();
			}
		 if(CashBookMonth==open_month && CashBookYear==open_year){
			 trial_count=1;
		 }else{
        	try {
		  	
		  		int prevmonth_trial=CashBookMonth-1;
		      ps =connection.prepareStatement("  select 'X'                  \n" +
		                        "  from                        \n" +
		                        "    FAS_TRIAL_BALANCE_STATUS  \n" +
		                        "  WHERE                       \n" +
		                        "     ACCOUNTING_UNIT_ID=?     \n" +
		                        "  AND CASHBOOK_YEAR=?      \n" +
		                        "  AND CASHBOOK_MONTH=?");
		      ps.setInt(1, AccountUnitCode);
		      ps.setInt(2, CashBookYear);
		      ps.setInt(3, prevmonth_trial);
		      ResultSet res = ps.executeQuery();
		      if (res.next()) // if the row doesn't return by the query
		      {
		         trial_count++;
		      }
		      res.close();
		      ps.close();
		  } catch (Exception e) {
		      System.out.println(e);
		  }
        	}
		  if(trial_count==0)
	        {
	        	 sendMessage(response, "Trial Balance Status is Not Freezed For Previous Month", "ok");
	             return;
	        }
        }
        else
        {
        	 try {
     		  	
 		  		int prevyear_trial=CashBookYear-1;
 		      ps =connection.prepareStatement("  select 'X'                  \n" +
 		                        "  from                        \n" +
 		                        "    FAS_TRIAL_BALANCE_STATUS  \n" +
 		                        "  WHERE                       \n" +
 		                        "     ACCOUNTING_UNIT_ID=?     \n" +
 		                        "  AND CASHBOOK_YEAR=?      \n" +
 		                        "  AND CASHBOOK_MONTH=12");
 		      ps.setInt(1, AccountUnitCode);
 		      ps.setInt(2, prevyear_trial);
 		    //  ps.setInt(3, prevmonth_trial);
 		      ResultSet res = ps.executeQuery();
 		      if (res.next()) // if the row doesn't return by the query
 		      {
 		         trial_count++;
 		      }
 		      res.close();
 		      ps.close();
 		  } catch (Exception e) {
 		      System.out.println(e);
 		  }
 		  if(trial_count==0)
 	        {
 	        	 sendMessage(response, "Trial Balance Status is Not Freezed For December Month", "ok");
 	             return;
 	        }
        }
    }
        //---------------------------------------------------------------------------------

        /**
         * Should not allow for Generating TB if Vouchers in Journal are not verified
         */
        int journal_count = 0;
        try {

            String sql =
                "" + "select                                                         \n" +
                "  count(*) as v_count                                          \n" +
                "from                                                           \n" +
                "  fas_journal_master a ,                                       \n" +
                "  fas_journal_transaction b                                    \n" +
                "where                                                          \n" +
                "    a.accounting_unit_id = ?                                   \n" +
                "and a.cashbook_month= ?                                        \n" +
                "and a.cashbook_year = ?                                        \n" +
                "and a.accounting_unit_id=b.accounting_unit_id                  \n" +
                "and a.accounting_for_office_id =b.accounting_for_office_id     \n" +
                "and a.cashbook_year=b.cashbook_year                            \n" +
                "and a.cashbook_month=b.cashbook_month                          \n" +
                "and a.voucher_no=b.voucher_no                                  \n" +
                "and a.JOURNAL_STATUS='L'                                       \n" +
                "and b.VERIFIED is null and a.created_by_module in ( 'GJV', 'LJV' )   ";

            ps1 = connection.prepareStatement(sql);
            ps1.setInt(1, AccountUnitCode);
            ps1.setInt(2, CashBookMonth);
            ps1.setInt(3, CashBookYear);
            System.out.println("before execution");
            ResultSet rs = ps1.executeQuery();
            System.out.println("after execution");
            while (rs.next()) {
                journal_count = rs.getInt("v_count");
            }

            System.out.println("journal_count--->" + journal_count);

        } catch (Exception e) {
            System.out.println(e);
        }


/*
 ** Should not allow for Generating TB if Vouchers in Payment are not verified
 */
        int pay_counted = 0;
        try {

            String sql =
                "" + "select                                                         \n" +
                "  count(*) as v_count                                          \n" +
                "from                                                           \n" +
                "  fas_payment_master a ,                                       \n" +
                "  fas_payment_transaction b                                    \n" +
                "where                                                          \n" +
                "    a.accounting_unit_id = ?                                   \n" +
                "and a.cashbook_month= ?                                        \n" +
                "and a.cashbook_year = ?                                        \n" +
                "and a.accounting_unit_id=b.accounting_unit_id                  \n" +
                "and a.accounting_for_office_id =b.accounting_for_office_id     \n" +
                "and a.cashbook_year=b.cashbook_year                            \n" +
                "and a.cashbook_month=b.cashbook_month                          \n" +
                "and a.voucher_no=b.voucher_no                                  \n" +
                "and a.PAYMENT_STATUS='L'                                       \n" +
                "and b.VERIFIED is null and a.created_by_module in ( 'BPF', 'BPP' )   ";

            ps1 = connection.prepareStatement(sql);
            ps1.setInt(1, AccountUnitCode);
            ps1.setInt(2, CashBookMonth);
            ps1.setInt(3, CashBookYear);
            System.out.println("before execution");
            ResultSet rs = ps1.executeQuery();
            System.out.println("after execution");
            while (rs.next()) {
                pay_counted = rs.getInt("v_count");
            }

            System.out.println("pay_counted--::::::::::->" + pay_counted);

        } catch (Exception e) {
            System.out.println(e);
        }

/*
 ** Should not allow for Generating TB if Vouchers in Fund Trf at Office are not verified
 */
        int ftOff_count = 0;
        try {

            String sql =
                "" + "select                                                         \n" +
                "  count(*) as v_count                                          \n" +
                "from                                                           \n" +
                "  FAS_FUND_TRF_FROM_OFFICE a                                        \n" +
                "where                                                          \n" +
                "    a.accounting_unit_id = ?                                   \n" +
                "and a.cashbook_month= ?                                        \n" +
                "and a.cashbook_year = ?                                        \n" +
                "and a.TRANSFER_STATUS='L'                                       \n" +
                "and a.VERIFY is null  ";

            ps1 = connection.prepareStatement(sql);
            ps1.setInt(1, AccountUnitCode);
            ps1.setInt(2, CashBookMonth);
            ps1.setInt(3, CashBookYear);
            System.out.println("before execution");
            ResultSet rs = ps1.executeQuery();
            System.out.println("after execution");
            while (rs.next()) {
                ftOff_count = rs.getInt("v_count");
            }

            System.out.println("ftOff_count--->" + ftOff_count);

        } catch (Exception e) {
            System.out.println(e);
        }
        
        /*
         ** Should not allow for Generating TB if Vouchers in Fund Trf at HO are not verified
         */
                int ftHO_count = 0;
                try {

                    String sql =
                        "" + "select                                                         \n" +
                        "  count(*) as v_count                                          \n" +
                        "from                                                           \n" +
                        "  FAS_FUND_TRF_FROM_HO_MASTER a ,                                       \n" +
                      "  FAS_FUND_TRF_FROM_HO_TRN b                                    \n" +
                        "where                                                          \n" +
                        "    a.accounting_unit_id = ?                                   \n" +
                        "and a.cashbook_month= ?                                        \n" +
                        "and a.cashbook_year = ?                                        \n" +
                        "and a.accounting_unit_id=b.accounting_unit_id                  \n" +
                        "and a.accounting_for_office_id =b.accounting_for_office_id     \n" +
                        "and a.cashbook_year=b.cashbook_year                            \n" +
                        "and a.cashbook_month=b.cashbook_month                          \n" +
                        "and a.voucher_no=b.voucher_no                                  \n" +
                        "and a.TRANSFER_STATUS='L'                                       \n" +
                        "and b.VERIFY is null  ";

                    ps1 = connection.prepareStatement(sql);
                    ps1.setInt(1, AccountUnitCode);
                    ps1.setInt(2, CashBookMonth);
                    ps1.setInt(3, CashBookYear);
                    System.out.println("before execution");
                    ResultSet rs = ps1.executeQuery();
                    System.out.println("after execution");
                    while (rs.next()) {
                        ftHO_count = rs.getInt("v_count");
                    }

                    System.out.println("ftHO_count--->" + ftHO_count);

                } catch (Exception e) {
                    System.out.println(e);
                }
                //---------------------------------------------------------------------------------

        
        //---------------------------------------------------------------------------------

        /**
                  * Should not allow for Generating TB if Vouchers in Remittance are not verified
                  */
        int count = 0;
        try {
            String sql =
                "select count(*) as v_count from fas_remittance   " +
                "where accounting_unit_id=?  and cashbook_month=?  and cashbook_year=? and verified='N' " +
                "and status is null ";
            ps1 = connection.prepareStatement(sql);
            ps1.setInt(1, AccountUnitCode);
            ps1.setInt(2, CashBookMonth);
            ps1.setInt(3, CashBookYear);
            System.out.println("before execution");
            ResultSet rs = ps1.executeQuery();
            System.out.println("after execution");
            while (rs.next()) {
                count = rs.getInt("v_count");
            }

            System.out.println("count--->" + count);

        } catch (Exception e) {
            System.out.println(e);
        }
        
        /*
         should not allow for Generating TB if PB_UPDATED_BY_USER_ID IS NOT NULL in FAS_GENERAL_LEDGER table
         */
         
         
         int count_pb = 0,counted_field=0,prevMonth=0;
         try {
             String sql ="SELECT MAX(MONTH)as mn1\n" + 
             " FROM FAS_GENERAL_LEDGER\n" + 
             " WHERE PB_UPDATED_BY_USER_ID IS NOT NULL\n" + 
             " AND ACCOUNTING_UNIT_ID      =?\n" + 
             " AND YEAR                     =?\n";
             ps1 = connection.prepareStatement(sql);
             ps1.setInt(1, AccountUnitCode);
             ps1.setInt(2, CashBookYear);
            
             ResultSet rs = ps1.executeQuery();
               while (rs.next()) {
                 count_pb = rs.getInt("mn1");
             }

             System.out.println("count_pb--->" + count_pb);
             ps1.close();
         } catch (Exception e) {
             System.out.println(e);
         }
        /* if(count_pb>0) { //if(count_pb==0)
         
             prevMonth=CashBookMonth-1;// from field month
             if(prevMonth>count_pb) {
                 counted_field++;
             }
             
         }*/
         
         
         
         if(count_pb==0) {
             counted_field++;
         }
         else {
             int cm  =CashBookMonth-1;
             try{
                 String sql =" SELECT COUNT(*)as count FROM fas_general_ledger WHERE year=? and MONTH="+cm+" and extract(MONTH FROM PB_UPDATED_DATE)=? and ACCOUNTING_UNIT_ID="+AccountUnitCode;
               System.out.println("sql::::::"+sql);
                 ps3 = connection.prepareStatement(sql);
                 ps3.setInt(1, CashBookYear);
                 ps3.setInt(2, CashBookMonth);
                 ResultSet rs = ps3.executeQuery();
                   while (rs.next()) {
                   int c_ount=rs.getInt(1);
                   if(c_ount>0)
                     nov_count++;
                 }
                 ps3.close();
                 
                 if(nov_count==0){
                 System.out.println("nov_count*********************************************************:::::"+nov_count);
                     nov_count_inc++;
                 }
             }
             catch(Exception e) {
                 
             }
         }
         
        /*
         should not allow for Generating TB if PB_UPDATED_BY_USER_ID IS NOT NULL in FAS_SUB_LEDGER_MASTER table
         */
         
         
       /*  int count_pb_sub = 0,counted_field_sub=0,prevMonth_sub=0;
         try {
             String sql_sub ="SELECT MAX(MONTH)as mn1\n" + 
             " FROM FAS_SUB_LEDGER_MASTER\n" + 
             " WHERE PB_UPDATED_BY_USER_ID IS NOT NULL\n" + 
             " AND ACCOUNTING_UNIT_ID      =?\n" + 
             " AND YEAR                     =?\n";
             ps1 = connection.prepareStatement(sql_sub);
             ps1.setInt(1, AccountUnitCode);
             ps1.setInt(2, CashBookYear);
            
             ResultSet rs = ps1.executeQuery();
               while (rs.next()) {
                 count_pb_sub = rs.getInt("mn1");
             }

             System.out.println("count_pb_sub--->" + count_pb_sub);
             ps1.close();

         } catch (Exception e) {
             System.out.println(e);
         }
         if(count_pb_sub>0) {
         
             prevMonth_sub=CashBookMonth-1;// from field month
             if(prevMonth_sub>count_pb) {
                 counted_field_sub++;
             }
             
         }  */
       
        // starts 4/5/2011
        
        
         int jou_count=0;
         String jou_vo=" -- NIL";
         
         int pay_count=0;
         String pay_vo=" -- NIL";
         int rec_count=0;
         String rec_vo=" -- NIL";

         int tda_count=0,tda_tca_dateCount=0,accept_Count=0,tda_tca_counted=0,adj_memo_count=0;
         String tda_vo=" -- NIL",tda_tca_vo=" -- NIL",acceptance_vo=" -- NIL",tda_tca_status_NO="   --NIL";
            String adj_memo_no="---NIL";

         int tca_count=0;
         String tca_vo=" -- NIL";
           int payment_count=0;
         String Payment_vo=" -- NIL";

         int receipt_count=0;
         String receipt_vo=" -- NIL";
          int payroll_count=0;
          String payroll_vo=" -- NIL";


         int sus_count=0;
         String sus_vo=" -- NIL";
         
         int HOA_count =0;
         String HOA_list =" -- NIL";
         
         int HOA_count_DR =0;
         String HOA_list_DR =" -- NIL";
         
         int HOA_count_NCR =0;
         String HOA_list_NCR =" -- NIL";
         
         int HOA_count_NDR =0;
         String HOA_list_NDR =" -- NIL";
         
         int k=0;           
         int flagg=0,a1=0,a2=0,a3=0,a4=0,a5=0,a6=0,a7=0,a8=0,a9=0,
        		 a10=0,a11=0,a12=0;   
        
        
        //-----------------------------------------------------------Sl  Check Apr 2011-may 3d
        //--------------------------------Payment
        
         try {
        // System.out.println("enter%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
        
//Modified on 21/06/2019 by NK---
//             String sql =
//            "select distinct a.account_head_code,a.VOUCHER_NO  from fas_payment_transaction a where accounting_unit_id=? and cashbook_month=? and cashbook_year=? and a.SUB_LEDGER_TYPE_CODE=0 and SUB_LEDGER_CODE=0 and \n" +
//             "voucher_no in(select voucher_no from fas_payment_master where  accounting_unit_id=? and cashbook_month=? and cashbook_year=? \n" +
//             "  and  created_by_module in('BPF','BPP') and payment_status='L') and a.account_head_code in(select account_head_code from com_mst_account_heads  \n" +
//             " where SUB_LEDGER_TYPE_APPLICABLE='Y' and SL_MANDATORY='Y') and a.account_head_code not in(select ACCOUNT_HEAD_CODE from FAS_APPLICABLE_SL_TYPE where SUB_LEDGER_TYPE_CODE=7) ";
        	 String sql =
        	            "select distinct a.account_head_code,a.VOUCHER_NO  from fas_payment_transaction a where accounting_unit_id=? and cashbook_month=? and cashbook_year=? and (a.SUB_LEDGER_TYPE_CODE=0 or SUB_LEDGER_CODE=0) and \n" +
        	             "voucher_no in(select voucher_no from fas_payment_master where  accounting_unit_id=? and cashbook_month=? and cashbook_year=? \n" +
        	             "  and  created_by_module in('BPF','BPP') and payment_status='L') and a.account_head_code in(select account_head_code from com_mst_account_heads  \n" +
        	             " where SUB_LEDGER_TYPE_APPLICABLE='Y' and SL_MANDATORY='Y') and a.account_head_code not in(select ACCOUNT_HEAD_CODE from FAS_APPLICABLE_SL_TYPE where SUB_LEDGER_TYPE_CODE=7) ";
             ps1 = connection.prepareStatement(sql);
             
             System.out.println("enter%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+sql);
             ps1.setInt(1, AccountUnitCode);
             ps1.setInt(2, CashBookMonth);
             ps1.setInt(3, CashBookYear);
             ps1.setInt(4, AccountUnitCode);
             ps1.setInt(5, CashBookMonth);
             ps1.setInt(6, CashBookYear);
             System.out.println("before execution");
             ResultSet rs = ps1.executeQuery();
             System.out.println("after execution");
             
             while (rs.next()) {                 
             if(flagg==0)
             {
                 pay_vo="("+rs.getString("VOUCHER_NO")+")"+rs.getString("account_head_code");
           flagg=1;
             }else{
                 pay_vo=pay_vo+","+""+"("+rs.getString("VOUCHER_NO")+")"+rs.getString("account_head_code");
             }
                     System.out.println("enter%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+pay_vo);
                     pay_count++;
                // }
                 //}
             }
        

         } catch (Exception e) {
             System.out.println(e);
         }
        //-------------------------------------------------------------------------------------------------------------Receipt
        
        try {
        System.out.println("enter*************************************************");
       //Modified by NK on 21/06/2019 
//        String sql =
//        "select distinct a.account_head_code,a.RECEIPT_NO  from FAS_RECEIPT_TRANSACTION a where accounting_unit_id=? and cashbook_month=? and cashbook_year=? and  a.SUB_LEDGER_TYPE_CODE=0 and SUB_LEDGER_CODE=0 and \n" +
//        "RECEIPT_NO in(select RECEIPT_NO from fas_receipt_master where  accounting_unit_id=? and cashbook_month=? and cashbook_year=? \n" +
//        "  and  created_by_module in('CR','BR') and RECEIPT_STATUS='L') and a.account_head_code in(select account_head_code from com_mst_account_heads  \n" +
//        " where SUB_LEDGER_TYPE_APPLICABLE='Y' and SL_MANDATORY='Y') and a.account_head_code not in(select ACCOUNT_HEAD_CODE from FAS_APPLICABLE_SL_TYPE where SUB_LEDGER_TYPE_CODE=7) ";
        String sql =
                "select distinct a.account_head_code,a.RECEIPT_NO  from FAS_RECEIPT_TRANSACTION a where accounting_unit_id=? and cashbook_month=? and cashbook_year=? and  (a.SUB_LEDGER_TYPE_CODE=0 or SUB_LEDGER_CODE=0) and \n" +
                "RECEIPT_NO in(select RECEIPT_NO from fas_receipt_master where  accounting_unit_id=? and cashbook_month=? and cashbook_year=? \n" +
                "  and  created_by_module in('CR','BR') and RECEIPT_STATUS='L') and a.account_head_code in(select account_head_code from com_mst_account_heads  \n" +
                " where SUB_LEDGER_TYPE_APPLICABLE='Y' and SL_MANDATORY='Y') and a.account_head_code not in(select ACCOUNT_HEAD_CODE from FAS_APPLICABLE_SL_TYPE where SUB_LEDGER_TYPE_CODE=7) ";
        ps1 = connection.prepareStatement(sql);
        
        System.out.println("enter*************************************************"+sql);
        ps1.setInt(1, AccountUnitCode);
        ps1.setInt(2, CashBookMonth);
        ps1.setInt(3, CashBookYear);
        ps1.setInt(4, AccountUnitCode);
        ps1.setInt(5, CashBookMonth);
        ps1.setInt(6, CashBookYear);
        System.out.println("before execution");
        ResultSet rs = ps1.executeQuery();
        System.out.println("after execution");
        while (rs.next()) {
        
               if(a1==0)
        {
                 rec_vo="("+rs.getString("RECEIPT_NO")+")"+rs.getString("account_head_code");
                 a1=1;
        }
        else {
        rec_vo=rec_vo+","+""+"("+rs.getString("RECEIPT_NO")+")"+rs.getString("account_head_code");
        
        }
                 
                 rec_count++;
          
        
        }
         System.out.println("*************************************************"+rec_count+"String"+rec_vo);
        
        

        } catch (Exception e) {
        System.out.println(e);
        }
        
        //---------------------------------------------------------------------------------------------------------------  journal
        
        try {
        System.out.println("enter%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%");
//        Modified By NK on 21/06/2019 
//         String sql =
//        "select distinct a.account_head_code,a.VOUCHER_NO  from FAS_JOURNAL_TRANSACTION a where accounting_unit_id=? and cashbook_month=? and " +
//        "cashbook_year=? and  a.SUB_LEDGER_TYPE_CODE=0 and SUB_LEDGER_CODE=0 and \n" +
//         "VOUCHER_NO in(select VOUCHER_NO from FAS_JOURNAL_MASTER where  accounting_unit_id=? and cashbook_month=? and cashbook_year=? \n" +
//         "  and  created_by_module in('GJV','LJV') and JOURNAL_STATUS='L') and a.account_head_code in(select account_head_code from com_mst_account_heads  \n" +
//         " where SUB_LEDGER_TYPE_APPLICABLE='Y' and SL_MANDATORY='Y') and a.account_head_code not in(select ACCOUNT_HEAD_CODE from FAS_APPLICABLE_SL_TYPE where SUB_LEDGER_TYPE_CODE=7)  AND a.account_head_code NOT IN(390654)";
         String sql =
        	        "select distinct a.account_head_code,a.VOUCHER_NO  from FAS_JOURNAL_TRANSACTION a where accounting_unit_id=? and cashbook_month=? and " +
        	        "cashbook_year=? and  (a.SUB_LEDGER_TYPE_CODE=0 or SUB_LEDGER_CODE=0 )and \n" +
        	         "VOUCHER_NO in(select VOUCHER_NO from FAS_JOURNAL_MASTER where  accounting_unit_id=? and cashbook_month=? and cashbook_year=? \n" +
        	         "  and  created_by_module in('GJV','LJV') and JOURNAL_STATUS='L') and a.account_head_code in(select account_head_code from com_mst_account_heads  \n" +
        	         " where SUB_LEDGER_TYPE_APPLICABLE='Y' and SL_MANDATORY='Y') and a.account_head_code not in(select ACCOUNT_HEAD_CODE from FAS_APPLICABLE_SL_TYPE where SUB_LEDGER_TYPE_CODE=7)  AND a.account_head_code NOT IN(390654)";

         ps1 = connection.prepareStatement(sql);
         
         System.out.println("enter%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+sql);
         ps1.setInt(1, AccountUnitCode);
         ps1.setInt(2, CashBookMonth);
         ps1.setInt(3, CashBookYear);
         ps1.setInt(4, AccountUnitCode);
         ps1.setInt(5, CashBookMonth);
         ps1.setInt(6, CashBookYear);
      //   System.out.println("before execution");
         ResultSet rs = ps1.executeQuery();
      //   System.out.println("after execution");
         while (rs.next()) {
          
             
             
                 if(a2==0)
                 {
                    // System.out.println("ifffffffffff");
                 jou_vo="("+rs.getString("VOUCHER_NO")+")"+rs.getString("account_head_code");
               //  System.out.println("jou_vo in iffffff"+jou_vo);
                 a2=1;
                 }
                 else {
                 
                // System.out.println("else");
                     jou_vo=jou_vo+","+""+"("+rs.getString("VOUCHER_NO")+")"+rs.getString("account_head_code");
                  //   System.out.println("jou_vo in else>>>>>>"+jou_vo);
                 }
                 jou_count++;
             }
        
        // System.out.println("journal_count--->" + journal_count);

        } catch (Exception e) {
         System.out.println(e);
        }
        
        try
        {
       String sq_ul="select ACCOUNTING_UNIT_OFFICE_ID from fas_mst_acct_units where accounting_unit_id="+AccountUnitCode;
       ps_units=connection.prepareStatement(sq_ul);
        res_unit=ps1.executeQuery();
	       if(res_unit.next())
	       {
	        office_id=res_unit.getInt("ACCOUNTING_UNIT_OFFICE_ID");
	       }
       
       }
       catch(Exception e)
       {
       System.out.println("ee in gettting offid::"+e);
       }
        //checking assets verification table
       try {
  		 
  		String reg = "select f_val,f_qty,hlp_ct from "+
				" (SELECT COUNT(A52_STATUS_VAL)AS f_val,COUNT(A52_STATUS_QTY)     AS f_qty,ACCOUNTING_UNIT_ID as unit_id "+
  		" FROM FAS_A52_AA52_VERIFY_STATUS "+
  		" WHERE ACCOUNTING_UNIT_ID="+AccountUnitCode+" group by ACCOUNTING_UNIT_ID)a "+
  		" full outer join "+
  		" (SELECT count(issue_status)as hlp_ct,OFFICE_ID, "+
  		" (select u.accounting_unit_id from fas_mst_acct_units u" +
  		" where u.accounting_unit_office_id=OFFICE_ID)as unitid "+
  		" FROM hlp_issue_requests "+
  		" WHERE OFFICE_ID    = "+office_id+
  		" AND (ISSUE_STATUS  ='P' "+
  		" OR ISSUE_STATUS    ='N') "+
  		" AND MAJOR_SYSTEM_ID='FAS' "+
  		" AND MINOR_SYSTEM_ID='FAS11' "+
  		" and issue_reported_date>='26-Aug-2013' "+
  		//" and OFFICE_ID!=5000 "+
  		" group by OFFICE_ID)b "+
  		" on a.unit_id=b.unitid";
  		System.out.println("reg:::"+reg);
  		pres_aset = connection.prepareStatement(reg);
  		rset_aset = pres_aset.executeQuery();
			while(rset_aset.next())
			{ 
				
				int f_qty=rset_aset.getInt("f_qty");
				int hlp_ct=rset_aset.getInt("hlp_ct");
			
        		if(f_qty>0)
        		{
        			asset_cleared++;
        		}
        		else if(hlp_ct>0)
        		{
        			asset_cleared=0;
        		}
			}
			
		} catch (SQLException e) {				
			e.printStackTrace();
			
		}
        
        //---------------------------------------------TrailBalace Check
        // * TDA_TCA TABLE  CHECK=======
           // * TDA/ TCA originating advice entered, but journal not posted
        
        
            try
                     {
                    String sql="select  a.VOUCHER_NO,a.ORGINATING_JVR_NO from FAS_TDA_TCA_RAISED_MST a where a.accounting_unit_id=? \n "+
                    "and a.cashbook_month=? and a.cashbook_year=? and a.TDA_OR_TCA in('TDAO','TCAO') and  a.ORGINATING_JVR_NO is null and  a.ORGINATING_JVR_DATE is null and STATUS='L'";
                    ps1=connection.prepareStatement(sql);
                    ps1.setInt(1,AccountUnitCode);
                    ps1.setInt(2,CashBookMonth);
                    ps1.setInt(3,CashBookYear);
                    ResultSet rs=ps1.executeQuery();
                    while(rs.next())
                    {
                      if(a3==0)
                        {
                        tda_vo=rs.getString("VOUCHER_NO");
                        a3=1;
                        }
                        else {
                            tda_vo=tda_vo+","+rs.getString("VOUCHER_NO");
                        }
                        tda_count++;
                    }
                    
                    }
                    catch(Exception e)
                    {
                    System.out.println(e);
                    }
            
                    //tda_tca_monthEnd
                    if(AccountUnitCode!=5)
                    {
                    	rsflag=0;
			                    try{
			                    	String td="Select Verify_Flag From Fas_Tda_Tca_Monthend WHERE ACCOUNTING_UNIT_ID    =? AND " +
			                    			" Cashbook_Year           =? " +
			                    			" AND CASHBOOK_MONTH::numeric=?";
			                    	ps22=connection.prepareStatement(td);
			                    	ps22.setInt(1,AccountUnitCode);
			                    	ps22.setInt(2,CashBookYear);
			                    	ps22.setInt(3,CashBookMonth);
			                    	rs22=ps22.executeQuery();
			                    	while(rs22.next())
			                    	{
			                    		rsflag++;
			                    	}
			                    	
			                    }
			                    catch(Exception ee)
			                    {
			                    	System.out.println("excep::::"+ee);
			                    }
			                  //double check for Verification of TDA/TCA Register and Transactions - Every Month  
			                    
			                    try{

			                        String que="SELECT Unit_Id,\n" + 
			                        "    Cashbook_Month,\n" + 
			                        "    Account_Head_Code,\n" + 
			                        "    Trn_Dr,\n" + 
			                        "    Trn_Cr,\n" + 
			                        "    Tda_Dr,\n" + 
			                        "    Tda_Cr,\n" + 
			                        "    Trn_Net,\n" + 
			                        "    Tda_Net,\n" + 
			                        "    (Trn_Net-tda_net)AS difference\n" + 
			                        "  FROM\n" + 
			                        "    (SELECT aaa.Accounting_Unit_Id AS unit_id,\n" + 
			                        "      Aaa.Cashbook_Month,\n" + 
			                        "      Aaa.Account_Head_Code,\n" + 
			                        "      Aaa.Trn_Dr,\n" + 
			                        "      Aaa.Trn_Cr,\n" + 
			                        "      Bbb.Tda_Dr,\n" + 
			                        "      Bbb.Tda_Cr,\n" + 
			                        "      CASE\n" + 
			                        "        WHEN Aaa.Trn_Dr>Aaa.Trn_Cr\n" + 
			                        "        THEN (Aaa.Trn_Dr-Aaa.Trn_Cr)\n" + 
			                        "        WHEN Aaa.Trn_Cr>Aaa.Trn_Dr\n" + 
			                        "        THEN (Aaa.Trn_Cr-Aaa.Trn_Dr)\n" + 
			                        "        ELSE 0\n" + 
			                        "      END AS Trn_Net,\n" + 
			                        "      CASE\n" + 
			                        "        WHEN Bbb.Tda_Dr>Bbb.Tda_Cr\n" + 
			                        "        THEN (Bbb.Tda_Dr-Bbb.Tda_Cr)\n" + 
			                        "        WHEN Bbb.Tda_Cr>Bbb.Tda_Dr\n" + 
			                        "        THEN (Bbb.Tda_Cr-Bbb.Tda_Dr)\n" + 
			                        "        ELSE 0\n" + 
			                        "      END AS Tda_Net\n" + 
			                        "    FROM\n" + 
			                        "      (SELECT Accounting_Unit_Id,\n" + 
			                        "        Cashbook_Month,\n" + 
			                        "        Account_Head_Code,\n" + 
			                        "        SUM(dr_Amount)AS trn_dr,\n" + 
			                        "        SUM(cr_amount)AS trn_cr\n" + 
			                        "      FROM\n" + 
			                        "        (SELECT Accounting_Unit_Id,\n" + 
			                        "          Cashbook_Month,\n" + 
			                        "          Account_Head_Code,\n" + 
			                        "          SUM(dr_Amount)AS dr_Amount,\n" + 
			                        "          SUM(cr_amount)AS cr_amount\n" + 
			                        "        FROM\n" + 
			                        "          (SELECT B.Cashbook_Month,\n" + 
			                        "            B.Account_Head_Code,\n" + 
			                        "            B.Accounting_Unit_Id,\n" + 
			                        "            CASE\n" + 
			                        "              WHEN B.Cr_Dr_Indicator='DR'\n" + 
			                        "              THEN SUM(B.Amount)\n" + 
			                        "              ELSE 0\n" + 
			                        "            END AS dr_Amount,\n" + 
			                        "            CASE\n" + 
			                        "              WHEN b.Cr_Dr_Indicator='CR'\n" + 
			                        "              THEN SUM(B.Amount)\n" + 
			                        "              ELSE 0\n" + 
			                        "            END AS cr_amount\n" + 
			                        "          FROM fas_journal_master a,\n" + 
			                        "            Fas_Journal_Transaction b\n" + 
			                        "          WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
			                        "          AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
			                        "          AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
			                        "          AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
			                        "          AND A.Voucher_No              =B.Voucher_No\n" + 
			                        "          And A.Journal_Status          ='L'\n" + 
			                        "          and A.Accounting_Unit_Id=" +AccountUnitCode+ 
			                        "          AND A.Journal_Type_Code      IN (62,63,65,66)\n" + 
			                        "          AND A.Mode_Of_Creation        ='A'\n" + 
                       " AND a.created_by_module       ='GJV' "+
                       "     AND (a.SUPPLEMENT_NO         IS NULL "+
                       "     OR a.SUPPLEMENT_NO            =0) "+
			                        "          And B.Cashbook_Year           = " +CashBookYear+ 
			                        "          and B.Cashbook_Month=" +CashBookMonth+ 
			                        "          AND B.Account_Head_Code      IN (900108,900109,901001,901002)\n" + 
			                        "          GROUP BY B.Accounting_Unit_Id,\n" + 
			                        "            B.Cashbook_Month,\n" + 
			                        "            B.Account_Head_Code,\n" + 
			                        "            B.Cr_Dr_Indicator\n" + 
			                        "          UNION ALL\n" + 
			                        "          SELECT B.Cashbook_Month,\n" + 
			                        "            B.Account_Head_Code,\n" + 
			                        "            B.Accounting_Unit_Id,\n" + 
			                        "            CASE\n" + 
			                        "              WHEN B.Cr_Dr_Indicator='DR'\n" + 
			                        "              THEN SUM(B.Amount)\n" + 
			                        "              ELSE 0\n" + 
			                        "            END AS dr_Amount,\n" + 
			                        "            CASE\n" + 
			                        "              WHEN b.Cr_Dr_Indicator='CR'\n" + 
			                        "              THEN SUM(B.Amount)\n" + 
			                        "              ELSE 0\n" + 
			                        "            END AS cr_amount\n" + 
			                        "          FROM fas_journal_master a,\n" + 
			                        "            Fas_Journal_Transaction b\n" + 
			                        "          WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
			                        "          AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
			                        "          AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
			                        "          AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
			                        "          AND A.Voucher_No              =B.Voucher_No\n" + 
			                        "          And A.Journal_Status          ='L'\n" + 
			                        "          and A.Accounting_Unit_Id=" +AccountUnitCode+ 
			                        "          AND A.Journal_Type_Code      IN (54)\n" +
                       " AND (A.SUPPLEMENT_NO         IS NULL "+
                       "         OR A.SUPPLEMENT_NO            =0) "+
			                        "          And B.Cashbook_Year           = " +CashBookYear+
			                        "          and B.Cashbook_Month=" +CashBookMonth+
			                        "          AND B.Account_Head_Code      IN (900108,900109,901001,901002)\n" + 
			                        "          GROUP BY B.Accounting_Unit_Id,\n" + 
			                        "            B.Cashbook_Month,\n" + 
			                        "            B.Account_Head_Code,\n" + 
			                        "            B.Cr_Dr_Indicator\n" + 
			                        "          )as sm\n" + 
			                        "        GROUP BY Accounting_Unit_Id,\n" + 
			                        "          Cashbook_Month,\n" + 
			                        "          Account_Head_Code\n" + 
			                        "        UNION ALL\n" + 
			                        "        SELECT B.Accounting_Unit_Id,\n" + 
			                        "          B.Cashbook_Month,\n" + 
			                        "          B.Account_Head_Code,\n" + 
			                        "          CASE\n" + 
			                        "            WHEN B.Cr_Dr_Indicator='DR'\n" + 
			                        "            THEN SUM(B.Amount)\n" + 
			                        "            ELSE 0\n" + 
			                        "          END AS dr_Amount,\n" + 
			                        "          CASE\n" + 
			                        "            WHEN b.Cr_Dr_Indicator='CR'\n" + 
			                        "            THEN SUM(B.Amount)\n" + 
			                        "            ELSE 0\n" + 
			                        "          END AS cr_amount\n" + 
			                        "        FROM FAS_PAYMENT_MASTER a,\n" + 
			                        "          FAS_PAYMENT_TRANSACTION b\n" + 
			                        "        WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
			                        "        AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
			                        "        AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
			                        "        AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
			                        "        And A.Voucher_No              =B.Voucher_No\n" + 
			                        "        and A.Accounting_Unit_Id=" +AccountUnitCode+ 
			                        "        AND A.Payment_Status          ='L'\n" + 
			                        "        And B.Cashbook_Year           = " +CashBookYear+
			                        "        and B.Cashbook_Month=" +CashBookMonth+
			                        "        AND B.Account_Head_Code      IN (900108,900109,901001,901002)\n" + 
			                        "        GROUP BY B.Accounting_Unit_Id,\n" + 
			                        "          B.Cashbook_Month,\n" + 
			                        "          B.Account_Head_Code,\n" + 
			                        "          B.Cr_Dr_Indicator\n" + 
			                        "        UNION ALL\n" + 
			                        "        SELECT B.Accounting_Unit_Id,\n" + 
			                        "          B.Cashbook_Month,\n" + 
			                        "          B.Account_Head_Code,\n" + 
			                        "          CASE\n" + 
			                        "            WHEN B.Cr_Dr_Indicator='DR'\n" + 
			                        "            THEN SUM(B.Amount)\n" + 
			                        "            ELSE 0\n" + 
			                        "          END AS dr_Amount,\n" + 
			                        "          CASE\n" + 
			                        "            WHEN b.Cr_Dr_Indicator='CR'\n" + 
			                        "            THEN SUM(B.Amount)\n" + 
			                        "            ELSE 0\n" + 
			                        "          END AS cr_amount\n" + 
			                        "        FROM FAS_RECEIPT_MASTER a,\n" + 
			                        "          FAS_RECEIPT_TRANSACTION b\n" + 
			                        "        WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
			                        "        AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
			                        "        AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
			                        "        AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
			                        "        AND A.RECEIPT_NO              =B.RECEIPT_NO\n" + 
			                        "        And A.Receipt_Status          ='L'\n" + 
			                        "        and A.Accounting_Unit_Id=" +AccountUnitCode+ 
			                        "        And B.Cashbook_Year           = " +CashBookYear+
			                        "        and B.Cashbook_Month=" +CashBookMonth+ 
			                        "        AND B.Account_Head_Code      IN (900108,900109,901001,901002)\n" + 
			                        "        GROUP BY B.Accounting_Unit_Id,\n" + 
			                        "          B.Cashbook_Month,\n" + 
			                        "          B.Account_Head_Code,\n" + 
			                        "          B.Cr_Dr_Indicator\n" + 
			                        "        )as sm\n" + 
			                        "      GROUP BY Accounting_Unit_Id,\n" + 
			                        "        Cashbook_Month,\n" + 
			                        "        Account_Head_Code\n" + 
			                        "      ORDER BY Accounting_Unit_Id,\n" + 
			                        "        Cashbook_Month,\n" + 
			                        "        Account_Head_Code\n" + 
			                        "      )aaa\n" + 
			                       // "    LEFT OUTER JOIN\n" + 
			                        "    FULL OUTER JOIN\n" + 
			                        "      (SELECT Accounting_Unit_Id,\n" + 
			                        "        Cashbook_Month,\n" + 
			                        "        Account_Head_Code,\n" + 
			                        "        SUM(dr_Amount)AS tda_dr,\n" + 
			                        "        SUM(cr_amount)AS tda_cr\n" + 
			                        "      FROM\n" + 
			                        "        (SELECT B.Accounting_Unit_Id,\n" + 
			                        "          B.Cashbook_Month,\n" + 
			                        "          B.Account_Head_Code,\n" + 
			                        "          CASE\n" + 
			                        "            WHEN B.Cr_Dr_Indicator='DR'\n" + 
			                        "            THEN SUM(B.Amount)\n" + 
			                        "            ELSE 0\n" + 
			                        "          END AS dr_Amount,\n" + 
			                        "          CASE\n" + 
			                        "            WHEN b.Cr_Dr_Indicator='CR'\n" + 
			                        "            THEN SUM(B.Amount)\n" + 
			                        "            ELSE 0\n" + 
			                        "          END AS cr_amount\n" + 
			                        "        FROM Fas_Tda_Tca_Raised_Mst A,\n" + 
			                        "          Fas_Tda_Tca_Raised_Trn B\n" + 
			                        "        WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
			                        "        AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
			                        "        AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
			                        "        AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
			                        "        AND A.Voucher_No              =B.Voucher_No\n" + 
			                        "        And A.Status                  ='L'\n" + 
			                        "        and A.Accounting_Unit_Id=" +AccountUnitCode+ 
			                        "        And B.Cashbook_Year           = " +CashBookYear+
			                        "        and B.Cashbook_Month=" +CashBookMonth+
	                     "   AND (a.SUPPLEMENT_NO         IS NULL "+
	                       " 	      OR a.SUPPLEMENT_NO            =0) "+
			                        "        AND B.Account_Head_Code      IN (900108,900109,901001,901002)\n" + 
			                        "        GROUP BY B.Accounting_Unit_Id,\n" + 
			                        "          B.Cashbook_Month,\n" + 
			                        "          B.Account_Head_Code,\n" + 
			                        "          B.Cr_Dr_Indicator\n" + 
			                        "        ORDER BY B.Accounting_Unit_Id,\n" + 
			                        "          B.Cashbook_Month,\n" + 
			                        "          b.Account_Head_Code\n" + 
			                        "        )as sm \n" + 
			                        "      GROUP BY Accounting_Unit_Id,\n" + 
			                        "        Cashbook_Month,\n" + 
			                        "        Account_Head_Code\n" + 
			                        "      ORDER BY Accounting_Unit_Id,\n" + 
			                        "        Cashbook_Month,\n" + 
			                        "        Account_Head_Code\n" + 
			                        "      )Bbb\n" + 
			                        "    ON aaa.Accounting_Unit_Id=bbb.Accounting_Unit_Id\n" + 
			                        "    AND Aaa.Cashbook_Month   =Bbb.Cashbook_Month\n" + 
			                        "    AND Aaa.Account_Head_Code=Bbb.Account_Head_Code\n" + 
			                        "    )as sm where (Trn_Net-tda_net)!=0";
			                       
			                   	 System.out.println("que:::"+que);
			                                    ps = connection.prepareStatement(que);
			                                    result_tda = ps.executeQuery();
			                                    while(result_tda.next())
			                                    {
			                                    diff_error++;	
			                                    }
			                                    System.out
														.println("diff_error "+diff_error);
			                    }
			                    catch(Exception et)
			                    {
			                    	System.out.println("excep in tda_monthend::::"+et);
			                    }
			                    
			                    
    				}
                    // for Adjustment_Memo Verification checking
                    
                    if(AccountUnitCode!=5)
                    {
                    	rsflag_adj=0;
			                    try{
			                    	String td="Select Verify_Flag From Fas_AdjMemo_Monthend WHERE ACCOUNTING_UNIT_ID    =? AND " +
			                    			" Cashbook_Year           =? " +
			                    			" AND CASHBOOK_MONTH::numeric=?";
			                    	ps22=connection.prepareStatement(td);
			                    	ps22.setInt(1,AccountUnitCode);
			                    	ps22.setInt(2,CashBookYear);
			                    	ps22.setInt(3,CashBookMonth);
			                    	rs22=ps22.executeQuery();
			                    	while(rs22.next())
			                    	{
			                    		rsflag_adj++;
			                    	}
			                    	
			                    }
			                    catch(Exception ee)
			                    {
			                    	System.out.println("excep::::"+ee);
			                    }
			                  //double check for Verification of TDA/TCA Register and Transactions - Every Month  
			                    
			                    try{

			                        String que="";
			                        
			                    	if(AccountUnitCode==5)
			      	              {
			                  	  que="SELECT Unit_Id,\n" + 
			                  	 		"  (SELECT U.Accounting_Unit_Name\n" + 
			                  	 		"  FROM Fas_Mst_Acct_Units u\n" + 
			                  	 		"  WHERE U.Accounting_Unit_Id=unit_id\n" + 
			                  	 		"  )AS unit_name,\n" + 
			                  	 		"  Cashbook_Month,\n" + 
			                  	 		"  CASE\n" + 
			                  	 		"    WHEN Cashbook_Month=1\n" + 
			                  	 		"    THEN 'January'\n" + 
			                  	 		"    WHEN Cashbook_Month=2\n" + 
			                  	 		"    THEN 'February'\n" + 
			                  	 		"    WHEN Cashbook_Month=3\n" + 
			                  	 		"    THEN 'March'\n" + 
			                  	 		"    WHEN Cashbook_Month=4\n" + 
			                  	 		"    THEN 'April'\n" + 
			                  	 		"    WHEN Cashbook_Month=5\n" + 
			                  	 		"    THEN 'May'\n" + 
			                  	 		"    WHEN Cashbook_Month=6\n" + 
			                  	 		"    THEN 'June'\n" + 
			                  	 		"    WHEN Cashbook_Month=7\n" + 
			                  	 		"    THEN 'July'\n" + 
			                  	 		"    WHEN Cashbook_Month=8\n" + 
			                  	 		"    THEN 'August'\n" + 
			                  	 		"    WHEN Cashbook_Month=9\n" + 
			                  	 		"    THEN 'September'\n" + 
			                  	 		"    WHEN Cashbook_Month=10\n" + 
			                  	 		"    THEN 'October'\n" + 
			                  	 		"    WHEN Cashbook_Month=11\n" + 
			                  	 		"    THEN 'November'\n" + 
			                  	 		"    WHEN Cashbook_Month=12\n" + 
			                  	 		"    THEN 'December'\n" + 
			                  	 		"  END AS month_desc,\n" + 
			                  	 		"  ACCOUNT_HEAD_CODE,\n" + 
			                  	 		"  TRN_DR,\n" + 
			                  	 		"  TRN_CR,\n" + 
//			                  	 		"  AdjMemo_Dr,\n" + 
//			                  	 		"  AdjMemo_Cr,\n" + 			
										"case when (ADJMEMO_DR=NULL) then 0 else ADJMEMO_DR end as AdjMemo_Dr,"+
										//" DECODE(ADJMEMO_DR,NULL,0,ADJMEMO_DR) as AdjMemo_Dr,\n" + 
										"case when (AdjMemo_Cr=NULL) then 0 else AdjMemo_Cr end as AdjMemo_Cr,"+
			      						//"  decode(AdjMemo_Cr,NULL,0,AdjMemo_Cr) as AdjMemo_Cr,\n" +
			                  	 		"  Trn_Net,\n" + 
			                  	 		"  AdjMemo_Net,\n" + 
			                  	 		"  (Trn_Net-AdjMemo_net)AS difference\n" + 
			                  	 		"FROM\n" + 
			                  	 		"  (SELECT aaa.Accounting_Unit_Id AS unit_id,\n" + 
			                  	 		"    Aaa.Cashbook_Month,\n" + 
			                  	 		"    Aaa.Account_Head_Code,\n" + 
			                  	 		"    Aaa.Trn_Dr,\n" + 
			                  	 		"    Aaa.Trn_Cr,\n" + 
			                  	 		"    Bbb.AdjMemo_Dr,\n" + 
			                  	 		"    Bbb.AdjMemo_Cr,\n" + 
			                  	 		"    CASE\n" + 
			                  	 		"      WHEN Aaa.Trn_Dr>Aaa.Trn_Cr\n" + 
			                  	 		"      THEN (Aaa.Trn_Dr-Aaa.Trn_Cr)\n" + 
			                  	 		"      WHEN Aaa.Trn_Cr>Aaa.Trn_Dr\n" + 
			                  	 		"      THEN (Aaa.Trn_Cr-Aaa.Trn_Dr)\n" + 
			                  	 		"      ELSE 0\n" + 
			                  	 		"    END AS Trn_Net,\n" + 
			                  	 		"    CASE\n" + 
			                  	 		"      WHEN Bbb.AdjMemo_Dr>Bbb.AdjMemo_Cr\n" + 
			                  	 		"      THEN (Bbb.AdjMemo_Dr-Bbb.AdjMemo_Cr)\n" + 
			                  	 		"      WHEN Bbb.AdjMemo_Cr>Bbb.AdjMemo_Dr\n" + 
			                  	 		"      THEN (Bbb.AdjMemo_Cr-Bbb.AdjMemo_Dr)\n" + 
			                  	 		"      ELSE 0\n" + 
			                  	 		"    END AS AdjMemo_Net\n" + 
			                  	 		"  FROM\n" + 
			                  	 		"    (SELECT Accounting_Unit_Id,\n" + 
			                  	 		"      Cashbook_Month,\n" + 
			                  	 		"      Account_Head_Code,\n" + 
			                  	 		"      SUM(dr_Amount)AS trn_dr,\n" + 
			                  	 		"      SUM(cr_amount)AS trn_cr\n" + 
			                  	 		"    FROM\n" + 
			                  	 		"      (SELECT Accounting_Unit_Id,\n" + 
			                  	 		"        Cashbook_Month,\n" + 
			                  	 		"        Account_Head_Code,\n" + 
			                  	 		"        SUM(dr_Amount)AS dr_Amount,\n" + 
			                  	 		"        SUM(cr_amount)AS cr_amount\n" + 
			                  	 		"      FROM\n" + 
			                  	 		"        (SELECT B.Cashbook_Month,\n" + 
			                  	 		"          B.Account_Head_Code,\n" + 
			                  	 		"          B.Accounting_Unit_Id,\n" + 
			                  	 		"          CASE\n" + 
			                  	 		"            WHEN B.Cr_Dr_Indicator='DR'\n" + 
			                  	 		"            THEN SUM(B.Amount)\n" + 
			                  	 		"            ELSE 0\n" + 
			                  	 		"          END AS dr_Amount,\n" + 
			                  	 		"          CASE\n" + 
			                  	 		"            WHEN b.Cr_Dr_Indicator='CR'\n" + 
			                  	 		"            THEN SUM(B.Amount)\n" + 
			                  	 		"            ELSE 0\n" + 
			                  	 		"          END AS cr_amount\n" + 
			                  	 		"        FROM fas_journal_master a,\n" + 
			                  	 		"          Fas_Journal_Transaction b\n" + 
			                  	 		"        WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
			                  	 		"        AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
			                  	 		"        AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
			                  	 		"        AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
			                  	 		"        AND A.Voucher_No              =B.Voucher_No\n" + 
			                  	 		"        AND A.JOURNAL_STATUS          ='L'\n" + 
			                  	 		"        AND B.Accounting_Unit_Id      =" +AccountUnitCode +
			                  	 		"        AND (a.SUPPLEMENT_NO         IS NULL\n" + 
			                  	 		"        OR a.SUPPLEMENT_NO            =0)\n" + 
			                  	 		"        AND B.CASHBOOK_YEAR           =" +CashBookYear+
			                  	 		"        AND B.CASHBOOK_MONTH          =" +CashBookMonth+ 
			                  	 		"        AND B.Account_Head_Code      IN (610101,610102)\n" + 
			                  	 		"        GROUP BY B.Accounting_Unit_Id,\n" + 
			                  	 		"          B.Cashbook_Month,\n" + 
			                  	 		"          B.Account_Head_Code,\n" + 
			                  	 		"          B.Cr_Dr_Indicator\n" + 
			                  	 		"        )as sm\n" + 
			                  	 		"      GROUP BY Accounting_Unit_Id,\n" + 
			                  	 		"        Cashbook_Month,\n" + 
			                  	 		"        Account_Head_Code\n" + 
			                  	 		"      UNION ALL\n" + 
			                  	 		"      SELECT B.Accounting_Unit_Id,\n" + 
			                  	 		"        B.Cashbook_Month,\n" + 
			                  	 		"        B.Account_Head_Code,\n" + 
			                  	 		"        CASE\n" + 
			                  	 		"          WHEN B.Cr_Dr_Indicator='DR'\n" + 
			                  	 		"          THEN SUM(B.Amount)\n" + 
			                  	 		"          ELSE 0\n" + 
			                  	 		"        END AS dr_Amount,\n" + 
			                  	 		"        CASE\n" + 
			                  	 		"          WHEN b.Cr_Dr_Indicator='CR'\n" + 
			                  	 		"          THEN SUM(B.Amount)\n" + 
			                  	 		"          ELSE 0\n" + 
			                  	 		"        END AS cr_amount\n" + 
			                  	 		"      FROM FAS_PAYMENT_MASTER a,\n" + 
			                  	 		"        FAS_PAYMENT_TRANSACTION b\n" + 
			                  	 		"      WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
			                  	 		"      AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
			                  	 		"      AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
			                  	 		"      AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
			                  	 		"      AND A.Voucher_No              =B.Voucher_No\n" + 
			                  	 		"      AND B.Accounting_Unit_Id      =" +AccountUnitCode +
			                  	 		"      AND A.Payment_Status          ='L'\n" + 
			                  	 		"      AND B.Cashbook_Year           =" +CashBookYear+
			                  	 		"      AND B.CASHBOOK_MONTH          =" +CashBookMonth+ 
			                  	 		"      AND B.Account_Head_Code      IN (610101,610102)\n" + 
			                  	 		"      GROUP BY B.Accounting_Unit_Id,\n" + 
			                  	 		"        B.Cashbook_Month,\n" + 
			                  	 		"        B.Account_Head_Code,\n" + 
			                  	 		"        B.Cr_Dr_Indicator\n" + 
			                  	 		"      UNION ALL\n" + 
			                  	 		"      SELECT B.Accounting_Unit_Id,\n" + 
			                  	 		"        B.Cashbook_Month,\n" + 
			                  	 		"        B.Account_Head_Code,\n" + 
			                  	 		"        CASE\n" + 
			                  	 		"          WHEN B.Cr_Dr_Indicator='DR'\n" + 
			                  	 		"          THEN SUM(B.Amount)\n" + 
			                  	 		"          ELSE 0\n" + 
			                  	 		"        END AS dr_Amount,\n" + 
			                  	 		"        CASE\n" + 
			                  	 		"          WHEN b.Cr_Dr_Indicator='CR'\n" + 
			                  	 		"          THEN SUM(B.Amount)\n" + 
			                  	 		"          ELSE 0\n" + 
			                  	 		"        END AS cr_amount\n" + 
			                  	 		"      FROM FAS_RECEIPT_MASTER a,\n" + 
			                  	 		"        FAS_RECEIPT_TRANSACTION b\n" + 
			                  	 		"      WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
			                  	 		"      AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
			                  	 		"      AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
			                  	 		"      AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
			                  	 		"      AND A.RECEIPT_NO              =B.RECEIPT_NO\n" + 
			                  	 		"      AND A.RECEIPT_STATUS          ='L'\n" + 
			                  	 		"      AND B.Accounting_Unit_Id      ="+AccountUnitCode +
			                  	 		"      AND B.CASHBOOK_YEAR           ="+CashBookYear+
			                  	 		"      AND B.CASHBOOK_MONTH          ="+CashBookMonth+ 
			                  	 		"      AND B.Account_Head_Code      IN (610101,610102)" + 
			                  	 		"      GROUP BY B.Accounting_Unit_Id,\n" + 
			                  	 		"        B.Cashbook_Month,\n" + 
			                  	 		"        B.Account_Head_Code,\n" + 
			                  	 		"        B.Cr_Dr_Indicator\n" + 
			                  	 		"      )as sm \n" + 
			                  	 		"    GROUP BY Accounting_Unit_Id,\n" + 
			                  	 		"      Cashbook_Month,\n" + 
			                  	 		"      Account_Head_Code\n" + 
			                  	 		"    ORDER BY Accounting_Unit_Id,\n" + 
			                  	 		"      Cashbook_Month,\n" + 
			                  	 		"      Account_Head_Code\n" + 
			                  	 		"    )aaa\n" + 
			                  	 		"  FULL OUTER JOIN\n" + 
			                  	 		"    (SELECT Accounting_Unit_Id,\n" + 
			                  	 		"      Cashbook_Month,\n" + 
			                  	 		"      Account_Head_Code,\n" + 
			                  	 		"      SUM(dr_Amount)AS AdjMemo_dr,\n" + 
			                  	 		"      SUM(cr_amount)AS AdjMemo_cr\n" + 
			                  	 		"    FROM\n" + 
			                  	 		"      (SELECT B.Accounting_Unit_Id,\n" + 
			                  	 		"        B.Cashbook_Month,\n" + 
			                  	 		"        B.Account_Head_Code,\n" + 
			                  	 		"        CASE\n" + 
			                  	 		"          WHEN B.CR_DR_TYPE='DR'\n" + 
			                  	 		"          THEN SUM(B.Amount)\n" + 
			                  	 		"          ELSE 0\n" + 
			                  	 		"        END AS dr_Amount,\n" + 
			                  	 		"        CASE\n" + 
			                  	 		"          WHEN b.CR_DR_TYPE='CR'\n" + 
			                  	 		"          THEN SUM(B.Amount)\n" + 
			                  	 		"          ELSE 0\n" + 
			                  	 		"        END AS cr_amount\n" + 
			                  	 		"      FROM FAS_ADJUST_MEMO_MST A,\n" + 
			                  	 		"        FAS_ADJUST_MEMO_TRN B\n" + 
			                  	 		"      WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
			                  	 		"      AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
			                  	 		"      AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
			                  	 		"      AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
			                  	 		"      AND A.VOUCHER_NO              =B.VOUCHER_NO\n" + 
			                  	 		"      AND A.MEMO_STATUS             ='L'\n" + 
			                  	 		"      AND b.ACCOUNTING_UNIT_ID      =" +AccountUnitCode +
			                  	 		"      AND B.CASHBOOK_YEAR           =" +CashBookYear+  
			                  	 		"      AND B.CASHBOOK_MONTH          =" +CashBookMonth+ 
			                  	 		"      AND B.Account_Head_Code      IN (610101,610102)\n" + 
			                  	 		"      GROUP BY B.Accounting_Unit_Id,\n" + 
			                  	 		"        B.Cashbook_Month,\n" + 
			                  	 		"        B.Account_Head_Code,\n" + 
			                  	 		"        B.CR_DR_TYPE\n" + 
			                  	 		"      ORDER BY B.Accounting_Unit_Id,\n" + 
			                  	 		"        B.Cashbook_Month,\n" + 
			                  	 		"        b.Account_Head_Code\n" + 
			                  	 		"      ) as sm\n" + 
			                  	 		"    GROUP BY Accounting_Unit_Id,\n" + 
			                  	 		"      Cashbook_Month,\n" + 
			                  	 		"      Account_Head_Code\n" + 
			                  	 		"    ORDER BY Accounting_Unit_Id,\n" + 
			                  	 		"      Cashbook_Month,\n" + 
			                  	 		"      Account_Head_Code\n" + 
			                  	 		"    )Bbb\n" + 
			                  	 		"  ON aaa.Accounting_Unit_Id=bbb.Accounting_Unit_Id\n" + 
			                  	 		"  AND Aaa.Cashbook_Month   =Bbb.Cashbook_Month::numeric\n" + 
			                  	 		"  AND AAA.ACCOUNT_HEAD_CODE=BBB.ACCOUNT_HEAD_CODE::numeric \n" + 
			                  	 		"  ) sm where (Trn_Net-AdjMemo_net)!=0";
			      	              }
			      	              else
			      	              {
			      	            	  que="SELECT Unit_Id,\n" + 
			      	            	  		"  (SELECT U.Accounting_Unit_Name\n" + 
			      	            	  		"  FROM Fas_Mst_Acct_Units u\n" + 
			      	            	  		"  WHERE U.Accounting_Unit_Id=unit_id\n" + 
			      	            	  		"  )AS unit_name,\n" + 
			      	            	  		"  Cashbook_Month,\n" + 
			      	            	  		"  CASE\n" + 
			      	            	  		"    WHEN Cashbook_Month=1\n" + 
			      	            	  		"    THEN 'January'\n" + 
			      	            	  		"    WHEN Cashbook_Month=2\n" + 
			      	            	  		"    THEN 'February'\n" + 
			      	            	  		"    WHEN Cashbook_Month=3\n" + 
			      	            	  		"    THEN 'March'\n" + 
			      	            	  		"    WHEN Cashbook_Month=4\n" + 
			      	            	  		"    THEN 'April'\n" + 
			      	            	  		"    WHEN Cashbook_Month=5\n" + 
			      	            	  		"    THEN 'May'\n" + 
			      	            	  		"    WHEN Cashbook_Month=6\n" + 
			      	            	  		"    THEN 'June'\n" + 
			      	            	  		"    WHEN Cashbook_Month=7\n" + 
			      	            	  		"    THEN 'July'\n" + 
			      	            	  		"    WHEN Cashbook_Month=8\n" + 
			      	            	  		"    THEN 'August'\n" + 
			      	            	  		"    WHEN Cashbook_Month=9\n" + 
			      	            	  		"    THEN 'September'\n" + 
			      	            	  		"    WHEN Cashbook_Month=10\n" + 
			      	            	  		"    THEN 'October'\n" + 
			      	            	  		"    WHEN Cashbook_Month=11\n" + 
			      	            	  		"    THEN 'November'\n" + 
			      	            	  		"    WHEN Cashbook_Month=12\n" + 
			      	            	  		"    THEN 'December'\n" + 
			      	            	  		"  END AS month_desc,\n" + 
			      	            	  		"  ACCOUNT_HEAD_CODE,\n" + 
			      	            	  		"  TRN_DR,\n" + 
			      	            	  		"  TRN_CR,\n" + 
			      	            	  		"case when (ADJMEMO_DR=NULL) then 0 else ADJMEMO_DR end as ADJMEMO_DR,"+
			      	            	  		//"  DECODE(ADJMEMO_DR,NULL,0,ADJMEMO_DR) AS AdjMemo_Dr,\n" + 
			      	            	  		"case when (AdjMemo_Cr=NULL) then 0 else AdjMemo_Cr end as AdjMemo_Cr,"+
			      	            	  		//"  DECODE(AdjMemo_Cr,NULL,0,AdjMemo_Cr) AS AdjMemo_Cr,\n" + 
			      	            	  		"  Trn_Net,\n" + 
			      	            	  		"  AdjMemo_Net,\n" + 
			      	            	  		"  (Trn_Net-AdjMemo_net)AS difference\n" + 
			      	            	  		"FROM\n" + 
			      	            	  		"  (SELECT aaa.Accounting_Unit_Id AS unit_id,\n" + 
			      	            	  		"    Aaa.Cashbook_Month,\n" + 
			      	            	  		"    Aaa.Account_Head_Code,\n" + 
			      	            	  		"    Aaa.Trn_Dr,\n" + 
			      	            	  		"    Aaa.Trn_Cr,\n" + 
			      	            	  		"    Bbb.AdjMemo_Dr,\n" + 
			      	            	  		"    Bbb.AdjMemo_Cr,\n" + 
			      	            	  		"    CASE\n" + 
			      	            	  		"      WHEN Aaa.Trn_Dr>Aaa.Trn_Cr\n" + 
			      	            	  		"      THEN (Aaa.Trn_Dr-Aaa.Trn_Cr)\n" + 
			      	            	  		"      WHEN Aaa.Trn_Cr>Aaa.Trn_Dr\n" + 
			      	            	  		"      THEN (Aaa.Trn_Cr-Aaa.Trn_Dr)\n" + 
			      	            	  		"      ELSE 0\n" + 
			      	            	  		"    END AS Trn_Net,\n" + 
			      	            	  		"    CASE\n" + 
			      	            	  		"      WHEN Bbb.AdjMemo_Dr>Bbb.AdjMemo_Cr\n" + 
			      	            	  		"      THEN (Bbb.AdjMemo_Dr-Bbb.AdjMemo_Cr)\n" + 
			      	            	  		"      WHEN Bbb.AdjMemo_Cr>Bbb.AdjMemo_Dr\n" + 
			      	            	  		"      THEN (Bbb.AdjMemo_Cr-Bbb.AdjMemo_Dr)\n" + 
			      	            	  		"      ELSE 0\n" + 
			      	            	  		"    END AS AdjMemo_Net\n" + 
			      	            	  		"  FROM\n" + 
			      	            	  		"    (SELECT Accounting_Unit_Id,\n" + 
			      	            	  		"      Cashbook_Month,\n" + 
			      	            	  		"      Account_Head_Code,\n" + 
			      	            	  		"      SUM(dr_Amount)AS trn_dr,\n" + 
			      	            	  		"      SUM(cr_amount)AS trn_cr\n" + 
			      	            	  		"    FROM\n" + 
			      	            	  		"      (SELECT Accounting_Unit_Id,\n" + 
			      	            	  		"        Cashbook_Month,\n" + 
			      	            	  		"        Account_Head_Code,\n" + 
			      	            	  		"        SUM(dr_Amount)AS dr_Amount,\n" + 
			      	            	  		"        SUM(cr_amount)AS cr_amount\n" + 
			      	            	  		"      FROM\n" + 
			      	            	  		"        (SELECT B.Cashbook_Month,\n" + 
			      	            	  		"          B.Account_Head_Code,\n" + 
			      	            	  		"          B.Accounting_Unit_Id,\n" + 
			      	            	  		"          CASE\n" + 
			      	            	  		"            WHEN B.Cr_Dr_Indicator='DR'\n" + 
			      	            	  		"            THEN SUM(B.Amount)\n" + 
			      	            	  		"            ELSE 0\n" + 
			      	            	  		"          END AS dr_Amount,\n" + 
			      	            	  		"          CASE\n" + 
			      	            	  		"            WHEN b.Cr_Dr_Indicator='CR'\n" + 
			      	            	  		"            THEN SUM(B.Amount)\n" + 
			      	            	  		"            ELSE 0\n" + 
			      	            	  		"          END AS cr_amount\n" + 
			      	            	  		"        FROM fas_journal_master a,\n" + 
			      	            	  		"          Fas_Journal_Transaction b\n" + 
			      	            	  		"        WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
			      	            	  		"        AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
			      	            	  		"        AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
			      	            	  		"        AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
			      	            	  		"        AND A.Voucher_No              =B.Voucher_No\n" + 
			      	            	  		"        AND A.JOURNAL_STATUS          ='L'\n" + 
			      	            	  		"        AND b.Accounting_Unit_Id      =" +AccountUnitCode +
			      	            	  		"        AND (a.SUPPLEMENT_NO         IS NULL\n" + 
			      	            	  		"        OR a.SUPPLEMENT_NO            =0)\n" + 
			      	            	  		"        AND B.CASHBOOK_YEAR           =" +CashBookYear+ 
			      	            	  		"        AND B.CASHBOOK_MONTH          =" +CashBookMonth+  
			      	            	  		"        AND B.Account_Head_Code      IN (610102)\n" + 
			      	            	  		"        GROUP BY B.Accounting_Unit_Id,\n" + 
			      	            	  		"          B.Cashbook_Month,\n" + 
			      	            	  		"          B.Account_Head_Code,\n" + 
			      	            	  		"          B.Cr_Dr_Indicator\n" + 
			      	            	  		"        )as sm\n" + 
			      	            	  		"      GROUP BY Accounting_Unit_Id,\n" + 
			      	            	  		"        Cashbook_Month,\n" + 
			      	            	  		"        Account_Head_Code\n" + 
			      	            	  		"      UNION ALL\n" + 
			      	            	  		"      SELECT B.Accounting_Unit_Id,\n" + 
			      	            	  		"        B.Cashbook_Month,\n" + 
			      	            	  		"        B.Account_Head_Code,\n" + 
			      	            	  		"        CASE\n" + 
			      	            	  		"          WHEN B.Cr_Dr_Indicator='DR'\n" + 
			      	            	  		"          THEN SUM(B.Amount)\n" + 
			      	            	  		"          ELSE 0\n" + 
			      	            	  		"        END AS dr_Amount,\n" + 
			      	            	  		"        CASE\n" + 
			      	            	  		"          WHEN b.Cr_Dr_Indicator='CR'\n" + 
			      	            	  		"          THEN SUM(B.Amount)\n" + 
			      	            	  		"          ELSE 0\n" + 
			      	            	  		"        END AS cr_amount\n" + 
			      	            	  		"      FROM FAS_PAYMENT_MASTER a,\n" + 
			      	            	  		"        FAS_PAYMENT_TRANSACTION b\n" + 
			      	            	  		"      WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
			      	            	  		"      AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
			      	            	  		"      AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
			      	            	  		"      AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
			      	            	  		"      AND A.VOUCHER_NO              =B.VOUCHER_NO\n" + 
			      	            	  		"      AND B.Accounting_Unit_Id      =" +AccountUnitCode +
			      	            	  		"      AND A.Payment_Status          ='L'\n" + 
			      	            	  		"      AND B.Cashbook_Year           =" +CashBookYear+ 
			      	            	  		"      AND B.CASHBOOK_MONTH          =" +CashBookMonth+  
			      	            	  		"      AND B.Account_Head_Code      IN (610102)\n" + 
			      	            	  		"      GROUP BY B.Accounting_Unit_Id,\n" + 
			      	            	  		"        B.Cashbook_Month,\n" + 
			      	            	  		"        B.Account_Head_Code,\n" + 
			      	            	  		"        B.Cr_Dr_Indicator\n" + 
			      	            	  		"      UNION ALL\n" + 
			      	            	  		"      SELECT B.Accounting_Unit_Id,\n" + 
			      	            	  		"        B.Cashbook_Month,\n" + 
			      	            	  		"        B.Account_Head_Code,\n" + 
			      	            	  		"        CASE\n" + 
			      	            	  		"          WHEN B.Cr_Dr_Indicator='DR'\n" + 
			      	            	  		"          THEN SUM(B.Amount)\n" + 
			      	            	  		"          ELSE 0\n" + 
			      	            	  		"        END AS dr_Amount,\n" + 
			      	            	  		"        CASE\n" + 
			      	            	  		"          WHEN b.Cr_Dr_Indicator='CR'\n" + 
			      	            	  		"          THEN SUM(B.Amount)\n" + 
			      	            	  		"          ELSE 0\n" + 
			      	            	  		"        END AS cr_amount\n" + 
			      	            	  		"      FROM FAS_RECEIPT_MASTER a,\n" + 
			      	            	  		"        FAS_RECEIPT_TRANSACTION b\n" + 
			      	            	  		"      WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
			      	            	  		"      AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
			      	            	  		"      AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
			      	            	  		"      AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
			      	            	  		"      AND A.RECEIPT_NO              =B.RECEIPT_NO\n" + 
			      	            	  		"      AND A.RECEIPT_STATUS          ='L'\n" + 
			      	            	  		"      AND B.Accounting_Unit_Id      =" +AccountUnitCode +
			      	            	  		"      AND B.CASHBOOK_YEAR           =" +CashBookYear+ 
			      	            	  		"      AND B.CASHBOOK_MONTH          =" +CashBookMonth+  
			      	            	  		"      AND B.Account_Head_Code      IN (610102)\n" + 
			      	            	  		"      GROUP BY B.Accounting_Unit_Id,\n" + 
			      	            	  		"        B.Cashbook_Month,\n" + 
			      	            	  		"        B.Account_Head_Code,\n" + 
			      	            	  		"        B.Cr_Dr_Indicator\n" + 
			      	            	  		"      )as sm \n" + 
			      	            	  		"    GROUP BY Accounting_Unit_Id,\n" + 
			      	            	  		"      Cashbook_Month,\n" + 
			      	            	  		"      Account_Head_Code\n" + 
			      	            	  		"    ORDER BY Accounting_Unit_Id,\n" + 
			      	            	  		"      Cashbook_Month,\n" + 
			      	            	  		"      Account_Head_Code\n" + 
			      	            	  		"    )aaa\n" + 
			      	            	  		"  FULL OUTER JOIN\n" + 
			      	            	  		"    (SELECT FOR_ACCOUNTING_UNIT_ID,\n" + 
			      	            	  		"      CASHBOOK_MONTH,\n" + 
			      	            	  		"      '610102' as Account_Head_Code,\n" + 
			      	            	  		"      SUM(dr_Amount)AS AdjMemo_dr,\n" + 
			      	            	  		"      SUM(cr_amount)AS AdjMemo_cr\n" + 
			      	            	  		"    FROM\n" + 
			      	            	  		"      (SELECT B.FOR_ACCOUNTING_UNIT_ID,\n" + 
			      	            	  		"        TO_CHAR( B.ACCEPT_VOUCHER_DATE, 'mm' ) AS Cashbook_Month,\n" + 
			      	            	  		"        B.Account_Head_Code,\n" + 
			      	            	  		"        CASE\n" + 
			      	            	  		"          WHEN B.CR_DR_TYPE='DR'\n" + 
			      	            	  		"          THEN SUM(B.Amount)\n" + 
			      	            	  		"          ELSE 0\n" + 
			      	            	  		"        END AS dr_Amount,\n" + 
			      	            	  		"        CASE\n" + 
			      	            	  		"          WHEN b.CR_DR_TYPE='CR'\n" + 
			      	            	  		"          THEN SUM(B.Amount)\n" + 
			      	            	  		"          ELSE 0\n" + 
			      	            	  		"        END AS cr_amount\n" + 
			      	            	  		"      FROM FAS_ADJUST_MEMO_MST A,\n" + 
			      	            	  		"        FAS_ADJUST_MEMO_TRN B\n" + 
			      	            	  		"      WHERE A.Accounting_Unit_Id    =b.Accounting_Unit_Id\n" + 
			      	            	  		"      AND A.Accounting_For_Office_Id=B.Accounting_For_Office_Id\n" + 
			      	            	  		"      AND A.Cashbook_Year           =B.Cashbook_Year\n" + 
			      	            	  		"      AND A.Cashbook_Month          =B.Cashbook_Month\n" + 
			      	            	  		"      AND A.VOUCHER_NO              =B.VOUCHER_NO\n" + 
			      	            	  		"      AND A.MEMO_STATUS             ='L'\n" + 
			      	            	  		"      and B.FOR_ACCOUNTING_UNIT_ID  =" +AccountUnitCode +
//			      	            	  		"      AND B.CASHBOOK_YEAR           =" +txtCB_Year+ 
//			      	            	  		"      AND B.CASHBOOK_MONTH          =" +txtCB_Month+  
			      	            	  		"      AND EXTRACT(YEAR FROM B.ACCEPT_VOUCHER_DATE) = " +CashBookYear+
			      	            	  		"      AND EXTRACT(MONTH FROM B.ACCEPT_VOUCHER_DATE) = " +CashBookMonth+  
			      	            	  		"      AND B.ACCEPTANCE_STATUS       ='Y'\n" + 
			      	            	  		"      AND B.ACCOUNT_HEAD_CODE      IN (610101,610102)\n" + 
			      	            	  		"      GROUP BY B.FOR_ACCOUNTING_UNIT_ID,\n" + 
			      	            	  		"      TO_CHAR( B.ACCEPT_VOUCHER_DATE, 'mm' ),\n" + 
			      	            	  		"      B.ACCOUNT_HEAD_CODE,\n" + 
			      	            	  		"      B.CR_DR_TYPE " + 
			      	            	  		"      ORDER BY B.FOR_ACCOUNTING_UNIT_ID,\n" + 
			      	            	  		"       TO_CHAR( B.ACCEPT_VOUCHER_DATE, 'mm' ),\n" + 
			      	            	  		" \n"+
			      	            	  		"        b.Account_Head_Code\n" + 
			      	            	  		"      )as sm\n" + 
			      	            	  		"    GROUP BY FOR_ACCOUNTING_UNIT_ID,\n" + 
			      	            	  		"      Cashbook_Month,\n" + 
			      	            	  		"      Account_Head_Code\n" + 
			      	            	  		"    ORDER BY FOR_ACCOUNTING_UNIT_ID,\n" + 
			      	            	  		"      Cashbook_Month,\n" + 
			      	            	  		"      Account_Head_Code\n" + 
			      	            	  		"    )Bbb\n" + 
			      	            	  		"  ON aaa.Accounting_Unit_Id=bbb.FOR_ACCOUNTING_UNIT_ID\n" + 
			      	            	  		"  AND Aaa.Cashbook_Month   =Bbb.Cashbook_Month::numeric\n" + 
			      	            	  		"  AND AAA.ACCOUNT_HEAD_CODE=BBB.ACCOUNT_HEAD_CODE::numeric\n" + 
			      	            	  		"  )as sm where (Trn_Net-AdjMemo_net)!=0";
			                    	
			                    	
			                    	
			      	              }
			                    	
			                         		
			                       
			                   	 System.out.println("que:::"+que);
			                                    ps = connection.prepareStatement(que);
			                                    result_adj = ps.executeQuery();
			                                    while(result_adj.next())
			                                    {
			                                    diff_error_Adj++;	
			                                    }
			                                    System.out
														.println("diff_error "+diff_error_Adj);
			                    }
			                    catch(Exception et)
			                    {
			                    	System.out.println("excep in tda_monthend::::"+et);
			                    }
			                    
			                    
    				}
                    
                    
                    
                    
                   int diff_errorCheque=0;
                    
                    // for Cheque Memo and Payments are equal 
                    
                    try{

                 	   String que="SELECT Yy.*, " +
                       		 "  xx.voucher_no, " +
                       		 "  xx.payment_date, " +
                       		 "  Xx.Pay_Amt, " +
                       		 "  xx.pay_amt-yy.che_amt AS diff " +
                       		 "FROM " +
                       		 "  (SELECT SUM(t.amount) AS pay_amt, " +
                       		 "    T.Cheque_Dd_No, " +
                       		"  TO_CHAR(T.cheque_dd_date,'dd/mm/yy') AS cheque_dd_date, " +
                       		 "    m.Accounting_Unit_Id, " +
                       		 "    M.Cashbook_Year, " +
                       		 "    m.cashbook_month, " +
                       		 "    m.voucher_no, " +
                       		 "    to_char(m.payment_date,'dd/mm/yy') as payment_date  " +
                       		 "  FROM Fas_Payment_Master M " +
                       		 "  INNER JOIN Fas_Payment_Transaction T " +
                       		 "  ON m.accounting_for_office_id        =t.accounting_for_office_id " +
                       		 "  AND M.Accounting_Unit_Id             =T.Accounting_Unit_Id " +
                       		 "  AND M.Cashbook_Month                 =T.Cashbook_Month " +
                       		 "  AND M.Cashbook_Year                  =T.Cashbook_Year " +
                       		 "  AND m.voucher_no                     =T.voucher_no " +
                       		 "  AND (M.Voucher_No , M.Payment_Date) IN " +
                       		 "    (SELECT DISTINCT T.Pvr_No , " +
                       		 "      T.Pvr_Date " +
                       		 //"      --, " +
                       		 //"      -- t.payment_unit, " +
                       		 //"      --   t.payment_office " +
                       		 "    FROM Fas_Memo_Of_Payment_Mst M " +
                       		 "    INNER JOIN Fas_Memo_Of_Payment_Trn T " +
                       		 "    ON m.accounting_for_office_id =t.accounting_for_office_id " +
                       		 "    AND M.Accounting_Unit_Id      =T.Accounting_Unit_Id " +
                       		 "    AND M.Cashbook_Month          =T.Cashbook_Month " +
                       		 "    AND M.Cashbook_Year           =T.Cashbook_Year " +
                       		 "    AND M.Bill_No                 =T.Bill_No " +
                       		 "    AND t.payment_unit            =? " +
                       	//	 "    AND t.payment_office      =? " +
                       		 "    AND M.Cashbook_Year           =? " +
                       		 "    AND M.Cashbook_Month          =? " +
                       		 "    AND M.Status                  ='L' " +
                       		 "    ) " +
                       		 "  AND M.Payment_Status     ='L' " +
                       		 "  AND M.Accounting_Unit_Id =? " +
                       		 "  GROUP BY T.Cheque_Dd_No, " +
                       		 "    t.cheque_dd_date, " +
                       		 "    m.Accounting_Unit_Id, " +
                       		 "    M.Cashbook_Year, " +
                       		 "    m.cashbook_month, " +
                       		 "    m.voucher_no, " +
                       		 "    m.payment_date " +
                       		 "  ) Xx " +
                       		 " INNER JOIN " +
                       		 "  (SELECT SUM(cheque_amount) AS che_amt, " +
                       		 "    cheque_no, " +
                       		 "    to_char(cheque_date,'dd/mm/yy') as cheque_date , " +
                       		 "    Accounting_Unit_Id, " +
                       		 "    Cashbook_Year, " +
                       		 "    Cashbook_Month, " +
                       		 "    CHEQUE_MEMO_NO, " +
                       		 "    to_char(CHEQUE_MEMO_DATE,'dd/mm/yy') as CHEQUE_MEMO_DATE " +
                       		 "  FROM Fas_Cheque_Memo_Mst " +
                       		 "  WHERE Accounting_Unit_Id=? " +
                       	//	 "  and  ACCOUNTING_FOR_OFFICE_ID=? " +
                       		 "  AND Cashbook_Year       =? " +
                       		 "  AND Cashbook_Month      =? " +
                       		 "  AND Status              ='L' " +
                       		 "  GROUP BY cheque_no, " +
                       		 "    cheque_date, " +
                       		 "    Accounting_Unit_Id, " +
                       		 "    Cashbook_Year, " +
                       		 "    Cashbook_Month, " +
                       		 "    CHEQUE_MEMO_NO, " +
                       		 "    CHEQUE_MEMO_DATE " +
                       		 "  )Yy " +
                       		 " ON xx.Accounting_Unit_Id=yy.Accounting_Unit_Id " +
                       		 " AND Xx.Cheque_Dd_No     =Yy.Cheque_No " +
                       		 " AND Xx.Cheque_Dd_Date   =Yy.Cheque_Date " +
                       		 " and  xx.pay_amt-yy.che_amt <> 0" + 
                       		 " ORDER BY xx.Cheque_Dd_No";
		                       
		                //   	 System.out.println("que:::"+que);
		                                 PreparedStatement   ps_memo = connection.prepareStatement(que);
		                              
		                                 ps_memo.setInt(1,AccountUnitCode);
		                              
		                                 ps_memo.setInt(2,CashBookYear);
		                                 ps_memo.setInt(3,CashBookMonth);
		                                 ps_memo.setInt(4,AccountUnitCode);
		                                 ps_memo.setInt(5,AccountUnitCode);
		                               
		                                 ps_memo.setInt(6,CashBookYear);
		                                 ps_memo.setInt(7,CashBookMonth);
		                                  ResultSet  result_memo = ps_memo.executeQuery();
		                                    while(result_memo.next())
		                                    {
		                                    diff_errorCheque++;	
		                                    }
		                    }
		                    catch(Exception et)
		                    {
		                    	System.out.println("excep in tda_monthend::::"+et);
		                    }
		                  
                    
                	
                    
                	int rsflag_memo=1;
                	
                		if((CashBookYear==2015 && CashBookMonth> 10) || CashBookYear > 2015){
if(AccountUnitCode != 3 || AccountUnitCode != 5 || AccountUnitCode != 6 || AccountUnitCode != 999){
             		
	rsflag_memo=0;
	                    try{
	                    	String td="Select Verify_Flag From FAS_CHEQUE_MEMO_MONTHEND WHERE ACCOUNTING_UNIT_ID    =? AND " +
	                    			" Cashbook_Year           =? " +
	                    			" AND CASHBOOK_MONTH::numeric=?";
	                    	PreparedStatement ps222=connection.prepareStatement(td);
	                    	ps222.setInt(1,AccountUnitCode);
	                    	ps222.setInt(2,CashBookYear);
	                    	ps222.setInt(3,CashBookMonth);
	                    	ResultSet rs222=ps222.executeQuery();
	                    	while(rs222.next())
	                    	{
	                    		rsflag_memo++;
	                    	}
	                    	
	                    }
	                    catch(Exception ee)
	                    {
	                    	System.out.println("excep::::"+ee);
	                    }
                    
		                  
}
                		}
                	
                    
                    
                    
                    
                    if(AccountUnitCode!=5)
                    {
                    //brs update check
                    	brs_update_flag=0;
                   try{
                    	String td="Select Tb_Year,Tb_Month From Brs_Status_Update Where " +
                    			" Accounting_Unit_Id    =?  AND Tb_Year                 =? " +
                    			" AND TB_MONTH::numeric                =? ";
                    	ps22=connection.prepareStatement(td);
                    	ps22.setInt(1,AccountUnitCode);
                    	ps22.setInt(2,CashBookYear);
                    	ps22.setInt(3,CashBookMonth);//be4
                    	rs22=ps22.executeQuery();
                    	while(rs22.next())
                    	{
                    		brs_update_flag++;
                    	}
                    	
                    }
                    catch(Exception ee)
                    {
                    	System.out.println("excep:::in brs_update_flag:::::"+ee);
                    }
                    
                    
                   
                    
                    }
            //tpa check
            
            try
            {
         
           String sql="SELECT COUNT(*)as tpa_counting  FROM FAS_TPA_MASTER WHERE TRF_ACCOUNTING_UNIT_ID=? AND " +
           		" CASHBOOK_YEAR       =? AND Cashbook_Month      =? AND STATUS              ='L' " +
           		" And (Acceptance_Status      Is Null Or Acceptance_Status='N')";
         System.out.println("sql:::"+sql);
           ps1=connection.prepareStatement(sql);
           ps1.setInt(1,AccountUnitCode);
           ps1.setInt(2,CashBookYear);
           ps1.setInt(3,CashBookMonth);
         
           ResultSet rs=ps1.executeQuery();
           while(rs.next())
           {
           
               rec=rs.getInt("tpa_counting");
               if(rec==0)
               {
            	   tpa_count=0;
               }
               else
               {
               tpa_count++;
               }
           }
          
           }
           catch(Exception e)
           {
           System.out.println(e);
           }
           
           try
           {
        	   String q1="SELECT t.ACCOUNTING_UNIT_ID as unitid,(select u.accounting_unit_name from " +
        	   		" fas_mst_acct_units u where u.accounting_unit_id=t.ACCOUNTING_UNIT_ID)as unitname," +
        	   		" extract(MONTH FROM ORGIN_VOUCHER_DATE)AS month1," +
        	   		" extract(YEAR FROM ORGIN_VOUCHER_DATE) AS year1 " +
        	   		" FROM FAS_TPA_MASTER t WHERE TRF_ACCOUNTING_UNIT_ID="+AccountUnitCode+" AND STATUS ='L' " +
        	   		" AND ACCEPTANCE_STATUS       ='Y' and tpa_type in ('TPAOD','TPAOC')";
        	  System.out.println("q1:::"+q1);
        	   pret=connection.prepareStatement(q1);
        	   rest=pret.executeQuery();
        	   if(rest.next())
        	   {
        		   closedunit=rest.getInt("unitid");
        		   closedunitname=rest.getString("unitname");
        		   closedmn=rest.getInt("month1");
        		   closedyr=rest.getInt("year1");
        		   String q2=null;
        		   if((CashBookYear == closedyr) && (CashBookMonth == closedmn))   
       				{  
        		   
        		    q2="SELECT count(*)as cnt FROM fas_trial_balance_status s" +
        		   		" WHERE s.accounting_unit_id="+closedunit+" AND " +
        		   		" s.cashbook_year="+closedyr+" and s.cashbook_month="+closedmn;
       				
        		   pret1=connection.prepareStatement(q2);
        		   rest1=pret1.executeQuery();
        		   if(rest1.next())
        		   {
        			   cl_unit_yes=rest1.getInt("cnt");
        		   }
        		   else
        		   {
        			   cl_unit_yes=0; 
        		   }
       				}
        		   else 
        		   {
        			   cl_unit_yes=1;  
        		   }
        	   }
        	   else
        	   {
        		   cl_unit_yes=1;
        	   }
        	   
        	   
           }
           catch(Exception etp)
           {
        	   System.out.println("err in tpa closed unit::"+etp);
           }
           //brs_bank balance update
           if(CashBookMonth==1)
           {
           	pvyear=(CashBookYear-1);
           	pvmonth=12;
           }
           else
           {
           		pvyear=CashBookYear;
           		pvmonth=(CashBookMonth-1);	
           }
       /*    pvyear=CashBookYear;
       	pvmonth=CashBookMonth;	*/
       
           try{
           	/*String bank_bal = "SELECT a.brs_update,  "+
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
				  " WHERE ACCOUNTING_UNIT_ID=  "+AccountUnitCode+
				  " AND PS_YEAR             =  "+pvyear+
				  " AND PS_MONTH            = "+pvmonth+
				  " GROUP BY ACCOUNTING_UNIT_ID "+
				  " )a "+
				  " INNER JOIN "+
				  " (SELECT COUNT(*)AS bank_bal_count, "+
				  " accounting_unit_id "+
				  " FROM FAS_MST_BANK_BALANCE "+
				  " WHERE ACCOUNTING_UNIT_ID= "+AccountUnitCode+
				  "  AND STATUS              ='Y' "+
				  "  GROUP BY ACCOUNTING_UNIT_ID "+
				  "  )b "+
				  " ON a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID";*/
        	   // joan change on 23 Sep 2014
        	  /* String bank_bal = "SELECT a.brs_update,  "+
       				"  b.bank_bal_count, "+
       				  " CASE "+
       				  "    WHEN brs_update=(bank_bal_count *"+pvmonth+") "+
       				  " THEN 'Complete' "+
       				//  " WHEN brs_update<(bank_bal_count *"+pvmonth+") "+
       				  " else "+
       				  "  'InComplete' "+
       				  " END AS count_tally "+
       				  " FROM "+
       				  " (SELECT COUNT(*)AS brs_update, "+
       				  " ACCOUNTING_UNIT_ID "+
       				  " FROM brs_bank_balance_update "+
       				  " WHERE ACCOUNTING_UNIT_ID=  "+AccountUnitCode+
       				  " AND PS_YEAR             =  "+pvyear+
       				  " AND PS_MONTH            <= "+pvmonth+
       				  " GROUP BY ACCOUNTING_UNIT_ID "+
       				  " )a "+
       				  " INNER JOIN "+
       				  " (SELECT COUNT(*)AS bank_bal_count, "+
       				  " accounting_unit_id "+
       				  " FROM FAS_MST_BANK_BALANCE "+
       				  " WHERE ACCOUNTING_UNIT_ID= "+AccountUnitCode+
       				  "  AND STATUS              ='Y' "+
       				  "  GROUP BY ACCOUNTING_UNIT_ID "+
       				  "  )b "+
       				  " ON a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID";*/
        	   //joan changed on 12 Dec 2014
        	   String bank_bal="";
        	   String fin_year="";
        
        	   int month_cnt=0,month_cnt1=0;
     		   
               if(CashBookMonth==1){month_cnt=10;
               month_cnt1=3;}
               else if(CashBookMonth==2){month_cnt=11;
               month_cnt1=2;}
               else if(CashBookMonth==3){month_cnt=12;
               month_cnt1=1;}
               else if(CashBookMonth==4){month_cnt=1;
               month_cnt1=12;}
               else if(CashBookMonth==5){month_cnt=2;
               month_cnt1=11;}
               else if(CashBookMonth==6){month_cnt=3;
               month_cnt1=10;}
               else if(CashBookMonth==7){month_cnt=4;
               month_cnt1=9;}
               else if(CashBookMonth==8){month_cnt=5;
               month_cnt1=8;}
               else if(CashBookMonth==9){month_cnt=6;
               month_cnt1=7;}
               else if(CashBookMonth==10){month_cnt=7;
               month_cnt1=6;}
               else if(CashBookMonth==11){month_cnt=8;
               month_cnt1=5;}
               else if(CashBookMonth==12){month_cnt=9;
               month_cnt1=4;}
        	   /*if (CashBookYear>=2014){
        		   System.out.println("year >=2014 BRS Update***");
        			 bank_bal="SELECT COALESCE(SUM(count_tally),0) AS count_tally " +
                			"FROM " +
                			"  (SELECT " +
                			"    CASE " +
                			"      WHEN ( FIN.AC_OPENING_DATE < TO_DATE('01/04/"+CashBookYear+"','dd-mm-yyyy') " +
                			"      OR FIN.AC_OPENING_DATE    IS NULL ) " +
                			"      AND BRS_UPDATE             = " +month_cnt +
                			"      THEN 0 " +
                			"      WHEN FIN.AC_OPENING_DATE >= TO_DATE('01/04/"+CashBookYear+"','dd-mm-yyyy') " +
                			"      AND BRS_UPDATE            = " +
                			"        (SELECT COUNT(*) " +
                			"        FROM " +
                			"          ( select t.n from (SELECT rownum n  CONNECT BY level <=  " +CashBookMonth+" )t where t.n >=4"+
                			"          ) " +
                			"        WHERE n>=to_number(TO_CHAR(AC_OPENING_DATE,'mm')) " +
                			"        ) " +
                			"      THEN 0 " +
                			"      WHEN ( FIN.AC_OPENING_DATE < TO_DATE('01/04/"+CashBookYear+"','dd-mm-yyyy') " +
                			"      OR FIN.AC_OPENING_DATE    IS NULL ) " +
                			"      AND BRS_UPDATE            <>  " +month_cnt+
                			"      THEN 1 " +
                			"      WHEN FIN.AC_OPENING_DATE >= TO_DATE('01/04/"+CashBookYear+"','dd-mm-yyyy') " +
                			"      AND BRS_UPDATE           <> " +
                			"        (SELECT COUNT(*) " +
                			"        FROM " +
                			"          (select t.n from (SELECT rownum n  CONNECT BY level <=  " +CashBookMonth+" )t where t.n >=4"+
                			"          ) " +
                			"        WHERE n>=to_number(TO_CHAR(AC_OPENING_DATE,'mm')) " +
                			"        ) " +
                			"      THEN 1 " +
                			"    END AS count_tally, " +
                			"    fin.bank_ac_no, " +
                			"    fin.ACCOUNTING_UNIT_ID, " +
                			"    fin.brs_update " +
                			"  FROM " +
                			"    (SELECT COUNT(*)AS brs_update, " +
                			"      u.bank_ac_no, " +
                			"      AC_OPENING_DATE, " +
                			"      u.ACCOUNTING_UNIT_ID " +
                			"    FROM brs_bank_balance_update u, " +
                			"      FAS_MST_BANK_BALANCE b " +
                			"    WHERE u.accounting_unit_id=b.accounting_unit_id " +
                			"    AND u.bank_ac_no          =b.bank_ac_no " +
                			"    AND b.status              ='Y' " +
                			"    AND u.ACCOUNTING_UNIT_ID  =  " +AccountUnitCode+
                			"    AND Cb_YEAR               =  " +CashBookYear+
                			"    AND CB_MONTH             <=  " +CashBookMonth+"  and CB_MONTH             >=  4 "+
                			"    AND ( AC_OPENING_DATE     < to_date('01/04/"+CashBookYear+"','dd-mm-yyyy') " +
                			"    OR AC_OPENING_DATE       IS NULL ) " +
                			"    GROUP BY U.BANK_AC_NO, " +
                			"      AC_OPENING_DATE, " +
                			"      U.ACCOUNTING_UNIT_ID " +
                			"    UNION " +
                			"    SELECT COUNT(brs_update) AS brs_update, " +
                			"      a.bank_ac_no, " +
                			"      a.AC_OPENING_DATE, " +
                			"      a.ACCOUNTING_UNIT_ID " +
                			"    FROM " +
                			"      (SELECT COUNT(*) AS brs_update, " +
                			"        u.bank_ac_no, " +
                			"        AC_OPENING_DATE, " +
                			"        Cb_MONTH, " +
                			"        u.ACCOUNTING_UNIT_ID " +
                			"      FROM brs_bank_balance_update u, " +
                			"        FAS_MST_BANK_BALANCE b " +
                			"      WHERE u.accounting_unit_id=b.accounting_unit_id " +
                			"      AND u.bank_ac_no          =b.bank_ac_no " +
                			"      AND B.STATUS              ='Y' " +
                			"      AND CB_YEAR               =  " +CashBookYear+
                			"      AND CB_MONTH             IN " +
                			"        (SELECT                 * " +
                			"        FROM " +
                			"          ( select t.n from (SELECT rownum n  CONNECT BY level <=  " +CashBookMonth+" )t where t.n >=4"+
                			"          ) " +
                			"        WHERE n>=to_number(TO_CHAR(AC_OPENING_DATE,'mm')) " +
                			"        ) " +
                			"      AND AC_OPENING_DATE    >= TO_DATE('01/04/"+CashBookYear+"','dd-mm-yyyy') " +
                			"      AND b.accounting_unit_id= " +AccountUnitCode+
                			"      GROUP BY U.BANK_AC_NO, " +
                			"        AC_OPENING_DATE, " +
                			"        CB_MONTH, " +
                			"        U.ACCOUNTING_UNIT_ID " +
                			"      )A " +
                			"    GROUP BY A.BANK_AC_NO, " +
                			"      A.AC_OPENING_DATE, " +
                			"      A.ACCOUNTING_UNIT_ID " +
                			"    )fin " +
                			"  )s ";
        	   }else{
        		 bank_bal="SELECT COALESCE(SUM(count_tally),0) AS count_tally " +
            			"FROM " +
            			"  (SELECT " +
            			"    CASE " +
            			"      WHEN ( FIN.AC_OPENING_DATE < TO_DATE('01/01/"+CashBookYear+"','dd-mm-yyyy') " +
            			"      OR FIN.AC_OPENING_DATE    IS NULL ) " +
            			"      AND BRS_UPDATE             = " +CashBookMonth+
            			"      THEN 0 " +
            			"      WHEN FIN.AC_OPENING_DATE >= TO_DATE('01/01/"+CashBookYear+"','dd-mm-yyyy') " +
            			"      AND BRS_UPDATE            = " +
            			"        (SELECT COUNT(*) " +
            			"        FROM " +
            			"          ( SELECT rownum n  CONNECT BY level <=  " +CashBookMonth+
            			"          ) " +
            			"        WHERE n>=to_number(TO_CHAR(AC_OPENING_DATE,'mm')) " +
            			"        ) " +
            			"      THEN 0 " +
            			"      WHEN ( FIN.AC_OPENING_DATE < TO_DATE('01/01/"+CashBookYear+"','dd-mm-yyyy') " +
            			"      OR FIN.AC_OPENING_DATE    IS NULL ) " +
            			"      AND BRS_UPDATE            <>  " +CashBookMonth+
            			"      THEN 1 " +
            			"      WHEN FIN.AC_OPENING_DATE >= TO_DATE('01/01/"+CashBookYear+"','dd-mm-yyyy') " +
            			"      AND BRS_UPDATE           <> " +
            			"        (SELECT COUNT(*) " +
            			"        FROM " +
            			"          ( SELECT rownum n  CONNECT BY level <=  " +CashBookMonth+
            			"          ) " +
            			"        WHERE n>=to_number(TO_CHAR(AC_OPENING_DATE,'mm')) " +
            			"        ) " +
            			"      THEN 1 " +
            			"    END AS count_tally, " +
            			"    fin.bank_ac_no, " +
            			"    fin.ACCOUNTING_UNIT_ID, " +
            			"    fin.brs_update " +
            			"  FROM " +
            			"    (SELECT COUNT(*)AS brs_update, " +
            			"      u.bank_ac_no, " +
            			"      AC_OPENING_DATE, " +
            			"      u.ACCOUNTING_UNIT_ID " +
            			"    FROM brs_bank_balance_update u, " +
            			"      FAS_MST_BANK_BALANCE b " +
            			"    WHERE u.accounting_unit_id=b.accounting_unit_id " +
            			"    AND u.bank_ac_no          =b.bank_ac_no " +
            			"    AND b.status              ='Y' " +
            			"    AND u.ACCOUNTING_UNIT_ID  =  " +AccountUnitCode+
            			"    AND Cb_YEAR               =  " +CashBookYear+
            			"    AND CB_MONTH             <=  " +CashBookMonth+
            			"    AND ( AC_OPENING_DATE     < to_date('01/01/"+CashBookYear+"','dd-mm-yyyy') " +
            			"    OR AC_OPENING_DATE       IS NULL ) " +
            			"    GROUP BY U.BANK_AC_NO, " +
            			"      AC_OPENING_DATE, " +
            			"      U.ACCOUNTING_UNIT_ID " +
            			"    UNION " +
            			"    SELECT COUNT(brs_update) AS brs_update, " +
            			"      a.bank_ac_no, " +
            			"      a.AC_OPENING_DATE, " +
            			"      a.ACCOUNTING_UNIT_ID " +
            			"    FROM " +
            			"      (SELECT COUNT(*) AS brs_update, " +
            			"        u.bank_ac_no, " +
            			"        AC_OPENING_DATE, " +
            			"        Cb_MONTH, " +
            			"        u.ACCOUNTING_UNIT_ID " +
            			"      FROM brs_bank_balance_update u, " +
            			"        FAS_MST_BANK_BALANCE b " +
            			"      WHERE u.accounting_unit_id=b.accounting_unit_id " +
            			"      AND u.bank_ac_no          =b.bank_ac_no " +
            			"      AND B.STATUS              ='Y' " +
            			"      AND CB_YEAR               =  " +CashBookYear+
            			"      AND CB_MONTH             IN " +
            			"        (SELECT                 * " +
            			"        FROM " +
            			"          ( SELECT rownum n  CONNECT BY level <=  " +CashBookMonth+
            			"          ) " +
            			"        WHERE n>=to_number(TO_CHAR(AC_OPENING_DATE,'mm')) " +
            			"        ) " +
            			"      AND AC_OPENING_DATE    >= TO_DATE('01/01/"+CashBookYear+"','dd-mm-yyyy') " +
            			"      AND b.accounting_unit_id= " +AccountUnitCode+
            			"      GROUP BY U.BANK_AC_NO, " +
            			"        AC_OPENING_DATE, " +
            			"        CB_MONTH, " +
            			"        U.ACCOUNTING_UNIT_ID " +
            			"      )A " +
            			"    GROUP BY A.BANK_AC_NO, " +
            			"      A.AC_OPENING_DATE, " +
            			"      A.ACCOUNTING_UNIT_ID " +
            			"    )fin " +
            			"  )s ";
        	     	System.out.println("brsssssssssssssssssssss:::"+bank_bal);
        	   }
           	System.out.println("brsssssssssssssssssssss:::"+bank_bal);
           	ps223=connection.prepareStatement(bank_bal);
           	
           	rs223=ps223.executeQuery();
           	while(rs223.next())
           	{
           		System.out.println("pvyear"+CashBookYear+"  res : "+rs223.getString("count_tally")+" "+"AccountUnitCode : "+AccountUnitCode);
           	if(count_tally.equals("null"))
           	{
           		counted_bank_bal=0;
           	}
           	if(AccountUnitCode==5)
           		{
           			counted_bank_bal=0;
           		}
           		else {
           			if(pvyear>2013){
           			
           		
           		count_tally=rs223.getString("count_tally");
           		if(count_tally.equals("1"))
           		{
           			counted_bank_bal=rs223.getInt("brs_update");
           		}
           		else if(count_tally.equals("0"))
           		{
           			counted_bank_bal=0;
           		}
           	
           		}else{
           			counted_bank_bal=0;
           		}
           		}
           	}*/
           	// Joan added on 11 Nov 2015
               
              /* bank_bal="SELECT COUNT(*) as count_value " +
            		   " FROM " +
            		   "  (SELECT Bank_Ac_No, " +
            		   "    Ac_Opening_Date,Accounting_Unit_Id, " +
            		   "    CASE " +
            		   "      WHEN Ac_Opening_Date IS NULL " +
            		   "      THEN " +
            		   "        (SELECT COUNT(*) " +
            		   "        FROM brs_bank_balance_update u " +
            		   "        WHERE U.Accounting_Unit_Id =Bsal.Accounting_Unit_Id " +
            		   "        AND u.bank_ac_no           =bsal.bank_ac_no " +
            		   "        AND To_Date((Cb_Month " +
            		   "          ||'-' " +
            		   "          || Cb_Year),'mm-yyyy') BETWEEN To_Date(1 " +
            		   "          ||'-' " +
            		   "          ||"+CashBookYear+",'mm-yyyy') " +
            		   "        AND to_date( " +CashBookMonth+
            		   "          ||'-' " +
            		   "          ||"+CashBookYear+",'mm-yyyy') " +
            		   "        ) " +
            		   "      WHEN Ac_Opening_Date < '01-jan-"+CashBookYear+"' " +
            		   "      THEN " +
            		   "        (SELECT COUNT(*) " +
            		   "        FROM brs_bank_balance_update u " +
            		   "        WHERE u.accounting_unit_id =bsal.accounting_unit_id " +
            		   "        AND u.bank_ac_no           =bsal.bank_ac_no " +
            		   "        AND To_Date((Cb_Month " +
            		   "          ||'-' " +
            		   "          || Cb_Year),'mm-yyyy') BETWEEN To_Date(1 " +
            		   "          ||'-' " +
            		   "          ||"+CashBookYear+",'mm-yyyy') " +
            		   "        AND to_date( " +CashBookMonth+
            		   "          ||'-' " +
            		   "          ||"+CashBookYear+",'mm-yyyy') " +
            		   "        ) " +
            		   "      WHEN Ac_Opening_Date >= '01-jan-"+CashBookYear+"' " +
            		   "      THEN " +
            		   "        (SELECT COUNT(*) " +
            		   "        FROM Brs_Bank_Balance_Update u " +
            		   "        WHERE u.accounting_unit_id =bsal.accounting_unit_id " +
            		   "        AND u.bank_ac_no           =bsal.bank_ac_no " +
            		   "        AND To_Date((Cb_Month " +
            		   "          ||'-' " +
            		   "          || Cb_Year),'mm-yyyy') BETWEEN To_Date(extract(MONTH FROM Ac_Opening_Date) " +
            		   "          ||'-' " +
            		   "          ||extract(YEAR FROM Ac_Opening_Date),'mm-yyyy') " +
            		   "        AND to_date( " +CashBookMonth+
            		   "          ||'-' " +
            		   "          ||"+CashBookYear+",'mm-yyyy') " +
            		   "        ) " +
            		   "    END AS Count_No " +
            		   "  FROM fas_mst_bank_balance bsal " +
            		   "  WHERE Accounting_Unit_Id= " +AccountUnitCode+
            		   "  AND Status              ='Y' " +
            		   "  )Xx " +
            		   " INNER JOIN " +
            		   "  (SELECT bank_ac_no, " +
            		   "    Ac_Opening_Date,Accounting_Unit_Id, " +
            		   "    CASE " +
            		   "      WHEN Ac_Opening_Date IS NULL " +
            		   "      THEN " +
            		   "        (SELECT COUNT(*) FROM " +
            		   "          (SELECT rownum n  CONNECT BY level <=  " +CashBookMonth+
            		   "          )t " +
            		   "        ) " +
            		   "      WHEN Ac_Opening_Date >= '01-jan-"+CashBookYear+"' " +
            		   "      THEN " +
            		   "        (SELECT COUNT(*) " +
            		   "        FROM " +
            		   "          (SELECT rownum n  CONNECT BY Level <=  " +CashBookMonth+
            		   "          )T " +
            		   "        WHERE n >= to_number(TO_CHAR(Ac_Opening_Date,'mm')) " +
            		   "        ) " +
            		   "      WHEN Ac_Opening_Date < '01-jan-"+CashBookYear+"' " +
            		   "      THEN " +
            		   "        (SELECT COUNT(*) FROM " +
            		   "          (SELECT rownum n  CONNECT BY Level <=  " +CashBookMonth+
            		   "          )T " +
            		   "        ) " +
            		   "    END AS rt " +
            		   "  FROM fas_mst_bank_balance bsal " +
            		   "  WHERE Accounting_Unit_Id= " +AccountUnitCode+
            		   "  AND Status              ='Y' " +
            		   "  ) Yy " +
            		   " ON Xx.Bank_Ac_No=Yy.Bank_Ac_No and xx.Accounting_Unit_Id=yy.Accounting_Unit_Id  " +
            		   " WHERE count_no <> rt";
               */
               String month = "";
               if(CashBookMonth==1){
            	   month="Jan";
               }
               else if(CashBookMonth==2)
               {
            	   month="Feb";
               }
               else if(CashBookMonth==3)
               {
            	   month="Mar";
               }
               else if(CashBookMonth==4)
               {
            	   month="Apr";
               }
               else if(CashBookMonth==5)
               {
            	   month="May";
               }
               else if(CashBookMonth==6)
               {
            	   month="Jun";
               }
               else if(CashBookMonth==7){
            	   month="Jul";
               }
               else if(CashBookMonth==8){
            	   month="Aug";
               }
               else if(CashBookMonth==9){
            	   month="Sep";
               }
               else if(CashBookMonth==10){
            	   month="Oct";
               }
               else if(CashBookMonth==11){
            	   month="Nov";
               }
               else if(CashBookMonth==12){
            	   month="Dec";
               } 
               
               Date lastDay = new Date(CashBookYear, CashBookMonth , 0);
               int lastDayWithSlashes=lastDay.getDate();
               System.out.println("lastDayWithSlashes===>"+lastDayWithSlashes);
               System.out.println("AccountUnitCode===>"+AccountUnitCode);
               
//               if (AccountUnitCode==5)
//               {
//            	   counted_bank_bal=0;
//               }
//               else
//               {
               bank_bal="SELECT COUNT(*) AS count_value " +
            		   "FROM " +
            		   "  (SELECT Bank_Ac_No, " +
            		   "    Ac_Opening_Date, " +
            		   "    Accounting_Unit_Id, " +
            		   "    CASE " +
            		   "      WHEN Ac_Opening_Date IS NULL " +
            		   "      THEN " +
            		   "        (SELECT COUNT(*) " +
            		   "        FROM brs_bank_balance_update u " +
            		   "        WHERE U.Accounting_Unit_Id =Bsal.Accounting_Unit_Id " +
            		   "        AND u.bank_ac_no::numeric       =bsal.bank_ac_no " +
            		   "        AND To_Date((PS_MONTH " +
            		   "          ||'-' " +
            		   "          || PS_YEAR),'mm-yyyy') BETWEEN To_Date(4 " +
            		   "          ||'-' " +
            		   "          || " +CashBookYear+",'mm-yyyy') " +
            		   "        AND to_date(  " +CashBookMonth+
            		   "          ||'-' " +
            		   "          || " +CashBookYear+",'mm-yyyy') " +
            		   "        ) " +
            		   "      WHEN Ac_Opening_Date < '01-Apr-"+CashBookYear+"' " +
            		   "      THEN " +
            		   "        (SELECT COUNT(*) " +
            		   "        FROM brs_bank_balance_update u " +
            		   "        WHERE u.accounting_unit_id =bsal.accounting_unit_id " +
            		   "        AND u.bank_ac_no::numeric           =bsal.bank_ac_no " +
            		   "        AND To_Date((PS_MONTH " +
            		   "          ||'-' " +
            		   "          || PS_YEAR),'mm-yyyy') BETWEEN To_Date(4 " +
            		   "          ||'-' " +
            		   "          ||" +CashBookYear+",'mm-yyyy') " +
            		   "        AND to_date(  " +CashBookMonth+
            		   "          ||'-' " +
            		   "          ||" +CashBookYear+",'mm-yyyy') " +
            		   "        ) " +
            		   "      WHEN (Ac_Opening_Date >= '01-Apr-"+CashBookYear+"') and (Ac_Opening_Date <= '"+lastDayWithSlashes+"-"+ month +"-"+CashBookYear+"')" +
            		   "      THEN " +
            		   "        (SELECT COUNT(*) " +
            		   "        FROM Brs_Bank_Balance_Update u " +
            		   "        WHERE u.accounting_unit_id =bsal.accounting_unit_id " +
            		   "        AND u.bank_ac_no::numeric       =bsal.bank_ac_no " +
            		   "        AND To_Date((PS_MONTH " +
            		   "          ||'-' " +
            		   "          || PS_YEAR),'mm-yyyy') BETWEEN To_Date(extract(MONTH FROM Ac_Opening_Date) " +
            		   "          ||'-' " +
            		   "          ||extract(YEAR FROM Ac_Opening_Date),'mm-yyyy') " +
            		   "        AND to_date(  " +CashBookMonth+
            		   "          ||'-' " +
            		   "          ||"+CashBookYear+",'mm-yyyy') " +
            		   "        ) " +
            		   "    END AS Count_No " +
            		   "  FROM Fas_Mst_Bank_Balance Bsal " +
            		   "  WHERE Accounting_Unit_Id= " +AccountUnitCode+
            		   "  AND Status              ='Y' " +
            		   "  )Xx " +
            		   " INNER JOIN " +
            		   "  (SELECT bank_ac_no, " +
            		   "    Ac_Opening_Date, " +
            		   "    Accounting_Unit_Id, " +
            		   "    CASE " +
            		   "      WHEN Ac_Opening_Date IS NULL " +
            		   "      THEN 0" +
//            		   "        (SELECT COUNT(*) " +
//            		   "        FROM " +
//            		   "          (SELECT row_number()  over() N  CONNECT BY Level <=  " +CashBookMonth+
//            		   "          )t " +
//            		   "        WHERE n >3 " +
//            		   "        ) " +
            		   "      WHEN Ac_Opening_Date >= '01-Apr-"+CashBookYear+"' " +
            		   "      THEN 3" +
//            		   "        (SELECT COUNT(*) " +
//            		   "        FROM " +
//            		   "          (SELECT row_number()  over() n  CONNECT BY Level <=  " +CashBookMonth+
//            		   "          )T " +
//            		   "        WHERE N >= to_number(TO_CHAR(Ac_Opening_Date,'mm')) " +
//            		  // "          --and n >3 " +
//            		   "        ) " +
            		   "      WHEN Ac_Opening_Date < '01-Apr-"+CashBookYear+"' " +
            		   "      THEN 0" +
//            		   "        (SELECT COUNT(*) " +
//            		   "        FROM " +
//            		   "          (SELECT row_number()  over() N  CONNECT BY Level <=  " +CashBookMonth+
//            		   "          )T " +
//            		   "        WHERE n >3 " +
//            		   "        ) " +
            		   "    END AS rt " +
            		   "  FROM Fas_Mst_Bank_Balance Bsal " +
            		   "  WHERE Accounting_Unit_Id= " +AccountUnitCode+
            		   "  AND Status              ='Y' " +

            		   
						// commanded on 28-Sep-18
//            		   "(SELECT bank_ac_no," + 
//            		   "    Ac_Opening_Date," + 
//            		   "    Accounting_Unit_Id," + 
//            		   "    CASE" + 
//            		   "      WHEN Ac_Opening_Date IS NULL" + 
//            		   "      THEN" + 
//            		   "        (SELECT RT1 as COUNT" + 
//            		   "        FROM(" + 
//            		   "        SELECT" + 
//            		   "        CASE" + 
//            		   "        WHEN DATE_OF_OPENING IS NULL" + 
//            		   "        THEN" + 
//            		   "        (SELECT COUNT(*)" + 
//            		   "        FROM" + 
//            		   "          (SELECT Rownum N  CONNECT BY Level <= " +CashBookMonth+
//            		   "          )T" + 
//            		   "        WHERE N >3)" + 
//            		   "        WHEN DATE_OF_OPENING IS NOT NULL" + 
//            		   "        THEN" + 
//            		   "        (SELECT COUNT(*)" + 
//            		   "        FROM" + 
//            		   "          (SELECT Rownum N  CONNECT BY Level <= " +CashBookMonth+ 
//            		   "          )T" + 
//            		   "        WHERE N >= TO_NUMBER(TO_CHAR(DATE_OF_OPENING,'mm')))" + 
//            		   "        END AS rt1" + 
//            		   "        FROM FAS_MST_ACCT_UNITS" + 
//            		   "        WHERE ACCOUNTING_UNIT_ID=" +AccountUnitCode+
//            		   "        )" + 
//            		   "        )" + 
//            		   "        WHEN Ac_Opening_Date >= '01-Apr-"+CashBookYear+"' " +
//            		   "      THEN" + 
//            		   "        (SELECT COUNT(*)" + 
//            		   "        FROM" + 
//            		   "          (SELECT rownum n  CONNECT BY Level <= " +CashBookMonth+
//            		   "          )T" + 
//            		   "        WHERE N >= TO_NUMBER(TO_CHAR(AC_OPENING_DATE,'mm'))" + 
//            		   "        )" + 
//            		   "        WHEN Ac_Opening_Date < '01-Apr-"+CashBookYear+"' " +
//            		   "         THEN" + 
//            		   "        (SELECT RT1 as COUNT" + 
//            		   "        FROM(" + 
//            		   "        SELECT" + 
//            		   "        CASE" + 
//            		   "        WHEN DATE_OF_OPENING IS NULL" + 
//            		   "        THEN" + 
//            		   "        (SELECT COUNT(*)" + 
//            		   "        FROM" + 
//            		   "          (SELECT Rownum N  CONNECT BY Level <= " +CashBookMonth+
//            		   "          )T" + 
//            		   "        WHERE N >3)" + 
//            		   "        WHEN DATE_OF_OPENING IS NOT NULL" + 
//            		   "        THEN" + 
//            		   "        (SELECT COUNT(*)" + 
//            		   "        FROM" + 
//            		   "          (SELECT Rownum N  CONNECT BY Level <= " +CashBookMonth+
//            		   "          )T" + 
//            		   "        WHERE N >= TO_NUMBER(TO_CHAR(DATE_OF_OPENING,'mm')))" + 
//            		   "        END AS rt1" + 
//            		   "        FROM FAS_MST_ACCT_UNITS" + 
//            		   "        WHERE ACCOUNTING_UNIT_ID="+AccountUnitCode+
//            		   "        )" + 
//            		   "        )" + 
//            		   "        END AS rt" + 
//            		   "  FROM Fas_Mst_Bank_Balance Bsal" + 
//            		   "  WHERE ACCOUNTING_UNIT_ID=" +AccountUnitCode+
//            		   "  AND Status              ='Y' " +
            		   "  ) Yy " +
            		   " ON Xx.Bank_Ac_No         =Yy.Bank_Ac_No " +
            		   " AND Xx.Accounting_Unit_Id=Yy.Accounting_Unit_Id " +
            		   " WHERE count_no          <> rt";
               
               
               
              	System.out.println("brsssssssssssssssssssss:::"+bank_bal);
               	ps223=connection.prepareStatement(bank_bal);
               	
               	rs223=ps223.executeQuery();
               	while(rs223.next())
               	{
               	
               			counted_bank_bal=rs223.getInt("count_value");
               		
               	
               	}
//               }
               
           }
           catch(Exception ee)
           {
           	System.out.println("excep:::in brs_bank balance:::::"+ee);
           }
        System.out.println("counted_bank_bal >>> "+counted_bank_bal);
        //adjustment_memo
        if(AccountUnitCode==5) {
            System.out.println("comes for banking section:");
        
         try
                         {
                      
                        String sql="SELECT mas.RECEIPT_NO\n" + 
                        " FROM FAS_RECEIPT_MASTER mas,FAS_RECEIPT_TRANSACTION trn\n" + 
                        " WHERE trn.ACCOUNTING_UNIT_ID=mas.ACCOUNTING_UNIT_ID\n" + 
                        " and trn.ACCOUNTING_FOR_OFFICE_ID=mas.ACCOUNTING_FOR_OFFICE_ID\n" + 
                        " and trn.CASHBOOK_YEAR=mas.CASHBOOK_YEAR\n" + 
                        " and trn.CASHBOOK_MONTH=mas.CASHBOOK_MONTH\n" + 
                        " and trn.RECEIPT_NO=mas.RECEIPT_NO\n" + 
                        " and trn.ACCOUNTING_UNIT_ID                      =?\n" + 
                        " AND trn.CASHBOOK_MONTH=? \n" + 
                        " AND trn.CASHBOOK_YEAR                             =?\n" + 
                        " and trn.ACCOUNT_HEAD_CODE=610101\n" + 
                        " and (trn.CB_REF_NO is null or trn.cb_ref_no=0)\n" + 
                        " and (trn.CB_REF_DATE is null)\n" + 
                        " AND mas.RECEIPT_STATUS='L'";
                      
                        ps1=connection.prepareStatement(sql);
                        ps1.setInt(1,AccountUnitCode);
                        ps1.setInt(2,CashBookMonth);
                        ps1.setInt(3,CashBookYear);
                      
                        System.out.println("before execution******************");
                        ResultSet rs=ps1.executeQuery();
                        while(rs.next())
                        {
                        
                            if(a3==0)
                            {
                            adj_memo_no=rs.getString("VOUCHER_NO");
                            a3=1;
                            }
                            else {
                                adj_memo_no=adj_memo_no+","+rs.getString("VOUCHER_NO");
                            }
                            adj_memo_count++;
                        }
                         
                        System.out.println("after execution"+adj_memo_no);
                        
                        }
                        catch(Exception e)
                        {
                        System.out.println(e);
                        } 
        }
        int hotrn_counted = 0;
         if(CashBookMonth==1) 
        {
            System.out.println("january month only activated");
            
           
            try {

                
                  String sql="SELECT COUNT(*)AS ttt,\n" + 
                  "  CASHBOOK_MONTH\n" + 
                  " FROM FAS_FUND_TRF_FROM_HO_TRN\n" + 
                  " WHERE TRANSFER_TO_OFFICE_ID=?\n" + 
                  " AND CASHBOOK_YEAR          ="+CashBookYear+"-1\n" + 
                  " AND CASHBOOK_MONTH         =?\n" + 
                  " AND AUTO_STATUS           IS NULL\n" + 
                  " GROUP BY CASHBOOK_MONTH";
                System.out.println("sql for january month::::"+sql);
                  ps1=connection.prepareStatement(sql);
                  ps1.setInt(1,AccountUnitCode);
                  ps1.setInt(2,CashBookMonth);
                System.out.println("before execution");
                ResultSet rs = ps1.executeQuery();
                System.out.println("after execution");
                while (rs.next()) {
                    hotrn_counted = rs.getInt("ttt");
                }

                System.out.println("hotrn_counted--::::::::::->" + hotrn_counted);

            } catch (Exception e) {
                System.out.println(e);
            }
            
        }
        else if(CashBookMonth>1) 
        {
           // int hotrn_counted_notjanuary = 0;
            try {

                
                  String sql=" SELECT COUNT(*)AS ttt\n" + 
                  " FROM FAS_FUND_TRF_FROM_HO_TRN\n" + 
                  " WHERE TRANSFER_TO_OFFICE_ID=?\n" + 
                  " AND CASHBOOK_YEAR          =?\n" + 
                  " AND CASHBOOK_MONTH         =?\n" + 
                  " AND AUTO_STATUS           IS NULL\n";
                System.out.println("sql for remaining month::::"+sql);
                  ps1=connection.prepareStatement(sql);
                  ps1.setInt(1,AccountUnitCode);
                  ps1.setInt(2,CashBookYear);
                  ps1.setInt(3,CashBookMonth);
                System.out.println("before execution");
                ResultSet rs = ps1.executeQuery();
                System.out.println("after execution");
                while (rs.next()) {
                    hotrn_counted = rs.getInt("ttt");
                }

                System.out.println("hotrn_counted--::::::::::->" + hotrn_counted);

            } catch (Exception e) {
                System.out.println(e);
            }
        }
        //FAS_GL_PBSTATUS checking
        
         int PB_counted_status = 0,less_count=0,Count_test=0,month_max=0,prevmonth=0,generate_pbStatus=0,prevYear=0;
          if(CashBookMonth==1) 
         {
             System.out.println("january month in FAS_GL_PBSTATUS ");
             
             try {

                 
            	 String sql_one="select case when(count1 is null) then 0 else count1 end as count1 "+
            	 		//+ "decode(count1,null,0,count1)as count1,"
            	 		 "mmsl from( "+
					"	Select GL_PB_FREEZE_STATUS as count1, "+
         	   "	  max(CASHBOOK_MONTH)as mmsl "+
         	   "	 From FAS_GL_PBSTATUS "+
         	   "	 Where Cashbook_Year   =? "+
         	   "	 AND ACCOUNTING_UNIT_ID=? GROUP BY GL_PB_FREEZE_STATUS)as sm ";
               
                 System.out.println("sql for jan month::::"+sql_one);
                   ps1=connection.prepareStatement(sql_one);
                   ps1.setInt(1,CashBookYear);
                   ps1.setInt(2,AccountUnitCode);
                
                 ResultSet rs = ps1.executeQuery();
                 while (rs.next()) {
                // less_count++;
                     Count_test = rs.getInt("Count");
                     month_max = rs.getInt("mm");
                     System.out.println("month_max:::::"+month_max);
                 }
                 if(Count_test>0) 
                 {
                  prevmonth=12;
                //  prevYear=CashBookYear-1;
                  if(prevmonth==month_max) {
                      generate_pbStatus=0;
                      System.out.println("no error::::");
                  }
                  else {
                      generate_pbStatus++;
                  }
                     
                 }
                 else if(Count_test==0) 
                 {
                	 //No Data for Any more month in GL
                	 generate_pbStatus++;
                 }
                    rs.close();
                    ps1.close();
                 System.out.println("generate_pbStatus--::::::::::->" + generate_pbStatus);

             } catch (Exception e) {
                 System.out.println(e);
             }
             
         }
         else if(CashBookMonth>1) 
         {
           
             try {

                 
                   String sql_one="select coalesce (count1,'0') as count1,"+
                   		//+ "decode(count1,null,0,count1)as count1,
                		   "mmsl from( "+
						"	Select GL_PB_FREEZE_STATUS as count1, "+
                	   "	  max(CASHBOOK_MONTH)as mmsl "+
                	   "	 From FAS_GL_PBSTATUS "+
                	   "	 Where Cashbook_Year   =? "+
                	   "	 AND ACCOUNTING_UNIT_ID=? GROUP BY GL_PB_FREEZE_STATUS)as sm";
               
                 System.out.println("sql_one for remaining month::::"+sql_one);
                   ps1=connection.prepareStatement(sql_one);
                   ps1.setInt(1,CashBookYear);
                   ps1.setInt(2,AccountUnitCode);
                
                 ResultSet rs = ps1.executeQuery();
                 while (rs.next()) {
                // less_count++;
                     Count_test = rs.getInt("count1");
                     month_max = rs.getInt("mm");
                     System.out.println("month_max:::::"+month_max);
                 }
                 if(Count_test>0) 
                 {
                  prevmonth=CashBookMonth-1;
                  if(prevmonth==month_max) {
                      generate_pbStatus=0;
                      System.out.println("no error::::");
                  }
                  else if(month_max==CashBookMonth)
                  {
                	  generate_pbStatus=0;
                      System.out.println("no error::::");
                  }
                  else {
                	  System.out.println("else:::::error no data ");
                      generate_pbStatus++;
                  }
                     
                 }
                 else  if(Count_test==0) 
                 {
                	 //No Data for Any more month in GL
                	 generate_pbStatus++;
                 }
                    rs.close();
                    ps1.close();
                 System.out.println("generate_pbStatus--::::::::::->" + generate_pbStatus);

             } catch (Exception e) {
                 System.out.println(e);
             }
         }  
        
          //FAS_SL_PBSTATUS checking
          
          int Count_testsl=0,month_maxsl=0,prevmonthsl=0,generate_pbStatus_sl=0;
           if(CashBookMonth==1) 
          {
              System.out.println("january month in FAS_SL_PBSTATUS ");
              
              try {

                  
            	  String sql_one="select coalesce (count1,'0') as count1,"+
            	  		//+ "decode(count1,null,0,count1)as count1,"
            	  		"mmsl from(  "+
										" Select SL_PB_FREEZE_STATUS as count1, "+
					      "   max(CASHBOOK_MONTH)as mmsl "+
					      " 		 From Fas_Sl_Pbstatus "+
					      " 		 Where Cashbook_Year   =? "+
					      " 		 AND ACCOUNTING_UNIT_ID=? GROUP BY SL_PB_FREEZE_STATUS)as sm";
                
                  System.out.println("sql for month jan::::"+sql_one);
                    ps1=connection.prepareStatement(sql_one);
                    ps1.setInt(1,CashBookYear);
                    ps1.setInt(2,AccountUnitCode);
                 
                  ResultSet rs = ps1.executeQuery();
                  while (rs.next()) {
                 // less_count++;
                      Count_testsl = rs.getInt("Count");
                      month_maxsl = rs.getInt("mmsl");
                      System.out.println("month_maxsl:::::"+month_maxsl);
                  }
                  if(Count_testsl>0) 
                  {
                   prevmonthsl=12;
                 //  prevYear=CashBookYear-1;
                   if(prevmonthsl==month_maxsl) {
                       generate_pbStatus_sl=0;
                       System.out.println("no error::::in sl");
                   }
                   else {
                	   generate_pbStatus_sl++;
                   }
                      
                  }
                  else  if(Count_testsl==0) 
                  {
                 	 //No Data for Any more month in SL
                	  generate_pbStatus_sl++;
                  }
                     rs.close();
                     ps1.close();
                  System.out.println("generate_pbStatussl--::::::::::->" + generate_pbStatus_sl);

              } catch (Exception e) {
                  System.out.println(e);
              }
              
          }
          else if(CashBookMonth>1) 
          {
            
              try {

                  
                    String sql_one="select coalesce (count1,'0')as count1, "+
                    		//+ "decode(count1,null,0,count1)as count1,"
                    		 "mmsl from(  "+
								" Select SL_PB_FREEZE_STATUS as count1, "+
                    "   max(CASHBOOK_MONTH)as mmsl "+
                    " 		 From Fas_Sl_Pbstatus "+
                    " 		 Where Cashbook_Year   =? "+
                    " 		 AND ACCOUNTING_UNIT_ID=? GROUP BY SL_PB_FREEZE_STATUS)as sm";
                
                  System.out.println("sql_one for remaining month::::"+sql_one);
                    ps1=connection.prepareStatement(sql_one);
                    ps1.setInt(1,CashBookYear);
                    ps1.setInt(2,AccountUnitCode);
                 
                  ResultSet rs = ps1.executeQuery();
                  while (rs.next()) {
                 // less_count++;
                      Count_testsl = rs.getInt("count1");
                      month_maxsl = rs.getInt("mmsl");
                      System.out.println("month_maxsl:greater::::"+month_maxsl);
                  }
                  if(Count_testsl>0) 
                  {
                	  prevmonthsl=CashBookMonth-1;
                   if(prevmonthsl==month_maxsl) {
                	   generate_pbStatus_sl=0;
                       System.out.println("no error for prev month sl::::");
                   }
                   else if(month_maxsl==CashBookMonth)
                   {
                	   generate_pbStatus_sl=0;
                       System.out.println("no error::::");
                   }
                   else {
                	   generate_pbStatus_sl++;
                   }
                      
                  }
                  else  if(Count_testsl==0) 
                  {
                 	 //No Data for Any more month in SL
                	  generate_pbStatus_sl++;
                  }
                     rs.close();
                     ps1.close();
                  System.out.println("generate_pbStatussl--::::::::::->" + generate_pbStatus_sl);

              } catch (Exception e) {
                  System.out.println(e);
              }
          }
          //FT vs FR check
           int ftvsfr=10;
           if(CashBookMonth==3){
        	   System.out.println("if month is three......");
           try
           {
        
          String sql="SELECT COUNT(*)"+ 
			"	FROM Fas_Ft_Fr_Verify"+
        	  "	WHERE Particulars IS NOT NULL"+
        	  "	AND Accounting_Unit_Id=? "+
        	  "	AND Cashbook_Year     =? "+
        	  "	AND CASHBOOK_MONTH    =? ";
        
           ps1=connection.prepareStatement(sql);
           ps1.setInt(1,AccountUnitCode);
	       ps1.setInt(2,CashBookYear);
	       ps1.setInt(3,CashBookMonth);
        
          ResultSet rs=ps1.executeQuery();
          while(rs.next())
          {
            ftvsfr=rs.getInt(count);
          }
           
          System.out.println("after ftvsfr"+ftvsfr);
          
          }
          catch(Exception e)
          {
          System.out.println(e);
          } 
           
           }
        //acceptance status N
        
         try
                         {
                      
                        String sql="SELECT a.VOUCHER_NO\n" + 
                        " FROM FAS_TDA_TCA_RAISED_MST a\n" + 
                        " WHERE a.TRF_ACCOUNTING_UNIT_ID=?\n" + 
                        " and a.CASHBOOK_YEAR=?\n" + 
                        " and a.CASHBOOK_MONTH=?\n" + 
                        " and a.ACCEPTANCE_STATUS='N'";
                      
                        ps1=connection.prepareStatement(sql);
                        ps1.setInt(1,AccountUnitCode);
                        ps1.setInt(2,CashBookMonth);
                        ps1.setInt(3,CashBookYear);
                      
                        System.out.println("before execution******************");
                        ResultSet rs=ps1.executeQuery();
                        while(rs.next())
                        {
                        
                            if(a3==0)
                            {
                            tda_tca_status_NO=rs.getString("VOUCHER_NO");
                            a3=1;
                            }
                            else {
                                tda_tca_status_NO=tda_tca_status_NO+","+rs.getString("VOUCHER_NO");
                            }
                            tda_tca_counted++;
                        }
                         
                        System.out.println("after execution"+tda_tca_counted);
                        
                        }
                        catch(Exception e)
                        {
                        System.out.println(e);
                        } 
        
        //tda_tca_date
        
     /*   try
                 {
              
                String sql="SELECT a.VOUCHER_NO,a.VOUCHER_DATE,a.ACCEPTING_DATE,a.responding_jvr_date\n" + 
                " FROM FAS_TDA_TCA_RAISED_MST a\n" + 
                " WHERE (a.VOUCHER_DATE>a.ACCEPTING_DATE\n" + 
                " or a.ACCEPTING_DATE>a.responding_jvr_date or a.VOUCHER_DATE>a.responding_jvr_date)\n" + 
                " and a.accounting_unit_id =?               \n" + 
                " AND a.cashbook_month       =?\n" + 
                " AND a.cashbook_year        =?\n" + 
                " AND a.TDA_OR_TCA          IN('TDAO','TCAO','TDACB','TCACB')\n" + 
                " AND a.ORGINATING_JVR_NO   IS NOT NULL\n" + 
                " AND a.ORGINATING_JVR_DATE IS NOT NULL\n" + 
                " AND a.STATUS                 ='L'\n" + 
                " AND a.ACCEPTANCE_STATUS='Y'\n" + 
                " AND a.accepting_date is not null";
              
                ps1=connection.prepareStatement(sql);
                ps1.setInt(1,AccountUnitCode);
                ps1.setInt(2,CashBookMonth);
                ps1.setInt(3,CashBookYear);
              
                System.out.println("before execution******************");
                ResultSet rs=ps1.executeQuery();
                while(rs.next())
                {
                
                    if(a3==0)
                    {
                    tda_tca_vo=rs.getString("VOUCHER_NO");
                    a3=1;
                    }
                    else {
                        tda_tca_vo=tda_tca_vo+","+rs.getString("VOUCHER_NO");
                    }
                    tda_tca_dateCount++;
                }
                 
                System.out.println("after execution"+tda_tca_dateCount);
                
                }
                catch(Exception e)
                {
                System.out.println(e);
                }  
       // accetance side
        
        
        try
                 {
              
                String sql="SELECT a.VOUCHER_NO,a.VOUCHER_DATE,a.ACCOUNTING_UNIT_ID,a.UPDATED_BY_USERID\n" + 
                " FROM FAS_TDA_TCA_RAISED_MST a\n" + 
                " WHERE (a.VOUCHER_DATE>a.ACCEPTING_JVR_DATE\n" + 
                " or a.VOUCHER_DATE>a.responding_jvr_date)\n" + 
                " and a.accounting_unit_id =?               \n" + 
                " AND a.cashbook_month       =?\n" + 
                " AND a.cashbook_year        =?\n" + 
                " AND a.TDA_OR_TCA          IN('TDAA','TCAA')\n" + 
                " AND a.STATUS                 ='L'\n" + 
                " AND a.ACCEPTING_JVR_NO   IS NOT NULL\n" + 
                " AND a.ACCEPTING_JVR_DATE IS NOT NULL\n" + 
                " AND a.ACCEPTANCE_STATUS='Y'";
              
                ps1=connection.prepareStatement(sql);
                ps1.setInt(1,AccountUnitCode);
                ps1.setInt(2,CashBookMonth);
                ps1.setInt(3,CashBookYear);
              
                System.out.println("before execution******************");
                ResultSet rs=ps1.executeQuery();
                while(rs.next())
                {
                
                    if(a3==0)
                    {
                    acceptance_vo=rs.getString("VOUCHER_NO");
                    a3=1;
                    }
                    else {
                        acceptance_vo=acceptance_vo+","+rs.getString("VOUCHER_NO");
                    }
                    accept_Count++;
                }
                 
                System.out.println("after execution acceptance_vo:"+acceptance_vo);
                
                }
                catch(Exception e)
                {
                System.out.println(e);
                }
        */
        
        /*
                 * TDA_TCA TABLE  CHECK===========
                 * 
                 * TDA / TCA acceptance advice entered , but journal not posted*/
                 
                  
                  
         try
                  {
                     
                  String sql= "select  a.VOUCHER_NO,a.ACCEPTING_JVR_NO from FAS_TDA_TCA_RAISED_MST a where a.accounting_unit_id=?   and a.cashbook_month=? and a.cashbook_year=? and a.TDA_OR_TCA in('TDAA','TCAA') and (a.ACCEPTING_JVR_NO is null or a.ACCEPTING_JVR_NO=0)  and a.ACCEPTING_JVR_DATE is null and STATUS='L' ";
                
                  ps1=connection.prepareStatement(sql);
                      ps1.setInt(1,AccountUnitCode);
                      ps1.setInt(2,CashBookMonth);
                      ps1.setInt(3,CashBookYear);
                  //System.out.println("before execution&&&&&&&&&&&&&&&&&&&&&&&");
                  ResultSet rs=ps1.executeQuery();
                  while(rs.next())
                  {
                      if(a4==0)
                      {
                      tca_vo=rs.getString("VOUCHER_NO");
                      a4=1;
                      }
                      else {
                          tca_vo=tca_vo+","+rs.getString("VOUCHER_NO");
                      }
                      tca_count++;
                      
                  }
           
                 // System.out.println("after execution");
                  
                  }
                  catch(Exception e)
                  {
                  System.out.println(e);
                  }
        
        /*TDA head entered in payment but post TDA not done*/
        
        try {
        
        
         String sql=" select  a.VOUCHER_NO from FAS_PAYMENT_TRANSACTION a where a.accounting_unit_id=?  and a.cashbook_month=? and a.cashbook_year=? and VOUCHER_NO in( select VOUCHER_NO from  FAS_PAYMENT_MASTER where accounting_unit_id=? and cashbook_month=? and cashbook_year=? and PAYMENT_STATUS='L') and a.account_head_code=?   and a.PAYABLE_VOUCHER_NO=0 and PAYABLE_VOUCHER_DATE is null " ;
                
                
                System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+sql);
                 ps1=connection.prepareStatement(sql);
                 ps1.setInt(1,AccountUnitCode);
                 ps1.setInt(2,CashBookMonth);
                 ps1.setInt(3,CashBookYear);
         ps1.setInt(4,AccountUnitCode);
         ps1.setInt(5,CashBookMonth);
         ps1.setInt(6,CashBookYear);
         ps1.setInt(7,900108);
                 System.out.println("before execution");
                 ResultSet rs=ps1.executeQuery();
                 //System.out.println("after execution");
                 while (rs.next()) {   
                     if(a5==0)
                     {
                     Payment_vo=rs.getString("VOUCHER_NO");
                     a5=1;
                     }
                     else {
                         Payment_vo=Payment_vo+","+rs.getString("VOUCHER_NO");
                     }
                     System.out.println("Payment Voucher No::::::::::::"+Payment_vo);
                     payment_count++;                    
                 }            
                 
        System.out.println("after execution");  
        }
        catch(Exception e) {
         System.out.println(e);
        }
        
        /*TCA Head entered in receipt but post TCA not done
        
        Receipt Table Check =================
          *  TCA Head entered in receipt but post TCA not done*****************/
          
     
        try
         {
           
         
         String sql= "select  a.RECEIPT_NO from fas_receipt_transaction a where a.accounting_unit_id=?  and a.cashbook_month=? and a.cashbook_year=? and a.account_head_code=? and a.RECEIPT_NO IN(SELECT RECEIPT_NO FROM FAS_RECEIPT_MASTER\n" + 
         "WHERE accounting_unit_id     =?            \n" + 
         "AND cashbook_month           =?\n" + 
         "AND cashbook_year            =?\n" + 
         "AND RECEIVABLE_VOUCHER_NO    =0\n" + 
         "AND RECEIVABLE_VOUCHER_DATE IS NULL\n" + 
         "AND RECEIPT_STATUS           ='L')\n ";
            System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+sql);
             ps1=connection.prepareStatement(sql);
             ps1.setInt(1,AccountUnitCode);
             ps1.setInt(2,CashBookMonth);
             ps1.setInt(3,CashBookYear);
             
             ps1.setInt(4,901001);
            ps1.setInt(5,AccountUnitCode);
            ps1.setInt(6,CashBookMonth);
            ps1.setInt(7,CashBookYear);
         System.out.println("before execution");
         ResultSet rs=ps1.executeQuery();
         System.out.println("after execution");
         while (rs.next()) {                      
          
          
             if(a6==0)
             {
             receipt_vo=rs.getString("RECEIPT_NO");
             a6=1;
             }
             else {
                 receipt_vo=receipt_vo+","+rs.getString("RECEIPT_NO");
             }
             receipt_count++;      
            
         } 
         //}
        }
         catch (Exception e)
         {
         System.out.println(e);
         }
        /**
                          * Trial Balance Check New On March 3
                          * TCA Head entered in payroll journal but PostTCA not done*/
                          
                        
                          try
                          {
                          //last modifications for 2 offices on 8thmarch2012  
//                          String sql= "select  a.VOUCHER_NO from FAS_JOURNAL_TRANSACTION a where a.accounting_unit_id=? and a.accounting_for_office_id=  and a.cashbook_month=? and a.cashbook_year=? and a.account_head_code=? and a.voucher_no in(SELECT VOUCHER_NO\n" + 
//                          "FROM FAS_JOURNAL_MASTER\n" + 
//                          "WHERE accounting_unit_id=?\n" + 
//                          "AND cashbook_month      =?\n" + 
//                          "AND cashbook_year       =? and CB_REF_TYPE is null and JOURNAL_STATUS      ='L' and JOURNAL_TYPE_CODE=54)";
                        	  
                        	  String sql="Select A.Voucher_No,A.Account_Head_Code,A.Amount "+
								"	From Fas_Journal_Transaction A,Fas_Journal_Master b "+
                        		  "	Where A.Accounting_Unit_Id=? "+
                        		  "	And A.Cashbook_Month      =? "+
                        		  "	And A.Cashbook_Year       =? "+
                        		  "	And A.Account_Head_Code   =? "+
                        		  "	And A.Accounting_For_Office_Id=B.Accounting_For_Office_Id "+
                        		  "	And A.ACCOUNTING_UNIT_ID=B.ACCOUNTING_UNIT_ID "+
                        		  "	And A.CASHBOOK_YEAR=B.CASHBOOK_YEAR "+
                        		  "	And A.Cashbook_Month=B.Cashbook_Month "+
                        		  "	And A.Voucher_No=B.Voucher_No "+
                        		  "	And B.Accounting_Unit_Id=? "+
                        		  "	And B.Cashbook_Month      =? "+
                        		  "	  And B.Cashbook_Year       =? "+
                        		  "	  And B.Cb_Ref_Type        Is Null "+
                        		  "	  And B.Journal_Status      ='L' "+
                        		  "	  And b.Journal_Type_Code   =54";
                        	  
                              System.out.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^"+sql);
                          ps1=connection.prepareStatement(sql);
                              ps1.setInt(1,AccountUnitCode);
                              ps1.setInt(2,CashBookMonth);
                              ps1.setInt(3,CashBookYear);
                              ps1.setInt(4,901001);
                              ps1.setInt(5,AccountUnitCode);
                              ps1.setInt(6,CashBookMonth);
                              ps1.setInt(7,CashBookYear);
                          System.out.println("before execution");
                          ResultSet rs=ps1.executeQuery();
                          System.out.println("after execution");
                          while (rs.next()) {                    
                         
                             
                                
                                  if(a7==0)
                                  {   
                                  payroll_vo=rs.getString("VOUCHER_NO");
                                  a7=1;
                                  }
                                  else {
                                      payroll_vo=payroll_vo+","+rs.getString("VOUCHER_NO");
                                  }
                              payroll_count++;                    
                              
                         // }
                          } 
                             System.out.println("after execution"+payroll_vo);
                        
                          }
                          catch (Exception e)
                          {
                          System.out.println(e);
                          }
                        
                         
        /*   TDA / TCA responding is pending for acceptance*/
        
        
        /**
                   * Trial Balance Check New On March 3
                   * TDA/TCA Responding is pending for acceptance*/
                   
                
                   try
                   {
                
                   
                     String sql= "SELECT a.VOUCHER_NO\n" + 
                     " FROM FAS_TDA_TCA_RAISED_MST a,FAS_TDA_TCA_RAISED_MST b\n" + 
                     " WHERE a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID \n" + 
                     " and a.ACCEPTING_SLNO=b.VOUCHER_NO \n" + 
                     " and a.accounting_unit_id =?\n" + 
                     " AND a.cashbook_month       =?\n" + 
                     " AND a.cashbook_year        =?\n" + 
                     " AND a.TDA_OR_TCA          IN('TDAO','TCAO')\n" + 
                     " AND a.ACCEPTANCE_STATUS    ='Y'\n" + 
                     " AND b.ACCEPTING_JVR_NO    IS NOT NULL\n" + 
                     " AND a.RESPONDING_JVR_NO   IS NULL\n" + 
                     " AND a.RESPONDING_JVR_DATE IS NULL\n" + 
                     " AND a.STATUS               ='L'";
                       System.out.println("^^^^^^^^^^^^^^^^^^:::::::::::::::;"+sql);
                   ps2=connection.prepareStatement(sql);
                       ps2.setInt(1,AccountUnitCode);
                       ps2.setInt(2,CashBookMonth);
                       ps2.setInt(3,CashBookYear);
              
                   System.out.println("before execution::::::::::::::::::");
                   ResultSet rs1=ps2.executeQuery();
                   while(rs1.next())
                   {
                 //  System.out.println("whileeeeeeeee");
                          if(a8==0)
                          {   
                          sus_vo=rs1.getString("VOUCHER_NO");
                          a8=1;
                          }
                          else {
                              sus_vo=sus_vo+","+rs1.getString("VOUCHER_NO");
                          }
                          sus_count++;
                      
                      }
                     
                    
                   }
                   catch (Exception e)
                   {
                   System.out.println("error in::::"+e);
                   }
               
                   //New check code for balance type CR of HOA sum_current_month_CR must >=0 and current_month_dr=0
                   //for balance type DR of HOA, sum_current_month_DR must >=0 and cuurent_month_cr=0
                   //for balance type null either sum_current_month_CR or sum_current_month_DR >=0
                   //for balance type NetCR/NetDR closing balance CR/DR must >=0
                   //implemented on 05/05/2018-----
                   //case I CR=0
                   try
                   {
                	   String sql_HOA="SELECT gl.ACCOUNT_HEAD_CODE, " +
                			   "  h.BALANCE_TYPE, " +
                			   "  gl.CURRENT_MONTH_DEBIT " +
                			   "FROM COM_MST_ACCOUNT_HEADS h, " +
                			   "  fas_general_ledger gl " +
                			   "WHERE gl.ACCOUNTING_UNIT_ID =? " +
                			   "AND gl.YEAR                 =? " +
                			   "AND gl.MONTH                =? " +
                			   "AND gl.account_head_code    =h.account_head_code " +
                			   "AND h.balance_type          ='CR' " +
                			   "AND gl.CURRENT_MONTH_DEBIT !=0 " +
                			   "ORDER BY ACCOUNT_HEAD_CODE"; 
                	   ps2=connection.prepareStatement(sql_HOA);
                       ps2.setInt(1,AccountUnitCode);
                       ps2.setInt(2,CashBookYear);
                       ps2.setInt(3,CashBookMonth);
                       //System.out.println("before execution HOA Balance type check");
                       ResultSet rs1=ps2.executeQuery();
                       while(rs1.next())
                       {
                      // System.out.println("whileeeeeeeee");
                              if(a9==0)
                              {   
                            	  HOA_list=rs1.getString("ACCOUNT_HEAD_CODE");
                              a9=1;
                              }
                              else {
                            	  HOA_list=HOA_list+","+rs1.getString("ACCOUNT_HEAD_CODE");
                              }
                              HOA_count++;
                          System.out.println("Inside New HOA CR Check code %%%"+HOA_count);
                          }
                   }
                   catch(Exception e)
                   {
                	   System.out.println("Error in HOA balance type and amount**"+e);
                   }
                 //case II DR ==0
                   try
                   {
                	   String sql_HOA_DR="SELECT gl.ACCOUNT_HEAD_CODE, " +
                			   "  h.BALANCE_TYPE, " +
                			   "  gl.CURRENT_MONTH_CREDIT " +
                			   "FROM COM_MST_ACCOUNT_HEADS h, " +
                			   "  fas_general_ledger gl " +
                			   "WHERE gl.ACCOUNTING_UNIT_ID =? " +
                			   "AND gl.YEAR                 =? " +
                			   "AND gl.MONTH                =? " +
                			   "AND gl.account_head_code    =h.account_head_code " +
                			   "AND h.balance_type          ='DR' " +
                			   "AND gl.CURRENT_MONTH_CREDIT !=0 " +
                			   "ORDER BY ACCOUNT_HEAD_CODE"; 
                	   ps2=connection.prepareStatement(sql_HOA_DR);
                       ps2.setInt(1,AccountUnitCode);
                       ps2.setInt(2,CashBookYear);
                       ps2.setInt(3,CashBookMonth);
                       //System.out.println("before execution HOA Balance DR type check");
                       ResultSet rs1=ps2.executeQuery();
                       while(rs1.next())
                       {
                       //System.out.println("whileeeeeeeee");
                              if(a10==0)
                              {   
                            	  HOA_list_DR=rs1.getString("ACCOUNT_HEAD_CODE");
                              a10=1;
                              }
                              else {
                            	  HOA_list_DR=HOA_list_DR+","+rs1.getString("ACCOUNT_HEAD_CODE");
                              }
                              HOA_count_DR++;
                          System.out.println("Inside New HOA CR Check code %%%"+HOA_count_DR);
                          }
                   }
                   catch(Exception e)
                   {
                	   System.out.println("Error in HOA balance type and amount**"+e);
                   }
                 //case III NCR ==0
                   try
                   {
                	   String sql_HOA_NCR= "SELECT gl.ACCOUNT_HEAD_CODE, " +
                               "  h.BALANCE_TYPE, " +
                               "  gl.CURRENT_MONTH_DEBIT, "+
                               "  gl.CURRENT_MONTH_CREDIT "+
                               "FROM COM_MST_ACCOUNT_HEADS h, " +
                               "  fas_general_ledger gl " +
                               "WHERE gl.ACCOUNTING_UNIT_ID       =? " +
                               "AND gl.YEAR                       =? " +
                               "AND gl.MONTH                      =? " +
                               "AND gl.account_head_code          =h.account_head_code " +
                               "AND h.balance_type                ='NCR' " +
                               "AND gl.CURRENT_MONTH_CREDIT      < gl.CURRENT_MONTH_DEBIT " +
                               "ORDER BY ACCOUNT_HEAD_CODE";
                   	   ps2=connection.prepareStatement(sql_HOA_NCR);
                       ps2.setInt(1,AccountUnitCode);
                       ps2.setInt(2,CashBookYear);
                       ps2.setInt(3,CashBookMonth);
                       //System.out.println("before execution HOA Balance DR type check");
                       ResultSet rs1=ps2.executeQuery();
                       while(rs1.next())
                       {
                       //System.out.println("whileeeeeeeee");
                              if(a11==0)
                              {   
                            	  HOA_list_NCR=rs1.getString("ACCOUNT_HEAD_CODE");
                              a11=1;
                              }
                              else {
                            	  HOA_list_NCR=HOA_list_NCR+","+rs1.getString("ACCOUNT_HEAD_CODE");
                              }
                              HOA_count_NCR++;
                          System.out.println("Inside New HOA CR Check code %%%"+HOA_count_NCR);
                          }
                   }
                   catch(Exception e)
                   {
                	   System.out.println("Error in HOA balance type and amount**"+e);
                   }
                  
                 //case iv NDR ==0
                   try
                   {
                	   String sql_HOA_NDR= "SELECT gl.ACCOUNT_HEAD_CODE, " +
                               "  h.BALANCE_TYPE, " +
                               "  gl.CURRENT_MONTH_DEBIT, "+
                               "  gl.CURRENT_MONTH_CREDIT "+
                               "FROM COM_MST_ACCOUNT_HEADS h, " +
                               "  fas_general_ledger gl " +
                               "WHERE gl.ACCOUNTING_UNIT_ID       =? " +
                               "AND gl.YEAR                       =? " +
                               "AND gl.MONTH                      =? " +
                               "AND gl.account_head_code          =h.account_head_code " +
                               "AND h.balance_type                ='NDR' " +
                               "AND gl.CURRENT_MONTH_DEBIT     <  gl.CURRENT_MONTH_CREDIT " +
                               "ORDER BY ACCOUNT_HEAD_CODE";
                   	   ps2=connection.prepareStatement(sql_HOA_NDR);
      
                   	   ps2.setInt(1,AccountUnitCode);
                       ps2.setInt(2,CashBookYear);
                       ps2.setInt(3,CashBookMonth);
                       //System.out.println("before execution HOA Balance DR type check");
                       ResultSet rs1=ps2.executeQuery();
                       while(rs1.next())
                       {
                       //System.out.println("whileeeeeeeee");
                              if(a12==0)
                              {   
                            	  HOA_list_NDR=rs1.getString("ACCOUNT_HEAD_CODE");
                              a12=1;
                              }
                              else {
                            	  HOA_list_NDR=HOA_list_NDR+","+rs1.getString("ACCOUNT_HEAD_CODE");
                              }
                              HOA_count_NDR++;
                          System.out.println("Inside New HOA CR Check code %%%"+HOA_count_NDR);
                          }
                   }
                   catch(Exception e)
                   {
                	   System.out.println("Error in HOA balance type and amount**"+e);
                   }
          // End of New Check function for CR/DR/NCR/NDR------
        try
        {
        	 System.out.println("--------------tda_count"+tda_count+"tca_count"+tca_count+"receipt_count"+receipt_count+"payment_count"+payment_count+"payroll_count"+payroll_count);
        	
        	System.out.println("generate_pbStatus"+generate_pbStatus);
        	 if(AccountUnitCode !=5 && CashBookYear !=2015 && CashBookMonth !=2)   { 
        		
        if(jou_count>0 ||pay_count>0 || rec_count>0) {
        
        if(tda_count>0||tca_count>0||receipt_count>0||payment_count>0||payroll_count>0)
        //if(tda_count>0||tca_count>0||receipt_count>0||payment_count>0||payroll_count>0||sus_count>0)
        {
         
         
         connection.rollback();
            String rk="SUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE To Be Filled  In Payment..."+pay_vo +"\nSUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE To" +
            "Be Filled In Receipt..."+rec_vo+"\nSUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE  To Be Filled  In journal"+jou_vo;
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+rk);
           // sendMessage(response,rk,"ok");
         sendMessage(response,
                     " SUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE  To Be Filled  In Payment..."+pay_vo +" <br><br> SUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE  To Be Filled  In Receipt..."+rec_vo+ 
                     "<br><br>SUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE  To Be Filled  In journal>>>>>>>>..."+jou_vo+"<br><br>TDA,TCA Originating Journal Posting is Pending Vr.Nos..."+tda_vo +"<br><br>TDA,TCA Accepting Journal Posting is Pending Vr.Nos.."+tca_vo+ 
                         "<br><br>Posting of TDA head in Final Payment is Pending Vr.Nos...."+Payment_vo+"<br><br>Posting of " +
                         "TCA head in Receipt is Pending Vr.Nos..."+receipt_vo+"<br><br>Posting of TCA from Payroll Journal is " +
                         "Pending Vr.Nos..."+payroll_vo,"ok");
            //"Pending Vr.Nos..."+payroll_vo+"<br><br>TDA/TCA Suspense Head Clearance Journal Posting is Pending  Vr.Nos...."+sus_vo,
         return;
        
         
         
        }
           
        
        else {
            System.out.println("ftHO_count222222222222222222222"+ftHO_count);
            connection.rollback();
            String rk="<br><br>SUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE To Be Filled  In Payment..."+pay_vo +"<br><br>SUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE " +
            "To Be Filled In Receipt..."+rec_vo+"<br><br>SUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE  To Be Filled  In journal********..."+jou_vo;
            System.out.println("$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$$"+rk);
            sendMessage(response,rk,"ok");
            return;
        }
        
        }
        }
//        else if(nov_count_inc>0) {
//            System.out.println("nov_count_inc:::::"+nov_count_inc);
//            connection.rollback(); 
//            //sendMessage(response, "PB Updation is done upto..."+count_pb+"/"+CashBookYear+" only,Please do upto "+prevMonth+"/"+CashBookYear,"ok");  
//             sendMessage(response, "PB Not done Upto Previous Month...","ok");  
//            return;
//        }
        
       // else if(tda_count>0||tca_count>0||receipt_count>0||payment_count>0||payroll_count>0||sus_count>0)
        else if(tda_count>0||tca_count>0||receipt_count>0||payment_count>0||payroll_count>0)//||counted_field>0
        {
        	 System.out.println("--------------tda_count"+tda_count+"tca_count"+tca_count+"receipt_count"+receipt_count+"payment_count"+payment_count+"payroll_count"+payroll_count);
            System.out.println("ftHO_count3333333333333333333333333"+ftHO_count);
            connection.rollback(); 
            sendMessage(response,
                        " TDA,TCA Originating Journal Posting is Pending Vr.Nos..."+tda_vo +"<br><br>TDA,TCA Accepting Journal Posting is Pending Vr.Nos..."+tca_vo+ 
                        "<br><br>Posting of TDA head in Final Payment is Pending Vr.Nos..."+Payment_vo+"<br><br>Posting of TCA head in " +
                        "Receipt is Pending Vr.Nos..."+receipt_vo+"<br><br>Posting of TCA from Payroll Journal is " +
                        "Pending Vr.Nos..."+payroll_vo,"ok");  
                        //"Pending Vr.Nos..."+payroll_vo+"<br><br>TDA,TCA Suspense Head Clearance Journal Posting is Pending Vr.Nos..."+sus_vo,
            return;
            
        }
       /* else if(tpa_count>0) 
        {
           
            connection.rollback(); 
            sendMessage(response, "TPA CREDIT/DEBIT Acceptance is Pending","ok");  
            return;    
            
        }  */
    
      /*  else if(tda_tca_dateCount>0) 
        {
            System.out.println("tda_tca_dateCount33:::::"+tda_tca_dateCount);
            connection.rollback(); 
            sendMessage(response,
                        " TDA,TCA Originating Journal Posting is Pending Vr.Nos..."+tda_vo +"<br><br>TDA,TCA Accepting Journal Posting is Pending Vr.Nos..."+tca_vo+ 
                        "<br><br>Accepting Date is less than Originating Voucher Date for voucher_no..."+tda_tca_vo,"ok");  
            return;
        }
        else if(accept_Count>0) 
        {
            System.out.println("accept_Count:::::"+accept_Count);
            connection.rollback(); 
            sendMessage(response,
                        " TDA,TCA Originating Journal Posting is Pending Vr.Nos..."+tda_vo +"<br><br>TDA,TCA Accepting Journal Posting is Pending Vr.Nos..."+tca_vo+ 
                        "<br><br>Accepting JVR Date is less than Originating Voucher Date in type(TDAA,TCAA) for voucher_no..."+acceptance_vo,"ok");  
            return;
            
        }   */
       else if(tda_tca_counted>0) 
              {
                  System.out.println("tda_tca_counted:::::"+tda_tca_counted);
                  connection.rollback(); 
                  sendMessage(response,
                              " TDA,TCA Originating Journal Posting is Pending Vr.Nos..."+tda_vo +"<br><br>TDA,TCA Accepting Journal Posting is Pending Vr.Nos..."+tca_vo+ 
                              "<br><br>TDA/TCA Rejection Verifacation is Pending for voucher_no..."+tda_tca_status_NO,"ok");  
                  return;
              } 
     /*  else if(asset_cleared==0)
       {
    	   connection.rollback(); 
           sendMessage(response,
                       " Please Verify A52 and AA52 Register Entry...","ok");  
           return;
       } */
              
     /*     else if(counted_field>0) {
              System.out.println("counted_field:::::"+counted_field);
              connection.rollback(); 
              //sendMessage(response, "PB Updation is done upto..."+count_pb+"/"+CashBookYear+" only,Please do upto "+prevMonth+"/"+CashBookYear,"ok");  
               sendMessage(response, "PB Updation is not done from April...","ok");  
              return;
          } */
           
            
       /*     else if(counted_field_sub>0) {
                System.out.println("counted_field_sub:::::"+counted_field_sub);
                connection.rollback(); 
                sendMessage(response, "PB Updation is done in SubLedger Posting upto..."+count_pb_sub+"/"+CashBookYear+" only,Please do upto "+prevMonth_sub+"/"+CashBookYear,"ok");  
                return;
            }  */
            
              
        //else if(adj_memo_count>0 && AccountUnitCode==5) {
      /*  else if(adj_memo_count>0) 
        {
            System.out.println("adj_memo_count:::::"+adj_memo_count);
            connection.rollback(); 
            sendMessage(response, " Adj.Memo Posting From Bank Receipts is Pending For Vr.Nos..."+adj_memo_no,"ok");  
            return;    
        }  */
        else if(ftvsfr==0) 
        {
           
            connection.rollback(); 
            sendMessage(response, " Please View FT vs FR Report and then Generate TB","ok");  
            return;    
        } 
        //temporary block is shifted to Freeze TB
       
      /*  else if(generate_pbStatus>0)
        {
           
            connection.rollback(); 
            sendMessage(response, " PB Updation is not Done Upto the Previous month for GeneralLedger","ok");  
            return;    
        }
        else if(generate_pbStatus_sl>0) 
        {
           
            connection.rollback(); 
            sendMessage(response, " PB Updation is not Done Upto the Previous month for Sub Ledger","ok");  
            return;    
        }  */
        else if(rsflag==0)
        {
        	connection.rollback(); 
            sendMessage(response, " Please Verify TDA/TCA Report and Then Generate TB","ok");  
            return;
        }if(rsflag_memo==0)
    	{
        	if(AccountUnitCode ==3 || AccountUnitCode==5 || AccountUnitCode==6 || AccountUnitCode==999){
        		
        	}{
    		
        		sendMessage(response, " Please Verify GPF Payment and Cheque memo and Then Generate TB ","ok"); 
           return;
        	}
           
    	}
        if(rsflag_adj==0)
        {
        	connection.rollback(); 
            sendMessage(response, " Please Verify Adjustment Memo and Then Generate TB","ok");  
            return;
        }
        else if(diff_error_Adj>0)
        {
        	connection.rollback(); 
            sendMessage(response, " Please Once Again Verify Adjustment Memo and Then Generate TB","ok");  
            return;
        }
        else if(diff_error>0)
        {
        	connection.rollback(); 
            sendMessage(response, " Please Once Again Verify TDA/TCA Report and Then Generate TB","ok");  
            return;
        }/*if(diff_errorCheque>0)
    	{
    		sendMessage(response, " Please Once Again Verify Cheque Memo and Payment ","ok");  
            
            return;
            
    	}if(rsflag_memo==0)
    	{
    		sendMessage(response, " Please Verify Cheque Memo and Payment ","ok"); 
           return;
           
   	}*/
        else if(brs_update_flag==0)
        {
        	connection.rollback(); 
            sendMessage(response, " Please Update BRS Status","ok");  
            return;
        }
        else if(cl_unit_yes==0)
        {
        	connection.rollback(); 
            sendMessage(response, "please Generate and Freeze TB for TPA Originated Unit-"+closedunitname,"ok");  
            return;
        }
        else if(counted_bank_bal>0)
        {
        	
        	connection.rollback(); 
          	  sendMessage(response, "Bank Balance Update  is Incomplete","ok");
            return;	
        	
        }   
        
        
        else if(ftOff_count>0) 
        {
            System.out.println("ftOff_count>>>>>>>>>>>>>>>"+ftOff_count);
            connection.rollback();
            String rk="SUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE To Be Filled " +
            "In Payment..."+pay_vo +"\nSUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE To Be Filled In Receipt..."+rec_vo+"\nSUB_LEDGER_TYPE_CODE or " +
            "SUB_LEDGER_CODE  To Be Filled  In journal"+jou_vo;
            System.out.println("testttttttttttt ft off count "+rk);
           
            sendMessage(response,
                     "<br><br>Fund Transfer Verification is Pending...",
                     "ok");
            return;
            
        }
            else if(pay_counted>0) 
            {
                System.out.println("pay_counted>>>>>>>>>>>>>>>"+pay_counted);
                connection.rollback();
                String rk="SUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE To Be Filled " +
                "In Payment..."+pay_vo +"\nSUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE To Be " +
                "Filled In Receipt..."+rec_vo+"\nSUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE  To Be Filled  In journal"+jou_vo;
               System.out.println("testttttttttttt pay countb  "+rk);
               
                sendMessage(response,
                         " <br><br>Payment Verification is Pending.",
                         "ok");
                return;
                
            }
            
            else if(hotrn_counted>0) 
            {
                System.out.println("hotrn_counted>>>>>>>>>>>>>>>"+hotrn_counted);
                connection.rollback();
                String rk="AutoFund Receipt Pending For the Previous Months:";
             //   System.out.println("testttttttttttt"+rk);
               
                sendMessage(response,
                         " <br><br>AutoFund Receipt is Pending.",
                         "ok");
                return;
                
            }
        
            
        else if(ftHO_count>0) {
          System.out.println("ftHO_count>>>>>>>>>>>>>>>"+ftHO_count);
            connection.rollback();
            String rk="SUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE To Be Filled  In Payment..."+pay_vo +"\nSUB_LEDGER_TYPE_CODE or " +
           "SUB_LEDGER_CODE To Be Filled In Receipt..."+rec_vo+"\nSUB_LEDGER_TYPE_CODE or SUB_LEDGER_CODE  To Be Filled  In journal"+jou_vo;
           // System.out.println("testttttttttttt"+rk);
            
            sendMessage(response,
                     " <br><br>Fund Transfer Verification is Pending for HO...",
                     "ok");
           return;
       }
        
        //added on 05/05/2018 ****
       //nk else if(HOA_count>0)
        else if(HOA_count>0)
        {
        	System.out.println("HOA_count>>>>>>>>>>>"+HOA_count);
        	connection.rollback();
        	String rk="List of CR HOA in Current month Debit >0 "+HOA_list;
        	//System.out.println("Testt in HOA***"+rk);
        	sendMessage(response," <br><br>Current month Debit cant be >0..."+HOA_list+" ",
        			"ok");
        	return;
        }
        
        //DR current month
        else if(HOA_count_DR>0)
        {
        	System.out.println("HOA_count>>>>>>>>>>>"+HOA_count_DR);
        	connection.rollback();
        	String rk="List of DR HOA in Current month Credit >0 "+HOA_list_DR;
        	//System.out.println("Testt in HOA DR***"+rk);
        	sendMessage(response," <br><br>Current month Credit cant be >0..."+HOA_list_DR+" ",
        			"ok");
        	return;
        }
      //NCR Net Closing balance Credit
        else if(HOA_count_NCR>0)
        {
        	System.out.println("HOA_count>>>>>>>>>>>"+HOA_count_NCR);
        	connection.rollback();
        	String rk="List of NET CR HOA in Current month credit < Current month debit "+HOA_list_NCR;
        	//System.out.println("Testt in HOA***"+rk);
        	sendMessage(response," <br><br>Net CR HOA CM CR cant be < CM DR..."+HOA_list_NCR+" ",
        			"ok");
        	return;
        }
        
        //NDR Net Closing balance Debit
        else if(HOA_count_NDR>0)
        {
        	System.out.println("HOA_count>>>>>>>>>>>"+HOA_count_NDR);
        	connection.rollback();
        	String rk="List of NET DR HOA in CM DR < CM CR "+HOA_list_NDR;
        	//System.out.println("Testt in HOA DR***"+rk);
        	sendMessage(response," <br><br>Net DR HOA CM DR cant be < CM CR..."+HOA_list_NDR+" ",
        			"ok");
        	return;
        }
        
        }
        catch(Exception e) {
         System.out.println("Final Output"+e);
        }      
        
        
        
        // ----------------------------------------------ends 4/5/2011


        //-----------------------------------------------------------------------------------------------------------------
        /*  try {

                      int result_code = -1;
                      int error_code = -1;
                      cs1=connection.prepareCall("{call FAS_SL_GL_TB_GENERATION_CHECK(?,?,?,?,?,?,?) }");
                      cs1.setInt(1,AccountUnitCode);
                      cs1.setInt(2,0);
                      cs1.setInt(3,CashBookMonth);
                      cs1.setInt(4,CashBookYear);
                      cs1.setString(5,"TB_FREEZE");
                      cs1.registerOutParameter(6,java.sql.Types.NUMERIC);
                      cs1.registerOutParameter(7,java.sql.Types.NUMERIC);

                      cs1.execute();

                      result_code=cs1.getInt(6);
                      error_code=cs1.getInt(7);

                      if ( error_code == 0 ) {

                          if ( result_code == 10) {
                                sendMessage(response,"Both Sub Ledger and General Ledger Posting not Generated for the Month "+CashBookMonth+" / " + CashBookYear,"ok");
                                return;
                          }
                          else if ( result_code == 20) {
                               sendMessage(response,"Sub Ledger not Generated for the Month "+CashBookMonth+" / " + CashBookYear,"ok");
                               return;
                          }
                          else if ( result_code == 30) {
                              sendMessage(response,"General Ledger not Generated for the Month "+CashBookMonth+" / " + CashBookYear,"ok");
                              return;
                          }
                          else if ( result_code == -300) {
                              sendMessage(response,"Receipts have been modified after SL and GL posting. Please do SL and GL posting once again","ok");
                              return;
                          }
                          else if ( result_code == -301) {
                              sendMessage(response,"Payments have been modified after SL and GL posting. Please do SL and GL posting once again","ok");
                              return;
                          }
                          else if ( result_code == -302) {
                              sendMessage(response,"Journals have been modified after SL and GL posting. Please do SL and GL posting once again","ok");
                              return;
                          }
                          else if ( result_code == -303) {
                              sendMessage(response,"Fund Receipts have been modified after SL and GL posting. Please do SL and GL posting once again","ok");
                              return;
                          }
                          else if ( result_code == -304) {
                              sendMessage(response,"Fund Transfers have been modified after SL and GL posting. Please do SL and GL posting once again","ok");
                              return;
                          }

                      }

                 }
                 catch(Exception e ) {
                     System.out.println(e);
                 }

              */

        //-----------------------------------------------------------------------------------------------------------------

        try {
            //connection.setAutoCommit(false);

            int err_code = -1;
            System.out.println("err" + err_code);
            System.out.println(AccountUnitCode+"::CashBookYear:"+CashBookYear+"::CashBookMonth::"+CashBookMonth+"::update_user::"+update_user);
            cs =  connection.prepareCall("call  FAS_TRIAL_BALANCE_PROC_1(?::numeric,?::numeric,?::numeric,?,?::numeric)");
            cs.setInt(1, AccountUnitCode);
            cs.setInt(2, CashBookYear);
            cs.setInt(3, CashBookMonth);
            cs.setString(4, update_user);
            cs.registerOutParameter(5, java.sql.Types.NUMERIC);
            cs.setInt(5, 0);
            cs.executeUpdate();
            //System.out.println("be4:::::::::::::"+cs.getInt(5));
            //err_code = cs.getInt(5);
            err_code = cs.getBigDecimal(5).intValue();      

           // err_code=0;//nk
            System.out.println("2 nd err" + err_code);
            System.out.println("count" + count);
            System.out.println("journal_count" + journal_count);
            if (err_code == 0 && count == 0 && journal_count == 0) {
                System.out.println("success");
                //connection.commit();
            } else if (err_code == 16) {
               //connection.rollback();
                sendMessage(response,
                            "Vouchers are pending for Remittance in cash,bank and fund system...",
                            "ok");
                return;
            } else if (err_code == 15) {
                // connection.rollback();
                sendMessage(response,
                            "Vouchers are pending for Remittance in bank and fund system ...",
                            "ok");
                return;
            } else if (err_code == 14) {
                //connection.rollback();
                sendMessage(response,
                            "Vouchers are pending for Remittance in cash and fund system...",
                            "ok");
                return;
            } else if (err_code == 13) {
                //connection.rollback();
                sendMessage(response,
                            "Vouchers are pending for Remittance in cash and bank system...",
                            "ok");
                return;
            } else if (err_code == 12) {
                //connection.rollback();
                sendMessage(response,
                            "Vouchers are pending for Remittance in cash system ...",
                            "ok");
                return;
            } else if (err_code == 11) {
                //connection.rollback();
                sendMessage(response,
                            "Vouchers are pending for Remittance in bank system...",
                            "ok");
                return;
            } else if (err_code == 10) {
                //connection.rollback();
                sendMessage(response,
                            "Vouchers are pending for Remittance in fund system...",
                            "ok");
                return;
            } else if (err_code == 9) {
                //connection.rollback();
                sendMessage(response,
                            "You should make General Ledger Posting...", "ok");
                return;
            } else if (count > 0) {
                //connection.rollback();
                sendMessage(response,
                            " Voucher(s) are pending for Verification in Remittance...",
                            "ok");
                return;
            } 
            else if (journal_count > 0) {
                //connection.rollback();
                sendMessage(response,
                            " Voucher(s) are pending for Verification in Journal...",
                            "ok");
                return;
            }

            else {
                sendMessage(response, "Trial Balance generation failed", "ok");
                //connection.rollback();
                return;
            }


            File reportFile = null;
            try {
            
            
                System.out.println("calling servlet...");
                reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TrialBalance_1.jasper"));
                System.out.println("from 1..."+reportFile);

                if (!reportFile.exists())
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                System.out.println("from2 ..."+reportFile);
                JasperReport jasperReport =
                    (JasperReport)JRLoader.loadObject(reportFile.getPath());
                Map map = null;
                map = new HashMap();
                map.put("cashbookyear", CashBookYear);
                map.put("cashbookmonth", CashBookMonth);
                map.put("accountingunitid", AccountUnitCode);
                map.put("monthvalue", monthInWords);
                System.out.println("from .............");

                JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperReport, map,
                                                 connection);

                System.out.println("upto");
                String rtype = "PDF"; // request.getParameter("cmbReportType");
                System.out.println(rtype);
                if (rtype.equalsIgnoreCase("HTML")) {
                    response.setContentType("text/html");

                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Challan.html\"");
                    PrintWriter out = response.getWriter();
                    JRHtmlExporter exporter = new JRHtmlExporter();
                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
                                          false);
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                          jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER,
                                          out);
                    exporter.exportReport();
                    out.flush();
                    out.close();
                } else if (rtype.equalsIgnoreCase("PDF")) {
                    System.out.println(rtype + "...inside PDF");
                    byte buf[] =
                        JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    response.setHeader ("Content-Disposition", "attachment;filename=\"Challan1.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
                } else if (rtype.equalsIgnoreCase("EXCEL")) {

                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Challan.xls\"");
                    JRXlsExporter exporterXLS = new JRXlsExporter();
                    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                                             jasperPrint);

                    ByteArrayOutputStream xlsReport =
                        new ByteArrayOutputStream();
                    exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
                                             xlsReport);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
                                             Boolean.FALSE);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,
                                             Boolean.TRUE);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
                                             Boolean.FALSE);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                                             Boolean.TRUE);
                    exporterXLS.exportReport();
                    byte[] bytes;
                    bytes = xlsReport.toByteArray();
                    ServletOutputStream ouputStream =
                        response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();

                } else if (rtype.equalsIgnoreCase("TXT")) {

                    response.setContentType("text/plain");
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Challan.txt\"");

                    JRTextExporter exporter = new JRTextExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                          jasperPrint);
                    ByteArrayOutputStream txtReport =
                        new ByteArrayOutputStream();
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                          txtReport);
                    exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,
                                          new Integer(200));
                    exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,
                                          new Integer(50));
                    exporter.exportReport();

                    byte[] bytes;
                    bytes = txtReport.toByteArray();
                    ServletOutputStream ouputStream =
                        response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();

                }

            } catch (Exception ex) {
                String connectMsg =
                    "Could not create the report " + ex.getMessage(); //+ " uu " +  ex.getLocalizedMessage();
                System.out.println(connectMsg);
                //sendMessage(response,"The Challan Report Creation failed","ok");
                sendMessage(response, "Unable to display the PDF file", "ok");
            }
            //////////////////---------------------------- End -----------------
            System.out.println("here after PDF");
            //sendMessage(response,"The Trial Balance done successfully","ok");
            System.out.println("after send message");


        } catch (Exception e) {
            System.out.println("Exception in Main:" + e);
//            try {
//                connection.rollback();
//            } catch (SQLException e1) {
//                System.out.println("catch:" + e1);
//            }
            String msg = "Trial Balance Has failed to Update";
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");

        }
    }


	private void sendMessage(HttpServletResponse response, String msg,
			String bType) {
		System.out.println("url   "+msg);
		try {
			String url = "org/Library/jsps/Messenger.jsp?message=" + msg
					+ "&button=" + bType;
			response.sendRedirect(url);
		} catch (IOException e) {
			System.out.println("Eror in send message");
		}
	}

}
