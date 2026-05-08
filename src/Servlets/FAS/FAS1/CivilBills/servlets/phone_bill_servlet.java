package Servlets.FAS.FAS1.CivilBills.servlets;



import Servlets.Security.classes.UserProfile;
import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

//import oracle.sql.TIMESTAMP;

import com.sun.jmx.snmp.Timestamp;

/**
 * Servlet implementation class phone_bill_servlet
 */
public class phone_bill_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    /**
     * @see HttpServlet#HttpServlet()
     */
	
	
	 public void init(ServletConfig config) throws ServletException {
	        super.init(config);
	    }
	
	
    public phone_bill_servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		PrintWriter out = response.getWriter();
		String CONTENT_TYPE = "text/xml; charset=windows-1252";
		response.setContentType(CONTENT_TYPE);

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
		PreparedStatement ps1=null,ps2 = null,ps3=null;
		ResultSet rs=null,rs2 = null,rs3=null;
		int year = 0;
		int month = 0;
		int day = 0;
		String cmd = "";
		String sql2="";

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

		String accUnitId = "", accOfficeId = "", phoneNo = "", billDate = "", genRemarks = "", status = "", updatedDate = "", detRemarks = "", invoiceDate = "";

		String userId = "";

		int billYear = 0, billMonth = 0, bookYear = 0, bookMonth = 0, invoiceNo = 0,genBillNo=0;

		float totalBillAmount = 0, currentBillAmount = 0;

		String xml = null, opr_mode = null;

		String sql = "";
		int count = 0;
		/** Get Accouting UNit ID */
		try {
			cmd = request.getParameter("command");

		} catch (Exception e) {
			System.out.println(e);
		}

		xml = "<response>";

		if (cmd.equalsIgnoreCase("LoadPhoneNumber")) {

			xml = xml + "<command>LoadPhoneNumber</command>";
			try {
				accUnitId = request.getParameter("cmbAcc_UnitCode");
				accOfficeId = request.getParameter("cmbAcc_OfficeCode");
			} catch (Exception e) {
				System.out.println("Err in bank_id :: " + accUnitId);
			}
			sql = " SELECT PHONE_NO FROM FAS_PHONE_TRN where ACCOUNTING_UNIT_ID=? and "
					+

					"accounting_unit_ofice_id=?";

			try {
				ps2 = con.prepareStatement(sql);
				ps2.setInt(1, Integer.parseInt(accUnitId.trim()));
				ps2.setInt(2, Integer.parseInt(accOfficeId.trim()));

				rs2 = ps2.executeQuery();

				System.out.println("The Office code = " + accOfficeId);
				System.out.println("The Unit code = " + accUnitId);

				System.out.println("query ----> : " + sql);

				// xml=xml+"<command>LoadPhoneNumber</command>";

				/** Count How many Records are available */
				
				while (rs2.next()) {
					xml = xml + "<phoneNo>" + rs2.getString("PHONE_NO")
							+ "</phoneNo>";
					count++;
				}

				if (count == 0) {
					xml = xml + "<flag>NoData</flag>";
				} else {
					xml = xml + "<flag>success</flag>";
				}
			} catch (Exception e) {
				System.out.println("Exception in assigning..." + e);
				xml = xml + "<flag>failure</flag>";
			}
		}

		  else if (cmd.equalsIgnoreCase("getNoDetails")) {
			xml = xml + "<command>getNoDetails</command>";
			try {
				phoneNo = request.getParameter("cmb_Phone_No");
				System.out.println("phone no : " + phoneNo);
			} catch (Exception e) {
				System.out.println("Err in phoneNo :: " + phoneNo);
			}
			// xml = xml+"<flag>success</flag>";

			try {
				sql = "SELECT SS.EMPLOYEE_ID," + "SS.CONNECTION_TYPE,"
						+ "SS.PURPOSE," + "SS.SERVICE_PROVIDER_NAME,"
						+ "SS.SERVICE_TYPE," + "AA.EMPLOYEE_NAME,"
						+ "BB.DESIGNATION_ID,"
						+ "CC.DESIGNATION FROM FAS_PHONE_TRN SS"
						+ " INNER JOIN HRM_MST_EMPLOYEES AA ON "
						+ "SS.EMPLOYEE_ID= AA.EMPLOYEE_ID"
						+ " INNER JOIN HR_MST_EMP_CURRENT_POSTING1 BB ON "
						+ "AA.EMPLOYEE_ID=BB.EMPLOYEE_ID "
						+ "INNER JOIN HRM_MST_DESIGNATIONS CC ON "
						+ "BB.DESIGNATION_ID = CC.DESIGNATION_ID"
						+ " WHERE PHONE_NO=?";

				ps2 = con.prepareStatement(sql);
				ps2.setLong(1, Long.parseLong(phoneNo));

				rs2 = ps2.executeQuery();

				System.out.println("query ----> : " + sql);

				// xml=xml+"<command>LoadPhoneNumber</command>";

				/** Count How many Records are available */
				while (rs2.next()) {

					xml = xml + "<employeeId>"
							+ rs2.getString("EMPLOYEE_ID").trim()
							+ "</employeeId>";
					xml = xml + "<connectionType>"
							+ rs2.getString("CONNECTION_TYPE").trim()
							+ "</connectionType>";
					xml = xml + "<purpose>" + rs2.getString("PURPOSE").trim()
							+ "</purpose>";
					xml = xml + "<serviceProviderName>"
							+ rs2.getString("SERVICE_PROVIDER_NAME").trim()
							+ "</serviceProviderName>";
					xml = xml + "<serviceType>"
							+ rs2.getString("SERVICE_TYPE").trim()
							+ "</serviceType>";
					xml = xml + "<emplyeeName>"
							+ rs2.getString("EMPLOYEE_NAME").trim()
							+ "</emplyeeName>";
					xml = xml + "<designation>"
							+ rs2.getString("DESIGNATION").trim()
							+ "</designation>";
					count++;
				}

				if (count == 0) {
					xml = xml + "<flag>NoData</flag>";
				} else {
					xml = xml + "<flag>success</flag>";
				}
			} catch (Exception e) {
				System.out.println("Exception in assigning..." + e);
				xml = xml + "<flag>failure</flag>";
			}
		}
		 
		  else {

		}

		xml = xml + "</response>";
		System.out.println(xml);
		out.println(xml);
		out.close();

	}

	
	
	
	
	
	
	
	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub

		String strCommand = "";
		Connection con = null;
		ResultSet rs1 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		CallableStatement cs = null;
		CallableStatement cs1 = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		String xml = "";
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

		try {
			ResourceBundle rsb1 = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rsb1.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rsb1.getString("Config.DSN");
			String strhostname = rsb1.getString("Config.HOST_NAME");
			String strportno = rsb1.getString("Config.PORT_NUMBER");
			String strsid = rsb1.getString("Config.SID");
			String strdbusername = rsb1.getString("Config.USER_NAME");
			String strdbpassword = rsb1.getString("Config.PASSWORD");
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);
			// sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");
		}

		String accUnitId = "", accOfficeId = "", phoneNo = "", billDate = "", genRemarks = "", status = "", updatedDate = "", detRemarks = "", invoiceDate = "";
		String userId = "";
		int billYear = 0, billMonth = 0, bookYear = 0, bookMonth = 0, invoiceNo = 0, genBillNo = 0, detBillNo = 0;
		float totalBillAmount = 0, currentBillAmount = 0;
		String opr_mode = null, cmd = "";

		String message = "";

		int billMstRows = 0, billTrnRows = 0, totAfctRows = 0;

		String arAccUnitId[] = null;
		String arOfficeId[] = null;
		String arPhoneNo[] = null;
		String arInvoiceNo[] = null;
		String arInvoiceDate[] = null;
		String arInvoiceYear[] = null;
		String arInvoiceMonth[] = null;
		String arBillAmount[] = null;
		String arParticulars[] = null;
		String arDetBillNo[] = null;

		String sql = "", sql1 = "", sql2 = "";
		int count = 0;

		

		/** Get Command */
		try {
			cmd = request.getParameter("command");
			System.out
					.println("assign. chid phone test .here command..." + cmd);

		} catch (Exception e) {
			System.out.println(e);
		}
		try {
			message = request.getParameter("hdnMessage");

			System.out.println("The Message: " + message);
		} catch (Exception e) {
			System.out.println("Err in Message : " + message);
		}
		try {
			userId = (String) session.getAttribute("UserId");

			System.out.println("The Current User : " + userId);
		} catch (Exception e) {
			System.out.println("Err in userId :: " + userId);
		}

		if (cmd.equalsIgnoreCase("Add")) {
			try {
				accUnitId = request.getParameter("cmbAcc_UnitCode");
				System.out.println("Unit code : " + accUnitId);

			} catch (Exception e) {
				System.out.println("Err in accountingUnitId :: " + accUnitId);
			}
			try {
				accOfficeId = request.getParameter("cmbOffice_code");
				System.out.println("office code : " + accOfficeId);
			} catch (Exception e) {
				System.out.println("Err in accOfficeId :: " + accOfficeId);
			}
			try {
				billDate = request.getParameter("txtBillDate");

				System.out.println("Cash Book Date : " + billDate);

				String bookYearMonth[] = billDate.split("/");

				bookYear = Integer.parseInt(bookYearMonth[2]);
				bookMonth = Integer.parseInt(bookYearMonth[1]);

				System.out.println("Book Year : " + bookYear);
				System.out.println("Book Month : " + bookMonth);

			} catch (Exception e) {
				System.out.println("Err in book billDate :: " + billDate);
			}
			try {
				totalBillAmount = Float.parseFloat(request
						.getParameter("txtTotalBillAmount"));
				System.out.println("Total Bill Amout : " + totalBillAmount);

			} catch (Exception e) {
				System.out.println("Err in totalBillAmount :: "
						+ totalBillAmount);
			}
			try {
				genRemarks = request.getParameter("txtGeneralRemarks");
				System.out.println(" General Remarks : " + genRemarks);
			} catch (Exception e) {
				System.out.println("Err in genRemarks :: " + genRemarks);

			}

			System.out.println("Entered into Add Transaction Section ");
			try {
				try {
					invoiceDate = request.getParameter("txtDate");
				} catch (Exception e) {
					System.out.println("Err in invoiceDate :: ");
				}
				try {

					currentBillAmount = Float.parseFloat(request
							.getParameter("txtCurrentBillAmount"));

				} catch (Exception e) {
					System.out.println("Err in currentBillAmount :: ");
				}
				try {

					detRemarks = request.getParameter("txtDetailRemarks");

				} catch (Exception e) {
					System.out.println("Err in txtDetailRemarks :: ");
				}

				try {
					userId = (String) session.getAttribute("UserId");

					System.out.println("The Current User : " + userId);
				} catch (Exception e) {
					System.out.println("Err in userId :: " + userId);
				}

				try {

					arAccUnitId = request.getParameterValues("hdnAccUnitId");
					arOfficeId = request.getParameterValues("hdnAccOfficeId");
					arPhoneNo = request.getParameterValues("hdnPhoneNo");
					arInvoiceNo = request.getParameterValues("hdnInvoiceNo");
					arInvoiceDate = request.getParameterValues("hdnBillDate");
					arInvoiceYear = request.getParameterValues("hdnBillYear");
					arInvoiceMonth = request.getParameterValues("hdnBillMonth");
					arBillAmount = request
							.getParameterValues("hdnCurrentBillAmount");
					arParticulars = request
							.getParameterValues("hdnDetailRemarks");
					arDetBillNo = request.getParameterValues("hdnDetBillNo");
					// invoiceNo =
					// Integer.parseInt(request.getParameter("txtInvoiceNo"));

				} catch (Exception e) {
					System.out.println("Err in getting array values :: " + e);
				}
			} catch (NullPointerException ex) {
				System.out
						.println("----------------Null Pointer Error Occured---------------");
			} catch (Exception e) {
				System.out.println("Error is :" + e);
			}

			// -------------------------------------------------------------------------//

			String existInvoiceNo = "";
			// Code for generate new bIll No --/
			

			sql = " SELECT BILL_NO FROM (select BILL_NO from FAS_PHONE_BILL_MST ORDER BY BILL_NO DESC) WHERE ROWNUM = ?";
			
			int newBillNo=0;

			try {
				ps2 = con.prepareStatement(sql);
				ps2.setInt(1, 1);
				rs2 = ps2.executeQuery();
				
				if (rs2.next()) {
					
					newBillNo = rs2.getInt("BILL_NO")+1;
				}
				else {
					newBillNo=100;
				}
			} catch (Exception e) {
				System.out.println("Exception in assigning..." + e);
			}

			genBillNo = newBillNo;
			//--------------------------------------------------------
			// -----------------------------Code for insert the record in
			// Fas_Phone_Bill_Mst-------------------------------------------------

			try {
				con.setAutoCommit(false);
			} catch (Exception ex) {
				System.out.println("Error in auto commit : " + ex.getMessage());
			}
			
			
			try {

				System.out.println("Maseter Record values are :");

				System.out.println(accUnitId);
				System.out.println(accOfficeId);
				System.out.println(billDate);
				System.out.println(bookYear);
				System.out.println(bookMonth);
				System.out.println(genBillNo);
				System.out.println(genRemarks);
				System.out.println(totalBillAmount);
				System.out.println(userId);

				sql = "insert into FAS_PHONE_BILL_MST("
						+ "ACCOUNTING_UNIT_ID,"
						+ "ACCOUNTING_UNIT_OFFICE_ID,"
						+ "BILL_DATE"
						+ ",CASHBOOK_YEAR,"
						+ "CASHBOOK_MONTH,"
						+ "BILL_NO,"
						+ "REMARKS,"
						+ "TOTAL_BILL_AMOUNT,"
						+ "UPDATED_BY_USER_ID,"
						+ "UPDATED_DATE,STATUS)"
						+ " values(?,?,TO_Date(?,'DD.MM.YYYY'),?,?,?,?,?,?,now(),?)";

				ps1 = con.prepareStatement(sql);

				ps1.setInt(1, Integer.parseInt(accUnitId));
				ps1.setInt(2, Integer.parseInt(accOfficeId));
				ps1.setString(3, billDate);
				ps1.setInt(4, bookYear);
				ps1.setInt(5, bookMonth);
				ps1.setLong(6, genBillNo);
				ps1.setString(7, genRemarks);
				ps1.setFloat(8, totalBillAmount);
				ps1.setString(9, userId);
				ps1.setString(10, "Y");

				billMstRows = ps1.executeUpdate();

				if (billMstRows >= 0) {
					// con.commit();
					System.out.println(" Master Record inserted Successfully");

				} else {
					System.out.println("Record Not inserted Successfully");

					con.rollback();
					con.commit();

				}
			} catch (Exception e) {
				try {
					// con.rollback();
					// con.commit();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				System.out.println("Exception in assigning data to Master..."
						+ e);

			}

			// -----------------------------Code for insert the Master record in
			// to
			// Fas_Phone_Bill_Mst(End)-------------------------------------------------

			// -----------------------------Code for insert the Transaction
			// record in to
			// Fas_Phone_Bill_Trn(End)-------------------------------------------------

			System.out.println("-------------Grid Values ----------------");

			for (int k = 0; k < arAccUnitId.length; k++) {

				accUnitId = arAccUnitId[k];
				accOfficeId = arOfficeId[k];
				phoneNo = arPhoneNo[k];
				invoiceNo = Integer.parseInt(arInvoiceNo[k]);
				invoiceDate = arInvoiceDate[k];
				billYear = Integer.parseInt(arInvoiceYear[k]);
				billMonth = Integer.parseInt(arInvoiceMonth[k]);
				currentBillAmount = Float.parseFloat(arBillAmount[k]);
				detRemarks = arParticulars[k];
				//detBillNo = Integer.parseInt(arDetBillNo[k]);

				System.out.println("Record   : " + k + 1);

				System.out.println(" User Id =" + userId);
				System.out.println(" Unit Id =" + accUnitId);
				System.out.println(" Office Id =" + accOfficeId);
				System.out.println(" Phone No =" + phoneNo);
				System.out.println(" Invoice No =" + invoiceNo);
				System.out.println(" Invoice Date =" + invoiceDate);
				System.out.println(" invoice Year =" + billYear);
				System.out.println(" invoice Month =" + billMonth);
				System.out.println(" invoice Amount =" + currentBillAmount);
				System.out.println(" Bill No =" + genBillNo);
				System.out.println(" invoice particulars =" + detRemarks
						+ "\n\n\n\n\n");

				try {

					sql1 = "INSERT INTO "
							+ "FAS_PHONE_BILL_TRN ("
							+ "ACCOUNTING_UNIT_ID,"
							+ "ACCOUNTING_UNIT_OFFICE_ID,"
							+ "BILL_NO,"
							+ "PHONE_NO,"
							+ "INVOICE_NO,"
							+ "INVOICE_DATE,"
							+ "BILL_YEAR,"
							+ "BILL_MONTH,"
							+ "BILL_AMOUNT,"
							+ "PARTICULARS,"
							+ "UPDATED_USER_ID,"
							+ "UPDATED_DATE) "
							+ "values (?,?,?,?,?,TO_Date(?,'DD.MM.YYYY'),?,?,?,?,?,now())";

					ps2 = con.prepareStatement(sql1);
					ps2.setInt(1, Integer.parseInt(accUnitId));
					ps2.setInt(2, Integer.parseInt(accOfficeId));
					ps2.setInt(3, genBillNo);
					ps2.setLong(4, Long.parseLong(phoneNo));
					ps2.setInt(5, invoiceNo);
					ps2.setString(6, invoiceDate);
					ps2.setInt(7, billYear);
					ps2.setInt(8, billMonth);
					ps2.setFloat(9, currentBillAmount);
					ps2.setString(10, detRemarks);
					ps2.setString(11, userId);

					billTrnRows = ps2.executeUpdate();

					totAfctRows = totAfctRows + billTrnRows;

				} catch (Exception e) {
					try {

					} catch (Exception e2) {
						e2.printStackTrace();
					}
					System.out
							.println("Exception in assigning data to transaction table..."
									+ e);

				}

			}

			System.out
					.println("-------------Grid Values ----------------/n/n/n ");
			System.out.println(" Tot Affectted Rows : " + totAfctRows);
			System.out.println(" Length : " + arAccUnitId.length);

			if (arAccUnitId.length == totAfctRows && billMstRows > 0) {
				try {
					con.commit();
					System.out.println("Record inserted Successfully");
				} catch (Exception ex1) {
					System.out.println("Error in rollback :" + ex1);
				}

			} else {
				try {
					con.rollback();
					con.commit();
				} catch (Exception ex1) {
					System.out.println("Error in rollback :" + ex1);
				}

			}

			if (billMstRows > 0 && totAfctRows == arAccUnitId.length) {
				sendMessage(response, "Record Saved Successfully......", "ok");
			} else {
				sendMessage(response, "Failed To Save Record....... ", "ok");
			}

		}
	}

	
	 private void sendMessage(HttpServletResponse response,String msg,String bType)
	    {
	        try
	        {
	            String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
	            response.sendRedirect(url);
	        }
	        catch(IOException e)
	        {}
	    }
	 
	 

}

