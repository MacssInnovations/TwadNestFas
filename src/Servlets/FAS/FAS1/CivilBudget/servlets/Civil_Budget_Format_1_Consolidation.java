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
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Civil_Budget_Format_1_Consolidation
 */
public class Civil_Budget_Format_1_Consolidation extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Civil_Budget_Format_1_Consolidation() {
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
		int flag1 = 0;int flag11 = 0;int flag12 = 0;
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
					sql = " select HEAD_OF_ACCOUNT,sum(ACTUALS_FOR_LAST_YEAR) as ACTUALS_FOR_LAST_YEAR,"
							+ "sum(BE_FOR_THE_YEAR) as BE_FOR_THE_YEAR,"
							+ "sum(ACTUALS_FOR_PERIOD_APR_TO_NOV) as ACTUALS_FOR_PERIOD_APR_TO_NOV,"
							+ "sum(ANTICIPATED_FR_PERIOD_DEC_MAR) as ANTICIPATED_FR_PERIOD_DEC_MAR,"
							+ "sum(RE_FOR_YEAR) as RE_FOR_YEAR,"
							+ "sum(VARIATION_BETWEN_BE_RE) as VARIATION_BETWEN_BE_RE,"
							+ "sum(BE_FOR_NEXT_YEAR) as BE_FOR_NEXT_YEAR from FAS_BUDGET_FORMAT_1 "
							+ "where FINANCIAL_YEAR=? and CIRCLE=? group by HEAD_OF_ACCOUNT order by HEAD_OF_ACCOUNT ";
//					xml = xml
//							+ "<ofiice_type>AllUnits</ofiice_type>";
					//for circle level consolidation format creation 3 cases are there (1) All Units (2) Only Maintenance (3) only Non Maintenance
					//case (1) all units
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
						pss.setString(4, "1");
						results2 = pss.executeQuery();
						if (results2.next()) {
							flag = 1;
						} else {
							flag1 = 1;
						}
					}
					//case (2) only for Maintenance Divisions
//					xml = xml
//					+ "<ofiice_type>MaintenanceDivisions</ofiice_type>";
					ps1 = connection
							.prepareStatement(" select accounting_unit_id ,accounting_unit_name,"
									+ "accounting_unit_office_id,OFFICE_NAME from (select accounting_unit_id ,"
									+ "accounting_unit_name,accounting_unit_office_id from  "
									+ "fas_mst_acct_units where accounting_unit_office_id in "
									+ "(select OFFICE_ID  from COM_MST_ALL_OFFICES_VIEW  "
									+ "where CIRCLE_OFFICE_ID=? and OFFICE_LEVEL_ID in ('DN') )  )x left outer join(select OFFICE_ID,"
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
						pss.setString(4, "1");
						results2 = pss.executeQuery();
						if (results2.next()) {
							flag = 1;
						} else {
							flag11 = 11;
						}
					}
					//case (3) only for Non Maintenance Divisions
