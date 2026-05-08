package Servlets.FAS.FAS1.InterBankTransferSystem.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

/**
 * Servlet implementation class InterBankTransfer_MultipleBanks
 */
public class InterBankTransfer_MultipleBanks extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public InterBankTransfer_MultipleBanks() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
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
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps = null;
		PreparedStatement ps5 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs = null;
		ResultSet rs5 = null;
		ResultSet rs6 = null;

		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

		String strCommand = "";

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

		/**
		 * Get Command Parameter
		 */
		try {
			strCommand = request.getParameter("Command");
			System.out.println("assign..here command..." + strCommand);
		} catch (Exception e) {
			System.out.println("Exception in assigning..." + e);
		}

		/**
		 * Variables Declaration
		 */
		int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtVoucher_No = 0, txtCash_Month_hid = 0, txtCash_year = 0;
		Date txtCrea_date = null;
		Calendar c;
                String txtBankAccountNo="";

		/** Get Accounting Unit ID */
		try {
			cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
		} catch (Exception e) {
			System.out.println("Exception to Read Reason Code ");
		}
		if (strCommand.equalsIgnoreCase("AddRow")) {

		    txtBankAccountNo = request.getParameter("txtBankAccountNo");

			String CONTENT_TYPE = "text/xml; charset=windows-12"
					+ cmbAcc_UnitCode + "2";
			response.setContentType(CONTENT_TYPE);
			String xml = "";
			xml = "<response><command>AddRow</command>";
			try {

				String sql3 = "select b.BANK_AC_NO, a.BANK_SHORT_NAME || '-' || b.BANK_AC_TYPE_ID || '-' || "
						+ "b.BANK_AC_NO as BANK_AC_NO_DISPLAY from FAS_MST_BANKS a,FAS_OFFICE_BANK_AC_CURRENT b "
						+ "where b.STATUS='Y' and b.BANK_ID=a.BANK_ID and ACCOUNTING_UNIT_ID=? and  MODULE_ID=? "
						+ "and  CR_DR_TYPE=? and b.BANK_AC_NO not in ('"+txtBankAccountNo+"') order by a.BANK_SHORT_NAME";

				ps3 = con.prepareStatement(sql3);
				ps3.setInt(1, cmbAcc_UnitCode);
				ps3.setString(2, "MF010");
				ps3.setString(3, "DR");

				rs3 = ps3.executeQuery();

				while (rs3.next()) {
					xml = xml + "<trans_pair>";
					xml = xml + "<trans_code>" + rs3.getLong("BANK_AC_NO")
							+ "</trans_code>";
					xml = xml + "<trans_desc>"
							+ rs3.getString("BANK_AC_NO_DISPLAY")
							+ "</trans_desc>";
					xml = xml + "</trans_pair>";
				}
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

			xml = xml + "</response>";
			System.out.println(xml);
			out.write(xml);
		} else if (strCommand.equalsIgnoreCase("load_bank_deatils")) {
			String CONTENT_TYPE = "text/xml; charset=windows-1252";
			response.setContentType(CONTENT_TYPE);
			String xml = "";
			xml = "<response><command>load_bank_deatils</command>";
			xml = xml + "<seq>" + request.getParameter("seq") + "</seq>";
			long bank_acc_no = 0;
			String cr_dr_indicator = "";

			try {
				bank_acc_no = Long.parseLong(request
						.getParameter("bank_acc_no"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			System.out.println("bank_acc_no " + bank_acc_no);

			cr_dr_indicator = request.getParameter("cr_dr_indicator");

			String sql_bank = "select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || coalesce (br.CITY_TOWN_NAME,'') as bk_br_city "
					+ " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID=? and curr.BANK_AC_NO=?  and curr.MODULE_ID=? and curr.CR_DR_TYPE=? "
					+ " and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID";
			try {
				ps = con.prepareStatement(sql_bank);
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setLong(2, bank_acc_no);
				ps.setString(3, "MF010");
				ps.setString(4, cr_dr_indicator);
				rs = ps.executeQuery();

				xml = xml + "<cr_dr_indicator>" + cr_dr_indicator
						+ "</cr_dr_indicator>";

				if (rs.next()) {
					xml = xml + "<flag>success</flag>";
					xml = xml + "<BANK_ID>" + rs.getInt("BANK_ID")
							+ "</BANK_ID>";
					xml = xml + "<bk_br_city>" + rs.getString("bk_br_city")
							+ "</bk_br_city>";
					xml = xml + "<BRANCH_ID>" + rs.getInt("BRANCH_ID")
							+ "</BRANCH_ID>";
					xml = xml + "<AC_HEAD_CODE>" + rs.getInt("AC_HEAD_CODE")
							+ "</AC_HEAD_CODE>";
				} else
					xml = xml + "<flag>failure</flag>";
				ps.close();
				rs.close();

			} catch (Exception e) {
				System.out
						.println("Finding Branch failed due to exception" + e);
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
			System.out.println(xml);
			out.println(xml);
		} else if (strCommand.equalsIgnoreCase("load_Voucher_details")) {
			String CONTENT_TYPE = "text/xml; charset=windows-1252";
			response.setContentType(CONTENT_TYPE);
			String xml = "";
			xml = "<response><command>load_Voucher_details</command>";

			/** Get cmbOffice_code */
			try {
				cmbOffice_code = Integer.parseInt(request
						.getParameter("cmbOffice_code"));
			} catch (Exception e) {
				System.out.println("Exception to cmbOffice_code ");
			}

			/** Get Voucher Number */
			try {
				txtVoucher_No = Integer.parseInt(request
						.getParameter("txtVoucher_No"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}

			/** Get Date */
			try {
				String[] sd = request.getParameter("txtCrea_date").split("/");
				c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer
						.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
				java.util.Date d = c.getTime();
				txtCrea_date = new Date(d.getTime());
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}

			/** Get Receipt Creation Date */
			String Receipt_Creation_Date = request.getParameter("txtCrea_date");

			/**
			 * Call Com_CashBook Servlet for Calculating Cash Book Month and
			 * Year
			 */
			Com_CashBook1 cb = new Com_CashBook1();

			/** Assign Cashbook Year and Month to year_month Variable */
			String year_month = cb.cb_date(Receipt_Creation_Date).toString();

			/** Split Cash Book Year and Month */
			String[] ym = year_month.split("/");

			/** Assign Year and Month */
			txtCash_year = Integer.parseInt(ym[0]);
			txtCash_Month_hid = Integer.parseInt(ym[1]);

			try {

				String sql3 = "select b.BANK_AC_NO, a.BANK_SHORT_NAME || '-' || b.BANK_AC_TYPE_ID || '-' || "
						+ "b.BANK_AC_NO as BANK_AC_NO_DISPLAY from FAS_MST_BANKS a,FAS_OFFICE_BANK_AC_CURRENT b "
						+ "where b.STATUS='Y' and b.BANK_ID=a.BANK_ID and ACCOUNTING_UNIT_ID=? and  MODULE_ID=? "
						+ "and  CR_DR_TYPE=?";

				ps3 = con.prepareStatement(sql3);
				ps3.setInt(1, cmbAcc_UnitCode);
				ps3.setString(2, "MF010");
				ps3.setString(3, "DR");

				rs3 = ps3.executeQuery();

				while (rs3.next()) {
					xml = xml + "<trans_pair>";
					xml = xml + "<trans_code>" + rs3.getLong("BANK_AC_NO")
							+ "</trans_code>";
					xml = xml + "<trans_desc>"
							+ rs3.getString("BANK_AC_NO_DISPLAY")
							+ "</trans_desc>";
					xml = xml + "</trans_pair>";
				}
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

			try {
				ps = con
						.prepareStatement("SELECT CR_ACCOUNT_HEAD_CODE,  FROM_BANK_ID,  FROM_BRANCH_ID,  "
								+ "FROM_ACCOUNT_NO,  bk_br_city,  REF_NO,  to_char(REF_DATE,'DD/MM/YYY')as "
								+ "REF_DATE,  PARTICULARS,  TOTAL_AMOUNT,  CHEQUE_OR_DD,  CHEQUE_DD_NO,  "
								+ "to_char(CHEQUE_DD_DATE,'DD/MM/YYY')as CHEQUE_DD_DATE,   DR_ACCOUNT_HEAD_CODE,  "
								+ "TO_BANK_ID,  TO_BRANCH_ID,  TO_ACCOUNT_NO,  bk_br_city1,  SL_NO,  REMARKS,  "
								+ "TOTAL_AMOUNT1 FROM  (SELECT VOUCHER_NO,    CR_ACCOUNT_HEAD_CODE,    "
								+ "FROM_BANK_ID,    FROM_BRANCH_ID,    FROM_ACCOUNT_NO,    REF_NO,    REF_DATE,"
								+ "    PARTICULARS,    TOTAL_AMOUNT  FROM FAS_INTER_BANK_MST  WHERE "
								+ "ACCOUNTING_UNIT_ID     =?  AND ACCOUNTING_FOR_OFFICE_ID =?  AND "
								+ "DATE_OF_TRANSFER         =?  AND CASHBOOK_YEAR            =?  AND "
								+ "CASHBOOK_MONTH           =?  AND VOUCHER_NO               =?   )a "
								+ "LEFT OUTER JOIN  (SELECT VOUCHER_NO AS vno,    TOTAL_AMOUNT     AS "
								+ "TOTAL_AMOUNT1,    CHEQUE_OR_DD,    CHEQUE_DD_NO,    CHEQUE_DD_DATE,   "
								+ " DR_ACCOUNT_HEAD_CODE,    TO_BANK_ID,    TO_BRANCH_ID,    TO_ACCOUNT_NO,    "
								+ "SL_NO,    REMARKS  FROM FAS_INTER_BANK_TRF  WHERE ACCOUNTING_UNIT_ID     =? "
								+ " AND ACCOUNTING_FOR_OFFICE_ID =?  AND DATE_OF_TRANSFER         =?  AND "
								+ "CASHBOOK_YEAR            =?  AND CASHBOOK_MONTH           =?  AND VOUCHER_NO "
								+ "              =?   )b ON a.VOUCHER_NO =b.vno LEFT OUTER JOIN  "
								+ "(SELECT curr.BANK_ID,    curr.BRANCH_ID,    curr.BANK_AC_NO,    "
								+ "curr.AC_HEAD_CODE,    bk.BANK_NAME    || '-'    || br.BRANCH_NAME "
								+ "   ||'-'    || coalesce (br.CITY_TOWN_NAME,'') AS bk_br_city  FROM "
								+ "FAS_OFFICE_BANK_AC_CURRENT curr,    FAS_MST_BANK_BRANCHES br ,    "
								+ "FAS_MST_BANKS bk  WHERE curr.STATUS          ='Y'  AND "
								+ "curr.ACCOUNTING_UNIT_ID=?  AND curr.MODULE_ID         ='MF010'  AND "
								+ "curr.CR_DR_TYPE        ='CR'  AND curr.BANK_ID           =br.BANK_ID "
								+ " AND curr.BRANCH_ID         =br.BRANCH_ID   AND "
								+ "br.BANK_ID             =bk.BANK_ID   )c ON a.FROM_BANK_ID     = c.BANK_ID"
								+ " AND a.FROM_BRANCH_ID  = c.BRANCH_ID AND a.FROM_ACCOUNT_NO = c.BANK_AC_NO  "
								+ "left outer join (SELECT curr.BANK_ID,    curr.BRANCH_ID,    curr.BANK_AC_NO, "
								+ "   curr.AC_HEAD_CODE,    bk.BANK_NAME    || '-'    || br.BRANCH_NAME   "
								+ " ||'-'    || coalesce (br.CITY_TOWN_NAME,'') AS bk_br_city1  FROM "
								+ "FAS_OFFICE_BANK_AC_CURRENT curr,    FAS_MST_BANK_BRANCHES br ,    "
								+ "FAS_MST_BANKS bk  WHERE curr.STATUS          ='Y'  AND "
								+ "curr.ACCOUNTING_UNIT_ID=?  AND curr.MODULE_ID         ='MF010'  AND "
								+ "curr.CR_DR_TYPE        ='DR'  AND curr.BANK_ID           =br.BANK_ID  AND"
								+ " curr.BRANCH_ID         =br.BRANCH_ID  AND "
								+ "br.BANK_ID             =bk.BANK_ID   )d   ON "
								+ "b.TO_BANK_ID     = d.BANK_ID AND b.TO_BRANCH_ID  = d.BRANCH_ID AND"
								+ " b.TO_ACCOUNT_NO = d.BANK_AC_NO");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setDate(3, txtCrea_date);
				ps.setInt(4, txtCash_year);
				ps.setInt(5, txtCash_Month_hid);
				ps.setInt(6, txtVoucher_No);
				ps.setInt(7, cmbAcc_UnitCode);
				ps.setInt(8, cmbOffice_code);
				ps.setDate(9, txtCrea_date);
				ps.setInt(10, txtCash_year);
				ps.setInt(11, txtCash_Month_hid);
				ps.setInt(12, txtVoucher_No);
				ps.setInt(13, cmbAcc_UnitCode);
				ps.setInt(14, cmbAcc_UnitCode);
				rs = ps.executeQuery();
				while (rs.next()) {
					xml = xml + "<CR_ACCOUNT_HEAD_CODE>"
							+ rs.getInt("CR_ACCOUNT_HEAD_CODE")
							+ "</CR_ACCOUNT_HEAD_CODE>";
					xml = xml + "<FROM_BANK_ID>" + rs.getInt("FROM_BANK_ID")
							+ "</FROM_BANK_ID>";
					xml = xml + "<FROM_BRANCH_ID>"
							+ rs.getInt("FROM_BRANCH_ID") + "</FROM_BRANCH_ID>";
					xml = xml + "<FROM_ACCOUNT_NO>"
							+ rs.getInt("FROM_ACCOUNT_NO")
							+ "</FROM_ACCOUNT_NO>";

					xml = xml + "<bk_br_city>" + rs.getString("bk_br_city")
							+ "</bk_br_city>";
					xml = xml + "<REF_NO>" + rs.getInt("REF_NO") + "</REF_NO>";
					xml = xml + "<REF_DATE>" + rs.getString("REF_DATE")
							+ "</REF_DATE>";
					xml = xml + "<PARTICULARS>" + rs.getString("PARTICULARS")
							+ "</PARTICULARS>";

					xml = xml + "<TOTAL_AMOUNT>"
							+ rs.getBigDecimal("TOTAL_AMOUNT")
							+ "</TOTAL_AMOUNT>";
					xml = xml + "<CHEQUE_OR_DD>" + rs.getString("CHEQUE_OR_DD")
							+ "</CHEQUE_OR_DD>";
					xml = xml + "<CHEQUE_DD_NO>" + rs.getInt("CHEQUE_DD_NO")
							+ "</CHEQUE_DD_NO>";
					xml = xml + "<CHEQUE_DD_DATE>"
							+ rs.getString("CHEQUE_DD_DATE")
							+ "</CHEQUE_DD_DATE>";

					xml = xml + "<DR_ACCOUNT_HEAD_CODE>"
							+ rs.getString("DR_ACCOUNT_HEAD_CODE")
							+ "</DR_ACCOUNT_HEAD_CODE>";
					xml = xml + "<TO_BANK_ID>" + rs.getInt("TO_BANK_ID")
							+ "</TO_BANK_ID>";
					xml = xml + "<TO_BRANCH_ID>" + rs.getInt("TO_BRANCH_ID")
							+ "</TO_BRANCH_ID>";
					xml = xml + "<TO_ACCOUNT_NO>" + rs.getInt("TO_ACCOUNT_NO")
							+ "</TO_ACCOUNT_NO>";
					xml = xml + "<bk_br_city1>" + rs.getString("bk_br_city1")
							+ "</bk_br_city1>";

					xml = xml + "<SL_NO>" + rs.getInt("SL_NO") + "</SL_NO>";
					xml = xml + "<REMARKS>" + rs.getString("REMARKS")
							+ "</REMARKS>";
					xml = xml + "<TOTAL_AMOUNT1>"
							+ rs.getBigDecimal("TOTAL_AMOUNT1")
							+ "</TOTAL_AMOUNT1>";
				}
				xml = xml + "<flag1>success</flag1>";
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag1>failure</flag1>";
			}
			xml = xml + "</response>";
			System.out.println(xml);
			out.println(xml);
		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
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
		Connection con = null;
		ResultSet rs = null,rs2=null;
		CallableStatement cs = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		String xml = "";
		String strCommand = "";
		int s = 0, bg = 0;
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
			// sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

		}
		try {

			strCommand = request.getParameter("filter");
			System.out.println("assign..here command..." + strCommand);
		}

		catch (Exception e) {
			System.out.println("Exception in assigning..." + e);
		}

		int RecordCount = Integer.parseInt(request.getParameter("RecordCount"));

		if (strCommand.equalsIgnoreCase("save")) {
			String CONTENT_TYPE = "text/html; charset=windows-1252";
			response.setContentType(CONTENT_TYPE);
			xml = "<response><command>Add</command>";
			Calendar c;
			// HO details
			int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid = 0, txtCash_year = 0;
			int txtCash_Acc_code = 0, txtBankId = 0, txtBranchId = 0;
			int txtDebitAccCode = 0, txtSubBankId = 0, txtSubBranchId = 0;
			long txtSubBankAccountNo = 0;
			long txtBankAccountNo = 0;
			String txtCheque_DD = null, txtCheque_DD_NO = null;
			Date txtCheque_DD_date = null;
			double txtTotalAmount = 0;
			double txtAmount = 0;
			String txtRemarks = "";
			String txtRemarks1 = "";
			Date txtCrea_date = null, txtReferenceDate = null;
			String txtReferenceNo = "";
			String update_user = (String) session.getAttribute("UserId");

			long l = System.currentTimeMillis();
			Timestamp ts = new Timestamp(l);

			try {
				txtCash_Acc_code = Integer.parseInt(request
						.getParameter("txtCash_Acc_code"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			try {
				txtBankId = Integer.parseInt(request.getParameter("txtBankId"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			try {
				txtBranchId = Integer.parseInt(request
						.getParameter("txtBranchId"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			try {
				txtBankAccountNo = Long.parseLong(request
						.getParameter("txtBankAccountNo"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			try {
				cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("cmbAcc_UnitCode"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			try {
				cmbOffice_code = Integer.parseInt(request
						.getParameter("cmbOffice_code"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			String[] sd = request.getParameter("txtCrea_date").split("/");
			c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer
					.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c.getTime();
			txtCrea_date = new Date(d.getTime());

			try {
				txtTotalAmount = Double.parseDouble(request
						.getParameter("txtTotalAmount"));
			} catch (Exception e) {
				System.out.println("exception" + e);
			}

			txtRemarks = request.getParameter("txtRemarks");

			txtReferenceNo = request.getParameter("txtReferenceNo");

			if (!request.getParameter("txtReferenceDate").equalsIgnoreCase("")) {
				sd = request.getParameter("txtReferenceDate").split("/");
				c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer
						.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
				d = c.getTime();
				txtReferenceDate = new Date(d.getTime());
			}
			/** Get Receipt Creation Date */
			String Receipt_Creation_Date = request.getParameter("txtCrea_date");

			/**
			 * Call Com_CashBook Servlet for Calculating Cash Book Month and
			 * Year
			 */
			Com_CashBook1 cb = new Com_CashBook1();

			/** Assign Cashbook Year and Month to year_month Variable */
			String year_month = cb.cb_date(Receipt_Creation_Date).toString();

			/** Split Cash Book Year and Month */
			String[] ym = year_month.split("/");

			/** Assign Year and Month */
			txtCash_year = Integer.parseInt(ym[0]);
			txtCash_Month_hid = Integer.parseInt(ym[1]);

			int i = 1, Originated_SL_No = 0,vou1=0,vou2=0,count=0;
                        
                        

			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

		/*	try {
				con.clearWarnings();
				con.setAutoCommit(false);
				ps = con
						.prepareStatement("insert into FAS_INTER_BANK_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,DATE_OF_TRANSFER,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,CR_ACCOUNT_HEAD_CODE,FROM_BANK_ID,FROM_BRANCH_ID,FROM_ACCOUNT_NO,REF_NO,REF_DATE,PARTICULARS,TRANSFER_STATUS,UPDATED_BY_USER_ID,UPDATED_DATE,TOTAL_AMOUNT) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setDate(3, txtCrea_date);
				ps.setInt(4, txtCash_year);
				ps.setInt(5, txtCash_Month_hid);
				ps.setInt(6, i);
				ps.setInt(7, txtCash_Acc_code);
				ps.setInt(8, txtBankId);
				ps.setInt(9, txtBranchId);
				ps.setLong(10, txtBankAccountNo);
				ps.setString(11, txtReferenceNo);
				ps.setDate(12, txtReferenceDate);
				ps.setString(13, txtRemarks);
				ps.setString(14, "L");
				ps.setString(15, update_user);
				ps.setTimestamp(16, ts);
				ps.setDouble(17, txtTotalAmount);

				s = ps.executeUpdate();
			} catch (Exception e) {
				try {
					con.rollback();
					con.commit();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}  */

			for (int k = 0; k < RecordCount; k++) {
				try {
					txtDebitAccCode = Integer.parseInt(request
							.getParameter("txtDebitAccCode" + k));
				} catch (NumberFormatException e) {
					System.out.println("exception" + e);
				}
				try {
					txtSubBankId = Integer.parseInt(request
							.getParameter("txtSubBankId" + k));
				} catch (NumberFormatException e) {
					System.out.println("exception" + e);
				}
				try {
					txtSubBranchId = Integer.parseInt(request
							.getParameter("txtSubBranchId" + k));
				} catch (NumberFormatException e) {
					System.out.println("exception" + e);
				}
				try {
					txtSubBankAccountNo = Long.parseLong(request
							.getParameter("txtSubBankAccountNo" + k));
				} catch (NumberFormatException e) {
					System.out.println("exception" + e);
				}
				try {
					txtAmount = Double.parseDouble(request
							.getParameter("txtAmount" + k));
				} catch (NumberFormatException e) {
					System.out.println("exception" + e);
				}

				//txtCheque_DD = request.getParameter("txtCheque_DD");
				 txtCheque_DD = request.getParameter("txtCheque_DD" + k);
				 System.out.println("txtCheque_DD"+txtCheque_DD);
			   
				txtCheque_DD_NO = request.getParameter("txtCheque_DD_NO" + k);
			  //  System.out.println("txtCheque_DD**********************"+txtCheque_DD);
                            
			

				if (!request.getParameter("txtCheque_DD_date" + k)
						.equalsIgnoreCase("")) {
					sd = request.getParameter("txtCheque_DD_date" + k).split(
							"/");
					c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer
							.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
					d = c.getTime();
					txtCheque_DD_date = new Date(d.getTime());
				}
				txtRemarks1 = request.getParameter("Remarks" + k);
                                
			    try {
			       String sql=" select max(VOUCHER_NO)as vno from FAS_INTER_BANK_TRF_AT_HO where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and" +
			                              " ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_MONTH="+txtCash_Month_hid+" and " +
			                              " CASHBOOK_YEAR="+txtCash_year;
			          System.out.println("sql::::::"+sql);
                                  ps = con.prepareStatement(sql);
			            rs = ps.executeQuery();

			        if(rs.next()) 
			        {
			            Originated_SL_No = rs.getInt(1);                                               
			        }
			        Originated_SL_No=Originated_SL_No+1;
			        rs.close();
			    } catch (Exception e) {
			            e.printStackTrace();
			    }
                                
				try {
                                
					ps1 = con.prepareStatement("insert into FAS_INTER_BANK_TRF_AT_HO(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," +
                                        "DATE_OF_TRANSFER,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,CR_ACCOUNT_HEAD_CODE,FROM_BANK_ID,FROM_BRANCH_ID,FROM_ACCOUNT_NO," +
                                        "TOTAL_AMOUNT,CHEQUE_OR_DD,CHEQUE_DD_NO,CHEQUE_DD_DATE,DR_ACCOUNT_HEAD_CODE,TO_BANK_ID," +
                                        "TO_BRANCH_ID,TO_ACCOUNT_NO,REF_NO,REF_DATE,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE,TRANSFER_STATUS) " +
                                        "values (?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?,?,?,?) ");
					ps1.setInt(1, cmbAcc_UnitCode);
					ps1.setInt(2, cmbOffice_code);
					ps1.setDate(3, txtCrea_date);
					ps1.setInt(4, txtCash_year);
					ps1.setInt(5, txtCash_Month_hid);
					ps1.setInt(6, Originated_SL_No);
                                        ps1.setInt(7, txtCash_Acc_code);
                                        ps1.setInt(8, txtBankId);
                                        ps1.setInt(9, txtBranchId);
                                        ps1.setLong(10, txtBankAccountNo);
                                       	ps1.setDouble(11, txtAmount);
                                        
					ps1.setString(12, txtCheque_DD);
					ps1.setString(13, txtCheque_DD_NO);
					ps1.setDate(14, txtCheque_DD_date);
                                        
					ps1.setInt(15, txtDebitAccCode);
					ps1.setInt(16, txtSubBankId);
					ps1.setInt(17, txtSubBranchId);
					ps1.setLong(18, txtSubBankAccountNo);
                                        ps1.setString(19, txtReferenceNo);
                                        ps1.setDate(20, txtReferenceDate);
                                        ps1.setString(21, txtRemarks1);
                                        
					ps1.setString(22, update_user);
					ps1.setTimestamp(23, ts);
                                        ps1.setString(24,"L");
					
					ps1.executeUpdate();
					bg++;

				} catch (Exception e) {
					try {
						con.rollback();
						con.commit();
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
					e.printStackTrace();
				}
			}
			try {
				if (bg == RecordCount) {
					//con.commit();
					sendMessage(
							response,
							"The InterBank Transfer Transaction Created Successfully ",
							"ok");
				} else {
					con.rollback();
					sendMessage(response,
							"The InterBank Transfer TransactionFailed ", "ok");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}
		} else if (strCommand.equalsIgnoreCase("update")) {
			String CONTENT_TYPE = "text/html; charset=windows-1252";
			response.setContentType(CONTENT_TYPE);
			xml = "<response><command>Add</command>";
			Calendar c;
			String check[] = new String[RecordCount];
			String slno[] = new String[RecordCount];
			int check2 = 0;
			int slno2 = 0;
			// HO details
			int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid = 0, txtCash_year = 0, txtVoucher_No = 0;
			int txtCash_Acc_code = 0, txtBankId = 0, txtBranchId = 0;
			int txtDebitAccCode = 0, txtSubBankId = 0, txtSubBranchId = 0;
			long txtSubBankAccountNo = 0;
			long txtBankAccountNo = 0;
			String txtCheque_DD = null, txtCheque_DD_NO = null;
			Date txtCheque_DD_date = null;
			double txtTotalAmount = 0;
			double txtAmount = 0;
			String txtRemarks = "";
			String txtRemarks1 = "";
			Date txtCrea_date = null, txtReferenceDate = null;
			String txtReferenceNo = "";
			String update_user = (String) session.getAttribute("UserId");

			long l = System.currentTimeMillis();
			Timestamp ts = new Timestamp(l);

			try {
				txtCash_Acc_code = Integer.parseInt(request
						.getParameter("txtCash_Acc_code"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			try {
				txtBankId = Integer.parseInt(request.getParameter("txtBankId"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			try {
				txtBranchId = Integer.parseInt(request
						.getParameter("txtBranchId"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			try {
				txtBankAccountNo = Long.parseLong(request
						.getParameter("txtBankAccountNo"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			try {
				cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("cmbAcc_UnitCode"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			try {
				cmbOffice_code = Integer.parseInt(request
						.getParameter("cmbOffice_code"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			String[] sd = request.getParameter("txtCrea_date").split("/");
			c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer
					.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c.getTime();
			txtCrea_date = new Date(d.getTime());

			try {
				txtTotalAmount = Double.parseDouble(request
						.getParameter("txtTotalAmount"));
			} catch (Exception e) {
				System.out.println("exception" + e);
			}

			txtRemarks = request.getParameter("txtRemarks");

			txtReferenceNo = request.getParameter("txtReferenceNo");

			if (!request.getParameter("txtReferenceDate").equalsIgnoreCase("")) {
				sd = request.getParameter("txtReferenceDate").split("/");
				c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer
						.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
				d = c.getTime();
				txtReferenceDate = new Date(d.getTime());
			}
			/** Get Receipt Creation Date */
			String Receipt_Creation_Date = request.getParameter("txtCrea_date");

			/**
			 * Call Com_CashBook Servlet for Calculating Cash Book Month and
			 * Year
			 */
			Com_CashBook1 cb = new Com_CashBook1();

			/** Assign Cashbook Year and Month to year_month Variable */
			String year_month = cb.cb_date(Receipt_Creation_Date).toString();

			/** Split Cash Book Year and Month */
			String[] ym = year_month.split("/");

			/** Assign Year and Month */
			txtCash_year = Integer.parseInt(ym[0]);
			txtCash_Month_hid = Integer.parseInt(ym[1]);

			try {
				txtVoucher_No = Integer.parseInt(request
						.getParameter("txtVoucher_No"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}

			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

			/* try {
				con.clearWarnings();
				con.setAutoCommit(false);
				ps = con.prepareStatement("update FAS_INTER_BANK_MST set CR_ACCOUNT_HEAD_CODE=?,FROM_BANK_ID=?,FROM_BRANCH_ID=?,FROM_ACCOUNT_NO=?,REF_NO=?,REF_DATE=?,PARTICULARS=?,TRANSFER_STATUS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,TOTAL_AMOUNT=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and DATE_OF_TRANSFER=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?");

				ps.setInt(1, txtCash_Acc_code);
				ps.setInt(2, txtBankId);
				ps.setInt(3, txtBranchId);
				ps.setLong(4, txtBankAccountNo);
				ps.setString(5, txtReferenceNo);
				ps.setDate(6, txtReferenceDate);
				ps.setString(7, txtRemarks);
				ps.setString(8, "L");
				ps.setString(9, update_user);
				ps.setTimestamp(10, ts);
				ps.setDouble(11, txtTotalAmount);
				ps.setInt(12, cmbAcc_UnitCode);
				ps.setInt(13, cmbOffice_code);
				ps.setDate(14, txtCrea_date);
				ps.setInt(15, txtCash_year);
				ps.setInt(16, txtCash_Month_hid);
				ps.setInt(17, txtVoucher_No);

				s = ps.executeUpdate();
			} catch (Exception e) {
				try {
					con.rollback();
					con.commit();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
				e.printStackTrace();
			}  */

			try {
				ps = con.prepareStatement("delete from FAS_INTER_BANK_TRF_AT_HO where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and DATE_OF_TRANSFER=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setDate(3, txtCrea_date);
				ps.setInt(4, txtCash_year);
				ps.setInt(5, txtCash_Month_hid);
				ps.setInt(6, txtVoucher_No);
				ps.executeUpdate();
			} catch (Exception e) {
				e.printStackTrace();
				try {
					con.rollback();
					con.commit();
				} catch (SQLException e1) {
					e1.printStackTrace();
				}
			}
			for (int k = 0; k < RecordCount; k++) {
				try {
					txtDebitAccCode = Integer.parseInt(request
							.getParameter("txtDebitAccCode" + k));
				} catch (NumberFormatException e) {
					System.out.println("exception" + e);
				}
				try {
					txtSubBankId = Integer.parseInt(request
							.getParameter("txtSubBankId" + k));
				} catch (NumberFormatException e) {
					System.out.println("exception" + e);
				}
				try {
					txtSubBranchId = Integer.parseInt(request
							.getParameter("txtSubBranchId" + k));
				} catch (NumberFormatException e) {
					System.out.println("exception" + e);
				}
				try {
					txtSubBankAccountNo = Long.parseLong(request
							.getParameter("txtSubBankAccountNo" + k));
				} catch (NumberFormatException e) {
					System.out.println("exception" + e);
				}
				try {
					txtAmount = Double.parseDouble(request
							.getParameter("txtAmount" + k));
				} catch (NumberFormatException e) {
					System.out.println("exception" + e);
				}

				txtCheque_DD = request.getParameter("txtCheque_DD" + k);
                                System.out.println("txtCheque_DD"+txtCheque_DD);

				txtCheque_DD_NO = request.getParameter("txtCheque_DD_NO" + k);

				if (!request.getParameter("txtCheque_DD_date" + k)
						.equalsIgnoreCase("")) {
					sd = request.getParameter("txtCheque_DD_date" + k).split(
							"/");
					c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer
							.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
					d = c.getTime();
					txtCheque_DD_date = new Date(d.getTime());
				}
				txtRemarks1 = request.getParameter("Remarks" + k);

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
					System.out.println("Error for getting Head_of_Account -->"
							+ e);
				}

				/* Check Box */
				try {
					check[k] = request.getParameter("slno_db1" + k);
					check2 = Integer.parseInt(check[k]);

				} catch (Exception e) {
					System.out.println("Error for getting Head_of_Account -->"
							+ e);
				}
				System.out
						.println("***********************************************************************"
								+ check2);
				if (k == check2) {
					System.out.println("Update");
					System.out
							.println("check2------------------------>>>>>>>>>>>>>>>>."
									+ check2);
				    try {
				    
				            ps1 = con.prepareStatement("insert into FAS_INTER_BANK_TRF_AT_HO(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," +
				            "DATE_OF_TRANSFER,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,CR_ACCOUNT_HEAD_CODE,FROM_BANK_ID,FROM_BRANCH_ID,FROM_ACCOUNT_NO," +
				            "TOTAL_AMOUNT,CHEQUE_OR_DD,CHEQUE_DD_NO,CHEQUE_DD_DATE,DR_ACCOUNT_HEAD_CODE,TO_BANK_ID," +
				            "TO_BRANCH_ID,TO_ACCOUNT_NO,REF_NO,REF_DATE,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE,TRANSFER_STATUS) " +
				            "values (?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?,?,?,?) ");
				            ps1.setInt(1, cmbAcc_UnitCode);
				            ps1.setInt(2, cmbOffice_code);
				            ps1.setDate(3, txtCrea_date);
				            ps1.setInt(4, txtCash_year);
				            ps1.setInt(5, txtCash_Month_hid);
				            ps1.setInt(6, txtVoucher_No);
				            ps1.setInt(7, txtCash_Acc_code);
				            ps1.setInt(8, txtBankId);
				            ps1.setInt(9, txtBranchId);
				            ps1.setLong(10, txtBankAccountNo);
				            ps1.setDouble(11, txtAmount);
				            
				            ps1.setString(12, txtCheque_DD);
				            ps1.setString(13, txtCheque_DD_NO);
				            ps1.setDate(14, txtCheque_DD_date);
				            
				            ps1.setInt(15, txtDebitAccCode);
				            ps1.setInt(16, txtSubBankId);
				            ps1.setInt(17, txtSubBranchId);
				            ps1.setLong(18, txtSubBankAccountNo);
				            ps1.setString(19, txtReferenceNo);
				            ps1.setDate(20, txtReferenceDate);
				            ps1.setString(21, txtRemarks1);
				            
				            ps1.setString(22, update_user);
				            ps1.setTimestamp(23, ts);
				            ps1.setString(24,"L");
				            
				            ps1.executeUpdate();
				            bg++;

				    } catch (Exception e) {
				            try {
				                    con.rollback();
				                    con.commit();
				            } catch (SQLException e1) {
				                    e1.printStackTrace();
				            }
				            e.printStackTrace();
				    }
				}

			}

			try {
				if ((s > 0)) {
					con.commit();
					sendMessage(
							response,
							"The InterBank Transfer Transaction Updated Successfully ",
							"ok");
				} else {
					con.rollback();
					sendMessage(
							response,
							"The InterBank Transfer Transaction Updation Failed ",
							"ok");
				}
			} catch (SQLException e) {
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("cancel")) {
			String CONTENT_TYPE = "text/html; charset=windows-1252";
			response.setContentType(CONTENT_TYPE);
			int cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid = 0, txtCash_year = 0, txtVoucher_No = 0;
			Date txtCrea_date = null;
			Calendar c;

			try {
				cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("cmbAcc_UnitCode"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			try {
				cmbOffice_code = Integer.parseInt(request
						.getParameter("cmbOffice_code"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			String[] sd = request.getParameter("txtCrea_date").split("/");
			c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer
					.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c.getTime();
			txtCrea_date = new Date(d.getTime());

			/** Get Receipt Creation Date */
			String Receipt_Creation_Date = request.getParameter("txtCrea_date");

			/**
			 * Call Com_CashBook Servlet for Calculating Cash Book Month and
			 * Year
			 */
			Com_CashBook1 cb = new Com_CashBook1();

			/** Assign Cashbook Year and Month to year_month Variable */
			String year_month = cb.cb_date(Receipt_Creation_Date).toString();

			/** Split Cash Book Year and Month */
			String[] ym = year_month.split("/");

			/** Assign Year and Month */
			txtCash_year = Integer.parseInt(ym[0]);
			txtCash_Month_hid = Integer.parseInt(ym[1]);

			try {
				txtVoucher_No = Integer.parseInt(request
						.getParameter("txtVoucher_No"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}

			String update_user = (String) session.getAttribute("UserId");

			long l = System.currentTimeMillis();
			Timestamp ts = new Timestamp(l);

			try {
				ps = con.prepareStatement("update FAS_INTER_BANK_TRF_AT_HO set TRANSFER_STATUS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and DATE_OF_TRANSFER=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?");

				ps.setString(1, "C");
				ps.setString(2, update_user);
				ps.setTimestamp(3, ts);
				ps.setInt(4, cmbAcc_UnitCode);
				ps.setInt(5, cmbOffice_code);
				ps.setDate(6, txtCrea_date);
				ps.setInt(7, txtCash_year);
				ps.setInt(8, txtCash_Month_hid);
				ps.setInt(9, txtVoucher_No);
				s = ps.executeUpdate();				
					if ((s > 0)) {
						con.commit();
						sendMessage(
								response,
								"The InterBank Transfer Transaction Canceled Successfully ",
								"ok");
					} else {
						con.rollback();
						sendMessage(
								response,
								"The InterBank Transfer Transaction Cancel Failed ",
								"ok");
					}			
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
	}

	private void sendMessage(HttpServletResponse response, String msg,
			String bType) {
		try {
			String url = "org/Library/jsps/MessengerOkBack.jsp?message=" + msg
					+ "&button=" + bType;
			response.sendRedirect(url);
		} catch (IOException e) {
			System.out.println("error in send message");
		}
	}
}
