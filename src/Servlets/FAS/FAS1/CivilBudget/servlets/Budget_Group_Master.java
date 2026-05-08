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
 * Servlet implementation class Budget_Group_Master
 */
public class Budget_Group_Master extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Budget_Group_Master() {
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

			int txtBudgetGroupId = Integer.parseInt(request
					.getParameter("txtBudgetGroupId"));
			String txtBudgetGroupMajor = request
					.getParameter("txtBudgetGroupMajor");

			int i = 1, i1 = 0;

			try {
				ps1 = connection
						.prepareStatement("Select max(BUDGET_GROUP_ID) from FAS_BUDGET_GROUP_MASTER");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					i = i + i1;

				} else {
					i = i;
				}
				xml = xml + "<BudgetGroupId>" + i + "</BudgetGroupId>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();
			}

			try {
				ps1 = connection
						.prepareStatement("select BUDGET_GROUP_ID from FAS_BUDGET_GROUP_MASTER where BUDGET_GROUP_ID=?");
				ps1.setInt(1, txtBudgetGroupId);
				rs = ps1.executeQuery();
				if (rs.next()) {
					xml = xml + "<flag>Exist</flag>";
				} else {
					ps = connection
							.prepareStatement("insert into FAS_BUDGET_GROUP_MASTER(BUDGET_GROUP_ID,BUDGET_GROUP_MAJOR,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?)");
					ps.setInt(1, txtBudgetGroupId);
					ps.setString(2, txtBudgetGroupMajor);
					ps.setString(3, userid);
					ps.setTimestamp(4, ts);

					ps.executeUpdate();

					xml = xml + "<flag>success</flag>";
					xml = xml + "<txtBudgetGroupId>" + txtBudgetGroupId
							+ "</txtBudgetGroupId>";
					xml = xml + "<txtBudgetGroupMajor>" + txtBudgetGroupMajor
							+ "</txtBudgetGroupMajor>";
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("getGrid")) {
			xml = "<response><command>getGrid</command>";

			int i = 1, i1 = 0;

			try {
				ps1 = connection
						.prepareStatement("Select max(BUDGET_GROUP_ID) from FAS_BUDGET_GROUP_MASTER");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					i = i + i1;

				} else {
					i = i;
				}
				xml = xml + "<BudgetGroupId>" + i + "</BudgetGroupId>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();
			}

			try {
				ps1 = connection
						.prepareStatement("select BUDGET_GROUP_ID,BUDGET_GROUP_MAJOR from FAS_BUDGET_GROUP_MASTER order by BUDGET_GROUP_ID");
				rs = ps1.executeQuery();
				while (rs.next()) {
					xml = xml + "<txtBudgetGroupId>"
							+ rs.getInt("BUDGET_GROUP_ID")
							+ "</txtBudgetGroupId>";
					xml = xml + "<txtBudgetGroupMajor>"
							+ rs.getString("BUDGET_GROUP_MAJOR")
							+ "</txtBudgetGroupMajor>";
				}
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("update")) {

			xml = "<response><command>update</command>";

			int txtBudgetGroupId = Integer.parseInt(request
					.getParameter("txtBudgetGroupId"));
			String txtBudgetGroupMajor = request
					.getParameter("txtBudgetGroupMajor");

			int i = 1, i1 = 0;

			try {
				ps1 = connection
						.prepareStatement("Select max(BUDGET_GROUP_ID) from FAS_BUDGET_GROUP_MASTER");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					i = i + i1;

				} else {
					i = i;
				}
				xml = xml + "<BudgetGroupId>" + i + "</BudgetGroupId>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();
			}

			try {
				ps1 = connection
						.prepareStatement("select BUDGET_GROUP_ID from FAS_BUDGET_GROUP_MASTER where BUDGET_GROUP_ID=?");
				ps1.setInt(1, txtBudgetGroupId);
				rs = ps1.executeQuery();
				if (rs.next()) {
					ps = connection
							.prepareStatement("update FAS_BUDGET_GROUP_MASTER set BUDGET_GROUP_MAJOR=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where BUDGET_GROUP_ID=?");
					ps.setString(1, txtBudgetGroupMajor);
					ps.setString(2, userid);
					ps.setTimestamp(3, ts);
					ps.setInt(4, txtBudgetGroupId);
					ps.executeUpdate();

					xml = xml + "<flag>success</flag>";
					xml = xml + "<txtBudgetGroupId>" + txtBudgetGroupId
							+ "</txtBudgetGroupId>";
					xml = xml + "<txtBudgetGroupMajor>" + txtBudgetGroupMajor
							+ "</txtBudgetGroupMajor>";
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
			int txtBudgetGroupId = Integer.parseInt(request
					.getParameter("txtBudgetGroupId"));
			try {
				ps1 = connection
						.prepareStatement("select BUDGET_GROUP_ID from FAS_BUDGET_GROUP_MASTER where BUDGET_GROUP_ID=?");
				ps1.setInt(1, txtBudgetGroupId);
				rs = ps1.executeQuery();
				if (rs.next()) {
					ps = connection
							.prepareStatement("delete from FAS_BUDGET_GROUP_MASTER where BUDGET_GROUP_ID=?");
					ps.setInt(1, txtBudgetGroupId);
					ps.executeUpdate();

					ps1 = connection
							.prepareStatement("delete from FAS_BUDGET_HEADS_MASTER where BUDGET_GROUP_MAJOR=?");
					ps1.setInt(1, txtBudgetGroupId);
					ps1.executeUpdate();

					ps2 = connection
							.prepareStatement("delete from FAS_BUDGET_HDS_AC_HDS_MAP_MST where BUDGET_GROUP_MAJOR=?");
					ps2.setInt(1, txtBudgetGroupId);
					ps2.executeUpdate();

					xml = xml + "<flag>success</flag>";
					connection.commit();
					xml = xml + "<id>" + txtBudgetGroupId + "</id>";
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
			int i = 1, i1 = 0;

			try {
				ps1 = connection
						.prepareStatement("Select max(BUDGET_GROUP_ID) from FAS_BUDGET_GROUP_MASTER");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					i = i + i1;

				} else {
					i = i;
				}
				xml = xml + "<BudgetGroupId>" + i + "</BudgetGroupId>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();
			}
		} else if (strCommand.equalsIgnoreCase("clear")) {
			xml = "<response><command>clear</command>";
			int i = 1, i1 = 0;

			try {
				ps1 = connection
						.prepareStatement("Select max(BUDGET_GROUP_ID) from FAS_BUDGET_GROUP_MASTER");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					i = i + i1;

				} else {
					i = i;
				}
				xml = xml + "<BudgetGroupId>" + i + "</BudgetGroupId>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();
			}
		}
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}

}
