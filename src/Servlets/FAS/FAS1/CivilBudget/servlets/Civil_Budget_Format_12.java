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
 * Servlet implementation class Civil_Budget_Format_12
 */
public class Civil_Budget_Format_12 extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Civil_Budget_Format_12() {
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
		String Jobs_Pending[] = new String[RecordCount];
		String Previous_Year_Jobs_Entrusted[] = new String[RecordCount];
		String Current_Year_Jobs_Entrusted[] = new String[RecordCount];
		String Previous_Year_Jobs_Complited[] = new String[RecordCount];
		String Current_Year_Jobs_Complited[] = new String[RecordCount];
		String Previous_Year_Direct_Labour[] = new String[RecordCount];
		String Current_Year_Direct_Labour[] = new String[RecordCount];
		String Previous_Year_Indirect_Labour[] = new String[RecordCount];
		String Current_Year_Indirect_Labour[] = new String[RecordCount];
		String Previous_Year_Value_of_Work_Outside[] = new String[RecordCount];
		String Current_Year_Value_of_Work_Outside[] = new String[RecordCount];
		String Previous_Year_Cost_of_Spares[] = new String[RecordCount];
		String Current_Year_Cost_of_Spares[] = new String[RecordCount];
		String Previous_Year_Expenditure_of_Others[] = new String[RecordCount];
		String Current_Year_Expenditure_of_Others[] = new String[RecordCount];
		String Previous_Year_Total_Cost[] = new String[RecordCount];
		String Current_Year_Total_Cost[] = new String[RecordCount];

		/* Variables Declaration */
		int check2 = 0;
		int slno2 = 0;

		String Vehicles2 = null;
		String Jobs_Pending2 = null;
		int Previous_Year_Jobs_Entrusted2 = 0;
		int Current_Year_Jobs_Entrusted2 = 0;
		int Previous_Year_Jobs_Complited2 = 0;
		int Current_Year_Jobs_Complited2 = 0;
		float Previous_Year_Direct_Labour2 = 0.0f;
		float Current_Year_Direct_Labour2 = 0.0f;
		float Previous_Year_Indirect_Labour2 = 0.0f;
		float Current_Year_Indirect_Labour2 = 0.0f;
		float Previous_Year_Value_of_Work_Outside2 = 0.0f;
		float Current_Year_Value_of_Work_Outside2 = 0.0f;
		float Previous_Year_Cost_of_Spares2 = 0.0f;
		float Current_Year_Cost_of_Spares2 = 0.0f;
		float Previous_Year_Expenditure_of_Others2 = 0.0f;
		float Current_Year_Expenditure_of_Others2 = 0.0f;
		float Previous_Year_Total_Cost2 = 0.0f;
		float Current_Year_Total_Cost2 = 0.0f;

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
				ps1.setString(4, "12");
				rs2 = ps1.executeQuery();
				if (rs2.next()) {
					sendMessage(
							response,
							"Civil Budget Format-12 have Already Freezed ............ ",
							"ok", "Civil_Budget_Format_12.jsp");
				} else {
					ps = con
							.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR from FAS_BUDGET_FORMAT_12 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
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
								Jobs_Pending2 = request
										.getParameter("Jobs_Pending" + k);

							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Previous_Year_Jobs_Entrusted[k] = request
										.getParameter("Previous_Year_Jobs_Entrusted"
												+ k);
								if (Previous_Year_Jobs_Entrusted[k] != null) {
									if (Previous_Year_Jobs_Entrusted[k]
											.equals("")) {
										Previous_Year_Jobs_Entrusted2 = 0;
									} else {
										Previous_Year_Jobs_Entrusted2 = Integer
												.parseInt(Previous_Year_Jobs_Entrusted[k]);
									}
								} else {
									Previous_Year_Jobs_Entrusted2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting Previous_Year_Jobs_Entrusted -->"
												+ e);
							}

							try {
								Current_Year_Jobs_Entrusted[k] = request
										.getParameter("Current_Year_Jobs_Entrusted"
												+ k);
								if (Current_Year_Jobs_Entrusted[k] != null) {
									if (Current_Year_Jobs_Entrusted[k]
											.equals("")) {
										Current_Year_Jobs_Entrusted2 = 0;
									} else {
										Current_Year_Jobs_Entrusted2 = Integer
												.parseInt(Current_Year_Jobs_Entrusted[k]);
									}
								} else {
									Current_Year_Jobs_Entrusted2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting Current_Year_Jobs_Entrusted -->"
												+ e);
							}

