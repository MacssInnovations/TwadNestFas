package Servlets.FAS.FAS1.PaymentEntry.servlets;

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
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.FAS.FAS1.CivilBills.servlets.SL_TYPE_CODE_NAME;

/**
 * Servlet implementation class MemoOfPayment
 */
public class Memo_Of_Pay_Edit extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();

		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null;
		ResultSet rs1 = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null,ps3=null;
		int cashbookYear = 0,up=0;
		String cashbookMonth = null;
		int unitid = 0; int errcode=0;
		String unitname = "";
		int accid = 0;

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
		String strCommand = "";
		String xml = "";

		HttpSession session = request.getSession(false);

		try {

			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		String userid = (String) session.getAttribute("UserId");
		System.out.println("User Id is:" + userid);
		try {
			
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
			System.out.println("strCommand:-" + strCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
				.getAttribute("UserProfile");
		int empid = empProfile.getEmployeeId();
		System.out.println("empid::"+empid);
		String empName = empProfile.getEmployeeName();
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);

		 
         String update_user=(String)session.getAttribute("UserId");
         System.out.println("Updaated_by_userid  ::::"+update_user);
         l=System.currentTimeMillis();
         ts=new Timestamp(l);  
		
		if (strCommand.equalsIgnoreCase("gett")) {

			xml = xml + "<response><command>gett</command>";
			try {
				ps = connection
						.prepareStatement("select SUB_LEDGER_TYPE_CODE,SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES order by SUB_LEDGER_TYPE_DESC");
				rs = ps.executeQuery();
				while (rs.next()) {
					xml = xml + "<flag> success </flag>";
					xml = xml + "<subLedgerTypeCode>"
							+ rs.getString("SUB_LEDGER_TYPE_CODE")
							+ "</subLedgerTypeCode>";
					xml = xml + "<subLedgerTypeDesc>"
							+ rs.getString("SUB_LEDGER_TYPE_DESC")
							+ "</subLedgerTypeDesc>";
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag> failure </flag>";
			}

		} else if (strCommand.equalsIgnoreCase("BillNo")) {

			xml = xml + "<response><command>BillNo</command>";

			String cboAcc_UnitCode1 = request.getParameter("cboAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);
			int plus=0;
			String cboOffice_code1 = request.getParameter("cboOffice_code");
			int cboOffice_code = Integer.parseInt(cboOffice_code1);

			String cboCashBook_Year1 = request.getParameter("cboCashBook_Year");
			int cboCashBook_Year = Integer.parseInt(cboCashBook_Year1);

			String cboCashBook_Month1 = request.getParameter("cboCashBook_Month");
			int cboCashBook_Month = Integer.parseInt(cboCashBook_Month1);
			
			String sancidwith = request.getParameter("sancidwith");
			//System.out.println("sancidwith:::"+sancidwith);
			if(sancidwith.equals("with_sanc")){
				try {

					String su="SELECT BILL_NO FROM FAS_MEMO_OF_PAYMENT_MST WHERE ACCOUNTING_UNIT_ID     = "+cboAcc_UnitCode+
								" AND ACCOUNTING_FOR_OFFICE_ID= "+cboOffice_code+" AND CASHBOOK_YEAR            ="+cboCashBook_Year+" AND CASHBOOK_MONTH           ="+cboCashBook_Month+
						" AND status                   ='L' AND BILL_SCRUTINY      IS NULL ORDER BY BILL_NO ";
					System.out.println("su:::"+su);
					ps = connection.prepareStatement(su);
//					
					rs = ps.executeQuery();
					while (rs.next()) {
						plus++;
							xml = xml + "<billNo>" + rs.getInt("BILL_NO")
									+ "</billNo>";
						}
					if(plus>0)
					{
						xml = xml + "<flag> success </flag>";
					}
					else {
						xml = xml + "<flag> NoData </flag>";
					}
					ps.close();
					rs.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					xml = xml + "<flag> failure </flag>";
				}
			}
			else if(sancidwith.equals("with_out_sanc_GPF")){
				try {

					String su="SELECT BILL_NO FROM FAS_MEMO_OF_PAYMENT_MST WHERE ACCOUNTING_UNIT_ID     = "+cboAcc_UnitCode+
								" AND ACCOUNTING_FOR_OFFICE_ID= "+cboOffice_code+" AND CASHBOOK_YEAR            ="+cboCashBook_Year+" AND CASHBOOK_MONTH           ="+cboCashBook_Month+
						" AND status                   ='L' AND BILL_SCRUTINY      IS NULL ORDER BY BILL_NO ";
					System.out.println("su:::"+su);
					ps = connection.prepareStatement(su);
//					
					rs = ps.executeQuery();
					while (rs.next()) {
						plus++;
							xml = xml + "<billNo>" + rs.getInt("BILL_NO")
									+ "</billNo>";
						}
					if(plus>0)
					{
						xml = xml + "<flag> success </flag>";
					}
					else {
						xml = xml + "<flag> NoData </flag>";
					}
					ps.close();
					rs.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					xml = xml + "<flag> failure </flag>";
				}
			}
			
			else
			{
				try {

					String su = "select BILL_NO from FAS_BILL_REGISTERNEW where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and status='L' and MEMO_ENTRY is null order by BILL_NO";
					ps = connection.prepareStatement(su);
					ps.setInt(1, cboAcc_UnitCode);
					ps.setInt(2, cboOffice_code);
					ps.setInt(3, cboCashBook_Year);
					ps.setInt(4, cboCashBook_Month);
					rs = ps.executeQuery();
					if (rs.next()) {
						String su1 = "select BILL_NO from FAS_BILL_REGISTERNEW where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and status='L' and MEMO_ENTRY is null order by BILL_NO";
						ps1 = connection.prepareStatement(su1);
						ps1.setInt(1, cboAcc_UnitCode);
						ps1.setInt(2, cboOffice_code);
						ps1.setInt(3, cboCashBook_Year);
						ps1.setInt(4, cboCashBook_Month);
						rs1 = ps1.executeQuery();
						xml = xml + "<flag> success </flag>";
						while (rs1.next()) {

							xml = xml + "<billNo>" + rs1.getInt("BILL_NO")
									+ "</billNo>";
						}
					} else {
						xml = xml + "<flag> NoData </flag>";
					}
					ps.close();
					rs.close();
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					xml = xml + "<flag> failure </flag>";
				}
			}
			
		} 
		else if (strCommand.equalsIgnoreCase("getDetails")) {
			String su="";
			xml = xml + "<response><command>getDetails</command>";

			String cboAcc_UnitCode1 = request.getParameter("cboAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cboOffice_code");
			int cboOffice_code = Integer.parseInt(cboOffice_code1);

			String cboCashBook_Year1 = request.getParameter("cboCashBook_Year");
			int cboCashBook_Year = Integer.parseInt(cboCashBook_Year1);

			String cboCashBook_Month1 = request.getParameter("cboCashBook_Month");
			int cboCashBook_Month = Integer.parseInt(cboCashBook_Month1);

			int cboBillNo = Integer.parseInt(request.getParameter("cboBillNo"));
			String sub_q="",sub_main="";
			/*if(cboCashBook_Year>2014 && cboCashBook_Month>3)
			{
				 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
				 sub_main=" Fas_Bill_Register_MasterNEW M, "+
				" 	  Fas_Bill_Register_Transactionw T ";
			}else{
				sub_q = " FAS_BILL_REGISTER_MASTER "; 
				 sub_main=" Fas_Bill_Register_Master M, "+
							" 	  Fas_Bill_Register_Transaction T ";
			}*/
			if (cboCashBook_Year > 2014) {
				if (cboCashBook_Year == 2015 && cboCashBook_Month <= 3) {
					sub_q = " FAS_BILL_REGISTER_MASTER "; 
					 sub_main=" Fas_Bill_Register_Master M, "+
								" 	  Fas_Bill_Register_Transaction T ";
				} else {
					sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
					 sub_main=" Fas_Bill_Register_MasterNEW M, "+
					" 	  Fas_Bill_Register_Transactionw T ";
				}
			} else {
				sub_q = " FAS_BILL_REGISTER_MASTER "; 
				 sub_main=" Fas_Bill_Register_Master M, "+
							" 	  Fas_Bill_Register_Transaction T ";
			}
			String billDate = null;
			String proceedingDate = null;
			String ManproceedingDate = null;
			String agreementDate = null;
			String invoiceDate = null;
			int billMajorType = 0,DEBIT_AC_HEAD_CODE=0;
			int debitAccHead = 0;
			int agreementNo = 0;
			int proceedingNo = 0,f_one=0;
			String Stringdate = null;
			String Stringdate1 = null;
			String Stringdate2 = null;
			String Stringdate3 = null;
			int Major=0,Minor=0,Sub_type=0;
			
			String sancidwith = request.getParameter("sancidwith");
	
			String qry="SELECT BILL_MINOR_TYPE_CODE,BILL_MAJOR_TYPE,BILL_SUB_TYPE_CODE FROM  "+sub_q+
                        " WHERE ACCOUNTING_UNIT_ID     ="+cboAcc_UnitCode+" AND ACCOUNTING_UNIT_OFFICE_ID="+cboOffice_code+" AND CASHBOOK_YEAR            ="+cboCashBook_Year+
                        " AND CASHBOOK_MONTH           ="+cboCashBook_Month+" AND MEMO_ENTRY              IS NULL and status='L' and BILL_NO="+cboBillNo+" ORDER BY BILL_NO";
			//System.out.println("main q:::"+qry);
			try {
				PreparedStatement ps_chk=connection.prepareStatement(qry);
				ResultSet rs_chk=ps_chk.executeQuery();
				while(rs_chk.next()){
					Major=rs_chk.getInt("BILL_MAJOR_TYPE");
					Minor=rs_chk.getInt("BILL_MINOR_TYPE_CODE");
					Sub_type=rs_chk.getInt("BILL_SUB_TYPE_CODE");
					
				}
			} catch (SQLException e1) {
				
				e1.printStackTrace();
			}
			
			try {

				if(sancidwith.equalsIgnoreCase("with_sanc")){
					if(Major==2 && Minor==2 && Sub_type==1 ){
						System.out.println("sls");
						su = " Select M.Bill_Major_Type, "+
						" (select BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where BILL_MAJOR_TYPE_CODE=m.Bill_Major_Type)as type_desc, "+
				"   M.Bill_Date, "+
				" 	  m.Sanction_Proc_No, "+
				" 	  (SELECT distinct s.Sanction_Proc_No "+
				" 	  FROM SLS_SANCTIONS_BILLS_LINK_MST1 s "+
				" 	  WHERE S.HRMS_SANCTION_ID=m.Sanction_Proc_No "+
				" 	  )AS Sanc_Id, "+
				" 	  M.Proceeding_Recd_Date, "+
				" 	  m.Total_Sanctioned_Amount, "+
				" 	  T.Account_Head_Code, "+
				" 	  (select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=t.Account_Head_Code)as head_desc, "+
				" t.Payee_Type_Code, "+
				"  (select S.Sub_Ledger_Type_Desc from com_mst_sl_types s where S.Sub_Ledger_Type_Code=t.Payee_Type_Code)as payee_desc,  "+
				//" M.Payee_Code, "+
				" t.Payable_To as Payee_Code," +
				" (select u.employee_name||'-'||u.employee_initial from hrm_mst_employees u where u.employee_id=t.Payable_To)as codedesc,"+
				" TOTAL_BILL_AMOUNT as debit_amt,'DR' as indicator,T.Amount,T.Particulars "	+	
				" 	FROM  "+sub_main+
				" 	WHERE M.Accounting_Unit_Id     =T.Accounting_Unit_Id "+
				" 	AND M.Accounting_Unit_Office_Id=T.Accounting_Unit_Office_Id "+
				" 	AND M.Cashbook_Year            =T.Cashbook_Year "+
				" 	AND M.Cashbook_Month           =T.Cashbook_Month "+
				" 	And M.Bill_No                  =T.Bill_No and m.status='L'"+
				" and(BILL_TYPE               ='WSP' or  BILL_TYPE              is null) "+
				" 	And M.Accounting_Unit_Id       = "+cboAcc_UnitCode+
				" 	And M.Accounting_Unit_Office_Id= "+cboOffice_code+
				" 	And M.Cashbook_Year            = "+cboCashBook_Year+
				" 	And M.Cashbook_Month           = "+cboCashBook_Month+
				" 	AND m.BILL_NO                  ="+cboBillNo;
					}else{
						System.out.println("gpf");
				su = " Select M.Bill_Major_Type, "+
							" (select BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where " +
							" BILL_MAJOR_TYPE_CODE=m.Bill_Major_Type)as type_desc, "+
					"   M.Bill_Date, "+
					" 	  m.Sanction_Proc_No, "+
					" 	  (SELECT s.Sanction_Proc_No "+
					" 	  FROM HRM_SANCTIONS_BILLS_LINK_MST s "+
					" 	  WHERE S.HRMS_SANCTION_ID=m.Sanction_Proc_No "+
					" 	  )AS Sanc_Id, "+
					" 	  M.Proceeding_Recd_Date, "+
					" 	  m.Total_Sanctioned_Amount, "+
					" 	  T.Account_Head_Code, "+
					" 	  (select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=t.Account_Head_Code)" +
					" as head_desc, "+
					" t.Payee_Type_Code, "+
					"   (SELECT S.Sub_Ledger_Type_Desc "+
					" FROM com_mst_sl_types s "+
					" WHERE S.Sub_Ledger_Type_Code=t.Payee_Type_Code "+
					" )AS payee_desc, "+
					" t.PAYABLE_TO as Payee_Code, "+
					" (select v.sl_codename from sl_type_code_name_view v where v.sl_type=t.Payee_Type_Code and v.sl_code=t.PAYABLE_TO)as codedesc, "+
					" TOTAL_BILL_AMOUNT as debit_amt,'DR' as indicator,T.Amount,T.Particulars "	+	
					" 	FROM  "+sub_main+
					" 	WHERE M.Accounting_Unit_Id     =T.Accounting_Unit_Id "+
					" 	AND M.Accounting_Unit_Office_Id=T.Accounting_Unit_Office_Id "+
					" 	AND M.Cashbook_Year            =T.Cashbook_Year "+
					" 	AND M.Cashbook_Month           =T.Cashbook_Month "+
					" 	And M.Bill_No                  =T.Bill_No and m.status='L'"+
					" and(BILL_TYPE               ='WSP' or  BILL_TYPE              is null) "+
					" 	And M.Accounting_Unit_Id       = "+cboAcc_UnitCode+
					" 	And M.Accounting_Unit_Office_Id= "+cboOffice_code+
					" 	And M.Cashbook_Year            = "+cboCashBook_Year+
					" 	And M.Cashbook_Month           = "+cboCashBook_Month+
					" 	AND m.BILL_NO                  ="+cboBillNo;
				
					}
				System.out.println(su);
				}
				else if(sancidwith.equalsIgnoreCase("with_out_sanc_GPF"))//without
				{
					System.out.println("GPPPPPF ");
					if(Major==2 && Minor==1){
						System.out.println("GPF");
						
						su = " Select M.Bill_Major_Type, "+
						" (select BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where " +
						" BILL_MAJOR_TYPE_CODE=m.Bill_Major_Type)as type_desc, "+
				"   M.Bill_Date, m.Sanction_Proc_No,  "+
				" 	  m.Sanction_Proc_No as MANUAL_PROCEEDING_NO, "+
				 "  (SELECT s.Sanction_Proc_No "+
				 "  FROM Hrm_Gpf_Withdrawal_Sanction s "+
				 "  WHERE S.Sanction_Proc_Id=m.Sanction_Proc_No "+
				 "  )AS Sanc_Id, "+
				
				" 	  M.Proceeding_Recd_Date , "+
				" 	  m.Total_Sanctioned_Amount, "+
				" 	  T.Account_Head_Code, "+
				" 	  (select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=t.Account_Head_Code)" +
				" as head_desc, "+
				" t.Payee_Type_Code, "+
				"   (SELECT S.Sub_Ledger_Type_Desc "+
				" FROM com_mst_sl_types s "+
				" WHERE S.Sub_Ledger_Type_Code=t.Payee_Type_Code "+
				" )AS payee_desc, "+
				" t.PAYABLE_TO as Payee_Code, "+
				" (select v.sl_codename from sl_type_code_name_view v where v.sl_type=t.Payee_Type_Code and v.sl_code=t.PAYABLE_TO)as codedesc, "+
				" TOTAL_BILL_AMOUNT as debit_amt,'DR' as indicator,T.Amount,T.Particulars "	+	
				" 	FROM  "+sub_main+
				" 	WHERE M.Accounting_Unit_Id     =T.Accounting_Unit_Id "+
				" 	AND M.Accounting_Unit_Office_Id=T.Accounting_Unit_Office_Id "+
				" 	AND M.Cashbook_Year            =T.Cashbook_Year "+
				" 	AND M.Cashbook_Month           =T.Cashbook_Month "+
				" 	And M.Bill_No                  =T.Bill_No and m.status='L'"+
				"           and m.bill_type='WOSP' "+
				" 	And M.Accounting_Unit_Id       = "+cboAcc_UnitCode+
				" 	And M.Accounting_Unit_Office_Id= "+cboOffice_code+
				" 	And M.Cashbook_Year            = "+cboCashBook_Year+
				" 	And M.Cashbook_Month           = "+cboCashBook_Month+
				" 	AND m.BILL_NO                  ="+cboBillNo;
					}
				}
				
				else if(sancidwith.equalsIgnoreCase("with_out_sanc"))//without
				{
					su = "SELECT M.Bill_Major_Type, "+
					 " (SELECT BILL_MAJOR_TYPE_DESC "+
					 " FROM FAS_BILL_MAJOR_TYPES "+
					 " WHERE BILL_MAJOR_TYPE_CODE=m.Bill_Major_Type "+
					 " )AS type_desc, "+
					 " M.Bill_Date, "+
					 " m.Sanction_Proc_No,m.MANUAL_PROCEEDING_NO, "+
					 "  (SELECT s.Sanction_Proc_No "+
					 "  FROM Hrm_Gpf_Withdrawal_Sanction s "+
					 "  WHERE S.Sanction_Proc_Id=m.Sanction_Proc_No "+
					 "  )AS Sanc_Id,m.MANUAL_PROCEEDING_DATE, "+
					 "  M.Proceeding_Recd_Date, "+
					 " m.Total_Sanctioned_Amount, "+
					 " m.Account_Head_Code, "+
					 " (SELECT ACCOUNT_HEAD_DESC "+
					 " FROM COM_MST_ACCOUNT_HEADS "+
					 " WHERE ACCOUNT_HEAD_CODE=m.Account_Head_Code "+
					 " )AS head_desc, "+
					 " M.Payee_Type_Code, "+
					 " (SELECT PAYEE_CODENAME FROM PAYEE_TYPE_CODE_NAME_VIEW WHERE PAYEE_TYPE= M.Payee_Type_Code and PAYEE_CODE=M.Payee_Code)as codedesc,"+
					 " (SELECT S.Sub_Ledger_Type_Desc "+
					 " FROM com_mst_sl_types s "+
					 " WHERE S.Sub_Ledger_Type_Code=M.Payee_Type_Code "+
					 " )AS payee_desc, "+
					 " M.Payee_Code, "+
					 " (select v.sl_codename from sl_type_code_name_view v where " +
					 " v.sl_type=m.Payee_Type_Code and v.sl_code=M.Payee_Code)as Payable_To, "+
					// "   M.Payable_To, "+
					 " TOTAL_BILL_AMOUNT AS debit_amt, "+
					 " 'DR'              AS indicator, "+
					 " TOTAL_BILL_AMOUNT as Amount, "+
					 "  m.REMARKS as Particulars"+
					 " FROM FAS_BILL_REGISTERNEW m "+
					 " WHERE M.Accounting_Unit_Id       = "+cboAcc_UnitCode+
					 " AND M.Accounting_Unit_Office_Id= "+cboOffice_code+
					 " AND M.Cashbook_Year            = "+cboCashBook_Year+
					 " AND M.Cashbook_Month           = "+cboCashBook_Month+
					 " AND m.BILL_NO                  ="+cboBillNo+" and m.status='L'";
				}
				System.out.println("su========>>>>>>>>>"+su);
				ps = connection.prepareStatement(su);
				rs = ps.executeQuery();
				while (rs.next()) {
					f_one++;
					xml = xml + "<flag>success</flag>";
					Date billDate1 = rs.getDate("BILL_DATE");
					if (billDate1 != null) {
						Stringdate = billDate1.toString();
					} else {
						Stringdate = "00-00-0000";
					}
					String[] ddd = Stringdate.split("-");
				//	String[] ddd1 = Stringdate1.split("-");
					
					int day = Integer.parseInt(ddd[2]);
					int month = Integer.parseInt(ddd[1]);
					int year = Integer.parseInt(ddd[0]);

					
					if (month >= 10) {
						billDate = (day + "/" + month + "/" + year);
					} else {
						billDate = (day + "/0" + month + "/" + year);
					}
					
					Date proceedingDate1 = rs.getDate("PROCEEDING_RECD_DATE");
					if (proceedingDate1 != null) {
						Stringdate1 = proceedingDate1.toString();
					} else {
						Stringdate1 = "00-00-0000";
					}
					String[] ddd1 = Stringdate1.split("-");
					int day1 = Integer.parseInt(ddd1[2]);
					int month1 = Integer.parseInt(ddd1[1]);
					int year1 = Integer.parseInt(ddd1[0]);
					
					

					if (month1 >= 10) {
						proceedingDate = (day1 + "/" + month1 + "/" + year1);
					} else {
						proceedingDate = (day1 + "/0" + month1 + "/" + year1);
					}
				
					String sql_bank="select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' ||coalesce ( br.CITY_TOWN_NAME,'') as bk_br_city "+
                    " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.STATUS='Y' and curr.ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" and curr.MODULE_ID='MF005' and curr.CR_DR_TYPE='CR' "+
                    " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID";
                     System.out.println("::::"+sql_bank);
                   PreparedStatement psbank=connection.prepareStatement(sql_bank);
                   ResultSet rsbank=psbank.executeQuery();
                    while(rsbank.next())
                    {
                        DEBIT_AC_HEAD_CODE=rsbank.getInt("AC_HEAD_CODE");
                     //   System.out.println("aaa::::"+AC_HEAD_CODE);
                    }
                    psbank.close();
                    rsbank.close();
                    
					proceedingNo = rs.getInt("SANCTION_PROC_NO");
					

					System.out.println("debitAccHead========>>>>>>>>>"
							+ debitAccHead);
					
					xml = xml + "<major_type>"+ rs.getInt("Bill_Major_Type") + "</major_type>";
					xml = xml + "<major_type_desc>"+ rs.getString("type_desc") + "</major_type_desc>";
					xml = xml + "<Account_Head_Code>"+ rs.getInt("Account_Head_Code") + "</Account_Head_Code>";
					xml = xml + "<head_desc>"+ rs.getString("head_desc") + "</head_desc>";
					xml = xml + "<billDate>" + billDate + "</billDate>";
					xml = xml + "<proceedingNo>"+ rs.getInt("SANCTION_PROC_NO") + "</proceedingNo>";
					if(!sancidwith.equalsIgnoreCase("with_sanc"))
					{
					xml = xml + "<proceedingid>"+ rs.getString("MANUAL_PROCEEDING_NO") + "</proceedingid>";
					
					}
					xml = xml + "<proceedingDate>" + proceedingDate	+ "</proceedingDate>";
					xml = xml + "<sancamt>"+ rs.getString("TOTAL_SANCTIONED_AMOUNT") + "</sancamt>";
					
					xml = xml + "<debitAccHead>" + DEBIT_AC_HEAD_CODE	+ "</debitAccHead>";
					
					xml = xml + "<Payee_Type_Code>"+ rs.getInt("Payee_Type_Code") + "</Payee_Type_Code>";
					xml = xml + "<payee_desc>"+ rs.getString("payee_desc") + "</payee_desc>";
					xml = xml + "<Payee_Code>"+ rs.getString("Payee_Code") + "</Payee_Code>";
					xml = xml + "<code_desc>"+ rs.getString("codedesc") + "</code_desc>";
					xml = xml + "<debit_amt>"+ rs.getFloat("debit_amt") + "</debit_amt>";
					xml = xml + "<indicator>"+ rs.getString("indicator") + "</indicator>";
					xml = xml + "<amount>"+ rs.getFloat("Amount") + "</amount>";
					xml = xml + "<Particulars>"+ rs.getString("Particulars") + "</Particulars>";
			
				}
				if(f_one>0)
				{
					xml = xml + "<flag>success</flag>";
				}
				else {
					xml = xml + "<flag>NoData</flag>";
				}

				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}

		} else if (strCommand.equalsIgnoreCase("Add")) {

			xml = "<response><command>add</command>";

			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cboOffice_code = Integer.parseInt(cboOffice_code1);

			String cboCashBook_Year1 = request.getParameter("cboCashBook_Year");
			int cboCashBook_Year = Integer.parseInt(cboCashBook_Year1);

			String cboCashBook_Month1 = request
					.getParameter("cboCashBook_Month");
			int cboCashBook_Month = Integer.parseInt(cboCashBook_Month1);

			int cboBillNo = Integer.parseInt(request.getParameter("cboBillNo"));

			int txtAccountHeadCode = Integer.parseInt(request
					.getParameter("txtAccountHeadCode"));

			int txtInvoiceNo = Integer.parseInt(request
					.getParameter("txtInvoiceNo"));

			String txtInvoiceDate = request.getParameter("txtInvoiceDate");

			java.sql.Date InvoiceDate = null;
			java.util.GregorianCalendar c;
			String[] sd = request.getParameter("txtInvoiceDate").split("/");
			c = new java.util.GregorianCalendar(Integer.parseInt(sd[2]),
					Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c.getTime();
			InvoiceDate = new Date(d.getTime());

			float txtInvoiceAmount = Float.parseFloat(request
					.getParameter("txtInvoiceAmount"));

			int txtAgreementNo = Integer.parseInt(request
					.getParameter("txtAgreementNo"));

			String txtAgreementDate = request.getParameter("txtAgreementDate");

			java.sql.Date AgreementDate = null;
			String[] sd1 = request.getParameter("txtAgreementDate").split("/");
			c = new java.util.GregorianCalendar(Integer.parseInt(sd1[2]),
					Integer.parseInt(sd1[1]) - 1, Integer.parseInt(sd1[0]));
			java.util.Date d1 = c.getTime();
			AgreementDate = new Date(d1.getTime());

			float txtAgreementAmount = Float.parseFloat(request
					.getParameter("txtAgreementAmount"));

			int cmbSL_type = Integer.parseInt(request
					.getParameter("cmbSL_type"));

			int cmbSL_Code = Integer.parseInt(request
					.getParameter("cmbSL_Code"));

			String txtCRDRIndicator = request.getParameter("txtCRDRIndicator");

			float txtAmount = Float.parseFloat(request
					.getParameter("txtAmount"));

			String rdoIfFirstParty = request.getParameter("rdoIfFirstParty");

			String txtPayeeType = request.getParameter("txtPayeeType");

			int txtPayeeCode = Integer.parseInt(request
					.getParameter("txtPayeeCode"));

			String mtxtRemarks1 = request.getParameter("mtxtRemarks1");

			int rowcount = Integer.parseInt(request.getParameter("rowcount"));

			int i = 0, i1 = 1;
			try {

				if (rowcount == 0) {

					i = i1;

				} else {
					i = i1 + rowcount;
				}

				System.out.println("iiiiiiiii--------" + i);

			} catch (Exception e) {
				e.printStackTrace();

			}

			xml = xml + "<flag>success</flag>";
			xml = xml + "<cboAcc_UnitCode>" + cboAcc_UnitCode
					+ "</cboAcc_UnitCode>";
			xml = xml + "<cboOffice_code>" + cboOffice_code
					+ "</cboOffice_code>";
			xml = xml + "<cboCashBook_Year>" + cboCashBook_Year
					+ "</cboCashBook_Year>";
			xml = xml + "<cboCashBook_Month>" + cboCashBook_Month
					+ "</cboCashBook_Month>";
			xml = xml + "<cboBillNo>" + cboBillNo + "</cboBillNo>";
			xml = xml + "<txtAccountHeadCode>" + txtAccountHeadCode
					+ "</txtAccountHeadCode>";
			xml = xml + "<slNo>" + i + "</slNo>";
			xml = xml + "<txtInvoiceNo>" + txtInvoiceNo + "</txtInvoiceNo>";
			xml = xml + "<InvoiceDate>" + txtInvoiceDate + "</InvoiceDate>";
			xml = xml + "<txtInvoiceAmount>" + txtInvoiceAmount
					+ "</txtInvoiceAmount>";
			xml = xml + "<txtAgreementNo>" + txtAgreementNo
					+ "</txtAgreementNo>";
			xml = xml + "<AgreementDate>" + txtAgreementDate
					+ "</AgreementDate>";
			xml = xml + "<txtAgreementAmount>" + txtAgreementAmount
					+ "</txtAgreementAmount>";

			xml = xml + "<cmbSL_type>" + cmbSL_type + "</cmbSL_type>";
			xml = xml + "<cmbSL_Code>" + cmbSL_Code + "</cmbSL_Code>";
			xml = xml + "<txtCRDRIndicator>" + txtCRDRIndicator
					+ "</txtCRDRIndicator>";
			xml = xml + "<txtAmount>" + txtAmount + "</txtAmount>";
			xml = xml + "<rdoIfFirstParty>" + rdoIfFirstParty
					+ "</rdoIfFirstParty>";
			xml = xml + "<txtPayeeType>" + txtPayeeType + "</txtPayeeType>";

			xml = xml + "<txtPayeeCode>" + txtPayeeCode + "</txtPayeeCode>";
			xml = xml + "<mtxtRemarks1>" + mtxtRemarks1 + "</mtxtRemarks1>";

		} else if (strCommand.equalsIgnoreCase("addMst")) {

			xml = "<response><command>addMst</command>";

			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cboOffice_code = Integer.parseInt(cboOffice_code1);

			String cboCashBook_Year1 = request.getParameter("cboCashBook_Year");
			int cboCashBook_Year = Integer.parseInt(cboCashBook_Year1);

			String cboCashBook_Month1 = request.getParameter("cboCashBook_Month");
			int cboCashBook_Month = Integer.parseInt(cboCashBook_Month1);

			int cboBillNo = Integer.parseInt(request.getParameter("cboBillNo"));
			int rowcount = Integer.parseInt(request.getParameter("rowcount"));
			//System.out.println("rowcount::"+rowcount);
			String txtBillMajorType1 = request.getParameter("txtBillMajorType");
			int txtBillMajorType2=Integer.parseInt(txtBillMajorType1);
			String sancidwith = request.getParameter("sancidwith");
			
			int txtBillMajorType = 0,db_journal=0;
			
			int db1 = 0, db2 = 0,dbcount=0;
			int txtJournalVou_No=0;
			double dep_rate = 0;int cheq_no=0;String cheq_date="";
			  String txtCB_REF_TYPE="";
			int slno=0;
			try {
				ps = connection
						.prepareStatement("select BILL_MAJOR_TYPE_CODE from FAS_BILL_MAJOR_TYPES where BILL_MAJOR_TYPE_DESC=?");
				ps.setString(1, txtBillMajorType1);
				rs = ps.executeQuery();
				while (rs.next()) {

					txtBillMajorType = rs.getInt("BILL_MAJOR_TYPE_CODE");

				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag1> failure </flag1>";
			}
			System.out.println("txtBillMajorType1:::::"+txtBillMajorType1);
			int txtSanctionProceedingNo = Integer.parseInt(request.getParameter("txtSanctionProceedingNo"));
		//	String txtSanctionProceedingid =request.getParameter("txtSanctionProceedingid");
			
			java.sql.Date BillDate = null;
			java.util.GregorianCalendar c;
			String[] sd = request.getParameter("txtBillDate").split("/");
			c = new java.util.GregorianCalendar(Integer.parseInt(sd[2]),
					Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c.getTime();
			BillDate = new Date(d.getTime());

			float txtSanctionedAmount = Float.parseFloat(request.getParameter("txtSanctionedAmount"));

			String txtDebitAccountHead1 = request.getParameter("txtDebitAccountHead");
			int txtDebitAccountHead2 = Integer.parseInt(txtDebitAccountHead1);
			int txtDebitAccountHead = 0;
			try {
				ps = connection
						.prepareStatement("select ACCOUNT_HEAD_CODE from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_DESC=?");
				ps.setString(1, txtDebitAccountHead1);
				rs = ps.executeQuery();
				while (rs.next()) {

					txtDebitAccountHead = rs.getInt("ACCOUNT_HEAD_CODE");

				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag2> failure </flag2>";
			}
			//System.out.println("two:::::");
			java.sql.Date SanctionProceedingDate = null;
			String[] sd1 = request.getParameter("txtSanctionProceedingDate")
					.split("/");
			c = new java.util.GregorianCalendar(Integer.parseInt(sd1[2]),
					Integer.parseInt(sd1[1]) - 1, Integer.parseInt(sd1[0]));
			java.util.Date d1 = c.getTime();
			SanctionProceedingDate = new Date(d1.getTime());
       //System.out.println("SanctionProceedingDate "+SanctionProceedingDate);
		/*	int cmbMas_SL_type = Integer.parseInt(request
					.getParameter("cmbMas_SL_type"));

			int cmbMas_SL_Code = Integer.parseInt(request
					.getParameter("cmbMas_SL_Code")); */

			float txtDebitAmount = Float.parseFloat(request
					.getParameter("txtDebitAmount"));

			String mtxtRemarks = request.getParameter("mtxtRemarks");
			String ljv = request.getParameter("ljv");
			System.out.println("ljv yes or no:::"+ljv);
			/*String al = request.getParameter("al2");
			System.out.println("al:::::"+al); */
			String[] al1 = request.getParameter("al").split(",");
			System.out.println("al:::::"+al1.length);
			
			try {
				connection.setAutoCommit(false);
				if(ljv.equals("noljv"))
				{
				try {
					ps2 = connection
							.prepareStatement("select BILL_NO from fas_memo_of_payment_mst where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=? and status='L'");
					ps2.setInt(1, cboAcc_UnitCode);
					ps2.setInt(2, cboOffice_code);
					ps2.setInt(3, cboCashBook_Year);
					ps2.setInt(4, cboCashBook_Month);
					ps2.setInt(5, cboBillNo);
					results = ps2.executeQuery();
					if (results.next()) {
						xml = xml + "<flagE>Exist</flagE>";
					} 
					else {
						int k=0;
						for(int m=0;m<rowcount;m++) {
							
							System.out.println("again"+al1[k]);
							int txtAccountHeadCode = Integer.parseInt(al1[k]);
							System.out.println(txtAccountHeadCode);

							int cmbSL_type = Integer.parseInt(al1[k + 1]);
							System.out.println(cmbSL_type);
					
							int cmbSL_Code = Integer.parseInt(al1[k + 2]);
							System.out.println(cmbSL_Code);
							
							int txtInvoiceNo = Integer.parseInt(al1[k + 3]);
							System.out.println(txtInvoiceNo);
							
							
							String txtInvoiceDate = al1[k + 4];
							System.out.println(txtInvoiceDate);

							java.sql.Date InvoiceDate = null;
							java.util.GregorianCalendar c5;
							if(!txtInvoiceDate.equals("0"))
							{
							String[] sd5 = txtInvoiceDate.split("/");
							c5 = new java.util.GregorianCalendar(Integer
									.parseInt(sd5[2]),
									Integer.parseInt(sd5[1]) - 1, Integer
											.parseInt(sd5[0]));
							java.util.Date d5 = c5.getTime();
							InvoiceDate = new Date(d5.getTime());
						    }
						
							float txtInvoiceAmount = Float
									.parseFloat(al1[k + 5]);

							int txtAgreementNo = Integer.parseInt(al1[k + 6]);

							String txtAgreementDate = al1[k + 7];
							System.out.println("txtAgreementDate:::"+txtAgreementDate);
							java.sql.Date AgreementDate = null;
							if(txtAgreementDate.equals("0"))
							{
								AgreementDate = null;
							}
							else{
								System.out.println("not zero");
							String[] sd15 = txtAgreementDate.split("/");
							c = new java.util.GregorianCalendar(Integer
									.parseInt(sd15[2]), Integer
									.parseInt(sd15[1]) - 1, Integer
									.parseInt(sd15[0]));
							java.util.Date d15 = c.getTime();
							AgreementDate = new Date(d15.getTime());
							}
							System.out.println("txtAgreementAmount::::"+al1[k + 8]);
							float txtAgreementAmount = Float
									.parseFloat(al1[k + 8]);
							//xml = xml + "<flag1>failure</flag1>";
							String txtCRDRIndicator = al1[k + 9];

							float txtAmount = Float.parseFloat(al1[k + 10]);
							String rdoIfFirstParty = al1[k + 11];
							System.out.println("rdoIfFirstParty::::"+al1[k + 11]);
							

							String txtPayeeType = al1[k + 12];

							int txtPayeeCode = Integer.parseInt(al1[k + 13]);

							String mtxtRemarks1 = al1[k + 14];
							System.out.println("mtxtRemarks1::::"+al1[k + 14]);
							int i = 1, i1 = 0;
							slno++;
							try {
								ps1 = connection.prepareStatement("insert into FAS_MEMO_OF_PAYMENT_TRN(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,BILL_NO,SL_NO,ACCOUNT_HEAD_CODE,INVOICE_NO,INVOICE_DATE,INVOICE_AMOUNT,AGREEMENT_NO,AGREEMENT_DATE,AGREEMENT_AMOUNT,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,CR_DR_INDICATOR,AMOUNT,FIRST_PARTY,PAYEE_TYPE_CODE,PAYEE_CODE,PARTICULARS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
								ps1.setInt(1, cboAcc_UnitCode);
								ps1.setInt(2, cboOffice_code);
								ps1.setInt(3, cboCashBook_Year);
								ps1.setInt(4, cboCashBook_Month);
								ps1.setInt(5, cboBillNo);
								ps1.setInt(6, slno);
								ps1.setInt(7, txtAccountHeadCode);
								ps1.setInt(8, txtInvoiceNo);
								ps1.setDate(9, InvoiceDate);
								ps1.setFloat(10, txtInvoiceAmount);
								ps1.setInt(11, txtAgreementNo);
								ps1.setDate(12, AgreementDate);
								ps1.setFloat(13, txtAgreementAmount);
								ps1.setInt(14, cmbSL_type);
								ps1.setInt(15, cmbSL_Code);
								ps1.setString(16, txtCRDRIndicator.substring(0,2));
								ps1.setFloat(17, txtAmount);
								ps1.setString(18, rdoIfFirstParty);
								ps1.setString(19, txtPayeeType);
								ps1.setInt(20, txtPayeeCode);
								ps1.setString(21, mtxtRemarks1);
								ps1.setInt(22, empid);
								ps1.setTimestamp(23, ts);
								db1 = ps1.executeUpdate();
								System.out.println("db1:::"+db1);
								if(db1>0)
								{
									k = k + 15;
									dbcount++;
								}
							
								System.out.println("slno"+slno);

							} catch (Exception e) {
								xml = xml + "<flag>failure</flag>";
								connection.rollback();
								e.printStackTrace();
							}
						}
						if(dbcount==rowcount)
						{
							
						System.out.println("master");
						
					
						try {
							ps = connection.prepareStatement("insert into FAS_MEMO_OF_PAYMENT_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,BILL_NO,BILL_DATE,BILL_MAJOR_TYPE_CODE,SANCTION_PROCEEDING_NO,SANCTION_PROCEEDING_DATE,DR_ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,AMOUNT,REMARKS,UPDATED_BY_USERID,UPDATED_DATE,SANCTIONED_AMOUNT,STATUS) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
							ps.setInt(1, cboAcc_UnitCode);
							ps.setInt(2, cboOffice_code);
							ps.setInt(3, cboCashBook_Year);
							ps.setInt(4, cboCashBook_Month);
							ps.setInt(5, cboBillNo);
							ps.setDate(6, BillDate);
							ps.setInt(7, txtBillMajorType2);
							ps.setInt(8, txtSanctionProceedingNo);
							ps.setDate(9, SanctionProceedingDate);
							ps.setInt(10, txtDebitAccountHead2);
//							ps.setInt(11, cmbMas_SL_type);
//							ps.setInt(12, cmbMas_SL_Code);
							ps.setInt(11, 0);
							ps.setInt(12, 0);
							ps.setFloat(13, txtDebitAmount);
							ps.setString(14, mtxtRemarks);
							ps.setInt(15, empid);
							ps.setTimestamp(16, ts);
							ps.setFloat(17, txtSanctionedAmount);
							ps.setString(18, "L");
							db2=ps.executeUpdate();
				

						} catch (Exception e) {
							connection.rollback();
							xml = xml + "<flag1>failure</flag1>";
							e.printStackTrace();
						}
					//	System.out.println("db2 "+db2);
						if(db2>0)
						{
							
							try{
								if(sancidwith.equals("with_sanc"))
								{
								ps3 = connection.prepareStatement("update FAS_BILL_REGISTER_MASTER set MEMO_ENTRY='Y',MEMO_UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=? and SANCTION_PROC_NO=?");
								ps3.setTimestamp(1, ts);
								ps3.setInt(2, cboAcc_UnitCode);
								ps3.setInt(3, cboOffice_code);
								ps3.setInt(4, cboCashBook_Year);
								ps3.setInt(5, cboCashBook_Month);
								ps3.setInt(6, cboBillNo);
								ps3.setInt(7, txtSanctionProceedingNo);
								}else if(sancidwith.equals("with_out_sanc_GPF"))
								{
									ps3 = connection.prepareStatement("update FAS_BILL_REGISTER_MASTER set MEMO_ENTRY='Y',MEMO_UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=? and SANCTION_PROC_NO=?");
									ps3.setTimestamp(1, ts);
									ps3.setInt(2, cboAcc_UnitCode);
									ps3.setInt(3, cboOffice_code);
									ps3.setInt(4, cboCashBook_Year);
									ps3.setInt(5, cboCashBook_Month);
									ps3.setInt(6, cboBillNo);
									ps3.setInt(7, txtSanctionProceedingNo);
									}
								else if(sancidwith.equals("with_out_sanc"))
								{
									ps3 = connection.prepareStatement("update FAS_BILL_REGISTERNEW set MEMO_ENTRY='Y',MEMO_UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=? ");
									ps3.setTimestamp(1, ts);
									ps3.setInt(2, cboAcc_UnitCode);
									ps3.setInt(3, cboOffice_code);
									ps3.setInt(4, cboCashBook_Year);
									ps3.setInt(5, cboCashBook_Month);
									ps3.setInt(6, cboBillNo);
								//	ps3.setString(7, txtSanctionProceedingid);
									//
								}
								//System.out.println("txtSanctionProceedingid "+txtSanctionProceedingid);
								up=ps3.executeUpdate();
								//System.out.println("up "+up);
								if(up>0)
								{
									db_journal=1;
								}
							}
							catch(Exception eee)
							{
								System.out.println("eee::::"+eee);
							}
						}
						else
						{
							xml = xml + "<flag1>failure</flag1>";
						}
					}
						else
						{
							xml = xml + "<flag1>failure</flag1>";
						}
						
					}

				} catch (Exception e) {
					e.printStackTrace();
					xml = xml + "<flagE>failure</flagE>";
				}
			}
				
			else if(ljv.equals("yesljv"))
			{

				try {
					ps2 = connection
							.prepareStatement("select BILL_NO from FAS_MEMO_OF_PAYMENT_TRN where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=?");
					ps2.setInt(1, cboAcc_UnitCode);
					ps2.setInt(2, cboOffice_code);
					ps2.setInt(3, cboCashBook_Year);
					ps2.setInt(4, cboCashBook_Month);
					ps2.setInt(5, cboBillNo);
					results = ps2.executeQuery();
					if (results.next()) {
						xml = xml + "<flagE>Exist</flagE>";
					} 
					else {
						int k=0;
						for(int m=0;m<rowcount;m++) {
							
							System.out.println("again");
							int txtAccountHeadCode = Integer.parseInt(al1[k]);
							System.out.println(txtAccountHeadCode);

							int cmbSL_type = Integer.parseInt(al1[k + 1]);
							System.out.println(cmbSL_type);
					
							int cmbSL_Code = Integer.parseInt(al1[k + 2]);
							System.out.println(cmbSL_Code);
							
							int txtInvoiceNo = Integer.parseInt(al1[k + 3]);
							System.out.println(txtInvoiceNo);
							
							
							String txtInvoiceDate = al1[k + 4];
							System.out.println(txtInvoiceDate);

							java.sql.Date InvoiceDate = null;
							java.util.GregorianCalendar c5;
							if(!txtInvoiceDate.equals("0"))
							{
							String[] sd5 = txtInvoiceDate.split("/");
							c5 = new java.util.GregorianCalendar(Integer
									.parseInt(sd5[2]),
									Integer.parseInt(sd5[1]) - 1, Integer
											.parseInt(sd5[0]));
							java.util.Date d5 = c5.getTime();
							InvoiceDate = new Date(d5.getTime());
						    }
						
							float txtInvoiceAmount = Float
									.parseFloat(al1[k + 5]);

							int txtAgreementNo = Integer.parseInt(al1[k + 6]);

							String txtAgreementDate = al1[k + 7];
							System.out.println("txtAgreementDate:::"+txtAgreementDate);
							java.sql.Date AgreementDate = null;
							if(txtAgreementDate.equals("0"))
							{
								AgreementDate = null;
							}
							else{
								System.out.println("not zero");
							String[] sd15 = txtAgreementDate.split("/");
							c = new java.util.GregorianCalendar(Integer
									.parseInt(sd15[2]), Integer
									.parseInt(sd15[1]) - 1, Integer
									.parseInt(sd15[0]));
							java.util.Date d15 = c.getTime();
							AgreementDate = new Date(d15.getTime());
							}
							System.out.println("txtAgreementAmount::::"+al1[k + 8]);
							float txtAgreementAmount = Float
									.parseFloat(al1[k + 8]);
							//xml = xml + "<flag1>failure</flag1>";
							String txtCRDRIndicator = al1[k + 9];

							float txtAmount = Float.parseFloat(al1[k + 10]);
							String rdoIfFirstParty = al1[k + 11];
							System.out.println("rdoIfFirstParty::::"+al1[k + 11]);
							

							String txtPayeeType = al1[k + 12];

							int txtPayeeCode = Integer.parseInt(al1[k + 13]);

							String mtxtRemarks1 = al1[k + 14];
							System.out.println("mtxtRemarks1::::"+al1[k + 14]);
							int i = 1, i1 = 0;
							slno++;
							try {
								ps1 = connection.prepareStatement("insert into FAS_MEMO_OF_PAYMENT_TRN(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,BILL_NO,SL_NO,ACCOUNT_HEAD_CODE,INVOICE_NO,INVOICE_DATE,INVOICE_AMOUNT,AGREEMENT_NO,AGREEMENT_DATE,AGREEMENT_AMOUNT,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,CR_DR_INDICATOR,AMOUNT,FIRST_PARTY,PAYEE_TYPE_CODE,PAYEE_CODE,PARTICULARS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
								ps1.setInt(1, cboAcc_UnitCode);
								ps1.setInt(2, cboOffice_code);
								ps1.setInt(3, cboCashBook_Year);
								ps1.setInt(4, cboCashBook_Month);
								ps1.setInt(5, cboBillNo);
								ps1.setInt(6, slno);
								ps1.setInt(7, txtAccountHeadCode);
								ps1.setInt(8, txtInvoiceNo);
								ps1.setDate(9, InvoiceDate);
								ps1.setFloat(10, txtInvoiceAmount);
								ps1.setInt(11, txtAgreementNo);
								ps1.setDate(12, AgreementDate);
								ps1.setFloat(13, txtAgreementAmount);
								ps1.setInt(14, cmbSL_type);
								ps1.setInt(15, cmbSL_Code);
								ps1.setString(16, txtCRDRIndicator.substring(0,2));
								ps1.setFloat(17, txtAmount);
								ps1.setString(18, rdoIfFirstParty);
								ps1.setString(19, txtPayeeType);
								ps1.setInt(20, txtPayeeCode);
								ps1.setString(21, mtxtRemarks1);
								ps1.setInt(22, empid);
								ps1.setTimestamp(23, ts);
								db1 = ps1.executeUpdate();
								System.out.println("db1:::"+db1);
								if(db1>0)
								{
									k = k + 15;
									dbcount++;
								}
							
								System.out.println("slno"+slno);

							} catch (Exception e) {
								xml = xml + "<flag>failure</flag>";
								connection.rollback();
								e.printStackTrace();
							}
						}
						if(dbcount==rowcount)
						{
							
						System.out.println("master");
						try {
							ps = connection.prepareStatement("insert into FAS_MEMO_OF_PAYMENT_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,BILL_NO,BILL_DATE,BILL_MAJOR_TYPE_CODE,SANCTION_PROCEEDING_NO,SANCTION_PROCEEDING_DATE,DR_ACCOUNT_HEAD_CODE,SUB_LEDGER_TYPE_CODE,SUB_LEDGER_CODE,AMOUNT,REMARKS,UPDATED_BY_USERID,UPDATED_DATE,SANCTIONED_AMOUNT,STATUS) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
							ps.setInt(1, cboAcc_UnitCode);
							ps.setInt(2, cboOffice_code);
							ps.setInt(3, cboCashBook_Year);
							ps.setInt(4, cboCashBook_Month);
							ps.setInt(5, cboBillNo);
							ps.setDate(6, BillDate);
							ps.setInt(7, txtBillMajorType);
							ps.setInt(8, txtSanctionProceedingNo);
							ps.setDate(9, SanctionProceedingDate);
							ps.setInt(10, txtDebitAccountHead);
							ps.setInt(11, 0);
							ps.setInt(12, 0);
							ps.setFloat(13, txtDebitAmount);
							ps.setString(14, mtxtRemarks);
							ps.setInt(15, empid);
							ps.setTimestamp(16, ts);
							ps.setFloat(17, txtSanctionedAmount);
							ps.setString(18, "L");
							db2=ps.executeUpdate();
				

						} catch (Exception e) {
							connection.rollback();
							xml = xml + "<flag1>failure</flag1>";
							e.printStackTrace();
						}
						if(db2>0)
						{
							System.out.println("journal table");

                     	   String txtMode_of_creat = "A", txtCreat_By_Module = "LJV";
                     	 String remar="Cheque Memo Journal";
                            System.out.println("inside proc");
//                           CallableStatement cs =connection.prepareCall("{call TEST_JOURNAL_MASTER_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
//                            cs.setInt(1, cboAcc_UnitCode);
//                            cs.setInt(2, cboOffice_code);
//                            cs.setInt(3,cboCashBook_Year);
//                            cs.setInt(4,cboCashBook_Month);
//                            cs.setInt(5, txtJournalVou_No);
//                            cs.setDate(6,BillDate);
//                            cs.setInt(7, 0);
//                            cs.setInt(8, 0);
////                            cs.setInt(7, cmbMas_SL_type);
////                            cs.setInt(8, cmbMas_SL_Code);
//                            cs.setDouble(9, dep_rate);
//                            cs.setInt(10, cheq_no);
//                            cs.setString(11, cheq_date);
//                            cs.setString(12, txtCB_REF_TYPE);
//                            cs.setInt(13, rowcount);
//                            cs.setString(14,mtxtRemarks);
//                            cs.setString(15, txtMode_of_creat);
//                            cs.setString(16, txtCreat_By_Module);
//                            cs.setString(17, "insert");
//                            cs.registerOutParameter(5, java.sql.Types.NUMERIC);
//                            cs.registerOutParameter(18, java.sql.Types.NUMERIC);
//                            cs.setString(19, update_user);
//                            cs.setTimestamp(20, ts);
//                          //  cs.setInt(21,supplement_no);
//                            System.out.println("b4 exe ");
//                            cs.execute();
//                            txtJournalVou_No = cs.getInt(5);
//                            errcode = cs.getInt(18);
                            
                            CallableStatement cs =connection.prepareCall("call TEST_JOURNAL_MASTER_PROC(?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?,?::numeric,?::numeric,?::numeric,?::numeric,?,?,?::numeric,?,?,?,?,?::numeric,?,?)");
                            cs.setInt(1, cboAcc_UnitCode);
                            cs.setInt(2, cboOffice_code);
                            cs.setInt(3,cboCashBook_Year);
                            cs.setInt(4,cboCashBook_Month);
                            cs.setInt(5, txtJournalVou_No);
                            cs.setDate(6,BillDate);
                            cs.setInt(7, 0);
                            cs.setInt(8, 0);
//                            cs.setInt(7, cmbMas_SL_type);
//                            cs.setInt(8, cmbMas_SL_Code);
                            cs.setDouble(9, dep_rate);
                            cs.setInt(10, cheq_no);
                            cs.setString(11, cheq_date);
                            cs.setString(12, txtCB_REF_TYPE);
                            cs.setInt(13, rowcount);
                            cs.setString(14,mtxtRemarks);
                            cs.setString(15, txtMode_of_creat);
                            cs.setString(16, txtCreat_By_Module);
                            cs.setString(17, "insert");
                            cs.registerOutParameter(5, java.sql.Types.NUMERIC);
                            cs.registerOutParameter(18, java.sql.Types.NUMERIC);
                            cs.setNull(5, java.sql.Types.NUMERIC);
                            cs.setNull(18, java.sql.Types.NUMERIC);
                            cs.setString(19, update_user);
                            cs.setTimestamp(20, ts);
                          //  cs.setInt(21,supplement_no);
                            System.out.println("b4 exe ");
                            cs.execute();
//                            txtJournalVou_No = cs.getInt(5);
//                            errcode = cs.getInt(18);
                            txtJournalVou_No =cs.getBigDecimal(5).intValue();
                           errcode = cs.getBigDecimal(18).intValue();
                            System.out.println("SQLCODE:::" + errcode);
                            if (errcode != 0) {
                            	connection.rollback();
    							xml = xml + "<flag1>failure</flag1>";
    							//e.printStackTrace();
                            } else {
                            	
                            	
                            	int g=0;
                            	int slcount=0;
        						for(int m=0;m<rowcount;m++) {
        							
        							System.out.println("journal");
        							int txtAccountHeadCode = Integer.parseInt(al1[g]);
        							System.out.println(txtAccountHeadCode);

        							int cmbSL_type = Integer.parseInt(al1[g + 1]);
        							System.out.println(cmbSL_type);
        					
        							int cmbSL_Code = Integer.parseInt(al1[g + 2]);
        							System.out.println(cmbSL_Code);
        							
        							int txtInvoiceNo = Integer.parseInt(al1[g + 3]);
        							System.out.println(txtInvoiceNo);
        							
        							
        							String txtInvoiceDate = al1[g + 4];
        							System.out.println(txtInvoiceDate);

        							java.sql.Date InvoiceDate = null;
        							java.util.GregorianCalendar c5;
        							if(!txtInvoiceDate.equals("0"))
        							{
        							String[] sd5 = txtInvoiceDate.split("/");
        							c5 = new java.util.GregorianCalendar(Integer
        									.parseInt(sd5[2]),
        									Integer.parseInt(sd5[1]) - 1, Integer
        											.parseInt(sd5[0]));
        							java.util.Date d5 = c5.getTime();
        							InvoiceDate = new Date(d5.getTime());
        						    }
        						
        							float txtInvoiceAmount = Float
        									.parseFloat(al1[g + 5]);

        							int txtAgreementNo = Integer.parseInt(al1[g + 6]);

        							String txtAgreementDate = al1[g + 7];
        							System.out.println("txtAgreementDate:::"+txtAgreementDate);
        							java.sql.Date AgreementDate = null;
        							if(txtAgreementDate.equals("0"))
        							{
        								AgreementDate = null;
        							}
        							else{
        								System.out.println("not zero");
        							String[] sd15 = txtAgreementDate.split("/");
        							c = new java.util.GregorianCalendar(Integer
        									.parseInt(sd15[2]), Integer
        									.parseInt(sd15[1]) - 1, Integer
        									.parseInt(sd15[0]));
        							java.util.Date d15 = c.getTime();
        							AgreementDate = new Date(d15.getTime());
        							}
        							System.out.println("txtAgreementAmount::::"+al1[g + 8]);
        							float txtAgreementAmount = Float
        									.parseFloat(al1[g + 8]);
        							//xml = xml + "<flag1>failure</flag1>";
        							String txtCRDRIndicator = al1[g + 9];

        							float txtAmount = Float.parseFloat(al1[g + 10]);
        							String rdoIfFirstParty = al1[g + 11];
        							System.out.println("rdoIfFirstParty::::"+al1[g + 11]);
        							

        							String txtPayeeType = al1[g + 12];

        							int txtPayeeCode = Integer.parseInt(al1[g + 13]);

        							String mtxtRemarks1 = al1[g + 14];
        							System.out.println("mtxtRemarks1::::"+al1[g + 14]);
        							int i = 1, i1 = 0;
        						
        							try {
        								
        								 String sql_new =
        	                                    "insert into TEST_JOURNAL_TRANSACTION(ACCOUNTING_UNIT_ID, " +
        	                                    "ACCOUNTING_FOR_OFFICE_ID ,CASHBOOK_YEAR, CASHBOOK_MONTH, VOUCHER_NO, SL_NO, ACCOUNT_HEAD_CODE, " +
        	                                    "CR_DR_INDICATOR, SUB_LEDGER_TYPE_CODE, SUB_LEDGER_CODE, BILL_NO,BILL_TYPE,AGREEMENT_NO,AGREEMENT_DATE," +
        	                                    "BILL_DATE,  " +
        	                                    "AMOUNT, PARTICULARS,CB_REF_NO,CB_REF_DATE,UPDATED_BY_USER_ID,UPDATED_DATE ) " +
        	                                    "values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";

        	                               
        	                               String cbdate="";
        	                          
        	                               int cbno=0;
        	                               PreparedStatement ps_new = connection.prepareStatement(sql_new);
        	                                ps_new.setInt(1, cboAcc_UnitCode);
                                            ps_new.setInt(2, cboOffice_code);
                                            ps_new.setInt(3, cboCashBook_Year);
                                            ps_new.setInt(4, cboCashBook_Month);
                                            ps_new.setInt(5, txtJournalVou_No);
                                            ps_new.setInt(6, slcount);
        	                         
                                            ps_new.setInt(7,txtAccountHeadCode);
                                            ps_new.setString(8,txtCRDRIndicator.substring(0,2));
                                        
                                            ps_new.setInt(9,cmbSL_type);
                                            ps_new.setInt(10,cmbSL_Code);
                                            
                                            ps_new.setInt(11,cboBillNo);
                                            ps_new.setInt(12, txtBillMajorType);
                                            ps_new.setInt(13, txtAgreementNo);
                                            ps_new.setDate(14, AgreementDate);
                                            ps_new.setDate(15,BillDate);
                                            ps_new.setFloat(16, txtAmount);
                                            ps_new.setString(17, mtxtRemarks1);
                                            ps_new.setInt(18, cbno);
                                            ps_new.setString(19, cbdate);
                                            ps_new.setInt(20, empid);
                                            ps_new.setTimestamp(21, ts);
        								db_journal = ps_new.executeUpdate();
        							
        								if(db_journal>0)
        								{
        									System.out.println("db_journal::"+db_journal);
        									g = g + 15;
        									slcount++;
        								}
        							
        							

        							} catch (Exception e) {
        								xml = xml + "<flag>failure</flag>";
        								connection.rollback();
        								e.printStackTrace();
        							}
        						}
                              
                            }
                       	
						}
						else
						{
							xml = xml + "<flag1>failure</flag1>";
							connection.rollback();
							
						}
						
						if(db_journal>0)
						{
							try{
								ps3 = connection.prepareStatement("update FAS_BILL_REGISTER_MASTER set MEMO_ENTRY='Y',MEMO_UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=? and SANCTION_PROC_NO=?");
								ps3.setTimestamp(1, ts);
								ps3.setInt(2, cboAcc_UnitCode);
								ps3.setInt(3, cboOffice_code);
								ps3.setInt(4, cboCashBook_Year);
								ps3.setInt(5, cboCashBook_Month);
								ps3.setInt(6, cboBillNo);
								ps3.setInt(7, txtSanctionProceedingNo);
								up=ps3.executeUpdate();
							}
							catch(Exception eee)
							{
								System.out.println("eee::::"+eee);
							}
						}
						else
						{
							xml = xml + "<flag1>failure</flag1>";
						}
					}
						else
						{
							xml = xml + "<flag1>failure</flag1>";
						}
						
					}

				} catch (Exception e) {
					e.printStackTrace();
					xml = xml + "<flagE>failure</flagE>";
				}
			
			}
				System.out.println(" db1 "+db1+"db_journal "+db_journal+" up "+up+" db2 "+db2 );
				if ((db1 > 0) && (db_journal > 0) && (up>0) && (db2 > 0)) {
					connection.commit();
					xml = xml + "<flagE>success</flagE>";
					xml = xml + "<flag>success</flag>";
				} else {
					System.out.println("db1-db_journal");
					connection.rollback();
					xml = xml + "<flag>failure</flag>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flagE>failure</flagE>";
			}
		} else if (strCommand.equalsIgnoreCase("deleted")) {

			xml = "<response><command>deleted</command>";
			int slNo = Integer.parseInt(request.getParameter("slNo"));

			xml = xml + "<id>" + slNo + "</id>";

		} else if (strCommand.equalsIgnoreCase("update")) {

			xml = "<response><command>update</command>";
			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cboOffice_code = Integer.parseInt(cboOffice_code1);

			String cboCashBook_Year1 = request.getParameter("cboCashBook_Year");
			int cboCashBook_Year = Integer.parseInt(cboCashBook_Year1);

			String cboCashBook_Month1 = request
					.getParameter("cboCashBook_Month");
			int cboCashBook_Month = Integer.parseInt(cboCashBook_Month1);

			int cboBillNo = Integer.parseInt(request.getParameter("cboBillNo"));

			int txtAccountHeadCode = Integer.parseInt(request
					.getParameter("txtAccountHeadCode"));

			int txtInvoiceNo = Integer.parseInt(request
					.getParameter("txtInvoiceNo"));

			String txtInvoiceDate = request.getParameter("txtInvoiceDate");

			java.sql.Date InvoiceDate = null;
			java.util.GregorianCalendar c;
			String[] sd = request.getParameter("txtInvoiceDate").split("/");
			c = new java.util.GregorianCalendar(Integer.parseInt(sd[2]),
					Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c.getTime();
			InvoiceDate = new Date(d.getTime());

			float txtInvoiceAmount = Float.parseFloat(request
					.getParameter("txtInvoiceAmount"));

			int txtAgreementNo = Integer.parseInt(request
					.getParameter("txtAgreementNo"));

			String txtAgreementDate = request.getParameter("txtAgreementDate");

			java.sql.Date AgreementDate = null;
			String[] sd1 = request.getParameter("txtAgreementDate").split("/");
			c = new java.util.GregorianCalendar(Integer.parseInt(sd1[2]),
					Integer.parseInt(sd1[1]) - 1, Integer.parseInt(sd1[0]));
			java.util.Date d1 = c.getTime();
			AgreementDate = new Date(d1.getTime());

			float txtAgreementAmount = Float.parseFloat(request
					.getParameter("txtAgreementAmount"));

			int cmbSL_type = Integer.parseInt(request
					.getParameter("cmbSL_type"));

			int cmbSL_Code = Integer.parseInt(request
					.getParameter("cmbSL_Code"));

			String txtCRDRIndicator = request.getParameter("txtCRDRIndicator");

			float txtAmount = Float.parseFloat(request
					.getParameter("txtAmount"));

			String rdoIfFirstParty = request.getParameter("rdoIfFirstParty");

			String txtPayeeType = request.getParameter("txtPayeeType");

			int txtPayeeCode = Integer.parseInt(request
					.getParameter("txtPayeeCode"));

			String mtxtRemarks1 = request.getParameter("mtxtRemarks1");

			int slNo = Integer.parseInt(request.getParameter("slNo"));

			try {
			PreparedStatement ps_new = connection
						.prepareStatement("update FAS_MEMO_OF_PAYMENT_TRN set ACCOUNT_HEAD_CODE=?,INVOICE_NO=?,INVOICE_DATE=?,INVOICE_AMOUNT=?,AGREEMENT_NO=?,AGREEMENT_DATE=?,AGREEMENT_AMOUNT=?,SUB_LEDGER_TYPE_CODE=?,SUB_LEDGER_CODE=?,CR_DR_INDICATOR=?,AMOUNT=?,FIRST_PARTY=?,PAYEE_TYPE_CODE=?,PAYEE_CODE=?,PARTICULARS=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=? and SL_NO=?");

				ps_new.setInt(1, txtAccountHeadCode);
				ps_new.setInt(2, txtInvoiceNo);
				ps_new.setDate(3, InvoiceDate);
				ps_new.setFloat(4, txtInvoiceAmount);
				ps_new.setInt(5, txtAgreementNo);
				ps_new.setDate(6, AgreementDate);
				ps_new.setFloat(7, txtAgreementAmount);
				ps_new.setInt(8, cmbSL_type);
				ps_new.setInt(9, cmbSL_Code);
				ps_new.setString(10, txtCRDRIndicator.substring(0, 2));
				ps_new.setFloat(11, txtAmount);
				ps_new.setString(12, rdoIfFirstParty);
				ps_new.setString(13, txtPayeeType);
				ps_new.setInt(14, txtPayeeCode);
				ps_new.setString(15, mtxtRemarks1);
				ps_new.setInt(16, empid);
				ps_new.setTimestamp(17, ts);
				ps_new.setInt(18, cboAcc_UnitCode);
				ps_new.setInt(19, cboOffice_code);
				ps_new.setInt(20, cboCashBook_Year);
				ps_new.setInt(21, cboCashBook_Month);
				ps_new.setInt(22, cboBillNo);
				ps_new.setInt(23, slNo);
				ps_new.executeUpdate();

				xml = xml + "<flag>success</flag>";
				xml = xml + "<cboAcc_UnitCode>" + cboAcc_UnitCode
						+ "</cboAcc_UnitCode>";
				xml = xml + "<cboOffice_code>" + cboOffice_code
						+ "</cboOffice_code>";
				xml = xml + "<cboCashBook_Year>" + cboCashBook_Year
						+ "</cboCashBook_Year>";
				xml = xml + "<cboCashBook_Month>" + cboCashBook_Month
						+ "</cboCashBook_Month>";
				xml = xml + "<cboBillNo>" + cboBillNo + "</cboBillNo>";
				xml = xml + "<txtAccountHeadCode>" + txtAccountHeadCode
						+ "</txtAccountHeadCode>";
				xml = xml + "<slNo>" + slNo + "</slNo>";
				xml = xml + "<txtInvoiceNo>" + txtInvoiceNo + "</txtInvoiceNo>";
				xml = xml + "<InvoiceDate>" + txtInvoiceDate + "</InvoiceDate>";
				xml = xml + "<txtInvoiceAmount>" + txtInvoiceAmount
						+ "</txtInvoiceAmount>";
				xml = xml + "<txtAgreementNo>" + txtAgreementNo
						+ "</txtAgreementNo>";
				xml = xml + "<AgreementDate>" + txtAgreementDate
						+ "</AgreementDate>";
				xml = xml + "<txtAgreementAmount>" + txtAgreementAmount
						+ "</txtAgreementAmount>";

				xml = xml + "<cmbSL_type>" + cmbSL_type + "</cmbSL_type>";
				xml = xml + "<cmbSL_Code>" + cmbSL_Code + "</cmbSL_Code>";
				xml = xml + "<txtCRDRIndicator>" + txtCRDRIndicator
						+ "</txtCRDRIndicator>";
				xml = xml + "<txtAmount>" + txtAmount + "</txtAmount>";
				xml = xml + "<rdoIfFirstParty>" + rdoIfFirstParty
						+ "</rdoIfFirstParty>";
				xml = xml + "<txtPayeeType>" + txtPayeeType + "</txtPayeeType>";

				xml = xml + "<txtPayeeCode>" + txtPayeeCode + "</txtPayeeCode>";
				xml = xml + "<mtxtRemarks1>" + mtxtRemarks1 + "</mtxtRemarks1>";

			} catch (Exception e) {
				System.out.println("exception in update is" + e);

			}
		}
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}
}
