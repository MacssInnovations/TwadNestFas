package Servlets.FAS.FAS1.ProceedingGeneration.servlets;

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
 * Servlet implementation class General_Sanction_Proceedings
 */
public class General_Sanction_Proceedings extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public General_Sanction_Proceedings() {
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
		if (strCommand.equalsIgnoreCase("SanctionProceedingNo")) {
			xml = xml + "<response><command>SanctionProceedingNo</command>";
			int cboAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int cboOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			int cboCashBook_Year = Integer.parseInt(request.getParameter("cboCashBook_Year"));
			int cboCashBook_Month = Integer.parseInt(request.getParameter("cboCashBook_Month"));
			try {

				String su1 = "select SANCTION_PROCEEDING_NO from FAS_SANC_PROC_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?";
				ps1 = connection.prepareStatement(su1);
				ps1.setInt(1, cboAcc_UnitCode);
				ps1.setInt(2, cboOffice_code);
				ps1.setInt(3, cboCashBook_Year);
				ps1.setInt(4, cboCashBook_Month);
				results = ps1.executeQuery();				
				if(results.next())
				{
			try {

				String su = "select SANCTION_PROCEEDING_NO from FAS_SANC_PROC_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?";
				ps = connection.prepareStatement(su);
				ps.setInt(1, cboAcc_UnitCode);
				ps.setInt(2, cboOffice_code);
				ps.setInt(3, cboCashBook_Year);
				ps.setInt(4, cboCashBook_Month);
				rs = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (rs.next()) {

					xml = xml + "<SanctionProceedingNo>"
							+ rs.getInt("SANCTION_PROCEEDING_NO")
							+ "</SanctionProceedingNo>";
				}
				ps.close();
				rs.close();
			}				
					catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
					}
				else
				{
					xml = xml + "<flag>Nodata</flag>";
				}
			}catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		}else if (strCommand.equalsIgnoreCase("SanctionProceedingDate")) {
			xml = xml + "<response><command>SanctionProceedingDate</command>";
			int cboAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int cboOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
			int cboCashBook_Year = Integer.parseInt(request.getParameter("cboCashBook_Year"));
			int cboCashBook_Month = Integer.parseInt(request.getParameter("cboCashBook_Month"));
			int cboSanctionProceedingNo = Integer.parseInt(request.getParameter("cboSanctionProceedingNo"));
			String proceedingDate=null;
			String SanctionProceedingAuthority1 = null;
			int SanctionProceedingAuthority = 0;
			String SanctionProceedingSactionedBy1 = null;
			int SanctionProceedingSactionedBy = 0;
			try {

				String su = "select SANCTION_PROCEEDING_DATE,SANCTION_AUTHORITY,SANCTIONED_BY,TOTLA_SANCTION_AMOUNT from FAS_SANC_PROC_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SANCTION_PROCEEDING_NO=?";
				ps = connection.prepareStatement(su);
				ps.setInt(1, cboAcc_UnitCode);
				ps.setInt(2, cboOffice_code);
				ps.setInt(3, cboCashBook_Year);
				ps.setInt(4, cboCashBook_Month);
				ps.setInt(5, cboSanctionProceedingNo);
				rs = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (rs.next()) {
                    Date proceedingDate1= rs.getDate("SANCTION_PROCEEDING_DATE");

					String Stringdate1 = proceedingDate1.toString();
					
					String[] ddd1 = Stringdate1.split("-");

					int day1 =Integer.parseInt(ddd1[2]);
					int month1 =Integer.parseInt(ddd1[1]);
					int year1 = Integer.parseInt(ddd1[0]);
					
					if(month1>=10)
			        {
						proceedingDate=(day1+"/"+month1+"/"+year1);
			        }
			        else
			        {
			        	proceedingDate=(day1+"/0"+month1+"/"+year1);
			        }	   
					xml = xml + "<SanctionProceedingDate>"
							+ proceedingDate
							+ "</SanctionProceedingDate>";									
					
					SanctionProceedingAuthority1 = rs.getString("SANCTION_AUTHORITY");
					SanctionProceedingAuthority = Integer.parseInt(SanctionProceedingAuthority1);
					
					SanctionProceedingSactionedBy1 = rs.getString("SANCTIONED_BY");
					SanctionProceedingSactionedBy = Integer.parseInt(SanctionProceedingSactionedBy1);										
					
					xml = xml + "<SanctionProceedingAmount>"
					+ rs.getInt("TOTLA_SANCTION_AMOUNT")
					+ "</SanctionProceedingAmount>";
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			
			try {

				String su1 = "select DESIGNATION from HRM_MST_DESIGNATIONS where DESIGNATION_ID=?";
				ps = connection.prepareStatement(su1);
				ps.setInt(1, SanctionProceedingAuthority);
				rs = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (rs.next()) {					
					xml = xml + "<SanctionProceedingAuthority>"
					+ rs.getString("DESIGNATION")
					+ "</SanctionProceedingAuthority>";				
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			
			try {

				String su1 = "select EMPLOYEE_NAME from HRM_MST_EMPLOYEES where EMPLOYEE_ID=?";
				ps = connection.prepareStatement(su1);
				ps.setInt(1, SanctionProceedingSactionedBy);
				rs = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (rs.next()) {					
					xml = xml + "<SanctionProceedingSactionedBy>"
					+ rs.getString("EMPLOYEE_NAME")
					+ "</SanctionProceedingSactionedBy>";		
				}
				ps.close();
				rs.close();
			} catch (Exception e) {
				// TODO: handle exception
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		}else if (strCommand.equalsIgnoreCase("saveFunc")) {
			xml = xml + "<response><command>saveFunc</command>";

			String cboAcc_UnitCode1 = request.getParameter("cmbAcc_UnitCode");
			int cboAcc_UnitCode = Integer.parseInt(cboAcc_UnitCode1);

			String cboOffice_code1 = request.getParameter("cmbOffice_code");
			int cmbOffice_code = Integer.parseInt(cboOffice_code1);

			String cboCashBook_Year1 = request.getParameter("cboCashBook_Year");
			int cboCashBook_Year = Integer.parseInt(cboCashBook_Year1);

			String cboCashBook_Month1 = request.getParameter("cboCashBook_Month");
			int cboCashBook_Month = Integer.parseInt(cboCashBook_Month1);

			String cboSanctionProceedingNo1 = request.getParameter("cboSanctionProceedingNo");
			int cboSanctionProceedingNo = Integer.parseInt(cboSanctionProceedingNo1);
			
			java.sql.Date SanctionProceedingDate = null;
			java.util.GregorianCalendar c2;
			String[] sd = request.getParameter("txtSanctionProceedingDate")
					.split("/");
			c2 = new java.util.GregorianCalendar(Integer.parseInt(sd[2]),
					Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c2.getTime();
			SanctionProceedingDate = new Date(d.getTime());

			String txtSanctioningAuthority = request.getParameter("txtSanctioningAuthority");

			String txtSanctionedBy = request.getParameter("txtSanctionedBy");

		//	String al = request.getParameter("al");
			//String[] al1 = request.getParameter("al").split(",");
			
			String txtTotalSanctionedAmount1 = request.getParameter("txtTotalSanctionedAmount");	
			int txtTotalSanctionedAmount = Integer.parseInt(txtTotalSanctionedAmount1);

			String txtPresidingOfficer = request.getParameter("txtPresidingOfficer");

			String txtPrefix = request.getParameter("txtPrefix");

			String txtSuffix = request.getParameter("txtSuffix");

			String txtPresidingOfficerDesignation = request.getParameter("txtPresidingOfficerDesignation");

			String mtxtHeader = request.getParameter("mtxtHeader");

			String mtxtSubject = request.getParameter("mtxtSubject");

			String mtxtReference = request.getParameter("mtxtReference");

			String mtxtBodyOftheProceeding = request.getParameter("mtxtBodyOftheProceeding");

			String mtxtProceedingtobeAddressedTo = request.getParameter("mtxtProceedingtobeAddressedTo");

			String mtxtCopyTo = request.getParameter("mtxtCopyTo");

			String mtxtAdditionalPara1 = request.getParameter("mtxtAdditionalPara1");

			String mtxtAdditionalPara2 = request.getParameter("mtxtAdditionalPara2");

			String mtxtSignedByWithDesignation = request.getParameter("mtxtSignedByWithDesignation");
			
			String mtxtRemarks = request.getParameter("mtxtRemarks");
			try {
			String su = "select SANCTION_PROC_NO from FAS_SANC_PROC_GEN_DETAILS where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=? and SANCTION_PROC_NO=?";
			ps = connection.prepareStatement(su);
			ps.setInt(1, cboAcc_UnitCode);
			ps.setInt(2, cmbOffice_code);
			ps.setInt(3, cboCashBook_Year);
			ps.setInt(4, cboCashBook_Month);
			ps.setInt(5, cboSanctionProceedingNo);
			rs = ps.executeQuery();						
			if(rs.next()) {
				xml = xml + "<flag>Exist</flag>";
			}else{
			try {

				ps1 = connection
						.prepareStatement("insert into FAS_SANC_PROC_GEN_DETAILS(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_YEAR,CASHBOOK_MONTH,SANCTION_PROC_NO,SANCTION_PROC_DATE,TOTAL_SANCTIONED_AMOUNT,PRESIDING_OFFICER,PREFIX,SUFFIX,PRESIDING_OFFICER_DESIGNATION	,HEADER,SUBJECT,REFERENCE,BODY_OF_PROCEEDING,TO_BE_ADDRESSED_TO,COPY_TO,ADDITIONAL_PARA_1,ADDITIONAL_PARA_2,SIGNED_BY,SIGNED_DESIGNATION,REMARKS,STATUS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
				ps1.setInt(1, cboAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setInt(3, cboCashBook_Year);
				ps1.setInt(4, cboCashBook_Month);
				ps1.setInt(5, cboSanctionProceedingNo);
				ps1.setDate(6, SanctionProceedingDate);
				ps1.setInt(7, txtTotalSanctionedAmount);
				ps1.setString(8, txtPresidingOfficer);
				ps1.setString(9, txtPrefix);
				ps1.setString(10, txtSuffix);
				ps1.setString(11, txtPresidingOfficerDesignation);
				ps1.setString(12, mtxtHeader);
				ps1.setString(13, mtxtSubject);
				ps1.setString(14, mtxtReference);
				ps1.setString(15, mtxtBodyOftheProceeding);
				ps1.setString(16, mtxtProceedingtobeAddressedTo);
				ps1.setString(17, mtxtCopyTo);
				ps1.setString(18, mtxtAdditionalPara1);
				ps1.setString(19, mtxtAdditionalPara2);
				ps1.setString(20, txtPresidingOfficer);
				ps1.setString(21, txtPresidingOfficerDesignation);
				ps1.setString(22, mtxtRemarks);
				ps1.setString(23, "L");
				ps1.setInt(24, empid);
				ps1.setTimestamp(25, ts);
				
				ps1.executeUpdate();
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			}
		}catch (Exception e) {
			xml = xml + "<flag>failure</flag>";
			e.printStackTrace();
		}
		}
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}

}
