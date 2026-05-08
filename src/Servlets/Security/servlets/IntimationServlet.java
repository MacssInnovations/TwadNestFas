package Servlets.Security.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

/**
 * Servlet implementation class IntimationServlet
 */
public class IntimationServlet extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public IntimationServlet() {
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
		ResultSet rs = null,resuse_id=null,resuse_id1=null;
		ResultSet rss = null;
		ResultSet rss2 = null,resuse=null;
		ResultSet rs2 = null,resu=null;
		PreparedStatement ps = null,prepst=null;
		PreparedStatement pss = null,prepst_id=null,prepst_id1=null;
		PreparedStatement pss2 = null;
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

//			ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":"
//					+ strportno.trim() + ":" + strsid.trim();
			
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
		
		int office_id = 0;
		int Acc_Unit_ID = 0;
		if (strCommand.equalsIgnoreCase("get")) {
			
                System.out.println("hereeeeeeeee this is intimation servlet::");
					xml = "<response><command>get</command>";
					try {
						ps = connection.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");
					/*	ps = connection.prepareStatement("SELECT "+
						"  Case  "+
						"    WHEN Old_Office_Id IS NOT NULL and DATE_ALLOWED_UPTO>='03-feb-2012'"+
						"    THEN OLD_OFFICE_ID"+
						"   ELSE Office_Id"+
						" END AS office_id"+
						" FROM  "+
						" (SELECT Office_Id, "+
						"   OLD_OFFICE_ID, "+
						"   DATE_ALLOWED_UPTO "+
						" FROM Hrm_Emp_Current_Posting "+
						" WHERE EMPLOYEE_ID=? )");  */
				ps.setInt(1, empid);
				System.out.println("empid::::"+empid);
				results = ps.executeQuery();
				if (results.next()) {

					office_id = results.getInt("OFFICE_ID");
					System.out.println("office_id:::"+office_id);
				}
				if (office_id != 5000) {
					ps = connection.prepareStatement("select ACCOUNTING_UNIT_ID from (select OFFICE_ID from "
									+ "HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=? )x left outer join "
									+ "(select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID from "
									+ "FAS_MST_ACCT_UNITS )y on x.OFFICE_ID = y.ACCOUNTING_UNIT_OFFICE_ID");
			/*		ps = connection.prepareStatement("SELECT ACCOUNTING_UNIT_ID "+
					" From "+
							"   ( "+
							" SELECT "+
							" CASE "+
							" WHEN Old_Office_Id   IS NOT NULL "+
							" AND DATE_ALLOWED_UPTO>='03-feb-2012' "+
							" THEN OLD_OFFICE_ID "+
							" ELSE Office_Id "+
							" END AS OFFICE_ID "+
							" FROM "+
							" (SELECT Office_Id, "+
							" OLD_OFFICE_ID, "+
							" DATE_ALLOWED_UPTO "+
							" From Hrm_Emp_Current_Posting "+
							" Where Employee_Id=? "+
							"  ) "+
							"  )x "+
							" LEFT OUTER JOIN "+
							" (SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID FROM FAS_MST_ACCT_UNITS "+
							"  )y "+
							" ON x.OFFICE_ID = y.ACCOUNTING_UNIT_OFFICE_ID");  */
					 ps.setInt(1, empid);
					results = ps.executeQuery();
					if (results.next()) {
						Acc_Unit_ID = results.getInt("ACCOUNTING_UNIT_ID");
					}
				} else {
					ps = connection
							.prepareStatement("select ACCOUNTING_UNIT_ID from (select office_wing_sino as "
									+ "office_wing_sino1 from HRM_EMP_CURRENT_WING where employee_id=?)x "
									+ "left outer join (select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID,"
									+ "office_wing_sino from FAS_MST_ACCT_UNITS where "
									+ "ACCOUNTING_UNIT_OFFICE_ID=?)y on "
									+ "x.office_wing_sino1 = y.office_wing_sino");
					ps.setInt(1, empid);
					ps.setInt(2, office_id);
					results = ps.executeQuery();
					if (results.next()) {

						Acc_Unit_ID = results.getInt("ACCOUNTING_UNIT_ID");
					}
				}
				
				if(Acc_Unit_ID!=0)
				{
				ps1 = connection
						.prepareStatement("select count(TRF_ACCOUNTING_UNIT_ID) as count,TPA_TYPE from "
								+ "FAS_TPA_MASTER where TRF_ACCOUNTING_UNIT_ID=? and ACCEPTANCE_STATUS "
								+ "is null and STATUS='L' group by TPA_TYPE");

				ps1.setInt(1, Acc_Unit_ID);
				rs = ps1.executeQuery();
				while (rs.next()) {
					xml = xml + "<No_of_proforma_raised>" + rs.getInt("count")
							+ "</No_of_proforma_raised>";

					xml = xml + "<TPA_Type>" + rs.getString("TPA_TYPE")
							+ "</TPA_Type>";
				}
				xml = xml + "<flag>success</flag>";

			/*	String ss="SELECT SUM(COUNT) AS COUNT,   TPA_TYPE,  ACCEPTANCE_STATUS FROM   "
								+ "(SELECT *   FROM    (SELECT COUNT(ACCOUNTING_UNIT_ID) AS COUNT,       "
								+ "TPA_TYPE,      ACCEPTANCE_STATUS,       cashbook_year,      cashbook_month,"
								+ "       voucher_no    FROM FAS_TPA_MASTER     WHERE ACCOUNTING_UNIT_ID= "+Acc_Unit_ID+" "
								+ "AND ACCEPTANCE_STATUS  IS NOT NULL     GROUP BY TPA_TYPE, "
								+ "     ACCEPTANCE_STATUS,      cashbook_year,      cashbook_month,      "
								+ " voucher_no     )x  LEFT OUTER JOIN  " 
								+ "  (SELECT distinct m.cashbook_year AS cby,      "
								+ "m.cashbook_month      AS cbm,       m.voucher_no          AS vn    "
								+ "FROM FAS_JOURNAL_TRANSACTION t, Fas_Journal_Master m    " +
								" WHERE m.accounting_unit_id =t.accounting_unit_id " +
								" AND m.cashbook_year        = t.cashbook_year AND " +
								" m.cashbook_month       = t.cashbook_month " +
								"And M.Voucher_No           = T.Voucher_No  And M.Journal_Status       ='L' " +
								" and verified IS NULL     )y   "
								+ "ON x.cashbook_year   = y.cby   AND x.cashbook_month = y.cbm   "
								+ "AND x.voucher_no     = y.vn   ) WHERE cby IS NOT NULL AND cbm   "
								+ "IS NOT NULL AND vn    IS NOT NULL GROUP BY TPA_TYPE,  ACCEPTANCE_STATUS";
				*/
                String ss="SELECT SUM(COUNT) AS COUNT,\n" + 
                "  TPA_TYPE,\n" + 
                "  ACCEPTANCE_STATUS\n" + 
                "FROM\n" + 
                "  (SELECT *\n" + 
                "  FROM\n" + 
                "    (SELECT COUNT(ACCOUNTING_UNIT_ID) AS COUNT,\n" + 
                "      TPA_TYPE,\n" + 
                "      ACCEPTANCE_STATUS,\n" + 
                "      cashbook_year,\n" + 
                "      cashbook_month,\n" + 
                "      voucher_no\n" + 
                "    From Fas_Tpa_Master\n" + 
                "    WHERE ACCOUNTING_UNIT_ID=" +Acc_Unit_ID+"    AND ACCEPTANCE_STATUS  IS NOT NULL and STATUS='L' \n" + 
                "    GROUP BY TPA_TYPE,\n" + 
                "      ACCEPTANCE_STATUS,\n" + 
                "      cashbook_year,\n" + 
                "      cashbook_month,\n" + 
                "      voucher_no\n" + 
                "    )x\n" + 
                "  Left Outer Join\n" + 
                "    (Select distinct m.Cashbook_Year As Cby,\n" + 
                "      m.Cashbook_Month      As Cbm,\n" + 
                "      m.Voucher_No          As Vn\n" + 
                "    From Fas_Journal_Transaction T,\n" + 
                "    Fas_Journal_Master M\n" + 
                "    where m.accounting_unit_id =t.accounting_unit_id\n" + 
                "    AND m.cashbook_year        = t.cashbook_year\n" + 
                "    AND m.cashbook_month       = t.cashbook_month\n" + 
                "    And M.Voucher_No           = T.Voucher_No\n" + 
                "    And M.Journal_Status       ='L'\n" + 
                "    And T.Verified Is Null\n" + 
                "    )y\n" + 
                "  ON x.cashbook_year   = y.cby\n" + 
                "  AND x.cashbook_month = y.cbm\n" + 
                "  AND x.voucher_no     = y.vn\n" + 
                "  )\n" + 
                "WHERE cby IS NOT NULL\n" + 
                "AND cbm   IS NOT NULL\n" + 
                "AND vn    IS NOT NULL\n" + 
                "GROUP BY TPA_TYPE,\n" + 
                "  ACCEPTANCE_STATUS";
             	System.out.println("ss:::::::"+ss);				
				ps2 = connection.prepareStatement(ss);
				
				rs2 = ps2.executeQuery();
				while (rs2.next()) {
				//	System.out.println("while:");
					xml = xml + "<No_of_proforma_status>" + rs2.getInt("count")
							+ "</No_of_proforma_status>";

					xml = xml + "<TPA_Type1>" + rs2.getString("TPA_TYPE")
							+ "</TPA_Type1>";

					xml = xml + "<Acceptance_Status>"
							+ rs2.getString("ACCEPTANCE_STATUS")
							+ "</Acceptance_Status>";
				}
				xml = xml + "<flag1>success</flag1>";

				// 15-06-2011
				pss = connection.prepareStatement("(SELECT SUM(COUNT) AS COUNT,\n"
								+ "  'TCA'            AS TDA_OR_TCA\n"
								+ "FROM\n"
								+ "  (SELECT COUNT(ACCOUNTING_UNIT_ID) AS COUNT,\n"
								+ "    TDA_OR_TCA\n"
								+ "  FROM FAS_TDA_TCA_RAISED_MST\n"
								+ "  WHERE ACCOUNTING_UNIT_ID=?\n"
								+ "  AND TDA_OR_TCA         IN('TCACB','TCAO')\n"
								+ "  AND ORGINATING_JVR_NO  IS NOT NULL\n"
								+ "  AND ACCEPTANCE_STATUS  IS NULL\n"
								+ "  AND STATUS              ='L'\n"
								+ "  GROUP BY TDA_OR_TCA\n"
								+ "  )\n"
								+ "GROUP BY 'TCA'\n"
								+ ")\n"
								+ "UNION\n"
								+ "\n"
								+ "(SELECT SUM(COUNT) AS COUNT,\n"
								+ "  'TDA'            AS TDA_OR_TCA from\n"
								+ "  (SELECT COUNT(ACCOUNTING_UNIT_ID) AS COUNT,\n"
								+ "    TDA_OR_TCA\n"
								+ "  FROM FAS_TDA_TCA_RAISED_MST\n"
								+ "  WHERE ACCOUNTING_UNIT_ID=?\n"
								+ "  AND TDA_OR_TCA         IN('TDACB','TDAO')\n"
								+ "  AND ORGINATING_JVR_NO  IS NOT NULL\n"
								+ "  AND ACCEPTANCE_STATUS  IS NULL\n"
								+ "  AND STATUS              ='L'\n"
								+ "  GROUP BY TDA_OR_TCA\n" + "  )\n" + " \n"
								+ "  GROUP BY 'TDA' )");
System.out.println("Acc_Unit_ID:::"+Acc_Unit_ID);
				pss.setInt(1, Acc_Unit_ID);
				pss.setInt(2, Acc_Unit_ID);
				rss = pss.executeQuery();
				while (rss.next()) {
					xml = xml + "<No_of_TDA_raised1>" + rss.getInt("COUNT")
							+ "</No_of_TDA_raised1>";

					xml = xml + "<TDA_OR_TCA1>" + rss.getString("TDA_OR_TCA")
							+ "</TDA_OR_TCA1>";
				}
				xml = xml + "<flag22>success</flag22>";

				ps1 = connection
						.prepareStatement("(SELECT SUM(COUNT) AS COUNT,\n"
								+ "  'TCA'            AS TDA_OR_TCA\n"
								+ "FROM\n"
								+ "  (SELECT COUNT(TRF_ACCOUNTING_UNIT_ID) AS COUNT,\n"
								+ "    TDA_OR_TCA\n"
								+ "  FROM FAS_TDA_TCA_RAISED_MST\n"
								+ "  WHERE TRF_ACCOUNTING_UNIT_ID=?\n"
								+ "  AND TDA_OR_TCA             IN('TCACB','TCAO')\n"
								+ "  AND ORGINATING_JVR_NO      IS NOT NULL\n"
								+ "  AND ACCEPTANCE_STATUS      IS NULL\n"
								+ "  AND STATUS                  ='L'\n"
								+ "  GROUP BY TDA_OR_TCA\n"
								+ "  )\n"
								+ "GROUP BY 'TCA'\n"
								+ ")\n"
								+ "UNION\n"
								+ "(SELECT SUM(COUNT) AS COUNT,\n"
								+ "  'TDA'            AS TDA_OR_TCA\n"
								+ "FROM\n"
								+ "  (SELECT COUNT(TRF_ACCOUNTING_UNIT_ID) AS COUNT,\n"
								+ "    TDA_OR_TCA\n"
								+ "  FROM FAS_TDA_TCA_RAISED_MST\n"
								+ "  WHERE TRF_ACCOUNTING_UNIT_ID=?\n"
								+ "  AND TDA_OR_TCA             IN('TDAA','TDAO')\n"
								+ "  AND ORGINATING_JVR_NO      IS NOT NULL\n"
								+ "  AND ACCEPTANCE_STATUS      IS NULL\n"
								+ "  AND STATUS                  ='L'\n"
								+ "  GROUP BY TDA_OR_TCA\n"
								+ "  )GROUP BY 'TDA'\n" + ")");

				ps1.setInt(1, Acc_Unit_ID);
				ps1.setInt(2, Acc_Unit_ID);
				rs = ps1.executeQuery();
				while (rs.next()) {
					xml = xml + "<No_of_TDA_raised4>" + rs.getInt("COUNT")
							+ "</No_of_TDA_raised4>";

					xml = xml + "<TDA_OR_TCA4>" + rs.getString("TDA_OR_TCA")
							+ "</TDA_OR_TCA4>";
				}
				xml = xml + "<flag2>success</flag2>";

				// 15-06-2011

				pss2 = connection.prepareStatement("SELECT COUNT(ACCOUNTING_UNIT_ID) AS COUNT,\n"
								+ "  TDA_OR_TCA,\n"
								+ "  ACCEPTANCE_STATUS\n"
								+ "FROM FAS_TDA_TCA_RAISED_MST\n"
								+ "WHERE ACCOUNTING_UNIT_ID=?\n"
								+ "AND ORGINATING_JVR_NO  IS NOT NULL\n"
								+ "AND ACCEPTANCE_STATUS  IS NOT NULL\n"
								+ "AND RESPONDING_JVR_NO  IS NULL\n"
								+ "AND STATUS              ='L'\n"
								+ "GROUP BY TDA_OR_TCA,\n"
								+ "  ACCEPTANCE_STATUS");
				pss2.setInt(1, Acc_Unit_ID);
				rss2 = pss2.executeQuery();
				while (rss2.next()) {
					xml = xml + "<No_of_TDA_status3>" + rss2.getInt("COUNT")
							+ "</No_of_TDA_status3>";

					xml = xml + "<TDA_OR_TCA3>" + rss2.getString("TDA_OR_TCA")
							+ "</TDA_OR_TCA3>";

					xml = xml + "<Acceptance_Status3>"
							+ rss2.getString("ACCEPTANCE_STATUS")
							+ "</Acceptance_Status3>";
				}
				xml = xml + "<flag33>success</flag33>";

				ps2 = connection
						.prepareStatement("SELECT COUNT(TRF_ACCOUNTING_UNIT_ID) AS COUNT,\n"
								+ "  TDA_OR_TCA,\n"
								+ "  ACCEPTANCE_STATUS\n"
								+ "FROM FAS_TDA_TCA_RAISED_MST\n"
								+ "WHERE TRF_ACCOUNTING_UNIT_ID=?\n"
								+ "AND ORGINATING_JVR_NO  IS NOT NULL\n"
								+ "AND ACCEPTANCE_STATUS  IS NOT NULL\n"
								+ "AND RESPONDING_JVR_NO  IS NULL\n"
								+ "AND STATUS              ='L'\n"
								+ "GROUP BY TDA_OR_TCA,\n"
								+ "  ACCEPTANCE_STATUS");

				ps2.setInt(1, Acc_Unit_ID);
				rs2 = ps2.executeQuery();
				while (rs2.next()) {
					xml = xml + "<No_of_TDA_status>" + rs2.getInt("COUNT")
							+ "</No_of_TDA_status>";

					xml = xml + "<TDA_OR_TCA>" + rs2.getString("TDA_OR_TCA")
							+ "</TDA_OR_TCA>";

					xml = xml + "<Acceptance_Status>"
							+ rs2.getString("ACCEPTANCE_STATUS")
							+ "</Acceptance_Status>";
				}
				xml = xml + "<flag3>success</flag3>";
				}//end of o unit code
			} catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
			
			int wing_id = 0;
			try {
				Date date = new Date();
				SimpleDateFormat sdf;
				sdf = new SimpleDateFormat("MM");

				Calendar cal = Calendar.getInstance();
				int year = cal.get(Calendar.YEAR);
				if (office_id != 5000) {
					xml = xml + "<office_id>" + office_id + "</office_id>";
					ps = connection
							.prepareStatement(" SELECT COUNT(t.TRANSFER_TO_OFFICE_ID) AS total\n" + 
							"FROM FAS_FUND_TRF_FROM_HO_TRN t,\n" + 
							"FAS_FUND_TRF_FROM_HO_MASTER m\n" + 
							"WHERE \n" + 
							"m.cashbook_year=t.cashbook_year\n" + 
							"and m.cashbook_month=t.cashbook_month and\n" + 
							"m.voucher_no=t.voucher_no and m.transfer_status='L' and t.auto_status is null \n" + 
							"and t.TRANSFER_TO_OFFICE_ID=?\n" + 
							"AND t.CASHBOOK_YEAR=? \n" + 
							"AND t.CASHBOOK_MONTH=?\n ");
				} else if (office_id == 5000) {
					xml = xml + "<office_id>" + office_id + "</office_id>";
					ps1 = connection
							.prepareStatement("SELECT OFFICE_WING_SINO FROM HRM_EMP_CURRENT_WING WHERE EMPLOYEE_ID=?");
					ps1.setInt(1, empid);
					results2 = ps1.executeQuery();
					if (results2.next()) {

						wing_id = results2.getInt("OFFICE_WING_SINO");
					}
					xml = xml + "<wing_id>" + wing_id + "</wing_id>";

					if (wing_id == 2) {
						ps = connection
								.prepareStatement(" SELECT COUNT(ACCOUNTING_FOR_OFFICE_ID) as total " +
                                                                "FROM FAS_FUND_TRF_FROM_OFFICE WHERE CASHBOOK_YEAR=? AND CASHBOOK_MONTH=? and TRANSFER_STATUS='L' and auto_status is null");
					}
				}
				if (office_id != 5000) {
					ps.setInt(1, office_id);
					ps.setInt(2, year);
					ps.setInt(3, Integer.parseInt(sdf.format(date)));
					results = ps.executeQuery();
					while (results.next()) {
						xml = xml + "<No_of_Transfers_to_Office_ID>"
								+ results.getInt("total")
								+ "</No_of_Transfers_to_Office_ID>";
					}
				} else if ((office_id == 5000) && (wing_id == 2)) {					
					ps.setInt(1, year);
					ps.setInt(2, Integer.parseInt(sdf.format(date)));
					results = ps.executeQuery();
					while (results.next()) {
						xml = xml + "<No_of_Transfers_to_Office_ID>"
								+ results.getInt("total")
								+ "</No_of_Transfers_to_Office_ID>";
					}
				}
                                else {
                                    xml = xml + "<No_of_Transfers_to_Office_ID>"+"0"+ "</No_of_Transfers_to_Office_ID>";
                                }
				xml = xml + "<flag10>success</flag10>";
			} catch (Exception e) {
				xml = xml + "<flag10>failure</flag10>";
				e.printStackTrace();
			}
			try
			{
				PreparedStatement prep = connection.prepareStatement("SELECT COUNT(*)as counted "+
					"	FROM FAS_ADJUST_MEMO_MST m,FAS_ADJUST_MEMO_TRN t  "+
						" WHERE m.ACCOUNTING_UNIT_ID    =t.ACCOUNTING_UNIT_ID "+
						" AND m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID "+
						" AND m.CASHBOOK_YEAR           =t.CASHBOOK_YEAR "+
						" AND m.CASHBOOK_MONTH          =t.CASHBOOK_MONTH "+
						" AND m.VOUCHER_NO              =t.VOUCHER_NO "+
						" AND m.MEMO_STATUS             ='L' "+
						" AND t.acceptance_status      IS NULL "+
						" AND t.FOR_ACCOUNTING_UNIT_ID  =?");
				prep.setInt(1,Acc_Unit_ID);
				resu=prep.executeQuery();
				if(resu.next())
				{
					if(resu.getInt("counted")!=0)
					{
					xml = xml + "<flag_adj>success</flag_adj>";
					xml = xml + "<no_adjust_memo>"+ resu.getInt("counted")+ "</no_adjust_memo>";
					}
					else
					{
						xml = xml + "<flag_adj>failure</flag_adj>";	
					}
				}
				else
				{
					xml = xml + "<flag_adj>failure</flag_adj>";	
				}
			}
			catch(Exception ee)
			{
				System.out.println("ee in adjust memo::"+ee);
			}
			//verify a52 and aa52
			try
			{
				PreparedStatement prepsta = connection.prepareStatement("SELECT OFFICE_NAME,OFFICE_LEVEL_ID,OFFICE_ID FROM COM_MST_OFFICES WHERE office_status_id NOT IN('CL','RD','NC') and OFFICE_ID="+office_id);
				
				ResultSet resuset=prepsta.executeQuery();
				if(resuset.next())
				{
						xml = xml + "<flag_offices>success</flag_offices>";	
						xml = xml + "<off_level_id>"+ resuset.getString("OFFICE_LEVEL_ID")+ "</off_level_id>";
				}
				else
				{
					xml = xml + "<flag_offices>failure</flag_offices>";	
				}
			}
			catch(Exception ee)
			{
				System.out.println("ee in adjust memo::"+ee);
			}
			
			//TDA-TCA VERIFICATION DIFFERENCE UNITS
			try
			{
				 prepst = connection.prepareStatement("select ACCOUNTING_UNIT_ID from TDA_TCA_VERIFICATIONERRORUNITS where ACCOUNTING_UNIT_ID="+Acc_Unit_ID);
				resuse=prepst.executeQuery();
				if(resuse.next())
				{
					
					xml = xml + "<tda_error>success</tda_error>";
					
				}
				else
				{
					xml = xml + "<tda_error>failure</tda_error>";	
				}
			}
			catch(Exception ee)
			{
				System.out.println("ee inTDA_TCA_VERIFICATIONERRORUNITS ::"+ee);
			}
			//A52 check for CE offices
			try
			{
				 prepst_id = connection.prepareStatement("SELECT OFFICE_ID,OFFICE_NAME FROM com_mst_all_offices_view " +
				 		"WHERE OFFICE_LEVEL_ID ='RN' AND REGION_OFFICE_ID  ="+office_id);
				 resuse_id=prepst_id.executeQuery();
				if(resuse_id.next())
				{
					
					//check freeze or in fas_budget_clousure_allocation
					String checqry="select count(*) as coun from  FAS_BUDGET_CLOSURE_ALLOCATION where ACCOUNTING_FOR_OFFICE_ID="+office_id;
					 prepst_id1 = connection.prepareStatement(checqry);
						 resuse_id1=prepst_id1.executeQuery();
						 xml = xml + "<verify_a52_ce>verify</verify_a52_ce>";
						 if(resuse_id1.next()){
							 
							 int cc=resuse_id1.getInt("coun");
							 if(cc==10){
								 xml = xml + "<verify_a52_ce_status>fullverify</verify_a52_ce_status>"; 
							 }else{
								 xml = xml + "<verify_a52_ce_status>notfullverify</verify_a52_ce_status>";  
							 } 
							
						 }/*else{
							 xml = xml + "<verify_a52_ce_status>notfullverify</verify_a52_ce_status>"; 
						 }*/
				}
				else
				{
					xml = xml + "<verify_a52_ce>noverify</verify_a52_ce>";	
				}
			}
			catch(Exception ee)
			{
				System.out.println("ee in verify_a52_ce ::"+ee);
			}
			
		}
		       

		
		xml = xml + "</response>";
		out.write(xml);
		System.out.println(xml);
	}

}
