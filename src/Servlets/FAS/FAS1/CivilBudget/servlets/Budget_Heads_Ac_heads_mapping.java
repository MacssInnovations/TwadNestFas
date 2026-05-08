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
 * Servlet implementation class Budget_Heads_Ac_heads_mapping
 */
public class Budget_Heads_Ac_heads_mapping extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Budget_Heads_Ac_heads_mapping() {
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
		            
		             reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/Civil_Budget_ACHead_Mapping.jasper")); 
		           
		         
		         
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
		                response.setHeader("Content-Disposition","attachment;filename=\"Civil_Budget_AccountHeadMapping.pdf\"");
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
		if (strCommand.equalsIgnoreCase("add")) {
			xml = "<response><command>add</command>";

			String cmbFormatNo = request.getParameter("cmbFormatNo");
			int cmbBudgetGroupMajor = Integer.parseInt(request
					.getParameter("cmbBudgetGroupMajor"));
			/*String cmbBudgetDescMain = request
					.getParameter("cmbBudgetDescMain");
			String cmbBudgetGroupMinor = request
					.getParameter("cmbBudgetGroupMinor");
			int cmbBudgetDescSub = Integer.parseInt(request
					.getParameter("cmbBudgetDescSub"));*/
			int txtAcc_HeadCode = Integer.parseInt(request
					.getParameter("txtAcc_HeadCode"));

			try {
				ps1 = connection
						.prepareStatement("select FORMAT_NO from FAS_BUDGET_AC_HEADS_MAP where FORMAT_NO=? and BUDGET_GROUP_ID=? and ACC_HEAD_CODE=?");
				ps1.setString(1, cmbFormatNo);
				//ps1.setString(2, cmbBudgetDescMain);
				//ps1.setInt(3, cmbBudgetDescSub);
				ps1.setInt(2, cmbBudgetGroupMajor);
				ps1.setInt(3, txtAcc_HeadCode);
				rs = ps1.executeQuery();
				if (rs.next()) {
					xml = xml + "<flag>Exist</flag>";
				} else {
					ps = connection
							.prepareStatement("insert into FAS_BUDGET_AC_HEADS_MAP(FORMAT_NO,BUDGET_GROUP_ID,ACC_HEAD_CODE,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?)");
					ps.setString(1, cmbFormatNo);
					//ps.setString(2, cmbBudgetDescMain);
					//ps.setInt(3, cmbBudgetDescSub);
					ps.setInt(2, cmbBudgetGroupMajor);
					ps.setInt(3, txtAcc_HeadCode);
					ps.setString(4, userid);
					ps.setTimestamp(5, ts);
					
					//ps.setString(8, cmbBudgetGroupMinor);

					if(ps.executeUpdate()>0)
					{
						String qry="select a.FORMAT_NO,a.BUDGET_GROUP_ID, b.BUDGET_GROUP_MAJOR,a.ACC_HEAD_CODE from FAS_BUDGET_AC_HEADS_MAP a inner join FAS_BUDGET_GROUP_MASTER b on b.budget_group_id=a.budget_group_id where a.FORMAT_NO='"+cmbFormatNo+"' and a.BUDGET_GROUP_ID="+cmbBudgetGroupMajor+" and a.ACC_HEAD_CODE="+txtAcc_HeadCode;
						ps = connection.prepareStatement(qry);
	 				//	System.out.println(qry);
	 					rs=ps.executeQuery();
	 					if(rs.next())
	 					{
					
					xml = xml + "<cmbFormatNo>" + rs.getString("FORMAT_NO")
							+ "</cmbFormatNo>";
					/*xml = xml + "<cmbBudgetDescMain>" + cmbBudgetDescMain
							+ "</cmbBudgetDescMain>";*/

					/*xml = xml + "<cmbBudgetDescSub>" + cmbBudgetDescSub
							+ "</cmbBudgetDescSub>";*/
					xml = xml + "<BudgetGroupid>"+rs.getInt("BUDGET_GROUP_ID")
					          +"</BudgetGroupid>";
					xml = xml + "<txtAcc_HeadCode>" + rs.getInt("ACC_HEAD_CODE")
							+ "</txtAcc_HeadCode>";
					xml = xml + "<BudgetGroupMajor>" + rs.getString("BUDGET_GROUP_MAJOR")
							+ "</BudgetGroupMajor>";
					
					/*xml = xml + "<BudgetGroupMinor>" + cmbBudgetGroupMinor
							+ "</BudgetGroupMinor>";*/
					xml = xml + "<flag>success</flag>";
	 			}
			}
			}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("LoadMajorandSubBudgetHeads")) {
			xml = "<response><command>LoadMajorandSubBudgetHeads</command>";
System.out.println("LoadMajorandSubBudgetHeads");
			String cmbFormatNo = request.getParameter("cmbFormatNo");
			try {
				ps = connection
						.prepareStatement(" select BUDGET_HEADS_ID_MAJOR,MAJOR_HEAD_DESC from "
								+ "( select distinct BUDGET_HEADS_ID_MAJOR from FAS_BUDGET_HEADS_MASTER where FORMAT_NO=? )a"
								+ " left outer join ( select MAJOR_HEAD_CODE,MAJOR_HEAD_DESC from COM_MST_MAJOR_HEADS )b "
								+ "on a.BUDGET_HEADS_ID_MAJOR = b.MAJOR_HEAD_CODE order by BUDGET_HEADS_ID_MAJOR");
				ps.setString(1, cmbFormatNo);
				rs2 = ps.executeQuery();
				while (rs2.next()) {
					xml = xml + "<BudgetHeadMajorid>"
							+ rs2.getString("BUDGET_HEADS_ID_MAJOR")
							+ "</BudgetHeadMajorid>";
					xml = xml + "<BudgetHeadMajorDesc>"
							+ rs2.getString("MAJOR_HEAD_DESC")
							+ "</BudgetHeadMajorDesc>";
				}
				xml = xml + "<flag1>success</flag1>";
			} catch (Exception e) {
				xml = xml + "<flag1>failure</flag1>";
				e.printStackTrace();
			}

			try {
				ps1 = connection
						.prepareStatement(" select BUDGET_GROUP_ID,BUDGET_GROUP_MAJOR from "
								+ "( select distinct BUDGET_GROUP_MAJOR as bg1 from FAS_BUDGET_HEADS_MASTER  "
								+ "where FORMAT_NO=? )a left outer join ( select BUDGET_GROUP_ID,"
								+ "BUDGET_GROUP_MAJOR from FAS_BUDGET_GROUP_MASTER )b "
								+ "on a.bg1 = b.BUDGET_GROUP_ID order by BUDGET_GROUP_ID");
				ps1.setString(1, cmbFormatNo);
				rs = ps1.executeQuery();
				while (rs.next()) {
					xml = xml + "<BudgetGroupMajorid>"
							+ rs.getInt("BUDGET_GROUP_ID")
							+ "</BudgetGroupMajorid>";
					xml = xml + "<BudgetGroupMajorDesc>"
							+ rs.getString("BUDGET_GROUP_MAJOR")
							+ "</BudgetGroupMajorDesc>";
				}
				xml = xml + "<flag11>success</flag11>";
			} catch (Exception e) {
				xml = xml + "<flag11>failure</flag11>";
				e.printStackTrace();
			}

		} else if (strCommand.equalsIgnoreCase("getMinorBudgetHeadDesc")) {
			xml = "<response><command>getMinorBudgetHeadDesc</command>";
			System.out.println("getMinorBudgetHeadDesc");
			String cmbFormatNo = request.getParameter("cmbFormatNo");
			String cmbBudgetDescMain = request
					.getParameter("cmbBudgetDescMain");
			try {
				ps = connection
						.prepareStatement(" select distinct BUDGET_HEADS_ID_SUB,MINOR_HEAD_DESC from( select BUDGET_"
								+ "HEADS_ID_MAJOR,BUDGET_HEADS_ID_SUB from FAS_BUDGET_HEADS_MASTER where "
								+ "BUDGET_HEADS_ID_MAJOR=? and FORMAT_NO = ? )a left outer join "
								+ "( select MAJOR_HEAD_CODE,MINOR_HEAD_CODE,MINOR_HEAD_DESC from "
								+ "COM_MST_MINOR_HEADS )b on a.BUDGET_HEADS_ID_MAJOR = b.MAJOR_HEAD_CODE "
								+ "and a.BUDGET_HEADS_ID_SUB = b.MINOR_HEAD_CODE order by BUDGET_HEADS_ID_SUB");
				ps.setString(1, cmbBudgetDescMain);
				ps.setString(2, cmbFormatNo);
				rs2 = ps.executeQuery();
				while (rs2.next()) {
					xml = xml + "<BudgetIdSub>"
							+ rs2.getInt("BUDGET_HEADS_ID_SUB")
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
		} else if (strCommand.equalsIgnoreCase("getBudgetGroupMinor")) {
			xml = "<response><command>getBudgetGroupMinor</command>";
	System.out.println("getBudgetGroupMinor");
			String cmbFormatNo = request.getParameter("cmbFormatNo");
			int cmbBudgetGroupMajor = Integer.parseInt(request
					.getParameter("cmbBudgetGroupMajor"));
			try {
				ps = connection
						.prepareStatement(" select BUDGET_GROUP_MINOR from FAS_BUDGET_HEADS_MASTER  "
								+ "where FORMAT_NO=? and BUDGET_GROUP_MAJOR=? and BUDGET_GROUP_MINOR "
								+ "is not null ");
				ps.setString(1, cmbFormatNo);
				ps.setInt(2, cmbBudgetGroupMajor);
				rs2 = ps.executeQuery();
				while (rs2.next()) {
					xml = xml + "<BudgetGroupMinor>"
							+ rs2.getString("BUDGET_GROUP_MINOR")
							+ "</BudgetGroupMinor>";
				}
				xml = xml + "<flag3>success</flag3>";
			} catch (Exception e) {
				xml = xml + "<flag3>failure</flag3>";
				e.printStackTrace();
			}
		} else if (strCommand.equalsIgnoreCase("LoadAccHdCode")) {
			xml = "<response><command>LoadAccHdCode</command>";
			System.out.println("LoadAccHdCode11");
			String cmbBudgetDescMain = request
					.getParameter("cmbBudgetDescMain");
			int cmbBudgetDescSub = Integer.parseInt(request
					.getParameter("cmbBudgetDescSub"));
			try {
				ps = connection
						.prepareStatement("select ACCOUNT_HEAD_CODE from COM_MST_ACCOUNT_HEADS");
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
		} else if (strCommand.equalsIgnoreCase("LoadAccHdDesc")) {
			xml = "<response><command>LoadAccHdDesc</command>";
System.out.println("LoadAccHdDesc22");
			String cmbBudgetDescMain = request
					.getParameter("cmbBudgetDescMain");
			int cmbBudgetDescSub = Integer.parseInt(request
					.getParameter("cmbBudgetDescSub"));
			int txtAcc_HeadCode = Integer.parseInt(request
					.getParameter("txtAcc_HeadCode"));
		/*	try {
				ps = connection
						.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
				ps.setString(1, cmbBudgetDescMain);
				ps.setInt(2, cmbBudgetDescSub);
				ps.setInt(1, txtAcc_HeadCode);
				rs2 = ps.executeQuery();
				while (rs2.next()) {
					xml = xml + "<AccHdDesc>"
							+ rs2.getString("ACCOUNT_HEAD_DESC")
							+ "</AccHdDesc>";
				}
				xml = xml + "<flag3>success</flag3>";
			}*/
						try {
							ps = connection
							.prepareStatement("select ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where ACCOUNT_HEAD_CODE=?");
				
					ps.setInt(1, txtAcc_HeadCode);
					rs2 = ps.executeQuery();
					while (rs2.next()) {
						xml = xml + "<AccHdDesc>"
								+ rs2.getString("ACCOUNT_HEAD_DESC")
								+ "</AccHdDesc>";
						xml = xml + "<flag3>success</flag3>";
					}
					xml = xml + "<flag3>failure</flag3>";
				}
			catch (Exception e) {
				xml = xml + "<flag3>failure</flag3>";
				e.printStackTrace();
			}
		} else if (strCommand.equalsIgnoreCase("LoadData")) {
			
			xml = "<response><command>LoadData</command>";
			System.out.println("loaddata");
			//System.out.println("enter.......");
			String cmbFormatNo = request.getParameter("cmbFormatNo");
			//System.out.println("cmbFormatNo::::"+cmbFormatNo);		
			int cmbBudgetGroupMajor = Integer.parseInt(request
					.getParameter("cmbBudgetGroupMajor"));
			//System.out.println("cmbBudgetGroupMajor:::::"+cmbBudgetGroupMajor);
			int txtAcc_HeadCode = Integer.parseInt(request
					.getParameter("txtAcc_HeadCode"));
			
			
			try {
				ps = connection.prepareStatement("select a.FORMAT_NO,a.BUDGET_GROUP_ID,a.ACC_HEAD_CODE ,b.BUDGET_GROUP_MAJOR,c.ACCOUNT_HEAD_DESC from FAS_BUDGET_AC_HEADS_MAP a inner join FAS_BUDGET_GROUP_MASTER b on b.BUDGET_GROUP_ID=a.BUDGET_GROUP_ID inner join COM_MST_ACCOUNT_HEADS c on c.ACCOUNT_HEAD_CODE=a.ACC_HEAD_CODE where  a.FORMAT_NO=?  and a.BUDGET_GROUP_ID=? and a.ACC_HEAD_CODE=? ");
				ps.setString(1, cmbFormatNo);
				ps.setInt(2, cmbBudgetGroupMajor);
				ps.setInt(3, txtAcc_HeadCode);
				rs2 = ps.executeQuery();
				while (rs2.next()) {
					   
					
					xml = xml + "<formateNo>"
							+ rs2.getString("FORMAT_NO")
							+ "</formateNo>";
					
					xml = xml + "<BudgetGroupid>"
							+ rs2.getString("BUDGET_GROUP_ID")
							+ "</BudgetGroupid>";
					xml = xml + "<AccHdCode>" + rs2.getInt("ACC_HEAD_CODE")
							+ "</AccHdCode>";
					xml=xml+"<AccHdDesc><![CDATA["+rs2.getString("ACCOUNT_HEAD_DESC")+"]]></AccHdDesc>";
					
					xml = xml + "<BudgetGroupMajorDesc>"
							+ rs2.getString("BUDGET_GROUP_MAJOR")
							+ "</BudgetGroupMajorDesc>";
					
				}
				xml = xml + "<flag1>success</flag1>";
			} catch (Exception e) {
				xml = xml + "<flag1>failure</flag1>";
				e.printStackTrace();
			}
			/*try {
				ps = connection
						.prepareStatement(" select BUDGET_GROUP_MINOR from FAS_BUDGET_HEADS_MASTER  "
								+ "where FORMAT_NO=? and BUDGET_GROUP_MAJOR=? and BUDGET_GROUP_MINOR "
								+ "is not null ");
				ps.setString(1, cmbFormatNo);
				ps.setInt(2, cmbBudgetGroupMajor);
				rs2 = ps.executeQuery();
				while (rs2.next()) {
					xml = xml + "<BudgetGroupMinorDesc>"
							+ rs2.getString("BUDGET_GROUP_MINOR")
							+ "</BudgetGroupMinorDesc>";
				}
				xml = xml + "<flag3>success</flag3>";
			} catch (Exception e) {
				xml = xml + "<flag3>failure</flag3>";
				e.printStackTrace();
			}
			try {
				ps = connection
						.prepareStatement(" select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS ");
				ps.setString(1, cmbBudgetDescMain);
				ps.setInt(2, cmbBudgetDescSub);
				rs2 = ps.executeQuery();
				while (rs2.next()) {
					xml = xml + "<AccHdCode>" + rs2.getInt("ACCOUNT_HEAD_CODE")
							+ "</AccHdCode>";
					xml=xml+"<AccHdCodeDesc><![CDATA["+rs2.getString("ACCOUNT_HEAD_DESC")+"]]></AccHdCodeDesc>";
				}
				xml = xml + "<flag4>success</flag4>";
			} catch (Exception e) {
				xml = xml + "<flag4>failure</flag4>";
				e.printStackTrace();
			}*/

		} else if (strCommand.equalsIgnoreCase("formateno")) {
			xml = "<response><command>getGrid</command>";
			System.out.println("formatno getgrid");
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
		}else if (strCommand.equalsIgnoreCase("getGrid")) {
			xml = "<response><command>getGrid</command>";
			System.out.println("getgrid direct");
			String condn = "";
			String cmbFormatNo=null; 
			
			 cmbFormatNo = request.getParameter("cmbFormatNo");
			// System.out.println("cmbFormatNo::"+cmbFormatNo);
			 if(cmbFormatNo!=null)
					condn += "where a.FORMAT_NO ='"+cmbFormatNo+"'";
			// System.out.println("where a.FORMAT_NO ='"+cmbFormatNo+"'");
			int cmbBudgetGroupMajor=0;
			try{
				 cmbBudgetGroupMajor = Integer.parseInt(request.getParameter("cmbBudgetGroupMajor"));	
			//System.out.println("cmbBudgetGroupMajor::::"+cmbBudgetGroupMajor);
			}catch(Exception e)
			{}
			if(cmbBudgetGroupMajor>0)
				condn += "and a.BUDGET_GROUP_ID  ="+cmbBudgetGroupMajor;
             if(cmbFormatNo==null && cmbBudgetGroupMajor==0)
             {
			try {
				ps1 = connection.prepareStatement("select a.FORMAT_NO,a.BUDGET_GROUP_ID, b.BUDGET_GROUP_MAJOR,a.ACC_HEAD_CODE from FAS_BUDGET_AC_HEADS_MAP a inner join FAS_BUDGET_GROUP_MASTER b on b.budget_group_id=a.budget_group_id");
				rs = ps1.executeQuery();
				while (rs.next()) {

					xml = xml + "<cmbFormatNo>" + rs.getString("FORMAT_NO")
							+ "</cmbFormatNo>";
					/*xml = xml + "<cmbBudgetDescMain>"
							+ rs.getString("BUDGET_HEADS_MAJOR")
							+ "</cmbBudgetDescMain>";*/

					/*xml = xml + "<cmbBudgetDescSub>"
							+ rs.getInt("BUDGET_HEADS_SUB")
							+ "</cmbBudgetDescSub>";*/
					xml = xml +"<BudgetGroupid>"+ rs.getInt("BUDGET_GROUP_ID")
					          +"</BudgetGroupid>";
				//	System.out.println( rs.getInt("BUDGET_GROUP_ID"));
					xml = xml + "<txtAcc_HeadCode>"
							+ rs.getInt("ACC_HEAD_CODE") + "</txtAcc_HeadCode>";
					xml = xml + "<BudgetGroupMajor>"
							+ rs.getString("BUDGET_GROUP_MAJOR")
							+ "</BudgetGroupMajor>";
				//	System.out.println(rs.getString("BUDGET_GROUP_MAJOR"));
					/*xml = xml + "<BudgetGroupMinor>"
							+ rs.getString("BUDGET_GROUP_MINOR")
							+ "</BudgetGroupMinor>";
					xml = xml + "<BUDGET_GROUP_MAJOR>"
							+ rs.getString("BUDGET_GROUP_MAJOR_DESC")
							+ "</BUDGET_GROUP_MAJOR>";*/

				}
				xml = xml + "<flag>success</flag>";
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
          }
             else
        	  {
 			try {
 				ps1 = connection.prepareStatement("select a.FORMAT_NO,a.BUDGET_GROUP_ID, b.BUDGET_GROUP_MAJOR,a.ACC_HEAD_CODE from FAS_BUDGET_AC_HEADS_MAP a inner join FAS_BUDGET_GROUP_MASTER b on b.budget_group_id=a.budget_group_id "+ condn);
 				System.out.println("select a.FORMAT_NO,a.BUDGET_GROUP_ID, b.BUDGET_GROUP_MAJOR,a.ACC_HEAD_CODE from FAS_BUDGET_AC_HEADS_MAP a inner join FAS_BUDGET_GROUP_MASTER b on b.budget_group_id=a.budget_group_id "+ condn);
 				rs = ps1.executeQuery();
 				while (rs.next()) {

 					xml = xml + "<cmbFormatNo>" + rs.getString("FORMAT_NO")
					+ "</cmbFormatNo>";
			/*xml = xml + "<cmbBudgetDescMain>"
					+ rs.getString("BUDGET_HEADS_MAJOR")
					+ "</cmbBudgetDescMain>";*/

			/*xml = xml + "<cmbBudgetDescSub>"
					+ rs.getInt("BUDGET_HEADS_SUB")
					+ "</cmbBudgetDescSub>";*/
			xml = xml +"<BudgetGroupid>"+rs.getInt("BUDGET_GROUP_ID")
			          +"</BudgetGroupid>";
			xml = xml + "<txtAcc_HeadCode>"
					+ rs.getInt("ACC_HEAD_CODE") + "</txtAcc_HeadCode>";
			xml = xml + "<BudgetGroupMajor>"
					+ rs.getString("BUDGET_GROUP_MAJOR")
					+ "</BudgetGroupMajor>";
			/*xml = xml + "<BudgetGroupMinor>"
					+ rs.getString("BUDGET_GROUP_MINOR")
					+ "</BudgetGroupMinor>";
			xml = xml + "<BUDGET_GROUP_MAJOR>"
					+ rs.getString("BUDGET_GROUP_MAJOR_DESC")
					+ "</BUDGET_GROUP_MAJOR>";*/


 				}
 				xml = xml + "<flag>success</flag>";
 			} catch (Exception e) {
 				xml = xml + "<flag>failure</flag>";
 				e.printStackTrace();
 			}
         }

		} else if (strCommand.equalsIgnoreCase("update")) {

			xml = "<response><command>update</command>";

			String cmbFormatNo = request.getParameter("cmbFormatNo");
			int cmbBudgetGroupMajor = Integer.parseInt(request
					.getParameter("cmbBudgetGroupMajor"));
			String cmbBudgetDescMain = request
					.getParameter("cmbBudgetDescMain");
			String cmbBudgetGroupMinor = request
					.getParameter("cmbBudgetGroupMinor");
			int cmbBudgetDescSub = Integer.parseInt(request
					.getParameter("cmbBudgetDescSub"));
			int txtAcc_HeadCode = Integer.parseInt(request
					.getParameter("txtAcc_HeadCode"));
			try {
				ps1 = connection
						.prepareStatement("select FORMAT_NO from FAS_BUDGET_HDS_AC_HDS_MAP_MST where FORMAT_NO=? and BUDGET_GROUP_MAJOR=?  and ACC_HEAD_CODE=?");
				ps1.setString(1, cmbFormatNo);
				//ps1.setString(2, cmbBudgetDescMain);
				//ps1.setInt(3, cmbBudgetDescSub);
				ps1.setInt(2, cmbBudgetGroupMajor);
				ps1.setInt(3, txtAcc_HeadCode);
				rs = ps1.executeQuery();
				if (rs.next()) {
					ps = connection
							.prepareStatement("update FAS_BUDGET_HDS_AC_HDS_MAP_MST set ACC_HEAD_CODE=?,UPDATED_BY_USERID=?,UPDATED_DATE=?,BUDGET_GROUP_MINOR=? where FORMAT_NO=? and BUDGET_GROUP_MAJOR=? and ACC_HEAD_CODE=?");

					ps.setInt(1, txtAcc_HeadCode);
					ps.setString(2, userid);
					ps.setTimestamp(3, ts);
					ps.setString(4, cmbBudgetGroupMinor);
					ps.setString(5, cmbFormatNo);
					//ps.setString(6, cmbBudgetDescMain);
					//ps.setInt(7, cmbBudgetDescSub);
					ps.setInt(6, cmbBudgetGroupMajor);
					ps.setInt(7, txtAcc_HeadCode);
					ps.executeUpdate();

					xml = xml + "<flag>success</flag>";
					xml = xml + "<cmbFormatNo>" + cmbFormatNo
							+ "</cmbFormatNo>";
					xml = xml + "<cmbBudgetDescMain>" + cmbBudgetDescMain
							+ "</cmbBudgetDescMain>";

					xml = xml + "<cmbBudgetDescSub>" + cmbBudgetDescSub
							+ "</cmbBudgetDescSub>";
					xml = xml + "<txtAcc_HeadCode>" + txtAcc_HeadCode
							+ "</txtAcc_HeadCode>";
					xml = xml + "<BudgetGroupMajor>" + cmbBudgetGroupMajor
							+ "</BudgetGroupMajor>";
					xml = xml + "<BudgetGroupMinor>" + cmbBudgetGroupMinor
							+ "</BudgetGroupMinor>";
					ps.close();
				} else {
					xml = xml + "<flag>NoData</flag>";
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();

			}

		} else if (strCommand.equalsIgnoreCase("deleted")) {

			xml = "<response><command>deleted</command>";
			String cmbFormatNo = request.getParameter("cmbFormatNo");
			int cmbBudgetGroupMajor = Integer.parseInt(request
					.getParameter("cmbBudgetGroupMajor"));
			int txtAcc_HeadCode = Integer.parseInt(request
					.getParameter("txtAcc_HeadCode"));
			try {
				ps1 = connection
						.prepareStatement("select FORMAT_NO from FAS_BUDGET_AC_HEADS_MAP where FORMAT_NO=? and BUDGET_GROUP_ID=? and ACC_HEAD_CODE=?");
				ps1.setString(1, cmbFormatNo);
				//ps1.setString(2, cmbBudgetDescMain);
				//ps1.setInt(3, cmbBudgetDescSub);
				ps1.setInt(2, cmbBudgetGroupMajor);
				ps1.setInt(3, txtAcc_HeadCode);

				rs = ps1.executeQuery();
				if (rs.next()) {
					ps = connection
							.prepareStatement("delete from FAS_BUDGET_AC_HEADS_MAP where FORMAT_NO=? and BUDGET_GROUP_ID=?  and ACC_HEAD_CODE=?");
					ps.setString(1, cmbFormatNo);
					//ps.setString(2, cmbBudgetDescMain);
					//ps.setInt(3, cmbBudgetDescSub);
					ps.setInt(2, cmbBudgetGroupMajor);
					ps.setInt(3, txtAcc_HeadCode);
					ps.executeUpdate();
					xml = xml + "<flag>success</flag>";
					xml = xml
							+ "<id>"
							+ (cmbFormatNo + cmbBudgetGroupMajor + txtAcc_HeadCode)
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
		//System.out.println(xml);
	}

}
