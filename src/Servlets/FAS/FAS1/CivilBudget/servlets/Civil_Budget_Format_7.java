package Servlets.FAS.FAS1.CivilBudget.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class Civil_Budget_Format_7
 */
public class Civil_Budget_Format_7 extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Civil_Budget_Format_7() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request,
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
		if (strCommand.equalsIgnoreCase("get")) {
			xml = "<response><command>get</command>";
			int year1 = Integer.parseInt(request.getParameter("y1"));
			String date1 = "01-APR-" + year1;
			int year2 = Integer.parseInt(request.getParameter("y2"));
			String date2 = "30-MAR-" + year2;
			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));

			String sql = " SELECT  post_rank_id,post_rank_name,       postid,    sanctioned_no_of_posts,  "
					+ "filledup_posts,  diversion_to_other,  diversion_from_other,  total,  remainingposts FROM  "
					+ "(SELECT fin_year,    region_office_id,    circle_office_id,    division_office_id,    "
					+ "auditwing_office_id,    lab_office_id,    offid2,    office_id,    postid,    post_rank_id,"
					+ "    sanctioned_no_of_posts,    filledup_posts,    diversion_to_other,    "
					+ "diversion_from_other,    total,    (total-filledup_posts) AS remainingposts,    "
					+ "off_order  FROM    (SELECT fin_year,      region_office_id,      circle_office_id,      "
					+ "division_office_id,      auditwing_office_id,      lab_office_id,      offid2,      "
					+ "office_id,      postid,      post_rank_id,      sanctioned_no_of_posts,      "
					+ "filledup_posts,      diversion_to_other,      diversion_from_other,      "
					+ "(sanctioned_no_of_posts-diversion_to_other+diversion_from_other) AS total,      "
					+ "off_order    FROM      (SELECT fin_year,        region_office_id,        circle_office_id,"
					+ "        division_office_id,        auditwing_office_id,        lab_office_id,        "
					+ "offid2,        office_id,        postid,        post_rank_id,        "
					+ "DECODE(sanctioned_no_of_posts,NULL,0,sanctioned_no_of_posts) AS sanctioned_no_of_posts, "
					+ "       DECODE(filledup_posts,NULL,0,filledup_posts)                 AS filledup_posts, "
					+ "       DECODE(diversion_to_other,NULL,0,diversion_to_other)         AS diversion_to_other,"
					+ "        DECODE(diversion_from_other,NULL,0,diversion_from_other)     AS "
					+ "diversion_from_other,        off_order      FROM        (SELECT *        FROM        "
					+ "  ( SELECT office_id,            post_rank_id,            region_office_id,            "
					+ "circle_office_id,            division_office_id,            auditwing_office_id,       "
					+ "     lab_office_id,            off_order          FROM HRM_SANCTION_STRENGTH_VIEW "
					+ "         WHERE office_id IS NOT NULL          UNION          SELECT office_id,        "
					+ "    post_rank_id,            region_office_id,            circle_office_id,         "
					+ "   division_office_id,            auditwing_office_id,            lab_office_id,   "
					+ "         off_order          FROM hrm_sanction_filled_view          WHERE office_id "
					+ "IS NOT NULL          ) aa        LEFT OUTER JOIN          ( SELECT office_id AS offid2,"
					+ "            post_rank_id    AS postid,            filledup_posts          FROM "
					+ "HRM_SANCTION_FILLED_VIEW          WHERE office_id IN (?)          ) bb       "
					+ " ON aa.office_id    = bb.offid2        AND aa.post_rank_id=bb.postid        "
					+ "LEFT OUTER JOIN          (SELECT fin_year,            office_id    AS offid,           "
					+ " post_rank_id AS postrankid,            sanctioned_no_of_posts,            "
					+ "diversion_to_other,            diversion_from_other          FROM "
					+ "HRM_SANCTION_STRENGTH_VIEW          WHERE office_id IN (?)          AND fin_year   "
					+ " IN(?)          )cc        ON aa.office_id    =cc.offid        AND "
					+ "aa.post_rank_id=cc.postrankid        )      )    )  WHERE office_id  IN (?)  AND "
					+ "post_Rank_id IN    (SELECT post_rank_id FROM hrm_mst_post_Ranks    )  )xyz "
					+ "LEFT OUTER JOIN  ( SELECT post_rank_id AS psid,post_rank_name FROM "
					+ "hrm_mst_post_ranks   )xyz2 ON xyz.post_rank_id=xyz2.psid ORDER BY off_order,  "
					+ "region_office_id,  circle_office_id,  division_office_id,  auditwing_office_id,  "
					+ "lab_office_id,  post_rank_id  ";
			System.out.println(sql);

			try {
				ps1 = connection
						.prepareStatement("select UPDATED_DATE from FAS_BUDGET_CLOSURE where "
								+ "ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? "
								+ "and FORMAT_NAME=?");
				ps1.setInt(1, cmbAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setString(3, (year1) + "-" + (year2));
				ps1.setString(4, "7");
				results = ps1.executeQuery();
				if (results.next()) {
					xml = xml + "<flag>Exist</flag>";
				} else {
					ps = connection.prepareStatement(sql);

					ps.setInt(1, cmbOffice_code);
					ps.setInt(2, cmbOffice_code);
					ps.setString(3, (year1-1) + "-" + (year2-1));
					ps.setInt(4, cmbOffice_code);

					rs = ps.executeQuery();
					while (rs.next()) {
						xml = xml + "<post_rank_id>"
								+ rs.getInt("post_rank_id") + "</post_rank_id>";

						xml = xml + "<post_rank_name>"
								+ rs.getString("post_rank_name")
								+ "</post_rank_name>";

						xml = xml + "<sanctioned_no_of_posts>"
								+ rs.getInt("sanctioned_no_of_posts")
								+ "</sanctioned_no_of_posts>";

						xml = xml + "<filledup_posts>"
								+ rs.getInt("filledup_posts")
								+ "</filledup_posts>";

						xml = xml + "<diversion_to_other>"
								+ rs.getInt("diversion_to_other")
								+ "</diversion_to_other>";

						xml = xml + "<diversion_from_other>"
								+ rs.getInt("diversion_from_other")
								+ "</diversion_from_other>";

						xml = xml + "<total>" + rs.getInt("total") + "</total>";

						xml = xml + "<remainingposts>"
								+ rs.getInt("remainingposts")
								+ "</remainingposts>";

					}
					xml = xml + "<flag>success</flag>";
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

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		/**
		 * Session Checking
		 */
		HttpSession session = request.getSession(false);
		try {

			if (session == null) {

				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");
				return;
			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}

		/**
		 * Variables Declaration
		 */

		Connection con = null;
		PreparedStatement ps = null;
		PreparedStatement ps1 = null;
		ResultSet rs = null;
		response.setContentType(CONTENT_TYPE);
		response.setHeader("Cache-Control", "no-cache");
		PrintWriter out = response.getWriter();

		/**
		 * Database Connection
		 */
		try {
			ResourceBundle rs1 = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rs1.getString("Config.DSN");
			String strhostname = rs1.getString("Config.HOST_NAME");
			String strportno = rs1.getString("Config.PORT_NUMBER");
			String strsid = rs1.getString("Config.SID");
			String strdbusername = rs1.getString("Config.USER_NAME");
			String strdbpassword = rs1.getString("Config.PASSWORD");
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);
		}

		String userid = (String) session.getAttribute("UserId");
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);

		Date date = new Date(0000 - 00 - 00);
		String strCommand = "";
		String xml = "";

		/* Get Parameters */
		int cmbAcc_UnitCode = 0;
		int cmbOffice_code = 0;
		String FinancialYear = null;
		/* Get Accounting Unit ID */
		try {
			cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
		} catch (Exception e) {
			System.out.println("Error Not Getting Accounitng Unit ID --> " + e);
		}

		/* Get Accounting for Office ID */
		try {
			cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));

		} catch (Exception e) {
			System.out
					.println("Error Not Getting Accounting for Office Id --> "
							+ e);
		}

		/* Get FinancialYear */
		try {
			FinancialYear = request.getParameter("cmbFinancialYear");
		} catch (Exception e) {
			System.out.println("Error Not Getting Financial Year -->" + e);
		}

		int RecordCount = 0;
		String filter = null;
		/*
		 * Get Total Number of Records
		 */
		try {
			filter = request.getParameter("filter");
		} catch (Exception e) {
			System.out.println("Error Getting filter value ");
		}

		/*
		 * Get filter value
		 */
		try {
			RecordCount = Integer.parseInt(request.getParameter("RecordCount"));
		} catch (Exception e) {
			System.out
					.println("Error Getting Total Number of Records in TWAD Transaction ");
		}

		/* String Array Declaration */
		String Post_Rank_id[] = new String[RecordCount];
		String Sanctioned_Post[] = new String[RecordCount];
		String Diversion_to_Others[] = new String[RecordCount];
		String Diversion_from_Others[] = new String[RecordCount];
		String Total[] = new String[RecordCount];
		String Utilised[] = new String[RecordCount];
		String Vacant[] = new String[RecordCount];

		/* Variables Declaration */
		int Post_Rank_id2 = 0;
		int Sanctioned_Post2 = 0;
		int Diversion_to_Others2 = 0;
		int Diversion_from_Others2 = 0;
		int Total2 = 0;
		int Utilised2 = 0;
		int Vacant2 = 0;

		int flag = 0;
		String office_level_id = null;
		int division_id = 0;
		int circle_id = 0;
		int region_id = 0;
		int head_office_id = 0;
		String sd[] = new String[10];
		java.util.Date d = null;
		Calendar c;
		try {
			con.setAutoCommit(false);
			con.clearWarnings();
			if (filter.equals("save")) {
				ps1 = con
						.prepareStatement(" select DIVISION_OFFICE_ID,CIRCLE_OFFICE_ID,REGION_OFFICE_ID,"
								+ "OFFICE_ID as HEAD_OFFICE_ID,OFFICE_LEVEL_ID from ( SELECT ROWNUM AS slno1,"
								+ "OFFICE_LEVEL_ID,REGION_OFFICE_ID,CIRCLE_OFFICE_ID,DIVISION_OFFICE_ID FROM "
								+ "COM_MST_ALL_OFFICES_VIEW WHERE OFFICE_ID=? )x left outer join ( "
								+ "SELECT ROWNUM AS SLNO2,OFFICE_ID FROM COM_MST_ALL_OFFICES_VIEW WHERE "
								+ "OFFICE_LEVEL_ID='HO' )y on x.slno1 =y.slno2  ");
				ps1.setInt(1, cmbOffice_code);
				rs = ps1.executeQuery();
				if (rs.next()) {
					division_id = rs.getInt("DIVISION_OFFICE_ID");
					circle_id = rs.getInt("CIRCLE_OFFICE_ID");
					region_id = rs.getInt("REGION_OFFICE_ID");
					head_office_id = rs.getInt("HEAD_OFFICE_ID");
					office_level_id = rs.getString("OFFICE_LEVEL_ID");
				}
				ps = con
						.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR from FAS_BUDGET_FORMAT_7 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setString(3, FinancialYear);
				rs = ps.executeQuery();
				if (rs.next()) {
					flag = 1;
				} else {
					for (int k = 0; k < RecordCount; k++) {

						/* Post_Rank_id */
						try {
							Post_Rank_id[k] = request
									.getParameter("Post_Rank_id" + k);
							if (Post_Rank_id[k] != null) {
								if (Post_Rank_id[k].equals("")) {
									Post_Rank_id2 = 0;
								} else {
									Post_Rank_id2 = Integer
											.parseInt(Post_Rank_id[k]);
								}
							} else {
								Post_Rank_id2 = 0;
							}
						} catch (Exception e) {
							System.out
									.println("Error for getting Post_Rank_id -->"
											+ e);
						}

						/* Sanctioned_Post */
						try {
							Sanctioned_Post[k] = request
									.getParameter("Sanctioned_Post" + k);
							if (Sanctioned_Post[k] != null) {
								if (Sanctioned_Post[k].equals("")) {
									Sanctioned_Post2 = 0;
								} else {
									Sanctioned_Post2 = Integer
											.parseInt(Sanctioned_Post[k]);
								}
							} else {
								Sanctioned_Post2 = 0;
							}
						} catch (Exception e) {
							System.out
									.println("Error for getting Sanctioned_Post -->"
											+ e);
						}

						/* Diversion_to_Others */
						try {
							Diversion_to_Others[k] = request
									.getParameter("Diversion_to_Others" + k);
							if (Diversion_to_Others[k] != null) {
								if (Diversion_to_Others[k].equals("")) {
									Diversion_to_Others2 = 0;
								} else {
									Diversion_to_Others2 = Integer
											.parseInt(Diversion_to_Others[k]);
								}
							} else {
								Diversion_to_Others2 = 0;
							}
						} catch (Exception e) {
							System.out
									.println("Error for getting Diversion_to_Others -->"
											+ e);
						}

						/* Diversion_from_Others */
						try {
							Diversion_from_Others[k] = request
									.getParameter("Diversion_from_Others" + k);
							if (Diversion_from_Others[k] != null) {
								if (Diversion_from_Others[k].equals("")) {
									Diversion_from_Others2 = 0;
								} else {
									Diversion_from_Others2 = Integer
											.parseInt(Diversion_from_Others[k]);
								}
							} else {
								Diversion_from_Others2 = 0;
							}
						} catch (Exception e) {
							System.out
									.println("Error for getting Diversion_from_Others -->"
											+ e);
						}

						/* Total */
						try {
							Total[k] = request.getParameter("Total" + k);
							if (Total[k] != null) {
								if (Total[k].equals("")) {
									Total2 = 0;
								} else {
									Total2 = Integer.parseInt(Total[k]);
								}
							} else {
								Total2 = 0;
							}
						} catch (Exception e) {
							System.out.println("Error for getting Total -->"
									+ e);
						}

						/* Utilised */
						try {
							Utilised[k] = request.getParameter("Utilised" + k);
							if (Utilised[k] != null) {
								if (Utilised[k].equals("")) {
									Utilised2 = 0;
								} else {
									Utilised2 = Integer.parseInt(Utilised[k]);
								}
							} else {
								Utilised2 = 0;
							}
						} catch (Exception e) {
							System.out.println("Error for getting Utilised -->"
									+ e);
						}

						/* Vacant */
						try {
							Vacant[k] = request.getParameter("Vacant" + k);
							if (Vacant[k] != null) {
								if (Vacant[k].equals("")) {
									Vacant2 = 0;
								} else {
									Vacant2 = Integer.parseInt(Vacant[k]);
								}
							} else {
								Vacant2 = 0;
							}
						} catch (Exception e) {
							System.out.println("Error for getting Vacant -->"
									+ e);
						}

						int i = 1, i1 = 0;
						try {
							ps = con
									.prepareStatement("Select max(SL_NO) from FAS_BUDGET_FORMAT_7");
							rs = ps.executeQuery();

							if (rs.next()) {
								i1 = rs.getInt(1);
								i = i + i1;
							} else {
								i = i;
							}
						} catch (Exception e) {
							e.printStackTrace();
						}

						ps = con
								.prepareStatement("insert into FAS_BUDGET_FORMAT_7 (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,POST_RANK_ID,SANCTIONED_POST,DIVERSION_TO_OTHERS,DIVERSION_FROM_OTHERS,TOTAL,UTILISED,VACANT,DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, FinancialYear);
						ps.setInt(4, i);
						ps.setInt(5, Post_Rank_id2);
						ps.setInt(6, Sanctioned_Post2);
						ps.setInt(7, Diversion_to_Others2);
						ps.setInt(8, Diversion_from_Others2);
						ps.setInt(9, Total2);
						ps.setInt(10, Utilised2);
						ps.setInt(11, Vacant2);
						ps.setInt(12, division_id);
						ps.setInt(13, circle_id);
						ps.setInt(14, region_id);
						ps.setInt(15, head_office_id);
						ps.setString(16, office_level_id);
						ps.setString(17, userid);
						ps.setTimestamp(18, ts);

						int kk = ps.executeUpdate();
					}
					flag = 2;
				}
				con.commit();
				if (flag == 1) {
					sendMessage(
							response,
							"Records Alredy Exist for given Office for given Financial Year ............ ",
							"ok", "Civil_Budget_Format_7.jsp");
				} else if (flag == 2) {
					sendMessage(response,
							"Records Saved Successfully ............ ", "ok",
							"Civil_Budget_Format_7.jsp");
				}
			} else if (filter.equals("view")) {
				try {
					xml = "<response><command>LoadData</command>";
					ps1 = con
							.prepareStatement("select UPDATED_DATE from FAS_BUDGET_CLOSURE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and FORMAT_NAME=?");
					ps1.setInt(1, cmbAcc_UnitCode);
					ps1.setInt(2, cmbOffice_code);
					ps1.setString(3, FinancialYear);
					ps1.setString(4, "7");
					rs = ps1.executeQuery();
					if (rs.next()) {
						xml = xml + "<flag>Exist</flag>";
					} else {
						ps = con
								.prepareStatement(" select SL_NO,POST_RANK_ID,post_rank_name,SANCTIONED_POST,"
										+ "DIVERSION_TO_OTHERS,DIVERSION_FROM_OTHERS,TOTAL,UTILISED,VACANT from "
										+ "(select SL_NO,POST_RANK_ID,SANCTIONED_POST,DIVERSION_TO_OTHERS,"
										+ "DIVERSION_FROM_OTHERS,TOTAL, UTILISED,VACANT from FAS_BUDGET_FORMAT_7 "
										+ "where  ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and "
										+ "FINANCIAL_YEAR=?)x left outer join (SELECT post_rank_id AS psid,"
										+ "post_rank_name FROM hrm_mst_post_ranks)y on x.POST_RANK_ID =y.psid "
										+ "order by SL_NO ");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, FinancialYear);
						rs = ps.executeQuery();
						while (rs.next()) {
							xml = xml + "<post_rank_id>"
									+ rs.getInt("POST_RANK_ID")
									+ "</post_rank_id>";

							xml = xml + "<post_rank_name>"
									+ rs.getString("post_rank_name")
									+ "</post_rank_name>";

							xml = xml + "<sanctioned_no_of_posts>"
									+ rs.getInt("SANCTIONED_POST")
									+ "</sanctioned_no_of_posts>";

							xml = xml + "<filledup_posts>"
									+ rs.getInt("UTILISED")
									+ "</filledup_posts>";

							xml = xml + "<diversion_to_other>"
									+ rs.getInt("DIVERSION_TO_OTHERS")
									+ "</diversion_to_other>";

							xml = xml + "<diversion_from_other>"
									+ rs.getInt("DIVERSION_FROM_OTHERS")
									+ "</diversion_from_other>";

							xml = xml + "<total>" + rs.getInt("TOTAL")
									+ "</total>";

							xml = xml + "<remainingposts>"
									+ rs.getInt("VACANT")
									+ "</remainingposts>";

						}

						xml = xml + "<flag>success</flag>";
					}
				} catch (Exception e) {
					e.printStackTrace();
					xml = xml + "<flag>failure</flag>";
				}
				xml = xml + "</response>";
				out.write(xml);
				System.out.println(xml);
			} else if (filter.equals("delete")) {
				try {
					ps = con
							.prepareStatement(" select * from FAS_BUDGET_FORMAT_7 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setString(3, FinancialYear);
					rs = ps.executeQuery();
					if (rs.next()) {
						ps1 = con
								.prepareStatement(" delete from FAS_BUDGET_FORMAT_7 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
						ps1.setInt(1, cmbAcc_UnitCode);
						ps1.setInt(2, cmbOffice_code);
						ps1.setString(3, FinancialYear);
						int sbg = ps1.executeUpdate();
						if (sbg > 0) {
							con.commit();
							sendMessage(
									response,
									"Records Deleted Successfully ............ ",
									"ok", "Civil_Budget_Format_7.jsp");
						} else {
							sendMessage(response,
									"Records Deletion Failed ............ ",
									"ok", "Civil_Budget_Format_7.jsp");
						}
					} else {
						sendMessage(response,
								"Records Does Not Exist ............ ", "ok",
								"Civil_Budget_Format_7.jsp");
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Deletion Failed ............ " + e, "ok",
							"Civil_Budget_Format_7.jsp");
					try {
						con.rollback();
						con.setAutoCommit(true);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}

			} else if (filter.equals("update")) {
				try {
					ps1 = con
							.prepareStatement(" select DIVISION_OFFICE_ID,CIRCLE_OFFICE_ID,REGION_OFFICE_ID,"
									+ "OFFICE_ID as HEAD_OFFICE_ID,OFFICE_LEVEL_ID from ( SELECT ROWNUM AS slno1,"
									+ "OFFICE_LEVEL_ID,REGION_OFFICE_ID,CIRCLE_OFFICE_ID,DIVISION_OFFICE_ID FROM "
									+ "COM_MST_ALL_OFFICES_VIEW WHERE OFFICE_ID=? )x left outer join ( "
									+ "SELECT ROWNUM AS SLNO2,OFFICE_ID FROM COM_MST_ALL_OFFICES_VIEW WHERE "
									+ "OFFICE_LEVEL_ID='HO' )y on x.slno1 =y.slno2  ");
					ps1.setInt(1, cmbOffice_code);
					rs = ps1.executeQuery();
					if (rs.next()) {
						division_id = rs.getInt("DIVISION_OFFICE_ID");
						circle_id = rs.getInt("CIRCLE_OFFICE_ID");
						region_id = rs.getInt("REGION_OFFICE_ID");
						head_office_id = rs.getInt("HEAD_OFFICE_ID");
						office_level_id = rs.getString("OFFICE_LEVEL_ID");
					}

					ps = con
							.prepareStatement(" select * from FAS_BUDGET_FORMAT_7 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setString(3, FinancialYear);
					rs = ps.executeQuery();
					if (rs.next()) {
						ps1 = con
								.prepareStatement(" delete from FAS_BUDGET_FORMAT_7 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
						ps1.setInt(1, cmbAcc_UnitCode);
						ps1.setInt(2, cmbOffice_code);
						ps1.setString(3, FinancialYear);
						ps1.executeUpdate();
						for (int k = 0; k < RecordCount; k++) {

							/* Post_Rank_id */
							try {
								Post_Rank_id[k] = request
										.getParameter("Post_Rank_id" + k);
								if (Post_Rank_id[k] != null) {
									if (Post_Rank_id[k].equals("")) {
										Post_Rank_id2 = 0;
									} else {
										Post_Rank_id2 = Integer
												.parseInt(Post_Rank_id[k]);
									}
								} else {
									Post_Rank_id2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting Post_Rank_id -->"
												+ e);
							}

							/* Sanctioned_Post */
							try {
								Sanctioned_Post[k] = request
										.getParameter("Sanctioned_Post" + k);
								if (Sanctioned_Post[k] != null) {
									if (Sanctioned_Post[k].equals("")) {
										Sanctioned_Post2 = 0;
									} else {
										Sanctioned_Post2 = Integer
												.parseInt(Sanctioned_Post[k]);
									}
								} else {
									Sanctioned_Post2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting Sanctioned_Post -->"
												+ e);
							}

							/* Diversion_to_Others */
							try {
								Diversion_to_Others[k] = request
										.getParameter("Diversion_to_Others" + k);
								if (Diversion_to_Others[k] != null) {
									if (Diversion_to_Others[k].equals("")) {
										Diversion_to_Others2 = 0;
									} else {
										Diversion_to_Others2 = Integer
												.parseInt(Diversion_to_Others[k]);
									}
								} else {
									Diversion_to_Others2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting Diversion_to_Others -->"
												+ e);
							}

							/* Diversion_from_Others */
							try {
								Diversion_from_Others[k] = request
										.getParameter("Diversion_from_Others"
												+ k);
								if (Diversion_from_Others[k] != null) {
									if (Diversion_from_Others[k].equals("")) {
										Diversion_from_Others2 = 0;
									} else {
										Diversion_from_Others2 = Integer
												.parseInt(Diversion_from_Others[k]);
									}
								} else {
									Diversion_from_Others2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting Diversion_from_Others -->"
												+ e);
							}

							/* Total */
							try {
								Total[k] = request.getParameter("Total" + k);
								if (Total[k] != null) {
									if (Total[k].equals("")) {
										Total2 = 0;
									} else {
										Total2 = Integer.parseInt(Total[k]);
									}
								} else {
									Total2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting Total -->"
												+ e);
							}

							/* Utilised */
							try {
								Utilised[k] = request.getParameter("Utilised"
										+ k);
								if (Utilised[k] != null) {
									if (Utilised[k].equals("")) {
										Utilised2 = 0;
									} else {
										Utilised2 = Integer
												.parseInt(Utilised[k]);
									}
								} else {
									Utilised2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting Utilised -->"
												+ e);
							}

							/* Vacant */
							try {
								Vacant[k] = request.getParameter("Vacant" + k);
								if (Vacant[k] != null) {
									if (Vacant[k].equals("")) {
										Vacant2 = 0;
									} else {
										Vacant2 = Integer.parseInt(Vacant[k]);
									}
								} else {
									Vacant2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting Vacant -->"
												+ e);
							}

							int i = 1, i1 = 0;
							try {
								ps = con
										.prepareStatement("Select max(SL_NO) from FAS_BUDGET_FORMAT_7");
								rs = ps.executeQuery();

								if (rs.next()) {
									i1 = rs.getInt(1);
									i = i + i1;
								} else {
									i = i;
								}
							} catch (Exception e) {
								e.printStackTrace();
							}

							ps = con
									.prepareStatement("insert into FAS_BUDGET_FORMAT_7 (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,POST_RANK_ID,SANCTIONED_POST,DIVERSION_TO_OTHERS,DIVERSION_FROM_OTHERS,TOTAL,UTILISED,VACANT,DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
							ps.setInt(1, cmbAcc_UnitCode);
							ps.setInt(2, cmbOffice_code);
							ps.setString(3, FinancialYear);
							ps.setInt(4, i);
							ps.setInt(5, Post_Rank_id2);
							ps.setInt(6, Sanctioned_Post2);
							ps.setInt(7, Diversion_to_Others2);
							ps.setInt(8, Diversion_from_Others2);
							ps.setInt(9, Total2);
							ps.setInt(10, Utilised2);
							ps.setInt(11, Vacant2);
							ps.setInt(12, division_id);
							ps.setInt(13, circle_id);
							ps.setInt(14, region_id);
							ps.setInt(15, head_office_id);
							ps.setString(16, office_level_id);
							ps.setString(17, userid);
							ps.setTimestamp(18, ts);

							int kk = ps.executeUpdate();
						}
						con.commit();
						sendMessage(response,
								"Records Updated Successfully ............ ",
								"ok", "Civil_Budget_Format_7.jsp");
					} else {
						sendMessage(response,
								"Records Does Not Exist ............ ", "ok",
								"Civil_Budget_Format_7.jsp");
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Updation Failed ............ " + e, "ok",
							"Civil_Budget_Format_7.jsp");
					try {
						con.rollback();
						con.setAutoCommit(true);
					} catch (SQLException e1) {
						e1.printStackTrace();
					}
				}
			}

		} catch (Exception e) {
			try {
				con.rollback();
				con.setAutoCommit(true);
			} catch (SQLException e1) {
				e1.printStackTrace();
			}
			e.printStackTrace();
			sendMessage(response, "Records Not Inserted ............ " + e,
					"ok", "Civil_Budget_Format_7.jsp");
		}

	}

	private void sendMessage(HttpServletResponse response, String msg,
			String bType, String jsp) {
		try {
			String url = "org/FAS/FAS1/CivilBudget/jsps/MessengerOkBack.jsp?message="
					+ msg + "&button=" + bType + "&jspname=" + jsp;
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
