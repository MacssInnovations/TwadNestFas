package Servlets.FAS.FAS1.JournalSystem.servlets;

import Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_DETAILS;

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

public class Imprest_Journal_Edit extends HttpServlet 
{
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
    }
    public void doPost(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
         String strCommand="";
         Connection con=null;
         ResultSet rs=null;
         CallableStatement cs=null,cs1=null;
         PreparedStatement ps=null;
         String xml="";
        
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
				  ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection				  Class.forName(strDriver.trim());
				  con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
         }
         catch(Exception e)
         {
        	 	  System.out.println("Exception in opening connection :"+e);	             
         }
        
         try 
         {
        
	          	  strCommand=request.getParameter("Command");
	          	  System.out.println("assign..here command..."+strCommand);
           
         }
         catch(Exception e) 
         {
        	  	  System.out.println("Exception in assigning..."+e);
         }
         if(strCommand.equalsIgnoreCase("Add")) 
	     {
	              String CONTENT_TYPE = "text/html; charset=windows-1252";
	              response.setContentType(CONTENT_TYPE);
	              xml="<response><command>Add</command>";
	              Calendar c;
	              int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,txtJournalVou_No=0,cmbPayVocNo=0;
	              int Total_TRN_Rec=0;
	              String  txtCheque_NO="",txtCB_REF_TYPE="";
	              Date txtCrea_date=null,txtCheque_date=null,cmbPay_date=null;
	              String txtRemarks="";
	          
	              int cmbMas_SL_type=0,cmbMas_SL_Code=0;
	              String txtMode_of_creat="",txtCreat_By_Module="GJV",doc_type="";
	              double dep_rate=0;                           // changes here
	              String update_user=(String)session.getAttribute("UserId");
	              long l=System.currentTimeMillis();
	              Timestamp ts=new Timestamp(l);
	
	            //For Banking Purpose
	           
	               
	              try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	              catch(NumberFormatException e){System.out.println("exception"+e );}
	              System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
	            
	              try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	              catch(NumberFormatException e){System.out.println("exception"+e );}
	              System.out.println("cmbOffice_code "+cmbOffice_code);
	            
	          
	              String[] sd=request.getParameter("txtCrea_date").split("/");
	              c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	              java.util.Date d=c.getTime();
	              txtCrea_date=new Date(d.getTime());
	              System.out.println("txtCrea_date "+txtCrea_date);
	            
	              System.out.println("b4 getting month and year");
	              try{txtCash_year=Integer.parseInt(sd[2]);}
	              catch(Exception e){System.out.println("exception"+e );}
	              System.out.println("txtCash_year "+txtCash_year);
                
	              try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
	              catch(Exception e){System.out.println("exception"+e );}
	              System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
	            
	            
		          try{txtJournalVou_No=Integer.parseInt(request.getParameter("txtJournalVou_No"));}
		          catch(Exception e){System.out.println("exception"+e );}
		          System.out.println("txtJournalVou_No "+txtJournalVou_No);
		          
		          try{cmbPayVocNo=Integer.parseInt(request.getParameter("cmbPayVocNo"));}
		          catch(Exception e){System.out.println("exception"+e );}
		          System.out.println("cmbPayVocNo "+cmbPayVocNo);
		          
		          
		          String[] sd1=request.getParameter("txtCrea_date").split("/");
	              c=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
	              java.util.Date d1=c.getTime();
	              cmbPay_date=new Date(d1.getTime());
	              System.out.println("cmbPay_date "+cmbPay_date);
	              
		          txtCheque_NO=request.getParameter("txtCheque_NO");
		          System.out.println("txtCheque_NO "+txtCheque_NO);
	            
		          if(!request.getParameter("txtCheque_date").equalsIgnoreCase(""))
		          {
			            sd=request.getParameter("txtCheque_date").split("/");
			            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
			            d=c.getTime();
			            txtCheque_date=new Date(d.getTime());
		          }
		          System.out.println("txtCheque_date "+txtCheque_date);
		        
		          try{cmbMas_SL_Code=Integer.parseInt(request.getParameter("cmbMas_SL_Code"));}
		          catch(Exception e){System.out.println("exception"+e );}
		          System.out.println("cmbMas_SL and office "+cmbMas_SL_type+" "+cmbMas_SL_Code);//+" "+cmbMas_offid);
	            
		          try{cmbMas_SL_type=Integer.parseInt(request.getParameter("cmbMas_SL_type"));}
	              catch(Exception e){System.out.println("exception"+e );}
	             
	              if(cmbMas_SL_type==68)
	              {	txtMode_of_creat="I";doc_type="IJV";}
	              else
	              {	txtMode_of_creat="T";doc_type="TJV";}
	           
	         
		          txtRemarks=request.getParameter("txtRemarks");
		          System.out.println("txtRemarks "+txtRemarks);
	          
		          try 
	              {   
	                    con.clearWarnings();
	                    con.setAutoCommit(false);
	                    System.out.println("inside proc");
	                    String No_TRN_Rec[]=request.getParameterValues("H_code");
	                    Total_TRN_Rec=No_TRN_Rec.length;//Integer.parseInt(No_TRN_Rec);
	                    System.out.println(Total_TRN_Rec+" Total_TRN_Rec");
	                    
                        cs=con.prepareCall("{call FAS_IMP_JOURNAL_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}") ;                          cs.setInt(1,cmbAcc_UnitCode);
                        cs.setInt(2,cmbOffice_code);
                        cs.setInt(3,txtCash_year);
                        cs.setInt(4,txtCash_Month_hid);
                        cs.setInt(5,txtJournalVou_No);
                        cs.setDate(6,txtCrea_date);
                                     
                        cs.setInt(7,cmbMas_SL_type);
                        cs.setInt(8,cmbMas_SL_Code);
                        cs.setDouble(9,dep_rate);
                        cs.setString(10,txtCheque_NO);
                        cs.setDate(11,txtCheque_date);
                        cs.setString(12,txtCB_REF_TYPE);
                        
                        cs.setInt(13,Total_TRN_Rec);
                        cs.setString(14,txtRemarks);
                        cs.setString(15,txtMode_of_creat);
                        cs.setString(16,txtCreat_By_Module);
                        cs.setString(17,"update");                     
                        cs.registerOutParameter(5,java.sql.Types.NUMERIC);
                        cs.registerOutParameter(18,java.sql.Types.NUMERIC);  
                        cs.setString(19,update_user);
                        cs.setTimestamp(20,ts);
                        System.out.println("b4 exe ");
                        cs.execute();
                        int errcode=cs.getInt(18);
	                     
	                     
	                    System.out.println("SQLCODE:::"+errcode);
	                    if(errcode!=0)
	                    {         
		                        System.out.println("redirect");
		                        if(txtMode_of_creat.equals("I"))
		                        	sendMessage(response,"The Imprest Journal Voucher Number Creation Failed ","ok");
		                        else
			                        sendMessage(response,"The Temporary Advance Journal Voucher Number Creation Failed ","ok");
		                        xml=xml+"<flag>failure</flag>";                          
	                    }
	                    else
	                    {  
	                         	 String sql_del="delete from FAS_JOURNAL_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?" +
	                         	 "and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=?  and VOUCHER_NO=?  ";
	                         
	                         	 ps=con.prepareStatement(sql_del);
		                         ps.setInt(1,cmbAcc_UnitCode);
		                         ps.setInt(2,cmbOffice_code);
		                         ps.setInt(3,txtCash_year);
		                         ps.setInt(4,txtCash_Month_hid);
		                         ps.setInt(5,txtJournalVou_No);
		                         ps.executeUpdate();                        // deletion from transaction table
		                         ps.close();
	                         
		                         String Grid_H_code[]=request.getParameterValues("H_code");
		                         String Grid_CR_DR_type[]=request.getParameterValues("CR_DR_type");
		                         String Grid_SL_type[]=request.getParameterValues("SL_type");
		                         String Grid_SL_code[]=request.getParameterValues("SL_code");
		                       
		                         String Grid_Bill_No[]=request.getParameterValues("Bill_NO");
		                         String Grid_Bill_date[]=request.getParameterValues("Bill_date");
		                         String Grid_Bill_type[]=request.getParameterValues("Bill_type");
		                         
		                         String Grid_Agree_No[]=request.getParameterValues("Agree_No");
		                         String Grid_Agree_date[]=request.getParameterValues("Agree_date");
		                         
		                         String Grid_sl_amt[]=request.getParameterValues("sl_amt");
		                         String Grid_particular[]=request.getParameterValues("particular");
		                         
		                         String sql="insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID, " +
		                         "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
		                         "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, BILL_NO,BILL_TYPE,AGREEMENT_NO,AGREEMENT_DATE," +
		                         "BILL_DATE,CHEQUE_OR_DD ,CHEQUE_DD_NO, CHEQUE_DD_DATE,  " +
		                         "AMOUNT, PARTICULARS,CB_REF_NO,CB_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE ) "+
		                         "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;
	                          
	                         
	                         
		                         int SL_NO=1,txtAcc_HeadCode=0,cmbSL_Code=0,cmbSL_type=0,txtCB_REF_NO=cmbPayVocNo;
		                         Date txtBill_Date=null,txtAgree_Date=null,txtCheque_DD_date=null,txtCB_REF_DATE=cmbPay_date;
		                         double txtsub_Amount=0;                                  
		                         String rad_sub_CR_DR="",txtBill_no="",txtBill_Type="",txtAgree_No="",txtParticular="";
		                         String txtCheque_DD="",txtCheque_DD_NO="";  
	
		                         ps=con.prepareStatement(sql);
		                         for(int k=0;k<Grid_H_code.length;k++) 
		                         {
		                                   try{txtAcc_HeadCode=Integer.parseInt(Grid_H_code[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
		                                   rad_sub_CR_DR=Grid_CR_DR_type[k];
		                                   
		                                   try{cmbSL_type=Integer.parseInt(Grid_SL_type[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
		                                   try{cmbSL_Code=Integer.parseInt(Grid_SL_code[k]);}catch(Exception e){System.out.println("exception in trans "+e);}
		                                   System.out.println("Grid_H_code[k] "+Grid_H_code[k]);
		                                   System.out.println("Grid_CR_DR_type[k] "+Grid_CR_DR_type[k]);
		                                   System.out.println("Grid_SL_type[k]"+Grid_SL_type[k]+"u");
		                                   System.out.println("Grid_SL_code[k]"+Grid_SL_code[k]+"from here"+cmbSL_Code);
		                                   txtBill_no=Grid_Bill_No[k];
		                                    
		                                   txtBill_Type=Grid_Bill_type[k];		                                    
		                                   if(!Grid_Bill_date[k].equalsIgnoreCase(""))
		                                   {
				                                    sd=Grid_Bill_date[k].split("/");
				                                    c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
				                                    d=c.getTime();
				                                    txtBill_Date=new Date(d.getTime());
		                                   }
		                                   txtAgree_No=Grid_Agree_No[k];
		                                   if(!Grid_Agree_date[k].equalsIgnoreCase(""))
		                                   {
				                                    sd=Grid_Agree_date[k].split("/");
				                                    c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
				                                    d=c.getTime();
				                                    txtAgree_Date=new Date(d.getTime());
		                                   }
		                                    
		                                   System.out.println("txtBill_no..."+txtBill_no);
		                                   System.out.println("txtBill_Type..."+txtBill_Type);
		                                   System.out.println("txtBill_Date..."+txtBill_Date);
		                                   System.out.println("txtAgree_No..."+txtAgree_No);
		                                   System.out.println("txtAgree_Date..."+txtAgree_Date);
		                                    
		                                   
		                                   System.out.println("amount");
		                                   txtsub_Amount=Double.parseDouble(Grid_sl_amt[k]);
		                                   txtParticular=Grid_particular[k];
		                                   System.out.println("amount");
		                                   System.out.println("Grid_sl_amt[k] "+Grid_sl_amt[k]);
		                                   System.out.println("Grid_particular[k] "+Grid_particular[k]);
		                                   
		                                   ps.setInt(1,cmbAcc_UnitCode);
		                                   ps.setInt(2,cmbOffice_code);
		                                   ps.setInt(3,txtCash_year);
		                                   ps.setInt(4,txtCash_Month_hid);
		                                   ps.setInt(5,txtJournalVou_No);
		                                   ps.setInt(6,SL_NO);
		                                   ps.setInt(7,txtAcc_HeadCode);
		                                   ps.setString(8,rad_sub_CR_DR);
		                                   ps.setInt(9,cmbSL_type);
		                                   ps.setInt(10,cmbSL_Code);
		                                   ps.setString(11,txtBill_no);
		                                   ps.setString(12,txtBill_Type);
		                                   ps.setString(13,txtAgree_No);
		                                   ps.setDate(14,txtAgree_Date);
		                                   ps.setDate(15,txtBill_Date);
		                                   
		                                   ps.setString(16,txtCheque_DD);
		                                   ps.setString(17,txtCheque_DD_NO);
		                                   ps.setDate(18,txtCheque_DD_date);
		                                  
		                                   ps.setDouble(19,txtsub_Amount);
		                                   ps.setString(20,txtParticular);
		                                   ps.setInt(21,txtCB_REF_NO);
		                                   ps.setDate(22,txtCB_REF_DATE);
		                                   ps.setString(23,update_user);
		                                   ps.setTimestamp(24,ts);
		                                   SL_NO++;
		                                   ps.executeUpdate(); 
		                                   
		                                   txtAcc_HeadCode=0;
		                                   rad_sub_CR_DR="";
		                                   cmbSL_type=0;
		                                   cmbSL_Code=0;
		                                   txtCheque_DD="";
		                                   txtCheque_DD_NO="";
		                                   txtCheque_DD_date=null;
		                                   txtAgree_No="";
		                                   txtAgree_Date=null;
		                                   txtsub_Amount=0;
		                                   txtParticular="";
	                             }
	                             ps.close();
	                             
	                             
		                         String txtReferNO_edit="",txtRemak_edit="",txtRefdate="";         // for cross reference
		                         Date txtReferDate_edit=null; 
		                         String radAuth_MC="";
		                         int txtAuth_By=0;
		                         
		                         System.out.println("txtReferDate_edit "+txtReferDate_edit);
		                         cs1=con.prepareCall("call FAS_CROSS_REFERENCE_PROC(?::NUMERIC,?,?,?,?,?,?,?,?,?,?::VARCHAR,?,?,?,?,?)") ; 
		                         cs1.setInt(1,cmbAcc_UnitCode);
		                         cs1.setInt(2,txtCash_year);
		                         cs1.setInt(3,txtCash_Month_hid);
		                         cs1.setInt(4,txtJournalVou_No);
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
		                         cs1.execute();   
		                         
		                         errcode=cs1.getInt(13);
		                         System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
		                         System.out.println("cmbOffice_code "+cmbOffice_code);
		                         System.out.println("txtCrea_date "+txtCrea_date);
		                         System.out.println("txtCash_year "+txtCash_year);
		                         System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
		                         System.out.println("SQLCODE:::"+errcode);
		                         if(errcode!=0)
		                         {   
				                           con.rollback();
				                           if(txtMode_of_creat.equals("I"))
				                        	   sendMessage(response,"The  Imprest Voucher Number Modification Failed ","ok");
				                           else
				                        	   sendMessage(response,"The  Temporary Advance Voucher Number Modification Failed ","ok");
				                        	   
				                           xml=xml+"<flag>failure</flag>";                          
		                         }
		                         
		                         con.commit();
		                         if(txtMode_of_creat.equals("I"))
		                        	 	   sendMessage(response,"The  Imprest Voucher Number '"+txtJournalVou_No+"' has been Modified Successfully ","ok");
		                         else
		                        	 	   sendMessage(response,"The  Temporary Advance Voucher Number '"+txtJournalVou_No+"' has been Modified Successfully ","ok");
	                     }
	                    
	                 }
	                 
	                 catch(Exception e) 
	                 {
	                     try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
	                     if(txtMode_of_creat.equals("I"))
	                    	 	 sendMessage(response,"The  Imprest Voucher Number Modification Failed ","ok");
	                     else
	                    	 	 sendMessage(response,"The  Temporary Advance Voucher Number Modification Failed ","ok");
	                     System.out.println("Exception occur due to "+e);
	                 }
	                 finally
	                 {
	                     System.out.println("done");
	                     try{con.setAutoCommit(true);  }catch(SQLException sqle){}
	                 }
	                 
	        }
    }
    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
            Connection con=null;
            ResultSet rs=null,rs2=null,rs3=null,rs4=null;
            PreparedStatement ps=null,ps2=null,ps3=null,ps4=null;
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            Calendar c;
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
                      ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                      Class.forName(strDriver.trim());
                      con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
             }
             catch(Exception e)
             {
		              System.out.println("Exception in opening connection :"+e);
		              //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

             }
             response.setContentType(CONTENT_TYPE);
             response.setHeader("Cache-Control","no-cache");
             PrintWriter out = response.getWriter();
             String strCommand="",txtMode_of_creat="",doc_type="",text1="";
             String xml="",mode="";
             try 
             {
		              strCommand=request.getParameter("Command");
		              System.out.println("assign.. command..."+strCommand);
             }
             catch(Exception e) 
             {
            	 	  System.out.println("Exception in assigning..."+e);
             }
             int cmbAcc_UnitCode=0,cmbOffice_code=0,txtAcc_HeadCodee=0;
             int count=0;
             Date txtCrea_date=null;
             try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
               
             try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("cmbOffice_code "+cmbOffice_code);
            
             int txtJournalVou_No=0,txtCash_year=0,txtCash_Month=0,cmbSL_Code=0;
            
             try{txtJournalVou_No=Integer.parseInt(request.getParameter("txtJournalVou_No"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("txtJournalVou_No "+txtJournalVou_No);
            
             try
             {
	                    String[] sd=request.getParameter("txtCrea_date").split("/");
	                    c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	                    java.util.Date d=c.getTime();
	                    txtCrea_date=new Date(d.getTime());
             }
             catch(Exception e)
             {
            	 		txtCrea_date=null;
            	 		System.out.println("Err in txtCrea_date "+e.getMessage());
             }
             
             try{txtCash_year=Integer.parseInt(request.getParameter("txtCB_Year"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("txtCash_year "+txtCash_year);
             try{txtCash_Month=Integer.parseInt(request.getParameter("txtCB_Month"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("txtCash_Month "+txtCash_Month);
             
             try{cmbSL_Code=Integer.parseInt(request.getParameter("cmbSL_Code"));
             }catch(Exception e){System.out.println("Exception in cmbSL_Code ");}
             
             try{txtMode_of_creat=request.getParameter("txtMode_of_creat");}
             catch(Exception e){System.out.println("Err in Journal Type ::: "+e.getMessage());}
             
             
            /* if(text1.equals("Imprest Journal-Payment"))
             {
            	 txtMode_of_creat="I";
            	 doc_type="IJV";
            	 mode="0";
             }
             else if(text1.equals("Imprest Journal-SelfCheque"))
             {
            	 txtMode_of_creat="I";
            	 doc_type="SC";
            	 mode="IT";
             }
             else if(text1.equals("Temporary Adv.  Journal-Payment"))
             {
            	 txtMode_of_creat="T";
            	 doc_type="TJV";
            	 mode="0";
             }
             else if(text1.equals("Temporary Adv.  Journal-SelfCheque"))
             {
            	 txtMode_of_creat="T";
            	 doc_type="SC";
            	 mode="IT";
             }  */
             if(txtMode_of_creat.equals("68"))
             {	txtMode_of_creat="I";doc_type="IJV";}
             else
             {	txtMode_of_creat="T";doc_type="TJV";}
            
             if(strCommand.equalsIgnoreCase("load_Voucher_No")) 
             {	                  	                  
	                  xml="<response><command>load_Voucher_No</command>";	                  
	                  System.out.println("txtCrea_date "+txtCrea_date);                 
                      try 
                      {                                
                                ps=con.prepareStatement("select i.VOUCHER_NO from FAS_JOURNAL_MASTER i,FAS_CROSS_REFERENCE c where " +
                                 " i.ACCOUNTING_UNIT_ID=?  and i.VOUCHER_DATE=? and i.JOURNAL_STATUS!='C'  and CREATED_BY_MODULE='GJV'" +
                                 " and MODE_OF_CREATION='"+txtMode_of_creat+"'" +
                                 " and i.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and i.ACCOUNTING_FOR_OFFICE_ID=c.ACCOUNTING_FOR_OFFICE_ID " +
                                 " and i.VOUCHER_DATE=c.ORIGINAL_DATE and i.VOUCHER_NO=c.VOUCHER_NO " +
                                 " and c.AUTHORIZED_TO='M' and DOC_TYPE=? and CHANGE_NO=0");
                                ps.setInt(1,cmbAcc_UnitCode);
                               // ps.setInt(2,cmbOffice_code);
                                ps.setDate(2,txtCrea_date);
                              //  ps.setString(4,txtMode_of_creat);
                                ps.setString(3,doc_type);
                                System.out.println("doc_type"+doc_type);
                                rs=ps.executeQuery();
                                                                
                                while(rs.next())
                                {
                                System.out.println("ins while");
	                                	xml=xml+"<Rec_No>"+rs.getInt("VOUCHER_NO")+"</Rec_No>";
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
	                    	  	System.out.println("catch..HERE.in load head code."+e);
	                            xml=xml+"<flag>failure</flag>";
                      }
                      
             }
             
             else if(strCommand.equalsIgnoreCase("load_Voucher_Details")) 
             {                    
                     xml="<response><command>load_Voucher_Details</command>";                     
                     System.out.println("txtCrea_date "+txtCrea_date);                    
                     try 
                     {
		                        String sql="select distinct \n" + 
		                        "  mst.CASHBOOK_YEAR,\n" + 
		                        "  mst.CASHBOOK_MONTH,\n" + 
		                        "  mst.CHEQUE_NO,\n" + 
		                        "  mst.JOURNAL_TYPE_CODE,\n" + 
		                        "  mst.SUB_LEDGER_CODE,\n" + 
		                        "  to_char(mst.CHEQUE_DATE,'DD/MM/YYYY')as CHEQ_date,\n" + 
		                        "  mst.REMARKS,\n" + 
		                        "  mst.TOTAL_TRN_RECORDS,\n" + 
		                        "  trn.CB_REF_NO,\n" + 
		                        "  to_char(trn.CB_REF_DATE,'DD/MM/YYYY')as CB_REF_DATE\n" + 
		                        "from  \n" + 
		                        "  FAS_JOURNAL_MASTER mst,\n" + 
		                        "  FAS_JOURNAL_TRANSACTION trn\n" + 
		                        "where \n" + 
		                        "  mst.ACCOUNTING_UNIT_ID=trn.ACCOUNTING_UNIT_ID and\n" + 
		                        "  mst.ACCOUNTING_FOR_OFFICE_ID=trn.ACCOUNTING_FOR_OFFICE_ID and\n" + 
		                        "  mst.CASHBOOK_YEAR=trn.CASHBOOK_YEAR and\n" + 
		                        "  mst.CASHBOOK_MONTH=trn.CASHBOOK_MONTH and\n" + 
		                        "  mst.VOUCHER_NO=trn.VOUCHER_NO and\n" + 
		                        "  mst.ACCOUNTING_UNIT_ID=? and \n" + 
		                     //   "  mst.ACCOUNTING_FOR_OFFICE_ID=? and \n" + 
		                        "  mst.VOUCHER_DATE=? and \n" + 
		                        "  mst.VOUCHER_NO=? and \n" + 
		                        "  mst.MODE_OF_CREATION=? and \n" + 
		                        "  mst.CREATED_BY_MODULE='GJV' \n" + 
		                        "  \n" + 
		                        "  ";       
                           		ps=con.prepareStatement(sql);
	                            ps.setInt(1,cmbAcc_UnitCode);
	                           // ps.setInt(2,cmbOffice_code);
	                            ps.setDate(2,txtCrea_date);
	                            ps.setInt(3,txtJournalVou_No);
	                            ps.setString(4,txtMode_of_creat);
	                            rs=ps.executeQuery();
	                            if(rs.next())
	                            {
			                           xml=xml+"<flag>success</flag>";
			                           xml=xml+"<cheq_No>"+rs.getString("CHEQUE_NO")+
			                           "</cheq_No><cheq_Date>"+rs.getString("CHEQ_date")+
			                           "</cheq_Date><No_TRN_Rec>"+rs.getInt("TOTAL_TRN_RECORDS")+
			                           "</No_TRN_Rec><Remak>"+rs.getString("REMARKS")+
			                           "</Remak><Mas_SL_type>"+rs.getInt("JOURNAL_TYPE_CODE")+
			                           "</Mas_SL_type><Mas_SL_code>"+rs.getInt("SUB_LEDGER_CODE")+			                           
			                           "</Mas_SL_code><CB_Ref_No>"+rs.getInt("CB_REF_NO")+
			                           "</CB_Ref_No><CB_Ref_Date>"+rs.getString("CB_REF_DATE")+"</CB_Ref_Date>";
	                            }
                           
	                            //System.out.println("in b/w here"+rs.getInt("CASHBOOK_MONTH"));
	                            System.out.println("in b/w here");
                           
	                            ps2=con.prepareStatement("select ACCOUNT_HEAD_CODE ,CR_DR_INDICATOR ,SUB_LEDGER_TYPE_CODE ," +
	                            	   "SUB_LEDGER_CODE,BILL_NO, BILL_TYPE, AGREEMENT_NO, to_char(AGREEMENT_DATE,'DD/MM/YYYY') as agree_date," +
	                            	   "to_char(BILL_DATE,'DD/MM/YYYY') as b_date  " +
	                            	   ",trim(to_char(AMOUNT,'99999999999999.99')) as  AMOUNT, PARTICULARS  from FAS_JOURNAL_TRANSACTION where ACCOUNTING_UNIT_ID=? and " +
	                            	   " CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?");
	                            ps2.setInt(1,cmbAcc_UnitCode);
	                         //   ps2.setInt(2,cmbOffice_code);
	                            ps2.setString(2,rs.getString("CASHBOOK_YEAR"));
	                            ps2.setInt(3,rs.getInt("CASHBOOK_MONTH"));
	                            ps2.setInt(4,txtJournalVou_No);
	                            rs2=ps2.executeQuery();
	                            while(rs2.next()) 
	                            {
			                           xml=xml+"<AHcode>"+rs2.getInt("ACCOUNT_HEAD_CODE")+"</AHcode>";
			                           ps3=con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
			                           ps3.setInt(1,rs2.getInt("ACCOUNT_HEAD_CODE"));
			                           rs3=ps3.executeQuery();
			                           if(rs3.next())
			                           xml=xml+"<AHdesc>"+rs3.getString("ACCOUNT_HEAD_DESC")+"</AHdesc>";
			                           ps3.close();
			                           rs3.close();
			                           xml=xml+"<CR_DR_ind>"+rs2.getString("CR_DR_INDICATOR")+"</CR_DR_ind><SL_Type>"+rs2.getInt("SUB_LEDGER_TYPE_CODE")+"</SL_Type>";
                           
			                           if(rs2.getInt("SUB_LEDGER_TYPE_CODE")!=0)
			                           {
					                           System.out.println("take SL DESC");
					                           ps3=con.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
					                           ps3.setInt(1,rs2.getInt("SUB_LEDGER_TYPE_CODE"));
					                           rs3=ps3.executeQuery();
					                           if(rs3.next())
					                           xml=xml+"<SL_Desc>"+rs3.getString("SUB_LEDGER_TYPE_DESC")+"</SL_Desc>";
			                           }
			                           else
			                           {
				                               xml=xml+"<SL_Desc>"+null+"</SL_Desc>";   // it also return null value
				                               System.out.println("else part  23");
			                           }                          
			                           rs3.close();                            
			                           ps3.close();                           
			                           xml=xml+"<SL_Code>"+rs2.getInt("SUB_LEDGER_CODE")+"</SL_Code>";                               
			                           if(rs2.getInt("SUB_LEDGER_TYPE_CODE")!=0)
			                           {
				                               SL_TYPE_CODE_NAME_DETAILS obj_det=new SL_TYPE_CODE_NAME_DETAILS();
				                               ResultSet rs_det=obj_det.getResult_Details(cmbAcc_UnitCode,cmbOffice_code,rs2.getInt("SUB_LEDGER_TYPE_CODE"),rs2.getString("SUB_LEDGER_CODE"),0);
				                               if(rs_det!=null)
				                               {
					                                   if(rs_det.next())
					                                   {
						                                       System.out.println(rs_det.getString("cname"));
						                                       xml=xml+"<desc_type>"+rs_det.getString("cname")+"</desc_type>";
					                                   }
					                                   rs_det.close();
				                               }
				                               else
				                                   	   System.out.println("null result set");
			                           }
			                           else
			                           {
			                                   xml=xml+"<desc_type>"+null+"</desc_type>";  
			                           }
			                           xml=xml+"<Bill_NO>"+rs2.getString("BILL_NO")+"</Bill_NO>"+"<Bill_date>"+rs2.getString("b_date")+"</Bill_date>"+"<Bill_type>"
											   +rs2.getString("BILL_TYPE")+"</Bill_type>"+"<Agree_No>"+rs2.getString("AGREEMENT_NO")+"</Agree_No>"+
											   "<Agree_date>"+rs2.getString("agree_date")+"</Agree_date>"+
											   "<sub_amount>"+rs2.getString("AMOUNT") +
											   "</sub_amount><sub_part>"+rs2.getString("PARTICULARS")+"</sub_part>";
                                }
                           
                     }
                     catch(Exception e)
                     {
	                    	 	System.out.println("catch..HERE.in failure to retrieve."+e);
	                    	 	xml="<response><command>load_Voucher_Details</command><flag>failure</flag>";
                     }
                     
                }
	            else if(strCommand.equalsIgnoreCase("loadSLType"))
	            {
	             	  
	             	  System.out.println("loadSLType");
	            	  xml=xml+"<response><command>loadSLType</command>";
	             	  try	
	             	  {
	     			        	 
			                      String sql=
		                    	  "select   \n" + 
		                          "    *  \n" + 
		                          "from   \n" + 
		                          "(  \n" + 
		                          "   select    \n" + 
		                          "    voucher_no,   \n" + 
		                          "    payment_date,   \n" + 
		                          "    sl_type_code,   \n" + 
		                          "    sl_code,   \n" +
		                          "	   (pay_amt - rec_amt) as remaining_amt," + 
		                          "    (pay_amt - jour_amt - rec_amt)  as bal  \n" + 
		                          "   from    \n" + 
		                          "   (           \n" + 
		                          "      select    \n" + 
		                          "         voucher_no,    \n" + 
		                          "         payment_date,    \n" + 
		                          "         sl_type_code,    \n" + 
		                          "         sl_code,    \n" + 
		                          "         pay_amt as pay_amt,    \n" + 
		                          "         sum(jour_amt) as jour_amt ,    \n" + 
		                          "         sum(rec_amt) as rec_amt    \n" + 
		                          "       from    \n" + 
		                          "       (   \n" + 
		                          "             select    \n" + 
		                          "              voucher_no,    \n" + 
		                          "              payment_date,    \n" + 
		                          "              sl_type_code,   \n" + 
		                          "              sl_code,    \n" + 
		                          "              pay_amt,    \n" + 
		                          "              jour_amt,   \n" + 
		                          "              0 as rec_amt   \n" + 
		                          "             from    \n" + 
		                          "             (                                                                                                           \n" + 
		                          "                  select * from                     \n" + 
		                          "                  (     \n" + 
		                          "                       select     \n" + 
		                          "                           t.SUB_LEDGER_TYPE_CODE as j_sl_type_code,    \n" + 
		                          "                           t.SUB_LEDGER_CODE as j_sl_code,     \n" + 
		                          "                           t.cb_ref_no,                    \n" + 
		                          "                           to_char(t.cb_ref_date,'DD/MM/YY') as cb_ref_date,     \n" + 
		                          "                           amount as jour_amt    \n" + 
		                          "                       from     \n" + 
		                          "                          fas_journal_master m,     \n" + 
		                          "                          fas_journal_transaction  t                                                                                   \n" + 
		                          "                       where                              \n" + 
		                          "                          m.accounting_unit_id =?					              \n" + 
		                        //  "                          and m.accounting_for_office_id =?  \n" + 
		                          "                          and m.cashbook_month =?		                      \n" + 
		                          "                          and m.cashbook_year=?                  	 	      \n" + 
		                          "                          and m.VOUCHER_NO =?                                \n" + 
		                          "                          and m.mode_of_creation='"+txtMode_of_creat+"'                            \n" + 
		                          "                          and m.JOURNAL_STATUS='L' and  \n" + 
		                          "                          m.accounting_unit_id=t.accounting_unit_id and     \n" + 
		                          "                          m.accounting_for_office_id = t.accounting_for_office_id and     \n" + 
		                          "                          m.cashbook_month=t.cashbook_month and     \n" + 
		                          "                          m.cashbook_year=t.cashbook_year and     \n" + 
		                          "                          m.voucher_no = t.voucher_no and                               \n" + 
		                          "                          t.cr_dr_indicator='CR'                         \n" + 
		                          "                  )j  \n" + 
		                          "                  inner join  \n" + 
		                          "                  (  \n" + 
		                          "                          select					                                              \n" + 
		                          "                             m.voucher_no,     \n" + 
		                          "                             to_char(m.PAYMENT_DATE,'DD/MM/YY')as payment_date,                                                                    \n" + 
		                          "                             t.SUB_LEDGER_TYPE_CODE as sl_type_code,                                            \n" + 
		                          "                             t.SUB_LEDGER_CODE as sl_code,                                                      \n" + 
		                          "                             t.AMOUNT as pay_amt                                                                  \n" + 
		                          "                          from						                              \n" + 
		                          "                             fas_payment_master	m,               	      \n" + 
		                          "                             fas_payment_transaction t       \n" + 
		                          "                          where 											      \n" + 
		                          "                             m.accounting_unit_id= t.accounting_unit_id        \n" + 
		                          "                             and m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID         \n" + 
		                          "                             and m.cashbook_month=t.cashbook_month                 \n" + 
		                          "                             and m.cashbook_year=t.cashbook_year                   \n" + 
		                          "                             and m.voucher_no=t.voucher_no        \n" + 
		                          "                             and m.MODE_OF_CREATION='"+txtMode_of_creat+"'                                                                                                                           \n" + 
		                          "                             and m.accounting_unit_id=?     \n" + 
		                          "                             and m.PAYMENT_STATUS='L'                           \n" + 
		                          "                  )p  \n" + 
		                          "                  on     \n" + 
		                          "                          j.CB_REF_NO=p.voucher_no and   \n" + 
		                          "                          j.CB_REF_DATE=p.payment_date and  \n" + 
		                          "                          j_sl_type_code=p.sl_type_code and  \n" + 
		                          "                          j_sl_code=p.sl_code    \n" + 
		                          "                  left outer join                         \n" + 
		                          "                  (     \n" + 
		                          "                     select     \n" + 
		                          "                         t.SUB_LEDGER_TYPE_CODE as r_sl_type_code,    \n" + 
		                          "                         t.SUB_LEDGER_CODE as r_sl_code,                                                                                                                           \n" + 
		                          "                         m.RECEIVABLE_VOUCHER_NO,                                                                                                                         \n" + 
		                          "                         to_char(m.RECEIVABLE_VOUCHER_DATE,'DD/MM/YY') as RECEIVABLE_VOUCHER_DATE,                                                                                                                                                                                                                                                 \n" + 
		                          "                         t.AMOUNT as rec_amt    \n" + 
		                          "                     from     \n" + 
		                          "                         fas_receipt_master m,     \n" + 
		                          "                         fas_receipt_transaction  t                                                                                   \n" + 
		                          "                     where    \n" + 
		                          "                         m.accounting_unit_id=t.accounting_unit_id and     \n" + 
		                          "                         m.accounting_for_office_id = t.accounting_for_office_id and     \n" + 
		                          "                         m.cashbook_month=t.cashbook_month and     \n" + 
		                          "                         m.cashbook_year=t.cashbook_year and     \n" + 
		                          "                         m.receipt_no = t.receipt_no and     \n" + 
		                          "                         m.MODE_OF_CREATION='"+txtMode_of_creat+"' and                                                                                                                          \n" + 
		                          "                         m.accounting_unit_id=? and    \n" + 
		                          "                         m.RECEIPT_STATUS='L'                        \n" + 
		                          "                   )r                                                              \n" + 
		                          "                   on     \n" + 
		                          "                        p.voucher_no = r.RECEIVABLE_VOUCHER_NO and     \n" + 
		                          "                        p.payment_date = r.RECEIVABLE_VOUCHER_DATE and     \n" + 
		                          "                        p.sl_type_code = r.r_sl_type_code and     \n" + 
		                          "                        p.sl_code = r.r_sl_code  \n" + 
		                          "           )           \n" + 
		                          "       ) group by voucher_no, payment_date, sl_type_code, sl_code , pay_amt            \n" + 
		                          "        order by payment_date, voucher_no, sl_type_code, sl_code                    \n" + 
		                          "   )  \n" + 
		                          ")x   \n" + 
		                          "left outer join									                                                              \n" + 
		                          "(  select										      \n" + 
		                          "      e.EMPLOYEE_ID,                                   \n" + 
		                          "      e.EMPLOYEE_NAME||'.'||e.EMPLOYEE_INITIAL||'-'|| d.DESIGNATION as ENAME              \n" + 
		                          "   from      \n" + 
		                          "      HRM_MST_EMPLOYEES e,      \n" + 
		                          "      HRM_EMP_CURRENT_POSTING c,      \n" + 
		                          "      HRM_MST_DESIGNATIONS d       \n" + 
		                          "   where 												      \n" + 
		                          "      c.DESIGNATION_ID=d.DESIGNATION_ID 				      \n" + 
		                          "      and e.EMPLOYEE_ID=c.EMPLOYEE_ID                             \n" + 
		                          ") y														      \n" + 
		                          "on 														      \n" + 
		                          "    x.sl_code = y.employee_id	      ";
		                          /*"where bal>0 ";*/
	     			        	  System.out.println(" SQL :::"+sql);	     			        	 
	     			        	  ps=con.prepareStatement(sql);                                                                                   
	     	                      ps.setInt(1,cmbAcc_UnitCode);System.out.println(cmbAcc_UnitCode);
	     	                     // ps.setInt(2,cmbOffice_code);System.out.println(cmbOffice_code);
	     	                      ps.setInt(2,txtCash_Month);System.out.println(txtCash_Month);
	     	                      ps.setInt(3,txtCash_year);System.out.println(txtCash_year);
	     	                      ps.setInt(4,txtJournalVou_No);    System.out.println(txtJournalVou_No);
	     	                   //   ps.setString(6,txtMode_of_creat);
	     	                  //    ps.setString(7,txtMode_of_creat);	     	                                                                      
	     	                      ps.setInt(5,cmbAcc_UnitCode); System.out.println(cmbAcc_UnitCode);
	     	                  //    ps.setString(9,txtMode_of_creat);	     
	     	                      ps.setInt(6,cmbAcc_UnitCode);    System.out.println(cmbAcc_UnitCode);                 
	     			        	  rs=ps.executeQuery();			        	  
	     			        	  while(rs.next())
	     			        	  {
	     			        		  		 System.out.println("inside while");
	     			        		   		 xml +="<SUB_LEDGER_CODE>"+rs.getString("sl_code")+"</SUB_LEDGER_CODE>";
	     			        		   		 xml +="<ENAME>"+rs.getString("ENAME")+"</ENAME>";		
	     			        		   		 xml+="<Balance>"+rs.getDouble("remaining_amt")+"</Balance>";
	     			        		   		 count++;
	     			        	  }
	     			        	  if(count>0)
	     			        		  		 xml +="<flag>success</flag>";
	     			        	  else
	     			        		  		 xml +="<flag>failure</flag>";
	             	  }
	             	  catch(Exception e)
	             	  {
	             		  		  System.out.println("Err in loadSLType:   "+e.getMessage());
	             		  		  xml +="<flag>failure</flag>";
	             	  }
                }
	            /*else if(strCommand.equalsIgnoreCase("loadPaymentTotal"))
	            {
	                  xml=xml+"<response><command>loadPaymentTotal</command>";
	                  try   
	                  {
	                                              //rs.close();
			                      String sql="select  \n" + 
			                      "*  \n" + 
			                      "from  \n" + 
			                      "( \n" + 
			                      "  select  \n" + 
			                      "    voucher_no,  \n" + 
			                      "    payment_date,  \n" + 
			                      "    sl_type_code,  \n" + 
			                      "    sl_code,  \n" + 
			                      "    decode(sum(pay_amt),null,0,sum(pay_amt)) as pay_amt,  \n" + 
			                      "    decode(sum(jour_amt),null,0,sum(jour_amt)) as jour_amt ,  \n" + 
			                      "    decode(sum(rec_amt),null,0,sum(rec_amt)) as rec_amt  \n" + 
			                      "  from  \n" + 
			                      "  ( \n" + 
			                      "    select  \n" + 
			                      "      voucher_no,  \n" + 
			                      "      payment_date,  \n" + 
			                      "      sl_type_code, \n" + 
			                      "      sl_code,  \n" + 
			                      "      pay_amt,  \n" + 
			                      "      jour_amt, \n" + 
			                      "      0 as rec_amt \n" + 
			                      "    from  \n" + 
			                      "    (                                                                                                         \n" + 
			                      "      select * from   \n" + 
			                      "      (         \n" + 
			                      "        select             \n" + 
			                      "          t.SUB_LEDGER_TYPE_CODE as j_sl_type_code,  \n" + 
			                      "          t.SUB_LEDGER_CODE as j_sl_code,   \n" + 
			                      "          t.cb_ref_no,                  \n" + 
			                      "          to_char(t.cb_ref_date,'DD/MM/YY') as cb_ref_date,   \n" + 
			                      "          amount as jour_amt  \n" + 
			                      "        from   \n" + 
			                      "          fas_journal_master m,   \n" + 
			                      "          fas_journal_transaction  t                                                                                 \n" + 
			                      "        where  \n" + 
			                      "          m.accounting_unit_id = ?                                \n" + 
			                      "          and m.accounting_for_office_id = ?                                         \n" + 
			                      "          and m.cashbook_month =?                           \n" + 
			                      "          and m.cashbook_year=?                               \n" + 
			                      "          and m.VOUCHER_NO =?                              \n" + 
			                      "          and t.SUB_LEDGER_CODE=?                             \n" + 
			                      "          and m.mode_of_creation='I'                          \n" + 
			                      "          and m.journal_status='L'         \n" + 
			                      "          and m.accounting_unit_id=t.accounting_unit_id and   \n" + 
			                      "          m.accounting_for_office_id = t.accounting_for_office_id and   \n" + 
			                      "          m.cashbook_month=t.cashbook_month and   \n" + 
			                      "          m.cashbook_year=t.cashbook_year and   \n" + 
			                      "          m.voucher_no = t.voucher_no and             \n" + 
			                      "          t.cr_dr_indicator='CR' \n" + 
			                      "      )j\n" + 
			                      "      inner join\n" + 
			                      "      (\n" + 
			                      "        select      \n" + 
			                      "          m.voucher_no,\n" + 
			                      "          to_char(m.PAYMENT_DATE,'DD/MM/YY')as payment_date,                                                                  \n" + 
			                      "          t.SUB_LEDGER_TYPE_CODE as sl_type_code,                                          \n" + 
			                      "          t.SUB_LEDGER_CODE as sl_code,                                                    \n" + 
			                      "          t.AMOUNT   as pay_amt                                                                \n" + 
			                      "        from                                                    \n" + 
			                      "          fas_payment_master    m,                       \n" + 
			                      "          fas_payment_transaction t     \n" + 
			                      "        where                                                 \n" + 
			                      "          m.accounting_unit_id =?                                                                    \n" + 
			                      "          and m.accounting_unit_id= t.accounting_unit_id      \n" + 
			                      "          and m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID       \n" + 
			                      "          and m.cashbook_month=t.cashbook_month               \n" + 
			                      "          and m.cashbook_year=t.cashbook_year                 \n" + 
			                      "          and m.voucher_no=t.voucher_no  \n" + 
			                      "      )p\n" + 
			                      "      on  \n" + 
			                      "        j.CB_REF_NO=p.voucher_no and       \n" + 
			                      "        j.CB_REF_DATE=p.payment_date and\n" + 
			                      "        p.payment_date = j.CB_REF_DATE and   \n" + 
			                      "        p.sl_type_code = j_sl_type_code and   \n" + 
			                      "        p.sl_code = j_sl_code   \n" + 
			                      "      right outer join  \n" + 
			                      "      (   \n" + 
			                      "        select   \n" + 
			                      "          t.SUB_LEDGER_TYPE_CODE as r_sl_type_code,  \n" + 
			                      "          t.SUB_LEDGER_CODE as r_sl_code,                                                                                                                         \n" + 
			                      "          m.RECEIVABLE_VOUCHER_NO,                                                                                                                       \n" + 
			                      "          to_char(m.RECEIVABLE_VOUCHER_DATE,'DD/MM/YY') as RECEIVABLE_VOUCHER_DATE,                                                                                                                                                                                                                                               \n" + 
			                      "          t.AMOUNT as rec_amt  \n" + 
			                      "        from   \n" + 
			                      "          fas_receipt_master m,   \n" + 
			                      "          fas_receipt_transaction  t                                                                                 \n" + 
			                      "        where  \n" + 
			                      "          m.accounting_unit_id=t.accounting_unit_id and   \n" + 
			                      "          m.accounting_for_office_id = t.accounting_for_office_id and   \n" + 
			                      "          m.cashbook_month=t.cashbook_month and   \n" + 
			                      "          m.cashbook_year=t.cashbook_year and   \n" + 
			                      "          m.receipt_no = t.receipt_no and   \n" + 
			                      "          m.MODE_OF_CREATION='I' and                                                                                                                        \n" + 
			                      "          m.accounting_unit_id=? and  \n" + 
			                      "          m.RECEIPT_STATUS='L'         \n" + 
			                      "      )r                                                            \n" + 
			                      "      on   \n" + 
			                      "        p.voucher_no = r.RECEIVABLE_VOUCHER_NO and   \n" + 
			                      "        p.payment_date = r.RECEIVABLE_VOUCHER_DATE and   \n" + 
			                      "        p.sl_type_code = r.r_sl_type_code and   \n" + 
			                      "        p.sl_code = r.r_sl_code   \n" + 
			                      "      where voucher_no is not null          \n" + 
			                      "    )      \n" + 
			                      "  )group by voucher_no, payment_date, sl_type_code, sl_code           \n" + 
			                      "  order by payment_date, voucher_no, sl_type_code, sl_code           \n" + 
			                      ") x \n" + 
			                      "left outer join                                                          \n" + 
			                      "(  \n" + 
			                      "  select                                            \n" + 
			                      "    e.EMPLOYEE_ID,                                 \n" + 
			                      "    e.EMPLOYEE_NAME||'.'||e.EMPLOYEE_INITIAL||'-'|| d.DESIGNATION as ENAME            \n" + 
			                      "  from    \n" + 
			                      "    HRM_MST_EMPLOYEES e,    \n" + 
			                      "    HRM_EMP_CURRENT_POSTING c,    \n" + 
			                      "    HRM_MST_DESIGNATIONS d     \n" + 
			                      "  where                                                     \n" + 
			                      "    c.DESIGNATION_ID=d.DESIGNATION_ID                     \n" + 
			                      "    and e.EMPLOYEE_ID=c.EMPLOYEE_ID                           \n" + 
			                      ")y                                                            \n" + 
			                      "on                                                             \n" + 
			                      "  x.sl_code = y.employee_id ";
	    	                  
		                          System.out.println(" SQL :::"+sql);
		                          ps=con.prepareStatement(sql);   
		                          ps.setInt(1,cmbAcc_UnitCode);
	     	                      ps.setInt(2,cmbOffice_code);
	     	                      ps.setInt(3,txtCash_Month);
	     	                      ps.setInt(4,txtCash_year);
	     	                      ps.setInt(5,txtJournalVou_No); 
	     	                      ps.setInt(6,cmbSL_Code);
	     	                      ps.setInt(7,cmbAcc_UnitCode);                          
	     	                      ps.setInt(8,cmbAcc_UnitCode);		                          
		                          rs=ps.executeQuery();                                   
		                          if(rs.next())
		                          {
		    		                     System.out.println("pay_amt :"+rs.getDouble("pay_amt"));        
		    		                     System.out.println("jour_amt :"+rs.getDouble("jour_amt"));        
		    		                     System.out.println("rec_amt :"+rs.getDouble("rec_amt")); 
		    		                     xml=xml+"<pay_amt>"+rs.getDouble("pay_amt")+"</pay_amt>";
		    		                     xml=xml+"<jour_amt>"+rs.getDouble("jour_amt")+"</jour_amt>";
		    		                     xml=xml+"<rec_amt>"+rs.getDouble("rec_amt")+"</rec_amt>";
		    		                     count++;
		                          }
		                          if(count>0)
		                                 xml +="<flag>success</flag>";
		                          else
		                                 xml +="<flag>failure</flag>";
		              }
		              catch(Exception e)
		              {
                                 System.out.println("Err in loadPaymentTotal:   "+e.getMessage());
                                 xml +="<flag>failure</flag>";
		              }
	                           
	            }*/
             	xml=xml+"</response>";
             	System.out.println(xml);
             	out.println(xml);
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
