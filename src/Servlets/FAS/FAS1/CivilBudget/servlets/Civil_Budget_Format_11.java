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
 * Servlet implementation class Civil_Budget_Format_11
 */
public class Civil_Budget_Format_11 extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Civil_Budget_Format_11() {
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
		String Divn_Code[] = new String[RecordCount];
		String Type_of_Vehicle[] = new String[RecordCount];
		String Regn_No[] = new String[RecordCount];
		String Date_Of_Purchase[] = new String[RecordCount];
		String No_of_Kms_Done[] = new String[RecordCount];
		String Age_of_Vehicle[] = new String[RecordCount];
		String Date_of_Condemnation_Fit[] = new String[RecordCount];
		String Date_of_Condemnation_Not_Fit[] = new String[RecordCount];
		String Salary_LY[] = new String[RecordCount];
		String Fuel_Materia_LY[] = new String[RecordCount];
		String Ordinary_Repairs_LY[] = new String[RecordCount];
		String Special_Repairs_LY[] = new String[RecordCount];
		String Total_Cost_LY[] = new String[RecordCount];
		String Salary_CY[] = new String[RecordCount];
		String Fuel_Materia_CY[] = new String[RecordCount];
		String Ordinary_Repairs_CY[] = new String[RecordCount];
		String Special_Repairs_CY[] = new String[RecordCount];
		String Total_Cost_CY[] = new String[RecordCount];
		String Salary_NY[] = new String[RecordCount];
		String Fuel_Materia_NY[] = new String[RecordCount];
		String Ordinary_Repairs_NY[] = new String[RecordCount];
		String Special_Repairs_NY[] = new String[RecordCount];
		String Total_Cost_NY[] = new String[RecordCount];

