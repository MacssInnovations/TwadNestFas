package Servlets.FAS.FAS1.TPA.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;
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

import java.text.DecimalFormat;

public class TPA_Acceptance_Cancel extends HttpServlet
{
    private String CONTENT_TYPE = "text/html; charset=windows-1252";
  
    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
      
    }
    public void doGet(HttpServletRequest request, 
            HttpServletResponse response) throws ServletException, IOException 
    {

  	
             /**
	       * Set Content Type 
	      */
	      PrintWriter out = response.getWriter();
	      response.setHeader("cache-control","no-cache");
	      String CONTENT_TYPE = "text/xml; charset=windows-1252";
	      response.setContentType(CONTENT_TYPE);
	      
	      
	      
	      /**
	       * Session Checking 
	      */
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
	      
	     
	      /**
	       * Variables Declaration 
	      */		        
	      Connection con=null;
	      PreparedStatement ps2=null,ps3=null;        
	      ResultSet rs2=null,rs3=null;
	      String sql="";
	        	      
	      /**
	       * Database Connection 
	      */
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
                    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                    Class.forName(strDriver.trim());
                    con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
            }
            catch(Exception e)
            {
                	System.out.println("Exception in opening connection :"+e);
            }
        
	    
	    
            int count=0,cmbAcc_UnitCode=0,cmbOffice_code=0,cashbook_year=0,cashbook_month=0,txtUnitId=0,accepted_slno=0;
            String xml=null,cmd="",cr_dr="";          
      
            /** Get Employee ID */
            try{cmd=request.getParameter("command");}
            catch(Exception e){System.out.println(e);}
            
            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
            
            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("cmbOffice_code "+cmbOffice_code);
            
            try{cashbook_year=Integer.parseInt(request.getParameter("txtCB_Year"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("cashbook_year "+cashbook_year);
            
            try{cashbook_month=Integer.parseInt(request.getParameter("txtCB_Month"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("cashbook_month "+cashbook_month);
            
            try{accepted_slno=Integer.parseInt(request.getParameter("accepted_slno"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("accepted_slno "+accepted_slno);
            
            try{txtUnitId=Integer.parseInt(request.getParameter("txtUnitId"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("txtUnitId "+txtUnitId);
            
            try{cr_dr=request.getParameter("CR_DR");}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("cr_dr "+cr_dr);
            
            if(cr_dr.equals("CR"))
           	 	cr_dr="TPAAC";
            else
           	 	cr_dr="TPAAD";
            int ver=0;
            System.out.println("cmd:::"+cmd);
            xml="<response>";
            if(cmd.equalsIgnoreCase("loadVoucher"))
            {     
                    xml=xml+"<command>loadVoucher</command>";
                    sql=" select  \n" + 
                    "   mst.VOUCHER_NO,mst.ORGIN_VOUCHER_NO,mst.TRF_ACCOUNTING_UNIT_ID," +
                    "to_char(mst.ORGIN_VOUCHER_DATE,'dd/mm/yyyy')as ORGIN_VOUCHER_DATE \n" + 
                    " from \n" + 
                    "   FAS_TPA_MASTER mst, \n" + 
                    "   FAS_CROSS_REFERENCE c  \n" + 
                    "where  \n" + 
                    "   mst.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and	 \n" + 
                    "   mst.ACCOUNTING_FOR_OFFICE_ID=c.ACCOUNTING_FOR_OFFICE_ID and	 \n" + 
                    "   mst.CASHBOOK_YEAR=c.CASHBOOK_YEAR and	 \n" + 
                    "   mst.CASHBOOK_MONTH=c.CASHBOOK_MONTH and  \n" + 
                    "   mst.VOUCHER_NO=c.VOUCHER_NO and	       \n" + 
                    "   mst.ACCOUNTING_UNIT_ID=? and \n" + 
                    "   mst.ACCOUNTING_FOR_OFFICE_ID=? and \n" + 
                    "   mst.CASHBOOK_YEAR=? and \n" + 
                    "   mst.CASHBOOK_MONTH=? and \n" + 
                    "   mst.STATUS='L' and   \n" + 
                    "   TPA_TYPE=? and  \n" + 
                    "   c.CHANGE_NO=0  and  + \n" + 
                    "   c.DOC_TYPE=?\n" + 
                    "and AUTHORIZED_TO='C' ";
                    System.out.println(" SQL :: "+sql);
                    try
                    {
                             ps2=con.prepareStatement(sql);
                             ps2.setInt(1,cmbAcc_UnitCode);
                             ps2.setInt(2,cmbOffice_code);
                             ps2.setInt(3,cashbook_year);
                             ps2.setInt(4,cashbook_month);
                             ps2.setString(5,cr_dr);
                             ps2.setString(6,cr_dr);
                             rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                            	 String year[]=rs2.getString("ORGIN_VOUCHER_DATE").split("/");
                            	 int journalMonth=Integer.parseInt(year[1]);
                            	 int journalYear=Integer.parseInt(year[2]);
                            	 System.out.println("year:"+year);
                            	 String ss="SELECT DISTINCT VERIFIED "+
									"	FROM FAS_JOURNAL_TRANSACTION "+
									"	WHERE ACCOUNTING_UNIT_ID="+ rs2.getInt("TRF_ACCOUNTING_UNIT_ID")+
									"	AND CASHBOOK_YEAR       ="+journalYear+
									"	AND Cashbook_Month      = "+journalMonth+
									"	And Verified            ='Y' "+
									"	AND VOUCHER_NO          ="+rs2.getInt("ORGIN_VOUCHER_NO");
                            	 System.out.println("query for verify in journal:::"+ss);
                            	 ps3=con.prepareStatement(ss);
                            	 rs3=ps3.executeQuery();
                            	 while(rs3.next())
                            	 {
                            		 System.out.println("verified=y in journal transaction,so cancel option is disabled");
                            		 ver++;
                            	 }
                            	 if(ver==0)
                            	 {
                            		 xml+= "<verifiedYes>"+ "verifiedNo" +"</verifiedYes>";
                            		 xml+= "<voucher_no>"+ rs2.getInt("voucher_no") +"</voucher_no>";	                                     			                		                
                                     count++;
                            	 }
                            	 else
                            	 {
                            		 xml+= "<verifiedYes>"+ "verifiedYes" +"</verifiedYes>";
                            		 xml+= "<voucher_no>"+ rs2.getInt("voucher_no") +"</voucher_no>";	                                     			                		                
                                     count++;
                            	 }
                                     
                             }					              
                             if(count==0)
                             {
                            	 xml+= "<verifiedYes>"+ "verifiedNOFlagNo" +"</verifiedYes>";
                                     xml+="<flag>NoData</flag>";
                             }
                             else               
                                     xml+="<flag>success</flag>";
                                         
                    }
                    catch(Exception e) 
                    {
                             System.out.println("Exception in loadVoucher..."+e);
                             xml+="<flag>"+e.getMessage()+"</flag>";
                    }                      
            }	
            else if(cmd.equalsIgnoreCase("loadVoucherDetails"))
            {     
            	DecimalFormat df=new DecimalFormat("#0.00");
	            	 xml=xml+"<command>loadVoucherDetails</command>";
	                 sql=" select \n" + 
	                 "   org_mst.ACCOUNTING_UNIT_ID,\n" + 
	                 "   org_mst.REASON_FOR_TRANSFER,\n" +
	                 "   org_mst.VOUCHER_NO," +
	                 "	 to_char(org_mst.VOUCHER_DATE,'dd-mm-yyyy') as org_voucher_date," +
	                 "   to_char(mst.VOUCHER_DATE,'dd-mm-yyyy') as VOUCHER_DATE," + 
	                 "   mst.PARTICULARS as mstparticulars,\n" + 
	                 "   trn.PARTICULARS ,\n" + 
	                 "   trn.ACCOUNT_HEAD_CODE,\n" + 
	                 "   acc_mst.account_head_desc,\n" + 
	                 "   trn.CR_DR_INDICATOR,\n" + 
	                 "   trn.SUB_LEDGER_TYPE_CODE, \n" + 
	                 "   trn.SUB_LEDGER_CODE,\n" + 
	                 "   trn.AMOUNT,\n" + 
	                 "   trn.PARTICULARS as trn_particulars,\n" + 
	                 "   SUB_LEDGER_TYPE_DESC,\n" + 
	                 "   unit_mst.accounting_unit_name\n" + 
	                 "from \n" + 
	                 "  FAS_TPA_MASTER mst inner join FAS_TPA_TRANSACTION trn on mst.ACCOUNTING_UNIT_ID=trn.ACCOUNTING_UNIT_ID and mst.ACCOUNTING_FOR_OFFICE_ID=trn.ACCOUNTING_FOR_OFFICE_ID and mst.CASHBOOK_MONTH=trn.CASHBOOK_MONTH and mst.CASHBOOK_YEAR=trn.CASHBOOK_YEAR and mst.VOUCHER_NO=trn.VOUCHER_NO\n" + 
	                 "  inner join FAS_TPA_MASTER org_mst on mst.ACCOUNTING_UNIT_ID=org_mst.TRF_ACCOUNTING_UNIT_ID and mst.VOUCHER_NO=org_mst.ACCEPTING_SLNO and mst.VOUCHER_DATE=org_mst.ACCEPTING_DATE\n" + 
	                 "  left outer join COM_MST_SL_TYPES sl_mst on trn.SUB_LEDGER_TYPE_CODE=sl_mst.SUB_LEDGER_TYPE_CODE\n" + 
	                 "  left outer join com_mst_account_heads acc_mst on trn.account_head_code=acc_mst.account_head_code\n" + 
	                 "  left outer join fas_mst_acct_units unit_mst on org_mst.ACCOUNTING_UNIT_ID=unit_mst.ACCOUNTING_UNIT_ID\n" + 
	                 "where\n" + 
	                 "  mst.ACCOUNTING_UNIT_ID=? and\n" + 
	                 "  mst.ACCOUNTING_FOR_OFFICE_ID=? and\n" + 
	                 "  mst.CASHBOOK_MONTH=? and\n" + 
	                 "  mst.CASHBOOK_YEAR=? and\n" + 
	                 "  mst.VOUCHER_NO=?\n" + 
	                 "order by SL_NO ";
	                 System.out.println(" SQL :: "+sql);
	                 try
	                 {
	                          ps2=con.prepareStatement(sql);
	                          ps2.setInt(1,cmbAcc_UnitCode);
	                          ps2.setInt(2,cmbOffice_code);
	                          ps2.setInt(3,cashbook_month);
	                          ps2.setInt(4,cashbook_year);
	                          ps2.setInt(5,accepted_slno);
	                          rs2=ps2.executeQuery();                                 
	                          while(rs2.next()) 
	                          {
	                        	  	  xml=xml+"<org_voucher_no>"+rs2.getString("VOUCHER_NO")+"</org_voucher_no>";
	                        	  	  xml=xml+"<org_voucher_date>"+rs2.getString("org_voucher_date")+"</org_voucher_date>";
                                      xml=xml+"<org_reason_for_transfer>"+rs2.getString("reason_for_transfer")+"</org_reason_for_transfer>";
                                      xml=xml+"<org_accounting_unit>"+rs2.getString("accounting_unit_name")+"</org_accounting_unit>";
                                      xml=xml+"<voucher_date>"+rs2.getString("voucher_date")+"</voucher_date>";
	                                  xml+="<account_head_code>"+rs2.getString("account_head_code")+"</account_head_code>";	                                     			                		                
	                                  xml+="<account_head_code_desc>"+rs2.getString("account_head_desc")+"</account_head_code_desc>";
	                                  xml+="<cr_dr_indicator>"+rs2.getString("cr_dr_indicator")+"</cr_dr_indicator>";
	                                  xml+="<amount>"+df.format(rs2.getBigDecimal("amount"))+"</amount>";
	                                  System.out.println("Sub code ::: "+rs2.getInt("sub_ledger_type_code"));
	                                  xml+="<sub_ledger_type_code>"+rs2.getInt("sub_ledger_type_code")+"</sub_ledger_type_code>";	                                  
	                                  xml+="<sub_ledger_code>"+rs2.getInt("sub_ledger_code")+"</sub_ledger_code>";
	                                 
	                                  if(!rs2.getString("account_head_code").equalsIgnoreCase("900301") || !rs2.getString("account_head_code").equalsIgnoreCase("620101"))
	                                  {
	                                  if(rs2.getString("particulars")==null)
                                                    xml+="<particulars>--</particulars>";
	                                  else
                                                	xml+="<particulars>"+rs2.getString("particulars")+"</particulars>";
	                                  }else{
	                                	  if(rs2.getString("mstparticulars")==null)
                                              xml+="<particulars>--</particulars>";
	                                	  else
                                          	xml+="<particulars>"+rs2.getString("mstparticulars")+"</particulars>"; 
	                                  }
	                                 if(rs2.getInt("sub_ledger_type_code")!=0 && rs2.getInt("sub_ledger_code")!=0)
		                              {
	                                                xml+="<sub_ledger_type_desc>"+rs2.getString("sub_ledger_type_desc")+"</sub_ledger_type_desc>";
	                                                SL_TYPE_CODE_NAME_GENERAL obj_gen=new SL_TYPE_CODE_NAME_GENERAL();
				                                    ResultSet rs_get=obj_gen.getResult_General(cmbAcc_UnitCode,cmbOffice_code,rs2.getInt("sub_ledger_type_code"),rs2.getInt("sub_ledger_code"),0);
				                                   
				                                    if(rs_get!=null)
				                                    { String slcheck="";
				                                            while(rs_get.next())
				                                            {
				                                            	slcheck=rs_get.getString("cname");
					                                           	
					                                              xml=xml+"<sub_ledger_desc>"+slcheck+"</sub_ledger_desc>";
				                                            }
				                                            rs_get.close();
				                                            if(slcheck=="")
				                                            {
				                                            	xml+= "<sub_ledger_desc>--</sub_ledger_desc>";  
				                                            }
				                                    }
				                                    else
				                                    {
                                                            System.out.println("null result set");
                                                            xml=xml+"<sub_ledger_desc>--</sub_ledger_desc>";
				                                    }
		                              }	
	                                  else
	                                  {
	                                	  			xml=xml+"<sub_ledger_desc>--</sub_ledger_desc>";
	                                	  			xml+="<sub_ledger_type_desc>--</sub_ledger_type_desc>";
	                                  } 
	                                  count++;
	                          }					              
	                          if(count==0)
	                                  xml+="<flag>NoData</flag>";					           
	                          else               
	                                  xml+="<flag>success</flag>";
                                         
                    }
                    catch(Exception e) 
                    {
                             System.out.println("Exception in loadVoucherDetails..."+e);
                             xml+="<flag>"+e.getMessage()+"</flag>";
                    }                      
            }
            
            xml=xml+"</response>";
            System.out.println(xml);
            out.println(xml);
            out.close();
    }
    public void doPost(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
    
         
         String strCommand="";
         Connection con=null;        
         PreparedStatement ps=null,ps1=null,ps2=null;
         String xml="";
         Statement st=null;
         ResultSet rs=null;
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
              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection              Class.forName(strDriver.trim());
              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
              st=con.createStatement();
         }
         catch(Exception e)
         {
              System.out.println("Exception in opening connection :"+e);
              //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

         }
                 
 //-----------------------------------------------------------------------------------------------        
        
        try
        {        
             strCommand=request.getParameter("Command");
             System.out.println("assign..here command..."+strCommand);
           
        }
        
        catch(Exception e) 
        {
             System.out.println("Exception in assigning..."+e);
        }
        
//-----------------------------------------------------------------------------------------------        
       
        if(strCommand.equalsIgnoreCase("Cancel")) 
        {
             System.out.println("Inside Cancel");
             String CONTENT_TYPE = "text/html; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
            
             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
             Calendar c;
             int txtAcc_HeadCode=0,cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,txtUnitId=0,txtAcHead=0,originate_Acc_unit=0,originate_office_id=0;
             int count=0,originated_year=0,originated_month=0,originated_slno=0,txtReason=0,orgin_no=0,accept_no=0,acceptedSerialNo=0;
             Date txtCrea_date=null,originated_date=null;
             String particulars=null,tpa_type=null,isAccept=null,notAccepting_reason=null,sql=null,cr_dr=null;
             Boolean flag=true;
             
             
             int orginVoucherNo=0;
             String orginVocherDate="";
             int acceptVoucherNo=0;
             String acceptVocherDate="";	 
             int orginUnitId=0;
             int orginOfficeId=0;
             boolean exeflag=false;String tpa_type_one="";
                                     // changes here
             String update_user=(String)session.getAttribute("UserId");
             long l=System.currentTimeMillis();
             Timestamp ts=new Timestamp(l);                      
             int errcode=0;
             
             
             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
                                
             try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             
             try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             
             try{originated_year=Integer.parseInt(request.getParameter("txtCB_Year"));}
             catch(Exception e){System.out.println("exception"+e );}
             
             try{originated_month=Integer.parseInt(request.getParameter("txtCB_Month"));}
             catch(Exception e){System.out.println("exception"+e );}
             
            
             
             try{originated_slno=Integer.parseInt(request.getParameter("originated_slno"));}
             catch(Exception e){System.out.println("exception"+e );}
            
             

             try{cr_dr=request.getParameter("Org_CR_DR");}
             catch(Exception e){System.out.println("exception"+e );}
             
             if(cr_dr.equals("CR")){
            	 tpa_type="TPAAC";
            	 tpa_type_one="TPAOC";
             }
             else{
            	 tpa_type="TPAAD";
            	 tpa_type_one="TPAOD";
             }
             try{acceptedSerialNo=Integer.parseInt(request.getParameter("accepted_slno"));}
             catch(Exception e){System.out.println("exception"+e );}
            
             String[] sd2=request.getParameter("txtCreate_Date").split("-");
             c=new GregorianCalendar(Integer.parseInt(sd2[2]),Integer.parseInt(sd2[1])-1,Integer.parseInt(sd2[0]));
             java.util.Date d2=c.getTime();
             txtCrea_date=new Date(d2.getTime());
                        
             
             
             try{txtCash_year=Integer.parseInt(request.getParameter("txtCB_Year"));}
             catch(Exception e){System.out.println("exception"+e );}
             
             try{txtCash_Month_hid=Integer.parseInt(request.getParameter("txtCB_Month"));}
             catch(Exception e){System.out.println("exception"+e );}
             
             
             
             try{txtAcHead=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             
           
             
             try{particulars=request.getParameter("particulars");}
             catch(NumberFormatException e){System.out.println("exception"+e );}
                                     
             //////////////////////Financial year calculation/////////////////////////////////
            
             //modifications on 29march2012 for checking journal table verify field
             
             try 
             {    
            	 System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
                 System.out.println("txtCash_Month_hid"+txtCash_Month_hid);
                 System.out.println("txtCash_year"+txtCash_year);
                 System.out.println("acceptVocherDate"+acceptVocherDate);
                 System.out.println("tpa_type"+tpa_type);
                 System.out.println("acceptedSerialNo"+acceptedSerialNo);
                 System.out.println("txtCrea_date"+txtCrea_date);
            	 
            	 sql="select ORGIN_VOUCHER_NO,to_char(ORGIN_VOUCHER_DATE,'dd/MM/yyyy') as ORGIN_VOUCHER_DATE,ACCEPT_VOUCHER_NO,to_char(ACCEPT_VOUCHER_DATE,'dd/MM/yyyy') as ACCEPT_VOUCHER_DATE,ORG_ACCOUNTING_UNIT_ID from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID=? and CASHBOOK_MONTH=? and CASHBOOK_YEAR=? and TPA_TYPE=? and VOUCHER_NO=?";
    	 	  	  	System.out.println("sql::"+sql);
                 ps=con.prepareStatement(sql);
                 ps.setInt(1,cmbAcc_UnitCode);
                 ps.setInt(2,txtCash_Month_hid);	 
                 ps.setInt(3,txtCash_year);	  
                 ps.setString(4,tpa_type);	   
                 ps.setInt(5,acceptedSerialNo);	  	 
                // ps.setDate(6,txtCrea_date);
            	 
            	rs=ps.executeQuery();
            
            	if(rs.next()){
             orginVoucherNo=rs.getInt("ORGIN_VOUCHER_NO");
             orginVocherDate=rs.getString("ORGIN_VOUCHER_DATE");
             acceptVoucherNo=rs.getInt("ACCEPT_VOUCHER_NO");
             acceptVocherDate=rs.getString("ACCEPT_VOUCHER_DATE");	 
             orginUnitId=rs.getInt("ORG_ACCOUNTING_UNIT_ID");
           //  orginOfficeId=rs.getInt("ACCOUNTING_FOR_OFFICE_ID");
             exeflag=true;
            	}
             rs.close();
             ps.close();
             
             if(exeflag){
             String[] orginjournaldate=orginVocherDate.split("/");
             String[] acceptjournaldate=acceptVocherDate.split("/");
             
            
             
             
             System.out.println("orginVoucherNo"+orginVoucherNo);
             System.out.println("orginVocherDate"+orginVocherDate);
             System.out.println("acceptVoucherNo"+acceptVoucherNo);
             System.out.println("acceptVocherDate"+acceptVocherDate);
             System.out.println("orginUnitId"+orginUnitId);
            // System.out.println("orginOfficeId"+orginOfficeId);
             
             int orginCashMonth=Integer.parseInt(orginjournaldate[1]);
             int orginCashYear=Integer.parseInt(orginjournaldate[2]);
             int acceptCashMonth=Integer.parseInt(acceptjournaldate[1]);
             int acceptCashYear=Integer.parseInt(acceptjournaldate[2]);
             
             System.out.println("orginCashMonth"+orginCashMonth);
             System.out.println("orginCashYear"+orginCashYear);
             System.out.println("acceptCashMonth"+acceptCashMonth);
             System.out.println("acceptCashYear"+acceptCashYear);
             
           String date="";  
            
             for(int j=0;j<2;j++)
             {
            sql="update FAS_JOURNAL_MASTER set JOURNAL_STATUS='C',UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?";
            ps=con.prepareStatement(sql);
            ps.setString(1,update_user);
            ps.setTimestamp(2, ts);
            if(j==0)
            {
            	
            ps.setInt(3,orginUnitId);
          //  ps.setInt(4,orginOfficeId);	 
            ps.setInt(4,orginCashYear);	  
            ps.setInt(5,orginCashMonth);	  
            ps.setInt(6,orginVoucherNo);
            }else{
            	ps.setInt(3,cmbAcc_UnitCode);
            //    ps.setInt(4,cmbOffice_code);	 
                ps.setInt(4,acceptCashYear);	  
                ps.setInt(5,acceptCashMonth);	  
                ps.setInt(6,acceptVoucherNo);
            }
            
            ps.executeUpdate();
            System.out.println("Inside loop"+j);
             }
            ps.close(); 
          
             //sql="update FAS_TPA_MASTER set ACCEPTANCE_STATUS=NULL,ORGIN_VOUCHER_NO=NULL,ACCEPT_VOUCHER_NO=NULL,UPDATED_BY_USERID=?,UPDATED_DATE=?,ORGIN_VOUCHER_DATE=to_date(?,'dd/MM/yyyy'),ACCEPT_VOUCHER_DATE=to_date(?,'dd/MM/yyyy') where ACCOUNTING_UNIT_ID=? and CASHBOOK_MONTH=? and CASHBOOK_YEAR=? and TPA_TYPE=? and ACCEPTING_DATE=?";
   	 	  	
//             sql="update FAS_TPA_MASTER set ACCEPTANCE_STATUS=NULL,ORGIN_VOUCHER_NO=NULL,ACCEPT_VOUCHER_NO=NULL,UPDATED_BY_USERID=?,UPDATED_DATE=?,ORGIN_VOUCHER_DATE=NULL,ACCEPT_VOUCHER_DATE=NULL where ACCOUNTING_UNIT_ID=? and ACCEPTING_SLNO=? and TRF_ACCOUNTING_UNIT_ID=? and TPA_TYPE=? and ORGIN_VOUCHER_NO=?";
             
            sql="update FAS_TPA_MASTER set ACCEPTING_SLNO=NULL,ACCEPTING_DATE=NULL,ACCEPTANCE_STATUS=NULL,ORGIN_VOUCHER_NO=NULL,ACCEPT_VOUCHER_NO=NULL,UPDATED_BY_USERID=?,UPDATED_DATE=?,ORGIN_VOUCHER_DATE=NULL,ACCEPT_VOUCHER_DATE=NULL where ACCOUNTING_UNIT_ID=? and ACCEPTING_SLNO=? and TRF_ACCOUNTING_UNIT_ID=? and TPA_TYPE=? and ORGIN_VOUCHER_NO=?";

             ps=con.prepareStatement(sql);
             ps.setString(1,update_user);
             ps.setTimestamp(2, ts);
           //  ps.setString(3,date);
            // ps.setString(4,date);
             ps.setInt(3,orginUnitId);
             System.out.println("orginUnitId===>"+orginUnitId);
             ps.setInt(4,acceptedSerialNo);
             System.out.println("acceptedSerialNo===>"+acceptedSerialNo);
             ps.setInt(5,cmbAcc_UnitCode);	  
             System.out.println("cmbAcc_UnitCode===>"+cmbAcc_UnitCode);
             ps.setString(6,tpa_type_one);	   
             System.out.println("tpa_type_one===>"+tpa_type_one);
             ps.setInt(7,orginVoucherNo);
             System.out.println("orginVoucherNo===>"+orginVoucherNo);
             
             
             int checkOne=ps.executeUpdate();
             ps.close();
             
             sql="update FAS_TPA_MASTER set ACCEPTANCE_STATUS='N',DATE_OF_ACCEPTANCE=NULL,ORGIN_VOUCHER_NO=NULL,ACCEPT_VOUCHER_NO=NULL,STATUS='C',UPDATED_BY_USERID=?,UPDATED_DATE=?,ORGIN_VOUCHER_DATE=to_date(?,'dd/MM/yyyy'),ACCEPT_VOUCHER_DATE=to_date(?,'dd/MM/yyyy') where ACCOUNTING_UNIT_ID=? and CASHBOOK_MONTH=? and CASHBOOK_YEAR=? and TPA_TYPE=? and VOUCHER_NO=?";
	 	  	  	
             ps=con.prepareStatement(sql);
             ps.setString(1,update_user);
             ps.setTimestamp(2, ts);
             ps.setString(3,date);
          	 ps.setString(4,date);
             ps.setInt(5,cmbAcc_UnitCode);
             ps.setInt(6,txtCash_Month_hid);	 
             ps.setInt(7,txtCash_year);	  
             ps.setString(8,tpa_type);	  
             ps.setInt(9,acceptedSerialNo);	  
             int checkSec=ps.executeUpdate();
               ps.close();
             
               
               System.out.println("checkOne===>"+checkOne);
               System.out.println("checkSec====>"+checkSec);
               
               if(checkOne>0 && checkSec>0 )
               System.out.println("Completed");
               sendMessage(response,"The TPA Acceptance Cancel Successfully completed ","ok");
               
               
               
             }      
             
             else{
            	System.out.println("here no result"); 
             }
             }
             catch(Exception e) 
             {
            	 System.out.print(e);
		              try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
		              e.getStackTrace();
		              sendMessage(response,"Accepting Sl.No. Creation Failed ","ok");
             }
             finally
             {
		              System.out.println("done");
		              try{con.setAutoCommit(true);con.close(); }catch(SQLException sqle){}
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
