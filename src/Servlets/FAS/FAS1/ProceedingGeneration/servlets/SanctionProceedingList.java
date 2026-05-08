package Servlets.FAS.FAS1.ProceedingGeneration.servlets;


import java.beans.Statement;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
public class SanctionProceedingList extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	PreparedStatement ps1 = null;
	ResultSet rs1 = null;
	PreparedStatement ps2 = null;
	ResultSet rs2 = null;
	Statement statement=null;
       
     public SanctionProceedingList() {
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
	    String cmd=request.getParameter("Command");
	    
        Date txtFrom_date=null,txtTo_date=null;
        Calendar c;
      
	    System.out.println("command"+cmd);
	    
	    int count=0;
		String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
		int cmbAcc_UnitCode1=Integer.parseInt(cmbAcc_UnitCode);
		System.out.println("cmbAcc_UnitCode1"+cmbAcc_UnitCode1);
		
		String cmbOffice_code=request.getParameter("cmbOffice_code");
		int cmbOffice_code1=Integer.parseInt(cmbOffice_code);
		System.out.println("cmbOffice_code1"+cmbOffice_code1);
		
		String txtCB_Year=request.getParameter("txtCB_Year");
		int txtCB_Year1=Integer.parseInt(txtCB_Year);
		System.out.println("txtCB_Year1"+txtCB_Year1);
		
		String txtCB_Month=request.getParameter("txtCB_Month");
		int txtCB_Month1=Integer.parseInt(txtCB_Month);
		System.out.println("txtCB_Month1"+txtCB_Month1);
		
		
		String cmbStatus=request.getParameter("cmbStatus");
		System.out.println("cmbStatus"+cmbStatus);
	  
	   
	   
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
		if(cmd.equalsIgnoreCase("searchByMonth"))
		{
			
			
			
			xml="<response><command>searchByMonth</command>";
			
			try
			{
				System.out.println("enter1");
				
				ps1=con.prepareStatement("select * from FAS_SANC_PROC_MST where ACCOUNTING_UNIT_ID='"+cmbAcc_UnitCode1+"' and ACCOUNTING_FOR_OFFICE_ID='"+cmbOffice_code1+"'and CASHBOOK_YEAR='"+txtCB_Year1+"' and CASHBOOK_MONTH='"+txtCB_Month1+"' and STATUS='"+cmbStatus+"'");
				
			    xml=xml+"<Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
	             "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
				//ps1=con.prepareStatement("select * from FAS_SANC_PROC_MST where ACCOUNTING_UNIT_ID='3' and ACCOUNTING_FOR_OFFICE_ID='5000'and CASHBOOK_YEAR='2010' and CASHBOOK_MONTH='7' and STATUS='L'");
					rs1=ps1.executeQuery();	
			
				 
				  System.out.println("size"+rs1.getFetchSize());
				
				while(rs1.next())
				{
					 xml=xml+"<leng>";
					xml=xml+"<san_no>"+rs1.getInt("SANCTION_PROCEEDING_NO")+"</san_no>";
				
					String a[]=rs1.getDate("SANCTION_PROCEEDING_DATE").toString().split("-");
					
					String sandate=a[2]+"/"+a[1]+"/"+a[0];
					xml=xml+"<san_Date>"+sandate+"</san_Date>";
					xml=xml+"<amount>"+rs1.getDouble("TOTLA_SANCTION_AMOUNT")+"</amount>";
					xml=xml+"<remark>"+rs1.getString("REMARKS")+"</remark>";
					 xml=xml+"</leng>";
                     count++;
                }
               if(count==0) 
               {
                  System.out.println("inside count==0");
               //   xml="<response><command>searchByMonth</command><flag>failure</flag>";
                  xml=xml+"<flag>failure</flag>";
               }
               else
               {
            	  
            	   System.out.println("inside else");
            	   xml=xml+"<flag>success</flag>";
            	  
            	   
               }
            
			}	catch(Exception e)
			{
				xml = xml + "<flag>failure</flag>";
				System.out.println(e);
			}
			
			   xml=xml+"</response>";   
		       
		        System.out.println(xml); 
		        System.out.println("Record Count"+count);	
			
			
			
			
			
			
			
		}
		 if(cmd.equalsIgnoreCase("searchByDate"))
		 {
			 
			 
			 xml="<response><command>searchByDate</command>";
			 
			 String s1=request.getParameter("txtFrom_date");
			 String s2=request.getParameter("txtTo_date");
			 
			 
			 String[] sd=request.getParameter("txtFrom_date").split("/");
	            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	            java.util.Date d=c.getTime();
	            txtFrom_date=new Date(d.getTime());
	            System.out.println("from_date "+txtFrom_date);
	            
	           
	            
	            
	            sd=request.getParameter("txtTo_date").split("/");
	            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
	            d=c.getTime();
	            txtTo_date=new Date(d.getTime());
	            System.out.println("txtTo_date "+txtTo_date);
			 
			    try
			    {
			    	
			    	//ps1=con.prepareStatement("SELECT *FROM FAS_SANC_PROC_MST  WHERE SANCTION_PROCEEDING_DATE  BETWEEN '"+txtFrom_date+"' AND '"+txtTo_date+"'");
			    	ps1=con.prepareStatement("select * from FAS_SANC_PROC_MST  WHERE SANCTION_PROCEEDING_DATE BETWEEN to_date(?, 'dd-MM-yyyy') AND to_date(?, 'dd-MM-yyyy') and ACCOUNTING_UNIT_ID='"+cmbAcc_UnitCode1+"' and ACCOUNTING_FOR_OFFICE_ID='"+cmbOffice_code1+"'and CASHBOOK_YEAR='"+txtCB_Year1+"' and CASHBOOK_MONTH='"+txtCB_Month1+"'and   STATUS='"+cmbStatus+"'");
			    	ps1.setString(1, s1);
			    	ps1.setString(2, s2);
			    	rs1=ps1.executeQuery();	
					
			    	 xml=xml+"<Ucode>"+cmbAcc_UnitCode+"</Ucode><Offid>"+cmbOffice_code+
		             "</Offid><txtCB_Year>"+txtCB_Year+"</txtCB_Year><txtCB_Month>"+txtCB_Month+"</txtCB_Month>";
					 
					
					while(rs1.next())
					{
						 xml=xml+"<leng>";
						 xml=xml+"<san_no>"+rs1.getInt("SANCTION_PROCEEDING_NO")+"</san_no>";
						
						 String a[]=rs1.getDate("SANCTION_PROCEEDING_DATE").toString().split("-");
							
							String sandate=a[2]+"/"+a[1]+"/"+a[0];
							xml=xml+"<san_Date>"+sandate+"</san_Date>";
							xml=xml+"<amount>"+rs1.getDouble("TOTLA_SANCTION_AMOUNT")+"</amount>";
							xml=xml+"<remark>"+rs1.getString("REMARKS")+"</remark>";
							 xml=xml+"</leng>";
		                     count++;
	                }
	               if(count==0) 
	               {
	                  System.out.println("inside count==0");
	               //   xml="<response><command>searchByMonth</command><flag>failure</flag>";
	                  xml=xml+"<flag>failure</flag>";
	               }
	               else
	               {
	            	  
	            	   System.out.println("inside else");
	            	   xml=xml+"<flag>success</flag>";
	            	  
	            	   
	               }
	            
				}	catch(Exception e)
				{
					xml = xml + "<flag>failure</flag>";
					System.out.println(e);
				}
				
				   xml=xml+"</response>";   
			       
			        System.out.println(xml); 
			        System.out.println("Record Count"+count);	
				
			 
			 
			 
			 
		 }
		
		 if(cmd.equalsIgnoreCase("details"))
		 {
			 
			 
			 xml="<response><command>details</command>";
			 
			 String sanNO=request.getParameter("sanNo");
			
			 
			 
			
			    try
			    {
			    	
			    	
			    	ps1=con.prepareStatement("select * from FAS_SANC_PROC_TRN where ACCOUNTING_UNIT_ID='"+cmbAcc_UnitCode1+"' and ACCOUNTING_FOR_OFFICE_ID='"+cmbOffice_code1+"'and CASHBOOK_YEAR='"+txtCB_Year1+"' and CASHBOOK_MONTH='"+txtCB_Month1+"' and SANCTION_PROCEEDING_NO='"+sanNO+"'");
			    	rs1=ps1.executeQuery();	
					
			    	
			    	xml = xml + "<flag>success</flag>";
					
					while(rs1.next())
					{
						 
						int payeecode=rs1.getInt(8);
						
						
						
						String a[]=rs1.getDate(12).toString().split("-");
						
						String refdate=a[2]+"/"+a[1]+"/"+a[0];
						
						
						
						xml=xml+"<sl_no>"+rs1.getInt(6)+"</sl_no>";
						xml=xml+"<payeetype>"+rs1.getString(7)+"</payeetype>";
						xml=xml+"<paymentType>"+rs1.getString(9)+"</paymentType>";
						xml=xml+"<amount>"+rs1.getDouble(10)+"</amount>";
						xml=xml+"<refno>"+rs1.getInt(11)+"</refno>";
						xml=xml+"<refdate>"+refdate+"</refdate>";
						xml=xml+"<details>"+rs1.getString(13)+"</details>";
						xml=xml+"<payeename>"+rs1.getInt(8)+"</payeename>";
						
						/*ps=con.prepareStatement("select * from HRM_MST_EMPLOYEES where EMPLOYEE_ID='"+rs1.getInt(8)+"'");
						rs=ps.executeQuery();
						if(rs.next())
						{
							xml=xml+"<payeename>"+rs.getString(2)+"</payeename>";
						}*/
						
						
						}
	                
	              
	            
				}	catch(Exception e)
				{
					xml = xml + "<flag>failure</flag>";
					System.out.println(e);
				}
				
				   xml=xml+"</response>";   
			       
			        System.out.println(xml); 
			        System.out.println("Record Count"+count);	
				
			 
			 
			 
			 
		 }
		
		
		out.write(xml);
		
		
	

}
}
