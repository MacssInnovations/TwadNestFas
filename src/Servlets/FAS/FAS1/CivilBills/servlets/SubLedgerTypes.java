package Servlets.FAS.FAS1.CivilBills.servlets;

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
import java.text.SimpleDateFormat;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class SubLedgerTypes
 */
public class SubLedgerTypes extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public SubLedgerTypes() {
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

		System.out.println("chk 1");
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2;
		ResultSet rs = null;
		ResultSet rs2 = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null;

		String cboAccountingUnit1 = null;
		String cboAccountingForOffice1 = null;
		String txtOwnersCode1 = null;
		int cmbAcc_UnitCode;
		int cmbOffice_code;
		int txtOwnersCode;
		String txtOwnersName = null;
		String txtRentAgreementPeriodFrom = null;
		String txtRentAgreementPeriodTo = null;
		String txtAdvancePaid1 = null;
		int txtAdvancePaid = 0;
		String txtAdvancePaidDate = null;
		String txtRentValueasonDate = null;
		String txtRentValue1 = null;
		int txtRentValue = 0;
		String cboRentalPaymentOption = null;
		String txtTDSDeductionifany1 = null;
		int txtTDSDeductionifany = 0;
		String mtxtRemarks = null;

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

		System.out.println("chk 2");

		String strCommand = "";
		String xml = "";

		HttpSession session = request.getSession(false);

