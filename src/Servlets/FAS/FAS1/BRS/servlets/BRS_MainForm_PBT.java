package Servlets.FAS.FAS1.BRS.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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
 * Servlet implementation class BRS_MainForm_PBT
 */
public class BRS_MainForm_PBT extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BRS_MainForm_PBT() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
		
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

		Date date = new Date(0000 - 00 - 00);

		/* Get Parameters */
		int cmbAcc_UnitCode = 0;
		int cmbOffice_code = 0;
		int txtCB_Year = 0;
		int txtCB_Month = 0;

		long cmbBankAccNo = 0;
		String txtOprMode = null,cr_dr=null;
		int txtBankID = 0;
		int txtBranchID = 0;
		double txtPBBalance = 0.00;
		

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
		System.out.println("txtCB_Year forrrrrrrrrrrrrr:::"+txtCB_Year);
		/* Get Cashbook Month */
		try {
			txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
		} catch (Exception e) {
			System.out.println("Error Not Getting Cashbook Month -->" + e);
		}

		/* Get Bank Account Number */
		try {
			cmbBankAccNo = Long.parseLong(request
					.getParameter("cmbBankAccNo"));
		} catch (Exception e) {
			System.out.println("Error Not Getting Bank Account Number -->" + e);
		}

		/* Get Operation Mode */
		try {
			txtOprMode = request.getParameter("txtOprMode");
		} catch (Exception e) {
			System.out.println("Error Not Getting Operation Mode -->" + e);
		}

		/* filter Flag */
		String filterFlag = request.getParameter("filterFlag");

		/* Get Bank ID */
		try {
			String txtBankID2 = request.getParameter("txtBankID");
			System.out.println("txtBankID2:" + txtBankID2);
			if (txtBankID2 != null) {
				if (txtBankID2.equals("")) {
					txtBankID = 0;
				} else {
					txtBankID = Integer.parseInt(txtBankID2);
					System.out.println("txtBankID:" + txtBankID);
				}
			}
		} catch (Exception e) {
			System.out.println("Error Not Getting Bank ID -->" + e);
		}

		/* Get Branch ID */
		try {
			String txtBranchID2 = request.getParameter("txtBranchID");
			System.out.println("txtBranchID2:" + txtBranchID2);
			if (txtBranchID2 != null) {
				if (txtBranchID2.equals("")) {
					txtBranchID = 0;
				} else {
					txtBranchID = Integer.parseInt(txtBranchID2);
					System.out.println("txtBranchID:" + txtBranchID);
				}
			}
		} catch (Exception e) {
			System.out.println("Error Not Getting Branch ID  -->" + e);
		}

		/* Get Pass Book Balance Amount */
		try {
			txtPBBalance =Double.parseDouble(request
					.getParameter(("txtPBBalance")));
		} catch (Exception e) {
			System.out.println("Error Not Getting Pass Book Balance -->" + e);
		}
	
      /*    GET CR/DR TYPE VALUE */
		
		try {
			cr_dr =request
					.getParameter(("crdr_value"));
		} catch (Exception e) {
			System.out.println("Error Not Getting Pass Book Balance -->" + e);
		}
		
		/* User ID */
		String update_user = (String) session.getAttribute("UserId");

		/* Get Time Stamp */
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
		try {
			System.out.println(" select TB_STATUS,STATUS from(SELECT TB_STATUS,"
							+ "ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH "
							+ "FROM FAS_TRIAL_BALANCE_STATUS WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+" AND "
							+ "CASHBOOK_YEAR           ="+txtCB_Year 
							+ " AND CASHBOOK_MONTH          ="+txtCB_Month+" )a left outer join (select ACCOUNTING_UNIT_ID as accid,"
							+ "ACCOUNTING_FOR_OFFICE_ID as accoffid,CASHBOOK_YEAR as cby,CASHBOOK_MONTH as cbm,STATUS "
							+ "FROM FAS_BRS_MONTHLY_CLOSURE WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+" AND ACCOUNTING_FOR_OFFICE_ID= "+cmbOffice_code
							+ "AND CASHBOOK_YEAR           ="+txtCB_Year+" AND CASHBOOK_MONTH          ="+txtCB_Month+" )b on "
							+ "a.ACCOUNTING_UNIT_ID = b.accid and a.ACCOUNTING_FOR_OFFICE_ID = b.accoffid and "
							+ "a.CASHBOOK_YEAR = b.cby and a.CASHBOOK_MONTH = b.cbm  ");
			PreparedStatement ps = con.prepareStatement(" select TB_STATUS,STATUS from(SELECT TB_STATUS,"
							+ "ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH "
							+ "FROM FAS_TRIAL_BALANCE_STATUS WHERE ACCOUNTING_UNIT_ID    =? AND "
							+ "CASHBOOK_YEAR           =? "
							+ " AND CASHBOOK_MONTH          =? )a left outer join (select ACCOUNTING_UNIT_ID as accid,"
							+ "ACCOUNTING_FOR_OFFICE_ID as accoffid,CASHBOOK_YEAR as cby,CASHBOOK_MONTH as cbm,STATUS "
							+ "FROM FAS_BRS_MONTHLY_CLOSURE WHERE ACCOUNTING_UNIT_ID    =? AND ACCOUNTING_FOR_OFFICE_ID=? "
							+ " AND CASHBOOK_YEAR           =? AND CASHBOOK_MONTH          =? )b on "
							+ "a.ACCOUNTING_UNIT_ID = b.accid and a.ACCOUNTING_FOR_OFFICE_ID = b.accoffid and "
							+ "a.CASHBOOK_YEAR = b.cby and a.CASHBOOK_MONTH = b.cbm  ");
			ps.setInt(1, cmbAcc_UnitCode);
                        
			ps.setInt(2, txtCB_Year);
			ps.setInt(3, txtCB_Month);
			ps.setInt(4, cmbAcc_UnitCode);
                        ps.setInt(5, cmbOffice_code);
                        ps.setInt(6, txtCB_Year);
			ps.setInt(7, txtCB_Month);
			ResultSet rs = ps.executeQuery();
			if (rs.next()) {
System.out.println("Its mine  ");
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
							try { // Main Try I

								//con.clearWarnings();
								con.setAutoCommit(false);

								/**
								 * --------------------- General Details -
								 * Master Trans -----------------------
								 */
								
								System.out.println("General Details");
								try {
									String sql_insert_mst = "						"
											+ "insert into fas_brs_master_PBT ( 		"
											+ "  ACCOUNTING_UNIT_ID,				"
											+ "  ACCOUNTING_FOR_OFFICE_ID,		"
											+ "  CASHBOOK_YEAR,					"
											+ "  CASHBOOK_MONTH,					"
											+ "  ENTRY_DATE,						"
											+ "  ACCOUNT_NO,						"
											+ "  BANK_ID,							"
											+ "  BRANCH_ID,						"
											+ "  OPERATIONAL_MODE,				"
											+ "  PASSBOOK_BALANCE,				"
											+ "  UPDATED_BY_USER_ID,				"
											+ "  UPDATED_DATE,						"
											+ "  PASS_BOOK_BALANCE_TYPE,				"
											+ "  PASS_BOOK_YEAR,				"
												+ "  PASS_BOOK_MONTH				"
											+ " ) 								" + " values							"
											+ " ( ?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)		";

									PreparedStatement ps5 = con
											.prepareStatement("select ACCOUNT_NO from fas_brs_master_PBT where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=?");
									ps5.setInt(1, cmbAcc_UnitCode);
									ps5.setInt(2, cmbOffice_code);
									ps5.setInt(3, txtCB_Year);
									ps5.setInt(4, txtCB_Month);
									ps5.setLong(5, cmbBankAccNo);
									ResultSet rss = ps5.executeQuery();
									if (rss.next()) {
										ps2 = con.prepareStatement("update fas_brs_master_PBT set ENTRY_DATE=?,BANK_ID=?,BRANCH_ID=?,OPERATIONAL_MODE=?,PASSBOOK_BALANCE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=?");
										ps2.setTimestamp(1, ts);
										ps2.setInt(2, txtBankID);
										ps2.setInt(3, txtBranchID);
										ps2.setString(4, txtOprMode);
										System.out.println("txtPBBalance >>>> "+txtPBBalance);
										ps2.setDouble(5, txtPBBalance);
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
										ps2.setDouble(10, txtPBBalance);
										ps2.setString(11, update_user);
										ps2.setTimestamp(12, ts);
										ps2.setString(13, cr_dr);
										ps2.setInt(14, txtCB_Year);
										ps2.setInt(15, txtCB_Month);
										ps2.executeUpdate();
									}
								} catch (Exception e) {
									con.rollback();
									con.setAutoCommit(true);
									sendMessage(response,
											"Records Not Inserted .... " + e,
											"ok");
									System.out.println(e);
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
									RecordCount = Integer.parseInt(request
											.getParameter("RecordCount"));
								} catch (Exception e) {
									System.out
											.println("Error Getting Total Number of Records in TWAD Transaction ");
								}

								int RecordCount1 = 0;

								/*
								 * Get Total Number of Transaction in TWAD
								 * Transactions
								 */
								try {
									RecordCount1 = Integer.parseInt(request
											.getParameter("RecordCountF"));
								} catch (Exception e) {
									System.out.println("Error Getting Total Number of Records in TWAD Transaction ");
								}

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

								String r_date1[] = new String[RecordCount1];
								String w_date1[] = new String[RecordCount1];
								String r_w_no1[] = new String[RecordCount1];
								String ccdd_no1[] = new String[RecordCount1];
								String cr_amount1[] = new String[RecordCount1];
								String dr_amount1[] = new String[RecordCount1];
								String doc_type1[] = new String[RecordCount1];
								String doc_no1[] = new String[RecordCount1];
								String doc_date1[] = new String[RecordCount1];

								/* Variables Declaration */
								Date r_date2 = null;
								Date w_date2 = null;
								int r_w_no2 = 0;
								int passyear=0,passmonth=0;
								int ccdd_no2 = 0;
								double cr_amount2 = 0.0;
								double dr_amount2 = 0.0;
								String doc_type2 = null;
								int doc_no2 = 0;
								Date doc_date2 = null;
								String EntryFoundInPassBook2 = null;
								Date Entry_Date2 = null;
								double Amt_in_PassBk2 = 0.0;
								int Amt_Diff2 = 0;
								String cmbReason4Diff2 = "";
								String FollowUpAction2 = null;
								String ClearanceEntry2 = null;

								Date r_date21 = null;
								Date w_date21 = null;
								int r_w_no21 = 0;
								int ccdd_no21 = 0;
								float cr_amount21 = 0.0f;
								float dr_amount21 = 0.0f;
								String doc_type21 = null;
								int doc_no21 = 0;
								Date doc_date21 = null;

								String sd1[] = new String[10];
								java.util.Date d1 = null;
								Calendar c1;

								String sd[] = new String[10];
								java.util.Date d = null;
								Calendar c;
								System.out.println("RecordCount1 >>> "+RecordCount1);
								System.out.println("RecordCount >>> "+RecordCount);
								try {
									ps2 = con
											.prepareStatement("delete from FAS_BRS_TRANSACTION_NOENTRYPBT where ACCOUNTING_UNIT_ID=? and " +
                                                                                        "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TWAD_OR_NON_TWAD=? " +
                                                                                        "and ACCOUNT_NO=? and DOC_TYPE != 'J' ");
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
									con.rollback();
									con.setAutoCommit(true);
								
								}
								try {
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
										System.out.println("r_date2  "+r_date2+"w_date2==>"+w_date2);
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
													cr_amount2 = Double
															.parseDouble(cr_amount[k]);
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
													dr_amount2 = 0.0;
												} else {
													dr_amount2 = Double
															.parseDouble(dr_amount[k]);
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
											Entry_Date[k] = request.getParameter("Entry_Date"+ k);

											if (!Entry_Date[k].equalsIgnoreCase("")) {
												sd = Entry_Date[k].split("/");
												passyear=Integer.parseInt(sd[2]);
												passmonth=Integer.parseInt(sd[1]);
												
												if(passmonth!=txtCB_Month)
												{
													if(passyear!=txtCB_Year){
													sendMessage(
															response,
															"PassBook Date should be with in CashBook Year & CashBook Month ............ ",
															"ok");	
												}else{
													sendMessage(
															response,
															"PassBook Date should be with in CashBook Year & CashBook Month ............ ",
															"ok");	
												}}else if(passyear!=txtCB_Year){
													sendMessage(
															response,
															"PassBook Date should be with in CashBook Year & CashBook Month............ ",
															"ok");
												}
													
												int pas_year=0,pas_month=0;
												pas_month=Integer.parseInt(sd[1]);
												pas_year=Integer.parseInt(sd[2]);
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
											Amt_in_PassBk[k] = request.getParameter("Amt_in_PassBk"+ k);
											Amt_in_PassBk2 = Double
													.parseDouble(Amt_in_PassBk[k]);
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
										

										if (EntryFoundInPassBook2.equals("NA")) {
											cs1 = con.prepareCall("{call FAS_BRS_PROCEDURE_NOENTRY_PBT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
											cs1.setInt(1, cmbAcc_UnitCode);
											cs1.setInt(2, cmbOffice_code);
											cs1.setInt(3, txtCB_Year);
											cs1.setInt(4, txtCB_Month);
											cs1.setDate(5, r_date2);
											cs1.setDate(6, w_date2);
											cs1.setInt(7, r_w_no2);
											cs1.setInt(8, ccdd_no2);
											cs1.setDouble(9, cr_amount2);
											cs1.setDouble(10, dr_amount2);
											cs1.setString(11,
													EntryFoundInPassBook2);
											cs1.setDate(12, Entry_Date2);
											cs1.setDouble(13, Amt_in_PassBk2);
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
											cs1.setString(24, "INSERT");
											cs1.executeQuery();

											int errcode = cs1.getInt(23);
											if (errcode != 0) {
												con.rollback();
												con.setAutoCommit(true);
												sendMessage(
														response,
														"TWAD Transaction Records Not Inserted ............ ",
														"ok");
											}
										} else {
											//entry found in passbook=y and Amt_Diff2>0 means 
											//insert into both noentry and brs_trn tables
											
												if(Amt_Diff2>0)
												{
													
													//no entry
													cs1 = con.prepareCall("{call FAS_BRS_PROCEDURE_NOENTRY_PBT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
													cs1.setInt(1, cmbAcc_UnitCode);
													cs1.setInt(2, cmbOffice_code);
													String[] entdate=Entry_Date[k].split("/");
												//	int passyear=
													//changes on 22-oct-2013 dhana
													cs1.setInt(3, passyear);
													cs1.setInt(4, passmonth);
													cs1.setDate(5, r_date2);
													cs1.setDate(6, w_date2);
													cs1.setInt(7, r_w_no2);
													cs1.setInt(8, ccdd_no2);
													if(cr_amount2>0)
													{
														cs1.setDouble(9, Amt_Diff2);
														cs1.setDouble(10,dr_amount2);	
													}
													else if(dr_amount2>0)
													{
														cs1.setDouble(9, cr_amount2);
														cs1.setDouble(10,Amt_Diff2);
													}
//													cs1.setDouble(9, cr_amount2);
//													cs1.setDouble(10, dr_amount2);
													cs1.setString(11,"NA");
													cs1.setDate(12, null);
													cs1.setDouble(13, 0);
													cs1.setInt(14, 0);
													cs1.setString(15, "");
													cs1.setString(16, "Y");
													cs1.setString(17, "N");
													cs1.setDate(18, doc_date2);
													cs1.setString(19, update_user);
													cs1.setLong(20, cmbBankAccNo);
													cs1.setString(21, doc_type2);
													cs1.setInt(22, doc_no2);
													cs1.registerOutParameter(23,
															java.sql.Types.NUMERIC);
													cs1.setString(24, "INSERT");
													cs1.executeQuery();

													int errcode = cs1.getInt(23);
													if (errcode != 0) {
														con.rollback();
														con.setAutoCommit(true);
														sendMessage(
																response,
																"TWAD Transaction Records Not Inserted ............ ",
																"ok");
													}	
												}
											
											String IS_IT_CLEARING="";
											cs = con.prepareCall("{call FAS_BRS_PROCEDURE_new_PBT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
											cs.setInt(1, cmbAcc_UnitCode);
											cs.setInt(2, cmbOffice_code);
										
											cs.setInt(3, txtCB_Year);
											cs.setInt(4, txtCB_Month);
											cs.setDate(5, r_date2);
											cs.setDate(6, w_date2);
											cs.setInt(7, r_w_no2);
											cs.setInt(8, ccdd_no2);
											cs.setDouble(9, cr_amount2);
										
											cs.setDouble(10, dr_amount2);
											cs.setString(11, EntryFoundInPassBook2);
											cs.setDate(12, Entry_Date2);System.out.println("Entry_Date2   "+Entry_Date2+" pas month "+passmonth+" pass year "+passyear);
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
											cs.setInt(24, passmonth);
											cs.setInt(25, passyear);
											cs.registerOutParameter(26,java.sql.Types.NUMERIC);
											cs.setString(27, "INSERT");
										
											
											cs.executeQuery();
											int errcode = cs.getInt(26);

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
									System.out.println("RecordCount1"
											+ RecordCount1);
									try {
										if (filterFlag.equals("yes")) {
											for (int k = 0; k < RecordCount1; k++) {
												/* Receipt Date */
												try {
													r_date1[k] = request
															.getParameter("r_date1"
																	+ k);

													if (!r_date1[k]
															.equalsIgnoreCase("")) {
														sd1 = r_date1[k]
																.split("/");

														c1 = new GregorianCalendar(
																Integer
																		.parseInt(sd1[2]),
																Integer
																		.parseInt(sd1[1]) - 1,
																Integer
																		.parseInt(sd1[0]));
														d1 = c1.getTime();
														r_date21 = new Date(d1
																.getTime());
													}
												} catch (Exception e) {
													System.out
															.println("Error Converting Receipt Date -->"
																	+ e);
												}

												/* Withdraw Date */
												try {
													w_date1[k] = request
															.getParameter("w_date1"
																	+ k);

													if (!w_date1[k]
															.equalsIgnoreCase("")) {
														sd1 = w_date1[k]
																.split("/");
														c1 = new GregorianCalendar(
																Integer
																		.parseInt(sd1[2]),
																Integer
																		.parseInt(sd1[1]) - 1,
																Integer
																		.parseInt(sd1[0]));
														d1 = c1.getTime();
														w_date21 = new Date(d1
																.getTime());
													}
												} catch (Exception e) {
													System.out.println(e);
												}
											System.out.println("secomd  w_date21 "+w_date21+ " r_date21 "+r_date21);	
												/* Receipt or Challan Number */
												try {
													r_w_no1[k] = request
															.getParameter("r_w_no1"
																	+ k);
													r_w_no21 = Integer
															.parseInt(r_w_no1[k]);
												} catch (Exception e) {
													System.out.println(e);
												}

												/* Cheque or DD Number */
												try {
													ccdd_no1[k] = request
															.getParameter("ccdd_no1"
																	+ k);
													ccdd_no21 = Integer
															.parseInt(ccdd_no1[k]);
												} catch (Exception e) {
													System.out.println(e);
												}

												/* Cr Amount */
												try {
													cr_amount1[k] = request
															.getParameter("cr_amount1"
																	+ k);
													if (cr_amount1[k] != null) {
														if (cr_amount1[k]
																.equals("")) {
															cr_amount21 = 0.0f;
														} else {
															cr_amount21 = Float
																	.parseFloat(cr_amount1[k]);
														}
													} else {
														cr_amount21 = 0.0f;
													}
												} catch (Exception e) {
													System.out.println(e);
												}

												/* Dr Amount */
												try {
													dr_amount1[k] = request
															.getParameter("dr_amount1"
																	+ k);
													if (dr_amount1[k] != null) {
														if (dr_amount1[k]
																.equals("")) {
															dr_amount21 = 0.0f;
														} else {
														}
													} else {
														dr_amount21 = 0.0f;
													}
												} catch (Exception e) {
													System.out.println(e);
												}

												/* Doc type */
												try {
													doc_type1[k] = request
															.getParameter("doc_type1"
																	+ k);
													doc_type21 = doc_type1[k];
												} catch (Exception e) {
													System.out.println(e);
												}

												/* Doc Date */
												try {
													doc_date1[k] = request
															.getParameter("doc_date1"
																	+ k);
													if (!doc_date1[k]
															.equalsIgnoreCase("")) {

														sd = doc_date1[k]
																.split("/");
														c = new GregorianCalendar(
																Integer
																		.parseInt(sd[2]),
																Integer
																		.parseInt(sd[1]) - 1,
																Integer
																		.parseInt(sd[0]));
														d = c.getTime();
														doc_date21 = new Date(d
																.getTime());

													} else {
														doc_date1[k] = "00/00/0000";
													}
												} catch (Exception e) {
													System.out.println(e);
												}

												/* Doc Number */
												try {
													doc_no1[k] = request
															.getParameter("doc_no1"
																	+ k);
													doc_no21 = Integer
															.parseInt(doc_no1[k]);
												} catch (Exception e) {
													System.out.println(e);
												}

												cs1 = con
														.prepareCall("{call FAS_BRS_PROCEDURE_NOENTRY_BPT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
												cs1.setInt(1, cmbAcc_UnitCode);
												cs1.setInt(2, cmbOffice_code);
												cs1.setInt(3, txtCB_Year);
												cs1.setInt(4, txtCB_Month);
												cs1.setDate(5, r_date21);
												cs1.setDate(6, w_date21);
												cs1.setInt(7, r_w_no21);
												cs1.setInt(8, ccdd_no21);
												cs1.setFloat(9, cr_amount21);
												cs1.setFloat(10, dr_amount21);
												cs1.setString(11, "");
												cs1.setDate(12, date.valueOf("0000-00-00"));
												cs1.setFloat(13, 0.0f);
												cs1.setInt(14, 0);
												cs1.setString(15, "");
												cs1.setString(16, "Y");
												cs1.setString(17, "N");
												cs1.setDate(18, doc_date21);
												cs1.setString(19, update_user);
												cs1.setLong(20, cmbBankAccNo);
												cs1.setString(21, doc_type21);
												cs1.setInt(22, doc_no21);
												cs1.registerOutParameter(23,
														java.sql.Types.NUMERIC);
												cs1.setString(24, "INSERT");
												cs1.executeQuery();
												int errcode = cs1.getInt(23);
												if (errcode != 0) {
													con.rollback();
													con.setAutoCommit(true);
													sendMessage(
															response,
															"--TWAD Transaction Records Not Inserted--",
															"ok");
												}
											}
										}
									} catch (Exception e) {
										e.printStackTrace();
										// TODO: handle exception
									}

								} catch (Exception e) {
									System.out.println(e);
									con.rollback();
									con.setAutoCommit(true);
									sendMessage(response,
											"TWAD Transaction Records Not Inserted ............ "
													+ e, "ok");
									System.out.println(e);
									e.printStackTrace();
									return;
								}

								/**
								 * -------------------------- NON TWAD
								 * Transaction ---------------------------Bank Transactions
								 */

								try {

									int RecordCount_NT = 0;

									/*
									 * Get Total Number of Transaction in
									 * NON-TWAD Transactions
									 */
									System.out.println("&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&&");
									try {
										System.out.println(request
												.getParameter("RecordCountNT"));
										System.out.println(request
												.getParameter("test"));
										RecordCount_NT = Integer
												.parseInt(request
														.getParameter("RecordCountNT"));
									} catch (Exception e) {
										System.out
												.println("Error Getting Total Number of Records in TWAD Transaction ");
									}

									/* String Array Declaration */
									String Entry_Date_NT[] = new String[RecordCount_NT];
									String Particualrs_NT[] = new String[RecordCount_NT];
									String ChequeNo_NT[] = new String[RecordCount_NT];
									String Details_NT[] = new String[RecordCount_NT];
									String cr_amount_NT[] = new String[RecordCount_NT];
									String dr_amount_NT[] = new String[RecordCount_NT];
									String Trans_Type_NT[] = new String[RecordCount_NT];
									String FollowUpAction_NT[] = new String[RecordCount_NT];
									String ClearanceEntry_NT[] = new String[RecordCount_NT];
									String Journalized[] = new String[RecordCount_NT];

									/* Variables Declaration */
									Date Entry_Date_NT2 = null;
									String Particualrs_NT2 = "";
									int ChequeNo_NT2 = 0;
									String Details_NT2 = "";
									float cr_amount_NT2 = 0.0f;
									float dr_amount_NT2 = 0.0f;
									String Trans_Type_NT2 = "";
									String FollowUpAction_NT2 = "";
									String ClearanceEntry_NT2 = "";
									String Journalized2 = "N";
System.out.println("RecordCount_NT  RecordCount_NTRecordCount_NT  "+RecordCount_NT);
									for (int k = 0; k < RecordCount_NT; k++) {
										//System.out.println("RecordCount_NT len:::"+RecordCount_NT);
										/* Entry Date */
										try {
											Entry_Date_NT[k] = request
													.getParameter("Entry_Date_NT"
															+ k);
											if (!Entry_Date_NT[k]
													.equalsIgnoreCase("")) {
												sd = Entry_Date_NT[k]
														.split("/");
												passmonth=Integer.parseInt(sd[1]);
												passyear=	Integer.parseInt(sd[2]);	
												
												c = new GregorianCalendar(
														Integer.parseInt(sd[2]),
														Integer.parseInt(sd[1]) - 1,
														Integer.parseInt(sd[0]));
												d = c.getTime();
												Entry_Date_NT2 = new Date(d
														.getTime());
											}
										} catch (Exception e) {
											System.out.println(e);
										}

										/* Particulars */
										try {
											Particualrs_NT[k] = request
													.getParameter("Particualrs_NT"
															+ k);
											Particualrs_NT2 = Particualrs_NT[k];
										} catch (Exception e) {
											System.out.println(e);
										}

										/* Cheque Number */
										try {
											ChequeNo_NT[k] = request
													.getParameter("ChequeNo_NT"
															+ k);
											ChequeNo_NT2 = Integer
													.parseInt(ChequeNo_NT[k]);
										} catch (Exception e) {
											System.out.println(e);
										}
										System.out.println("ChequeNo_NT2 slno:::"+ChequeNo_NT2);

										/* Details */
										try {
											Details_NT[k] = request
													.getParameter("Details_NT"
															+ k);
											Details_NT2 = Details_NT[k];
										} catch (Exception e) {
											System.out.println(e);
										}
										System.out.println("Details_NT2"+Details_NT2);
										/* Cr Amount */
										
										int dr_nt=0,cr_nt=0;
										
										try {
											cr_amount_NT[k] = request.getParameter("cr_amount_NT"+ k);
											if (cr_amount_NT[k] != null) {
												if (cr_amount_NT[k].equals("")) {
													cr_amount_NT2 = 0.0f;
													cr_nt=0;
												} else {
													cr_amount_NT2 = Float.parseFloat(cr_amount_NT[k]);
													cr_nt=1;
												}

											} else {
												cr_amount_NT2 = 0.0f;
												cr_nt=0;
											}
										} catch (Exception e) {
											System.out.println(e);
										}
										System.out.println("cr_amount_NT2"+cr_amount_NT2);
										/* Dr Amount */
									
										try {
											dr_amount_NT[k] = request.getParameter("dr_amount_NT"+ k);
											if (dr_amount_NT[k] != null) {
												if (dr_amount_NT[k].equals("")) {
													dr_amount_NT2 = 0.0f;
													dr_nt=0;
												} else {
													dr_amount_NT2 = Float.parseFloat(dr_amount_NT[k]);
													dr_nt=1;
												}

											} else {
												dr_amount_NT2 = 0.0f;
												dr_nt=0;
											}
										} catch (Exception e) {
											System.out.println(e);
										}
										System.out.println("dr_amount_NT2"+dr_amount_NT2);
										/* Transaction Type */
										try {
											Trans_Type_NT[k] = request
													.getParameter("Trans_Type_NT"
															+ k);
											Trans_Type_NT2 = Trans_Type_NT[k];
										} catch (Exception e) {
											System.out.println(e);
										}
										System.out.println("Trans_Type_NT2"+Trans_Type_NT2);
										/* Follow up action Required */
										try {
											FollowUpAction_NT[k] = request
													.getParameter("FollowUpAction_NT"
															+ k);

											if (FollowUpAction_NT[k] != null) {
												FollowUpAction_NT2 = FollowUpAction_NT[k];
											} else {
												FollowUpAction_NT2 = "N";
											}
										} catch (Exception e) {
											System.out.println(e);
										}
										System.out.println("FollowUpAction_NT2"+FollowUpAction_NT2);
										/* Clearance Entry */
										try {
											ClearanceEntry_NT[k] = request
													.getParameter("ClearanceEntry_NT"
															+ k);
											if (ClearanceEntry_NT[k] != null) {
												ClearanceEntry_NT2 = ClearanceEntry_NT[k];
											} else {
												ClearanceEntry_NT2 = "N";
											}
										} catch (Exception e) {
											System.out.println(e);
										}
										System.out.println("ClearanceEntry_NT2"+ClearanceEntry_NT2);
										/* Journalized */
										try {
											Journalized[k] = request.getParameter("Journalized"+ k);
											Journalized2 = Journalized[k];
										} catch (Exception e) {
											System.out.println(e);
										}
										if (Journalized2 != null) {

										} else {
											Journalized2 = "N";
										}
										
										
										System.out.println("cr_nt"+cr_nt);
											System.out.println("dr_nt"+dr_nt);
										if(cr_nt != 0 || dr_nt != 0  ){
											
										cs1 = con.prepareCall("{call FAS_BRS_PROCEDURE_NT_PBT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
										cs1.setInt(1, cmbAcc_UnitCode);
										cs1.setInt(2, cmbOffice_code);
										cs1.setInt(3, txtCB_Year);
										cs1.setInt(4, txtCB_Month);
										cs1.setString(5, Particualrs_NT2);
										cs1.setInt(6, ChequeNo_NT2);
										cs1.setFloat(7, cr_amount_NT2);
										cs1.setFloat(8, dr_amount_NT2);
										cs1.setDate(9, Entry_Date_NT2);
										cs1.setString(10, Trans_Type_NT2);
										cs1.setString(11, FollowUpAction_NT2);
										cs1.setString(12, ClearanceEntry_NT2);
										cs1.setString(13, update_user);
										cs1.setLong(14, cmbBankAccNo);
										cs1.setInt(15, 0);
										cs1.setInt(16, 0);
										cs1.setString(17, "NT");
										cs1.setString(18, Details_NT2);
										cs1.registerOutParameter(19,java.sql.Types.NUMERIC);
										cs1.setString(20, "INSERT");
										cs1.setString(21, Journalized2);
										cs1.setInt(22, passmonth);
										cs1.setInt(23, passyear);
										

										if (Entry_Date_NT2 != null) {
											cs1.executeQuery();

											int errcode = cs1.getInt(19);
                                            System.out.println("errcode"+errcode);
											if (errcode != 0) {
												con.rollback();
												con.setAutoCommit(true);
												sendMessage(
														response,
														" TWAD Transaction Records Not Updated ~~~~~~! ",
														"ok");
											}
										}
									}
									}

								} catch (Exception e) {
									e.printStackTrace();
									con.rollback();
									con.setAutoCommit(true);
									sendMessage(response,
											"NON TWAD Transaction Records Not Inserted ............ "
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
								System.out.println(e);

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
			String url = "org/Library/jsps/MessengerOkBack.jsp?message=" + msg
					+ "&button=" + bType;
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
