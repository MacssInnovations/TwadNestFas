package Servlets.FAS.FAS1.BillVerification.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.sun.org.apache.xerces.internal.impl.dtd.models.CMBinOp;

/**
 * Servlet implementation class Cheque_Memo
 */
public class Cheque_Memo extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Cheque_Memo() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;

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
			System.out.println("Exception in connection...." + e);
		}
		ResultSet rs = null, rs1 = null, rs2 = null, rs4 = null;
		CallableStatement cs = null;
		PreparedStatement ps = null, ps1 = null, ps2 = null;
		String xml = "";

		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");
		HttpSession session = request.getSession(false);
		try {
			if (session == null) {
				xml = "<response><command>sessionout</command><flag>sessionout</flag></response>";
				out.println(xml);
				System.out.println(xml);
				out.close();
				return;

			}
			// System.out.println(session);

		} catch (Exception e) {
			// System.out.println("Redirect Error :"+e);
		}
		System.out.println("java");
		String command;
		command = request.getParameter("command");

		session = request.getSession(false);
		String updatedby = (String) session.getAttribute("UserId");
		long l = System.currentTimeMillis();
		java.sql.Timestamp ts = new java.sql.Timestamp(l);
		System.out.println("got");
		System.out.println("command" + command);
		String CONTENT_TYPE = "text/xml; charset=windows-1252";
		response.setContentType(CONTENT_TYPE);
	//	int memotype=Integer.parseInt(request.getParameter("memotype"));
		if (command.equalsIgnoreCase("get")) {
		
			String ss="";
			int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
			int cmbSL_type = 0;
			int addtional_field_value = 0;
			int y = 0;
			String withpro="";
			xml = "<response><command>" + command + "</command>";
			
			try {
				cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("cmbAcc_UnitCode"));
			} catch (Exception e) {
				System.out.println("error get acc unit code");
			}
			try {
				cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			} catch (Exception e) {
				System.out.println("error get office id");
			}
			// Joan Changed on 17 Apr 2015 Vasanthi Mam suggestion Upto the month&year from Current Month&Year
			int cashMonth = Integer.parseInt(request.getParameter("check_memo_Month"));
		    int cashYear = Integer.parseInt(request.getParameter("check_memo_Year"));
		/*	long l1=System.currentTimeMillis();
			java.sql.Timestamp ts1= new java.sql.Timestamp(l1);
			java.sql.Date tdate=new java.sql.Date(l1);
			int cashMonth=tdate.getMonth();
		  
			int cashYear=tdate.getYear();
			   if(cashYear < 1900) cashYear += 1900;*/
			System.out.println("cashMonth  >> "+cashMonth);
			System.out.println("cashYear  >> "+cashYear);
			
//			if(cmbOffice_code!=5000)
//			{
			String Flag_TB="";
			try{
				PreparedStatement ps_flag=con.prepareStatement("select ACCOUNTING_UNIT_ID from FAS_TRIAL_BALANCE_STATUS where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+ " and  CASHBOOK_MONTH="+cashMonth+" and CASHBOOK_YEAR="+cashYear);
			ResultSet rs_flag=ps_flag.executeQuery();
			if(rs_flag.next()){
				Flag_TB="F";
			}else{
				Flag_TB="T";
			}
			}catch(Exception e){
				e.printStackTrace();
			}
			System.out.println("Flag_TBFlag_TBFlag_TBFlag_TB"+Flag_TB);
			
			if(Flag_TB.equalsIgnoreCase("T"))
			{
				ss = "select a.*,b.BILL_MINOR_TYPE_CODE,b.BILL_MAJOR_TYPE,b.BILL_SUB_TYPE_CODE from "
						+ " (SELECT m.sanctioned_amount, "
						+ "   TO_CHAR(M.BILL_DATE,'dd/MM/yyyy') AS BILL_DATE, "
						+ " M.BILL_NO, "
						+ " T.AMOUNT, "
						+ " t.PARTICULARS, "
						+ " T.ACCOUNT_HEAD_CODE, "
						+ " (SELECT S.ACCOUNT_HEAD_DESC "
						+ " FROM com_mst_account_heads s "
						+ " WHERE S.ACCOUNT_HEAD_CODE=T.ACCOUNT_HEAD_CODE "
						+ " )AS head_desc, "
						+ " t.cr_dr_indicator, "
						+ " t.sub_ledger_type_code, "
						+ " (SELECT d.sub_ledger_type_desc "
						+ " FROM COM_MST_SL_TYPES d "
						+ " WHERE d.sub_ledger_type_code=t.sub_ledger_type_code "
						+ " )AS type_desc, "
						+ " t.sub_ledger_code, "
						+ " (SELECT v.sl_codename "
						+ " FROM sl_type_code_name_view v "
						+ " WHERE v.sl_type=t.sub_ledger_type_code "
						+ " AND v.sl_code  =t.sub_ledger_code "
						+ " )AS codedesc,m.ACCOUNTING_UNIT_ID,M.ACCOUNTING_FOR_OFFICE_ID,M.CASHBOOK_YEAR,M.CASHBOOK_MONTH "
						+ " FROM FAS_MEMO_OF_PAYMENT_MST M, "
						+ "   FAS_MEMO_OF_PAYMENT_TRN T "
						+ " WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID "
						+ " AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID "
						+ " AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR "
						+ " AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH "
						+ " AND M.BILL_NO                 =T.BILL_NO "
						+ " AND m.STATUS                  ='L' "
						+ " AND t.first_party             ='Y' " +
						// " AND m.PVR_NO                 IS NULL "+
						" AND T.Payment_Unit    =  "
						+ cmbAcc_UnitCode
						+ " AND t.payment_office =  "
						+ cmbOffice_code
						+
						/*
						 * " AND M.CASHBOOK_YEAR           =  "+cashYear+
						 * " AND M.CASHBOOK_MONTH          ="+cashMonth+""
						 */
						"	 AND  "
						+ "  (M.CASHBOOK_YEAR           = "
						+ cashYear
						+ "   AND M.CASHBOOK_MONTH          <= "
						+ cashMonth
						+ " OR  "
						+ "  M.CASHBOOK_YEAR           < "
						+ cashYear
						+ " ) "
						+ " and t.PVR_NO is null"
						+ ")a "
						+
						// " right outer join  "+
						" inner join  "
						+ " ( "
						+ " SELECT mtc_70_register_date,ACCOUNTING_UNIT_ID,accounting_unit_office_id,CASHBOOK_YEAR, "
						+ " CASHBOOK_MONTH,bill_no,BILL_MINOR_TYPE_CODE,BILL_MAJOR_TYPE,BILL_SUB_TYPE_CODE "
						+ " FROM fas_bill_register_master "
						+ " WHERE "
			/*			+ "ACCOUNTING_UNIT_ID     =  "
						+ cmbAcc_UnitCode
						+ " AND accounting_unit_office_id=  "
						+ cmbOffice_code
						+*/
						// " AND CASHBOOK_YEAR            =  "+cashYear+
						// " AND CASHBOOK_MONTH           = "+cashMonth+
					//	"	 AND  "
						+ "  (CASHBOOK_YEAR           = "
						+ cashYear
						+ "   AND CASHBOOK_MONTH          <= "
						+ cashMonth
						+ " OR  "
						+ "  CASHBOOK_YEAR           < "
						+ cashYear
						+ " ) "
						+ " and (( MTC70ENTRY ='Y' AND mtc_70_register_date    IS NOT NULL ) or (  MTC70ENTRY <> 'Y' AND mtc_70_register_date    IS NULL  ) ) "
						+ " and STATUS='L'   and Bill_date < '01-Apr-2015' "
						+

						" )b "
						+ " on a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID "
						+ " and a.ACCOUNTING_FOR_OFFICE_ID=b.accounting_unit_office_id "
						+ " and a.CASHBOOK_YEAR=b.CASHBOOK_YEAR "
						+ " and a.CASHBOOK_MONTH=b.CASHBOOK_MONTH "
						+ " and a.BILL_NO=b.bill_no"
						// joan added on 05 May 2015
						+" union all    select a.*,b.BILL_MINOR_TYPE_CODE,b.BILL_MAJOR_TYPE,b.BILL_SUB_TYPE_CODE from "
						+ " (SELECT m.sanctioned_amount, "
						+ "   TO_CHAR(M.BILL_DATE,'dd/MM/yyyy') AS BILL_DATE, "
						+ " M.BILL_NO, "
						+ " T.AMOUNT, "
						+ " t.PARTICULARS, "
						+ " T.ACCOUNT_HEAD_CODE, "
						+ " (SELECT S.ACCOUNT_HEAD_DESC "
						+ " FROM com_mst_account_heads s "
						+ " WHERE S.ACCOUNT_HEAD_CODE=T.ACCOUNT_HEAD_CODE "
						+ " )AS head_desc, "
						+ " t.cr_dr_indicator, "
						+ " t.sub_ledger_type_code, "
						+ " (SELECT d.sub_ledger_type_desc "
						+ " FROM COM_MST_SL_TYPES d "
						+ " WHERE d.sub_ledger_type_code=t.sub_ledger_type_code "
						+ " )AS type_desc, "
						+ " t.sub_ledger_code, "
						+ " (SELECT v.sl_codename "
						+ " FROM sl_type_code_name_view v "
						+ " WHERE v.sl_type=t.sub_ledger_type_code "
						+ " AND v.sl_code  =t.sub_ledger_code "
						+ " )AS codedesc,m.ACCOUNTING_UNIT_ID,M.ACCOUNTING_FOR_OFFICE_ID,M.CASHBOOK_YEAR,M.CASHBOOK_MONTH "
						+ " FROM FAS_MEMO_OF_PAYMENT_MST M, "
						+ "   FAS_MEMO_OF_PAYMENT_TRN T "
						+ " WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID "
						+ " AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID "
						+ " AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR "
						+ " AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH "
						+ " AND M.BILL_NO                 =T.BILL_NO "
						+ " AND m.STATUS                  ='L' "
						+ " AND t.first_party             ='Y' " +
						// " AND m.PVR_NO                 IS NULL "+
						" AND t.Payment_Unit      =  "
						+ cmbAcc_UnitCode
						+ " AND t.payment_office=  "
						+ cmbOffice_code
						+
						/*
						 * " AND M.CASHBOOK_YEAR           =  "+cashYear+
						 * " AND M.CASHBOOK_MONTH          ="+cashMonth+""
						 */
						"	 AND  "
						+ "  (M.CASHBOOK_YEAR           = "
						+ cashYear
						+ "   AND M.CASHBOOK_MONTH          <= "
						+ cashMonth
						+ " OR  "
						+ "  M.CASHBOOK_YEAR           < "
						+ cashYear
						+ " ) "
						+ " and t.PVR_NO is null"
						+ ")a "
						+
						// " right outer join  "+
						" inner join  "
						+ " ( "
						+ " SELECT mtc_70_register_date,ACCOUNTING_UNIT_ID,accounting_unit_office_id,CASHBOOK_YEAR, "
						+ " CASHBOOK_MONTH,bill_no,BILL_MINOR_TYPE_CODE,BILL_MAJOR_TYPE,BILL_SUB_TYPE_CODE "
						+ " FROM fas_bill_register_masterNEW "
						+ " WHERE "
					/*	+ "ACCOUNTING_UNIT_ID     =  "
						+ cmbAcc_UnitCode
						+ " AND accounting_unit_office_id=  "
						+ cmbOffice_code
						+
						// " AND CASHBOOK_YEAR            =  "+cashYear+
						// " AND CASHBOOK_MONTH           = "+cashMonth+
						"	 AND  "*/
						+ "  (CASHBOOK_YEAR           = "
						+ cashYear
						+ "   AND CASHBOOK_MONTH          <= "
						+ cashMonth
						+ " OR  "
						+ "  CASHBOOK_YEAR           < "
						+ cashYear
						+ " ) "
						+ " and (( MTC70ENTRY ='Y' AND mtc_70_register_date    IS NOT NULL ) or (  MTC70ENTRY <> 'Y' AND mtc_70_register_date    IS NULL  ) ) "
						+ " and STATUS='L'   "
						+

						" )b "
						+ " on a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID "
						+ " and a.ACCOUNTING_FOR_OFFICE_ID=b.accounting_unit_office_id "
						+ " and a.CASHBOOK_YEAR=b.CASHBOOK_YEAR "
						+ " and a.CASHBOOK_MONTH=b.CASHBOOK_MONTH "
						+ " and a.BILL_NO=b.bill_no"
						
						;
				System.out.println("ss >> "+ss);
		try{
			PreparedStatement	ps11 = con.prepareStatement(ss);
			ResultSet	rs11 = ps11.executeQuery();
			if(rs11.next()){
				int major=rs11.getInt("BILL_MAJOR_TYPE");
				int minor=rs11.getInt("BILL_MINOR_TYPE_CODE");
				int sub=rs11.getInt("BILL_SUB_TYPE_CODE");
				
				if(major==2)
				{
					if(minor==2)
					{
						if(sub==1)
						{
						withpro=" and m.jvr_no is not null ";
						}
					}
				}
				
				ss="select a.*,b.BILL_MINOR_TYPE_CODE,b.BILL_MAJOR_TYPE,b.BILL_SUB_TYPE_CODE from "+
				" (SELECT m.sanctioned_amount, "+
				"   TO_CHAR(M.BILL_DATE,'dd/MM/yyyy') AS BILL_DATE, "+
				" M.BILL_NO, "+
				" T.AMOUNT, "+
				" t.PARTICULARS, "+
				" T.ACCOUNT_HEAD_CODE, "+
				" (SELECT S.ACCOUNT_HEAD_DESC "+
				" FROM com_mst_account_heads s "+
				" WHERE S.ACCOUNT_HEAD_CODE=T.ACCOUNT_HEAD_CODE "+
				" )AS head_desc, "+
				" t.cr_dr_indicator, "+
				" t.sub_ledger_type_code, "+
				" (SELECT d.sub_ledger_type_desc "+
				" FROM COM_MST_SL_TYPES d "+
				" WHERE d.sub_ledger_type_code=t.sub_ledger_type_code "+
				" )AS type_desc, "+
				" t.sub_ledger_code, "+
				" (SELECT v.sl_codename "+
				" FROM sl_type_code_name_view v "+
				" WHERE v.sl_type=t.sub_ledger_type_code "+
				" AND v.sl_code  =t.sub_ledger_code "+
				" )AS codedesc,m.ACCOUNTING_UNIT_ID,M.ACCOUNTING_FOR_OFFICE_ID,M.CASHBOOK_YEAR,M.CASHBOOK_MONTH "+
				" FROM FAS_MEMO_OF_PAYMENT_MST M, "+
				"   FAS_MEMO_OF_PAYMENT_TRN T "+
				" WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID "+
				" AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID "+
				" AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR "+
				" AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH "+
				" AND M.BILL_NO                 =T.BILL_NO "+
				" AND m.STATUS                  ='L' "+
				" AND t.first_party             ='Y' "+
				//" AND m.PVR_NO                 IS NULL "+
				" AND t.Payment_Unit      =  "
				+ cmbAcc_UnitCode
				+ " AND t.payment_office=  "+cmbOffice_code+
			/*	" AND M.CASHBOOK_YEAR           =  "+cashYear+
				" AND M.CASHBOOK_MONTH          ="+cashMonth+*/
					"	 AND  "+
			"  (M.CASHBOOK_YEAR           = "+cashYear+
			"   AND M.CASHBOOK_MONTH          <= "+cashMonth+
			" OR  "+
			"  M.CASHBOOK_YEAR           < "+cashYear+" ) "+
				" and t.PVR_NO is null "+
				withpro+")a "+
				" inner join  "+
				" ( "+
				" SELECT mtc_70_register_date,ACCOUNTING_UNIT_ID,accounting_unit_office_id,CASHBOOK_YEAR, "+
				" CASHBOOK_MONTH,bill_no,BILL_MINOR_TYPE_CODE,BILL_MAJOR_TYPE,BILL_SUB_TYPE_CODE "+
				" FROM fas_bill_register_master "+
				" WHERE "+
			/*	+ "ACCOUNTING_UNIT_ID     =  "+cmbAcc_UnitCode+
				" AND accounting_unit_office_id=  "+cmbOffice_code+
			//	" AND CASHBOOK_YEAR            =  "+cashYear+
				//" AND CASHBOOK_MONTH           = "+cashMonth+
						"	 AND  "+*/
			"  (CASHBOOK_YEAR           = "+cashYear+
			"   AND CASHBOOK_MONTH          <= "+cashMonth+
			" OR  "+
			"  CASHBOOK_YEAR           < "+cashYear+" ) "+
				//" AND mtc_70_register_date    IS NOT NULL "+
				" and (( MTC70ENTRY ='Y' AND mtc_70_register_date    IS NOT NULL ) or (  MTC70ENTRY <> 'Y' AND mtc_70_register_date    IS NULL  ) ) "+
				" and STATUS='L'  and Bill_date < '01-Apr-2015' "+
				" )b "+
				" on a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID "+
				" and a.ACCOUNTING_FOR_OFFICE_ID=b.accounting_unit_office_id "+
				" and a.CASHBOOK_YEAR=b.CASHBOOK_YEAR "+
				" and a.CASHBOOK_MONTH=b.CASHBOOK_MONTH "+
				" and a.BILL_NO=b.bill_no"+
				" union all "+
				" select a.*,b.BILL_MINOR_TYPE_CODE,b.BILL_MAJOR_TYPE,b.BILL_SUB_TYPE_CODE from "+
				" (SELECT m.sanctioned_amount, "+
				"   TO_CHAR(M.BILL_DATE,'dd/MM/yyyy') AS BILL_DATE, "+
				" M.BILL_NO, "+
				" T.AMOUNT, "+
				" t.PARTICULARS, "+
				" T.ACCOUNT_HEAD_CODE, "+
				" (SELECT S.ACCOUNT_HEAD_DESC "+
				" FROM com_mst_account_heads s "+
				" WHERE S.ACCOUNT_HEAD_CODE=T.ACCOUNT_HEAD_CODE "+
				" )AS head_desc, "+
				" t.cr_dr_indicator, "+
				" t.sub_ledger_type_code, "+
				" (SELECT d.sub_ledger_type_desc "+
				" FROM COM_MST_SL_TYPES d "+
				" WHERE d.sub_ledger_type_code=t.sub_ledger_type_code "+
				" )AS type_desc, "+
				" t.sub_ledger_code, "+
				" (SELECT v.sl_codename "+
				" FROM sl_type_code_name_view v "+
				" WHERE v.sl_type=t.sub_ledger_type_code "+
				" AND v.sl_code  =t.sub_ledger_code "+
				" )AS codedesc,m.ACCOUNTING_UNIT_ID,M.ACCOUNTING_FOR_OFFICE_ID,M.CASHBOOK_YEAR,M.CASHBOOK_MONTH "+
				" FROM FAS_MEMO_OF_PAYMENT_MST M, "+
				"   FAS_MEMO_OF_PAYMENT_TRN T "+
				" WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID "+
				" AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID "+
				" AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR "+
				" AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH "+
				" AND M.BILL_NO                 =T.BILL_NO "+
				" AND m.STATUS                  ='L' "+
				" AND t.first_party             ='Y' "+
				//" AND m.PVR_NO                 IS NULL "+
				" AND t.Payment_Unit      =  "
				+ cmbAcc_UnitCode
				+ " AND t.payment_office=  "+cmbOffice_code+
			/*	" AND M.CASHBOOK_YEAR           =  "+cashYear+
				" AND M.CASHBOOK_MONTH          ="+cashMonth+*/
					"	 AND  "+
			"  (M.CASHBOOK_YEAR           = "+cashYear+
			"   AND M.CASHBOOK_MONTH          <= "+cashMonth+
			" OR  "+
			"  M.CASHBOOK_YEAR           < "+cashYear+" ) "+
				" and t.PVR_NO is null "+
				withpro+")a "+
				" inner join  "+
				" ( "+
				" SELECT mtc_70_register_date,ACCOUNTING_UNIT_ID,accounting_unit_office_id,CASHBOOK_YEAR, "+
				" CASHBOOK_MONTH,bill_no,BILL_MINOR_TYPE_CODE,BILL_MAJOR_TYPE,BILL_SUB_TYPE_CODE "+
				" FROM fas_bill_register_masterNEW "+
				" WHERE "+
			/*	+ " ACCOUNTING_UNIT_ID     =  "+cmbAcc_UnitCode+
				" AND accounting_unit_office_id=  "+cmbOffice_code+
			//	" AND CASHBOOK_YEAR            =  "+cashYear+
				//" AND CASHBOOK_MONTH           = "+cashMonth+
						"	 AND  "+*/
			"  (CASHBOOK_YEAR           = "+cashYear+
			"   AND CASHBOOK_MONTH          <= "+cashMonth+
			" OR  "+
			"  CASHBOOK_YEAR           < "+cashYear+" ) "+
				//" AND mtc_70_register_date    IS NOT NULL "+
				" and (( MTC70ENTRY ='Y' AND mtc_70_register_date    IS NOT NULL ) or (  MTC70ENTRY <> 'Y' AND mtc_70_register_date    IS NULL  ) ) "+
				" and STATUS='L'   "+
				" )b "+
				" on a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID "+
				" and a.ACCOUNTING_FOR_OFFICE_ID=b.accounting_unit_office_id "+
				" and a.CASHBOOK_YEAR=b.CASHBOOK_YEAR "+
				" and a.CASHBOOK_MONTH=b.CASHBOOK_MONTH "+
				" and a.BILL_NO=b.bill_no ";
				
				
			}
			else{//without sanc proceeding
				ss="select a.*,b.BILL_MINOR_TYPE_CODE,b.BILL_MAJOR_TYPE,b.BILL_SUB_TYPE_CODE from "+
				" (SELECT m.sanctioned_amount, "+
				"   TO_CHAR(M.BILL_DATE,'dd/MM/yyyy') AS BILL_DATE, "+
				" M.BILL_NO, "+
				" T.AMOUNT, "+
				" t.PARTICULARS, "+
				" T.ACCOUNT_HEAD_CODE, "+
				" (SELECT S.ACCOUNT_HEAD_DESC "+
				" FROM com_mst_account_heads s "+
				" WHERE S.ACCOUNT_HEAD_CODE=T.ACCOUNT_HEAD_CODE "+
				" )AS head_desc, "+
				" t.cr_dr_indicator, "+
				" t.sub_ledger_type_code, "+
				" (SELECT d.sub_ledger_type_desc "+
				" FROM COM_MST_SL_TYPES d "+
				" WHERE d.sub_ledger_type_code=t.sub_ledger_type_code "+
				" )AS type_desc, "+
				" t.sub_ledger_code, "+
				"( SELECT PAYEE_CODENAME FROM PAYEE_TYPE_CODE_NAME_VIEW " +
				" WHERE PAYEE_TYPE= t.sub_ledger_type_code and PAYEE_CODE=t.sub_ledger_code "+
				" )AS codedesc,m.ACCOUNTING_UNIT_ID,M.ACCOUNTING_FOR_OFFICE_ID,M.CASHBOOK_YEAR,M.CASHBOOK_MONTH "+
				" FROM FAS_MEMO_OF_PAYMENT_MST M, "+
				"   FAS_MEMO_OF_PAYMENT_TRN T "+
				" WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID "+
				" AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID "+
				" AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR "+
				" AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH "+
				" AND M.BILL_NO                 =T.BILL_NO "+
				" AND m.STATUS                  ='L' "+
				" AND t.first_party             ='Y' "+
				" AND m.PVR_NO                 IS NULL "+
				" AND t.Payment_Unit      =  "
				+ cmbAcc_UnitCode
				+ " AND t.payment_office=  "+cmbOffice_code+
			/*	" AND M.CASHBOOK_YEAR           =  "+cashYear+
				" AND M.CASHBOOK_MONTH          ="+cashMonth*/
					"	 AND  "+
			"  (M.CASHBOOK_YEAR           = "+cashYear+
			"   AND M.CASHBOOK_MONTH          <= "+cashMonth+
			" OR  "+
			"  M.CASHBOOK_YEAR           < "+cashYear+" ) "
				+" and"
						+ " t.PVR_NO is null"
						+ ")a "+
				//" right outer join  "+
				" inner join  "+
				" ( "+
				" SELECT mtc_70_register_date,ACCOUNTING_UNIT_ID,accounting_unit_office_id,CASHBOOK_YEAR, "+
				" CASHBOOK_MONTH,bill_no,BILL_MINOR_TYPE_CODE,BILL_MAJOR_TYPE,BILL_SUB_TYPE_CODE "+
				" FROM FAS_BILL_REGISTERNEW "+
				" WHERE "+
			/*	+ "ACCOUNTING_UNIT_ID     =  "+cmbAcc_UnitCode+
				" AND accounting_unit_office_id=  "+cmbOffice_code+*/
				/*" AND CASHBOOK_YEAR            =  "+cashYear+
				" AND CASHBOOK_MONTH           = "+cashMonth+*/
				//	"	 AND  "+
			"  (CASHBOOK_YEAR           = "+cashYear+
			"   AND CASHBOOK_MONTH          <= "+cashMonth+
			" OR  "+
			"  CASHBOOK_YEAR           < "+cashYear+" ) "+
			//	" AND mtc_70_register_date    IS NOT NULL "+
				" and (( MTC70ENTRY ='Y' AND mtc_70_register_date    IS NOT NULL ) or (  MTC70ENTRY <> 'Y' AND mtc_70_register_date    IS NULL  ) ) "+
				" and STATUS='L' "+
				" )b "+
				" on a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID "+
				" and a.ACCOUNTING_FOR_OFFICE_ID=b.accounting_unit_office_id "+
				" and a.CASHBOOK_YEAR=b.CASHBOOK_YEAR "+
				" and a.CASHBOOK_MONTH=b.CASHBOOK_MONTH "+
				" and a.BILL_NO=b.bill_no";
				
			}
			
			
		}catch(Exception e){
			System.out.println("Error in map table");
		}
	
			
		//	}
		/*	else
			{
				ss="select a.*,b.BILL_MINOR_TYPE_CODE,b.BILL_MAJOR_TYPE,b.BILL_SUB_TYPE_CODE from "+
				" (SELECT m.sanctioned_amount, "+
				"   TO_CHAR(M.BILL_DATE,'dd/MM/yyyy') AS BILL_DATE, "+
				" M.BILL_NO, "+
				" T.AMOUNT, "+
				" t.PARTICULARS, "+
				" T.ACCOUNT_HEAD_CODE, "+
				" (SELECT S.ACCOUNT_HEAD_DESC "+
				" FROM com_mst_account_heads s "+
				" WHERE S.ACCOUNT_HEAD_CODE=T.ACCOUNT_HEAD_CODE "+
				" )AS head_desc, "+
				" t.cr_dr_indicator, "+
				" t.sub_ledger_type_code, "+
				" (SELECT d.sub_ledger_type_desc "+
				" FROM COM_MST_SL_TYPES d "+
				" WHERE d.sub_ledger_type_code=t.sub_ledger_type_code "+
				" )AS type_desc, "+
				" t.sub_ledger_code, "+
				" (SELECT v.sl_codename "+
				" FROM sl_type_code_name_view v "+
				" WHERE v.sl_type=t.sub_ledger_type_code "+
				" AND v.sl_code  =t.sub_ledger_code "+
				" )AS codedesc,m.ACCOUNTING_UNIT_ID,M.ACCOUNTING_FOR_OFFICE_ID,M.CASHBOOK_YEAR,M.CASHBOOK_MONTH "+
				" FROM FAS_MEMO_OF_PAYMENT_MST M, "+
				"   FAS_MEMO_OF_PAYMENT_TRN T "+
				" WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID "+
				" AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID "+
				" AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR "+
				" AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH "+
				" AND M.BILL_NO                 =T.BILL_NO "+
				" AND m.STATUS                  ='L' "+
				" AND t.first_party             ='Y' "+
				" AND m.PVR_NO                 IS NULL "+
				" AND m.ACCOUNTING_UNIT_ID      =  "+cmbAcc_UnitCode+
				" AND M.ACCOUNTING_FOR_OFFICE_ID=  "+cmbOffice_code+
				" AND M.CASHBOOK_YEAR           =  "+cashYear+
				" AND M.CASHBOOK_MONTH          ="+cashMonth+" and t.PVR_NO is null "+withpro+")a "+
				" inner join  "+
				" ( "+
				" SELECT mtc_70_register_date,ACCOUNTING_UNIT_ID,accounting_unit_office_id,CASHBOOK_YEAR, "+
				" CASHBOOK_MONTH,bill_no,BILL_MINOR_TYPE_CODE,BILL_MAJOR_TYPE,BILL_SUB_TYPE_CODE "+
				" FROM fas_bill_register_master "+
				" WHERE ACCOUNTING_UNIT_ID     =  "+cmbAcc_UnitCode+
				" AND accounting_unit_office_id=  "+cmbOffice_code+
				" AND CASHBOOK_YEAR            =  "+cashYear+
				" AND CASHBOOK_MONTH           = "+cashMonth+
				" AND mtc_70_register_date    IS NOT NULL and PRE_AUDIT_DATE is not null "+
				" and STATUS='L' "+
				" )b "+
				" on a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID "+
				" and a.ACCOUNTING_FOR_OFFICE_ID=b.accounting_unit_office_id "+
				" and a.CASHBOOK_YEAR=b.CASHBOOK_YEAR "+
				" and a.CASHBOOK_MONTH=b.CASHBOOK_MONTH "+
				" and a.BILL_NO=b.bill_no";
			}  */
			
			System.out.println("load q::"+ss);
			
			try {
				ps = con.prepareStatement(ss);
				rs = ps.executeQuery();

				while (rs.next()) {
					xml = xml + "<sanctionedamount>" + rs.getInt("sanctioned_amount")+ "</sanctionedamount>";
					xml = xml + "<billno>" + rs.getInt("BILL_NO")+ "</billno>";
					xml = xml + "<billdate>" + rs.getString("BILL_DATE")+ "</billdate>";
					xml = xml + "<passamount>" + rs.getInt("AMOUNT")+ "</passamount>";
					xml = xml + "<achead>" + rs.getInt("ACCOUNT_HEAD_CODE")+ "</achead>";
					xml = xml + "<head_desc>" + rs.getString("head_desc")+ "</head_desc>";
					xml = xml + "<indicator>" + rs.getString("cr_dr_indicator")+ "</indicator>";
					xml = xml + "<typecode>" + rs.getString("sub_ledger_type_code")+ "</typecode>";
					xml = xml + "<type_desc>" + rs.getString("type_desc")+ "</type_desc>";
					xml = xml + "<ledgercode>" + rs.getString("sub_ledger_code")+ "</ledgercode>";
					xml = xml + "<codedesc>" + rs.getString("codedesc")+ "</codedesc>";
					xml = xml + "<particulars>" + rs.getString("PARTICULARS")+ "</particulars>";
					
					xml = xml + "<majortype>" + rs.getInt("BILL_MAJOR_TYPE")+ "</majortype>";
					xml = xml + "<minortype>" + rs.getInt("BILL_MINOR_TYPE_CODE")+ "</minortype>";
					xml = xml + "<subtype>" + rs.getInt("BILL_SUB_TYPE_CODE")+ "</subtype>";
					
					y++;
				}
				if (y != 0) {
					xml = xml + "<flag>success</flag>";
				} else
					xml = xml + "<flag>failure</flag>";

				ps.close();
				rs.close();
			} catch (Exception e) {
				System.out.println("catch..HERE.in load supplier." + e);
				xml = xml + "<flag>failure</flag>";
			}
			}else{
				xml = xml + "<flag>TB</flag>";
			}
				
			xml = xml + "</response>";
			// System.out.println(xml);
			out.println(xml);
		}else if(command.equalsIgnoreCase("getEmpIDCombo")){
			int cmbAcc_UnitCode = 0, cmbOffice_code = 0,txtEmpId=0;
			String ss="";
			int y = 0;
			xml = "<response><command>" + command + "</command>";
			
			try {
				cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("cmbAcc_UnitCode"));
			} catch (Exception e) {
				System.out.println("error get acc unit code");
			}
			try {
				cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			} catch (Exception e) {
				System.out.println("error get office id");
			}
			
			int cashMonth = Integer.parseInt(request.getParameter("check_memo_Month"));
			int cashYear = Integer.parseInt(request.getParameter("check_memo_Year"));
			String memoType=request.getParameter("memotype");
			try {
			if(memoType.equalsIgnoreCase("1")){
				 ss="SELECT m.EMPLOYEE_ID as code,ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID, " 
						 +cashMonth+" as CASHBOOK_MONTH,"
						 	 +cashYear+" as CASHBOOK_YEAR,"
						+"  (SELECT EMPLOYEE_NAME" 
						+"    ||' ' " 
						+"    ||EMPLOYEE_INITIAL AS emp " 
						+"  FROM HRM_MST_EMPLOYEES s " 
						+"  WHERE s.EMPLOYEE_ID=m.EMPLOYEE_ID " 
						+"  )AS codedesc, " 
						+"  (SELECT o.office_name " 
						+"  FROM com_mst_offices o " 
						+"  WHERE o.office_id= " 
						+"    (SELECT OFFICE_ID " 
						+"    FROM hrm_emp_current_posting g " 
						+"    WHERE g.employee_id=m.EMPLOYEE_ID " 
						+"    ) " 
						+"  )officename " 
						+" FROM FAS_DRAWING_OFFICER_MST m " 
						+" WHERE ACCOUNTING_UNIT_ID    = " +cmbAcc_UnitCode
						+" AND ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code 
						+" ORDER BY m.EMPLOYEE_ID";
			}else{
				
			
			ss="SELECT DISTINCT t.sub_ledger_code as code, " +
			"  (SELECT v.sl_codename " +
			"  FROM sl_type_code_name_view v " +
//			"  WHERE v.sl_type= DECODE(t.sub_ledger_type_code,1,7,t.sub_ledger_type_code)" +
			"  WHERE v.sl_type= case when (t.sub_ledger_type_code=1) then 7 else t.sub_ledger_type_code end "+

			"  AND v.sl_code  =t.sub_ledger_code " +
			"  )AS codedesc, " +
			"  m.ACCOUNTING_UNIT_ID, " +
			"  M.ACCOUNTING_FOR_OFFICE_ID " +
		/*	"  M.CASHBOOK_YEAR, " +
			"  M.CASHBOOK_MONTH " +*/
			" FROM FAS_MEMO_OF_PAYMENT_MST M, " +
			"  FAS_MEMO_OF_PAYMENT_TRN T " +
			" WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID " +
			" AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " +
			" AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR " +
			" AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH " +
			" AND M.BILL_NO                 =T.BILL_NO " +
			" AND m.STATUS                  ='L' " +
			" AND t.first_party             ='Y' " +
			" AND t.Payment_Unit          =  " +cmbAcc_UnitCode+
			" AND t.payment_office   =  " +cmbOffice_code+
		//	" AND M.CASHBOOK_YEAR           =  " +cashYear+
		//	" AND M.CASHBOOK_MONTH          = " +cashMonth+
			" AND t.PVR_NO                 IS NULL";
			}
			System.out.println("ss :::: "+ss);
				ps = con.prepareStatement(ss);
				rs = ps.executeQuery();

				while (rs.next()) {
					xml = xml + "<leng>";
					xml = xml + "<Empid>"+rs.getInt("code")+"</Empid>";
					xml = xml + "<EmpName>"+rs.getString("codedesc")+"</EmpName>";
					xml = xml + "</leng>";
					y++;
				}if(y!=0)
				xml = xml + "<flag>success</flag>";
				else if(y==0)
					xml = xml + "<flag>failure</flag>";
		}catch (Exception e) {
			e.printStackTrace();
			xml = xml + "<flag>failure</flag>";
		}
		xml = xml + "</response>";
		System.out.println("xml :::: "+xml);
		out.println(xml);
		}
		else if (command.equalsIgnoreCase("getdet")) {
			
			String ss="",sub_str="";
			int cmbAcc_UnitCode = 0, cmbOffice_code = 0,txtEmpId=0;
			int cmbSL_type = 0;
			int addtional_field_value = 0;
			int y = 0;
			xml = "<response><command>" + command + "</command>";
			int memotype=Integer.parseInt(request.getParameter("memotype"));
			System.out.println("memotype"+memotype);
			try {
				cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("cmbAcc_UnitCode"));
			} catch (Exception e) {
				System.out.println("error get acc unit code");
			}
			try {
				cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			} catch (Exception e) {
				System.out.println("error get office id");
			}
			
			try {
				txtEmpId = Integer.parseInt(request.getParameter("txtEmpId"));
				System.out.println("txtEmpId"+txtEmpId);
			} catch (Exception e) {
				System.out.println("error get txtEmpId:");
			}
			int cashMonth = Integer.parseInt(request.getParameter("check_memo_Month"));
			int cashYear = Integer.parseInt(request.getParameter("check_memo_Year"));
