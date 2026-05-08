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

public class BRS_MainForm_Edit extends HttpServlet {
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
		Connection con5 = null;
		Connection con6 = null;
		PreparedStatement ps2 = null;
		PreparedStatement ps3 = null;
		ResultSet rs2 = null;
		ResultSet rs3 = null;

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
			cmbBankAccNo = Long.parseLong(request
					.getParameter("cmbBankAccNo"));
			System.out.println("cmbBankAccNo-->" + cmbBankAccNo);
		} catch (Exception e) {
			System.out.println("Exception to Read Bank Account Number");
		}
		
		
		
		String sql = "";
		String sql_T = " "
				+ "select                                         \n"
				+ "   *                                           \n"
				+ "from                                           \n"
				+ "(                                              \n"
				+ "    select  rownum,                            \n"
				+ "      ACCOUNTING_UNIT_ID as accunit,           \n"
				+ "      ACCOUNTING_FOR_OFFICE_ID as officeid,    \n"
				+ "      CASHBOOK_YEAR as cbyear,                 \n"
				+ "      CASHBOOK_MONTH as cbmonth,               \n"
				+ "      ACCOUNT_NO,                            \n"
				+ "      BRS_DOC_NO ,BRS_SERIAL_NO, BRS_DOC_TYPE    \n"
				+ "    from                                       \n"
				+ "      FAS_BRS_CROSS_REFERENCE_TABLE                      \n"
				+ "    where                                      \n"
				+ "        DOC_TYPE='BRST'                         \n"
				+ "    and ACCOUNT_NO =  "+cmbBankAccNo+" " +
				" and ACCOUNTING_UNIT_ID = "+cmbAcc_UnitCode+"   and " +
				" ACCOUNTING_FOR_OFFICE_ID = "+cmbOffice_code+"  and " +
				"CASHBOOK_YEAR = "+txtCB_Year+"           and CASHBOOK_MONTH = "+txtCB_Month+"   and change_no=0   \n"
				+ ")X                                             \n"
				+ "                                               \n"
				//+ "left outer join                                \n"
				+ "inner join                                \n"//Lakshmi 3Dec13 issue change
				+ "                                               \n"
				+ "(                                              \n"
				+ "    SELECT                                     \n"
				+ "      ACCOUNTING_UNIT_ID,                      \n"
				+ "      ACCOUNTING_FOR_OFFICE_ID, Extract (Year From Passbook_Date) As PbYear, \n"
				+" Extract (month From Passbook_Date)as pbMonth,\n"
				+ "      CASHBOOK_YEAR,                           \n"
				+ "      CASHBOOK_MONTH,                          \n"
				+ "      ENTRY_DATE,                              \n"
				+ "      SL_NO,                                   \n"
				+ "      TWAD_OR_NON_TWAD,                        \n"
				+ "      to_char(REMITTANCE_DATE,'DD/MM/YYYY') as REMITTANCE_DATE, \n"
				+ "      to_char(WITHDRAWAL_DATE,'DD/MM/YYYY') as WITHDRAWAL_DATE, \n"
				+ "      VOUCHER_OR_CHALLAN_NO,                   \n"
				+ "      CHEQUE_DD_NO,                            \n"
				+ "      PARTICULARS,                             \n"
				+ "      CR_AMOUNT,                               \n"
				+ "      DR_AMOUNT,                               \n"
				+ "      ENTRY_FOUND_IN_PASSBOOK,                 \n"
				+ "      to_char(PASSBOOK_DATE,'DD/MM/YYYY') as PASSBOOK_DATE , \n"
				+ "      AMOUNT_IN_PASSBOOK,                      \n"
				+ "      DIFFERENCE,                              \n"
				+ "      REASON_FOR_DIFFERENCE,                   \n"
				+ "      FOLLOW_UP_ACTION_REQUIRED,               \n"
				+ "      CLEARED_BASED_ON_FOLLOWUP,               \n"
				+ "      TRANSACTION_TYPE,                        \n"
				+ "      ACCOUNT_NO,DOC_NO, doc_type,              \n"
				+ "      DETAILS,                                 \n"
				+ "      JOURNALIZED,                              \n"
				+ "      DOC_DATE                              \n"
				+ "    FROM                                       \n"
				+ "      FAS_BRS_TRANSACTION                      \n"
				+ "    where                                      \n"
				+ "        ACCOUNTING_UNIT_ID = "+cmbAcc_UnitCode+"        and " +
						"ACCOUNTING_FOR_OFFICE_ID  = "+cmbOffice_code+"	           and " +
						"Extract (Year From Passbook_Date) = "+txtCB_Year+"                      \n"
				+ "    and extract (month from PASSBOOK_DATE) = "+txtCB_Month+"             " +
						"  and TWAD_OR_NON_TWAD = 'T' and ACCOUNT_NO = "+ cmbBankAccNo+") Y                                            \n"
				+ "on                                             \n"
				+ "  X.accunit = Y.ACCOUNTING_UNIT_ID and         \n"
				+ "  X.officeid = Y.ACCOUNTING_FOR_OFFICE_ID and  \n"
				+ "  X.cbyear = Y.PbYear and               \n"
				+ "  X.cbmonth = Y.pbMonth and             \n"
				+ "  X.ACCOUNT_NO = Y.ACCOUNT_NO and            \n"
				
				+ "  X.BRS_DOC_NO  = Y.doc_no  and                \n"
				//Lakshmi 17 April 2014 change Y.doc_notype to Y.TWAD_OR_NON_TWAD  
				+ "  X.BRS_DOC_TYPE = Y.TWAD_OR_NON_TWAD  and   X.BRS_SERIAL_NO=Y.SL_NO  ";//add   X.voucher_no=Y.SL_NO

		/*String sql_NT = "        select                                          \n"
				+ "           *                                            \n"
				+ "        from                                            \n"
				+ "        (                                               \n"
				+ "            select  rownum,                             \n"
				+ "              ACCOUNTING_UNIT_ID as accunit,            \n"
				+ "              ACCOUNTING_FOR_OFFICE_ID as officeid,     \n"
				+ "              CASHBOOK_YEAR as cbyear,                  \n"
				+ "              CASHBOOK_MONTH as cbmonth,                \n"
				+ "              REFERENCE_NO,                             \n"
				+ "              voucher_no                                \n"
				+ "            from                                        \n"
				+ "              FAS_CROSS_REFERENCE                       \n"
				+ "            where                                       \n"
				+ "                DOC_TYPE='BRSNT'                          \n"
				+ "            and REFERENCE_NO =  "+cmbBankAccNo+"                      \n"
				+ "            and ACCOUNTING_UNIT_ID = "+cmbAcc_UnitCode+"                  \n"
				+ "            and ACCOUNTING_FOR_OFFICE_ID = "+cmbOffice_code+"            \n"
				+ "            and CASHBOOK_YEAR = "+txtCB_Year+"                      \n"
				+ "            and CASHBOOK_MONTH = "+txtCB_Month+"   and change_no=0    \n"
				+ "        )X                                              \n"
				+ "                                                         \n"
				+ "        inner join                                     \n"
				+ "                                                        \n"
				+ "        (                                               \n"
				+ "            SELECT                                      \n"
				+ "              ACCOUNTING_UNIT_ID,                       \n"
				+ "              ACCOUNTING_FOR_OFFICE_ID,                 \n"
				+ "              CASHBOOK_YEAR,                            \n"
				+ "              CASHBOOK_MONTH,                           \n"
				+ "              ENTRY_DATE,                               \n"
				+ "              SL_NO,                                    \n"
				+ "              TWAD_OR_NON_TWAD,                         \n"
				+ "              DOC_NO,                         \n"
				+ "              DOC_TYPE,                         \n"
				+ "              to_char(REMITTANCE_DATE,'DD/MM/YYYY') as REMITTANCE_DATE,  \n"
				+ "              to_char(WITHDRAWAL_DATE,'DD/MM/YYYY') as WITHDRAWAL_DATE,  \n"
				+ "              VOUCHER_OR_CHALLAN_NO,                    \n"
				+ "              CHEQUE_DD_NO,                             \n"
				+ "              PARTICULARS,                              \n"
				+ "              CR_AMOUNT,                                \n"
				+ "              DR_AMOUNT,                                \n"
				+ "              ENTRY_FOUND_IN_PASSBOOK,                  \n"
				+ "              to_char(PASSBOOK_DATE,'DD/MM/YYYY') as PASSBOOK_DATE ,  \n"
				+ "              AMOUNT_IN_PASSBOOK,                       \n"
				+ "              DIFFERENCE,                               \n"
				+ "              REASON_FOR_DIFFERENCE,                    \n"
				+ "              FOLLOW_UP_ACTION_REQUIRED,                \n"
				+ "              CLEARED_BASED_ON_FOLLOWUP,                \n"
				+ "              TRANSACTION_TYPE,                         \n"
				+ "              ACCOUNT_NO,                                \n"
				+ "              DETAILS,                                   \n"
				+ "              JOURNALIZED,                                   \n"
				+ "              DOC_DATE                              \n"
				+ "            FROM                                        \n"
				+ "              FAS_BRS_TRANSACTION                       \n"
				+ "            where                                       \n"
				+ "                ACCOUNTING_UNIT_ID ="+cmbAcc_UnitCode+"                  \n"
				+ "            and ACCOUNTING_FOR_OFFICE_ID  = "+cmbOffice_code+"	         \n"
				+ "            and CASHBOOK_YEAR = "+txtCB_Year+"   and " +
				"CASHBOOK_MONTH = "+txtCB_Month+"           and TWAD_OR_NON_TWAD = 'NT'                    \n"
				+ "            and ACCOUNT_NO = "+cmbBankAccNo+"                          \n"
				+ "        ) Y                                             \n"
				+ "        on                                              \n"
				+ "          X.accunit = Y.ACCOUNTING_UNIT_ID and          \n"
				+ "          X.officeid = Y.ACCOUNTING_FOR_OFFICE_ID and   \n"
				+ "          X.cbyear = Y.CASHBOOK_YEAR and                \n"
				+ "          X.cbmonth = Y.CASHBOOK_MONTH and              \n"
				+ "          X.REFERENCE_NO = Y.ACCOUNT_NO and             \n"
				+ "          X.voucher_no = Y.SL_NO   \n"
				+ "        \n"
				+ "      ";*/
		
		
		
		
		//Lakshmi 1Apr14
	
		
		String sql_NT = "        select                                          \n"
			+ "           *                                            \n"
			+ "        from                                            \n"
			+ "        (                                               \n"
			+ "            select  rownum,                             \n"
			+ "              ACCOUNTING_UNIT_ID as accunit,            \n"
			+ "              ACCOUNTING_FOR_OFFICE_ID as officeid,     \n"
			+ "              CASHBOOK_YEAR as cbyear,                  \n"
			+ "              CASHBOOK_MONTH as cbmonth,                \n"
			+ "              ACCOUNT_NO,  BRS_DOC_NO, BRS_DOC_TYPE,                          \n"
			+ "              BRS_SERIAL_NO                                \n"
			+ "            from                                        \n"
			+ "              FAS_BRS_CROSS_REFERENCE_TABLE                       \n"
			+ "            where                                       \n"
			+ "                DOC_TYPE='BRSNT'                          \n"
			+ "            and ACCOUNT_NO =  "+cmbBankAccNo+"                      \n"
			+ "            and ACCOUNTING_UNIT_ID = "+cmbAcc_UnitCode+"                  \n"
			+ "            and ACCOUNTING_FOR_OFFICE_ID = "+cmbOffice_code+"            \n"
			+ "            and CASHBOOK_YEAR = "+txtCB_Year+"                      \n"
			+ "            and CASHBOOK_MONTH = "+txtCB_Month+"   and change_no=0    \n"
			+ "        )X                                              \n"
			+ "                                                         \n"
			+ "        inner join                                     \n"
			+ "                                                        \n"
			+ "        (                                               \n"
			+ "            SELECT                                      \n"
			+ "              ACCOUNTING_UNIT_ID,                       \n"
			+ "              ACCOUNTING_FOR_OFFICE_ID,                 \n"
			+ "              CASHBOOK_YEAR,                            \n"
			+ "              CASHBOOK_MONTH,                           \n"
			+ "              ENTRY_DATE,                               \n"
			+ "              SL_NO,                                    \n"
			+ "              TWAD_OR_NON_TWAD,                         \n"
			+ "              DOC_NO,                         \n"
			+ "              DOC_TYPE,                         \n"
			+ "              to_char(REMITTANCE_DATE,'DD/MM/YYYY') as REMITTANCE_DATE,  \n"
			+ "              to_char(WITHDRAWAL_DATE,'DD/MM/YYYY') as WITHDRAWAL_DATE,  \n"
			+ "              VOUCHER_OR_CHALLAN_NO,                    \n"
			+ "              CHEQUE_DD_NO,                             \n"
			+ "              PARTICULARS,                              \n"
			+ "              CR_AMOUNT,                                \n"
			+ "              DR_AMOUNT,                                \n"
			+ "              ENTRY_FOUND_IN_PASSBOOK,                  \n"
			+ "              to_char(PASSBOOK_DATE,'DD/MM/YYYY') as PASSBOOK_DATE ,  \n"
			+ "              AMOUNT_IN_PASSBOOK,                       \n"
			+ "              DIFFERENCE,                               \n"
			+ "              REASON_FOR_DIFFERENCE,                    \n"
			+ "              FOLLOW_UP_ACTION_REQUIRED,                \n"
			+ "              CLEARED_BASED_ON_FOLLOWUP,                \n"
			+ "              TRANSACTION_TYPE,                         \n"
			+ "              ACCOUNT_NO,                                \n"
			+ "              DETAILS,                                   \n"
			+ "              JOURNALIZED,                                   \n"
			+ "              DOC_DATE                              \n"
			+ "            FROM                                        \n"
			+ "              FAS_BRS_TRANSACTION                       \n"
			+ "            where                                       \n"
			+ "                ACCOUNTING_UNIT_ID ="+cmbAcc_UnitCode+"                  \n"
			+ "            and ACCOUNTING_FOR_OFFICE_ID  = "+cmbOffice_code+"	         \n"
			+ "            and CASHBOOK_YEAR = "+txtCB_Year+"   and " +
			"CASHBOOK_MONTH = "+txtCB_Month+"           and TWAD_OR_NON_TWAD = 'NT'                    \n"
			+ "            and ACCOUNT_NO = "+cmbBankAccNo+"                          \n"
			+ "        ) Y                                             \n"
			+ "        on                                              \n"
			+ "          X.accunit = Y.ACCOUNTING_UNIT_ID and          \n"
			+ "          X.officeid = Y.ACCOUNTING_FOR_OFFICE_ID and   \n"
			+ "          X.cbyear = Y.CASHBOOK_YEAR and                \n"
			+ "          X.cbmonth = Y.CASHBOOK_MONTH and              \n"
			+ "           X.ACCOUNT_NO   = Y.ACCOUNT_NO and             \n"
			+ "          X.BRS_DOC_NO   = Y.doc_no and             \n"
			+ "           X.BRS_DOC_TYPE = Y.doc_type and             \n"
			+ "         X.BRS_SERIAL_NO=Y.SL_NO  \n"
			+ "        \n"
			+ "      ";
		
		
		/**
		 * Display TWAD Transactions + Non - TWAD Transaction + Total Number of
		 * TWAD Transaction
		 */

		if (strCommand.equalsIgnoreCase("LoadTWADTransactions")
				|| strCommand.equalsIgnoreCase("LoadNONTWADTransactions")
				|| strCommand.equalsIgnoreCase("TotalTransactions")) {
			//System.out.println("sql_NT::::"+sql_NT);
		//	System.out.println("sql_T::iiiii::"+sql_T);
			
			
			String CONTENT_TYPE = "text/xml; charset=windows-1252";
			response.setContentType(CONTENT_TYPE);
			String xml = "";
			String TWAD_or_NonTWAD = "T";

			/* Get Display Sequence List */
			int ListSeq_S = 0;
			int ListSeq_E = 0;

			/* This is applicable only for TWAD Transactions */
			try {
				ListSeq_S = Integer.parseInt(request.getParameter("ListSeq"));
				ListSeq_E = ListSeq_S + 9;
			} catch (Exception e) {
				System.out.println(e);
			}

			/* Set xml command parameter */
			if (strCommand.equalsIgnoreCase("LoadTWADTransactions")) {
				xml = "<response><command>LoadTWADTransactions</command>";
				TWAD_or_NonTWAD = "T";
			} else if (strCommand.equalsIgnoreCase("LoadNONTWADTransactions")) {
				xml = "<response><command>LoadNONTWADTransactions</command>";
				TWAD_or_NonTWAD = "NT";
			}

			try {

				if (strCommand.equalsIgnoreCase("LoadTWADTransactions")) {
					sql = sql_T
							+ "where  SL_NO is not null";
					
					System.out.println("sql_T::iiiii::"+sql);
				} else if (strCommand.equalsIgnoreCase("LoadNONTWADTransactions")) {
					sql = sql_NT + "where SL_NO is not null ";
					System.out.println("sql_NT::::"+sql);
					//System.out.println("sql:::--->>>" + sql);
				}

				// System.out.println(sql);

				ps2 = con.prepareStatement(sql);

		/*		ps2.setLong(1, cmbBankAccNo);
				ps2.setInt(2, cmbAcc_UnitCode);
				ps2.setInt(3, cmbOffice_code);
				ps2.setInt(4, txtCB_Year);
				ps2.setInt(5, txtCB_Month);

				ps2.setInt(6, cmbAcc_UnitCode);
				ps2.setInt(7, cmbOffice_code);
				ps2.setInt(8, txtCB_Year);
				ps2.setInt(9, txtCB_Month);
				ps2.setString(10, TWAD_or_NonTWAD);
				ps2.setLong(11, cmbBankAccNo);  */

				rs2 = ps2.executeQuery();		

				int count = 0;

				while (rs2.next()) {

					xml = xml + "<sl_no>" + rs2.getString("SL_NO") + "</sl_no>";
					xml = xml + "<r_no>"
							+ rs2.getString("VOUCHER_OR_CHALLAN_NO")
							+ "</r_no>";
					xml = xml + "<r_date>" + rs2.getString("REMITTANCE_DATE")
							+ "</r_date>";
					xml = xml + "<r_ccdd_no>" + rs2.getString("CHEQUE_DD_NO")
							+ "</r_ccdd_no>";
					xml = xml + "<cr_amount>" + rs2.getString("CR_AMOUNT")
							+ "</cr_amount>";

					xml = xml + "<r_particulars>"
							+ rs2.getString("PARTICULARS") + "</r_particulars>";

					xml = xml + "<doc_no>" + rs2.getString("doc_no")
							+ "</doc_no>";
					xml = xml + "<doc_type>" + rs2.getString("doc_type")
							+ "</doc_type>";

					xml = xml + "<w_no>"
							+ rs2.getString("VOUCHER_OR_CHALLAN_NO")
							+ "</w_no>";
					xml = xml + "<w_date>" + rs2.getString("WITHDRAWAL_DATE")
							+ "</w_date>";
					xml = xml + "<w_ccdd_no>" + rs2.getString("CHEQUE_DD_NO")
							+ "</w_ccdd_no>";
					xml = xml + "<dr_amount>" + rs2.getString("DR_AMOUNT")
							+ "</dr_amount>";
					xml = xml + "<w_particulars>"
							+ rs2.getString("PARTICULARS") + "</w_particulars>";

					xml = xml + "<EntryFoundInPassBook>"
							+ rs2.getString("ENTRY_FOUND_IN_PASSBOOK")
							+ "</EntryFoundInPassBook>";
					xml = xml + "<Amt_in_PassBk>"
							+ rs2.getString("AMOUNT_IN_PASSBOOK")
							+ "</Amt_in_PassBk>";
					xml = xml + "<Amt_Diff>" + rs2.getString("DIFFERENCE")
							+ "</Amt_Diff>";

					xml = xml + "<cmbReason4Diff>"
							+ rs2.getString("REASON_FOR_DIFFERENCE")
							+ "</cmbReason4Diff>";
					xml = xml + "<FollowUpAction>"
							+ rs2.getString("FOLLOW_UP_ACTION_REQUIRED")
							+ "</FollowUpAction>";
					xml = xml + "<ClearanceEntry>"
							+ rs2.getString("CLEARED_BASED_ON_FOLLOWUP")
							+ "</ClearanceEntry>";
					xml = xml + "<Entry_Date>" + rs2.getString("PASSBOOK_DATE")
							+ "</Entry_Date>";
					xml = xml + "<Details>" + rs2.getString("DETAILS")
							+ "</Details>";
					xml = xml + "<Journalized>" + rs2.getString("JOURNALIZED")
							+ "</Journalized>";
					xml = xml + "<com_doc_date>" + rs2.getString("DOC_DATE")
							+ "</com_doc_date>";
					xml = xml + "<Trans_Type_NT>"
							+ rs2.getString("TRANSACTION_TYPE")
							+ "</Trans_Type_NT>";					
					count++;					
				}

				String sql2 = "";
				String sql_rsn = "";
				String sql_trans = "";

				sql_rsn = "								   "
						+ "select                                     \n"
						+ " *                                         \n"
						+ "from                                       \n"
						+ "(                                          \n"
						+ "    SELECT                                 \n"
						+ "      PASSBOOK_BALANCE                     \n"
						+ "    FROM                                   \n"
						+ "      FAS_BRS_MASTER                       \n"
						+ "    where                                  \n"
						+ "        ACCOUNTING_UNIT_ID = ?             \n"
						+ "    and ACCOUNTING_FOR_OFFICE_ID = ?		  \n"
						+ "    and CASHBOOK_YEAR = ?                  \n"
						+ "    and CASHBOOK_MONTH = ?                 \n"
						+ "    and ACCOUNT_NO = ?                     \n"
						+ "),                                         \n"
						+ "(                                          \n"
						+ "    SELECT				  				  \n"
						+ "       TRANS_CODE as rt_code,		      \n"
						+ "       TRANS_DESC as rt_desc	      \n"
						+ "    FROM 				                  \n"
						+ "       FAS_BRS_TRANSACTION_TYPE            \n"
						+ ")                                          ";

				sql_trans = "								   "
						+ "select                                     \n"
						+ " *                                         \n"
						+ "from                                       \n"
						+ "(                                          \n"
						+ "    SELECT                                 \n"
						+ "      PASSBOOK_BALANCE                     \n"
						+ "    FROM                                   \n"
						+ "      FAS_BRS_MASTER                       \n"
						+ "    where                                  \n"
						+ "        ACCOUNTING_UNIT_ID = ?             \n"
						+ "    and ACCOUNTING_FOR_OFFICE_ID = ?       \n"
						+ "    and CASHBOOK_YEAR = ?                  \n"
						+ "    and CASHBOOK_MONTH = ?                 \n"
						+ "    and ACCOUNT_NO = ?                     \n"
						+ "),                                         \n"
						+ "(                                          \n"
						+ "    SELECT				      \n"
						+ "       TRANS_CODE as rt_code,	      \n"
						+ "      TRANS_SHORT_DESC as rt_desc	      \n"
						+ "    FROM 				      \n"
						+ "       FAS_BRS_TRANSACTION_TYPE            \n"
						+ ")                                          ";

				if (strCommand.equalsIgnoreCase("LoadTWADTransactions")
						|| strCommand.equalsIgnoreCase("TotalTransactions")) {
					sql2 = sql_rsn;
				} else if (strCommand
						.equalsIgnoreCase("LoadNONTWADTransactions")) {
					sql2 = sql_trans;
				}

				ps3 = con.prepareStatement(sql2);
				ps3.setInt(1, cmbAcc_UnitCode);
				ps3.setInt(2, cmbOffice_code);
				ps3.setInt(3, txtCB_Year);
				ps3.setInt(4, txtCB_Month);
				ps3.setLong(5, cmbBankAccNo);

				rs3 = ps3.executeQuery();

				while (rs3.next()) {
					xml = xml + "<reason_pair>";
					xml = xml + "<reason_code>" + rs3.getString("rt_code")
							+ "</reason_code>";
					xml = xml + "<reason_desc>" + rs3.getString("rt_desc")
							+ "</reason_desc>";
					xml = xml + "<txtPBBalance>"
							+ rs3.getString("PASSBOOK_BALANCE")
							+ "</txtPBBalance>";
					xml = xml + "</reason_pair>";
				}

				/* Check Total Number of TWAD Transaction */
				if (strCommand.equalsIgnoreCase("TotalTransactions")) {
					xml = "<response><command>TotalTransactions</command>";
					xml = xml + "<TotalTrans>" + count + "</TotalTrans>";
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
		CallableStatement cs2 = null,cs11=null;

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
		String txtOprMode = null;
		int txtBankID = 0;
		int txtBranchID = 0;
		float txtPBBalance = 0.0f;
System.out.println("coming BRS NT/T servlet Edit");
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
			cmbBankAccNo =  Long.parseLong(request
					.getParameter("cmbBankAccNo"));
			System.out.println("cmbBankAccNo-->" + cmbBankAccNo);
		} catch (Exception e) {
			System.out.println("Error Not Getting Bank Account Number -->" + e);
		}

		/* Get Operation Mode */
		try {
			txtOprMode = request.getParameter("txtOprMode");
			System.out.println("txtOprMode-->" + txtOprMode);
		} catch (Exception e) {
			System.out.println("Error Not Getting Operation Mode -->" + e);
		}

		/* Get Bank ID */
		try {
			txtBankID = Integer.parseInt(request.getParameter("txtBankID"));
			System.out.println("txtBankID-->" + txtBankID);
		} catch (Exception e) {
			System.out.println("Error Not Getting Bank ID -->" + e);
		}

		/* Get Branch ID */
		try {
			txtBranchID = Integer.parseInt(request
					.getParameter(("txtBranchID")));
			System.out.println("txtBranchID-->" + txtBranchID);
		} catch (Exception e) {
			System.out.println("Error Not Getting Branch ID  -->" + e);
		}

		/* Get Pass Book Balance Amount */
	/*	try {
			txtPBBalance = Float.parseFloat(request
					.getParameter(("txtPBBalance")));
			System.out.println("txtPBBalance-->" + txtPBBalance);
		} catch (Exception e) {
			System.out.println("Error Not Getting Pass Book Balance -->" + e);
		}
*/
		/* User ID */
		String update_user = (String) session.getAttribute("UserId");
		System.out.println("update_user-->" + update_user);

		/* Get Time Stamp */
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
		System.out.println("Timestamp -->" + ts);

		try { // Main Try I

			con.clearWarnings();
			con.setAutoCommit(false);

			/**
			 * --------------------- General Details - Master Trans
			 * -----------------------
			 */
			
			// Joan Modified to remove passbook Balance update
			/*try {

				String sql_insert_mst = ""
						+ "update fas_brs_master                            "
						+ "set passbook_balance= ?                          \n"
						+ "where accounting_unit_id = ?                     \n"
						+ "and accounting_for_office_id = ?                 \n"
						+ "and cashbook_month= ?                            \n"
						+ "and cashbook_year = ?                            \n"
						+ "and account_no= ?                                \n";

				ps2 = con.prepareStatement(sql_insert_mst);

				ps2.setFloat(1, txtPBBalance);
				ps2.setInt(2, cmbAcc_UnitCode);
				ps2.setInt(3, cmbOffice_code);
				ps2.setInt(4, txtCB_Month);
				ps2.setInt(5, txtCB_Year);
				ps2.setLong(6, cmbBankAccNo);

				ps2.executeUpdate();

			} catch (Exception e) {
				con.rollback();
				con.setAutoCommit(true);
				sendMessage(response, "Records Not Inserted .... " + e, "ok");
				System.out.println(e);
				return;
			}*/

			/** --------------------- TWAD Transaction ------------------------- */

			int RecordCount = 0;

			/* Get Total Number of Transaction in TWAD Transactions */
			try {
				System.out.println(request.getParameter("RecordCount"));
				RecordCount = Integer.parseInt(request
						.getParameter("RecordCount"));
			} catch (Exception e) {
				System.out
						.println("Error Getting Total Number of Records in TWAD Transaction ");
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
			String doc_date[] = new String[RecordCount];
			String doc_no[] = new String[RecordCount];
			String doc_type[] = new String[RecordCount];
			String slNoU[] = new String[RecordCount];
			/* Variables Declaration */
			Date r_date2 = null;
			Date w_date2 = null;
			int r_w_no2 = 0;
			int ccdd_no2 = 0;
			float cr_amount2 = 0;
			float dr_amount2 = 0;
			int doc_no2 = 0;
			int slNoU2 = 0;
			String EntryFoundInPassBook2 = null;
			Date Entry_Date2 = null;
			float Amt_in_PassBk2 = 0.0f;
			float Amt_Diff2 = 0.0f;
			String cmbReason4Diff2 = "";
			String FollowUpAction2 = null;
			String ClearanceEntry2 = null;
			String doc_type2 = null;
			Date doc_date2 = null;

			String sd[] = new String[10];
			java.util.Date d = null;
			Calendar c;
			int Checked_value = -1;
			int pass_month=0;
			int pass_year=0;
			
			System.out.println(".................RecordCount.................."
					+ RecordCount);
			try {
				for (int k = 0; k < RecordCount; k++) {

					try {
						Checked_value = Integer.parseInt(request
								.getParameter("check" + k));
						System.out.println("Selected Check Box1 -->"
								+ Checked_value);
					} catch (Exception e) {
						System.out.println("Error Converting Seq Number -->"
								+ e);
					}

					/* Receipt Date */
					try {
						r_date[k] = request.getParameter("r_date" + k);

						if (!r_date[k].equalsIgnoreCase("")) {
							sd = r_date[k].split("/");
							/*
							 * System.out.println("sd[0]-->"+sd[0]);
							 * System.out.println("sd[1]-->"+sd[1]);
							 * System.out.println("sd[2]-->"+sd[2]);
							 */

							c = new GregorianCalendar(Integer.parseInt(sd[2]),
									Integer.parseInt(sd[1]) - 1, Integer
											.parseInt(sd[0]));
							d = c.getTime();
							r_date2 = new Date(d.getTime());
						}//Lakshmi 19Nov13 else
						else{
							System.out.println("r_date equalss nulll "+k);
						}
						System.out.println("r_date2-->" + r_date2);
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
						}//Lakshmi 19Nov13 else
						else{
							System.out.println("w_date equalss nulll "+k);
						}
						// System.out.println("w_date2-->"+w_date2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Receipt or Challan Number */
					try {
						r_w_no[k] = request.getParameter("r_w_no" + k);
						r_w_no2 = Integer.parseInt(r_w_no[k]);
						// System.out.println("r_w_no2-->"+r_w_no2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Doc Number */
					try {
						doc_no[k] = request.getParameter("doc_no" + k);
						doc_no2 = Integer.parseInt(doc_no[k]);
						// System.out.println("doc_no2-->"+doc_no2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Doc Type */
					try {
						doc_type[k] = request.getParameter("doc_type" + k);
						doc_type2 = doc_type[k];
						// System.out.println("doc_no2-->"+doc_type2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Doc Date */
					try {
						doc_date[k] = request.getParameter("doc_date" + k);
						if ((!doc_date[k].equalsIgnoreCase(""))
								&& (doc_date[k] != null)) {							
							//System.out
							//		.println(k
								//			+ "doc_date==========================================================="
								//			+ doc_date[k]);
							sd = doc_date[k].split("/");
							c = new GregorianCalendar(Integer.parseInt(sd[2]),
									Integer.parseInt(sd[1]) - 1, Integer
											.parseInt(sd[0]));
							d = c.getTime();
							doc_date2 = new Date(d.getTime());
							//System.out
									//.println("doc_date==========================================================="
											//+ doc_date2);
						} else {
							doc_date[k] = "00/00/0000";
							System.out.println("else block");
							//System.out
									//.println(k
										//	+ "doc_date==========================================================="
										//	+ doc_date[k]);
							sd = doc_date[k].split("/");
							c = new GregorianCalendar(Integer.parseInt(sd[2]),
									Integer.parseInt(sd[1]) - 1, Integer
											.parseInt(sd[0]));
							d = c.getTime();
							doc_date2 = new Date(d.getTime());
							//System.out
									//.println("doc_date==========================================================="
									//		+ doc_date2);
						}
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Cheque or DD Number */
					try {
						ccdd_no[k] = request.getParameter("ccdd_no" + k);
						ccdd_no2 = Integer.parseInt(ccdd_no[k]);
						// System.out.println("ccdd_no2-->"+ccdd_no2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* slNoU */
					try {
						slNoU[k] = request.getParameter("slNoU" + k);
						slNoU2 = Integer.parseInt(slNoU[k]);
						// System.out.println("slNoU2-->"+slNoU2);
					} catch (Exception e) {
						System.out.println(e);
					}
					
					/* Cr Amount */
					try {
						cr_amount[k] = request.getParameter("cr_amount" + k);
						cr_amount2 = Float.parseFloat(cr_amount[k]);
						// System.out.println("cr_amount2-->"+cr_amount2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Dr Amount */
					try {
						dr_amount[k] = request.getParameter("dr_amount" + k);
						dr_amount2 = Float.parseFloat(dr_amount[k]);
						// System.out.println("dr_amount2-->"+dr_amount2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Entry Found in Pass Book */
					try {
						EntryFoundInPassBook[k] = request
								.getParameter("EntryFoundInPassBook" + k);
						EntryFoundInPassBook2 = EntryFoundInPassBook[k];
						// System.out.println("EntryFoundInPassBook2-->"+EntryFoundInPassBook2);
					} catch (Exception e) {
						System.out.println(e);
					}

				
					/* Entry Date */
					try {
						Entry_Date[k] = request.getParameter("Entry_Date" + k);
						if (!Entry_Date[k].equalsIgnoreCase("")) {
							sd = Entry_Date[k].split("/");
							pass_month=Integer.parseInt(sd[1]);
							pass_year=Integer.parseInt(sd[2]);
							c = new GregorianCalendar(Integer.parseInt(sd[2]),
									Integer.parseInt(sd[1]) - 1, Integer
											.parseInt(sd[0]));
							d = c.getTime();
							Entry_Date2 = new Date(d.getTime());
						}
						// System.out.println("Entry_Date2-->"+Entry_Date2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Amount in Pass Book */
					try {
						Amt_in_PassBk[k] = request.getParameter("Amt_in_PassBk"
								+ k);
						Amt_in_PassBk2 = Float.parseFloat(Amt_in_PassBk[k]);
						// System.out.println("Amt_in_PassBk2-->"+Amt_in_PassBk2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Difference */
					try {
						Amt_Diff[k] = request.getParameter("Amt_Diff" + k);
						Amt_Diff2 = Float.parseFloat(Amt_Diff[k]);
						// System.out.println("Amt_Diff2-->"+Amt_Diff2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Reason for Difference */
					try {
						cmbReason4Diff[k] = request
								.getParameter("cmbReason4Diff" + k);
						cmbReason4Diff2 = cmbReason4Diff[k];
						// System.out.println("cmbReason4Diff2-->"+cmbReason4Diff2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Follow up action Required */
					try {
						FollowUpAction[k] = request
								.getParameter("FollowUpAction" + k);
						FollowUpAction2 = FollowUpAction[k];
						// System.out.println("FollowUpAction2-->"+FollowUpAction2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Clearance Entry */
					try {
						ClearanceEntry[k] = request
								.getParameter("ClearanceEntry" + k);
						ClearanceEntry2 = ClearanceEntry[k];
						// System.out.println("ClearanceEntry2-->"+ClearanceEntry2);
					} catch (Exception e) {
						System.out.println(e);
					}
					cs = con.prepareCall("{call FAS_BRS_PROCEDURE1_NEW(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
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
					cs.setFloat(14, Amt_Diff2);
					cs.setString(15, cmbReason4Diff2);
					cs.setString(16, FollowUpAction2);
					cs.setString(17, ClearanceEntry2);
					cs.setDate(18, doc_date2);
					cs.setString(19, update_user);
					cs.setLong(20, cmbBankAccNo);

					cs.setString(21, doc_type2);
					cs.setInt(22, doc_no2);

					cs.registerOutParameter(23, java.sql.Types.NUMERIC);
					cs.setString(24, "UPDATE");
					cs.setInt(25, slNoU2);
					cs.setInt(26, pass_month);
					cs.setInt(27, pass_year);
					
					System.out.println("EntryFoundInPassBook2 first Step ::::"+EntryFoundInPassBook2);
					
					if (EntryFoundInPassBook2 == null) {
						System.out.println("EntryFoundInPassBook2 is null  ");
						System.out.println("Checked_value:::"+Checked_value);
						System.out.println("k::"+k);
						if (Checked_value == k) {
							cs.executeQuery();
							int errcode = cs.getInt(23);

							if (errcode != 0) {
								con.rollback();
								con.setAutoCommit(true);
								sendMessage(
										response,
										"TWAD Transaction Records Not Inserted ............ ",
										"ok");
							}
						} else {
							System.out.println("empty records deleteddddddddddddddddddddddddddddddddddddddddddddddddddd from FAS_BRS_TRANSACTION :::::");
							int k1 = k + 1;
							ps2 = con.prepareStatement("delete from FAS_BRS_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and extract (year from PASSBOOK_DATE)=? and extract (month from PASSBOOK_DATE)=? and TWAD_OR_NON_TWAD=? and DOC_NO=? and DOC_TYPE=? and ACCOUNT_NO=? and SL_NO=?");
							System.out.println("doc_no2::::::"+doc_no2);
							System.out.println("doc_type2:"+doc_type2);
							System.out.println("cmbBankAccNo:"+cmbBankAccNo);
						//	System.out.println("k1::::"+k1);
							
							
							ps2.setInt(1, cmbAcc_UnitCode);
							ps2.setInt(2, cmbOffice_code);
							ps2.setInt(3, txtCB_Year);
							ps2.setInt(4, txtCB_Month);
							ps2.setString(5, "T");
							ps2.setInt(6, doc_no2);
							ps2.setString(7, doc_type2);
							ps2.setLong(8, cmbBankAccNo);
							ps2.setInt(9, slNoU2);
							int dt=ps2.executeUpdate();
								System.out.println("deleted::::::"+dt);
								
								System.out.println( "Insert FAS_BRS_TRANSACTION_NOENTRY table **********************************************************");
							cs1 = con.prepareCall("{call FAS_BRS_PROCEDURE_NOENTRY1(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
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
							cs1.setString(11, EntryFoundInPassBook2);
							cs1.setDate(12, Entry_Date2);
							cs1.setFloat(13, Amt_in_PassBk2);
							cs1.setFloat(14, Amt_Diff2);
							cs1.setString(15, cmbReason4Diff2);
							cs1.setString(16, FollowUpAction2);
							cs1.setString(17, ClearanceEntry2);
							cs1.setDate(18, doc_date2);
							cs1.setString(19, update_user);
							cs1.setLong(20, cmbBankAccNo);
							cs1.setString(21, doc_type2);
							cs1.setInt(22, doc_no2);
							cs1.registerOutParameter(23,java.sql.Types.NUMERIC);
							cs1.setString(24, "INSERT");
							cs1.executeQuery();
							System.out.println("-14-");
							int errcode = cs1.getInt(23);
							System.out
									.println("errcode==============================="
											+ errcode);
							if (errcode != 0) {
								con.rollback();
								con.setAutoCommit(true);
								sendMessage(
										response,
										"TWAD Transaction Records Not Inserted ............ ",
										"ok");
							}
						}
					}

					System.out.println("two");

					int errcode = 0;
					String txtReferNO_edit = "";
					String txtRemak_edit = "";
					Date txtReferDate_edit = null;
					String radAuth_MC = "";
					int txtAuth_By = 0;
					Date txtCrea_date = null;

					/** Update Cross Reference Table */
				System.out.println(" EntryFoundInPassBook2 step 2 *********************");  
					
					cs11 = con.prepareCall("{call FAS_BRS_CROSS_REFERENCE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				//	cs11 = con.prepareCall("{call FAS_BRS_CROSS_REFERENCE_one(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
					
				//CallableStatement	cs11 = con.prepareCall("{call FAS_BRS_CROSS_REFERENCE_ONE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				//	cs1 = con.prepareCall("{call FAS_CROSS_REFERENCE_PROC_ONE(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
				
				cs11.setInt(1, cmbAcc_UnitCode);
				cs11.setInt(2, txtCB_Year);
				cs11.setInt(3, txtCB_Month);
				cs11.setInt(4, doc_no2);
				cs11.setInt(5, cmbOffice_code);
				cs11.setDate(6, txtCrea_date);
				cs11.setString(7, "BRST");
				cs11.setString(8, txtReferNO_edit);
				cs11.setDate(9, txtReferDate_edit);
				cs11.setString(10, txtRemak_edit);
				cs11.setInt(11, txtAuth_By);
					if (EntryFoundInPassBook2 == null) {
					//	System.out.println("null");
						System.out.println("EntryFoundInPassBook2 is null means % --- Insert --- %  FAS_BRS_CROSS_REFERENCE_TABLE");
						cs11.setString(12, "insert");
					}else
					{
					//	System.out.println("not null");
						//cs11.setString(12, "edit");
						System.out.println("EntryFoundInPassBook2 is null means % --- Update --- %  FAS_BRS_CROSS_REFERENCE_TABLE");
						cs11.setString(12, "allow_modify");
					}
					cs11.registerOutParameter(13, java.sql.Types.NUMERIC);
					cs11.setString(14, update_user);
					cs11.setTimestamp(15, ts);
					cs11.setString(16, radAuth_MC);
					//System.out.println(slNoU2+";:"+doc_type2+":::"+ccdd_no2);
					cs11.setInt(17,slNoU2);//sl_no
					cs11.setString(18,doc_type2);// payment or sc
					cs11.setInt(19,ccdd_no2);  //cheque no
					cs11.execute();
					errcode = cs11.getInt(13);
					//System.out.println("errcodesssssssssssssssssssss::"+errcode);
					if (errcode != 0) {
						con.rollback();
						sendMessage(response,
								"Cross Reference Updation Failed ", "ok");
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
				System.out.println(e.getMessage());
				con.rollback();
				con.setAutoCommit(true);
				sendMessage(response,
						"TWAD Transaction Records Not Inserted ............ "
								+ e, "ok");
				//System.out.println(e);
				return;
			}

			/**
			 * -------------------------- NON TWAD Transaction
			 * ---------------------------
			 */

			try {

				int RecordCount_NT = 0;

				/* Get Total Number of Transaction in NON-TWAD Transactions */
				try {
					RecordCount_NT = Integer.parseInt(request
							.getParameter("RecordCountNT"));
				} catch (Exception e) {
					System.out
							.println("Error Getting Total Number of Records in TWAD Transaction ");
				}

				/* String Array Declaration */
				String sl_no_NT[] = new String[RecordCount_NT];
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
				int sl_no_NT2 = 0;
				Date Entry_Date_NT2 = null;
				String Particualrs_NT2 = "";
				int ChequeNo_NT2 = 0;
				String Details_NT2 = "";
				float cr_amount_NT2 = 0;
				float dr_amount_NT2 = 0;
				String Trans_Type_NT2 = "";
				String FollowUpAction_NT2 = "";
				String ClearanceEntry_NT2 = "";
				int Checked_value1 = -1;
				String Checked_value1New[]=new String[RecordCount_NT];
				String Checked_value_New="";
				String Journalized2 = "";
				int passyear=0,passmonth=0;
				
				System.out.println("Step 3 :::;  update  fas_brs_transaction"+RecordCount_NT);
				cs1 = con
						.prepareCall("{call FAS_BRS_PROCEDURE_NT(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

				for (int k = 0; k < RecordCount_NT; k++) {					
					try {
						/*Checked_value1 = Integer.parseInt(request
								.getParameter("check1" + k));*/
						Checked_value_New=request.getParameter("check1"+k);
						//Checked_value_New=Checked_value1New[k];
						System.out.println("testttttttt"+Checked_value_New);
						System.out.println("Checked_value1New value %%%%%% "+Checked_value1New[k]);
						
					} catch (Exception e) {
						System.out.println("Error Converting Seq Number -->"
								+ e);
					}

					/* sl no */
					try {
						sl_no_NT[k] = request.getParameter("sl_no" + k);
						sl_no_NT2 = Integer.parseInt(sl_no_NT[k]);
						// System.out.println("sl_no_NT2-->"+sl_no_NT2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Entry Date */
					try {
						Entry_Date_NT[k] = request.getParameter("Entry_Date_NT"
								+ k);
						// System.out.println("Entry_Date_NT[k]-->"+Entry_Date_NT[k]);

						if (!Entry_Date_NT[k].equalsIgnoreCase("")) {
							sd = Entry_Date_NT[k].split("/");
							passyear=Integer.parseInt(sd[2]);
							passmonth=Integer.parseInt(sd[1]);
							c = new GregorianCalendar(Integer.parseInt(sd[2]),
									Integer.parseInt(sd[1]) - 1, Integer
											.parseInt(sd[0]));
							d = c.getTime();
							Entry_Date_NT2 = new Date(d.getTime());
						}
						// System.out.println("Entry_Date_NT2-->"+Entry_Date_NT2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Particulars */
					try {
						Particualrs_NT[k] = request
								.getParameter("Particualrs_NT" + k);
						Particualrs_NT2 = Particualrs_NT[k];
						// System.out.println("Particulars_NT2-->"+Particualrs_NT2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Cheque Number */
					try {
						ChequeNo_NT[k] = request
								.getParameter("ChequeNo_NT" + k);
						ChequeNo_NT2 = Integer.parseInt(ChequeNo_NT[k]);
						// System.out.println("ChequeNo_NT2-->"+ChequeNo_NT2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Details */
					try {
						Details_NT[k] = request.getParameter("Details_NT" + k);
						Details_NT2 = Details_NT[k];
						// System.out.println("Details_NT2-->"+Details_NT2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Cr Amount */
					try {
						cr_amount_NT[k] = request.getParameter("cr_amount_NT"
								+ k);
						cr_amount_NT2 = Float.parseFloat(cr_amount_NT[k]);
						// System.out.println("cr_amount_NT2-->"+cr_amount_NT2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Dr Amount */
					try {
						dr_amount_NT[k] = request.getParameter("dr_amount_NT"
								+ k);
						dr_amount_NT2 = Float.parseFloat(dr_amount_NT[k]);
						// System.out.println("dr_amount_NT2-->"+dr_amount_NT2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Transaction Type */
					try {
						Trans_Type_NT[k] = request.getParameter("Trans_Type_NT"
								+ k);
						Trans_Type_NT2 = Trans_Type_NT[k];
						// System.out.println("Trans_Type_NT2-->"+Trans_Type_NT2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Follow up action Required */
					try {
						FollowUpAction_NT[k] = request
								.getParameter("FollowUpAction_NT" + k);
						FollowUpAction_NT2 = FollowUpAction_NT[k];
						// System.out.println("FollowUpAction_NT2-->"+FollowUpAction_NT2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Clearance Entry */
					try {
						ClearanceEntry_NT[k] = request
								.getParameter("ClearanceEntry_NT" + k);
						ClearanceEntry_NT2 = ClearanceEntry_NT[k];
						// System.out.println("ClearanceEntry_NT2-->"+ClearanceEntry_NT2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Doc Number */
					try {
						doc_no[k] = request.getParameter("doc_no" + k);
						doc_no2 = Integer.parseInt(doc_no[k]);
						// System.out.println("doc_no2-->"+doc_no2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Doc Type */
					try {
						doc_type[k] = request.getParameter("doc_type" + k);
						doc_type2 = doc_type[k];
						// System.out.println("doc_no2-->"+doc_type2);
					} catch (Exception e) {
						System.out.println(e);
					}

					/* Journalized */
					try {
						Journalized[k] = request
								.getParameter("Journalized" + k);
						Journalized2 = Journalized[k];
						System.out.println("Journalized2-->" + Journalized2);
					} catch (Exception e) {
						System.out.println(e);
					}
					if (Journalized2 != null) {

					} else {
						Journalized2 = "N";
					}

					/*
					 * System.out.println("$$$$$$$$$$$$$$$------------------------------$$$$$$$$$$$$$$$$$"
					 * ); System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
					 * System.out.println("cmbOffice_code"+cmbOffice_code);
					 * System.out.println("txtCB_Year"+txtCB_Year);
					 * System.out.println("txtCB_Month"+txtCB_Month);
					 * 
					 * System.out.println("Particualrs_NT2"+Particualrs_NT2);
					 * System.out.println("ChequeNo_NT2"+ChequeNo_NT2);
					 * System.out.println("cr_amount_NT2"+cr_amount_NT2);
					 * System.out.println("Entry_Date_NT2"+Entry_Date_NT2);
					 * System.out.println("Trans_Type_NT2"+Trans_Type_NT2);
					 * System
					 * .out.println("FollowUpAction_NT2"+FollowUpAction_NT2);
					 * System
					 * .out.println("ClearanceEntry_NT2"+ClearanceEntry_NT2);
					 * System.out.println("update_user"+update_user);
					 * System.out.println("cmbBankAccNo"+cmbBankAccNo);
					 * System.out.println("sl_no_NT2"+sl_no_NT2);
					 * System.out.println("doc_no2"+doc_no2);
					 * System.out.println("doc_type2"+doc_type2);
					 * System.out.println("Details"+Details_NT2);
					 * System.out.println(
					 * "$$$$$$$$$$$$$$$------------------------------$$$$$$$$$$$$$$$$$"
					 * );
					 */
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
					cs1.setInt(15, sl_no_NT2);
					cs1.setInt(16, doc_no2);
					cs1.setString(17, doc_type2);
					cs1.setString(18, Details_NT2);
					cs1.registerOutParameter(19, java.sql.Types.NUMERIC);
					cs1.setString(20, "UPDATE");
					cs1.setString(21, Journalized2);
					cs1.setInt(22, passmonth);
					cs1.setInt(23, passyear);

					System.out.println("three   step sss  ******************************** Entry_Date_NT2 >> "+Entry_Date_NT2);
System.out.println("value of Checked_value1New ****"+Checked_value_New);
					if (Entry_Date_NT2 != null) {
						//if (Checked_value1 == k) {
						System.out.println("inside ssssssssss"+Checked_value1);
						if(Checked_value_New.equalsIgnoreCase("Checked")){
							System.out
									.println("^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^^");
							System.out.println("Inside if condition...");
							ps2 = con
									.prepareStatement("delete from FAS_BRS_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SL_NO=? and TWAD_OR_NON_TWAD=?");
							ps2.setInt(1, cmbAcc_UnitCode);
							ps2.setInt(2, cmbOffice_code);
							ps2.setInt(3, txtCB_Year);
							ps2.setInt(4, txtCB_Month);
							ps2.setInt(5, sl_no_NT2);
							ps2.setString(6, "NT");
							ps2.executeUpdate();
						} else if(Checked_value_New.equalsIgnoreCase("Uncheckd")) {
							 System.out.println("Inside else condition...");
							cs1.executeQuery();
							// System.out.println("after query exe...");
							int errcode = cs1.getInt(19);

							 System.out.println("NT Error Code ---->"+errcode);

							if (errcode != 0) {
								con.rollback();
								con.setAutoCommit(true);
								sendMessage(
										response,
										"NON TWAD Transaction Records Not Updated in first............ ",
										"ok");
							}

						}
					}

					/** Update Cross Reference Table */

					int errcode = 0;
					String txtReferNO_edit = "";
					String txtRemak_edit = "";
					Date txtReferDate_edit = null;
					String radAuth_MC = "";
					String txtAuth_By = null;
					Date txtCrea_date = null;
System.out.println(" Step 4 *****    % insert%      ****** FAS_CROSS_REFERENCE");
					cs2 = con
							.prepareCall("call FAS_CROSS_REFERENCE_PROC(?::NUMERIC,?,?,?,?,?,?,?,?,?,?::VARCHAR,?,?,?,?,?)");
					cs2.setInt(1, cmbAcc_UnitCode);
					cs2.setInt(2, txtCB_Year);
					cs2.setInt(3, txtCB_Month);
					cs2.setInt(4, sl_no_NT2);
					cs2.setInt(5, cmbOffice_code);
					cs2.setDate(6, txtCrea_date);
					cs2.setString(7, "BRSNT");
					cs2.setString(8, txtReferNO_edit);
					cs2.setDate(9, txtReferDate_edit);
					cs2.setString(10, txtRemak_edit);
					cs2.setString(11, txtAuth_By);
					cs2.setString(12, "insert");
					cs2.registerOutParameter(13, java.sql.Types.NUMERIC);
					cs1.setNull(13,java.sql.Types.NUMERIC);
					cs2.setString(14, update_user);
					cs2.setTimestamp(15, ts);
					cs2.setString(16, radAuth_MC);
					cs2.execute();
					errcode = cs2.getInt(13);

					if (errcode != 0) {
						con.rollback();
						sendMessage(response,
								"Cross Reference Updation Failed ", "ok");
					}
				}

			} catch (Exception e) {
				System.out.println(e);
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
			sendMessage(response, "Records Updated Successfully ............ ",
					"ok");

		} catch (Exception e) {
			System.out.println(e);

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