//					xml = xml
//					+ "<ofiice_type>NonMaintenanceDivisions</ofiice_type>";
					ps1 = connection
							.prepareStatement(" select accounting_unit_id ,accounting_unit_name,"
									+ "accounting_unit_office_id,OFFICE_NAME from (select accounting_unit_id ,"
									+ "accounting_unit_name,accounting_unit_office_id from  "
									+ "fas_mst_acct_units where accounting_unit_office_id in "
									+ "(select OFFICE_ID  from COM_MST_ALL_OFFICES_VIEW  "
									+ "where CIRCLE_OFFICE_ID=? and OFFICE_LEVEL_ID not in ('DN') )  )x left outer join(select OFFICE_ID,"
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
						pss.setString(4, "1");
						results2 = pss.executeQuery();
						if (results2.next()) {
							flag = 1;
						} else {
							flag12 = 12;
						}
					}
					
				} else if (office_level_id.equals("RN")) {
					sql = " SELECT HEAD_OF_ACCOUNT,    SUM(ACTUALS_FOR_LAST_YEAR)         "
							+ "AS ACTUALS_FOR_LAST_YEAR,    SUM(BE_FOR_THE_YEAR)               "
							+ "AS BE_FOR_THE_YEAR,    SUM(ACTUALS_FOR_PERIOD_APR_TO_NOV) AS "
							+ "ACTUALS_FOR_PERIOD_APR_TO_NOV,    SUM(ANTICIPATED_FR_PERIOD_DEC_MAR) AS "
							+ "ANTICIPATED_FR_PERIOD_DEC_MAR,    SUM(RE_FOR_YEAR)                   "
							+ "AS RE_FOR_YEAR,    SUM(VARIATION_BETWEN_BE_RE)        AS VARIATION_BETWEN_BE_RE, "
							+ "   SUM(BE_FOR_NEXT_YEAR)              AS BE_FOR_NEXT_YEAR from (SELECT     "
							+ "HEAD_OF_ACCOUNT,    SUM(ACTUALS_FOR_LAST_YEAR)         AS ACTUALS_FOR_LAST_YEAR,"
							+ "    SUM(BE_FOR_THE_YEAR)               AS BE_FOR_THE_YEAR,    "
							+ "SUM(ACTUALS_FOR_PERIOD_APR_TO_NOV) AS ACTUALS_FOR_PERIOD_APR_TO_NOV,    "
							+ "SUM(ANTICIPATED_FR_PERIOD_DEC_MAR) AS ANTICIPATED_FR_PERIOD_DEC_MAR,   "
							+ " SUM(RE_FOR_YEAR)                   AS RE_FOR_YEAR,    "
							+ "SUM(VARIATION_BETWEN_BE_RE)        AS VARIATION_BETWEN_BE_RE,    "
							+ "SUM(BE_FOR_NEXT_YEAR)              AS BE_FOR_NEXT_YEAR  FROM FAS_BUDGET_FORMAT_1"
							+ "   WHERE FINANCIAL_YEAR=?  and OFFICE_LEVEL_ID='RN'  AND REGION     =?  "
							+ " GROUP BY HEAD_OF_ACCOUNT union all  SELECT   HEAD_OF_ACCOUNT,    "
							+ "SUM(ACTUALS_FOR_LAST_YEAR)         AS ACTUALS_FOR_LAST_YEAR,    "
							+ "SUM(BE_FOR_THE_YEAR)               AS BE_FOR_THE_YEAR,    "
							+ "SUM(ACTUALS_FOR_PERIOD_APR_TO_NOV) AS ACTUALS_FOR_PERIOD_APR_TO_NOV,    "
							+ "SUM(ANTICIPATED_FR_PERIOD_DEC_MAR) AS ANTICIPATED_FR_PERIOD_DEC_MAR,    "
							+ "SUM(RE_FOR_YEAR)                   AS RE_FOR_YEAR,    "
							+ "SUM(VARIATION_BETWEN_BE_RE)        AS VARIATION_BETWEN_BE_RE,    "
							+ "SUM(BE_FOR_NEXT_YEAR)              AS BE_FOR_NEXT_YEAR "
							+ " FROM FAS_BUDGET_FORMAT1_CONSOLIDATE   WHERE FINANCIAL_YEAR=?  "
							+ "AND REGION     =?  and OFFICE_LEVEL_ID='CL'  GROUP BY  HEAD_OF_ACCOUNT )  "
							+ "GROUP BY  HEAD_OF_ACCOUNT ORDER BY HEAD_OF_ACCOUNT  ";
					xml = xml
							+ "<ofiice_type>Circles</ofiice_type>";
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
						pss.setString(4, "1");
						results2 = pss.executeQuery();
						if (results2.next()) {
							flag = 1;
						} else {
							flag1 = 1;
						}
					}
				} else if (office_level_id.equals("HO")) {
					sql = " SELECT     HEAD_OF_ACCOUNT,    SUM(ACTUALS_FOR_LAST_YEAR)         "
							+ "AS ACTUALS_FOR_LAST_YEAR,    SUM(BE_FOR_THE_YEAR)               "
							+ "AS BE_FOR_THE_YEAR,    SUM(ACTUALS_FOR_PERIOD_APR_TO_NOV) AS "
							+ "ACTUALS_FOR_PERIOD_APR_TO_NOV,    SUM(ANTICIPATED_FR_PERIOD_DEC_MAR) AS "
							+ "ANTICIPATED_FR_PERIOD_DEC_MAR,    SUM(RE_FOR_YEAR)                  "
							+ " AS RE_FOR_YEAR,    SUM(VARIATION_BETWEN_BE_RE)        AS VARIATION_BETWEN_BE_RE, "
							+ "   SUM(BE_FOR_NEXT_YEAR)              AS BE_FOR_NEXT_YEAR from    ( SELECT     "
							+ "HEAD_OF_ACCOUNT,    SUM(ACTUALS_FOR_LAST_YEAR)         AS ACTUALS_FOR_LAST_YEAR, "
							+ "   SUM(BE_FOR_THE_YEAR)               AS BE_FOR_THE_YEAR,    "
							+ "SUM(ACTUALS_FOR_PERIOD_APR_TO_NOV) AS ACTUALS_FOR_PERIOD_APR_TO_NOV,   "
							+ " SUM(ANTICIPATED_FR_PERIOD_DEC_MAR) AS ANTICIPATED_FR_PERIOD_DEC_MAR,    "
							+ "SUM(RE_FOR_YEAR)                   AS RE_FOR_YEAR,    SUM(VARIATION_BETWEN_BE_RE)"
							+ "        AS VARIATION_BETWEN_BE_RE,    SUM(BE_FOR_NEXT_YEAR)             "
							+ " AS BE_FOR_NEXT_YEAR  FROM FAS_BUDGET_FORMAT_1  WHERE FINANCIAL_YEAR=? "
							+ " and OFFICE_LEVEL_ID='HO'  AND HEAD_OFFICE     =?  GROUP BY HEAD_OF_ACCOUNT "
							+ "union all SELECT   HEAD_OF_ACCOUNT,    SUM(ACTUALS_FOR_LAST_YEAR)         "
							+ "AS ACTUALS_FOR_LAST_YEAR,    SUM(BE_FOR_THE_YEAR)               "
							+ "AS BE_FOR_THE_YEAR,    SUM(ACTUALS_FOR_PERIOD_APR_TO_NOV) AS "
							+ "ACTUALS_FOR_PERIOD_APR_TO_NOV,    SUM(ANTICIPATED_FR_PERIOD_DEC_MAR) AS "
							+ "ANTICIPATED_FR_PERIOD_DEC_MAR,    SUM(RE_FOR_YEAR)                   "
							+ "AS RE_FOR_YEAR,    SUM(VARIATION_BETWEN_BE_RE)        AS VARIATION_BETWEN_BE_RE,"
							+ "    SUM(BE_FOR_NEXT_YEAR)              AS BE_FOR_NEXT_YEAR  FROM "
							+ "FAS_BUDGET_FORMAT1_CONSOLIDATE  WHERE FINANCIAL_YEAR=?  AND HEAD_OFFICE     =?  "
							+ "and OFFICE_LEVEL_ID='RN'  GROUP BY  HEAD_OF_ACCOUNT )    GROUP BY  "
							+ "HEAD_OF_ACCOUNT ORDER BY HEAD_OF_ACCOUNT ";
					xml = xml
							+ "<ofiice_type>Regions</ofiice_type>";
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
						pss.setString(4, "1");
						results2 = pss.executeQuery();
						if (results2.next()) {
							flag = 1;
						} else {
							flag1 = 1;
						}
					}
				}
				System.out.println("Flag1 valueeeeeeeeeeee::::::: before login unit******"+flag1);
				try {
					ps1 = connection
							.prepareStatement("select UPDATED_DATE from FAS_BUDGET_CLOSURE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and FORMAT_NAME=?");
					ps1.setInt(1, cmbAcc_UnitCode);
					ps1.setInt(2, cmbOffice_code);
					ps1.setString(3, (year1) + "-" + (year2));
					ps1.setString(4, "1");
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
					ps1.setString(4, "1");
					results = ps1.executeQuery();
					if (results.next()) {
						flag1 = 3;
					} else {
						flag = 1;
					}
				} catch (Exception e) {
					e.printStackTrace();
				}
				
				if (office_level_id.equals("CL")) 
				{
					System.out.println("Flag1 valueeeeeeeeeeee:::::::"+flag1+"flag11**********"+flag11+"flag12********"+flag12);
					if ( (flag1 == 1) ) 
					{
						xml = xml
						+ "<ofiice_type>AllUnits</ofiice_type>";
						xml = xml + "<flag>Freeze_PendingAllUnits</flag>";
					}
					else if ( (flag11 == 11) ) 
					{
						xml = xml
						+ "<ofiice_type>MaintenanceDivisions</ofiice_type>";
						xml = xml + "<flag>Freeze_PendingMD</flag>";
					}
					else if ( (flag12 == 12) ) 
					{
						xml = xml
						+ "<ofiice_type>NonMaintenanceDivisions</ofiice_type>";
						xml = xml + "<flag>Freeze_PendingNonMD</flag>";
					}
				}
				else if ( (office_level_id.equals("RN")) || (office_level_id.equals("HO")) )
				{
						if  (flag1 == 1) {
							xml = xml + "<flag>Freeze_Pending</flag>";
						}
				}else if (flag1 == 2) {
					xml = xml + "<flag>Freeze_Pending1</flag>";
				} else if (flag1 == 3) {
					xml = xml + "<flag>Freeze_Done</flag>";
				} else{
					ps = connection.prepareStatement(sql);
					if (office_level_id.equals("CL")) {					
					ps.setString(1, (year1 + "-" + year2));
					ps.setInt(2, office_id);					
					}else if (office_level_id.equals("RN")) {						
						ps.setString(1, (year1 + "-" + year2));
						ps.setInt(2, office_id);
						ps.setString(3, (year1 + "-" + year2));
						ps.setInt(4, office_id);
					}else if (office_level_id.equals("HO")) {						
						ps.setString(1, (year1 + "-" + year2));
						ps.setInt(2, office_id);
						ps.setString(3, (year1 + "-" + year2));
						ps.setInt(4, office_id);
					}
					rs = ps.executeQuery();
					while (rs.next()) {
						xml = xml + "<Head_of_Account>"
								+ rs.getString("HEAD_OF_ACCOUNT")
								+ "</Head_of_Account>";

						xml = xml + "<Actuals_for_Last_Year>"
								+ rs.getBigDecimal("ACTUALS_FOR_LAST_YEAR")
								+ "</Actuals_for_Last_Year>";

						xml = xml + "<BE_for_the_Year>"
								+ rs.getBigDecimal("BE_FOR_THE_YEAR")
								+ "</BE_for_the_Year>";

						xml = xml
								+ "<Actuals_for_Period_Apr_to_Nov>"
								+ rs
										.getBigDecimal("ACTUALS_FOR_PERIOD_APR_TO_NOV")
								+ "</Actuals_for_Period_Apr_to_Nov>";

						xml = xml
								+ "<Anticipated_for_Period_Dec_to_Mar>"
								+ rs
										.getBigDecimal("ANTICIPATED_FR_PERIOD_DEC_MAR")
								+ "</Anticipated_for_Period_Dec_to_Mar>";

						xml = xml + "<RE_for_Year>"
								+ rs.getBigDecimal("RE_FOR_YEAR")
								+ "</RE_for_Year>";

						xml = xml + "<Variation_betwen_BE_and_RE>"
								+ rs.getBigDecimal("VARIATION_BETWEN_BE_RE")
								+ "</Variation_betwen_BE_and_RE>";

						xml = xml + "<BE_for_Next_Year>"
								+ rs.getBigDecimal("BE_FOR_NEXT_YEAR")
								+ "</BE_for_Next_Year>";
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
		String Head_of_Account[] = new String[RecordCount];
		String Actuals_for_Last_Year[] = new String[RecordCount];
		String BE_for_the_Year[] = new String[RecordCount];
		String Actuals_for_Period_Apr_to_Nov[] = new String[RecordCount];
		String Anticipated_for_Period_Dec_to_Mar[] = new String[RecordCount];
		String RE_for_Year[] = new String[RecordCount];
		String Variation_betwen_BE_and_RE[] = new String[RecordCount];
		String Reason_for_Variation[] = new String[RecordCount];
		String BE_for_Next_Year[] = new String[RecordCount];
		String Variation_btwn_REyr_and_NXTyr[] = new String[RecordCount];

		/* Variables Declaration */
		String Head_of_Account2 = null;
		double Actuals_for_Last_Year2 = 0.0d;
		double BE_for_the_Year2 = 0.0d;
		double Actuals_for_Period_Apr_to_Nov2 = 0.0d;
		double Anticipated_for_Period_Dec_to_Mar2 = 0.0d;
		double RE_for_Year2 = 0.0d;
		double Variation_betwen_BE_and_RE2 = 0.0d;
		String Reason_for_Variation2 = null;
		double BE_for_Next_Year2 = 0.0d;
		String Variation_btwn_REyr_and_NXTyr2 = null;
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
						.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR from FAS_BUDGET_FORMAT1_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setString(3, FinancialYear);
				rs = ps.executeQuery();
				if (rs.next()) {
					flag = 1;
				} else {
					for (int k = 0; k < RecordCount; k++) {

						/* Head of Account */
						try {
							Head_of_Account2 = request
									.getParameter("Head_of_Account" + k);
						} catch (Exception e) {
							System.out
									.println("Error for getting Head_of_Account -->"
											+ e);
						}

						/* Actuals for the Last Year */
						try {
							Actuals_for_Last_Year[k] = request
									.getParameter("Actuals_for_Last_Year" + k);
							if (Actuals_for_Last_Year[k] != null) {
								if (Actuals_for_Last_Year[k].equals("")) {
									Actuals_for_Last_Year2 = 0.0d;
								} else {
									Actuals_for_Last_Year2 = Double
											.parseDouble(Actuals_for_Last_Year[k]);
								}
							} else {
								Actuals_for_Last_Year2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						/* BE for the Year */
						try {
							BE_for_the_Year[k] = request
									.getParameter("BE_for_the_Year" + k);
							if (BE_for_the_Year[k] != null) {
								if (BE_for_the_Year[k].equals("")) {
									BE_for_the_Year2 = 0.0d;
								} else {
									BE_for_the_Year2 = Double
											.parseDouble(BE_for_the_Year[k]);
								}
							} else {
								BE_for_the_Year2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						/* Actuals for the Period Apr to Nov */
						try {
							Actuals_for_Period_Apr_to_Nov[k] = request
									.getParameter("Actuals_for_Period_Apr_to_Nov"
											+ k);
							if (Actuals_for_Period_Apr_to_Nov[k] != null) {
								if (Actuals_for_Period_Apr_to_Nov[k].equals("")) {
									Actuals_for_Period_Apr_to_Nov2 = 0.0d;
								} else {
									Actuals_for_Period_Apr_to_Nov2 = Double
											.parseDouble(Actuals_for_Period_Apr_to_Nov[k]);
								}
							} else {
								Actuals_for_Period_Apr_to_Nov2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						/* Anticipated for the Period Dec to Mar */
						try {
							Anticipated_for_Period_Dec_to_Mar[k] = request
									.getParameter("Anticipated_for_Period_Dec_to_Mar"
											+ k);
							if (Anticipated_for_Period_Dec_to_Mar[k] != null) {
								if (Anticipated_for_Period_Dec_to_Mar[k]
										.equals("")) {
									Anticipated_for_Period_Dec_to_Mar2 = 0.0d;
								} else {
									Anticipated_for_Period_Dec_to_Mar2 = Double
											.parseDouble(Anticipated_for_Period_Dec_to_Mar[k]);
								}
							} else {
								Anticipated_for_Period_Dec_to_Mar2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						/* RE for the Year */
						try {
							RE_for_Year[k] = request.getParameter("RE_for_Year"
									+ k);
							if (RE_for_Year[k] != null) {
								if (RE_for_Year[k].equals("")) {
									RE_for_Year2 = 0.0d;
								} else {
									RE_for_Year2 = Double
											.parseDouble(RE_for_Year[k]);
								}
							} else {
								RE_for_Year2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						/* Variation betwen BE and RE */
						try {
							Variation_betwen_BE_and_RE[k] = request
									.getParameter("Variation_betwen_BE_and_RE"
											+ k);
							if (Variation_betwen_BE_and_RE[k] != null) {
								if (Variation_betwen_BE_and_RE[k].equals("")) {
									Variation_betwen_BE_and_RE2 = 0.0d;
								} else {
									Variation_betwen_BE_and_RE2 = Double
											.parseDouble(Variation_betwen_BE_and_RE[k]);
								}
							} else {
								Variation_betwen_BE_and_RE2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						/* Reason for Variation */
						try {
							Reason_for_Variation2 = request
									.getParameter("Reason_for_Variation" + k);
						} catch (Exception e) {
							System.out
									.println("Error for getting Reason_for_Variation -->"
											+ e);
						}

						/* BE for Next Year */
						try {
							BE_for_Next_Year[k] = request
									.getParameter("BE_for_Next_Year" + k);
							if (BE_for_Next_Year[k] != null) {
								if (BE_for_Next_Year[k].equals("")) {
									BE_for_Next_Year2 = 0.0d;
								} else {
									BE_for_Next_Year2 = Double
											.parseDouble(BE_for_Next_Year[k]);
								}
							} else {
								BE_for_Next_Year2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						/*
						 * Reason for Variation if any between RE for the Year
						 * and the next Year
						 */
						try {
							Variation_btwn_REyr_and_NXTyr2 = request
									.getParameter("Variation_btwn_REyr_and_NXTyr"
											+ k);
						} catch (Exception e) {
							System.out
									.println("Error for getting Variation_btwn_REyr_and_NXTyr -->"
											+ e);
						}
						int i = 1, i1 = 0;
						try {
							ps = con
									.prepareStatement("Select max(SL_NO) from FAS_BUDGET_FORMAT1_CONSOLIDATE");
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
								.prepareStatement("insert into FAS_BUDGET_FORMAT1_CONSOLIDATE (HEAD_OF_ACCOUNT,ACTUALS_FOR_LAST_YEAR,BE_FOR_THE_YEAR,ACTUALS_FOR_PERIOD_APR_TO_NOV,ANTICIPATED_FR_PERIOD_DEC_MAR,RE_FOR_YEAR,VARIATION_BETWEN_BE_RE,REASON_FOR_VARIATION,BE_FOR_NEXT_YEAR,VARIATION_BTWN_REYR_AND_NXTYR,ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						ps.setString(1, Head_of_Account2);
						ps.setDouble(2, Actuals_for_Last_Year2);
						ps.setDouble(3, BE_for_the_Year2);
						ps.setDouble(4, Actuals_for_Period_Apr_to_Nov2);
						ps.setDouble(5, Anticipated_for_Period_Dec_to_Mar2);
						ps.setDouble(6, RE_for_Year2);
						ps.setDouble(7, Variation_betwen_BE_and_RE2);
						ps.setString(8, Reason_for_Variation2);
						ps.setDouble(9, BE_for_Next_Year2);
						ps.setString(10, Variation_btwn_REyr_and_NXTyr2);
						ps.setInt(11, cmbAcc_UnitCode);
						ps.setInt(12, cmbOffice_code);
						ps.setString(13, FinancialYear);
						ps.setInt(14, i);
						ps.setInt(15, division_id);
						ps.setInt(16, circle_id);
						ps.setInt(17, region_id);
						ps.setInt(18, head_office_id);
						ps.setString(19, office_level_id);
						ps.setString(20, userid);
						ps.setTimestamp(21, ts);

						int kk = ps.executeUpdate();
					}
					flag = 2;
				}
				con.commit();
				if (flag == 1) {
					sendMessage(
							response,
							"Records Alredy Exist for given Office for given Financial Year ............ ",
							"ok", "Civil_Budget_Format_1_Consolidation.jsp");
				} else if (flag == 2) {
					sendMessage(response,
							"Records Saved Successfully ............ ", "ok",
							"Civil_Budget_Format_1_Consolidation.jsp");
				}
			} else if (filter.equals("view")) {
				try {
					xml = "<response><command>LoadData</command>";					
						ps1 = con
								.prepareStatement("select UPDATED_DATE from FAS_BUDGET_CLOSURE_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and FORMAT_NAME=?");
						ps1.setInt(1, cmbAcc_UnitCode);
						ps1.setInt(2, cmbOffice_code);
						ps1.setString(3, FinancialYear);
						ps1.setString(4, "1");
						rs = ps1.executeQuery();
						if (rs.next()) {
							xml = xml + "<flag>Freeze_Done</flag>";
						} 
						else{
					ps = con
							.prepareStatement(" select * from FAS_BUDGET_FORMAT1_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setString(3, FinancialYear);
					rs = ps.executeQuery();
					while (rs.next()) {
						xml = xml + "<Head_of_Account>"
								+ rs.getString("HEAD_OF_ACCOUNT")
								+ "</Head_of_Account>";

						xml = xml + "<Actuals_for_Last_Year>"
								+ rs.getBigDecimal("ACTUALS_FOR_LAST_YEAR")
								+ "</Actuals_for_Last_Year>";

						xml = xml + "<BE_for_the_Year>"
								+ rs.getBigDecimal("BE_FOR_THE_YEAR")
								+ "</BE_for_the_Year>";

						xml = xml
								+ "<Actuals_for_Period_Apr_to_Nov>"
								+ rs
										.getBigDecimal("ACTUALS_FOR_PERIOD_APR_TO_NOV")
								+ "</Actuals_for_Period_Apr_to_Nov>";

						xml = xml
								+ "<Anticipated_for_Period_Dec_to_Mar>"
								+ rs
										.getBigDecimal("ANTICIPATED_FR_PERIOD_DEC_MAR")
								+ "</Anticipated_for_Period_Dec_to_Mar>";

						xml = xml + "<RE_for_Year>"
								+ rs.getBigDecimal("RE_FOR_YEAR")
								+ "</RE_for_Year>";

						xml = xml + "<Variation_betwen_BE_and_RE>"
								+ rs.getBigDecimal("VARIATION_BETWEN_BE_RE")
								+ "</Variation_betwen_BE_and_RE>";

						xml = xml + "<Reason_for_Variation>"
								+ rs.getString("REASON_FOR_VARIATION")
								+ "</Reason_for_Variation>";

						xml = xml + "<BE_for_Next_Year>"
								+ rs.getBigDecimal("BE_FOR_NEXT_YEAR")
								+ "</BE_for_Next_Year>";

						xml = xml + "<Variation_btwn_REyr_and_NXTyr>"
								+ rs.getString("VARIATION_BTWN_REYR_AND_NXTYR")
								+ "</Variation_btwn_REyr_and_NXTyr>";

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
							.prepareStatement(" select * from FAS_BUDGET_FORMAT1_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setString(3, FinancialYear);
					rs = ps.executeQuery();
					if (rs.next()) {
						ps1 = con
								.prepareStatement(" delete from FAS_BUDGET_FORMAT1_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
						ps1.setInt(1, cmbAcc_UnitCode);
						ps1.setInt(2, cmbOffice_code);
						ps1.setString(3, FinancialYear);
						int sbg = ps1.executeUpdate();
						if (sbg > 0) {
							con.commit();
							sendMessage(
									response,
									"Records Deleted Successfully ............ ",
									"ok",
									"Civil_Budget_Format_1_Consolidation.jsp");
						} else {
							sendMessage(response,
									"Records Deletion Failed ............ ",
									"ok",
									"Civil_Budget_Format_1_Consolidation.jsp");
						}
					} else {
						sendMessage(response,
								"Records Does Not Exist ............ ", "ok",
								"Civil_Budget_Format_1_Consolidation.jsp");
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Deletion Failed ............ " + e, "ok",
							"Civil_Budget_Format_1_Consolidation.jsp");
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
							.prepareStatement(" select * from FAS_BUDGET_FORMAT1_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setString(3, FinancialYear);
					rs = ps.executeQuery();
					if (rs.next()) {
						ps1 = con
								.prepareStatement(" delete from FAS_BUDGET_FORMAT1_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
						ps1.setInt(1, cmbAcc_UnitCode);
						ps1.setInt(2, cmbOffice_code);
						ps1.setString(3, FinancialYear);
						ps1.executeUpdate();
						for (int k = 0; k < RecordCount; k++) {

							/* Head of Account */
							try {
								Head_of_Account2 = request
										.getParameter("Head_of_Account" + k);
							} catch (Exception e) {
								System.out
										.println("Error for getting Head_of_Account -->"
												+ e);
							}

							/* Actuals for the Last Year */
							try {
								Actuals_for_Last_Year[k] = request
										.getParameter("Actuals_for_Last_Year"
												+ k);
								if (Actuals_for_Last_Year[k] != null) {
									if (Actuals_for_Last_Year[k].equals("")) {
										Actuals_for_Last_Year2 = 0.0d;
									} else {
										Actuals_for_Last_Year2 = Double
												.parseDouble(Actuals_for_Last_Year[k]);
									}
								} else {
									Actuals_for_Last_Year2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* BE for the Year */
							try {
								BE_for_the_Year[k] = request
										.getParameter("BE_for_the_Year" + k);
								if (BE_for_the_Year[k] != null) {
									if (BE_for_the_Year[k].equals("")) {
										BE_for_the_Year2 = 0.0d;
									} else {
										BE_for_the_Year2 = Double
												.parseDouble(BE_for_the_Year[k]);
									}
								} else {
									BE_for_the_Year2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* Actuals for the Period Apr to Nov */
							try {
								Actuals_for_Period_Apr_to_Nov[k] = request
										.getParameter("Actuals_for_Period_Apr_to_Nov"
												+ k);
								if (Actuals_for_Period_Apr_to_Nov[k] != null) {
									if (Actuals_for_Period_Apr_to_Nov[k]
											.equals("")) {
										Actuals_for_Period_Apr_to_Nov2 = 0.0d;
									} else {
										Actuals_for_Period_Apr_to_Nov2 = Double
												.parseDouble(Actuals_for_Period_Apr_to_Nov[k]);
									}
								} else {
									Actuals_for_Period_Apr_to_Nov2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* Anticipated for the Period Dec to Mar */
							try {
								Anticipated_for_Period_Dec_to_Mar[k] = request
										.getParameter("Anticipated_for_Period_Dec_to_Mar"
												+ k);
								if (Anticipated_for_Period_Dec_to_Mar[k] != null) {
									if (Anticipated_for_Period_Dec_to_Mar[k]
											.equals("")) {
										Anticipated_for_Period_Dec_to_Mar2 = 0.0d;
									} else {
										Anticipated_for_Period_Dec_to_Mar2 = Double
												.parseDouble(Anticipated_for_Period_Dec_to_Mar[k]);
									}
								} else {
									Anticipated_for_Period_Dec_to_Mar2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* RE for the Year */
							try {
								RE_for_Year[k] = request
										.getParameter("RE_for_Year" + k);
								if (RE_for_Year[k] != null) {
									if (RE_for_Year[k].equals("")) {
										RE_for_Year2 = 0.0d;
									} else {
										RE_for_Year2 = Double
												.parseDouble(RE_for_Year[k]);
									}
								} else {
									RE_for_Year2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* Variation betwen BE and RE */
							try {
								Variation_betwen_BE_and_RE[k] = request
										.getParameter("Variation_betwen_BE_and_RE"
												+ k);
								if (Variation_betwen_BE_and_RE[k] != null) {
									if (Variation_betwen_BE_and_RE[k]
											.equals("")) {
										Variation_betwen_BE_and_RE2 = 0.0d;
									} else {
										Variation_betwen_BE_and_RE2 = Double
												.parseDouble(Variation_betwen_BE_and_RE[k]);
									}
								} else {
									Variation_betwen_BE_and_RE2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* Reason for Variation */
							try {
								Reason_for_Variation2 = request
										.getParameter("Reason_for_Variation"
												+ k);
							} catch (Exception e) {
								System.out
										.println("Error for getting Reason_for_Variation -->"
												+ e);
							}

							/* BE for Next Year */
							try {
								BE_for_Next_Year[k] = request
										.getParameter("BE_for_Next_Year" + k);
								if (BE_for_Next_Year[k] != null) {
									if (BE_for_Next_Year[k].equals("")) {
										BE_for_Next_Year2 = 0.0d;
									} else {
										BE_for_Next_Year2 = Double
												.parseDouble(BE_for_Next_Year[k]);
									}
								} else {
									BE_for_Next_Year2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/*
							 * Reason for Variation if any between RE for the
							 * Year and the next Year
							 */
							try {
								Variation_btwn_REyr_and_NXTyr2 = request
										.getParameter("Variation_btwn_REyr_and_NXTyr"
												+ k);
							} catch (Exception e) {
								System.out
										.println("Error for getting Variation_btwn_REyr_and_NXTyr -->"
												+ e);
							}
							int i = 1, i1 = 0;
							try {
								ps = con
										.prepareStatement("Select max(SL_NO) from FAS_BUDGET_FORMAT1_CONSOLIDATE");
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
									.prepareStatement("insert into FAS_BUDGET_FORMAT1_CONSOLIDATE (HEAD_OF_ACCOUNT,ACTUALS_FOR_LAST_YEAR,BE_FOR_THE_YEAR,ACTUALS_FOR_PERIOD_APR_TO_NOV,ANTICIPATED_FR_PERIOD_DEC_MAR,RE_FOR_YEAR,VARIATION_BETWEN_BE_RE,REASON_FOR_VARIATION,BE_FOR_NEXT_YEAR,VARIATION_BTWN_REYR_AND_NXTYR,ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
							ps.setString(1, Head_of_Account2);
							ps.setDouble(2, Actuals_for_Last_Year2);
							ps.setDouble(3, BE_for_the_Year2);
							ps.setDouble(4, Actuals_for_Period_Apr_to_Nov2);
							ps.setDouble(5, Anticipated_for_Period_Dec_to_Mar2);
							ps.setDouble(6, RE_for_Year2);
							ps.setDouble(7, Variation_betwen_BE_and_RE2);
							ps.setString(8, Reason_for_Variation2);
							ps.setDouble(9, BE_for_Next_Year2);
							ps.setString(10, Variation_btwn_REyr_and_NXTyr2);
							ps.setInt(11, cmbAcc_UnitCode);
							ps.setInt(12, cmbOffice_code);
							ps.setString(13, FinancialYear);
							ps.setInt(14, i);
							ps.setInt(15, division_id);
							ps.setInt(16, circle_id);
							ps.setInt(17, region_id);
							ps.setInt(18, head_office_id);
							ps.setString(19, office_level_id);
							ps.setString(20, userid);
							ps.setTimestamp(21, ts);
							int kk = ps.executeUpdate();
						}
						con.commit();
						sendMessage(response,
								"Records Updated Successfully ............ ",
								"ok", "Civil_Budget_Format_1_Consolidation.jsp");
					} else {
						sendMessage(response,
								"Records Does Not Exist ............ ", "ok",
								"Civil_Budget_Format_1_Consolidation.jsp");
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Updation Failed ............ " + e, "ok",
							"Civil_Budget_Format_1_Consolidation.jsp");
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
					"ok", "Civil_Budget_Format_1_Consolidation.jsp");
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
