package Servlets.FAS.FAS1.TDA.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class TDA_TCA_Responding_Edit_Supp
 */
public class TDA_TCA_Responding_Edit_Supp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TDA_TCA_Responding_Edit_Supp() {
        super();
        // TODO Auto-generated constructor stub
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
	      PreparedStatement ps2=null;        
	      ResultSet rs2=null;
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
        
	    
	    
            int count=0,cmbAcc_UnitCode=0,cmbOffice_code=0,cashbook_year=0,cashbook_month=0,responded_slno=0,txtUnitId=0,supNo=0;
            String xml=null,cmd="",option="",Journal_type="";          
      
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
            
            try{Journal_type=request.getParameter("Journal_type");}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("Journal_type "+Journal_type);
            
            try{cashbook_month=Integer.parseInt(request.getParameter("txtCB_Month"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("cashbook_month "+cashbook_month);
            
            try{responded_slno=Integer.parseInt(request.getParameter("responded_slno"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("responded_slno "+responded_slno);
            
            try{supNo=Integer.parseInt(request.getParameter("supNo"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("responded_slno "+supNo);
            
            System.out.println("cmd:::"+cmd);
            xml="<response>";
            if(cmd.equalsIgnoreCase("loadVr_sup"))
            {     
                 
                    xml=xml+"<command>loadVr_sup</command>";
                    sql="select a.VOUCHER_NO as originated_slno \n" + 
                    "from FAS_TDA_TCA_RAISED_MST a,FAS_TDA_TCA_RAISED_MST b where \n" + 
                    "a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID and \n" + 
                    "a.ACCEPTING_SLNO=b.VOUCHER_NO and \n" + 
                    "a.ACCEPTING_DATE=b.VOUCHER_DATE and \n" + 
                    "a.ACCOUNTING_UNIT_ID=? and \n" + 
                    "a.ACCOUNTING_FOR_OFFICE_ID=? and \n" + 
                    "a.CASHBOOK_YEAR=? and \n" + 
                    "a.CASHBOOK_MONTH=? and \n" + 
                    "a.TDA_OR_TCA=? and \n" +
                    "a.STATUS='L' and " + 
                    "a.SUPPLEMENT_NO=? and\n" +
                    "b.ACCEPTING_JVR_NO is not null and b.RESPONDING_JVR_NO is null"; 
                    System.out.println(" SQL :: "+sql);
                    try
                    {
                             ps2=con.prepareStatement(sql);
                             ps2.setInt(1,cmbAcc_UnitCode);
                             ps2.setInt(2,cmbOffice_code);
                             ps2.setInt(3,cashbook_year);
                             ps2.setInt(4,cashbook_month);
                             ps2.setString(5,Journal_type);
                             ps2.setInt(6,supNo);
                             rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                                     xml+= "<voucher_no>"+ rs2.getInt("originated_slno") +"</voucher_no>";	                                     			                		                
                                     count++;
                             }					              
                             if(count==0)
                                     xml+="<flag>NoData</flag>";					           
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
                 
                    xml=xml+"<command>loadVoucherDetails</command>";
                    sql="select to_char(a.VOUCHER_DATE,'dd/mm/yyyy') as originated_sldate, \n" + 
                    "a.ORGINATING_JVR_NO as originated_jvr_no, \n" + 
                    "to_char(a.ORGINATING_JVR_DATE,'dd/mm/yyyy') as originated_jvr_date, \n" + 
                    "b.VOUCHER_NO as accepted_slno, \n" + 
                    "to_char(b.VOUCHER_DATE,'dd/mm/yyyy') as accepted_sldate, \n" + 
                    "b.ACCEPTING_JVR_NO as accepted_jvr_no, \n" + 
                    "to_char(b.ACCEPTING_JVR_DATE,'dd/mm/yyyy') as accepted_jvr_date, \n" + 
                    "b.TRF_ACCOUNTING_UNIT_ID as accepted_unit_id, \n" + 
                    "trim(to_char(a.TOTAL_AMOUNT,'99999999999999.99')) as AMOUNT, \n" +
                    "c.ACCOUNTING_UNIT_NAME " + 
                    "from FAS_TDA_TCA_RAISED_MST a,FAS_TDA_TCA_RAISED_MST b,FAS_MST_ACCT_UNITS c where \n" + 
                    "a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID and \n" +  
                    "a.ACCEPTING_SLNO=b.VOUCHER_NO and \n" + 
                    "a.ACCEPTING_DATE=b.VOUCHER_DATE and \n" +
                    "a.TRF_ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and " + 
                    "a.ACCOUNTING_UNIT_ID=? and \n" + 
                    "a.ACCOUNTING_FOR_OFFICE_ID=? and \n" + 
                    "a.CASHBOOK_YEAR=? and \n" + 
                    "a.CASHBOOK_MONTH=? and \n" +
                    "a.VOUCHER_NO=? and " + 
                    "a.TDA_OR_TCA=? and \n" +
                    "a.STATUS='L' and " + 
                    "b.ACCEPTING_JVR_NO is not null and b.RESPONDING_JVR_NO is null ";
                    System.out.println(" SQL :: "+sql);
                    try
                    {
                             ps2=con.prepareStatement(sql);
                             ps2.setInt(1,cmbAcc_UnitCode);
                             ps2.setInt(2,cmbOffice_code);
                             ps2.setInt(3,cashbook_year);
                             ps2.setInt(4,cashbook_month);
                             ps2.setInt(5,responded_slno);
                             ps2.setString(6,Journal_type);
                             rs2=ps2.executeQuery();                                 
                             if(rs2.next()) 
                             {
                                     xml+="<originated_sldate>"+rs2.getString("originated_sldate")+"</originated_sldate>";	        
                                     xml+="<originated_jvr_no>"+rs2.getInt("originated_jvr_no")+"</originated_jvr_no>";	 
                                     xml+="<originated_jvr_date>"+rs2.getString("originated_jvr_date")+"</originated_jvr_date>";	 
                                     xml+="<accepted_slno>"+rs2.getInt("accepted_slno")+"</accepted_slno>";	 
                                     xml+="<accepted_sldate>"+rs2.getString("accepted_sldate")+"</accepted_sldate>";	
                                     xml+="<accepted_jvr_no>"+rs2.getInt("accepted_jvr_no")+"</accepted_jvr_no>";	 
                                     xml+="<accepted_jvr_date>"+rs2.getString("accepted_jvr_date")+"</accepted_jvr_date>";	
                                     xml+="<accounting_unit_name>"+rs2.getString("ACCOUNTING_UNIT_NAME")+"</accounting_unit_name>";	 
                                     xml+="<amount>"+rs2.getString("AMOUNT")+"</amount>";	
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
            else if(cmd.equalsIgnoreCase("loadSLType"))
            {
            	    xml=xml+"<command>loadSLType</command>";
            	    try	
            	    {			        	 			  	                 		  
            	    		 sql="select trn.ACCOUNTING_UNIT_OFFICE_ID,mst.OFFICE_SHORT_NAME from FAS_MST_ACCT_UNITS trn,COM_MST_OFFICES mst where trn.ACCOUNTING_UNIT_OFFICE_ID=mst.OFFICE_ID and trn.ACCOUNTING_UNIT_ID=?";            	    		 
            	    		 ps2=con.prepareStatement(sql);
            	    		 ps2.setInt(1,txtUnitId);
                             rs2=ps2.executeQuery();                                 
                             while(rs2.next()) 
                             {
                                     xml+= "<office_id>"+ rs2.getInt("ACCOUNTING_UNIT_OFFICE_ID") +"</office_id>";	 
                                     xml+= "<office_name>"+ rs2.getString("OFFICE_SHORT_NAME") +"</office_name>";  				                		                 
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
       
        if(strCommand.equalsIgnoreCase("Add")) 
        {
             String CONTENT_TYPE = "text/html; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
            
             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
             Calendar c;
             int txtAcc_HeadCode=0,cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,txtUnitId=0,txtCreditHead=0,txtDebitHead=0,depriciation_rate=0;
             int count=0,sub_ledger_code=0,accepted_slno=0,Responding_JVR_No=0,trn_count=0,account_head_code=0,sub_ledger_type=0;
             double txtTotalAmt=0;
             Date txtCrea_date=null,accepted_sldate=null;
             String txtRemarks="",Journal_type="",particulars="",paid_to="",cmbReason="",cr_dr_indicator="",flag="",sql="";
             int supNo=0;
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
             
             try{Journal_type=request.getParameter("Journal_type");}
             catch(Exception e){System.out.println("exception"+e );}
             
             try{accepted_slno=Integer.parseInt(request.getParameter("accepted_slno"));}
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("originated_slno "+accepted_slno);
             
             String[] sd=request.getParameter("accepted_sldate").split("/");
             c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
             java.util.Date d=c.getTime();
             accepted_sldate=new Date(d.getTime());
             
             String[] sd1=request.getParameter("txtCrea_date").split("/");
             c=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
             java.util.Date d1=c.getTime();
             txtCrea_date=new Date(d1.getTime());
     
             try{txtCash_year=Integer.parseInt(sd[2]);}
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("txtCash_year "+txtCash_year);
             
             try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
           
             try{txtCreditHead=Integer.parseInt(request.getParameter("cr_accHead_code"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("txtDebitHead "+txtCreditHead);
          
             try{txtDebitHead=Integer.parseInt(request.getParameter("dr_accHead_code"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("txtDebitHead "+txtCreditHead);
                                             
             try{txtRemarks=request.getParameter("txtRemarks");}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("txtRemarks "+txtRemarks);
             
             try{txtTotalAmt=Double.parseDouble(request.getParameter("txtTotalAmt"));}
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("txtAmount "+txtTotalAmt);
             
             try{Journal_type=request.getParameter("Journal_type");}
             catch(Exception e){System.out.println("Journal_type "+e );}
             System.out.println("Journal_type "+Journal_type);
             
             try{supNo=Integer.parseInt(request.getParameter("supNo"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("supNo "+supNo);
             
             
             try
             {
                      ps=con.prepareStatement("select VOUCHER_NO from FAS_JOURNAL_MASTER GROUP BY VOUCHER_NO HAVING VOUCHER_NO =(select max(VOUCHER_NO) from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SUPPLEMENT_NO=?)");
                      ps.setInt(1,cmbAcc_UnitCode);
                      ps.setInt(2,cmbOffice_code);
                      ps.setInt(3,txtCash_year);
                      ps.setInt(4,txtCash_Month_hid);    
                      ps.setInt(5,supNo);
            	 	  rs=ps.executeQuery();
                      if(rs.next()) 
                      {
                                Responding_JVR_No = rs.getInt(1);                                               
                      }
                      Responding_JVR_No=Responding_JVR_No+1;
                      rs.close();
             }           
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("Responding_JVR_No "+Responding_JVR_No);
             
             try
             {
            	 	 con.setAutoCommit(false);
	            	 ps1=con.prepareStatement("insert into FAS_JOURNAL_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,VOUCHER_DATE,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,JOURNAL_TYPE_CODE,SUB_LEDGER_CODE,TOTAL_TRN_RECORDS,REMARKS,MODE_OF_CREATION,CREATED_BY_MODULE,JOURNAL_STATUS,UPDATED_BY_USER_ID,UPDATED_DATE,DEPRECIATION_RATE,SUPPLEMENT_NO)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	         		 ps1.setInt(1,cmbAcc_UnitCode);
	         		 ps1.setInt(2,cmbOffice_code);
	         		 ps1.setDate(3,txtCrea_date);
	         		 ps1.setInt(4,txtCash_year);
	                 ps1.setInt(5,txtCash_Month_hid);  
	                 ps1.setInt(6,Responding_JVR_No);
	                 ps1.setInt(7,67);
	                 ps1.setInt(8,sub_ledger_code);
	                 ps1.setInt(9,2);
	                 ps1.setString(10,particulars);
	                 ps1.setString(11,"M");
	                 ps1.setString(12,"GJV");
	                 ps1.setString(13,"L");
	                 ps1.setString(14,update_user);
	                 ps1.setTimestamp(15,ts);
	                 ps1.setInt(16,depriciation_rate);
	                 ps1.setInt(17,supNo);
	                 int kk=ps1.executeUpdate();
	                 if(kk>0)
	                 {
	                 		flag="success";
	                 		System.out.println("Flag ::: "+flag);
	                 }
	                 else
	                 		flag="failure";
	                 
	                 System.out.println("Flag ------>"+flag);
                     
                     if(flag.equals("success"))
                     {
                    	 	for(int i=0;i<2;i++)
                    	 	{
		                    	 	count++;
		                    	 	if(i==0)
		                    	 	{
		                    	 		account_head_code=txtCreditHead;cr_dr_indicator="CR";
		                    	 	}
		                    	 	else
		                    	 	{
		                    	 		account_head_code=txtDebitHead;cr_dr_indicator="DR";
		                    	 		
		                    	 	}
		                    	 	ps=con.prepareStatement("insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,AMOUNT,PARTICULARS,CB_REF_NO,CB_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		                    	 	ps.setInt(1,cmbAcc_UnitCode);
		                    	 	ps.setInt(2,cmbOffice_code);
		                    	 	ps.setInt(3,txtCash_year);
		                    	 	ps.setInt(4,txtCash_Month_hid);
		                    	 	ps.setInt(5,Responding_JVR_No);
		                    	 	ps.setInt(6,count);
		                    	 	ps.setInt(7,account_head_code);
		                    	 	ps.setString(8,cr_dr_indicator);
		                    	 	ps.setInt(9,sub_ledger_type);
		                    	 	ps.setInt(10,sub_ledger_code);
		                        	ps.setDouble(11,txtTotalAmt);
		                        	ps.setString(12,particulars);
		                        	ps.setInt(13,accepted_slno);
		                        	ps.setDate(14,accepted_sldate);
		                        	ps.setString(15,update_user);
		                        	ps.setTimestamp(16,ts);
		                        	int mm=ps.executeUpdate();
		                        	if(mm>0)
		                        		trn_count++;  
                    	 	}
                    	 	if(trn_count==2)
                            {
                            		ps.close();
                            		ps=con.prepareStatement("update FAS_TDA_TCA_RAISED_MST set RESPONDING_JVR_NO=?,RESPONDING_JVR_DATE=? where TRF_ACCOUNTING_UNIT_ID=? and VOUCHER_NO=? and VOUCHER_DATE=? and TDA_OR_TCA=? and SUPPLEMENT_NO=?");
                            		ps.setInt(1,Responding_JVR_No);
                            		ps.setDate(2,txtCrea_date);
                            		ps.setInt(3,cmbAcc_UnitCode);                    		
                            		ps.setInt(4,accepted_slno);
                            		ps.setDate(5,accepted_sldate);
                            		ps.setInt(6,supNo);
                            		if(Journal_type.equals("TDAR"))
                            			ps.setString(6,"TDAA");
                            		else
                            			ps.setString(6,"TCAA");
                            		int tt=ps.executeUpdate();
                            		if(tt>0)
                            		{
                            			System.out.println("b4 commit");
	       		                        con.commit();
	       		                        sendMessage(response,"The Post Journal Voucher Number "+Responding_JVR_No+" has been Created Successfully ","ok");		                        
                            		}
                            		else
                            		{
                            			System.out.println("b4 Rollback");
    	                                con.rollback();
    	                                sendMessage(response,"The Post Journal Voucher Creation Failed ","ok");       
                            		}
                            }
                            else
                            {
	                           	 	System.out.println("b4 Rollback");
	                                con.rollback();
	                                sendMessage(response,"The Post Journal Voucher Creation Failed ","ok");       
                            }
                     }
                     else
                     {
	                    	System.out.println("b4 Rollback");
	                        con.rollback();
	                        sendMessage(response,"The Post Journal Voucher Creation Failed ","ok");  
                     }
                     
             }             
             
             catch(Exception e)
             {
            	 	System.out.println("Err in Journal Master Insertion ::: "+e.getMessage());
            	 	System.out.println("b4 Rollback");
            	 	try
            	 	{
                    con.rollback();
            	 	}catch(Exception ee){System.out.println("Err in Insert::"+ee.getMessage());}
                    sendMessage(response,"The Post Journal Voucher Creation Failed ","ok");       
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
