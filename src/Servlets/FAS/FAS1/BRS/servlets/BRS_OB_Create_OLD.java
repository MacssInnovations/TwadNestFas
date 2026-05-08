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
 * Servlet implementation class BRS_OB_Create
 */
public class BRS_OB_Create_OLD extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public BRS_OB_Create_OLD() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
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
		Connection connection = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null;
		PreparedStatement ps5 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		ResultSet rs5 = null;
		ResultSet rs6 = null;

		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

		String strCommand = "";

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

		/**
		 * Get Command Parameter
		 */
		try {
			strCommand = request.getParameter("Command");
			System.out.println("assign..here command..." + strCommand);
		} catch (Exception e) {
			System.out.println("Exception in assigning..." + e);
		}
		/**
		 * Variables Declaration
		 */
		int cmbAcc_UnitCode = 0;
		int cmbOffice_code = 0;
		int txtCB_Year = 0;
		int txtCB_Month = 0;
		long cmbBankAccNo = 0;
		String Filter = "";

		/**
		 * Get Parameters
		 */

		/** Get Accounting Unit ID */
		try {
			cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
		} catch (Exception e) {
			System.out.println("Exception to Read Reason Code ");
		}

		/** Get Accouting for office id */
		try {
			cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
		} catch (Exception e) {
			System.out.println("Exception to Read Reason short Description");
		}

		/** Get Cashbook Year */
		try {
			txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));
		} catch (Exception e) {
			System.out.println("Exception to Read Cashbook Year ");
		}

		/** Get Cashbook Month */
		try {
			txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));
		} catch (Exception e) {
			System.out.println("Exception to Read Cashbook Month ");
		}

		/** Get Bank Account Number */
		try {
			cmbBankAccNo = Long.parseLong(request.getParameter("cmbBankAccNo"));			
		} catch (Exception e) {
			System.out.println("Error Not Getting Bank Account Number -->" + e);
		}

		String r_date = null;
		String w_date = null;
		String EntryDate = null;
		String Doc_Date = null;
		String Stringdate = null;
		String Stringdate1 = null;
		String Stringdate2 = null;
		String Stringdate3 = null;
		String T_or_NT = null;
		int tnscde1 = 0;
		String tnscde = null;
		String xml = "";
		if (strCommand.equalsIgnoreCase("LoadNONTWADTransactions")) {

			String CONTENT_TYPE = "text/xml; charset=windows-12" + txtCB_Month
					+ "2";
			response.setContentType(CONTENT_TYPE);
			
			xml = "<response><command>LoadNONTWADTransactions</command>";
			try {

				String sql3 = "SELECT trans_code,  			"
						+ "  trans_short_desc 		"
						+ "FROM fas_brs_transaction_type 	";

				ps3 = con.prepareStatement(sql3);
				rs3 = ps3.executeQuery();

				while (rs3.next()) {
					xml = xml + "<trans_pair>";
					xml = xml + "<trans_code>" + rs3.getString("trans_code")
							+ "</trans_code>";
					xml = xml + "<trans_desc>"
							+ rs3.getString("trans_short_desc")
							+ "</trans_desc>";
					xml = xml + "</trans_pair>";
				}
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				System.out.println(e);
				e.printStackTrace();
			}

			xml = xml + "</response>";
			System.out.println(xml);
			out.println(xml);
		}
		//ob_freeze_NT
		
		else if (strCommand.equalsIgnoreCase("ob_freeze_NT")) {
			String CONTENT_TYPE = "text/xml; charset=windows-12" + txtCB_Month
			+ "2";
	response.setContentType(CONTENT_TYPE);
	int ct=0;
	xml = "<response><command>ob_freeze</command>";
	try {

		String sql3 = "select ACCOUNT_NO from FAS_BRS_OB_STATUS_NT where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNT_NO="+cmbBankAccNo;
        System.out.println("sql3:::"+sql3);
		ps3 = con.prepareStatement(sql3);
		rs3 = ps3.executeQuery();

		while (rs3.next()) {
			ct++;
			xml = xml + "<flag>success</flag>";
			xml = xml + "<accountNo>" + rs3.getLong("ACCOUNT_NO")+ "</accountNo>";
		
		}
		if(ct==0)
		{
			xml = xml + "<flag>nodata</flag>";	
		}
		
	} catch (Exception e) {
		xml = xml + "<flag>failure</flag>";
		System.out.println(e);
		e.printStackTrace();
	}

	xml = xml + "</response>";
	System.out.println(xml);
	out.println(xml);
		}
		
		else if (strCommand.equalsIgnoreCase("ob_freeze")) {
			String CONTENT_TYPE = "text/xml; charset=windows-12" + txtCB_Month
			+ "2";
	response.setContentType(CONTENT_TYPE);
	int ct=0;
	xml = "<response><command>ob_freeze</command>";
	try {

		String sql3 = "select ACCOUNT_NO from FAS_BRS_OB_STATUS where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNT_NO="+cmbBankAccNo;
        System.out.println("sql3:::"+sql3);
		ps3 = con.prepareStatement(sql3);
		rs3 = ps3.executeQuery();

		while (rs3.next()) {
			ct++;
			xml = xml + "<flag>success</flag>";
			xml = xml + "<accountNo>" + rs3.getLong("ACCOUNT_NO")+ "</accountNo>";
		
		}
		if(ct==0)
		{
			xml = xml + "<flag>nodata</flag>";	
		}
		
	} catch (Exception e) {
		xml = xml + "<flag>failure</flag>";
		System.out.println(e);
		e.printStackTrace();
	}

	xml = xml + "</response>";
	System.out.println(xml);
	out.println(xml);
		}
		else if (strCommand.equalsIgnoreCase("LoadTWADTransactions")) {  
			System.out.println("------------------  RKsbg ------------------");
			String CONTENT_TYPE = "text/xml; charset=windows-12" + txtCB_Month
			+ "2";
			response.setContentType(CONTENT_TYPE);
			
			xml = "<response><command>LoadTWADTransactions</command>";
			String filtersql = "";
			
			try {

				String sql = "                  select * from \n"
						+ "                                      (\n"
						+ "                                       select \n"
						+ "                                               \n"
						+ "                                         doc_type,                                                                                                     \n"
						+ "                                         r_challan_no, \n"
						+ "                                         r_challan_date, \n"
						+ "                                         r_no, 								  \n"
						+ "                                         r_date, 				\n"
						+ "                                         r_cheque_or_dd, 							  \n"
						+ "                                         r_cheque_dd_no, \n"
						+ "                                         r_cheque_dd_date, \n"
						+ "                                         cr_amount, 							  \n"
						+ "                                         r_particulars, 		\n"
						+ "                                         \n"
						+ "                                         w_challan_no, \n"
						+ "                                         w_challan_date, \n"
						+ "                                         w_no,								  \n"
						+ "                                         w_date,								  \n"
						+ "                                         w_cheque_or_dd,							  \n"
						+ "                                         w_cheque_dd_no,\n"
						+ "                                         w_cheque_dd_date, \n"
						+ "                                         dr_amount,								  \n"
						+ "                                         w_particulars,			 				  \n"
						+ "                                         \n"
						+ "                                         com_doc_no, \n"
						+ "                                         com_doc_date,\n"
						+ "                                         com_cheque_or_dd, \n"
						+ "                                         com_cheque_dd_no\n"
						+ "                                         \n"
						+ "                                       from \n"
						+ "                                        (\n"
						+ "                                         select 			                                                  \n"
						+ "                                             doc_type,                                                            \n"
						+ "                                             \n"
						+ "                                             r_challan_no, \n"
						+ "                                             r_challan_date, \n"
						+ "                            	 	             r_no, 								  \n"
						+ "                            	 	             r_date, 				\n"
						+ "                            	 	             r_cheque_or_dd, 							  \n"
						+ "                                             r_cheque_dd_no, \n"
						+ "                                             r_cheque_dd_date, \n"
						+ "                                             cr_amount, 							  \n"
						+ "                            	 	             r_particulars, 		\n"
						+ "                                             \n"
						+ "                                             w_challan_no, \n"
						+ "                                             w_challan_date, \n"
						+ "                                             w_no,								  \n"
						+ "                            	 	             w_date,								  \n"
						+ "                            	 	             w_cheque_or_dd,							  \n"
						+ "                            	 	             w_cheque_dd_no,\n"
						+ "                                             w_cheque_dd_date, \n"
						+ "                            	 	             dr_amount,								  \n"
						+ "                            	 	             w_particulars,			 				  \n"
						+ "              \n"
						+ "                                                 case when ( r_challan_no = 0 ) then                                                 \n"
						+ "                                                    w_no                                                                     \n"
						+ "                                                 else                                                                        \n"
						+ "                                                    r_challan_no                                                                     \n"
						+ "                                                 end as com_doc_no,                                                              \n"
						+ "                                                                                                                                 \n"
						+ "              \n"
						+ "                                                 case when ( r_date is null ) then                                               \n"
						+ "                                                    w_date                                                                       \n"
						+ "                                                 else                                                                            \n"
						+ "                                                    r_date                                                                       \n"
						+ "                                                 end as com_doc_date,\n"
						+ "                                                 \n"
						+ "                                              ---------------\n"
						+ "                                                 case when ( r_cheque_or_dd is null ) then                                            \n"
						+ "                                                    to_char(w_cheque_or_dd)                                                                    \n"
						+ "                                                 else                                                                            \n"
						+ "                                                    to_char(r_cheque_or_dd)                                                                   \n"
						+ "                                                 end as com_cheque_or_dd,\n"
						+ "                                              ----------------\n"
						+ "                                                 \n"
						+ "                                                 case when ( r_cheque_dd_no is null ) then                                            \n"
						+ "                                                    to_char(w_cheque_dd_no)                                                                    \n"
						+ "                                                 else                                                                            \n"
						+ "                                                    to_char(r_cheque_dd_no)                                                                   \n"
						+ "                                                 end as com_cheque_dd_no\n"
						+ "                                              \n"
						+ "                                              \n"
						+ "                            	            from									  \n"
						+ "                            	 	          (					 				     \n"
						+ "                                          \n"
						+ "                                          \n"
						+ "                                           --- Bank Receipt  --- \n"
						+ "                                             select \n"
						+ "                                               * \n"
						+ "                                             from \n"
						+ "                                             (\n"
						+ "                                                select         \n"
						+ "                                                \n"
						+ "                                                  'BR' as doc_type,                \n"
						+ "                                                  \n"
						+ "                            	                    rem.CHALLAN_NO as r_challan_no,								   \n"
						+ "                            	                    to_char(rem.CHALLAN_DATE,'DD/MM/YYYY') as r_challan_date,     \n"
						+ "                                                  \n"
						+ "                                                  m.receipt_no as r_no, \n"
						+ "                                                  to_char(m.receipt_date,'DD/MM/YYYY') as r_date, \n"
						+ "                            	                    t.CHEQUE_OR_DD as r_cheque_or_dd, \n"
						+ "                                                  t.CHEQUE_DD_NO as r_cheque_dd_no,\n"
						+ "                                                  to_char(t.CHEQUE_DD_DATE,'DD/MM/YYYY') as r_cheque_dd_date,\n"
						+ "                            	                    t.AMOUNT as cr_amount, 						   \n"
						+ "                            	                    m.REMARKS as r_particulars,			 \n"
						+ "                                                  \n"
						+ "                                                  0 as w_challan_no, \n"
						+ "                                                  null as w_challan_date, \n"
						+ "                            	                    0 as w_no, 										   \n"
						+ "                            	                    null as w_date,									   \n"
						+ "                            	                    null as w_cheque_or_dd, 							   \n"
						+ "                            	                    '' as w_cheque_dd_no,								   \n"
						+ "                                                  null as w_cheque_dd_date, \n"
						+ "                            	                    0 as dr_amount,   								   \n"
						+ "                            	                    null as w_particulars							                                                     \n"
						+ "                                                  \n"
						+ "                            	                  from												   \n"
						+ "                            	                     fas_receipt_master m,							   \n"
						+ "                            	                     fas_remittance rem,                                           \n"
						+ "                                                   fas_receipt_transaction t                                      \n"
						+ "                            	                  where								   \n"
						+ "                            	                     m.accounting_unit_id = "
						+ cmbAcc_UnitCode
						+ " and 				   \n"
						+ "                            	                     m.accounting_for_office_id = "
						+ cmbOffice_code
						+ " and 			   \n"
						+ "                            	                     m.cashbook_month = "
						+ txtCB_Month
						+ " and 					   \n"
						+ "                            	                     m.cashbook_year  = "
						+ txtCB_Year
						+ "  and    				   \n"
						+ "                            	                     m.ACCOUNT_NO in ( "
						+ cmbBankAccNo
						+ " ) and	   			   \n"
						+ " (t.CHEQ_DISHONOUR_STATUS !='Y' or t.CHEQ_DISHONOUR_STATUS is null) \n"
						+ " and												                      \n"
						+ "                            	                     rem.STATUS is null and   					   \n"
						+ "                            	                     m.ACCOUNTING_UNIT_ID = rem.ACCOUNTING_UNIT_ID and 		   \n"
						+ "                            	                     m.ACCOUNTING_FOR_OFFICE_ID = rem.ACCOUNTING_FOR_OFFICE_ID and \n"
						+ "                            	                     m.CASHBOOK_YEAR = rem.CASHBOOK_YEAR and 			  \n"
						+ "                            	                     m.CASHBOOK_MONTH = rem.CASHBOOK_MONTH and 		          \n"
						+ "                            	                     m.CHALLAN_NO = rem.CHALLAN_NO and 			          \n"
						+ "                                                   m.CHALLAN_DATE = rem.CHALLAN_DATE and                         \n"
						+ "                                                   m.accounting_unit_id = t.accounting_unit_id and \n"
						+ "                                                   m.accounting_for_office_id= t.accounting_for_office_id and    \n"
						+ "                                                   m.cashbook_month = t.cashbook_month and                       \n"
						+ "                                                   m.cashbook_year = t.cashbook_year and                         \n"
						+ "                                                   m.receipt_no = t.receipt_no  " +
						//added on 08may2013 to avoid repeated values in CR and BR
						"     and m.created_by_module!='CR'                            \n"
						+ "                                                   \n"
						+ "                                              ) a \n"
						+ "                                               \n"
						+ "                                             full join \n"
						+ "                                               \n"
						+ "                                             (                                                \n"
						+ "                                              select \n"
						+ "                                                 accounting_unit_id as unit_id, \n"
						+ "                                                 accounting_for_office_id as office_id, \n"
						+ "                                                 cashbook_month as cbmonth, \n"
						+ "                                                 cashbook_year as cbyear,\n"
						+ "                                                 to_char(remittance_date,'DD/MM/YYYY')    as rem_date, \n"
						+ "                                                 voucher_or_challan_no as vc_no, \n"
						+ "                                                 cr_amount as cr_amt, \n"
						+ "                                                 account_no as acc, \n"
						+ "                                                 doc_no\n"
						+ "                                              from                                                 \n"
						+ "                                                FAS_BRS_OB_TRANSACTION  \n"
						+ "                                              where \n"
						+ "                                                ACCOUNTING_UNIT_ID = "
						+ cmbAcc_UnitCode
						+ " and \n"
						+ "                                                ACCOUNTING_FOR_OFFICE_ID = "
						+ cmbOffice_code
						+ " and \n"
						+ "                                                CASHBOOK_YEAR = "
						+ txtCB_Year
						+ " and \n"
						+ "                                                CASHBOOK_MONTH = "
						+ txtCB_Month
						+ " and \n"
						+ "                                                ACCOUNT_NO = "
						+ cmbBankAccNo
						+ " and \n"
						+ "                                                doc_type='BR' and \n"
						+ "                                                twad_or_non_twad='T'                                                 \n"
						+ "                                            )b   \n"
						+ "                                            on \n"
						+ "                                               b.vc_no = a.r_challan_no and \n"
						+ "                                               b.rem_date = a.r_challan_date and \n"
						+ "                                               b.cr_amt = a.cr_amount and \n"
						+ "                                               b.doc_no = a.r_no  \n"
						+ "                                           where unit_id is null     \n"
						+ "                                       \n"
						+ "                                         \n"
						+ "                                      \n"
						+ "                                            union all \n"
						+ "                                          \n"
						+ "                                      \n"
						+ "                                      \n"
						+ "                                            --- Cash Receipt  --- \n"
						+ "                                            \n"
						+ "                               select * from \n"
						+ "                                                                 (\n"
						+ "                                                                  select                                                             \n"
						+ "                                                                   'CR' as doc_type , \n"
						+ "                                                                   p_challan_no as r_challan_no,  \n"
						+ "                                                                   to_char(p_challan_date,'DD/MM/YYYY') as r_challan_date,                                                                                                       \n"
						+ "                                                                   r_no,  \n"
						+ "                                                                    to_char(r_date,'DD/MM/YYYY') r_date,                   \n"
						+ "                                                                   r_cheque_or_dd,					    \n"
						+ "                                                                   r_cheque_dd_no,			 \n"
						+ "                                                                   to_char(r_cheque_dd_date,'DD/MM/YYYY') as r_cheque_dd_date ,  \n"
						+ "                                                                   cr_amount,    \n"
						+ "                                                                   r_particulars,  \n"
						+ "                                                                   \n"
						+ "                                                                    \n"
						+ "                                                                  0 as w_challan_no,  \n"
						+ "                                                                  null as w_challan_date,  \n"
						+ "                                            	                    0 as w_no, 										    \n"
						+ "                                            	                    null as w_date,									    \n"
						+ "                                            	                    null as w_cheque_or_dd, 							    \n"
						+ "                                            	                    '' as w_cheque_dd_no,								    \n"
						+ "                                                                  null as w_cheque_dd_date,  \n"
						+ "                                            	                    0 as dr_amount,   								    \n"
						+ "                                            	                    null as w_particulars									    \n"
						+ "                                                                       \n"
						+ "                                                                from   \n"
						+ "                                                                (    \n"
						+ "                                                                  select   \n"
						+ "                                                                   *   \n"
						+ "                                                                  from   \n"
						+ "                                                                  (  \n"
						+ "                                                                    select   \n"
						+ "                                                                       DISTINCT(challan_no) as p_challan_no,          \n"
						+ "                                                                       payment_date as p_challan_date  \n"
						+ "                                                                    from   \n"
						+ "                                                                       fas_payment_master m  \n"
						+ "                                                                    where   \n"
						+ "                                                                       ACCOUNTING_UNIT_ID = "
						+ cmbAcc_UnitCode
						+ " and 										    \n"
						+ "                                                                       ACCOUNTING_FOR_OFFICE_ID = "
						+ cmbOffice_code
						+ " and 									    \n"
						+ "                                                                       CASHBOOK_MONTH =  "
						+ txtCB_Month
						+ " and 											    \n"
						+ "                                                                       CASHBOOK_YEAR = "
						+ txtCB_Year
						+ " and												                       \n"
						+ "                                                                       ACCOUNT_NO in  ( "
						+ cmbBankAccNo
						+ " )  and		  \n"
						+ "                                                                       created_by_module in ('CRM','BPF','BPP')   and payment_status='L' \n"
						+ "                                                                   )X   \n"
						+ "                                                                      \n"
						// changed from left outer to inner on 21/03/2013 due to urban salem ob problem
						//+ "                                                                   left outer join   \n"
						+ "                                                                   inner join   \n"
						+ "                                                                     \n"
						+ "                                                                   (   \n"
						+ "                                                                       select											    \n"
						+ "                                                                           m.receipt_no as r_no,   \n"
						+ "                                                                           m.receipt_date as r_date, \n"
						+ "                                                                           m.CHALLAN_NO as challan_no,							    \n"
						+ "                                                                           to_char(m.CHALLAN_DATE,'DD/MM/YYYY') as  challan_date,      \n"
						+ "                                                                           null as r_cheque_or_dd,					    \n"
						+ "                                                                           '' as r_cheque_dd_no,			 \n"
						+ "                                                                           null as r_cheque_dd_date,  \n"
						+ "                                                                           m.TOTAL_AMOUNT as cr_amount , 						    \n"
						+ "                                                                           m.REMARKS as r_particulars  \n"
						+ "                                                                       from												    \n"
						+ "                                                                          fas_receipt_master m						                        \n"
						+ "                                                                       where												    \n"
						+ "                                                                          m.accounting_unit_id = "
						+ cmbAcc_UnitCode
						+ "   and 				    \n"
						+ "                                                                          m.accounting_for_office_id = "
						+ cmbOffice_code
						+ "     \n"
						+ "                                                                            \n"
						+ "                                                                    )Y on  \n"
						+ "                                                                       X.p_challan_no = Y.challan_no and    \n"
						+ "                                                                      to_char(X.p_challan_date,'DD/MM/YYYY') = Y.challan_date            \n"
						+ "                                                                 ) \n"
						+ "                                                               \n"
						+ "                                                                ) a       \n"
						+ "                                                                  \n"
						+ "                                                                full join  \n"
						+ "                                                                 \n"
						+ "                                                              (  \n"
						+ "                                                               select  \n"
						+ "                                                                 accounting_unit_id as unit_id,  \n"
						+ "                                                                 accounting_for_office_id as office_id,  \n"
						+ "                                                                 cashbook_month as cbmonth,  \n"
						+ "                                                                 cashbook_year as cbyear, \n"
						+ "                                                                 to_char(remittance_date,'DD/MM/YYYY')    as rem_date,  \n"
						+ "                                                                 voucher_or_challan_no as vc_no,  \n"
						+ "                                                                 cr_amount as cr_amt,  \n"
						+ "                                                                 account_no as acc,  \n"
						+ "                                                                 doc_no \n"
						+ "                                                              from                                                  \n"
						+ "                                                                FAS_BRS_OB_TRANSACTION   \n"
						+ "                                                              where  \n"
						+ "                                                                ACCOUNTING_UNIT_ID = "
						+ cmbAcc_UnitCode
						+ " and  \n"
						+ "                                                                ACCOUNTING_FOR_OFFICE_ID = "
						+ cmbOffice_code
						+ " and  \n"
						+ "                                                                CASHBOOK_YEAR = "
						+ txtCB_Year
						+ " and  \n"
						+ "                                                                CASHBOOK_MONTH = "
						+ txtCB_Month
						+ " and  \n"
						+ "                                                                ACCOUNT_NO = "
						+ cmbBankAccNo
						+ " and  \n"
						+ "                                                                doc_type='CR' and  \n"
						+ "                                                                twad_or_non_twad='T'    \n"
						+ "                                                              ) b \n"
						+ "                                                              on  \n"
						+ "                                                               b.vc_no = a.r_challan_no and  \n"
						+ "                                                               b.rem_date = a.r_challan_date and  \n"
						+ "                                                               b.cr_amt = a.cr_amount and  \n"
						+ "                                                               b.doc_no = a.r_no   \n"
						+ "                                                           where unit_id is null      \n"
						+ "                                                                   \n"
						+ "                                         union all \n"
						+ "                                         \n"
						+ "                                        \n"
						+ "                                                                              \n"
						+ "                                             ---- Payment  --- \n"
						+ "                                      \n"
						+ "                                               select \n"
						+ "                                               * \n"
						+ "                                               from \n"
						+ "                                               (\n"
						+ "                                                 select 			 \n"
						+ "                                                  'P' as doc_type,                                   \n"
						+ "                                                   \n"
						+ "                                                  0 as  r_challan_no, \n"
						+ "                                                  null as r_challan_date,                                                   \n"
						+ "                            	                    0 as r_no, 														   \n"
						+ "                            	                    '' as r_date,														   \n"
						+ "                            	                    null as r_cheque_or_dd, 												   \n"
						+ "                            	                    '' as r_cheque_dd_no,		\n"
						+ "                                                  null as r_cheque_dd_date,\n"
						+ "                            	                    0 as cr_amount ,  													   \n"
						+ "                            	                    null as r_particulars,		\n"
						+ "                                                   \n"
						+ "                                                  0 as w_challan_no, \n"
						+ "                                                  null as w_challan_date,  \n"
						+ "                            	                    m.VOUCHER_NO as w_no, 												   \n"
						+ "                            	                    to_char(m.PAYMENT_DATE,'DD/MM/YYYY') as w_date ,				       \n"
						+ "                            	                     t.CHEQUE_OR_DD as w_cheque_or_dd,										   \n"
						+ "                            	                     t.CHEQUE_DD_NO as w_cheque_dd_no,											   \n"
						+ "                                                   to_char(t.cheque_dd_date,'DD/MM/YYYY') as w_cheque_dd_date, \n"
						+ "                            	                     t.AMOUNT as dr_amount,												   \n"
						+ "                            	                     t.PARTICULARS as w_particulars									   \n"
						+ "                                                from 																   \n"
						+ "                            	                    fas_payment_master m, 												   \n"
						+ "                            	                    fas_payment_transaction t											   \n"
						+ "                            	                   where															       \n"
						+ "                            	                    m.ACCOUNTING_UNIT_ID = "
						+ cmbAcc_UnitCode
						+ " and 										   \n"
						+ "                            	                    m.ACCOUNTING_FOR_OFFICE_ID = "
						+ cmbOffice_code
						+ " and 									   \n"
						+ "                            	                    m.CASHBOOK_MONTH =  "
						+ txtCB_Month
						+ " and 											   \n"
						+ "                            	                    m.CASHBOOK_YEAR = "
						+ txtCB_Year
						+ " and												                      \n"
						+ "                            	                    m.ACCOUNT_NO in  ( "
						+ cmbBankAccNo
						+ " )  and										   \n"
						+ " (t.CHEQ_CANCEL_STATUS !='Y' or t.CHEQ_CANCEL_STATUS is null) \n"						
						+ " and												                      \n"
						+ "                                                  m.created_by_module !='CRM' and  \n"
						+ "                                                  m.accounting_unit_id = t.accounting_unit_id and 					   \n"
						+ "                            	                    m.accounting_for_office_id = t.accounting_for_office_id and 		   \n"
						+ "                            	                    m.cashbook_month =t.cashbook_month and 								   \n"
						+ "                            	                    m.cashbook_year = t.cashbook_year and 								   \n"
						+ "                            	                    m.voucher_no = t.voucher_no	and m.payment_status='L'			                                                     \n"
						+ "                                              )a                                               \n"
						+ "                                             \n"
						+ "                                              full join \n"
						+ "                                              \n"
						+ "                                              ( \n"
						+ "                                               select \n"
						+ "                                                 accounting_unit_id as unit_id, \n"
						+ "                                                 accounting_for_office_id as office_id, \n"
						+ "                                                 cashbook_month as cbmonth, \n"
						+ "                                                 cashbook_year as cbyear,\n"
						+ "                                                 to_char(WITHDRAWAL_DATE,'DD/MM/YYYY') as with_date, \n"
						+ "                                                 voucher_or_challan_no as vc_no, \n"
						+ "                                                 dr_amount as dr_amt, \n"
						+ "                                                 account_no as acc, \n"
						+ "                                                 doc_no\n"
						+ "                                              from                                                 \n"
						+ "                                                FAS_BRS_OB_TRANSACTION  \n"
						+ "                                              where \n"
						+ "                                                ACCOUNTING_UNIT_ID = "
						+ cmbAcc_UnitCode
						+ " and \n"
						+ "                                                ACCOUNTING_FOR_OFFICE_ID = "
						+ cmbOffice_code
						+ " and \n"
						+ "                                                CASHBOOK_YEAR = "
						+ txtCB_Year
						+ " and \n"
						+ "                                                CASHBOOK_MONTH = "
						+ txtCB_Month
						+ " and \n"
						+ "                                                ACCOUNT_NO = "
						+ cmbBankAccNo
						+ " and \n"
						+ "                                                doc_type='P' and \n"
						+ "                                                twad_or_non_twad='T'                                                 \n"
						+ "                                            )b   \n"
						+ "                                            on \n"
						+ "                                               b.vc_no = a.w_no and \n"
						+ "                                               b.with_date = a.w_date and \n"
						+ "                                               b.dr_amt = a.dr_amount and \n"
						+ "                                               b.doc_no = a.w_no   \n"
						+ "                                           where unit_id is null     \n"
						+ "                                                                                      \n"
						+ "                                              \n"
						+ "                                                                                            \n"
						+ "                                        union all \n"
						+ "                                        \n"
						+ "                                        \n"
						+ "                                        ------- Fund Trf from HO ------ \n"
						+ "                    \n"
						+ "                                                  \n"
						+ "                                                                                  -- Fund Trf from HO -- \n"
						+ "                                          \n"
						+ "                                             select \n"
						+ "                                             *\n"
						+ "                                             from \n"
						+ "                                             (\n"
						+ "                                                select				 \n"
						+ "                                                   'FT from HO' as doc_type, \n"
						+ "                                                   \n"
						+ "                            	                     0 as  r_challan_no, \n"
						+ "                                                   null as r_challan_date,                                                   \n"
						+ "                            	                     0 as r_no, 														   \n"
						+ "                            	                     '' as r_date,														   \n"
						+ "                            	                     null as r_cheque_or_dd, 												   \n"
						+ "                            	                     '' as r_cheque_dd_no,		\n"
						+ "                                                   null as r_cheque_dd_date,\n"
						+ "                            	                     0 as cr_amount ,  													   \n"
						+ "                            	                     null as r_particulars,		\n"
						+ "                                              \n"
						+ "                                                   0 as w_challan_no, \n"
						+ "                                                   null as w_challan_date,                              	                      \n"
						+ "                            	                     VOUCHER_NO as w_no, 												   \n"
						+ "                            	                     to_char(DATE_OF_TRANSFER,'DD/MM/YYYY') as w_date ,				       \n"
						+ "                            	                     null as w_cheque_or_dd,										   \n"
						+ "                            	                     '' as w_cheque_dd_no,											   \n"
						+ "                                                   null as w_cheque_dd_date,                                                    \n"
						+ "                            	                     TOTAL_AMOUNT as dr_amount,												   \n"
						+ "                            	                     REMARKS as w_particulars					   \n"
						+ "                            	                  from												   \n"
						+ "                            	                     fas_fund_trf_from_ho_master  \n"
						+ "                            	                  where												   \n"
						+ "                            	                     ACCOUNTING_UNIT_ID = "
						+ cmbAcc_UnitCode
						+ " and   \n"
						+ "                            	                     ACCOUNTING_FOR_OFFICE_ID = "
						+ cmbOffice_code
						+ " and   \n"
						+ "                            	                     CASHBOOK_MONTH = "
						+ txtCB_Month
						+ " and   \n"
						+ "                            	                     CASHBOOK_YEAR = "
						+ txtCB_Year
						+ " and                      \n"
						+ "                            	                     HO_ACCOUNT_NO =  "
						+ cmbBankAccNo
						+ " and   \n"
						+ "                            	                     TRANSFER_STATUS ='L'    \n"
						+ "                            	               )a\n"
						+ "                                            \n"
						+ "                                             full join \n"
						+ "                                               \n"
						+ "                                             (                                                \n"
						+ "                                              select \n"
						+ "                                                 accounting_unit_id as unit_id, \n"
						+ "                                                 accounting_for_office_id as office_id, \n"
						+ "                                                 cashbook_month as cbmonth, \n"
						+ "                                                 cashbook_year as cbyear,\n"
						+ "                                                 to_char(WITHDRAWAL_DATE,'DD/MM/YYYY')    as w_date, \n"
						+ "                                                 voucher_or_challan_no as vc_no, \n"
						+ "                                                 dr_amount as dr_amt, \n"
						+ "                                                 account_no as acc, \n"
						+ "                                                 doc_no\n"
						+ "                                              from                                                 \n"
						+ "                                                FAS_BRS_OB_TRANSACTION  \n"
						+ "                                              where \n"
						+ "                                                ACCOUNTING_UNIT_ID = "
						+ cmbAcc_UnitCode
						+ " and \n"
						+ "                                                ACCOUNTING_FOR_OFFICE_ID = "
						+ cmbOffice_code
						+ " and \n"
						+ "                                                CASHBOOK_YEAR = "
						+ txtCB_Year
						+ " and \n"
						+ "                                                CASHBOOK_MONTH = "
						+ txtCB_Month
						+ " and \n"
						+ "                                                ACCOUNT_NO = "
						+ cmbBankAccNo
						+ " and \n"
						+ "                                                doc_type='FT from HO' and \n"
						+ "                                                twad_or_non_twad='T'                                                 \n"
						+ "                                            )b   \n"
						+ "                                            on \n"
						+ "                                               b.vc_no = a.w_no and \n"
						+ "                                               b.w_date = a.w_date and \n"
						+ "                                               b.dr_amt = a.dr_amount and \n"
						+ "                                               b.doc_no = a.w_no  \n"
						+ "                                           where unit_id is null     \n"
						+ "                                          \n"
						+ "                                                    \n"
						+ "                                        \n"
						+ "                                         union all \n"
						+ "                                         \n"
						+ "                                         \n"
						+ "                                         \n"
						+ "                                         ------- Fund Trf from Office  -------                                         \n"
						+ "                                         \n"
						+ "                                         select \n"
						+ "                                          *\n"
						+ "                                         from \n"
						+ "                                              (                                              \n"
						+ "                                                select				 \n"
						+ "                                                   'FT from Office' as doc_type,                                   \n"
						+ "                            	                     \n"
						+ "                                                   0 as  r_challan_no, \n"
						+ "                                                   null as r_challan_date,                                                   \n"
						+ "                            	                     0 as r_no, 														   \n"
						+ "                            	                     null as r_date,														   \n"
						+ "                            	                     null as r_cheque_or_dd, 												   \n"
						+ "                            	                     '' as r_cheque_dd_no,		\n"
						+ "                                                   null as r_cheque_dd_date,\n"
						+ "                            	                     0 as cr_amount ,  													   \n"
						+ "                            	                     null as r_particulars,		\n"
						+ "                                                   \n"
						+ "                                                   0 as w_challan_no, \n"
						+ "                                                   null as w_challan_date, \n"
						+ "                            	                     VOUCHER_NO as w_no, 												   \n"
						+ "                            	                     to_char(DATE_OF_TRANSFER,'DD/MM/YYYY') as w_date ,				       \n"
						+ "                            	                     CHEQUE_OR_DD as w_cheque_or_dd,										   \n"
						+ "                            	                     CHEQUE_DD_NO as w_cheque_dd_no,\n"
						+ "                                                   to_char(cheque_dd_date,'DD/MM/YYYY') as w_cheque_dd_date, \n"
						+ "                            	                     TOTAL_AMOUNT as dr_amount,												   \n"
						+ "                            	                     PARTICULARS as w_particulars					   \n"
						+ "                                                   \n"
						+ "                            	                  from												   \n"
						+ "                            	                     fas_fund_trf_from_office  \n"
						+ "                            	                  where												   \n"
						+ "                            	                     ACCOUNTING_UNIT_ID = "
						+ cmbAcc_UnitCode
						+ " and   \n"
						+ "                            	                     ACCOUNTING_FOR_OFFICE_ID = "
						+ cmbOffice_code
						+ " and                       \n"
						+ "                            	                     CASHBOOK_MONTH = "
						+ txtCB_Month
						+ " and   \n"
						+ "                            	                     CASHBOOK_YEAR = "
						+ txtCB_Year
						+ " and   \n"
						+ "                            	                     OFFICE_ACCOUNT_NO =  "
						+ cmbBankAccNo
						+ " and   \n"
						+ "                            	                     TRANSFER_STATUS ='L'    \n"
						+ "                            	                ) a\n"
						+ "                                              \n"
						+ "                                             full join \n"
						+ "                                               \n"
						+ "                                             (                                                \n"
						+ "                                              select \n"
						+ "                                                 accounting_unit_id as unit_id, \n"
						+ "                                                 accounting_for_office_id as office_id, \n"
						+ "                                                 cashbook_month as cbmonth, \n"
						+ "                                                 cashbook_year as cbyear,\n"
						+ "                                                 to_char(WITHDRAWAL_DATE,'DD/MM/YYYY') as w_date, \n"
						+ "                                                 voucher_or_challan_no as vc_no, \n"
						+ "                                                 dr_amount as dr_amt, \n"
						+ "                                                 account_no as acc, \n"
						+ "                                                 doc_no\n"
						+ "                                              from                                                 \n"
						+ "                                                 FAS_BRS_OB_TRANSACTION  \n"
						+ "                                              where \n"
						+ "                                                ACCOUNTING_UNIT_ID = "
						+ cmbAcc_UnitCode
						+ " and \n"
						+ "                                                ACCOUNTING_FOR_OFFICE_ID = "
						+ cmbOffice_code
						+ " and \n"
						+ "                                                CASHBOOK_YEAR = "
						+ txtCB_Year
						+ " and \n"
						+ "                                                CASHBOOK_MONTH = "
						+ txtCB_Month
						+ " and \n"
						+ "                                                ACCOUNT_NO = "
						+ cmbBankAccNo
						+ " and \n"
						+ "                                                doc_type='FT from Office' and \n"
						+ "                                                twad_or_non_twad='T'                                                 \n"
						+ "                                            )b   \n"
						+ "                                            on \n"
						+ "                                               b.vc_no = a.w_no and \n"
						+ "                                               b.w_date = a.w_date and \n"
						+ "                                               b.dr_amt = a.dr_amount and \n"
						+ "                                               b.doc_no = a.w_no  \n"
						+ "                                           where unit_id is null     \n"
						+ "                                         \n"
						+ "                                                            \n"
						+ "                                          union all   \n"
						+ "                                       \n"
						+ "                                         \n"
						+ "                                          -- Fund Receipt by HO                                             \n"
						+ "                                         \n"
						+ "                                      \n"
						+ "                                              select \n"
						+ "                                              * \n"
						+ "                                              from \n"
						+ "                                              (                                             \n"
						+ "                                               select                                                     \n"
						+ "                                                  'FR by HO' as doc_type,                                                                                      \n"
						+ "                            	                    rem.CHALLAN_NO as r_challan_no,							   \n"
						+ "                            	                    to_char(rem.CHALLAN_DATE,'DD/MM/YYYY')  as r_challan_date, \n"
						+ "                                                  \n"
						+ "                                                  m.receipt_no as r_no, \n"
						+ "                                                  to_char(m.receipt_date,'DD/MM/YYYY') as r_date, \n"
						+ "                            	                    CHEQUE_OR_DD as r_cheque_or_dd,					   \n"
						+ "                            	                    CHEQUE_DD_NO as r_cheque_dd_no,		\n"
						+ "                                                  to_char(CHEQUE_DD_DATE,'DD/MM/YYYY')  as r_cheque_dd_date,\n"
						+ "                            	                    m.TOTAL_AMOUNT as cr_amount , 						   \n"
						+ "                            	                    m.PARTICULARS as r_particulars,			   \n"
						+ "                                                  \n"
						+ "                            	                    0 as w_challan_no, \n"
						+ "                                                  null as w_challan_date, \n"
						+ "                            	                    0 as w_no, 										   \n"
						+ "                            	                    null as w_date,									   \n"
						+ "                            	                    null as w_cheque_or_dd, 							   \n"
						+ "                            	                    '' as w_cheque_dd_no,								   \n"
						+ "                                                  null as w_cheque_dd_date, \n"
						+ "                            	                    0 as dr_amount,   								   \n"
						+ "                            	                    null as w_particulars										   \n"
						+ "                                                  \n"
						+ "                            	                 from   \n"
						+ "                            	                    fas_fund_receipt_by_ho m,  \n"
						+ "                            	                    fas_remittance rem   		  \n"
						+ "                            	                 where    \n"
						+ "                            	                    m.ACCOUNTING_UNIT_ID = "
						+ cmbAcc_UnitCode
						+ " and   \n"
						+ "                            	                    m.ACCOUNTING_FOR_OFFICE_ID = "
						+ cmbOffice_code
						+ " and   \n"
						+ "                            	                    m.CASHBOOK_MONTH = "
						+ txtCB_Month
						+ " and   \n"
						+ "                            	                    m.CASHBOOK_YEAR = "
						+ txtCB_Year
						+ " and                      \n"
						+ "                            	                    m.HO_ACCOUNT_NO = "
						+ cmbBankAccNo
						+ " and                     \n"
						+ "                            	                    m.ACCOUNTING_UNIT_ID = rem.ACCOUNTING_UNIT_ID and 					           \n"
						+ "                            	                    m.ACCOUNTING_FOR_OFFICE_ID = rem.ACCOUNTING_FOR_OFFICE_ID and 		                   \n"
						+ "                            	                    m.CASHBOOK_YEAR = rem.CASHBOOK_YEAR and 							   \n"
						+ "                            	                    m.CASHBOOK_MONTH = rem.CASHBOOK_MONTH and 							   \n"
						+ "                            	                    m.CHALLAN_NO = rem.CHALLAN_NO  and 								   \n"
						+ "                            	                    m.CHALLAN_DATE = rem.CHALLAN_DATE and  							   \n"
						+ "                            	                    rem.STATUS is null   \n"
						+ "                                              )a\n"
						+ "                                              \n"
						+ "                                             full join \n"
						+ "                                               \n"
						+ "                                             (                                                \n"
						+ "                                              select \n"
						+ "                                                 accounting_unit_id as unit_id, \n"
						+ "                                                 accounting_for_office_id as office_id, \n"
						+ "                                                 cashbook_month as cbmonth, \n"
						+ "                                                 cashbook_year as cbyear,\n"
						+ "                                                 to_char(remittance_date,'DD/MM/YYYY')    as rem_date, \n"
						+ "                                                 voucher_or_challan_no as vc_no, \n"
						+ "                                                 cr_amount as cr_amt, \n"
						+ "                                                 account_no as acc, \n"
						+ "                                                 doc_no\n"
						+ "                                              from                                                 \n"
						+ "                                                FAS_BRS_OB_TRANSACTION  \n"
						+ "                                              where \n"
						+ "                                                ACCOUNTING_UNIT_ID = "
						+ cmbAcc_UnitCode
						+ " and \n"
						+ "                                                ACCOUNTING_FOR_OFFICE_ID = "
						+ cmbOffice_code
						+ " and \n"
						+ "                                                CASHBOOK_YEAR = "
						+ txtCB_Year
						+ " and \n"
						+ "                                                CASHBOOK_MONTH = "
						+ txtCB_Month
						+ " and \n"
						+ "                                                ACCOUNT_NO = "
						+ cmbBankAccNo
						+ " and \n"
						+ "                                                doc_type='FR by HO' and \n"
						+ "                                                twad_or_non_twad='T'                                                 \n"
						+ "                                            )b   \n"
						+ "                                            on \n"
						+ "                                               b.vc_no = a.r_challan_no and \n"
						+ "                                               b.rem_date = a.r_challan_date and \n"
						+ "                                               b.cr_amt = a.cr_amount and \n"
						+ "                                               b.doc_no = a.r_no  \n"
						+ "                                           where unit_id is null     \n"
						+ "                                         \n"
						+ "                                                   \n"
						+ "                                        union all \n"
						+ "                                        \n"
						+ "                                        \n"
						+ "                                        ------ Fund Receipt by Office  ----- \n"
						+ "                                        \n"
						+ "                                        \n"
						+ "                                          select \n"
						+ "                                          * \n"
						+ "                                          from \n"
						+ "                                          (\n"
						+ "                                                select   \n"
						+ "                                                  'FR by Office' as doc_type,   \n"
						+ "                                                  \n"
						+ "                            	                    rem.CHALLAN_NO as r_challan_no,							   \n"
						+ "                            	                    to_char(rem.CHALLAN_DATE,'DD/MM/YYYY')  as r_challan_date,     \n"
						+ "                                                  m.receipt_no as r_no, \n"
						+ "                                                  to_char(m.receipt_date,'DD/MM/YYYY') as r_date, \n"
						+ "                            	                    m.CHEQUE_OR_DD as r_cheque_or_dd,					   \n"
						+ "                            	                    m.CHEQUE_DD_NO as r_cheque_dd_no,	\n"
						+ "                                                  to_char(m.CHEQUE_DD_DATE,'DD/MM/YYYY') as r_cheque_dd_date,\n"
						+ "                            	                    m.TOTAL_AMOUNT as cr_amount , 						   \n"
						+ "                            	                    m.PARTICULARS as r_particulars,	\n"
						+ "                                                  \n"
						+ "                            	                    0 as w_challan_no, \n"
						+ "                                                  null as w_challan_date, \n"
						+ "                            	                    0 as w_no, 										   \n"
						+ "                            	                    null as w_date,									   \n"
						+ "                            	                    null as w_cheque_or_dd, 							   \n"
						+ "                            	                    '' as w_cheque_dd_no,								   \n"
						+ "                                                  null as w_cheque_dd_date, \n"
						+ "                            	                    0 as dr_amount,   								   \n"
						+ "                            	                    null as w_particulars		\n"
						+ "                                                  \n"
						+ "                            	                 from   \n"
						+ "                            	                    fas_fund_receipt_by_office m,  \n"
						+ "                            	                    fas_remittance rem   							                      \n"
						+ "                            	                 where   \n"
						+ "                            	                    m.ACCOUNTING_UNIT_ID = "
						+ cmbAcc_UnitCode
						+ " and   \n"
						+ "                            	                    m.ACCOUNTING_FOR_OFFICE_ID = "
						+ cmbOffice_code
						+ " and   \n"
						+ "                            	                    m.CASHBOOK_MONTH = "
						+ txtCB_Month
						+ " and   \n"
						+ "                            	                    m.CASHBOOK_YEAR = "
						+ txtCB_Year
						+ " and                      \n"
						+ "                            	                    m.OFFICE_ACCOUNT_NO = "
						+ cmbBankAccNo
						+ " and                     \n"
						+ "                            	                    m.ACCOUNTING_UNIT_ID = rem.ACCOUNTING_UNIT_ID and 					   \n"
						+ "                            	                    m.ACCOUNTING_FOR_OFFICE_ID = rem.ACCOUNTING_FOR_OFFICE_ID and 		   \n"
						+ "                            	                    m.CASHBOOK_YEAR = rem.CASHBOOK_YEAR and 							   \n"
						+ "                            	                    m.CASHBOOK_MONTH = rem.CASHBOOK_MONTH and 							   \n"
						+ "                            	                    m.CHALLAN_NO = rem.CHALLAN_NO  and 								   \n"
						+ "                            	                    m.CHALLAN_DATE = rem.CHALLAN_DATE and  									   \n"
						+ "                            	                    rem.STATUS is null   \n"
						+ "                                            ) a   \n"
						+ "                                            full join \n"
						+ "                                            (\n"
						+ "                                              select \n"
						+ "                                                   accounting_unit_id as unit_id, \n"
						+ "                                                   accounting_for_office_id as office_id, \n"
						+ "                                                   cashbook_month as cbmonth, \n"
						+ "                                                   cashbook_year as cbyear,\n"
						+ "                                                   to_char(remittance_date,'DD/MM/YYYY')    as rem_date, \n"
						+ "                                                   voucher_or_challan_no as vc_no, \n"
						+ "                                                   cr_amount as cr_amt, \n"
						+ "                                                   account_no as acc, \n"
						+ "                                                   doc_no\n"
						+ "                                                from                                                 \n"
						+ "                                                  FAS_BRS_OB_TRANSACTION  \n"
						+ "                                                where \n"
						+ "                                                  ACCOUNTING_UNIT_ID = "
						+ cmbAcc_UnitCode
						+ " and \n"
						+ "                                                  ACCOUNTING_FOR_OFFICE_ID = "
						+ cmbOffice_code
						+ " and \n"
						+ "                                                  CASHBOOK_YEAR = "
						+ txtCB_Year
						+ " and \n"
						+ "                                                  CASHBOOK_MONTH = "
						+ txtCB_Month
						+ " and \n"
						+ "                                                  ACCOUNT_NO = "
						+ cmbBankAccNo
						+ " and \n"
						+ "                                                  doc_type='FR by Office' and \n"
						+ "                                                  twad_or_non_twad='T'                                                 \n"
						+ "                                            )b   \n"
						+ "                                            on \n"
						+ "                                                 b.vc_no = a.r_challan_no and \n"
						+ "                                                 b.rem_date = a.r_challan_date and \n"
						+ "                                                 b.cr_amt = a.cr_amount and \n"
						+ "                                                 b.doc_no = a.r_no  \n"
						+ "                                             where unit_id is null     \n"
						+ "                                           \n"
						+ "                                        \n"
						+ "                                        union all \n"
						+ "                                        \n"
						+ "                                        \n"
						+ "                                        ----- Self Cheque ------\n"
						+ "                                        \n"
						+ "                                        \n"
						+ "                                                select \n"
						+ "                                                  * \n"
						+ "                                                 from \n"
						+ "                                                 ( \n"
						+ "                                                  select   \n"
						+ "                                                      'SC' as doc_type,                  \n"
						+ "                                                                                         \n"
						+ "                                                      m.receipt_no  as r_challan_no,     \n"
						+ "                                                      to_char(m.RECEIPT_DATE,'DD/MM/YYYY') as r_challan_date,  \n"
						+ "                                                                                         \n"
						+ "                                                      m.RECEIPT_NO as r_no,                                                     \n"
						+ "                                                      to_char(m.RECEIPT_DATE,'DD/MM/YYYY')  as r_date,                          \n"
						+ "                                                      t.CHEQUE_OR_DD as r_cheque_or_dd,                                            \n"
						+ "                                                      t.CHEQUE_DD_NO as r_cheque_dd_no, \n"
						+ "                                                      to_char(t.CHEQUE_DD_DATE,'DD/MM/YYYY')  as r_cheque_dd_date,\n"
						+ "                                                      m.TOTAL_AMOUNT as cr_amount ,                                             \n"
						+ "                                                      m.REMARKS as r_particulars,     \n"
						+ "                                                      \n"
						+ "                                                      0 as w_challan_no, \n"
						+ "                                                      null as w_challan_date, \n"
						+ "                                                      0 as w_no, 										   \n"
						+ "                                                      null as w_date,									   \n"
						+ "                                                      null as w_cheque_or_dd, 							   \n"
						+ "                                                      '' as w_cheque_dd_no,								   \n"
						+ "                                                      null as w_cheque_dd_date, \n"
						+ "                                                      0 as dr_amount,   								   \n"
						+ "                                                      null as w_particulars					\n"
						+ "                                                      \n"
						+ "                                                  from    \n"
						+ "                                                      fas_receipt_master m, \n"
						+ "                                                      fas_receipt_transaction t                                                 \n"
						+ "                                                  where    \n"
						+ "                                                      m.ACCOUNTING_UNIT_ID = "
						+ cmbAcc_UnitCode
						+ " and    \n"
						+ "                                                      m.ACCOUNTING_FOR_OFFICE_ID = "
						+ cmbOffice_code
						+ " and    \n"
						+ "                                                      m.CASHBOOK_MONTH = "
						+ txtCB_Month
						+ " and    \n"
						+ "                                                      m.CASHBOOK_YEAR = "
						+ txtCB_Year
						+ " and                       \n"
						+ "                                                      m.ACCOUNT_NO = "
						+ cmbBankAccNo
						+ " and \n"
						+ " ((m.CHEQ_DISHONOUR_STATUS !='Y' or m.CHEQ_DISHONOUR_STATUS is null) \n"
						+ " and												                      \n"
						+ " (t.CHEQ_DISHONOUR_STATUS !='Y' or t.CHEQ_DISHONOUR_STATUS is null)) \n"
						+ " and												                      \n"		
						+ "                                                      m.created_by_module = 'SC' and  \n"
						+ "                                                      m.RECEIPT_STATUS ='L' and                                                  \n"
						+ "                                                      t.accounting_unit_id=m.accounting_unit_id and  \n"
						+ "                                                      t.ACCOUNTING_FOR_OFFICE_ID=m.ACCOUNTING_FOR_OFFICE_ID and  \n"
						+ "                                                      t.CASHBOOK_MONTH =m.CASHBOOK_MONTH and  \n"
						+ "                                                      t.CASHBOOK_YEAR =m.CASHBOOK_YEAR and  \n"
						+ "                                                      t.receipt_no = m.receipt_no  \n"
						+ "                            	                 	) a 													  \n"
						+ "                                                                                \n"
						+ "                                              full join \n"
						+ "                                               \n"
						+ "                                              (                                                \n"
						+ "                                               select \n"
						+ "                                                 accounting_unit_id as unit_id, \n"
						+ "                                                 accounting_for_office_id as office_id, \n"
						+ "                                                 cashbook_month as cbmonth, \n"
						+ "                                                 cashbook_year as cbyear,\n"
						+ "                                                 to_char(remittance_date,'DD/MM/YYYY')    as rem_date, \n"
						+ "                                                 voucher_or_challan_no as vc_no, \n"
						+ "                                                 cr_amount as cr_amt, \n"
						+ "                                                 account_no as acc, \n"
						+ "                                                 doc_no\n"
						+ "                                              from                                                 \n"
						+ "                                                FAS_BRS_OB_TRANSACTION  \n"
						+ "                                              where \n"
						+ "                                                ACCOUNTING_UNIT_ID = "
						+ cmbAcc_UnitCode
						+ " and \n"
						+ "                                                ACCOUNTING_FOR_OFFICE_ID = "
						+ cmbOffice_code
						+ " and \n"
						+ "                                                CASHBOOK_YEAR = "
						+ txtCB_Year
						+ " and \n"
						+ "                                                CASHBOOK_MONTH = "
						+ txtCB_Month
						+ " and \n"
						+ "                                                ACCOUNT_NO = "
						+ cmbBankAccNo
						+ " and \n"
						+ "                                                doc_type='SC' and \n"
						+ "                                                twad_or_non_twad='T'                                                 \n"
						+ "                                            )b   \n"
						+ "                                            on \n"
						+ "                                               b.vc_no = a.r_no and \n"
						+ "                                               b.rem_date = a.r_date and \n"
						+ "                                               b.cr_amt = a.cr_amount and \n"
						+ "                                               b.doc_no = a.r_no  \n"
						+ "                                           where unit_id is null     \n"
						+ "union all\n"
						+ "SELECT *\n"
						+ "FROM\n"
						+ " (SELECT 'IBT'                            AS doc_type,\n"
						+ "  0                                      AS r_challan_no,\n"
						+ "   NULL                                   AS r_challan_date,\n"
						+ "    0                                      AS r_no,\n"
						+ " NULL                                   AS r_date,\n"
						+ " NULL                                   AS r_cheque_or_dd,\n"
						+ " ''                                     AS r_cheque_dd_no,\n"
						+ " NULL                                   AS r_cheque_dd_date,\n"
						+ " 0                                      AS cr_amount ,\n"
						+ " NULL                                   AS r_particulars,\n"
						+ " VOUCHER_NO                                      AS w_challan_no,\n"
						+ " TO_CHAR(DATE_OF_TRANSFER,'DD/MM/YYYY')                                   AS w_challan_date,\n"
						+ " VOUCHER_NO                             AS w_no,\n"
						+ " TO_CHAR(DATE_OF_TRANSFER,'DD/MM/YYYY') AS w_date ,\n"
						+ " CHEQUE_OR_DD                           AS w_cheque_or_dd,\n"
						+ " CHEQUE_DD_NO                           AS w_cheque_dd_no,\n"
						+ " TO_CHAR(cheque_dd_date,'DD/MM/YYYY')   AS w_cheque_dd_date,\n"
						+ " TOTAL_AMOUNT                           AS dr_amount,\n"
						+ " PARTICULARS                            AS w_particulars\n"
						+ " FROM FAS_INTER_BANK_TRF_AT_HO\n"
						+ "  WHERE ACCOUNTING_UNIT_ID     ="+cmbAcc_UnitCode+ " AND " +
						"ACCOUNTING_FOR_OFFICE_ID = "+cmbOffice_code+ " AND CASHBOOK_MONTH           = "+txtCB_Month+ " AND CASHBOOK_YEAR            = "+txtCB_Year+ " AND FROM_ACCOUNT_NO          = "+cmbBankAccNo+ " AND TRANSFER_STATUS          ='L'\n"
						+ " )a\n"
						+ " FULL JOIN\n"
						+ " (SELECT accounting_unit_id              AS unit_id,\n"
						+ " accounting_for_office_id              AS office_id,\n"
						+ " cashbook_month                        AS cbmonth,\n"
						+ " cashbook_year                         AS cbyear,\n"
						+ " TO_CHAR(WITHDRAWAL_DATE,'DD/MM/YYYY') AS w_date,\n"
						+ " voucher_or_challan_no                 AS vc_no,\n"
						+ " dr_amount                             AS dr_amt,\n"
						+ " account_no                            AS acc,\n"
						+ " doc_no\n"
						+ "FROM FAS_BRS_OB_TRANSACTION\n"
						+ "  WHERE ACCOUNTING_UNIT_ID     = "+cmbAcc_UnitCode+ " AND ACCOUNTING_FOR_OFFICE_ID = "+cmbOffice_code+ " AND CASHBOOK_YEAR            = "+txtCB_Year+ " AND CASHBOOK_MONTH           = "+txtCB_Month+ " AND ACCOUNT_NO               = "+cmbBankAccNo+ " AND doc_type                 ='IBT'\n"
						+ " AND twad_or_non_twad         ='T'\n"
						+ " )b\n"
						+ " ON b.vc_no     = a.w_no\n"
						+ " AND b.w_date   = a.w_date\n"
						+ " AND b.dr_amt   = a.dr_amount\n"
						+ " AND b.doc_no   = a.w_no\n"
						+ " WHERE unit_id IS NULL\n"
						+ " UNION ALL\n"
						+ " SELECT *\n"
						+ " FROM\n"
						+ "  (SELECT 'IBT'                            AS doc_type,\n"
						+ "   VOUCHER_NO                                     AS r_challan_no,\n"
						+ "   TO_CHAR(DATE_OF_TRANSFER,'DD/MM/YYYY')                                   AS r_challan_date,\n"
						+ "    VOUCHER_NO                             AS r_no,\n"
						+ "    TO_CHAR(DATE_OF_TRANSFER,'DD/MM/YYYY') AS r_date,\n"
						+ "    CHEQUE_OR_DD                           AS r_cheque_or_dd,\n"
						+ " CHEQUE_DD_NO                           AS r_cheque_dd_no,\n"
						+ "  TO_CHAR(cheque_dd_date,'DD/MM/YYYY')   AS r_cheque_dd_date,\n"
						+ " TOTAL_AMOUNT                           AS cr_amount ,\n"
						+ " PARTICULARS                            AS r_particulars,\n"
						+ " 0                                      AS w_challan_no,\n"
						+ " NULL                                   AS w_challan_date,\n"
						+ " 0                                      AS w_no,\n"
						+ " NULL                                   AS w_date ,\n"
						+ " NULL                                   AS w_cheque_or_dd,\n"
						+ " ''                                     AS w_cheque_dd_no,\n"
						+ " NULL                                   AS w_cheque_dd_date,\n"
						+ " 0                                      AS dr_amount,\n"
						+ " NULL                                   AS w_particulars\n"
						+ " FROM FAS_INTER_BANK_TRF_AT_HO\n"
						+ " WHERE ACCOUNTING_UNIT_ID     = "+cmbAcc_UnitCode+" AND ACCOUNTING_FOR_OFFICE_ID = "+cmbOffice_code+" AND CASHBOOK_MONTH           = "+txtCB_Month+" AND CASHBOOK_YEAR            = "+txtCB_Year+ " AND TO_ACCOUNT_NO            = "+cmbBankAccNo+" AND TRANSFER_STATUS          ='L'\n"
						+ " )a\n"
						+ " FULL JOIN\n"
						+ "  (SELECT accounting_unit_id              AS unit_id,\n"
						+ "    accounting_for_office_id              AS office_id,\n"
						+ "    cashbook_month                        AS cbmonth,\n"
						+ "    cashbook_year                         AS cbyear,\n"
						+ "    TO_CHAR(REMITTANCE_DATE,'DD/MM/YYYY') AS r_date,\n"
						+ "   voucher_or_challan_no                 AS vc_no,\n"
						+ "  cr_amount                             AS cr_amt,\n"
						+ "   account_no                            AS acc,\n"
						+ "    doc_no\n"
						+ "  FROM FAS_BRS_OB_TRANSACTION\n"
						+ "  WHERE ACCOUNTING_UNIT_ID     = "+cmbAcc_UnitCode+ "  AND ACCOUNTING_FOR_OFFICE_ID = "+cmbOffice_code+ "  AND CASHBOOK_YEAR            = "+txtCB_Year+ "  AND CASHBOOK_MONTH           = "+txtCB_Month+" and ACCOUNT_NO               = "+cmbBankAccNo+ "  AND doc_type                 ='IBT'\n"
						+ "  AND twad_or_non_twad         ='T'\n"
						+ "  )b\n"
						+ " ON b.vc_no     = a.r_no\n"
						+ " AND b.r_date   = a.r_date\n"
						+ " AND b.cr_amt   = a.cr_amount\n"
						+ " AND b.doc_no   = a.r_challan_no\n"
						+ " WHERE unit_id IS NULL\n"
						+ "                                       ) order by com_cheque_dd_no,com_doc_date,doc_type,com_doc_no	                    \n"
						+ "                                     )   "
						+ filtersql
						+ "       \n"
						+ "                                   )                    \n"						
						+ "                    order by com_cheque_dd_no,com_doc_date,doc_type,com_doc_no \n"

						+ "                   \n";
System.out.println("sql::::"+sql);
				ps2 = con.prepareStatement(sql);
				rs2 = ps2.executeQuery();	
				int count = 0;
				while (rs2.next()) {

					xml = xml + "<r_challan_no>"
							+ rs2.getString("r_challan_no") + "</r_challan_no>";
					xml = xml + "<r_challan_date>"
							+ rs2.getString("r_challan_date")
							+ "</r_challan_date>";

					xml = xml + "<r_no>" + rs2.getString("r_no") + "</r_no>";
					xml = xml + "<r_date>" + rs2.getString("r_date")
							+ "</r_date>";
					xml = xml + "<r_cheque_or_dd>"
							+ rs2.getString("r_cheque_or_dd")
							+ "</r_cheque_or_dd>";
					xml = xml + "<r_cheque_dd_no>"
							+ rs2.getString("r_cheque_dd_no")
							+ "</r_cheque_dd_no>";
					xml = xml + "<r_cheque_dd_date>"
							+ rs2.getString("r_cheque_dd_date")
							+ "</r_cheque_dd_date>";
					xml = xml + "<cr_amount>" + rs2.getString("cr_amount")
							+ "</cr_amount>";
					xml = xml + "<r_particulars>"
							+ rs2.getString("r_particulars")
							+ "</r_particulars>";

					xml = xml + "<w_challan_no>"
							+ rs2.getString("w_challan_no") + "</w_challan_no>";
					xml = xml + "<w_challan_date>"
							+ rs2.getString("w_challan_date")
							+ "</w_challan_date>";
					xml = xml + "<w_no>" + rs2.getString("w_no") + "</w_no>";
					xml = xml + "<w_date>" + rs2.getString("w_date")
							+ "</w_date>";
					xml = xml + "<w_cheque_or_dd>"
							+ rs2.getString("w_cheque_or_dd")
							+ "</w_cheque_or_dd>";
					xml = xml + "<w_cheque_dd_no>"
							+ rs2.getString("w_cheque_dd_no")
							+ "</w_cheque_dd_no>";
					xml = xml + "<w_cheque_dd_date>"
							+ rs2.getString("w_cheque_dd_date")
							+ "</w_cheque_dd_date>";
					xml = xml + "<dr_amount>" + rs2.getString("dr_amount")
							+ "</dr_amount>";
					xml = xml + "<w_particulars>"
							+ rs2.getString("w_particulars")
							+ "</w_particulars>";

					xml = xml + "<com_doc_no>" + rs2.getString("com_doc_no")
							+ "</com_doc_no>";
					xml = xml + "<com_doc_date>"
							+ rs2.getString("com_doc_date") + "</com_doc_date>";
					xml = xml + "<com_cheque_dd_no>"
							+ rs2.getString("com_cheque_dd_no")
							+ "</com_cheque_dd_no>";

					xml = xml + "<doc_type>" + rs2.getString("doc_type")
							+ "</doc_type>";

					count++;

				}		
				xml = xml + "<flag>success</flag>";
			}catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			
			xml = xml + "</response>";
			System.out.println(xml);
			out.println(xml);
			
		}else if (strCommand.equalsIgnoreCase("LoadGrid")) {

			String CONTENT_TYPE = "text/xml; charset=windows-12" + txtCB_Month
					+ "2";
			response.setContentType(CONTENT_TYPE);
			xml = "<response><command>LoadGrid</command>";
			try {

				ps3 = con
						.prepareStatement("select * from ( select * from FAS_BRS_OB_TRANSACTION where " +
								"ACCOUNTING_UNIT_ID=? and " +
								"ACCOUNTING_FOR_OFFICE_ID=? and " +
								"CASHBOOK_YEAR=? and " +
								"CASHBOOK_MONTH=? and " +
								"ACCOUNT_NO=? )X " +
								"left outer join " +
								"( select PASSBOOK_BALANCE,ACCOUNTING_UNIT_ID as accid," +
								"ACCOUNTING_FOR_OFFICE_ID as offid,CASHBOOK_YEAR as cby," +
								"CASHBOOK_MONTH as cbm,ACCOUNT_NO as accno from FAS_BRS_OB_MASTER " +
								"where ACCOUNTING_UNIT_ID=? and " +
								"ACCOUNTING_FOR_OFFICE_ID=? and " +
								"CASHBOOK_YEAR=? and " +
								"CASHBOOK_MONTH=? and ACCOUNT_NO=? )Y on " +
								"X.ACCOUNTING_UNIT_ID = Y.accid and " +
								"X.ACCOUNTING_FOR_OFFICE_ID = Y.offid and " +
								"X.CASHBOOK_YEAR = Y.cby and " +
								"X.CASHBOOK_MONTH = Y.cbm and " +
								"X.ACCOUNT_NO = Y.accno");

				ps3.setInt(1, cmbAcc_UnitCode);
				ps3.setInt(2, cmbOffice_code);
				ps3.setInt(3, txtCB_Year);
				ps3.setInt(4, txtCB_Month);
				ps3.setLong(5, cmbBankAccNo);
				ps3.setInt(6, cmbAcc_UnitCode);
				ps3.setInt(7, cmbOffice_code);
				ps3.setInt(8, txtCB_Year);
				ps3.setInt(9, txtCB_Month);
				ps3.setLong(10, cmbBankAccNo);
				rs3 = ps3.executeQuery();

				while (rs3.next()) {
					Date r_date1 = rs3.getDate("REMITTANCE_DATE");
					Date EntryDate1 = rs3.getDate("PASSBOOK_DATE");
					Date Doc_Date1 = rs3.getDate("DOC_DATE");

					try {
						Stringdate = r_date1.toString();
					} catch (Exception e) {
						Stringdate = "0000-00-00";
					}
					try {
						Stringdate2 = EntryDate1.toString();
					} catch (Exception e) {
						Stringdate2 = "0000-00-00";
					}

					try {
						Stringdate3 = Doc_Date1.toString();
					} catch (Exception e) {
						Stringdate2 = "0000-00-00";
					}

					String[] ddd = Stringdate.split("-");
					String[] ddd2 = Stringdate2.split("-");
					String[] ddd3 = Stringdate2.split("-");

					int day = Integer.parseInt(ddd[2]);
					int month = Integer.parseInt(ddd[1]);
					int year = Integer.parseInt(ddd[0]);

					int day2 = Integer.parseInt(ddd2[2]);
					int month2 = Integer.parseInt(ddd2[1]);
					int year2 = Integer.parseInt(ddd2[0]);

					int day3 = Integer.parseInt(ddd2[2]);
					int month3 = Integer.parseInt(ddd2[1]);
					int year3 = Integer.parseInt(ddd2[0]);

					if (month >= 10) {
						r_date = (day + "/" + month + "/" + year);
					} else {
						r_date = (day + "/0" + month + "/" + year);
					}

					if (month2 >= 10) {
						EntryDate = (day2 + "/" + month2 + "/" + year2);
					} else {
						EntryDate = (day2 + "/0" + month2 + "/" + year2);
					}

					if (month3 >= 10) {
						Doc_Date = (day3 + "/" + month3 + "/" + year3);
					} else {
						Doc_Date = (day3 + "/0" + month3 + "/" + year3);
					}

					Date w_date1 = rs3.getDate("WITHDRAWAL_DATE");
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
					T_or_NT = rs3.getString("TWAD_OR_NON_TWAD");
					if (T_or_NT.equals("T")) {
						xml = xml + "<r_date>" + r_date + "</r_date>";
						xml = xml + "<w_date>" + w_date + "</w_date>";
						xml = xml + "<doc_date>" + Doc_Date	+ "</doc_date>";
						xml = xml + "<challan_no>"+ rs3.getInt("VOUCHER_OR_CHALLAN_NO")	+ "</challan_no>";
						xml = xml + "<cheque_dd_no>"+ rs3.getInt("CHEQUE_DD_NO")+ "</cheque_dd_no>";
						xml = xml + "<sno>"+ rs3.getInt("SL_NO")+ "</sno>";
						xml = xml + "<cr_amount>" + rs3.getFloat("CR_AMOUNT")+ "</cr_amount>";
						xml = xml + "<dr_amount>" + rs3.getFloat("DR_AMOUNT")+ "</dr_amount>";
						xml = xml + "<EntryFoundInPassBook>"+ rs3.getString("ENTRY_FOUND_IN_PASSBOOK")+ "</EntryFoundInPassBook>";
						xml = xml + "<doc_type>" + rs3.getString("DOC_TYPE")+ "</doc_type>";
						xml = xml + "<doc_no>" + rs3.getString("DOC_NO")+ "</doc_no>";
						xml = xml + "<passBookBalance>" + rs3.getFloat("PASSBOOK_BALANCE")+ "</passBookBalance>";
					} else if (T_or_NT.equals("NT")){
						xml = xml + "<PassbookDate1>" + EntryDate
								+ "</PassbookDate1>";
						xml = xml + "<Particulars1>"
								+ rs3.getString("PARTICULARS")
								+ "</Particulars1>";
						xml = xml + "<Cheqe_or_DDNo1>"
								+ rs3.getInt("CHEQUE_DD_NO")
								+ "</Cheqe_or_DDNo1>";
						xml = xml + "<Details1>" + rs3.getString("DETAILS")
								+ "</Details1>";
						xml = xml + "<CRAmount1>" + rs3.getFloat("CR_AMOUNT")
								+ "</CRAmount1>";
						xml = xml + "<DRAmount1>" + rs3.getFloat("DR_AMOUNT")
								+ "</DRAmount1>";

						xml = xml + "<TypeOfTransaction1>"+ rs3.getString("TRANSACTION_TYPE") + "</TypeOfTransaction1>";
						
						try {

							String sql33 = "SELECT trans_code,  			"
									+ "  trans_short_desc 		"
									+ "FROM fas_brs_transaction_type 	";

							ps4 = con.prepareStatement(sql33);
							rs4 = ps4.executeQuery();

							while (rs4.next()) {
								xml = xml + "<reason_pair>";
								xml = xml + "<reason_code>" + rs4.getString("trans_code")
										+ "</reason_code>";
								xml = xml + "<reason_desc>"
										+ rs4.getString("trans_short_desc")
										+ "</reason_desc>";
								xml = xml + "</reason_pair>";
							}
							xml = xml + "<flag>success</flag>";
						} catch (Exception e) {
							xml = xml + "<flag>failure</flag>";
							System.out.println(e);
							e.printStackTrace();
						}

					}

				}
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				System.out.println(e);
				e.printStackTrace();
			}

			xml = xml + "</response>";
			System.out.println(xml);
			out.println(xml);

		}
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
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
		PreparedStatement ps2 = null;
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
		String txtOprMode = null;
		int txtBankID = 0;
		int txtBranchID = 0;
		float txtPBBalance = 0.0f;

		int x = 0, y = 0, z = 0;
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

		/* filter Flag TWAD */
		String filterFlag = request.getParameter("filterFlag");

		/* filter Flag NON TWAD */
		String filterFlag1 = request.getParameter("filterFlag1");
		
		/* filter Flag for List */
		String filterFlag2 = request.getParameter("filterFlag2");

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

		/* Get Pass Book Balance Amount */
		try {
			txtPBBalance = Float.parseFloat(request
					.getParameter(("txtPBBalance")));
			System.out.println("txtPBBalance:-" + txtPBBalance);
		} catch (Exception e) {
			System.out.println("Error Not Getting Pass Book Balance -->" + e);
		}

		/* User ID */
		String update_user = (String) session.getAttribute("UserId");

		/* Get Time Stamp */
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);

		int RecordCount = 0;

		/* Get Total Number of Transaction in TWAD Transactions */
		try {
			RecordCount = Integer.parseInt(request
					.getParameter("RecordCount"));
			System.out.println("RecordCount:-" + RecordCount);
		} catch (Exception e) {
			System.out
					.println("Error Getting Total Number of Records in TWAD Transaction ");
		}

		/* String Array Declaration */
		String r_date[] = new String[RecordCount];
		String w_date[] = new String[RecordCount];
		String r_w_no[] = new String[RecordCount];
		String ccdd_no[] = new String[RecordCount];
		String doc_type[] = new String[RecordCount];
		String doc_no[] = new String[RecordCount];
		String doc_date[] = new String[RecordCount];
		String slno[] = new String[RecordCount];
		String cr_amount[] = new String[RecordCount];
		String dr_amount[] = new String[RecordCount];
		String EntryFoundInPassBook[] = new String[RecordCount];
		String Entry_Date[] = new String[RecordCount];
		String Delete[] = new String[RecordCount];

		/* Variables Declaration */
		Date r_date2 = null;
		Date w_date2 = null;
		int r_w_no2 = 0;
		int ccdd_no2 = 0;
		String doc_type2 = null;
		int doc_no2 = 0;
		Date doc_date2 = null;
		float cr_amount2 = 0.0f;
		float dr_amount2 = 0.0f;
		float Amt_in_PassBk2 = 0.0f;
		int Amt_Diff2 = 0;
		String cmbReason4Diff2 = "";
		String FollowUpAction2 = "Y";
		String ClearanceEntry2 = "N";
		String EntryFoundInPassBook2 = null;
		String Delete2 = null;
		Date Entry_Date2 = null;
		
		int RecordCount_NT = 0;

		/* Get Total Number of Transaction in NON-TWAD Transactions */
		try {
			RecordCount_NT = Integer.parseInt(request
					.getParameter("RecordCountNT"));
			System.out.println("RecordCount_NT" + RecordCount_NT);
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
		String Delete_NT[] = new String[RecordCount];
		
		/* Variables Declaration */
		Date Entry_Date_NT2 = null;
		String Particualrs_NT2 = "";
		int ChequeNo_NT2 = 0;
		String Details_NT2 = "";
		float cr_amount_NT2 = 0.0f;
		float dr_amount_NT2 = 0.0f;
		String Trans_Type_NT2 = "";
		String Delete2_NT2 = null;
		
		try { // Main Try I
			con.setAutoCommit(false);

			

			PreparedStatement ps5 = con.prepareStatement("select ACCOUNT_NO from FAS_BRS_OB_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=?");
			ps5.setInt(1, cmbAcc_UnitCode);
			ps5.setInt(2, cmbOffice_code);
			ps5.setInt(3, txtCB_Year);
			ps5.setInt(4, txtCB_Month);
			ps5.setLong(5, cmbBankAccNo);
			ResultSet rss = ps5.executeQuery();
			if (rss.next()) {
				ps2 = con
						.prepareStatement("update FAS_BRS_OB_MASTER set ENTRY_DATE=?,BANK_ID=?,BRANCH_ID=?,OPERATIONAL_MODE=?,PASSBOOK_BALANCE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=?");
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
				x = ps2.executeUpdate();
			} else {	
				String sql_insert_mst = "						"
					+ "insert into FAS_BRS_OB_MASTER ( 		"
					+ "  ACCOUNTING_UNIT_ID,				"
					+ "  ACCOUNTING_FOR_OFFICE_ID,		" + "  CASHBOOK_YEAR,					"
					+ "  CASHBOOK_MONTH,					" + "  ENTRY_DATE,						"
					+ "  ACCOUNT_NO,						" + "  BANK_ID,							"
					+ "  BRANCH_ID,						" + "  OPERATIONAL_MODE,				"
					+ "  PASSBOOK_BALANCE,				" + "  UPDATED_BY_USER_ID,				"
					+ "  UPDATED_DATE						" + " ) 								" + " values							"
					+ " ( ?,?,?,?,?,?,?,?,?,?,?,?)		";
				ps2 = con.prepareStatement(sql_insert_mst);
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
				x = ps2.executeUpdate();
			}
			/** --------------------- TWAD Transaction ------------------------- */			
			if ((filterFlag.equals("T")) && (filterFlag2.equals("no"))) {
				System.out.println("twad"+":::no");
				String sd[] = new String[10];
				java.util.Date d = null;
				Calendar c;
				for (int k = 0; k < RecordCount; k++) {

					/* Receipt Date */
					try {
						r_date[k] = request.getParameter("r_date" + k);

						if (!r_date[k].equalsIgnoreCase("")) {
							sd = r_date[k].split("/");

							c = new GregorianCalendar(Integer.parseInt(sd[2]),
									Integer.parseInt(sd[1]) - 1, Integer
											.parseInt(sd[0]));
							d = c.getTime();
							r_date2 = new Date(d.getTime());
						}else
						{
							r_date2 = null;
						}
						
					} catch (Exception e) {
						System.out.println("Error Converting Receipt Date -->"
								+ e);
					}

					/* Withdraw Date */
					try {
						w_date[k] = request.getParameter("w_date" + k);

						if (!w_date[k].equalsIgnoreCase("")) {
							sd = w_date[k].split("/");
							c = new GregorianCalendar(Integer.parseInt(sd[2]),
									Integer.parseInt(sd[1]) - 1, Integer
											.parseInt(sd[0]));
							d = c.getTime();
							w_date2 = new Date(d.getTime());
						}else
						{
							w_date2 = null;
						}
					} catch (Exception e) {
						System.out.println(e);
					}
					/* Receipt or Challan Number */
					try {
						r_w_no[k] = request.getParameter("r_w_no" + k);
						if (!r_w_no[k].equalsIgnoreCase("")) {
						r_w_no2 = Integer.parseInt(r_w_no[k]);
						}else{
							r_w_no2 = 0;
						}
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Cheque or DD Number */
					try {
						ccdd_no[k] = request.getParameter("ccdd_no" + k);
						if (!ccdd_no[k].equalsIgnoreCase("")) {
						ccdd_no2 = Integer.parseInt(ccdd_no[k]);
						}else
						{
							ccdd_no2 = 0;
						}
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Doc type */
					try {
						doc_type[k] = request.getParameter("doc_type" + k);
						doc_type2 = doc_type[k];
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Doc Number */
					try {
						doc_no[k] = request.getParameter("doc_no" + k);
						doc_no2 = Integer.parseInt(doc_no[k]);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Doc Date */
					try {
						doc_date[k] = request.getParameter("doc_date" + k);
						if ((!doc_date[k].equalsIgnoreCase(""))
								&& (doc_date[k] != null)) {
							sd = doc_date[k].split("/");
							c = new GregorianCalendar(Integer.parseInt(sd[2]),
									Integer.parseInt(sd[1]) - 1, Integer
											.parseInt(sd[0]));
							d = c.getTime();
							doc_date2 = new Date(d.getTime());
						} else {
							doc_date2 = null;
						}
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Cr Amount */
					try {
						cr_amount[k] = request.getParameter("cr_amount" + k);
						if (cr_amount[k] != null) {
							if (cr_amount[k].equals("")) {
								cr_amount2 = 0.0f;
							} else {
								cr_amount2 = Float.parseFloat(cr_amount[k]);
							}
						} else {
							cr_amount2 = 0.0f;
						}
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Dr Amount */
					try {
						dr_amount[k] = request.getParameter("dr_amount" + k);
						if (dr_amount[k] != null) {
							if (dr_amount[k].equals("")) {
								dr_amount2 = 0.0f;
							} else {
								dr_amount2 = Float.parseFloat(dr_amount[k]);
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
								.getParameter("EntryFoundInPassBook" + k);
						EntryFoundInPassBook2 = EntryFoundInPassBook[k];
						if (EntryFoundInPassBook2 != null) {
							if (EntryFoundInPassBook2.equals("Y")) {

							} else {
								EntryFoundInPassBook2 = "N";
							}
						} else if (EntryFoundInPassBook2 == "") {
							EntryFoundInPassBook2 = "N";
						} else if (EntryFoundInPassBook2 == null) {
							EntryFoundInPassBook2 = "N";
						} else {
							EntryFoundInPassBook2 = "N";
						}
					} catch (Exception e) {
						System.out.println(e);
					}
					
					/* Amount in Pass Book */
					Amt_in_PassBk2 = cr_amount2-dr_amount2;
					if(Amt_in_PassBk2<0)
					{
						Amt_in_PassBk2=-(Amt_in_PassBk2);
					}
					/* Difference */
					
					/* Reason for Difference */
					/* Follow up action Required */
					/* Clearance Entry */

					int i = 1, i1 = 0;
					try {
						PreparedStatement ps1 = con
								.prepareStatement("Select max(SL_NO) from FAS_BRS_OB_TRANSACTION where TWAD_OR_NON_TWAD='T'");
						ResultSet results2 = ps1.executeQuery();
						if (results2.next()) {
							i1 = results2.getInt(1);
							i = i + i1;

						} else {
							i = i;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					if(EntryFoundInPassBook2.equals("Y"))
					{
						System.out.println("insertion in FAS_BRS_OB_TRANSACTION");
					PreparedStatement ps = con
							.prepareStatement("insert into FAS_BRS_OB_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ENTRY_DATE,SL_NO,TWAD_OR_NON_TWAD,ACCOUNT_NO,REMITTANCE_DATE,WITHDRAWAL_DATE,VOUCHER_OR_CHALLAN_NO,CHEQUE_DD_NO,DOC_NO,DOC_TYPE,DOC_DATE,CR_AMOUNT,DR_AMOUNT,ENTRY_FOUND_IN_PASSBOOK,PASSBOOK_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,AMOUNT_IN_PASSBOOK,DIFFERENCE,REASON_FOR_DIFFERENCE,FOLLOW_UP_ACTION_REQUIRED,CLEARED_BASED_ON_FOLLOWUP) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setInt(3, txtCB_Year);
					ps.setInt(4, txtCB_Month);
					ps.setTimestamp(5, ts);
					ps.setInt(6, i);
					ps.setString(7, "T");
					ps.setLong(8, cmbBankAccNo);
					ps.setDate(9, r_date2);
					ps.setDate(10, w_date2);
					ps.setInt(11, r_w_no2);
					ps.setInt(12, ccdd_no2);
					ps.setInt(13, doc_no2);
					ps.setString(14, doc_type2);
					ps.setDate(15, doc_date2);
					ps.setFloat(16, cr_amount2);
					ps.setFloat(17, dr_amount2);
					ps.setString(18, EntryFoundInPassBook2);
					ps.setDate(19, r_date2);
					ps.setString(20, update_user);
					ps.setTimestamp(21, ts);	
					ps.setFloat(22, Amt_in_PassBk2);
					ps.setInt(23, Amt_Diff2);
					ps.setString(24, cmbReason4Diff2);
					ps.setString(25, FollowUpAction2);
					ps.setString(26, ClearanceEntry2);
					y = ps.executeUpdate();
					}
				}
			}

			/**
			 * -------------------------- NON TWAD Transaction
			 * ---------------------------
			 */

			if ((filterFlag1.equals("NT")) && (filterFlag2.equals("no"))) {
				System.out.println("nontwad"+":::no");
				String sd[] = new String[10];
				java.util.Date d = null;
				Calendar c;

				for (int k = 0; k < RecordCount_NT; k++) {

					/* Entry Date */
					try {
						Entry_Date_NT[k] = request.getParameter("Entry_Date_NT"
								+ k);
						if (!Entry_Date_NT[k].equalsIgnoreCase("")) {
							sd = Entry_Date_NT[k].split("/");
							c = new GregorianCalendar(Integer.parseInt(sd[2]),
									Integer.parseInt(sd[1]) - 1, Integer
											.parseInt(sd[0]));
							d = c.getTime();
							Entry_Date_NT2 = new Date(d.getTime());
						}
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Particulars */
					try {
						Particualrs_NT[k] = request
								.getParameter("Particualrs_NT" + k);
						Particualrs_NT2 = Particualrs_NT[k];
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Cheque Number */
					try {
						ChequeNo_NT[k] = request
								.getParameter("ChequeNo_NT" + k);
						ChequeNo_NT2 = Integer.parseInt(ChequeNo_NT[k]);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Details */
					try {
						Details_NT[k] = request.getParameter("Details_NT" + k);
						Details_NT2 = Details_NT[k];
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Cr Amount */
					try {
						cr_amount_NT[k] = request.getParameter("cr_amount_NT"
								+ k);
						cr_amount_NT2 = Float.parseFloat(cr_amount_NT[k]);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Dr Amount */
					try {
						dr_amount_NT[k] = request.getParameter("dr_amount_NT"
								+ k);
						if (dr_amount_NT[k] != null) {
							if (dr_amount_NT[k].equals("")) {
								dr_amount_NT2 = 0.0f;
							} else {
								dr_amount_NT2 = Float
										.parseFloat(dr_amount_NT[k]);
							}

						} else {
							dr_amount_NT2 = 0.0f;
						}
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Transaction Type */
					try {
						Trans_Type_NT[k] = request.getParameter("Trans_Type_NT"
								+ k);
						Trans_Type_NT2 = Trans_Type_NT[k];
					} catch (Exception e) {
						System.out.println(e);
					}

					int i = 0;
					try {
						ps2 = con.prepareStatement(" select TWAD_OR_NON_TWAD FROM FAS_BRS_OB_TRANSACTION where TWAD_OR_NON_TWAD='NT'");
						ResultSet rss1 = ps2.executeQuery();
						if(rss1.next())
						{
						PreparedStatement ps1 = con
								.prepareStatement("Select max(SL_NO) from FAS_BRS_OB_TRANSACTION where TWAD_OR_NON_TWAD='NT'");
						ResultSet results2 = ps1.executeQuery();
						if (results2.next()) {
							i = results2.getInt(1);
							
							i = i + 1;

						} else {
							i = 0;
						}
						}else
						{
							i = 0;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}

					PreparedStatement pss = con
							.prepareStatement("insert into FAS_BRS_OB_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,ENTRY_DATE,SL_NO,TWAD_OR_NON_TWAD,ACCOUNT_NO,PARTICULARS,CHEQUE_DD_NO,CR_AMOUNT,DR_AMOUNT,PASSBOOK_DATE,TRANSACTION_TYPE,DETAILS,UPDATED_BY_USER_ID,UPDATED_DATE,DOC_NO,DOC_TYPE,DOC_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					pss.setInt(1, cmbAcc_UnitCode);
					pss.setInt(2, cmbOffice_code);
					pss.setInt(3, txtCB_Year);
					pss.setInt(4, txtCB_Month);
					pss.setTimestamp(5, ts);
					pss.setInt(6, i);
					pss.setString(7, "NT");
					pss.setLong(8, cmbBankAccNo);
					pss.setString(9, Particualrs_NT2);
					pss.setInt(10, ChequeNo_NT2);
					pss.setFloat(11, cr_amount_NT2);
					pss.setFloat(12, dr_amount_NT2);
					pss.setDate(13, Entry_Date_NT2);
					pss.setString(14, Trans_Type_NT2);
					pss.setString(15, Details_NT2);
					pss.setString(16, update_user);
					pss.setTimestamp(17, ts);
					pss.setInt(18, 0);
					pss.setString(19, "NA");
					pss.setDate(20, date.valueOf("0000-00-00"));
					z = pss.executeUpdate();
				}
			}
        if(filterFlag2.equals("List"))        	
        		{
        	System.out.println("Listttttttttt");
        		}
        else
        {
        	System.out.println("finally inserted:::::");
			if ((RecordCount > 0) && (RecordCount_NT < 1)) {
				if ((x > 0) && (y > 0)) {
					con.commit();
					sendMessage(response,
							"Records Inserted Succesfully !.... ", "ok");
					System.out.println("Success");
				} else {
					con.rollback();
					sendMessage(response, "Records Not Inserted .... ", "ok");
				}
			} else if ((RecordCount < 1) && (RecordCount_NT > 0)) {
				if ((x > 0) && (z > 0)) {
					con.commit();
					sendMessage(response,
							"Records Inserted Succesfully !!.... ", "ok");
					System.out.println("Success");
				} else {
					con.rollback();
					sendMessage(response, "Records Not Inserted .... ", "ok");
				}
			} else {
				if ((x > 0) && (y > 0) && (z > 0)) {
					con.commit();
					sendMessage(response,
							"Records Inserted Succesfully !!.... ", "ok");
					System.out.println("Success");
				} else {
					con.rollback();
					sendMessage(response, "Records Not Inserted .... ", "ok");
				}
			}
		}

		} catch (Exception e) {
			sendMessage(response, "Records Not Inserted .... " + e, "ok");
			e.printStackTrace();
		}

		
			try
			{
				if( (filterFlag.equals("T")) && (filterFlag2.equals("List")) )
				{
					int slno2=0;
					System.out.println("T and list");
			String sd[] = new String[10];
			java.util.Date d = null;
			Calendar c;			
			for (int k = 0; k < RecordCount; k++) {

				/* Receipt Date */
				try {
					r_date[k] = request.getParameter("r_date" + k);

					if (!r_date[k].equalsIgnoreCase("")) {
						sd = r_date[k].split("/");

						c = new GregorianCalendar(Integer.parseInt(sd[2]),
								Integer.parseInt(sd[1]) - 1, Integer
										.parseInt(sd[0]));
						d = c.getTime();
						r_date2 = new Date(d.getTime());
					}
				} catch (Exception e) {
					System.out.println("Error Converting Receipt Date -->"
							+ e);
				}

				/* Withdraw Date */
				try {
					w_date[k] = request.getParameter("w_date" + k);

					if (!w_date[k].equalsIgnoreCase("")) {
						sd = w_date[k].split("/");
						c = new GregorianCalendar(Integer.parseInt(sd[2]),
								Integer.parseInt(sd[1]) - 1, Integer
										.parseInt(sd[0]));
						d = c.getTime();
						w_date2 = new Date(d.getTime());
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				/* Receipt or Challan Number */
				try {
					r_w_no[k] = request.getParameter("r_w_no" + k);
					r_w_no2 = Integer.parseInt(r_w_no[k]);
				} catch (Exception e) {
					System.out.println(e);
				}

				/* Cheque or DD Number */
				try {
					ccdd_no[k] = request.getParameter("ccdd_no" + k);
					ccdd_no2 = Integer.parseInt(ccdd_no[k]);
				} catch (Exception e) {
					System.out.println(e);
				}

				/* Doc type */
				try {
					doc_type[k] = request.getParameter("doc_type" + k);
					doc_type2 = doc_type[k];
				} catch (Exception e) {
					System.out.println(e);
				}

				/* Doc Number */
				try {
					doc_no[k] = request.getParameter("doc_no" + k);
					doc_no2 = Integer.parseInt(doc_no[k]);
				} catch (Exception e) {
					System.out.println(e);
				}
				//serial no
				try {
					slno[k] = request.getParameter("slno" + k);
					slno2 = Integer.parseInt(slno[k]);
				} catch (Exception e) {
					System.out.println(e);
				}
				
				/* Doc Date */
				try {
					doc_date[k] = request.getParameter("doc_date" + k);
					if ((!doc_date[k].equalsIgnoreCase(""))
							&& (doc_date[k] != null)) {
						sd = doc_date[k].split("/");
						c = new GregorianCalendar(Integer.parseInt(sd[2]),
								Integer.parseInt(sd[1]) - 1, Integer
										.parseInt(sd[0]));
						d = c.getTime();
						doc_date2 = new Date(d.getTime());
					} else {
						doc_date[k] = "00/00/0000";
						sd = doc_date[k].split("/");
						c = new GregorianCalendar(Integer.parseInt(sd[2]),
								Integer.parseInt(sd[1]) - 1, Integer
										.parseInt(sd[0]));
						d = c.getTime();
						doc_date2 = new Date(d.getTime());
					}
				} catch (Exception e) {
					System.out.println(e);
				}

				/* Cr Amount */
				try {
					cr_amount[k] = request.getParameter("cr_amount" + k);
					if (cr_amount[k] != null) {
						if (cr_amount[k].equals("")) {
							cr_amount2 = 0.0f;
						} else {
							cr_amount2 = Float.parseFloat(cr_amount[k]);
						}
					} else {
						cr_amount2 = 0.0f;
					}
				} catch (Exception e) {
					System.out.println(e);
				}

				/* Dr Amount */
				try {
					dr_amount[k] = request.getParameter("dr_amount" + k);
					if (dr_amount[k] != null) {
						if (dr_amount[k].equals("")) {
							dr_amount2 = 0.0f;
						} else {
							dr_amount2 = Float.parseFloat(dr_amount[k]);
						}
					} else {
						dr_amount2 = 0.0f;
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				System.out.println("dramt::::"+dr_amount[k]);
				/* Entry Found in Pass Book */
				try {
					EntryFoundInPassBook[k] = request
							.getParameter("EntryFoundInPassBook" + k);
					EntryFoundInPassBook2 = EntryFoundInPassBook[k];
					if (EntryFoundInPassBook2 != null) {
						if (EntryFoundInPassBook2.equals("Y")) {

						} else {
							EntryFoundInPassBook2 = "N";
						}
					} else if (EntryFoundInPassBook2 == "") {
						EntryFoundInPassBook2 = "N";
					} else if (EntryFoundInPassBook2 == null) {
						EntryFoundInPassBook2 = "N";
					} else {
						EntryFoundInPassBook2 = "N";
					}					
				} catch (Exception e) {
					System.out.println(e);
				}
				System.out.println("EntryFoundInPassBook2:::"+EntryFoundInPassBook2);
				if(EntryFoundInPassBook2.equals("N"))
				{
					PreparedStatement ps = con.prepareStatement("delete from FAS_BRS_OB_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=? and TWAD_OR_NON_TWAD=? and VOUCHER_OR_CHALLAN_NO=? and CHEQUE_DD_NO=? and DOC_NO=? and DOC_TYPE=?");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setInt(3, txtCB_Year);
					ps.setInt(4, txtCB_Month);	
					ps.setLong(5, cmbBankAccNo);
					ps.setString(6, "T");
					ps.setInt(7, r_w_no2);
					ps.setInt(8, ccdd_no2);
					ps.setInt(9, doc_no2);
					ps.setString(10, doc_type2);
					ps.executeUpdate();
					
				}else{
				PreparedStatement ps = con.prepareStatement("update FAS_BRS_OB_TRANSACTION set ENTRY_DATE=?,REMITTANCE_DATE=?,WITHDRAWAL_DATE=?,DOC_DATE=?,CR_AMOUNT=?,DR_AMOUNT=?,ENTRY_FOUND_IN_PASSBOOK=?,PASSBOOK_DATE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=? and TWAD_OR_NON_TWAD=? and VOUCHER_OR_CHALLAN_NO=? and CHEQUE_DD_NO=? and DOC_NO=? and DOC_TYPE=? and SL_NO=?");				
				ps.setTimestamp(1, ts);	
				ps.setDate(2, r_date2);
				ps.setDate(3, w_date2);
				ps.setDate(4, doc_date2);
				ps.setFloat(5, cr_amount2);
				System.out.println("upd dr_amount2:::"+dr_amount2);
				ps.setFloat(6, dr_amount2);
				ps.setString(7, EntryFoundInPassBook2);
				ps.setDate(8, r_date2);
				ps.setString(9, update_user);
				ps.setTimestamp(10, ts);				
				ps.setInt(11, cmbAcc_UnitCode);
				ps.setInt(12, cmbOffice_code);
				ps.setInt(13, txtCB_Year);
				ps.setInt(14, txtCB_Month);	
				ps.setLong(15, cmbBankAccNo);
				ps.setString(16, "T");
				ps.setInt(17, r_w_no2);
				ps.setInt(18, ccdd_no2);
				ps.setInt(19, doc_no2);
				ps.setString(20, doc_type2);
				ps.setInt(21, slno2);
				
				ps.executeUpdate();
				}										
			}
				}
			if( (filterFlag1.equals("NT")) && (filterFlag2.equals("List")) )
			{			
				System.out.println("NT and list");
				for (int k = 0; k < RecordCount_NT; k++) {
					String sd[] = new String[10];
					java.util.Date d = null;
					Calendar c;
					/* Entry Date */
					try {
						Entry_Date_NT[k] = request.getParameter("Entry_Date_NT"
								+ k);
						if (!Entry_Date_NT[k].equalsIgnoreCase("")) {
							sd = Entry_Date_NT[k].split("/");
							c = new GregorianCalendar(Integer.parseInt(sd[2]),
									Integer.parseInt(sd[1]) - 1, Integer
											.parseInt(sd[0]));
							d = c.getTime();
							Entry_Date_NT2 = new Date(d.getTime());
						}
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Particulars */
					try {
						Particualrs_NT[k] = request
								.getParameter("Particualrs_NT" + k);
						Particualrs_NT2 = Particualrs_NT[k];
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Cheque Number */
					try {
						ChequeNo_NT[k] = request
								.getParameter("ChequeNo_NT" + k);
						ChequeNo_NT2 = Integer.parseInt(ChequeNo_NT[k]);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Details */
					try {
						Details_NT[k] = request.getParameter("Details_NT" + k);
						Details_NT2 = Details_NT[k];
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Cr Amount */
					try {
						cr_amount_NT[k] = request.getParameter("cr_amount_NT"
								+ k);
						cr_amount_NT2 = Float.parseFloat(cr_amount_NT[k]);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Dr Amount */
					try {
						dr_amount_NT[k] = request.getParameter("dr_amount_NT"
								+ k);
						if (dr_amount_NT[k] != null) {
							if (dr_amount_NT[k].equals("")) {
								dr_amount_NT2 = 0.0f;
							} else {
								dr_amount_NT2 = Float
										.parseFloat(dr_amount_NT[k]);
							}

						} else {
							dr_amount_NT2 = 0.0f;
						}
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Transaction Type */
					try {
						Trans_Type_NT[k] = request.getParameter("Trans_Type_NT"
								+ k);
						Trans_Type_NT2 = Trans_Type_NT[k];
					} catch (Exception e) {
						System.out.println(e);
					}
					try
					{
						Delete_NT[k] = request.getParameter("Delete_NT" + k);
						Delete2_NT2 = Delete_NT[k];
						if (Delete2_NT2 != null) {
							if (Delete2_NT2.equals("Y")) {

							} else {
								Delete2_NT2 = "N";
							}
						} else if (Delete2_NT2 == "") {
							Delete2_NT2 = "N";
						} else if (Delete2_NT2 == null) {
							Delete2_NT2 = "N";
						} else {
							Delete2_NT2 = "N";
						}
					}catch (Exception e) {
						e.printStackTrace();
					}					
					if(Delete2_NT2.equals("Y"))
					{
						System.out.println("deleteddddddddd");
						PreparedStatement ps = con.prepareStatement("delete from FAS_BRS_OB_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=? and TWAD_OR_NON_TWAD=? and CHEQUE_DD_NO=?");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setInt(3, txtCB_Year);
						ps.setInt(4, txtCB_Month);	
						ps.setLong(5, cmbBankAccNo);
						ps.setString(6, "NT");						
						ps.setInt(7, ChequeNo_NT2);						
						ps.executeUpdate();
						
					}else{	
						PreparedStatement pss = con.prepareStatement("update FAS_BRS_OB_TRANSACTION set ENTRY_DATE=?,PARTICULARS=?,CR_AMOUNT=?,DR_AMOUNT=?,PASSBOOK_DATE=?,TRANSACTION_TYPE=?,DETAILS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and  TWAD_OR_NON_TWAD=? and ACCOUNT_NO =? and CHEQUE_DD_NO=?");				
				
				pss.setTimestamp(1, ts);					
				pss.setString(2, Particualrs_NT2);				
				pss.setFloat(3, cr_amount_NT2);
				pss.setFloat(4, dr_amount_NT2);
				pss.setDate(5, Entry_Date_NT2);
				pss.setString(6, Trans_Type_NT2);
				pss.setString(7, Details_NT2);
				pss.setString(8, update_user);
				pss.setTimestamp(9, ts);				
				pss.setInt(10, cmbAcc_UnitCode);
				pss.setInt(11, cmbOffice_code);
				pss.setInt(12, txtCB_Year);
				pss.setInt(13, txtCB_Month);
				pss.setString(14, "NT");
				pss.setLong(15, cmbBankAccNo);
				pss.setInt(16, ChequeNo_NT2);
				x=pss.executeUpdate();
					}				

				}				
			}
			if(x>0)
			{
			con.commit();
			sendMessage(response,
					"Records Updated Succesfully !.... ", "ok");
			}else{
				con.rollback();
				sendMessage(response,"Records Not Updated .... ","ok");
			}
			}catch (Exception e) {
				try {
					con.rollback();
					sendMessage(response,"Records Not Updated .... ","ok");
				} catch (Exception e1) {					
					e1.printStackTrace();
				}
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
		}
	}
}
