package Servlets.FAS.FAS1.ContractorsInfoSys.servlets;

import java.text.SimpleDateFormat;
import javax.servlet.*;
import javax.servlet.http.*;
import java.io.PrintWriter;
import java.io.IOException;
import java.sql.*;
import java.util.ResourceBundle;

// Servlet For PMS_ReqNewRegn.jsp
public class NewReqRegn_Servlet extends HttpServlet {

	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection connection = null;
		Statement statement = null;
		PreparedStatement ps = null;

		// opening connection to the database
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

		PrintWriter pw = response.getWriter();
		response.setContentType("text/html");
		response.setHeader("cache-control", "no-cache");
		HttpSession session = request.getSession(false);

		System.out.println("servlet called for insertion and updation");

		// Declaring the Variables
		int txtOffID = 0, txtResNo = 0, txtClass = 0, year = 0;
		int txtContID = 0, cmbWing = 0; // changes...
		// java.sql.Date txtDate=null,txtDate_Upto=null;
		String txtContName = "", txtadd = "", txtPhone = "", txtEmail = "", State = "", txtAlias = "", txtJuris = "", txtstatus = "";
		String cmd = null;
		java.sql.Date date2 = null;
		java.text.SimpleDateFormat sdf1 = null;
		try {

			try {
				txtContID = Integer.parseInt(request.getParameter("txtContID")); // changes..

			} catch (Exception e) {
				System.out.println("exception in getting txtContID");
			}
			try {
				cmbWing = Integer.parseInt(request.getParameter("cmbWing")); // changes..

			} catch (Exception e) {
				System.out.println("exception in getting cmbWing");
			}

			System.out.println("txtContID:" + txtContID);
			cmd = request.getParameter("command");
			System.out.println("command is" + cmd);
			txtContName = request.getParameter("txtContName");
			System.out.println("txtContName:" + txtContName);

			txtadd = request.getParameter("txtadd");
			System.out.println("txtadd:" + txtadd);

			txtPhone = request.getParameter("txtPhone");
			System.out.println("txtPhone:" + txtPhone);

			txtEmail = request.getParameter("txtEmail");
			System.out.println("txtEmail:" + txtEmail);

			State = request.getParameter("State");
			System.out.println("State:" + State);

			txtAlias = request.getParameter("txtAlias");
			System.out.println("txtAlias:" + txtAlias);
			txtstatus = request.getParameter("txtstatus");
			System.out.println("txtstatus:" + txtstatus);
			String regdate = request.getParameter("txtDate");
			String[] str = regdate.split("/");
			year = Integer.parseInt(str[2]);
			System.out.println("Year :" + year);

			txtOffID = Integer.parseInt(request.getParameter("txtOffID"));
			System.out.println("txtOffID:" + txtOffID);

			txtResNo = Integer.parseInt(request.getParameter("txtResNo"));
			System.out.println("txtResNo:" + txtResNo);

			txtClass = Integer.parseInt(request.getParameter("txtClass"));
			System.out.println("txtClass:" + txtClass);

			txtJuris = (request.getParameter("cmbjurisdiction"));
			System.out.println("cmbjurisdiction:" + txtJuris);

		} catch (Exception e) {
			System.out.println("exce **** " + e);
		}

		// first date field for Date_creation field in the form
		String dateString1 = request.getParameter("txtDate");
		System.out.println("date in String" + dateString1);
		SimpleDateFormat dateFormat = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date d1;
		try {
			d1 = dateFormat.parse(dateString1);
			dateFormat.applyPattern("yyyy-MM-dd");
			dateString1 = dateFormat.format(d1);
		} catch (Exception e) {
			System.out.println("exception in date1");
			e.printStackTrace();
		}

		java.sql.Date date1 = java.sql.Date.valueOf(dateString1);
		System.out.println(date1);

		java.text.SimpleDateFormat sdf = new java.text.SimpleDateFormat(
				"dd/MM/yyyy");
		String DateTo = sdf.format(date1);
		System.out.println(DateTo);

