package Servlets.FAS.FAS1.CivilBudget.servlets;

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

import Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver;

/**
 * Servlet implementation class Civil_Budget_Statement_1_Reg_to_Cir_and_Div
 */
public class Civil_Budget_Statement_1_Reg_to_Cir_and_Div extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Civil_Budget_Statement_1_Reg_to_Cir_and_Div() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("own servlet");
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();

		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null,rss1=null;
		ResultSet rs2 = null,results_one=null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
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
		if (strCommand.equalsIgnoreCase("LoadGrid_Head")) {
			xml = "<response><command>LoadGrid_Head</command>";
			int cmbStatementName = Integer.parseInt(request.getParameter("cmbStatementName"));
			/*String sql = "select STATEMENT_GROUP_NO,STATEMENT_GROUP_DESC,ACC_HD_CODE from "
					+ "( select STATEMENT_GROUP_NO,ACC_HD_CODE from FAS_STATEMENT_ACC_HD_MAPPING "
					+ "where STATEMENT_NO=? )x left outer join ( select STATEMENT_GROUP_NO as stmt_grp_no,"
					+ "STATEMENT_GROUP_DESC from FAS_STATEMENT_GROUP_MASTER where STATEMENT_NAME=?  )y on "
					+ "x.STATEMENT_GROUP_NO =y.stmt_grp_no order by STATEMENT_GROUP_NO";*/
			String sql="SELECT STATEMENT_GROUP_NO, " +
			"  STATEMENT_GROUP_DESC, " +
			"  GROUP_TYPE, " +
			"  FROM_ACC_HD_CODE, " +
			"  TO_ACC_HD_CODE " +
			"FROM " +
			"  (SELECT STATEMENT_GROUP_NO, " +
			"    GROUP_TYPE, " +
			"    FROM_ACC_HD_CODE, " +
			"    TO_ACC_HD_CODE " +
			"  FROM FAS_STATEMENT_ACC_HD_MAPPING " +
			"  WHERE STATEMENT_NO=? " +
			"  )x " +
			"LEFT OUTER JOIN " +
			"  (SELECT STATEMENT_GROUP_NO AS stmt_grp_no, " +
			"    STATEMENT_GROUP_DESC " +
			"  FROM FAS_STATEMENT_GROUP_MASTER " +
			"  WHERE STATEMENT_NAME=? " +
			"  )y " +
			"ON x.STATEMENT_GROUP_NO =y.stmt_grp_no " +
			"ORDER BY STATEMENT_GROUP_NO";
			//System.out.println(sql);

			try {
				ps = connection.prepareStatement(sql);
				ps.setInt(1, cmbStatementName);
				ps.setInt(2, cmbStatementName);
				rs = ps.executeQuery();
				while (rs.next()) {
					xml = xml + "<STATEMENT_GROUP_NO>"
							+ rs.getInt("STATEMENT_GROUP_NO")
							+ "</STATEMENT_GROUP_NO>";

					xml = xml + "<STATEMENT_GROUP_DESC>"
							+ rs.getString("STATEMENT_GROUP_DESC")
							+ "</STATEMENT_GROUP_DESC>";
					if(rs.getString("GROUP_TYPE").equalsIgnoreCase("G")){
						xml = xml + "<ACC_HD_CODE>"
						+ rs.getString("FROM_ACC_HD_CODE")+" to "+rs.getString("TO_ACC_HD_CODE")
						+ "</ACC_HD_CODE>";
					}else{
						xml = xml + "<ACC_HD_CODE>"
						+ rs.getString("FROM_ACC_HD_CODE")
						+ "</ACC_HD_CODE>";
					}
					xml = xml + "<ACC_HD_CODE_VALUE>"
					+ rs.getString("FROM_ACC_HD_CODE")+"|"+rs.getString("TO_ACC_HD_CODE")
					+ "</ACC_HD_CODE_VALUE>";
				}
				xml = xml + "<flag>success</flag>";

			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}

		} else if (strCommand.equalsIgnoreCase("get")) {
			xml = "<response><command>get</command>";
			int year1 = Integer.parseInt(request.getParameter("y1"));
			int year2 = Integer.parseInt(request.getParameter("y2"));
			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			int cmbStatementName = Integer.parseInt(request
					.getParameter("cmbStatementName"));

			String sql = " select OFFICE_ID,OFFICE_NAME from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('RN','HO') order by OFFICE_ID ";
			String sql1 = " select OFFICE_ID,OFFICE_NAME from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('RN','CL','DN') and REGION_OFFICE_ID=? order by OFFICE_ID ";
			//System.out.println(sql);
			//System.out.println(sql1);

			try {
				ps1 = connection
						.prepareStatement("select UPDATED_DATE from FAS_BUDGET_CLOSURE_ALLOCATION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and STATEMENT_NAME=?");
				ps1.setInt(1, cmbAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setString(3, (year1) + "-" + (year2));
				ps1.setInt(4, cmbStatementName);
				results = ps1.executeQuery();
				if (results.next()) {
					xml = xml + "<flag>Exist</flag>";
				} else {
					if(cmbOffice_code==5000)
					{
					ps = connection.prepareStatement(sql);
					}else{
						ps = connection.prepareStatement(sql1);
						ps.setInt(1, cmbOffice_code);
					}
					

					rs = ps.executeQuery();
					while (rs.next()) {
						xml = xml + "<OFFICE_ID>"
								+ rs.getInt("OFFICE_ID")
								+ "</OFFICE_ID>";

						xml = xml + "<OFFICE_NAME>"
								+ rs.getString("OFFICE_NAME")
								+ "</OFFICE_NAME>";
					}
					xml = xml + "<flag>success</flag>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}

		}
		else if (strCommand.equalsIgnoreCase("hostatement")) {
			int count = 0;
			xml = "<response><command>HOState</command>";
			String financialYear = request.getParameter("cmbFinancialYear");			
			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			int cmbStatementName = Integer.parseInt(request
					.getParameter("cmbStatementName"));
			String sql = "";
			try {
				sql="SELECT ACCOUNTING_UNIT_ID, " +
				"  ACCOUNTING_FOR_OFFICE_ID, " +
				"  FINANCIAL_YEAR, " +
				"  STATEMENT_NAME, " +
				"  HEAD_OF_ACCOUNT, " +
				"  AMOUNT " +
				"FROM CIVIL_BUDGET_STATEMENT " +
				"WHERE ACCOUNTING_UNIT_ID    =? " +
				"AND ACCOUNTING_FOR_OFFICE_ID=? " +
				"AND FINANCIAL_YEAR          =? " +
				"AND STATEMENT_NAME          =?";
				ps1 = connection.prepareStatement(sql);
				ps1.setInt(1, cmbAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setString(3, financialYear);
				ps1.setInt(4, cmbStatementName);
				results = ps1.executeQuery();
				while(results.next()){
					xml += "<AMOUNT>"+results.getFloat("AMOUNT")+"</AMOUNT>";
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
				"FROM FAS_BUDGET_CLOSURE_ALLOCATION " +
				"WHERE ACCOUNTING_UNIT_ID    =? " +
				"AND ACCOUNTING_FOR_OFFICE_ID=? " +
				"AND FINANCIAL_YEAR          =? " +
				"AND STATEMENT_NAME          =?";
				ps1 = connection.prepareStatement(sql);
				ps1.setInt(1, cmbAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setString(3, financialYear);
				ps1.setInt(4, cmbStatementName);
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
		
		else if (strCommand.equalsIgnoreCase("load_grid")) {
			int count = 0,r_count=0;
			float total_amount=0.0f;
			xml = "<response><command>load_grid</command>";
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			String cmbFinancialYear = request.getParameter("cmbFinancialYear");
			String head_code = request.getParameter("head_code");
			int cmbStatementName = Integer.parseInt(request.getParameter("cmbStatementName"));
			int statementGp = Integer.parseInt(request.getParameter("statementGp"));
			
			
			try{
				String hq="Select r.ACCOUNTING_FOR_OFFICE_ID,(select o.Office_Name from COM_MST_ALL_OFFICES_VIEW o " +
				" where o.Office_Id=r.Accounting_For_Office_Id)as Office_Name," +
				" ACCOUNT_HEAD_CODE,AMOUNT,UNIT_ALLOCATION,RESERVED From Fas_Statement_By_Region r " +
				" WHERE Statement_No    ="+cmbStatementName+" AND Statement_Group_No="+statementGp+" AND Financial_Year    ='"+cmbFinancialYear+"' And  " +
				" Region_Office_Id  = "+cmbOffice_code+" AND ACCOUNT_HEAD_CODE like '"+head_code+"%' order by ACCOUNT_HEAD_CODE,r.ACCOUNTING_FOR_OFFICE_ID";
			System.out.println("hq:::"+hq);
				ps1 = connection.prepareStatement(hq);
			
			rss1=ps1.executeQuery();
			while(rss1.next())
			{
				xml += "<intial_load>no</intial_load>";
				r_count++;
				xml += "<off_id>"+rss1.getInt("ACCOUNTING_FOR_OFFICE_ID")+"</off_id>";
				xml += "<Office_Name>"+rss1.getString("Office_Name")+"</Office_Name>";
				xml += "<AMOUNT>"+rss1.getString("AMOUNT")+"</AMOUNT>";
				xml += "<h_code>"+rss1.getString("ACCOUNT_HEAD_CODE")+"</h_code>";
				xml += "<u_allocation>"+rss1.getString("UNIT_ALLOCATION")+"</u_allocation>";
				xml += "<reserved>"+rss1.getString("RESERVED")+"</reserved>";
				total_amount=total_amount+rss1.getFloat("AMOUNT");
			}
			if(r_count>0)
			{
				xml += "<total_amount>"+total_amount+"</total_amount>";
			}
			}
			catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			System.out.println(" r_count "+r_count);
			if(r_count==0){
					String sql = "";
					try {
						System.out.println("inside try ");
						xml += "<intial_load>yes</intial_load>";
						/*sql="Select Office_Name,Office_Id From COM_MST_ALL_OFFICES_VIEW  Where REGION_OFFICE_ID="+cmbOffice_code+" AND " +
								"OFFICE_LEVEL_ID   IN ('RN','CL','DN''AW') order by OFFICE_LEVEL_ID,Office_Id";
						*/
						
						sql="Select Office_Name,Office_Id From COM_MST_ALL_OFFICES_VIEW  Where (OFFICE_ID="+cmbOffice_code+" and OFFICE_LEVEL_ID='HO') or (REGION_OFFICE_ID="+cmbOffice_code+" AND " +
						"OFFICE_LEVEL_ID   IN ('RN','CL','DN','AW')) order by OFFICE_LEVEL_ID,Office_Id";
				
				
						
						
						
						
						ps1 = connection.prepareStatement(sql);
						
						results = ps1.executeQuery();
						while(results.next()){
							xml += "<off_id>"+results.getInt("Office_Id")+"</off_id>";
							xml += "<off_name>"+results.getString("Office_Name")+"</off_name>";
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
			System.out.println(" afetr qry ");
			
				
		}
		else if (strCommand.equalsIgnoreCase("head_test")) {
			int count = 0,from_code=0,to_code=0;
			xml = "<response><command>head_test</command>";
			String head_code = request.getParameter("head_code");
			String cmbFinancialYear = request.getParameter("cmbFinancialYear");
			
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			int cmbStatementName = Integer.parseInt(request.getParameter("cmbStatementName"));
			int statementGp = Integer.parseInt(request.getParameter("statementGp"));
			if(head_code.length()>6)
			{
				//System.out.println("if lenght greater");
				String[] from_to=head_code.split(" to ");
				from_code=Integer.parseInt(from_to[0]);
				to_code=Integer.parseInt(from_to[1]);
			}
			else
			{
				//System.out.println("else lenght single");
				from_code=Integer.parseInt(head_code);
				to_code=Integer.parseInt(head_code);
			}
		
			String sql = "";
			try {
				/*sql="Select Group_Type From Fas_Statement_Acc_Hd_Mapping Where " +
						" From_Acc_Hd_Code="+from_code+" And To_Acc_Hd_Code="+to_code+" And Statement_No= " +cmbStatementName+
						" and STATEMENT_GROUP_NO="+statementGp;*/
				sql="select a.groupty as grouptyp,b.grpDesc as grpDescc from "+
							" (SELECT Group_Type as GroupTy,STATEMENT_SUB_GROUP_NO "+
							" from fas_statement_acc_hd_mapping "+
							" where from_acc_hd_code=  "+from_code+" "+
							" and to_acc_hd_code               = "+to_code+""+
							" and statement_no                 = "+cmbStatementName+
							" and statement_group_no           = "+statementGp+" )a"+
							" inner join"+
							" (select statement_sub_group_dec as grpDesc,statement_sub_group_no " +
							" from fas_statement_sub_group_master "+ 
							" where statement_no= "+cmbStatementName+
							" and statement_group_no= "+statementGp+" )b"+
							" on a.statement_sub_group_no=b.statement_sub_group_no";
				ps1 = connection.prepareStatement(sql);
				//System.out.println("sql  head display "+sql);
				results = ps1.executeQuery();
				while(results.next()){
					xml += "<grouptype>"+results.getString("grouptyp")+"</grouptype>";
					xml += "<groupdesc>"+results.getString("grpDescc")+"</groupdesc>";
					count++;
				}
				if(count>0){
					ps4=connection.prepareStatement("SELECT sum(Amount)as amt From Fas_Statement_By_Region  " +
							" Where Statement_No="+cmbStatementName+" And Statement_Group_No="+statementGp+" And " +
							" Financial_Year='"+cmbFinancialYear+"' And Region_Office_Id=" +cmbOffice_code+
							" And ACCOUNT_HEAD_CODE='"+head_code+"'");
					results_one = ps4.executeQuery();
					if(results_one.next())
					{
						xml += "<amt_ttl>"+results_one.getString("amt")+"</amt_ttl>";
					}
					else
					{
						xml += "<amt_ttl>"+"0"+"</amt_ttl>";
					}
					xml += "<flag>success</flag>";
				}else{
					xml += "<flag>failure</flag>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}

		}
		 else if (strCommand.equalsIgnoreCase("callstatement")) {
			xml = "<response><command>callstatement</command>";
			int count = 0;
		
			//int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));			
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			
			String fyear =request.getParameter("cmbFinancialYear");
			System.out.println("fyear"+fyear);
			int cmbStatementName = Integer.parseInt(request.getParameter("cmbStatementName"));
			System.out.println("cmbStatementName"+cmbStatementName);
			int statementGp = Integer.parseInt(request.getParameter("statementGp"));
			System.out.println("statementGp"+statementGp);
			String sql = "";
			
			try {
				sql="Select Allocation_Type,Account_Head_Code,sum(Amount)as regAmt,UNIT_ALLOCATION From Fas_Statement_By_Region Where Statement_No    = " +cmbStatementName+
						"And Statement_Group_No="+statementGp+" And Financial_Year    ='"+fyear+"' AND Region_Office_Id  = "+cmbOffice_code+" group by Allocation_Type,Account_Head_Code,UNIT_ALLOCATION ";
				System.out.println(sql);
				ps1 = connection.prepareStatement(sql);
				
				results = ps1.executeQuery();
				if(results.next()){
					xml = xml + "<regAmt>"+ results.getInt("regAmt")+ "</regAmt>";
					xml = xml + "<Allocation_Type>"+ results.getString("Allocation_Type")+ "</Allocation_Type>";
					xml = xml + "<Account_Head_Code>"+ results.getInt("Account_Head_Code")+ "</Account_Head_Code>";
					xml = xml + "<unit_allocation>"+ results.getString("UNIT_ALLOCATION")+ "</unit_allocation>";
					count++;
				}
				if(count>0){
					xml += "<flag>already</flag>";
					
					
				}
				else{
					xml += "<flag>nodata</flag>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}

		}  
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Connection connection = null;
		LoadDriver load = new LoadDriver();
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		connection = load.getConnection();
		PreparedStatement preparedStatement = null,psss=null;
		String strCommand = "";
		String xml = "";
		int RecordCount=0;
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
		if(strCommand.equalsIgnoreCase("add")){
			String head_code=null;
			int count = 0,from_code=0,to_code=0,grid_off_name=0;
			int stategroupNo = Integer.parseInt(request.getParameter("statementGp"));
			int statementNo = Integer.parseInt(request.getParameter("cmbStatementName").trim());
			String financialYear = request.getParameter("cmbFinancialYear");
			String  unitallocation= request.getParameter("unitallocation");
			String  groupId= request.getParameter("groupId");
			int regionId = Integer.parseInt(request.getParameter("cmbOffice_code"));
			String reserveid = request.getParameter("reserveid");
			String  groupId_one= request.getParameter("groupId");
			System.out.println("groupId_one::::"+groupId_one);
			if(groupId_one.equalsIgnoreCase("G")){
				head_code="0";
				from_code=0;
				to_code=0;
			}
			else{
					head_code = request.getParameter("head_code");
					if(head_code.length()>6)
					{
						//System.out.println("if lenght greater");
						String[] from_to=head_code.split(" to ");
						from_code=Integer.parseInt(from_to[0]);
						to_code=Integer.parseInt(from_to[1]);
					}
					else
					{
						//System.out.println("else lenght single");
						from_code=Integer.parseInt(head_code);
						to_code=Integer.parseInt(head_code);
					}
			}
			RecordCount = Integer.parseInt(request.getParameter("RecordCount"));
			String off_name[] = new String[RecordCount];
			String Amount_grid[] = new String[RecordCount];
			
			
			try{
			
           for(int i=0; i<RecordCount; i++){
        	   off_name[i] = request.getParameter("office_name" + i);
        	   Amount_grid[i] = request.getParameter("Amount_grid" + i);
             
					try {						
						
						preparedStatement = connection.prepareStatement("insert into FAS_STATEMENT_BY_REGION(STATEMENT_NO,STATEMENT_GROUP_NO,FINANCIAL_YEAR,ACCOUNTING_FOR_OFFICE_ID,ACCOUNT_HEAD_CODE,AMOUNT,UPDATED_BY_USERID,UPDATED_DATE,FROM_ACC_HD_CODE,TO_ACC_HD_CODE,REGION_OFFICE_ID,ALLOCATION_TYPE,UNIT_ALLOCATION,RESERVED)values(?,?,?,?,?,?,?,SYSTIMESTAMP,?,?,?,?,?,?)");
						preparedStatement.setInt(1, statementNo);
						preparedStatement.setInt(2, stategroupNo);
						preparedStatement.setString(3, financialYear);
						if(off_name[i].equals("R"))
						{
							preparedStatement.setInt(4, regionId);
							String reserved=head_code+"-R";
							preparedStatement.setString(5, reserved);
						}
						else
						{
							grid_off_name=Integer.parseInt(off_name[i]);
							preparedStatement.setInt(4, grid_off_name);
						preparedStatement.setString(5, head_code);
						}
						
						
						preparedStatement.setString(6,Amount_grid[i]);
						System.out.println("grid_off_name:::"+Amount_grid[i]);
						preparedStatement.setString(7, userid);
						preparedStatement.setInt(8, from_code);
						preparedStatement.setInt(9, to_code);
						preparedStatement.setInt(10, regionId);
						preparedStatement.setString(11,groupId);
						preparedStatement.setString(12,unitallocation);
						preparedStatement.setString(13,reserveid);
						
						
						count = preparedStatement.executeUpdate();
						System.out.println("count:-" + count);
						
						
					} catch (Exception e) {
						System.out.println("Error "+e);
						connection.rollback();
						sendMessage(response, "Error "+e,
								"ok", "Civil_Budget_Statement_1_Reg_to_Cir_and_Div.jsp");
					}
						
			}
           
           if(count>0)
			{
				connection.commit();
				
				sendMessage(response, "Records Inserted Successfully ",
						"ok", "Civil_Budget_Statement_1_Reg_to_Cir_and_Div.jsp");
			}
			else
			{
				connection.rollback();	
				sendMessage(response, "Records Not Inserted ............ ",
						"ok", "Civil_Budget_Statement_1_Reg_to_Cir_and_Div.jsp");
			}	
			}
			catch(Exception ee)
			{
				System.out.println("ee in grid:::"+ee);
			}
		}
		else if(strCommand.equalsIgnoreCase("update_fn"))
		{
			xml = xml + "<response><command>update</command>";
			String head_code=null;
			int count = 0,from_code=0,to_code=0,grid_off_name=0;
			int stategroupNo = Integer.parseInt(request.getParameter("statementGp"));
			int statementNo = Integer.parseInt(request.getParameter("cmbStatementName").trim());
			String financialYear = request.getParameter("cmbFinancialYear");
			String  unitallocation= request.getParameter("unitallocation");
			String  groupId= request.getParameter("groupId");
			int regionId = Integer.parseInt(request.getParameter("cmbOffice_code"));
			String reserveid = request.getParameter("reserveid");
			
			if(groupId.equalsIgnoreCase("G")){
				head_code="0";
				from_code=0;
				to_code=0;
			}
			else{
			head_code = request.getParameter("head_code");
			if(head_code.length()>6)
			{
				//System.out.println("if lenght greater");
				String[] from_to=head_code.split(" to ");
				from_code=Integer.parseInt(from_to[0]);
				to_code=Integer.parseInt(from_to[1]);
			}
			else
			{
				//System.out.println("else lenght single");
				from_code=Integer.parseInt(head_code);
				to_code=Integer.parseInt(head_code);
			}
		}
			try{
			
				RecordCount = Integer.parseInt(request.getParameter("RecordCount"));
			 String office_name=request.getParameter("office_name_arr");
			// System.out.println("office_name::::"+office_name);
			 String[] office=office_name.split(",");
             String Amount_grid=request.getParameter("amtarr");
             String[] Amount_split=Amount_grid.split(",");
             
             
             try{
            	 psss = connection.prepareStatement("delete from FAS_STATEMENT_BY_REGION where STATEMENT_NO=? and STATEMENT_GROUP_NO=? and FINANCIAL_YEAR=? and REGION_OFFICE_ID=? and ACCOUNT_HEAD_CODE like '"+head_code+"%'");
           
            	 psss.setInt(1, statementNo);
            	 psss.setInt(2, stategroupNo);
            	 psss.setString(3, financialYear);
            	 psss.setInt(4, regionId);
            	// psss.setString(5, head_code);
            	 int yy=psss.executeUpdate();
            	 if(yy>0)
            	 {
            		 System.out.println("deleted:::::");
            		 connection.commit();
            	 }
             }
             catch(Exception ee)
             {
            	 System.out.println("ee:::"+ee);
             }
             
           for(int i=0; i<RecordCount; i++){
				
						//System.out.println("fff");
					try {						
						
						preparedStatement = connection.prepareStatement("insert into FAS_STATEMENT_BY_REGION(STATEMENT_NO,STATEMENT_GROUP_NO,FINANCIAL_YEAR,ACCOUNTING_FOR_OFFICE_ID,ACCOUNT_HEAD_CODE,AMOUNT,UPDATED_BY_USERID,UPDATED_DATE,FROM_ACC_HD_CODE,TO_ACC_HD_CODE,REGION_OFFICE_ID,ALLOCATION_TYPE,UNIT_ALLOCATION,RESERVED)values(?,?,?,?,?,?,?,SYSTIMESTAMP,?,?,?,?,?,?)");
						preparedStatement.setInt(1, statementNo);
						preparedStatement.setInt(2, stategroupNo);
						preparedStatement.setString(3, financialYear);
						//System.out.println("office[i]:::"+office[i]);
						if(office[i].equals("R"))
						{
							preparedStatement.setInt(4, regionId);
							//System.out.println("regionId:::"+regionId);
							String reserved=head_code+"-R";
							preparedStatement.setString(5, reserved);
							//System.out.println("reserved:::"+reserved);
						}
						else
						{
							grid_off_name=Integer.parseInt(office[i]);
							preparedStatement.setInt(4, grid_off_name);
						preparedStatement.setString(5, head_code);
						}
						
						
						preparedStatement.setString(6,Amount_split[i]);
						System.out.println("amt:::"+Amount_split[i]);
						preparedStatement.setString(7, userid);
						preparedStatement.setInt(8, from_code);
						preparedStatement.setInt(9, to_code);
						preparedStatement.setInt(10, regionId);
						preparedStatement.setString(11,groupId);
						preparedStatement.setString(12,unitallocation);
						preparedStatement.setString(13,reserveid);
						count = preparedStatement.executeUpdate();
						//System.out.println("count:" + count);
						
						
					} catch (Exception e) {
						
						connection.rollback();
						xml += "<flag>failure</flag>";
					}
						
			}
           
           if(count>0)
			{
				connection.commit();
				xml += "<flag>success</flag>";
				xml = xml + "</response>";
				out.write(xml);
				System.out.println(xml);
			}
			else
			{
				connection.rollback();	
				xml += "<flag>failure</flag>";
				xml = xml + "</response>";
				out.write(xml);
				System.out.println(xml);
			}	
			}
			catch(Exception ee)
			{
				System.out.println(ee);
				xml += "<flag>failure</flag>";
				xml = xml + "</response>";
				out.write(xml);
				System.out.println(xml);
			}
			
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