//----------------------------------------------------------------------------------------------------------


/*try
{
	sql1 = "select sum(total_bill_amount) from FAS_PHONE_BILL_MST " +
		"where ACCOUNTING_UNIT_ID=? and " +
		"ACCOUNTING_UNIT_OFFICE_ID=? and " +
		"CASHBOOK_YEAR =? and " +
		"CASHBOOK_MONTH=? and " +
		"PHONE_NO=?";

	ps2 = con.prepareStatement(sql1);

	ps2.setInt(1, Integer.parseInt(accUnitId));
	ps2.setInt(2, Integer.parseInt(accOfficeId));
	ps2.setInt(3, bookYear);
	ps2.setInt(4, bookMonth);
	ps2.setLong(5, Long.parseLong(phoneNo));

	rs2 = ps2.executeQuery();
	if(rs2.next())
	{
		lstTotAmount = rs2.getFloat(1);
		
		finalAmount = lstTotAmount + currentBillAmount;
		System.out.println(" The  total Amount is :" + lstTotAmount);
		System.out.println(" The current Amount is :" + currentBillAmount);
		System.out.println(" The final Amount is :" + finalAmount);
		
		sql2 = "UPDATE FAS_PHONE_BILL_MST SET TOTAL_BILL_AMOUNT=? WHERE " +
				"ACCOUNTING_UNIT_ID=? AND " +
				"ACCOUNTING_UNIT_OFFICE_ID=? AND " +
				"CASHBOOK_YEAR=? AND " +
				"CASHBOOK_MONTH=? AND PHONE_NO=?";
		try
		{
			ps3 = con.prepareStatement(sql2);
		
			ps3.setFloat(1, finalAmount);
			ps3.setInt(2, Integer.parseInt(accUnitId));
			ps3.setInt(3, Integer.parseInt(accOfficeId));
			ps3.setInt(4, bookYear);
			ps3.setInt(5, bookMonth);
			ps3.setLong(6, Long.parseLong(phoneNo));
			updatedRows = ps3.executeUpdate();
			System.out.println("The update row = : "+ updatedRows );
			
		}
		catch(Exception ex2)
		{
			System.out.println("Error in updation"+ ex2);
		}
		
	}
	
	
	

}
catch(Exception ex3)
{
	System.out.println("Error at select amount : " + ex3);
}*/




