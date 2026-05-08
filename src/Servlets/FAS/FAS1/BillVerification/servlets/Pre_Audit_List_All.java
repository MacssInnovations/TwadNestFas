package Servlets.FAS.FAS1.BillVerification.servlets;

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
 * Servlet implementation class Pre_Audit_List_All
 */
public class Pre_Audit_List_All extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    Connection connection = null;    
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Pre_Audit_List_All() {
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
 	  optioiiid=" and f.STATUS='L'";
   }else if(optionId.equalsIgnoreCase("cancel")){
 	  optioiiid=" and f.STATUS='C'";
   }
    
    
  //  System.out.println("billapprej1  "+billapprej1);
	            xml="<response><command>searchByMonth</command>"; 
	            String sql="";
	           
	            
	         	    	  sql="  select CHECK_LIST_CODE,"+
	         	    	 " ACCOUNTING_UNIT_ID, "+
	 			    	"  ACCOUNTING_FOR_OFFICE_ID, "+
	 			    	   " (select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS f1 where f1.ACCOUNTING_UNIT_ID= f.ACCOUNTING_UNIT_ID  )as UnitName, "+
	 			    	 " (select OFFICE_NAME from COM_MST_OFFICES f2 where f2.OFFICE_ID=f.ACCOUNTING_FOR_OFFICE_ID) as OfficeName, "+
	 			    	
	         	    	" (select CHECK_LIST_DESC from fas_pre_audit_chklst_mst p where p.accounting_unit_id=f.ACCOUNTING_UNIT_ID and "+
	         	    		"	p.cashbook_year="+txtCB_Year+" and "+
	         	    		"	p.CASHBOOK_MONTH="+txtCB_Month+"  and p.STATUS='L' and p.CHECK_LIST_CODE=f.CHECK_LIST_CODE) as checkDesc, "+
				" to_char(PRE_AUDIT_DATE,'dd/mm/yyyy')PRE_AUDIT_DATE,"+
				" BILLNO,"+
	           " to_char(BILL_DATE,'dd/mm/yyyy')BILL_DATE,"+ 
	            " BILL_AMOUNT , "+
				" BILL_MAJOR_TYPE_CODE,"+
				" BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,PRE_AUDIT_BY," +
				
				
				" (select BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES b1 where b1.BILL_MAJOR_TYPE_CODE=f.BILL_MAJOR_TYPE_CODE)as MajorDesc , "+

				"(select bill_minor_type_desc from fas_bill_minor_types_mst b2 where b2.bill_minor_type_code=f.BILL_MINOR_TYPE_CODE and b2.bill_major_type_code=f.BILL_MAJOR_TYPE_CODE) as MinorDesc, "+


					"(select  bill_sub_type_desc  FROM  FAS_BILL_SUB_TYPES b3 where b3.BILL_MINOR_TYPE_CODE=f.BILL_MINOR_TYPE_CODE and b3.BILL_MAJOR_TYPE_CODE=f.BILL_MAJOR_TYPE_CODE and b3.BILL_SUB_TYPE_CODE=f.BILL_SUB_TYPE_CODE) as SubDesc,"+
				
				"(select EMPLOYEE_NAME from HRM_MST_EMPLOYEES where EMPLOYEE_ID=f.PRE_AUDIT_BY )as EntryDesc from " +
				" FAS_PRE_AUDIT_CHECK_NEW f where CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+optioiiid+" order by ACCOUNTING_UNIT_ID,CHECK_LIST_CODE";
	            
	      
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
						                    
						                    xml=xml+"<BILLNO>"+rs.getInt("BILLNO")+"</BILLNO>";			
						                    xml=xml+"<BILL_DATE>"+rs.getString("BILL_DATE")+"</BILL_DATE>";
						                    xml=xml+"<BILL_AMOUNT>"+rs.getString("BILL_AMOUNT")+"</BILL_AMOUNT>";
						                    
						                    xml=xml+"<PRE_AUDIT_DATE>"+rs.getString("PRE_AUDIT_DATE")+"</PRE_AUDIT_DATE>";
						                    xml=xml+"<CHECK_LIST_CODE>"+rs.getString("CHECK_LIST_CODE")+"</CHECK_LIST_CODE>";	                
						                    xml=xml+"<PRE_AUDIT_BY>"+rs.getString("EntryDesc") +"</PRE_AUDIT_BY>";
						                    xml=xml+"<MajorDesc><![CDATA["+rs.getString("MajorDesc")+"]]></MajorDesc>";
						                    xml=xml+"<MinorDesc><![CDATA["+rs.getString("MinorDesc")+"]]></MinorDesc>";
						                    xml=xml+"<SubDesc><![CDATA["+rs.getString("SubDesc")+"]]></SubDesc>";
						                    xml=xml+"<checkDesc><![CDATA["+rs.getString("checkDesc")+"]]></checkDesc>";
						                    
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
