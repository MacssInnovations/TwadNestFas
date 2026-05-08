package Servlets.FAS.FAS1.JournalSystem.servlets;

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
import java.text.DecimalFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Work_Bill_Journal
 */
public class Work_Bill_Journal extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Work_Bill_Journal() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
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
		if (strCommand.equalsIgnoreCase("gettGrid")) {
			System.out.println("RK");
			xml = xml + "<response><command>gettGrid</command>";

			String cmbAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			String cmbOffice_code1 = request.getParameter("cmbOffice_code");
			String txtCB_Year1 = request.getParameter("txtCB_Year");
			String txtCB_Month1 = request.getParameter("txtCB_Month");

			int cmbAcc_UnitCode = Integer.parseInt(cmbAcc_UnitCode1);
			int cmbOffice_code = Integer.parseInt(cmbOffice_code1);
			int txtCB_Year = Integer.parseInt(txtCB_Year1);
			int txtCB_Month = Integer.parseInt(txtCB_Month1);

			try {

				String su = "select JRNL_VCHR_SNO,  SUB_LEDGER_CODE, " +
						" to_char(BILL_DATE,'DD/MM/YYYY') as BILL_DATE, REMARKS,SL_CODENAME" +
						" from (select JRNL_VCHR_SNO,SUB_LEDGER_CODE,JOURNAL_TYPE_CODE,BILL_DATE,REMARKS from " +
						"PMS_FAS_JOURNAL_MASTER_VW where  ACCOUNTING_UNIT_ID=? and " +
						"ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? " +
						" and CASHBOOK_MONTH=? )a LEFT OUTER JOIN" +
						" (SELECT sl_type,SL_CODE,SL_CODENAME FROM SL_TYPE_CODE_NAME_VIEW " +
						" )b ON b.sl_type=a.JOURNAL_TYPE_CODE and  a.SUB_LEDGER_CODE =b.SL_CODE order by JRNL_VCHR_SNO";
				ps = connection.prepareStatement(su);
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setInt(3, txtCB_Year);
				ps.setInt(4, txtCB_Month);

				rs = ps.executeQuery();
				while (rs.next()) {

					xml = xml + "<JournalVoucherNo>"
							+ rs.getInt("JRNL_VCHR_SNO")
							+ "</JournalVoucherNo>";

					xml = xml + "<SubLedgerCode>"
							+ rs.getInt("SUB_LEDGER_CODE") + "</SubLedgerCode>";
                                                        
				    xml = xml + "<SubLedgerCodeDesc>"
				                    + rs.getString("SL_CODENAME") + "</SubLedgerCodeDesc>";

				    xml = xml + "<Billdate>"
                    + rs.getString("BILL_DATE") + "</Billdate>";
				    
				    
					xml = xml + "<Remarks>" + rs.getString("REMARKS")
							+ "</Remarks>";

				}
				xml = xml + "<flag>success</flag>";
				ps.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
			}

		}

		if (strCommand.equalsIgnoreCase("getVoucherDetails")) {
			double amt;
			xml = xml + "<response><command>getVoucherDetails</command>";

			String cmbAcc_UnitCode22 = request.getParameter("cboAcc_UnitCode");
			String cmbOffice_code22 = request.getParameter("cboOffice_code");
			String txtCB_Year22 = request.getParameter("cboCashBook_Year");
			String txtCB_Month22 = request.getParameter("cboCashBook_Month");
			String JournalVoucherNo22 = request
					.getParameter("JournalVoucherNo");

			int cmbAcc_UnitCode2 = Integer.parseInt(cmbAcc_UnitCode22);
			int cmbOffice_code2 = Integer.parseInt(cmbOffice_code22);
			int txtCB_Year2 = Integer.parseInt(txtCB_Year22);
			int txtCB_Month2 = Integer.parseInt(txtCB_Month22);
			int JournalVoucherNo2 = Integer.parseInt(JournalVoucherNo22);

			try {
				String su = "SELECT JRNL_VCHR_DR_CR_SNO,  ACCOUNT_HEAD_CODE,  CR_DR_INDICATOR,  SUB_LEDGER_TYPE_CODE,  SUB_LEDGER_TYPE_DESC,  SUB_LEDGER_CODE,  SL_CODENAME,  BILL_NO,  BILL_TYPE,  AGREEMENT_NO,  AGREEMENT_DATE,  BILL_DATE,  AMOUNT FROM  (SELECT JRNL_VCHR_DR_CR_SNO,    ACCOUNT_HEAD_CODE,    CR_DR_INDICATOR,    SUB_LEDGER_TYPE_CODE,    SUB_LEDGER_CODE,    BILL_NO,    BILL_TYPE,    AGREEMENT_NO,    TO_CHAR(AGREEMENT_DATE,'dd/mm/yyyy') AS AGREEMENT_DATE,    TO_CHAR(BILL_DATE,'dd/mm/yyyy')      AS BILL_DATE,    AMOUNT  FROM PMS_FAS_JOURNAL_TRANSACTION_VW   WHERE ACCOUNTING_UNIT_ID    =?   AND ACCOUNTING_FOR_OFFICE_ID=?  AND CASHBOOK_YEAR           =?  AND CASHBOOK_MONTH          =?  AND JRNL_VCHR_SNO           =?  )a LEFT OUTER JOIN   (SELECT SL_TYPE,SL_CODE,SL_CODENAME from SL_TYPE_CODE_NAME_VIEW   )b ON a.SUB_LEDGER_TYPE_CODE =b.SL_TYPE AND a.SUB_LEDGER_CODE     =b.SL_CODE LEFT OUTER JOIN   (SELECT SUB_LEDGER_TYPE_CODE as sl_type_code,SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES   )c ON a.SUB_LEDGER_TYPE_CODE =c.sl_type_code order by JRNL_VCHR_DR_CR_SNO";
				ps1 = connection.prepareStatement(su);
				ps1.setInt(1, cmbAcc_UnitCode2);
				ps1.setInt(2, cmbOffice_code2);
				ps1.setInt(3, txtCB_Year2);
				ps1.setInt(4, txtCB_Month2);
				ps1.setInt(5, JournalVoucherNo2);

				rs1 = ps1.executeQuery();
				while (rs1.next()) {
					xml = xml + "<Journal_Voucher_DR_CR_SNO>"
							+ rs1.getInt("JRNL_VCHR_DR_CR_SNO")
							+ "</Journal_Voucher_DR_CR_SNO>";

					xml = xml + "<Acc_Hd_Code>"
							+ rs1.getInt("ACCOUNT_HEAD_CODE")
							+ "</Acc_Hd_Code>";

					xml = xml + "<Sub_Ledger_Type>"
							+ rs1.getInt("SUB_LEDGER_TYPE_CODE")
							+ "</Sub_Ledger_Type>";

					xml = xml + "<Sub_Ledger_Type_desc>"
							+ rs1.getString("SUB_LEDGER_TYPE_DESC")
							+ "</Sub_Ledger_Type_desc>";

					xml = xml + "<Sub_Ledger_Code>"
							+ rs1.getInt("SUB_LEDGER_CODE")
							+ "</Sub_Ledger_Code>";

					xml = xml + "<Sub_Ledger_Code_desc><![CDATA["
							+ rs1.getString("SL_CODENAME")
							+ "]]></Sub_Ledger_Code_desc>";

					xml = xml + "<CR_DR_Indicator>"
							+ rs1.getString("CR_DR_INDICATOR")
							+ "</CR_DR_Indicator>";

					xml = xml + "<Bill_No>" + rs1.getString("BILL_NO")
							+ "</Bill_No>";

					xml = xml + "<Bill_Type>" + rs1.getString("BILL_TYPE")
							+ "</Bill_Type>";

					xml = xml + "<Agreement_No>"
							+ rs1.getString("AGREEMENT_NO") + "</Agreement_No>";

					xml = xml + "<Agreement_Date>"
							+ rs1.getString("AGREEMENT_DATE")
							+ "</Agreement_Date>";

					xml = xml + "<Bill_Date>" + rs1.getString("BILL_DATE")
							+ "</Bill_Date>";

					//joe change
					amt= rs1.getDouble("AMOUNT");
					DecimalFormat c=new DecimalFormat("0.00");
					String s=c.format(amt);
					System.out.println(s);
					/*xml = xml + "<Amount>" + rs1.getFloat("AMOUNT")
							+ "</Amount>";*/
					xml = xml + "<Amount>" + s
					+ "</Amount>";
				}
				xml = xml + "<flag>success</flag>";
				ps1.close();
				rs1.close();
			} catch (Exception e) {
				// TODO: handle exception
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
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Connection connection = null;
		Statement statement = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null,ps2 = null;
		CallableStatement cs = null;
		ResultSet rs = null;
		ResultSet rs1 = null,rs2 = null;
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

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
		String userid = (String) session.getAttribute("UserId");
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);

		Date date = new Date(0000 - 00 - 00);
		int RecordCount = 0;
		try {
			RecordCount = Integer.parseInt(request.getParameter("RecordCount"));
		} catch (Exception e) {
			System.out
					.println("Error Getting Total Number of Records in TWAD Transaction ");
		}
System.out.println("RecordCount******"+RecordCount);
		/* Get Parameters */
		int cmbAcc_UnitCode = 0;
		int cmbOffice_code = 0;
		int cboCashBook_Year = 0;
		int cboCashBook_Month = 0;
                int err=0;

		if (strCommand.equals("Add")) {
			String check[] = new String[RecordCount];
			String JournalVoucherNo[] = new String[RecordCount];
			String Billdate[] = new String[RecordCount];
			String SubLedgerCode[] = new String[RecordCount];
			String Remarks[] = new String[RecordCount];

			int check2 = 0;
			int JournalVoucherNo2 = 0;
			Date Billdate2=null;
			int SubLedgerCode2 = 0;
			String Remarks2 = null;
			Calendar c,c1;
			Date txtCrea_date = null;
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

			/* cboCashBook_Year */
			try {
				cboCashBook_Year = Integer.parseInt(request
						.getParameter("cboCashBook_Year"));
			} catch (Exception e) {
				System.out
						.println("Error Not Getting cboCashBook_Year--> " + e);
			}

			/* cboCashBook_Month */
			try {
				cboCashBook_Month = Integer.parseInt(request
						.getParameter("cboCashBook_Month"));

			} catch (Exception e) {
				System.out.println("Error Not Getting cboCashBook_Month --> "
						+ e);
			}

		/*	String[] sd = request.getParameter("txtCrea_date").split("/");
			c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer
					.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c.getTime();
			txtCrea_date = new Date(d.getTime());*/
			
			 /* String[] sd = request.getParameter("txtCrea_date").split("/");
	            c =
	   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
	                         Integer.parseInt(sd[0]));
	            java.util.Date d = c.getTime();
	            txtCrea_date = new Date(d.getTime());
	            System.out.println("txtCrea_date " + txtCrea_date);*/
                        
                      /*  int cbm = Integer.parseInt(sd[1]);
		    int cby = Integer.parseInt(sd[2]);*/
                    
			int cbm = cboCashBook_Month;
		    int cby = cboCashBook_Year;
			
                    System.out.println("cbm~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+cbm);
		    System.out.println("cby~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"+cby);
                    
		   
			
			
			int errcode = -1;
			int kk = 0;
			try {
				int k = 0;
				for (k = 0; k < RecordCount; k++) {
					/* Check Box */
					try {
						check[k] = request.getParameter("slno_db1" + k);
						check2 = Integer.parseInt(check[k]);

					} catch (Exception e) {
						System.out.println("Error for getting check -->" + e);
					}
					if (k == check2) {
						try {
							JournalVoucherNo[k] = request
									.getParameter("JournalVoucherNo" + k);
							if (JournalVoucherNo[k] != null) {
								if (JournalVoucherNo[k].equals("")) {
									JournalVoucherNo2 = 0;
								} else {
									JournalVoucherNo2 = Integer
											.parseInt(JournalVoucherNo[k]);
								}
							} else {
								JournalVoucherNo2 = 0;
							}
						} catch (Exception e) {
							System.out
									.println("Error for getting JournalVoucherNo -->"
											+ e);
						}
						
						
						//Billdate
						
						try {
							Billdate[k] = request.getParameter("Billdate" + k);
							
									
							if (Billdate[k] != null) {
								if (Billdate[k].equals("")) {
									Billdate2 = null;
								} else {
									
									String[] sd1 = request.getParameter("Billdate" + k).split("/");
						            c1 =
						   new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]) - 1,
						                         Integer.parseInt(sd1[0]));
						            java.util.Date d1 = c1.getTime();
						            Billdate2 = new Date(d1.getTime());
						            //System.out.println("Billdate2 " + Billdate2);
						            
						            
									
									
									//Billdate2 = Billdate[k];
								}
							} else {
								Billdate2 = null;
							}
						} catch (Exception e) {
							System.out
									.println("Error for getting Bill date -->"
											+ e);
						}

						try {
							SubLedgerCode[k] = request
									.getParameter("SubLedgerCode" + k);
							if (SubLedgerCode[k] != null) {
								if (SubLedgerCode[k].equals("")) {
									SubLedgerCode2 = 0;
								} else {
									SubLedgerCode2 = Integer
											.parseInt(SubLedgerCode[k]);
								}
							} else {
								SubLedgerCode2 = 0;
							}
						} catch (Exception e) {
							System.out
									.println("Error for getting SubLedgerCode -->"
											+ e);
						}
						try {
							Remarks2 = request.getParameter("Remarks" + k);

						} catch (Exception e) {
							System.out.println("Error for getting Remarks -->"
									+ e);
						}
						int jou_vou_no=JournalVoucherNo2;
						 System.out.println(cmbAcc_UnitCode+">>"+cmbOffice_code+">>"+cby+">>"+cbm+">>"+JournalVoucherNo2);
						 System.out.println(Billdate2+">>"+Remarks2+">>"+SubLedgerCode2);
						 int cno=0;
							try{
								String su = "SELECT count(*) cno  FROM PMS_FAS_JOURNAL_TRANSACTION_VW  WHERE ACCOUNTING_UNIT_ID    =?   AND ACCOUNTING_FOR_OFFICE_ID=?   AND CASHBOOK_YEAR           =?   AND CASHBOOK_MONTH          =?   AND JRNL_VCHR_SNO           =?  and (SUB_LEDGER_TYPE_CODE is null or SUB_LEDGER_CODE is null )";
								PreparedStatement ps1_set = connection.prepareStatement(su);
								ps1_set.setInt(1, cmbAcc_UnitCode);
								ps1_set.setInt(2, cmbOffice_code);
								ps1_set.setInt(3, cboCashBook_Year);
								ps1_set.setInt(4, cboCashBook_Month);
								ps1_set.setInt(5, jou_vou_no);
								ResultSet rs_set=ps1_set.executeQuery();
								while (rs_set.next()) {
								cno=rs_set.getInt("cno");									
								}
								
							}catch (Exception e) {
								e.printStackTrace();
							}
							
							if(cno!=0){
								 sendMessage(response,"All the Subledger details to be filled", "ok");
								 connection.rollback();
							}
							
							
						try {
							connection.clearWarnings();
							connection.setAutoCommit(false);
							cs = connection.
//									.prepareCall("{call FAS_JOURNAL_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
//							cs.setInt(1, cmbAcc_UnitCode);
//							cs.setInt(2, cmbOffice_code);
//							cs.setInt(3, cby);
//							cs.setInt(4, cbm);
//							cs.setInt(5, JournalVoucherNo2);
//							cs.setDate(6, Billdate2);
//							cs.setInt(7, 11);
//							cs.setInt(8, SubLedgerCode2);
//							cs.setDouble(9, 0);
//							cs.setString(10, "");
//							cs.setDate(11, null);
//							cs.setString(12, "");
//							cs.setInt(13, 0);
//							cs.setString(14, Remarks2);
//							cs.setString(15, "A");
//							cs.setString(16, "LJV");
//							cs.setString(17, "insert");
//							cs.registerOutParameter(5, java.sql.Types.NUMERIC);
//							cs.registerOutParameter(18, java.sql.Types.NUMERIC);
//							cs.setString(19, userid);
//							cs.setTimestamp(20, ts);
//							System.out.println("execute procedure .. ");
//							cs.execute();
//							JournalVoucherNo2 = cs.getInt(5);
//							errcode = cs.getInt(18);
									  prepareCall("call FAS_JOURNAL_MASTER_PROC(?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?,?,?,?::numeric,?,?,?,?,?::int,?,?)");
		                    cs.setInt(1, cmbAcc_UnitCode);
		                    cs.setInt(2, cmbOffice_code);
		                    cs.setInt(3, cby);
		                    cs.setInt(4, cbm);
		                    cs.setInt(5, JournalVoucherNo2);
		                    cs.setDate(6, txtCrea_date);
		                    // cs.setString(7,txtReceipt_type);
		                    //  cs.setInt(8,txtCash_Acc_code);
		                    cs.setInt(7, 11);
		                    cs.setInt(8, SubLedgerCode2);
		                    cs.setDouble(9, 0);
		                    cs.setString(10, "");
		                    cs.setDate(11, null);
		                    cs.setString(12, "");
		                    // cs.setInt(13,txtCB_REF_NO);
		                    // cs.setDate(14,txtCB_REF_DATE);
		                    // cs.setDouble(19,txtAmount);
		                    cs.setInt(13, 0);
		                    cs.setString(14, Remarks2);
		                    cs.setString(15, "A");
		                    cs.setString(16, "LJV");
		                    cs.setString(17, "insert");
		                    cs.registerOutParameter(5, java.sql.Types.NUMERIC);
		                    cs.registerOutParameter(18, java.sql.Types.NUMERIC);
		                    cs.setNull(5, java.sql.Types.NUMERIC);
		                    cs.setNull(18, java.sql.Types.NUMERIC);
		                    cs.setString(19, userid);
		                    cs.setTimestamp(20, ts);
		                    System.out.println("b4 exe ");
		                    cs.execute();
		                    JournalVoucherNo2 = cs.getBigDecimal(5).intValue();
		                    errcode = cs.getInt(18);		
							System.out.println("SQLCODE::::::" + errcode+" vr no"+JournalVoucherNo2);

						} catch (Exception e) {
							System.out.println("e");
							e.printStackTrace();
							connection.rollback();
							//connection.commit();
						}

						try {
							String su = "SELECT JRNL_VCHR_DR_CR_SNO,    ACCOUNT_HEAD_CODE,    CR_DR_INDICATOR,    SUB_LEDGER_TYPE_CODE,    SUB_LEDGER_CODE,    BILL_NO,    BILL_TYPE,    AGREEMENT_NO,    AGREEMENT_DATE,    BILL_DATE,    AMOUNT  FROM PMS_FAS_JOURNAL_TRANSACTION_VW  WHERE ACCOUNTING_UNIT_ID    =?   AND ACCOUNTING_FOR_OFFICE_ID=?   AND CASHBOOK_YEAR           =?   AND CASHBOOK_MONTH          =?   AND JRNL_VCHR_SNO           =?  ORDER BY JRNL_VCHR_DR_CR_SNO";
							ps1 = connection.prepareStatement(su);
							ps1.setInt(1, cmbAcc_UnitCode);
							ps1.setInt(2, cmbOffice_code);
							ps1.setInt(3, cboCashBook_Year);
							ps1.setInt(4, cboCashBook_Month);
							ps1.setInt(5, jou_vou_no);
							
							
							rs1 = ps1.executeQuery();
							while (rs1.next()) {
								String sql = "insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID, "
										+ "ACCOUNTING_FOR_OFFICE_ID ,    CASHBOOK_YEAR,  "
										+ "  CASHBOOK_MONTH,    VOUCHER_NO,    SL_NO,    ACCOUNT_HEAD_CODE,"
										+ "    CR_DR_INDICATOR,    SUB_LEDGER_TYPE_CODE,    SUB_LEDGER_CODE,  "
										+ "  BILL_NO,    BILL_TYPE,    AGREEMENT_NO,    AGREEMENT_DATE,   "
										+ " BILL_DATE,        AMOUNT,        UPDATED_BY_USER_ID,    UPDATED_DATE" +
												",CB_REF_NO,CB_REF_DATE ) "
										+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
								try{
								ps = connection.prepareStatement(sql);
								
								ps.setInt(1, cmbAcc_UnitCode);
								ps.setInt(2, cmbOffice_code);
								ps.setInt(3, cby);
								ps.setInt(4, cbm);
								ps.setInt(5, JournalVoucherNo2);
								ps.setInt(6, rs1.getInt("JRNL_VCHR_DR_CR_SNO"));
								ps.setInt(7, rs1.getInt("ACCOUNT_HEAD_CODE"));
								ps.setString(8, rs1
										.getString("CR_DR_INDICATOR"));
								ps
										.setInt(9, rs1
												.getInt("SUB_LEDGER_TYPE_CODE"));
								ps.setInt(10, rs1.getInt("SUB_LEDGER_CODE"));
								ps.setString(11, rs1.getString("BILL_NO"));
								ps.setString(12, rs1.getString("BILL_TYPE"));
								ps.setString(13, rs1.getString("AGREEMENT_NO"));
								ps.setDate(14, rs1.getDate("AGREEMENT_DATE"));
								ps.setDate(15, rs1.getDate("BILL_DATE"));
								ps.setDouble(16, rs1.getFloat("AMOUNT"));
								ps.setString(17, userid);
								ps.setTimestamp(18, ts);
								ps.setInt(19, 0);
								ps.setDate(20, null);
								kk = ps.executeUpdate();
								System.out.println("final kk "+kk);
								}
								catch (Exception e) {
									System.out.println("trn");
									e.printStackTrace();
									// TODO: handle exception
								}
							}
						} catch (Exception e) {
							e.printStackTrace();
							connection.rollback();
							///connection.commit();
						}

					}

				}
				System.out.println("errcode "+errcode+" kk "+kk);
				if ((errcode != 0) || (kk <= 0)) {
					System.out.println("redirect");
					sendMessage(response, "Journal Creation Failed ", "ok");
					xml = xml + "<flag>failure</flag>";
					connection.rollback();
					//connection.commit();
				} else {
                                               
                                                //added on 29/08/2011 to update the jnl vcher details in Pms_Wb_Jrnl_Vchr table after journal creation-----
                                                  try
                                                  {
                                                    String upqy="Update Pms_Wb_Jrnl_Vchr a Set FAS_JVR_POSTED='Y',\n" + 
                                                    "(Fas_Jvr_Posted_By,Fas_Jvr_No,Fas_Jvr_Date)=\n" + 
                                                    "(Select Updated_By_User_Id,Voucher_No,Voucher_Date\n" + 
                                                    "From Fas_Journal_Master b Where\n" + 
                                                    "a.Accounting_Unit_Id=b.Accounting_Unit_Id\n" + 
                                                    "And a.Accounting_For_Office_Id=b.Accounting_For_Office_Id\n" + 
                                                    "And a.Cashbook_Month=b.Cashbook_Month\n" + 
                                                    "and a.cashbook_year=b.cashbook_year and b.Voucher_No=? \n" + 
                                                    ")\n" + 
                                                    "Where a.Accounting_Unit_Id=? And\n" + 
                                                    "a.Accounting_For_Office_Id=?\n" + 
                                                    "And a.Cashbook_Month=?\n" + 
                                                    "and a.cashbook_year=?";
                                                    System.out
															.println("upqy >>> "+upqy);
                                                    ps2=connection.prepareStatement(upqy);
                                                    ps2.setInt(1, JournalVoucherNo2);
                                                      ps2.setInt(2,cmbAcc_UnitCode);
                                                      ps2.setInt(3,cmbOffice_code);
                                                      ps2.setInt(4,cboCashBook_Month);
                                                      ps2.setInt(5,cboCashBook_Year);
                                                    
                                                      err=ps2.executeUpdate();
                                                      System.out
															.println("err"+err);
                                                  }
                                                  catch(Exception e)
                                                  {
                                                  System.out.println("Exception in updating the details in Pms_Wb_Jrnl_Vchr*****"+e);
                                                  connection.rollback();
                                                  }
                                                if (err>0)
                                                {
                                                	 connection.commit();
                                                System.out.println("testttttt******"+err);
                                                sendMessage(response,"Journal has been Created and pms journal details updated Successfully ", "ok");
                                                }
                                        }
			} catch (Exception e) {
				e.printStackTrace();
			}
		    finally
		    {
		        System.out.println("done");
		        try{
		        	connection.setAutoCommit(true);  }catch(SQLException sqle){}
		    }
		}

	}

	private void sendMessage(HttpServletResponse response, String msg,
			String bType) {
		try {
			String url = "org/Library/jsps/MessengerOkBack.jsp?message=" + msg
					+ "&button=" + bType;
			response.sendRedirect(url);
		} catch (Exception e) {
			System.out.println("error in messenger" + e);
		}
	}
}
