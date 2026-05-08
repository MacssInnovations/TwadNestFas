package Servlets.FAS.FAS1.CivilBills.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class PassOrderPreparation_Edit
 */
public class PassOrderPreparation_Edit extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PassOrderPreparation_Edit() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
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
	         
	       
	        if(cmd.equalsIgnoreCase("loadpassno")){
	    			System.out.println("loadpassno.....");
	        	 try{
	    			xml = "";
	    			//int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
	    			//int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	    			int cbmonth=0,cbyear=0;
		        	 try{cbyear= Integer.parseInt(request.getParameter("cbyear"));} 
			         catch(Exception e1){System.out.println("Err in getting cbyear "+e1.getMessage()); }
			         System.out.println("cbyear"+cbyear);
			         try
			         {cbmonth= Integer.parseInt(request.getParameter("cbmonth")); }
			         catch(Exception e2){ System.out.println("Err in getting cbmonth "+e2.getMessage()); }
	    			
	    			count = 0;
	    			//connection = load.getConnection();
	    			xml = "<response><command>passorder</command>";
	    			String sql="SELECT m.PASS_ORDER_NO,m.CASHBOOK_YEAR,m.CASHBOOK_MONTH "+
	    				"	FROM FAS_PASS_ORDER_MST m"+
	    				"	WHERE "+
	    				
	    				" m.ACCOUNTING_UNIT_ID    =? "+
	    				" AND m.ACCOUNTING_FOR_OFFICE_ID=? "+
	    				"  AND m.CASHBOOK_YEAR             =? "+
	    				 "   AND m.CASHBOOK_MONTH            =? "+
	    				" and m.status='L' and (m.APPROVED_BY='N' or m.APPROVED_BY is NULL)";		
	    		 ps = con.prepareStatement(sql);
	    			
	    			ps.setInt(1,unitid);
	    			ps.setInt(2,offid);
	    			ps.setInt(3,cbyear);
	    			ps.setInt(4,cbmonth);
	    			
	    			System.out.println("sql  passorder number "+sql );
	    			result = ps.executeQuery();
	    			while(result.next()){
	    				xml +="<PASS_ORDER_NO>"+result.getInt("PASS_ORDER_NO")+"</PASS_ORDER_NO>";
	    				//xml +="<sanction_proc_no>"+result.getInt("sanction_proc_no")+"</sanction_proc_no>";
	    				xml +="<CASHBOOK_YEAR>"+result.getInt("CASHBOOK_YEAR")+"</CASHBOOK_YEAR>";
	    				xml +="<CASHBOOK_MONTH>"+result.getInt("CASHBOOK_MONTH")+"</CASHBOOK_MONTH>";
	    				count++;
	    			}
	    			if(count==0){
	    				xml +="<status>nodata</status>";
	    			}else{
	    				xml +="<status>success</status>";
	    			}
	    			xml +="</response>";
	    			    			
	    			
	    			
	    				 				
	 				
	 			} catch (SQLException e) {
	 				// TODO Auto-generated catch block
	 				xml="<response><status>fail</status></response>";
	 				e.printStackTrace();
	 			}
	 			
	 			
	 			
	 			
	 			
	 			
	 			
	 			
	    		}
	         else if(cmd.equalsIgnoreCase("loadGrid"))
	         {
	        	 int cbmonth=0,cbyear=0,passNo=0;
	        	 try{cbyear= Integer.parseInt(request.getParameter("cbyear"));} 
		         catch(Exception e1){System.out.println("Err in getting cbyear "+e1.getMessage()); }
		         System.out.println("cbyear"+cbyear);
		         try
		         {cbmonth= Integer.parseInt(request.getParameter("cbmonth")); }
		         catch(Exception e2){ System.out.println("Err in getting cbmonth "+e2.getMessage()); }
		         System.out.println("cbmonth"+cbmonth); 
		         
		         passNo=Integer.parseInt(request.getParameter("passOrderNo"));
		         
	             xml="<response><command>loadGrid</command>"; 
	              try 
	                      {
	                          /* String qu="(select BILL_NO,BILL_MAJOR_TYPE, "+
										" BILL_MAJOR_TYPE_DESC, "+
	                        	   "   BILL_MINOR_TYPE_CODE, "+
	                        	   " 	  BILL_MINOR_TYPE_DESC, "+
	                        	   " 	  BILL_SUB_TYPE_CODE, "+
	                        	   " 	  BILL_SUB_TYPE_DESC, "+
	                        	   " 	  to_char(BILL_DATE,'dd/mm/yyyy')as BILL_DATE, "+
	                        	   " 	  sum(amount)as amount, "+
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
	                            		  " FROM FAS_BILL_REGISTER_MASTER reg,FAS_BILL_REGISTER_TRANSACTION trn, "+
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
	                            		  " and reg.MEMO_ENTRY='Y' and PASS_ORDER_DATE is null "+
	                            		  " AND reg.BILL_MAJOR_TYPE          =major1.BILL_MAJOR_TYPE_CODE "+
	                            		  " AND reg.BILL_MAJOR_TYPE          =minor.BILL_MAJOR_TYPE_CODE "+
	                            		  " AND reg.BILL_MINOR_TYPE_CODE     =minor.BILL_MINOR_TYPE_CODE "+
	                            		  " AND reg.BILL_MAJOR_TYPE          =sub.BILL_MAJOR_TYPE_CODE "+
	                            		  " AND reg.BILL_MINOR_TYPE_CODE     =sub.BILL_MINOR_TYPE_CODE "+
	                            		  " And Reg.Bill_Sub_Type_Code       =Sub.Bill_Sub_Type_Code "+
	                              		  " AND trn.ACCOUNT_HEAD_CODE        =head.ACCOUNT_HEAD_CODE)" +
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
	                              		"  SUM(amount)                    AS amount,  " +
	                              		"  PAYEE_CODE,  " +
	                              		"  TO_CHAR(BILL_SCRUTINY_DATE,'dd/mm/yyyy')AS BILL_SCRUTINY_DATE,  " +
	                              		"  Remarks ,  " +
	                              		" SANCTION_PROC_NO,  " +
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
	                              		"  AND reg.BILL_SCRUTINY_DONE        ='Y'  " +
	                              		"  AND reg.MEMO_ENTRY                ='Y'  " +
	                              		"  AND PASS_ORDER_DATE              IS NULL  " +
	                              		"  AND reg.BILL_MAJOR_TYPE           =major1.BILL_MAJOR_TYPE_CODE  " +
	                              		" AND reg.BILL_MAJOR_TYPE           =minor.BILL_MAJOR_TYPE_CODE  " +
	                              		" AND reg.BILL_MINOR_TYPE_CODE      =minor.BILL_MINOR_TYPE_CODE  " +
	                              		"  AND reg.BILL_MAJOR_TYPE           =sub.BILL_MAJOR_TYPE_CODE  " +
	                              		"  AND reg.BILL_MINOR_TYPE_CODE      =sub.BILL_MINOR_TYPE_CODE  " +
	                              		"  AND Reg.Bill_Sub_Type_Code        =sub.Bill_Sub_Type_Code  " +
	                              		"  AND reg.ACCOUNT_HEAD_CODE         =head.ACCOUNT_HEAD_CODE  " +
	                              		"  )  " +
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
	                              		"  ) "  ;*/
	            	  
	            	  String ttoqry=" select sum(amount) as Totamt,PASS_ORDER_NO,PASS_ORDER_DATE,PASS_ORDER_PREPARED_BY,employeeName,REMARKS from (SELECT t.BILL_NO, "+
						 " TO_CHAR(t.BILL_DATE,'dd/mm/yyyy')AS BILL_DATE,  "+
						 "   t.BILL_AMOUNT                    AS amount, "+
						 "   t.SANCTION_PROC_NO,t.PAYABLE_TO, "+
						 "   m.PASS_ORDER_NO, "+
						// "   m.PASS_ORDER_DATE, "+
						"  TO_CHAR(m.PASS_ORDER_DATE,'dd/mm/yyyy')AS PASS_ORDER_DATE, "+
						 "   m.PASS_ORDER_PREPARED_BY, "+
						 "   (SELECT EMPLOYEE_INITIAL "+
						 "     ||' ' "+
						 "     ||EMPLOYEE_NAME AS employee_name "+
						 "   FROM HRM_MST_EMPLOYEES h "+
						 "   WHERE h.EMPLOYEE_ID= m.PASS_ORDER_PREPARED_BY "+
						 "   ) AS employeeName, "+
						 "   m.REMARKS "+
						  " FROM FAS_PASS_ORDER_MST m, "+
						  "   FAS_PASS_ORDER_TRN t "+
						  " WHERE m.Accounting_Unit_Id     =t.Accounting_Unit_Id "+
						  " AND m.ACCOUNTING_FOR_OFFICE_ID =t.ACCOUNTING_FOR_OFFICE_ID "+
						  " AND m.CASHBOOK_YEAR            =t.CASHBOOK_YEAR "+
						  " AND m.CASHBOOK_MONTH           =t.CASHBOOK_MONTH "+
						  " AND m.PASS_ORDER_NO            =t.PASS_ORDER_NO "+
						//  " --  AND m.APPROVED_DATE IS NULL "+
						  " 	AND (m.APPROVED_BY             ='N' "+
						  " OR m.APPROVED_BY              IS NULL) "+
						  " AND m.Accounting_Unit_Id       =? "+
						  " AND m.ACCOUNTING_FOR_OFFICE_ID =? "+
						  " AND m.CASHBOOK_YEAR            =? "+
						  " AND m.CASHBOOK_MONTH           =? "+
						  " AND m.PASS_ORDER_NO            =? "+
						  " ORDER BY m.PASS_ORDER_NO )" +
						  " group by PASS_ORDER_NO,PASS_ORDER_DATE,PASS_ORDER_PREPARED_BY,employeeName,REMARKS";
	            	  ps1 = con.prepareStatement(ttoqry);
                      ps1.setInt(1,unitid);
		                  ps1.setInt(2,offid);
		                  ps1.setInt(3,cbyear);
		                  ps1.setInt(4,cbmonth);
		                 ps1.setInt(5,passNo);
		                 result1 = ps1.executeQuery();   
		                 if(result1.next()){
		            		  
		            	  
	            	  String qu="  SELECT t.BILL_NO, "+
								 " TO_CHAR(t.BILL_DATE,'dd/mm/yyyy')AS BILL_DATE,  "+
								 "   t.BILL_AMOUNT                    AS amount, "+
								 "   t.SANCTION_PROC_NO,t.PAYABLE_TO, "+
								 "   m.PASS_ORDER_NO, "+
								// "   m.PASS_ORDER_DATE, "+
								 "  TO_CHAR(m.PASS_ORDER_DATE,'dd/mm/yyyy')AS PASS_ORDER_DATE, "+
								 "   m.PASS_ORDER_PREPARED_BY, "+
								 "   (SELECT EMPLOYEE_INITIAL "+
								 "     ||' ' "+
								 "     ||EMPLOYEE_NAME AS employee_name "+
								 "   FROM HRM_MST_EMPLOYEES h "+
								 "   WHERE h.EMPLOYEE_ID= m.PASS_ORDER_PREPARED_BY "+
								 "   ) AS employeeName, "+
								 "   m.REMARKS "+
								  " FROM FAS_PASS_ORDER_MST m, "+
								  "   FAS_PASS_ORDER_TRN t "+
								  " WHERE m.Accounting_Unit_Id     =t.Accounting_Unit_Id "+
								  " AND m.ACCOUNTING_FOR_OFFICE_ID =t.ACCOUNTING_FOR_OFFICE_ID "+
								  " AND m.CASHBOOK_YEAR            =t.CASHBOOK_YEAR "+
								  " AND m.CASHBOOK_MONTH           =t.CASHBOOK_MONTH "+
								  " AND m.PASS_ORDER_NO            =t.PASS_ORDER_NO "+
								//  " --  AND m.APPROVED_DATE IS NULL "+
								  " 	AND (m.APPROVED_BY             ='N' "+
								  " OR m.APPROVED_BY              IS NULL) "+
								  " AND m.Accounting_Unit_Id       =? "+
								  " AND m.ACCOUNTING_FOR_OFFICE_ID =? "+
								  " AND m.CASHBOOK_YEAR            =? "+
								  " AND m.CASHBOOK_MONTH           =? "+
								  " AND m.PASS_ORDER_NO            =? "+
								  " ORDER BY m.PASS_ORDER_NO ";
	                              System.out.println(qu);
	                              ps = con.prepareStatement(qu);
	                              ps.setInt(1,unitid);
	     		                  ps.setInt(2,offid);
	     		                  ps.setInt(3,cbyear);
	     		                  ps.setInt(4,cbmonth);
	     		                 ps.setInt(5,passNo);
	     		               
	                             result = ps.executeQuery();      
	                            // System.out.println("result>>>"+result);
	                              while(result.next()) 
	                              {
	                            	// BigDecimal bam=new BigDecimal(0.00);
	                            		//bam=new BigDecimal(result.getFloat("AMOUNT"));
	                            //	System.out.println("::::amt :"+result.get("AMOUNT"));  
	                            	  xml=xml+"<display>"; 
	                            	  xml=xml+"<billno>"+result.getInt("BILL_NO")+"</billno>";
	                               
	                                  xml=xml+"<billdate>"+result.getString("BILL_DATE")+"</billdate>";
	                                  xml=xml+"<billamount>"+result.getInt("AMOUNT")+"</billamount>";
	                                
	                                  xml=xml+"<payto>"+result.getString("PAYABLE_TO")+"</payto>";
	                                
	                                  xml=xml+"<rem>"+result.getString("REMARKS")+"</rem>";
	                                  xml=xml+"<SANCTION_PROC_NO>"+result.getInt("SANCTION_PROC_NO")+"</SANCTION_PROC_NO>";
	                                
	                                  
	                                  
	                                  xml=xml+"</display>";
	                                  count++;
	                              }
	                              
	                              
	                              xml=xml+"<PassOrderAmt>"+result1.getInt("Totamt")+"</PassOrderAmt>";
	                              xml=xml+"<PASS_ORDER_NO>"+result1.getInt("PASS_ORDER_NO")+"</PASS_ORDER_NO>";
	                              xml=xml+"<PASS_ORDER_DATE>"+result1.getString("PASS_ORDER_DATE")+"</PASS_ORDER_DATE>";
	                              xml=xml+"<PASS_ORDER_PREPARED_BY>"+result1.getInt("PASS_ORDER_PREPARED_BY")+"</PASS_ORDER_PREPARED_BY>";
	                              xml=xml+"<employeeName>"+result1.getString("employeeName")+"</employeeName>";
	                              xml=xml+"<REMARKS>"+result1.getString("REMARKS")+"</REMARKS>";
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		   
        
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
	     int Pass_or_no=0; 
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
	    {cbmonth= Integer.parseInt(request.getParameter("cbmonth"));
	    Pass_or_no=Integer.parseInt(request.getParameter("passOrderNo"));
	    }
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
	    
	   // checkNo = request.getParameterValues("checkNo"); 
	   checkNo = request.getParameterValues("verify_select"); 
	    
	    tnodebillno = request.getParameterValues("tnodebillno"); 
	  
	    billAmt = request.getParameterValues("billAmt"); 
	   
	    tpayable = request.getParameterValues("tpayable"); 
	 
	   billdatt=request.getParameterValues("billdatee");
	    sanNo = request.getParameterValues("SANCTION_PROC_NO1"); 
	   
	  
	    
	  String verify_select[]=request.getParameterValues("verify_select");
        
       String verify_select_status[]=request.getParameterValues("verify_select_status");
        
	    //System.out.println("checkNo>>>"+checkNo.length);
	    String[] tnodebillno1=request.getParameterValues("tnodebillno");
		
	    
	    /*System.out.println("PassOrderDate "+PassOrderDate);
	    System.out.println("PassOrderPreparedBye "+PassOrderPreparedBye);
	    System.out.println("TotalAmt "+TotalAmt);*/
		
	     cmd=request.getParameter("command");
	     int i2 = 1, i3 = 0;
            if(cmd.equalsIgnoreCase("EditPassorder"))
            {
                try {
                	con.clearWarnings();
        			con.setAutoCommit(false);
        			
        			
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
     	             
        			
					ps3 = con.prepareStatement("update FAS_PASS_ORDER_MST set PASS_ORDER_DATE=?,PASS_ORDER_PREPARED_BY=?,REMARKS=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? AND PASS_ORDER_NO=?");
					
    				ps3.setDate(1, PassOrderDate);
    				ps3.setInt(2, PassOrderPreparedBye);
    				ps3.setString(3, remark);	
    				//ps3.setString(9, "L");//STATUS,
    				ps3.setString(4, userid);
    				ps3.setTimestamp(5, ts);
    				ps3.setInt(6, unitid);
    				ps3.setInt(7, offid);
    				ps3.setInt(8, txtCash_year);
    				ps3.setInt(9, txtCash_Month_hid);
    				ps3.setInt(10, Pass_or_no);
    				
    				int sd1=ps3.executeUpdate();
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
            	   // int major2 = Integer.parseInt(major1[i]); 
            	   // int minor2= Integer.parseInt(minor1[i]); 
            	   // int sub2 = Integer.parseInt(sub1[i]);
            	    double billAmt2 = Double.parseDouble(billAmt[i]); 
            	 
            	    String tpayable2 = tpayable[i]; 
            	 //  String scrDate2= scrDate[i]; 
            	    // String tremarks2 = tremarks[i]; 
            	     Date billdate1,sandate1;
            	     Calendar c;
                        int sannno=Integer.parseInt(sanNo[i]);
                       // String sanndate=sanDate[i];
                         String billda=billdatt[i];
                       //  System.out.println("billda ---- "+billda);
                      
                     //  System.out.println("sanndate  -->"+ sanndate);
                                            
                       SL_NO++;
                      /*   	ps4 = con.prepareStatement("insert into  FAS_PASS_ORDER_TRN(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,PASS_ORDER_NO,SANCTION_PROC_NO,BILL_NO,BILL_DATE,BILL_AMOUNT,PAYABLE_TO,REMARKS,UPDATED_BY_USERID,UPDATED_DATE,SANCTION_PROC_DATE,SLNO) values(?,?,?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?,?,?,?,?,to_date(?,'dd/mm/yyyy'),?)");
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
            				
                         	System.out.println("sd22 "+sd2);*/
                         	                         	
		    				ps2 = con.prepareStatement("update FAS_BILL_REGISTER_MASTER set PASS_ORDER_DATE=?,PASS_ORDER_BY=?,PASS_ORDER_AMOUNT=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=? and BILL_SCRUTINY_DONE='Y' and MEMO_ENTRY='Y' ");
		    				ps2.setDate(1, PassOrderDate);
		    				ps2.setInt(2, PassOrderPreparedBye);
		    				ps2.setInt(3, TotalAmt);	
		    				ps2.setInt(4, unitid);
		    				ps2.setInt(5, offid);
		    				ps2.setInt(6, cbyear);
		    				ps2.setInt(7, cbmonth);
		    				ps2.setInt(8, billnumber1);				
		    				int sd0=ps2.executeUpdate();
		    				if(sd0==0)
		    				{
		    					ps2 = con.prepareStatement("update FAS_BILL_REGISTERNEW set PASS_ORDER_DATE=?,PASS_ORDER_BY=?,PASS_ORDER_AMOUNT=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=? and BILL_SCRUTINY_DONE='Y' and MEMO_ENTRY='Y' ");
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
		    				System.out.println(" sd0 "+sd0);
		    				
		    				   ched++;
                      }else if((verify_select_status[i].equals("UNCHECKED"))){
                    	  int billnumber1=Integer.parseInt(tnodebillno[i]); 
                      	System.out.println("billnumber1 "+billnumber1);
          		int tnodebillno2 =Integer.parseInt(tnodebillno[i]);
          	   // int major2 = Integer.parseInt(major1[i]); 
          	  //  int minor2= Integer.parseInt(minor1[i]); 
          	   // int sub2 = Integer.parseInt(sub1[i]);
          	    double billAmt2 = Double.parseDouble(billAmt[i]); 
          	 
          	  //  String tpayable2 = tpayable[i]; 
          	   // String scrDate2= scrDate[i]; 
          	    // String tremarks2 = tremarks[i]; 
          	   String billda=billdatt[i];
               //System.out.println("billda --billAmt2-- "+billda+"---"+billAmt2);
                    	   	ps4 = con.prepareStatement("delete from FAS_PASS_ORDER_TRN where ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? AND PASS_ORDER_NO=? AND BILL_NO=? AND BILL_AMOUNT=? AND BILL_DATE=to_date(?,'dd/mm/yyyy')");//AND BILL_DATE=to_date(?,'dd/mm/yyyy')
        					ps4.setInt(1, unitid);
            				ps4.setInt(2, offid);
            				
            				ps4.setInt(3, txtCash_year);
            				ps4.setInt(4, txtCash_Month_hid);
            				ps4.setInt(5, Pass_or_no);
            				ps4.setInt(6, tnodebillno2);	
            				ps4.setDouble(7, billAmt2);
            				ps4.setString(8, billda);
            				int sd2=ps4.executeUpdate();
            				
                         	System.out.println("sd22 "+sd2);
                    	  
                         	ps2 = con.prepareStatement("update FAS_BILL_REGISTER_MASTER set PASS_ORDER_DATE=?,PASS_ORDER_BY=?,PASS_ORDER_AMOUNT=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=? and BILL_SCRUTINY_DONE='Y' and MEMO_ENTRY='Y' ");
		    				ps2.setString(1, "");
		    				ps2.setString(2, "");
		    				ps2.setString(3, "");	
		    				ps2.setInt(4, unitid);
		    				ps2.setInt(5, offid);
		    				ps2.setInt(6, cbyear);
		    				ps2.setInt(7, cbmonth);
		    				ps2.setInt(8, billnumber1);				
		    				int sd21=ps2.executeUpdate();
		    				if(sd21==0)
		    				{
		    					ps2 = con.prepareStatement("update FAS_BILL_REGISTERNEW set PASS_ORDER_DATE=?,PASS_ORDER_BY=?,PASS_ORDER_AMOUNT=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=? and BILL_SCRUTINY_DONE='Y' and MEMO_ENTRY='Y' ");
			    				ps2.setString(1, "");
		    					ps2.setString(2, "");
			    				ps2.setString(3, "");	
			    				ps2.setInt(4, unitid);
			    				ps2.setInt(5, offid);
			    				ps2.setInt(6, cbyear);
			    				ps2.setInt(7, cbmonth);
			    				ps2.setInt(8, billnumber1);				
			    				int sd22=ps2.executeUpdate();	
		    				}
		    				System.out.println(" sd2222 "+sd21);
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
                   	sendMessage(response,"PassOrder Preparation is Prepared Updated Successfully","ok");   
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
