package Servlets.FAS.FAS1.CivilBudget.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.CallableStatement;
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
 * Servlet implementation class Civil_Budget_Format_1
 */
public class Civil_Budget_Format_1 extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Civil_Budget_Format_1() {
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
			//System.out.println("chk 3");
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
			int count=0;
			xml = "<response><command>get</command>";
			int year1 = Integer.parseInt(request.getParameter("y1"));
			int year2 = Integer.parseInt(request.getParameter("y2"));
			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			try
			{
				String splitlastyr=String.valueOf(year2);
				String ssyr1=splitlastyr.substring(2,4);
				
				/*String ss=" select t.budger_g_id,t.BUDGET_GROUP_MAJOR, "+
					" c.* from "+
					" (SELECT budger_g_id, "+
					" BUDGET_GROUP_MAJOR, "+
					" ACC_HEAD_CODE "+
					" FROM "+
					" (SELECT DISTINCT BUDGET_GROUP_ID AS budger_g_id, "+
					" ACC_HEAD_CODE "+
					" FROM FAS_BUDGET_AC_HEADS_MAP "+
					" WHERE FORMAT_NO=1 "+
					" ORDER BY BUDGET_GROUP_ID "+
					" )a "+
					" left outer join"+
					" (SELECT BUDGET_GROUP_ID, "+
					" BUDGET_GROUP_MAJOR "+
					" FROM FAS_BUDGET_GROUP_MASTER "+
					" ORDER BY BUDGET_GROUP_ID "+
					"  )b "+
					"   ON a.budger_g_id =b.BUDGET_GROUP_ID)t "+
					"   inner join "+
					"   ( "+
					"  select head_of_account,actuals_for_last_year AS Ac_fr_lst_yr, "+
					"   be_for_the_year, "+
					"   actuals_for_period_apr_to_nov AS Actual_fr_Period_Apr_Nov, "+
				  " ANTICIPATED_FR_PERIOD_DEC_MAR,RE_FOR_YEAR, "+
					" VARIATION_BETWEN_BE_RE,REASON_FOR_VARIATION,BE_FOR_NEXT_YEAR, "+
					" VARIATION_BTWN_REYR_AND_NXTYR,UPDATED_BY_USERID,UPDATED_DATE, "+
					"  DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,BUDGET_GROUP_ID, "+
					"  SL_NO from FAS_BUDGET_FORMAT_1 WHERE ACCOUNTING_UNIT_ID    =? "+
					" AND ACCOUNTING_FOR_OFFICE_ID=? "+
					" AND FINANCIAL_YEAR          =? "+
					" )c "+
					"   on t.ACC_HEAD_CODE=c.head_of_account and t.budger_g_id=c.BUDGET_GROUP_ID"+
					"  order by c.head_of_account ";*/
				
				String ss="select head_of_account,actuals_for_last_year AS Ac_fr_lst_yr,  "+
				  " be_for_the_year,   "+
				 "  actuals_for_period_apr_to_nov AS Actual_fr_Period_Apr_Nov,   "+
				  " ANTICIPATED_FR_PERIOD_DEC_MAR,RE_FOR_YEAR,   "+
				" VARIATION_BETWEN_BE_RE,REASON_FOR_VARIATION,BE_FOR_NEXT_YEAR,  "+ 
				" VARIATION_BTWN_REYR_AND_NXTYR,UPDATED_BY_USERID,UPDATED_DATE,  "+ 
				"  DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,BUDGET_GROUP_ID as budger_g_id,   "+
				 "(SELECT BUDGET_GROUP_MAJOR FROM FAS_BUDGET_GROUP_MASTER m   "+
				  " where m.BUDGET_GROUP_ID=f.BUDGET_GROUP_ID) as BUDGET_GROUP_MAJOR,  "+
				"  SL_NO from FAS_BUDGET_FORMAT_1  f  "+
				 " WHERE ACCOUNTING_UNIT_ID    =? "+
				" AND ACCOUNTING_FOR_OFFICE_ID=? "+
				" AND FINANCIAL_YEAR          = ?  "+
				" and ALLOCATION_TYPE='H' ";
				
				
				
				/*String ss=" select t.budger_g_id,t.STATEMENT_DESC, "+
				" c.* from "+
				" (SELECT budger_g_id, "+
				" STATEMENT_DESC, "+
				" ACC_HEAD_CODE "+
				" FROM "+
				" (SELECT DISTINCT STATEMENT_GROUP_NO AS budger_g_id, "+
				" FROM_ACC_HD_CODE as ACC_HEAD_CODE "+
				" FROM FAS_STATEMENT_ACC_HD_MAPPING "+
				" WHERE STATEMENT_NO=1 "+
				" ORDER BY STATEMENT_GROUP_NO "+
				" )a "+
				" left outer join"+
				" (SELECT STATEMENT_NO, "+
				" STATEMENT_DESC "+
				" FROM fas_statement_master "+
				" ORDER BY STATEMENT_NO "+
				"  )b "+
				"   ON a.budger_g_id =b.STATEMENT_NO)t "+
				"   inner join "+
				"   ( "+
				"  select head_of_account,actuals_for_last_year AS Ac_fr_lst_yr, "+
				"   be_for_the_year, "+
				"   actuals_for_period_apr_to_nov AS Actual_fr_Period_Apr_Nov, "+
			  " ANTICIPATED_FR_PERIOD_DEC_MAR,RE_FOR_YEAR, "+
				" VARIATION_BETWEN_BE_RE,REASON_FOR_VARIATION,BE_FOR_NEXT_YEAR, "+
				" VARIATION_BTWN_REYR_AND_NXTYR,UPDATED_BY_USERID,UPDATED_DATE, "+
				"  DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID, "+
				"  SL_NO from FAS_BUDGET_FORMAT_1 WHERE ACCOUNTING_UNIT_ID    =? "+
				" AND ACCOUNTING_FOR_OFFICE_ID=? "+
				" AND FINANCIAL_YEAR          = ?"+//+(year1) + "-" + (ssyr1)+"'"+
				" )c "+
				"   on t.ACC_HEAD_CODE=c.head_of_account "+
				"  order by c.head_of_account ";*/
				
				System.out.println("query:"+ss);
				ps1=connection.prepareStatement(ss);
				ps1.setInt(1, cmbAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setString(3, (year1) + "-" + (ssyr1));
				ResultSet rsss = ps1.executeQuery();
				while(rsss.next())
				{
					
					//System.out.println("ifffffffffffffffffffffffffffffffffffffffff catherine");
		xml = xml + "<BudgetGroupMajor>"+ rsss.getString("BUDGET_GROUP_MAJOR")+ "</BudgetGroupMajor>";
		xml = xml + "<budger_g_id>"+ rsss.getInt("budger_g_id")+ "</budger_g_id>";	
		xml = xml + "<ALLOCATION_TYPE>H</ALLOCATION_TYPE>";
					//budger_g_id
			xml = xml + "<Ac_fr_lst_yr>"
					+ rsss.getInt("Ac_fr_lst_yr") + "</Ac_fr_lst_yr>";
			xml = xml + "<BE_fr_Yr>" + rsss.getInt("be_for_the_year")
					+ "</BE_fr_Yr>";
			xml = xml + "<account_head>" + rsss.getInt("head_of_account")+ "</account_head>";
			xml = xml + "<Actual_fr_Period_Apr_Nov>"
					+ rsss.getInt("Actual_fr_Period_Apr_Nov")
					+ "</Actual_fr_Period_Apr_Nov>";
			
			xml = xml + "<anticipated>"
			+ rsss.getInt("ANTICIPATED_FR_PERIOD_DEC_MAR")
			+ "</anticipated>";
			
			xml = xml + "<RE_FOR_YEAR>"
			+ rsss.getInt("RE_FOR_YEAR")
			+ "</RE_FOR_YEAR>";
			
			xml = xml + "<BE_FOR_NEXT_YEAR>"
			+ rsss.getInt("BE_FOR_NEXT_YEAR")
			+ "</BE_FOR_NEXT_YEAR>";
				
			xml = xml + "<variation>"
			+ rsss.getInt("VARIATION_BETWEN_BE_RE")
			+ "</variation>";
			xml = xml + "<reason_variation>"
			+ rsss.getString("REASON_FOR_VARIATION")
			+ "</reason_variation>";
			xml = xml + "<variation_btwn>"
			+ rsss.getString("VARIATION_BTWN_REYR_AND_NXTYR")
			+ "</variation_btwn>";
			count++;
				}
				if(count>0)
				{
					System.out.println(count);
					xml = xml + "<flag>success</flag>";
				}
				else
				{
					
					//int y1=(year1 - 1);
					//int y2=(year2 - 1);
					
					int y1=(year1 - 2);
					int y2=(year2 - 2);
					String splitlastyr1=String.valueOf(year2-1);
					String ssyr2=splitlastyr1.substring(2,4);
					
					//System.out.println("elllllllllllllllllseeeeeeeeeeeeeeeeeeeee");
					/*String sq="SELECT budger_g_id,  "+
					 " BUDGET_GROUP_MAJOR, "+
						"  ACC_HEAD_CODE, "+
						" SUM(Ac_fr_lst_yr)            AS Ac_fr_lst_yr, "+
						" SUM(BE_fr_Yr)                AS BE_fr_Yr, "+
						" SUM(Actual_fr_Period_Apr_Nov)AS Actual_fr_Period_Apr_Nov "+
						" FROM "+
						" (SELECT budger_g_id, "+
						"  BUDGET_GROUP_MAJOR, "+
						"  ACC_HEAD_CODE, "+
						" DECODE(Ac_fr_lst_yr,NULL,0,Ac_fr_lst_yr)                         AS Ac_fr_lst_yr, "+
						" DECODE(BE_fr_Yr,NULL,0,BE_fr_Yr)                                 AS BE_fr_Yr, "+
						" DECODE(Actual_fr_Period_Apr_Nov,NULL,0,Actual_fr_Period_Apr_Nov) AS Actual_fr_Period_Apr_Nov "+
						" FROM "+
						" (SELECT rownum AS slno1, "+
						" budger_g_id,BUDGET_GROUP_MAJOR,ACC_HEAD_CODE "+
						"  FROM "+
						"   (SELECT DISTINCT BUDGET_GROUP_ID AS budger_g_id, "+
						"    ACC_HEAD_CODE "+
						"   FROM FAS_BUDGET_AC_HEADS_MAP "+
						"   WHERE FORMAT_NO=1 "+
						"   ORDER BY BUDGET_GROUP_ID "+
						"   )a "+
						"  LEFT OUTER JOIN "+
						"   (SELECT BUDGET_GROUP_ID, "+
						"     BUDGET_GROUP_MAJOR "+
						"   FROM FAS_BUDGET_GROUP_MASTER "+
						"   ORDER BY BUDGET_GROUP_ID "+
						"   )b "+
						"  ON a.budger_g_id =b.BUDGET_GROUP_ID "+
						"  )X "+
						" LEFT OUTER JOIN "+
						" ( "+
						" SELECT ACCOUNT_HEAD_CODE_XX, "+
						"  (DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) + DECODE(Ac_fr_lst_yr_YY,'',0,Ac_fr_lst_yr_YY)) AS Ac_fr_lst_yr "+
						"  FROM "+
						"  (SELECT ACCOUNT_HEAD_CODE1                                                            AS ACCOUNT_HEAD_CODE_XX, "+
						// Lakshmi 7Nov13"    DECODE((a.Ac_fr_lst_yr1+b.Ac_fr_lst_yr2),NULL,0,(a.Ac_fr_lst_yr1+b.Ac_fr_lst_yr2)) AS Ac_fr_lst_yr_XX "+
						" ( DECODE(a.Ac_fr_lst_yr1,NULL,0,a.Ac_fr_lst_yr1)+ DECODE(b.Ac_fr_lst_yr2,NULL,0,b.Ac_fr_lst_yr2)) AS Ac_fr_lst_yr_XX   "+
						"  FROM "+
						"    (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) as Ac_fr_lst_yr1, "+
						"      ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE1 "+
						"     FROM FAS_TRIAL_BALANCE "+
						"     WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
						"     AND CASHBOOK_YEAR       = "+y1+
						"    AND CASHBOOK_MONTH BETWEEN 4 AND 12 "+
						"    GROUP BY ACCOUNT_HEAD_CODE "+
						"    )a "+
						"  LEFT OUTER JOIN "+
						"    (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) as Ac_fr_lst_yr2, "+
						"      ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE2 "+
						"   FROM FAS_TRIAL_BALANCE "+
						"   WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
						"   AND CASHBOOK_YEAR       = "+y2+
						"   AND CASHBOOK_MONTH BETWEEN 1 AND 3 "+
						"   GROUP BY ACCOUNT_HEAD_CODE "+
						"   )b "+
						" ON a.ACCOUNT_HEAD_CODE1 =b.ACCOUNT_HEAD_CODE2 "+
						" )XX "+
						"  LEFT OUTER JOIN "+
						"  (SELECT ACCOUNT_HEAD_CODE2                                                           AS ACCOUNT_HEAD_CODE_YY, "+
						"     Ac_fr_lst_yr2 AS Ac_fr_lst_yr_YY "+
						"    FROM "+
						"   (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) as Ac_fr_lst_yr2, "+
						"      ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE2 "+
						"   FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
						"   WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
						"   AND CASHBOOK_YEAR       =  "+y2+
						"   And Cashbook_Month Between 1 And 3 "+
						"   GROUP BY ACCOUNT_HEAD_CODE) "+
						"  )YY ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
						" )Y ON X.ACC_HEAD_CODE            =Y.ACCOUNT_HEAD_CODE_XX "+
						" LEFT OUTER JOIN "+
						"  (SELECT ACCOUNT_HEAD_CODE_XX                   AS ACCOUNT_HEAD_CODE_ZZ, "+
						"    DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) AS BE_fr_Yr "+
						"  FROM "+
						"   (SELECT DECODE(SUM(CURRENT_YEAR_BUDGET_ALLOTTED),NULL,0,SUM(CURRENT_YEAR_BUDGET_ALLOTTED)) AS Ac_fr_lst_yr_XX, "+
						"    ACCOUNT_HEAD_CODE                                                                        AS ACCOUNT_HEAD_CODE_XX "+
						"  FROM COM_BUDGET_DETAILS "+
						"  Where Accounting_Unit_Id="+cmbAcc_UnitCode+
						"   AND FINANCIAL_YEAR      = '"+(year1)+"-"+(ssyr2.trim())+"'"+ //para change 
						"   GROUP BY ACCOUNT_HEAD_CODE "+
						"    ) "+
						"  )Z "+
						" ON X.ACC_HEAD_CODE =Z.ACCOUNT_HEAD_CODE_ZZ "+
						"  LEFT OUTER JOIN "+
						"   ( "+
						"  SELECT ACCOUNT_HEAD_CODE_XX  AS ACCOUNT_HEAD_CODE_KK, "+
						"   (DECODE(Ac_fr_lst_yr1,'',0,Ac_fr_lst_yr1) + DECODE(Ac_fr_lst_yr2,'',0,Ac_fr_lst_yr2)) AS Actual_fr_Period_Apr_Nov "+
						"  FROM "+
						"  (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr1, "+
						"    ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE_XX "+
						"   FROM FAS_TRIAL_BALANCE "+
						"  WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
						"   AND CASHBOOK_YEAR       = "+y2+
						"  AND CASHBOOK_MONTH BETWEEN 4 AND 11 "+
						" GROUP BY ACCOUNT_HEAD_CODE "+
						"   )XX "+
						"  Left Outer Join "+
						"   (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
						"   ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE_YY "+
						"   FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
						"   WHERE ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode+
						"   AND CASHBOOK_YEAR       = "+y2+
						"  AND CASHBOOK_MONTH BETWEEN 4 AND 11 "+
						"  GROUP BY ACCOUNT_HEAD_CODE "+
						"   )Yy "+
						"  ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
						"  )KK ON X.ACC_HEAD_CODE     =KK.ACCOUNT_HEAD_CODE_KK "+
						" ) "+
						" GROUP BY budger_g_id, "+
						"  BUDGET_GROUP_MAJOR, "+
						"  ACC_HEAD_CODE "+
						" ORDER BY budger_g_id, "+
						"  ACC_HEAD_CODE ";*/
					
					String sq=" SELECT budger_g_id, "+
					" BUDGET_GROUP_MAJOR,  "+
					" ACC_HEAD_CODE, "+
					" SUM(Ac_fr_lst_yr)            AS Ac_fr_lst_yr, "+
					"  SUM(BE_fr_Yr)                AS BE_fr_Yr, "+
					"  SUM(Actual_fr_Period_Apr_Nov)AS Actual_fr_Period_Apr_Nov "+
					" FROM "+
					" (SELECT budger_g_id, "+
					"  BUDGET_GROUP_MAJOR, "+
					"  ACC_HEAD_CODE, "+
					"  DECODE(Ac_fr_lst_yr,NULL,0,Ac_fr_lst_yr)                         AS Ac_fr_lst_yr, "+
					"  DECODE(BE_fr_Yr,NULL,0,BE_fr_Yr)                                 AS BE_fr_Yr, "+
					"  DECODE(Actual_fr_Period_Apr_Nov,NULL,0,Actual_fr_Period_Apr_Nov) AS Actual_fr_Period_Apr_Nov "+
					"  FROM "+
					"  (SELECT rownum AS slno1, "+
					"   budger_g_id, "+
					"  BUDGET_GROUP_MAJOR, "+
					"  ACC_HEAD_CODE,ALLOCATION_TYPE "+
					"  FROM "+
					"  ( select budger_g_id,ACC_HEAD_CODE,BUDGET_GROUP_MAJOR from "+
				
					"  ( (SELECT DISTINCT BUDGET_GROUP_ID AS budger_g_id, "+
					"    ACC_HEAD_CODE "+
		"   FROM FAS_BUDGET_AC_HEADS_MAP "+
		"   WHERE FORMAT_NO=1 "+
		"   ORDER BY BUDGET_GROUP_ID "+
		"   )a "+
		" LEFT OUTER JOIN "+
		"   (SELECT BUDGET_GROUP_ID, "+
		"     BUDGET_GROUP_MAJOR "+
		"   FROM FAS_BUDGET_GROUP_MASTER "+
		"  ORDER BY BUDGET_GROUP_ID "+
		"   )b "+
		"  ON a.budger_g_id =b.BUDGET_GROUP_ID))c "+
		" right outer join  "+
		"  (SELECT ALLOCATION_TYPE, "+
		"   FROM_ACC_HD_CODE as ACCOUNT_HEAD_CODE, "+
		"  TO_ACC_HD_CODE  "+
		" FROM FAS_STATEMENT_BY_REGION "+
		"  where   STATEMENT_NO=1 "+
		  " and FINANCIAL_YEAR= '"+(year1-1)+"-"+(ssyr2.trim())+"'"+
		"  and ACCOUNTING_FOR_OFFICE_ID= "+cmbOffice_code+
		"  and ALLOCATION_TYPE='H' "+
		//"  --ORDER BY STATEMENT_NO "+
		"   )d "+
		" ON c.ACC_HEAD_CODE =d.ACCOUNT_HEAD_CODE "+
		"  and c.ACC_HEAD_CODE=d.TO_ACC_HD_CODE "+
		"  )X "+
		" LEFT OUTER JOIN "+
		" (SELECT ACCOUNT_HEAD_CODE_XX, "+
		"   (DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) + DECODE(Ac_fr_lst_yr_YY,'',0,Ac_fr_lst_yr_YY)) AS Ac_fr_lst_yr "+
		"  FROM "+
			"    (SELECT ACCOUNT_HEAD_CODE1                                                                          AS ACCOUNT_HEAD_CODE_XX, "+
			" ( DECODE(a.Ac_fr_lst_yr1,NULL,0,a.Ac_fr_lst_yr1) + DECODE(b.Ac_fr_lst_yr2,NULL,0,b.Ac_fr_lst_yr2)) AS Ac_fr_lst_yr_XX "+
			" FROM "+
			"   (SELECT (DECODE(SUM(Current_Month_Debit),NULL,0,SUM(Current_Month_Debit))-DECODE(SUM(Current_Month_Credit),NULL,0,SUM(Current_Month_Credit))) AS Ac_fr_lst_yr1, "+
			"     ACCOUNT_HEAD_CODE                                                                                                                           AS ACCOUNT_HEAD_CODE1 "+
			"   FROM FAS_TRIAL_BALANCE "+
			"     WHERE ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode+
			"    AND CASHBOOK_YEAR       = "+y1+
			"   AND CASHBOOK_MONTH BETWEEN 4 AND 12 "+
			"    GROUP BY ACCOUNT_HEAD_CODE "+
			"   )a "+
			"   LEFT OUTER JOIN "+
			"   (SELECT (DECODE(SUM(Current_Month_Debit),NULL,0,SUM(Current_Month_Debit))-DECODE(SUM(Current_Month_Credit),NULL,0,SUM(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
			"      ACCOUNT_HEAD_CODE                                                                                                                           AS ACCOUNT_HEAD_CODE2 "+
			"   FROM FAS_TRIAL_BALANCE "+
			"   WHERE ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode+
			"   AND CASHBOOK_YEAR       =  "+y2+
			"   AND CASHBOOK_MONTH BETWEEN 1 AND 3 "+
			"   GROUP BY ACCOUNT_HEAD_CODE "+
			"   )b "+
			"  ON a.ACCOUNT_HEAD_CODE1 =b.ACCOUNT_HEAD_CODE2 "+
			"  )XX "+
			"  LEFT OUTER JOIN "+
			"  (SELECT ACCOUNT_HEAD_CODE2 AS ACCOUNT_HEAD_CODE_YY, "+
			"   Ac_fr_lst_yr2            AS Ac_fr_lst_yr_YY "+
			"  FROM "+
			"  (SELECT (DECODE(SUM(Current_Month_Debit),NULL,0,SUM(Current_Month_Debit))-DECODE(SUM(Current_Month_Credit),NULL,0,SUM(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
			"     ACCOUNT_HEAD_CODE                                                                                                                           AS ACCOUNT_HEAD_CODE2 "+
			"   FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
			"   WHERE ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode+
			"   AND CASHBOOK_YEAR       = "+y2+
			"   AND Cashbook_Month =3 "+
			"   GROUP BY ACCOUNT_HEAD_CODE "+
		"   ) "+
    					"   )YY "+
		"  ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
	"   )Y ON X.ACC_HEAD_CODE      =Y.ACCOUNT_HEAD_CODE_XX "+
	"  LEFT OUTER JOIN "+
	"  (SELECT ACCOUNT_HEAD_CODE_XX                   AS ACCOUNT_HEAD_CODE_ZZ, "+
			"    DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) AS BE_fr_Yr "+
		"  FROM "+
	"   (SELECT DECODE(SUM(CURRENT_YEAR_BUDGET_ALLOTTED),NULL,0,SUM(CURRENT_YEAR_BUDGET_ALLOTTED)) AS Ac_fr_lst_yr_XX, "+
				"     FROM_ACC_HD_CODE                                                                         AS ACCOUNT_HEAD_CODE_XX "+
 
		"  FROM COM_BUDGET_DETAILS "+
		"  WHERE Accounting_Unit_Id= "+cmbAcc_UnitCode+
		"  AND FINANCIAL_YEAR      = '"+(year1-1)+"-"+(ssyr2.trim())+"'"+//para change
			"  and  ALLOCATION_TYPE='H' "+
			"   and STATEMENT_NO=1 "+
		"    GROUP BY FROM_ACC_HD_CODE "+
		"    ) "+
	"   )Z "+
	"  ON X.ACC_HEAD_CODE =Z.ACCOUNT_HEAD_CODE_ZZ "+
	" LEFT OUTER JOIN "+
	"  (SELECT ACCOUNT_HEAD_CODE_XX                                                           AS ACCOUNT_HEAD_CODE_KK, "+
			"   (DECODE(Ac_fr_lst_yr1,'',0,Ac_fr_lst_yr1) + DECODE(Ac_fr_lst_yr2,'',0,Ac_fr_lst_yr2)) AS Actual_fr_Period_Apr_Nov "+
		"  FROM "+
	"  (SELECT (DECODE(SUM(Current_Month_Debit),NULL,0,SUM(Current_Month_Debit))-DECODE(SUM(Current_Month_Credit),NULL,0,SUM(Current_Month_Credit))) AS Ac_fr_lst_yr1, "+
				"   ACCOUNT_HEAD_CODE                                                                                                                         AS ACCOUNT_HEAD_CODE_XX "+
		"  FROM FAS_TRIAL_BALANCE "+
		"  WHERE ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode+
		"   AND CASHBOOK_YEAR       =  "+y2+
		"  AND CASHBOOK_MONTH BETWEEN 4 AND 11 "+
		"  GROUP BY ACCOUNT_HEAD_CODE "+
		"    )XX "+
		"  LEFT OUTER JOIN "+
	"  (SELECT (DECODE(SUM(Current_Month_Debit),NULL,0,SUM(Current_Month_Debit))-DECODE(SUM(Current_Month_Credit),NULL,0,SUM(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
				"  ACCOUNT_HEAD_CODE                                                                                                                            AS ACCOUNT_HEAD_CODE_YY "+
		" FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
		" WHERE ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode+
		"  AND CASHBOOK_YEAR       =  "+y2+
		"   AND CASHBOOK_MONTH =3 "+
		"   GROUP BY ACCOUNT_HEAD_CODE "+
		"    )Yy "+
		"   ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
	"   )KK ON X.ACC_HEAD_CODE     =KK.ACCOUNT_HEAD_CODE_KK "+
	"  ) "+ 
					" GROUP BY budger_g_id, "+
"  BUDGET_GROUP_MAJOR, "+
	"  ACC_HEAD_CODE  "+
	" ORDER BY budger_g_id,  "+
"  ACC_HEAD_CODE";
					
					
					
					/*String sq="SELECT budger_g_id,  "+
					 " STATEMENT_DESC, "+
						"  ACC_HEAD_CODE, "+
						" SUM(Ac_fr_lst_yr)            AS Ac_fr_lst_yr, "+
						" SUM(BE_fr_Yr)                AS BE_fr_Yr, "+
						" SUM(Actual_fr_Period_Apr_Nov)AS Actual_fr_Period_Apr_Nov "+
						" FROM "+
						" (SELECT budger_g_id, "+
						"  STATEMENT_DESC, "+
						"  ACC_HEAD_CODE, "+
						" DECODE(Ac_fr_lst_yr,NULL,0,Ac_fr_lst_yr)                         AS Ac_fr_lst_yr, "+
						" DECODE(BE_fr_Yr,NULL,0,BE_fr_Yr)                                 AS BE_fr_Yr, "+
						" DECODE(Actual_fr_Period_Apr_Nov,NULL,0,Actual_fr_Period_Apr_Nov) AS Actual_fr_Period_Apr_Nov "+
						" FROM "+
						" (SELECT rownum AS slno1, "+
						" budger_g_id,STATEMENT_DESC,ACC_HEAD_CODE "+
						"  FROM "+
						"   (SELECT DISTINCT STATEMENT_GROUP_NO AS budger_g_id, "+
						"    FROM_ACC_HD_CODE as ACC_HEAD_CODE "+
						"   FROM FAS_STATEMENT_ACC_HD_MAPPING "+
						"   WHERE STATEMENT_NO=1"+
						"   ORDER BY STATEMENT_GROUP_NO "+
						"   )a "+
						"  LEFT OUTER JOIN "+
						"   (SELECT STATEMENT_NO, "+
						"     STATEMENT_DESC "+
						"   FROM fas_statement_master "+
						"   ORDER BY STATEMENT_NO "+
						"   )b "+
						"  ON a.budger_g_id =b.STATEMENT_NO "+
						"  )X "+
						" LEFT OUTER JOIN "+
						" ( "+
						" SELECT ACCOUNT_HEAD_CODE_XX, "+
						"  (DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) + DECODE(Ac_fr_lst_yr_YY,'',0,Ac_fr_lst_yr_YY)) AS Ac_fr_lst_yr "+
						"  FROM "+
						"  (SELECT ACCOUNT_HEAD_CODE1                                                            AS ACCOUNT_HEAD_CODE_XX, "+
						// Lakshmi 7Nov13"    DECODE((a.Ac_fr_lst_yr1+b.Ac_fr_lst_yr2),NULL,0,(a.Ac_fr_lst_yr1+b.Ac_fr_lst_yr2)) AS Ac_fr_lst_yr_XX "+
						" ( DECODE(a.Ac_fr_lst_yr1,NULL,0,a.Ac_fr_lst_yr1)+ DECODE(b.Ac_fr_lst_yr2,NULL,0,b.Ac_fr_lst_yr2)) AS Ac_fr_lst_yr_XX   "+
						"  FROM "+
						"    (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) as Ac_fr_lst_yr1, "+
						"      ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE1 "+
						"     FROM FAS_TRIAL_BALANCE "+
						"     WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
						"     AND CASHBOOK_YEAR       = "+y1+
						"    AND CASHBOOK_MONTH BETWEEN 4 AND 12 "+
						"    GROUP BY ACCOUNT_HEAD_CODE "+
						"    )a "+
						"  LEFT OUTER JOIN "+
						"    (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) as Ac_fr_lst_yr2, "+
						"      ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE2 "+
						"   FROM FAS_TRIAL_BALANCE "+
						"   WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
						"   AND CASHBOOK_YEAR       = "+y2+
						"   AND CASHBOOK_MONTH BETWEEN 1 AND 3 "+
						"   GROUP BY ACCOUNT_HEAD_CODE "+
						"   )b "+
						" ON a.ACCOUNT_HEAD_CODE1 =b.ACCOUNT_HEAD_CODE2 "+
						" )XX "+
						"  LEFT OUTER JOIN "+
						"  (SELECT ACCOUNT_HEAD_CODE2                                                           AS ACCOUNT_HEAD_CODE_YY, "+
						"     Ac_fr_lst_yr2 AS Ac_fr_lst_yr_YY "+
						"    FROM "+
						"   (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) as Ac_fr_lst_yr2, "+
						"      ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE2 "+
						"   FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
						"   WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
						"   AND CASHBOOK_YEAR       =  "+y2+
						"   And Cashbook_Month Between 1 And 3 "+
						"   GROUP BY ACCOUNT_HEAD_CODE) "+
						"  )YY ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
						" )Y ON X.ACC_HEAD_CODE            =Y.ACCOUNT_HEAD_CODE_XX "+
						" LEFT OUTER JOIN "+
						"  (SELECT ACCOUNT_HEAD_CODE_XX                   AS ACCOUNT_HEAD_CODE_ZZ, "+
						"    DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) AS BE_fr_Yr "+
						"  FROM "+
						"   (SELECT DECODE(SUM(CURRENT_YEAR_BUDGET_ALLOTTED),NULL,0,SUM(CURRENT_YEAR_BUDGET_ALLOTTED)) AS Ac_fr_lst_yr_XX, "+
						"   FROM_ACC_HD_CODE                                                                       AS ACCOUNT_HEAD_CODE_XX "+
						"  FROM COM_BUDGET_DETAILS "+
						"  Where Accounting_Unit_Id="+cmbAcc_UnitCode+
						"   AND FINANCIAL_YEAR      = '"+(year1)+"-"+(ssyr2.trim())+"'"+ //para change 
						"   GROUP BY FROM_ACC_HD_CODE "+
						"    ) "+
						"  )Z "+
						" ON X.ACC_HEAD_CODE =Z.ACCOUNT_HEAD_CODE_ZZ "+
						"  LEFT OUTER JOIN "+
						"   ( "+
						"  SELECT ACCOUNT_HEAD_CODE_XX  AS ACCOUNT_HEAD_CODE_KK, "+
						"   (DECODE(Ac_fr_lst_yr1,'',0,Ac_fr_lst_yr1) + DECODE(Ac_fr_lst_yr2,'',0,Ac_fr_lst_yr2)) AS Actual_fr_Period_Apr_Nov "+
						"  FROM "+
						"  (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr1, "+
						"    ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE_XX "+
						"   FROM FAS_TRIAL_BALANCE "+
						"  WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
						"   AND CASHBOOK_YEAR       = "+y2+
						"  AND CASHBOOK_MONTH BETWEEN 4 AND 11 "+
						" GROUP BY ACCOUNT_HEAD_CODE "+
						"   )XX "+
						"  Left Outer Join "+
						"   (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
						"   ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE_YY "+
						"   FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
						"   WHERE ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode+
						"   AND CASHBOOK_YEAR       = "+y2+
						"  AND CASHBOOK_MONTH BETWEEN 4 AND 11 "+
						"  GROUP BY ACCOUNT_HEAD_CODE "+
						"   )Yy "+
						"  ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
						"  )KK ON X.ACC_HEAD_CODE     =KK.ACCOUNT_HEAD_CODE_KK "+
						" ) "+
						" GROUP BY budger_g_id, "+
						"  STATEMENT_DESC, "+
						"  ACC_HEAD_CODE "+
						" ORDER BY budger_g_id, "+
						"  ACC_HEAD_CODE ";*/
					System.out.println("sq:::"+sq);
					ps = connection.prepareStatement(sq);
				//	ps.setInt(1, year1 - 1);//2011
				//	ps.setInt(2, year2 - 1);//2012
				//	ps.setInt(3, year1 - 1);//2011
				//	ps.setInt(4, year2 - 1);//2012
				//	ps.setString(1, (year1) + "-" + (year2));//2012-2013
					
					rs = ps.executeQuery();
					while (rs.next()) {
				xml = xml + "<BudgetGroupMajor>"+ rs.getString("BUDGET_GROUP_MAJOR")+ "</BudgetGroupMajor>";
				xml = xml + "<budger_g_id>"+ rs.getInt("budger_g_id")+ "</budger_g_id>";
				xml = xml + "<ALLOCATION_TYPE>H</ALLOCATION_TYPE>";
				xml = xml + "<Ac_fr_lst_yr>"+ rs.getInt("Ac_fr_lst_yr") + "</Ac_fr_lst_yr>";
				xml = xml + "<BE_fr_Yr>" + rs.getInt("BE_fr_Yr")+ "</BE_fr_Yr>";
				xml = xml + "<account_head>" + rs.getInt("ACC_HEAD_CODE")+ "</account_head>";
				xml = xml + "<Actual_fr_Period_Apr_Nov>"+ rs.getInt("Actual_fr_Period_Apr_Nov")+ "</Actual_fr_Period_Apr_Nov>";
				int anti1=((rs.getInt("Actual_fr_Period_Apr_Nov"))/8)*4;
				xml = xml + "<anticipated>"+anti1+ "</anticipated>";
				xml = xml + "<RE_FOR_YEAR>"	+ 0+ "</RE_FOR_YEAR>";
				xml = xml + "<BE_FOR_NEXT_YEAR>"+ 0+ "</BE_FOR_NEXT_YEAR>";
				
				xml = xml + "<variation>"+0	+ "</variation>";
				xml = xml + "<reason_variation>"+"-"+ "</reason_variation>";
				xml = xml + "<variation_btwn>"+"-"+ "</variation_btwn>";
				
					}
					xml = xml + "<flag>success</flag>";
				
				}
				
			}
			catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		
		}
		else if (strCommand.equalsIgnoreCase("getGrp")) {
			int count=0;
			xml = "<response><command>getGrp</command>";
			int year1 = Integer.parseInt(request.getParameter("y1"));
			int year2 = Integer.parseInt(request.getParameter("y2"));
			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			try
			{

				String splitlastyr=String.valueOf(year2);
				String ssyr1=splitlastyr.substring(2,4);
			//	System.out.println("ssyr1 "+ssyr1);
				
				String ss="select head_of_account,actuals_for_last_year AS Ac_fr_lst_yr,  "+
				  " be_for_the_year,   "+
				 "  actuals_for_period_apr_to_nov AS Actual_fr_Period_Apr_Nov,   "+
			  " ANTICIPATED_FR_PERIOD_DEC_MAR,RE_FOR_YEAR,   "+
				" VARIATION_BETWEN_BE_RE,REASON_FOR_VARIATION,BE_FOR_NEXT_YEAR,  "+ 
				" VARIATION_BTWN_REYR_AND_NXTYR,UPDATED_BY_USERID,UPDATED_DATE,  "+ 
				"  DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,BUDGET_GROUP_ID as budger_g_id,   "+
     "(SELECT BUDGET_GROUP_MAJOR FROM FAS_BUDGET_GROUP_MASTER m   "+
      " where m.BUDGET_GROUP_ID=f.BUDGET_GROUP_ID) as BUDGET_GROUP_MAJOR,  "+
				"  SL_NO from FAS_BUDGET_FORMAT_1  f  "+
     " WHERE ACCOUNTING_UNIT_ID    =? "+
				" AND ACCOUNTING_FOR_OFFICE_ID=? "+
				" AND FINANCIAL_YEAR          = ?  "+
				" and ALLOCATION_TYPE='G' ";
				
				
				System.out.println("query:"+ss);
				ps1=connection.prepareStatement(ss);
				ps1.setInt(1, cmbAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setString(3, (year1) + "-" + (ssyr1));
				ResultSet rsss = ps1.executeQuery();
				while(rsss.next())
				{
					
					//System.out.println("ifffffffffffffffffffffffffffffffffffffffff catherine");
					xml = xml + "<BudgetGroupMajor>"
					+ rsss.getString("BUDGET_GROUP_MAJOR")
					+ "</BudgetGroupMajor>";
					xml = xml + "<budger_g_id>"+ rsss.getInt("budger_g_id")+ "</budger_g_id>";
					xml = xml + "<ALLOCATION_TYPE>G</ALLOCATION_TYPE>";
			xml = xml + "<Ac_fr_lst_yr>"
					+ rsss.getInt("Ac_fr_lst_yr") + "</Ac_fr_lst_yr>";
			xml = xml + "<BE_fr_Yr>" + rsss.getInt("be_for_the_year")
					+ "</BE_fr_Yr>";
			xml = xml + "<account_head>" + rsss.getInt("head_of_account")+ "</account_head>";
			xml = xml + "<Actual_fr_Period_Apr_Nov>"
					+ rsss.getInt("Actual_fr_Period_Apr_Nov")
					+ "</Actual_fr_Period_Apr_Nov>";
			
			xml = xml + "<anticipated>"
			+ rsss.getInt("ANTICIPATED_FR_PERIOD_DEC_MAR")
			+ "</anticipated>";
			
			xml = xml + "<RE_FOR_YEAR>"
			+ rsss.getInt("RE_FOR_YEAR")
			+ "</RE_FOR_YEAR>";
			
			xml = xml + "<BE_FOR_NEXT_YEAR>"
			+ rsss.getInt("BE_FOR_NEXT_YEAR")
			+ "</BE_FOR_NEXT_YEAR>";
				
			xml = xml + "<variation>"
			+ rsss.getInt("VARIATION_BETWEN_BE_RE")
			+ "</variation>";
			xml = xml + "<reason_variation>"
			+ rsss.getString("REASON_FOR_VARIATION")
			+ "</reason_variation>";
			xml = xml + "<variation_btwn>"
			+ rsss.getString("VARIATION_BTWN_REYR_AND_NXTYR")
			+ "</variation_btwn>";
			count++;
				}
				if(count>0)
				{
					System.out.println(count);
					xml = xml + "<flag>success</flag>";
				}
				else
				{
					
					/*int y1=(year1 - 1);
					int y2=(year2 - 1);
					String splitlastyr=String.valueOf(year2);
					String ssyr2=splitlastyr.substring(2,4);
					System.out.println("ssyr2 "+ssyr2+" year2  "+year2);*/
					
					int y1=(year1 - 2);
					int y2=(year2 - 2);
					String splitlastyr0=String.valueOf(year2-1);
					String ssyr2=splitlastyr0.substring(2,4);
				
					
					String sq=" SELECT budger_g_id, "+
					" BUDGET_GROUP_MAJOR, rangeHead, "+
					" ACC_HEAD_CODE, "+
					" SUM(Ac_fr_lst_yr)            AS Ac_fr_lst_yr, "+
					"  SUM(BE_fr_Yr)                AS BE_fr_Yr, "+
					"  SUM(Actual_fr_Period_Apr_Nov)AS Actual_fr_Period_Apr_Nov "+
					" FROM "+
					" (SELECT budger_g_id, "+
					"   BUDGET_GROUP_MAJOR, "+
					"     ACC_HEAD_CODE, "+
					"     rangeHead, "+
					"     DECODE(Ac_fr_lst_yr,NULL,0,Ac_fr_lst_yr)                   AS Ac_fr_lst_yr, "+
					" 	    DECODE(BE_fr_Yr,NULL,0,BE_fr_Yr)                               AS BE_fr_Yr, "+
					" 	    DECODE(Actual_fr_Period_Apr_Nov,NULL,0,Actual_fr_Period_Apr_Nov) AS Actual_fr_Period_Apr_Nov "+
					" 	  FROM "+
					"  ( SELECT rownum AS slno1, "+
					"   budger_g_id, "+
					"   BUDGET_GROUP_MAJOR, "+
					"   ACC_HEAD_CODE,ALLOCATION_TYPE," +
					"TO_ACC_HD_CODE,rangeHead "+
					"  FROM "+
					"  ( select budger_g_id,ACC_HEAD_CODE,BUDGET_GROUP_MAJOR from "+
					"  ( (SELECT DISTINCT BUDGET_GROUP_ID AS budger_g_id, "+
					"      ACC_HEAD_CODE "+
					"    FROM FAS_BUDGET_AC_HEADS_MAP "+
					"    WHERE FORMAT_NO=1 "+
					"    ORDER BY BUDGET_GROUP_ID "+
					"    )a "+
					"  LEFT OUTER JOIN "+
					"    (SELECT BUDGET_GROUP_ID, "+
					"     BUDGET_GROUP_MAJOR "+
					"   FROM FAS_BUDGET_GROUP_MASTER "+
					"   ORDER BY BUDGET_GROUP_ID "+
					"   )b "+
					"  ON a.budger_g_id =b.BUDGET_GROUP_ID) "+
					"  )c "+
					"  left outer join  "+
					"  (SELECT ALLOCATION_TYPE, "+
					"      FROM_ACC_HD_CODE as ACCOUNT_HEAD_CODE, "+
					"      TO_ACC_HD_CODE ,ACCOUNT_HEAD_CODE as rangeHead "+
					"    FROM FAS_STATEMENT_BY_REGION "+
					"   where   STATEMENT_NO=1 "+
					  " and FINANCIAL_YEAR= '"+(year1-1)+"-"+(ssyr2.trim())+"'"+
					"   and ACCOUNTING_FOR_OFFICE_ID= "+cmbOffice_code+
					"   and ALLOCATION_TYPE='G' "+
					"   ORDER BY FROM_ACC_HD_CODE "+
					"    )d "+
					"   ON d.ACCOUNT_HEAD_CODE= c.ACC_HEAD_CODE  "+ 
					"  and d.TO_ACC_HD_CODE !=c.ACC_HEAD_CODE  "+
					"  and ( d.ACCOUNT_HEAD_CODE <=c.ACC_HEAD_CODE   "+
					"   and d.TO_ACC_HD_CODE>=c.ACC_HEAD_CODE ) "+
				    
		"  )X "+
		" LEFT OUTER JOIN "+
		" (SELECT ACCOUNT_HEAD_CODE_XX, "+
		"   (DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) + DECODE(Ac_fr_lst_yr_YY,'',0,Ac_fr_lst_yr_YY)) AS Ac_fr_lst_yr "+
		"  FROM "+
			"    (SELECT ACCOUNT_HEAD_CODE1                                                                          AS ACCOUNT_HEAD_CODE_XX, "+
			" ( DECODE(a.Ac_fr_lst_yr1,NULL,0,a.Ac_fr_lst_yr1) + DECODE(b.Ac_fr_lst_yr2,NULL,0,b.Ac_fr_lst_yr2)) AS Ac_fr_lst_yr_XX "+
			" FROM "+
			"   (SELECT (DECODE(SUM(Current_Month_Debit),NULL,0,SUM(Current_Month_Debit))-DECODE(SUM(Current_Month_Credit),NULL,0,SUM(Current_Month_Credit))) AS Ac_fr_lst_yr1, "+
			"     ACCOUNT_HEAD_CODE                                                                                                                           AS ACCOUNT_HEAD_CODE1 "+
			"   FROM FAS_TRIAL_BALANCE "+
			"     WHERE ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode+
			"    AND CASHBOOK_YEAR       = "+y1+
			"   AND CASHBOOK_MONTH BETWEEN 4 AND 12 "+
			"    GROUP BY ACCOUNT_HEAD_CODE "+
			"   )a "+
			"   LEFT OUTER JOIN "+
			"   (SELECT (DECODE(SUM(Current_Month_Debit),NULL,0,SUM(Current_Month_Debit))-DECODE(SUM(Current_Month_Credit),NULL,0,SUM(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
			"      ACCOUNT_HEAD_CODE                                                                                                                           AS ACCOUNT_HEAD_CODE2 "+
			"   FROM FAS_TRIAL_BALANCE "+
			"   WHERE ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode+
			"   AND CASHBOOK_YEAR       =  "+y2+
			"   AND CASHBOOK_MONTH BETWEEN 1 AND 3 "+
			"   GROUP BY ACCOUNT_HEAD_CODE "+
			"   )b "+
			"  ON a.ACCOUNT_HEAD_CODE1 =b.ACCOUNT_HEAD_CODE2 "+
			"  )XX "+
			"  LEFT OUTER JOIN "+
			"  (SELECT ACCOUNT_HEAD_CODE2 AS ACCOUNT_HEAD_CODE_YY, "+
			"   Ac_fr_lst_yr2            AS Ac_fr_lst_yr_YY "+
			"  FROM "+
			"  (SELECT (DECODE(SUM(Current_Month_Debit),NULL,0,SUM(Current_Month_Debit))-DECODE(SUM(Current_Month_Credit),NULL,0,SUM(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
			"     ACCOUNT_HEAD_CODE                                                                                                                           AS ACCOUNT_HEAD_CODE2 "+
			"   FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
			"   WHERE ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode+
			"   AND CASHBOOK_YEAR       = "+y2+
			"   AND Cashbook_Month =3 "+
			"   GROUP BY ACCOUNT_HEAD_CODE "+
		"   ) "+
    					"   )YY "+
		"  ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
	"   )Y ON X.ACC_HEAD_CODE      =Y.ACCOUNT_HEAD_CODE_XX "+
	"  LEFT OUTER JOIN "+
	"  (SELECT ACCOUNT_HEAD_CODE_XX                   AS ACCOUNT_HEAD_CODE_ZZ, "+
			"    DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) AS BE_fr_Yr "+
		"  FROM "+
	"   (SELECT DECODE(SUM(CURRENT_YEAR_BUDGET_ALLOTTED),NULL,0,SUM(CURRENT_YEAR_BUDGET_ALLOTTED)) AS Ac_fr_lst_yr_XX, "+
				"     FROM_ACC_HD_CODE                                                                         AS ACCOUNT_HEAD_CODE_XX "+
 
		"  FROM COM_BUDGET_DETAILS "+
		"  WHERE Accounting_Unit_Id= "+cmbAcc_UnitCode+
		"  AND FINANCIAL_YEAR      = '"+(year1-1)+"-"+(ssyr2.trim())+"'"+//para change
			"  and  ALLOCATION_TYPE='G' "+
			"   and STATEMENT_NO=1 "+
		"    GROUP BY FROM_ACC_HD_CODE "+
		"    ) "+
	"   )Z "+
	"  ON X.ACC_HEAD_CODE =Z.ACCOUNT_HEAD_CODE_ZZ "+
	" LEFT OUTER JOIN "+
	"  (SELECT ACCOUNT_HEAD_CODE_XX                                                           AS ACCOUNT_HEAD_CODE_KK, "+
			"   (DECODE(Ac_fr_lst_yr1,'',0,Ac_fr_lst_yr1) + DECODE(Ac_fr_lst_yr2,'',0,Ac_fr_lst_yr2)) AS Actual_fr_Period_Apr_Nov "+
		"  FROM "+
	"  (SELECT (DECODE(SUM(Current_Month_Debit),NULL,0,SUM(Current_Month_Debit))-DECODE(SUM(Current_Month_Credit),NULL,0,SUM(Current_Month_Credit))) AS Ac_fr_lst_yr1, "+
				"   ACCOUNT_HEAD_CODE                                                                                                                         AS ACCOUNT_HEAD_CODE_XX "+
		"  FROM FAS_TRIAL_BALANCE "+
		"  WHERE ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode+
		"   AND CASHBOOK_YEAR       =  "+y2+
		"  AND CASHBOOK_MONTH BETWEEN 4 AND 11 "+
		"  GROUP BY ACCOUNT_HEAD_CODE "+
		"    )XX "+
		"  LEFT OUTER JOIN "+
	"  (SELECT (DECODE(SUM(Current_Month_Debit),NULL,0,SUM(Current_Month_Debit))-DECODE(SUM(Current_Month_Credit),NULL,0,SUM(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
				"  ACCOUNT_HEAD_CODE                                                                                                                            AS ACCOUNT_HEAD_CODE_YY "+
		" FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
		" WHERE ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode+
		"  AND CASHBOOK_YEAR       =  "+y2+
		"   AND CASHBOOK_MONTH =3 "+
		"   GROUP BY ACCOUNT_HEAD_CODE "+
		"    )Yy "+
		"   ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
	"   )KK ON X.ACC_HEAD_CODE     =KK.ACCOUNT_HEAD_CODE_KK "+
	"  ) "+ 
					" GROUP BY budger_g_id, "+
"  BUDGET_GROUP_MAJOR, "+
	"  ACC_HEAD_CODE,rangeHead  "+
	" ORDER BY ACC_HEAD_CODE,  "+
"  budger_g_id";
					
					
					
					System.out.println("sq  Gtrp :::"+sq);
					ps = connection.prepareStatement(sq);
			
					rs = ps.executeQuery();
					
					int ac_val=0,br_val=0,actual_val=0,acc_head=0,budgetid=0,annnti=0,fromi=0,tooi=0;
					String budget_gp="";
					String rengeHead="";
				
					String[] rengeHead1 = new String[3];
					String[] fromHead1 = new String[10];
					String[] toHead1 = new String[10];
					String from="",too="";
					/*var fy1=fy.split('-');
					var y1 = fy1[0];
					var y2 = "20"+fy1[1];*/
					int ccc=0;			
					while (rs.next()) {
					/*		int ccc=0;			
					while (rs.next()) {
						
						
						rengeHead=rs.getString("rangeHead");*/
						//String splitlastyr=String.valueOf(year2);
						//String ssyr1=splitlastyr.substring(2,4);
						
						/*if((rengeHead=="")|| (rengeHead==null) || (rengeHead.equalsIgnoreCase("")) || (rengeHead.equalsIgnoreCase(null)) ){
							System.out.println("renge head if "+rengeHead);
						}else{*/
							
						String sss2="SELECT ALLOCATION_TYPE, "+
				        " FROM_ACC_HD_CODE, "+
				       " TO_ACC_HD_CODE , "+
				      "  ACCOUNT_HEAD_CODE AS rangeHead "+
				     " FROM FAS_STATEMENT_BY_REGION "+
				    "  WHERE STATEMENT_NO          =1 "+
				    " and FINANCIAL_YEAR= '"+(year1-1)+"-"+(ssyr2.trim())+"'"+
				     " AND ACCOUNTING_FOR_OFFICE_ID= "+cmbOffice_code+
				     " AND ALLOCATION_TYPE         ='G' order by FROM_ACC_HD_CODE ";
										PreparedStatement pss2=connection.prepareStatement(sss2);
										System.out.println("sss2 "+sss2);
										ResultSet rss2=pss2.executeQuery();
										while(rss2.next()){
											fromi=rss2.getInt("FROM_ACC_HD_CODE");
											tooi=rss2.getInt("TO_ACC_HD_CODE");
											//System.out.println(fromi+"-"+tooi);					
						rengeHead=rs.getString("rangeHead");
						budget_gp=rs.getString("BUDGET_GROUP_MAJOR");
						ac_val=rs.getInt("Ac_fr_lst_yr") ;
							br_val=rs.getInt("BE_fr_Yr");
							actual_val= rs.getInt("Actual_fr_Period_Apr_Nov");
							//acc_head=0,
							budgetid=rs.getInt("budger_g_id");
							annnti=((rs.getInt("Actual_fr_Period_Apr_Nov"))/8)*4;
									
						
				acc_head=rs.getInt("ACC_HEAD_CODE");
				//System.out.println("acc_head before if "+acc_head);
				//int fromacc=(Integer.parseInt(from.trim()));
				
				//int tooacc=(Integer.parseInt(too.trim()));
				if( (acc_head>=fromi) && (acc_head<=tooi) )
				{
					//System.out.println("acc_head "+acc_head);
					xml = xml + "<BudgetGroupMajor>"+ budget_gp+ "</BudgetGroupMajor>";
					xml = xml + "<budger_g_id>"+budgetid+ "</budger_g_id>";
					xml = xml + "<ALLOCATION_TYPE>G</ALLOCATION_TYPE>";
					//xml = xml + "<BudgetGroupMajor>"+ rs.getString("STATEMENT_DESC")+ "</BudgetGroupMajor>";
					xml = xml + "<Ac_fr_lst_yr>"+ ac_val + "</Ac_fr_lst_yr>";
					xml = xml + "<BE_fr_Yr>" + br_val+ "</BE_fr_Yr>";
					xml = xml + "<account_head>" + fromi+"to"+tooi+ "</account_head>";
					xml = xml + "<Actual_fr_Period_Apr_Nov>"+actual_val+ "</Actual_fr_Period_Apr_Nov>";
					int anti1=((actual_val)/8)*4;
					/*xml = xml + "<BudgetGroupMajor>"+ rs.getString("BUDGET_GROUP_MAJOR")+ "</BudgetGroupMajor>";
					xml = xml + "<budger_g_id>"+ rs.getInt("budger_g_id")+ "</budger_g_id>";
					//xml = xml + "<BudgetGroupMajor>"+ rs.getString("STATEMENT_DESC")+ "</BudgetGroupMajor>";
					xml = xml + "<Ac_fr_lst_yr>"+ rs.getInt("Ac_fr_lst_yr") + "</Ac_fr_lst_yr>";
					xml = xml + "<BE_fr_Yr>" + rs.getInt("BE_fr_Yr")+ "</BE_fr_Yr>";
					xml = xml + "<account_head>" + rs.getInt("ACC_HEAD_CODE")+ "</account_head>";
					xml = xml + "<Actual_fr_Period_Apr_Nov>"+ rs.getInt("Actual_fr_Period_Apr_Nov")+ "</Actual_fr_Period_Apr_Nov>";
					int anti=((rs.getInt("Actual_fr_Period_Apr_Nov"))/8)*4;*/
					
					
					xml = xml + "<anticipated>"+anti1+ "</anticipated>";
					xml = xml + "<RE_FOR_YEAR>"	+ 0+ "</RE_FOR_YEAR>";
					xml = xml + "<BE_FOR_NEXT_YEAR>"+ 0+ "</BE_FOR_NEXT_YEAR>";
					
					xml = xml + "<variation>"+0	+ "</variation>";
					xml = xml + "<reason_variation>"+"-"+ "</reason_variation>";
					xml = xml + "<variation_btwn>"+"-"+ "</variation_btwn>";
					xml = xml + "<acc_head>"+acc_head+"</acc_head>";
				}else{
					System.out.println("acc_head else "+acc_head);
				}
				
				ccc++;
										}//inside while loop
				
				
				
				
						//} else null
				
				//end while 
					}
					
					
					xml = xml + "<flag>success</flag>";
				
				}
				
			}
			catch (Exception e) {
				e.printStackTrace();
				xml = xml + "<flag>failure</flag>";
			}
		
		}
		else if(strCommand.equalsIgnoreCase("get1"))
		{
			xml = "<response><command>get1</command>";
			int year1 = Integer.parseInt(request.getParameter("y1"));
			int year2 = Integer.parseInt(request.getParameter("y2"));
			int cmbAcc_UnitCode = Integer.parseInt(request
					.getParameter("cmbAcc_UnitCode"));
			int cmbOffice_code = Integer.parseInt(request
					.getParameter("cmbOffice_code"));
			int less=0;
			try
			{
				String splitlastyr1=String.valueOf(year2);
				String ssyr1=splitlastyr1.substring(2,4);
				String qu = "Select Budger_G_Id,Budget_Group_Major,  "+
						"	Sum(Ac_Fr_Lst_Yr)As Ac_Fr_Lst_Yr,Sum(Be_For_The_Year)As Be_For_The_Year, "+
							" Sum(Actual_Fr_Period_Apr_Nov)As Actual_Fr_Period_Apr_Nov, "+
					" Sum(Anticipated_Fr_Period_Dec_Mar)As Anticipated_Fr_Period_Dec_Mar, "+
					" 	Sum(Re_For_Year)As Re_For_Year, "+
					" 	sum(VARIATION_BETWEN_BE_RE)as VARIATION_BETWEN_BE_RE, "+
					" 	    sum(BE_FOR_NEXT_YEAR)as BE_FOR_NEXT_YEAR, "+
					" Case Max(Reason_For_Variation) When '0' Then Null Else Max(Reason_For_Variation) End As Reason_For_Variation, "+
					" Case Max(Variation_Btwn_Reyr_And_Nxtyr) When '0' Then Null Else Max(Variation_Btwn_Reyr_And_Nxtyr) End As Variation_Btwn_Reyr_And_Nxtyr "+
							" from "+
					" (SELECT t.budger_g_id, "+
					" 	  t.BUDGET_GROUP_MAJOR, "+
					" 	  c.* "+
					" 	FROM "+
					" 	  (SELECT budger_g_id, "+
					" 	    BUDGET_GROUP_MAJOR, "+
					" 	    ACC_HEAD_CODE "+
					" 	  FROM "+
					" 	    (SELECT DISTINCT BUDGET_GROUP_ID AS budger_g_id, "+
					" 	      ACC_HEAD_CODE "+
					" 	    FROM FAS_BUDGET_AC_HEADS_MAP "+
					" 	    WHERE FORMAT_NO=1 "+
					" 	    ORDER BY BUDGET_GROUP_ID "+
					" 	    )a "+
					" 	  LEFT OUTER JOIN "+
					" 	    (SELECT BUDGET_GROUP_ID, "+
					" 	      BUDGET_GROUP_MAJOR "+
					" 	    FROM FAS_BUDGET_GROUP_MASTER "+
					" 	    ORDER BY BUDGET_GROUP_ID "+
					" 	    )b "+
					" 	  ON a.budger_g_id =b.BUDGET_GROUP_ID "+
					" 	  )t "+
					" 	INNER JOIN "+
					" 	  (SELECT head_of_account, "+
					" 	    actuals_for_last_year AS Ac_fr_lst_yr, "+
					" 	    be_for_the_year, "+
					" 	    actuals_for_period_apr_to_nov AS Actual_fr_Period_Apr_Nov, "+
					" 	    ANTICIPATED_FR_PERIOD_DEC_MAR, "+
					" 	    RE_FOR_YEAR, "+
					" 	    VARIATION_BETWEN_BE_RE, "+
					" 	    REASON_FOR_VARIATION, "+
					" 	    BE_FOR_NEXT_YEAR, "+
					" 	    VARIATION_BTWN_REYR_AND_NXTYR, "+
					" 	    UPDATED_BY_USERID, "+
					" 	    UPDATED_DATE, "+
					" 	    DIVISION, "+
					" 	    CIRCLE, "+
					" 	    REGION, "+
					" 	    HEAD_OFFICE, "+
					" 	    OFFICE_LEVEL_ID,BUDGET_GROUP_ID, "+
					" 	    SL_NO "+
					" 	  From Fas_Budget_Format_1 "+
					" 	  Where Accounting_Unit_Id    =? "+
					" 	  And Accounting_For_Office_Id=? "+
					" 	  AND FINANCIAL_YEAR          =?"+
					" 	  )c "+
					" 	On T.Acc_Head_Code=C.Head_Of_Account and t.budger_g_id=c.BUDGET_GROUP_ID "+
					" 	Order By C.Head_Of_Account) "+
					" 	group by Budger_G_Id,Budget_Group_Major order by Budger_G_Id";
				
				/*String qu = "Select Budger_G_Id,STATEMENT_DESC,  "+
				"	Sum(Ac_Fr_Lst_Yr)As Ac_Fr_Lst_Yr,Sum(Be_For_The_Year)As Be_For_The_Year, "+
					" Sum(Actual_Fr_Period_Apr_Nov)As Actual_Fr_Period_Apr_Nov, "+
			" Sum(Anticipated_Fr_Period_Dec_Mar)As Anticipated_Fr_Period_Dec_Mar, "+
			" 	Sum(Re_For_Year)As Re_For_Year, "+
			" 	sum(VARIATION_BETWEN_BE_RE)as VARIATION_BETWEN_BE_RE, "+
			" 	    sum(BE_FOR_NEXT_YEAR)as BE_FOR_NEXT_YEAR, "+
			" Case Max(Reason_For_Variation) When '0' Then Null Else Max(Reason_For_Variation) End As Reason_For_Variation, "+
			" Case Max(Variation_Btwn_Reyr_And_Nxtyr) When '0' Then Null Else Max(Variation_Btwn_Reyr_And_Nxtyr) End As Variation_Btwn_Reyr_And_Nxtyr "+
					" from "+
			" (SELECT t.budger_g_id, "+
			" 	  t.STATEMENT_DESC, "+
			" 	  c.* "+
			" 	FROM "+
			" 	  (SELECT budger_g_id, "+
			" 	    STATEMENT_DESC, "+
			" 	    ACC_HEAD_CODE "+
			" 	  FROM "+
			" 	    (SELECT DISTINCT STATEMENT_GROUP_NO AS budger_g_id, "+
			" 	      FROM_ACC_HD_CODE as ACC_HEAD_CODE "+
			" 	    FROM FAS_STATEMENT_ACC_HD_MAPPING "+
			" 	    WHERE STATEMENT_NO=1 "+
			" 	    ORDER BY STATEMENT_GROUP_NO "+
			" 	    )a "+
			" 	  LEFT OUTER JOIN "+
			" 	    (SELECT STATEMENT_NO, "+
			" 	      STATEMENT_DESC "+
			" 	    FROM fas_statement_master "+
			" 	    ORDER BY STATEMENT_NO "+
			" 	    )b "+
			" 	  ON a.budger_g_id =b.STATEMENT_NO "+
			" 	  )t "+
			" 	INNER JOIN "+
			" 	  (SELECT head_of_account, "+
			" 	    actuals_for_last_year AS Ac_fr_lst_yr, "+
			" 	    be_for_the_year, "+
			" 	    actuals_for_period_apr_to_nov AS Actual_fr_Period_Apr_Nov, "+
			" 	    ANTICIPATED_FR_PERIOD_DEC_MAR, "+
			" 	    RE_FOR_YEAR, "+
			" 	    VARIATION_BETWEN_BE_RE, "+
			" 	    REASON_FOR_VARIATION, "+
			" 	    BE_FOR_NEXT_YEAR, "+
			" 	    VARIATION_BTWN_REYR_AND_NXTYR, "+
			" 	    UPDATED_BY_USERID, "+
			" 	    UPDATED_DATE, "+
			" 	    DIVISION, "+
			" 	    CIRCLE, "+
			" 	    REGION, "+
			" 	    HEAD_OFFICE, "+
			" 	    OFFICE_LEVEL_ID, "+
			" 	    SL_NO "+
			" 	  From Fas_Budget_Format_1 "+
			" 	  Where Accounting_Unit_Id    =? "+
			" 	  And Accounting_For_Office_Id=? "+
			" 	  AND FINANCIAL_YEAR          =?"+
			" 	  )c "+
			" 	On T.Acc_Head_Code=C.Head_Of_Account "+
			" 	Order By C.Head_Of_Account) "+
			" 	group by Budger_G_Id,STATEMENT_DESC order by Budger_G_Id"; */
				
				System.out.println("quer::"+qu);
				ps1 = connection.prepareStatement(qu);
				ps1.setInt(1, cmbAcc_UnitCode);
				ps1.setInt(2, cmbOffice_code);
				ps1.setString(3, (year1) + "-" + (ssyr1));
				results = ps1.executeQuery();
				while(results.next())
				{
					//System.out.println("if loop");
					xml = xml + "<BudgetGroupMajor>"+ results.getString("BUDGET_GROUP_MAJOR")+ "</BudgetGroupMajor>";
					xml = xml + "<budger_g_id>"+ results.getInt("Budger_G_Id")+ "</budger_g_id>";
					xml = xml + "<Ac_fr_lst_yr>"+ results.getInt("Ac_Fr_Lst_Yr") + "</Ac_fr_lst_yr>";
					xml = xml + "<BE_fr_Yr>" + results.getInt("Be_For_The_Year")+ "</BE_fr_Yr>";
					xml = xml + "<account_head>" +0+ "</account_head>";
					xml = xml + "<actuals_for_period_apr_to_nov>"+ results.getInt("Actual_Fr_Period_Apr_Nov")+ "</actuals_for_period_apr_to_nov>";
					xml = xml + "<ANTICIPATED_FR_PERIOD_DEC_MAR>"+ results.getInt("Anticipated_Fr_Period_Dec_Mar")+ "</ANTICIPATED_FR_PERIOD_DEC_MAR>";
					xml = xml + "<RE_FOR_YEAR>"+ results.getInt("Re_For_Year")+ "</RE_FOR_YEAR>";
					xml = xml + "<variation>"+ results.getInt("VARIATION_BETWEN_BE_RE")+ "</variation>";
					xml = xml + "<BE_FOR_NEXT_YEAR>"+ results.getInt("BE_FOR_NEXT_YEAR")+ "</BE_FOR_NEXT_YEAR>";
					xml = xml + "<variation>"
					+ results.getInt("VARIATION_BETWEN_BE_RE")
					+ "</variation>";
					xml = xml + "<reason_variation>"
					+ results.getString("REASON_FOR_VARIATION")
					+ "</reason_variation>";
					xml = xml + "<variation_btwn>"
					+ results.getString("VARIATION_BTWN_REYR_AND_NXTYR")
					+ "</variation_btwn>";
					less++;
				}
				if(less>0)
				{
					xml = xml + "<flag>success</flag>";
				}
				else
				{
					int y1=(year1 - 2);
					int y2=(year2 - 2);
					
					String splitlastyr=String.valueOf(year2-1);
					String ssyr2=splitlastyr.substring(2,4);
					
					String ss1 = " SELECT budger_g_id, "+
					 " BUDGET_GROUP_MAJOR, "+
					"  SUM(Ac_fr_lst_yr)            AS Ac_fr_lst_yr, "+
					" SUM(BE_fr_Yr)                AS BE_fr_Yr, "+
					" SUM(Actual_fr_Period_Apr_Nov)AS Actual_fr_Period_Apr_Nov "+
					" FROM "+
					" (SELECT budger_g_id,BUDGET_GROUP_MAJOR, "+
					"   DECODE(Ac_fr_lst_yr,NULL,0,Ac_fr_lst_yr)                         AS Ac_fr_lst_yr, "+
					"   DECODE(BE_fr_Yr,NULL,0,BE_fr_Yr)                                 AS BE_fr_Yr, "+
					"   DECODE(Actual_fr_Period_Apr_Nov,NULL,0,Actual_fr_Period_Apr_Nov) AS Actual_fr_Period_Apr_Nov "+
					" FROM "+
					"   (SELECT rownum AS slno1,budger_g_id, "+
					"     BUDGET_GROUP_MAJOR,ACC_HEAD_CODE "+
					"   FROM "+
					"     (SELECT DISTINCT BUDGET_GROUP_ID AS budger_g_id, "+
					"       ACC_HEAD_CODE FROM FAS_BUDGET_AC_HEADS_MAP "+
					"     WHERE FORMAT_NO=1 ORDER BY BUDGET_GROUP_ID "+
					"     )a "+
					"   LEFT OUTER JOIN "+
					"     (SELECT BUDGET_GROUP_ID,BUDGET_GROUP_MAJOR "+
					"     FROM FAS_BUDGET_GROUP_MASTER "+
					"     ORDER BY BUDGET_GROUP_ID "+
					"     )b "+
					"   ON a.budger_g_id =b.BUDGET_GROUP_ID "+
					"   )X "+
					" LEFT OUTER JOIN "+
					"   ( "+
					"   SELECT ACCOUNT_HEAD_CODE_XX, "+
					"     (DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) + DECODE(Ac_fr_lst_yr_YY,'',0,Ac_fr_lst_yr_YY)) AS Ac_fr_lst_yr "+
					"   FROM "+
					"     (SELECT ACCOUNT_HEAD_CODE1                                                           AS ACCOUNT_HEAD_CODE_XX, "+
				//Lakshmi 7Nov13	"       DECODE((a.Ac_fr_lst_yr1+b.Ac_fr_lst_yr2),NULL,0,(a.Ac_fr_lst_yr1+b.Ac_fr_lst_yr2)) AS Ac_fr_lst_yr_XX "+
					" ( DECODE(a.Ac_fr_lst_yr1,NULL,0,a.Ac_fr_lst_yr1) + DECODE(b.Ac_fr_lst_yr2,NULL,0,b.Ac_fr_lst_yr2) )AS Ac_fr_lst_yr_XX"+
					"     FROM "+
					"        (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr1, "+
					"          ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE1 "+
					"       FROM FAS_TRIAL_BALANCE "+
					"       WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
					"       AND CASHBOOK_YEAR       = "+y1+
					"       AND CASHBOOK_MONTH BETWEEN 4 AND 12 "+
					"       GROUP BY ACCOUNT_HEAD_CODE "+
					"       )a "+
					"     LEFT OUTER JOIN "+
					"       (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
					"         ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE2 "+
					"       FROM FAS_TRIAL_BALANCE "+
					"       WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
					"       AND CASHBOOK_YEAR       = "+y2+
					"       AND CASHBOOK_MONTH BETWEEN 1 AND 3 "+
					"       GROUP BY ACCOUNT_HEAD_CODE "+
					"       )b "+
					"     ON a.ACCOUNT_HEAD_CODE1 =b.ACCOUNT_HEAD_CODE2 "+
					"     )XX "+
					"   LEFT OUTER JOIN "+
					"     (SELECT ACCOUNT_HEAD_CODE2                                   AS ACCOUNT_HEAD_CODE_YY,"+
					"      Ac_fr_lst_yr2 AS Ac_fr_lst_yr_YY "+
					"     FROM "+
					"         (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
					"         ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE2 "+
					"       FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
					"       WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
					"       AND CASHBOOK_YEAR       = "+y2+
					"       AND CASHBOOK_MONTH BETWEEN 1 AND 3 "+
					"       GROUP BY ACCOUNT_HEAD_CODE "+
					"       ) "+
					"     )YY ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
					"   )Y ON X.ACC_HEAD_CODE            =Y.ACCOUNT_HEAD_CODE_XX "+
					" LEFT OUTER JOIN "+
					"   (SELECT ACCOUNT_HEAD_CODE_XX                   AS ACCOUNT_HEAD_CODE_ZZ, "+
					"     DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) AS BE_fr_Yr "+
					"   FROM "+
					"     (SELECT DECODE(SUM(CURRENT_YEAR_BUDGET_ALLOTTED),NULL,0,SUM(CURRENT_YEAR_BUDGET_ALLOTTED)) AS Ac_fr_lst_yr_XX, "+
					"       ACCOUNT_HEAD_CODE                                                                        AS ACCOUNT_HEAD_CODE_XX "+
					"     FROM COM_BUDGET_DETAILS "+
					"     Where Accounting_Unit_Id="+cmbAcc_UnitCode+
					"     AND FINANCIAL_YEAR      = '"+(year1)+"-"+(ssyr2.trim())+"'"+ //para change 
					"     GROUP BY ACCOUNT_HEAD_CODE "+
					"     ) "+
					"   )Z "+
					" ON X.ACC_HEAD_CODE =Z.ACCOUNT_HEAD_CODE_ZZ "+
					" LEFT OUTER JOIN "+
					"   (SELECT ACCOUNT_HEAD_CODE_XX              AS ACCOUNT_HEAD_CODE_KK, "+
					"     (DECODE(Ac_fr_lst_yr1,'',0,Ac_fr_lst_yr1) + DECODE(Ac_fr_lst_yr2,'',0,Ac_fr_lst_yr2)) AS Actual_fr_Period_Apr_Nov "+
					"   FROM "+
					"     (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr1, "+
					"       ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE_XX "+
					"     FROM FAS_TRIAL_BALANCE "+
					"     WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
					"     AND CASHBOOK_YEAR       ="+y2+
					"     AND CASHBOOK_MONTH BETWEEN 4 AND 12 "+
					"     GROUP BY ACCOUNT_HEAD_CODE "+
					"     )XX "+
					"   LEFT OUTER JOIN "+
					"     (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
					"       ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE_YY "+
					"     FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
					"     WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
					"     AND CASHBOOK_YEAR       ="+y2+
					"     AND CASHBOOK_MONTH BETWEEN 4 AND 12 "+
					"     GROUP BY ACCOUNT_HEAD_CODE "+
					"     )YY "+
					"   ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
					"   )KK ON X.ACC_HEAD_CODE     =KK.ACCOUNT_HEAD_CODE_KK "+
					" ) "+
					" GROUP BY budger_g_id, "+
					" BUDGET_GROUP_MAJOR "+
					" ORDER BY budger_g_id  ";
					
					/*String ss1 = " SELECT budger_g_id, "+
					 " STATEMENT_DESC, "+
					"  SUM(Ac_fr_lst_yr)            AS Ac_fr_lst_yr, "+
					" SUM(BE_fr_Yr)                AS BE_fr_Yr, "+
					" SUM(Actual_fr_Period_Apr_Nov)AS Actual_fr_Period_Apr_Nov "+
					" FROM "+
					" (SELECT budger_g_id,STATEMENT_DESC, "+
					"   DECODE(Ac_fr_lst_yr,NULL,0,Ac_fr_lst_yr)                         AS Ac_fr_lst_yr, "+
					"   DECODE(BE_fr_Yr,NULL,0,BE_fr_Yr)                                 AS BE_fr_Yr, "+
					"   DECODE(Actual_fr_Period_Apr_Nov,NULL,0,Actual_fr_Period_Apr_Nov) AS Actual_fr_Period_Apr_Nov "+
					" FROM "+
					"   (SELECT rownum AS slno1,budger_g_id, "+
					"     STATEMENT_DESC,ACC_HEAD_CODE "+
					"   FROM "+
					"   (SELECT DISTINCT STATEMENT_GROUP_NO AS budger_g_id, "+
					"    FROM_ACC_HD_CODE as ACC_HEAD_CODE "+
					"   FROM FAS_STATEMENT_ACC_HD_MAPPING "+
					"   WHERE STATEMENT_NO=1 "+
					"   ORDER BY STATEMENT_GROUP_NO "+
					"   )a "+
					"  LEFT OUTER JOIN "+
					"   (SELECT STATEMENT_NO, "+
					"     STATEMENT_DESC "+
					"   FROM fas_statement_master "+
					"   ORDER BY STATEMENT_NO "+
					"   )b "+
					"  ON a.budger_g_id =b.STATEMENT_NO "+
					"  )X "+
					" LEFT OUTER JOIN "+
					"   ( "+
					"   SELECT ACCOUNT_HEAD_CODE_XX, "+
					"     (DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) + DECODE(Ac_fr_lst_yr_YY,'',0,Ac_fr_lst_yr_YY)) AS Ac_fr_lst_yr "+
					"   FROM "+
					"     (SELECT ACCOUNT_HEAD_CODE1                                                           AS ACCOUNT_HEAD_CODE_XX, "+
				//Lakshmi 7Nov13	"       DECODE((a.Ac_fr_lst_yr1+b.Ac_fr_lst_yr2),NULL,0,(a.Ac_fr_lst_yr1+b.Ac_fr_lst_yr2)) AS Ac_fr_lst_yr_XX "+
					" ( DECODE(a.Ac_fr_lst_yr1,NULL,0,a.Ac_fr_lst_yr1) + DECODE(b.Ac_fr_lst_yr2,NULL,0,b.Ac_fr_lst_yr2) )AS Ac_fr_lst_yr_XX"+
					"     FROM "+
					"        (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr1, "+
					"          ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE1 "+
					"       FROM FAS_TRIAL_BALANCE "+
					"       WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
					"       AND CASHBOOK_YEAR       = "+y1+
					"       AND CASHBOOK_MONTH BETWEEN 4 AND 12 "+
					"       GROUP BY ACCOUNT_HEAD_CODE "+
					"       )a "+
					"     LEFT OUTER JOIN "+
					"       (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
					"         ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE2 "+
					"       FROM FAS_TRIAL_BALANCE "+
					"       WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
					"       AND CASHBOOK_YEAR       = "+y2+
					"       AND CASHBOOK_MONTH BETWEEN 1 AND 3 "+
					"       GROUP BY ACCOUNT_HEAD_CODE "+
					"       )b "+
					"     ON a.ACCOUNT_HEAD_CODE1 =b.ACCOUNT_HEAD_CODE2 "+
					"     )XX "+
					"   LEFT OUTER JOIN "+
					"     (SELECT ACCOUNT_HEAD_CODE2                                   AS ACCOUNT_HEAD_CODE_YY,"+
					"      Ac_fr_lst_yr2 AS Ac_fr_lst_yr_YY "+
					"     FROM "+
					"         (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
					"         ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE2 "+
					"       FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
					"       WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
					"       AND CASHBOOK_YEAR       = "+y2+
					"       AND CASHBOOK_MONTH BETWEEN 1 AND 3 "+
					"       GROUP BY ACCOUNT_HEAD_CODE "+
					"       ) "+
					"     )YY ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
					"   )Y ON X.ACC_HEAD_CODE            =Y.ACCOUNT_HEAD_CODE_XX "+
					" LEFT OUTER JOIN "+
					"   (SELECT ACCOUNT_HEAD_CODE_XX                   AS ACCOUNT_HEAD_CODE_ZZ, "+
					"     DECODE(Ac_fr_lst_yr_XX,'',0,Ac_fr_lst_yr_XX) AS BE_fr_Yr "+
					"   FROM "+
					"     (SELECT DECODE(SUM(CURRENT_YEAR_BUDGET_ALLOTTED),NULL,0,SUM(CURRENT_YEAR_BUDGET_ALLOTTED)) AS Ac_fr_lst_yr_XX, "+
					"       FROM_ACC_HD_CODE                                                                        AS ACCOUNT_HEAD_CODE_XX "+
					"     FROM COM_BUDGET_DETAILS "+
					"     Where Accounting_Unit_Id="+cmbAcc_UnitCode+
					"     AND FINANCIAL_YEAR      = '"+(year1)+"-"+(ssyr2.trim())+"'"+ //para change 
					"     GROUP BY FROM_ACC_HD_CODE "+
					"     ) "+
					"   )Z "+
					" ON X.ACC_HEAD_CODE =Z.ACCOUNT_HEAD_CODE_ZZ "+
					" LEFT OUTER JOIN "+
					"   (SELECT ACCOUNT_HEAD_CODE_XX              AS ACCOUNT_HEAD_CODE_KK, "+
					"     (DECODE(Ac_fr_lst_yr1,'',0,Ac_fr_lst_yr1) + DECODE(Ac_fr_lst_yr2,'',0,Ac_fr_lst_yr2)) AS Actual_fr_Period_Apr_Nov "+
					"   FROM "+
					"     (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr1, "+
					"       ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE_XX "+
					"     FROM FAS_TRIAL_BALANCE "+
					"     WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
					"     AND CASHBOOK_YEAR       ="+y2+
					"     AND CASHBOOK_MONTH BETWEEN 4 AND 12 "+
					"     GROUP BY ACCOUNT_HEAD_CODE "+
					"     )XX "+
					"   LEFT OUTER JOIN "+
					"     (SELECT (decode(Sum(Current_Month_Debit),null,0,Sum(Current_Month_Debit))-decode(Sum(Current_Month_Credit),null,0,Sum(Current_Month_Credit))) AS Ac_fr_lst_yr2, "+
					"       ACCOUNT_HEAD_CODE                 AS ACCOUNT_HEAD_CODE_YY "+
					"     FROM FAS_TRIAL_BALANCE_SUPPLEMENT "+
					"     WHERE ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+
					"     AND CASHBOOK_YEAR       ="+y2+
					"     AND CASHBOOK_MONTH BETWEEN 4 AND 12 "+
					"     GROUP BY ACCOUNT_HEAD_CODE "+
					"     )YY "+
					"   ON XX.ACCOUNT_HEAD_CODE_XX = YY.ACCOUNT_HEAD_CODE_YY "+
					"   )KK ON X.ACC_HEAD_CODE     =KK.ACCOUNT_HEAD_CODE_KK "+
					" ) "+
					" GROUP BY budger_g_id, "+
					" STATEMENT_DESC "+
					" ORDER BY budger_g_id  ";*/
					
					System.out.println("ss1:::"+ss1);
					ps = connection.prepareStatement(ss1);
				//	ps.setInt(1, year1 - 1);
				//	ps.setInt(2, year2 - 1);
				//	ps.setInt(3, year1 - 1);
				//	ps.setInt(4, year2 - 1);
				//	ps.setString(1, (year1) + "-" + (year2));

					rs = ps.executeQuery();
					while (rs.next()) {
						xml = xml + "<BudgetGroupMajor>"
								+ rs.getString("BUDGET_GROUP_MAJOR")
								+ "</BudgetGroupMajor>";
						xml = xml + "<budger_g_id>"+ rs.getInt("budger_g_id")+ "</budger_g_id>";
						xml = xml + "<Ac_fr_lst_yr>"
								+ rs.getInt("Ac_fr_lst_yr") + "</Ac_fr_lst_yr>";
						xml = xml + "<BE_fr_Yr>" + rs.getInt("BE_fr_Yr")+ "</BE_fr_Yr>";
						xml = xml + "<actuals_for_period_apr_to_nov>"+0+ "</actuals_for_period_apr_to_nov>";
						xml = xml + "<account_head>" +0+ "</account_head>";
						xml = xml + "<Actual_fr_Period_Apr_Nov>"+ rs.getInt("Actual_fr_Period_Apr_Nov")+ "</Actual_fr_Period_Apr_Nov>";
						int anti1=((rs.getInt("Actual_fr_Period_Apr_Nov"))/8)*4;
						xml = xml + "<ANTICIPATED_FR_PERIOD_DEC_MAR>"+anti1+ "</ANTICIPATED_FR_PERIOD_DEC_MAR>";
						xml = xml + "<RE_FOR_YEAR>"+0+ "</RE_FOR_YEAR>";
						xml = xml + "<BE_FOR_NEXT_YEAR>"+0+ "</BE_FOR_NEXT_YEAR>";
						xml = xml + "<variation>"+0	+ "</variation>";
						xml = xml + "<reason_variation>"+"-"+ "</reason_variation>";
						xml = xml + "<variation_btwn>"+"-"+ "</variation_btwn>";
					}
					xml = xml + "<flag>success</flag>";
				}
				
				
			}
			catch(Exception e) {
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
		PreparedStatement ps = null,ps4=null;
		PreparedStatement ps1 = null,ps5=null;
		ResultSet rs = null,rs4=null;
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
		int cmbAcc_UnitCode = 0,test_insert=0;
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
		String Head_of_Account[] = new String[RecordCount];
		String budget_gg_id1[]=new String[RecordCount] ;
		String hd_of_Acc[]=new String[RecordCount];
		String Actuals_for_Last_Year[] = new String[RecordCount];
		String BE_for_the_Year[] = new String[RecordCount];
		String Actuals_for_Period_Apr_to_Nov[] = new String[RecordCount];
		String Anticipated_for_Period_Dec_to_Mar[] = new String[RecordCount];
		String RE_for_Year[] = new String[RecordCount];
		String Variation_betwen_BE_and_RE[] = new String[RecordCount];
		String Reason_for_Variation[] = new String[RecordCount];
		String BE_for_Next_Year[] = new String[RecordCount];
		String Variation_btwn_REyr_and_NXTyr[] = new String[RecordCount];

		/* Variables Declaration */
		String Head_of_Account2 = null;
		String HD_of_ACC=null,Allocatype2=null;;
		double Actuals_for_Last_Year2 = 0.0d;
		double BE_for_the_Year2 = 0.0d;
		double Actuals_for_Period_Apr_to_Nov2 = 0.0d;
		double Anticipated_for_Period_Dec_to_Mar2 = 0.0d;
		double RE_for_Year2 = 0.0d;
		double Variation_betwen_BE_and_RE2 = 0.0d;
		String Reason_for_Variation2 = null;
		double BE_for_Next_Year2 = 0.0d;
		String Variation_btwn_REyr_and_NXTyr2 = null;
		int flag = 0;
		String office_level_id = null;
		int division_id = 0,budget_gg_id2=0;
		int circle_id = 0;
		int region_id = 0;
		int head_office_id = 0;
		try {
			con.setAutoCommit(false);
			con.clearWarnings();
			if (filter.equals("save")) {
				String detail=request.getParameter("r1");
				if(detail.equalsIgnoreCase("DetailHead"))
				{
					System.out.println("inside the detail check............");
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
				ps=con.prepareStatement("select accounting_unit_id,accounting_for_office_id,financial_year,  head_of_account,  actuals_for_last_year,"
								+"  be_for_the_year,  actuals_for_period_apr_to_nov,  ANTICIPATED_FR_PERIOD_DEC_MAR,  RE_FOR_YEAR,  VARIATION_BETWEN_BE_RE,"
								+"  REASON_FOR_VARIATION,  BE_FOR_NEXT_YEAR,  VARIATION_BTWN_REYR_AND_NXTYR, UPDATED_BY_USERID,  UPDATED_DATE,"
								+"  DIVISION,  CIRCLE,  REGION,  HEAD_OFFICE,  OFFICE_LEVEL_ID,  SL_NO from  FAS_BUDGET_FORMAT_1 WHERE ACCOUNTING_UNIT_ID    =?"
								+"AND ACCOUNTING_FOR_OFFICE_ID=? AND FINANCIAL_YEAR          =?");
				ps.setInt(1, cmbAcc_UnitCode);
				ps.setInt(2, cmbOffice_code);
				ps.setString(3, FinancialYear);
				rs = ps.executeQuery();
				if(rs.next())
				{

					
					String ss="delete from FAS_BUDGET_FORMAT_1 where accounting_unit_id  =? AND accounting_for_office_id     =? AND financial_year               =?";
					//System.out.println("ssssssssssssssssssssssssssssssssssssssssssss"+ss);
					ps = con
					.prepareStatement(ss);
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setString(3, FinancialYear);
					ps.executeUpdate();
					con.commit();
			for(int k=0;k<RecordCount;k++)
			{


				/* Budget group */
				try {
					Head_of_Account2 = request
							.getParameter("head_account" + k);
				} catch (Exception e) {
					System.out
							.println("Error for getting Head_of_Account -->"
									+ e);
				}
				
				/* Budget group  id */
				try {
					budget_gg_id2 = Integer.parseInt(request.getParameter("budget_gg_id" + k));
				} catch (Exception e) {
					System.out
							.println("Error for getting budget_gg_id   -->"
									+ e);
				}
				
				//ALLOCATION_TYPE
				try {
					Allocatype2 = request.getParameter("ALLOCATION_TYPE" + k);
				} catch (Exception e) {
					System.out
							.println("Error for getting Allocatype2   -->"
									+ e);
				}
				
				/* Actuals for the Last Year */
				try {
					Actuals_for_Last_Year[k] = request
							.getParameter("Actuals_for_Last_Year" + k);
					if (Actuals_for_Last_Year[k] != null) {
						if (Actuals_for_Last_Year[k].equals("")) {
							Actuals_for_Last_Year2 = 0.0d;
						} else {
							Actuals_for_Last_Year2 = Double
									.parseDouble(Actuals_for_Last_Year[k]);
						}
					} else {
						Actuals_for_Last_Year2 = 0.0d;
					}
				} catch (Exception e) {
					System.out.println(e);
				}

				/* BE for the Year */
				try {
					BE_for_the_Year[k] = request
							.getParameter("BE_for_the_Year" + k);
					if (BE_for_the_Year[k] != null) {
						if (BE_for_the_Year[k].equals("")) {
							BE_for_the_Year2 = 0.0d;
						} else {
							BE_for_the_Year2 = Double
									.parseDouble(BE_for_the_Year[k]);
						}
					} else {
						BE_for_the_Year2 = 0.0d;
					}
				} catch (Exception e) {
					System.out.println(e);
				}

				/* Actuals for the Period Apr to Nov */
				try {
					Actuals_for_Period_Apr_to_Nov[k] = request.getParameter("Actuals_for_Period_Apr_to_Nov"+ k);
					if (Actuals_for_Period_Apr_to_Nov[k] != null) {
						if (Actuals_for_Period_Apr_to_Nov[k].equals("")) {
							Actuals_for_Period_Apr_to_Nov2 = 0.0d;
						} else {
							Actuals_for_Period_Apr_to_Nov2 = Double
									.parseDouble(Actuals_for_Period_Apr_to_Nov[k]);
						}
					} else {
						Actuals_for_Period_Apr_to_Nov2 = 0.0d;
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				//System.out.println("Actuals_for_Period_Apr_to_Nov  " + Actuals_for_Period_Apr_to_Nov2);
				/* Anticipated for the Period Dec to Mar */
				try {
					Anticipated_for_Period_Dec_to_Mar[k] = request
							.getParameter("Anticipated_for_Period_Dec_to_Mar"
									+ k);
					if (Anticipated_for_Period_Dec_to_Mar[k] != null) {
						if (Anticipated_for_Period_Dec_to_Mar[k]
								.equals("")) {
							Anticipated_for_Period_Dec_to_Mar2 = 0.0d;
						} else {
							Anticipated_for_Period_Dec_to_Mar2 = Double
									.parseDouble(Anticipated_for_Period_Dec_to_Mar[k]);
							//System.out.println("Anticipated_for_Period_Dec_to_Mar2............."+Anticipated_for_Period_Dec_to_Mar2);
						}
					} else {
						Anticipated_for_Period_Dec_to_Mar2 = 0.0d;
					}
				} catch (Exception e) {
					System.out.println(e);
				}
				//System.out.println("be44444444444444444444444444444444444444444:");
				/* RE for the Year */
				try {
					RE_for_Year[k] = request.getParameter("RE_FOR_YEAR"+ k);
					//System.out.println("hh::"+RE_for_Year[k]);
					if (RE_for_Year[k] != null) {
						if (RE_for_Year[k].equals("")) {
							RE_for_Year2 = 0.0d;
						} else {
							RE_for_Year2 = Double
									.parseDouble(RE_for_Year[k]);
						}
					} else {
						RE_for_Year2 = 0.0d;
					}
				} catch (Exception e) {
					System.out.println(e);
				}

				/* Variation betwen BE and RE */
				try {
					Variation_betwen_BE_and_RE[k] = request
							.getParameter("Variation_betwen_BE_and_RE"
									+ k);
					if (Variation_betwen_BE_and_RE[k] != null) {
						if (Variation_betwen_BE_and_RE[k].equals("")) {
							Variation_betwen_BE_and_RE2 = 0.0d;
						} else {
							Variation_betwen_BE_and_RE2 = Double
									.parseDouble(Variation_betwen_BE_and_RE[k]);
							
						}
					} else {
						Variation_betwen_BE_and_RE2 = 0.0d;
					}
				} catch (Exception e) {
					System.out.println(e);
				}

				/* Reason for Variation */
				try {
					Reason_for_Variation2 = request
							.getParameter("Reason_for_Variation" + k);
				} catch (Exception e) {
					System.out
							.println("Error for getting Reason_for_Variation -->"
									+ e);
				}

				/* BE for Next Year */
				try {
					BE_for_Next_Year[k] = request
							.getParameter("BE_FOR_NEXT_YEAR" + k);
					if (BE_for_Next_Year[k] != null) {
						if (BE_for_Next_Year[k].equals("")) {
							BE_for_Next_Year2 = 0.0d;
						} else {
							BE_for_Next_Year2 = Double
									.parseDouble(BE_for_Next_Year[k]);
						}
					} else {
						BE_for_Next_Year2 = 0.0d;
					}
				} catch (Exception e) {
					System.out.println(e);
				}

				/*
				 * Reason for Variation if any between RE for the Year
				 * and the next Year
				 */
				try {
					Variation_btwn_REyr_and_NXTyr2 = request
							.getParameter("Variation_btwn_REyr_and_NXTyr"
									+ k);
				} catch (Exception e) {
					System.out
							.println("Error for getting Variation_btwn_REyr_and_NXTyr -->"
									+ e);
				}
				int i = 1, i1 = 0;
				try {
					ps = con
							.prepareStatement("Select max(SL_NO) from FAS_BUDGET_FORMAT_1");
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
				//System.out.println("Anticipated_for_Period_Dec_to_Mar2.....fgdfg........"+Anticipated_for_Period_Dec_to_Mar2);
				//System.out.println("Head_of_Account2.....fgdfg........"+Head_of_Account2);
				String ss1="insert into FAS_BUDGET_FORMAT_1(HEAD_OF_ACCOUNT,ACTUALS_FOR_LAST_YEAR,BE_FOR_THE_YEAR,ACTUALS_FOR_PERIOD_APR_TO_NOV,ANTICIPATED_FR_PERIOD_DEC_MAR,RE_FOR_YEAR,VARIATION_BETWEN_BE_RE,REASON_FOR_VARIATION,BE_FOR_NEXT_YEAR,VARIATION_BTWN_REYR_AND_NXTYR,ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,UPDATED_BY_USERID,UPDATED_DATE,BUDGET_GROUP_ID,ALLOCATION_TYPE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)";
			//	System.out.println("inserttttttttttttttttttttttttttttttt"+ss1);
				ps = con
						.prepareStatement(ss1);
				/*ps=con.prepareStatement("UPDATE FAS_BUDGET_FORMAT_1 SET ANTICIPATED_FR_PERIOD_DEC_MAR=?,  RE_FOR_YEAR   =?, BE_FOR_NEXT_YEAR   =?"
						+"WHERE accounting_unit_id  =? AND accounting_for_office_id     =? AND financial_year               =?");*/
				ps.setString(1, Head_of_Account2);
				ps.setDouble(2, Actuals_for_Last_Year2);
				ps.setDouble(3, BE_for_the_Year2);
				ps.setDouble(4, Actuals_for_Period_Apr_to_Nov2);
				ps.setDouble(5, Anticipated_for_Period_Dec_to_Mar2);
				ps.setDouble(6, RE_for_Year2);
				ps.setDouble(7, Variation_betwen_BE_and_RE2);
				ps.setString(8, Reason_for_Variation2);
				ps.setDouble(9, BE_for_Next_Year2);
				ps.setString(10, Variation_btwn_REyr_and_NXTyr2);
				ps.setInt(11, cmbAcc_UnitCode);
				ps.setInt(12, cmbOffice_code);
				ps.setString(13, FinancialYear);
				ps.setInt(14, i);
				ps.setInt(15, division_id);
				ps.setInt(16, circle_id);
				ps.setInt(17, region_id);
				ps.setInt(18, head_office_id);
				ps.setString(19, office_level_id);
				ps.setString(20, userid);
				ps.setTimestamp(21, ts);
				ps.setInt(22,budget_gg_id2);
				ps.setString(23,Allocatype2);
				
				int kk = ps.executeUpdate();
			
			}flag = 2;
				
				
				}
				else
				{

					System.out.println("else block");
					
					for (int k = 0; k < RecordCount; k++) {

						/* Budget group */
						try {
							Head_of_Account2 = request
									.getParameter("head_account" + k);
						} catch (Exception e) {
							System.out
									.println("Error for getting Head_of_Account -->"
											+ e);
						}
						
						/* Budget group  id */
						try {
							budget_gg_id2 = Integer.parseInt(request.getParameter("budget_gg_id" + k));
						} catch (Exception e) {
							System.out
									.println("Error for getting budget_gg_id   -->"
											+ e);
						}
						//ALLOCATION_TYPE
						try {
							Allocatype2 = request.getParameter("ALLOCATION_TYPE" + k);
						} catch (Exception e) {
							System.out
									.println("Error for getting Allocatype2   -->"
											+ e);
						}
						
						/*Head of Account*/
						
						try {
							HD_of_ACC = request
									.getParameter("head_account" + k);
						} catch (Exception e) {
							System.out
									.println("Error for getting Head_of_Account Code->"
											+ e);
						}
						
						
						
						
						/* Actuals for the Last Year */
						try {
							Actuals_for_Last_Year[k] = request
									.getParameter("Actuals_for_Last_Year" + k);
							if (Actuals_for_Last_Year[k] != null) {
								if (Actuals_for_Last_Year[k].equals("")) {
									Actuals_for_Last_Year2 = 0.0d;
								} else {
									Actuals_for_Last_Year2 = Double
											.parseDouble(Actuals_for_Last_Year[k]);
								}
							} else {
								Actuals_for_Last_Year2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						/* BE for the Year */
						try {
							BE_for_the_Year[k] = request
									.getParameter("BE_for_the_Year" + k);
							if (BE_for_the_Year[k] != null) {
								if (BE_for_the_Year[k].equals("")) {
									BE_for_the_Year2 = 0.0d;
								} else {
									BE_for_the_Year2 = Double
											.parseDouble(BE_for_the_Year[k]);
								}
							} else {
								BE_for_the_Year2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						/* Actuals for the Period Apr to Nov */
						try {
							Actuals_for_Period_Apr_to_Nov[k] = request
									.getParameter("Actuals_for_Period_Apr_to_Nov"
											+ k);
							if (Actuals_for_Period_Apr_to_Nov[k] != null) {
								if (Actuals_for_Period_Apr_to_Nov[k].equals("")) {
									Actuals_for_Period_Apr_to_Nov2 = 0.0d;
								} else {
									Actuals_for_Period_Apr_to_Nov2 = Double
											.parseDouble(Actuals_for_Period_Apr_to_Nov[k]);
								}
							} else {
								Actuals_for_Period_Apr_to_Nov2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						/* Anticipated for the Period Dec to Mar */
						try {
							Anticipated_for_Period_Dec_to_Mar[k] = request
									.getParameter("Anticipated_for_Period_Dec_to_Mar"
											+ k);
							if (Anticipated_for_Period_Dec_to_Mar[k] != null) {
								if (Anticipated_for_Period_Dec_to_Mar[k]
										.equals("")) {
									Anticipated_for_Period_Dec_to_Mar2 = 0.0d;
								} else {
									Anticipated_for_Period_Dec_to_Mar2 = Double
											.parseDouble(Anticipated_for_Period_Dec_to_Mar[k]);
								}
							} else {
								Anticipated_for_Period_Dec_to_Mar2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						/* RE for the Year */
						try {
							RE_for_Year[k] = request.getParameter("RE_FOR_YEAR"
									+ k);
							if (RE_for_Year[k] != null) {
								if (RE_for_Year[k].equals("")) {
									RE_for_Year2 = 0.0d;
								} else {
									RE_for_Year2 = Double
											.parseDouble(RE_for_Year[k]);
								}
							} else {
								RE_for_Year2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						/* Variation betwen BE and RE */
						try {
							Variation_betwen_BE_and_RE[k] = request
									.getParameter("Variation_betwen_BE_and_RE"
											+ k);
							if (Variation_betwen_BE_and_RE[k] != null) {
								if (Variation_betwen_BE_and_RE[k].equals("")) {
									Variation_betwen_BE_and_RE2 = 0.0d;
								} else {
									Variation_betwen_BE_and_RE2 = Double
											.parseDouble(Variation_betwen_BE_and_RE[k]);
								}
							} else {
								Variation_betwen_BE_and_RE2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						/* Reason for Variation */
						try {
							Reason_for_Variation2 = request
									.getParameter("Reason_for_Variation" + k);
						} catch (Exception e) {
							System.out
									.println("Error for getting Reason_for_Variation -->"
											+ e);
						}

						/* BE for Next Year */
						try {
							BE_for_Next_Year[k] = request
									.getParameter("BE_FOR_NEXT_YEAR" + k);
							if (BE_for_Next_Year[k] != null) {
								if (BE_for_Next_Year[k].equals("")) {
									BE_for_Next_Year2 = 0.0d;
								} else {
									BE_for_Next_Year2 = Double
											.parseDouble(BE_for_Next_Year[k]);
								}
							} else {
								BE_for_Next_Year2 = 0.0d;
							}
						} catch (Exception e) {
							System.out.println(e);
						}

						/*
						 * Reason for Variation if any between RE for the Year
						 * and the next Year
						 */
						try {
							Variation_btwn_REyr_and_NXTyr2 = request
									.getParameter("Variation_btwn_REyr_and_NXTyr"
											+ k);
						} catch (Exception e) {
							System.out
									.println("Error for getting Variation_btwn_REyr_and_NXTyr -->"
											+ e);
						}
						int i = 1, i1 = 0;
						try {
							ps = con
									.prepareStatement("Select max(SL_NO) from FAS_BUDGET_FORMAT_1");
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
								.prepareStatement("insert into FAS_BUDGET_FORMAT_1(HEAD_OF_ACCOUNT,ACTUALS_FOR_LAST_YEAR,BE_FOR_THE_YEAR,ACTUALS_FOR_PERIOD_APR_TO_NOV,ANTICIPATED_FR_PERIOD_DEC_MAR,RE_FOR_YEAR,VARIATION_BETWEN_BE_RE,REASON_FOR_VARIATION,BE_FOR_NEXT_YEAR,VARIATION_BTWN_REYR_AND_NXTYR,ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,UPDATED_BY_USERID,UPDATED_DATE,BUDGET_GROUP_ID,ALLOCATION_TYPE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
						ps.setString(1, Head_of_Account2);
						ps.setDouble(2, Actuals_for_Last_Year2);
						ps.setDouble(3, BE_for_the_Year2);
						ps.setDouble(4, Actuals_for_Period_Apr_to_Nov2);
						ps.setDouble(5, Anticipated_for_Period_Dec_to_Mar2);
						ps.setDouble(6, RE_for_Year2);
						ps.setDouble(7, Variation_betwen_BE_and_RE2);
						ps.setString(8, Reason_for_Variation2);
						ps.setDouble(9, BE_for_Next_Year2);
						ps.setString(10, Variation_btwn_REyr_and_NXTyr2);
						ps.setInt(11, cmbAcc_UnitCode);
						ps.setInt(12, cmbOffice_code);
						ps.setString(13, FinancialYear);
						ps.setInt(14, i);
						ps.setInt(15, division_id);
						ps.setInt(16, circle_id);
						ps.setInt(17, region_id);
						ps.setInt(18, head_office_id);
						ps.setString(19, office_level_id);
						ps.setString(20, userid);
						ps.setTimestamp(21, ts);
						ps.setInt(22,budget_gg_id2);
						ps.setString(23,Allocatype2);
						
						int kk = ps.executeUpdate();
					}
					flag = 2;
				
				}
			}
				String abstractdet=request.getParameter("r1");
				System.out.println("abstractdet:::"+abstractdet);
				  
				con.commit();
				if (flag == 1) {
					sendMessage(
							response,
							"Records Alredy Exist for given Office for given Financial Year ............ ",
							"ok", "Civil_Budget_Format_1.jsp");
				} else if (flag == 2) {
					sendMessage(response,"Records Saved Successfully ............ ", "ok",
							"Civil_Budget_Format_1.jsp");
				}
			} else if (filter.equals("view")) {
				try {
					xml = "<response><command>LoadData</command>";
					ps1 = con
							.prepareStatement("select UPDATED_DATE from FAS_BUDGET_CLOSURE where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and FORMAT_NAME=?");
					ps1.setInt(1, cmbAcc_UnitCode);
					ps1.setInt(2, cmbOffice_code);
					ps1.setString(3, FinancialYear);
					ps1.setString(4, "2");
					rs = ps1.executeQuery();
					if (rs.next()) {
						xml = xml + "<flag>Exist</flag>";
					} else {
						ps = con
								.prepareStatement(" select * from FAS_BUDGET_FORMAT_1 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
						ps.setInt(1, cmbAcc_UnitCode);
						ps.setInt(2, cmbOffice_code);
						ps.setString(3, FinancialYear);
						rs = ps.executeQuery();
						while (rs.next()) {
							xml = xml + "<Head_of_Account>"
									+ rs.getString("HEAD_OF_ACCOUNT")
									+ "</Head_of_Account>";

							xml = xml + "<Actuals_for_Last_Year>"
									+ rs.getBigDecimal("ACTUALS_FOR_LAST_YEAR")
									+ "</Actuals_for_Last_Year>";

							xml = xml + "<BE_for_the_Year>"
									+ rs.getBigDecimal("BE_FOR_THE_YEAR")
									+ "</BE_for_the_Year>";

							xml = xml
									+ "<Actuals_for_Period_Apr_to_Nov>"
									+ rs
											.getBigDecimal("ACTUALS_FOR_PERIOD_APR_TO_NOV")
									+ "</Actuals_for_Period_Apr_to_Nov>";

							xml = xml
									+ "<Anticipated_for_Period_Dec_to_Mar>"
									+ rs
											.getBigDecimal("ANTICIPATED_FR_PERIOD_DEC_MAR")
									+ "</Anticipated_for_Period_Dec_to_Mar>";

							xml = xml + "<RE_for_Year>"
									+ rs.getBigDecimal("RE_FOR_YEAR")
									+ "</RE_for_Year>";

							xml = xml
									+ "<Variation_betwen_BE_and_RE>"
									+ rs
											.getBigDecimal("VARIATION_BETWEN_BE_RE")
									+ "</Variation_betwen_BE_and_RE>";

							xml = xml + "<Reason_for_Variation>"
									+ rs.getString("REASON_FOR_VARIATION")
									+ "</Reason_for_Variation>";

							xml = xml + "<BE_for_Next_Year>"
									+ rs.getBigDecimal("BE_FOR_NEXT_YEAR")
									+ "</BE_for_Next_Year>";

							xml = xml
									+ "<Variation_btwn_REyr_and_NXTyr>"
									+ rs
											.getString("VARIATION_BTWN_REYR_AND_NXTYR")
									+ "</Variation_btwn_REyr_and_NXTyr>";

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
							.prepareStatement(" select * from FAS_BUDGET_FORMAT_1 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setString(3, FinancialYear);
					rs = ps.executeQuery();
					if (rs.next()) {
						ps1 = con
								.prepareStatement(" delete from FAS_BUDGET_FORMAT_1 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
						ps1.setInt(1, cmbAcc_UnitCode);
						ps1.setInt(2, cmbOffice_code);
						ps1.setString(3, FinancialYear);
						int sbg = ps1.executeUpdate();
						if (sbg > 0) {
							con.commit();
							sendMessage(
									response,
									"Records Deleted Successfully ............ ",
									"ok", "Civil_Budget_Format_1.jsp");
						} else {
							sendMessage(response,
									"Records Deletion Failed ............ ",
									"ok", "Civil_Budget_Format_1.jsp");
						}
					} else {
						sendMessage(response,
								"Records Does Not Exist ............ ", "ok",
								"Civil_Budget_Format_1.jsp");
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Deletion Failed ............ " + e, "ok",
							"Civil_Budget_Format_1.jsp");
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
							.prepareStatement(" select * from FAS_BUDGET_FORMAT_1 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
					ps.setInt(1, cmbAcc_UnitCode);
					ps.setInt(2, cmbOffice_code);
					ps.setString(3, FinancialYear);
					rs = ps.executeQuery();
					if (rs.next()) {
						ps1 = con
								.prepareStatement(" delete from FAS_BUDGET_FORMAT_1 where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
						ps1.setInt(1, cmbAcc_UnitCode);
						ps1.setInt(2, cmbOffice_code);
						ps1.setString(3, FinancialYear);
						ps1.executeUpdate();
						for (int k = 0; k < RecordCount; k++) {

							/* Head of Account */
							try {
								Head_of_Account2 = request
										.getParameter("Head_of_Account" + k);
							} catch (Exception e) {
								System.out
										.println("Error for getting Head_of_Account -->"
												+ e);
							}

							/* Actuals for the Last Year */
							try {
								Actuals_for_Last_Year[k] = request
										.getParameter("Actuals_for_Last_Year"
												+ k);
								if (Actuals_for_Last_Year[k] != null) {
									if (Actuals_for_Last_Year[k].equals("")) {
										Actuals_for_Last_Year2 = 0.0d;
									} else {
										Actuals_for_Last_Year2 = Double
												.parseDouble(Actuals_for_Last_Year[k]);
									}
								} else {
									Actuals_for_Last_Year2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* BE for the Year */
							try {
								BE_for_the_Year[k] = request
										.getParameter("BE_for_the_Year" + k);
								if (BE_for_the_Year[k] != null) {
									if (BE_for_the_Year[k].equals("")) {
										BE_for_the_Year2 = 0.0d;
									} else {
										BE_for_the_Year2 = Double
												.parseDouble(BE_for_the_Year[k]);
									}
								} else {
									BE_for_the_Year2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* Actuals for the Period Apr to Nov */
							try {
								Actuals_for_Period_Apr_to_Nov[k] = request
										.getParameter("Actuals_for_Period_Apr_to_Nov"
												+ k);
								if (Actuals_for_Period_Apr_to_Nov[k] != null) {
									if (Actuals_for_Period_Apr_to_Nov[k]
											.equals("")) {
										Actuals_for_Period_Apr_to_Nov2 = 0.0d;
									} else {
										Actuals_for_Period_Apr_to_Nov2 = Double
												.parseDouble(Actuals_for_Period_Apr_to_Nov[k]);
									}
								} else {
									Actuals_for_Period_Apr_to_Nov2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* Anticipated for the Period Dec to Mar */
							try {
								Anticipated_for_Period_Dec_to_Mar[k] = request
										.getParameter("Anticipated_for_Period_Dec_to_Mar"
												+ k);
								if (Anticipated_for_Period_Dec_to_Mar[k] != null) {
									if (Anticipated_for_Period_Dec_to_Mar[k]
											.equals("")) {
										Anticipated_for_Period_Dec_to_Mar2 = 0.0d;
									} else {
										Anticipated_for_Period_Dec_to_Mar2 = Double
												.parseDouble(Anticipated_for_Period_Dec_to_Mar[k]);
									}
								} else {
									Anticipated_for_Period_Dec_to_Mar2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* RE for the Year */
							try {
								RE_for_Year[k] = request
										.getParameter("RE_for_Year" + k);
								if (RE_for_Year[k] != null) {
									if (RE_for_Year[k].equals("")) {
										RE_for_Year2 = 0.0d;
									} else {
										RE_for_Year2 = Double
												.parseDouble(RE_for_Year[k]);
									}
								} else {
									RE_for_Year2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* Variation betwen BE and RE */
							try {
								Variation_betwen_BE_and_RE[k] = request
										.getParameter("Variation_betwen_BE_and_RE"
												+ k);
								if (Variation_betwen_BE_and_RE[k] != null) {
									if (Variation_betwen_BE_and_RE[k]
											.equals("")) {
										Variation_betwen_BE_and_RE2 = 0.0d;
									} else {
										Variation_betwen_BE_and_RE2 = Double
												.parseDouble(Variation_betwen_BE_and_RE[k]);
									}
								} else {
									Variation_betwen_BE_and_RE2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/* Reason for Variation */
							try {
								Reason_for_Variation2 = request
										.getParameter("Reason_for_Variation"
												+ k);
							} catch (Exception e) {
								System.out
										.println("Error for getting Reason_for_Variation -->"
												+ e);
							}

							/* BE for Next Year */
							try {
								BE_for_Next_Year[k] = request
										.getParameter("BE_for_Next_Year" + k);
								if (BE_for_Next_Year[k] != null) {
									if (BE_for_Next_Year[k].equals("")) {
										BE_for_Next_Year2 = 0.0d;
									} else {
										BE_for_Next_Year2 = Double
												.parseDouble(BE_for_Next_Year[k]);
									}
								} else {
									BE_for_Next_Year2 = 0.0d;
								}
							} catch (Exception e) {
								System.out.println(e);
							}

							/*
							 * Reason for Variation if any between RE for the
							 * Year and the next Year
							 */
							try {
								Variation_btwn_REyr_and_NXTyr2 = request
										.getParameter("Variation_btwn_REyr_and_NXTyr"
												+ k);
							} catch (Exception e) {
								System.out
										.println("Error for getting Variation_btwn_REyr_and_NXTyr -->"
												+ e);
							}
							int i = 1, i1 = 0;
							try {
								ps = con
										.prepareStatement("Select max(SL_NO) from FAS_BUDGET_FORMAT_1");
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
									.prepareStatement("insert into FAS_BUDGET_FORMAT_1 (HEAD_OF_ACCOUNT,ACTUALS_FOR_LAST_YEAR,BE_FOR_THE_YEAR,ACTUALS_FOR_PERIOD_APR_TO_NOV,ANTICIPATED_FR_PERIOD_DEC_MAR,RE_FOR_YEAR,VARIATION_BETWEN_BE_RE,REASON_FOR_VARIATION,BE_FOR_NEXT_YEAR,VARIATION_BTWN_REYR_AND_NXTYR,ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,SL_NO,DIVISION,CIRCLE,REGION,HEAD_OFFICE,OFFICE_LEVEL_ID,UPDATED_BY_USERID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
							ps.setString(1, Head_of_Account2);
							ps.setDouble(2, Actuals_for_Last_Year2);
							ps.setDouble(3, BE_for_the_Year2);
							ps.setDouble(4, Actuals_for_Period_Apr_to_Nov2);
							ps.setDouble(5, Anticipated_for_Period_Dec_to_Mar2);
							ps.setDouble(6, RE_for_Year2);
							ps.setDouble(7, Variation_betwen_BE_and_RE2);
							ps.setString(8, Reason_for_Variation2);
							ps.setDouble(9, BE_for_Next_Year2);
							ps.setString(10, Variation_btwn_REyr_and_NXTyr2);
							ps.setInt(11, cmbAcc_UnitCode);
							ps.setInt(12, cmbOffice_code);
							ps.setString(13, FinancialYear);
							ps.setInt(14, i);
							ps.setInt(15, division_id);
							ps.setInt(16, circle_id);
							ps.setInt(17, region_id);
							ps.setInt(18, head_office_id);
							ps.setString(19, office_level_id);
							ps.setString(20, userid);
							ps.setTimestamp(21, ts);
							int kk = ps.executeUpdate();
						}
						con.commit();
						sendMessage(response,
								"Records Updated Successfully ............ ",
								"ok", "Civil_Budget_Format_1.jsp");
					} else {
						sendMessage(response,
								"Records Does Not Exist ............ ", "ok",
								"Civil_Budget_Format_1.jsp");
					}
				} catch (Exception e) {
					e.printStackTrace();
					sendMessage(response,
							"Records Updation Failed ............ " + e, "ok",
							"Civil_Budget_Format_1.jsp");
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
					"ok", "Civil_Budget_Format_1.jsp");
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
