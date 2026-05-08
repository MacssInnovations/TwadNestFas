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

/**
 * Servlet implementation class Civil_Budget_Freezed_Unit_List
 */
public class Consolidation_Freezed_Unit_List extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Consolidation_Freezed_Unit_List() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();

		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		PreparedStatement ps = null;
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
		int office_id = 0;
		String empName = empProfile.getEmployeeName();
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
		String office_level_id = null;

		try {
			/** Get Employee Office ID */
			ps = connection
					.prepareStatement("select OFFICE_ID   from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
			ps.setInt(1, empid);
			results = ps.executeQuery();
			if (results.next()) {

				office_id = results.getInt("OFFICE_ID");
			}
			
			System.out.println("office d >>>>"+office_id);
			
			if(office_id==0)office_id=Integer.parseInt(request.getParameter("cmbOffice_code"));
			
			

			ps2 = connection
					.prepareStatement("select OFFICE_LEVEL_ID  from COM_MST_OFFICES  where OFFICE_ID=?");
			ps2.setInt(1, office_id);
			rs2 = ps2.executeQuery();
			if (rs2.next()) {
				office_level_id = rs2.getString("OFFICE_LEVEL_ID");
			}
		} catch (Exception e) {
			e.printStackTrace();
		}
		if (strCommand.equalsIgnoreCase("LoadAccountingUnitID")) {
			xml = "<response><command>LoadAccountingUnitID</command>";
			try {
				ps1 = connection
						.prepareStatement(" SELECT accounting_unit_id ,  accounting_unit_name,  "
								+ "accounting_unit_office_id,  OFFICE_NAME FROM (SELECT accounting_unit_id ,    "
								+ "accounting_unit_name,    accounting_unit_office_id  FROM fas_mst_acct_units "
								+ "where accounting_unit_office_id=?)x  LEFT OUTER JOIN  (SELECT OFFICE_ID, "
								+ "OFFICE_NAME FROM COM_MST_OFFICES  )y ON "
								+ "x.accounting_unit_office_id =y.OFFICE_ID   ");
				ps1.setInt(1, office_id);
				rs = ps1.executeQuery();
				while (rs.next()) {
					xml = xml + "<Acc_UnitID>"
							+ rs.getInt("accounting_unit_id") + "</Acc_UnitID>";

					xml = xml + "<Acc_UnitName>"
							+ rs.getString("accounting_unit_name")
							+ "</Acc_UnitName>";

					xml = xml + "<Acc_OfficeID>"
							+ rs.getInt("accounting_unit_office_id")
							+ "</Acc_OfficeID>";

					xml = xml + "<Acc_OfficeName>"
							+ rs.getString("OFFICE_NAME") + "</Acc_OfficeName>";
				}

				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		} else if (strCommand.equalsIgnoreCase("list")) {
			xml = "<response><command>list</command>";
			int cmbAcc_UnitCode = 0;
			int cmbOffice_code = 0;
			String FinancialYear = null;
			String Statement_Name = null;
			String qry="";

			/* Get Accounting Unit ID */
			try {
				cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("cmbAcc_UnitCode"));
			} catch (Exception e) {
				System.out.println("Error Not Getting Accounitng Unit ID --> "
						+ e);
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

			try {
				Statement_Name = request.getParameter("Statement_Name");
			} catch (Exception e) {
				System.out.println("Error Not Getting Statement_Name -->" + e);
			}
			try {
				if (office_level_id.equals("CL")) {
qry="SELECT accounting_unit_id ,  accounting_unit_name,  "
									+ "accounting_unit_office_id,  OFFICE_NAME,to_char(UPDATED_DATE,'DD/MM/YYYY') as UPDATED_DATE FROM "
									+ "(((SELECT ACCOUNTING_UNIT_ID "
									+ "AS auid,  ACCOUNTING_FOR_OFFICE_ID AS acoid,  UPDATED_DATE FROM "
									+ "FAS_BUDGET_CLOSURE_ALLOCATION WHERE FINANCIAL_YEAR='"+FinancialYear+"' AND STATEMENT_NAME     ="+Statement_Name+" ) UNION   "
									+ "(SELECT ACCOUNTING_UNIT_ID AS auid,     ACCOUNTING_FOR_OFFICE_ID AS acoid,"
									+ "    UPDATED_DATE   FROM FAS_BUDGET_CLOSURE_ALLOCATION   WHERE FINANCIAL_YEAR        = '"+FinancialYear+"'"
									+ "  AND STATEMENT_NAME             ="+Statement_Name+"   AND ACCOUNTING_UNIT_ID      ="+cmbAcc_UnitCode+"   AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code
									+ "  ) )a   left outer join   (   SELECT accounting_unit_id ,   accounting_unit_name, "
									+ "  accounting_unit_office_id,   OFFICE_NAME FROM   (SELECT accounting_unit_id ,"
									+ "     accounting_unit_name,     accounting_unit_office_id  FROM fas_mst_acct_units "
									+ "  WHERE accounting_unit_office_id IN     ( select OFFICE_ID  from COM_MST_ALL_OFFICES_VIEW  where CIRCLE_OFFICE_ID="+office_id
									+ "   )   )x LEFT OUTER JOIN  "
									+ " (SELECT OFFICE_ID, OFFICE_NAME FROM COM_MST_OFFICES   )y ON x.accounting_unit_office_id =y.OFFICE_ID"
									+ "   )b ON a.auid         =b.accounting_unit_id AND a.acoid =b.accounting_unit_office_id ) "
									+ "where accounting_unit_id is not null  ";
					ps1 = connection
							.prepareStatement(qry);
				
					rs = ps1.executeQuery();

				} else if (office_level_id.equals("RN")) {

					qry=" SELECT accounting_unit_id ,  accounting_unit_name,  "
									+ "accounting_unit_office_id,  OFFICE_NAME,to_char(UPDATED_DATE,'DD/MM/YYYY') as UPDATED_DATE FROM "
									+ "(((SELECT ACCOUNTING_UNIT_ID "
									+ "AS auid,  ACCOUNTING_FOR_OFFICE_ID AS acoid,  UPDATED_DATE FROM "
									+ "FAS_BUDGET_CLOSURE_ALLOCATION WHERE FINANCIAL_YEAR='"+FinancialYear+"' AND STATEMENT_NAME     ="+Statement_Name+"  ) UNION   "
									+ "(SELECT ACCOUNTING_UNIT_ID AS auid,     ACCOUNTING_FOR_OFFICE_ID AS acoid,"
									+ "    UPDATED_DATE   FROM FAS_BUDGET_CLOSURE_ALLOCATION   WHERE FINANCIAL_YEAR='"+FinancialYear+"' AND STATEMENT_NAME     ="+Statement_Name
									+ " AND ACCOUNTING_UNIT_ID      ="+cmbAcc_UnitCode+"   AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code
									+ "  ) )a   left outer join   (   SELECT accounting_unit_id ,   accounting_unit_name, "
									+ "  accounting_unit_office_id,   OFFICE_NAME FROM   (SELECT accounting_unit_id ,"
									+ "     accounting_unit_name,     accounting_unit_office_id  FROM fas_mst_acct_units "
									+ "  WHERE accounting_unit_office_id IN     ( select CIRCLE_OFFICE_ID  from COM_MST_ALL_OFFICES_VIEW  where REGION_OFFICE_ID="+office_id
									+ "   )   )x LEFT OUTER JOIN  "
									+ " (SELECT OFFICE_ID, OFFICE_NAME FROM COM_MST_OFFICES   )y ON x.accounting_unit_office_id =y.OFFICE_ID"
									+ "   )b ON a.auid         =b.accounting_unit_id AND a.acoid =b.accounting_unit_office_id ) "
									+ "where accounting_unit_id is not null  ";
					ps1 = connection
							.prepareStatement(qry);
					
					rs = ps1.executeQuery();

				} else if (office_level_id.equals("HO")) {
					qry=" SELECT accounting_unit_id ,  accounting_unit_name,  "
									+ "accounting_unit_office_id,  OFFICE_NAME,to_char(UPDATED_DATE,'DD/MM/YYYY') as UPDATED_DATE FROM (((SELECT ACCOUNTING_UNIT_ID "
									+ "AS auid,  ACCOUNTING_FOR_OFFICE_ID AS acoid,  UPDATED_DATE FROM "
									+ "FAS_BUDGET_CLOSURE_ALLOCATION WHERE FINANCIAL_YEAR='"+FinancialYear+"' AND STATEMENT_NAME     ="+Statement_Name+" ) UNION   "
									+ "(SELECT ACCOUNTING_UNIT_ID AS auid,     ACCOUNTING_FOR_OFFICE_ID AS acoid,"
									+ "    UPDATED_DATE   FROM FAS_BUDGET_CLOSURE_ALLOCATION   WHERE FINANCIAL_YEAR        ='"+FinancialYear+"'"
									+ "  AND STATEMENT_NAME             ="+Statement_Name+"   AND ACCOUNTING_UNIT_ID      ="+cmbAcc_UnitCode+"   AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code
									+ "  ) )a   left outer join   (   SELECT accounting_unit_id ,   accounting_unit_name, "
									+ "  accounting_unit_office_id,   OFFICE_NAME FROM   (SELECT accounting_unit_id ,"
									+ "     accounting_unit_name,     accounting_unit_office_id  FROM fas_mst_acct_units "
									+ "  WHERE accounting_unit_office_id IN     ( SELECT to_number(REGION_OFFICE_ID,9999) AS id1 "
									+ "FROM COM_MST_ALL_OFFICES_VIEW     UNION     SELECT to_number(OFFICE_ID,9999) AS id1     "
									+ "FROM COM_MST_ALL_OFFICES_VIEW     WHERE OFFICE_LEVEL_ID='HO'     )   )x LEFT OUTER JOIN  "
									+ " (SELECT OFFICE_ID, OFFICE_NAME FROM COM_MST_OFFICES   )y ON x.accounting_unit_office_id =y.OFFICE_ID"
									+ "   )b ON a.auid         =b.accounting_unit_id AND a.acoid =b.accounting_unit_office_id  )where accounting_unit_id is not null ";

					ps1 = connection
							.prepareStatement(qry);
			
					rs = ps1.executeQuery();
				}
				System.out.println("qry >>>> "+qry);
				while (rs.next()) {
					xml = xml + "<Acc_UnitID>"
							+ rs.getInt("accounting_unit_id") + "</Acc_UnitID>";

					xml = xml + "<Acc_UnitName>"
							+ rs.getString("accounting_unit_name")
							+ "</Acc_UnitName>";

					xml = xml + "<Acc_OfficeID>"
							+ rs.getInt("accounting_unit_office_id")
							+ "</Acc_OfficeID>";

					xml = xml + "<Acc_OfficeName>"
							+ rs.getString("OFFICE_NAME") + "</Acc_OfficeName>";

					xml = xml + "<Freezed_Date>" + rs.getString("UPDATED_DATE")
							+ "</Freezed_Date>";
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
}