package Servlets.FAS.FAS1.FundReceiptSystem.servlets;

import Servlets.Security.classes.UserProfile;

import java.io.IOException;
import java.io.PrintWriter;

import java.math.BigDecimal;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.sql.SQLException;
import java.sql.Timestamp;

import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Auto_Fund_Receipt_Create_atOfficeServ extends HttpServlet {
	
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    
    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
     	
    	/**
    	 * Set Content Type 
    	 */
    	response.setContentType(CONTENT_TYPE);
                      
                      
        /**
         * Session Checking               
         */
        try
        {
            HttpSession session=request.getSession(false);
            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp");
                response.sendRedirect(request.getContextPath()+"/index.jsp");
                return;
            }
            System.out.println(session);
                
        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        }
        
        
        /**
         * Variables Declaration 
         */
        Connection con=null;
        ResultSet rs=null,rs4=null,rs5=null;        
        PreparedStatement ps=null,ps4=null,ps5=null;
        String xml="";
        String strCommand="";
        Calendar c1;
        int next_one=0;
        String trfDate_string="";
        
        /**
         * Database Connection 
         */
           try {
                                ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                                String ConnectionString="";

                                String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                                String strdsn=rs1.getString("Config.DSN");
                                String strhostname=rs1.getString("Config.HOST_NAME");
                                String strportno=rs1.getString("Config.PORT_NUMBER");
                                String strsid=rs1.getString("Config.SID");
                                String strdbusername=rs1.getString("Config.USER_NAME");
                                String strdbpassword=rs1.getString("Config.PASSWORD");
                                ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                                Class.forName(strDriver.trim());
                                con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
               }
               catch(Exception e)
               {
                  System.out.println("Exception in opening connection :"+e);
               }
        
               /** 
                * 
                */
               response.setHeader("Cache-Control","no-cache");
               PrintWriter out = response.getWriter();
        
               
               
               /**
                * Get Command Parameter 
                */
               try 
               {
            	   strCommand=request.getParameter("Command");
            	   System.out.println("assign..here command***************..."+strCommand);
               }               
               catch(Exception e) 
               {
            	   System.out.println("Exception in assigning..."+e);
               }
	               
               
               /**
                * Variables Declaration  
                */
	           int cmbAcc_UnitCode=0;
	           int cmbOffice_code=0;
	           
	           /* Get Accounting Unit Code */
	           try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	           catch(NumberFormatException e){System.out.println("exception"+e );}
	           System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
	           
	           /* Get Accounting for Office ID */
	           try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	           catch(NumberFormatException e){System.out.println("exception"+e );}
	           System.out.println("cmbOffice_code "+cmbOffice_code);
	           
        
        if(strCommand.equalsIgnoreCase("searchByMonth")) 
        {
             String CONTENT_TYPE = "text/xml; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
             /* Get Cashbook Year */
             int year=Integer.parseInt(request.getParameter("txtCB_Year"));
             System.out.println("the year is"+year);
             
             /* Get Cashbook Month */
             int month=Integer.parseInt(request.getParameter("txtCB_Month"));
             System.out.println("the month is"+month);
             xml="<response><command>searchByMonth</command>";
             
                  try {
                            //operMode added
                	  
                    /* 
                     * 
                     * 
                     * joe change on 09/05/2014
                     * 
                     * 
                     * 
                     * 
                     * String  sql="SELECT fund_type, " +
                        "  TO_CHAR(date_of_transfer,'DD/MM/YYYY') AS date_of_transfer, " +
                        "  voucher_no, " +
                        "  sl_no, " +
                        "  ( " +
                        "  CASE " +
                        "    WHEN ho_ref_no IS NULL " +
                        "    THEN '-' " +
                        "    ELSE ho_ref_no " +
                        "  END ) AS horef_no, " +
                        "  ho_ref_date, " +
                        "  cheque_dd_no, " +
                        "  cheque_dd_date,operMode, " +
                        "  trim(TO_CHAR(amount,'99999999999999.99')) AS amount, " +
                        "  particulars, " +
                        "  SUBSTR(ACCOUNT_HEAD_CODE, 1, 4) AS account_head_code, " +
                        "  office_bank_id, " +
                        "  bank_name, " +
                        "  office_branch_id, " +
                        "  office_account_no, " +
                        "  trim(TO_CHAR(total_amount,'99999999999999.99')) AS total_amount, " +
                        "  ho_bank_id, " +
                        "  ho_branch_id, " +
                        "  ho_account_no, " +
                        "  cheque_or_dd " +
                        "FROM " +
                        "  (SELECT accounting_unit_id, " +
                        "    accounting_for_office_id, " +
                        "    voucher_no, " +
                        "    date_of_transfer, " +
                        "    trim(TO_CHAR(total_amount,'99999999999999.99')) AS total_amount, " +
                        "    cashbook_year, " +
                        "    cashbook_month, " +
                        "    transfer_status, " +
                        "    ho_ref_no, " +
                        "    ho_ref_date, " +
                        "    ho_bank_id, " +
                        "    ho_branch_id, " +
                        "    ho_account_no " +
                        "  FROM fas_fund_trf_from_ho_master " +
                        "  )a " +
                        "INNER JOIN " +
                        "  (SELECT accounting_unit_id, " +
                        "    accounting_for_office_id, " +
                        "    transfer_to_office_id, " +
                        "    voucher_no AS vouch_no, " +
                        "    account_head_code, " +
                        "    cashbook_year, " +
                        "    cashbook_month, " +
                        "    cheque_dd_no, " +
                        "    cheque_dd_date, " +
                        "    trim(TO_CHAR(amount,'99999999999999.99')) AS amount, " +
                        "    particulars, " +
                        "    fund_type, " +
                        "    auto_status, " +
                        "    sl_no, " +
                        "    office_bank_id, " +
                        "    (SELECT BANK_SHORT_NAME FROM FAS_MST_BANKS WHERE BANK_ID = office_bank_id " +
                        "    ) AS bank_name , " +
                        "    office_branch_id, " +
                        "    office_account_no, " +
                       " (select AC_OPERATIONAL_MODE_ID from FAS_MST_BANK_BALANCE where BANK_ID = office_bank_id  and BRANCH_ID=office_branch_id and BANK_AC_NO=office_account_no and status='Y' and ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+") as operMode, "+
                        "    cheque_or_dd, " +
                        "    TRANSFERED_TO_HO_UNIT_ID,VERIFY " +
                        "  FROM fas_fund_trf_from_ho_trn " +
                        "  )b " +
                        "ON a.accounting_unit_id       =b.accounting_unit_id " +
                        "AND a.accounting_for_office_id=b.accounting_for_office_id " +
                        "AND a.voucher_no              =b.vouch_no " +
                        "AND a.cashbook_year           =b.cashbook_year " +
                        "AND a.cashbook_month          =b.cashbook_month " +
                        "WHERE a.cashbook_month        =" +month+ " " +
                        "AND a.cashbook_year           =" +year+  " " +
                        "AND b.transfer_to_office_id   =  " +cmbOffice_code+ " " +
                        "AND a.transfer_status         ='L' " +
                        "AND b.auto_status            IS NULL and b.VERIFY='Y'" ;   */
                        
                	  
                	  String  sql="SELECT fund_type, " +
                      "  date_of_transfer AS date_of_transfer, " +
                      "  voucher_no, " +
                      "  sl_no, " +
                      "  ( " +
                      "  CASE " +
                      "    WHEN ho_ref_no IS NULL " +
                      "    THEN '-' " +
                      "    ELSE ho_ref_no " +
                      "  END ) AS horef_no, " +
                      "  ho_ref_date, " +
                      "  cheque_dd_no, " +
                      "  cheque_dd_date,operMode, " +
                      "  trim(TO_CHAR(amount::numeric,'99999999999999.99')) AS amount, " +
                      "  particulars, " +
                     // "  SUBSTR(ACCOUNT_HEAD_CODE, 1, 4) AS account_head_code, " +
                     " ACCOUNT_HEAD_CODE as ACCOUNT_HEAD_CODE_cp,"+
                     " CASE "+
                    " WHEN (operMode='OPR-NRDWP-Main' "+
                    	  "  OR operMode  ='OPR-NRDWP-Support') and operMode is not null "+
                      " THEN "+
                       "  (SELECT  SUBSTR(c.AC_HEAD_CODE::varchar, 1, 4) "+
                       "  FROM FAS_OFFICE_BANK_AC_CURRENT c"+
                        " WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
                        " AND c.BANK_ID           =office_bank_id and  c.BRANCH_ID=office_branch_id and c.CR_DR_TYPE='DR' and C.MODULE_ID='MF009' AND C.AC_OPERATIONAL_MODE_ID=operMode "+
                       " and c.BANK_AC_NO=office_account_no    limit 1 "+
                        " ) "+
                      "  WHEN (operMode!='OPR-NRDWP-Main' "+
                         "   AND operMode !='OPR-NRDWP-Support') or operMode is null "+
                       "  then SUBSTR(ACCOUNT_HEAD_CODE::varchar, 1, 4) end AS account_head_code, "+
                      "  office_bank_id, " +
                      "  bank_name, " +
                      "  office_branch_id, " +
                      "  office_account_no, " +
                      "  trim(TO_CHAR(total_amount::numeric,'99999999999999.99')) AS total_amount, " +
                      "  ho_bank_id, " +
                      "  ho_branch_id, " +
                      "  ho_account_no, " +
                      "  cheque_or_dd " +
                      "FROM " +
                      "  (SELECT accounting_unit_id, " +
                      "    accounting_for_office_id, " +
                      "    voucher_no, " +
                      "    date_of_transfer, " +
                      "    trim(TO_CHAR(total_amount::numeric,'99999999999999.99')) AS total_amount, " +
                      "    cashbook_year, " +
                      "    cashbook_month, " +
                      "    transfer_status, " +
                      "    ho_ref_no, " +
                      "    ho_ref_date, " +
                      "    ho_bank_id, " +
                      "    ho_branch_id, " +
                      "    ho_account_no " +
                      "  FROM fas_fund_trf_from_ho_master " +
                      "  )a " +
                      "INNER JOIN " +
                      "  (SELECT accounting_unit_id, " +
                      "    accounting_for_office_id, " +
                      "    transfer_to_office_id, " +
                      "    voucher_no AS vouch_no, " +
                      "    account_head_code, " +
                      "    cashbook_year, " +
                      "    cashbook_month, " +
                      "    cheque_dd_no, " +
                      "    cheque_dd_date, " +
                      "    trim(TO_CHAR(amount::numeric,'99999999999999.99')) AS amount, " +
                      "    particulars, " +
                      "    fund_type, " +
                      "    auto_status, " +
                      "    sl_no, " +
                      "    office_bank_id, " +
                      "    (SELECT BANK_SHORT_NAME FROM FAS_MST_BANKS WHERE BANK_ID = office_bank_id " +
                      "    ) AS bank_name , " +
                      "    office_branch_id, " +
                      "    office_account_no, " +
                     " (select AC_OPERATIONAL_MODE_ID from FAS_MST_BANK_BALANCE where BANK_ID = office_bank_id  and BRANCH_ID=office_branch_id and BANK_AC_NO=office_account_no and status='Y' and ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+") as operMode, "+
                      "    cheque_or_dd, " +
                      "    TRANSFERED_TO_HO_UNIT_ID,VERIFY " +
                      "  FROM fas_fund_trf_from_ho_trn " +
                      "  )b " +
                      "ON a.accounting_unit_id       =b.accounting_unit_id " +
                      "AND a.accounting_for_office_id=b.accounting_for_office_id " +
                      "AND a.voucher_no              =b.vouch_no " +
                      "AND a.cashbook_year           =b.cashbook_year " +
                      "AND a.cashbook_month          =b.cashbook_month " +
                      "WHERE a.cashbook_month        =" +month+ " " +
                      "AND a.cashbook_year           =" +year+  " " +
                      "AND b.transfer_to_office_id   =  " +cmbOffice_code+ " " +
                      "AND a.transfer_status         ='L' " +
                      "AND b.auto_status            IS NULL and b.VERIFY='Y'" ;   
                        
                        if( cmbOffice_code == 5000 ) 
                        {
                           sql=sql+" and b.TRANSFERED_TO_HO_UNIT_ID="+cmbAcc_UnitCode;
                        }
                        
                        
                             System.out.println(sql);
                             
                           ps=con.prepareStatement(sql);
                             
                             
                             System.out.println("i am Here 1");     
                             rs=ps.executeQuery();
                            
                            int count=0;
                            while(rs.next())
                            {
                            	String amt=rs.getString("amount");
                            	int vno=rs.getInt("voucher_no");
                            	int serno=rs.getInt("sl_no");
                            	trfDate_string=rs.getString("date_of_transfer");
                            	 java.util.Date d1=null; 
                                 String sd1[]=null;
                                 Date trfDate=null;
                            	try
                                {
                                	sd1=trfDate_string.split("-");
                                	 c1=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
                                	d1=c1.getTime();
                                	trfDate=new Date(d1.getTime());
                                	System.out.println("trfDate "+trfDate);
                                }
                                catch(Exception e)
                                {
                                	System.out.println("Error Getting trfDate_string -->"+e);
                                }
                            	
                            	try{
                            	String nsql="Select Trf_Voucher_No,TRF_SL_NO,Total_Amount From Fas_Fund_Receipt_By_Office Where "+
								"	Accounting_For_Office_Id="+cmbOffice_code+" And Total_Amount="+amt+" And Trf_Voucher_No="+vno+" And Trf_Voucher_Date=? "+ 
								"	and TRF_CB_YEAR="+year+" and Trf_Cb_Month="+month+" and Trf_Sl_No="+serno+" and RECEIPT_STATUS='L'";
                            	System.out.println("rssssssssss"+nsql);
                            	
                            	ps4=con.prepareStatement(nsql);
                            	ps4.setDate(1,trfDate);
                            	rs4=ps4.executeQuery();
                            	if(rs4.next())
                            	{
                            		System.out.println("record already exist in receipt table");
                            	}
                            	else{
                            		  count++;
                                String dat_of_trans="";
                                String fund_type="";
                                String ho_ref_date="";
                                String cheque_dd_no="";
                                String cheque_dd_date="";
                                String particulars="";
                                String ho_ref_no="";
                                int voucher_no=0;
                                int sl_no=0;
                                String account_head_code="";
                                int office_bank_id=0;
                                int office_branch_id=0;
                                long office_account_no=0;
                                BigDecimal total_amount;
                                int ho_bank_id=0;
                                int ho_branch_id=0;
                                String ho_account_no="";
                                String cheque_or_dd="";
                                String bank_name="";
                                String total_amount1="",operM="";
                                BigDecimal amount1;
                           System.out.println("i am Here*********");     
                                
                                if(rs.getString("date_of_transfer")!=null)
                                {
                                   // dat_of_trans=new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("date_of_transfer"));
                                   // System.out.println("date of transfer :"+rs.getDate("date_of_transfer"));
                                      dat_of_trans=rs.getString("date_of_transfer");
                                }
                                else
                                {
                                    dat_of_trans="-";
                                }
                                
                                
                                
                                if(rs.getString("fund_type")!=null)
                                {
                                    fund_type=rs.getString("fund_type");
                                    System.out.println("the fund type is"+fund_type);
                                }
                                else
                                {
                                    fund_type="-";
                                }
                                
                                
                                
                                ho_ref_no=rs.getString("horef_no");
                                System.out.println("the ho ref number is::::"+ho_ref_no);
                                
                                
                                
                                account_head_code=rs.getString("account_head_code");
                                System.out.println("the account head code"+account_head_code);
                                
                                
                                
                                office_bank_id=rs.getInt("office_bank_id");
                                System.out.println("the office bank id"+office_bank_id);
                                
                                
                                bank_name=rs.getString("bank_name");
                                System.out.println("the office bank name==>"+bank_name);
                                
                                
                                
                                office_branch_id=rs.getInt("office_branch_id");
                                System.out.println("the office branch id"+office_branch_id);
                                
                                
                                office_account_no=rs.getLong("office_account_no");
                                System.out.println("the office account no"+office_account_no);
                                
                                
                                
                                total_amount=rs.getBigDecimal("total_amount");
                                //total_amount1=rs.getString("total_amount");
                                System.out.println("the total amount"+total_amount);
                                
                                
                                
                                ho_bank_id=rs.getInt("ho_bank_id");
                                System.out.println("the bank id is"+ho_bank_id);
                                
                                
                                ho_branch_id=rs.getInt("ho_branch_id");
                                System.out.println("the branch id"+ho_branch_id);
                                
                                
                                ho_account_no=rs.getString("ho_account_no");
                                System.out.println("the account no is"+ho_account_no);
                                
                                
                                System.out.println("the refno is"+ho_ref_no);
                                
                                
                                if(rs.getDate("ho_ref_date")!=null)
                                {
                                    ho_ref_date=new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("ho_ref_date"));
                                    System.out.println("date of reference :"+rs.getDate("ho_ref_date"));
                                }
                                else {
                                    ho_ref_date="-";
                                }
                                
                                
                                
                                if(rs.getString("cheque_dd_no")!=null)
                                {
                                    cheque_dd_no=rs.getString("cheque_dd_no");
                                    System.out.println("the cheque dd number is"+cheque_dd_no);
                                }
                                else
                                {
                                    cheque_dd_no="-";
                                }
                                
                                
                                
                                if(rs.getString("cheque_or_dd")!=null)
                                {
                                    cheque_or_dd=rs.getString("cheque_or_dd");
                                    System.out.println("the cheque_or_dd is"+cheque_or_dd);
                                }
                                else
                                {
                                    cheque_or_dd="-";
                                }
                                
                                
                                
                                
                                if(rs.getDate("cheque_dd_date")!=null)
                                {
                                    cheque_dd_date=new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("cheque_dd_date"));
                                    System.out.println("date of transfer :"+rs.getDate("cheque_dd_date"));
                                }
                                else
                                {
                                    cheque_dd_date="-";
                                }
                                
                                
                                
                                BigDecimal amount;
                                
                               // amount=rs.getFloat("amount");
                                amount1=rs.getBigDecimal("amount");
                                
                                
                                if(rs.getString("particulars")!=null)
                                {
                                    particulars=rs.getString("particulars");
                                    System.out.println("the particulars is"+particulars);
                                }
                                else
                                {
                                	particulars="-";
                                }
                                
                                operM=rs.getString("operMode");
                                
                                System.out.println("amount is"+amount1);
                                
                                voucher_no=rs.getInt("voucher_no");
                                System.out.println("the voucher number is"+voucher_no);
                                
                                sl_no=rs.getInt("sl_no");
                                System.out.println("the serial number is"+sl_no);
                                xml=xml+"<details>";
                                xml=xml+"<fund_type>"+fund_type+"</fund_type><dat_of_trans>"+dat_of_trans+"</dat_of_trans><ho_ref_no>"+ho_ref_no+"</ho_ref_no>" +
                                "<ho_ref_date>"+ho_ref_date+"</ho_ref_date><cheque_dd_no>"+cheque_dd_no+"</cheque_dd_no><cheque_dd_date>"+cheque_dd_date+"</cheque_dd_date>" +
                                "<amount>"+amount1+"</amount>" +
                                "<particulars>"+particulars+"</particulars><voucher_no>"+voucher_no+"</voucher_no><sl_no>"+sl_no+"</sl_no><account_head_code>"+account_head_code+"</account_head_code><operMode>"+operM+"</operMode><office_bank_id>"+office_bank_id+"</office_bank_id><office_branch_id>"+office_branch_id+"</office_branch_id><office_account_no>"+office_account_no+"</office_account_no><total_amount>"+total_amount+"</total_amount><ho_bank_id>"+ho_bank_id+"</ho_bank_id><ho_branch_id>"+ho_branch_id+"</ho_branch_id><ho_account_no>"+ho_account_no+"</ho_account_no><cheque_or_dd>"+cheque_or_dd+"</cheque_or_dd>";
                                try{
                                	String s2="select AC_HEAD_CODE,BANK_AC_NO from FAS_OFFICE_BANK_AC_CURRENT " +
                                			"where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and AC_OPERATIONAL_MODE_ID='FDW' " +
                                			"and MODULE_ID='MF009'";
                                	ps5=con.prepareStatement(s2);
                                	
                                	rs5=ps5.executeQuery();
                                	while(rs5.next())
                                	{
                                		next_one++;
                                		 xml=xml+"<another_dr_head>"+rs5.getInt("AC_HEAD_CODE")+"</another_dr_head>";	
                                		 xml=xml+"<office_another_bank>"+rs5.getLong("BANK_AC_NO")+"</office_another_bank>";
                                	}
                                	if(next_one==0)
                                	{
                                		 xml=xml+"<another_dr_head>"+0+"</another_dr_head>";	
                                		 xml=xml+"<office_another_bank>"+0+"</office_another_bank>";
                                	}
                                }
                                catch(Exception ee)
                                {
                                	System.out.println("ee::"+ee);
                                }
                                xml=xml+"</details>";
                               // count++;
                            }
                            	}
                            	catch(Exception eee)
                            	{
                            	System.out.println("ee:::"+eee);	
                            	}
                            }
                            if(count==0)
                                xml=xml+"<flag>failure</flag>";
                            else 
                                xml=xml+"<flag>success</flag>";
                        System.out.println("count  "+count);
                        ps.close();
                        rs.close();
                        }
                        catch(Exception e)
                        {
                        	 System.out.println(e);
                        e.printStackTrace();
                       
                        xml=xml+"<flag>failure</flag>";
                        }
    }
        
              
        
        
        
    
    else if(strCommand.equalsIgnoreCase("LoadUnitWise_Office")) 
    {
    	
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        xml="<response><command>"+strCommand+"</command>";
        
        
        try{
         cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
         System.out.println("cmbAcc_UnitCode********"+cmbAcc_UnitCode);
        }
        catch(Exception e){System.out.println("Exception to catch account head ");}
         
         
         
        try {
                ps=con.prepareStatement("select ACCOUNTING_FOR_OFFICE_ID,b.OFFICE_NAME from FAS_MST_ACCT_UNIT_OFFICES a," +
                "COM_MST_OFFICES b where a.ACCOUNTING_FOR_OFFICE_ID=b.OFFICE_ID and a.ACCOUNTING_UNIT_ID=? ");
                ps.setInt(1,cmbAcc_UnitCode);
                rs=ps.executeQuery();
                int cnt=0;
                
                while(rs.next())
                {
                    xml=xml+"<offid>"+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+"</offid>";
                    xml=xml+"<offname>"+rs.getString("OFFICE_NAME")+"</offname>";
                    cnt++;
                }
                if(cnt!=0)
                    xml=xml+"<flag>success</flag>";
                else
                    xml=xml+"<flag>failure</flag>";
            }
           catch(Exception e)
           {
           System.out.println("catch..HERE.in load head code."+e);
           xml=xml+"<flag>failure</flag>";
           }
    }
    
        
    
   /**
    * Search By Date      
    */        
   else  if(strCommand.equalsIgnoreCase("searchByDate")) 
    {
	     /* Set Content Type */
         String CONTENT_TYPE = "text/xml; charset=windows-1252";
         response.setContentType(CONTENT_TYPE);
         
         /* Variables Declaration */         
         Calendar c;
         
         /* xml Declaration */
         xml="<response><command>searchByDate</command>";
          
         
                try {
                	    /* Variables Declaration */   
                	    Date txtFrom_date=null;
                        Date txtTo_date=null;
                        java.util.Date d=null; 
                        String sd[]=null;
                        int year=0;
                        int month=0;
                        
                        /* Get From Date */
                        try
                        {
                        	sd=request.getParameter("txtFrom_date").split("/");
                        	c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                        	d=c.getTime();
                        	txtFrom_date=new Date(d.getTime());
                        	System.out.println("from_date "+txtFrom_date);
                        }
                        catch(Exception e)
                        {
                        	System.out.println("Error Getting from Date -->"+e);
                        }
                        
                        
                        /* Get To Date */
                        try
                        {
                        	sd=request.getParameter("txtTo_date").split("/");
                        	c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                        	d=c.getTime();
                        	txtTo_date=new Date(d.getTime());
                        	System.out.println("txtTo_date "+txtTo_date);
                        }
                        catch(Exception e)
                        {
                        	System.out.println("Error Getting from Date -->"+e);
                        }
                        
                        /* Get Year */
                        try
                        {
                        	year=Integer.parseInt(request.getParameter("txtCB_Year"));
                        	System.out.println("the year is"+year);
                        }
                        catch(Exception e )
                        {
                           System.out.println("Error get Year -->"+e);
                        }
                        
                        /* Get Month */
                        try
                        {
                        	month=Integer.parseInt(request.getParameter("txtCB_Month"));
                        	System.out.println("the month is"+month);
                        }
                        catch(Exception e)
                        {
                        	System.out.println("Error Get Month --> "+e);
                        }
                        
                        
                        /* SQL Query */
                        String sql="" +
                        " select 																	\n" + 
                        "   fund_type,																\n" + 
                        "   date_of_transfer,														\n" + 
                        "   voucher_no,																\n" + 
                        "   sl_no,																	\n" + 
                        "   ( case when ho_ref_no is null then '-' else ho_ref_no end) as horef_no,	\n" + 
                        "   ho_ref_date,   															\n" + 
                        "   cheque_dd_no,															\n" + 
                        "   cheque_dd_date,															\n" + 
                        "   trim(to_char(amount,'99999999999999.99')) as amount,  	 																	\n" + 
                        "   particulars,  															\n" + 
                        "   SUBSTR(ACCOUNT_HEAD_CODE::varchar, 1, 4) as account_head_code,					\n" + 
                        "   office_bank_id,															\n" + 
                        "   office_branch_id,														\n" + 
                        "   office_account_no,   													\n" + 
                        "   total_amount,operMode,															\n" + 
                        "   ho_bank_id,																\n" + 
                        "   ho_branch_id,															\n" + 
                        "   ho_account_no,															\n" + 
                        "   cheque_or_dd 															\n" + 
                        "from 																		\n" + 
                        "   (   \n" + 
                        "       select\n" + 
                        "           accounting_unit_id,\n" + 
                        "           accounting_for_office_id,\n" + 
                        "           voucher_no,\n" + 
                        "           date_of_transfer,\n" + 
                        "           total_amount,     \n" + 
                        "           cashbook_year,\n" + 
                        "           cashbook_month,\n" + 
                        "           transfer_status,\n" + 
                        "           ho_ref_no,\n" + 
                        "           ho_ref_date,   \n" + 
                        "           ho_bank_id,\n" + 
                        "           ho_branch_id,\n" + 
                        "           ho_account_no \n" + 
                        "       from \n" + 
                        "           fas_fund_trf_from_ho_master      \n" + 
                        "    )a   \n" + 
                        "  \n" + 
                        "    inner join   \n" + 
                        "  \n" + 
                        "    (   \n" + 
                        "       select \n" + 
                        "          accounting_unit_id,\n" + 
                        "          accounting_for_office_id,\n" + 
                        "          transfer_to_office_id,\n" + 
                        "          voucher_no as vouch_no,\n" + 
                        "          account_head_code,     \n" + 
                        "          cashbook_year,\n" + 
                        "          cashbook_month,\n" + 
                        "          cheque_dd_no,\n" + 
                        "          cheque_dd_date,\n" + 
                        "          amount,\n" + 
                        "          particulars,\n" + 
                        "          fund_type,\n" + 
                        "          auto_status,\n" + 
                        "          sl_no,   \n" + 
                        "          office_bank_id,\n" + 
                        "          office_branch_id,\n" + 
                        "          office_account_no,\n" + 
                        " (select AC_OPERATIONAL_MODE_ID from FAS_MST_BANK_BALANCE where BANK_ID = office_bank_id  and BRANCH_ID=office_branch_id and BANK_AC_NO=office_account_no and status='Y'  and ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" ) as operMode, "+
                        "          cheque_or_dd\n , TRANSFERED_TO_HO_UNIT_ID " + 
                        "       from      \n" + 
                        "          fas_fund_trf_from_ho_trn     \n" + 
                        "    )b   \n" + 
                        "    \n" + 
                        " on a.accounting_unit_id = b.accounting_unit_id \n" + 
                        "and a.accounting_for_office_id = b.accounting_for_office_id     \n" + 
                        "and a.voucher_no = b.vouch_no \n" + 
                        "and a.cashbook_year = b.cashbook_year \n" + 
                        "and a.cashbook_month = b.cashbook_month  \n" + 
                        "where \n" + 
                        "    b.transfer_to_office_id = ? \n" + 
                        "and a.date_of_transfer between ? and ? \n"+ 
                        "and a.transfer_status = 'L' \n" + 
                        "and b.auto_status is null  \n" + 
                        "and a.cashbook_month = ? \n" + 
                        "and a.cashbook_year = ? " ; 
                        
                        if(cmbOffice_code == 5000) 
                        {
                           sql=sql+"and b.TRANSFERED_TO_HO_UNIT_ID="+cmbAcc_UnitCode;
                        }
                        
                        ps=con.prepareStatement(sql);

                        ps.setInt(1,cmbOffice_code);                        
                        ps.setDate(2,txtFrom_date);
                        ps.setDate(3,txtTo_date);
                        ps.setInt(4,month);
                        ps.setInt(5,year);
                        rs=ps.executeQuery();
                       //System.out.println("sqlsqlsql -----"+sql);
                        int count=0;
                        while(rs.next())
                        {
                            String dat_of_trans="";
                            String fund_type="";
                            String ho_ref_date="";
                            String cheque_dd_no="";
                            String cheque_dd_date="";
                            String particulars="";
                            String ho_ref_no="";
                            int voucher_no=0;
                            int sl_no=0;
                            int account_head_code=0;
                            int office_bank_id=0;
                            int office_branch_id=0;
                            long office_account_no=0;
                            BigDecimal total_amount;
                            int ho_bank_id=0;
                            int ho_branch_id=0;
                           // int ho_account_no=0;
                            String cheque_or_dd="",operMode="",ho_account_no="";
                            
                            if(rs.getDate("date_of_transfer")!=null)
                            {
                                dat_of_trans=new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("date_of_transfer"));
                                System.out.println("date of transfer :"+rs.getDate("date_of_transfer"));
                            }
                            else
                            {
                                dat_of_trans="-";
                            }
                            if(rs.getString("fund_type")!=null)
                            {
                                fund_type=rs.getString("fund_type");
                                System.out.println("the fund type is"+fund_type);
                            }
                            else
                            {
                                fund_type="-";
                            }
                            
                            ho_ref_no=rs.getString("horef_no");
                            System.out.println("the ho ref number is"+ho_ref_no);
                            
                            account_head_code=rs.getInt("account_head_code");
                            System.out.println("the account head code"+account_head_code);
                            
                            office_bank_id=rs.getInt("office_bank_id");
                            System.out.println("the office bank id"+office_bank_id);
                            
                            office_branch_id=rs.getInt("office_branch_id");
                            System.out.println("the office branch id"+office_branch_id);
                            
                            office_account_no=rs.getLong("office_account_no");
                            System.out.println("the office account no"+office_account_no);
                            
                            total_amount=rs.getBigDecimal("total_amount");
                            System.out.println("the total amount"+total_amount);
                            
                            ho_bank_id=rs.getInt("ho_bank_id");
                            System.out.println("the bank id is"+ho_bank_id);
                            
                            ho_branch_id=rs.getInt("ho_branch_id");
                            System.out.println("the branch id"+ho_branch_id);
                            
                           // ho_account_no=rs.getInt("ho_account_no");
                            ho_account_no=rs.getString("ho_account_no");
                            System.out.println("the account no is"+ho_account_no);
                            
                            
                            System.out.println("the refno is_________"+ho_ref_no);
                            
                            
                            if(rs.getDate("ho_ref_date")!=null)
                            {
                                ho_ref_date=new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("ho_ref_date"));
                                System.out.println("date of reference :"+rs.getDate("ho_ref_date"));
                            }
                            else {
                                ho_ref_date="-";
                            }
                            
                            if(rs.getString("cheque_dd_no")!=null)
                            {
                                cheque_dd_no=rs.getString("cheque_dd_no");
                                System.out.println("the cheque dd number is"+cheque_dd_no);
                            }
                            else
                            {
                                cheque_dd_no="-";
                            }
                            
                            if(rs.getString("cheque_or_dd")!=null)
                            {
                                cheque_or_dd=rs.getString("cheque_or_dd");
                                System.out.println("the cheque_or_dd is"+cheque_or_dd);
                            }
                            else
                            {
                                cheque_or_dd="-";
                            }
                            
                            
                            if(rs.getDate("cheque_dd_date")!=null)
                            {
                                cheque_dd_date=new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("cheque_dd_date"));
                                System.out.println("date of transfer :"+rs.getDate("cheque_dd_date"));
                            }
                            else
                            {
                                cheque_dd_date="-";
                            }
                            
                            BigDecimal amount;
                            amount=rs.getBigDecimal("amount");
                            
                            if(rs.getString("particulars")!=null)
                            {
                                particulars=rs.getString("particulars");
                                System.out.println("the particulars is"+particulars);
                            }
                            else
                            {
                                fund_type="";
                            }
                            operMode=rs.getString("operMode");
                            System.out.println("amount is"+amount);
                            
                            voucher_no=rs.getInt("voucher_no");
                            System.out.println("the voucher number is"+voucher_no);
                            
                            sl_no=rs.getInt("sl_no");
                            System.out.println("the serial number is"+sl_no);
                            xml=xml+"<details>";
                            xml=xml+"<fund_type>"+fund_type+"</fund_type><dat_of_trans>"+dat_of_trans+"</dat_of_trans><ho_ref_no>"+ho_ref_no+"</ho_ref_no>" +
                            "<ho_ref_date>"+ho_ref_date+"</ho_ref_date><cheque_dd_no>"+cheque_dd_no+"</cheque_dd_no><cheque_dd_date>"+cheque_dd_date+"</cheque_dd_date>" +
                            "<amount>"+amount+"</amount><particulars>"+particulars+"</particulars><voucher_no>"+voucher_no+"</voucher_no><sl_no>"+sl_no+"</sl_no>" +
                            "<account_head_code>"+account_head_code+"</account_head_code><office_bank_id>"+office_bank_id+"</office_bank_id>" +
                            "<office_branch_id>"+office_branch_id+"</office_branch_id><office_account_no>"+office_account_no+"</office_account_no>" +
                            "<total_amount>"+total_amount+"</total_amount><ho_bank_id>"+ho_bank_id+"</ho_bank_id><ho_branch_id>"+ho_branch_id+"</ho_branch_id>" +
                            "<ho_account_no>"+ho_account_no+"</ho_account_no><cheque_or_dd>"+cheque_or_dd+"</cheque_or_dd><operMode>"+operMode+"</operMode>";
                            
                            try{
                            	String s2="select AC_HEAD_CODE,BANK_AC_NO from FAS_OFFICE_BANK_AC_CURRENT " +
                            			"where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and AC_OPERATIONAL_MODE_ID='FDW' " +
                            			"and MODULE_ID='MF009'";
                            	ps5=con.prepareStatement(s2);
                            	
                            	rs5=ps5.executeQuery();
                            	while(rs5.next())
                            	{
                            		next_one++;
                            		 xml=xml+"<another_dr_head>"+rs5.getInt("AC_HEAD_CODE")+"</another_dr_head>";	
                            		 xml=xml+"<office_another_bank>"+rs5.getLong("BANK_AC_NO")+"</office_another_bank>";
                            	}
                            	if(next_one==0)
                            	{
                            		 xml=xml+"<another_dr_head>"+0+"</another_dr_head>";	
                            		 xml=xml+"<office_another_bank>"+0+"</office_another_bank>";
                            	}
                            }
                            catch(Exception ee)
                            {
                            	System.out.println("ee::"+ee);
                            }
                            xml=xml+"</details>";
                            count++;
                        }
                        if(count==0)
                            xml=xml+"<flag>failure</flag>";
                        else 
                            xml=xml+"<flag>success</flag>";                            
                            
                            
                    System.out.println("count  "+count);
                    ps.close();
                    rs.close();
                    }
                    catch(Exception e)
                    {
                    System.out.println("catch..HERE.in load valuesssssssss."+e);
                        xml=xml+"<flag>failure</flag>";
                    }
    }
    xml=xml+"</response>";
    System.out.println(xml);
    out.println(xml);
}


    
    
    
    public void doPost(HttpServletRequest request, 
                       HttpServletResponse response) throws ServletException, IOException {response.setContentType(CONTENT_TYPE);
        
    
        /** Variables Declaration */
        
    
        Connection con = null;              
        Statement st=null;
        ResultSet rs=null;
        int cnt_office=0;
        Calendar c=null;
        Date txtCrea_date=null;
        
        /** Seession Checking */
        HttpSession session = request.getSession(false);
        String updatedby = (String)session.getAttribute("UserId");
        System.out.println("updated by user id is:" + updatedby);
        
        
        java.util.Date dt = new java.util.Date(System.currentTimeMillis());
        java.sql.Timestamp sqldt = new java.sql.Timestamp(dt.getTime());
        
       
        
        /** Get Database Connection */        
        try 
        {
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
        } catch (Exception e) 
        {
            System.out.println("Exception in opening connection :" + e);
            sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }
        
        
        /**
         * Get Parameters          
         */
        
        /** Get Accounting Unit ID */ 
        String accunitid=request.getParameter("cmbAcc_UnitCode");
        
        /** Get Accounting for Office ID */
        String officeid=request.getParameter("cmbOffice_code");
        
        /** Get Cashbook_year */
        String cashbookyear=request.getParameter("txtCB_Year");
        
        /** Get Cashbook_month */
        String cashbookmonth=request.getParameter("txtCB_Month");
        int cashbookyear_ho=0,cashbookmonth_ho=0;
        /** Get Receipt Date */
         String[] sd=request.getParameter("txtCrea_date").split("/");
         cashbookyear_ho=Integer.parseInt(sd[2]);
         cashbookmonth_ho=Integer.parseInt(sd[1]);
         c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
         
         java.util.Date d=c.getTime();
         txtCrea_date=new Date(d.getTime());
         System.out.println("txtCrea_date "+txtCrea_date);
         
        
        
        /** Get Accounting for Office ID */
      //  int login_oid=Integer.parseInt(request.getParameter("login_oid"));        
        
        Date sysdt=null;
        int temp=0;       
        int bank=0;
        int branch=0;
      //  int accountid=0;
       String accountid="";
        double tot_amt=0.00;
        String cheque_dd="";
       
        String ddno="",mode="";
            
        /** Convert From String Data to Integer */
        int year=Integer.parseInt(cashbookyear);        
        int month=Integer.parseInt(cashbookmonth);        
        int accunit=Integer.parseInt(accunitid);        
        int office_id=Integer.parseInt(officeid);          
        
        
        String update_user = (String)session.getAttribute("UserId");
        
        
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
        String acct_head="";
        
        /** Voucher Number */ 
        String voucher_no=request.getParameter("voucher_no");        
        String voucher_no1[]=voucher_no.split(",");        
        int voucher_no2[]=new int [voucher_no1.length];   
        for(int i=0;i<voucher_no1.length;i++)
        {
            voucher_no2[i]=Integer.parseInt(voucher_no1[i]);
            System.out.println("the voucher number is"+voucher_no2[i]);
        }
        
        /** Date of Transfer */
         String date_trans=request.getParameter("date_trans");        
         String date_trans1[]=date_trans.split(","); 
         
         Date date_trans2[]=new Date [date_trans1.length];   
        
         for(int i=0;i<date_trans1.length;i++)
         {
             String[] sd2=date_trans1[i].split("-");
             
             c=new GregorianCalendar(Integer.parseInt(sd2[2]),Integer.parseInt(sd2[1])-1,Integer.parseInt(sd2[0]));
             
             java.util.Date d2=c.getTime();
             date_trans2[i]=new Date(d2.getTime());
         }
        
        
        String sl_no=request.getParameter("sl_no");
        String sl_no1[]=sl_no.split(",");
        int sl_no2[]=new int [sl_no1.length];
        for(int i=0;i<sl_no1.length;i++)
        {
            sl_no2[i]=Integer.parseInt(sl_no1[i]);
            System.out.println("the serial number is"+sl_no2[i]);
        }
        
        String chcksel=request.getParameter("chcksel");
        String chcksel1[]=chcksel.split(",");
        int chcksel2[]=new int [chcksel1.length];
        for(int i=0;i<chcksel1.length;i++)
        {
            chcksel2[i]=Integer.parseInt(chcksel1[i]);
            System.out.println("the checkbox number is"+chcksel2[i]);
        }
        mode=request.getParameter("mode");        
        String mode1[]=mode.split(",");        
        String mode2[]=new String [mode1.length];   
        for(int i=0;i<mode1.length;i++)
        {
            mode2[i]=mode1[i];
            System.out.println("the mode is"+mode2[i]);
        }
        String bank_id=request.getParameter("bank_id");
        String bank_id1[]=bank_id.split(",");
        int bank_id2[]=new int [bank_id1.length];
        for(int i=0;i<bank_id1.length;i++)
        {
            bank_id2[i]=Integer.parseInt(bank_id1[i]);
            System.out.println("the bank id is"+bank_id2[i]);
        }
        
        String branch_id=request.getParameter("branch_id");
        String branch_id1[]=branch_id.split(",");
        int branch_id2[]=new int [branch_id1.length];
        for(int i=0;i<branch_id1.length;i++)
        {
            branch_id2[i]=Integer.parseInt(branch_id1[i]);
            System.out.println("the branch id is"+branch_id2[i]);
        }
        
        String total_amount=request.getParameter("total_amount");
        String total_amount1[]=total_amount.split(",");
        Double total_amount2[]=new Double [total_amount1.length];
        for(int i=0;i<total_amount1.length;i++)
        {
            total_amount2[i]=Double.parseDouble(total_amount1[i]);
            System.out.println("the total amount is"+total_amount2[i]);
        }
        
      //  String cheque_or_dd=request.getParameter("cheque_or_dd");
       String cheque_or_dd=request.getParameter("Off_Account_Number");
        System.out.println("cheque_or_dd*********************"+cheque_or_dd);
        String cheque_or_dd1[]=cheque_or_dd.split(",");
        String cheque_or_dd2[]=new String [cheque_or_dd1.length];
        for(int i=0;i<cheque_or_dd1.length;i++)
        {
            cheque_or_dd2[i]=cheque_or_dd1[i];
            System.out.println("the cheque dd is"+cheque_or_dd2[i]);
        }
        //  String cheque_or_dd=request.getParameter("cheque_or_dd");
        String dr_AccHeadCode=request.getParameter("Off_Dr_AccHeadCode");
         System.out.println("dr_AccHeadCode*********************"+dr_AccHeadCode);
         String dr_AccHeadCode1[]=dr_AccHeadCode.split(",");
         String dr_AccHeadCode2[]=new String [dr_AccHeadCode1.length];
      String final_dr_code[]=new String[dr_AccHeadCode1.length];
      String final_off_ac_no[]=new String[dr_AccHeadCode1.length];
         for(int i=0;i<dr_AccHeadCode1.length;i++)
         {
        	 dr_AccHeadCode2[i]=dr_AccHeadCode1[i];
             System.out.println("the dr_AccHeadCode is:::::::;"+dr_AccHeadCode2[i]);
             //spliting accountHeadCode and AccountNo
          String split_dr_head[]=dr_AccHeadCode2[i].split("/");
          final_dr_code[i]=split_dr_head[0];
        final_off_ac_no[i]=split_dr_head[1];
         }   
        String ho_acct_id=request.getParameter("ho_acct_id");
        System.out.println("ho_acct_id:::"+ho_acct_id);
        String ho_acct_id1[]=ho_acct_id.split(",");
        String ho_acct_id2[]=new String [ho_acct_id1.length];
        for(int i=0;i<ho_acct_id1.length;i++)
        {
      //  System.out.println("looopppppppppppp"+Integer.parseInt(ho_acct_id1[i]));
            //ho_acct_id2[i]=Integer.parseInt(ho_acct_id1[i]);
             ho_acct_id2[i]=ho_acct_id1[i];
            System.out.println("the account id is"+ho_acct_id2[i]);
        }
        String dd_no=request.getParameter("dd_no");
        String dd_no1[]=dd_no.split(",");
        String dd_no2[]=new String [dd_no1.length];
        for(int i=0;i<ho_acct_id1.length;i++)
        {
            dd_no2[i]=dd_no1[i];
            System.out.println("the dd number is"+dd_no2[i]);
        }
        
        String dd_dt=request.getParameter("dd_dt");
        String dd_dt1[]=dd_dt.split(",");
        String dd_dt2[]=new String [dd_dt1.length];
        for(int i=0;i<dd_dt1.length;i++)
        {
            dd_dt2[i]=dd_dt1[i];
            System.out.println("the dd_dt is"+dd_dt2[i]);
        }
        
        String ho_bank_id=request.getParameter("ho_bank_id");
        String ho_bank_id1[]=ho_bank_id.split(",");
        int ho_bank_id2[]=new int [ho_bank_id1.length];
        for(int i=0;i<ho_bank_id1.length;i++)
        {
            ho_bank_id2[i]=Integer.parseInt(ho_bank_id1[i]);
            System.out.println("the ho_bank_id is"+ho_bank_id2[i]);
        }
        
        String ho_branch_id=request.getParameter("ho_branch_id");
        String ho_branch_id1[]=ho_branch_id.split(",");
        int ho_branch_id2[]=new int [ho_branch_id1.length];
        for(int i=0;i<ho_branch_id1.length;i++)
        {
            ho_branch_id2[i]=Integer.parseInt(ho_branch_id1[i]);
            System.out.println("the ho_branch_id is"+ho_branch_id2[i]);
        }
        
        String account_no=request.getParameter("account_no");
        String account_no1[]=account_no.split(",");
        long account_no2[]=new long [account_no1.length];
        for(int i=0;i<account_no1.length;i++)
        {
            account_no2[i]=Long.parseLong(account_no1[i]);
            System.out.println("the account_no is"+account_no2[i]);
        }
        
        String ref_no=request.getParameter("ref_no");
        String ref_no1[]=ref_no.split(",");
        String ref_no2[]=new String [ref_no1.length];
        for(int i=0;i<ref_no1.length;i++)
        {
            ref_no2[i]=ref_no1[i];
            System.out.println("the ref_no is"+ref_no2[i]);
        }
        
        String ref_dt=request.getParameter("ref_dt");
        String ref_dt1[]=ref_dt.split(",");
        String ref_dt2[]=new String [ref_dt1.length];
        for(int i=0;i<ref_dt1.length;i++)
        {
            ref_dt2[i]=ref_dt1[i];
            System.out.println("the ref_dt is"+ref_dt2[i]);
        }
        
        String particulars=request.getParameter("particulars");
        String particulars1[]=particulars.split(",");
        String particulars2[]=new String [particulars1.length];
        for(int i=0;i<particulars1.length;i++)
        {
            particulars2[i]=particulars1[i];
            System.out.println("the particulars is"+particulars2[i]);
        }    
            
        String acct_head_code=request.getParameter("acct_head_code");
        String acct_head_code1[]=acct_head_code.split(",");
        String acct_head_code2[]=new String [acct_head_code1.length];
        for(int i=0;i<acct_head_code1.length;i++)
        {
            acct_head_code2[i]=acct_head_code1[i];
            System.out.println("the account head number is"+acct_head_code2[i]);
            
        }
        String Off_Cr_AccHeadCode=request.getParameter("Off_Cr_AccHeadCode");
        String Off_Cr_AccHeadCode1[]=Off_Cr_AccHeadCode.split(",");
        int Off_Cr_AccHeadCode2[]=new int [Off_Cr_AccHeadCode1.length];
        for(int i=0;i<Off_Cr_AccHeadCode1.length;i++)
        {
     	   Off_Cr_AccHeadCode2[i]=Integer.parseInt(Off_Cr_AccHeadCode1[i]);
            System.out.println("the Off_Cr_AccHeadCode is"+Off_Cr_AccHeadCode2[i]);
        }
        String Off_Dr_AccHeadCode=request.getParameter("Off_Dr_AccHeadCode");
        String Off_Dr_AccHeadCode1[]=Off_Dr_AccHeadCode.split(",");
        int Off_Dr_AccHeadCode2[]=new int [Off_Dr_AccHeadCode1.length];
        for(int i=0;i<Off_Dr_AccHeadCode1.length;i++)
        {
     	   Off_Dr_AccHeadCode2[i]=Integer.parseInt(Off_Dr_AccHeadCode1[i].split("/")[0]);
            System.out.println("the Off_Dr_AccHeadCode is"+Off_Dr_AccHeadCode2[i]);
        }
         
        
        
        
        
        /** Actual Transaciton Starts Here */
            
         try 
         {
            
        	// String submitbtn=request.getParameter("submitid");
        	// System.out.println("submitid::"+submitbtn.d);
             /** Update "FAS_FUND_TRF_FROM_HO_TRN" table auto statu field */
        	   System.out.println("***************** STARTING ******************");
             int vno=0; int sno=0,count_trn=0; String status="";
             String sql="update FAS_FUND_TRF_FROM_HO_TRN set AUTO_STATUS='Y',UPDATED_DATE=?, UPDATED_BY_USER_ID=? where  TRANSFER_TO_OFFICE_ID=? and CASHBOOK_MONTH=? and CASHBOOK_YEAR=? AND VOUCHER_NO=? AND SL_NO=? "; 
             System.out.println(sql);
             PreparedStatement statement=con.prepareStatement(sql);
             con.setAutoCommit(false);
             for(int j=0;j<chcksel2.length;j++)
             {
                 System.out.println("the total length is"+chcksel2.length);
                 System.out.println("***************** STARTING 1 ******************");
                 vno=voucher_no2[j];
                 System.out.println(vno);
                
                 sno=sl_no2[j];
                 System.out.println(sno);            
                
                 statement.setTimestamp(1, ts);
                 System.out.println("time stamp is"+ts);
                 statement.setString(2, update_user);
                 System.out.println(" updated user is"+update_user);                 
                 
                 statement.setInt(3,office_id);
                 System.out.println("office id is"+office_id);
                 statement.setInt(4,month);
                 System.out.println("month is"+month);
                 statement.setInt(5,year);
                 System.out.println("year is"+year);
                 statement.setInt(6,vno); 
                 System.out.println("vno is"+vno);
                 statement.setInt(7,sno);
                 System.out.println("sno is"+sno);
              
                vno=0;
                sno=0;
                int i=statement.executeUpdate();
                if(i>0)
                {
                	count_trn++;
                	sysdt = txtCrea_date;
                    
                    /** Get Maximun Receipt Number */
                    
                    String sql_max="select max(receipt_no) as receipt from fas_fund_receipt_by_office where ACCOUNTING_UNIT_ID="+accunit+" and ACCOUNTING_FOR_OFFICE_ID="+office_id+" and cashbook_year="+cashbookyear_ho+" and cashbook_month="+cashbookmonth_ho+"";
                  //  System.out.println(sql_max);
                    st=con.createStatement();
                    rs=st.executeQuery(sql_max);
                    if(rs.next()) 
                    {
                           temp=rs.getInt("receipt");
                   }
                    
                    // joe changes on 08/04/2014
                    
                    
                    String mode_value=mode2[j];
                    int dr_head_final=0;
                    if(mode_value.equalsIgnoreCase("OPR-NRDWP-Main")||mode_value.equalsIgnoreCase("OPR-NRDWP-Support"))
                    {
                    System.out.println(" final sql query  >>> "+Off_Dr_AccHeadCode1[j].split("/")[0]);
                    String sql_head="select AC_HEAD_CODE from FAS_OFFICE_BANK_AC_CURRENT where accounting_unit_id="+accunitid+" and AC_OPERATIONAL_MODE_ID='"+mode_value+"' and module_id='MF009' and cr_dr_type='DR' ";
                    try{
                    	System.out.println("sql_head >>> "+sql_head);
                    	PreparedStatement ps_head=con.prepareStatement(sql_head);
                    	ResultSet rs_head=ps_head.executeQuery();
                    	while(rs_head.next()){
                    		dr_head_final=rs_head.getInt("AC_HEAD_CODE");
                    	}
                    
                    	
                    }catch(Exception e){
                    	System.out.println("Rxception in acc head for dr ..... ");
                    	e.printStackTrace();
                    }
                  
                    }
                    
                    String sql_ins="insert into FAS_FUND_RECEIPT_BY_OFFICE(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,RECEIPT_DATE,CASHBOOK_YEAR," +
                    "CASHBOOK_MONTH,RECEIPT_NO,DR_ACCOUNT_HEAD_CODE,OFFICE_BANK_ID,OFFICE_BRANCH_ID,OFFICE_ACCOUNT_NO,TOTAL_AMOUNT," +
                    "CHEQUE_OR_DD,CHEQUE_DD_NO,CHEQUE_DD_DATE,CR_ACCOUNT_HEAD_CODE,HO_BANK_ID,HO_BRANCH_ID,HO_ACCOUNT_NO,HO_REF_NO,HO_REF_DATE,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE,RECEIPT_STATUS,REMITTANCE_STATUS, AUTO_STATUS, TRF_VOUCHER_NO," +
                    " TRF_VOUCHER_DATE , CHALLAN_NO,TRF_CB_YEAR,TRF_CB_MONTH,TRF_SL_NO) values(?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?::numeric,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,?,?,?,?,?,?,?)"; 
                //    System.out.println(sql_ins);
                    PreparedStatement stat=con.prepareStatement(sql_ins);
                    

                    temp=temp+1;
                 //   System.out.println("temp:"+temp);
                    vno=voucher_no2[j];
                   // System.out.println(vno);
                    acct_head=acct_head_code2[j];
                    sno=sl_no2[j];
                   //  System.out.println(sno);
                     bank=bank_id2[j];
                     branch=branch_id2[j];
                     accountid=ho_acct_id2[j];
                     tot_amt=total_amount2[j];
                     cheque_dd=cheque_or_dd2[j];
                     ddno=dd_no2[j];
                     //String mode_value=mode2[j];
                   
                    
                     stat.setInt(1,accunit);
                     stat.setInt(2,office_id);
                     stat.setDate(3,sysdt);
                   
                     stat.setInt(4,cashbookyear_ho);
                  
                     stat.setInt(5,cashbookmonth_ho);
                     stat.setInt(6,temp);
                //   int dr_head=Integer.parseInt(dr_AccHeadCode2[j]);
                     // joe change final_dr_code
                    
                     System.out.println("mode_value"+mode_value);
                     if(mode_value.equalsIgnoreCase("OPR-NRDWP-Main")||mode_value.equalsIgnoreCase("OPR-NRDWP-Support"))
                     {
                   //  int dr_head=Integer.parseInt(final_dr_code[j]);
                    // int dr_head=Integer.parseInt(Off_Dr_AccHeadCode1[j].split("/")[0]);
                     System.out.println(" final dr amount dr_head_final head code >>> "+dr_head_final);
                     stat.setInt(7,dr_head_final);
                     }
                else if(!mode_value.equalsIgnoreCase("OPR-NRDWP-Main") && !mode_value.equalsIgnoreCase("OPR-NRDWP-Support"))
                	{
                		 int dr_head=Integer.parseInt(final_dr_code[j]);
                		   stat.setInt(7,dr_head);
                	}
                    
                     stat.setInt(8,bank);
                     stat.setInt(9,branch);
                //     stat.setLong(10,account_no2[j]);
                     stat.setLong(10,Long.parseLong(final_off_ac_no[j]));
                     
                     stat.setDouble(11,tot_amt);
                     stat.setString(12,cheque_dd);
                     stat.setString(13,ddno);
                     if(dd_dt2[j].equalsIgnoreCase("-")) 
                     {
                        dd_dt2[j]=null;    
                     }
                     stat.setString(14,dd_dt2[j]);
                     
                     String cr_head="";
                     System.out.println("cr head code"+Integer.parseInt(Off_Cr_AccHeadCode1[j]));
                     if(mode_value.equalsIgnoreCase("OPR-NRDWP-Main")||mode_value.equalsIgnoreCase("OPR-NRDWP-Support"))
                     {
                     stat.setInt(15,Integer.parseInt(Off_Cr_AccHeadCode1[j]));
                     }else if(!mode_value.equalsIgnoreCase("OPR-NRDWP-Main") && !mode_value.equalsIgnoreCase("OPR-NRDWP-Support")){
                    	 cr_head=acct_head+"05";
                    	 stat.setInt(15,Integer.parseInt(cr_head));
                     }
                     
                     
                     
                     stat.setInt(16,ho_bank_id2[j]);
                     System.out.println("cr ho_bank_id2 code"+ho_bank_id2[j]);
                     stat.setInt(17,ho_branch_id2[j]);
                     System.out.println("ho_branch_id2"+ho_branch_id2);
                     stat.setString(18,accountid);
                     System.out.println("accountid"+accountid);
                     if(ref_no2[j].equalsIgnoreCase("-")) 
                     {
                        ref_no2[j]=null;    
                     }
                     stat.setString(19,ref_no2[j]);
                     if(ref_dt2[j].equalsIgnoreCase("-")) 
                     {
                        ref_dt2[j]=null;    
                     }
                     stat.setString(20,ref_dt2[j]);
                     System.out.println("ref_dt2"+ref_dt2);
                    stat.setString(21,particulars2[j]);
                    System.out.println("particulars2"+particulars2[j]);
                    
                     stat.setString(22, update_user);
                     stat.setTimestamp(23, ts);
                     stat.setString(24,"L");
                     stat.setString(25,"N");
                     stat.setString(26,"Y");
                     stat.setInt(27,voucher_no2[j]);
                    stat.setDate(28,date_trans2[j]); 
                    stat.setInt(29,0);
                     stat.setInt(30,year);
                     stat.setInt(31,month);
                     stat.setInt(32,sno);
                    int ii=stat.executeUpdate();
                   // System.out.println("i:::"+i);
                    if(ii>0)
                    {
                        cnt_office++;
                    }   
                     /**
                      * Auto Generation of Fund Remittance for ECS Transaction 
                      */
                     
                     String Remittance_Type="";
                     
                     Remittance_Type = cheque_dd;
                     
                     System.out.println("Remittance_Type----><--"+Remittance_Type);
                     
                     int  Verified_Authority=0;
                     CallableStatement cs1=null;
                     
                     if (Remittance_Type.equalsIgnoreCase("E"))      
                     {
                       
                         UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
                         Verified_Authority= empProfile.getEmployeeId();                 
                                       
                         
                         System.out.println("inside E ");
                         cs1=con.prepareCall("call FAS_ECS_REMITTANCE_PROC(?::numeric,?::numeric,?::numeric,?::numeric,?,?,?::numeric,?::numeric,?,?)");
                         cs1.setInt(1,accunit);
                         cs1.setInt(2,office_id);
                         cs1.setInt(3,cashbookyear_ho);
                         cs1.setInt(4,cashbookmonth_ho);
                         cs1.setDate(5,sysdt);
                         cs1.setString(6,"F-OFF");
                         cs1.setDouble(7,tot_amt);
                         cs1.setInt(8,Verified_Authority);
                         cs1.setString(9, update_user);
                         cs1.registerOutParameter(10, java.sql.Types.VARCHAR);
                         cs1.setNull(10, java.sql.Types.VARCHAR);
                         cs1.execute();
                         //int err_code = cs1.getInt(10);
                         String err_code = cs1.getString(10);
                         //if (err_code.equals("0"))
                         if (!err_code.equals("0"))
                         {
                                 con.rollback();
                                 System.out.println("Auto Fund Receipt Creation Failed");
                                 sendMessage(response," Auto Fund Receipt Creation Failed ","ok");
                                 return;     
                         }
                         
                     }      
                    
                }
             } 
             
             System.out.println(" Checkbox length: "+chcksel2.length+"  count: "+count_trn);
             
             if(count_trn==cnt_office)
             {
                con.commit();
                sendMessage(response,"The Auto Fund Receipt Saved Successfully","ok");
             }
             else
             {
                con.rollback();    
                sendMessage(response,"The Auto Fund Receipt Are Not Saved ","ok");
             }
             
             
             
             
         }
         catch(Exception e) 
         {
            System.out.println("the exception in update is"+e.getMessage());   
            e.printStackTrace();
             try{con.rollback();}catch(SQLException sqle){System.out.println("Excep"+sqle);}
           
         }         
        finally
        {
           System.out.println("done here");
           try{con.setAutoCommit(true);  }catch(SQLException sqle){System.out.println("Excep"+sqle);}
        }
   }
        
    
    
    
    
        private void sendMessage(HttpServletResponse response,String msg,String bType)
        {
        	try
        	{
        		System.out.println("Inside.....................");
        		String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
        		response.sendRedirect(url);
        	}
        	catch(Exception e)
        	{
        		System.out.println("error in messenger"+e);
        	}        	
        }
        
        
}
