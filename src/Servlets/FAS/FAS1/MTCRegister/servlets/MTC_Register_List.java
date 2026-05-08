package Servlets.FAS.FAS1.MTCRegister.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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

/**
 * Servlet implementation class MTC_Register_List
 */
public class MTC_Register_List extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    Connection connection = null; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MTC_Register_List() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		  try
	         {
		             HttpSession session=request.getSession(false);
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
	         ResultSet rs=null;
	         int  txtCB_Year=0, txtCB_Month=0,cmbAcc_UnitCode=0,cmbOffice_code=0;
	         PreparedStatement ps=null;
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
	                 ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	                 Class.forName(strDriver.trim());
	                 con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	         }
	         catch(Exception e)
	         {
	        	 	 System.out.println("Exception in openeing connection :"+e);

	         }
	       
	        String CONTENT_TYPE = "text/xml; charset=windows-1252";
	        response.setContentType(CONTENT_TYPE);
	        PrintWriter out = response.getWriter();
	        String strType = "",xml="<response>";
	        try
	        {
	        	     strType = request.getParameter("Command");
	        }
	        catch(Exception e)
	        {
	        		 e.printStackTrace();
	        }
	       
		 if(strType.equalsIgnoreCase("searchByMonth"))  
	        {/*

	 try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
     catch(NumberFormatException e){System.out.println("exception"+e );}
     
     
     try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
     catch(NumberFormatException e){System.out.println("exception"+e );}
     
     txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
     txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));        
    
     
     String optionId=request.getParameter("optionId");
     String optioiiid="";
     
    if(optionId.equalsIgnoreCase("live")){
  	  optioiiid=" and STATUS='L'";
    }else if(optionId.equalsIgnoreCase("cancel")){
  	  optioiiid=" and STATUS='C'";
    }
	            xml="<response><command>searchByMonth</command>";                        
	          
	            String sql="  select  register_updated_by,"+
	            	 "     to_char(PRE_AUDIT_SENT_DATE,'dd/mm/yyyy')PRE_AUDIT_SENT_DATE,"+
	                 "  sanctioned_amount,"+
	            "  UPDATED_REMARKS,"+
	            "  remarks,"+
	            "  status,"+
	            "   to_char(REGISTER_UPDATED_DATE,'dd/mm/yyyy')REGISTER_UPDATED_DATE,"+
	             "  bill_major_type_code,"+
	            "  bill_no,"+
	            "  to_char(bill_date,'dd/mm/yyyy')bill_date,"+
	            "total_deduction_amount,"+
	            "net,"+
	           " DR_ACCOUNT_HEAD_CODE,"+
	            "payee_type,"+
	           " PAYEE_CODE, "+
	            "pass_order_amount,"+
	               "  to_char(mtc70_entry_date,'dd/mm/yyyy')mtc70_entry_date,MTC70_ENTRY_BY, (select EMPLOYEE_NAME from HRM_MST_EMPLOYEES where EMPLOYEE_ID=MTC70_ENTRY_BY )as EntryDesc"+
	               " from  FAS_MTC70_REGISTER" +
				" where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+optioiiid ;
	            
				          System.out.println("SQL::::"+sql);
				            try
				            {
						            int count=0;
						            ps=con.prepareStatement(sql);
						            rs=ps.executeQuery();
					                while(rs.next())
					                {
						                    xml=xml+"<leng>";
						                    xml=xml+"<bill_no>"+rs.getInt("bill_no")+"</bill_no>";						                  
						                    xml=xml+"<BILL_DATE>"+rs.getString("BILL_DATE")+"</BILL_DATE>";
						                    xml=xml+"<sanctioned_amount>"+rs.getInt("sanctioned_amount")+"</sanctioned_amount>";	                
						                    xml=xml+"<total_deduction_amount>"+rs.getString("total_deduction_amount") +"</total_deduction_amount>";						                    
						                    xml=xml+"<net>"+rs.getString("net") +"</net>";
						                    xml=xml+"<mtc70_entry_date>"+rs.getString("mtc70_entry_date") +"</mtc70_entry_date>";
						                    xml=xml+"<EntryDesc>"+rs.getString("EntryDesc") +"</EntryDesc>";
						                  
						                    xml=xml+"<remarks><![CDATA["+rs.getString("remarks")+"]]></remarks>";
						                    
						                    xml=xml+"</leng>";
						                    count++;
					                }
					                if(count>0) 
					                {
						                   // System.out.println("inside count==0");
						                    xml=xml+"<flag>success</flag>";
						                  
					                }
					                else
					                {
					                	 xml=xml+"<flag>failure</flag>";
					                	
					                }
				            }
				            catch(SQLException sqle)
				            {
					        	    sqle.printStackTrace();
					        	    System.out.println("error while fetching data " + sqle);
					                xml="<response><command>searchByMonth</command><flag>failure</flag>";
				            }
        
       
			 
	        changed on mar2014*/
			 

			 String reportType=request.getParameter("Report_type");
			 String subQry="";
			 			if (reportType.equalsIgnoreCase("MtcHO")) {
			 				cmbAcc_UnitCode=0;
			 				cmbOffice_code=0;
			 				subQry=" ";
			 				
			 			} else if (reportType.equalsIgnoreCase("MtcOFF")) {
			 				try {
			 					cmbAcc_UnitCode = Integer.parseInt(request
			 							.getParameter("cmbAcc_UnitCode"));
			 				} catch (NumberFormatException e) {
			 					System.out.println("exception" + e);
			 				}

			 				try {
			 					cmbOffice_code = Integer.parseInt(request
			 							.getParameter("cmbOffice_code"));
			 				} catch (NumberFormatException e) {
			 					System.out.println("exception" + e);
			 				}
			 				subQry=" ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and  ";

			 			}
			 	
			      txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
			      txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));        
			     
			      
			      String optionId=request.getParameter("optionId");
			      String optioiiid="";
			      
			     if(optionId.equalsIgnoreCase("live")){
			   	  optioiiid=" and STATUS='L'";
			     }else if(optionId.equalsIgnoreCase("cancel")){
			   	  optioiiid=" and STATUS='C'";
			     }
			 	            xml="<response><command>searchByMonth</command>";                        
			 	          
			 	            String sql="  select  r.ACCOUNTING_UNIT_ID,(select u.accounting_unit_name from fas_mst_acct_units u where u.accounting_unit_id=r.accounting_unit_id) as unit_name," +
			 	            		"ACCOUNTING_FOR_OFFICE_ID,register_updated_by,"+
			 	            	 "     to_char(PRE_AUDIT_SENT_DATE,'dd/mm/yyyy')PRE_AUDIT_SENT_DATE,"+
			 	                 "  sanctioned_amount,"+
			 	            "  UPDATED_REMARKS,"+
			 	            "  remarks,"+
			 	            "  status,"+
			 	            "   to_char(REGISTER_UPDATED_DATE,'dd/mm/yyyy')REGISTER_UPDATED_DATE,"+
			 	             "  bill_major_type_code,"+
			 	            "  bill_no,"+
			 	            "  to_char(bill_date,'dd/mm/yyyy')bill_date,"+
			 	            "total_deduction_amount,"+
			 	            "net,"+
			 	           " DR_ACCOUNT_HEAD_CODE,"+
			 	            "payee_type,"+
			 	           " PAYEE_CODE, "+
			 	            "pass_order_amount,"+
			 	               "  to_char(mtc70_entry_date,'dd/mm/yyyy')mtc70_entry_date,MTC70_ENTRY_BY, (select EMPLOYEE_NAME from HRM_MST_EMPLOYEES where EMPLOYEE_ID=MTC70_ENTRY_BY )as EntryDesc"+
			 	               " from  FAS_MTC70_REGISTER r" +
			 				" where "+subQry+" CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+optioiiid+"  order by  ACCOUNTING_UNIT_ID, bill_no" ;
			 	            
			 				          System.out.println("SQL::::"+sql);
			 				            try
			 				            {
			 						            int count=0;
			 						            ps=con.prepareStatement(sql);
			 						            rs=ps.executeQuery();
			 					                while(rs.next())
			 					                {
			 						                    xml=xml+"<leng>";
			 						                  
			 						                    xml=xml+"<ACCOUNTING_UNIT_ID>"+rs.getInt("ACCOUNTING_UNIT_ID")+"</ACCOUNTING_UNIT_ID>";			
			 						                    xml=xml+"<unitname>"+rs.getString("unit_name")+"</unitname>";
			 						                    xml=xml+"<ACCOUNTING_FOR_OFFICE_ID>"+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+"</ACCOUNTING_FOR_OFFICE_ID>";
			 						                    xml=xml+"<bill_no>"+rs.getInt("bill_no")+"</bill_no>";						                  
			 						                    xml=xml+"<BILL_DATE>"+rs.getString("BILL_DATE")+"</BILL_DATE>";
			 						                    xml=xml+"<sanctioned_amount>"+rs.getInt("sanctioned_amount")+"</sanctioned_amount>";	                
			 						                    xml=xml+"<total_deduction_amount>"+rs.getString("total_deduction_amount") +"</total_deduction_amount>";						                    
			 						                    xml=xml+"<net>"+rs.getString("net") +"</net>";
			 						                    xml=xml+"<mtc70_entry_date>"+rs.getString("mtc70_entry_date") +"</mtc70_entry_date>";
			 						                    xml=xml+"<EntryDesc>"+rs.getString("EntryDesc") +"</EntryDesc>";
			 						                  
			 						                    xml=xml+"<remarks><![CDATA["+rs.getString("remarks")+"]]></remarks>";
			 						                    
			 						                    xml=xml+"</leng>";
			 						                    count++;
			 					                }
			 					                if(count>0) 
			 					                {
			 						                   // System.out.println("inside count==0");
			 						                    xml=xml+"<flag>success</flag>";
			 						                   xml=xml+"<reportType>"+reportType+"</reportType>";
			 						                  
			 					                }
			 					                else
			 					                {
			 					                	 xml=xml+"<flag>failure</flag>";
			 					                	  xml=xml+"<reportType>"+reportType+"</reportType>";
			 					                	
			 					                }
			 				            }
			 				            catch(SQLException sqle)
			 				            {
			 					        	    sqle.printStackTrace();
			 					        	    System.out.println("error while fetching data " + sqle);
			 					                xml="<response><command>searchByMonth</command><flag>failure</flag>";
			 				            }
			         
			        
			 			 
	        }
		 else  if(strType.equalsIgnoreCase("searchByMonthUpdated"))  
	        { String param=request.getParameter("param");
			 String subQRY="";
			 if(param.equalsIgnoreCase("MTCHO")){
				 cmbAcc_UnitCode=0;
				 cmbOffice_code=0;
				 subQRY="";
			 }else if(param.equalsIgnoreCase("MTCOFF")){
				 try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
			     catch(NumberFormatException e){System.out.println("exception"+e );}
			     
			     
			     try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
			     catch(NumberFormatException e){System.out.println("exception"+e );}
			     subQRY= " ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ";
			 }

			
		     
		     txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
		     txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));        
		    
		     
		     String optionId=request.getParameter("optionId");
		     String optioiiid="";
		     
		    if(optionId.equalsIgnoreCase("live")){
		  	  optioiiid=" and STATUS='L'";
		    }else if(optionId.equalsIgnoreCase("cancel")){
		  	  optioiiid=" and STATUS='C'";
		    }
			            xml="<response><command>searchByMonthUpdated</command>";                        
			          
			            String sql="  select  r.ACCOUNTING_UNIT_ID,"+
  " (SELECT u.accounting_unit_name   FROM fas_mst_acct_units u   WHERE u.accounting_unit_id=r.accounting_unit_id "+
  " ) AS unit_name,   ACCOUNTING_FOR_OFFICE_ID, " +
			            		"register_updated_by,"+
			            	 "     to_char(PRE_AUDIT_SENT_DATE,'dd/mm/yyyy')PRE_AUDIT_SENT_DATE,"+
			                 "  sanctioned_amount,"+
			            "  UPDATED_REMARKS,"+
			            "  remarks,"+
			            "  status,"+
			            "   to_char(REGISTER_UPDATED_DATE,'dd/mm/yyyy')REGISTER_UPDATED_DATE,"+
			             "  bill_major_type_code,"+
			            "  bill_no,"+
			            "  to_char(bill_date,'dd/mm/yyyy')bill_date,"+
			            "total_deduction_amount,"+
			            "net,"+
			           " DR_ACCOUNT_HEAD_CODE,"+
			            "payee_type,"+
			           " PAYEE_CODE, "+
			            "pass_order_amount," +
			            "to_char(CHECKED_AND_PASSED_DATE,'dd/mm/yyyy')CHECKED_AND_PASSED_DATE," +
			            "CHECKED_AND_PASSED_BY,"+
			               "  to_char(mtc70_entry_date,'dd/mm/yyyy')mtc70_entry_date,MTC70_ENTRY_BY, (select EMPLOYEE_NAME from HRM_MST_EMPLOYEES where EMPLOYEE_ID=MTC70_ENTRY_BY )as EntryDesc"+
			               " from  FAS_MTC70_REGISTER r" +
						" where "+subQRY+"  CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+optioiiid+" and REGISTER_UPDATED_DATE is not null order by  ACCOUNTING_UNIT_ID, bill_no" ;//"and STATUS='L'";
			            
						          System.out.println("updasted SQL::::"+sql);
						            try
						            {
								            int count=0;
								            ps=con.prepareStatement(sql);
								            rs=ps.executeQuery();
							                while(rs.next())
							                {
								                    xml=xml+"<leng>";
								                  
								                    xml=xml+"<unitname>"+rs.getString("unit_name")+"</unitname>";	
								                    xml=xml+"<ACCOUNTING_UNIT_ID>"+rs.getInt("ACCOUNTING_UNIT_ID")+"</ACCOUNTING_UNIT_ID>";	
								                    xml=xml+"<ACCOUNTING_FOR_OFFICE_ID>"+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+"</ACCOUNTING_FOR_OFFICE_ID>";	
								                    xml=xml+"<bill_no>"+rs.getInt("bill_no")+"</bill_no>";						                  
								                    xml=xml+"<BILL_DATE>"+rs.getString("BILL_DATE")+"</BILL_DATE>";
								                    xml=xml+"<sanctioned_amount>"+rs.getInt("sanctioned_amount")+"</sanctioned_amount>";	                
								                    xml=xml+"<total_deduction_amount>"+rs.getString("total_deduction_amount") +"</total_deduction_amount>";						                    
								                    xml=xml+"<net>"+rs.getString("net") +"</net>";
								                    xml=xml+"<mtc70_entry_date>"+rs.getString("mtc70_entry_date") +"</mtc70_entry_date>";
								                    xml=xml+"<EntryDesc>"+rs.getString("EntryDesc") +"</EntryDesc>";
								                  
								                    xml=xml+"<CHECKED_AND_PASSED_DATE>"+rs.getString("CHECKED_AND_PASSED_DATE") +"</CHECKED_AND_PASSED_DATE>";
								                    xml=xml+"<CHECKED_AND_PASSED_BY>"+rs.getString("CHECKED_AND_PASSED_BY") +"</CHECKED_AND_PASSED_BY>";
								                    xml=xml+"<REGISTER_UPDATED_DATE>"+rs.getString("REGISTER_UPDATED_DATE") +"</REGISTER_UPDATED_DATE>";
								             							              						                    
								                    xml=xml+"<remarks><![CDATA["+rs.getString("remarks")+"]]></remarks>";
								                    
								                    xml=xml+"</leng>";
								                    count++;
							                }
							                if(count>0) 
							                {
								                   // System.out.println("inside count==0");
								                    xml=xml+"<flag>success</flag>";
								                    xml=xml+"<param>"+param+"</param>";	
							                }
							                else
							                {
							                	 xml=xml+"<flag>failure</flag>";
							                	  xml=xml+"<param>"+param+"</param>";	
							                	
							                }
						            }
						            catch(SQLException sqle)
						            {
							        	    sqle.printStackTrace();
							        	    System.out.println("error while fetching data " + sqle);
							                xml="<response><command>searchByMonthUpdated</command><flag>failure</flag>";
						            }
		        }
			        
	        xml=xml+"</response>";   
	        out.println(xml); 
	        System.out.println(xml); 
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
