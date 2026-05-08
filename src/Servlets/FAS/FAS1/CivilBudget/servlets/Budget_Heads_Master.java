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
 * Servlet implementation class Budget_Heads_Master
 */
public class Budget_Heads_Master extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Budget_Heads_Master() {
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
		ResultSet rs2 = null,res=null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		PreparedStatement ps2 = null,prep=null;
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
		String gr_desc=null;

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

			String cmbFormatNo = request.getParameter("cmbFormatNo");
			int txtBudgetGroupMajor = Integer.parseInt(request
					.getParameter("txtBudgetGroupMajor"));

			String txtBudgetGroupMinor = request
					.getParameter("txtBudgetGroupMinor");
			if ((txtBudgetGroupMinor.equals("")) || (txtBudgetGroupMinor == null)) {
				txtBudgetGroupMinor = null;
			}
			String txtBudgetDescMain = request
					.getParameter("txtBudgetDescMain");
			int txtBudgetDescSub = Integer.parseInt(request.getParameter("txtBudgetDescSub"));
			String txtCanExceedBudget = request
					.getParameter("txtCanExceedBudget");
			String txtCanbeReAppropriated = request
					.getParameter("txtCanbeReAppropriated");
			String txtCanbeRatified = request.getParameter("txtCanbeRatified");

