package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.MathContext;
import java.sql.DriverManager;
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

public class TrialBalance_Gen_SJV extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
    		 HttpServletResponse response) throws ServletException,
                                                            IOException {

        /**
         * Variables Connection
         */
        Connection connection = null;
        Statement statement = null;
        CallableStatement cs = null;
        PreparedStatement ps = null,ps1=null,ps22=null,prestpa=null,prestpa1=null;
        ResultSet rs2=null,rs22=null,restps=null,restps1=null;
        int cl_unit_yes=0;
        /**
         * Database connection
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


        response.setContentType(CONTENT_TYPE);


        /**
         * Get Session
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


        String userid = (String)session.getAttribute("UserId");
        System.out.println("session id is:" + userid);

        String Account_unit_Code = request.getParameter("cmbAcc_UnitCode");
      //  String Office_Code = request.getParameter("cmbOffice_code");
        String CashBook_Year = request.getParameter("txtCB_Year");
        String CashBook_Month = request.getParameter("txtCB_Month");
        String supplement_no = request.getParameter("txtsupplement_no");


        System.out.println("Account_Unit_Code is:" + Account_unit_Code);
        System.out.println("CashBook_Year is:" + CashBook_Year);
        System.out.println("CashBook Month is:" + CashBook_Month);
        System.out.println("supplement_no is :" + supplement_no);


        /**
         * Variables Declaration
         */

        int AccountUnitCode = 0;
        int OfficeCode = 0;
        int CashBookYear = 0;
        int CashBookMonth = 0;
        int Supplment_No = 0,tca_count=0,tda_count=0;
        String tca_vo="",tda_vo="";;

        /**
         * Convert String Data into Integer Data
         */
        try {
            AccountUnitCode = Integer.parseInt(Account_unit_Code);
          //  OfficeCode = Integer.parseInt(Office_Code);
            CashBookYear = Integer.parseInt(CashBook_Year);
            CashBookMonth = Integer.parseInt(CashBook_Month);
            Supplment_No = Integer.parseInt(supplement_no);

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

            ps =
  connection.prepareStatement("  select 'X'                      \n" +
                              "  from                            \n" +
                              "    FAS_TRIAL_BALANCE_STATUS_SJV  \n" +
                              "  WHERE                           \n" +
                              "     ACCOUNTING_UNIT_ID=?         \n" +
                              "  AND CASHBOOK_YEAR=?             \n" +
                              "  AND CASHBOOK_MONTH=?            \n" +
                              " and SUPPLEMENT_NO = ?             ");

            ps.setInt(1, AccountUnitCode);
            ps.setInt(2, CashBookYear);
            ps.setInt(3, CashBookMonth);
            ps.setInt(4, Supplment_No);

            ResultSet res = ps.executeQuery();
            if (res.next()) {
                sendMessage(response, "Supplement Trial Balance Already Froze",
                            "ok");
                return;
            }
            res.close();
            ps.close();
        } catch (Exception e) {
            System.out.println(e);
        }



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
                "and b.VERIFIED is null and a.created_by_module in ( 'SJV' )   ";

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

            System.out.println("journal_count--------------------------------------------->" + journal_count);
            
            if(journal_count>0) {
                sendMessage(response, " Supplement Journal Vouchers are pending for  Verification", "ok");
                return;
            }
            

        } catch (Exception e) {
            System.out.println(e);
        }
        // Joan Added on 05/05/2015 for SL mantatotry for  mantatotry Y flag Heads
        int head_man = 0 ,a2=0;
        int flagg=0;
        String jou_vo=" -- NIL";
        try {

            String sql =
                "" + " SELECT distinct A.VOUCHER_NO " +
                		" FROM fas_journal_master a , " +
                		"  FAS_JOURNAL_TRANSACTION B, " +
                		"  COM_MST_ACCOUNT_HEADS h " +
                		" WHERE A.ACCOUNTING_UNIT_ID     = ? " +
                		" AND A.CASHBOOK_MONTH           = ? " +
                		" AND a.cashbook_year            = ? " +
                		" AND a.accounting_unit_id       =b.accounting_unit_id " +
                		" AND a.accounting_for_office_id =b.accounting_for_office_id " +
                		" AND a.cashbook_year            =b.cashbook_year " +
                		" AND a.cashbook_month           =b.cashbook_month " +
                		" AND A.VOUCHER_NO               =B.VOUCHER_NO " +
                		" AND B.ACCOUNT_HEAD_CODE        =H.ACCOUNT_HEAD_CODE " +
                		" AND a.JOURNAL_STATUS           ='L' " +
                		" AND B.VERIFIED                IS NOT NULL " +
                		" AND ( B.SUB_LEDGER_CODE        =0 " +
                		" OR B.SUB_LEDGER_TYPE_CODE      =0) " +
                		" AND A.CREATED_BY_MODULE       IN ( 'SJV' ) " +
                		" AND USAGE_STATUS               ='Y' " +
                		" AND SUB_LEDGER_TYPE_APPLICABLE ='Y' " +
                		" AND SL_MANDATORY               ='Y' order by A.VOUCHER_NO ";
                		//+ ",b.account_head_code  ";
System.out.println(" SELECT distinct A.VOUCHER_NO,b.account_head_code  " +
		" FROM fas_journal_master a , " +
		"  FAS_JOURNAL_TRANSACTION B, " +
		"  COM_MST_ACCOUNT_HEADS h " +
		" WHERE A.ACCOUNTING_UNIT_ID     =  " +AccountUnitCode+
		" AND A.CASHBOOK_MONTH           =  " +CashBookMonth+
		" AND a.cashbook_year            =  " +CashBookYear+
		" AND a.accounting_unit_id       =b.accounting_unit_id " +
		" AND a.accounting_for_office_id =b.accounting_for_office_id " +
		" AND a.cashbook_year            =b.cashbook_year " +
		" AND a.cashbook_month           =b.cashbook_month " +
		" AND A.VOUCHER_NO               =B.VOUCHER_NO " +
		" AND B.ACCOUNT_HEAD_CODE        =H.ACCOUNT_HEAD_CODE " +
		" AND a.JOURNAL_STATUS           ='L' " +
		" AND B.VERIFIED                IS NOT NULL " +
		" AND ( B.SUB_LEDGER_CODE        =0 " +
		" OR B.SUB_LEDGER_TYPE_CODE      =0) " +
		" AND A.CREATED_BY_MODULE       IN ( 'SJV' ) " +
		" AND USAGE_STATUS               ='Y' " +
		" AND SUB_LEDGER_TYPE_APPLICABLE ='Y' " +
		" AND SL_MANDATORY               ='Y' order by A.VOUCHER_NO,b.account_head_code ");
            ps1 = connection.prepareStatement(sql);
            ps1.setInt(1, AccountUnitCode);
            ps1.setInt(2, CashBookMonth);
            ps1.setInt(3, CashBookYear);
            System.out.println("before execution");
            ResultSet rs = ps1.executeQuery();
            System.out.println("after execution");
            while (rs.next()) {
            	System.out.println("flagg "+flagg);
            
            	 if(a2==0)
                 {
                    System.out.println("ifffffffffff");
            		 jou_vo="("+rs.getString("VOUCHER_NO")+")";
                    //+rs.getString("account_head_code");
                System.out.println("jou_vo in iffffff"+jou_vo);
                 a2=1;
                 }
                 else {
                 
                System.out.println("else");
                	 jou_vo=jou_vo+","+""+"("+rs.getString("VOUCHER_NO")+")";
                	 //+rs.getString("account_head_code");
                     System.out.println("jou_vo in else>>>>>>"+jou_vo);
                 }
            	 head_man++;
            	
            	 System.out.println("VOUCHER_NO--------------------------------------------->" + jou_vo);
            	//head_man++;
            }

            System.out.println("head_man--------------------------------------------->" + head_man);
            
            if(head_man>1) {
                sendMessage(response, " SlType and SlCode should not zero for sl Mantatory Account Head In Journal.. "+jou_vo, "ok");
                return;
            }
            

        } catch (Exception e) {
            System.out.println(e);
        }
        
        
        try
        {
     int a3=0;
       String sql="select  a.VOUCHER_NO,a.ORGINATING_JVR_NO from FAS_TDA_TCA_RAISED_MST a where a.accounting_unit_id=? \n "+
       "and a.cashbook_month=? and a.cashbook_year=? and a.TDA_OR_TCA in('TDAO','TCAO') and  a.ORGINATING_JVR_NO is null and  a.ORGINATING_JVR_DATE is null and STATUS='L'";
     
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
           tda_vo=rs.getString("VOUCHER_NO");
           a3=1;
           }
           else {
               tda_vo=tda_vo+","+rs.getString("VOUCHER_NO");
           }
           tda_count++;
       }
       if(tda_count>0)
       {
    	   connection.rollback(); 
           sendMessage(response, "TDA,TCA Originating Journal Posting is Pending ","ok");  
           return; 
       }
       
       
       System.out.println("after execution");
       
       }
       catch(Exception e)
       {
       System.out.println(e);
       }
       //tpa
       try{
    	   String ss="SELECT t.ACCOUNTING_UNIT_ID AS unitid,t.orgin_voucher_no, "+
				" t.orgin_voucher_date, "+
    		   "   (SELECT u.accounting_unit_name "+
    		   "  FROM fas_mst_acct_units u "+
    		   "  WHERE u.accounting_unit_id=t.ACCOUNTING_UNIT_ID "+
    		   "  )                                      AS unitname, "+
    		   "  extract(MONTH FROM ACCEPT_VOUCHER_DATE)AS month1, "+
    		   "  extract(YEAR FROM ACCEPT_VOUCHER_DATE) AS year1 "+
    		   " FROM FAS_TPA_MASTER t "+
    		   " WHERE TRF_ACCOUNTING_UNIT_ID= "+AccountUnitCode+
    		   " AND STATUS                  ='L' "+
    		   " AND ACCEPTANCE_STATUS       ='Y' "+
    		   " AND ACCOUNTING_UNIT_ID IN "+
    		   "  (SELECT ACCOUNTING_UNIT_ID FROM FAS_JOURNAL_MASTER "+
    		   "  WHERE ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "+
    		   "  AND CASHBOOK_YEAR       =extract(YEAR FROM ACCEPT_VOUCHER_DATE) "+
    		   "  AND CASHBOOK_MONTH      =extract(MONTH FROM ACCEPT_VOUCHER_DATE) "+
    		   "  and voucher_no=t.orgin_voucher_no "+
    		   "  and VOUCHER_DATE=t.orgin_voucher_date "+
    		   "  AND SUPPLEMENT_NO=1 "+
    		   " and status='L' "+
    		   "  )";
    	   prestpa=connection.prepareStatement(ss);
    	   restps=prestpa.executeQuery();
    	   if(restps.next())
    	   {

    		  int closedunit=restps.getInt("unitid");
    		  String closedunitname=restps.getString("unitname");
    		  int closedmn=restps.getInt("month1");
    		  int closedyr=restps.getInt("year1");
    		   
    		  prestpa1=connection.prepareStatement("SELECT count(*)as cnt FROM fas_trial_balance_status_sjv s" +
    		   		" WHERE s.accounting_unit_id="+closedunit+" AND s.cashbook_year="+closedyr+" and s.cashbook_month="+closedmn);
    		  restps1=prestpa1.executeQuery();
    		   if(restps1.next())
    		   {
    			   cl_unit_yes=restps1.getInt("cnt");
    			   if(cl_unit_yes==0)
    			   {
    				   connection.rollback(); 
    				   sendMessage(response, "please Generate and Freeze Supplement TB for TPA Originated Unit-"+closedunitname,"ok");  
                       return;
    			   }
    		   }
    		   else
    		   {
    			   connection.rollback(); 
    			   sendMessage(response, "please Generate and Freeze Supplement TB for TPA Originated Unit-"+closedunitname,"ok");  
                   return;
    		   }
    	   }
    	   else
    	   {
    		   cl_unit_yes=1;
    	   }
       }
       catch(Exception et)
       {
       System.out.println("tpa error:"+et);
       }
        try
        {
           int a4=0;
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
        if(tca_count>0)
        {
        	connection.rollback(); 
            sendMessage(response, "TDA,TCA Accepting Journal Posting is Pending","ok");  
            return;
        	//"+tda_vo +
        }
    
        
        }
        catch(Exception e)
        {
        System.out.println(e);
        }
        
        int rsflag=0; 
        try{
             	String td="Select Verify_Flag From FAS_TDA_TCA_MONTHEND_SJV WHERE ACCOUNTING_UNIT_ID    =? AND " +
             			" Cashbook_Year           =? AND CASHBOOK_MONTH::numeric=? and SUPPLEMENT_NO=?";
             	ps22=connection.prepareStatement(td);
             	ps22.setInt(1,AccountUnitCode);
             	ps22.setInt(2,CashBookYear);
             	ps22.setInt(3,CashBookMonth);
             	ps22.setInt(4,Supplment_No);
             	rs22=ps22.executeQuery();
             	while(rs22.next())
             	{
             		rsflag++;
             	}
             	if(rsflag==0)
                {
                	//connection.rollback(); 
                    sendMessage(response, "Please Verify of TDA/TCA register For Supplement","ok");  
                    return;
                }
             	
             }
             catch(Exception ee)
             {
             	System.out.println("excep::::"+ee);
             }
        


        //dhana changes on may10 for adding office code in sl table...
        int offCount1=0;
        String msg = "";
       try
       {
    	   PreparedStatement ps3=connection.prepareStatement("delete from FAS_SUB_LEDGER_MASTER_SJV " +
    	   		"where  Accounting_Unit_Id =  "+AccountUnitCode+" and MONTH = "+CashBookMonth+" and Year  = "+CashBookYear+" and SUPPLEMENT_NO="+Supplment_No);
    	   int ss=ps3.executeUpdate();
    	   if(ss>0)
    	   {
    		   //connection.commit();
    	   }
       }
       catch (Exception e) {
           System.out.println("error in delete:"+e);
       }
			try{
				String sql="select ACCOUNTING_FOR_OFFICE_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_UNIT_ID="+AccountUnitCode;
				System.out.println("sql:::"+sql);
				PreparedStatement ps2=connection.prepareStatement(sql);
				rs2=ps2.executeQuery();
				while(rs2.next())
				{
					offCount1++;
					CallableStatement cs3 = null;
			       
			        try {
			            cs3 =
			 connection.prepareCall(" call FAS_SL_SJV_POSTING_NEW ( ?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric,?::numeric ) ");
			            cs3.setInt(1, AccountUnitCode);
			            System.out.println("offcode:::"+rs2.getInt("ACCOUNTING_FOR_OFFICE_ID"));
			            cs3.setInt(2, rs2.getInt("ACCOUNTING_FOR_OFFICE_ID"));
			            cs3.setInt(3, CashBookYear);
			            cs3.setInt(4, CashBookMonth);
			            cs3.setString(5, userid);
			            cs3.setInt(6, Supplment_No);
			            cs3.registerOutParameter(7, java.sql.Types.NUMERIC);
			            cs3.setInt(7, 0);
			            cs3.execute();
			            //int errcode = cs3.getInt(7);
			            int errcode = cs3.getBigDecimal(7).intValue();

			            System.out.println("Error Code -sl-->" + errcode);

			            if (errcode != 0) {
			                msg = "Sub Ledger Posting for SJV  Failed ";
			                msg = msg + "<br><br>";
			                sendMessage(response, msg, "ok");
			                return;
			            }

			        } catch (Exception e) {
			            System.out.println(e);
			        }

					
				}
			}
			catch (Exception e) {
	            System.out.println("exception be4 ::"+e);
	        }
        
        
        /**
         * Calling Procedure
         */
   
        
        //dhana changes on may10 for adding office code in gl table...
        int offCount=0;
        try
        {
     	   PreparedStatement ps3=connection.prepareStatement("delete from FAS_GENERAL_LEDGER_SJV " +
     	   		"where  Accounting_Unit_Id =  "+AccountUnitCode+" and MONTH = "+CashBookMonth+" and Year  = "+CashBookYear+" and SUPPLEMENT_NO="+Supplment_No);
     	   int ss1=ps3.executeUpdate();
     	   if(ss1>0)
     	   {
     		   //connection.commit();
     	   }
        }
        catch (Exception e) {
            System.out.println("error in delete:"+e);
        }
			try{
				String sql="select ACCOUNTING_FOR_OFFICE_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_UNIT_ID="+AccountUnitCode;
				System.out.println("sql:::"+sql);
				PreparedStatement ps2=connection.prepareStatement(sql);
				rs2=ps2.executeQuery();
				while(rs2.next())
				{
					offCount++;
					 CallableStatement cs2 = null;
				        try {
				            cs2 =
				 connection.prepareCall(" call FAS_GL_SJV_POSTING_NEW ( ?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric,?::numeric ) ");
				            cs2.setInt(1, AccountUnitCode);
				            System.out.println("offcode:::"+rs2.getInt("ACCOUNTING_FOR_OFFICE_ID"));
				            cs2.setInt(2, rs2.getInt("ACCOUNTING_FOR_OFFICE_ID"));
				            cs2.setInt(3, CashBookYear);
				            cs2.setInt(4, CashBookMonth);
				            cs2.setString(5, userid);
				            cs2.setInt(6, Supplment_No);
				            cs2.registerOutParameter(7, java.sql.Types.NUMERIC);
				            cs2.setInt(7, 0);
				            cs2.execute();
				            //int errcode = cs2.getInt(7);
				            int errcode = cs2.getBigDecimal(7).intValue();

				            System.out.println("Error Code -gl-->" + errcode);

				            if (errcode != 0) {
				            	System.out.println("error:::::::");
				                msg = "General Ledger Posting for SJV  Failed ";
				                msg = msg + "<br><br>";
				                sendMessage(response, msg, "ok");
				                return;
				            }

				        } catch (Exception e) {
				            System.out.println(e);
				        }
					
				}
			}
			catch (Exception e) {
	            System.out.println("exception be4 ::"+e);
	        }
        
        
        /**
         * Calling TB Generation Procedure
         */

        try {
            cs = connection.prepareCall("call FAS_SUPPLEMENT_TB_GEN_NEW(?::numeric,?::numeric,?::numeric,?,?::numeric,?::numeric)");
            //cs = connection.prepareCall("{call FAS_SUPPLEMENT_TB_GENERATION(?,?,?,?,?,?)}");
            cs.setInt(1, AccountUnitCode);
            cs.setInt(2, CashBookYear);
            cs.setInt(3, CashBookMonth);
            cs.setString(4, userid);
            cs.setInt(5, Supplment_No);
            cs.registerOutParameter(6, java.sql.Types.NUMERIC);
            cs.setNull(6, java.sql.Types.NUMERIC);
            cs.execute();
            //int errcode = cs.getInt(6);
            int errcode = cs.getBigDecimal(6).intValue();      


            System.out.println("Error Code--finish in TB->" + errcode);

            if (errcode != 0) {
                sendMessage(response,
                            " Supplement Trial Balance Generation Failed ",
                            "ok");
                return;
            } else {

               

                System.out.println("here after PDF");
                System.out.println("after send message");
                msg = "Supplement Trial Balance Generated Successfully ";
                msg = msg + "<br><br>";
                sendMessage(response, msg, "ok");
                return;
//                sendMessage(response,
//                            " Supplement Trial Balance Generated Successfully ",
//                            "ok");

            }


        } catch (Exception e) {
            System.out.println("Exception in Main:yes::" + e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println("catch:" + e1);
            }
            msg = "Trial Balance Has failed to Update";
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");
            return;
        }
        
        
    }

/*    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
        	System.out.println("*************");
            String url =
                "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
                bType;
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println("ERROR");
        }
        return;
    }*/
    
    private void sendMessage(HttpServletResponse response, String msg,
            String bType) {
try {
	System.out.println(msg);
String url =
"org/Library/jsps/Messenger.jsp?message=" + msg +
"&button=" + bType;
response.sendRedirect(url);

} catch (IOException e) {
System.out.println("error in send message");
}
return;
}


}
