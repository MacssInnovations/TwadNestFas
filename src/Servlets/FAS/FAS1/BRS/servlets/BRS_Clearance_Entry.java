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
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class BRS_Clearance_Entry
 */
public class BRS_Clearance_Entry extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BRS_Clearance_Entry() {
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

		System.out.println("chk 1");
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null;
		ResultSet rs2 = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null,ps21=null;
		CallableStatement cs = null;
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

		if (strCommand.equalsIgnoreCase("ListAll")) {

			xml = xml + "<response><command>ListAll</command>";

			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
			int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
			long cmbBankAccNo =Long.parseLong(request.getParameter("cmbBankAccNo"));
			String indicator=request.getParameter("indicator");
			int Trans_Type_NT0 = Integer.parseInt(request.getParameter("Trans_Type_NT0"));
			System.out.println("Trans_Type_NT0 clearance servlet::"+Trans_Type_NT0);
			
			String RemitanceDate = null;
			String WithdrawlDate = null;
			String EntryDate = null;
			String E_Date = null;
			String doc_date = null;
			String Voucher_date = null;
			String Stringdate = null;
			String Stringdate1 = null;
			String Stringdate2 = null;
			String Stringdate3 = null;
			String Stringdate4 = null;
			String Stringdate5 = null;
			String T_or_NT = null;
			int rsndiff1 = 0;
			String rsndif = null;
			int tnscde1 = 0;
			String tnscde = null;
			try {
				
				String hq = "select * from(select case when cr_amount=0 then 'DR' when dr_amount=0 then 'CR' else '0' end as indic,cashbook_year,cashbook_month,TWAD_OR_NON_TWAD,REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,ENTRY_FOUND_IN_PASSBOOK,PASSBOOK_DATE,AMOUNT_IN_PASSBOOK,DIFFERENCE,REASON_FOR_DIFFERENCE," +
								"FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP,PARTICULARS,DETAILS,TRANSACTION_TYPE,DOC_NO,DOC_TYPE,SL_NO,ENTRY_DATE,DOC_DATE from FAS_BRS_TRANSACTION where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and " +
								" ACCOUNT_NO="+cmbBankAccNo+" and FOLLOW_UP_ACTION_REQUIRED='Y' and (CLEARED_BASED_ON_FOLLOWUP is null or CLEARED_BASED_ON_FOLLOWUP='N') and passbook_date<=(SELECT last_day(to_date(max(date1), 'dd-mm-yy'))ls_date "+
								"	FROM "+
									  " (SELECT DISTINCT ('01' "+
								"   ||'-' "+
								"     ||CASHBOOK_MONTH "+
								"     ||'-' "+
								"     ||CASHBOOK_YEAR)date1 "+
								"   FROM FAS_BRS_TRANSACTION "+
								"   WHERE CASHBOOK_YEAR   = "+txtCB_Year+
								"   AND CASHBOOK_MONTH    <= "+txtCB_Month+
								"   AND account_no        = "+cmbBankAccNo+
								"   AND accounting_unit_id= "+cmbAcc_UnitCode+
								"   ))" +
								"  AND REASON_FOR_DIFFERENCE IN " +
								"  (SELECT TRANS_DESC FROM FAS_BRS_TRANSACTION_TYPE WHERE TRANS_CODE="+Trans_Type_NT0+")" +
								" order by twad_or_non_twad,passbook_date desc)where indic='"+indicator+"'";
				System.out.println("hq::"+hq);
				ps=connection.prepareStatement(hq);
			/*	ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
		
				ps.setLong(3, cmbBankAccNo);
				ps.setString(4, "Y");  */
				results = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (results.next()) {

					Date RemitanceDate1 = results.getDate("REMITTANCE_DATE");
					Date WithdrawlDate1 = results.getDate("WITHDRAWAL_DATE");
					Date EntryDate1 = results.getDate("PASSBOOK_DATE");
					Date E_Date1 = results.getDate("ENTRY_DATE");
					Date doc_date1 = results.getDate("DOC_DATE");
					try {
						Stringdate = RemitanceDate1.toString();
					} catch (Exception e) {
						Stringdate = "0000-00-00";
					}
					try {
						Stringdate1 = WithdrawlDate1.toString();
					} catch (Exception e) {
						Stringdate1 = "0000-00-00";
					}
					try {
						Stringdate2 = EntryDate1.toString();
					} catch (Exception e) {
						Stringdate2 = "0000-00-00";
					}
					try {
						Stringdate3 = E_Date1.toString();
					} catch (Exception e) {
						Stringdate3 = "0000-00-00";
					}
					try {
						Stringdate4 = doc_date1.toString();
					} catch (Exception e) {
						Stringdate4 = "0000-00-00";
					}
					String[] ddd = Stringdate.split("-");
					String[] ddd1 = Stringdate1.split("-");
					String[] ddd2 = Stringdate2.split("-");
					String[] ddd3 = Stringdate3.split("-");
					String[] ddd4 = Stringdate4.split("-");

					int day = Integer.parseInt(ddd[2]);
					int month = Integer.parseInt(ddd[1]);
					int year = Integer.parseInt(ddd[0]);

					int day1 = Integer.parseInt(ddd1[2]);
					int month1 = Integer.parseInt(ddd1[1]);
					int year1 = Integer.parseInt(ddd1[0]);

					int day2 = Integer.parseInt(ddd2[2]);
					int month2 = Integer.parseInt(ddd2[1]);
					int year2 = Integer.parseInt(ddd2[0]);

					int day3 = Integer.parseInt(ddd3[2]);
					int month3 = Integer.parseInt(ddd3[1]);
					int year3 = Integer.parseInt(ddd3[0]);

					int day4 = Integer.parseInt(ddd4[2]);
					int month4 = Integer.parseInt(ddd4[1]);
					int year4 = Integer.parseInt(ddd4[0]);

					if (month >= 10) {
						RemitanceDate = (day + "/" + month + "/" + year);
					} else {
						RemitanceDate = (day + "/0" + month + "/" + year);
					}

					if (month1 >= 10) {
						WithdrawlDate = (day1 + "/" + month1 + "/" + year1);
					} else {
						WithdrawlDate = (day1 + "/0" + month1 + "/" + year1);
					}
					if (month2 >= 10) {
						EntryDate = (day2 + "/" + month2 + "/" + year2);
					} else {
						EntryDate = (day2 + "/0" + month2 + "/" + year2);
					}

					if (month3 >= 10) {
						E_Date = (day3 + "/" + month3 + "/" + year3);
					} else {
						E_Date = (day3 + "/0" + month3 + "/" + year3);
					}
					if (month4 >= 10) {
						doc_date = (day4 + "/" + month4 + "/" + year4);
					} else {
						doc_date = (day4 + "/0" + month4 + "/" + year4);
					}
					T_or_NT = results.getString("TWAD_OR_NON_TWAD");

					if (T_or_NT.equals("T")) {
						xml = xml + "<T_or_NT>T</T_or_NT>";
						xml = xml + "<RemitanceDate>" + RemitanceDate
								+ "</RemitanceDate>";
						xml = xml + "<WithdrawlDate>" + WithdrawlDate
								+ "</WithdrawlDate>";
						xml = xml + "<Voucher_or_ChallanNo>"
								+ results.getInt("VOUCHER_OR_CHALLAN_NO")
								+ "</Voucher_or_ChallanNo>";
						xml = xml + "<Cheqe_or_DDNo>"
								+ results.getInt("CHEQUE_DD_NO")
								+ "</Cheqe_or_DDNo>";
						xml = xml + "<CRAmount>"
								+ results.getFloat("CR_AMOUNT") + "</CRAmount>";
						xml = xml + "<DRAmount>"
								+ results.getFloat("DR_AMOUNT") + "</DRAmount>";
						xml = xml + "<EntryFoundInPassBook>"
								+ results.getString("ENTRY_FOUND_IN_PASSBOOK")
								+ "</EntryFoundInPassBook>";
						xml = xml + "<EntryDate>" + EntryDate + "</EntryDate>";
						xml = xml + "<Amount>"
								+ results.getFloat("AMOUNT_IN_PASSBOOK")
								+ "</Amount>";
						xml = xml + "<Difference>"
								+ results.getFloat("DIFFERENCE")
								+ "</Difference>";
						xml = xml + "<doc_no>" + results.getInt("DOC_NO")
								+ "</doc_no>";
						xml = xml + "<doc_type>"
								+ results.getString("DOC_TYPE") + "</doc_type>";
						xml = xml + "<sl_no>" + results.getInt("SL_NO")
								+ "</sl_no>";
						xml = xml + "<E_Date>" + E_Date + "</E_Date>";
						xml = xml + "<com_doc_date>" + doc_date
								+ "</com_doc_date>";

						xml = xml + "<RsnForDiff>"
								+ results.getString("REASON_FOR_DIFFERENCE")
								+ "</RsnForDiff>";

						xml = xml
								+ "<FollowUpAction>"
								+ results
										.getString("FOLLOW_UP_ACTION_REQUIRED")
								+ "</FollowUpAction>";
						xml = xml
								+ "<Clearance>"
								+ results
										.getString("CLEARED_BASED_ON_FOLLOWUP")
								+ "</Clearance>";
					} else if (T_or_NT.equals("NT")) {
						xml = xml + "<T_or_NT>NT</T_or_NT>";
						xml = xml + "<RemitanceDate>" + EntryDate
								+ "</RemitanceDate>";
						xml = xml + "<WithdrawlDate>" + WithdrawlDate
								+ "</WithdrawlDate>";
						xml = xml + "<Voucher_or_ChallanNo>"
								+ results.getInt("VOUCHER_OR_CHALLAN_NO")
								+ "</Voucher_or_ChallanNo>";
						xml = xml + "<Cheqe_or_DDNo>"
								+ results.getInt("CHEQUE_DD_NO")
								+ "</Cheqe_or_DDNo>";
						xml = xml + "<CRAmount>"
								+ results.getFloat("CR_AMOUNT") + "</CRAmount>";
						xml = xml + "<DRAmount>"
								+ results.getFloat("DR_AMOUNT") + "</DRAmount>";
						xml = xml + "<EntryFoundInPassBook>"
								+ results.getString("ENTRY_FOUND_IN_PASSBOOK")
								+ "</EntryFoundInPassBook>";
						xml = xml + "<EntryDate>" + RemitanceDate
								+ "</EntryDate>";
						xml = xml + "<Amount>"
								+ results.getFloat("AMOUNT_IN_PASSBOOK")
								+ "</Amount>";
						xml = xml + "<Difference>"
								+ results.getFloat("DIFFERENCE")
								+ "</Difference>";
						xml = xml + "<doc_no>" + results.getInt("DOC_NO")
								+ "</doc_no>";
						xml = xml + "<doc_type>"
								+ results.getString("DOC_TYPE") + "</doc_type>";
						xml = xml + "<sl_no>" + results.getInt("SL_NO")
								+ "</sl_no>";
						xml = xml + "<E_Date>" + E_Date + "</E_Date>";
						xml = xml + "<com_doc_date>" + doc_date
								+ "</com_doc_date>";
						xml = xml + "<RsnForDiff>"
								+ results.getString("REASON_FOR_DIFFERENCE")
								+ "</RsnForDiff>";
						xml = xml
								+ "<FollowUpAction>"
								+ results
										.getString("FOLLOW_UP_ACTION_REQUIRED")
								+ "</FollowUpAction>";
						xml = xml
								+ "<Clearance>"
								+ results
										.getString("CLEARED_BASED_ON_FOLLOWUP")
								+ "</Clearance>";
					}
					xml = xml + "<year>" + results.getInt("cashbook_year")+ "</year>";
					xml = xml + "<month>" + results.getInt("cashbook_month")+ "</month>";
				}
			/*	ps1 = connection
						.prepareStatement("select ACCOUNTING_UNIT_ID,AC_HEAD_CODE,acc_unt_id,VOUCHER_NO,SL_NO,"
								+ "CR_DR_INDICATOR,BILL_NO,BILL_TYPE,CHEQUE_OR_DD,CHEQUE_DD_NO,CHEQUE_DD_DATE,"
								+ "AMOUNT,PARTICULARS,VOUCHER_DATE from (SELECT ACCOUNTING_UNIT_ID,AC_HEAD_CODE "
								+ "FROM FAS_OFFICE_BANK_AC_CURRENT WHERE ACCOUNTING_UNIT_ID=? AND BANK_AC_NO=? "
								+ "and module_id=?)x left outer join ( select acc_unt_id,acc_hd_code,"
								+ "ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,"
								+ "CR_DR_INDICATOR,BILL_NO,BILL_TYPE,CHEQUE_OR_DD,CHEQUE_DD_NO,CHEQUE_DD_DATE,"
								+ "AMOUNT,PARTICULARS,VOUCHER_DATE from ( select ACCOUNTING_UNIT_ID as acc_unt_id,"
								+ "ACCOUNT_HEAD_CODE as acc_hd_code,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,"
								+ "CASHBOOK_MONTH,VOUCHER_NO,SL_NO,CR_DR_INDICATOR,BILL_NO,BILL_TYPE,"
								+ "CHEQUE_OR_DD,CHEQUE_DD_NO,CHEQUE_DD_DATE,AMOUNT,PARTICULARS "
								+ "from FAS_JOURNAL_TRANSACTION where ACCOUNTING_UNIT_ID=? and "
								+ "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? )a "
								+ "left outer join ( select VOUCHER_DATE,VOUCHER_NO as v_no "
								+ "from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and "
								+ "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? )b "
								+ "on a.VOUCHER_NO = b.v_no )y on x.ACCOUNTING_UNIT_ID = y.acc_unt_id "
								+ "and x.AC_HEAD_CODE = y.acc_hd_code ");
				ps1.setInt(1, cmbAcc_UnitCode);
				ps1.setInt(2, cmbBankAccNo);
				ps1.setString(3, "MF005");
				ps1.setInt(4, cmbAcc_UnitCode);
				ps1.setInt(5, cmbOffice_code);
				ps1.setInt(6, txtCB_Year);
				ps1.setInt(7, txtCB_Month);
				ps1.setInt(8, cmbAcc_UnitCode);
				ps1.setInt(9, cmbOffice_code);
				ps1.setInt(10, txtCB_Year);
				ps1.setInt(11, txtCB_Month);

				rs = ps1.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (rs.next()) {
					int acc_U_ID = rs.getInt("acc_unt_id");
					if(acc_U_ID!=0)
					{
					System.out.println("acc_U_ID:"+acc_U_ID+":acc_U_ID");
					Date Voucher_date1 = rs.getDate("VOUCHER_DATE");

					try {
						Stringdate5 = Voucher_date1.toString();
					} catch (Exception e) {
						Stringdate5 = "0000-00-00";
					}

					String[] ddd5 = Stringdate5.split("-");

					int day5 = Integer.parseInt(ddd5[2]);
					int month5 = Integer.parseInt(ddd5[1]);
					int year5 = Integer.parseInt(ddd5[0]);

					if (month5 >= 10) {
						Voucher_date = (day5 + "/" + month5 + "/" + year5);
					} else {
						Voucher_date = (day5 + "/0" + month5 + "/" + year5);
					}
					xml = xml + "<T_or_NT>T</T_or_NT>";
					xml = xml + "<RemitanceDate>" + Voucher_date + "</RemitanceDate>";
					xml = xml + "<WithdrawlDate>null</WithdrawlDate>";
					xml = xml + "<Voucher_or_ChallanNo>" + rs.getInt("VOUCHER_NO") + "</Voucher_or_ChallanNo>";
					xml = xml + "<Cheqe_or_DDNo>" + rs.getInt("CHEQUE_DD_NO") + "</Cheqe_or_DDNo>";
					String cr_dr_ind = rs.getString("CR_DR_INDICATOR");
					System.out.println("cr_dr_ind:-----------------------"+cr_dr_ind);
					if (cr_dr_ind.equals("CR")) {
						xml = xml + "<CRAmount>" + rs.getInt("AMOUNT")	+ "</CRAmount>";
						xml = xml + "<DRAmount>0.0</DRAmount>";
					} else {
						xml = xml + "<CRAmount>0.0</CRAmount>";
						xml = xml + "<DRAmount>" + rs.getInt("AMOUNT")	+ "</DRAmount>";
					}

					xml = xml + "<EntryFoundInPassBook>null</EntryFoundInPassBook>";
					xml = xml + "<EntryDate>null</EntryDate>";
					xml = xml + "<Amount>" + rs.getInt("AMOUNT") + "</Amount>";
					xml = xml + "<Difference>0.0</Difference>";
					xml = xml + "<doc_no>" + rs.getInt("VOUCHER_NO") + "</doc_no>";
					xml = xml + "<doc_type>J</doc_type>";
					xml = xml + "<sl_no>null</sl_no>";
					xml = xml + "<E_Date>0/00/0</E_Date>";
					xml = xml + "<com_doc_date>null</com_doc_date>";
					xml = xml + "<RsnForDiff>null</RsnForDiff>";
					xml = xml + "<FollowUpAction>Y</FollowUpAction>";
					xml = xml + "<Clearance>N</Clearance>";
					}
				}  */
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		} else if (strCommand.equalsIgnoreCase("SaveFunc")) {
			Date jour_date=null;
			Date Remitance_Date=null;
			xml = xml + "<response><command>SaveFunc</command>";
			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			int txtCB_Year = Integer.parseInt(request
					.getParameter("txtCB_Year"));
			int txtCB_Month = Integer.parseInt(request
					.getParameter("txtCB_Month"));
			long cmbBankAccNo = Long.parseLong(request
					.getParameter("cmbBankAccNo"));

			//added on 31may2013
			String jrdate=request.getParameter("journal_date");
			int ref_doc_no=Integer.parseInt(request.getParameter("ref_doc_no"));
			String ref_doc_type=request.getParameter("ref_doc_type");
			Float ref_doc_amt=Float.parseFloat(request.getParameter("ref_doc_amt"));
		String Trans_type=request.getParameter("Trans_Type_NT0");
			//String [] Trans_type=request.getParameterValues("Trans_Type_NT0");
			int t_type=Integer.parseInt(Trans_type);
		System.out.println("Trans_type"+Trans_type+"t_type"+t_type);
		
		String Trans_type_New=request.getParameter("Trans_Type_NT_New0");
		int t_type_New=Integer.parseInt(Trans_type_New);
	    System.out.println("Trans_type"+Trans_type+"t_type_New"+t_type_New);
		
	    String Particualrs_NT=request.getParameter("Particualrs_NT");
	    System.out.println("Particualrs_NT"+Particualrs_NT);
	    
	   
	    
			try {
				java.util.GregorianCalendar c;
				String[] sdd = jrdate.split("/");
			Calendar c9 = new java.util.GregorianCalendar(Integer
						.parseInt(sdd[2]), Integer
						.parseInt(sdd[1]) - 1, Integer
						.parseInt(sdd[0]));
				java.util.Date d9 = c9.getTime();
				jour_date = new Date(d9.getTime());
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<Date>invalid</Date>";
			}
			
			String[] al = request.getParameter("al").split(",");
			int db1 = 0, db2 = 0, errcode = 0,db3=0;
			int k = 0;
			int ref_sl_no2 = 0;
			Date r_date2 = null;
			Date w_date2 = null;
			int r_w_no2 = 0;
			int ccdd_no2 = 0;
			float cr_amount2 = 0.0f;
			float dr_amount2 = 0.0f;
			float commamt=0.0f;
			int year_one=0;
			int  month_one=0;
			int db22=0;
			String doc_type2 = null;
			int doc_no2 = 0;
			Date doc_date2 = null;

			String EntryFoundInPassBook2 = null;
			Date Entry_Date2 = null;
			float Amt_in_PassBk2 = 0.0f;
			float Amt_Diff2 = 0.0f;
			String cmbReason4Diff2 = "";
			String FollowUpAction2 = null;
			String ClearanceEntry2 = null;
			String t_or_nt1 = null;
			String t_or_nt = null;

			//added on 31-may-2013
			String nt_trn_type="";
			String refnt_trn_type="";
			
			
			int i = 1, i1 = 0;
			try {
				ps1 = connection
						.prepareStatement("Select max(SL_NO) from FAS_BRS_CLEARANCE");
				results2 = ps1.executeQuery();
				if (results2.next()) {
					i1 = results2.getInt(1);
					i = i + i1;

				} else {
					i = i;
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
			
			int g = 1, g1 = 0;
			try {
				ps1 = connection
						.prepareStatement("Select max(SL_NO) from FAS_BRS_TRANSACTION where TWAD_OR_NON_TWAD='T' ");
				results2 = ps1.executeQuery();
				if (results2.next()) {
					g1 = results2.getInt(1);
					g = g + g1;

				} else {
					g = g;
				}
			} catch (Exception e) {
				e.printStackTrace();

			}
			String t = null;
			int nn = 0;
			int k1 = 0;
			int loop = 0;
			while (k1 < al.length) {
				t = al[k1 + 10];
				if ((t.equals("T")) || (t.equals("NT"))) {
					nn = nn + 1;
				}
				k1 = k1 + 16;
			}
			try {
				connection.setAutoCommit(false);
				while (loop < nn) {
					String r_date1 = al[k];
					if (r_date1 != null) {
						if (r_date1.equals("-")) {
							r_date2 = null;
						} else {
							try {
								java.util.GregorianCalendar c;
								String[] sd = r_date1.split("/");
								c = new java.util.GregorianCalendar(Integer
										.parseInt(sd[2]), Integer
										.parseInt(sd[1]) - 1, Integer
										.parseInt(sd[0]));
								java.util.Date d = c.getTime();
								r_date2 = new Date(d.getTime());
							} catch (Exception e) {
								e.printStackTrace();
								xml = xml + "<Date>invalid</Date>";
							}
						}
					} else {
						r_date2 = null;
					}

					String w_date1 = al[k + 1];
					if (w_date1 != null) {
						if (w_date1.equals("-")) {
							w_date2 = null;
						} else {
							try {
								java.util.GregorianCalendar c;
								String[] sd = w_date1.split("/");
								c = new java.util.GregorianCalendar(Integer
										.parseInt(sd[2]), Integer
										.parseInt(sd[1]) - 1, Integer
										.parseInt(sd[0]));
								java.util.Date d = c.getTime();
								w_date2 = new Date(d.getTime());
							} catch (Exception e) {
								e.printStackTrace();
								xml = xml + "<Date>invalid</Date>";
							}
						}
					} else {
						w_date2 = null;
					}

					
					String rem_date = al[k];
					String with_date = al[k + 1];
					if (rem_date.equals("-")) {
						String[] sd = with_date.split("/");
						year_one=Integer.parseInt(sd[2]);
						month_one=(Integer.parseInt(sd[1])-1);
					
					}
					else
					{
						String[] sd1 = rem_date.split("/");
						year_one=Integer.parseInt(sd1[2]);
						month_one=(Integer.parseInt(sd1[1]));	
					}
					
					String r_w_no1 = al[k + 2];
					if (r_w_no1 != null) {
						r_w_no2 = Integer.parseInt(r_w_no1);
					}

					String ccdd_no1 = al[k + 3];
					if (ccdd_no1 != null) {
						ccdd_no2 = Integer.parseInt(ccdd_no1);
					}

					String cr_amount1 = al[k + 4];
					if (cr_amount1 != null) {
						cr_amount2 = Float.parseFloat(cr_amount1);
					}

					String dr_amount1 = al[k + 5];
					if (dr_amount1 != null) {
						dr_amount2 = Float.parseFloat(dr_amount1);
					}

					String Entry_Date1 = al[k + 6];
					if (Entry_Date1 != null) {
						if (Entry_Date1.equals("")) {
							Entry_Date2 = null;
						} else {
							try {
								java.util.GregorianCalendar c6;
								String[] sd6 = Entry_Date1.split("/");
								c6 = new java.util.GregorianCalendar(Integer
										.parseInt(sd6[2]), Integer
										.parseInt(sd6[1]) - 1, Integer
										.parseInt(sd6[0]));
								java.util.Date d6 = c6.getTime();
								Entry_Date2 = new Date(d6.getTime());
							} catch (Exception e) {
								e.printStackTrace();
								xml = xml + "<Date>invalid</Date>";
							}
						}
					} else {
						Entry_Date2 = null;
					}

					String Amt_in_PassBk = al[k + 7];
					if (Amt_in_PassBk != null) {
						Amt_in_PassBk2 = Float.parseFloat(Amt_in_PassBk);
					}

					String Amt_Diff1 = al[k + 8];
					if (Amt_Diff1.equals("0.0")) {
						Amt_Diff2 = 0.0f;
					} else {
						Amt_Diff2 = Float.parseFloat(Amt_Diff1);
					}

					String cmbReason4Diff1 = al[k + 9];
					if (cmbReason4Diff1.equals("-")) {
						cmbReason4Diff2 = "";
					} else {
						cmbReason4Diff2 = cmbReason4Diff1;
					}

					t_or_nt = al[k + 10];

					String doc_no1 = al[k + 11];
					if (doc_no1 != null) {
						doc_no2 = Integer.parseInt(doc_no1);
					}

					doc_type2 = al[k + 12];

				/*	String ref_sl_no1 = al[k + 13];
					if (ref_sl_no1 != null) {
						ref_sl_no2 = Integer.parseInt(ref_sl_no1);
					}
*/
					String doc_date1 = al[k + 14];
					if (doc_date1 != null) {
						if (doc_date1.equals("")) {
							doc_date2 = null;
						} else {
							try {
								java.util.GregorianCalendar c;
								String[] sd = doc_date1.split("/");
								c = new java.util.GregorianCalendar(Integer
										.parseInt(sd[2]), Integer
										.parseInt(sd[1]) - 1, Integer
										.parseInt(sd[0]));
								java.util.Date d = c.getTime();
								doc_date2 = new Date(d.getTime());
							} catch (Exception e) {
								e.printStackTrace();
								xml = xml + "<Date>invalid</Date>";
							}
						}
					} else {
						doc_date2 = null;
					}
					String year_month = al[k + 15];
					String[] yrmn = year_month.split("-");
					int year_nt_brs = Integer.parseInt(yrmn[0]);
					int month_nt_brs = Integer.parseInt(yrmn[1]);
					k = k + 16;
/*					String r_date1 = al[k];
					if (r_date1 != null) {
						if (r_date1.equals("-")) {
							r_date2 = null;
						} else if (r_date1.length()==10) {
							try {
								java.util.GregorianCalendar c;
								System.out.println("r_date1"+r_date1);
								String[] sd = (r_date1.trim()).split("/");
								c = new java.util.GregorianCalendar(Integer.parseInt(sd[2]), (Integer.parseInt(sd[1]) - 1), Integer.parseInt(sd[0]));
								java.util.Date d = c.getTime();
								r_date2 = new Date(d.getTime());
								
								System.out.println("Integer.parseInt(sd[2])"+Integer.parseInt(sd[2])+",Integer.parseInt(sd[1]) - 1"+(Integer.parseInt(sd[1]) - 1)+",Integer.parseInt(sd[0])"+Integer.parseInt(sd[0]));
								System.out.println("r_date2"+r_date2);
							} catch (Exception e) {
								e.printStackTrace();
								xml = xml + "<Date>invalid</Date>";
							}
						}else  {
							r_date2 = null;
						}
					} else {
						r_date2 = null;
					}

					String w_date1 = al[k + 1];
					if (w_date1 != null) {
						if (w_date1.equals("-")) {
							w_date2 = null;
						} else {
							try {
								java.util.GregorianCalendar c;
								String[] sd = w_date1.split("/");
								c = new java.util.GregorianCalendar(Integer
										.parseInt(sd[2]), Integer
										.parseInt(sd[1]) - 1, Integer
										.parseInt(sd[0]));
								java.util.Date d = c.getTime();
								w_date2 = new Date(d.getTime());
							} catch (Exception e) {
								e.printStackTrace();
								xml = xml + "<Date>invalid</Date>";
							}
						}
					} else {
						w_date2 = null;
					}

					
					String rem_date = al[k];
					String with_date = al[k + 1];
					if (rem_date.equals("-")) {
						String[] sd = with_date.split("/");
						year_one=Integer.parseInt(sd[2]);
						month_one=(Integer.parseInt(sd[1])-1);
					
					}
					else if (rem_date.length()==10)
					{
						
						System.out.println("rem_date"+rem_date);
						String[] sd1 = rem_date.split("/");
						year_one=Integer.parseInt(sd1[2]);
						month_one=(Integer.parseInt(sd1[1]));	
						System.out.println("year_one"+year_one+"month_one"+month_one);
					}
					else{
						System.out.println("rem_date"+rem_date);
						String[] sd1 = rem_date.split("-");
						year_one=Integer.parseInt(sd1[1]);
						month_one=(Integer.parseInt(sd1[0]));	
						System.out.println("year_one"+year_one+"month_one"+month_one);
					}
					
					String r_w_no1 = al[k + 2];
					if (r_w_no1.equals("-")) {
						r_w_no2=0;
					}else if (r_w_no1 != null) {
						r_w_no2 = Integer.parseInt(r_w_no1);
					}
					
					String ccdd_no1 = al[k + 3];
					if (ccdd_no1 != null) {
						ccdd_no2 = Integer.parseInt(ccdd_no1);
					}

					String cr_amount1 = al[k + 4];
					if (cr_amount1 != null) {
						cr_amount2 = Float.parseFloat(cr_amount1);
					}

					String dr_amount1 = al[k + 5];
					if (dr_amount1 != null) {
						dr_amount2 = Float.parseFloat(dr_amount1);
					}

					String Entry_Date1 = al[k + 6];
					if (Entry_Date1 != null) {
						if (Entry_Date1.equals("")) {
							Entry_Date2 = null;
						} else {
							try {
								java.util.GregorianCalendar c6;
								String[] sd6 = Entry_Date1.split("/");
								c6 = new java.util.GregorianCalendar(Integer
										.parseInt(sd6[2]), Integer
										.parseInt(sd6[1]) - 1, Integer
										.parseInt(sd6[0]));
								java.util.Date d6 = c6.getTime();
								Entry_Date2 = new Date(d6.getTime());
							} catch (Exception e) {
								e.printStackTrace();
								xml = xml + "<Date>invalid</Date>";
							}
						}
					} else {
						Entry_Date2 = null;
					}

					String Amt_in_PassBk = al[k + 7];
					if (Amt_in_PassBk != null) {
				    Amt_in_PassBk2 = Float.parseFloat(Amt_in_PassBk);
					}

					String Amt_Diff1 = al[k + 8];
					if (Amt_Diff1.equals("0.0")) {
						Amt_Diff2 = 0.0f;
					} else {
						Amt_Diff2 = Float.parseFloat(Amt_Diff1);
					}

					String cmbReason4Diff1 = al[k + 9];
					if (cmbReason4Diff1.equals("-")) {
						cmbReason4Diff2 = "";
					} else {
						cmbReason4Diff2 = cmbReason4Diff1;
					}

					t_or_nt = al[k + 10];

					String doc_no1 = al[k + 11];
					if (doc_no1 != null) {
						doc_no2 = Integer.parseInt(doc_no1);
					}

					doc_type2 = al[k + 12];

				String ref_sl_no1 = al[k + 13];
					if (ref_sl_no1 != null) {
						ref_sl_no2 = Integer.parseInt(ref_sl_no1);
					}

					String doc_date1 = al[k + 14];
					if (doc_date1 != null) {
						if (doc_date1.equals("")) {
							doc_date2 = null;
						} else {
							try {
								System.out.println("doc_date1"+doc_date1);
								java.util.GregorianCalendar c;
								String[] sd = doc_date1.split("/");
								c = new java.util.GregorianCalendar(Integer
										.parseInt(sd[2]), Integer
										.parseInt(sd[1]) - 1, Integer
										.parseInt(sd[0]));
								java.util.Date d = c.getTime();
								doc_date2 = new Date(d.getTime());
							} catch (Exception e) {
								e.printStackTrace();
								xml = xml + "<Date>invalid</Date>";
							}
						}
					} else {
						doc_date2 = null;
					}
					String year_month = al[k + 15];
					String[] yrmn = year_month.split("-");
					int year_nt_brs = Integer.parseInt(yrmn[0]);
					int month_nt_brs = Integer.parseInt(yrmn[1]);
					k = k + 15;*/
					
					String Trans_desc="";
					String Trans_desc_old="";
					 ps21=connection.prepareStatement("SELECT TRANS_DESC FROM FAS_BRS_TRANSACTION_TYPE WHERE TRANS_CODE="+t_type_New);
					 ResultSet rs21 = ps21.executeQuery();
					 if(rs21.next())
					 {
						 Trans_desc=rs21.getString("TRANS_DESC");
					 }
					 
					 ps21=connection.prepareStatement("SELECT TRANS_DESC FROM FAS_BRS_TRANSACTION_TYPE WHERE TRANS_CODE="+t_type);
					 ResultSet rs22 = ps21.executeQuery();
					 if(rs21.next())
					 {
						 Trans_desc_old=rs21.getString("TRANS_DESC");
					 }
					 
					System.out.println("Trans_desc===>"+Trans_desc);
					
					try {						
						ps1 = connection
								.prepareStatement("insert into FAS_BRS_CLEARANCE(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,CLEARED_DATE,SL_NO,TWAD_OR_NON_TWAD,DOC_NO,DOC_TYPE,REF_SL_NO,REF_DOC_NO,REF_DOC_TYPE,REF_AMOUNT,STATUS,ACCOUNT_NO,UPDATED_BY_USER_ID,UPDATED_DATE,NT_TRN_TYPE,PASSBOOK_DATE,REF_DOC_DATE,REF_NT_TRN_TYPE,CR_AMOUNT,DR_AMOUNT,REF_TWAD_OR_NON_TWAD,DOC_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						ps1.setInt(1, cmbAcc_UnitCode);
						ps1.setInt(2, cmbOffice_code);
						ps1.setInt(3, txtCB_Year);
						ps1.setInt(4, txtCB_Month);
						ps1.setDate(5,jour_date);
						ps1.setInt(6, i);
						ps1.setString(7, t_or_nt);
						ps1.setInt(8, doc_no2);
						ps1.setString(9, doc_type2);
						ps1.setInt(10, ref_sl_no2);
						ps1.setInt(11,ref_doc_no);//ref_doc_no
						ps1.setString(12,ref_doc_type);
						ps1.setFloat(13, ref_doc_amt);
						ps1.setString(14, "L");
						ps1.setLong(15, cmbBankAccNo);
						ps1.setString(16, userid);
						ps1.setTimestamp(17, ts);
						ps1.setInt(18, t_type);
						ps1.setDate(19, jour_date);//passbookdate
						ps1.setDate(20, jour_date);//ref_doc_date
						ps1.setInt(21, t_type_New);
						ps1.setFloat(22, cr_amount2);
						ps1.setFloat(23,dr_amount2);
						//ps1.setString(24, "T");'
						ps1.setString(24, t_or_nt);
						if(t_or_nt.equalsIgnoreCase("NT"))
						{
						ps1.setDate(25, r_date2);
						}
						else
						{
							ps1.setDate(25, jour_date);	
						}
						
						db1 = ps1.executeUpdate();
					} catch (Exception e) {
						System.out.println("try-catch-1");
						xml = xml + "<flag>failure</flag>";
						e.printStackTrace();
					}
					i = i + 1;
					loop = loop + 1;

					try {
						if(cr_amount2!=0)
						{
							commamt=cr_amount2;
						}
						else
						{
							commamt=dr_amount2;
						}
						
						
						
						//System.out.println("month_one::"+month_one+":::year_one::;"+year_one);
						String brs_q="";
						
						if (dr_amount2==0) {
							 brs_q="SELECT * FROM FAS_BRS_TRANSACTION WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+" AND ACCOUNTING_FOR_OFFICE_ID= "+cmbOffice_code+" AND CASHBOOK_YEAR           ="+year_nt_brs+" AND CASHBOOK_MONTH          =  " +month_nt_brs+
									"and TWAD_OR_NON_TWAD='"+t_or_nt+"' AND DOC_NO                  ='"+doc_no2+"' AND DOC_TYPE                ='"+doc_type2+"' AND ACCOUNT_NO              ="+cmbBankAccNo+" and DR_AMOUNT="+commamt;
							System.out.println("brs_q::CR::"+brs_q);
						} else {
							 brs_q="SELECT * FROM FAS_BRS_TRANSACTION WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+" AND ACCOUNTING_FOR_OFFICE_ID= "+cmbOffice_code+" AND CASHBOOK_YEAR           ="+year_nt_brs+" AND CASHBOOK_MONTH          =  " +month_nt_brs+
									"and TWAD_OR_NON_TWAD='"+t_or_nt+"' AND DOC_NO                  ='"+doc_no2+"' AND DOC_TYPE                ='"+doc_type2+"' AND ACCOUNT_NO              ="+cmbBankAccNo+" and CR_AMOUNT="+commamt;
							System.out.println("brs_q::DR::"+brs_q);
						}
						/*String brs_q="SELECT * FROM FAS_BRS_TRANSACTION WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+" AND ACCOUNTING_FOR_OFFICE_ID= "+cmbOffice_code+" AND CASHBOOK_YEAR           ="+year_nt_brs+" AND CASHBOOK_MONTH          =  " +month_nt_brs+
								"and TWAD_OR_NON_TWAD='"+t_or_nt+"' AND DOC_NO                  ='"+doc_no2+"' AND DOC_TYPE                ='"+doc_type2+"' AND ACCOUNT_NO              ="+cmbBankAccNo+" and (CR_AMOUNT="+commamt+" or DR_AMOUNT="+commamt+")";
						System.out.println("brs_q::::"+brs_q);*/
						ps2 = connection.prepareStatement(brs_q);	
						ResultSet rs5 = ps2.executeQuery();
						if(rs5.next()) {  
							ps2 = connection.prepareStatement("update FAS_BRS_TRANSACTION set FOLLOW_UP_ACTION_REQUIRED=?,CLEARED_BASED_ON_FOLLOWUP=?,IS_IT_CLEARING_ENTRY=?,DOC_DATE=?,DOC_NO=?,JOURNALIZED=?,CLEARENCE_REF_TYPE=?,CLEARENCE_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TWAD_OR_NON_TWAD=? and DOC_NO=? and DOC_TYPE=? and ACCOUNT_NO=? and CR_AMOUNT=? and DR_AMOUNT=?");

							ps2.setString(1, "Y");
							ps2.setString(2, "Y");
							ps2.setString(3, "");
							ps2.setDate(4,jour_date);//DOC_DATE
							ps2.setInt(5, ref_doc_no);//DOC_NO
							ps2.setString(6, "Y");//journalised
							ps2.setString(7, ref_doc_type);//CLEARENCE_REF_TYPE
							ps2.setDate(8,jour_date);//CLEARENCE_DATE
							
							ps2.setInt(9, cmbAcc_UnitCode);
							ps2.setInt(10, cmbOffice_code);
							ps2.setInt(11, year_nt_brs);
							ps2.setInt(12, month_nt_brs);
							ps2.setString(13, t_or_nt);
							ps2.setInt(14, doc_no2);
							ps2.setString(15, doc_type2);
							ps2.setLong(16, cmbBankAccNo);
							ps2.setFloat(17,rs5.getFloat("CR_AMOUNT"));
							ps2.setFloat(18,rs5.getFloat("DR_AMOUNT"));
							db2 = ps2.executeUpdate();
							System.out.println("db2:::"+db2);
						 }
						
//						else
//						{
//						/*ps2 = connection.prepareStatement("insert into FAS_BRS_TRANSACTION(ACCOUNTING_UNIT_ID," +
//										"ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ACCOUNT_NO," +
//										"TWAD_OR_NON_TWAD,SL_NO,ENTRY_DATE,REMITTANCE_DATE,WITHDRAWAL_DATE," +
//										"VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT," +
//										"AMOUNT_IN_PASSBOOK,DIFFERENCE,REASON_FOR_DIFFERENCE," +
//										"FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP,DOC_TYPE," +
//										"DOC_NO,DOC_DATE,UPDATED_BY_USER_ID,UPDATED_DATE) " +
//										"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
//
//						ps2.setInt(1, cmbAcc_UnitCode);
//						ps2.setInt(2, cmbOffice_code);
//						ps2.setInt(3, year_nt_brs);
//						ps2.setInt(4, month_nt_brs);
//						ps2.setLong(5, cmbBankAccNo);
//						ps2.setString(6, "T");
//						ps2.setInt(7, g);
//						ps2.setTimestamp(8, ts);							
//						ps2.setDate(9, r_date2);
//						ps2.setDate(10, w_date2);
//						ps2.setInt(11, r_w_no2);
//						ps2.setInt(12, ccdd_no2);
//						ps2.setFloat(13, cr_amount2);
//						ps2.setFloat(14, dr_amount2);
//						ps2.setFloat(15, Amt_in_PassBk2);
//						ps2.setInt(16, 0);
//						ps2.setString(17, "");
//						ps2.setString(18, "N");
//						ps2.setString(19, "Y");
//						ps2.setString(20, doc_type2);
//						ps2.setInt(21, doc_no2);
//						ps2.setDate(22, doc_date2);													
//						ps2.setString(23, userid);
//						ps2.setTimestamp(24, ts);
//						
//						*/
//							 
//							 
							 if (cr_amount2==0) {
//								 ps2 = connection.prepareStatement("insert into FAS_BRS_TRANSACTION(ACCOUNTING_UNIT_ID," +
//											"ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ACCOUNT_NO," +
//											"TWAD_OR_NON_TWAD,SL_NO,ENTRY_DATE,REMITTANCE_DATE,WITHDRAWAL_DATE," +
//											"VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT," +
//											"AMOUNT_IN_PASSBOOK,PASSBOOK_DATE,REASON_FOR_DIFFERENCE "+
//											"FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP,DOC_TYPE," +
//											"DOC_NO,DOC_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,IS_IT_CLEARING_ENTRY,CLEARENCE_REF_TYPE,CLEARENCE_DATE,PASS_BOOK_YEAR,PASS_BOOK_MONTH,TRANSACTION_TYPE,PARTICULARS) " +
//											"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
//                                  
//								 
//								 
//								 
//								    ps2.setInt(1, cmbAcc_UnitCode);
//									ps2.setInt(2, cmbOffice_code);
//									ps2.setInt(3, txtCB_Year);
//									ps2.setInt(4, txtCB_Month);
//									//ps2.setInt(4, month_nt_brs);
//									ps2.setLong(5, cmbBankAccNo);
//									ps2.setString(6, "NT");
//									ps2.setInt(7, g);
//									ps2.setTimestamp(8, ts);							
//									ps2.setDate(9, jour_date);
//									ps2.setDate(10, null);
//									ps2.setInt(11, r_w_no2);
//									ps2.setInt(12, ccdd_no2);									
//									ps2.setFloat(13, dr_amount2);
//									ps2.setFloat(14, 0);	
//									ps2.setFloat(15, Amt_in_PassBk2);
//									ps2.setDate(16, jour_date);
//									ps2.setString(17, Trans_desc);
//									ps2.setString(18, "N");
//									ps2.setString(19, "Y");
//									ps2.setString(20, ref_doc_type);
//									ps2.setInt(21, ref_doc_no);
//									ps2.setDate(22, jour_date);													
//									ps2.setString(23, userid);
//									ps2.setTimestamp(24, ts);
//									ps2.setString(25, "Y");
//									ps2.setString(26, ref_doc_type);
//									ps2.setDate(27, jour_date);
//									ps2.setInt(28, txtCB_Year);
//									ps2.setInt(29, txtCB_Month);
//									ps2.setInt(30, t_type_New);
//									ps2.setString(31, Particualrs_NT);
//									db2 = ps2.executeUpdate();
//									System.out.println("CR_AMT ==0 "+db2);
//									if(db2>0)
//									{
										ps2 = connection.prepareStatement("update FAS_BRS_TRANSACTION set FOLLOW_UP_ACTION_REQUIRED=?,CLEARED_BASED_ON_FOLLOWUP=?,IS_IT_CLEARING_ENTRY=?,CLEARENCE_REF_TYPE=?,CLEARENCE_DATE=? ,DOC_DATE=?,DOC_NO=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TWAD_OR_NON_TWAD=? and DOC_NO=? and DOC_TYPE=? and ACCOUNT_NO=? and DR_AMOUNT=?");
	
										ps2.setString(1, "Y");
										ps2.setString(2, "Y");
										ps2.setString(3, "");
										ps2.setString(4, ref_doc_type);
										ps2.setDate(5,jour_date);
										ps2.setDate(6,jour_date);//DOC_DATE
										ps2.setInt(7, ref_doc_no);
										ps2.setInt(8, cmbAcc_UnitCode);
										ps2.setInt(9, cmbOffice_code);
										ps2.setInt(10, year_nt_brs);
										ps2.setInt(11, month_nt_brs);
										ps2.setString(12, t_or_nt);
										ps2.setInt(13, doc_no2);
										ps2.setString(14, doc_type2);
										ps2.setLong(15, cmbBankAccNo);
										ps2.setFloat(16, dr_amount2);
										
										 db22 = ps2.executeUpdate();
//									}
//									
//								 
//								
							} else {
//								 ps2 = connection.prepareStatement("insert into FAS_BRS_TRANSACTION(ACCOUNTING_UNIT_ID," +
//											"ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ACCOUNT_NO," +
//											"TWAD_OR_NON_TWAD,SL_NO,ENTRY_DATE,REMITTANCE_DATE,WITHDRAWAL_DATE," +
//											"VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,DR_AMOUNT,CR_AMOUNT," +
//											"AMOUNT_IN_PASSBOOK,PASSBOOK_DATE,REASON_FOR_DIFFERENCE," +
//											"FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP,DOC_TYPE," +
//											"DOC_NO,DOC_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,IS_IT_CLEARING_ENTRY,CLEARENCE_REF_TYPE,CLEARENCE_DATE,PASS_BOOK_YEAR,PASS_BOOK_MONTH,TRANSACTION_TYPE,PARTICULARS) " +
//											"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
//                               
//								    ps2.setInt(1, cmbAcc_UnitCode);
//									ps2.setInt(2, cmbOffice_code);
//									ps2.setInt(3, txtCB_Year);
//									ps2.setInt(4, txtCB_Month);									
//									ps2.setLong(5, cmbBankAccNo);
//									ps2.setString(6, "NT");									
//									ps2.setInt(7, g);
//									ps2.setTimestamp(8, ts);							
//									ps2.setDate(9, jour_date);
//									ps2.setDate(10, null);
//									ps2.setInt(11, r_w_no2);
//									ps2.setInt(12, ccdd_no2);
//									ps2.setFloat(13, cr_amount2);
//									ps2.setFloat(14, 0);
//									ps2.setFloat(15, Amt_in_PassBk2);
//									ps2.setDate(16, jour_date);
//									ps2.setString(17, Trans_desc);
//									ps2.setString(18, "N");
//									ps2.setString(19, "Y");
//									ps2.setString(20, ref_doc_type);
//									ps2.setInt(21, ref_doc_no);
//									ps2.setDate(22, jour_date);													
//									ps2.setString(23, userid);
//									ps2.setTimestamp(24, ts);
//									ps2.setString(25, "Y");
//									ps2.setString(26, ref_doc_type);
//									ps2.setDate(27, jour_date);
//									ps2.setInt(28, txtCB_Year);
//									ps2.setInt(29, txtCB_Month);
//									ps2.setInt(30, t_type_New);
//									ps2.setString(31, Particualrs_NT);
//									db2 = ps2.executeUpdate();
//									
//									System.out.println("DR_AMT ==0 "+db2);
//									if(db2>0)
//									{
										ps2 = connection.prepareStatement("update FAS_BRS_TRANSACTION set FOLLOW_UP_ACTION_REQUIRED=?,CLEARED_BASED_ON_FOLLOWUP=?,IS_IT_CLEARING_ENTRY=?,CLEARENCE_REF_TYPE=?,CLEARENCE_DATE=? ,DOC_DATE=?,DOC_NO=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TWAD_OR_NON_TWAD=? and DOC_NO=? and DOC_TYPE=? and ACCOUNT_NO=? and CR_AMOUNT=?");
	
										ps2.setString(1, "Y");
										ps2.setString(2, "Y");
										ps2.setString(3, "");
										ps2.setString(4, ref_doc_type);
										ps2.setDate(5,jour_date);
										ps2.setDate(6,jour_date);//DOC_DATE
										ps2.setInt(7, ref_doc_no);
										ps2.setInt(8, cmbAcc_UnitCode);
										ps2.setInt(9, cmbOffice_code);
										ps2.setInt(10, year_nt_brs);
										ps2.setInt(11, month_nt_brs);
										ps2.setString(12, t_or_nt);
										ps2.setInt(13, doc_no2);
										ps2.setString(14, doc_type2);
										ps2.setLong(15, cmbBankAccNo);
										ps2.setFloat(16, cr_amount2);
										
										 db22 = ps2.executeUpdate();
//									}
//									
//									
//
							}
//							 
//							 /*ps2 = connection.prepareStatement("insert into FAS_BRS_TRANSACTION(ACCOUNTING_UNIT_ID," +
//										"ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ACCOUNT_NO," +
//										"TWAD_OR_NON_TWAD,SL_NO,ENTRY_DATE,REMITTANCE_DATE,WITHDRAWAL_DATE," +
//										"VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT," +
//										"AMOUNT_IN_PASSBOOK,DIFFERENCE,REASON_FOR_DIFFERENCE," +
//										"FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP,DOC_TYPE," +
//										"DOC_NO,DOC_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,IS_IT_CLEARING_ENTRY) " +
//										"values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?) ");
//
//						ps2.setInt(1, cmbAcc_UnitCode);
//						ps2.setInt(2, cmbOffice_code);
//						ps2.setInt(3, year_nt_brs);
//						ps2.setInt(4, month_nt_brs);
//						ps2.setLong(5, cmbBankAccNo);
//						ps2.setString(6, "T");
//						ps2.setInt(7, g);
//						ps2.setTimestamp(8, ts);							
//						ps2.setDate(9, r_date2);
//						ps2.setDate(10, w_date2);
//						ps2.setInt(11, r_w_no2);
//						ps2.setInt(12, ccdd_no2);
//						ps2.setFloat(13, cr_amount2);
//						ps2.setFloat(14, dr_amount2);
//						ps2.setFloat(15, Amt_in_PassBk2);
//						ps2.setInt(16, 0);
//						ps2.setString(17, "");
//						ps2.setString(18, "N");
//						ps2.setString(19, "Y");
//						ps2.setString(20, ref_doc_type);
//						ps2.setInt(21, ref_doc_no);
//						ps2.setDate(22, jour_date);													
//						ps2.setString(23, userid);
//						ps2.setTimestamp(24, ts);
//						ps2.setString(25, "Y");
//						*/
//						
//						
//					System.out.println("1"+cmbAcc_UnitCode+"2"+cmbOffice_code+"3"+year_nt_brs+"4"+month_nt_brs+"5"+cmbBankAccNo+"7"+g+"8"+ts
//							+"9"+r_date2+"10"+w_date2+"11"+r_w_no2+"12"+ccdd_no2+"13"+cr_amount2+"14"+dr_amount2+"15"+Amt_in_PassBk2+"20"+doc_type2+"21"+doc_no2+"22"+doc_date2+"23"+userid);
//						
//						
//						}  
					} catch (Exception e) {
						// TODO: handle exception
						System.out.println("try-catch-2");
						xml = xml + "<flag>failure</flag>";
						e.printStackTrace();
					}  

					if ((db1 > 0) && (db22 > 0)) {
						connection.commit();
						
						xml = xml + "<flag>success</flag>";
					} else {
						System.out.println("db1-db2");
						connection.rollback();
						//connection.commit();
						xml = xml + "<flag>failure</flag>";
					}

				}
			} catch (Exception e) {
				// TODO: handle exception
				System.out.println("try-catch-4");
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		}
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}
}