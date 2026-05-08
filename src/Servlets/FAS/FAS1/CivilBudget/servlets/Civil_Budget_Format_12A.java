package Servlets.FAS.FAS1.CivilBudget.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
 * Servlet implementation class Civil_Budget_Format_12A
 */
public class Civil_Budget_Format_12A extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Civil_Budget_Format_12A() {
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
		ResultSet rs2 = null;
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
		String check[] = new String[RecordCount];
		String slno[] = new String[RecordCount];

		String Vehicles[] = new String[RecordCount];
		String Pending_on_Begining_of_the_Yr_1a[] = new String[RecordCount];
		String Pending_on_Begining_of_the_Yr_1b[] = new String[RecordCount];
		String TDA_Raised_2a[] = new String[RecordCount];
		String TDA_Raised_2b[] = new String[RecordCount];
		String TDA_Raised_3a[] = new String[RecordCount];
		String TDA_Raised_3b[] = new String[RecordCount];
		String TDA_Accepted_and_Adjusted_4a[] = new String[RecordCount];
		String TDA_Accepted_and_Adjusted_4b[] = new String[RecordCount];
		String TDA_Accepted_and_Adjusted_5a[] = new String[RecordCount];
		String TDA_Accepted_and_Adjusted_5b[] = new String[RecordCount];
		String Value_of_Jobs_in_Progress_6a[] = new String[RecordCount];
		String Value_of_Jobs_in_Progress_6b[] = new String[RecordCount];
		String Value_of_Jobs_in_Progress_7a[] = new String[RecordCount];

		/* Variables Declaration */
		int check2 = 0;
		int slno2 = 0;

		String Vehicles2 = null;
		int Pending_on_Begining_of_the_Yr_1a2 = 0;
		float Pending_on_Begining_of_the_Yr_1b2 = 0.0f;
		int TDA_Raised_2a2 = 0;
		float TDA_Raised_2b2 = 0.0f;
		int TDA_Raised_3a2 = 0;
		float TDA_Raised_3b2 = 0.0f;
		int TDA_Accepted_and_Adjusted_4a2 = 0;
		float TDA_Accepted_and_Adjusted_4b2 = 0.0f;
		int TDA_Accepted_and_Adjusted_5a2 = 0;
		float TDA_Accepted_and_Adjusted_5b2 = 0.0f;
		int Value_of_Jobs_in_Progress_6a2 = 0;
		float Value_of_Jobs_in_Progress_6b2 = 0.0f;
		Date Value_of_Jobs_in_Progress_7a2 = null;
		String sd[] = new String[10];
		java.util.Date d = null;
		Calendar c;
		
