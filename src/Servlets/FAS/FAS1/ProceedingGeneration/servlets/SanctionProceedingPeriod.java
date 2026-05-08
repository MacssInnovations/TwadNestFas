package Servlets.FAS.FAS1.ProceedingGeneration.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ResourceBundle;



import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;




import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class DrawingOfficerDetails
 */
public class SanctionProceedingPeriod extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	PreparedStatement ps1 = null;
	ResultSet rs1 = null;
	PreparedStatement ps2 = null;
	ResultSet rs2 = null;
       
     public SanctionProceedingPeriod() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

		protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	// TODO Auto-generated method stub
			PrintWriter out = response.getWriter();
	   HttpSession session=request.getSession(true);
	    String cmd=request.getParameter("command");
	    String unit_id=request.getParameter("unit_id");
	    String office_id=request.getParameter("office_id");
	    String cyear=request.getParameter("cyear");
	    String cmonth=request.getParameter("cmonth");
	    String mtcno=request.getParameter("mtcno");
	    String passno=request.getParameter("passno");
	    String prono=request.getParameter("prono");
	    String billno=request.getParameter("billno");
	    
	    System.out.println(cmd);
	  
	   
	   
        String xml="";
        try {
			ResourceBundle rsb = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rsb.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rsb.getString("Config.DSN");
			String strhostname = rsb.getString("Config.HOST_NAME");
			String strportno = rsb.getString("Config.PORT_NUMBER");
			String strsid = rsb.getString("Config.SID");
			String strdbusername = rsb.getString("Config.USER_NAME");
			String strdbpassword = rsb.getString("Config.PASSWORD");

			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString,
					strdbusername.trim(), strdbpassword.trim());
			
		} catch (Exception e) {
			System.out.println("Exception in openeing connection:" + e);
		}
		if(cmd.equalsIgnoreCase("checkpayment"))
		{
			System.out.println("enter into the check payment@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@@");
			
			 xml=xml+"<response><command>checkpayment</command>";
				
			   String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
				int accno=Integer.parseInt(cmbAcc_UnitCode);
				System.out.println("values1..............................."+accno);
				
				
				
			   String cmbOffice_code=request.getParameter("cmbOffice_code");
				int officecode=Integer.parseInt(cmbOffice_code);
				System.out.println("values2..............................."+officecode);
			   
			   
			   
			   
			   
			   
			   String cmbMajorType=request.getParameter("cmbMajorType");
				int majortype=Integer.parseInt(cmbMajorType);
				System.out.println("values.5.............................."+majortype);
			   
			   
			   String cmbMinorType=request.getParameter("cmbMinorType");
			    int minortype=Integer.parseInt(cmbMinorType);
				System.out.println("values.6.............................."+minortype);
			   
			   
			   
			    
			    
			      String payeecode=request.getParameter("payeecode");
				   int payeecode1=Integer.parseInt(payeecode);
					System.out.println("values.9.............................."+payeecode1);
					if(majortype==1&&minortype==1)
					{
					try
			 		   {
						xml=xml+"<type>rent</type>";
			 		    ps=con.prepareStatement("select * from FAS_RENT_MASTER where  ACCOUNTING_UNIT_ID='"+accno+"' and ACCOUNTING_FOR_OFFICE_ID='"+officecode+"' and OWNER_CODE='"+payeecode1+"'");
			 		   rs= ps.executeQuery();
			 		
			 		   if(rs.next())
			 		   {
			 			  xml=xml+"<option>"+rs.getString("RENTAL_OPTION")+"</option>";
			 			  xml=xml+"<payment1>"+rs.getInt("PAYMENT_MONTH1")+"</payment1>";
			 			  xml=xml+"<payment2>"+rs.getInt("PAYMENT_MONTH2")+"</payment2>";
			 			  xml=xml+"<payment3>"+rs.getInt("PAYMENT_MONTH3")+"</payment3>";
			 			  xml=xml+"<payment4>"+rs.getInt("PAYMENT_MONTH4")+"</payment4>";
			 			  xml=xml+"<flag>success</flag>";
			 			   
			 		   }
			 		   else
			 		   {
			 			  xml=xml+"<flag>nodata</flag>";
			 		   }
			 			   
			 		   
			 			
			 		}
			 		   catch(Exception e)
			 		   {
			 			  xml=xml+"<flag>failure</flag>";
							System.out.println(e);
						}
					}
					else
					{
						xml=xml+"<type>norent</type>";
					}
						
						xml = xml + "</response>";
						System.out.println(xml);
			
			
			
		}
		else if(cmd.equalsIgnoreCase("add"))
		{
			
			
			
			System.out.println("enter into add function");
			
              int autonumber=0;
              int autonumber1=0;
			
			try
 		   {
 		
 		    ps=con.prepareStatement("select max(SANCTION_PROCEEDING_NO)from FAS_SANC_PROC_RENT_MST");
 		   rs= ps.executeQuery();
 		 
 		   if(rs.next())
 		   {
 			   
 			   if(rs.getString(1)==null)
 			   {
 				   autonumber=1;
 				  System.out.println("auto----------------"+autonumber);
 			   }
 			   else
 			   {
 			   System.out.println("auto----------------"+rs.getString(1));
 			   autonumber=Integer.parseInt(rs.getString(1))+1;
 			   }
 			   
 			   
 		   }
 			
 		}
 		   catch(Exception e)
 		   {
 			   System.out.println(e);
 		   }
 		
			
			
 		  xml=xml+"<response><command>add</command>";
			
		   String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
			int accno=Integer.parseInt(cmbAcc_UnitCode);
			System.out.println("values1..............................."+accno);
			
			
			
		   String cmbOffice_code=request.getParameter("cmbOffice_code");
			int officecode=Integer.parseInt(cmbOffice_code);
			System.out.println("values2..............................."+officecode);
		   
		   
		   String cmbCashBookYear=request.getParameter("cmbCashBookYear");
			int cashbookyear=Integer.parseInt(cmbCashBookYear);
			System.out.println("values3..............................."+cashbookyear);
		   
		   String cmbCashBookMonth=request.getParameter("cmbCashBookMonth");
			int cashbookmonth=Integer.parseInt(cmbCashBookMonth);
			System.out.println("values.4.............................."+cashbookmonth);
		   
		   
		   
		   
		   String cmbMajorType=request.getParameter("cmbMajorType");
			int majortype=Integer.parseInt(cmbMajorType);
			System.out.println("values.5.............................."+majortype);
		   
		   
		   String cmbMinorType=request.getParameter("cmbMinorType");
		    int minortype=Integer.parseInt(cmbMinorType);
			System.out.println("values.6.............................."+minortype);
		   
		   
		   
		   String cmbBillSubType=request.getParameter("cmbBillSubType");
		   int billtype=Integer.parseInt(cmbBillSubType);
			System.out.println("values.7.............................."+billtype);
		   
			String payeetype=request.getParameter("payeetype");
		    System.out.println("values.8.............................."+payeetype);
		    
		    
		    String payeecode=request.getParameter("payeecode");
			   int payeecode1=Integer.parseInt(payeecode);
				System.out.println("values.9.............................."+payeecode1);
		    
		    
		   
		   String txtRefNo=request.getParameter("txtRefNo");
		   int refno=Integer.parseInt(txtRefNo);
			System.out.println("values.10.............................."+refno);
		   
		   
		   
		   String txtRefDate=request.getParameter("txtRefDate");
		   java.sql.Date refDate = null;
		   java.sql.Date sanctionproceedingdate = null;
		   java.sql.Date fromdate = null;
		   java.sql.Date todate = null;
		   
		    java.util.GregorianCalendar c2;
			
			String[] ref_Date= txtRefDate.split("/");
			c2 = new java.util.GregorianCalendar(Integer.parseInt(ref_Date[2]),
		    Integer.parseInt(ref_Date[1]) - 1, Integer.parseInt(ref_Date[0]));
			java.util.Date ddd = c2.getTime();
			refDate = new Date(ddd.getTime());
			System.out.println("values.11.............................."+refDate);
		   

		    String txtSanctionProceedingDate=request.getParameter("txtSanctionProceedingDate");
		   
		   String[] sanction_proceedingdate= txtSanctionProceedingDate.split("/");
			c2 = new java.util.GregorianCalendar(Integer.parseInt(sanction_proceedingdate[2]),
		    Integer.parseInt(sanction_proceedingdate[1]) - 1, Integer.parseInt(sanction_proceedingdate[0]));
			java.util.Date ddd1 = c2.getTime();
			sanctionproceedingdate = new Date(ddd1.getTime());

			System.out.println("values.12.............................."+sanctionproceedingdate);
			
		   
		   String cmbSanctionAutrority=request.getParameter("cmbSanctionAutrority");
		   int autrority=Integer.parseInt(cmbSanctionAutrority);
			System.out.println("values.13.............................."+autrority);
		   
		   
		   
		   
		   String cmbSanctionBy=request.getParameter("cmbSanctionBy");
		   int sanctionBy=Integer.parseInt(cmbSanctionBy);
			System.out.println("values.14.............................."+sanctionBy);
		   
			 
			String txtSanctionProceedingFromDate=request.getParameter("txtSanctionProceedingFromDate");
			   
			   String[] sanction_fromdate= txtSanctionProceedingFromDate.split("/");
				c2 = new java.util.GregorianCalendar(Integer.parseInt(sanction_fromdate[2]),
			    Integer.parseInt(sanction_fromdate[1]) - 1, Integer.parseInt(sanction_fromdate[0]));
				java.util.Date ddd2 = c2.getTime();
				fromdate = new Date(ddd2.getTime());

				System.out.println("values.15.............................."+fromdate);
				
				
				
				
				
				String txtSanctionProceedingToDate=request.getParameter("txtSanctionProceedingToDate");
				   
				   String[] sanction_ToDate= txtSanctionProceedingToDate.split("/");
					c2 = new java.util.GregorianCalendar(Integer.parseInt(sanction_ToDate[2]),
				    Integer.parseInt(sanction_ToDate[1]) - 1, Integer.parseInt(sanction_ToDate[0]));
					java.util.Date ddd3 = c2.getTime();
					todate = new Date(ddd3.getTime());

					 System.out.println("values.16.............................."+todate);
		   
		   
					 String cmbPaymentOption=request.getParameter("cmbPaymentOption");			
					 System.out.println("values.17.............................."+cmbPaymentOption);
					
					 String txtPaymentPeriod1=request.getParameter("txtPaymentPeriod1");
					 int payment1=Integer.parseInt(txtPaymentPeriod1);
					 System.out.println("values.18.............................."+payment1);
						
					 String txtPaymentPeriod2=request.getParameter("txtPaymentPeriod2");
				     int payment2=Integer.parseInt(txtPaymentPeriod2);
				     System.out.println("values.19.............................."+payment2);
							
					 String txtPaymentPeriod3=request.getParameter("txtPaymentPeriod3");
					 int payment3=Integer.parseInt(txtPaymentPeriod3);
					 System.out.println("values.20.............................."+payment3);
								
					 String txtPaymentPeriod4=request.getParameter("txtPaymentPeriod4");
					 int payment4=Integer.parseInt(txtPaymentPeriod4);
					 System.out.println("values.21.............................."+payment4);
					
		   
		   String txtSanctionAmount=request.getParameter("txtSanctionAmount");
		   double sanctionAmount=Double.parseDouble(txtSanctionAmount);
		   System.out.println("values.22.............................."+sanctionAmount);
		   
		   String cmbAccHeadCode=request.getParameter("cmbAccHeadCode");
		   int accHeadCode=Integer.parseInt(cmbAccHeadCode);
			System.out.println("values..23............................."+accHeadCode);
		   
		   
		   
		   
		   String txtBudgetProvided=request.getParameter("txtBudgetProvided");
		   double BugetProvided=Double.parseDouble(txtBudgetProvided);
		   System.out.println("values.24.............................."+BugetProvided);
		   
		   
		   String txtBudgetSoFar=request.getParameter("txtBudgetSoFar");
		   double Bugetsofar=Double.parseDouble(txtBudgetSoFar);
		   System.out.println("values.25.............................."+Bugetsofar);
		   
		   String txtMade=request.getParameter("txtMade");
		   int made=Integer.parseInt(txtMade);
			System.out.println("values.26.............................."+made);
		   
		   
		   String mtxtRemarks=request.getParameter("mtxtRemarks");
		   
		   String txtAmount=request.getParameter("txtAmount");
		   double amount=Double.parseDouble(txtAmount);
		   System.out.println("values.27.............................."+amount);
		   
		   
		   
		 
		   
		   
		   
		   String profile=(String)session.getAttribute("UserId");
			//int employeeid=profile.getEmployeeId();
			System.out.println("values.28.............................."+profile);
			
		
			long l = System.currentTimeMillis();
			Timestamp ts = new Timestamp(l);
			System.out.println("values.30.............................."+ts);
			
			 System.out.println("values.............................................................................End");
			
	            	
		
			try
			{
				
				    ps=con.prepareStatement("insert into FAS_SANC_PROC_RENT_MST values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				    ps.setInt(1,accno);
		            ps.setInt(2,officecode);
		            ps.setInt(3,cashbookyear);
		            ps.setInt(4,cashbookmonth);
		            ps.setInt(5, autonumber);
		            ps.setDate(6, sanctionproceedingdate);
		            ps.setInt(7, majortype);
		            ps.setInt(8, minortype);
		            ps.setInt(9,billtype);
		            ps.setString(10,payeetype);
		            ps.setInt(11,payeecode1);
		            ps.setInt(12,refno);
                    ps.setDate(13,refDate);
                    ps.setInt(14, autrority);
                    ps.setInt(15, sanctionBy);
                    ps.setInt(16, accHeadCode);
				    ps.setDouble(17, sanctionAmount);
				  
				    ps.setInt(18, made);
				    ps.setString(19, mtxtRemarks);
				    ps.setString(20, "L");
				  
				    ps.setString(21, profile);
		            ps.setTimestamp(22, ts);
		            ps.setDate(23,fromdate);
		            ps.setDate(24,todate);
		            ps.setString(25,cmbPaymentOption);
		            ps.setInt(26,payment1);
		            ps.setInt(27,payment2);
		            ps.setInt(28,payment3);
		            ps.setInt(29,payment4 );
		            ps.setDouble(30, amount);
				    ps.executeUpdate();
				    xml=xml+"<flag>success</flag>";
				    

					
			         
				
			}
			catch(Exception e)
			{
				xml=xml+"<flag>failure</flag>";
				System.out.println(e);
			}
			
			xml = xml + "</response>";
			System.out.println(xml);
			
		}
	
		
		
		
		
		out.write(xml);

}
}
