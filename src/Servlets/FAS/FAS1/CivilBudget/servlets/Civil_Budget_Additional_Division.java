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

import Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver;

/**
 * Servlet implementation class Civil_Budget_Additional_Division
 */
public class Civil_Budget_Additional_Division extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Civil_Budget_Additional_Division() {
        super();
        // TODO Auto-generated constructor stub
    }
    protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
	
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();

		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		//ResultSet results2;
		ResultSet rs = null,rss1=null,results_one=null;
		ResultSet rs2 = null;
		PreparedStatement ps = null;
		PreparedStatement preparedStatement1 = null;
		PreparedStatement ps1 = null,ps4=null;
		//PreparedStatement ps2 = null;
		//int cashbookYear = 0;
		//String cashbookMonth = null;
		//int unitid = 0;
		//String unitname = "";
		//int accid = 0;

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
			System.out.println("inside getStatementName   menthod...");
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
			String statementno = request.getParameter("statement");			
			
			String sql = "";
			try {
				sql="Select Statement_Group_No,STATEMENT_GROUP_DESC from FAS_STATEMENT_GROUP_MASTER where STATEMENT_NAME='"+statementno+"' order by Statement_Group_No";
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
		else if (strCommand.equalsIgnoreCase("load_grid")) {
			int count = 0,r_count=0;
			xml = "<response><command>load_grid</command>";
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			String cmbFinancialYear = request.getParameter("cmbFinancialYear");
			String head_code = request.getParameter("head_code");
			int cmbStatementName = Integer.parseInt(request.getParameter("cmbStatementName"));
			int statementGp = Integer.parseInt(request.getParameter("statementGp"));
			
			//System.out.println("in civil budget additional---"+"cmbOffice_code"+cmbOffice_code+"cmbFinancialYear"+cmbFinancialYear+"head_code"+head_code+"cmbStatementName"+cmbStatementName+"statementGp"+statementGp);
			try{
				String hq="Select r.ACCOUNTING_FOR_OFFICE_ID,(select o.Office_Name from COM_MST_ALL_OFFICES_VIEW o " +
				" where o.Office_Id=r.Accounting_For_Office_Id)as Office_Name," +
				" ACCOUNT_HEAD_CODE,AMOUNT_ALLOTTED From FAS_ADDTIONAL_BUDGET_REQ r " +
				" WHERE Statement_No    ="+cmbStatementName+" AND Statement_Group_No="+statementGp+" AND Financial_Year    ='"+cmbFinancialYear+"' And  " +
				" Region_Office_Id  = "+cmbOffice_code+" AND ACCOUNT_HEAD_CODE like '"+head_code+"%' order by ACCOUNT_HEAD_CODE";
			
				ps1 = connection.prepareStatement(hq);	
				
			rss1=ps1.executeQuery();
			//System.out.println("hq:::load gird--in civil budget additional---"+hq);
			//System.out.println("REsultset value-->"+rss1);
			while(rss1.next())
			{
				xml += "<intial_load>no</intial_load>";
				r_count++;
				xml += "<off_id>"+rss1.getInt("ACCOUNTING_FOR_OFFICE_ID")+"</off_id>";
				xml += "<Office_Name>"+rss1.getString("Office_Name")+"</Office_Name>";
				xml += "<AMOUNT>"+rss1.getString("AMOUNT")+"</AMOUNT>";
				xml += "<h_code>"+rss1.getString("ACCOUNT_HEAD_CODE")+"</h_code>";
			}
			
			
			}
			catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			if(r_count==0){
			String sql = "";
			try {
				xml += "<intial_load>yes</intial_load>";
				sql="Select Office_Name,Office_Id From COM_MST_ALL_OFFICES_VIEW  Where REGION_OFFICE_ID="+cmbOffice_code+" AND OFFICE_LEVEL_ID   IN ('RN','CL','DN')";
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
				sql="Select Group_Type From Fas_Statement_Acc_Hd_Mapping Where " +
						" From_Acc_Hd_Code="+from_code+" And To_Acc_Hd_Code="+to_code+" And Statement_No= " +cmbStatementName+
						" and STATEMENT_GROUP_NO="+statementGp;
				ps1 = connection.prepareStatement(sql);
				//System.out.println("query in civil budget additional-----"+sql);
				
				results = ps1.executeQuery();
				while(results.next()){
					xml += "<grouptype>"+results.getString("Group_Type")+"</grouptype>";
					count++;
				}
				if(count>0){
					ps4=connection.prepareStatement("SELECT sum(AMOUNT_ALLOTTED)as amt From FAS_ADDTIONAL_BUDGET_REQ  " +
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
		else if (strCommand.equalsIgnoreCase("load_table")) {
			int count = 0,r_count=0;
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
            String cmbFinancialYear = request.getParameter("cmbFinancialYear");
		//	int cmbStatementName = Integer.parseInt(request.getParameter("cmbStatementName"));
			//int statementGp = Integer.parseInt(request.getParameter("statementGp"));
			
			String condn="";
			if(cmbFinancialYear!=null)
			{
				condn+=" r.FINANCIAL_YEAR ='"+cmbFinancialYear+"'";
			}
			int cmbStatementName=0;
			try 
			{
				cmbStatementName=Integer.parseInt(request.getParameter("cmbStatementName"));
				
			}catch(Exception e){}
			
			if(cmbStatementName>0)
			{
				condn+="AND r.STATEMENT_NO ="+cmbStatementName;
			}
			int statementGp=0;
			try 
			{
				statementGp=Integer.parseInt(request.getParameter("statementGp"));
				
			}catch(Exception e){}
			
			if((cmbStatementName>0) && (statementGp>0))
			{
				condn+="AND r.STATEMENT_NO="+cmbStatementName+" AND r.STATEMENT_GROUP_NO ="+statementGp;
			}
			
			//System.out.println("inside load table method...");
			xml = "<response><command>load_table</command>";
			//System.out.println("in civil budget additional---"+"cmbOffice_code"+cmbOffice_code+"cmbFinancialYear"+cmbFinancialYear+"head_code"+head_code+"cmbStatementName"+cmbStatementName+"statementGp"+statementGp);
			try{
				String hq1="";
				//if((cmbFinancialYear.equalsIgnoreCase(""))&&(cmbStatementName==0)&&(statementGp==0)) {
					hq1="SELECT (SELECT f1.STATEMENT_DESC FROM FAS_STATEMENT_MASTER f1 WHERE f1.Statement_No=r.STATEMENT_NO )AS statementName,(SELECT f2.STATEMENT_GROUP_DESC FROM FAS_STATEMENT_GROUP_MASTER f2 WHERE f2.STATEMENT_NAME=r.STATEMENT_NO AND f2.STATEMENT_GROUP_NO=r.STATEMENT_GROUP_NO) AS StatementGroup,r.STATEMENT_NO,r.STATEMENT_GROUP_NO,r.ACCOUNT_HEAD_CODE,r.AMOUNT_ALLOTTED,r.ADDL_BUDGET_REQ,r.REASON,r.ACCOUNTING_FOR_OFFICE_ID FROM FAS_ADDTIONAL_BUDGET_REQ r where ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+ " and "+condn;	
				
				//String hq1="Select (Select STATEMENT_GROUP_DESC from FAS_STATEMENT_GROUP_MASTER fgm where fgm.Statement_Group_No=r.STATEMENT_GROUP_NO)as statementGroup,(select STATEMENT_DESC from FAS_STATEMENT_MASTER f1 where f1.STATEMENT_NO=r.STATEMENT_NO) as StatementName,ACCOUNT_HEAD_CODE,AMOUNT_ALLOTTED,ADDL_BUDGET_REQ,REASON From FAS_ADDTIONAL_BUDGET_REQ r";
				//String hq1="Select STATEMENT_NO,STATEMENT_GROUP_NO,ACCOUNT_HEAD_CODE,AMOUNT_ALLOTTED,ADDL_BUDGET_REQ,REASON From FAS_ADDTIONAL_BUDGET_REQ r";
				
			System.out.println("load table  "+hq1);
				ps1 = connection.prepareStatement(hq1);				
			rs2=ps1.executeQuery();
			while(rs2.next())
			{
				
				
				xml += "<st_name>"+rs2.getString("statementName")+"</st_name>";
				xml += "<st_Group>"+rs2.getString("StatementGroup")+"</st_Group>";
				xml += "<st_name_no>"+rs2.getString("STATEMENT_NO")+"</st_name_no>";
				xml += "<st_Group_no>"+rs2.getString("STATEMENT_GROUP_NO")+"</st_Group_no>";
				xml += "<headofaccount>"+rs2.getString("ACCOUNT_HEAD_CODE")+"</headofaccount>";
				xml += "<amt_alloted>"+rs2.getBigDecimal("AMOUNT_ALLOTTED")+"</amt_alloted>";
				xml += "<budget_req>"+rs2.getInt("ADDL_BUDGET_REQ")+"</budget_req>";
				xml += "<reason>"+rs2.getString("REASON")+"</reason>";
				xml += "<officeid>"+rs2.getInt("ACCOUNTING_FOR_OFFICE_ID")+"</officeid>";
				count++;
				
			}
			xml += "<count>"+count+"</count>";
			System.out.println("Count inn java "+count);
			if(count>0){
				xml = xml + "<flag>success</flag>";
			}else if(count==0){
				xml = xml + "<flag>NoData</flag>";
			}
			}
			catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			
		}
		else if (strCommand.equalsIgnoreCase("groupchfind")) 
		{

			int count = 0;
			xml = "<response><command>groupchupdate</command>";
			String statementno = request.getParameter("statement");			
			String id=request.getParameter("id");
			String sql = "";
			try {
				sql="Select Statement_Group_No,STATEMENT_GROUP_DESC from FAS_STATEMENT_GROUP_MASTER where STATEMENT_NAME='"+statementno+"' and Statement_Group_No='"+id+"'";
				System.out.println("group find...."+sql);
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
		else if (strCommand.equalsIgnoreCase("callHeadupdate")) 
		{
			
			int count = 0;
			xml = "<response><command>callHeadUpdate</command>";
			
			String cmbStatementName = request.getParameter("cmbStatementName");
			//System.out.println("cmbStatementName"+cmbStatementName);
			int statementGp = Integer.parseInt(request.getParameter("statementGp"));
			//System.out.println("statementGp"+statementGp);
			String id = request.getParameter("id");
			System.out.println("callupdate in java  before..."+id);
			String sql = "";
			
			try {

					xml = xml + "<codeHeads>"+id+"</codeHeads>";
					System.out.println("callupdate in java ..."+id);
					count++;
				//}
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
		else if (strCommand.equalsIgnoreCase("checkFreeze")) 
		{
			
			//int count = 0;
			xml = "<response><command>checkFreeze</command>";
			//int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));			
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			
			String fyear =request.getParameter("cmbFinancialYear");
			//System.out.println("fyear"+fyear);
			int cmbStatementName = Integer.parseInt(request.getParameter("cmbStatementName"));
			//System.out.println("cmbStatementName"+cmbStatementName);

			String sql = "";
			//int cou=0;
			try {
				sql="Select 'X' From FAS_ADDITIONAL_FREEZE Where "+ 
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
				/*sql="Select Amount From Fas_Budget_Allocation Where "+ 
			" ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" And " +
			" Financial_Year= '"+fyear+"' And Statement_No="+cmbStatementName+" and STATMENT_GROUP_NO="+statementGp;*/
				
				sql="Select Amount From FAS_STATEMENT_BY_REGION Where "+ 
				" ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" And " +
				" Financial_Year= '"+fyear+"' And STATEMENT_NO="+cmbStatementName+" and STATEMENT_GROUP_NO="+statementGp;
				System.out.println("load amt"+sql);
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
		 else if(strCommand.equalsIgnoreCase("updated"))
         {
 			//System.out.println("updated ");
             xml="<response><command>updated</command>";
 			int count = 0;
 			int stategroupNo = Integer.parseInt(request.getParameter("statementGp"));
 			String  statementNo = request.getParameter("cmbStatementName").trim();
 			String financialYear = request.getParameter("cmbFinancialYear");	
 			int officeId = Integer.parseInt(request.getParameter("cmbOffice_code"));		
 			String head_code = request.getParameter("head_code");
 			String amt=request.getParameter("hoamountinrs");
 			int additionalBudget = Integer.parseInt(request.getParameter("budgetrequired"));
 			//String additionalBudget = request.getParameter("budgetrequired");
 			System.out.println("officeId "+officeId);
 			String reason = request.getParameter("txtReason");
 			System.out.println("update FAS_ADDTIONAL_BUDGET_REQ set UPDATED_BY_USERID='"+userid+"',UPDATED_DATE=SYSTIMESTAMP,ADDL_BUDGET_REQ='"+additionalBudget+"',REASON='"+reason+"' where STATEMENT_NO=? and STATEMENT_GROUP_NO=? and FINANCIAL_YEAR=? and ACCOUNT_HEAD_CODE=? and AMOUNT_ALLOTTED=? and ACCOUNTING_FOR_OFFICE_ID=?");
 			//System.out.println("stategroupNo"+stategroupNo+"statementNo"+statementNo+"head_code"+head_code+"amt"+amt+"additionalBudget"+additionalBudget+"reason"+reason);
 						try {						

 						preparedStatement1 = connection.prepareStatement("update FAS_ADDTIONAL_BUDGET_REQ set UPDATED_BY_USERID='"+userid+"',UPDATED_DATE=SYSTIMESTAMP,ADDL_BUDGET_REQ='"+additionalBudget+"',REASON='"+reason+"' where STATEMENT_NO=? and STATEMENT_GROUP_NO=? and FINANCIAL_YEAR=? and ACCOUNT_HEAD_CODE=? and AMOUNT_ALLOTTED=? and ACCOUNTING_FOR_OFFICE_ID=?");
 						preparedStatement1.setString(1, statementNo);
 						preparedStatement1.setInt(2, stategroupNo);
 						preparedStatement1.setString(3, financialYear);						
 						preparedStatement1.setString(4, head_code);	
 						preparedStatement1.setString(5,amt);
 						preparedStatement1.setInt(6, officeId);
     					count = preparedStatement1.executeUpdate();
 						System.out.println("count:" + count);

                     if(count>0) {
                         xml=xml+"<flag>success</flag>"; 
                     }else{
                    	 xml=xml+"<flag>failure</flag>"; 
                     }
                     
                 }
             catch(Exception e)
                 {
                      System.out.println("exception in update is"+e);
                      xml=xml+"<flag>failure</flag>";
                 }
            
         }
		 else if(strCommand.equalsIgnoreCase("deleteRecord"))
         {
 			System.out.println("deleteRecord ");
             xml="<response><command>deleteRecord</command>";
            

 			int count = 0;
 			int stategroupNo = Integer.parseInt(request.getParameter("statementGp"));
 			String  statementNo = request.getParameter("cmbStatementName").trim();
 			String financialYear = request.getParameter("cmbFinancialYear");	
 			int officeId = Integer.parseInt(request.getParameter("cmbOffice_code"));		
 			String head_code = request.getParameter("head_code");
 			int amt=Integer.parseInt(request.getParameter("hoamountinrs"));
 			int additionalBudget = Integer.parseInt(request.getParameter("budgetrequired"));
 			
 			String reason = request.getParameter("txtReason");

 						try {						

 							preparedStatement1 = connection.prepareStatement("delete from  FAS_ADDTIONAL_BUDGET_REQ where STATEMENT_NO=? and STATEMENT_GROUP_NO=? and ACCOUNT_HEAD_CODE=? and AMOUNT_ALLOTTED=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
 							//preparedStatement1 = connection.prepareStatement("delete STATEMENT_NO,STATEMENT_GROUP_NO,FINANCIAL_YEAR,ACCOUNTING_FOR_OFFICE_ID,ACCOUNT_HEAD_CODE,AMOUNT_ALLOTTED,UPDATED_BY_USERID,UPDATED_DATE,FROM_ACC_HD_CODE,TO_ACC_HD_CODE,REGION_OFFICE_ID,ADDL_BUDGET_REQ,REASON from  FAS_ADDTIONAL_BUDGET_REQ where STATEMENT_NO=? and STATEMENT_GROUP_NO=? and ACCOUNT_HEAD_CODE=? and AMOUNT_ALLOTTED=?");
 							preparedStatement1.setString(1, statementNo);
 							preparedStatement1.setInt(2, stategroupNo);
 							preparedStatement1.setString(3, head_code);
 							preparedStatement1.setInt(4,amt);
 							preparedStatement1.setInt(5, officeId);
 							preparedStatement1.setString(6, financialYear);			
 							count = preparedStatement1.executeUpdate();
 						System.out.println("count:" + count);

                     if(count>0) {
                         xml=xml+"<flag>success</flag>"; 
                     }
                     
                 }
             catch(Exception e)
                 {
                      System.out.println("exception in delete is"+e);
                      xml=xml+"<flag>failure</flag>";
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
		//PrintWriter out = response.getWriter();
		connection = load.getConnection();
		PreparedStatement preparedStatement = null,ps=null,ps1=null;
		ResultSet rss=null;
		String strCommand = "";
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
			int count = 0,from_code=0,to_code=0;
			int stategroupNo = Integer.parseInt(request.getParameter("statementGp"));
			String statementNo = request.getParameter("cmbStatementName").trim();
			String financialYear = request.getParameter("cmbFinancialYear");	
			int officeId = Integer.parseInt(request.getParameter("cmbOffice_code"));
			double amountallocate=Double.parseDouble(request.getParameter("hoamountinrs"));
			String groupId=request.getParameter("groupId");
			String head_code = request.getParameter("head_code");
			
			int additionalBudget = Integer.parseInt(request.getParameter("budgetrequired"));
			String reason = request.getParameter("txtReason");
			//System.out.println("Head code"+head_code);
			if(groupId.equalsIgnoreCase("H")){
				if(head_code.length()>6)
				{
					
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
			else
			{
				head_code="0";
				from_code=0;
				to_code=0;
				
			}
			
			
			try{
				int regionId=0;
				String getRegionid="select REGION_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID="+officeId;
				ps=connection.prepareStatement(getRegionid);
				System.out.println("get redio "+getRegionid);
				rss=ps.executeQuery();
				while(rss.next()){
					regionId=rss.getInt("REGION_OFFICE_ID");
				}
		     String check="select 'X' from FAS_ADDTIONAL_BUDGET_REQ where STATEMENT_NO=? and STATEMENT_GROUP_NO=? and FINANCIAL_YEAR=? and ACCOUNTING_FOR_OFFICE_ID=? and ACCOUNT_HEAD_CODE=?";
				ps1=connection.prepareStatement(check);
				ps1.setString(1, statementNo);
				ps1.setInt(2, stategroupNo);
				ps1.setString(3, financialYear);
				ps1.setInt(4, officeId);
				ps1.setString(5, head_code);
				int cc=ps1.executeUpdate();
				if(cc>0){
					connection.rollback();	
					sendMessage(response,"Record Already Inserted ","ok");
				}else{

					try {						
						
						preparedStatement = connection.prepareStatement("insert into FAS_ADDTIONAL_BUDGET_REQ(STATEMENT_NO,STATEMENT_GROUP_NO,FINANCIAL_YEAR,ACCOUNTING_FOR_OFFICE_ID,ACCOUNT_HEAD_CODE,AMOUNT_ALLOTTED,UPDATED_BY_USERID,UPDATED_DATE,FROM_ACC_HD_CODE,TO_ACC_HD_CODE,REGION_OFFICE_ID,ADDL_BUDGET_REQ,REASON)values(?,?,?,?,?,?,?,SYSTIMESTAMP,?,?,?,?,?)");
						preparedStatement.setString(1, statementNo);
						preparedStatement.setInt(2, stategroupNo);
						preparedStatement.setString(3, financialYear);
						preparedStatement.setInt(4, officeId);
						preparedStatement.setString(5, head_code);
						preparedStatement.setDouble(6,amountallocate);
						preparedStatement.setString(7, userid);
						preparedStatement.setInt(8, from_code);
						preparedStatement.setInt(9, to_code);
						preparedStatement.setInt(10, regionId);
						preparedStatement.setInt(11, additionalBudget);
						preparedStatement.setString(12, reason);						
						count = preparedStatement.executeUpdate();
						System.out.println("count:-addd method civil budget additional---" + count);
						
						
					} catch (Exception e) {
						
						connection.rollback();
                         sendMessage(response,"Failed to Add Data","ok");  
					}
				
						
          
          if(count>0)
			{
				connection.commit();
				sendMessage(response,"Record Inserted successfully ","ok");
			}
			else
			{
				connection.rollback();	
				sendMessage(response,"Record Not Inserted  ","ok");
			}
				}
			}
			catch(Exception ee)
			{
				System.out.println("ee in grid:::"+ee);
			}
		}
		
		
		
	}
	 private void sendMessage(HttpServletResponse response,String msg,String bType)
	    {
	        try
	        {
	        	System.out.println("message...."+msg+"button type "+bType);
	                  String url="org/Library/jsps/MessengerOkBack.jsp?message=" +msg+ "&button=" + bType;
	                  response.sendRedirect(url);
	        }
	        catch(IOException e)
	        {
	        }
	    }
}