		System.out.println("first date is ok....................");
		// Second Date field for Renewal due on
		String dateString2 = request.getParameter("txtDate_Upto");
		System.out.println(dateString2);
		SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
		java.util.Date d2;
		if (dateString2 != null || !(dateString2.equals(""))) {
			try {
				d2 = dateFormat1.parse(dateString2);
				System.out.println("Parsed date is " + d2);
				dateFormat1.applyPattern("yyyy-MM-dd");
				dateString2 = dateFormat1.format(d2);
			} catch (Exception e) {
				System.out.println("exception in date2");
				e.printStackTrace();
			}

			date2 = java.sql.Date.valueOf(dateString2);
			System.out.println(date2);
			sdf1 = new java.text.SimpleDateFormat("dd/MM/yyyy");
			String DateTo1 = sdf1.format(date2);
			System.out.println(DateTo1);
		} else {
			java.util.Date dd = new Date(System.currentTimeMillis());
			System.out.println("ddddddddddddddddd" + dd);
			// date2 = java.sql.Date.valueOf(dateString2);
			date2 = java.sql.Date.valueOf(sdf1.format(dd));
		}
		if (cmd.equalsIgnoreCase("Add")) {
			try {

				String sql = "select OFFICE_ID from  PMS_CONT_REQUEST_REGN where OFFICE_ID=? and REGN_YEAR =? and REGN_SLNO=? ";
				ps = connection.prepareStatement(sql);
				ps.setInt(1, txtOffID);
				ps.setInt(2, year);
				ps.setInt(3, txtResNo);
				ResultSet rs = ps.executeQuery();
				if (rs.next()) {
					pw.write("<html>");
					pw.write("<head></head>");
					pw.write("<body>");
					pw
							.write("<table border=\"1\" width=\"92%\" bgcolor=\"yellow\" align=\"center\"><tr>");
					pw
							.write("<td><center><b>Sorry Record not inserted. Registration No. is already exist.</center></b></td></tr></table>");
					pw
							.print("<center><input type=\"button\" onClick=\"history.go(-1)\" value=\"Back\"/>");
					pw
							.write("<input type='button' value='Exit' onClick=window.close();></center>");

					pw.write("</body></html>");
				} else {
					// changes...
					ps = connection
							.prepareStatement("select max(CONTRACTOR_ID) as maxContra_ID from  PMS_CONT_REQUEST_REGN");
					rs = ps.executeQuery();
					if (rs.next()) {
						System.out.println("maxContra_ID"
								+ rs.getInt("maxContra_ID"));
						txtContID = rs.getInt("maxContra_ID");
						if (txtContID > 0)
							txtContID = txtContID + 1;
						else
							txtContID = 1;
					}
					System.out.println(txtContID);
					ps.close();
					rs.close();
					// corrected...
					sql = "insert into PMS_CONT_REQUEST_REGN(OFFICE_ID,REGN_YEAR,REGN_SLNO,"
							+ "DATE_OF_REGN,CONTRACTOR_NAME,ADDRESS,PHONE,EMAIL,REGN_CLASS_ID,"
							+ "REGN_STATE_COVERAGE,REGN_VALID_UPTO,REGN_ALIAS_CODE,UPDATED_BY_USER_ID,"
							+ "UPDATED_DATE,CONTRACTOR_ID,jurisdiction,STATUS,WING_ID) ";
					sql = sql + " values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
					System.out.println(sql);
					try {

						session = request.getSession(false);
						String updatedby = (String) session
								.getAttribute("UserId");
						long l = System.currentTimeMillis();
						Timestamp ts = new Timestamp(l);

						ps = connection.prepareStatement(sql);
						ps.setInt(1, txtOffID);
						ps.setInt(2, year);
						ps.setInt(3, txtResNo);
						ps.setDate(4, date1);
						ps.setString(5, txtContName);
						ps.setString(6, txtadd);
						ps.setString(7, txtPhone);
						ps.setString(8, txtEmail);
						ps.setInt(9, txtClass);
						ps.setString(10, State);
						System.out.println("date2......." + date2);
						ps.setDate(11, date2);
						ps.setString(12, txtAlias);
						ps.setString(13, updatedby);
						ps.setTimestamp(14, ts);
						ps.setInt(15, txtContID);
						ps.setString(16, txtJuris);
						ps.setString(17, txtstatus);
						ps.setInt(18, cmbWing);
						ps.executeUpdate();
						System.out.println("success");

						pw.write("<html>");
						pw.write("<head></head>");
						pw.write("<body>");
						pw
								.write("<table border=\"1\" width=\"92%\" bgcolor=\"yellow\" align=\"center\"><tr>");
						pw
								.write("<td><center><b>Record inserted Successfully</center></b></td></tr></table>");

						pw
								.print("<center><input type=\"button\" onClick=\"window.open(\'"
										+ request.getContextPath()
										+ "/org/PMS/PMS1/ContractorsInfoSys/jsps/PMS_ReqNewRegn.jsp\',\'_parent\','');\" value=\"Back\"/>");
						pw
								.write("<input type='button' value='Exit' onClick=window.close();></center>");

						pw.write("</body></html>");
					}

					catch (Exception s) {
						System.out.println("Exception:" + s);

						pw.write("<html>");
						pw.write("<head></head>");
						pw.write("<body>");
						pw
								.write("<table border=\"1\" width=\"92%\" bgcolor=\"yellow\" align=\"center\"><tr>");
						pw
								.write("<td><center><b>Sorry Record not inserted</center></b></td></tr></table>");
						pw
								.print("<center><input type=\"button\" onClick=\"history.go(-1)\" value=\"Back\"/>");
						pw
								.write("<input type='button' value='Exit' onClick=window.close();></center>");

						pw.write("</body></html>");

					}
				}
			} catch (Exception e3) {
				System.out.println("exce ****2 " + e3);

				pw.write("<html>");
				pw.write("<head></head>");
				pw.write("<body>");
				pw
						.write("<table border=\"1\" width=\"92%\" bgcolor=\"yellow\" align=\"center\"><tr>");
				pw
						.write("<td><center><b>Sorry Record not inserted</center></b></td></tr></table>");
				pw
						.print("<center><input type=\"button\" onClick=\"history.go(-1)\" value=\"Back\"/>");
				pw
						.write("<input type='button' value='Exit' onClick=window.close();></center>");

				pw.write("</body></html>");

			}
		}

