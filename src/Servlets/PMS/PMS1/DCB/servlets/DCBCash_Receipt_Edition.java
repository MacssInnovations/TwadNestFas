package Servlets.PMS.PMS1.DCB.servlets;

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;

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
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class DCBCash_Receipt_Edition extends HttpServlet {

	private String CONTENT_TYPE = "text/xml; charset=windows-1252";

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		String strCommand = "";
		Connection con = null;
		ResultSet rs = null;
		CallableStatement cs = null, cs1 = null;
		PreparedStatement ps = null;
		String xml = "";
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
//			ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":"
//					+ strportno.trim() + ":" + strsid.trim();
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);
			// sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

		}
		try {

			strCommand = request.getParameter("Command");
			System.out.println("assign..here command..." + strCommand);
		}

		catch (Exception e) {
			System.out.println("Exception in assigning..." + e);
		}
		if (strCommand.equalsIgnoreCase("Add")) {
			System.out.println("RK");
			String CONTENT_TYPE = "text/html; charset=windows-1252";
			response.setContentType(CONTENT_TYPE);
			xml = "<response><command>Add</command>";
			Calendar c;
			int txtAcc_HeadCode = 0, cmbAcc_UnitCode = 0, cmbOffice_code = 0, txtCash_Month_hid = 0, txtCash_year = 0, txtReceipt_No = 0;
			int txtCash_Acc_code = 0, Total_TRN_Rec = 0;
			double txtAmount = 0;
			String txtReceipt_type = "C", txtCR_DB = "", txtRecei_from = "";
			Date txtCrea_date = null, txtRef_date = null;
			String txtRef_no = "", txtRemarks = "";
			// String txtD_ODep_Id="";
			// int txtD_ODep_OffId=0;
			int cmbMas_SL_type = 0, cmbMas_SL_Code = 0; // ,cmbMas_offid=0; //
														// changes here
			String update_user = (String) session.getAttribute("UserId");
			long l = System.currentTimeMillis();
			//Timestamp ts = new Timestamp(l);
			System.out.println("RK1");
			// For Banking Purpose

			String txtRec_Vou_type = "", txtMode_of_creat = "M", txtCreat_By_Module = "CR";
			int txtJournal_code = 0;
			Date txtCha_Date = null, txtRec_Vou_date = null;
			int txtBankId = 0, txtBranchId = 0, txtNo_of_pay_voucher = 0, txtCha_No = 0, txtRec_Vou_No = 0;
			long txtBankAccountNo = 0;

			// Special case this flag is used to Modify the receipt ,even after
			// Cash Remittance made ********** important
			String rad_ReClass = "";
			try {
				rad_ReClass = request.getParameter("rad_ReClass");
			} catch (Exception e) {
				e.printStackTrace();
			}

			if (rad_ReClass.equalsIgnoreCase("Y"))
				txtMode_of_creat = "S";
			else
				txtMode_of_creat = "M";

			String rem_current_month = "";
			try {
				rem_current_month = request.getParameter("rem_current_month");
			} catch (Exception e) {
				e.printStackTrace();
			}

			System.out.println("remittance in current month"
					+ rem_current_month);

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

			try {
				txtCash_Acc_code = Integer.parseInt(request
						.getParameter("txtCash_Acc_code"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			System.out.println("txtCash_Acc_code " + txtCash_Acc_code);

			String[] sd = request.getParameter("txtCrea_date").split("/");
			c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer
					.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c.getTime();
			txtCrea_date = new Date(d.getTime());
			System.out.println("txtCrea_date " + txtCrea_date);

			System.out.println("b4 getting month and year");
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

			/*
			 * try{txtCash_year=Integer.parseInt(request.getParameter("txtCash_year"
			 * ));} catch(Exception e){System.out.println("exception"+e );}
			 * System.out.println("txtCash_year "+txtCash_year);
			 * 
			 * try{txtCash_Month_hid=Integer.parseInt(request.getParameter(
			 * "txtCash_Month_hid"));} catch(Exception
			 * e){System.out.println("exception"+e );}
			 * System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
			 */
			try {
				txtReceipt_No = Integer.parseInt(request
						.getParameter("txtReceipt_No"));
			} catch (Exception e) {
				System.out.println("exception" + e);
			}
			System.out.println("txtReceipt_No " + txtReceipt_No);

			try {
				txtAmount = Double.parseDouble(request
						.getParameter("txtAmount"));
			} catch (Exception e) {
				System.out.println("exception" + e);
			}
			System.out.println("txtAmount " + txtAmount);

			txtCR_DB = request.getParameter("txtCR_DB");
			System.out.println("txtCR_DB " + txtCR_DB);

			try {
				cmbMas_SL_type = Integer.parseInt(request
						.getParameter("cmbMas_SL_type"));
			} catch (Exception e) {
				System.out.println("exception" + e);
			}

			try {
				cmbMas_SL_Code = Integer.parseInt(request
						.getParameter("cmbMas_SL_Code"));
			} catch (Exception e) {
				System.out.println("exception" + e);
			}

			txtRecei_from = request.getParameter("txtRecei_from");
			System.out.println("txtRecei_from " + txtRecei_from);

			txtRef_no = request.getParameter("txtRef_no");
			System.out.println("txtRef_no " + txtRef_no);

			String Ref_date = request.getParameter("txtRef_date");
			System.out.println("txtRef_date " + txtRef_date);

			if (!Ref_date.equals("")) // if not a empty string, convert to SQL
										// date
			{
				sd = request.getParameter("txtRef_date").split("/");
				c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer
						.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
				d = c.getTime();
				txtRef_date = new Date(d.getTime());
			}
			System.out.println("after txtRef_date " + txtRef_date);
			txtRemarks = request.getParameter("txtRemarks");
			System.out.println("txtRemarks " + txtRemarks);

			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

			/** Get Receipt Creation Date */
			String Receipt_Creation_Date = request.getParameter("txtCrea_date");

			/**
			 * Call Com_CashBook Servlet for Calculating Cash Book Month and
			 * Year
			 */
			Com_CashBook1 cb = new Com_CashBook1();

			/** Assign Cashbook Year and Month to year_month Variable */
			String year_month = cb.cb_date(Receipt_Creation_Date).toString();

			/** Split Cash Book Year and Month */
			String[] ym = year_month.split("/");

			/** Assign Year and Month */
			txtCash_year = Integer.parseInt(ym[0]);
			txtCash_Month_hid = Integer.parseInt(ym[1]);
			System.out.println("RK2");
			// ~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~~

			/* Old Procedure for finding Cash Book Month and Year */
			/*
			 * 
			 * String[] sp=request.getParameter("txtCrea_date").split("/");
			 * System.out.println(sp[0]+" "+sp[1]+" "+sp[2]); int
			 * check_year=Integer.parseInt(sp[2]); // to check in while loop int
			 * check_day=Integer.parseInt(sp[0]); // to check in while loop
			 * System.out.println(Integer.parseInt(sp[2]));
			 * System.out.println("here"+check_year);
			 * 
			 * String check_date=request.getParameter("txtCrea_date");
			 * sp=request.getParameter("txtCrea_date").split("/");
			 * check_date=sp[2]+"/"+sp[1]+"/"+sp[0];
			 * 
			 * System.out.println(check_date); // to check in while loop with
			 * d/b date it converted to yyyy/mm/dd form try { String
			 * sql1="select FINANCIAL_YEAR," +
			 * "to_char(CB_FROM_DATE_FOR_MARCH,'YYYY/MM/DD') as mar_beg,to_char(CB_TO_DATE_FOR_MARCH,'YYYY/MM/DD') as mar_end ,"
			 * + "to_char(CB_FROM_DATE_FOR_APRIL,'YYYY/MM/DD') as apr_beg ," +
			 * "to_char(CB_TO_DATE_FOR_APRIL,'YYYY/MM/DD') as apr_end ,CB_FROM_DATE_FOR_OTH ,CB_TO_DATE_FOR_OTH  "
			 * + "from CASH_BOOK_CONTROL order by FINANCIAL_YEAR";
			 * 
			 * // date is taken as string from database in above format for
			 * checking with receipt date variable ( check_date is string type)
			 * // checking of dates performed in form of String checking
			 * ps=con.prepareStatement(sql1); rs=ps.executeQuery(); int
			 * Begin_yr,End_yr; while(rs.next()) { String[]
			 * yr=rs.getString("FINANCIAL_YEAR").split("-");
			 * Begin_yr=Integer.parseInt(yr[0]); End_yr=Integer.parseInt(yr[1]);
			 * System.out.println("while"); System.out.println(Begin_yr+
			 * " "+End_yr);
			 * System.out.println(rs.getString("mar_beg")+" "+rs.getString
			 * ("mar_end"));
			 * 
			 * if(check_year==Begin_yr) // to check which financial year it
			 * belongs { if(txtCash_Month_hid>=4 && txtCash_Month_hid<=12) {
			 * System.out.println("if 4");
			 * if((check_date.compareToIgnoreCase(rs.getString("mar_beg"))>=0 )
			 * && ( check_date.compareToIgnoreCase(rs.getString("mar_end"))<=0)
			 * ) { txtCash_Month_hid=03;
			 * System.out.println(check_date.compareToIgnoreCase
			 * (rs.getString("mar_beg"))+"mar"+txtCash_Month_hid); } else
			 * if((check_date.compareToIgnoreCase(rs.getString("apr_beg"))>=0 )
			 * && ( check_date.compareToIgnoreCase(rs.getString("apr_end"))<=0 )
			 * ) { txtCash_Month_hid=04;
			 * System.out.println(check_date.compareToIgnoreCase
			 * (rs.getString("mar_beg"))+"apr"+txtCash_Month_hid); } else
			 * if(check_day>=rs.getInt("CB_FROM_DATE_FOR_OTH")) {
			 * txtCash_Month_hid=txtCash_Month_hid+1; if(txtCash_Month_hid>12) {
			 * txtCash_Month_hid=1; txtCash_year=txtCash_year+1;
			 * System.out.println("hello"+txtCash_year); }
			 * System.out.println(rs.
			 * getInt("CB_FROM_DATE_FOR_OTH")+"oth1 "+txtCash_Month_hid); } else
			 * if(check_day<=rs.getInt("CB_TO_DATE_FOR_OTH")) {
			 * //txtCash_Month_hid=txtCash_Month_hid;
			 * System.out.println(rs.getInt
			 * ("CB_FROM_DATE_FOR_OTH")+"oth2 "+txtCash_Month_hid); } }
			 * 
			 * } else if(check_year==End_yr) { if(txtCash_Month_hid>=1 &&
			 * txtCash_Month_hid<=3) {
			 * txtCash_year=End_yr;System.out.println("if 3");
			 * if((check_date.compareToIgnoreCase(rs.getString("mar_beg"))>=0 )
			 * && ( check_date.compareToIgnoreCase(rs.getString("mar_end"))<=0)
			 * ) { txtCash_Month_hid=03;
			 * System.out.println(check_date.compareToIgnoreCase
			 * (rs.getString("mar_beg"))+"mar"+txtCash_Month_hid); } else
			 * if((check_date.compareToIgnoreCase(rs.getString("apr_beg"))>=0 )
			 * && ( check_date.compareToIgnoreCase(rs.getString("apr_end"))<=0 )
			 * ) { txtCash_Month_hid=04;
			 * System.out.println(check_date.compareToIgnoreCase
			 * (rs.getString("mar_beg"))+"apr"+txtCash_Month_hid); } else
			 * if(check_day>=rs.getInt("CB_FROM_DATE_FOR_OTH")) {
			 * txtCash_Month_hid=txtCash_Month_hid+1; if(txtCash_Month_hid>12)
			 * // No chance for this condition { txtCash_Month_hid=1;
			 * txtCash_year=txtCash_year+1;
			 * System.out.println("hello"+txtCash_year); }
			 * System.out.println(rs.
			 * getInt("CB_FROM_DATE_FOR_OTH")+"oth1 "+txtCash_Month_hid); } else
			 * if(check_day<=rs.getInt("CB_TO_DATE_FOR_OTH")) {
			 * //txtCash_Month_hid=txtCash_Month_hid;
			 * System.out.println(rs.getInt
			 * ("CB_FROM_DATE_FOR_OTH")+"oth2 "+txtCash_Month_hid); } } } }
			 * ps.close(); rs.close(); } catch(Exception e) {
			 * sendMessage(response
			 * ,"The Cash Receipt Modification Failed finding Cash book month"
			 * ,"ok"); System.out.println("exception"+e); }
			 */

			try {
				System.out.println("RK3");
				con.clearWarnings();
				con.setAutoCommit(false);
				System.out.println("inside proc");
				String No_TRN_Rec[] = request.getParameterValues("H_code");
				SimpleDateFormat obj = new SimpleDateFormat("yyyy-MM-dd");  
    			SimpleDateFormat objTs = new SimpleDateFormat("yyyy-MM-dd hh:mm:ss");  
                //java.sql.Date txtCrea_date = java.sql.Date.valueOf( "txtCrea_date" );
                //long epoch = objTs.parse("ts").getTime();    
               
    			   c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
    					                         Integer.parseInt(sd[0]));
    					            d = c.getTime();
    					            txtCrea_date = new Date(d.getTime());
    			
    			Timestamp ts=new Timestamp(l); 
				// int NTR=No_TRN_Rec.length;
				// System.out.println(Total_TRN_Rec+" Total_TRN_Rec"+No_TRN_Rec.length);
				Total_TRN_Rec = No_TRN_Rec.length; // Integer.parseInt(No_TRN_Rec);
				System.out.println(Total_TRN_Rec + " Total_TRN_Rec");
				System.out.println("UU.." + cmbAcc_UnitCode + "  yearU.."
						+ txtCash_year + "  mon.." + txtCash_Month_hid
						+ "  rno.." + "" + txtReceipt_No + "  off.."
						+ cmbOffice_code + "  rdate.." + txtCrea_date
						+ "  ty.." + txtReceipt_type + "  UU.."
						+ txtCash_Acc_code + "  crdr.." + txtCR_DB + "  b.."
						+ txtBankId + "  b.." + txtBranchId + "  b.."
						+ txtBankAccountNo + "  recfrom.." + txtRecei_from
						+ "  refno.." + txtRef_no + "  refdat..e" + txtRef_date
						+ "  amoutn.." + txtAmount + "  remak.." + txtRemarks);
//
//				cs = con
//						.prepareCall("call FAS_RECEIPT_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
//				cs.setInt(1, cmbAcc_UnitCode);
//				cs.setInt(2, txtCash_year);
//				cs.setInt(3, txtCash_Month_hid);
//				cs.setInt(4, txtReceipt_No);
//				cs.setInt(5, cmbOffice_code);
//				cs.setDate(6, txtCrea_date);
//				cs.setString(7, txtReceipt_type);
//				cs.setInt(8, txtCash_Acc_code);
//				cs.setString(9, txtCR_DB);
//				cs.setInt(10, txtBankId);
//				cs.setInt(11, txtBranchId);
//				cs.setLong(12, txtBankAccountNo);
//				cs.setString(13, txtRecei_from);
//				cs.setString(14, txtRef_no);
//				cs.setDate(15, txtRef_date);
//				cs.setInt(16, txtNo_of_pay_voucher);
//				cs.setInt(17, txtCha_No);
//				cs.setDate(18, txtCha_Date);
//				cs.setObject(19, txtAmount,java.sql.Types.NUMERIC);
//				cs.setInt(20, Total_TRN_Rec);
//				cs.setString(21, txtRec_Vou_type);
//				cs.setInt(22, txtRec_Vou_No);
//				cs.setDate(23, txtRec_Vou_date);
//				cs.setInt(24, txtJournal_code);
//				cs.setString(25, txtRemarks);
//				cs.setString(26, txtMode_of_creat);
//				cs.setString(27, txtCreat_By_Module);
//				cs.setString(28, "update");
//				cs.registerOutParameter(4, java.sql.Types.NUMERIC);
//				cs.registerOutParameter(29, java.sql.Types.NUMERIC);
//				cs.setNull(4, java.sql.Types.NUMERIC);
//                cs.setNull(29, java.sql.Types.INTEGER);
//				cs.setInt(30, cmbMas_SL_type);
//				cs.setInt(31, cmbMas_SL_Code);
//				// cs.setInt(32,cmbMas_offid);
//				cs.setString(32, update_user);
//				cs.setTimestamp(33, ts);
//				cs.setString(34, rem_current_month);
//
//				System.out.println("b4 exe ");
//				cs.execute();
//				txtReceipt_No =cs.getBigDecimal(4).intValue();
//                int errcode = cs.getBigDecimal(29).intValue();
				   cs =
						   con.prepareCall("call FAS_RECEIPT_MASTER_PROC(?::NUMERIC,?::NUMERIC,?::NUMERIC,?::NUMERIC,?::NUMERIC,?,?,?::NUMERIC,?,?::NUMERIC,?::NUMERIC,?::NUMERIC,?,?,?,?::NUMERIC,?::NUMERIC,?,?::NUMERIC,?::NUMERIC,?,?::NUMERIC,?,?,?,?,?,?,?::NUMERIC,?::NUMERIC,?::NUMERIC,?,?,?)");
						                 cs.setInt(1, cmbAcc_UnitCode);
						                 cs.setInt(2, txtCash_year);
						                 cs.setInt(3, txtCash_Month_hid);
						                 cs.setInt(4, txtReceipt_No);
						                 cs.setInt(5, cmbOffice_code);
						                 cs.setDate(6, txtCrea_date);
						                 cs.setString(7, txtReceipt_type);
						                 cs.setInt(8, txtCash_Acc_code);
						                 cs.setString(9, txtCR_DB);
						                 cs.setInt(10, txtBankId);
						                 cs.setInt(11, txtBranchId);
						                 cs.setLong(12, txtBankAccountNo);
						                 cs.setString(13, txtRecei_from);
						                 cs.setString(14, txtRef_no);
						                 cs.setDate(15, txtRef_date);
						                 cs.setInt(16, txtNo_of_pay_voucher);
						                 cs.setInt(17, txtCha_No);
						                 cs.setDate(18, txtCha_Date);
						                 cs.setObject(19, txtAmount,java.sql.Types.NUMERIC);
						                 cs.setInt(20, Total_TRN_Rec);
						                 cs.setString(21, txtRec_Vou_type);
						                 cs.setInt(22, txtRec_Vou_No);
						                 cs.setDate(23, txtRec_Vou_date);
						                 //cs.setInt(24, txtJournal_code);
						                 cs.setString(24, String.valueOf(txtJournal_code));
						                 cs.setString(25, txtRemarks);
						                 cs.setString(26, txtMode_of_creat);
						                 cs.setString(27, txtCreat_By_Module);
						                 cs.setString(28, "update");
						                 cs.registerOutParameter(4, java.sql.Types.NUMERIC);
						                 cs.registerOutParameter(29, java.sql.Types.NUMERIC);
						                 //cs.setNull(4, java.sql.Types.NUMERIC);
						                 cs.setNull(29, java.sql.Types.NUMERIC);
						                 cs.setInt(30, cmbMas_SL_type);
						                 cs.setInt(31, cmbMas_SL_Code);
						                 cs.setString(32, update_user);
						                 cs.setTimestamp(33, ts);
						                 cs.setString(34, rem_current_month);
						                 System.out.println("b4 exe ");
						                 cs.execute();
						                 //txtReceipt_No = cs.getInt(4);
						                 //int errcode = cs.getInt(29);
						                 txtReceipt_No = cs.getBigDecimal(4).intValue();
						                 int errcode = cs.getBigDecimal(29).intValue();
				System.out.println("SQLCODE:::" + errcode);
				if (errcode != 0) {
					System.out.println("redirect");
					sendMessage(response, "The Cash Receipt Creation Failed ",
							"ok");
					xml = xml + "<flag>failure</flag>";
				} else {
					String sql_del = "delete from FAS_RECEIPT_TRANSACTION where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?"
							+ "and CASHBOOK_YEAR=?  and CASHBOOK_MONTH=?  and RECEIPT_NO=?  ";
					ps = con.prepareStatement(sql_del);
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setInt(3, txtCash_year);
					ps.setInt(4, txtCash_Month_hid);
					ps.setInt(5, txtReceipt_No);
					ps.executeUpdate(); // deletion from transaction table
					ps.close();

					String Grid_H_code[] = request.getParameterValues("H_code");
					String Grid_CR_DR_type[] = request
							.getParameterValues("CR_DR_type");
					String Grid_SL_type[] = request
							.getParameterValues("SL_type");
					String Grid_SL_code[] = request
							.getParameterValues("SL_code");
					// String
					// Grid_rec_from[]=request.getParameterValues("rec_from");
					String Grid_sl_amt[] = request.getParameterValues("sl_amt");
					String Grid_particular[] = request
							.getParameterValues("particular");

					String sql = "insert into FAS_RECEIPT_TRANSACTION(ACCOUNTING_UNIT_ID, "
							+ "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, RECEIPT_NO, SL_NO, ACCOUNT_HEAD_CODE, "
							+ "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, RECEIVED_FROM,"
							+ "CHEQUE_OR_DD ,CHEQUE_DD_NO, CHEQUE_DD_DATE, BANK_NAME, DRAWEE_BRANCH, "
							+ "BANK_MICR_CODE, AMOUNT, PARTICULARS,UPDATED_BY_USER_ID,UPDATED_DATE ) "
							+ "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

					int SL_NO = 1, cmbSL_type = 0, cmbSL_Code = 0;
					String rad_sub_CR_DR = "", txtParticular = "", txtCheque_DD = "", txtCheque_DD_NO = "", txtBank_Name = "", txtDraw_BR = "", txtBank_M_Code = "", txtsub_Recei_from = "";
					Date txtCheque_DD_date = null;
					double txtsub_Amount = 0;
					ps = con.prepareStatement(sql);
					System.out.println("length of TRN" + Grid_H_code.length);
					for (int k = 0; k < Grid_H_code.length; k++) {
						try {
							txtAcc_HeadCode = Integer.parseInt(Grid_H_code[k]);
						} catch (Exception e) {
							System.out.println("exception in trans " + e);
						}
						rad_sub_CR_DR = Grid_CR_DR_type[k];

						try {
							cmbSL_type = Integer.parseInt(Grid_SL_type[k]);
						} catch (Exception e) {
							System.out.println("exception in trans " + e);
						}
						try {
							cmbSL_Code = Integer.parseInt(Grid_SL_code[k]);
						} catch (Exception e) {
							System.out.println("exception in trans " + e);
						}
						System.out.println("Grid_H_code[k] " + Grid_H_code[k]);
						System.out.println("Grid_CR_DR_type[k] "
								+ Grid_CR_DR_type[k]);
						System.out.println("Grid_SL_type[k]" + Grid_SL_type[k]
								+ "u");
						System.out.println("Grid_SL_code[k]" + Grid_SL_code[k]
								+ "from here" + cmbSL_Code);
						// System.out.println(cmbSL_type.equalsIgnoreCase("7"));

						// txtsub_Recei_from=Grid_rec_from[k];
						txtsub_Amount = Double.parseDouble(Grid_sl_amt[k]);
						txtParticular = Grid_particular[k];
						System.out.println("Grid_sl_amt[k] " + Grid_sl_amt[k]);

						// System.out.println("Grid_rec_from[k] "+Grid_rec_from[k]);

						System.out.println("Grid_particular[k] "
								+ Grid_particular[k]);

						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setInt(3, txtCash_year);
						ps.setInt(4, txtCash_Month_hid);
						ps.setInt(5, txtReceipt_No);
						ps.setInt(6, SL_NO);
						ps.setInt(7, txtAcc_HeadCode);
						ps.setString(8, rad_sub_CR_DR);
						ps.setInt(9, cmbSL_type);
						ps.setInt(10, cmbSL_Code);
						ps.setString(11, txtsub_Recei_from);
						// ps.setString(12,DPN_deptId);
						// ps.setInt(13,DPN_offId);
						ps.setString(12, txtCheque_DD);
						ps.setString(13, txtCheque_DD_NO);
						ps.setDate(14, txtCheque_DD_date);
						ps.setString(15, txtBank_Name);
						ps.setString(16, txtDraw_BR);
						ps.setString(17, txtBank_M_Code);
						ps.setDouble(18, txtsub_Amount);
						ps.setString(19, txtParticular);
						ps.setString(20, update_user);
						ps.setTimestamp(21, ts);
						SL_NO++;

						ps.executeUpdate(); // insertion into transaction table
						txtAcc_HeadCode = 0;
						rad_sub_CR_DR = "";
						cmbSL_type = 0;
						cmbSL_Code = 0;
						txtsub_Recei_from = "";
						// DPN_deptId="";
						// DPN_offId=0;
						txtsub_Amount = 0;
						txtParticular = "";
					}
					ps.close();

					String txtReferNO_edit = "", txtRemak_edit = ""; // for
																		// cross
																		// reference
					Date txtReferDate_edit = null;
					String radAuth_MC = "";
					int txtAuth_By = 0;
					/*
					 * Here Modification has taken out of form , b'coz it has
					 * separate interface
					 * try{txtAuth_By=Integer.parseInt(request
					 * .getParameter("txtAuth_By"));} catch(Exception
					 * e){System.out.println("exception"+e );}
					 * System.out.println("txtAuth_By  "+txtAuth_By);
					 * 
					 * txtReferNO_edit=request.getParameter("txtReferNO_edit");
					 * System.out.println("txtReferNO_edit  "+txtReferNO_edit);
					 * 
					 * txtRemak_edit=request.getParameter("txtRemak_edit");
					 * System.out.println("txtRemak_edit  "+txtRemak_edit);
					 * txtRefdate=request.getParameter("txtReferDate_edit");
					 * if(!txtRefdate.equalsIgnoreCase("")) {
					 * sd=request.getParameter("txtReferDate_edit").split("/");
					 * c=new
					 * GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt
					 * (sd[1])-1,Integer.parseInt(sd[0])); d=c.getTime();
					 * txtReferDate_edit=new Date(d.getTime()); }
					 */
					System.out
							.println("txtReferDate_edit " + txtReferDate_edit);
					cs1 = con
							.prepareCall("call FAS_CROSS_REFERENCE_PROC(?::NUMERIC,?,?,?,?,?,?,?,?,?,?::VARCHAR,?,?,?,?,?)");
					cs1.setInt(1, cmbAcc_UnitCode);
					cs1.setInt(2, txtCash_year);
					cs1.setInt(3, txtCash_Month_hid);
					cs1.setInt(4, txtReceipt_No);
					cs1.setInt(5, cmbOffice_code);
					cs1.setDate(6, txtCrea_date);
					cs1.setString(7, "CR");
					cs1.setString(8, txtReferNO_edit);
					cs1.setDate(9, txtReferDate_edit);
					cs1.setString(10, txtRemak_edit);
					cs1.setInt(11, txtAuth_By);
					cs1.setString(12, "insert");
					cs1.registerOutParameter(13, java.sql.Types.NUMERIC);
					cs1.setNull(13,java.sql.Types.NUMERIC);
					cs1.setString(14, update_user);
					cs1.setTimestamp(15, ts);
					cs1.setString(16, radAuth_MC);
					cs1.execute(); // insertion into cross reference table
					//errcode = cs1.getInt(13);
					 errcode = cs1.getBigDecimal(13).intValue();
					System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);
					System.out.println("cmbOffice_code " + cmbOffice_code);
					System.out.println("txtCrea_date " + txtCrea_date);
					System.out.println("txtCash_year " + txtCash_year);
					System.out
							.println("txtCash_Month_hid " + txtCash_Month_hid);
					System.out.println("SQLCODE:::" + errcode);
					if (errcode != 0) {
						con.rollback();
						sendMessage(response,
								"The Cash Receipt Modification Failed ", "ok");
						xml = xml + "<flag>failure</flag>";
					}

					con.commit();
					sendMessage(response, "The Cash Receipt Number'"
							+ txtReceipt_No
							+ "' has been modified Successfully ", "ok");
				}
			}

			catch (Exception e) {
				e.printStackTrace();
				try {
					con.rollback();
				} catch (SQLException sqle) {
					System.out.println("Excep" + sqle);
				}
				sendMessage(response, "The Cash Receipt Modification Failed ",
						"ok");
				System.out.println("Exception occur due to " + e);
			} finally {
				System.out.println("done");
				try {
					con.setAutoCommit(true);
				} catch (SQLException sqle) {
					System.out.println("Excep" + sqle);
				}
			}

		}
	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		ResultSet rs = null, rs2 = null, rs3 = null, rs4 = null;
		PreparedStatement ps = null, ps2 = null, ps3 = null, ps4 = null;

		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");
				return;
			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
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
//			ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":"
//					+ strportno.trim() + ":" + strsid.trim();
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);
			// sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

		}
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();
		String strCommand = "";

		try {
			strCommand = request.getParameter("Command");
			System.out.println("assign.. command..." + strCommand);
		}

		catch (Exception e) {
			System.out.println("Exception in assigning..." + e);
		}
		int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
		Date txtCrea_date = null;
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

		if (strCommand.equalsIgnoreCase("load_Receipt_No")) {
			String CONTENT_TYPE = "text/xml; charset=windows-1252";
			response.setContentType(CONTENT_TYPE);
			Calendar c;
			String xml = "";
			xml = "<response><command>load_Receipt_No</command>";

			String[] sd = request.getParameter("txtCrea_date").split("/");
			c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer
					.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c.getTime();
			txtCrea_date = new Date(d.getTime());
			System.out.println("txtCrea_date " + txtCrea_date);

			try {
				// ps=con.prepareStatement("select RECEIPT_NO from FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_DATE=? and RECEIPT_TYPE='C' and CREATED_BY_MODULE='CR' and RECEIPT_STATUS!='C'");

				ps = con
						.prepareStatement("select i.RECEIPT_NO from FAS_RECEIPT_MASTER i,FAS_CROSS_REFERENCE c where "
								+ " i.ACCOUNTING_UNIT_ID=?  and i.ACCOUNTING_FOR_OFFICE_ID=? and i.RECEIPT_DATE=? and i.RECEIPT_STATUS!='C' and RECEIPT_TYPE='C' and CREATED_BY_MODULE='CR' "
								+ " and i.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID and i.ACCOUNTING_FOR_OFFICE_ID=c.ACCOUNTING_FOR_OFFICE_ID "
								+ " and i.CASHBOOK_YEAR=c.CASHBOOK_YEAR and i.CASHBOOK_MONTH=c.CASHBOOK_MONTH and i.RECEIPT_NO=c.VOUCHER_NO "
								+ " and c.CHANGE_NO=0 and c.AUTHORIZED_TO='M' and DOC_TYPE='DCC'");

				System.out.println(txtCrea_date);
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setDate(3, txtCrea_date);
				rs = ps.executeQuery();

				int count = 0;
				while (rs.next()) {

					xml = xml + "<Rec_No>" + rs.getInt("RECEIPT_NO")
							+ "</Rec_No>";
					count++;
				}
				if (count == 0)
					xml = xml + "<flag>failure</flag>";
				else
					xml = xml + "<flag>success</flag>";
				System.out.println("count  " + count);
				ps.close();
				rs.close();
			} catch (Exception e) {
				System.out.println("catch..HERE.in load head code." + e);
				xml = xml + "<flag>failure</flag>";
			}
			xml = xml + "</response>";
			System.out.println(xml);
			out.println(xml);

		}

		else if (strCommand.equalsIgnoreCase("load_Receipt_Details")) {
			String CONTENT_TYPE = "text/xml; charset=windows-1252";
			response.setContentType(CONTENT_TYPE);
			Calendar c;
			String xml = "";
			xml = "<response><command>load_Receipt_Details</command>";
			int txtReceipt_No = 0;
			// Date txtCrea_date;

			try {
				txtReceipt_No = Integer.parseInt(request
						.getParameter("txtReceipt_No"));
			} catch (NumberFormatException e) {
				System.out.println("exception" + e);
			}
			System.out.println("txtReceipt_No " + txtReceipt_No);

			String[] sd = request.getParameter("txtCrea_date").split("/");
			c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer
					.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c.getTime();
			txtCrea_date = new Date(d.getTime());
			System.out.println("txtCrea_date " + txtCrea_date);

			try {
				ps = con
						.prepareStatement("select REMITTANCE_IN_CURR_MONTH, MODE_OF_CREATION,CASHBOOK_YEAR,CASHBOOK_MONTH,REF_NO,to_char(REF_DATE,'DD/MM/YYYY')as ref_date,trim(to_char(TOTAL_AMOUNT,'99999999999999.99')) as TOTAL_AMOUNT,TOTAL_TRN_RECORDS,RECEIVED_FROM,REMARKS,SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE  from  FAS_RECEIPT_MASTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and RECEIPT_TYPE='C' and CREATED_BY_MODULE='CR' and  RECEIPT_DATE=? and RECEIPT_NO=?");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setDate(3, txtCrea_date);
				ps.setInt(4, txtReceipt_No);
				rs = ps.executeQuery();
				if (rs.next()) {
					xml = xml + "<flag>success</flag>";
					xml = xml + "<Ref_No>" + rs.getString("REF_NO").replace("", "-")
							+ "</Ref_No><Ref_Date>" + rs.getString("ref_date")
							+ "</Ref_Date><Total_amt>"
							+ rs.getString("TOTAL_AMOUNT")
							+ "</Total_amt><No_TRN_Rec>"
							+ rs.getInt("TOTAL_TRN_RECORDS")
							+ "</No_TRN_Rec><Rec_From>"
							+ rs.getString("RECEIVED_FROM")
							+ "</Rec_From><Remak>" + rs.getString("REMARKS").replace("", "-")
							+ "</Remak><Mas_SL_type>"
							+ rs.getInt("SUB_LEDGER_TYPE_CODE")
							+ "</Mas_SL_type><Mas_SL_code>"
							+ rs.getInt("SUB_LEDGER_CODE") + "</Mas_SL_code>";
					xml = xml + "<MO_creation>"
							+ rs.getString("MODE_OF_CREATION")
							+ "</MO_creation>";
					xml = xml + "<rem_in_curr_month>"
							+ rs.getString("REMITTANCE_IN_CURR_MONTH")
							+ "</rem_in_curr_month>";

				}
				System.out.println("total..."
						+ rs.getInt("SUB_LEDGER_TYPE_CODE"));

				// Start of fetching sub-Ledger , here u r passing parameters to
				// the function getResult_General which is inside the class
				// SL_TYPE_CODE_NAME_GENERAL
				if (rs.getInt("SUB_LEDGER_TYPE_CODE") != 0) {

					/*
					 * SL_TYPE_CODE_NAME_GENERAL obj_gen =new
					 * SL_TYPE_CODE_NAME_GENERAL(); ResultSet rs_get
					 * =obj_gen.getResult_General(cmbAcc_UnitCode,
					 * cmbOffice_code, rs.getInt("SUB_LEDGER_TYPE_CODE"),
					 * rs.getInt("SUB_LEDGER_CODE"), 0); if (rs_get != null) {
					 * while (rs_get.next()) { //
					 * System.out.println(rs_get.getString("cid"));
					 * //System.out.println(rs_get.getString("cname")); xml =
					 * xml + "<cid>" + rs_get.getInt("cid") + "</cid><cname>" +
					 * rs_get.getString("cname") + "</cname>"; } rs_get.close();
					 * } else System.out.println("null result set");
					 */
					int ledgercode_ben = rs.getInt("SUB_LEDGER_CODE");

					PreparedStatement ps5 = con
							.prepareStatement("SELECT BENEFICIARY_TYPE_ID FROM PMS_DCB_MST_BENEFICIARY	WHERE BENEFICIARY_SNO=?	AND OFFICE_ID=?");
					ps5.setInt(1, rs.getInt("SUB_LEDGER_CODE"));
					ps5.setInt(2, cmbOffice_code);
					ResultSet rs5 = ps5.executeQuery();
					rs5.next();
					int benificiaryTypeId = rs5.getInt("BENEFICIARY_TYPE_ID");

					xml = xml + "<beneficiaryid>" + benificiaryTypeId
							+ "</beneficiaryid>";
					ps4 = con
							.prepareStatement("SELECT BENEFICIARY_SNO,BENEFICIARY_NAME  FROM PMS_DCB_MST_BENEFICIARY WHERE BENEFICIARY_TYPE_ID=? and OFFICE_ID=?");
					ps4.setInt(1, benificiaryTypeId);
					ps4.setInt(2, cmbOffice_code);
					rs4 = ps4.executeQuery();

					while (rs4.next()) {
						xml = xml + "<cid>" + rs4.getInt("BENEFICIARY_SNO")
								+ "</cid><cname>"
								+ rs4.getString("BENEFICIARY_NAME")
								+ "</cname>";
					}
					ps5.close();
					rs5.close();
					ps4.close();
					rs4.close();

				}
				// End of fetching sub-Ledger

				System.out.println("in b/w here");

				ps2 = con
						.prepareStatement("select ACCOUNT_HEAD_CODE ,CR_DR_INDICATOR ,SUB_LEDGER_TYPE_CODE ,SUB_LEDGER_CODE "
								+ ",RECEIVED_FROM ,trim(to_char(AMOUNT,'99999999999999.99')) as  AMOUNT, PARTICULARS  from FAS_RECEIPT_TRANSACTION where ACCOUNTING_UNIT_ID=? and "
								+ "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and RECEIPT_NO=?");

				System.out
						.println("select ACCOUNT_HEAD_CODE ,CR_DR_INDICATOR ,SUB_LEDGER_TYPE_CODE ,SUB_LEDGER_CODE "
								+ ",RECEIVED_FROM ,trim(to_char(AMOUNT,'99999999999999.99')) as  AMOUNT, PARTICULARS  from FAS_RECEIPT_TRANSACTION where ACCOUNTING_UNIT_ID=? and "
								+ "ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and RECEIPT_NO=?");

				ps2.setInt(1, cmbAcc_UnitCode);
				ps2.setInt(2, cmbOffice_code);
				ps2.setInt(3, rs.getInt("CASHBOOK_YEAR"));
				ps2.setInt(4, rs.getInt("CASHBOOK_MONTH"));
				ps2.setInt(5, txtReceipt_No);
				rs2 = ps2.executeQuery();
				while (rs2.next()) {
					xml = xml + "<AHcode>" + rs2.getInt("ACCOUNT_HEAD_CODE")
							+ "</AHcode>";
					ps3 = con
							.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
					ps3.setInt(1, rs2.getInt("ACCOUNT_HEAD_CODE"));
					rs3 = ps3.executeQuery();
					if (rs3.next())
						xml = xml + "<AHdesc>"
								+ rs3.getString("ACCOUNT_HEAD_DESC")
								+ "</AHdesc>";
					ps3.close();
					rs3.close();
					xml = xml + "<CR_DR_ind>"
							+ rs2.getString("CR_DR_INDICATOR")
							+ "</CR_DR_ind><SL_Type>"
							+ rs2.getInt("SUB_LEDGER_TYPE_CODE") + "</SL_Type>";
					if (rs2.getInt("SUB_LEDGER_TYPE_CODE") != 0) {
						System.out.println("DCB CASH take SL DESC");

						ps3 = con
								.prepareStatement("select SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=?");
						ps3.setInt(1, rs2.getInt("SUB_LEDGER_TYPE_CODE"));
						rs3 = ps3.executeQuery();
						if (rs3.next())
							xml = xml + "<SL_Desc>"
									+ rs3.getString("SUB_LEDGER_TYPE_DESC")
									+ "</SL_Desc>";
					} else {
						xml = xml + "<SL_Desc>" + null + "</SL_Desc>"; // it
																		// also
																		// return
																		// null
																		// value
						System.out.println("else part  23");
					}
					rs3.close();
					ps3.close();
					xml = xml + "<SL_Code>" + rs2.getInt("SUB_LEDGER_CODE")
							+ "</SL_Code>";

					// Start of fetching sub-Ledger , here u r passing
					// parameters to the function getResult_General which is
					// inside the class SL_TYPE_CODE_NAME_GENERAL
					if (rs2.getInt("SUB_LEDGER_TYPE_CODE") != 0) {

						System.out.println("SUB _CODE "
								+ rs2.getInt("SUB_LEDGER_CODE"));

						PreparedStatement ps6 = con
								.prepareStatement("SELECT SCH_SNO FROM PMS_MST_PROJECTS_VIEW WHERE PROJECT_ID=? and OFFICE_ID=? and STATUS='L'");
						ps6.setInt(1, rs2.getInt("SUB_LEDGER_CODE"));
						ps6.setInt(2, cmbOffice_code);
						int schemeNo = 0;
						ResultSet rs6 = ps6.executeQuery();
						if (rs6.next()) {
							schemeNo = rs6.getInt("SCH_SNO");
						}
						System.out.println("Scheme no" + schemeNo);
						xml = xml + "<schemeno>" + schemeNo + "</schemeno>";
						ps4 = con
								.prepareStatement("SELECT SCH_NAME FROM PMS_SCH_MASTER WHERE SCH_SNO =? and OFFICE_ID=?");
						ps4.setInt(1, schemeNo);
						ps4.setInt(2, cmbOffice_code);

						rs4 = ps4.executeQuery();
						rs4.next();
						xml = xml + "<desc_type>" + rs4.getString("SCH_NAME")
								+ "</desc_type>";

						
						
										/*	SL_TYPE_CODE_NAME_DETAILS obj_det = new SL_TYPE_CODE_NAME_DETAILS();
											ResultSet rs_det = obj_det.getResult_Details(
													cmbAcc_UnitCode, cmbOffice_code, rs2
															.getInt("SUB_LEDGER_TYPE_CODE"), rs2
															.getInt("SUB_LEDGER_CODE"), 0);
											if (rs_det != null) {
					
												if (rs_det.next()) {
													// System.out.println(rs_det.getString("cid"));
													System.out.println(rs_det.getString("cname"));
													xml = xml + "<desc_type>"
															+ rs_det.getString("cname")
															+ "</desc_type>";
												}
												rs_det.close();
						} else
							System.out.println("null result set"); */
						
						
						
						
						
						
						
					} else {
						xml = xml + "<desc_type>" + null + "</desc_type>";
					}
					// End of fetching sub-Ledger

					// System.out.println("...rs.getDouble(AMOUNT)"+rs.getDouble("AMOUNT"));
					// If you use getDouble it'll return in terms of
					// 2.2323232232323E23.. Note 'E' in the number
					// So i used getString to avoid 'E' power

					xml = xml + "<sub_rec_frm>"
							+ rs2.getString("RECEIVED_FROM")
							+ "</sub_rec_frm><sub_amount>"
							+ rs2.getString("AMOUNT")
							+ "</sub_amount><sub_part>"
							+ rs2.getString("PARTICULARS").replace("", "-") + "</sub_part>";
				}

			} catch (Exception e) {
				e.printStackTrace();
				System.out.println("catch..HERE.in failure to retrieve." + e);
				xml = "<response><command>load_Receipt_Details</command><flag>failure</flag>";
			}
			xml = xml + "</response>";
			System.out.println(xml);
			out.println(xml);
		}
	}

	private void sendMessage(HttpServletResponse response, String msg,
			String bType) {
		try {
			String url = "org/Library/jsps/MessengerOkBack.jsp?message=" + msg
					+ "&button=" + bType;
			response.sendRedirect(url);
		} catch (IOException e) {
			System.out.println("Excep" + e);
		}
	}
}
