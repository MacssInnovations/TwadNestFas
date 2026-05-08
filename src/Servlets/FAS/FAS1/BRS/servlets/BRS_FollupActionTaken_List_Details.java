package Servlets.FAS.FAS1.BRS.servlets;

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
 * Servlet implementation class BRS_FollupActionTaken_List_Details
 */
public class BRS_FollupActionTaken_List_Details extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BRS_FollupActionTaken_List_Details() {
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

		if (strCommand.equalsIgnoreCase("getPreviousDetails")) {
			xml = xml + "<response><command>getPreviousDetails</command>";
			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			int txtCB_Year = Integer.parseInt(request
					.getParameter("txtCB_Year"));
			int txtCB_Month = Integer.parseInt(request
					.getParameter("txtCB_Month"));
			int cmbBankAccNo = Integer.parseInt(request
					.getParameter("cmbBankAccNo"));
			String TwadNonTwad = request.getParameter("TwadNonTwad");
			int slno1 = Integer.parseInt(request.getParameter("slno"));
			int ActionNo = Integer.parseInt(request.getParameter("ActionNo"));

			String EntryDate = null;
			String ActionDate = null;

			String Stringdate = null;
			String Stringdate1 = null;
			int count = 0;
			try {

				ps = connection
						.prepareStatement("SELECT * FROM  (SELECT SL_NO,    CHEQUE_DD_NO,    CR_AMOUNT,  "
								+ "  DR_AMOUNT  FROM FAS_BRS_TRANSACTION   WHERE ACCOUNTING_UNIT_ID     =?"
								+ "  AND ACCOUNTING_FOR_OFFICE_ID =?  AND CASHBOOK_YEAR            =? "
								+ " AND CASHBOOK_MONTH           =?  AND ACCOUNT_NO               =? "
								+ " AND SL_NO                    =?  AND FOLLOW_UP_ACTION_REQUIRED='Y' "
								+ " AND TWAD_OR_NON_TWAD         =?    )X LEFT OUTER JOIN   (SELECT SL_NO "
								+ "AS slno,    ENTRY_DATE,    TWAD_OR_NON_TWAD,    DOC_NO,    DOC_TYPE,   "
								+ " ACTION_NO,    ACTION_DATE,    ACTION_TAKEN,    UPDATED_DATE  "
								+ "FROM FAS_BRS_FOLLOWUP   WHERE ACCOUNTING_UNIT_ID    =? "
								+ " AND ACCOUNTING_FOR_OFFICE_ID=?  AND CASHBOOK_YEAR           =? "
								+ " AND CASHBOOK_MONTH          =?  AND ACCOUNT_NO              =?  "
								+ "AND ACTION_NO              !=?  AND TWAD_OR_NON_TWAD        =?  ) Y "
								+ "ON X.SL_NO = Y.slno");

				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setInt(3, txtCB_Year);
				ps.setInt(4, txtCB_Month);
				ps.setInt(5, cmbBankAccNo);
				ps.setInt(6, slno1);
				ps.setString(7, TwadNonTwad);
				ps.setInt(8, cmbAcc_UnitCode);
				ps.setInt(9, cmbOffice_code);
				ps.setInt(10, txtCB_Year);
				ps.setInt(11, txtCB_Month);
				ps.setInt(12, cmbBankAccNo);				
				ps.setInt(13, ActionNo);
				ps.setString(14, TwadNonTwad);
				results = ps.executeQuery();

				while (results.next()) {
					int slno = results.getInt("SL_NO");
					Date EntryDate1 = results.getDate("ENTRY_DATE");
					Date ActionDate1 = results.getDate("ACTION_DATE");

					try {
						Stringdate = EntryDate1.toString();
					} catch (Exception e) {
						Stringdate = "0000-00-00";
					}
					try {
						Stringdate1 = ActionDate1.toString();
					} catch (Exception e) {
						Stringdate1 = "0000-00-00";
					}

					String[] ddd = Stringdate.split("-");
					String[] ddd1 = Stringdate1.split("-");

					int day = Integer.parseInt(ddd[2]);
					int month = Integer.parseInt(ddd[1]);
					int year = Integer.parseInt(ddd[0]);

					int day1 = Integer.parseInt(ddd1[2]);
					int month1 = Integer.parseInt(ddd1[1]);
					int year1 = Integer.parseInt(ddd1[0]);

					if (month >= 10) {
						EntryDate = (day + "/" + month + "/" + year);
					} else {
						EntryDate = (day + "/0" + month + "/" + year);
					}

					if (month1 >= 10) {
						ActionDate = (day1 + "/" + month1 + "/" + year1);
					} else {
						ActionDate = (day1 + "/0" + month1 + "/" + year1);
					}

					xml = xml + "<SerialNumber>" + slno + "</SerialNumber>";
					xml = xml + "<EntryDate>" + EntryDate + "</EntryDate>";
					xml = xml + "<ActionDate>" + ActionDate + "</ActionDate>";
					xml = xml + "<TwadNonTwad>"
							+ results.getString("TWAD_OR_NON_TWAD")
							+ "</TwadNonTwad>";
					xml = xml + "<Doc_No>" + results.getInt("DOC_NO")
							+ "</Doc_No>";
					xml = xml + "<DocType>" + results.getString("DOC_TYPE")
							+ "</DocType>";
					xml = xml + "<ActionNo>" + results.getInt("ACTION_NO")
							+ "</ActionNo>";
					xml = xml + "<ActionTaken>"
							+ results.getString("ACTION_TAKEN")
							+ "</ActionTaken>";

					xml = xml + "<Cheqe_or_DDNo>"
							+ results.getInt("CHEQUE_DD_NO")
							+ "</Cheqe_or_DDNo>";

					xml = xml + "<CRAmount>" + results.getFloat("CR_AMOUNT")
							+ "</CRAmount>";

					xml = xml + "<DRAmount>" + results.getFloat("DR_AMOUNT")
							+ "</DRAmount>";
					count++;
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
