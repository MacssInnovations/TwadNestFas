package Servlets.FAS.FAS1.CivilBudget.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Civil_Budget_Statement_1
 */
public class Civil_Budget_Statement_1 extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Civil_Budget_Statement_1() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();

		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null,rsss=null;
		ResultSet rs2 = null,results4=null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null,psss=null;
		PreparedStatement ps2 = null,ps4=null;
		int cashbookYear = 0;
		String cashbookMonth = null;
		int unitid = 0;
		String unitname = "";
		int accid = 0;

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

		HttpSession session = request.getSession(false);

		try {

			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}

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
			System.out.println("strCommand:-" + strCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
				.getAttribute("UserProfile");
		int empid = empProfile.getEmployeeId();
		String empName = empProfile.getEmployeeName();
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
		if (strCommand.equalsIgnoreCase("getStatementName")) {
			xml = "<response><command>getStatementName</command>";
			try {
				ps = connection
						.prepareStatement("select STATEMENT_NO,STATEMENT_DESC from FAS_STATEMENT_MASTER order by STATEMENT_NO");
				results = ps.executeQuery();
				while (results.next()) {
					xml = xml + "<STATEMENT_NO>"
							+ results.getString("STATEMENT_NO")
							+ "</STATEMENT_NO>";
					xml = xml + "<STATEMENT_DESC>"
							+ results.getString("STATEMENT_DESC")+" ( "+results.getString("STATEMENT_NO")+" )"
							+ "</STATEMENT_DESC>";
				}

				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		}
		else if (strCommand.equalsIgnoreCase("CheckFreeze")) {
			int count = 0;
			xml = "<response><command>CheckFreeze</command>";
			String financialYear = request.getParameter("cmbFinancialYear");			
			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			int cmbStatementName = Integer.parseInt(request
					.getParameter("cmbStatementName"));
			String sql = "";
			try {
				sql="SELECT 'X' " +
				"FROM FAS_BUDGET_ALLOCATION_HO_CLOSE " +
				"WHERE "+//ACCOUNTING_UNIT_ID    =? AND " +
				"ACCOUNTING_FOR_OFFICE_ID=? " +
				"AND FINANCIAL_YEAR          =? " +
				"AND STATEMENT_NAME          =?";
				ps1 = connection.prepareStatement(sql);
				//ps1.setInt(1, cmbAcc_UnitCode);
				ps1.setInt(1, cmbOffice_code);
				ps1.setString(2, financialYear);
				ps1.setInt(3, cmbStatementName);
				results = ps1.executeQuery();
				if(results.next()){
					xml += "<flag>Freeze</flag>";
					
				}else{
					xml += "<flag>NotFreeze</flag>";
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}

		}
		else if (strCommand.equalsIgnoreCase("groupch")) 
		{

			int count = 0;
			xml = "<response><command>groupch</command>";
			int statementno = Integer.parseInt(request.getParameter("statement"));			
			
			String sql = "";
			try {
				sql="Select Statement_Group_No,STATEMENT_GROUP_DESC from FAS_STATEMENT_GROUP_MASTER where STATEMENT_NAME="+statementno+" order by Statement_Group_No";
				System.out.println(sql);
				ps1 = connection.prepareStatement(sql);
				
				results = ps1.executeQuery();
				while(results.next()){
					xml = xml + "<gp_no>"+ results.getInt("Statement_Group_No")+ "</gp_no>";
					xml = xml + "<gp_desc>"+ results.getString("STATEMENT_GROUP_DESC")+ "</gp_desc>";
					count++;
				}
				if(count>0){
					xml += "<flag>success</flag>";
				}else{
					xml += "<flag>failure</flag>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		}
		
		
		else if (strCommand.equalsIgnoreCase("callHead")) 
		{
			
			int count = 0;
			xml = "<response><command>callHead</command>";
			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));			
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			
			String fyear =request.getParameter("cmbFinancialYear");
			System.out.println("fyear"+fyear);
			int cmbStatementName = Integer.parseInt(request.getParameter("cmbStatementName"));
			System.out.println("cmbStatementName"+cmbStatementName);
			int statementGp = Integer.parseInt(request.getParameter("statementGp"));
			System.out.println("statementGp"+statementGp);
			String sql = "";
			
			try {
				sql="Select Case When Group_Type='H' Then From_Acc_Hd_Code||'' "+ 
			" When Group_Type='G' Then From_Acc_Hd_Code||' to '||To_Acc_Hd_Code  "+
			" End As Range_Of_Heads "+
			" From Fas_Statement_Acc_Hd_Mapping Where Statement_No="+cmbStatementName+" And " +
			" Statement_Group_No="+statementGp+" order by From_Acc_Hd_Code";
				System.out.println(sql);
				ps1 = connection.prepareStatement(sql);
				
				results = ps1.executeQuery();
				while(results.next()){
					xml = xml + "<Range_Of_Heads>"+ results.getString("Range_Of_Heads")+ "</Range_Of_Heads>";
					count++;
				}
				if(count>0){
					xml += "<flag>success</flag>";
					
					
				}
				else{
					xml += "<flag>failure</flag>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		}
		else if (strCommand.equalsIgnoreCase("loadAmt")) 
		{
			
			int count = 0;
			xml = "<response><command>loadAmt</command>";
			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));			
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			
			String fyear =request.getParameter("cmbFinancialYear");
			System.out.println("fyear"+fyear);
			int cmbStatementName = Integer.parseInt(request.getParameter("cmbStatementName"));
			System.out.println("cmbStatementName"+cmbStatementName);
			int statementGp = Integer.parseInt(request.getParameter("statementGp"));
			System.out.println("statementGp"+statementGp);
			String sql = "";
			int cou=0;
			try {
				sql="Select Amount From Fas_Budget_Allocation Where "+ 
			" ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" And " +
			" Financial_Year= '"+fyear+"' And Statement_No="+cmbStatementName+" and STATMENT_GROUP_NO="+statementGp;
				System.out.println(sql);
				ps1 = connection.prepareStatement(sql);
				
				results = ps1.executeQuery();
				while(results.next()){
					xml = xml + "<amt>"+ results.getString("Amount")+ "</amt>";
					count++;
				}
				if(count>0){
					xml += "<flag>success</flag>";
					
					
				}
				else{
					xml += "<flag>failure</flag>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		}
		
		
		else if(strCommand.equalsIgnoreCase("getofficeid"))
		{
			xml = "<response><command>getofficeid</command>";
			try {
				ps = connection
						.prepareStatement("SELECT accounting_unit_id, "+
				 " trim(accounting_unit_name) AS accounting_unit_name "+
            	" FROM fas_mst_acct_units "+
            	" WHERE  ACCOUNTING_UNIT_ID =888 "+
            	 	" ORDER BY accounting_unit_name");
				results = ps.executeQuery();
				while (results.next()) {
					xml = xml + "<ACCOUNTING_UNIT_ID>"
							+ results.getString("ACCOUNTING_UNIT_ID")
							+ "</ACCOUNTING_UNIT_ID>";
					xml = xml + "<ACCOUNTING_UNIT_NAME>"
							+ results.getString("ACCOUNTING_UNIT_NAME")+" ( "+results.getString("ACCOUNTING_UNIT_ID")+" )"
							+ "</ACCOUNTING_UNIT_NAME>";
				}

				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		}
		else if(strCommand.equalsIgnoreCase("getOfficeName"))
		{
			int UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			xml = "<response><command>getOfficeName</command>";
			try {
				ps = connection
						.prepareStatement("SELECT accounting_unit_id, "+
								 " trim(accounting_unit_name) AS accounting_unit_name "+
					            	" FROM fas_mst_acct_units "+
					            	" WHERE  ACCOUNTING_UNIT_OFFICE_ID=5000 and accounting_unit_id <> '"+UnitCode+"' ORDER BY accounting_unit_name"); 
				results = ps.executeQuery();
				while (results.next()) {
					xml = xml + "<ACCOUNTING_UNIT_ID>"
							+ results.getString("accounting_unit_id")
							+ "</ACCOUNTING_UNIT_ID>";
					xml = xml + "<ACCOUNTING_UNIT_NAME>"
							+ results.getString("ACCOUNTING_UNIT_NAME")
							+ "</ACCOUNTING_UNIT_NAME>";
				}

				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		}
		
		else if (strCommand.equalsIgnoreCase("get_head_office")) {
			xml = "<response><command>get_head_office</command>";
			double ttl_amount=0.0;
			int year1 = Integer.parseInt(request.getParameter("y1"));
			int year2 = Integer.parseInt(request.getParameter("y2"));
			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			String cmbStatementName = request.getParameter("cmbStatementName").toString();
			String fy = request.getParameter("fy");
			int already=0;
			
			try{
				String ss = " Select B.Accounting_For_Office_Id,  "+
				 " (Select Office_Name From Com_Mst_Offices S Where Office_Level_Id In ('RN','HO')   "+
					"  And Office_Status_Id Not In('CL','RD','NC') And S.Office_Id=B.Accounting_For_Office_Id)As Office_Name,  "+
					"  B.Statement_No,  "+
					"  (Select Statement_Desc From Fas_Statement_Master M Where M.Statement_No=B.Statement_No)As State_Name,  "+
					"   Financial_Year,  "+
					"   B.Statment_Group_No,  "+
					"   (Select R.Statement_Group_Desc From Fas_Statement_Group_Master R  "+ 
					"   Where R.Statement_Name=B.Statement_No And R.Statement_Group_No=B.Statment_Group_No)As Group_Desc,  "+
					//"  (Select R.Statement_Sub_Group_Dec From Fas_Statement_Sub_Group_Master R  "+ 
					//"   Where R.Statement_No=B.Statement_No And R.Statement_Group_No=B.Statment_Group_No  "+ 
					//"   and R.Statement_Sub_Group_No=b.Statement_Sub_Group_No)as sub_group_desc,  "+
					"     Budget_Type,  "+
					" Allocation_Date,  "+
					" REF_NO,  "+
					" Approved_By,  "+
					" Remarks,  "+
					" AMOUNT  "+
					"  From Fas_Budget_Allocation b  "+
					"  Where Accounting_Unit_Id     =   "+cmbAcc_UnitCode+
					" And Accounting_For_Office_Id = "+cmbOffice_code+
					"  And Financial_Year           =   '"+fy+"'"+
					"  AND STATEMENT_NO           = "+cmbStatementName;
			System.out.println("ss:::"+ss);
			ps = connection.prepareStatement(ss);
			
			rs = ps.executeQuery();
					while (rs.next()) 
					{
						already++;
						xml = xml + "<flag>success</flag>";
						xml = xml + "<already>"+already+ "</already>";
						xml=xml+"<Remarks><![CDATA["+rs.getString("Remarks")+"]]></Remarks>";
						xml = xml + "<REF_NO>"+ rs.getString("REF_NO")+ "</REF_NO>";
						xml = xml + "<STATEMENT_GROUP_NO>"+ rs.getInt("Statment_Group_No")+ "</STATEMENT_GROUP_NO>";
						xml=xml+"<STATEMENT_GROUP_DESC><![CDATA["+rs.getString("Group_Desc")+"]]></STATEMENT_GROUP_DESC>";
						xml = xml + "<amount>"+ rs.getString("AMOUNT")+ "</amount>";
						ttl_amount=ttl_amount+rs.getDouble("AMOUNT");
					}
					if(already==0)
					{
						xml = xml + "<already>"+already+ "</already>";
						String sql = "SELECT Statement_Group_No,Statement_Group_Desc " +
								" From Fas_Statement_Group_Master where Statement_Name    = "+cmbStatementName+" Order " +
								" By Statement_Group_No";
						System.out.println(sql);
						psss = connection.prepareStatement(sql);
						rsss = psss.executeQuery();
						while (rsss.next()) {
							xml = xml + "<flag>success</flag>";
							xml = xml + "<STATEMENT_GROUP_NO>"+ rsss.getInt("STATEMENT_GROUP_NO")+ "</STATEMENT_GROUP_NO>";
							xml=xml+"<STATEMENT_GROUP_DESC><![CDATA["+rsss.getString("STATEMENT_GROUP_DESC")+"]]></STATEMENT_GROUP_DESC>";
							
						}
						xml = xml + "<flag>success</flag>";
					}
					else
					{
						xml = xml + "<ttl_amount>"+ ttl_amount+ "</ttl_amount>";
						System.out.println("ttl_amount:::"+ttl_amount);	
					}
					
			}
			catch(Exception ee)
			{
				System.out.println("excep in load:"+ee);
			}

		}
		//not for 5000
		else if (strCommand.equalsIgnoreCase("get")) {
			xml = "<response><command>get</command>";
			int year1 = Integer.parseInt(request.getParameter("y1"));
			int year2 = Integer.parseInt(request.getParameter("y2"));
			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			String cmbStatementName = request.getParameter("cmbStatementName").toString();
			String fy = request.getParameter("fy");
			int already=0;
			double ttl_amount=0.0;
			
			try{
				String ss = " Select B.Accounting_For_Office_Id,  "+
				 " (Select Office_Name From Com_Mst_Offices S Where Office_Level_Id In ('RN','HO')   "+
					"  And Office_Status_Id Not In('CL','RD','NC') And S.Office_Id=B.Accounting_For_Office_Id)As Office_Name,  "+
					"  B.Statement_No,  "+
					"  (Select Statement_Desc From Fas_Statement_Master M Where M.Statement_No=B.Statement_No)As State_Name,  "+
					"   Financial_Year,  "+
					"   B.Statment_Group_No,  "+
					"   (Select R.Statement_Group_Desc From Fas_Statement_Group_Master R  "+ 
					"   Where R.Statement_Name=B.Statement_No And R.Statement_Group_No=B.Statment_Group_No)As Group_Desc,  "+
					"     b.Statement_Sub_Group_No,  "+
					"  (Select R.Statement_Sub_Group_Dec From Fas_Statement_Sub_Group_Master R  "+ 
					"   Where R.Statement_No=B.Statement_No And R.Statement_Group_No=B.Statment_Group_No  "+ 
					"   and R.Statement_Sub_Group_No=b.Statement_Sub_Group_No)as sub_group_desc,  "+
					"     Budget_Type,  "+
					" Allocation_Date,  "+
					" REF_NO,  "+
					" Approved_By,  "+
					" Remarks,  "+
					" AMOUNT  "+
					"  From Fas_Budget_Allocation b  "+
					"  Where Accounting_Unit_Id     =   "+cmbAcc_UnitCode+
					" And Accounting_For_Office_Id = "+cmbOffice_code+
					"  And Financial_Year           =   '"+fy+"'"+
					"  AND STATEMENT_NO           = "+cmbStatementName;
			System.out.println("ss:::"+ss);
			ps = connection.prepareStatement(ss);
			
			rs = ps.executeQuery();
					while (rs.next()) 
					{
						already++;
						xml = xml + "<flag>success</flag>";
						xml = xml + "<already>"+already+ "</already>";
						xml=xml+"<STATEMENT_GROUP_DESC><![CDATA["+rs.getString("Group_Desc")+"]]></STATEMENT_GROUP_DESC>";
						xml=xml+"<Remarks><![CDATA["+rs.getString("Remarks")+"]]></Remarks>";
						xml = xml + "<REF_NO>"+ rs.getString("REF_NO")+ "</REF_NO>";
						xml=xml+"<STATEMENT_GROUP_DESC><![CDATA["+rs.getString("Group_Desc")+"]]></STATEMENT_GROUP_DESC>";
						xml = xml + "<Statement_Sub_Group_No>"+ rs.getInt("Statement_Sub_Group_No")+ "</Statement_Sub_Group_No>";
						xml=xml+"<Statement_Sub_Group_Dec><![CDATA["+rs.getString("sub_group_desc")+"]]></Statement_Sub_Group_Dec>";
						xml = xml + "<amount>"+ rs.getString("AMOUNT")+ "</amount>";
						ttl_amount=ttl_amount+rs.getDouble("AMOUNT");
					}
					if(already==0)
					{
						xml = xml + "<already>"+already+ "</already>";
						String sql = " Select A.Statement_Group_No, "+
						"	  a.Statement_Group_Desc,B.Statement_Sub_Group_No,B.Statement_Sub_Group_Dec "+
							"	From Fas_Statement_Group_Master A Inner Join Fas_Statement_Sub_Group_Master B "+
							"	On A.Statement_Name=B.Statement_No "+
							"		And A.Statement_Group_No=B.Statement_Group_No "+
							"		And A.Statement_Name= "+cmbStatementName+
							"		ORDER BY a.STATEMENT_GROUP_NO  ";
						System.out.println(sql);
						psss = connection.prepareStatement(sql);
						rsss = psss.executeQuery();
						while (rsss.next()) {
							xml = xml + "<flag>success</flag>";
							xml = xml + "<STATEMENT_GROUP_NO>"+ rsss.getInt("STATEMENT_GROUP_NO")+ "</STATEMENT_GROUP_NO>";
							xml=xml+"<STATEMENT_GROUP_DESC><![CDATA["+rsss.getString("STATEMENT_GROUP_DESC")+"]]></STATEMENT_GROUP_DESC>";
							xml = xml + "<Statement_Sub_Group_No>"+ rsss.getInt("Statement_Sub_Group_No")+ "</Statement_Sub_Group_No>";
							xml=xml+"<Statement_Sub_Group_Dec><![CDATA["+rsss.getString("Statement_Sub_Group_Dec")+"]]></Statement_Sub_Group_Dec>";
							
						}
						xml = xml + "<flag>success</flag>";
					}
					else
					{
						xml = xml + "<ttl_amount>"+ ttl_amount+ "</ttl_amount>";
					System.out.println("ttl_amount:::"+ttl_amount);	
					}
					
			}
			catch(Exception ee)
			{
				System.out.println("excep in load:"+ee);
			}

			

		/*	try {
				ps1 = connection.prepareStatement("select UPDATED_DATE from FAS_BUDGET_CLOSURE_ALLOCATION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and STATEMENT_NAME=?");
				ps1.setInt(1, cmbAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setString(3, (year1) + "-" + (year2));
				ps1.setString(4, cmbStatementName);
				results = ps1.executeQuery();
				if (results.next()) {
					xml = xml + "<flag>Exist</flag>";
				} else {
					ps = connection.prepareStatement(sql);

					//ps.setString(1, cmbStatementName);

					rs = ps.executeQuery();
					while (rs.next()) {
						xml = xml + "<STATEMENT_GROUP_NO>"+ rs.getInt("STATEMENT_GROUP_NO")+ "</STATEMENT_GROUP_NO>";
						xml=xml+"<STATEMENT_GROUP_DESC><![CDATA["+rs.getString("STATEMENT_GROUP_DESC")+"]]></STATEMENT_GROUP_DESC>";
						xml = xml + "<Statement_Sub_Group_No>"+ rs.getInt("Statement_Sub_Group_No")+ "</Statement_Sub_Group_No>";
						xml=xml+"<Statement_Sub_Group_Dec><![CDATA["+rs.getString("Statement_Sub_Group_Dec")+"]]></Statement_Sub_Group_Dec>";
						
					}
					xml = xml + "<flag>success</flag>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}  */

		}
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/**
		 * Session Checking
		 */
		HttpSession session = request.getSession(false);
		try {

			if (session == null) {

				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");
				return;
			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}

		/**
		 * Variables Declaration
		 */

		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null,prep_st=null;
		ResultSet rs = null;
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		 Calendar c,c1;
		 int kk=0;
		/**
		 * Database Connection
		 */
		try {
			ResourceBundle rs1 = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rs1.getString("Config.DSN");
			String strhostname = rs1.getString("Config.HOST_NAME");
			String strportno = rs1.getString("Config.PORT_NUMBER");
			String strsid = rs1.getString("Config.SID");
			String strdbusername = rs1.getString("Config.USER_NAME");
			String strdbpassword = rs1.getString("Config.PASSWORD");
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);
		}

		String userid = (String) session.getAttribute("UserId");
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);

		Date date = new Date(0000 - 00 - 00);
		String strCommand = "";
		String xml = "";

		/* Get Parameters */
		int cmbAcc_UnitCode = 0;
		int cmbOffice_code = 0;
		int cmbStatementName = 0;
		String FinancialYear = null,budgettype=null;
		/* Get Accounting Unit ID */
		try {
			cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		} catch (Exception e) {
			System.out.println("Error Not Getting Accounitng Unit ID --> " + e);
		}

		/* Get Accounting for Office ID */
		try {
			cmbOffice_code = Integer.parseInt(request.getParameter("txtRegionId"));

		} catch (Exception e) {
			System.out
					.println("Error Not Getting Accounting for Office Id --> "
							+ e);
		}

		/* Get FinancialYear */
		try {
			FinancialYear = request.getParameter("cmbFinancialYear");
		} catch (Exception e) {
			System.out.println("Error Not Getting Financial Year -->" + e);
		}

		/* cmbStatementName */
		try {
			cmbStatementName = Integer.parseInt(request.getParameter("cmbStatementName"));

		} catch (Exception e) {
			System.out.println("Error Not Getting cmbStatementName --> " + e);
		}
		System.out.println("cmbStatementName::"+cmbStatementName);
		try {
			budgettype = request.getParameter("budgettype");

		} catch (Exception e) {
			System.out.println("Error Not Getting budgettype --> " + e);
		}
		
		  String[] sd=request.getParameter("txtCrea_date").split("/");
          c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
          java.util.Date d=c.getTime();
          Date txtCrea_date=new Date(d.getTime());
          System.out.println("txtCrea_date "+txtCrea_date);
		
		int RecordCount =0;
		String approved="";
		String filter = null,remarks=null,refno=null;
		/*
		 * Get Total Number of Records
		 */
		try {
			filter = request.getParameter("filter");
		} catch (Exception e) {
			System.out.println("Error Getting filter value ");
		}
		
		try {
			approved = request.getParameter("app");
		} catch (Exception e) {
			System.out.println("Error Getting approved by ");
		}
		System.out.println("approved:::"+approved);
		try {
			remarks = request.getParameter("rem");
		} catch (Exception e) {
			System.out.println("Error Getting rem ");
		}
		
		
		try {
			refno = request.getParameter("refno");
		} catch (Exception e) {
			System.out
					.println("Error Getting refno");
		}
		/*
		 * Get filter value
		 */
		try {
			RecordCount = Integer.parseInt(request.getParameter("RecordCount"));
		} catch (Exception e) {
			System.out
					.println("Error Getting Total Number of Records in TWAD Transaction ");
		}

		/* String Array Declaration */
		String Head_of_Account[] = new String[RecordCount];
		String sub_group[] = new String[RecordCount];
		String Amount[] = new String[RecordCount];

		/* Variables Declaration */
		int Head_of_Account2 = 0,sub_group_number=0;
		double Amount2 = 0.0d;
		int flag = 0;

		try {
			con.setAutoCommit(false);
			con.clearWarnings();
			if (filter.equals("save")) {

				ps = con.prepareStatement("SELECT ACCOUNTING_UNIT_ID, "+
						 " ACCOUNTING_FOR_OFFICE_ID,  "+
						"  FINANCIAL_YEAR "+
						" From Fas_Budget_Allocation "+
						" Where Accounting_Unit_Id    =? "+
						" And Accounting_For_Office_Id=? "+
						" And Financial_Year          =? "+
						" And Statement_No            =? "+
						" And Statment_Group_No in(Select Statement_Group_No as gno "+
								" From Fas_Statement_Group_Master Where Statement_Name="+cmbStatementName+")");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setString(3, FinancialYear);
				ps.setInt(4, cmbStatementName);
				
				rs = ps.executeQuery();
				if (rs.next()) {
					flag = 1;
					prep_st=con.prepareStatement("Delete From Fas_Budget_Allocation Where Accounting_Unit_Id="+cmbAcc_UnitCode+" And " +
							" Accounting_For_Office_Id= " +cmbOffice_code+
							" And Financial_Year='"+FinancialYear+"' And  STATEMENT_NO="+cmbStatementName);
					int kk1=prep_st.executeUpdate();
					if(kk1>0)
					{
					//	con.commit();
					}
				} 
				
					for (int k = 0; k < RecordCount; k++) {

						/* Head of Account */
						try {
							Head_of_Account[k] = request
									.getParameter("Statement_Group_No" + k);
							if (Head_of_Account[k] != null) {
								if (Head_of_Account[k].equals("")) {
									Head_of_Account2 = 0;
								} else {
									Head_of_Account2 = Integer
											.parseInt(Head_of_Account[k]);
								}
							} else {
								Head_of_Account2 = 0;
							}
						} catch (Exception e) {
							System.out
									.println("Error for getting Head_of_Account -->"
											+ e);
						}
						System.out.println("Head_of_Account2::::::::::::::::::::"+Head_of_Account2);
						try {
							sub_group[k] = request
									.getParameter("Statement_sub_Group_No" + k);
							if (sub_group[k] != null) {
								if (sub_group[k].equals("")) {
									sub_group_number = 0;
								} else {
									sub_group_number = Integer
											.parseInt(sub_group[k]);
								}
							} else {
								sub_group_number = 0;
							}
						} catch (Exception e) {
							System.out
									.println("Error for getting Head_of_Account -->"
											+ e);
						}
						System.out.println("sub_group_number::::::::::::::::::::"+sub_group_number);
						/* Amount */
						try {
							Amount[k] = request.getParameter("Amount" + k);
							if (Amount[k] != null) {
								if (Amount[k].equals("")) {
									Amount2 = 0.0d;
								} else {
									Amount2 = Double.parseDouble(Amount[k]);
								}
							} else {
								Amount2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						ps = con.prepareStatement("insert into FAS_BUDGET_ALLOCATION (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,STATEMENT_NO,AMOUNT,UPDATED_BY_USERID,UPDATED_DATE,BUDGET_TYPE,ALLOCATION_DATE,REF_NO,APPROVED_BY,REMARKS,STATMENT_GROUP_NO,STATEMENT_SUB_GROUP_NO) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, FinancialYear);
						ps.setInt(4, cmbStatementName);
						ps.setDouble(5, Amount2);
						ps.setString(6, userid);
						ps.setTimestamp(7, ts);
						ps.setString(8, budgettype);
						ps.setDate(9, txtCrea_date);
						ps.setString(10, refno);
						ps.setString(11, approved);
						ps.setString(12, remarks);
						ps.setInt(13, Head_of_Account2);
						ps.setInt(14, sub_group_number);
						kk = ps.executeUpdate();
					}
				if (kk>0) {
					con.commit();
					sendMessage(response,"Records Saved Successfully ............ ", "ok","Civil_Budget_Statement_1.jsp");
				}
				else
				{
					con.rollback();
					sendMessage(response,"Records Not Inserted For The Financial Year ............ ","ok", "Civil_Budget_Statement_1.jsp");	
				}
			} 
			else if (filter.equals("view")) {
				try {
					xml = "<response><command>LoadData</command>";
					ps1 = con
							.prepareStatement("select UPDATED_DATE from FAS_BUDGET_CLOSURE_ALLOCATION where ACCOUNTING_UNIT_ID=? and FINANCIAL_YEAR=? and STATEMENT_NAME=?");
					ps1.setInt(1, cmbAcc_UnitCode);
					//ps1.setInt(2, cmbOffice_code);
					ps1.setString(2, FinancialYear);
					ps1.setInt(3, cmbStatementName);
					rs = ps1.executeQuery();
					if (rs.next()) {
						xml = xml + "<flag>Exist</flag>";
					} else {
						String ss = " Select B.Accounting_For_Office_Id,  "+
							 " (Select Office_Name From Com_Mst_Offices S Where Office_Level_Id In ('RN','HO')   "+
								"  And Office_Status_Id Not In('CL','RD','NC') And S.Office_Id=B.Accounting_For_Office_Id)As Office_Name,  "+
								"  B.Statement_No,  "+
								"  (Select Statement_Desc From Fas_Statement_Master M Where M.Statement_No=B.Statement_No)As State_Name,  "+
								"   Financial_Year,  "+
								"   B.Statment_Group_No,  "+
								"   (Select R.Statement_Group_Desc From Fas_Statement_Group_Master R  "+ 
								"   Where R.Statement_Name=B.Statement_No And R.Statement_Group_No=B.Statment_Group_No)As Group_Desc,  "+
								"     b.Statement_Sub_Group_No,  "+
								"  (Select R.Statement_Sub_Group_Dec From Fas_Statement_Sub_Group_Master R  "+ 
								"   Where R.Statement_No=B.Statement_No And R.Statement_Group_No=B.Statment_Group_No  "+ 
								"   and R.Statement_Sub_Group_No=b.Statement_Sub_Group_No)as sub_group_desc,  "+
								"     Budget_Type,  "+
								" Allocation_Date,  "+
								" REF_NO,  "+
								" Approved_By,  "+
								" Remarks,  "+
								" AMOUNT  "+
								"  From Fas_Budget_Allocation b  "+
								"  Where Accounting_Unit_Id     = ?  "+
								" And Accounting_For_Office_Id = ?  "+
								"  And Financial_Year           = ?  "+
								"  AND STATEMENT_NO           = ? ";
						System.out.println("ss:::"+ss);
						ps = con.prepareStatement(ss);
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, FinancialYear);
						ps.setInt(4, cmbStatementName);
						rs = ps.executeQuery();
						while (rs.next()) {
							
							xml = xml + "<STATEMENT_GROUP_NO>"
									+ rs.getInt("Statment_Group_No")
									+ "</STATEMENT_GROUP_NO>";

							xml = xml + "<STATEMENT_GROUP_DESC>"
									+ rs.getString("Group_Desc")
									+ "</STATEMENT_GROUP_DESC>";
							
							xml = xml + "<sub_group>"
							+ rs.getInt("Statement_Sub_Group_No")
							+ "</sub_group>";
	
						xml = xml + "<sub_GROUP_DESC>"
								+ rs.getString("sub_group_desc")
								+ "</sub_GROUP_DESC>";
								
						xml = xml + "<Budget_Type>"
						+ rs.getString("Budget_Type")
						+ "</Budget_Type>";
	
						xml = xml + "<Allocation_Date>"
								+ rs.getString("Allocation_Date")
								+ "</Allocation_Date>";
						xml = xml + "<REF_NO>"
						+ rs.getString("REF_NO")
						+ "</REF_NO>";
	
						xml = xml + "<Approved_By>"+ rs.getString("Approved_By")+ "</Approved_By>";
						xml = xml + "<Remarks>"+ rs.getString("Remarks")+ "</Remarks>";
					
							xml = xml + "<AMOUNT>"
							+ rs.getBigDecimal("AMOUNT")
							+ "</AMOUNT>";
							

						}

						xml = xml + "<flag>success</flag>";
					}
				} catch (Exception e) {
					e.printStackTrace();
					xml = xml + "<flag>failure</flag>";
				}
				xml = xml + "</response>";
				out.write(xml);
				System.out.println(xml);
			} else if (filter.equals("delete")) {
				try {
					ps = con
							.prepareStatement(" select * from FAS_BUDGET_ALLOCATION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and STATEMENT_NAME=?");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setString(3, FinancialYear);
					ps.setInt(4, cmbStatementName);
					rs = ps.executeQuery();
					if (rs.next()) {
						ps1 = con
								.prepareStatement(" delete from FAS_BUDGET_ALLOCATION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and STATEMENT_NAME=?");
						ps1.setInt(1, cmbAcc_UnitCode);
						ps1.setInt(2, cmbOffice_code);
						ps1.setString(3, FinancialYear);
						ps1.setInt(4, cmbStatementName);
						int sbg = ps1.executeUpdate();
						if (sbg > 0) {
							con.commit();
							sendMessage(
									response,
									"Records Deleted Successfully ............ ",
									"ok", "Civil_Budget_Statement_1.jsp");
						} else {
							sendMessage(response,
									"Records Deletion Failed ............ ",
									"ok", "Civil_Budget_Statement_1.jsp");
						}
					} else {
						sendMessage(response,
								"Records Does Not Exist ............ ", "ok",
								"Civil_Budget_Statement_1.jsp");
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Deletion Failed ............ " + e, "ok",
							"Civil_Budget_Statement_1.jsp");
					try {
						con.rollback();
						con.setAutoCommit(true);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			} else if (filter.equals("update")) {
				try {

					ps = con
							.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR from FAS_BUDGET_ALLOCATION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and STATEMENT_NAME=?");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setString(3, FinancialYear);
					ps.setInt(4, cmbStatementName);
					rs = ps.executeQuery();
					if (rs.next()) {
						ps1 = con
								.prepareStatement(" delete from FAS_BUDGET_ALLOCATION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and STATEMENT_NAME=?");
						ps1.setInt(1, cmbAcc_UnitCode);
						ps1.setInt(2, cmbOffice_code);
						ps1.setString(3, FinancialYear);
						ps1.setInt(4, cmbStatementName);
						ps1.executeUpdate();
						for (int k = 0; k < RecordCount; k++) {

							/* Head of Account */
							try {
								Head_of_Account[k] = request
										.getParameter("Statement_Group_No" + k);
								if (Head_of_Account[k] != null) {
									if (Head_of_Account[k].equals("")) {
										Head_of_Account2 = 0;
									} else {
										Head_of_Account2 = Integer
												.parseInt(Head_of_Account[k]);
									}
								} else {
									Head_of_Account2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting Head_of_Account -->"
												+ e);
							}

							/* Amount */
							try {
								Amount[k] = request.getParameter("Amount" + k);
								if (Amount[k] != null) {
									if (Amount[k].equals("")) {
										Amount2 = 0.0d;
									} else {
										Amount2 = Double.parseDouble(Amount[k]);
									}
								} else {
									Amount2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							ps = con
									.prepareStatement("insert into FAS_BUDGET_ALLOCATION (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,STATEMENT_NAME,HEAD_OF_ACCOUNT,AMOUNT,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?)");
							ps.setInt(1, cmbAcc_UnitCode);
							ps.setInt(2, cmbOffice_code);
							ps.setString(3, FinancialYear);
							ps.setInt(4, cmbStatementName);
							ps.setInt(5, Head_of_Account2);
							ps.setDouble(6, Amount2);
							ps.setString(7, userid);
							ps.setTimestamp(8, ts);
							kk = ps.executeUpdate();
						}
						con.commit();
						sendMessage(response,
								"Records Updated Successfully ............ ",
								"ok", "Civil_Budget_Statement_1.jsp");
					} else {
						sendMessage(response,
								"Records Does Not Exist ............ ", "ok",
								"Civil_Budget_Statement_1.jsp");
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Updation Failed ............ " + e, "ok",
							"Civil_Budget_Statement_1.jsp");
					try {
						con.rollback();
						con.setAutoCommit(true);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}

		} catch (Exception e) {
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			sendMessage(response, "Records Not Inserted ............ " + e,
					"ok", "Civil_Budget_Statement_1.jsp");
		}

	}

	private void sendMessage(HttpServletResponse response, String msg,
			String bType, String jsp) {
		try {
			String url = "org/FAS/FAS1/CivilBudget/jsps/MessengerOkBack.jsp?message="
					+ msg + "&button=" + bType + "&jspname=" + jsp;
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
