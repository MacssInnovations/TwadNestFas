package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.StringTokenizer;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver;

/**
 * Servlet implementation class BRS_Bank_Balance_Update
 */
public class BRS_Bank_Balance_Update extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE ="text/html; charset=windows-1252";   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BRS_Bank_Balance_Update() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
   	 Connection connection = null;
 	PrintWriter out = response.getWriter();
 	   response.setHeader("cache-control","no-cache");
	      String CONTENT_TYPE = "text/xml; charset=windows-1252";
	      response.setContentType(CONTENT_TYPE);
	      
 	PreparedStatement ps = null;
 	PreparedStatement preparedStatement1 = null;
 	ResultSet results;
 	PreparedStatement ps1 = null;
 	PreparedStatement ps2= null,ps3=null;
 	ResultSet rs1,rs2,rs3,results1;
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

     String selstr = "";
     String selspestr = "";
     String sel = "";
     String opt = "";
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
     String strCommand = "",unitid="";
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
	
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
		
		if (strCommand.equalsIgnoreCase("loadBankDetails")) {
			try {
				unitid = request.getParameter("unitid");
				System.out.println("unitid:-" + unitid);
			} catch (Exception e) {
				e.printStackTrace();
			}
			//System.out.println("inside loadBankDetails...");
			int count=0;
			xml = "<response><command>bankDetailsLoad</command>";
			
			try {
				ps = connection
						.prepareStatement("select f2.BANK_ID,(select f1.BANK_SHORT_NAME from FAS_MST_BANKS f1 where f2.BANK_ID=f1.BANK_ID)as BankName,f2.BRANCH_ID,f2.BANK_AC_NO,f2.BANK_AC_TYPE_ID,f2.AC_OPERATIONAL_MODE_ID from FAS_MST_BANK_BALANCE f2 where f2.ACCOUNTING_UNIT_ID='"+unitid+"' and f2.STATUS='Y'");
				results = ps.executeQuery();
				while (results.next()) {
					xml = xml + "<bank_Detail>"+
							 /* results.getInt("BANK_ID")+" - "+
							  results.getInt("BRANCH_ID")+" - "+*/
					          results.getString("BankName")+" - "+
							  results.getString("BANK_AC_NO").trim()+" - "+
							  results.getString("BANK_AC_TYPE_ID")+" - "+
							  results.getString("AC_OPERATIONAL_MODE_ID")
							  
							+ "</bank_Detail>";
					xml=xml+"<bankNo>"+ results.getString("BANK_AC_NO").trim()+"</bankNo>";
					//xml=xml+"<desc>"+results.getString("unit_name").trim()+"("+results.getString("ACCT_UNIT_ID_RENDERED_FOR")+")"+"</desc>";
					count++;
				
				}
				xml = xml + "<count>"+count+"</count>";
				if(count==0){
					xml = xml + "<flag>failure</flag>";
				}else{
					xml = xml + "<flag>success</flag>";
					System.out.println("count"+count);

					
				}
								
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			
		} else if (strCommand.equalsIgnoreCase("loadNonUpdate")) {
			xml = "<response><command>loadNonUpdate</command>";
			int cno = 0;
			String option=request.getParameter("txtoption");
			int cmbAcc_UnitCode,CashbookYear,CashbookMonth = 0;
			PreparedStatement ps_non = null;
			if(option.equalsIgnoreCase("Monthwise"))
			{
				System.out.println("Monthwise....");
				 cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("unitid"));
				 CashbookYear = Integer.parseInt(request
						.getParameter("txtCB_Year"));
				 CashbookMonth = Integer.parseInt(request
						.getParameter("txtCB_Month"));
				 Date new_Date = null;String sqlMonth="";
					Calendar c;
					String[] sd = ("01/01/2014").split("/");
					c = new GregorianCalendar(Integer.parseInt(sd[2]),
							Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
					java.util.Date d = c.getTime();
					new_Date = new Date(d.getTime());
					System.out.println("new_Date >> " + new_Date);
					System.out.println("new_Date....."+new_Date+"CashbookYear..."+CashbookYear+"CashbookMonth..."+CashbookMonth+"cmbAcc_UnitCode..."+cmbAcc_UnitCode);
					try{
					/* String qry="SELECT f.ACCOUNTING_UNIT_ID, "
							+ "  u.accounting_unit_name, "
							+ "  decode(AC_OPENING_DATE,null,'',to_char(f.AC_OPENING_DATE,'dd/mm/yyyy')) as AC_OPENING_DATE, "
							+ "  f.tot_count, "
							+ "  f.cno, "
							+ "  f.BANK_ID, "
							+ "  f.BRANCH_ID, "
							+ "  bank.bank_short_name "
							+ "  ||'-' "
							+ "  ||branch.branch_name AS bank_det, "
							+ "  f.BANK_AC_NO, "
							+ "  f.BANK_AC_TYPE_ID, "
							+ "  ty.account_type, "
							+ "  f.AC_OPERATIONAL_MODE_ID, "
							+ "  mo.ac_operational_mode, "
							+ "  f.initial_deposit_amt, "
							+ "  CASE "
							+ "    WHEN cno<tot_count "
							+ "    THEN 'N' "
							+ "    ELSE 'Y' "
							+ "  END AS status "
							+ " FROM "
							+ "  (SELECT ACCOUNTING_UNIT_ID, "
							+ "    AC_OPENING_DATE, "
							+ "    CASE "
							+ "      WHEN AC_OPENING_DATE IS NOT NULL "
							+ "      AND AC_OPENING_DATE   >? "
							+ // New Date Value
							"      THEN ( MONTHS_BETWEEN ( TO_DATE(? "
							+ // New Year Value
							"        ||'/' "
							+ "        ||? "
							+ // New Month Value
							"        ||'/01','yyyy/mm/dd'), TO_DATE (TO_CHAR(AC_OPENING_DATE, 'yyyy') "
							+ "        ||'/' "
							+ "        ||TO_CHAR(AC_OPENING_DATE, 'mm') "
							+ "        ||'/01','yyyy/mm/dd'))) "
							+ "      ELSE ROUND( MONTHS_BETWEEN ( TO_DATE(? "
							+ // New year Value
							"        ||'/' "
							+ "        ||? "
							+ // New Month Value
							"        ||'/31','yyyy/mm/dd'), TO_DATE (2014 "
							+ "        ||'/' "
							+ "        ||01 "
							+ "        ||'/01','yyyy/mm/dd'))) "
							+ "    END AS tot_count, "
							+ "    (SELECT COUNT(*)AS brs_update "
							+ "    FROM brs_bank_balance_update u "
							+ "    WHERE CB_YEAR           = ? "
							+ // New year Value
							"    AND CB_MONTH           <= ? "
							+ // // New Month Value
							"    AND u.accounting_unit_id=b.accounting_unit_id "
							+ "    AND u.bank_ac_no        =b.bank_ac_no "
							+ "    ) AS cno, "
							+ "    b.BANK_ID, "
							+ "    b.BRANCH_ID, "
							+ "    b.BANK_AC_TYPE_ID, "
							+ "    b.AC_OPERATIONAL_MODE_ID, "
							+ "    b.BANK_AC_NO, "
							+ "    b.initial_deposit_amt "
							+ "  FROM FAS_MST_BANK_BALANCE b "
							+ "  WHERE STATUS ='Y' "
							+
							// "    --  and (AC_OPENING_DATE is null or " +
							// "    --  AC_OPENING_DATE<'01-aug-2014') " +
							"  GROUP BY ACCOUNTING_UNIT_ID, "
							+ "    AC_OPENING_DATE, "
							+ "    b.BANK_ID, "
							+ "    b.BRANCH_ID, "
							+ "    b.BANK_AC_TYPE_ID, "
							+ "    b.AC_OPERATIONAL_MODE_ID, "
							+ "    b.BANK_AC_NO, "
							+ "    b.initial_deposit_amt "
							+ "  )f "
							+ " INNER JOIN FAS_MST_BANK_AC_TYPES ty "
							+ " ON ty.account_type_id=f.BANK_AC_TYPE_ID "
							+ " INNER JOIN FAS_MST_BANKS bank "
							+ " ON bank.bank_id=f.bank_id "
							+ " INNER JOIN FAS_MST_BANK_BRANCHES branch "
							+ " ON branch.bank_id   =f.bank_id "
							+ " AND branch.branch_id=f.branch_id "
							+ " INNER JOIN FAS_MST_AC_OPER_MODES mo "
							+ " ON mo.ac_operational_mode_id=f.ac_operational_mode_id "
							+ " INNER JOIN fas_mst_acct_units u "
							+ " ON u.accounting_unit_id =f.accounting_unit_id "
							+ " AND f.ACCOUNTING_UNIT_ID=? " + // New Unit
																// Value
							" ORDER BY f.ACCOUNTING_UNIT_ID";*/
						if (CashbookMonth>=1 && CashbookMonth<=9)
				        	sqlMonth="0"+CashbookMonth;
				        	else
				        		sqlMonth=""+CashbookMonth;
						/*String   qry="SELECT f.id, " +
				        		"  f.accounting_unit_id, f.CB_YEAR,f.cb_month," +
				        		"  u.accounting_unit_name, " +
				        		"  DECODE(f.AC_OPENING_DATE,NULL,'',TO_CHAR(f.AC_OPENING_DATE,'dd/mm/yyyy')) AS AC_OPENING_DATE, " +
				        		"  f.BANK_ID, " +
				        		"  f.BRANCH_ID, " +
				        		"  bank.bank_short_name " +
				        		"  ||'-' " +
				        		"  ||branch.branch_name AS bank_det, " +
				        		"  f.BANK_AC_NO, " +
				        		"  f.BANK_AC_TYPE_ID, " +
				        		"  ty.account_type, " +
				        		"  f.AC_OPERATIONAL_MODE_ID, " +
				        		"  mo.ac_operational_mode, " +
				        		"  f.initial_deposit_amt, " +
				        		"  f.flag as status" +
				        		" FROM ("+
				        		" SELECT "+CashbookMonth+" AS id, " +
					        	"  A.accounting_unit_id, " +
					        	//"  --B.CB_MONTH , " +
					        	"  a.BANK_ID, " +
					        	"  a.BRANCH_ID, " +
					        	"  a.BANK_AC_TYPE_ID, " +
					        	"  a.AC_OPERATIONAL_MODE_ID, " +
					        	"  a.BANK_AC_NO, " +
					        //	"  --  a.initial_deposit_amt, " +
					        	"  a.ac_opening_date, " +
					        	"  NVL(B.CB_YEAR,"+CashbookYear+") AS CB_YEAR, " +
					        	"  NVL(B.cb_month,"+CashbookMonth+")   AS cb_month, " +
					        	"  CASE " +
					        	"    WHEN B.CB_MONTH      IS NULL " +
					        	"    AND ( A.ac_opening_date < to_date('01/"+sqlMonth+"/"+CashbookYear+"','dd-mm-yy')  or A.ac_opening_date is null ) " +
					        	"    THEN 'N' " +
					        	"    WHEN B.CB_MONTH      IS NULL " +
					        	"    AND A.ac_opening_date > to_date('01/"+sqlMonth+"/"+CashbookYear+"','dd-mm-yy') " +
					        	"    THEN 'Not-Appl' " +
					        	"    ELSE 'Y' " +
					        	"  END AS flag, " +
					        	"  A.initial_deposit_amt " +
					        	" FROM " +
					        	"  (SELECT Accounting_Unit_Id, " +
					        	"    BANK_ID, " +
					        	"    BRANCH_ID, " +
					        	"    BANK_AC_TYPE_ID, " +
					        	"    AC_OPERATIONAL_MODE_ID, " +
					        	"    BANK_AC_NO, " +
					        	"    AC_OPENING_DATE, " +
					        	"    initial_deposit_amt " +
					        	"  FROM fas_mst_bank_balance " +
					        	"  WHERE Accounting_Unit_Id=  " +cmbAcc_UnitCode+
					        	"  AND Status              ='Y' " +
					        	"  )a " +
					        	" LEFT OUTER JOIN " +
					        	"  (SELECT CB_YEAR, " +
					        	"    accounting_unit_id, " +
					        	"    CB_MONTH, " +
					        	"    PS_YEAR, " +
					        	"    PS_MONTH, " +
					        	"    BANK_AC_NO " +
					        	"  FROM brs_bank_balance_update " +
					        	"  WHERE ACCOUNTING_UNIT_ID =  " +cmbAcc_UnitCode+
					        	"  AND Cb_Year              =  " +CashbookYear+
					        	"  AND CB_MONTH             = " +CashbookMonth+
					        	"  )b " +
					        	" ON A.Accounting_Unit_Id=B.Accounting_Unit_Id " +
					        	" AND A.BANK_AC_NO       =B.BANK_AC_NO"+
					        	" ) f INNER JOIN FAS_MST_BANK_AC_TYPES ty ON ty.account_type_id=f.BANK_AC_TYPE_ID INNER JOIN FAS_MST_BANKS bank ON bank.bank_id=f.bank_id INNER JOIN FAS_MST_BANK_BRANCHES branch ON branch.bank_id =f.bank_id AND branch.branch_id=f.branch_id INNER JOIN FAS_MST_AC_OPER_MODES mo ON mo.ac_operational_mode_id=f.ac_operational_mode_id INNER JOIN fas_mst_acct_units u ON u.accounting_unit_id =f.accounting_unit_id and f.flag='N' ORDER BY f.ACCOUNTING_UNIT_ID";
				*/
						
						
						String   qry="SELECT f.id, " +
				        		"  f.accounting_unit_id, f.CB_YEAR,f.cb_month," +
				        		"  u.accounting_unit_name, " +
				        		" CASE WHEN f.ac_opening_date IS NULL THEN null ELSE (f.AC_OPENING_DATE,'yyyy/mm/dd') end as  AC_OPENING_DATE," +
//				        		"  DECODE(f.AC_OPENING_DATE,NULL,'',TO_CHAR(f.AC_OPENING_DATE,'dd/mm/yyyy')) AS AC_OPENING_DATE, " +
				        		"  f.BANK_ID, " +
				        		"  f.BRANCH_ID, " +
				        		"  bank.bank_short_name " +
				        		"  ||'-' " +
				        		"  ||branch.branch_name AS bank_det, " +
				        		"  f.BANK_AC_NO, " +
				        		"  f.BANK_AC_TYPE_ID, " +
				        		"  ty.account_type, " +
				        		"  f.AC_OPERATIONAL_MODE_ID, "
				        		+ " f.ps_date," +
				        		"  mo.ac_operational_mode, " +
				        		"  f.initial_deposit_amt, " +
				        		"  f.flag as status" +
				        		" FROM ("+
				        		" SELECT "+CashbookMonth+" AS id, " +
					        	"  A.accounting_unit_id, " +
					        	//"  --B.CB_MONTH , " +
					        	"  a.BANK_ID, " +
					        	"  a.BRANCH_ID, " +
					        	"  a.BANK_AC_TYPE_ID, " +
					        	"  a.AC_OPERATIONAL_MODE_ID, " +
					        	"  a.BANK_AC_NO, " +
					        //	"  --  a.initial_deposit_amt, " +
					        	"  a.ac_opening_date, " +
					        	"  COALESCE(B.CB_YEAR,"+CashbookYear+") AS CB_YEAR, " +
					        	"  COALESCE(B.cb_month,"+CashbookMonth+")   AS cb_month, " +
					        	"  CASE " +
					        	"    WHEN B.CB_MONTH      IS NULL " +
					        	"    AND ( A.ac_opening_date < to_date('01/"+sqlMonth+"/"+CashbookYear+"','dd-mm-yy')  or A.ac_opening_date is null ) " +
					        	"    THEN 'N' " +
					        	"    WHEN B.CB_MONTH      IS NULL " +
					        	"    AND A.ac_opening_date > to_date('01/"+sqlMonth+"/"+CashbookYear+"','dd-mm-yy') " +
					        	"    THEN 'Not-Appl' " +
					        	"    ELSE 'Y' " +
					        	"  END AS flag, " +
					        	"  A.initial_deposit_amt "
					        	+ " ,  b.ps_date" +
					        	" FROM " +
					        	"  (SELECT Accounting_Unit_Id, " +
					        	"    BANK_ID, " +
					        	"    BRANCH_ID, " +
					        	"    BANK_AC_TYPE_ID, " +
					        	"    AC_OPERATIONAL_MODE_ID, " +
					        	"    BANK_AC_NO, " +
					        	"    AC_OPENING_DATE, " +
					        	"    initial_deposit_amt " +
					        	"  FROM fas_mst_bank_balance " +
					        	"  WHERE Accounting_Unit_Id=  " +cmbAcc_UnitCode+
					        	"  AND Status              ='Y' " +
					        	"  )a " +
					        	" LEFT OUTER JOIN " +
					        	"  (SELECT CB_YEAR, " +
					        	"    accounting_unit_id, " +
					        	"    CB_MONTH, " +
					        	"    PS_YEAR, " +
					        	"    PS_MONTH ,ps_date,  " +
					        	"    BANK_AC_NO " +
					        	"  FROM brs_bank_balance_update " +
					        	"  WHERE ACCOUNTING_UNIT_ID =  " +cmbAcc_UnitCode+
					        	"  AND Cb_Year              =  " +CashbookYear+
					        	"  AND CB_MONTH             = " +CashbookMonth+
					        	"  )b " +
					        	" ON A.Accounting_Unit_Id=B.Accounting_Unit_Id " +
					        	" AND A.BANK_AC_NO       =B.BANK_AC_NO::NUMERIC"+
					        	" ) f INNER JOIN FAS_MST_BANK_AC_TYPES ty ON ty.account_type_id=f.BANK_AC_TYPE_ID INNER JOIN FAS_MST_BANKS bank ON bank.bank_id=f.bank_id INNER JOIN FAS_MST_BANK_BRANCHES branch ON branch.bank_id =f.bank_id AND branch.branch_id=f.branch_id INNER JOIN FAS_MST_AC_OPER_MODES mo ON mo.ac_operational_mode_id=f.ac_operational_mode_id INNER JOIN fas_mst_acct_units u ON u.accounting_unit_id =f.accounting_unit_id and f.flag='N' ORDER BY f.ACCOUNTING_UNIT_ID";
				
						
						System.out.println("qry....."+qry);
					 ps_non = connection
							.prepareStatement(qry);
	                             /*  ps_non.setDate(1, new_Date);
	                               ps_non.setInt(2, CashbookYear);
	                               ps_non.setInt(3, CashbookMonth);
	                               ps_non.setInt(4, CashbookYear);
	                               ps_non.setInt(5, CashbookMonth);
	                               ps_non.setInt(6, CashbookYear);
	                               ps_non.setInt(7, CashbookMonth);
	                               ps_non.setInt(8, cmbAcc_UnitCode);*/
					}
					catch (Exception e) {
						// TODO: handle exception
					}
			}
			else
			{
				System.out.println("yearwise....");
				cmbAcc_UnitCode = Integer.parseInt(request
						.getParameter("unitid"));
				 CashbookYear = Integer.parseInt(request
						.getParameter("txtCB_Year"));
				 Date new_Date = null;
					Calendar c;
					String sdat="01/01/"+CashbookYear;
					String[] sd = (sdat).split("/");
					c = new GregorianCalendar(Integer.parseInt(sd[2]),
							Integer.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
					java.util.Date d = c.getTime();
					new_Date = new Date(d.getTime());
					System.out.println("new_Date >> " + new_Date);
					
					String s=new_Date.toString();
					String from[]=s.split("-");
					
					java.sql.Date dt1 = new java.sql.Date(System.currentTimeMillis());
					String dt1Text = dt1.toString();
					
					String to[]=dt1Text.split("-");
					System.out.println("from[1] to to[1]"+from[1] +"to"+ to[1]);
					
					
					System.out.println("Current Date1 : " + dt1Text);
			        int CashbookMonthnew=Integer.parseInt(to[1]);
			        
			        CashbookMonth = Integer.parseInt((from[1]));
			        
			        System.out.println("CashbookMonthnew..."+CashbookMonthnew+"CashbookMonth....."+CashbookMonth);
			        
			      //  Date today=new_Date();
			     //   Date today= new Date(); 
			        int sqlDate=0,sql_yr=0;
			        java.util.Date today=new java.util.Date();
			        // sqlDate=new Date(today.getMonth());
			        sqlDate=today.getMonth();
			        sql_yr=today.getYear();
			        if(sql_yr < 1900) sql_yr += 1900;
			       System.out.println("sqlDate >> "+sqlDate);
			       sqlDate=sqlDate+1;
			        String qry="",sub_qry="";
			        String sqlMonth="";
			        
			        qry="SELECT f.id, " +
			        		"  f.accounting_unit_id, f.CB_YEAR,f.cb_month," +
			        		"  u.accounting_unit_name, " +
			        		"  DECODE(f.AC_OPENING_DATE,NULL,'',TO_CHAR(f.AC_OPENING_DATE,'dd/mm/yyyy')) AS AC_OPENING_DATE, " +
			        		"  f.BANK_ID, " +
			        		"  f.BRANCH_ID, " +
			        		"  bank.bank_short_name " +
			        		"  ||'-' " +
			        		"  ||branch.branch_name AS bank_det, " +
			        		"  f.BANK_AC_NO, " +
			        		"  f.BANK_AC_TYPE_ID, " +
			        		"  ty.account_type, " +
			        		"  f.AC_OPERATIONAL_MODE_ID, " +
			        		"  mo.ac_operational_mode, " +
			        		"  f.initial_deposit_amt, " +
			        		"  f.flag as status" +
			        		" FROM (";System.out.println("sql_yr >  "+sql_yr+" CashbookYear "+CashbookYear);
			        		
			    	if(sql_yr>CashbookYear){
			    		sqlDate=12;
			    	}
			        for(int i=1;i<=sqlDate;i++){
			        	if(i==sqlDate){
			        		sub_qry="";
			        	}else{
			        		sub_qry= " union ";
			        	}
			        
			        	if (i>=1 && i<=9)
			        	sqlMonth="0"+i;
			        	else
			        		sqlMonth=""+i;
			     		        	
			        	qry+=" SELECT "+i+" AS id, " +
			        	"  A.accounting_unit_id, " +
			        	//"  --B.CB_MONTH , " +
			        	"  a.BANK_ID, " +
			        	"  a.BRANCH_ID, " +
			        	"  a.BANK_AC_TYPE_ID, " +
			        	"  a.AC_OPERATIONAL_MODE_ID, " +
			        	"  a.BANK_AC_NO, " +
			        //	"  --  a.initial_deposit_amt, " +
			        	"  a.ac_opening_date, " +
			        	"  NVL(B.CB_YEAR,"+CashbookYear+") AS CB_YEAR, " +
			        	"  NVL(B.cb_month,"+i+")   AS cb_month, " +
			        	"  CASE " +
			        	"    WHEN B.CB_MONTH      IS NULL " +
			        	"    AND ( A.ac_opening_date < to_date('01/"+sqlMonth+"/"+CashbookYear+"','dd-mm-yy')  or A.ac_opening_date is null ) " +
			        	"    THEN 'N' " +
			        	"    WHEN B.CB_MONTH      IS NULL " +
			        	"    AND A.ac_opening_date > to_date('01/"+sqlMonth+"/"+CashbookYear+"','dd-mm-yy') " +
			        	"    THEN 'Not-Appl' " +
			        	"    ELSE 'Y' " +
			        	"  END AS flag, " +
			        	"  A.initial_deposit_amt " +
			        	"FROM " +
			        	"  (SELECT Accounting_Unit_Id, " +
			        	"    BANK_ID, " +
			        	"    BRANCH_ID, " +
			        	"    BANK_AC_TYPE_ID, " +
			        	"    AC_OPERATIONAL_MODE_ID, " +
			        	"    BANK_AC_NO, " +
			        	"    AC_OPENING_DATE, " +
			        	"    initial_deposit_amt " +
			        	"  FROM fas_mst_bank_balance " +
			        	"  WHERE Accounting_Unit_Id=  " +cmbAcc_UnitCode+
			        	"  AND Status              ='Y' " +
			        	"  )a " +
			        	"LEFT OUTER JOIN " +
			        	"  (SELECT CB_YEAR, " +
			        	"    accounting_unit_id, " +
			        	"    CB_MONTH, " +
			        	"    PS_YEAR, " +
			        	"    PS_MONTH, " +
			        	"    BANK_AC_NO " +
			        	"  FROM brs_bank_balance_update " +
			        	"  WHERE ACCOUNTING_UNIT_ID =  " +cmbAcc_UnitCode+
			        	"  AND Cb_Year              =  " +CashbookYear+
			        	"  AND CB_MONTH             = " +i+
			        	"  )b " +
			        	"ON A.Accounting_Unit_Id=B.Accounting_Unit_Id " +
			        	"AND A.BANK_AC_NO       =B.BANK_AC_NO"+sub_qry;


			        }	
			        qry+=") f INNER JOIN FAS_MST_BANK_AC_TYPES ty ON ty.account_type_id=f.BANK_AC_TYPE_ID INNER JOIN FAS_MST_BANKS bank ON bank.bank_id=f.bank_id INNER JOIN FAS_MST_BANK_BRANCHES branch ON branch.bank_id =f.bank_id AND branch.branch_id=f.branch_id INNER JOIN FAS_MST_AC_OPER_MODES mo ON mo.ac_operational_mode_id=f.ac_operational_mode_id INNER JOIN fas_mst_acct_units u ON u.accounting_unit_id =f.accounting_unit_id and f.flag ='N' ORDER BY f.id,f.ACCOUNTING_UNIT_ID";
			        
			        System.out.println(" *****************       "+qry);
			        
			        try{
				 /* qry="SELECT f.ACCOUNTING_UNIT_ID, "
						+ "  u.accounting_unit_name, "
						+ "  decode(AC_OPENING_DATE,null,'',to_char(f.AC_OPENING_DATE,'dd/mm/yyyy')) as AC_OPENING_DATE, "
						+ "  f.tot_count, "
						+ "  f.cno, "
						+ "  f.BANK_ID, "
						+ "  f.BRANCH_ID, "
						+ "  bank.bank_short_name "
						+ "  ||'-' "
						+ "  ||branch.branch_name AS bank_det, "
						+ "  f.BANK_AC_NO, "
						+ "  f.BANK_AC_TYPE_ID, "
						+ "  ty.account_type, "
						+ "  f.AC_OPERATIONAL_MODE_ID, "
						+ "  mo.ac_operational_mode, "
						+ "  f.initial_deposit_amt, "
						+ "  CASE "
						+ "    WHEN cno<tot_count "
						+ "    THEN 'N' "
						+ "    ELSE 'Y' "
						+ "  END AS status "
						+ " FROM "
						+ "  (SELECT ACCOUNTING_UNIT_ID, "
						+ "    AC_OPENING_DATE, "
						+ "    CASE "
						+ "      WHEN AC_OPENING_DATE IS NOT NULL "
						+ "      AND AC_OPENING_DATE   >? "
						+ // New Date Value
						"      THEN ( MONTHS_BETWEEN ( TO_DATE(? "
						+ // New Year Value
						"        ||'/' "
						+ "        ||? "
						+ // New Month Value
						"        ||'/01','yyyy/mm/dd'), TO_DATE (TO_CHAR(AC_OPENING_DATE, 'yyyy') "
						+ "        ||'/' "
						+ "        ||TO_CHAR(AC_OPENING_DATE, 'mm') "
						+ "        ||'/01','yyyy/mm/dd'))) "
						+ "      ELSE ROUND( MONTHS_BETWEEN ( TO_DATE(? "
						+ // New year Value
						"        ||'/' "
						+ "        ||? "
						+ // New Month Value
						"        ||'/31','yyyy/mm/dd'), TO_DATE (2014 "
						+ "        ||'/' "
						+ "        ||01 "
						+ "        ||'/01','yyyy/mm/dd'))) "
						+ "    END AS tot_count, "
						+ "    (SELECT COUNT(*)AS brs_update "
						+ "    FROM brs_bank_balance_update u "
						+ "    WHERE CB_YEAR           = ? "
						+ // New year Value
						"    AND CB_MONTH   between ? and ? "
						+ // // New Month Value
						"    AND u.accounting_unit_id=b.accounting_unit_id "
						+ "    AND u.bank_ac_no        =b.bank_ac_no "
						+ "    ) AS cno, "
						+ "    b.BANK_ID, "
						+ "    b.BRANCH_ID, "
						+ "    b.BANK_AC_TYPE_ID, "
						+ "    b.AC_OPERATIONAL_MODE_ID, "
						+ "    b.BANK_AC_NO, "
						+ "    b.initial_deposit_amt "
						+ "  FROM FAS_MST_BANK_BALANCE b "
						+ "  WHERE STATUS ='Y' "
						+
						// "    --  and (AC_OPENING_DATE is null or " +
						// "    --  AC_OPENING_DATE<'01-aug-2014') " +
						"  GROUP BY ACCOUNTING_UNIT_ID, "
						+ "    AC_OPENING_DATE, "
						+ "    b.BANK_ID, "
						+ "    b.BRANCH_ID, "
						+ "    b.BANK_AC_TYPE_ID, "
						+ "    b.AC_OPERATIONAL_MODE_ID, "
						+ "    b.BANK_AC_NO, "
						+ "    b.initial_deposit_amt "
						+ "  )f "
						+ " INNER JOIN FAS_MST_BANK_AC_TYPES ty "
						+ " ON ty.account_type_id=f.BANK_AC_TYPE_ID "
						+ " INNER JOIN FAS_MST_BANKS bank "
						+ " ON bank.bank_id=f.bank_id "
						+ " INNER JOIN FAS_MST_BANK_BRANCHES branch "
						+ " ON branch.bank_id   =f.bank_id "
						+ " AND branch.branch_id=f.branch_id "
						+ " INNER JOIN FAS_MST_AC_OPER_MODES mo "
						+ " ON mo.ac_operational_mode_id=f.ac_operational_mode_id "
						+ " INNER JOIN fas_mst_acct_units u "
						+ " ON u.accounting_unit_id =f.accounting_unit_id "
						+ " AND f.ACCOUNTING_UNIT_ID=? " + // New Unit
															// Value
						" ORDER BY f.ACCOUNTING_UNIT_ID";*/
				 
				 System.out.println("qry....."+qry.toString());
					 ps_non = connection
							.prepareStatement(qry);
	                           /*    ps_non.setDate(1, new_Date);
	                               ps_non.setInt(2, CashbookYear);
	                               ps_non.setInt(3, CashbookMonth);
	                               ps_non.setInt(4, CashbookYear);
	                               ps_non.setInt(5, CashbookMonth);
	                               ps_non.setInt(6, CashbookYear);
	                               ps_non.setInt(7, CashbookMonth);
	                               ps_non.setInt(8, CashbookMonthnew);
	                               ps_non.setInt(9, cmbAcc_UnitCode);*/
	                               
	                              // from[1] to to[1]
			        }
			        catch (Exception e) {
						// TODO: handle exception
					}
				// select ACCOUNTING_UNIT_ID from brs_bank_balance_update where cb_month between '1' and '11'
				// and cb_year='2013' and accounting_unit_id=3
			}
			//System.out.println("option..."+option);
			
			
			
			
			
			try {
				
				
				ResultSet rs_non = ps_non.executeQuery();
				
				System.out.println("ps_non"+ps_non.toString());
		
				while (rs_non.next()) {
					
					  String monthInWords="";
				        if( rs_non.getInt("cb_month")==1)
				            monthInWords="January";
				        else if( rs_non.getInt("cb_month")==2)
				            monthInWords="February";
				        else if( rs_non.getInt("cb_month")==3)
				            monthInWords="March";
				        else if( rs_non.getInt("cb_month")==4)
				            monthInWords="April";
				        else if( rs_non.getInt("cb_month")==5)
				            monthInWords="May";
				        else if( rs_non.getInt("cb_month")==6)
				            monthInWords="June";
				        else if( rs_non.getInt("cb_month")==7)
				            monthInWords="July";
				        else if( rs_non.getInt("cb_month")==8)
				            monthInWords="August";
				        else if( rs_non.getInt("cb_month")==9)
				            monthInWords="September";
				        else if( rs_non.getInt("cb_month")==10)
				            monthInWords="October";
				        else if( rs_non.getInt("cb_month")==11)
				            monthInWords="November";
				        else if( rs_non.getInt("cb_month")==12)
				            monthInWords="December";
					xml = xml + "<ACCOUNTING_UNIT_ID>"
							+ rs_non.getInt("ACCOUNTING_UNIT_ID")
							+ "</ACCOUNTING_UNIT_ID>";
					xml = xml + "<accounting_unit_name>"
							+ rs_non.getString("accounting_unit_name")
							+ "</accounting_unit_name>";
					xml = xml + "<AC_OPENING_DATE>"
							+ rs_non.getString("AC_OPENING_DATE")
							+ "</AC_OPENING_DATE>";
					xml = xml + "<bank_det>" + rs_non.getString("bank_det")
							+ "</bank_det>";
					xml = xml + "<BANK_AC_NO>" + rs_non.getLong("BANK_AC_NO")
							+ "</BANK_AC_NO>";
					xml = xml + "<account_type>"
							+ rs_non.getString("BANK_AC_TYPE_ID")
							+ "</account_type>";
					xml = xml + "<ac_operational_mode>"
							+ rs_non.getString("ac_operational_mode")
							+ "</ac_operational_mode>";
					xml = xml + "<initial_deposit_amt>"
							+ rs_non.getBigDecimal("initial_deposit_amt")
							+ "</initial_deposit_amt>";
					xml = xml + "<status>" + rs_non.getString("status")
							+ "</status>";
					xml = xml + "<CB_YEAR>"
							+ rs_non.getBigDecimal("CB_YEAR")
							+ "</CB_YEAR>";
					xml = xml + "<cb_month>" + monthInWords
							+ "</cb_month>";
					xml = xml + "<ps_date>" + rs_non.getLong("ps_date")
							+ "</ps_date>";
					
					cno++;
				}
				if (cno > 0) {
					xml = xml + "<flag>success</flag>";
				} else {
					xml = xml + "<flag>NODTA</flag>";
				}
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}

		}
		else if(strCommand.equalsIgnoreCase("Add"))
        {
			
			String CashbookMonthWords="";
			String passsheetMonthWords="";
            xml="<response><command>Add</command>";
			int count = 0;
			
			//try{
				int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));	
				int comOffCode = Integer.parseInt(request.getParameter("comOffCode"));
				String Balupd = request.getParameter("Balupd").trim();
				int CashbookYear = Integer.parseInt(request.getParameter("CashbookYear"));	
				String CashbookMonth = request.getParameter("CashbookMonth");
				int passsheetYear=Integer.parseInt(request.getParameter("passsheetYear1"));
				String passsheetMonth = request.getParameter("passsheetMonth1");
				String PS_PrintDate=request.getParameter("PS_PrintDate"); 
				String Bank_AcNO = request.getParameter("Bank_AcNO");
				String Bank_Bal_PS=request.getParameter("Bank_Bal_PS");
				String Remarks = request.getParameter("Remarks");	
				
				int mn=Integer.parseInt(request.getParameter("CashbookMonth"));
				int passmn=Integer.parseInt(request.getParameter("passsheetMonth1"));
				Date ps_date=null;
                Calendar c;
                String[] sd=request.getParameter("PS_PrintDate").split("/");
                c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
                java.util.Date d=c.getTime();
                ps_date=new Date(d.getTime());
			/*System.out.println("Balupd   "+Balupd+"CashbookYear=="+CashbookYear+"CashbookMonth=="+CashbookMonth+"passsheetYear "+passsheetYear+"passsheetMonth "+passsheetMonth+"PS_PrintDate "+PS_PrintDate);
			System.out.println("Bank_AcNO  "+Bank_AcNO+"  Bank_Bal_PS  "+Bank_Bal_PS+"  Remarks  "+Remarks);*/
			String BANK_AC_NO="",BANK_AC_TYPE_ID="",AC_OPERATIONAL_MODE_ID="",BANK_ID="",BRANCH_ID="",Bank_name="";
			//int 
			try{
				ps1 = connection
				.prepareStatement("select (select f1.BANK_SHORT_NAME from FAS_MST_BANKS f1 where f2.BANK_ID=f1.BANK_ID)as BankName,BANK_ID,BRANCH_ID,BANK_AC_NO,BANK_AC_TYPE_ID,AC_OPERATIONAL_MODE_ID from FAS_MST_BANK_BALANCE f2 where f2.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and f2.STATUS='Y' and f2.BANK_AC_NO='"+Bank_AcNO+"'");
		results1 = ps1.executeQuery();
		while (results1.next()) {
			
			BANK_ID=results1.getString("BANK_ID");
			BRANCH_ID=results1.getString("BRANCH_ID");
			BANK_AC_NO=results1.getString("BANK_AC_NO");
			BANK_AC_TYPE_ID=results1.getString("BANK_AC_TYPE_ID");
			AC_OPERATIONAL_MODE_ID= results1.getString("AC_OPERATIONAL_MODE_ID");
					  
			//count++;
		
		}
			}catch(Exception ee){
				System.out.println("error  acct details"+ee);
			}
			try {
							int cc=0;
							ps2=connection.prepareStatement("select ACCOUNTING_FOR_OFFICE_ID from BRS_BANK_BALANCE_UPDATE where ACCOUNTING_UNIT_ID='"+cmbAcc_UnitCode+"' and CB_YEAR="+CashbookYear+" and CB_MONTH="+mn+" and BANK_AC_NO='"+Bank_AcNO+"'");
							ResultSet cc1=ps2.executeQuery();
							if(cc1.next()){
								xml=xml+"<flag>AlreadyExist</flag>";
							}
							else{
								//System.out.println("DHANA:::::");
								preparedStatement1 = connection.prepareStatement("insert into BRS_BANK_BALANCE_UPDATE(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CB_YEAR,CB_MONTH,PS_YEAR,PS_MONTH,UPDATED_BY_USERID,UPDATED_DATE,BANK_AC_NO,BANK_ID,BRANCH_ID,BANK_AC_TYPE_ID,AC_OPERATIONAL_MODE_ID,REMARKS,PS_DATE,PB_BALANCE,CR_DR_TYPE)values(?,?,?,?,?,?,?,?,?,?::numeric,?::numeric,?,?,?,?,?::numeric,?)");
								preparedStatement1.setInt(1, cmbAcc_UnitCode);
								preparedStatement1.setInt(2, comOffCode);
								preparedStatement1.setInt(3, CashbookYear);
							//	preparedStatement1.setString(4, CashbookMonthWords);
								preparedStatement1.setInt(4, mn);
								//System.out.println("mn:::"+mn);
								//System.out.println("passsheetYear:::"+passsheetYear);
								preparedStatement1.setInt(5, passsheetYear);
								preparedStatement1.setInt(6,passmn);
								//System.out.println("passmn:::"+passmn);
								preparedStatement1.setString(7, userid);
								preparedStatement1.setTimestamp(8, ts);
								preparedStatement1.setString(9, BANK_AC_NO);	
								preparedStatement1.setString(10, BANK_ID);
								preparedStatement1.setString(11, BRANCH_ID);
								preparedStatement1.setString(12, BANK_AC_TYPE_ID);
								preparedStatement1.setString(13, AC_OPERATIONAL_MODE_ID);
								preparedStatement1.setString(14, Remarks);
								preparedStatement1.setDate(15, ps_date);
								preparedStatement1.setString(16, Bank_Bal_PS);
								preparedStatement1.setString(17, Balupd);						
		    					count = preparedStatement1.executeUpdate();
								System.out.println("count:" + count);

		                    if(count>0) {
		                        xml=xml+"<flag>success</flag>"; 
		                    }
		                    else
		                    {
		                    	 xml=xml+"<flag>notinsert</flag>";
		                    }
									}
                    
                }
            catch(Exception e)
                {
                     System.out.println("exception in add is"+e);
                     xml=xml+"<flag>failure</flag>";
                }
           
        }
		else if(strCommand.equalsIgnoreCase("Update"))
         {
 			
             xml="<response><command>Update</command>";
 			int count = 0;
 			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));	
			int comOffCode = Integer.parseInt(request.getParameter("comOffCode"));
			String Balupd = request.getParameter("Balupd").trim();
			int CashbookYear = Integer.parseInt(request.getParameter("CashbookYear"));	
			System.out.println("CashbookYear:::"+CashbookYear);
			int CashbookMonth = Integer.parseInt(request.getParameter("CashbookMonth"));
			System.out.println("CashbookMonth:::"+CashbookMonth);
			int passsheetYear=Integer.parseInt(request.getParameter("passsheetYear1"));
			int passsheetMonth =Integer.parseInt(request.getParameter("passsheetMonth1"));
			String PS_PrintDate=request.getParameter("PS_PrintDate"); 
			String Bank_AcNO = request.getParameter("Bank_AcNO");
			System.out.println("Bank_AcNO::::"+Bank_AcNO);
			String Bank_Bal_PS=request.getParameter("Bank_Bal_PS");
			String Remarks = request.getParameter("Remarks");	
			String CashbookMonthWords="";
			String passsheetMonthWords="";
			Date ps_date=null;
            Calendar c;
            String[] sd=request.getParameter("PS_PrintDate").split("/");
            c=new GregorianCalendar(Integer.parseInt(sd[2]),Integer.parseInt(sd[1])-1,Integer.parseInt(sd[0]));
            java.util.Date d=c.getTime();
            ps_date=new Date(d.getTime());
            
        
 						try {						
 							preparedStatement1 = connection.prepareStatement("update BRS_BANK_BALANCE_UPDATE set UPDATED_BY_USERID=?,REMARKS=?,PS_DATE=?,PB_BALANCE=?,CR_DR_TYPE=?,PS_YEAR=?,PS_MONTH=?,UPDATED_DATE=clock_timestamp() WHERE ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? and PS_YEAR=? and PS_MONTH=? and BANK_AC_NO=? ");
 							preparedStatement1.setString(1, userid);
 							preparedStatement1.setString(2, "Updated By : "+userid+" - "+Remarks);
 							preparedStatement1.setDate(3, ps_date);
 							preparedStatement1.setObject(4, Bank_Bal_PS,java.sql.Types.NUMERIC);
 							preparedStatement1.setString(5, Balupd);
 							preparedStatement1.setInt(6, passsheetYear);
 							preparedStatement1.setInt(7, passsheetMonth);
 							preparedStatement1.setInt(8, cmbAcc_UnitCode);
 							preparedStatement1.setInt(9, comOffCode);
 							preparedStatement1.setInt(10, passsheetYear);
 							preparedStatement1.setInt(11, passsheetMonth);
 							preparedStatement1.setString(12, Bank_AcNO);
     					count = preparedStatement1.executeUpdate();
 						System.out.println("count:" + count);

                     if(count>0) {
                         xml=xml+"<flag>success</flag>"; 
                     }
                     else
                     {
                    	 xml=xml+"<flag>failure</flag>";
                     }
                     
                 }
             catch(Exception e)
                 {
                      System.out.println("exception in update is"+e);
                      xml=xml+"<flag>failure</flag>";
                 }
            
         }
		 else if(strCommand.equalsIgnoreCase("Delete1"))
         {
 			System.out.println("deleteRecord ");
             xml="<response><command>Delete</command>";
            

 			int count = 0;
 			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));	
			int comOffCode = Integer.parseInt(request.getParameter("comOffCode"));
			String Balupd = request.getParameter("Balupd").trim();
			int CashbookYear = Integer.parseInt(request.getParameter("CashbookYear"));	
			String CashbookMonth = request.getParameter("CashbookMonth");
			int passsheetYear=Integer.parseInt(request.getParameter("passsheetYear1"));
			String passsheetMonth = request.getParameter("passsheetMonth1");
			String PS_PrintDate=request.getParameter("PS_PrintDate"); 
			String Bank_AcNO = request.getParameter("Bank_AcNO");
			String Bank_Bal_PS=request.getParameter("Bank_Bal_PS");
			String Remarks = request.getParameter("Remarks");
