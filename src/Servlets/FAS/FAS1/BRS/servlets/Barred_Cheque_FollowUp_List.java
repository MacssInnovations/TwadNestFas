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
import java.sql.Timestamp;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Barred_Cheque_FollowUp_List
 */
public class Barred_Cheque_FollowUp_List extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Barred_Cheque_FollowUp_List() {
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

		System.out.println("chk 1");
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null;
		ResultSet rs2 = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;

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

		System.out.println("chk 2");

		String strCommand = "";
		String xml = "";

		HttpSession session = request.getSession(false);
		try {

			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}
			System.out.println(session);

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
			System.out.println(strCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}

		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
		if (strCommand.equalsIgnoreCase("list")) {
			xml = xml + "<response><command>list</command>";

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
			try {
				ps = connection
						.prepareStatement("select DOC_TYPE,CHEQUE_NO,DOC_NO,DOC_DATE,CHEQUE_DATE,CHEQUE_AMOUNT,CHEQUE_VALID_UPTO,FOLLOWUP_ACTION from FAS_BRS_BARRED_CHEQUE_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=? AND CLEARED_ENTRIES=?");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setInt(3, txtCB_Year);
				ps.setInt(4, txtCB_Month);
				ps.setInt(5, cmbBankAccNo);
				ps.setString(6, "N");
				rs = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				String ChequeDate = null;
				String DocDate = null;
				String CheckValidUpto = null;
				String ClearedDate = null;
				while (rs.next()) {
					Date ChequeDate1 = rs.getDate("CHEQUE_DATE");
					Date DocDate1 = rs.getDate("DOC_DATE");
					Date CheckValidUpto1 = rs.getDate("CHEQUE_VALID_UPTO");

					String Stringdate = ChequeDate1.toString();
					String Stringdate1 = DocDate1.toString();
					String Stringdate2 = CheckValidUpto1.toString();

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
						ChequeDate = (day + "/" + month + "/" + year);
					} else {
						ChequeDate = (day + "/0" + month + "/" + year);
					}

					if (month1 >= 10) {
						DocDate = (day1 + "/" + month1 + "/" + year1);
					} else {
						DocDate = (day1 + "/0" + month1 + "/" + year1);
					}

					if (month2 >= 10) {
						CheckValidUpto = (day2 + "/" + month2 + "/" + year2);
					} else {
						CheckValidUpto = (day2 + "/0" + month2 + "/" + year2);
					}

					xml = xml + "<Doc_Type>" + rs.getString("DOC_TYPE")
							+ "</Doc_Type>";
					xml = xml + "<Cheque_No>" + rs.getInt("CHEQUE_NO")
							+ "</Cheque_No>";
					xml = xml + "<Doc_No>" + rs.getInt("DOC_NO") + "</Doc_No>";
					xml = xml + "<ChequeDate>" + ChequeDate + "</ChequeDate>";
					xml = xml + "<DocDate>" + DocDate + "</DocDate>";
					xml = xml + "<ChequeAmount>" + rs.getFloat("CHEQUE_AMOUNT")
							+ "</ChequeAmount>";
					xml = xml + "<CheckValidUpto>" + CheckValidUpto
							+ "</CheckValidUpto>";
					xml = xml + "<Follow_Up>" + rs.getString("FOLLOWUP_ACTION")
							+ "</Follow_Up>";
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

}