//			String checkDate=request.getParameter("chequedate");
//			
//			String[] cashMonthYear = checkDate.split("/");
//			int cashMonth = Integer.parseInt(cashMonthYear[1]);
//			int cashYear = Integer.parseInt(cashMonthYear[2]);
			String qry1="",qry2="";
			if(memotype!=3){
				sub_str="";	
				qry1=" ";
				qry2=" ";
				
			}else if(memotype==3){
				sub_str=" and t.sub_ledger_code="+txtEmpId;
				qry1="AND t.PAYABLE_TO               = '"+txtEmpId+"'";
				qry2="AND PAYEE_CODE               ="+txtEmpId;
			}
			int majortype = Integer.parseInt(request.getParameter("majortype"));
			int minortype = Integer.parseInt(request.getParameter("minortype"));
			int subtype = Integer.parseInt(request.getParameter("subtype"));
			System.out.println(majortype+" >>> " +minortype+" >>> "+subtype);
			if(cmbOffice_code!=5000)
			{
				
				System.out.println("testing  ");
				ss="SELECT a.*, " +
						"  b.mtc_70_register_date AS not_yes_treasury_date, " +
						"  (SELECT BILL_SUB_TYPE_DESC " +
						"  FROM FAS_BILL_SUB_TYPES " +
						 " WHERE BILL_MAJOR_TYPE_CODE= " +majortype+
						 "  AND BILL_MINOR_TYPE_CODE  =" +minortype+
						 " AND BILL_SUB_TYPE_CODE    = " +subtype+
						"  ) AS sub_desc, " +
						"  (SELECT BILL_MINOR_TYPE_DESC " +
						"  FROM FAS_BILL_MINOR_TYPES_MST " +
						 " WHERE BILL_MAJOR_TYPE_CODE= " +majortype+
						 "  AND BILL_MINOR_TYPE_CODE  =" +minortype+
						"  ) AS minor_desc, " +
					//	"  --  (SELECT SANCTION_PROC_NO " +
					//	"  --  FROM HRM_SANCTIONS_BILLS_LINK_MST " +
					//	"  --  WHERE HRMS_SANCTION_ID=a.SANCTION_PROCEEDING_NO " +
					//	"  --  ) " +
						"  SanProcNo AS sanction_desc, " +
						"  CASE " +
						"    WHEN b.BILL_TYPE='WOSP' " +
						"    THEN b.BILL_DATE " +
						"    ELSE " +
						"      (SELECT SANCTION_PROC_DATE " +
						"      FROM HRM_SANCTIONS_BILLS_LINK_MST " +
					//	"      WHERE HRMS_SANCTION_ID=SanProcNo " +
					"      WHERE HRMS_SANCTION_ID=a.SANCTION_PROCEEDING_NO::numeric " +	
						"      ) " +
						"  END AS sanction_date " +
						" FROM " +
						"  (SELECT m.sanctioned_amount, " +
						"    SANCTION_PROCEEDING_NO, " +
						"    TO_CHAR(M.BILL_DATE,'dd/MM/yyyy') AS BILL_DATE, " +
						"    M.BILL_NO, " +
						"    T.AMOUNT, " +
						"    t.PARTICULARS, " +
						"    T.ACCOUNT_HEAD_CODE, " +
						"    (SELECT S.ACCOUNT_HEAD_DESC " +
						"    FROM com_mst_account_heads s " +
						"    WHERE S.ACCOUNT_HEAD_CODE=T.ACCOUNT_HEAD_CODE " +
						"    )AS head_desc, " +
						"    t.cr_dr_indicator, " +
						"    t.sub_ledger_type_code, " +
						"    (SELECT d.sub_ledger_type_desc " +
						"    FROM COM_MST_SL_TYPES d " +
						"    WHERE d.sub_ledger_type_code=t.sub_ledger_type_code " +
						"    )AS type_desc, " +
						"    t.sub_ledger_code, " +
						"    (SELECT PAYEE_CODENAME " +
						"    FROM PAYEE_TYPE_CODE_NAME_VIEW " +
						"    WHERE PAYEE_TYPE= t.sub_ledger_type_code " +
						"    AND PAYEE_CODE  =t.sub_ledger_code " +
						"    )AS codedesc, " +
						"    m.ACCOUNTING_UNIT_ID, " +
						"    M.ACCOUNTING_FOR_OFFICE_ID, " +
						"    M.CASHBOOK_YEAR, " +
						"    M.CASHBOOK_MONTH , t.SL_NO" +
						"  FROM FAS_MEMO_OF_PAYMENT_MST M, " +
						"    FAS_MEMO_OF_PAYMENT_TRN T " +
						"  WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID " +
						"  AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " +
						"  AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR " +
						"  AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH " +
						"  AND M.BILL_NO                 =T.BILL_NO " +
						"  AND m.STATUS                  ='L' " +
						"  AND t.first_party             ='Y' " +
						"  AND t.PVR_NO                 IS NULL " +
					/*	" AND m.ACCOUNTING_UNIT_ID      =  "+cmbAcc_UnitCode+
						" AND M.ACCOUNTING_FOR_OFFICE_ID=  "+cmbOffice_code+*/
						" AND t.Payment_Unit          =  " +cmbAcc_UnitCode+
						" AND t.payment_office   =  " +cmbOffice_code+
					/*	" AND M.CASHBOOK_YEAR           =  "+cashYear+
						" AND M.CASHBOOK_MONTH          ="+cashMonth+""*/
								 " "+sub_str+")a "+
					//	" RIGHT OUTER JOIN " +
				//				 " LEFT OUTER JOIN "+
				 " INNER JOIN "+
						"  (SELECT TO_CHAR(m.mtc_70_register_date,'dd/MM/yyyy') AS mtc_70_register_date, " +
						"    TO_CHAR(m.PRE_AUDIT_DATE,'dd/MM/yyyy')             AS PRE_AUDIT_DATE, " +
						"    m.ACCOUNTING_UNIT_ID, " +
						"    m.accounting_unit_office_id, " +
						"    m.CASHBOOK_YEAR, " +
						"    m.CASHBOOK_MONTH, " +
						"    ACCOUNT_HEAD_CODE ,t.amount, " +
						"    CASE " +
						"      WHEN M.BILL_TYPE         <> 'WOSP' " +
						"      AND M.BILL_MINOR_TYPE_CODE=2 " +
						"      AND m.BILL_MAJOR_TYPE     =2 " +
						"      AND m.BILL_SUB_TYPE_CODE  =1 " +
						"      THEN " +
						"        (SELECT SANCTION_PROC_NO " +
						"        FROM SLS_SANCTIONS_BILLS_LINK_MST1 " +
						"        WHERE HRMS_SANCTION_ID=m.SANCTION_PROC_NO::numeric  " +
						"        ) " +
						"      WHEN m.bill_type = 'WOSP' " +
						"      THEN m.SANCTION_PROC_NO " +
						"      ELSE " +
						"        (SELECT SANCTION_PROC_NO " +
						"        FROM HRM_SANCTIONS_BILLS_LINK_MST " +
						"        WHERE HRMS_SANCTION_ID=M.SANCTION_PROC_NO::numeric " +
						"        ) " +
						"    END AS SANPROCNO, " +
						"    m.bill_no, " +
						"    m.bill_type, " +
						"    M.BILL_DATE, t.SL_NO " +
						"  FROM fas_bill_register_master m, " +
						"    fas_bill_register_transaction t " +
						"  WHERE m.ACCOUNTING_UNIT_ID     =t.ACCOUNTING_UNIT_ID " +
						"  AND m.accounting_unit_office_id=t.accounting_unit_office_id " +
						"  AND m.CASHBOOK_YEAR            =t.CASHBOOK_YEAR " +
						"  AND m.CASHBOOK_MONTH           =t.CASHBOOK_MONTH " +
						"  AND m.BILL_NO                  =t.BILL_NO " +
				/*		"  and m.ACCOUNTING_UNIT_ID     =  "+cmbAcc_UnitCode+
						"  AND m.accounting_unit_office_id=  "+cmbOffice_code+*/
					/*	" AND m.CASHBOOK_YEAR            =  "+cashYear+
						" AND m.CASHBOOK_MONTH           =  "+cashMonth+*/
						"  AND ( ( M.MTC70ENTRY           ='Y' " +
						"  AND M.MTC_70_REGISTER_DATE    IS NOT NULL " +
						"  AND M.PRE_AUDIT_DATE          IS NOT NULL ) " +
						"  OR ( M.MTC70ENTRY              ='N' " +
						"  AND M.MTC_70_REGISTER_DATE    IS NULL " +
						"  AND M.PRE_AUDIT_DATE          IS NULL) ) " +
					//	"  AND t.PAYABLE_TO               =  " +txtEmpId+
					qry1+	"  AND m.STATUS                   ='L'   and M.BILL_APPROVED is not null and  M.BILL_DATE < '01-Apr-15' " +
						"  AND m.BILL_NO                 IN " +
						"    (SELECT memo.bill_no " +
						"    FROM FAS_MEMO_OF_PAYMENT_TRN memo " +
						"    WHERE memo.ACCOUNTING_UNIT_ID= m.ACCOUNTING_UNIT_ID " +
						"    AND memo.bill_no             =m.BILL_NO " +
						"    AND memo.cashbook_year       =m.CASHBOOK_YEAR " +
						"    AND memo.cashbook_month      =m.CASHBOOK_MONTH " +
						"    AND memo.pvr_date           IS NULL " +
						"    AND memo.pvr_no             IS NULL " +
						"    ) " +
						"  UNION ALL " +
						"  SELECT TO_CHAR(mtc_70_register_date,'dd/MM/yyyy') AS mtc_70_register_date, " +
						"    TO_CHAR(PRE_AUDIT_DATE,'dd/MM/yyyy')            AS PRE_AUDIT_DATE, " +
						"    ACCOUNTING_UNIT_ID, " +
						"    accounting_unit_office_id, " +
						"    CASHBOOK_YEAR, " +
						"    CASHBOOK_MONTH, " +
						"    ACCOUNT_HEAD_CODE ,0 as amount, " +
						"    SANCTION_PROC_NO AS SANPROCNO, " +
						"    bill_no, " +
						"    'WSP' AS bill_type, " +
						"    NULL  AS BILL_DATE , 1 as SL_NO" +
						"  FROM FAS_BILL_REGISTERNEW " +
						" WHERE "
					/*	+ "ACCOUNTING_UNIT_ID     =  "+cmbAcc_UnitCode+
						" AND accounting_unit_office_id=  "+cmbOffice_code+
						" AND CASHBOOK_YEAR            =  "+cashYear+
						" AND CASHBOOK_MONTH           = "+cashMonth+
						" AND "*/
						+ ""
						+ "mtc_70_register_date    IS NOT NULL and PRE_AUDIT_DATE is not null " +
					//	" and PAYEE_CODE="+txtEmpId+
						qry2+
						" and STATUS='L' "+
						" union all "+
					"	SELECT TO_CHAR(m.mtc_70_register_date,'dd/MM/yyyy') AS mtc_70_register_date, " +
						"    TO_CHAR(m.PRE_AUDIT_DATE,'dd/MM/yyyy')             AS PRE_AUDIT_DATE, " +
						"    m.ACCOUNTING_UNIT_ID, " +
						"    m.accounting_unit_office_id, " +
						"    m.CASHBOOK_YEAR, " +
						"    m.CASHBOOK_MONTH, " +
						"    ACCOUNT_HEAD_CODE ,t.amount, " +
						"    CASE " +
						"      WHEN M.BILL_TYPE         <> 'WOSP' " +
						"      AND M.BILL_MINOR_TYPE_CODE=2 " +
						"      AND m.BILL_MAJOR_TYPE     =2 " +
						"      AND m.BILL_SUB_TYPE_CODE  =1 " +
						"      THEN " +
						"        (SELECT SANCTION_PROC_NO " +
						"        FROM SLS_SANCTIONS_BILLS_LINK_MST1 " +
						"        WHERE HRMS_SANCTION_ID=m.SANCTION_PROC_NO::numeric " +
						"        ) " +
						"      WHEN m.bill_type = 'WOSP' " +
						"      THEN m.SANCTION_PROC_NO " +
						"      ELSE " +
						"        (SELECT SANCTION_PROC_NO " +
						"        FROM HRM_SANCTIONS_BILLS_LINK_MST " +
						"        WHERE HRMS_SANCTION_ID=M.SANCTION_PROC_NO::numeric " +
						"        ) " +
						"    END AS SANPROCNO, " +
						"    m.bill_no, " +
						"    m.bill_type, " +
						"    M.BILL_DATE, t.SL_NO " +
						"  FROM fas_bill_register_masternew m, " +
						"    fas_bill_register_transactionw t " +
						"  WHERE m.ACCOUNTING_UNIT_ID     =t.ACCOUNTING_UNIT_ID " +
						"  AND m.accounting_unit_office_id=t.accounting_unit_office_id " +
						"  AND m.CASHBOOK_YEAR            =t.CASHBOOK_YEAR " +
						"  AND m.CASHBOOK_MONTH           =t.CASHBOOK_MONTH " +
						"  AND m.BILL_NO                  =t.BILL_NO " +
					/*	"  and m.ACCOUNTING_UNIT_ID     =  "+cmbAcc_UnitCode+
						"  AND m.accounting_unit_office_id=  "+cmbOffice_code+*/
					/*	" AND m.CASHBOOK_YEAR            =  "+cashYear+
						" AND m.CASHBOOK_MONTH           =  "+cashMonth+*/
						"  AND ( ( M.MTC70ENTRY           ='Y' " +
						"  AND M.MTC_70_REGISTER_DATE    IS NOT NULL " +
						"  AND M.PRE_AUDIT_DATE          IS NOT NULL ) " +
						"  OR ( M.MTC70ENTRY              ='N' " +
						"  AND M.MTC_70_REGISTER_DATE    IS NULL " +
						"  AND M.PRE_AUDIT_DATE          IS NULL) ) " +
					//	"  AND t.PAYABLE_TO               =  " +txtEmpId+
					qry1+	"  AND m.STATUS                   ='L'   and M.BILL_APPROVED is not null " +
						"  AND m.BILL_NO                 IN " +
						"    (SELECT memo.bill_no " +
						"    FROM FAS_MEMO_OF_PAYMENT_TRN memo " +
						"    WHERE memo.ACCOUNTING_UNIT_ID= m.ACCOUNTING_UNIT_ID " +
						"    AND memo.bill_no             =m.BILL_NO " +
						"    AND memo.cashbook_year       =m.CASHBOOK_YEAR " +
						"    AND memo.cashbook_month      =m.CASHBOOK_MONTH " +
						"    AND memo.pvr_date           IS NULL " +
						"    AND memo.pvr_no             IS NULL " +
						"    ) " +
						"  UNION ALL " +
						"  SELECT TO_CHAR(mtc_70_register_date,'dd/MM/yyyy') AS mtc_70_register_date, " +
						"    TO_CHAR(PRE_AUDIT_DATE,'dd/MM/yyyy')            AS PRE_AUDIT_DATE, " +
						"    ACCOUNTING_UNIT_ID, " +
						"    accounting_unit_office_id, " +
						"    CASHBOOK_YEAR, " +
						"    CASHBOOK_MONTH, " +
						"    ACCOUNT_HEAD_CODE ,0 as amount, " +
						"    SANCTION_PROC_NO AS SANPROCNO, " +
						"    bill_no, " +
						"    'WSP' AS bill_type, " +
						"    NULL  AS BILL_DATE, 1 as SL_NO " +
						"  FROM FAS_BILL_REGISTERNEW " +
						" WHERE "
					/*	+ "ACCOUNTING_UNIT_ID     =  "+cmbAcc_UnitCode+
						" AND accounting_unit_office_id=  "+cmbOffice_code+
						" AND CASHBOOK_YEAR            =  "+cashYear+
						" AND CASHBOOK_MONTH           = "+cashMonth+
						" AND "
						+ ""
						+ ""*/
						+ "mtc_70_register_date    IS NOT NULL and PRE_AUDIT_DATE is not null " +
					//	" and PAYEE_CODE="+txtEmpId+
						qry2+
						" and STATUS='L' "+
						"  )b " +
						" ON a.ACCOUNTING_UNIT_ID       =b.ACCOUNTING_UNIT_ID " +
						" AND a.ACCOUNTING_FOR_OFFICE_ID=b.accounting_unit_office_id " +
						" AND a.CASHBOOK_YEAR           =b.CASHBOOK_YEAR " +
						" AND a.CASHBOOK_MONTH          =b.CASHBOOK_MONTH " +
						" AND a.BILL_NO                 =b.bill_no " +
						" AND a.ACCOUNT_HEAD_CODE       =b.ACCOUNT_HEAD_CODE and a.amount=b.amount and a.SL_NO=b.SL_NO  order by a.BILL_NO" ;
				
			/*ss="select a.*,b.mtc_70_register_date as not_yes_treasury_date," +
			 " (SELECT BILL_SUB_TYPE_DESC " +
			 " FROM FAS_BILL_SUB_TYPES " +
			 " WHERE BILL_MAJOR_TYPE_CODE= " +majortype+
			 "  AND BILL_MINOR_TYPE_CODE  =" +minortype+
			 " AND BILL_SUB_TYPE_CODE    = " +subtype+
			 ") " +
			 " AS " +
			 "  sub_desc, " +
			 "  (SELECT BILL_MINOR_TYPE_DESC " +
			 "  FROM FAS_BILL_MINOR_TYPES_MST " +
			 "  WHERE BILL_MAJOR_TYPE_CODE= " +majortype+
			 "  AND BILL_MINOR_TYPE_CODE  = " +minortype+
			 "  ) " +
			 "AS " +
			 "  minor_desc, " +
			 "  (SELECT SANCTION_PROC_NO " +
			 "  FROM HRM_SANCTIONS_BILLS_LINK_MST " +
			 "  WHERE HRMS_SANCTION_ID=a.SANCTION_PROCEEDING_NO " +
			 "  ) " +
			 " AS " +
			 "  sanction_desc, (SELECT SANCTION_PROC_DATE   FROM HRM_SANCTIONS_BILLS_LINK_MST   WHERE HRMS_SANCTION_ID=a.SANCTION_PROCEEDING_NO    ) AS sanction_date "+		
			" from "+
				" (SELECT m.sanctioned_amount,SANCTION_PROCEEDING_NO, "+
				"   TO_CHAR(M.BILL_DATE,'dd/MM/yyyy') AS BILL_DATE, "+
				" M.BILL_NO, "+
				" T.AMOUNT, "+
				" t.PARTICULARS, "+
				" T.ACCOUNT_HEAD_CODE, "+
				" (SELECT S.ACCOUNT_HEAD_DESC "+
				" FROM com_mst_account_heads s "+
				" WHERE S.ACCOUNT_HEAD_CODE=T.ACCOUNT_HEAD_CODE "+
				" )AS head_desc, "+
				" t.cr_dr_indicator, "+
				" t.sub_ledger_type_code, "+
				" (SELECT d.sub_ledger_type_desc "+
				" FROM COM_MST_SL_TYPES d "+
				" WHERE d.sub_ledger_type_code=t.sub_ledger_type_code "+
				" )AS type_desc, "+
				" t.sub_ledger_code, "+
				"( SELECT PAYEE_CODENAME FROM PAYEE_TYPE_CODE_NAME_VIEW " +
				"WHERE PAYEE_TYPE= t.sub_ledger_type_code and PAYEE_CODE=t.sub_ledger_code "+
				" )AS codedesc,m.ACCOUNTING_UNIT_ID,M.ACCOUNTING_FOR_OFFICE_ID,M.CASHBOOK_YEAR,M.CASHBOOK_MONTH "+
				" FROM FAS_MEMO_OF_PAYMENT_MST M, "+
				"   FAS_MEMO_OF_PAYMENT_TRN T "+
				" WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID "+
				" AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID "+
				" AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR "+
				" AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH "+
				" AND M.BILL_NO                 =T.BILL_NO "+
				" AND m.STATUS                  ='L' "+
				" AND t.first_party             ='Y' "+
				" AND t.PVR_NO                 IS NULL "+
				" AND m.ACCOUNTING_UNIT_ID      =  "+cmbAcc_UnitCode+
				" AND M.ACCOUNTING_FOR_OFFICE_ID=  "+cmbOffice_code+
				" AND M.CASHBOOK_YEAR           =  "+cashYear+
				" AND M.CASHBOOK_MONTH          ="+cashMonth+" "+sub_str+")a "+
				" right outer join  "+
				//" full outer join  "+
				" ( "+
				" SELECT TO_CHAR(m.mtc_70_register_date,'dd/MM/yyyy') AS mtc_70_register_date, "+
				" TO_CHAR(m.PRE_AUDIT_DATE,'dd/MM/yyyy') AS PRE_AUDIT_DATE, "+
				" m.ACCOUNTING_UNIT_ID, "+
				"    m.accounting_unit_office_id, "+
				"  m.CASHBOOK_YEAR, "+
				"   m.CASHBOOK_MONTH, ACCOUNT_HEAD_CODE ,"+
				"  m.bill_no "+
				"  FROM fas_bill_register_master m,fas_bill_register_transaction t "+
				"  WHERE m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "+
				" and m.accounting_unit_office_id=t.accounting_unit_office_id "+
				"  and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR "+
				"  and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH "+
				"  and m.BILL_NO=t.BILL_NO "+
				"  and m.ACCOUNTING_UNIT_ID     =  "+cmbAcc_UnitCode+
				"  AND m.accounting_unit_office_id=  "+cmbOffice_code+
				" AND m.CASHBOOK_YEAR            =  "+cashYear+
				" AND m.CASHBOOK_MONTH           =  "+cashMonth+
				" AND m.mtc_70_register_date    IS NOT NULL "+
				" AND m.PRE_AUDIT_DATE          IS NOT NULL "+
		qry1+
				"  AND m.STATUS                   ='L' "+
				" AND m.BILL_NO in "+
				 "   (SELECT memo.bill_no "+
				 "   FROM FAS_MEMO_OF_PAYMENT_TRN memo "+
				  "  WHERE memo.accounting_unit_id= m.ACCOUNTING_UNIT_ID "+
				 "   AND memo.bill_no             =m.BILL_NO "+
				 "   AND memo.cashbook_year       =m.CASHBOOK_YEAR "+
				 "   AND memo.cashbook_month      =m.CASHBOOK_MONTH "+
				 "   and memo.pvr_date is null and memo.pvr_no is null "+
				 "   ) "+
				" union all "+
				" SELECT TO_CHAR(mtc_70_register_date,'dd/MM/yyyy') AS mtc_70_register_date, "+
				" TO_CHAR(PRE_AUDIT_DATE,'dd/MM/yyyy') AS PRE_AUDIT_DATE, "+
				"    ACCOUNTING_UNIT_ID, "+
				"    accounting_unit_office_id, "+
				"   CASHBOOK_YEAR, "+
				"   CASHBOOK_MONTH,ACCOUNT_HEAD_CODE , "+
				"     bill_no "+
				"  FROM FAS_BILL_REGISTERNEW "+
				" WHERE ACCOUNTING_UNIT_ID     =  "+cmbAcc_UnitCode+
				" AND accounting_unit_office_id=  "+cmbOffice_code+
				" AND CASHBOOK_YEAR            =  "+cashYear+
				" AND CASHBOOK_MONTH           = "+cashMonth+
				" AND mtc_70_register_date    IS NOT NULL "+
				" and STATUS='L' "+qry2+
				" )b "+
				" on a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID "+
				" and a.ACCOUNTING_FOR_OFFICE_ID=b.accounting_unit_office_id "+
				" and a.CASHBOOK_YEAR=b.CASHBOOK_YEAR "+
				" and a.CASHBOOK_MONTH=b.CASHBOOK_MONTH "+
				" and a.BILL_NO=b.bill_no  and a.ACCOUNT_HEAD_CODE=b.ACCOUNT_HEAD_CODE  ";*/
			}
			else
			{
				ss = "select " + " (SELECT BILL_SUB_TYPE_DESC "
						+ " FROM FAS_BILL_SUB_TYPES "
						+ " WHERE BILL_MAJOR_TYPE_CODE= "
						+ majortype
						+ "  AND BILL_MINOR_TYPE_CODE  ="
						+ minortype
						+ " AND BILL_SUB_TYPE_CODE    = "
						+ subtype
						+ ") "
						+ " AS "
						+ "  sub_desc, "
						+ "  (SELECT BILL_MINOR_TYPE_DESC "
						+ "  FROM FAS_BILL_MINOR_TYPES_MST "
						+ "  WHERE BILL_MAJOR_TYPE_CODE= "
						+ majortype
						+ "  AND BILL_MINOR_TYPE_CODE  = "
						+ minortype
						+ "  ) "
						+ "AS "
						+ "  minor_desc, "
						+ "  (SELECT SANCTION_PROC_NO "
						+ "  FROM HRM_SANCTIONS_BILLS_LINK_MST "
						+ "  WHERE HRMS_SANCTION_ID=a.SANCTION_PROCEEDING_NO::numeric "
						+ "  ) "
						+ " AS "
						+ "  sanction_desc, a.SANCTION_PROCEEDING_DATE AS sanction_date ,"
						+

						"a.*,b.PRE_AUDIT_DATE as not_yes_treasury_date from "
						+ " (SELECT m.sanctioned_amount,m.SANCTION_PROCEEDING_NO,m.SANCTION_PROCEEDING_DATE, "
						+ "   TO_CHAR(M.BILL_DATE,'dd/MM/yyyy') AS BILL_DATE, "
						+ " M.BILL_NO, "
						+ " T.AMOUNT, "
						+ " t.PARTICULARS, "
						+ " T.ACCOUNT_HEAD_CODE,"
						+ " (SELECT S.ACCOUNT_HEAD_DESC "
						+ " FROM com_mst_account_heads s "
						+ " WHERE S.ACCOUNT_HEAD_CODE=T.ACCOUNT_HEAD_CODE "
						+ " )AS head_desc, "
						+ " t.cr_dr_indicator, "
						+ " t.sub_ledger_type_code, "
						+ " (SELECT d.sub_ledger_type_desc "
						+ " FROM COM_MST_SL_TYPES d "
						+ " WHERE d.sub_ledger_type_code=t.sub_ledger_type_code "
						+ " )AS type_desc, "
						+ " t.sub_ledger_code, "
						+ " (SELECT PAYEE_CODENAME FROM PAYEE_TYPE_CODE_NAME_VIEW "
						+ " WHERE PAYEE_TYPE= t.sub_ledger_type_code and PAYEE_CODE=t.sub_ledger_code "
						+ " )AS codedesc,m.ACCOUNTING_UNIT_ID,M.ACCOUNTING_FOR_OFFICE_ID,M.CASHBOOK_YEAR,M.CASHBOOK_MONTH "
						+ " FROM FAS_MEMO_OF_PAYMENT_MST M, "
						+ "   FAS_MEMO_OF_PAYMENT_TRN T "
						+ " WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID "
						+ " AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID "
						+ " AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR "
						+ " AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH "
						+ " AND M.BILL_NO                 =T.BILL_NO "
						+ " AND m.STATUS                  ='L' "
						+ " AND t.first_party             ='Y' "
						+ " AND t.PVR_NO                 IS NULL "
						+ " AND m.ACCOUNTING_UNIT_ID      =  "
						+ cmbAcc_UnitCode
						+ " AND M.ACCOUNTING_FOR_OFFICE_ID=  "
						+ cmbOffice_code
						+
						/*
						 * " AND M.CASHBOOK_YEAR           =  "+cashYear+
						 * " AND M.CASHBOOK_MONTH          ="+cashMonth
						 */
						" "
						+ sub_str
						+ ")a "
						+
						// " right outer join  "+
						" inner join  "
						+ " (  "
						+ " SELECT m.mtc_70_register_date, "
						+ " TO_CHAR(m.PRE_AUDIT_DATE,'dd/MM/yyyy') AS PRE_AUDIT_DATE, "
						+ " m.ACCOUNTING_UNIT_ID, "
						+ "    m.accounting_unit_office_id, "
						+ "  m.CASHBOOK_YEAR, "
						+ "   m.CASHBOOK_MONTH, T.ACCOUNT_HEAD_CODE, "
						+ "  m.bill_no "
						+ "  FROM fas_bill_register_master m,fas_bill_register_transaction t "
						+ "  WHERE m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "
						+ " and m.accounting_unit_office_id=t.accounting_unit_office_id "
						+ "  and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR "
						+ "  and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH "
						+ "  and m.BILL_NO=t.BILL_NO "
					/*	+ "  and m.ACCOUNTING_UNIT_ID     =  "
						+ cmbAcc_UnitCode
						+ "  AND m.accounting_unit_office_id=  "
						+ cmbOffice_code*/
						+
						/*
						 * " AND m.CASHBOOK_YEAR            =  "+cashYear+
						 * " AND m.CASHBOOK_MONTH           =  "+cashMonth+
						 */
						/*
						 * " AND m.mtc_70_register_date    IS NOT NULL "+
						 * " AND m.PRE_AUDIT_DATE          IS NOT NULL "+
						 */
						"   and M.BILL_APPROVED is not null AND m.PRE_AUDIT_DATE          IS NOT NULL and PRE_AUDIT_BY  IS NOT NULL  "
						+
						// " and	(( m.MTC70ENTRY ='Y' AND m.mtc_70_register_date    IS NOT NULL AND m.PRE_AUDIT_DATE          IS NOT NULL  ) or (  m.MTC70ENTRY <> 'Y' AND m.mtc_70_register_date    IS NULL AND m.PRE_AUDIT_DATE          IS  NULL  ) ) "+
						"	and	(( m.MTC70ENTRY ='Y' AND m.mtc_70_register_date    IS NOT NULL   ) or (  m.MTC70ENTRY <> 'Y' AND m.mtc_70_register_date    IS NULL  ) ) ";
				if(memotype==3 )	
		{ss=ss+" AND t.PAYABLE_TO               = '"+txtEmpId+"'";}
	else
	{	ss=ss+" ";}
	
			ss=ss+	"  AND m.STATUS                   ='L' "+
				"union all "+
				" SELECT mtc_70_register_date," +
				"   TO_CHAR(PRE_AUDIT_DATE,'dd/MM/yyyy') AS PRE_AUDIT_DATE, "+
				"ACCOUNTING_UNIT_ID,accounting_unit_office_id,CASHBOOK_YEAR, "+
				" CASHBOOK_MONTH, ACCOUNT_HEAD_CODE,bill_no "+
				" FROM FAS_BILL_REGISTERNEW "+
				" WHERE "
				+ ""
			/*	+ "ACCOUNTING_UNIT_ID     =  "+cmbAcc_UnitCode+
				" AND accounting_unit_office_id=  "+cmbOffice_code+*/
				/*" AND CASHBOOK_YEAR            =  "+cashYear+
				" AND CASHBOOK_MONTH           = "+cashMonth+*/
			//	" AND mtc_70_register_date    IS NOT NULL and PRE_AUDIT_DATE is not null " +
				
				/*" and"*/
				+ "	(( MTC70ENTRY ='Y' AND mtc_70_register_date    IS NOT NULL AND PRE_AUDIT_DATE          IS NOT NULL  ) or ( MTC70ENTRY <> 'Y' AND mtc_70_register_date    IS NULL  AND PRE_AUDIT_DATE          IS  NULL  ) ) ";
				if(memotype==3)	
				{ ss=ss+" and PAYEE_CODE="+txtEmpId;
					}
				else{
					ss=ss+" ";
				}
			ss=ss+" and STATUS='L' "+
			
					//
					" union all "+
				 
					 " SELECT m.mtc_70_register_date, "
					+ " TO_CHAR(m.PRE_AUDIT_DATE,'dd/MM/yyyy') AS PRE_AUDIT_DATE, "
					+ " m.ACCOUNTING_UNIT_ID, "
					+ "    m.accounting_unit_office_id, "
					+ "  m.CASHBOOK_YEAR, "
					+ "   m.CASHBOOK_MONTH, T.ACCOUNT_HEAD_CODE, "
					+ "  m.bill_no "
					+ "  FROM fas_bill_register_masternew m,fas_bill_register_transactionw t "
					+ "  WHERE m.ACCOUNTING_UNIT_ID=t.ACCOUNTING_UNIT_ID "
					+ " and m.accounting_unit_office_id=t.accounting_unit_office_id "
					+ "  and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR "
					+ "  and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH "
					+ "  and m.BILL_NO=t.BILL_NO "
				/*	+ "  and m.ACCOUNTING_UNIT_ID     =  "
					+ cmbAcc_UnitCode
					+ "  AND m.accounting_unit_office_id=  "
					+ cmbOffice_code*/
					+
					/*
					 * " AND m.CASHBOOK_YEAR            =  "+cashYear+
					 * " AND m.CASHBOOK_MONTH           =  "+cashMonth+
					 */
					/*
					 * " AND m.mtc_70_register_date    IS NOT NULL "+
					 * " AND m.PRE_AUDIT_DATE          IS NOT NULL "+
					 */
					"   and M.BILL_APPROVED is not null AND m.PRE_AUDIT_DATE          IS NOT NULL and PRE_AUDIT_BY  IS NOT NULL  "
					+
					// " and	(( m.MTC70ENTRY ='Y' AND m.mtc_70_register_date    IS NOT NULL AND m.PRE_AUDIT_DATE          IS NOT NULL  ) or (  m.MTC70ENTRY <> 'Y' AND m.mtc_70_register_date    IS NULL AND m.PRE_AUDIT_DATE          IS  NULL  ) ) "+
					"	and	(( m.MTC70ENTRY ='Y' AND m.mtc_70_register_date    IS NOT NULL   ) or (  m.MTC70ENTRY <> 'Y' AND m.mtc_70_register_date    IS NULL  ) ) ";
			if(memotype==3 )	
	{ss=ss+" AND t.PAYABLE_TO               = '"+txtEmpId+"'";}
else
{	ss=ss+" ";}

		ss=ss+	"  AND m.STATUS                   ='L' "+
			"union all "+
			" SELECT mtc_70_register_date," +
			"   TO_CHAR(PRE_AUDIT_DATE,'dd/MM/yyyy') AS PRE_AUDIT_DATE, "+
			"ACCOUNTING_UNIT_ID,accounting_unit_office_id,CASHBOOK_YEAR, "+
			" CASHBOOK_MONTH, ACCOUNT_HEAD_CODE,bill_no "+
			" FROM FAS_BILL_REGISTERNEW "+
			" WHERE "
			+ ""
			+ ""
		/*	+ "ACCOUNTING_UNIT_ID     =  "+cmbAcc_UnitCode+
			" AND accounting_unit_office_id=  "+cmbOffice_code+
			" AND CASHBOOK_YEAR            =  "+cashYear+
			" AND CASHBOOK_MONTH           = "+cashMonth+
		//	" AND mtc_70_register_date    IS NOT NULL and PRE_AUDIT_DATE is not null " +
			
			" and	"*/
			+ ""
			+ ""
			+ ""
			+ "(( MTC70ENTRY ='Y' AND mtc_70_register_date    IS NOT NULL AND PRE_AUDIT_DATE          IS NOT NULL  ) or ( MTC70ENTRY <> 'Y' AND mtc_70_register_date    IS NULL  AND PRE_AUDIT_DATE          IS  NULL  ) ) ";
			if(memotype==3)	
			{ ss=ss+" and PAYEE_CODE="+txtEmpId;
				}
			else{
				ss=ss+" ";
			}
		ss=ss+" and STATUS='L' "+
			
		
				" )b "+
				" on a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID "+
				" and a.ACCOUNTING_FOR_OFFICE_ID=b.accounting_unit_office_id "+
				" and a.CASHBOOK_YEAR=b.CASHBOOK_YEAR "+
				" and a.CASHBOOK_MONTH=b.CASHBOOK_MONTH "+
				" and a.BILL_NO=b.bill_no and a.ACCOUNT_HEAD_CODE=b.ACCOUNT_HEAD_CODE order by a.bill_no ";
		ss="";
		 ss="SELECT " +
				"  (SELECT BILL_SUB_TYPE_DESC " +
				"  FROM FAS_BILL_SUB_TYPES " +
				"  WHERE BILL_MAJOR_TYPE_CODE= b.BILL_MAJOR_TYPE " +
				"  AND BILL_MINOR_TYPE_CODE  =b.BILL_MINOR_TYPE_CODE " +
				"  AND BILL_SUB_TYPE_CODE    = b.BILL_SUB_TYPE_CODE " +
				"  ) AS sub_desc, " +
				"  (SELECT BILL_MINOR_TYPE_DESC " +
				"  FROM FAS_BILL_MINOR_TYPES_MST " +
				"  WHERE BILL_MAJOR_TYPE_CODE= b.BILL_MAJOR_TYPE " +
				"  AND BILL_MINOR_TYPE_CODE  = b.BILL_MINOR_TYPE_CODE " +
				"  ) AS minor_desc, " +
				"  (SELECT SANCTION_PROC_NO " +
				"  FROM HRM_SANCTIONS_BILLS_LINK_MST " +
				"  WHERE HRMS_SANCTION_ID=a.SANCTION_PROCEEDING_NO::numeric " +
				"  )                          AS sanction_desc, " +
				"  a.SANCTION_PROCEEDING_DATE AS sanction_date , " +
				"  a.*, " +
				"  b.PRE_AUDIT_DATE AS not_yes_treasury_date " +
				"FROM " +
				"  (SELECT m.sanctioned_amount, " +
				"    m.SANCTION_PROCEEDING_NO, " +
				"    t.amount AS tamount, " +
				"    m.SANCTION_PROCEEDING_DATE, " +
				"    TO_CHAR(M.BILL_DATE,'dd/MM/yyyy') AS BILL_DATE, " +
				"    M.BILL_NO, " +
				"    t.sl_no, " +
				"    T.AMOUNT, " +
				"    t.PARTICULARS, " +
				"    T.ACCOUNT_HEAD_CODE, " +
				"    (SELECT S.ACCOUNT_HEAD_DESC " +
				"    FROM com_mst_account_heads s " +
				"    WHERE S.ACCOUNT_HEAD_CODE=T.ACCOUNT_HEAD_CODE " +
				"    )AS head_desc, " +
				"    t.cr_dr_indicator, " +
				"    t.sub_ledger_type_code, " +
				"    (SELECT d.sub_ledger_type_desc " +
				"    FROM COM_MST_SL_TYPES d " +
				"    WHERE d.sub_ledger_type_code=t.sub_ledger_type_code " +
				"    )AS type_desc, " +
				"    t.sub_ledger_code, " +
				"    (SELECT PAYEE_CODENAME " +
				"    FROM PAYEE_TYPE_CODE_NAME_VIEW " +
				"    WHERE PAYEE_TYPE= t.sub_ledger_type_code " +
				"    AND PAYEE_CODE  =t.sub_ledger_code " +
				"    )AS codedesc, " +
				"    m.ACCOUNTING_UNIT_ID, " +
				"    M.ACCOUNTING_FOR_OFFICE_ID, " +
				"    M.CASHBOOK_YEAR, " +
				"    M.CASHBOOK_MONTH " +
				"  FROM FAS_MEMO_OF_PAYMENT_MST M, " +
				"    FAS_MEMO_OF_PAYMENT_TRN T " +
				"  WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID " +
				"  AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " +
				"  AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR " +
				"  AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH " +
				"  AND M.BILL_NO                 =T.BILL_NO " +
				"  AND m.STATUS                  ='L' " +
				"  AND t.first_party             ='Y' " +
				"  AND t.PVR_NO                 IS NULL " +
				"  AND t.Payment_Unit      =  " +cmbAcc_UnitCode+
				"  AND t.Payment_Office=  " +cmbOffice_code+
				"  )a " +
				"INNER JOIN " +
				"  (SELECT m.mtc_70_register_date, " +
				"    m.sanction_proc_no, " +
				"    m.total_sanctioned_amount, " +
				"    t.amount                               AS tamount, " +
				"    TO_CHAR(m.PRE_AUDIT_DATE,'dd/MM/yyyy') AS PRE_AUDIT_DATE, " +
				"    m.ACCOUNTING_UNIT_ID, " +
				"    m.accounting_unit_office_id, " +
				"    m.CASHBOOK_YEAR, " +
				"    m.CASHBOOK_MONTH, " +
				"    T.ACCOUNT_HEAD_CODE, " +
				"    m.bill_no, " +
				"    t.sl_no AS sl_no, " +
				"    t.BILL_MINOR_TYPE_CODE, " +
				"    t.BILL_MAJOR_TYPE, " +
				"    t.BILL_SUB_TYPE_CODE " +
				"  FROM fas_bill_register_master m, " +
				"    fas_bill_register_transaction t " +
				"  WHERE m.ACCOUNTING_UNIT_ID     =t.ACCOUNTING_UNIT_ID " +
				"  AND m.accounting_unit_office_id=t.accounting_unit_office_id " +
				"  AND m.CASHBOOK_YEAR            =t.CASHBOOK_YEAR " +
				"  AND m.CASHBOOK_MONTH           =t.CASHBOOK_MONTH " +
				"  AND m.BILL_NO                  =t.BILL_NO " +
		/*		"  AND m.ACCOUNTING_UNIT_ID       = " +cmbAcc_UnitCode+
				"  AND m.accounting_unit_office_id= " +cmbOffice_code+*/
				"  AND M.BILL_APPROVED           IS NOT NULL " +
				"  AND m.PRE_AUDIT_DATE          IS NOT NULL " +
				"  AND PRE_AUDIT_BY              IS NOT NULL " +
				"  AND (( m.MTC70ENTRY            ='Y' " +
				"  AND m.mtc_70_register_date    IS NOT NULL ) " +
				"  OR ( m.MTC70ENTRY             <> 'Y' " +
				"  AND m.mtc_70_register_date    IS NULL ) ) " ;
				if(memotype==3 )	
				{ss=ss+" AND t.PAYABLE_TO               = '"+txtEmpId+"'";}
			else
			{	ss=ss+" ";}

					ss=ss+	"  AND m.STATUS                   ='L' "+
				"  AND m.bill_date                < '01-Apr-15' " +
				"  UNION ALL " +
				"  SELECT mtc_70_register_date, " +
				"    sanction_proc_no, " +
				"    total_sanctioned_amount, " +
				"    0                                    AS tamount, " +
				"    TO_CHAR(PRE_AUDIT_DATE,'dd/MM/yyyy') AS PRE_AUDIT_DATE, " +
				"    ACCOUNTING_UNIT_ID, " +
				"    accounting_unit_office_id, " +
				"    CASHBOOK_YEAR, " +
				"    CASHBOOK_MONTH, " +
				"    ACCOUNT_HEAD_CODE, " +
				"    bill_no, " +
				"    0 AS sl_no, " +
				"    BILL_MINOR_TYPE_CODE, " +
				"    BILL_MAJOR_TYPE, " +
				"    BILL_SUB_TYPE_CODE " +
				"  FROM FAS_BILL_REGISTERNEW " +
				"  WHERE "
				+ ""
			/*	+ "ACCOUNTING_UNIT_ID     = " +cmbAcc_UnitCode+
				"  AND accounting_unit_office_id=" +cmbOffice_code+
				"  AND "*/
				+ ""
				+ ""
				+ "(( MTC70ENTRY            ='Y' " +
				"  AND mtc_70_register_date    IS NOT NULL " +
				"  AND PRE_AUDIT_DATE          IS NOT NULL ) " +
				"  OR ( MTC70ENTRY             <> 'Y' " +
				"  AND mtc_70_register_date    IS NULL " +
				"  AND PRE_AUDIT_DATE          IS NULL ) ) " ;
				if(memotype==3)	
				{ ss=ss+" and PAYEE_CODE="+txtEmpId;
					}
				else{
					ss=ss+" ";
				}
			ss=ss+" and STATUS='L' "+
				"  UNION ALL " +
				"  SELECT m.mtc_70_register_date, " +
				"    m.sanction_proc_no, " +
				"    m.total_sanctioned_amount, " +
				"    t.amount                               AS tamount, " +
				"    TO_CHAR(m.PRE_AUDIT_DATE,'dd/MM/yyyy') AS PRE_AUDIT_DATE, " +
				"    m.ACCOUNTING_UNIT_ID, " +
				"    m.accounting_unit_office_id, " +
				"    m.CASHBOOK_YEAR, " +
				"    m.CASHBOOK_MONTH, " +
				"    T.ACCOUNT_HEAD_CODE, " +
				"    m.bill_no, " +
				"    t.sl_no AS sl_no, " +
				"    t.BILL_MINOR_TYPE_CODE, " +
				"    t.BILL_MAJOR_TYPE, " +
				"    t.BILL_SUB_TYPE_CODE " +
				"  FROM fas_bill_register_masternew m, " +
				"    fas_bill_register_transactionw t " +
				"  WHERE m.ACCOUNTING_UNIT_ID     =t.ACCOUNTING_UNIT_ID " +
				"  AND m.accounting_unit_office_id=t.accounting_unit_office_id " +
				"  AND m.CASHBOOK_YEAR            =t.CASHBOOK_YEAR " +
				"  AND m.CASHBOOK_MONTH           =t.CASHBOOK_MONTH " +
				"  AND m.BILL_NO                  =t.BILL_NO " +
			/*	"  AND m.ACCOUNTING_UNIT_ID       =" +cmbAcc_UnitCode+
				"  AND m.accounting_unit_office_id= " +cmbOffice_code+*/
				"  AND M.BILL_APPROVED           IS NOT NULL " +
				"  AND m.PRE_AUDIT_DATE          IS NOT NULL " +
				"  AND PRE_AUDIT_BY              IS NOT NULL " +
				"  AND (( m.MTC70ENTRY            ='Y' " +
				"  AND m.mtc_70_register_date    IS NOT NULL ) " +
				"  OR ( m.MTC70ENTRY             <> 'Y' " +
				"  AND m.mtc_70_register_date    IS NULL ) ) " ;
				if(memotype==3 )	
				{ss=ss+" AND t.PAYABLE_TO               = '"+txtEmpId+"'";}
			else
			{	ss=ss+" ";}

					ss=ss+	"  AND m.STATUS                   ='L' "+
				"  UNION ALL " +
				"  SELECT mtc_70_register_date, " +
				"    sanction_proc_no, " +
				"    total_sanctioned_amount, " +
				"    0                                    AS tamount, " +
				"    TO_CHAR(PRE_AUDIT_DATE,'dd/MM/yyyy') AS PRE_AUDIT_DATE, " +
				"    ACCOUNTING_UNIT_ID, " +
				"    accounting_unit_office_id, " +
				"    CASHBOOK_YEAR, " +
				"    CASHBOOK_MONTH, " +
				"    ACCOUNT_HEAD_CODE, " +
				"    bill_no, " +
				"    0 AS sl_no, " +
				"    BILL_MINOR_TYPE_CODE, " +
				"    BILL_MAJOR_TYPE, " +
				"    BILL_SUB_TYPE_CODE " +
				"  FROM FAS_BILL_REGISTERNEW " +
				"  WHERE "
				+ ""
			/*	+ "ACCOUNTING_UNIT_ID     = " +cmbAcc_UnitCode+
				"  AND accounting_unit_office_id= " +cmbOffice_code +
				"  AND "*/
				+ ""
				+ ""
				+ "(( MTC70ENTRY            ='Y' " +
				"  AND mtc_70_register_date    IS NOT NULL " +
				"  AND PRE_AUDIT_DATE          IS NOT NULL ) " +
				"  OR ( MTC70ENTRY             <> 'Y' " +
				"  AND mtc_70_register_date    IS NULL " +
				"  AND PRE_AUDIT_DATE          IS NULL ) ) " ;
				if(memotype==3)	
				{ ss=ss+" and PAYEE_CODE="+txtEmpId;
					}
				else{
					ss=ss+" ";
				}
			ss=ss+" and STATUS='L' "+
				"  )b " +
				"ON a.ACCOUNTING_UNIT_ID       =b.ACCOUNTING_UNIT_ID " +
				"AND a.ACCOUNTING_FOR_OFFICE_ID=b.accounting_unit_office_id " +
				"AND a.CASHBOOK_YEAR           =b.CASHBOOK_YEAR " +
				"AND a.CASHBOOK_MONTH          =b.CASHBOOK_MONTH " +
				"AND a.BILL_NO                 =b.bill_no " +
				"AND a.SANCTION_PROCEEDING_NO  =b.sanction_proc_no " +
				"AND a.sanctioned_amount       =b.total_sanctioned_amount " +
				"AND a.ACCOUNT_HEAD_CODE       =b.ACCOUNT_HEAD_CODE " +
				"AND a.sl_no                   =b.sl_no " ;
		if(memotype==7){
				ss+="AND b.BILL_MAJOR_TYPE         =2 " +
				"AND b.BILL_MINOR_TYPE_CODE    =1 " +
				"AND b.BILL_SUB_TYPE_CODE     IN (1,6) " +
				"ORDER BY a.bill_no" ;
		}
			}
		
			System.out.println("load q::"+ss);
			
		
			try {
				ps = con.prepareStatement(ss);
				rs = ps.executeQuery();

				while (rs.next()) {
					
					xml = xml + "<mtcdate>" + rs.getString("not_yes_treasury_date")+ "</mtcdate>";
					xml = xml + "<sanctionedamount>" + rs.getInt("sanctioned_amount")+ "</sanctionedamount>";
					xml = xml + "<billno>" + rs.getInt("BILL_NO")+ "</billno>";
					xml = xml + "<billdate>" + rs.getString("BILL_DATE")+ "</billdate>";
					xml = xml + "<passamount>" + rs.getInt("AMOUNT")+ "</passamount>";
					xml = xml + "<achead>" + rs.getInt("ACCOUNT_HEAD_CODE")+ "</achead>";
					xml = xml + "<head_desc>" + rs.getString("head_desc")+ "</head_desc>";
					xml = xml + "<indicator>" + rs.getString("cr_dr_indicator")+ "</indicator>";
					xml = xml + "<typecode>" + rs.getString("sub_ledger_type_code")+ "</typecode>";
					xml = xml + "<type_desc>" + rs.getString("type_desc")+ "</type_desc>";
					xml = xml + "<ledgercode>" + rs.getString("sub_ledger_code")+ "</ledgercode>";
					xml = xml + "<codedesc>" + rs.getString("codedesc")+ "</codedesc>";
					xml = xml + "<particulars>" + rs.getString("PARTICULARS")+ "</particulars>";
						xml = xml + "<minor_desc>" + rs.getString("minor_desc")+ "</minor_desc>";
						xml = xml + "<sanction_desc>" + rs.getString("sanction_desc")+ "</sanction_desc>";
						xml = xml + "<sub_desc>" + rs.getString("sub_desc")+ "</sub_desc>";
						xml = xml + "<sanction_date>" + rs.getDate("sanction_date")+ "</sanction_date>";
						
					y++;
				}
				if (y != 0) {
					xml = xml + "<flag>success</flag>";
				} else
					xml = xml + "<flag>failure</flag>";

				ps.close();
				rs.close();
			} catch (Exception e) {
				System.out.println("catch..HERE.in load supplier." + e);
				xml = xml + "<flag>failure</flag>";
			}
				
			xml = xml + "</response>";
			// System.out.println(xml);
			out.println(xml);
		
		}
		else if(command.equalsIgnoreCase("loadLicCombo")){
		/*	int y = 0;
			int cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			xml = "<response><command>" + command + "</command>";
			try {
				String query="select distinct AC_OPERATIONAL_MODE_ID,BANK_AC_NO from FAS_MST_BANK_BALANCE where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and AC_OPERATIONAL_MODE_ID='OPR' order by BANK_AC_NO";
				ps = con.prepareStatement(query);
				
				rs = ps.executeQuery();
							
				
				while (rs.next()) {
					xml = xml + "<bank_ac_no>" + rs.getLong("BANK_AC_NO")+ "</bank_ac_no>";
					xml = xml + "<ac_operational_mode_id>" + rs.getString("AC_OPERATIONAL_MODE_ID")+ "</ac_operational_mode_id>";
						
					y++;
				}
				if (y != 0) {
					xml = xml + "<flag>success</flag>";
				} else
					xml = xml + "<flag>failure</flag>";

				ps.close();
				rs.close();
			} catch (Exception e) {
				System.out.println("catch..HERE.in load supplier." + e);
				xml = xml + "<flag>failure</flag>";
			}
				
			xml = xml + "</response>";
			 System.out.println(xml);
			out.println(xml);*/
			
			

            try {
                System.out.println("inside 10 here projects");
                int y = 0;
    		
    			
    			xml = "<response><command>" + command + "</command>";
               ps = con.prepareStatement("select dep.OTHER_DEPT_NAME || '-' || off.OTHER_DEPT_OFFICE_NAME as OTHER_DEPT_OFF_NAME,off.OTHER_DEPT_OFFICE_ALIAS_ID as OTHER_DEPT_OFFICE_ALIAS_ID from HRM_MST_OTHER_DEPTS dep" +
                ",HRM_MST_OTHER_DEPT_OFFICES off where dep.OTHER_DEPT_ID=off.OTHER_DEPT_ID and off.OTHER_DEPT_ID='LIC' ORDER BY dep.OTHER_DEPT_NAME");
             rs = ps.executeQuery();

             while (rs.next()) {

                 xml =
xml + "<cid>" + rs.getInt("OTHER_DEPT_OFFICE_ALIAS_ID") + "</cid><cname>" +
rs.getString("OTHER_DEPT_OFF_NAME") + "</cname>";
                    y++;
                }
                if (y != 0) {
                    xml = xml + "<flag>success</flag>";
                } else
                    xml = xml + "<flag>failure</flag>";//Lakshmi 29oct13

                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load supplier." + e);
                xml = xml + "<flag>failure</flag>";
            }
        	xml = xml + "</response>";
			 System.out.println(xml);
			out.println(xml);
		}
		
		
		
		else if(command.equalsIgnoreCase("getBankIDCombo")){
			int y = 0;
			int cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			xml = "<response><command>" + command + "</command>";
			try {
				String query="select distinct AC_OPERATIONAL_MODE_ID,BANK_AC_NO from FAS_MST_BANK_BALANCE where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and AC_OPERATIONAL_MODE_ID='OPR' order by BANK_AC_NO";
				ps = con.prepareStatement(query);
				
				rs = ps.executeQuery();
							
				
				while (rs.next()) {
					xml = xml + "<bank_ac_no>" + rs.getLong("BANK_AC_NO")+ "</bank_ac_no>";
					xml = xml + "<ac_operational_mode_id>" + rs.getString("AC_OPERATIONAL_MODE_ID")+ "</ac_operational_mode_id>";
						
					y++;
				}
				if (y != 0) {
					xml = xml + "<flag>success</flag>";
				} else
					xml = xml + "<flag>failure</flag>";

				ps.close();
				rs.close();
			} catch (Exception e) {
				System.out.println("catch..HERE.in load supplier." + e);
				xml = xml + "<flag>failure</flag>";
			}
				
			xml = xml + "</response>";
			 System.out.println(xml);
			out.println(xml);
		}
		
			else if (command.equalsIgnoreCase("getcode")) {
			
			int y = 0;
			xml = "<response><command>" + command + "</command>";
			
			int memoType = Integer.parseInt(request.getParameter("memotype"));
		
			try {
				String query="select ms.payee_type_code,ms.payee_type_desc from FAS_CHEQUEMEMO_PAYEE_TYPES_MST fs,FAS_PAYEE_TYPES_MST ms " +
						"where fs.cheque_memo_type_code="+memoType+" and fs.payee_type_code=ms.payee_type_code and fs.status='L'";
				System.out.println("query:::"+query);
				ps = con.prepareStatement(query);
				
				rs = ps.executeQuery();
							
				
				while (rs.next()) {
					xml = xml + "<paycode>" + rs.getInt("payee_type_code")+ "</paycode>";
					xml = xml + "<paydesc>" + rs.getString("payee_type_desc")+ "</paydesc>";
						
					y++;
				}
				if (y != 0) {
					xml = xml + "<flag>success</flag>";
				} else
					xml = xml + "<flag>failure</flag>";

				ps.close();
				rs.close();
			} catch (Exception e) {
				System.out.println("catch..HERE.in load supplier." + e);
				xml = xml + "<flag>failure</flag>";
			}
				
			xml = xml + "</response>";
			out.println(xml);
			 System.out.println(xml);
		}
