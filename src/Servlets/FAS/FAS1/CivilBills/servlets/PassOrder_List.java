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
 * Servlet implementation class PassOrder_List
 */
public class PassOrder_List extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    Connection connection = null; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public PassOrder_List() {
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

    	 try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
         catch(NumberFormatException e){System.out.println("exception"+e );}
         
         
         try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
         catch(NumberFormatException e){System.out.println("exception"+e );}
         
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
	    	  /*sql="  select pass_order_no,"+
				" to_char(PASS_ORDER_DATE,'dd/mm/yyyy')PASS_ORDER_DATE,"+
				" approved_by,"+
				" remarks,TO_CHAR(APPROVED_DATE,'dd/mm/yyyy')APPROVED_DATE,"+
				" pass_order_prepared_by,(select EMPLOYEE_NAME from HRM_MST_EMPLOYEES where EMPLOYEE_ID=pass_order_prepared_by )as EntryDesc from  fas_pass_order_mst where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and APPROVED_BY ='Y' "+optioiiid+" order by pass_order_no";
	            */
	    	   sql="SELECT mst.pass_order_no, " +
	    			   "  TO_CHAR(mst.PASS_ORDER_DATE,'dd/mm/yyyy')PASS_ORDER_DATE, " +
	    			   "  mst.approved_by, " +
	    			   "  mst.remarks, " +
	    			   "  mst.pass_order_prepared_by, " +
	    			   "  (SELECT EMPLOYEE_NAME " +
	    			   "  FROM HRM_MST_EMPLOYEES " +
	    			   "  WHERE EMPLOYEE_ID=mst.pass_order_prepared_by " +
	    			   "  )AS EntryDesc, " +
	    			   "  TO_CHAR(mst.APPROVED_DATE,'dd/mm/yyyy')APPROVED_DATE, " +
	    			   "  SUM(BILL_AMOUNT)AS amount " +
	    			   "FROM fas_pass_order_mst mst , " +
	    			   "  FAS_PASS_ORDER_TRN trn " +
	    			   "WHERE TRN.ACCOUNTING_FOR_OFFICE_ID = MST.ACCOUNTING_FOR_OFFICE_ID " +
	    			   "AND TRN.ACCOUNTING_UNIT_ID         = MST.ACCOUNTING_UNIT_ID " +
	    			   "AND TRN.CASHBOOK_MONTH             = MST.CASHBOOK_MONTH " +
	    			   "AND TRN.CASHBOOK_YEAR              = MST.CASHBOOK_YEAR " +
	    			   "AND TRN.PASS_ORDER_NO              = MST.PASS_ORDER_NO " +
	    			   "AND mst.ACCOUNTING_UNIT_ID         ="+cmbAcc_UnitCode+
	    			   "AND mst.CASHBOOK_YEAR              ="+txtCB_Year+
	    			   "AND mst.CASHBOOK_MONTH             ="+txtCB_Month+
	    			   " and mst.APPROVED_BY ='Y'  " +optioiiid+
	    			   "GROUP BY mst.pass_order_no, " +
	    			   "  mst.PASS_ORDER_DATE, " +
	    			   "  mst.approved_by, " +
	    			   "  mst.remarks, " +
	    			   "  mst.pass_order_prepared_by, " +
	    			   "  mst.APPROVED_DATE " +
	    			   "ORDER BY mst.pass_order_no";
	       }else if(billapprej1.equalsIgnoreCase("Rejected")){
	    	 /* sql="  select pass_order_no,"+
				" to_char(PASS_ORDER_DATE,'dd/mm/yyyy')PASS_ORDER_DATE,"+
				" approved_by,"+
				" remarks,TO_CHAR(APPROVED_DATE,'dd/mm/yyyy')APPROVED_DATE,"+
				" pass_order_prepared_by,(select EMPLOYEE_NAME from HRM_MST_EMPLOYEES where EMPLOYEE_ID=pass_order_prepared_by )as EntryDesc from  fas_pass_order_mst where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and APPROVED_BY ='N' "+optioiiid+" order by pass_order_no";
	            */
	    	   sql="SELECT mst.pass_order_no, " +
	    			   "  TO_CHAR(mst.PASS_ORDER_DATE,'dd/mm/yyyy')PASS_ORDER_DATE, " +
	    			   "  mst.approved_by, " +
	    			   "  mst.remarks, " +
	    			   "  mst.pass_order_prepared_by, " +
	    			   "  (SELECT EMPLOYEE_NAME " +
	    			   "  FROM HRM_MST_EMPLOYEES " +
	    			   "  WHERE EMPLOYEE_ID=mst.pass_order_prepared_by " +
	    			   "  )AS EntryDesc, " +
	    			   "  TO_CHAR(mst.APPROVED_DATE,'dd/mm/yyyy')APPROVED_DATE, " +
	    			   "  SUM(BILL_AMOUNT)AS amount " +
	    			   "FROM fas_pass_order_mst mst , " +
	    			   "  FAS_PASS_ORDER_TRN trn " +
	    			   "WHERE TRN.ACCOUNTING_FOR_OFFICE_ID = MST.ACCOUNTING_FOR_OFFICE_ID " +
	    			   "AND TRN.ACCOUNTING_UNIT_ID         = MST.ACCOUNTING_UNIT_ID " +
	    			   "AND TRN.CASHBOOK_MONTH             = MST.CASHBOOK_MONTH " +
	    			   "AND TRN.CASHBOOK_YEAR              = MST.CASHBOOK_YEAR " +
	    			   "AND TRN.PASS_ORDER_NO              = MST.PASS_ORDER_NO " +
	    			   "AND mst.ACCOUNTING_UNIT_ID         ="+cmbAcc_UnitCode+
	    			   "AND mst.CASHBOOK_YEAR              ="+txtCB_Year+
	    			   "AND mst.CASHBOOK_MONTH             ="+txtCB_Month+
	    			   " and mst.APPROVED_BY ='N'  " +optioiiid+
	    			   "GROUP BY mst.pass_order_no, " +
	    			   "  mst.PASS_ORDER_DATE, " +
	    			   "  mst.approved_by, " +
	    			   "  mst.remarks, " +
	    			   "  mst.pass_order_prepared_by, " +
	    			   "  mst.APPROVED_DATE " +
	    			   "ORDER BY mst.pass_order_no";
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
						                    xml=xml+"<pass_order_no>"+rs.getInt("pass_order_no")+"</pass_order_no>";						                  
						                    xml=xml+"<PASS_ORDER_DATE>"+rs.getString("PASS_ORDER_DATE")+"</PASS_ORDER_DATE>";
						                    xml=xml+"<approved_by>"+rs.getString("approved_by")+"</approved_by>";	                
						                    xml=xml+"<pass_order_prepared_by>"+rs.getString("EntryDesc") +"</pass_order_prepared_by>";
						                    xml=xml+"<remarks><![CDATA["+rs.getString("remarks")+"]]></remarks>";
						                    xml=xml+"<APPROVED_DATE>"+rs.getString("APPROVED_DATE") +"</APPROVED_DATE>";
						                    xml=xml+"<amount>"+rs.getString("amount") +"</amount>";
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

	    	 try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	         catch(NumberFormatException e){System.out.println("exception"+e );}
	         
	         
	         try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	         catch(NumberFormatException e){System.out.println("exception"+e );}
	         
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
		    	  /*sql="  select pass_order_no,"+
					" to_char(PASS_ORDER_DATE,'dd/mm/yyyy')PASS_ORDER_DATE,"+
					" approved_by,"+
					" remarks,"+
					" pass_order_prepared_by," +
					"(select EMPLOYEE_NAME from HRM_MST_EMPLOYEES where " +
					"EMPLOYEE_ID=pass_order_prepared_by )as EntryDesc,TO_CHAR(APPROVED_DATE,'dd/mm/yyyy')APPROVED_DATE from " +
					" fas_pass_order_mst" +
					" where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and CASHBOOK_YEAR="+txtCB_Year+"" +
							" and CASHBOOK_MONTH="+txtCB_Month+optioiiid+" order by pass_order_no";
		           */ 
		  sql="SELECT mst.pass_order_no, " +
			    			   "  TO_CHAR(mst.PASS_ORDER_DATE,'dd/mm/yyyy')PASS_ORDER_DATE, " +
			    			   "  mst.approved_by, " +
			    			   "  mst.remarks, " +
			    			   "  mst.pass_order_prepared_by, " +
			    			   "  trn.BILL_NO, " +
			    			   "  TRN.BILL_AMOUNT, " +
			    			   "  TO_CHAR(TRN.BILL_DATE,'dd/mm/yyyy')BILL_DATE," +
			    			   "  (SELECT EMPLOYEE_NAME " +
			    			   "  FROM HRM_MST_EMPLOYEES " +
			    			   "  WHERE EMPLOYEE_ID=mst.pass_order_prepared_by " +
			    			   "  )AS EntryDesc, " +
			    			   "  TO_CHAR(mst.APPROVED_DATE,'dd/mm/yyyy')APPROVED_DATE, " +
			    			   "  SUM(BILL_AMOUNT)AS amount " +
			    			   "FROM fas_pass_order_mst mst , " +
			    			   "  FAS_PASS_ORDER_TRN trn " +
			    			   "WHERE TRN.ACCOUNTING_FOR_OFFICE_ID = MST.ACCOUNTING_FOR_OFFICE_ID " +
			    			   " AND TRN.ACCOUNTING_UNIT_ID         = MST.ACCOUNTING_UNIT_ID " +
			    			   " AND TRN.CASHBOOK_MONTH             = MST.CASHBOOK_MONTH " +
			    			   " AND TRN.CASHBOOK_YEAR              = MST.CASHBOOK_YEAR " +
			    			   " AND TRN.PASS_ORDER_NO              = MST.PASS_ORDER_NO " +
			    			   " AND mst.ACCOUNTING_UNIT_ID         ="+cmbAcc_UnitCode+
			    			   " AND mst.CASHBOOK_YEAR              ="+txtCB_Year+
			    			   " AND mst.CASHBOOK_MONTH             ="+txtCB_Month+
			    			optioiiid+
			    			   "GROUP BY mst.pass_order_no, " +
			    			   "  mst.PASS_ORDER_DATE, " +
			    			   "  mst.approved_by, " +
			    			   "  mst.remarks, " +
			    			   "  mst.pass_order_prepared_by, " +
			    			   "  trn.BILL_NO, " +
			    			   "  TRN.BILL_AMOUNT, " +
			    			   "  TRN.BILL_DATE, " +
			    			   "  mst.APPROVED_DATE " +
			    			   "ORDER BY mst.pass_order_no";
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
							                    xml=xml+"<pass_order_no>"+rs.getInt("pass_order_no")+"</pass_order_no>";						                  
							                    xml=xml+"<PASS_ORDER_DATE>"+rs.getString("PASS_ORDER_DATE")+"</PASS_ORDER_DATE>";
							                    xml=xml+"<pass_order_prepared_by>"+rs.getString("EntryDesc") +"</pass_order_prepared_by>";
							                    xml=xml+"<amount>"+rs.getString("amount") +"</amount>";
							                    xml=xml+"<BILL_NO>"+rs.getInt("BILL_NO") +"</BILL_NO>";
							                    xml=xml+"<BILL_AMOUNT>"+rs.getString("BILL_AMOUNT") +"</BILL_AMOUNT>";
							                    xml=xml+"<BILL_DATE>"+rs.getString("BILL_DATE") +"</BILL_DATE>";
							                    xml=xml+"<approved_by>"+rs.getString("approved_by")+"</approved_by>";	     
							                    xml=xml+"<remarks><![CDATA["+rs.getString("remarks")+"]]></remarks>";
							                    
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
						                	System.out.println("failure Flag");
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
	        out.print(xml); 
	        System.out.println(xml); 
	
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
