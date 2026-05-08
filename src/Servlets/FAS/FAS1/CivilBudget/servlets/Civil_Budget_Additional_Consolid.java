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
 * Servlet implementation class Civil_Budget_Additional_Consolid
 */
public class Civil_Budget_Additional_Consolid extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Civil_Budget_Additional_Consolid() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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
		else if (strCommand.equalsIgnoreCase("groupch")) 
		{

			int count = 0;
			xml = "<response><command>groupch</command>";
			int statementno = Integer.parseInt(request.getParameter("statement"));			
			
			String sql = "";
			try {
				sql="Select Statement_Group_No,STATEMENT_GROUP_DESC from FAS_STATEMENT_GROUP_MASTER where STATEMENT_NAME="+statementno+" order by Statement_Group_No";
				//System.out.println(sql);
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
		
		else if (strCommand.equalsIgnoreCase("checkFreeze")) 
		{
			
			//int count = 0;
			xml = "<response><command>checkFreeze</command>";
			//int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));			
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			
			String fyear =request.getParameter("cmbFinancialYear");
		//	System.out.println("fyear"+fyear);
			int cmbStatementName = Integer.parseInt(request.getParameter("cmbStatementName"));
			//System.out.println("cmbStatementName"+cmbStatementName);

			String sql = "";
			//int cou=0;
			try {
				sql="Select 'X' From FAS_ADDITIONAL_CONSOLID_FREEZE Where "+ 
			" ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" And " +
			" FINANCIAL_YEAR= '"+fyear+"' And Statement_No='"+cmbStatementName+"'";
				System.out.println("check Freezed "+sql);
				ps1 = connection.prepareStatement(sql);
				
				int cc = ps1.executeUpdate();
				if(cc>0){
					xml += "<flag>Freezed</flag>";
				
				}
				else{
					xml += "<flag>NotFreezed</flag>";
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
			//System.out.println("fyear"+fyear);
			int cmbStatementName = Integer.parseInt(request.getParameter("cmbStatementName"));
			//System.out.println("cmbStatementName"+cmbStatementName);
			int statementGp = Integer.parseInt(request.getParameter("statementGp"));
			//System.out.println("statementGp"+statementGp);
			String sql = "";
			
			try {
				sql="Select Case When Group_Type='H' Then From_Acc_Hd_Code||'' "+ 
			" When Group_Type='G' Then From_Acc_Hd_Code||' to '||To_Acc_Hd_Code  "+
			" End As Range_Of_Heads "+
			" From Fas_Statement_Acc_Hd_Mapping Where Statement_No="+cmbStatementName+" And " +
			" Statement_Group_No="+statementGp+" order by From_Acc_Hd_Code";
				System.out.println("call head query "+sql);
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
			//System.out.println("fyear"+fyear);
			int cmbStatementName = Integer.parseInt(request.getParameter("cmbStatementName"));
			//System.out.println("cmbStatementName"+cmbStatementName);
			int statementGp = Integer.parseInt(request.getParameter("statementGp"));
			//System.out.println("statementGp"+statementGp);
			String sql = "";
			int cou=0;
			try {
				sql="Select AMOUNT_ALLOTTED From FAS_ADDTIONAL_BUDGET_REQ Where "+ 
			" ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" And " +
			" Financial_Year= '"+fyear+"' And Statement_No="+cmbStatementName+" and STATEMENT_GROUP_NO="+statementGp;
				System.out.println("load amt query  "+sql);
				ps1 = connection.prepareStatement(sql);
				
				results = ps1.executeQuery();
				while(results.next()){
					xml = xml + "<amt>"+ results.getString("AMOUNT_ALLOTTED")+ "</amt>";
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
		else if (strCommand.equalsIgnoreCase("LoadGrid_Head")) {
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
				" ACCOUNT_HEAD_CODE,AMOUNT_ALLOTTED,ADDL_BUDGET_REQ From FAS_ADDTIONAL_BUDGET_REQ r " +
				" WHERE Statement_No    ="+cmbStatementName+" AND Statement_Group_No="+statementGp+" AND Financial_Year    ='"+cmbFinancialYear+"' And  " +
				" Region_Office_Id  = "+cmbOffice_code;
				//+" AND ACCOUNT_HEAD_CODE like '"+head_code+"%' order by ACCOUNT_HEAD_CODE"
			System.out.println("load_grid:::"+hq);
				ps1 = connection.prepareStatement(hq);
			
			rss1=ps1.executeQuery();
			while(rss1.next())
			{
				xml += "<intial_load>no</intial_load>";
				r_count++;
				xml += "<off_id>"+rss1.getInt("ACCOUNTING_FOR_OFFICE_ID")+"</off_id>";
				xml += "<Office_Name>"+rss1.getString("Office_Name")+"</Office_Name>";
				xml += "<ADDL_BUDGET_REQ>"+rss1.getString("ADDL_BUDGET_REQ")+"</ADDL_BUDGET_REQ>";
				xml += "<h_code>"+rss1.getString("ACCOUNT_HEAD_CODE")+"</h_code>";
				xml += "<AMOUNT_ALLOTTED>"+rss1.getString("AMOUNT_ALLOTTED")+"</AMOUNT_ALLOTTED>";
				//xml += "<reserved>"+rss1.getString("RESERVED")+"</reserved>";
				total_amount=total_amount+rss1.getFloat("ADDL_BUDGET_REQ");
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
			if(r_count==0){
				xml += "<intial_load>yes</intial_load>";
			}
			/*if(r_count==0){
					String sql = "";
					try {
						xml += "<intial_load>yes</intial_load>";
						sql="Select Office_Name,Office_Id From COM_MST_ALL_OFFICES_VIEW  Where REGION_OFFICE_ID="+cmbOffice_code+" AND " +
								"OFFICE_LEVEL_ID   IN ('RN','CL','DN','AW') order by OFFICE_LEVEL_ID,Office_Id";
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
			}*/
			
				
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
					ps4=connection.prepareStatement("SELECT sum(ADDL_BUDGET_REQ)as amt From FAS_ADDTIONAL_BUDGET_REQ  " +
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

		}else if(strCommand.equalsIgnoreCase("deleteFn")) {
			xml = "<response><command>deleteFn</command>";
			//int count = 0;
		
			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));			
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			String fyear =request.getParameter("cmbFinancialYear");
			int cmbStatementName = Integer.parseInt(request.getParameter("cmbStatementName"));
			int statementGp = Integer.parseInt(request.getParameter("statementGp"));
			
			String sql = "";
			
			try {
				sql="delete From FAS_ADDTIONAL_BUDGET_REQ Where Statement_No  = " +cmbStatementName+
						"And Statement_Group_No="+statementGp+" And Financial_Year    ='"+fyear+"' AND ACCOUNTING_FOR_OFFICE_ID  = "+cmbOffice_code+" and ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode;
				System.out.println("delete  query"+sql);
				ps1 = connection.prepareStatement(sql);
				int cc = ps1.executeUpdate();
				if(cc>0){
					xml += "<flag>deletesuccess</flag>";
				}
				else{
					xml += "<flag>nodelete</flag>";
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
				sql="Select account_head_code,SUM(ADDL_BUDGET_REQ)AS reqAmt From FAS_ADDTIONAL_BUDGET_REQ Where Statement_No    = " +cmbStatementName+
						"And Statement_Group_No="+statementGp+" And Financial_Year    ='"+fyear+"' AND Region_Office_Id  = "+cmbOffice_code+" group by Account_Head_Code";
				System.out.println("callstatement query"+sql);
				ps1 = connection.prepareStatement(sql);
				
				results = ps1.executeQuery();
				if(results.next()){
					xml = xml + "<reqAmt>"+ results.getInt("reqAmt")+ "</reqAmt>";
					xml = xml + "<Account_Head_Code>"+ results.getInt("Account_Head_Code")+ "</Account_Head_Code>";
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
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection connection = null;
		LoadDriver load = new LoadDriver();
		response.setContentType(CONTENT_TYPE);
		//PrintWriter out = response.getWriter();
		connection = load.getConnection();
		PreparedStatement preparedStatement = null,ps=null,ps1=null;
		ResultSet rss=null;
		String strCommand = "";
		//String xml = "";
		//int RecordCount=0;
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
			System.out.println("strCommand:-post " + strCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
				.getAttribute("UserProfile");
		int empid = empProfile.getEmployeeId();
		if(strCommand.equalsIgnoreCase("add")){
			String head_code=null;
			int count = 0,from_code=0,to_code=0;
			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));			
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			int stategroupNo = Integer.parseInt(request.getParameter("statementGp"));
			int statementNo = Integer.parseInt(request.getParameter("cmbStatementName").trim());
			String financialYear = request.getParameter("cmbFinancialYear");
			//String  unitallocation= request.getParameter("unitallocation");
		//	String  groupId= request.getParameter("groupId");
			//int regionId = Integer.parseInt(request.getParameter("cmbOffice_code"));
			int reqallocation = Integer.parseInt(request.getParameter("reallocation"));
			String  groupId_one= request.getParameter("groupId");
			System.out.println("financialYear::::"+financialYear);
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
			//RecordCount = Integer.parseInt(request.getParameter("RecordCount"));
			//String off_name[] = new String[RecordCount];
			//String Amount_grid[] = new String[RecordCount];
			
			
			try{
			
         //  for(int i=0; i<RecordCount; i++){
        	  // off_name[i] = request.getParameter("office_name" + i);
        	 //  Amount_grid[i] = request.getParameter("Amount_grid" + i);
				int regionId=0;
				String getRegionid="select REGION_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID="+cmbOffice_code;
				ps=connection.prepareStatement(getRegionid);
				rss=ps.executeQuery();
				while(rss.next()){
					regionId=rss.getInt("REGION_OFFICE_ID");
				}
				String check="select 'x' from FAS_ADDTIONAL_BUDGET_CONSOLID where STATEMENT_NO=? AND STATEMENT_GROUP_NO=? AND FINANICAL_YEAR=? AND ACCOUNTING_FOR_OFFICE_ID=? AND  ACCOUNTING_UNIT_ID=?";
				ps1=connection.prepareStatement(check);
				ps1.setInt(1, statementNo);
				ps1.setInt(2, stategroupNo);
				ps1.setString(3, financialYear);
				ps1.setInt(4, cmbOffice_code);
				ps1.setInt(5,cmbAcc_UnitCode);	
				int cc=ps1.executeUpdate();
				if(cc>0){
					connection.commit();
					
					sendMessage(response, "Records Already Inserted ",
							"ok", "Civil_Budget_Additional_Consolid.jsp");
				}else{
					
				System.out.println("else part ");
					try {						
						
						preparedStatement = connection.prepareStatement("insert into FAS_ADDTIONAL_BUDGET_CONSOLID(STATEMENT_NO,STATEMENT_GROUP_NO,FINANICAL_YEAR,ACCOUNTING_FOR_OFFICE_ID,ACCOUNT_HEAD_CODE,ADDITIONAL_AMOUNT_REQ,UPDATED_BY_USERID,UPDATED_DATE,FROM_ACC_HD_CODE,TO_ACC_HD_CODE,REGION_OFFICE_ID,ACCOUNTING_UNIT_ID)values(?,?,?,?,?,?,?,SYSTIMESTAMP,?,?,?,?)");
						preparedStatement.setInt(1, statementNo);
						preparedStatement.setInt(2, stategroupNo);
						preparedStatement.setString(3, financialYear);
						preparedStatement.setInt(4, cmbOffice_code);
						preparedStatement.setString(5, head_code);
						preparedStatement.setInt(6,reqallocation);
						preparedStatement.setString(7, userid);
						preparedStatement.setInt(8, from_code);
						preparedStatement.setInt(9, to_code);
						preparedStatement.setInt(10, regionId);
						preparedStatement.setInt(11,cmbAcc_UnitCode);	
						count = preparedStatement.executeUpdate();
						System.out.println("count:-" + count);
						
						
					} catch (Exception e) {
						
						connection.rollback();
						sendMessage(response, "Error "+e,
								"ok", "Civil_Budget_Additional_Consolid.jsp");
					}
				}	
		//	}
           
           if(count>0)
			{
				connection.commit();
				
				sendMessage(response, "Records Inserted Successfully ",
						"ok", "Civil_Budget_Additional_Consolid.jsp");
			}
			else
			{
				connection.rollback();	
				sendMessage(response, "Records Not Inserted ............ ",
						"ok", "Civil_Budget_Additional_Consolid.jsp");
			}	
			}
			catch(Exception ee)
			{
				System.out.println("ee in grid:::"+ee);
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
