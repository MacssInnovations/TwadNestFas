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

public class BRS_FollupActionTaken extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	public BRS_FollupActionTaken() {
		super();
	}

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		System.out.println("chk 1");
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		PreparedStatement ps = null;
		// PreparedStatement ps1 = null;
		// ResultSet rs1 = null;
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

		if (strCommand.equalsIgnoreCase("ListAll")) {

			xml = xml + "<response><command>ListAll</command>";
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
			String txtFromDate = request.getParameter("txtFromDate");
			String txtToDate = request.getParameter("txtToDate");

			String FromDate = null;
			String ToDate = null;
			String m = null;
			String m1 = null;
			String[] dd = txtFromDate.split("/");
			String[] dd1 = txtToDate.split("/");

			String dayF = dd[1];
			int monthF = Integer.parseInt(dd[0]);
			String yearF = (dd[2]).substring(2);

			String dayT1 = dd1[1];
			int monthT1 = Integer.parseInt(dd1[0]);
			String yearT1 = (dd1[2]).substring(2);

			if (monthF == 01) {
				m = "JAN";
			} else if (monthF == 02) {
				m = "FEB";
			} else if (monthF == 03) {
				m = "MAR";
			} else if (monthF == 04) {
				m = "APR";
			} else if (monthF == 05) {
				m = "MAY";
			} else if (monthF == 06) {
				m = "JUN";
			} else if (monthF == 07) {
				m = "JUL";
			} else if (monthF == 8) {
				m = "AUG";
			} else if (monthF == 9) {
				m = "SEP";
			} else if (monthF == 10) {
				m = "OCT";
			} else if (monthF == 11) {
				m = "NOV";
			} else if (monthF == 12) {
				m = "DEC";
			}

			if (monthT1 == 01) {
				m1 = "JAN";
			} else if (monthT1 == 02) {
				m1 = "FEB";
			} else if (monthT1 == 03) {
				m1 = "MAR";
			} else if (monthT1 == 04) {
				m1 = "APR";
			} else if (monthT1 == 05) {
				m1 = "MAY";
			} else if (monthT1 == 06) {
				m1 = "JUN";
			} else if (monthT1 == 07) {
				m1 = "JUL";
			} else if (monthT1 == 8) {
				m1 = "AUG";
			} else if (monthT1 == 9) {
				m1 = "SEP";
			} else if (monthT1 == 10) {
				m1 = "OCT";
			} else if (monthT1 == 11) {
				m1 = "NOV";
			} else if (monthT1 == 12) {
				m1 = "DEC";
			}
			FromDate = (dayF + "-" + m + "-" + yearF);
			ToDate = (dayT1 + "-" + m1 + "-" + yearT1);
			String EntryDate = null;
			String ActionDate = null;

			String Stringdate = null;
			String Stringdate1 = null;
			int count = 0;
			try {

				ps = connection
				.prepareStatement("SELECT * FROM ( SELECT * FROM ( SELECT SL_NO,CHEQUE_DD_NO,CR_AMOUNT," +
								"DR_AMOUNT FROM FAS_BRS_TRANSACTION WHERE ACCOUNTING_UNIT_ID =? AND ACCOUNTING_FOR_OFFICE_ID =?  AND CASHBOOK_YEAR            =?  AND CASHBOOK_MONTH           =?  AND ACCOUNT_NO               =? AND FOLLOW_UP_ACTION_REQUIRED='Y' AND TWAD_OR_NON_TWAD         ='T' ) X  LEFT OUTER JOIN  (  SELECT           *         FROM           (             SELECT               MAX(ACTION_NO) AS act_no             FROM               FAS_BRS_FOLLOWUP             WHERE               ACCOUNTING_UNIT_ID         =?             AND ACCOUNTING_FOR_OFFICE_ID =?              AND CASHBOOK_YEAR            =?             AND CASHBOOK_MONTH           =?             AND ACCOUNT_NO               =?             AND ACTION_DATE             >= '"
								+ FromDate
								+ "'             AND ACTION_DATE             <= '"
								+ ToDate
								+ "'             AND TWAD_OR_NON_TWAD         ='T'             GROUP BY                SL_NO          )           x         LEFT OUTER JOIN           (             SELECT               SL_NO AS slno,               ENTRY_DATE,               TWAD_OR_NON_TWAD,               DOC_NO,               DOC_TYPE,               ACTION_NO,               ACTION_DATE,              ACTION_TAKEN,              UPDATED_DATE            FROM              FAS_BRS_FOLLOWUP            WHERE              ACCOUNTING_UNIT_ID         =?            AND ACCOUNTING_FOR_OFFICE_ID =?            AND CASHBOOK_YEAR            =?            AND CASHBOOK_MONTH           =?            AND ACCOUNT_NO               =?            AND ACTION_DATE             >= '"
								+ FromDate
								+ "'            AND ACTION_DATE             <= '"
								+ ToDate
								+ "'            AND TWAD_OR_NON_TWAD         ='T'          )          y        ON          x.act_no=y.ACTION_NO      )      Y ON X.SL_NO = Y.slno    UNION    SELECT      *    FROM      (        SELECT          SL_NO,          CHEQUE_DD_NO,          CR_AMOUNT,          DR_AMOUNT        FROM          FAS_BRS_TRANSACTION        WHERE          ACCOUNTING_UNIT_ID         =?        AND ACCOUNTING_FOR_OFFICE_ID =?        AND CASHBOOK_YEAR            =?        AND CASHBOOK_MONTH           =?        AND ACCOUNT_NO               =?        AND FOLLOW_UP_ACTION_REQUIRED='Y'        AND TWAD_OR_NON_TWAD         ='NT'      )      X    LEFT OUTER JOIN      (        SELECT          *        FROM          (            SELECT              MAX(ACTION_NO) AS act_no            FROM              FAS_BRS_FOLLOWUP            WHERE              ACCOUNTING_UNIT_ID         =?            AND ACCOUNTING_FOR_OFFICE_ID =?            AND CASHBOOK_YEAR            =?            AND CASHBOOK_MONTH           =?            AND ACCOUNT_NO               =?            AND ACTION_DATE             >= '"
								+ FromDate
								+ "'            AND ACTION_DATE             <= '"
								+ ToDate
								+ "'            AND TWAD_OR_NON_TWAD         ='NT'            GROUP BY              SL_NO          )          x        LEFT OUTER JOIN          (            SELECT              SL_NO AS slno,              ENTRY_DATE,              TWAD_OR_NON_TWAD,              DOC_NO,              DOC_TYPE,              ACTION_NO,              ACTION_DATE,              ACTION_TAKEN,              UPDATED_DATE            FROM              FAS_BRS_FOLLOWUP            WHERE              ACCOUNTING_UNIT_ID         =?            AND ACCOUNTING_FOR_OFFICE_ID =?            AND CASHBOOK_YEAR            =?            AND CASHBOOK_MONTH           =?            AND ACCOUNT_NO               =?            AND ACTION_DATE             >= '"
								+ FromDate
								+ "'            AND ACTION_DATE             <= '"
								+ ToDate
								+ "'            AND TWAD_OR_NON_TWAD         ='NT'          )          y        ON          x.act_no=y.ACTION_NO      )      Y ON X.SL_NO = Y.slno  )WHERE  ACTION_TAKEN IS NOT NULL");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setInt(3, txtCB_Year);
				ps.setInt(4, txtCB_Month);
				ps.setInt(5, cmbBankAccNo);
				
				ps.setInt(6, cmbAcc_UnitCode);				
				ps.setInt(7, cmbOffice_code);
				ps.setInt(8, txtCB_Year);
				ps.setInt(9, txtCB_Month);
				ps.setInt(10, cmbBankAccNo);
				
				ps.setInt(11, cmbAcc_UnitCode);
				ps.setInt(12, cmbOffice_code);
				ps.setInt(13, txtCB_Year);
				ps.setInt(14, txtCB_Month);
				ps.setInt(15, cmbBankAccNo);
				
				ps.setInt(16, cmbAcc_UnitCode);
				ps.setInt(17, cmbOffice_code);
				ps.setInt(18, txtCB_Year);
				ps.setInt(19, txtCB_Month);
				ps.setInt(20, cmbBankAccNo);
				
				ps.setInt(21, cmbAcc_UnitCode);
				ps.setInt(22, cmbOffice_code);
				ps.setInt(23, txtCB_Year);
				ps.setInt(24, txtCB_Month);
				ps.setInt(25, cmbBankAccNo);
				
				ps.setInt(26, cmbAcc_UnitCode);				
				ps.setInt(27, cmbOffice_code);
				ps.setInt(28, txtCB_Year);
				ps.setInt(29, txtCB_Month);
				ps.setInt(30, cmbBankAccNo);
				
				/*ps.setInt(31, cmbAcc_UnitCode);
				ps.setInt(32, cmbOffice_code);
				ps.setInt(33, txtCB_Year);
				ps.setInt(34, txtCB_Month);
				ps.setInt(35, cmbBankAccNo);
				
				ps.setInt(36, cmbAcc_UnitCode);
				ps.setInt(37, cmbOffice_code);
				ps.setInt(38, txtCB_Year);
				ps.setInt(39, txtCB_Month);
				ps.setInt(40, cmbBankAccNo);*/

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
