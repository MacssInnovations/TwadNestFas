package Servlets.FAS.FAS1.BRS.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class BRS_NonTwad extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
   
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
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
	        PreparedStatement ps2=null,po=null;
	        ResultSet rs2=null,ro=null;       
	        
	        response.setContentType(CONTENT_TYPE);
	        response.setHeader("Cache-Control","no-cache");
	        PrintWriter out = response.getWriter();
	        
	        String command="",sql="";
	        
	        
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
                    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
                    Class.forName(strDriver.trim());
                    con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	        }
	        catch(Exception e)
	        {
	        		System.out.println("Exception in opening connection :"+e);
             
	        }
	        int cmbAcc_UnitCode =0,cmbOffice_code=0,txtCB_Year_from=0,txtCB_Month_from=0,txtCB_Year_to=0,txtCB_Month_to=0,count=0;
	        long BankAccNo=0;
		    command=request.getParameter("command");
	        try
		    {cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		    }catch(Exception e){System.out.println("Error Not Getting Accounitng Unit ID --> "+e);}
		      
		    /* Get Accounting for Office ID */
		    try
		    {cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
		    }catch(Exception e){System.out.println("Error Not Getting Accounting for Office Id --> "+e);
		    }
		    
		    /* Get Cashbook Year */
		    try
		    {txtCB_Year_from = Integer.parseInt(request.getParameter("txtCB_Year_From"));
		    }catch(Exception e){System.out.println("Error Not Getting Cashbook Year -->"+e);
		    }
		    
		    /* Get Cashbook Month */
		    try
		    {txtCB_Month_from = Integer.parseInt(request.getParameter("txtCB_Month_From"));
		    }catch(Exception e){System.out.println("Error Not Getting Cashbook Month -->"+e);
		    }
		    
		   
		    String option=request.getParameter("option");
		
		    try
		    {
		    	BankAccNo=Long.parseLong(request.getParameter("BankAccNo"));
			    }catch(Exception e){System.out.println("Err in cmbBankAccNo "+e.getMessage());
		    }
			    try
			    {
			    	BankAccNo=Long.parseLong(request.getParameter("cmbBankAccNo"));
				    }catch(Exception e){System.out.println("Err in cmbBankAccNo "+e.getMessage());
			    }
		    
		    String xml="<response>";
		    if(command.equalsIgnoreCase("LoadListOb"))
		    {
		    	 try
				    {txtCB_Year_to = Integer.parseInt(request.getParameter("cashbook_yr_to"));
				    }catch(Exception e){System.out.println("Error Not Getting Cashbook Year -->"+e);
				    }
				    
				   
				    try
				    {txtCB_Month_to = Integer.parseInt(request.getParameter("cashbook_mn_to"));
				    }catch(Exception e){System.out.println("Error Not Getting Cashbook Month -->"+e);
				    }
		    	
		    		xml=xml+"<command>"+command+"</command>";
				    try
				    {				    		
					        sql="select * from \n " + 
					        "( SELECT trim(a.ACCOUNT_NO)AS ACCOUNT_NO,b.TWAD_OR_NON_TWAD, "+
							"    a.BANK_ID, "+
							"   a.BRANCH_ID,b.cashbook_year,b.cashbook_month, "+
							"   TO_CHAR(b.PASSBOOK_DATE,'dd/mm/yyyy') AS PASS_BOOK_DATE, "+
							"  b.CHEQUE_DD_NO,b.SL_NO, "+
							" b.DETAILS,b.FOLLOW_UP_ACTION_REQUIRED, "+
							" trim(TO_CHAR(b.CR_AMOUNT,'99999999999999.99')) AS CR_AMOUNT, "+
					        " trim(TO_CHAR(b.DR_AMOUNT,'99999999999999.99')) AS DR_AMOUNT,b.particulars, "+
					        " (select TRANS_DESC from FAS_BRS_TRANSACTION_TYPE where TRANS_TYPE='NT' " +
					       // " and TRANS_CODE=b.particulars)as part_desc "+
					        " and TRANS_CODE=b.TRANSACTION_TYPE)as part_desc "+
					        
					        " FROM FAS_BRS_OB_MASTER_NT a,FAS_BRS_OB_TRANSACTION_NT b "+
					        " WHERE a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID "+
					        " and a.ACCOUNTING_FOR_OFFICE_ID=b.ACCOUNTING_FOR_OFFICE_ID "+
					        " and a.CASHBOOK_YEAR=b.CASHBOOK_YEAR "+
					        " and a.CASHBOOK_MONTH=b.CASHBOOK_MONTH "+
					        " and a.ACCOUNT_NO=b.ACCOUNT_NO "+
					        " and a.ACCOUNTING_UNIT_ID    = "+cmbAcc_UnitCode+
					        " AND a.ACCOUNTING_FOR_OFFICE_ID= "+cmbOffice_code+"  and b.TWAD_OR_NON_TWAD='NT' "+
							 " and To_Date((b.Cashbook_Month "+
					        "  ||'-' "+
					        " || b.Cashbook_Year),'mm-yyyy') BETWEEN To_Date("+txtCB_Month_from+" "+
					        " ||'-' "+
					        " ||"+txtCB_Year_from+",'mm-yyyy') "+
					        " AND to_date("+txtCB_Month_to+" "+
					        "   ||'-' "+
					        "   ||"+txtCB_Year_to+",'mm-yyyy') )x " + 
					        "left outer join															\n" + 
					        "(																				 \n" + 
					        "    select bank_id as y_bank_id ,trim(bank_name) as y_bank_name from fas_bank_list		\n" + 
					        ")Y																		\n" + 
					        "on X.bank_id  = Y.y_bank_id													\n" + 
					        "left outer join 															\n" + 
					        "(																			\n" + 
					        "    select  BANK_ID as z_bank_id, BRANCH_ID as z_BRANCH_ID ,trim(BRANCH_NAME) as z_BRANCH_NAME from FAS_MST_BANK_BRANCHES	                   \n" + 
					        ")Z " + 
					        "on  																		\n" + 
					        "X.bank_id  = Z.z_bank_id  and											 \n" + 
					        "X.BRANCH_ID = Z.z_branch_id "+
					        "Left Outer Join "+
							" (Select Ob_Status,Account_No as acno From FAS_BRS_OB_STATUS_NT Where Accounting_Unit_Id    ="+cmbAcc_UnitCode+")Aaa "+
							" on aaa.acno=x.ACCOUNT_NO";
					        
					        if(option.equals("List"))
					        {
					        	sql=sql+" where x.ACCOUNT_NO= "+BankAccNo;
                                System.out.println("Query passed ::::::::::"+sql);
					        }
				    		ps2=con.prepareStatement(sql);

				    		rs2=ps2.executeQuery();
				    		while(rs2.next())
				    		{
				    				count++;
				    				xml+="<acc_no>"+rs2.getString("ACCOUNT_NO")+"</acc_no>";
				    				xml+="<bank_id>"+rs2.getString("BANK_ID")+"</bank_id>";
				    				xml+="<branch_id>"+rs2.getString("BRANCH_ID")+"</branch_id>";
				    				xml+="<bank_name>"+rs2.getString("y_bank_name")+"</bank_name>";
				    				xml+="<branch_name>"+rs2.getString("z_BRANCH_NAME")+"</branch_name>";
				    				//xml+="<acc_head_code>"+rs2.getString("ACCOUNT_HEAD_CODE")+"</acc_head_code>";
				    				if(rs2.getString("PASS_BOOK_DATE")==null)
				    					xml+="<passbook_dt>-</passbook_dt>";
				    				else
				    					xml+="<passbook_dt>"+rs2.getString("PASS_BOOK_DATE")+"</passbook_dt>";
				    				xml+="<particulars>"+rs2.getString("PARTICULARS")+"</particulars>";
				    				xml+="<part_desc>"+rs2.getString("part_desc")+"</part_desc>";
				    				
				    				xml+="<cheque_no>"+rs2.getString("CHEQUE_DD_NO")+"</cheque_no>";
				    				xml+="<cheque_details>"+rs2.getString("DETAILS")+"</cheque_details>";
				    				xml+="<cr_amount>"+rs2.getString("CR_AMOUNT")+"</cr_amount>";
				    				xml+="<dr_amount>"+rs2.getString("DR_AMOUNT")+"</dr_amount>";
				    				xml+="<actionreq>"+rs2.getString("FOLLOW_UP_ACTION_REQUIRED")+"</actionreq>";
				    				xml+="<slno>"+rs2.getString("SL_NO")+"</slno>";
				    				xml+="<year>"+rs2.getString("cashbook_year")+"</year>";
				    				xml+="<month>"+rs2.getString("cashbook_month")+"</month>";
				    				xml+="<nt>"+rs2.getString("TWAD_OR_NON_TWAD")+"</nt>";
				    				
				    			
				    		}
				    		if(count>0)
				    				xml+="<flag>success</flag>";
				    		else
				    		{
				    				xml+="<flag>failure</flag>";
				    				try{
				    					po=con.prepareStatement("SELECT Ob_Status,Account_No AS acno FROM FAS_BRS_OB_STATUS_NT WHERE Accounting_Unit_Id ="+cmbAcc_UnitCode+" and Account_No="+BankAccNo);
				    					ro=po.executeQuery();
				    					if(ro.next())
				    					{
				    						xml+="<disabled>true</disabled>";
				    					}
				    					else
				    					{
				    						xml+="<disabled>false</disabled>";
				    					}
				    				}
				    				catch(Exception e)
				    				{
				    					System.out.println("ob status failure:::"+e);
				    				}
				    		}
				    			
				    }
				    catch(Exception e)
				    {
				    		xml+="<flag>failure</flag>";
				    		System.out.println("Err in list Selection :: "+e.getMessage());
				    }
		    }
		    else if(command.equalsIgnoreCase("LoadList"))
		    {
		    	System.out.println("comes here");
		    		xml=xml+"<command>"+command+"</command>";
				    try
				    {				    		
					        sql="select * from \n " + 
					        "( SELECT trim(a.ACCOUNT_NO)AS ACCOUNT_NO, "+
							"    a.BANK_ID, "+
							"   a.BRANCH_ID,b.cashbook_year,b.cashbook_month, "+
							"   TO_CHAR(b.PASSBOOK_DATE,'dd/mm/yyyy') AS PASS_BOOK_DATE, "+
							"  b.CHEQUE_DD_NO,b.SL_NO,b.REASON, "+
							" b.DETAILS,b.FOLLOW_UP_ACTION_REQUIRED, "+
							" trim(TO_CHAR(b.CR_AMOUNT,'99999999999999.99')) AS CR_AMOUNT, "+
					        " trim(TO_CHAR(b.DR_AMOUNT,'99999999999999.99')) AS DR_AMOUNT,b.particulars, "+
					        " (select TRANS_DESC from FAS_BRS_TRANSACTION_TYPE where TRANS_TYPE='NT' " +
					        " and TRANS_CODE=b.particulars)as part_desc "+
					        " FROM FAS_BRS_OB_MASTER_NT a,FAS_BRS_OB_TRANSACTION_NT b "+
					        " WHERE a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID "+
					        " and a.ACCOUNTING_FOR_OFFICE_ID=b.ACCOUNTING_FOR_OFFICE_ID "+
					        " and a.CASHBOOK_YEAR=b.CASHBOOK_YEAR "+
					        " and a.CASHBOOK_MONTH=b.CASHBOOK_MONTH "+
					        " and a.ACCOUNT_NO=b.ACCOUNT_NO "+
					        " and b.ACCOUNTING_UNIT_ID    = "+cmbAcc_UnitCode+
					        " AND b.ACCOUNTING_FOR_OFFICE_ID= "+cmbOffice_code+				        
					        " and a.CASHBOOK_YEAR           ="+txtCB_Year_from+"  AND a.CASHBOOK_MONTH ="+txtCB_Month_from+" )x " + 
					        "left outer join															\n" + 
					        "(																				 \n" + 
					        "    select bank_id as y_bank_id ,trim(bank_name) as y_bank_name from fas_bank_list		\n" + 
					        ")Y																		\n" + 
					        "on X.bank_id  = Y.y_bank_id													\n" + 
					        "left outer join 															\n" + 
					        "(																			\n" + 
					        "    select  BANK_ID as z_bank_id, BRANCH_ID as z_BRANCH_ID ,trim(BRANCH_NAME) as z_BRANCH_NAME from FAS_MST_BANK_BRANCHES	                   \n" + 
					        ")Z " + 
					        "on  																		\n" + 
					        "X.bank_id  = Z.z_bank_id  and											 \n" + 
					        "X.BRANCH_ID = Z.z_branch_id "+
					        "Left Outer Join "+
							" (Select Ob_Status,Account_No as acno From FAS_BRS_OB_STATUS_NT Where Accounting_Unit_Id    ="+cmbAcc_UnitCode+")Aaa "+
							" on aaa.acno=x.ACCOUNT_NO";
					        if(option.equals("Edit"))
					        {
				        		sql=sql+" where x.ACCOUNT_NO= "+BankAccNo;
                                System.out.println("Query passed ::::::::::"+sql);
					        }
					        else if(option.equals("List"))
					        {
					        	sql=sql+" where x.ACCOUNT_NO= "+BankAccNo;
                                System.out.println("Query passed ::::::::::"+sql);
					        }
				    		ps2=con.prepareStatement(sql);

				    		rs2=ps2.executeQuery();
				    		while(rs2.next())
				    		{
				    				count++;
				    				xml+="<acc_no>"+rs2.getString("ACCOUNT_NO")+"</acc_no>";
				    				xml+="<bank_id>"+rs2.getString("BANK_ID")+"</bank_id>";
				    				xml+="<branch_id>"+rs2.getString("BRANCH_ID")+"</branch_id>";
				    				xml+="<bank_name>"+rs2.getString("y_bank_name")+"</bank_name>";
				    				xml+="<branch_name>"+rs2.getString("z_BRANCH_NAME")+"</branch_name>";
				    				//xml+="<acc_head_code>"+rs2.getString("ACCOUNT_HEAD_CODE")+"</acc_head_code>";
				    				if(rs2.getString("PASS_BOOK_DATE")==null)
				    					xml+="<passbook_dt>-</passbook_dt>";
				    				else
				    					xml+="<passbook_dt>"+rs2.getString("PASS_BOOK_DATE")+"</passbook_dt>";
				    				xml+="<particulars>"+rs2.getString("PARTICULARS")+"</particulars>";
				    				xml+="<part_desc>"+rs2.getString("part_desc")+"</part_desc>";
				    				
				    				xml+="<cheque_no>"+rs2.getString("CHEQUE_DD_NO")+"</cheque_no>";
				    				xml+="<cheque_details>"+rs2.getString("DETAILS")+"</cheque_details>";
				    				xml+="<cr_amount>"+rs2.getString("CR_AMOUNT")+"</cr_amount>";
				    				xml+="<dr_amount>"+rs2.getString("DR_AMOUNT")+"</dr_amount>";
				    				xml+="<actionreq>"+rs2.getString("FOLLOW_UP_ACTION_REQUIRED")+"</actionreq>";
				    				xml+="<slno>"+rs2.getString("SL_NO")+"</slno>";				    				
				    				xml+="<REASON>"+rs2.getInt("REASON")+"</REASON>";
				    				
				    				try{
				    					PreparedStatement ps=con.prepareStatement("select TRANS_CODE,TRANS_DESC from FAS_BRS_TRANSACTION_TYPE where TRANS_TYPE='NT' and TRANS_CODE ="+rs2.getInt("REASON"));
				    				ResultSet rs=ps.executeQuery();
				    				while(rs.next()){
				    					xml+="<REASON_desc>"+rs.getString("TRANS_DESC")+"</REASON_desc>";
				    				}
				    				}catch (Exception e) {
				    					e.printStackTrace();
										// TODO: handle exception
									}
				    				xml+="<year>"+rs2.getString("cashbook_year")+"</year>";
				    				xml+="<month>"+rs2.getString("cashbook_month")+"</month>";
				    			
				    		}
				    		if(count>0)
				    				xml+="<flag>success</flag>";
				    		else
				    		{
				    				xml+="<flag>failure</flag>";
				    				try{
				    					po=con.prepareStatement("SELECT Ob_Status,Account_No AS acno FROM FAS_BRS_OB_STATUS_NT WHERE Accounting_Unit_Id ="+cmbAcc_UnitCode+" and Account_No="+BankAccNo);
				    					ro=po.executeQuery();
				    					if(ro.next())
				    					{
				    						xml+="<disabled>true</disabled>";
				    					}
				    					else
				    					{
				    						xml+="<disabled>false</disabled>";
				    					}
				    				}
				    				catch(Exception e)
				    				{
				    					System.out.println("ob status failure:::"+e);
				    				}
				    		}
				    			
				    }
				    catch(Exception e)
				    {
				    		xml+="<flag>failure</flag>";
				    		System.out.println("Err in list Selection :: "+e.getMessage());
				    }
		    }
		    else if(command.equalsIgnoreCase("checkNTstatus"))
		    {
		    	xml=xml+"<command>"+command+"</command>";
		    	long acno=Long.parseLong(request.getParameter("acno"));
		    	try
		    	{
			    	po=con.prepareStatement("SELECT Ob_Status,Account_No AS acno FROM FAS_BRS_OB_STATUS_NT WHERE Accounting_Unit_Id ="+cmbAcc_UnitCode+" and Account_No="+acno);
					ro=po.executeQuery();
					xml+="<flag>success</flag>";
					if(ro.next())
					{
						xml+="<disabled>true</disabled>";
					}
					else
					{
						
						xml+="<disabled>false</disabled>";
					}
		    	}
		    	catch(Exception eee)
		    	{
		    		xml+="<flag>failure</flag>";
		    	System.out.println("exception in NT:::"+eee);	
		    	}
		    }
		    xml+="</response>";
		    System.out.println("XML :: "+xml);
		    out.println(xml);    
    }

   
	public void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
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
	      **/
	       
	      Connection con=null;        
	      PreparedStatement ps2=null,pstat=null,ps_st=null,ps3=null;     
	      response.setContentType(CONTENT_TYPE);
	      response.setHeader("Cache-Control","no-cache");
	      PrintWriter out = response.getWriter();
	      ResultSet rset=null;
	      int ins=0;
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
                    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
                    Class.forName(strDriver.trim());
                    con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	      }
	      catch(Exception e)
	      {
	          		System.out.println("Exception in opening connection :"+e);
	      }
	      
	      /* Get Parameters  */
	      int cmbAcc_UnitCode =0;
	      int cmbOffice_code  =0;
	      int txtCB_Year=0;
	      int txtCB_Month =0;
	      Calendar c;
	      long cmbBankAccNo=0;
	      String txtOprCode=null,txtParticular=null,txtDetails=null,action_required=null,clearance_entry=null;      
	      int txtBankID=0;
	      double txtCr_Amount=0,txtDr_Amount=0;
	      int txtBranchID=0,txtCheque_NO=0;
	      Date txtPassBook_date=null;
	      
	      /* Get Accounting Unit ID */
	      String command=request.getParameter("command");
	      try
	      {
		    	    cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		    	    System.out.println("cmbAcc_UnitCode-->"+cmbAcc_UnitCode);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting Accounitng Unit ID --> "+e);
	      }
	      /* Get Accounting for Office ID */
	      try
	      {
			    	cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			    	System.out.println("cmbOffice_code-->"+cmbOffice_code);
	    	  
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting Accounting for Office Id --> "+e);
	      }
	      /* Get Cashbook Year */
	      try
	      {
	    	  		txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
	    	  		System.out.println("txtCB_Year-->"+txtCB_Year);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting Cashbook Year -->"+e);
	      }
	      /* Get Cashbook Month */
	      try
	      {
	    	  		txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
	    	  		System.out.println("txtCB_Month-->"+txtCB_Month);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting Cashbook Month -->"+e);
	      }
	      /* Get Bank Account Number */
	      try
	      {
	    	  		cmbBankAccNo =Long.parseLong(request.getParameter("cmbBankAccNo"));
	    	  		System.out.println("cmbBankAccNo-->"+cmbBankAccNo);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting Bank Account Number -->"+e);
	      }
	      /* Get Operation Mode */
	      try
	      {
	    	  		txtOprCode = request.getParameter("txtOprCode");
	    	  		System.out.println("txtOprMode-->"+txtOprCode);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting Operation Mode -->"+e);
	      }
	      /* Get Bank ID */
	      try
	      {
	    	  		txtBankID = Integer.parseInt(request.getParameter("txtBankID"));
	    	  		System.out.println("txtBankID-->"+txtBankID);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting Bank ID -->"+e);
	      }
	      /* Get Branch ID */
	      try
	      {
	    	  		txtBranchID = Integer.parseInt(request.getParameter(("txtBranchID")));
	    	  		System.out.println("txtBranchID-->"+txtBranchID);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting Branch ID  -->"+e);
	      }
	      
	      try
	      {
	    	  		String[] sd=request.getParameter("txtPassBook_date").split("/");
	    	  		c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	    	  		java.util.Date d=c.getTime();
	    	  		txtPassBook_date=new Date(d.getTime());
	    	  		System.out.println("txtCrea_date "+txtPassBook_date);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting txtCrea_date -->"+e);
	      }
	      try
	      {
	    	  		txtParticular = request.getParameter(("txtParticular"));
	    	  		System.out.println("txtParticular-->"+txtParticular);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting txtParticular  -->"+e);
	      }
	      try
	      {
	    	  		txtCheque_NO = Integer.parseInt(request.getParameter(("txtCheque_NO")));
	    	  		System.out.println("txtCheque_NO-->"+txtCheque_NO);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting txtCheque_NO-->"+e);
	      }
	      try
	      {
	    	  		txtDetails =request.getParameter(("txtDetails"));
	    	  		System.out.println("txtDetails-->"+txtDetails);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting txtDetails-->"+e);
	      }
	      try
	      {
	    	  		txtCr_Amount =Double.parseDouble(request.getParameter(("txtCr_Amount")));
	    	  		System.out.println("txtCr_Amount-->"+txtCr_Amount);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting txtCr_Amount-->"+e);
	      }
	      try
	      {
	    	  		txtDr_Amount =Double.parseDouble(request.getParameter(("txtDr_Amount")));
	    	  		System.out.println("txtDr_Amount-->"+txtDr_Amount);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting txtDr_Amount-->"+e);
	      }
	      try
	      {
	    	  		action_required=request.getParameter(("action_required"));
	    	  		System.out.println("action_required-->"+action_required);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting action_required-->"+e);
	      }
	      try
	      {
	    	  		clearance_entry=request.getParameter(("clearance_entry"));
	    	  		System.out.println("clearance_entry-->"+clearance_entry);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting clearance_entry-->"+e);
	      }
	      /* User ID */
	      String update_user=(String)session.getAttribute("UserId");
	      System.out.println("update_user-->"+update_user);
	      
	      /* Get Time Stamp */
	      long l=System.currentTimeMillis();
	      Timestamp ts=new Timestamp(l);
	      System.out.println("Timestamp -->"+ts); 
	      String xml="<response>";
	      if(command.equalsIgnoreCase("add"))
	      {
	    	  
	    	  int xnt=0,ynt=0;
	    	  		xml+="<command>"+command+"</command>";
				    try
				    {  
				    	con.setAutoCommit(false);
				    	PreparedStatement ps5 = con.prepareStatement("select ACCOUNT_NO from FAS_BRS_OB_MASTER_NT where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=?");
						ps5.setInt(1, cmbAcc_UnitCode);
						ps5.setInt(2, cmbOffice_code);
						ps5.setInt(3, txtCB_Year);
						ps5.setInt(4, txtCB_Month);
						ps5.setLong(5, cmbBankAccNo);
						ResultSet rss = ps5.executeQuery();
						if (rss.next()) 
						{
									ps2 = con.prepareStatement("update FAS_BRS_OB_MASTER_NT set ENTRY_DATE=?,BANK_ID=?,BRANCH_ID=?,OPERATIONAL_MODE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=?");
									ps2.setTimestamp(1, ts);
									ps2.setInt(2, txtBankID);
									ps2.setInt(3, txtBranchID);
									ps2.setString(4, txtOprCode);
								
									ps2.setString(5, update_user);
									ps2.setTimestamp(6, ts);
									ps2.setInt(7, cmbAcc_UnitCode);
									ps2.setInt(8, cmbOffice_code);
									ps2.setInt(9, txtCB_Year);
									ps2.setInt(10, txtCB_Month);
									ps2.setLong(11, cmbBankAccNo);
									xnt = ps2.executeUpdate();
						}
						else
						{
							    	  ps2=con.prepareStatement("insert into FAS_BRS_OB_MASTER_NT(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ENTRY_DATE,ACCOUNT_NO,BANK_ID,BRANCH_ID,OPERATIONAL_MODE,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?)");
							    	  ps2.setInt(1, cmbAcc_UnitCode);
							    	  ps2.setInt(2, cmbOffice_code);
							    	  ps2.setInt(3, txtCB_Year);
							    	  ps2.setInt(4, txtCB_Month);
							    	  ps2.setTimestamp(5,ts);
							    	  ps2.setLong(6,cmbBankAccNo);
							    	  ps2.setInt(7, txtBankID);
							    	  ps2.setInt(8, txtBranchID);
							    	  ps2.setString(9, txtOprCode);
							    	  ps2.setString(10,update_user);
							    	  ps2.setTimestamp(11,ts);
							    	  xnt=ps2.executeUpdate();	
						}
				    	  if(xnt>0)
				    	  {
				    		  System.out.println("added");
				    		  ps2.close();
				    		  int i = 1, i1 = 0;
								try {
									PreparedStatement ps1 = con.prepareStatement("Select max(SL_NO) from FAS_BRS_OB_TRANSACTION_NT where TWAD_OR_NON_TWAD='NT'");
									ResultSet results2 = ps1.executeQuery();
									if (results2.next()) {
										i1 = results2.getInt(1);
										i = i + i1;

									} else {
										i = i;
									}
								} catch (Exception e) {
									e.printStackTrace();
								}
								  System.out.println("slno:"+i);
				    		  ps3=con.prepareStatement("insert into FAS_BRS_OB_TRANSACTION_NT(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ENTRY_DATE,SL_NO," +
				    		  		"TWAD_OR_NON_TWAD,REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,PASSBOOK_DATE,AMOUNT_IN_PASSBOOK,REASON," +
				    		  		"FOLLOW_UP_ACTION_REQUIRED,UPDATED_BY_USER_ID," +
				    		  		"UPDATED_DATE,ACCOUNT_NO,DOC_NO,DOC_TYPE,DETAILS,TRANSACTION_TYPE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

				    		    ps3.setInt(1, cmbAcc_UnitCode);
				    		    ps3.setInt(2, cmbOffice_code);
				    		    ps3.setInt(3, txtCB_Year);
				    		    ps3.setInt(4, txtCB_Month);
				    		    ps3.setTimestamp(5, ts);
								ps3.setInt(6, i);
								ps3.setString(7, "NT");
								if(txtCr_Amount==0)
								{
									//if credit amount,remitance date to be filled else zero
									//if debit amount,withdrawl date to be filled else zero
									ps3.setDate(8,null);//remitance
									ps3.setDate(9, txtPassBook_date);//withdrawl date
								}
								else if(txtDr_Amount==0)
								{
									ps3.setDate(8,txtPassBook_date);//remitance date
									ps3.setDate(9, null);//withdrawl date
								}
								
								ps3.setInt(10, 0);
								ps3.setInt(11,txtCheque_NO);
								ps3.setDouble(12,txtCr_Amount);
								ps3.setDouble(13,txtDr_Amount);
								
								ps3.setDate(14,txtPassBook_date);
								if(txtCr_Amount==0)
								{
									ps3.setDouble(15,txtDr_Amount);
									
								}
								else if(txtDr_Amount==0)
								{
									ps3.setDouble(15,txtCr_Amount);
								}
								else
								{
									ps3.setDouble(15,0);
								}
								ps3.setString(16,txtParticular);
								ps3.setString(17,action_required);
								
								ps3.setString(18, update_user);
								ps3.setTimestamp(19, ts);	
								ps3.setLong(20, cmbBankAccNo);
								ps3.setInt(21,0);
								ps3.setString(22, "NT");
								ps3.setString(23,txtDetails);
								ps3.setString(24,txtParticular);
								ynt = ps3.executeUpdate();
				    		  System.out.println("trn added:"+ynt);
				    	  		 
				    	  }
				    	  else
				    	  {
				    		  con.rollback();
				    		  System.out.println("failure in master");
				    		  xml+="<flag>failure</flag>";	
				    	  }
				    	  if(ynt>0)
				    	  {
				    		  con.commit();
				    		  xml+="<flag>success</flag>";	   
				    	  }
				    	  else
				    	  {
				    		  con.rollback();
				    		  System.out.println("failure in trn");
				    		  xml+="<flag>failure</flag>";
				    	  }
				    	  
				    		  
				    	  
				    }
				    catch(Exception e)
				    {
				    	 xml+="<flag>failure</flag>";  
				    	try{
				    	 con.rollback();
				    	}
				    	catch(Exception ee)
				    	{
				    		
				    	}
				    	  	
				    }
	      }
	      
	      if(command.equalsIgnoreCase("update"))
	      {
	    	int  serialno=Integer.parseInt(request.getParameter("serialno"));
	    	int  trnyr=Integer.parseInt(request.getParameter("trnyear"));
	    	int  trnmn=Integer.parseInt(request.getParameter("trnmonth"));
	    	  		xml+="<command>"+command+"</command>";
				    try
				    {  
				    	ps2 = con.prepareStatement("update FAS_BRS_OB_TRANSACTION_NT set PASSBOOK_DATE=?,REMITTANCE_DATE=?,WITHDRAWAL_DATE=?,REASON=?,CHEQUE_DD_NO=?,DETAILS=?,AMOUNT_IN_PASSBOOK=?,CR_AMOUNT=?,DR_AMOUNT=?,FOLLOW_UP_ACTION_REQUIRED=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=? and SL_NO=?");
				    	ps2.setDate(1,txtPassBook_date);
				    	if(txtCr_Amount==0)
						{
							//if credit amount,remitance date to be filled else zero
							//if debit amount,withdrawl date to be filled else zero
				    		ps2.setDate(2,null);//remitance
				    		ps2.setDate(3, txtPassBook_date);//withdrawl date
				    		ps2.setDouble(7,txtDr_Amount);//amountinpass
						}
						else if(txtDr_Amount==0)
						{
							ps2.setDate(2,txtPassBook_date);//remitance date
							ps2.setDate(3, null);//withdrawl date
							ps2.setDouble(7,txtCr_Amount);//amountinpass
						}
				    	else
						{
							ps2.setDate(2,null);//remitance
				    		ps2.setDate(3, null);
				    		ps2.setDouble(7,0);//amountinpass
						}
				    	ps2.setString(4, txtParticular);
						ps2.setInt(5, txtCheque_NO);
						ps2.setString(6, txtDetails);
						//System.out.println("txtCr_Amount::"+txtCr_Amount);
						ps2.setDouble(8,txtCr_Amount);
						ps2.setDouble(9,txtDr_Amount);
						
						ps2.setString(10, action_required);
						ps2.setString(11, update_user);
						ps2.setTimestamp(12, ts);
						
						ps2.setInt(13, cmbAcc_UnitCode);
						ps2.setInt(14, cmbOffice_code);
						ps2.setInt(15, trnyr);
						ps2.setInt(16, trnmn);
						ps2.setLong(17, cmbBankAccNo);
						ps2.setInt(18, serialno);
				    	  int i=ps2.executeUpdate();	
				    	  if(i>0)
				    	  {
				    	  		xml+="<flag>success</flag>";	  
				    	  }
				    	  else
				    		  	xml+="<flag>failure</flag>";
				    }
				    catch(Exception e)
				    {
				    	  xml+="<flag>failure</flag>";   	
				    }
	      }
	      //update FAS_BRS_OB_TRANSACTION_NT set PASSBOOK_DATE=?,REMITTANCE_DATE=?,WITHDRAWAL_DATE=?,REASON=?,CHEQUE_DD_NO=?,DETAILS=?,AMOUNT_IN_PASSBOOK=?,CR_AMOUNT=?,DR_AMOUNT=?,FOLLOW_UP_ACTION_REQUIRED=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=? and SL_NO=?");
	   
	    		  if(command.equalsIgnoreCase("delete"))
	    	      {	int  serialno=Integer.parseInt(request.getParameter("serialno"));
	    	    	  		xml+="<command>"+command+"</command>";
	    				    try
	    				    {  
	    				    	  String sql_delete_mst="delete from FAS_BRS_OB_TRANSACTION_NT where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=? and SL_NO=?";
	    				    	  System.out.println(sql_delete_mst);
	    				    	  ps2=con.prepareStatement(sql_delete_mst);
	    				    	  ps2.setInt(1, cmbAcc_UnitCode);
	    				    	  System.out.println(cmbAcc_UnitCode);
	    				    	  ps2.setInt(2, cmbOffice_code);
	    				    	  System.out.println(txtCB_Year);
	    				    	  ps2.setInt(3, txtCB_Year);
	    				    	  System.out.println(txtCB_Month);
	    				    	  ps2.setInt(4, txtCB_Month);
	    				    	  ps2.setLong(5,cmbBankAccNo);
	    				    	  System.out.println(cmbBankAccNo);
	    				    	  ps2.setInt(6,serialno);				    	 
	    				    	  int i=ps2.executeUpdate();	
	    				    	  if(i>0)
	    				    	  {
	    				    	  		xml+="<flag>success</flag>";	  
	    				    	  }
	    				    	  else
	    				    		  	xml+="<flag>failure</flag>";
	    				    }
	    				    catch(Exception e)
	    				    {
	    				    	  xml+="<flag>failure</flag>";   	
	    				    }
	    	      }
	    		  
	    		  /*  
	    * 
	    * 
	    * if(command.equalsIgnoreCase("delete"))
	      {
	    	  		xml+="<command>"+command+"</command>";
				    try
				    {  
				    	  String sql_insert_mst="delete from FAS_BRS_OB where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and OB_TYPE=? and ACCOUNT_NO=?";
				    	  System.out.println(sql_insert_mst);
				    	  ps2=con.prepareStatement(sql_insert_mst);
				    	  ps2.setInt(1, cmbAcc_UnitCode);
				    	  System.out.println(cmbAcc_UnitCode);
				    	  ps2.setInt(2, cmbOffice_code);
				    	  System.out.println(txtCB_Year);
				    	  ps2.setInt(3, txtCB_Year);
				    	  System.out.println(txtCB_Month);
				    	  ps2.setInt(4, txtCB_Month);
				    	  ps2.setString(5,"NT");
				    	  System.out.println(cmbBankAccNo);
				    	  ps2.setLong(6,cmbBankAccNo);				    	 
				    	  int i=ps2.executeUpdate();	
				    	  if(i>0)
				    	  {
				    	  		xml+="<flag>success</flag>";	  
				    	  }
				    	  else
				    		  	xml+="<flag>failure</flag>";
				    }
				    catch(Exception e)
				    {
				    	  xml+="<flag>failure</flag>";   	
				    }
	      } */ 
	      xml+="</response>";
	      System.out.println("XML :: "+xml);
	      out.println(xml);
	}


}