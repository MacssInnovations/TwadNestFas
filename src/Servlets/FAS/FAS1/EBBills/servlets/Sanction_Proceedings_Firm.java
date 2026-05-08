package Servlets.FAS.FAS1.EBBills.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
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
 * Servlet implementation class Sanction_Proceedings_Firm
 */
public class Sanction_Proceedings_Firm extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";     
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sanction_Proceedings_Firm() {
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
			           
			             if(command.equalsIgnoreCase("get")) {
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
		       
		        
		        String command;
		        command = request.getParameter("command");
		        
		       
		       
		        long l=System.currentTimeMillis();
		        java.sql.Timestamp ts=new java.sql.Timestamp(l);
		            System.out.println("got");
		            System.out.println("command"+command);
		        
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
		            String updatedby=(String)session.getAttribute("UserId");
		            
		      if(command.equalsIgnoreCase("Add"))
		      {
		    	int sanctionNo=0;  
		    	int serialNo=1;
		    	int unitId= Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		    	int officeId=  Integer.parseInt(request.getParameter("cmbOffice_code"));
		    	String payment=  request.getParameter("payment");
		    	int majorType=   Integer.parseInt(request.getParameter("majortype"));
		    	int minorType=   Integer.parseInt(request.getParameter("minortype")); 
		    	int subType=   Integer.parseInt(request.getParameter("subtype")); 
		    	String payeeType=  request.getParameter("payeetype");
		    	 
		    	int refNo=   Integer.parseInt(request.getParameter("refno"));
		    	String refDate=  request.getParameter("refdate");
		    	String proDate=  request.getParameter("prodate");
		    	int sanctionAuthority=   Integer.parseInt(request.getParameter("sanctionauthority"));
		    	int sanctionedBy=   Integer.parseInt(request.getParameter("sanctionedby"));
		    	int headCode=   Integer.parseInt(request.getParameter("cmbAcHeadCode"));
		    	
		    float totalamount=   Float.parseFloat(request.getParameter("totalamount"));
		    	
		    	int estimateNo=   Integer.parseInt(request.getParameter("estimatenumber"));
		    	
		    	String particulars=  request.getParameter("particulars");
		    	
		    	 String[] cashMonthYear=proDate.split("/");
		          int  cashMonth=	Integer.parseInt(cashMonthYear[1]);
		          int  cashYear=	Integer.parseInt(cashMonthYear[2]);
		          try{
		          ps=con.prepareStatement("select max(SANCTION_PROCEEDING_NO) as sanctionno from FAS_SANC_PROC_FIRM_MST1");
		          rs=ps.executeQuery();
		          if(rs.next())
		          {
		        	  sanctionNo=rs.getInt("sanctionno") ;
		          }
		          }catch(Exception e){System.out.println(e);}
		          
		          
		          sanctionNo=sanctionNo+1;
		         
		          try{
		        	  con.clearWarnings();
	                  con.setAutoCommit(false);
		        	  
		          ps=con.prepareStatement("insert into FAS_SANC_PROC_FIRM_MST1(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,SANCTION_PROCEEDING_DATE,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,PAYEE_TYPE,REF_NO,REF_DATE,SANCTION_AUTHORITY,SANCTIONED_BY,TOTAL_SANCTION_AMOUNT,ACCOUNT_HEAD_CODE,PARTICULARS,STATUS,UPDATED_BY_USERID,UPDATED_DATE,PAYMENT,ESTIMATE_NUMBER) values(?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,?,?,?)");
		          ps.setInt(1, unitId);
		          ps.setInt(2, officeId);
		          ps.setInt(3, cashYear);
		          ps.setInt(4, cashMonth);
		          ps.setString(5,proDate);
		          ps.setInt(6, majorType);
		          ps.setInt(7, minorType);
		          ps.setInt(8, subType);
		          ps.setString(9, payeeType);
		          ps.setInt(10, refNo);
		          ps.setString(11,refDate);
		          ps.setInt(12, sanctionAuthority);
		          ps.setInt(13, sanctionedBy);
		          ps.setFloat(14, totalamount);
		          ps.setInt(15, headCode);
		          ps.setString(16, particulars);
		          ps.setString(17, "L");
		          ps.setString(18, updatedby);
		          ps.setTimestamp(19, ts);
		          ps.setString(20, payment);
		          ps.setInt(21, estimateNo);
		          ps.executeUpdate();
		          
		          
		          String invoice_No[]=request.getParameterValues("invoice_no");	
		          String invoice_Date[]=request.getParameterValues("invoice_date");
		          String particular_Detail[]=request.getParameterValues("particular_detail");
		          String m_RefNo[]=request.getParameterValues("m_refno");
		          String m_RefDate[]=request.getParameterValues("m_refdate");
		          String agree_No[]=request.getParameterValues("agree_no");
		          String agree_Date[]=request.getParameterValues("agree_date");
		          String supplement_No[]=request.getParameterValues("supplement_no");
		          String supplement_Date[]=request.getParameterValues("supplement_date");
		          String invoice_Amount[]=request.getParameterValues("invoice_amount");
		          String sanc_Amount[]=request.getParameterValues("sanc_amount");
		          String remarks12[]=request.getParameterValues("remarks12");
		                 
		          int invoiceNo=0,mRefNo=0,agreeNo=0,supplementNo=0;
		          float invoiceAmount=0,sancAmount=0;
		          
		          for(int i=0;i<invoice_No.length;i++){
		          ps=con.prepareStatement("insert into FAS_SANC_PROC_FIRM_TRN1(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,SANCTION_PROCEEDING_NO,SL_NO,INVOICE_NO,INVOICE_DATE,INVOICE_AMOUNT,SANCTION_AMOUNT,M_BOOK_REF_NO,M_BOOK_REF_DATE,REMARKS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,?,to_date(?,'dd-mm-yyyy'),?,?,?)"); 
		         
		          
		          try{
		        	  invoiceNo=Integer.parseInt(invoice_No[i]);
		          }catch(Exception e){System.out.println(e);}
		          
		          try{
		        	  mRefNo=Integer.parseInt(m_RefNo[i]);
		          }catch(Exception e){System.out.println(e);}
		          
		          try{
		        	  agreeNo=Integer.parseInt(agree_No[i]);
		          }catch(Exception e){System.out.println(e);}
		          
		          try{
		        	  supplementNo=Integer.parseInt(supplement_No[i]);
		          }catch(Exception e){System.out.println(e);}
		          
		          try{
		        	  invoiceAmount=Float.parseFloat(invoice_Amount[i]);
		          }catch(Exception e){System.out.println(e);}
		          
		          try{
		        	  sancAmount=Float.parseFloat(sanc_Amount[i]);
		          }catch(Exception e){System.out.println(e);}
		          
		          
		         String invoiceDate=invoice_Date[i];
		         String mRefDate=m_RefDate[i];
		         String remarks=remarks12[i];
		         String agreeDate=agree_Date[i];
		         String suppleDate=supplement_Date[i];
		         String particularDetails=particular_Detail[i];
		         
		          ps.setInt(1, unitId);
		          ps.setInt(2, officeId);
		          ps.setInt(3, cashYear);
		          ps.setInt(4, cashMonth);
		          ps.setInt(5, sanctionNo);
		          ps.setInt(6, serialNo);
		          ps.setInt(7, invoiceNo);
		          ps.setString(8, invoiceDate);
		          ps.setFloat(9, invoiceAmount);
		          ps.setFloat(10, sancAmount);
		          ps.setInt(11, mRefNo);
		          ps.setString(12, mRefDate);
		          ps.setString(13, remarks);
		          ps.setString(14, updatedby);
		          ps.setTimestamp(15, ts);
		          ps.executeUpdate(); 
		          serialNo++;
		          
		          }
		          if(invoice_No.length==(serialNo-1))
		          {
		          con.commit();
		          sendMessage(response,"The Sanction Number '"+sanctionNo+"' has been Created Successfully ","ok");
		          }
		          
		          }catch(Exception e){
		        	  try{con.rollback();}catch(SQLException sqle){System.out.println("exception in rollback "+sqle);}
	                     e.getStackTrace();
	                    
	                    	 sendMessage(response,"The Sanction Number Creation Failed ","ok");
	                     
	                     System.out.println("Exception occur due to "+e);
		        	  
		        	  
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
