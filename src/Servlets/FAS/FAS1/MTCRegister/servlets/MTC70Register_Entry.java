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
import java.text.SimpleDateFormat;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class MTC70RegisterEntry
 */
public class MTC70Register_Entry extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MTC70Register_Entry() {
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

		if (strCommand.equalsIgnoreCase("gett")) {

			xml = xml + "<response><command>gett</command>";
			xml = xml + "<empid>" + empid + "</empid>";
			xml = xml + "<empName>" + empName + "</empName>";
			System.out.println("strCommand:--------" + strCommand);

			int i = 1, i1 = 0;

			try {
				ps1 = connection
						.prepareStatement("Select max(MTC70_REGISTER_NO) from FAS_MTC70_REGISTER_MST");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					System.out.println("count:-----------" + i1);
					i = i + i1;

				} else {
					i = i;
				}
				xml = xml + "<MTCRegisterNO>" + i + "</MTCRegisterNO>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();

			}

		} else if (strCommand.equalsIgnoreCase("PODate")) {

			xml = xml + "<response><command>PODate</command>";

			String cboAcc_UnitCode1 = request.getParameter("cboAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cboOffice_code");
			int cboOffice_code = Integer.parseInt(cboOffice_code1);

			String cboCashBook_Year1 = request.getParameter("cboCashBook_Year");
			int cboCashBook_Year = Integer.parseInt(cboCashBook_Year1);

			String cboCashBook_Month = request
					.getParameter("cboCashBook_Month");

			String poNo = request.getParameter("cboPassOrderNo");
			int poNo1 = Integer.parseInt(poNo);
			String passOrderDate=null;
			try {
				String su = "select PASS_ORDER_DATE from FAS_PASS_ORDER_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and PASS_ORDER_NO=?";
				ps = connection.prepareStatement(su);
				ps.setInt(1, cboAcc_UnitCode);
				ps.setInt(2, cboOffice_code);
				ps.setInt(3, cboCashBook_Year);
				ps.setString(4, cboCashBook_Month);
				ps.setInt(5, poNo1);
				rs = ps.executeQuery();
				while (rs.next()) {
					Date passOrderDate1=rs.getDate("PASS_ORDER_DATE");
					System.out.println("Date"+passOrderDate1);
					
					String Stringdate = passOrderDate1.toString();
					String[] ddd = Stringdate.split("-");
					
					//c2 = new java.util.GregorianCalendar(Integer.parseInt(sd[2]),
						//	Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
					
					int day =Integer.parseInt(ddd[2]);
					int month =Integer.parseInt(ddd[1]);
					int year = Integer.parseInt(ddd[0]);
					
					if(month>=10)
			        {
						passOrderDate=(day+"/"+month+"/"+year);
			        }
			        else
			        {
			        	passOrderDate=(day+"/0"+month+"/"+year);	
			        }	   
					
					System.out.println(passOrderDate);
															
					xml = xml + "<passOrderDate>"
							+ passOrderDate
							+ "</passOrderDate>";
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			try {				
				String billDate=null;
				String proceedingDate=null;
				String su = "select SANCTION_PROC_NO,SANCTION_PROC_DATE,BILL_NO,BILL_DATE,BILL_AMOUNT,REMARKS from FAS_PASS_ORDER_TRN where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and PASS_ORDER_NO=? order by BILL_NO";
				ps = connection.prepareStatement(su);
				ps.setInt(1, cboAcc_UnitCode);
				ps.setInt(2, cboOffice_code);
				ps.setInt(3, cboCashBook_Year);
				ps.setString(4, cboCashBook_Month);
				ps.setInt(5, poNo1);
				rs = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (rs.next()) {
					Date billDate1=rs.getDate("BILL_DATE");
					Date proceedingDate1= rs.getDate("SANCTION_PROC_DATE");
					
					
					String Stringdate = billDate1.toString();
					String Stringdate1 = proceedingDate1.toString();
					
					String[] ddd = Stringdate.split("-");
					String[] ddd1 = Stringdate1.split("-");
					
					//c2 = new java.util.GregorianCalendar(Integer.parseInt(sd[2]),
						//	Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
					
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
					xml = xml + "<proceedingNo>"
							+ rs.getInt("SANCTION_PROC_NO") + "</proceedingNo>";

					xml = xml + "<proceedingDate>"
							+ proceedingDate
							+ "</proceedingDate>";

					xml = xml + "<billNo>" + rs.getInt("BILL_NO") + "</billNo>";

					xml = xml + "<billDate>" + billDate
							+ "</billDate>";

					xml = xml + "<billAmount>" + rs.getFloat("BILL_AMOUNT")
							+ "</billAmount>";

					xml = xml + "<Remarks>" + rs.getString("REMARKS")
							+ "</Remarks>";

				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
			try {
				
				String su = "select APPROVED_BY from FAS_PASS_ORDER_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and PASS_ORDER_NO=?";
				ps = connection.prepareStatement(su);
				ps.setInt(1, cboAcc_UnitCode);
				ps.setInt(2, cboOffice_code);
				ps.setInt(3, cboCashBook_Year);
				ps.setString(4, cboCashBook_Month);
				ps.setInt(5, poNo1);
				rs = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (rs.next()) {
					
					xml = xml + "<approvedBy>" + rs.getString("APPROVED_BY")
							+ "</approvedBy>";
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
			}
		} else if (strCommand.equalsIgnoreCase("PONo")) {

			xml = xml + "<response><command>PONo</command>";

			String cboAcc_UnitCode1 = request.getParameter("cboAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cboOffice_code");
			int cboOffice_code = Integer.parseInt(cboOffice_code1);

			String cboCashBook_Year1 = request.getParameter("cboCashBook_Year");
			int cboCashBook_Year = Integer.parseInt(cboCashBook_Year1);

			String cboCashBook_Month = request
					.getParameter("cboCashBook_Month");
			
          try {
				
				String su1 = "select PASS_ORDER_NO from FAS_PASS_ORDER_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? order by PASS_ORDER_NO";
				ps1 = connection.prepareStatement(su1);
				ps1.setInt(1, cboAcc_UnitCode);
				ps1.setInt(2, cboOffice_code);
				ps1.setInt(3, cboCashBook_Year);
				ps1.setString(4, cboCashBook_Month);
				results = ps1.executeQuery();
				if(results.next())
				{
			try {
				
				String su = "select PASS_ORDER_NO from FAS_PASS_ORDER_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? order by PASS_ORDER_NO";
				ps = connection.prepareStatement(su);
				ps.setInt(1, cboAcc_UnitCode);
				ps.setInt(2, cboOffice_code);
				ps.setInt(3, cboCashBook_Year);
				ps.setString(4, cboCashBook_Month);
				rs = ps.executeQuery();				
				while (rs.next()) {
					xml = xml + "<flag> success </flag>";
					xml = xml + "<passOrderNo>" + rs.getInt("PASS_ORDER_NO")
							+ "</passOrderNo>";
				}
				
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag> failure </flag>";
			}
				}else
				{
					xml = xml + "<flag> NoData </flag>";
				}
          }
				catch (Exception e) {
					// TODO: handle exception
					e.printStackTrace();
					xml = xml + "<flag> failure </flag>";
				}
		} else if (strCommand.equalsIgnoreCase("saveFunc")) {
			xml = xml + "<response><command>saveFunc</command>";

			int qcheck = 0;
			int qcheck1 = 0;
			
			String cboAcc_UnitCode1 = request.getParameter("cboAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cboOffice_code");
			int cboOffice_code = Integer.parseInt(cboOffice_code1);

			String cboCashBook_Year1 = request.getParameter("cboCashBook_Year");
			int cboCashBook_Year = Integer.parseInt(cboCashBook_Year1);

			String cboCashBook_Month = request
					.getParameter("cboCashBook_Month");

			String poNo = request.getParameter("cboPassOrderNo");
			int poNo1 = Integer.parseInt(poNo);

			java.sql.Date MTCEntryDate = null;
			java.util.GregorianCalendar c2;
			String[] sd = request.getParameter("txtMTCEntryDate").split("/");
			c2 = new java.util.GregorianCalendar(Integer.parseInt(sd[2]),
					Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c2.getTime();
			MTCEntryDate = new Date(d.getTime());

			String cboEnteredBy1 = request.getParameter("cboEnteredBy");
			int cboEnteredBy = Integer.parseInt(cboEnteredBy1);

			
			
			
			
			java.sql.Date PassOrderDate = null;
			java.util.GregorianCalendar c5;
			String[] sd5 = request.getParameter("txtPassOrderDate").split("/");
			c5 = new java.util.GregorianCalendar(Integer.parseInt(sd5[2]),
					Integer.parseInt(sd5[1]) - 1, Integer.parseInt(sd5[0]));
			java.util.Date d5 = c5.getTime();
			PassOrderDate = new Date(d5.getTime());

			String txtMTCRegisterNO1 = request.getParameter("txtMTCRegisterNO");
			int txtMTCRegisterNO = Integer.parseInt(txtMTCRegisterNO1);

			String txtRefNo1 = request.getParameter("txtRefNo");
			int txtRefNo = Integer.parseInt(txtRefNo1);
			
			java.sql.Date RefDate = null;
			java.util.GregorianCalendar cc5;
			String[] sdc5 = request.getParameter("txtRefDate").split("/");
			cc5 = new java.util.GregorianCalendar(Integer.parseInt(sdc5[2]),Integer.parseInt(sdc5[1]) - 1,Integer.parseInt(sdc5[0]));
			java.util.Date dc5 = cc5.getTime();
			RefDate = new Date(dc5.getTime());

			String mtxtRemarks = request.getParameter("mtxtRemarks");

			String txtApprovedBy1 = request.getParameter("txtApprovedBy");
			int txtApprovedBy = Integer.parseInt(txtApprovedBy1);

			int proceedingNo = 0;
			java.sql.Date ProceedingOrderDate = null;
			int BillNo = 0;
			java.sql.Date BillDate = null;
			float BillAmount = 0;
			String Remarks1 = null;

			String al = request.getParameter("al");
			String[] al1 = request.getParameter("al").split(",");
			int k = 0;


				try {
					
					while (k < al1.length) {
						proceedingNo = Integer.parseInt(al1[k]);
						
						
						String ProceedingOrderDate1 = al1[k + 1];
						java.sql.Date ProceedingOrderDate2 = null;
						java.util.GregorianCalendar c6;
						String[] sd6 = ProceedingOrderDate1.split("/");
						c6 = new java.util.GregorianCalendar(Integer.parseInt(sd6[2]),
								Integer.parseInt(sd6[1]) - 1, Integer.parseInt(sd6[0]));
						java.util.Date d6 = c6.getTime();
						ProceedingOrderDate2 = new Date(d6.getTime());

						BillNo = Integer.parseInt(al1[k + 2]);

						String BillDate1 = al1[k + 3];

						
						java.sql.Date BillDate2 = null;
						java.util.GregorianCalendar c7;
						String[] sd7 = BillDate1.split("/");
						c7 = new java.util.GregorianCalendar(Integer.parseInt(sd7[2]),
								Integer.parseInt(sd7[1]) - 1, Integer.parseInt(sd7[0]));
						java.util.Date d7 = c7.getTime();
						BillDate2 = new Date(d7.getTime());
						
						BillAmount = Float.parseFloat(al1[k + 4]);

						Remarks1 = al1[k + 5];

						k = k + 6;
						
					connection.setAutoCommit(false);
					ps1 = connection
							.prepareStatement("insert into FAS_MTC70_REGISTER_TRN(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,MTC70_REGISTER_NO,BILL_NO,BILL_DATE,BILL_AMOUNT,SANCTION_PROC_NO,SANCTION_PROC_DATE,APPROVED_BY,REMARKS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					ps1.setInt(1, cboAcc_UnitCode);
					ps1.setInt(2, cboOffice_code);
					ps1.setInt(3, cboCashBook_Year);
					ps1.setString(4, cboCashBook_Month);
					ps1.setInt(5, txtMTCRegisterNO);

					ps1.setInt(6, BillNo);
					ps1.setDate(7, BillDate2);
					ps1.setFloat(8, BillAmount);
					ps1.setInt(9, proceedingNo);
					ps1.setDate(10, ProceedingOrderDate2);
					ps1.setInt(11, txtApprovedBy);
					ps1.setString(12, Remarks1);
					ps1.setString(13, userid);
					ps1.setTimestamp(14, ts);
					qcheck = ps1.executeUpdate();
				} 
					
				ps = connection
						.prepareStatement("insert into FAS_MTC70_REGISTER_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,MTC70_REGISTER_NO,MTC70_REGISTER_DATE,ENTERED_BY,PASS_ORDER_NO,PASS_ORDER_DATE,REF_NO,REF_DATE,STATUS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				ps.setInt(1, cboAcc_UnitCode);
				ps.setInt(2, cboOffice_code);
				ps.setInt(3, cboCashBook_Year);
				ps.setString(4, cboCashBook_Month);
				ps.setInt(5, txtMTCRegisterNO);
				ps.setDate(6, MTCEntryDate);
				ps.setInt(7, cboEnteredBy);
				ps.setInt(8, poNo1);
				ps.setDate(9, PassOrderDate);
				ps.setInt(10, txtRefNo);
				ps.setDate(11, RefDate);
				ps.setString(12, "L");
				ps.setString(13, userid);
				ps.setTimestamp(14, ts);
				qcheck1 = ps.executeUpdate();
				xml = xml + "<flag>success</flag>";
				
				if((qcheck1>0) && (qcheck>0) )
				{
					connection.commit();
				}else{
					connection.rollback();
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
