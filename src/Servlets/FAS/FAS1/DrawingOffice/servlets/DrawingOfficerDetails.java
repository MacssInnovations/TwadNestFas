package Servlets.FAS.FAS1.DrawingOffice.servlets;


import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
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
public class DrawingOfficerDetails extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	PreparedStatement ps1 = null;
	ResultSet rs1 = null;
	PreparedStatement ps2 = null;
	ResultSet rs2 = null;
       
     public DrawingOfficerDetails() {
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
	   response.setContentType(CONTENT_TYPE);
	   
	    String cmd=request.getParameter("command");
	    String empid=request.getParameter("employeeId");
	    int employeeid=0;
	    int Desgnationid=0;
	    String desname="";
	    
	    
	   
	    System.out.println("=====================================================================================>"+cmd);
	 
	   
	   
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
		if(cmd.equalsIgnoreCase("getload"))
		{
			   
			xml="<response><command>getload</command>";
			try
			{
				ps=con.prepareStatement("select * from HRM_EMP_CURRENT_POSTING where EMPLOYEE_STATUS_ID='WKG'");
				rs=ps.executeQuery();
				xml=xml+"<flag>success</flag>";
				while(rs.next())
				{
					xml=xml+"<id>"+rs.getString(1)+"</id>";
					xml=xml+"<name>"+rs.getString(1)+"</name>";
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
		else if(cmd.equalsIgnoreCase("getv"))
		{ 
			   System.out.println("getvalue");
			UserProfile profile=(UserProfile)session.getAttribute("UserProfile");
		    employeeid=profile.getEmployeeId();
		   // System.out.println("test");
			xml="<response><command>getv</command>";
			try
			{
			/* ps1=con.prepareStatement("select * from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID='"+employeeid+"'");
				rs1=ps1.executeQuery();
				if(rs1.next())
				{
					Desgnationid=rs1.getInt(3);
					  System.out.println("testdesid"+Desgnationid);
					*/
				int ii=0;
					ps2=con.prepareStatement("select * from HRM_MST_DESIGNATIONS");
					ResultSet rs3=ps2.executeQuery();
					xml=xml+"<flag>success</flag>";
					while(rs3.next())
					{
						//desname=rs2.getString(2);
						xml=xml+"<DesgnationId>"+rs3.getString(1)+"</DesgnationId>";
						xml=xml+"<DesgnationName>"+rs3.getString(2)+"</DesgnationName>";
						ii++;
					}
					xml=xml+"<countid>"+ii+"</countid>";
				//}
			}
			catch(Exception e)
			{
				xml = xml + "<flag>failure</flag>";
				System.out.println(e);
			}
			xml = xml + "</response>";
			System.out.println(xml);
			
		}
		
		else if(cmd.equalsIgnoreCase("designload"))
		{
			xml=xml+"<response><command>designload</command>";
			try
			{
				ps=con.prepareStatement("(select a.designation_id,"+
				" a.designation from hrm_mst_designations a " +
				"where a.designation_id=(select b.designation_id from hrm_emp_current_posting b "+ 
				" where b.EMPLOYEE_ID="+empid+"))");
				rs=ps.executeQuery();
				xml=xml+"<flag>success</flag>";
				while(rs.next())
				{
					xml=xml+"<DesgnationId>"+rs.getString(1)+"</DesgnationId>";
					xml=xml+"<DesgnationName>"+rs.getString(2)+"</DesgnationName>";
				}
			}
			catch(Exception e)
			{
				xml=xml+"<flag>failure</flag>";
				System.out.println(e);
			}
			xml=xml+"</response>";
		   System.out.println(xml);
			
		}
		
		
		
		else if(cmd.equalsIgnoreCase("getname"))
		{
			xml=xml+"<response><command>getname</command>";
			try
			{
				ps=con.prepareStatement("select * from HRM_MST_EMPLOYEES where EMPLOYEE_ID="+empid);
				rs=ps.executeQuery();
				xml=xml+"<flag>success</flag>";
				while(rs.next())
				{
					xml=xml+"<name>"+rs.getString(2)+"</name>";
				}
			}
			catch(Exception e)
			{
				xml=xml+"<flag>failure</flag>";
				System.out.println(e);
			}
			xml=xml+"</response>";
		   System.out.println(xml);
			
		}
		else if(cmd.equalsIgnoreCase("add"))
		{
			
			xml=xml+"<response><command>add</command>";
			String accno=request.getParameter("accountUnitId");
			int accno1=Integer.parseInt(accno);
			System.out.println(accno1);
			String officeid=request.getParameter("accountOfficeId");
			System.out.println(officeid);
			int officeid1=Integer.parseInt(officeid);
			String eid=request.getParameter("employeeId");
			int id1=Integer.parseInt(eid);
			String remark=request.getParameter("remarks");
			 String profile=(String)session.getAttribute("UserId");
				//int employeeid=profile.getEmployeeId();
				System.out.println("values..............................."+profile);
			String desid=request.getParameter("DesId");
			int desid1=Integer.parseInt(desid);
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new java.util.Date();
           // int year=date.getYear();
			//int month=date.getMonth();
		Calendar c=Calendar.getInstance();
		int year=c.get(Calendar.YEAR);
		int month=c.get(Calendar.MONTH);
			
            System.out.println("Current Date Time : " + dateFormat.format(date));
            System.out.println("Calendar Date Time : " + year);
            System.out.println("Calendar Date Time : " + month);
           /* try
            {
			
            ps1=con.prepareStatement("select * from FAS_ACQ_ROLL_DETAILS where ACCOUNTING_UNIT_ID='"+accno1+"'and ACCOUNTING_FOR_OFFICE_ID='"+officeid1+"'");
            rs=ps1.executeQuery();
            if(rs.next())
            {
            	year=rs.getInt(3);
            	month=rs.getInt(4);
            }*/
          
           
           try{
           
        	  
//   				String sqlload="select * from FAS_DRAWING_OFFICER_MST where ACCOUNTING_UNIT_ID='"+accno1+"'and ACCOUNTING_FOR_OFFICE_ID="+officeid1;
//   				PreparedStatement ps3=con.prepareStatement(sqlload);
//   				rs=ps3.executeQuery();
   				
   				
   					
   					String sql="update FAS_DRAWING_OFFICER_MST set STATUS='C' where ACCOUNTING_UNIT_ID='"+accno1+"'and ACCOUNTING_FOR_OFFICE_ID="+officeid1;
   					System.out.println("sql===>"+sql);
   					PreparedStatement ps3=con.prepareStatement(sql);
   					ps3.executeUpdate();
   					ps3.close();
   					System.out.println("sql== before inserting ==>"+sql);
   					
        	ps=con.prepareStatement("insert into FAS_DRAWING_OFFICER_MST values(?,?,?,?,?,?,?,?,?,?)");
            ps.setInt(1, accno1);
            ps.setInt(2,officeid1);
            ps.setInt(3,year );
            ps.setInt(4,month);
            ps.setInt(5,desid1);
            ps.setInt(6,id1);
            ps.setString(7,remark);
            ps.setString(8,profile);
            ps.setString(9,dateFormat.format(date));
            ps.setString(10, "L");
           int i =ps.executeUpdate();
           if(i>0)
           {
            xml=xml+"<flag>success</flag>";
           }
           else
           {
        	   xml=xml+"<flag>failure</flag>";
           }
        	   }
         
            
           
            catch(Exception e)
            {
            	 xml=xml+"<flag>failure</flag>";
            System.out.println(e);
            }
			xml=xml+"</response>";
			System.out.println(xml);
            
		}
		
		else if(cmd.equalsIgnoreCase("cancel")) {
			xml=xml+"<response><command>cancel</command>";
			
			String accno=request.getParameter("accountUnitId");
			int accno1=Integer.parseInt(accno);
			System.out.println(accno1);
			String officeid=request.getParameter("accountOfficeId");
			System.out.println(officeid);
			int officeid1=Integer.parseInt(officeid);
			String eid=request.getParameter("employeeId");
			int id1=Integer.parseInt(eid);
			
			
			try {
				String sqlload="select * from FAS_DRAWING_OFFICER_MST where ACCOUNTING_UNIT_ID='"+accno1+"'and ACCOUNTING_FOR_OFFICE_ID='"+officeid1+"'and EMPLOYEE_ID="+id1;
				System.out.println("sqlload==>"+sqlload);
				
				ps=con.prepareStatement(sqlload);
				rs=ps.executeQuery();
				
				while(rs.next())
				{
					
					String sql="update FAS_DRAWING_OFFICER_MST set STATUS='C' where ACCOUNTING_UNIT_ID='"+accno1+"'and ACCOUNTING_FOR_OFFICE_ID='"+officeid1+"'and EMPLOYEE_ID="+id1;
					System.out.println("sql===>"+sql);
					ps=con.prepareStatement(sql);
					int k = ps.executeUpdate();
					if(k>0)
			           {
			            xml=xml+"<flag>success</flag>";
			           }
			           else
			           {
			        	   xml=xml+"<flag>failure</flag>";
			           }
			          
					}
			            
			            }
			            catch(Exception e)
			            {
			            	 xml=xml+"<flag>failure</flag>";
			            System.out.println(e);
			            }
						xml=xml+"</response>";
						System.out.println(xml);

		}
		else if(cmd.equalsIgnoreCase("update"))
		{
			
			xml=xml+"<response><command>update</command>";
			String accno=request.getParameter("accountUnitId");
			int accno1=Integer.parseInt(accno);
			System.out.println(accno1);
			String officeid=request.getParameter("accountOfficeId");
			System.out.println(officeid);
			int officeid1=Integer.parseInt(officeid);
			String eid=request.getParameter("employeeId");
			int id1=Integer.parseInt(eid);
			String remark=request.getParameter("remarks");
			 String profile=(String)session.getAttribute("UserId");
				//int employeeid=profile.getEmployeeId();
				System.out.println("values..............................."+profile);
			String desid=request.getParameter("DesId");
			int desid1=Integer.parseInt(desid);
			System.out.println(desid1);
			DateFormat dateFormat = new SimpleDateFormat("yyyy/MM/dd");
            Date date = new java.util.Date();
            int year=0;
			int month=0;
            System.out.println("Current Date Time : " + dateFormat.format(date));
            try
            {
            ps=con.prepareStatement("update FAS_DRAWING_OFFICER_MST set EMPLOYEE_ID=?,REMARKS=?,UPDATEED_BY_USERID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID='"+accno1+"' and ACCOUNTING_FOR_OFFICE_ID='"+officeid1+"'and DESIGNATION_ID='"+desid1+"'");
       
            ps.setInt(1,id1);
            ps.setString(2,remark);
            ps.setString(3,profile);
            ps.setString(4,dateFormat.format(date));
            int j=ps.executeUpdate();
            if(j>0)
            {
             xml=xml+"<flag>success</flag>";
            }
            else
            {
         	   xml=xml+"<flag>failure</flag>";
            }
           
          
           
            
            }
            catch(Exception e)
            {
            	 xml=xml+"<flag>failure</flag>";
            System.out.println(e);
            }
			xml=xml+"</response>";
			System.out.println(xml);
		}
		else if(cmd.equalsIgnoreCase("deleted"))
		{
			
			xml=xml+"<response><command>deleted</command>";
			String accno=request.getParameter("accountUnitId");
			int accno1=Integer.parseInt(accno);
			System.out.println(accno1);
			String officeid=request.getParameter("accountOfficeId");
			System.out.println(officeid);
			int officeid1=Integer.parseInt(officeid);
			String eid=request.getParameter("employeeId");
			int id1=Integer.parseInt(eid);
			String desid=request.getParameter("DesId");
			int desid1=Integer.parseInt(desid);
            try
            {
            ps=con.prepareStatement("delete from FAS_DRAWING_OFFICER_MST where ACCOUNTING_UNIT_ID='"+accno1+"'and ACCOUNTING_FOR_OFFICE_ID='"+officeid1+"'and EMPLOYEE_ID='"+id1+"'and DESIGNATION_ID='"+desid1+"'");
          
           int k= ps.executeUpdate();
           if(k>0)
           {
            xml=xml+"<flag>success</flag>";
           }
           else
           {
        	   xml=xml+"<flag>failure</flag>";
           }
            
            }
            catch(Exception e)
            {
            	 xml=xml+"<flag>failure</flag>";
            System.out.println(e);
            }
			xml=xml+"</response>";
			System.out.println(xml);
			
			
			
			
			
			
			
		}
		else if(cmd.equalsIgnoreCase("gridvalue"))
		{
			
			xml=xml+"<response><command>gridvalue</command>";
			String accno=request.getParameter("accountUnitId");
			int accno1=Integer.parseInt(accno);
			System.out.println(accno1);
			String officeid=request.getParameter("accountOfficeId");
			System.out.println(officeid);
			int officeid1=Integer.parseInt(officeid);
			
			try
			{
				
				ps=con.prepareStatement("select * from  FAS_DRAWING_OFFICER_MST where ACCOUNTING_UNIT_ID='"+accno1+"'and ACCOUNTING_FOR_OFFICE_ID='"+officeid1+"' order by  status desc");
				rs=ps.executeQuery();
				 xml=xml+"<flag>success</flag>";
				while(rs.next())
				{
					System.out.println("id"+rs.getInt(5));
					 xml=xml+"<desID>"+rs.getInt(5)+"</desID>";
					 xml=xml+"<empID>"+rs.getInt(6)+"</empID>";
					 xml=xml+"<status>"+rs.getString(10)+"</status>";
					ps1=con.prepareStatement("select * from  HRM_MST_EMPLOYEES where EMPLOYEE_ID='"+rs.getInt(6)+"'");
					rs1=ps1.executeQuery();
					if(rs1.next())
					{
						 xml=xml+"<empName>"+rs1.getString(2)+"</empName>";
						System.out.println(rs1.getString(2));
						
					}
					
					ps2=con.prepareStatement("select * from  HRM_MST_DESIGNATIONS where DESIGNATION_ID='"+rs.getInt(5)+"'");
					rs2=ps2.executeQuery();
					if(rs2.next())
					{
						System.out.println(rs2.getString(2));
						 xml=xml+"<DesignationName>"+rs2.getString(2)+"</DesignationName>";
						
					}  
			         
					
					
			            xml=xml+"<remarks>"+rs.getString(7)+"</remarks>";
			            
					
					
				}
				
				
			}
			catch(Exception e)
			{
				 xml=xml+"<flag>failure</flag>";
				System.out.println(e);
			}
			xml=xml+"</response>";
			System.out.println(xml);
			
			
			
			
		}
		
		
		out.write(xml);
	}

}