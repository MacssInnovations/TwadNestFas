package Servlets.FAS.FAS1.MTCRegister.servlets;

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
 * Servlet implementation class MTC_70_Treasury
 */
public class MTC_70_Treasury extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public MTC_70_Treasury() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();

		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null;
		ResultSet rs2 = null;
		PreparedStatement ps = null,ps3=null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;
		int cashbookYear = 0;
		String cashbookMonth = null;
		int unitid = 0;
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
			System.out.println("strCommand:-" + strCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}

		Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
				.getAttribute("UserProfile");
		int empid = empProfile.getEmployeeId();
		String empName = empProfile.getEmployeeName();
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);

		if (strCommand.equalsIgnoreCase("getBillNo")) {
			xml = xml + "<response><command>getBillNo</command>";

			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cmbAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cmbOffice_code = Integer.parseInt(cboOffice_code1);

			String cboCashBook_Year1 = request.getParameter("txtCB_Year");
			int txtCB_Year = Integer.parseInt(cboCashBook_Year1);

			String cboCashBook_Month1 = request.getParameter("txtCB_Month");
			int txtCB_Month = Integer.parseInt(cboCashBook_Month1);

		
			try {
				String su = "select BILL_NO from FAS_MTC70_REGISTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and status = 'L' and CHECKED_AND_PASSED_DATE is null ";
				ps = connection.prepareStatement(su);
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setInt(3, txtCB_Year);
				ps.setInt(4, txtCB_Month);
				
				rs = ps.executeQuery();

				while (rs.next()) {
					xml = xml + "<BillNo>" + rs.getInt("BILL_NO") + "</BillNo>";
				}
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failur</flag>";
			}

		} else if (strCommand.equalsIgnoreCase("getBillDetails")) {
			xml = xml + "<response><command>getBillDetails</command>";
			int mas_ct=0;
			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cmbAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cmbOffice_code = Integer.parseInt(cboOffice_code1);

			String cboCashBook_Year1 = request.getParameter("txtCB_Year");
			int txtCB_Year = Integer.parseInt(cboCashBook_Year1);

			String cboCashBook_Month1 = request.getParameter("txtCB_Month");
			int txtCB_Month = Integer.parseInt(cboCashBook_Month1);
			String sub_q = "",sub_main="";
			

			String cboBillNo1 = request.getParameter("cboBillNo");
			int cboBillNo = Integer.parseInt(cboBillNo1);

			int flag = 0,bill_maj=0;
			try {
				String billDate = null,mtc_orginal_date=null;
				String su = "select BILL_DATE,MTC70_ENTRY_DATE,SANCTIONED_AMOUNT,TOTAL_DEDUCTION_AMOUNT,m.BILL_MAJOR_TYPE_CODE,(select K.BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES k " +
						"where K.BILL_MAJOR_TYPE_CODE=m.BILL_MAJOR_TYPE_CODE) as major_desc from FAS_MTC70_REGISTER m where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and BILL_NO="+cboBillNo+" and  CHECKED_AND_PASSED_DATE is null";
				System.out.println("q:::"+su);
				ps = connection.prepareStatement(su);
//				ps.setInt(1, cmbAcc_UnitCode);
//				ps.setInt(2, cmbOffice_code);
//				ps.setInt(3, txtCB_Year);
//				ps.setInt(4, txtCB_Month);
//				ps.setInt(5, cboBillNo);
				
				rs = ps.executeQuery();

				while (rs.next()) {
					Date billDate1 = rs.getDate("BILL_DATE");

					String Stringdate = billDate1.toString();

					String[] ddd = Stringdate.split("-");

					int day = Integer.parseInt(ddd[2]);
					int month = Integer.parseInt(ddd[1]);
					int year = Integer.parseInt(ddd[0]);

					if (month >= 10) {
						billDate = (day + "/" + month + "/" + year);
					} else {
						billDate = (day + "/0" + month + "/" + year);
					}
					
					Date MTC70_ENTRY_DATE = rs.getDate("MTC70_ENTRY_DATE");

					String MTC70_ENTRY_DATE_one = MTC70_ENTRY_DATE.toString();

					String[] dddmtc = MTC70_ENTRY_DATE_one.split("-");

					int daymtc = Integer.parseInt(dddmtc[2]);
					int monthmtc = Integer.parseInt(dddmtc[1]);
					int yearmtc = Integer.parseInt(dddmtc[0]);

					if (monthmtc >= 10) {
						mtc_orginal_date = (daymtc + "/" + monthmtc + "/" + yearmtc);
					} else {
						mtc_orginal_date = (daymtc + "/0" + monthmtc + "/" + yearmtc);
					}
					
					/*if(year>2014 && month>3)
					{
						 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
						 sub_main=" Fas_Bill_Register_MasterNEW M, "+
						" 	  Fas_Bill_Register_Transactionw T ";
					}else{
						sub_q = " FAS_BILL_REGISTER_MASTER "; 
						 sub_main=" Fas_Bill_Register_Master M, "+
									" 	  Fas_Bill_Register_Transaction T ";
					}*/
					if (year > 2014) {
						if (year == 2015 && month <= 3) {
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
					
					System.out.println("sub_main   "+sub_main);
					System.out.println("sub_q   "+sub_q);
					xml = xml + "<billDate>" + billDate + "</billDate>";
					xml = xml + "<mtc_orginal_date>" + mtc_orginal_date + "</mtc_orginal_date>";
					xml = xml + "<totalSanctionedAmount>"
							+ rs.getInt("SANCTIONED_AMOUNT")
							+ "</totalSanctionedAmount>";
					xml = xml + "<DeductedAmount>"+ rs.getFloat("TOTAL_DEDUCTION_AMOUNT")+ "</DeductedAmount>";
					xml = xml + "<major_code>"+ rs.getInt("BILL_MAJOR_TYPE_CODE")+ "</major_code>";
					bill_maj=rs.getInt("BILL_MAJOR_TYPE_CODE");
					xml = xml + "<major_desc>"+ rs.getString("major_desc")+ "</major_desc>";
					xml = xml+ "<netAmount>"+ (rs.getInt("SANCTIONED_AMOUNT") - rs
									.getFloat("TOTAL_DEDUCTION_AMOUNT"))
							+ "</netAmount>";
				/*	if (rs.getInt("SANCTIONED_AMOUNT") != 
						(rs.getInt("SANCTIONED_AMOUNT") - rs.getFloat("TOTAL_DEDUCTION_AMOUNT"))) 
					{ */
						flag = 1;
				//	}
				}
				if (flag==1) {
				/*	String st = "select t.account_head_code, "+
							"  m.payee_type_code, "+
						" m.payee_code "+
						" from  "+sub_main+
						" where m.accounting_unit_id=t.accounting_unit_id "+
						" and m.accounting_unit_office_id=t.accounting_unit_office_id "+
						" and m.cashbook_year=t.cashbook_year "+
						" and m.cashbook_month=t.cashbook_month "+
						" and m.cashbook_month=t.cashbook_month "+
						" and m.bill_no=t.bill_no and m.STATUS='L' "+
						" and m.accounting_unit_id     =? "+
						" and m.accounting_unit_office_id=? "+
						" and m.cashbook_year            =? "+
						" and m.cashbook_month           =? "+
						" and m.bill_no                  =? "+
						" AND m.BILL_MAJOR_TYPE          =?";*/
					String st = " SELECT b.* " +
							" FROM " +
							"  (SELECT T.Bill_No, " +
							"    M.Bill_Date, " +
							"    t.Accounting_Unit_Id , " +
							"    T.Accounting_For_Office_Id, " +
							"    T.Payment_Unit, " +
							"    T.Payment_Office " +
							"  FROM Fas_Memo_Of_Payment_Mst M " +
							"  INNER JOIN Fas_Memo_Of_Payment_Trn T " +
							"  ON M.Accounting_Unit_Id       =T.Accounting_Unit_Id " +
							"  AND M.Accounting_For_Office_Id=T.Accounting_For_Office_Id " +
							"  AND M.Cashbook_Year           =T.Cashbook_Year " +
							"  AND M.Cashbook_Month          =T.Cashbook_Month " +
							"  AND M.Bill_No                 =T.Bill_No " +
							"  AND M.Status                  ='L' " +
							"  AND T.Payment_Unit            =? " +
							"  AND T.Payment_Office          =? " +
							"  AND T.Cashbook_Year           =? " +
							"  AND T.Cashbook_Month          =? " +
							"  AND M.Bill_No                 =? " +
							"  )A " +
							" INNER JOIN " +
							"  (SELECT t.account_head_code, " +
							"    m.accounting_unit_id , " +
							"    m.accounting_unit_office_id, " +
							"    M.Payee_Type_Code, " +
							"    m.payee_code, " +
							"    m.Bill_No " +
							"  FROM Fas_Bill_Register_MasterNEW M, " +
							"    Fas_Bill_Register_Transactionw T " +
							"  WHERE m.accounting_unit_id     =t.accounting_unit_id " +
							"  AND m.accounting_unit_office_id=t.accounting_unit_office_id " +
							"  AND m.cashbook_year            =t.cashbook_year " +
							"  AND m.cashbook_month           =t.cashbook_month " +
							"  AND m.cashbook_month           =t.cashbook_month " +
							"  AND m.bill_no                  =t.bill_no " +
							"  AND M.Status                   ='L' " +
							"  AND m.cashbook_year            =? " +
							"  AND M.Cashbook_Month           =? " +
							"  AND M.Bill_Major_Type          =? " +
							"  )B " +
							" ON b.Accounting_Unit_Id        =A.Accounting_Unit_Id " +
							" AND B.Accounting_Unit_Office_Id=A.Accounting_For_Office_Id " +
							" AND b.Bill_No                  =a.Bill_No" ;
					System.out.println("if:"+st);
					ps = connection.prepareStatement(st);
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setInt(3, txtCB_Year);
					ps.setInt(4, txtCB_Month);
					ps.setInt(5, cboBillNo);
					ps.setInt(6, txtCB_Year);
					ps.setInt(7, txtCB_Month);
					ps.setInt(8, bill_maj);
					rs = ps.executeQuery();
					while (rs.next()) {
						mas_ct++;
						xml = xml + "<AccHdCode>"
								+ rs.getInt("ACCOUNT_HEAD_CODE")
								+ "</AccHdCode>";
						xml = xml + "<PayeeType>"
								+ rs.getString("PAYEE_TYPE_CODE")
								+ "</PayeeType>";
						xml = xml + "<PayeeCode>" + rs.getInt("PAYEE_CODE")
								+ "</PayeeCode>";
						xml = xml + "<Accounting_Unit_Id>" + rs.getInt("Accounting_Unit_Id")
								+ "</Accounting_Unit_Id>";
						xml = xml + "<Accounting_Unit_Office_Id>" + rs.getInt("Accounting_Unit_Office_Id")
								+ "</Accounting_Unit_Office_Id>";
					}
				}
				
				if(mas_ct==0)
				{
					String st = "select m.account_head_code, "+
					"  m.payee_type_code, "+
				" m.payee_code "+
				" from FAS_BILL_REGISTERNEW m "+
				" where m.STATUS='L' "+
				" and m.accounting_unit_id     =? "+
				" and m.accounting_unit_office_id=? "+
				" and m.cashbook_year            =? "+
				" and m.cashbook_month           =? "+
				" and m.bill_no                  =? "+
				" AND m.BILL_MAJOR_TYPE          =?";
			System.out.println("else"+st);
			ps = connection.prepareStatement(st);
			ps.setInt(1, cmbAcc_UnitCode);
			ps.setInt(2, cmbOffice_code);
			ps.setInt(3, txtCB_Year);
			ps.setInt(4, txtCB_Month);
			ps.setInt(5, cboBillNo);
			ps.setInt(6, bill_maj);
			rs = ps.executeQuery();
			while (rs.next()) {
				xml = xml + "<AccHdCode>"
						+ rs.getInt("ACCOUNT_HEAD_CODE")
						+ "</AccHdCode>";
				xml = xml + "<PayeeType>"
						+ rs.getString("PAYEE_TYPE_CODE")
						+ "</PayeeType>";
				xml = xml + "<PayeeCode>" + rs.getInt("PAYEE_CODE")
						+ "</PayeeCode>";
				}
				}
				else{
					
				}
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failur</flag>";
			}

		} else if (strCommand.equalsIgnoreCase("saveFunc")) {
			xml = xml + "<response><command>saveFunc</command>";
			int kk2=0;
			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cmbAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cmbOffice_code = Integer.parseInt(cboOffice_code1);

			String cboCashBook_Year1 = request.getParameter("txtCB_Year");
			int txtCB_Year = Integer.parseInt(cboCashBook_Year1);

			String cboCashBook_Month1 = request.getParameter("txtCB_Month");
			int txtCB_Month = Integer.parseInt(cboCashBook_Month1);

			String cboBillMajorType1 = request.getParameter("cboBillMajorType");
			int cboBillMajorType = Integer.parseInt(cboBillMajorType1);
		
			int ori_unit = Integer.parseInt(request.getParameter("ori_unit"));
			int ori_office = Integer.parseInt(request.getParameter("ori_office"));
			String cboBillNo1 = request.getParameter("cboBillNo");
			int cboBillNo = Integer.parseInt(cboBillNo1);
			
			java.sql.Date txtChecked_Passed_Date = null;
			java.util.GregorianCalendar c2;
			String[] sd = request.getParameter("txtChecked_Passed_Date").split("/");
			c2 = new java.util.GregorianCalendar(Integer.parseInt(sd[2]),
					Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c2.getTime();
			txtChecked_Passed_Date = new Date(d.getTime());

			int Checked_Passed_By = Integer.parseInt(request.getParameter("EmpID_mas"));

			java.sql.Date txtMTCUpdatedDate = null;
			java.util.GregorianCalendar c3;
			String[] sd3 = request.getParameter("txtMTCUpdatedDate").split("/");
			c3 = new java.util.GregorianCalendar(Integer.parseInt(sd3[2]),
					Integer.parseInt(sd3[1]) - 1, Integer.parseInt(sd3[0]));
			java.util.Date d3 = c3.getTime();
			txtMTCUpdatedDate = new Date(d3.getTime());			

			java.sql.Date SenttoPreAuditon = null;
			java.util.GregorianCalendar c5;
			String[] sd5 = request.getParameter("SenttoPreAuditon").split("/");
			c5 = new java.util.GregorianCalendar(Integer.parseInt(sd5[2]),
					Integer.parseInt(sd5[1]) - 1, Integer.parseInt(sd5[0]));
			java.util.Date d5 = c5.getTime();
			SenttoPreAuditon = new Date(d5.getTime());

			String Remarks = request.getParameter("Remarks");
			
			
			String sub_q = "",sub_main="";
/*			if(txtCB_Year>2014 && txtCB_Month>3)
			{
				 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
				 sub_main=" Fas_Bill_Register_MasterNEW M, "+
				" 	  Fas_Bill_Register_Transactionw T ";
			}else{
				sub_q = " FAS_BILL_REGISTER_MASTER "; 
				 sub_main=" Fas_Bill_Register_Master M, "+
							" 	  Fas_Bill_Register_Transaction T ";
			}*/
			if (txtCB_Year > 2014) {
				if (txtCB_Year == 2015 && txtCB_Month <= 3) {
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
			try {
				connection.setAutoCommit(false);
				ps = connection.prepareStatement("update FAS_MTC70_REGISTER set CHECKED_AND_PASSED_DATE=?,CHECKED_AND_PASSED_BY=?,REGISTER_UPDATED_DATE=?,PRE_AUDIT_SENT_DATE=?,UPDATED_REMARKS=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_MAJOR_TYPE_CODE=? and BILL_NO=?");
				ps.setDate(1, txtChecked_Passed_Date);
				ps.setInt(2, Checked_Passed_By);
				ps.setDate(3, txtMTCUpdatedDate);
				ps.setDate(4, SenttoPreAuditon);
				ps.setString(5, Remarks);				
				ps.setInt(6, cmbAcc_UnitCode);
				ps.setInt(7, cmbOffice_code);
				ps.setInt(8, txtCB_Year);
				ps.setInt(9, txtCB_Month);				
				ps.setInt(10, cboBillMajorType);
				ps.setInt(11, cboBillNo);
				int k=ps.executeUpdate();
				//System.out.println("k:::"+k);
				if (k > 0) {
					if(cmbOffice_code!=5000)
					{//for other offices no need for pre-audit approval 
						System.out.println("other offices::::");
					ps3=connection.prepareStatement("update "+sub_q+" set TREASURY_VERIFY_DATE=?,SENT_TO_PRE_AUDIT_ON=?,DOR_BY_PRE_AUDIT=?,PRE_AUDIT_DATE=? where ACCOUNTING_UNIT_ID="+ori_unit+" and ACCOUNTING_UNIT_OFFICE_ID= " +ori_office+
							"and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and BILL_NO="+cboBillNo+" and MTC_70_REGISTER_DATE is not null");
					ps3.setDate(1, txtChecked_Passed_Date);
					ps3.setDate(2, SenttoPreAuditon);
					ps3.setDate(3, SenttoPreAuditon);
					ps3.setDate(4, SenttoPreAuditon);
				//	ps3.setDate(5, SenttoPreAuditon);
					kk2 = ps3.executeUpdate();
					}else
					{
						System.out.println("head office 5000::::");
						ps3=connection.prepareStatement("update "+sub_q+" set TREASURY_VERIFY_DATE=?,SENT_TO_PRE_AUDIT_ON=? where ACCOUNTING_UNIT_ID="+ori_unit+" and ACCOUNTING_UNIT_OFFICE_ID= " +ori_office+
								"and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and BILL_NO="+cboBillNo+" and MTC_70_REGISTER_DATE is not null");
						ps3.setDate(1, txtChecked_Passed_Date);
						ps3.setDate(2, SenttoPreAuditon);
					//	ps3.setDate(3, SenttoPreAuditon);
						kk2 = ps3.executeUpdate();
						
					}
					
					if(kk2>0)
					{	
						System.out.println("yesss");
						connection.commit();
					xml = xml + "<flag>success</flag>";
					}
					else
					{
						if(cmbOffice_code!=5000)
						{//for other offices no need for pre-audit approval 
						ps3=connection.prepareStatement("update FAS_BILL_REGISTERNEW set TREASURY_VERIFY_DATE=?,SENT_TO_PRE_AUDIT_ON=?,DOR_BY_PRE_AUDIT=?,PRE_AUDIT_DATE=? where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_UNIT_OFFICE_ID= " +cmbOffice_code+
								"and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and BILL_NO="+cboBillNo+" and MTC_70_REGISTER_DATE is not null");
						ps3.setDate(1, txtChecked_Passed_Date);
						ps3.setDate(2, SenttoPreAuditon);
						ps3.setDate(3, SenttoPreAuditon);
						ps3.setDate(4, SenttoPreAuditon);
						//ps3.setDate(5, SenttoPreAuditon);
						}
						else
						{
							ps3=connection.prepareStatement("update FAS_BILL_REGISTERNEW set TREASURY_VERIFY_DATE=?,SENT_TO_PRE_AUDIT_ON=? where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_UNIT_OFFICE_ID= " +cmbOffice_code+
									"and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and BILL_NO="+cboBillNo+" and MTC_70_REGISTER_DATE is not null");
							ps3.setDate(1, txtChecked_Passed_Date);
							ps3.setDate(2, SenttoPreAuditon);
						//	ps3.setDate(3, SenttoPreAuditon);
						}
						int kk22 = ps3.executeUpdate();
						if(kk22>0)
						{
							connection.commit();
							xml = xml + "<flag>success</flag>";
						}
					}
				} else {
					connection.rollback();
					xml = xml + "<flag>failur</flag>";
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		}
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}

}
