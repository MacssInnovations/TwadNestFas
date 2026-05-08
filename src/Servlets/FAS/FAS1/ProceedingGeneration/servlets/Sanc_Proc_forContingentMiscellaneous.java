package Servlets.FAS.FAS1.ProceedingGeneration.servlets;

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
 * Servlet implementation class Sanc_Proc_forContingentMiscellaneous
 */
public class Sanc_Proc_forContingentMiscellaneous extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Sanc_Proc_forContingentMiscellaneous() {
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
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

			xml = xml + "<response><command>getBillMajorType</command>";
			xml = xml + "<empid>" + empid + "</empid>";
			xml = xml + "<empName>" + empName + "</empName>";
			try {

				String su = "select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where STATUS='L' order by BILL_MAJOR_TYPE_CODE";
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

				String su1 = "select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS";
				ps = connection.prepareStatement(su1);
				rs = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (rs.next()) {

					xml = xml + "<accUnitID>"
							+ rs.getInt("ACCOUNTING_UNIT_ID")
							+ "</accUnitID>";

					xml = xml + "<accUnitName>"
							+ rs.getString("ACCOUNTING_UNIT_NAME")
							+ "</accUnitName>";
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			
			try {

				String su1 = "select DESIGNATION_ID,DESIGNATION from HRM_MST_DESIGNATIONS";
				ps = connection.prepareStatement(su1);
				rs = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (rs.next()) {

					xml = xml + "<designationID>" + rs.getInt("DESIGNATION_ID")
							+ "</designationID>";

					xml = xml + "<designation>" + rs.getString("DESIGNATION")
							+ "</designation>";
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}

		}else if (strCommand.equalsIgnoreCase("getBillMinorType")) {

			xml = xml + "<response><command>getBillMinorType</command>";
			int cboBillMajorType = Integer.parseInt(request.getParameter("cboBillMajorType"));		
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
		}else if (strCommand.equalsIgnoreCase("getGrid")) {

			xml = xml + "<response><command>getGrid</command>";
			
			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cmbOffice_code = Integer.parseInt(cboOffice_code1);
			
			int cboBillMajorType = Integer.parseInt(request.getParameter("cboBillMajorType"));
			int cboBillMinorType = Integer.parseInt(request.getParameter("cboBillMinorType"));
			int cboBillSubType = Integer.parseInt(request.getParameter("cboBillSubType"));
			String InvoiceDate= null;
			
			try {
				ps2 = connection.prepareStatement("select SUPPORTING_INVOICE from FAS_SUP_INV_BILL_TYPES where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and BILL_SUB_TYPE_CODE=?");
				ps2.setInt(1, cboAcc_UnitCode);
				ps2.setInt(2, cmbOffice_code);
				ps2.setInt(3, cboBillMajorType);
				ps2.setInt(4, cboBillMinorType);
				ps2.setInt(5, cboBillSubType);
				
				rs2 = ps2.executeQuery();
				if(rs2.next())
				{
				
				try {
				ps1 = connection.prepareStatement("select SUPPORTING_INVOICE from FAS_SUP_INV_BILL_TYPES where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and BILL_SUB_TYPE_CODE=?");
				ps1.setInt(1, cboAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setInt(3, cboBillMajorType);
				ps1.setInt(4, cboBillMinorType);
				ps1.setInt(5, cboBillSubType);
				
				rs = ps1.executeQuery();
				while(rs.next()) {
					xml = xml + "<flag>success</flag>";
													
					xml = xml + "<supportingInvoice>" + rs.getString("SUPPORTING_INVOICE")
					+ "</supportingInvoice>";											
				}
			} catch (Exception e) {
				System.out.println("Exception in HeadCode:" + e);
			}
				}else
				{
					xml = xml + "<flag>NoData</flag>";
				}
				
				ps2.close();
				rs2.close();
			}catch (Exception e) {
				System.out.println("Exception in HeadCode:" + e);
				xml = xml + "<flag>Failure</flag>";
			}
			
			try {
				ps2 = connection.prepareStatement("select INVOICE_NO from FAS_INVOICE_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and BILL_SUB_TYPE_CODE=?");
				ps2.setInt(1, cboAcc_UnitCode);
				ps2.setInt(2, cmbOffice_code);
				ps2.setInt(3, cboBillMajorType);
				ps2.setInt(4, cboBillMinorType);
				ps2.setInt(5, cboBillSubType);
				
				rs2 = ps2.executeQuery();				
				if(rs2.next())
				{
			try {
				ps = connection.prepareStatement("select INVOICE_NO,INVOICE_DATE,INVOICE_AMOUNT,INVOICE_PARTICULARS from FAS_INVOICE_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and BILL_SUB_TYPE_CODE=?");
				ps.setInt(1, cboAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setInt(3, cboBillMajorType);
				ps.setInt(4, cboBillMinorType);
				ps.setInt(5, cboBillSubType);
				
				results = ps.executeQuery();
				while(results.next()) {
					xml = xml + "<flag>success</flag>";
					
					Date InvoiceDate1 = results.getDate("INVOICE_DATE");

					String Stringdate = InvoiceDate1.toString();
		
					String[] ddd = Stringdate.split("-");
		
					int day = Integer.parseInt(ddd[2]);
					int month = Integer.parseInt(ddd[1]);
					int yearr = Integer.parseInt(ddd[0]);
		
					if (month >= 10) {
						InvoiceDate = (day + "/" + month + "/" + yearr);
					} else {
						InvoiceDate = (day + "/0" + month + "/" + yearr);
					}						
					
					xml = xml + "<invoiceNo>" + results.getInt("INVOICE_NO")
					+ "</invoiceNo>";
					
					xml = xml + "<invoiceDate>" + InvoiceDate
					+ "</invoiceDate>";
					
					xml = xml + "<invoiceAmount>" + results.getFloat("INVOICE_AMOUNT")
					+ "</invoiceAmount>";
					
					xml = xml + "<invoiceParticulars>" + results.getString("INVOICE_PARTICULARS")
					+ "</invoiceParticulars>";											
				}
			} catch (Exception e) {
				System.out.println("Exception in HeadCode:" + e);
			}
				}
				else
				{
					xml = xml + "<flag>NoData</flag>";
				}
			}catch (Exception e) {
					System.out.println("Exception in HeadCode:" + e);
					xml = xml + "<flag>Failure</flag>";
				}

		}else if (strCommand.equalsIgnoreCase("calculateBudget")) {

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
		} else if (strCommand.equalsIgnoreCase("saveFunc")) {
			xml = xml + "<response><command>saveFunc</command>";

			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cmbOffice_code = Integer.parseInt(cboOffice_code1);

			String cboCashBook_Year1 = request.getParameter("year");
			int cboCashBook_Year = Integer.parseInt(cboCashBook_Year1);

			String cboCashBook_Month1 = request.getParameter("month");
			int cboCashBook_Month = Integer.parseInt(cboCashBook_Month1);

			java.sql.Date SanctionProceedingDate = null;
			java.util.GregorianCalendar c2;
			String[] sd = request.getParameter("txtSanctionProceedingDate")
					.split("/");
			c2 = new java.util.GregorianCalendar(Integer.parseInt(sd[2]),
					Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c2.getTime();
			SanctionProceedingDate = new Date(d.getTime());

			String cboBillMajorType1 = request.getParameter("cboBillMajorType");
			int cboBillMajorType = Integer.parseInt(cboBillMajorType1);

			String cboBillMinorType1 = request.getParameter("cboBillMinorType");
			int cboBillMinorType = Integer.parseInt(cboBillMinorType1);

			String cboBillSubType1 = request.getParameter("cboBillSubType");
			int cboBillSubType = Integer.parseInt(cboBillSubType1);

			String rdoSupportingInvoices = request.getParameter("rdoSupportingInvoices");

			String cboPayeeType = request.getParameter("cboPayeeType");

			String cboPayeeCode1 = request.getParameter("cboPayeeCode");
			int cboPayeeCode = Integer.parseInt(cboPayeeCode1);
			
			String txtRefNo1 = request.getParameter("txtRefNo");
			int txtRefNo = Integer.parseInt(txtRefNo1);

			java.sql.Date RefDate = null;
			java.util.GregorianCalendar c5;
			String[] sd5 = request.getParameter("txtRefDate").split("/");
			c5 = new java.util.GregorianCalendar(Integer.parseInt(sd5[2]),
					Integer.parseInt(sd5[1]) - 1, Integer.parseInt(sd5[0]));
			java.util.Date d5 = c5.getTime();
			RefDate = new Date(d5.getTime());

			String cboSanctioningAuthority1 = request
					.getParameter("cboSanctioningAuthority");
			int cboSanctioningAuthority = Integer
					.parseInt(cboSanctioningAuthority1);

			String cbosanctionedBy1 = request.getParameter("cmbSL_Code");
			int cbosanctionedBy = Integer.parseInt(cbosanctionedBy1);

			String txtTotalSanctionedAmount1 = request
					.getParameter("txtTotalSanctionedAmount");
			int txtTotalSanctionedAmount = Integer
					.parseInt(txtTotalSanctionedAmount1);

			float txtBudgetProvided = Float.parseFloat(request.getParameter("txtBudgetProvided"));
			
			float txtBudgetSpent = Float.parseFloat(request.getParameter("txtBudgetSpent"));
			
			String txtaccountheadcode1 = request
					.getParameter("txtaccountheadcode");
			int txtaccountheadcode = Integer.parseInt(txtaccountheadcode1);

			String mtxtRemarks = request.getParameter("mtxtRemarks");

			int i = 1, i1 = 0;
			int db1 = 0, db2 = 0;
			try {
				ps1 = connection
						.prepareStatement("Select max(SANCTION_PROCEEDING_NO) from FAS_SANC_PROC_MISC_MST");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);					
					i = i + i1;

				} else {
					i = i;
				}

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();
			}			
			String al = request.getParameter("al");
			String[] al1 = request.getParameter("al").split(",");
			int k = 0;	
			try
			{
				connection.setAutoCommit(false);
			try {

				ps1 = connection
						.prepareStatement("insert into FAS_SANC_PROC_MISC_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,SANCTION_PROCEEDING_NO,SANCTION_PROCEEDING_DATE,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,PAYEE_TYPE,PAYEE_CODE,REF_NO,REF_DATE,SANCTION_AUTH0RITY,SANCTIONED_BY,TOTAL_SANCTION_AMOUNT,ACCOUNT_HEAD_CODE,PARTICULARS,STATUS,UPDATED_BY_USERID,UPDATED_DATE,BUDGET_PROVIDED,BUDGET_SO_FAR_SPENT) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				ps1.setInt(1, cboAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setInt(3, cboCashBook_Year);
				ps1.setInt(4, cboCashBook_Month);
				ps1.setInt(5, i);
				ps1.setDate(6, SanctionProceedingDate);
				ps1.setInt(7, cboBillMajorType);
				ps1.setInt(8, cboBillMinorType);
				ps1.setInt(9, cboBillSubType);
				ps1.setString(10, cboPayeeType);
				ps1.setInt(11, cboPayeeCode);
				ps1.setInt(12, txtRefNo);
				ps1.setDate(13, RefDate);
				ps1.setInt(14, cboSanctioningAuthority);
				ps1.setInt(15, cbosanctionedBy);
				ps1.setFloat(16, txtTotalSanctionedAmount);
				ps1.setInt(17, txtaccountheadcode);
				ps1.setString(18, mtxtRemarks);
				ps1.setString(19, "L");
				ps1.setInt(20, empid);
				ps1.setTimestamp(21, ts);
				ps1.setFloat(22, txtBudgetProvided);
				ps1.setFloat(23, txtBudgetSpent);
				
				db1=ps1.executeUpdate();
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			
			int txtInvoiceNo = 0;
			java.sql.Date InvoiceDate2 = null;
			Float txtInvoiceAmount = 0.0f;			
			String InvoiceParicularss = null;
			
			while (k < al1.length) {
				txtInvoiceNo = Integer.parseInt(al1[k]);
				
				
				String InvoiceDate1 = al1[k + 1];				
				java.util.GregorianCalendar c6;
				String[] sd6 = InvoiceDate1.split("/");
				c6 = new java.util.GregorianCalendar(Integer.parseInt(sd6[2]),
						Integer.parseInt(sd6[1]) - 1, Integer.parseInt(sd6[0]));
				java.util.Date d6 = c6.getTime();
				InvoiceDate2 = new Date(d6.getTime());

				txtInvoiceAmount = Float.parseFloat(al1[k + 2]);

				InvoiceParicularss = al1[k + 3];

				int i2 = 1, i3 = 0;
				try {
					ps1 = connection
							.prepareStatement("Select max(SL_NO) from FAS_SANC_PROC_MISC_TRN");
					results2 = ps1.executeQuery();
					xml = xml + "<flag1>success1</flag1>";

					if (results2.next()) {
						i3 = results2.getInt(1);
						System.out.println("count:-----------" + i1);
						i2 = i2 + i3;

					} else {
						i2 = i2;
					}

					System.out.println("iiiiiiiii--------" + i2);
					xml = xml + "<ownerCode>" + i2 + "</ownerCode>";

				} catch (Exception e) {
					xml = xml + "<flag1>failure1</flag1>";
					e.printStackTrace();

				}

				k = k + 4;

				try {
					xml = xml + "<flag>success</flag>";
					ps1 = connection
							.prepareStatement("insert into FAS_SANC_PROC_MISC_TRN(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,SANCTION_PROCEEDING_NO,SL_NO,INVOICE_NO,INVOICE_DATE,INVOICE_AMOUNT,SANCTION_AMOUNT,REMARKS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
					ps1.setInt(1, cboAcc_UnitCode);
					ps1.setInt(2, cmbOffice_code);
					ps1.setInt(3, cboCashBook_Year);
					ps1.setInt(4, cboCashBook_Month);
					ps1.setInt(5, i);
					ps1.setInt(6, i2);
					ps1.setInt(7, txtInvoiceNo);
					ps1.setDate(8, InvoiceDate2);
					ps1.setFloat(9, txtInvoiceAmount);
					ps1.setFloat(10, txtTotalSanctionedAmount);
					ps1.setString(11, InvoiceParicularss);					
					ps1.setInt(12, empid);
					ps1.setTimestamp(13, ts);
					
					db2=ps1.executeUpdate();

				} catch (Exception e) {
					xml = xml + "<flag>failure</flag>";
					e.printStackTrace();
				}
			}			
			if((db1>0) && (db2>0) )
			{
				connection.commit();
				xml = xml + "<flag>success</flag>";
			}else{
				connection.rollback();
				xml = xml + "<flag>failure</flag>";
			}
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
