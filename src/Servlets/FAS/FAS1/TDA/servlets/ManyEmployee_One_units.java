package Servlets.FAS.FAS1.TDA.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;
import Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_GENERAL;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Types;

import java.sql.SQLException;

import java.sql.Statement;
import java.sql.Timestamp;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ManyEmployee_One_units extends HttpServlet {
	private String CONTENT_TYPE = "text/html; charset=windows-1252";

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		/**
		 * Set Content Type
		 */
		PrintWriter out = response.getWriter();
		response.setHeader("cache-control", "no-cache");
		String CONTENT_TYPE = "text/xml; charset=windows-1252";
		response.setContentType(CONTENT_TYPE);

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
		PreparedStatement ps2 = null, ps = null, ps7 = null;
		ResultSet rs2 = null, rs = null;
		String sql = "";
		String[] sd = {};
	    String[] litVoucher=null;
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

		int count = 0, AccUnitId = 0, cmbOffice_code = 0, voucher_no = 0, sub_code = 0,slnum=0;
		String xml = null, cmd = "";
		Date txtCrea_date = null;
                String splVoucher="";
		try {
			cmd = request.getParameter("command");
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			AccUnitId = Integer.parseInt(request.getParameter("txtUnitId"));
			System.out.println("AccUnitId " + AccUnitId);

		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			System.out.println("cmbOffice_code " + cmbOffice_code);

		} catch (Exception e) {
			System.out.println("cmbOffice_code" + e.getMessage());
		}

		try {
			sd = request.getParameter("txtCrea_date").split("/");
			Calendar c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer
					.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c.getTime();
			txtCrea_date = new Date(d.getTime());
			System.out.println("txtCrea_date " + txtCrea_date);
		} catch (Exception e) {
			System.out.println("Eception :: " + e.getMessage());
		}

		int year = Integer.parseInt(sd[2]);
		int month = Integer.parseInt(sd[1]);

		try {
			voucher_no = Integer.parseInt(request.getParameter("voucher_no"));
		} catch (Exception e) {
			System.out.println("voucher_no" + e.getMessage());
		}

		System.out.println("cmd:::******" + cmd);
		xml = "<response>";
		if (cmd.equalsIgnoreCase("load_voucher_no")) {
			String cmbDocType = request.getParameter("cmbDocType");
			ResultSet rs7 = null;
			xml = xml + "<command>load_voucher_no</command>";
			try {
				if (cmbDocType.equals("J")) {
					sql = "select distinct VOUCHER_NO,ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,VOUCHER_DATE,SL_NO from \n"
							+ "( \n"
							+ "    select  \n"
							+ "    mst.ACCOUNTING_UNIT_ID, \n"
							+ "    mst.ACCOUNTING_FOR_OFFICE_ID, \n"
							+ "    mst.VOUCHER_DATE , \n"
							+ "    mst.VOUCHER_NO, trn.SUB_LEDGER_TYPE_CODE,\n" + 
							"    trn.SUB_LEDGER_CODE,trn.SL_NO \n"
							+ "    from \n"
							+ "     FAS_JOURNAL_MASTER mst, \n"
							+ "    FAS_JOURNAL_TRANSACTION trn \n"
							+ "    where \n"
							+ "    mst.ACCOUNTING_UNIT_ID=trn.ACCOUNTING_UNIT_ID and \n"
							+ "    mst.ACCOUNTING_FOR_OFFICE_ID=trn.ACCOUNTING_FOR_OFFICE_ID and  \n"
							+ "    mst.CASHBOOK_YEAR=trn.CASHBOOK_YEAR and  \n"
							+ "    mst.CASHBOOK_MONTH=trn.CASHBOOK_MONTH and \n"
							+ "    mst.VOUCHER_NO=trn.VOUCHER_NO and \n"
							+ "    mst.ACCOUNTING_UNIT_ID=? and \n"
							+ "    mst.ACCOUNTING_FOR_OFFICE_ID=? and \n"
							+ "    mst.VOUCHER_DATE=? and  mst.JOURNAL_TYPE_CODE=54 and\n"
							+ "    mst.JOURNAL_STATUS='L' and "
                                                    //    + "    mst.CB_REF_TYPE is null and "
                                                        + "    (trn.CB_REF_NO is null or trn.CB_REF_NO=0)  and "
                                                        + "    trn.VERIFIED ='Y' and "
							+ "    trn.ACCOUNT_HEAD_CODE=901001  \n"
							+ ")aa \n"
							+ "where \n"
							+ "aa.VOUCHER_NO not in(select ORGINATING_JVR_NO from FAS_TDA_TCA_RAISED_MST where ACCOUNTING_UNIT_ID=aa.ACCOUNTING_UNIT_ID and ACCOUNTING_FOR_OFFICE_ID=aa.ACCOUNTING_FOR_OFFICE_ID and ORGINATING_JVR_NO=aa.VOUCHER_NO and ORGINATING_JVR_DATE=aa.VOUCHER_DATE AND SUB_LEDGER_TYPE_CODE=aa.SUB_LEDGER_TYPE_CODE  and SUB_LEDGER_CODE=aa.SUB_LEDGER_CODE and STATUS='L')";
				} else if (cmbDocType.equals("R")) {
					sql = "SELECT *\n" + 
					" FROM         (         \n" + 
					" SELECT          mst.ACCOUNTING_UNIT_ID,\n" + 
					"           mst.ACCOUNTING_FOR_OFFICE_ID,\n" + 
					"           mst.RECEIPT_DATE,\n" + 
					"           mst.RECEIPT_NO               AS VOUCHER_NO,trn.SL_NO, \n" + 
					"           COUNT(trn.account_head_code) AS no_of_rec         \n" + 
					" FROM          FAS_RECEIPT_MASTER mst,\n" + 
					"           FAS_RECEIPT_TRANSACTION trn         \n" + 
					" WHERE          mst.ACCOUNTING_UNIT_ID    =trn.ACCOUNTING_UNIT_ID\n" + 
					" AND          mst.ACCOUNTING_FOR_OFFICE_ID=trn.ACCOUNTING_FOR_OFFICE_ID\n" + 
					" AND          mst.CASHBOOK_YEAR           =trn.CASHBOOK_YEAR\n" + 
					" AND          mst.CASHBOOK_MONTH          =trn.CASHBOOK_MONTH\n" + 
					" AND          mst.RECEIPT_NO              =trn.RECEIPT_NO\n" + 
					" AND          mst.ACCOUNTING_UNIT_ID      =?\n" + 
					" AND          mst.ACCOUNTING_FOR_OFFICE_ID=?\n" + 
					" AND          mst.RECEIPT_DATE            =? \n" + 
					" AND          mst.RECEIPT_STATUS          ='L'\n" + 
                                         "AND mst.remittance_status        ='Y'\n"   +
                                        "AND   (mst.receivable_voucher_type IS NULL or mst.receivable_voucher_type ='')\n" +
					" AND          trn.ACCOUNT_HEAD_CODE         =901001 \n" + 
					" AND (TRN.CB_REF_NO =0 OR TRN.CB_REF_NO is null) "+
					" and TRN.CB_REF_DATE is null "+
                                            " GROUP BY          mst.ACCOUNTING_UNIT_ID,\n" + 
					"           mst.ACCOUNTING_FOR_OFFICE_ID,\n" + 
					"           mst.RECEIPT_DATE,\n" + 
                                        "     trn.SL_NO, \n" +
					"           mst.RECEIPT_NO         )aa         \n" + 
					" WHERE         aa.VOUCHER_NO NOT IN\n" + 
					"  (SELECT ORGINATING_JVR_NO\n" + 
					"  FROM FAS_TDA_TCA_RAISED_MST\n" + 
					"  WHERE ACCOUNTING_UNIT_ID                   =aa.ACCOUNTING_UNIT_ID\n" + 
					"  AND ACCOUNTING_FOR_OFFICE_ID               =aa.ACCOUNTING_FOR_OFFICE_ID\n" + 
					"  AND ORGINATING_JVR_NO                      =aa.VOUCHER_NO\n" + 
					"  AND ORGINATING_JVR_DATE=aa.RECEIPT_DATE\n" + 
					"  AND STATUS                                 ='L'\n" + 
					"  AND ADVICE_TYPE                            ='CB'\n" + 
					"  )";
				} else if (cmbDocType.equals("P")) {
					sql = "SELECT mst.ACCOUNTING_UNIT_ID, "
							+ "       mst.ACCOUNTING_FOR_OFFICE_ID,"
							+ "        mst.PAYMENT_DATE,"
							+ "          mst.VOUCHER_NO,trn.SL_NO  "
							+ "       FROM                         FAS_PAYMENT_MASTER mst,"
							+ "      FAS_PAYMENT_TRANSACTION trn       "
							+ "    WHERE                         mst.ACCOUNTING_UNIT_ID    =trn.ACCOUNTING_UNIT_ID "
							+ "AND                         mst.ACCOUNTING_FOR_OFFICE_ID=trn.ACCOUNTING_FOR_OFFICE_ID "
							+ "    AND                          mst.CASHBOOK_YEAR          =trn.CASHBOOK_YEAR "
							+ "    AND                          mst.CASHBOOK_MONTH         =trn.CASHBOOK_MONTH "
							+ "       AND                         mst.VOUCHER_NO              =trn.VOUCHER_NO "
							+ "       AND                         mst.ACCOUNTING_UNIT_ID      =? "
							+ "       AND                         mst.ACCOUNTING_FOR_OFFICE_ID=?  "
							+ "       AND                         mst.PAYMENT_DATE            =?  "
							+ "       AND                        mst.PAYMENT_STATUS           ='L'  "
							+ "       AND                          trn.VERIFIED                 ='Y'  "
							+ "       AND                        trn.ACCOUNT_HEAD_CODE          =900108    "
						//	+ "       and PAYABLE_VOUCHER_TYPE is  null";
				    + "    and (trn.PAYABLE_VOUCHER_NO =0 or trn.PAYABLE_VOUCHER_NO is null)";
				

				}
                                System.out.println("sql****"+sql);
				ps7 = con.prepareStatement(sql);
				ps7.setInt(1, AccUnitId);
				ps7.setInt(2, cmbOffice_code);
				ps7.setDate(3, txtCrea_date);
				// ps7.setInt(4,901001);
				rs7 = ps7.executeQuery();
				while (rs7.next()) {
					xml += "<VOUCHER_NO>" + rs7.getInt("VOUCHER_NO")+ "</VOUCHER_NO>";
				    xml += "<slno>" + rs7.getInt("SL_NO")+ "</slno>";
					count++;
				}
				if (count == 0)
					xml += "<flag>NoData</flag>";
				else
					xml += "<flag>success</flag>";
			} catch (Exception e) {
				e.printStackTrace();
			}
		} else if (cmd.equalsIgnoreCase("load_Voucher_Details")) {
			
                      System.out.println("load deatailssssssssssss");  
		    try {//voucher_no
		       splVoucher= request.getParameter("voucher_no");
                       System.out.println("splVoucher::::in try:::"+splVoucher);
		    litVoucher =  splVoucher.split("-");
		       
		            //payment_voucher_no = Integer.parseInt(request.getParameter("txtVoucher_No"));
		    } catch (NumberFormatException e) {
		            System.out.println("exception" + e);
		    }
                    System.out.println("litVoucher[0]:::"+litVoucher[0]);
		    voucher_no=Integer.parseInt(litVoucher[0]);
                    System.out.println("voucher_no"+voucher_no);
		    slnum=Integer.parseInt(litVoucher[1]);
                    System.out.println("voucher_no::"+voucher_no+"::::slnum****"+slnum);
		   // System.out.println("litVoucher **********" + litVoucher[0]);
		  //  System.out.println("serial number **********" + litVoucher[1]);
		    String cmbDocType = request.getParameter("cmbDocType");
                    System.out.println("cmbDocType::::"+cmbDocType);
		 //   int slnum = Integer.parseInt(request.getParameter("slno"));
                    System.out.println("slnum>>>>>>>>>>"+slnum);
			xml = xml + "<command>load_Voucher_Details</command>";
			try {

				if (cmbDocType.equals("J")) {
					sql = "SELECT trn.ACCOUNT_HEAD_CODE AS ac_code,  trn.CR_DR_INDICATOR,  "
							+ "trn.SUB_LEDGER_TYPE_CODE,  trn.SUB_LEDGER_CODE,  trim(TO_CHAR(trn.AMOUNT,"
							+ "'99999999999999.99')) AS amount,  trn.PARTICULARS ,   (SELECT account_head_desc  "
							+ " FROM com_mst_account_heads a   WHERE a.account_head_code=trn.account_head_code  "
							+ " )AS DEC ,   (SELECT sub_ledger_type_desc   FROM com_mst_sl_types  "
							+ " WHERE sub_ledger_type_code=trn.sub_ledger_type_code   )AS desc1 "
							+ "FROM FAS_JOURNAL_TRANSACTION trn,fas_journal_master mst WHERE  "
							+ "mst.accounting_unit_id     =trn.accounting_unit_id AND "
							+ "mst.accounting_for_office_id =trn.accounting_for_office_id AND "
							+ "mst.cashbook_year            =trn.cashbook_year AND "
							+ "mst.cashbook_month           =trn.cashbook_month AND "
							+ "mst.voucher_no               =trn.voucher_no AND "
							+ "mst.accounting_unit_id       =? AND mst.accounting_for_office_id =? "
							+ "AND mst.cashbook_year            =? AND mst.cashbook_month           =? AND "
							+ "mst.voucher_no               =? and  trn.sl_no="+slnum+" AND trn.account_head_code        =901001 AND "
							+ "mst.journal_status           ='L' " +
                                                     //   "AND trn.SUB_LEDGER_TYPE_CODE    =? AND "
							//+ "trn.SUB_LEDGER_CODE         =? " +
                                                        "and trn.VERIFIED is not null ";
				} 
				else if (cmbDocType.equals("R")) {
					sql="SELECT *  "+
						"	FROM "+
						"	  (SELECT * "+
						"	  FROM "+
						"	    ( "+
						"	    SELECT trn.account_head_code, "+
						"	      trn.cr_dr_indicator, "+
						"	      trn.sub_ledger_type_code, "+
						"	      trn.sub_ledger_code, "+
						"	     (SELECT account_head_desc "+
						"	  FROM com_mst_account_heads a "+
						"	  WHERE a.account_head_code=trn.account_head_code "+
						"	  )AS DEC , "+
						"	  (SELECT sub_ledger_type_desc "+
						"	  FROM com_mst_sl_types "+
						"	  Where Sub_Ledger_Type_Code=Trn.Sub_Ledger_Type_Code "+
						"	  )AS desc1, "+
						"	      trim(TO_CHAR(amount,'99999999999999.99')) AS amount, "+
							"      particulars "+
							 "   FROM fas_receipt_transaction trn, "+
							  "    fas_receipt_master mst "+
							   " WHERE mst.accounting_unit_id     =trn.accounting_unit_id "+
							    " AND mst.accounting_for_office_id =trn.accounting_for_office_id "+
						" AND mst.cashbook_year            =trn.cashbook_year "+
						"     AND mst.cashbook_month           =trn.cashbook_month "+
						" 	    And Mst.Receipt_No               =Trn.Receipt_No "+
						"     And Mst.Accounting_Unit_Id       =? "+
						"     And Mst.Accounting_For_Office_Id =? "+
						"     And Mst.Cashbook_Year            =? "+
						"     And Mst.Cashbook_Month           =? "+
						"     And Mst.Receipt_No               =? "+
						" 	    AND trn.account_head_code        =901001 "+
						"     AND trn.sl_no                    ="+slnum+
						"     AND mst.receipt_status           ='L' "+
						"     AND mst.remittance_status        ='Y' "+
						"     AND (mst.receivable_voucher_type IS NULL or mst.receivable_voucher_type ='') "+
						"     )opt "+
						"   WHERE account_head_code=901001 "+
						"   )aa "+
						" LEFT OUTER JOIN "+
						"   (SELECT account_head_code AS ac_code, "+
						"     account_head_desc       AS DEC "+
						"   FROM com_mst_account_heads "+
						"   )bb "+
						" ON aa.account_head_code=bb.ac_code "+
						" LEFT OUTER JOIN "+
						"   (SELECT SUB_LEDGER_TYPE_CODE AS code1, "+
						"     SUB_LEDGER_TYPE_DESC       AS desc1 "+
						"   FROM com_mst_sl_types "+
						"   )ff "+
						" ON aa.sub_ledger_type_code=ff.code1 "+
						" LEFT OUTER JOIN "+
						"   (SELECT SL_TYPE, "+
						"     SL_CODE sub_type_code, "+
						"     SL_CODENAME slcode "+
						"   FROM SL_TYPE_CODE_NAME_VIEW "+
						"   )cc "+
						" ON aa.sub_ledger_type_code=cc.SL_TYPE "+
						" AND aa.sub_ledger_code    = cc.sub_type_code";
				}
				
				/* else if (cmbDocType.equals("R")) {
					sql = "SELECT *\n"
							+ "FROM\n"
							+ " ( select * from (SELECT account_head_code,\n"
							+ "    account_no,\n"
							+ "    mst.bank_id,\n"
							+ "    branch_id,\n"
							+ "    bank.bank_name,\n"
							+ "    cr_dr_indicator,\n"
							+ "    sub_ledger_type_code,\n"
							+ "    sub_ledger_code,\n"
							+ "    ''                                              AS cheque_or_dd,\n"
							+ "    ''                                              AS cheque_dd_no,\n"
							+ "    ''                                              AS cheque_dd_date,\n"
							+ "    trim(TO_CHAR(total_amount,'99999999999999.99')) AS amount,\n"
							+ "    remarks                                         AS particulars\n"
							+ "  FROM fas_receipt_master mst,\n"
							+ "    fas_bank_list bank\n"
							+ "  WHERE mst.bank_id            =bank.bank_id\n"
							+ "  AND accounting_unit_id       =?\n"
							+ "  AND accounting_for_office_id =?\n"
							+ "  AND receipt_no               =?\n"
							+ "  UNION ALL\n"
							+ "  SELECT trn.account_head_code,\n"
							+ "  0 AS account_no,\n"
							+ "  0 AS bank_id,\n"
							+ "  0 AS branch_id,\n"
							+ "  trn.bank_name,\n"
							+ "  trn.cr_dr_indicator,\n"
							+ "  trn.sub_ledger_type_code,\n"
							+ "  trn.sub_ledger_code,\n"
							+ "  cheque_or_dd,\n"
							+ "  cheque_dd_no,\n"
							+ "  TO_CHAR(cheque_dd_date,'dd-mm-yyyy'),\n"
							+ "  trim(TO_CHAR(amount,'99999999999999.99')) AS amount,\n"
							+ "  particulars\n"
							+ "FROM fas_receipt_transaction trn,\n"
							+ "  fas_receipt_master mst\n"
							+ "WHERE mst.accounting_unit_id     =trn.accounting_unit_id\n"
							+ "AND mst.accounting_for_office_id =trn.accounting_for_office_id\n"
							+ "AND mst.cashbook_year            =trn.cashbook_year\n"
							+ "AND mst.cashbook_month           =trn.cashbook_month\n"
							+ "AND mst.receipt_no               =trn.receipt_no\n"
							+ "AND mst.accounting_unit_id       =?\n"
							+ "AND mst.accounting_for_office_id =?\n"
							+ "AND mst.cashbook_year            =?\n"
							+ "AND mst.cashbook_month           =?\n"
							+ "AND mst.receipt_no               =?\n"
							+ "AND trn.account_head_code        =901001 and  trn.sl_no="+slnum+" AND mst.receipt_status           ='L'\n"
							+ "AND mst.remittance_status        ='Y'\n"
							+ "AND mst.receivable_voucher_type IS NULL\n"
						//	+ "AND trn.sub_ledger_code          =? \n"
						//	+ "AND trn.sub_ledger_type_code     =? \n"
							+ "  ) where account_head_code=901001 )aa\n"
							+ "LEFT OUTER JOIN\n"
							+ "  (SELECT account_head_code AS ac_code,\n"
							+ "    account_head_desc       AS DEC\n"
							+ "  FROM com_mst_account_heads\n"
							+ "  )bb\n"
							+ "ON aa.account_head_code=bb.ac_code\n"
                                                        + "left outer join \n"
                                                        + "(select SUB_LEDGER_TYPE_CODE as code1,SUB_LEDGER_TYPE_DESC as desc1 from com_mst_sl_types)ff  \n"
                                                        + "ON aa.sub_ledger_type_code=ff.code1  \n"
							+ "LEFT OUTER JOIN\n"
							+ "(SELECT SL_TYPE,SL_CODE sub_type_code,SL_CODENAME slcode from SL_TYPE_CODE_NAME_VIEW)cc\n"
							+ "ON aa.sub_ledger_type_code=cc.SL_TYPE\n"
							+ "and aa.sub_ledger_code = cc.sub_type_code";

				} */ 
				else if (cmbDocType.equals("P")) {
					sql = "SELECT * FROM  (select * from (SELECT account_head_code,    account_no,    "
							+ "bank_id,    branch_id,    cr_dr_indicator,    sub_ledger_type_code,    "
							+ "sub_ledger_code,    paid_to,    ''                                              "
							+ "AS cheque_or_dd,    ''                                              "
							+ "AS cheque_dd_no,    ''                                              "
							+ "AS cheque_dd_date,    trim(TO_CHAR(total_amount,'99999999999999.99')) AS amount, "
							+ "   remarks                                         AS particulars  FROM "
							+ "fas_payment_master  WHERE accounting_unit_id     =?  AND "
							+ "accounting_for_office_id =?  AND payment_date             =?  AND voucher_no "
							+ "              =?  UNION ALL  SELECT trn.account_head_code,    trn.account_no,  "
							+ "  trn.bank_id,    trn.branch_id,    trn.cr_dr_indicator,    "
							+ "trn.sub_ledger_type_code,    trn.sub_ledger_code,    trn.paid_to,    "
							+ "cheque_or_dd,    cheque_dd_no,    TO_CHAR(cheque_dd_date,'dd-mm-yyyy'),    "
							+ "trim(TO_CHAR(amount,'99999999999999.99')) AS amount,    particulars  FROM "
							+ "fas_payment_transaction trn,    fas_payment_master mst  WHERE "
							+ "mst.accounting_unit_id     =trn.accounting_unit_id  AND "
							+ "mst.accounting_for_office_id =trn.accounting_for_office_id  AND "
							+ "mst.cashbook_year            =trn.cashbook_year  AND "
							+ "mst.cashbook_month           =trn.cashbook_month  AND "
							+ "mst.voucher_no               =trn.voucher_no  AND "
							+ "mst.accounting_unit_id       =?  AND mst.accounting_for_office_id =?  "
							+ "AND mst.cashbook_year            =?  AND mst.cashbook_month           =?  "
							+ "AND mst.voucher_no               =? and  trn.sl_no="+slnum+"  AND trn.ACCOUNT_HEAD_CODE        =900108 "
							+ " AND mst.payment_status           ='L' " +
                                                     //   "AND trn.sub_ledger_code          =? "
						//	+ "AND trn.sub_ledger_type_code     =? " +
                                                        "AND trn.verified             "
							+ "   IS NOT NULL  )opt1 where account_head_code=900108)aa LEFT OUTER JOIN  "
							+ "(SELECT account_head_code AS ac_code,    account_head_desc       AS DEC "
							+ " FROM com_mst_account_heads  )bb ON aa.account_head_code=bb.ac_code "
                                                        + "left outer join \n"
                                                        + "(select SUB_LEDGER_TYPE_CODE as code1,SUB_LEDGER_TYPE_DESC as desc1 from com_mst_sl_types)ff  \n"
                                                        + "ON aa.sub_ledger_type_code=ff.code1  \n"
							+ "LEFT OUTER JOIN  (SELECT sub_ledger_type_code AS sub_type_code,    "
							+ "sub_ledger_type_desc  as slcode  FROM com_mst_sl_types  )cc ON "
							+ "aa.sub_ledger_type_code=cc.sub_type_code LEFT OUTER JOIN   "
							+ "( SELECT bank_id AS bid,bank_name FROM fas_bank_list   )dd ON "
							+ "aa.bank_id=dd.bid ";
				}

				if (cmbDocType.equals("J")) {
					ps2 = con.prepareStatement(sql);
					ps2.setInt(1, AccUnitId);
					ps2.setInt(2, cmbOffice_code);
					ps2.setInt(3, year);
					ps2.setInt(4, month);
					ps2.setInt(5, voucher_no);
					//ps2.setInt(6, sltype);
					//ps2.setInt(7, slCode);
				} else if (cmbDocType.equals("R")) {
					ps2 = con.prepareStatement(sql);
					ps2.setInt(1, AccUnitId);
					ps2.setInt(2, cmbOffice_code);
					ps2.setInt(3, year);
					ps2.setInt(4, month);
					ps2.setInt(5, voucher_no);
					
					//ps2.setInt(9, slCode);
					//ps2.setInt(10, sltype);
				} else if (cmbDocType.equals("P")) {					
					ps2 = con.prepareStatement(sql);
					ps2.setInt(1, AccUnitId);
					ps2.setInt(2, cmbOffice_code);
					ps2.setDate(3, txtCrea_date);
					ps2.setInt(4, voucher_no);
					ps2.setInt(5, AccUnitId);
					ps2.setInt(6, cmbOffice_code);
					ps2.setInt(7, year);
					ps2.setInt(8, month);
					ps2.setInt(9, voucher_no);
					//ps2.setInt(10, slCode);
					//ps2.setInt(11, sltype);

				}
				rs2 = ps2.executeQuery();

				while (rs2.next()) {
					xml += "<account_head_code>" + rs2.getInt("ac_code")
							+ "</account_head_code>";
					xml += "<account_head_desc>" + rs2.getString("dec")
							+ "</account_head_desc>";
					xml += "<cr_dr_indicator>"
							+ rs2.getString("CR_DR_INDICATOR")
							+ "</cr_dr_indicator>";
					xml += "<sub_ledger_type_code>"+ rs2.getInt("SUB_LEDGER_TYPE_CODE")+ "</sub_ledger_type_code>";
					if (rs2.getInt("sub_ledger_type_code") == 0)
						xml += "<sub_ledger_type_desc>--</sub_ledger_type_desc>";
					else
						xml += "<sub_ledger_type_desc>"+ rs2.getString("desc1")+ "</sub_ledger_type_desc>";
					xml += "<sub_ledger_code>" + rs2.getInt("SUB_LEDGER_CODE")+ "</sub_ledger_code>";

					xml += "<amount>" + rs2.getString("amount") + "</amount>";
					xml += "<particulars>" + rs2.getString("PARTICULARS")
							+ "</particulars>";
					sub_code = rs2.getInt("sub_ledger_code");
					System.out.println(sub_code);
					if (rs2.getInt("sub_ledger_type_code") != 0
							&& rs2.getInt("sub_ledger_code") != 0) {
						String sql1 = "select ACCOUNTING_UNIT_ID, ACCOUNTING_UNIT_NAME,ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID='"
								+ sub_code + "'";
						ps = con.prepareStatement(sql1);
						ResultSet rs_get = ps.executeQuery();
						if (rs_get != null) {
							if (rs_get.next()) {
								// System.out.println(rs_get.getString("cid"));
								// System.out.println(rs_get.getString("cname"));
								xml = xml+ "<cid>"+ rs_get.getInt("ACCOUNTING_UNIT_ID")+ "</cid><cname>"	+ rs_get.getString("ACCOUNTING_UNIT_NAME")+ "</cname>";
                                                                xml=xml+"<offId>"+rs_get.getInt("ACCOUNTING_UNIT_OFFICE_ID")+"</offId>";
							}
							rs_get.close();
						} else {
							System.out.println("null result set");
							xml = xml + "<cid>--</cid><cname>--</cname>";
						}
					} else
						xml = xml + "<cid>--</cid><cname>--</cname>";
					count++;
				}
				if (count == 0)
					xml += "<flag>NoData</flag>";
				else
					xml += "<flag>success</flag>";
			} catch (Exception e) {
				e.printStackTrace();
			}
		}
		xml = xml + "</response>";
		System.out.println(xml);
		out.println(xml);
		out.close();
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		String strCommand = "";
		Connection con = null;
		PreparedStatement ps = null, ps1 = null, ps2 = null, ps3 = null, ps4 = null;
		String xml = "";
		Statement st = null;
		ResultSet rs = null;
		// -----------------------------------------------------------------------------------------------

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

		// -----------------------------------------------------------------------------------------------

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
			st = con.createStatement();
		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);
			// sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

		}

		// -----------------------------------------------------------------------------------------------

		try {
			strCommand = request.getParameter("Command");
			System.out.println("assign..here command..." + strCommand);

		}

		catch (Exception e) {
			System.out.println("Exception in assigning..." + e);
		}

		// -----------------------------------------------------------------------------------------------

		if (strCommand.equalsIgnoreCase("Add")) {
			String CONTENT_TYPE = "text/html; charset=windows-1252";
			response.setContentType(CONTENT_TYPE);
			int i = 0;
			int updateCmd=0;
			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			Calendar c;
			int txtAcc_HeadCode = 0, cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid = 0, txtCash_year = 0, txtUnitId = 0, txtDebitHead = 0, payment_voucher_no = 0;
			int count = 0, cmbMas_SL_type = 0, cmbMas_SL_Code = 0, cmbReason = 0, bank_id1 = 0, branch_id1 = 0, account_no = 0;
			double txtTotalAmt = 0;
			Date txtCrea_date = null, txtPayment_date = null;
			String txtRemarks = "", paid_to = "", Journal_type = "", cr_dr_indicator = "", sql = "",splVoucher="",txtVoucher_No_new_test="";
		    String[] litVoucher=null;
			// changes here
			String update_user = (String) session.getAttribute("UserId");
			long l = System.currentTimeMillis();
			Timestamp ts = new Timestamp(l);
			int errcode = 0, receipt_month = 0, receipt_year = 0;
                        String[] splvou11=null;
			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

			try {
				cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("cmbAcc_UnitCode"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

			try {
				cmbOffice_code = Integer.parseInt(request
						.getParameter("cmbOffice_code"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			System.out.println("cmbOffice_code " + cmbOffice_code);

			String[] sd = request.getParameter("txtCrea_date").split("/");
			c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer
					.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c.getTime();
			txtCrea_date = new Date(d.getTime());

			try {
				txtCash_year = Integer.parseInt(sd[2]);
			} catch (Exception e) {
				System.out.println("exception" + e);
			}
			System.out.println("txtCash_year " + txtCash_year);

			try {
				txtCash_Month_hid = Integer.parseInt(sd[1]);
			} catch (Exception e) {
				System.out.println("exception" + e);
			}
			System.out.println("txtCash_Month_hid " + txtCash_Month_hid);
			int Originated_SL_No = 0;
			int fin_year_from = 0, fin_year_to = 0;
		    int numconversion=0;
			// ////////////////////Financial year
			// calculation/////////////////////////////////
			if (txtCash_Month_hid > 3) {
				fin_year_from = txtCash_year;
				fin_year_to = txtCash_year + 1;
			} else {
				fin_year_from = txtCash_year - 1;
				fin_year_to = txtCash_year;
			}

			try {
				sql = "SELECT VOUCHER_NO FROM FAS_TDA_TCA_RAISED_MST GROUP BY VOUCHER_NO HAVING  "
						+ " VOUCHER_NO =(select max(VOUCHER_NO) from FAS_TDA_TCA_RAISED_MST where to_date(CASHBOOK_MONTH||'-'||CASHBOOK_YEAR,'mm-yyyy') between to_date(4||'-'||?,'mm-yyyy') and to_date(3||'-'||?,'mm-yyyy'))";
				ps = con.prepareStatement(sql);
				ps.setInt(1, fin_year_from);
				ps.setInt(2, fin_year_to);
				rs = ps.executeQuery();
				if (rs.next()) {
					Originated_SL_No = rs.getInt(1);
				}
				Originated_SL_No = Originated_SL_No + 1;
				rs.close();
			} catch (Exception e) {
				System.out.println("exception" + e);
			}
			System.out.println("Originated_SL_No " + Originated_SL_No);

			String[] sd1 = request.getParameter("txtPayment_date").split("/");
			c = new GregorianCalendar(Integer.parseInt(sd1[2]), Integer
					.parseInt(sd1[1]) - 1, Integer.parseInt(sd1[0]));
			java.util.Date d1 = c.getTime();
			txtPayment_date = new Date(d1.getTime());

			try {
				receipt_year = Integer.parseInt(sd1[2]);
			} catch (Exception e) {
				System.out.println("exception" + e);
			}
			System.out.println("receipt_year " + receipt_year);

			try {
				receipt_month = Integer.parseInt(sd1[1]);
			} catch (Exception e) {
				System.out.println("exception" + e);
			}
			System.out.println("receipt_month " + receipt_month);

			try {
			   splVoucher= request.getParameter("txtVoucher_No");
			litVoucher =  splVoucher.split("-");
			   
				//payment_voucher_no = Integer.parseInt(request.getParameter("txtVoucher_No"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			System.out.println("litVoucher **********" + litVoucher[0]);
		    System.out.println("serial number **********" + litVoucher[1]);

			try {
				cmbReason = Integer.parseInt(request.getParameter("cmbReason"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			System.out.println("cmbReason " + cmbReason);
			try {
				txtDebitHead = Integer.parseInt(request
						.getParameter("txtDebitHead"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			//System.out.println("txtDebitHead " + txtDebitHead);

			try {
				cmbMas_SL_type = Integer.parseInt(request
						.getParameter("cmbMas_SL_type"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			//System.out.println("cmbMas_SL_type " + cmbMas_SL_type);

			try {
				cmbMas_SL_Code = Integer.parseInt(request
						.getParameter("cmbMas_SL_Code"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			

			try {
				txtRemarks = request.getParameter("txtParticular");
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			//System.out.println("txtRemarks " + txtRemarks);

			try {
				txtTotalAmt = Double.parseDouble(request
						.getParameter("txtTotalAmt"));
			} catch (Exception e) {
				System.out.println("exception" + e);
			}
			//System.out.println("txtAmount " + txtTotalAmt);
			String cmbDocType = request.getParameter("cmbDocType");
			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

			try {
				try {
					con.clearWarnings();
					con.setAutoCommit(false);
                                      //  System.out.println("be4 insertttttttttttttttttt");
					ps = con.prepareStatement("insert into FAS_TDA_TCA_RAISED_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,ADVICE_TYPE,VOUCHER_DATE,CR_DR_INDICATOR,ACCOUNT_HEAD_CODE,REASON_FOR_TRANSFER,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,TOTAL_AMOUNT,PARTICULARS,ORGINATING_JVR_NO,ORGINATING_JVR_DATE,STATUS,UPDATED_BY_USERID,UPDATED_DATE,TDA_OR_TCA,TRF_ACCOUNTING_UNIT_ID) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					// System.out.println("insert into FAS_ADJUSTMENT_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,ADJUSTMENT_DATE,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,BEHALF_OF_OFFICE_ID,PARTICULARS,TOTAL_AMOUNT,UPDATED_BY_USER_ID,UPDATED_DATE,VOUCHER_STATUS) values ("+cmbAcc_UnitCode+","+cmbOffice_code+",'"+txtCrea_date+"',"+txtCash_year+","+txtCash_Month_hid+","+txtAdvice_No+","+office_id+",'"+particulars+"','"+txtTotalAmt+"','"+update_user+"','"+ts+"','L')");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setInt(3, txtCash_year);
					ps.setInt(4, txtCash_Month_hid);
					ps.setInt(5, Originated_SL_No);
				    if (cmbDocType.equals("P")) {
					ps.setString(6, "P");
                                    }
				    else if (cmbDocType.equals("R")){
                                       ps.setString(6, "R");  
                                        }
                                        else if (cmbDocType.equals("J")){
                                           ps.setString(6, "J"); 
                                            }
					ps.setDate(7, txtCrea_date);
					if (cmbDocType.equals("P")) {
						ps.setString(8, "DR"); // payment=DR else CR
					} else {
						ps.setString(8, "CR"); // payment=DR else CR
					}
					ps.setInt(9, txtDebitHead);
					ps.setInt(10, cmbReason);
					ps.setInt(11, cmbMas_SL_type);
					ps.setInt(12, cmbMas_SL_Code);
					ps.setDouble(13, txtTotalAmt);
					ps.setString(14, txtRemarks);
					//ps.setInt(15, payment_voucher_no);
                                        numconversion=Integer.parseInt( litVoucher[0]);
                                      //  System.out.println("numconversion****"+numconversion);
					 ps.setInt(15,numconversion);
					
					ps.setDate(16, txtPayment_date);
					ps.setString(17, "L");
					ps.setString(18, update_user);
					ps.setTimestamp(19, ts);
					if (cmbDocType.equals("P")) {
						ps.setString(20, "TDACB"); // receipt,journal tcacb else
						// tdacb
					} else {
						ps.setString(20, "TCACB"); // receipt,journal tcacb else
						// tdacb
					}
					// tdacb
					ps.setInt(21, cmbMas_SL_Code);
					errcode = ps.executeUpdate();
				} catch (Exception e) {
					e.printStackTrace();
				}
				if (errcode == 0) {
					System.out.println("redirect");
					sendMessage(response, "The Post TCA Creation Failed ", "ok");
				} else {
					System.out.println("inside 2 nd table");
					String Grid_H_code[] = request.getParameterValues("H_code");
					String Grid_CR_DR_type[] = request
							.getParameterValues("acc_no");
					String Grid_SL_type[] = request
							.getParameterValues("SL_type");
					String Grid_SL_code[] = request
							.getParameterValues("SL_code");
					String ch_dd[] = request.getParameterValues("letterno");
					String ch_no[] = request.getParameterValues("pageno");
					String ch_date[] = request.getParameterValues("letterdate");
					String Grid_sl_amt[] = request.getParameterValues("sl_amt");
					String Grid_particular[] = request
							.getParameterValues("remarkss");

					System.out.println("2 nd table insert");

					int cmbSL_type = 0, cmbSL_Code = 0, pageno = 0, letterno = 0, ref_num;
					double txtsub_Amount = 0;

					int SL_NO = 1, i1 = 0;

					try {
						ps1 = con
								.prepareStatement("Select max(SL_NO) from FAS_TDA_TCA_RAISED_TRN");
						rs = ps1.executeQuery();
						if (rs.next()) {
							i1 = rs.getInt(1);
							SL_NO = SL_NO + i1;
						} else {
							SL_NO = SL_NO;
						}
					} catch (Exception e) {
						e.printStackTrace();
					}
					System.out.println("SL_NO~~~~~~*****************~~~~~~~"
							+ SL_NO);
					try {
						sql = "insert into FAS_TDA_TCA_RAISED_TRN(ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID, CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE,CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, AMOUNT,PARTICULARS,MBOOK_NO,MBOOK_PAGENO,MBOOK_DATE,UPDATED_BY_USERID, UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?)";
						ps = con.prepareStatement(sql);
						for (int k = 0; k < Grid_H_code.length; k++) {
							cmbSL_type = 0;
							cmbSL_Code = 0;
							ref_num = 0;
							txtAcc_HeadCode = 0;
							txtsub_Amount = 0;
							txtsub_Amount = 0;
							bank_id1 = 0;
							branch_id1 = 0;
							account_no = 0;
							SL_NO = SL_NO + 1;
							System.out
									.println("SL_NO~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~"
											+ SL_NO);
							ps.setInt(1, cmbAcc_UnitCode);
							ps.setInt(2, cmbOffice_code);
							ps.setInt(3, txtCash_year);
							ps.setInt(4, txtCash_Month_hid);
							ps.setInt(5, Originated_SL_No);
							ps.setInt(6, SL_NO);

							txtAcc_HeadCode = Integer.parseInt(Grid_H_code[k]);
							//System.out.println("txtAcc_HeadCode&&&&&&&&&&&&&&&&&&&&&&&&&&&"+ txtAcc_HeadCode);
							ps.setInt(7, txtAcc_HeadCode);

							String rad_sub_CR_DR = Grid_CR_DR_type[k];
						//	System.out.println("rad_sub_CR_DR&&&&&&&&&&&&&&&&&&&&&&&&&&&"+ rad_sub_CR_DR);
							ps.setString(8, rad_sub_CR_DR);

							try {
								cmbSL_type = Integer.parseInt(Grid_SL_type[k]);
							} catch (NumberFormatException e) {
								System.out.println("exception" + e);
							}
							
							ps.setInt(9, cmbSL_type);

							try {
								cmbSL_Code = Integer.parseInt(Grid_SL_code[k]);
							} catch (NumberFormatException e) {
								System.out.println("exception ya::" + e);
							}
							System.out.println("cmbSL_Code:::"+cmbSL_Code);
							ps.setInt(10, cmbSL_Code);

							try {
								txtsub_Amount = Double
										.parseDouble(Grid_sl_amt[k]);
							} catch (NumberFormatException e) {
								System.out.println("exception no:::" + e);
							}
							
							ps.setDouble(11, txtsub_Amount);

							
							ps.setString(12, Grid_particular[k]);

							try {
								letterno = Integer.parseInt(ch_dd[k]);
							} catch (NumberFormatException e) {
								System.out.println("exception" + e);
							}
							
							ps.setInt(13, letterno);

							try {
								pageno = Integer.parseInt(ch_no[k]);
							} catch (NumberFormatException e) {
								System.out.println("exception" + e);
							}
							
							ps.setInt(14, pageno);
							
							ps.setString(15, ch_date[k]);

							ps.setString(16, update_user);
							ps.setTimestamp(17, ts);
							
							count++;
							i = ps.executeUpdate();
							
						}
						
                                                if (i > 0) 
                                             {
                                                 int receipt_year_new=0,receipt_month_new=0,txtVoucher_No_new=0;
                                                 String[] sd2 = request.getParameter("txtPayment_date").split("/");
                                                 c = new GregorianCalendar(Integer.parseInt(sd2[2]), Integer
                                                                 .parseInt(sd2[1]) - 1, Integer.parseInt(sd2[0]));
                                                 java.util.Date d2 = c.getTime();
                                                 txtPayment_date = new Date(d2.getTime());
                                             
                                                 
                                                 try {
                                                         txtVoucher_No_new_test = request.getParameter("txtVoucher_No");
                                                     splvou11 =  txtVoucher_No_new_test.split("-");
                                                 } catch (NumberFormatException e) {
                                                         System.out.println("exception" + e);
                                                 }

                                                 try {
                                                         receipt_year_new = Integer.parseInt(sd2[2]);
                                                 } catch (Exception e) {
                                                         System.out.println("exception" + e);
                                                 }
                                             //    System.out.println("receipt_year_new " + receipt_year_new);

                                                 try {
                                                         receipt_month_new = Integer.parseInt(sd2[1]);
                                                 } catch (Exception e) {
                                                         System.out.println("exception" + e);
                                                 }
                                               //  System.out.println("receipt_month_new " + receipt_month_new);
                                             
                                             String updateReference="",updateTrans="";
                                                      if(cmbDocType.equals("P"))
                                                      {
                                                    //  System.out.println("payment update*****************"+cmbAcc_UnitCode+cmbOffice_code+receipt_year_new+receipt_month_new+txtVoucher_No_new+"::::Originated_SL_No"+Originated_SL_No);
                                                          updateReference="update FAS_PAYMENT_MASTER set PAYABLE_VOUCHER_TYPE='T' where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+receipt_year_new+" and CASHBOOK_MONTH="+receipt_month_new+" and VOUCHER_NO="+splvou11[0];
                                                          updateTrans="update FAS_PAYMENT_TRANSACTION set PAYABLE_VOUCHER_NO="+Originated_SL_No+",PAYABLE_VOUCHER_DATE=?  where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+receipt_year_new+" and " +
                                                          		" CASHBOOK_MONTH="+receipt_month_new+" and VOUCHER_NO="+splvou11[0]+" and ACCOUNT_HEAD_CODE=900108 and SL_NO="+splvou11[1]+" and" +
                                                          		" (PAYABLE_VOUCHER_NO =0 or PAYABLE_VOUCHER_NO is null)";
                                                         System.out.println("updateTrans:::"+updateTrans);
                                                      }
                                                      else if(cmbDocType.equals("R")){
                                                  
                                                          updateReference="update FAS_RECEIPT_MASTER set RECEIVABLE_VOUCHER_TYPE='T',RECEIVABLE_VOUCHER_NO="+Originated_SL_No+"," +
                                                          " RECEIVABLE_VOUCHER_DATE=? where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and " +
                                                          " ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+receipt_year_new+" and CASHBOOK_MONTH="+receipt_month_new+" and " +
                                                          " RECEIPT_NO="+numconversion+" and (RECEIVABLE_VOUCHER_NO=0 or RECEIVABLE_VOUCHER_NO is null)";
                                                          System.out.println("updateReference"+updateReference);
                                                          ps2=con.prepareStatement(updateReference);
                                                          ps2.setDate(1,txtPayment_date);
                                                         updateCmd= ps2.executeUpdate();
                                                      }
                                                      else if(cmbDocType.equals("J"))
                                                      {
                                                         updateReference="update FAS_JOURNAL_MASTER set CB_REF_TYPE='T' where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+receipt_year_new+" and CASHBOOK_MONTH="+receipt_month_new+" and VOUCHER_NO="+splvou11[0]+"";
//commanded on 06-02-2020 by kanaga                      updateTrans="update FAS_JOURNAL_TRANSACTION set CB_REF_NO="+Originated_SL_No+",CB_REF_DATE=?  where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+receipt_year_new+" and CASHBOOK_MONTH="+receipt_month_new+" and VOUCHER_NO="+splvou11[0]+" and ACCOUNT_HEAD_CODE=901001 and SL_NO="+splvou11[1]+" and (CB_REF_NO=0 or CB_REF_NO is null)";
                                                         updateTrans="update FAS_JOURNAL_TRANSACTION set CB_TDCA_REF_NO="+Originated_SL_No+",CB_TDCA_REF_DATE=?  where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+receipt_year_new+" and CASHBOOK_MONTH="+receipt_month_new+" and VOUCHER_NO="+splvou11[0]+" and ACCOUNT_HEAD_CODE=901001 and SL_NO="+splvou11[1]+" and (CB_REF_NO=0 or CB_REF_NO is null)";

                                                      }
                                                      System.out.println("cmbDocType*****"+cmbDocType);
                                                      if(!cmbDocType.equals("R"))
                                                      {
			                                                    //  System.out.println("updateReference"+updateReference);
			                                                     ps2=con.prepareStatement(updateReference);
			                                                    int last= ps2.executeUpdate();
			                                                         
			                                                    if(last>0 && !cmbDocType.equals("R")){
			                                                     ps2.close();
			                                                    // System.out.println("updateTrans*****"+updateTrans);
			                                                        ps3=con.prepareStatement(updateTrans);
			                                                        ps3.setDate(1,txtPayment_date);
			                                                        updateCmd=ps3.executeUpdate();
			                                                    }
                                                       }
                                             }  
                                                
					} catch (Exception e) {
						e.printStackTrace();
						con.rollback();
					}
					ps.close();
					System.out.println("Length:  " + count + " "
							+ Grid_H_code.length);
					if (count == Grid_H_code.length) {
						if(updateCmd>0){
						con.commit();
						sendMessage(response, "The Post TCA Sl.No '"+ Originated_SL_No+ "' has been Created Successfully ", "ok");
						}
						else
						{
							System.out.println("Rollback due to duplicates");
							con.rollback();
						}
					} else {
						System.out.println("b4 Rollback");
						sendMessage(response, "The Post TCA Creation Failed ",
								"ok");
						con.rollback();
					}

				}

			}

			catch (Exception e) {
				try {
					con.rollback();
				} catch (SQLException sqle) {
					sqle.printStackTrace();
				}
				e.printStackTrace();
				sendMessage(response, "The Post TCA Creation Failed ", "ok");

			} finally {
				System.out.println("done");
				try {
					con.setAutoCommit(true);
				} catch (SQLException sqle) {
				}
			}

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