//----------------------------------------------------------------------------------------------------------

/*
 			if (message.equalsIgnoreCase("AddMst")) 
			{
				sql = "SELECT * FROM FAS_PHONE_BILL_MST WHERE "
						+ "ACCOUNTING_UNIT_ID=? AND "
						+ "ACCOUNTING_UNIT_OFFICE_ID = ? AND "
						+ "CASHBOOK_YEAR = ? AND "
						+ "CASHBOOK_MONTH = ? AND BILL_NO = ?";

				try {
					ps1 = con.prepareStatement(sql);
					ps1.setInt(1, Integer.parseInt(accUnitId));
					ps1.setInt(2, Integer.parseInt(accOfficeId));
					ps1.setInt(3, bookYear);
					ps1.setInt(4, bookMonth);
					ps1.setInt(5, genBillNo);
					rs1 = ps1.executeQuery();

					if (rs1.next()) 
					{
						System.out
								.println("------------>Master Record Alredady Exist (Add Mst Section)......");
						
						sendMessage(response,
								"Master Record Exists For This Same", "ok");
					} else {
						System.out
								.println("------------>Record Not Available......");

						sql = "insert into FAS_PHONE_BILL_MST("
								+ "ACCOUNTING_UNIT_ID,"
								+ "ACCOUNTING_UNIT_OFFICE_ID,"
								+ "BILL_DATE"
								+ ",CASHBOOK_YEAR,"
								+ "CASHBOOK_MONTH,"
								+ "BILL_NO,"
								+ "REMARKS,"
								+ "TOTAL_BILL_AMOUNT,"
								+ "UPDATED_BY_USER_ID,"
								+ "UPDATED_DATE,STATUS)"
								+ " values(?,?,TO_Date(?,'DD.MM.YYYY'),?,?,?,?,?,?,now(),?)";

						try 
						{
							ps1 = con.prepareStatement(sql);
							
							ps1.setInt(1, Integer.parseInt(accUnitId));
							ps1.setInt(2, Integer.parseInt(accOfficeId));
							ps1.setString(3, billDate);
							ps1.setInt(4, bookYear);
							ps1.setInt(5, bookMonth);
							ps1.setLong(6, genBillNo);
							ps1.setString(7, genRemarks);
							ps1.setFloat(8, totalBillAmount);
							ps1.setString(9, userId);
							ps1.setString(10, "Y");

							billMstRows = ps1.executeUpdate();

							if (billMstRows >= 0) 
							{
								con.commit();
								System.out
										.println("Record inserted Successfully");
								sendMessage(response,
										"Master Record Added Successfully....",
										"ok");
							}
							else 
							{
								con.rollback();
								con.commit();
								sendMessage(response,
										"Master Record Creation Failed....",
										"ok");

							}
						}
						catch (Exception e) 
						{
							try 
							{
								con.rollback();
								con.commit();
							}
							catch (Exception e2) 
							{
								e2.printStackTrace();
							}
							System.out.println("Exception in assigning..." + e);

						}
					}
				} catch (Exception ex) {
					System.out.println("Error in AddMst Block : "
							+ ex.getMessage());
					sendMessage(response, "Master Record Creation Error :"
							+ ex.getMessage(), "ok");
				}

			}
			*/
 

