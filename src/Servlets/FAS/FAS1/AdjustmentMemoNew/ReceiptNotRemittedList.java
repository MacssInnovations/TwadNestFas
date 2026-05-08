package Servlets.FAS.FAS1.AdjustmentMemoNew;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;
import java.util.Calendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class ReceiptNotRemittedList
 */
public class ReceiptNotRemittedList extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";  
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReceiptNotRemittedList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		response.setContentType(CONTENT_TYPE);
		  String CONTENT_TYPE = "text/xml";
		  response.setHeader("Cache-Control", "no-cache");
		  PrintWriter out = response.getWriter();
		  String strType = "";
	      String xml="<response>";
		  
		  /*-------------------------- Session Checking -----------------------------*/
	        
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
	         
	         /*---------------------------------------------------------------------------*/
	         /*------------------------- Variables Declaration--------------------------- */
	         
	         Connection con=null;
	         ResultSet rs=null;
	         Statement stmt=null;
	         PreparedStatement ps=null;
	         PreparedStatement ps2=null;
	         
	         /*----------------------------------------------------------------------------*/
	        /*--------------------------------- Database Connection------------------------*/
	       
	         try {
	                          ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
	                          String ConnectionString="";

	                          String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
	                          String strdsn=rs1.getString("Config.DSN");
	                          String strhostname=rs1.getString("Config.HOST_NAME");
	                          String strportno=rs1.getString("Config.PORT_NUMBER");
	                          String strsid=rs1.getString("Config.SID");
	                          String strdbusername=rs1.getString("Config.USER_NAME");
	                          String strdbpassword=rs1.getString("Config.PASSWORD");
	                          ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	                          Class.forName(strDriver.trim());
	                          con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	         }
	         catch(Exception e)
	         {
	             System.out.println("Exception in opening connection :"+e);
	         }
	         
	        /*-----------------------------------------------------------------------------*/
	         
	         /* Get Command Parameter */     
	         try
	         {
	         	strType = request.getParameter("Command");
	         }
	         catch(Exception e)
	         {
	         	e.printStackTrace();
	         }
	
	         int txtCB_Year=0,count=0;
	         int txtCB_Month=0;
	         int cmbAcc_UnitCode=0;
	         int cmbOffice_code=0;
	         Date txtFrom_date=null;
	         Date txtTo_date=null;
	         Calendar c;
	         String sql="";
	         String txtReceipt_type="";
	         String cmbStatus="",cmbAdvice="";
	         
	         /* Accounting Unit ID */
	         try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	         catch(NumberFormatException e){System.out.println("exception"+e );}
	         System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
	         
	         /* Accounting For Office ID */
	         try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	         catch(NumberFormatException e){System.out.println("exception"+e );}
	         System.out.println("cmbOffice_code "+cmbOffice_code);
	         
	         
	         /* Get Cashbook Month and Year */
	         txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
	         txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));
	         
	         System.out.println("year..."+txtCB_Year);
	         System.out.println("Month..."+txtCB_Month);
	         if(strType.equalsIgnoreCase("searchByMonth"))  
	         {
	        	 
	        	 xml="<response><command>searchByMonth</command>"; 
	        	 try
	        	 {
	        	 String Sql=" select m.account_no, m.receipt_no,TO_CHAR(m.receipt_date,'DD/MM/YYYY') as receipt_date,trim(TO_CHAR(m.total_amount,'99999999999999.99'))AS TOTAL_AMOUNT from fas_receipt_master m " + 
	        	 			" where m.accounting_unit_id ="+ cmbAcc_UnitCode +" and m.ACCOUNTING_FOR_OFFICE_ID ="+cmbOffice_code+" and m.cashbook_year=" + txtCB_Year + " and m.cashbook_month=" + txtCB_Month +
	        			    " and m.receipt_status ='L' and m.remittance_in_curr_month ='Y' and m.remittance_status !='Y' order by m.account_no";
	        	 ps=con.prepareStatement(Sql);
	        	 rs=ps.executeQuery();
	        	 xml=xml+"<Ucode>"+cmbAcc_UnitCode+"</Ucode>" ;
	             xml=xml+"<Offid>"+cmbOffice_code+"</Offid>";
	             xml=xml+"<txtCB_Year>"+txtCB_Year+"</txtCB_Year>";
		         xml=xml+"<txtCB_Month>"+txtCB_Month+"</txtCB_Month>"; 
	        	 System.out.println("Sql>>>>>>>>>>>>>>>>>"+Sql);
	        	 while(rs.next())
	                {
	        		 xml=xml+"<leng>";
	        		 xml=xml+"<account_no>"+rs.getLong("account_no")+"</account_no>";
	        	  	 xml=xml+"<receipt_no>"+rs.getInt("receipt_no")+"</receipt_no>";
	                 xml=xml+"<receipt_date>"+rs.getString("receipt_date")+"</receipt_date>";
	                 xml=xml+"<total_amount>"+rs.getString("total_amount")+"</total_amount>";
	                 xml=xml+"</leng>";
	                    count++;
	                }
	        	 if(count==0) 
	               {
		                    System.out.println("inside count==0");
		                    xml=xml+"<flag>failure</flag>";
	               }
	        	 else
	        	 {
	        		 System.out.println("inside count>0");
	                    xml=xml+"<flag>success</flag>";
	        	 }
	        	 }catch(Exception e){
	        		 System.out.println("Query Exception is>>>>>"+e);
	        		 xml=xml+"<flag>failure</flag>";
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
