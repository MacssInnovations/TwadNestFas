package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class ProjectMasterServlet extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

	public void init(ServletConfig config) throws ServletException {
		super.init(config);

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection connection = null;
		Statement statement = null;
		ResultSet results = null;
		ResultSet results2 = null;
		PreparedStatement ps = null;
		PreparedStatement ps2 = null;
		try {
			ResourceBundle rs = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
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
		response.setContentType(CONTENT_TYPE);
		String strCommand = "";
		String xml = "";
		int ret_code = 0;
		String projectname = "";
		String projectalias = "";
		String componentname = "";
		/*
		 * String AssetTypeCode=""; String AssetTypeDesc=""; String
		 * AssetTypeAlias="";
		 */

		HttpSession session = request.getSession(false);
		try {

			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		String userid = (String) session.getAttribute("UserId");
		System.out.println("session id is:" + userid);

		response.setContentType("text/xml");
		PrintWriter pw = response.getWriter();
		response.setHeader("Cache-Control", "no-cache");
		try {
			strCommand = request.getParameter("command");
		} catch (Exception e) {
			e.printStackTrace();
		}
		Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
				.getAttribute("UserProfile");
		int empid = empProfile.getEmployeeId();
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
		String status = request.getParameter("status");
		/*
		 * try {
		 * 
		 * AssetTypeDesc = request.getParameter("AssetTypeDesc");
		 * AssetTypeCode=request.getParameter("AssetTypeCode");
		 * AssetTypeAlias=request.getParameter("AssetTypeAlias");
		 * System.out.println("Asset Code:"+AssetTypeCode);
		 * System.out.println("Asset desc:"+AssetTypeDesc);
		 * System.out.println("Asset alias:"+AssetTypeAlias);
		 * 
		 * }
		 * 
		 * catch(Exception e) {
		 * System.out.println("in getting values in all other values **** "+ e);
		 * }
		 */

		try {

			if (strCommand.equalsIgnoreCase("Cancel")) {
				xml = "<response><command>Cancel</command>";
				try {
					projectname = request.getParameter("ProjectTypeDesc");
					projectalias = request.getParameter("ProjectCode");
					componentname = request.getParameter("ComponentName");

					int oid = Integer.parseInt(request.getParameter("Office"));
					int projectno = Integer.parseInt(request
							.getParameter("ProjectId"));
					PreparedStatement pstmt = connection
							.prepareStatement("UPDATE PMS_MST_PROJECTS_VIEW SET STATUS='C',UPDATED_BY_USER_ID=?,UPDATED_DATE=? where office_id=? and project_id=?");
					System.out.println(pstmt);
					pstmt.setString(1, userid);
					pstmt.setTimestamp(2, ts);
					pstmt.setInt(3, oid);
					pstmt.setInt(4, projectno);

					pstmt.executeUpdate();
					xml = xml + "<flag>success</flag><ProjectId>" + projectno
							+ "</ProjectId>";
					pstmt.close();
				} catch (SQLException e) {
					ret_code = e.getErrorCode();
					System.err.println(ret_code + e.getMessage());
					xml = xml + "<flag>failure</flag>";
				}

				xml = xml + "</response>";
			} else if (strCommand.equalsIgnoreCase("loadWing")) {
				xml = "<response><command>loadWing</command>";
				int wing_id=0;
				try {
					ps = connection
							.prepareStatement("select EMPLOYEE_ID,OFFICE_ID,OFFICE_WING_SINO from HRM_EMP_CURRENT_WING where EMPLOYEE_ID=?");
					ps.setInt(1, empid);
					results = ps.executeQuery();
					if (results.next()) {

						wing_id = results.getInt("OFFICE_WING_SINO");
					}

					int oid = Integer.parseInt(request.getParameter("Office"));
					PreparedStatement pstmt = connection
							.prepareStatement("select OFFICE_WING_SINO,WING_NAME from COM_OFFICE_WINGS where OFFICE_ID=? and OFFICE_WING_SINO=?");
					pstmt.setInt(1, oid);
					pstmt.setInt(2, wing_id);
					results = pstmt.executeQuery();
					while (results.next()) {
						xml = xml + "<wing_id>"
								+ results.getInt("OFFICE_WING_SINO")
								+ "</wing_id>";
						xml = xml + "<wing_name>"
								+ results.getString("WING_NAME")
								+ "</wing_name>";
					}
					xml = xml + "<flag>success</flag>";
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					xml = xml + "<flag>failure</flag>";
				}

				xml = xml + "</response>";
			} else if (strCommand.equalsIgnoreCase("loadWing1")) {
				xml = "<response><command>loadWing1</command>";
				try {
					int rid = Integer.parseInt(request.getParameter("rid"));
					xml = xml + "<rid>" + rid + "</rid>";
					PreparedStatement pstmt = connection
							.prepareStatement("select WING_ID from PMS_MST_PROJECTS_VIEW where PROJECT_ID=?");
					pstmt.setInt(1, rid);
					results = pstmt.executeQuery();
					while (results.next()) {
						xml = xml + "<wing_id>" + results.getInt("WING_ID")
								+ "</wing_id>";
					}
					xml = xml + "<flag>success</flag>";
					pstmt.close();
				} catch (SQLException e) {
					e.printStackTrace();
					xml = xml + "<flag>failure</flag>";
				}

				xml = xml + "</response>";
			}

			else if (strCommand.equalsIgnoreCase("Update")) {
				projectname = request.getParameter("ProjectTypeDesc");
				projectalias = request.getParameter("ProjectCode");
				componentname = request.getParameter("ComponentName");
				int cmbWing = 0;
				int oid = Integer.parseInt(request.getParameter("Office"));
				if (oid == 5000) {
					cmbWing = Integer.parseInt(request.getParameter("cmbWing"));
				}
				int projectno = Integer.parseInt(request
						.getParameter("ProjectId"));
				xml = "<response><command>Update</command>";
				try {

					ps2 = connection
							.prepareStatement("update PMS_MST_PROJECTS_VIEW set project_name=?,project_alias_code=?,component_name=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,STATUS=?,WING_ID=? where office_id=? and project_id=?");

					ps2.setString(1, projectname);
					ps2.setString(2, projectalias);
					ps2.setString(3, componentname);
					ps2.setString(4, userid);
					ps2.setTimestamp(5, ts);
					ps2.setString(6, status);
					ps2.setInt(7, cmbWing);
					ps2.setInt(8, oid);
					ps2.setInt(9, projectno);
					System.out.println(status);
					int ii = ps2.executeUpdate();
					if (ii > 0) {
						xml = xml + "<flag>success</flag>";
						xml = xml + "<ProjectId>" + projectno
								+ "</ProjectId><ProjectTypeDesc>" + projectname
								+ "</ProjectTypeDesc><ProjectCode>"
								+ projectalias + "</ProjectCode><CompName>"
								+ componentname + "</CompName><status>"
								+ status + "</status>";
					}

				} catch (SQLException e) {
					System.out.println("catch. in  adding...." + e);
					xml = xml + "<flag>failure</flag>";

				}

				xml = xml + "</response>";
			}

			else if (strCommand.equalsIgnoreCase("Add")) {

				projectname = request.getParameter("ProjectTypeDesc");
				projectalias = request.getParameter("ProjectTypeCode");
				if ((projectalias.equals("")) || (projectalias == null)) {
					projectalias = null;
				}
				componentname = request.getParameter("ComponentName");
				if ((componentname.equals("")) || (componentname == null)) {
					componentname = null;
				}
				int cmbWing = 0;
				int oid = Integer.parseInt(request.getParameter("Office"));
				if (oid == 5000) {
					cmbWing = Integer.parseInt(request.getParameter("cmbWing"));
				}
				System.out.println("Office Id is:" + oid);
				xml = "<response><command>Add</command>";
				int projectnum = 0;

				try {

					PreparedStatement ps1 = connection
							.prepareStatement("select max(PROJECT_ID) as projectno from PMS_MST_PROJECTS_VIEW");
					results = ps1.executeQuery();

					if (results.next()) {

						projectnum = results.getInt("projectno") + 1;

						System.out.println("project no find is:" + projectnum);

					}
				} catch (Exception e) {
					System.out.println(e.getMessage());
				}

				try {
					String sql = "insert into PMS_MST_PROJECTS_VIEW(office_id,project_id,project_name,"
							+ " project_alias_code,component_name,STATUS,UPDATED_BY_USER_ID,UPDATED_DATE,WING_ID) values(?,?,?,?,?,?,?,?,?)";

					System.out.println(sql);

					ps2 = connection.prepareStatement(sql);
					ps2.setInt(1, oid);
					ps2.setInt(2, projectnum);
					ps2.setString(3, projectname);
					ps2.setString(4, projectalias);
					ps2.setString(5, componentname);
					ps2.setString(6, status);
					ps2.setString(7, userid);
					ps2.setTimestamp(8, ts);
					ps2.setInt(9, cmbWing);
					int ii = ps2.executeUpdate();
					System.out.println(ii);
					if (ii > 0) {
						xml = xml + "<flag>success</flag>";
						xml = xml + "<ProjectId>" + projectnum
								+ "</ProjectId><ProjectTypeDesc>" + projectname
								+ "</ProjectTypeDesc><ProjectCode>"
								+ projectalias + "</ProjectCode><CompName>"
								+ componentname + "</CompName><status>"
								+ status + "</status>";
					}

				} catch (SQLException e) {
					System.out.println("error is" + e);

					xml = xml + "<flag>failure</flag>";

				}

				xml = xml + "</response>";
			}

			else if (strCommand.equals("List")) {
				int wing_id = 0;
				xml = "<response><command>List</command>";
				try {
					ps = connection
							.prepareStatement("select EMPLOYEE_ID,OFFICE_ID,OFFICE_WING_SINO from HRM_EMP_CURRENT_WING where EMPLOYEE_ID=?");
					ps.setInt(1, empid);
					results = ps.executeQuery();
					if (results.next()) {

						wing_id = results.getInt("OFFICE_WING_SINO");
					}

					int oid = Integer.parseInt(request.getParameter("Office"));
					System.out.println("Office Id is:" + oid);

					// results2 =
					// statement.executeQuery("SELECT HRM_MST_CADRE.CADRE_ID, HRM_MST_CADRE.CADRE_NAME,HRM_MST_CADRE.CADRE_SHORT_NAME,HRM_MST_CADRE.PAY_SCALE_ID,HRM_MST_CADRE.REMARKS,HRM_MST_PAYSCALES.PAY_SCALE_ID, HRM_MST_PAYSCALES.PAY_SCALE FROM HRM_MST_CADRE,HRM_MST_PAYSCALES WHERE HRM_MST_CADRE.PAY_SCALE_ID=HRM_MST_PAYSCALES.PAY_SCALE_ID ORDER BY HRM_MST_CADRE.CADRE_ID");
					if (oid == 5000) {
						results2 = statement
								.executeQuery("select PROJECT_ID,PROJECT_NAME,PROJECT_ALIAS_CODE,COMPONENT_NAME,STATUS from PMS_MST_PROJECTS_VIEW where OFFICE_ID="
										+ oid
										+ " and WING_ID="
										+ wing_id
										+ " and STATUS='L' order by project_id");
					} else {
						results2 = statement
								.executeQuery("select PROJECT_ID,PROJECT_NAME,PROJECT_ALIAS_CODE,COMPONENT_NAME,STATUS from PMS_MST_PROJECTS_VIEW where OFFICE_ID="
										+ oid + "and STATUS='L' order by project_id ");
					}
					int project_id = 0;

					try {
						xml = xml + "<flag>success</flag>";
						while (results2.next()) {

							project_id = results2.getInt("PROJECT_ID");
							String ProjectTypeDesc1 = results2
									.getString("PROJECT_NAME");
							String ProjectTypeCode1 = results2
									.getString("PROJECT_ALIAS_CODE");
							String Component1 = results2
									.getString("COMPONENT_NAME");
							String status1 = results2.getString("status");

							// <PayName>" + PayName + "</PayName>;
							xml = xml + "<ProjectId>" + project_id
									+ "</ProjectId><ProjectTypeDesc>"
									+ ProjectTypeDesc1
									+ "</ProjectTypeDesc><ProjectCode>"
									+ ProjectTypeCode1
									+ "</ProjectCode><CompName>" + Component1
									+ "</CompName><status>" + status1
									+ "</status>";
						}
					} catch (Exception aee) {
						System.out
								.println("Exception in the getting values OF GET: "
										+ aee);
					}
					results2.close();
					response.setHeader("cache-control", "no-cache");
				} catch (Exception e1) {
					e1.printStackTrace();
					xml = xml + "<flag>failure</flag>";
				}
				xml = xml + "</response>";
			}

			System.out.println("xml is : " + xml);
			pw.write(xml);
			pw.flush();
			pw.close();

		}

		catch (Exception e) {
			System.out.println(e.getMessage());
		}

		finally {
			System.out.println("done");
			try {
				connection.setAutoCommit(true);
			} catch (SQLException sqle) {
				System.out.println("Excep" + sqle);
			}
		}

	}
}
