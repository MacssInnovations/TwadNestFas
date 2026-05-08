package Servlets.FAS.FAS1.Masters.Reports;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ResourceBundle;
import java.util.Vector;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class A52_Register_Unit_Status_Freeze
 */
public class A52_Register_Unit_Status_Freeze extends HttpServlet {
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public A52_Register_Unit_Status_Freeze() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {

		Connection connection = null;
		PrintWriter out = response.getWriter();
		response.setHeader("cache-control", "no-cache");
		String CONTENT_TYPE = "text/xml; charset=windows-1252";
		response.setContentType(CONTENT_TYPE);

		PreparedStatement preparedStatement1 = null;
		PreparedStatement ps2 = null, ps22 = null;
		ResultSet rss = null;
		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}

		response.setContentType(CONTENT_TYPE);
		try {

			ResourceBundle rs = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rs.getString("Config.DSN");
			String strhostname = rs.getString("Config.HOST_NAME");
			String strportno = rs.getString("Config.PORT_NUMBER");
			String strsid = rs.getString("Config.SID");
			String strdbusername = rs.getString("Config.USER_NAME");
			String strdbpassword = rs.getString("Config.PASSWORD");

		    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


			Class.forName(strDriver.trim());
			connection = DriverManager.getConnection(ConnectionString,
					strdbusername.trim(), strdbpassword.trim());
		} catch (Exception ex) {
			String connectMsg = "Could not create the connection"
					+ ex.getMessage() + " " + ex.getLocalizedMessage();
			System.out.println(connectMsg);
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

		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
		Vector<Integer> status = new Vector<Integer>();
		Vector<Integer> statusnot = new Vector<Integer>();
		if (strCommand.equalsIgnoreCase("LoadUnitWise_OfficeCode")) {
			xml = "<response><command>" + strCommand + "</command>";
			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));