else if(command.equalsIgnoreCase("load")) {
	 xml="<response><command>load</command>";  
	 String strEmpName=request.getParameter("empid");
	 
   try{
              		                  		                          
       
       
       ps=con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
       ps.setInt(1,Integer.parseInt(strEmpName));
       rs=ps.executeQuery();
       if(!rs.next()) 
       {
           xml=xml+"<flag>failure</flag>";
       }
       else
         {
               ps=con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_EMP_CURRENT_POSTING WHERE EMPLOYEE_ID=?");
               ps.setInt(1,Integer.parseInt(strEmpName));
               rs=ps.executeQuery();
               if(!rs.next()) {
                   xml=xml+"<flag>failure1</flag>";
               }
               else {
               	rs.close();
               ps.close();
              int designationId=0; 
               System.out.println("emp id" + strEmpName);
               
               String sql="select  A.EMPLOYEE_NAME ||coalesce(a.EMPLOYEE_INITIAL,null,' ','.'||a.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME , b.designation_id from hrm_mst_employees a,hrm_emp_current_posting b \n"+
               	" where b.employee_id = a.employee_id and a.employee_id=? ";
               
              ps=con.prepareStatement(sql);
             ps.setInt(1,Integer.parseInt(strEmpName));
              rs=ps.executeQuery();
              if(rs.next())
              {
           	   xml=xml+"<flag>success</flag>";
           	   xml=xml+"<empname>"+rs.getString("EMPLOYEE_NAME")+"</empname>"; 
           	   designationId=rs.getInt("designation_id");
           	   System.out.println("Designation" + designationId);
           	   ps1=con.prepareStatement("select DESIGNATION from HRM_MST_DESIGNATIONS where DESIGNATION_ID=?");
                     ps1.setInt(1,designationId); 
                     rs1=ps1.executeQuery();
                     rs1.next();
                  xml=xml+"<designation>"+rs1.getString("DESIGNATION")+"</designation>";      
              }

               }
         }
      
                  
       }

catch(SQLException e) {
   xml=xml+"<flag>failure</flag>";
   e.printStackTrace();
}
xml=xml+"</response>";
// System.out.println(xml);
out.println(xml);
}         

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;

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
			System.out.println("Exception in connection...." + e);
		}
		ResultSet rs = null, rs1 = null, rs2 = null, rs4 = null;
		CallableStatement cs = null,cs_set;
		PreparedStatement ps = null, ps1 = null, ps2 = null;
		String xml = "";

		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");

		String command;
		command = request.getParameter("command");
		String pro_qry="";
		long l = System.currentTimeMillis();
		java.sql.Timestamp ts = new java.sql.Timestamp(l);
		//System.out.println("got");
		System.out.println("command" + command);

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
		String updatedby = (String) session.getAttribute("UserId");

		if (command.equalsIgnoreCase("Add")) {
			
			int pay_code=0;
			String sub_q = "",sub_main="";
			int memoType = Integer.parseInt(request.getParameter("memotype"));
			
			System.out.println("*******************************"+memoType+"**********************************");
			String payeeType = request.getParameter("payeetype");
		try{
			String pay_check="SELECT ms.payee_type_code, " +
			"  fs.cheque_memo_type_code, " +
			"  ms.payee_type_desc " +
			" FROM FAS_CHEQUEMEMO_PAYEE_TYPES_MST fs, " +
			"  FAS_PAYEE_TYPES_MST MS " +
			 "WHERE fs.cheque_memo_type_code="+memoType+" AND  fs.payee_type_code =ms.payee_type_code and fs.status='L'";
			PreparedStatement ps_py=con.prepareStatement(pay_check);
			ResultSet rs_py=ps_py.executeQuery();
			if(rs_py.next()){
				pay_code=rs_py.getInt("payee_type_code");
			}
		}catch(Exception e){
			e.printStackTrace();
		}
		if(pay_code!=Integer.parseInt(payeeType)){
			 sendMessage(response, "Not Suffcient Payee Code , ","ok", "Cheque_Memo.jsp");
		}if(pay_code==Integer.parseInt(payeeType)){
		
			if(memoType==2 || memoType==1 || memoType==7 )
			
		{
			//System.out.println("hai this is add::");
			int chequeMemoNo = 0;
			int serialNo = 1;
			 int paymentno=0,pvupdate=0,hrm_billupdate=0,pvinRegister=0;
			int last=0,final_upd=0;
			int unitId = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int officeId = Integer.parseInt(request.getParameter("cmbOffice_code"));
		//	int memoType = Integer.parseInt(request.getParameter("memotype"));
			//Add 20Nov13 Lakshmi insert in Update qry last
			int check_memo_Year1 = Integer.parseInt(request.getParameter("check_memo_Year"));
			int check_memo_Month1 = Integer.parseInt(request.getParameter("check_memo_Month"));
			
			
		//	String memoDate = request.getParameter("memodate");
			String vocherDate = request.getParameter("vochardate");
		//	long bankNo = Long.parseLong(request.getParameter("txtBankAccountNo"));
			String bankNo=request.getParameter("txtBankAccountNo");
			int operationHeadCode = Integer.parseInt(request.getParameter("txtCash_Acc_code"));
			int bankId =Integer.parseInt( request.getParameter("txtBankID"));
			int branchId = Integer.parseInt(request.getParameter("txtBranchID"));
		
			//txtEmpId
			
			String chequeNo = request.getParameter("txtCheque_DD_NO");
			String chequeDate = request.getParameter("txtCheque_DD_date");
			
			String particulars = request.getParameter("particulars");
		
			String assignVoucherDate="";

			String[] cashMonthYear = vocherDate.split("/");
			int cashMonth = Integer.parseInt(cashMonthYear[1]);
			int cashYear = Integer.parseInt(cashMonthYear[2]);
			//String bill_No_new[] = request.getParameterValues("billno");
			int count=0;
			try{
				ps = con.prepareStatement("select count(*) as chequememono from FAS_CHEQUE_MEMO_MST where ACCOUNTING_UNIT_ID=" +unitId+
						" and ACCOUNTING_FOR_OFFICE_ID="+officeId+" and CASHBOOK_MONTH="+cashMonth+" and CASHBOOK_YEAR="+cashYear+" and STATUS='L' and CHEQUE_NO='"+chequeNo+"'"  );
				rs = ps.executeQuery();
				if (rs.next()) {
					count = rs.getInt("chequememono");
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			//if(count==0)
			//{
			try {
				ps = con.prepareStatement("select COALESCE(MAX(CHEQUE_MEMO_NO),0) as chequememono from FAS_CHEQUE_MEMO_MST where ACCOUNTING_UNIT_ID=" +unitId+
						" and ACCOUNTING_FOR_OFFICE_ID="+officeId+" and CASHBOOK_MONTH="+cashMonth+" and CASHBOOK_YEAR="+cashYear);
				rs = ps.executeQuery();
				if (rs.next()) {
					chequeMemoNo = rs.getInt("chequememono");
				}
			} catch (Exception e) {
				System.out.println(e);
			}

			
			chequeMemoNo = chequeMemoNo + 1;
			System.out.println("chequeMemoNo >>> "+chequeMemoNo);
			int bno=0;float passamt=0;
			String bill_No_new[] = request.getParameterValues("billno");
			 String YourselfChk[]=request.getParameterValues("YourselfChk");
			 String pass_amount[] = request.getParameterValues("passamt");
		//	 String verify_select_status[] = request.getParameterValues("verify_select_status");
			 
			float txtAmount2 =0;int payeeCode =0;
	
				 txtAmount2 = Float.parseFloat(request.getParameter("txtAmount"));
				
					BigDecimal txtAmount = new BigDecimal(txtAmount2);

				 
				 
				// payeeCode=Integer.parseInt(request.getParameter("txtEmpId"));
				 
				 //payeeCode = 0;
			
			for(int ii=0;ii<bill_No_new.length;ii++)
			{
				bno=Integer.parseInt(bill_No_new[ii]);
			}
			
			

			try {
				con.clearWarnings();
				con.setAutoCommit(false);

				ps1 = con.prepareStatement("insert into FAS_CHEQUE_MEMO_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,CHEQUE_MEMO_TYPE_CODE,CHEQUE_MEMO_NO,VOUCHER_DATE,BANK_AC_NO,ACCOUNT_HEAD_CODE,PAYEE_TYPE_CODE,PAYEE_CODE,BANK_ID,BRANCH_ID,CHEQUE_NO,CHEQUE_DATE,CHEQUE_AMOUNT,PARTICULARS,UPDATED_BY_USERID,UPDATED_DATE,CHEQUE_MEMO_DATE,STATUS)" +
						" values(?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,to_date(?,'dd-mm-yyyy'),?)");
				  ps1.setInt(1, unitId);
				  ps1.setInt(2, officeId);
				  ps1.setInt(3, cashYear);
				  ps1.setInt(4, cashMonth);
				  ps1.setInt(5,memoType);
				  ps1.setInt(6,chequeMemoNo);
			//	  ps.setString(7, memoDate);	pq.setDate(2,PaymentDate);
				  ps1.setString(7, vocherDate);
				  ps1.setLong(8, Long.parseLong(bankNo));
				  ps1.setInt(9, operationHeadCode);
				  ps1.setString(10, payeeType); 
				 
					  ps1.setInt(11,0);
				 
				  ps1.setInt(12,bankId);
				  ps1.setInt(13,branchId);
				  ps1.setString(14,chequeNo);
				  ps1.setString(15, chequeDate);
				  ps1.setBigDecimal(16, txtAmount);
				  ps1.setString(17, particulars);
				  ps1.setString(18, updatedby); 
				  ps1.setTimestamp(19, ts);
				  ps1.setString(20, vocherDate);
				  ps1.setString(21,"L");
				 int jj= ps1.executeUpdate();
				  System.out.println("............ >>>> "+jj);
				  
			
				String bill_No[] = request.getParameterValues("billno");
				String bill_Date[] = request.getParameterValues("billdate");
				
				String headcode[] = request.getParameterValues("headcode");
				
				String sl_Type[] = request.getParameterValues("sltype");
				String sl_Code[] = request.getParameterValues("slcode");
				 String verify_select_status[] = request.getParameterValues("verify_select_status");        
				String dr_Amount[] = request.getParameterValues("dramt");
				String remarks12[] = request.getParameterValues("remarks");
				String crDRIndicator[] = request.getParameterValues("indicator");
				System.out.println("length og headcode >>> "+headcode.length);
				int billNo = 0, assignedVocherNo = 0, acHeadCode = 0, slType = 0,slCode = 0;
				float passAmount = 0, drAmount = 0;

				for (int i = 0; i < bill_No.length; i++) {
					if(verify_select_status[i].equalsIgnoreCase("CHECKED")){
						
					ps2 = con.prepareStatement("insert into FAS_CHEQUE_MEMO_TRN(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,CHEQUE_MEMO_NO,SL_NO,BILL_NO,BILL_DATE,PASS_ORDER_AMOUNT,VOUCHER_NO,VOUCHER_DATE,ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,AMOUNT,PARTICULARS,UPDATED_BY_USERID,UPDATED_DATE,CR_DR_INDICATOR) values(?,?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,?)");

					
					try {
						billNo = Integer.parseInt(bill_No[i]);//
					} catch (Exception e) {
						System.out.println(e);
					}
					System.out.println("CHECKED    >>> "+billNo);
					

					try {
						acHeadCode = Integer.parseInt(headcode[i]);
					} catch (Exception e) {
						System.out.println(e);
					}

					try {
						slType = Integer.parseInt(sl_Type[i]);
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						slCode = Integer.parseInt(sl_Code[i]);
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						passAmount = Float.parseFloat(pass_amount[i]);
					} catch (Exception e) {
						System.out.println(e);
					}

					try {
						drAmount = Float.parseFloat(dr_Amount[i]);
					} catch (Exception e) {
						System.out.println(e);
					}

//					BigDecimal bdPassAmount = new BigDecimal(passAmount);
//					BigDecimal bdDrAmount = new BigDecimal(drAmount);

					
					String billDate = bill_Date[i];
					
					String remarks = remarks12[i];
					String indicator=crDRIndicator[i];
					ps2.setInt(1, unitId);
					ps2.setInt(2, officeId);
					ps2.setInt(3, cashYear);
					ps2.setInt(4, cashMonth);
					ps2.setInt(5, chequeMemoNo);
					ps2.setInt(6, serialNo);
					ps2.setInt(7, billNo);
					ps2.setString(8, billDate);
					ps2.setFloat(9, passAmount);
					ps2.setInt(10, assignedVocherNo);
					ps2.setString(11, assignVoucherDate);
					ps2.setInt(12, acHeadCode);
					ps2.setInt(13, slType);
					ps2.setInt(14, slCode);
					ps2.setFloat(15, drAmount);
					ps2.setString(16, remarks);
					ps2.setString(17, updatedby);
					ps2.setTimestamp(18, ts);
					ps2.setString(19, indicator);
					last=ps2.executeUpdate();
					serialNo++;
				}
				}
				System.out.println("last >>>>> "+last);
				if(last>0)
                {
             	   int txtJournal_type_code=0,txtchallan_No=0,txtNo_Acq_rolls=0;
             	   int bill_no_Recs=0;
             	   String mode="A",module="BPF",paid="";
             	  //String vocherDate = request.getParameter("vochardate");
             	  String[] sd = request.getParameter("vochardate").split("/");
                Calendar  c =
         new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                               Integer.parseInt(sd[0]));
                  java.util.Date d = c.getTime();
                 Date PaymentDate = new Date(d.getTime());
                  System.out.println("paymentno >>>>  " + paymentno);
             	   
             	   String radPart_Amt = "",parti="Automatic Cheque Memo to Payments";
             	 // cs =con.prepareCall("{call FAS_PAYMENT_MASTER_PROC_1(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
             	  //pro_qry="{call FAS_PAYMENT_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
              	 pro_qry="call FAS_PAYMENT_MASTER_PROC(?::numeric,?::numeric,?,?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?,?::numeric,?,?,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?,?,?,?,?::numeric)";

             	   // pro_qry="{call FAS_PAYMENT_MASTER_PROC_CP(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
             	   cs_set=con.prepareCall(pro_qry);	
             	   System.out.println("cs_set >>>>> "+cs_set);
             	  cs_set.setInt(1, unitId);
             	 cs_set.setInt(2, officeId);
             	cs_set.setDate(3,PaymentDate);
             	cs_set.setInt(4,cashYear);
             	cs_set.setInt(5,cashMonth);
             	cs_set.setString(6, "B");
             	cs_set.setInt(7,paymentno);
             	cs_set.setInt(8,operationHeadCode); System.out.println("operationHeadCode "+operationHeadCode);            		               
             	cs_set.setInt(9, bankId);
             	cs_set.setInt(10, branchId);
             	cs_set.setLong(11, Long.parseLong(bankNo)); System.out.println("Long.parseLong(bankNo) "+Long.parseLong(bankNo));            		               
             	
             	cs_set.setString(12,"6");//sltype
             //	String payeeCode=bankNo;
             	cs_set.setLong(13,Long.parseLong(bankNo));
             	cs_set.setString(14, "CR");
             	cs_set.setString(15, particulars);
             	cs_set.setString(16, "C");
             		                 // cs.setInt(17,txtPay_Vou_No);
             		                 //cs.setDate(18,txtPay_Vou_date);
             	cs_set.setInt(17, txtJournal_type_code);
             	cs_set.setString(18, particulars);
             	cs_set.setString(19, radPart_Amt);
             	cs_set.setBigDecimal(20, txtAmount); 
             	cs_set.setInt(21, txtchallan_No);
             	cs_set.setInt(22,bill_No.length);
             	System.out.println(" bill_No.length >> "+bill_No.length);
             	cs_set.setBigDecimal(23,txtAmount);
             	cs_set.setInt(24, txtNo_Acq_rolls);
             	cs_set.setString(25,mode);
             	cs_set.setString(26, module);
             	cs_set.setString(27, "insert");
             	cs_set.registerOutParameter(7, java.sql.Types.NUMERIC);
             	cs_set.registerOutParameter(28, java.sql.Types.NUMERIC);
             	
             	cs_set.setInt(28, 0);
             	cs_set.setString(29, updatedby);
             	cs_set.setTimestamp(30, ts);
             	cs_set.setInt(31, 0);
             		                 System.out.println("b4 exe ");
             		                boolean hh=cs_set.execute();
             		                System.out.println("hh >>> "+hh);
             		                 //paymentno =cs_set.getInt(7);  
             		               // System.out.println("paymentno "+paymentno);
             		               paymentno =cs_set.getBigDecimal(7).intValue();
           		                 
             		                System.out.println("paymentno result "+paymentno);
             		              
             		                 //int pay_mas = cs_set.getInt(28);
             		               int pay_mas = cs_set.getBigDecimal(28).intValue(); 
             		                 System.out.println("pay_mas== "+pay_mas);
             		                 if(pay_mas==0)
             		                 {
             		                	// System.out.println("ffff");
             		                	 
             		                	 String bill_no_Rec[]=request.getParameterValues("billno");
                                          bill_no_Recs=bill_no_Rec.length;//Integer.parseInt(No_TRN_Rec);
                                           System.out.println(" bill_no_Recs :"+bill_no_Recs);
                                           
                                           
             	                          String Grid_bill_date[]=request.getParameterValues("billdate");
             	                          String Grid_pass_amount[]=request.getParameterValues("passamt");
             	                          String Grid_head_code[]=request.getParameterValues("headcode");
             	                          String Grid_sl_type[]=request.getParameterValues("sltype");
             	                          String Grid_SL_code[]=request.getParameterValues("slcode");                          
             	                          String Grid_crdrindicator[]=request.getParameterValues("indicator");
             	                          String Grid_dr_amount[]=request.getParameterValues("dramt");                         
             	                          String Trn_remarks12[]=request.getParameterValues("remarks"); 
             	                         String verify_select_status1[] = request.getParameterValues("verify_select_status");  
             	                          String grid_cheque_no_dates[]=request.getParameterValues("cheque_no_dates");                          
             	                          String grid_bill_no[]=request.getParameterValues("billno");
             		                	 
             		                	 String sql =
             		                        "insert into FAS_PAYMENT_TRANSACTION(ACCOUNTING_UNIT_ID, " +
             		                		//  "insert into FAS_PAYMENT_TRANSACTION(ACCOUNTING_UNIT_ID, " +
             		                         "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
             		                         "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, BILL_NO,BILL_TYPE,AGREEMENT_NO,AGREEMENT_DATE," +
             		                         "BILL_DATE,BANK_ID,BRANCH_ID,ACCOUNT_NO,CHEQUE_OR_DD,CHEQUE_DD_NO, CHEQUE_DD_DATE, " +
             		                         "AMOUNT, PARTICULARS,PAYABLE_VOUCHER_NO,PAYABLE_VOUCHER_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,REFERENCE_NO) " +
             		                         "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


             		                     int SL_NO = 1, txtAcc_HeadCode = 0, cmbSL_Code =
             		                         0, cmbSL_type = 0, txtPay_Vou_No = 0;
             		                     Date txtBill_Date = null, txtAgree_Date =null, txtCheque_DD_date = null,
             		                     txtPay_Vou_date = null,billDate=null,chDate=null,pay_voudate=null;
             		                     double txtsub_Amount = 0;
             		                     String rad_sub_CR_DR = "", txtBill_no = "", txtBill_Type =
             		                         "", txtAgree_No = "", txtParticular = "",cdate="";
             		                     String txtCheque_DD = "", txtCheque_DD_NO =
             		                         "", txtsub_Paid_to = "", txtReference_No = "";
             		                     
             		                     ps = con.prepareStatement(sql);

             		                     
             		                     for (int k = 0; k <bill_no_Recs; k++) {
             		                    	if(verify_select_status1[k].equalsIgnoreCase("CHECKED")) {
             		                    		System.out
														.println("Integer.parseInt(grid_bill_no[k])  "+Integer.parseInt(grid_bill_no[k]));
             		                    	 ps.setInt(1, unitId);
             		                         ps.setInt(2, officeId);
             		                         ps.setInt(3, cashYear);
                                             ps.setInt(4, cashMonth);
             		                         ps.setInt(5,paymentno);
             		                         ps.setInt(6, SL_NO);
             		                         ps.setInt(7,Integer.parseInt(Grid_head_code[k]));
             		                         ps.setString(8, "DR");
             		                         ps.setInt(9,Integer.parseInt(Grid_sl_type[k]));
                                             ps.setInt(10,Integer.parseInt(Grid_SL_code[k]));
                                             ps.setInt(11,Integer.parseInt(grid_bill_no[k]));
             		                         ps.setString(12, txtBill_Type);
             		                         ps.setString(13, txtAgree_No);
             		                         ps.setDate(14, txtAgree_Date);
             		                        try
                                              { if(!Grid_bill_date[k].equalsIgnoreCase(""))
             		                                 {
             				                              String[] sd1=Grid_bill_date[k].split("/");
             				                                Calendar c1=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
             				                                 java.util.Date d1=c1.getTime();
             				                                 billDate=new Date(d1.getTime());
             				                                 ps.setDate(15,billDate);
             				                         }
             		                                 else
             		                                 { 	 ps.setNull(15,java.sql.Types.DATE);
             		                                 }
             	    	                    	 }
                                              catch(Exception e) {
                                                  	 System.out.println(e);
                                              }
                                             
                                              ps.setInt(16,bankId);
                                              ps.setInt(17,branchId);
                                              ps.setLong(18,Long.parseLong(bankNo));
                                        
             		                         ps.setString(19,"C");
             		                       
             		                         ps.setString(20,chequeNo);
             		                         
             		                        try
                                            { 
             		                        	
           				                              String[] sd11=chequeDate.split("/");
           				                                Calendar c11=new GregorianCalendar(Integer.parseInt(sd11[2]),Integer.parseInt(sd11[1])-1,Integer.parseInt(sd11[0]));
           				                                 java.util.Date d11=c11.getTime();
           				                                 chDate=new Date(d11.getTime());
           				                                 ps.setDate(21,chDate);
           				                         
           	    	                    	 }
                                            catch(Exception e) {
                                                	 System.out.println(e);
                                            }
             		                         
//             		                        System.out.println("chequeDate:"+chequeDate);
//             		     				     ps.setString(20, chequeDate);
             		     				  
             		                         ps.setDouble(22,Double.parseDouble(Grid_pass_amount[k]));
             		                         ps.setString(23,Trn_remarks12[k]);
             		                        
             		                        ps.setInt(24,chequeMemoNo);
             		                       try
                                           { 
            		                        	
          				                              String[] sd111=vocherDate.split("/");
          				                                Calendar c111=new GregorianCalendar(Integer.parseInt(sd111[2]),Integer.parseInt(sd111[1])-1,Integer.parseInt(sd111[0]));
          				                                 java.util.Date d111=c111.getTime();
          				                                 pay_voudate=new Date(d111.getTime());
          				                                 ps.setDate(25,pay_voudate);
          				                         
          	    	                    	 }
                                           catch(Exception e) {
                                               	 System.out.println(e);
                                           }
             		                      
             		                         ps.setString(26, updatedby);
             		                      
             		                         ps.setTimestamp(27, ts);
             		                       
             		                         ps.setString(28, txtReference_No);

             		                         SL_NO++;
             		                        final_upd=ps.executeUpdate();
             		                       System.out.println("final_upd:"+final_upd);
             		                     }
             		                     }
             		                 }
                
             		                 else
             		                 {
             		                	 try{con.rollback();}
             		                	 catch(SQLException sqle){
             		                		 System.out.println("exception in rollback "+sqle);
             		                		 }
                                          
             		               	 sendMessage(response, "Insertion Failed","ok", "Cheque_Memo.jsp");
                                          System.out.println("Exception occur due to payment "); 
             		                 }
             		                System.out.println("final_upd:"+final_upd);
             		                 if(final_upd>0)
             		                 {
             		                	
             		                	

             		                		
             		                		pvupdate=0;
             		                		int txtbillNo=0,txtSCode=0;
             		                		double txtAmount1=0;
                		                	
                		                	 for(int j=0;j<bill_No.length;j++)
                		                	 {
                		                		  
                		                		 String bill_Date1[]=request.getParameterValues("billdate");
                		                		 String Grid_sl_type[]=request.getParameterValues("sltype");
                		                		    String Grid_pass_amount[]=request.getParameterValues("passamt");
                		                		    String verify_status[] = request.getParameterValues("verify_select_status");  
                       	                          String Grid_SL_code[]=request.getParameterValues("slcode");                          
                       	                    if(verify_status[j].equalsIgnoreCase("CHECKED")){
                       	                           
                		                		    System.out.println(paymentno+" 22 memo   --PaymentDate//--officeId "+PaymentDate+" "+officeId+"  officeId---"+vocherDate+"::::"+cashYear+cashMonth+billNo);
                		                	
                		                		    try{
                		                		String qry ="update FAS_MEMO_OF_PAYMENT_TRN set PVR_NO="+paymentno+"," +
                		                				" PVR_DATE=to_char('"+PaymentDate+"','dd-mm-yy') where PAYMENT_UNIT="+unitId+
                		                						" and PAYMENT_OFFICE="+officeId+" and CASHBOOK_YEAR="+Integer.parseInt(bill_Date1[j].split("/")[2])+
                		                								" and CASHBOOK_MONTH="+Integer.parseInt(bill_Date1[j].split("/")[1])+" and BILL_NO=" + Integer.parseInt(bill_No[j])+
            		                	 		" and SUB_LEDGER_CODE= " + Integer.parseInt(Grid_SL_code[j])+
            		                	 		" and AMOUNT=" +Double.parseDouble(Grid_pass_amount[j])+
            		                	 		" and (PVR_NO=0 or PVR_NO is null) and (PVR_DATE is null)";
                		                		System.out.println("qry 22 >>> "+qry);
                		                		    PreparedStatement pq = con.prepareStatement("update FAS_MEMO_OF_PAYMENT_TRN set PVR_NO=?, PVR_DATE=? where PAYMENT_UNIT=? and PAYMENT_OFFICE=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=?" +
                		                	 		" and SUB_LEDGER_CODE=? " +
                		                	 		" and AMOUNT=?" +
                		                	 		" and (PVR_NO=0 or PVR_NO is null) and (PVR_DATE is null)");
                		                		    
                		                	
                		                	 pq.setInt(1,paymentno);
                		                	pq.setDate(2,PaymentDate);
                		                	// pq.setString(2,vocherDate);
                		                	pq.setInt(3, unitId);
                		                	pq.setInt(4, officeId);
                		                //	pq.setInt(5, check_memo_Year1);
                		                //	pq.setInt(6, check_memo_Month1);
                		                	pq.setInt(5, Integer.parseInt(bill_Date1[j].split("/")[2]));
                		                	pq.setInt(6, Integer.parseInt(bill_Date1[j].split("/")[1]));
                		                	txtbillNo=Integer.parseInt(bill_No[j]);
                		                	txtSCode=Integer.parseInt(Grid_SL_code[j]);
                		                	txtAmount1=Double.parseDouble(Grid_pass_amount[j]);
                		                	pq.setInt(7, txtbillNo);
                		                	pq.setInt(8,txtSCode);
                		                	pq.setDouble(9,txtAmount1);
                		                	pvupdate=pq.executeUpdate();
                		                		    }
                		                		    catch (Exception e) {
                		                		    	System.out.println("pvupdate Error --> ");
														e.printStackTrace();
													}
                		                	System.out.println("pvupdate 22 --> "+pvupdate);
                		                	if(pvupdate>0)
                		                	{
                		                	//	select SANCTION_PROCEEDING_NO from FAS_MEMO_OF_PAYMENT_MST WHERE ACCOUNTING_UNIT_ID    =? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR =? AND CASHBOOK_MONTH =? AND BILL_NO=?
                		                	
                		                		
                		                		
                		                	//	PreparedStatement pq1 = con.prepareStatement("select SANCTION_PROCEEDING_NO from FAS_MEMO_OF_PAYMENT_MST WHERE ACCOUNTING_UNIT_ID    =? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR =? AND CASHBOOK_MONTH =? AND BILL_NO=?  ");
									/*	PreparedStatement pq1 = con
												.prepareStatement("SELECT SANCTION_PROCEEDING_NO "
														+ " FROM FAS_MEMO_OF_PAYMENT_MST m "
														+ " WHERE ACCOUNTING_UNIT_ID          =? "
														+ " AND ACCOUNTING_FOR_OFFICE_ID      =? "
														+ " AND CASHBOOK_YEAR                 =? "
														+ " AND CASHBOOK_MONTH                =? "
														+ " AND BILL_NO                       =? "
														+ " AND m.SANCTION_PROCEEDING_NO NOT IN "
														+ "  (SELECT bm.sanction_proc_no "
														+ "  FROM FAS_BILL_REGISTER_MASTER bm "
														+ "  WHERE bm.ACCOUNTING_UNIT_ID =m.ACCOUNTING_UNIT_ID "
														+ "  AND bm.CASHBOOK_YEAR        =m.CASHBOOK_YEAR "
														+ "  AND bm.CASHBOOK_MONTH       =m.CASHBOOK_MONTH "
														+ "  AND bm.BILL_NO              =m.BILL_NO "
														+ "  AND bm.bill_type LIKE 'WOSP' "
														+ "  ) ");*/
                		                		
                		                		PreparedStatement pq1=con.prepareStatement("SELECT Sanction_Proceeding_No " +
" FROM FAS_MEMO_OF_PAYMENT_MST m, " +
"  FAS_MEMO_OF_PAYMENT_TRN t " +
" WHERE M.Accounting_Unit_Id    =T.Accounting_Unit_Id " +
" AND M.Accounting_For_Office_Id=T.Accounting_For_Office_Id " +
" AND M.Cashbook_Month          = T.Cashbook_Month " +
" AND M.Cashbook_Year           =T.Cashbook_Year " +
" AND m.bill_no                 =t.bill_no " +
" AND T.Payment_Unit            =? " +
" AND T.Payment_Office          =? " +
" AND M.Cashbook_Year           =? " +
" AND M.Cashbook_Month          =? " +
" AND m.BILL_NO                 =?" );
                		                		
                		                		pq1.setInt(1, unitId);
                    		                	pq1.setInt(2, officeId);
                    		                /*	pq1.setInt(3, check_memo_Year1);
                    		                	pq1.setInt(4, check_memo_Month1);*/
                    		                	pq1.setInt(3, Integer.parseInt(bill_Date1[j].split("/")[2]));
                    		                	pq1.setInt(4, Integer.parseInt(bill_Date1[j].split("/")[1]));
                    		                	pq1.setInt(5, Integer.parseInt(bill_No[j]));
                  		                	ResultSet rss1=pq1.executeQuery();
                  		                	int sanno=0;
                  		                	String san_no="";
                  		                	if(rss1.next()){
                  		                		san_no=rss1.getString("SANCTION_PROCEEDING_NO");
                  		                	}else{
                  		                		
                  		                		System.out.println("sanno--else-- 22   >"+sanno);
                  		                	}
                  		                	sanno=Integer.parseInt(san_no);
                  		             	System.out.println("sanno   22 --->"+sanno);
                  		             	System.out.println(" bill no >> "+Integer.parseInt(bill_No[j]));
                  		             	if(sanno>0){
                  		             		
                      		      			/*if(Integer.parseInt(bill_Date1[j].split("/")[2])>2014 && Integer.parseInt(bill_Date1[j].split("/")[1])>3)
                      		      			{
                      		      				 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
                      		      				 sub_main=" Fas_Bill_Register_MasterNEW M, "+
                      		      				" 	  Fas_Bill_Register_Transactionw T ";
                      		      			}else{
                      		      				sub_q = " FAS_BILL_REGISTER_MASTER "; 
                      		      				 sub_main=" Fas_Bill_Register_Master M, "+
                      		      							" 	  Fas_Bill_Register_Transaction T ";
                      		      			}*/
                  		             		
                  		             		if (Integer.parseInt(bill_Date1[j].split("/")[2]) > 2014) {
                  		      				if (Integer.parseInt(bill_Date1[j].split("/")[2]) == 2015 && Integer.parseInt(bill_Date1[j].split("/")[1]) <= 3) {
                  		      				sub_q = " FAS_BILL_REGISTER_MASTER "; 
                 		      				 sub_main=" Fas_Bill_Register_Master M, "+
                 		      							" 	  Fas_Bill_Register_Transaction T ";
                  		      				}else{
                  		      				 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
                  		      				 sub_main=" Fas_Bill_Register_MasterNEW M, "+
                  		      				" 	  Fas_Bill_Register_Transactionw T ";
                  		      				}}
                  		             		else{
                  		             			sub_q = " FAS_BILL_REGISTER_MASTER "; 
                     		      				 sub_main=" Fas_Bill_Register_Master M, "+
                     		      							" 	  Fas_Bill_Register_Transaction T ";
                  		             		}
                  		             		
                  		             		int count_ckh=0;
                  		             		String cntsub_qry = "SELECT count(*) con  "
               									+ " FROM  "+sub_q
               									+ " WHERE ACCOUNTING_UNIT_ID     =? "
               									+ " AND ACCOUNTING_UNIT_OFFICE_ID=? "
               									+ " AND CASHBOOK_YEAR            =? "
               									+ " AND CASHBOOK_MONTH           =? "
               									+ " AND BILL_NO                  =? " 
               									+ " AND SANCTION_PROC_NO                  =? " 
               									+ " and bill_type not LIKE 'WOSP'";
                  		             		
                  		             		PreparedStatement ps_Count=con.prepareStatement(cntsub_qry);
                  		             		ps_Count.setInt(1, unitId);
                  		             		ps_Count.setInt(2, officeId);
                  		             	/*	ps_Count.setInt(3, check_memo_Year1);
                  		             		ps_Count.setInt(4, check_memo_Month1);*/
                  		             		ps_Count.setInt(3, Integer.parseInt(bill_Date1[j].split("/")[2]));
                		                	ps_Count.setInt(4, Integer.parseInt(bill_Date1[j].split("/")[1]));
                  		             		ps_Count.setInt(5, Integer.parseInt(bill_No[j]));
                  		             		ps_Count.setString(6,san_no);
               								ResultSet rs_Count=ps_Count.executeQuery();
                              		             	while(rs_Count.next())
                              		             	{
                              		             		count_ckh=rs_Count.getInt(1);	
                              		             	}
                              		             	System.out
															.println("count_ckh >>> "+count_ckh);
                              		             	if(count_ckh==0){
                              		             	
                              		      			
                              		             		try{
                              		             		String cntsub_qry1 = "SELECT count(*) con  "
                               									+ " FROM  "+sub_q
                               									+ " WHERE ACCOUNTING_UNIT_ID     =? "
                               									+ " AND ACCOUNTING_UNIT_OFFICE_ID=? "
                               									+ " AND CASHBOOK_YEAR            =? "
                               									+ " AND CASHBOOK_MONTH           =? "
                               									+ " AND BILL_NO                  =? " 
                               									+ " AND SANCTION_PROC_NO                  =? " 
                               									+ " and bill_type  LIKE 'WOSP'";
                                  		             		System.out
																	.println(" Qry for WOSP cntsub_qry1"+cntsub_qry1);
                                  		             		PreparedStatement ps_Count1=con.prepareStatement(cntsub_qry1);
                                  		             		ps_Count1.setInt(1, unitId);
                                  		             		ps_Count1.setInt(2, officeId);
                                  		             		/*ps_Count1.setInt(3, check_memo_Year1);
                                  		             		ps_Count1.setInt(4, check_memo_Month1);*/
                                  		             		ps_Count1.setInt(3, Integer.parseInt(bill_Date1[j].split("/")[2]));
                                		                	ps_Count1.setInt(4, Integer.parseInt(bill_Date1[j].split("/")[1]));
                                  		             		ps_Count1.setInt(5, Integer.parseInt(bill_No[j]));
                                  		             		ps_Count1.setString(6,san_no);
                               								ResultSet rs_Count1=ps_Count1.executeQuery();
                              		             		if(rs_Count1.next()){
                              		             		hrm_billupdate=1;}
                              		             		}catch(Exception e)
                              		             		{
                              		             			e.printStackTrace();
                              		             		}
                              		             		}
                              		             	else if(count_ckh>0)	
                              		             	{
                  		             		
                  		             		
                  		             		
                  		             	int major=0,minor=0,sub_minor=0;
                  		             	String sub_qry = "SELECT BILL_MINOR_TYPE_CODE, "
   									+ "  BILL_MAJOR_TYPE, "
   									+ "  BILL_SUB_TYPE_CODE "
   									+ " FROM  "+sub_q
   									+ " WHERE ACCOUNTING_UNIT_ID     =? "
   									+ " AND ACCOUNTING_UNIT_OFFICE_ID=? "
   									+ " AND CASHBOOK_YEAR            =? "
   									+ " AND CASHBOOK_MONTH           =? "
   									+ " AND BILL_NO                  =? ";
   							try{
   								System.out.println(sub_qry);
   								PreparedStatement ps_bill=con.prepareStatement(sub_qry);
   								ps_bill.setInt(1, unitId);
   								ps_bill.setInt(2, officeId);
   								/*ps_bill.setInt(3, check_memo_Year1);
   								ps_bill.setInt(4, check_memo_Month1);*/
   								ps_bill.setInt(3, Integer.parseInt(bill_Date1[j].split("/")[2]));
   								ps_bill.setInt(4, Integer.parseInt(bill_Date1[j].split("/")[1]));
   								ps_bill.setInt(5, Integer.parseInt(bill_No[j]));
   								ResultSet rs_bill=ps_bill.executeQuery();
                  		             	while(rs_bill.next()){
                  		             		major=rs_bill.getInt("BILL_MAJOR_TYPE");
                  		             	minor=rs_bill.getInt("BILL_MINOR_TYPE_CODE");
                  		         	sub_minor=rs_bill.getInt("BILL_SUB_TYPE_CODE");
                  		             	/*}
                  		             	
                  		             	}catch (Exception e) {
                  		             	System.out.println("Bill No--else-->"+e);
   									}*/
                  		         //	System.out.println("major  222 -->"+major+"   "+minor+"   "+sub_minor);
                  		         	PreparedStatement pq2 =null,pq23=null;
                  		         	ResultSet rq23=null;
                  		         	String update_Qry="",Alread_Chk="",count_chk="";
                  		         	int countChk=0;
													if (major == 2 && minor == 2 && sub_minor == 1) {
														update_Qry = "update SLS_SANCTIONS_BILLS_LINK_MST1 SET VOUCHER_NO =?, VOUCHER_DATE=?, CASHBOOK_YEAR=?, CASHBOOK_MONTH =?, PROCESS_FLOW_ID='FR' WHERE " +
															//	SANCTION_PROC_OFFICE_ID=? AND" +
																 " OFFICE_ID=? AND " +
																"  HRMS_SANCTION_ID=? AND EMPLOYEE_ID=? ";
															//	+ "AND (VOUCHER_NO=0 OR VOUCHER_NO IS NULL) AND (VOUCHER_DATE IS NULL)";
														count_chk = "select count(*) as cno from  SLS_SANCTIONS_BILLS_LINK_MST1 where  VOUCHER_NO =? and VOUCHER_DATE=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH =?  and PROCESS_FLOW_ID='FR' and  OFFICE_ID=? AND HRMS_SANCTION_ID=?::numeric AND EMPLOYEE_ID=? AND (VOUCHER_NO=0 OR VOUCHER_NO IS NULL) AND (VOUCHER_DATE IS NULL)";
													} else {
														update_Qry = "update HRM_SANCTIONS_BILLS_LINK_MST SET VOUCHER_NO =?, VOUCHER_DATE=?, CASHBOOK_YEAR=?, CASHBOOK_MONTH =?, PROCESS_FLOW_ID='FR' WHERE "
																+ " ( SANCTION_PROC_OFFICE_ID=? or "
																+ " OFFICE_ID=" +officeId+
																		") AND HRMS_SANCTION_ID=?::numeric AND EMPLOYEE_ID=? ";
																		//+ "AND (VOUCHER_NO=0 OR VOUCHER_NO IS NULL) AND (VOUCHER_DATE IS NULL)";
														count_chk = "select count(*) as cno from  HRM_SANCTIONS_BILLS_LINK_MST where  VOUCHER_NO =? and VOUCHER_DATE=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH =?  and PROCESS_FLOW_ID='FR' and ( SANCTION_PROC_OFFICE_ID=? or OFFICE_ID="+officeId+") AND HRMS_SANCTION_ID=?::numeric AND EMPLOYEE_ID=? ";
													}
                  		          
                  		             	/// count check   joe on Jul 3
                  		             	try{

                   							System.out.println("count_chk qry ===  "+count_chk);
                   							System.out.println("sanno qry ===  "+sanno);
                   							System.out.println("txtSCode qry ===  "+txtSCode);
                   							System.out.println("count_chk qry ===  "+paymentno);
                   							
               							  	pq23 = con.prepareStatement(count_chk);
               							 pq23.setInt(1,paymentno);System.out.println("paymentno ===  "+paymentno);
               							pq23.setDate(2,PaymentDate);System.out.println("PaymentDate ===  "+PaymentDate);
               							pq23.setInt(3,cashYear);
               							pq23.setInt(4,cashMonth);
                    		                	pq23.setInt(5,officeId);
                    		                	pq23.setInt(6,sanno);
                    		                	pq23.setInt(7, txtSCode);
                    		                	rq23=pq23.executeQuery();   
                   		                	while(rq23.next())
                   		                	{
                   		                		countChk=rq23.getInt("cno");
                   		                	}
               							}catch (Exception e) {
               								System.out.println("error in count chkj  ");
            								e.printStackTrace();
            							}
               							
               							
               							System.out.println("final countChk "+countChk);
               							
                  		             	
                  		             	if(countChk==0){
                  		             	try{
                  		             		System.out.println(" update_Qry  >   "+update_Qry);
                  		             	pq2 = con.prepareStatement(update_Qry);
                   		                	pq2.setInt(1,paymentno);
                   		                	pq2.setDate(2,PaymentDate);
                   		                	pq2.setInt(3,cashYear);
                    		                	pq2.setInt(4,cashMonth);
                   		                	pq2.setInt(5,officeId);
                   		                	pq2.setInt(6,sanno);
                   		                	pq2.setInt(7, txtSCode);
                   		                	hrm_billupdate=pq2.executeUpdate();
                   		                	System.out.println("hrm_billupdate  memeo 22 "+hrm_billupdate);
                  		             	}catch (Exception e) {
                  		             		System.out.println("error in hrm_billupdate  ");
										e.printStackTrace();
										}
                  		             	
                  		             	}
                  		             	}
                  		          
      		             	}catch (Exception e) {
      		             	System.out.println("Bill No--else-->"+e);
							}
                   		                	
                   		                	
                              		             	}         	
                		                		
                  		             	}//else sanno >0
                  		             	else if(sanno==0){
                  		             	
                  		             		System.out.println("sannnooo  equal zero....22.");
                  		             		
                  		             	PreparedStatement pqq = con.prepareStatement("update FAS_BILL_REGISTERNEW set PVR_NO=?, PVR_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=? and (PVR_NO=0 or PVR_NO is null) and (PVR_DATE is null)");
           		                	 pqq.setInt(1,paymentno);
           		                	pqq.setDate(2,PaymentDate);
           		                	// pq.setString(2,vocherDate);
           		                	pqq.setInt(3, unitId);
           		                	pqq.setInt(4, officeId);
           		                	pqq.setInt(5, check_memo_Year1);
           		                	pqq.setInt(6, check_memo_Month1);
           		                	pqq.setInt(7, Integer.parseInt(bill_No[j]));
           		                	hrm_billupdate=pqq.executeUpdate();
           		                	System.out.println("pvinRegister   22 --> "+hrm_billupdate);
           		              /*  	if(pvinRegister>0)
           		                	{
           		                		 con.commit();
                		                	 sendMessage(response, "Records Inserted Successfully",
                		      						"ok", "Cheque_Memo.jsp");
           		                		
           		                	}else{
           		                		
           		                		try {
                    		   					con.rollback();
                    		   				 
       		             		   				} catch (SQLException sqle) {
       		             		   					System.out.println("exception in rollback1 " + sqle);
       		             		   				}	
       		             		   			  sendMessage(response, "Insertion Failed without Sanction","ok", "Cheque_Memo.jsp");
           		                	}
           		                	*/
                  		             		
                  		             		
                  		             	}
                  		             	
                		                	}    
                		                	 }
                		                	 }
                		                	 System.out.println("hrm_billupdate --> "+hrm_billupdate);
                		                		if(hrm_billupdate>0){
               		                			 con.commit();
                       		                	 sendMessage(response, "Records Inserted Successfully",
                       		      						"ok", "Cheque_Memo.jsp");
               		                		}	
                		                		
                		                		else{
               		                			try {
                           		   					con.rollback();
                           		   				 
              		             		   				} catch (SQLException sqle) {
              		             		   					System.out.println("exception in rollback1 " + sqle);
              		             		   				}	
              		             		   			  sendMessage(response, "insertion failed ","ok", "Cheque_Memo.jsp");
               		                		}
               		                		
               		                		
             		                	
                		                	
                		                 
             		                 }
             		                   	
             		                 
             		                 else
             		                 {
             		                	try {
             		   					con.rollback();
		             		   				} catch (SQLException sqle) {
		             		   					System.out.println("exception in rollback3 " + sqle);
		             		   				}
             		                	 sendMessage(response, "Insertion Failed","ok", "Cheque_Memo.jsp");
             		                 }
                 
                }
                
                else
                {
             	   try{
             		  // con.rollback();
             		   System.out.println("exception in rollback ");
             		   }catch(Exception sqle){System.out.println("exception in rollback4 "+sqle);}
                    
             	  sendMessage(response, "Insertion Failed","ok", "Cheque_Memo.jsp");
                } 
				
//				if (bill_No.length == (serialNo - 1)) {
//					con.commit();
//					 sendMessage(response, "Records Inserted Successfully",
//      						"ok", "cheque_memo.jsp");
//				}

                } catch (Exception e) {
				try {
					con.rollback();
				} catch (SQLException sqle) {
					System.out.println("exception in rollback5 " + sqle);
				}
				e.getStackTrace();

				 sendMessage(response, "Insertion Failed","ok", "Cheque_Memo.jsp");
				e.printStackTrace();
				System.out.println("Exception occur due to " + e);

			}
			//}
			/*else if(count!=0)
			{
				 sendMessage(response, "Already Created","ok", "Cheque_Memo.jsp");	
			}*/
		
		
		}else if(memoType==3){

			System.out.println("hai    Memo type  not a 2222      this is add::"+memoType);
			int chequeMemoNo = 0;
			int serialNo = 1;
			 int paymentno=0,pvupdate=0,hrm_billupdate=0,pvinRegister=0;
			int last=0,final_upd=0;
			int unitId = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int officeId = Integer.parseInt(request.getParameter("cmbOffice_code"));
			
			//Add 20Nov13 Lakshmi insert in Update qry last
			int check_memo_Year1 = Integer.parseInt(request.getParameter("check_memo_Year"));
			int check_memo_Month1 = Integer.parseInt(request.getParameter("check_memo_Month"));
			
			
		//	String memoDate = request.getParameter("memodate");
			String vocherDate = request.getParameter("vochardate");
			long bankNo = Long.parseLong(request.getParameter("txtBankAccountNo"));
			int operationHeadCode = Integer.parseInt(request.getParameter("txtCash_Acc_code"));
			int bankId =Integer.parseInt( request.getParameter("txtBankID"));
			int branchId = Integer.parseInt(request.getParameter("txtBranchID"));
			//String payeeType = request.getParameter("payeetype");
			//txtEmpId
			int payeeCode = Integer.parseInt(request.getParameter("txtEmpId"));
			String chequeNo = request.getParameter("txtCheque_DD_NO");
			String chequeDate = request.getParameter("txtCheque_DD_date");
			float txtAmount = Float.parseFloat(request.getParameter("txtAmount"));
			String particulars = request.getParameter("particulars");
			String assignVoucherDate="";

			String[] cashMonthYear = vocherDate.split("/");
			int cashMonth = Integer.parseInt(cashMonthYear[1]);
			int cashYear = Integer.parseInt(cashMonthYear[2]);
			//String bill_No_new[] = request.getParameterValues("billno");
			int count=0;
			try{
				ps = con.prepareStatement("select count(*) as chequememono from FAS_CHEQUE_MEMO_MST where ACCOUNTING_UNIT_ID=" +unitId+  
						" and ACCOUNTING_FOR_OFFICE_ID="+officeId+" and CASHBOOK_MONTH="+cashMonth+" and CASHBOOK_YEAR="+cashYear+" and CHEQUE_NO="+chequeNo+" and status='L' "); 
				rs = ps.executeQuery();
				if (rs.next()) {
					count = rs.getInt("chequememono");
				}
			} catch (Exception e) {
				System.out.println(e);
			}
			//if(count==0)
			//{
			try {
				ps = con.prepareStatement("select COALESCE(MAX(CHEQUE_MEMO_NO),0) as chequememono from FAS_CHEQUE_MEMO_MST where ACCOUNTING_UNIT_ID=" +unitId+
						" and ACCOUNTING_FOR_OFFICE_ID="+officeId+" and CASHBOOK_MONTH="+cashMonth+" and CASHBOOK_YEAR="+cashYear);
				rs = ps.executeQuery();
				if (rs.next()) {
					chequeMemoNo = rs.getInt("chequememono");
				}
			} catch (Exception e) {
				System.out.println(e);
			}

			
			chequeMemoNo = chequeMemoNo + 1;
			System.out.println("chequeMemoNo >>> "+chequeMemoNo);
			int bno=0;
			String bill_No_new[] = request.getParameterValues("billno");
			for(int ii=0;ii<bill_No_new.length;ii++)
			{
				bno=Integer.parseInt(bill_No_new[ii]);
			}
			

			try {
				con.clearWarnings();
				con.setAutoCommit(false);

				ps1 = con.prepareStatement("insert into FAS_CHEQUE_MEMO_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,CHEQUE_MEMO_TYPE_CODE,CHEQUE_MEMO_NO,VOUCHER_DATE,BANK_AC_NO,ACCOUNT_HEAD_CODE,PAYEE_TYPE_CODE,PAYEE_CODE,BANK_ID,BRANCH_ID,CHEQUE_NO,CHEQUE_DATE,CHEQUE_AMOUNT,PARTICULARS,UPDATED_BY_USERID,UPDATED_DATE,CHEQUE_MEMO_DATE,STATUS)" +
						" values(?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,?,?,to_date(?,'dd-mm-yyyy'),?)");
				  ps1.setInt(1, unitId);
				  ps1.setInt(2, officeId);
				  ps1.setInt(3, cashYear);
				  ps1.setInt(4, cashMonth);
				  ps1.setInt(5,memoType);
				  ps1.setInt(6,chequeMemoNo);
			//	  ps.setString(7, memoDate);	pq.setDate(2,PaymentDate);
				  ps1.setString(7, vocherDate);
				  ps1.setLong(8, bankNo);
				  ps1.setInt(9, operationHeadCode);
				  ps1.setString(10, payeeType); 
				  ps1.setInt(11,payeeCode);
				  ps1.setInt(12,bankId);
				  ps1.setInt(13,branchId);
				  ps1.setString(14,chequeNo);
				  ps1.setString(15, chequeDate);
				  ps1.setFloat(16, txtAmount);
				  ps1.setString(17, particulars);
				  ps1.setString(18, updatedby); 
				  ps1.setTimestamp(19, ts);
				  ps1.setString(20, vocherDate);
				  ps1.setString(21,"L");
				 int jj= ps1.executeUpdate();
				  System.out.println("............ >>>> "+jj);
				  
			
				String bill_No[] = request.getParameterValues("billno");
				String bill_Date[] = request.getParameterValues("billdate");
				String pass_amount[] = request.getParameterValues("passamt");
				String headcode[] = request.getParameterValues("headcode");
				
				String sl_Type[] = request.getParameterValues("sltype");
				String sl_Code[] = request.getParameterValues("slcode");
                            
				String dr_Amount[] = request.getParameterValues("dramt");
				String remarks12[] = request.getParameterValues("remarks");
				String crDRIndicator[] = request.getParameterValues("indicator");
				System.out.println("length og headcode >>> "+headcode.length);
				int billNo = 0, assignedVocherNo = 0, acHeadCode = 0, slType = 0,slCode = 0;
				float passAmount = 0, drAmount = 0;

				for (int i = 0; i < bill_No.length; i++) {
					ps2 = con.prepareStatement("insert into FAS_CHEQUE_MEMO_TRN(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,"
							+ "CASHBOOK_YEAR,CASHBOOK_MONTH,"
							+ "CHEQUE_MEMO_NO,SL_NO,"
							+ "BILL_NO,BILL_DATE,"
							+ "PASS_ORDER_AMOUNT,VOUCHER_NO,"
							+ "VOUCHER_DATE,ACCOUNT_HEAD_CODE,"
							+ "SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,AMOUNT,PARTICULARS,UPDATED_BY_USERID,UPDATED_DATE,CR_DR_INDICATOR) values(?,?,?,?,?,?,?,to_date(?,'dd-mm-yyyy'),?,?,"
							+ "to_date(?,'dd-mm-yyyy'),?,?,?,?,?,?,?,?)");

					
					try {
						billNo = Integer.parseInt(bill_No[i]);//
					} catch (Exception e) {
						System.out.println(e);
					}

					

					try {
						acHeadCode = Integer.parseInt(headcode[i]);
					} catch (Exception e) {
						System.out.println(e);
					}

					try {
						slType = Integer.parseInt(sl_Type[i]);
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						slCode = Integer.parseInt(sl_Code[i]);
					} catch (Exception e) {
						System.out.println(e);
					}
					try {
						passAmount = Float.parseFloat(pass_amount[i]);
					} catch (Exception e) {
						System.out.println(e);
					}

					try {
						drAmount = Float.parseFloat(dr_Amount[i]);
					} catch (Exception e) {
						System.out.println(e);
					}

					String billDate = bill_Date[i];
					
					String remarks = remarks12[i];
					String indicator=crDRIndicator[i];
					ps2.setInt(1, unitId);
					ps2.setInt(2, officeId);
					ps2.setInt(3, cashYear);
					ps2.setInt(4, cashMonth);
					ps2.setInt(5, chequeMemoNo);
					ps2.setInt(6, serialNo);
					ps2.setInt(7, billNo);
					ps2.setString(8, billDate);
					ps2.setFloat(9, passAmount);
					ps2.setInt(10, assignedVocherNo);
					ps2.setString(11, assignVoucherDate);
					ps2.setInt(12, acHeadCode);
					ps2.setInt(13, slType);
					ps2.setInt(14, slCode);
					ps2.setFloat(15, drAmount);
					ps2.setString(16, remarks);
					ps2.setString(17, updatedby);
					ps2.setTimestamp(18, ts);
					ps2.setString(19, indicator);
					last=ps2.executeUpdate();
					serialNo++;

				}
				System.out.println("last >>>>> "+last);
				if(last>0)
                {
             	   int txtJournal_type_code=0,txtchallan_No=0,txtNo_Acq_rolls=0;
             	   int bill_no_Recs=0;
             	   String mode="A",module="BPF",paid="";
             	  //String vocherDate = request.getParameter("vochardate");
             	  String[] sd = request.getParameter("vochardate").split("/");
                Calendar  c =
         new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                               Integer.parseInt(sd[0]));
                  java.util.Date d = c.getTime();
                 Date PaymentDate = new Date(d.getTime());
                  System.out.println("paymentno >>>>  " + paymentno);
             	   
             	   String radPart_Amt = "",parti="Automatic Cheque Memo to Payments";
             	 // cs =con.prepareCall("{call FAS_PAYMENT_MASTER_PROC_1(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
             	  // pro_qry="{call FAS_PAYMENT_MASTER_PROC_cp(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
             	  //pro_qry="{call FAS_PAYMENT_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}";
                	 pro_qry="call FAS_PAYMENT_MASTER_PROC(?::numeric,?::numeric,?,?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?,?::numeric,?,?,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?,?,?,?,?::numeric)";

             	   cs_set=con.prepareCall(pro_qry);	
             	   System.out.println("cs_set >>>>> "+cs_set);
             	  cs_set.setInt(1, unitId);
             	 cs_set.setInt(2, officeId);
             	cs_set.setDate(3,PaymentDate);
             	cs_set.setInt(4,cashYear);
             	cs_set.setInt(5,cashMonth);
             	cs_set.setString(6, "B");
             	cs_set.setInt(7,paymentno);
             	cs_set.setInt(8,operationHeadCode);             		               
             	cs_set.setInt(9, bankId);
             	cs_set.setInt(10, branchId);
             	cs_set.setLong(11, bankNo);
             	cs_set.setString(12,payeeType);//sltype
             	cs_set.setInt(13,payeeCode);
             	cs_set.setString(14, "CR");
             	cs_set.setString(15, particulars);
             	cs_set.setString(16, "C");
             		                 // cs.setInt(17,txtPay_Vou_No);
             		                 //cs.setDate(18,txtPay_Vou_date);
             	cs_set.setInt(17, txtJournal_type_code);
             	cs_set.setString(18, particulars);
             	cs_set.setString(19, radPart_Amt);
             	cs_set.setFloat(20, txtAmount);
             	cs_set.setInt(21, txtchallan_No);
             	cs_set.setInt(22,bill_No.length);
             	cs_set.setDouble(23,txtAmount);
             	cs_set.setInt(24, txtNo_Acq_rolls);
             	cs_set.setString(25,mode);
             	cs_set.setString(26, module);
             	cs_set.setString(27, "insert");
             	cs_set.registerOutParameter(7, java.sql.Types.NUMERIC);
             	cs_set.registerOutParameter(28, java.sql.Types.NUMERIC);
             	cs_set.setInt(28, 0);
             	cs_set.setString(29, updatedby);
             	cs_set.setTimestamp(30, ts);
             	cs_set.setInt(31, 0);
             		                 System.out.println("b4 exe ");
             		                boolean hh=cs_set.execute();
             		                System.out.println("hh >>> "+hh);
             		                 //paymentno =cs_set.getInt(7);  
             		               paymentno =cs_set.getBigDecimal(7).intValue();
             		               //  System.out.println("paymentno "+paymentno);
             		                System.out.println("paymentno "+paymentno);
             		              
             		                 //int pay_mas = cs_set.getInt(28);
             		               int pay_mas = cs_set.getBigDecimal(28).intValue(); 
             		                 System.out.println("pay_mas=="+pay_mas);
             		                 if(pay_mas==0)
             		                 {
             		                	// System.out.println("ffff");
             		                	 
             		                	 String bill_no_Rec[]=request.getParameterValues("billno");
                                          bill_no_Recs=bill_no_Rec.length;//Integer.parseInt(No_TRN_Rec);
                                           System.out.println(" bill_no_Recs :"+bill_no_Recs);
                                           
                                           
             	                          String Grid_bill_date[]=request.getParameterValues("billdate");
             	                          String Grid_pass_amount[]=request.getParameterValues("passamt");
             	                          String Grid_head_code[]=request.getParameterValues("headcode");
             	                          String Grid_sl_type[]=request.getParameterValues("sltype");
             	                          String Grid_SL_code[]=request.getParameterValues("slcode");                          
             	                          String Grid_crdrindicator[]=request.getParameterValues("indicator");
             	                          String Grid_dr_amount[]=request.getParameterValues("dramt");                         
             	                          String Trn_remarks12[]=request.getParameterValues("remarks"); 
             	                          
             	                          String grid_cheque_no_dates[]=request.getParameterValues("cheque_no_dates");                          
             	                          String grid_bill_no[]=request.getParameterValues("billno");
             		                	 
             		                	 String sql =
             		                      //   "insert into FAS_PAYMENT_TRANSACTION_cp(ACCOUNTING_UNIT_ID, " +
             		                        "insert into FAS_PAYMENT_TRANSACTION(ACCOUNTING_UNIT_ID, " +
             		                         "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
             		                         "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, BILL_NO,BILL_TYPE,AGREEMENT_NO,AGREEMENT_DATE," +
             		                         "BILL_DATE,BANK_ID,BRANCH_ID,ACCOUNT_NO,CHEQUE_OR_DD,CHEQUE_DD_NO, CHEQUE_DD_DATE, " +
             		                         "AMOUNT, PARTICULARS,PAYABLE_VOUCHER_NO,PAYABLE_VOUCHER_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,REFERENCE_NO) " +
             		                         "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";


             		                     int SL_NO = 1, txtAcc_HeadCode = 0, cmbSL_Code =
             		                         0, cmbSL_type = 0, txtPay_Vou_No = 0;
             		                     Date txtBill_Date = null, txtAgree_Date =null, txtCheque_DD_date = null,
             		                     txtPay_Vou_date = null,billDate=null,chDate=null,pay_voudate1=null,pay_voudate=null;
             		                     double txtsub_Amount = 0;
             		                     String rad_sub_CR_DR = "", txtBill_no = "", txtBill_Type =
             		                         "", txtAgree_No = "", txtParticular = "",cdate="";
             		                     String txtCheque_DD = "", txtCheque_DD_NO =
             		                         "", txtsub_Paid_to = "", txtReference_No = "";
             		                     
             		                     ps = con.prepareStatement(sql);

             		                     
             		                     for (int k = 0; k <bill_no_Recs; k++) {
             		                    	 
             		                    	 ps.setInt(1, unitId);
             		                         ps.setInt(2, officeId);
             		                         ps.setInt(3, cashYear);
                                             ps.setInt(4, cashMonth);
             		                         ps.setInt(5,paymentno);
             		                         ps.setInt(6, SL_NO);
             		                         ps.setInt(7,Integer.parseInt(Grid_head_code[k]));
             		                         ps.setString(8, "DR");
             		                         ps.setInt(9,Integer.parseInt(Grid_sl_type[k]));
                                             ps.setInt(10,Integer.parseInt(Grid_SL_code[k]));
                                             ps.setInt(11,Integer.parseInt(grid_bill_no[k]));
             		                         ps.setString(12, txtBill_Type);
             		                         ps.setString(13, txtAgree_No);
             		                         ps.setDate(14, txtAgree_Date);
             		                        try
                                              { if(!Grid_bill_date[k].equalsIgnoreCase(""))
             		                                 {
             				                              String[] sd1=Grid_bill_date[k].split("/");
             				                                Calendar c1=new GregorianCalendar(Integer.parseInt(sd1[2]),Integer.parseInt(sd1[1])-1,Integer.parseInt(sd1[0]));
             				                                 java.util.Date d1=c1.getTime();
             				                                 billDate=new Date(d1.getTime());
             				                                 ps.setDate(15,billDate);
             				                         }
             		                                 else
             		                                 { 	 ps.setNull(15,java.sql.Types.DATE);
             		                                 }
             	    	                    	 }
                                              catch(Exception e) {
                                                  	 System.out.println(e);
                                              }
                                             
                                              ps.setInt(16,bankId);
                                              ps.setInt(17,branchId);
                                              ps.setLong(18,bankNo);
                                        
             		                         ps.setString(19,"C");
             		                       
             		                         ps.setString(20,chequeNo);
             		                         
             		                        try
                                            { 
             		                        	
           				                              String[] sd11=chequeDate.split("/");
           				                                Calendar c11=new GregorianCalendar(Integer.parseInt(sd11[2]),Integer.parseInt(sd11[1])-1,Integer.parseInt(sd11[0]));
           				                                 java.util.Date d11=c11.getTime();
           				                                 chDate=new Date(d11.getTime());
           				                                 ps.setDate(21,chDate);
           				                         
           	    	                    	 }
                                            catch(Exception e) {
                                                	 System.out.println(e);
                                            }
             		                         
//             		                        System.out.println("chequeDate:"+chequeDate);
//             		     				     ps.setString(20, chequeDate);
             		     				  
             		                         ps.setDouble(22,Double.parseDouble(Grid_pass_amount[k]));
             		                         ps.setString(23,Trn_remarks12[k]);
             		                        
             		                        ps.setInt(24,chequeMemoNo);
             		                       try
                                           { 
            		                        	
          				                              String[] sd111=vocherDate.split("/");
          				                                Calendar c111=new GregorianCalendar(Integer.parseInt(sd111[2]),Integer.parseInt(sd111[1])-1,Integer.parseInt(sd111[0]));
          				                                 java.util.Date d111=c111.getTime();
          				                                 pay_voudate=new Date(d111.getTime());
          				                                 ps.setDate(25,pay_voudate);
          				                         
          	    	                    	 }
                                           catch(Exception e) {
                                               	 System.out.println(e);
                                           }
             		                      
             		                         ps.setString(26, updatedby);
             		                      
             		                         ps.setTimestamp(27, ts);
             		                       
             		                         ps.setString(28, txtReference_No);

             		                         SL_NO++;
             		                        final_upd=ps.executeUpdate();
             		                       System.out.println("final_upd:"+final_upd);

             		                     }
             		                 }
             		                 else
             		                 {
             		                	 try{con.rollback();}
             		                	 catch(SQLException sqle){
             		                		 System.out.println("exception in rollback "+sqle);
             		                		 }
                                          
             		               	 sendMessage(response, "Insertion Failed","ok", "Cheque_Memo.jsp");
                                          System.out.println("Exception occur due to payment "); 
             		                 }
             		            	int sanno=0;
        		                	 
         		                 	int major=0,minor=0,sub_minor=0,ori_uint=0,ori_office=0;
         		               	String billType="";
             		                 if(final_upd>0)
             		                 {
             		                	 
             		                
             		                	 
             		                	 String bill_nonw[]=request.getParameterValues("billno");
                                    
                                        
                                          
                                          
            	                          String Grid_bill_date1[]=request.getParameterValues("billdate");
            	                          String Grid_pass_amount1[]=request.getParameterValues("passamt");
            	                          String Grid_SL_code[]=request.getParameterValues("slcode");      
            	                          for (int kz = 0; kz <bill_nonw.length; kz++) { 
            	                        	  
            	                        	  	billNo=Integer.parseInt(bill_nonw[kz]) ;
                 		                    	

            		                        	
  				                              String[] sd1n1=Grid_bill_date1[kz].split("/");
  				                                Calendar c1141=new GregorianCalendar(Integer.parseInt(sd1n1[2]),Integer.parseInt(sd1n1[1])-1,Integer.parseInt(sd1n1[0]));
  				                                 java.util.Date d13s=c1141.getTime();
  				                                 Date pay_voudate1 = new Date(d13s.getTime());
            	                        	  try{
            	                        		  PreparedStatement ps_cb=con.prepareStatement("SELECT distinct m.CASHBOOK_YEAR, " +
            	                        				  "  M.CASHBOOK_month,M.ACCOUNTING_UNIT_ID,M.ACCOUNTING_FOR_OFFICE_ID " +
            	                        				  "FROM FAS_MEMO_OF_PAYMENT_MST M, " +
            	                        				  "  FAS_MEMO_OF_PAYMENT_TRN T " +
            	                        				  " WHERE M.ACCOUNTING_UNIT_ID    =T.ACCOUNTING_UNIT_ID " +
            	                        				  " AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " +
            	                        				  " AND M.CASHBOOK_YEAR           =T.CASHBOOK_YEAR " +
            	                        				  " AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH " +
            	                        				  " AND M.BILL_NO                 =T.BILL_NO " +
            	                        				  " AND M.BILL_NO                 =? " +
            	                        				  " AND M.Bill_DAte                 =? " +
            	                        				  "  AND T.AMOUNT                  =? " +
            	                        				  " AND m.STATUS                  ='L' " +
            	                        				  " AND t.first_party             ='Y' " +
            	                        				  " AND t.PVR_NO                 IS NULL " +
            	                        				  " AND t.Payment_Unit      = ? " +
            	                        				  " AND t.Payment_Office= ?" );
            	                        	
            	                        		  ps_cb.setInt(1,billNo);
            	                        		  ps_cb.setDate(2,pay_voudate1);
            	                        		  
            	                        		  ps_cb.setDouble(3,Double.parseDouble(Grid_pass_amount1[kz]));
            	                        		  ps_cb.setInt(4, unitId);
            	                        		  ps_cb.setInt(5, officeId);
            	                        		  ResultSet rs_cb=ps_cb.executeQuery();
            	                        		  if(rs_cb.next()){
            	                        			  check_memo_Year1=	 rs_cb.getInt("CASHBOOK_YEAR") ;
            	                        			  check_memo_Month1=	 rs_cb.getInt("CASHBOOK_month") ;
            	                        			  ori_uint=	 rs_cb.getInt("ACCOUNTING_UNIT_ID") ;
            	                        			  ori_office=	 rs_cb.getInt("ACCOUNTING_FOR_OFFICE_ID") ;
            	                        		  }
            	                        	  }catch(Exception e){
            	                        		 e.printStackTrace(); 
            	                        	  }
            	                        	  
            	                        	  
            	                        	  
            	                        	  
            	                        	  
            	                        	  
             		                  
				                             
             		                    	
             		                	 System.out.println(paymentno+"--PaymentDate//--officeId "+PaymentDate+" "+officeId+"  officeId---"+vocherDate+"::::"+cashYear+cashMonth+billNo);
								
             		                	 PreparedStatement pq = con
											.prepareStatement("update FAS_MEMO_OF_PAYMENT_TRN set PVR_NO=?, PVR_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? "
													+ "and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?"
													+ " and BILL_NO=? " +
													
												///	+ " and SUB_LEDGER_CODE=? and AMOUNT=? " +
													// +
													" and (PVR_NO=0 or PVR_NO is null) and (PVR_DATE is null)");
                                        pq.setInt(1,paymentno);
             		                	pq.setDate(2,PaymentDate);
             		                	// pq.setString(2,vocherDate);
             		                /*	pq.setInt(3, unitId);
             		                	pq.setInt(4, officeId);*/
             		               	pq.setInt(3, ori_uint);
         		                	pq.setInt(4, ori_office);
             		                	pq.setInt(5, check_memo_Year1);System.out.println("check_memo_Year1   "+check_memo_Year1);
             		                     pq.setInt(6, check_memo_Month1);System.out.println("check_memo_Month1   "+check_memo_Month1);
             		                	pq.setInt(7, billNo);System.out.println("billNo   "+billNo);
             		                //	pq.setInt(6,Integer.parseInt(Grid_SL_code[kz]));
             		                //	pq.setDouble(7,Double.parseDouble(Grid_pass_amount1[kz]));
             		                	pvupdate=pq.executeUpdate();
             		                	System.out.println("pvupdate --> "+pvupdate);
             		                	if(pvupdate>0)
             		                	{
             		                	//	select SANCTION_PROCEEDING_NO from FAS_MEMO_OF_PAYMENT_MST WHERE ACCOUNTING_UNIT_ID    =? AND ACCOUNTING_FOR_OFFICE_ID=? AND CASHBOOK_YEAR =? AND CASHBOOK_MONTH =? AND BILL_NO=?
             		                	
             		                		
             		                		
             		                	/*	PreparedStatement pq1 = con.prepareStatement("select SANCTION_PROCEEDING_NO from FAS_MEMO_OF_PAYMENT_MST WHERE ACCOUNTING_UNIT_ID    =? AND ACCOUNTING_FOR_OFFICE_ID=?"
             		                				+ " AND CASHBOOK_YEAR =? AND CASHBOOK_MONTH =?"
             		                			+" and BILL_DATE =?"
             		                		
             		                				+ " AND BILL_NO=?");*/
             		                		
             		                	/*	pq1.setInt(1, unitId);
                 		                	pq1.setInt(2, officeId);*/
             		                		PreparedStatement pq1 = con.prepareStatement("SELECT Sanction_Proceeding_No " +
             		                				"FROM FAS_MEMO_OF_PAYMENT_MST m, " +
             		                				"  FAS_MEMO_OF_PAYMENT_TRN t " +
             		                				"WHERE M.Accounting_Unit_Id    =T.Accounting_Unit_Id " +
             		                				"AND M.Accounting_For_Office_Id=T.Accounting_For_Office_Id " +
             		                				"AND M.Cashbook_Month          = T.Cashbook_Month " +
             		                				"AND M.Cashbook_Year           =T.Cashbook_Year " +
             		                				"AND m.bill_no                 =t.bill_no " +
             		                				"AND T.Payment_Unit            =? " +
             		                				"AND T.Payment_Office          =? " +
             		                				"AND M.Cashbook_Year           =? " +
             		                				"AND M.Cashbook_Month          =? " +
             		                				"AND M.BILL_DATE          =? " +
             		                				"AND m.BILL_NO                 =?" );
             		                		pq1.setInt(1, ori_uint);
                 		                	pq1.setInt(2, ori_office);
                 		                	pq1.setInt(3, check_memo_Year1);
                 		                	pq1.setInt(4, check_memo_Month1);
                 		                	pq1.setDate(5,pay_voudate1);
                     		            
                 		                	pq1.setInt(6, billNo);
               		                	ResultSet rss1=pq1.executeQuery();
               		                
               		                	if(rss1.next()){
               		                		sanno=rss1.getInt("SANCTION_PROCEEDING_NO");
               		                	}else{
               		                		
               		                		System.out.println("sanno--else-->"+sanno);
               		                	}
               		             	System.out.println("sanno--->"+sanno);
               		             	if(sanno>0){
               		             /*	if(Integer.parseInt(Grid_bill_date1[kz].split("/")[2])>2014 && Integer.parseInt(Grid_bill_date1[kz].split("/")[1])>3)
              		      			{
              		      				 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
              		      				 sub_main=" Fas_Bill_Register_MasterNEW M, "+
              		      				" 	  Fas_Bill_Register_Transactionw T ";
              		      			}else{
              		      				sub_q = " FAS_BILL_REGISTER_MASTER "; 
              		      				 sub_main=" Fas_Bill_Register_Master M, "+
              		      							" 	  Fas_Bill_Register_Transaction T ";
              		      			}
               		             */
               		             	if (Integer.parseInt(Grid_bill_date1[kz].split("/")[2]) > 2014) {
               		 				if (Integer.parseInt(Grid_bill_date1[kz].split("/")[2]) == 2015 && Integer.parseInt(Grid_bill_date1[kz].split("/")[1]) <= 3) {
               		 				sub_q = " FAS_BILL_REGISTER_MASTER "; 
         		      				 sub_main=" Fas_Bill_Register_Master M, "+
         		      							" 	  Fas_Bill_Register_Transaction T ";
               		 				}else{
               		 				 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
          		      				 sub_main=" Fas_Bill_Register_MasterNEW M, "+
          		      				" 	  Fas_Bill_Register_Transactionw T ";
               		 				}
               		             	}else
               		             	{
               		             	sub_q = " FAS_BILL_REGISTER_MASTER "; 
         		      				 sub_main=" Fas_Bill_Register_Master M, "+
         		      							" 	  Fas_Bill_Register_Transaction T ";
               		             	}
               		             		
               		             		
               		             	String sub_qry = "SELECT BILL_MINOR_TYPE_CODE, "
									+ "  BILL_MAJOR_TYPE, "
									+ "  BILL_SUB_TYPE_CODE ,bill_type"
									+ " FROM  "+sub_q
									+ " WHERE ACCOUNTING_UNIT_ID     =? "
									+ " AND ACCOUNTING_UNIT_OFFICE_ID=? "
									+ " AND CASHBOOK_YEAR            =? "
									+ " AND CASHBOOK_MONTH           =? "
									+ " AND BILL_NO                  =?";
							try{
								System.out.println(sub_qry);
								PreparedStatement ps_bill=con.prepareStatement(sub_qry);
							/*	ps_bill.setInt(1, unitId);
								ps_bill.setInt(2, officeId);*/
								ps_bill.setInt(1, ori_uint);
								ps_bill.setInt(2, ori_office);
								check_memo_Month1=Integer.parseInt(Grid_bill_date1[kz].split("/")[1]);
								check_memo_Year1=Integer.parseInt(Grid_bill_date1[kz].split("/")[2]);
								ps_bill.setInt(3, check_memo_Year1);
								ps_bill.setInt(4, check_memo_Month1);
								ps_bill.setInt(5, billNo);
								ResultSet rs_bill=ps_bill.executeQuery();
               		             	while(rs_bill.next()){
               		             		major=rs_bill.getInt("BILL_MAJOR_TYPE");
               		             	minor=rs_bill.getInt("BILL_MINOR_TYPE_CODE");
               		         	sub_minor=rs_bill.getInt("BILL_SUB_TYPE_CODE");
               		         billType=rs_bill.getString("bill_type");
               		             	}
               		             	
               		             	}catch (Exception e) {
               		             	System.out.println("Bill No--else-->"+e);
									}
               		             	}	else if(sanno==0){
               		             		/*
               		             		System.out.println("sannnooo  equal zero.....");
               		             	//siva hide to chages 2016-07-19
               		             	//	PreparedStatement pqq = con.prepareStatement("update FAS_BILL_REGISTERNEW set PVR_NO=?, PVR_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=? and (PVR_NO=0 or PVR_NO is null) and (PVR_DATE is null)");	
               		             	PreparedStatement pqq = con.prepareStatement("update FAS_MEMO_OF_PAYMENT_MST set PVR_NO=?, PVR_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=? and (PVR_NO=0 or PVR_NO is null) and (PVR_DATE is null)");
        		                	 pqq.setInt(1,paymentno);
        		                	pqq.setDate(2,PaymentDate);
        		                	// pq.setString(2,vocherDate);
        		                	pqq.setInt(3, unitId);
        		                	pqq.setInt(4, officeId);
        		                	pqq.setInt(5, check_memo_Year1);
        		                	pqq.setInt(6, check_memo_Month1);
        		                	pqq.setInt(7, billNo);
        		                	pvinRegister=pqq.executeUpdate();
        		                	System.out.println("pvinRegister --> "+pvinRegister);
        		                	if(pvinRegister>0)
        		                	{
        		                		// con.commit();
             		                	 sendMessage(response, "Records Inserted Successfully",
             		      						"ok", "Cheque_Memo.jsp");
        		                		
        		                	}else{
        		                		
        		                		try {
                 		   					con.rollback();
                 		   				 
    		             		   				} catch (SQLException sqle) {
    		             		   					System.out.println("exception in rollback1 " + sqle);
    		             		   				}	
    		             		   			  sendMessage(response, "Insertion Failed without Sanction","ok", "Cheque_Memo.jsp");
        		                	}
               		             		
               		             	*/
               		             		}    else
               		                {
               		             	   try{
               		             		  // con.rollback();
               		             		   System.out.println("exception in rollback ");
               		             		   }catch(Exception sqle){System.out.println("exception in rollback4 "+sqle);}
               		                    
               		             	  sendMessage(response, "Insertion Failed","ok", "Cheque_Memo.jsp");
               		                } 
               						
             		                	}	
             		                	}
               		         	System.out.println("major-->"+major+"   "+minor+"   "+sub_minor +" billtyep0"+billType);
               		         	PreparedStatement pq2 =null;
               		         	String sub_qry="";
               		         if(major==2 && minor==1 && billType.equalsIgnoreCase("WOSP"))
               		         {
               		        	hrm_billupdate=1;
               		        	System.out.println("hrm_billupdate *** "+hrm_billupdate);
               		         }else{

               		             for (int kz = 0; kz <bill_nonw.length; kz++) { 
               		          	billNo=Integer.parseInt(bill_nonw[kz]) ;
               		              String[] sd1n1=Grid_bill_date1[kz].split("/");
	                                Calendar c1141=new GregorianCalendar(Integer.parseInt(sd1n1[2]),Integer.parseInt(sd1n1[1])-1,Integer.parseInt(sd1n1[0]));
	                                 java.util.Date d13s=c1141.getTime();
	                                 Date pay_voudate1 = new Date(d13s.getTime());
               		             

      		                	/*	PreparedStatement pq1new = con.prepareStatement("select SANCTION_PROCEEDING_NO from FAS_MEMO_OF_PAYMENT_MST WHERE ACCOUNTING_UNIT_ID    =? AND ACCOUNTING_FOR_OFFICE_ID=?"
      		                				+ " AND CASHBOOK_YEAR =? AND CASHBOOK_MONTH =?"
      		                			+" and BILL_DATE =?"
      		                		
      		                				+ " AND BILL_NO=?");*/
	                                 PreparedStatement pq1new = con.prepareStatement( "SELECT Sanction_Proceeding_No " +
		                				"FROM FAS_MEMO_OF_PAYMENT_MST m, " +
		                				"  FAS_MEMO_OF_PAYMENT_TRN t " +
		                				"WHERE M.Accounting_Unit_Id    =T.Accounting_Unit_Id " +
		                				"AND M.Accounting_For_Office_Id=T.Accounting_For_Office_Id " +
		                				"AND M.Cashbook_Month          = T.Cashbook_Month " +
		                				"AND M.Cashbook_Year           =T.Cashbook_Year " +
		                				"AND m.bill_no                 =t.bill_no " +
		                				"AND T.Payment_Unit            =? " +
		                				"AND T.Payment_Office          =? " +
		                				"AND M.Cashbook_Year           =? " +
		                				"AND M.Cashbook_Month          =? " +
		                				"AND M.BILL_DATE          =? " +
		                				"AND m.BILL_NO                 =?" );
      		                	/*	pq1new.setInt(1, unitId);
      		                		pq1new.setInt(2, officeId);*/

      		                		pq1new.setInt(1, ori_uint);
      		                		pq1new.setInt(2, ori_office);
      		                		pq1new.setInt(3, check_memo_Year1);
      		                		pq1new.setInt(4, check_memo_Month1);
      		                		pq1new.setDate(5,pay_voudate1);
              		            
      		                		pq1new.setInt(6, billNo);
      		                		System.out.println("************************billNo***************************"+billNo);
        		                	ResultSet rss1new=pq1new.executeQuery();
        		                
        		                	while(rss1new.next()){
        		                		sanno=rss1new.getInt("SANCTION_PROCEEDING_NO");
        		                	
                		                	
               		             	
               		        	 
               		             	if(major==2 && minor==2 && sub_minor==1){
               		             		if(memoType==2){
               		             		sub_qry= " AND EMPLOYEE_ID= "+payeeCode;
               		             		}else{
               		             		sub_qry="";
               		             		}
               		             
							String qry = "update SLS_SANCTIONS_BILLS_LINK_MST1 SET VOUCHER_NO ="+paymentno+", VOUCHER_DATE=to_date('"+PaymentDate+"','dd-mm-yy')," +
									" CASHBOOK_YEAR="+cashYear+", CASHBOOK_MONTH ="+cashMonth+", PROCESS_FLOW_ID='FR'" +
									" WHERE SANCTION_PROC_OFFICE_ID="+ori_office+" AND HRMS_SANCTION_ID=" +sanno+
									 sub_qry+" AND (VOUCHER_NO=0 OR VOUCHER_NO IS NULL)" +
									" AND (VOUCHER_DATE IS NULL)";
							
							System.out.println(qry);
							
               		         	 pq2 = con.prepareStatement("update SLS_SANCTIONS_BILLS_LINK_MST1 SET VOUCHER_NO =?, VOUCHER_DATE=?, CASHBOOK_YEAR=?, CASHBOOK_MONTH =?, PROCESS_FLOW_ID='FR' WHERE " +
               		         	 		//"SANCTION_PROC_OFFICE_ID=?" +
               		         		    " OFFICE_ID=?" +
               		         	 		" AND HRMS_SANCTION_ID=?::numeric "+sub_qry);
						
						
							
             		                	}else{
                 		                		/* pq2 = con.prepareStatement("update HRM_SANCTIONS_BILLS_LINK_MST SET VOUCHER_NO =?, VOUCHER_DATE=?, CASHBOOK_YEAR=?, CASHBOOK_MONTH =?, PROCESS_FLOW_ID ='FR' WHERE" +
                 		                		 		" (SANCTION_PROC_OFFICE_ID=? or " +
                 		                		 		" OFFICE_ID="+ori_office+" )" +
                 		                		 		" AND HRMS_SANCTION_ID=?");*/
                 		                		 
                 		                		 pq2 = con.prepareStatement("update HRM_SANCTIONS_BILLS_LINK_MST SET VOUCHER_NO =?, VOUCHER_DATE=?, CASHBOOK_YEAR=?, CASHBOOK_MONTH =?, PROCESS_FLOW_ID ='FR' WHERE" +
                  		                		 		" (SANCTION_PROC_OFFICE_ID=? or " +
                  		                		 		" OFFICE_ID="+ori_office+" )" +
                  		                		 		" AND HRMS_SANCTION_ID=?::numeric");
                 		                		

             		                		}
               		      
                		                	pq2.setInt(1,paymentno);
                		                	pq2.setDate(2,PaymentDate);
                		                	pq2.setInt(3,cashYear);
                 		                	pq2.setInt(4,cashMonth);
                		                //	pq2.setInt(5,officeId);
                 		                	pq2.setInt(5,ori_office);
                		                	System.out.println("**************************************************");
                		                	String sanno1=Integer.toString(sanno);
                		                	pq2.setString(6,sanno1);System.out.println("sanno not 2 "+sanno);
                		                //	pq2.setInt(7, payeeCode);
                		                	System.out.println("payeeCode not 2 "+payeeCode);
                		                	int pqv=pq2.executeUpdate();
                		                	hrm_billupdate=hrm_billupdate+pqv;
                		                
                		                	System.out.println("hrm_billupdate not 2 "+hrm_billupdate);
             		                		
               		             	}
               		             }
               		         }
               		             	
               		             	
               		             	if(hrm_billupdate>0){
             		                			 con.commit();
             		                			System.out
														.println("Success1");
                     		                	 sendMessage(response, "Records Inserted Successfully",
                     		      						"ok", "Cheque_Memo.jsp");
             		                		}else{
             		                			try {
                         		   					con.rollback();
                         		   				 
            		             		   				} catch (SQLException sqle) {
            		             		   					System.out.println("exception in rollback1 " + sqle);
            		             		   				}	
            		             		   			  sendMessage(response, "Insertion Failed","ok", "Cheque_Memo.jsp");
             		                		}
             		                		
             		                		
             		                		
               		             	//else sanno >0
               		             
               		             	
             		                		
             		                	}
             		                	else
             		                	{
             		                		try {
                     		   					con.rollback();
        		             		   				} catch (SQLException sqle) {
        		             		   					System.out.println("exception in rollback2 " + sqle);
        		             		   				}	
             		                	}
             		                 
             		                 }
             		                 else
             		                 {
             		                	try {
             		   					con.rollback();
		             		   				} catch (SQLException sqle) {
		             		   					System.out.println("exception in rollback3 " + sqle);
		             		   				}
             		                	 sendMessage(response, "Insertion Failed","ok", "Cheque_Memo.jsp");
             		                 }
                 
                
            
//				if (bill_No.length == (serialNo - 1)) {
//					con.commit();
//					 sendMessage(response, "Records Inserted Successfully",
//      						"ok", "cheque_memo.jsp");
//				}

			} catch (Exception e) {
				try {
					con.rollback();
				} catch (SQLException sqle) {
					System.out.println("exception in rollback5 " + sqle);
				}
				e.getStackTrace();

				 sendMessage(response, "Insertion Failed","ok", "Cheque_Memo.jsp");
				e.printStackTrace();
				System.out.println("Exception occur due to " + e);

			}
			/*}
			else if(count!=0)
			{
				 sendMessage(response, "Already Created","ok", "Cheque_Memo.jsp");	
			}*/
		
		}
		
		}
		}
		
		
		
	
	
}
	

	
	private void sendMessage(HttpServletResponse response, String msg,
			String bType, String jsp) {
		try {
			String url = "org/FAS/FAS1/BillVerification/jsps/MessengerOkBack.jsp?message="
					+ msg + "&button=" + bType + "&jspname=" + jsp;
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
