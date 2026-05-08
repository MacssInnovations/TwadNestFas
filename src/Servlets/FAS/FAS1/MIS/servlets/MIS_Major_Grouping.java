package Servlets.FAS.FAS1.MIS.servlets;

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
 * Servlet implementation class MIS_Major_Grouping
 */
public class MIS_Major_Grouping extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MIS_Major_Grouping() {
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

	    PrintWriter out = response.getWriter();
	    response.setContentType(CONTENT_TYPE);

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

			String cmbBudgetGroupMajor = request
					.getParameter("cmbBudgetGroupMajor");
			int txtGroup_Head_Code = Integer.parseInt(request
					.getParameter("txtGroup_Head_Code"));
			String txtGroup_Head_Desc = request
					.getParameter("txtGroup_Head_Desc");

			try {
				ps1 = connection
						.prepareStatement("select MAJOR_HEAD_CODE,GROUP_HEAD_CODE from FAS_MIS_GROUP_MASTER where GROUP_HEAD_CODE=?");
				ps1.setInt(1, txtGroup_Head_Code);
				rs = ps1.executeQuery();
				if (rs.next()) {
					xml = xml + "<flag>Exist</flag>";
				} else {
					ps = connection
							.prepareStatement("insert into FAS_MIS_GROUP_MASTER(MAJOR_HEAD_CODE,GROUP_HEAD_CODE,GROUP_HEAD_DESC,STATUS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?)");
					ps.setString(1, cmbBudgetGroupMajor);
					ps.setInt(2, txtGroup_Head_Code);
					ps.setString(3, txtGroup_Head_Desc);
					ps.setString(4, "L");
					ps.setString(5, userid);
					ps.setTimestamp(6, ts);

					ps.executeUpdate();

					xml = xml + "<cmbBudgetGroupMajor>" + cmbBudgetGroupMajor
							+ "</cmbBudgetGroupMajor>";
					xml = xml + "<txtGroup_Head_Code>" + txtGroup_Head_Code
							+ "</txtGroup_Head_Code>";
					xml = xml + "<txtGroup_Head_Desc>" + txtGroup_Head_Desc
							+ "</txtGroup_Head_Desc>";

					xml = xml + "<flag>success</flag>";
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			int i = 1, i1 = 0;

			try {
				ps1 = connection
						.prepareStatement("Select max(GROUP_HEAD_CODE) from FAS_MIS_GROUP_MASTER");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					i = i + i1;

				} else {
					i = i;
				}
				xml = xml + "<Group_Head_Code>" + i + "</Group_Head_Code>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();
			}
		} else if (strCommand.equalsIgnoreCase("getGrid")) {
			xml = "<response><command>getGrid</command>";

			int i = 1, i1 = 0;

			try {
				ps1 = connection
						.prepareStatement("Select max(GROUP_HEAD_CODE) from FAS_MIS_GROUP_MASTER");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					i = i + i1;

				} else {
					i = i;
				}
				xml = xml + "<Group_Head_Code>" + i + "</Group_Head_Code>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();
			}

