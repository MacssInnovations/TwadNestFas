package Servlets.FAS.FAS1.TDA.servlets;

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

public class SingleEmployee_Many_units extends HttpServlet
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
	      PreparedStatement ps2=null,ps=null,ps7=null;        
	      ResultSet rs2=null,rs=null;
	      String sql="";
	      String[] sd={};     
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
        
	    
            int count=0,AccUnitId=0,cmbOffice_code=0,voucher_no=0,sub_code=0;
            String xml=null,cmd="";          
            Date txtCrea_date=null;
            
            try{cmd=request.getParameter("command");}
            catch(Exception e){System.out.println(e);}
            
            try{AccUnitId=Integer.parseInt(request.getParameter("txtUnitId"));
            System.out.println("AccUnitId "+AccUnitId);
            
            }
            catch(Exception e){System.out.println(e);}
            
            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
            System.out.println("cmbOffice_code "+cmbOffice_code);
            
            }
            catch(Exception e){System.out.println("cmbOffice_code"+e.getMessage());}
            
            try
            {
           sd=request.getParameter("txtCrea_date").split("/");
            Calendar c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            java.util.Date d=c.getTime();
            txtCrea_date=new Date(d.getTime());
            System.out.println("txtCrea_date "+txtCrea_date);
            }catch(Exception e){System.out.println("Eception :: "+e.getMessage());}
            
            try{voucher_no=Integer.parseInt(request.getParameter("voucher_no"));}
            catch(Exception e){System.out.println("voucher_no"+e.getMessage());}
            
            System.out.println("cmd:::"+cmd);
            xml="<response>"; 
            if(cmd.equalsIgnoreCase("load_voucher_no"))
            {
            	
            	ResultSet rs7=null;
            	System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&load_voucher_no^^^^^^^^^^^^^^^^^^^^^^^^^^^");
            	    xml=xml+"<command>load_voucher_no</command>";
            	    try	
 {
				    //  sql ="select * from(select mst.VOUCHER_NO  , mst.ACCOUNTING_UNIT_ID, mst.ACCOUNTING_FOR_OFFICE_ID, mst.VOUCHER_DATE   from    FAS_JOURNAL_MASTER mst,   FAS_JOURNAL_TRANSACTION trn   where    mst.ACCOUNTING_UNIT_ID=trn.ACCOUNTING_UNIT_ID and   mst.ACCOUNTING_FOR_OFFICE_ID=trn.ACCOUNTING_FOR_OFFICE_ID and   mst.CASHBOOK_YEAR=trn.CASHBOOK_YEAR and  mst.CASHBOOK_MONTH=trn.CASHBOOK_MONTH and   mst.VOUCHER_NO=trn.VOUCHER_NO and  mst.ACCOUNTING_UNIT_ID=? and  mst.ACCOUNTING_FOR_OFFICE_ID=? and mst.VOUCHER_DATE=? and  mst.JOURNAL_STATUS='L' and   trn.ACCOUNT_HEAD_CODE=?  and	 trn.VERIFIED='Y'	)aa  where aa.VOUCHER_NO not in(select ORGINATING_JVR_NO from FAS_TDA_TCA_RAISED_MST where ACCOUNTING_UNIT_ID=aa.ACCOUNTING_UNIT_ID and ACCOUNTING_FOR_OFFICE_ID=aa.ACCOUNTING_FOR_OFFICE_ID and ORGINATING_JVR_NO=aa.VOUCHER_NO and to_date(ORGINATING_JVR_DATE,'dd-mm-yy')=to_date(aa.VOUCHER_DATE,'dd-mm-yy') and STATUS='L' )";
//            	    
	                 		  
            	        sql="select * from \n" + 
            	        "( \n" + 
            	        "    select  \n" + 
            	        "    mst.ACCOUNTING_UNIT_ID, \n" + 
            	        "    mst.ACCOUNTING_FOR_OFFICE_ID, \n" + 
            	        "    mst.VOUCHER_DATE , \n" + 
            	        "    mst.VOUCHER_NO\n" + 
            	        "    from \n" + 
            	        "     FAS_JOURNAL_MASTER mst, \n" + 
            	        "    FAS_JOURNAL_TRANSACTION trn \n" + 
            	        "    where \n" + 
            	        "    mst.ACCOUNTING_UNIT_ID=trn.ACCOUNTING_UNIT_ID and \n" + 
            	        "    mst.ACCOUNTING_FOR_OFFICE_ID=trn.ACCOUNTING_FOR_OFFICE_ID and  \n" + 
            	        "    mst.CASHBOOK_YEAR=trn.CASHBOOK_YEAR and  \n" + 
            	        "    mst.CASHBOOK_MONTH=trn.CASHBOOK_MONTH and \n" + 
            	        "    mst.VOUCHER_NO=trn.VOUCHER_NO and \n" + 
            	        "    mst.ACCOUNTING_UNIT_ID=? and \n" + 
            	        "    mst.ACCOUNTING_FOR_OFFICE_ID=? and \n" + 
            	        "    mst.VOUCHER_DATE=? and mst.JOURNAL_TYPE_CODE=54 and \n" + 
            	        "    mst.JOURNAL_STATUS='L' and " + 
            	        "    trn.ACCOUNT_HEAD_CODE=?  \n" + 
            	        ")aa \n" + 
            	        "where \n" + 
            	        "aa.VOUCHER_NO not in(select ORGINATING_JVR_NO from FAS_TDA_TCA_RAISED_MST where ACCOUNTING_UNIT_ID=aa.ACCOUNTING_UNIT_ID and ACCOUNTING_FOR_OFFICE_ID=aa.ACCOUNTING_FOR_OFFICE_ID and ORGINATING_JVR_NO=aa.VOUCHER_NO and ORGINATING_JVR_DATE=aa.VOUCHER_DATE and STATUS='L')";  	    	
				
	                           
                                 System.out.println("SQL :: "+sql);
                                 System.out.println("SQL :: "+AccUnitId);
                                 System.out.println("SQL :: "+cmbOffice_code);
                                 System.out.println("SQL :: "+txtCrea_date);
                             
            	    		 ps7=con.prepareStatement(sql);
            	    		 ps7.setInt(1,AccUnitId);
            	    		 ps7.setInt(2,cmbOffice_code);
           	    	    	 ps7.setDate(3,txtCrea_date);
            	    		 ps7.setInt(4,901001);
                             rs7=ps7.executeQuery();  
                            System.out.println("rs777777"+rs7.getFetchSize());
                             while(rs7.next()) 
                             {
                            	 System.out.println("&&&&&&&&&&&&&&&&inside while");
                                     xml+= "<VOUCHER_NO>"+ rs7.getInt("VOUCHER_NO") +"</VOUCHER_NO>";	 			                		                 
                                     count++;
                             }	
                             System.out.println("@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@"+count);
                             if(count==0)
                                     xml+="<flag>NoData</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
            	    }
            	    catch(Exception e)
            	    {
            		  		 System.out.println("Err in loading loadSLType ::: "+e.getMessage());            		  	
            	    }
            }
            else if(cmd.equalsIgnoreCase("load_Voucher_Details"))
            {
            	 	xml=xml+"<command>load_Voucher_Details</command>";
	         	    try	
	         	    {			        	 			  	                 		  
		         	        sql="select trn.ACCOUNT_HEAD_CODE,trn.CR_DR_INDICATOR,trn.SUB_LEDGER_TYPE_CODE,trn.SUB_LEDGER_CODE,trim(to_char(trn.AMOUNT,'99999999999999.99')) as amount, trn.PARTICULARS ,(select  account_head_desc from com_mst_account_heads a where a.account_head_code=trn.account_head_code)as dec ,(select  sub_ledger_type_desc from com_mst_sl_types where sub_ledger_type_code=trn.sub_ledger_type_code)as slcode from FAS_JOURNAL_TRANSACTION trn where trn.accounting_unit_id=? and  trn.accounting_for_office_id=? and trn.cashbook_month=?and trn.cashbook_year=? and trn.voucher_no=? and trn.ACCOUNT_HEAD_CODE=? ";
		         	         System.out.println("SQL ::: "+sql);
		         	        System.out.println("SQL ::: "+AccUnitId);
		         	       System.out.println("SQL ::: "+cmbOffice_code);
		         	      System.out.println("SQL ::: "+Integer.parseInt(sd[2]));
		         	     System.out.println("SQL ::: "+Integer.parseInt(sd[1]));
		         	    System.out.println("SQL ::: "+voucher_no);
	         	    		 ps2=con.prepareStatement(sql);
	         	    		 ps2.setInt(1,AccUnitId);
	         	    		 ps2.setInt(2,cmbOffice_code);
	         	    		 ps2.setInt(4,Integer.parseInt(sd[2]));
	         	    		 ps2.setInt(3,Integer.parseInt(sd[1]));
	         	    		 ps2.setInt(5,voucher_no);
	         	    		 ps2.setInt(6,901001);
	                         rs2=ps2.executeQuery();                                 
	                         while(rs2.next()) 
	                         {
	                        	 System.out.println("enter into the while*****************************");
	                                 xml+= "<account_head_code>"+ rs2.getInt("ACCOUNT_HEAD_CODE") +"</account_head_code>";	 			
	                                 xml+= "<account_head_desc>"+ rs2.getString("dec") +"</account_head_desc>";	 			
	                              
	                                
	                                 xml+= "<cr_dr_indicator>"+ rs2.getString("CR_DR_INDICATOR") +"</cr_dr_indicator>";	                               
	                                 xml+= "<sub_ledger_type_code>"+ rs2.getInt("SUB_LEDGER_TYPE_CODE") +"</sub_ledger_type_code>";
	                                 if(rs2.getInt("sub_ledger_type_code")==0)
	                                	 	xml+= "<sub_ledger_type_desc>--</sub_ledger_type_desc>";
	                                 else
	                                	 	xml+= "<sub_ledger_type_desc>"+ rs2.getString("slcode") +"</sub_ledger_type_desc>";
	                                 xml+= "<sub_ledger_code>"+ rs2.getInt("SUB_LEDGER_CODE") +"</sub_ledger_code>";
	                              
	                                 xml+= "<amount>"+ rs2.getString("amount") +"</amount>";
	                                 xml+= "<particulars>"+ rs2.getString("PARTICULARS") +"</particulars>";
	                                 sub_code=rs2.getInt("sub_ledger_code");
	                                // System.out.println("******************************************************************************"+rs2.getInt("sub_ledger_code"));
	                                 if(rs2.getInt("sub_ledger_type_code")!=0 && rs2.getInt("sub_ledger_code")!=0)
	                                 {
			                                	String sql1="select ACCOUNTING_UNIT_ID, ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID='"+sub_code+"'";
	                                     ps=con.prepareStatement(sql1);
			                                    ResultSet rs_get=ps.executeQuery();
			                                    if(rs_get!=null)
			                                    {
			                                            if(rs_get.next())
			                                            {
				                                              //System.out.println(rs_get.getString("cid"));
				                                              //System.out.println(rs_get.getString("cname"));
				                                              xml=xml+"<cid>"+rs_get.getInt("ACCOUNTING_UNIT_ID")+"</cid><cname>"+rs_get.getString("ACCOUNTING_UNIT_NAME")+"</cname>";
			                                            }
			                                            rs_get.close();
			                                    }
			                                    else
			                                    {
			                                          	System.out.println("null result set");
			                                          	xml=xml+"<cid>--</cid><cname>--</cname>";
			                                    }
	                                 }
	                                 else
	                                	 		xml=xml+"<cid>--</cid><cname>--</cname>";
	                                 count++;
	                         }					              
	                         if(count==0)
	                                 xml+="<flag>NoData</flag>";					           
	                         else               
	                                 xml+="<flag>success</flag>";
	         	    }
	         	    catch(Exception e)
	         	    {
	         		  		 System.out.println("Err in loading loadSLType ::: "+e.getMessage());            		  	
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
	         PreparedStatement ps=null,ps1=null,ps2=null,ps3=null,ps4=null;
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
	              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	              Class.forName(strDriver.trim());
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
	       
	        if(strCommand.equalsIgnoreCase("Add")) 
	        {
	             String CONTENT_TYPE = "text/html; charset=windows-1252";
	             response.setContentType(CONTENT_TYPE);
	            
	             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	             Calendar c;
	             int i=0;
	             int txtAcc_HeadCode=0,cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,txtUnitId=0,txtDebitHead=0,payment_voucher_no=0;
	             int count=0,cmbMas_SL_type=0,cmbMas_SL_Code=0,cmbReason=0,bank_id1=0,branch_id1=0,account_no=0;
	             double txtTotalAmt=0;
	             Date txtCrea_date=null,txtPayment_date=null;
	             String txtRemarks="",paid_to="",Journal_type="",cr_dr_indicator="",sql="";
	             
	                                     // changes here
	             String update_user=(String)session.getAttribute("UserId");
	             long l=System.currentTimeMillis();
	             Timestamp ts=new Timestamp(l);                      
	             int errcode=0,receipt_month=0,receipt_year=0;
	             
	             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	                                
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
	     
	             try{txtCash_year=Integer.parseInt(sd[2]);}
	             catch(Exception e){System.out.println("exception"+e );}
	             System.out.println("txtCash_year "+txtCash_year);
	             
	             try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
	             catch(Exception e){System.out.println("exception"+e );}
	             System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
	             int Originated_SL_No=0;
	             int fin_year_from=0,fin_year_to=0;
	             
	             //////////////////////Financial year calculation/////////////////////////////////
	             if(txtCash_Month_hid>3)
	             {
	            	 	  fin_year_from=txtCash_year;
	            	 	  fin_year_to=txtCash_year+1;
	             }
	             else
	             {
	            	 	  fin_year_from=txtCash_year-1;
	            	 	  fin_year_to=txtCash_year;
	             }
	             System.out.println("fin_year_from ::: "+fin_year_from+"  fin_year_to:::"+fin_year_to);
	             try
	             {
	            	 	  sql="SELECT VOUCHER_NO FROM FAS_TDA_TCA_RAISED_MST GROUP BY VOUCHER_NO HAVING  "+
	            	 	  	  " VOUCHER_NO =(select max(VOUCHER_NO) from FAS_TDA_TCA_RAISED_MST where to_date(CASHBOOK_MONTH||'-'||CASHBOOK_YEAR,'mm-yyyy') between to_date(4||'-'||?,'mm-yyyy') and to_date(3||'-'||?,'mm-yyyy'))";	
	                      ps=con.prepareStatement(sql);
	                      ps.setInt(1,fin_year_from);
	                      ps.setInt(2,fin_year_to);
	            	 	  rs=ps.executeQuery();
	                      if(rs.next()) 
	                      {
	                    	  Originated_SL_No = rs.getInt(1);                                               
	                      }
	                      Originated_SL_No=Originated_SL_No+1;
	                      rs.close();
	             }           	    
	             catch(Exception e){System.out.println("exception"+e );}
	             System.out.println("Originated_SL_No "+Originated_SL_No);
	             
	             String[] sd1=request.getParameter("txtPayment_date").split("/");
	             c=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
	             java.util.Date d1=c.getTime();
	             txtPayment_date=new Date(d1.getTime());
	             
	             try{receipt_year=Integer.parseInt(sd1[2]);}
	             catch(Exception e){System.out.println("exception"+e );}
	             System.out.println("receipt_year "+receipt_year);
	             
	             try{receipt_month=Integer.parseInt(sd1[1]);}
	             catch(Exception e){System.out.println("exception"+e );}
	             System.out.println("receipt_month "+receipt_month);
	             
	             try{payment_voucher_no=Integer.parseInt(request.getParameter("txtVoucher_No"));}
	 	         catch(NumberFormatException e){System.out.println("exception"+e );}
	 	         System.out.println("payment_voucher_no "+payment_voucher_no);
	             
	             
	             try{cmbReason=Integer.parseInt(request.getParameter("cmbReason"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	             System.out.println("cmbReason "+cmbReason);
	             
//	             try{txtUnitId=Integer.parseInt(request.getParameter("txtUnitId"));}
//	             catch(NumberFormatException e){System.out.println("exception"+e );}
//	             System.out.println("txtUnitId "+txtUnitId);
	             
	             try{txtDebitHead=Integer.parseInt(request.getParameter("txtDebitHead"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	             System.out.println("txtDebitHead "+txtDebitHead);
	            
	             try{cmbMas_SL_type=Integer.parseInt(request.getParameter("cmbMas_SL_type"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	             System.out.println("cmbMas_SL_type "+cmbMas_SL_type);
	            
	             try{cmbMas_SL_Code=Integer.parseInt(request.getParameter("cmbMas_SL_Code"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	             System.out.println("cmbMas_SL_Code "+cmbMas_SL_Code);
	             
//	             try{paid_to=request.getParameter("txtPaidTo");}
//	             catch(NumberFormatException e){System.out.println("exception"+e );}
//	             System.out.println("paid_to "+paid_to);
	            
	                                     
	             try{txtRemarks=request.getParameter("txtParticular");}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	             System.out.println("txtRemarks "+txtRemarks);
	             
	             try{txtTotalAmt=Double.parseDouble(request.getParameter("txtTotalAmt"));}
	             catch(Exception e){System.out.println("exception"+e );}
	             System.out.println("txtAmount "+txtTotalAmt);
	             
	                         
	             
	             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
	                 
	             try 
	             {   
	            	 	  try
	            	 	  {
		                      con.clearWarnings();
		                      con.setAutoCommit(false);
		                      ps=con.prepareStatement("insert into FAS_TDA_TCA_RAISED_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,ADVICE_TYPE,VOUCHER_DATE,CR_DR_INDICATOR,ACCOUNT_HEAD_CODE,REASON_FOR_TRANSFER,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,TOTAL_AMOUNT,PARTICULARS,ORGINATING_JVR_NO,ORGINATING_JVR_DATE,STATUS,UPDATED_BY_USERID,UPDATED_DATE,TDA_OR_TCA,TRF_ACCOUNTING_UNIT_ID) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		                      //System.out.println("insert into FAS_ADJUSTMENT_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,ADJUSTMENT_DATE,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,BEHALF_OF_OFFICE_ID,PARTICULARS,TOTAL_AMOUNT,UPDATED_BY_USER_ID,UPDATED_DATE,VOUCHER_STATUS) values ("+cmbAcc_UnitCode+","+cmbOffice_code+",'"+txtCrea_date+"',"+txtCash_year+","+txtCash_Month_hid+","+txtAdvice_No+","+office_id+",'"+particulars+"','"+txtTotalAmt+"','"+update_user+"','"+ts+"','L')");
		                      ps.setInt(1,cmbAcc_UnitCode);
		                      ps.setInt(2,cmbOffice_code);
		                      ps.setInt(3,txtCash_year);
		                      ps.setInt(4,txtCash_Month_hid);
		                      ps.setInt(5,Originated_SL_No);
		                      ps.setString(6,"O");
		                      ps.setDate(7,txtCrea_date);
		                      ps.setString(8,"DR");                      
		                      ps.setInt(9,txtDebitHead);               
		                     
		                      ps.setInt(10,cmbReason);
		                      ps.setInt(11,cmbMas_SL_type);
		                      ps.setInt(12,cmbMas_SL_Code);
		                      ps.setDouble(13,txtTotalAmt);
		                      ps.setString(14,txtRemarks);
		                      ps.setInt(15,payment_voucher_no);
		                      ps.setDate(16,txtPayment_date);
		                      ps.setString(17,"L");
		                      ps.setString(18,update_user);
		                      ps.setTimestamp(19,ts);
		                      ps.setString(20,"TCAJ1");
		                      ps.setInt(21,cmbAcc_UnitCode);
		                      errcode=ps.executeUpdate();
	            	 	  }catch(Exception e){System.out.println("Err in first table create :: "+e.getMessage());}
	                      if(errcode==0)
	                      {         
	                          System.out.println("redirect");
	                          sendMessage(response,"The Post TCA Creation Failed ","ok");                                      
	                      }
	                      else
	                      {  
	                          System.out.println("inside 2 nd table");                              
	                          String Grid_H_code[]=request.getParameterValues("H_code");
	                          String Grid_CR_DR_type[]=request.getParameterValues("acc_no");
	                          String Grid_SL_type[]=request.getParameterValues("SL_type");
	                          String Grid_SL_code[]=request.getParameterValues("SL_code"); 
	                          String ch_dd[]=request.getParameterValues("letterno");
	                          String ch_no[]=request.getParameterValues("pageno");
	                          String ch_date[]=request.getParameterValues("letterdate");
	                          String Grid_sl_amt[]=request.getParameterValues("sl_amt");
	                          String Grid_particular[]=request.getParameterValues("remarkss");                         
	                                    
	                          System.out.println("2 nd table insert");
	                        
	                          int SL_NO=0,cmbSL_type=0,cmbSL_Code=0,pageno=0,letterno=0,ref_num,iten=0;
	                          double txtsub_Amount=0;
	                          try
	                          {
	                                      sql="insert into FAS_TDA_TCA_RAISED_TRN(ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID, CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE,CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, AMOUNT,PARTICULARS,MBOOK_NO,MBOOK_PAGENO,MBOOK_DATE,UPDATED_BY_USERID, UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?)" ;                          
	                                      ps=con.prepareStatement(sql);
	                                      for(int k=0;k<Grid_H_code.length;k++) 
	                                      {                                                                                                        
	                                                cmbSL_type=0;cmbSL_Code=0;ref_num=0;
	                                                txtAcc_HeadCode=0;  txtsub_Amount=0; 
	                                                txtsub_Amount=0;
	                                                bank_id1=0;branch_id1=0;account_no=0;           
	                                                SL_NO++;
	                                                ps.setInt(1,cmbAcc_UnitCode);     
	                                                ps.setInt(2,cmbOffice_code);    
	                                                ps.setInt(3,txtCash_year);
	                                                ps.setInt(4,txtCash_Month_hid);
	                                                ps.setInt(5,Originated_SL_No);       
	                                                ps.setInt(6,SL_NO);
	                                                
	                                                txtAcc_HeadCode=Integer.parseInt(Grid_H_code[k]);
	                                                System.out.println("txtAcc_HeadCode&&&&&&&&&&&&&&&&&&&&&&&&&&&"+txtAcc_HeadCode);
	                                                ps.setInt(7,txtAcc_HeadCode);
	                                                
	                                                String rad_sub_CR_DR=Grid_CR_DR_type[k];    
	                                                System.out.println("rad_sub_CR_DR&&&&&&&&&&&&&&&&&&&&&&&&&&&"+rad_sub_CR_DR);
	                                                ps.setString(8,rad_sub_CR_DR);   
	                                               
	                                                try{cmbSL_type=Integer.parseInt(Grid_SL_type[k]);}
	                                                catch(NumberFormatException e){System.out.println("exception"+e );}
	                                                System.out.println("cmbSL_type&&&&&&&&&&&&&&&&&&&&&&&&&&&"+cmbSL_type);
	                                                ps.setInt(9,cmbSL_type); 
	                                                
	                                                try{cmbSL_Code=Integer.parseInt(Grid_SL_code[k]);}
	                                                catch(NumberFormatException e){System.out.println("exception"+e );}
	                                                System.out.println("cmbSL_Code&&&&&&&&&&&&&&&&&&&&&&&&&&&"+cmbSL_Code);
	                                                ps.setInt(10,cmbSL_Code);
	                                                
	                                                
	                                                
	                                              
	                                                
	                                             
	                                                                                   
	                                                try{txtsub_Amount=Double.parseDouble(Grid_sl_amt[k]);}
	                                                catch(NumberFormatException e){System.out.println("exception"+e );}
	                                                System.out.println("txtsub_Amount&&&&&&&&&&&&&&&&&&&&&&&&&&&"+txtsub_Amount);
	                                                ps.setDouble(11,txtsub_Amount);
	                                               
	                                                
	                                                
	                                               // System.out.println("Grid_particular&&&&&&&&&&&&&&&&&&&&&&&&&&&"+Grid_particular[k]);
	                                                ps.setString(12,Grid_particular[k]);   
	                                              
	                                                
	                                                try{letterno=Integer.parseInt(ch_dd[k]);}
	                                                catch(NumberFormatException e){System.out.println("exception"+e );}
	                                                System.out.println("letterno&&&&&&&&&&&&&&&&&&&&&&&&&&&"+letterno);
	                                                ps.setInt(13,letterno);
	                                                
	                                                try{pageno=Integer.parseInt(ch_no[k]);}
	                                                catch(NumberFormatException e){System.out.println("exception"+e );}
	                                                System.out.println("pageno&&&&&&&&&&&&&&&&&&&&&&&&&&&"+pageno);
	                                                ps.setInt(14,pageno);
	                                                System.out.println("ch_date[k]&&&&&&&&&&&&&&&&&&&&&&&&&&&"+ch_date[k]);
	                                                ps.setString(15,ch_date[k]);
	                                                
	                                                
	                                                
	                                                ps.setString(16,update_user);
	                                                ps.setTimestamp(17,ts);
	                                                System.out.println("------------------------"+SL_NO);
	                                                count++;
	                                               iten=ps.executeUpdate(); 
	                                              System.out.println("i after insert"+i);
	                                               
	                                                //con.commit();  // System.out.println("SQL::::insert into FAS_ADJUSTMENT_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,HO_REF_NUMBER,HO_REF_DATE,AMOUNT,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE) values("+cmbAcc_UnitCode+","+cmbOffice_code+","+txtCash_year+","+txtCash_Month_hid+","+txtAdvice_No+","+SL_NO+","+txtAcc_HeadCode+",'"+rad_sub_CR_DR+"',"+cmbSL_type+","+cmbSL_Code+","+ref_num+",?,?,?,?)");                                    
	                                        }
	                                      
	                                      if(iten>0){
                                              
                                              try{
                                              System.out.println("inside update");
                                              	
                                              	String sql2="update FAS_JOURNAL_MASTER set CB_REF_TYPE='T' where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+receipt_year+" and CASHBOOK_MONTH="+receipt_month+" and VOUCHER_NO="+payment_voucher_no;
                                           	   System.out.println("sql2 for up::::"+sql2);
                                           	   ps3=con.prepareStatement(sql2);
                                           	  int cbtype= ps3.executeUpdate();
//                                                  if(cbtype>0) {
//                                                      String sql3="update FAS_PAYMENT_TRANSACTION set PAYABLE_VOUCHER_NO=?,PAYABLE_VOUCHER_DATE=?  where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+receipt_year+" and CASHBOOK_MONTH="+receipt_month+" and VOUCHER_NO="+payment_voucher_no;
//                                                         System.out.println("sql3 for up::::"+sql3);
//                                                         ps4=con.prepareStatement(sql3);
//                                                      ps4.setInt(1,Originated_SL_No);System.out.println("Originated_SL_No"+Originated_SL_No);
//                                                      ps4.setDate(2,txtCrea_date);System.out.println("txtCrea_date"+txtCrea_date);
//                                                         ps4.executeUpdate();
//                                                  }
                                              }
                                              catch(Exception e1)
                                              {
                                              System.out.println("excep in update:"+e1);	
                                              }
                                          }
	                            }
	                            catch(Exception e)
	                            {
	                                        e.getStackTrace();
	                                        System.out.println("Err in value setting for insertion:::"+e.getMessage());
	                                        con.rollback();
	                            }
	                            ps.close();
	                            System.out.println("Length:  "+count+" "+Grid_H_code.length);
	                            if(count==Grid_H_code.length)
	                            {	       con.commit();                                
	                                        sendMessage(response,"The Post TCA Sl.No '"+Originated_SL_No+"' has been Created Successfully ","ok");
	                                        
	                            }
	                            else
	                            {
	                                        System.out.println("b4 Rollback");
	                                        sendMessage(response,"The Post TCA Creation Failed ","ok");
	                                        con.rollback();
	                            }
	                            
	                      }
	                     
	                  }
	                  
	                  catch(Exception e) 
	                  {
	                      try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
	                      e.getStackTrace();
	                    	  	sendMessage(response,"The Post TCA Creation Failed ","ok");
	                      
	                      
	                  }
	                  finally
	                  {
	                      System.out.println("done");
	                      try{con.setAutoCommit(true);  }catch(SQLException sqle){}
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