		else if (cmd.equalsIgnoreCase("Update")) {
			try {

				String sql = null;

				// corrected...
				sql = "update PMS_CONT_REQUEST_REGN"
						+ " set OFFICE_ID=?,REGN_YEAR=?,REGN_SLNO=?,DATE_OF_REGN=?,CONTRACTOR_NAME=?,ADDRESS=?,"
						+ " PHONE=?,EMAIL=?,REGN_CLASS_ID=?,REGN_STATE_COVERAGE=?,REGN_VALID_UPTO=?,REGN_ALIAS_CODE=?,"
						+ " UPDATED_BY_USER_ID=?,UPDATED_DATE=?,jurisdiction=?,STATUS=?,WING_ID=? where contractor_id=?";
				System.out.println(sql);
				try {

					session = request.getSession(false);
					String updatedby = (String) session.getAttribute("UserId");
					long l = System.currentTimeMillis();
					Timestamp ts = new Timestamp(l);

					ps = connection.prepareStatement(sql);
					ps.setInt(1, txtOffID);
					ps.setInt(2, year);
					ps.setInt(3, txtResNo);
					ps.setDate(4, date1);
					ps.setString(5, txtContName);
					ps.setString(6, txtadd);
					ps.setString(7, txtPhone);
					ps.setString(8, txtEmail);
					ps.setInt(9, txtClass);
					ps.setString(10, State);
					System.out.println("date2......." + date2);
					ps.setDate(11, date2);
					ps.setString(12, txtAlias);
					ps.setString(13, updatedby);
					ps.setTimestamp(14, ts);
					ps.setString(15, txtJuris);
					ps.setString(16, txtstatus);
					ps.setInt(17, cmbWing);
					ps.setInt(18, txtContID);
					int x = ps.executeUpdate();
					System.out.println("success");

					pw.write("<html>");
					pw.write("<head></head>");
					pw.write("<body>");
					pw
							.write("<table border=\"1\" width=\"92%\" bgcolor=\"yellow\" align=\"center\"><tr>");
					pw
							.write("<td><center><b>Record Updated Successfully</center></b></td></tr></table>");

					pw
							.print("<center><input type=\"button\" onClick=\"window.open(\'"
									+ request.getContextPath()
									+ "/org/PMS/PMS1/ContractorsInfoSys/jsps/PMS_ReqNewRegn.jsp\',\'_parent\','');\" value=\"Back\"/>");
					pw
							.write("<input type='button' value='Exit' onClick=window.close();></center>");

					pw.write("</body></html>");
				}

				catch (Exception s) {
					System.out.println("Exception:" + s);

					pw.write("<html>");
					pw.write("<head></head>");
					pw.write("<body>");
					pw
							.write("<table border=\"1\" width=\"92%\" bgcolor=\"yellow\" align=\"center\"><tr>");
					pw
							.write("<td><center><b>Sorry Record not Updated</center></b></td></tr></table>");
					pw
							.print("<center><input type=\"button\" onClick=\"history.go(-1)\" value=\"Back\"/>");
					pw
							.write("<input type='button' value='Exit' onClick=window.close();></center>");

					pw.write("</body></html>");

				}
				// }
			} catch (Exception e3) {
				System.out.println("exce ****2 " + e3);

				pw.write("<html>");
				pw.write("<head></head>");
				pw.write("<body>");
				pw
						.write("<table border=\"1\" width=\"92%\" bgcolor=\"yellow\" align=\"center\"><tr>");
				pw
						.write("<td><center><b>Sorry Record not updated</center></b></td></tr></table>");
				pw
						.print("<center><input type=\"button\" onClick=\"history.go(-1)\" value=\"Back\"/>");
				pw
						.write("<input type='button' value='Exit' onClick=window.close();></center>");

				pw.write("</body></html>");

			}
		} else if (cmd.equalsIgnoreCase("Cancel")) {
			try {

				String sql = null;

				// corrected...
				sql = "UPDATE PMS_CONT_REQUEST_REGN SET STATUS='C',UPDATED_BY_USER_ID=?,UPDATED_DATE=? where contractor_id=?";
				System.out.println(sql);
				try {

					session = request.getSession(false);
					String updatedby = (String) session.getAttribute("UserId");
					long l = System.currentTimeMillis();
					Timestamp ts = new Timestamp(l);

					ps = connection.prepareStatement(sql);
					ps.setString(1, updatedby);
					ps.setTimestamp(2, ts);
					ps.setInt(3, txtContID);
					/*
					 * ps.setInt(1,txtOffID); ps.setInt(2,year);
					 * ps.setInt(3,txtResNo); ps.setDate(4,date1);
					 * ps.setString(5,txtContName); ps.setString(6,txtadd);
					 * ps.setString(7,txtPhone); ps.setString(8,txtEmail);
					 * ps.setInt(9,txtClass); ps.setString(10,State);
					 * System.out.println("date2......."+date2);
					 * ps.setDate(11,date2); ps.setString(12,txtAlias);
					 * ps.setString(13,updatedby); ps.setTimestamp(14,ts);
					 * ps.setString(15,txtJuris);
					 */

					int x = ps.executeUpdate();
					System.out.println("success");

					pw.write("<html>");
					pw.write("<head></head>");
					pw.write("<body>");
					pw
							.write("<table border=\"1\" width=\"92%\" bgcolor=\"yellow\" align=\"center\"><tr>");
					pw
							.write("<td><center><b>Record Canceled Successfully</center></b></td></tr></table>");

					pw
							.print("<center><input type=\"button\" onClick=\"window.open(\'"
									+ request.getContextPath()
									+ "/org/PMS/PMS1/ContractorsInfoSys/jsps/PMS_ReqNewRegn.jsp\',\'_parent\','');\" value=\"Back\"/>");
					pw
							.write("<input type='button' value='Exit' onClick=window.close();></center>");

					pw.write("</body></html>");
				}

				catch (Exception s) {
					System.out.println("Exception:" + s);

					pw.write("<html>");
					pw.write("<head></head>");
					pw.write("<body>");
					pw
							.write("<table border=\"1\" width=\"92%\" bgcolor=\"yellow\" align=\"center\"><tr>");
					pw
							.write("<td><center><b>Sorry Record not Deleted</center></b></td></tr></table>");
					pw
							.print("<center><input type=\"button\" onClick=\"history.go(-1)\" value=\"Back\"/>");
					pw
							.write("<input type='button' value='Exit' onClick=window.close();></center>");

					pw.write("</body></html>");

				}
				// }
			} catch (Exception e3) {
				System.out.println("exce ****2 " + e3);

				pw.write("<html>");
				pw.write("<head></head>");
				pw.write("<body>");
				pw
						.write("<table border=\"1\" width=\"92%\" bgcolor=\"yellow\" align=\"center\"><tr>");
				pw
						.write("<td><center><b>Sorry Record not deleted</center></b></td></tr></table>");
				pw
						.print("<center><input type=\"button\" onClick=\"history.go(-1)\" value=\"Back\"/>");
				pw
						.write("<input type='button' value='Exit' onClick=window.close();></center>");

				pw.write("</body></html>");

			}
		}
		pw.close();

	}

	public void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		Connection connection = null;
		Statement statement = null;
		PreparedStatement ps = null;
		ResultSet rst = null;
		String xml = "";
		// opening connection to the database
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

		PrintWriter pw = response.getWriter();
		response.setContentType("text/xml");
		response.setHeader("cache-control", "no-cache");
		HttpSession session = request.getSession(false);

		String cmd = request.getParameter("command");
		System.out.println("command is" + cmd);

		int txtOffID = Integer.parseInt(request.getParameter("txtOffID"));
		System.out.println("txtOffID:" + txtOffID);
		String sql5 = "";
		int cnt = 0;
		int cmbWing = 0;
		xml = "<response>";
		if (cmd.equalsIgnoreCase("loadGrid")) {
			if (txtOffID == 5000) {
				cmbWing = Integer.parseInt(request.getParameter("cmbWing"));
			}
			try {
				xml += "<command>loadGrid</command>";
				if (txtOffID == 5000) {
					sql5 = "select REGN_ALIAS_CODE,REGN_STATE_COVERAGE,regn_year,regn_slno,to_char(date_of_regn,'dd/mm/yyyy')as date_of_regn,contractor_id,contractor_name,address,phone,email,regn_class_id,jurisdiction,to_char(regn_valid_upto,'dd/mm/yyyy')as regn_valid_upto,status,WING_ID from pms_cont_request_regn where office_id=? and WING_ID=?";

					ps = connection.prepareStatement(sql5);
					ps.setInt(1, txtOffID);
					ps.setInt(2, cmbWing);
					rst = ps.executeQuery();
				} else {
					sql5 = "select REGN_ALIAS_CODE,REGN_STATE_COVERAGE,regn_year,regn_slno,to_char(date_of_regn,'dd/mm/yyyy')as date_of_regn,contractor_id,contractor_name,address,phone,email,regn_class_id,jurisdiction,to_char(regn_valid_upto,'dd/mm/yyyy')as regn_valid_upto,status,WING_ID from pms_cont_request_regn where office_id=?";
					ps = connection.prepareStatement(sql5);
					ps.setInt(1, txtOffID);
					rst = ps.executeQuery();
				}

				while (rst.next()) {
					cnt++;
					xml = xml + "<contractor_id>"
							+ rst.getString("contractor_id")
							+ "</contractor_id>";
					xml = xml + "<contractor_name>"
							+ rst.getString("contractor_name")
							+ "</contractor_name>";
					xml = xml + "<address>" + rst.getString("address")
							+ "</address>";
					xml = xml + "<regn_slno>" + rst.getString("regn_slno")
							+ "</regn_slno>";
					xml = xml + "<date_of_regn>"
							+ rst.getString("date_of_regn") + "</date_of_regn>";
					xml = xml + "<regn_class_id>"
							+ rst.getString("regn_class_id")
							+ "</regn_class_id>";
					xml = xml + "<jurisdiction>"
							+ rst.getString("jurisdiction") + "</jurisdiction>";
					System.out.println(" Phone number :::: "
							+ rst.getString("phone"));
					if (rst.getString("phone") == null)
						xml = xml + "<phone>-</phone>";
					else if (rst.getString("phone").equals(""))
						xml = xml + "<phone>-</phone>";
					else
						xml = xml + "<phone>" + rst.getString("phone")
								+ "</phone>";

					xml = xml + "<email>" + rst.getString("email") + "</email>";
					xml = xml + "<regn_valid_upto>"
							+ rst.getString("regn_valid_upto")
							+ "</regn_valid_upto>";
					xml = xml + "<REGN_STATE_COVERAGE>"
							+ rst.getString("REGN_STATE_COVERAGE")
							+ "</REGN_STATE_COVERAGE>";
					xml = xml + "<REGN_ALIAS_CODE>"
							+ rst.getString("REGN_ALIAS_CODE")
							+ "</REGN_ALIAS_CODE>";
					xml = xml + "<status>" + rst.getString("status")
							+ "</status>";
					xml = xml + "<wing_id>" + rst.getInt("WING_ID")
							+ "</wing_id>";
				}
				if (cnt > 0)
					xml = xml + "<flag>success</flag>";
				else
					xml = xml + "<flag>failure</flag>";
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		}
		xml = xml + "</response>";
		System.out.println("XML  ::: " + xml);
		pw.println(xml);
	}

}
