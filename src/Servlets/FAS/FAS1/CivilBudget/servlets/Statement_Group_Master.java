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
public class Statement_Group_Master extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Statement_Group_Master() {
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
			int max = 1;
			xml = "<response>" ;
			xml = xml+"<command>add</command>";

			String cmbStatementName = request.getParameter("cmbStatementName");			
			String txtStatementGroupDesc = request
					.getParameter("txtStatementGroupDesc");
			try {
				ps1 = connection
						.prepareStatement("select max(STATEMENT_GROUP_NO) as max from FAS_STATEMENT_GROUP_MASTER where STATEMENT_NAME=?");
				ps1.setString(1, cmbStatementName);
				//ps1.setInt(2, txtStatementGroupNo);
				rs = ps1.executeQuery();
				if (rs.next()) {
					max = rs.getInt("max");
					max++; 
				}
				ps = connection
							.prepareStatement("insert into FAS_STATEMENT_GROUP_MASTER(STATEMENT_NAME,STATEMENT_GROUP_NO,STATEMENT_GROUP_DESC,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?)");
				ps.setString(1, cmbStatementName);
				ps.setInt(2, max);
				ps.setString(3, txtStatementGroupDesc);
				ps.setString(4, userid);
				ps.setTimestamp(5, ts);

				if(ps.executeUpdate()>0)
				{
					String qry="SELECT b.STATEMENT_DESC,a.STATEMENT_NAME,a.STATEMENT_GROUP_NO,a.STATEMENT_GROUP_DESC FROM FAS_STATEMENT_GROUP_MASTER a INNER JOIN FAS_STATEMENT_MASTER b ON b.STATEMENT_NO=a.STATEMENT_NAME and a.STATEMENT_NAME='"+cmbStatementName+"' and a.STATEMENT_GROUP_NO="+max+" and a.STATEMENT_GROUP_DESC='"+txtStatementGroupDesc+"' ORDER BY a.STATEMENT_NAME,  a.STATEMENT_GROUP_NO";
				
					ps = connection.prepareStatement(qry);
 					System.out.println(qry);
 					rs=ps.executeQuery();
 					if(rs.next())
 					{
				
				xml = xml + "<cmbStatementcode>" +
				           rs.getString("STATEMENT_NAME")
							+ "</cmbStatementcode>";
				xml = xml + "<cmbStatementName>"+
				            rs.getString("STATEMENT_DESC")
				           +"</cmbStatementName>";
				xml = xml + "<txtStatementGroupNo>" + 
                           rs.getInt("STATEMENT_GROUP_NO")
							+ "</txtStatementGroupNo>";
				xml = xml + "<txtStatementGroupDesc>"
							+ rs.getString("STATEMENT_GROUP_DESC")
							+ "</txtStatementGroupDesc>";
				}
 					xml = xml + "<flag>success</flag>";
				}
				else{
					xml = xml + "<flag>failure</flag>";
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			xml = xml + "</response>";
			System.out.println("xml:::"+xml);
			out.write(xml);

	}else if (strCommand.equalsIgnoreCase("statementname")) {
		xml = "<response>" ;
		xml = xml+"<command>statementname</command>";
		try
		{
			ps = connection
			.prepareStatement("select STATEMENT_NO,STATEMENT_DESC from FAS_STATEMENT_MASTER order by STATEMENT_NO");
	      results = ps.executeQuery();
	       while (results.next()) {
		   xml = xml + "<STATEMENT_NO>"
				+ results.getString("STATEMENT_NO")
				+ "</STATEMENT_NO>";
		   xml=xml+"<STATEMENT_DESC><![CDATA["+results.getString("STATEMENT_DESC")+"]]></STATEMENT_DESC>";
		
	}
	xml = xml + "<flag>success</flag>";
		}
		catch(Exception e)
		{
			
			xml = xml + "<flag>failure</flag>";
			e.printStackTrace();
		}
		xml = xml + "</response>";
		System.out.println("xml:::"+xml);
		out.write(xml);
	}
	
	
	
	else if (strCommand.equalsIgnoreCase("getGrid")) {
		xml = "<response>";
		xml = xml + "<command>getGrid</command>";

			try {
				ps1 = connection
						.prepareStatement("select a.STATEMENT_NAME,b.STATEMENT_DESC,a.STATEMENT_GROUP_NO,a.STATEMENT_GROUP_DESC from FAS_STATEMENT_GROUP_MASTER a inner join FAS_STATEMENT_MASTER b on b.STATEMENT_NO=a.STATEMENT_NAME order by a.STATEMENT_NAME,a.STATEMENT_GROUP_NO");
				rs = ps1.executeQuery();
				while (rs.next()) {
					xml = xml + "<cmbStatementcode>"+
					           rs.getString("STATEMENT_NAME")
					           +"</cmbStatementcode>";
					xml = xml + "<cmbStatementName>"
						+ rs.getString("STATEMENT_DESC")
						+ "</cmbStatementName>";
					xml = xml + "<txtStatementGroupNo>"
							+ rs.getInt("STATEMENT_GROUP_NO")
							+ "</txtStatementGroupNo>";
					xml=xml+"<txtStatementGroupDesc><![CDATA["+rs.getString("STATEMENT_GROUP_DESC")+"]]></txtStatementGroupDesc>";
				}
				xml = xml + "<flag>success</flag>";
				
				
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			xml = xml + "</response>";
			System.out.println("xml:::"+xml);
			out.write(xml);

		} else if (strCommand.equalsIgnoreCase("secondload")) {
			xml = "<response>";
			xml = xml+"<command>secondload</command>";
			System.out.println("Seond Load::::"+strCommand);
			String cmbStatementName=request.getParameter("cmbStatementName");
			System.out.println("cmbStatementName::::"+cmbStatementName);
			
             
			try {
				ps1 = connection
						.prepareStatement("SELECT a.STATEMENT_NAME,b.STATEMENT_DESC, a.STATEMENT_GROUP_NO,a.STATEMENT_GROUP_DESC FROM FAS_STATEMENT_GROUP_MASTER a inner join FAS_STATEMENT_MASTER b on b.STATEMENT_NO=a.STATEMENT_NAME and a.statement_name='"+cmbStatementName+"' ORDER BY a.STATEMENT_NAME, a.STATEMENT_GROUP_NO");
				rs = ps1.executeQuery();
				while (rs.next()) {
					xml = xml + "<cmbStatementcode>"+
			                  rs.getString("STATEMENT_NAME")
			                  +"</cmbStatementcode>";
					xml = xml + "<cmbStatementName>"
						+ rs.getString("STATEMENT_DESC")
						+ "</cmbStatementName>";
					xml = xml + "<txtStatementGroupNo>"
							+ rs.getInt("STATEMENT_GROUP_NO")
							+ "</txtStatementGroupNo>";
					xml=xml+"<txtStatementGroupDesc><![CDATA["+rs.getString("STATEMENT_GROUP_DESC")+"]]></txtStatementGroupDesc>";
				}
				xml = xml + "<flag>success</flag>";
			}catch(Exception e)
			{
				xml = xml + "<flag>failure</flag>";
			e.printStackTrace();	
			}
			xml = xml + "</response>";
			System.out.println("xml:::"+xml);
			out.write(xml);
		}else if (strCommand.equalsIgnoreCase("EditData")) {
			xml = "<response>" ;
			xml = xml+"<command>EditData</command>";
			int Groupno=0;
			System.out.println("Seond Load::::"+strCommand);
			String cmbStatementcode=request.getParameter("cmbStatementcode");
			System.out.println("cmbStatementName::::"+cmbStatementcode);
			try
			{
			Groupno = Integer.parseInt(request.getParameter("txtStatementGroupNo"));
			
			}
			catch(Exception e)
			{
				e.printStackTrace();
			}
			
             System.out.println("Groupno::"+Groupno);
			try {
				ps1 = connection
						.prepareStatement("SELECT b.STATEMENT_DESC,a.STATEMENT_NAME, a.STATEMENT_GROUP_NO, a.STATEMENT_GROUP_DESC FROM FAS_STATEMENT_GROUP_MASTER a INNER JOIN FAS_STATEMENT_MASTER b ON b.STATEMENT_NO=a.STATEMENT_NAME and a.STATEMENT_NAME='"+cmbStatementcode+"' and a.statement_group_no="+Groupno+"");
				rs = ps1.executeQuery();
				while (rs.next()) {
					xml = xml + "<cmbStatementcode>"+
			                  rs.getString("STATEMENT_NAME")
			                  +"</cmbStatementcode>";
					xml = xml + "<cmbStatementName>"
						+ rs.getString("STATEMENT_DESC")
						+ "</cmbStatementName>";
					xml = xml + "<txtStatementGroupNo>"
							+ rs.getInt("STATEMENT_GROUP_NO")
							+ "</txtStatementGroupNo>";
					xml=xml+"<txtStatementGroupDesc><![CDATA["+rs.getString("STATEMENT_GROUP_DESC")+"]]></txtStatementGroupDesc>";
				}
				xml = xml + "<flag>success</flag>";
				
			}catch(Exception e)
			{
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			xml = xml + "</response>";
			System.out.println("xml:::"+xml);
			out.write(xml);
		}
		else if (strCommand.equalsIgnoreCase("update")) {

			xml = "<response>";
			xml = xml +	"<command>update</command>";

			String cmbStatementName = request.getParameter("cmbStatementName");
			int txtStatementGroupNo = Integer.parseInt(request
					.getParameter("txtStatementGroupNo"));
			String txtStatementGroupDesc = request
					.getParameter("txtStatementGroupDesc");

			try {
				ps1 = connection
						.prepareStatement("select STATEMENT_NAME,STATEMENT_GROUP_NO from FAS_STATEMENT_GROUP_MASTER where STATEMENT_NAME=? and STATEMENT_GROUP_NO=?");
				ps1.setString(1, cmbStatementName);
				ps1.setInt(2, txtStatementGroupNo);
				rs = ps1.executeQuery();
				if (rs.next()) {
					ps = connection
							.prepareStatement("update FAS_STATEMENT_GROUP_MASTER set STATEMENT_GROUP_DESC=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where STATEMENT_NAME=? and STATEMENT_GROUP_NO=?");
					ps.setString(1, txtStatementGroupDesc);
					ps.setString(2, userid);
					ps.setTimestamp(3, ts);
					ps.setString(4, cmbStatementName);
					ps.setInt(5, txtStatementGroupNo);
					ps.executeUpdate();

					xml = xml + "<flag>success</flag>";
					xml = xml + "<cmbStatementName>" + cmbStatementName
							+ "</cmbStatementName>";
					xml = xml + "<txtStatementGroupNo>" + txtStatementGroupNo
							+ "</txtStatementGroupNo>";
					xml = xml + "<txtStatementGroupDesc>"
							+ txtStatementGroupDesc
							+ "</txtStatementGroupDesc>";

					ps.close();
				} else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();

			}
			xml = xml + "</response>";
			System.out.println("xml:::"+xml);
			out.write(xml);
		} else if (strCommand.equalsIgnoreCase("deleted")) {
			try {
				connection.setAutoCommit(false);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			xml = "<response>";
			xml = xml +	"<command>deleted</command>";
			
			String cmbStatementName = request.getParameter("cmbStatementName");
			int txtStatementGroupNo = Integer.parseInt(request
					.getParameter("txtStatementGroupNo"));
			try {
				ps1 = connection
						.prepareStatement("select STATEMENT_NAME,STATEMENT_GROUP_NO from FAS_STATEMENT_GROUP_MASTER where STATEMENT_NAME=? and STATEMENT_GROUP_NO=?");
				ps1.setString(1, cmbStatementName);
				ps1.setInt(2, txtStatementGroupNo);
				rs = ps1.executeQuery();
				if (rs.next()) {
					ps = connection
							.prepareStatement("delete from FAS_STATEMENT_GROUP_MASTER where STATEMENT_NAME=? and STATEMENT_GROUP_NO=?");
					ps.setString(1, cmbStatementName);
					ps.setInt(2, txtStatementGroupNo);
					ps.executeUpdate();

					xml = xml + "<flag>success</flag>";
					connection.commit();
					xml = xml + "<id>" + cmbStatementName + "</id>";
					xml = xml + "<id1>" + txtStatementGroupNo + "</id1>";
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
			xml = xml + "</response>";
			System.out.println("xml:::"+xml);
			out.write(xml);
		}
		
		
	}

}
