package Servlets.FAS.FAS1.CivilBudget.servlets;

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
 * Servlet implementation class Format_Master
 */
public class Format_Master extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Format_Master() {
		super();
		
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		
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
		if (strCommand.equalsIgnoreCase("add")) {
			xml = "<response><command>add</command>";

			int txtFormatNo = Integer.parseInt(request.getParameter("txtFormatNo"));
			String txtFormatDescMain = request
					.getParameter("txtFormatDescMain");
			String txtFormatDescSub = request.getParameter("txtFormatDescSub");
			if ((txtFormatDescSub == "") || (txtFormatDescSub == null)) {
				txtFormatDescSub = null;
			}
			String txtFormatType = request.getParameter("txtFormatType");

			try {
				ps1 = connection
						.prepareStatement("select FORMAT_NO from FAS_FORMAT_MASTER where FORMAT_NO=?");
				ps1.setInt(1, txtFormatNo);
				rs = ps1.executeQuery();
				if (rs.next()) {
					xml = xml + "<flag>Exist</flag>";
				} else {
					ps = connection
							.prepareStatement("insert into FAS_FORMAT_MASTER(FORMAT_NO,FORMAT_DESC_MAIN,FORMAT_DESC_SUB,FORMAT_TYPE,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?)");
					ps.setInt(1, txtFormatNo);
					ps.setString(2, txtFormatDescMain);
					ps.setString(3, txtFormatDescSub);
					ps.setString(4, txtFormatType);
					ps.setString(5, userid);
					ps.setTimestamp(6, ts);

					ps.executeUpdate();

					xml = xml + "<flag>success</flag>";
					xml = xml + "<txtFormatNo>" + txtFormatNo
							+ "</txtFormatNo>";
					xml = xml + "<txtFormatDescMain>" + txtFormatDescMain
							+ "</txtFormatDescMain>";
					xml = xml + "<txtFormatDescSub>" + txtFormatDescSub
							+ "</txtFormatDescSub>";
					xml = xml + "<txtFormatType>" + txtFormatType
							+ "</txtFormatType>";
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("getGrid")) {
			xml = "<response><command>getGrid</command>";

			try {
				ps1 = connection
						.prepareStatement("select FORMAT_NO,FORMAT_DESC_MAIN,FORMAT_DESC_SUB,FORMAT_TYPE from FAS_FORMAT_MASTER order by FORMAT_NO");
				rs = ps1.executeQuery();
				while (rs.next()) {
					xml = xml + "<txtFormatNo>" + rs.getString("FORMAT_NO")
							+ "</txtFormatNo>";
					xml = xml + "<txtFormatDescMain>"
							+ rs.getString("FORMAT_DESC_MAIN")
							+ "</txtFormatDescMain>";
					xml = xml + "<txtFormatDescSub>"
							+ rs.getString("FORMAT_DESC_SUB")
							+ "</txtFormatDescSub>";
					xml = xml + "<txtFormatType>" + rs.getString("FORMAT_TYPE")
							+ "</txtFormatType>";
				}
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("update")) {

			xml = "<response><command>update</command>";

			int txtFormatNo = Integer.parseInt(request.getParameter("txtFormatNo"));
			String txtFormatDescMain = request
					.getParameter("txtFormatDescMain");
			String txtFormatDescSub = request.getParameter("txtFormatDescSub");
			if ((txtFormatDescSub == "") || (txtFormatDescSub == null)) {
				txtFormatDescSub = null;
			}
			String txtFormatType = request.getParameter("txtFormatType");

			try {
				ps1 = connection
						.prepareStatement("select FORMAT_NO from FAS_FORMAT_MASTER where FORMAT_NO=?");
				ps1.setInt(1, txtFormatNo);
				rs = ps1.executeQuery();
				if (rs.next()) {
					ps = connection
							.prepareStatement("update FAS_FORMAT_MASTER set FORMAT_DESC_MAIN=?,FORMAT_DESC_SUB=?,FORMAT_TYPE=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where FORMAT_NO=?");
					ps.setString(1, txtFormatDescMain);
					ps.setString(2, txtFormatDescSub);
					ps.setString(3, txtFormatType);
					ps.setString(4, userid);
					ps.setTimestamp(5, ts);
					ps.setInt(6, txtFormatNo);
					ps.executeUpdate();

					xml = xml + "<flag>success</flag>";
					xml = xml + "<txtFormatNo>" + txtFormatNo
							+ "</txtFormatNo>";
					xml = xml + "<txtFormatDescMain>" + txtFormatDescMain
							+ "</txtFormatDescMain>";
					xml = xml + "<txtFormatDescSub>" + txtFormatDescSub
							+ "</txtFormatDescSub>";
					xml = xml + "<txtFormatType>" + txtFormatType
							+ "</txtFormatType>";

					ps.close();
				} else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();

			}
		} else if (strCommand.equalsIgnoreCase("deleted")) {
			try {
				connection.setAutoCommit(false);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			xml = "<response><command>deleted</command>";
			int txtFormatNo = Integer.parseInt(request.getParameter("txtFormatNo"));
			try {
				ps1 = connection
						.prepareStatement("select FORMAT_NO from FAS_FORMAT_MASTER where FORMAT_NO=?");
				ps1.setInt(1, txtFormatNo);
				rs = ps1.executeQuery();
				if (rs.next()) {
					ps = connection
							.prepareStatement("delete from FAS_FORMAT_MASTER where FORMAT_NO=?");
					ps.setInt(1, txtFormatNo);
					ps.executeUpdate();

					ps1 = connection
							.prepareStatement("delete from FAS_BUDGET_HEADS_MASTER where FORMAT_NO=?");
					ps1.setInt(1, txtFormatNo);
					ps1.executeUpdate();

					ps2 = connection
							.prepareStatement("delete from FAS_BUDGET_HDS_AC_HDS_MAP_MST where FORMAT_NO=?");
					ps2.setInt(1, txtFormatNo);
					
					ps2.executeUpdate();
					xml = xml + "<flag>success</flag>";
					connection.commit();
					xml = xml + "<id>" + txtFormatNo + "</id>";
				} else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				try {
					connection.rollback();
					connection.commit();
				} catch (SQLException e1) {					
					e1.printStackTrace();
				}
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		}
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}

}
