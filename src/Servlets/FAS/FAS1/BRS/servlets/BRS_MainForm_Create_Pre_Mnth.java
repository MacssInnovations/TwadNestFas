package Servlets.FAS.FAS1.BRS.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
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
 * Servlet implementation class BRS_MainForm_Create_Pre_Mnth
 */
public class BRS_MainForm_Create_Pre_Mnth extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BRS_MainForm_Create_Pre_Mnth() {
		super();

	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
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
			strCommand = request.getParameter("Command");
			System.out.println(strCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}

		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);

		if (strCommand.equalsIgnoreCase("LoadTWADTransactions")) {
			xml = "<response><command>LoadTWADTransactions</command>";

			int cmbAcc_UnitCode = 0;
			int cmbOffice_code = 0;
			int txtCB_Year = 0;
			int txtCB_Month = 0;
			long cmbBankAccNo = 0;

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

			/* Get Bank Account Number */
			try {
				cmbBankAccNo = Long.parseLong(request
						.getParameter("cmbBankAccNo"));
			} catch (Exception e) {
				System.out.println("Error Not Getting Bank Account Number -->"
						+ e);
			}
			
			
			try {
				txtCB_Year = Integer.parseInt(request
						.getParameter("txtCB_Year"));
			} catch (Exception e) {
				System.out
						.println("Error Not Getting Cashbook Year -->"
								+ e);
			}

			/* Get Cashbook Month */
			try {
				txtCB_Month = Integer.parseInt(request
						.getParameter("txtCB_Month"));
			} catch (Exception e) {
				System.out
						.println("Error Not Getting Cashbook Month -->"
								+ e);
			}
			
			

			String filterflag = request.getParameter("filterflag");

			String r_date = null;
			String w_date = null;
			String doc_date = null;
			String Stringdate = null;
			String Stringdate1 = null;
			String Stringdate2 = null;
			Calendar cal = Calendar.getInstance();

			int f_month = cal.get(Calendar.MONTH) + 1;
			System.out.println("f_month >>> " + f_month);
			int f_year = cal.get(Calendar.YEAR);
			int f_year1 = f_year - 1;
			String mode_id=null;
			String sqlrpt="";
			ResultSet rs1=null;
			int countrpt=0,start_cb_yr=0,start_cb_mon=0;
			try {

				try
				{
					PreparedStatement psss=connection.prepareStatement("select trim(AC_OPERATIONAL_MODE_ID)as mode_id from fas_mst_bank_balance" +
							" where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and BANK_AC_NO="+cmbBankAccNo+" and STATUS='Y'");
					
					ResultSet res=psss.executeQuery();
					if(res.next())
					{
						mode_id=res.getString("mode_id");
					}
					
					ps = connection.prepareStatement("SELECT CASHBOOK_YEAR AS CB_YEAR,CASHBOOK_MONTH as CB_Month from BRS_START_MONTH_AND_YEAR where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and account_no="+cmbBankAccNo);
					System.out.println("SELECT CASHBOOK_YEAR AS CB_YEAR,CASHBOOK_MONTH as CB_Month from BRS_START_MONTH_AND_YEAR where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and account_no="+cmbBankAccNo);
					ResultSet res1=ps.executeQuery();
					if(res1.next())
					{
						start_cb_yr=res1.getInt("CB_YEAR");
						start_cb_mon=res1.getInt("CB_Month");
					}
					
					
				}
				catch(Exception ex)
				{
					System.out.println("excep in mode_id::"+ex);
				}
				
				System.out.println("Mode_ID==>"+mode_id);
				System.out.println("Start_Yr===>"+start_cb_yr);
				System.out.println("Start_Mon===>"+start_cb_mon);
				
				if(mode_id.equalsIgnoreCase("OPR"))
				{
					System.out.println("Welcome");
					sqlrpt="SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,PASS_SHEET_YEAR,PASS_SHEET_MONTH\r\n" + 
							"FROM FAS_BRS_PART_2A\r\n" + 
							"WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+"\r\n" + 
							"AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+"\r\n" + 						
							"AND PASS_SHEET_YEAR         ="+start_cb_yr+"\r\n" + 
							"AND PASS_SHEET_MONTH        ="+start_cb_mon+"\r\n" + 
							"AND ACCOUNT_NO              ="+cmbBankAccNo+"\r\n" + 
							"union all\r\n" + 
							"SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,PASS_SHEET_YEAR,PASS_SHEET_MONTH\r\n" + 
							"FROM FAS_BRS_PART_2B\r\n" + 
							"WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+"\r\n" + 
							"AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+"\r\n" + 						
							"AND PASS_SHEET_YEAR         ="+start_cb_yr+"\r\n" + 
							"AND PASS_SHEET_MONTH        ="+start_cb_mon+"\r\n" + 
							"AND ACCOUNT_NO              ="+cmbBankAccNo+"\r\n" + 
							"UNION all\r\n" + 
							"SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,PASS_SHEET_YEAR,PASS_SHEET_MONTH\r\n" + 
							"FROM FAS_BRS_PART_2C\r\n" + 
							"WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+"\r\n" + 
							"AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+"\r\n" + 						
							"AND PASS_SHEET_YEAR         ="+start_cb_yr+"\r\n" + 
							"AND PASS_SHEET_MONTH        ="+start_cb_mon+"\r\n" + 
							"AND ACCOUNT_NO              ="+cmbBankAccNo+"\r\n" ;
							 ;
					System.out.println("sqlrpt====>"+sqlrpt);
					  ps1 = connection.prepareStatement(sqlrpt);
						rs1 = ps1.executeQuery();
						if(rs1.next())
						{
						 xml = xml + "<filterFlag>no</filterFlag><flag>AlreadyFreezed</flag>";
						}
						else
						{
						 countrpt=1;
						}
					
				}
				else if(mode_id.equalsIgnoreCase("COL"))
				{
					sqlrpt="SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,PASS_SHEET_YEAR,PASS_SHEET_MONTH\r\n" + 
							"FROM FAS_BRS_PART1\r\n" + 
							"WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+"\r\n" + 
							"AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+"\r\n" + 						
							"AND PASS_SHEET_YEAR         ="+start_cb_yr+"\r\n" + 
							"AND PASS_SHEET_MONTH        ="+start_cb_mon+"\r\n" + 
							"AND ACCOUNT_NO              ="+cmbBankAccNo+"\r\n";
					System.out.println("sqlrpt==COL==>"+sqlrpt);
					  ps1 = connection.prepareStatement(sqlrpt);
						rs1 = ps1.executeQuery();
						if(rs1.next())
						{
						 xml = xml + "<filterFlag>no</filterFlag><flag>AlreadyFreezed</flag>";
						}
						else
						{
						 countrpt=1;
						}
				}
				else
				{
					
					sqlrpt="SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,PASS_SHEET_YEAR,PASS_SHEET_MONTH\r\n" + 
							"FROM FAS_BRS_PART_2A\r\n" + 
							"WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+"\r\n" + 
							"AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+"\r\n" + 						
							"AND PASS_SHEET_YEAR         ="+start_cb_yr+"\r\n" + 
							"AND PASS_SHEET_MONTH        ="+start_cb_mon+"\r\n" + 
							"AND ACCOUNT_NO              ="+cmbBankAccNo+"\r\n" + 
							"union all\r\n" + 
							"SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,PASS_SHEET_YEAR,PASS_SHEET_MONTH\r\n" + 
							"FROM FAS_BRS_PART_2B\r\n" + 
							"WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+"\r\n" + 
							"AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+"\r\n" + 						
							"AND PASS_SHEET_YEAR         ="+start_cb_yr+"\r\n" + 
							"AND PASS_SHEET_MONTH        ="+start_cb_mon+"\r\n" + 
							"AND ACCOUNT_NO              ="+cmbBankAccNo+"\r\n" + 
							"UNION all\r\n" + 
							"SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,PASS_SHEET_YEAR,PASS_SHEET_MONTH\r\n" + 
							"FROM FAS_BRS_PART_2C\r\n" + 
							"WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+"\r\n" + 
							"AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+"\r\n" + 						
							"AND PASS_SHEET_YEAR         ="+start_cb_yr+"\r\n" + 
							"AND PASS_SHEET_MONTH        ="+start_cb_mon+"\r\n" + 
							"AND ACCOUNT_NO              ="+cmbBankAccNo+"\r\n" + 
							"UNION all\r\n" + 
							"SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,PASS_SHEET_YEAR,PASS_SHEET_MONTH\r\n" + 
							"FROM FAS_BRS_PART1\r\n" + 
							"WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+"\r\n" + 
							"AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+"\r\n" + 						
							"AND PASS_SHEET_YEAR         ="+start_cb_yr+"\r\n" + 
							"AND PASS_SHEET_MONTH        ="+start_cb_mon+"\r\n" + 
							"AND ACCOUNT_NO              ="+cmbBankAccNo+"\r\n" ;
					System.out.println("sqlrpt==Others==>"+sqlrpt);
					  ps1 = connection.prepareStatement(sqlrpt);
					
						 rs1 = ps1.executeQuery();
						 if(rs1.next())
							{
							 xml = xml + "<filterFlag>no</filterFlag><flag>AlreadyFreezed</flag>";
							}
							else
							{
							 countrpt=1;
							}
					
				}
				
				
				System.out.println("filterflag==>filterflag"+filterflag);
				if(countrpt==1)
				{
				
				
				if (filterflag.equals("CashbookYrMnth")) {
					/* Get Cashbook Year */
					try {
						txtCB_Year = Integer.parseInt(request
								.getParameter("txtCB_Year"));
					} catch (Exception e) {
						System.out
								.println("Error Not Getting Cashbook Year -->"
										+ e);
					}

					/* Get Cashbook Month */
					try {
						txtCB_Month = Integer.parseInt(request
								.getParameter("txtCB_Month"));
					} catch (Exception e) {
						System.out
								.println("Error Not Getting Cashbook Month -->"
										+ e);
					}
					// System.out.println(" unit "+cmbAcc_UnitCode+"-office-"+cmbOffice_code+"  year "+txtCB_Year+"month "+txtCB_Month+"acno "+
					// cmbBankAccNo);
					// ps =
					// connection.prepareStatement("select REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,DOC_NO,DOC_TYPE,DOC_DATE from FAS_BRS_TRANSACTION_NOENTRY where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR<=? and CASHBOOK_MONTH<? and ACCOUNT_NO=? and doc_type !='J' order by REMITTANCE_DATE");

					String qry = "SELECT a.REMITTANCE_DATE, "
							+ " a.WITHDRAWAL_DATE,"
							+ " a.VOUCHER_OR_CHALLAN_NO,"
							+ " a.CHEQUE_DD_NO, a.CR_AMOUNT, a.DR_AMOUNT, a.DOC_NO,"
							+ " a.DOC_TYPE,"
							+ "  a.DOC_DATE"
							+ "  FROM FAS_BRS_TRANSACTION_NOENTRY a,BRS_START_MONTH_AND_YEAR b "
							+ "	  WHERE a.ACCOUNTING_UNIT_ID    = "+cmbAcc_UnitCode+ "	  AND a.ACCOUNTING_FOR_OFFICE_ID= "+cmbOffice_code+ " AND ((a.cashbook_year             <"+txtCB_Year+" AND " +
							"a.cashbook_month             <=12) OR (a.cashbook_year               ="+txtCB_Year+" AND a.cashbook_month             <="+txtCB_Month+")) AND a.ACCOUNT_NO              = "+cmbBankAccNo+"		  and a.doc_type !='J'  "
							+ "		  and( (a.CASHBOOK_YEAR = b.cashbook_year "
							+ "		  and a.CASHBOOK_MONTH < b.CASHBOOK_MONTH )"
							+ "  or (a.CASHBOOK_YEAR <b.cashbook_year ) )"
							+ "		 	  and a.ACCOUNTING_UNIT_ID =b.ACCOUNTING_UNIT_ID  "
							+ "		  and a.ACCOUNTING_FOR_OFFICE_ID =b.ACCOUNTING_FOR_OFFICE_ID  "
							+ " and a.ACCOUNT_NO =b.ACCOUNT_NO  "
							+ "  ORDER BY a.WITHDRAWAL_DATE,a.VOUCHER_OR_CHALLAN_NO ";
				
					System.out.println("test1"+qry);
					ps = connection.prepareStatement(qry);
				
					
					rs = ps.executeQuery();
				} else {
					if (f_month == 5) {
						String q = "select REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,DOC_NO,DOC_TYPE,DOC_DATE from FAS_BRS_TRANSACTION_NOENTRY where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and ACCOUNT_NO=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH between ? and ?   union select REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,DOC_NO,DOC_TYPE,DOC_DATE from FAS_BRS_TRANSACTION_NOENTRY where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and ACCOUNT_NO=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH between ? and ?  order by WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO";
						ps = connection.prepareStatement(q);
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setLong(3, cmbBankAccNo);
						ps.setInt(4, f_year);
						ps.setInt(5, 1);
						ps.setInt(6, 5);
						ps.setInt(7, cmbAcc_UnitCode);
						ps.setInt(8, cmbOffice_code);
						ps.setLong(9, cmbBankAccNo);
						ps.setInt(10, f_year - 1);
						ps.setInt(11, 12);
						ps.setInt(12, 12);
						rs = ps.executeQuery();
					} else if (f_month == 4) {
						String q = "select REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,DOC_NO,DOC_TYPE,DOC_DATE from FAS_BRS_TRANSACTION_NOENTRY where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and ACCOUNT_NO=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH between ? and ?   union select REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,DOC_NO,DOC_TYPE,DOC_DATE from FAS_BRS_TRANSACTION_NOENTRY where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and ACCOUNT_NO=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH between ? and ?  order by WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO";
						ps = connection.prepareStatement(q);
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setLong(3, cmbBankAccNo);
						ps.setInt(4, f_year);
						ps.setInt(5, 1);
						ps.setInt(6, 4);
						ps.setInt(7, cmbAcc_UnitCode);
						ps.setInt(8, cmbOffice_code);
						ps.setLong(9, cmbBankAccNo);
						ps.setInt(10, f_year - 1);
						ps.setInt(11, 11);
						ps.setInt(12, 12);
						rs = ps.executeQuery();
					} else if (f_month == 3) {
						String q = "select REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,DOC_NO,DOC_TYPE,DOC_DATE from FAS_BRS_TRANSACTION_NOENTRY where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and ACCOUNT_NO=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH between ? and ?   union select REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,DOC_NO,DOC_TYPE,DOC_DATE from FAS_BRS_TRANSACTION_NOENTRY where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and ACCOUNT_NO=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH between ? and ?  order by WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO";
						ps = connection.prepareStatement(q);
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setLong(3, cmbBankAccNo);
						ps.setInt(4, f_year);
						ps.setInt(5, 1);
						ps.setInt(6, 3);
						ps.setInt(7, cmbAcc_UnitCode);
						ps.setInt(8, cmbOffice_code);
						ps.setLong(9, cmbBankAccNo);
						ps.setInt(10, f_year - 1);
						ps.setInt(11, 10);
						ps.setInt(12, 12);
						rs = ps.executeQuery();
					} else if (f_month == 2) {
						String q = "select REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,DOC_NO,DOC_TYPE,DOC_DATE from FAS_BRS_TRANSACTION_NOENTRY where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and ACCOUNT_NO=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH between ? and ?   union select REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,DOC_NO,DOC_TYPE,DOC_DATE from FAS_BRS_TRANSACTION_NOENTRY where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and ACCOUNT_NO=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH between ? and ?  order by WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO";
						ps = connection.prepareStatement(q);
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setLong(3, cmbBankAccNo);
						ps.setInt(4, f_year);
						ps.setInt(5, 1);
						ps.setInt(6, 2);
						ps.setInt(7, cmbAcc_UnitCode);
						ps.setInt(8, cmbOffice_code);
						ps.setLong(9, cmbBankAccNo);
						ps.setInt(10, f_year1);
						ps.setInt(11, 9);
						ps.setInt(12, 12);
						rs = ps.executeQuery();
					} else if (f_month == 1) {
						String q = "select REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,DOC_NO,DOC_TYPE,DOC_DATE from FAS_BRS_TRANSACTION_NOENTRY where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and ACCOUNT_NO=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH between ? and ?   union select REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,DOC_NO,DOC_TYPE,DOC_DATE from FAS_BRS_TRANSACTION_NOENTRY where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and ACCOUNT_NO=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH between ? and ?  order by WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO";
						ps = connection.prepareStatement(q);
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setLong(3, cmbBankAccNo);
						ps.setInt(4, f_year);
						ps.setInt(5, 1);
						ps.setInt(6, 1);
						ps.setInt(7, cmbAcc_UnitCode);
						ps.setInt(8, cmbOffice_code);
						ps.setLong(9, cmbBankAccNo);
						ps.setInt(10, f_year - 1);
						ps.setInt(11, 8);
						ps.setInt(12, 12);
						rs = ps.executeQuery();
					} else {
						int mn=(f_month - 5);
						String q="select REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,DOC_NO,DOC_TYPE,DOC_DATE from FAS_BRS_TRANSACTION_NOENTRY where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and ACCOUNT_NO="+cmbBankAccNo+" and CASHBOOK_YEAR="+f_year+" and CASHBOOK_MONTH between "+mn+" and "+f_month+"  order by REMITTANCE_DATE";
						System.out.println("q:::"+q);
						ps = connection.prepareStatement(q);
					/*	ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setLong(3, cmbBankAccNo);
						ps.setInt(4, f_year);
						System.out.println("f_year:::" + f_year);
						ps.setInt(5, f_month - 5);
						System.out.println("fmo:::" + (f_month - 5));
						ps.setInt(6, f_month);
						System.out.println("f_month:::" + f_month); */
						rs = ps.executeQuery();
					}
				}
				xml = xml + "<flag>success</flag>";
				while (rs.next()) {
					Date r_date1 = rs.getDate("REMITTANCE_DATE");
					Date doc_date1 = rs.getDate("DOC_DATE");

					try {
						Stringdate = r_date1.toString();
					} catch (Exception e) {
						Stringdate = "0000-00-00";
					}

					try {
						Stringdate2 = doc_date1.toString();
					} catch (Exception e) {
						Stringdate2 = "0000-00-00";
					}

					String[] ddd = Stringdate.split("-");
					String[] ddd2 = Stringdate2.split("-");

					int day = Integer.parseInt(ddd[2]);
					int month = Integer.parseInt(ddd[1]);
					int year = Integer.parseInt(ddd[0]);

					int day2 = Integer.parseInt(ddd2[2]);
					int month2 = Integer.parseInt(ddd2[1]);
					int year2 = Integer.parseInt(ddd2[0]);
					// System.out.println("hhhhhhhhh");
					if (month >= 10) {
						r_date = (day + "/" + month + "/" + year);
					} else {
						r_date = (day + "/0" + month + "/" + year);
					}

					if (month2 >= 10) {
						doc_date = (day2 + "/" + month2 + "/" + year2);
					} else {
						doc_date = (day2 + "/0" + month2 + "/" + year2);
					}

					Date w_date1 = rs.getDate("WITHDRAWAL_DATE");
					try {
						Stringdate1 = w_date1.toString();
					} catch (Exception e) {
						Stringdate1 = "0000-00-00";
					}
					String[] ddd1 = Stringdate1.split("-");

					int day1 = Integer.parseInt(ddd1[2]);
					int month1 = Integer.parseInt(ddd1[1]);
					int year1 = Integer.parseInt(ddd1[0]);

					if (month1 >= 10) {
						w_date = (day1 + "/" + month1 + "/" + year1);
					} else {
						w_date = (day1 + "/0" + month1 + "/" + year1);
					}
				
					xml = xml + "<REMITTANCE_DATE>" + rs.getDate("REMITTANCE_DATE") + "</REMITTANCE_DATE>";
					xml = xml + "<WITHDRAWAL_DATE>" + rs.getDate("WITHDRAWAL_DATE") + "</WITHDRAWAL_DATE>";
					xml = xml + "<r_date>" + r_date + "</r_date>";
					xml = xml + "<w_date>" + w_date + "</w_date>";
					xml = xml + "<w_challan_no>"
							+ rs.getInt("VOUCHER_OR_CHALLAN_NO")
							+ "</w_challan_no>";
					xml = xml + "<r_cheque_dd_no>" + rs.getInt("CHEQUE_DD_NO")
							+ "</r_cheque_dd_no>";
					xml = xml + "<cr_amount>" + rs.getInt("CR_AMOUNT")
							+ "</cr_amount>";
					xml = xml + "<dr_amount>" + rs.getInt("DR_AMOUNT")
							+ "</dr_amount>";
					xml = xml + "<doc_no>" + rs.getInt("DOC_NO") + "</doc_no>";
					xml = xml + "<doc_type>" + rs.getString("DOC_TYPE")
							+ "</doc_type>";
					xml = xml + "<com_doc_date>" + doc_date + "</com_doc_date>";
				}

				String sql2 = "SELECT TRANS_CODE,TRANS_DESC FROM FAS_BRS_TRANSACTION_TYPE ";
				// System.out.println("sql2:::::"+sql2);
				ps1 = connection.prepareStatement(sql2);
				rs2 = ps1.executeQuery();

				while (rs2.next()) {
					xml = xml + "<reason_pair>";
					xml = xml + "<reason_code>" + rs2.getString("TRANS_CODE")
							+ "</reason_code>";
					xml = xml + "<reason_desc>" + rs2.getString("TRANS_DESC")
							+ "</reason_desc>";
					xml = xml + "</reason_pair>";
				}
				xml = xml + "<flag>success</flag>";
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		}
		else if (strCommand.equalsIgnoreCase("loadmnYEr")) {
			xml = "<response><command>loadmnYEr</command>";

			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			Long cmbAcc_no = Long.parseLong(request.getParameter("cmbBankAccNo"));
			try{
				PreparedStatement ps_MY=connection.prepareStatement("select CASHBOOK_YEAR,CASHBOOK_MONTH from BRS_START_MONTH_AND_YEAR where ACCOUNTING_UNIT_ID=? and ACCOUNT_NO =?");
				ps_MY.setInt(1, cmbAcc_UnitCode);
				ps_MY.setLong(2, cmbAcc_no);
				ResultSet rs_MY=ps_MY.executeQuery();
				while (rs_MY.next()) {
					xml = xml + "<count>";
					xml = xml + "<CASHBOOK_YEAR>" + rs_MY.getInt("CASHBOOK_YEAR")
							+ "</CASHBOOK_YEAR>";
					xml = xml + "<CASHBOOK_MONTH>" + rs_MY.getInt("CASHBOOK_MONTH")
							+ "</CASHBOOK_MONTH>";
					xml = xml + "</count>";
				}xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		}
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

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
int count_error=0;
		Connection con = null;
		PreparedStatement ps2 = null, pss = null, preps = null;
		CallableStatement cs = null;
		CallableStatement cs1 = null;

		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		ResultSet ress = null;
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

		/* Get Parameters */
		int cmbAcc_UnitCode = 0;
		int cmbOffice_code = 0;
		int txtCB_Year = 0;
		int txtCB_Month = 0;

		long cmbBankAccNo = 0;
		String txtOprMode = "";
		String query_acc_no = null;
		int txtBankID = 0;
		int txtBranchID = 0;
		float txtPBBalance = 0.0f;
		int month_insert = 0, year_insert = 0;
		/* Get Accounting Unit ID */
		try {
			cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			System.out.println("cmbAcc_UnitCode-->" + cmbAcc_UnitCode);
		} catch (Exception e) {
			System.out.println("Error Not Getting Accounitng Unit ID --> " + e);
		}

		/* Get Accounting for Office ID */
		try {
			cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			System.out.println("cmbOffice_code-->" + cmbOffice_code);

		} catch (Exception e) {
			System.out
					.println("Error Not Getting Accounting for Office Id --> "
							+ e);
		}

		/* Get Cashbook Year */
		try {
			txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
			System.out.println("txtCB_Year-->" + txtCB_Year);
		} catch (Exception e) {
			System.out.println("Error Not Getting Cashbook Year -->" + e);
		}

		/* Get Cashbook Month */
		try {
			txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
			System.out.println("txtCB_Month-->" + txtCB_Month);
		} catch (Exception e) {
			System.out.println("Error Not Getting Cashbook Month -->" + e);
		}

		/* Get Bank Account Number */
		try {
			cmbBankAccNo = Long.parseLong(request.getParameter("cmbBankAccNo"));
			System.out.println("cmbBankAccNo-->" + cmbBankAccNo);
		} catch (Exception e) {
			System.out.println("Error Not Getting Bank Account Number -->" + e);
		}

		try {
			String ac = "SELECT bank_ac_no,AC_OPERATIONAL_MODE_ID From Fas_Mst_Bank_Balance WHERE accounting_unit_id = "
					+ cmbAcc_UnitCode
					+ " and status='Y' and bank_ac_no="
					+ cmbBankAccNo;
			pss = con.prepareStatement(ac);
			ResultSet rss = pss.executeQuery();
			while (rss.next()) {
				query_acc_no = rss.getString("AC_OPERATIONAL_MODE_ID");// opr
			}
		} catch (Exception e) {
			System.out.println("Error Not Bank Account opr or col -->" + e);
		}
		System.out.println();
// w_date
		/* Get Pass Book Balance Amount */
		/*
		 * try { txtPBBalance = Float.parseFloat(request
		 * .getParameter(("txtPBBalance")));
		 * System.out.println("txtPBBalance-->" + txtPBBalance); } catch
		 * (Exception e) {
		 * System.out.println("Error Not Getting Pass Book Balance -->" + e); }
		 */

		/* User ID */
		String update_user = (String) session.getAttribute("UserId");
		System.out.println("update_user-->" + update_user);

		/* Get Time Stamp */
		long l = System.currentTimeMillis();
		int acc_no = 0;
		Timestamp ts = new Timestamp(l);
		System.out.println("Timestamp -->" + ts);
		String sub_Qry="",sub_Qry1="",NIL_OB_STATUS="";
		int hid_month=Integer.parseInt(request.getParameter("hidMonth"));
		int hid_Year=Integer.parseInt(request.getParameter("hidYear"));
		int txtCB_Yearsts=0,txtCB_Monthsts=0;int txtCB_Monthnew=0;
		System.out.println("hidYear "+hid_Year+"hid_month "+hid_month);
		if(txtCB_Month==12){
			txtCB_Yearsts=txtCB_Year+1;
					txtCB_Monthsts=1;}
		else{
			txtCB_Monthsts=txtCB_Month+1;
			txtCB_Yearsts=txtCB_Year;
		}
		if(txtCB_Month==12)
			txtCB_Monthnew=0;
		else
			txtCB_Monthnew=txtCB_Month;
		 
		
		if(hid_month!=txtCB_Monthnew+1)
		{
			
		/*	if(txtCB_Month==1){
				txtCB_Month=12;
				txtCB_Year=txtCB_Year-1;}
			else{
				txtCB_Month=txtCB_Month-1;
			
			}*/
			sub_Qry=" LEFT OUTER JOIN "
			+ " (SELECT ACCOUNTING_UNIT_ID AS accid, "
			+ "   ACCOUNTING_FOR_OFFICE_ID AS accoffid, "
			+ "   CASHBOOK_YEAR            AS cby, "
			+ "   CASHBOOK_MONTH           AS cbm, "
			+ "   STATUS "
			+ " FROM FAS_BRS_MONTHLY_CLOSURE "
			+ " WHERE ACCOUNTING_UNIT_ID = "
			+ cmbAcc_UnitCode
			+ " AND CASHBOOK_YEAR        = "
			+ txtCB_Year
			+ " AND CASHBOOK_MONTH       = "
			+ txtCB_Month
			+ " )b "
			+ " ON a.ACCOUNTING_UNIT_ID = b.accid "
			+ " LEFT OUTER JOIN "
			+ "   (SELECT OB_STATUS,NIL_OB_STATUS,ACCOUNTING_UNIT_ID "
			+ "  FROM FAS_BRS_OB_STATUS "
			+ "   WHERE ACCOUNTING_UNIT_ID        = "
			+ cmbAcc_UnitCode
			+ "  AND            CASHBOOK_YEAR = "
			+ txtCB_Year
			+ "  AND CASHBOOK_MONTH              = "
			+ txtCB_Month
			+ "  AND ACCOUNT_NO                  ="
			+ cmbBankAccNo
			+ ")c " + " ON a.ACCOUNTING_UNIT_ID =c.ACCOUNTING_UNIT_ID	";
			sub_Qry1=" ,STATUS,OB_STATUS,NIL_OB_STATUS ";
		}else{
			sub_Qry=" ";
			sub_Qry1=", '-' as STATUS,'-' as OB_STATUS ,'-' as NIL_OB_STATUS  ";
		}
		
		
		try {
			String s1 = " SELECT TB_STATUS " +sub_Qry1 + " FROM "
					+ " (SELECT TB_STATUS, " + "  ACCOUNTING_UNIT_ID, "
					+ "   ACCOUNTING_FOR_OFFICE_ID, " + "   CASHBOOK_YEAR, "
					+ "   CASHBOOK_MONTH " + " FROM FAS_TRIAL_BALANCE_STATUS "
					+ " WHERE ACCOUNTING_UNIT_ID = "
					+ cmbAcc_UnitCode
					+ " AND CASHBOOK_YEAR        = "
					+ txtCB_Year
					+ " AND CASHBOOK_MONTH       ="
					+ txtCB_Month
					+ " )a "+sub_Qry					;
			System.out.println(s1);
			System.out.println("s1 >>> "+s1);
			PreparedStatement ps = con.prepareStatement(s1);

			ResultSet rs = ps.executeQuery();
			// System.out.println("be4");
			if (rs.next()) {
				String tb_status = rs.getString("TB_STATUS");
				String status = rs.getString("STATUS");
				String obstatus = rs.getString("OB_STATUS");
				NIL_OB_STATUS=rs.getString("NIL_OB_STATUS");
				System.out.println("tb_status >> "+tb_status);
				System.out.println("status >> "+status);
				System.out.println("obstatus >> "+obstatus);
				
				if (tb_status != null) {
					if (tb_status.equals("Y")) {

						if (status == null && obstatus == null && NIL_OB_STATUS==null) {
							System.out.println("status null::" + status);

							sendMessage(
									response,
									"Records Not Inserted ,Because BRS Monthly Closure is Not Freezed ............ ",
									"ok");

						} else {
							System.out.println("status not null");
							try {
								// Main Try I
								con.clearWarnings();
								con.setAutoCommit(false);
								/**
								 * --------------------- General Details -
								 * Master Trans -----------------------
								 */
								try {
									String sql_insert_mst = "insert into fas_brs_master(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,"
											+ "CASHBOOK_YEAR,CASHBOOK_MONTH,ENTRY_DATE,ACCOUNT_NO,BANK_ID,BRANCH_ID,OPERATIONAL_MODE,"
											+ "UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?)";
									System.out.println(sql_insert_mst);
									PreparedStatement ps5 = con
											.prepareStatement("select ACCOUNT_NO from fas_brs_master where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=?");
									ps5.setInt(1, cmbAcc_UnitCode);
									ps5.setInt(2, cmbOffice_code);
									ps5.setInt(3, txtCB_Year);
									ps5.setInt(4, txtCB_Month);
									ps5.setLong(5, cmbBankAccNo);
									ResultSet rss = ps5.executeQuery();
									if (rss.next()) {
										ps2 = con
												.prepareStatement("update fas_brs_master set ENTRY_DATE=?,BANK_ID=?,BRANCH_ID=?,OPERATIONAL_MODE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=?");

										System.out
												.println("*********************** RK **************************");
										ps2.setTimestamp(1, ts);
										ps2.setInt(2, txtBankID);
										ps2.setInt(3, txtBranchID);
										ps2.setString(4, txtOprMode);
										// ps2.setFloat(5, txtPBBalance);
										ps2.setString(5, update_user);
										ps2.setTimestamp(6, ts);
										ps2.setInt(7, cmbAcc_UnitCode);
										ps2.setInt(8, cmbOffice_code);
										ps2.setInt(9, txtCB_Year);
										ps2.setInt(10, txtCB_Month);
										ps2.setLong(11, cmbBankAccNo);
										ps2.executeUpdate();
									} else {
										System.out
												.println("***********************RK 1 **************************");
										ps2 = con
												.prepareStatement(sql_insert_mst);
										ps2.setInt(1, cmbAcc_UnitCode);
										ps2.setInt(2, cmbOffice_code);
										ps2.setInt(3, txtCB_Year);
										ps2.setInt(4, txtCB_Month);
										ps2.setTimestamp(5, ts);
										ps2.setLong(6, cmbBankAccNo);
										ps2.setInt(7, txtBankID);
										ps2.setInt(8, txtBranchID);
										ps2.setString(9, txtOprMode);
										// ps2.setFloat(10, txtPBBalance);
										ps2.setString(10, update_user);
										ps2.setTimestamp(11, ts);
										ps2.executeUpdate();
									}
								} catch (Exception e) {

									e.printStackTrace();
									con.rollback();
									con.setAutoCommit(true);
									sendMessage(response,
											"Records Not Inserted .... " + e,
											"ok");
									return;
								}

								/**
								 * --------------------- TWAD Transaction
								 * -------------------------
								 */
								int RecordCount = 0;

								/*
								 * Get Total Number of Transaction in TWAD
								 * Transactions
								 */
								try {
									System.out
											.println("---------********    RK  *******---------------"
													+ request
															.getParameter("RecordCount"));
									RecordCount = Integer.parseInt(request
											.getParameter("txtNoofRecords"));
								} catch (Exception e) {
								}
String AccNo_Text=request.getParameter("hidAccNo");
								/* String Array Declaration */
								String r_date[] = new String[RecordCount];
								String w_date[] = new String[RecordCount];
								String r_w_no[] = new String[RecordCount];
								String ccdd_no[] = new String[RecordCount];
								String cr_amount[] = new String[RecordCount];
								String dr_amount[] = new String[RecordCount];
								String EntryFoundInPassBook[] = new String[RecordCount];
								String Entry_Date[] = new String[RecordCount];
								String Amt_in_PassBk[] = new String[RecordCount];
								String Amt_Diff[] = new String[RecordCount];
								String cmbReason4Diff[] = new String[RecordCount];
								String FollowUpAction[] = new String[RecordCount];
								String ClearanceEntry[] = new String[RecordCount];

								String doc_type[] = new String[RecordCount];
								String doc_no[] = new String[RecordCount];
								String doc_date[] = new String[RecordCount];
								int pass_month=0,pass_year=0;
								/* Variables Declaration */
								Date r_date2 = null;
								Date w_date2 = null;
								int r_w_no2 = 0;
								int ccdd_no2 = 0;
								double cr_amount2 = 0.0f;
								double dr_amount2 = 0.0f;

								String IS_IT_CLEARING="";
								String doc_type2 = null;
								int doc_no2 = 0;
								Date doc_date2 = null;

								String EntryFoundInPassBook2 = null;
								Date Entry_Date2 = null;
								double Amt_in_PassBk2 =  0.0f;
								int Amt_Diff2 = 0;
								String cmbReason4Diff2 = "";
								String FollowUpAction2 = null;
								String ClearanceEntry2 = null;

								String sd[] = new String[10];
								java.util.Date d = null;
								Calendar c;
								System.out
										.println("*********************** RK 2 **************************");
								cs = con
										.prepareCall("{call FAS_BRS_PROCEDURE_NEW(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
								System.out
										.println("*********************** RecordCount **************************"
												+ RecordCount);
								try {
									for (int k = 0; k < RecordCount; k++) {
										System.out
												.println("my codingssssssssssssssssssssssssssss");
										// dhanapradha codings....
										// OPR account query_acc_no
										// if(cmbBankAccNo==4181) {
										if (query_acc_no.equals("OPR")) {
											System.out.println("operation");
											
											/* Doc type */
											try {
												System.out.println("Document type...."
																+ request.getParameter("doc_type"
																				+ k));
												doc_type[k] = request
														.getParameter("doc_type"
																+ k);
												doc_type2 = doc_type[k];
											} catch (Exception e) {
												System.out.println(e);
											}
											//Lakshmi 20May2014
											
											if(doc_type2.equalsIgnoreCase("FR by Office")){
												r_date[k] = request
												.getParameter("r_date" + k);
										if (!r_date[k].equalsIgnoreCase("")) {
											sd = r_date[k].split("/");
											year_insert = Integer
													.parseInt(sd[2]);
											month_insert = Integer
													.parseInt(sd[1]);

											System.out
													.println("year_insert:::"
															+ year_insert);
											System.out
													.println("month_insert:::"
															+ month_insert);
										}
												
											}else{
											w_date[k] = request
													.getParameter("w_date" + k);
											if (!w_date[k].equalsIgnoreCase("")) {
												sd = w_date[k].split("/");
												year_insert = Integer
														.parseInt(sd[2]);
												month_insert = Integer
														.parseInt(sd[1]);

												System.out
														.println("year_insert:::"
																+ year_insert);
												System.out
														.println("month_insert:::"
																+ month_insert);
											}
											
											}
											
											
										}
										// collection acc
										// if(cmbBankAccNo==6722) {
										else if (query_acc_no.equals("COL")) {
											r_date[k] = request
													.getParameter("r_date" + k);
											if (!r_date[k].equalsIgnoreCase("")) {
												sd = r_date[k].split("/");
												year_insert = Integer
														.parseInt(sd[2]);
												month_insert = Integer
														.parseInt(sd[1]);

												System.out
														.println("year_insert:::"
																+ year_insert);
												System.out
														.println("month_insert:::"
																+ month_insert);
											}
										}else if (query_acc_no.equals("FDW")) {
											
											year_insert = txtCB_Year;
											month_insert = txtCB_Month;
											System.out
													.println("year_insert:::"
															+ year_insert);
											System.out
													.println("month_insert:::"
															+ month_insert);
										}else if(query_acc_no.equals("OPR-NRDWP-Main")){

											w_date[k] = request
													.getParameter("w_date" + k);
											if (!w_date[k].equalsIgnoreCase("")) {
												sd = w_date[k].split("/");
												year_insert = Integer
														.parseInt(sd[2]);
												month_insert = Integer
														.parseInt(sd[1]);

												System.out
														.println("year_insert:: ::::"
																+ year_insert);
												System.out
														.println("month_insert:::"
																+ month_insert);
											}
										
										}else if(query_acc_no.equals("OPR-NRDWP-Support")){

											w_date[k] = request
													.getParameter("w_date" + k);
											if (!w_date[k].equalsIgnoreCase("")) {
												sd = w_date[k].split("/");
												year_insert = Integer
														.parseInt(sd[2]);
												month_insert = Integer
														.parseInt(sd[1]);

												System.out
														.println("year_insert:: ::::"
																+ year_insert);
												System.out
														.println("month_insert:::"
																+ month_insert);
											}
										
										}
										/* Receipt Date */
										System.out
												.println("end of my codingssssssssssssssssssss:");
										try {
											r_date[k] = request
													.getParameter("r_date" + k);
System.out.println(r_date[k]);

											
											if (!r_date[k].equalsIgnoreCase("")) {
												sd = r_date[k].split("/");

												c = new GregorianCalendar(
														Integer.parseInt(sd[2]),
														Integer.parseInt(sd[1]) - 1,
														Integer.parseInt(sd[0]));
												d = c.getTime();
												r_date2 = new Date(d.getTime());
												System.out.println("r_date2 >>> "+r_date2);	
												
											}
										} catch (Exception e) {
											System.out
													.println("Error Converting Receipt Date -->"
															+ e);
										}
										System.out.println("*********************** sbg 1 **************************");
										/* Withdraw Date */
										try {
											w_date[k] = request
													.getParameter("w_date" + k);

											if (!w_date[k].equalsIgnoreCase("")) {
												sd = w_date[k].split("/");
												c = new GregorianCalendar(
														Integer.parseInt(sd[2]),
														Integer.parseInt(sd[1]) - 1,
														Integer.parseInt(sd[0]));
												d = c.getTime();
												w_date2 = new Date(d.getTime());
											}
											System.out.println("w_date2 >>> "+w_date2);
										} catch (Exception e) {
											System.out.println(e);
										}
										System.out
												.println("*********************** sbg 2 **************************");
										/* Receipt or Challan Number */
										try {
											r_w_no[k] = request
													.getParameter("w_challan_no"
															+ k);
											r_w_no2 = Integer
													.parseInt(r_w_no[k]);
										} catch (Exception e) {
											System.out.println(e);
										}
										System.out
												.println("*********************** sbg 3 **************************");
										/* Cheque or DD Number */
										try {
											ccdd_no[k] = request
													.getParameter("r_cheque_dd_no"
															+ k);
											ccdd_no2 = Integer
													.parseInt(ccdd_no[k]);
										} catch (Exception e) {
											System.out.println(e);
										}
										System.out
												.println("*********************** sbg 4 **************************");
										/* Cr Amount */
										try {
											cr_amount[k] = request
													.getParameter("cr_amount"
															+ k);
											cr_amount2 = Double
													.parseDouble(cr_amount[k]);
										} catch (Exception e) {
											System.out.println(e);
										}
										System.out.println("***sbg 5 ******"+cr_amount2);
										/* Dr Amount */
										try {
											dr_amount[k] = request
													.getParameter("dr_amount"
															+ k);
											dr_amount2 = Double
													.parseDouble(dr_amount[k]);
										} catch (Exception e) {
											System.out.println(e);
										}
										System.out.println("cr_amount2 >>"+cr_amount2);
										System.out.println("dr_amount2 >>"+dr_amount2);
										System.out.println("*******sbg 6 ******dr_amount2********"+dr_amount2);
										/* Entry Found in Pass Book */
										try {
											EntryFoundInPassBook[k] = request
													.getParameter("EntryFoundInPassBook"
															+ k);
											EntryFoundInPassBook2 = EntryFoundInPassBook[k];
											if (EntryFoundInPassBook2 != null) {
												if (EntryFoundInPassBook2
														.equals("Y")) {

												} else {
													EntryFoundInPassBook2 = "NA";
												}
											} else if (EntryFoundInPassBook2 == "") {
												EntryFoundInPassBook2 = "NA";
											} else if (EntryFoundInPassBook2 == null) {
												EntryFoundInPassBook2 = "NA";
											} else {
												EntryFoundInPassBook2 = "NA";
											}
										} catch (Exception e) {
											System.out.println(e);
										}
										System.out
												.println("*********************** sbg 7 **************************");
										/* Entry Date */
										try {
										
											Entry_Date[k] = request
													.getParameter("Entry_Date"
															+ k);
											if (!Entry_Date[k]
													.equalsIgnoreCase("")) {
												sd = Entry_Date[k].split("/");
												pass_month=Integer.parseInt(sd[1]);
												pass_year=Integer.parseInt(sd[2]);
												c = new GregorianCalendar(
														Integer.parseInt(sd[2]),
														Integer.parseInt(sd[1]) - 1,
														Integer.parseInt(sd[0]));
												d = c.getTime();
												Entry_Date2 = new Date(d
														.getTime());
											}
										} catch (Exception e) {
											System.out.println(e);
										}

										/* Amount in Pass Book */
										try {
											Amt_in_PassBk[k] = request
													.getParameter("Amt_in_PassBk"
															+ k);
											Amt_in_PassBk2 = Double
													.parseDouble(Amt_in_PassBk[k]);
										} catch (Exception e) {
											System.out.println(e);
										}
										System.out.println("Amt_in_PassBk2:::::dhana:::"+Amt_in_PassBk2);
										/* Difference */
										try {
											Amt_Diff[k] = request
													.getParameter("Amt_Diff"
															+ k);
											Amt_Diff2 = Integer
													.parseInt(Amt_Diff[k]);
										} catch (Exception e) {
											System.out.println(e);
										}
										System.out.println("Amt_Diff2:::::dhana:::"+Amt_Diff2);
										/* Reason for Difference */
										try {
											cmbReason4Diff[k] = request
													.getParameter("cmbReason4Diff"
															+ k);
											cmbReason4Diff2 = cmbReason4Diff[k];
										} catch (Exception e) {
											System.out.println(e);
										}

										/* Follow up action Required */
										try {
											FollowUpAction[k] = request
													.getParameter("FollowUpAction"
															+ k);
											FollowUpAction2 = FollowUpAction[k];
										} catch (Exception e) {
											System.out.println(e);
										}

										/* Clearance Entry */
										try {
											ClearanceEntry[k] = request
													.getParameter("ClearanceEntry"
															+ k);
											ClearanceEntry2 = ClearanceEntry[k];
										} catch (Exception e) {
											System.out.println(e);
										}

										/* Doc type */
										try {
											System.out.println("Document type...."
															+ request
																	.getParameter("doc_type"
																			+ k));
											doc_type[k] = request
													.getParameter("doc_type"
															+ k);
											doc_type2 = doc_type[k];
										} catch (Exception e) {
											System.out.println(e);
										}

										/* Doc Date */
										try {
											doc_date[k] = request
													.getParameter("doc_date"
															+ k);
											if ((!doc_date[k]
													.equalsIgnoreCase(""))
													&& (doc_date[k] != null)) {
												sd = doc_date[k].split("/");
												c = new GregorianCalendar(
														Integer.parseInt(sd[2]),
														Integer.parseInt(sd[1]) - 1,
														Integer.parseInt(sd[0]));
												d = c.getTime();
												doc_date2 = new Date(d
														.getTime());
											} else {
												doc_date[k] = "00/00/0000";
												sd = doc_date[k].split("/");
												c = new GregorianCalendar(
														Integer.parseInt(sd[2]),
														Integer.parseInt(sd[1]) - 1,
														Integer.parseInt(sd[0]));
												d = c.getTime();
												doc_date2 = new Date(d
														.getTime());
											}
										} catch (Exception e) {
											System.out.println(e);
										}

										/* Doc Number */
										try {
											System.out
													.println("Document No...."
															+ request
																	.getParameter("doc_no"
																			+ k));
											doc_no[k] = request
													.getParameter("doc_no" + k);
											doc_no2 = Integer
													.parseInt(doc_no[k]);
										} catch (Exception e) {
											System.out.println(e);
										}
										System.out.println("lachu doc no ::: EntryFoundInPassBook2 "+doc_no2+"---"+EntryFoundInPassBook2);
										if (EntryFoundInPassBook2.equals("NA")) {
											System.out
													.println("*********************** RK 3 **************************");
										} 
										else {
											//entry found in passbood=y
											System.out
													.println("*********************** RK 4 **************************");
											ps2 = con
													.prepareStatement("select * from FAS_BRS_TRANSACTION_NOENTRY where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TWAD_OR_NON_TWAD=? and DOC_NO=? and DOC_TYPE=? and ACCOUNT_NO=?");
											ps2.setInt(1, cmbAcc_UnitCode);
											ps2.setInt(2, cmbOffice_code);
											// ps2.setInt(3, txtCB_Year);
											// ps2.setInt(4, txtCB_Month);
											ps2.setInt(3, year_insert);
											System.out.println(" year_insert "+year_insert);
											ps2.setInt(4, month_insert);
											System.out.println(" month_insert "+month_insert);
											ps2.setString(5, "T");
											ps2.setInt(6, doc_no2);
											ps2.setString(7, doc_type2);
											ps2.setLong(8, cmbBankAccNo);
											ResultSet rs5 = ps2.executeQuery();
											System.out.println("Starting ... ");
											while (rs5.next()) {
												System.out.println("Here Step 1");
												
												
												//added by dhana on 18sep2013 start
												//System.out.println("lachu  Amt_Diff2 "+Amt_Diff2);
												if(Amt_Diff2>0)
												{
													System.out.println("Here Step 2");
														if(dr_amount2==0.0)
														{
															
															ps2 = con.prepareStatement("update FAS_BRS_TRANSACTION_NOENTRY set CR_AMOUNT=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TWAD_OR_NON_TWAD=? and DOC_NO=? and DOC_TYPE=? and ACCOUNT_NO=? and CHEQUE_DD_NO=? and DR_AMOUNT=0");
															ps2.setFloat(1, Amt_Diff2);
															ps2.setString(2, update_user);
															ps2.setTimestamp(3, ts);
															
														}
														else if(cr_amount2==0.0)
														{
															
															ps2 = con.prepareStatement("update FAS_BRS_TRANSACTION_NOENTRY set DR_AMOUNT=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TWAD_OR_NON_TWAD=? and DOC_NO=? and DOC_TYPE=? and ACCOUNT_NO=? and CHEQUE_DD_NO=? and CR_AMOUNT=0");
															ps2.setFloat(1, Amt_Diff2);
															ps2.setString(2, update_user);
															ps2.setTimestamp(3, ts);
														}
														
														
														ps2.setInt(4, cmbAcc_UnitCode);
														ps2.setInt(5, cmbOffice_code);
														ps2.setInt(6, year_insert);
														ps2.setInt(7, month_insert);
														ps2.setString(8, "T");
														ps2.setInt(9, doc_no2);
														ps2.setString(10, doc_type2);
														ps2.setLong(11, cmbBankAccNo);
														ps2.setInt(12,ccdd_no2);
														ps2.executeUpdate();
												}			//added by dhana on 18sep2013 end
												else
												{
													System.out.println("Here Step 3");
													//System.out.println("else for delete"+doc_no2);
													//ps2 = con.prepareStatement("delete from FAS_BRS_TRANSACTION_NOENTRY where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TWAD_OR_NON_TWAD=? and DOC_NO=? and DOC_TYPE=? and ACCOUNT_NO=? ");
													ps2 = con.prepareStatement("delete from FAS_BRS_TRANSACTION_NOENTRY where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TWAD_OR_NON_TWAD=? and DOC_NO=? and DOC_TYPE=? and ACCOUNT_NO=? and CHEQUE_DD_NO=?");
												ps2.setInt(1, cmbAcc_UnitCode);
												ps2.setInt(2, cmbOffice_code);
												// dhanapradha changes
												ps2.setInt(3, year_insert);
												ps2.setInt(4, month_insert);
												// ps2.setInt(3, txtCB_Year);
												// ps2.setInt(4, txtCB_Month);
												ps2.setString(5, "T");
												ps2.setInt(6, doc_no2);
												ps2.setString(7, doc_type2);
												ps2.setLong(8, cmbBankAccNo);
												//Lachu 3Apr14
												ps2.setInt(9, ccdd_no2);
												int No_Ent=ps2.executeUpdate();
												System.out
														.println("No_Ent >>>  "+No_Ent);
												}
											}
										}
										cs.setInt(1, cmbAcc_UnitCode);
										cs.setInt(2, cmbOffice_code);
										// dhanapradha changes
										cs.setInt(3, txtCB_Year);
										 cs.setInt(4, txtCB_Month);
										
										 /*cs.setInt(3, hid_Year);System.out.println("hid_Year >> "+hid_Year);
										cs.setInt(4, hid_month);System.out.println("hid_month >> "+hid_month);*/
										cs.setDate(5, r_date2);
										cs.setDate(6, w_date2);
										cs.setInt(7, r_w_no2);
										cs.setInt(8, ccdd_no2);
										cs.setDouble(9, cr_amount2);
										cs.setDouble(10, dr_amount2);
										cs.setString(11, EntryFoundInPassBook2);
										cs.setDate(12, Entry_Date2);
										cs.setDouble(13, Amt_in_PassBk2);
										cs.setInt(14, Amt_Diff2);
										cs.setString(15, cmbReason4Diff2);
										cs.setString(16, FollowUpAction2);
										cs.setString(17, ClearanceEntry2);
										cs.setDate(18, doc_date2);
										cs.setString(19, update_user);
										cs.setLong(20, cmbBankAccNo);
										cs.setString(21, doc_type2);
										cs.setInt(22, doc_no2);
										cs.setString(23, IS_IT_CLEARING);
										cs.setInt(24, pass_month);
										cs.setInt(25, pass_year);
										cs.registerOutParameter(26,java.sql.Types.NUMERIC);
										cs.setString(27, "INSERT");
									
									
										if (EntryFoundInPassBook2.equals("NA")) {
											System.out
													.println("*********************** RK 5 **************************");
										} else {
											System.out
													.println("*********************** RK 6 **************************");
											System.out.println("-15-");
											
											System.out.println("-15-151515 "+pass_month);								
											System.out.println("-15-151515 "+pass_year);									
											
											
											
											
											cs.executeQuery();
											int errcode = cs.getInt(26);
System.out.println("errcode >>> "+errcode);
											if (errcode != 0) {
												count_error=count_error+1;
												con.rollback();
												con.setAutoCommit(true);
												sendMessage(
														response,
														"TWAD Transaction Records Not Inserted ............ ",
														"ok");

											}
											else{
												count_error=count_error+0;
											}
										}
									//now temp
										System.out.println("two");

										/* Clear Variables Values */

										r_date2 = null;
										w_date2 = null;
										r_w_no2 = 0;
										ccdd_no2 = 0;
										cr_amount2 = 0;
										dr_amount2 = 0;
										EntryFoundInPassBook2 = null;
										Entry_Date2 = null;
										Amt_in_PassBk2 =  0.0f;
										Amt_Diff2 = 0;
										cmbReason4Diff2 = "";
										FollowUpAction2 = null;
										ClearanceEntry2 = null;

									}

								} catch (Exception e) {
									e.printStackTrace();
									con.rollback();
									con.setAutoCommit(true);
									sendMessage(response,
											"TWAD Transaction Records Not Inserted ............ "
													+ e, "ok");
									System.out.println(e);
									return;
								}
								/* Final Commit */
								if(count_error==0)
								{con.commit();
								con.setAutoCommit(true);
								sendMessage(
								response,
									"Records Saved Successfully ............ ",
									"ok");}
								else if(count_error>0){
									con.rollback();
									con.setAutoCommit(true);
									sendMessage(response,
											"TWAD Transaction Records Not Inserted ............ "
													 , "ok");
									
									
								}

							} catch (Exception e) {
								e.printStackTrace();
							}
						}
					} else {
						sendMessage(
								response,
								"Records Not Inserted ,Because TRIAL BALANCE should have been freezed ............ ",
								"ok");
					}

				} else {
					sendMessage(
							response,
							"Records Not Inserted ,Because TRIAL BALANCE should have been freezed ............ ",
							"ok");
				}
			} else {
				sendMessage(
						response,
						"Records Not Inserted ,Because TRIAL BALANCE should have been freezed ............ ",
						"ok");
			}

		} catch (Exception e) {
			e.printStackTrace();

		}

	}

	private void sendMessage(HttpServletResponse response, String msg,
			String bType) {
		try {
			String url = "org/FAS/FAS1/BRS/jsps/MessengerOkBack1.jsp?message="
					+ msg + "&button=" + bType;
			System.out.println("after url");
			response.sendRedirect(url);
		} catch (IOException e) {
		}
	}

}