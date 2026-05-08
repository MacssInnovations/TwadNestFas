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
public class EXP_Major_Group_Acc_Head_Mapping extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public EXP_Major_Group_Acc_Head_Mapping() {
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
		if (strCommand.equalsIgnoreCase("FirstLoad2")) {
			xml = "<response>";
			xml=xml+"<root>";
			xml= xml + "<command>FirstLoad2</command>";

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
			xml=xml+"</root>";
			
			}

		 if(strCommand.equalsIgnoreCase("Exp")){
			xml = "<response>";
			xml=xml+"<root>";
			xml = xml + "<command>FirstLoad3</command>";
             System.out.println(strCommand);
			try {
				ps = connection
						.prepareStatement("select MAJOR_HEAD_CODE,MAJOR_HEAD_DESC from COM_MST_MAJOR_HEADS where MAJOR_HEAD_CODE='L'");
				rs2 = ps.executeQuery();
				while (rs2.next()) {
					System.out.println(rs2.getString("MAJOR_HEAD_CODE"));
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
			xml=xml+"</root>";
		 }else if(strCommand.equalsIgnoreCase("secondLoad"))
		 {
		 xml = "<response>";
			 xml=xml+"<root>";
			 String budgetGroup = request.getParameter("budgetGroup");
			 int count=0;
			try {				
				
				String sql="";
				System.out.println("next.....");
			
					//strBuffer = new StringBuilder();
					
					//sql = "select MAJOR_HEAD_CODE,GROUP_HEAD_CODE,MINOR_HEAD_CODE,ACCOUNT_HEAD_CODE from FAS_MIS_GROUP_ACC_HD_MAPPING where MAJOR_HEAD_CODE='"+budgetGroup+"' order by GROUP_HEAD_CODE";
				sql="SELECT A.MAJOR_HEAD_CODE,A.GROUP_HEAD_CODE,A.MINOR_HEAD_CODE,A.ACCOUNT_HEAD_CODE,B.MAJOR_HEAD_DESC,C.GROUP_HEAD_DESC,D.SUB_HEAD_DESC,E.ACCOUNT_HEAD_DESC FROM FAS_MIS_EXP_GROUP_ACC_HD_MAP A INNER JOIN COM_MST_MAJOR_HEADS B ON B.MAJOR_HEAD_CODE=A.MAJOR_HEAD_CODE INNER JOIN FAS_MIS_GROUP_MASTER C ON C.GROUP_HEAD_CODE=A.GROUP_HEAD_CODE INNER JOIN COM_MST_SUB_HEADS D ON D.SUB_HEAD_CODE =A.MINOR_HEAD_CODE INNER JOIN COM_MST_ACCOUNT_HEADS E ON E.ACCOUNT_HEAD_CODE=A.ACCOUNT_HEAD_CODE WHERE A.MAJOR_HEAD_CODE='"+budgetGroup+"' AND C.TYPE='EXP'";
				//sql = strBuffer.toString();
				ps1 = connection.prepareStatement(sql);
				rs = ps1.executeQuery();
				while (rs.next()) {
					
			     xml = xml + "<BudgetGroupMajor>"
					      + rs.getString("MAJOR_HEAD_CODE")
					      + "</BudgetGroupMajor>";
			     xml = xml + "<Group_Head_Code>"
					  + rs.getInt("GROUP_HEAD_CODE")
					    + "</Group_Head_Code>";
			     xml = xml + "<BudgetGroupMinor>"
					 + rs.getInt("MINOR_HEAD_CODE")
					+ "</BudgetGroupMinor>";
			     xml = xml + "<Acc_HeadCode>"
					  + rs.getInt("ACCOUNT_HEAD_CODE")
					     + "</Acc_HeadCode>";

					xml= xml+"<MAJOR_HEAD_DESC><![CDATA["
							+rs.getString("MAJOR_HEAD_DESC")
							+"]]></MAJOR_HEAD_DESC>";
					xml= xml+"<GROUP_HEAD_DESC><![CDATA["
							+rs.getString("GROUP_HEAD_DESC")
							+"]]></GROUP_HEAD_DESC>";
					xml= xml+"<MINOR_HEAD_DESC><![CDATA["
							+rs.getString("SUB_HEAD_DESC")
							+"]]></MINOR_HEAD_DESC>";
					if(rs.getString("ACCOUNT_HEAD_DESC")!=null){
						xml=xml+"<ACCOUNT_HEAD_DESC><![CDATA["
								+rs.getString("ACCOUNT_HEAD_DESC")
								+"]]></ACCOUNT_HEAD_DESC>";
					}else{
						xml=xml+"<ACCOUNT_HEAD_DESC>"+" "+"</ACCOUNT_HEAD_DESC>";
					}
					count++;

				}
				xml = xml + "<count>"+count+"</count>";
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			xml=xml+"</root>";
			
		}else if (strCommand.equalsIgnoreCase("getGroupMinorHead")) {
			
			
			
			System.out.println(strCommand);
			xml = "<response>";
			xml = xml + "<root>";
			xml = xml+"<command>getMinorBudgetHeadDesc</command>";
			//String cmbBudgetGroupMajor = request.getParameter("cmbBudgetGroupMajor");
			try {
				ps = connection
						.prepareStatement("select SUB_HEAD_CODE,SUB_HEAD_DESC from COM_MST_SUB_HEADS order by SUB_HEAD_DESC");
				//ps.setString(1, cmbBudgetGroupMajor);
				rs2 = ps.executeQuery();
				while (rs2.next()) {
					xml = xml + "<BudgetIdSub1>" 
					          + rs2.getInt("SUB_HEAD_CODE")
							  + "</BudgetIdSub1>";
					xml = xml + "<BudgetDescSub1><![CDATA["
							  + rs2.getString("SUB_HEAD_DESC")
							  + "]]></BudgetDescSub1>";
				}
				xml = xml + "<flagfirst>success</flagfirst>";
			} catch (Exception e) {
				xml = xml + "<flagfirst>failure</flagfirst>";
				e.printStackTrace();
			}
			
			try {
				ps1 = connection
						.prepareStatement("select GROUP_HEAD_CODE,GROUP_HEAD_DESC from FAS_MIS_GROUP_MASTER where TYPE='EXP' order by MAJOR_HEAD_CODE");
				//ps1.setString(1, cmbBudgetGroupMajor);
				rs = ps1.executeQuery();
				while (rs.next()) {
					xml = xml + "<cmbGroup_Head_Code1>"
							+ rs.getInt("GROUP_HEAD_CODE")
							+ "</cmbGroup_Head_Code1>";
					xml = xml + "<cmbGroup_Head_Desc1><![CDATA["
							+ rs.getString("GROUP_HEAD_DESC")
							+ "]]></cmbGroup_Head_Desc1>";
				}
				xml = xml + "<flag4>success</flag4>";
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag4>failure</flag4>";
			}
			xml = xml + "</root>";
			
		
		} else if (strCommand.equalsIgnoreCase("LoadAccCode")) {
			xml = "<response>"; 
			xml=xml+"<command>LoadAccHdCode</command>";

			String cmbBudgetDescMain = request
					.getParameter("cmbBudgetDescMain");
			int cmbBudgetDescSub = Integer.parseInt(request
					.getParameter("cmbBudgetDescSub"));
			try {
				ps = connection
						.prepareStatement("select ACCOUNT_HEAD_CODE from COM_MST_ACCOUNT_HEADS where MAJOR_HEAD_CODE='L'");
				/*ps.setString(1, cmbBudgetDescMain);
				ps.setInt(2, cmbBudgetDescSub);*/
				rs2 = ps.executeQuery();
				while (rs2.next()) {
					xml = xml + "<AccHdCode>" + rs2.getInt("ACCOUNT_HEAD_CODE")
							+ "</AccHdCode>";

				}
				xml = xml + "<flag3>success</flag3>";
			} catch (Exception e) {
				xml = xml + "<flag3>failure</flag3>";
				e.printStackTrace();
			}
		}else if (strCommand.equalsIgnoreCase("add")) 
		{
			xml = "<response>";
			System.out.println("THis is add method of the java");
			 xml = xml+ "<command>add</command>";
               int count=0;
			String cmbBudgetGroupMajor = request
					.getParameter("cmbBudgetGroupMajor");
			int cmbGroup_Head_Code = Integer.parseInt(request
					.getParameter("cmbGroup_Head_Code"));
			int cmbBudgetGroupMinor = Integer.parseInt(request
					.getParameter("cmbBudgetGroupMinor"));
			int txtAcc_HeadCode = Integer.parseInt(request
					.getParameter("txtAcc_HeadCode"));
			int levelno=Integer.parseInt(request
					.getParameter("levelno"));

			try {
				ps1 = connection
						.prepareStatement("select ACCOUNT_HEAD_CODE from FAS_MIS_EXP_GROUP_ACC_HD_MAP where MAJOR_HEAD_CODE=? and GROUP_HEAD_CODE=? and MINOR_HEAD_CODE=? and ACCOUNT_HEAD_CODE=?");
				ps1.setString(1, cmbBudgetGroupMajor);
				ps1.setInt(2, cmbGroup_Head_Code);
				ps1.setInt(3, cmbBudgetGroupMinor);
				ps1.setInt(4, txtAcc_HeadCode);
				rs = ps1.executeQuery();
				if (rs.next()) {
					xml = xml + "<flagA>Exist</flagA>";
				} else {
					ps = connection
							.prepareStatement("insert into FAS_MIS_EXP_GROUP_ACC_HD_MAP(MAJOR_HEAD_CODE,GROUP_HEAD_CODE,MINOR_HEAD_CODE,ACCOUNT_HEAD_CODE,STATUS,UPDATED_BY_USERID,UPDATED_DATE,LEVEL_NO) values(?,?,?,?,?,?,?,?)");
					ps.setString(1, cmbBudgetGroupMajor);
					ps.setInt(2, cmbGroup_Head_Code);
					ps.setInt(3, cmbBudgetGroupMinor);
					ps.setInt(4, txtAcc_HeadCode);
					ps.setString(5, "L");
					ps.setString(6, userid);
					ps.setTimestamp(7, ts);
					ps.setInt(8, levelno);
					//ps.executeUpdate();

					/*xml = xml + "<BudgetGroupMajor>" + cmbBudgetGroupMajor
							+ "</BudgetGroupMajor>";
					xml = xml + "<Group_Head_Code>" + cmbGroup_Head_Code
							+ "</Group_Head_Code>";
					xml = xml + "<BudgetGroupMinor>" + cmbBudgetGroupMinor
							+ "</BudgetGroupMinor>";
					xml = xml + "<Acc_HeadCode>" + txtAcc_HeadCode
							+ "</Acc_HeadCode>";*/
					count++;
					
				if(ps.executeUpdate()>0){
					
					String qry="SELECT A.MAJOR_HEAD_CODE,A.GROUP_HEAD_CODE,A.MINOR_HEAD_CODE,A.ACCOUNT_HEAD_CODE, B.MAJOR_HEAD_DESC,C.GROUP_HEAD_DESC,D.SUB_HEAD_DESC,E.ACCOUNT_HEAD_DESC FROM FAS_MIS_EXP_GROUP_ACC_HD_MAP A INNER JOIN COM_MST_MAJOR_HEADS B ON B.MAJOR_HEAD_CODE=A.MAJOR_HEAD_CODE INNER JOIN FAS_MIS_GROUP_MASTER C ON C.GROUP_HEAD_CODE=A.GROUP_HEAD_CODE INNER JOIN COM_MST_SUB_HEADS D ON D.SUB_HEAD_CODE=A.MINOR_HEAD_CODE INNER JOIN COM_MST_ACCOUNT_HEADS E ON E.ACCOUNT_HEAD_CODE=A.ACCOUNT_HEAD_CODE WHERE A.MAJOR_HEAD_CODE='"+cmbBudgetGroupMajor+"' AND A.GROUP_HEAD_CODE="+cmbGroup_Head_Code+" AND A.MINOR_HEAD_CODE="+cmbBudgetGroupMinor+" AND A.ACCOUNT_HEAD_CODE="+txtAcc_HeadCode+"";
 					ps = connection.prepareStatement(qry);
 					System.out.println(qry);
 					rs=ps.executeQuery();
 					if(rs.next())
 					{
 				 xml = xml + "<BudgetGroupMajor>"
					      + rs.getString("MAJOR_HEAD_CODE")
					      + "</BudgetGroupMajor>";
			     xml = xml + "<Group_Head_Code>"
					  + rs.getInt("GROUP_HEAD_CODE")
					    + "</Group_Head_Code>";
			     xml = xml + "<BudgetGroupMinor>"
					 + rs.getInt("MINOR_HEAD_CODE")
					+ "</BudgetGroupMinor>";
			     xml = xml + "<Acc_HeadCode>"
					  + rs.getInt("ACCOUNT_HEAD_CODE")
					     + "</Acc_HeadCode>";
			     
 				xml= xml+"<MAJOR_HEAD_DESC><![CDATA["
						+rs.getString("MAJOR_HEAD_DESC")
						+"]]></MAJOR_HEAD_DESC>";
				xml= xml+"<GROUP_HEAD_DESC><![CDATA["
						+rs.getString("GROUP_HEAD_DESC")
						+"]]></GROUP_HEAD_DESC>";
				xml= xml+"<MINOR_HEAD_DESC><![CDATA["
						+rs.getString("SUB_HEAD_DESC")
						+"]]></MINOR_HEAD_DESC>";
				if(rs.getString("ACCOUNT_HEAD_DESC")!=null){
					xml=xml+"<ACCOUNT_HEAD_DESC><![CDATA["
							+rs.getString("ACCOUNT_HEAD_DESC")
							+"]]></ACCOUNT_HEAD_DESC>";
				}
				else{
					xml=xml+"<ACCOUNT_HEAD_DESC>"+" "+"</ACCOUNT_HEAD_DESC>";
				}
 				}
			 			
				xml = xml + "<flagA>success</flagA>";
				
					}
				else{
				xml = xml + "<flagA>failure</flagA>";
					}
			
				}
			} catch (Exception e) {
				xml = xml + "<flagA>failure</flagA>";
				e.printStackTrace();
			}
			
			
		} else if (strCommand.equalsIgnoreCase("LoadGroupHeadCode")) {
			
			xml="<response>";
			
			xml=xml+"<command>LoadGroupHeadCode</command>";
			int count=0;
			String condn = "";
			int cmbGroup_Head_Code = 0;
			try{
				cmbGroup_Head_Code=Integer.parseInt(request.getParameter("cmbGroup_Head_Code"));
			}
			catch (Exception e) {}
			
			if(cmbGroup_Head_Code>0)
				condn += " AND A.GROUP_HEAD_CODE  ="+cmbGroup_Head_Code;
			
			int cmbBudgetGroupMinor = 0;
			try{
				cmbBudgetGroupMinor=Integer.parseInt(request.getParameter("cmbBudgetGroupMinor"));
			}
			catch (Exception e) {}
			
			if(cmbBudgetGroupMinor>0)
				condn += " AND A.MINOR_HEAD_CODE  ="+cmbBudgetGroupMinor;
			int txtAcc_HeadCode = 0; 
			try{
				txtAcc_HeadCode=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
			}
			catch (Exception e) {}
			
			if(txtAcc_HeadCode>0)
				condn += " AND A.ACCOUNT_HEAD_CODE="+txtAcc_HeadCode;
			
			try{
			
			String sql1="SELECT A.MAJOR_HEAD_CODE,A.GROUP_HEAD_CODE,A.MINOR_HEAD_CODE, A.ACCOUNT_HEAD_CODE,B.MAJOR_HEAD_DESC,C.GROUP_HEAD_DESC,D.SUB_HEAD_DESC,E.ACCOUNT_HEAD_DESC FROM FAS_MIS_EXP_GROUP_ACC_HD_MAP A INNER JOIN COM_MST_MAJOR_HEADS B ON B.MAJOR_HEAD_CODE=A.MAJOR_HEAD_CODE INNER JOIN FAS_MIS_GROUP_MASTER C ON C.GROUP_HEAD_CODE=A.GROUP_HEAD_CODE INNER JOIN COM_MST_SUB_HEADS D ON D.SUB_HEAD_CODE=A.MINOR_HEAD_CODE INNER JOIN COM_MST_ACCOUNT_HEADS E ON E.ACCOUNT_HEAD_CODE =A.ACCOUNT_HEAD_CODE WHERE A.MAJOR_HEAD_CODE='L' "+ condn; 
			//sql = strBuffer.toString();
			System.out.println(sql1);
			ps1 = connection.prepareStatement(sql1);
			rs = ps1.executeQuery();
			while (rs.next()) {
				System.out.println("value..............."+rs.getString("MAJOR_HEAD_CODE"));
				
		     xml = xml + "<BudgetGroupMajor>"
				      + rs.getString("MAJOR_HEAD_CODE")
				      + "</BudgetGroupMajor>";
		     xml = xml + "<Group_Head_Code>"
				  + rs.getInt("GROUP_HEAD_CODE")
				    + "</Group_Head_Code>";
		     xml = xml + "<BudgetGroupMinor>"
				 + rs.getInt("MINOR_HEAD_CODE")
				+ "</BudgetGroupMinor>";
		     xml = xml + "<Acc_HeadCode>"
				  + rs.getInt("ACCOUNT_HEAD_CODE")
				     + "</Acc_HeadCode>";
		     
                 if(rs.getString("MAJOR_HEAD_DESC")!=null){
				xml= xml+"<MAJOR_HEAD_DESC><![CDATA["
						+rs.getString("MAJOR_HEAD_DESC")
						+"]]></MAJOR_HEAD_DESC>";
                 }else{
                	 xml=xml+"<MAJOR_HEAD_DESC>"+" "+"</MAJOR_HEAD_DESC>";
                 }
                 if(rs.getString("GROUP_HEAD_DESC")!=null){
				xml= xml+"<GROUP_HEAD_DESC><![CDATA["
						+rs.getString("GROUP_HEAD_DESC")
						+"]]></GROUP_HEAD_DESC>";
                 }else{
                	 xml=xml+"<GROUP_HEAD_DESC>"+" "+"</GROUP_HEAD_DESC>";
                 }
                 if(rs.getString("SUB_HEAD_DESC")!=null){      	 
                     xml= xml+"<MINOR_HEAD_DESC><![CDATA["
						+rs.getString("SUB_HEAD_DESC")
						+"]]></MINOR_HEAD_DESC>";
                 }else{
                	 xml=xml+"<SUB_HEAD_DESC>"+" "+"</SUB_HEAD_DESC>";
                 }
				if(rs.getString("ACCOUNT_HEAD_DESC")!=null){
					xml=xml+"<ACCOUNT_HEAD_DESC><![CDATA["
							+rs.getString("ACCOUNT_HEAD_DESC")
							+"]]></ACCOUNT_HEAD_DESC>";
				}else{
					xml=xml+"<ACCOUNT_HEAD_DESC>"+" "+"</ACCOUNT_HEAD_DESC>";
				}
				count++;
			}  
			xml=xml+"<count>"+count+"</count>";
			xml = xml + "<flagB>success</flagB>";
			}catch(SQLException e){
				e.printStackTrace();
			xml = xml + "<flagB>success</flagB>";
			}
	    }else if (strCommand.equalsIgnoreCase("LoadLib")) 
		{ 
			System.out.println(strCommand);
			xml="<response>";
			xml=xml+"<command>LoadLib</command>";
	     String BudgetGroupMajor = request
			     .getParameter("BudgetGroupMajor");
	     int Group_Head_Code = Integer.parseInt(request
			.getParameter("Group_Head_Code"));
	     int BudgetGroupMinor = Integer.parseInt(request
			.getParameter("BudgetGroupMinor"));
	     int Acc_HeadCode = Integer.parseInt(request
			.getParameter("Acc_HeadCode"));

			xml = xml + "<BudgetGroupMajor>" + BudgetGroupMajor
					+ "</BudgetGroupMajor>";

			try {
				String test="select A.MAJOR_HEAD_CODE,A.GROUP_HEAD_CODE,A.MINOR_HEAD_CODE," +
						"A.ACCOUNT_HEAD_CODE,B.MAJOR_HEAD_DESC,C.GROUP_HEAD_DESC,D.SUB_HEAD_CODE," +
						"d.SUB_HEAD_DESC,E.ACCOUNT_HEAD_DESC FROM FAS_MIS_EXP_GROUP_ACC_HD_MAP A " +
						"INNER JOIN COM_MST_MAJOR_HEADS B ON B.MAJOR_HEAD_CODE=" +
						"A.MAJOR_HEAD_CODE INNER JOIN FAS_MIS_GROUP_MASTER C " +
						"ON C.GROUP_HEAD_CODE=A.GROUP_HEAD_CODE INNER JOIN COM_MST_SUB_HEADS D " +
						"ON D.SUB_HEAD_CODE=A.MINOR_HEAD_CODE INNER JOIN COM_MST_ACCOUNT_HEADS E " +
						"ON E.ACCOUNT_HEAD_CODE=A.ACCOUNT_HEAD_CODE WHERE A.MAJOR_HEAD_CODE= '" +BudgetGroupMajor+"'"+
						"AND A.GROUP_HEAD_CODE="+Group_Head_Code+" AND A.MINOR_HEAD_CODE="+BudgetGroupMinor+" AND A.ACCOUNT_HEAD_CODE="+Acc_HeadCode;
				System.out.println("test::::"+test);
				ps = connection	.prepareStatement(test);
//				ps.setString(1, cmbBudgetGroupMajor);
//				ps.setInt(2, cmbGroup_Head_Code);
//				ps.setInt(3, cmbBudgetGroupMinor);
//				ps.setInt(4, txtAcc_HeadCode);
				rs2 = ps.executeQuery();
				while (rs2.next()) {
			xml = xml + "<Group_Head_Code>" + Group_Head_Code
					+ "</Group_Head_Code>";
			xml = xml + "<Group_Head_Desc>"
					+ rs2.getString("GROUP_HEAD_DESC")
					+ "</Group_Head_Desc>";
			xml = xml + "<BudgetGroupMinor>" + BudgetGroupMinor
					+ "</BudgetGroupMinor>";
			xml = xml + "<BudgetGroupMinorDesc>"
					+ rs2.getString("SUB_HEAD_DESC")
					+ "</BudgetGroupMinorDesc>";
			xml = xml + "<Acc_HeadCode>" + Acc_HeadCode
					+ "</Acc_HeadCode>";
			xml = xml + "<Acc_HeadCodeDesc>"
					+ rs2.getString("ACCOUNT_HEAD_DESC")
					+ "</Acc_HeadCodeDesc>";
				}
				
				xml = xml + "<flag1>success</flag1>";
				
				System.out.println("loop xml:::"+xml);
			} catch (Exception e) {
				xml = xml + "<flag1>failure</flag1>";
				e.printStackTrace();
			}
             
			
             
		} else if (strCommand.equalsIgnoreCase("deleteExp")) {

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
						.prepareStatement("select ACCOUNT_HEAD_CODE from FAS_MIS_EXP_GROUP_ACC_HD_MAP where MAJOR_HEAD_CODE=? and GROUP_HEAD_CODE=? and MINOR_HEAD_CODE=? and ACCOUNT_HEAD_CODE=?");
				ps1.setString(1, cmbBudgetGroupMajor);
				ps1.setInt(2, cmbGroup_Head_Code);
				ps1.setInt(3, cmbBudgetGroupMinor);
				ps1.setInt(4, txtAcc_HeadCode);
				rs = ps1.executeQuery();
				if (rs.next()) {
					ps = connection
							.prepareStatement("delete from FAS_MIS_EXP_GROUP_ACC_HD_MAP where MAJOR_HEAD_CODE=? and GROUP_HEAD_CODE=? and MINOR_HEAD_CODE=? and ACCOUNT_HEAD_CODE=?");
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

