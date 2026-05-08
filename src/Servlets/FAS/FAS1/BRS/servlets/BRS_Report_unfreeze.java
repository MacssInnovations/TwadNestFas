package Servlets.FAS.FAS1.BRS.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class BRS_Report_unfreeze
 */
public class BRS_Report_unfreeze extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
       
    
    public BRS_Report_unfreeze() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
//	System.out.println("haii");
	response.setContentType(CONTENT_TYPE);
	PrintWriter out = response.getWriter();
	
	Connection connection = null;
	Statement statement = null;				
	ResultSet rs = null,rs_one=null,rs_two=null;		
	PreparedStatement ps = null;
	PreparedStatement ps1 = null,ps2=null,ps_two=null,pss=null;		

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
		connection = DriverManager.getConnection(ConnectionString,
				strdbusername.trim(), strdbpassword.trim());
		try {
			statement = connection.createStatement();
			connection.clearWarnings();
		} catch (SQLException e) {
			System.out.println("Exception in creating statement:" + e);
		}
	} catch (Exception e) {
		System.out.println("Exception in openeing connection:" + e);
	}		

	String strCommand = "";
	String xml = "";
                int counted=0;
	HttpSession session = request.getSession(false);
	try {

		if (session == null) {
			System.out.println(request.getContextPath() + "/index.jsp");
			response.sendRedirect(request.getContextPath() + "/index.jsp");

		}
		System.out.println(session);

	} catch (Exception e) {
		System.out.println("Redirect Error :" + e);
	}
	String userid = (String) session.getAttribute("UserId");
	System.out.println("User Id is:" + userid);
	try {
		System.out.println("chk 3");
		if (session == null) {
			System.out.println(request.getContextPath() + "/index.jsp");
			response.sendRedirect(request.getContextPath() + "/index.jsp");

		}
		System.out.println(session);

	} catch (Exception e) {
		System.out.println("Redirect Error :" + e);
	}

	try {
		strCommand = request.getParameter("command");
		System.out.println(strCommand);
	} catch (Exception e) {
		e.printStackTrace();
	}

	long l = System.currentTimeMillis();
	Timestamp ts = new Timestamp(l);
	if (strCommand.equalsIgnoreCase("unfreeze_part")) {
		xml = xml + "<response><command>unfreeze_part</command>";
		int part1=0,part2a=0,part2b=0,part2c=0;
		int c_one=0;
		
		int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
		int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
		int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
	    long cmbBankAccNo = Long.parseLong(request.getParameter("cmbBankAccNo"));
	    String reportType=request.getParameter("reportType");
      
        String s4="Select Count(*)As C_One,Accounting_Unit_Id From Fas_Brs_Monthly_Closure Where Accounting_Unit_Id    = " +cmbAcc_UnitCode+
        		" And Accounting_For_Office_Id="+cmbOffice_code+" AND Cashbook_Year           ="+txtCB_Year+" And Cashbook_Month          ="+txtCB_Month+" and ACCOUNT_NO="+cmbBankAccNo+" group by Accounting_Unit_Id";
			System.out.println("s4:"+s4);
			try{
			PreparedStatement ps_one=connection.prepareStatement(s4);
			rs_one=ps_one.executeQuery();
			while(rs_one.next())
			{
				part1=rs_one.getInt("c_one");
			}
			rs_one.close();
			ps_one.close();
				if(part1>0)
				{
					System.out.println("BRS Monthly Closure is Already Freezed");
					 xml=xml+"<proceed>stop</proceed>";
				}
				else
				{
					  xml=xml+"<proceed>start</proceed>";
					  if(reportType.equalsIgnoreCase("part1a"))
					  {
				
	                   String ff="Select Count(*)As C_One From Fas_Brs_Part1 Where Accounting_Unit_Id ="+cmbAcc_UnitCode+" AND Accounting_For_Office_Id="+cmbOffice_code+" AND Pass_Sheet_Year="+txtCB_Year+" AND Pass_Sheet_Month ="+txtCB_Month+" and account_no="+cmbBankAccNo;             
	                   System.out.println("ff:::"+ff);
	                   ps_two=connection.prepareStatement(ff);
	       			   rs_two=ps_two.executeQuery();
	       			   while(rs_two.next())
	       			   {
	       				   c_one=rs_two.getInt("C_One");
	       			   }
	       			   if(c_one==0)
	       			   {
	       				System.out.println("no need to Delete:"); 
	       				xml=xml+"<flag>norecords</flag>";
	       			   }
	       			   else
	       			   {
	       				xml=xml+"<flag>success</flag>";
	       				pss=connection.prepareStatement("delete from Fas_Brs_Part1 Where Accounting_Unit_Id =? AND Accounting_For_Office_Id=? AND Pass_Sheet_Year=? AND Pass_Sheet_Month =? and ACCOUNT_NO=?");
	       			   pss.setInt(1,cmbAcc_UnitCode);
	       			   pss.setInt(2,cmbOffice_code);
	       			   pss.setInt(3,txtCB_Year);
	       			   pss.setInt(4,txtCB_Month);
	       			   pss.setLong(5,cmbBankAccNo);
	       			   int kkk=pss.executeUpdate();
	       			   System.out.println("Fas_Brs_Part1 is deleted::::"+kkk);
		       			   if(kkk>0)
		       			   {
		       				  xml=xml+"<flag>deleted</flag>";
		       			   }
		       			   else
		       			   {
		       				xml=xml+"<flag>error</flag>";
		       			   }
	       			   }
	       			   
					  }
					  else  if(reportType.equalsIgnoreCase("part2a"))
					  {

							
		                   String twoa="Select Count(*)As C_One From FAS_BRS_PART_2A Where Accounting_Unit_Id ="+cmbAcc_UnitCode+" AND Accounting_For_Office_Id="+cmbOffice_code+" AND Pass_Sheet_Year="+txtCB_Year+" AND Pass_Sheet_Month ="+txtCB_Month+" and account_no="+cmbBankAccNo;             
		                   System.out.println("ff:::"+twoa);
		                   ps_two=connection.prepareStatement(twoa);
		       			   rs_two=ps_two.executeQuery();
		       			   while(rs_two.next())
		       			   {
		       				   c_one=rs_two.getInt("C_One");
		       			   }
		       			   if(c_one==0)
		       			   {
		       				System.out.println("no need to Delete:"); 
		       				xml=xml+"<flag>norecords</flag>";
		       			   }
		       			   else
		       			   {
			       				pss=connection.prepareStatement("delete from FAS_BRS_PART_2A Where Accounting_Unit_Id =? AND Accounting_For_Office_Id=? AND Pass_Sheet_Year=? AND Pass_Sheet_Month =? and ACCOUNT_NO=?");
			       			   pss.setInt(1,cmbAcc_UnitCode);
			       			   pss.setInt(2,cmbOffice_code);
			       			   pss.setInt(3,txtCB_Year);
			       			   pss.setInt(4,txtCB_Month);
			       			   pss.setLong(5,cmbBankAccNo);
			       			   int kkk=pss.executeUpdate();
			       			   System.out.println("FAS_BRS_PART_2A is deleted::::"+kkk);
				       			   if(kkk>0)
				       			   {
				       				  xml=xml+"<flag>deleted</flag>";
				       			   }
				       			   else
				       			   {
				       				xml=xml+"<flag>error</flag>";
				       			   }
		       			   }
					  }
					  else  if(reportType.equalsIgnoreCase("part2b"))
					  {

							
		                   String twob="Select Count(*)As C_One From FAS_BRS_PART_2B Where Accounting_Unit_Id ="+cmbAcc_UnitCode+" AND Accounting_For_Office_Id="+cmbOffice_code+" AND Pass_Sheet_Year="+txtCB_Year+" AND Pass_Sheet_Month ="+txtCB_Month+" and account_no="+cmbBankAccNo;                  
		                   System.out.println("twob:"+twob);
		                   ps_two=connection.prepareStatement(twob);
		       			   rs_two=ps_two.executeQuery();
		       			   while(rs_two.next())
		       			   {
		       				   c_one=rs_two.getInt("C_One");
		       			   }
		       			   if(c_one==0)
		       			   {
		       				System.out.println("no need to Delete:");  
		       				xml=xml+"<flag>norecords</flag>";
		       			   }
		       			   else
		       			   {
		       				pss=connection.prepareStatement("delete from FAS_BRS_PART_2B Where Accounting_Unit_Id =? AND Accounting_For_Office_Id=? AND Pass_Sheet_Year=? AND Pass_Sheet_Month =? and ACCOUNT_NO=?");
		       			   pss.setInt(1,cmbAcc_UnitCode);
		       			   pss.setInt(2,cmbOffice_code);
		       			   pss.setInt(3,txtCB_Year);
		       			   pss.setInt(4,txtCB_Month);
		       			   pss.setLong(5,cmbBankAccNo);
		       			   int kkk=pss.executeUpdate();
		       			   System.out.println("FAS_BRS_PART_2B is deleted::::"+kkk);
			       			   if(kkk>0)
			       			   {
			       				  xml=xml+"<flag>deleted</flag>";
			       			   }
			       			   else
			       			   {
			       				xml=xml+"<flag>error</flag>";
			       			   }
		       			   }
					  }
					  else  if(reportType.equalsIgnoreCase("part2c"))
					  {
	
		                   String twoc="Select Count(*)As C_One From FAS_BRS_PART_2C Where Accounting_Unit_Id ="+cmbAcc_UnitCode+" AND Accounting_For_Office_Id="+cmbOffice_code+" AND Pass_Sheet_Year="+txtCB_Year+" AND Pass_Sheet_Month ="+txtCB_Month+" and account_no="+cmbBankAccNo;            
		                   System.out.println("twoc:"+twoc);
		                   ps_two=connection.prepareStatement(twoc);
		       			   rs_two=ps_two.executeQuery();
		       			   while(rs_two.next())
		       			   {
		       				   c_one=rs_two.getInt("C_One");
		       			
		       			   }
		       			   if(c_one==0)
		       			   {
		       				System.out.println("no need to Delete:");   
		       				xml=xml+"<flag>norecords</flag>";
		       			   }
		       			   else
		       			   {
		       				pss=connection.prepareStatement("delete from FAS_BRS_PART_2C Where Accounting_Unit_Id =? AND Accounting_For_Office_Id=? AND Pass_Sheet_Year=? AND Pass_Sheet_Month =? and ACCOUNT_NO=?");
		       			   pss.setInt(1,cmbAcc_UnitCode);
		       			   pss.setInt(2,cmbOffice_code);
		       			   pss.setInt(3,txtCB_Year);
		       			   pss.setInt(4,txtCB_Month);
		       			   pss.setLong(5,cmbBankAccNo);
		       			   int kkk=pss.executeUpdate();
		       			   System.out.println("FAS_BRS_PART_2C is deleted::::"+kkk);
			       			   if(kkk>0)
			       			   {
			       				  xml=xml+"<flag>deleted</flag>";
			       			   }
			       			   else
			       			   {
			       				xml=xml+"<flag>error</flag>";
			       			   }
		       			   }
					  }
			
	                
		         }
			
			}
			 catch(Exception e) 
		       {
		              System.out.println("Exception in onload..."+e);
		              xml=xml+"<flag>failure</flag>";
		       }
	
	
	}
	
	xml = xml + "</response>";
	out.write(xml);
	System.out.println(xml);		
	
	
	
	}
	
	
	
	
	

}