//----------------------------------------------------------------------------------------------------

/*	try
{
	sql1 = "select sum(total_bill_amount) from FAS_PHONE_BILL_MST " +
		"where ACCOUNTING_UNIT_ID=? and " +
		"ACCOUNTING_UNIT_OFFICE_ID=? and " +
		"CASHBOOK_YEAR =? and " +
		"CASHBOOK_MONTH=? and " +
		"PHONE_NO=?";

	ps2 = con.prepareStatement(sql1);

	ps2.setInt(1, Integer.parseInt(accUnitId));
	ps2.setInt(2, Integer.parseInt(accOfficeId));
	ps2.setInt(3, bookYear);
	ps2.setInt(4, bookMonth);
	ps2.setLong(5, Long.parseLong(phoneNo));

	rs2 = ps2.executeQuery();
	if(rs2.next())
	{
		lstTotAmount = rs2.getFloat(1);
		
		finalAmount = lstTotAmount + currentBillAmount;
		System.out.println(" The  total Amount is :" + lstTotAmount);
		System.out.println(" The current Amount is :" + currentBillAmount);
		System.out.println(" The final Amount is :" + finalAmount);
		
		sql2 = "UPDATE FAS_PHONE_BILL_MST SET TOTAL_BILL_AMOUNT=? WHERE " +
				"ACCOUNTING_UNIT_ID=? AND " +
				"ACCOUNTING_UNIT_OFFICE_ID=? AND " +
				"CASHBOOK_YEAR=? AND " +
				"CASHBOOK_MONTH=? AND PHONE_NO=?";
		try
		{
			ps3 = con.prepareStatement(sql2);
		
			ps3.setFloat(1, finalAmount);
			ps3.setInt(2, Integer.parseInt(accUnitId));
			ps3.setInt(3, Integer.parseInt(accOfficeId));
			ps3.setInt(4, bookYear);
			ps3.setInt(5, bookMonth);
			ps3.setLong(6, Long.parseLong(phoneNo));
			updatedRows = ps3.executeUpdate();
			System.out.println("The update row = : "+ updatedRows );
			
		}
		catch(Exception ex2)
		{
			System.out.println("Error in updation"+ ex2);
		}
		
	}
	
	
	

}
catch(Exception ex3)
{
	System.out.println("Error at select amount : " + ex3);
}   */


