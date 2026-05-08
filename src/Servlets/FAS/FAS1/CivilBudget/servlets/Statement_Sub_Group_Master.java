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
 * Servlet implementation class Statement_Group_Master
 */
public class Statement_Sub_Group_Master extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Statement_Sub_Group_Master() {
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
		System.out.println("sub group");
		System.out.println("http version::::"+request.getProtocol());
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


			strCommand = request.getParameter("command");
		    System.out.println("strCommand"+strCommand);
		

		Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
				.getAttribute("UserProfile");
		int empid = empProfile.getEmployeeId();
		String empName = empProfile.getEmployeeName();
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
		if (strCommand.equalsIgnoreCase("add")) {
			xml = "<response><command>add</command>";
			StringBuffer buf = new StringBuffer();
			String sql = "",ins="";
			int max = 1;
			int count = 0;
			String statementNo = request.getParameter("statementNo");
			int statementGroupNo = Integer.parseInt(request.getParameter("statementGroupNo"));
			String statementSubGroupDesc = request.getParameter("statementSubGroupDesc");
			String path = request.getParameter("path");
			try {
				ps1 = connection.prepareStatement("SELECT STATEMENT_NO, STATEMENT_GROUP_NO FROM FAS_STATEMENT_SUB_GROUP_MASTER where STATEMENT_NO=? and STATEMENT_GROUP_NO=? and STATEMENT_SUB_GROUP_DEC=? ");
				ps1.setString(1, statementNo);
				ps1.setInt(2, statementGroupNo);
				ps1.setString(3, statementSubGroupDesc);
				rs = ps1.executeQuery();
				if (rs.next()) {
					xml = xml + "<flag>Exist</flag>";
				} else {
					sql="select max(STATEMENT_SUB_GROUP_NO) as max from FAS_STATEMENT_SUB_GROUP_MASTER where STATEMENT_NO=? and STATEMENT_GROUP_NO=?";					
					ps2=connection.prepareStatement(sql);
					ps2.setString(1, statementNo);
					ps2.setInt(2, statementGroupNo);
					results2 = ps2.executeQuery();
					if(results2.next()){
						max = results2.getInt("max");
						max++;
					} else {
						max = 1;
					}
					buf.append("INSERT \n");
					buf.append("INTO FAS_STATEMENT_SUB_GROUP_MASTER \n");
					buf.append("  ( \n");
					buf.append("    STATEMENT_NO, \n");
					buf.append("    STATEMENT_GROUP_NO, \n");
					buf.append("    STATEMENT_SUB_GROUP_NO, \n");
					buf.append("    STATEMENT_SUB_GROUP_DEC, \n");
					buf.append("    UPDATED_BY_USERID, \n");
					buf.append("    UPDATED_DATE, \n");
					buf.append("    STATUS \n");
					buf.append("  ) \n");
					buf.append("  VALUES \n");
					buf.append("  ( \n");
					buf.append("    ?, \n");
					buf.append("    ?, \n");
					buf.append("    ?, \n");
					buf.append("    ?, \n");
					buf.append("    ?, \n");
					buf.append("    SYSTIMESTAMP, \n");
					buf.append("    'L' \n");
					buf.append("  )");
					ins = buf.toString();
					ps = connection.prepareStatement(ins);
					ps.setString(1, statementNo);
					ps.setInt(2, statementGroupNo);
					ps.setInt(3, max);
					ps.setString(4, statementSubGroupDesc);
					ps.setString(5, userid);
					count = ps.executeUpdate();
					if(count>0){
						xml = xml + "<flag>success</flag>";
						xml = xml + "<SUBGROUPID>" + max
						+ "</SUBGROUPID>";
					}else{
						xml = xml + "<flag>fail</flag>";
					}
					xml = xml + "<path>"+path+"</path>";
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("getGrid")) {
			xml = "<response><command>getGrid</command>";
			String path = request.getParameter("path");
			try {				
				StringBuffer strBuf=new StringBuffer();
				strBuf.append("SELECT c.STATEMENT_NO AS STATEMENT_NO, \n");
				strBuf.append("  c.STATEMENT_DESC AS STATEMENT_DESC, \n");
				strBuf.append("  b.STATEMENT_GROUP_NO AS STATEMENT_GROUP_NO, \n");
				strBuf.append("  b.STATEMENT_GROUP_DESC AS STATEMENT_GROUP_DESC, \n");
				strBuf.append("  a.STATEMENT_SUB_GROUP_NO AS STATEMENT_SUB_GROUP_NO, \n");
				strBuf.append("  a.STATEMENT_SUB_GROUP_DEC AS STATEMENT_SUB_GROUP_DEC, \n");
				strBuf.append("  a.STATUS AS STATUS \n");
				strBuf.append("FROM \n");
				strBuf.append("  (SELECT STATEMENT_NO, \n");
				strBuf.append("    STATEMENT_GROUP_NO, \n");
				strBuf.append("    STATEMENT_SUB_GROUP_NO, \n");
				strBuf.append("    STATEMENT_SUB_GROUP_DEC, \n");
				strBuf.append("    STATUS \n");
				strBuf.append("  FROM FAS_STATEMENT_SUB_GROUP_MASTER \n");
				strBuf.append("  )a \n");
				strBuf.append("LEFT OUTER JOIN \n");
				strBuf.append("  (SELECT STATEMENT_NAME, \n");
				strBuf.append("    STATEMENT_GROUP_NO, \n");
				strBuf.append("    STATEMENT_GROUP_DESC \n");
				strBuf.append("  FROM FAS_STATEMENT_GROUP_MASTER \n");
				strBuf.append("  )b \n");
				strBuf.append("ON a.STATEMENT_NO       =b.STATEMENT_NAME \n");
				strBuf.append("AND a.STATEMENT_GROUP_NO=b.STATEMENT_GROUP_NO \n");
				strBuf.append("LEFT OUTER JOIN \n");
				strBuf.append("  ( SELECT STATEMENT_NO, STATEMENT_DESC FROM FAS_STATEMENT_MASTER \n");
				strBuf.append("  )c \n");
				strBuf.append("ON a.STATEMENT_NO=c.STATEMENT_NO \n");
				strBuf.append("ORDER BY a.STATEMENT_NO, \n");
				strBuf.append("  b.STATEMENT_GROUP_NO");
				String sql=strBuf.toString();
				ps1 = connection.prepareStatement(sql);
				rs = ps1.executeQuery();
				while (rs.next()) {
					xml = xml + "<StatementNo>"
					+ rs.getString("STATEMENT_NO")
					+ "</StatementNo>";
					xml=xml+"<cmbStatementName><![CDATA["+rs.getString("STATEMENT_DESC")+"]]></cmbStatementName>";
					xml = xml + "<txtStatementGroupNo>"
					+ rs.getInt("STATEMENT_GROUP_NO")
					+ "</txtStatementGroupNo>";
					xml=xml+"<txtStatementGroupDesc><![CDATA["+rs.getString("STATEMENT_GROUP_DESC")+"]]></txtStatementGroupDesc>";
					xml = xml + "<StatementSubGroupNo>"
					+ rs.getInt("STATEMENT_SUB_GROUP_NO")
					+ "</StatementSubGroupNo>";
					xml=xml+"<StatementSubGroupDesc><![CDATA["+rs.getString("STATEMENT_SUB_GROUP_DEC")+"]]></StatementSubGroupDesc>";
					xml = xml + "<STATUS>"+ rs.getString("STATUS")+ "</STATUS>";
				}

				

				xml = xml + "<path>"+path+"</path><flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("statementname")) 
		{
			xml = "<response><command>secondload</command>";
			String path = request.getParameter("path");
			try
			{
			ps = connection.prepareStatement("select STATEMENT_NO,STATEMENT_DESC from FAS_STATEMENT_MASTER order by STATEMENT_NO");
			results = ps.executeQuery();
			while (results.next()) {
				xml = xml + "<STATEMENT_NO>"
						+ results.getString("STATEMENT_NO")
						+ "</STATEMENT_NO>";
				xml=xml+"<STATEMENT_DESC><![CDATA["+results.getString("STATEMENT_DESC")+"]]></STATEMENT_DESC>";
				
			}
			xml = xml + "<path>"+path+"</path><flag>success</flag>";
			}catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		}
		else if (strCommand.equalsIgnoreCase("secondload")) 
		{
			xml = "<response><command>secondload</command>";
			String path = request.getParameter("path");
			
			String condn = "";
			String cmbStatementName=request.getParameter("cmbStatementName");
			System.out.println("cmbStatementName"+cmbStatementName);
			if(cmbStatementName!=null)
			{
				condn+="WHERE a.STATEMENT_NO ='"+cmbStatementName+"'";
			}
			int statementGroupName=0;
			try 
			{
				statementGroupName=Integer.parseInt(request.getParameter("statementGroupName"));
				System.out.println("statementGroupName"+statementGroupName);
			}catch(Exception e){}
			if(statementGroupName>0)
			{
				condn+="AND a.STATEMENT_GROUP_NO ="+statementGroupName;
			}
			try
			{
				String sqy="SELECT a.STATEMENT_NO,(select c.STATEMENT_DESC from FAS_STATEMENT_MASTER c where c.STATEMENT_NO=a.STATEMENT_NO) as STATEMENT_DESC,   a.STATEMENT_GROUP_NO,   b.STATEMENT_GROUP_DESC,  a.STATEMENT_SUB_GROUP_NO,   a.STATEMENT_SUB_GROUP_DEC,a.STATUS FROM FAS_STATEMENT_SUB_GROUP_MASTER a inner join FAS_STATEMENT_GROUP_MASTER b on b.STATEMENT_NAME=a.STATEMENT_NO and b.STATEMENT_GROUP_NO= a.STATEMENT_GROUP_NO "+condn;
				ps1 = connection.prepareStatement(sqy);
				rs = ps1.executeQuery();
				while (rs.next()) {
					xml = xml + "<StatementNo>"
					+ rs.getString("STATEMENT_NO")
					+ "</StatementNo>";
					xml=xml+"<cmbStatementName><![CDATA["+rs.getString("STATEMENT_DESC")+"]]></cmbStatementName>";
					xml = xml + "<txtStatementGroupNo>"
					+ rs.getInt("STATEMENT_GROUP_NO")
					+ "</txtStatementGroupNo>";
					xml=xml+"<txtStatementGroupDesc><![CDATA["+rs.getString("STATEMENT_GROUP_DESC")+"]]></txtStatementGroupDesc>";
					xml = xml + "<StatementSubGroupNo>"
					+ rs.getInt("STATEMENT_SUB_GROUP_NO")
					+ "</StatementSubGroupNo>";
					xml=xml+"<StatementSubGroupDesc><![CDATA["+rs.getString("STATEMENT_SUB_GROUP_DEC")+"]]></StatementSubGroupDesc>";
					xml = xml + "<STATUS>"+ rs.getString("STATUS")+ "</STATUS>";
					xml=xml+"<STATEMENT_DESC><![CDATA["+rs.getString("STATEMENT_DESC")+"]]></STATEMENT_DESC>";
				}
				xml = xml + "<path>"+path+"</path>";
				xml = xml + "<flag>success</flag>";
			}catch(Exception e)
			{
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		}
		else if (strCommand.equalsIgnoreCase("update")) {
			xml = "<response><command>update</command>";

			String statementNo = request.getParameter("statementNo");
			int statementGroupNo = Integer.parseInt(request.getParameter("statementGroupNo"));
			int statementSubGroupNo = Integer.parseInt(request.getParameter("statementSubGroupNo"));
			String statementSubGroupDesc = request.getParameter("statementSubGroupDesc");
			String path = request.getParameter("path");
			int count = 0;

			try {
				ps1 = connection.prepareStatement("SELECT * FROM FAS_STATEMENT_SUB_GROUP_MASTER where STATEMENT_NO=? and STATEMENT_GROUP_NO=? and STATEMENT_SUB_GROUP_NO=? and STATEMENT_SUB_GROUP_DEC=? ");
				ps1.setString(1, statementNo);
				ps1.setInt(2, statementGroupNo);
				ps1.setInt(3, statementSubGroupNo);
				ps1.setString(4, statementSubGroupDesc);
				rs = ps1.executeQuery();
				if(rs.next()){
					xml = xml + "<flag>Exist</flag>";
				}else{
					ps = connection.prepareStatement("update FAS_STATEMENT_SUB_GROUP_MASTER set STATEMENT_SUB_GROUP_DEC=?,UPDATED_BY_USERID=?,UPDATED_DATE=SYSTIMESTAMP where STATEMENT_NO=? and STATEMENT_GROUP_NO=? and STATEMENT_SUB_GROUP_NO=? ");
					ps.setString(1, statementSubGroupDesc);
					ps.setString(2, userid);
					ps.setString(3, statementNo);
					ps.setInt(4, statementGroupNo);
					ps.setInt(5, statementSubGroupNo);
					count = ps.executeUpdate();
				}
				if(count>0){
					xml = xml + "<flag>success</flag>";
					xml = xml + "<SUBGROUPID>" + statementSubGroupNo
					+ "</SUBGROUPID>";
				}else{
					xml = xml + "<flag>fail</flag>";
				}
				xml = xml + "<path>"+path+"</path>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();

			}
		} else if (strCommand.equalsIgnoreCase("deleted")) {
			xml = "<response><command>update</command>";
			
			String statementNo = request.getParameter("cmbStatementName");
			int statementGroupNo = Integer.parseInt(request.getParameter("txtStatementGroupNo"));
			int statementSubGroupNo = Integer.parseInt(request.getParameter("statementSubGroupNo"));
			String path = request.getParameter("path");
			int count=0;
			try {				
				ps = connection.prepareStatement("update FAS_STATEMENT_SUB_GROUP_MASTER set STATUS=?,UPDATED_BY_USERID=?,UPDATED_DATE=SYSTIMESTAMP where STATEMENT_NO=? and STATEMENT_GROUP_NO=? and STATEMENT_SUB_GROUP_NO=? ");
				ps.setString(1, "C");
				ps.setString(2, userid);
				ps.setString(3, statementNo);
				ps.setInt(4, statementGroupNo);
				ps.setInt(5, statementSubGroupNo);
				count = ps.executeUpdate();
				if(count>0){
					xml = xml + "<flag>success</flag>";
					xml = xml + "<SUBGROUPID>" + statementSubGroupNo
					+ "</SUBGROUPID>";
				}else{
					xml = xml + "<flag>fail</flag>";
				}
				xml = xml + "<path>"+path+"</path>";
				
			} catch (Exception e) {				
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		} else if (strCommand.equalsIgnoreCase("loadStatementName")){			
			xml = "<response><command>loadStatementName</command>";			
			String statementNo = request.getParameter("statementNo");
			int count = 0;
			try {
				ps1 = connection.prepareStatement("SELECT STATEMENT_GROUP_NO,STATEMENT_GROUP_DESC FROM FAS_STATEMENT_GROUP_MASTER WHERE STATEMENT_NAME=?");
				ps1.setString(1, statementNo);
				rs = ps1.executeQuery();
				while(rs.next()){
					xml = xml+"<STATEMENT_GROUP_NO>"+rs.getInt("STATEMENT_GROUP_NO")+"</STATEMENT_GROUP_NO>";
					xml=xml+"<STATEMENT_GROUP_DESC><![CDATA["+rs.getString("STATEMENT_GROUP_DESC")+"]]></STATEMENT_GROUP_DESC>";
					count++;
				}
				if(count>0){
					xml = xml+"<flag>success</flag>";
				}else{
					xml = xml+"<flag>nodata</flag>";
				}
				
			} catch (SQLException e) {				
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		}else if (strCommand.equalsIgnoreCase("edit")){			
			xml = "<response><command>edit</command>";			
			String statementNo = request.getParameter("statementNo");
			int statementGroupNo = Integer.parseInt(request.getParameter("statementGroupNo"));
			int statementSubGroupNo = Integer.parseInt(request.getParameter("statementSubGroupNo"));
			try {
				ps1 = connection.prepareStatement("SELECT a.STATEMENT_NO,  a.STATEMENT_GROUP_NO,  b.STATEMENT_GROUP_DESC,  a.STATEMENT_SUB_GROUP_NO,  a.STATEMENT_SUB_GROUP_DEC FROM FAS_STATEMENT_SUB_GROUP_MASTER a inner join FAS_STATEMENT_GROUP_MASTER b on b.STATEMENT_NAME=a.STATEMENT_NO and b.STATEMENT_GROUP_NO= a.STATEMENT_GROUP_NO WHERE a.STATEMENT_NO  =? AND a.STATEMENT_GROUP_NO =? AND a.STATEMENT_SUB_GROUP_NO=?");
				ps1.setString(1, statementNo);
				ps1.setInt(2, statementGroupNo);
				ps1.setInt(3, statementSubGroupNo);
				rs = ps1.executeQuery();
				if(rs.next()){
					xml = xml+"<status>success</status>";
					xml = xml+"<STATEMENT_NO>"+rs.getString("STATEMENT_NO")+"</STATEMENT_NO>";
					xml = xml+"<STATEMENT_GROUP_NO>"+rs.getInt("STATEMENT_GROUP_NO")+"</STATEMENT_GROUP_NO>";
					xml = xml+"<STATEMENT_SUB_GROUP_NO>"+rs.getInt("STATEMENT_SUB_GROUP_NO")+"</STATEMENT_SUB_GROUP_NO>";
					xml = xml+"<STATEMENT_SUB_GROUP_DEC><![CDATA["+rs.getString("STATEMENT_SUB_GROUP_DEC")+"]]></STATEMENT_SUB_GROUP_DEC>";
				//}				
				/*ps2 = connection.prepareStatement("SELECT STATEMENT_GROUP_NO,STATEMENT_GROUP_DESC FROM FAS_STATEMENT_GROUP_MASTER where STATEMENT_NAME=?");
				ps2.setString(1, statementNo);
				results=ps2.executeQuery();
				while(results.next()){*/
					xml = xml+"<STATEMENT_GROUP_NO>"+rs.getInt("STATEMENT_GROUP_NO")+"</STATEMENT_GROUP_NO>";
					xml=xml+"<STATEMENT_GROUP_DESC><![CDATA["+rs.getString("STATEMENT_GROUP_DESC")+"]]></STATEMENT_GROUP_DESC>";
					//count++;
				}
				/*if(count>0){
					xml = xml+"<flag>success</flag>";
				}else{
					xml = xml+"<flag>nodata</flag>";
				}*/
				
			} catch (SQLException e) {				
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		}
		xml = xml + "</response>";
		System.out.println("xml"+xml);
		out.write(xml);
		out.flush();
		out.close();
	}

}