			try {
				// unit_id
				int offid1 = 0;
				int cnt = 0;

				String ss = "select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID="
						+ cmbAcc_UnitCode;

				PreparedStatement ps21 = connection.prepareStatement(ss);
				ResultSet rs1 = ps21.executeQuery();
				if (rs1.next()) {
					offid1 = rs1.getInt("ACCOUNTING_UNIT_OFFICE_ID");
				}

				// find reployement or not....
				String ssq = "select OFFICE_LEVEL_ID from COM_MST_OFFICES where OFFICE_LEVEL_ID='CL' and OFFICE_ID="
						+ offid1;
				// System.out.println(ssq);
				PreparedStatement ps20 = connection.prepareStatement(ssq);
				ResultSet rs11 = ps20.executeQuery();
				if (rs11.next()) {

					String ssq2 = "select a.RENDERING_UNIT_OFFICE_ID,a.ACCT_UNIT_ID_RENDERED_FOR,(select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=a.rendering_unit_office_id)as officeName from fas_asset_val_ac_render_units a where a.acct_rendering_unit_id="
							+ cmbAcc_UnitCode
							+ " order by a.RENDERING_UNIT_OFFICE_ID";

					// System.out.println(ssq2);
					PreparedStatement ps211 = connection.prepareStatement(ssq2);
					ResultSet rs112 = ps211.executeQuery();
					while (rs112.next()) {

						xml = xml + "<offid>"
								+ rs112.getInt("RENDERING_UNIT_OFFICE_ID")
								+ "</offid>";

						xml = xml + "<uuid>"
								+ rs112.getInt("ACCT_UNIT_ID_RENDERED_FOR")
								+ "</uuid>";
						xml = xml + "<offname>"
								+ rs112.getString("officeName").trim()
								+ "</offname>";
						cnt++;

					}

				} else {

					String ss1 = "select ACCOUNTING_FOR_OFFICE_ID,ACCOUNTING_UNIT_ID,b.OFFICE_NAME from FAS_MST_ACCT_UNIT_OFFICES a,COM_MST_OFFICES b "
							+ "where a.ACCOUNTING_FOR_OFFICE_ID=b.OFFICE_ID  and a.ACCOUNTING_UNIT_ID= "
							+ cmbAcc_UnitCode;
					// System.out.println(ss1);
					PreparedStatement ps = connection.prepareStatement(ss1);
					ResultSet rs10 = ps.executeQuery();

					while (rs10.next()) {
						xml = xml + "<offid>"
								+ rs10.getInt("ACCOUNTING_FOR_OFFICE_ID")
								+ "</offid>";
						xml = xml + "<uuid>"
								+ rs10.getInt("ACCOUNTING_UNIT_ID") + "</uuid>";
						xml = xml + "<offname>"
								+ rs10.getString("OFFICE_NAME").trim()
								+ "</offname>";
						cnt++;
					}

				}

				if (cnt != 0)
					xml = xml + "<flag>success</flag>";
				else
					xml = xml + "<flag>failure</flag>";
			} catch (Exception e) {
				System.out.println("catch..HERE.in load head code." + e);
				xml = xml + "<flag>failure</flag>";
			}

		} else if (strCommand.equalsIgnoreCase("FreezeA52Unit")) {

			xml = "<response><command>FreezeA52Unit</command>";
			int count = 0;

			// try{
			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			String cmbFinancialYear = request.getParameter("cmbFinancialYear");
			String verifya52aa52 = request.getParameter("verifya52").trim();
			String verifyvalueA52 = "";
			// String A52Date1="",AA52Date1="";
			try {

				int uuunitid = 0;

				String ss1 = "select a.RENDERING_UNIT_OFFICE_ID,a.ACCT_UNIT_ID_RENDERED_FOR,(select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=a.rendering_unit_office_id)as officeName from fas_asset_val_ac_render_units a where a.acct_rendering_unit_id="
						+ cmbAcc_UnitCode
						+ " and a.RENDERING_UNIT_OFFICE_ID="
						+ cmbOffice_code
						+ "  order by a.RENDERING_UNIT_OFFICE_ID";
				// System.out.println(ss1);
				PreparedStatement ps = connection.prepareStatement(ss1);
				ResultSet rs10 = ps.executeQuery();
				if (rs10.next()) {
					uuunitid = rs10.getInt("ACCT_UNIT_ID_RENDERED_FOR");
				} else {
					uuunitid = cmbAcc_UnitCode;
				}

				System.out.println("uuunitid--->  " + uuunitid);

				System.out.println("verifya52aa52  " + verifya52aa52);

				ResultSet rs0 = null;
				// PreparedStatement ps1=null,ps3=null;
				ps2 = connection
						.prepareStatement("select ACCOUNTING_FOR_OFFICE_ID from FAS_A52_REGISTER_STATUS where ACCOUNTING_UNIT_ID="
								+ uuunitid
								+ " AND ACCOUNTING_FOR_OFFICE_ID="
								+ cmbOffice_code
								+ " AND FINANCIAL_YEAR='"
								+ cmbFinancialYear
								+ "' AND A52_STATUS_UNIT='Y'");
				rs0 = ps2.executeQuery();
				if (rs0.next()) {
					xml = xml + "<flag>AlreadyExist</flag>";
				} else {
					if (verifya52aa52.equalsIgnoreCase("A52")) {
						System.out.println("inside a52  unit level  ");
						verifyvalueA52 = "Y";
						java.sql.Date A52Date_Unit = new java.sql.Date(
								ts.getTime());
						System.out.println("if a52 " + A52Date_Unit);
						preparedStatement1 = connection
								.prepareStatement("insert into FAS_A52_REGISTER_STATUS(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,A52_STATUS_UNIT,A52_STATUS_UNIT_DATE,UPDATED_BY_USERID,UPDATED_DATE)values(?,?,?,?,?,?,SYSTIMESTAMP)");
						preparedStatement1.setInt(1, uuunitid);
						preparedStatement1.setInt(2, cmbOffice_code);
						preparedStatement1.setString(3, cmbFinancialYear);
						preparedStatement1.setString(4, verifyvalueA52);
						preparedStatement1.setDate(5, A52Date_Unit);
						preparedStatement1.setString(6, userid);
						count = preparedStatement1.executeUpdate();
						System.out.println("count:" + count);
						// }
					}
					if (count > 0) {
						xml = xml + "<flag>success</flag>";
					}

					// }
				}
			} catch (Exception e) {
				System.out.println("exception in add is" + e);
				xml = xml + "<flag>failure</flag>";
			}

		} else if (strCommand.equalsIgnoreCase("FreezeA52_Circle")) {

			xml = "<response><command>FreezeA52_Circle</command>";
			int count = 0;

			// try{
			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			String cmbFinancialYear = request.getParameter("cmbFinancialYear");
			String verifya52aa52 = request.getParameter("verifya52").trim();
			// String verifyvalueA52="";
			String verifyvalueA52 = "";
			// String A52Date1="",AA52Date1="";
			try {
				ResultSet rs0 = null, rs1 = null, rs2 = null, rs3 = null;
				PreparedStatement ps1 = null, ps3 = null;
				ps2 = connection
						.prepareStatement("select ACCOUNTING_FOR_OFFICE_ID from FAS_A52_REGISTER_STATUS where ACCOUNTING_UNIT_ID="
								+ cmbAcc_UnitCode
								+ " AND ACCOUNTING_FOR_OFFICE_ID="
								+ cmbOffice_code
								+ " AND FINANCIAL_YEAR='"
								+ cmbFinancialYear
								+ "' AND A52_STATUS_UNIT='Y'  AND A52_STATUS_CIRCLE='Y' ");
				rs0 = ps2.executeQuery();
				if (rs0.next()) {
					// System.out.println("Already exists ");
					xml = xml + "<flag>AlreadyExist</flag>";
				} else {
					String notofficestatusupdate = "";
					String officestatusupdate = "";
					String queryFind = "select RENDERING_UNIT_OFFICE_ID from fas_asset_val_ac_render_units where acct_rendering_unit_id="
							+ cmbAcc_UnitCode;
					ps1 = connection.prepareStatement(queryFind);
					rs1 = ps1.executeQuery();
					if (rs1.next()) {
						ResultSet rs4 = ps1.executeQuery();
						while (rs4.next()) {

							int unitofficeid = rs4
									.getInt("RENDERING_UNIT_OFFICE_ID");
							// System.out.println("inside checking with this table..."+unitofficeid);
							String findingintab = "select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID from FAS_A52_REGISTER_STATUS where ACCOUNTING_FOR_OFFICE_ID="
									+ unitofficeid
									+ " AND FINANCIAL_YEAR='"
									+ cmbFinancialYear
									+ "' AND A52_STATUS_UNIT='Y'";
							ps3 = connection.prepareStatement(findingintab);
							rs2 = ps3.executeQuery();
							if (rs2.next()) {
								ResultSet rs5 = ps3.executeQuery();
								while (rs5.next()) {
									// System.out.println("inside inner while looppp ");
									int statusoffice = rs5
											.getInt("ACCOUNTING_FOR_OFFICE_ID");
									// System.out.println("statusoffice "+statusoffice);
									if (!status.contains(statusoffice)) {
										status.add(statusoffice);
									}
								}

							} else {
								// notofficestatusupdate+=unitofficeid;
								if (!statusnot.contains(unitofficeid)) {
									statusnot.add(unitofficeid);
								}
							}

							// System.out.println("  status  "+status);
							// System.out.println("  statusnot  "+statusnot);
						}
						int sizevect = statusnot.size();
						if (statusnot.isEmpty()) {
							// (statusnot.contains(cmbAcc_UnitCode))
							System.out.println("vector status vector empty");

							if (verifya52aa52.equalsIgnoreCase("A52")) {
								System.out.println("inside a52 ");
								verifyvalueA52 = "Y";

								java.sql.Date A52Date = new java.sql.Date(
										ts.getTime());
								// System.out.println("if aa52 "+AA52Date);
								preparedStatement1 = connection
										.prepareStatement("update FAS_A52_REGISTER_STATUS set A52_STATUS_CIRCLE=?,A52_STATUS_CIRCLE_DATE=? where ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND FINANCIAL_YEAR=? AND A52_STATUS_UNIT='Y'");
								preparedStatement1.setString(1, verifyvalueA52);
								preparedStatement1.setDate(2, A52Date);
								preparedStatement1.setInt(3, cmbAcc_UnitCode);
								preparedStatement1.setInt(4, cmbOffice_code);
								preparedStatement1.setString(5,
										cmbFinancialYear);

								count = preparedStatement1.executeUpdate();
								System.out.println("count:" + count);

							}
							if (count > 0) {
								xml = xml + "<flag>success</flag>";
							}

						} else {
							xml = xml + "<flag>someOfficeNot</flag>";
						}

					} else {
						System.out.println("Else part no rendering");
						xml = xml + "<flag>NotCircle</flag>";
						/*
						 * 
						 * if(verifya52aa52.equalsIgnoreCase("AA52")){ //
						 * System.out.println("inside a52 ");
						 * verifyvalueAA52="Y";
						 * 
						 * java.sql.Date AA52Date=new
						 * java.sql.Date(ts.getTime()); //
						 * System.out.println("if a52 "+A52Date);
						 * preparedStatement1 = connection.prepareStatement(
						 * "update FREEZE_AA52_STATUS set AA52_STATUS_CIRCLE=?,AA52_DATE_CIRCLE=? where ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND FINANCIAL_YEAR=?"
						 * ); preparedStatement1.setString(1, verifyvalueAA52);
						 * preparedStatement1.setDate(2, AA52Date);
						 * preparedStatement1.setInt(3, cmbAcc_UnitCode);
						 * preparedStatement1.setInt(4, cmbOffice_code);
						 * preparedStatement1.setString(5, cmbFinancialYear);
						 * 
						 * count = preparedStatement1.executeUpdate();
						 * System.out.println("count:" + count); //} }
						 * if(count>0) { xml=xml+"<flag>success</flag>"; }
						 */
						System.out.println("No  rendering ---");
					}

				}
			} catch (Exception e) {
				System.out.println("exception in add is" + e);
				xml = xml + "<flag>failure</flag>";
			}

		}

		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
