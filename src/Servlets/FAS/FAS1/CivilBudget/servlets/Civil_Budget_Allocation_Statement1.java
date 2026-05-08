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
 * Servlet implementation class Civil_Budget_Allocation_Statement1
 */
public class Civil_Budget_Allocation_Statement1 extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Civil_Budget_Allocation_Statement1() {
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
			String financialYear = request.getParameter("financialYear");
			String cmbFormat_Type = request.getParameter("cmbFormat_Type");
			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbAcc_UnitCode1 = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode1"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));

			String sql = "";
			String sql1 = "";
			String sql2 = "";
			int flagg = 0;
			try {
				ps1 = connection
						.prepareStatement("select UPDATED_DATE from FAS_BUDGET_CLOSURE_ALLOCATION where ACCOUNTING_UNIT_ID=? and FINANCIAL_YEAR=? and FORMAT_NAME=?");
				ps1.setInt(1, cmbAcc_UnitCode);
				ps1.setString(2, financialYear);
				ps1.setString(3, cmbFormat_Type);
				results = ps1.executeQuery();
				if (results.next()) {
					flag1 = 3;
				} else {
					flag = 1;
				}
			} catch (Exception e) {
				e.printStackTrace();
			}
			if (flag1 == 3) {
				try {
					ps = connection
							.prepareStatement("select OFFICE_ID   from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
					ps.setInt(1, empid);
					results = ps.executeQuery();
					if (results.next()) {

						office_id = results.getInt("OFFICE_ID");
					}

					xml = xml + "<office_id>" + office_id + "</office_id>";

					xml = xml + "<flag>Freeze_Done</flag>";
				} catch (Exception e) {
					e.printStackTrace();
				}

			} else {
				try {
					ps = connection
							.prepareStatement("select OFFICE_ID   from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
					ps.setInt(1, empid);
					results = ps.executeQuery();
					if (results.next()) {

						office_id = results.getInt("OFFICE_ID");
					}

					xml = xml + "<office_id>" + office_id + "</office_id>";

					ps2 = connection
							.prepareStatement("select OFFICE_LEVEL_ID  from COM_MST_OFFICES  where OFFICE_ID=?");
					ps2.setInt(1, office_id);
					rs2 = ps2.executeQuery();
					if (rs2.next()) {
						office_level_id = rs2.getString("OFFICE_LEVEL_ID");
					}

					if (office_level_id.equals("RN")) {
						if (cmbFormat_Type.equals("1")) {
							sql = " SELECT HEAD_OF_ACCOUNT, BE_FOR_NEXT_YEAR FROM FAS_BUDGET_FORMAT_1 WHERE "
									+ "ACCOUNTING_UNIT_ID=? AND FINANCIAL_YEAR=? AND OFFICE_LEVEL_ID in ('CL','RN','DN') ";
						} else if (cmbFormat_Type.equals("2")) {
							sql = " SELECT HEAD_OF_ACCOUNT, BE_FOR_NEXT_YEAR FROM FAS_BUDGET_FORMAT_2 WHERE "
									+ "ACCOUNTING_UNIT_ID=? AND FINANCIAL_YEAR=? AND OFFICE_LEVEL_ID in ('CL','RN','DN') ";
						} else if (cmbFormat_Type.equals("9")) {
							sql = " SELECT HEAD_OF_ACCOUNT, BE_FOR_NEXT_YEAR FROM FAS_BUDGET_FORMAT_9 WHERE "
									+ "ACCOUNTING_UNIT_ID=? AND FINANCIAL_YEAR=? AND OFFICE_LEVEL_ID in ('CL','RN','DN') ";
						} else if (cmbFormat_Type.equals("10")) {
							sql = " SELECT HEAD_OF_ACCOUNT, BE_FOR_NEXT_YEAR FROM FAS_BUDGET_FORMAT_10 WHERE "
									+ "ACCOUNTING_UNIT_ID=? AND FINANCIAL_YEAR=? AND OFFICE_LEVEL_ID in ('CL','RN','DN') ";
						}
						sql1 = "select ALLOTED_AMOUNT from BUDGET_ALLOCATION_1_2_9_10 where AMT_ALTD_ACCOUNTING_UNIT_ID=? and FINANCIAL_YEAR=?";
						sql2 = "SELECT sum(ALLOTED_AMOUNT) as amt,'T_Altd_Amt_By_HO' as indicator "
								+ "FROM BUDGET_ALLOCATION_1_2_9_10 WHERE AMT_ALTD_ACCOUNTING_UNIT_ID=? AND "
								+ "FINANCIAL_YEAR=?  GROUP BY 'T_Altd_Amt_By_HO' union SELECT sum(ALLOTED_AMOUNT)"
								+ " as amt,'T_Altd_Amt_By_RN' as indicator FROM BUDGET_ALLOCATION_1_2_9_10 WHERE"
								+ " REGION=? AND FINANCIAL_YEAR=?  GROUP BY 'T_Altd_Amt_By_RN'";

					} else if (office_level_id.equals("HO")) {
						if (cmbAcc_UnitCode == 3) {
							if (cmbFormat_Type.equals("1")) {
								sql = " SELECT HEAD_OF_ACCOUNT, BE_FOR_NEXT_YEAR FROM FAS_BUDGET_FORMAT_1 WHERE "
										+ "ACCOUNTING_UNIT_ID=? AND FINANCIAL_YEAR=? AND OFFICE_LEVEL_ID in ('HO') ";
							} else if (cmbFormat_Type.equals("2")) {
								sql = " SELECT HEAD_OF_ACCOUNT, BE_FOR_NEXT_YEAR FROM FAS_BUDGET_FORMAT_2 WHERE "
										+ "ACCOUNTING_UNIT_ID=? AND FINANCIAL_YEAR=? AND OFFICE_LEVEL_ID in ('HO) ";
							} else if (cmbFormat_Type.equals("9")) {
								sql = " SELECT HEAD_OF_ACCOUNT, BE_FOR_NEXT_YEAR FROM FAS_BUDGET_FORMAT_9 WHERE "
										+ "ACCOUNTING_UNIT_ID=? AND FINANCIAL_YEAR=? AND OFFICE_LEVEL_ID in ('HO) ";
							} else if (cmbFormat_Type.equals("10")) {
								sql = " SELECT HEAD_OF_ACCOUNT, BE_FOR_NEXT_YEAR FROM FAS_BUDGET_FORMAT_10 WHERE "
										+ "ACCOUNTING_UNIT_ID=? AND FINANCIAL_YEAR=? AND OFFICE_LEVEL_ID in ('HO) ";
							}
						} else {
							if (cmbFormat_Type.equals("1")) {
								sql = " SELECT HEAD_OF_ACCOUNT, BE_FOR_NEXT_YEAR FROM FAS_BUDGET_FORMAT1_CONSOLIDATE WHERE "
										+ "ACCOUNTING_UNIT_ID=? AND FINANCIAL_YEAR=? AND OFFICE_LEVEL_ID in ('RN') ";
							} else if (cmbFormat_Type.equals("2")) {
								sql = " SELECT HEAD_OF_ACCOUNT, BE_FOR_NEXT_YEAR FROM FAS_BUDGET_FORMAT2_CONSOLIDATE WHERE "
										+ "ACCOUNTING_UNIT_ID=? AND FINANCIAL_YEAR=? AND OFFICE_LEVEL_ID in ('RN') ";
							} else if (cmbFormat_Type.equals("9")) {
								sql = " SELECT HEAD_OF_ACCOUNT, BE_FOR_NEXT_YEAR FROM FAS_BUDGET_FORMAT2_CONSOLIDATE WHERE "
										+ "ACCOUNTING_UNIT_ID=? AND FINANCIAL_YEAR=? AND OFFICE_LEVEL_ID in ('RN') ";
							} else if (cmbFormat_Type.equals("10")) {
								sql = " SELECT HEAD_OF_ACCOUNT, BE_FOR_NEXT_YEAR FROM FAS_BUDGET_FMT10_CONSOLIDATE WHERE "
										+ "ACCOUNTING_UNIT_ID=? AND FINANCIAL_YEAR=? AND OFFICE_LEVEL_ID in ('RN') ";
							}
						}
					}

					ps = connection.prepareStatement(sql);
					if (office_level_id.equals("RN")) {
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setString(2, financialYear);
					} else if (office_level_id.equals("HO")) {
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setString(2, financialYear);
					}
					rs = ps.executeQuery();
					while (rs.next()) {
						xml = xml + "<Head_of_Account>"
								+ rs.getString("HEAD_OF_ACCOUNT")
								+ "</Head_of_Account>";

						xml = xml + "<BE_for_Next_Year>"
								+ rs.getBigDecimal("BE_FOR_NEXT_YEAR")
								+ "</BE_for_Next_Year>";
						flagg++;
					}
					xml = xml + "<flag>success</flag>";
					if (flagg > 0) {
						if (office_level_id.equals("RN")) {
							ps1 = connection.prepareStatement(sql1);
							ps1.setInt(1, cmbAcc_UnitCode1);
							ps1.setString(2, financialYear);

							rs2 = ps1.executeQuery();
							while (rs2.next()) {
								xml = xml + "<Amount_Alloted>"
										+ rs2.getBigDecimal("ALLOTED_AMOUNT")
										+ "</Amount_Alloted>";
							}

							pss = connection.prepareStatement(sql2);
							pss.setInt(1, cmbAcc_UnitCode1);
							pss.setString(2, financialYear);
							pss.setInt(3, cmbOffice_code);
							pss.setString(4, financialYear);
							results2 = pss.executeQuery();
							while (results2.next()) {
								xml = xml + "<Amount>"
										+ results2.getBigDecimal("amt")
										+ "</Amount>";

								xml = xml + "<Indicator>"
										+ results2.getString("indicator")
										+ "</Indicator>";
							}
						}
						xml = xml + "<flagg>success</flagg>";
					} else {
						xml = xml + "<flagg>failure</flagg>";
					}
				} catch (Exception e) {
					e.printStackTrace();
					xml = xml + "<flag>failure</flag>";
				}
			}
		} else if (strCommand.equalsIgnoreCase("LoadAccountingUnitID")) {
			xml = "<response><command>LoadAccountingUnitIDD</command>";
			try {
				ps = connection
						.prepareStatement("select OFFICE_ID   from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
				ps.setInt(1, empid);
				results = ps.executeQuery();
				if (results.next()) {

					office_id = results.getInt("OFFICE_ID");
				}

				ps2 = connection
						.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME  from FAS_MST_ACCT_UNITS  where ACCOUNTING_UNIT_OFFICE_ID=?");
				ps2.setInt(1, office_id);
				rs2 = ps2.executeQuery();
				if (rs2.next()) {
					xml = xml + "<Unit_id>" + rs2.getInt("ACCOUNTING_UNIT_ID")
							+ "</Unit_id>";
					xml = xml + "<Unit_name>"
							+ rs2.getString("ACCOUNTING_UNIT_NAME")
							+ "</Unit_name>";
				}

				pss = connection
						.prepareStatement("select OFFICE_ID,OFFICE_NAME  from COM_MST_OFFICES  where OFFICE_ID=?");
				pss.setInt(1, office_id);
				results = pss.executeQuery();
				if (results.next()) {
					xml = xml + "<Office_id>" + results.getInt("OFFICE_ID")
							+ "</Office_id>";
					xml = xml + "<Office_name>"
							+ results.getString("OFFICE_NAME")
							+ "</Office_name>";
				}

			} catch (Exception e) {
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("LoadAccountingUnitID1")) {
			xml = "<response><command>LoadAccountingUnitID</command>";
			try {
				/** Get Employee Office ID */
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

				if (office_level_id.equals("RN")) {
					ps1 = connection
							.prepareStatement(" SELECT accounting_unit_id ,  accounting_unit_name,  "
									+ "accounting_unit_office_id,  OFFICE_NAME FROM  ( SELECT accounting_unit_id ,"
									+ "    accounting_unit_name,    accounting_unit_office_id  FROM "
									+ "fas_mst_acct_units  WHERE accounting_unit_office_id IN    "
									+ "(SELECT CIRCLE_OFFICE_ID    FROM COM_MST_ALL_OFFICES_VIEW    "
									+ "WHERE REGION_OFFICE_ID=?    )    union     SELECT accounting_unit_id ,    "
									+ "accounting_unit_name,    accounting_unit_office_id   FROM "
									+ "fas_mst_acct_units   WHERE accounting_unit_office_id IN     "
									+ "(SELECT DIVISION_OFFICE_ID     FROM COM_MST_ALL_OFFICES_VIEW     "
									+ "WHERE REGION_OFFICE_ID=?     )   )x LEFT OUTER JOIN  (SELECT OFFICE_ID, "
									+ "OFFICE_NAME FROM COM_MST_OFFICES  )y ON "
									+ "x.accounting_unit_office_id =y.OFFICE_ID  ");
					ps1.setInt(1, office_id);
					ps1.setInt(2, office_id);
					rs = ps1.executeQuery();
					while (rs.next()) {
						xml = xml + "<Acc_UnitID>"
								+ rs.getInt("accounting_unit_id")
								+ "</Acc_UnitID>";

						xml = xml + "<Acc_UnitName>"
								+ rs.getString("accounting_unit_name")
								+ "</Acc_UnitName>";

						xml = xml + "<Acc_OfficeID>"
								+ rs.getInt("accounting_unit_office_id")
								+ "</Acc_OfficeID>";

						xml = xml + "<Acc_OfficeName>"
								+ rs.getString("OFFICE_NAME")
								+ "</Acc_OfficeName>";
					}
				} else if (office_level_id.equals("HO")) {
					ps1 = connection
							.prepareStatement(" SELECT accounting_unit_id ,  accounting_unit_name,  "
									+ "accounting_unit_office_id,  OFFICE_NAME FROM  (SELECT accounting_unit_id , "
									+ "   accounting_unit_name,    accounting_unit_office_id  FROM "
									+ "fas_mst_acct_units  WHERE accounting_unit_office_id IN    (     (    "
									+ "SELECT to_number(REGION_OFFICE_ID,9999) as id1 FROM "
									+ "COM_MST_ALL_OFFICES_VIEW     union    SELECT to_number(OFFICE_ID,9999)"
									+ "  as id1 FROM COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID='HO'     )  "
									+ "  )  )x LEFT OUTER JOIN   (SELECT OFFICE_ID, OFFICE_NAME FROM "
									+ "COM_MST_OFFICES  )y ON x.accounting_unit_office_id =y.OFFICE_ID  ");
					rs = ps1.executeQuery();
					while (rs.next()) {
						xml = xml + "<Acc_UnitID>"
								+ rs.getInt("accounting_unit_id")
								+ "</Acc_UnitID>";

						xml = xml + "<Acc_UnitName>"
								+ rs.getString("accounting_unit_name")
								+ "</Acc_UnitName>";

						xml = xml + "<Acc_OfficeID>"
								+ rs.getInt("accounting_unit_office_id")
								+ "</Acc_OfficeID>";

						xml = xml + "<Acc_OfficeName>"
								+ rs.getString("OFFICE_NAME")
								+ "</Acc_OfficeName>";
					}
				}
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
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
		PreparedStatement pss = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		ResultSet rs2 = null;
		ResultSet results2 = null;
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
		int cmbAcc_UnitCode1 = 0;
		int cmbOffice_code = 0;
		String FinancialYear = null;
		String cmbFormat_Type = null;
		int flagg = 0;
		/* Get Accounting Unit ID */
		try {
			cmbAcc_UnitCode1 = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
		} catch (Exception e) {
			System.out.println("Error Not Getting Accounitng Unit ID --> " + e);
		}

		/* Get Budget Alloted to Accounting Unit ID */
		try {
			cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode1"));
		} catch (Exception e) {
			System.out
					.println("Error Not Getting Accounitng Unit ID1 --> " + e);
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

		/* Get cmbFormat_Type */
		try {
			cmbFormat_Type = request.getParameter("cmbFormat_Type");
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
		String BE_for_Next_Year[] = new String[RecordCount];
		String Amount_to_be_Alloted[] = new String[RecordCount];
		String Reason_for_Variation[] = new String[RecordCount];

		/* Variables Declaration */
		String Head_of_Account2 = null;
		double BE_for_Next_Year2 = 0.0d;
		double Amount_to_be_Alloted2 = 0.0d;
		String Reason_for_Variation2 = null;
		int flag = 0;
		String office_level_id = null;
		int division_id = 0;
		int circle_id = 0;
		int region_id = 0;
		int head_office_id = 0;
		String sql1 = "";
		String sql2 = "";
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
						.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR from BUDGET_ALLOCATION_1_2_9_10 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and AMT_ALTD_ACCOUNTING_UNIT_ID=? and FORMAT_TYPE=?");
				ps.setInt(1, cmbAcc_UnitCode1);
				ps.setInt(2, cmbOffice_code);
				ps.setString(3, FinancialYear);
				ps.setInt(4, cmbAcc_UnitCode);
				ps.setString(5, cmbFormat_Type);
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

						/* BE for Next Year */
						try {
							Amount_to_be_Alloted[k] = request
									.getParameter("Amount_to_be_Alloted" + k);
							if (Amount_to_be_Alloted[k] != null) {
								if (Amount_to_be_Alloted[k].equals("")) {
									Amount_to_be_Alloted2 = 0.0d;
								} else {
									Amount_to_be_Alloted2 = Double
											.parseDouble(Amount_to_be_Alloted[k]);
								}
							} else {
								Amount_to_be_Alloted2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						/*
						 * Reason for Variation if any between RE for the Year
						 * and the next Year
						 */
						try {
							Reason_for_Variation2 = request
									.getParameter("Reason_for_Variation" + k);
						} catch (Exception e) {
							System.out
									.println("Error for getting Reason_for_Variation -->"
											+ e);
						}
						int i = 1, i1 = 0;
						try {
							ps = con
									.prepareStatement("Select max(SL_NO) from BUDGET_ALLOCATION_1_2_9_10");
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
								.prepareStatement("insert into BUDGET_ALLOCATION_1_2_9_10 (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,AMT_ALTD_ACCOUNTING_UNIT_ID,HEAD_OF_ACCOUNT,PROPOSED_AMOUNT,ALLOTED_AMOUNT,RSN_FOR_VARIATION,DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,UPDATED_BY_USERID,UPDATED_DATE,FORMAT_TYPE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

						ps.setInt(1, cmbAcc_UnitCode1);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, FinancialYear);
						ps.setInt(4, i);
						ps.setInt(5, cmbAcc_UnitCode);
						ps.setString(6, Head_of_Account2);
						ps.setDouble(7, BE_for_Next_Year2);
						ps.setDouble(8, Amount_to_be_Alloted2);
						ps.setString(9, Reason_for_Variation2);
						ps.setInt(10, division_id);
						ps.setInt(11, circle_id);
						ps.setInt(12, region_id);
						ps.setInt(13, head_office_id);
						ps.setString(14, office_level_id);
						ps.setString(15, userid);
						ps.setTimestamp(16, ts);
						ps.setString(17, cmbFormat_Type);

						int kk = ps.executeUpdate();
					}
					flag = 2;
				}
				con.commit();
				if (flag == 1) {
					sendMessage(
							response,
							"Records Alredy Exist for given Office for given Financial Year ............ ",
							"ok", "Civil_Budget_Allocation_Statement1.jsp");
				} else if (flag == 2) {
					sendMessage(response,
							"Records Saved Successfully ............ ", "ok",
							"Civil_Budget_Allocation_Statement1.jsp");
				}
			} else if (filter.equals("view")) {
				try {
					xml = "<response><command>LoadData</command>";
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
					xml = xml + "<office_id>" + cmbOffice_code + "</office_id>";
					ps1 = con
							.prepareStatement("select UPDATED_DATE from FAS_BUDGET_CLOSURE_ALLOCATION where ACCOUNTING_UNIT_ID=? and FINANCIAL_YEAR=? and FORMAT_NAME=?");
					ps1.setInt(1, cmbAcc_UnitCode);
					ps1.setString(2, FinancialYear);
					ps1.setString(3, cmbFormat_Type);
					rs = ps1.executeQuery();
					if (rs.next()) {
						xml = xml + "<flag>Freeze_Done</flag>";
					} else {
						ps = con
								.prepareStatement(" select HEAD_OF_ACCOUNT,PROPOSED_AMOUNT,ALLOTED_AMOUNT,RSN_FOR_VARIATION from BUDGET_ALLOCATION_1_2_9_10 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and AMT_ALTD_ACCOUNTING_UNIT_ID=? and FORMAT_TYPE=?");
						ps.setInt(1, cmbAcc_UnitCode1);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, FinancialYear);
						ps.setInt(4, cmbAcc_UnitCode);
						ps.setString(5, cmbFormat_Type);
						rs = ps.executeQuery();
						while (rs.next()) {
							xml = xml + "<Head_of_Account>"
									+ rs.getString("HEAD_OF_ACCOUNT")
									+ "</Head_of_Account>";

							xml = xml + "<BE_for_Next_Year>"
									+ rs.getBigDecimal("PROPOSED_AMOUNT")
									+ "</BE_for_Next_Year>";

							xml = xml + "<Amount_to_be_Alloted>"
									+ rs.getBigDecimal("ALLOTED_AMOUNT")
									+ "</Amount_to_be_Alloted>";

							xml = xml + "<Reason_for_Variation>"
									+ rs.getString("RSN_FOR_VARIATION")
									+ "</Reason_for_Variation>";
							flagg++;
						}
						xml = xml + "<flag>success</flag>";

						sql1 = "select ALLOTED_AMOUNT from BUDGET_ALLOCATION_1_2_9_10 where AMT_ALTD_ACCOUNTING_UNIT_ID=? and FINANCIAL_YEAR=?";
						sql2 = "SELECT sum(ALLOTED_AMOUNT) as amt,'T_Altd_Amt_By_HO' as indicator "
								+ "FROM BUDGET_ALLOCATION_1_2_9_10 WHERE AMT_ALTD_ACCOUNTING_UNIT_ID=? AND "
								+ "FINANCIAL_YEAR=?  GROUP BY 'T_Altd_Amt_By_HO' union SELECT sum(ALLOTED_AMOUNT)"
								+ " as amt,'T_Altd_Amt_By_RN' as indicator FROM BUDGET_ALLOCATION_1_2_9_10 WHERE"
								+ " REGION=? AND FINANCIAL_YEAR=?  GROUP BY 'T_Altd_Amt_By_RN'";
						if (flagg > 0) {
							if (office_level_id.equals("RN")) {
								ps1 = con.prepareStatement(sql1);
								ps1.setInt(1, cmbAcc_UnitCode1);
								ps1.setString(2, FinancialYear);

								rs2 = ps1.executeQuery();
								while (rs2.next()) {
									xml = xml
											+ "<Amount_Alloted>"
											+ rs2
													.getBigDecimal("ALLOTED_AMOUNT")
											+ "</Amount_Alloted>";
								}

								pss = con.prepareStatement(sql2);
								pss.setInt(1, cmbAcc_UnitCode1);
								pss.setString(2, FinancialYear);
								pss.setInt(3, cmbOffice_code);
								pss.setString(4, FinancialYear);
								results2 = pss.executeQuery();
								while (results2.next()) {
									xml = xml + "<Amount>"
											+ results2.getBigDecimal("amt")
											+ "</Amount>";

									xml = xml + "<Indicator>"
											+ results2.getString("indicator")
											+ "</Indicator>";
								}
							}
							xml = xml + "<flagg>success</flagg>";
						} else {
							xml = xml + "<flagg>failure</flagg>";
						}
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
							.prepareStatement(" select * from BUDGET_ALLOCATION_1_2_9_10 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and AMT_ALTD_ACCOUNTING_UNIT_ID=? and FORMAT_TYPE=?");
					ps.setInt(1, cmbAcc_UnitCode1);
					ps.setInt(2, cmbOffice_code);
					ps.setString(3, FinancialYear);
					ps.setInt(4, cmbAcc_UnitCode);
					ps.setString(5, cmbFormat_Type);
					rs = ps.executeQuery();
					if (rs.next()) {
						ps1 = con
								.prepareStatement(" delete from BUDGET_ALLOCATION_1_2_9_10 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?  and AMT_ALTD_ACCOUNTING_UNIT_ID=? and FORMAT_TYPE=?");
						ps1.setInt(1, cmbAcc_UnitCode1);
						ps1.setInt(2, cmbOffice_code);
						ps1.setString(3, FinancialYear);
						ps1.setInt(4, cmbAcc_UnitCode);
						ps1.setString(5, cmbFormat_Type);
						int sbg = ps1.executeUpdate();
						if (sbg > 0) {
							con.commit();
							sendMessage(
									response,
									"Records Deleted Successfully ............ ",
									"ok",
									"Civil_Budget_Allocation_Statement1.jsp");
						} else {
							sendMessage(response,
									"Records Deletion Failed ............ ",
									"ok",
									"Civil_Budget_Allocation_Statement1.jsp");
						}
					} else {
						sendMessage(response,
								"Records Does Not Exist ............ ", "ok",
								"Civil_Budget_Allocation_Statement1.jsp");
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Deletion Failed ............ " + e, "ok",
							"Civil_Budget_Allocation_Statement1.jsp");
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
							.prepareStatement(" select * from BUDGET_ALLOCATION_1_2_9_10 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and AMT_ALTD_ACCOUNTING_UNIT_ID=?");
					ps.setInt(1, cmbAcc_UnitCode1);
					ps.setInt(2, cmbOffice_code);
					ps.setString(3, FinancialYear);
					ps.setInt(4, cmbAcc_UnitCode);
					rs = ps.executeQuery();
					if (rs.next()) {
						ps1 = con
								.prepareStatement(" delete from BUDGET_ALLOCATION_1_2_9_10 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and AMT_ALTD_ACCOUNTING_UNIT_ID=?");
						ps1.setInt(1, cmbAcc_UnitCode1);
						ps1.setInt(2, cmbOffice_code);
						ps1.setString(3, FinancialYear);
						ps1.setInt(4, cmbAcc_UnitCode);
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

							/* BE for Next Year */
							try {
								Amount_to_be_Alloted[k] = request
										.getParameter("Amount_to_be_Alloted"
												+ k);
								if (Amount_to_be_Alloted[k] != null) {
									if (Amount_to_be_Alloted[k].equals("")) {
										Amount_to_be_Alloted2 = 0.0d;
									} else {
										Amount_to_be_Alloted2 = Double
												.parseDouble(Amount_to_be_Alloted[k]);
									}
								} else {
									Amount_to_be_Alloted2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/*
							 * Reason for Variation if any between RE for the
							 * Year and the next Year
							 */
							try {
								Reason_for_Variation2 = request
										.getParameter("Reason_for_Variation"
												+ k);
							} catch (Exception e) {
								System.out
										.println("Error for getting Reason_for_Variation -->"
												+ e);
							}
							int i = 1, i1 = 0;
							try {
								ps = con
										.prepareStatement("Select max(SL_NO) from BUDGET_ALLOCATION_1_2_9_10");
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
									.prepareStatement("insert into BUDGET_ALLOCATION_1_2_9_10 (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,AMT_ALTD_ACCOUNTING_UNIT_ID,HEAD_OF_ACCOUNT,PROPOSED_AMOUNT,ALLOTED_AMOUNT,RSN_FOR_VARIATION,DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,UPDATED_BY_USERID,UPDATED_DATE,FORMAT_TYPE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

							ps.setInt(1, cmbAcc_UnitCode1);
							ps.setInt(2, cmbOffice_code);
							ps.setString(3, FinancialYear);
							ps.setInt(4, i);
							ps.setInt(5, cmbAcc_UnitCode);
							ps.setString(6, Head_of_Account2);
							ps.setDouble(7, BE_for_Next_Year2);
							ps.setDouble(8, Amount_to_be_Alloted2);
							ps.setString(9, Reason_for_Variation2);
							ps.setInt(10, division_id);
							ps.setInt(11, circle_id);
							ps.setInt(12, region_id);
							ps.setInt(13, head_office_id);
							ps.setString(14, office_level_id);
							ps.setString(15, userid);
							ps.setTimestamp(16, ts);
							ps.setString(17, cmbFormat_Type);
							int kk = ps.executeUpdate();
						}
						con.commit();
						sendMessage(response,
								"Records Updated Successfully ............ ",
								"ok", "Civil_Budget_Allocation_Statement1.jsp");
					} else {
						sendMessage(response,
								"Records Does Not Exist ............ ", "ok",
								"Civil_Budget_Allocation_Statement1.jsp");
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Updation Failed ............ " + e, "ok",
							"Civil_Budget_Allocation_Statement1.jsp");
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
					"ok", "Civil_Budget_Allocation_Statement1.jsp");
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