//System.out.println("inside delete  block....");
 						try {						

 							preparedStatement1 = connection.prepareStatement("delete from  BRS_BANK_BALANCE_UPDATE where"
 									+ " ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? AND "
 									+ " CB_YEAR=? AND CB_MONTH=? AND PS_YEAR=? AND PS_MONTH=? AND BANK_AC_NO=? AND PB_BALANCE=? "
 									//+ "AND CR_DR_TYPE=?"
 									);
 							preparedStatement1.setInt(1, cmbAcc_UnitCode);
 							preparedStatement1.setInt(2, comOffCode);
 							preparedStatement1.setInt(3, CashbookYear);
 							preparedStatement1.setInt(4, Integer.parseInt(CashbookMonth));
 							preparedStatement1.setInt(5, passsheetYear);
 							preparedStatement1.setInt(6, Integer.parseInt(passsheetMonth));
 							preparedStatement1.setLong(7, Long.parseLong(Bank_AcNO));		
 							preparedStatement1.setFloat(8, Float.parseFloat(Bank_Bal_PS));
 							count = preparedStatement1.executeUpdate();
 						System.out.println("count:" + count);

                     if(count>0) {
                         xml=xml+"<flag>success</flag>"; 
                     }
                     
                 }
             catch(Exception e)
                 {
                      System.out.println("exception in delete is"+e);
                      xml=xml+"<flag>failure</flag>";
                 }
            
         }
		 else if (strCommand.equalsIgnoreCase("load_table")) {
				int count = 0,r_count=0;
				
				xml = "<response><command>gett</command>";
				int unitcode = Integer.parseInt(request.getParameter("unitcode"));	
				int year = Integer.parseInt(request.getParameter("year"));
				int month = Integer.parseInt(request.getParameter("month"));
				try{

					String hq1="select f3.accounting_unit_id,f3.bank_id,(select f1.bank_short_name from fas_mst_banks f1 where f3.bank_id=f1.bank_id)as bankname,f3.bank_ac_no,f3.bank_ac_type_id,f3.remarks,f3.pb_balance,f3.cr_dr_type,f3.cb_year,f3.CB_MONTH,f3.ps_month , f3.ps_date , f3.ps_year  FROM BRS_BANK_BALANCE_UPDATE f3 where f3.ACCOUNTING_UNIT_ID="+unitcode+" and f3.PS_YEAR="+year+" and f3.PS_MONTH="+month;
				System.out.println(hq1);
					ps1 = connection.prepareStatement(hq1);				
				rs2=ps1.executeQuery();
				while(rs2.next())
				{
					
					xml += "<acunitid>"+rs2.getString("ACCOUNTING_UNIT_ID")+"</acunitid>";
					xml += "<cb_year>"+rs2.getString("cb_year")+"</cb_year>";
					xml += "<CB_MONTH>"+rs2.getString("CB_MONTH")+"</CB_MONTH>";
					xml += "<ps_year>"+rs2.getString("ps_year")+"</ps_year>";
					xml += "<ps_month>"+rs2.getString("ps_month")+"</ps_month>";
					// extra added the ps_date in 2016-04-25 
					xml += "<ps_date>"+rs2.getString("ps_date")+"</ps_date>";
					xml += "<bank_name>"+rs2.getString("BankName")+"</bank_name>";
					xml += "<ac_type>"+rs2.getString("BANK_AC_TYPE_ID")+"</ac_type>";
					xml += "<ac_no>"+rs2.getString("BANK_AC_NO")+"</ac_no>";
					xml += "<bank_bal>"+rs2.getString("PB_BALANCE")+"</bank_bal>";
					xml += "<dtorcr>"+rs2.getString("CR_DR_TYPE")+"</dtorcr>";
					xml += "<remark>"+rs2.getString("REMARKS")+"</remark>";
					/*xml += "<budget_req>"+rs2.getInt("ADDL_BUDGET_REQ")+"</budget_req>";
					xml += "<reason>"+rs2.getString("REASON")+"</reason>";*/
					count++;
					
				}
				 if(count>0)
                 {
					 xml=xml+"<count>"+count+"</count>"; 
                     xml=xml+"<flag>success</flag>"; 
                 }
                 else
                 {
                     xml=xml+"<flag>nodata</flag>";    
                 }
				}
				catch (Exception e) {
					e.printStackTrace();
					xml = xml + "<flag>failure</flag>";
				}
				
			}
		 else if(strCommand.equalsIgnoreCase("retrieve")){//retrieve
			 int count=0;
				xml = "<response><command>retrieve</command>";
				String acunitid=request.getParameter("unitid");
				//unitid+"&accNo="+accNo+"&year="+year+"&mn="+mn
				//String acunitid
				String acno=request.getParameter("accNo");
				String year=request.getParameter("year");
				String mn=request.getParameter("mn");
				
				//System.out.println("unitid  "+acunitid);
				try {
					String ss="SELECT ACCOUNTING_UNIT_ID,(select f1.ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS f1 where f1.ACCOUNTING_UNIT_ID='"+acunitid+"')as actngName,ACCOUNTING_FOR_OFFICE_ID,PS_YEAR,PS_MONTH,BANK_AC_NO,BANK_ID,BRANCH_ID,BANK_AC_TYPE_ID,AC_OPERATIONAL_MODE_ID,REMARKS,PS_DATE,PB_BALANCE,CR_DR_TYPE FROM BRS_BANK_BALANCE_UPDATE where ACCOUNTING_UNIT_ID="+acunitid+" and BANK_AC_NO::numeric="+acno+" and CB_YEAR="+year+" and CB_MONTH="+mn;
					System.out.println(ss);
					ps = connection.prepareStatement(ss);
					
					results = ps.executeQuery();
					while (results.next()) {
						String bankid=results.getString("BANK_ID").trim();
					//	String cbmon=results.getString("CB_MONTH").trim();
						String psmon=results.getString("PS_MONTH").trim();
						String offna=results.getString("ACCOUNTING_FOR_OFFICE_ID").trim();
						String cbmonno="",psmonno="";
						xml=xml+"<acunitid>"+ results.getString("ACCOUNTING_UNIT_ID").trim()+"</acunitid>";
						xml=xml+"<acunitval>"+ results.getString("actngName").trim()+"("+results.getString("ACCOUNTING_UNIT_ID").trim()+")"+"</acunitval>";
						xml=xml+"<acoffice>"+results.getString("ACCOUNTING_FOR_OFFICE_ID").trim()+"</acoffice>";
					
						xml=xml+"<psyear>"+ results.getString("PS_YEAR").trim()+"</psyear>";
						xml=xml+"<psmon>"+results.getString("PS_MONTH").trim()+"</psmon>";
						String bankNameGet="select BANK_SHORT_NAME from FAS_MST_BANKS where BANK_ID='"+bankid+"'";
						String bann="";
						ps2=connection.prepareStatement(bankNameGet);
						rs1=ps2.executeQuery();
						while(rs1.next()){
							bann= rs1.getString("BANK_SHORT_NAME").trim();	
						}
						xml = xml + "<bank_Detail>"+bann+" - "+
						 /* results.getInt("BANK_ID")+" - "+
						  results.getInt("BRANCH_ID")+" - "+*/
						  results.getString("BANK_AC_NO").trim()+" - "+
						  results.getString("BANK_AC_TYPE_ID")+" - "+
						  results.getString("AC_OPERATIONAL_MODE_ID")+
						  //results.getString("BankName")
						"</bank_Detail>";
						 
				            
						  if(psmon.equals("January"))
				            {
							  psmonno="1";
				            }
				            else if(psmon.equals("February")){
				            	psmonno="2";
				            }else if(psmon.equals("March")){
				            	psmonno="3";
				            }
				            else if(psmon.equals("April")){
				            	psmonno="4";
				            }
				            else if(psmon.equals("May")){
				            	psmonno="5";
				            }
				            else if(psmon.equals("June")){
				            	psmonno="6";
				            }
				            else if(psmon.equals("July")){
				            	psmonno="7";
				            }
				            else if(psmon.equals("August")){
				            	psmonno="8";
				            }
				            else if(psmon.equals("September")){
				            	psmonno="9";
				            }
				            else if(psmon.equals("October")){
				            	psmonno="10";
				            }
				            else if(psmon.equals("November")){
				            	psmonno="11";
				            }
				            else if(psmon.equals("December")){
				            	psmonno="12";
				            }
				            
						
						xml=xml+"<psmonno>"+psmonno+"</psmonno>";
						xml=xml+"<bankid>"+results.getString("BANK_AC_NO").trim()+"</bankid>";
						xml=xml+"<remark>"+ results.getString("REMARKS").trim()+"</remark>";
						xml=xml+"<psdate>"+results.getDate("PS_DATE")+"</psdate>";
						xml=xml+"<psbal>"+results.getString("PB_BALANCE").trim()+"</psbal>";
						xml=xml+"<crdr>"+results.getString("CR_DR_TYPE").trim()+"</crdr>";
						count++;
						
					
					}
					//xml = xml + "<count>"+count+"</count>";
					if(count==0){
						xml = xml + "<flag>failure</flag>";
					}else{
						xml = xml + "<flag>success</flag>";
						System.out.println("count"+count);

						
					}
									
				} catch (Exception e) {
					xml = xml + "<flag>failure</flag>";
					e.printStackTrace();
				}
				
		 } else if(strCommand.equalsIgnoreCase("loadDetails")){//retrieve
			 int count=0;
				xml = "<response><command>loadDetails</command>";
				int cou = 0;
	 			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));	
				int comOffCode = Integer.parseInt(request.getParameter("comOffCode"));
				String Balupd = request.getParameter("Balupd").trim();
				int CashbookYear = Integer.parseInt(request.getParameter("CashbookYear"));	
				int CashbookMonth = Integer.parseInt(request.getParameter("CashbookMonth"));
				int passsheetYear=Integer.parseInt(request.getParameter("passsheetYear1"));
				int passsheetMonth = Integer.parseInt(request.getParameter("passsheetMonth1"));
				String PS_PrintDate=request.getParameter("PS_PrintDate"); 
				String Bank_AcNO = request.getParameter("Bank_AcNO");
				String Bank_Bal_PS=request.getParameter("Bank_Bal_PS");
				String Remarks = request.getParameter("Remarks");

				try {
					String ss="SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,PS_YEAR,PS_MONTH,BANK_AC_NO,BANK_ID,BRANCH_ID,BANK_AC_TYPE_ID,AC_OPERATIONAL_MODE_ID,REMARKS,PS_DATE,PB_BALANCE,CR_DR_TYPE FROM BRS_BANK_BALANCE_UPDATE where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+comOffCode+" and BANK_AC_NO='"+Bank_AcNO+"' and CB_YEAR="+CashbookYear+" and CB_MONTH="+CashbookMonth+" and PS_YEAR="+passsheetYear+" and PS_MONTH="+passsheetMonth;
					System.out.println(ss);
					ps = connection.prepareStatement(ss);
					results = ps.executeQuery();
					while (results.next()) {
						
						xml=xml+"<REMARKS>"+ results.getString("REMARKS").trim()+"</REMARKS>";
						//xml=xml+"<psmon>"+results.getString("PS_MONTH").trim()+"</psmon>";
						//xml=xml+"<bankid>"+results.getString("BANK_AC_NO").trim()+"</bankid>";
						//xml=xml+"<remark>"+ results.getString("REMARKS").trim()+"</remark>";
						xml=xml+"<psdate>"+results.getDate("PS_DATE")+"</psdate>";
						xml=xml+"<psbal>"+results.getString("PB_BALANCE").trim()+"</psbal>";
						xml=xml+"<crdr>"+results.getString("CR_DR_TYPE").trim()+"</crdr>";
						count++;
						
					
					}
					//xml = xml + "<count>"+count+"</count>";
					if(count==0){
						xml = xml + "<flag>failure</flag>";
					}else{
						xml = xml + "<flag>success</flag>";
						System.out.println("count"+count);

						
					}
									
				} catch (Exception e) {
					xml = xml + "<flag>failure</flag>";
					e.printStackTrace();
				}
				
		 }

		
		xml = xml+"</response>";
		out.write(xml);
		System.out.println(xml);


	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection connection = null;
		LoadDriver load = new LoadDriver();
		response.setContentType(CONTENT_TYPE);
		PrintWriter out = response.getWriter();
		connection = load.getConnection();
		PreparedStatement preparedStatement = null,psss=null;
		String strCommand = "";
		String xml = "";
		int RecordCount=0;
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
		if(strCommand.equalsIgnoreCase("add")){
		
		}
	}
	
}
