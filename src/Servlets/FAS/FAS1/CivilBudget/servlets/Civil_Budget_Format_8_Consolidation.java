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
 * Servlet implementation class Civil_Budget_Format_8_Consolidation
 */
public class Civil_Budget_Format_8_Consolidation extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Civil_Budget_Format_8_Consolidation() {
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
					sql = " select HEAD_OF_ACCOUNT,BUDGET_GROUP_MAJOR,BE_FOR_THE_YEAR,ANTICIPATED_DEC_TO_END_OF_CY,TOTAL,NEXT_YEAR from (select HEAD_OF_ACCOUNT,SUM(BE_FOR_THE_YEAR)         AS BE_FOR_THE_YEAR,   "
							+ " SUM(ANTICIPATED_DEC_TO_END_OF_CY)               AS ANTICIPATED_DEC_TO_END_OF_CY,"
							+ "    SUM(TOTAL) AS TOTAL,    SUM(NEXT_YEAR) AS NEXT_YEAR from FAS_BUDGET_FORMAT_8 "
							+ "where FINANCIAL_YEAR=? and CIRCLE=? group by HEAD_OF_ACCOUNT order by HEAD_OF_ACCOUNT)a LEFT OUTER JOIN   (SELECT BUDGET_GROUP_ID,BUDGET_GROUP_MAJOR FROM FAS_BUDGET_GROUP_MASTER   )b ON a.HEAD_OF_ACCOUNT =b.BUDGET_GROUP_ID ";
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
						pss.setString(4, "8");
						results2 = pss.executeQuery();
						if (results2.next()) {
							flag = 1;
						} else {
							flag1 = 1;
						}
					}
				} else if (office_level_id.equals("RN")) {
					sql = " SELECT select HEAD_OF_ACCOUNT,BUDGET_GROUP_MAJOR,BE_FOR_THE_YEAR,ANTICIPATED_DEC_TO_END_OF_CY,TOTAL,NEXT_YEAR from (select HEAD_OF_ACCOUNT, SUM(BE_FOR_THE_YEAR)         AS BE_FOR_THE_YEAR,    SUM(ANTICIPATED_DEC_TO_END_OF_CY)               AS ANTICIPATED_DEC_TO_END_OF_CY,    SUM(TOTAL) AS TOTAL,    SUM(NEXT_YEAR) AS NEXT_YEAR FROM  (SELECT HEAD_OF_ACCOUNT,    SUM(BE_FOR_THE_YEAR)         AS BE_FOR_THE_YEAR,    SUM(ANTICIPATED_DEC_TO_END_OF_CY)               AS ANTICIPATED_DEC_TO_END_OF_CY,    SUM(TOTAL) AS TOTAL,    SUM(NEXT_YEAR) AS NEXT_YEAR  FROM FAS_BUDGET_FORMAT_8  WHERE FINANCIAL_YEAR=?  AND OFFICE_LEVEL_ID ='RN'  AND REGION     =?  GROUP BY HEAD_OF_ACCOUNT  UNION ALL  SELECT HEAD_OF_ACCOUNT,    SUM(BE_FOR_THE_YEAR)         AS BE_FOR_THE_YEAR,    SUM(ANTICIPATED_DEC_TO_END_OF_CY)               AS ANTICIPATED_DEC_TO_END_OF_CY,    SUM(TOTAL) AS TOTAL,    SUM(NEXT_YEAR) AS NEXT_YEAR  FROM FAS_BUDGET_FORMAT8_CONSOLIDATE  WHERE FINANCIAL_YEAR=?  AND REGION     =?  AND OFFICE_LEVEL_ID ='CL'  GROUP BY HEAD_OF_ACCOUNT  )GROUP BY HEAD_OF_ACCOUNT ORDER BY HEAD_OF_ACCOUNT)a LEFT OUTER JOIN   (SELECT BUDGET_GROUP_ID,BUDGET_GROUP_MAJOR FROM FAS_BUDGET_GROUP_MASTER   )b ON a.HEAD_OF_ACCOUNT =b.BUDGET_GROUP_ID  ";
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
						pss.setString(4, "8");
						results2 = pss.executeQuery();
						if (results2.next()) {
							flag = 1;
						} else {
							flag1 = 1;
						}
					}
				} else if (office_level_id.equals("HO")) {
					sql = " SELECT HEAD_OF_ACCOUNT,BUDGET_GROUP_MAJOR,BE_FOR_THE_YEAR,ANTICIPATED_DEC_TO_END_OF_CY,TOTAL,NEXT_YEAR from (select HEAD_OF_ACCOUNT, SUM(BE_FOR_THE_YEAR)         AS BE_FOR_THE_YEAR,    SUM(ANTICIPATED_DEC_TO_END_OF_CY)               AS ANTICIPATED_DEC_TO_END_OF_CY,    SUM(TOTAL) AS TOTAL,    SUM(NEXT_YEAR) AS NEXT_YEAR FROM  (SELECT HEAD_OF_ACCOUNT,    SUM(BE_FOR_THE_YEAR)         AS BE_FOR_THE_YEAR,    SUM(ANTICIPATED_DEC_TO_END_OF_CY)               AS ANTICIPATED_DEC_TO_END_OF_CY,    SUM(TOTAL) AS TOTAL,    SUM(NEXT_YEAR) AS NEXT_YEAR  FROM FAS_BUDGET_FORMAT_8  WHERE FINANCIAL_YEAR=?  AND OFFICE_LEVEL_ID ='HO'  AND HEAD_OFFICE     =?  GROUP BY HEAD_OF_ACCOUNT  UNION ALL  SELECT HEAD_OF_ACCOUNT,    SUM(BE_FOR_THE_YEAR)         AS BE_FOR_THE_YEAR,    SUM(ANTICIPATED_DEC_TO_END_OF_CY)               AS ANTICIPATED_DEC_TO_END_OF_CY,    SUM(TOTAL) AS TOTAL,    SUM(NEXT_YEAR) AS NEXT_YEAR  FROM FAS_BUDGET_FORMAT8_CONSOLIDATE  WHERE FINANCIAL_YEAR=?  AND HEAD_OFFICE     =?  AND OFFICE_LEVEL_ID ='RN'  GROUP BY HEAD_OF_ACCOUNT  )GROUP BY HEAD_OF_ACCOUNT ORDER BY HEAD_OF_ACCOUNT)a LEFT OUTER JOIN   (SELECT BUDGET_GROUP_ID,BUDGET_GROUP_MAJOR FROM FAS_BUDGET_GROUP_MASTER   )b ON a.HEAD_OF_ACCOUNT =b.BUDGET_GROUP_ID ";
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
						pss.setString(4, "8");
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
					ps1.setString(4, "8");
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
					ps1.setString(4, "8");
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
					if (office_level_id.equals("CL")) {
						ps.setString(1, (year1 + "-" + year2));
						ps.setInt(2, office_id);
					} else if (office_level_id.equals("RN")) {
						ps.setString(1, (year1 + "-" + year2));
						ps.setInt(2, office_id);
						ps.setString(3, (year1 + "-" + year2));
						ps.setInt(4, office_id);
					} else if (office_level_id.equals("HO")) {
						ps.setString(1, (year1 + "-" + year2));
						ps.setInt(2, office_id);
						ps.setString(3, (year1 + "-" + year2));
						ps.setInt(4, office_id);
					}
					rs = ps.executeQuery();
					while (rs.next()) {
						xml = xml + "<Head_of_Account>"
								+ rs.getInt("HEAD_OF_ACCOUNT")
								+ "</Head_of_Account>";

						xml = xml + "<BUDGET_GROUP_MAJOR>"
								+ rs.getString("BUDGET_GROUP_MAJOR")
								+ "</BUDGET_GROUP_MAJOR>";

						xml = xml + "<BE_for_the_Year>"
								+ rs.getBigDecimal("BE_FOR_THE_YEAR")
								+ "</BE_for_the_Year>";

						xml = xml
								+ "<Anticipated_Dec_to_End_of_CY>"
								+ rs
										.getBigDecimal("ANTICIPATED_DEC_TO_END_OF_CY")
								+ "</Anticipated_Dec_to_End_of_CY>";

						xml = xml + "<Total>" + rs.getBigDecimal("TOTAL")
								+ "</Total>";

						xml = xml + "<Next_Year>"
								+ rs.getBigDecimal("NEXT_YEAR")
								+ "</Next_Year>";
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
		String budger_g_id[] = new String[RecordCount];
		String BE_for_the_Year[] = new String[RecordCount];
		String Anticipated_Dec_to_End_of_CY[] = new String[RecordCount];
		String Total[] = new String[RecordCount];
		String Next_Year[] = new String[RecordCount];

		/* Variables Declaration */
		int budger_g_id2 = 0;
		double BE_for_the_Year2 = 0.0d;
		double Anticipated_Dec_to_End_of_CY2 = 0.0d;
		double Total2 = 0.0d;
		double Next_Year2 = 0.0d;
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
						.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR from FAS_BUDGET_FORMAT8_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
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
							budger_g_id[k] = request.getParameter("budger_g_id"
									+ k);
							if (budger_g_id[k] != null) {
								if (budger_g_id[k].equals("")) {
									budger_g_id2 = 0;
								} else {
									budger_g_id2 = Integer
											.parseInt(budger_g_id[k]);
								}
							} else {
								budger_g_id2 = 0;
							}
						} catch (Exception e) {
							System.out
									.println("Error for getting budger_g_id -->"
											+ e);
						}

						/* BE_for_the_Year */
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

						/* Anticipated_Dec_to_End_of_CY */
						try {
							Anticipated_Dec_to_End_of_CY[k] = request
									.getParameter("Anticipated_Dec_to_End_of_CY"
											+ k);
							if (Anticipated_Dec_to_End_of_CY[k] != null) {
								if (Anticipated_Dec_to_End_of_CY[k].equals("")) {
									Anticipated_Dec_to_End_of_CY2 = 0.0d;
								} else {
									Anticipated_Dec_to_End_of_CY2 = Double
											.parseDouble(Anticipated_Dec_to_End_of_CY[k]);
								}
							} else {
								Anticipated_Dec_to_End_of_CY2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						/* Actuals for the Period Apr to Nov */
						try {
							Total[k] = request.getParameter("Total" + k);
							if (Total[k] != null) {
								if (Total[k].equals("")) {
									Total2 = 0.0d;
								} else {
									Total2 = Double.parseDouble(Total[k]);
								}
							} else {
								Total2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						/* Anticipated for the Period Dec to Mar */
						try {
							Next_Year[k] = request
									.getParameter("Next_Year" + k);
							if (Next_Year[k] != null) {
								if (Next_Year[k].equals("")) {
									Next_Year2 = 0.0d;
								} else {
									Next_Year2 = Double
											.parseDouble(Next_Year[k]);
								}
							} else {
								Next_Year2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						int i = 1, i1 = 0;
						try {
							ps = con
									.prepareStatement("Select max(SL_NO) from FAS_BUDGET_FORMAT8_CONSOLIDATE");
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
								.prepareStatement("insert into FAS_BUDGET_FORMAT8_CONSOLIDATE (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,HEAD_OF_ACCOUNT,BE_FOR_THE_YEAR,ANTICIPATED_DEC_TO_END_OF_CY,TOTAL,NEXT_YEAR,DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, FinancialYear);
						ps.setInt(4, i);
						ps.setInt(5, budger_g_id2);
						ps.setDouble(6, BE_for_the_Year2);
						ps.setDouble(7, Anticipated_Dec_to_End_of_CY2);
						ps.setDouble(8, Total2);
						ps.setDouble(9, Next_Year2);
						ps.setInt(10, division_id);
						ps.setInt(11, circle_id);
						ps.setInt(12, region_id);
						ps.setInt(13, head_office_id);
						ps.setString(14, office_level_id);
						ps.setString(15, userid);
						ps.setTimestamp(16, ts);

						int kk = ps.executeUpdate();
					}
					flag = 2;
				}
				con.commit();
				if (flag == 1) {
					sendMessage(
							response,
							"Records Alredy Exist for given Office for given Financial Year ............ ",
							"ok", "Civil_Budget_Format_8_Consolidation.jsp");
				} else if (flag == 2) {
					sendMessage(response,
							"Records Saved Successfully ............ ", "ok",
							"Civil_Budget_Format_8_Consolidation.jsp");
				}
			} else if (filter.equals("view")) {
				try {
					xml = "<response><command>LoadData</command>";
					ps1 = con
							.prepareStatement("select UPDATED_DATE from FAS_BUDGET_CLOSURE_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and FORMAT_NAME=?");
					ps1.setInt(1, cmbAcc_UnitCode);
					ps1.setInt(2, cmbOffice_code);
					ps1.setString(3, FinancialYear);
					ps1.setString(4, "8");
					rs = ps1.executeQuery();
					if (rs.next()) {
						xml = xml + "<flag>Exist</flag>";
					} else {
						ps = con
								.prepareStatement(" select SL_NO,HEAD_OF_ACCOUNT,  BE_FOR_THE_YEAR,  ANTICIPATED_DEC_TO_END_OF_CY,  TOTAL,  NEXT_YEAR,BUDGET_GROUP_MAJOR from (SELECT SL_NO,HEAD_OF_ACCOUNT,  BE_FOR_THE_YEAR,  ANTICIPATED_DEC_TO_END_OF_CY,  TOTAL,  NEXT_YEAR FROM FAS_BUDGET_FORMAT8_CONSOLIDATE WHERE ACCOUNTING_UNIT_ID    =? AND ACCOUNTING_FOR_OFFICE_ID=? AND FINANCIAL_YEAR          =?)a left outer join (select BUDGET_GROUP_ID,BUDGET_GROUP_MAJOR from FAS_BUDGET_GROUP_MASTER)b on a.HEAD_OF_ACCOUNT =b.BUDGET_GROUP_ID order by SL_NO");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, FinancialYear);
						rs = ps.executeQuery();
						while (rs.next()) {
							xml = xml + "<Head_of_Account>"
									+ rs.getInt("HEAD_OF_ACCOUNT")
									+ "</Head_of_Account>";

							xml = xml + "<BUDGET_GROUP_MAJOR>"
									+ rs.getString("BUDGET_GROUP_MAJOR")
									+ "</BUDGET_GROUP_MAJOR>";

							xml = xml + "<BE_for_the_Year>"
									+ rs.getBigDecimal("BE_FOR_THE_YEAR")
									+ "</BE_for_the_Year>";

							xml = xml
									+ "<Anticipated_Dec_to_End_of_CY>"
									+ rs
											.getBigDecimal("ANTICIPATED_DEC_TO_END_OF_CY")
									+ "</Anticipated_Dec_to_End_of_CY>";

							xml = xml + "<Total>" + rs.getBigDecimal("TOTAL")
									+ "</Total>";

							xml = xml + "<Next_Year>"
									+ rs.getBigDecimal("NEXT_YEAR")
									+ "</Next_Year>";

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
							.prepareStatement(" select * from FAS_BUDGET_FORMAT8_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setString(3, FinancialYear);
					rs = ps.executeQuery();
					if (rs.next()) {
						ps1 = con
								.prepareStatement(" delete from FAS_BUDGET_FORMAT8_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
						ps1.setInt(1, cmbAcc_UnitCode);
						ps1.setInt(2, cmbOffice_code);
						ps1.setString(3, FinancialYear);
						int sbg = ps1.executeUpdate();
						if (sbg > 0) {
							con.commit();
							sendMessage(
									response,
									"Records Deleted Successfully ............ ",
									"ok", "Civil_Budget_Format_8_Consolidation.jsp");
						} else {
							sendMessage(response,
									"Records Deletion Failed ............ ",
									"ok", "Civil_Budget_Format_8_Consolidation.jsp");
						}
					} else {
						sendMessage(response,
								"Records Does Not Exist ............ ", "ok",
								"Civil_Budget_Format_8_Consolidation.jsp");
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Deletion Failed ............ " + e, "ok",
							"Civil_Budget_Format_8_Consolidation.jsp");
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
							.prepareStatement(" select * from FAS_BUDGET_FORMAT8_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setString(3, FinancialYear);
					rs = ps.executeQuery();
					if (rs.next()) {
						ps1 = con
								.prepareStatement(" delete from FAS_BUDGET_FORMAT8_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
						ps1.setInt(1, cmbAcc_UnitCode);
						ps1.setInt(2, cmbOffice_code);
						ps1.setString(3, FinancialYear);
						ps1.executeUpdate();
						for (int k = 0; k < RecordCount; k++) {

							/* Head of Account */
							try {
								budger_g_id[k] = request
										.getParameter("budger_g_id" + k);
								if (budger_g_id[k] != null) {
									if (budger_g_id[k].equals("")) {
										budger_g_id2 = 0;
									} else {
										budger_g_id2 = Integer
												.parseInt(budger_g_id[k]);
									}
								} else {
									budger_g_id2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting budger_g_id -->"
												+ e);
							}

							/* BE_for_the_Year */
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

							/* Anticipated_Dec_to_End_of_CY */
							try {
								Anticipated_Dec_to_End_of_CY[k] = request
										.getParameter("Anticipated_Dec_to_End_of_CY"
												+ k);
								if (Anticipated_Dec_to_End_of_CY[k] != null) {
									if (Anticipated_Dec_to_End_of_CY[k]
											.equals("")) {
										Anticipated_Dec_to_End_of_CY2 = 0.0d;
									} else {
										Anticipated_Dec_to_End_of_CY2 = Double
												.parseDouble(Anticipated_Dec_to_End_of_CY[k]);
									}
								} else {
									Anticipated_Dec_to_End_of_CY2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* Actuals for the Period Apr to Nov */
							try {
								Total[k] = request.getParameter("Total" + k);
								if (Total[k] != null) {
									if (Total[k].equals("")) {
										Total2 = 0.0d;
									} else {
										Total2 = Double.parseDouble(Total[k]);
									}
								} else {
									Total2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* Anticipated for the Period Dec to Mar */
							try {
								Next_Year[k] = request.getParameter("Next_Year"
										+ k);
								if (Next_Year[k] != null) {
									if (Next_Year[k].equals("")) {
										Next_Year2 = 0.0d;
									} else {
										Next_Year2 = Double
												.parseDouble(Next_Year[k]);
									}
								} else {
									Next_Year2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							int i = 1, i1 = 0;
							try {
								ps = con
										.prepareStatement("Select max(SL_NO) from FAS_BUDGET_FORMAT8_CONSOLIDATE");
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
									.prepareStatement("insert into FAS_BUDGET_FORMAT8_CONSOLIDATE (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,HEAD_OF_ACCOUNT,BE_FOR_THE_YEAR,ANTICIPATED_DEC_TO_END_OF_CY,TOTAL,NEXT_YEAR,DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
							ps.setInt(1, cmbAcc_UnitCode);
							ps.setInt(2, cmbOffice_code);
							ps.setString(3, FinancialYear);
							ps.setInt(4, i);
							ps.setInt(5, budger_g_id2);
							ps.setDouble(6, BE_for_the_Year2);
							ps.setDouble(7, Anticipated_Dec_to_End_of_CY2);
							ps.setDouble(8, Total2);
							ps.setDouble(9, Next_Year2);
							ps.setInt(10, division_id);
							ps.setInt(11, circle_id);
							ps.setInt(12, region_id);
							ps.setInt(13, head_office_id);
							ps.setString(14, office_level_id);
							ps.setString(15, userid);
							ps.setTimestamp(16, ts);
							int kk = ps.executeUpdate();
						}
						con.commit();
						sendMessage(response,
								"Records Updated Successfully ............ ",
								"ok", "Civil_Budget_Format_8_Consolidation.jsp");
					} else {
						sendMessage(response,
								"Records Does Not Exist ............ ", "ok",
								"Civil_Budget_Format_8_Consolidation.jsp");
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Updation Failed ............ " + e, "ok",
							"Civil_Budget_Format_8.jsp");
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
					"ok", "Civil_Budget_Format_8_Consolidation.jsp");
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
