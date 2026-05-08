package Servlets.FAS.FAS1.CivilBills.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
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
 * Servlet implementation class Pre_Audit_Check_List_MST
 */
public class Pre_Audit_Check_List_MST extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Pre_Audit_Check_List_MST() {
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
		
		if (strCommand.equalsIgnoreCase("getBillMajorType")) {
			xml = xml + "<response><command>getBillMajorType</command>";
			try {

				String su = "select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where STATUS='L'";
				ps = connection.prepareStatement(su);
				rs = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (rs.next()) {

					xml = xml + "<billMajorTypeCode>"
							+ rs.getInt("BILL_MAJOR_TYPE_CODE")
							+ "</billMajorTypeCode>";

					xml = xml + "<billMajorTypeDesc>"
							+ rs.getString("BILL_MAJOR_TYPE_DESC")
							+ "</billMajorTypeDesc>";
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			
			try {
				ps1 = connection.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CHECK_LIST_CODE,CHECK_LIST_DESC,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,MANDATE,NOT_APPLY,STATUS from FAS_PRE_AUDIT_CHKLST_MST  order by CHECK_LIST_CODE");
				results = ps1.executeQuery();

				xml = xml + "<flag>success</flag>";
				while(results.next())
				{
				xml = xml + "<AccountingUnit>"
						+ results.getInt("ACCOUNTING_UNIT_ID")
						+ "</AccountingUnit>";
				xml = xml + "<AccountingForOffice>"
						+ results.getInt("ACCOUNTING_FOR_OFFICE_ID")
						+ "</AccountingForOffice>";
				xml = xml + "<CheckListCode>"
						+ results.getInt("CHECK_LIST_CODE")
						+ "</CheckListCode>";
				xml = xml + "<CheckListDesc>"
						+ results.getString("CHECK_LIST_DESC")
						+ "</CheckListDesc>";
				xml = xml + "<BillMajorType>"
						+ results.getInt("BILL_MAJOR_TYPE_CODE")
						+ "</BillMajorType>";
				xml = xml + "<BillMinorType>"
						+ results.getInt("BILL_MINOR_TYPE_CODE")
						+ "</BillMinorType>";
				xml = xml + "<BillSubType>"
						+ results.getInt("BILL_SUB_TYPE_CODE")
						+ "</BillSubType>";
				xml = xml + "<Mandate>" + results.getString("MANDATE")
						+ "</Mandate>";
				xml = xml + "<NotApplicable>" + results.getString("NOT_APPLY")
						+ "</NotApplicable>";
				xml = xml + "<status>" + results.getString("STATUS")
						+ "</status>";
				}

			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			
			int i = 1, i1 = 0;
			try {
				ps1 = connection
						.prepareStatement("Select max(CHECK_LIST_CODE) from FAS_PRE_AUDIT_CHKLST_MST");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					System.out.println("count:-----------" + i1);
					i = i + i1;

				} else {
					i = i;
				}

				System.out.println("iiiiiiiii--------" + i);
				xml = xml + "<chklstID>" + i + "</chklstID>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();

			}
			
		}else if (strCommand.equalsIgnoreCase("getBillMinorType")) {

			xml = xml + "<response><command>getBillMinorType</command>";
			int cboBillMajorType = Integer.parseInt(request.getParameter("cboBillMajorType"));		
			try {
				String su1 = "select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=?";
				ps1 = connection.prepareStatement(su1);
				ps1.setInt(1, cboBillMajorType);
				results = ps1.executeQuery();
				if (results.next()) {
					try {

						String su = "select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=?";
						ps = connection.prepareStatement(su);
						ps.setInt(1, cboBillMajorType);
						rs = ps.executeQuery();
						xml = xml + "<flag>success</flag>";
						while (rs.next()) {

							xml = xml + "<billMinorTypeCode>"
									+ rs.getInt("BILL_MINOR_TYPE_CODE")
									+ "</billMinorTypeCode>";

							xml = xml + "<billMinorTypeDesc>"
									+ rs.getString("BILL_MINOR_TYPE_DESC")
									+ "</billMinorTypeDesc>";
						}
						ps.close();
						rs.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						xml = xml + "<flag>failure</flag>";
					}
				} else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}

		} else if (strCommand.equalsIgnoreCase("getBillsubType")) {

			xml = xml + "<response><command>getBillsubType</command>";
			int cboBillMajorType = Integer.parseInt(request
					.getParameter("cboBillMajorType"));
			int cboBillMinorType = Integer.parseInt(request
					.getParameter("cboBillMinorType"));
			try {

				String su1 = "select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=?";
				ps1 = connection.prepareStatement(su1);
				ps1.setInt(1, cboBillMajorType);
				ps1.setInt(2, cboBillMinorType);
				results = ps1.executeQuery();
				if (results.next()) {
					try {

						String su = "select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=?";
						ps = connection.prepareStatement(su);
						ps.setInt(1, cboBillMajorType);
						ps.setInt(2, cboBillMinorType);
						rs = ps.executeQuery();
						xml = xml + "<flag>success</flag>";
						while (rs.next()) {

							xml = xml + "<billSubTypeCode>"
									+ rs.getInt("BILL_SUB_TYPE_CODE")
									+ "</billSubTypeCode>";

							xml = xml + "<billsubTypeDesc>"
									+ rs.getString("BILL_SUB_TYPE_DESC")
									+ "</billsubTypeDesc>";
						}
						ps.close();
						rs.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						xml = xml + "<flag>failure</flag>";
					}
				} else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		}else if (strCommand.equalsIgnoreCase("add")) {

			xml = "<response><command>add</command>";
			
//			String cmbAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
//			int cmbAcc_UnitCode = Integer.parseInt(cmbAcc_UnitCode1);
//			
//			String cmbOffice_code1 = request.getParameter("cmbOffice_code");
//			int cmbOffice_code = Integer.parseInt(cmbOffice_code1);	
			
			int txtCheckListCode = Integer.parseInt(request.getParameter("txtCheckListCode"));
			
			String mtxtCheckListDesc = request.getParameter("mtxtCheckListDesc");
			
			int cboBillMajorType = Integer.parseInt(request.getParameter("cboBillMajorType"));
			int cboBillMinorType = Integer.parseInt(request.getParameter("cboBillMinorType"));
			int cboBillSubType = Integer.parseInt(request.getParameter("cboBillSubType"));
			
			String rdoMandate = request.getParameter("rdoMandate");
			String rdoNotApplicable = request.getParameter("rdoNotApplicable");
			
			int month = Integer.parseInt(request.getParameter("month"));
			int year2 = Integer.parseInt(request.getParameter("year"));
			
			String year = request.getParameter("year");
			String year1 = request.getParameter("year1");
			String financialYear1 = (year + "-" + (year1.substring(2, 4)));
			
			int i = 1, i1 = 0;
			try {
				ps1 = connection
						.prepareStatement("Select max(CHECK_LIST_CODE) from FAS_PRE_AUDIT_CHKLST_MST");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					i = i + i1;

				} else {
					i = i;
				}

				xml = xml + "<chklstID>" + i + "</chklstID>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();

			}

			try {
//				ps = connection
//						.prepareStatement("insert into FAS_PRE_AUDIT_CHKLST_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,CASHBOOK_YEAR,CASHBOOK_MONTH,CHECK_LIST_CODE,CHECK_LIST_DESC,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,MANDATE,NOT_APPLY,STATUS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				
				ps = connection
						.prepareStatement("insert into FAS_PRE_AUDIT_CHKLST_MST(FINANCIAL_YEAR,CASHBOOK_YEAR,CASHBOOK_MONTH,CHECK_LIST_CODE,CHECK_LIST_DESC,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,MANDATE,NOT_APPLY,STATUS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
				
				
//				ps.setInt(1, cmbAcc_UnitCode);
//				ps.setInt(2, cmbOffice_code);
				ps.setString(1, financialYear1);
				ps.setInt(2, year2);
				ps.setInt(3, month);
				ps.setInt(4, txtCheckListCode);
				ps.setString(5, mtxtCheckListDesc);
				ps.setInt(6, cboBillMajorType);
				ps.setInt(7, cboBillMinorType);
				ps.setInt(8, cboBillSubType);
				ps.setString(9, rdoMandate);
				ps.setString(10, rdoNotApplicable);
				ps.setString(11, "L");
				ps.setInt(12, empid);
				ps.setTimestamp(13, ts);

				ps.executeUpdate();

				xml = xml + "<flag>success</flag>";
//				xml = xml + "<AccountingUnit>" + cmbAcc_UnitCode
//						+ "</AccountingUnit>";
//				xml = xml + "<AccountingForOffice>" + cmbOffice_code
//						+ "</AccountingForOffice>";				
				xml = xml + "<CheckListCode>" + i + "</CheckListCode>";
				xml = xml + "<CheckListDesc>" + mtxtCheckListDesc + "</CheckListDesc>";
				xml = xml + "<BillMajorType>" + cboBillMajorType + "</BillMajorType>";
				xml = xml + "<BillMinorType>" + cboBillMinorType + "</BillMinorType>";
				
				xml = xml + "<BillSubType>" + cboBillSubType + "</BillSubType>";
				xml = xml + "<Mandate>" + rdoMandate + "</Mandate>";
				xml = xml + "<NotApplicable>" + rdoNotApplicable + "</NotApplicable>";

			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		}else if (strCommand.equalsIgnoreCase("deleted")) {

			xml = "<response><command>deleted</command>";
			int txtCheckListCode = Integer.parseInt(request.getParameter("txtCheckListCode"));
			try {
				//ps = connection.prepareStatement("delete from FAS_PRE_AUDIT_CHKLST_MST where CHECK_LIST_CODE=?");
				ps = connection.prepareStatement("update FAS_PRE_AUDIT_CHKLST_MST set STATUS='C' where CHECK_LIST_CODE=?");
				ps.setInt(1, txtCheckListCode);
				ps.executeUpdate();
				xml = xml + "<flag>success</flag>";
				xml = xml + "<id>" + txtCheckListCode + "</id>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
			}		
			
			int i = 1, i1 = 0;
			try {
				ps1 = connection
						.prepareStatement("Select max(CHECK_LIST_CODE) from FAS_PRE_AUDIT_CHKLST_MST");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					i = i + i1;

				} else {
					i = i;
				}

				xml = xml + "<chklstID>" + i + "</chklstID>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();

			}
		} else if (strCommand.equalsIgnoreCase("ClearAll")) {

			xml = "<response><command>ClearAll</command>";			
			int i = 1, i1 = 0;
			try {
				ps1 = connection
						.prepareStatement("Select max(CHECK_LIST_CODE) from FAS_PRE_AUDIT_CHKLST_MST");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					i = i + i1;

				} else {
					i = i;
				}
				xml = xml + "<chklstID>" + i + "</chklstID>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();

			}
		} else if (strCommand.equalsIgnoreCase("Edit")) {

			xml = "<response><command>Edit</command>";
			int CheckListCode = Integer.parseInt(request.getParameter("CheckListCode"));			
			
			int BillMajorType = 0, BillMinorType = 0, BillSubType=0;
			try {
				ps1 = connection
						.prepareStatement("Select BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE from FAS_PRE_AUDIT_CHKLST_MST where CHECK_LIST_CODE=?");				
				ps1.setInt(1, CheckListCode);
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";
				while(results2.next())
				{
					BillMajorType = results2.getInt("BILL_MAJOR_TYPE_CODE");
					BillMinorType = results2.getInt("BILL_MINOR_TYPE_CODE");
					BillSubType = results2.getInt("BILL_SUB_TYPE_CODE");
				}
				ps1.close();
				results2.close();
			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();

			}
			try {

				String su1 = "select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=?";
				ps1 = connection.prepareStatement(su1);
				ps1.setInt(1, BillMajorType);
				ps1.setInt(2, BillMinorType);
				results = ps1.executeQuery();
				if (results.next()) {
					try {

						String su = "select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=?";
						ps = connection.prepareStatement(su);
						ps.setInt(1, BillMajorType);
						ps.setInt(2, BillMinorType);
						rs = ps.executeQuery();
						xml = xml + "<flag>success</flag>";
						while (rs.next()) {

							xml = xml + "<billMinorTypeCode>"
									+ rs.getInt("BILL_MINOR_TYPE_CODE")
									+ "</billMinorTypeCode>";

							xml = xml + "<billMinorTypeDesc>"
									+ rs.getString("BILL_MINOR_TYPE_DESC")
									+ "</billMinorTypeDesc>";
						}
						ps.close();
						rs.close();
					} catch (Exception e) {
						// TODO: handle exception
						e.printStackTrace();
						xml = xml + "<flag>failure</flag>";
					}} else {
						xml = xml + "<flag>NoData</flag>";
					}
				} catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					xml = xml + "<flag>failure</flag>";
				}
					
					try {

						String su1 = "select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and BILL_SUB_TYPE_CODE=?";
						ps1 = connection.prepareStatement(su1);
						ps1.setInt(1, BillMajorType);
						ps1.setInt(2, BillMinorType);
						ps1.setInt(3, BillSubType);
						results = ps1.executeQuery();
						if (results.next()) {
							try {

								String su = "select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and BILL_SUB_TYPE_CODE=?";
								ps = connection.prepareStatement(su);
								ps.setInt(1, BillMajorType);
								ps.setInt(2, BillMinorType);
								ps.setInt(3, BillSubType);
								rs = ps.executeQuery();
								xml = xml + "<flag>success</flag>";
								while (rs.next()) {

									xml = xml + "<billSubTypeCode>"
											+ rs.getInt("BILL_SUB_TYPE_CODE")
											+ "</billSubTypeCode>";

									xml = xml + "<billsubTypeDesc>"
											+ rs.getString("BILL_SUB_TYPE_DESC")
											+ "</billsubTypeDesc>";
								}
								ps.close();
								rs.close();
							} catch (Exception e) {
								// TODO: handle exception
								e.printStackTrace();
								xml = xml + "<flag>failure</flag>";
							}} else {
								xml = xml + "<flag>NoData</flag>";
							}
						} catch (Exception e) {
							// TODO: handle exception
							e.printStackTrace();
							xml = xml + "<flag>failure</flag>";
						}
			
		}else if (strCommand.equalsIgnoreCase("update")) {

			xml = "<response><command>update</command>";
			
//			String cmbAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
//			int cmbAcc_UnitCode = Integer.parseInt(cmbAcc_UnitCode1);
//			
//			String cmbOffice_code1 = request.getParameter("cmbOffice_code");
//			int cmbOffice_code = Integer.parseInt(cmbOffice_code1);	
			
			int txtCheckListCode = Integer.parseInt(request.getParameter("txtCheckListCode"));
			
			String mtxtCheckListDesc = request.getParameter("mtxtCheckListDesc");
			
			int cboBillMajorType = Integer.parseInt(request.getParameter("cboBillMajorType"));
			int cboBillMinorType = Integer.parseInt(request.getParameter("cboBillMinorType"));
			int cboBillSubType = Integer.parseInt(request.getParameter("cboBillSubType"));
			
			String rdoMandate = request.getParameter("rdoMandate");
			String rdoNotApplicable = request.getParameter("rdoNotApplicable");
			
			int month = Integer.parseInt(request.getParameter("month"));
			int year2 = Integer.parseInt(request.getParameter("year"));
			
			String year = request.getParameter("year");
			String year1 = request.getParameter("year1");
			String financialYear1 = (year + "-" + year1);

			int i = 1, i1 = 0;
			try {
				ps1 = connection
						.prepareStatement("Select max(CHECK_LIST_CODE) from FAS_PRE_AUDIT_CHKLST_MST");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					i = i + i1;

				} else {
					i = i;
				}
				xml = xml + "<chklstID>" + i + "</chklstID>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();

			}
			try {
//				ps = connection
//						.prepareStatement("update FAS_PRE_AUDIT_CHKLST_MST set ACCOUNTING_UNIT_ID=?,ACCOUNTING_FOR_OFFICE_ID=?,FINANCIAL_YEAR=?,CASHBOOK_YEAR=?,CASHBOOK_MONTH=?,CHECK_LIST_DESC=?,BILL_MAJOR_TYPE_CODE=?,BILL_MINOR_TYPE_CODE=?,BILL_SUB_TYPE_CODE=?,MANDATE=?,NOT_APPLY=?,STATUS=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where CHECK_LIST_CODE=?");
				
				ps = connection
						.prepareStatement("update FAS_PRE_AUDIT_CHKLST_MST set FINANCIAL_YEAR=?,CASHBOOK_YEAR=?,CASHBOOK_MONTH=?,CHECK_LIST_DESC=?,BILL_MAJOR_TYPE_CODE=?,BILL_MINOR_TYPE_CODE=?,BILL_SUB_TYPE_CODE=?,MANDATE=?,NOT_APPLY=?,STATUS=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where CHECK_LIST_CODE=?");
				
//				ps.setInt(1, cmbAcc_UnitCode);
//				ps.setInt(2, cmbOffice_code);
				ps.setString(1, financialYear1);
				ps.setInt(2, year2);
				ps.setInt(3, month);				
				ps.setString(4, mtxtCheckListDesc);
				ps.setInt(5, cboBillMajorType);
				ps.setInt(6, cboBillMinorType);
				ps.setInt(7, cboBillSubType);
				ps.setString(8, rdoMandate);
				ps.setString(9, rdoNotApplicable);
				ps.setString(10, "L");
				ps.setInt(11, empid);
				ps.setTimestamp(12, ts);
				ps.setInt(13, txtCheckListCode);
				ps.executeUpdate();
				xml = xml + "<flag>success</flag>";
//				xml = xml + "<AccountingUnit>" + cmbAcc_UnitCode
//				+ "</AccountingUnit>";
//				xml = xml + "<AccountingForOffice>" + cmbOffice_code
//						+ "</AccountingForOffice>";				
				xml = xml + "<CheckListCode>" + txtCheckListCode + "</CheckListCode>";
				xml = xml + "<CheckListDesc>" + mtxtCheckListDesc + "</CheckListDesc>";
				xml = xml + "<BillMajorType>" + cboBillMajorType + "</BillMajorType>";
				xml = xml + "<BillMinorType>" + cboBillMinorType + "</BillMinorType>";
				
				xml = xml + "<BillSubType>" + cboBillSubType + "</BillSubType>";
				xml = xml + "<Mandate>" + rdoMandate + "</Mandate>";
				xml = xml + "<NotApplicable>" + rdoNotApplicable + "</NotApplicable>";
				
				ps.close();
			} catch (Exception e) {
				System.out.println("exception in update is" + e);

			}
		}
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}

}
