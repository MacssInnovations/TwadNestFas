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
 * Servlet implementation class Civil_Budget_Format_6_Consolidation
 */
public class Civil_Budget_Format_6_Consolidation extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Civil_Budget_Format_6_Consolidation() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
					sql = " SELECT SL_NO,CATEGORY,NO_OF_PENSIONERS,TOTAL_BASIC_PENSION_UPTO_11Y,TOT_BASIC_PNSN_ANTCPTD_12Y_3Y1,TOTAL_D_A_UPTO_11Y,TOTAL_D_A_ANTICIPATED_12Y_3Y1,TOTAL_OTHER_PAYMENT_UPTO_11Y,TOT_OTHR_PYMNT_ANTCPTD_12Y_3Y1,TOTAL_UPTO_11Y,TOTAL_ANTICIPATED_12Y_3Y1,NO_OF_PENSIONERS1,TOTAL_BASIC_PENSION,TOTAL_D_A,TOTAL_OTHER_PAYMENT,TOTAL FROM FAS_BUDGET_FORMAT_6 WHERE FINANCIAL_YEAR=? and"
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
						pss.setString(4, "6");
						results2 = pss.executeQuery();
						if (results2.next()) {
							flag = 1;
						} else {
							flag1 = 1;
						}
					}
				} else if (office_level_id.equals("RN")) {
					sql = " SELECT SL_NO,CATEGORY,NO_OF_PENSIONERS,TOTAL_BASIC_PENSION_UPTO_11Y,TOT_BASIC_PNSN_ANTCPTD_12Y_3Y1,TOTAL_D_A_UPTO_11Y,TOTAL_D_A_ANTICIPATED_12Y_3Y1,TOTAL_OTHER_PAYMENT_UPTO_11Y,TOT_OTHR_PYMNT_ANTCPTD_12Y_3Y1,TOTAL_UPTO_11Y,TOTAL_ANTICIPATED_12Y_3Y1,NO_OF_PENSIONERS1,TOTAL_BASIC_PENSION,TOTAL_D_A,TOTAL_OTHER_PAYMENT,TOTAL FROM FAS_BUDGET_FORMAT_6 WHERE FINANCIAL_YEAR=? and "
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
						pss.setString(4, "6");
						results2 = pss.executeQuery();
						if (results2.next()) {
							flag = 1;
						} else {
							flag1 = 1;
						}
					}
				} else if (office_level_id.equals("HO")) {
					sql = " SELECT SL_NO,CATEGORY,NO_OF_PENSIONERS,TOTAL_BASIC_PENSION_UPTO_11Y,TOT_BASIC_PNSN_ANTCPTD_12Y_3Y1,TOTAL_D_A_UPTO_11Y,TOTAL_D_A_ANTICIPATED_12Y_3Y1,TOTAL_OTHER_PAYMENT_UPTO_11Y,TOT_OTHR_PYMNT_ANTCPTD_12Y_3Y1,TOTAL_UPTO_11Y,TOTAL_ANTICIPATED_12Y_3Y1,NO_OF_PENSIONERS1,TOTAL_BASIC_PENSION,TOTAL_D_A,TOTAL_OTHER_PAYMENT,TOTAL FROM FAS_BUDGET_FORMAT_6 WHERE FINANCIAL_YEAR=? and "
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
						pss.setString(4, "6");
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
					ps1.setString(4, "6");
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
					ps1.setString(4, "6");
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
						xml = xml + "<slno>" + rs.getInt("SL_NO")
						+ "</slno>";

				xml = xml + "<Category>" + rs.getString("CATEGORY")
						+ "</Category>";

				xml = xml + "<No_of_Pensioners>"
						+ rs.getInt("NO_OF_PENSIONERS")
						+ "</No_of_Pensioners>";

				xml = xml
						+ "<Total_Basic_Pension_Upto_11Y>"
						+ rs
								.getBigDecimal("TOTAL_BASIC_PENSION_UPTO_11Y")
						+ "</Total_Basic_Pension_Upto_11Y>";

				xml = xml
						+ "<Total_Basic_Pension_Anticipated_12Y_3Y1>"
						+ rs
								.getBigDecimal("TOT_BASIC_PNSN_ANTCPTD_12Y_3Y1")
						+ "</Total_Basic_Pension_Anticipated_12Y_3Y1>";

				xml = xml + "<Total_D_A_Upto_11Y>"
						+ rs.getBigDecimal("TOTAL_D_A_UPTO_11Y")
						+ "</Total_D_A_Upto_11Y>";

				xml = xml
						+ "<Total_D_A_Anticipated_12Y_3Y1>"
						+ rs
								.getBigDecimal("TOTAL_D_A_ANTICIPATED_12Y_3Y1")
						+ "</Total_D_A_Anticipated_12Y_3Y1>";

				xml = xml
						+ "<Total_Other_Payment_Upto_11Y>"
						+ rs
								.getBigDecimal("TOTAL_OTHER_PAYMENT_UPTO_11Y")
						+ "</Total_Other_Payment_Upto_11Y>";

				xml = xml
						+ "<Total_Other_Payment_Anticipated_12Y_3Y1>"
						+ rs
								.getBigDecimal("TOT_OTHR_PYMNT_ANTCPTD_12Y_3Y1")
						+ "</Total_Other_Payment_Anticipated_12Y_3Y1>";

				xml = xml + "<Total_Upto_11Y>"
						+ rs.getBigDecimal("TOTAL_UPTO_11Y")
						+ "</Total_Upto_11Y>";

				xml = xml + "<Total_Anticipated_12Y_3Y1>"
						+ rs.getBigDecimal("TOTAL_ANTICIPATED_12Y_3Y1")
						+ "</Total_Anticipated_12Y_3Y1>";

				xml = xml + "<No_of_Pensioners1>"
						+ rs.getInt("NO_OF_PENSIONERS1")
						+ "</No_of_Pensioners1>";

				xml = xml + "<Total_Basic_Pension>"
						+ rs.getBigDecimal("TOTAL_BASIC_PENSION")
						+ "</Total_Basic_Pension>";

				xml = xml + "<Total_D_A>"
						+ rs.getBigDecimal("TOTAL_D_A") + "</Total_D_A>";

				xml = xml + "<Total_Other_Payment>"
						+ rs.getBigDecimal("TOTAL_OTHER_PAYMENT")
						+ "</Total_Other_Payment>";

				xml = xml + "<Total>" + rs.getBigDecimal("TOTAL")
						+ "</Total>";
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
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

		String Category[] = new String[RecordCount];
		String No_of_Pensioners[] = new String[RecordCount];
		String Total_Basic_Pension_Upto_11Y[] = new String[RecordCount];
		String Total_Basic_Pension_Anticipated_12Y_3Y1[] = new String[RecordCount];
		String Total_D_A_Upto_11Y[] = new String[RecordCount];
		String Total_D_A_Anticipated_12Y_3Y1[] = new String[RecordCount];
		String Total_Other_Payment_Upto_11Y[] = new String[RecordCount];
		String Total_Other_Payment_Anticipated_12Y_3Y1[] = new String[RecordCount];
		String Total_Upto_11Y[] = new String[RecordCount];
		String Total_Anticipated_12Y_3Y1[] = new String[RecordCount];
		String No_of_Pensioners1[] = new String[RecordCount];
		String Total_Basic_Pension[] = new String[RecordCount];
		String Total_D_A[] = new String[RecordCount];
		String Total_Other_Payment[] = new String[RecordCount];
		String Total[] = new String[RecordCount];

		/* Variables Declaration */
		int check2 = 0;
		int slno2 = 0;

		String Category2 = null;
		int No_of_Pensioners2 = 0;
		float Total_Basic_Pension_Upto_11Y2 = 0.0f;
		float Total_Basic_Pension_Anticipated_12Y_3Y12 = 0.0f;
		float Total_D_A_Upto_11Y2 = 0.0f;
		float Total_D_A_Anticipated_12Y_3Y12 = 0.0f;
		float Total_Other_Payment_Upto_11Y2 = 0.0f;
		float Total_Other_Payment_Anticipated_12Y_3Y12 = 0.0f;
		float Total_Upto_11Y2 = 0.0f;
		float Total_Anticipated_12Y_3Y12 = 0.0f;
		int No_of_Pensioners12 = 0;
		float Total_Basic_Pension2 = 0.0f;
		float Total_D_A2 = 0.0f;
		float Total_Other_Payment2 = 0.0f;
		float Total2 = 0.0f;
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
				ps1.setString(4, "6");
				rs2 = ps1.executeQuery();
				if (rs2.next()) {
					sendMessage(
							response,
							"Civil Budget Format-6 have Consolidation Already Freezed ............ ",
							"ok", "Civil_Budget_Format_6_Consolidation.jsp");
				} else {
					ps = con
							.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR from FAS_BUDGET_FORMAT6_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
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
								Category2 = request
										.getParameter("Category" + k);
							} catch (Exception e) {
								System.out
										.println("Error for getting Category -->"
												+ e);
							}

							try {
								No_of_Pensioners[k] = request
										.getParameter("No_of_Pensioners" + k);
								if (No_of_Pensioners[k] != null) {
									if (No_of_Pensioners[k].equals("")) {
										No_of_Pensioners2 = 0;
									} else {
										No_of_Pensioners2 = Integer
												.parseInt(No_of_Pensioners[k]);
									}
								} else {
									No_of_Pensioners2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting No_of_Pensioners -->"
												+ e);
							}

							try {
								Total_Basic_Pension_Upto_11Y[k] = request
										.getParameter("Total_Basic_Pension_Upto_11Y"
												+ k);
								if (Total_Basic_Pension_Upto_11Y[k] != null) {
									if (Total_Basic_Pension_Upto_11Y[k]
											.equals("")) {
										Total_Basic_Pension_Upto_11Y2 = 0.0f;
									} else {
										Total_Basic_Pension_Upto_11Y2 = Float
												.parseFloat(Total_Basic_Pension_Upto_11Y[k]);
									}
								} else {
									Total_Basic_Pension_Upto_11Y2 = 0.0f;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting Total_Basic_Pension_Upto_11Y -->"
												+ e);
							}

							try {
								Total_Basic_Pension_Anticipated_12Y_3Y1[k] = request
										.getParameter("Total_Basic_Pension_Anticipated_12Y_3Y1"
												+ k);
								if (Total_Basic_Pension_Anticipated_12Y_3Y1[k] != null) {
									if (Total_Basic_Pension_Anticipated_12Y_3Y1[k]
											.equals("")) {
										Total_Basic_Pension_Anticipated_12Y_3Y12 = 0.0f;
									} else {
										Total_Basic_Pension_Anticipated_12Y_3Y12 = Float
												.parseFloat(Total_Basic_Pension_Anticipated_12Y_3Y1[k]);
									}
								} else {
									Total_Basic_Pension_Anticipated_12Y_3Y12 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Total_D_A_Upto_11Y[k] = request
										.getParameter("Total_D_A_Upto_11Y" + k);
								if (Total_D_A_Upto_11Y[k] != null) {
									if (Total_D_A_Upto_11Y[k].equals("")) {
										Total_D_A_Upto_11Y2 = 0.0f;
									} else {
										Total_D_A_Upto_11Y2 = Float
												.parseFloat(Total_D_A_Upto_11Y[k]);
									}
								} else {
									Total_D_A_Upto_11Y2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Total_D_A_Anticipated_12Y_3Y1[k] = request
										.getParameter("Total_D_A_Anticipated_12Y_3Y1"
												+ k);
								if (Total_D_A_Anticipated_12Y_3Y1[k] != null) {
									if (Total_D_A_Anticipated_12Y_3Y1[k]
											.equals("")) {
										Total_D_A_Anticipated_12Y_3Y12 = 0.0f;
									} else {
										Total_D_A_Anticipated_12Y_3Y12 = Float
												.parseFloat(Total_D_A_Anticipated_12Y_3Y1[k]);
									}
								} else {
									Total_D_A_Anticipated_12Y_3Y12 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Total_Other_Payment_Upto_11Y[k] = request
										.getParameter("Total_Other_Payment_Upto_11Y"
												+ k);
								if (Total_Other_Payment_Upto_11Y[k] != null) {
									if (Total_Other_Payment_Upto_11Y[k]
											.equals("")) {
										Total_Other_Payment_Upto_11Y2 = 0.0f;
									} else {
										Total_Other_Payment_Upto_11Y2 = Float
												.parseFloat(Total_Other_Payment_Upto_11Y[k]);
									}
								} else {
									Total_Other_Payment_Upto_11Y2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Total_Other_Payment_Anticipated_12Y_3Y1[k] = request
										.getParameter("Total_Other_Payment_Anticipated_12Y_3Y1"
												+ k);
								if (Total_Other_Payment_Anticipated_12Y_3Y1[k] != null) {
									if (Total_Other_Payment_Anticipated_12Y_3Y1[k]
											.equals("")) {
										Total_Other_Payment_Anticipated_12Y_3Y12 = 0.0f;
									} else {
										Total_Other_Payment_Anticipated_12Y_3Y12 = Float
												.parseFloat(Total_Other_Payment_Anticipated_12Y_3Y1[k]);
									}
								} else {
									Total_Other_Payment_Anticipated_12Y_3Y12 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Total_Upto_11Y[k] = request
										.getParameter("Total_Upto_11Y" + k);
								if (Total_Upto_11Y[k] != null) {
									if (Total_Upto_11Y[k].equals("")) {
										Total_Upto_11Y2 = 0.0f;
									} else {
										Total_Upto_11Y2 = Float
												.parseFloat(Total_Upto_11Y[k]);
									}
								} else {
									Total_Upto_11Y2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Total_Anticipated_12Y_3Y1[k] = request
										.getParameter("Total_Anticipated_12Y_3Y1"
												+ k);
								if (Total_Anticipated_12Y_3Y1[k] != null) {
									if (Total_Anticipated_12Y_3Y1[k].equals("")) {
										Total_Anticipated_12Y_3Y12 = 0.0f;
									} else {
										Total_Anticipated_12Y_3Y12 = Float
												.parseFloat(Total_Anticipated_12Y_3Y1[k]);
									}
								} else {
									Total_Anticipated_12Y_3Y12 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								No_of_Pensioners1[k] = request
										.getParameter("No_of_Pensioners1" + k);
								if (No_of_Pensioners1[k] != null) {
									if (No_of_Pensioners1[k].equals("")) {
										No_of_Pensioners12 = 0;
									} else {
										No_of_Pensioners12 = Integer
												.parseInt(No_of_Pensioners1[k]);
									}
								} else {
									No_of_Pensioners12 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting No_of_Pensioners1 -->"
												+ e);
							}

							try {
								Total_Basic_Pension[k] = request
										.getParameter("Total_Basic_Pension" + k);
								if (Total_Basic_Pension[k] != null) {
									if (Total_Basic_Pension[k].equals("")) {
										Total_Basic_Pension2 = 0.0f;
									} else {
										Total_Basic_Pension2 = Float
												.parseFloat(Total_Basic_Pension[k]);
									}
								} else {
									Total_Basic_Pension2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Total_D_A[k] = request.getParameter("Total_D_A"
										+ k);
								if (Total_D_A[k] != null) {
									if (Total_D_A[k].equals("")) {
										Total_D_A2 = 0.0f;
									} else {
										Total_D_A2 = Float
												.parseFloat(Total_D_A[k]);
									}
								} else {
									Total_D_A2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Total_Other_Payment[k] = request
										.getParameter("Total_Other_Payment" + k);
								if (Total_Other_Payment[k] != null) {
									if (Total_Other_Payment[k].equals("")) {
										Total_Other_Payment2 = 0.0f;
									} else {
										Total_Other_Payment2 = Float
												.parseFloat(Total_Other_Payment[k]);
									}
								} else {
									Total_Other_Payment2 = 0.0f;
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

							int i = 1, i1 = 0;
							try {
								ps = con
										.prepareStatement("Select max(SL_NO) from FAS_BUDGET_FORMAT6_CONSOLIDATE");
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
									.prepareStatement("insert into FAS_BUDGET_FORMAT6_CONSOLIDATE (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,CATEGORY,NO_OF_PENSIONERS,TOTAL_BASIC_PENSION_UPTO_11Y,TOT_BASIC_PNSN_ANTCPTD_12Y_3Y1,TOTAL_D_A_UPTO_11Y,TOTAL_D_A_ANTICIPATED_12Y_3Y1,TOTAL_OTHER_PAYMENT_UPTO_11Y,TOT_OTHR_PYMNT_ANTCPTD_12Y_3Y1,TOTAL_UPTO_11Y,TOTAL_ANTICIPATED_12Y_3Y1,NO_OF_PENSIONERS1,TOTAL_BASIC_PENSION,TOTAL_D_A,TOTAL_OTHER_PAYMENT,TOTAL,DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
							ps.setInt(1, cmbAcc_UnitCode);
							ps.setInt(2, cmbOffice_code);
							ps.setString(3, FinancialYear);
							ps.setInt(4, i);
							ps.setString(5, Category2);
							ps.setInt(6, No_of_Pensioners2);
							ps.setFloat(7, Total_Basic_Pension_Upto_11Y2);
							ps.setFloat(8,
									Total_Basic_Pension_Anticipated_12Y_3Y12);
							ps.setFloat(9, Total_D_A_Upto_11Y2);
							ps.setFloat(10, Total_D_A_Anticipated_12Y_3Y12);
							ps.setFloat(11, Total_Other_Payment_Upto_11Y2);
							ps.setFloat(12,
									Total_Other_Payment_Anticipated_12Y_3Y12);
							ps.setFloat(13, Total_Upto_11Y2);
							ps.setFloat(14, Total_Anticipated_12Y_3Y12);
							ps.setInt(15, No_of_Pensioners12);
							ps.setFloat(16, Total_Basic_Pension2);
							ps.setFloat(17, Total_D_A2);
							ps.setFloat(18, Total_Other_Payment2);
							ps.setFloat(19, Total2);
							ps.setInt(20, division_id);
							ps.setInt(21, circle_id);
							ps.setInt(22, region_id);
							ps.setInt(23, head_office_id);
							ps.setString(24, office_level_id);
							ps.setString(25, userid);
							ps.setTimestamp(26, ts);
							int kk = ps.executeUpdate();
						}
						flag = 2;
					}
					con.commit();
					if (flag == 1) {
						sendMessage(
								response,
								"Records Alredy Exist for given Office for given Financial Year ............ ",
								"ok", "Civil_Budget_Format_6_Consolidation.jsp");
					} else if (flag == 2) {
						sendMessage(response,
								"Records Saved Successfully ............ ",
								"ok", "Civil_Budget_Format_6_Consolidation.jsp");
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
					ps1.setString(4, "6");
					rs = ps1.executeQuery();
					if (rs.next()) {
						xml = xml + "<flag>Freezed</flag>";
					} else {
						ps = con
								.prepareStatement(" select SL_NO,CATEGORY,NO_OF_PENSIONERS,TOTAL_BASIC_PENSION_UPTO_11Y,TOT_BASIC_PNSN_ANTCPTD_12Y_3Y1,TOTAL_D_A_UPTO_11Y,TOTAL_D_A_ANTICIPATED_12Y_3Y1,TOTAL_OTHER_PAYMENT_UPTO_11Y,TOT_OTHR_PYMNT_ANTCPTD_12Y_3Y1,TOTAL_UPTO_11Y,TOTAL_ANTICIPATED_12Y_3Y1,NO_OF_PENSIONERS1,TOTAL_BASIC_PENSION,TOTAL_D_A,TOTAL_OTHER_PAYMENT,TOTAL from FAS_BUDGET_FORMAT6_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, FinancialYear);
						rs = ps.executeQuery();
						while (rs.next()) {
							xml = xml + "<slno>" + rs.getInt("SL_NO")
									+ "</slno>";

							xml = xml + "<Category>" + rs.getString("CATEGORY")
									+ "</Category>";

							xml = xml + "<No_of_Pensioners>"
									+ rs.getInt("NO_OF_PENSIONERS")
									+ "</No_of_Pensioners>";

							xml = xml
									+ "<Total_Basic_Pension_Upto_11Y>"
									+ rs
											.getBigDecimal("TOTAL_BASIC_PENSION_UPTO_11Y")
									+ "</Total_Basic_Pension_Upto_11Y>";

							xml = xml
									+ "<Total_Basic_Pension_Anticipated_12Y_3Y1>"
									+ rs
											.getBigDecimal("TOT_BASIC_PNSN_ANTCPTD_12Y_3Y1")
									+ "</Total_Basic_Pension_Anticipated_12Y_3Y1>";

							xml = xml + "<Total_D_A_Upto_11Y>"
									+ rs.getBigDecimal("TOTAL_D_A_UPTO_11Y")
									+ "</Total_D_A_Upto_11Y>";

							xml = xml
									+ "<Total_D_A_Anticipated_12Y_3Y1>"
									+ rs
											.getBigDecimal("TOTAL_D_A_ANTICIPATED_12Y_3Y1")
									+ "</Total_D_A_Anticipated_12Y_3Y1>";

							xml = xml
									+ "<Total_Other_Payment_Upto_11Y>"
									+ rs
											.getBigDecimal("TOTAL_OTHER_PAYMENT_UPTO_11Y")
									+ "</Total_Other_Payment_Upto_11Y>";

							xml = xml
									+ "<Total_Other_Payment_Anticipated_12Y_3Y1>"
									+ rs
											.getBigDecimal("TOT_OTHR_PYMNT_ANTCPTD_12Y_3Y1")
									+ "</Total_Other_Payment_Anticipated_12Y_3Y1>";

							xml = xml + "<Total_Upto_11Y>"
									+ rs.getBigDecimal("TOTAL_UPTO_11Y")
									+ "</Total_Upto_11Y>";

							xml = xml + "<Total_Anticipated_12Y_3Y1>"
									+ rs.getBigDecimal("TOTAL_ANTICIPATED_12Y_3Y1")
									+ "</Total_Anticipated_12Y_3Y1>";

							xml = xml + "<No_of_Pensioners1>"
									+ rs.getInt("NO_OF_PENSIONERS1")
									+ "</No_of_Pensioners1>";

							xml = xml + "<Total_Basic_Pension>"
									+ rs.getBigDecimal("TOTAL_BASIC_PENSION")
									+ "</Total_Basic_Pension>";

							xml = xml + "<Total_D_A>"
									+ rs.getBigDecimal("TOTAL_D_A") + "</Total_D_A>";

							xml = xml + "<Total_Other_Payment>"
									+ rs.getBigDecimal("TOTAL_OTHER_PAYMENT")
									+ "</Total_Other_Payment>";

							xml = xml + "<Total>" + rs.getBigDecimal("TOTAL")
									+ "</Total>";
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
									.prepareStatement(" select * from FAS_BUDGET_FORMAT6_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and SL_NO=?");
							ps.setInt(1, cmbAcc_UnitCode);
							ps.setInt(2, cmbOffice_code);
							ps.setString(3, FinancialYear);
							ps.setInt(4, slno2);
							rs = ps.executeQuery();
							if (rs.next()) {
								ps1 = con
										.prepareStatement(" delete from FAS_BUDGET_FORMAT6_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and SL_NO=?");
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
								"ok", "Civil_Budget_Format_6_Consolidation.jsp");
					} else if (flag == 3) {
						con.commit();
						sendMessage(response,
								"Record Does Not Exist ............ ", "ok",
								"Civil_Budget_Format_6_Consolidation.jsp");
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Deletion Failed ............ " + e, "ok",
							"Civil_Budget_Format_6_Consolidation.jsp");
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
					ps1.setString(4, "6");
					rs2 = ps1.executeQuery();
					if (rs2.next()) {
						sendMessage(
								response,
								"Civil Budget Format-6 have Already Freezed ............ ",
								"ok", "Civil_Budget_Format_6_Consolidation.jsp");
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
								.prepareStatement(" select * from FAS_BUDGET_FORMAT6_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, FinancialYear);
						rs = ps.executeQuery();
						if (rs.next()) {
							ps1 = con
									.prepareStatement(" delete from FAS_BUDGET_FORMAT6_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
							ps1.setInt(1, cmbAcc_UnitCode);
							ps1.setInt(2, cmbOffice_code);
							ps1.setString(3, FinancialYear);
							ps1.executeUpdate();
							for (int k = 0; k < RecordCount; k++) {

								try {
									Category2 = request.getParameter("Category"
											+ k);
								} catch (Exception e) {
									System.out
											.println("Error for getting Category -->"
													+ e);
								}

								try {
									No_of_Pensioners[k] = request
											.getParameter("No_of_Pensioners"
													+ k);
									if (No_of_Pensioners[k] != null) {
										if (No_of_Pensioners[k].equals("")) {
											No_of_Pensioners2 = 0;
										} else {
											No_of_Pensioners2 = Integer
													.parseInt(No_of_Pensioners[k]);
										}
									} else {
										No_of_Pensioners2 = 0;
									}
								} catch (Exception e) {
									System.out
											.println("Error for getting No_of_Pensioners -->"
													+ e);
								}

								try {
									Total_Basic_Pension_Upto_11Y[k] = request
											.getParameter("Total_Basic_Pension_Upto_11Y"
													+ k);
									if (Total_Basic_Pension_Upto_11Y[k] != null) {
										if (Total_Basic_Pension_Upto_11Y[k]
												.equals("")) {
											Total_Basic_Pension_Upto_11Y2 = 0.0f;
										} else {
											Total_Basic_Pension_Upto_11Y2 = Float
													.parseFloat(Total_Basic_Pension_Upto_11Y[k]);
										}
									} else {
										Total_Basic_Pension_Upto_11Y2 = 0.0f;
									}
								} catch (Exception e) {
									System.out
											.println("Error for getting Total_Basic_Pension_Upto_11Y -->"
													+ e);
								}

								try {
									Total_Basic_Pension_Anticipated_12Y_3Y1[k] = request
											.getParameter("Total_Basic_Pension_Anticipated_12Y_3Y1"
													+ k);
									if (Total_Basic_Pension_Anticipated_12Y_3Y1[k] != null) {
										if (Total_Basic_Pension_Anticipated_12Y_3Y1[k]
												.equals("")) {
											Total_Basic_Pension_Anticipated_12Y_3Y12 = 0.0f;
										} else {
											Total_Basic_Pension_Anticipated_12Y_3Y12 = Float
													.parseFloat(Total_Basic_Pension_Anticipated_12Y_3Y1[k]);
										}
									} else {
										Total_Basic_Pension_Anticipated_12Y_3Y12 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Total_D_A_Upto_11Y[k] = request
											.getParameter("Total_D_A_Upto_11Y"
													+ k);
									if (Total_D_A_Upto_11Y[k] != null) {
										if (Total_D_A_Upto_11Y[k].equals("")) {
											Total_D_A_Upto_11Y2 = 0.0f;
										} else {
											Total_D_A_Upto_11Y2 = Float
													.parseFloat(Total_D_A_Upto_11Y[k]);
										}
									} else {
										Total_D_A_Upto_11Y2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Total_D_A_Anticipated_12Y_3Y1[k] = request
											.getParameter("Total_D_A_Anticipated_12Y_3Y1"
													+ k);
									if (Total_D_A_Anticipated_12Y_3Y1[k] != null) {
										if (Total_D_A_Anticipated_12Y_3Y1[k]
												.equals("")) {
											Total_D_A_Anticipated_12Y_3Y12 = 0.0f;
										} else {
											Total_D_A_Anticipated_12Y_3Y12 = Float
													.parseFloat(Total_D_A_Anticipated_12Y_3Y1[k]);
										}
									} else {
										Total_D_A_Anticipated_12Y_3Y12 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Total_Other_Payment_Upto_11Y[k] = request
											.getParameter("Total_Other_Payment_Upto_11Y"
													+ k);
									if (Total_Other_Payment_Upto_11Y[k] != null) {
										if (Total_Other_Payment_Upto_11Y[k]
												.equals("")) {
											Total_Other_Payment_Upto_11Y2 = 0.0f;
										} else {
											Total_Other_Payment_Upto_11Y2 = Float
													.parseFloat(Total_Other_Payment_Upto_11Y[k]);
										}
									} else {
										Total_Other_Payment_Upto_11Y2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Total_Other_Payment_Anticipated_12Y_3Y1[k] = request
											.getParameter("Total_Other_Payment_Anticipated_12Y_3Y1"
													+ k);
									if (Total_Other_Payment_Anticipated_12Y_3Y1[k] != null) {
										if (Total_Other_Payment_Anticipated_12Y_3Y1[k]
												.equals("")) {
											Total_Other_Payment_Anticipated_12Y_3Y12 = 0.0f;
										} else {
											Total_Other_Payment_Anticipated_12Y_3Y12 = Float
													.parseFloat(Total_Other_Payment_Anticipated_12Y_3Y1[k]);
										}
									} else {
										Total_Other_Payment_Anticipated_12Y_3Y12 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Total_Upto_11Y[k] = request
											.getParameter("Total_Upto_11Y" + k);
									if (Total_Upto_11Y[k] != null) {
										if (Total_Upto_11Y[k].equals("")) {
											Total_Upto_11Y2 = 0.0f;
										} else {
											Total_Upto_11Y2 = Float
													.parseFloat(Total_Upto_11Y[k]);
										}
									} else {
										Total_Upto_11Y2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Total_Anticipated_12Y_3Y1[k] = request
											.getParameter("Total_Anticipated_12Y_3Y1"
													+ k);
									if (Total_Anticipated_12Y_3Y1[k] != null) {
										if (Total_Anticipated_12Y_3Y1[k]
												.equals("")) {
											Total_Anticipated_12Y_3Y12 = 0.0f;
										} else {
											Total_Anticipated_12Y_3Y12 = Float
													.parseFloat(Total_Anticipated_12Y_3Y1[k]);
										}
									} else {
										Total_Anticipated_12Y_3Y12 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									No_of_Pensioners1[k] = request
											.getParameter("No_of_Pensioners1"
													+ k);
									if (No_of_Pensioners1[k] != null) {
										if (No_of_Pensioners1[k].equals("")) {
											No_of_Pensioners12 = 0;
										} else {
											No_of_Pensioners12 = Integer
													.parseInt(No_of_Pensioners1[k]);
										}
									} else {
										No_of_Pensioners12 = 0;
									}
								} catch (Exception e) {
									System.out
											.println("Error for getting No_of_Pensioners1 -->"
													+ e);
								}

								try {
									Total_Basic_Pension[k] = request
											.getParameter("Total_Basic_Pension"
													+ k);
									if (Total_Basic_Pension[k] != null) {
										if (Total_Basic_Pension[k].equals("")) {
											Total_Basic_Pension2 = 0.0f;
										} else {
											Total_Basic_Pension2 = Float
													.parseFloat(Total_Basic_Pension[k]);
										}
									} else {
										Total_Basic_Pension2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Total_D_A[k] = request
											.getParameter("Total_D_A" + k);
									if (Total_D_A[k] != null) {
										if (Total_D_A[k].equals("")) {
											Total_D_A2 = 0.0f;
										} else {
											Total_D_A2 = Float
													.parseFloat(Total_D_A[k]);
										}
									} else {
										Total_D_A2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Total_Other_Payment[k] = request
											.getParameter("Total_Other_Payment"
													+ k);
									if (Total_Other_Payment[k] != null) {
										if (Total_Other_Payment[k].equals("")) {
											Total_Other_Payment2 = 0.0f;
										} else {
											Total_Other_Payment2 = Float
													.parseFloat(Total_Other_Payment[k]);
										}
									} else {
										Total_Other_Payment2 = 0.0f;
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

								int i = 1, i1 = 0;
								try {
									ps = con
											.prepareStatement("Select max(SL_NO) from FAS_BUDGET_FORMAT6_CONSOLIDATE");
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
										.prepareStatement("insert into FAS_BUDGET_FORMAT6_CONSOLIDATE (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,CATEGORY,NO_OF_PENSIONERS,TOTAL_BASIC_PENSION_UPTO_11Y,TOT_BASIC_PNSN_ANTCPTD_12Y_3Y1,TOTAL_D_A_UPTO_11Y,TOTAL_D_A_ANTICIPATED_12Y_3Y1,TOTAL_OTHER_PAYMENT_UPTO_11Y,TOT_OTHR_PYMNT_ANTCPTD_12Y_3Y1,TOTAL_UPTO_11Y,TOTAL_ANTICIPATED_12Y_3Y1,NO_OF_PENSIONERS1,TOTAL_BASIC_PENSION,TOTAL_D_A,TOTAL_OTHER_PAYMENT,TOTAL,DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
								ps.setInt(1, cmbAcc_UnitCode);
								ps.setInt(2, cmbOffice_code);
								ps.setString(3, FinancialYear);
								ps.setInt(4, i);
								ps.setString(5, Category2);
								ps.setInt(6, No_of_Pensioners2);
								ps.setFloat(7, Total_Basic_Pension_Upto_11Y2);
								ps
										.setFloat(8,
												Total_Basic_Pension_Anticipated_12Y_3Y12);
								ps.setFloat(9, Total_D_A_Upto_11Y2);
								ps.setFloat(10, Total_D_A_Anticipated_12Y_3Y12);
								ps.setFloat(11, Total_Other_Payment_Upto_11Y2);
								ps
										.setFloat(12,
												Total_Other_Payment_Anticipated_12Y_3Y12);
								ps.setFloat(13, Total_Upto_11Y2);
								ps.setFloat(14, Total_Anticipated_12Y_3Y12);
								ps.setInt(15, No_of_Pensioners12);
								ps.setFloat(16, Total_Basic_Pension2);
								ps.setFloat(17, Total_D_A2);
								ps.setFloat(18, Total_Other_Payment2);
								ps.setFloat(19, Total2);
								ps.setInt(20, division_id);
								ps.setInt(21, circle_id);
								ps.setInt(22, region_id);
								ps.setInt(23, head_office_id);
								ps.setString(24, office_level_id);
								ps.setString(25, userid);
								ps.setTimestamp(26, ts);
								int kk = ps.executeUpdate();
							}
							con.commit();
							sendMessage(
									response,
									"Records Updated Successfully ............ ",
									"ok", "Civil_Budget_Format_6_Consolidation.jsp");
						} else {
							sendMessage(response,
									"Records Does Not Exist ............ ",
									"ok", "Civil_Budget_Format_6_Consolidation.jsp");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Updation Failed ............ " + e, "ok",
							"Civil_Budget_Format_6_Consolidation.jsp");
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
					"ok", "Civil_Budget_Format_6.jsp");
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
