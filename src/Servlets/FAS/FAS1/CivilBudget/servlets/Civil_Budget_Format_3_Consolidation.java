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
 * Servlet implementation class Civil_Budget_Format_3_Consolidation
 */
public class Civil_Budget_Format_3_Consolidation extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Civil_Budget_Format_3_Consolidation() {
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
		ResultSet rs = null;
		ResultSet rs2 = null;
		PreparedStatement ps = null;
		PreparedStatement pss = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
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
		String office_level_id = null;
		int office_id = 0;
		int flag = 0;
		int flag1 = 0;
		String ofiice_type = null;
		if (strCommand.equalsIgnoreCase("get")) {
			xml = "<response><command>get</command>";
			int year1 = Integer.parseInt(request.getParameter("y1"));
			int year2 = Integer.parseInt(request.getParameter("y2"));
			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));

			String sql = "";

			try {

				ps = connection
						.prepareStatement("select OFFICE_ID   from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
				ps.setInt(1, empid);
				results = ps.executeQuery();
				if (results.next()) {

					office_id = results.getInt("OFFICE_ID");
				}

				ps2 = connection
						.prepareStatement("select OFFICE_LEVEL_ID  from COM_MST_OFFICES  where OFFICE_ID=?");
				ps2.setInt(1, office_id);
				rs2 = ps2.executeQuery();
				if (rs2.next()) {
					office_level_id = rs2.getString("OFFICE_LEVEL_ID");
				}

				if (office_level_id.equals("CL")) {
					sql = " SELECT SL_NO,NAME_OF_CATEGORY,TIME_SCALE_OF_PAY,NO_OF_SANCTIONED_POSTS,NO_OF_INCUMBENTS_IN_ROLL,NO_OF_VACANT_POSTS,BEGINING_OF_THE_YEAR_BASIC_PAY,BEGINING_OF_THE_YEAR_50_BP,to_char(INCREMENT_DATE,'DD/MM/YYYY') as INCREMENT_DATE,INCREMENT_AMOUNT,AFTER_INCREMENT_BASIC_PAY,AFTER_INCREMENT_50_BP,BASIC_PAY_AFTER_INCREMENT,BASIC_PAY_50_DEARNESS_PAY,DA_32_ON_COL_NO_13,OTHER_ALLOWANCES,TOTAL,to_char(DATE_OF_RETIREMENT,'DD/MM/YYYY') as DATE_OF_RETIREMENT FROM FAS_BUDGET_FORMAT_3 WHERE FINANCIAL_YEAR=? and"
							+ " OFFICE_LEVEL_ID in('CL','DN') and CIRCLE=? ";
					xml = xml + "<ofiice_type>Divisions</ofiice_type>";
					ps1 = connection
							.prepareStatement(" select accounting_unit_id ,accounting_unit_name,"
									+ "accounting_unit_office_id,OFFICE_NAME from (select accounting_unit_id ,"
									+ "accounting_unit_name,accounting_unit_office_id from  "
									+ "fas_mst_acct_units where accounting_unit_office_id in "
									+ "(select OFFICE_ID  from COM_MST_ALL_OFFICES_VIEW  "
									+ "where CIRCLE_OFFICE_ID=? )  )x left outer join(select OFFICE_ID,"
									+ "OFFICE_NAME from COM_MST_OFFICES)y on "
									+ "x.accounting_unit_office_id =y.OFFICE_ID  ");
					ps1.setInt(1, office_id);
					rs = ps1.executeQuery();
					while (rs.next()) {
						pss = connection
								.prepareStatement(" select ACCOUNTING_UNIT_ID from FAS_BUDGET_CLOSURE where FINANCIAL_YEAR=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FORMAT_NAME=?");
						pss.setString(1, (year1 + "-" + year2));
						pss.setInt(2, rs.getInt("accounting_unit_id"));
						pss.setInt(3, rs.getInt("accounting_unit_office_id"));
						pss.setString(4, "3");
						results2 = pss.executeQuery();
						if (results2.next()) {
							flag = 1;
						} else {
							flag1 = 1;
						}
					}
				} else if (office_level_id.equals("RN")) {
					sql = " SELECT SL_NO,NAME_OF_CATEGORY,TIME_SCALE_OF_PAY,NO_OF_SANCTIONED_POSTS,NO_OF_INCUMBENTS_IN_ROLL,NO_OF_VACANT_POSTS,BEGINING_OF_THE_YEAR_BASIC_PAY,BEGINING_OF_THE_YEAR_50_BP,to_char(INCREMENT_DATE,'DD/MM/YYYY') as INCREMENT_DATE,INCREMENT_AMOUNT,AFTER_INCREMENT_BASIC_PAY,AFTER_INCREMENT_50_BP,BASIC_PAY_AFTER_INCREMENT,BASIC_PAY_50_DEARNESS_PAY,DA_32_ON_COL_NO_13,OTHER_ALLOWANCES,TOTAL,to_char(DATE_OF_RETIREMENT,'DD/MM/YYYY') as DATE_OF_RETIREMENT FROM FAS_BUDGET_FORMAT_3 WHERE FINANCIAL_YEAR=? and "
							+ "OFFICE_LEVEL_ID in('RN','CL','DN') and REGION=?";
					xml = xml + "<ofiice_type>Circles</ofiice_type>";
					ps1 = connection
							.prepareStatement(" select accounting_unit_id ,accounting_unit_name,"
									+ "accounting_unit_office_id,OFFICE_NAME from (select accounting_unit_id ,"
									+ "accounting_unit_name,accounting_unit_office_id from  "
									+ "fas_mst_acct_units where accounting_unit_office_id in "
									+ "(select CIRCLE_OFFICE_ID  from COM_MST_ALL_OFFICES_VIEW  where REGION_OFFICE_ID=?  "
									+ " )  )x left outer join(select OFFICE_ID,"
									+ "OFFICE_NAME from COM_MST_OFFICES)y on "
									+ "x.accounting_unit_office_id =y.OFFICE_ID  ");
					ps1.setInt(1, office_id);
					rs = ps1.executeQuery();
					while (rs.next()) {
						pss = connection
								.prepareStatement(" select ACCOUNTING_UNIT_ID from FAS_BUDGET_CLOSURE_CONSOLIDATE where FINANCIAL_YEAR=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?  and FORMAT_NAME=?");
						pss.setString(1, (year1 + "-" + year2));
						pss.setInt(2, rs.getInt("accounting_unit_id"));
						pss.setInt(3, rs.getInt("accounting_unit_office_id"));
						pss.setString(4, "3");
						results2 = pss.executeQuery();
						if (results2.next()) {
							flag = 1;
						} else {
							flag1 = 1;
						}
					}
				} else if (office_level_id.equals("HO")) {
					sql = " SELECT SL_NO,NAME_OF_CATEGORY,TIME_SCALE_OF_PAY,NO_OF_SANCTIONED_POSTS,"
							+ "NO_OF_INCUMBENTS_IN_ROLL,NO_OF_VACANT_POSTS,BEGINING_OF_THE_YEAR_BASIC_PAY,"
							+ "BEGINING_OF_THE_YEAR_50_BP,to_char(INCREMENT_DATE,'DD/MM/YYYY') as INCREMENT_DATE,"
							+ "INCREMENT_AMOUNT,AFTER_INCREMENT_BASIC_PAY,AFTER_INCREMENT_50_BP,"
							+ "BASIC_PAY_AFTER_INCREMENT,BASIC_PAY_50_DEARNESS_PAY,DA_32_ON_COL_NO_13,"
							+ "OTHER_ALLOWANCES,TOTAL,to_char(DATE_OF_RETIREMENT,'DD/MM/YYYY') as "
							+ "DATE_OF_RETIREMENT FROM FAS_BUDGET_FORMAT_3 WHERE FINANCIAL_YEAR=? and "
							+ "OFFICE_LEVEL_ID in('HO','RN','CL','DN') ";
					xml = xml + "<ofiice_type>Regions</ofiice_type>";
					ps1 = connection
							.prepareStatement(" select accounting_unit_id ,accounting_unit_name,"
									+ "accounting_unit_office_id,OFFICE_NAME from (select accounting_unit_id ,"
									+ "accounting_unit_name,accounting_unit_office_id from  "
									+ "fas_mst_acct_units where accounting_unit_office_id in "
									+ "(select REGION_OFFICE_ID  from COM_MST_ALL_OFFICES_VIEW  "
									+ " )  )x left outer join(select OFFICE_ID,"
									+ "OFFICE_NAME from COM_MST_OFFICES)y on "
									+ "x.accounting_unit_office_id =y.OFFICE_ID  ");
					rs = ps1.executeQuery();
					while (rs.next()) {
						pss = connection
								.prepareStatement(" select ACCOUNTING_UNIT_ID from FAS_BUDGET_CLOSURE_CONSOLIDATE where FINANCIAL_YEAR=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?  and FORMAT_NAME=?");
						pss.setString(1, (year1 + "-" + year2));
						pss.setInt(2, rs.getInt("accounting_unit_id"));
						pss.setInt(3, rs.getInt("accounting_unit_office_id"));
						pss.setString(4, "3");
						results2 = pss.executeQuery();
						if (results2.next()) {
							flag = 1;
						} else {
							flag1 = 1;
						}
					}
				}
				try {
					ps1 = connection
							.prepareStatement("select UPDATED_DATE from FAS_BUDGET_CLOSURE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and FORMAT_NAME=?");
					ps1.setInt(1, cmbAcc_UnitCode);
					ps1.setInt(2, cmbOffice_code);
					ps1.setString(3, (year1) + "-" + (year2));
					ps1.setString(4, "3");
					results = ps1.executeQuery();
					if (results.next()) {
						flag = 1;
					} else {
						flag1 = 2;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				try {
					ps1 = connection
							.prepareStatement("select UPDATED_DATE from FAS_BUDGET_CLOSURE_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and FORMAT_NAME=?");
					ps1.setInt(1, cmbAcc_UnitCode);
					ps1.setInt(2, cmbOffice_code);
					ps1.setString(3, (year1) + "-" + (year2));
					ps1.setString(4, "3");
					results = ps1.executeQuery();
					if (results.next()) {
						flag1 = 3;
					} else {
						flag = 1;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}

				if (flag1 == 1) {
					xml = xml + "<flag>Freeze_Pending</flag>";
				} else if (flag1 == 2) {
					xml = xml + "<flag>Freeze_Pending1</flag>";
				} else if (flag1 == 3) {
					xml = xml + "<flag>Freeze_Done</flag>";
				} else {

					ps = connection.prepareStatement(sql);
					ps.setString(1, (year1 + "-" + year2));
					if (office_level_id.equals("CL")) {
						ps.setInt(2, office_id);
					} else if (office_level_id.equals("RN")) {
						ps.setInt(2, office_id);
					}
					rs = ps.executeQuery();
					while (rs.next()) {
						xml = xml + "<slno>" + rs.getInt("SL_NO") + "</slno>";

						xml = xml + "<Name_of_Category>"
								+ rs.getString("NAME_OF_CATEGORY")
								+ "</Name_of_Category>";

						xml = xml + "<Time_Scale_of_Pay>"
								+ rs.getString("TIME_SCALE_OF_PAY")
								+ "</Time_Scale_of_Pay>";

						xml = xml + "<No_of_Sanctioned_Posts>"
								+ rs.getInt("NO_OF_SANCTIONED_POSTS")
								+ "</No_of_Sanctioned_Posts>";

						xml = xml + "<No_of_Incumbents_in_Roll>"
								+ rs.getInt("NO_OF_INCUMBENTS_IN_ROLL")
								+ "</No_of_Incumbents_in_Roll>";

						xml = xml + "<No_of_Vacant_Posts>"
								+ rs.getInt("NO_OF_VACANT_POSTS")
								+ "</No_of_Vacant_Posts>";

						xml = xml + "<Begining_of_the_Year_Basic_Pay>"
								+ rs.getFloat("BEGINING_OF_THE_YEAR_BASIC_PAY")
								+ "</Begining_of_the_Year_Basic_Pay>";

						xml = xml + "<Begining_of_the_Year_50_BP>"
								+ rs.getFloat("BEGINING_OF_THE_YEAR_50_BP")
								+ "</Begining_of_the_Year_50_BP>";

						xml = xml + "<Increment_Date>"
								+ rs.getString("INCREMENT_DATE")
								+ "</Increment_Date>";

						xml = xml + "<Increment_Amount>"
								+ rs.getFloat("INCREMENT_AMOUNT")
								+ "</Increment_Amount>";

						xml = xml + "<After_Increment_Basic_Pay>"
								+ rs.getFloat("AFTER_INCREMENT_BASIC_PAY")
								+ "</After_Increment_Basic_Pay>";

						xml = xml + "<After_Increment_50_BP>"
								+ rs.getFloat("AFTER_INCREMENT_50_BP")
								+ "</After_Increment_50_BP>";

						xml = xml + "<Basic_Pay_After_Increment>"
								+ rs.getFloat("BASIC_PAY_AFTER_INCREMENT")
								+ "</Basic_Pay_After_Increment>";

						xml = xml + "<Basic_Pay_50_Dearness_pay>"
								+ rs.getFloat("BASIC_PAY_50_DEARNESS_PAY")
								+ "</Basic_Pay_50_Dearness_pay>";

						xml = xml + "<DA_32_on_Col_No_13>"
								+ rs.getFloat("DA_32_ON_COL_NO_13")
								+ "</DA_32_on_Col_No_13>";

						xml = xml + "<Other_Allowances>"
								+ rs.getFloat("OTHER_ALLOWANCES")
								+ "</Other_Allowances>";

						xml = xml + "<Total>" + rs.getFloat("TOTAL")
								+ "</Total>";

						xml = xml + "<Date_of_Retirement>"
								+ rs.getString("DATE_OF_RETIREMENT")
								+ "</Date_of_Retirement>";
					}
					xml = xml + "<flag>success</flag>";
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
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

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
		String FinancialYear = null;
		/* Get Accounting Unit ID */
		try {
			cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
		} catch (Exception e) {
			System.out.println("Error Not Getting Accounitng Unit ID --> " + e);
		}

		/* Get Accounting for Office ID */
		try {
			cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));

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

		int RecordCount = 0;
		String filter = null;
		/*
		 * Get Total Number of Records
		 */
		try {
			filter = request.getParameter("filter");
		} catch (Exception e) {
			System.out.println("Error Getting filter value ");
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
		String check[] = new String[RecordCount];
		String slno[] = new String[RecordCount];

		String Name_of_Category[] = new String[RecordCount];
		String Time_Scale_of_Pay[] = new String[RecordCount];
		String No_of_Sanctioned_Posts[] = new String[RecordCount];
		String No_of_Incumbents_in_Roll[] = new String[RecordCount];
		String No_of_Vacant_Posts[] = new String[RecordCount];
		String Begining_of_the_Year_Basic_Pay[] = new String[RecordCount];
		String Begining_of_the_Year_50_BP[] = new String[RecordCount];
		String Increment_Date[] = new String[RecordCount];
		String Increment_Amount[] = new String[RecordCount];
		String After_Increment_Basic_Pay[] = new String[RecordCount];
		String After_Increment_50_BP[] = new String[RecordCount];
		String Basic_Pay_After_Increment[] = new String[RecordCount];
		String Basic_Pay_50_Dearness_pay[] = new String[RecordCount];
		String DA_32_on_Col_No_13[] = new String[RecordCount];
		String Other_Allowances[] = new String[RecordCount];
		String Total[] = new String[RecordCount];
		String Date_of_Retirement[] = new String[RecordCount];

		/* Variables Declaration */
		int check2 = 0;
		int slno2 = 0;
		String sd[] = new String[10];
		java.util.Date d = null;
		Calendar c;

		String Name_of_Category2 = null;
		String Time_Scale_of_Pay2 = null;
		int No_of_Sanctioned_Posts2 = 0;
		int No_of_Incumbents_in_Roll2 = 0;
		int No_of_Vacant_Posts2 = 0;
		float Begining_of_the_Year_Basic_Pay2 = 0;
		float Begining_of_the_Year_50_BP2 = 0.0f;
		Date Increment_Date2 = null;
		float Increment_Amount2 = 0.0f;
		float After_Increment_Basic_Pay2 = 0.0f;
		float After_Increment_50_BP2 = 0.0f;
		float Basic_Pay_After_Increment2 = 0.0f;
		float Basic_Pay_50_Dearness_pay2 = 0.0f;
		float DA_32_on_Col_No_132 = 0.0f;
		float Other_Allowances2 = 0.0f;
		float Total2 = 0.0f;
		Date Date_of_Retirement2 = null;

		int flag = 0;
		String office_level_id = null;
		int division_id = 0;
		int circle_id = 0;
		int region_id = 0;
		int head_office_id = 0;
		try {
			con.setAutoCommit(false);
			con.clearWarnings();
			if (filter.equals("save")) {
				ps1 = con
						.prepareStatement("select UPDATED_DATE from FAS_BUDGET_CLOSURE_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and FORMAT_NAME=?");
				ps1.setInt(1, cmbAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setString(3, FinancialYear);
				ps1.setString(4, "3");
				rs2 = ps1.executeQuery();
				if (rs2.next()) {
					sendMessage(
							response,
							"Civil Budget Format-3 Consolidation have Already Freezed ............ ",
							"ok", "Civil_Budget_Format_3_Consolidation.jsp");
				} else {
					ps = con
							.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR from FAS_BUDGET_FORMAT3_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setString(3, FinancialYear);
					rs = ps.executeQuery();
					if (rs.next()) {
						flag = 1;
					} else {
						ps1 = con
								.prepareStatement(" select DIVISION_OFFICE_ID,CIRCLE_OFFICE_ID,REGION_OFFICE_ID,"
										+ "OFFICE_ID as HEAD_OFFICE_ID,OFFICE_LEVEL_ID from ( SELECT ROWNUM AS slno1,"
										+ "OFFICE_LEVEL_ID,REGION_OFFICE_ID,CIRCLE_OFFICE_ID,DIVISION_OFFICE_ID FROM "
										+ "COM_MST_ALL_OFFICES_VIEW WHERE OFFICE_ID=? )x left outer join ( "
										+ "SELECT ROWNUM AS SLNO2,OFFICE_ID FROM COM_MST_ALL_OFFICES_VIEW WHERE "
										+ "OFFICE_LEVEL_ID='HO' )y on x.slno1 =y.slno2  ");
						ps1.setInt(1, cmbOffice_code);
						rs = ps1.executeQuery();
						if (rs.next()) {
							division_id = rs.getInt("DIVISION_OFFICE_ID");
							circle_id = rs.getInt("CIRCLE_OFFICE_ID");
							region_id = rs.getInt("REGION_OFFICE_ID");
							head_office_id = rs.getInt("HEAD_OFFICE_ID");
							office_level_id = rs.getString("OFFICE_LEVEL_ID");
						}
						for (int k = 0; k < RecordCount; k++) {

							try {
								Name_of_Category2 = request
										.getParameter("Name_of_Category" + k);
							} catch (Exception e) {
								System.out
										.println("Error for getting Name_of_Category -->"
												+ e);
							}

							try {
								Time_Scale_of_Pay2 = request
										.getParameter("Time_Scale_of_Pay" + k);

							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								No_of_Sanctioned_Posts[k] = request
										.getParameter("No_of_Sanctioned_Posts"
												+ k);
								if (No_of_Sanctioned_Posts[k] != null) {
									if (No_of_Sanctioned_Posts[k].equals("")) {
										No_of_Sanctioned_Posts2 = 0;
									} else {
										No_of_Sanctioned_Posts2 = Integer
												.parseInt(No_of_Sanctioned_Posts[k]);
									}
								} else {
									No_of_Sanctioned_Posts2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting No_of_Sanctioned_Posts -->"
												+ e);
							}

							try {
								No_of_Incumbents_in_Roll[k] = request
										.getParameter("No_of_Incumbents_in_Roll"
												+ k);
								if (No_of_Incumbents_in_Roll[k] != null) {
									if (No_of_Incumbents_in_Roll[k].equals("")) {
										No_of_Incumbents_in_Roll2 = 0;
									} else {
										No_of_Incumbents_in_Roll2 = Integer
												.parseInt(No_of_Incumbents_in_Roll[k]);
									}
								} else {
									No_of_Incumbents_in_Roll2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting No_of_Incumbents_in_Roll -->"
												+ e);
							}

							try {
								No_of_Vacant_Posts[k] = request
										.getParameter("No_of_Vacant_Posts" + k);
								if (No_of_Vacant_Posts[k] != null) {
									if (No_of_Vacant_Posts[k].equals("")) {
										No_of_Vacant_Posts2 = 0;
									} else {
										No_of_Vacant_Posts2 = Integer
												.parseInt(No_of_Vacant_Posts[k]);
									}
								} else {
									No_of_Vacant_Posts2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting No_of_Vacant_Posts -->"
												+ e);
							}

							try {
								Begining_of_the_Year_Basic_Pay[k] = request
										.getParameter("Begining_of_the_Year_Basic_Pay"
												+ k);
								if (Begining_of_the_Year_Basic_Pay[k] != null) {
									if (Begining_of_the_Year_Basic_Pay[k]
											.equals("")) {
										Begining_of_the_Year_Basic_Pay2 = 0;
									} else {
										Begining_of_the_Year_Basic_Pay2 = Float
												.parseFloat(Begining_of_the_Year_Basic_Pay[k]);
									}
								} else {
									Begining_of_the_Year_Basic_Pay2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting Current_Year_Jobs_Complited -->"
												+ e);
							}

							try {
								Begining_of_the_Year_50_BP[k] = request
										.getParameter("Begining_of_the_Year_50_BP"
												+ k);
								if (Begining_of_the_Year_50_BP[k] != null) {
									if (Begining_of_the_Year_50_BP[k]
											.equals("")) {
										Begining_of_the_Year_50_BP2 = 0.0f;
									} else {
										Begining_of_the_Year_50_BP2 = Float
												.parseFloat(Begining_of_the_Year_50_BP[k]);
									}
								} else {
									Begining_of_the_Year_50_BP2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Increment_Date[k] = request
										.getParameter("Increment_Date" + k);

								if (!Increment_Date[k].equalsIgnoreCase("")) {
									sd = Increment_Date[k].split("/");
									c = new GregorianCalendar(Integer
											.parseInt(sd[2]), Integer
											.parseInt(sd[1]) - 1, Integer
											.parseInt(sd[0]));
									d = c.getTime();
									Increment_Date2 = new Date(d.getTime());
								} else {
									Increment_Date2 = null;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Increment_Amount[k] = request
										.getParameter("Increment_Amount" + k);
								if (Increment_Amount[k] != null) {
									if (Increment_Amount[k].equals("")) {
										Increment_Amount2 = 0.0f;
									} else {
										Increment_Amount2 = Float
												.parseFloat(Increment_Amount[k]);
									}
								} else {
									Increment_Amount2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								After_Increment_Basic_Pay[k] = request
										.getParameter("After_Increment_Basic_Pay"
												+ k);
								if (After_Increment_Basic_Pay[k] != null) {
									if (After_Increment_Basic_Pay[k].equals("")) {
										After_Increment_Basic_Pay2 = 0.0f;
									} else {
										After_Increment_Basic_Pay2 = Float
												.parseFloat(After_Increment_Basic_Pay[k]);
									}
								} else {
									After_Increment_Basic_Pay2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								After_Increment_50_BP[k] = request
										.getParameter("After_Increment_50_BP"
												+ k);
								if (After_Increment_50_BP[k] != null) {
									if (After_Increment_50_BP[k].equals("")) {
										After_Increment_50_BP2 = 0.0f;
									} else {
										After_Increment_50_BP2 = Float
												.parseFloat(After_Increment_50_BP[k]);
									}
								} else {
									After_Increment_50_BP2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Basic_Pay_After_Increment[k] = request
										.getParameter("Basic_Pay_After_Increment"
												+ k);
								if (Basic_Pay_After_Increment[k] != null) {
									if (Basic_Pay_After_Increment[k].equals("")) {
										Basic_Pay_After_Increment2 = 0.0f;
									} else {
										Basic_Pay_After_Increment2 = Float
												.parseFloat(Basic_Pay_After_Increment[k]);
									}
								} else {
									Basic_Pay_After_Increment2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Basic_Pay_50_Dearness_pay[k] = request
										.getParameter("Basic_Pay_50_Dearness_pay"
												+ k);
								if (Basic_Pay_50_Dearness_pay[k] != null) {
									if (Basic_Pay_50_Dearness_pay[k].equals("")) {
										Basic_Pay_50_Dearness_pay2 = 0.0f;
									} else {
										Basic_Pay_50_Dearness_pay2 = Float
												.parseFloat(Basic_Pay_50_Dearness_pay[k]);
									}
								} else {
									Basic_Pay_50_Dearness_pay2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								DA_32_on_Col_No_13[k] = request
										.getParameter("DA_32_on_Col_No_13" + k);
								if (DA_32_on_Col_No_13[k] != null) {
									if (DA_32_on_Col_No_13[k].equals("")) {
										DA_32_on_Col_No_132 = 0.0f;
									} else {
										DA_32_on_Col_No_132 = Float
												.parseFloat(DA_32_on_Col_No_13[k]);
									}
								} else {
									DA_32_on_Col_No_132 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Other_Allowances[k] = request
										.getParameter("Other_Allowances" + k);
								if (Other_Allowances[k] != null) {
									if (Other_Allowances[k].equals("")) {
										Other_Allowances2 = 0.0f;
									} else {
										Other_Allowances2 = Float
												.parseFloat(Other_Allowances[k]);
									}
								} else {
									Other_Allowances2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Total[k] = request.getParameter("Total" + k);
								if (Total[k] != null) {
									if (Total[k].equals("")) {
										Total2 = 0.0f;
									} else {
										Total2 = Float.parseFloat(Total[k]);
									}
								} else {
									Total2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Date_of_Retirement[k] = request
										.getParameter("Date_of_Retirement" + k);

								if (!Date_of_Retirement[k].equalsIgnoreCase("")) {
									sd = Date_of_Retirement[k].split("/");
									c = new GregorianCalendar(Integer
											.parseInt(sd[2]), Integer
											.parseInt(sd[1]) - 1, Integer
											.parseInt(sd[0]));
									d = c.getTime();
									Date_of_Retirement2 = new Date(d.getTime());
								} else {
									Date_of_Retirement2 = null;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							int i = 1, i1 = 0;
							try {
								ps = con
										.prepareStatement("Select max(SL_NO) from FAS_BUDGET_FORMAT3_CONSOLIDATE");
								rs = ps.executeQuery();

								if (rs.next()) {
									i1 = rs.getInt(1);
									i = i + i1;
								} else {
									i = i;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							ps = con
									.prepareStatement("insert into FAS_BUDGET_FORMAT3_CONSOLIDATE (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,NAME_OF_CATEGORY,TIME_SCALE_OF_PAY,NO_OF_SANCTIONED_POSTS,NO_OF_INCUMBENTS_IN_ROLL,NO_OF_VACANT_POSTS,BEGINING_OF_THE_YEAR_BASIC_PAY,BEGINING_OF_THE_YEAR_50_BP,INCREMENT_DATE,INCREMENT_AMOUNT,AFTER_INCREMENT_BASIC_PAY,AFTER_INCREMENT_50_BP,BASIC_PAY_AFTER_INCREMENT,BASIC_PAY_50_DEARNESS_PAY,DA_32_ON_COL_NO_13,OTHER_ALLOWANCES,TOTAL,DATE_OF_RETIREMENT,DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
							ps.setInt(1, cmbAcc_UnitCode);
							ps.setInt(2, cmbOffice_code);
							ps.setString(3, FinancialYear);
							ps.setInt(4, i);
							ps.setString(5, Name_of_Category2);
							ps.setString(6, Time_Scale_of_Pay2);
							ps.setInt(7, No_of_Sanctioned_Posts2);
							ps.setInt(8, No_of_Incumbents_in_Roll2);
							ps.setInt(9, No_of_Vacant_Posts2);
							ps.setFloat(10, Begining_of_the_Year_Basic_Pay2);
							ps.setFloat(11, Begining_of_the_Year_50_BP2);
							ps.setDate(12, Increment_Date2);
							ps.setFloat(13, Increment_Amount2);
							ps.setFloat(14, After_Increment_Basic_Pay2);
							ps.setFloat(15, After_Increment_50_BP2);
							ps.setFloat(16, Basic_Pay_After_Increment2);
							ps.setFloat(17, Basic_Pay_50_Dearness_pay2);
							ps.setFloat(18, DA_32_on_Col_No_132);
							ps.setFloat(19, Other_Allowances2);
							ps.setFloat(20, Total2);
							ps.setDate(21, Date_of_Retirement2);
							ps.setInt(22, division_id);
							ps.setInt(23, circle_id);
							ps.setInt(24, region_id);
							ps.setInt(25, head_office_id);
							ps.setString(26, office_level_id);
							ps.setString(27, userid);
							ps.setTimestamp(28, ts);
							int kk = ps.executeUpdate();
						}
						flag = 2;
					}
					con.commit();
					if (flag == 1) {
						sendMessage(
								response,
								"Records Alredy Exist for given Office for given Financial Year ............ ",
								"ok", "Civil_Budget_Format_3_Consolidation.jsp");
					} else if (flag == 2) {
						sendMessage(response,
								"Records Saved Successfully ............ ",
								"ok", "Civil_Budget_Format_3_Consolidation.jsp");
					}
				}
			} else if (filter.equals("view")) {
				try {
					xml = "<response><command>LoadData</command>";
					ps1 = con
							.prepareStatement("select UPDATED_DATE from FAS_BUDGET_CLOSURE_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and FORMAT_NAME=?");
					ps1.setInt(1, cmbAcc_UnitCode);
					ps1.setInt(2, cmbOffice_code);
					ps1.setString(3, FinancialYear);
					ps1.setString(4, "3");
					rs = ps1.executeQuery();
					if (rs.next()) {
						xml = xml + "<flag>Freezed</flag>";
					} else {
						ps = con
								.prepareStatement(" select SL_NO,NAME_OF_CATEGORY,TIME_SCALE_OF_PAY,NO_OF_SANCTIONED_POSTS,NO_OF_INCUMBENTS_IN_ROLL,NO_OF_VACANT_POSTS,BEGINING_OF_THE_YEAR_BASIC_PAY,BEGINING_OF_THE_YEAR_50_BP,to_char(INCREMENT_DATE,'DD/MM/YYYY') as INCREMENT_DATE,INCREMENT_AMOUNT,AFTER_INCREMENT_BASIC_PAY,AFTER_INCREMENT_50_BP,BASIC_PAY_AFTER_INCREMENT,BASIC_PAY_50_DEARNESS_PAY,DA_32_ON_COL_NO_13,OTHER_ALLOWANCES,TOTAL,to_char(DATE_OF_RETIREMENT,'DD/MM/YYYY') as DATE_OF_RETIREMENT from FAS_BUDGET_FORMAT3_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, FinancialYear);
						rs = ps.executeQuery();
						while (rs.next()) {
							xml = xml + "<slno>" + rs.getInt("SL_NO")
									+ "</slno>";

							xml = xml + "<Name_of_Category>"
									+ rs.getString("NAME_OF_CATEGORY")
									+ "</Name_of_Category>";

							xml = xml + "<Time_Scale_of_Pay>"
									+ rs.getString("TIME_SCALE_OF_PAY")
									+ "</Time_Scale_of_Pay>";

							xml = xml + "<No_of_Sanctioned_Posts>"
									+ rs.getInt("NO_OF_SANCTIONED_POSTS")
									+ "</No_of_Sanctioned_Posts>";

							xml = xml + "<No_of_Incumbents_in_Roll>"
									+ rs.getInt("NO_OF_INCUMBENTS_IN_ROLL")
									+ "</No_of_Incumbents_in_Roll>";

							xml = xml + "<No_of_Vacant_Posts>"
									+ rs.getInt("NO_OF_VACANT_POSTS")
									+ "</No_of_Vacant_Posts>";

							xml = xml
									+ "<Begining_of_the_Year_Basic_Pay>"
									+ rs
											.getFloat("BEGINING_OF_THE_YEAR_BASIC_PAY")
									+ "</Begining_of_the_Year_Basic_Pay>";

							xml = xml + "<Begining_of_the_Year_50_BP>"
									+ rs.getFloat("BEGINING_OF_THE_YEAR_50_BP")
									+ "</Begining_of_the_Year_50_BP>";

							xml = xml + "<Increment_Date>"
									+ rs.getString("INCREMENT_DATE")
									+ "</Increment_Date>";

							xml = xml + "<Increment_Amount>"
									+ rs.getFloat("INCREMENT_AMOUNT")
									+ "</Increment_Amount>";

							xml = xml + "<After_Increment_Basic_Pay>"
									+ rs.getFloat("AFTER_INCREMENT_BASIC_PAY")
									+ "</After_Increment_Basic_Pay>";

							xml = xml + "<After_Increment_50_BP>"
									+ rs.getFloat("AFTER_INCREMENT_50_BP")
									+ "</After_Increment_50_BP>";

							xml = xml + "<Basic_Pay_After_Increment>"
									+ rs.getFloat("BASIC_PAY_AFTER_INCREMENT")
									+ "</Basic_Pay_After_Increment>";

							xml = xml + "<Basic_Pay_50_Dearness_pay>"
									+ rs.getFloat("BASIC_PAY_50_DEARNESS_PAY")
									+ "</Basic_Pay_50_Dearness_pay>";

							xml = xml + "<DA_32_on_Col_No_13>"
									+ rs.getFloat("DA_32_ON_COL_NO_13")
									+ "</DA_32_on_Col_No_13>";

							xml = xml + "<Other_Allowances>"
									+ rs.getFloat("OTHER_ALLOWANCES")
									+ "</Other_Allowances>";

							xml = xml + "<Total>" + rs.getFloat("TOTAL")
									+ "</Total>";

							xml = xml + "<Date_of_Retirement>"
									+ rs.getString("DATE_OF_RETIREMENT")
									+ "</Date_of_Retirement>";

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
					int k = 0;
					for (k = 0; k < RecordCount; k++) {
						/* Check Box */
						try {
							check[k] = request.getParameter("slno_db1" + k);
							check2 = Integer.parseInt(check[k]);

						} catch (Exception e) {
							System.out
									.println("Error for getting Head_of_Account -->"
											+ e);
						}
						if (k == check2) {
							try {
								slno[k] = request.getParameter("slno_db" + k);
								if (slno[k] != null) {
									if (slno[k].equals("")) {
										slno2 = 0;
									} else {
										slno2 = Integer.parseInt(slno[k]);
									}
								} else {
									slno2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting Head_of_Account -->"
												+ e);
							}
							ps = con
									.prepareStatement(" select * from FAS_BUDGET_FORMAT3_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and SL_NO=?");
							ps.setInt(1, cmbAcc_UnitCode);
							ps.setInt(2, cmbOffice_code);
							ps.setString(3, FinancialYear);
							ps.setInt(4, slno2);
							rs = ps.executeQuery();
							if (rs.next()) {
								ps1 = con
										.prepareStatement(" delete from FAS_BUDGET_FORMAT3_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and SL_NO=?");
								ps1.setInt(1, cmbAcc_UnitCode);
								ps1.setInt(2, cmbOffice_code);
								ps1.setString(3, FinancialYear);
								ps1.setInt(4, slno2);
								int sbg = ps1.executeUpdate();
							} else {
								flag = 2;
							}
							flag = 3;
						}
					}
					if (flag == 3) {
						con.commit();
						sendMessage(response,
								"Records Deleted Successfully ............ ",
								"ok", "Civil_Budget_Format_3_Consolidation.jsp");
					} else if (flag == 3) {
						con.commit();
						sendMessage(response,
								"Record Does Not Exist ............ ", "ok",
								"Civil_Budget_Format_3_Consolidation.jsp");
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Deletion Failed ............ " + e, "ok",
							"Civil_Budget_Format_3_Consolidation.jsp");
					try {
						con.rollback();
						con.setAutoCommit(true);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			} else if (filter.equals("update")) {
				try {
					ps1 = con
							.prepareStatement("select UPDATED_DATE from FAS_BUDGET_CLOSURE_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and FORMAT_NAME=?");
					ps1.setInt(1, cmbAcc_UnitCode);
					ps1.setInt(2, cmbOffice_code);
					ps1.setString(3, FinancialYear);
					ps1.setString(4, "3");
					rs2 = ps1.executeQuery();
					if (rs2.next()) {
						sendMessage(
								response,
								"Civil Budget Format-3 have Already Freezed ............ ",
								"ok", "Civil_Budget_Format_3_Consolidation.jsp");
					} else {
						ps1 = con
								.prepareStatement(" select DIVISION_OFFICE_ID,CIRCLE_OFFICE_ID,REGION_OFFICE_ID,"
										+ "OFFICE_ID as HEAD_OFFICE_ID,OFFICE_LEVEL_ID from ( SELECT ROWNUM AS slno1,"
										+ "OFFICE_LEVEL_ID,REGION_OFFICE_ID,CIRCLE_OFFICE_ID,DIVISION_OFFICE_ID FROM "
										+ "COM_MST_ALL_OFFICES_VIEW WHERE OFFICE_ID=? )x left outer join ( "
										+ "SELECT ROWNUM AS SLNO2,OFFICE_ID FROM COM_MST_ALL_OFFICES_VIEW WHERE "
										+ "OFFICE_LEVEL_ID='HO' )y on x.slno1 =y.slno2  ");
						ps1.setInt(1, cmbOffice_code);
						rs = ps1.executeQuery();
						if (rs.next()) {
							division_id = rs.getInt("DIVISION_OFFICE_ID");
							circle_id = rs.getInt("CIRCLE_OFFICE_ID");
							region_id = rs.getInt("REGION_OFFICE_ID");
							head_office_id = rs.getInt("HEAD_OFFICE_ID");
							office_level_id = rs.getString("OFFICE_LEVEL_ID");
						}
						ps = con
								.prepareStatement(" select * from FAS_BUDGET_FORMAT3_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, FinancialYear);
						rs = ps.executeQuery();
						if (rs.next()) {
							ps1 = con
									.prepareStatement(" delete from FAS_BUDGET_FORMAT3_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
							ps1.setInt(1, cmbAcc_UnitCode);
							ps1.setInt(2, cmbOffice_code);
							ps1.setString(3, FinancialYear);
							ps1.executeUpdate();
							for (int k = 0; k < RecordCount; k++) {

								try {
									Name_of_Category2 = request
											.getParameter("Name_of_Category"
													+ k);
								} catch (Exception e) {
									System.out
											.println("Error for getting Name_of_Category -->"
													+ e);
								}

								try {
									Time_Scale_of_Pay2 = request
											.getParameter("Time_Scale_of_Pay"
													+ k);

								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									No_of_Sanctioned_Posts[k] = request
											.getParameter("No_of_Sanctioned_Posts"
													+ k);
									if (No_of_Sanctioned_Posts[k] != null) {
										if (No_of_Sanctioned_Posts[k]
												.equals("")) {
											No_of_Sanctioned_Posts2 = 0;
										} else {
											No_of_Sanctioned_Posts2 = Integer
													.parseInt(No_of_Sanctioned_Posts[k]);
										}
									} else {
										No_of_Sanctioned_Posts2 = 0;
									}
								} catch (Exception e) {
									System.out
											.println("Error for getting No_of_Sanctioned_Posts -->"
													+ e);
								}

								try {
									No_of_Incumbents_in_Roll[k] = request
											.getParameter("No_of_Incumbents_in_Roll"
													+ k);
									if (No_of_Incumbents_in_Roll[k] != null) {
										if (No_of_Incumbents_in_Roll[k]
												.equals("")) {
											No_of_Incumbents_in_Roll2 = 0;
										} else {
											No_of_Incumbents_in_Roll2 = Integer
													.parseInt(No_of_Incumbents_in_Roll[k]);
										}
									} else {
										No_of_Incumbents_in_Roll2 = 0;
									}
								} catch (Exception e) {
									System.out
											.println("Error for getting No_of_Incumbents_in_Roll -->"
													+ e);
								}

								try {
									No_of_Vacant_Posts[k] = request
											.getParameter("No_of_Vacant_Posts"
													+ k);
									if (No_of_Vacant_Posts[k] != null) {
										if (No_of_Vacant_Posts[k].equals("")) {
											No_of_Vacant_Posts2 = 0;
										} else {
											No_of_Vacant_Posts2 = Integer
													.parseInt(No_of_Vacant_Posts[k]);
										}
									} else {
										No_of_Vacant_Posts2 = 0;
									}
								} catch (Exception e) {
									System.out
											.println("Error for getting No_of_Vacant_Posts -->"
													+ e);
								}

								try {
									Begining_of_the_Year_Basic_Pay[k] = request
											.getParameter("Begining_of_the_Year_Basic_Pay"
													+ k);
									if (Begining_of_the_Year_Basic_Pay[k] != null) {
										if (Begining_of_the_Year_Basic_Pay[k]
												.equals("")) {
											Begining_of_the_Year_Basic_Pay2 = 0;
										} else {
											Begining_of_the_Year_Basic_Pay2 = Float
													.parseFloat(Begining_of_the_Year_Basic_Pay[k]);
										}
									} else {
										Begining_of_the_Year_Basic_Pay2 = 0;
									}
								} catch (Exception e) {
									System.out
											.println("Error for getting Current_Year_Jobs_Complited -->"
													+ e);
								}

								try {
									Begining_of_the_Year_50_BP[k] = request
											.getParameter("Begining_of_the_Year_50_BP"
													+ k);
									if (Begining_of_the_Year_50_BP[k] != null) {
										if (Begining_of_the_Year_50_BP[k]
												.equals("")) {
											Begining_of_the_Year_50_BP2 = 0.0f;
										} else {
											Begining_of_the_Year_50_BP2 = Float
													.parseFloat(Begining_of_the_Year_50_BP[k]);
										}
									} else {
										Begining_of_the_Year_50_BP2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Increment_Date[k] = request
											.getParameter("Increment_Date" + k);

									if (!Increment_Date[k].equalsIgnoreCase("")) {
										sd = Increment_Date[k].split("/");
										c = new GregorianCalendar(Integer
												.parseInt(sd[2]), Integer
												.parseInt(sd[1]) - 1, Integer
												.parseInt(sd[0]));
										d = c.getTime();
										Increment_Date2 = new Date(d.getTime());
									} else {
										Increment_Date2 = null;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Increment_Amount[k] = request
											.getParameter("Increment_Amount"
													+ k);
									if (Increment_Amount[k] != null) {
										if (Increment_Amount[k].equals("")) {
											Increment_Amount2 = 0.0f;
										} else {
											Increment_Amount2 = Float
													.parseFloat(Increment_Amount[k]);
										}
									} else {
										Increment_Amount2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									After_Increment_Basic_Pay[k] = request
											.getParameter("After_Increment_Basic_Pay"
													+ k);
									if (After_Increment_Basic_Pay[k] != null) {
										if (After_Increment_Basic_Pay[k]
												.equals("")) {
											After_Increment_Basic_Pay2 = 0.0f;
										} else {
											After_Increment_Basic_Pay2 = Float
													.parseFloat(After_Increment_Basic_Pay[k]);
										}
									} else {
										After_Increment_Basic_Pay2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									After_Increment_50_BP[k] = request
											.getParameter("After_Increment_50_BP"
													+ k);
									if (After_Increment_50_BP[k] != null) {
										if (After_Increment_50_BP[k].equals("")) {
											After_Increment_50_BP2 = 0.0f;
										} else {
											After_Increment_50_BP2 = Float
													.parseFloat(After_Increment_50_BP[k]);
										}
									} else {
										After_Increment_50_BP2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Basic_Pay_After_Increment[k] = request
											.getParameter("Basic_Pay_After_Increment"
													+ k);
									if (Basic_Pay_After_Increment[k] != null) {
										if (Basic_Pay_After_Increment[k]
												.equals("")) {
											Basic_Pay_After_Increment2 = 0.0f;
										} else {
											Basic_Pay_After_Increment2 = Float
													.parseFloat(Basic_Pay_After_Increment[k]);
										}
									} else {
										Basic_Pay_After_Increment2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Basic_Pay_50_Dearness_pay[k] = request
											.getParameter("Basic_Pay_50_Dearness_pay"
													+ k);
									if (Basic_Pay_50_Dearness_pay[k] != null) {
										if (Basic_Pay_50_Dearness_pay[k]
												.equals("")) {
											Basic_Pay_50_Dearness_pay2 = 0.0f;
										} else {
											Basic_Pay_50_Dearness_pay2 = Float
													.parseFloat(Basic_Pay_50_Dearness_pay[k]);
										}
									} else {
										Basic_Pay_50_Dearness_pay2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									DA_32_on_Col_No_13[k] = request
											.getParameter("DA_32_on_Col_No_13"
													+ k);
									if (DA_32_on_Col_No_13[k] != null) {
										if (DA_32_on_Col_No_13[k].equals("")) {
											DA_32_on_Col_No_132 = 0.0f;
										} else {
											DA_32_on_Col_No_132 = Float
													.parseFloat(DA_32_on_Col_No_13[k]);
										}
									} else {
										DA_32_on_Col_No_132 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Other_Allowances[k] = request
											.getParameter("Other_Allowances"
													+ k);
									if (Other_Allowances[k] != null) {
										if (Other_Allowances[k].equals("")) {
											Other_Allowances2 = 0.0f;
										} else {
											Other_Allowances2 = Float
													.parseFloat(Other_Allowances[k]);
										}
									} else {
										Other_Allowances2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Total[k] = request
											.getParameter("Total" + k);
									if (Total[k] != null) {
										if (Total[k].equals("")) {
											Total2 = 0.0f;
										} else {
											Total2 = Float.parseFloat(Total[k]);
										}
									} else {
										Total2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Date_of_Retirement[k] = request
											.getParameter("Date_of_Retirement"
													+ k);

									if (!Date_of_Retirement[k]
											.equalsIgnoreCase("")) {
										sd = Date_of_Retirement[k].split("/");
										c = new GregorianCalendar(Integer
												.parseInt(sd[2]), Integer
												.parseInt(sd[1]) - 1, Integer
												.parseInt(sd[0]));
										d = c.getTime();
										Date_of_Retirement2 = new Date(d
												.getTime());
									} else {
										Date_of_Retirement2 = null;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								int i = 1, i1 = 0;
								try {
									ps = con
											.prepareStatement("Select max(SL_NO) from FAS_BUDGET_FORMAT3_CONSOLIDATE");
									rs = ps.executeQuery();

									if (rs.next()) {
										i1 = rs.getInt(1);
										i = i + i1;
									} else {
										i = i;
									}
								} catch (Exception e) {
									e.printStackTrace();
								}

								ps = con
										.prepareStatement("insert into FAS_BUDGET_FORMAT3_CONSOLIDATE (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,NAME_OF_CATEGORY,TIME_SCALE_OF_PAY,NO_OF_SANCTIONED_POSTS,NO_OF_INCUMBENTS_IN_ROLL,NO_OF_VACANT_POSTS,BEGINING_OF_THE_YEAR_BASIC_PAY,BEGINING_OF_THE_YEAR_50_BP,INCREMENT_DATE,INCREMENT_AMOUNT,AFTER_INCREMENT_BASIC_PAY,AFTER_INCREMENT_50_BP,BASIC_PAY_AFTER_INCREMENT,BASIC_PAY_50_DEARNESS_PAY,DA_32_ON_COL_NO_13,OTHER_ALLOWANCES,TOTAL,DATE_OF_RETIREMENT,DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
								ps.setInt(1, cmbAcc_UnitCode);
								ps.setInt(2, cmbOffice_code);
								ps.setString(3, FinancialYear);
								ps.setInt(4, i);
								ps.setString(5, Name_of_Category2);
								ps.setString(6, Time_Scale_of_Pay2);
								ps.setInt(7, No_of_Sanctioned_Posts2);
								ps.setInt(8, No_of_Incumbents_in_Roll2);
								ps.setInt(9, No_of_Vacant_Posts2);
								ps
										.setFloat(10,
												Begining_of_the_Year_Basic_Pay2);
								ps.setFloat(11, Begining_of_the_Year_50_BP2);
								ps.setDate(12, Increment_Date2);
								ps.setFloat(13, Increment_Amount2);
								ps.setFloat(14, After_Increment_Basic_Pay2);
								ps.setFloat(15, After_Increment_50_BP2);
								ps.setFloat(16, Basic_Pay_After_Increment2);
								ps.setFloat(17, Basic_Pay_50_Dearness_pay2);
								ps.setFloat(18, DA_32_on_Col_No_132);
								ps.setFloat(19, Other_Allowances2);
								ps.setFloat(20, Total2);
								ps.setDate(21, Date_of_Retirement2);
								ps.setInt(22, division_id);
								ps.setInt(23, circle_id);
								ps.setInt(24, region_id);
								ps.setInt(25, head_office_id);
								ps.setString(26, office_level_id);
								ps.setString(27, userid);
								ps.setTimestamp(28, ts);
								int kk = ps.executeUpdate();
							}
							con.commit();
							sendMessage(
									response,
									"Records Updated Successfully ............ ",
									"ok",
									"Civil_Budget_Format_3_Consolidation.jsp");
						} else {
							sendMessage(response,
									"Records Does Not Exist ............ ",
									"ok",
									"Civil_Budget_Format_3_Consolidation.jsp");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Updation Failed ............ " + e, "ok",
							"Civil_Budget_Format_3_Consolidation.jsp");
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
					"ok", "Civil_Budget_Format_3.jsp");
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