			try {
				String sql="";
				if(request.getParameter("budgetGroup")==null){
					sql = "select MAJOR_HEAD_CODE,GROUP_HEAD_CODE,GROUP_HEAD_DESC from FAS_MIS_GROUP_MASTER order by GROUP_HEAD_CODE";
				}else{
					String budgetGroup = request.getParameter("budgetGroup");
					sql = "select MAJOR_HEAD_CODE,GROUP_HEAD_CODE,GROUP_HEAD_DESC from FAS_MIS_GROUP_MASTER where MAJOR_HEAD_CODE='"+budgetGroup+"' order by GROUP_HEAD_CODE";
				}
				ps1 = connection.prepareStatement(sql);
				rs = ps1.executeQuery();
				while (rs.next()) {
					xml = xml + "<cmbBudgetGroupMajor>"
							+ rs.getString("MAJOR_HEAD_CODE")
							+ "</cmbBudgetGroupMajor>";
					xml = xml + "<txtGroup_Head_Code>"
							+ rs.getInt("GROUP_HEAD_CODE")
							+ "</txtGroup_Head_Code>";
				    xml=xml+"<txtGroup_Head_Desc><![CDATA["+rs.getString("GROUP_HEAD_DESC")+"]]></txtGroup_Head_Desc>";
//					xml = xml + "<txtGroup_Head_Desc>"
//							+ rs.getString("GROUP_HEAD_DESC")
//							+ "</txtGroup_Head_Desc>";
				}
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

			try {
				ps = connection
						.prepareStatement("select MAJOR_HEAD_CODE,MAJOR_HEAD_DESC from COM_MST_MAJOR_HEADS order by MAJOR_HEAD_DESC");
				rs2 = ps.executeQuery();
				while (rs2.next()) {
					xml = xml + "<BudgetIdMain>"
							+ rs2.getString("MAJOR_HEAD_CODE")
							+ "</BudgetIdMain>";
					xml = xml + "<BudgetDescMain>"
							+ rs2.getString("MAJOR_HEAD_DESC")
							+ "</BudgetDescMain>";
				}
				xml = xml + "<flag2>success</flag2>";
			} catch (Exception e) {
				xml = xml + "<flag2>failure</flag2>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("update")) {

			xml = "<response><command>update</command>";

			String cmbBudgetGroupMajor = request
					.getParameter("cmbBudgetGroupMajor");
			int txtGroup_Head_Code = Integer.parseInt(request
					.getParameter("txtGroup_Head_Code"));
			String txtGroup_Head_Desc = request
					.getParameter("txtGroup_Head_Desc");

			int i = 1, i1 = 0;

			try {
				ps1 = connection
						.prepareStatement("Select max(GROUP_HEAD_CODE) from FAS_MIS_GROUP_MASTER");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					i = i + i1;

				} else {
					i = i;
				}
				xml = xml + "<Group_Head_Code>" + i + "</Group_Head_Code>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();
			}

			try {
				ps1 = connection
						.prepareStatement("select MAJOR_HEAD_CODE,GROUP_HEAD_CODE from FAS_MIS_GROUP_MASTER where GROUP_HEAD_CODE=?");
				ps1.setInt(1, txtGroup_Head_Code);
				rs = ps1.executeQuery();
				if (rs.next()) {
					ps = connection
							.prepareStatement("update FAS_MIS_GROUP_MASTER set MAJOR_HEAD_CODE=?,GROUP_HEAD_DESC=?,STATUS=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where GROUP_HEAD_CODE=?");
					ps.setString(1, cmbBudgetGroupMajor);
					ps.setString(2, txtGroup_Head_Desc);
					ps.setString(3, "L");
					ps.setString(4, userid);
					ps.setTimestamp(5, ts);
					ps.setInt(6, txtGroup_Head_Code);
					ps.executeUpdate();

					xml = xml + "<cmbBudgetGroupMajor>" + cmbBudgetGroupMajor
							+ "</cmbBudgetGroupMajor>";
					xml = xml + "<txtGroup_Head_Code>" + txtGroup_Head_Code
							+ "</txtGroup_Head_Code>";
					xml = xml + "<txtGroup_Head_Desc>" + txtGroup_Head_Desc
							+ "</txtGroup_Head_Desc>";

					xml = xml + "<flag>success</flag>";
				} else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();

			}
		} else if (strCommand.equalsIgnoreCase("deleted")) {
			int k = 0, k1 = 0;
			try {
				connection.setAutoCommit(false);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			xml = "<response><command>deleted</command>";
			int txtGroup_Head_Code = Integer.parseInt(request
					.getParameter("txtGroup_Head_Code"));
			try {
				ps1 = connection
						.prepareStatement("select MAJOR_HEAD_CODE,GROUP_HEAD_CODE from FAS_MIS_GROUP_MASTER where GROUP_HEAD_CODE=?");
				ps1.setInt(1, txtGroup_Head_Code);
				rs = ps1.executeQuery();
				if (rs.next()) {
					ps = connection
							.prepareStatement("delete from FAS_MIS_GROUP_MASTER where GROUP_HEAD_CODE=?");
					ps.setInt(1, txtGroup_Head_Code);
					k = ps.executeUpdate();

					ps1 = connection
							.prepareStatement("delete from FAS_MIS_GROUP_ACC_HD_MAPPING where GROUP_HEAD_CODE=?");
					ps1.setInt(1, txtGroup_Head_Code);
					k1 = ps1.executeUpdate();

					if(k>0 && k1>0)
					{
					xml = xml + "<flag>success</flag>";
					connection.commit();
					}else{
						connection.rollback();
						connection.commit();
						xml = xml + "<flag>failure</flag>";
					}
					xml = xml + "<id>" + txtGroup_Head_Code + "</id>";
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
						.prepareStatement("Select max(GROUP_HEAD_CODE) from FAS_MIS_GROUP_MASTER");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					i = i + i1;

				} else {
					i = i;
				}
				xml = xml + "<Group_Head_Code>" + i + "</Group_Head_Code>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();
			}
		} else if (strCommand.equalsIgnoreCase("clear")) {
			xml = "<response><command>clear</command>";
			int i = 1, i1 = 0;

			try {
				ps1 = connection
						.prepareStatement("Select max(GROUP_HEAD_CODE) from FAS_MIS_GROUP_MASTER");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					i = i + i1;

				} else {
					i = i;
				}
				xml = xml + "<MIS_Major_Group_Id>" + i
						+ "</MIS_Major_Group_Id>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();
			}
		}
		xml = xml + "</response>";
		out.write(xml);
		System.out.println("xml::::"+xml);
	}

}
