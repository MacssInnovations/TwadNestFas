package Servlets.FAS.FAS1.Imprest.servlets;

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

public class Imprest_Account_Creation extends HttpServlet
{
    private String CONTENT_TYPE = "text/xml; charset=windows-1252";
  
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
	      String sql="";
	      int txtEmpID=0;
	      
	      
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
		            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection		            Class.forName(strDriver.trim());
		            con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
		    }
		    catch(Exception e)
		    {
		            System.out.println("Exception in opening connection :"+e);
		    }
	        
		    
		    
		    int OfficeId=0,LoginOffice=0,empid=0,cmbAcc_UnitCode=0,cashbook_yr=0,cashbook_mn=0,count=0,cheque_no_one=0;
		    int txtCB_Year=0,cmbOffice_code=0,autoCount=0;
		    String xml=null,cmd="",from_dt="",to_dt="",txtMode_of_creat="";
		    Date txtCrea_date=null;
		    Calendar c;
		    String[] sd=null;  
	      
	      
		    /** Get Employee ID */
		    try
		    {	    	  	  
			        cmd=request.getParameter("command");
			    //    System.out.println("cmd:::"+cmd);
		    }
		    catch(Exception e)
		    {
	        	    System.out.println(e);
	     	}	    
		    
		    try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            
            try{OfficeId=Integer.parseInt(request.getParameter("txtOfficeId"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            
            try
            {
		            sd=request.getParameter("txtCrea_date").split("/");
		            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
		            java.util.Date d=c.getTime();
		            txtCrea_date=new Date(d.getTime());
            }
            catch(Exception e)
            {
            		System.out.println("Exception in date:"+e.getMessage());
            }
            
            try{cashbook_yr=Integer.parseInt(sd[2]);}
            catch(Exception e){System.out.println("exception"+e );}
            
            try{cashbook_mn=Integer.parseInt(sd[1]);}
            catch(Exception e){System.out.println("exception"+e );}
            
            try{empid=Integer.parseInt(request.getParameter("txtEmpID"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            
            try{txtMode_of_creat=request.getParameter("txtMode_of_creat");}
            catch(Exception e){System.out.println("Err in get txtMode_of_creat :: "+e.getMessage());}
            
		    if(cmd.equalsIgnoreCase("loadOfficeDetails"))
		    {     
		         
	             	xml="<response><command>loadEmployeeDetails</command>";
		    		LoginOffice = Integer.parseInt(request.getParameter("txtLoginOfficeId"));
	    	  	 //   System.out.println("txtOfficeId:::"+OfficeId+"  LoginOffice:::"+LoginOffice);
	    	  	    
	    	  	    
		    		       		 sql = "  select "+
							              		 " a.office_id,	"+
							              		 " b.office_short_name	"+ 									              
							           "  from	 "+
							           			 " com_office_control a,"+
							           			 " com_mst_offices b	"+								            					      
							           "  where	 "+
							              		 " a.office_id=? and    ";
				    if(OfficeId!=LoginOffice)
				    	   		 sql+= " a.controlling_office_id='"+LoginOffice+"' and ";				    				    
				    sql+= " a.office_id=b.office_id ";  // For Both Conditions				    				   
				    System.out.println(" SQL :: "+sql);
			        try
			        {
					             ps2=con.prepareStatement(sql);
					             ps2.setInt(1,OfficeId);
					             rs2=ps2.executeQuery();
					             
					             if (rs2.next()) 
					             {
						                 xml+= "<office_id>"+ rs2.getInt("office_id") +"</office_id>";	 
						                 xml+= "<office_short_name>"+ rs2.getString("office_short_name") +"</office_short_name>";  				                		                 
						                 count++;
					             }					              
					             if(count==0)
					                  	 xml+="<flag>NoData</flag>";					           
					             else               
					                  	 xml+="<flag>success</flag>";
					                         
			        }
			        catch(Exception e) 
			        {
						         System.out.println("Exception in assigning..."+e);
						         xml+="<flag>"+e.getMessage()+"</flag>";
			        }
			       
		    }
		    
		    else if(cmd.equalsIgnoreCase("check_cheque_no"))
		    {
//		    	try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
//	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            
		    	try{txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            
	            try{cheque_no_one=Integer.parseInt(request.getParameter("cheque_no"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}		
		    		xml="<response><command>check_cheque_no</command>";
		    		try {
                         
		    					 ps2=con.prepareStatement("SELECT trn.CHEQUE_DD_NO\n" + 
                                                        " FROM FAS_PAYMENT_TRANSACTION trn,FAS_PAYMENT_MASTER mas\n" + 
		    					 " WHERE trn.ACCOUNTING_UNIT_ID=mas.ACCOUNTING_UNIT_ID\n" + 
		    					 " and trn.ACCOUNTING_FOR_OFFICE_ID=mas.ACCOUNTING_FOR_OFFICE_ID\n" + 
		    					 " and trn.CASHBOOK_YEAR=mas.CASHBOOK_YEAR\n" + 
		    					 " and trn.CASHBOOK_MONTH=mas.CASHBOOK_MONTH\n" + 
		    					 " and trn.VOUCHER_NO=mas.VOUCHER_NO\n" + 
		    					 " and trn.ACCOUNTING_UNIT_ID                      =?\n" + 
		    				//	 " AND trn.ACCOUNTING_FOR_OFFICE_ID=?\n" + 
		    					 " AND trn.CASHBOOK_YEAR                             =?\n" + 
		    					 " AND trn.CHEQUE_DD_NO                              =?::VARCHAR \n" + 
		    					 " AND mas.PAYMENT_STATUS='L' ");
		    					 ps2.setInt(1,cmbAcc_UnitCode);
		    				//	 ps2.setInt(2,cmbOffice_code);
		    					 ps2.setInt(2,txtCB_Year);
		    					 ps2.setInt(3,cheque_no_one);
		    					 rs2=ps2.executeQuery();
		    					
		    					 while(rs2.next())
		                         {
		                              	 autoCount++;
		                              	 xml=xml+"<flag>failure</flag>";
		                         }
		                         if(autoCount==0)
		                         {
		                        	 xml=xml+"<flag>success</flag>";
		                         }
		                         else
		                              	 xml=xml+"<flag>failure</flag>";
                         
		                         ps2.close();
		                         rs2.close();
                    }
                    catch(Exception e)
                    {
                    			 System.out.println("catch..HERE.in cheque no."+e);
                    			 xml=xml+"<flag>failure</flag>";
                    }
		    }
		    
		    else if(cmd.equalsIgnoreCase("Load_SL_Code"))
		    {
		    	System.out.println("imprest");	
		    		xml="<response><command>Load_SL_Code</command>";
		    		try {
                         
		    					// ps2=con.prepareStatement("select e.EMPLOYEE_ID,e.EMPLOYEE_NAME||'.'||e.EMPLOYEE_INITIAL||'-'|| d.DESIGNATION as ENAME,c.EMPLOYEE_STATUS_ID from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d where c.DESIGNATION_ID=d.DESIGNATION_ID  and e.EMPLOYEE_ID=c.EMPLOYEE_ID and c.EMPLOYEE_ID=? and c.OFFICE_ID=? and c.EMPLOYEE_STATUS_ID='WKG' order by e.EMPLOYEE_NAME ");
		    					 ps2=con.prepareStatement("SELECT e.EMPLOYEE_ID,\n" + 
		    					 "  e.EMPLOYEE_NAME\n" + 
		    					 "  ||'.'\n" + 
		    					 "  ||e.EMPLOYEE_INITIAL\n" + 
		    					 "  ||'-'\n" + 
		    					 "  || d.DESIGNATION AS ENAME,\n" + 
		    					 "  c.EMPLOYEE_STATUS_ID\n" + 
		    					 "FROM HRM_MST_EMPLOYEES e,\n" + 
		    					 "  HRM_EMP_CURRENT_POSTING c,\n" + 
		    					 "  HRM_MST_DESIGNATIONS d,\n" + 
		    					 "  HRM_EMP_CONTROLLING_OFFICE f\n" + 
		    					 "WHERE c.DESIGNATION_ID  =d.DESIGNATION_ID\n" + 
		    					 "AND e.EMPLOYEE_ID       =c.EMPLOYEE_ID\n" + 
		    					 "and  c.EMPLOYEE_ID      = f.EMPLOYEE_ID\n" + 
		    					 "AND c.EMPLOYEE_ID       =?\n" + 
		    					 "and f.controlling_office_id=?\n" + 
		    					 "AND c.EMPLOYEE_STATUS_ID='WKG'\n" + 
		    					 "ORDER BY e.EMPLOYEE_NAME");
		    					 ps2.setInt(1,empid);
		    					 ps2.setInt(2,OfficeId);
		    					 rs2=ps2.executeQuery();
		    					 
		                         if(rs2.next())
		                         {
		                              	 xml=xml+"<cid>"+rs2.getInt("EMPLOYEE_ID")+"</cid><cname>"+rs2.getString("ENAME")+"</cname>";
		                              	 xml=xml+"<state>"+rs2.getString("EMPLOYEE_STATUS_ID")+"</state>";
		                              	 xml=xml+"<flag>success</flag>";
		                         }
		                         else
		                              	 xml=xml+"<flag>failure</flag>";
                         
		                         ps2.close();
		                         rs2.close();
                    }
                    catch(Exception e)
                    {
                    			 System.out.println("catch..HERE.in load emp cod in else part."+e);
                    			 xml=xml+"<flag>failure</flag>";
                    }
		    }
		    else if(cmd.equalsIgnoreCase("loadRecoupDetails"))
		    {
		    	System.out.println("*********loadRecoupDetails******");
		    		String Option=request.getParameter("Option");
		    		xml="<response><command>loadRecoupDetails</command>";
		    		/*****Financial Year Calculation******/
		    	
		    		to_dt=cashbook_mn+"-"+cashbook_yr;
		    		if(cashbook_mn > 3)
		    		{
		    					
		    					 from_dt="3-"+cashbook_yr;
		    		}
		    		else
		    		{
		    			
		    					 from_dt="3-"+(cashbook_yr-1);
		    		}
		    		
		    		/*****End Financial Year Calculation******/
		    	//	System.out.println("From dt:"+from_dt);
		    	//	System.out.println("To dt:"+from_dt);
		    		
			    //	System.out.println("Inside Load Recoup Details");
		    		try 
		    		{		    					
		    			   		 sql=" select "+
	    					 			  " pay_mst.VOUCHER_NO, "+
	    					 			  " to_char(pay_mst.PAYMENT_DATE,'DD/MM/YYYY') as PAYMENT_DATE, "+
	    					 			  " pay_trn.SL_NO, "+
	    					 			  " pay_trn.SUB_LEDGER_CODE, "+
	    					 			  " pay_trn.WD_PURPOSE_ID, "+
	    					 			  " trim(to_char(pay_trn.AMOUNT,'99999999999999.99')) as AMOUNT, "+	    					 			 
	    					 			  " mst.WD_PURPOSE_DESC	"+
		    					 " from   "+
		    					 		  " FAS_PAYMENT_MASTER pay_mst, "+
		    					 		  " FAS_PAYMENT_TRANSACTION pay_trn, "+
		    					 		  " FAS_WD_PURPOSE_MST mst "+
								 " where  "+
								      	  " pay_mst.ACCOUNTING_UNIT_ID=pay_trn.ACCOUNTING_UNIT_ID and "+
								          " pay_mst.ACCOUNTING_FOR_OFFICE_ID=pay_trn.ACCOUNTING_FOR_OFFICE_ID and "+
								          "	pay_mst.CASHBOOK_YEAR=pay_trn.CASHBOOK_YEAR and "+
								          " pay_mst.CASHBOOK_MONTH=pay_trn.CASHBOOK_MONTH and "+
								          " pay_mst.VOUCHER_NO=pay_trn.VOUCHER_NO and "+
								          " pay_trn.WD_PURPOSE_ID=mst.WD_PURPOSE_ID and "+
								          " pay_mst.ACCOUNTING_UNIT_ID=? and "+
								          " pay_mst.ACCOUNTING_FOR_OFFICE_ID=? and "+
								          " to_date(pay_mst.cashbook_month ||'-'|| pay_mst.cashbook_year, 'mm-yyyy') BETWEEN to_date(?,'mm-yyyy')  AND to_date(?,'mm-yyyy') and "+
								          " pay_trn.SUB_LEDGER_CODE=? and "+
								          "(pay_trn.AMOUNT_FULLY_SPENT='N' or pay_trn.AMOUNT_FULLY_SPENT is null)and "+
								          " (pay_mst.MODE_OF_CREATION='"+txtMode_of_creat+"' or pay_mst.MODE_OF_CREATION='IT')  and " ;
								          if(Option.equals("Create"))
								 sql +=   "	(pay_trn.RECOUPED_STATUS='N' or pay_trn.RECOUPED_STATUS is null) and " ;
								 sql +=   " pay_mst.PAYMENT_STATUS!='C' "+
								          
								 " order by "+								      	  
								      	  " pay_mst.PAYMENT_DATE, "+
								      	  " pay_mst.VOUCHER_NO, "+
								      	  " pay_trn.SL_NO "; 
		    			   		 
		    			   		 System.out.println("SQL ::: "+sql);
		    					 ps2=con.prepareStatement(sql);
		    					 ps2.setInt(1,cmbAcc_UnitCode);System.out.println(cmbAcc_UnitCode);
		    					 ps2.setInt(2,OfficeId);System.out.println(OfficeId);
		    					 ps2.setString(3,from_dt);System.out.println(from_dt);
		    					 ps2.setString(4,to_dt);System.out.println(to_dt);
		    					 ps2.setInt(5,empid);System.out.println(empid);
		    				//	 ps2.setString(6,txtMode_of_creat);System.out.println(txtMode_of_creat);
		    					 rs2=ps2.executeQuery();System.out.println("rs2"+rs2);
		    					 
		                         while(rs2.next())
		                         {
		                        	// System.out.println("while loop");
		                              	  xml=xml+"<voucher_no>"+rs2.getInt("VOUCHER_NO")+"</voucher_no>";
		                              	  xml=xml+"<slno>"+rs2.getInt("SL_NO")+"</slno>";
		                              	  xml=xml+"<payment_dt>"+rs2.getString("PAYMENT_DATE")+"</payment_dt>";
		                              	  xml=xml+"<purpose>"+rs2.getString("WD_PURPOSE_DESC")+"</purpose>";
		                              	  xml=xml+"<amount>"+rs2.getDouble("AMOUNT")+"</amount>";		                              	  
		                              	  count++;
		                         }
		                         if(count>0)
		                        	 	  xml=xml+"<flag>success</flag>";
		                         else
		                              	  xml=xml+"<flag>failure</flag>";
	                     
		                         ps2.close();
		                         rs2.close();
	                }
	                catch(Exception e)
	                {
	                			 System.out.println("catch..HERE.in load emp cod in else part."+e);
	                			 xml=xml+"<flag>failure</flag>";
	                }
		    	
		    }
		    else if(cmd.equalsIgnoreCase("loaddrawlPurpose"))
		    {
		    		xml="<response><command>loaddrawlPurpose</command>";
		    		try
		    		{
		    					sql="select wd_purpose_id,wd_purpose_desc from fas_wd_purpose_mst where type=? order by wd_purpose_desc";
						    	System.out.println("sql:::::::"+sql);
		    					ps2=con.prepareStatement(sql);
								 ps2.setString(1,txtMode_of_creat);		
							//	 System.out.println("Journal_Type ::: "+txtMode_of_creat);
								 rs2=ps2.executeQuery();					 
				                 while(rs2.next())
				                 {
				                	// System.out.println("insidev while");
				                     	  xml=xml+"<wd_purpose_id>"+rs2.getString("wd_purpose_id")+"</wd_purpose_id>";
				                     	  xml=xml+"<wd_purpose_desc>"+rs2.getString("wd_purpose_desc")+"</wd_purpose_desc>";	                     	 
				                     	  count++;
				                 }
				                 if(count>0)
				                		  xml=xml+"<flag>success</flag>";
				                 else
				                     	  xml=xml+"<flag>failure</flag>";
		    		}
		    		catch(Exception e)
		    		{
		    				 System.out.println("Err in load cmbJournal_type Combo");
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
	            xml="<response><command>Add</command>";
	            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	            Calendar c;
	            int txtAcc_HeadCode=0,cmbAcc_UnitCode=0,cmbOffice_code=0,txtCash_Month_hid=0,txtCash_year=0;
	            int bank_id=0,branch_id=0,count=0,acc_no=0,txtOfficeId=0,sub_type_code=0;
	            double txtTotalAmt=0;
	            Date txtCrea_date=null;
	            String PaidTo="",remarks="",txtMode_of_creat="";
	      
	                                   
	            String update_user=(String)session.getAttribute("UserId");
	            long l=System.currentTimeMillis();
	            Timestamp ts=new Timestamp(l);	                
	            int errcode=0,sno=0;
	            
	            int h_code=0,stype=0,stype_code=0,cheque_num=0,p_id=0,recoup_vou=0,recoup_slno=0;
	            double amt=0,recoup_amt=0;
	           	String bill_no="";
	            Date bill_date=null,dd_date=null,recoup_dt=null;
	            
	            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
	                               
	            try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            
	            try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
                    
	            String[] sd=request.getParameter("txtCrea_date").split("/");
	            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	            java.util.Date d=c.getTime();
	            txtCrea_date=new Date(d.getTime());
	            
	            try{txtCash_year=Integer.parseInt(sd[2]);}
	            catch(Exception e){System.out.println("exception"+e );}
	            
	            try{txtCash_Month_hid=Integer.parseInt(sd[1]);}
	            catch(Exception e){System.out.println("exception"+e );}
	            int txtVoucher_No=0;
	            try
	            {
	            	rs=st.executeQuery("SELECT VOUCHER_NO FROM FAS_PAYMENT_MASTER GROUP BY VOUCHER_NO HAVING VOUCHER_NO =(select max(VOUCHER_NO) from FAS_PAYMENT_MASTER where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCash_year+" and CASHBOOK_MONTH="+txtCash_Month_hid+")");
	                if(rs.next()) 
	                {
	                	txtVoucher_No = rs.getInt("VOUCHER_NO");                		                   
	                }
	                txtVoucher_No=txtVoucher_No+1;
	            }
	            catch(Exception e){System.out.println("exception"+e );}
	            
	            
	            try
	            {
	            	rs.close();
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
	            
	            try{txtMode_of_creat=request.getParameter("cmbPayment_type");}
	            catch(Exception e)
	            {
	            	System.out.println("Exception in txtMode_of_creat ::: "+e.getMessage());
	            }
	            
	           
	            try{remarks=request.getParameter("txtRemarks");}
	            catch(NumberFormatException e){System.out.println("exception"+e );}
	            
	            try{txtTotalAmt=Double.parseDouble(request.getParameter("txtTotalAmt"));}
	            catch(Exception e){System.out.println("exception"+e );}
	            
	           
	            int row_num=Integer.parseInt(request.getParameter("num_rows"));
	            //~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~        
                        
	            try 
	            {   
	                     con.clearWarnings();
	                     con.setAutoCommit(false);
	                     ps=con.prepareStatement("insert into FAS_PAYMENT_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,PAYMENT_DATE,CASHBOOK_YEAR,CASHBOOK_MONTH,PAYMENT_TYPE,VOUCHER_NO,ACCOUNT_HEAD_CODE,BANK_ID,BRANCH_ID,ACCOUNT_NO,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,CR_DR_INDICATOR,PAID_TO,REMARKS,TOTAL_AMOUNT,MODE_OF_CREATION,CREATED_BY_MODULE,UPDATED_BY_USER_ID,UPDATED_DATE,PAYMENT_STATUS,TOTAL_TRN_RECORDS) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	                     //System.out.println("insert into FAS_ADJUSTMENT_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,ADJUSTMENT_DATE,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,BEHALF_OF_OFFICE_ID,PARTICULARS,TOTAL_AMOUNT,UPDATED_BY_USER_ID,UPDATED_DATE,VOUCHER_STATUS) values ("+cmbAcc_UnitCode+","+cmbOffice_code+",'"+txtCrea_date+"',"+txtCash_year+","+txtCash_Month_hid+","+txtAdvice_No+","+office_id+",'"+particulars+"','"+txtTotalAmt+"','"+update_user+"','"+ts+"','L')");
	                     ps.setInt(1,cmbAcc_UnitCode);
	                     
	                     ps.setInt(2,cmbOffice_code);
	                     
	                     ps.setDate(3,txtCrea_date);
	                     
	                     ps.setInt(4,txtCash_year);
	                     
	                     ps.setInt(5,txtCash_Month_hid);
	                     ps.setString(6,"B");
	                     
	                     ps.setInt(7,txtVoucher_No);
	                     ps.setInt(8,txtAcc_HeadCode);
	                     
	                     ps.setInt(9,bank_id);
	                     ps.setInt(10,branch_id);
	                     ps.setInt(11,acc_no);
	                    
	                     ps.setInt(12,sub_type_code);
	                     
	                     ps.setInt(13,txtOfficeId);
	                     ps.setString(14,"CR");
	                     ps.setString(15,PaidTo);
	                     ps.setString(16,remarks);
	                     ps.setDouble(17,txtTotalAmt);
	                     System.out.println("journal_type :::>>> "+txtMode_of_creat);
	                     ps.setString(18,txtMode_of_creat);
	                     ps.setString(19,"BPF");
	                     
	                     ps.setString(20,update_user);
	                     
	                     ps.setTimestamp(21,ts);
	                     
	                     ps.setString(22,"L");
	                     ps.setInt(23,row_num);
	                    
	                     errcode=ps.executeUpdate();
	                     if(errcode==0)
	                     {         
		                       System.out.println("redirect");
		                       sendMessage(response,"The Imprest Account Creation Failed ","ok");		                       
	                     }
	                     else
	                     {
		                    	 System.out.println("created successfully");
		                    	 sno=0;
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
		                         System.out.println(" Length :"+head_code.length);
		                         ps=con.prepareStatement("insert into FAS_PAYMENT_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH," +
                                         "VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,BILL_NO,BILL_DATE,BANK_ID,BRANCH_ID,ACCOUNT_NO,CHEQUE_OR_DD,CHEQUE_DD_NO,CHEQUE_DD_DATE,PAID_TO,AMOUNT,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE,WD_PURPOSE_ID,TYPE_OF_PAYMENT,RECOUPED_STATUS) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		                         for(int i=0;i<head_code.length;i++)
		                         {
					                        	
		                        	 			 h_code=0;stype=0;stype_code=0;cheque_num=0;p_id=0;
					                        	 amt=0;
					                        	 sno=sno+1;

					                        	 bill_no="";
					                        	 bill_date=null;dd_date=null;
					                        	 
					                        	 System.out.println("head_code:::::"+i+":::"+head_code[i]);
					                        	 System.out.println("s_type:::::"+i+":::"+s_type[i]);
					                        	 ps.setInt(1,cmbAcc_UnitCode);					    	                     
					    	                     ps.setInt(2,cmbOffice_code);					    	                     
					    	                     ps.setInt(3,txtCash_year);					    	                     
					    	                     ps.setInt(4,txtCash_Month_hid);
					    	                     ps.setInt(5,txtVoucher_No);
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
					                             ps.setString(26,"N");
					                             int k=ps.executeUpdate(); 
					                             
					                             if(k>0)
					                             {
					                            	 	 System.out.println(" Payment Type :: "+p_type[i]);
							                             if(p_type[i].equals("R"))
							                             {
							                            	 	 
							                            	 	 System.out.println(" inside Recoup create ");
							                            	 	 //seq=seq+1;
							                            	 	 
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
						                            	 		 ps1.setInt(6,txtVoucher_No);
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
							                             	 		 	 ps2=con.prepareStatement("update FAS_PAYMENT_TRANSACTION set RECOUPED_STATUS=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and VOUCHER_NO=? and SUB_LEDGER_CODE=?");
							                             	 		 	 ps2.setString(1,"Y");
							                             	 		 	 ps2.setInt(2,cmbAcc_UnitCode);
								                            	 		 ps2.setInt(3,cmbOffice_code);
								                            	 		// ps2.setDate(4,recoup_dt);
								                            	 		 ps2.setInt(4,recoup_vou);
								                            	 		ps2.setInt(5,stype_code);System.out.println("stype_code"+stype_code);
																				
								                            	 		
								                            	 		 int cnt=ps2.executeUpdate();
								                            	 		 if(cnt>0)
								                            	 			 count++;
							                             	 	 }
							                            	 	 
							                             }
							                             else
						                            	 	 	  count++;
					                             }
					                             
					    	                     
		                         }
		                         if(count==row_num)
		                         {
			                         System.out.println("b4 commit");
			                         con.commit();
			                         if(txtMode_of_creat.equals("I"))
			                        	 sendMessage(response,"The Imprest Account Voucher Number '"+txtVoucher_No+"' has been Created Successfully ","ok");
			                         else
			                        	 sendMessage(response,"The Temporary Advance Account Voucher Number '"+txtVoucher_No+"' has been Created Successfully ","ok");
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
	                    	 sendMessage(response,"The Imprest Account Creation Failed ","ok");
	                     else
	                    	 sendMessage(response,"The Temporary Advance Account Creation Failed ","ok");
	                     System.out.println("Exception occur due to "+e);
	                     
	                 }
	                 finally
	                 {
	                     System.out.println("done");
	                     try
	                     {
	                    	 	 con.setAutoCommit(true); 
	                    	 	 con.close();
	                     }catch(SQLException sqle){}
	                     
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
