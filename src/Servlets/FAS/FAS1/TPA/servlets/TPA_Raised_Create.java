package Servlets.FAS.FAS1.TPA.servlets;

import Servlets.FAS.FAS1.CommonControls2.servlets.GL_SL_Checking_for_TPA;
import Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_GENERAL;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.Calendar;

import javax.servlet.*;
import javax.servlet.http.*;

public class TPA_Raised_Create extends HttpServlet
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
	      DecimalFormat df=new DecimalFormat("#0.00");
	     
	      /**
	       * Variables Declaration 
	      */		        
	      Connection con=null;
	      PreparedStatement ps2=null,ps1=null;        
	      ResultSet rs2=null;
	      String sql=null;
	      PreparedStatement ps=null;        
	      ResultSet rs=null;
	      PreparedStatement ps3=null;        
	      ResultSet rs3=null,rs4=null;	
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
        
	    
            int count=0,g_Count=0,AccUnitId=0,officeCode=0,cashBookMonth=0,cashBookYear=0;
            String xml=null,cmd=null,option=null;          
      int cnt=0;
            /** Get Employee ID */
            try{cmd=request.getParameter("command");}
            catch(Exception e){System.out.println(e);}
            
            try{option=request.getParameter("Option");}
            catch(Exception e){System.out.println(e);}
            
            try{AccUnitId=Integer.parseInt(request.getParameter("txtUnitId"));}
            catch(Exception e){System.out.println(e);}
            
            try{officeCode=Integer.parseInt(request.getParameter("cmbOffice_code"));}
            catch(Exception e){System.out.println(e);}
            
            System.out.println("cmd:::"+cmd);
            xml="<response>";
            System.out.println("TPA Raised");
            if(cmd.equalsIgnoreCase("loadGlSlGrid"))
            {     
                 
                    xml=xml+"<command>loadGlSlGrid</command>";
                    try
                    {
                    		 // Find cashbook month & year from TB status table 
                    	String crDrIndicator=request.getParameter("crdrindicator");
                    	String crDR="";
                    	if(crDrIndicator.equalsIgnoreCase("CR"))
                    	crDR="DR";
                    	else
                    		crDR="CR";
                    	
                    	int minorHeads=Integer.parseInt(request.getParameter("minorheads"));
                    	
                    	String majorMinor=" MAJOR_HEAD_CODE in('A','L')";
                    	
                    	if(minorHeads==0)
                    	{
                    		majorMinor="MAJOR_HEAD_CODE in('A','L')";
                    	}else{
                    		majorMinor=" MINOR_HEAD_CODE="+minorHeads;	
                    	}
                    System.out.println("crDrIndicator"+crDrIndicator)	;	
                    System.out.println("majorMinor"+majorMinor)	;
                    System.out.println("minorHeads"+minorHeads)	;
                    	
                    		/* try
                    		 {
                    			 	sql="select max(CASHBOOK_YEAR) as CASHBOOK_YEAR,max(CASHBOOK_MONTH) as CASHBOOK_MONTH from FAS_TRIAL_BALANCE_STATUS where accounting_unit_id=?";
                    			 	ps2=con.prepareStatement(sql);
                    			 	ps2.setInt(1,AccUnitId);
                    			 	rs2=ps2.executeQuery();
                    			 	if(rs2.next())
                    			 	{
                    			 			cashBookYear=rs2.getInt("CASHBOOK_YEAR");
                    			 			cashBookMonth=rs2.getInt("CASHBOOK_MONTH");
                    			 	}
                    			 		
                    		 }
                    		 catch(Exception e)
                    		 {
                    			 	System.out.println("Exception in finding cashbook_month and cashbook_year ::: "+e.getMessage());
                    		 }*/
                    		 
                    		 cashBookYear=Integer.parseInt(request.getParameter("cashbookyear"));
                    		 cashBookMonth=Integer.parseInt(request.getParameter("cashbookmonth"));
                    		 
                    		 BigDecimal cr_amt=new BigDecimal(0.00);
                    		 BigDecimal dr_amt=new BigDecimal(0.00);
                    		 
                    		 BigDecimal gen_cr_amt=new BigDecimal(0.00);
                    		 BigDecimal gen_dr_amt=new BigDecimal(0.00);
                    		 
                    		 
                    		 //--------------------------------------------------
                    	/*	 GL_SL_Checking_for_TPA obj=new GL_SL_Checking_for_TPA();                    		
                    		 rs2=obj.getGLSLData(con,AccUnitId,officeCode,cashBookMonth,cashBookYear);  
                    		 
                    		 BigDecimal cr_amt=new BigDecimal(0.00);
                    		 BigDecimal dr_amt=new BigDecimal(0.00);
                    		 
                    		 try
                    		 {
		                             while(rs2.next()) 
		                             {         
		                            	     
		                                     xml+= "<account_head_code>"+ rs2.getInt("account_head_code") +"</account_head_code>";
		                                     xml+= "<account_head_desc>"+ rs2.getString("account_head_desc") +"</account_head_desc>";
		                                     xml+= "<sub_ledger_type_code>"+rs2.getInt("sub_ledger_type_code")+"</sub_ledger_type_code>";
		                                     xml+= "<sub_ledger_type_desc>"+rs2.getString("sub_ledger_type_desc")+"</sub_ledger_type_desc>";
		                                     xml+= "<sub_ledger_code>"+ rs2.getInt("sub_ledger_code") +"</sub_ledger_code>";		                                     
		           
		                                     xml+= "<cr_dr_indicator>"+ rs2.getString("month_closing_bal_dr_cr_ind") +"</cr_dr_indicator>";
		                                     if(rs2.getString("month_closing_bal_dr_cr_ind").equals("CR"))
		                                    	 cr_amt=cr_amt.add(rs2.getBigDecimal("month_closing_balance"));
		                                     else
		                                    	 dr_amt=dr_amt.add(rs2.getBigDecimal("month_closing_balance"));
		                                     
		                                     xml+= "<closing_balance>"+ rs2.getString("month_closing_balance") +"</closing_balance>";  
		                                     
			                                 if(rs2.getInt("sub_ledger_type_code")!=0 && rs2.getInt("sub_ledger_code")!=0)
			                                 {
					                                SL_TYPE_CODE_NAME_GENERAL obj_gen=new SL_TYPE_CODE_NAME_GENERAL();
					                                ResultSet rs_get=obj_gen.getResult_General(AccUnitId,officeCode,rs2.getInt("sub_ledger_type_code"),rs2.getInt("sub_ledger_code"),0);
					                                if(rs_get!=null)
					                                {
			                                            while(rs_get.next())
			                                            {		
			                                            	xml+= "<sub_ledger_desc>"+ rs_get.getString("cname") +"</sub_ledger_desc>";  				                                              
			                                            }
			                                            rs_get.close();
					                                }
					                                else
					                                {
				                                      	System.out.println("null result set");
				                                      	xml+= "<sub_ledger_desc>--</sub_ledger_desc>";  
					                                }
			                                 }
			                                 else
			                                	 	xml+= "<sub_ledger_desc>--</sub_ledger_desc>";  	
		                                     count++;
		                             }	
                    		 }
                    		 catch(Exception e)
                    		 {
                    			 	 System.out.println("Err in retrive values "+e.getMessage());
                    		 }
                    		 xml=xml+"<cr_amt>"+cr_amt+"</cr_amt>";
                    		 xml=xml+"<dr_amt>"+dr_amt+"</dr_amt>";
                             if(count==0) 
                                     xml+="<flag>NoData</flag>";					           
                             else               
                                     xml+="<flag>success</flag>";
                             
                             */
                             
                             
                      String slSQL="select a.*,b.account_head_desc, c.sub_ledger_type_desc from \n"+  
             " (SELECT \n"+  
              " accounting_unit_id,  \n"+
               " accounting_for_office_id, \n"+  
               " year,  \n"+
               " month,  \n"+
               " account_head_code,  \n"+ 
               " sub_ledger_type_code,   \n"+
               " sub_ledger_code, \n"+
               " month_closing_bal_dr_cr_ind,  \n"+
               " trim(to_char(month_closing_balance,'99999999999999.99')) as month_closing_balance  \n"+
           "FROM  \n"+
             "  fas_sub_ledger_master  \n"+
           " WHERE   \n"+
             "  accounting_unit_id=? AND  \n"+
               " accounting_for_office_id=? AND  \n"+
               " year=? AND  \n"+
               " month=? AND \n" +
               " month_closing_bal_dr_cr_ind=? AND \n"+
               "  account_head_code in (select account_head_code from COM_MST_ACCOUNT_HEADS " +
               "where "+majorMinor+") and account_head_code in(select ACCOUNT_HEAD_CODE from FAS_TPA_HEADS) \n"+
               ")a  left outer join com_mst_account_heads b on a.account_head_code=b.account_head_code  \n"+
              " left outer join com_mst_sl_types c on a.sub_ledger_type_code=c.sub_ledger_type_code"  ;     
                 System.out.println("slSQL:::"+slSQL);
                 ps=con.prepareStatement(slSQL);
                                 
                 ps.setInt(1, AccUnitId);             
                 ps.setInt(2, officeCode); 
                 ps.setInt(3, cashBookYear); 
                 ps.setInt(4, cashBookMonth); 
                 ps.setString(5, crDrIndicator); 
                 rs=ps.executeQuery();            
                             
           		 try
        		 {
                         while(rs.next()) 
                         { 
                        	 String sql1="select * from FAS_TPA_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_MONTH=? and CASHBOOK_YEAR=?  and ACCOUNT_HEAD_CODE=? and SUB_LEDGER_TYPE_CODE=? and SUB_LEDGER_CODE=?";
                        	// System.out.println("Records EXists************************************************"+sql1);
                        	 ps1=con.prepareStatement(sql1);
                             
                             ps1.setInt(1, AccUnitId);   
                            // System.out.println("Records EXists************************************************"+rs.getInt("account_head_code"));
                             ps1.setInt(2, officeCode); 
                             //System.out.println("Records EXists************************************************"+rs.getInt("account_head_code"));
                             ps1.setInt(3, cashBookMonth); 
                            // System.out.println("Records EXists************************************************"+rs.getInt("cashBookYear"));
                             ps1.setInt(4, cashBookYear); 
                             ps1.setInt(5, rs.getInt("account_head_code")); 
                           //  System.out.println("Records EXists************************************************"+rs.getInt("account_head_code"));
                             ps1.setInt(6, rs.getInt("sub_ledger_type_code")); 
                           //  System.out.println("Records EXists************************************************"+rs.getInt("sub_ledger_type_code"));
                             ps1.setInt(7, rs.getInt("sub_ledger_code")); 
                          //   System.out.println("Records EXists************************************************"+rs.getInt("sub_ledger_code"));
                             rs4=ps1.executeQuery();
                             //System.out.println("Records EXists************************************************"+rs4.next());
                             if(rs4.next())
                             {
                            	 System.out.println("Records EXists************************************************");
                             }
                             else
                             {
                        	    int clBalance=0; 
                        	    int glBalance=0;
                        	 int acHeadCode=rs.getInt("account_head_code");
                                 xml+= "<account_head_code>"+ acHeadCode +"</account_head_code>";
                                 xml+= "<account_head_desc>"+ rs.getString("account_head_desc") +"</account_head_desc>";
                                 xml+= "<sub_ledger_type_code>"+rs.getInt("sub_ledger_type_code")+"</sub_ledger_type_code>";
                                 xml+= "<sub_ledger_type_desc>"+rs.getString("sub_ledger_type_desc")+"</sub_ledger_type_desc>";
                                 xml+= "<sub_ledger_code>"+ rs.getInt("sub_ledger_code") +"</sub_ledger_code>";		                                     
       
                                // xml+= "<cr_dr_indicator>"+ rs.getString("month_closing_bal_dr_cr_ind") +"</cr_dr_indicator>";
                                 xml+= "<cr_dr_indicator>"+ crDR +"</cr_dr_indicator>"; 
                                 
                                 
                                 if(rs.getString("month_closing_bal_dr_cr_ind").equals("CR"))
                                	 cr_amt=cr_amt.add(rs.getBigDecimal("month_closing_balance"));
                                 else
                                	 dr_amt=dr_amt.add(rs.getBigDecimal("month_closing_balance"));
                                 
                                 xml+= "<closing_balance>"+ df.format(rs.getBigDecimal("month_closing_balance")) +"</closing_balance>";  
                                 
                            ps3=con.prepareStatement(" select sum(month_closing_balance) as clbalance from fas_sub_ledger_master where \n"+
              " accounting_unit_id=? AND \n"+ 
               " accounting_for_office_id=? AND  \n"+ 
               " year=? AND  \n"+ 
               " month=? AND \n"+ 
               " account_head_code=? AND month_closing_bal_dr_cr_ind=? group by account_head_code")  ;   
                                 
                            ps3.setInt(1, AccUnitId);             
                            ps3.setInt(2, officeCode); 
                            ps3.setInt(3, cashBookYear); 
                            ps3.setInt(4, cashBookMonth);              
                            ps3.setInt(5, acHeadCode);
                            ps3.setString(6, crDrIndicator);
                            rs3=ps3.executeQuery();     
                                 if(rs3.next())
                                 {
                                	 clBalance= rs3.getInt("clbalance");
                                 
                                 }
                                 
                                 ps3=con.prepareStatement(" select sum(month_closing_balance) as glbalance from fas_general_ledger where \n"+
                                         " accounting_unit_id=? AND \n"+ 
                                          " accounting_for_office_id=? AND  \n"+ 
                                          " year=? AND  \n"+ 
                                          " month=? AND \n"+ 
                                          " account_head_code=? AND month_closing_bal_dr_cr_ind=? group by account_head_code")  ;   
                                                            
                                                       ps3.setInt(1, AccUnitId);             
                                                       ps3.setInt(2, officeCode); 
                                                       ps3.setInt(3, cashBookYear); 
                                                       ps3.setInt(4, cashBookMonth);              
                                                       ps3.setInt(5, acHeadCode);  
                                                       ps3.setString(6, crDrIndicator);
                                                       rs3=ps3.executeQuery();     
                                                            if(rs3.next())
                                                            {
                                                           	 glBalance= rs3.getInt("glbalance");
                                                           	
                                                            }   
                                 if(glBalance!=0 && clBalance!=0)
                                 {
                                 if(glBalance==clBalance)
                                 {
                                	 xml+= "<balanceindicator>Black</balanceindicator>";
                                	 System.out.println("total balance Sl"+clBalance+"GL"+glBalance);
                                 }else{
                                	 xml+= "<balanceindicator>Red</balanceindicator>";	 
                                 }
                                 }else{
                                	 xml+= "<balanceindicator>Red</balanceindicator>";
                                 }
                                 
                                 if(rs.getInt("sub_ledger_type_code")!=0 && rs.getInt("sub_ledger_code")!=0)
                                 {
		                                SL_TYPE_CODE_NAME_GENERAL obj_gen=new SL_TYPE_CODE_NAME_GENERAL();
		                                ResultSet rs_get=obj_gen.getResult_General(AccUnitId,officeCode,rs.getInt("sub_ledger_type_code"),rs.getInt("sub_ledger_code"),0);
		                                String slcheck="";
		                                if(rs_get!=null)
		                                {
                                            while(rs_get.next())
                                            {	
                                            	if(rs_get.getInt("cid")==rs.getInt("sub_ledger_code") )
                                            	{
                                            		slcheck=rs_get.getString("cname");
                                            		xml+= "<sub_ledger_desc>"+ slcheck +"</sub_ledger_desc>"; 
                                            	}
                                            	
                                            	
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
	                                      	xml+= "<sub_ledger_desc>--</sub_ledger_desc>";  
		                                }
                                 }
                                 else
                                	 	xml+= "<sub_ledger_desc>--</sub_ledger_desc>";  	
                                 count++;
                         }	
        		 
        		 } 
        		 }
        		 catch(Exception e)
        		 {
        			 	 System.out.println("Err in retrive values "+e.getMessage());
        		 }
           		 
           		 
           	slSQL= "select a.*,b.account_head_desc, c.sub_ledger_type_desc from \n"+   
            " (SELECT  \n"+    
                "   accounting_unit_id, \n"+    
                "   accounting_for_office_id,  \n"+    
                "   year,  \n"+   
                "   month,  \n"+   
                "  account_head_code,  \n"+    
                "   0 AS sub_ledger_type_code,  \n"+    
               "     0 AS sub_ledger_code, \n"+   
                "    month_closing_bal_dr_cr_ind, \n"+    
                 "   trim(to_char(month_closing_balance,'99999999999999.99')) as month_closing_balance  \n"+   
               " FROM  \n"+   
                "    fas_general_ledger  \n"+   
               " WHERE   \n"+   
                "    accounting_unit_id=? AND  \n"+   
                 "   accounting_for_office_id=? AND  \n"+   
                  "  year=? AND  \n"+   
                   " month=? AND \n" +
                   " month_closing_bal_dr_cr_ind=? AND \n"+
                   "  account_head_code in (select account_head_code from COM_MST_ACCOUNT_HEADS where "+majorMinor+")   and account_head_code in(select ACCOUNT_HEAD_CODE from FAS_TPA_HEADS)\n"+
                   "   )a  left outer join com_mst_account_heads b on a.account_head_code=b.account_head_code  \n"+   
                  " left outer join com_mst_sl_types c on a.sub_ledger_type_code=c.sub_ledger_type_code ";	 
            ps=con.prepareStatement(slSQL);
            ps.setInt(1, AccUnitId);             
            ps.setInt(2, officeCode); 
            ps.setInt(3, cashBookYear); 
            ps.setInt(4, cashBookMonth);
            ps.setString(5, crDrIndicator); 
            rs=ps.executeQuery();   
            try
   		 {
                    while(rs.next()) 
                    { 
                    	
                    	
                    	String sql1="select * from FAS_TPA_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_MONTH=? and CASHBOOK_YEAR=?  and ACCOUNT_HEAD_CODE=? and SUB_LEDGER_TYPE_CODE=? and SUB_LEDGER_CODE=?";
                   	// System.out.println("Records EXists************************************************"+sql1);
                   	 ps1=con.prepareStatement(sql1);
                        
                        ps1.setInt(1, AccUnitId);   
                       // System.out.println("Records EXists************************************************"+rs.getInt("account_head_code"));
                        ps1.setInt(2, officeCode); 
                        //System.out.println("Records EXists************************************************"+rs.getInt("account_head_code"));
                        ps1.setInt(3, cashBookMonth); 
                       // System.out.println("Records EXists************************************************"+rs.getInt("cashBookYear"));
                        ps1.setInt(4, cashBookYear); 
                        ps1.setInt(5, rs.getInt("account_head_code")); 
                    //    System.out.println("Records EXists************************************************"+rs.getInt("account_head_code"));
                        ps1.setInt(6, rs.getInt("sub_ledger_type_code")); 
                     //   System.out.println("Records EXists************************************************"+rs.getInt("sub_ledger_type_code"));
                        ps1.setInt(7, rs.getInt("sub_ledger_code")); 
                   //     System.out.println("Records EXists************************************************"+rs.getInt("sub_ledger_code"));
                        rs4=ps1.executeQuery();
                        //System.out.println("Records EXists************************************************"+rs4.next());
                        if(rs4.next())
                        {
                       	 System.out.println("Records EXists************************************************");
                        }
                        else
                        {
                    	
                    	
                    	
                    	
                   	     int clBalance=0;
                   	  int glBalance=0;
                   	 int acHeadCode=rs.getInt("account_head_code");
                            xml+= "<g_account_head_code>"+ rs.getInt("account_head_code") +"</g_account_head_code>";
                            xml+= "<g_account_head_desc>"+ rs.getString("account_head_desc") +"</g_account_head_desc>";
                            xml+= "<g_sub_ledger_type_code>"+rs.getInt("sub_ledger_type_code")+"</g_sub_ledger_type_code>";
                            xml+= "<g_sub_ledger_type_desc>"+rs.getString("sub_ledger_type_desc")+"</g_sub_ledger_type_desc>";
                            xml+= "<g_sub_ledger_code>"+ rs.getInt("sub_ledger_code") +"</g_sub_ledger_code>";		                                     
  
                            //xml+= "<g_cr_dr_indicator>"+ rs.getString("month_closing_bal_dr_cr_ind") +"</g_cr_dr_indicator>";
                            xml+= "<g_cr_dr_indicator>"+ crDR +"</g_cr_dr_indicator>"; 
                            
                            if(rs.getString("month_closing_bal_dr_cr_ind").equals("CR"))
                            	 gen_cr_amt= gen_cr_amt.add(rs.getBigDecimal("month_closing_balance"));
                            else
                            	 gen_dr_amt= gen_dr_amt.add(rs.getBigDecimal("month_closing_balance"));
                           
                            xml+= "<g_closing_balance>"+  df.format(rs.getBigDecimal("month_closing_balance")) +"</g_closing_balance>";  
                            
                            
                            
                            ps3=con.prepareStatement(" select sum(month_closing_balance) as clbalance from fas_sub_ledger_master where \n"+
                                    " accounting_unit_id=? AND \n"+ 
                                     " accounting_for_office_id=? AND  \n"+ 
                                     " year=? AND  \n"+ 
                                     " month=? AND \n"+ 
                                     " account_head_code=? AND month_closing_bal_dr_cr_ind=? group by account_head_code")  ;   
                                                       
                                                  ps3.setInt(1, AccUnitId);             
                                                  ps3.setInt(2, officeCode); 
                                                  ps3.setInt(3, cashBookYear); 
                                                  ps3.setInt(4, cashBookMonth);              
                                                  ps3.setInt(5, acHeadCode);
                                                  ps3.setString(6, crDrIndicator);     
                                                  rs3=ps3.executeQuery();     
                                                       if(rs3.next())
                                                       {
                                                      	 clBalance= rs3.getInt("clbalance");
                                                      
                                                       }
                                                       
                                                       ps3=con.prepareStatement(" select sum(month_closing_balance) as glbalance from fas_general_ledger where \n"+
                                                               " accounting_unit_id=? AND \n"+ 
                                                                " accounting_for_office_id=? AND  \n"+ 
                                                                " year=? AND  \n"+ 
                                                                " month=? AND \n"+ 
                                                                " account_head_code=? AND month_closing_bal_dr_cr_ind=? group by account_head_code")  ;   
                                                                                  
                                                                             ps3.setInt(1, AccUnitId);             
                                                                             ps3.setInt(2, officeCode); 
                                                                             ps3.setInt(3, cashBookYear); 
                                                                             ps3.setInt(4, cashBookMonth);              
                                                                             ps3.setInt(5, acHeadCode);
                                                                             ps3.setString(6, crDrIndicator);
                                                                             rs3=ps3.executeQuery();     
                                                                                  if(rs3.next())
                                                                                  {
                                                                                 	 glBalance= rs3.getInt("glbalance");
                                                                                 	
                                                                                  }   
                                                       if(glBalance!=0 && clBalance!=0)
                                                       {
                                                       if(glBalance==clBalance)
                                                       {
                                                    	   
                                                      	 xml+= "<g_balanceindicator>Black</g_balanceindicator>";
                                                     	System.out.println("total balance Sl"+clBalance+"GL"+glBalance);
                                                       }else{
                                                      	 xml+= "<g_balanceindicator>Red</g_balanceindicator>";	 
                                                       }
                                                       }else{
                                                    	   xml+= "<g_balanceindicator>Red</g_balanceindicator>"; 
                                                       }
                            
                            
                            if(rs.getInt("sub_ledger_type_code")!=0 && rs.getInt("sub_ledger_code")!=0)
                            {
	                                SL_TYPE_CODE_NAME_GENERAL obj_gen=new SL_TYPE_CODE_NAME_GENERAL();
	                                ResultSet rs_get=obj_gen.getResult_General(AccUnitId,officeCode,rs.getInt("sub_ledger_type_code"),rs.getInt("sub_ledger_code"),0);
	                                if(rs_get!=null)
	                                {
                                       while(rs_get.next())
                                       {		
                                       	xml+= "<g_sub_ledger_desc>"+ rs_get.getString("cname") +"</g_sub_ledger_desc>";  				                                              
                                       }
                                       rs_get.close();
	                                }
	                                else
	                                {
                                     	System.out.println("null result set");
                                     	xml+= "<g_sub_ledger_desc>--</g_sub_ledger_desc>";  
	                                }
                            }
                            else
                           	 	xml+= "<g_sub_ledger_desc>--</g_sub_ledger_desc>";  	
                            g_Count++;
                    }
                    }
   		 
   		 } 
      		 
   		 catch(Exception e)
   		 {
   			 	 System.out.println("Err in retrive values "+e.getMessage());
   		 }	 
           		 
   		 
        		 
        		 xml=xml+"<cr_amt>"+cr_amt+"</cr_amt>";
        		 xml=xml+"<dr_amt>"+dr_amt+"</dr_amt>";
        		 
        		 xml=xml+"<g_cr_amt>"+gen_cr_amt+"</g_cr_amt>";
        		 xml=xml+"<g_dr_amt>"+gen_dr_amt+"</g_dr_amt>";
                 if(count==0 && g_Count==0) 
                         xml+="<flag>NoData</flag>";					           
                 else               
                         xml+="<flag>success</flag>";
                            
  
                                         
                    }
                    catch(Exception e) 
                    {
                    	e.printStackTrace();
                             System.out.println("Exception in loadGlSlGrid..."+e);
                             xml+="<flag>"+e.getMessage()+"</flag>";
                    }                      
            }
            else if(cmd.equalsIgnoreCase("checkauthorization"))
            {     
            	String crDrIndicator=request.getParameter("crdrindicator");
            	int cashYear=0,cashMonth=0;
            	
            	try{AccUnitId=Integer.parseInt(request.getParameter("txtUnitId"));}
                catch(Exception e){System.out.println(e);}
                
                               
                try{cashYear=Integer.parseInt(request.getParameter("cashbookyear"));}
                catch(Exception e){System.out.println(e);}
                
                try{cashMonth=Integer.parseInt(request.getParameter("cashbookmonth"));}
                catch(Exception e){System.out.println(e);}
                
                
                
                String vDate=request.getParameter("vdate");
                
                String reason=request.getParameter("reason");
                
                System.out.println("AccUnitId"+AccUnitId);
                System.out.println("crDrIndicator"+crDrIndicator);
                System.out.println("reason"+reason);
                System.out.println("cashYear"+cashYear);
                System.out.println("cashMonth"+cashMonth);
                
                    xml=xml+"<command>checkauthorization</command>";
                    try
                    {
                    	
                    cnt++;
                  int y=0,z=0;
                    
                    	ps=con.prepareStatement("select AUTHORIZED_ACCOUNTING_UNIT_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ACCEPT_ACCOUNTING_UNIT_ID,REASON_FOR_TRANSFER,TPA_TYPE,to_char(EFFECTIVE_DATE,'dd/MM/yyyy') as EFFECTIVE_DATE from FAS_TPA_AHUTHORIZATION_SYSTEM where AUTHORIZED_ACCOUNTING_UNIT_ID=?");
                    	ps.setInt(1, AccUnitId);
                    	//ps.setString(2, crDrIndicator);
                    	
                    	//ps.setInt(3, cashYear);
                    	//ps.setInt(4, cashMonth);
                    	//ps.setString(5, vDate);
                    	rs=ps.executeQuery();
                    	while(rs.next())
                    	{
                    		String CR_DR=rs.getString("TPA_TYPE");
                    		if(CR_DR.equalsIgnoreCase("CR"))
                    			CR_DR="TPAOC";
                    		else
                    			CR_DR="TPAOD";
                    		System.out.println("CR_DR   ####"+CR_DR);
                    		ps1=con.prepareStatement("select * from FAS_TPA_MASTER where ACCOUNTING_UNIT_ID=? and CASHBOOK_MONTH=? and CASHBOOK_YEAR=? and REASON_FOR_TRANSFER=? and TRF_ACCOUNTING_UNIT_ID=? and TPA_TYPE=? and STATUS='L' ");
                        	ps1.setInt(1, rs.getInt("AUTHORIZED_ACCOUNTING_UNIT_ID"));
                        	ps1.setInt(2, rs.getInt("CASHBOOK_MONTH"));
                        	ps1.setInt(3, rs.getInt("CASHBOOK_YEAR"));
                        	ps1.setString(4, rs.getString("REASON_FOR_TRANSFER"));
                        	ps1.setInt(5, rs.getInt("ACCEPT_ACCOUNTING_UNIT_ID"));
                        	ps1.setString(6, CR_DR);
                    		rs2=ps1.executeQuery();
                    		
                    		/*System.out.println("AUTHORIZED_ACCOUNTING_UNIT_ID   ####"+rs.getInt("AUTHORIZED_ACCOUNTING_UNIT_ID"));
                    		System.out.println("CASHBOOK_MONTH   ####"+rs.getInt("CASHBOOK_MONTH"));
                    		System.out.println("CASHBOOK_YEAR   ####"+rs.getInt("CASHBOOK_YEAR"));
                    		System.out.println("REASON_FOR_TRANSFER   ####"+rs.getString("REASON_FOR_TRANSFER"));
                    		System.out.println("ACCEPT_ACCOUNTING_UNIT_ID   ####"+rs.getInt("ACCEPT_ACCOUNTING_UNIT_ID"));*/
                    		
                    		if(!rs2.next())
                    		{
                    		
                    		
                    		xml=xml+"<acceptunit>"+rs.getInt("ACCEPT_ACCOUNTING_UNIT_ID")+"</acceptunit>";
                    		
                    		xml=xml+"<reason>"+rs.getString("REASON_FOR_TRANSFER")+"</reason>";
                    		
                    		xml=xml+"<cashmonth>"+rs.getInt("CASHBOOK_MONTH")+"</cashmonth>";
                    		
                    		xml=xml+"<cashyear>"+rs.getInt("CASHBOOK_YEAR")+"</cashyear>";
                    		
                    		xml=xml+"<tpatype>"+rs.getString("TPA_TYPE")+"</tpatype>";
                    		
                    		xml=xml+"<effectivedate>"+rs.getString("EFFECTIVE_DATE")+"</effectivedate>";
                    		
                    		y++;
                    		
                    		}
                    		
                    		
                    	z++;	
                    		
                    	}
                    	if(z<1)
                    		xml=xml+"<flag>noauthorize</flag>";		
                    	else if(y<1)
                    		xml=xml+"<flag>nodata</flag>";
                    	else 
                    		xml=xml+"<flag>success</flag>";	
                    	
                    	
                    }catch(Exception e){
                    	System.out.println(e);
                    } 
            }
            xml=xml+"</response>";
            System.out.println("xml :: "+xml);
            out.println(xml);
            out.close();
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,IOException
    {
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
		      PreparedStatement ps2=null,ps=null;        
		      ResultSet rs2=null,rs=null;
		      String sql=null;      
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
	                  ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	                  Class.forName(strDriver.trim());
	                  con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	          }
	          catch(Exception e)
	          {
	              	  System.out.println("Exception in opening connection :"+e);
	          }
	          Calendar c;
	          int txtAcc_HeadCode=0,cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0,txtUnitId=0,count=0;
	          String cr_dr=null,reason4Trf=null,particulars=null;
	          Date txtCrea_date=null,txtVoucher_date=null;
	          String update_user=(String)session.getAttribute("UserId");
	          long l=System.currentTimeMillis();
	          Timestamp ts=new Timestamp(l);                      
	          int errcode=0;
	          
	          //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
              
	             try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	             System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
	             
	             try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	             System.out.println("cmbOffice_code "+cmbOffice_code);
	             
	             String[] sd=request.getParameter("Voucher_Date").split("/");
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
	            	 	  sql="SELECT VOUCHER_NO FROM FAS_TPA_MASTER GROUP BY VOUCHER_NO HAVING  "+
	            	 	  	  " VOUCHER_NO =(select max(VOUCHER_NO) from FAS_TPA_MASTER where to_date(CASHBOOK_MONTH||'-'||CASHBOOK_YEAR,'mm-yyyy') between to_date(4||'-'||?,'mm-yyyy') and to_date(3||'-'||?,'mm-yyyy'))";	
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
	             
	             String[] sd1=request.getParameter("Voucher_Date").split("/");
	             c=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
	             java.util.Date d1=c.getTime();
	             txtVoucher_date=new Date(d1.getTime());
	             
	             try{cr_dr=request.getParameter("Org_CR_DR");}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	             System.out.println("Org_CR_DR "+cr_dr);
	             
	             if(cr_dr.equals("CR"))
	            	 cr_dr="TPAOC";
	             else
	            	 cr_dr="TPAOD";
	             
	             try{txtUnitId=Integer.parseInt(request.getParameter("TransferedID"));}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	             System.out.println("txtUnitId "+txtUnitId);
	             	                                     
	             try{reason4Trf=request.getParameter("Reason4Trf");}
	             catch(NumberFormatException e){System.out.println("exception"+e );}
	             System.out.println("txtRemarks "+reason4Trf);
	             
	             try{particulars=request.getParameter("GenParticulars");}
	             catch(Exception e){System.out.println("exception"+e );}
	             System.out.println("txtAmount "+particulars);
	             boolean flag=true;
	             //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	             try
	             {
		             try
		       	 	 {
		                     con.clearWarnings();
		                     con.setAutoCommit(false);
		                     ps=con.prepareStatement("insert into FAS_TPA_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,TPA_TYPE,VOUCHER_DATE,TRF_ACCOUNTING_UNIT_ID,REASON_FOR_TRANSFER,PARTICULARS,STATUS,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");	                     
		                     ps.setInt(1,cmbAcc_UnitCode);
		                     ps.setInt(2,cmbOffice_code);
		                     ps.setInt(3,txtCash_year);
		                     ps.setInt(4,txtCash_Month_hid);
		                     ps.setInt(5,Originated_SL_No);
		                     ps.setString(6,cr_dr);
		                     ps.setDate(7,txtCrea_date);
		                     ps.setInt(8,txtUnitId);                      
		                     ps.setString(9,reason4Trf);               
		                     ps.setString(10,particulars);
		                     ps.setString(11,"L");
		                     ps.setString(12,update_user);
		                     ps.setTimestamp(13,ts);
		                     errcode=ps.executeUpdate();
		       	 	 }catch(Exception e){System.out.println("Err in first table create :: "+e.getMessage());}
	                 if(errcode==0)
	                 {         
		                     System.out.println("redirect");
		                     flag=false;                                   
	                 }
	                 else
	                 {
	                	 	 int acc_head_code=0,cmbSL_type=0,cmbSL_Code=0,SL_NO=0;
	                	 	 double amount=0,txtsub_Amount=0;
	                	 	 String cr_dr_indicator=null,det_particulars=null;
	                	 	 System.out.println("First tableinserted successfully");
	                	 	 try{acc_head_code=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
	                	 	 }catch(Exception e){System.out.println("Exp in acc_head_code ::: ");}
	                	 	 try{cmbSL_type=Integer.parseInt(request.getParameter("cmbSL_type"));
	                	 	 }catch(Exception e){System.out.println("Exp in cmbSL_type ::: ");}
	                	 	 try{cmbSL_Code=Integer.parseInt(request.getParameter("cmbSL_Code"));
	                	 	 }catch(Exception e){System.out.println("Exp in cmbSL_type ::: ");}
	                	 	 try{amount=Double.parseDouble(request.getParameter("Amount"));
	                	 	 }catch(Exception e){System.out.println("Exp in cmbSL_type ::: ");}
	                	 	 try{cr_dr_indicator=request.getParameter("indicrdr");
	                	 	 }catch(Exception e){System.out.println("Exp in cmbSL_type ::: ");}
	                	 	 try{det_particulars=request.getParameter("DetParticular");
	                	 	 }catch(Exception e){System.out.println("Exp in cmbSL_type ::: ");}
	                	 	 
	                	 	 String Grid_H_code[]=request.getParameterValues("H_code");
	                	 	 String Grid_SL_type[]=request.getParameterValues("SL_type");
	                         String Grid_SL_code[]=request.getParameterValues("SL_code"); 
	                         String Grid_CR_DR[]=request.getParameterValues("cr_dr");
	                         String Grid_Amt[]=request.getParameterValues("amount");
	                         String Grid_particular[]=request.getParameterValues("particular");
	                         String chckparameter[]=request.getParameterValues("chckparameter");
	                         System.out.println("2 nd table insert"+chckparameter.length);
	                         try
	                         {	                        	 
	                        	 sql="insert into FAS_TPA_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,AMOUNT,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)" ;                          
                                 ps=con.prepareStatement(sql);
                                 int k=-1;
                                 for(;k<chckparameter.length;k++) 
                                 {                                                                                                        
                                         //  cmbSL_type=0;cmbSL_Code=0;
                                           txtAcc_HeadCode=0;txtsub_Amount=0;                                             
                                           SL_NO++;
                                           ps.setInt(1,cmbAcc_UnitCode);     
                                           ps.setInt(2,cmbOffice_code);    
                                           ps.setInt(3,txtCash_year);
                                           System.out.println("txtCash_Month_hid :: "+txtCash_Month_hid);
                                           ps.setInt(4,txtCash_Month_hid);
                                           ps.setInt(5,Originated_SL_No);       
                                           ps.setInt(6,SL_NO);
                                           if(k<0)
                                           {
                                        	   ps.setInt(7,acc_head_code);
                                        	   ps.setString(8,cr_dr_indicator);
                                        	   ps.setInt(9,cmbSL_type);
                                        	   ps.setInt(10,cmbSL_Code);
                                        	   ps.setDouble(11,amount);
                                        	   ps.setString(12,det_particulars);
                                           }
                                           else
                                           {
                                        	   txtAcc_HeadCode=Integer.parseInt(Grid_H_code[k]);
	                                           ps.setInt(7,txtAcc_HeadCode);
	                                         System.out.println("*********"+txtAcc_HeadCode);
	                                           String rad_sub_CR_DR=Grid_CR_DR[k];                               
	                                           ps.setString(8,rad_sub_CR_DR);   
	                                           
	                                           try{cmbSL_type=Integer.parseInt(Grid_SL_type[k]);}
	                                           catch(NumberFormatException e){System.out.println("exception"+e );}
	                                           ps.setInt(9,cmbSL_type); 
	                                           
	                                           try{cmbSL_Code=Integer.parseInt(Grid_SL_code[k]);}
	                                           catch(NumberFormatException e){System.out.println("exception"+e );}
	                                           ps.setInt(10,cmbSL_Code);
	                                                                          
	                                           try{txtsub_Amount=Double.parseDouble(Grid_Amt[k]);}
	                                           catch(NumberFormatException e){System.out.println("exception"+e );}
	                                           ps.setDouble(11,txtsub_Amount);
	                                           
	                                           ps.setString(12,Grid_particular[k]);      
                                           }
                                           ps.setString(13,update_user);
                                           ps.setTimestamp(14,ts);
                                           int i=ps.executeUpdate(); 
                                           if(i>0)
                                           {
                                               count++;
                                               System.out.println("------------------------"+SL_NO+" inserted successfully");
                                           }
                                          // System.out.println("SQL::::insert into FAS_ADJUSTMENT_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,HO_REF_NUMBER,HO_REF_DATE,AMOUNT,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE) values("+cmbAcc_UnitCode+","+cmbOffice_code+","+txtCash_year+","+txtCash_Month_hid+","+txtAdvice_No+","+SL_NO+","+txtAcc_HeadCode+",'"+rad_sub_CR_DR+"',"+cmbSL_type+","+cmbSL_Code+","+ref_num+",?,?,?,?)");
                                           cmbSL_Code=0;
                                   }   
                                   if(count==chckparameter.length+1)
                            	   		   flag=true;
                                   else
                                	   	   flag=false;
	                         }
	                         catch(Exception e)
	                         {
	                        	 e.printStackTrace();
	                        	   System.out.println("Exp in 2 nd table ::: "+e.getMessage());	        
	                        	   flag=false;       
	                         }
	                 }
	             }
	             catch(Exception e)
	             {
            	 	 System.out.println("Err in insertion :: "+e.getMessage());
            	 	 flag=false;  
	             }
	             try
	             {	
	            	 if(flag==true)
		             {
		            	 sendMessage(response,"The TPA Sl.No '"+Originated_SL_No+"' has been Created Successfully ","ok");
	                     con.commit();
		             }
		             else
	                 {
			             System.out.println("b4 Rollback");
			             sendMessage(response,"The TPA Creation Failed ","ok");
			             con.rollback(); 
	                 }
	             }
	             catch(Exception e)
	             {
	            	 System.out.println("b4 Rollback");
		             sendMessage(response,"The TPA Creation Failed ","ok");
		             //con.rollback();
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
