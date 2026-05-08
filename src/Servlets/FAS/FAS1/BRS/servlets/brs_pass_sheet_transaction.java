package Servlets.FAS.FAS1.BRS.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

public class brs_pass_sheet_transaction extends HttpServlet {
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";

	public void doGet(HttpServletRequest request, HttpServletResponse response)
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
		Connection con = null;
		PreparedStatement ps2 = null,prep=null;
		PreparedStatement ps3 = null;
		PreparedStatement ps4 = null,pre=null,pre1=null;
		PreparedStatement ps5 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;
		ResultSet rs4 = null;
		ResultSet rs5 = null,resul=null;
		ResultSet rs6 = null,re=null,re1=null;

		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

		String strCommand = "";
		double br_cr_amount=0.0,ft_amount=0.0,coll=0.0;
		double payment_amt=0.0,ft_amount_opr=0.0,opr=0.0;
		 BigDecimal ii  = null,i2=null;
		 double crAmount = 0,drAmount=0;
		   NumberFormat obj= new DecimalFormat("#0.00");
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
		String cmbBankAc="";
		int cmbAcc_UnitCode = 0;
		int cmbOffice_code = 0;
		int txtCB_Year = 0;
		int txtCB_Month = 0;
		long cmbBankAccNo = 0;
		String Filter = "";
		String mode_id="";
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
			cmbBankAccNo =Long.parseLong(request.getParameter("cmbBankAccNo"));
			System.out.println("cmbBankAccNo-->" + cmbBankAccNo);
		} catch (Exception e) {
			System.out.println("Error Not Getting Bank Account Number -->" + e);
		}
		
		try {
			cmbBankAc = request.getParameter("splAccNum");
			System.out.println("cmbBankAc:::"+cmbBankAc);
		} catch (Exception e) {
			System.out.println("Error Not Getting Bank Account Number text -->" + e);
		}

		/** Get Filtering Type */
		try {
			Filter = request.getParameter("Filter");
			System.out.println("Filter-->" + Filter);
		} catch (Exception e) {
			System.out.println("Error Not Getting Filtering Type -->" + e);
		}
		String FromDate = null;
		String ToDate = null;
		if (strCommand.equalsIgnoreCase("LoadTWADTransactions")
				|| strCommand.equalsIgnoreCase("TotalTransactions")) {
			String CONTENT_TYPE = "text/xml; charset=windows-12" + txtCB_Month
					+ "2";
			response.setContentType(CONTENT_TYPE);
			String xml = "";

			/* Get Display Sequence List */
			int ListSeq_S = 0;
			int ListSeq_E = 0;

			/* Get Filter Type and Filter Value */
			String FilterType = "";
			String FilterValue = "";
			String filtersql = "";
			String filtersql5 = "";
			try {
				FilterType = request.getParameter("Filter");
				FilterValue = request.getParameter("FilterValue");
			} catch (Exception e) {
				System.out.println(e);
			}

			/* Filter SQL Query Formation */
			if (FilterType.equalsIgnoreCase("ChequeNoWise")) {
				filtersql = "where com_cheque_dd_no = '" + FilterValue + "'";
				filtersql5 = "where com_cheque_dd_no != '" + FilterValue + "'";
			} else if (FilterType.equalsIgnoreCase("DocTypeWise")) {
				filtersql = "where doc_type = '" + FilterValue + "'";
				filtersql5 = "where doc_type != '" + FilterValue + "'";
			} else if (FilterType == "NORMAL") {
				filtersql = "";
				filtersql5 = "";
			} else if (FilterType.equalsIgnoreCase("DateWise")) {

				String txtFromDate = request.getParameter("txtFromDate");
				String txtToDate = request.getParameter("txtToDate");

				String m = null;
				String m1 = null;
				String[] ddd = txtFromDate.split("/");
				String[] ddd1 = txtToDate.split("/");

				String day = ddd[0];
				int month = Integer.parseInt(ddd[1]);
				String year = (ddd[2]).substring(2);

				String day1 = ddd1[0];
				int month1 = Integer.parseInt(ddd1[1]);
				String year1 = (ddd1[2]).substring(2);

				if (month == 01) {
					m = "JAN";
				} else if (month == 02) {
					m = "FEB";
				} else if (month == 03) {
					m = "MAR";
				} else if (month == 04) {
					m = "APR";
				} else if (month == 05) {
					m = "MAY";
				} else if (month == 06) {
					m = "JUN";
				} else if (month == 07) {
					m = "JUL";
				} else if (month == 8) {
					m = "AUG";
				} else if (month == 9) {
					m = "SEP";
				} else if (month == 10) {
					m = "OCT";
				} else if (month == 11) {
					m = "NOV";
				} else if (month == 12) {
					m = "DEC";
				}

				if (month1 == 01) {
					m1 = "JAN";
				} else if (month1 == 02) {
					m1 = "FEB";
				} else if (month1 == 03) {
					m1 = "MAR";
				} else if (month1 == 04) {
					m1 = "APR";
				} else if (month1 == 05) {
					m1 = "MAY";
				} else if (month1 == 06) {
					m1 = "JUN";
				} else if (month1 == 07) {
					m1 = "JUL";
				} else if (month1 == 8) {
					m1 = "AUG";
				} else if (month1 == 9) {
					m1 = "SEP";
				} else if (month1 == 10) {
					m1 = "OCT";
				} else if (month1 == 11) {
					m1 = "NOV";
				} else if (month1 == 12) {
					m1 = "DEC";
				}

				FromDate = (day + "-" + m + "-" + year);
				ToDate = (day1 + "-" + m1 + "-" + year1);

				filtersql = "where decode(w_date,null,r_date,w_date) >= '"
						+ FromDate
						+ "' and decode(r_date,null,w_date,r_date) <= '"
						+ ToDate + "'";

				filtersql5 = "where decode(w_date,null,r_date,w_date) <= '"
						+ FromDate
						+ "' or decode(r_date,null,w_date,r_date) >= '"
						+ ToDate + "'";

			}

			/* Display Sequence */
			try {
				ListSeq_S = Integer.parseInt(request.getParameter("ListSeq"));
				ListSeq_E = ListSeq_S + 9;
			} catch (Exception e) {
				System.out.println(e);
			}
			
			try
			{
				PreparedStatement psss=con.prepareStatement("select trim(AC_OPERATIONAL_MODE_ID)as mode_id from fas_mst_bank_balance" +
						" where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and BANK_AC_NO="+cmbBankAccNo+" and STATUS='Y'");
				
				ResultSet res=psss.executeQuery();
				if(res.next())
				{
					mode_id=res.getString("mode_id");
				}
			}
			catch(Exception ex)
			{
				System.out.println("excep in mode_id::"+ex);
			}
			

			xml = "<response><command>LoadTWADTransactions</command>";

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
						+ "                                                   m.receipt_no = t.receipt_no   and m.receipt_type='B'                                \n"
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
						+ "                                                fas_brs_transaction  \n"
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
						+ "and                                                                    PAYMENT_STATUS ='L' "
						+ " and												                       \n"
						+ "                                                                       ACCOUNT_NO in  ( "
						+ cmbBankAccNo
						+ " )  and		  \n"
						+ "                                                                       created_by_module = 'CRM'   \n"
						+ "                                                                   )X   \n"
						+ "                                                                      \n"
						+ "                                                                   left outer join   \n"
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
						+ "   and 				    \n"
						+ "                                                                          m.RECEIPT_STATUS !='C'"
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
						+ "                                                                fas_brs_transaction   \n"
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
						+ " and	m.PAYMENT_STATUS ='L' and 											                      \n"
						+ "                            	                    m.ACCOUNT_NO in  ( "
						+ cmbBankAccNo
						+ " )  and										   \n"
						+ " (t.CHEQ_CANCEL_STATUS !='Y' or t.CHEQ_CANCEL_STATUS is null) \n"
						+ " and												                      \n"
						+ " (m.CHEQ_CANCEL_STATUS !='Y' or m.CHEQ_CANCEL_STATUS is null) \n"
						+ " and												                      \n"
						+ "                                                  m.created_by_module !='CRM' and  \n"
						+ "                                                  m.accounting_unit_id = t.accounting_unit_id and 					   \n"
						+ "                            	                    m.accounting_for_office_id = t.accounting_for_office_id and 		   \n"
						+ "                            	                    m.cashbook_month =t.cashbook_month and 								   \n"
						+ "                            	                    m.cashbook_year = t.cashbook_year and 								   \n"
						+ "                            	                    m.voucher_no = t.voucher_no				                                                     \n"
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
						+ "                                                 CHEQUE_DD_NO as vc_no, \n"
						+ "                                                 dr_amount as dr_amt, \n"
						+ "                                                 account_no as acc, \n"
						+ "                                                 doc_no\n"
						+ "                                              from                                                 \n"
						+ "                                                fas_brs_transaction  \n"
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
						+ "                                               b.vc_no = a.w_cheque_dd_no and \n"
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
						+ " and                       \n"
						+ " (CHEQ_CANCEL_STATUS !='Y' or CHEQ_CANCEL_STATUS is null) \n"
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
						+ "                                                fas_brs_transaction  \n"
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
						+ " and                       \n"
						+ " (CHEQ_CANCEL_STATUS !='Y' or CHEQ_CANCEL_STATUS is null) \n"
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
						+ "                                                 fas_brs_transaction  \n"
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
						+ "                                                fas_brs_transaction  \n"
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
						+ "                                                  fas_brs_transaction  \n"
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
						+ "                                                fas_brs_transaction  \n"
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
						+ "FROM fas_brs_transaction\n"
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
						+ "   TO_CHAR(REMITTANCE_DATE,'DD/MM/YYYY') AS r_date,\n"
						+ "   voucher_or_challan_no                 AS vc_no,\n"
						+ "  cr_amount                             AS cr_amt,\n"
						+ "   account_no                            AS acc,\n"
						+ "    doc_no\n"
						+ "  FROM fas_brs_transaction\n"
						+ "  WHERE ACCOUNTING_UNIT_ID     = "+cmbAcc_UnitCode+ "  AND ACCOUNTING_FOR_OFFICE_ID = "+cmbOffice_code+ "  AND CASHBOOK_YEAR            = "+txtCB_Year+ "  AND CASHBOOK_MONTH           = "+txtCB_Month+" and ACCOUNT_NO               = "+cmbBankAccNo+ "  AND doc_type                 ='IBT'\n"
						+ "  AND twad_or_non_twad         ='T'\n"
						+ "  )b\n"
						+ " ON b.vc_no     = a.r_no\n"
						+ " AND b.r_date   = a.r_date\n"
						+ " AND b.cr_amt   = a.cr_amount\n"
						+ " AND b.doc_no   = a.r_challan_no\n"
						+ " WHERE unit_id IS NULL\n"
						+ "                                       ) order by com_doc_date,doc_type,com_doc_no	                    \n"
						+ "                                     )   "
						+ filtersql
						+ "       \n"
						+ "                                   )                    \n"						
					//	+ "                   order by com_doc_date,doc_type,com_doc_no \n"
					+ "                   order by com_doc_date,com_cheque_dd_no \n"

						+ "                   \n";

				// ===========================================================================================================
				System.out.println("ravi sql : "+sql);
				

				if (strCommand.equalsIgnoreCase("LoadTWADTransactions")) {
					sql = sql;
				//	sql1 = sql1;
				}

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
					xml = xml + "<cr_amount>" + rs2.getString("cr_amount")+ "</cr_amount>";
					  crAmount += rs2.getDouble("cr_amount");
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
					xml = xml + "<dr_amount>" + rs2.getString("dr_amount")+ "</dr_amount>";
					 drAmount += rs2.getDouble("dr_amount");
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
				xml = xml + "<crAmount>"+obj.format(crAmount)+"</crAmount>";
				xml = xml + "<drAmount>"+obj.format(drAmount)+"</drAmount>";
				String sql22 = "SELECT							" + "	TRANS_CODE,			        "
						+ "	TRANS_DESC			" + "FROM 					"
						+ "	FAS_BRS_TRANSACTION_TYPE 		";

				ps3 = con.prepareStatement(sql22);
				rs3 = ps3.executeQuery();

				while (rs3.next()) {
					xml = xml + "<reason_pair>";
					xml = xml + "<reason_code>" + rs3.getString("TRANS_CODE")
							+ "</reason_code>";
					xml = xml + "<reason_desc>"
							+ rs3.getString("TRANS_DESC")
							+ "</reason_desc>";
					xml = xml + "</reason_pair>";
				}
				//col
				System.out.println("cmbBankAc:::"+cmbBankAc+"::::");
				System.out.println("mode_id:::"+mode_id);
			//	if(cmbBankAc.equals("COL"))
				if(mode_id.equals("COL"))
				{
					
					try
					{
					pre=con.prepareStatement("SELECT COUNT(RECEIPT_NO) AS total_count,  "+
									"    SUM(TOTAL_AMOUNT)       AS totAmt"+
							"   FROM FAS_RECEIPT_MASTER"+
							" 	  WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+
							" 	  AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+
							" 	  AND CASHBOOK_YEAR           ="+txtCB_Year+
							" 	  And Cashbook_Month          ="+txtCB_Month+
							" 	  And Created_By_Module       In ('CR','BR')"+
							" 	  And Receipt_Status!         ='C'");	
					re=pre.executeQuery();
						while(re.next())
						{
							br_cr_amount=re.getDouble("totAmt");
						}
						System.out.println("br_cr_amount:::"+br_cr_amount);
						pre1=con.prepareStatement("SELECT COUNT(VOUCHER_NO) AS total_count, "+
						        "    SUM(TOTAL_AMOUNT)       AS totAmt "+
								"   FROM FAS_FUND_TRF_FROM_OFFICE "+
								" WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+
								" AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+
								" AND CASHBOOK_YEAR           ="+txtCB_Year+
								" AND CASHBOOK_MONTH          ="+txtCB_Month+
								" AND TRANSFER_STATUS!        ='C'");	
				re1=pre1.executeQuery();
					while(re1.next())
					{
						ft_amount=re1.getDouble("totAmt");
					}
					System.out.println("ft_amount:::"+ft_amount);
					
					coll=br_cr_amount-ft_amount;
					xml = xml + "<vmcount>" + coll + "</vmcount>";
					}
					catch(Exception e)
					{
						System.out.println("exc in collection:::"+e);
						xml = xml + "<vmcount>" + "0.0" + "</vmcount>";
					}
				}
				//else if(cmbBankAc.equals("OPR"))
				else if(mode_id.equals("OPR"))
				{
					//System.out.println("oper:::");
					try{
		             String sql_one="select sum(totAmt)as total_amt from(SELECT SUM(TOTAL_AMOUNT)       AS totAmt\n" + 
		    	             "  FROM FAS_PAYMENT_MASTER\n" + 
		    	             "  WHERE ACCOUNTING_UNIT_ID    =" +cmbAcc_UnitCode+ 
		    	             "  AND ACCOUNTING_FOR_OFFICE_ID=" +cmbOffice_code+ 
		    	             "  AND CASHBOOK_YEAR           =" +txtCB_Year+ 
		    	             "  And Cashbook_Month          =" +txtCB_Month+ 
		    	             "  AND CREATED_BY_MODULE      in('BPF','BPP')\n" + 
		    	             "  And Payment_Status!         ='C'\n" + 
		    	             "  \n" + 
		    	             "  union all\n" + 
		    	             "  \n" + 
		    	             "  SELECT SUM(TOTAL_AMOUNT)       AS totAmt\n" + 
		    	             "  FROM FAS_FUND_RECEIPT_BY_OFFICE\n" + 
		    	             "  WHERE ACCOUNTING_UNIT_ID    =" +cmbAcc_UnitCode+ 
		    	             "  And Accounting_For_Office_Id=" +cmbOffice_code+ 
		    	             "  AND CASHBOOK_YEAR           =" +txtCB_Year+ 
		    	             "  And Cashbook_Month          =" +txtCB_Month+ 
		    	             "  AND RECEIPT_STATUS!         ='C')\n" + 
		    	             "  ";
		             prep=con.prepareStatement(sql_one);
		             resul=prep.executeQuery();
		             while(resul.next())
		             {
		            	 payment_amt= resul.getDouble("total_amt");
		            	//payment_amt= resul.getDouble("total_amt");
		             }
		             System.out.println("payment_amt:::"+payment_amt);
		             
		             pre1=con.prepareStatement("SELECT COUNT(VOUCHER_NO) AS total_count, "+
						        "    SUM(TOTAL_AMOUNT)       AS totAmt "+
								"   FROM FAS_FUND_TRF_FROM_OFFICE "+
								" WHERE ACCOUNTING_UNIT_ID    ="+cmbAcc_UnitCode+
								" AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+
								" AND CASHBOOK_YEAR           ="+txtCB_Year+
								" AND CASHBOOK_MONTH          ="+txtCB_Month+
								" AND TRANSFER_STATUS!        ='C'  and Office_Account_No=4181");	
				re1=pre1.executeQuery();
					while(re1.next())
					{
						ft_amount_opr=re1.getDouble("totAmt");
					}
					System.out.println("ft_amount:::"+ft_amount);
					
					opr=payment_amt-ft_amount_opr;
					   ii = new BigDecimal(opr);
					xml = xml + "<vmcount>" + ii + "</vmcount>";
					}
					catch(Exception ee)
					{
					System.out.println("exc in oper::::"+ee);
					xml = xml + "<vmcount>" + "0.0" + "</vmcount>";
					}
				}

				if (strCommand.equalsIgnoreCase("TotalTransactions")) {
					xml = "<response><command>TotalTransactions</command>";
					xml = xml + "<TotalTrans>" + count + "</TotalTrans>";
					xml = xml + "<FilterType>" + FilterType + "</FilterType>";

				}

				if (FilterType.equals("NORMAL")) {
					xml = xml + "<filterFlag>no</filterFlag>";
				} 
				else {
					
					String sql1 = "                  select * from \n"
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
						+ "                                                   m.receipt_no = t.receipt_no                                   \n"
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
						+ "                                                fas_brs_transaction  \n"
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
						+ "and                                                                        PAYMENT_STATUS ='L' "
						+ " and												                       \n"
						+ "                                                                       ACCOUNT_NO in  ( "
						+ cmbBankAccNo
						+ " )  and		  \n"
						+ "                                                                       created_by_module = 'CRM'   \n"
						+ "                                                                   )X   \n"
						+ "                                                                      \n"
						+ "                                                                   left outer join   \n"
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
						+ "   and 				    \n"
						+ "                                                                          m.RECEIPT_STATUS !='C'"
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
						+ "                                                                fas_brs_transaction   \n"
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
						+ " and	m.PAYMENT_STATUS ='L' and 											                      \n"
						+ "                            	                    m.ACCOUNT_NO in  ( "
						+ cmbBankAccNo
						+ " )  and										   \n"
						+ " (t.CHEQ_CANCEL_STATUS!='Y' or t.CHEQ_CANCEL_STATUS is null) \n"
						+ " and												                      \n"
						+ " (m.CHEQ_CANCEL_STATUS !='Y' or m.CHEQ_CANCEL_STATUS is null) \n"
						+ " and												                      \n"
						+ "                                                  m.created_by_module !='CRM' and  \n"
						+ "                                                  m.accounting_unit_id = t.accounting_unit_id and 					   \n"
						+ "                            	                    m.accounting_for_office_id = t.accounting_for_office_id and 		   \n"
						+ "                            	                    m.cashbook_month =t.cashbook_month and 								   \n"
						+ "                            	                    m.cashbook_year = t.cashbook_year and 								   \n"
						+ "                            	                    m.voucher_no = t.voucher_no				                                                     \n"
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
						+ "                                                fas_brs_transaction  \n"
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
						+ " and                       \n"
						+ " (CHEQ_CANCEL_STATUS !='Y' or CHEQ_CANCEL_STATUS is null) \n"
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
						+ "                                                fas_brs_transaction  \n"
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
						+ " and                       \n"
						+ " (CHEQ_CANCEL_STATUS !='Y' or CHEQ_CANCEL_STATUS is null) \n"
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
						+ "                                                 fas_brs_transaction  \n"
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
						+ "                                                fas_brs_transaction  \n"
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
						+ "                                                  fas_brs_transaction  \n"
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
						+ " and                       \n"
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
						+ "                                                fas_brs_transaction  \n"
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
						+ " VOUCHER_NO                                    AS w_challan_no,\n"
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
						+ "FROM fas_brs_transaction\n"
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
						+ "    TO_CHAR(WITHDRAWAL_DATE,'DD/MM/YYYY') AS w_date,\n"
						+ "   voucher_or_challan_no                 AS vc_no,\n"
						+ "  cr_amount                             AS cr_amt,\n"
						+ "   account_no                            AS acc,\n"
						+ "    doc_no\n"
						+ "  FROM fas_brs_transaction\n"
						+ "  WHERE ACCOUNTING_UNIT_ID     = "+cmbAcc_UnitCode+ "  AND ACCOUNTING_FOR_OFFICE_ID = "+cmbOffice_code+ "  AND CASHBOOK_YEAR            = "+txtCB_Year+ "  AND CASHBOOK_MONTH           = "+txtCB_Month+" and ACCOUNT_NO               = "+cmbBankAccNo+ "  AND doc_type                 ='IBT'\n"
						+ "  AND twad_or_non_twad         ='T'\n"
						+ "  )b\n"
						+ " ON b.vc_no     = a.r_no\n"
						+ " AND b.w_date   = a.r_date\n"
						+ " AND b.cr_amt   = a.cr_amount\n"
						+ " AND b.doc_no   = a.w_no\n"
						+ " WHERE unit_id IS NULL\n"
						+ "                                       ) order by com_doc_date,doc_type,com_doc_no	                    \n"
						+ "                                     )   "
						+ filtersql5
						+ "       \n"
						+ "                                   )                    \n"						
				//		+ "                   order by com_doc_date,doc_type,com_doc_no \n"
				+ "                   order by com_doc_date,r_cheque_dd_no \n"

						+ "                   \n";
					
					xml = xml + "<filterFlag>yes</filterFlag>";
					ps4 = con.prepareStatement(sql1);
					rs5 = ps4.executeQuery();
					int count1 = 0;

					while (rs5.next()) {

						xml = xml + "<r_challan_no1>"
								+ rs5.getString("r_challan_no")
								+ "</r_challan_no1>";
						xml = xml + "<r_challan_date1>"
								+ rs5.getString("r_challan_date")
								+ "</r_challan_date1>";

						xml = xml + "<r_no1>" + rs5.getString("r_no")
								+ "</r_no1>";
						xml = xml + "<r_date1>" + rs5.getString("r_date")
								+ "</r_date1>";
						xml = xml + "<r_cheque_or_dd1>"
								+ rs5.getString("r_cheque_or_dd")
								+ "</r_cheque_or_dd1>";
						xml = xml + "<r_cheque_dd_no1>"
								+ rs5.getString("r_cheque_dd_no")
								+ "</r_cheque_dd_no1>";
						xml = xml + "<r_cheque_dd_date1>"
								+ rs5.getString("r_cheque_dd_date")
								+ "</r_cheque_dd_date1>";
						xml = xml + "<cr_amount1>" + rs5.getString("cr_amount")
								+ "</cr_amount1>";
						xml = xml + "<r_particulars1>"
								+ rs5.getString("r_particulars")
								+ "</r_particulars1>";

						xml = xml + "<w_challan_no1>"
								+ rs5.getString("w_challan_no")
								+ "</w_challan_no1>";
						xml = xml + "<w_challan_date1>"
								+ rs5.getString("w_challan_date")
								+ "</w_challan_date1>";
						xml = xml + "<w_no1>" + rs5.getString("w_no")
								+ "</w_no1>";
						xml = xml + "<w_date1>" + rs5.getString("w_date")
								+ "</w_date1>";
						xml = xml + "<w_cheque_or_dd1>"
								+ rs5.getString("w_cheque_or_dd")
								+ "</w_cheque_or_dd1>";
						xml = xml + "<w_cheque_dd_no1>"
								+ rs5.getString("w_cheque_dd_no")
								+ "</w_cheque_dd_no1>";
						xml = xml + "<w_cheque_dd_date1>"
								+ rs5.getString("w_cheque_dd_date")
								+ "</w_cheque_dd_date1>";
						xml = xml + "<dr_amount1>" + rs5.getString("dr_amount")
								+ "</dr_amount1>";
						xml = xml + "<w_particulars1>"
								+ rs5.getString("w_particulars")
								+ "</w_particulars1>";

						xml = xml + "<com_doc_no1>"
								+ rs5.getString("com_doc_no")
								+ "</com_doc_no1>";
						xml = xml + "<com_doc_date1>"
								+ rs5.getString("com_doc_date")
								+ "</com_doc_date1>";
						xml = xml + "<com_cheque_dd_no1>"
								+ rs5.getString("com_cheque_dd_no")
								+ "</com_cheque_dd_no1>";

						xml = xml + "<doc_type1>" + rs5.getString("doc_type")
								+ "</doc_type1>";

						count1++;

					}
				}
				xml = xml + "<flag>success</flag>";

			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				System.out.println(e);
				e.printStackTrace();
			}

			xml = xml + "</response>";
			//System.out.println(xml);
			out.println(xml);

		}

		else if (strCommand.equalsIgnoreCase("LoadNONTWADTransactions")) {

			String CONTENT_TYPE = "text/xml; charset=windows-12" + txtCB_Month
					+ "2";
			response.setContentType(CONTENT_TYPE);
			String xml = "";
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

	}

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
		String txtOprMode = null;
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

		/* User ID */
		String update_user = (String) session.getAttribute("UserId");

		/* Get Time Stamp */
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
		try {
			PreparedStatement ps = con.prepareStatement(" select TB_STATUS,STATUS from(SELECT TB_STATUS,"
							+ "ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH "
							+ "FROM FAS_TRIAL_BALANCE_STATUS WHERE ACCOUNTING_UNIT_ID    =? AND "
							+ "CASHBOOK_YEAR           =? "
							+ "AND CASHBOOK_MONTH          =? )a left outer join (select ACCOUNTING_UNIT_ID as accid,"
							+ "ACCOUNTING_FOR_OFFICE_ID as accoffid,CASHBOOK_YEAR as cby,CASHBOOK_MONTH as cbm,STATUS "
							+ "FROM FAS_BRS_MONTHLY_CLOSURE WHERE ACCOUNTING_UNIT_ID    =? AND ACCOUNTING_FOR_OFFICE_ID=? "
							+ "AND CASHBOOK_YEAR           =? AND CASHBOOK_MONTH          =? )b on "
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
								try {
									String sql_insert_mst = "						"
											+ "insert into fas_brs_master ( 		"
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
											+ "  UPDATED_DATE						"
											+ " ) 								" + " values							"
											+ " ( ?,?,?,?,?,?,?,?,?,?,?,?)		";

									PreparedStatement ps5 = con
											.prepareStatement("select ACCOUNT_NO from fas_brs_master where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=?");
									ps5.setInt(1, cmbAcc_UnitCode);
									ps5.setInt(2, cmbOffice_code);
									ps5.setInt(3, txtCB_Year);
									ps5.setInt(4, txtCB_Month);
									ps5.setLong(5, cmbBankAccNo);
									ResultSet rss = ps5.executeQuery();
									if (rss.next()) {
										ps2 = con.prepareStatement("update fas_brs_master set ENTRY_DATE=?,BANK_ID=?,BRANCH_ID=?,OPERATIONAL_MODE=?,PASSBOOK_BALANCE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and ACCOUNT_NO=?");
										ps2.setTimestamp(1, ts);
										ps2.setInt(2, txtBankID);
										ps2.setInt(3, txtBranchID);
										ps2.setString(4, txtOprMode);
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

								String sd[] = new String[10];
								java.util.Date d = null;
								Calendar c;
								
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
										
											String IS_IT_CLEARING="";
											cs = con.prepareCall("call FAS_BRS_PROCEDURE(?::numeric,?::numeric,?::numeric,?::numeric,?,?,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?::numeric,?::numeric,?,?,?,?,?,?::numeric,?,?::numeric,?,?::numeric,?)");
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
											cs.registerOutParameter(24,java.sql.Types.NUMERIC);
											cs.setNull(24, java.sql.Types.NUMERIC);
											cs.setString(25, "INSERT");
											cs.executeQuery();
											//int errcode = cs.getInt(24);
											int errcode = cs.getBigDecimal(24).intValue();

												if (errcode != 0) {
													con.rollback();
													con.setAutoCommit(true);
													sendMessage(
															response,
															"TWAD Transaction Records Not Inserted ............ ",
															"ok");
	
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
									try {
										System.out.println(request
												.getParameter("RecordCountNT"));
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

										/* Cr Amount */
										
										
										
										try {
											cr_amount_NT[k] = request.getParameter("cr_amount_NT"+ k);
											if (cr_amount_NT[k] != null) {
												if (cr_amount_NT[k].equals("")) {
													cr_amount_NT2 = 0.0f;
												} else {
													cr_amount_NT2 = Float.parseFloat(cr_amount_NT[k]);
												}

											} else {
												cr_amount_NT2 = 0.0f;
											}
										} catch (Exception e) {
											System.out.println(e);
										}

										/* Dr Amount */
										try {
											dr_amount_NT[k] = request.getParameter("dr_amount_NT"+ k);
											if (dr_amount_NT[k] != null) {
												if (dr_amount_NT[k].equals("")) {
													dr_amount_NT2 = 0.0f;
												} else {
													dr_amount_NT2 = Float.parseFloat(dr_amount_NT[k]);
												}

											} else {
												dr_amount_NT2 = 0.0f;
											}
										} catch (Exception e) {
											System.out.println(e);
										}

										/* Transaction Type */
										try {
											Trans_Type_NT[k] = request
													.getParameter("Trans_Type_NT"
															+ k);
											Trans_Type_NT2 = Trans_Type_NT[k];
										} catch (Exception e) {
											System.out.println(e);
										}

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
										cs1 = con.prepareCall("call FAS_BRS_PROCEDURE_NT(?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?,?,?,?,?,?::numeric,?::numeric,?::numeric,?,?,?::numeric,?,?)");
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
										cs1.registerOutParameter(19,
												java.sql.Types.NUMERIC);
										cs1.setNull(19, java.sql.Types.NUMERIC);
										cs1.setString(20, "INSERT");
										cs1.setString(21, Journalized2);

										if (Entry_Date_NT2 != null) {
											cs1.executeQuery();

											//int errcode = cs1.getInt(19);
											int errcode = cs1.getBigDecimal(19).intValue();

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
