package Servlets.FAS.FAS1.TDA.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

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

public class TDA_TCA_Responding_Create extends HttpServlet
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
	      PreparedStatement ps2=null;        
	      ResultSet rs2=null;
	      String sql="",type2="";
	        	      
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
                    con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());//
            }
            catch(Exception e)
            {
                	System.out.println("Exception in opening connection :"+e);
            }
        
	    
	    
            int count=0,cmbAcc_UnitCode=0,cmbOffice_code=0,cashbook_year=0,cashbook_month=0,originated_slno=0,txtUnitId=0,supNo=0;
            String xml=null,cmd="",option="",Journal_type="",reptype="";   
      
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
            
        try{type2=request.getParameter("type2");}
        catch(Exception e){System.out.println(e);}
            
        try{  reptype=request.getParameter("reptype");}
        catch(Exception e){System.out.println(e);}
        
        try{supNo=Integer.parseInt(request.getParameter("supNo"));}
        catch(Exception e){System.out.println("exception"+e );}
        
            System.out.println("cmd:::"+cmd);
            xml="<response>";
            
            if(cmd.equalsIgnoreCase("loadVoucher_SJV"))
            {     
                 
            	
            	System.out.println("loadVoucher dddddddddddddddddddd");
                    xml=xml+"<command>loadVoucher_SJV</command>";                   
                   
                    try
                    {
                    	if(reptype.equals("Regular"))
                    	{
                    	
                    	sql="select DISTINCT a.VOUCHER_NO as VOUCHER_NO \n" + 
                            "from FAS_TDA_TCA_RAISED_MST a,FAS_TDA_TCA_RAISED_MST b where \n" + 
                            "a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID and \n" + 
                            "a.ACCEPTING_SLNO=b.VOUCHER_NO and \n" + 
                            "a.ACCEPTING_DATE=b.VOUCHER_DATE and \n" + 
                            "a.ACCOUNTING_UNIT_ID=? and \n" + 
                         //   "a.ACCOUNTING_FOR_OFFICE_ID=? and \n" + 
                            "a.CASHBOOK_YEAR=? and \n" + 
                            "a.CASHBOOK_MONTH=? and \n" + 
                            "(a.TDA_OR_TCA=?  or a.TDA_OR_TCA=?) and \n" +
                            "a.STATUS='L' and " + 
                            " b.ACCEPTING_JVR_NO is not null and b.ACCEPTING_JVR_NO     !=0 and (a.RESPONDING_JVR_NO is null or a.RESPONDING_JVR_NO=0) AND (a.SUPPLEMENT_NO IS NULL OR a.SUPPLEMENT_NO =0)";
                    	}
                    	else if(reptype.equals("InclusiveSJV"))
                    	{
                    		sql="select DISTINCT a.VOUCHER_NO as VOUCHER_NO \n" + 
                                    "from FAS_TDA_TCA_RAISED_MST a,FAS_TDA_TCA_RAISED_MST b where \n" + 
                                    "a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID and \n" + 
                                    "a.ACCEPTING_SLNO=b.VOUCHER_NO and \n" + 
                                    "a.ACCEPTING_DATE=b.VOUCHER_DATE and \n" + 
                                    "a.ACCOUNTING_UNIT_ID=? and \n" + 
                                 //   "a.ACCOUNTING_FOR_OFFICE_ID=? and \n" + 
                                    "a.CASHBOOK_YEAR=? and \n" + 
                                    "a.CASHBOOK_MONTH=? and \n" + 
                                    "(a.TDA_OR_TCA=?  or a.TDA_OR_TCA=?) and \n" +
                                    "a.STATUS='L' and " + 
                                    "a.SUPPLEMENT_NO=? and "+
                                    " b.ACCEPTING_JVR_NO is not null and b.ACCEPTING_JVR_NO     !=0 and (a.RESPONDING_JVR_NO is null or a.RESPONDING_JVR_NO=0)";
                    	}
//                        sql=" SELECT VOUCHER_NO  FROM FAS_TDA_TCA_RAISED_MST " +
//                        " WHERE ACCOUNTING_UNIT_ID                =? " +
//                        " AND ACCOUNTING_FOR_OFFICE_ID          =? " + 
//                        " AND CASHBOOK_YEAR                     =? " + 
//                        " AND CASHBOOK_MONTH                    =? " +
//                        " AND ( TDA_OR_TCA                       =? OR TDA_OR_TCA                         =?) " + 
//                          " AND STATUS                            ='L' " +
//                           " AND ACCEPTING_JVR_NO                 IS NOT NULL  AND (RESPONDING_JVR_NO is null or RESPONDING_JVR_NO=0) ";
                        System.out.println(" SQL :: "+sql);
                             ps2=con.prepareStatement(sql);
                             ps2.setInt(1,cmbAcc_UnitCode);System.out.println("cmbAcc_UnitCode************"+cmbAcc_UnitCode);
                         //    ps2.setInt(2,cmbOffice_code);System.out.println("cmbOffice_code"+cmbOffice_code);
                             ps2.setInt(2,cashbook_year);System.out.println("cashbook_year"+cashbook_year);
                             ps2.setInt(3,cashbook_month);System.out.println("cashbook_month"+cashbook_month);
                             ps2.setString(4,Journal_type);System.out.println("Journal_type"+Journal_type);
                             ps2.setString(5,type2);System.out.println("type2"+type2);
                             
                             supNo=1;
                             if(reptype.equals("InclusiveSJV"))
                             {
                            	 ps2.setInt(6,supNo);System.out.println("supNo"+supNo);
                             }
                             ResultSet rs9=ps2.executeQuery();  
                        
                             while(rs9.next()) 
                             {System.out.println("while");
                                     xml+= "<voucher_no>"+ rs9.getInt("VOUCHER_NO") +"</voucher_no>";	                                     			                		                
                                     count++;
                             }					              
                             if(count>0)
                                 xml+="<flag>success</flag>";
                                     
                             else               
                        xml+="<flag>NoData</flag>";                                                
                                         
                    }
                    catch(Exception e) 
                    {
                             System.out.println("Exception in loadVoucher..."+e);
                             xml+="<flag>"+e.getMessage()+"</flag>";
                    }                      
            }	
            
            if(cmd.equalsIgnoreCase("loadVoucher"))
            {     
                 
            	
            	System.out.println("loadVoucher dddddddddddddddddddd");
                xml=xml+"<command>loadVoucher</command>";                   
               
                try
                {
                    sql="select  a.VOUCHER_NO as VOUCHER_NO \n" + 
                        "from FAS_TDA_TCA_RAISED_MST a,FAS_TDA_TCA_RAISED_MST b where \n" + 
                        "a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID and \n" + 
                        "a.ACCEPTING_SLNO=b.VOUCHER_NO and \n" + 
                        "a.ACCEPTING_DATE=b.VOUCHER_DATE and \n" + 
                        "a.ACCOUNTING_UNIT_ID=? and \n" + 
                     //   "a.ACCOUNTING_FOR_OFFICE_ID=? and \n" + 
                        "a.CASHBOOK_YEAR=? and \n" + 
                        "a.CASHBOOK_MONTH=? and \n" + 
                        "(a.TDA_OR_TCA=?  or a.TDA_OR_TCA=?) and \n" +
                        "a.STATUS='L' and " + 
                        " b.ACCEPTING_JVR_NO is not null and b.ACCEPTING_JVR_NO!=0 and (a.RESPONDING_JVR_NO is null or a.RESPONDING_JVR_NO=0) "; 
//                    sql=" SELECT VOUCHER_NO  FROM FAS_TDA_TCA_RAISED_MST " +
//                    " WHERE ACCOUNTING_UNIT_ID                =? " +
//                    " AND ACCOUNTING_FOR_OFFICE_ID          =? " + 
//                    " AND CASHBOOK_YEAR                     =? " + 
//                    " AND CASHBOOK_MONTH                    =? " +
//                    " AND ( TDA_OR_TCA                       =? OR TDA_OR_TCA                         =?) " + 
//                      " AND STATUS                            ='L' " +
//                       " AND ACCEPTING_JVR_NO                 IS NOT NULL  AND (RESPONDING_JVR_NO is null or RESPONDING_JVR_NO=0) ";
                    System.out.println(" SQL :: "+sql);
                         ps2=con.prepareStatement(sql);
                         ps2.setInt(1,cmbAcc_UnitCode);System.out.println("cmbAcc_UnitCode************"+cmbAcc_UnitCode);
                     //    ps2.setInt(2,cmbOffice_code);System.out.println("cmbOffice_code"+cmbOffice_code);
                         ps2.setInt(2,cashbook_year);System.out.println("cashbook_year"+cashbook_year);
                         ps2.setInt(3,cashbook_month);System.out.println("cashbook_month"+cashbook_month);
                         ps2.setString(4,Journal_type);System.out.println("Journal_type"+Journal_type);
                         ps2.setString(5,type2);System.out.println("type2"+type2);
                         ResultSet rs9=ps2.executeQuery();  
                    
                         while(rs9.next()) 
                         {System.out.println("while");
                                 xml+= "<voucher_no>"+ rs9.getInt("VOUCHER_NO") +"</voucher_no>";	                                     			                		                
                                 count++;
                         }					              
                         if(count>0)
                             xml+="<flag>success</flag>";
                                 
                         else               
                    xml+="<flag>NoData</flag>";                                                
                                     
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
                /*    sql="SELECT TO_CHAR(a.VOUCHER_DATE,'dd/mm/yyyy')   AS originated_sldate,\n" + 
                    "  a.ORGINATING_JVR_NO                         AS originated_jvr_no,\n" + 
                    "  TO_CHAR(a.ORGINATING_JVR_DATE,'dd/mm/yyyy') AS originated_jvr_date,\n" + 
                    "  a.ACCOUNTING_UNIT_ID,\n" + 
                    "  a.ACCOUNTING_FOR_OFFICE_ID,\n" + 
                    "  a.ACCEPTING_SLNO                                      AS accepted_slno,\n" + 
                    "  TO_CHAR(a.ACCEPTING_DATE,'dd/mm/yyyy')              AS accepted_sldate,\n" + 
                    "  a.ACCEPTING_JVR_NO                                AS accepted_jvr_no,\n" + 
                    "  TO_CHAR(a.ACCEPTING_JVR_DATE,'dd/mm/yyyy')        AS accepted_jvr_date,\n" + 
                    "  a.TRF_ACCOUNTING_UNIT_ID                          AS accepted_unit_id,\n" + 
                    "  trim(TO_CHAR(a.TOTAL_AMOUNT,'99999999999999.99')) AS AMOUNT,\n" + 
                    "  c.ACCOUNTING_UNIT_NAME\n" + 
                    "FROM FAS_TDA_TCA_RAISED_MST a,\n" + 
                    "  FAS_MST_ACCT_UNITS c\n" + 
                    "WHERE a.TRF_ACCOUNTING_UNIT_ID            =c.ACCOUNTING_UNIT_ID\n" + 
                    "AND a.ACCOUNTING_UNIT_ID                =?\n" + 
                    "AND a.ACCOUNTING_FOR_OFFICE_ID          =?\n" + 
                    "AND a.CASHBOOK_YEAR                     =?\n" + 
                    "AND a.CASHBOOK_MONTH                    =?\n" + 
                    "AND a.VOUCHER_NO                        =?\n" + 
                    "AND (a.TDA_OR_TCA                       =?\n" + 
                    "OR a.TDA_OR_TCA                         =?)\n" + 
                    "AND a.STATUS                            ='L'\n" + 
                    "AND a.ACCEPTING_JVR_NO                 IS NOT NULL\n" + 
                    "AND (a.RESPONDING_JVR_NO                  =0 or a.RESPONDING_JVR_NO is null)\n";   */
                    sql="select to_char(a.VOUCHER_DATE,'dd/mm/yyyy') as originated_sldate,   \n" + 
                    "                    a.ORGINATING_JVR_NO as originated_jvr_no,   \n" + 
                    "                    to_char(a.ORGINATING_JVR_DATE,'dd/mm/yyyy') as originated_jvr_date,  \n" + 
                    "                    b.ACCOUNTING_UNIT_ID,b.ACCOUNTING_FOR_OFFICE_ID,  \n" + 
                    "                    b.VOUCHER_NO as accepted_slno,   \n" + 
                    "                    to_char(b.VOUCHER_DATE,'dd/mm/yyyy') as accepted_sldate,   \n" + 
                    "                    b.ACCEPTING_JVR_NO as accepted_jvr_no,   \n" + 
                    "                    to_char(b.ACCEPTING_JVR_DATE,'dd/mm/yyyy') as accepted_jvr_date,   \n" + 
                    "                    b.TRF_ACCOUNTING_UNIT_ID as accepted_unit_id,   \n" + 
                    "                    trim(to_char(a.TOTAL_AMOUNT,'99999999999999.99')) as AMOUNT,  \n" + 
                    "                    c.ACCOUNTING_UNIT_NAME   \n" + 
                    "                    from FAS_TDA_TCA_RAISED_MST a,FAS_TDA_TCA_RAISED_MST b,FAS_MST_ACCT_UNITS c where   \n" + 
                    "                    a.TRF_ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID and    \n" + 
                    "                    a.ACCEPTING_SLNO=b.VOUCHER_NO and   \n" + 
                    "                    a.ACCEPTING_DATE=b.VOUCHER_DATE and  \n" + 
                    "                    a.TRF_ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and   \n" + 
                    "                    a.ACCOUNTING_UNIT_ID=? and   \n" + 
                  //  "                    a.ACCOUNTING_FOR_OFFICE_ID=? and   \n" + 
                    "                    a.CASHBOOK_YEAR=? and   \n" + 
                    "                    a.CASHBOOK_MONTH=? and  \n" + 
                    "                    a.VOUCHER_NO=? and   \n" + 
                    "                    (a.TDA_OR_TCA              =? OR a.TDA_OR_TCA                =?) AND  \n" + 
                    "                    a.STATUS='L' and   \n" + 
                    "                    b.ACCEPTING_JVR_NO is not null and b.ACCEPTING_JVR_NO     !=0 AND (a.RESPONDING_JVR_NO      IS NULL or a.RESPONDING_JVR_NO=0)";
                    System.out.println(" SQL :: "+sql);
                    try
                    {
                             ps2=con.prepareStatement(sql);
                             ps2.setInt(1,cmbAcc_UnitCode);
                          //   ps2.setInt(2,cmbOffice_code);
                             ps2.setInt(2,cashbook_year);
                             ps2.setInt(3,cashbook_month);
                             ps2.setInt(4,originated_slno);
                             ps2.setString(5,Journal_type);
                        ps2.setString(6,type2);
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
    public void doPost(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
    
         
         String strCommand="";
         Connection con=null;        
         PreparedStatement ps=null,ps1=null,ps2=null,ps3=null,ps4=null,ps5=null;
         String xml="";
         Statement st=null;
         ResultSet rs=null,rs5=null;
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
             int cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,journal_type_code=0,txtCreditHead=0,txtDebitHead=0,depriciation_rate=0,cashbook_year=0,cashbook_month=0;
             int count=0,sub_ledger_code=0,accepted_slno=0,Responding_JVR_No=0,trn_count=0,account_head_code=0,sub_ledger_type=0,org_sltype=0,org_slcode=0,acc_sltype=0,acc_slcode=0,originated_slno=0;
             double txtTotalAmt=0;
             Date txtCrea_date=null,accepted_sldate=null,accepted_jvr_date=null;
             String txtRemarks="",Journal_type="",particulars="",cr_dr_indicator="",flag="",sql="",tda_type="",originate_jvr_date="",accepting_jvr_date="";
             int originated_jvr_no_old=0,accepted_jvr_no=0,supplement_no=0;
                                     // changes here
             String update_user=(String)session.getAttribute("UserId");
             long l=System.currentTimeMillis();
             Timestamp ts=new Timestamp(l);           
             int accepted_month=0,accepted_year=0,accepted_office_id=0,accepted_unit_id=0;
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
             
            try{originated_jvr_no_old=Integer.parseInt(request.getParameter("originated_jvr_no"));
            supplement_no=Integer.parseInt(request.getParameter("supNo"));
            	}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("originated_jvr_no_old "+originated_jvr_no_old);
            
            // ACCEPTING DATE,NO,CASHBOOKYEAR,CASHBOOKMONTH
             
             String[] sd=request.getParameter("accepted_sldate").split("/");
             c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
             java.util.Date d=c.getTime();
             accepted_sldate=new Date(d.getTime());
             
             String[] sd1=request.getParameter("txtCrea_date").split("/");
             c=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
             java.util.Date d1=c.getTime();
             txtCrea_date=new Date(d1.getTime());
     if(sd1[2].length()>3) {
         System.out.println("year:**********"+Integer.parseInt(sd1[2]));
    
             try{txtCash_year=Integer.parseInt(sd1[2]);}
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("txtCash_year "+txtCash_year);
             
             try{txtCash_Month_hid=Integer.parseInt(sd1[1]);}
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
             
            String[] ss1=request.getParameter("accepted_jvr_date").split("/");
            c=new GregorianCalendar(Integer.parseInt(ss1[2]),Integer.parseInt(ss1[1])-1,Integer.parseInt(ss1[0]));
            java.util.Date d2=c.getTime();
            accepted_jvr_date=new Date(d2.getTime());
             
            try{accepted_year=Integer.parseInt(ss1[2]);}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("accepted_year**** "+accepted_year);
            
            try{accepted_month=Integer.parseInt(ss1[1]);}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("accepted_month "+accepted_month);
            
            try{accepted_jvr_no=Integer.parseInt(request.getParameter("accepted_jvr_no"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("accepted_jvr_no "+accepted_jvr_no);
            //ACCEPTED UNIT ID
            try{accepted_unit_id=Integer.parseInt(request.getParameter("accepted_unit_id"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("accepted_unit_id "+accepted_unit_id);
            //ACCEPTED OFFICE ID
            try{accepted_office_id=Integer.parseInt(request.getParameter("accepted_office_id"));}
            catch(Exception e){System.out.println("exception"+e );}
            System.out.println("accepted_office_id "+accepted_office_id);
            
           
             try{txtCreditHead=Integer.parseInt(request.getParameter("cr_accHead_code"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("txtDebitHead "+txtCreditHead);
          
             try{txtDebitHead=Integer.parseInt(request.getParameter("dr_accHead_code"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("txtDebitHead "+txtCreditHead);
             
             try{txtTotalAmt=Double.parseDouble(request.getParameter("txtTotalAmt"));}
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("txtAmount "+txtTotalAmt);
             
             try{Journal_type=request.getParameter("Journal_type");}
             catch(Exception e){System.out.println("Journal_type "+e );}
             System.out.println("Journal_type "+Journal_type);
             if(Journal_type.equals("TDAR"))	
            	 	 journal_type_code=64;
             else
            	 	 journal_type_code=67;
             
             try{originate_jvr_date=request.getParameter("originated_jvr_date");}
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("Originated jvr date *** :: "+originate_jvr_date);
             
             
             try{accepting_jvr_date=request.getParameter("accepted_jvr_date");}
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("Accepted jvr date *** :: "+accepting_jvr_date);
             //Sathya added on 11/02/2015 In case of Remarks null add all the other form details and save in journal master table *******                                 
             try{
            	 txtRemarks=request.getParameter("txtRemarks");
            	 
            	 if((txtRemarks.equalsIgnoreCase("")) || (txtRemarks.equalsIgnoreCase("null")) )
            	 {
            		 txtRemarks="originated_slno "+originated_slno+"/"+"originated jvr no and Date :::"+originated_jvr_no_old+"/"+originate_jvr_date+
            	                " /Total Amount ::"+txtTotalAmt+"/accepted unit id ::"+accepted_unit_id+"/accepted JVR NO,date:"+accepted_jvr_no+"/"+accepting_jvr_date;
            	 }
             }
             catch(NumberFormatException e){System.out.println("exception"+e );}
             System.out.println("txtRemarks "+txtRemarks);
             
             
             try
             {
                     ps=con.prepareStatement("select VOUCHER_NO from FAS_JOURNAL_MASTER GROUP BY VOUCHER_NO HAVING VOUCHER_NO =(select max(VOUCHER_NO) from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?)");
                     ps.setInt(1,cmbAcc_UnitCode);
                     ps.setInt(2,cmbOffice_code);
                     ps.setInt(3,txtCash_year);
                     ps.setInt(4,txtCash_Month_hid);                      
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
	                 ps1.setInt(7,journal_type_code);
	                 ps1.setInt(8,sub_ledger_code);
	                 ps1.setInt(9,2);
	                 ps1.setString(10,txtRemarks);
	                 ps1.setString(11,"A");
	                 ps1.setString(12,"GJV");
	                 ps1.setString(13,"L");
	                 ps1.setString(14,update_user);
	                 ps1.setTimestamp(15,ts);
	                 ps1.setInt(16,depriciation_rate);
	                 ps1.setInt(17,supplement_no);
	                 int kk=ps1.executeUpdate();
	                 if(kk>0)
	                 {
	                 		flag="success";
	                 		System.out.println("Flag ::: "+flag);
	                 }
	                 else
	                 		flag="failure";
	                 
	               //  System.out.println("Flag ------>"+flag);
                     
                     if(flag.equals("success"))
                     {
                    		ps1.close();
                    	 	try
                    	 	{
		                            sql="SELECT mst1.sub_ledger_type_code as org_sltype_code, \n" + 
		                            "  mst1.sub_ledger_code as org_slcode, \n" + 
		                            "  mst2.sub_ledger_type_code as acc_sltype_code, \n" + 
		                            "  mst2.TRF_ACCOUNTING_UNIT_ID as acc_slcode \n" + 
		                            "FROM fas_tda_tca_raised_mst mst1,fas_tda_tca_raised_mst mst2 \n" + 
		                            "WHERE  mst1.trf_accounting_unit_id=mst2.accounting_unit_id \n" + 
		                            " AND mst1.accepting_slno=mst2.voucher_no \n" + 
		                            " AND mst1.accepting_date=mst2.voucher_date \n" + 
		                            " AND mst1.accounting_unit_id =? \n" + 
		                          //  " AND mst1.accounting_for_office_id =? \n" + 
		                            " AND mst1.cashbook_year=? \n" +
		                            " AND mst1.cashbook_month=? " + 
		                            " AND mst1.voucher_no=? \n" + 
		                            " AND mst1.status='L' \n" + 
		                            " AND mst2.status='L' ";
		                        //    System.out.println("SL_Type sql ::: "+sql);
		                            ps1=con.prepareStatement(sql);
		                            ps1.setInt(1,cmbAcc_UnitCode);
		                    	 	//ps1.setInt(2,cmbOffice_code);
		                    	 	ps1.setInt(2,cashbook_year);
		                    	 	ps1.setInt(3,cashbook_month);
		                    	 	ps1.setInt(4,originated_slno);
		                            rs=ps1.executeQuery();
		                            if(rs.next())
		                            {
			                            	org_sltype=rs.getInt("org_sltype_code");
                                                     //   System.out.println("org_sltype::::"+org_sltype);
			                            	org_slcode=rs.getInt("org_slcode");
		                              //  System.out.println("org_slcode::::"+org_slcode);
			                            	acc_sltype=rs.getInt("acc_sltype_code");
			                            	acc_slcode=rs.getInt("acc_slcode");
		                             //   System.out.println("acc_slcode::::"+acc_slcode);
		                            }
		                    	 	
                    	 	}
                    	 	catch(Exception e)
                    	 	{
                    	 			System.out.println("Exception in sl code select :: "+e.getMessage());
                    	 	}
                    	 	for(int i=0;i<2;i++)
                    	 	{
                    	 		System.out.println("journal trn 2:::");
		                    	 	count++;		                    	 
		                    	 	if(i==0)
		                    	 	{
			                    	 		account_head_code=txtCreditHead;cr_dr_indicator="CR";
			                    	 		sub_ledger_type=org_sltype;
			                    	 		sub_ledger_code=org_slcode;
		                    	 	}
		                    	 	else
		                    	 	{
			                    	 		account_head_code=txtDebitHead;cr_dr_indicator="DR";
			                    	 		sub_ledger_type=org_sltype;
			                    	 		sub_ledger_code=acc_slcode;
		                    	 	}
//		                    	 	ps=con.prepareStatement("insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,AMOUNT,PARTICULARS,CB_REF_NO,CB_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		                    	 	ps=con.prepareStatement("insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,AMOUNT,PARTICULARS,CB_TDCA_REF_NO,CB_TDCA_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,CB_REF_NO)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");


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
		                        	ps.setString(12,txtRemarks);
		                        	ps.setInt(13,accepted_slno);
		                        	ps.setDate(14,accepted_sldate);
		                        	ps.setString(15,update_user);
		                        	ps.setTimestamp(16,ts);
		                        	ps.setInt(17,0);
		                        	int mm=ps.executeUpdate();
		                        	if(mm>0)
		                        			trn_count++;  
                    	 	}
                    	 	if(trn_count==2)
                            {
                    	 		System.out.println("trn");
                            		ps.close();
                            		ps=con.prepareStatement("update FAS_TDA_TCA_RAISED_MST set RESPONDING_JVR_NO=?,RESPONDING_JVR_DATE=?" +
                            		" where TRF_ACCOUNTING_UNIT_ID=? and VOUCHER_NO=? and VOUCHER_DATE=? and TDA_OR_TCA=? " +
                            		"AND (RESPONDING_JVR_NO      IS NULL or RESPONDING_JVR_NO=0) and RESPONDING_JVR_DATE is null ");
                            		ps.setInt(1,Responding_JVR_No);
                                        System.out.println("Responding_JVR_No::::"+Responding_JVR_No);
                            		ps.setDate(2,txtCrea_date);
                            //    System.out.println("txtCrea_date::::"+txtCrea_date);
                            		ps.setInt(3,cmbAcc_UnitCode);     
                           //     System.out.println("cmbAcc_UnitCode::::"+cmbAcc_UnitCode);
                            		ps.setInt(4,accepted_slno);
                            //    System.out.println("accepted_slno::::"+accepted_slno);
                            		ps.setDate(5,accepted_sldate);
                            //    System.out.println("accepted_sldate::::"+accepted_sldate);
                            		if(Journal_type.equals("TDAR"))
                            				ps.setString(6,"TDAA");
                            		else
                            				ps.setString(6,"TCAA");
                            		int tt=ps.executeUpdate();
                            		if(tt>0)
                            		{
                                                ps5=con.prepareStatement("select TDA_OR_TCA from FAS_TDA_TCA_RAISED_MST where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" AND CASHBOOK_YEAR ="+cashbook_year+" AND CASHBOOK_MONTH="+cashbook_month+" AND VOUCHER_NO="+originated_slno);
                                                rs5=ps5.executeQuery();
                                                while(rs5.next()) {
                                                    tda_type=rs5.getString("TDA_OR_TCA");
                                                    System.out.println("tda_type:::"+tda_type);
                                                }
                            			System.out.println("tt:"+tt);
                            			ps=con.prepareStatement("update FAS_TDA_TCA_RAISED_MST set RESPONDING_JVR_NO=?,RESPONDING_JVR_DATE=? where ACCOUNTING_UNIT_ID=? and VOUCHER_NO=? and TDA_OR_TCA=? AND (RESPONDING_JVR_NO      IS NULL or RESPONDING_JVR_NO=0) and RESPONDING_JVR_DATE is null");
                            			ps.setInt(1,Responding_JVR_No);
                                                System.out.println("Responding_JVR_No"+Responding_JVR_No);
                                		ps.setDate(2,txtCrea_date);
                                           //     System.out.println("txtCrea_date"+txtCrea_date);
                                		ps.setInt(3,cmbAcc_UnitCode);
                                           //     System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
                                		ps.setInt(4,originated_slno);
                                             //   System.out.println("originated_slno"+originated_slno);
                                             //   System.out.println("Journal_type*******"+Journal_type);
                                                ps.setString(5,tda_type);	
                                		int tt1=ps.executeUpdate();
                                                System.out.println("tt1"+tt1);
                                                if(tt1>0) 
                                                {
                                               // System.out.println("cbbbbbbbbbbbbbbbb"+tda_type);
                                                    if(tda_type.equals("TDAO") || tda_type.equals("TCAO"))
                                                    {
                                                    System.out.println("only for originating::::::::");
                                                        //updating originated journal numbers cb_fer_type
                                                        ps=con.prepareStatement("UPDATE FAS_JOURNAL_MASTER SET CB_REF_TYPE ='T' WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID   =? AND CASHBOOK_YEAR =? AND CASHBOOK_MONTH=? AND VOUCHER_NO=?");
                                                        ps.setInt(1,cmbAcc_UnitCode);
                                                      //  System.out.println("cmbAcc_UnitCode**NOT TDACB::"+cmbAcc_UnitCode);
                                                        ps.setInt(2,cmbOffice_code);
                                                   //     System.out.println("cmbOffice_code"+cmbOffice_code);
                                                        ps.setInt(3,cashbook_year);
                                                   //     System.out.println("cashbook_year"+cashbook_year);
                                                        ps.setInt(4,cashbook_month);
                                                   //     System.out.println("cashbook_month"+cashbook_month);
                                                        ps.setInt(5,originated_jvr_no_old);
                                                   //     System.out.println("originated_jvr_no_old"+originated_jvr_no_old); 
                                                        int test1=ps.executeUpdate();
                                                    }

                                                       //updating responding journal numbers cb_fer_type
                                                       ps3=con.prepareStatement("UPDATE FAS_JOURNAL_MASTER SET CB_REF_TYPE ='T' WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID   =? AND CASHBOOK_YEAR =? AND CASHBOOK_MONTH=? AND VOUCHER_NO=?");
                                                       ps3.setInt(1,cmbAcc_UnitCode);
                                                       ps3.setInt(2,cmbOffice_code);
                                                       ps3.setInt(3,txtCash_year);
                                                       ps3.setInt(4,txtCash_Month_hid);
                                                       ps3.setInt(5,Responding_JVR_No);
                                                       System.out.println("Responding_JVR_No"+Responding_JVR_No); 
                                                       int res=ps3.executeUpdate();
                                                       if(res>0) 
                                                       {
                                                       //updating accepted journal numbers cb_fer_type
                                                           ps4=con.prepareStatement("UPDATE FAS_JOURNAL_MASTER SET CB_REF_TYPE ='T' WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID   =? AND CASHBOOK_YEAR =? AND CASHBOOK_MONTH=? AND VOUCHER_NO=?");
                                                           ps4.setInt(1,accepted_unit_id);
                                                           System.out.println("accepted_unit_id"+accepted_unit_id);
                                                           ps4.setInt(2,accepted_office_id);
                                                       //    System.out.println("accepted_office_id"+accepted_office_id);
                                                           ps4.setInt(3,accepted_year);
                                                        //   System.out.println("accepted_year"+accepted_year);
                                                           ps4.setInt(4,accepted_month);
                                                       //    System.out.println("accepted_month"+accepted_month);
                                                           ps4.setInt(5,accepted_jvr_no);
                                                        //   System.out.println("accepted_jvr_no"+accepted_jvr_no); 
                                                           ps4.executeUpdate();
                                                           
                                                       }
                                                       else 
                                                        {
                                                            System.out.println("Rollback in updating Originating Journals CBrefType");
                                                            con.rollback();
                                                            sendMessage(response,"Updating CBRefType in AcceptedJournal is Failed ","ok");
                                                        }
                                                 
                                                }
                                                else {
                                                    System.out.println("Rollback in updating Originating Journals CBrefType");
                                                    con.rollback();
                                                    sendMessage(response,"Updating CBRefType in OriginatingJournal is Failed ","ok");
                                                }
                                		
                            			System.out.println("b4 commit");
		       		                        con.commit();
		       		                        sendMessage(response,"The Post Journal Voucher Number "+Responding_JVR_No+" has been Created Successfully ","ok");		                        
                            		}
                            		else
                            		{
	                            			System.out.println("b4 Rollback");
	    	                                con.rollback();
	    	                                sendMessage(response,"Updating RESPONDING_JVR_NO is Failed ","ok");       
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
        }//end of responding date date:
            
            else {
                System.out.println("else::::::::****:::::"+Integer.parseInt(sd1[2]));
                sendMessage(response,"The Suspense Head Failed because of suspense Head Clearence Date: "+txtCrea_date,"ok");
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
