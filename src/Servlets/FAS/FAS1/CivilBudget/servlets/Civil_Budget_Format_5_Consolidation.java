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
 * Servlet implementation class Civil_Budget_Format_5_Consolidation
 */
public class Civil_Budget_Format_5_Consolidation extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Civil_Budget_Format_5_Consolidation() {
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
					sql = " select SL_NO,EMP_ID,EMPLOYEE_NAME, offid,  OFFICE_NAME, DESC_ID,designation,   DATE_OF_RETIREMENT,  SA_ENCASHMENT_OF_LS,  SA_COMMUTATION_OF_PENSION,   SA_GRATUITY,  DATE_OF_RETIREMENT1,  VR_ENCASHMENT_OF_LS,   VR_COMMUTATION_OF_PENSION,  VR_GRATUITY from (SELECT SL_NO,EMP_ID,   DESC_ID,  TO_CHAR(DATE_OF_RETIREMENT,'DD/MM/YYYY') AS  DATE_OF_RETIREMENT,  SA_ENCASHMENT_OF_LS,  SA_COMMUTATION_OF_PENSION,   SA_GRATUITY,  TO_CHAR(DATE_OF_RETIREMENT1,'DD/MM/YYYY') AS  DATE_OF_RETIREMENT1,  VR_ENCASHMENT_OF_LS,  VR_COMMUTATION_OF_PENSION,     VR_GRATUITY FROM FAS_BUDGET_FORMAT_5 WHERE FINANCIAL_YEAR  =? and OFFICE_LEVEL_ID in('CL','DN') and CIRCLE=? )x  left outer join (select EMPLOYEE_ID,EMPLOYEE_NAME from  HRM_MST_EMPLOYEES)y on x.EMP_ID =y.EMPLOYEE_ID left outer join  (select designation_id,designation from hrm_mst_designations)z on  x.DESC_ID =z.designation_id  LEFT OUTER JOIN  (SELECT EMPLOYEE_ID,OFFICE_ID FROM HRM_EMP_CURRENT_POSTING   )xx ON x.EMP_ID =xx.EMPLOYEE_ID LEFT OUTER JOIN   (SELECT OFFICE_ID AS offid,OFFICE_NAME FROM COM_MST_OFFICES   )yy ON xx.OFFICE_ID =yy.offid order by SL_NO ";
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
						pss.setString(4, "5");
						results2 = pss.executeQuery();
						if (results2.next()) {
							flag = 1;
						} else {
							flag1 = 1;
						}
					}
				} else if (office_level_id.equals("RN")) {
					sql = " select SL_NO,EMP_ID,EMPLOYEE_NAME, offid,  OFFICE_NAME, DESC_ID,designation,   DATE_OF_RETIREMENT,  SA_ENCASHMENT_OF_LS,  SA_COMMUTATION_OF_PENSION,   SA_GRATUITY,  DATE_OF_RETIREMENT1,  VR_ENCASHMENT_OF_LS,   VR_COMMUTATION_OF_PENSION,  VR_GRATUITY from (SELECT SL_NO,EMP_ID,   DESC_ID,  TO_CHAR(DATE_OF_RETIREMENT,'DD/MM/YYYY') AS  DATE_OF_RETIREMENT,  SA_ENCASHMENT_OF_LS,  SA_COMMUTATION_OF_PENSION,   SA_GRATUITY,  TO_CHAR(DATE_OF_RETIREMENT1,'DD/MM/YYYY') AS  DATE_OF_RETIREMENT1,  VR_ENCASHMENT_OF_LS,  VR_COMMUTATION_OF_PENSION,     VR_GRATUITY FROM FAS_BUDGET_FORMAT_5 WHERE FINANCIAL_YEAR  =? and OFFICE_LEVEL_ID in('RN','CL','DN') and REGION=? )x  left outer join (select EMPLOYEE_ID,EMPLOYEE_NAME from  HRM_MST_EMPLOYEES)y on x.EMP_ID =y.EMPLOYEE_ID left outer join  (select designation_id,designation from hrm_mst_designations)z on  x.DESC_ID =z.designation_id  LEFT OUTER JOIN  (SELECT EMPLOYEE_ID,OFFICE_ID FROM HRM_EMP_CURRENT_POSTING   )xx ON x.EMP_ID =xx.EMPLOYEE_ID LEFT OUTER JOIN   (SELECT OFFICE_ID AS offid,OFFICE_NAME FROM COM_MST_OFFICES   )yy ON xx.OFFICE_ID =yy.offid order by SL_NO";
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
						pss.setString(4, "5");
						results2 = pss.executeQuery();
						if (results2.next()) {
							flag = 1;
						} else {
							flag1 = 1;
						}
					}
				} else if (office_level_id.equals("HO")) {
					sql = " select SL_NO,EMP_ID,EMPLOYEE_NAME, offid,  OFFICE_NAME, DESC_ID,designation,   DATE_OF_RETIREMENT,  SA_ENCASHMENT_OF_LS,  SA_COMMUTATION_OF_PENSION,   SA_GRATUITY,  DATE_OF_RETIREMENT1,  VR_ENCASHMENT_OF_LS,   VR_COMMUTATION_OF_PENSION,  VR_GRATUITY from (SELECT SL_NO,EMP_ID,   DESC_ID,  TO_CHAR(DATE_OF_RETIREMENT,'DD/MM/YYYY') AS  DATE_OF_RETIREMENT,  SA_ENCASHMENT_OF_LS,  SA_COMMUTATION_OF_PENSION,   SA_GRATUITY,  TO_CHAR(DATE_OF_RETIREMENT1,'DD/MM/YYYY') AS  DATE_OF_RETIREMENT1,  VR_ENCASHMENT_OF_LS,  VR_COMMUTATION_OF_PENSION,     VR_GRATUITY FROM FAS_BUDGET_FORMAT_5 WHERE FINANCIAL_YEAR  =? and OFFICE_LEVEL_ID in('HO','RN','CL','DN') )x  left outer join (select EMPLOYEE_ID,EMPLOYEE_NAME from  HRM_MST_EMPLOYEES)y on x.EMP_ID =y.EMPLOYEE_ID left outer join  (select designation_id,designation from hrm_mst_designations)z on  x.DESC_ID =z.designation_id  LEFT OUTER JOIN  (SELECT EMPLOYEE_ID,OFFICE_ID FROM HRM_EMP_CURRENT_POSTING   )xx ON x.EMP_ID =xx.EMPLOYEE_ID LEFT OUTER JOIN   (SELECT OFFICE_ID AS offid,OFFICE_NAME FROM COM_MST_OFFICES   )yy ON xx.OFFICE_ID =yy.offid order by SL_NO ";
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
						pss.setString(4, "5");
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
					ps1.setString(4, "5");
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
					ps1.setString(4, "5");
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
						
						xml = xml + "<emp_id>" + rs.getInt("EMP_ID")
								+ "</emp_id>";

						xml = xml + "<emp_name>" + rs.getString("EMPLOYEE_NAME")
						+ "</emp_name>";
						
						xml = xml + "<desc_id>" + rs.getInt("DESC_ID")
								+ "</desc_id>";

						xml = xml + "<desc_name>" + rs.getString("designation")
						+ "</desc_name>";
						
						xml = xml + "<Date_of_Retirement>"
								+ rs.getString("DATE_OF_RETIREMENT")
								+ "</Date_of_Retirement>";

						xml = xml + "<SA_Encashment_of_LS>"
								+ rs.getBigDecimal("SA_ENCASHMENT_OF_LS")
								+ "</SA_Encashment_of_LS>";

						xml = xml
								+ "<SA_Commutation_of_Pension>"
								+ rs
										.getBigDecimal("SA_COMMUTATION_OF_PENSION")
								+ "</SA_Commutation_of_Pension>";

						xml = xml + "<SA_Gratuity>"
								+ rs.getBigDecimal("SA_GRATUITY")
								+ "</SA_Gratuity>";

						xml = xml + "<Date_of_Retirement1>"
								+ rs.getString("DATE_OF_RETIREMENT1")
								+ "</Date_of_Retirement1>";

						xml = xml + "<VR_Encashment_of_LS>"
								+ rs.getBigDecimal("VR_ENCASHMENT_OF_LS")
								+ "</VR_Encashment_of_LS>";

						xml = xml
								+ "<VR_Commutation_of_Pension>"
								+ rs
										.getBigDecimal("VR_COMMUTATION_OF_PENSION")
								+ "</VR_Commutation_of_Pension>";

						xml = xml + "<VR_Gratuity>"
								+ rs.getBigDecimal("VR_GRATUITY")
								+ "</VR_Gratuity>";

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
		String emp_id[] = new String[RecordCount];
		String desc_id[] = new String[RecordCount];
		String Date_of_Retirement[] = new String[RecordCount];
		String SA_Encashment_of_LS[] = new String[RecordCount];
		String SA_Commutation_of_Pension[] = new String[RecordCount];
		String SA_Gratuity[] = new String[RecordCount];
		String Date_of_Retirement1[] = new String[RecordCount];
		String VR_Encashment_of_LS[] = new String[RecordCount];
		String VR_Commutation_of_Pension[] = new String[RecordCount];
		String VR_Gratuity[] = new String[RecordCount];

		/* Variables Declaration */
		int emp_id2 = 0;
		int desc_id2 = 0;
		Date Date_of_Retirement2 = null;
		double SA_Encashment_of_LS2 = 0.0d;
		double SA_Commutation_of_Pension2 = 0.0d;
		double SA_Gratuity2 = 0.0d;
		Date Date_of_Retirement12 = null;
		double VR_Encashment_of_LS2 = 0.0d;
		double VR_Commutation_of_Pension2 = 0.0d;
		double VR_Gratuity2 = 0.0d;
		int flag = 0;
		String office_level_id = null;
		int division_id = 0;
		int circle_id = 0;
		int region_id = 0;
		int head_office_id = 0;
		String sd[] = new String[10];
		java.util.Date d = null;
		Calendar c;
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
						.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR from FAS_BUDGET_FORMAT5_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setString(3, FinancialYear);
				rs = ps.executeQuery();
				if (rs.next()) {
					flag = 1;
				} else {
					for (int k = 0; k < RecordCount; k++) {

						/* emp_id */
						try {
							emp_id[k] = request.getParameter("emp_id" + k);
							if (emp_id[k] != null) {
								if (emp_id[k].equals("")) {
									emp_id2 = 0;
								} else {
									emp_id2 = Integer.parseInt(emp_id[k]);
								}
							} else {
								emp_id2 = 0;
							}
						} catch (Exception e) {
							System.out
									.println("Error for getting Head_of_Account -->"
											+ e);
						}

						/* desc_id */
						try {
							desc_id[k] = request.getParameter("desc_id" + k);
							if (desc_id[k] != null) {
								if (desc_id[k].equals("")) {
									desc_id2 = 0;
								} else {
									desc_id2 = Integer.parseInt(desc_id[k]);
								}
							} else {
								desc_id2 = 0;
							}
						} catch (Exception e) {
							System.out
									.println("Error for getting Head_of_Account -->"
											+ e);
						}

						/* Date_of_Retirement */
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

						/* SA_Encashment_of_LS */
						try {
							SA_Encashment_of_LS[k] = request
									.getParameter("SA_Encashment_of_LS" + k);
							if (SA_Encashment_of_LS[k] != null) {
								if (SA_Encashment_of_LS[k].equals("")) {
									SA_Encashment_of_LS2 = 0.0d;
								} else {
									SA_Encashment_of_LS2 = Double
											.parseDouble(SA_Encashment_of_LS[k]);
								}
							} else {
								SA_Encashment_of_LS2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						/* SA_Commutation_of_Pension */
						try {
							SA_Commutation_of_Pension[k] = request
									.getParameter("SA_Commutation_of_Pension"
											+ k);
							if (SA_Commutation_of_Pension[k] != null) {
								if (SA_Commutation_of_Pension[k].equals("")) {
									SA_Commutation_of_Pension2 = 0.0d;
								} else {
									SA_Commutation_of_Pension2 = Double
											.parseDouble(SA_Commutation_of_Pension[k]);
								}
							} else {
								SA_Commutation_of_Pension2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						/* RE for the Year */
						try {
							SA_Gratuity[k] = request.getParameter("SA_Gratuity"
									+ k);
							if (SA_Gratuity[k] != null) {
								if (SA_Gratuity[k].equals("")) {
									SA_Gratuity2 = 0.0d;
								} else {
									SA_Gratuity2 = Double
											.parseDouble(SA_Gratuity[k]);
								}
							} else {
								SA_Gratuity2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						/* Date_Of_Purchase1 */
						try {
							Date_of_Retirement1[k] = request
									.getParameter("Date_of_Retirement1" + k);

							if (!Date_of_Retirement1[k].equalsIgnoreCase("")) {
								sd = Date_of_Retirement1[k].split("/");
								c = new GregorianCalendar(Integer
										.parseInt(sd[2]), Integer
										.parseInt(sd[1]) - 1, Integer
										.parseInt(sd[0]));
								d = c.getTime();
								Date_of_Retirement12 = new Date(d.getTime());
							} else {
								Date_of_Retirement12 = null;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						/* VR_Encashment_of_LS */
						try {
							VR_Encashment_of_LS[k] = request
									.getParameter("VR_Encashment_of_LS" + k);
							if (VR_Encashment_of_LS[k] != null) {
								if (VR_Encashment_of_LS[k].equals("")) {
									VR_Encashment_of_LS2 = 0.0d;
								} else {
									VR_Encashment_of_LS2 = Double
											.parseDouble(VR_Encashment_of_LS[k]);
								}
							} else {
								VR_Encashment_of_LS2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						/* VR_Commutation_of_Pension */
						try {
							VR_Commutation_of_Pension[k] = request
									.getParameter("VR_Commutation_of_Pension"
											+ k);
							if (VR_Commutation_of_Pension[k] != null) {
								if (VR_Commutation_of_Pension[k].equals("")) {
									VR_Commutation_of_Pension2 = 0.0d;
								} else {
									VR_Commutation_of_Pension2 = Double
											.parseDouble(VR_Commutation_of_Pension[k]);
								}
							} else {
								VR_Commutation_of_Pension2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						/* VR_Gratuity */
						try {
							VR_Gratuity[k] = request.getParameter("VR_Gratuity"
									+ k);
							if (VR_Gratuity[k] != null) {
								if (VR_Gratuity[k].equals("")) {
									VR_Gratuity2 = 0.0d;
								} else {
									VR_Gratuity2 = Double
											.parseDouble(VR_Gratuity[k]);
								}
							} else {
								VR_Gratuity2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						int i = 1, i1 = 0;
						try {
							ps = con
									.prepareStatement("Select max(SL_NO) from FAS_BUDGET_FORMAT5_CONSOLIDATE");
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
								.prepareStatement("insert into FAS_BUDGET_FORMAT5_CONSOLIDATE (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,EMP_ID,DESC_ID,DATE_OF_RETIREMENT,SA_ENCASHMENT_OF_LS,SA_COMMUTATION_OF_PENSION,SA_GRATUITY,DATE_OF_RETIREMENT1,VR_ENCASHMENT_OF_LS,VR_COMMUTATION_OF_PENSION,VR_GRATUITY,DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, FinancialYear);
						ps.setInt(4, i);
						ps.setInt(5, emp_id2);
						ps.setInt(6, desc_id2);
						ps.setDate(7, Date_of_Retirement2);
						ps.setDouble(8, SA_Encashment_of_LS2);
						ps.setDouble(9, SA_Commutation_of_Pension2);
						ps.setDouble(10, SA_Gratuity2);
						ps.setDate(11, Date_of_Retirement12);
						ps.setDouble(12, VR_Encashment_of_LS2);
						ps.setDouble(13, VR_Commutation_of_Pension2);
						ps.setDouble(14, VR_Gratuity2);
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
							"ok", "Civil_Budget_Format_5_Consolidation.jsp");
				} else if (flag == 2) {
					sendMessage(response,
							"Records Saved Successfully ............ ", "ok",
							"Civil_Budget_Format_5_Consolidation.jsp");
				}
			} else if (filter.equals("view")) {
				try {
					xml = "<response><command>LoadData</command>";
					ps1 = con
							.prepareStatement("select UPDATED_DATE from FAS_BUDGET_CLOSURE_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and FORMAT_NAME=?");
					ps1.setInt(1, cmbAcc_UnitCode);
					ps1.setInt(2, cmbOffice_code);
					ps1.setString(3, FinancialYear);
					ps1.setString(4, "5");
					rs = ps1.executeQuery();
					if (rs.next()) {
						xml = xml + "<flag>Exist</flag>";
					} else {
						ps = con
								.prepareStatement(" select SL_NO,EMP_ID,EMPLOYEE_NAME,  DESC_ID,designation,  "
										+ "DATE_OF_RETIREMENT,  SA_ENCASHMENT_OF_LS,  SA_COMMUTATION_OF_PENSION,"
										+ "  SA_GRATUITY,  DATE_OF_RETIREMENT1,  VR_ENCASHMENT_OF_LS,  "
										+ "VR_COMMUTATION_OF_PENSION,  VR_GRATUITY from (SELECT SL_NO,EMP_ID,  "
										+ "DESC_ID,  TO_CHAR(DATE_OF_RETIREMENT,'DD/MM/YYYY') AS "
										+ "DATE_OF_RETIREMENT,  SA_ENCASHMENT_OF_LS,  SA_COMMUTATION_OF_PENSION,"
										+ "  SA_GRATUITY,  TO_CHAR(DATE_OF_RETIREMENT1,'DD/MM/YYYY') AS "
										+ "DATE_OF_RETIREMENT1,  VR_ENCASHMENT_OF_LS,  VR_COMMUTATION_OF_PENSION,"
										+ "   VR_GRATUITY FROM FAS_BUDGET_FORMAT5_CONSOLIDATE WHERE ACCOUNTING_UNIT_ID    =?"
										+ " AND ACCOUNTING_FOR_OFFICE_ID=? AND FINANCIAL_YEAR  =?)x "
										+ "left outer join (select EMPLOYEE_ID,EMPLOYEE_NAME from "
										+ "HRM_MST_EMPLOYEES)y on x.EMP_ID =y.EMPLOYEE_ID left outer join "
										+ "(select designation_id,designation from hrm_mst_designations)z on "
										+ "x.DESC_ID =z.designation_id order by SL_NO");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, FinancialYear);
						rs = ps.executeQuery();
						while (rs.next()) {
							xml = xml + "<slno>" + rs.getInt("SL_NO")
							+ "</slno>";
							
							xml = xml + "<emp_id>" + rs.getInt("EMP_ID")
									+ "</emp_id>";

							xml = xml + "<emp_name>" + rs.getString("EMPLOYEE_NAME")
							+ "</emp_name>";
							
							xml = xml + "<desc_id>" + rs.getInt("DESC_ID")
									+ "</desc_id>";

							xml = xml + "<desc_name>" + rs.getString("designation")
							+ "</desc_name>";
							
							xml = xml + "<Date_of_Retirement>"
									+ rs.getString("DATE_OF_RETIREMENT")
									+ "</Date_of_Retirement>";

							xml = xml + "<SA_Encashment_of_LS>"
									+ rs.getBigDecimal("SA_ENCASHMENT_OF_LS")
									+ "</SA_Encashment_of_LS>";

							xml = xml
									+ "<SA_Commutation_of_Pension>"
									+ rs
											.getBigDecimal("SA_COMMUTATION_OF_PENSION")
									+ "</SA_Commutation_of_Pension>";

							xml = xml + "<SA_Gratuity>"
									+ rs.getBigDecimal("SA_GRATUITY")
									+ "</SA_Gratuity>";

							xml = xml + "<Date_of_Retirement1>"
									+ rs.getString("DATE_OF_RETIREMENT1")
									+ "</Date_of_Retirement1>";

							xml = xml + "<VR_Encashment_of_LS>"
									+ rs.getBigDecimal("VR_ENCASHMENT_OF_LS")
									+ "</VR_Encashment_of_LS>";

							xml = xml
									+ "<VR_Commutation_of_Pension>"
									+ rs
											.getBigDecimal("VR_COMMUTATION_OF_PENSION")
									+ "</VR_Commutation_of_Pension>";

							xml = xml + "<VR_Gratuity>"
									+ rs.getBigDecimal("VR_GRATUITY")
									+ "</VR_Gratuity>";

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
							.prepareStatement(" select * from FAS_BUDGET_FORMAT5_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setString(3, FinancialYear);
					rs = ps.executeQuery();
					if (rs.next()) {
						ps1 = con
								.prepareStatement(" delete from FAS_BUDGET_FORMAT5_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
						ps1.setInt(1, cmbAcc_UnitCode);
						ps1.setInt(2, cmbOffice_code);
						ps1.setString(3, FinancialYear);
						int sbg = ps1.executeUpdate();
						if (sbg > 0) {
							con.commit();
							sendMessage(
									response,
									"Records Deleted Successfully ............ ",
									"ok", "Civil_Budget_Format_5_Consolidation.jsp");
						} else {
							sendMessage(response,
									"Records Deletion Failed ............ ",
									"ok", "Civil_Budget_Format_5_Consolidation.jsp");
						}
					} else {
						sendMessage(response,
								"Records Does Not Exist ............ ", "ok",
								"Civil_Budget_Format_5_Consolidation.jsp");
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Deletion Failed ............ " + e, "ok",
							"Civil_Budget_Format_5_Consolidation.jsp");
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
							.prepareStatement(" select * from FAS_BUDGET_FORMAT5_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setString(3, FinancialYear);
					rs = ps.executeQuery();
					if (rs.next()) {
						ps1 = con
								.prepareStatement(" delete from FAS_BUDGET_FORMAT5_CONSOLIDATE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
						ps1.setInt(1, cmbAcc_UnitCode);
						ps1.setInt(2, cmbOffice_code);
						ps1.setString(3, FinancialYear);
						ps1.executeUpdate();
						for (int k = 0; k < RecordCount; k++) {

							/* emp_id */
							try {
								emp_id[k] = request.getParameter("emp_id" + k);
								if (emp_id[k] != null) {
									if (emp_id[k].equals("")) {
										emp_id2 = 0;
									} else {
										emp_id2 = Integer.parseInt(emp_id[k]);
									}
								} else {
									emp_id2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting Head_of_Account -->"
												+ e);
							}

							/* desc_id */
							try {
								desc_id[k] = request
										.getParameter("desc_id" + k);
								if (desc_id[k] != null) {
									if (desc_id[k].equals("")) {
										desc_id2 = 0;
									} else {
										desc_id2 = Integer.parseInt(desc_id[k]);
									}
								} else {
									desc_id2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting Head_of_Account -->"
												+ e);
							}

							/* Date_of_Retirement */
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

							/* SA_Encashment_of_LS */
							try {
								SA_Encashment_of_LS[k] = request
										.getParameter("SA_Encashment_of_LS" + k);
								if (SA_Encashment_of_LS[k] != null) {
									if (SA_Encashment_of_LS[k].equals("")) {
										SA_Encashment_of_LS2 = 0.0d;
									} else {
										SA_Encashment_of_LS2 = Double
												.parseDouble(SA_Encashment_of_LS[k]);
									}
								} else {
									SA_Encashment_of_LS2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* SA_Commutation_of_Pension */
							try {
								SA_Commutation_of_Pension[k] = request
										.getParameter("SA_Commutation_of_Pension"
												+ k);
								if (SA_Commutation_of_Pension[k] != null) {
									if (SA_Commutation_of_Pension[k].equals("")) {
										SA_Commutation_of_Pension2 = 0.0d;
									} else {
										SA_Commutation_of_Pension2 = Double
												.parseDouble(SA_Commutation_of_Pension[k]);
									}
								} else {
									SA_Commutation_of_Pension2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* RE for the Year */
							try {
								SA_Gratuity[k] = request
										.getParameter("SA_Gratuity" + k);
								if (SA_Gratuity[k] != null) {
									if (SA_Gratuity[k].equals("")) {
										SA_Gratuity2 = 0.0d;
									} else {
										SA_Gratuity2 = Double
												.parseDouble(SA_Gratuity[k]);
									}
								} else {
									SA_Gratuity2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* Date_Of_Purchase1 */
							try {
								Date_of_Retirement1[k] = request
										.getParameter("Date_of_Retirement1" + k);

								if (!Date_of_Retirement1[k]
										.equalsIgnoreCase("")) {
									sd = Date_of_Retirement1[k].split("/");
									c = new GregorianCalendar(Integer
											.parseInt(sd[2]), Integer
											.parseInt(sd[1]) - 1, Integer
											.parseInt(sd[0]));
									d = c.getTime();
									Date_of_Retirement12 = new Date(d.getTime());
								} else {
									Date_of_Retirement12 = null;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* VR_Encashment_of_LS */
							try {
								VR_Encashment_of_LS[k] = request
										.getParameter("VR_Encashment_of_LS" + k);
								if (VR_Encashment_of_LS[k] != null) {
									if (VR_Encashment_of_LS[k].equals("")) {
										VR_Encashment_of_LS2 = 0.0d;
									} else {
										VR_Encashment_of_LS2 = Double
												.parseDouble(VR_Encashment_of_LS[k]);
									}
								} else {
									VR_Encashment_of_LS2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* VR_Commutation_of_Pension */
							try {
								VR_Commutation_of_Pension[k] = request
										.getParameter("VR_Commutation_of_Pension"
												+ k);
								if (VR_Commutation_of_Pension[k] != null) {
									if (VR_Commutation_of_Pension[k].equals("")) {
										VR_Commutation_of_Pension2 = 0.0d;
									} else {
										VR_Commutation_of_Pension2 = Double
												.parseDouble(VR_Commutation_of_Pension[k]);
									}
								} else {
									VR_Commutation_of_Pension2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* VR_Gratuity */
							try {
								VR_Gratuity[k] = request
										.getParameter("VR_Gratuity" + k);
								if (VR_Gratuity[k] != null) {
									if (VR_Gratuity[k].equals("")) {
										VR_Gratuity2 = 0.0d;
									} else {
										VR_Gratuity2 = Double
												.parseDouble(VR_Gratuity[k]);
									}
								} else {
									VR_Gratuity2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							int i = 1, i1 = 0;
							try {
								ps = con
										.prepareStatement("Select max(SL_NO) from FAS_BUDGET_FORMAT5_CONSOLIDATE");
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
									.prepareStatement("insert into FAS_BUDGET_FORMAT5_CONSOLIDATE (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,EMP_ID,DESC_ID,DATE_OF_RETIREMENT,SA_ENCASHMENT_OF_LS,SA_COMMUTATION_OF_PENSION,SA_GRATUITY,DATE_OF_RETIREMENT1,VR_ENCASHMENT_OF_LS,VR_COMMUTATION_OF_PENSION,VR_GRATUITY,DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
							ps.setInt(1, cmbAcc_UnitCode);
							ps.setInt(2, cmbOffice_code);
							ps.setString(3, FinancialYear);
							ps.setInt(4, i);
							ps.setInt(5, emp_id2);
							ps.setInt(6, desc_id2);
							ps.setDate(7, Date_of_Retirement2);
							ps.setDouble(8, SA_Encashment_of_LS2);
							ps.setDouble(9, SA_Commutation_of_Pension2);
							ps.setDouble(10, SA_Gratuity2);
							ps.setDate(11, Date_of_Retirement12);
							ps.setDouble(12, VR_Encashment_of_LS2);
							ps.setDouble(13, VR_Commutation_of_Pension2);
							ps.setDouble(14, VR_Gratuity2);
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
								"ok", "Civil_Budget_Format_5_Consolidation.jsp");
					} else {
						sendMessage(response,
								"Records Does Not Exist ............ ", "ok",
								"Civil_Budget_Format_5_Consolidation.jsp");
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Updation Failed ............ " + e, "ok",
							"Civil_Budget_Format_5_Consolidation.jsp");
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
					"ok", "Civil_Budget_Format_5.jsp");
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