		try {

			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		String userid = (String) session.getAttribute("UserId");
		//added on 01Mar2012 Sathya
		//to load the details of all units if fas_SU
		//to check whether the user logged in Super user
		String FAS_SU = "";
        if (session.getAttribute("FAS_SU") != null &&
            ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
            FAS_SU = "YES";
        else
            FAS_SU = "NO";

        String FAS_CAO = "";
        if (session.getAttribute("FAS_CAO") != null &&
            ((String)session.getAttribute("FAS_CAO")).equalsIgnoreCase("YES"))
            FAS_CAO = "YES";
        else
            FAS_CAO = "NO";

        String FAS_SU_ALL = "";
        if (session.getAttribute("FAS_SU_ALL") != null &&
            ((String)session.getAttribute("FAS_SU_ALL")).equalsIgnoreCase("YES"))
            FAS_SU_ALL = "YES";
        else
            FAS_SU_ALL = "NO";

        String FAS_SU_REGION = "";
        if (session.getAttribute("FAS_SU_REGION") != null &&
            ((String)session.getAttribute("FAS_SU_REGION")).equalsIgnoreCase("YES"))
            FAS_SU_REGION = "YES";
        else
            FAS_SU_REGION = "NO";

        String FAS_SU_CIRCLE = "";
        if (session.getAttribute("FAS_SU_CIRCLE") != null &&
            ((String)session.getAttribute("FAS_SU_CIRCLE")).equalsIgnoreCase("YES"))
            FAS_SU_CIRCLE = "YES";
        else
            FAS_SU_CIRCLE = "NO";
		
		
		

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
			System.out.println(strCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}
		


		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);

		Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
				.getAttribute("UserProfile");

		int empid = empProfile.getEmployeeId();
		if (strCommand.equalsIgnoreCase("Add")) {

			xml = "<response><command>add</command>";
			cboAccountingUnit1 = request.getParameter("cmbAcc_UnitCode");
			cmbAcc_UnitCode = Integer.parseInt(cboAccountingUnit1);
			cboAccountingForOffice1 = request.getParameter("cmbOffice_code");
			cmbOffice_code = Integer.parseInt(cboAccountingForOffice1);
			String txtName = request.getParameter("txtName");
			String mtxtAddress = request.getParameter("mtxtAddress");
			long txtPhone = Long.parseLong(request.getParameter("txtPhone"));

			int i = 1, i1 = 0;
			try {
				ps1 = connection
						.prepareStatement("Select max(TYPE_ID) from FAS_SL_TYPES_USER_DEFINED");
				results2 = ps1.executeQuery();

				if (results2.next()) {
					i1 = results2.getInt(1);

					i = i + i1;

				} else {
					i = i;
				}

			} catch (Exception e) {
				e.printStackTrace();

			}

			try {
				ps = connection
						.prepareStatement("insert into FAS_SL_TYPES_USER_DEFINED(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,TYPE_ID,TYPE_NAME,TYPE_ADDRESS,TYPE_PHONE,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?,?)");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setInt(3, i);
				ps.setString(4, txtName);
				ps.setString(5, mtxtAddress);
				ps.setLong(6, txtPhone);
				ps.setString(7, userid);
				ps.setTimestamp(8, ts);

				ps.executeUpdate();

				xml = xml + "<flag>success</flag>";
				xml = xml + "<AccountingUnit>" + cmbAcc_UnitCode
						+ "</AccountingUnit>";
				xml = xml + "<AccountingForOffice>" + cmbOffice_code
						+ "</AccountingForOffice>";
				xml = xml + "<typeID>" + i + "</typeID>";
				xml = xml + "<Name>" + txtName + "</Name>";
				xml = xml + "<Address>" + mtxtAddress + "</Address>";
				xml = xml + "<phone>" + txtPhone + "</phone>";

			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("gett")) {
			int accunitid=0;
			try
			{
				accunitid=Integer.parseInt(request.getParameter("accunitid"));
				System.out.println("accunitid sssssss"+accunitid);
			}
			catch(Exception e)
			{
				System.out.println("Exception arised ****"+e);
			}

			xml = xml + "<response><command>gett</command>";

			try {
				ps = connection
						.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," +
								" TYPE_ID,TYPE_NAME,TYPE_ADDRESS,TYPE_PHONE from FAS_SL_TYPES_USER_DEFINED " +
								" where ACCOUNTING_UNIT_ID=? order by TYPE_ID");
				ps.setInt(1,accunitid);
				results = ps.executeQuery();
				xml = xml + "<flag>success</flag>";
				while (results.next()) {

					xml = xml + "<AccountingUnit>"
							+ results.getInt("ACCOUNTING_UNIT_ID")
							+ "</AccountingUnit>";
					xml = xml + "<AccountingForOffice>"
							+ results.getInt("ACCOUNTING_FOR_OFFICE_ID")
							+ "</AccountingForOffice>";

					xml = xml + "<typeID>" + results.getInt("TYPE_ID")
							+ "</typeID>";
					xml = xml + "<Name>" + results.getString("TYPE_NAME")
							+ "</Name>";
					xml = xml + "<Address>" + results.getString("TYPE_ADDRESS")
							+ "</Address>";
					xml = xml + "<phone>" + results.getLong("TYPE_PHONE")
							+ "</phone>";
				}

			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		} 
		//added as aon 01Mar2012 ********
		
		else if( (strCommand.equalsIgnoreCase("gettall")) && ((FAS_SU.equalsIgnoreCase("YES")) || (FAS_CAO.equalsIgnoreCase("YES")) || (FAS_SU_ALL.equalsIgnoreCase("YES")) || (FAS_SU_REGION.equalsIgnoreCase("YES")) || (FAS_SU_CIRCLE.equalsIgnoreCase("YES"))) )
		{

			xml = xml + "<response><command>gettall</command>";
System.out.println("inside load allllllllllllllllllll");
			try {
				ps = connection
						.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," +
								" TYPE_ID,TYPE_NAME,TYPE_ADDRESS,TYPE_PHONE from FAS_SL_TYPES_USER_DEFINED " +
								" order by TYPE_ID");
				results = ps.executeQuery();
				System.out.println("testttttttttttttttt"+results);
				xml = xml + "<flag>success</flag>";
				while (results.next()) {

					xml = xml + "<AccountingUnit>"
							+ results.getInt("ACCOUNTING_UNIT_ID")
							+ "</AccountingUnit>";
					xml = xml + "<AccountingForOffice>"
							+ results.getInt("ACCOUNTING_FOR_OFFICE_ID")
							+ "</AccountingForOffice>";

					xml = xml + "<typeID>" + results.getInt("TYPE_ID")
							+ "</typeID>";
					xml = xml + "<Name>" + results.getString("TYPE_NAME")
							+ "</Name>";
					xml = xml + "<Address>" + results.getString("TYPE_ADDRESS")
							+ "</Address>";
					xml = xml + "<phone>" + results.getLong("TYPE_PHONE")
							+ "</phone>";
				}

			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		} 
		
		
		
		
		else if (strCommand.equalsIgnoreCase("deleted")) {

			xml = "<response><command>deleted</command>";
			int typeid = Integer.parseInt(request.getParameter("typeid"));
			try {
				ps = connection
						.prepareStatement("delete from FAS_SL_TYPES_USER_DEFINED where TYPE_ID=?");
				ps.setInt(1, typeid);
				ps.executeUpdate();
				xml = xml + "<flag>success</flag>";
				xml = xml + "<id>" + typeid + "</id>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
			}
		} else if (strCommand.equalsIgnoreCase("loadValuesFromTable")) {

			xml = "<response><command>loadValuesFromTable</command>";
			int officeid = Integer.parseInt(request.getParameter("officeid"));
			String idd = request.getParameter("idd");	
			xml = xml + "<idd>" + idd + "</idd>";
			try {
				ps = connection
						.prepareStatement("select OFFICE_ID,OFFICE_NAME from COM_MST_OFFICES where office_id=?");
				ps.setInt(1, officeid);
				rs=ps.executeQuery();
				while(rs.next())
				{
					xml = xml + "<officeid>" + rs.getInt("OFFICE_ID") + "</officeid>";
					xml = xml + "<officename>" + rs.getString("OFFICE_NAME") + "</officename>";
				}
				xml = xml + "<flag>success</flag>";				
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		} else if (strCommand.equalsIgnoreCase("update")) {

			xml = "<response><command>update</command>";
			cboAccountingUnit1 = request.getParameter("cmbAcc_UnitCode");
			cmbAcc_UnitCode = Integer.parseInt(cboAccountingUnit1);
			cboAccountingForOffice1 = request.getParameter("cmbOffice_code");
			cmbOffice_code = Integer.parseInt(cboAccountingForOffice1);
			String txtName = request.getParameter("txtName");
			String mtxtAddress = request.getParameter("mtxtAddress");
			long txtPhone = Long.parseLong(request.getParameter("txtPhone"));
			int typeid = Integer.parseInt(request.getParameter("typeid"));

			try {
				ps = connection
						.prepareStatement("update FAS_SL_TYPES_USER_DEFINED set ACCOUNTING_UNIT_ID=?,ACCOUNTING_FOR_OFFICE_ID=?,TYPE_NAME=?,TYPE_ADDRESS=?,TYPE_PHONE=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where TYPE_ID=?");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setString(3, txtName);
				ps.setString(4, mtxtAddress);
				ps.setLong(5, txtPhone);
				ps.setString(6, userid);
				ps.setTimestamp(7, ts);
				ps.setInt(8, typeid);
				ps.executeUpdate();

				xml = xml + "<flag>success</flag>";
				xml = xml + "<AccountingUnit>" + cmbAcc_UnitCode
						+ "</AccountingUnit>";
				xml = xml + "<AccountingForOffice>" + cmbOffice_code
						+ "</AccountingForOffice>";
				xml = xml + "<typeID>" + typeid + "</typeID>";
				xml = xml + "<Name>" + txtName + "</Name>";
				xml = xml + "<Address>" + mtxtAddress + "</Address>";
				xml = xml + "<phone>" + txtPhone + "</phone>";

			} catch (Exception e) {
				System.out.println("exception in update is" + e);

			}
		}
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);

	}

}
