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
 * Servlet implementation class BRS_FollowUp
 */
public class BRS_FollowUp extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BRS_FollowUp() {
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
			long cmbBankAccNo =Long.parseLong(request
					.getParameter("cmbBankAccNo"));

			String RemitanceDate = null;
			String WithdrawlDate = null;
			String EntryDate = null;
			String E_Date = null;
			String Stringdate = null;
			String Stringdate1 = null;
			String Stringdate2 = null;
			String Stringdate3 = null;
			String T_or_NT = null;
			int rsndiff1 = 0;
			String rsndif = null;
			int tnscde1 = 0;
			String tnscde = null;
			try {
			System.out.println("select TWAD_OR_NON_TWAD,REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,ENTRY_FOUND_IN_PASSBOOK,PASSBOOK_DATE,AMOUNT_IN_PASSBOOK,DIFFERENCE,REASON_FOR_DIFFERENCE,FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP,PARTICULARS,DETAILS,TRANSACTION_TYPE,DOC_NO,DOC_TYPE,SL_NO,ENTRY_DATE from FAS_BRS_TRANSACTION where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and ACCOUNT_NO="+cmbBankAccNo+" and FOLLOW_UP_ACTION_REQUIRED='Y' and  ACTION_TAKEN  is null order by REMITTANCE_DATE,DOC_TYPE,DOC_NO");
				ps = connection
						.prepareStatement("select TWAD_OR_NON_TWAD,REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,ENTRY_FOUND_IN_PASSBOOK,PASSBOOK_DATE,AMOUNT_IN_PASSBOOK,DIFFERENCE,REASON_FOR_DIFFERENCE,FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP,PARTICULARS,DETAILS,TRANSACTION_TYPE,DOC_NO,DOC_TYPE,SL_NO,ENTRY_DATE from FAS_BRS_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=? and FOLLOW_UP_ACTION_REQUIRED=? and  ACTION_TAKEN  is null order by REMITTANCE_DATE,DOC_TYPE,DOC_NO");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setInt(3, txtCB_Year);
				ps.setInt(4, txtCB_Month);
				ps.setLong(5, cmbBankAccNo);
				ps.setString(6, "Y");
				results = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (results.next()) {

					Date RemitanceDate1 = results.getDate("REMITTANCE_DATE");
					Date WithdrawlDate1 = results.getDate("WITHDRAWAL_DATE");
					Date EntryDate1 = results.getDate("PASSBOOK_DATE");
					Date E_Date1 = results.getDate("ENTRY_DATE");
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

					String[] ddd = Stringdate.split("-");
					String[] ddd1 = Stringdate1.split("-");
					String[] ddd2 = Stringdate2.split("-");
					String[] ddd3 = Stringdate3.split("-");

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

					T_or_NT = results.getString("TWAD_OR_NON_TWAD");

					if (T_or_NT.equals("T")) {
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
								+ results.getInt("AMOUNT_IN_PASSBOOK")
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
						rsndif = results.getString("REASON_FOR_DIFFERENCE");
						
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

						try {
							ps1 = connection
									.prepareStatement("select REASON_SHORT_DESC from FAS_BRS_REASON_CATALOGUE where REASON_CODE=?");
							ps1.setInt(1, rsndiff1);
							rs = ps1.executeQuery();
							if (rs.next()) {
								xml = xml + "<RsnForDiff>"
										+ rs.getString("REASON_SHORT_DESC")
										+ "</RsnForDiff>";
							} else {
								xml = xml + "<RsnForDiff>null</RsnForDiff>";
							}
						} catch (Exception e) {
							System.out.println(e);
						}
					} else {
						xml = xml + "<PassbookDate1>" + EntryDate
								+ "</PassbookDate1>";
						xml = xml + "<Particulars1>"
								+ results.getString("PARTICULARS")
								+ "</Particulars1>";
						xml = xml + "<Cheqe_or_DDNo1>"
								+ results.getInt("CHEQUE_DD_NO")
								+ "</Cheqe_or_DDNo1>";
						xml = xml + "<Details1>" + results.getString("DETAILS")
								+ "</Details1>";
						xml = xml + "<CRAmount1>"
								+ results.getFloat("CR_AMOUNT")
								+ "</CRAmount1>";
						xml = xml + "<DRAmount1>"
								+ results.getFloat("DR_AMOUNT")
								+ "</DRAmount1>";
						xml = xml
								+ "<FollowUpAction1>"
								+ results
										.getString("FOLLOW_UP_ACTION_REQUIRED")
								+ "</FollowUpAction1>";
						xml = xml
								+ "<Clearance1>"
								+ results
										.getString("CLEARED_BASED_ON_FOLLOWUP")
								+ "</Clearance1>";
						xml = xml + "<doc_no1>" + results.getInt("DOC_NO")
								+ "</doc_no1>";
						xml = xml + "<doc_type1>"
								+ results.getString("DOC_TYPE")
								+ "</doc_type1>";
						xml = xml + "<sl_no1>" + results.getInt("SL_NO")
								+ "</sl_no1>";
						xml = xml + "<E_Date1>" + E_Date + "</E_Date1>";
						tnscde = results.getString("TRANSACTION_TYPE");
						if (tnscde == null) {
							tnscde1 = 0;
						} else {
							tnscde1 = Integer.parseInt(tnscde);
						}

						try {
							ps1 = connection
									.prepareStatement("select TRANS_SHORT_DESC from FAS_BRS_TRANSACTION_TYPE where TRANS_CODE=?");
							ps1.setInt(1, tnscde1);
							rs = ps1.executeQuery();
							if (rs.next()) {
								xml = xml + "<TypeOfTransaction1>"
										+ rs.getString("TRANS_SHORT_DESC")
										+ "</TypeOfTransaction1>";
							} else {
								xml = xml + "<RsnForDiff>null</RsnForDiff>";
							}
						} catch (Exception e) {
							System.out.println(e);
						}
					}
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		}
		else if(strCommand.equalsIgnoreCase("loadStart"))
			{
			xml = xml + "<response><command>loadStart</command>";
			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
		
					
			long cmbBankAccNo =Long.parseLong(request
					.getParameter("cmbBankAccNo"));
			try{
				String Qry="select cashbook_month,cashbook_year from BRS_START_MONTH_AND_YEAR where accounting_unit_id=? and accounting_for_office_id=? and account_no=?";
			System.out.println("Qry >> "+Qry);
				PreparedStatement ps_sttrt=connection.prepareStatement(Qry);
			ps_sttrt.setInt(1, cmbAcc_UnitCode);
			ps_sttrt.setInt(2, cmbOffice_code);
			ps_sttrt.setLong(3, cmbBankAccNo);
			ResultSet rs_sttrt=ps_sttrt.executeQuery();
			int cc=0;
			while(rs_sttrt.next())
			{
				xml = xml + "<cashbook_month>"+rs_sttrt.getInt(1)+"</cashbook_month>";
				xml = xml + "<cashbook_year>"+rs_sttrt.getInt(2)+"</cashbook_year>";
				cc++;
			}if(cc>0){
				xml+="<flag>success</flag>";
			}else{
				xml+="<flag>failure</flag>";
			}
			}catch (Exception e) {
				xml+="<flag>failure</flag>";
                e.printStackTrace();
			}
			//xml = xml + "</response>";
			System.out.println(">>>"+xml);
			}else if(strCommand.equalsIgnoreCase("LoadPassAmt")){
				System.out.println(">>>strCommand "+strCommand);
				xml="<response><command>"+strCommand+"</command>";
				int cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("cmbAcc_UnitCode"));
		
				int cmbOffice_code = Integer.parseInt(request
						.getParameter("cmbOffice_code"));
		
				int txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
		
				int txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
		
				long cmbBankAccNo = Long.parseLong(request
						.getParameter("cmbBankAccNo"));
				System.out.println("cmbBankAccNo-->" + cmbBankAccNo);
		
			
			
			
			try{
			String Qry= "SELECT PASSBOOK_BALANCE AS AMOUNT_IN_PASSBOOK " 
			+" FROM fas_brs_master " 
			+" WHERE accounting_for_office_id=? " 
			+" AND accounting_unit_id        =? " 
			+" AND cashbook_month            =? " 
			+" AND cashbook_year             =?"
			+" And ACCOUNT_NO             =?" ;
			System.out.println("Qry >> "+Qry);
			PreparedStatement ps_pass=connection.prepareStatement(Qry);
			ps_pass.setInt(1, cmbOffice_code);
			ps_pass.setInt(2, cmbAcc_UnitCode);
			ps_pass.setInt(3, txtCB_Month);
			ps_pass.setInt(4, txtCB_Year);
			ps_pass.setLong(5, cmbBankAccNo);
			ResultSet rs_pass=ps_pass.executeQuery();
			if (rs_pass.next()) {
				xml+="<AMOUNT_IN_PASSBOOK>"+rs_pass.getBigDecimal(1)+"</AMOUNT_IN_PASSBOOK><flag>success</flag>";
				
			}else{
				xml+="<flag>failure</flag>";
			}
			
			}catch (Exception e) {
				xml+="<flag>failure</flag>";
			e.printStackTrace();
			}
			
			
				}
			else if (strCommand.equalsIgnoreCase("SaveFunc")) {
			
			xml = xml + "<response><command>SaveFunc</command>";

			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			int txtCB_Year = Integer.parseInt(request
					.getParameter("txtCB_Year"));
			int txtCB_Month = Integer.parseInt(request
					.getParameter("txtCB_Month"));
			long cmbBankAccNo =Long.parseLong(request
					.getParameter("cmbBankAccNo"));

			String[] al = request.getParameter("al").split(",");
			System.out.println("al:->"+al.length);
			String[] al1 = request.getParameter("al1").split(",");
			System.out.println("al1:->"+al1.length);
			int k = 0;
			int doc_no = 0;
			int sl_no = 0;
			String doc_type = null;
			Date e_date = null;
			String t_or_nt1 = null;
			String t_or_nt = null;
			Date act_date = null;
			String act_taken = null;

			int db1=0,db2=0;
			int i = 1, i1 = 0;
			try {
				ps1 = connection
						.prepareStatement("Select max(ACTION_NO) from FAS_BRS_FOLLOWUP");
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
			if(al.length > 1)
			{
			while (k < al.length) {
				String act_date1 = al[k + 1];
				if (act_date1 != null){
					if (act_date1.equals("")){
						act_date = null;
					}else{	
						try{
					java.util.GregorianCalendar c;
					String[] sd = act_date1.split("/");
					c = new java.util.GregorianCalendar(
							Integer.parseInt(sd[2]),
							Integer.parseInt(sd[1]) - 1, Integer
									.parseInt(sd[0]));
					java.util.Date d = c.getTime();
					act_date = new Date(d.getTime());
						}catch (Exception e) {
							e.printStackTrace();
							xml = xml + "<Date>invalid</Date>";
						}
					}
				} else {
					act_date = null;
				}
				act_taken = al[k + 2];

				t_or_nt = al[k];

				String doc_no1 = al[k + 3];
				if (doc_no1 != null) {
					doc_no = Integer.parseInt(doc_no1);
				}

				doc_type = al[k + 4];

				sl_no = Integer.parseInt(al[k + 5]);

				String e_date1 = al[k + 6];
				if (e_date1 != null){
					if (e_date1.equals("")){
						e_date = null;
					}else{
						try{
					java.util.GregorianCalendar c6;
					String[] sd6 = e_date1.split("/");
					c6 = new java.util.GregorianCalendar(Integer
							.parseInt(sd6[2]), Integer.parseInt(sd6[1]) - 1,
							Integer.parseInt(sd6[0]));
					java.util.Date d6 = c6.getTime();
					e_date = new Date(d6.getTime());
						}catch (Exception e) {
							e.printStackTrace();
							xml = xml + "<Date>invalid</Date>";
						}
					}
				} else {
					e_date = null;
				}
				k = k + 7;
				try {			
					ps1 = connection
							.prepareStatement("insert into FAS_BRS_FOLLOWUP(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ENTRY_DATE,SL_NO,TWAD_OR_NON_TWAD,DOC_NO,DOC_TYPE,ACTION_NO,ACTION_DATE,ACTION_TAKEN,STATUS,UPDATED_BY_USER_ID,UPDATED_DATE,ACCOUNT_NO) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					ps1.setInt(1, cmbAcc_UnitCode);
					ps1.setInt(2, cmbOffice_code);
					ps1.setInt(3, txtCB_Year);
					ps1.setInt(4, txtCB_Month);
					ps1.setDate(5, e_date);
					ps1.setInt(6, sl_no);
					ps1.setString(7, t_or_nt);
					ps1.setInt(8, doc_no);
					ps1.setString(9, doc_type);
					ps1.setInt(10, i);
					ps1.setDate(11, act_date);
					ps1.setString(12, act_taken);
					ps1.setString(13, "L");
					ps1.setString(14, userid);
					ps1.setTimestamp(15, ts);
					ps1.setLong(16, cmbBankAccNo);
					db1=ps1.executeUpdate();					
					
					ps = connection.prepareStatement("update fas_brs_transaction set action_taken=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SL_NO=? and TWAD_OR_NON_TWAD=? and DOC_NO=? and DOC_TYPE=?");
					ps.setString(1, act_taken);
					ps.setInt(2, cmbAcc_UnitCode);
					ps.setInt(3, cmbOffice_code);
					ps.setInt(4, txtCB_Year);
					ps.setInt(5, txtCB_Month);
					ps.setInt(6, sl_no);
					ps.setString(7, t_or_nt);
					ps.setInt(8, doc_no);
					ps.setString(9, doc_type);
					ps.executeUpdate();
					xml = xml + "<flag>success</flag>";
				} catch (Exception e) {
					xml = xml + "<flag>failure</flag>";
					e.printStackTrace();
				}
				i=i+1;
			}
			}
			k = 0;
			if(al1.length > 1)
			{
			while (k < al1.length) {
				String act_date1 = al1[k + 1];
				System.out.println("---------------------------->>>"
						+ act_date1);
				if (act_date1 != null){
					if (act_date1.equals("")){
						act_date = null;
					}else{	
						try{
					java.util.GregorianCalendar c;
					String[] sd = act_date1.split("/");
					c = new java.util.GregorianCalendar(
							Integer.parseInt(sd[2]),
							Integer.parseInt(sd[1]) - 1, Integer
									.parseInt(sd[0]));
					java.util.Date d = c.getTime();
					act_date = new Date(d.getTime());
						}catch (Exception e) {
							e.printStackTrace();
							xml = xml + "<Date>invalid</Date>";
						}
					}
				} else {
					act_date = null;
				}
				act_taken = al1[k + 2];

				t_or_nt = al1[k];

				String doc_no1 = al1[k + 3];
				if (doc_no1 != null) {
					doc_no = Integer.parseInt(doc_no1);
				}

				doc_type = al1[k + 4];

				sl_no = Integer.parseInt(al1[k + 5]);

				String e_date1 = al1[k + 6];
				if (e_date1 != null){
					if (e_date1.equals("")){
						e_date = null;
					}else{
						try{
					java.util.GregorianCalendar c6;
					String[] sd6 = e_date1.split("/");
					c6 = new java.util.GregorianCalendar(Integer
							.parseInt(sd6[2]), Integer.parseInt(sd6[1]) - 1,
							Integer.parseInt(sd6[0]));
					java.util.Date d6 = c6.getTime();
					e_date = new Date(d6.getTime());
						}catch (Exception e) {
							e.printStackTrace();
							xml = xml + "<Date>invalid</Date>";
						}
					}
				} else {
					e_date = null;
				}
				k = k + 7;
				try {
					ps1 = connection
							.prepareStatement("insert into FAS_BRS_FOLLOWUP(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ENTRY_DATE,SL_NO,TWAD_OR_NON_TWAD,DOC_NO,DOC_TYPE,ACTION_NO,ACTION_DATE,ACTION_TAKEN,STATUS,UPDATED_BY_USER_ID,UPDATED_DATE,ACCOUNT_NO) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					ps1.setInt(1, cmbAcc_UnitCode);
					ps1.setInt(2, cmbOffice_code);
					ps1.setInt(3, txtCB_Year);
					ps1.setInt(4, txtCB_Month);
					ps1.setDate(5, e_date);
					ps1.setInt(6, sl_no);
					ps1.setString(7, t_or_nt);
					ps1.setInt(8, doc_no);
					ps1.setString(9, doc_type);
					ps1.setInt(10, i);
					ps1.setDate(11, act_date);
					ps1.setString(12, act_taken);
					ps1.setString(13, "L");
					ps1.setString(14, userid);
					ps1.setTimestamp(15, ts);
					ps1.setLong(16, cmbBankAccNo);
					db2=ps1.executeUpdate();
					
					ps = connection.prepareStatement("update fas_brs_transaction set action_taken=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SL_NO=? and TWAD_OR_NON_TWAD=? and DOC_NO=? and DOC_TYPE=?");
					ps.setString(1, act_taken);
					ps.setInt(2, cmbAcc_UnitCode);
					ps.setInt(3, cmbOffice_code);
					ps.setInt(4, txtCB_Year);
					ps.setInt(5, txtCB_Month);
					ps.setInt(6, sl_no);
					ps.setString(7, t_or_nt);
					ps.setInt(8, doc_no);
					ps.setString(9, doc_type);
					ps.executeUpdate();
					xml = xml + "<flag>success</flag>";
				} catch (Exception e) {
					xml = xml + "<flag>failure</flag>";
					e.printStackTrace();
				}
				i=i+1;
			}
			}			
		}
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}

}