			try {
				ps1 = connection
						.prepareStatement("select FORMAT_NO from FAS_BUDGET_HEADS_MASTER where FORMAT_NO=? and BUDGET_GROUP_MAJOR=?");
				ps1.setString(1, cmbFormatNo);
				//ps1.setString(2, txtBudgetDescMain);
				//ps1.setInt(3, txtBudgetDescSub);
				ps1.setInt(2, txtBudgetGroupMajor);
				rs = ps1.executeQuery();
				if (rs.next()) {
					xml = xml + "<flag>Exist</flag>";
				} else {
					ps = connection
							.prepareStatement("insert into FAS_BUDGET_HEADS_MASTER(FORMAT_NO,BUDGET_HEADS_ID_MAJOR,BUDGET_HEADS_ID_SUB,CAN_EXCEED_BUDGET,CAN_BE_RE_APPROPRIATED,CAN_BE_RATIFIED,UPDATED_BY_USERID,UPDATED_DATE,BUDGET_GROUP_MAJOR,BUDGET_GROUP_MINOR) values(?,?,?,?,?,?,?,?,?,?)");
					ps.setString(1, cmbFormatNo);
					ps.setString(2, txtBudgetDescMain);
					ps.setInt(3, txtBudgetDescSub);
					ps.setString(4, txtCanExceedBudget);
					ps.setString(5, txtCanbeReAppropriated);
					ps.setString(6, txtCanbeRatified);
					ps.setString(7, userid);
					ps.setTimestamp(8, ts);
					ps.setInt(9, txtBudgetGroupMajor);
					ps.setString(10, txtBudgetGroupMinor);

					int k=ps.executeUpdate();
					if(k>0)
					{
						prep=connection.prepareStatement("select BUDGET_GROUP_ID,BUDGET_GROUP_MAJOR from FAS_BUDGET_GROUP_MASTER where BUDGET_GROUP_ID="+txtBudgetGroupMajor);
						res=prep.executeQuery();
						while(res.next())
						{
							gr_desc=res.getString("BUDGET_GROUP_MAJOR");
							
						}
					}

					xml = xml + "<flag>success</flag>";
					xml = xml + "<cmbFormatNo>" + cmbFormatNo
							+ "</cmbFormatNo>";
					xml = xml + "<txtBudgetDescMain>" + txtBudgetDescMain
							+ "</txtBudgetDescMain>";
					xml = xml + "<txtBudgetDescSub>" + txtBudgetDescSub
							+ "</txtBudgetDescSub>";
					xml = xml + "<txtCanExceedBudget>" + txtCanExceedBudget
							+ "</txtCanExceedBudget>";
					xml = xml + "<txtCanbeReAppropriated>"
							+ txtCanbeReAppropriated
							+ "</txtCanbeReAppropriated>";
					xml = xml + "<txtCanbeRatified>" + txtCanbeRatified
							+ "</txtCanbeRatified>";
					xml = xml + "<txtBudgetGroupMajor>" + txtBudgetGroupMajor
							+ "</txtBudgetGroupMajor>";
					xml = xml + "<txtBudgetGroupMinor>" + txtBudgetGroupMinor
							+ "</txtBudgetGroupMinor>";
					xml = xml + "<group_desc>" +gr_desc+ "</group_desc>";	
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		}
		else if (strCommand.equalsIgnoreCase("loadgp")) {
		String val=request.getParameter("format");
		int count_1=0;
		//System.out.println("val:::"+val);
		xml = "<response><command>loadgp</command>";
			try {
			String ss="SELECT a.FORMAT_NO         AS FORMAT_NO, " +
								"  a.BUDGET_GROUP_MAJOR     AS BUDGET_GROUP_MAJOR, " +
								"  a.BUDGET_GROUP_MINOR     AS BUDGET_GROUP_MINOR, " +
								"  a.CAN_EXCEED_BUDGET      AS CAN_EXCEED_BUDGET, " +
								"  a.CAN_BE_RE_APPROPRIATED AS CAN_BE_RE_APPROPRIATED, " +
								"  a.CAN_BE_RATIFIED        AS CAN_BE_RATIFIED, " +
								"  a.BUDGET_HEADS_ID_MAJOR  AS BUDGET_HEADS_ID_MAJOR, " +
								"  a.BUDGET_HEADS_ID_SUB    AS BUDGET_HEADS_ID_SUB, " +
								"  b.BUDGET_GROUP_MAJOR     AS BUDGET_GROUP_MAJOR_DEC " +
								"FROM " +
								"  (SELECT FORMAT_NO, " +
								"    BUDGET_GROUP_MAJOR, " +
								"    BUDGET_GROUP_MINOR, " +
								"    CAN_EXCEED_BUDGET, " +
								"    CAN_BE_RE_APPROPRIATED, " +
								"    CAN_BE_RATIFIED, " +
								"    BUDGET_HEADS_ID_MAJOR, " +
								"    BUDGET_HEADS_ID_SUB " +
								"  FROM FAS_BUDGET_HEADS_MASTER  where FORMAT_NO='"+val+"' )a " +
								" LEFT OUTER JOIN " +
								"  ( SELECT BUDGET_GROUP_ID, BUDGET_GROUP_MAJOR FROM FAS_BUDGET_GROUP_MASTER " +
								"  )b " +
								"ON a.BUDGET_GROUP_MAJOR=b.BUDGET_GROUP_ID " +
								"ORDER BY a.FORMAT_NO, " +
								"  a.BUDGET_GROUP_MAJOR";
			System.out.println("ss:::"+ss);
			
				ps1 = connection.prepareStatement(ss);
				rs = ps1.executeQuery();
				while (rs.next()) {
				
					xml = xml + "<cmbFormatNo>" + rs.getString("FORMAT_NO")
							+ "</cmbFormatNo>";
//					xml = xml + "<txtBudgetDescMain>"
//							+ rs.getString("BUDGET_HEADS_ID_MAJOR")
//							+ "</txtBudgetDescMain>";
//					xml = xml + "<txtBudgetDescSub>"
//							+ rs.getInt("BUDGET_HEADS_ID_SUB")
//							+ "</txtBudgetDescSub>";
					xml = xml + "<txtCanExceedBudget>"
							+ rs.getString("CAN_EXCEED_BUDGET")
							+ "</txtCanExceedBudget>";
					xml = xml + "<txtCanbeReAppropriated>"
							+ rs.getString("CAN_BE_RE_APPROPRIATED")
							+ "</txtCanbeReAppropriated>";
					xml = xml + "<txtCanbeRatified>"
							+ rs.getString("CAN_BE_RATIFIED")
							+ "</txtCanbeRatified>";
					xml = xml + "<txtBudgetGroupMajor1>"
							+ rs.getInt("BUDGET_GROUP_MAJOR")
							+ "</txtBudgetGroupMajor1>";
					xml = xml + "<txtBudgetGroupMinor>"
							+ rs.getString("BUDGET_GROUP_MINOR")
							+ "</txtBudgetGroupMinor>";
					xml = xml + "<BUDGET_GROUP_MAJOR>"
							+ rs.getString("BUDGET_GROUP_MAJOR_DEC")
							+ "</BUDGET_GROUP_MAJOR>";
					count_1++;
				}
				if(count_1>0)
				{
				xml = xml + "<flag>success</flag>";
				}
				else
				{
					xml = xml + "<flag>failure</flag>";
				}
				
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		}
		
		else if (strCommand.equalsIgnoreCase("loadsec")) {
			String val=request.getParameter("format");
			int major=Integer.parseInt(request.getParameter("txtBudgetGroupMajor"));
			int count_1=0;
			xml = "<response><command>loadsec</command>";
				try {
					ps1 = connection
							.prepareStatement("select * from(SELECT a.FORMAT_NO         AS FORMAT_NO, " +
									"  a.BUDGET_GROUP_MAJOR     AS BUDGET_GROUP_MAJOR, " +
									"  a.BUDGET_GROUP_MINOR     AS BUDGET_GROUP_MINOR, " +
									"  a.CAN_EXCEED_BUDGET      AS CAN_EXCEED_BUDGET, " +
									"  a.CAN_BE_RE_APPROPRIATED AS CAN_BE_RE_APPROPRIATED, " +
									"  a.CAN_BE_RATIFIED        AS CAN_BE_RATIFIED, " +
									"  a.BUDGET_HEADS_ID_MAJOR  AS BUDGET_HEADS_ID_MAJOR, " +
									"  a.BUDGET_HEADS_ID_SUB    AS BUDGET_HEADS_ID_SUB, " +
									"  b.BUDGET_GROUP_MAJOR     AS BUDGET_GROUP_MAJOR_DEC " +
									"FROM " +
									"  (SELECT FORMAT_NO, " +
									"    BUDGET_GROUP_MAJOR, " +
									"    BUDGET_GROUP_MINOR, " +
									"    CAN_EXCEED_BUDGET, " +
									"    CAN_BE_RE_APPROPRIATED, " +
									"    CAN_BE_RATIFIED, " +
									"    BUDGET_HEADS_ID_MAJOR, " +
									"    BUDGET_HEADS_ID_SUB " +
									"  FROM FAS_BUDGET_HEADS_MASTER)a " +
									" LEFT OUTER JOIN " +
									"  ( SELECT BUDGET_GROUP_ID, BUDGET_GROUP_MAJOR FROM FAS_BUDGET_GROUP_MASTER " +
									"  )b " +
									"ON a.BUDGET_GROUP_MAJOR=b.BUDGET_GROUP_ID " +
									"ORDER BY a.FORMAT_NO, " +
									"  a.BUDGET_GROUP_MAJOR)where FORMAT_NO='"+val+"' and BUDGET_GROUP_MAJOR="+major);
					rs = ps1.executeQuery();
					while (rs.next()) {
					
						xml = xml + "<cmbFormatNo>" + rs.getString("FORMAT_NO")
								+ "</cmbFormatNo>";
						xml = xml + "<txtCanExceedBudget>"
								+ rs.getString("CAN_EXCEED_BUDGET")
								+ "</txtCanExceedBudget>";
						xml = xml + "<txtCanbeReAppropriated>"
								+ rs.getString("CAN_BE_RE_APPROPRIATED")
								+ "</txtCanbeReAppropriated>";
						xml = xml + "<txtCanbeRatified>"
								+ rs.getString("CAN_BE_RATIFIED")
								+ "</txtCanbeRatified>";
						xml = xml + "<txtBudgetGroupMajor1>"
								+ rs.getInt("BUDGET_GROUP_MAJOR")
								+ "</txtBudgetGroupMajor1>";
						xml = xml + "<txtBudgetGroupMinor>"
								+ rs.getString("BUDGET_GROUP_MINOR")
								+ "</txtBudgetGroupMinor>";
						xml = xml + "<BUDGET_GROUP_MAJOR>"
								+ rs.getString("BUDGET_GROUP_MAJOR_DEC")
								+ "</BUDGET_GROUP_MAJOR>";
						count_1++;
					}
					if(count_1>0)
					{
					xml = xml + "<flag>success</flag>";
					}
					else
					{
						xml = xml + "<flag>failure</flag>";
					}
					
				} catch (Exception e) {
					xml = xml + "<flag>failure</flag>";
					e.printStackTrace();
				}
			}
		else if (strCommand.equalsIgnoreCase("getGrid")) {
			xml = "<response><command>getGrid</command>";

			try {
				ps = connection
						.prepareStatement("select FORMAT_NO from FAS_FORMAT_MASTER order by FORMAT_NO");
				rs2 = ps.executeQuery();
				while (rs2.next()) {
					xml = xml + "<FormatNo>" + rs2.getString("FORMAT_NO")
							+ "</FormatNo>";
				}
				xml = xml + "<flag1>success</flag1>";
			} catch (Exception e) {
				xml = xml + "<flag1>failure</flag1>";
				e.printStackTrace();
			}
			try {
				ps = connection
						.prepareStatement("select BUDGET_GROUP_ID,BUDGET_GROUP_MAJOR from FAS_BUDGET_GROUP_MASTER order by BUDGET_GROUP_ID");
				rs2 = ps.executeQuery();
				while (rs2.next()) {
					xml = xml + "<txtBudgetGroupId>"
							+ rs2.getInt("BUDGET_GROUP_ID")
							+ "</txtBudgetGroupId>";
					xml = xml + "<txtBudgetGroupMajor>"
							+ rs2.getString("BUDGET_GROUP_MAJOR")
							+ "</txtBudgetGroupMajor>";
				}
				xml = xml + "<flag11>success</flag11>";
			} catch (Exception e) {
				xml = xml + "<flag11>failure</flag11>";
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

			try {
				ps1 = connection
						.prepareStatement("SELECT a.FORMAT_NO         AS FORMAT_NO, " +
								"  a.BUDGET_GROUP_MAJOR     AS BUDGET_GROUP_MAJOR, " +
								"  a.BUDGET_GROUP_MINOR     AS BUDGET_GROUP_MINOR, " +
								"  a.CAN_EXCEED_BUDGET      AS CAN_EXCEED_BUDGET, " +
								"  a.CAN_BE_RE_APPROPRIATED AS CAN_BE_RE_APPROPRIATED, " +
								"  a.CAN_BE_RATIFIED        AS CAN_BE_RATIFIED, " +
								"  a.BUDGET_HEADS_ID_MAJOR  AS BUDGET_HEADS_ID_MAJOR, " +
								"  a.BUDGET_HEADS_ID_SUB    AS BUDGET_HEADS_ID_SUB, " +
								"  b.BUDGET_GROUP_MAJOR     AS BUDGET_GROUP_MAJOR_DEC " +
								"FROM " +
								"  (SELECT FORMAT_NO, " +
								"    BUDGET_GROUP_MAJOR, " +
								"    BUDGET_GROUP_MINOR, " +
								"    CAN_EXCEED_BUDGET, " +
								"    CAN_BE_RE_APPROPRIATED, " +
								"    CAN_BE_RATIFIED, " +
								"    BUDGET_HEADS_ID_MAJOR, " +
								"    BUDGET_HEADS_ID_SUB " +
								"  FROM FAS_BUDGET_HEADS_MASTER " +
								"  )a " +
								"LEFT OUTER JOIN " +
								"  ( SELECT BUDGET_GROUP_ID, BUDGET_GROUP_MAJOR FROM FAS_BUDGET_GROUP_MASTER " +
								"  )b " +
								"ON a.BUDGET_GROUP_MAJOR=b.BUDGET_GROUP_ID " +
								"ORDER BY a.FORMAT_NO, " +
								"  a.BUDGET_GROUP_MAJOR");
				rs = ps1.executeQuery();
				while (rs.next()) {

					xml = xml + "<cmbFormatNo>" + rs.getString("FORMAT_NO")
							+ "</cmbFormatNo>";
					xml = xml + "<txtBudgetDescMain>"
							+ rs.getString("BUDGET_HEADS_ID_MAJOR")
							+ "</txtBudgetDescMain>";
					xml = xml + "<txtBudgetDescSub>"
							+ rs.getInt("BUDGET_HEADS_ID_SUB")
							+ "</txtBudgetDescSub>";
					xml = xml + "<txtCanExceedBudget>"
							+ rs.getString("CAN_EXCEED_BUDGET")
							+ "</txtCanExceedBudget>";
					xml = xml + "<txtCanbeReAppropriated>"
							+ rs.getString("CAN_BE_RE_APPROPRIATED")
							+ "</txtCanbeReAppropriated>";
					xml = xml + "<txtCanbeRatified>"
							+ rs.getString("CAN_BE_RATIFIED")
							+ "</txtCanbeRatified>";
					xml = xml + "<txtBudgetGroupMajor1>"
							+ rs.getInt("BUDGET_GROUP_MAJOR")
							+ "</txtBudgetGroupMajor1>";
					xml = xml + "<txtBudgetGroupMinor>"
							+ rs.getString("BUDGET_GROUP_MINOR")
							+ "</txtBudgetGroupMinor>";
					xml = xml + "<BUDGET_GROUP_MAJOR>"
							+ rs.getString("BUDGET_GROUP_MAJOR_DEC")
							+ "</BUDGET_GROUP_MAJOR>";
				}
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("update")) {

			xml = "<response><command>update</command>";

			String cmbFormatNo = request.getParameter("cmbFormatNo");
			int txtBudgetGroupMajor = Integer.parseInt(request
					.getParameter("txtBudgetGroupMajor"));
			String txtBudgetGroupMinor = request
					.getParameter("txtBudgetGroupMinor");
			if ((txtBudgetGroupMinor.equals("")) || (txtBudgetGroupMinor == null)) {
				txtBudgetGroupMinor = null;
			}
			String txtBudgetDescMain = request
					.getParameter("txtBudgetDescMain");
			int txtBudgetDescSub = Integer.parseInt(request
                     .getParameter("txtBudgetDescSub"));
			String txtCanExceedBudget = request
					.getParameter("txtCanExceedBudget");
			String txtCanbeReAppropriated = request
					.getParameter("txtCanbeReAppropriated");
			String txtCanbeRatified = request.getParameter("txtCanbeRatified");
//System.out.println("Update values  -->cmbFormatNo"+cmbFormatNo+"txtBudgetGroupMajor"+txtBudgetGroupMajor+"txtBudgetGroupMinor"+txtBudgetGroupMinor+"txtBudgetDescMain"+txtBudgetDescMain+"txtBudgetDescSub"+txtBudgetDescSub+"txtCanExceedBudget"+txtCanExceedBudget+"txtCanbeReAppropriated"+txtCanbeReAppropriated+"txtCanbeRatified"+txtCanbeRatified);
			try {
				
				ps1 = connection
						.prepareStatement("select FORMAT_NO from FAS_BUDGET_HEADS_MASTER where FORMAT_NO=? and BUDGET_GROUP_MAJOR=?");
				ps1.setString(1, cmbFormatNo);
				//ps1.setString(2, txtBudgetDescMain);
				//ps1.setInt(3, txtBudgetDescSub);
				ps1.setInt(2, txtBudgetGroupMajor);
				rs = ps1.executeQuery();
				if (rs.next()) {
					
					ps = connection
							.prepareStatement("update FAS_BUDGET_HEADS_MASTER set CAN_EXCEED_BUDGET=?,CAN_BE_RE_APPROPRIATED=?,CAN_BE_RATIFIED=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,BUDGET_GROUP_MINOR=? where FORMAT_NO=? and BUDGET_GROUP_MAJOR=?");
					ps.setString(1, txtCanExceedBudget);
					ps.setString(2, txtCanbeReAppropriated);
					ps.setString(3, txtCanbeRatified);
					ps.setString(4, userid);
					ps.setTimestamp(5, ts);	
					ps.setString(6, txtBudgetGroupMinor);
					ps.setString(7, cmbFormatNo);
					ps.setInt(8, txtBudgetGroupMajor);
					/*ps.setString(9, txtBudgetDescMain);
					ps.setInt(10, txtBudgetDescSub);*/
					ps.executeUpdate();

					xml = xml + "<flag>success</flag>";
					xml = xml + "<cmbFormatNo>" + cmbFormatNo
							+ "</cmbFormatNo>";
					xml = xml + "<txtBudgetDescMain>" + txtBudgetDescMain
							+ "</txtBudgetDescMain>";
					xml = xml + "<txtBudgetDescSub>" + txtBudgetDescSub
							+ "</txtBudgetDescSub>";
					xml = xml + "<txtCanExceedBudget>" + txtCanExceedBudget
							+ "</txtCanExceedBudget>";
					xml = xml + "<txtCanbeReAppropriated>"
							+ txtCanbeReAppropriated
							+ "</txtCanbeReAppropriated>";
					xml = xml + "<txtCanbeRatified>" + txtCanbeRatified
							+ "</txtCanbeRatified>";
					xml = xml + "<txtBudgetGroupMajor>" + txtBudgetGroupMajor
							+ "</txtBudgetGroupMajor>";
					xml = xml + "<txtBudgetGroupMinor>" + txtBudgetGroupMinor
							+ "</txtBudgetGroupMinor>";

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
			String cmbFormatNo = request.getParameter("cmbFormatNo");
			String txtBudgetDescMain = request
					.getParameter("txtBudgetDescMain");
			int txtBudgetDescSub = Integer.parseInt(request
					.getParameter("txtBudgetDescSub"));
			int txtBudgetGroupMajor = Integer.parseInt(request
					.getParameter("txtBudgetGroupMajor"));
			try {
				ps1 = connection
						.prepareStatement("select FORMAT_NO from FAS_BUDGET_HEADS_MASTER where FORMAT_NO=? and BUDGET_GROUP_MAJOR=?");
				ps1.setString(1, cmbFormatNo);
				//ps1.setString(2, txtBudgetDescMain);
				//ps1.setInt(3, txtBudgetDescSub);
				ps1.setInt(2, txtBudgetGroupMajor);
				rs = ps1.executeQuery();
				if (rs.next()) {
					ps = connection
							.prepareStatement("delete from FAS_BUDGET_HEADS_MASTER where FORMAT_NO=? and BUDGET_GROUP_MAJOR=?");
					ps.setString(1, cmbFormatNo);
					//ps.setString(2, txtBudgetDescMain);
					//ps.setInt(3, txtBudgetDescSub);
					ps.setInt(2, txtBudgetGroupMajor);
					ps.executeUpdate();

					ps2 = connection
							.prepareStatement("delete from FAS_BUDGET_HDS_AC_HDS_MAP_MST where FORMAT_NO=?");
					ps2.setString(1, cmbFormatNo);
					//ps2.setString(2, txtBudgetDescMain);
					//ps2.setInt(3, txtBudgetDescSub);
					ps2.executeUpdate();
					xml = xml + "<flag>success</flag>";
					connection.commit();
					xml = xml
							+ "<id>"
							+ (cmbFormatNo + txtBudgetGroupMajor)
							+ "</id>";
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
		} else if (strCommand.equalsIgnoreCase("getMinorBudgetHeadDesc")) {
			xml = "<response><command>getMinorBudgetHeadDesc</command>";
			String txtBudgetDescMain = request
					.getParameter("txtBudgetDescMain");
			try {
				ps = connection
						.prepareStatement("select MINOR_HEAD_CODE,MINOR_HEAD_DESC from COM_MST_MINOR_HEADS where MAJOR_HEAD_CODE=? order by MINOR_HEAD_DESC");
				ps.setString(1, txtBudgetDescMain);
				rs2 = ps.executeQuery();
				while (rs2.next()) {
					xml = xml + "<BudgetIdSub>" + rs2.getInt("MINOR_HEAD_CODE")
							+ "</BudgetIdSub>";
					xml = xml + "<BudgetDescSub>"
							+ rs2.getString("MINOR_HEAD_DESC")
							+ "</BudgetDescSub>";
				}
				xml = xml + "<flag3>success</flag3>";
			} catch (Exception e) {
				xml = xml + "<flag3>failure</flag3>";
				e.printStackTrace();
			}
		} else if (strCommand.equalsIgnoreCase("LoadData")) {
			xml = "<response><command>LoadData</command>";
			String txtFormatNo = request.getParameter("txtFormatNo");
			String txtBudgetIdMain = request.getParameter("txtBudgetIdMain");
			int txtBudgetIdSub = Integer.parseInt(request
					.getParameter("txtBudgetIdSub"));
			int txtBudgetGroupMajor = Integer.parseInt(request
					.getParameter("txtBudgetGroupMajor"));

			try {
				ps = connection
						.prepareStatement("select FORMAT_NO,BUDGET_GROUP_MAJOR,CAN_EXCEED_BUDGET,CAN_BE_RE_APPROPRIATED,CAN_BE_RATIFIED from FAS_BUDGET_HEADS_MASTER where FORMAT_NO=? and BUDGET_GROUP_MAJOR=?");
				ps.setString(1, txtFormatNo);
				ps.setInt(2, txtBudgetGroupMajor);
				rs2 = ps.executeQuery();
				if (rs2.next()) {
					xml = xml + "<cmbFormatNo>"+rs2.getString("FORMAT_NO")+"</cmbFormatNo>";
					xml = xml + "<BudgetIdSub>"+rs2.getInt("BUDGET_GROUP_MAJOR")+ "</BudgetIdSub>";
					xml = xml + "<CAN_EXCEED_BUDGET>"+ rs2.getString("CAN_EXCEED_BUDGET")+ "</CAN_EXCEED_BUDGET>";
					xml = xml + "<CAN_BE_RE_APPROPRIATED>" + rs2.getString("CAN_BE_RE_APPROPRIATED")+ "</CAN_BE_RE_APPROPRIATED>";
					xml = xml + "<CAN_BE_RATIFIED>" + rs2.getString("CAN_BE_RATIFIED")+ "</CAN_BE_RATIFIED>";
					xml = xml + "<flag3>success</flag3>";
				}else{
					xml = xml + "<flag3>fail</flag3>";
				}
				
				xml = xml + "<txtBudgetDescMain>" + txtBudgetIdMain
						+ "</txtBudgetDescMain>";
				xml = xml + "<txtBudgetDescSub>" + txtBudgetIdSub
						+ "</txtBudgetDescSub>";				
				
			} catch (Exception e) {
				xml = xml + "<flag3>failure</flag3>";
				e.printStackTrace();
			}
		}
		/*else if (strCommand.equalsIgnoreCase("loadmajorgroup")) {
			xml = "<response><command>LoadData</command>";
			String txtFormatNo = request.getParameter("txtFormatNo");
			String txtBudgetIdMain = request.getParameter("txtBudgetIdMain");
			try {
				ps = connection
						.prepareStatement("select BUDGET_GROUP_ID,BUDGET_GROUP_MAJOR from FAS_BUDGET_GROUP_MASTER order by BUDGET_GROUP_ID");
				ps.setString(1, txtFormatNo);
				rs2 = ps.executeQuery();
				while (rs2.next()) {
					xml = xml + "<BUDGET_GROUP_ID>" + rs2.getInt("BUDGET_GROUP_ID")
							+ "</BUDGET_GROUP_ID>";
					xml = xml + "<BUDGET_GROUP_MAJOR>"
							+ rs2.getString("BUDGET_GROUP_MAJOR")
							+ "</BUDGET_GROUP_MAJOR>";
				}				
				xml = xml + "<flag3>success</flag3>";
			} catch (Exception e) {
				xml = xml + "<flag3>failure</flag3>";
				e.printStackTrace();
			}
		}*/
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}

}
