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
 * Servlet implementation class MTC_70_RegisterEntry1
 */
public class MTC_70_RegisterEntry1 extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MTC_70_RegisterEntry1() {
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
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();

		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null;
		ResultSet rs2 = null;
		PreparedStatement ps = null;
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

		if (strCommand.equalsIgnoreCase("getEmp")) {

			xml = xml + "<response><command>getEmp</command>";
			xml = xml + "<empid>" + empid + "</empid>";
			xml = xml + "<empName>" + empName + "</empName>";
			try {
				String su = "select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where STATUS='L' order by BILL_MAJOR_TYPE_CODE";
				ps = connection.prepareStatement(su);
				rs = ps.executeQuery();
				while (rs.next()) {
					xml = xml + "<billMajorTypeCode>"
							+ rs.getInt("BILL_MAJOR_TYPE_CODE")
							+ "</billMajorTypeCode>";

					xml = xml + "<billMajorTypeDesc>"
							+ rs.getString("BILL_MAJOR_TYPE_DESC")
							+ "</billMajorTypeDesc>";
				}
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		} else if (strCommand.equalsIgnoreCase("getBillNo")) {
			xml = xml + "<response><command>getBillNo</command>";

			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cmbAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cmbOffice_code = Integer.parseInt(cboOffice_code1);

			String cboCashBook_Year1 = request.getParameter("txtCB_Year");
			int txtCB_Year = Integer.parseInt(cboCashBook_Year1);

			String cboCashBook_Month1 = request.getParameter("txtCB_Month");
			int txtCB_Month = Integer.parseInt(cboCashBook_Month1);
			String sub_q = "",sub_main="";
			/*if(txtCB_Year>2014 && txtCB_Month>3)
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
				//cahnged on 14 OCt2015
				/*String su = "(select BILL_NO from "+sub_q+" where status='L' and ACCOUNTING_UNIT_ID= " +cmbAcc_UnitCode+
				" and ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH= " +txtCB_Month+
				" and BILL_APPROVED='Y' and MTC70ENTRY='Y' and MTC_70_REGISTER_DATE is null) union all "+
				 "(select BILL_NO from FAS_BILL_REGISTERNEW where status='L' and ACCOUNTING_UNIT_ID= " +cmbAcc_UnitCode+
					" and ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code+" and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH= " +txtCB_Month+
					" and BILL_APPROVED='Y' and MTC70ENTRY='Y' and MTC_70_REGISTER_DATE is null)";
*/
				String su = " SELECT distinct b.bill_no ,a.Accounting_Unit_Id,a.Accounting_For_Office_Id , "+
						 " a.Payment_Unit, " +
						"  a.Payment_Office " +
					/*	"  b.* " +*/
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
						"  AND T.Cashbook_Month          = ? " +
						"  )A " +
						" INNER JOIN "+sub_q+" B " +
						" ON Status                      ='L' " +
						" AND B.Accounting_Unit_Id       = A.Accounting_Unit_Id " +
						" AND B.Accounting_Unit_Office_Id=A.Accounting_For_Office_Id " +
						" AND B.Bill_Date                =A.Bill_Date " +
						" AND B.bill_no                  = a.bill_no " +
						" AND B.Bill_Approved            ='Y' " +
						" AND B.Mtc70entry               ='Y' " +
						" AND b.MTC_70_REGISTER_DATE    IS NULL";

				System.out.println("::"+su);
				ps = connection.prepareStatement(su);
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2,cmbOffice_code);
				ps.setInt(3, txtCB_Year);
				ps.setInt(4, txtCB_Month);
				rs = ps.executeQuery();
				int cc=0;
				while (rs.next()) {
					xml = xml + "<flag>success</flag>";
					xml = xml + "<BillNo>" + rs.getInt("BILL_NO") + "</BillNo>";
					xml = xml + "<ORI_UNIT>" + rs.getInt("Accounting_Unit_Id") + "</ORI_UNIT>";
					xml = xml + "<ORI_OFFICE>" + rs.getInt("Accounting_For_Office_Id") + "</ORI_OFFICE>";
				
					cc++;
				}
				if(cc==0){
					xml = xml + "<flag>failure</flag>";
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}

		} else if (strCommand.equalsIgnoreCase("getBillDetails")) {
			xml = xml + "<response><command>getBillDetails</command>";

			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cmbAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cmbOffice_code = Integer.parseInt(cboOffice_code1);

			String cboCashBook_Year1 = request.getParameter("txtCB_Year");
			int txtCB_Year = Integer.parseInt(cboCashBook_Year1);

			String cboCashBook_Month1 = request.getParameter("txtCB_Month");
			int txtCB_Month = Integer.parseInt(cboCashBook_Month1);
			String sub_q = "",sub_main="";
			/*if(txtCB_Year>2014 && txtCB_Month>3)
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
			
			String cboBillNo1 = request.getParameter("cboBillNo");
			int cboBillNo = Integer.parseInt(cboBillNo1);

			try {
				String billDate = null,billDate_App=null;
				String su = "select BILL_DATE,TOTAL_SANCTIONED_AMOUNT,DEDUCTED_AMOUNT,DRAWING_OFFICER_APPROVE_DATE,BILL_MAJOR_TYPE,(select V.BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES v " +
						" where v.BILL_MAJOR_TYPE_CODE=BILL_MAJOR_TYPE)as major_desc from "+sub_q+" where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? " +
						" and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=? and BILL_APPROVED='Y' and status ='L' and MTC70ENTRY='Y'";
				
				//Joan changed on 14 OCt2015 for Add PAyment unit and office check
				 su = " SELECT "+
						 "  B.Bill_Date, " +
						 "  B.Total_Sanctioned_Amount, " +
						 "  b.DEDUCTED_AMOUNT, " +
						 "  B.Drawing_Officer_Approve_Date, " +
						 "  b.BILL_MAJOR_TYPE, " +
						 "  (SELECT V.BILL_MAJOR_TYPE_DESC " +
						 "  FROM Fas_Bill_Major_Types V " +
						 "  WHERE v.BILL_MAJOR_TYPE_CODE= b.BILL_MAJOR_TYPE " +
						 "  )AS major_desc,a.Accounting_Unit_Id as o_unit,a.Accounting_For_Office_Id as o_office " +
						" FROM " +
						"  (SELECT T.Bill_No, " +
						"    M.Bill_Date, " +
						"    t.Accounting_Unit_Id, " +
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
						"  AND T.Cashbook_Month          = ? " +
						"  AND  M.Bill_No               = ? " +
						"  )A " +
						" INNER JOIN "+sub_q+" B " +
						" ON Status                      ='L' " +
						" AND B.Accounting_Unit_Id       = A.Accounting_Unit_Id " +
						" AND B.Accounting_Unit_Office_Id=A.Accounting_For_Office_Id " +
						" AND B.Bill_Date                =A.Bill_Date " +
						" AND B.bill_no                  = a.bill_no " +
						" AND B.Bill_Approved            ='Y' " +
						" AND B.Mtc70entry               ='Y' " +
						" AND b.MTC_70_REGISTER_DATE    IS NULL";
				System.out.println("su det:"+su);
				ps = connection.prepareStatement(su);
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setInt(3, txtCB_Year);
				ps.setInt(4, txtCB_Month);
				ps.setInt(5, cboBillNo);
				rs = ps.executeQuery();

				if (rs.next()) {
					xml = xml + "<flag>success</flag>";
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

//modified by sathya on 06Apr15 due to nullpointer exception arised ......added this condition......
					if( rs.getDate("DRAWING_OFFICER_APPROVE_DATE") == null)
					{
						//billDate ="-";
						billDate_App = "-";
					}
					else
					{
					Date DRAWING_OFFICER_APPROVE_DATE1 = rs.getDate("DRAWING_OFFICER_APPROVE_DATE");

					String OFFICER_APPROVE_DATE1 = DRAWING_OFFICER_APPROVE_DATE1.toString();
					
										
					String[] ddd_app = OFFICER_APPROVE_DATE1.split("-");

					int day_app = Integer.parseInt(ddd_app[2]);
					int month_app = Integer.parseInt(ddd_app[1]);
					int year_app = Integer.parseInt(ddd_app[0]);

					if (month_app >= 10) {
						billDate_App = (day_app + "/" + month_app + "/" + year_app);
					} else {
						billDate_App = (day_app + "/0" + month_app + "/" + year_app);
					}
					}
					xml = xml + "<billDate>" + billDate + "</billDate>";
					xml = xml + "<bill_app_date>" + billDate_App + "</bill_app_date>";
					
					xml = xml + "<ori_unit>" +  rs.getInt("o_unit") + "</ori_unit>";
					xml = xml + "<ori_office>" +  rs.getInt("o_office") + "</ori_office>";
					
					xml = xml + "<totalSanctionedAmount>"+ rs.getInt("TOTAL_SANCTIONED_AMOUNT")
							+ "</totalSanctionedAmount>";
					xml = xml + "<DeductedAmount>"+ rs.getFloat("DEDUCTED_AMOUNT")+ "</DeductedAmount>";
					xml = xml
							+ "<netAmount>"
							+ (rs.getInt("TOTAL_SANCTIONED_AMOUNT") - rs
									.getFloat("DEDUCTED_AMOUNT"))
							+ "</netAmount>";
					xml = xml + "<major_code>"+ rs.getInt("BILL_MAJOR_TYPE")+ "</major_code>";
					xml = xml + "<major_desc>"+ rs.getString("major_desc")+ "</major_desc>";
				}
				//without 
				else{
					
					
					String su1 = "select BILL_DATE,TOTAL_SANCTIONED_AMOUNT,DEDUCTED_AMOUNT,DRAWING_OFFICER_APPROVE_DATE,BILL_MAJOR_TYPE,(select V.BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES v " +
					" where v.BILL_MAJOR_TYPE_CODE=BILL_MAJOR_TYPE)as major_desc from FAS_BILL_REGISTERNEW where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? " +
					" and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=? and BILL_APPROVED='Y' and status ='L' and MTC70ENTRY='Y'";
			ps = connection.prepareStatement(su1);
			ps.setInt(1, cmbAcc_UnitCode);
			ps.setInt(2, cmbOffice_code);
			ps.setInt(3, txtCB_Year);
			ps.setInt(4, txtCB_Month);
			ps.setInt(5, cboBillNo);
			rs = ps.executeQuery();

			if (rs.next()) {
				xml = xml + "<flag>success</flag>";
				Date billDate1 = rs.getDate("BILL_DATE");

				String Stringdate = billDate1.toString();

				Date DRAWING_OFFICER_APPROVE_DATE1 = rs.getDate("DRAWING_OFFICER_APPROVE_DATE");

				String OFFICER_APPROVE_DATE1 = DRAWING_OFFICER_APPROVE_DATE1.toString();
				
				String[] ddd = Stringdate.split("-");

				int day = Integer.parseInt(ddd[2]);
				int month = Integer.parseInt(ddd[1]);
				int year = Integer.parseInt(ddd[0]);

				if (month >= 10) {
					billDate = (day + "/" + month + "/" + year);
				} else {
					billDate = (day + "/0" + month + "/" + year);
				}
				
				String[] ddd_app = OFFICER_APPROVE_DATE1.split("-");

				int day_app = Integer.parseInt(ddd_app[2]);
				int month_app = Integer.parseInt(ddd_app[1]);
				int year_app = Integer.parseInt(ddd_app[0]);

				if (month_app >= 10) {
					billDate_App = (day_app + "/" + month_app + "/" + year_app);
				} else {
					billDate_App = (day_app + "/0" + month_app + "/" + year_app);
				}
				xml = xml + "<billDate>" + billDate + "</billDate>";
				xml = xml + "<bill_app_date>" + billDate_App + "</bill_app_date>";
				
				xml = xml + "<totalSanctionedAmount>"+ rs.getInt("TOTAL_SANCTIONED_AMOUNT")
						+ "</totalSanctionedAmount>";
				xml = xml + "<DeductedAmount>"+ rs.getFloat("DEDUCTED_AMOUNT")+ "</DeductedAmount>";
				xml = xml
						+ "<netAmount>"
						+ (rs.getInt("TOTAL_SANCTIONED_AMOUNT") - rs
								.getFloat("DEDUCTED_AMOUNT"))
						+ "</netAmount>";
				xml = xml + "<major_code>"+ rs.getInt("BILL_MAJOR_TYPE")+ "</major_code>";
				xml = xml + "<major_desc>"+ rs.getString("major_desc")+ "</major_desc>";
			}
					
					
				}
				
				
				
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failur</flag>";
			}

		} else if (strCommand.equalsIgnoreCase("saveFunc")) {
			xml = xml + "<response><command>saveFunc</command>";
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
			
			String cboBillNo1 = request.getParameter("cboBillNo");
			int cboBillNo = Integer.parseInt(cboBillNo1);

			String BillDate1 = request.getParameter("txtBillDate");
			java.sql.Date BillDate = null;
			java.util.GregorianCalendar c7;
			String[] sd7 = BillDate1.split("/");
			c7 = new java.util.GregorianCalendar(Integer.parseInt(sd7[2]),
					Integer.parseInt(sd7[1]) - 1, Integer.parseInt(sd7[0]));
			java.util.Date d7 = c7.getTime();
			BillDate = new Date(d7.getTime());

			String txtTotalSanctionAmount1 = request
					.getParameter("txtTotalSanctionAmount");
			float txtTotalSanctionAmount = Float.parseFloat(txtTotalSanctionAmount1);

			String txtTotalDeductionAmount1 = request
					.getParameter("txtTotalDeductionAmount");
			float txtTotalDeductionAmount = Float
					.parseFloat(txtTotalDeductionAmount1);

			String txtNetAmount1 = request.getParameter("txtNetAmount");
			float txtNetAmount = Float.parseFloat(txtNetAmount1);
String sub_Qry="";
			java.sql.Date MTCEntryDate = null;
			java.util.GregorianCalendar c2;
			String[] sd = request.getParameter("txtMTCEntryDate").split("/");
			c2 = new java.util.GregorianCalendar(Integer.parseInt(sd[2]),
					Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c2.getTime();
			MTCEntryDate = new Date(d.getTime());

			String txtEmpID_mas1 = request.getParameter("txtEmpID_mas");
			int txtEmpID_mas = Integer.parseInt(txtEmpID_mas1);

			String mtxtRemarks = request.getParameter("mtxtRemarks");
			int ori_unit = Integer.parseInt(request.getParameter("ori_unit"));
			int ori_office = Integer.parseInt(request.getParameter("ori_office"));
		
			String sub_q = "",sub_main="";
			if((txtCB_Year==2015 && txtCB_Month>3) || (txtCB_Year>2015))
			{
				 sub_q = " FAS_BILL_REGISTER_MASTERNEW "; 
				 sub_main=" Fas_Bill_Register_MasterNEW M, "+
				" 	  Fas_Bill_Register_Transactionw T ";
			}else{
				sub_q = " FAS_BILL_REGISTER_MASTER "; 
				 sub_main=" Fas_Bill_Register_Master M, "+
							" 	  Fas_Bill_Register_Transaction T ";
			}
			
			
			try {
				String su = "select BILL_NO from FAS_MTC70_REGISTER where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_MAJOR_TYPE_CODE=? and BILL_NO=? and status='L'";
				ps2 = connection.prepareStatement(su);
				ps2.setInt(1, cmbAcc_UnitCode);
				ps2.setInt(2, cmbOffice_code);
				ps2.setInt(3, txtCB_Year);
				ps2.setInt(4, txtCB_Month);
				ps2.setInt(5, cboBillMajorType);
				ps2.setInt(6, cboBillNo);
				rs2 = ps2.executeQuery();

				if (rs2.next()) {
					xml = xml + "<flag>Exist</flag>";
				}else{
					connection.setAutoCommit(false);
				ps1 = connection.prepareStatement("insert into FAS_MTC70_REGISTER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,BILL_NO,BILL_DATE,SANCTIONED_AMOUNT,TOTAL_DEDUCTION_AMOUNT,NET,MTC70_ENTRY_DATE,MTC70_ENTRY_BY,REMARKS,UPDATED_BY_USERID,UPDATED_DATE,BILL_MAJOR_TYPE_CODE,STATUS) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				ps1.setInt(1, cmbAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setInt(3, txtCB_Year);
				ps1.setInt(4, txtCB_Month);
				ps1.setInt(5, cboBillNo);
				ps1.setDate(6, BillDate);
				ps1.setFloat(7, txtTotalSanctionAmount);
				ps1.setFloat(8, txtTotalDeductionAmount);
				ps1.setFloat(9, txtNetAmount);
				ps1.setDate(10, MTCEntryDate);
				ps1.setInt(11, txtEmpID_mas);
				ps1.setString(12, mtxtRemarks);
				ps1.setString(13, userid);
				ps1.setTimestamp(14, ts);
				ps1.setInt(15, cboBillMajorType);
				ps1.setString(16, "L");
				int k = ps1.executeUpdate();
				if (k > 0) {
					
					if(cmbOffice_code==5000){
						ps2=connection.prepareStatement("update "+sub_q+" set MTC_70_REGISTER_DATE=? where ACCOUNTING_UNIT_ID="+ori_unit+" and ACCOUNTING_UNIT_OFFICE_ID= " +ori_office+
								"and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and BILL_NO="+cboBillNo+" and MTC_70_REGISTER_DATE is null and status ='L'");
						ps2.setDate(1, MTCEntryDate);
					}else{
						System.out.println("Else part of 5000 Office iD");
						ps2=connection.prepareStatement("update "+sub_q+" set MTC_70_REGISTER_DATE=?,PRE_AUDIT_DATE=? where ACCOUNTING_UNIT_ID="+ori_unit+" and ACCOUNTING_UNIT_OFFICE_ID= " +ori_office+
								"and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and BILL_NO="+cboBillNo+" and MTC_70_REGISTER_DATE is null and status ='L' ");
						ps2.setDate(1, MTCEntryDate);
						ps2.setDate(2, MTCEntryDate);
					}
					int kk2 = ps2.executeUpdate();
					if(kk2>0)
					{	
						connection.commit();
					xml = xml + "<flag>success</flag>";
					}
					else
					{
						ps2=connection.prepareStatement("update FAS_BILL_REGISTERNEW set MTC_70_REGISTER_DATE=? where ACCOUNTING_UNIT_ID="+ori_unit+" and ACCOUNTING_UNIT_OFFICE_ID= " +ori_office+
								"and CASHBOOK_YEAR="+txtCB_Year+" and CASHBOOK_MONTH="+txtCB_Month+" and BILL_NO="+cboBillNo+" and MTC_70_REGISTER_DATE is null and status ='L' ");
						ps2.setDate(1, MTCEntryDate);
						int kk3 = ps2.executeUpdate();
						if(kk3>0)
						{	
							connection.commit();
						xml = xml + "<flag>success</flag>";
						}
						else
						{
							connection.rollback();
							xml = xml + "<flag>failur</flag>";
						}
						
						
						
						//connection.rollback();
						//xml = xml + "<flag>failur</flag>";
						
						
					}
					
					
					
					
					
				} else {
					connection.rollback();
					xml = xml + "<flag>failur</flag>";
				}
				}
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failur</flag>";
			}
		}
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}

}