//--------------------------------------------------------------------------------------------------------


/*	if (rs1.next())
{

	System.out
			.println("------------>Master Record Alredady Exist (AddTrn Section)......");

	boolean test = true;
	
	System.out.println("-------------Grid Values ----------------");
	for (int k = 0; k < arAccUnitId.length; k++) 
	{

		try {
			accUnitId = arAccUnitId[k];
			accOfficeId = arOfficeId[k];
			phoneNo = arPhoneNo[k];
			invoiceNo = Integer.parseInt(arInvoiceNo[k]);
			invoiceDate = arInvoiceDate[k];
			billYear = Integer.parseInt(arInvoiceYear[k]);
			billMonth = Integer.parseInt(arInvoiceMonth[k]);
			currentBillAmount = Float.parseFloat(arBillAmount[k]);
			detRemarks = arParticulars[k];
			detBillNo = Integer.parseInt(arDetBillNo[k]);
								
			System.out.println("Record   : " + k + 1);
			
			System.out.println(" User Id =" + userId);
			System.out.println(" Unit Id =" + accUnitId);
			System.out.println(" Office Id =" + accOfficeId);
			System.out.println(" Phone No =" + phoneNo);
			System.out.println(" Invoice No =" + invoiceNo);
			System.out.println(" Invoice Date =" + invoiceDate);
			System.out.println(" invoice Year =" + billYear);
			System.out.println(" invoice Month ="+ billMonth);
			System.out.println(" invoice Amount ="+ currentBillAmount);
			System.out.println("Detail Bill No = " + detBillNo);
			System.out.println(" invoice particulars ="	+ detRemarks + "\n\n\n\n\n");

	
			
			

			try {
				sql1 = "INSERT INTO "
						+ "FAS_PHONE_BILL_TRN ("
						+ "ACCOUNTING_UNIT_ID,"
						+ "ACCOUNTING_UNIT_OFFICE_ID," 
						+ "BILL_NO"
						+ "PHONE_NO,"
						+ "INVOICE_NO,"
						+ "INVOICE_DATE,"
						+ "BILL_YEAR,"
						+ "BILL_MONTH,"
						+ "BILL_AMOUNT,"
						+ "PARTICULARS,"
						+ "UPDATED_USER_ID,"
						+ "UPDATED_DATE) "
						+ "values (?,?,?,?,TO_Date(?,'DD.MM.YYYY'),?,?,?,?,?,now())";

				ps2 = con.prepareStatement(sql1);
				ps2.setInt(1, Integer.parseInt(accUnitId));
				ps2.setInt(2, Integer.parseInt(accOfficeId));
				ps2.setInt(3, detBillNo);
				ps2.setLong(4, Long.parseLong(phoneNo));
				ps2.setInt(5, invoiceNo);
				ps2.setString(6, invoiceDate);
				ps2.setInt(7, billYear);
				ps2.setInt(8, billMonth);
				ps2.setFloat(9, currentBillAmount);
				ps2.setString(10, detRemarks);
				ps2.setString(11, userId);
				billTrnRows = ps2.executeUpdate();
				totAfctRows = totAfctRows + billTrnRows;
				
				
				float lstTotAmount=0,finalAmount=0;
				int updatedRows = 0;



				

			} catch (Exception e) {
				try {
					con.rollback();
					con.commit();
				} catch (Exception e2) {
					e2.printStackTrace();
				}
				System.out
						.println("Exception in assigning..."
								+ e);

			}
		}

		

	}
	System.out.println("-------------Grid Values ----------------/n/n/n ");
	System.out.println(" Tot Affectted Rows : " + totAfctRows);
	System.out.println(" Length : " + arAccUnitId.length);
	if (arAccUnitId.length == totAfctRows)
	{ try{
		con.commit();
		System.out
				.println("Record inserted Successfully");
		}catch(Exception ex1){System.out.println("Error in rollback :"+ex1);}
		
	} else {
		try{
		con.rollback();
		con.commit();
		}catch(Exception ex1){System.out.println("Error in rollback :"+ex1);}

	}

	if(billMstRows==0 && totAfctRows ==arAccUnitId.length)
	{
		sendMessage(response, "Record Added Successfully", "ok");
	}

  */