							try {
								Previous_Year_Jobs_Complited[k] = request
										.getParameter("Previous_Year_Jobs_Complited"
												+ k);
								if (Previous_Year_Jobs_Complited[k] != null) {
									if (Previous_Year_Jobs_Complited[k]
											.equals("")) {
										Previous_Year_Jobs_Complited2 = 0;
									} else {
										Previous_Year_Jobs_Complited2 = Integer
												.parseInt(Previous_Year_Jobs_Complited[k]);
									}
								} else {
									Previous_Year_Jobs_Complited2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting Previous_Year_Jobs_Complited -->"
												+ e);
							}

							try {
								Current_Year_Jobs_Complited[k] = request
										.getParameter("Current_Year_Jobs_Complited"
												+ k);
								if (Current_Year_Jobs_Complited[k] != null) {
									if (Current_Year_Jobs_Complited[k]
											.equals("")) {
										Current_Year_Jobs_Complited2 = 0;
									} else {
										Current_Year_Jobs_Complited2 = Integer
												.parseInt(Current_Year_Jobs_Complited[k]);
									}
								} else {
									Current_Year_Jobs_Complited2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting Current_Year_Jobs_Complited -->"
												+ e);
							}

							try {
								Previous_Year_Direct_Labour[k] = request
										.getParameter("Previous_Year_Direct_Labour"
												+ k);
								if (Previous_Year_Direct_Labour[k] != null) {
									if (Previous_Year_Direct_Labour[k]
											.equals("")) {
										Previous_Year_Direct_Labour2 = 0.0f;
									} else {
										Previous_Year_Direct_Labour2 = Float
												.parseFloat(Previous_Year_Direct_Labour[k]);
									}
								} else {
									Previous_Year_Direct_Labour2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Current_Year_Direct_Labour[k] = request
										.getParameter("Current_Year_Direct_Labour"
												+ k);
								if (Current_Year_Direct_Labour[k] != null) {
									if (Current_Year_Direct_Labour[k]
											.equals("")) {
										Current_Year_Direct_Labour2 = 0.0f;
									} else {
										Current_Year_Direct_Labour2 = Float
												.parseFloat(Current_Year_Direct_Labour[k]);
									}
								} else {
									Current_Year_Direct_Labour2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Previous_Year_Indirect_Labour[k] = request
										.getParameter("Previous_Year_Indirect_Labour"
												+ k);
								if (Previous_Year_Indirect_Labour[k] != null) {
									if (Previous_Year_Indirect_Labour[k]
											.equals("")) {
										Previous_Year_Indirect_Labour2 = 0.0f;
									} else {
										Previous_Year_Indirect_Labour2 = Float
												.parseFloat(Previous_Year_Indirect_Labour[k]);
									}
								} else {
									Previous_Year_Indirect_Labour2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Current_Year_Indirect_Labour[k] = request
										.getParameter("Current_Year_Indirect_Labour"
												+ k);
								if (Current_Year_Indirect_Labour[k] != null) {
									if (Current_Year_Indirect_Labour[k]
											.equals("")) {
										Current_Year_Indirect_Labour2 = 0.0f;
									} else {
										Current_Year_Indirect_Labour2 = Float
												.parseFloat(Current_Year_Indirect_Labour[k]);
									}
								} else {
									Current_Year_Indirect_Labour2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Previous_Year_Value_of_Work_Outside[k] = request
										.getParameter("Previous_Year_Value_of_Work_Outside"
												+ k);
								if (Previous_Year_Value_of_Work_Outside[k] != null) {
									if (Previous_Year_Value_of_Work_Outside[k]
											.equals("")) {
										Previous_Year_Value_of_Work_Outside2 = 0.0f;
									} else {
										Previous_Year_Value_of_Work_Outside2 = Float
												.parseFloat(Previous_Year_Value_of_Work_Outside[k]);
									}
								} else {
									Previous_Year_Value_of_Work_Outside2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Current_Year_Value_of_Work_Outside[k] = request
										.getParameter("Current_Year_Value_of_Work_Outside"
												+ k);
								if (Current_Year_Value_of_Work_Outside[k] != null) {
									if (Current_Year_Value_of_Work_Outside[k]
											.equals("")) {
										Current_Year_Value_of_Work_Outside2 = 0.0f;
									} else {
										Current_Year_Value_of_Work_Outside2 = Float
												.parseFloat(Current_Year_Value_of_Work_Outside[k]);
									}
								} else {
									Current_Year_Value_of_Work_Outside2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Previous_Year_Cost_of_Spares[k] = request
										.getParameter("Previous_Year_Cost_of_Spares"
												+ k);
								if (Previous_Year_Cost_of_Spares[k] != null) {
									if (Previous_Year_Cost_of_Spares[k]
											.equals("")) {
										Previous_Year_Cost_of_Spares2 = 0.0f;
									} else {
										Previous_Year_Cost_of_Spares2 = Float
												.parseFloat(Previous_Year_Cost_of_Spares[k]);
									}
								} else {
									Previous_Year_Cost_of_Spares2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Current_Year_Cost_of_Spares[k] = request
										.getParameter("Current_Year_Cost_of_Spares"
												+ k);
								if (Current_Year_Cost_of_Spares[k] != null) {
									if (Current_Year_Cost_of_Spares[k]
											.equals("")) {
										Current_Year_Cost_of_Spares2 = 0.0f;
									} else {
										Current_Year_Cost_of_Spares2 = Float
												.parseFloat(Current_Year_Cost_of_Spares[k]);
									}
								} else {
									Current_Year_Cost_of_Spares2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Previous_Year_Expenditure_of_Others[k] = request
										.getParameter("Previous_Year_Expenditure_of_Others"
												+ k);
								if (Previous_Year_Expenditure_of_Others[k] != null) {
									if (Previous_Year_Expenditure_of_Others[k]
											.equals("")) {
										Previous_Year_Expenditure_of_Others2 = 0.0f;
									} else {
										Previous_Year_Expenditure_of_Others2 = Float
												.parseFloat(Previous_Year_Expenditure_of_Others[k]);
									}
								} else {
									Previous_Year_Expenditure_of_Others2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Current_Year_Expenditure_of_Others[k] = request
										.getParameter("Current_Year_Expenditure_of_Others"
												+ k);
								if (Current_Year_Expenditure_of_Others[k] != null) {
									if (Current_Year_Expenditure_of_Others[k]
											.equals("")) {
										Current_Year_Expenditure_of_Others2 = 0.0f;
									} else {
										Current_Year_Expenditure_of_Others2 = Float
												.parseFloat(Current_Year_Expenditure_of_Others[k]);
									}
								} else {
									Current_Year_Expenditure_of_Others2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Previous_Year_Total_Cost[k] = request
										.getParameter("Previous_Year_Total_Cost"
												+ k);
								if (Previous_Year_Total_Cost[k] != null) {
									if (Previous_Year_Total_Cost[k].equals("")) {
										Previous_Year_Total_Cost2 = 0.0f;
									} else {
										Previous_Year_Total_Cost2 = Float
												.parseFloat(Previous_Year_Total_Cost[k]);
									}
								} else {
									Previous_Year_Total_Cost2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							try {
								Current_Year_Total_Cost[k] = request
										.getParameter("Current_Year_Total_Cost"
												+ k);
								if (Current_Year_Total_Cost[k] != null) {
									if (Current_Year_Total_Cost[k].equals("")) {
										Current_Year_Total_Cost2 = 0.0f;
									} else {
										Current_Year_Total_Cost2 = Float
												.parseFloat(Current_Year_Total_Cost[k]);
									}
								} else {
									Current_Year_Total_Cost2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}
							int i = 1, i1 = 0;
							try {
								ps = con
										.prepareStatement("Select max(SL_NO) from FAS_BUDGET_FORMAT_12");
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
									.prepareStatement("insert into FAS_BUDGET_FORMAT_12 (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,VEHICLES,JOBS_PENDING,PRE_YR_JOBS_ENTRUSTED,CUR_YR_JOBS_ENTRUSTED,PRE_YR_JOBS_COMPLITED,CUR_YR_JOBS_COMPLITED,PRE_YR_DIRECT_LABOUR,CUR_YR_DIRECT_LABOUR,PRE_YR_INDIRECT_LABOUR,CUR_YR_INDIRECT_LABOUR,PRE_YR_VALUE_OF_WORK,CUR_YR_VALUE_OF_WORK,PRE_YR_COST_OF_SPARES,CUR_YR_COST_OF_SPARES,PRE_YR_EXPENDITURE_OF_OTHERS,CUR_YR_EXPENDITURE_OF_OTHERS,PRE_YR_TOTAL_COST,CUR_YR_TOTAL_COST,DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
							ps.setInt(1, cmbAcc_UnitCode);
							ps.setInt(2, cmbOffice_code);
							ps.setString(3, FinancialYear);
							ps.setInt(4, i);
							ps.setString(5, Vehicles2);
							ps.setString(6, Jobs_Pending2);
							ps.setInt(7, Previous_Year_Jobs_Entrusted2);
							ps.setInt(8, Current_Year_Jobs_Entrusted2);
							ps.setInt(9, Previous_Year_Jobs_Complited2);
							ps.setInt(10, Current_Year_Jobs_Complited2);
							ps.setFloat(11, Previous_Year_Direct_Labour2);
							ps.setFloat(12, Current_Year_Direct_Labour2);
							ps.setFloat(13, Previous_Year_Indirect_Labour2);
							ps.setFloat(14, Current_Year_Indirect_Labour2);
							ps.setFloat(15,
									Previous_Year_Value_of_Work_Outside2);
							ps
									.setFloat(16,
											Current_Year_Value_of_Work_Outside2);
							ps.setFloat(17, Previous_Year_Cost_of_Spares2);
							ps.setFloat(18, Current_Year_Cost_of_Spares2);
							ps.setFloat(19,
									Previous_Year_Expenditure_of_Others2);
							ps
									.setFloat(20,
											Current_Year_Expenditure_of_Others2);
							ps.setFloat(21, Previous_Year_Total_Cost2);
							ps.setFloat(22, Current_Year_Total_Cost2);
							ps.setInt(23, division_id);
							ps.setInt(24, circle_id);
							ps.setInt(25, region_id);
							ps.setInt(26, head_office_id);
							ps.setString(27, office_level_id);
							ps.setString(28, userid);
							ps.setTimestamp(29, ts);
							int kk = ps.executeUpdate();
						}
						flag = 2;
					}
					con.commit();
					if (flag == 1) {
						sendMessage(
								response,
								"Records Alredy Exist for given Office for given Financial Year ............ ",
								"ok", "Civil_Budget_Format_12.jsp");
					} else if (flag == 2) {
						sendMessage(response,
								"Records Saved Successfully ............ ",
								"ok", "Civil_Budget_Format_12.jsp");
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
					ps1.setString(4, "12");
					rs = ps1.executeQuery();
					if (rs.next()) {
						xml = xml + "<flag>Freezed</flag>";
					} else {
						ps = con
								.prepareStatement(" select SL_NO,VEHICLES,JOBS_PENDING,PRE_YR_JOBS_ENTRUSTED,CUR_YR_JOBS_ENTRUSTED,PRE_YR_JOBS_COMPLITED,CUR_YR_JOBS_COMPLITED,PRE_YR_DIRECT_LABOUR,CUR_YR_DIRECT_LABOUR,PRE_YR_INDIRECT_LABOUR,CUR_YR_INDIRECT_LABOUR,PRE_YR_VALUE_OF_WORK,CUR_YR_VALUE_OF_WORK,PRE_YR_COST_OF_SPARES,CUR_YR_COST_OF_SPARES,PRE_YR_EXPENDITURE_OF_OTHERS,CUR_YR_EXPENDITURE_OF_OTHERS,PRE_YR_TOTAL_COST,CUR_YR_TOTAL_COST from FAS_BUDGET_FORMAT_12 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, FinancialYear);
						rs = ps.executeQuery();
						while (rs.next()) {
							xml = xml + "<slno>" + rs.getInt("SL_NO")
									+ "</slno>";

							xml = xml + "<Vehicles>" + rs.getString("VEHICLES")
									+ "</Vehicles>";

							xml = xml + "<Jobs_Pending>"
									+ rs.getString("JOBS_PENDING")
									+ "</Jobs_Pending>";

							xml = xml + "<Previous_Year_Jobs_Entrusted>"
									+ rs.getInt("PRE_YR_JOBS_ENTRUSTED")
									+ "</Previous_Year_Jobs_Entrusted>";

							xml = xml + "<Current_Year_Jobs_Entrusted>"
									+ rs.getInt("CUR_YR_JOBS_ENTRUSTED")
									+ "</Current_Year_Jobs_Entrusted>";

							xml = xml + "<Previous_Year_Jobs_Complited>"
									+ rs.getInt("PRE_YR_JOBS_COMPLITED")
									+ "</Previous_Year_Jobs_Complited>";

							xml = xml + "<Current_Year_Jobs_Complited>"
									+ rs.getInt("CUR_YR_JOBS_COMPLITED")
									+ "</Current_Year_Jobs_Complited>";

							xml = xml + "<Previous_Year_Direct_Labour>"
									+ rs.getFloat("PRE_YR_DIRECT_LABOUR")
									+ "</Previous_Year_Direct_Labour>";

							xml = xml + "<Current_Year_Direct_Labour>"
									+ rs.getFloat("CUR_YR_DIRECT_LABOUR")
									+ "</Current_Year_Direct_Labour>";

							xml = xml + "<Previous_Year_Indirect_Labour>"
									+ rs.getFloat("PRE_YR_INDIRECT_LABOUR")
									+ "</Previous_Year_Indirect_Labour>";

							xml = xml + "<Current_Year_Indirect_Labour>"
									+ rs.getFloat("CUR_YR_INDIRECT_LABOUR")
									+ "</Current_Year_Indirect_Labour>";

							xml = xml + "<Previous_Year_Value_of_Work_Outside>"
									+ rs.getFloat("PRE_YR_VALUE_OF_WORK")
									+ "</Previous_Year_Value_of_Work_Outside>";

							xml = xml + "<Current_Year_Value_of_Work_Outside>"
									+ rs.getFloat("CUR_YR_VALUE_OF_WORK")
									+ "</Current_Year_Value_of_Work_Outside>";

							xml = xml + "<Previous_Year_Cost_of_Spares>"
									+ rs.getFloat("PRE_YR_COST_OF_SPARES")
									+ "</Previous_Year_Cost_of_Spares>";

							xml = xml + "<Current_Year_Cost_of_Spares>"
									+ rs.getFloat("CUR_YR_COST_OF_SPARES")
									+ "</Current_Year_Cost_of_Spares>";

							xml = xml
									+ "<Previous_Year_Expenditure_of_Others>"
									+ rs
											.getFloat("PRE_YR_EXPENDITURE_OF_OTHERS")
									+ "</Previous_Year_Expenditure_of_Others>";

							xml = xml
									+ "<Current_Year_Expenditure_of_Others>"
									+ rs
											.getFloat("CUR_YR_EXPENDITURE_OF_OTHERS")
									+ "</Current_Year_Expenditure_of_Others>";

							xml = xml + "<Previous_Year_Total_Cost>"
									+ rs.getFloat("PRE_YR_TOTAL_COST")
									+ "</Previous_Year_Total_Cost>";

							xml = xml + "<Current_Year_Total_Cost>"
									+ rs.getFloat("CUR_YR_TOTAL_COST")
									+ "</Current_Year_Total_Cost>";
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
									.prepareStatement(" select * from FAS_BUDGET_FORMAT_12 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and SL_NO=?");
							ps.setInt(1, cmbAcc_UnitCode);
							ps.setInt(2, cmbOffice_code);
							ps.setString(3, FinancialYear);
							ps.setInt(4, slno2);
							rs = ps.executeQuery();
							if (rs.next()) {
								ps1 = con
										.prepareStatement(" delete from FAS_BUDGET_FORMAT_12 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and SL_NO=?");
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
								"ok", "Civil_Budget_Format_12.jsp");
					} else if (flag == 3) {
						con.commit();
						sendMessage(response,
								"Record Does Not Exist ............ ", "ok",
								"Civil_Budget_Format_12.jsp");
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Deletion Failed ............ " + e, "ok",
							"Civil_Budget_Format_12.jsp");
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
					ps1.setString(4, "12");
					rs2 = ps1.executeQuery();
					if (rs2.next()) {
						sendMessage(
								response,
								"Civil Budget Format-6 have Already Freezed ............ ",
								"ok", "Civil_Budget_Format_6.jsp");
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
								.prepareStatement(" select * from FAS_BUDGET_FORMAT_12 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, FinancialYear);
						rs = ps.executeQuery();
						if (rs.next()) {
							ps1 = con
									.prepareStatement(" delete from FAS_BUDGET_FORMAT_12 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
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
									Jobs_Pending2 = request
											.getParameter("Jobs_Pending" + k);

								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Previous_Year_Jobs_Entrusted[k] = request
											.getParameter("Previous_Year_Jobs_Entrusted"
													+ k);
									if (Previous_Year_Jobs_Entrusted[k] != null) {
										if (Previous_Year_Jobs_Entrusted[k]
												.equals("")) {
											Previous_Year_Jobs_Entrusted2 = 0;
										} else {
											Previous_Year_Jobs_Entrusted2 = Integer
													.parseInt(Previous_Year_Jobs_Entrusted[k]);
										}
									} else {
										Previous_Year_Jobs_Entrusted2 = 0;
									}
								} catch (Exception e) {
									System.out
											.println("Error for getting Previous_Year_Jobs_Entrusted -->"
													+ e);
								}

								try {
									Current_Year_Jobs_Entrusted[k] = request
											.getParameter("Current_Year_Jobs_Entrusted"
													+ k);
									if (Current_Year_Jobs_Entrusted[k] != null) {
										if (Current_Year_Jobs_Entrusted[k]
												.equals("")) {
											Current_Year_Jobs_Entrusted2 = 0;
										} else {
											Current_Year_Jobs_Entrusted2 = Integer
													.parseInt(Current_Year_Jobs_Entrusted[k]);
										}
									} else {
										Current_Year_Jobs_Entrusted2 = 0;
									}
								} catch (Exception e) {
									System.out
											.println("Error for getting Current_Year_Jobs_Entrusted -->"
													+ e);
								}

								try {
									Previous_Year_Jobs_Complited[k] = request
											.getParameter("Previous_Year_Jobs_Complited"
													+ k);
									if (Previous_Year_Jobs_Complited[k] != null) {
										if (Previous_Year_Jobs_Complited[k]
												.equals("")) {
											Previous_Year_Jobs_Complited2 = 0;
										} else {
											Previous_Year_Jobs_Complited2 = Integer
													.parseInt(Previous_Year_Jobs_Complited[k]);
										}
									} else {
										Previous_Year_Jobs_Complited2 = 0;
									}
								} catch (Exception e) {
									System.out
											.println("Error for getting Previous_Year_Jobs_Complited -->"
													+ e);
								}

								try {
									Current_Year_Jobs_Complited[k] = request
											.getParameter("Current_Year_Jobs_Complited"
													+ k);
									if (Current_Year_Jobs_Complited[k] != null) {
										if (Current_Year_Jobs_Complited[k]
												.equals("")) {
											Current_Year_Jobs_Complited2 = 0;
										} else {
											Current_Year_Jobs_Complited2 = Integer
													.parseInt(Current_Year_Jobs_Complited[k]);
										}
									} else {
										Current_Year_Jobs_Complited2 = 0;
									}
								} catch (Exception e) {
									System.out
											.println("Error for getting Current_Year_Jobs_Complited -->"
													+ e);
								}

								try {
									Previous_Year_Direct_Labour[k] = request
											.getParameter("Previous_Year_Direct_Labour"
													+ k);
									if (Previous_Year_Direct_Labour[k] != null) {
										if (Previous_Year_Direct_Labour[k]
												.equals("")) {
											Previous_Year_Direct_Labour2 = 0.0f;
										} else {
											Previous_Year_Direct_Labour2 = Float
													.parseFloat(Previous_Year_Direct_Labour[k]);
										}
									} else {
										Previous_Year_Direct_Labour2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Current_Year_Direct_Labour[k] = request
											.getParameter("Current_Year_Direct_Labour"
													+ k);
									if (Current_Year_Direct_Labour[k] != null) {
										if (Current_Year_Direct_Labour[k]
												.equals("")) {
											Current_Year_Direct_Labour2 = 0.0f;
										} else {
											Current_Year_Direct_Labour2 = Float
													.parseFloat(Current_Year_Direct_Labour[k]);
										}
									} else {
										Current_Year_Direct_Labour2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Previous_Year_Indirect_Labour[k] = request
											.getParameter("Previous_Year_Indirect_Labour"
													+ k);
									if (Previous_Year_Indirect_Labour[k] != null) {
										if (Previous_Year_Indirect_Labour[k]
												.equals("")) {
											Previous_Year_Indirect_Labour2 = 0.0f;
										} else {
											Previous_Year_Indirect_Labour2 = Float
													.parseFloat(Previous_Year_Indirect_Labour[k]);
										}
									} else {
										Previous_Year_Indirect_Labour2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Current_Year_Indirect_Labour[k] = request
											.getParameter("Current_Year_Indirect_Labour"
													+ k);
									if (Current_Year_Indirect_Labour[k] != null) {
										if (Current_Year_Indirect_Labour[k]
												.equals("")) {
											Current_Year_Indirect_Labour2 = 0.0f;
										} else {
											Current_Year_Indirect_Labour2 = Float
													.parseFloat(Current_Year_Indirect_Labour[k]);
										}
									} else {
										Current_Year_Indirect_Labour2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Previous_Year_Value_of_Work_Outside[k] = request
											.getParameter("Previous_Year_Value_of_Work_Outside"
													+ k);
									if (Previous_Year_Value_of_Work_Outside[k] != null) {
										if (Previous_Year_Value_of_Work_Outside[k]
												.equals("")) {
											Previous_Year_Value_of_Work_Outside2 = 0.0f;
										} else {
											Previous_Year_Value_of_Work_Outside2 = Float
													.parseFloat(Previous_Year_Value_of_Work_Outside[k]);
										}
									} else {
										Previous_Year_Value_of_Work_Outside2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Current_Year_Value_of_Work_Outside[k] = request
											.getParameter("Current_Year_Value_of_Work_Outside"
													+ k);
									if (Current_Year_Value_of_Work_Outside[k] != null) {
										if (Current_Year_Value_of_Work_Outside[k]
												.equals("")) {
											Current_Year_Value_of_Work_Outside2 = 0.0f;
										} else {
											Current_Year_Value_of_Work_Outside2 = Float
													.parseFloat(Current_Year_Value_of_Work_Outside[k]);
										}
									} else {
										Current_Year_Value_of_Work_Outside2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Previous_Year_Cost_of_Spares[k] = request
											.getParameter("Previous_Year_Cost_of_Spares"
													+ k);
									if (Previous_Year_Cost_of_Spares[k] != null) {
										if (Previous_Year_Cost_of_Spares[k]
												.equals("")) {
											Previous_Year_Cost_of_Spares2 = 0.0f;
										} else {
											Previous_Year_Cost_of_Spares2 = Float
													.parseFloat(Previous_Year_Cost_of_Spares[k]);
										}
									} else {
										Previous_Year_Cost_of_Spares2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Current_Year_Cost_of_Spares[k] = request
											.getParameter("Current_Year_Cost_of_Spares"
													+ k);
									if (Current_Year_Cost_of_Spares[k] != null) {
										if (Current_Year_Cost_of_Spares[k]
												.equals("")) {
											Current_Year_Cost_of_Spares2 = 0.0f;
										} else {
											Current_Year_Cost_of_Spares2 = Float
													.parseFloat(Current_Year_Cost_of_Spares[k]);
										}
									} else {
										Current_Year_Cost_of_Spares2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Previous_Year_Expenditure_of_Others[k] = request
											.getParameter("Previous_Year_Expenditure_of_Others"
													+ k);
									if (Previous_Year_Expenditure_of_Others[k] != null) {
										if (Previous_Year_Expenditure_of_Others[k]
												.equals("")) {
											Previous_Year_Expenditure_of_Others2 = 0.0f;
										} else {
											Previous_Year_Expenditure_of_Others2 = Float
													.parseFloat(Previous_Year_Expenditure_of_Others[k]);
										}
									} else {
										Previous_Year_Expenditure_of_Others2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Current_Year_Expenditure_of_Others[k] = request
											.getParameter("Current_Year_Expenditure_of_Others"
													+ k);
									if (Current_Year_Expenditure_of_Others[k] != null) {
										if (Current_Year_Expenditure_of_Others[k]
												.equals("")) {
											Current_Year_Expenditure_of_Others2 = 0.0f;
										} else {
											Current_Year_Expenditure_of_Others2 = Float
													.parseFloat(Current_Year_Expenditure_of_Others[k]);
										}
									} else {
										Current_Year_Expenditure_of_Others2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Previous_Year_Total_Cost[k] = request
											.getParameter("Previous_Year_Total_Cost"
													+ k);
									if (Previous_Year_Total_Cost[k] != null) {
										if (Previous_Year_Total_Cost[k]
												.equals("")) {
											Previous_Year_Total_Cost2 = 0.0f;
										} else {
											Previous_Year_Total_Cost2 = Float
													.parseFloat(Previous_Year_Total_Cost[k]);
										}
									} else {
										Previous_Year_Total_Cost2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								try {
									Current_Year_Total_Cost[k] = request
											.getParameter("Current_Year_Total_Cost"
													+ k);
									if (Current_Year_Total_Cost[k] != null) {
										if (Current_Year_Total_Cost[k]
												.equals("")) {
											Current_Year_Total_Cost2 = 0.0f;
										} else {
											Current_Year_Total_Cost2 = Float
													.parseFloat(Current_Year_Total_Cost[k]);
										}
									} else {
										Current_Year_Total_Cost2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								int i = 1, i1 = 0;
								try {
									ps = con
											.prepareStatement("Select max(SL_NO) from FAS_BUDGET_FORMAT_12");
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
										.prepareStatement("insert into FAS_BUDGET_FORMAT_12 (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,VEHICLES,JOBS_PENDING,PRE_YR_JOBS_ENTRUSTED,CUR_YR_JOBS_ENTRUSTED,PRE_YR_JOBS_COMPLITED,CUR_YR_JOBS_COMPLITED,PRE_YR_DIRECT_LABOUR,CUR_YR_DIRECT_LABOUR,PRE_YR_INDIRECT_LABOUR,CUR_YR_INDIRECT_LABOUR,PRE_YR_VALUE_OF_WORK,CUR_YR_VALUE_OF_WORK,PRE_YR_COST_OF_SPARES,CUR_YR_COST_OF_SPARES,PRE_YR_EXPENDITURE_OF_OTHERS,CUR_YR_EXPENDITURE_OF_OTHERS,PRE_YR_TOTAL_COST,CUR_YR_TOTAL_COST,DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
								ps.setInt(1, cmbAcc_UnitCode);
								ps.setInt(2, cmbOffice_code);
								ps.setString(3, FinancialYear);
								ps.setInt(4, i);
								ps.setString(5, Vehicles2);
								ps.setString(6, Jobs_Pending2);
								ps.setInt(7, Previous_Year_Jobs_Entrusted2);
								ps.setInt(8, Current_Year_Jobs_Entrusted2);
								ps.setInt(9, Previous_Year_Jobs_Complited2);
								ps.setInt(10, Current_Year_Jobs_Complited2);
								ps.setFloat(11, Previous_Year_Direct_Labour2);
								ps.setFloat(12, Current_Year_Direct_Labour2);
								ps.setFloat(13, Previous_Year_Indirect_Labour2);
								ps.setFloat(14, Current_Year_Indirect_Labour2);
								ps.setFloat(15,
										Previous_Year_Value_of_Work_Outside2);
								ps.setFloat(16,
										Current_Year_Value_of_Work_Outside2);
								ps.setFloat(17, Previous_Year_Cost_of_Spares2);
								ps.setFloat(18, Current_Year_Cost_of_Spares2);
								ps.setFloat(19,
										Previous_Year_Expenditure_of_Others2);
								ps.setFloat(20,
										Current_Year_Expenditure_of_Others2);
								ps.setFloat(21, Previous_Year_Total_Cost2);
								ps.setFloat(22, Current_Year_Total_Cost2);
								ps.setInt(23, division_id);
								ps.setInt(24, circle_id);
								ps.setInt(25, region_id);
								ps.setInt(26, head_office_id);
								ps.setString(27, office_level_id);
								ps.setString(28, userid);
								ps.setTimestamp(29, ts);
								int kk = ps.executeUpdate();
							}
							con.commit();
							sendMessage(
									response,
									"Records Updated Successfully ............ ",
									"ok", "Civil_Budget_Format_12.jsp");
						} else {
							sendMessage(response,
									"Records Does Not Exist ............ ",
									"ok", "Civil_Budget_Format_12.jsp");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Updation Failed ............ " + e, "ok",
							"Civil_Budget_Format_12.jsp");
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
					"ok", "Civil_Budget_Format_12.jsp");
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
