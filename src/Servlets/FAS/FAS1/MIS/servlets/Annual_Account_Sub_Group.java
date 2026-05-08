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
 * Servlet implementation class Annual_Account_Sub_Group
 */
public class Annual_Account_Sub_Group extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Annual_Account_Sub_Group() {
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
System.out.println("pooooooooooooooooooooooooooooooooooooooooooooooooo");
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
		// System.out.println("User Id is:" + userid);
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
		if (strCommand.equalsIgnoreCase("FirstLoad1")) {
			xml = "<response><command>FirstLoad1</command>";
System.out.println("FirstLoad1");
			try {
				ps = connection.prepareStatement("select MAJOR_HEAD_CODE,MAJOR_HEAD_DESC from COM_MST_MAJOR_HEADS order by MAJOR_HEAD_DESC");
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

			try {
				//ps1 = connection.prepareStatement("select MAJOR_HEAD_CODE,GROUP_HEAD_CODE,SUB_HEAD_CODE,SUB_HEAD_DESC from ANNUAL_ACCOUNT_SUB_GROUP order by GROUP_HEAD_CODE,SUB_HEAD_CODE");
				String sql="";
				if(request.getParameter("budgetGroup")==null){
					sql="SELECT a.MAJOR_HEAD_CODE AS MAJOR_HEAD_CODE, " +
					"  a.GROUP_HEAD_CODE      AS GROUP_HEAD_CODE, " +
					"  a.MINOR_HEAD_CODE      AS MINOR_HEAD_CODE, " +
					"  a.ORDER_NO             AS ORDER_NO, " +
					"  b.GROUP_HEAD_DESC      AS GROUP_HEAD_DESC " +
					" FROM " +
					"  (SELECT MAJOR_HEAD_CODE, " +
					"    GROUP_HEAD_CODE, " +
					"    MINOR_HEAD_CODE, " +
					"    ORDER_NO " +
					"  FROM ANNUAL_ACCOUNT_SUB_GROUP " +
					"  WHERE STATUS='L' " +
					"  )a " +
					"LEFT OUTER JOIN " +
					"  ( SELECT GROUP_HEAD_CODE, GROUP_HEAD_DESC FROM FAS_MIS_GROUP_MASTER " +
					"  )b " +
					"ON a.GROUP_HEAD_CODE=b.GROUP_HEAD_CODE " +
					"ORDER BY a.GROUP_HEAD_CODE, " +
					"  a.MINOR_HEAD_CODE";
				}else if((request.getParameter("budgetGroup")!=null)&&(request.getParameter("groupHead").equals(""))){
					String budgetGroup = request.getParameter("budgetGroup");
					sql="SELECT a.MAJOR_HEAD_CODE AS MAJOR_HEAD_CODE, " +
					"  a.GROUP_HEAD_CODE      AS GROUP_HEAD_CODE, " +
					"  a.MINOR_HEAD_CODE      AS MINOR_HEAD_CODE, " +
					"  a.ORDER_NO             AS ORDER_NO, " +
					"  b.GROUP_HEAD_DESC      AS GROUP_HEAD_DESC " +
					" FROM " +
					"  (SELECT MAJOR_HEAD_CODE, " +
					"    GROUP_HEAD_CODE, " +
					"    MINOR_HEAD_CODE, " +
					"    ORDER_NO " +
					"  FROM ANNUAL_ACCOUNT_SUB_GROUP " +
					"  WHERE STATUS='L' " +
					"  AND MAJOR_HEAD_CODE='"+budgetGroup+"' " +
					"  )a " +
					"LEFT OUTER JOIN " +
					"  ( SELECT GROUP_HEAD_CODE, GROUP_HEAD_DESC FROM FAS_MIS_GROUP_MASTER " +
					"  )b " +
					"ON a.GROUP_HEAD_CODE=b.GROUP_HEAD_CODE " +
					"ORDER BY a.GROUP_HEAD_CODE, " +
					"  a.MINOR_HEAD_CODE";
				}else{
					String budgetGroup = request.getParameter("budgetGroup");
					String groupHead = request.getParameter("groupHead");
					sql="SELECT a.MAJOR_HEAD_CODE AS MAJOR_HEAD_CODE, " +
					"  a.GROUP_HEAD_CODE      AS GROUP_HEAD_CODE, " +
					"  a.MINOR_HEAD_CODE      AS MINOR_HEAD_CODE, " +
					"  a.ORDER_NO             AS ORDER_NO, " +
					"  b.GROUP_HEAD_DESC      AS GROUP_HEAD_DESC " +
					" FROM " +
					"  (SELECT MAJOR_HEAD_CODE, " +
					"    GROUP_HEAD_CODE, " +
					"    MINOR_HEAD_CODE, " +
					"    ORDER_NO " +
					"  FROM ANNUAL_ACCOUNT_SUB_GROUP " +
					"  WHERE STATUS='L' " +
					"  AND MAJOR_HEAD_CODE='"+budgetGroup+"' " +
					"  AND GROUP_HEAD_CODE='"+groupHead+"' " +
					"  )a " +
					"LEFT OUTER JOIN " +
					"  ( SELECT GROUP_HEAD_CODE, GROUP_HEAD_DESC FROM FAS_MIS_GROUP_MASTER " +
					"  )b " +
					"ON a.GROUP_HEAD_CODE=b.GROUP_HEAD_CODE " +
					"ORDER BY a.GROUP_HEAD_CODE, " +
					"  a.MINOR_HEAD_CODE";
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
					xml = xml + "<txtSub_Head_Code>"
							+ rs.getInt("MINOR_HEAD_CODE")
							+ "</txtSub_Head_Code>";
					xml = xml + "<txtSub_Head_Desc>"
							+ rs.getString("ORDER_NO")
							+ "</txtSub_Head_Desc>";
					xml = xml + "<GROUP_HEAD_DESC>"
							+ rs.getString("GROUP_HEAD_DESC")
							+ "</GROUP_HEAD_DESC>";

				}
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

			int i = 1, i1 = 0;

			try {
				ps1 = connection
						.prepareStatement("Select max(MINOR_HEAD_CODE) from ANNUAL_ACCOUNT_SUB_GROUP");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					i = i + i1;

				} else {
					i = i;
				}
				xml = xml + "<sub_Head_Code>" + i + "</sub_Head_Code>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();
			}
		} else if (strCommand.equalsIgnoreCase("getMinorBudgetHeadDesc")) {
			xml = "<response><command>getMinorBudgetHeadDesc</command>";
			String cmbBudgetGroupMajor = request
					.getParameter("cmbBudgetGroupMajor");
			try {
//				ps1 = connection
//						.prepareStatement("select GROUP_HEAD_CODE,GROUP_HEAD_DESC from FAS_MIS_GROUP_MASTER where MAJOR_HEAD_CODE=? and TYPE='AA' order by MAJOR_HEAD_CODE");
				//code changed on 08-12-2017 for all group head code are loaded
				
				ps1 = connection
						.prepareStatement("select GROUP_HEAD_CODE,GROUP_HEAD_DESC from FAS_MIS_GROUP_MASTER where MAJOR_HEAD_CODE=?  order by MAJOR_HEAD_CODE");
				
				ps1.setString(1, cmbBudgetGroupMajor);
				rs = ps1.executeQuery();
				while (rs.next()) {
					xml = xml + "<cmbGroup_Head_Code>"
							+ rs.getInt("GROUP_HEAD_CODE")
							+ "</cmbGroup_Head_Code>";
					xml = xml + "<cmbGroup_Head_Desc>"
							+ rs.getString("GROUP_HEAD_DESC")
							+ "</cmbGroup_Head_Desc>";
				}
				xml = xml + "<flag4>success</flag4>";
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag4>failure</flag4>";
			}
		} 
		else if (strCommand.equalsIgnoreCase("getMinorHeadDesc")) {
            xml = "<response><command>getMinorHeadDesc</command>";
            String cmbBudgetGroupMajor = request
                            .getParameter("cmbBudgetGroupMajor");
            try {
                    ps1 = connection
                                    .prepareStatement("select MINOR_HEAD_CODE,MINOR_HEAD_DESC from COM_MST_MINOR_HEADS where MAJOR_HEAD_CODE=? order by MINOR_HEAD_CODE");
                    ps1.setString(1, cmbBudgetGroupMajor);
                    rs = ps1.executeQuery();
                    while (rs.next()) {
                            xml = xml + "<cmbGroup_Head_Code>"
                                            + rs.getInt("MINOR_HEAD_CODE")
                                            + "</cmbGroup_Head_Code>";
                            xml = xml + "<cmbGroup_Head_Desc><![CDATA["
                                            + rs.getString("MINOR_HEAD_DESC")
                                            + "]]></cmbGroup_Head_Desc>";
                    }
                    xml = xml + "<flag4>success</flag4>";
            } catch (Exception e) {
                    e.printStackTrace();
                    xml = xml + "<flag4>failure</flag4>";
            }
    }
		else if (strCommand.equalsIgnoreCase("getMinorBudgetHeadDesc1")) {
			xml = "<response><command>getMinorBudgetHeadDesc</command>";
			String cmbBudgetGroupMajor = request
					.getParameter("cmbBudgetGroupMajor");
			try {
				ps1 = connection
						.prepareStatement("select GROUP_HEAD_CODE,GROUP_HEAD_DESC from FAS_MIS_GROUP_MASTER where MAJOR_HEAD_CODE=? order by MAJOR_HEAD_CODE");
				ps1.setString(1, cmbBudgetGroupMajor);
				rs = ps1.executeQuery();
				while (rs.next()) {
					xml = xml + "<cmbGroup_Head_Code>"
							+ rs.getInt("GROUP_HEAD_CODE")
							+ "</cmbGroup_Head_Code>";
					xml = xml + "<cmbGroup_Head_Desc>"
							+ rs.getString("GROUP_HEAD_DESC")
							+ "</cmbGroup_Head_Desc>";
				}
				xml = xml + "<flag4>success</flag4>";
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag4>failure</flag4>";
			}
		} else if (strCommand.equalsIgnoreCase("add")) {
			xml = "<response><command>add</command>";

			String cmbBudgetGroupMajor = request
					.getParameter("cmbBudgetGroupMajor");
			int cmbGroup_Head_Code = Integer.parseInt(request
					.getParameter("cmbGroup_Head_Code"));
			int txtSubGroup_Head_Code = Integer.parseInt(request
					.getParameter("txtSubGroup_Head_Code"));
			String txtSubGroup_Head_Desc = request
					.getParameter("txtSubGroup_Head_Desc");

			try {
				ps1 = connection
						.prepareStatement("select MINOR_HEAD_CODE from ANNUAL_ACCOUNT_SUB_GROUP where MAJOR_HEAD_CODE=? and GROUP_HEAD_CODE=? and MINOR_HEAD_CODE=?");
				ps1.setString(1, cmbBudgetGroupMajor);
				ps1.setInt(2, cmbGroup_Head_Code);
				ps1.setInt(3, txtSubGroup_Head_Code);
				rs = ps1.executeQuery();
				if (rs.next()) {
					xml = xml + "<flag>Exist</flag>";
				} else {
					ps = connection
							.prepareStatement("insert into ANNUAL_ACCOUNT_SUB_GROUP(MAJOR_HEAD_CODE,GROUP_HEAD_CODE,MINOR_HEAD_CODE,ORDER_NO,STATUS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?)");
					ps.setString(1, cmbBudgetGroupMajor);
					ps.setInt(2, cmbGroup_Head_Code);
					ps.setInt(3, txtSubGroup_Head_Code);
					ps.setString(4, txtSubGroup_Head_Desc);
					ps.setString(5, "L");
					ps.setString(6, userid);
					ps.setTimestamp(7, ts);
					ps.executeUpdate();

					xml = xml + "<cmbBudgetGroupMajor>" + cmbBudgetGroupMajor
							+ "</cmbBudgetGroupMajor>";
					xml = xml + "<cmbGroup_Head_Code>" + cmbGroup_Head_Code
							+ "</cmbGroup_Head_Code>";
					xml = xml + "<txtSubGroup_Head_Code>"
							+ txtSubGroup_Head_Code
							+ "</txtSubGroup_Head_Code>";
					xml = xml + "<txtSubGroup_Head_Desc>"
							+ txtSubGroup_Head_Desc
							+ "</txtSubGroup_Head_Desc>";

					xml = xml + "<flag>success</flag>";
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

			int i = 1, i1 = 0;

			try {
				ps1 = connection
						.prepareStatement("Select max(MINOR_HEAD_CODE) from ANNUAL_ACCOUNT_SUB_GROUP");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					i = i + i1;

				} else {
					i = i;
				}
				xml = xml + "<sub_Head_Code>" + i + "</sub_Head_Code>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();
			}
		} else if (strCommand.equalsIgnoreCase("update")) {
			xml = "<response><command>update</command>";

			String cmbBudgetGroupMajor = request
					.getParameter("cmbBudgetGroupMajor");
			int cmbGroup_Head_Code = Integer.parseInt(request
					.getParameter("cmbGroup_Head_Code"));
			int txtSubGroup_Head_Code = Integer.parseInt(request
					.getParameter("txtSubGroup_Head_Code"));
			String txtSubGroup_Head_Desc = request
					.getParameter("txtSubGroup_Head_Desc");

			try {
				ps1 = connection
						.prepareStatement("select MINOR_HEAD_CODE from ANNUAL_ACCOUNT_SUB_GROUP where MAJOR_HEAD_CODE=? and GROUP_HEAD_CODE=? and MINOR_HEAD_CODE=?");
				ps1.setString(1, cmbBudgetGroupMajor);
				ps1.setInt(2, cmbGroup_Head_Code);
				ps1.setInt(3, txtSubGroup_Head_Code);
				rs = ps1.executeQuery();
				if (rs.next()) {

					ps = connection
							.prepareStatement("update ANNUAL_ACCOUNT_SUB_GROUP set ORDER_NO=?,STATUS=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where MAJOR_HEAD_CODE=? and GROUP_HEAD_CODE=? and MINOR_HEAD_CODE=? ");
					ps.setString(1, txtSubGroup_Head_Desc);
					ps.setString(2, "L");
					ps.setString(3, userid);
					ps.setTimestamp(4, ts);
					ps.setString(5, cmbBudgetGroupMajor);
					ps.setInt(6, cmbGroup_Head_Code);
					ps.setInt(7, txtSubGroup_Head_Code);
					ps.executeUpdate();

					xml = xml + "<cmbBudgetGroupMajor>" + cmbBudgetGroupMajor
							+ "</cmbBudgetGroupMajor>";
					xml = xml + "<cmbGroup_Head_Code>" + cmbGroup_Head_Code
							+ "</cmbGroup_Head_Code>";
					xml = xml + "<txtSubGroup_Head_Code>"
							+ txtSubGroup_Head_Code
							+ "</txtSubGroup_Head_Code>";
					xml = xml + "<txtSubGroup_Head_Desc>"
							+ txtSubGroup_Head_Desc
							+ "</txtSubGroup_Head_Desc>";

					xml = xml + "<flag>success</flag>";
				} else {
					xml = xml + "<flag>NoData</flag>";
				}

			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

			int i = 1, i1 = 0;

			try {
				ps1 = connection
						.prepareStatement("Select max(MINOR_HEAD_CODE) from ANNUAL_ACCOUNT_SUB_GROUP");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					i = i + i1;

				} else {
					i = i;
				}
				xml = xml + "<sub_Head_Code>" + i + "</sub_Head_Code>";

			} catch (Exception e) {
				xml = xml + "<flag1>failure1</flag1>";
				e.printStackTrace();
			}
		} else if (strCommand.equalsIgnoreCase("LoadData")) {
			xml = "<response><command>LoadData</command>";

			String cmbBudgetGroupMajor = request
					.getParameter("cmbBudgetGroupMajor");
			int cmbGroup_Head_Code = Integer.parseInt(request
					.getParameter("cmbGroup_Head_Code"));
			int txtSubGroup_Head_Code = Integer.parseInt(request
					.getParameter("txtSubGroup_Head_Code"));

			xml = xml + "<cmbBudgetGroupMajor>" + cmbBudgetGroupMajor
					+ "</cmbBudgetGroupMajor>";

			xml = xml + "<cmbGroup_Head_Code>" + cmbGroup_Head_Code
					+ "</cmbGroup_Head_Code>";

			xml = xml + "<txtSubGroup_Head_Code>" + txtSubGroup_Head_Code
					+ "</txtSubGroup_Head_Code>";

			try {
				ps1 = connection
						.prepareStatement("select GROUP_HEAD_CODE,GROUP_HEAD_DESC from FAS_MIS_GROUP_MASTER where MAJOR_HEAD_CODE=? and TYPE='AA' order by MAJOR_HEAD_CODE");
				ps1.setString(1, cmbBudgetGroupMajor);
				rs = ps1.executeQuery();
				while (rs.next()) {
					xml = xml + "<cmbGroup_Head_Code1>"
							+ rs.getInt("GROUP_HEAD_CODE")
							+ "</cmbGroup_Head_Code1>";
					xml = xml + "<cmbGroup_Head_Desc1>"
							+ rs.getString("GROUP_HEAD_DESC")
							+ "</cmbGroup_Head_Desc1>";
				}
				xml = xml + "<flag4>success</flag4>";
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag4>failure</flag4>";
			}

		} else if (strCommand.equalsIgnoreCase("deleted")) {

			xml = "<response><command>deleted</command>";
			String cmbBudgetGroupMajor = request
					.getParameter("cmbBudgetGroupMajor");
			int cmbGroup_Head_Code = Integer.parseInt(request
					.getParameter("cmbGroup_Head_Code"));
			int txtSubGroup_Head_Code = Integer.parseInt(request
					.getParameter("txtSubGroup_Head_Code"));
			try {
				ps1 = connection
						.prepareStatement("select MINOR_HEAD_CODE from ANNUAL_ACCOUNT_SUB_GROUP where MAJOR_HEAD_CODE=? and GROUP_HEAD_CODE=? and MINOR_HEAD_CODE=?");
				ps1.setString(1, cmbBudgetGroupMajor);
				ps1.setInt(2, cmbGroup_Head_Code);
				ps1.setInt(3, txtSubGroup_Head_Code);
				rs = ps1.executeQuery();
				if (rs.next()) {
					ps = connection
							.prepareStatement("delete from ANNUAL_ACCOUNT_SUB_GROUP where MAJOR_HEAD_CODE=? and GROUP_HEAD_CODE=? and MINOR_HEAD_CODE=?");
					ps.setString(1, cmbBudgetGroupMajor);
					ps.setInt(2, cmbGroup_Head_Code);
					ps.setInt(3, txtSubGroup_Head_Code);
					ps.executeUpdate();
					xml = xml + "<flag>success</flag>";
					xml = xml
							+ "<id>"
							+ (cmbBudgetGroupMajor + cmbGroup_Head_Code + txtSubGroup_Head_Code)
							+ "</id>";
				} else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			
			int i = 1, i1 = 0;

			try {
				ps1 = connection
						.prepareStatement("Select max(MINOR_HEAD_CODE) from ANNUAL_ACCOUNT_SUB_GROUP");
				results2 = ps1.executeQuery();
				xml = xml + "<flag1>success1</flag1>";

				if (results2.next()) {
					i1 = results2.getInt(1);
					i = i + i1;

				} else {
					i = i;
				}
				xml = xml + "<sub_Head_Code>" + i + "</sub_Head_Code>";

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
