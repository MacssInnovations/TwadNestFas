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
 * Servlet implementation class BRS_Create_From_Journal
 */
public class BRS_Create_From_Journal extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BRS_Create_From_Journal() {
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
			int txtBankID = 0;
			int txtBranchID = 0;
			String txtOprMode = null;
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
				cmbBankAccNo =Long.parseLong(request
						.getParameter("cmbBankAccNo"));
			} catch (Exception e) {
				System.out.println("Error Not Getting Bank Account Number -->"
						+ e);
			}
			/* Get Operation Mode */
			try {
				txtOprMode = request.getParameter("txtOprMode");
			} catch (Exception e) {
				System.out.println("Error Not Getting Operation Mode -->" + e);
			}
			String module_id = null;
			// Joan chenged on 31 Mar 2015 
			if (txtOprMode.equals("COL") || txtOprMode.equals("OPR-NRDWP-Main") || txtOprMode.equals("SCH")) {
				module_id = "MF004";
			} else if (txtOprMode.equals("OPR")) {
				module_id = "MF005";
			}
			/* filter Flag */
			String filterFlag = request.getParameter("filterFlag");

			/* Get Bank ID */
			try {
				String txtBankID2 = request.getParameter("txtBankID");
				if (txtBankID2 != null) {
					if (txtBankID2.equals("")) {
						txtBankID = 0;
					} else {
						txtBankID = Integer.parseInt(txtBankID2);
					}
				}
			} catch (Exception e) {
				System.out.println("Error Not Getting Bank ID -->" + e);
			}

			/* Get Branch ID */
			try {
				String txtBranchID2 = request.getParameter("txtBranchID");
				if (txtBranchID2 != null) {
					if (txtBranchID2.equals("")) {
						txtBranchID = 0;
					} else {
						txtBranchID = Integer.parseInt(txtBranchID2);
					}
				}
			} catch (Exception e) {
				System.out.println("Error Not Getting Branch ID  -->" + e);
			}

			String r_date = null;
			String w_date = null;
			String doc_date = null;
			String Stringdate = null;
			String Stringdate1 = null;
			String Stringdate2 = null;
			Calendar cal = Calendar.getInstance();

			int f_month = cal.get(Calendar.MONTH) + 1;
			int f_year = cal.get(Calendar.YEAR);
			int f_year1 = f_year - 1;
			try {

				/* Get Cashbook Year */
				try {
					txtCB_Year = Integer.parseInt(request
							.getParameter("txtCB_Year"));
				} catch (Exception e) {
					System.out.println("Error Not Getting Cashbook Year -->"
							+ e);
				}

				/* Get Cashbook Month */
				try {
					txtCB_Month = Integer.parseInt(request
							.getParameter("txtCB_Month"));
				} catch (Exception e) {
					System.out.println("Error Not Getting Cashbook Month -->"
							+ e);
				}

				String s1 =" select decode(vou_no,null,0,vou_no) as vou_no,CR_DR_INDICATOR,AMOUNT,VOUCHER_DATE,"
								+ " CHEQUE_NO from ( select ACCOUNTING_UNIT_ID as acc_u_id,BANK_ID,"
								+ " BRANCH_ID,BANK_AC_NO,AC_HEAD_CODE as acc_hd_cd from "
								+ " FAS_OFFICE_BANK_AC_CURRENT where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and "
								+ " BANK_ID="+txtBankID+" and BRANCH_ID="+txtBranchID+" and BANK_AC_NO="+cmbBankAccNo+" and module_id='"+module_id+"' )a "
								+ " left outer join (select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,"
								+ "  VOUCHER_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,AMOUNT,CASHBOOK_YEAR,"
								+ "  CASHBOOK_MONTH from fas_journal_transaction where  ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode
								+ " and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month
								+ "  and VOUCHER_NO not in (select DOC_NO from FAS_BRS_TRANSACTION where "
								+ " ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" AND CASHBOOK_YEAR    "
								+ "       ="+txtCB_Year+" AND CASHBOOK_MONTH          ="+txtCB_Month+" AND TWAD_OR_NON_TWAD  "
								+ "        ='T' and doc_type='J'))b on a.acc_u_id = b.ACCOUNTING_UNIT_ID "
								+ " and a.acc_hd_cd = b.ACCOUNT_HEAD_CODE left outer join"
								+ " ( select ACCOUNTING_UNIT_ID as acc_u_id1,ACCOUNTING_FOR_OFFICE_ID "
								+ " as acc_off_id,VOUCHER_NO as vou_no,VOUCHER_DATE,CHEQUE_NO "
								+ " from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and"
								+ "  ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCB_Year+" and "
								+ " CASHBOOK_MONTH="+txtCB_Month+" and JOURNAL_STATUS='L' and VOUCHER_NO not in (select doc_no "
								+ " from FAS_BRS_TRANSACTION where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and"
								+ "  ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" AND CASHBOOK_YEAR           = "+txtCB_Year
								+ " AND CASHBOOK_MONTH          ="+txtCB_Month+" AND TWAD_OR_NON_TWAD          ='T' and doc_type='J'))c "
								+ " on b.ACCOUNTING_UNIT_ID =c.acc_u_id1 and "
								+ " b.ACCOUNTING_FOR_OFFICE_ID =c.acc_off_id and "
								+ " b.VOUCHER_NO =c.vou_no  ";
				System.out.println(s1);
				System.out.println(txtBankID+":"+txtBranchID+"::"+module_id);
				ps = connection.prepareStatement(s1);
			/*	ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, txtBankID);
				ps.setInt(3, txtBranchID);
				ps.setLong(4, cmbBankAccNo);
				ps.setString(5, module_id);
				ps.setInt(6, cmbAcc_UnitCode);
				ps.setInt(7, cmbOffice_code);
				ps.setInt(8, txtCB_Year);
				ps.setInt(9, txtCB_Month);
				ps.setInt(10, cmbAcc_UnitCode);
				ps.setInt(11, cmbOffice_code);
				ps.setInt(12, txtCB_Year);
				ps.setInt(13, txtCB_Month);
				ps.setString(14, "T");
				ps.setInt(15, cmbAcc_UnitCode);
				ps.setInt(16, cmbOffice_code);
				ps.setInt(17, txtCB_Year);
				ps.setInt(18, txtCB_Month);
				ps.setInt(19, cmbAcc_UnitCode);
				ps.setInt(20, cmbOffice_code);
				ps.setInt(21, txtCB_Year);
				ps.setInt(22, txtCB_Month);
				ps.setString(23, "T");  */

				rs = ps.executeQuery();

				xml = xml + "<flag>success</flag>";
				while (rs.next()) {
					int vouno = rs.getInt("vou_no");
					System.out.println("vouno:::"+vouno);
					if (vouno != 0) {
						Date r_date1 = rs.getDate("VOUCHER_DATE");
						Date doc_date1 = rs.getDate("VOUCHER_DATE");

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

						Date w_date1 = rs.getDate("VOUCHER_DATE");
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

						xml = xml + "<r_date>" + r_date + "</r_date>";
						xml = xml + "<w_date>" + w_date + "</w_date>";
						xml = xml + "<w_challan_no>" + rs.getInt("vou_no")
								+ "</w_challan_no>";
						xml = xml + "<r_cheque_dd_no>" + rs.getInt("CHEQUE_NO")
								+ "</r_cheque_dd_no>";
						if (rs.getString("CR_DR_INDICATOR").equals("CR")) {
							xml = xml + "<cr_amount>" + rs.getInt("AMOUNT")
									+ "</cr_amount>";
							xml = xml + "<dr_amount>0</dr_amount>";
						} else if (rs.getString("CR_DR_INDICATOR").equals("DR")) {
							xml = xml + "<cr_amount>0</cr_amount>";
							xml = xml + "<dr_amount>" + rs.getInt("AMOUNT")
									+ "</dr_amount>";
						}

						xml = xml + "<doc_no>" + rs.getInt("vou_no")
								+ "</doc_no>";
						xml = xml + "<doc_type>J</doc_type>";
						xml = xml + "<com_doc_date>" + doc_date
								+ "</com_doc_date>";
					}
				}

				String sql2 = "SELECT							" + "	REASON_CODE,			        "
						+ "	REASON_SHORT_DESC			" + "FROM 					"
						+ "	FAS_BRS_REASON_CATALOGUE 		";

				ps1 = connection.prepareStatement(sql2);
				rs2 = ps1.executeQuery();

				while (rs2.next()) {
					xml = xml + "<reason_pair>";
					xml = xml + "<reason_code>" + rs2.getString("reason_code")
							+ "</reason_code>";
					xml = xml + "<reason_desc>"
							+ rs2.getString("REASON_SHORT_DESC")
							+ "</reason_desc>";
					xml = xml + "</reason_pair>";
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

		/**
		 * Variables Declaration
		 */

		Connection con = null;
		PreparedStatement ps2 = null;
		CallableStatement cs = null;
		CallableStatement cs1 = null;

		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

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
		int txtBankID = 0;
		int txtBranchID = 0;
		float txtPBBalance = 0.0f;

		String IS_IT_CLEARING="";
		/* Get Accounting Unit ID */
		try {
			cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));

		} catch (Exception e) {
			System.out.println("Error Not Getting Accounitng Unit ID --> " + e);
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

		/* Get Cashbook Year */
		try {
			txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));

		} catch (Exception e) {
			System.out.println("Error Not Getting Cashbook Year -->" + e);
		}

		/* Get Cashbook Month */
		try {
			txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));

		} catch (Exception e) {
			System.out.println("Error Not Getting Cashbook Month -->" + e);
		}

		/* Get Bank Account Number */
		try {
			cmbBankAccNo =Long.parseLong(request
					.getParameter("cmbBankAccNo"));

		} catch (Exception e) {
			System.out.println("Error Not Getting Bank Account Number -->" + e);
		}

		/* Get Pass Book Balance Amount */
		try {
			txtPBBalance = Float.parseFloat(request
					.getParameter(("txtPBBalance")));

		} catch (Exception e) {
			System.out.println("Error Not Getting Pass Book Balance -->" + e);
		}

		/* Get Operation Mode */
		try {
			txtOprMode = request.getParameter("txtOprMode");
		} catch (Exception e) {
			System.out.println("Error Not Getting Operation Mode -->" + e);
		}

		/* Get Bank ID */
		try {
			String txtBankID2 = request.getParameter("txtBankID");
			if (txtBankID2 != null) {
				if (txtBankID2.equals("")) {
					txtBankID = 0;
				} else {
					txtBankID = Integer.parseInt(txtBankID2);
				}
			}
		} catch (Exception e) {
			System.out.println("Error Not Getting Bank ID -->" + e);
		}

		/* Get Branch ID */
		try {
			String txtBranchID2 = request.getParameter("txtBranchID");
			if (txtBranchID2 != null) {
				if (txtBranchID2.equals("")) {
					txtBranchID = 0;
				} else {
					txtBranchID = Integer.parseInt(txtBranchID2);
				}
			}
		} catch (Exception e) {
			System.out.println("Error Not Getting Branch ID  -->" + e);
		}

		/* User ID */
		String update_user = (String) session.getAttribute("UserId");
		System.out.println("update_user-->" + update_user);

		/* Get Time Stamp */
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
		System.out.println("Timestamp -->" + ts);
		try {
			PreparedStatement ps = con
					.prepareStatement(" select TB_STATUS,STATUS from(SELECT TB_STATUS,"
							+ "ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH "
							+ "FROM FAS_TRIAL_BALANCE_STATUS WHERE ACCOUNTING_UNIT_ID    =? "
							// + "AND ACCOUNTING_FOR_OFFICE_ID=? "
							+ "AND CASHBOOK_YEAR           =? "
							+ "AND CASHBOOK_MONTH          =? )a left outer join (select ACCOUNTING_UNIT_ID as accid,"
							+ "ACCOUNTING_FOR_OFFICE_ID as accoffid,CASHBOOK_YEAR as cby,CASHBOOK_MONTH as cbm,STATUS "
							+ "FROM FAS_BRS_MONTHLY_CLOSURE WHERE ACCOUNTING_UNIT_ID    =? "
							//+ "AND ACCOUNTING_FOR_OFFICE_ID=? "
							+ "AND CASHBOOK_YEAR           =? AND CASHBOOK_MONTH          =? )b on "
							+ "a.ACCOUNTING_UNIT_ID = b.accid and "
							+ "a.ACCOUNTING_FOR_OFFICE_ID = b.accoffid and "
							+ "a.CASHBOOK_YEAR = b.cby and a.CASHBOOK_MONTH = b.cbm  ");
			ps.setInt(1, cmbAcc_UnitCode);
			// ps.setInt(2, cmbOffice_code);
			ps.setInt(2, txtCB_Year);
			ps.setInt(3, txtCB_Month);
			ps.setInt(4, cmbAcc_UnitCode);
			// ps.setInt(6, cmbOffice_code);
			ps.setInt(5, txtCB_Year);
			ps.setInt(6, txtCB_Month);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {

				String tb_status = rs.getString("TB_STATUS");
				String status = rs.getString("STATUS");
				if (tb_status != null) {
					if (tb_status.equals("Y")) {

						if (status != null) {
							if (status.equals("L")) {
								sendMessage(
										response,
										"Records Not Inserted ,Because BRS Monthly Closure had already freezed ............ ",
										"ok");
							}
						} else {
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
											+ "PASSBOOK_BALANCE,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?)";
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
												.prepareStatement("update fas_brs_master set ENTRY_DATE=?,BANK_ID=?,BRANCH_ID=?,OPERATIONAL_MODE=?,PASSBOOK_BALANCE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=?");

										ps2.setTimestamp(1, ts);
										ps2.setInt(2, txtBankID);
										ps2.setInt(3, txtBranchID);
										ps2.setString(4, txtOprMode);
										ps2.setFloat(5, txtPBBalance);
										ps2.setString(6, update_user);
										ps2.setTimestamp(7, ts);
										ps2.setInt(8, cmbAcc_UnitCode);
										ps2.setInt(9, cmbOffice_code);
										ps2.setInt(10, txtCB_Year);
										ps2.setInt(11, txtCB_Month);
										ps2.setLong(12, cmbBankAccNo);
										ps2.executeUpdate();
									} else {
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
										ps2.setFloat(10, txtPBBalance);
										ps2.setString(11, update_user);
										ps2.setTimestamp(12, ts);
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
								    System.out.println("---------********    RK  *******---------------"+request
								                    .getParameter("RecordCount"));
								    RecordCount = Integer.parseInt(request
								                    .getParameter("txtNoofRecords"));
								} catch (Exception e) {
								}
							    System.out.println("---------********    RK  *******---------------"+RecordCount);
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

								/* Variables Declaration */
								Date r_date2 = null;
								Date w_date2 = null;
								int r_w_no2 = 0;
								int ccdd_no2 = 0;
								float cr_amount2 = 0.0f;
								float dr_amount2 = 0.0f;

								String doc_type2 = null;
								int doc_no2 = 0;
								Date doc_date2 = null;

								String EntryFoundInPassBook2 = null;
								Date Entry_Date2 = null;
								float Amt_in_PassBk2 = 0.0f;
								int Amt_Diff2 = 0;
								String cmbReason4Diff2 = "";
								String FollowUpAction2 = null;
								String ClearanceEntry2 = null;
								int pass_month=0;
								
								int pass_year=0;
								String sd[] = new String[10];
								java.util.Date d = null;
								Calendar c;
								try {
									ps2 = con
											.prepareStatement("delete from FAS_BRS_TRANSACTION_NOENTRY where ACCOUNTING_UNIT_ID=? and " +
                                                                                        "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TWAD_OR_NON_TWAD=? " +
                                                                                        "and ACCOUNT_NO=? and DOC_TYPE='J' ");
									ps2.setInt(1, cmbAcc_UnitCode);
									ps2.setInt(2, cmbOffice_code);
									ps2.setInt(3, txtCB_Year);
									ps2.setInt(4, txtCB_Month);
									ps2.setString(5, "T");
									ps2.setLong(6, cmbBankAccNo);
									ps2.executeUpdate();
								} catch (Exception e) {
									// TODO: handle exception
									e.printStackTrace();
								}
							    System.out.println("---------********    RK  *******---------------"+RecordCount);
								try {
								    System.out.println("---------********   test  *******---------------");
									for (int k = 0; k < RecordCount; k++) {

										/* Receipt Date */
										try {
											r_date[k] = request
													.getParameter("r_date" + k);

											if (!r_date[k].equalsIgnoreCase("")) {
												sd = r_date[k].split("/");

												c = new GregorianCalendar(
														Integer.parseInt(sd[2]),
														Integer.parseInt(sd[1]) - 1,
														Integer.parseInt(sd[0]));
												d = c.getTime();
												r_date2 = new Date(d.getTime());
											}
										} catch (Exception e) {
											System.out
													.println("Error Converting Receipt Date -->"
															+ e);
										}

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

										/* Receipt or Challan Number */
										try {
											r_w_no[k] = request
													.getParameter("r_w_no" + k);
											r_w_no2 = Integer
													.parseInt(r_w_no[k]);
										} catch (Exception e) {
											System.out.println(e);
										}

										/* Cheque or DD Number */
										try {
											ccdd_no[k] = request
													.getParameter("ccdd_no" + k);
											ccdd_no2 = Integer
													.parseInt(ccdd_no[k]);
										} catch (Exception e) {
											System.out.println(e);
										}

										/* Cr Amount */
										try {
											cr_amount[k] = request
													.getParameter("cr_amount"
															+ k);
											if (cr_amount[k] != null) {
												if (cr_amount[k].equals("")) {
													cr_amount2 = 0.0f;
												} else {
													cr_amount2 = Float
															.parseFloat(cr_amount[k]);
												}
											} else {
												cr_amount2 = 0.0f;
											}
										} catch (Exception e) {
											System.out.println(e);
										}

										/* Dr Amount */
										try {
											dr_amount[k] = request
													.getParameter("dr_amount"
															+ k);
											if (dr_amount[k] != null) {
												if (dr_amount[k].equals("")) {
													dr_amount2 = 0.0f;
												} else {
													dr_amount2 = Float
															.parseFloat(dr_amount[k]);
												}
											} else {
												dr_amount2 = 0.0f;
											}
										} catch (Exception e) {
											System.out.println(e);
										}

										/* Entry Found in Pass Book */
										try {
											EntryFoundInPassBook[k] = request
													.getParameter("EntryFoundInPassBook"
															+ k);
											EntryFoundInPassBook2 = EntryFoundInPassBook[k];
											System.out
													.println("EntryFoundInPassBook2-->"
															+ EntryFoundInPassBook2);
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
											Amt_in_PassBk2 = Float
													.parseFloat(Amt_in_PassBk[k]);
										} catch (Exception e) {
											System.out.println(e);
										}

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
											if (FollowUpAction[k] != null) {
												if (FollowUpAction[k]
														.equals("")) {
													FollowUpAction2 = "N";
												} else {
													FollowUpAction2 = FollowUpAction[k];
												}
											} else {
												FollowUpAction2 = "N";
											}
										} catch (Exception e) {
											System.out.println(e);
										}

										/* Clearance Entry */
										try {
											ClearanceEntry[k] = request
													.getParameter("ClearanceEntry"
															+ k);
											if (ClearanceEntry[k] != null) {
												if (ClearanceEntry[k]
														.equals("")) {
													ClearanceEntry2 = "N";
												} else {
													ClearanceEntry2 = ClearanceEntry[k];
												}
											} else {
												ClearanceEntry2 = "N";
											}
										} catch (Exception e) {
											System.out.println(e);
										}

										/* Doc type */
										try {
											doc_type[k] = request
													.getParameter("doc_type"
															+ k);
											doc_type2 = doc_type[k];
										} catch (Exception e) {
											System.out.println(e);
										}

										/* Doc Number */
										try {
											doc_no[k] = request
													.getParameter("doc_no" + k);
											doc_no2 = Integer
													.parseInt(doc_no[k]);
										} catch (Exception e) {
											System.out.println(e);
										}
										
										//IS_IT_CLEARING_ENTRY is to be updated as Y if poup updated
										
										try
										{
											String popupck="SELECT * FROM FAS_BRS_TRANSACTION WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+" AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" AND extract(year from doc_date)           = "+txtCB_Year+
												" AND extract(month from doc_date)          ="+txtCB_Month+" AND ACCOUNT_NO              ="+cmbBankAccNo+" AND DOC_TYPE                ='NT' and CLEARENCE_DATE is not null and (CR_AMOUNT=? and DR_AMOUNT=?)";
										System.out.println("popupck:: updated or not :::"+popupck);
											PreparedStatement pps=con.prepareStatement(popupck);
											pps.setFloat(1, dr_amount2);
											pps.setFloat(2, cr_amount2);
											ResultSet ress=pps.executeQuery();
											if(ress.next())
											{
												IS_IT_CLEARING="Y";
												
											}
											else
											{
												IS_IT_CLEARING="";
												
											}
										}
										catch(Exception ee)
										{
											System.out.println("ee:::"+ee);
										}
										cs = con.prepareCall("call FAS_BRS_PROCEDURE_NEW(?::numeric,?::numeric,?::numeric,?::numeric,?,?,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?::numeric,?::numeric,?,?,?,?,?,?::numeric,?,?::numeric,?,?::numeric,?::numeric,?::numeric,?)");
										cs.setInt(1, cmbAcc_UnitCode);
										cs.setInt(2, cmbOffice_code);
										cs.setInt(3, txtCB_Year);
										cs.setInt(4, txtCB_Month);
										cs.setDate(5, r_date2);
										cs.setDate(6, w_date2);
										cs.setInt(7, r_w_no2);
										cs.setInt(8, ccdd_no2);
										cs.setFloat(9, cr_amount2);
										cs.setFloat(10, dr_amount2);
										cs.setString(11, EntryFoundInPassBook2);
										cs.setDate(12, Entry_Date2);
										cs.setFloat(13, Amt_in_PassBk2);
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
										cs1.setNull(26,java.sql.Types.NUMERIC);
										cs.setString(27, "INSERT");
										/*cs.registerOutParameter(24,
												java.sql.Types.NUMERIC);
										cs.setString(25, "INSERT");
										cs.setInt(26, pass_month);System.out.println("pass_month >>  "+pass_month);
										cs.setInt(27, pass_year);System.out.println("pass_year >>  "+pass_year);*/
										if (EntryFoundInPassBook2.equals("NA")) {
											System.out.println("no entry:::");
											cs1 = con
													.prepareCall("call FAS_BRS_PROCEDURE_NOENTRY(?::numeric,?::numeric,?::numeric,?::numeric,?,?,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?::numeric,?::numeric,?,?,?,?,?,?::numeric,?,?,?::numeric,?)");
											cs1.setInt(1, cmbAcc_UnitCode);
											cs1.setInt(2, cmbOffice_code);
											cs1.setInt(3, txtCB_Year);
											cs1.setInt(4, txtCB_Month);
											cs1.setDate(5, r_date2);
											cs1.setDate(6, w_date2);
											cs1.setInt(7, r_w_no2);
											cs1.setInt(8, ccdd_no2);
											cs1.setFloat(9, cr_amount2);
											cs1.setFloat(10, dr_amount2);
											cs1.setString(11,
													EntryFoundInPassBook2);
											cs1.setDate(12, Entry_Date2);
											cs1.setFloat(13, Amt_in_PassBk2);
											cs1.setInt(14, Amt_Diff2);
											cs1.setString(15, cmbReason4Diff2);
											cs1.setString(16, "Y");
											cs1.setString(17, ClearanceEntry2);
											cs1.setDate(18, doc_date2);
											cs1.setString(19, update_user);
											cs1.setLong(20, cmbBankAccNo);
											cs1.setString(21, doc_type2);
											cs1.setInt(22, doc_no2);
											cs1.registerOutParameter(23,
													java.sql.Types.NUMERIC);
											cs1.setNull(23,java.sql.Types.NUMERIC);
											cs1.setString(24, "INSERT");
											cs1.executeQuery();

											//int errcode = cs1.getInt(23);
											int errcode = cs1.getBigDecimal(23).intValue();
											if (errcode != 0) {
												con.rollback();
												con.setAutoCommit(true);
												sendMessage(
														response,
														"TWAD Transaction Records Not Inserted ............ ",
														"ok");
											}
										} else {

											cs.executeQuery();
											//int errcode = cs.getInt(26);
											int errcode = cs1.getBigDecimal(26).intValue();

											if (errcode != 0) {
												con.rollback();
												con.setAutoCommit(true);
												sendMessage(
														response,
														"TWAD Transaction Records Not Inserted ............ ",
														"ok");

											}
										}

										/* Clear Variables Values */

										r_date2 = null;
										w_date2 = null;
										r_w_no2 = 0;
										ccdd_no2 = 0;
										cr_amount2 = 0;
										dr_amount2 = 0;
										EntryFoundInPassBook2 = null;
										Entry_Date2 = null;
										Amt_in_PassBk2 = 0;
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
								con.commit();
								con.setAutoCommit(true);
								sendMessage(
										response,
										"Records Saved Successfully ............ ",
										"ok");

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
			String url = "org/FAS/FAS1/BRS/jsps/MessengerOkBack2.jsp?message="
					+ msg + "&button=" + bType;
			System.out.println("after url");
			response.sendRedirect(url);
		} catch (IOException e) {
		}
	}

}