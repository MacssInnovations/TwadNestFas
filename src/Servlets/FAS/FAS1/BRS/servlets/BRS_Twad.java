package Servlets.FAS.FAS1.BRS.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;


public class BRS_Twad extends HttpServlet {
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
	        PreparedStatement ps2=null;
	        ResultSet rs2=null;       
	        
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
	        String ob_type="";
	        int txtCB_Month=0,txtCB_Year=0;
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
		    /* Get Cashbook Year */
		    try
		    {txtCB_Year_to = Integer.parseInt(request.getParameter("txtCB_Year_To"));
		    }catch(Exception e){System.out.println("Error Not Getting Cashbook Year -->"+e);
		    }
		    
		    /* Get Cashbook Month */
		    try
		    {txtCB_Month_to = Integer.parseInt(request.getParameter("txtCB_Month_To"));
		    }catch(Exception e){System.out.println("Error Not Getting Cashbook Month -->"+e);
		    }
		    
		    try{ob_type = request.getParameter("ob_type");
		    }catch(Exception e){System.out.println("Error Not Getting Operation Mode -->"+e);
		    }
		    String option=request.getParameter("option");
		    try
		    {BankAccNo=Long.parseLong(request.getParameter("cmbBankAccNo"));
		    }catch(Exception e){System.out.println("Err in cmbBankAccNo "+e.getMessage());}
		    
		    
		    String xml="<response>";
		    if(command.equalsIgnoreCase("LoadList"))
		    {
		    	
		    	try
			    {txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
			    }catch(Exception e){System.out.println("Error Not Getting Cashbook Year -->"+e);
			    }
			    
			    /* Get Cashbook Month */
			    try
			    {txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
			    }catch(Exception e){System.out.println("Error Not Getting Cashbook Month -->"+e);
			    }
		    	
		    		xml=xml+"<command>"+command+"</command>";
				    try
				    {				    		
					        sql="select * from \n " + 
					        "(" + 
					        "    select  trim(ACCOUNT_NO)as ACCOUNT_NO,BANK_ID,BRANCH_ID,ACCOUNT_HEAD_CODE,to_char(PASS_BOOK_DATE,'dd/mm/yyyy') as PASS_BOOK_DATE,trim(to_char(OB_PART1,'99999999999999.99')) as OB_PART1,trim(to_char(OB_PART2A,'99999999999999.99')) as OB_PART2A,trim(to_char(OB_PART2B,'99999999999999.99')) as OB_PART2B \n" + 
					        "    from FAS_BRS_OB \n" + 
					        "    where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? " +
					       "	 and to_date( ( CASHBOOK_MONTH	"+
						       " 		  || '-' 	"+
						       "		  || CASHBOOK_YEAR) ,'mm-yyyy') BETWEEN to_date( ? 	"+
						       " 		  || '-'	"+
						       "		  || ? ,'mm-yyyy')	"+
						       " 		AND to_date( ?	"+
						       " 		  || '-' 	"+
						       "		  || ? ,'mm-yyyy' ) 	"+
					        "	and OB_TYPE='T'  and ACCOUNT_NO="+BankAccNo+")x " + 
					        "left outer join															\n" + 
					        "(																				 \n" + 
					        "    select bank_id as y_bank_id ,trim(bank_name) as y_bank_name from fas_bank_list		\n" + 
					        ")Y																		\n" + 
					        "on X.bank_id  = Y.y_bank_id													\n" + 
					        "left outer join 															\n" + 
					        "(																			\n" + 
					        "    select  BANK_ID as z_bank_id, BRANCH_ID as z_BRANCH_ID ,trim(BRANCH_NAME) as z_BRANCH_NAME from fas_mst_bank_branches	                   \n" + 
					        ")Z " + 
					        "on  																		\n" + 
					        "X.bank_id  = Z.z_bank_id  and											 \n" + 
					        "X.BRANCH_ID = Z.z_branch_id " +
					        "Left Outer Join "+
							" (Select Ob_Status,Account_No as acno From Fas_Brs_Ob_Status Where Accounting_Unit_Id    ="+cmbAcc_UnitCode+")Aaa "+
							" on aaa.acno=x.ACCOUNT_NO";
                           
					        if(option.equals("Edit"))
					        {
				        		sql=sql+" where x.ACCOUNT_NO=? ";
                                System.out.println("Query passed ::::::::::"+sql);
					        }
					        else if(option.equals("List"))
					        {
					        	sql=sql+" where x.ACCOUNT_NO=? ";
                                System.out.println("Query passed ::::::::::"+sql);
					        }
					        
					     //   System.out.println("SQL ::: "+sql);
					        
                            ps2=con.prepareStatement(sql);
				    		ps2.setInt(1,cmbAcc_UnitCode);
				    		ps2.setInt(2,cmbOffice_code);
				    		ps2.setInt(3,txtCB_Month);
				    		ps2.setInt(4,txtCB_Year);
				    		ps2.setInt(5,txtCB_Month);
				    		ps2.setInt(6,txtCB_Year);
				    		if(option.equals("Edit"))
				    		{
			    				ps2.setLong(7,BankAccNo);
				    		}  
				    		else if(option.equals("List"))
				    		{
			    				ps2.setLong(7,BankAccNo);
				    		}   
                                        /*        System.out.println("1:"+cmbAcc_UnitCode);
                                                System.out.println("2:"+cmbOffice_code);
                                                System.out.println("3:"+txtCB_Year_from);
                                                System.out.println("4:"+txtCB_Month_from);
                                                System.out.println("5:"+BankAccNo);
				    		System.out.println("option passed"+option); */
                            rs2=ps2.executeQuery();
				    		while(rs2.next())
				    		{
				    				count++;
				    				xml+="<acc_no>"+rs2.getString("ACCOUNT_NO")+"</acc_no>";
				    				xml+="<bank_id>"+rs2.getString("BANK_ID")+"</bank_id>";
				    				xml+="<branch_id>"+rs2.getString("BRANCH_ID")+"</branch_id>";
				    				xml+="<bank_name>"+rs2.getString("y_bank_name")+"</bank_name>";
				    				xml+="<branch_name>"+rs2.getString("z_BRANCH_NAME")+"</branch_name>";
				    				xml+="<acc_head_code>"+rs2.getString("ACCOUNT_HEAD_CODE")+"</acc_head_code>";
				    				xml+="<OB1>"+rs2.getString("OB_PART1")+"</OB1>";
				    				xml+="<OB2>"+rs2.getString("OB_PART2A")+"</OB2>";
				    				xml+="<OB3>"+rs2.getString("OB_PART2B")+"</OB3>";
				    				xml+="<obStatus>"+rs2.getString("Ob_Status")+"</obStatus>";
				    		}
				    		System.out.println("command passed ::::"+command);
                            System.out.println("no of records :::"+count);
                            if(count>0)
                            {
                            	xml+="<flag>success</flag>";
                            }
				    		else
				    		{
				    				xml+="<flag>failure</flag>";
				    		}
				    			
				    }
				    catch(Exception e)
				    {
				    		xml+="<flag>failure</flag>";
				    		System.out.println("Err in list Selection :: "+e.getMessage());
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
	      PreparedStatement ps2=null;     
	      response.setContentType(CONTENT_TYPE);
	      response.setHeader("Cache-Control","no-cache");
	      PrintWriter out = response.getWriter();
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
	      String txtOprCode=null;      
	      int txtBankID=0;
	      double OB1=0,OB2=0,OB3=0;
	      int txtBranchID=0;//txtCheque_NO=0;
	      
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
	      try
	      {
			    	cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			    	System.out.println("cmbOffice_code-->"+cmbOffice_code);
	    	  
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting Accounting for Office Id --> "+e);
	      }
	      try
	      {
	    	  		txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
	    	  		System.out.println("txtCB_Year-->"+txtCB_Year);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting Cashbook Year -->"+e);
	      }
	      try
	      {
	    	  		txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
	    	  		System.out.println("txtCB_Month-->"+txtCB_Month);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting Cashbook Month -->"+e);
	      }
	      try
	      {
	    	  		cmbBankAccNo = Long.parseLong(request.getParameter("cmbBankAccNo"));
	    	  		System.out.println("cmbBankAccNo-->"+cmbBankAccNo);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting Bank Account Number -->"+e);
	      }
	      try
	      {
	    	  		txtOprCode = request.getParameter("txtOprCode");
	    	  		System.out.println("txtOprMode-->"+txtOprCode);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting Operation Mode -->"+e);
	      }
	      try
	      {
	    	  		txtBankID = Integer.parseInt(request.getParameter("txtBankID"));
	    	  		System.out.println("txtBankID-->"+txtBankID);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting Bank ID -->"+e);
	      }
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
	    	  		OB1=Double.parseDouble(request.getParameter(("OB1")));
	    	  		System.out.println("txtCr_Amount-->"+OB1);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting OB1-->"+e);
	      }
	      try
	      {
	    	  		OB2 =Double.parseDouble(request.getParameter(("OB2")));
	    	  		System.out.println("OB2-->"+OB2);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting OB2-->"+e);
	      }
	      try
	      {
	    	  		OB3 =Double.parseDouble(request.getParameter(("OB3")));
	    	  		System.out.println("OB3-->"+OB3);
	      }
	      catch(Exception e)
	      {
	    	  		System.out.println("Error Not Getting OB3-->"+e);
	      }
	     

	      String update_user=(String)session.getAttribute("UserId");
	      System.out.println("update_user-->"+update_user);

	      long l=System.currentTimeMillis();
	      Timestamp ts=new Timestamp(l);
	      System.out.println("Timestamp -->"+ts); 
	      String xml="<response>";
	      if(command.equalsIgnoreCase("add"))
	      {
	    	  String ac=request.getParameter("cmbBankAccNo");
	    	 long account=Long.parseLong(ac);
	    	  //int ac_no=Integer.parseInt("ac");
	    	  System.out.println("ac::account::"+account);
	    	  		xml+="<command>"+command+"</command>";
				    try
				    {  
				    	  String sql_insert_mst="insert into FAS_BRS_OB(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,OB_TYPE,ACCOUNT_NO,BANK_ID,BRANCH_ID,ACCOUNT_HEAD_CODE,OB_PART1,OB_PART2A,OB_PART2B,UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
				    	  ps2=con.prepareStatement(sql_insert_mst);
				    	//  System.out.println("cmbAcc_UnitCode ::: "+cmbAcc_UnitCode);
				    	  ps2.setInt(1,cmbAcc_UnitCode);
				    	  ps2.setInt(2,cmbOffice_code);
				    	  ps2.setInt(3,txtCB_Year);
					      ps2.setInt(4,txtCB_Month);
				    	  ps2.setString(5,"T");
				    	  ps2.setLong(6,account);
				    	  ps2.setInt(7,txtBankID);
				    	  ps2.setInt(8,txtBranchID);
				    	  ps2.setString(9,txtOprCode);
				    	  ps2.setDouble(10,OB1);
				    	  ps2.setDouble(11,OB2);
				    	  ps2.setDouble(12,OB3);
				    	  ps2.setString(13,update_user);
				    	  ps2.setTimestamp(14,ts);
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
				    	  System.out.println("Err in Insertion :: "+e.getMessage());
				    	  xml+="<flag>failure</flag>";   	
				    }
	      }
	      if(command.equalsIgnoreCase("update"))
	      {
	    	  		xml+="<command>"+command+"</command>";
				    try
				    {  
				    	  String sql_insert_mst="update FAS_BRS_OB set BANK_ID=?,BRANCH_ID=?,ACCOUNT_HEAD_CODE=?,OB_PART1=?,OB_PART2A=?,OB_PART2B=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and OB_TYPE='T' and ACCOUNT_NO=?";
				    	  ps2=con.prepareStatement(sql_insert_mst);	
				    	  ps2.setInt(1, txtBankID);
				    	  ps2.setInt(2, txtBranchID);
				    	  ps2.setString(3, txtOprCode);
				    	  ps2.setDouble(4,OB1);
				    	  ps2.setDouble(5,OB2);
				    	  ps2.setDouble(6,OB3);
				    	  ps2.setString(7,update_user);
				    	  ps2.setTimestamp(8,ts);
				    	  ps2.setInt(9, cmbAcc_UnitCode);
				    	  ps2.setInt(10, cmbOffice_code);
				    	  
				    	  ps2.setInt(11, txtCB_Year);
				    	  ps2.setInt(12, txtCB_Month);
				    	 // ps2.setString(13,"T");
				    	  ps2.setLong(13,cmbBankAccNo);
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
				    	  System.out.println("Err in updation :: "+e.getMessage());
				    	  xml+="<flag>failure</flag>";   	
				    }
	      }
	      if(command.equalsIgnoreCase("delete"))
	      {
	    	  		xml+="<command>"+command+"</command>";
				    try
				    {  
				    	  String sql_insert_mst="delete from FAS_BRS_OB where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and OB_TYPE='T' and ACCOUNT_NO=?";
				    	  ps2=con.prepareStatement(sql_insert_mst);
				    	  ps2.setInt(1, cmbAcc_UnitCode);
				    	  ps2.setInt(2, cmbOffice_code);
				    	 
				    	  ps2.setInt(3, txtCB_Year);
				    	  ps2.setInt(4, txtCB_Month);
				    	  ps2.setLong(5,cmbBankAccNo);				    	 
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
	     
	      
	      
	      
	      xml+="</response>";
	      System.out.println("XML :: "+xml);
	      out.println(xml);
	}


}