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
 * Servlet implementation class Statement_Master
 */
public class Statement_Master extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Statement_Master() {
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
		if (strCommand.equalsIgnoreCase("add")) {
			xml = "<response><command>add</command>";

			String txtStatementNo = request.getParameter("txtStatementNo");
			String txtStatementDesc = request.getParameter("txtStatementDesc");
			try {
				ps1 = connection
						.prepareStatement("select STATEMENT_NO from FAS_STATEMENT_MASTER where STATEMENT_NO=?");
				ps1.setString(1, txtStatementNo);
				rs = ps1.executeQuery();
				if (rs.next()) {
					xml = xml + "<flag>Exist</flag>";
				} else {
					ps = connection
							.prepareStatement("insert into FAS_STATEMENT_MASTER(STATEMENT_NO,STATEMENT_DESC,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?)");
					ps.setString(1, txtStatementNo);
					ps.setString(2, txtStatementDesc);
					ps.setString(3, userid);
					ps.setTimestamp(4, ts);

					ps.executeUpdate();

					xml = xml + "<flag>success</flag>";
					xml = xml + "<txtStatementNo>" + txtStatementNo
							+ "</txtStatementNo>";
					xml = xml + "<txtStatementDesc>" + txtStatementDesc
							+ "</txtStatementDesc>";
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("getGrid")) {
			xml = "<response><command>getGrid</command>";

			try {
				ps1 = connection
						.prepareStatement("select STATEMENT_NO,STATEMENT_DESC from FAS_STATEMENT_MASTER order by STATEMENT_NO");
				rs = ps1.executeQuery();
				while (rs.next()) {
					xml = xml + "<txtStatementNo>"
							+ rs.getString("STATEMENT_NO")
							+ "</txtStatementNo>";
					xml = xml + "<txtStatementDesc>"
							+ rs.getString("STATEMENT_DESC")
							+ "</txtStatementDesc>";
				}
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("update")) {

			xml = "<response><command>update</command>";

			String txtStatementNo = request.getParameter("txtStatementNo");
			String txtStatementDesc = request.getParameter("txtStatementDesc");

			try {
				ps1 = connection
						.prepareStatement("select STATEMENT_NO from FAS_STATEMENT_MASTER where STATEMENT_NO=?");
				ps1.setString(1, txtStatementNo);
				rs = ps1.executeQuery();
				if (rs.next()) {
					ps = connection
							.prepareStatement("update FAS_STATEMENT_MASTER set STATEMENT_DESC=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where STATEMENT_NO=?");
					ps.setString(1, txtStatementDesc);
					ps.setString(2, userid);
					ps.setTimestamp(3, ts);
					ps.setString(4, txtStatementNo);
					ps.executeUpdate();

					xml = xml + "<flag>success</flag>";
					xml = xml + "<txtStatementNo>" + txtStatementNo
							+ "</txtStatementNo>";
					xml = xml + "<txtStatementDesc>" + txtStatementDesc
							+ "</txtStatementDesc>";

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
			String txtStatementNo = request.getParameter("txtStatementNo");
			System.out.println("txtStatementNo:::"+txtStatementNo);
			//int txtStatementNo1 = Integer.parseInt(request.getParameter("txtStatementNo"));
			try {
				ps1 = connection
						.prepareStatement("select STATEMENT_NO from FAS_STATEMENT_MASTER where STATEMENT_NO=?");
				ps1.setString(1, txtStatementNo);
				rs = ps1.executeQuery();
				if (rs.next()) {
					ps = connection.prepareStatement("delete from FAS_STATEMENT_MASTER where STATEMENT_NO='"+txtStatementNo+"'");
					  //ps.setString(1, txtStatementNo);
				      ps.executeUpdate();
				
						
						/*ps1 = connection.prepareStatement("delete from FAS_STATEMENT_GROUP_MASTER where STATEMENT_NAME=?");
				        ps1.setInt(1, txtStatementNo1);
				        ps1.executeUpdate();*/
					

					

					/*
					 * ps2 = connection.prepareStatement(
					 * "delete from FAS_BUDGET_HDS_AC_HDS_MAP_MST where STATEMENT_NO=?"
					 * ); ps2.setString(1, txtFormatNo);
					 * 
					 * ps2.executeUpdate();
					 */

					xml = xml + "<flag>success</flag>";
					connection.commit();
					xml = xml + "<id>" + txtStatementNo + "</id>";
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
