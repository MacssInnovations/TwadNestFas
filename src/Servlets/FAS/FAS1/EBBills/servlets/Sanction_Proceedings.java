package Servlets.FAS.FAS1.EBBills.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class Sanction_Proceedings
 */
public class Sanction_Proceedings extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";         
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sanction_Proceedings() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 Connection con=null;
	   	 
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
		      System.out.println("Exception in connection...."+e);
		    }
			        ResultSet rs=null,rs1=null,rs2=null,rs4=null;
			        CallableStatement cs=null;
			        PreparedStatement ps=null,ps1=null,ps2=null;
			        String xml="";
			   	   
			        response.setContentType(CONTENT_TYPE);
			        PrintWriter out = response.getWriter();
			        response.setHeader("Cache-Control","no-cache");  
			        HttpSession session=request.getSession(false);
			        try
			        {
			                if(session==null)
			                {
			                        xml="<response><command>sessionout</command><flag>sessionout</flag></response>";
			                        out.println(xml);
			                        System.out.println(xml);
			                        out.close();
			                        return;

			                    }
			                    //System.out.println(session);

			        }catch(Exception e)
			        {
			                //System.out.println("Redirect Error :"+e);
			        }
			        System.out.println("java");
			        String command;
			        command = request.getParameter("command");
			        
			        session =request.getSession(false);
			        String updatedby=(String)session.getAttribute("UserId");
			        long l=System.currentTimeMillis();
			        java.sql.Timestamp ts=new java.sql.Timestamp(l);
			            System.out.println("got");
			            System.out.println("command"+command);
			            if(command.equalsIgnoreCase("add")) {
			            	 xml="<response><command>Add</command>";	           
				         int acUnit = Integer.parseInt(request.getParameter("unitid").trim());
				         int officeId=Integer.parseInt(request.getParameter("officeid").trim());
				         int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
				         int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
				         int majorType=Integer.parseInt(request.getParameter("majortype").trim());
				         int MinorType=Integer.parseInt(request.getParameter("minortype").trim());
				         int subType=Integer.parseInt(request.getParameter("subtype").trim());
				         int payeeCode=Integer.parseInt(request.getParameter("empid").trim());
				         int refNo=Integer.parseInt(request.getParameter("refno").trim());
				         String refDate=request.getParameter("refdate");
				         String proDate=request.getParameter("prodate");
				         int sanctionAuthority=Integer.parseInt(request.getParameter("sanctionauthority").trim());
				         int sanctionedBy=Integer.parseInt(request.getParameter("sanctionedby").trim());
				         int acHead=Integer.parseInt(request.getParameter("achead").trim());
				        
				         int paymentUnitId=Integer.parseInt(request.getParameter("paymentunit").trim());
				         int totalInstallment=Integer.parseInt(request.getParameter("totalinstallment").trim());
				         int recoveryMonth=Integer.parseInt(request.getParameter("recoverymonth").trim());
				         float residualAmount=Float.parseFloat(request.getParameter("residualamount").trim());
				         int installment=Integer.parseInt(request.getParameter("installment").trim());
				         float totalAmount=Float.parseFloat(request.getParameter("totalamount").trim());
				         float eMi=Float.parseFloat(request.getParameter("emi").trim());
				         String payeeType=request.getParameter("payeetype");
				         String recovery=request.getParameter("recovery");
				         String remarks=request.getParameter("remarks");	           
				         String payment=request.getParameter("payment");
				         String[] cashMonthYear=proDate.split("/");
				            cashMonth=	Integer.parseInt(cashMonthYear[1]);
				            cashYear=	Integer.parseInt(cashMonthYear[2]);
				            try{
		               	              		                  		                          
			                    xml=xml+"<flag>success</flag>";
			                    
			                   // to_date(?,'dd-mm-yyyy')
			                 //   String columns="ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,SANCTION_PROCEEDING_DATE,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,PAYEE_TYPE,PAYEE_CODE,REF_NO,REF_DATE,SANCTION_AUTHORITY,SANCTIONED_BY,ACCOUNT_HEAD_CODE,TOTAL_SANCTION_AMOUNT,PAYMENT_TO_BE_MADE_UNIT_ID,RECOVERY, \n+" +
			                    	//	"RECOVERY_START_MONTH,RESIDUAL_AMOUNT,RESIDUAL_INSTL_NO,REMARKS,STATUS,UPDATED_BY_USERID,UPDATED_DATE";
			                    ps=con.prepareStatement("insert into FAS_SANC_PROC_MST1(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,SANCTION_PROCEEDING_DATE,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,PAYEE_TYPE,PAYEE_CODE,REF_NO,REF_DATE,SANCTION_AUTHORITY,SANCTIONED_BY,ACCOUNT_HEAD_CODE,TOTAL_SANCTION_AMOUNT,PAYMENT_TO_BE_MADE_UNIT_ID,RECOVERY,RECOVERY_START_MONTH,RESIDUAL_AMOUNT,RESIDUAL_INSTL_NO,REMARKS,STATUS,UPDATED_BY_USERID,UPDATED_DATE,PAYMENT,TOTAL_INSTALLMENT,EMI) values(?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				                ps.setInt(1,acUnit);
				                ps.setInt(2,officeId);
				                ps.setInt(3,cashYear);
			                    ps.setInt(4,cashMonth);
			                    ps.setString(5,proDate);
			                    ps.setInt(6,majorType);
			                    ps.setInt(7,MinorType);
			                    ps.setInt(8,subType);
			                    ps.setString(9,payeeType);
			                    ps.setInt(10,payeeCode);
			                    ps.setInt(11,refNo);
			                    ps.setString(12,refDate);
			                    ps.setInt(13,sanctionAuthority);
			                    ps.setInt(14,sanctionedBy);
			                    ps.setInt(15,acHead);
			                    ps.setFloat(16,totalAmount);
			                    ps.setInt(17,paymentUnitId);
			                    ps.setString(18,recovery);
			                    ps.setInt(19,recoveryMonth);
			                    ps.setFloat(20,residualAmount);
			                    ps.setInt(21,installment);
			                    ps.setString(22,remarks);		                   
			                    ps.setString(23,"L");
			                    ps.setString(24,updatedby);
				                ps.setTimestamp(25,ts);
				                ps.setString(26,payment);
				               
				                ps.setInt(27,totalInstallment);
				                ps.setFloat(28,eMi);
				                ps.executeUpdate();
			                    }
			           
			            catch(SQLException e) {
			                xml=xml+"<flag>failure</flag>";
			                e.printStackTrace();
			            }
			            xml=xml+"</response>";
			           // System.out.println(xml);
			        }
			            if(command.equalsIgnoreCase("update")) {
			            	 xml="<response><command>update</command>";	           
				         int acUnit = Integer.parseInt(request.getParameter("unitid").trim());
				         int officeId=Integer.parseInt(request.getParameter("officeid").trim());
				         int cashMonth=Integer.parseInt(request.getParameter("cashmonth").trim());
				         int cashYear=Integer.parseInt(request.getParameter("cashyear").trim());
				         int majorType=Integer.parseInt(request.getParameter("majortype").trim());
				         int MinorType=Integer.parseInt(request.getParameter("minortype").trim());
				         int subType=Integer.parseInt(request.getParameter("subtype").trim());
				         int payeeCode=Integer.parseInt(request.getParameter("empid").trim());
				         int refNo=Integer.parseInt(request.getParameter("refno").trim());
				         String refDate=request.getParameter("refdate");
				         String proDate=request.getParameter("prodate");
				         int sanctionAuthority=Integer.parseInt(request.getParameter("sanctionauthority").trim());
				         int sanctionedBy=Integer.parseInt(request.getParameter("sanctionedby").trim());
				         int acHead=Integer.parseInt(request.getParameter("achead").trim());
				      
				         int paymentUnitId=Integer.parseInt(request.getParameter("paymentunit").trim());
				         int totalInstallment=Integer.parseInt(request.getParameter("totalinstallment").trim());
				         int recoveryMonth=Integer.parseInt(request.getParameter("recoverymonth").trim());
				         float residualAmount=Float.parseFloat(request.getParameter("residualamount").trim());
				         int installment=Integer.parseInt(request.getParameter("installment").trim());
				         float totalAmount=Float.parseFloat(request.getParameter("totalamount").trim());
				         float eMi=Float.parseFloat(request.getParameter("emi").trim());
				         String payeeType=request.getParameter("payeetype");
				         String recovery=request.getParameter("recovery");
				         String remarks=request.getParameter("remarks");	           
				         String payment=request.getParameter("payment");
				         String[] cashMonthYear=proDate.split("/");
				            cashMonth=	Integer.parseInt(cashMonthYear[1]);
				            cashYear=	Integer.parseInt(cashMonthYear[2]);
				            
				            int sanctionNo=Integer.parseInt(request.getParameter("sanctionno").trim());   
				            
				            try{
		               	              		                  		                          
			                    xml=xml+"<flag>success</flag>";
			                    
			                   // to_date(?,'dd-mm-yyyy')
			                 //   String columns="ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,SANCTION_PROCEEDING_DATE,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,PAYEE_TYPE,PAYEE_CODE,REF_NO,REF_DATE,SANCTION_AUTHORITY,SANCTIONED_BY,ACCOUNT_HEAD_CODE,TOTAL_SANCTION_AMOUNT,PAYMENT_TO_BE_MADE_UNIT_ID,RECOVERY, \n+" +
			                    	//	"RECOVERY_START_MONTH,RESIDUAL_AMOUNT,RESIDUAL_INSTL_NO,REMARKS,STATUS,UPDATED_BY_USERID,UPDATED_DATE";
			                    ps=con.prepareStatement("update FAS_SANC_PROC_MST1 set BILL_MAJOR_TYPE_CODE=?,BILL_MINOR_TYPE_CODE=?,BILL_SUB_TYPE_CODE=?,PAYEE_TYPE=?,PAYEE_CODE=?,REF_NO=?,REF_DATE=to_date(?,'dd-mm-yyyy'),SANCTION_AUTHORITY=?,SANCTIONED_BY=?,ACCOUNT_HEAD_CODE=?,TOTAL_SANCTION_AMOUNT=?,PAYMENT_TO_BE_MADE_UNIT_ID=?,RECOVERY=?,RECOVERY_START_MONTH=?,RESIDUAL_AMOUNT=?,RESIDUAL_INSTL_NO=?,REMARKS=?,STATUS=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,PAYMENT=?,TOTAL_INSTALLMENT=?,EMI=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SANCTION_PROCEEDING_DATE=to_date(?,'dd-mm-yyyy') and SANCTION_PROCEEDING_NO=?");
				               
			                   
			                    ps.setInt(1,majorType);
			                    ps.setInt(2,MinorType);
			                    ps.setInt(3,subType);
			                    ps.setString(4,payeeType);
			                    ps.setInt(5,payeeCode);
			                    ps.setInt(6,refNo);
			                    ps.setString(7,refDate);
			                    ps.setInt(8,sanctionAuthority);
			                    ps.setInt(9,sanctionedBy);
			                    ps.setInt(10,acHead);
			                    ps.setFloat(11,totalAmount);
			                    ps.setInt(12,paymentUnitId);
			                    ps.setString(13,recovery);
			                    ps.setInt(14,recoveryMonth);
			                    ps.setFloat(15,residualAmount);
			                    ps.setInt(16,installment);
			                    ps.setString(17,remarks);		                   
			                    ps.setString(18,"L");
			                    ps.setString(19,updatedby);
				                ps.setTimestamp(20,ts);
				                ps.setString(21,payment);
				               
				                ps.setInt(22,totalInstallment);
				                ps.setFloat(23,eMi);
				                ps.setInt(24,acUnit);
				                ps.setInt(25,officeId);
				                ps.setInt(26,cashYear);
			                    ps.setInt(27,cashMonth);
			                    ps.setString(28,proDate);
			                    ps.setInt(29,sanctionNo);
				                ps.executeUpdate();
			                    }
			           
			            catch(SQLException e) {
			                xml=xml+"<flag>failure</flag>";
			                e.printStackTrace();
			            }
			            xml=xml+"</response>";
			           // System.out.println(xml);
			        } 
			            if(command.equalsIgnoreCase("Delete")) {
			            	 xml="<response><command>Delete</command>";	           
				         int acUnit = Integer.parseInt(request.getParameter("unitid").trim());
				         int officeId=Integer.parseInt(request.getParameter("officeid").trim());
				        
				         String proDate=request.getParameter("prodate");
				        
				         String[] cashMonthYear=proDate.split("/");
				         int   cashMonth=	Integer.parseInt(cashMonthYear[1]);
				          int  cashYear=	Integer.parseInt(cashMonthYear[2]);
				            
				            int sanctionNo=Integer.parseInt(request.getParameter("sanctionno").trim());   
				            
				            try{
		               	              		                  		                          
			                    xml=xml+"<flag>success</flag>";
			                    
			                   // to_date(?,'dd-mm-yyyy')
			                 //   String columns="ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,SANCTION_PROCEEDING_DATE,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,PAYEE_TYPE,PAYEE_CODE,REF_NO,REF_DATE,SANCTION_AUTHORITY,SANCTIONED_BY,ACCOUNT_HEAD_CODE,TOTAL_SANCTION_AMOUNT,PAYMENT_TO_BE_MADE_UNIT_ID,RECOVERY, \n+" +
			                    	//	"RECOVERY_START_MONTH,RESIDUAL_AMOUNT,RESIDUAL_INSTL_NO,REMARKS,STATUS,UPDATED_BY_USERID,UPDATED_DATE";
			                    ps=con.prepareStatement("delete from FAS_SANC_PROC_MST1  where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SANCTION_PROCEEDING_DATE=to_date(?,'dd-mm-yyyy') and SANCTION_PROCEEDING_NO=?");
				               
			                   
			                   
				                ps.setInt(1,acUnit);
				                ps.setInt(2,officeId);
				                ps.setInt(3,cashYear);
			                    ps.setInt(4,cashMonth);
			                    ps.setString(5,proDate);
			                    ps.setInt(6,sanctionNo);
				                ps.executeUpdate();
			                    }
			           
			            catch(SQLException e) {
			                xml=xml+"<flag>failure</flag>";
			                e.printStackTrace();
			            }
			            xml=xml+"</response>";
			           // System.out.println(xml);
			        } 
			            else if(command.equalsIgnoreCase("get")) {
			            	 xml="<response><command>get</command>";	           
			            	 int majorType = Integer.parseInt(request.getParameter("majortype").trim());
					           
				           		           
				            try{
		             	              		                  		                          
			                    xml=xml+"<flag>success</flag>";
			                    
			                   // to_date(?,'dd-mm-yyyy')
			                    ps=con.prepareStatement("select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=?");
				               
			                    ps.setInt(1,majorType);
				              
				               rs= ps.executeQuery();
			                           
			                   while(rs.next()) 
			                   {
			                	   xml=xml+"<minorcode>"+rs.getInt("BILL_MINOR_TYPE_CODE")+"</minorcode>";
			                	   xml=xml+"<minordesc>"+rs.getString("BILL_MINOR_TYPE_DESC")+"</minordesc>"; 
			                	   
			                   }
				               
				               
				                           
			                    }
			           
			            catch(SQLException e) {
			                xml=xml+"<flag>failure</flag>";
			                e.printStackTrace();
			            }
			            xml=xml+"</response>";
			            System.out.println(xml);
			        }         
			            
			            else if(command.equalsIgnoreCase("getsub")) {
			            	 xml="<response><command>getsub</command>";	           
			            	 int majorType = Integer.parseInt(request.getParameter("majortype").trim());
					           
			            	 int minorType = Integer.parseInt(request.getParameter("minortype").trim());           
				            try{
		             	              		                  		                          
			                    xml=xml+"<flag>success</flag>";
			                    
			                   // to_date(?,'dd-mm-yyyy')
			                    ps=con.prepareStatement("select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=?");
				               
			                    ps.setInt(1,majorType);
			                    ps.setInt(2,minorType);
				               rs= ps.executeQuery();
			                           
			                   while(rs.next()) 
			                   {
			                	   xml=xml+"<subcode>"+rs.getInt("BILL_SUB_TYPE_CODE")+"</subcode>";
			                	   xml=xml+"<subdesc>"+rs.getString("BILL_SUB_TYPE_DESC")+"</subdesc>"; 
			                	   
			                   }
				               
				               
				                           
			                    }
			           
			            catch(SQLException e) {
			                xml=xml+"<flag>failure</flag>";
			                e.printStackTrace();
			            }
			            xml=xml+"</response>";
			           // System.out.println(xml);
			        }        
			           
			            else if(command.equalsIgnoreCase("load")) {
			            	 xml="<response><command>load</command>";  
			            	 String strEmpName=request.getParameter("empid");
			            	 
				            try{
		             	              		                  		                          
			                    
			                    
			                    ps=con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
			                    ps.setInt(1,Integer.parseInt(strEmpName));
			                    rs=ps.executeQuery();
			                    if(!rs.next()) 
			                    {
			                        xml=xml+"<flag>failure</flag>";
			                    }
			                    else
			                      {
			                            ps=con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_EMP_CURRENT_POSTING WHERE EMPLOYEE_ID=?");
			                            ps.setInt(1,Integer.parseInt(strEmpName));
			                            rs=ps.executeQuery();
			                            if(!rs.next()) {
			                                xml=xml+"<flag>failure1</flag>";
			                            }
			                            else {
			                            	rs.close();
			                            ps.close();
			                           int designationId=0; 
			                            System.out.println("emp id" + strEmpName);
			                            
			                            String sql="select  A.EMPLOYEE_NAME ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME , b.designation_id from hrm_mst_employees a,hrm_emp_current_posting b \n"+
			                            	" where b.employee_id = a.employee_id and a.employee_id=? ";
			                            
			                           ps=con.prepareStatement(sql);
			                          ps.setInt(1,Integer.parseInt(strEmpName));
			                           rs=ps.executeQuery();
			                           if(rs.next())
			                           {
			                        	   xml=xml+"<flag>success</flag>";
			                        	   xml=xml+"<empname>"+rs.getString("EMPLOYEE_NAME")+"</empname>"; 
			                        	   designationId=rs.getInt("designation_id");
			                        	   System.out.println("Designation" + designationId);
			                        	   ps1=con.prepareStatement("select DESIGNATION from HRM_MST_DESIGNATIONS where DESIGNATION_ID=?");
					                          ps1.setInt(1,designationId); 
					                          rs1=ps1.executeQuery();
					                          rs1.next();
					                       xml=xml+"<designation>"+rs1.getString("DESIGNATION")+"</designation>";      
			                           }
		           
			                            }
			                      }
				               
				                           
			                    }
			           
			            catch(SQLException e) {
			                xml=xml+"<flag>failure</flag>";
			                e.printStackTrace();
			            }
			            xml=xml+"</response>";
			           // System.out.println(xml);
			        }         
			           
			            else if(command.equalsIgnoreCase("checkemp")) {
			            	 xml="<response><command>checkemp</command>";  
			            	 String strEmpName=request.getParameter("empid");
			            	 
				            try{
		             	              		                  		                          
			                   
			                    
			                    ps=con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
			                    ps.setInt(1,Integer.parseInt(strEmpName));
			                    rs=ps.executeQuery();
			                    if(!rs.next()) 
			                    {
			                        xml=xml+"<flag>failure</flag>";
			                    }
			                    else
			                      {
			                            ps=con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_EMP_CURRENT_POSTING WHERE EMPLOYEE_ID=?");
			                            ps.setInt(1,Integer.parseInt(strEmpName));
			                            rs=ps.executeQuery();
			                            if(!rs.next()) {
			                                xml=xml+"<flag>failure1</flag>";
			                            }
			                            else {
			                            	rs.close();
			                            ps.close();
			                           int designationId=0; 
			                            System.out.println("emp id" + strEmpName);
			                            
			                            String sql="select  A.EMPLOYEE_NAME ||decode(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME , b.designation_id from hrm_mst_employees a,hrm_emp_current_posting b \n"+
			                            	" where b.employee_id = a.employee_id and a.employee_id=? ";
			                            
			                           ps=con.prepareStatement(sql);
			                          ps.setInt(1,Integer.parseInt(strEmpName));
			                           rs=ps.executeQuery();
			                           if(rs.next())
			                           {
			                        	   xml=xml+"<flag>success</flag>";
			                        	   xml=xml+"<empname>"+rs.getString("EMPLOYEE_NAME")+"</empname>"; 
			                        	   designationId=rs.getInt("designation_id");
			                        	   System.out.println("Designation" + designationId);
			                        	   ps1=con.prepareStatement("select DESIGNATION from HRM_MST_DESIGNATIONS where DESIGNATION_ID=?");
					                          ps1.setInt(1,designationId); 
					                          rs1=ps1.executeQuery();
					                          rs1.next();
					                       xml=xml+"<designation>"+rs1.getString("DESIGNATION")+"</designation>";      
			                           }
		           
			                            }
			                      }
				               
				                           
			                    }
			           
			            catch(SQLException e) {
			                xml=xml+"<flag>failure</flag>";
			                e.printStackTrace();
			            }
			            xml=xml+"</response>";
			           // System.out.println(xml);
			        }         
			            else if(command.equalsIgnoreCase("headcode")) {
			            	 xml="<response><command>headcode</command>";	           
			            	 int headCode = Integer.parseInt(request.getParameter("hcode").trim());
					           
			            	            
				            try{
		             	              		                  		                          
			                   
			                    
			                   // to_date(?,'dd-mm-yyyy')
			                    ps=con.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
				               
			                    ps.setInt(1,headCode);
			                   
				               rs= ps.executeQuery();
			                           
			                   if(rs.next()) 
			                   {
			                	   xml=xml+"<flag>success</flag>";
			                	   xml=xml+"<headname>"+rs.getString("ACCOUNT_HEAD_DESC")+"</headname>";
			                	  
			                	   
			                   }
			                   else{
			                	   xml=xml+"<flag>fail</flag>";
			                   }
				               
				                           
			                    }
			           
			            catch(SQLException e) {
			                xml=xml+"<flag>failure</flag>";
			                e.printStackTrace();
			            }
			            xml=xml+"</response>";
			           // System.out.println(xml);
			        }        
			            
			            else if(command.equalsIgnoreCase("budget")) {
			            	 xml="<response><command>budget</command>";	           
			            	 int headCode = Integer.parseInt(request.getParameter("hcode").trim());
			            	 int unitId = Integer.parseInt(request.getParameter("unitid").trim());
			            	 int officeId = Integer.parseInt(request.getParameter("officeid").trim());
			            	String proDate=request.getParameter("prodate");
			            	
			            String[] cashMonthYear=proDate.split("/");
			            int cashMonth=	Integer.parseInt(cashMonthYear[1]);
			            int cashYear=	Integer.parseInt(cashMonthYear[2]);
			            System.out.println("cashMonth"+cashMonth);
			            System.out.println("cashYear"+cashYear);
			            
			            String finYear="";	
			            if(cashMonth>3)
			            {
			            	finYear=cashYear+"-"+(cashYear+1);
			            }else if(cashMonth<=3)
			            {
			            	finYear=(cashYear-1)+"-"+cashYear;	
			            }
			            System.out.println("Financial Year"+finYear);
			            
				            try{
		             	     
			                   // to_date(?,'dd-mm-yyyy')
			                    ps=con.prepareStatement("select CURRENT_YEAR_BUDGET_ALLOTTED,BUDGET_SOFAR_SPENT from COM_BUDGET_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNT_HEAD_CODE=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
				               
			                    ps.setInt(1,unitId);
			                    ps.setInt(2,headCode);
			                    ps.setInt(3,officeId);
			                    ps.setString(4,finYear);
				               rs= ps.executeQuery();
			                           
			                   if(rs.next()) 
			                   {
			                	   xml=xml+"<flag>success</flag>";
			                	   xml=xml+"<budgetalotted>"+rs.getInt("CURRENT_YEAR_BUDGET_ALLOTTED")+"</budgetalotted>";
			                	   xml=xml+"<budgetspent>"+rs.getInt("BUDGET_SOFAR_SPENT")+"</budgetspent>";
			                	   
			                   }
			                   else{
			                	   xml=xml+"<flag>fail</flag>";
			                   }
				               
				                           
			                    }
			           
			            catch(SQLException e) {
			                xml=xml+"<flag>failure</flag>";
			                e.printStackTrace();
			            }
			            xml=xml+"</response>";
			           // System.out.println(xml);
			        }             
			            
			            
			            out.println(xml);
				       	       
				        out.close();

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
