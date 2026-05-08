package Servlets.FAS.FAS1.BillRegister.servlets;

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
 * Servlet implementation class BillTokenRegisterEntry_WithoutProceeding
 */
public class BillTokenRegisterEntry_WithoutProceeding extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BillTokenRegisterEntry_WithoutProceeding() {
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
		// TODO Auto-generated method stub
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();

		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null;
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
		String empName = empProfile.getEmployeeName();
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);

		if (strCommand.equalsIgnoreCase("getBillMajorType")) {
			System.out.println("withoutttttttttttttttttttttttt");
			xml = xml + "<response><command>getBillMajorType</command>";
			xml = xml + "<empid>" + empid + "</empid>";
			xml = xml + "<empName>" + empName + "</empName>";
			try {

				String su = "select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where status='L' and BILL_MAJOR_TYPE_CODE!=2 ";
				System.out.println("su:::"+su);
				ps = connection.prepareStatement(su);
				rs = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (rs.next()) {

					xml = xml + "<billMajorTypeCode>"
							+ rs.getInt("BILL_MAJOR_TYPE_CODE")
							+ "</billMajorTypeCode>";

					xml = xml + "<billMajorTypeDesc>"
							+ rs.getString("BILL_MAJOR_TYPE_DESC")
							+ "</billMajorTypeDesc>";
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			try {

				String su1 = "select b.office_id,b.office_name from (select employee_id,office_id from HRM_EMP_CURRENT_POSTING where employee_id=?)a left outer join (select office_id,office_name from COM_MST_OFFICES)b on a.office_id=b.office_id";
				ps = connection.prepareStatement(su1);
				ps.setInt(1, empid);
				rs = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (rs.next()) {

					xml = xml + "<OfficeID>" + rs.getInt("OFFICE_ID")
							+ "</OfficeID>";

					xml = xml + "<OfficeName>" + rs.getString("OFFICE_NAME")
							+ "</OfficeName>";

					System.out.println(rs.getInt("OFFICE_ID"));
					System.out.println(rs.getString("OFFICE_NAME"));
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}

			int i2 = 1, i3 = 0;
			try {
				ps1 = connection
						.prepareStatement("Select max(BILL_NO) from FAS_BILL_REGISTERNEW");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i3 = results2.getInt(1);
					i2 = i2 + i3;

				} else {
					i2 = i2;
				}

				xml = xml + "<BillNo>" + i2 + "</BillNo>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();

			}
		} else if (strCommand.equalsIgnoreCase("getOffice")) {
			xml = xml + "<response><command>getOffice</command>";
			int txtEmpID_mas = Integer.parseInt(request
					.getParameter("txtEmpID_mas"));
			try {

				String su1 = "select b.office_id,b.office_name from (select employee_id,office_id from HRM_EMP_CURRENT_POSTING where employee_id=?)a left outer join (select office_id,office_name from COM_MST_OFFICES)b on a.office_id=b.office_id";
				ps = connection.prepareStatement(su1);
				ps.setInt(1, txtEmpID_mas);
				rs = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (rs.next()) {

					xml = xml + "<OfficeID>" + rs.getInt("OFFICE_ID")
							+ "</OfficeID>";

					xml = xml + "<OfficeName>" + rs.getString("OFFICE_NAME")
							+ "</OfficeName>";

					System.out.println(rs.getInt("OFFICE_ID"));
					System.out.println(rs.getString("OFFICE_NAME"));
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}

		} else if (strCommand.equalsIgnoreCase("getBillMinorType")) {
			xml = xml + "<response><command>getBillMinorType</command>";
			int cboBillMajorType = Integer.parseInt(request
					.getParameter("cboBillMajorType"));

			try {

				String su1 = "select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=? and status='L'";
				ps1 = connection.prepareStatement(su1);
				ps1.setInt(1, cboBillMajorType);
				results = ps1.executeQuery();
				if (results.next()) {
					try {

						String su = "select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=? and status='L'";
						ps = connection.prepareStatement(su);
						ps.setInt(1, cboBillMajorType);
						rs = ps.executeQuery();
						xml = xml + "<flag>success</flag>";
						while (rs.next()) {

							xml = xml + "<billMinorTypeCode>"
									+ rs.getInt("BILL_MINOR_TYPE_CODE")
									+ "</billMinorTypeCode>";

							xml = xml + "<billMinorTypeDesc>"
									+ rs.getString("BILL_MINOR_TYPE_DESC")
									+ "</billMinorTypeDesc>";
						}
						ps.close();
						rs.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						xml = xml + "<flag>failure</flag>";
					}
				} else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}

		} else if (strCommand.equalsIgnoreCase("getBillsubType")) {

			xml = xml + "<response><command>getBillsubType</command>";
			int cboBillMajorType = Integer.parseInt(request
					.getParameter("cboBillMajorType"));
			int cboBillMinorType = Integer.parseInt(request
					.getParameter("cboBillMinorType"));
			try {

				String su1 = "select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and status='L'";
				ps1 = connection.prepareStatement(su1);
				ps1.setInt(1, cboBillMajorType);
				ps1.setInt(2, cboBillMinorType);
				results = ps1.executeQuery();
				if (results.next()) {
					try {

						String su = "select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and status='L'";
						ps = connection.prepareStatement(su);
						ps.setInt(1, cboBillMajorType);
						ps.setInt(2, cboBillMinorType);
						rs = ps.executeQuery();
						xml = xml + "<flag>success</flag>";
						while (rs.next()) {

							xml = xml + "<billSubTypeCode>"
									+ rs.getInt("BILL_SUB_TYPE_CODE")
									+ "</billSubTypeCode>";

							xml = xml + "<billsubTypeDesc>"
									+ rs.getString("BILL_SUB_TYPE_DESC")
									+ "</billsubTypeDesc>";
						}
						ps.close();
						rs.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						xml = xml + "<flag>failure</flag>";
					}
				} else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		} else if (strCommand.equalsIgnoreCase("calculateBudget")) {

			xml = xml + "<response><command>calculateBudget</command>";
			int cboAcc_UnitCode = Integer.parseInt(request
					.getParameter("cboAcc_UnitCode"));
			int cboOffice_code = Integer.parseInt(request
					.getParameter("cboOffice_code"));
			String txtaccountheadcode = request
					.getParameter("txtaccountheadcode");

			String year = request.getParameter("year");
			String year1 = request.getParameter("year1");
			String financialYear1 = (year + "-" + year1);

			try {

				String su1 = "select * from COM_BUDGET_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?";

				System.out.println(cboAcc_UnitCode);
				System.out.println(cboOffice_code);
				System.out.println(financialYear1);
				// System.out.println(txtaccountheadcode);

				ps1 = connection.prepareStatement(su1);
				ps1.setInt(1, cboAcc_UnitCode);
				ps1.setInt(2, cboOffice_code);
				ps1.setString(3, financialYear1);
				// ps1.setString(4, txtaccountheadcode);
				results = ps1.executeQuery();

				if (results.next()) {

					System.out.println("enter");

					try {

						String su = "select * from COM_BUDGET_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?";
						ps = connection.prepareStatement(su);
						ps.setInt(1, cboAcc_UnitCode);
						ps.setInt(2, cboOffice_code);
						ps.setString(3, financialYear1);
						// ps.setString(4, txtaccountheadcode);

						rs = ps.executeQuery();
						xml = xml + "<flag>success</flag>";
						while (rs.next()) {
							System.out.println("while");
							int currentYearBudgetAlloted = rs
									.getInt("CURRENT_YEAR_BUDGET_ALLOTTED");
							int budgetSoFarSpent = rs
									.getInt("BUDGET_SOFAR_SPENT");
							int balanceAmount = (currentYearBudgetAlloted - budgetSoFarSpent);

							xml = xml + "<BudgetProvided>"
									+ currentYearBudgetAlloted
									+ "</BudgetProvided>";

							xml = xml + "<BudgetSoFarSpent>" + budgetSoFarSpent
									+ "</BudgetSoFarSpent>";

							xml = xml + "<balanceAmount>" + balanceAmount
									+ "</balanceAmount>";
						}
						ps.close();
						rs.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						xml = xml + "<flag>failure</flag>";
					}
				} else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		} 
		
		/* else if (strCommand.equalsIgnoreCase("saveFunc")) {
			xml = xml + "<response><command>saveFunc</command>";

			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cmbOffice_code = Integer.parseInt(cboOffice_code1);

			String cboCashBook_Year1 = request.getParameter("year");
			int cboCashBook_Year = Integer.parseInt(cboCashBook_Year1);

			String cboCashBook_Month1 = request.getParameter("month");
			int cboCashBook_Month = Integer.parseInt(cboCashBook_Month1);

			String cboBillMajorType1 = request.getParameter("cboBillMajorType");
			int cboBillMajorType = Integer.parseInt(cboBillMajorType1);

			String cboBillMinorType1 = request.getParameter("cboBillMinorType");
			int cboBillMinorType = Integer.parseInt(cboBillMinorType1);

			String cboBillSubType1 = request.getParameter("cboBillSubType");
			int cboBillSubType = Integer.parseInt(cboBillSubType1);

			String txtBillNo1 = request.getParameter("txtBillNo");
			int txtBillNo = Integer.parseInt(txtBillNo1);

			xml = xml + "<BillNo>" + txtBillNo + "</BillNo>";

			java.sql.Date BillDate = null;
			java.util.GregorianCalendar c2;
			String[] sd = request.getParameter("txtBillDate").split("/");
			c2 = new java.util.GregorianCalendar(Integer.parseInt(sd[2]),
					Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c2.getTime();
			BillDate = new Date(d.getTime());

			java.sql.Date InvoiceReceivedDate = null;
			java.util.GregorianCalendar c5;
			String[] sd5 = request.getParameter("txtInvoiceReceivedDate")
					.split("/");
			c5 = new java.util.GregorianCalendar(Integer.parseInt(sd5[2]),
					Integer.parseInt(sd5[1]) - 1, Integer.parseInt(sd5[0]));
			java.util.Date d5 = c5.getTime();
			InvoiceReceivedDate = new Date(d5.getTime());

			String txtNoofInvoices1 = request.getParameter("txtNoofInvoices");
			int txtNoofInvoices = Integer.parseInt(txtNoofInvoices1);

			String txtTotalBillAmount1 = request
					.getParameter("txtTotalBillAmount");
			float txtTotalBillAmount = Float.parseFloat(txtTotalBillAmount1);

			String txtAcc_HeadCode1 = request.getParameter("txtAcc_HeadCode");
			int txtAcc_HeadCode = Integer.parseInt(txtAcc_HeadCode1);

			String txtPayeeType = request.getParameter("txtPayeeType");

			String txtPayeeCode1 = request.getParameter("txtPayeeCode");
			int txtPayeeCode = Integer.parseInt(txtPayeeCode1);

			String txtEmpID_mas1 = request.getParameter("txtEmpID_mas");
			int txtEmpID_mas = Integer.parseInt(txtEmpID_mas1);

			String cboOffice1 = request.getParameter("cboOffice");
			int cboOffice = Integer.parseInt(cboOffice1);

			float txtBudgetProvision = Float.parseFloat(request
					.getParameter("txtBudgetProvision"));

			float txtBudgetSpent = Float.parseFloat(request
					.getParameter("txtBudgetSpent"));

			String txtRefNo1 = request.getParameter("txtRefNo");
			int txtRefNo = Integer.parseInt(txtRefNo1);

			java.sql.Date RefDate = null;
			java.util.GregorianCalendar cc8;
			String[] sdc8 = request.getParameter("txtRefDate").split("/");
			cc8 = new java.util.GregorianCalendar(Integer.parseInt(sdc8[2]),
					Integer.parseInt(sdc8[1]) - 1, Integer.parseInt(sdc8[0]));
			java.util.Date dc8 = cc8.getTime();
			RefDate = new Date(dc8.getTime());

			String mtxtRemarks = request.getParameter("mtxtRemarks");

			String rdoInvoiceEntryOption = request.getParameter("rdoInvoiceEntryOption");

			int txtInvoiceNo = 0;
			if(rdoInvoiceEntryOption.equals("Entry"))
			{
				String txtInvoiceNo1 = request.getParameter("txtInvoiceNo");
				txtInvoiceNo = Integer.parseInt(txtInvoiceNo1);
			}else{
				
				String txtIfSelectfromList = request.getParameter("txtIfSelectfromList");	
				txtInvoiceNo = Integer.parseInt(txtIfSelectfromList);
			}			
			
			java.sql.Date InvoiceDate = null;
			java.util.GregorianCalendar cc5;
			String[] sdc5 = request.getParameter("txtInvoiceDate").split("/");
			cc5 = new java.util.GregorianCalendar(Integer.parseInt(sdc5[2]),
					Integer.parseInt(sdc5[1]) - 1, Integer.parseInt(sdc5[0]));
			java.util.Date dc5 = cc5.getTime();
			InvoiceDate = new Date(dc5.getTime());

			String txtInvoiceAmount1 = request.getParameter("txtInvoiceAmount");
			int txtInvoiceAmount = Integer.parseInt(txtInvoiceAmount1);

			String mtxtParticulars = request.getParameter("mtxtParticulars1");

			try {

				ps1 = connection.prepareStatement("insert into FAS_BILL_REGISTERNEW(ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,BILL_MAJOR_TYPE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,BILL_NO,BILL_DATE,INVOICE_RECEIVED_DATE,NO_OFINVOICES,TOTAL_BILL_AMOUNT,ACCOUNT_HEAD_CODE,PAYEE_TYPE_CODE,PAYEE_CODE,BILL_PROCESSING_DONE_BY,REF_NO,REF_DATE,REMARKS,STATUS,UPDATED_BY_USERID,UPDATED_DATE,BUDGET_PROVISION,BUDGET_SO_FAR_SPENT) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				ps1.setInt(1, cboAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setInt(3, cboCashBook_Year);
				ps1.setInt(4, cboCashBook_Month);
				ps1.setInt(5, cboBillMajorType);
				ps1.setInt(6, cboBillMinorType);
				ps1.setInt(7, cboBillSubType);
				ps1.setInt(8, txtBillNo);
				ps1.setDate(9, BillDate);
				ps1.setDate(10, InvoiceReceivedDate);
				ps1.setInt(11, txtNoofInvoices);
				ps1.setFloat(12, txtTotalBillAmount);
				ps1.setInt(13, txtAcc_HeadCode);
				ps1.setString(14, txtPayeeType);
				ps1.setInt(15, txtPayeeCode);
				ps1.setInt(16, txtEmpID_mas);
				ps1.setInt(17, txtRefNo);
				ps1.setDate(18, RefDate);
				ps1.setString(19, mtxtRemarks);
				ps1.setString(20, "L");
				ps1.setInt(21, empid);
				ps1.setTimestamp(22, ts);
				ps1.setFloat(23, txtBudgetProvision);
				ps1.setFloat(24, txtBudgetSpent);

				ps1.executeUpdate();
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

			try {

				ps1 = connection
						.prepareStatement("insert into FAS_BILL_REGISTER_INV_DETAILS(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,BILL_MAJOR_TYPE,BILL_NO,INVOICE_NO,BILL_DATE,INVOICE_DATE,INVOICE_AMOUNT,REMARKS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
				ps1.setInt(1, cboAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setInt(3, cboCashBook_Year);
				ps1.setInt(4, cboCashBook_Month);
				ps1.setInt(5, cboBillMajorType);
				ps1.setInt(6, txtBillNo);
				ps1.setInt(7, txtInvoiceNo);
				ps1.setDate(8, BillDate);
				ps1.setDate(9, InvoiceDate);
				ps1.setInt(10, txtInvoiceAmount);
				ps1.setString(11, mtxtParticulars);
				ps1.setInt(12, empid);
				ps1.setTimestamp(13, ts);

				ps1.executeUpdate();
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		} */ 
		else if (strCommand.equalsIgnoreCase("loadGrid")) {
			xml = xml + "<response><command>loadGrid</command>";
			int BillNo = 0;
			String BillDate = null;
			String InvoiceReceivedDate = null;
			String RefDate = null;
			String InvoiceDate = null;
			try {

				String su1 = "select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID,BILL_NO,BILL_MAJOR_TYPE from FAS_BILL_REGISTERNEW";
				ps1 = connection.prepareStatement(su1);
				results = ps1.executeQuery();
				if (results.next()) {
					try {

						String su = "select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID,BILL_NO,PAYEE_CODE,BILL_DATE,BILL_PROCESSING_DONE_BY,ACCOUNT_HEAD_CODE,REMARKS,UPDATED_BY_USERID,UPDATED_DATE,BILL_MINOR_TYPE_CODE,BILL_MAJOR_TYPE,BILL_SUB_TYPE_CODE,INVOICE_RECEIVED_DATE,NO_OFINVOICES,PAYEE_TYPE_CODE,TOTAL_BILL_AMOUNT,REF_NO,REF_DATE from FAS_BILL_REGISTERNEW";
						ps = connection.prepareStatement(su);
						rs = ps.executeQuery();
						xml = xml + "<flag>success</flag>";
						while (rs.next()) {
							Date BillDate1 = rs.getDate("BILL_DATE");
							Date InvoiceReceivedDate1 = rs
									.getDate("INVOICE_RECEIVED_DATE");
							Date RefDate1 = rs.getDate("REF_DATE");

							String Stringdate = BillDate1.toString();
							String Stringdate1 = InvoiceReceivedDate1
									.toString();
							String Stringdate2 = RefDate1.toString();

							String[] ddd = Stringdate.split("-");
							String[] ddd1 = Stringdate1.split("-");
							String[] ddd2 = Stringdate2.split("-");

							int day = Integer.parseInt(ddd[2]);
							int month = Integer.parseInt(ddd[1]);
							int year = Integer.parseInt(ddd[0]);

							int day1 = Integer.parseInt(ddd1[2]);
							int month1 = Integer.parseInt(ddd1[1]);
							int year1 = Integer.parseInt(ddd1[0]);

							int day2 = Integer.parseInt(ddd2[2]);
							int month2 = Integer.parseInt(ddd2[1]);
							int year2 = Integer.parseInt(ddd2[0]);

							if (month >= 10) {
								BillDate = (day + "/" + month + "/" + year);
							} else {
								BillDate = (day + "/0" + month + "/" + year);
							}

							if (month1 >= 10) {
								InvoiceReceivedDate = (day1 + "/" + month1
										+ "/" + year1);
							} else {
								InvoiceReceivedDate = (day1 + "/0" + month1
										+ "/" + year1);
							}
							if (month2 >= 10) {
								RefDate = (day2 + "/" + month2 + "/" + year2);
							} else {
								RefDate = (day2 + "/0" + month2 + "/" + year2);
							}
							xml = xml + "<AccUnitCode>"
									+ rs.getInt("ACCOUNTING_UNIT_ID")
									+ "</AccUnitCode>";

							xml = xml + "<AccForOfficeCode>"
									+ rs.getInt("ACCOUNTING_UNIT_OFFICE_ID")
									+ "</AccForOfficeCode>";

							xml = xml + "<BillMajorType>"
									+ rs.getInt("BILL_MAJOR_TYPE")
									+ "</BillMajorType>";

							xml = xml + "<billMinorTypeCode>"
									+ rs.getInt("BILL_MINOR_TYPE_CODE")
									+ "</billMinorTypeCode>";

							xml = xml + "<billSubTypeCode>"
									+ rs.getInt("BILL_SUB_TYPE_CODE")
									+ "</billSubTypeCode>";

							BillNo = rs.getInt("BILL_NO");
							xml = xml + "<BillNo>" + rs.getInt("BILL_NO")
									+ "</BillNo>";

							xml = xml + "<billDate>" + BillDate + "</billDate>";

							xml = xml + "<InvoiceReceivedDate>"
									+ InvoiceReceivedDate
									+ "</InvoiceReceivedDate>";

							xml = xml + "<NoOfInvoies>"
									+ rs.getInt("NO_OFINVOICES")
									+ "</NoOfInvoies>";

							xml = xml + "<TotalBillAmount>"
									+ rs.getInt("TOTAL_BILL_AMOUNT")
									+ "</TotalBillAmount>";

							xml = xml + "<AccHeadCode>"
									+ rs.getInt("ACCOUNT_HEAD_CODE")
									+ "</AccHeadCode>";

							xml = xml + "<PayeeType>"
									+ rs.getString("PAYEE_TYPE_CODE")
									+ "</PayeeType>";

							xml = xml + "<PayeeCode>" + rs.getInt("PAYEE_CODE")
									+ "</PayeeCode>";

							xml = xml + "<BillProcessingDoneBy>"
									+ rs.getInt("BILL_PROCESSING_DONE_BY")
									+ "</BillProcessingDoneBy>";

							xml = xml + "<RefNo>"
									+ rs.getInt("TOTAL_BILL_AMOUNT")
									+ "</RefNo>";

							xml = xml + "<RefDate>" + RefDate + "</RefDate>";

							xml = xml + "<Remarks>" + rs.getString("REMARKS")
									+ "</Remarks>";

							String su2 = "select INVOICE_NO,INVOICE_DATE,INVOICE_AMOUNT,REMARKS from FAS_BILL_REGISTER_INV_DETAILS where BILL_NO=?";
							ps1 = connection.prepareStatement(su2);
							ps1.setInt(1, BillNo);
							rs2 = ps1.executeQuery();
							xml = xml + "<flag>success</flag>";
							while (rs2.next()) {
								Date InvoiceDate1 = rs2.getDate("INVOICE_DATE");
								String Stringdate5 = InvoiceDate1.toString();
								String[] ddd5 = Stringdate5.split("-");

								int day5 = Integer.parseInt(ddd5[2]);
								int month5 = Integer.parseInt(ddd5[1]);
								int year5 = Integer.parseInt(ddd5[0]);

								if (month5 >= 10) {
									InvoiceDate = (day5 + "/" + month5 + "/" + year5);
								} else {
									InvoiceDate = (day5 + "/0" + month5 + "/" + year5);
								}
								xml = xml + "<InvoiceNo>"
										+ rs2.getInt("INVOICE_NO")
										+ "</InvoiceNo>";

								xml = xml + "<InvoiceDate>" + InvoiceDate
										+ "</InvoiceDate>";

								xml = xml + "<InvoiceAmount>"
										+ rs2.getInt("INVOICE_AMOUNT")
										+ "</InvoiceAmount>";

								xml = xml + "<Remarks1>"
										+ rs2.getString("REMARKS")
										+ "</Remarks1>";
							}
						}
						ps.close();
						rs.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						xml = xml + "<flag>failure</flag>";
					}
				} else {
					xml = xml + "<flag>NoData</flag>";
				}
				ps1.close();
				results.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		} else if (strCommand.equalsIgnoreCase("Edit")) {

			xml = "<response><command>Edit</command>";
			int txtBillNo = Integer.parseInt(request.getParameter("txtBillNo"));
			int txtEmpID_mas = Integer.parseInt(request
					.getParameter("txtEmpID_mas"));
			int BillMajorType = 0, BillMinorType = 0, BillSubType = 0;

			int cboAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cboOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			String txtaccountheadcode = request.getParameter("txtAcc_HeadCode");

			try {
				ps1 = connection
						.prepareStatement("Select BILL_MAJOR_TYPE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE from FAS_BILL_REGISTERNEW where BILL_NO=?");
				ps1.setInt(1, txtBillNo);
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";
				while (results2.next()) {
					BillMajorType = results2.getInt("BILL_MAJOR_TYPE");
					BillMinorType = results2.getInt("BILL_MINOR_TYPE_CODE");
					BillSubType = results2.getInt("BILL_SUB_TYPE_CODE");
				}
				ps1.close();
				results2.close();
			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();

			}
			try {

				String su1 = "select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and status='L'";
				ps1 = connection.prepareStatement(su1);
				ps1.setInt(1, BillMajorType);
				ps1.setInt(2, BillMinorType);
				results = ps1.executeQuery();
				if (results.next()) {
					try {

						String su = "select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and status='L'";
						ps = connection.prepareStatement(su);
						ps.setInt(1, BillMajorType);
						ps.setInt(2, BillMinorType);
						rs = ps.executeQuery();
						xml = xml + "<flag>success</flag>";
						while (rs.next()) {

							xml = xml + "<billMinorTypeCode>"
									+ rs.getInt("BILL_MINOR_TYPE_CODE")
									+ "</billMinorTypeCode>";

							xml = xml + "<billMinorTypeDesc>"
									+ rs.getString("BILL_MINOR_TYPE_DESC")
									+ "</billMinorTypeDesc>";
						}
						ps.close();
						rs.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						xml = xml + "<flag>failure</flag>";
					}
				} else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}

			try {

				String su1 = "select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and BILL_SUB_TYPE_CODE=? and status='L'";
				ps1 = connection.prepareStatement(su1);
				ps1.setInt(1, BillMajorType);
				ps1.setInt(2, BillMinorType);
				ps1.setInt(3, BillSubType);
				results = ps1.executeQuery();
				if (results.next()) {
					try {

						String su = "select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and BILL_SUB_TYPE_CODE=? and status='L'";
						ps = connection.prepareStatement(su);
						ps.setInt(1, BillMajorType);
						ps.setInt(2, BillMinorType);
						ps.setInt(3, BillSubType);
						rs = ps.executeQuery();
						xml = xml + "<flag>success</flag>";
						while (rs.next()) {

							xml = xml + "<billSubTypeCode>"
									+ rs.getInt("BILL_SUB_TYPE_CODE")
									+ "</billSubTypeCode>";

							xml = xml + "<billsubTypeDesc>"
									+ rs.getString("BILL_SUB_TYPE_DESC")
									+ "</billsubTypeDesc>";
						}
						ps.close();
						rs.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						xml = xml + "<flag>failure</flag>";
					}
				} else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			try {

				String su1 = "select EMPLOYEE_NAME from HRM_MST_EMPLOYEES where EMPLOYEE_ID=?";
				ps = connection.prepareStatement(su1);
				ps.setInt(1, txtEmpID_mas);
				rs = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (rs.next()) {
					xml = xml + "<BillProcessingDoneBy>"
							+ rs.getString("EMPLOYEE_NAME")
							+ "</BillProcessingDoneBy>";

					xml = xml + "<empid>" + txtEmpID_mas + "</empid>";
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			try {

				String su1 = "select b.office_id,b.office_name from (select employee_id,office_id from HRM_EMP_CURRENT_POSTING where employee_id=?)a left outer join (select office_id,office_name from COM_MST_OFFICES)b on a.office_id=b.office_id";
				ps = connection.prepareStatement(su1);
				ps.setInt(1, txtEmpID_mas);
				rs = ps.executeQuery();
				xml = xml + "<flag2>success</flag2>";
				while (rs.next()) {

					xml = xml + "<OfficeID>" + rs.getInt("OFFICE_ID")
							+ "</OfficeID>";

					xml = xml + "<OfficeName>" + rs.getString("OFFICE_NAME")
							+ "</OfficeName>";

					System.out.println(rs.getInt("OFFICE_ID"));
					System.out.println(rs.getString("OFFICE_NAME"));
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag2>failure</flag2>";
			}
			String year = request.getParameter("year");
			String year1 = request.getParameter("year1");
			String financialYear1 = (year + "-" + year1);

			try {

				String su1 = "select * from COM_BUDGET_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?";

				System.out.println(cboAcc_UnitCode);
				System.out.println(cboOffice_code);
				System.out.println(financialYear1);
				// System.out.println(txtaccountheadcode);

				ps1 = connection.prepareStatement(su1);
				ps1.setInt(1, cboAcc_UnitCode);
				ps1.setInt(2, cboOffice_code);
				ps1.setString(3, financialYear1);
				// ps1.setString(4, txtaccountheadcode);
				results = ps1.executeQuery();

				if (results.next()) {

					System.out.println("enter");

					try {

						String su = "select * from COM_BUDGET_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?";
						ps = connection.prepareStatement(su);
						ps.setInt(1, cboAcc_UnitCode);
						ps.setInt(2, cboOffice_code);
						ps.setString(3, financialYear1);
						// ps.setString(4, txtaccountheadcode);

						rs = ps.executeQuery();
						xml = xml + "<flagg>success</flagg>";
						while (rs.next()) {
							System.out.println("while");
							int currentYearBudgetAlloted = rs
									.getInt("CURRENT_YEAR_BUDGET_ALLOTTED");
							int budgetSoFarSpent = rs
									.getInt("BUDGET_SOFAR_SPENT");
							int balanceAmount = (currentYearBudgetAlloted - budgetSoFarSpent);

							xml = xml + "<BudgetProvided>"
									+ currentYearBudgetAlloted
									+ "</BudgetProvided>";

							xml = xml + "<BudgetSoFarSpent>" + budgetSoFarSpent
									+ "</BudgetSoFarSpent>";

							xml = xml + "<balanceAmount>" + balanceAmount
									+ "</balanceAmount>";
						}
						ps.close();
						rs.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						xml = xml + "<flagg>failure</flagg>";
					}
				} else {
					xml = xml + "<flagg>NoData</flagg>";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flagg>failure</flagg>";
			}
		} else if (strCommand.equalsIgnoreCase("deleted")) {

			xml = "<response><command>deleted</command>";
			int txtBillNo = Integer.parseInt(request.getParameter("txtBillNo"));

			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cmbOffice_code = Integer.parseInt(cboOffice_code1);

			try {
				ps = connection
						.prepareStatement("delete from FAS_BILL_REGISTERNEW where BILL_NO=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=?");
				ps.setInt(1, txtBillNo);
				ps.setInt(2, cboAcc_UnitCode);
				ps.setInt(3, cmbOffice_code);
				ps.executeUpdate();
				xml = xml + "<flag>success</flag>";
				xml = xml + "<id>" + txtBillNo + "</id>";

				ps1 = connection
						.prepareStatement("delete from FAS_BILL_REGISTER_INV_DETAILS where BILL_NO=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?");
				ps1.setInt(1, txtBillNo);
				ps1.setInt(2, cboAcc_UnitCode);
				ps1.setInt(3, cmbOffice_code);
				ps1.executeUpdate();
				xml = xml + "<flag>success</flag>";
				xml = xml + "<id>" + txtBillNo + "</id>";

			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
			}

			int i2 = 1, i3 = 0;
			try {
				ps1 = connection
						.prepareStatement("Select max(BILL_NO) from FAS_BILL_REGISTERNEW");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i3 = results2.getInt(1);
					i2 = i2 + i3;

				} else {
					i2 = i2;
				}
				xml = xml + "<BillNo>" + i2 + "</BillNo>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();

			}
		} else if (strCommand.equalsIgnoreCase("IVno")) {

			xml = "<response><command>IVno</command>";			

			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cmbOffice_code = Integer.parseInt(cboOffice_code1);
			
			String month1 = request.getParameter("month");
			int month = Integer.parseInt(month1);

			String year1 = request.getParameter("year");
			int year = Integer.parseInt(year1);

			try {
				ps = connection.prepareStatement("Select INVOICE_NO from FAS_INVOICE_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
				ps.setInt(1, cboAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setInt(3, year);
				ps.setInt(4, month);
				
				rs2 = ps.executeQuery();
				if(rs2.next())
				{
				try {
				ps1 = connection.prepareStatement("Select INVOICE_NO from FAS_INVOICE_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
				ps1.setInt(1, cboAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setInt(3, year);
				ps1.setInt(4, month);
				
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				while(results2.next()) {
					xml = xml + "<InvoiceNo>" + results2.getInt("INVOICE_NO") + "</InvoiceNo>";
				} 
				

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();

			}}
				else
				{
					xml = xml + "<flag1>NoData</flag1>";
				}
			}catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();

			}
			
		} else if (strCommand.equalsIgnoreCase("InvoiceDetails")) {

			xml = "<response><command>InvoiceDetails</command>";			

			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cmbOffice_code = Integer.parseInt(cboOffice_code1);
			
			String month1 = request.getParameter("month");
			int month = Integer.parseInt(month1);

			String year1 = request.getParameter("year");
			int year = Integer.parseInt(year1);
			
			String txtIfSelectfromList1 = request.getParameter("txtIfSelectfromList");
			int txtIfSelectfromList = Integer.parseInt(txtIfSelectfromList1);

			String InvoiveDate = null;
			try {
				ps = connection.prepareStatement("Select INVOICE_DATE,INVOICE_AMOUNT from FAS_INVOICE_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and INVOICE_NO=?");
				ps.setInt(1, cboAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setInt(3, year);
				ps.setInt(4, month);
				ps.setInt(5, txtIfSelectfromList);
				rs2 = ps.executeQuery();
				if(rs2.next())
				{
				try {
				ps1 = connection.prepareStatement("Select INVOICE_DATE,INVOICE_AMOUNT from FAS_INVOICE_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and INVOICE_NO=?");
				ps1.setInt(1, cboAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setInt(3, year);
				ps1.setInt(4, month);
				ps1.setInt(5, txtIfSelectfromList);
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				while(results2.next()) {
					Date InvoiveDate1 = results2.getDate("INVOICE_DATE");

					String Stringdate = InvoiveDate1.toString();

					String[] ddd = Stringdate.split("-");

					int day = Integer.parseInt(ddd[2]);
					int month11 = Integer.parseInt(ddd[1]);
					int year11 = Integer.parseInt(ddd[0]);

					if (month11 >= 10) {
						InvoiveDate = (day + "/" + month11 + "/" + year11);
					} else {
						InvoiveDate = (day + "/0" + month11 + "/" + year11);
					}

					xml = xml + "<InvoiceDate>" + InvoiveDate + "</InvoiceDate>";
					xml = xml + "<InvoiceAmount>" + results2.getInt("INVOICE_AMOUNT") + "</InvoiceAmount>";
				} 
				

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();

			}}
				else
				{
					xml = xml + "<flag1>NoData</flag1>";
				}
			}catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();

			}
			
		} else if (strCommand.equalsIgnoreCase("update")) {

			xml = "<response><command>update</command>";

			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cmbOffice_code = Integer.parseInt(cboOffice_code1);

			String cboCashBook_Year1 = request.getParameter("year");
			int cboCashBook_Year = Integer.parseInt(cboCashBook_Year1);

			String cboCashBook_Month1 = request.getParameter("month");
			int cboCashBook_Month = Integer.parseInt(cboCashBook_Month1);

			String cboBillMajorType1 = request.getParameter("cboBillMajorType");
			int cboBillMajorType = Integer.parseInt(cboBillMajorType1);

			String cboBillMinorType1 = request.getParameter("cboBillMinorType");
			int cboBillMinorType = Integer.parseInt(cboBillMinorType1);

			String cboBillSubType1 = request.getParameter("cboBillSubType");
			int cboBillSubType = Integer.parseInt(cboBillSubType1);

			String txtBillNo1 = request.getParameter("txtBillNo");
			int txtBillNo = Integer.parseInt(txtBillNo1);

			xml = xml + "<BillNo>" + txtBillNo + "</BillNo>";

			java.sql.Date BillDate = null;
			java.util.GregorianCalendar c2;
			String[] sd = request.getParameter("txtBillDate").split("/");
			c2 = new java.util.GregorianCalendar(Integer.parseInt(sd[2]),
					Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c2.getTime();
			BillDate = new Date(d.getTime());

			java.sql.Date InvoiceReceivedDate = null;
			java.util.GregorianCalendar c5;
			String[] sd5 = request.getParameter("txtInvoiceReceivedDate")
					.split("/");
			c5 = new java.util.GregorianCalendar(Integer.parseInt(sd5[2]),
					Integer.parseInt(sd5[1]) - 1, Integer.parseInt(sd5[0]));
			java.util.Date d5 = c5.getTime();
			InvoiceReceivedDate = new Date(d5.getTime());

			String txtNoofInvoices1 = request.getParameter("txtNoofInvoices");
			int txtNoofInvoices = Integer.parseInt(txtNoofInvoices1);

			String txtTotalBillAmount1 = request
					.getParameter("txtTotalBillAmount");
			float txtTotalBillAmount = Float.parseFloat(txtTotalBillAmount1);

			String txtAcc_HeadCode1 = request.getParameter("txtAcc_HeadCode");
			int txtAcc_HeadCode = Integer.parseInt(txtAcc_HeadCode1);

			String txtPayeeType = request.getParameter("txtPayeeType");

			String txtPayeeCode1 = request.getParameter("txtPayeeCode");
			int txtPayeeCode = Integer.parseInt(txtPayeeCode1);

			String txtEmpID_mas1 = request.getParameter("txtEmpID_mas");
			int txtEmpID_mas = Integer.parseInt(txtEmpID_mas1);

			String cboOffice1 = request.getParameter("cboOffice");
			int cboOffice = Integer.parseInt(cboOffice1);

			float txtBudgetProvision = Float.parseFloat(request
					.getParameter("txtBudgetProvision"));

			float txtBudgetSpent = Float.parseFloat(request
					.getParameter("txtBudgetSpent"));

			String txtRefNo1 = request.getParameter("txtRefNo");
			int txtRefNo = Integer.parseInt(txtRefNo1);

			java.sql.Date RefDate = null;
			java.util.GregorianCalendar cc8;
			String[] sdc8 = request.getParameter("txtRefDate").split("/");
			cc8 = new java.util.GregorianCalendar(Integer.parseInt(sdc8[2]),
					Integer.parseInt(sdc8[1]) - 1, Integer.parseInt(sdc8[0]));
			java.util.Date dc8 = cc8.getTime();
			RefDate = new Date(dc8.getTime());

			String mtxtRemarks = request.getParameter("mtxtRemarks");

			String rdoInvoiceEntryOption = request.getParameter("rdoInvoiceEntryOption");

			int txtInvoiceNo = 0;
			if(rdoInvoiceEntryOption.equals("Entry"))
			{
				String txtInvoiceNo1 = request.getParameter("txtInvoiceNo");
				txtInvoiceNo = Integer.parseInt(txtInvoiceNo1);
			}else{
				
				String txtIfSelectfromList = request.getParameter("txtIfSelectfromList");	
				txtInvoiceNo = Integer.parseInt(txtIfSelectfromList);
			}			
			
			java.sql.Date InvoiceDate = null;
			java.util.GregorianCalendar cc5;
			String[] sdc5 = request.getParameter("txtInvoiceDate").split("/");
			cc5 = new java.util.GregorianCalendar(Integer.parseInt(sdc5[2]),
					Integer.parseInt(sdc5[1]) - 1, Integer.parseInt(sdc5[0]));
			java.util.Date dc5 = cc5.getTime();
			InvoiceDate = new Date(dc5.getTime());

			String txtInvoiceAmount1 = request.getParameter("txtInvoiceAmount");
			int txtInvoiceAmount = Integer.parseInt(txtInvoiceAmount1);

			String mtxtParticulars = request.getParameter("mtxtParticulars1");

			int i2 = 1, i3 = 0;
			try {
				ps1 = connection
						.prepareStatement("Select max(BILL_NO) from FAS_BILL_REGISTERNEW");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i3 = results2.getInt(1);
					i2 = i2 + i3;

				} else {
					i2 = i2;
				}

				System.out.println("iiiiiiiii--------" + i2);
				xml = xml + "<BillNo>" + i2 + "</BillNo>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();

			}

			try {
				ps1 = connection
						.prepareStatement("update FAS_BILL_REGISTERNEW set ACCOUNTING_UNIT_ID=?,ACCOUNTING_UNIT_OFFICE_ID=?,CASHBOOK_YEAR=?,CASHBOOK_MONTH=?,BILL_MAJOR_TYPE=?,BILL_MINOR_TYPE_CODE=?,BILL_SUB_TYPE_CODE=?,BILL_DATE=?,INVOICE_RECEIVED_DATE=?,NO_OFINVOICES=?,TOTAL_BILL_AMOUNT=?,ACCOUNT_HEAD_CODE=?,PAYEE_TYPE_CODE=?,PAYEE_CODE=?,BILL_PROCESSING_DONE_BY=?,REF_NO=?,REF_DATE=?,REMARKS=?,STATUS=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,BUDGET_PROVISION=?,BUDGET_SO_FAR_SPENT=? where BILL_NO=?");
				ps1.setInt(1, cboAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setInt(3, cboCashBook_Year);
				ps1.setInt(4, cboCashBook_Month);
				ps1.setInt(5, cboBillMajorType);
				ps1.setInt(6, cboBillMinorType);
				ps1.setInt(7, cboBillSubType);
				ps1.setDate(8, BillDate);
				ps1.setDate(9, InvoiceReceivedDate);
				ps1.setInt(10, txtNoofInvoices);
				ps1.setFloat(11, txtTotalBillAmount);
				ps1.setInt(12, txtAcc_HeadCode);
				ps1.setString(13, txtPayeeType);
				ps1.setInt(14, txtPayeeCode);
				ps1.setInt(15, txtEmpID_mas);
				ps1.setInt(16, txtRefNo);
				ps1.setDate(17, RefDate);
				ps1.setString(18, mtxtRemarks);
				ps1.setString(19, "L");
				ps1.setInt(20, empid);
				ps1.setTimestamp(21, ts);
				ps1.setFloat(22, txtBudgetProvision);
				ps1.setFloat(23, txtBudgetSpent);
				ps1.setInt(24, txtBillNo);
				ps1.executeUpdate();
				xml = xml + "<flag>success</flag>";

				System.out
						.println("*****************************************************************************");
				ps = connection
						.prepareStatement("update FAS_BILL_REGISTER_INV_DETAILS set INVOICE_DATE=?,INVOICE_AMOUNT=?,REMARKS=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=? and INVOICE_NO=?");
				ps.setDate(1, InvoiceDate);
				ps.setInt(2, txtInvoiceAmount);
				ps.setString(3, mtxtParticulars);
				ps.setInt(4, cboAcc_UnitCode);
				ps.setInt(5, cmbOffice_code);
				ps.setInt(6, cboCashBook_Year);
				ps.setInt(7, cboCashBook_Month);
				ps.setInt(8, txtBillNo);
				ps.setInt(9, txtInvoiceNo);
				ps.executeUpdate();
				xml = xml + "<flag>success</flag>";
				ps.close();
				ps1.close();
			} catch (Exception e) {
				System.out.println("exception in update is" + e);

			}
		} else if (strCommand.equalsIgnoreCase("ClearAll")) {

			xml = "<response><command>ClearAll</command>";
			int i2 = 1, i3 = 0;
			try {
				ps1 = connection
						.prepareStatement("Select max(BILL_NO) from FAS_BILL_REGISTERNEW");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i3 = results2.getInt(1);
					i2 = i2 + i3;

				} else {
					i2 = i2;
				}

				System.out.println("iiiiiiiii--------" + i2);
				xml = xml + "<BillNo>" + i2 + "</BillNo>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();

			}
		}

		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}

}
