package Servlets.FAS.FAS1.TPA.servlets;

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

public class TPA_Acceptance_Create extends HttpServlet {
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
		PreparedStatement ps2 = null, ps3 = null, ps4,ps5=null;
		ResultSet rs2 = null, rs3 = null, rs4 = null,rs5=null;
		String sql = "";

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

		int count = 0, cmbAcc_UnitCode = 0, cmbOffice_code = 0, cashbook_year = 0, cashbook_month = 0, originated_slno = 0, txtUnitId = 0;
		String xml = null, cmd = "", cr_dr = "";

		/** Get Employee ID */
		try {
			cmd = request.getParameter("command");
		} catch (Exception e) {
			System.out.println(e);
		}

		try {
			cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
		} catch (Exception e) {
			System.out.println("exception" + e);
		}
		System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);

		try {
			cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
		} catch (Exception e) {
			System.out.println("exception" + e);
		}
		System.out.println("cmbOffice_code " + cmbOffice_code);

		try {
			cashbook_year = Integer
					.parseInt(request.getParameter("txtCB_Year"));
		} catch (Exception e) {
			System.out.println("exception" + e);
		}
		System.out.println("cashbook_year " + cashbook_year);

		try {
			cashbook_month = Integer.parseInt(request
					.getParameter("txtCB_Month"));
		} catch (Exception e) {
			System.out.println("exception" + e);
		}
		System.out.println("cashbook_month " + cashbook_month);

		try {
			originated_slno = Integer.parseInt(request
					.getParameter("originated_slno"));
		} catch (Exception e) {
			System.out.println("exception" + e);
		}
		System.out.println("originated_slno " + originated_slno);

		try {
			txtUnitId = Integer.parseInt(request.getParameter("txtUnitId"));
		} catch (Exception e) {
			System.out.println("exception" + e);
		}
		System.out.println("txtUnitId " + txtUnitId);

		try {
			cr_dr = request.getParameter("CR_DR");
		} catch (Exception e) {
			System.out.println("exception" + e);
		}
		System.out.println("cr_dr " + cr_dr);
		String crdr_status = cr_dr;
		if (cr_dr.equals("CR"))
			cr_dr = "TPAOC";
		else
			cr_dr = "TPAOD";
		int slPush = 0;

		System.out.println("cmd:::" + cmd);
		xml = "<response>";
		if (cmd.equalsIgnoreCase("loadVoucher")) {
			xml = xml + "<command>loadVoucher</command>";
			sql = "select voucher_no,ACCOUNTING_UNIT_ID from fas_tpa_master where trf_accounting_unit_id=? and cashbook_year=? and cashbook_month=? and status='L' and (acceptance_status is null or acceptance_status='N') and (accept_voucher_no is null or accept_voucher_no=0) and AUDIT_VERIFY IS NOT NULL and AUDIT_VERIFIED_DATE is not null and tpa_type=?";
			System.out.println(" SQL :: " + sql);
			try {
				slPush = 1;
				ps2 = con.prepareStatement(sql);
				ps2.setInt(1, cmbAcc_UnitCode);
				ps2.setInt(2, cashbook_year);
				ps2.setInt(3, cashbook_month);
				ps2.setString(4, cr_dr);
				rs2 = ps2.executeQuery();
				while (rs2.next()) {

					int voucherNo = rs2.getInt("voucher_no");

					sql = "select REASON_FOR_TRANSFER,AUDIT_VERIFY from fas_tpa_master where trf_accounting_unit_id=? and cashbook_year=? and cashbook_month=?  and VOUCHER_NO=? ";
					ps4 = con.prepareStatement(sql);
					ps4.setInt(1, cmbAcc_UnitCode);
					ps4.setInt(2, cashbook_year);
					ps4.setInt(3, cashbook_month);
					ps4.setInt(4, voucherNo);
					rs4 = ps4.executeQuery();

					rs4.next();

					if (!rs4.getString("REASON_FOR_TRANSFER").equalsIgnoreCase("Others")) {
						System.out.println("verify field:::::"+rs4.getString("AUDIT_VERIFY"));
						if(rs4.getString("AUDIT_VERIFY").equals("Y")){
							/*@NK CHANGED ON 14 AUG 20 INCLUDE CASHBOOK YEAR AND MONTH*/
						ps4 = con
								.prepareStatement("select count(*) as total from FAS_TPA_TRANSACTION where voucher_no=? and sub_ledger_type_code not in(0,5,7,9,10,15) and sl_code_pushed='Y' and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? ");
						/*@NK CHANGED ON 14 AUG 20*/
						ps4.setInt(1, voucherNo);
						ps4.setInt(2, cashbook_year);
						ps4.setInt(3, cashbook_month);
						rs4 = ps4.executeQuery();

						if (rs4.next()) {
							//if (rs4.getInt("total") >= 0) {  by nanda on 14aug20
							if (rs4.getInt("total") > 0) {
								ps3 = con
										.prepareStatement("select * from FAS_TPA_STATUS where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TPA_TYPE=? and TPA_STATUS='Y'");
								ps3.setInt(1, rs2.getInt("ACCOUNTING_UNIT_ID"));
								ps3.setInt(2, cashbook_year);
								ps3.setInt(3, cashbook_month);
								ps3.setString(4, crdr_status);
								rs3 = ps3.executeQuery();
								if (rs3.next()) {

									xml += "<voucher_no>"
											+ rs2.getInt("voucher_no")
											+ "</voucher_no>";
									count++;
								}
							}
						}
					}
					} else {
						System.out.println("else");
						String push="SELECT COUNT(*) AS total "+
						"	FROM FAS_TPA_TRANSACTION T,FAS_tpa_master m "+
						"	WHERE m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "+
							" and m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID "+
							" 	and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH "+
							" 	and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR "+
							" 	and m.VOUCHER_NO=t.VOUCHER_NO "+
							" 	and m.STATUS='L' "+
							" 	and m.voucher_no              =? "+
							" AND t.sub_ledger_type_code NOT IN (0,5,9,10,15,7,18,6,2,17) "+
							" AND t.sl_code_pushed           IS NULL "+
//							" AND m.TRF_ACCOUNTING_UNIT_ID        ="+rs2.getInt("ACCOUNTING_UNIT_ID"); //changed on 10-01-2018 sathya mam told
							" AND m.ACCOUNTING_UNIT_ID        ="+rs2.getInt("ACCOUNTING_UNIT_ID");
						System.out.println(push);
						ps4 = con.prepareStatement(push);
							ps4.setInt(1, voucherNo);
							rs4 = ps4.executeQuery();
							while(rs4.next()){
								
								int tt=rs4.getInt("total");
								System.out.println("while:::"+tt);
								if(tt>0)
								{
									count=0;
								}
								else{
						xml += "<voucher_no>" + rs2.getInt("voucher_no")
								+ "</voucher_no>";
						count++;
								}
						
							}
					}
				}
System.out.println("count--->"+count);
System.out.println("slPush--->"+slPush);
				
				
				if (count == 0 && slPush == 1)
					xml += "<flag>slnotpush</flag>";
				else if (count == 0 && slPush == 0)
					xml += "<flag>NoData</flag>";
				else
					xml += "<flag>success</flag>";

			} catch (Exception e) {
				System.out.println("Exception in loadVoucher..." + e);
				xml += "<flag>" + e.getMessage() + "</flag>";
			}
		} else if (cmd.equalsIgnoreCase("loadVoucherDetails")) {

			xml = xml + "<command>loadVoucherDetails</command>";
			sql = "select a.*, b.sub_ledger_type_desc,c.account_head_desc ,T.CONTRACTOR_ID from\n"
					+ "(\n"
					+ "select mst.ACCOUNTING_UNIT_ID,mst.ACCOUNTING_FOR_OFFICE_ID, \n"
					+ "  to_char(mst.voucher_date,'dd/mm/yyyy') as voucher_date,"
					+ "  mst.reason_for_transfer,"
					+ "  trn.account_head_code, \n"
					+ "  trn.cr_dr_indicator, \n"
					+ "  trn.sl_no, \n"
					+ "  trn.sub_ledger_type_code, \n"
					+ "  trn.sub_ledger_code, \n"
					+ "  trim(to_char(trn.amount,'99999999999999.99'))as amount, \n"
					+ "  trn.particulars  , trn.VOUCHER_NO, \n"
					+ "  to_char(TRN.SL_PUSHED_DATE,'dd/mm/yyyy') as SL_PUSHED_DATE , trn.tpa_accepting_slcode\n"	/*	@NK on 26Aug20*/
					+ " from fas_tpa_master mst,fas_tpa_transaction trn\n"
					+ " where\n"
					+ "  mst.accounting_unit_id= trn.accounting_unit_id and\n"
					+ "  mst.accounting_for_office_id= trn.accounting_for_office_id and\n"
					+ "  mst.cashbook_month= trn.cashbook_month and\n"
					+ "  mst.cashbook_year= trn.cashbook_year and\n"
					+ "  mst.voucher_no= trn.voucher_no and\n"
					+ "  mst.trf_accounting_unit_id=? and\n"
					+ "  mst.cashbook_month=? and\n"
					+ "  mst.cashbook_year=? and\n"
					+ "  mst.voucher_no=? and\n"
					+ "  mst.status='L'\n"
					+ ")a left outer join com_mst_sl_types b on a.sub_ledger_type_code=b.sub_ledger_type_code\n "
					//siva added extra check for tranfer sl_code inserted
					+ "LEFT OUTER JOIN PMS_CONT_REQUEST_REGN T ON A.SUB_LEDGER_CODE=T.OLD_SL_CODE AND a.accounting_for_office_id=t.OLD_OFFICE_ID and a.VOUCHER_NO=T.TPA_VOUCHER_NO and t.office_id=?\n"
					//end					
					+ "left outer join com_mst_account_heads c on a.account_head_code=c.account_head_code";
			System.out.println(" SQL :: " + sql);
			try {
				ps2 = con.prepareStatement(sql);
				ps2.setInt(1, cmbAcc_UnitCode);
				ps2.setInt(2, cashbook_month);
				ps2.setInt(3, cashbook_year);
				ps2.setInt(4, originated_slno);
				ps2.setInt(5, cmbOffice_code);
				rs2 = ps2.executeQuery();
				while (rs2.next()) {
					xml = xml + "<voucher_date><![CDATA["+rs2.getString("voucher_date")+"]]></voucher_date>";
					xml = xml + "<reason_for_transfer><![CDATA["+rs2.getString("reason_for_transfer")+"]]></reason_for_transfer>";
					xml += "<account_head_code><![CDATA["+rs2.getString("account_head_code")+"]]></account_head_code>";
					xml += "<account_head_code_desc><![CDATA["+rs2.getString("account_head_desc")+"]]></account_head_code_desc>";
					xml += "<SL_PUSHED_DATE><![CDATA["+rs2.getString("SL_PUSHED_DATE")+"]]></SL_PUSHED_DATE>";
					xml += "<tpa_accepting_slcode><![CDATA[" + rs2.getInt("tpa_accepting_slcode")+ "]]></tpa_accepting_slcode>"; /*	@NK on 26Aug20*/
					
					/*siva changes 25-01-2016

*xml = xml + "<voucher_date>"
							+ rs2.getString("voucher_date") + "</voucher_date>";
					xml = xml + "<reason_for_transfer>"
							+ rs2.getString("reason_for_transfer")
							+ "</reason_for_transfer>";
					xml += "<account_head_code>"
							+ rs2.getString("account_head_code")
							+ "</account_head_code>";
					xml += "<account_head_code_desc>"
							+ rs2.getString("account_head_desc")
							+ "</account_head_code_desc>";
*/
					
					ps5=con.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");	
					int acunit=rs2.getInt("ACCOUNTING_UNIT_ID");
					ps5.setInt(1, rs2.getInt("ACCOUNTING_UNIT_ID"));
					rs5=ps5.executeQuery();
						if(rs5.next()){
					xml += "<acc_unitname><![CDATA["+acunit+"-"+ rs5.getString("ACCOUNTING_UNIT_NAME")+ "]]></acc_unitname>";		
					
						}
					
					
					

					if (rs2.getString("cr_dr_indicator").equalsIgnoreCase("CR")) {
						xml += "<cr_dr_indicator>DR</cr_dr_indicator>";
					} else {
						xml += "<cr_dr_indicator>CR</cr_dr_indicator>";
					}

					xml += "<slno><![CDATA[" + rs2.getString("sl_no") + "]]></slno>";
					xml += "<amount><![CDATA[" + rs2.getString("amount") + "]]></amount>";
					System.out.println("Sub code ::: "
							+ rs2.getInt("sub_ledger_type_code"));
					xml += "<sub_ledger_type_code><![CDATA["+ rs2.getInt("sub_ledger_type_code")+ "]]></sub_ledger_type_code>";
					xml += "<Contractor_Id><![CDATA[" + rs2.getInt("CONTRACTOR_ID")+ "]]></Contractor_Id>";
					xml += "<sub_ledger_code><![CDATA[" + rs2.getInt("sub_ledger_code")+ "]]></sub_ledger_code>";
					if (rs2.getString("particulars") == null)
						xml += "<particulars>--</particulars>";
					else
						xml += "<particulars><![CDATA[" + rs2.getString("particulars")+ "]]></particulars>";
					if (rs2.getInt("sub_ledger_type_code") != 0
							&& rs2.getInt("sub_ledger_code") != 0) {
						xml += "<sub_ledger_type_desc><![CDATA["+ rs2.getString("sub_ledger_type_desc")+"]]></sub_ledger_type_desc>";
						SL_TYPE_CODE_NAME_GENERAL obj_gen = new SL_TYPE_CODE_NAME_GENERAL();
						ResultSet rs_get = obj_gen.getResult_General(rs2
								.getInt("ACCOUNTING_UNIT_ID"), rs2
								.getInt("ACCOUNTING_FOR_OFFICE_ID"), rs2
								.getInt("sub_ledger_type_code"), rs2
								.getInt("sub_ledger_code"), 0);
						if (rs_get != null) {
							String slcheck = "";
							while (rs_get.next()) {
								if (rs_get.getInt("cid") == rs2
										.getInt("sub_ledger_code")) {
									slcheck = rs_get.getString("cname");

									xml = xml + "<sub_ledger_desc><![CDATA[" + slcheck+ "]]></sub_ledger_desc>";
								}
							}
							rs_get.close();
							if (slcheck == "") {
								xml += "<sub_ledger_desc>--</sub_ledger_desc>";
							}
						} else {
							System.out.println("null result set");
							xml = xml + "<sub_ledger_desc>--</sub_ledger_desc>";
						}
					} else {
						xml = xml + "<sub_ledger_desc>--</sub_ledger_desc>";
						xml += "<sub_ledger_type_desc>--</sub_ledger_type_desc>";
					}
					count++;
				}
				if (count == 0)
					xml += "<flag>NoData</flag>";
				else
					xml += "<flag>success</flag>";

			} catch (Exception e) {
				System.out.println("Exception in loadVoucherDetails..." + e);
				xml += "<flag>" + e.getMessage() + "</flag>";
			}
			System.out.println("loadVoucherDetails.................-----++++"+xml);
		}
		
		else if (cmd.equalsIgnoreCase("loadtranferunit")) {
			xml = xml + "<command>loadtranferunit</command>";
			sql = "select voucher_no,ACCOUNTING_UNIT_ID,REASON_FOR_TRANSFER from fas_tpa_master where trf_accounting_unit_id=? and cashbook_year=? and cashbook_month=? and status='L' and (acceptance_status is null or acceptance_status='N') and (accept_voucher_no is null or accept_voucher_no=0) and AUDIT_VERIFY='Y' and tpa_type=?";
			System.out.println(" SQL :: " + sql);
			try {
				slPush = 1;
				ps2 = con.prepareStatement(sql);
				ps2.setInt(1, cmbAcc_UnitCode);
				ps2.setInt(2, cashbook_year);
				ps2.setInt(3, cashbook_month);
				ps2.setString(4, cr_dr);
				rs2 = ps2.executeQuery();
				while (rs2.next()) {

					int voucherNo = rs2.getInt("voucher_no");
					
					sql = "select REASON_FOR_TRANSFER from fas_tpa_master where trf_accounting_unit_id=? and cashbook_year=? and cashbook_month=?  and VOUCHER_NO=?";
					ps4 = con.prepareStatement(sql);
					ps4.setInt(1, cmbAcc_UnitCode);
					ps4.setInt(2, cashbook_year);
					ps4.setInt(3, cashbook_month);
					ps4.setInt(4, voucherNo);
					rs4 = ps4.executeQuery();

					rs4.next();

					if (!rs4.getString("REASON_FOR_TRANSFER").equalsIgnoreCase(
							"Others")) {
						
						ps4 = con
								.prepareStatement("select count(*) as total from FAS_TPA_TRANSACTION where voucher_no=? and sub_ledger_type_code not in(0,5,9) and sl_code_pushed='Y' ");
						
						ps4.setInt(1, voucherNo);
						rs4 = ps4.executeQuery();

						if (rs4.next()) {
							if (rs4.getInt("total") == 1) {
								ps3 = con
										.prepareStatement("select * from FAS_TPA_STATUS where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and TPA_TYPE=? and TPA_STATUS='Y'");
								ps3.setInt(1, rs2.getInt("ACCOUNTING_UNIT_ID"));
								ps3.setInt(2, cashbook_year);
								ps3.setInt(3, cashbook_month);
								ps3.setString(4, crdr_status);
								rs3 = ps3.executeQuery();
								if (rs3.next()) {

									/*xml += "<voucher_no>"
											+ rs2.getInt("voucher_no")
											+ "</voucher_no>";*/
									
								ps5=con.prepareStatement("select ACCOUNTING_UNIT_NAME FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");	
								ps5.setInt(1, rs2.getInt("ACCOUNTING_UNIT_ID"));
								rs5=ps5.executeQuery();
									if(rs5.next()){
								xml += "<acc_unitname>"+rs2.getInt("ACCOUNTING_UNIT_ID")+"-"+ rs5.getString("ACCOUNTING_UNIT_NAME")+ "</acc_unitname>";									
								xml += "<voucher_no>" + rs2.getInt("voucher_no")+ "</voucher_no>";	
								xml += "<reason>" + rs4.getString("REASON_FOR_TRANSFER")+ "</reason>";	
									}
									
									count++;
								}
							}
						}
					} else {
						
						ps5=con.prepareStatement("select ACCOUNTING_UNIT_NAME FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");	
						ps5.setInt(1, rs2.getInt("ACCOUNTING_UNIT_ID"));
						rs5=ps5.executeQuery();
							if(rs5.next()){
						xml += "<acc_unitname>"+ rs2.getInt("ACCOUNTING_UNIT_ID")+"-"+rs5.getString("ACCOUNTING_UNIT_NAME")+ "</acc_unitname>";									
						xml += "<voucher_no>" + rs2.getInt("voucher_no")+ "</voucher_no>";	
						xml += "<reason>" + rs4.getString("REASON_FOR_TRANSFER")+ "</reason>";	
							}
						
						count++;
					}
				}

				if (count == 0 && slPush == 1)
					xml += "<flag>slnotpush</flag>";
				else if (count == 0 && slPush == 0)
					xml += "<flag>NoData</flag>";
				else
					xml += "<flag>success</flag>";

			} catch (Exception e) {
				System.out.println("Exception in loadVoucher..." + e);
				xml += "<flag>" + e.getMessage() + "</flag>";
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
		PreparedStatement ps = null, ps1 = null, ps2 = null;
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

	      try
	      {
                  ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                  String ConnectionString="";
                  String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                  String strdsn=rs1.getString("Config.DSN");
                  String strhostname=rs1.getString("Config.HOST_NAME");
                  String strportno=rs1.getString("Config.PORT_NUMBER");
                  String strsid=rs1.getString("Config.SID");
                  String strdbusername=rs1.getString("Config.USER_NAME");
                  String strdbpassword=rs1.getString("Config.PASSWORD");
                  ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection                    Class.forName(strDriver.trim());
                  con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
          }
          catch(Exception e)
          {
              	System.out.println("Exception in opening connection :"+e);
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
			System.out.println("Inside Add");
			String CONTENT_TYPE = "text/html; charset=windows-1252";
			response.setContentType(CONTENT_TYPE);

			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
			Calendar c;
			int txtAcc_HeadCode = 0, cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid = 0, txtCash_year = 0, txtUnitId = 0, txtAcHead = 0, originate_Acc_unit = 0, originate_office_id = 0;
			int count = 0, originated_year = 0, originated_month = 0, originated_slno = 0, orgin_no = 0, accept_no = 0;
			Date txtCrea_date = null, originated_date = null;
			String particulars = null, tpa_type = null, isAccept = null, notAccepting_reason = null, sql = null, cr_dr = null, txtReason = null;
			Boolean flag = true;

			// changes here
			String update_user = (String) session.getAttribute("UserId");
			long l = System.currentTimeMillis();
			Timestamp ts = new Timestamp(l);
			int errcode = 0;

			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

			try {
				cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("cmbAcc_UnitCode"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}

			try {
				cmbOffice_code = Integer.parseInt(request
						.getParameter("cmbOffice_code"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}

			try {
				originated_year = Integer.parseInt(request
						.getParameter("txtCB_Year"));
			} catch (Exception e) {
				System.out.println("exception" + e);
			}

			try {
				originated_month = Integer.parseInt(request
						.getParameter("txtCB_Month"));
			} catch (Exception e) {
				System.out.println("exception" + e);
			}

			String orginatedDate = request.getParameter("originated_date");

			try {
				String[] sd1 = request.getParameter("originated_date").split(
						"/");
				c = new GregorianCalendar(Integer.parseInt(sd1[2]), Integer
						.parseInt(sd1[1]) - 1, Integer.parseInt(sd1[0]));
				java.util.Date d1 = c.getTime();
				originated_date = new Date(d1.getTime());
			} catch (Exception e) {
				System.out.println("Err in originated_date " + e.getMessage());
			}

			try {
				cr_dr = request.getParameter("Org_CR_DR");
			} catch (Exception e) {
				System.out.println("exception" + e);
			}

			if (cr_dr.equals("CR"))
				tpa_type = "TPAAC";
			else
				tpa_type = "TPAAD";

			try {
				originated_slno = Integer.parseInt(request
						.getParameter("originated_slno"));
			} catch (Exception e) {
				System.out.println("exception" + e);
			}

			String[] sd2 = request.getParameter("txtCreate_Date").split("/");
			c = new GregorianCalendar(Integer.parseInt(sd2[2]), Integer
					.parseInt(sd2[1]) - 1, Integer.parseInt(sd2[0]));
			java.util.Date d2 = c.getTime();
			txtCrea_date = new Date(d2.getTime());

			try {
				txtCash_year = Integer.parseInt(sd2[2]);
			} catch (Exception e) {
				System.out.println("exception" + e);
			}

			try {
				txtCash_Month_hid = Integer.parseInt(sd2[1]);
			} catch (Exception e) {
				System.out.println("exception" + e);
			}

			int accepting_SL_No = 0;
			int fin_year_from = 0, fin_year_to = 0;

			try {
				txtAcHead = Integer.parseInt(request
						.getParameter("txtAcc_HeadCode"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}

			txtReason = request.getParameter("txtReason");
			String tranferunitid[] = request.getParameter("tranferunitid").split("-");
			txtUnitId=Integer.parseInt(tranferunitid[0]);
			System.out.println("txtUnitId::"+txtUnitId);
			String Amount = request.getParameter("amount");

			particulars = request.getParameter("particulars");

			String orgUnitName = "";
			String accUnitName = "";

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
				sql = "SELECT VOUCHER_NO FROM FAS_TPA_MASTER GROUP BY VOUCHER_NO HAVING  "
						+ " VOUCHER_NO =(select max(VOUCHER_NO) from FAS_TPA_MASTER where to_date(CASHBOOK_MONTH||'-'||CASHBOOK_YEAR,'mm-yyyy') between to_date(4||'-'||?,'mm-yyyy') and to_date(3||'-'||?,'mm-yyyy'))";
				ps = con.prepareStatement(sql);
				ps.setInt(1, fin_year_from);
				ps.setInt(2, fin_year_to);
				rs = ps.executeQuery();
				if (rs.next()) {
					accepting_SL_No = rs.getInt(1);
				}
				accepting_SL_No = accepting_SL_No + 1;
				rs.close();

			} catch (Exception e) {
				System.out.println("exception" + e);
			}

			try {
				isAccept = request.getParameter("isAccept");
			} catch (Exception e) {
				System.out.println("isAccept " + e);
			}

			try {
				notAccepting_reason = request.getParameter("notAccepting");
			} catch (Exception e) {
				System.out.println("notAccepting " + e);
			}

			if (isAccept.equals("Y"))
				notAccepting_reason = "";

			String Grid_H_code[] = request.getParameterValues("H_code");
			String Grid_SL_type[] = request.getParameterValues("SL_type");
			String slcode[]=request.getParameterValues("SL_code");
			
			String Grid_SL_code[] = request.getParameterValues("SL_code");
			System.out.println("Grid_SL_code>>>>"+request.getParameterValues("SL_code"));
			/*@NK ON 1AUG2020*/
			String Grid_ORG_SL_code[] = request.getParameterValues("orgSL_code");
			/*@NK ON 1AUG2020*/
			String Grid_CR_DR[] = request.getParameterValues("CR_DR_type");
			String Grid_Amt[] = request.getParameterValues("sl_amt");
			String Grid_particular[] = request
					.getParameterValues("sl_particular");

			try {
				// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~TPA
				// Insertion~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				if (isAccept.equals("Y")) {
					con.clearWarnings();
					con.setAutoCommit(false);
					ps = con
							.prepareStatement("insert into FAS_TPA_MASTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,TPA_TYPE,VOUCHER_DATE,TRF_ACCOUNTING_UNIT_ID,PARTICULARS,STATUS,UPDATED_BY_USERID,UPDATED_DATE,ACCEPTANCE_STATUS) values (?,?,?,?,?,?,?,?,?,?,?,?,?)");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setInt(3, txtCash_year);
					ps.setInt(4, txtCash_Month_hid);
					ps.setInt(5, accepting_SL_No);
					ps.setString(6, tpa_type);
					ps.setDate(7, txtCrea_date);
					ps.setInt(8, txtUnitId);
					ps.setString(9, particulars);
					ps.setString(10, "L");
					ps.setString(11, update_user);
					ps.setTimestamp(12, ts);
					ps.setString(13, "Y");
					errcode = ps.executeUpdate();
					//errcode=1;  //nk
					if (errcode == 0) {
						System.out.println("redirect");
						flag = false;
					} else {
						/*
						 * if(isAccept.equals("Y")) {
						 */
						ps.close();
						System.out.println("inside 2 nd table");
						int SL_NO = 0, cmbSL_type = 0, cmbSL_Code = 0;
						double txtsub_Amount = 0;
						System.out.println("HCode"+Grid_H_code.length);

						try {
							ps.close();
							sql = "insert into FAS_TPA_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,AMOUNT,PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
							ps = con.prepareStatement(sql);
							
							for (int k = 0; k < Grid_H_code.length; k++) {
								cmbSL_type = 0;
								cmbSL_Code = 0;
								txtAcc_HeadCode = 0;
								txtsub_Amount = 0;
								SL_NO++;
								ps.setInt(1, cmbAcc_UnitCode);
								System.out.println(cmbAcc_UnitCode+"cmbAcc_UnitCode");
								ps.setInt(2, cmbOffice_code);
								System.out.println(cmbOffice_code+"cmbOffice_code");
								ps.setInt(3, txtCash_year);
								System.out.println(txtCash_year+"txtCash_year");
								ps.setInt(4, txtCash_Month_hid);
								System.out.println(txtCash_Month_hid+"txtCash_Month_hid");
								ps.setInt(5, accepting_SL_No);
								System.out.println(accepting_SL_No+"accepting_SL_No");
								ps.setInt(6, SL_NO);
								System.out.println(SL_NO+"SL_NO");
								txtAcc_HeadCode = Integer
										.parseInt(Grid_H_code[k]);
								ps.setInt(7, txtAcc_HeadCode);
								System.out.println(txtAcc_HeadCode+"txtAcc_HeadCode");
								String rad_sub_CR_DR = Grid_CR_DR[k];
								ps.setString(8, rad_sub_CR_DR);
								System.out.println(rad_sub_CR_DR+"rad_sub_CR_DR");
								try {
									cmbSL_type = Integer
											.parseInt(Grid_SL_type[k]);
								} catch (NumberFormatException e) {
									System.out.println("exception" + e);
								}
								ps.setInt(9, cmbSL_type);
								System.out.println(cmbSL_type+"cmbSL_type");
								try {
									cmbSL_Code = Integer
											.parseInt(Grid_SL_code[k]);
								} catch (NumberFormatException e) {
									System.out.println("exception" + e);
								}
								ps.setInt(10, cmbSL_Code);
								System.out.println(cmbSL_Code+"cmbSL_Code");
								try {
									txtsub_Amount = Double
											.parseDouble(Grid_Amt[k]);
								} catch (NumberFormatException e) {
									System.out.println("exception" + e);
								}
								ps.setDouble(11, txtsub_Amount);
								System.out.println(txtsub_Amount+"txtsub_Amount");
								ps.setString(12, Grid_particular[k]);
								System.out.println(Grid_particular[k]+"Grid_particular");
								ps.setString(13, update_user);
								System.out.println(update_user+"update_user");
								ps.setTimestamp(14, ts);
								System.out.println(ts+"ts");
								int i = ps.executeUpdate();
								//int i=1; //nk
								System.out.println("i value--->"+i);
								
								if (i > 0) {
									count++;
									System.out
											.println("------------------------"
													+ SL_NO
													+ " inserted successfully");
								}

							}
							System.out
									.println("After insert :: the count and grid count is :: "
											+ count + "  " + Grid_H_code.length);
							if (count == Grid_H_code.length) {
								System.out.println("inside update");
								try {
									ps.close();
									ps = con
											.prepareStatement("update FAS_TPA_MASTER set ACCEPTANCE_STATUS=?,REASON_FOR_NOT_ACCEPTING=?,ACCEPTING_SLNO=?,ACCEPTING_DATE=? where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? ");
									ps.setString(1, isAccept);
									ps.setString(2, notAccepting_reason);
									ps.setInt(3, accepting_SL_No);
									ps.setDate(4, txtCrea_date);
									ps.setInt(5, cmbAcc_UnitCode);
									ps.setInt(6, originated_year);
									ps.setInt(7, originated_month);
									ps.setInt(8, originated_slno);
									int kk = ps.executeUpdate();
									//int kk=1;//nk
									if (kk > 0) {
										System.out.println("b4 commit");
										flag = true;
									} else {
										flag = false;
									}

								} catch (Exception e) {
									flag = false;
								}
							} else
								flag = false;
						} catch (Exception e) {
							flag = false;
							e.getStackTrace();
						}
					}
					System.out.println("flag ::: " + flag);
				} else {
					try {
						ps.close();
						ps = con
								.prepareStatement("update FAS_TPA_MASTER set ACCEPTANCE_STATUS=?,REASON_FOR_NOT_ACCEPTING=? where TRF_ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? ");
						ps.setString(1, isAccept);
						ps.setString(2, notAccepting_reason);
						ps.setInt(3, cmbAcc_UnitCode);
						ps.setInt(4, originated_year);
						ps.setInt(5, originated_month);
						ps.setInt(6, originated_slno);
						int kk = ps.executeUpdate();
						//int kk=1;//nk
						if (kk > 0) {
							flag = true;
						} else
							flag = false;
					} catch (Exception e) {
						flag = false;
						System.out.println("Err in else part updation ::: "
								+ e.getMessage());
					}
				}
				// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~End TPA
				// Insertion~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~*/

				/*
				 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~Journal Entry for
				 * TPAOC/TPAOD and TPAAC/TPAAD
				 * ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~
				 */
				if (flag == true) {
					System.out.println("Journal started");
					int Journal_Vou_No = 0, j = 0, sub_code = 0, dep_rate = 0;

					try {
						/*----------------- Find accounting_unit_id and office_id for the originated unit----------------------------- */
						try {

							ps1 = con
									.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID from FAS_TPA_MASTER where TRF_ACCOUNTING_UNIT_ID=? and VOUCHER_NO=? and VOUCHER_DATE=? ");
							ps1.setInt(1, cmbAcc_UnitCode);
							ps1.setInt(2, originated_slno);
							ps1.setDate(3, originated_date);
							rs = ps1.executeQuery();
							while (rs.next()) {
								originate_Acc_unit = rs
										.getInt("ACCOUNTING_UNIT_ID");
								originate_office_id = rs
										.getInt("ACCOUNTING_FOR_OFFICE_ID");
							}
							ps1.close();
						} catch (Exception e) {
							System.out
									.println("Err in TPAO record selection ::: "
											+ e.getMessage());
						}
						System.out.println("originate_Acc_unit ::: "
								+ originate_Acc_unit
								+ " originate_office_id ::: "
								+ originate_office_id);
						/*------------------End---------------------------------------------------------------------------------------*/

						for (j = 0; j < 2; j++) {
							/*---find maximum voucher no. accordind to originated unit as well as acceptes unit---------------------------------------- */
							Journal_Vou_No = 0;
							try {
								ps = con
										.prepareStatement("select VOUCHER_NO from FAS_JOURNAL_MASTER GROUP BY VOUCHER_NO HAVING VOUCHER_NO =(select max(VOUCHER_NO) from FAS_JOURNAL_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?)");
								if (j == 0) {
									ps.setInt(1, originate_Acc_unit);
									ps.setInt(2, originate_office_id);
								} else {
									ps.setInt(1, cmbAcc_UnitCode);
									ps.setInt(2, cmbOffice_code);
								}
								ps.setInt(3, txtCash_year);
								ps.setInt(4, txtCash_Month_hid);
								rs = ps.executeQuery();
								if (rs.next()) {
									Journal_Vou_No = rs.getInt(1);
								}
								rs.close();
								ps.close();

								ps = con
										.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID in(?,?) order by ACCOUNTING_UNIT_NAME");
								ps.setInt(1, originate_Acc_unit);
								ps.setInt(2, cmbAcc_UnitCode);
								rs = ps.executeQuery();
								while (rs.next()) {
									if (rs.getInt("ACCOUNTING_UNIT_ID") == originate_Acc_unit)
										orgUnitName = rs
												.getString("ACCOUNTING_UNIT_NAME");
									else
										accUnitName = rs
												.getString("ACCOUNTING_UNIT_NAME");
								}
								rs.close();
								ps.close();

							} catch (Exception e) {
								System.out.println("exception" + e);
							}
							/*-----------------------------------------------------------------------------------------------------------------------*/

							System.out.println("JJJ " + j);
							System.out
									.println("Journal_Vou_No Before Increment"
											+ Journal_Vou_No);
							Journal_Vou_No = Journal_Vou_No + 1;
							System.out.println("Journal_Vou_No After Increment"
									+ Journal_Vou_No);
							try {

								String remarks = "GJV Created on TPA on "
										+ txtReason + ".VoucherNo "
										+ Journal_Vou_No + " and Date "
										+ orginatedDate + " For Amount "
										+ Amount + " To " + accUnitName + " ";
								String remarksone = "GJV Created on TPA on "
										+ txtReason + ".VoucherNo "
										+ Journal_Vou_No + " and Date "
										+ txtCrea_date + " For Amount "
										+ Amount + " From " + orgUnitName + " ";
								ps1 = con
										.prepareStatement("insert into fas_journal_master(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,VOUCHER_DATE,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,JOURNAL_TYPE_CODE,TOTAL_TRN_RECORDS,MODE_OF_CREATION,CREATED_BY_MODULE,JOURNAL_STATUS,UPDATED_BY_USER_ID,UPDATED_DATE,SUB_LEDGER_CODE,DEPRECIATION_RATE,REMARKS) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
								System.out.println("JJJ ::: " + j);
								if (j == 0) {
									ps1.setInt(1, originate_Acc_unit);
									ps1.setInt(2, originate_office_id);
									ps1.setDate(3, txtCrea_date);
									ps1.setInt(4, txtCash_year);
									ps1.setInt(5, txtCash_Month_hid);
									ps1.setInt(6, Journal_Vou_No);
									orgin_no = Journal_Vou_No;
									if (cr_dr.equals("CR"))
										ps1.setInt(7, 78);
									else
										ps1.setInt(7, 80);
								} else {
									ps1.setInt(1, cmbAcc_UnitCode);
									ps1.setInt(2, cmbOffice_code);
									ps1.setDate(3, txtCrea_date);
									ps1.setInt(4, txtCash_year);
									ps1.setInt(5, txtCash_Month_hid);
									ps1.setInt(6, Journal_Vou_No);
									accept_no = Journal_Vou_No;
									if (cr_dr.equals("CR"))
										ps1.setInt(7, 79);
									else
										ps1.setInt(7, 81);
								}
								ps1.setInt(8, Grid_H_code.length);
								ps1.setString(9, "A");
								ps1.setString(10, "GJV");
								ps1.setString(11, "L");
								ps1.setString(12, update_user);
								ps1.setTimestamp(13, ts);
								ps1.setInt(14, sub_code);
								ps1.setInt(15, dep_rate);

								if (j == 0) {
									ps1.setString(16, remarks);
								} else {
									ps1.setString(16, remarksone);
								}

								int kk = ps1.executeUpdate();
								//int kk=1; //nk
								if (kk > 0) {
									System.out
											.println("inside journal transaction");
									int SL_NO = 0, trn_count = 0, cmbSL_type = 0, cmbSL_Code = 0;
									double txtsub_Amount = 0;
									System.out.println("length::"+Grid_H_code.length);
									for (int ll = 0; ll < Grid_H_code.length; ll++) {

										cmbSL_type = 0;
										cmbSL_Code = 0;
										txtAcc_HeadCode = 0;
										txtsub_Amount = 0;
										SL_NO++;
										int CB_REF_NO=0;
//										ps2 = con.prepareStatement("insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,AMOUNT,PARTICULARS,CB_REF_NO,CB_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)"); // commanded on 30-05-2018
										ps2 = con.prepareStatement("insert into FAS_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,VOUCHER_NO,SL_NO,ACCOUNT_HEAD_CODE,CR_DR_INDICATOR,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,AMOUNT,PARTICULARS,CB_TPA_REF_NO,CB_TPA_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,CB_REF_NO)values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
										String rad_sub_CR_DR = Grid_CR_DR[ll];
										if (j == 0) {
											ps2.setInt(1, originate_Acc_unit);
											System.out.println("part1 ");
											ps2.setInt(2, originate_office_id);
											
										} else {
											System.out.println("part1 ");
											ps2.setInt(1, cmbAcc_UnitCode);
											ps2.setInt(2, cmbOffice_code);
											
										}
										System.out.println("part2 ");
										ps2.setInt(3, txtCash_year);
										ps2.setInt(4, txtCash_Month_hid);
										ps2.setInt(5, Journal_Vou_No);
										ps2.setInt(6, SL_NO);
										System.out.println("part3 ");
										txtAcc_HeadCode = Integer.parseInt(Grid_H_code[ll]);
										ps2.setInt(7, txtAcc_HeadCode);
										if (j == 0) {
											System.out.println("originating:::");
											System.out.println("txtAcc_HeadCode::"+txtAcc_HeadCode);
											if(txtAcc_HeadCode==900301)
											{
												System.out.println("commmm");
												rad_sub_CR_DR="DR";
											}
											else if(txtAcc_HeadCode==620101)
											{
												rad_sub_CR_DR="CR";
											}
											else
											{
												if(rad_sub_CR_DR.equals("CR"))
												{
													rad_sub_CR_DR="DR";
												}
												else if(rad_sub_CR_DR.equals("DR"))
												{
													rad_sub_CR_DR="CR";
												}
											}
											
											
										}
										
										System.out.println("part4 ");
										ps2.setString(8, rad_sub_CR_DR);
										
										try {
											cmbSL_type = Integer
													.parseInt(Grid_SL_type[ll]);
										} catch (NumberFormatException e) {
											System.out.println("exception" + e);
										}
										ps2.setInt(9, cmbSL_type);
										System.out.println("part5 ");
										/*NK ON 02SEP2020*/
										if (j==0)
										{
											try {
												cmbSL_Code = Integer
														.parseInt(Grid_ORG_SL_code[ll]);
											} catch (NumberFormatException e) {
												System.out.println("exception" + e);
											}	
										}
										else
										{
											try {
												cmbSL_Code = Integer
														.parseInt(Grid_SL_code[ll]);
											} catch (NumberFormatException e) {
												System.out.println("exception" + e);
											}		
										}
										
										/*NK ON 02SEP2020*/
										
										/*try {
											cmbSL_Code = Integer
													.parseInt(Grid_SL_code[ll]);
										} catch (NumberFormatException e) {
											System.out.println("exception" + e);
										}*/
										ps2.setInt(10, cmbSL_Code);
										System.out.println("part6 ");
										try {
											txtsub_Amount = Double
													.parseDouble(Grid_Amt[ll]);
										} catch (NumberFormatException e) {
											System.out.println("exception" + e);
										}
										ps2.setDouble(11, txtsub_Amount);
										System.out.println("part7 ");
										ps2.setString(12, Grid_particular[ll]);
										ps2.setInt(13, accepting_SL_No);
										ps2.setDate(14, txtCrea_date);
										ps2.setString(15, update_user);
										ps2.setTimestamp(16, ts);
										ps2.setInt(17, CB_REF_NO);
									   int lll = ps2.executeUpdate();
										//int lll=1;//nk
										System.out.println("part8 ");
										if (lll > 0) {
											trn_count++;
											System.out.println("Row " + ll
													+ " inserted successfully");
										}
									}
									System.out
											.println("After inserting journal mas and trn the trn_count is ::: "
													+ trn_count);
									if (trn_count == Grid_H_code.length) {
										flag = true;
									} else
										flag = false;
								} else
									flag = false;

							} catch (Exception e) {
								System.out.println("Err in creating journal "
										+ e.getMessage());
								flag = false;
							}
						}
					} catch (Exception e) {
						System.out.println("Err in outer journal exp :: "
								+ e.getMessage());
						flag = false;
					}

				} else
					flag = false;

				System.out.println("Final Flag :: " + flag);
				try {
					if (flag == true) {
						try {
						    con.commit();
							ps.close();
							System.out.print("orginated unit id"
									+ originate_Acc_unit);
							System.out.print("Current unit id"
									+ cmbAcc_UnitCode);
							ps = con
									.prepareStatement("update FAS_TPA_MASTER set ORGIN_VOUCHER_NO=?,ORGIN_VOUCHER_DATE=?,ACCEPT_VOUCHER_NO=?,ACCEPT_VOUCHER_DATE=? where ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=? ");
							ps.setInt(1, orgin_no);
							ps.setDate(2, txtCrea_date);
							ps.setInt(3, accept_no);
							ps.setDate(4, txtCrea_date);
							ps.setInt(5, originate_Acc_unit);
							ps.setInt(6, originated_year);
							ps.setInt(7, originated_month);
							ps.setInt(8, originated_slno);
							int kk = ps.executeUpdate();
							//int kk=1;
							System.out.println("Originate update ::: " + kk);
							if (kk > 0) {
								System.out
										.println("Originate vou no is updated");
							} else
								System.out
										.println("Originate vou no is not updated");
							System.out.println("Originate accounting unit"
									+ originate_Acc_unit);

							ps1.close();
							ps1 = con
									.prepareStatement("update FAS_TPA_MASTER set ACCEPT_VOUCHER_NO=?,ACCEPT_VOUCHER_DATE=?, ORGIN_VOUCHER_NO=?,ORGIN_VOUCHER_DATE=?,ORG_ACCOUNTING_UNIT_ID=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and VOUCHER_NO=?");
							ps1.setInt(1, accept_no);
							ps1.setDate(2, txtCrea_date);
							ps1.setInt(3, orgin_no);
							ps1.setDate(4, txtCrea_date);
							ps1.setInt(5, originate_Acc_unit);
							ps1.setInt(6, cmbAcc_UnitCode);
							ps1.setInt(7, cmbOffice_code);
							ps1.setInt(8, txtCash_year);
							ps1.setInt(9, txtCash_Month_hid);
							ps1.setInt(10, accepting_SL_No);
							int kk1 = ps1.executeUpdate();
							//int kk1=1;//nk
							System.out.println("Acceptance update ::: " + kk);
							if (kk1 > 0) {
								System.out
										.println("Accepted vou no is updated");
							} else
								System.out
										.println("Accepted vou no is not updated");

							sendMessage(
									response,
									"The TPA Acceptance has been Created Successfully ",
									"ok");
						} catch (Exception e) {
							System.out.println("Pls " + e);
						}

					} else {
						System.out.println("b4 Rollback");
						sendMessage(response,
								"The TPA Acceptance Creation Failed ", "ok");
						con.rollback();

					}
				} catch (Exception e) {
					System.out.println("b4 Rollback");
					sendMessage(response,
							"The TPA Acceptance Creation Failed ", "ok");
					con.rollback();
				}
			} catch (Exception e) {
				try {
					con.rollback();
				} catch (SQLException sqle) {
					System.out.println("exception in rollback " + sqle);
				}
				e.getStackTrace();
				sendMessage(response, "Accepting Sl.No. Creation Failed ", "ok");
			} finally {
				System.out.println("done");
				try {
					con.setAutoCommit(true);
					con.close();
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
