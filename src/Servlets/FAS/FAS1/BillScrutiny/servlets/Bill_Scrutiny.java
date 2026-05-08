package Servlets.FAS.FAS1.BillScrutiny.servlets;

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
import Servlets.FAS.FAS1.CivilBills.servlets.SL_TYPE_CODE_NAME;
/**
 * Servlet implementation class Bill_Scrutiny
 */
public class Bill_Scrutiny extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Bill_Scrutiny() {
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
		// TODO Auto-generated method stub
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();

		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null;
		ResultSet rs1 = null;
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

		if (strCommand.equalsIgnoreCase("gett")) {			
			xml = xml + "<response><command>gett</command>";

			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cboOffice_code = Integer.parseInt(cboOffice_code1);

			String cboCashBook_Year1 = request.getParameter("cboCashBook_Year");
			int cboCashBook_Year = Integer.parseInt(cboCashBook_Year1);

			String cboCashBook_Month1 = request.getParameter("cboCashBook_Month");
			int cboCashBook_Month = Integer.parseInt(cboCashBook_Month1);
			
			try {
				int memo=0;
				String su = "Select Bill_No From Fas_Bill_Register_Master Where Accounting_Unit_Id     ="+cboAcc_UnitCode+" And Accounting_Unit_Office_Id="+cboOffice_code+" And " +
						" Cashbook_Year            ="+cboCashBook_Year+" And Cashbook_Month           ="+cboCashBook_Month+"  AND (BILL_SCRUTINY_DONE!      ='Y' OR BILL_SCRUTINY_DONE       IS NULL OR BILL_SCRUTINY_DONE        ='N')";				
				ps = connection.prepareStatement(su);
				rs = ps.executeQuery();
				if(rs.next())
				{
					xml = xml + "<flag>success</flag>";
					try {
						String su1 = "Select Bill_No From Fas_Bill_Register_Master Where Accounting_Unit_Id     ="+cboAcc_UnitCode+" And Accounting_Unit_Office_Id="+cboOffice_code+" And " +
						" Cashbook_Year            ="+cboCashBook_Year+" And Cashbook_Month           ="+cboCashBook_Month+" and MEMO_ENTRY='Y' AND (BILL_SCRUTINY_DONE!      ='Y' OR BILL_SCRUTINY_DONE       IS NULL OR BILL_SCRUTINY_DONE        ='N')";				
						System.out.println("su1::::"+su1);
						ps1 = connection.prepareStatement(su1);
						rs1 = ps1.executeQuery();						
						while(rs1.next()) {
							memo++;
							xml = xml + "<billNo>"+ rs1.getInt("BILL_NO")+ "</billNo>";
						}	
						if(memo>0)
						{
							xml = xml + "<memo>yes</memo>";
						}
						else
						{
							xml = xml + "<memo>nodata</memo>";	
						}
						
						ps1.close();
						rs1.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
					}
				}
				else
				{
					xml = xml + "<flag>failure</flag>";
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		}else if (strCommand.equalsIgnoreCase("getDetails")) {

			xml = xml + "<response><command>getDetails</command>";
			
			int AccUnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int AccOfficeCode = Integer.parseInt(request.getParameter("cmbOffice_code"));
			int CashBookYear = Integer.parseInt(request.getParameter("cboCashBook_Year"));
			int CashBookMonth = Integer.parseInt(request.getParameter("cboCashBook_Month"));
			int BillNo = Integer.parseInt(request.getParameter("cboBillNo"));
			
			String billDate=null;
			String proceedingDate=null;
			int AccHeadCode=0;
			int subLedgerTypeCode=0;
			int subLedgerCode=0;
			try {
				String su1 = "Select M.Bill_Date, "+
							"  M.TOTAL_BILL_AMOUNT, "+
					" m.SANCTION_PROC_NO, "+
					" 	  M.PROCEEDING_RECD_DATE, "+
					" 	  T.Account_Head_Code, "+
					" 	  (select H.Account_Head_Desc from Com_Mst_Account_Heads h where H.Account_Head_Code=T.Account_Head_Code)as head_desc, "+
					" 	  M.Payee_Type_Code, "+
					" 	  (select S.Sub_Ledger_Type_Desc from com_mst_sl_types s where S.Sub_Ledger_Type_Code=M.PAYEE_TYPE_CODE)as type_desc, "+
					" 	  M.Payee_Code, "+
					" 	  (select V.Sl_Codename from sl_type_code_name_view v where V.Sl_Type=M.Payee_Type_Code and V.Sl_Code=m.Payee_Code)as code_desc, "+
					" 	  m.Remarks "+
					" 	From Fas_Bill_Register_Master M,Fas_Bill_Register_Transaction T "+
					" 	Where M.Accounting_Unit_Id=T.Accounting_Unit_Id "+
					" 	And M.Accounting_Unit_Office_Id=T.Accounting_Unit_Office_Id "+
					" 	And M.Cashbook_Year=T.Cashbook_Year "+
					" 	And M.Cashbook_Month=T.Cashbook_Month "+
					" 	and m.BILL_NO=t.BILL_NO "+
					" 	And M.Accounting_Unit_Id     ="+AccUnitCode+" 	And " +
					" M.Accounting_Unit_Office_Id="+AccOfficeCode+" 	And " +
					" M.Cashbook_Year            ="+CashBookYear+
					" 	And M.Cashbook_Month           ="+CashBookMonth+
					" 	AND m.BILL_NO                  ="+BillNo;				
				ps1 = connection.prepareStatement(su1);
//				ps1.setInt(1, AccUnitCode);
//				ps1.setInt(2, AccOfficeCode);
//				ps1.setInt(3, CashBookYear);
//				ps1.setInt(4, CashBookMonth);
//				ps1.setInt(5, BillNo);
				results = ps1.executeQuery();	
				
				if(results.next())
				{
					xml = xml + "<flag>success</flag>";
			try {
				String su = "Select M.Bill_Date, "+
				"  M.TOTAL_BILL_AMOUNT,m.TOTAL_SANCTIONED_AMOUNT, "+
		" m.SANCTION_PROC_NO, "+
		" 	  M.PROCEEDING_RECD_DATE, "+
		" 	  T.Account_Head_Code, "+
		" 	  (select H.Account_Head_Desc from Com_Mst_Account_Heads h where H.Account_Head_Code=T.Account_Head_Code)as head_desc, "+
		" 	  M.Payee_Type_Code, "+
		" 	  (select S.Sub_Ledger_Type_Desc from com_mst_sl_types s where S.Sub_Ledger_Type_Code=M.PAYEE_TYPE_CODE)as type_desc, "+
		" 	  M.Payee_Code, "+
		" 	  (select V.Sl_Codename from sl_type_code_name_view v where V.Sl_Type=M.Payee_Type_Code and V.Sl_Code=m.Payee_Code)as code_desc, "+
		" 	  m.Remarks "+
		" 	From Fas_Bill_Register_Master M,Fas_Bill_Register_Transaction T "+
		" 	Where M.Accounting_Unit_Id=T.Accounting_Unit_Id "+
		" 	And M.Accounting_Unit_Office_Id=T.Accounting_Unit_Office_Id "+
		" 	And M.Cashbook_Year=T.Cashbook_Year "+
		" 	And M.Cashbook_Month=T.Cashbook_Month "+
		" 	and m.BILL_NO=t.BILL_NO "+
		" 	And M.Accounting_Unit_Id     ="+AccUnitCode+" 	And " +
		" M.Accounting_Unit_Office_Id="+AccOfficeCode+" 	And " +
		" M.Cashbook_Year            ="+CashBookYear+
		" 	And M.Cashbook_Month           ="+CashBookMonth+
		" 	AND m.BILL_NO                  ="+BillNo;					
				ps = connection.prepareStatement(su);
				
				rs = ps.executeQuery();			

				while (rs.next()) {
					
					Date billDate1=rs.getDate("BILL_DATE");
					Date proceedingDate1=rs.getDate("PROCEEDING_RECD_DATE");
					
					String Stringdate = billDate1.toString();
					String Stringdate1 = proceedingDate1.toString();
					
					String[] ddd = Stringdate.split("-");
					String[] ddd1 = Stringdate1.split("-");
										
					int day =Integer.parseInt(ddd[2]);
					int month =Integer.parseInt(ddd[1]);
					int year = Integer.parseInt(ddd[0]);
					
					int day1 =Integer.parseInt(ddd1[2]);
					int month1 =Integer.parseInt(ddd1[1]);
					int year1 = Integer.parseInt(ddd1[0]);
					
					if(month>=10)
			        {
						billDate=(day+"/"+month+"/"+year);
			        }
			        else
			        {
			        	billDate=(day+"/0"+month+"/"+year);	
			        }
					
					if(month1>=10)
			        {
						proceedingDate=(day1+"/"+month1+"/"+year1);
			        }
			        else
			        {
			        	proceedingDate=(day1+"/0"+month1+"/"+year1);	
			        }
					AccHeadCode=rs.getInt("ACCOUNT_HEAD_CODE");
					subLedgerTypeCode=rs.getInt("Payee_Type_Code");
					subLedgerCode=rs.getInt("Payee_Code");
					
					xml = xml + "<billDate>"+ billDate+ "</billDate>";
					xml = xml + "<billAmount>"+ rs.getInt("TOTAL_BILL_AMOUNT")+ "</billAmount>";
					xml = xml + "<totalsancamt>"+ rs.getInt("TOTAL_SANCTIONED_AMOUNT")+ "</totalsancamt>";
					xml = xml + "<proceedingNo>"+ rs.getInt("SANCTION_PROC_NO")+ "</proceedingNo>";
					xml = xml + "<proceedingDate>"+ proceedingDate+ "</proceedingDate>";
					xml = xml + "<AccHeadCode>"+ AccHeadCode+ "</AccHeadCode>";
					xml = xml + "<subLedgerTypeCode>"+ subLedgerTypeCode+ "</subLedgerTypeCode>";
					xml = xml + "<subLedgerTypeCodeDesc>"+ rs.getString("type_desc")+ "</subLedgerTypeCodeDesc>";
					xml = xml + "<subLedgerCode>"+ subLedgerCode+ "</subLedgerCode>";
					xml = xml + "<subLedgerCodeDesc1>"+rs.getString("code_desc")+"</subLedgerCodeDesc1>";
					xml = xml + "<remarks>"+ rs.getString("REMARKS")+ "</remarks>";
					xml = xml + "<AccHeadCodeDesc>"+ rs.getString("head_desc")+ "</AccHeadCodeDesc>";
				}

				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			
			
			try {
				
			SL_TYPE_CODE_NAME slTypeCode= new SL_TYPE_CODE_NAME();
			ResultSet rs_get = slTypeCode.getResult(AccUnitCode, AccOfficeCode, subLedgerTypeCode, subLedgerCode);
			while(rs_get.next())
			{
			int ii = rs_get.getInt(1);
			String ss = rs_get.getString(2);
			
			xml = xml + "<subLedgerCodeDesc1>"
			+ ss
			+ "</subLedgerCodeDesc1>";
			
			}
						
		} catch (Exception e) {
			// TODO: handle exception
			e.printStackTrace();
		}	
				}
				else{
					xml = xml + "<flag>NoData</flag>";
				}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		}
		
		if (strCommand.equalsIgnoreCase("saveFunc")) {
			xml = xml + "<response><command>saveFunc</command>";
			
			int AccUnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int AccOfficeCode = Integer.parseInt(request.getParameter("cmbOffice_code"));
			int CashBookYear = Integer.parseInt(request.getParameter("cboCashBook_Year"));
			int CashBookMonth = Integer.parseInt(request.getParameter("cboCashBook_Month"));
			int BillNo = Integer.parseInt(request.getParameter("cboBillNo"));
			System.out.println("BillNo---------BillNo--------->"+BillNo);
			int DeductedAmount = Integer.parseInt(request.getParameter("DeductedAmount"));
			int NetAmount = Integer.parseInt(request.getParameter("NetAmount"));
			
			String rdoScrutinyDone=request.getParameter("BillScrunityDone");
			System.out.println("rdoScrutinyDone------------rdoScrutinyDone--------->>>"+rdoScrutinyDone);
			
			java.sql.Date BillScrunityDate = null;
			java.util.GregorianCalendar c3;
			String[] sd3 = request.getParameter("BillScrunityDate").split("/");
			c3 = new java.util.GregorianCalendar(Integer.parseInt(sd3[2]),
					Integer.parseInt(sd3[1]) - 1, Integer.parseInt(sd3[0]));
			java.util.Date d3 = c3.getTime();
			BillScrunityDate = new Date(d3.getTime());
			
						
			try {
				ps2 = connection.prepareStatement("update FAS_BILL_REGISTER_MASTER set BILL_SCRUTINY_DONE=?,BILL_SCRUTINY_BY=?,BILL_SCRUTINY_DATE=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,DEDUCTED_AMOUNT=?,NET_AMOUNT=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_UNIT_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and BILL_NO=?");
				ps2.setString(1, rdoScrutinyDone);
				ps2.setInt(2, empid);
				ps2.setDate(3, BillScrunityDate);	
				ps2.setInt(4, empid);
				ps2.setTimestamp(5, ts);
				ps2.setInt(6, DeductedAmount);
				ps2.setInt(7, NetAmount);
				ps2.setInt(8, AccUnitCode);
				ps2.setInt(9, AccOfficeCode);
				ps2.setInt(10, CashBookYear);
				ps2.setInt(11, CashBookMonth);
				ps2.setInt(12, BillNo);				
				ps2.executeUpdate();
				
				xml = xml + "<flag>success</flag>";
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
