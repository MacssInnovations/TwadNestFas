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
 * Servlet implementation class MIS_Major_Group_Acc_Head_Mapping
 */
public class MIS_Major_Group_Acc_Head_Mapping extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public MIS_Major_Group_Acc_Head_Mapping() {
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
		if (strCommand.equalsIgnoreCase("FirstLoad1")){
			xml = "<response><command>FirstLoad1</command>";

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

			try {				
				StringBuilder strBuffer = null;
				String sql="";
				if(request.getParameter("budgetGroup")==null){
					strBuffer = new StringBuilder();
					//strBuffer = "select MAJOR_HEAD_CODE,GROUP_HEAD_CODE,MINOR_HEAD_CODE,ACCOUNT_HEAD_CODE from FAS_MIS_GROUP_ACC_HD_MAPPING order by GROUP_HEAD_CODE";
					strBuffer.append("SELECT a.MAJOR_HEAD_CODE AS MAJOR_HEAD_CODE, \n");
					strBuffer.append("  a.GROUP_HEAD_CODE      AS GROUP_HEAD_CODE, \n");
					strBuffer.append("  a.MINOR_HEAD_CODE      AS MINOR_HEAD_CODE, \n");
					strBuffer.append("  a.ACCOUNT_HEAD_CODE    AS ACCOUNT_HEAD_CODE, \n");
					strBuffer.append("  b.MAJOR_HEAD_DESC      AS MAJOR_HEAD_DESC, \n");
					strBuffer.append("  c.GROUP_HEAD_DESC      AS GROUP_HEAD_DESC, \n");
					strBuffer.append("  d.MINOR_HEAD_DESC      AS MINOR_HEAD_DESC, \n");
					strBuffer.append("  e.ACCOUNT_HEAD_DESC    AS ACCOUNT_HEAD_DESC \n");
					strBuffer.append("FROM \n");
					strBuffer.append("  (SELECT MAJOR_HEAD_CODE, \n");
					strBuffer.append("    GROUP_HEAD_CODE, \n");
					strBuffer.append("    MINOR_HEAD_CODE, \n");
					strBuffer.append("    ACCOUNT_HEAD_CODE \n");
					strBuffer.append("  FROM FAS_MIS_GROUP_ACC_HD_MAPPING \n");
					strBuffer.append("  )a \n");
					strBuffer.append("LEFT OUTER JOIN \n");
					strBuffer.append("  (SELECT MAJOR_HEAD_CODE AS majorheadcode, \n");
					strBuffer.append("    MAJOR_HEAD_DESC \n");
					strBuffer.append("  FROM COM_MST_MAJOR_HEADS \n");
					strBuffer.append("  )b \n");
					strBuffer.append("ON a.MAJOR_HEAD_CODE=b.majorheadcode \n");
					strBuffer.append("LEFT OUTER JOIN \n");
					strBuffer.append("  (SELECT MAJOR_HEAD_CODE AS MAJOR_HEADCODE, \n");
					strBuffer.append("    GROUP_HEAD_CODE       AS GROUP_HEADCODE, \n");
					strBuffer.append("    GROUP_HEAD_DESC \n");
					strBuffer.append("  FROM FAS_MIS_GROUP_MASTER \n");
					strBuffer.append("  WHERE STATUS='L' \n");
					strBuffer.append("  )c \n");
					strBuffer.append("ON a.MAJOR_HEAD_CODE = c.MAJOR_HEADCODE \n");
					strBuffer.append("AND a.GROUP_HEAD_CODE=c.GROUP_HEADCODE \n");
					strBuffer.append("LEFT OUTER JOIN \n");
					strBuffer.append("  (SELECT MAJOR_HEAD_CODE AS MAJORHEAD_CODE, \n");
					strBuffer.append("    MINOR_HEAD_CODE       AS MINORHEAD_CODE, \n");
					strBuffer.append("    MINOR_HEAD_DESC \n");
					strBuffer.append("  FROM COM_MST_MINOR_HEADS \n");
					strBuffer.append("  )d \n");
					strBuffer.append("ON a.MAJOR_HEAD_CODE  =d.MAJORHEAD_CODE \n");
					strBuffer.append("AND a.MINOR_HEAD_CODE = d.MINORHEAD_CODE \n");
					strBuffer.append("LEFT OUTER JOIN \n");
					strBuffer.append("  (SELECT ACCOUNT_HEAD_CODE AS ACCOUNTHEAD_CODE, \n");
					strBuffer.append("    ACCOUNT_HEAD_DESC, \n");
					strBuffer.append("    MAJOR_HEAD_CODE AS MAJOR_HEAD, \n");
					strBuffer.append("    MINOR_HEAD_CODE AS MINOR_HEAD \n");
					strBuffer.append("  FROM COM_MST_ACCOUNT_HEADS \n");
					strBuffer.append("  )e \n");
					strBuffer.append("ON a.MAJOR_HEAD_CODE   = e.MAJOR_HEAD \n");
					strBuffer.append("AND a.MINOR_HEAD_CODE  =e.MINOR_HEAD \n");
					strBuffer.append("AND a.ACCOUNT_HEAD_CODE=e.ACCOUNTHEAD_CODE \n");
					strBuffer.append("ORDER BY a.GROUP_HEAD_CODE");
					//sql = strBuffer.toString();
				}else{
					strBuffer = new StringBuilder();
					String budgetGroup = request.getParameter("budgetGroup");
					//sql = "select MAJOR_HEAD_CODE,GROUP_HEAD_CODE,MINOR_HEAD_CODE,ACCOUNT_HEAD_CODE from FAS_MIS_GROUP_ACC_HD_MAPPING where MAJOR_HEAD_CODE='"+budgetGroup+"' order by GROUP_HEAD_CODE";
					strBuffer.append("SELECT a.MAJOR_HEAD_CODE AS MAJOR_HEAD_CODE, \n");
					strBuffer.append("  a.GROUP_HEAD_CODE      AS GROUP_HEAD_CODE, \n");
					strBuffer.append("  a.MINOR_HEAD_CODE      AS MINOR_HEAD_CODE, \n");
					strBuffer.append("  a.ACCOUNT_HEAD_CODE    AS ACCOUNT_HEAD_CODE, \n");
					strBuffer.append("  b.MAJOR_HEAD_DESC      AS MAJOR_HEAD_DESC, \n");
					strBuffer.append("  c.GROUP_HEAD_DESC      AS GROUP_HEAD_DESC, \n");
					strBuffer.append("  d.MINOR_HEAD_DESC      AS MINOR_HEAD_DESC, \n");
					strBuffer.append("  e.ACCOUNT_HEAD_DESC    AS ACCOUNT_HEAD_DESC \n");
					strBuffer.append("FROM \n");
					strBuffer.append("  (SELECT MAJOR_HEAD_CODE, \n");
					strBuffer.append("    GROUP_HEAD_CODE, \n");
					strBuffer.append("    MINOR_HEAD_CODE, \n");
					strBuffer.append("    ACCOUNT_HEAD_CODE \n");
					strBuffer.append("  FROM FAS_MIS_GROUP_ACC_HD_MAPPING \n");
					strBuffer.append("  WHERE MAJOR_HEAD_CODE='"+budgetGroup+"' \n");
					strBuffer.append("  )a \n");
					strBuffer.append("LEFT OUTER JOIN \n");
					strBuffer.append("  (SELECT MAJOR_HEAD_CODE AS majorheadcode, \n");
					strBuffer.append("    MAJOR_HEAD_DESC \n");
					strBuffer.append("  FROM COM_MST_MAJOR_HEADS \n");
					strBuffer.append("  )b \n");
					strBuffer.append("ON a.MAJOR_HEAD_CODE=b.majorheadcode \n");
					strBuffer.append("LEFT OUTER JOIN \n");
					strBuffer.append("  (SELECT MAJOR_HEAD_CODE AS MAJOR_HEADCODE, \n");
					strBuffer.append("    GROUP_HEAD_CODE       AS GROUP_HEADCODE, \n");
					strBuffer.append("    GROUP_HEAD_DESC \n");
					strBuffer.append("  FROM FAS_MIS_GROUP_MASTER \n");
					strBuffer.append("  WHERE STATUS='L' \n");
					strBuffer.append("  )c \n");
					strBuffer.append("ON a.MAJOR_HEAD_CODE = c.MAJOR_HEADCODE \n");
					strBuffer.append("AND a.GROUP_HEAD_CODE=c.GROUP_HEADCODE \n");
					strBuffer.append("LEFT OUTER JOIN \n");
					strBuffer.append("  (SELECT MAJOR_HEAD_CODE AS MAJORHEAD_CODE, \n");
					strBuffer.append("    MINOR_HEAD_CODE       AS MINORHEAD_CODE, \n");
					strBuffer.append("    MINOR_HEAD_DESC \n");
					strBuffer.append("  FROM COM_MST_MINOR_HEADS \n");
					strBuffer.append("  )d \n");
					strBuffer.append("ON a.MAJOR_HEAD_CODE  =d.MAJORHEAD_CODE \n");
					strBuffer.append("AND a.MINOR_HEAD_CODE = d.MINORHEAD_CODE \n");
					strBuffer.append("LEFT OUTER JOIN \n");
					strBuffer.append("  (SELECT ACCOUNT_HEAD_CODE AS ACCOUNTHEAD_CODE, \n");
					strBuffer.append("    ACCOUNT_HEAD_DESC, \n");
					strBuffer.append("    MAJOR_HEAD_CODE AS MAJOR_HEAD, \n");
					strBuffer.append("    MINOR_HEAD_CODE AS MINOR_HEAD \n");
					strBuffer.append("  FROM COM_MST_ACCOUNT_HEADS \n");
					strBuffer.append("  )e \n");
					strBuffer.append("ON a.MAJOR_HEAD_CODE   = e.MAJOR_HEAD \n");
					strBuffer.append("AND a.MINOR_HEAD_CODE  =e.MINOR_HEAD \n");
					strBuffer.append("AND a.ACCOUNT_HEAD_CODE=e.ACCOUNTHEAD_CODE \n");
					strBuffer.append("ORDER BY a.GROUP_HEAD_CODE");

				}
				sql = strBuffer.toString();
				ps1 = connection.prepareStatement(sql);
				rs = ps1.executeQuery();
				while (rs.next()) {

					xml = xml + "<cmbBudgetGroupMajor>"
							+ rs.getString("MAJOR_HEAD_CODE")
							+ "</cmbBudgetGroupMajor>";
					xml = xml + "<txtGroup_Head_Code>"
							+ rs.getInt("GROUP_HEAD_CODE")
							+ "</txtGroup_Head_Code>";
					xml = xml + "<cmbBudgetGroupMinor>"
							+ rs.getInt("MINOR_HEAD_CODE")
							+ "</cmbBudgetGroupMinor>";
					xml = xml + "<txtAcc_HeadCode>"
							+ rs.getInt("ACCOUNT_HEAD_CODE")
							+ "</txtAcc_HeadCode>";
					/*xml= xml+"<MAJOR_HEAD_DESC><![CDATA["
							+rs.getString("MAJOR_HEAD_DESC")
							+"]]></MAJOR_HEAD_DESC>";*/
					xml= xml+"<GROUP_HEAD_DESC><![CDATA["
							+rs.getString("GROUP_HEAD_DESC")
							+"]]></GROUP_HEAD_DESC>";
					xml= xml+"<MINOR_HEAD_DESC><![CDATA["
							+rs.getString("MINOR_HEAD_DESC")
							+"]]></MINOR_HEAD_DESC>";
					if(rs.getString("ACCOUNT_HEAD_DESC")!=null){
						xml=xml+"<ACCOUNT_HEAD_DESC><![CDATA["
								+rs.getString("ACCOUNT_HEAD_DESC")
								+"]]></ACCOUNT_HEAD_DESC>";
					}else{
						xml=xml+"<ACCOUNT_HEAD_DESC>"+" "+"</ACCOUNT_HEAD_DESC>";
					}
					

				}
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("getMinorBudgetHeadDesc")){
			System.out.println(strCommand);
			xml = "<response><command>getMinorBudgetHeadDesc</command>";
			String type = "";
			if(request.getParameter("type")!=null){
				type = request.getParameter("type");
			}else{
				type = "";
			}
			System.out.println("type "+type);
			String cmbBudgetGroupMajor = request.getParameter("cmbBudgetGroupMajor");
			System.out.println(cmbBudgetGroupMajor);
			if("E".equals(cmbBudgetGroupMajor))
			{
			try {
				PreparedStatement preparedStatement = null;
				ResultSet resultSet = null;
				String sql="select GROUP_HEAD_CODE,GROUP_HEAD_DESC from FAS_MIS_GROUP_MASTER where MAJOR_HEAD_CODE='E' and TYPE='MIS' order by MAJOR_HEAD_CODE";
				System.out.println("sql "+sql);
				preparedStatement = connection.prepareStatement(sql);
				//preparedStatement.setString(1, cmbBudgetGroupMajor);
				//preparedStatement.setString(1, type);
				resultSet= preparedStatement.executeQuery();
				int count = 0;
				while (resultSet.next()) {
					xml = xml + "<cmbGroup_Head_Code>"
							+ resultSet.getInt("GROUP_HEAD_CODE")
							+ "</cmbGroup_Head_Code>";
					xml = xml + "<cmbGroup_Head_Desc><![CDATA["
							+ resultSet.getString("GROUP_HEAD_DESC")
							+ "]]></cmbGroup_Head_Desc>";
					count++;
				}
				if(count>0){
					xml = xml + "<flag4>success</flag4>";
				}else{
					xml = xml + "<flag4>fail</flag4>";
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag4>failure</flag4>";
			}
			}else{
			try {
				PreparedStatement preparedStatement = null;
				ResultSet resultSet = null;
				String sql="select GROUP_HEAD_CODE,GROUP_HEAD_DESC from FAS_MIS_GROUP_MASTER where MAJOR_HEAD_CODE=? order by MAJOR_HEAD_CODE";
				System.out.println("sql "+sql);
				preparedStatement = connection.prepareStatement(sql);
				preparedStatement.setString(1, cmbBudgetGroupMajor);
				//preparedStatement.setString(1, type);
				resultSet= preparedStatement.executeQuery();
				int count = 0;
				while (resultSet.next()) {
					xml = xml + "<cmbGroup_Head_Code>"
							+ resultSet.getInt("GROUP_HEAD_CODE")
							+ "</cmbGroup_Head_Code>";
					xml = xml + "<cmbGroup_Head_Desc><![CDATA["
							+ resultSet.getString("GROUP_HEAD_DESC")
							+ "]]></cmbGroup_Head_Desc>";
					count++;
				}
				if(count>0){
					xml = xml + "<flag4>success</flag4>";
				}else{
					xml = xml + "<flag4>fail</flag4>";
				}
				
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag4>failure</flag4>";
			}
			}
			try {
				ps = connection.prepareStatement("select MINOR_HEAD_CODE,MINOR_HEAD_DESC from COM_MST_MINOR_HEADS where MAJOR_HEAD_CODE=? order by MINOR_HEAD_DESC");
				ps.setString(1, cmbBudgetGroupMajor);
				rs2 = ps.executeQuery();
				while (rs2.next()) {
					xml = xml + "<BudgetIdSub>" + rs2.getInt("MINOR_HEAD_CODE")
							+ "</BudgetIdSub>";
					xml = xml + "<BudgetDescSub><![CDATA["
							+ rs2.getString("MINOR_HEAD_DESC")
							+ "]]></BudgetDescSub>";
				}
				xml = xml + "<flag3>success</flag3>";
			} catch (Exception e) {
				xml = xml + "<flag3>failure</flag3>";
				e.printStackTrace();
			}
				//System.out.println(cmbBudgetGroupMajor);
					
		} else if (strCommand.equalsIgnoreCase("add")) {
			xml = "<response><command>add</command>";

			String cmbBudgetGroupMajor = request
					.getParameter("cmbBudgetGroupMajor");
			int cmbGroup_Head_Code = Integer.parseInt(request
					.getParameter("cmbGroup_Head_Code"));
			int cmbBudgetGroupMinor = Integer.parseInt(request
					.getParameter("cmbBudgetGroupMinor"));
			int txtAcc_HeadCode = Integer.parseInt(request
					.getParameter("txtAcc_HeadCode"));
			

			try {
				ps1 = connection
						.prepareStatement("select ACCOUNT_HEAD_CODE from FAS_MIS_GROUP_ACC_HD_MAPPING where MAJOR_HEAD_CODE=? and GROUP_HEAD_CODE=? and MINOR_HEAD_CODE=? and ACCOUNT_HEAD_CODE=?");
				ps1.setString(1, cmbBudgetGroupMajor);
				ps1.setInt(2, cmbGroup_Head_Code);
				ps1.setInt(3, cmbBudgetGroupMinor);
				ps1.setInt(4, txtAcc_HeadCode);
				rs = ps1.executeQuery();
				if (rs.next()) {
					xml = xml + "<flag>Exist</flag>";
				} else {
					ps = connection
							.prepareStatement("insert into FAS_MIS_GROUP_ACC_HD_MAPPING(MAJOR_HEAD_CODE,GROUP_HEAD_CODE,MINOR_HEAD_CODE,ACCOUNT_HEAD_CODE,STATUS,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?)");
					ps.setString(1, cmbBudgetGroupMajor);
					ps.setInt(2, cmbGroup_Head_Code);
					ps.setInt(3, cmbBudgetGroupMinor);
					ps.setInt(4, txtAcc_HeadCode);
					ps.setString(5, "L");
					ps.setString(6, userid);
					ps.setTimestamp(7, ts);
					ps.executeUpdate();

					xml = xml + "<cmbBudgetGroupMajor>" + cmbBudgetGroupMajor
							+ "</cmbBudgetGroupMajor>";
					xml = xml + "<cmbGroup_Head_Code>" + cmbGroup_Head_Code
							+ "</cmbGroup_Head_Code>";
					xml = xml + "<cmbBudgetGroupMinor>" + cmbBudgetGroupMinor
							+ "</cmbBudgetGroupMinor>";
					xml = xml + "<txtAcc_HeadCode>" + txtAcc_HeadCode
							+ "</txtAcc_HeadCode>";

					xml = xml + "<flag>success</flag>";
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		} else if (strCommand.equalsIgnoreCase("LoadData")) {
			
			xml = "<response><command>LoadData</command>";

			String cmbBudgetGroupMajor = request
					.getParameter("cmbBudgetGroupMajor");
			int cmbGroup_Head_Code = Integer.parseInt(request.getParameter("cmbGroup_Head_Code"));
			int cmbBudgetGroupMinor = Integer.parseInt(request
					.getParameter("cmbBudgetGroupMinor"));
			int txtAcc_HeadCode = Integer.parseInt(request
					.getParameter("txtAcc_HeadCode"));

			xml = xml + "<cmbBudgetGroupMajor>" + cmbBudgetGroupMajor
					+ "</cmbBudgetGroupMajor>";

			try {
				ps = connection
						.prepareStatement(" select GROUP_HEAD_CODE,GROUP_HEAD_DESC,MINOR_HEAD_CODE,"
								+ "MINOR_HEAD_DESC,ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from (SELECT "
								+ "MAJOR_HEAD_CODE,GROUP_HEAD_CODE,MINOR_HEAD_CODE,ACCOUNT_HEAD_CODE FROM "
								+ "FAS_MIS_GROUP_ACC_HD_MAPPING WHERE MAJOR_HEAD_CODE=? AND GROUP_HEAD_CODE  =? "
								+ "AND MINOR_HEAD_CODE  =? AND ACCOUNT_HEAD_CODE=?)a left outer join (select "
								+ "MAJOR_HEAD_CODE as mhc,GROUP_HEAD_CODE as ghc,GROUP_HEAD_DESC FROM "
								+ "FAS_MIS_GROUP_MASTER)b on a.MAJOR_HEAD_CODE = b.mhc and "
								+ "a.GROUP_HEAD_CODE = b.ghc left outer join ( select MAJOR_HEAD_CODE as mhc1,"
								+ "MINOR_HEAD_CODE as mihc1,MINOR_HEAD_DESC from COM_MST_MINOR_HEADS )c on "
								+ "a.MAJOR_HEAD_CODE = c.mhc1 and a.MINOR_HEAD_CODE = c.mihc1 left outer join "
								+ "(select ACCOUNT_HEAD_CODE as ahc2,ACCOUNT_HEAD_DESC,MAJOR_HEAD_CODE as mhc2,"
								+ "MINOR_HEAD_CODE as mihc2 from COM_MST_ACCOUNT_HEADS )d on "
								+ "a.ACCOUNT_HEAD_CODE = d.ahc2 and a.MAJOR_HEAD_CODE = d.mhc2 and "
								+ "a.MINOR_HEAD_CODE = d.mihc2 ");
				ps.setString(1, cmbBudgetGroupMajor);
				ps.setInt(2, cmbGroup_Head_Code);
				ps.setInt(3, cmbBudgetGroupMinor);
				ps.setInt(4, txtAcc_HeadCode);
				rs2 = ps.executeQuery();
				while (rs2.next()) {
					xml = xml + "<cmbGroup_Head_Code>" + cmbGroup_Head_Code
							+ "</cmbGroup_Head_Code>";
					xml = xml + "<cmbGroup_Head_Desc>"
							+ rs2.getString("GROUP_HEAD_DESC")
							+ "</cmbGroup_Head_Desc>";
					xml = xml + "<cmbBudgetGroupMinor>" + cmbBudgetGroupMinor
							+ "</cmbBudgetGroupMinor>";
					xml = xml + "<cmbBudgetGroupMinorDesc>"
							+ rs2.getString("MINOR_HEAD_DESC")
							+ "</cmbBudgetGroupMinorDesc>";
					xml = xml + "<txtAcc_HeadCode>" + txtAcc_HeadCode
							+ "</txtAcc_HeadCode>";
					xml = xml + "<txtAcc_HeadCodeDesc>"
							+ rs2.getString("ACCOUNT_HEAD_DESC")
							+ "</txtAcc_HeadCodeDesc>";
				}
				xml = xml + "<flag1>success</flag1>";
			} catch (Exception e) {
				xml = xml + "<flag1>failure</flag1>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("deleted")) {

			xml = "<response><command>deleted</command>";
			String cmbBudgetGroupMajor = request
					.getParameter("cmbBudgetGroupMajor");
			int cmbGroup_Head_Code = Integer.parseInt(request
					.getParameter("cmbGroup_Head_Code"));
			int cmbBudgetGroupMinor = Integer.parseInt(request
					.getParameter("cmbBudgetGroupMinor"));
			int txtAcc_HeadCode = Integer.parseInt(request
					.getParameter("txtAcc_HeadCode"));
			try {
				ps1 = connection
						.prepareStatement("select ACCOUNT_HEAD_CODE from FAS_MIS_GROUP_ACC_HD_MAPPING where MAJOR_HEAD_CODE=? and GROUP_HEAD_CODE=? and MINOR_HEAD_CODE=? and ACCOUNT_HEAD_CODE=?");
				ps1.setString(1, cmbBudgetGroupMajor);
				ps1.setInt(2, cmbGroup_Head_Code);
				ps1.setInt(3, cmbBudgetGroupMinor);
				ps1.setInt(4, txtAcc_HeadCode);
				rs = ps1.executeQuery();
				if (rs.next()) {
					ps = connection
							.prepareStatement("delete from FAS_MIS_GROUP_ACC_HD_MAPPING where MAJOR_HEAD_CODE=? and GROUP_HEAD_CODE=? and MINOR_HEAD_CODE=? and ACCOUNT_HEAD_CODE=?");
					ps.setString(1, cmbBudgetGroupMajor);
					ps.setInt(2, cmbGroup_Head_Code);
					ps.setInt(3, cmbBudgetGroupMinor);
					ps.setInt(4, txtAcc_HeadCode);
					ps.executeUpdate();
					xml = xml + "<flag>success</flag>";
					xml = xml
							+ "<id>"
							+ (cmbBudgetGroupMajor + cmbGroup_Head_Code
									+ cmbBudgetGroupMinor + txtAcc_HeadCode)
							+ "</id>";
				} else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		}
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}

}
