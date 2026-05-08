package Servlets.FAS.FAS1.CivilBills.servlets;

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
 * Servlet implementation class PassOrder_List_All
 */
public class PassOrder_List_All extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    Connection connection = null;    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PassOrder_List_All() {
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
	         
	      //  System.out.println("servlet called");
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
	        {

  
       
       txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
       txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));        
      String billapprej1=request.getParameter("billapprej");
      
      
      
      String optionId=request.getParameter("optionId");
      String optioiiid="";
      
     if(optionId.equalsIgnoreCase("live")){
   	  optioiiid=" and STATUS='L'";
     }else if(optionId.equalsIgnoreCase("cancel")){
   	  optioiiid="  and STATUS='C'";
     }
    //  System.out.println("billapprej1  "+billapprej1);
	            xml="<response><command>searchByMonth</command>"; 
	            String sql="";
	       if(billapprej1.equalsIgnoreCase("Approved")){
	    	 /* sql="  select " +
	    	  " ACCOUNTING_UNIT_ID, "+
	    	 " ACCOUNTING_FOR_OFFICE_ID, "+
	    	   " (select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS f1 where f1.ACCOUNTING_UNIT_ID= f.ACCOUNTING_UNIT_ID  )as UnitName, "+
	    	  	           " (select OFFICE_NAME from COM_MST_OFFICES f2 where f2.OFFICE_ID=f.ACCOUNTING_FOR_OFFICE_ID) as OfficeName, "+
	    	  		"pass_order_no,"+
				" to_char(PASS_ORDER_DATE,'dd/mm/yyyy')PASS_ORDER_DATE,"+
				" approved_by,"+
				" remarks,"+
				" pass_order_prepared_by,(select EMPLOYEE_NAME from HRM_MST_EMPLOYEES where EMPLOYEE_ID=pass_order_prepared_by )as EntryDesc " +
				"from  fas_pass_order_mst f " +
				"where CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and APPROVED_BY ='Y' "+optioiiid +" order by ACCOUNTING_UNIT_ID,pass_order_no";
	            */
	    	   sql="SELECT f.ACCOUNTING_UNIT_ID, " +
	            		"  f.ACCOUNTING_FOR_OFFICE_ID, " +
	            		"  (SELECT ACCOUNTING_UNIT_NAME " +
	            		"  FROM FAS_MST_ACCT_UNITS f1 " +
	            		"  WHERE f1.ACCOUNTING_UNIT_ID= f.ACCOUNTING_UNIT_ID " +
	            		"  )AS UnitName, " +
	            		"  (SELECT OFFICE_NAME " +
	            		"  FROM COM_MST_OFFICES f2 " +
	            		"  WHERE f2.OFFICE_ID=f.ACCOUNTING_FOR_OFFICE_ID " +
	            		"  ) AS OfficeName, " +
	            		"  f.pass_order_no, " +
	            		"  TO_CHAR(f.PASS_ORDER_DATE,'dd/mm/yyyy')PASS_ORDER_DATE, " +
	            		"  f.approved_by, " +
	            		"  f.remarks, " +
	            		"  f.pass_order_prepared_by, " +
	            		"  (SELECT EMPLOYEE_NAME " +
	            		"  FROM HRM_MST_EMPLOYEES " +
	            		"  WHERE EMPLOYEE_ID=pass_order_prepared_by " +
	            		"  )                 AS EntryDesc, " +
	            		"  SUM(t.bill_amount)AS amount " +
	            		"FROM fas_pass_order_mst f, " +
	            		"  fas_pass_order_trn t " +
	            		"WHERE T.ACCOUNTING_FOR_OFFICE_ID = F.ACCOUNTING_FOR_OFFICE_ID " +
	            		"AND T.ACCOUNTING_UNIT_ID         = F.ACCOUNTING_UNIT_ID " +
	            		"AND T.CASHBOOK_MONTH             = F.CASHBOOK_MONTH " +
	            		"AND T.CASHBOOK_YEAR              = F.CASHBOOK_YEAR " +
	            		"AND T.PASS_ORDER_NO              = F.PASS_ORDER_NO " +
	            		"  and APPROVED_BY ='Y'  AND f.CASHBOOK_YEAR              = " +txtCB_Year+
	            		"AND f.CASHBOOK_MONTH             ="+txtCB_Month+optioiiid+
	            		"GROUP BY f.ACCOUNTING_UNIT_ID, " +
	            		"  f.ACCOUNTING_FOR_OFFICE_ID, " +
	            		"  f.pass_order_no, " +
	            		"  f.PASS_ORDER_DATE, " +
	            		"  f.approved_by, " +
	            		"  f.remarks, " +
	            		"  f.pass_order_prepared_by " +
	            		"ORDER BY f.ACCOUNTING_UNIT_ID, " +
	            		"  f.pass_order_no";
	       }else if(billapprej1.equalsIgnoreCase("Rejected")){
/*	    	  sql="  select " +
	    	 " ACCOUNTING_UNIT_ID, "+
	    	"  ACCOUNTING_FOR_OFFICE_ID, "+
	    	   " (select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS f1 where f1.ACCOUNTING_UNIT_ID= f.ACCOUNTING_UNIT_ID  )as UnitName, "+
	    	  	           " (select OFFICE_NAME from COM_MST_OFFICES f2 where f2.OFFICE_ID=f.ACCOUNTING_FOR_OFFICE_ID) as OfficeName, "+
	    	  		" pass_order_no,"+
				" to_char(PASS_ORDER_DATE,'dd/mm/yyyy')PASS_ORDER_DATE,"+
				" approved_by,"+
				" remarks,"+
				" pass_order_prepared_by,(select EMPLOYEE_NAME from HRM_MST_EMPLOYEES " +
				"where EMPLOYEE_ID=pass_order_prepared_by )as EntryDesc from  fas_pass_order_mst f " +
				"where CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and APPROVED_BY ='N' "+optioiiid+" order by ACCOUNTING_UNIT_ID,pass_order_no";
	            */
	    	   
	    	   sql="SELECT f.ACCOUNTING_UNIT_ID, " +
	            		"  f.ACCOUNTING_FOR_OFFICE_ID, " +
	            		"  (SELECT ACCOUNTING_UNIT_NAME " +
	            		"  FROM FAS_MST_ACCT_UNITS f1 " +
	            		"  WHERE f1.ACCOUNTING_UNIT_ID= f.ACCOUNTING_UNIT_ID " +
	            		"  )AS UnitName, " +
	            		"  (SELECT OFFICE_NAME " +
	            		"  FROM COM_MST_OFFICES f2 " +
	            		"  WHERE f2.OFFICE_ID=f.ACCOUNTING_FOR_OFFICE_ID " +
	            		"  ) AS OfficeName, " +
	            		"  f.pass_order_no, " +
	            		"  TO_CHAR(f.PASS_ORDER_DATE,'dd/mm/yyyy')PASS_ORDER_DATE, " +
	            		"  f.approved_by, " +
	            		"  f.remarks, " +
	            		"  f.pass_order_prepared_by, " +
	            		"  (SELECT EMPLOYEE_NAME " +
	            		"  FROM HRM_MST_EMPLOYEES " +
	            		"  WHERE EMPLOYEE_ID=pass_order_prepared_by " +
	            		"  )                 AS EntryDesc, " +
	            		"  SUM(t.bill_amount)AS amount " +
	            		"FROM fas_pass_order_mst f, " +
	            		"  fas_pass_order_trn t " +
	            		"WHERE T.ACCOUNTING_FOR_OFFICE_ID = F.ACCOUNTING_FOR_OFFICE_ID " +
	            		"AND T.ACCOUNTING_UNIT_ID         = F.ACCOUNTING_UNIT_ID " +
	            		"AND T.CASHBOOK_MONTH             = F.CASHBOOK_MONTH " +
	            		"AND T.CASHBOOK_YEAR              = F.CASHBOOK_YEAR " +
	            		"AND T.PASS_ORDER_NO              = F.PASS_ORDER_NO " +
	            		"  and APPROVED_BY ='Y'  AND f.CASHBOOK_YEAR              = " +txtCB_Year+
	            		"AND f.CASHBOOK_MONTH             ="+txtCB_Month+optioiiid+
	            		"GROUP BY f.ACCOUNTING_UNIT_ID, " +
	            		"  f.ACCOUNTING_FOR_OFFICE_ID, " +
	            		"  f.pass_order_no, " +
	            		"  f.PASS_ORDER_DATE, " +
	            		"  f.approved_by, " +
	            		"  f.remarks, " +
	            		"  f.pass_order_prepared_by " +
	            		"ORDER BY f.ACCOUNTING_UNIT_ID, " +
	            		"  f.pass_order_no";
	       }
	           
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
						                    xml=xml+"<UnitName><![CDATA["+rs.getString("UnitName")+"]]></UnitName>";
						                    
						                    xml=xml+"<accounting_for_office_id>"+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+"</accounting_for_office_id>";						                  
						                    xml=xml+"<OfficeName><![CDATA["+rs.getString("OfficeName")+"]]></OfficeName>";
						                    
						                    xml=xml+"<pass_order_no>"+rs.getInt("pass_order_no")+"</pass_order_no>";						                  
						                    xml=xml+"<PASS_ORDER_DATE>"+rs.getString("PASS_ORDER_DATE")+"</PASS_ORDER_DATE>";
						                    xml=xml+"<approved_by>"+rs.getString("approved_by")+"</approved_by>";	                
						                    xml=xml+"<pass_order_prepared_by>"+rs.getString("EntryDesc") +"</pass_order_prepared_by>";
						                    xml=xml+"<remarks><![CDATA["+rs.getString("remarks")+"]]></remarks>";
						                    xml=xml+"<amount>"+rs.getString("amount")+"</amount>";
						                    xml=xml+"</leng>";
						                    count++;
					                }
					                if(count>0) 
					                {
						                   // System.out.println("inside count==0");
					                	  xml=xml+"<billapprej1>"+billapprej1+"</billapprej1>";
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
          
         
			 
	        } 
		 else  if(strType.equalsIgnoreCase("searchByMonthPrepared"))  
	        {

	    	
	         
	         txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
	         txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));        
	      //  String billapprej1=request.getParameter("billapprej");
	        
	        
	        
	        String optionId=request.getParameter("optionId");
	        String optioiiid="";
	        
	       if(optionId.equalsIgnoreCase("live")){
	     	  optioiiid=" and STATUS='L'";
	       }else if(optionId.equalsIgnoreCase("cancel")){
	     	  optioiiid="  and STATUS='C'";
	       }
	      //  System.out.println("billapprej1  "+billapprej1);
		            xml="<response><command>searchByMonthPrepared</command>"; 
		            String sql="";
		      // if(billapprej1.equalsIgnoreCase("Approved")){
		    	 /* sql="  select " +
		    	  " ACCOUNTING_UNIT_ID, "+
			    	"  ACCOUNTING_FOR_OFFICE_ID, "+
			    	   " (select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS f1 where f1.ACCOUNTING_UNIT_ID= f.ACCOUNTING_UNIT_ID  )as UnitName, "+
			    	 " (select OFFICE_NAME from COM_MST_OFFICES f2 where f2.OFFICE_ID=f.ACCOUNTING_FOR_OFFICE_ID) as OfficeName, "+
			    	
		    	  		"pass_order_no,"+
					" to_char(PASS_ORDER_DATE,'dd/mm/yyyy')PASS_ORDER_DATE,"+
					" approved_by,"+
					" remarks,"+
					" pass_order_prepared_by," +
					"(select EMPLOYEE_NAME from HRM_MST_EMPLOYEES where " +
					"EMPLOYEE_ID=pass_order_prepared_by )as EntryDesc from " +
					" fas_pass_order_mst f " +
					" where CASHBOOK_YEAR="+txtCB_Year+"" +
							" and CASHBOOK_MONTH="+txtCB_Month+optioiiid+" order by ACCOUNTING_UNIT_ID,pass_order_no";
		            */
		            sql="SELECT f.ACCOUNTING_UNIT_ID, " +
		            		"  f.ACCOUNTING_FOR_OFFICE_ID, " +
		            		"  (SELECT ACCOUNTING_UNIT_NAME " +
		            		"  FROM FAS_MST_ACCT_UNITS f1 " +
		            		"  WHERE f1.ACCOUNTING_UNIT_ID= f.ACCOUNTING_UNIT_ID " +
		            		"  )AS UnitName, " +
		            		"  (SELECT OFFICE_NAME " +
		            		"  FROM COM_MST_OFFICES f2 " +
		            		"  WHERE f2.OFFICE_ID=f.ACCOUNTING_FOR_OFFICE_ID " +
		            		"  ) AS OfficeName, " +
		            		"  f.pass_order_no, " +
		            		"  TO_CHAR(f.PASS_ORDER_DATE,'dd/mm/yyyy')PASS_ORDER_DATE, " +
		            		"  f.approved_by, " +
		            		"  f.remarks, " +
		            		"  f.pass_order_prepared_by, " +
		            		"  (SELECT EMPLOYEE_NAME " +
		            		"  FROM HRM_MST_EMPLOYEES " +
		            		"  WHERE EMPLOYEE_ID=pass_order_prepared_by " +
		            		"  )                 AS EntryDesc, " +
		            		"  SUM(t.bill_amount)AS amount " +
		            		"FROM fas_pass_order_mst f, " +
		            		"  fas_pass_order_trn t " +
		            		"WHERE T.ACCOUNTING_FOR_OFFICE_ID = F.ACCOUNTING_FOR_OFFICE_ID " +
		            		"AND T.ACCOUNTING_UNIT_ID         = F.ACCOUNTING_UNIT_ID " +
		            		"AND T.CASHBOOK_MONTH             = F.CASHBOOK_MONTH " +
		            		"AND T.CASHBOOK_YEAR              = F.CASHBOOK_YEAR " +
		            		"AND T.PASS_ORDER_NO              = F.PASS_ORDER_NO " +
		            		"AND f.CASHBOOK_YEAR              = " +txtCB_Year+
		            		"AND f.CASHBOOK_MONTH             ="+txtCB_Month+optioiiid+
		            		"GROUP BY f.ACCOUNTING_UNIT_ID, " +
		            		"  f.ACCOUNTING_FOR_OFFICE_ID, " +
		            		"  f.pass_order_no, " +
		            		"  f.PASS_ORDER_DATE, " +
		            		"  f.approved_by, " +
		            		"  f.remarks, " +
		            		"  f.pass_order_prepared_by " +
		            		"ORDER BY f.ACCOUNTING_UNIT_ID, " +
		            		"  f.pass_order_no";
		     //  }else if(billapprej1.equalsIgnoreCase("Rejected")){
		    	 /* sql="  select pass_order_no,"+
					" to_char(PASS_ORDER_DATE,'dd/mm/yyyy')PASS_ORDER_DATE,"+
					" approved_by,"+
					" remarks,"+
					" pass_order_prepared_by,(select EMPLOYEE_NAME from HRM_MST_EMPLOYEES where EMPLOYEE_ID=pass_order_prepared_by )as EntryDesc from  fas_pass_order_mst where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and APPROVED_BY ='N' "+optioiiid;
		            */
		     //  }
		           
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
							                    xml=xml+"<UnitName><![CDATA["+rs.getString("UnitName")+"]]></UnitName>";
							                    
							                    xml=xml+"<accounting_for_office_id>"+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+"</accounting_for_office_id>";						                  
							                    xml=xml+"<OfficeName><![CDATA["+rs.getString("OfficeName")+"]]></OfficeName>";
							                    
							                    xml=xml+"<pass_order_no>"+rs.getInt("pass_order_no")+"</pass_order_no>";						                  
							                    xml=xml+"<PASS_ORDER_DATE>"+rs.getString("PASS_ORDER_DATE")+"</PASS_ORDER_DATE>";
							                    xml=xml+"<approved_by>"+rs.getString("approved_by")+"</approved_by>";	                
							                    xml=xml+"<pass_order_prepared_by>"+rs.getString("EntryDesc") +"</pass_order_prepared_by>";
							                    xml=xml+"<remarks><![CDATA["+rs.getString("remarks")+"]]></remarks>";
							                    xml=xml+"<amount>"+rs.getString("amount")+"</amount>";
							                    xml=xml+"</leng>";
							                    count++;
						                }
						                if(count>0) 
						                {
							                   // System.out.println("inside count==0");
						                	 // xml=xml+"<billapprej1>"+billapprej1+"</billapprej1>";
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
