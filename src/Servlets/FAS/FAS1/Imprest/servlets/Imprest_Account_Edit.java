package Servlets.FAS.FAS1.Imprest.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;
import Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_DETAILS;
import Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_GENERAL;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import java.sql.SQLException;

import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class Imprest_Account_Edit extends HttpServlet
{
    private String CONTENT_TYPE = "text/xml; charset=windows-1252";
  
    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
      
    }
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException , IOException
    {
            String CONTENT_TYPE = "text/xml";
            response.setContentType(CONTENT_TYPE);
	    	String strCommand="";
	        Connection con=null;        
	        PreparedStatement ps=null;
	        ResultSet rs=null;
	        String xml="",sql="",txtMode_of_creat="",doc_type="";
	        int cmbAcc_UnitCode=0,cmbOffice_code=0,count=0;
	        PrintWriter out = response.getWriter();  
	        String txtCrea_date=null;
	        
		    //-----------------------------------------------------------------------------------------------        
		
		       HttpSession session=request.getSession(false);
		       try
		       {
		           
				            if(session==null)
				            {
						               System.out.println(request.getContextPath()+"/index.jsp");
						               response.sendRedirect(request.getContextPath()+"/index.jsp");
						               return;
				            }
				            System.out.println(session);
				               
		       }
		       catch(Exception e)
		       {
		    	   		    System.out.println("Redirect Error :"+e);
		       } 
		       
		   //-----------------------------------------------------------------------------------------------        
		       
			   try 
			   {
                             ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                             String ConnectionString="";

                             String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                             String strdsn=rs1.getString("Config.DSN");
                             String strhostname=rs1.getString("Config.HOST_NAME");
                             String strportno=rs1.getString("Config.PORT_NUMBER");
                             String strsid=rs1.getString("Config.SID");
                             String strdbusername=rs1.getString("Config.USER_NAME");
                             String strdbpassword=rs1.getString("Config.PASSWORD");
                             ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                             Class.forName(strDriver.trim());
                             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
		        }
		        catch(Exception e)
		        {
			           		 System.out.println("Exception in opening connection :"+e);
			           		 //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");
		
		        }
		                
	        //-----------------------------------------------------------------------------------------------        
		       
		        try
		        {

		                     response.setHeader("Cache-Control","no-cache");
					         strCommand=request.getParameter("Command");
					         System.out.println("assign..here command..."+strCommand);
			           
		          
		        }		       
		        catch(Exception e) 
		        {
		           	   		 System.out.println("Exception in assigning..."+e);
		        }
		       
		    //-----------------------------------------------------------------------------------------------        
    	
		        try{cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
				catch(Exception e){System.out.println(e);}
				
				try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
		        catch(NumberFormatException e){System.out.println("exception"+e );}
		        
		        try{txtMode_of_creat=request.getParameter("txtMode_of_creat");}
	            catch(Exception e){System.out.println("Err in get journal_type :: "+e.getMessage());}
	            
	            
	            if(txtMode_of_creat.equals("I"))
	            		    doc_type="IMP";
	            else
	            			doc_type="TMP";
	            
	            
		  //-----------------------------------------------------------------------------------------------        
		        xml="<response>";
		        if(strCommand.equalsIgnoreCase("load_Voucher_No")) 
		        {
		        	   System.out.println("load_Voucher_No");
				            Calendar c;count=0;
				            xml=xml+"<command>load_Voucher_No</command>";
				            			            
				            String option_val=request.getParameter("option");			            
				            txtCrea_date=request.getParameter("txtCrea_date");
				            System.out.println("txtCrea_date "+txtCrea_date);
				            
				            try {
			                           sql= " select           	"+ 
					                        "   i.VOUCHER_NO  	"+ 
				                            " from 				"+ 
				                            "     FAS_PAYMENT_MASTER i,"+ 
				                            "     FAS_CROSS_REFERENCE c "+ 
				                            " where "+ 
				                            "     i.ACCOUNTING_UNIT_ID=?  and	"+ 
				                            "     i.ACCOUNTING_FOR_OFFICE_ID=? and "+ 
				                            "     i.PAYMENT_DATE=to_date(?,'dd-MM-yyyy') and "+ 
				                            "     PAYMENT_TYPE='B' and 	"+ 
				                            "     i.PAYMENT_STATUS!='C'  and 	"+ 
				                            "     i.MODE_OF_CREATION='"+txtMode_of_creat+"' and 	"+ 
				                            "     i.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and	"+ 
				                            "     i.ACCOUNTING_FOR_OFFICE_ID=c.ACCOUNTING_FOR_OFFICE_ID and	"+ 
				                            "     i.CASHBOOK_YEAR=c.CASHBOOK_YEAR and	"+ 
				                            "     i.CASHBOOK_MONTH=c.CASHBOOK_MONTH and "+ 
				                            "     i.VOUCHER_NO=c.VOUCHER_NO and	"+ 
				                            "     c.CHANGE_NO=0  and "+ 				                           	   
				                            "	  i.VOUCHER_NO not in(select RECEIVABLE_VOUCHER_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and to_date(RECEIVABLE_VOUCHER_DATE,'dd-MM-yy')=to_date(?,'dd-MM-yy') and RECEIPT_STATUS='L' )  and "+
				                            "	  i.VOUCHER_NO not in(select CB_REF_NO from FAS_JOURNAL_TRANSACTION trn,FAS_JOURNAL_MASTER mst where trn.ACCOUNTING_UNIT_ID=mst.ACCOUNTING_UNIT_ID and trn.ACCOUNTING_FOR_OFFICE_ID=mst.ACCOUNTING_FOR_OFFICE_ID and trn.CASHBOOK_YEAR=mst.CASHBOOK_YEAR and trn.CASHBOOK_MONTH=mst.CASHBOOK_MONTH and trn.VOUCHER_NO=mst.VOUCHER_NO and trn.ACCOUNTING_UNIT_ID=? and trn.ACCOUNTING_FOR_OFFICE_ID=? and to_date(trn.CB_REF_DATE,'dd-MM-yy')=to_date(?,'dd-MM-yy') and mst.JOURNAL_STATUS='L' )";              
			                           if(option_val.equals("Edit"))
			                        	    sql=sql+" AND c.authorized_to = 'M'	  						  " ;
			                           else
                                            sql=sql+" AND c.authorized_to = 'C'	                          " ;
			                           sql=sql+" AND c.doc_type =? 								  " ;
			                        	
			                           //System.out.println("SQL:::"+sql);
			                        	    															  
			                           ps=con.prepareStatement(sql);
			                           ps.setInt(1,cmbAcc_UnitCode);
			                         
			                           ps.setInt(2,cmbOffice_code);
			                          
			                           ps.setString(3,txtCrea_date);
			                           //  ps.setString(4,txtMode_of_creat);
			                           ps.setInt(4,cmbAcc_UnitCode);
			                           ps.setInt(5,cmbOffice_code);
			                           ps.setString(6,txtCrea_date);
			                           ps.setInt(7,cmbAcc_UnitCode);
			                           ps.setInt(8,cmbOffice_code);
			                           ps.setString(9,txtCrea_date);
			                           ps.setString(10,doc_type);System.out.println("doc_type"+doc_type);
			                           rs=ps.executeQuery();                                                   
			                           while(rs.next())
			                           {
			                        	    xml=xml+"<Voc_No>"+rs.getInt("voucher_no")+"</Voc_No>";
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
					                   xml=xml+"<flag>failure</flag>";
					                   System.out.println(e);                           
				            }  
			               
		        }
		        else if(strCommand.equalsIgnoreCase("load_Voucher_Details")) 
		        {
		            System.out.println("load_Voucher_Details");
				           Calendar c;count=0;
				           xml="<response><command>load_Voucher_Details</command>";
				           int txtVoucher_No=0;
				           // Date txtCrea_date;
				              
				           try{txtVoucher_No=Integer.parseInt(request.getParameter("txtVoucher_No"));}
				           catch(NumberFormatException e){System.out.println("exception"+e );}
				           System.out.println("txtVoucher_No "+txtVoucher_No);
		             
				           try 
                           {                                  
	                                    txtCrea_date=request.getParameter("txtCrea_date");
	                                    System.out.println("txtCrea_date "+txtCrea_date);		                            
	                                    sql="select * from											    "+
	                                            "(														"+
	                                            "    select 											"+
	                                            "		 mst.ACCOUNTING_UNIT_ID,						"+
	                                            "		 mst.ACCOUNTING_FOR_OFFICE_ID,					"+
	                                            "		 mst.CASHBOOK_MONTH,							"+
	                                            "		 mst.CASHBOOK_YEAR,								"+
	                                            "		 mst.VOUCHER_NO,								"+	
	                                            "        mst.ACCOUNT_HEAD_CODE as OPR_HEAD_CODE,		"+
	                                            "        mst.BANK_ID,									"+
	                                            "        mst.BRANCH_ID,									"+
	                                            "        mst.ACCOUNT_NO,								"+
	                                            "        mst.SUB_LEDGER_CODE AS OFF_CODE,				"+
	                                            "        mst.CR_DR_INDICATOR as MST_CR_DR_INDICATOR,	"+
	                                            "        mst.REMARKS,									"+
	                                            "		 mst.RECOUPED_STATUS, "+
	                                            "        trim(to_char(mst.TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT,								"+										
	                                            "        trn.SL_NO,										"+
	                                            "        trn.ACCOUNT_HEAD_CODE,							"+
	                                            "        trn.CR_DR_INDICATOR as TRN_CR_DR_INDICATOR,	"+
	                                            "        trn.SUB_LEDGER_TYPE_CODE,						"+
	                                            "        trn.SUB_LEDGER_CODE,							"+
	                                            "        trn.BILL_NO,									"+
	                                            "        to_char(trn.BILL_DATE,'DD/MM/YY') as BILL_DATE,									"+
	                                            "        trn.CHEQUE_OR_DD,								"+
	                                            "        trn.CHEQUE_DD_NO,								"+
	                                            "        to_char(trn.CHEQUE_DD_DATE,'DD/MM/YY') as CHEQUE_DD_DATE,							"+
	                                            "        trn.PAID_TO,									"+
	                                            "        trim(to_char(trn.AMOUNT,'99999999999999.99')) as AMOUNT,									"+
	                                            "        trn.PARTICULARS,								"+
	                                            "        trn.WD_PURPOSE_ID,								"+
	                                            "        trim(trn.TYPE_OF_PAYMENT) as TYPE_OF_PAYMENT	"+
	                                            "    from													"+
	                                            "        FAS_PAYMENT_MASTER mst,						"+
	                                            "        FAS_PAYMENT_TRANSACTION trn					"+
	                                            "    where												"+
	                                            "        mst.ACCOUNTING_UNIT_ID=trn.ACCOUNTING_UNIT_ID and					"+
	                                            "        mst.ACCOUNTING_FOR_OFFICE_ID=trn.ACCOUNTING_FOR_OFFICE_ID and		"+
	                                            "        mst.CASHBOOK_YEAR=trn.CASHBOOK_YEAR and 							"+
	                                            "        mst.CASHBOOK_MONTH=trn.CASHBOOK_MONTH and							"+
	                                            "        mst.VOUCHER_NO=trn.VOUCHER_NO and									"+
	                                            "        mst.ACCOUNTING_UNIT_ID=? and										"+
	                                            "        mst.ACCOUNTING_FOR_OFFICE_ID=? and									"+		
	                                            "        mst.PAYMENT_DATE=to_date(?,'dd-MM-yyyy') and												"+										
	                                            "        mst.VOUCHER_NO=? and												"+
	                                            "        mst.MODE_OF_CREATION='"+txtMode_of_creat+"' and 	                                    "+ 
	                                            "        mst.PAYMENT_STATUS!='C'											"+
	                                            ")a																			"+
	                                            "left outer join										"+
	                                            "(														"+
	                                            "    select 											"+
	                                            "          br.BANK_ID as BID ,							"+
	                                            "          br.BRANCH_ID as BR_ID  ,						"+
	                                            "          bk.BANK_NAME ||'-'||br.BRANCH_NAME || '-' ||coalesce (br.CITY_TOWN_NAME,'') as bankNAME	"+
	                                            "    from 												"+
	                                            "          FAS_MST_BANKS bk,							"+
	                                            "          FAS_MST_BANK_BRANCHES br 					"+	
	                                            "    where 												"+
	                                            "          br.BANK_ID=bk.BANK_ID						"+
	                                            ")b														"+
	                                            "on 													"+
	                                            "  a.BANK_ID=b.BID AND									"+
	                                            "  a.BRANCH_ID=b.BR_ID									"+
	                                            "left outer join										"+
	                                            "(														" +
	                                            "	select SUB_LEDGER_TYPE_CODE as sub_code,SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES	 "+
	                                            ")c	 																					 "+
	                                            "on a.SUB_LEDGER_TYPE_CODE=c.sub_code 													 "+
	                                            "left outer join																		 "+
	                                            "(		"+
	                                            "	select e.EMPLOYEE_ID,e.EMPLOYEE_NAME||'.'||e.EMPLOYEE_INITIAL||'-'|| d.DESIGNATION as ENAME,c.EMPLOYEE_STATUS_ID from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID 	"+
	                                            ")d      "+
	                                            "on a.SUB_LEDGER_CODE=d.EMPLOYEE_ID and d.EMPLOYEE_STATUS_ID in('WKG','DPN')"+
	                                            "left outer join					"+
	                                            "(									"+
	                                            "    select wd_purpose_id as purpose_id,wd_purpose_desc from fas_wd_purpose_mst	where type='I'		"+
	                                            ")e									"+
	                                            "on a.WD_PURPOSE_ID=e.purpose_id	"+
	                                            "left outer join					"+
	                                            "( 									"+
	                                            "	 select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,SUB_LEDGER_CODE,trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as RECOUP_AMOUNT,RECOUPED_FOR_VOUCHER_NO,RECOUPED_FOR_VOUCHER_SLNO,to_char(RECOUPED_FOR_VOUCHER_DATE,'DD/MM/YYYY')as RECOUPED_FOR_VOUCHER_DATE from FAS_IMPREST_RECOUP  " +
	                                            ")f									"+
	                                            "on a.ACCOUNTING_UNIT_ID=f.ACCOUNTING_UNIT_ID and a.ACCOUNTING_FOR_OFFICE_ID=f.ACCOUNTING_FOR_OFFICE_ID and a.CASHBOOK_YEAR=f.CASHBOOK_YEAR and a.CASHBOOK_MONTH=f.CASHBOOK_MONTH and a.VOUCHER_NO=f.VOUCHER_NO and a.SL_NO=f.SL_NO and a.SUB_LEDGER_CODE=f.SUB_LEDGER_CODE  " +
	                                            "order by a.SL_NO";
			                            
	                                    System.out.println("SQL ::: "+sql);
			                            ps=con.prepareStatement(sql);  
			                            ps.setInt(1,cmbAcc_UnitCode);
			                            ps.setInt(2,cmbOffice_code);
			                            ps.setString(3,txtCrea_date);
			                            ps.setInt(4,txtVoucher_No);
			                         //   ps.setString(5,txtMode_of_creat);
			                            rs=ps.executeQuery();
			                            while(rs.next())
			                            {
				                            	count++;
				                            	xml+="<OPR_HEAD_CODE>"+rs.getInt("OPR_HEAD_CODE")+"</OPR_HEAD_CODE>";
				                            	
				                            	xml+="<BANK_ID>"+rs.getInt("BANK_ID")+"</BANK_ID>";
				                            	
				                            	xml+="<BRANCH_ID>"+rs.getInt("BRANCH_ID")+"</BRANCH_ID>";
				                            	xml+="<ACCOUNT_NO>"+rs.getInt("ACCOUNT_NO")+"</ACCOUNT_NO>";
				                            	
				                            	xml+="<bankNAME>"+rs.getString("bankNAME")+"</bankNAME>";
				                            	xml+="<MST_CR_DR_INDICATOR>"+rs.getString("MST_CR_DR_INDICATOR")+"</MST_CR_DR_INDICATOR>";				                            	
				                            	xml+="<OFF_CODE>"+rs.getInt("OFF_CODE")+"</OFF_CODE>";
				                            	if(rs.getString("REMARKS")==null)
				                            		xml+="<REMARKS>-</REMARKS>";
				                            	else
				                            		xml+="<REMARKS>"+rs.getString("REMARKS")+"</REMARKS>";
				                            	xml+="<TOTAL_AMOUNT>"+rs.getDouble("TOTAL_AMOUNT")+"</TOTAL_AMOUNT>";
				                            	xml+="<ACCOUNT_HEAD_CODE>"+rs.getInt("ACCOUNT_HEAD_CODE")+"</ACCOUNT_HEAD_CODE>";
				                            	if(rs.getString("SUB_LEDGER_TYPE_CODE")==null)
				                            		xml+="<SUB_LEDGER_TYPE_CODE>-</SUB_LEDGER_TYPE_CODE>";
				                            	else
				                            		xml+="<SUB_LEDGER_TYPE_CODE>"+rs.getInt("SUB_LEDGER_TYPE_CODE")+"</SUB_LEDGER_TYPE_CODE>";
				                            	
				                            	if(rs.getString("SUB_LEDGER_TYPE_DESC")==null)
					                            	xml+="<SUB_LEDGER_TYPE_DESC>-</SUB_LEDGER_TYPE_DESC>";
				                            	else
				                            		xml+="<SUB_LEDGER_TYPE_DESC>"+rs.getString("SUB_LEDGER_TYPE_DESC")+"</SUB_LEDGER_TYPE_DESC>";
				                            	
				                            	if(rs.getString("SUB_LEDGER_CODE")==null)
				                            		xml+="<SUB_LEDGER_CODE>-</SUB_LEDGER_CODE>";
				                            	else
				                            		xml+="<SUB_LEDGER_CODE>"+rs.getInt("SUB_LEDGER_CODE")+"</SUB_LEDGER_CODE>";
				                            	
				                            	if(rs.getString("ENAME")==null)
				                            		xml+="<ENAME>-</ENAME>";
				                            	else
				                            		xml+="<ENAME>"+rs.getString("ENAME")+"</ENAME>";
				                            	
				                            	if(rs.getString("BILL_NO")==null)
				                            		xml+="<BILL_NO>-</BILL_NO>";
				                            	else
				                            		xml+="<BILL_NO>"+rs.getString("BILL_NO")+"</BILL_NO>";
				                            	
				                            	if(rs.getString("BILL_DATE")==null)
				                            		xml+="<BILL_DATE>-</BILL_DATE>";
				                            	else
				                            		xml+="<BILL_DATE>"+rs.getString("BILL_DATE")+"</BILL_DATE>";
				                            	
				                            	xml+="<CHEQUE_OR_DD>"+rs.getString("CHEQUE_OR_DD")+"</CHEQUE_OR_DD>";
				                            	xml+="<CHEQUE_DD_NO>"+rs.getString("CHEQUE_DD_NO")+"</CHEQUE_DD_NO>";
				                            	xml+="<CHEQUE_DD_DATE>"+rs.getString("CHEQUE_DD_DATE")+"</CHEQUE_DD_DATE>";
				                            	xml+="<PAID_TO>"+rs.getString("PAID_TO")+"</PAID_TO>";
				                            	xml+="<AMOUNT>"+rs.getDouble("AMOUNT")+"</AMOUNT>";
				                            	if(rs.getString("PARTICULARS")==null)
				                            		xml+="<PARTICULARS>-</PARTICULARS>";
				                            	else
				                            		xml+="<PARTICULARS>"+rs.getString("PARTICULARS")+"</PARTICULARS>";
				                            	if(rs.getString("WD_PURPOSE_ID")==null)
				                            		xml+="<WD_PURPOSE_ID>-</WD_PURPOSE_ID>";
				                            	else
				                            		xml+="<WD_PURPOSE_ID>"+rs.getString("WD_PURPOSE_ID")+"</WD_PURPOSE_ID>";
				                            	if(rs.getString("wd_purpose_desc")==null)
				                            		xml+="<wd_purpose_desc>-</wd_purpose_desc>";
				                            	else
				                            		xml+="<wd_purpose_desc>"+rs.getString("wd_purpose_desc")+"</wd_purpose_desc>";
				                            	if(rs.getString("TYPE_OF_PAYMENT")==null)
				                            		xml+="<TYPE_OF_PAYMENT>-</TYPE_OF_PAYMENT>";
				                            	else
				                            		xml+="<TYPE_OF_PAYMENT>"+rs.getString("TYPE_OF_PAYMENT")+"</TYPE_OF_PAYMENT>";
				                            	
				                            	
				                            	if(rs.getString("TYPE_OF_PAYMENT").equals("R"))
				                            	{
				                            			xml+="<RECOUPED_STATUS>R</RECOUPED_STATUS>";
				                            			
						                            	if(rs.getString("RECOUPED_FOR_VOUCHER_NO")==null)
						                            		xml+="<RECOUP_VOUCHER_NO>-</RECOUP_VOUCHER_NO>";
						                            	else
						                            		xml+="<RECOUP_VOUCHER_NO>"+rs.getString("RECOUPED_FOR_VOUCHER_NO")+"</RECOUP_VOUCHER_NO>";
						                            	
						                            	if(rs.getString("RECOUPED_FOR_VOUCHER_SLNO")==null)
						                            		xml+="<RECOUP_VOUCHER_SLNO>-</RECOUP_VOUCHER_SLNO>";
						                            	else
						                            		xml+="<RECOUP_VOUCHER_SLNO>"+rs.getString("RECOUPED_FOR_VOUCHER_SLNO")+"</RECOUP_VOUCHER_SLNO>";
						                            	
						                            	if(rs.getString("RECOUPED_FOR_VOUCHER_DATE")==null)
						                            		xml+="<RECOUP_VOUCHER_DATE>-</RECOUP_VOUCHER_DATE>";
						                            	else
						                            		xml+="<RECOUP_VOUCHER_DATE>"+rs.getString("RECOUPED_FOR_VOUCHER_DATE")+"</RECOUP_VOUCHER_DATE>";
						                            	
						                            	if(rs.getString("RECOUP_AMOUNT")==null)
						                            		xml+="<RECOUP_VOUCHER_AMT>-</RECOUP_VOUCHER_AMT>";
						                            	else
						                            		xml+="<RECOUP_VOUCHER_AMT>"+rs.getString("RECOUP_AMOUNT")+"</RECOUP_VOUCHER_AMT>";
				                            	}
				                            	else
				                            			xml+="<RECOUPED_STATUS>N</RECOUPED_STATUS>";	
				                            	
				                            	
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
			                        	System.out.println("catch..HERE.in failure to retrieve."+e);
	                                    xml=xml+"<flag>failure</flag>";
		                        }
		                        
		       }
		       xml=xml+"</response>";
		       System.out.println(xml);
		       out.println(xml);
    }
    public void doPost(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
    
         
         String strCommand="";
         Connection con=null;        
         PreparedStatement ps=null,ps1=null,ps2=null;
         String xml="";
         ResultSet rs=null;
         Statement st=null;
 //-----------------------------------------------------------------------------------------------        
 
        HttpSession session=request.getSession(false);
        try
        {
            
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
        
//-----------------------------------------------------------------------------------------------        
        
        try 
        {
                              ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                              String ConnectionString="";

                              String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                              String strdsn=rs1.getString("Config.DSN");
                              String strhostname=rs1.getString("Config.HOST_NAME");
                              String strportno=rs1.getString("Config.PORT_NUMBER");
                              String strsid=rs1.getString("Config.SID");
                              String strdbusername=rs1.getString("Config.USER_NAME");
                              String strdbpassword=rs1.getString("Config.PASSWORD");
                              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                              Class.forName(strDriver.trim());
                              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                              st=con.createStatement();
         }
         catch(Exception e)
         {
            System.out.println("Exception in opening connection :"+e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

         }
                 
 //-----------------------------------------------------------------------------------------------        
        
        try {
        
            strCommand=request.getParameter("Command");
            System.out.println("assign..here command..."+strCommand);
           
        }
        
        catch(Exception e) 
        {
            System.out.println("Exception in assigning..."+e);
        }
        
//-----------------------------------------------------------------------------------------------        
       
        if(strCommand.equalsIgnoreCase("Add")) 
        {
	            String CONTENT_TYPE = "text/html; charset=windows-1252";
	            response.setContentType(CONTENT_TYPE);
	            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	            xml="<response><command>Add</command>";
	            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	            Calendar c;
	            int txtAcc_HeadCode=0,cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0;
	            int bank_id=0,branch_id=0,count=0,acc_no=0,voucher_no=0,txtOfficeId=0,sub_type_code=0;
	            double txtTotalAmt=0;
	            Date txtCrea_date=null;
	            String PaidTo="",remarks="",txtMode_of_creat="",doc_type="";
	            
	                                    // changes here
	            String update_user=(String)session.getAttribute("UserId");
	            long l=System.currentTimeMillis();
	            Timestamp ts=new Timestamp(l);	                
	            int errcode=0,sno=0,err=0;
	            
	            int h_code=0,stype=0,stype_code=0,cheque_num=0,p_id=0,recoup_vou=0,recoup_slno=0;
	            double amt=0,recoup_amt=0;
	           	String bill_no="";
	            Date bill_date=null,dd_date=null,recoup_dt=null;
                CallableStatement cs1=null;
	            
	            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	                               
	            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            
	            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            
	            String[] sd=request.getParameter("txtCrea_date").split("/");
	            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	            java.util.Date d=c.getTime();
	            txtCrea_date=new Date(d.getTime());
	            
	            System.out.println("b4 getting month and year");
	            try{txtCash_year=Integer.parseInt(sd[2]);}
	            catch(Exception e){System.out.println("exception"+e );}
	            
	            try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
	            catch(Exception e){System.out.println("exception"+e );}            
	            	            
	            try{voucher_no=Integer.parseInt(request.getParameter("txtVoucher_No"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
                    
	            try{txtMode_of_creat=request.getParameter("cmbPayment_type");}
	            catch(Exception e){	System.out.println("Exception in journal_type ::: "+e.getMessage()); }
                    
                    if(txtMode_of_creat.equals("I"))
                                    doc_type="IMP";
                    else
                                    doc_type="TMP";
	            
	            try
	            {
	            	rs=st.executeQuery("select SUB_LEDGER_TYPE_CODE from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_DESC='Offices'");
	                if(rs.next()) 
	                {
	                	sub_type_code = rs.getInt("SUB_LEDGER_TYPE_CODE");                		                   
	                }
	               
	            }
	            catch(Exception e){System.out.println("sub_type_code   "+e );}
	            
	            try{txtAcc_HeadCode=Integer.parseInt(request.getParameter("txtCash_Acc_code"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            
	            try{bank_id=Integer.parseInt(request.getParameter("txtBankID"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            
	            try{branch_id=Integer.parseInt(request.getParameter("txtBranchID"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            
	            try{acc_no=Integer.parseInt(request.getParameter("txtBankAccountNo"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            
	            try{txtOfficeId=Integer.parseInt(request.getParameter("txtOfficeId"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            
	           
	            try{PaidTo=request.getParameter("txtOfficeDet");}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            
	            
	            try{remarks=request.getParameter("txtRemarks");}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            
	            try{txtTotalAmt=Double.parseDouble(request.getParameter("txtTotalAmt"));}
	            catch(Exception e){System.out.println("exception"+e );}            	                      
	           
	            int row_num=Integer.parseInt(request.getParameter("num_rows"));
	            System.out.println("num_rows:"+row_num);
	            String sql_del="",sql="";
	            int bf_upd_count=0;
	           
	            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~   
	            try
	            {
	            		 System.out.println("Inside Imprest Recoup Update");
			             sql=" select RECOUPED_FOR_VOUCHER_NO,to_char(RECOUPED_FOR_VOUCHER_DATE,'DD/MM/YYYY') as RECOUPED_FOR_VOUCHER_DATE from FAS_IMPREST_RECOUP	"+ 
		    			 " where ACCOUNTING_UNIT_ID=? 					"+
		    			 " and ACCOUNTING_FOR_OFFICE_ID=?				"+
		    			 " and CASHBOOK_YEAR=? 							"+
		    			 " and CASHBOOK_MONTH=?							"+
		    			 " and voucher_no=? 						    ";	                 			
			             System.out.println("SQL :::"+sql);			            
			         	 ps=con.prepareStatement(sql);
			             ps.setInt(1,cmbAcc_UnitCode);
			             ps.setInt(2,cmbOffice_code);
			             ps.setInt(3,txtCash_year);
			             ps.setInt(4,txtCash_Month_hid);
			             ps.setInt(5,voucher_no);
			             rs=ps.executeQuery();
			             while(rs.next())
			             {
			            	 	 ps2=con.prepareStatement("update FAS_PAYMENT_MASTER set RECOUPED_STATUS=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and PAYMENT_DATE=to_date(?,'dd-MM-yyyy') and VOUCHER_NO=?");
			            	 	 ps2.setString(1,"N");
			            	 	 ps2.setInt(2,cmbAcc_UnitCode);
			            	 	 ps2.setInt(3,cmbOffice_code);
			            	 	 System.out.println("Recouped for voucher date :"+rs.getString("RECOUPED_FOR_VOUCHER_DATE"));			 
			            	 	 String dt=rs.getString("RECOUPED_FOR_VOUCHER_DATE");
			            	 	 ps2.setString(4,dt);
			            	 	 System.out.println("Recouped for voucher no :"+rs.getString("RECOUPED_FOR_VOUCHER_NO"));
			            	 	 ps2.setInt(5,rs.getInt("RECOUPED_FOR_VOUCHER_NO"));			            	 	 
			            	 	 err=ps2.executeUpdate();
			            	 	 if(err>0)
			            	 	 {
			            	 		 	System.out.println("Record Updated");
			            	 		 	bf_upd_count++;
			            	 	 }
			            	 	 else
			            	 		 	System.out.println("Record not Updated");
                	     }
			             System.out.println("bf_upd_count::: "+bf_upd_count);
			             if(bf_upd_count>0)
			             {
			            	 	 System.out.println("Inside Delete");
					             sql=" delete from FAS_IMPREST_RECOUP  		"+ 
		             			 " where ACCOUNTING_UNIT_ID=? 				"+
		             			 " and ACCOUNTING_FOR_OFFICE_ID=?			"+
		             			 " and CASHBOOK_YEAR=? 						"+
		             			 " and CASHBOOK_MONTH=?						"+
		             			 " and voucher_no=? 					    ";	                 			
		     	 
					         	 ps1=con.prepareStatement(sql);
					             ps1.setInt(1,cmbAcc_UnitCode);
					             ps1.setInt(2,cmbOffice_code);
					             ps1.setInt(3,txtCash_year);
					             ps1.setInt(4,txtCash_Month_hid);
					             ps1.setInt(5,voucher_no);
					             errcode=ps1.executeUpdate();  
					             if(errcode>0)
					            	 	System.out.println("Record Deleted");		
					             else
					            	 	System.out.println("ERR in Record Deletion");
			             }
	            }
	            catch(Exception e)
	            {
	            		 System.out.println("Err in select query1: "+e.getMessage());
	            		 System.out.println("No Records Available in Recoup table");
	            }
	            
	            if(errcode>0)
	            		 System.out.println("Recoup voucher has updated");
	            else
	            		 System.out.println("New Voucher");
	            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	            
	           
                        
	            try 
	            {   	 
	            		 System.out.println("Inside Updation ");
	            		 errcode=0;
	            		 //ps.close();
	                     //ps1.close();
	                     //ps2.close();
	            		 con.clearWarnings();
	                     con.setAutoCommit(false);
	                     System.out.println("Inside Updation one");
	                     ps=con.prepareStatement("update FAS_PAYMENT_MASTER set PAYMENT_TYPE=?,ACCOUNT_HEAD_CODE=?,BANK_ID=?,BRANCH_ID=?,ACCOUNT_NO=?,SUB_LEDGER_TYPE_CODE=?,SUB_LEDGER_CODE=?,CR_DR_INDICATOR=?,PAID_TO=?,REMARKS=?,TOTAL_AMOUNT=?,CREATED_BY_MODULE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,PAYMENT_STATUS=?,TOTAL_TRN_RECORDS=?,RECOUPED_STATUS=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and PAYMENT_DATE=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? and MODE_OF_CREATION='"+txtMode_of_creat+"'");
	                     System.out.println("update");
	                     ps.setString(1,"B");
	                     System.out.println("txtAcc_HeadCode "+txtAcc_HeadCode);
	                     ps.setInt(2,txtAcc_HeadCode);
	                     System.out.println("bank_id "+bank_id);
	                     ps.setInt(3,bank_id);
	                     System.out.println("branch_id "+branch_id);
	                     ps.setInt(4,branch_id);
	                     System.out.println("acc_no  "+acc_no);
	                     ps.setInt(5,acc_no);
	                     System.out.println("sub_type_code "+sub_type_code);
	                     ps.setInt(6,sub_type_code);
	                     System.out.println("txtOfficeId "+txtOfficeId);
	                     ps.setInt(7,txtOfficeId);	                    
	                     ps.setString(8,"CR");
	                     System.out.println("PaidTo "+PaidTo);
	                     ps.setString(9,PaidTo);
	                     System.out.println("remarks "+remarks);
	                     ps.setString(10,remarks);
	                     System.out.println("txtTotalAmt "+txtTotalAmt);
	                     ps.setDouble(11,txtTotalAmt);	            
	                     ps.setString(12,"BPF");
	                     System.out.println("update_user "+update_user);
	                     ps.setString(13,update_user);
	                     System.out.println("ts "+ts);
	                     ps.setTimestamp(14,ts);	  	                    
	                     ps.setString(15,"L");
	                     System.out.println("row_num "+row_num);
	                     ps.setInt(16,row_num);
	                     ps.setString(17,"N");	      
	                     System.out.println("cmbAcc_UnitCode11 "+cmbAcc_UnitCode);
	                     ps.setInt(18,cmbAcc_UnitCode);
	                     System.out.println("cmbOffice_code11 "+cmbOffice_code);
	                     ps.setInt(19,cmbOffice_code);
	                     System.out.println("txtCrea_date11 "+txtCrea_date);
	                     ps.setDate(20,txtCrea_date);
	                     System.out.println("txtCash_year11 "+txtCash_year);
	                     ps.setInt(21,txtCash_year);	
	                     System.out.println("txtCash_Month_hid11 "+txtCash_Month_hid);
	                     ps.setInt(22,txtCash_Month_hid);
	                     System.out.println("voucher_no11 "+voucher_no);
	                     ps.setInt(23,voucher_no);
	                   //  ps.setString(24,txtMode_of_creat);	   System.out.println("txtMode_of_creat"+txtMode_of_creat);                  
	                     errcode=ps.executeUpdate();
	                     if(errcode==0)
	                     {         		                         
		                         if(txtMode_of_creat.equals("I"))
		                        	 sendMessage(response,"--The Imprest Account Updation Failed-- ","ok");		                       
		                         else
		                        	 sendMessage(response,"The Temporary Advance Account Updation Failed ","ok");
	                     }
	                     else
	                     {
		                    	 System.out.println("FAS_PAYMENT_MASTER updated successfully");
		                    	 ps.close();
		                    	 errcode=0;
		                    	 sql_del=" delete from FAS_PAYMENT_TRANSACTION  "+ 
	                 			 " where ACCOUNTING_UNIT_ID=? 					"+
	                 			 " and ACCOUNTING_FOR_OFFICE_ID=?				"+
	                 			 " and CASHBOOK_YEAR=? 							"+
	                 			 " and CASHBOOK_MONTH=?							"+
	                 			 " and voucher_no=? 						    ";	                 			
	         	 
					         	 ps=con.prepareStatement(sql_del);
					             ps.setInt(1,cmbAcc_UnitCode);
					             ps.setInt(2,cmbOffice_code);
					             ps.setInt(3,txtCash_year);
					             ps.setInt(4,txtCash_Month_hid);
					             ps.setInt(5,voucher_no);
					             errcode=ps.executeUpdate();    
					             if(errcode==0)
			                     {         
				                       	 System.out.println("Can not delete old records for this voucher in transaction table");			                       
			                     }
					             else
					             {
							             System.out.println("FAS_PAYMENT_TRANSACTION records Deleted Successfully");
					            	 	 		                    	 
				                    	 String head_code[]=request.getParameterValues("head_code");
				                         String s_type[]=request.getParameterValues("s_type");
				                         String s_code[]=request.getParameterValues("s_code");
				                         String paid_to[]=request.getParameterValues("paid_to");
				                         String ref_no[]=request.getParameterValues("ref_no");
				                         String ref_dt[]=request.getParameterValues("ref_dt");
				                         String amount[]=request.getParameterValues("amount");
				                         String ch_dd[]=request.getParameterValues("ch_dd");
				                         String ch_no[]=request.getParameterValues("ch_no");
				                         String ch_date[]=request.getParameterValues("ch_date");
				                         String p_type[]=request.getParameterValues("p_type");
				                         String particular[]=request.getParameterValues("part");
				                         String purpose[]=request.getParameterValues("purpose");
				                         String recoup_vno[]=request.getParameterValues("recoup_vou_no");
				                         String recoup_vdt[]=request.getParameterValues("recoup_vou_dt");
				                         String recoup_vamt[]=request.getParameterValues("recoup_vou_amt");
				                         String recoup_sno[]=request.getParameterValues("sl_no");
				                         ps.close();
				                         ps=con.prepareStatement("insert into FAS_PAYMENT_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,BILL_NO,BILL_DATE,BANK_ID,BRANCH_ID,ACCOUNT_NO,CHEQUE_OR_DD,CHEQUE_DD_NO,CHEQUE_DD_DATE,PAID_TO,AMOUNT,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE,WD_PURPOSE_ID,TYPE_OF_PAYMENT) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				                         for(int i=0;i<head_code.length;i++)
				                         {						                        	
			                        	 			 h_code=0;stype=0;stype_code=0;cheque_num=0;p_id=0;
						                        	 amt=0;
						                        	 sno=sno+1;
	
						                        	 bill_no="";
						                        	 bill_date=null;dd_date=null;
						                        	 
						                        	 ps.setInt(1,cmbAcc_UnitCode);					    	                     
						    	                     ps.setInt(2,cmbOffice_code);					    	                     
						    	                     ps.setInt(3,txtCash_year);					    	                     
						    	                     ps.setInt(4,txtCash_Month_hid);
						    	                     ps.setInt(5,voucher_no);
						    	                     ps.setInt(6,sno);	
						    	                     try{h_code=Integer.parseInt(head_code[i]);}catch(Exception e){System.out.println("Err in head code selection:"+e.getMessage());}					    	                 
						    	                     ps.setInt(7,h_code);					    	                  
						    	                     ps.setString(8,"DR");					    	                     
						    	                     try{stype=Integer.parseInt(s_type[i]);}catch(Exception e){System.out.println("Err in sub ledger type selection:"+e.getMessage());}
						    	                     ps.setInt(9,stype);					    	                     
						    	                     try{stype_code=Integer.parseInt(s_code[i]);}catch(Exception e){System.out.println("Err in sub ledger type code selection:"+e.getMessage());}
						    	                     ps.setInt(10,stype_code);						    	                     
						    	                     try{bill_no=ref_no[i];}catch(Exception e){System.out.println("Err in bill number selection:"+e.getMessage());}
						    	                     ps.setString(11,bill_no);
						    	                    
						    	                     try
					                                 {							                                 
						    	                    	 	 if(!ref_dt[i].equalsIgnoreCase(""))
							                                 {
									                                 sd=ref_dt[i].split("/");
									                                 c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
									                                 d=c.getTime();
									                                 bill_date=new Date(d.getTime());
									                                 ps.setDate(12,bill_date);
							                                 }
							                                 else
							                                 {
							                                	 	 ps.setNull(12,java.sql.Types.DATE);
							                                 }						    	                    	 
					                                 }
					                                 catch(Exception e) {
					                                     	 System.out.println(e);
					                                 }					                                 
					                                 ps.setInt(13,bank_id);
					        	                     ps.setInt(14,branch_id);
					        	                     ps.setInt(15,acc_no);        	                     
					        	                     ps.setString(16,ch_dd[i]);					        	                     
					        	                     try{cheque_num=Integer.parseInt(ch_no[i]);}catch(Exception e){System.out.println("Err in cheque number selection:"+e.getMessage());}
						    	                     ps.setInt(17,cheque_num);					        	                     
						    	                     try
					                                 {						                                
							    	                    	 if(!ch_date[i].equalsIgnoreCase(""))
							                                 {
									                                 sd=ch_date[i].split("/");
									                                 c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
									                                 d=c.getTime();
									                                 dd_date=new Date(d.getTime());
									                                 ps.setDate(18,dd_date);
							                                 }
							                                 else
							                                 {
							                                	 	 ps.setNull(18,java.sql.Types.DATE);
							                                 }
						    	                    	 
					                                 }
					                                 catch(Exception e) {
					                                     	 System.out.println(e);
					                                 }					                                 
					                                 ps.setString(19,paid_to[i]);					                                 
					                                 try{amt=Double.parseDouble(amount[i]);}catch(Exception e){System.out.println("Err in cheque number selection:"+e.getMessage());}
						    	                     ps.setDouble(20,amt);
						    	                     ps.setString(21,particular[i]);
						    	                     ps.setString(22,update_user);
						                             ps.setTimestamp(23,ts);						                             
						                             try{p_id=Integer.parseInt(purpose[i]);}catch(Exception e){System.out.println("Err in purpose selection:"+e.getMessage());}
						                             ps.setInt(24,p_id);
						                             ps.setString(25,p_type[i]);						                             
						                             int k=ps.executeUpdate(); 						                             
						                             if(k>0)
						                             {
						                            	 	 System.out.println(" Payment Type :: "+p_type[i]);
								                             if(p_type[i].equals("R"))
								                             {
								                            	 	 
								                            	 	 System.out.println(" inside Recoup create ");
								                            	 	
								                            	 	 try{recoup_amt=Double.parseDouble(recoup_vamt[i]);}catch(Exception e){System.out.println("Err in recoup_vamt :: "+e.getMessage());recoup_amt=0;}
								                            	 	 try{recoup_vou=Integer.parseInt(recoup_vno[i]);}catch(Exception e){System.out.println("Err in recoup_vou :: "+e.getMessage());recoup_vou=0;}
								                            	 	 try{recoup_slno=Integer.parseInt(recoup_sno[i]);}catch(Exception e){System.out.println("Err in recoup_slno :: "+e.getMessage());recoup_slno=0;}
								                            	 	 try
							                            	 		 {
								                            	 			 sd=recoup_vdt[i].split("/");
											                                 c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
											                                 d=c.getTime();
											                                 recoup_dt=new Date(d.getTime());
											                                
							                            	 		 }
							                            	 		 catch(Exception e)
							                            	 		 {
							                            	 				 System.out.println(" Err in create date ::: "+e.getMessage());
							                            	 		 }
								                            	 	
							                            	 		 ps1=con.prepareStatement("insert into FAS_IMPREST_RECOUP(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,PAYMENT_DATE,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,SUB_LEDGER_CODE,TOTAL_AMOUNT,RECOUPED_FOR_VOUCHER_NO,RECOUPED_FOR_VOUCHER_SLNO,RECOUPED_FOR_VOUCHER_DATE,UPDATED_BY_USER_ID,UPDATED_DATE)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
							                            	 		 ps1.setInt(1,cmbAcc_UnitCode);
							                            	 		 ps1.setInt(2,cmbOffice_code);
							                            	 		 ps1.setDate(3,txtCrea_date);
							                            	 		 ps1.setInt(4,txtCash_year);
							                            	 		 ps1.setInt(5,txtCash_Month_hid);
							                            	 		 ps1.setInt(6,voucher_no);
							                            	 		 ps1.setInt(7,sno);
							                            	 		 ps1.setInt(8,stype_code);						                            	 		 
							                            	 		 ps1.setDouble(9,recoup_amt);						                            	 		 
							                            	 		 ps1.setInt(10,recoup_vou);
							                            	 		 ps1.setInt(11,recoup_slno);
							                            	 		 try
							                            	 		 {							                            	 			
											                                 ps1.setDate(12,recoup_dt);
							                            	 		 }
							                            	 		 catch(Exception e)
							                            	 		 {
							                            	 				 ps1.setNull(12,java.sql.Types.DATE);
							                            	 		 }
							                            	 		 ps1.setString(13,update_user);
										                             ps1.setTimestamp(14,ts);
								                            	 	 int j=ps1.executeUpdate();	
								                             	 	 if(j>0)
								                             	 	 {
								                             	 		 	 System.out.println(" inside Recoup update ");
								                             	 		 	 ps2=con.prepareStatement("update FAS_PAYMENT_MASTER set RECOUPED_STATUS=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and PAYMENT_DATE=? and VOUCHER_NO=?");
								                             	 		 	 ps2.setString(1,"Y");
								                             	 		 	 ps2.setInt(2,cmbAcc_UnitCode);
									                            	 		 ps2.setInt(3,cmbOffice_code);
									                            	 		 ps2.setDate(4,recoup_dt);
									                            	 		 ps2.setInt(5,recoup_vou);
									                            	 		 int cnt=ps2.executeUpdate();
									                            	 		 if(cnt>0)
									                            	 			 count++;
								                             	 	 }
								                            	 	 
								                             }
								                             else
							                            	 	 	  count++;
						                             }
						                            
				                         }
						                 String txtReferNO_edit="",txtRemak_edit="";         // for cross reference
						                 Date txtReferDate_edit=null; 
						                 String radAuth_MC="";
						                 int txtAuth_By=0;
						                 
						                 cs1=con.prepareCall("call FAS_CROSS_REFERENCE_PROC(?::NUMERIC,?,?,?,?,?,?,?,?,?,?::VARCHAR,?,?,?,?,?)") ; 
						                 cs1.setInt(1,cmbAcc_UnitCode);
						                 cs1.setInt(2,txtCash_year);
						                 cs1.setInt(3,txtCash_Month_hid);
						                 cs1.setInt(4,voucher_no);
						                 cs1.setInt(5,cmbOffice_code);
						                 cs1.setDate(6,txtCrea_date);
						                 cs1.setString(7,doc_type);
						                 cs1.setString(8,txtReferNO_edit);
						                 cs1.setDate(9,txtReferDate_edit);
						                 cs1.setString(10,txtRemak_edit);
						                 cs1.setInt(11,txtAuth_By);                                                      
						                 cs1.setString(12,"insert");
						                 cs1.registerOutParameter(13,java.sql.Types.NUMERIC);   
						                 cs1.setNull(13,java.sql.Types.NUMERIC);
						                 cs1.setString(14,update_user);
						                 cs1.setTimestamp(15,ts);     
						                 cs1.setString(16,radAuth_MC);
						                 cs1.execute();                                            // insertion into cross reference table
						                 errcode=cs1.getInt(13);
						                 System.out.println("cmbAcc_UnitCode22 "+cmbAcc_UnitCode);
						                 System.out.println("cmbOffice_code22 "+cmbOffice_code);
						                 System.out.println("txtCrea_date 22"+txtCrea_date);
						                 System.out.println("txtCash_year22 "+txtCash_year);
						                 System.out.println("txtCash_Month_hid22 "+txtCash_Month_hid);
						                 System.out.println("SQLCODE22:::"+errcode);
						                 if(errcode!=0)
						                 {   
						                   con.rollback();
						                   sendMessage(response,"The Cash Receipt Modification Failed ","ok");
						                   xml=xml+"<flag>failure</flag>";                          
						                 }
						                 
						                 con.commit();
						             }
			                         if(count==row_num)
			                         {
				                         System.out.println("b4 commit");
				                         con.commit();
				                         if(txtMode_of_creat.equals("I"))
				                        	 sendMessage(response,"The Imprest Account Voucher Number '"+voucher_no+"' has been Updated Successfully ","ok");
				                         else
				                        	 sendMessage(response,"The Temporary Advance Account Voucher Number '"+voucher_no+"' has been Updated Successfully ","ok");
			                         }
			                         else
			                         {
			                        	 System.out.println("b4 Rollback");
			                        	 con.rollback();
			                         }
	                  
	                     }
	            	 }
	                 catch(Exception e) 
	                 {
	                     try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
	                     e.getStackTrace();
	                     if(txtMode_of_creat.equals("I"))
	                    	 sendMessage(response,"***The Imprest Account Updation Failed*** ","ok");
	                     else
	                    	 sendMessage(response,"The Temporary Advance Account Updation Failed ","ok");
	                     System.out.println("Exception occur due to "+e);
	                     
	                 }
	                 finally
	                 {
	                     System.out.println("done");
	                     try{con.close();con.setAutoCommit(true);  }catch(SQLException sqle){}
	                 }
	                
                 
        }
    }
   
    private void sendMessage(HttpServletResponse response,String msg,String bType)
    {
        try
        {
            String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
            response.sendRedirect(url);
        }
        catch(IOException e)
        {
        }
    }
}
