package Servlets.HR.HR1.Payroll.Reports;


import Servlets.Security.classes.UserProfile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;


public class hrm_pay_ECS_report extends HttpServlet {
        private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
   
   String sub_type="";
   String acc_no="",netpay="",name="",employee_id="",bname="",braname="";
    
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
    }
    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
    	 Connection connection=null;
         Statement statement=null;    
         ResultSet result=null;
         ResultSet results3=null;
         PreparedStatement ps=null;
         response.setContentType("text/xml");
         PrintWriter pw=response.getWriter();   
         String strCommand = "",year,dateofRequest,sql; 
         String xml="";
         response.setHeader("Cache-Control","no-cache");
         HttpSession session=request.getSession(false);
         try
         {            
             if(session==null)
             {
                 System.out.println(request.getContextPath()+"/index.jsp?message=sessionout");
                 response.sendRedirect(request.getContextPath()+"/index.jsp?message=sessionout");
                return;
             }
             System.out.println(session);
                 
         }catch(Exception e)
         {
            System.out.println("Redirect Error :"+e);
         }
         try
         {
           strCommand = request.getParameter("command");      
         }
         catch(Exception e)
         {
           e.printStackTrace();
         }
       
    	try
        {
             ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
               String ConnectionString="";
              
               String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
               String strdsn=rs.getString("Config.DSN");
               String strhostname=rs.getString("Config.HOST_NAME");
               String strportno=rs.getString("Config.PORT_NUMBER");
               String strsid=rs.getString("Config.SID");
               String strdbusername=rs.getString("Config.USER_NAME");
               String strdbpassword=rs.getString("Config.PASSWORD");
                 
               ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
                Class.forName(strDriver.trim());
                connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
        }
    	catch (Exception e) {
    		if(strCommand.equalsIgnoreCase("Get"))
    		{
    			xml="Database Service not Available";
    		}
    		else
    		{
    			 xml="<response><status>success</status><value>databaseError</value></response>";
    		}
    		pw.write(xml);
    		 pw.flush();
    	       pw.close();	
    	       System.out.println("databse connection error");
		return;
		}
    	
        int count=0;
        String paygroupid="",offid="";
        String userid=(String)session.getAttribute("UserId");
        System.out.println("session id is:"+userid);
        if(strCommand.equalsIgnoreCase("Get"))
        {
        	try{
        	//	System.out.println("enter get");
        		paygroupid=request.getParameter("paygroup");
        		offid=request.getParameter("txtOffId");
        	Statement st = connection.createStatement();
        sql="Select  Pay_Bill_Subgroup_Id, Pay_Bill_Sub_Group_Desc, Pay_Bill_Subgroup_Short_Desc From  " +
        		"Hrm_Pay_Bill_Subgroup_Mst Where Pay_Bill_Group_Id='"+paygroupid+"'  And Office_Id='"+offid+"' ";
        System.out.println("sql "+sql);
		ResultSet result1 = st.executeQuery(sql);
		xml+="<response>";
		while (result1.next()) {
			count++;
			xml+="<Pay_Bill_Subgroups>";
			xml+="<Pay_Bill_Subgroup_Id>"+result1.getString("Pay_Bill_Subgroup_Id")+"</Pay_Bill_Subgroup_Id>"
			+"<Pay_Bill_Sub_Group_Desc>"+result1.getString("Pay_Bill_Sub_Group_Desc")+"</Pay_Bill_Sub_Group_Desc>";
			xml+="</Pay_Bill_Subgroups>";
		}
		if (count == 0) {
		
			xml+="<status>Nodate</status>";
		}
		else{
		xml+="<status>sucess</status>";
		}
		xml+="</response>";
        	}
        	catch (Exception e) {
        		e.printStackTrace();
			}

        }
        else if(strCommand.equalsIgnoreCase("pro_status"))
		{
			
			System.out.println("without using details if");
			xml="";
			xml+="<response><command>pro_status</command>";
			try
			{
			
			int processid=Integer.parseInt(request.getParameter("processid"));
						
			
			String sql2="SELECT PAYBILL_PROC_REQUIRED FROM HRM_PAY_REG_BILL_PROCESS_MST WHERE PAY_PROCESS_ID    ="+processid+" " ;
				
				
			Statement st = connection.createStatement();  
			System.out.println("query"+sql2);
			result =null;
			 result = st.executeQuery(sql2);
			int exist=0;
			while(result.next()) {
				exist=1;
				
				
				   xml+="<pay>" +
				   		"<PAYBILL_PROC_REQUIRED>"+result.getString("PAYBILL_PROC_REQUIRED")+"</PAYBILL_PROC_REQUIRED></pay>";
			}
			if(exist==1)
			 xml+="<status>success</status>";
			else
				xml+="<status>notexist</status>";
			result.close();
			st.close();
			}
			catch(Exception e)
			{
				System.out.println("exception"+e);
				xml+="<status>failure</status>";
			}
			xml+="</response>";
		}

        else if(strCommand.equalsIgnoreCase("process"))
		{
			
			System.out.println("without using details if");
			xml="";
			xml+="<response><command>PAY_PROCESS_ID</command>";
			try
			{
			int unit_code=Integer.parseInt(request.getParameter("unit_code"));
			int acc_off_id=Integer.parseInt(request.getParameter("acc_off_id"));
			int syear=Integer.parseInt(request.getParameter("syear"));
			int smonth=Integer.parseInt(request.getParameter("smonth"));
			String pay_group_id=request.getParameter("pay_group_id");
			System.out.println(unit_code+acc_off_id+acc_off_id);				
			System.out.println("unit_code"+unit_code+"acc_off_id"+acc_off_id+"syear"+syear);
			String sql2="SELECT PAY_PROCESS_ID, " 
				+"PAY_PROCESS_DESC,PAY_PROCESS_STATUS_DATE FROM HRM_PAY_REG_BILL_PROCESS_MST " 
				+" WHERE ACCOUNTING_UNIT_ID    ="+unit_code+" " 
				+"AND ACCOUNTING_FOR_OFFICE_ID="+acc_off_id+" " 
				+"AND PAY_PROCESS_YEAR        ="+syear+" " 
				+"AND PAY_PROCESS_MONTH       ="+smonth+""				
				+"AND PAY_BILL_GROUP_ID       ='"+pay_group_id+"'" +
						" and BILL_TYPE_ID='SUP' and SUPPLEMENT_TYPE_ID='2' ";
				
			Statement st = connection.createStatement();  
			System.out.println("query"+sql2);
			result =null;
			 result = st.executeQuery(sql2);
			int exist=0;
			while(result.next()) {
				exist=1;
				   xml+="<pay><PAY_PROCESS_ID>"+result.getInt("PAY_PROCESS_ID")+"</PAY_PROCESS_ID>" +
				   		"<PAY_PROCESS_DESC>"+result.getString("PAY_PROCESS_DESC")+"</PAY_PROCESS_DESC></pay>";
			}
			if(exist==1)
			 xml+="<status>success</status>";
			else
				xml+="<status>notexist</status>";
			result.close();
			st.close();
			}
			catch(Exception e)
			{
				System.out.println("exception"+e);
				xml+="<status>failure</status>";
			}
			xml+="</response>";
		}
        
        
        else if(strCommand.equalsIgnoreCase("emp"))
		{
			
			System.out.println("without using details if");
			xml="";
			xml+="<response><command>employee</command>";
			try
			{
			
			int pro_id=Integer.parseInt(request.getParameter("pro_id"));
			
		
			String sql2="select employee_id from HRM_PAY_REG_BILL_PROC_INCLSN where PAY_PROCESS_ID="+pro_id;
				
			Statement st = connection.createStatement();  
			System.out.println("query"+sql2);
			result =null;
			 result = st.executeQuery(sql2);
			int exist=0;
			while(result.next()) {
				exist=1;
				   xml+="<pay><employee_id>"+result.getInt("employee_id")+"</employee_id></pay>";
			}
			if(exist==1)
			 xml+="<status>success</status>";
			else
				xml+="<status>notexist</status>";
			result.close();
			st.close();
			}
			catch(Exception e)
			{
				System.out.println("exception"+e);
				xml+="<status>failure</status>";
			}
			xml+="</response>";
		}
        
    	else if(strCommand.equalsIgnoreCase("getdetails"))
		{
			
			System.out.println("without using details if");
			xml="";
			xml+="<response><command>PAY_PROCESS_ID</command>";
			try
			{
			int unit_code=Integer.parseInt(request.getParameter("unit_code"));
			int acc_off_id=Integer.parseInt(request.getParameter("acc_off_id"));
			int syear=Integer.parseInt(request.getParameter("syear"));
			int smonth=Integer.parseInt(request.getParameter("smonth"));
			String pay_group_id=request.getParameter("pay_group_id");
			System.out.println(unit_code+acc_off_id+acc_off_id);				
			System.out.println("unit_code"+unit_code+"acc_off_id"+acc_off_id+"syear"+syear);
			String sql2="SELECT PAY_PROCESS_ID, " 
				+"PAY_PROCESS_DESC,PAY_PROCESS_STATUS_DATE FROM HRM_PAY_REG_BILL_PROCESS_MST " 
				+" WHERE ACCOUNTING_UNIT_ID    ='"+unit_code+"' " 
				+"AND ACCOUNTING_FOR_OFFICE_ID='"+acc_off_id+"' " 
				+"AND PAY_PROCESS_YEAR        ='"+syear+"' " 
				+"AND PAY_PROCESS_MONTH       ='"+smonth+"'"
				+"AND PAY_PROCESS_MONTH       ='"+smonth+"'"
				+"AND PAY_BILL_GROUP_ID       ='"+pay_group_id+"'";
				
			Statement st = connection.createStatement();  
			System.out.println("query"+sql2);
			result =null;
			 result = st.executeQuery(sql2);
			int exist=0;
			while(result.next()) {
				exist=1;
				   xml+="<pay><PAY_PROCESS_ID>"+result.getInt("PAY_PROCESS_ID")+"</PAY_PROCESS_ID>" +
				   		"<PAY_PROCESS_DESC>"+result.getString("PAY_PROCESS_DESC")+"</PAY_PROCESS_DESC></pay>";
			}
			if(exist==1)
			 xml+="<status>success</status>";
			else
				xml+="<status>notexist</status>";
			result.close();
			st.close();
			}
			catch(Exception e)
			{
				System.out.println("exception"+e);
				xml+="<status>failure</status>";
			}
			xml+="</response>";
		}
        
        
        
        

        System.out.println(xml);
        pw.write(xml);
        pw.flush();
        pw.close();  
    }
    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
    	

   	 Connection connection=null;
        Statement statement=null;    
        ResultSet result=null;
        ResultSet results3=null;
        PreparedStatement ps=null;
        response.setContentType("text/xml");
   
        String strCommand = "",year,dateofRequest,sql; 
        String xml="";
        response.setHeader("Cache-Control","no-cache");
        
        HttpSession session=request.getSession(false);
        try
        {            
            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp?message=sessionout");
                response.sendRedirect(request.getContextPath()+"/index.jsp?message=sessionout");
               return;
            }
            System.out.println(session);
                
        }catch(Exception e)
        {
           System.out.println("Redirect Error :"+e);
        }
        
   	try
       {
            ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
              String ConnectionString="";
             
              String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
              String strdsn=rs.getString("Config.DSN");
              String strhostname=rs.getString("Config.HOST_NAME");
              String strportno=rs.getString("Config.PORT_NUMBER");
              String strsid=rs.getString("Config.SID");
              String strdbusername=rs.getString("Config.USER_NAME");
              String strdbpassword=rs.getString("Config.PASSWORD");
                
              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
               Class.forName(strDriver.trim());
               connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
       }
   	catch (Exception e) {
   		if(strCommand.equalsIgnoreCase("Get"))
   		{
   			xml="Database Service not Available";
   		}
   		else
   		{
   			 xml="<response><status>success</status><value>databaseError</value></response>";
   		}
   		
   	       System.out.println("databse connection error");
		return;
		}
   	
       int count=0;
       String paygroupid="",offid="";
       String userid=(String)session.getAttribute("UserId");
       System.out.println("session id is:"+userid);
    	String [] column_order = new String [6];
    	String [] column_name = new String [6];
    	String [] column_length = new String [6];
    	
    	String [] temp_order = new String [6];
    	String [] temp_name = new String [6];
    	String [] temp_length = new String [6];
    	
    	String [] column1 = new String [500];
    	String [] column2 = new String [500];
    	String [] column3 = new String [500];
    	String [] column4 = new String [500];
    	String [] column5 = new String [500];
    	String [] column6 = new String [500];
       try
		{
		int unit_code=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		int pro_id=Integer.parseInt(request.getParameter("pro_id"));
		int bank_id=Integer.parseInt(request.getParameter("pay_bill_subgroup_id"));
		String output=request.getParameter("output");	
		
		String empaccno_length=request.getParameter("empaccno_length");
		String empaccno_order=request.getParameter("empaccno_order");
		
		String empname_length=request.getParameter("empname_length");
		String empname_order=request.getParameter("empname_order");
		
		String empamount_length=request.getParameter("empamount_length");
		String empamount_order=request.getParameter("empamount_order");
		
		String empcode_length=request.getParameter("empcode_length");
		String empcode_order="";
		
		String bankname_length=request.getParameter("bankname_length");
		String bankname_order="";
		
		String branchname_length=request.getParameter("branchname_length");
		String branchname_order="";
		
		System.out.println("empcode_length: "+empcode_length);
		
		temp_order[0]=empaccno_order;
		temp_length[0]=empaccno_length;
		temp_name[0]="ACCOUNT NO";
		temp_order[1]=empname_order;
		temp_length[1]=empname_length;
		temp_name[1]="NAME";
		temp_order[2]=empamount_order;
		temp_length[2]=empamount_length;
		temp_name[2]="AMOUNT";
		int l=3;
		if(!empcode_length.equalsIgnoreCase(""))
		{
			empcode_order=request.getParameter("empcode_order");
			temp_order[l]=empcode_order;
			temp_length[l]=empcode_length;
			temp_name[l]="EMPLOYEE ID";
		l=l+1;
		}
		if(!bankname_length.equalsIgnoreCase(""))
		{
			bankname_order=request.getParameter("bankname_order");
			temp_order[l]=bankname_order;
			temp_length[l]=bankname_length;
			temp_name[l]="BANK NAME";
			l=l+1;
		}
		if(!branchname_length.equalsIgnoreCase(""))
		{
			branchname_order=request.getParameter("branchname_order");
			temp_order[l]=branchname_order;
			temp_length[l]=branchname_length;
			temp_name[l]="BRANCH NAME";
			l=l+1;
		}
		System.out.println("l : "+l);

			for(int p=0;p<l;p++)
			{
			System.out.println("temp_order["+p+"] = " +temp_order[p]);
				if(temp_order[p].equalsIgnoreCase("1"))
				{
					System.out.println("COME HERE");
					column_order[0]=temp_order[p];
					column_length[0]=temp_length[p];
					column_name[0]=temp_name[p];
					
				}
				else if(temp_order[p].equalsIgnoreCase("2"))
				{
					column_order[1]=temp_order[p];
					column_length[1]=temp_length[p];
					column_name[1]=temp_name[p];
					
				}
				else if(temp_order[p].equalsIgnoreCase("3"))
				{
					column_order[2]=temp_order[p];
					column_length[2]=temp_length[p];
					column_name[2]=temp_name[p];
					
				}
				else if(temp_order[p].equalsIgnoreCase("4"))
				{
					column_order[3]=temp_order[p];
					column_length[3]=temp_length[p];
					column_name[3]=temp_name[p];
					
				}
				else if(temp_order[p].equalsIgnoreCase("5"))
				{
					column_order[4]=temp_order[p];
					column_length[4]=temp_length[p];
					column_name[4]=temp_name[p];
					
				}
				else if(temp_order[p].equalsIgnoreCase("6"))
				{
					column_order[5]=temp_order[p];
					column_length[5]=temp_length[p];
					column_name[5]=temp_name[p];
					
				}
			
			}
		
			for(int j=0;j<column_name.length;j++)
			{
				System.out.println("column_name["+j+"]  : " +column_name[j]);
				System.out.println("temp_name["+j+"]  : " +temp_name[j]);
			}
	
		
		String sql2="SELECT mst.employee_id AS employee_id, " +
		"  empname, " +
		"  EMP_BANK_ACCOUNT_NO, " +
		"  (pay_earn-pay_ded) AS netpay , " +
		"  BANK_NAME, " +
		"  BANK_BRANCH_NAME " +
		"FROM " +
		"  (SELECT employee_id, " +
		"    pay_process_id " +
		"  FROM hrm_pay_reg_bill_proc_inclsn " +
		"  WHERE pay_process_id ="+pro_id+" " +
		"  AND employee_id NOT IN " +
		"    (SELECT employee_id " +
		"    FROM hrm_pay_reg_bill_proc_exclsn " +
		"    WHERE pay_process_id="+pro_id+" " +
		"    ) " +
		"  )mst " +
		"JOIN " +
		"  (SELECT employee_id, " +
		"    EMP_BANK_ACCOUNT_NO, " +
		"    BANK_ID, " +
		"    BANK_BRANCH_ID " +
		"  FROM hrm_pay_emp_bank_ac_mst " +
		"  WHERE bank_id="+bank_id+" " +
		"  )bank " +
		"ON bank.employee_id=mst.employee_id " +
		"JOIN " +
		"  (SELECT BANK_ID,BANK_NAME FROM HRM_PAY_GLOBAL_BANK_MST " +
		"  )ban " +
		"ON ban.bank_id=bank.bank_id " +
		"JOIN " +
		"  (SELECT BANK_ID, " +
		"    BANK_BRANCH_ID, " +
		"    BANK_BRANCH_NAME " +
		"  FROM HRM_PAY_GLOBAL_BANK_BRANCH_MST " +
		"  )branch " +
		"ON branch.bank_id        =bank.bank_id " +
		"AND branch.BANK_BRANCH_ID=bank.BANK_BRANCH_ID " +
		"JOIN " +
		"  (SELECT employee_id, " +
		"   EMPLOYEE_INITIAL  " +
		"    || '.' " +
		"    ||employee_name AS empname " +
		"  FROM view_employee2 " +
		"  )emp " +
		"ON emp.employee_id =mst.employee_id " +
		"JOIN " +
		"  (SELECT employee_id, " +
		"    SUM(pay_element_value) AS pay_earn, " +
		"    salary_process_id " +
		"  FROM hrm_pay_emp_earnings_trn " +
		"  GROUP BY employee_id, " +
		"    salary_process_id " +
		"  )ear " +
		"ON mst.pay_process_id=ear.salary_process_id " +
		"AND mst.employee_id  =ear.employee_id " +
		"JOIN " +
		"  (SELECT employee_id, " +
		"    SUM(PAY_ELEMENT_VALUE) AS pay_ded, " +
		"    PAY_PROCESS_ID " +
		"  FROM HRM_PAY_EMP_RECOVERY_VIEW " +
		"  GROUP BY employee_id, " +
		"    PAY_PROCESS_ID " +
		"  )ded " +
		"ON mst.pay_process_id=ded.PAY_PROCESS_ID " +
		"AND mst.employee_id  =ded.employee_id " +
		"ORDER BY employee_id";
			
		Statement st = connection.createStatement();  
//		System.out.println("query :"+sql2);
		result =null;
		 result = st.executeQuery(sql2);
		int exist=0;
		acc_no="";
		employee_id="";
		netpay="";
		name="";
		bname="";
		braname="";
		String [] emp_id =new String[500] ;
		String [] ac_no=new String[500];
		String [] net=new String[500];
		String [] empname=new String[500];
		String [] bankname=new String[500];
		String [] branchname=new String[500];
		String [] text=new String[1000];
		HSSFRow row=null;
		int i=0;
		while(result.next()) {
			emp_id[i]=result.getString("employee_id");
			ac_no[i]=result.getString("EMP_BANK_ACCOUNT_NO");
			net[i]=result.getString("netpay");
			empname[i]=result.getString("empname");
			bankname[i]=result.getString("BANK_NAME");
			branchname[i]=result.getString("BANK_BRANCH_NAME");
			i++;
		}
		
		
		HSSFWorkbook hwb=new HSSFWorkbook();
		HSSFSheet sheet =  hwb.createSheet("new sheet");

		HSSFRow rowhead=   sheet.createRow((short)0);
		for(int j=0;j<l;j++)
		{
		rowhead.createCell((short) j).setCellValue(column_name[j]);
		
		}
//		rowhead.createCell((short) 1).setCellValue("NAME");
//		rowhead.createCell((short) 2).setCellValue("AMOUNT");
		
//		rowhead.createCell((short) 3).setCellValue("EMPLOYEE CODE");
//		rowhead.createCell((short) 4).setCellValue("BANK NAME");
//		rowhead.createCell((short) 5).setCellValue("BRANCH NAME");
		
		String column_value="";

		int id=0;
		for(int j=0;j<i;j++)
		{
//			System.out.println("emp_id : "+ emp_id[j]);
//			System.out.println("ac_no : "+ ac_no[j]);
//			System.out.println("net : "+ net[j]);
//			System.out.println("empname : "+ empname[j]);
			
			
//			
//			
//			employee_id=emp_id[j];
//			acc_no=ac_no[j];
//			netpay=net[j]+"00";
//			netpay=netpay.trim();
//			name=empname[j].trim();
//			bname=bankname[j];
//			braname=branchname[j];
////			System.out.println(employee_id);
			id=j+1;
//
//			int acclen=acc_no.length();
//			int netlen=netpay.length();
//			int namelen=name.length();
////			System.out.println(acclen);
////			System.out.println(netlen);
//			
//			while(acclen<12)
//			{
//				acc_no="0"+acc_no;
//				acclen=acclen+1;
//			}
//			while(namelen<30)
//			{
//				name=name+" ";
//				namelen=namelen+1;
//			}
//			while(netlen<10)
//			{
//				netpay="0"+netpay;
//				netlen=netlen+1;
//			}
//			
////			System.out.println("acc_no="+acc_no.length());
////			System.out.println("netpay="+netpay.length());
////			System.out.println("name="+name.length());
////			System.out.println("employee_id="+employee_id);
			row=   sheet.createRow((short)id);
//			row.createCell((short) 0).setCellValue(acc_no);
//			row.createCell((short) 1).setCellValue(name);
//			row.createCell((short) 2).setCellValue(netpay);
//			
////			row.createCell((short) 3).setCellValue(employee_id);
////			row.createCell((short) 4).setCellValue(bname);
////			row.createCell((short) 5).setCellValue(braname);
//			
//			text[j]=acc_no+name+netpay;
			
			text[j]="";
			
			for(int k=0;k<l;k++)
			{
				int len=0;
				if(column_name[k].equalsIgnoreCase("ACCOUNT NO"))
				{
					column_value=ac_no[j].trim();
					len=column_value.length();
					while(len<Integer.parseInt(column_length[k]))
					{
						column_value="0"+column_value;
						len=len+1;
					}
					if(output.equalsIgnoreCase("TXTB"))
					{
						column_value=column_value.substring(0, column_value.length()-1);
						column_value=column_value+"0";
					}
					
				}
				else if(column_name[k].equalsIgnoreCase("NAME"))
				{
					column_value=empname[j].trim();
					
					len=column_value.length();
					while(len<Integer.parseInt(column_length[k]))
					{
						column_value=column_value+" ";
						len=len+1;
					}
				}
				else if(column_name[k].equalsIgnoreCase("AMOUNT"))
				{
					column_value=net[j]+"00";
					column_value=column_value.trim();
					len=column_value.length();
					while(len<Integer.parseInt(column_length[k]))
					{
						column_value="0"+column_value;
						len=len+1;
					}
				}
				else if(column_name[k].equalsIgnoreCase("EMPLOYEE ID"))
				{
					column_value=emp_id[j].trim();
					
					len=column_value.length();
					while(len<Integer.parseInt(column_length[k]))
					{
						column_value="0"+column_value;
						len=len+1;
					}
				}
				else if(column_name[k].equalsIgnoreCase("BANK NAME"))
				{
					column_value=bankname[j].trim();
					len=column_value.length();
					while(len<Integer.parseInt(column_length[k]))
					{
						column_value=column_value+" ";
						len=len+1;
					}
				}
				else if(column_name[k].equalsIgnoreCase("BRANCH NAME"))
				{
					column_value=branchname[j].trim();
					len=column_value.length();
					while(len<Integer.parseInt(column_length[k]))
					{
						column_value=column_value+" ";
						len=len+1;
					}
				}
				
				text[j]=text[j]+column_value;
				
				row.createCell((short) k).setCellValue(column_value);
				
			}
			
			
			
			
			
			
			
		}
		
		if(output.equalsIgnoreCase("EXCEL"))
		{
		response.setContentType("application/vnd.ms-excel");
        response.setHeader ("Content-Disposition", "attachment;filename=\"ECS.xls\"");
		ServletOutputStream fileOut = response.getOutputStream();
		hwb.write(fileOut);
		fileOut.close();
		}
		else
		{
			response.setContentType("application/text");
	        response.setHeader ("Content-Disposition", "attachment;filename=\"ECS.txt\"");
	        PrintWriter out=response.getWriter();
			for(int m=0;m<i;m++)
				out.println(text[m]);
			
			out.close();
		}
		}
		catch(Exception e)
		{
			
		}
    }
}
