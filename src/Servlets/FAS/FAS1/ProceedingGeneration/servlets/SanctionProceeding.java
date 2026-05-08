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

//import com.ibm.icu.text.SimpleDateFormat;


import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class DrawingOfficerDetails
 */
public class SanctionProceeding extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	PreparedStatement ps1 = null;
	ResultSet rs1 = null;
	PreparedStatement ps2 = null;
	ResultSet rs2 = null;
       
     public SanctionProceeding() {
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
		if(cmd.equalsIgnoreCase("loadmajortype"))
		{
			 
			
			
			xml="<response><command>loadmajortype</command>";
			try
			{      
				ps=con.prepareStatement("select * from FAS_BILL_MAJOR_TYPES where STATUS='L' order by BILL_MAJOR_TYPE_CODE");
				rs=ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while(rs.next())
				{
					xml=xml+"<majorid>"+rs.getInt(1)+"</majorid>";
					xml=xml+"<majorname>"+rs.getString(2)+"</majorname>";
					
				}
				
				
				
				
			}
			catch(Exception e)
			{
				xml = xml + "<flag>failure</flag>";
				System.out.println(e);
			}
			xml = xml + "</response>";
			System.out.println(xml);
			
			
		}
		
		if(cmd.equalsIgnoreCase("loadminortype"))
		{
			 
			String cmbMajorType=request.getParameter("cmbMajorType");
			
			xml="<response><command>loadminortype</command>";
			
			
			try
			{
				ps1=con.prepareStatement("select * from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE='"+cmbMajorType+"' and status='L' ");
				rs1=ps1.executeQuery();				
				if(rs1.next())
				{
			
			
			
			
			try
			{      
				ps=con.prepareStatement("select * from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE='"+cmbMajorType+"' and status='L' ");
				rs=ps.executeQuery();
			   
			
			    int count=rs.getRow();
			
			     System.out.println("count---------------------------"+count);
				
					
					xml = xml + "<flag>success</flag>";
				
                    
					while(rs.next())
					{
						xml=xml+"<miorid>"+rs.getInt(2)+"</miorid>";
						xml=xml+"<miorname>"+rs.getString(3)+"</miorname>";
						
					}
					
					
			}
				
				
				
			
			catch(Exception e)
			{
				xml = xml + "<flag>failure</flag>";
				System.out.println(e);
			}
				}
				else
				{
					xml = xml + "<flag>NoData</flag>";
				}
			}
			catch(Exception e)
			{
				xml = xml + "<flag>failure</flag>";
				System.out.println(e);
			}
			
			xml = xml + "</response>";
			System.out.println(xml);
			
			
		}
		if(cmd.equalsIgnoreCase("loadsubType"))
		{
			 
			String cmbMajorType=request.getParameter("cmbMajorType");
			String cmbMinorType=request.getParameter("cmbMinorType");
			
			xml="<response><command>loadsubType</command>";
			
			try
			{
				ps1=con.prepareStatement("select * from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE='"+cmbMajorType+"' and BILL_MINOR_TYPE_CODE='"+cmbMinorType+"' and status='L'");
				rs1=ps1.executeQuery();				
				if(rs1.next())
				{
			
			try
			{      
				ps=con.prepareStatement("select * from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE='"+cmbMajorType+"' and BILL_MINOR_TYPE_CODE='"+cmbMinorType+"' and status='L'");
				rs=ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while(rs.next())
				{
					xml=xml+"<subid>"+rs.getInt(3)+"</subid>";
					xml=xml+"<subname>"+rs.getString(4)+"</subname>";
					
				}
				
				
				
				
			}
			catch(Exception e)
			{
				xml = xml + "<flag>failure</flag>";
				System.out.println(e);
			}
			}
				else
				{
					xml = xml + "<flag>NoData</flag>";
				}
				}
					catch(Exception e)
			{
				xml = xml + "<flag>failure</flag>";
				System.out.println(e);
			}
			
			xml = xml + "</response>";
			System.out.println(xml);
			
			
		}
		if(cmd.equalsIgnoreCase("add"))
		{
			
			
			
			System.out.println("enter into add function");
			
              int autonumber=0;
              int autonumber1=0;
			
			try
 		   {
 		
 		    ps=con.prepareStatement("select max(SANCTION_PROCEEDING_NO)from FAS_SANC_PROC_MST");
 		   rs= ps.executeQuery();
 		 
 		   if(rs.next())
 		   {
 			   
 			   if(rs.getString(1)==null)
 			   {
 				   autonumber=1;
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
			System.out.println("values..............................."+accno);
			
			
			
		   String cmbOffice_code=request.getParameter("cmbOffice_code");
			int officecode=Integer.parseInt(cmbOffice_code);
			System.out.println("values..............................."+officecode);
		   
		   
		   String cmbCashBookYear=request.getParameter("cmbCashBookYear");
			int cashbookyear=Integer.parseInt(cmbCashBookYear);
			System.out.println("values..............................."+cashbookyear);
		   
		   String cmbCashBookMonth=request.getParameter("cmbCashBookMonth");
			int cashbookmonth=Integer.parseInt(cmbCashBookMonth);
			System.out.println("values..............................."+cashbookmonth);
		   
		   
		   
		   
		   String cmbMajorType=request.getParameter("cmbMajorType");
			int majortype=Integer.parseInt(cmbMajorType);
			System.out.println("values..............................."+majortype);
		   
		   
		   String cmbMinorType=request.getParameter("cmbMinorType");
		    int minortype=Integer.parseInt(cmbMinorType);
			System.out.println("values..............................."+minortype);
		   
		   
		   
		   String cmbBillSubType=request.getParameter("cmbBillSubType");
		   int billtype=Integer.parseInt(cmbBillSubType);
			System.out.println("values..............................."+billtype);
		   
		   
		   
		   
		   String txtRefNo=request.getParameter("txtRefNo");
		   int refno=Integer.parseInt(txtRefNo);
			System.out.println("values..............................."+refno);
		   
		   
		   
		   String txtRefDate=request.getParameter("txtRefDate");
		   java.sql.Date refDate = null;
		   java.sql.Date sanctionproceedingdate = null;
		   java.sql.Date refDate1 = null;
		   
		    java.util.GregorianCalendar c2;
			
			String[] ref_Date= txtRefDate.split("/");
			c2 = new java.util.GregorianCalendar(Integer.parseInt(ref_Date[2]),
		    Integer.parseInt(ref_Date[1]) - 1, Integer.parseInt(ref_Date[0]));
			java.util.Date ddd = c2.getTime();
			refDate = new Date(ddd.getTime());

		   
		   
		   String cmbSanctionAutrority=request.getParameter("cmbSanctionAutrority");
		   int autrority=Integer.parseInt(cmbSanctionAutrority);
			System.out.println("values..............................."+autrority);
		   
		   
		   
		   
		   String cmbSanctionBy=request.getParameter("cmbSanctionBy");
		   int sanctionBy=Integer.parseInt(cmbSanctionBy);
			System.out.println("values..............................."+sanctionBy);
		   
		   
		   
		    String txtSanctionProceedingDate=request.getParameter("txtSanctionProceedingDate");
		   
		   String[] sanction_proceedingdate= txtSanctionProceedingDate.split("/");
			c2 = new java.util.GregorianCalendar(Integer.parseInt(sanction_proceedingdate[2]),
		    Integer.parseInt(sanction_proceedingdate[1]) - 1, Integer.parseInt(sanction_proceedingdate[0]));
			java.util.Date ddd1 = c2.getTime();
			sanctionproceedingdate = new Date(ddd1.getTime());

		   
		   
		   String txtSanctionAmount=request.getParameter("txtSanctionAmount");
		   double sanctionAmount=Double.parseDouble(txtSanctionAmount);
		   System.out.println("values..............................."+sanctionAmount);
		   
		   String cmbAccHeadCode=request.getParameter("cmbAccHeadCode");
		   int accHeadCode=Integer.parseInt(cmbAccHeadCode);
			System.out.println("values..............................."+accHeadCode);
		   
		   
		   
		   
		   String txtBudgetProvided=request.getParameter("txtBudgetProvided");
		   double BugetProvided=Double.parseDouble(txtBudgetProvided);
		   System.out.println("values..............................."+BugetProvided);
		   
		   
		   String txtBudgetSoFar=request.getParameter("txtBudgetSoFar");
		   double Bugetsofar=Double.parseDouble(txtBudgetSoFar);
		   System.out.println("values..............................."+Bugetsofar);
		   
		   
		   
		   String txtAmount=request.getParameter("txtAmount");
		   double amount=Double.parseDouble(txtAmount);
		   System.out.println("values..............................."+amount);
		   
		   
		   
		   String txtMade=request.getParameter("txtMade");
		   int made=Integer.parseInt(txtMade);
			System.out.println("values..............................."+made);
		   
		   
		   String mtxtRemarks=request.getParameter("mtxtRemarks");
		   
		   
		   
		   String profile=(String)session.getAttribute("UserId");
			//int employeeid=profile.getEmployeeId();
			System.out.println("values..............................."+profile);
			
		
			long l = System.currentTimeMillis();
			Timestamp ts = new Timestamp(l);
			System.out.println("values..............................."+ts);
			
			 System.out.println("values.............................................................................End");
			
			   String txtSanctionAmountCommon=request.getParameter("txtSanctionAmountCommon");
			   double amount_Common=Double.parseDouble(txtSanctionAmountCommon);
			   System.out.println("values..............................."+amount_Common);
			   
			   
			   String txtAmountDeduct=request.getParameter("txtAmountDeduct");
			   double amount_deduct=Double.parseDouble(txtAmountDeduct);
			   System.out.println("values..............................."+amount_deduct);
			   
			 
			   String payee=request.getParameter("payee");
			   String Recovery_from=request.getParameter("Recovery_from");
			try
			{
				
				    ps=con.prepareStatement("insert into FAS_SANC_PROC_MST values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				    ps.setInt(1,accno);
		            ps.setInt(2,officecode);
		            ps.setInt(3,cashbookyear);
		            ps.setInt(4,cashbookmonth);
		            ps.setInt(5, autonumber);
		            ps.setDate(6, sanctionproceedingdate);
		            ps.setInt(7, majortype);
		            ps.setInt(8, minortype);
		            ps.setInt(9,billtype);
		            ps.setInt(10,refno);
                    ps.setDate(11,refDate);
                    ps.setInt(12, autrority);
                    ps.setInt(13, sanctionBy);
				    ps.setDouble(14, sanctionAmount);
				    ps.setInt(15, accHeadCode);
				    ps.setInt(16, made);
				    ps.setString(18, "L");
				    ps.setString(17, mtxtRemarks);
				    ps.setString(19, profile);
		            ps.setTimestamp(20, ts);
		            ps.setString(21, payee);
		            ps.setDouble(22, amount_Common);
		            ps.setString(23, Recovery_from);
		            ps.setDouble(24, amount_deduct);
		          
		         
				
				    ps.executeUpdate();
				    
				    

					  String[] grid=request.getParameter("grid").split(",");
			            int len=grid.length;
						System.out.println("arraylength"+len);
						
			            for(int i=0;i<len;i=i+12)
						{
			            	System.out.println("enter");
			            	String PayeeType =grid[i];
			            	System.out.println("---------------------------------------------------------------------------------"+grid[i]);
			            	System.out.println("values..............................."+PayeeType);
			            	
			            	int payeeCode=Integer.parseInt(grid[i+1]);
			            	System.out.println("values..............................."+payeeCode);
			            	
			            	
			            	String paymentType=grid[i+3];
			            	System.out.println("values..............................."+paymentType);
			            	
			            	
			            	double samt=Double.parseDouble(grid[i+4]);
			            	System.out.println("values..............................."+samt);
			            	
			            	
			            	
			            	int refno1=Integer.parseInt(grid[i+5]);
			            	System.out.println("values..............................."+refno1);
			            	
			            	
			            	
			            	String[] ref_Date2= grid[i+6].split("/");
			    			c2 = new java.util.GregorianCalendar(Integer.parseInt(ref_Date2[2]),
			    		    Integer.parseInt(ref_Date2[1]) - 1, Integer.parseInt(ref_Date2[0]));
			    			java.util.Date ddd3 = c2.getTime();
			    			refDate1 = new Date(ddd.getTime());
			    			System.out.println("values..............................."+refDate1);
			            	
				            int Instalments=Integer.parseInt(grid[i+7]);
				            System.out.println("values..............................."+Instalments);
				            
				            String Start_Month=grid[i+8];
				            System.out.println("values..............................."+Start_Month);
				            
				            double Residual_Amount=Double.parseDouble(grid[i+9]);
			            	System.out.println("values..............................."+Residual_Amount);
			            	
			            	 int Instl_No=Integer.parseInt(grid[i+10]);
					            System.out.println("values..............................."+Instl_No);
				    
				            String particulars =grid[i+11];
				            System.out.println("values..............................."+particulars);
			            	
				            
				            try
				  		   {
				  		
				  		    ps2=con.prepareStatement("select max(SL_NO)from FAS_SANC_PROC_TRN");
				  		   rs2= ps2.executeQuery();
				  		 
				  		   if(rs2.next())
				  		   {
				  			   
				  			   if(rs2.getString(1)==null)
				  			   {
				  				   autonumber1=1;
				  				 System.out.println("auto----------------"+autonumber1);
				  			   }
				  			   else
				  			   {
				  			   System.out.println("auto----------------"+rs2.getString(1));
				  			   autonumber1=Integer.parseInt(rs2.getString(1))+1;
				  			   
				  			   
				  			   
				  			   }
				  			   
				  			   
				  		   }
				  			
				  		}
				  		   catch(Exception e)
				  		   {
				  			   System.out.println("inner error"+e);
				  		   }
				    
				      
				  		   
				  		    ps1=con.prepareStatement("insert into FAS_SANC_PROC_TRN values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				  		    ps1.setInt(1,accno);
				            ps1.setInt(2,officecode);
				            ps1.setInt(3,cashbookyear);
				            ps1.setInt(4,cashbookmonth);
				            ps1.setInt(5, autonumber);
				            ps1.setInt(6, autonumber1);
				            ps1.setString(7,PayeeType);
				            ps1.setInt(8,payeeCode);
				            ps1.setString(9,paymentType);
				            ps1.setDouble(10,samt);
				            ps1.setInt(11, refno1);
				            ps1.setDate(12, refDate1);
				            ps1.setString(13, particulars);
				            ps1.setString(14, profile);
				            ps1.setTimestamp(15, ts);
				            ps1.setInt(16, Instalments);
				            ps1.setString(17, Start_Month);
				            ps1.setInt(18,Instl_No);
				            ps1.setDouble(19, Residual_Amount);
				            ps1.executeUpdate();
				
				            xml=xml+"<flag>success</flag>";
				
						}
				
			}
			catch(Exception e)
			{
				xml=xml+"<flag>failure</flag>";
				System.out.println(e);
			}
			
			xml = xml + "</response>";
			System.out.println(xml);
			
		}
		if(cmd.equalsIgnoreCase("getAmount"))
		{
			xml="<response><command>getAmount</command>";
			String finyear=null;
			String txtAcc_HeadCode=request.getParameter("txtAcc_HeadCode");
			 System.out.println("finyear..............................................................."+txtAcc_HeadCode);
			
			String cmbCashBookYear=request.getParameter("cmbCashBookYear");
			String cmbCashBookMonth=request.getParameter("cmbCashBookMonth");
			int month=Integer.parseInt(cmbCashBookMonth);
			int year=Integer.parseInt(cmbCashBookYear);
			if(month>3)
			{
			    year=year+1;
			    finyear=cmbCashBookYear+"-"+year;
			    System.out.println("finyear..............................................................."+finyear);
			}
			else
			{
				year=year-1;
				finyear=year+"-"+cmbCashBookYear;
				 System.out.println("finyear..............................................................."+finyear);
				
				
			}
			try
			{
			ps=con.prepareStatement("select * from COM_BUDGET_DETAILS where ACCOUNT_HEAD_CODE='"+txtAcc_HeadCode+"' and FINANCIAL_YEAR='"+finyear+"'");
			rs=ps.executeQuery();
			
			if(rs.next())
			{
				 xml=xml+"<flag>success</flag>";
				xml=xml+"<provided>"+rs.getInt(15)+"</provided>";
				xml=xml+"<spend>"+rs.getInt(16)+"</spend>";
				int balance=rs.getInt(15)-rs.getInt(16);
				xml=xml+"<balance>"+balance+"</balance>";
				
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
			
			
			xml = xml + "</response>";
			System.out.println(xml);
			
		}
		
		
		if(cmd.equalsIgnoreCase("loaddes"))
		{
			 
			
			
			xml="<response><command>loaddes</command>";
			try
			{      
				ps=con.prepareStatement("select * from HRM_MST_DESIGNATIONS");
				rs=ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while(rs.next())
				{
					xml=xml+"<desid>"+rs.getInt(1)+"</desid>";
					xml=xml+"<desname>"+rs.getString(2)+"</desname>";
					
				}
				
				
				
				
			}
			catch(Exception e)
			{
				xml = xml + "<flag>failure</flag>";
				System.out.println(e);
			}
			xml = xml + "</response>";
			System.out.println(xml);
			
			
		}
		
		if(cmd.equalsIgnoreCase("pensioner"))
		{
			   String empid=request.getParameter("empid");
			   String s1="In Valid EmployeeCode";
			 int eid=  Integer.parseInt(empid);
			xml="<response><command>loadempdetails</command>";
			try
			{      
				ps=con.prepareStatement("select * from HRM_PEN_MST_DETAILS where EMPLOYEE_ID='"+eid+"'");
				rs=ps.executeQuery();
			
				if(rs.next())
				{
					xml=xml+"<id>"+rs.getInt(1)+"</id>";
					xml=xml+"<name>"+rs.getString(5)+"</name>";
					xml=xml+"<flag>pension</flag>";
				}
				else
				{
					xml=xml+"<flag>nodata</flag>";
				
					
					
				}
				
				
				
			}
			catch(Exception e)
			{
				xml = xml + "<flag>failure</flag>";
				System.out.println(e);
			}
			xml = xml + "</response>";
			System.out.println(xml);	
			
			
		}
		
		
		
		
		
		out.write(xml);

}
}