		int flag = 0;
		String office_level_id = null;
		int division_id = 0;
		int circle_id = 0;
		int region_id = 0;
		int head_office_id = 0;
		try {
			con.setAutoCommit(false);
			con.clearWarnings();
			if (filter.equals("save")) {
				ps1 = con
						.prepareStatement("select UPDATED_DATE from FAS_BUDGET_CLOSURE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and FORMAT_NAME=?");
				ps1.setInt(1, cmbAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setString(3, FinancialYear);
				ps1.setString(4, "12A");
				rs2 = ps1.executeQuery();
				if (rs2.next()) {
					sendMessage(
							response,
							"Civil Budget Format-12A have Already Freezed ............ ",
							"ok", "Civil_Budget_Format_12A.jsp");
				} else {
					ps = con
							.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR from FAS_BUDGET_FORMAT_12A where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setString(3, FinancialYear);
					rs = ps.executeQuery();
					if (rs.next()) {
						flag = 1;
					} else {
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
						for (int k = 0; k < RecordCount; k++) {

							try {
								Vehicles2 = request
										.getParameter("Vehicles" + k);
							} catch (Exception e) {
								System.out
										.println("Error for getting Vehicles -->"
												+ e);
							}

							try {
								Pending_on_Begining_of_the_Yr_1a[k] = request
										.getParameter("Pending_on_Begining_of_the_Yr_1a"
												+ k);
								if (Pending_on_Begining_of_the_Yr_1a[k] != null) {
									if (Pending_on_Begining_of_the_Yr_1a[k]
											.equals("")) {
										Pending_on_Begining_of_the_Yr_1a2 = 0;
									} else {
										Pending_on_Begining_of_the_Yr_1a2 = Integer
												.parseInt(Pending_on_Begining_of_the_Yr_1a[k]);
									}
								} else {
									Pending_on_Begining_of_the_Yr_1a2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting Pending_on_Begining_of_the_Yr_1a -->"
												+ e);
							}

							try {
								Pending_on_Begining_of_the_Yr_1b[k] = request
										.getParameter("Pending_on_Begining_of_the_Yr_1b"
												+ k);
								if (Pending_on_Begining_of_the_Yr_1b[k] != null) {
									if (Pending_on_Begining_of_the_Yr_1b[k]
											.equals("")) {
										Pending_on_Begining_of_the_Yr_1b2 = 0.0f;
									} else {
										Pending_on_Begining_of_the_Yr_1b2 = Float
												.parseFloat(Pending_on_Begining_of_the_Yr_1b[k]);
									}
								} else {
									Pending_on_Begining_of_the_Yr_1b2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								TDA_Raised_2a[k] = request
										.getParameter("TDA_Raised_2a" + k);
								if (TDA_Raised_2a[k] != null) {
									if (TDA_Raised_2a[k].equals("")) {
										TDA_Raised_2a2 = 0;
									} else {
										TDA_Raised_2a2 = Integer
												.parseInt(TDA_Raised_2a[k]);
									}
								} else {
									TDA_Raised_2a2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting TDA_Raised_2a -->"
												+ e);
							}

							try {
								TDA_Raised_2b[k] = request
										.getParameter("TDA_Raised_2b" + k);
								if (TDA_Raised_2b[k] != null) {
									if (TDA_Raised_2b[k].equals("")) {
										TDA_Raised_2b2 = 0.0f;
									} else {
										TDA_Raised_2b2 = Float
												.parseFloat(TDA_Raised_2b[k]);
									}
								} else {
									TDA_Raised_2b2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								TDA_Raised_3a[k] = request
										.getParameter("TDA_Raised_3a" + k);
								if (TDA_Raised_3a[k] != null) {
									if (TDA_Raised_3a[k].equals("")) {
										TDA_Raised_3a2 = 0;
									} else {
										TDA_Raised_3a2 = Integer
												.parseInt(TDA_Raised_3a[k]);
									}
								} else {
									TDA_Raised_3a2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting TDA_Raised_3a -->"
												+ e);
							}

							try {
								TDA_Raised_3b[k] = request
										.getParameter("TDA_Raised_3b" + k);
								if (TDA_Raised_3b[k] != null) {
									if (TDA_Raised_3b[k].equals("")) {
										TDA_Raised_3b2 = 0.0f;
									} else {
										TDA_Raised_3b2 = Float
												.parseFloat(TDA_Raised_3b[k]);
									}
								} else {
									TDA_Raised_3b2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								TDA_Accepted_and_Adjusted_4a[k] = request
										.getParameter("TDA_Accepted_and_Adjusted_4a"
												+ k);
								if (TDA_Accepted_and_Adjusted_4a[k] != null) {
									if (TDA_Accepted_and_Adjusted_4a[k]
											.equals("")) {
										TDA_Accepted_and_Adjusted_4a2 = 0;
									} else {
										TDA_Accepted_and_Adjusted_4a2 = Integer
												.parseInt(TDA_Accepted_and_Adjusted_4a[k]);
									}
								} else {
									TDA_Accepted_and_Adjusted_4a2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting TDA_Accepted_and_Adjusted_4a -->"
												+ e);
							}

							try {
								TDA_Accepted_and_Adjusted_4b[k] = request
										.getParameter("TDA_Accepted_and_Adjusted_4b"
												+ k);
								if (TDA_Accepted_and_Adjusted_4b[k] != null) {
									if (TDA_Accepted_and_Adjusted_4b[k]
											.equals("")) {
										TDA_Accepted_and_Adjusted_4b2 = 0.0f;
									} else {
										TDA_Accepted_and_Adjusted_4b2 = Float
												.parseFloat(TDA_Accepted_and_Adjusted_4b[k]);
									}
								} else {
									TDA_Accepted_and_Adjusted_4b2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								TDA_Accepted_and_Adjusted_5a[k] = request
										.getParameter("TDA_Accepted_and_Adjusted_5a"
												+ k);
								if (TDA_Accepted_and_Adjusted_5a[k] != null) {
									if (TDA_Accepted_and_Adjusted_5a[k]
											.equals("")) {
										TDA_Accepted_and_Adjusted_5a2 = 0;
									} else {
										TDA_Accepted_and_Adjusted_5a2 = Integer
												.parseInt(TDA_Accepted_and_Adjusted_5a[k]);
									}
								} else {
									TDA_Accepted_and_Adjusted_5a2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting TDA_Accepted_and_Adjusted_5a -->"
												+ e);
							}

							try {
								TDA_Accepted_and_Adjusted_5b[k] = request
										.getParameter("TDA_Accepted_and_Adjusted_5b"
												+ k);
								if (TDA_Accepted_and_Adjusted_5b[k] != null) {
									if (TDA_Accepted_and_Adjusted_5b[k]
											.equals("")) {
										TDA_Accepted_and_Adjusted_5b2 = 0.0f;
									} else {
										TDA_Accepted_and_Adjusted_5b2 = Float
												.parseFloat(TDA_Accepted_and_Adjusted_5b[k]);
									}
								} else {
									TDA_Accepted_and_Adjusted_5b2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Value_of_Jobs_in_Progress_6a[k] = request
										.getParameter("Value_of_Jobs_in_Progress_6a"
												+ k);
								if (Value_of_Jobs_in_Progress_6a[k] != null) {
									if (Value_of_Jobs_in_Progress_6a[k]
											.equals("")) {
										Value_of_Jobs_in_Progress_6a2 = 0;
									} else {
										Value_of_Jobs_in_Progress_6a2 = Integer
												.parseInt(Value_of_Jobs_in_Progress_6a[k]);
									}
								} else {
									Value_of_Jobs_in_Progress_6a2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting Value_of_Jobs_in_Progress_6a -->"
												+ e);
							}

							try {
								Value_of_Jobs_in_Progress_6b[k] = request
										.getParameter("Value_of_Jobs_in_Progress_6b"
												+ k);
								if (Value_of_Jobs_in_Progress_6b[k] != null) {
									if (Value_of_Jobs_in_Progress_6b[k]
											.equals("")) {
										Value_of_Jobs_in_Progress_6b2 = 0.0f;
									} else {
										Value_of_Jobs_in_Progress_6b2 = Float
												.parseFloat(Value_of_Jobs_in_Progress_6b[k]);
									}
								} else {
									Value_of_Jobs_in_Progress_6b2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Value_of_Jobs_in_Progress_7a[k] = request
										.getParameter("Value_of_Jobs_in_Progress_7a" + k);

								if (!Value_of_Jobs_in_Progress_7a[k].equalsIgnoreCase("")) {
									sd = Value_of_Jobs_in_Progress_7a[k].split("/");
									c = new GregorianCalendar(Integer
											.parseInt(sd[2]), Integer
											.parseInt(sd[1]) - 1, Integer
											.parseInt(sd[0]));
									d = c.getTime();
									Value_of_Jobs_in_Progress_7a2 = new Date(d.getTime());
								} else {
									Value_of_Jobs_in_Progress_7a2 = null;
								}
							} catch (Exception e) {
								System.out.println(e);
							}														

							int i = 1, i1 = 0;
							try {
								ps = con
										.prepareStatement("Select max(SL_NO) from FAS_BUDGET_FORMAT_12A");
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
									.prepareStatement("insert into FAS_BUDGET_FORMAT_12A (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,VEHICLES,PENDING_ON_BEGIN_OF_THE_YR_1A,PENDING_ON_BEGIN_OF_THE_YR_1B,TDA_RAISED_2A,TDA_RAISED_2B,TDA_RAISED_3A,TDA_RAISED_3B,TDA_ACCEPTED_AND_ADJUSTED_4A,TDA_ACCEPTED_AND_ADJUSTED_4B,TDA_ACCEPTED_AND_ADJUSTED_5A,TDA_ACCEPTED_AND_ADJUSTED_5B,VALUE_OF_JOBS_IN_PROGRESS_6A,VALUE_OF_JOBS_IN_PROGRESS_6B,VALUE_OF_JOBS_IN_PROGRESS_7A,DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
							ps.setInt(1, cmbAcc_UnitCode);
							ps.setInt(2, cmbOffice_code);
							ps.setString(3, FinancialYear);
							ps.setInt(4, i);
							ps.setString(5, Vehicles2);
							ps.setInt(6, Pending_on_Begining_of_the_Yr_1a2);
							ps.setFloat(7, Pending_on_Begining_of_the_Yr_1b2);
							ps.setInt(8, TDA_Raised_2a2);
							ps.setFloat(9, TDA_Raised_2b2);
							ps.setInt(10, TDA_Raised_3a2);
							ps.setFloat(11, TDA_Raised_3b2);
							ps.setInt(12, TDA_Accepted_and_Adjusted_4a2);
							ps.setFloat(13, TDA_Accepted_and_Adjusted_4b2);
							ps.setInt(14, TDA_Accepted_and_Adjusted_5a2);
							ps.setFloat(15, TDA_Accepted_and_Adjusted_5b2);
							ps.setInt(16, Value_of_Jobs_in_Progress_6a2);
							ps.setFloat(17, Value_of_Jobs_in_Progress_6b2);
							ps.setDate(18, Value_of_Jobs_in_Progress_7a2);
							ps.setInt(19, division_id);
							ps.setInt(20, circle_id);
							ps.setInt(21, region_id);
							ps.setInt(22, head_office_id);
							ps.setString(23, office_level_id);
							ps.setString(24, userid);
							ps.setTimestamp(25, ts);
							int kk = ps.executeUpdate();
						}
						flag = 2;
					}
					con.commit();
					if (flag == 1) {
						sendMessage(
								response,
								"Records Alredy Exist for given Office for given Financial Year ............ ",
								"ok", "Civil_Budget_Format_12A.jsp");
					} else if (flag == 2) {
						sendMessage(response,
								"Records Saved Successfully ............ ",
								"ok", "Civil_Budget_Format_12A.jsp");
					}
				}
			} else if (filter.equals("view")) {
				try {
					xml = "<response><command>LoadData</command>";
					ps1 = con
							.prepareStatement("select UPDATED_DATE from FAS_BUDGET_CLOSURE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and FORMAT_NAME=?");
					ps1.setInt(1, cmbAcc_UnitCode);
					ps1.setInt(2, cmbOffice_code);
					ps1.setString(3, FinancialYear);
					ps1.setString(4, "12A");
					rs = ps1.executeQuery();
					if (rs.next()) {
						xml = xml + "<flag>Freezed</flag>";
					} else {
						ps = con
								.prepareStatement(" select SL_NO,VEHICLES,PENDING_ON_BEGIN_OF_THE_YR_1A,PENDING_ON_BEGIN_OF_THE_YR_1B,TDA_RAISED_2A,TDA_RAISED_2B,TDA_RAISED_3A,TDA_RAISED_3B,TDA_ACCEPTED_AND_ADJUSTED_4A,TDA_ACCEPTED_AND_ADJUSTED_4B,TDA_ACCEPTED_AND_ADJUSTED_5A,TDA_ACCEPTED_AND_ADJUSTED_5B,VALUE_OF_JOBS_IN_PROGRESS_6A,VALUE_OF_JOBS_IN_PROGRESS_6B,to_char(VALUE_OF_JOBS_IN_PROGRESS_7A,'dd/mm/yyyy') as VALUE_OF_JOBS_IN_PROGRESS_7A from FAS_BUDGET_FORMAT_12A where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, FinancialYear);
						rs = ps.executeQuery();
						while (rs.next()) {
							xml = xml + "<slno>" + rs.getInt("SL_NO")
									+ "</slno>";

							xml = xml + "<Vehicles>" + rs.getString("VEHICLES")
									+ "</Vehicles>";

							xml = xml
									+ "<Pending_on_Begining_of_the_Yr_1a>"
									+ rs
											.getInt("PENDING_ON_BEGIN_OF_THE_YR_1A")
									+ "</Pending_on_Begining_of_the_Yr_1a>";

							xml = xml
									+ "<Pending_on_Begining_of_the_Yr_1b>"
									+ rs
											.getFloat("PENDING_ON_BEGIN_OF_THE_YR_1B")
									+ "</Pending_on_Begining_of_the_Yr_1b>";

							xml = xml + "<TDA_Raised_2a>"
									+ rs.getInt("TDA_RAISED_2A")
									+ "</TDA_Raised_2a>";

							xml = xml + "<TDA_Raised_2b>"
									+ rs.getFloat("TDA_RAISED_2B")
									+ "</TDA_Raised_2b>";

							xml = xml + "<TDA_Raised_3a>"
									+ rs.getInt("TDA_RAISED_3A")
									+ "</TDA_Raised_3a>";

							xml = xml + "<TDA_Raised_3b>"
									+ rs.getFloat("TDA_RAISED_3B")
									+ "</TDA_Raised_3b>";

							xml = xml + "<TDA_Accepted_and_Adjusted_4a>"
									+ rs.getInt("TDA_ACCEPTED_AND_ADJUSTED_4A")
									+ "</TDA_Accepted_and_Adjusted_4a>";

							xml = xml
									+ "<TDA_Accepted_and_Adjusted_4b>"
									+ rs
											.getFloat("TDA_ACCEPTED_AND_ADJUSTED_4B")
									+ "</TDA_Accepted_and_Adjusted_4b>";

							xml = xml + "<TDA_Accepted_and_Adjusted_5a>"
									+ rs.getInt("TDA_ACCEPTED_AND_ADJUSTED_5A")
									+ "</TDA_Accepted_and_Adjusted_5a>";

							xml = xml
									+ "<TDA_Accepted_and_Adjusted_5b>"
									+ rs
											.getFloat("TDA_ACCEPTED_AND_ADJUSTED_5B")
									+ "</TDA_Accepted_and_Adjusted_5b>";

							xml = xml + "<Value_of_Jobs_in_Progress_6a>"
									+ rs.getInt("VALUE_OF_JOBS_IN_PROGRESS_6A")
									+ "</Value_of_Jobs_in_Progress_6a>";

							xml = xml
									+ "<Value_of_Jobs_in_Progress_6b>"
									+ rs
											.getFloat("VALUE_OF_JOBS_IN_PROGRESS_6B")
									+ "</Value_of_Jobs_in_Progress_6b>";

							xml = xml + "<Value_of_Jobs_in_Progress_7a>"
									+ rs.getString("VALUE_OF_JOBS_IN_PROGRESS_7A")
									+ "</Value_of_Jobs_in_Progress_7a>";
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
					int k = 0;
					for (k = 0; k < RecordCount; k++) {
						/* Check Box */
						try {
							check[k] = request.getParameter("slno_db1" + k);
							check2 = Integer.parseInt(check[k]);

						} catch (Exception e) {
							System.out
									.println("Error for getting Head_of_Account -->"
											+ e);
						}
						if (k == check2) {
							try {
								slno[k] = request.getParameter("slno_db" + k);
								if (slno[k] != null) {
									if (slno[k].equals("")) {
										slno2 = 0;
									} else {
										slno2 = Integer.parseInt(slno[k]);
									}
								} else {
									slno2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting Head_of_Account -->"
												+ e);
							}
							ps = con
									.prepareStatement(" select * from FAS_BUDGET_FORMAT_12A where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and SL_NO=?");
							ps.setInt(1, cmbAcc_UnitCode);
							ps.setInt(2, cmbOffice_code);
							ps.setString(3, FinancialYear);
							ps.setInt(4, slno2);
							rs = ps.executeQuery();
							if (rs.next()) {
								ps1 = con
										.prepareStatement(" delete from FAS_BUDGET_FORMAT_12A where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and SL_NO=?");
								ps1.setInt(1, cmbAcc_UnitCode);
								ps1.setInt(2, cmbOffice_code);
								ps1.setString(3, FinancialYear);
								ps1.setInt(4, slno2);
								int sbg = ps1.executeUpdate();
							} else {
								flag = 2;
							}
							flag = 3;
						}
					}
					if (flag == 3) {
						con.commit();
						sendMessage(response,
								"Records Deleted Successfully ............ ",
								"ok", "Civil_Budget_Format_12A.jsp");
					} else if (flag == 3) {
						con.commit();
						sendMessage(response,
								"Record Does Not Exist ............ ", "ok",
								"Civil_Budget_Format_12A.jsp");
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Deletion Failed ............ " + e, "ok",
							"Civil_Budget_Format_12A.jsp");
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
							.prepareStatement("select UPDATED_DATE from FAS_BUDGET_CLOSURE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and FORMAT_NAME=?");
					ps1.setInt(1, cmbAcc_UnitCode);
					ps1.setInt(2, cmbOffice_code);
					ps1.setString(3, FinancialYear);
					ps1.setString(4, "12A");
					rs2 = ps1.executeQuery();
					if (rs2.next()) {
						sendMessage(
								response,
								"Civil Budget Format-12A have Already Freezed ............ ",
								"ok", "Civil_Budget_Format_12A.jsp");
					} else {
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
								.prepareStatement(" select * from FAS_BUDGET_FORMAT_12A where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, FinancialYear);
						rs = ps.executeQuery();
						if (rs.next()) {
							ps1 = con
									.prepareStatement(" delete from FAS_BUDGET_FORMAT_12A where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
							ps1.setInt(1, cmbAcc_UnitCode);
							ps1.setInt(2, cmbOffice_code);
							ps1.setString(3, FinancialYear);
							ps1.executeUpdate();
							for (int k = 0; k < RecordCount; k++) {

								try {
									Vehicles2 = request.getParameter("Vehicles"
											+ k);
								} catch (Exception e) {
									System.out
											.println("Error for getting Vehicles -->"
													+ e);
								}

								try {
									Pending_on_Begining_of_the_Yr_1a[k] = request
											.getParameter("Pending_on_Begining_of_the_Yr_1a"
													+ k);
									if (Pending_on_Begining_of_the_Yr_1a[k] != null) {
										if (Pending_on_Begining_of_the_Yr_1a[k]
												.equals("")) {
											Pending_on_Begining_of_the_Yr_1a2 = 0;
										} else {
											Pending_on_Begining_of_the_Yr_1a2 = Integer
													.parseInt(Pending_on_Begining_of_the_Yr_1a[k]);
										}
									} else {
										Pending_on_Begining_of_the_Yr_1a2 = 0;
									}
								} catch (Exception e) {
									System.out
											.println("Error for getting Pending_on_Begining_of_the_Yr_1a -->"
													+ e);
								}

								try {
									Pending_on_Begining_of_the_Yr_1b[k] = request
											.getParameter("Pending_on_Begining_of_the_Yr_1b"
													+ k);
									if (Pending_on_Begining_of_the_Yr_1b[k] != null) {
										if (Pending_on_Begining_of_the_Yr_1b[k]
												.equals("")) {
											Pending_on_Begining_of_the_Yr_1b2 = 0.0f;
										} else {
											Pending_on_Begining_of_the_Yr_1b2 = Float
													.parseFloat(Pending_on_Begining_of_the_Yr_1b[k]);
										}
									} else {
										Pending_on_Begining_of_the_Yr_1b2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									TDA_Raised_2a[k] = request
											.getParameter("TDA_Raised_2a" + k);
									if (TDA_Raised_2a[k] != null) {
										if (TDA_Raised_2a[k].equals("")) {
											TDA_Raised_2a2 = 0;
										} else {
											TDA_Raised_2a2 = Integer
													.parseInt(TDA_Raised_2a[k]);
										}
									} else {
										TDA_Raised_2a2 = 0;
									}
								} catch (Exception e) {
									System.out
											.println("Error for getting TDA_Raised_2a -->"
													+ e);
								}

								try {
									TDA_Raised_2b[k] = request
											.getParameter("TDA_Raised_2b" + k);
									if (TDA_Raised_2b[k] != null) {
										if (TDA_Raised_2b[k].equals("")) {
											TDA_Raised_2b2 = 0.0f;
										} else {
											TDA_Raised_2b2 = Float
													.parseFloat(TDA_Raised_2b[k]);
										}
									} else {
										TDA_Raised_2b2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									TDA_Raised_3a[k] = request
											.getParameter("TDA_Raised_3a" + k);
									if (TDA_Raised_3a[k] != null) {
										if (TDA_Raised_3a[k].equals("")) {
											TDA_Raised_3a2 = 0;
										} else {
											TDA_Raised_3a2 = Integer
													.parseInt(TDA_Raised_3a[k]);
										}
									} else {
										TDA_Raised_3a2 = 0;
									}
								} catch (Exception e) {
									System.out
											.println("Error for getting TDA_Raised_3a -->"
													+ e);
								}

								try {
									TDA_Raised_3b[k] = request
											.getParameter("TDA_Raised_3b" + k);
									if (TDA_Raised_3b[k] != null) {
										if (TDA_Raised_3b[k].equals("")) {
											TDA_Raised_3b2 = 0.0f;
										} else {
											TDA_Raised_3b2 = Float
													.parseFloat(TDA_Raised_3b[k]);
										}
									} else {
										TDA_Raised_3b2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									TDA_Accepted_and_Adjusted_4a[k] = request
											.getParameter("TDA_Accepted_and_Adjusted_4a"
													+ k);
									if (TDA_Accepted_and_Adjusted_4a[k] != null) {
										if (TDA_Accepted_and_Adjusted_4a[k]
												.equals("")) {
											TDA_Accepted_and_Adjusted_4a2 = 0;
										} else {
											TDA_Accepted_and_Adjusted_4a2 = Integer
													.parseInt(TDA_Accepted_and_Adjusted_4a[k]);
										}
									} else {
										TDA_Accepted_and_Adjusted_4a2 = 0;
									}
								} catch (Exception e) {
									System.out
											.println("Error for getting TDA_Accepted_and_Adjusted_4a -->"
													+ e);
								}

								try {
									TDA_Accepted_and_Adjusted_4b[k] = request
											.getParameter("TDA_Accepted_and_Adjusted_4b"
													+ k);
									if (TDA_Accepted_and_Adjusted_4b[k] != null) {
										if (TDA_Accepted_and_Adjusted_4b[k]
												.equals("")) {
											TDA_Accepted_and_Adjusted_4b2 = 0.0f;
										} else {
											TDA_Accepted_and_Adjusted_4b2 = Float
													.parseFloat(TDA_Accepted_and_Adjusted_4b[k]);
										}
									} else {
										TDA_Accepted_and_Adjusted_4b2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									TDA_Accepted_and_Adjusted_5a[k] = request
											.getParameter("TDA_Accepted_and_Adjusted_5a"
													+ k);
									if (TDA_Accepted_and_Adjusted_5a[k] != null) {
										if (TDA_Accepted_and_Adjusted_5a[k]
												.equals("")) {
											TDA_Accepted_and_Adjusted_5a2 = 0;
										} else {
											TDA_Accepted_and_Adjusted_5a2 = Integer
													.parseInt(TDA_Accepted_and_Adjusted_5a[k]);
										}
									} else {
										TDA_Accepted_and_Adjusted_5a2 = 0;
									}
								} catch (Exception e) {
									System.out
											.println("Error for getting TDA_Accepted_and_Adjusted_5a -->"
													+ e);
								}

								try {
									TDA_Accepted_and_Adjusted_5b[k] = request
											.getParameter("TDA_Accepted_and_Adjusted_5b"
													+ k);
									if (TDA_Accepted_and_Adjusted_5b[k] != null) {
										if (TDA_Accepted_and_Adjusted_5b[k]
												.equals("")) {
											TDA_Accepted_and_Adjusted_5b2 = 0.0f;
										} else {
											TDA_Accepted_and_Adjusted_5b2 = Float
													.parseFloat(TDA_Accepted_and_Adjusted_5b[k]);
										}
									} else {
										TDA_Accepted_and_Adjusted_5b2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Value_of_Jobs_in_Progress_6a[k] = request
											.getParameter("Value_of_Jobs_in_Progress_6a"
													+ k);
									if (Value_of_Jobs_in_Progress_6a[k] != null) {
										if (Value_of_Jobs_in_Progress_6a[k]
												.equals("")) {
											Value_of_Jobs_in_Progress_6a2 = 0;
										} else {
											Value_of_Jobs_in_Progress_6a2 = Integer
													.parseInt(Value_of_Jobs_in_Progress_6a[k]);
										}
									} else {
										Value_of_Jobs_in_Progress_6a2 = 0;
									}
								} catch (Exception e) {
									System.out
											.println("Error for getting Value_of_Jobs_in_Progress_6a -->"
													+ e);
								}

								try {
									Value_of_Jobs_in_Progress_6b[k] = request
											.getParameter("Value_of_Jobs_in_Progress_6b"
													+ k);
									if (Value_of_Jobs_in_Progress_6b[k] != null) {
										if (Value_of_Jobs_in_Progress_6b[k]
												.equals("")) {
											Value_of_Jobs_in_Progress_6b2 = 0.0f;
										} else {
											Value_of_Jobs_in_Progress_6b2 = Float
													.parseFloat(Value_of_Jobs_in_Progress_6b[k]);
										}
									} else {
										Value_of_Jobs_in_Progress_6b2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Value_of_Jobs_in_Progress_7a[k] = request
											.getParameter("Value_of_Jobs_in_Progress_7a" + k);

									if (!Value_of_Jobs_in_Progress_7a[k].equalsIgnoreCase("")) {
										sd = Value_of_Jobs_in_Progress_7a[k].split("/");
										c = new GregorianCalendar(Integer
												.parseInt(sd[2]), Integer
												.parseInt(sd[1]) - 1, Integer
												.parseInt(sd[0]));
										d = c.getTime();
										Value_of_Jobs_in_Progress_7a2 = new Date(d.getTime());
									} else {
										Value_of_Jobs_in_Progress_7a2 = null;
									}
								} catch (Exception e) {
									System.out.println(e);
								}		

								int i = 1, i1 = 0;
								try {
									ps = con
											.prepareStatement("Select max(SL_NO) from FAS_BUDGET_FORMAT_12A");
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
										.prepareStatement("insert into FAS_BUDGET_FORMAT_12A (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,VEHICLES,PENDING_ON_BEGIN_OF_THE_YR_1A,PENDING_ON_BEGIN_OF_THE_YR_1B,TDA_RAISED_2A,TDA_RAISED_2B,TDA_RAISED_3A,TDA_RAISED_3B,TDA_ACCEPTED_AND_ADJUSTED_4A,TDA_ACCEPTED_AND_ADJUSTED_4B,TDA_ACCEPTED_AND_ADJUSTED_5A,TDA_ACCEPTED_AND_ADJUSTED_5B,VALUE_OF_JOBS_IN_PROGRESS_6A,VALUE_OF_JOBS_IN_PROGRESS_6B,VALUE_OF_JOBS_IN_PROGRESS_7A,DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
								ps.setInt(1, cmbAcc_UnitCode);
								ps.setInt(2, cmbOffice_code);
								ps.setString(3, FinancialYear);
								ps.setInt(4, i);
								ps.setString(5, Vehicles2);
								ps.setInt(6, Pending_on_Begining_of_the_Yr_1a2);
								ps.setFloat(7,
										Pending_on_Begining_of_the_Yr_1b2);
								ps.setInt(8, TDA_Raised_2a2);
								ps.setFloat(9, TDA_Raised_2b2);
								ps.setInt(10, TDA_Raised_3a2);
								ps.setFloat(11, TDA_Raised_3b2);
								ps.setInt(12, TDA_Accepted_and_Adjusted_4a2);
								ps.setFloat(13, TDA_Accepted_and_Adjusted_4b2);
								ps.setInt(14, TDA_Accepted_and_Adjusted_5a2);
								ps.setFloat(15, TDA_Accepted_and_Adjusted_5b2);
								ps.setInt(16, Value_of_Jobs_in_Progress_6a2);
								ps.setFloat(17, Value_of_Jobs_in_Progress_6b2);
								ps.setDate(18, Value_of_Jobs_in_Progress_7a2);
								ps.setInt(19, division_id);
								ps.setInt(20, circle_id);
								ps.setInt(21, region_id);
								ps.setInt(22, head_office_id);
								ps.setString(23, office_level_id);
								ps.setString(24, userid);
								ps.setTimestamp(25, ts);
								int kk = ps.executeUpdate();
							}
							con.commit();
							sendMessage(
									response,
									"Records Updated Successfully ............ ",
									"ok", "Civil_Budget_Format_12A.jsp");
						} else {
							sendMessage(response,
									"Records Does Not Exist ............ ",
									"ok", "Civil_Budget_Format_12A.jsp");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Updation Failed ............ " + e, "ok",
							"Civil_Budget_Format_12A.jsp");
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
					"ok", "Civil_Budget_Format_12A.jsp");
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
