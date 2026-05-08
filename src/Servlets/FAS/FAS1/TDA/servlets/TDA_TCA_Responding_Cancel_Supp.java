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
 * Servlet implementation class TDA_TCA_Responding_Cancel_Supp
 */
public class TDA_TCA_Responding_Cancel_Supp extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TDA_TCA_Responding_Cancel_Supp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("tis is responding cancel:::::::::::::;");	 
		PrintWriter out = response.getWriter();
	      response.setHeader("cache-control","no-cache");
	      String CONTENT_TYPE = "text/xml; charset=windows-1252";
	      response.setContentType(CONTENT_TYPE);
	      
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
	      
	      Connection con=null;
	      PreparedStatement ps2=null;        
	      ResultSet rs2=null;
	      String sql="";
	      
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
            
            int count=0,cmbAcc_UnitCode=0,cmbOffice_code=0,cashbook_year=0,cashbook_month=0,originated_slno=0,txtUnitId=0,supNo=0;
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
            
            try{originated_slno=Integer.parseInt(request.getParameter("originated_slno"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("originated_slno "+originated_slno);
            
            
            try{supNo=Integer.parseInt(request.getParameter("supNo"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("supNo "+supNo);
            
            String jt=null;
            if(Journal_type.equals("TCAO"))
            {
            	jt="TCACB";
            }
            else
            {
            	jt="TDACB";
            }
            System.out.println("cmd:::"+cmd);
            xml="<response>";
            if(cmd.equalsIgnoreCase("loadVr"))
            {     
                 
                    xml=xml+"<command>loadVr</command>";
                    sql="select a.VOUCHER_NO as originated_slno \n" + 
                    "from FAS_TDA_TCA_RAISED_MST a,FAS_TDA_TCA_RAISED_MST b where \n" + 
                    "a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID and \n" + 
                    "a.ACCEPTING_SLNO=b.VOUCHER_NO and \n" + 
                    "a.ACCEPTING_DATE=b.VOUCHER_DATE and \n" + 
                    "a.ACCOUNTING_UNIT_ID=? and \n" + 
                    "a.ACCOUNTING_FOR_OFFICE_ID=? and \n" +
                    "Extract (Year From A.Responding_Jvr_Date )                   =? and Extract (Month From A.Responding_Jvr_Date )                   =? and "+
                   // "a.CASHBOOK_YEAR=? and \n" + 
                   // "a.CASHBOOK_MONTH=? and \n" + 
                    "(a.TDA_OR_TCA=? or a.TDA_OR_TCA='"+jt+"')and \n" +
                    "a.STATUS='L' and " + 
                    "a.SUPPLEMENT_NO=? and " + 
                    "b.ACCEPTING_JVR_NO is not null and a.RESPONDING_JVR_NO>0"; 
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
            else if(cmd.equalsIgnoreCase("loadVrDetails"))
            {     
                 System.out.println("llllllllllllllllllllllllll");
                    xml=xml+"<command>loadVoucherDetails</command>";
                    
                    sql="select to_char(a.VOUCHER_DATE,'dd/mm/yyyy') as originated_sldate, \n" + 
                    "a.ORGINATING_JVR_NO as originated_jvr_no, \n" + 
                    "to_char(a.ORGINATING_JVR_DATE,'dd/mm/yyyy') as originated_jvr_date, \n" +
                    "b.ACCOUNTING_UNIT_ID,b.ACCOUNTING_FOR_OFFICE_ID," + 
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
                    "a.ACCEPTING_DATE=b.VOUCHER_DATEand \n" +
                    "a.TRF_ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and " + 
                    "a.ACCOUNTING_UNIT_ID=? and \n" + 
                    "a.ACCOUNTING_FOR_OFFICE_ID=? and \n" + 
                   // "a.CASHBOOK_YEAR=? and \n" + 
                   // "a.CASHBOOK_MONTH=? and \n" +
                    "Extract (Year From A.Responding_Jvr_Date )                   =? and Extract (Month From A.Responding_Jvr_Date )                   =? and "+
                    "a.VOUCHER_NO=? and " + 
                    "(a.TDA_OR_TCA=? or a.TDA_OR_TCA='"+jt+"')and \n" +
                    "a.STATUS='L' and " + 
                    "a.SUPPLEMENT_NO=? and " +
                    "b.ACCEPTING_JVR_NO is not null and a.RESPONDING_JVR_NO>0 ";
                    System.out.println(" SQL :: in loadVoucherDetails "+sql);
                    try
                    {
                             ps2=con.prepareStatement(sql);
                             ps2.setInt(1,cmbAcc_UnitCode);
                             ps2.setInt(2,cmbOffice_code);
                             ps2.setInt(3,cashbook_year);
                             ps2.setInt(4,cashbook_month);
                             ps2.setInt(5,originated_slno);
                             ps2.setString(6,Journal_type);
                             ps2.setInt(7,supNo);
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
                                     xml+="<accounting_office_id>"+rs2.getInt("ACCOUNTING_FOR_OFFICE_ID")+"</accounting_office_id>";	 
                                     xml+="<accounting_unit_id>"+rs2.getInt("ACCOUNTING_UNIT_ID")+"</accounting_unit_id>";	 
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


	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		   	 String strCommand="";
	         Connection con=null;        
	         PreparedStatement ps=null,ps1=null,ps2=null,ps3=null,ps_ac=null;
	         String xml="";
	         Statement st=null;
	         ResultSet rs=null,rs2=null;
	         System.out.println("for cancel submission>>>>>>>>");
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
	             int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,journal_type_code=0,txtCreditHead=0,txtDebitHead=0,depriciation_rate=0,cashbook_year=0,cashbook_month=0;
	             int count=0,sub_ledger_code=0,accepted_slno=0,Responding_JVR_No=0,trn_count=0,account_head_code=0,sub_ledger_type=0,org_sltype=0,org_slcode=0,acc_sltype=0,acc_slcode=0,originated_slno=0;
	             double txtTotalAmt=0;
	             Date txtCrea_date=null,accepted_sldate=null,originated_sldate1=null;
	             String txtRemarks="",Journal_type="",particulars="",cr_dr_indicator="",flag="",sql="";
	             int supNo=0;
	                                     // changes here
	             String update_user=(String)session.getAttribute("UserId");
	             long l=System.currentTimeMillis();
	             Timestamp ts=new Timestamp(l);           
	             int respondingNo=0;
	             Date respondingDate=null;
	             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	                                
	             try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	             
	             try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	             
	             try{Journal_type=request.getParameter("Journal_type");}
	             catch(Exception e){System.out.println("exception"+e );}
	             
	             try{cashbook_year=Integer.parseInt(request.getParameter("txtCB_Year"));}
	             catch(Exception e){System.out.println("exception"+e );}
	             System.out.println("cashbook_year "+cashbook_year);
	             
	             try{cashbook_month=Integer.parseInt(request.getParameter("txtCB_Month"));}
	             catch(Exception e){System.out.println("exception"+e );}
	             System.out.println("cashbook_month "+cashbook_month);
	            
	             try{originated_slno=Integer.parseInt(request.getParameter("originated_slno"));}
	             catch(Exception e){System.out.println("exception"+e );}
	             System.out.println("originated_slno "+originated_slno);
	             
	             try{accepted_slno=Integer.parseInt(request.getParameter("accepted_slno"));}
	             catch(Exception e){System.out.println("exception"+e );}
	             System.out.println("originated_slno "+accepted_slno);
	             
	             String[] sd=request.getParameter("accepted_sldate").split("/");
	             c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	             java.util.Date d=c.getTime();
	             accepted_sldate=new Date(d.getTime());
	             
	             String[] sd2=request.getParameter("originated_sldate").split("/");
	             c=new GregorianCalendar(Integer.parseInt(sd2[2]),Integer.parseInt(sd2[1])-1,Integer.parseInt(sd2[0]));
	             java.util.Date d2=c.getTime();
	             originated_sldate1=new Date(d2.getTime());
	             
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
	             catch(Exception e){System.out.println("exception"+e );}
	             System.out.println("supNo "+supNo);
	             
	             
	             int tt=0;
	             try
	             {
	            	 	 con.setAutoCommit(false);
	            	 	 				ps3=con.prepareStatement("select RESPONDING_JVR_NO,RESPONDING_JVR_DATE from FAS_TDA_TCA_RAISED_MST where ACCOUNTING_UNIT_ID=? and VOUCHER_NO=? and VOUCHER_DATE=? and SUPPLEMENT_NO=?");
	            	 	 				ps3.setInt(1,cmbAcc_UnitCode);            		
	                            		ps3.setInt(2,originated_slno); 
	                            		ps3.setDate(3,originated_sldate1);
	                            		ps3.setInt(4,supNo);
	                            		rs2=ps3.executeQuery();
	                            		while(rs2.next())
	                            		{
	                            		System.out.println("select resjvrno,resjvrDate");	
	                            		respondingNo=rs2.getInt("RESPONDING_JVR_NO");
	                            		respondingDate=rs2.getDate("RESPONDING_JVR_DATE");
	                            		System.out.println("respondingDate:::"+respondingDate);
	                            		}
	                            		//11feb2013 by dhana
	                            		System.out.println("cancel responding jvr no and date from accepting side::");
	                            		ps_ac=con.prepareStatement("update FAS_TDA_TCA_RAISED_MST set RESPONDING_JVR_NO='0',RESPONDING_JVR_DATE =null " +
	                            		"where TRF_ACCOUNTING_UNIT_ID=? and VOUCHER_NO=? and VOUCHER_DATE=? and TDA_OR_TCA=? and SUPPLEMENT_NO=?");
	                            		
	                            		ps_ac.setInt(1,cmbAcc_UnitCode); System.out.println("trf cmbAcc_UnitCode"+cmbAcc_UnitCode);                   		
	                            		ps_ac.setInt(2,accepted_slno); System.out.println("accepted_slno"+accepted_slno); 
	                            		ps_ac.setDate(3,accepted_sldate); System.out.println("accepted_sldate"+accepted_sldate); 
	                            		if(Journal_type.equals("TDAR"))
	                            		ps_ac.setString(4,"TDAA");
	                            		else
	                            			ps_ac.setString(4,"TCAA");
	                            		ps_ac.setInt(4,supNo);
	                            		
	                            		int upd_acc=ps_ac.executeUpdate();
	                            		System.out.println(" upd_acc "+upd_acc);
	                            		if(upd_acc>0)
	                            		{
	                            			//cancel esponding jvr no and date from Originating side
	                            		ps=con.prepareStatement("update FAS_TDA_TCA_RAISED_MST set RESPONDING_JVR_NO='0',RESPONDING_JVR_DATE =null where ACCOUNTING_UNIT_ID=? and VOUCHER_NO=? and VOUCHER_DATE=? and SUPPLEMENT_NO=?");
	                            		
	                            		ps.setInt(1,cmbAcc_UnitCode); System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);                   		
	                            		ps.setInt(2,originated_slno); System.out.println("originated_slno"+originated_slno); 
	                            		ps.setDate(3,originated_sldate1); System.out.println("originated_sldate1"+originated_sldate1); 
	                            		ps.setInt(4,supNo);
	                            		
	                            		tt=ps.executeUpdate();
	                            		}
	                            		else
	                            		{
	                            			System.out.println("failure in accepting side of RESPONDING_JVR_NO");
	    	                                con.rollback();
	    	                                sendMessage(response,"Updation in FAS_TDA_TCA_RAISED_MST Failed ","ok"); 	
	                            		}
	                            		if(tt>0)
	                            		{
	                            			System.out.println("tt:"+tt);	
	                            			ps=con.prepareStatement("update FAS_JOURNAL_MASTER set JOURNAL_STATUS='C' where ACCOUNTING_UNIT_ID=? and VOUCHER_NO=? and VOUCHER_DATE=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SUPPLEMENT_NO=?");
	                            			ps.setInt(1,cmbAcc_UnitCode);System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
	                                		ps.setInt(2,respondingNo);System.out.println("respondingNo"+respondingNo);
	                                		ps.setDate(3,respondingDate);System.out.println("respondingDate:"+respondingDate);
	                                		ps.setInt(4,cashbook_year);System.out.println("cashbook_year"+cashbook_year);
	                                		ps.setInt(5,cashbook_month);System.out.println("cashbook_month"+cashbook_month);
	                                		ps.setInt(6,supNo);
	                                		int tt1=ps.executeUpdate();
	                                		
	                            			System.out.println("b4 commit");
			       		                        con.commit();
			       		                        sendMessage(response,"FAS_JOURNAL_MASTER::"+originated_slno+" has been Updated Successfully ","ok");		                        
	                            		}
	                            		else
	                            		{
		                            			System.out.println("b4 Rollback");
		    	                                con.rollback();
		    	                                sendMessage(response,"The FAS_TDA_TCA_RAISED_MST Failed ","ok");       
	                            		}
	                         
	                           
	                   
	                     
	             }             
	             
	             catch(Exception e)
	             {
	            	 	 System.out.println("Err in FAS_TDA_TCA_RAISED_MST updation ::: "+e.getMessage());
	            	 	 System.out.println("b4 Rollback");
	            	 	 try
	            	 	 {
	            	 			con.rollback();
	            	 	 }catch(Exception ee){System.out.println("Err in Insert::"+ee.getMessage());}
	                     sendMessage(response,"The FAS_TDA_TCA_RAISED_MST Failed ","ok");       
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

