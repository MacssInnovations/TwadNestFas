package Servlets.FAS.FAS1.CivilBills.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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

import Servlets.Security.classes.UserProfile;


public class PassOrderPreparation_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	//private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
       
    
    public PassOrderPreparation_servlet() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
		
		  String CONTENT_TYPE = "text/xml; charset=windows-1252";
	       response.setContentType(CONTENT_TYPE);
	       PrintWriter out = response.getWriter();
	
			response.setHeader("Cache-Control", "no-cache");
		
	        Connection con=null;
	        PreparedStatement ps,ps1,ps2;
	        Statement st=null;
	        ResultSet result=null,result1=null,result2=null;
	        int eid=0,unitid=0,offid=0,major=0,count=0,minor=0,checkcode=0;
	        String cmd,xml="",checkDesc="",apply="",mand="";
	        
	        cmd=request.getParameter("command");
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
	                  con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	                  try
	                  {
	                        st=con.createStatement();
	                        con.clearWarnings();
	                  }
	                  catch(SQLException e)
	                  {
	                        System.out.println("Exception in creating statement:"+e);
	                  }          
	        }
	        catch(Exception e)
	        {
	                   System.out.println("Exception in openeing connection:"+e);
	        }
	          
	        HttpSession session=request.getSession(false);
	        String update_user=(String)session.getAttribute("UserId");
	        UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
	        eid=empProfile.getEmployeeId();
	        System.out.println("employee id:"+eid);
	         try
	         {
	            
	                if(session==null)
	                {
	                    System.out.println(request.getContextPath()+"/index.jsp");
	                    response.sendRedirect(request.getContextPath()+"/index.jsp");                   
	                }
	                System.out.println(session);
	                
	        }catch(Exception e)
	        {
	        System.out.println("Redirect Error :"+e);
	        }
	         String userid=(String)session.getAttribute("UserId");
	         System.out.println("session id is:"+userid);
	         long l=System.currentTimeMillis();
	         Timestamp ts=new Timestamp(l);
	        
	         try{unitid= Integer.parseInt(request.getParameter("unitid"));} 
	         catch(Exception e1){System.out.println("Err in getting offid "+e1.getMessage()); }
	         System.out.println("unitid"+unitid);
	         try
	         {offid= Integer.parseInt(request.getParameter("offid")); }
	         catch(Exception e2){ System.out.println("Err in getting offid "+e2.getMessage()); }
	         System.out.println("offid"+offid); 
	         
	         if(cmd.equalsIgnoreCase("add")) 
	         {
	        	 xml="<response><command>add</command>";
	        	 
	             checkDesc=request.getParameter("checkDesc");
	             major= Integer.parseInt(request.getParameter("major2"));
	             minor= Integer.parseInt(request.getParameter("minor"));
	             System.out.println("minor"+minor);
	             mand=request.getParameter("mand");
	             apply=request.getParameter("apply");
	             int chkcode=0;
	             String sql;
	            try
	             {
	                      sql="SELECT CHECK_CODE FROM FAS_BILL_SCRUTINY_CHECKLIST GROUP BY CHECK_CODE HAVING CHECK_CODE =(select max(CHECK_CODE) from FAS_BILL_SCRUTINY_CHECKLIST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?)";
	                      ps=con.prepareStatement(sql);
	                      ps.setInt(1,unitid);
	                      ps.setInt(2,offid);
	                       result=ps.executeQuery();
	                      if(result.next())
	                      {
	                    	  chkcode = result.getInt(1);                                              
	                      }
	                      chkcode=chkcode+1;
	                      result.close();
	             }                  
	             catch(Exception e){System.out.println("exception"+e );}
	             System.out.println("chkcode "+chkcode); 
	             try 
	             {
	            	 ps = con.prepareStatement("insert into FAS_BILL_SCRUTINY_CHECKLIST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CHECK_CODE,CHECK_DESC,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,MANDATE,NOT_APPLICABLE,STATUS,UPDATED_BY_USERID,UPDATED_DATE)values(?,?,?,?,?,?,?,?,?,?,?)");
	                 ps.setInt(1,unitid);
	                 ps.setInt(2,offid);
	                 ps.setInt(3,chkcode);
	                 ps.setString(4,checkDesc);
	                 ps.setInt(5,major);
	                 ps.setInt(6,minor);
	                 ps.setString(7, mand);
	                 ps.setString(8, apply);
	                 ps.setString(9, "L");
	                 ps.setString(10,update_user);
	                 ps.setTimestamp(11,ts);
	                 int i1=ps.executeUpdate();  
	                 
	                 if(i1>=1)
	                 {
	                	 	xml=xml+"<flag>success</flag>";
	                	    System.out.println("<<major>>"+major);
	                	 	System.out.println("<<minor>>"+minor);
		                	ps1=con.prepareStatement("select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where BILL_MAJOR_TYPE_CODE="+major);
		                    result1=ps1.executeQuery();
		                    if(result1.next()){
		                    	xml=xml+"<majorCode>" +result1.getInt("BILL_MAJOR_TYPE_CODE")+ "</majorCode>";
		                    	xml=xml+"<majorDesc>" +result1.getString("BILL_MAJOR_TYPE_DESC")+ "</majorDesc>";
		                    }
		                    else{
		                    	xml=xml+"<majorCode>" +"0"+ "</majorCode>";
		                    	xml=xml+"<majorDesc>" +"--"+ "</majorDesc>";
		                    }
		                    ps2 = con.prepareStatement("select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE="+major+" and BILL_MINOR_TYPE_CODE="+minor);
	                        result2 = ps2.executeQuery();	
	                        if(result2.next()){
	                        	xml=xml+"<minorCode>" +result2.getInt("BILL_MINOR_TYPE_CODE")+ "</minorCode>";
		                    	xml=xml+"<minorDesc>" +result2.getString("BILL_MINOR_TYPE_DESC")+ "</minorDesc>";
	                        }
		                    else{
		                    	xml=xml+"<minorCode>" +"0"+ "</minorCode>";
		                    	xml=xml+"<minorDesc>" +"--"+ "</minorDesc>";
		                    }
	                        xml=xml+"<checkcode>" +chkcode+ "</checkcode>";
	                        xml=xml+"<checkdesc>" +checkDesc+ "</checkdesc>";
	                        xml=xml+"<mandate>" +mand+ "</mandate>"; 
	                        xml=xml+"<notapply>" +apply+ "</notapply>";

	                 }
	                 else
	                    xml=xml+"<flag>failure</flag>";
	             }
	             catch(Exception e) 
	             {
	                 System.out.println("exception in add"+e);
	                 xml=xml+"<flag>failure</flag>";
	             }
	         xml=xml+"</response>";             
	             
	         }
	      /*   else if (cmd.equalsIgnoreCase("getname_employee")) {

	 			xml = xml + "<response><command>getname_employee</command>";
	 			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	 			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
	 			try {
	 				
	 				String su1 = "SELECT b.office_id, "+
					 " b.office_name,a.empname "+
					 " FROM "+
							 " (SELECT p.employee_id,(select e.employee_initial||'.'||e.employee_name from hrm_mst_employees e where e.employee_id=p.employee_id)as empname, "+
					"    office_id "+
					" 	  FROM HRM_EMP_CURRENT_POSTING p "+
					" 	  WHERE employee_id= "+empid+
					" 	  )a "+
					" 	LEFT OUTER JOIN "+
					" 	  (SELECT office_id,office_name FROM COM_MST_OFFICES "+
					" 	  )b "+
					" 	ON a.office_id=b.office_id";
	 				System.out.println("su1:::"+su1);
	 				ps = con.prepareStatement(su1);
	 				//ps.setInt(1, empid);
	 				ResultSet rs = ps.executeQuery();
	 				if (rs.next()) {
	 					xml = xml + "<empid>" + rs.getInt("EMPLOYEE_ID")+ "</empid>";
	 					xml = xml + "<empname>" + rs.getString("empname")+ "</empname>";
	 					xml = xml + "<officename>" + rs.getString("officename")+ "</officename>";
	 					
	 				}
	 				xml = xml + "<flag>success</flag>";
	 			} catch (Exception e) {
	 				xml = xml + "<flag>failure</flag>";
	 				e.printStackTrace();
	 			}
	 		} */
	         else if(cmd.equalsIgnoreCase("updated"))
	         {
	        	 xml="<response><command>updated</command>"; 
	        	 System.out.println("upda");
	        	 checkcode= Integer.parseInt(request.getParameter("checkcode"));
	        	 System.out.println("checkcode"+checkcode);
	        	 checkDesc=request.getParameter("checkDesc");
	        	 System.out.println("checkDesc"+checkDesc);
	             major= Integer.parseInt(request.getParameter("major2"));
	             System.out.println("major"+major);
	             minor= Integer.parseInt(request.getParameter("minor"));
	            System.out.println("minor"+minor);
	             mand=request.getParameter("mand");
	             apply=request.getParameter("apply");
	             try {
	                 ps = con.prepareStatement("update FAS_BILL_SCRUTINY_CHECKLIST set CHECK_DESC=?,BILL_MAJOR_TYPE_CODE=?,BILL_MINOR_TYPE_CODE=?,MANDATE=?,NOT_APPLICABLE=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CHECK_CODE=? ");    
	                 ps.setString(1, checkDesc);
	                 ps.setInt(2,major);
	                 ps.setInt(3,minor);
	                 ps.setString(4, mand);
	                 ps.setString(5, apply);
	                 ps.setString(6,update_user);
	                 ps.setTimestamp(7,ts);
	                 ps.setInt(8,unitid);
	                 ps.setInt(9,offid);
	                 ps.setInt(10,checkcode);
	                
	                 int i2=ps.executeUpdate(); 
	                 if(i2>=1)
	                 {
		                	 xml=xml+"<flag>success</flag>";
		             	    System.out.println("<<major>>"+major);
		             	 	System.out.println("<<minor>>"+minor);
			                	ps1=con.prepareStatement("select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where BILL_MAJOR_TYPE_CODE="+major);
			                    result1=ps1.executeQuery();
			                    if(result1.next()){
			                    	xml=xml+"<majorCode>" +result1.getInt("BILL_MAJOR_TYPE_CODE")+ "</majorCode>";
			                    	xml=xml+"<majorDesc>" +result1.getString("BILL_MAJOR_TYPE_DESC")+ "</majorDesc>";
			                    }
			                    else{
			                    	xml=xml+"<majorCode>" +"0"+ "</majorCode>";
			                    	xml=xml+"<majorDesc>" +"--"+ "</majorDesc>";
			                    }
			                    ps2 = con.prepareStatement("select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE="+major+" and BILL_MINOR_TYPE_CODE="+minor);
		                     result2 = ps2.executeQuery();	
		                     if(result2.next()){
		                     	xml=xml+"<minorCode>" +result2.getInt("BILL_MINOR_TYPE_CODE")+ "</minorCode>";
			                    	xml=xml+"<minorDesc>" +result2.getString("BILL_MINOR_TYPE_DESC")+ "</minorDesc>";
		                     }
			                    else{
			                    	xml=xml+"<minorCode>" +"0"+ "</minorCode>";
			                    	xml=xml+"<minorDesc>" +"--"+ "</minorDesc>";
			                    }
		                     xml=xml+"<checkcode>" +checkcode+ "</checkcode>";
		                     xml=xml+"<checkdesc>" +checkDesc+ "</checkdesc>";
		                     xml=xml+"<mandate>" +mand+ "</mandate>"; 
		                     xml=xml+"<notapply>" +apply+ "</notapply>";
		                     System.out.println("up::"+xml);
	                 }
	                 else
	                	 xml=xml+"<flag>failure</flag>"; 
	                 
	                 
	             }
	             catch(Exception e)
	             {
	                  System.out.println("exception in update is"+e);
	                  xml=xml+"<flag>failure</flag>";
	             }
	        	 
	        	 xml=xml+"</response>";      
	         }
	         else if(cmd.equalsIgnoreCase("deleted"))
	         {
	             xml="<response><command>deleted</command>";
	             checkcode= Integer.parseInt(request.getParameter("checkcode"));
		             try
		             {
	                
		                 ps = con.prepareStatement("delete from FAS_BILL_SCRUTINY_CHECKLIST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CHECK_CODE=? ");
		                 ps.setInt(1,unitid);
		                 ps.setInt(2,offid);
		                 ps.setInt(3,checkcode);
		                 int ii=ps.executeUpdate();
		                 if(ii>=1)
		                 {
		                	 xml=xml+"<flag>success</flag>";
		                 }
		                 else
		                	 xml=xml+"<flag>failure</flag>"; 
	                 
	                 }
	                 catch(SQLException e) {
	                     
	                      System.err.println("error on delete function" + e.getMessage());
	                     xml=xml+"<flag>failure</flag>";
	                 }

	             xml=xml+"</response>";
	         } 
	        	 
	         else if(cmd.equalsIgnoreCase("Get"))
	         { 
	        	 System.out.println("gettt");
		         xml="<response><command>Get</command>";
		             try 
		             {
		            	 System.out.println("inside gettt");
		            	// ps = con.prepareStatement("select CHECK_CODE,CHECK_DESC,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,MANDATE,NOT_APPLICABLE from FAS_BILL_SCRUTINY_CHECKLIST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");
		            	 ps = con.prepareStatement("select CHECK_CODE,CHECK_DESC,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,MANDATE,NOT_APPLICABLE from FAS_BILL_SCRUTINY_CHECKLIST order by CHECK_CODE");
//		            	 ps.setInt(1,unitid);
//		                 ps.setInt(2,offid);
		            	 result = ps.executeQuery();                                
	                     while(result.next()) 
	                     {
	                    	
	                    	 xml=xml+"<flag>success</flag>";
	                    	 ps1=con.prepareStatement("select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where BILL_MAJOR_TYPE_CODE="+result.getInt("BILL_MAJOR_TYPE_CODE"));
	 	                     result1=ps1.executeQuery();
	 	                    System.out.println("result1");
	 	                    if(result1.next()){
	 	                    	xml=xml+"<majorCode>" +result1.getInt("BILL_MAJOR_TYPE_CODE")+ "</majorCode>";
		                    	xml=xml+"<majorDesc>" +result1.getString("BILL_MAJOR_TYPE_DESC")+ "</majorDesc>";
	 	                    }
		                    else
		                    	xml=xml+"<majorDesc>" +"--"+ "</majorDesc>";
		                    ps2 = con.prepareStatement("select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE="+result.getInt("BILL_MAJOR_TYPE_CODE")+" and BILL_MINOR_TYPE_CODE="+result.getInt("BILL_MINOR_TYPE_CODE"));
	                        result2 = ps2.executeQuery();
	                        System.out.println("result2");
	                        if(result2.next()){
	                        	xml=xml+"<minorCode>" +result2.getInt("BILL_MINOR_TYPE_CODE")+ "</minorCode>";
		                    	xml=xml+"<minorDesc>" +result2.getString("BILL_MINOR_TYPE_DESC")+ "</minorDesc>";
	                        }
		                    else
		                    	xml=xml+"<minorDesc>" +"--"+ "</minorDesc>";
	                    	 System.out.println("after>>>>>>>>>>");
	                    	 xml=xml+"<checkcode>" +result.getInt("CHECK_CODE")+ "</checkcode>";
	                         xml=xml+"<checkdesc>" +result.getString("CHECK_DESC")+ "</checkdesc>";
	                         xml=xml+"<mandate>" +result.getString("MANDATE")+ "</mandate>"; 
	                         xml=xml+"<notapply>" +result.getString("NOT_APPLICABLE")+ "</notapply>";
	                         count++;
	                         System.out.println("count:::"+count);
	                     }
		             }
		             catch(Exception e1){
		                        System.out.println("Exception is in Get"+e1);
		                 		xml=xml+"<flag>failure</flag>";
		             }
		         xml=xml+"</response>";
	         }
	         else if(cmd.equalsIgnoreCase("loadGrid"))
	         {
	        	 int cbmonth=0,cbyear=0;
	        	 try{cbyear= Integer.parseInt(request.getParameter("cbyear"));} 
		         catch(Exception e1){System.out.println("Err in getting cbyear "+e1.getMessage()); }
		         System.out.println("cbyear"+cbyear);
		         try
		         {cbmonth= Integer.parseInt(request.getParameter("cbmonth")); }
		         catch(Exception e2){ System.out.println("Err in getting cbmonth "+e2.getMessage()); }
		         System.out.println("cbmonth"+cbmonth); 
		         String sub_q = "",sub_main="";
					/*if(cbyear>2014 && cbmonth>3)
					{
						 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
						 sub_main=" Fas_Bill_Register_MasterNEW reg, "+
						" 	  Fas_Bill_Register_Transactionw trn ";
					}else{
						sub_q = " FAS_BILL_REGISTER_MASTER "; 
						 sub_main=" Fas_Bill_Register_Master reg, "+
									" 	  Fas_Bill_Register_Transaction trn ";
					}*/
		         
		     	if (cbyear > 2014) {
					if (cbyear == 2015 && cbmonth <= 3) {
						sub_q = " FAS_BILL_REGISTER_MASTER "; 
						 sub_main=" Fas_Bill_Register_Master reg, "+
									" 	  Fas_Bill_Register_Transaction trn ";
					} else {
						 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
						 sub_main=" Fas_Bill_Register_MasterNEW reg, "+
						" 	  Fas_Bill_Register_Transactionw trn ";
					}
				} else {
					sub_q = " FAS_BILL_REGISTER_MASTER "; 
					 sub_main=" Fas_Bill_Register_Master reg, "+
								" 	  Fas_Bill_Register_Transaction trn ";
				}
	             xml="<response><command>loadGrid</command>"; 
	              try 
	                      {
	                           String qu="select * from ((select BILL_NO,BILL_MAJOR_TYPE, "+
										" BILL_MAJOR_TYPE_DESC, "+
	                        	   "   BILL_MINOR_TYPE_CODE, "+
	                        	   " 	  BILL_MINOR_TYPE_DESC, "+
	                        	   " 	  BILL_SUB_TYPE_CODE, "+
	                        	   " 	  BILL_SUB_TYPE_DESC, "+
	                        	   " 	  to_char(BILL_DATE,'dd/mm/yyyy')as BILL_DATE, "+
	                        	   " 	    max(BILL_DATE) as max_date,sum(amount)as amount, "+
	                        	   " 	  PAYEE_CODE, "+
	                        	   " 	  to_char(BILL_SCRUTINY_DATE,'dd/mm/yyyy')as BILL_SCRUTINY_DATE, "+
	                        	   " 	  Remarks , "+
	                        	   " 	  SANCTION_PROC_NO, "+
	                        	   " 	  to_char(PROCEEDING_RECD_DATE,'dd/mm/yyyy')as PROCEEDING_RECD_DATE "+
	                        	   " 	from( "+
	                           		"SELECT reg.BILL_NO, "+
	                            		  "  reg.BILL_MAJOR_TYPE,major1.BILL_MAJOR_TYPE_DESC,reg.BILL_MINOR_TYPE_CODE, "+
	                            		  " minor.BILL_MINOR_TYPE_DESC,reg.BILL_SUB_TYPE_CODE, "+
	                            		  " sub.BILL_SUB_TYPE_DESC,reg.BILL_DATE, "+
	                            		  " trn.amount,trn.ACCOUNT_HEAD_CODE, "+
	                            		  " head.ACCOUNT_HEAD_DESC,reg.PAYEE_CODE, "+
	                            		  " reg.BILL_SCRUTINY_DATE,Reg.Remarks ,reg.SANCTION_PROC_NO,reg.PROCEEDING_RECD_DATE "+
	                            		  " FROM "+sub_main+","+
	                            		//  + " FAS_BILL_REGISTER_MASTER reg,FAS_BILL_REGISTER_TRANSACTION trn, "+
	                            		  " FAS_BILL_MAJOR_TYPES major1, "+
	                            		  " FAS_BILL_MINOR_TYPES_MST minor, "+
	                            		  " FAS_BILL_SUB_TYPES sub, "+
	                            		  " Com_Mst_Account_Heads Head "+
	                            		  " Where Reg.Accounting_Unit_Id     =Trn.Accounting_Unit_Id "+
	                            		  " And Reg.Accounting_Unit_Office_Id     =Trn.Accounting_Unit_Office_Id "+
	                            		  " And Reg.Cashbook_Year     =Trn.Cashbook_Year "+
	                            		  " And Reg.Cashbook_Month     =Trn.Cashbook_Month "+
	                            		  " And Reg.BILL_NO     =Trn.BILL_NO "+
	                            		  " and Reg.Accounting_Unit_Id     =? AND reg.accounting_unit_office_id=?" +
	                            		  " and reg.CASHBOOK_YEAR=? and REG.CASHBOOK_MONTH=? "+
	                            		  "  and reg.BILL_SCRUTINY_DONE='Y'" +
	                            		  " and reg.MEMO_ENTRY='Y' and reg.status='L' and PASS_ORDER_DATE is null "+
	                            		  " AND reg.BILL_MAJOR_TYPE          =major1.BILL_MAJOR_TYPE_CODE "+
	                            		  " AND reg.BILL_MAJOR_TYPE          =minor.BILL_MAJOR_TYPE_CODE "+
	                            		  " AND reg.BILL_MINOR_TYPE_CODE     =minor.BILL_MINOR_TYPE_CODE "+
	                            		  " AND reg.BILL_MAJOR_TYPE          =sub.BILL_MAJOR_TYPE_CODE "+
	                            		  " AND reg.BILL_MINOR_TYPE_CODE     =sub.BILL_MINOR_TYPE_CODE "+
	                            		  " And Reg.Bill_Sub_Type_Code       =Sub.Bill_Sub_Type_Code "+
	                              		  " AND trn.ACCOUNT_HEAD_CODE        =head.ACCOUNT_HEAD_CODE) as opt4 " +
	                              		  "group by BILL_NO,BILL_MAJOR_TYPE, "+
											" BILL_MAJOR_TYPE_DESC,BILL_MINOR_TYPE_CODE, "+
	                              		"   BILL_MINOR_TYPE_DESC,BILL_SUB_TYPE_CODE, "+
	                              		"   BILL_SUB_TYPE_DESC,BILL_DATE, "+
	                              		"   PAYEE_CODE,BILL_SCRUTINY_DATE, "+
	                              		"   Remarks ,SANCTION_PROC_NO, "+
	                              		"   PROCEEDING_RECD_DATE ) "+
	                              		
	                              		" UNION ALL "+
	                              		"  (SELECT BILL_NO,  " +
	                              		"  BILL_MAJOR_TYPE,  " +
	                              		" BILL_MAJOR_TYPE_DESC,  " +
	                              		" BILL_MINOR_TYPE_CODE,  " +
	                              		" BILL_MINOR_TYPE_DESC,  " +
	                              		"  BILL_SUB_TYPE_CODE,  " +
	                              		" BILL_SUB_TYPE_DESC,  " +
	                              		"  TO_CHAR(BILL_DATE,'dd/mm/yyyy')AS BILL_DATE,  " +
	                              		"   max(BILL_DATE) as max_date,SUM(amount)                    AS amount,  " +
	                              		"  PAYEE_CODE,  " +
	                              		"  TO_CHAR(BILL_SCRUTINY_DATE,'dd/mm/yyyy')AS BILL_SCRUTINY_DATE,  " +
	                              		"  Remarks ,  " +
	                              		"  (SANCTION_PROC_NO) as SANCTION_PROC_NO,  " +
	                              		" TO_CHAR(PROCEEDING_RECD_DATE,'dd/mm/yyyy')AS PROCEEDING_RECD_DATE  " +
	                              		"  FROM  " +
	                              		"  (SELECT reg.BILL_NO,  " +
	                              		"   reg.BILL_MAJOR_TYPE,  " +
	                              		"   major1.BILL_MAJOR_TYPE_DESC,  " +
	                              		"  reg.BILL_MINOR_TYPE_CODE,  " +
	                              		"  minor.BILL_MINOR_TYPE_DESC,  " +
	                              		"   reg.BILL_SUB_TYPE_CODE,  " +
	                              		"  sub.BILL_SUB_TYPE_DESC,  " +
	                              		"  reg.BILL_DATE,  " +
	                              		" reg.TOTAL_BILL_AMOUNT AS AMOUNT,  " +
	                              		" reg.ACCOUNT_HEAD_CODE,  " +
	                              		"  head.ACCOUNT_HEAD_DESC,  " +
	                              		"  reg.PAYEE_CODE,  " +
	                              		"  reg.BILL_SCRUTINY_DATE,  " +
	                              		"  Reg.Remarks ,  " +
	                              		"  reg.SANCTION_PROC_NO,  " +  
	                              		"   reg.PROCEEDING_RECD_DATE  " +
	                              		" FROM FAS_BILL_REGISTERNEW reg,  " +
	                              		"   FAS_BILL_MAJOR_TYPES major1,  " +
	                              		"   FAS_BILL_MINOR_TYPES_MST minor,  " +
	                              		"   FAS_BILL_SUB_TYPES sub,  " +
	                              		"   Com_Mst_Account_Heads Head  " +  
	                              		"  WHERE Reg.Accounting_Unit_Id      =?  " +
	                              	  " AND reg.accounting_unit_office_id =?  " +
	                              		"  AND reg.CASHBOOK_YEAR             =?  " +
	                              		"  AND REG.CASHBOOK_MONTH            =?  " +
	                              		"  AND reg.BILL_SCRUTINY_DONE        ='Y' and reg.STATUS='L' " +
	                              		"  AND reg.MEMO_ENTRY                ='Y'  " +
	                              		"  AND PASS_ORDER_DATE              IS NULL  " +
	                              		"  AND reg.BILL_MAJOR_TYPE           =major1.BILL_MAJOR_TYPE_CODE  " +
	                              		" AND reg.BILL_MAJOR_TYPE           =minor.BILL_MAJOR_TYPE_CODE  " +
	                              		" AND reg.BILL_MINOR_TYPE_CODE      =minor.BILL_MINOR_TYPE_CODE  " +
	                              		"  AND reg.BILL_MAJOR_TYPE           =sub.BILL_MAJOR_TYPE_CODE  " +
	                              		"  AND reg.BILL_MINOR_TYPE_CODE      =sub.BILL_MINOR_TYPE_CODE  " +
	                              		"  AND Reg.Bill_Sub_Type_Code        =sub.Bill_Sub_Type_Code  " +
	                              		"  AND reg.ACCOUNT_HEAD_CODE         =head.ACCOUNT_HEAD_CODE  " +
	                              		"  ) as opt1 " +
	                              		"  GROUP BY BILL_NO,  " +
	                              		"    BILL_MAJOR_TYPE,  " +
	                              		"    BILL_MAJOR_TYPE_DESC,  " +
	                              		"   BILL_MINOR_TYPE_CODE,  " +
	                              		"  BILL_MINOR_TYPE_DESC,  " +
	                              		"  BILL_SUB_TYPE_CODE,  " +
	                              		"  BILL_SUB_TYPE_DESC,  " +
	                              	  "  BILL_DATE,  " +
	                              		"  PAYEE_CODE,  " +
	                              		"  BILL_SCRUTINY_DATE,  " +
	                              		"   Remarks ,  " +
	                              		"   SANCTION_PROC_NO,  " +
	                              		"   PROCEEDING_RECD_DATE  " +
	                              		"  ) )as opt3  order by BILL_NO "  ;
	                              System.out.println(qu);
	                              ps = con.prepareStatement(qu);
	                              ps.setInt(1,unitid);
	     		                  ps.setInt(2,offid);
	     		                  ps.setInt(3,cbyear);
	     		                  ps.setInt(4,cbmonth);
	     		                 ps.setInt(5,unitid);
	     		                  ps.setInt(6,offid);
	     		                  ps.setInt(7,cbyear);
	     		                  ps.setInt(8,cbmonth);
	                             result = ps.executeQuery();      
	                            // System.out.println("result>>>"+result);
	                              while(result.next()) 
	                              {
	                            	// BigDecimal bam=new BigDecimal(0.00);
	                            		//bam=new BigDecimal(result.getFloat("AMOUNT"));
	                            //	System.out.println("::::amt :"+result.get("AMOUNT"));  
	                            	  xml=xml+"<display>"; 
	                            	  xml=xml+"<billno>"+result.getInt("BILL_NO")+"</billno>";
	                                  xml=xml+"<majorcode>"+result.getInt("BILL_MAJOR_TYPE")+"</majorcode>";
	                                  xml=xml+"<majordesc>"+result.getString("BILL_MAJOR_TYPE_DESC")+"</majordesc>";
	                                  xml=xml+"<minorcode>"+result.getInt("BILL_MINOR_TYPE_CODE")+"</minorcode>";
	                                  xml=xml+"<minordesc>"+result.getString("BILL_MINOR_TYPE_DESC")+"</minordesc>";
	                                  xml=xml+"<subcode>"+result.getInt("BILL_SUB_TYPE_CODE")+"</subcode>";
	                                  xml=xml+"<subdesc>"+result.getString("BILL_SUB_TYPE_DESC")+"</subdesc>";
	                                  xml=xml+"<billdate>"+result.getString("BILL_DATE")+"</billdate>";
	                                  xml=xml+"<billamount>"+result.getInt("AMOUNT")+"</billamount>";
	                                 // xml=xml+"<accCode>"+result.getInt("ACCOUNT_HEAD_CODE")+"--"+result.getString("ACCOUNT_HEAD_DESC")+"</accCode>";
	                                  xml=xml+"<payto>"+result.getString("PAYEE_CODE")+"</payto>";
	                                  xml=xml+"<scrdate>"+result.getString("BILL_SCRUTINY_DATE")+"</scrdate>";
	                                  xml=xml+"<rem>"+result.getString("REMARKS")+"</rem>";
	                                  xml=xml+"<SANCTION_PROC_NO>"+result.getString("SANCTION_PROC_NO")+"</SANCTION_PROC_NO>";
	                                  xml=xml+"<PROCEEDING_RECD_DATE>"+result.getString("PROCEEDING_RECD_DATE")+"</PROCEEDING_RECD_DATE>";
	                                  
	                                  
	                                  xml=xml+"</display>";
	                                  count++;
	                              }
	                              if(count>0)
	                                  xml=xml+"<flag>success</flag>";
	                              else
	                                  xml=xml+"<flag>failure</flag>";
	                      }
	                catch(Exception e) 
	                      {
	                              System.out.println("Exception in loadGrid ===> "+e);   
	                              xml=xml+"<flag>failure</flag>";  
	                      }
	                  xml=xml+"</response>";
	          }
	         
	         
	         System.out.println("xml::::"+xml);
	         out.println(xml);
	         out.close();
		
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
        {
	   
	            
	    PrintWriter out = response.getWriter();
	    Connection con=null;
	    PreparedStatement ps,ps1=null,ps2=null,ps3=null,ps4=null;
	    Statement st=null;
	    ResultSet result=null,result1=null,result2=null,rs1=null;
	    int eid=0,unitid=0,offid=0,major=0,count=0,minor=0,checkcode=0,billnumber=0;
	    int cbyear=0;int cbmonth=0;int PassOrderPreparedBye=0;int TotalAmt=0;
            String cmd,checkDesc="",apply="",mand="",remark="";
	    int txtCash_year=0,txtCash_Month_hid=0;
	    cmd=request.getParameter("command");
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
	              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	              try
	              {
	                    st=con.createStatement();
	                    con.clearWarnings();
	              }
	              catch(SQLException e)
	              {
	                    System.out.println("Exception in creating statement:"+e);
	              }          
	    }
	    catch(Exception e)
	    {
	               System.out.println("Exception in openeing connection:"+e);
	    }
	      
	    HttpSession session=request.getSession(false);
	    String update_user=(String)session.getAttribute("UserId");
	    UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
	    eid=empProfile.getEmployeeId();
	    System.out.println("employee id:"+eid);
	     try
	     {
	        
	            if(session==null)
	            {
	                System.out.println(request.getContextPath()+"/index.jsp");
	                response.sendRedirect(request.getContextPath()+"/index.jsp");                   
	            }
	            System.out.println(session);
	            
	    }catch(Exception e)
	    {
	    System.out.println("Redirect Error :"+e);
	    }
	     String userid=(String)session.getAttribute("UserId");
	     System.out.println("session id is:"+userid);
	     long l=System.currentTimeMillis();
	     Timestamp ts=new Timestamp(l);
             
	    try{unitid= Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));} 
	    catch(Exception e1){System.out.println("Err in getting unit code**** "+e1.getMessage()); }
	    System.out.println("unitid::"+unitid);
	    try
	    {offid= Integer.parseInt(request.getParameter("cmbOffice_code")); }
	    catch(Exception e2){ System.out.println("Err in getting offid "+e2.getMessage()); }
	    System.out.println("offid::"+offid);
	    try{cbyear= Integer.parseInt(request.getParameter("cbyear"));} 
	    catch(Exception e1){System.out.println("Err in getting Cash Book Year**** "+e1.getMessage()); }
	    System.out.println("cbyear::"+cbyear);
	    try
	    {cbmonth= Integer.parseInt(request.getParameter("cbmonth")); }
	    catch(Exception e2){ System.out.println("Err in getting cash book Month "+e2.getMessage()); }
	    System.out.println("cbmonth::"+cbmonth);
	    
	   
	    String checkNo[]=null; 
	    

	    String tnodebillno[]=null; 
	    String major1[]=null; 
	    String minor1[]=null; 
	    String sub1[]=null; 
	    String billAmt[]=null; 
	    String tnodehead[]=null; 
	    String tpayable[]=null; 
	    String scrDate[]=null; 
	    String tremarks[]=null; 
	    String sanNo[]=null; 
	    String sanDate[]=null; 
	    String billdatt[]=null;
	    java.sql.Date PassOrderDate = null;
		java.util.GregorianCalendar c3;
		String[] sd3 = request.getParameter("txtCrea_date").split("/");
		c3 = new java.util.GregorianCalendar(Integer.parseInt(sd3[2]),
				Integer.parseInt(sd3[1]) - 1, Integer.parseInt(sd3[0]));
		java.util.Date d3 = c3.getTime();
		PassOrderDate = new Date(d3.getTime());
	    
		  try{txtCash_year=Integer.parseInt(sd3[2]);}
          catch(Exception e){System.out.println("exception"+e );}
        //  System.out.println("txtCash_year "+txtCash_year);
          
          try{txtCash_Month_hid=Integer.parseInt(sd3[1]);}
          catch(Exception e){System.out.println("exception"+e );}
		
	    try
	    {PassOrderPreparedBye= Integer.parseInt(request.getParameter("txtPass_order_preparedByEmpcode")); }
	    catch(Exception e2){ System.out.println("Err in getting Pass Order Prepared By"+e2.getMessage()); }
	    System.out.println("PassOrderPreparedBye::"+PassOrderPreparedBye);
	    try
	    {
	    	TotalAmt= Integer.parseInt(request.getParameter("txtTotalAmt"));
	    	remark=request.getParameter("txtRemarks");
	    
	    }
	    catch(Exception e2){ System.out.println("Err in getting Pass Order Prepared amount"+e2.getMessage()); }
	    System.out.println("Total Amount::"+TotalAmt);
	    String sub_q = "",sub_main="";
		/*if(cbyear>2014 && cbmonth>3)
		{
			 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
			 sub_main=" Fas_Bill_Register_MasterNEW M, "+
			" 	  Fas_Bill_Register_Transactionw T ";
		}else{
			sub_q = " FAS_BILL_REGISTER_MASTER "; 
			 sub_main=" Fas_Bill_Register_Master M, "+
						" 	  Fas_Bill_Register_Transaction T ";
		}*/
	    
	    if (cbyear > 2014) {
			if (cbyear == 2015 && cbmonth <= 3) {
				sub_q = " FAS_BILL_REGISTER_MASTER "; 
				 sub_main=" Fas_Bill_Register_Master M, "+
							" 	  Fas_Bill_Register_Transaction T ";
			} else {
				 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
				 sub_main=" Fas_Bill_Register_MasterNEW M, "+
				" 	  Fas_Bill_Register_Transactionw T ";
			}
		} else {
			sub_q = " FAS_BILL_REGISTER_MASTER "; 
			 sub_main=" Fas_Bill_Register_Master M, "+
						" 	  Fas_Bill_Register_Transaction T ";
		}
	   // checkNo = request.getParameterValues("checkNo"); 
	   checkNo = request.getParameterValues("verify_select"); 
	    
	    tnodebillno = request.getParameterValues("tnodebillno"); 
	    major1 = request.getParameterValues("major1"); 
	    minor1 = request.getParameterValues("minor1"); 
	    sub1 = request.getParameterValues("sub1"); 
	    billAmt = request.getParameterValues("billAmt"); 
	    tnodehead = request.getParameterValues("tnodehead"); 
	    tpayable = request.getParameterValues("tpayable"); 
	    scrDate = request.getParameterValues("scrDate"); 
	    tremarks = request.getParameterValues("tremarks"); 
	   billdatt=request.getParameterValues("billdatee");
	    sanNo = request.getParameterValues("SANCTION_PROC_NO1"); 
	    sanDate = request.getParameterValues("PROCEEDING_RECD_DATE1"); 
	  
	    
	  String verify_select[]=request.getParameterValues("verify_select");
        
       String verify_select_status[]=request.getParameterValues("verify_select_status");
        
	    //System.out.println("checkNo>>>"+checkNo.length);
	    String[] tnodebillno1=request.getParameterValues("tnodebillno");
		
	    
	    
	    
	     cmd=request.getParameter("command");
	     int i2 = 1, i3 = 0;
            if(cmd.equalsIgnoreCase("AddPassorder"))
            {
                try {
                	con.clearWarnings();
        			con.setAutoCommit(false);
        			int sd1=0;
        			  int Pass_or_no=0;
     	             int fin_year_from=0,fin_year_to=0;
     	             
     	             //////////////////////Financial year calculation/////////////////////////////////
     	             if(cbmonth>3)
     	             {
     	            	 	  fin_year_from=cbyear;
     	            	 	  fin_year_to=cbyear+1;
     	             }
     	             else
     	             {
     	            	 	  fin_year_from=cbyear-1;
     	            	 	  fin_year_to=cbyear;
     	             }
     	             System.out.println("fin_year_from ::: "+fin_year_from+"  fin_year_to:::"+fin_year_to);
     	             try
     	             {
     	            	 	String  sql="SELECT max(PASS_ORDER_NO) as count FROM FAS_PASS_ORDER_MST where " +
     	            	 			"ACCOUNTING_UNIT_ID= " +unitid+" and ACCOUNTING_FOR_OFFICE_ID="+offid+" and " +
     	            	 					"CASHBOOK_MONTH="+txtCash_Month_hid+" and CASHBOOK_YEAR="+txtCash_year;	
     	                      ps=con.prepareStatement(sql);
     	                      
     	            	 	 ResultSet rs=ps.executeQuery();
     	                      if(rs.next()) 
     	                      {
     	                    	 Pass_or_no = rs.getInt("count");                                               
     	                      }
     	                     Pass_or_no=Pass_or_no+1;
     	                      //rs.close();
     	             }           	    
     	             catch(Exception e)
     	             { 
     	            	 System.out.println("exception"+e );
     	             }
     	            System.out.println("Pass_or_no"+Pass_or_no );
     	          
					ps3 = con.prepareStatement("insert into  FAS_PASS_ORDER_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,PASS_ORDER_NO,PASS_ORDER_DATE,PASS_ORDER_PREPARED_BY,REMARKS,STATUS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?)");
					ps3.setInt(1, unitid);
    				ps3.setInt(2, offid);
    				ps3.setInt(3, txtCash_year);
    				ps3.setInt(4, txtCash_Month_hid);
    				ps3.setInt(5, Pass_or_no);
    				ps3.setDate(6, PassOrderDate);
    				ps3.setInt(7, PassOrderPreparedBye);
    				ps3.setString(8, remark);	
    				ps3.setString(9, "L");
    				   System.out.println("userid"+userid );
    				ps3.setString(10, userid);
    				ps3.setTimestamp(11, ts);
    				   System.out.println(sd1+" <<< PassOrderPreparedBye"+PassOrderPreparedBye );
    				 sd1=ps3.executeUpdate();
    				System.out.println("sd1111111   "+sd1);
    				  int ched=0,unched=0;
    				  int tot=tnodebillno1.length;
    				  System.out.println("toot  "+tot);
    				  int SL_NO=0;
                	for(int i=0;i<tnodebillno1.length;i++)
            		{
                		 if((verify_select_status[i].equals("CHECKED")))
                         {
                        	int billnumber1=Integer.parseInt(tnodebillno[i]); 
                        	System.out.println("billnumber1 "+billnumber1);
            		int tnodebillno2 =Integer.parseInt(tnodebillno[i]);
            	    int major2 = Integer.parseInt(major1[i]); 
            	    int minor2= Integer.parseInt(minor1[i]); 
            	    int sub2 = Integer.parseInt(sub1[i]);
            	    double billAmt2 = Double.parseDouble(billAmt[i]); 
            	 
            	    String tpayable2 = tpayable[i]; 
            	    String scrDate2= scrDate[i]; 
            	     String tremarks2 = tremarks[i]; 
            	     Date billdate1,sandate1;
            	     Calendar c;
                        int sannno=Integer.parseInt(sanNo[i].split("/")[0]);
                        String sanndate=sanDate[i];
                         String billda=billdatt[i];
                         System.out.println("billda ---- "+billda);
                         System.out.println("sannno ---- "+sannno);
                       System.out.println("sanndate  -->"+ sanndate);
                                            
                       SL_NO++;
                         	ps4 = con.prepareStatement("insert into  FAS_PASS_ORDER_TRN(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,PASS_ORDER_NO,SANCTION_PROC_NO,BILL_NO,BILL_DATE,BILL_AMOUNT,PAYABLE_TO,REMARKS,UPDATED_BY_USERID,UPDATED_DATE,SANCTION_PROC_DATE,SLNO) values(?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?)");
        					ps4.setInt(1, unitid);
            				ps4.setInt(2, offid);
            				
            				ps4.setInt(3, txtCash_year);
            				ps4.setInt(4, txtCash_Month_hid);
            				ps4.setInt(5, Pass_or_no);
            				ps4.setInt(6, sannno);
            		
            				ps4.setInt(7, tnodebillno2);	
            				ps4.setString(8, billda);
            				ps4.setDouble(9, billAmt2);
            				ps4.setString(10, tpayable2 );
            				ps4.setString(11, tremarks2);
            				ps4.setString(12, userid);
            				ps4.setTimestamp(13, ts);  
            				ps4.setString(14, sanndate);
            				ps4.setInt(15,SL_NO);
            				
            				int sd2=ps4.executeUpdate();
            				
                         	System.out.println("sd22 "+sd2);
                         	                         	
		    				ps2 = con.prepareStatement("update "+sub_q+" set PASS_ORDER_DATE=?,PASS_ORDER_BY=?,PASS_ORDER_AMOUNT=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=? and BILL_SCRUTINY_DONE='Y' and MEMO_ENTRY='Y' and status='L' ");
		    				ps2.setDate(1, PassOrderDate);
		    				ps2.setInt(2, PassOrderPreparedBye);
		    				ps2.setInt(3, TotalAmt);	
		    				ps2.setInt(4, unitid);
		    				ps2.setInt(5, offid);
		    				ps2.setInt(6, cbyear);
		    				ps2.setInt(7, cbmonth);
		    				System.out.println("billnumber1 >>> "+billnumber1);
		    				ps2.setInt(8, billnumber1);				
		    				int sd=ps2.executeUpdate();
		    				if(sd==0)
		    				{
		    					ps2 = con.prepareStatement("update FAS_BILL_REGISTERNEW set PASS_ORDER_DATE=?,PASS_ORDER_BY=?,PASS_ORDER_AMOUNT=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=? and BILL_SCRUTINY_DONE='Y' and MEMO_ENTRY='Y' and status='L' ");
			    				ps2.setDate(1, PassOrderDate);
			    				ps2.setInt(2, PassOrderPreparedBye);
			    				ps2.setInt(3, TotalAmt);	
			    				ps2.setInt(4, unitid);
			    				ps2.setInt(5, offid);
			    				ps2.setInt(6, cbyear);
			    				ps2.setInt(7, cbmonth);
			    				ps2.setInt(8, billnumber1);				
			    				int sd22=ps2.executeUpdate();	
		    				}
		    				System.out.println(" sd "+sd);
		    				
		    				   ched++;
                      }else{
                     	  System.out.println("count of unched ");
                     	   unched++;  
                        }
            		}
                	 int fina=tot-ched;
                      System.out.println(" fina "+fina+" tot"+tot+" ched "+ched +" unched "+unched);
                      if(fina==unched){
                     // if(up>0){
                   	  // System.out.println("inside if ");
                   	   con.commit();
                   	sendMessage(response,"PassOrder Preparation is Prepared Successfully","ok");   
                      }else{
                   	   con.rollback();
                   	System.out.println("failed");      
                   	 sendMessage(response," Cannot Update","ok"); 
                      }
                      
    			} catch (Exception e) {
    				
    				e.printStackTrace();
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
         catch(Exception e)
         {
                 System.out.println("error in messenger"+e);
         }
     }
}
