package Servlets.FAS.FAS1.CommonScriptJava;


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
public class EmpName extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
	Connection con = null;
	PreparedStatement ps = null;
	ResultSet rs = null;
	PreparedStatement ps1 = null;
	ResultSet rs1 = null;
	PreparedStatement ps2 = null;
	ResultSet rs2 = null;
       
     public EmpName() {
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
		
		
		if(cmd.equalsIgnoreCase("pensioner"))
		{
			   String empid=request.getParameter("empid");
			   String s1="In Valid EmployeeCode";
			 int eid=  Integer.parseInt(empid);
			 System.out.println(eid);
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
                
		else if(cmd.equalsIgnoreCase("getEmpName"))
		{
			   String empid=request.getParameter("empid");
			   String s1="In Valid EmployeeCode";
			 int eid=  Integer.parseInt(empid);
			xml="<response><command>loadempdetails</command>";
			try
			{      
				ps=con.prepareStatement("select * from HRM_MST_EMPLOYEES where EMPLOYEE_ID='"+eid+"'");
				rs=ps.executeQuery();
			
				if(rs.next())
				{
					xml=xml+"<id>"+rs.getString("EMPLOYEE_NAME")+"</id>";
					xml=xml+"<flag>success</flag>";
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
