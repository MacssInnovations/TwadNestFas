package Servlets.FAS.FAS1.InterBankTransferSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class List_of_InterBankTransfer_MultipleBanks
 */
public class List_of_InterBankTransfer_MultipleBanks extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public List_of_InterBankTransfer_MultipleBanks() {
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
		/*
		 * Servlets.Security.classes.UserProfile empProfile =
		 * (Servlets.Security.classes.UserProfile) session
		 * .getAttribute("UserProfile"); int empid = empProfile.getEmployeeId();
		 * String empName = empProfile.getEmployeeName(); long l =
		 * System.currentTimeMillis(); Timestamp ts = new Timestamp(l);
		 */
		if (strCommand.equalsIgnoreCase("gettGrid")) {
			System.out.println("RK");
			xml = xml + "<response><command>gettGrid</command>";

			String cmbAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			String cmbOffice_code1 = request.getParameter("cmbOffice_code");
			String txtCB_Year1 = request.getParameter("txtCB_Year");
			String txtCB_Month1 = request.getParameter("txtCB_Month");
			String cmbStatus = request.getParameter("cmbStatus");

			String txtFromDate = request.getParameter("txtFromDate");
			String txtToDate = request.getParameter("txtToDate");

			int cmbAcc_UnitCode = Integer.parseInt(cmbAcc_UnitCode1);
			int cmbOffice_code = Integer.parseInt(cmbOffice_code1);
			int txtCB_Year = Integer.parseInt(txtCB_Year1);
			int txtCB_Month = Integer.parseInt(txtCB_Month1);

			String mtc70RegisterDate = null;
			String passOrderDate = null;
			String refDate = null;
			if (txtFromDate.equals("empty") && txtToDate.equals("empty")) {
				try {
					String su = "SELECT VOUCHER_NO,  DATE_OF_TRANSFER,  bk_br_city,  FROM_ACCOUNT_NO,  TOTAL_AMOUNT,  REF_NO,  REF_DATE,  PARTICULARS FROM  (SELECT VOUCHER_NO,    TO_CHAR(DATE_OF_TRANSFER,'DD/MM/YYYY') AS DATE_OF_TRANSFER,    CR_ACCOUNT_HEAD_CODE,    FROM_BANK_ID,    FROM_BRANCH_ID,    FROM_ACCOUNT_NO,    TOTAL_AMOUNT,    REF_NO,    TO_CHAR(REF_DATE,'DD/MM/YYYY') AS REF_DATE,    PARTICULARS  FROM FAS_INTER_BANK_MST  WHERE ACCOUNTING_UNIT_ID     =?  AND ACCOUNTING_FOR_OFFICE_ID =?  AND CASHBOOK_YEAR            =?  AND CASHBOOK_MONTH           =? and TRANSFER_STATUS = ?  )a LEFT OUTER JOIN  (SELECT curr.BANK_ID,    curr.BRANCH_ID,    curr.BANK_AC_NO,    curr.AC_HEAD_CODE,    bk.BANK_NAME    || '-'    || br.BRANCH_NAME    ||'-'    ||coalesce ( br.CITY_TOWN_NAME,'') AS bk_br_city  FROM FAS_OFFICE_BANK_AC_CURRENT curr,    FAS_MST_BANK_BRANCHES br ,    FAS_MST_BANKS bk  WHERE curr.STATUS          ='Y'  AND curr.ACCOUNTING_UNIT_ID=?  AND curr.MODULE_ID         ='MF010'  AND curr.CR_DR_TYPE        ='CR'  AND curr.BANK_ID           =br.BANK_ID  AND curr.BRANCH_ID         =br.BRANCH_ID  AND br.BANK_ID             =bk.BANK_ID  )c ON a.FROM_BANK_ID     = c.BANK_ID AND a.FROM_BRANCH_ID  = c.BRANCH_ID AND a.FROM_ACCOUNT_NO = c.BANK_AC_NO order by VOUCHER_NO";
                                        System.out.println(su);
					ps = connection.prepareStatement(su);
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setInt(3, txtCB_Year);
					ps.setInt(4, txtCB_Month);
					ps.setString(5, cmbStatus);
					ps.setInt(6, cmbAcc_UnitCode);
					rs = ps.executeQuery();
					while (rs.next()) {

						xml = xml + "<VOUCHER_NO>" + rs.getInt("VOUCHER_NO")+ "</VOUCHER_NO>";

						xml = xml + "<DATE_OF_TRANSFER>"+ rs.getString("DATE_OF_TRANSFER")+ "</DATE_OF_TRANSFER>";

						xml = xml + "<bk_br_city>" + rs.getString("bk_br_city")+ "</bk_br_city>";

						xml = xml + "<FROM_ACCOUNT_NO>"+ rs.getLong("FROM_ACCOUNT_NO")+ "</FROM_ACCOUNT_NO>";

						xml = xml + "<TOTAL_AMOUNT>"+ rs.getBigDecimal("TOTAL_AMOUNT")+ "</TOTAL_AMOUNT>";

						xml = xml + "<REF_NO>" + rs.getInt("REF_NO")+ "</REF_NO>";

						xml = xml + "<REF_DATE>" + rs.getString("REF_DATE")+ "</REF_DATE>";

						xml = xml + "<PARTICULARS>"+ rs.getString("PARTICULARS")+ "</PARTICULARS>";
					}
					xml = xml + "<flag>success</flag>";
					ps.close();
					rs.close();
				} catch (Exception e) {
				    xml = xml + "<flag>failure</flag>";
					// TODO: handle exception
					e.printStackTrace();
				}
			} else {
				java.sql.Date FromDate = null;
				java.util.GregorianCalendar c5;
				String[] sd5 = request.getParameter("txtFromDate").split("/");
				c5 = new java.util.GregorianCalendar(Integer.parseInt(sd5[2]),
						Integer.parseInt(sd5[1]) - 1, Integer.parseInt(sd5[0]));
				java.util.Date d5 = c5.getTime();
				FromDate = new Date(d5.getTime());

				java.sql.Date ToDate = null;
				java.util.GregorianCalendar c6;
				String[] sd6 = request.getParameter("txtToDate").split("/");
				c6 = new java.util.GregorianCalendar(Integer.parseInt(sd6[2]),
						Integer.parseInt(sd6[1]) - 1, Integer.parseInt(sd6[0]));
				java.util.Date d6 = c6.getTime();
				ToDate = new Date(d6.getTime());

				try {

					String su = "SELECT VOUCHER_NO,  DATE_OF_TRANSFER,  bk_br_city,  FROM_ACCOUNT_NO,  TOTAL_AMOUNT,  REF_NO,  REF_DATE,  PARTICULARS FROM  (SELECT VOUCHER_NO,    TO_CHAR(DATE_OF_TRANSFER,'DD/MM/YYYY') AS DATE_OF_TRANSFER,    CR_ACCOUNT_HEAD_CODE,    FROM_BANK_ID,    FROM_BRANCH_ID,    FROM_ACCOUNT_NO,    TOTAL_AMOUNT,    REF_NO,    TO_CHAR(REF_DATE,'DD/MM/YYYY') AS REF_DATE,    PARTICULARS  FROM FAS_INTER_BANK_MST  WHERE ACCOUNTING_UNIT_ID     =?  AND ACCOUNTING_FOR_OFFICE_ID =?  AND CASHBOOK_YEAR            =?  AND CASHBOOK_MONTH           =? and TRANSFER_STATUS = ? and DATE_OF_TRANSFER between ? and ?   )a LEFT OUTER JOIN  (SELECT curr.BANK_ID,    curr.BRANCH_ID,    curr.BANK_AC_NO,    curr.AC_HEAD_CODE,    bk.BANK_NAME    || '-'    || br.BRANCH_NAME    ||'-'    || coalesce (br.CITY_TOWN_NAME,'') AS bk_br_city  FROM FAS_OFFICE_BANK_AC_CURRENT curr,    FAS_MST_BANK_BRANCHES br ,    FAS_MST_BANKS bk  WHERE curr.STATUS          ='Y'  AND curr.ACCOUNTING_UNIT_ID=?  AND curr.MODULE_ID         ='MF010'  AND curr.CR_DR_TYPE        ='CR'  AND curr.BANK_ID           =br.BANK_ID  AND curr.BRANCH_ID         =br.BRANCH_ID  AND br.BANK_ID             =bk.BANK_ID  )c ON a.FROM_BANK_ID     = c.BANK_ID AND a.FROM_BRANCH_ID  = c.BRANCH_ID AND a.FROM_ACCOUNT_NO = c.BANK_AC_NO order by VOUCHER_NO";
                                        System.out.println(su);
					ps = connection.prepareStatement(su);
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setInt(3, txtCB_Year);
					ps.setInt(4, txtCB_Month);
					ps.setString(5, cmbStatus);
					ps.setDate(6, FromDate);
					ps.setDate(7, ToDate);
					ps.setInt(8, cmbAcc_UnitCode);
					rs = ps.executeQuery();
				    xml = xml + "<flag>success</flag>";
					while (rs.next()) {
						xml = xml + "<VOUCHER_NO>" + rs.getInt("VOUCHER_NO")+ "</VOUCHER_NO>";

						xml = xml + "<DATE_OF_TRANSFER>"+ rs.getString("DATE_OF_TRANSFER")+ "</DATE_OF_TRANSFER>";

						xml = xml + "<bk_br_city>" + rs.getString("bk_br_city")+ "</bk_br_city>";

						xml = xml + "<FROM_ACCOUNT_NO>"+ rs.getLong("FROM_ACCOUNT_NO")+ "</FROM_ACCOUNT_NO>";

						xml = xml + "<TOTAL_AMOUNT>"+ rs.getBigDecimal("TOTAL_AMOUNT")+ "</TOTAL_AMOUNT>";

						xml = xml + "<REF_NO>" + rs.getInt("REF_NO")+ "</REF_NO>";

						xml = xml + "<REF_DATE>" + rs.getString("REF_DATE")+ "</REF_DATE>";

						xml = xml + "<PARTICULARS>"+ rs.getString("PARTICULARS")+ "</PARTICULARS>";
					}
					
					ps.close();
					rs.close();
				} catch (Exception e) {
					xml = xml + "<flag>failure</flag>";
					e.printStackTrace();
				}
			}
		} else if (strCommand.equalsIgnoreCase("getVoucherDetails")) {

			xml = xml + "<response><command>getVoucherDetails</command>";

			String cmbAcc_UnitCode22 = request.getParameter("cboAcc_UnitCode");
			String cmbOffice_code22 = request.getParameter("cboOffice_code");
			String txtCB_Year22 = request.getParameter("cboCashBook_Year");
			String txtCB_Month22 = request.getParameter("cboCashBook_Month");
			String VOUCHER_NO22 = request.getParameter("VOUCHER_NO");

			int cmbAcc_UnitCode2 = Integer.parseInt(cmbAcc_UnitCode22);
			int cmbOffice_code2 = Integer.parseInt(cmbOffice_code22);
			int txtCB_Year2 = Integer.parseInt(txtCB_Year22);
			int txtCB_Month2 = Integer.parseInt(txtCB_Month22);
			int VOUCHER_NO2 = Integer.parseInt(VOUCHER_NO22);

			String billDate = null;
			String proceedingOrderDate = null;

			try {
				String su = "SELECT bk_br_city,  TO_ACCOUNT_NO,  DR_ACCOUNT_HEAD_CODE,  CHEQUE_OR_DD,  CHEQUE_DD_NO,  TO_CHAR(CHEQUE_DD_DATE) AS CHEQUE_DD_DATE,  TOTAL_AMOUNT,  REMARKS FROM  (SELECT DATE_OF_TRANSFER,    TOTAL_AMOUNT,    CHEQUE_OR_DD,    CHEQUE_DD_NO,    TO_CHAR(CHEQUE_DD_DATE,'DD/MM/YYYY') AS CHEQUE_DD_DATE,    DR_ACCOUNT_HEAD_CODE,    TO_ACCOUNT_NO,    TO_BANK_ID,    TO_BRANCH_ID,    REMARKS  FROM FAS_INTER_BANK_TRF  WHERE ACCOUNTING_UNIT_ID    =?  AND ACCOUNTING_FOR_OFFICE_ID=?  AND CASHBOOK_YEAR           =?  AND CASHBOOK_MONTH          =?  AND VOUCHER_NO              =?   )a LEFT OUTER JOIN  (SELECT curr.BANK_ID,    curr.BRANCH_ID,    curr.BANK_AC_NO,    curr.AC_HEAD_CODE,    bk.BANK_NAME    || '-'    || br.BRANCH_NAME    ||'-'    || coalesce (br.CITY_TOWN_NAME,'') AS bk_br_city  FROM FAS_OFFICE_BANK_AC_CURRENT curr,    FAS_MST_BANK_BRANCHES br ,    FAS_MST_BANKS bk  WHERE curr.STATUS          ='Y'  AND curr.ACCOUNTING_UNIT_ID=?  AND curr.MODULE_ID         ='MF010'  AND curr.CR_DR_TYPE        ='CR'  AND curr.BANK_ID           =br.BANK_ID  AND curr.BRANCH_ID         =br.BRANCH_ID  AND br.BANK_ID             =bk.BANK_ID  )c ON a.TO_BANK_ID     = c.BANK_ID AND a.TO_BRANCH_ID  = c.BRANCH_ID AND a.TO_ACCOUNT_NO = c.BANK_AC_NO";
				ps1 = connection.prepareStatement(su);
				ps1.setInt(1, cmbAcc_UnitCode2);
				ps1.setInt(2, cmbOffice_code2);
				ps1.setInt(3, txtCB_Year2);
				ps1.setInt(4, txtCB_Month2);
				ps1.setInt(5, VOUCHER_NO2);
				ps1.setInt(6, cmbAcc_UnitCode2);

				rs1 = ps1.executeQuery();
				while (rs1.next()) {
					xml = xml + "<bk_br_city>" + rs1.getString("bk_br_city")
							+ "</bk_br_city>";

					xml = xml + "<TO_ACCOUNT_NO>" + rs1.getInt("TO_ACCOUNT_NO")
							+ "</TO_ACCOUNT_NO>";

					xml = xml + "<DR_ACCOUNT_HEAD_CODE>"
							+ rs1.getInt("DR_ACCOUNT_HEAD_CODE")
							+ "</DR_ACCOUNT_HEAD_CODE>";

					xml = xml + "<CHEQUE_OR_DD>"
							+ rs1.getString("CHEQUE_OR_DD") + "</CHEQUE_OR_DD>";

					xml = xml + "<CHEQUE_DD_NO>" + rs1.getInt("CHEQUE_DD_NO")
							+ "</CHEQUE_DD_NO>";

					xml = xml + "<CHEQUE_DD_DATE>"
							+ rs1.getString("CHEQUE_DD_DATE")
							+ "</CHEQUE_DD_DATE>";

					xml = xml + "<TOTAL_AMOUNT>"
							+ rs1.getBigDecimal("TOTAL_AMOUNT")
							+ "</TOTAL_AMOUNT>";

					xml = xml + "<REMARKS>" + rs1.getString("REMARKS")
							+ "</REMARKS>";
				}
				xml = xml + "<flag>success</flag>";
				ps1.close();
				rs1.close();
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