		/* Variables Declaration */
		int check2 = 0;
		int slno2 = 0;
		int Divn_Code2 = 0;
		String Type_of_Vehicle2 = null;
		String Regn_No2 = null;
		Date Date_Of_Purchase2 = null;
		String No_of_Kms_Done2 = null;
		String Age_of_Vehicle2 = null;
		Date Date_of_Condemnation_Fit2 = null;
		Date Date_of_Condemnation_Not_Fit2 = null;
		float Salary_LY2 = 0.0f;
		String Fuel_Materia_LY2 = null;
		String Ordinary_Repairs_LY2 = null;
		String Special_Repairs_LY2 = null;
		float Total_Cost_LY2 = 0.0f;
		float Salary_CY2 = 0.0f;
		String Fuel_Materia_CY2 = null;
		String Ordinary_Repairs_CY2 = null;
		String Special_Repairs_CY2 = null;
		float Total_Cost_CY2 = 0.0f;
		float Salary_NY2 = 0.0f;
		String Fuel_Materia_NY2 = null;
		String Ordinary_Repairs_NY2 = null;
		String Special_Repairs_NY2 = null;
		float Total_Cost_NY2 = 0.0f;

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
				ps1.setString(4, "11");
				rs2 = ps1.executeQuery();
				if (rs2.next()) {
					sendMessage(
							response,
							"Civil Budget Format-11 have Already Freezed ............ ",
							"ok", "Civil_Budget_Format_11.jsp");
				} else {
					ps = con
							.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR from FAS_BUDGET_FORMAT_11 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
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

							/* Divn_Code */
							try {
								Divn_Code[k] = request.getParameter("Divn_Code"
										+ k);
								if (Divn_Code[k] != null) {
									if (Divn_Code[k].equals("")) {
										Divn_Code2 = 0;
									} else {
										Divn_Code2 = Integer
												.parseInt(Divn_Code[k]);
									}
								} else {
									Divn_Code2 = 0;
								}
							} catch (Exception e) {
								System.out
										.println("Error for getting Head_of_Account -->"
												+ e);
							}

							/* Type_of_Vehicle */
							try {
								Type_of_Vehicle2 = request
										.getParameter("Type_of_Vehicle" + k);

							} catch (Exception e) {
								System.out.println(e);
							}

							/* Regn_No */
							try {
								Regn_No2 = request.getParameter("Regn_No" + k);
							} catch (Exception e) {
								System.out.println(e);
							}

							/* Date_Of_Purchase */
							try {
								Date_Of_Purchase[k] = request
										.getParameter("Date_Of_Purchase" + k);

								if (!Date_Of_Purchase[k].equalsIgnoreCase("")) {
									sd = Date_Of_Purchase[k].split("/");
									c = new GregorianCalendar(Integer
											.parseInt(sd[2]), Integer
											.parseInt(sd[1]) - 1, Integer
											.parseInt(sd[0]));
									d = c.getTime();
									Date_Of_Purchase2 = new Date(d.getTime());
								}else{
									Date_Of_Purchase2 =null;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* No_of_Kms_Done */
							try {

								No_of_Kms_Done2 = request
										.getParameter("No_of_Kms_Done" + k);
							} catch (Exception e) {
								System.out.println(e);
							}

							/* Age_of_Vehicle */
							try {
								Age_of_Vehicle2 = request
										.getParameter("Age_of_Vehicle" + k);
							} catch (Exception e) {
								System.out.println(e);
							}

							/* Date_of_Condemnation_Fit */
							try {
								Date_of_Condemnation_Fit[k] = request
										.getParameter("Date_of_Condemnation_Fit"
												+ k);

								if (!Date_of_Condemnation_Fit[k]
										.equalsIgnoreCase("")) {
									sd = Date_of_Condemnation_Fit[k].split("/");
									c = new GregorianCalendar(Integer
											.parseInt(sd[2]), Integer
											.parseInt(sd[1]) - 1, Integer
											.parseInt(sd[0]));
									d = c.getTime();
									Date_of_Condemnation_Fit2 = new Date(d
											.getTime());
								}else{
									Date_of_Condemnation_Fit2 =null;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* Date_of_Condemnation_Not_Fit */
							try {
								Date_of_Condemnation_Not_Fit[k] = request
										.getParameter("Date_of_Condemnation_Not_Fit"
												+ k);

								if (!Date_of_Condemnation_Not_Fit[k]
										.equalsIgnoreCase("")) {
									sd = Date_of_Condemnation_Not_Fit[k]
											.split("/");
									c = new GregorianCalendar(Integer
											.parseInt(sd[2]), Integer
											.parseInt(sd[1]) - 1, Integer
											.parseInt(sd[0]));
									d = c.getTime();
									Date_of_Condemnation_Not_Fit2 = new Date(d
											.getTime());
								}else{
									Date_of_Condemnation_Not_Fit2 =null;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* Salary_LY */
							try {
								Salary_LY[k] = request.getParameter("Salary_LY"
										+ k);
								if (Salary_LY[k] != null) {
									if (Salary_LY[k].equals("")) {
										Salary_LY2 = 0.0f;
									} else {
										Salary_LY2 = Float
												.parseFloat(Salary_LY[k]);
									}
								} else {
									Salary_LY2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* Fuel_Materia_LY */
							try {
								Fuel_Materia_LY2 = request
										.getParameter("Fuel_Materia_LY" + k);
							} catch (Exception e) {
								System.out
										.println("Error for getting Fuel_Materia_LY -->"
												+ e);
							}

							/* Ordinary_Repairs_LY */
							try {
								Ordinary_Repairs_LY2 = request
										.getParameter("Ordinary_Repairs_LY" + k);
							} catch (Exception e) {
								System.out
										.println("Error for getting Ordinary_Repairs_LY -->"
												+ e);
							}

							/* Special_Repairs_LY */
							try {
								Special_Repairs_LY2 = request
										.getParameter("Special_Repairs_LY" + k);
							} catch (Exception e) {
								System.out
										.println("Error for getting Special_Repairs_LY -->"
												+ e);
							}

							/* Total_Cost_LY */
							try {
								Total_Cost_LY[k] = request
										.getParameter("Total_Cost_LY" + k);
								if (Total_Cost_LY[k] != null) {
									if (Total_Cost_LY[k].equals("")) {
										Total_Cost_LY2 = 0.0f;
									} else {
										Total_Cost_LY2 = Float
												.parseFloat(Total_Cost_LY[k]);
									}
								} else {
									Total_Cost_LY2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* Salary_CY */
							try {
								Salary_CY[k] = request.getParameter("Salary_CY"
										+ k);
								if (Salary_CY[k] != null) {
									if (Salary_CY[k].equals("")) {
										Salary_CY2 = 0.0f;
									} else {
										Salary_CY2 = Float
												.parseFloat(Salary_CY[k]);
									}
								} else {
									Salary_CY2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* Fuel_Materia_CY */
							try {
								Fuel_Materia_CY2 = request
										.getParameter("Fuel_Materia_CY" + k);
							} catch (Exception e) {
								System.out
										.println("Error for getting Fuel_Materia_CY -->"
												+ e);
							}

							/* Ordinary_Repairs_CY */
							try {
								Ordinary_Repairs_CY2 = request
										.getParameter("Ordinary_Repairs_CY" + k);
							} catch (Exception e) {
								System.out
										.println("Error for getting Ordinary_Repairs_CY -->"
												+ e);
							}

							/* Special_Repairs_CY */
							try {
								Special_Repairs_CY2 = request
										.getParameter("Special_Repairs_CY" + k);
							} catch (Exception e) {
								System.out
										.println("Error for getting Special_Repairs_CY -->"
												+ e);
							}

							/* Total_Cost_CY */
							try {
								Total_Cost_CY[k] = request
										.getParameter("Total_Cost_CY" + k);
								if (Total_Cost_CY[k] != null) {
									if (Total_Cost_CY[k].equals("")) {
										Total_Cost_CY2 = 0.0f;
									} else {
										Total_Cost_CY2 = Float
												.parseFloat(Total_Cost_CY[k]);
									}
								} else {
									Total_Cost_CY2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* Salary_NY */
							try {
								Salary_NY[k] = request.getParameter("Salary_NY"
										+ k);
								if (Salary_NY[k] != null) {
									if (Salary_NY[k].equals("")) {
										Salary_NY2 = 0.0f;
									} else {
										Salary_NY2 = Float
												.parseFloat(Salary_NY[k]);
									}
								} else {
									Salary_NY2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* Fuel_Materia_NY */
							try {
								Fuel_Materia_NY2 = request
										.getParameter("Fuel_Materia_NY" + k);
							} catch (Exception e) {
								System.out
										.println("Error for getting Fuel_Materia_NY -->"
												+ e);
							}

							/* Ordinary_Repairs_NY */
							try {
								Ordinary_Repairs_NY2 = request
										.getParameter("Ordinary_Repairs_NY" + k);
							} catch (Exception e) {
								System.out
										.println("Error for getting Ordinary_Repairs_NY -->"
												+ e);
							}

							/* Special_Repairs_NY */
							try {
								Special_Repairs_NY2 = request
										.getParameter("Special_Repairs_NY" + k);
							} catch (Exception e) {
								System.out
										.println("Error for getting Special_Repairs_NY -->"
												+ e);
							}

							/* Total_Cost_NY */
							try {
								Total_Cost_NY[k] = request
										.getParameter("Total_Cost_NY" + k);
								if (Total_Cost_NY[k] != null) {
									if (Total_Cost_NY[k].equals("")) {
										Total_Cost_NY2 = 0.0f;
									} else {
										Total_Cost_NY2 = Float
												.parseFloat(Total_Cost_NY[k]);
									}
								} else {
									Total_Cost_NY2 = 0.0f;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							int i = 1, i1 = 0;
							try {
								ps = con
										.prepareStatement("Select max(SL_NO) from FAS_BUDGET_FORMAT_11");
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
									.prepareStatement("insert into FAS_BUDGET_FORMAT_11 (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,DIVN_CODE,TYPE_OF_VEHICLE,REGN_NO,DATE_OF_PURCHASE,NO_OF_KMS_DONE,AGE_OF_VEHICLE,DATE_OF_CONDEMNATION_FIT,DATE_OF_CONDEMNATION_NOT_FIT,SALARY_LY,FUEL_MATERIA_LY,ORDINARY_REPAIRS_LY,SPECIAL_REPAIRS_LY,TOTAL_COST_LY,SALARY_CY,FUEL_MATERIA_CY,ORDINARY_REPAIRS_CY,SPECIAL_REPAIRS_CY,TOTAL_COST_CY,SALARY_NY,FUEL_MATERIA_NY,ORDINARY_REPAIRS_NY,SPECIAL_REPAIRS_NY,TOTAL_COST_NY,DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
							ps.setInt(1, cmbAcc_UnitCode);
							ps.setInt(2, cmbOffice_code);
							ps.setString(3, FinancialYear);
							ps.setInt(4, i);
							ps.setInt(5, Divn_Code2);
							ps.setString(6, Type_of_Vehicle2);
							ps.setString(7, Regn_No2);
							ps.setDate(8, Date_Of_Purchase2);
							ps.setString(9, No_of_Kms_Done2);
							ps.setString(10, Age_of_Vehicle2);
							ps.setDate(11, Date_of_Condemnation_Fit2);
							ps.setDate(12, Date_of_Condemnation_Not_Fit2);
							ps.setFloat(13, Salary_LY2);
							ps.setString(14, Fuel_Materia_LY2);
							ps.setString(15, Ordinary_Repairs_LY2);
							ps.setString(16, Special_Repairs_LY2);
							ps.setFloat(17, Total_Cost_LY2);
							ps.setFloat(18, Salary_CY2);
							ps.setString(19, Fuel_Materia_CY2);
							ps.setString(20, Ordinary_Repairs_CY2);
							ps.setString(21, Special_Repairs_CY2);
							ps.setFloat(22, Total_Cost_CY2);
							ps.setFloat(23, Salary_NY2);
							ps.setString(24, Fuel_Materia_NY2);
							ps.setString(25, Ordinary_Repairs_NY2);
							ps.setString(26, Special_Repairs_NY2);
							ps.setFloat(27, Total_Cost_NY2);
							ps.setInt(28, division_id);
							ps.setInt(29, circle_id);
							ps.setInt(30, region_id);
							ps.setInt(31, head_office_id);
							ps.setString(32, office_level_id);
							ps.setString(33, userid);
							ps.setTimestamp(34, ts);
							int kk = ps.executeUpdate();
						}
						flag = 2;
					}
					con.commit();
					if (flag == 1) {
						sendMessage(
								response,
								"Records Alredy Exist for given Office for given Financial Year ............ ",
								"ok", "Civil_Budget_Format_11.jsp");
					} else if (flag == 2) {
						sendMessage(response,
								"Records Saved Successfully ............ ",
								"ok", "Civil_Budget_Format_11.jsp");
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
					ps1.setString(4, "11");
					rs = ps1.executeQuery();
					if (rs.next()) {
						xml = xml + "<flag>Freezed</flag>";
					} else {
						ps = con
								.prepareStatement(" select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,DIVN_CODE,TYPE_OF_VEHICLE,REGN_NO,to_char(DATE_OF_PURCHASE,'DD/MM/YYYY') as DATE_OF_PURCHASE,NO_OF_KMS_DONE,AGE_OF_VEHICLE,to_char(DATE_OF_CONDEMNATION_FIT,'DD/MM/YYYY') as DATE_OF_CONDEMNATION_FIT,to_char(DATE_OF_CONDEMNATION_NOT_FIT,'DD/MM/YYYY') as DATE_OF_CONDEMNATION_NOT_FIT,SALARY_LY,FUEL_MATERIA_LY,ORDINARY_REPAIRS_LY,SPECIAL_REPAIRS_LY,TOTAL_COST_LY,SALARY_CY,FUEL_MATERIA_CY,ORDINARY_REPAIRS_CY,SPECIAL_REPAIRS_CY,TOTAL_COST_CY,SALARY_NY,FUEL_MATERIA_NY,ORDINARY_REPAIRS_NY,SPECIAL_REPAIRS_NY,TOTAL_COST_NY from FAS_BUDGET_FORMAT_11 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, FinancialYear);
						rs = ps.executeQuery();
						while (rs.next()) {
							xml = xml + "<slno>" + rs.getInt("SL_NO")
									+ "</slno>";

							xml = xml + "<Divn_Code2>" + rs.getInt("DIVN_CODE")
									+ "</Divn_Code2>";

							xml = xml + "<Type_of_Vehicle2>"
									+ rs.getString("TYPE_OF_VEHICLE")
									+ "</Type_of_Vehicle2>";

							xml = xml + "<Regn_No2>" + rs.getString("REGN_NO")
									+ "</Regn_No2>";

							xml = xml + "<Date_Of_Purchase2>"
									+ rs.getString("DATE_OF_PURCHASE")
									+ "</Date_Of_Purchase2>";

							xml = xml + "<No_of_Kms_Done2>"
									+ rs.getString("NO_OF_KMS_DONE")
									+ "</No_of_Kms_Done2>";

							xml = xml + "<Age_of_Vehicle2>"
									+ rs.getString("AGE_OF_VEHICLE")
									+ "</Age_of_Vehicle2>";

							xml = xml + "<Date_of_Condemnation_Fit2>"
									+ rs.getString("DATE_OF_CONDEMNATION_FIT")
									+ "</Date_of_Condemnation_Fit2>";

							xml = xml
									+ "<Date_of_Condemnation_Not_Fit2>"
									+ rs
											.getString("DATE_OF_CONDEMNATION_NOT_FIT")
									+ "</Date_of_Condemnation_Not_Fit2>";

							xml = xml + "<Salary_LY2>"
									+ rs.getFloat("SALARY_LY")
									+ "</Salary_LY2>";

							xml = xml + "<Fuel_Materia_LY2>"
									+ rs.getString("FUEL_MATERIA_LY")
									+ "</Fuel_Materia_LY2>";

							xml = xml + "<Ordinary_Repairs_LY2>"
									+ rs.getString("ORDINARY_REPAIRS_LY")
									+ "</Ordinary_Repairs_LY2>";

							xml = xml + "<Special_Repairs_LY2>"
									+ rs.getString("SPECIAL_REPAIRS_LY")
									+ "</Special_Repairs_LY2>";

							xml = xml + "<Total_Cost_LY2>"
									+ rs.getFloat("TOTAL_COST_LY")
									+ "</Total_Cost_LY2>";

							xml = xml + "<Salary_CY2>"
									+ rs.getFloat("SALARY_CY")
									+ "</Salary_CY2>";

							xml = xml + "<Fuel_Materia_CY2>"
									+ rs.getString("FUEL_MATERIA_CY")
									+ "</Fuel_Materia_CY2>";

							xml = xml + "<Ordinary_Repairs_CY2>"
									+ rs.getString("ORDINARY_REPAIRS_CY")
									+ "</Ordinary_Repairs_CY2>";

							xml = xml + "<Special_Repairs_CY2>"
									+ rs.getString("SPECIAL_REPAIRS_CY")
									+ "</Special_Repairs_CY2>";

							xml = xml + "<Total_Cost_CY2>"
									+ rs.getFloat("TOTAL_COST_CY")
									+ "</Total_Cost_CY2>";

							xml = xml + "<Salary_NY2>"
									+ rs.getFloat("SALARY_NY")
									+ "</Salary_NY2>";

							xml = xml + "<Fuel_Materia_NY2>"
									+ rs.getString("FUEL_MATERIA_NY")
									+ "</Fuel_Materia_NY2>";

							xml = xml + "<Ordinary_Repairs_NY2>"
									+ rs.getString("ORDINARY_REPAIRS_NY")
									+ "</Ordinary_Repairs_NY2>";

							xml = xml + "<Special_Repairs_NY2>"
									+ rs.getString("SPECIAL_REPAIRS_NY")
									+ "</Special_Repairs_NY2>";

							xml = xml + "<Total_Cost_NY2>"
									+ rs.getFloat("TOTAL_COST_NY")
									+ "</Total_Cost_NY2>";

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
									.prepareStatement(" select * from FAS_BUDGET_FORMAT_11 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and SL_NO=?");
							ps.setInt(1, cmbAcc_UnitCode);
							ps.setInt(2, cmbOffice_code);
							ps.setString(3, FinancialYear);
							ps.setInt(4, slno2);
							rs = ps.executeQuery();
							if (rs.next()) {
								ps1 = con
										.prepareStatement(" delete from FAS_BUDGET_FORMAT_11 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and SL_NO=?");
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
								"ok", "Civil_Budget_Format_11.jsp");
					} else if (flag == 3) {
						con.commit();
						sendMessage(response,
								"Record Does Not Exist ............ ", "ok",
								"Civil_Budget_Format_11.jsp");
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Deletion Failed ............ " + e, "ok",
							"Civil_Budget_Format_11.jsp");
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
					ps1.setString(4, "11");
					rs2 = ps1.executeQuery();
					if (rs2.next()) {
						sendMessage(
								response,
								"Civil Budget Format-11 have Already Freezed ............ ",
								"ok", "Civil_Budget_Format_11.jsp");
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
								.prepareStatement(" select * from FAS_BUDGET_FORMAT_11 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, FinancialYear);
						rs = ps.executeQuery();
						if (rs.next()) {
							ps1 = con
									.prepareStatement(" delete from FAS_BUDGET_FORMAT_11 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
							ps1.setInt(1, cmbAcc_UnitCode);
							ps1.setInt(2, cmbOffice_code);
							ps1.setString(3, FinancialYear);
							ps1.executeUpdate();
							for (int k = 0; k < RecordCount; k++) {

								/* Divn_Code */
								try {
									Divn_Code[k] = request
											.getParameter("Divn_Code" + k);
									if (Divn_Code[k] != null) {
										if (Divn_Code[k].equals("")) {
											Divn_Code2 = 0;
										} else {
											Divn_Code2 = Integer
													.parseInt(Divn_Code[k]);
										}
									} else {
										Divn_Code2 = 0;
									}
								} catch (Exception e) {
									System.out
											.println("Error for getting Head_of_Account -->"
													+ e);
								}

								/* Type_of_Vehicle */
								try {
									Type_of_Vehicle2 = request
											.getParameter("Type_of_Vehicle" + k);

								} catch (Exception e) {
									System.out.println(e);
								}

								/* Regn_No */
								try {
									Regn_No2 = request.getParameter("Regn_No"
											+ k);
								} catch (Exception e) {
									System.out.println(e);
								}

								/* Date_Of_Purchase */
								try {
									Date_Of_Purchase[k] = request
											.getParameter("Date_Of_Purchase"
													+ k);

									if (!Date_Of_Purchase[k]
											.equalsIgnoreCase("")) {
										sd = Date_Of_Purchase[k].split("/");
										c = new GregorianCalendar(Integer
												.parseInt(sd[2]), Integer
												.parseInt(sd[1]) - 1, Integer
												.parseInt(sd[0]));
										d = c.getTime();
										Date_Of_Purchase2 = new Date(d
												.getTime());
									}else{
										Date_Of_Purchase2 =null;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								/* No_of_Kms_Done */
								try {

									No_of_Kms_Done2 = request
											.getParameter("No_of_Kms_Done" + k);
								} catch (Exception e) {
									System.out.println(e);
								}

								/* Age_of_Vehicle */
								try {
									Age_of_Vehicle2 = request
											.getParameter("Age_of_Vehicle" + k);
								} catch (Exception e) {
									System.out.println(e);
								}

								/* Date_of_Condemnation_Fit */
								try {
									Date_of_Condemnation_Fit[k] = request
											.getParameter("Date_of_Condemnation_Fit"
													+ k);

									if (!Date_of_Condemnation_Fit[k]
											.equalsIgnoreCase("")) {
										sd = Date_of_Condemnation_Fit[k]
												.split("/");
										c = new GregorianCalendar(Integer
												.parseInt(sd[2]), Integer
												.parseInt(sd[1]) - 1, Integer
												.parseInt(sd[0]));
										d = c.getTime();
										Date_of_Condemnation_Fit2 = new Date(d
												.getTime());
									}else{
										Date_of_Condemnation_Fit2 =null;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								/* Date_of_Condemnation_Not_Fit */
								try {
									Date_of_Condemnation_Not_Fit[k] = request
											.getParameter("Date_of_Condemnation_Not_Fit"
													+ k);

									if (!Date_of_Condemnation_Not_Fit[k]
											.equalsIgnoreCase("")) {
										sd = Date_of_Condemnation_Not_Fit[k]
												.split("/");
										c = new GregorianCalendar(Integer
												.parseInt(sd[2]), Integer
												.parseInt(sd[1]) - 1, Integer
												.parseInt(sd[0]));
										d = c.getTime();
										Date_of_Condemnation_Not_Fit2 = new Date(
												d.getTime());
									}else{
										Date_of_Condemnation_Not_Fit2 =null;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								/* Salary_LY */
								try {
									Salary_LY[k] = request
											.getParameter("Salary_LY" + k);
									if (Salary_LY[k] != null) {
										if (Salary_LY[k].equals("")) {
											Salary_LY2 = 0.0f;
										} else {
											Salary_LY2 = Float
													.parseFloat(Salary_LY[k]);
										}
									} else {
										Salary_LY2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								/* Fuel_Materia_LY */
								try {
									Fuel_Materia_LY2 = request
											.getParameter("Fuel_Materia_LY" + k);
								} catch (Exception e) {
									System.out
											.println("Error for getting Fuel_Materia_LY -->"
													+ e);
								}

								/* Ordinary_Repairs_LY */
								try {
									Ordinary_Repairs_LY2 = request
											.getParameter("Ordinary_Repairs_LY"
													+ k);
								} catch (Exception e) {
									System.out
											.println("Error for getting Ordinary_Repairs_LY -->"
													+ e);
								}

								/* Special_Repairs_LY */
								try {
									Special_Repairs_LY2 = request
											.getParameter("Special_Repairs_LY"
													+ k);
								} catch (Exception e) {
									System.out
											.println("Error for getting Special_Repairs_LY -->"
													+ e);
								}

								/* Total_Cost_LY */
								try {
									Total_Cost_LY[k] = request
											.getParameter("Total_Cost_LY" + k);
									if (Total_Cost_LY[k] != null) {
										if (Total_Cost_LY[k].equals("")) {
											Total_Cost_LY2 = 0.0f;
										} else {
											Total_Cost_LY2 = Float
													.parseFloat(Total_Cost_LY[k]);
										}
									} else {
										Total_Cost_LY2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								/* Salary_CY */
								try {
									Salary_CY[k] = request
											.getParameter("Salary_CY" + k);
									if (Salary_CY[k] != null) {
										if (Salary_CY[k].equals("")) {
											Salary_CY2 = 0.0f;
										} else {
											Salary_CY2 = Float
													.parseFloat(Salary_CY[k]);
										}
									} else {
										Salary_CY2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								/* Fuel_Materia_CY */
								try {
									Fuel_Materia_CY2 = request
											.getParameter("Fuel_Materia_CY" + k);
								} catch (Exception e) {
									System.out
											.println("Error for getting Fuel_Materia_CY -->"
													+ e);
								}

								/* Ordinary_Repairs_CY */
								try {
									Ordinary_Repairs_CY2 = request
											.getParameter("Ordinary_Repairs_CY"
													+ k);
								} catch (Exception e) {
									System.out
											.println("Error for getting Ordinary_Repairs_CY -->"
													+ e);
								}

								/* Special_Repairs_CY */
								try {
									Special_Repairs_CY2 = request
											.getParameter("Special_Repairs_CY"
													+ k);
								} catch (Exception e) {
									System.out
											.println("Error for getting Special_Repairs_CY -->"
													+ e);
								}

								/* Total_Cost_CY */
								try {
									Total_Cost_CY[k] = request
											.getParameter("Total_Cost_CY" + k);
									if (Total_Cost_CY[k] != null) {
										if (Total_Cost_CY[k].equals("")) {
											Total_Cost_CY2 = 0.0f;
										} else {
											Total_Cost_CY2 = Float
													.parseFloat(Total_Cost_CY[k]);
										}
									} else {
										Total_Cost_CY2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								/* Salary_NY */
								try {
									Salary_NY[k] = request
											.getParameter("Salary_NY" + k);
									if (Salary_NY[k] != null) {
										if (Salary_NY[k].equals("")) {
											Salary_NY2 = 0.0f;
										} else {
											Salary_NY2 = Float
													.parseFloat(Salary_NY[k]);
										}
									} else {
										Salary_NY2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								/* Fuel_Materia_NY */
								try {
									Fuel_Materia_NY2 = request
											.getParameter("Fuel_Materia_NY" + k);
								} catch (Exception e) {
									System.out
											.println("Error for getting Fuel_Materia_NY -->"
													+ e);
								}

								/* Ordinary_Repairs_NY */
								try {
									Ordinary_Repairs_NY2 = request
											.getParameter("Ordinary_Repairs_NY"
													+ k);
								} catch (Exception e) {
									System.out
											.println("Error for getting Ordinary_Repairs_NY -->"
													+ e);
								}

								/* Special_Repairs_NY */
								try {
									Special_Repairs_NY2 = request
											.getParameter("Special_Repairs_NY"
													+ k);
								} catch (Exception e) {
									System.out
											.println("Error for getting Special_Repairs_NY -->"
													+ e);
								}

								/* Total_Cost_NY */
								try {
									Total_Cost_NY[k] = request
											.getParameter("Total_Cost_NY" + k);
									if (Total_Cost_NY[k] != null) {
										if (Total_Cost_NY[k].equals("")) {
											Total_Cost_NY2 = 0.0f;
										} else {
											Total_Cost_NY2 = Float
													.parseFloat(Total_Cost_NY[k]);
										}
									} else {
										Total_Cost_NY2 = 0.0f;
									}
								} catch (Exception e) {
									System.out.println(e);
								}

								int i = 1, i1 = 0;
								try {
									ps = con
											.prepareStatement("Select max(SL_NO) from FAS_BUDGET_FORMAT_11");
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
										.prepareStatement("insert into FAS_BUDGET_FORMAT_11 (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,DIVN_CODE,TYPE_OF_VEHICLE,REGN_NO,DATE_OF_PURCHASE,NO_OF_KMS_DONE,AGE_OF_VEHICLE,DATE_OF_CONDEMNATION_FIT,DATE_OF_CONDEMNATION_NOT_FIT,SALARY_LY,FUEL_MATERIA_LY,ORDINARY_REPAIRS_LY,SPECIAL_REPAIRS_LY,TOTAL_COST_LY,SALARY_CY,FUEL_MATERIA_CY,ORDINARY_REPAIRS_CY,SPECIAL_REPAIRS_CY,TOTAL_COST_CY,SALARY_NY,FUEL_MATERIA_NY,ORDINARY_REPAIRS_NY,SPECIAL_REPAIRS_NY,TOTAL_COST_NY,DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
								ps.setInt(1, cmbAcc_UnitCode);
								ps.setInt(2, cmbOffice_code);
								ps.setString(3, FinancialYear);
								ps.setInt(4, i);
								ps.setInt(5, Divn_Code2);
								ps.setString(6, Type_of_Vehicle2);
								ps.setString(7, Regn_No2);
								ps.setDate(8, Date_Of_Purchase2);
								ps.setString(9, No_of_Kms_Done2);
								ps.setString(10, Age_of_Vehicle2);
								ps.setDate(11, Date_of_Condemnation_Fit2);
								ps.setDate(12, Date_of_Condemnation_Not_Fit2);
								ps.setFloat(13, Salary_LY2);
								ps.setString(14, Fuel_Materia_LY2);
								ps.setString(15, Ordinary_Repairs_LY2);
								ps.setString(16, Special_Repairs_LY2);
								ps.setFloat(17, Total_Cost_LY2);
								ps.setFloat(18, Salary_CY2);
								ps.setString(19, Fuel_Materia_CY2);
								ps.setString(20, Ordinary_Repairs_CY2);
								ps.setString(21, Special_Repairs_CY2);
								ps.setFloat(22, Total_Cost_CY2);
								ps.setFloat(23, Salary_NY2);
								ps.setString(24, Fuel_Materia_NY2);
								ps.setString(25, Ordinary_Repairs_NY2);
								ps.setString(26, Special_Repairs_NY2);
								ps.setFloat(27, Total_Cost_NY2);
								ps.setInt(28, division_id);
								ps.setInt(29, circle_id);
								ps.setInt(30, region_id);
								ps.setInt(31, head_office_id);
								ps.setString(32, office_level_id);
								ps.setString(33, userid);
								ps.setTimestamp(34, ts);
								int kk = ps.executeUpdate();
							}
							con.commit();
							sendMessage(
									response,
									"Records Updated Successfully ............ ",
									"ok", "Civil_Budget_Format_11.jsp");
						} else {
							sendMessage(response,
									"Records Does Not Exist ............ ",
									"ok", "Civil_Budget_Format_11.jsp");
						}
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Updation Failed ............ " + e, "ok",
							"Civil_Budget_Format_11.jsp");
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
					"ok", "Civil_Budget_Format_11.jsp");
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
