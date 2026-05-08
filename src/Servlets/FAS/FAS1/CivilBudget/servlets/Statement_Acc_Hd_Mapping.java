package Servlets.FAS.FAS1.CivilBudget.servlets;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class Statement_Acc_Hd_Mapping
 */
public class Statement_Acc_Hd_Mapping extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Statement_Acc_Hd_Mapping() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection connection = null;


		   try {
		           HttpSession session = request.getSession(false);
		           if (session == null) {
		               System.out.println(request.getContextPath() + "/index.jsp");
		               response.sendRedirect(request.getContextPath() + "/index.jsp");

		           }
		           System.out.println(session);

		       } catch (Exception e) {
		           System.out.println("Redirect Error :" + e);
		       }

		       response.setContentType(CONTENT_TYPE);
		       try {


		           ResourceBundle rs =
		               ResourceBundle.getBundle("Servlets.Security.servlets.Config");
		           String ConnectionString = "";

		           String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
		           String strdsn = rs.getString("Config.DSN");
		           String strhostname = rs.getString("Config.HOST_NAME");
		           String strportno = rs.getString("Config.PORT_NUMBER");
		           String strsid = rs.getString("Config.SID");
		           String strdbusername = rs.getString("Config.USER_NAME");
		           String strdbpassword = rs.getString("Config.PASSWORD");

		           ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

		           Class.forName(strDriver.trim());
		          connection =
		                   DriverManager.getConnection(ConnectionString, strdbusername.trim(),
		                                               strdbpassword.trim());
		       } catch (Exception ex) {
		           String connectMsg =
		               "Could not create the connection" + ex.getMessage() + " " +
		               ex.getLocalizedMessage();
		           System.out.println(connectMsg);
		       }
		      
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


		Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
		.getAttribute("UserProfile");
		int empid = empProfile.getEmployeeId();
		String empName = empProfile.getEmployeeName();
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
		         JasperDesign jasperDesign = null;
		       File reportFile = null;
		       try {
		           System.out.println("++++++++calling report*****");
		           String cmd="";
		         //  String rtype="PDF" ;
		          String fyear="";
		           try{
		         //  fyear=request.getParameter("cmbFinancialYear");
		           cmd=request.getParameter("command");
		             }catch(Exception e){
		            System.out.println("input get from jsp---"+e);
		           
		           }
		             System.out.println("cmmmd  "+cmd);
		            
		             reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/Civil_Statement_ACHead_Mapping.jasper")); 
		           
		         
		         
		           if (!reportFile.exists())
		               throw new JRRuntimeException("File J not found. The report design must be compiled first.");
		           JasperReport jasperReport =(JasperReport)JRLoader.loadObject(reportFile.getPath());

		          Map map = new HashMap();
		           //map.put("finyear",fyear);
		           JasperPrint jasperPrint =JasperFillManager.fillReport(jasperReport, map, connection);


		           //else if (rtype.equalsIgnoreCase("PDF")) {
		           
		            //System.out.println("the option chosen is :::::"+rtype);
		               byte buf[] =
		                   JasperExportManager.exportReportToPdf(jasperPrint);
		               response.setContentType("application/pdf");
		               response.setContentLength(buf.length);
		             
		            //   if(cmd.equalsIgnoreCase("Report")){
		                response.setHeader("Content-Disposition","attachment;filename=\"Civil_Statement_AccountHeadMapping.pdf\"");
		            // }else if(cmd.equalsIgnoreCase("A52UnVerified")){
		                /*  response.setHeader("Content-Disposition","attachment;filename=\"A52_UnVerify.pdf\"");
		             }else if(cmd.equalsIgnoreCase("A52QtyFreezeStatus")){
		             response.setHeader("Content-Disposition","attachment;filename=\"A52QtyFreezeStatus.pdf\"");
		             }*/
		               
		              
		               OutputStream out = response.getOutputStream();
		               out.write(buf, 0, buf.length);
		               //System.out.println("testing***"+jasperPrint);
		               out.close();
		           //}  

		       } catch (Exception ex) {
		           String connectMsg =
		               "Could not create the report " + ex.getMessage() + " " +
		               ex.getLocalizedMessage();
		           System.out.println(connectMsg);
		       }
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

			String cmbStatementName = request.getParameter("cmbStatementName");
			String  statement_group_desc = "";
			String  STATEMENT_SUB_GROUP_DEC = "";
			String statement_desc = "";
			int k=0;
			int txtStatementGroupNo = Integer.parseInt(request
					.getParameter("txtStatementGroupNo"));

			long txtFromAccHdCode = Long.parseLong(request
					.getParameter("txtFromAccHdCode"));

			long txtToAccHdCode = Long.parseLong(request
					.getParameter("txtToAccHdCode"));
			int statementSubGroupNo = Integer.parseInt(request.getParameter("statementSubGroupNo"));
			String accHeadCode = request.getParameter("accHdCode");
			String groupCheck = request.getParameter("groupCheck");
			try {
				ps1 = connection
						.prepareStatement("select STATEMENT_NO,STATEMENT_GROUP_NO,GROUP_TYPE,FROM_ACC_HD_CODE,TO_ACC_HD_CODE from FAS_STATEMENT_ACC_HD_MAPPING where STATEMENT_NO=? and STATEMENT_GROUP_NO=? and FROM_ACC_HD_CODE=? and TO_ACC_HD_CODE=?");
				ps1.setString(1, cmbStatementName);
				ps1.setInt(2, txtStatementGroupNo);
				//ps1.setString(3, groupCheck);
				ps1.setLong(3, txtFromAccHdCode);
				ps1.setLong(4, txtToAccHdCode);
				rs = ps1.executeQuery();
				if (rs.next()) {
					xml = xml + "<flag>Exists</flag>";
					ps=connection.prepareStatement("UPDATE fas_statement_acc_hd_mapping " +
							" SET STATEMENT_GROUP_NO  =? , " +
							"  STATEMENT_SUB_GROUP_NO=? , " +
							"  TO_ACC_HD_CODE        =?, " +
							"  GROUP_TYPE            =? ,UPDATED_BY_USERID=?,UPDATED_DATE=? " +
							" WHERE STATEMENT_NO      =? " +
							" AND FROM_ACC_HD_CODE    =?");
				
					ps.setInt(1, txtStatementGroupNo);
					ps.setInt(2, statementSubGroupNo);
					ps.setLong(3, txtToAccHdCode);
					ps.setString(4, groupCheck);
					ps.setString(5, userid);
					ps.setTimestamp(6, ts);
					ps.setString(7, cmbStatementName);
					ps.setLong(8, txtFromAccHdCode);
					 k=ps.executeUpdate();
					if(k==0)
					{
						xml = xml + "<flag_up>failure</flag_up>";
						
					}else if(k>0){
						xml = xml + "<flag_up>success</flag_up>";
					}
				} else {
					//ps = connection.prepareStatement("insert into FAS_STATEMENT_ACC_HD_MAPPING(STATEMENT_NO,STATEMENT_GROUP_NO,ACC_HD_CODE,GROUP_TYPE,FROM_ACC_HD_CODE,TO_ACC_HD_CODE,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?,?)");
					ps = connection.prepareStatement("insert into FAS_STATEMENT_ACC_HD_MAPPING(STATEMENT_NO,STATEMENT_GROUP_NO,GROUP_TYPE,FROM_ACC_HD_CODE,TO_ACC_HD_CODE,UPDATED_BY_USERID,UPDATED_DATE,STATEMENT_SUB_GROUP_NO) values(?,?,?,?,?,?,?,?)");
					ps.setString(1, cmbStatementName);
					ps.setInt(2, txtStatementGroupNo);
					//ps.setString(3, accHeadCode);
					ps.setString(3, groupCheck);
					ps.setLong(4, txtFromAccHdCode);
					ps.setLong(5, txtToAccHdCode);
					ps.setString(6, userid);
					ps.setTimestamp(7, ts);
					ps.setInt(8, statementSubGroupNo);

					ps.executeUpdate();
					
					ps1 = connection.prepareStatement("select s1.statement_group_desc,s2.STATEMENT_SUB_GROUP_DEC,s3.STATEMENT_DESC from fas_statement_group_master s1,fas_statement_sub_group_master s2,FAS_STATEMENT_MASTER s3 where s1.statement_name = s2.statement_no and s1.statement_group_no = s2.statement_group_no and S2.STATEMENT_NO = s3.statement_no and s1.statement_name = ? and s1.statement_group_no = ?");
					ps1.setString(1, cmbStatementName);
					ps1.setInt(2,txtStatementGroupNo);
					rs2 = ps1.executeQuery();
					while(rs2.next())
					{
						statement_group_desc = rs2.getString("statement_group_desc");
						STATEMENT_SUB_GROUP_DEC = rs2.getString("STATEMENT_SUB_GROUP_DEC");
						statement_desc = rs2.getString("STATEMENT_DESC");
					}
					
					xml = xml + "<flag>success</flag>";
					xml = xml + "<cmbStatementName>" + cmbStatementName
							+ "</cmbStatementName>";
					xml = xml + "<txtStatementGroupNo>" + txtStatementGroupNo
							+ "</txtStatementGroupNo>";
					xml = xml + "<txtFromAccHdCode>" + txtFromAccHdCode
							+ "</txtFromAccHdCode>";
					xml = xml + "<txtToAccHdCode>" + txtToAccHdCode
							+ "</txtToAccHdCode>";
					xml = xml + "<statementSubGroup>" + statementSubGroupNo
					+ "</statementSubGroup>";
					
					xml = xml + "<txtStatementGroupName>" + statement_group_desc
					+ "</txtStatementGroupName>";
					xml = xml + "<statementSubGroupName>" + STATEMENT_SUB_GROUP_DEC
					+ "</statementSubGroupName>";
					xml = xml + "<statementName>" + statement_desc
					+ "</statementName>";
					
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		}else if(strCommand.equalsIgnoreCase("Update")){

			xml = "<response><command>add</command>";

			String cmbStatementName = request.getParameter("cmbStatementName");
			String  statement_group_desc = "";
			String  STATEMENT_SUB_GROUP_DEC = "";
			String statement_desc = "";
			int k=0;
			int txtStatementGroupNo = Integer.parseInt(request
					.getParameter("txtStatementGroupNo"));

			long txtFromAccHdCode = Long.parseLong(request
					.getParameter("txtFromAccHdCode"));

			long txtToAccHdCode = Long.parseLong(request
					.getParameter("txtToAccHdCode"));
			int statementSubGroupNo = Integer.parseInt(request.getParameter("statementSubGroupNo"));
			String accHeadCode = request.getParameter("accHdCode");
			String groupCheck = request.getParameter("groupCheck");
			try {
				ps1 = connection
						.prepareStatement("select STATEMENT_NO,STATEMENT_GROUP_NO,GROUP_TYPE,FROM_ACC_HD_CODE,TO_ACC_HD_CODE from FAS_STATEMENT_ACC_HD_MAPPING where STATEMENT_NO=? and FROM_ACC_HD_CODE=? ");
				ps1.setString(1, cmbStatementName);
			
				ps1.setLong(2, txtFromAccHdCode);
			
				rs = ps1.executeQuery();
				if (rs.next()) {
					xml = xml + "<flag>Exists</flag>";
					ps=connection.prepareStatement("UPDATE fas_statement_acc_hd_mapping " +
							" SET STATEMENT_GROUP_NO  =? , " +
							"  STATEMENT_SUB_GROUP_NO=? , " +
							"  TO_ACC_HD_CODE        =?, " +
							"  GROUP_TYPE            =? ,UPDATED_BY_USERID=?,UPDATED_DATE=? " +
							" WHERE STATEMENT_NO      =? " +
							" AND FROM_ACC_HD_CODE    =?");
				
					ps.setInt(1, txtStatementGroupNo);
					ps.setInt(2, statementSubGroupNo);
					ps.setLong(3, txtToAccHdCode);
					ps.setString(4, groupCheck);
					ps.setString(5, userid);
					ps.setTimestamp(6, ts);
					ps.setString(7, cmbStatementName);
					ps.setLong(8, txtFromAccHdCode);
					 k=ps.executeUpdate();
					if(k==0)
					{
						xml = xml + "<flag_up>failure</flag_up>";
						
					}else if(k>0){
						xml = xml + "<flag_up>success</flag_up>";
					}
				} 
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		
		}
		else if (strCommand.equalsIgnoreCase("statementname")) 
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
		else if (strCommand.equalsIgnoreCase("LoadAccHdDescFrom")) {
			xml = "<response><command>LoadAccHdDescFrom</command>";

			System.out.println("inside load from");
				int txtFromAcc_HeadCode = Integer.parseInt(request
						.getParameter("txtFromAcc_HeadCode"));
				try {
					ps = connection
							.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
				
					ps.setInt(1, txtFromAcc_HeadCode);
					rs2 = ps.executeQuery();
					while (rs2.next()) {
						xml = xml + "<AccHdDesc>"
								+ rs2.getString("ACCOUNT_HEAD_DESC")
								+ "</AccHdDesc>";
						xml = xml + "<flag3>success</flag3>";
						//System.out.println("in txtFroAcc_HeadCode"+xml);
					}
					
					xml = xml + "<flag3>failure</flag3>";
					//System.out.println("in txtfrom2222222Acc_HeadCode"+xml);
					
				} catch (Exception e) {
					xml = xml + "<flag3>failure</flag3>";
					e.printStackTrace();
				}
			}else if (strCommand.equalsIgnoreCase("LoadAccHdDescTo")) {
				xml = "<response><command>LoadAccHdDescTo</command>";
				//System.out.println("inside load to");
			
				int txtToAcc_HeadCode = Integer.parseInt(request
						.getParameter("txtToAcc_HeadCode"));
				try {
					ps = connection
							.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
				
					ps.setInt(1, txtToAcc_HeadCode);
					rs2 = ps.executeQuery();
					while (rs2.next()) {
						xml = xml + "<AccHdDesc>"
								+ rs2.getString("ACCOUNT_HEAD_DESC")
								+ "</AccHdDesc>";
						xml = xml + "<flag3>success</flag3>";
						//System.out.println("in txttoAcc_HeadCode"+xml);
					}
					xml = xml + "<flag3>failure</flag3>";
					//System.out.println("in txttoA2222222222cc_HeadCode"+xml);
				} catch (Exception e) {
					xml = xml + "<flag3>failure</flag3>";
					e.printStackTrace();
				}
			}
		else if (strCommand.equalsIgnoreCase("getGrid")) {
			xml = "<response><command>getGrid</command>";
			String path = request.getParameter("path");
			try {
				//ps1 = connection
				//.prepareStatement("select STATEMENT_NO,STATEMENT_GROUP_NO,FROM_ACC_HD_CODE,TO_ACC_HD_CODE,STATEMENT_SUB_GROUP_NO from FAS_STATEMENT_ACC_HD_MAPPING order by STATEMENT_NO,STATEMENT_GROUP_NO");
				ps1= connection.prepareStatement(" select s1.statement_no,s2.statement_desc,s1.statement_group_no,s3.statement_group_desc,	"+
						"s1.from_acc_hd_code, s1.to_acc_hd_code,	"+
						"s1.statement_sub_group_no,s4.STATEMENT_SUB_GROUP_DEC	"+
						"from fas_statement_acc_hd_mapping s1,	"+
						"fas_statement_master s2,  fas_statement_group_master s3,	"+
						"fas_statement_sub_group_master s4	"+
						"where s1.statement_no = s2.statement_no	"+
						"and s1.statement_no = s3.statement_name	"+
						"and s1.statement_group_no = s3.statement_group_no	"+
						"and s4.statement_no = s1.statement_no	"+
						"and s4.statement_group_no = s1.statement_group_no	"+
						"and s4.statement_sub_group_no = s1.statement_sub_group_no	"+
						"order by S1.STATEMENT_NO,s1.statement_group_no,s1.statement_sub_group_no");
				rs = ps1.executeQuery();
				while (rs.next()) {
					
					xml = xml + "<cmbStatementName>"
							+ rs.getString("STATEMENT_NO") + "</cmbStatementName>";
					xml = xml + "<cmbStatementdesc>"
					+ rs.getString("statement_desc") + "</cmbStatementdesc>";
					xml = xml + "<txtStatementGroupNo>"
							+ rs.getInt("STATEMENT_GROUP_NO")
							+ "</txtStatementGroupNo>";
					xml = xml + "<txtStatementGroupName>"
					+ rs.getString("statement_group_desc")
					+ "</txtStatementGroupName>";
					xml = xml + "<txtFromAccHdCode>"
							+ rs.getLong("FROM_ACC_HD_CODE")
							+ "</txtFromAccHdCode>";
					xml = xml + "<txtToAccHdCode>" + rs.getLong("TO_ACC_HD_CODE")
							+ "</txtToAccHdCode>";
					xml = xml + "<STATEMENT_SUB_GROUP_NO>" + rs.getInt("STATEMENT_SUB_GROUP_NO")
					+ "</STATEMENT_SUB_GROUP_NO>";
					xml = xml + "<STATEMENT_SUB_GROUP_Name>" + rs.getString("STATEMENT_SUB_GROUP_DEC")
					+ "</STATEMENT_SUB_GROUP_Name>";
				}

				/*ps = connection
						.prepareStatement("select STATEMENT_NO,STATEMENT_DESC from FAS_STATEMENT_MASTER order by STATEMENT_NO");
				results = ps.executeQuery();
				while (results.next()) {
					xml = xml + "<STATEMENT_NO>"
							+ results.getString("STATEMENT_NO")
							+ "</STATEMENT_NO>";
					xml = xml + "<STATEMENT_DESC>"
							+ results.getString("STATEMENT_DESC")
							+ "</STATEMENT_DESC>";
				}*/
				xml = xml + "<path>"+path+"</path><flag>success</flag>";
				
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		}else if (strCommand.equalsIgnoreCase("secondload")) 
		{
			xml = "<response><command>secondload</command>";
			String path = request.getParameter("path");
			
			String condn = "";
			String cmbStatementName=request.getParameter("cmbStatementName");
		//	System.out.println("cmbStatementName"+cmbStatementName);
			if(cmbStatementName!=null)
			{
				condn+=" s1.STATEMENT_NO ='"+cmbStatementName+"'";
			}
			int statementGroupName=0;
			try 
			{
				statementGroupName=Integer.parseInt(request.getParameter("statementGroupName"));
				
			}catch(Exception e){}
			
			if(statementGroupName>0)
			{
				condn+="AND s1.STATEMENT_GROUP_NO ="+statementGroupName;
			}
			int statementSubGroupNo=0;
			try 
			{
				statementSubGroupNo=Integer.parseInt(request.getParameter("statementSubGroupNo"));
				
			}catch(Exception e){}
			
			if((statementGroupName>0) && (statementSubGroupNo>0))
			{
				condn+="AND s1.STATEMENT_GROUP_NO ="+statementGroupName+" AND s1.statement_sub_group_no ="+statementSubGroupNo;
			}
			try
			{
				String sqy=" select s1.statement_no,s2.statement_desc,s1.statement_group_no,s3.statement_group_desc,s1.from_acc_hd_code, s1.to_acc_hd_code,s1.statement_sub_group_no,s4.STATEMENT_SUB_GROUP_DEC from fas_statement_acc_hd_mapping s1,	fas_statement_master s2,  fas_statement_group_master s3,fas_statement_sub_group_master s4	where s1.statement_no = s2.statement_no	and s1.statement_no = s3.statement_name	and s1.statement_group_no = s3.statement_group_no	and s4.statement_no = s1.statement_no and s4.statement_group_no = s1.statement_group_no	and s4.statement_sub_group_no = s1.statement_sub_group_no and"+condn;
				ps1 = connection.prepareStatement(sqy);
				rs = ps1.executeQuery();
				while (rs.next()) {
					xml = xml + "<cmbStatementName>"
					+ rs.getString("STATEMENT_NO") + "</cmbStatementName>";
			xml = xml + "<cmbStatementdesc>"
			+ rs.getString("statement_desc") + "</cmbStatementdesc>";
			xml = xml + "<txtStatementGroupNo>"
					+ rs.getInt("STATEMENT_GROUP_NO")
					+ "</txtStatementGroupNo>";
			xml = xml + "<txtStatementGroupName>"
			+ rs.getString("statement_group_desc")
			+ "</txtStatementGroupName>";
			xml = xml + "<txtFromAccHdCode>"
					+ rs.getLong("FROM_ACC_HD_CODE")
					+ "</txtFromAccHdCode>";
			xml = xml + "<txtToAccHdCode>" + rs.getLong("TO_ACC_HD_CODE")
					+ "</txtToAccHdCode>";
			xml = xml + "<STATEMENT_SUB_GROUP_NO>" + rs.getInt("STATEMENT_SUB_GROUP_NO")
			+ "</STATEMENT_SUB_GROUP_NO>";
			xml = xml + "<STATEMENT_SUB_GROUP_Name>" + rs.getString("STATEMENT_SUB_GROUP_DEC")
			+ "</STATEMENT_SUB_GROUP_Name>";
				}
				xml = xml + "<path>"+path+"</path>";
				xml = xml + "<flag>success</flag>";
			}catch(Exception e)
			{
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
		}
		else if (strCommand.equalsIgnoreCase("edit")) {
			xml = "<response><command>edit</command>";
//			System.out.println("edit option query");
			
			StringBuffer str = new StringBuffer();
			String sql = "";
			String statementNo = request.getParameter("statementNo");
			int groupNo =Integer.parseInt(request.getParameter("statementGroup"));
			int subGroupNo = Integer.parseInt(request.getParameter("statementSub"));
			long fromAchead = Long.parseLong(request.getParameter("txtFromAccHdCode"));
			long toAchead = Long.parseLong(request.getParameter("txtToAccHdCode"));
			try {
				str.append("SELECT a.STATEMENT_NO       AS STATEMENT_NO, \n");
				str.append("  a.STATEMENT_GROUP_NO      AS STATEMENT_GROUP_NO, \n");
				str.append("  a.FROM_ACC_HD_CODE        AS FROM_ACC_HD_CODE, \n");
				str.append("  a.TO_ACC_HD_CODE          AS TO_ACC_HD_CODE, \n");
				str.append("  a.STATEMENT_SUB_GROUP_NO  AS STATEMENT_SUB_GROUP_NO, \n");
				str.append("  a.GROUP_TYPE              AS GROUP_TYPE, \n");
				str.append("  b.STATEMENT_SUB_GROUP_DEC AS STATEMENT_SUB_GROUP_DEC, \n");
				str.append("  c.STATEMENT_GROUP_DESC    AS STATEMENT_GROUP_DESC \n");
				str.append("FROM \n");
				str.append("  (SELECT STATEMENT_NO, \n");
				str.append("    STATEMENT_GROUP_NO, \n");
				str.append("    FROM_ACC_HD_CODE, \n");
				str.append("    TO_ACC_HD_CODE, \n");
				str.append("    STATEMENT_SUB_GROUP_NO, \n");
				str.append("    GROUP_TYPE \n");
				str.append("  FROM FAS_STATEMENT_ACC_HD_MAPPING \n");
				str.append("  WHERE STATEMENT_NO        ='"+statementNo+"' \n");
				str.append("  AND STATEMENT_GROUP_NO    ='"+groupNo+"' \n");
				str.append("  AND STATEMENT_SUB_GROUP_NO='"+subGroupNo+"' \n");
				str.append("  AND FROM_ACC_HD_CODE      ='"+fromAchead+"' \n");
				str.append("  AND TO_ACC_HD_CODE        ='"+toAchead+"' \n");
				str.append("  )a \n");
				str.append("INNER JOIN \n");
				str.append("  (SELECT STATEMENT_NO, \n");
				str.append("    STATEMENT_GROUP_NO, \n");
				str.append("    STATEMENT_SUB_GROUP_NO, \n");
				str.append("    STATEMENT_SUB_GROUP_DEC \n");
				str.append("  FROM FAS_STATEMENT_SUB_GROUP_MASTER \n");
				str.append("  )b \n");
				str.append("ON a.STATEMENT_NO            =b.STATEMENT_NO \n");
				str.append("AND a.STATEMENT_GROUP_NO     = b.STATEMENT_GROUP_NO \n");
				str.append("AND a.STATEMENT_SUB_GROUP_NO = b.STATEMENT_SUB_GROUP_NO \n");
				str.append("INNER JOIN \n");
				str.append("  (SELECT STATEMENT_NAME, \n");
				str.append("    STATEMENT_GROUP_NO, \n");
				str.append("    STATEMENT_GROUP_DESC \n");
				str.append("  FROM FAS_STATEMENT_GROUP_MASTER \n");
				str.append("  )c \n");
				str.append("ON a.STATEMENT_NO       =c.STATEMENT_NAME \n");
				str.append("AND a.STATEMENT_GROUP_NO=c.STATEMENT_GROUP_NO");				
				sql = str.toString();
				System.out.println("sql  >>>> "+sql);
				ps1 = connection.prepareStatement(sql);
				rs = ps1.executeQuery();
				if(rs.next()){
					xml = xml + "<flag>success</flag>";
					xml = xml + "<STATEMENT_NO>"+rs.getString("STATEMENT_NO")+"</STATEMENT_NO>";
					xml = xml + "<GROUP_NO>"+rs.getInt("STATEMENT_GROUP_NO")+"</GROUP_NO>";
					xml = xml + "<GROUP_DESC>"+rs.getString("STATEMENT_GROUP_DESC")+"</GROUP_DESC>";
					xml = xml + "<SUB_GROUP_NO>"+rs.getInt("STATEMENT_SUB_GROUP_NO")+"</SUB_GROUP_NO>";
					xml = xml + "<SUB_GROUP_DEC>"+rs.getString("STATEMENT_SUB_GROUP_DEC")+"</SUB_GROUP_DEC>";
					xml = xml + "<GROUP_TYPE>"+rs.getString("GROUP_TYPE")+"</GROUP_TYPE>";
					xml = xml + "<FROM_ACC_HD_CODE>"+rs.getLong("FROM_ACC_HD_CODE")+"</FROM_ACC_HD_CODE>";
					xml = xml + "<TO_ACC_HD_CODE>"+rs.getLong("TO_ACC_HD_CODE")+"</TO_ACC_HD_CODE>";					
				}else{
					xml = xml + "<flag>failure</flag>";
				}
				
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("LoadStatementGroupNo")) {
			xml = "<response><command>LoadStatementGroupNo</command>";

			String cmbStatementName = request.getParameter("cmbStatementName");

			try {
				ps = connection
						.prepareStatement("select STATEMENT_GROUP_NO,STATEMENT_GROUP_DESC from FAS_STATEMENT_GROUP_MASTER where STATEMENT_NAME=? order by STATEMENT_GROUP_NO");
				ps.setString(1, cmbStatementName);
				results = ps.executeQuery();
				while (results.next()) {
					xml = xml + "<STATEMENT_GROUP_NO>"
							+ results.getString("STATEMENT_GROUP_NO")
							+ "</STATEMENT_GROUP_NO>";
					xml=xml+"<STATEMENT_GROUP_DESC><![CDATA["+results.getString("STATEMENT_GROUP_DESC")+"]]></STATEMENT_GROUP_DESC>";
				}

				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("loadStatementSubGroupNo")) {
			xml = "<response><command>loadStatementSubGroupNo</command>";
			
			String statementNo = request.getParameter("statementNo");
			int statementGroupNo = Integer.parseInt(request.getParameter("statementGroupNo"));
				
			try {
				ps = connection.prepareStatement("select STATEMENT_SUB_GROUP_NO,STATEMENT_SUB_GROUP_DEC from FAS_STATEMENT_SUB_GROUP_MASTER where STATEMENT_NO=? and STATEMENT_GROUP_NO=? and STATUS='L' order by STATEMENT_SUB_GROUP_NO");
				ps.setString(1, statementNo);
				ps.setInt(2, statementGroupNo);
				results = ps.executeQuery();
				while (results.next()) {
					xml = xml + "<STATEMENT_SUB_GROUP_NO>"
							+ results.getString("STATEMENT_SUB_GROUP_NO")
							+ "</STATEMENT_SUB_GROUP_NO>";
					xml=xml+"<STATEMENT_SUB_GROUP_DESC><![CDATA["+results.getString("STATEMENT_SUB_GROUP_DEC")+"]]></STATEMENT_SUB_GROUP_DESC>";
				}

				xml = xml + "<flag>success</flag>";
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

			int cmbStatementName = Integer.parseInt(request
					.getParameter("cmbStatementName"));
			int txtStatementGroupNo = Integer.parseInt(request
					.getParameter("txtStatementGroupNo"));

			int txtFromAccHdCode = Integer.parseInt(request
					.getParameter("txtFromAccHdCode"));

			int txtToAccHdCode = Integer.parseInt(request
					.getParameter("txtToAccHdCode"));
			try {
				ps1 = connection
						.prepareStatement("select STATEMENT_NO,STATEMENT_GROUP_NO,FROM_ACC_HD_CODE,TO_ACC_HD_CODE,STATEMENT_SUB_GROUP_NO from FAS_STATEMENT_ACC_HD_MAPPING where STATEMENT_NO=? and STATEMENT_GROUP_NO=? and FROM_ACC_HD_CODE=? and TO_ACC_HD_CODE=?");
				ps1.setInt(1, cmbStatementName);
				ps1.setInt(2, txtStatementGroupNo);
				ps1.setInt(3, txtFromAccHdCode);
				ps1.setInt(4, txtToAccHdCode);
				rs = ps1.executeQuery();
				if (rs.next()) {
					ps = connection
							.prepareStatement("delete from FAS_STATEMENT_ACC_HD_MAPPING where STATEMENT_NO=? and STATEMENT_GROUP_NO=? and FROM_ACC_HD_CODE=? and TO_ACC_HD_CODE=?");
					ps.setInt(1, cmbStatementName);
					ps.setInt(2, txtStatementGroupNo);
					ps.setInt(3, txtFromAccHdCode);
					ps.setInt(4, txtToAccHdCode);
					ps.executeUpdate();

					xml = xml + "<flag>success</flag>";
					connection.commit();
					xml = xml + "<id>" + cmbStatementName + "</id>";
					xml = xml + "<id1>" + txtStatementGroupNo + "</id1>";
					xml = xml + "<id2>" + txtFromAccHdCode + "</id2>";
					xml = xml + "<id3>" + txtToAccHdCode + "</id3>";
					xml = xml + "<id4>" + rs.getInt("STATEMENT_SUB_GROUP_NO") + "</id4>";
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
