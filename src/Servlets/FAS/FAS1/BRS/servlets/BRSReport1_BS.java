package Servlets.FAS.FAS1.BRS.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.Calendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class BRSReport1_BS
 */
public class BRSReport1_BS extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
   
    public BRSReport1_BS() {
        super();
        
    }	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection con = null;
		 PreparedStatement ps=null,ps_l=null,pss1=null;
		 PreparedStatement pss2=null,pre_st=null;
		    ResultSet rs=null,rs_l=null;
		    ResultSet res2=null;
		    Calendar c;
		    String last_date_one="";
		    java.util.Date last_date_two=null;
	    String UnitName="",OfficeName="",smonth="",totalyear="";
	    
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
		 String update_user=(String)session.getAttribute("UserId");
	        long l=System.currentTimeMillis();
	        Timestamp ts=new Timestamp(l);
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
			// sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

		}
		String opr_node="",bankName="",branchName="";
		int cboAcc_UnitCode = Integer.parseInt(request
				.getParameter("cmbAcc_UnitCode"));
		int cboOffice_code = Integer.parseInt(request
				.getParameter("cmbOffice_code"));
		int cboCashBook_Year = Integer.parseInt(request
				.getParameter("cboCashBook_Year"));
		int cboCashBook_Month = Integer.parseInt(request
				.getParameter("cboCashBook_Month"));
		long cmbBankAccNo = Long.parseLong(request
				.getParameter("cmbBankAccNo"));
		String command=request.getParameter("command"); 

	    try {
	   
	    
	    ps=con.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
	    ps.setInt(1,cboAcc_UnitCode);
	    rs=ps.executeQuery();
	    if(rs.next())
	         UnitName=rs.getString("ACCOUNTING_UNIT_NAME");
	    
	   
	    }
	    catch (SQLException e) {
	        System.out.println("SQL Exception -->"+e);
	    }
	    try {
		    
		    
		    ps_l=con.prepareStatement("SELECT to_char(last_day(to_date(date1, 'dd-mm-yy')),'dd-mm-yyyy')ls_date "+
								"  FROM "+
		    		"   (SELECT DISTINCT ('01' "+
		    		" 		      ||'-' "+
		    		" 		      ||CASHBOOK_MONTH "+
		    		" 		      ||'-' "+
		    		" 		      ||CASHBOOK_YEAR)date1 "+
		    		" 		    FROM FAS_BRS_TRANSACTION "+
		    		" 		    WHERE CASHBOOK_YEAR   = "+cboCashBook_Year+
		    		" 		    AND CASHBOOK_MONTH    = "+cboCashBook_Month+
		    		" 		    AND account_no        = "+cmbBankAccNo+
		    		" 		    AND accounting_unit_id= "+cboAcc_UnitCode+" 		    )");
		    
		    rs_l=ps_l.executeQuery();
		    if(rs_l.next())
		    {
		      last_date_one =rs_l.getString("ls_date");
		    System.out.println("last_date_one::"+last_date_one);
		    String[] splto=last_date_one.split("-");
		  
		    if(splto[1].equals("01"))
		    {
		    	smonth="jan";
		    }
		    else if(splto[1].equals("02"))
		    {
		    	smonth="feb";
		    }else if(splto[1].equals("03"))
		    {
		    	smonth="mar";
		    }else if(splto[1].equals("04"))
		    {
		    	smonth="apr";
		    }else if(splto[1].equals("05"))
		    {
		    	smonth="may";
		    }else if(splto[1].equals("06"))
		    {
		    	smonth="jun";
		    }else if(splto[1].equals("07"))
		    {
		    	smonth="jul";
		    }else if(splto[1].equals("08"))
		    {
		    	smonth="aug";
		    }else if(splto[1].equals("09"))
		    {
		    	smonth="sep";
		    }else if(splto[1].equals("10"))
		    {
		    	smonth="oct";
		    }else if(splto[1].equals("11"))
		    {
		    	smonth="nov";
		    }else if(splto[1].equals("12"))
		    {
		    	smonth="dec";
		    }
		    totalyear=splto[0]+"-"+smonth+"-"+splto[2];
		    System.out.println("totalyear:::"+totalyear);
		   
		    }
		    
		    }
		    catch (SQLException e) {
		        System.out.println("SQL Exception -->"+e);
		    }
		String month = null;
		if(cboCashBook_Month == 1){
			month = "January";
		}else if(cboCashBook_Month == 2){
			month = "Febrary";
		}else if(cboCashBook_Month == 3){
			month = "March";
		}else if(cboCashBook_Month == 4){
			month = "April";
		}else if(cboCashBook_Month == 5){
			month = "May";
		}else if(cboCashBook_Month == 6){
			month = "June";
		}else if(cboCashBook_Month == 7){
			month = "July";
		}else if(cboCashBook_Month == 8){
			month = "August";
		}else if(cboCashBook_Month == 9){
			month = "September";
		}else if(cboCashBook_Month == 10){
			month = "October";
		}else if(cboCashBook_Month == 11){
			month = "November";
		}else if(cboCashBook_Month == 12){
			month = "December";
		}
	/*	String qry="SELECT rownum AS rowno1,\n" + 
        "  OFFICE_NAME,\n" + 
        "  OB_PART1,\n" + 
        "  cr,\n" + 
        "  total1,\n" + 
        "  dr,\n" + 
        "  total2,\n" + 
        "  amt_t2_A,\n" + 
        "  amt_2T_clos_bal,\n" + 
        "  BANK_NAME,\n" + 
        "  BRANCH_NAME,\n" + 
        "  t2_sl_no,\n" + 
        "  DECODE(amt_2t,NULL,0,amt_2t)AS amt_2t,\n" + 
        "  trans_desc,\n" + 
        "  total_t2\n" + 
        "FROM\n" + 
        "  (SELECT rownum AS rowno1,\n" + 
        "    OFFICE_NAME,\n" + 
        "    OB_PART1,\n" + 
        "    cr,\n" + 
        "    total1,\n" + 
        "    dr,\n" + 
        "    total2,\n" + 
        "    amt_t2_A,\n" + 
        "    amt_2T_clos_bal,\n" + 
        "    BANK_NAME,\n" + 
        "    BRANCH_NAME,\n" + 
        "    t2_sl_no,\n" + 
        "    amt_2t,\n" + 
        "    trans_desc,\n" + 
        "    (DECODE(total,NULL,0,total)+amt_t2_A) AS total_t2\n" + 
        "  FROM\n" + 
        "    (SELECT OFFICE_NAME,\n" + 
        "      acc_u_id_T,\n" + 
        "      acc_off_id_T,\n" + 
        "      csh_bk_yr_T,\n" + 
        "      csh_bk_mnth_T,\n" + 
        "      acc_no_T,\n" + 
        "      OB_PART1,\n" + 
        "      cr,\n" + 
        "      total1,\n" + 
        "      dr,\n" + 
        "      total2,\n" + 
        "      amt_t2_A,\n" + 
        "      amt_2T_clos_bal,\n" + 
        "      BANK_NAME,\n" + 
        "      BRANCH_NAME\n" + 
        "    FROM\n" + 
        "      (SELECT rownum AS rowno1,\n" + 
        "        OFFICE_NAME,\n" + 
        "        acc_u_id_T,\n" + 
        "        acc_off_id_T,\n" + 
        "        csh_bk_yr_T,\n" + 
        "        csh_bk_mnth_T,\n" + 
        "        acc_no_T,\n" + 
        "        DECODE(OB_PART1,NULL,0,OB_PART1) AS OB_PART1,\n" + 
        "        DECODE(cr,NULL,0,cr)             AS cr,\n" + 
        "        DECODE(total1,NULL,0,total1)     AS total1,\n" + 
        "        DECODE(dr,NULL,0,dr)             AS dr,\n" + 
        "        DECODE(total2,NULL,0,total2)     AS total2\n" + 
        "      FROM\n" + 
        "        (SELECT OFFICE_NAME,\n" + 
        "          acc_u_id_T,\n" + 
        "          acc_off_id_T,\n" + 
        "          csh_bk_yr_T,\n" + 
        "          csh_bk_mnth_T,\n" + 
        "          acc_no_T,\n" + 
        "          OB_PART1,\n" + 
        "          cr,\n" + 
        "          OB_PART1+cr AS total1,\n" + 
        "          dr,\n" + 
        "          ((OB_PART1+cr)-dr) AS total2\n" + 
        "        FROM\n" + 
        "          (SELECT acc_u_id,\n" + 
        "            acc_off_id,\n" + 
        "            csh_bk_yr,\n" + 
        "            csh_bk_mnth,\n" + 
        "            acc_no,\n" + 
        "            OB_PART1,\n" + 
        "            OFFICE_NAME\n" + 
        "          FROM\n" + 
        "            (SELECT ACCOUNTING_UNIT_ID AS acc_u_id,\n" + 
        "              ACCOUNTING_FOR_OFFICE_ID AS acc_off_id,\n" + 
        "              CASHBOOK_YEAR            AS csh_bk_yr,\n" + 
        "              CASHBOOK_MONTH           AS csh_bk_mnth,\n" + 
        "              ACCOUNT_NO               AS acc_no,\n" + 
        "              OB_PART1\n" + 
        "            FROM FAS_BRS_OB\n" +
        "            WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
        "            AND accounting_for_office_id=" +cboOffice_code+ 
        "            AND cashbook_month          = " +cboCashBook_Month+ 
        "            AND cashbook_year           =" +cboCashBook_Year+ 
        "            AND ACCOUNT_NO              = " +cmbBankAccNo+ 
        "            AND ob_type                 ='T'\n" + 
        "            )AUID1\n" + 
        "          LEFT OUTER JOIN\n" + 
        "            (SELECT OFFICE_ID,OFFICE_NAME FROM COM_MST_OFFICES\n" + 
        "            )AUID2\n" + 
        "          ON AUID1.acc_off_id = AUID2.OFFICE_ID\n" + 
        "          )X\n" + 
        "        LEFT OUTER JOIN\n" + 
        "          (SELECT acc_u_id_T,\n" + 
        "            acc_off_id_T,\n" + 
        "            csh_bk_yr_T,\n" + 
        "            csh_bk_mnth_T,\n" + 
        "            acc_no_T,\n" + 
        "            ibt AS cr,\n" + 
        "            dr_T1 AS dr\n" + 
        "          FROM\n" + 
        "            (\n" + 
        "            \n" + 
        "        SELECT ACCOUNTING_UNIT_ID as acc_u_id_T,ACCOUNTING_FOR_OFFICE_ID as acc_off_id_T,\n" + 
        "          CASHBOOK_MONTH as csh_bk_mnth_T,CASHBOOK_YEAR as csh_bk_yr_T,sum(TOTAL_AMOUNT)as ibt,\n" + 
        "          "+cmbBankAccNo+" as acc_no_T\n" + 
        "        FROM FAS_INTER_BANK_TRF_AT_HO\n" + 
        "        WHERE ACCOUNTING_UNIT_ID     = " +cboAcc_UnitCode+ 
        "        AND ACCOUNTING_FOR_OFFICE_ID = " +cboOffice_code+ 
        "        AND CASHBOOK_MONTH           = " +cboCashBook_Month+ 
        "        AND CASHBOOK_YEAR            = " +cboCashBook_Year+ 
       // "        AND (FROM_ACCOUNT_NO            = "+cmbBankAccNo+" or TO_ACCOUNT_NO="+cmbBankAccNo+")\n" + 
        "        AND (TO_ACCOUNT_NO="+cmbBankAccNo+")\n" +
        "        AND TRANSFER_STATUS          ='L'\n" + 
        "        group by ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_MONTH,CASHBOOK_YEAR\n" + 
        "            )a\n" + 
        "          LEFT OUTER JOIN\n" + 
        "            (\n" + 
        "\n" + 
        "            select sum(dr_T1)as dr_T1,acc_u_id_T1,acc_off_id_T1,csh_bk_yr_T1,csh_bk_mnth_T1,acc_no_T1 from\n" + 
        "(SELECT ACCOUNTING_UNIT_ID AS acc_u_id_T1,\n" + 
        "              ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T1,\n" + 
        "              CASHBOOK_YEAR            AS csh_bk_yr_T1,\n" + 
        "              CASHBOOK_MONTH           AS csh_bk_mnth_T1,\n" + 
        "              ACCOUNT_NO               AS acc_no_T1,\n" + 
        "              SUM(CR_AMOUNT)           AS dr_T1\n" + 
        "            FROM FAS_BRS_TRANSACTION\n" + 
        "            WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
        "            AND accounting_for_office_id=" +cboOffice_code+ 
        "            AND cashbook_month          =" +cboCashBook_Month+ 
        "            AND cashbook_year           =" +cboCashBook_Year+ 
        "            AND ACCOUNT_NO              =" +cmbBankAccNo+ 
        "            AND doc_type ='IBT'\n" + 
        "            GROUP BY ACCOUNTING_UNIT_ID,\n" + 
        "              ACCOUNTING_FOR_OFFICE_ID,\n" + 
        "              CASHBOOK_YEAR,\n" + 
        "              CASHBOOK_MONTH,\n" + 
        "              ACCOUNT_NO\n" + 
//        "              union all\n" + 
//        "              SELECT ACCOUNTING_UNIT_ID AS acc_u_id_T1,\n" + 
//        "              ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T1,\n" + 
//        "              CASHBOOK_YEAR            AS csh_bk_yr_T1,\n" + 
//        "              CASHBOOK_MONTH           AS csh_bk_mnth_T1,\n" + 
//        "              ACCOUNT_NO               AS acc_no_T1,\n" + 
//        "              SUM(DR_AMOUNT)           AS dr_T1\n" + 
//        "            FROM fas_brs_transaction_noentry\n" + 
//        "            WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
//        "            AND accounting_for_office_id=" +cboOffice_code+ 
//        "            AND cashbook_month          =" +cboCashBook_Month+ 
//        "            AND cashbook_year           =" +cboCashBook_Year+ 
//        "            AND ACCOUNT_NO              =" +cmbBankAccNo+ 
//        "            AND doc_type ='IBT'\n" + 
//        "            GROUP BY ACCOUNTING_UNIT_ID,\n" + 
//        "              ACCOUNTING_FOR_OFFICE_ID,\n" + 
//        "              CASHBOOK_YEAR,\n" + 
//        "              CASHBOOK_MONTH,\n" + 
       // "              ACCOUNT_NO" +
        ")group by acc_u_id_T1,acc_off_id_T1,csh_bk_yr_T1,csh_bk_mnth_T1,acc_no_T1\n" + 
        "		)c\n" + 
        "          ON a.acc_u_id_T     =c.acc_u_id_T1\n" + 
        "          AND a.acc_off_id_T  = c.acc_off_id_T1\n" + 
        "          AND a.csh_bk_yr_T   = c.csh_bk_yr_T1\n" + 
        "          AND a.csh_bk_mnth_T = c.csh_bk_mnth_T1\n" + 
        "          AND a.acc_no_T      = c.acc_no_T1\n" + 
        "          ) Y ON X.acc_u_id   = Y.acc_u_id_T\n" + 
        "        AND X.acc_off_id      = Y.acc_off_id_T\n" + 
        "        AND X.csh_bk_yr       = Y.csh_bk_yr_T\n" + 
        "        AND X.csh_bk_mnth     = Y.csh_bk_mnth_T\n" + 
        "        AND X.acc_no          = Y.acc_no_T\n" + 
        "        )\n" + 
        "      )XX\n" + 
        "    LEFT OUTER JOIN\n" + 
        "      (SELECT rownum                                   AS t2_slno,\n" + 
        "        DECODE(amt_t2_A,NULL,0,amt_t2_A)               AS amt_t2_A,\n" + 
        "        DECODE(amt_2T_clos_bal,NULL,0,amt_2T_clos_bal) AS amt_2T_clos_bal,\n" + 
        "        BANK_NAME,\n" + 
        "        BRANCH_NAME\n" + 
        "      FROM\n" + 
        "        (SELECT rownum AS r__2T_A,\n" + 
        "          amt_t2_A\n" + 
        "        FROM\n" + 
        "          (SELECT SUM(amt_t2_A) AS amt_t2_A\n" + 
        "          FROM\n" + 
        "            (\n" + 
        "           SELECT ( SUM(cr_amount)) AS amt_t2_A\n" + 
        "    FROM FAS_BRS_TRANSACTION_NOENTRY\n" + 
        "    WHERE accounting_unit_id               =" +cboAcc_UnitCode+ 
        "    AND accounting_for_office_id           =" +cboOffice_code+ 
        "   AND ((cashbook_year                     <"+cboCashBook_Year+" and cashbook_month<=12) or (cashbook_year="+cboCashBook_Year+" and cashbook_month<="+cboCashBook_Month+"))\n" + 
        "    AND ACCOUNT_NO                         = " +cmbBankAccNo+ 
        "    AND TWAD_OR_NON_TWAD                   ='T'\n" + 
//        "    UNION ALL\n" + 
//        "    SELECT ( SUM(cr_amount) - SUM(dr_amount) ) AS amt_t2_A\n" + 
//        "    FROM FAS_BRS_TRANSACTION\n" + 
//        "    WHERE accounting_unit_id                =" +cboAcc_UnitCode+ 
//        "    AND Accounting_For_Office_Id            =" +cboOffice_code+ 
//        "    and PASSBOOK_DATE>('"+totalyear+"')\n" + 
//        "    and (01||'-'||Cashbook_Month||'-'||cashbook_year)<=('"+totalyear+"')\n" + 
//        "    AND ((cashbook_year                     <"+cboCashBook_Year+" and cashbook_month<=12) or " +
//        "(cashbook_year="+cboCashBook_Year+" and cashbook_month<="+cboCashBook_Month+"))\n" + 
//        "    AND Account_No                          =" +cmbBankAccNo+ 
//        "    AND Twad_Or_Non_Twad                    ='T'\n" + 
        "    )\n" + 
        "          )\n" + 
        "        )T2_A\n" + 
        "      LEFT OUTER JOIN\n" + 
        "        (SELECT r__2T_C,\n" + 
        "          amt_2T_clos_bal,\n" + 
        "          BANK_ID,\n" + 
        "          BRANCH_ID,\n" + 
        "          BANK_NAME,\n" + 
        "          BRANCH_NAME\n" + 
        "        FROM\n" + 
        "          (SELECT rownum                                     AS r__2T_C,\n" + 
        "            DECODE(PASSBOOK_BALANCE,NULL,0,PASSBOOK_BALANCE) AS amt_2T_clos_bal,\n" + 
        "            BANK_ID,\n" + 
        "            BRANCH_ID\n" + 
        "          FROM FAS_BRS_MASTER\n" + 
        "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
        "          AND accounting_for_office_id=" +cboOffice_code+ 
        "          AND cashbook_month          = " +cboCashBook_Month+ 
        "          AND cashbook_year           =" +cboCashBook_Year+ 
        "          AND ACCOUNT_NO              = " +cmbBankAccNo+ 
        "          )sss\n" + 
        "        LEFT OUTER JOIN\n" + 
        "          (SELECT BRANCH_ID AS brnch_id,\n" + 
        "            BANK_ID         AS bnk_id,\n" + 
        "            BRANCH_NAME\n" + 
        "          FROM FAS_MST_BANK_BRANCHES\n" + 
        "          )c\n" + 
        "        ON sss.BANK_ID    = c.bnk_id\n" + 
        "        AND sss.BRANCH_ID = c.brnch_id\n" + 
        "        LEFT OUTER JOIN\n" + 
        "          (SELECT BANK_ID AS bnk_id1,BANK_NAME FROM FAS_BANK_LIST\n" + 
        "          )d\n" + 
        "        ON sss.BANK_ID        = d.bnk_id1\n" + 
        "        )T2_C ON T2_A.r__2T_A = T2_C.r__2T_C\n" + 
        "      ) YY ON XX.rowno1       = YY.t2_slno\n" + 
        "    ) AAA\n" + 
        "  LEFT OUTER JOIN\n" + 
        "    (SELECT *\n" + 
        "    FROM\n" + 
        "      (SELECT rownum AS t2_sl_no,\n" + 
        "        amt_2t,\n" + 
        "        trans_desc,"+cboCashBook_Year+" as cbyear,\n" + 
        "  "+cboCashBook_Month+" as cbmonth,\n" + 
        "        auid_2T_5A,\n" + 
        "        aoid_2T_5A,\n" + 
        "        ano_2T_5A\n" + 
        "      FROM\n" + 
        "      (select sum (amt_2t)as amt_2t,transaction_type,auid_2T_5A,aoid_2T_5A,ano_2T_5A from \n" + 
        "        (\n" + 
        "                  SELECT DECODE( (SUM(dr_amount) - SUM(cr_amount)),NULL,0,(SUM(dr_amount) -SUM(cr_amount)) ) AS amt_2t,\n" + 
        "            transaction_type,\n" + 
        "            accounting_unit_id       AS auid_2T_5A,\n" + 
        "            accounting_for_office_id AS aoid_2T_5A,\n" + 
        "            cashbook_month           AS cbm_2T_5A,\n" + 
        "            cashbook_year            AS cby_2T_5A,\n" + 
        "            ACCOUNT_NO               AS ano_2T_5A\n" + 
        "          FROM fas_brs_transaction\n" + 
        "          WHERE accounting_unit_id        = " +cboAcc_UnitCode+ 
        "          AND accounting_for_office_id    =" +cboOffice_code+ 
        "          AND ((cashbook_year             <" +cboCashBook_Year+ 
        "          AND cashbook_month             <=12)\n" + 
        "          OR (cashbook_year               =" +cboCashBook_Year+ 
        "          AND cashbook_month             <="+cboCashBook_Month+"))\n" + 
        "          AND ACCOUNT_NO                  = " +cmbBankAccNo+ 
        "          AND twad_or_non_twad            = 'NT' and TRANSACTION_TYPE=5\n" + 
        "          AND (CLEARED_BASED_ON_FOLLOWUP IS NULL\n" + 
        "          OR CLEARED_BASED_ON_FOLLOWUP    ='N')\n" + 
        "          GROUP BY transaction_type,\n" + 
        "            accounting_unit_id,\n" + 
        "            accounting_for_office_id,\n" + 
        "            cashbook_month,\n" + 
        "            cashbook_year,\n" + 
        "            ACCOUNT_NO\n" + 
        "            union all\n" + 
        "            SELECT DECODE( (SUM(dr_amount) - SUM(cr_amount)),NULL,0,(SUM(dr_amount) -SUM(cr_amount)) ) AS amt_2t,\n" + 
        "            transaction_type,\n" + 
        "            accounting_unit_id       AS auid_2T_5A,\n" + 
        "            accounting_for_office_id AS aoid_2T_5A,\n" + 
        "            cashbook_month           AS cbm_2T_5A,\n" + 
        "            cashbook_year            AS cby_2T_5A,\n" + 
        "            ACCOUNT_NO               AS ano_2T_5A\n" + 
        "          FROM fas_brs_transaction\n" + 
        "          WHERE accounting_unit_id        = " +cboAcc_UnitCode+ 
        "          AND accounting_for_office_id    =" +cboOffice_code+ 
        "          AND ((cashbook_year             <" +cboCashBook_Year+ 
        "          AND cashbook_month             <=12)\n" + 
        "          OR (cashbook_year               =" +cboCashBook_Year+ 
        "          AND cashbook_month             <="+cboCashBook_Month+"))\n" + 
        "          AND ACCOUNT_NO                  = " +cmbBankAccNo+ 
        "          AND twad_or_non_twad            = 'NT' and TRANSACTION_TYPE=5 \n" + 
        "          AND (CLEARED_BASED_ON_FOLLOWUP='Y' and clearence_date>('"+totalyear+"'))\n" + 
        "          \n" + 
        "          GROUP BY transaction_type,\n" + 
        "            accounting_unit_id,\n" + 
        "            accounting_for_office_id,\n" + 
        "            cashbook_month,\n" + 
        "            cashbook_year,\n" + 
        "            ACCOUNT_NO\n" + 
        "        )group by transaction_type,auid_2T_5A,aoid_2T_5A,ano_2T_5A)\n" + 
        "        t2_c\n" + 
        "      LEFT OUTER JOIN\n" + 
        "        ( SELECT trans_code,trans_desc FROM fas_brs_transaction_type\n" + 
        "        )t2_d\n" + 
        "      ON t2_c.transaction_type = t2_d.trans_code\n" + 
        "      )YYY,\n" + 
        "      (\n" + 
        "      SELECT SUM(amt_2t) AS total\n" + 
        "      FROM\n" + 
        "        (\n" + 
        "        SELECT rownum AS t2_sl_no,\n" + 
        "  amt_2t,\n" + 
        "  trans_desc,\n" + 
        "  auid_2T_5A,\n" + 
        "  aoid_2T_5A,\n" + 
        "  cby_2T_5A,\n" + 
        "  ano_2T_5A\n" + 
        "FROM\n" + 
        "  (\n" + 
        "        SELECT DECODE( (SUM(dr_amount) - SUM(cr_amount)),NULL,0,(SUM(dr_amount) -SUM(cr_amount)) ) AS amt_2t,\n" + 
        "        transaction_type,\n" + 
        "        accounting_unit_id       AS auid_2T_5A,\n" + 
        "        accounting_for_office_id AS aoid_2T_5A,\n" + 
        "        cashbook_year            AS cby_2T_5A,\n" + 
        "        ACCOUNT_NO               AS ano_2T_5A\n" + 
        "      FROM fas_brs_transaction\n" + 
        "      WHERE accounting_unit_id        = " +cboAcc_UnitCode+ 
        "      AND accounting_for_office_id    =" + cboOffice_code+
        "      AND ((cashbook_year             <" +cboCashBook_Year+ 
        "      AND cashbook_month             <=12)\n" + 
        "      OR (cashbook_year               =" +cboCashBook_Year+ 
        "      AND cashbook_month             <="+cboCashBook_Month+"))\n" + 
        "      AND ACCOUNT_NO                  = " +cmbBankAccNo+ 
        "      AND twad_or_non_twad            = 'NT'   " +
       // " and TRANSACTION_TYPE=5 \n" + 
        "      AND (CLEARED_BASED_ON_FOLLOWUP IS NULL\n" + 
        "      OR CLEARED_BASED_ON_FOLLOWUP    ='N')\n" + 
        "      GROUP BY transaction_type,\n" + 
        "        accounting_unit_id,\n" + 
        "        accounting_for_office_id,\n" + 
        "     \n" + 
        "        cashbook_year,\n" + 
        "        ACCOUNT_NO\n" + 
        "        union all\n" + 
        "        SELECT DECODE( (SUM(dr_amount) - SUM(cr_amount)),NULL,0,(SUM(dr_amount) -SUM(cr_amount)) ) AS amt_2t,\n" + 
        "        transaction_type,\n" + 
        "        accounting_unit_id       AS auid_2T_5A,\n" + 
        "        accounting_for_office_id AS aoid_2T_5A,\n" + 
        "        cashbook_year            AS cby_2T_5A,\n" + 
        "        ACCOUNT_NO               AS ano_2T_5A\n" + 
        "      FROM fas_brs_transaction\n" + 
        "      WHERE accounting_unit_id        = " +cboAcc_UnitCode+ 
        "      AND accounting_for_office_id    =" +cboOffice_code+ 
        "      AND ((cashbook_year             <" +cboCashBook_Year+ 
        "      AND cashbook_month             <=12)\n" + 
        "      OR (cashbook_year               =" +cboCashBook_Year+ 
        "      AND cashbook_month             <="+cboCashBook_Month+"))\n" + 
        "      AND ACCOUNT_NO                  = " +cmbBankAccNo+ 
        "      AND twad_or_non_twad            = 'NT'  " +
       // " and TRANSACTION_TYPE=5 \n" + 
        "     AND (CLEARED_BASED_ON_FOLLOWUP='Y' and clearence_date>('"+totalyear+"'))\n" + 
        "      \n" + 
        "      GROUP BY transaction_type,\n" + 
        "        accounting_unit_id,\n" + 
        "        accounting_for_office_id,\n" + 
        "        cashbook_year,\n" + 
        "        ACCOUNT_NO\n" + 
        "        )t2_c\n" + 
        "LEFT OUTER JOIN\n" + 
        "  ( SELECT trans_code,trans_desc FROM fas_brs_transaction_type\n" + 
        "  )t2_d\n" + 
        "ON t2_c.transaction_type = t2_d.trans_code\n" + 
        "        )\n" + 
        "      )XXX\n" + 
        "    )BBB ON AAA.acc_u_id_T = BBB.auid_2T_5A\n" + 
        "  AND AAA.acc_off_id_T     = BBB.aoid_2T_5A\n" + 
        "  AND AAA.csh_bk_yr_T      = BBB.cbyear\n" + 
        "  AND AAA.acc_no_T = BBB.ano_2T_5A\n" + 
        "  )\n" + 
        "WHERE amt_t2_A IS NOT NULL\n" + 
        "ORDER BY t2_sl_no";
	*/ ///joe change	
		
		String qry="SELECT rownum AS rowno1, " +
		"  OFFICE_NAME, " +
		"  OB_PART1, " +
		"  cr, " +
		"  total1, " +
		"  dr, " +
		"  total2, " +
		"  amt_t2_A, " +
		"  amt_2T_clos_bal, " +
		"  BANK_NAME, " +
		"  BRANCH_NAME, " +
		"  t2_sl_no, " +
		"  DECODE(amt_2t,NULL,0,amt_2t)AS amt_2t, " +
		"  trans_desc, " +
		"  total_t2 " +
		"FROM " +
		"  (SELECT rownum AS rowno1, " +
		"    OFFICE_NAME, " +
		"    OB_PART1, " +
		"    cr, " +
		"    total1, " +
		"    dr, " +
		"    total2, " +
		"    amt_t2_A, " +
		"    amt_2T_clos_bal, " +
		"    BANK_NAME, " +
		"    BRANCH_NAME, " +
		"    t2_sl_no, " +
		"    amt_2t, " +
		"    trans_desc, " +
		"    (DECODE(total,NULL,0,total)+amt_t2_A) AS total_t2 " +
		"  FROM " +
		"    (SELECT OFFICE_NAME, " +
		"      acc_u_id_T, " +
		"      acc_off_id_T, " +
		"      csh_bk_yr_T, " +
		"      csh_bk_mnth_T, " +
		"      acc_no_T, " +
		"      OB_PART1, " +
		"      cr, " +
		"      total1, " +
		"      dr, " +
		"      total2, " +
		"      amt_t2_A, " +
		"      amt_2T_clos_bal, " +
		"      BANK_NAME, " +
		"      BRANCH_NAME " +
		"    FROM " +
		"      (SELECT rownum AS rowno1, " +
		"        OFFICE_NAME, " +
		"        acc_u_id_T, " +
		"        acc_off_id_T, " +
		"        csh_bk_yr_T, " +
		"        csh_bk_mnth_T, " +
		"        acc_no_T, " +
		"        DECODE(OB_PART1,NULL,0,OB_PART1) AS OB_PART1, " +
		"        DECODE(cr,NULL,0,cr)             AS cr, " +
		"        DECODE(total1,NULL,0,total1)     AS total1, " +
		"        DECODE(dr,NULL,0,dr)             AS dr, " +
		"        DECODE(total2,NULL,0,total2)     AS total2 " +
		"      FROM " +
		"        (SELECT OFFICE_NAME, " +
		"          acc_u_id_T, " +
		"          acc_off_id_T, " +
		"          csh_bk_yr_T, " +
		"          csh_bk_mnth_T, " +
		"          acc_no_T, " +
		"          OB_PART1, " +
		"          cr, " +
		"          OB_PART1+cr AS total1, " +
		"          dr, " +
		"          ((OB_PART1+cr)-dr) AS total2 " +
		"        FROM " +
		"          (SELECT acc_u_id, " +
		"            acc_off_id, " +
		"            csh_bk_yr, " +
		"            csh_bk_mnth, " +
		"            acc_no, " +
		"            OB_PART1, " +
		"            OFFICE_NAME " +
		"          FROM " +
		"            (SELECT ACCOUNTING_UNIT_ID AS acc_u_id, " +
		"              ACCOUNTING_FOR_OFFICE_ID AS acc_off_id, " +
		"              CASHBOOK_YEAR            AS csh_bk_yr, " +
		"              CASHBOOK_MONTH           AS csh_bk_mnth, " +
		"              ACCOUNT_NO               AS acc_no, " +
		"              OB_PART1 " +
		"            FROM FAS_BRS_OB " +
		"            WHERE accounting_unit_id   = " +cboAcc_UnitCode+ 
		"            AND accounting_for_office_id =" +cboOffice_code+ 
		"            AND cashbook_month          = " +cboCashBook_Month+
		"            AND cashbook_year           = " +cboCashBook_Year+ 
		"            AND ACCOUNT_NO              = " +cmbBankAccNo+ 
		"            AND ob_type                 ='T' " +
		"            )AUID1 " +
		"          LEFT OUTER JOIN " +
		"            (SELECT OFFICE_ID,OFFICE_NAME FROM COM_MST_OFFICES " +
		"            )AUID2 " +
		"          ON AUID1.acc_off_id = AUID2.OFFICE_ID " +
		"          )X " +
		"        LEFT OUTER JOIN " +
		"          (SELECT acc_u_id_T1      AS acc_u_id_T, " +
		"            acc_off_id_T1          AS acc_off_id_T, " +
		"            csh_bk_yr_T1           AS csh_bk_yr_T, " +
		"            csh_bk_mnth_T1         AS csh_bk_mnth_T, " +
		"            acc_no_T1              AS acc_no_T, " +
		"            DECODE(ibt,NULL,0,ibt) AS cr, " +
		"            dr_T1                  AS dr " +
		"          FROM " +
		"            (SELECT ACCOUNTING_UNIT_ID AS acc_u_id_T, " +
		"              ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T, " +
		"              CASHBOOK_MONTH           AS csh_bk_mnth_T, " +
		"              CASHBOOK_YEAR            AS csh_bk_yr_T, " +
		"              SUM(TOTAL_AMOUNT)        AS ibt, " +
		"              950279626                AS acc_no_T " +
		"            FROM FAS_INTER_BANK_TRF_AT_HO " +
		"            WHERE ACCOUNTING_UNIT_ID    = " +cboAcc_UnitCode+ 
		"            AND ACCOUNTING_FOR_OFFICE_ID =  " +cboOffice_code+
		"            AND CASHBOOK_MONTH           =  " +cboCashBook_Month+
		"            AND CASHBOOK_YEAR            =  " +cboCashBook_Year+ 
		"            AND (TO_ACCOUNT_NO            = " +cmbBankAccNo+ ") " +
		"            AND TRANSFER_STATUS          ='L' " +
		"            GROUP BY ACCOUNTING_UNIT_ID, " +
		"              ACCOUNTING_FOR_OFFICE_ID, " +
		"              CASHBOOK_MONTH, " +
		"              CASHBOOK_YEAR " +
		"            )a " +
		"          RIGHT OUTER JOIN " +
		"            (SELECT SUM(DECODE(dr_T1,NULL,0,dr_T1))AS dr_T1, " +
		"              acc_u_id_T1, " +
		"              acc_off_id_T1, " +
		"              csh_bk_yr_T1, " +
		"              csh_bk_mnth_T1, " +
		"              acc_no_T1 " +
		"            FROM " +
		"              (SELECT ACCOUNTING_UNIT_ID AS acc_u_id_T1, " +
		"                ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T1, " +
		"                CASHBOOK_YEAR            AS csh_bk_yr_T1, " +
		"                CASHBOOK_MONTH           AS csh_bk_mnth_T1, " +
		"                ACCOUNT_NO               AS acc_no_T1, " +
		"                SUM(CR_AMOUNT)           AS dr_T1 " +
		"              FROM FAS_BRS_TRANSACTION " +
		"              WHERE accounting_unit_id   = " +cboAcc_UnitCode+ 
		"              AND accounting_for_office_id =" +cboOffice_code+ 
		"              AND cashbook_month          =" +cboCashBook_Month+
		"              AND cashbook_year           = " +cboCashBook_Year+ 
		"              AND ACCOUNT_NO              = " +cmbBankAccNo+ 
		"              AND doc_type                ='IBT' " +
		"              GROUP BY ACCOUNTING_UNIT_ID, " +
		"                ACCOUNTING_FOR_OFFICE_ID, " +
		"                CASHBOOK_YEAR, " +
		"                CASHBOOK_MONTH, " +
		"                ACCOUNT_NO " +
		"              ) " +
		"            GROUP BY acc_u_id_T1, " +
		"              acc_off_id_T1, " +
		"              csh_bk_yr_T1, " +
		"              csh_bk_mnth_T1, " +
		"              acc_no_T1 " +
		"            )c " +
		"          ON a.acc_u_id_T     =c.acc_u_id_T1 " +
		"          AND a.acc_off_id_T  = c.acc_off_id_T1 " +
		"          AND a.csh_bk_yr_T   = c.csh_bk_yr_T1 " +
		"          AND a.csh_bk_mnth_T = c.csh_bk_mnth_T1 " +
		"          AND a.acc_no_T      = c.acc_no_T1 " +
		"          ) Y ON X.acc_u_id   = Y.acc_u_id_T " +
		"        AND X.acc_off_id      = Y.acc_off_id_T " +
		"        AND X.csh_bk_yr       = Y.csh_bk_yr_T " +
		"        AND X.csh_bk_mnth     = Y.csh_bk_mnth_T " +
		"        AND X.acc_no          = Y.acc_no_T " +
		"        ) " +
		"      )XX " +
		"    LEFT OUTER JOIN " +
		"      (SELECT rownum                                   AS t2_slno, " +
		"        DECODE(amt_t2_A,NULL,0,amt_t2_A)               AS amt_t2_A, " +
		"        DECODE(amt_2T_clos_bal,NULL,0,amt_2T_clos_bal) AS amt_2T_clos_bal, " +
		"        BANK_NAME, " +
		"        BRANCH_NAME " +
		"      FROM " +
		"        (SELECT rownum AS r__2T_A, " +
		"          amt_t2_A " +
		"        FROM " +
		"          (SELECT SUM(amt_t2_A) AS amt_t2_A " +
		"          FROM " +
		"            (SELECT ( SUM(cr_amount)) AS amt_t2_A " +
		"            FROM FAS_BRS_TRANSACTION_NOENTRY " +
		"            WHERE accounting_unit_id   = " +cboAcc_UnitCode+ 
		"            AND accounting_for_office_id  =" +cboOffice_code+ 
		"            AND ((cashbook_year         <" +cboCashBook_Year+ 
		"            AND cashbook_month          <=12) " +
		"            OR (cashbook_year            = " +cboCashBook_Year+ 
		"            AND cashbook_month          <="+cboCashBook_Month+")) " +
		"            AND ACCOUNT_NO               = " +cmbBankAccNo+ 
		"            AND TWAD_OR_NON_TWAD         ='T' " +
		"            ) " +
		"          ) " +
		"        )T2_A " +
		"      LEFT OUTER JOIN " +
		"        (SELECT r__2T_C, " +
		"          amt_2T_clos_bal, " +
		"          BANK_ID, " +
		"          BRANCH_ID, " +
		"          BANK_NAME, " +
		"          BRANCH_NAME " +
		"        FROM " +
		"          (SELECT rownum                                     AS r__2T_C, " +
		"            DECODE(PASSBOOK_BALANCE,NULL,0,PASSBOOK_BALANCE) AS amt_2T_clos_bal, " +
		"            BANK_ID, " +
		"            BRANCH_ID " +
		"          FROM FAS_BRS_MASTER " +
		"          WHERE accounting_unit_id   = " +cboAcc_UnitCode+ 
		"          AND accounting_for_office_id =" +cboOffice_code+ 
		"          AND cashbook_month          =  " +cboCashBook_Month+
		"          AND cashbook_year           = " +cboCashBook_Year+ 
		"          AND ACCOUNT_NO              = " +cmbBankAccNo+ 
		"          )sss " +
		"        LEFT OUTER JOIN " +
		"          (SELECT BRANCH_ID AS brnch_id, " +
		"            BANK_ID         AS bnk_id, " +
		"            BRANCH_NAME " +
		"          FROM FAS_MST_BANK_BRANCHES " +
		"          )c " +
		"        ON sss.BANK_ID    = c.bnk_id " +
		"        AND sss.BRANCH_ID = c.brnch_id " +
		"        LEFT OUTER JOIN " +
		"          (SELECT BANK_ID AS bnk_id1,BANK_NAME FROM FAS_BANK_LIST " +
		"          )d " +
		"        ON sss.BANK_ID        = d.bnk_id1 " +
		"        )T2_C ON T2_A.r__2T_A = T2_C.r__2T_C " +
		"      ) YY ON XX.rowno1       = YY.t2_slno " +
		"    ) AAA " +
		"  LEFT OUTER JOIN " +
		"    (SELECT * " +
		"    FROM " +
		"      (SELECT rownum AS t2_sl_no, " +
		"        amt_2t, " +
		"        trans_desc, " +
		"        2013 AS cbyear, " +
		"        4   AS cbmonth, " +
		"        auid_2T_5A, " +
		"        aoid_2T_5A, " +
		"        ano_2T_5A " +
		"      FROM " +
		"        (SELECT SUM (amt_2t)AS amt_2t, " +
		"          transaction_type, " +
		"          auid_2T_5A, " +
		"          aoid_2T_5A, " +
		"          ano_2T_5A " +
		"        FROM " +
		"          (SELECT DECODE( (SUM(dr_amount) - SUM(cr_amount)),NULL,0,(SUM(dr_amount) -SUM(cr_amount)) ) AS amt_2t, " +
		"            transaction_type, " +
		"            accounting_unit_id       AS auid_2T_5A, " +
		"            accounting_for_office_id AS aoid_2T_5A, " +
		"            cashbook_month           AS cbm_2T_5A, " +
		"            cashbook_year            AS cby_2T_5A, " +
		"            ACCOUNT_NO               AS ano_2T_5A " +
		"          FROM fas_brs_transaction " +
		"          WHERE accounting_unit_id        = " +cboAcc_UnitCode+ 
		"          AND accounting_for_office_id     =" +cboOffice_code+ 
		"          AND ((cashbook_year             <" +cboCashBook_Year+ 
		"          AND cashbook_month             <=12) " +
		"          OR (cashbook_year               = " +cboCashBook_Year+ 
		"          AND cashbook_month             <="+cboCashBook_Month+"))\n" + 
		"          AND ACCOUNT_NO                   = " +cmbBankAccNo+ 
		"          AND twad_or_non_twad            = 'NT' " +
	//	"          AND TRANSACTION_TYPE            =5 " +
		"          AND TRANSACTION_TYPE            =31 " +
		"          AND (CLEARED_BASED_ON_FOLLOWUP IS NULL " +
		"          OR CLEARED_BASED_ON_FOLLOWUP    ='N') " +
		"          GROUP BY transaction_type, " +
		"            accounting_unit_id, " +
		"            accounting_for_office_id, " +
		"            cashbook_month, " +
		"            cashbook_year, " +
		"            ACCOUNT_NO " +
		"          UNION ALL " +
		"          SELECT DECODE( (SUM(dr_amount) - SUM(cr_amount)),NULL,0,(SUM(dr_amount) -SUM(cr_amount)) ) AS amt_2t, " +
		"            transaction_type, " +
		"            accounting_unit_id       AS auid_2T_5A, " +
		"            accounting_for_office_id AS aoid_2T_5A, " +
		"            cashbook_month           AS cbm_2T_5A, " +
		"            cashbook_year            AS cby_2T_5A, " +
		"            ACCOUNT_NO               AS ano_2T_5A " +
		"          FROM fas_brs_transaction " +
		"          WHERE accounting_unit_id     = " +cboAcc_UnitCode+ 
		"          AND accounting_for_office_id =" +cboOffice_code+ 
		"          AND ((cashbook_year         <" +cboCashBook_Year+ 
		"          AND cashbook_month           <=12) " +
		"          OR (cashbook_year             = " +cboCashBook_Year+ 
		"          AND cashbook_month           <="+cboCashBook_Month+"))\n" + 
		"          AND ACCOUNT_NO                = " +cmbBankAccNo+ 
		"          AND twad_or_non_twad          = 'NT' " +
	//	"          AND TRANSACTION_TYPE          =5 " +
		"          AND TRANSACTION_TYPE            =31 " +
		"          AND (CLEARED_BASED_ON_FOLLOWUP='Y' " +
		"          AND clearence_date            >('30-apr-2013')) " +
		"          GROUP BY transaction_type, " +
		"            accounting_unit_id, " +
		"            accounting_for_office_id, " +
		"            cashbook_month, " +
		"            cashbook_year, " +
		"            ACCOUNT_NO " +
		"          ) " +
		"        GROUP BY transaction_type, " +
		"          auid_2T_5A, " +
		"          aoid_2T_5A, " +
		"          ano_2T_5A " +
		"        ) t2_c " +
		"      LEFT OUTER JOIN " +
		"        ( SELECT trans_code,trans_desc FROM fas_brs_transaction_type " +
		"        )t2_d " +
		"      ON t2_c.transaction_type = t2_d.trans_code " +
		"      )YYY, " +
		"      (SELECT SUM(amt_2t) AS total " +
		"      FROM " +
		"        (SELECT rownum AS t2_sl_no, " +
		"          amt_2t, " +
		"          trans_desc, " +
		"          auid_2T_5A, " +
		"          aoid_2T_5A, " +
		"          cby_2T_5A, " +
		"          ano_2T_5A " +
		"        FROM " +
		"          (SELECT DECODE( (SUM(dr_amount) - SUM(cr_amount)),NULL,0,(SUM(dr_amount) -SUM(cr_amount)) ) AS amt_2t, " +
		"            transaction_type, " +
		"            accounting_unit_id       AS auid_2T_5A, " +
		"            accounting_for_office_id AS aoid_2T_5A, " +
		"            cashbook_year            AS cby_2T_5A, " +
		"            ACCOUNT_NO               AS ano_2T_5A " +
		"          FROM fas_brs_transaction " +
		"          WHERE accounting_unit_id      = " +cboAcc_UnitCode+ 
		"          AND accounting_for_office_id    =" +cboOffice_code+ 
		"          AND ((cashbook_year             <" +cboCashBook_Year+ 
		"          AND cashbook_month             <=12) " +
		"          OR (cashbook_year               = " +cboCashBook_Year+ 
		"          AND cashbook_month            <="+cboCashBook_Month+"))\n" + 
	//	"          AND TRANSACTION_TYPE            =5 " +
		"          AND TRANSACTION_TYPE            =31 " +
		"          AND ACCOUNT_NO                   = " +cmbBankAccNo+ 
		"          AND twad_or_non_twad            = 'NT' " +
		"          AND (CLEARED_BASED_ON_FOLLOWUP IS NULL " +
		"          OR CLEARED_BASED_ON_FOLLOWUP    ='N') " +
		"          GROUP BY transaction_type, " +
		"            accounting_unit_id, " +
		"            accounting_for_office_id, " +
		"            cashbook_year, " +
		"            ACCOUNT_NO " +
		"          UNION ALL " +
		"          SELECT DECODE( (SUM(dr_amount) - SUM(cr_amount)),NULL,0,(SUM(dr_amount) -SUM(cr_amount)) ) AS amt_2t, " +
		"            transaction_type, " +
		"            accounting_unit_id       AS auid_2T_5A, " +
		"            accounting_for_office_id AS aoid_2T_5A, " +
		"            cashbook_year            AS cby_2T_5A, " +
		"            ACCOUNT_NO               AS ano_2T_5A " +
		"          FROM fas_brs_transaction " +
		"          WHERE accounting_unit_id     = " +cboAcc_UnitCode+ 
		"          AND accounting_for_office_id   =" +cboOffice_code+ 
		"          AND ((cashbook_year           <" +cboCashBook_Year+ 
		"          AND cashbook_month           <=12) " +
		"          OR (cashbook_year             = " +cboCashBook_Year+ 
		"          AND cashbook_month          <="+cboCashBook_Month+"))\n" + 
		"          AND ACCOUNT_NO                 = " +cmbBankAccNo+ 
		"          AND twad_or_non_twad          = 'NT' " +
	//	"          AND TRANSACTION_TYPE          =5 " +
		"          AND TRANSACTION_TYPE            =31 " +
		"          AND (CLEARED_BASED_ON_FOLLOWUP='Y' " +
		"          AND clearence_date            >('30-apr-2013')) " +
		"          GROUP BY transaction_type, " +
		"            accounting_unit_id, " +
		"            accounting_for_office_id, " +
		"            cashbook_year, " +
		"            ACCOUNT_NO " +
		"          )t2_c " +
		"        LEFT OUTER JOIN " +
		"          ( SELECT trans_code,trans_desc FROM fas_brs_transaction_type " +
		"          )t2_d " +
		"        ON t2_c.transaction_type = t2_d.trans_code " +
		"        ) " +
		"      )XXX " +
		"    )BBB ON AAA.acc_u_id_T = BBB.auid_2T_5A " +
		"  AND AAA.acc_off_id_T     = BBB.aoid_2T_5A " +
		"  AND AAA.csh_bk_yr_T      = BBB.cbyear " +
		"  AND AAA.acc_no_T         = BBB.ano_2T_5A " +
		"  ) " +
		" WHERE amt_t2_A IS NOT NULL " +
		" ORDER BY t2_sl_no";
		
		
		System.out.println(">>>> "+qry);
		
		try{
			PreparedStatement ps_node=con.prepareStatement(" SELECT BB.AC_OPERATIONAL_MODE_ID,B.BANK_NAME,BR.BRANCH_NAME FROM FAS_MST_BANK_BALANCE BB,FAS_MST_BANKS B,FAS_MST_BANK_BRANCHES BR WHERE BB.BANK_ID=B.BANK_ID AND B.BANK_ID=BR.BANK_ID AND BB.BRANCH_ID=BR.BRANCH_ID and bank_ac_no= "+cmbBankAccNo)	;
			
			ResultSet rs_node=ps_node.executeQuery();
			while(rs_node.next()){
				opr_node=rs_node.getString("AC_OPERATIONAL_MODE_ID");
				bankName=rs_node.getString("BANK_NAME");
				branchName=rs_node.getString("BRANCH_NAME");
			}
			}catch(Exception e){
				e.printStackTrace();
			}	
		
		
		
		
		if(command.equalsIgnoreCase("printFunc")){
		try {
			File reportFile = null;
			
			Map map = null;
			map = new HashMap();
			map.put("opr_node", opr_node);
			map.put("bankName", bankName);
			map.put("branchName", branchName);			
			
		/*
		 * 
		 * 
		 * 	Joan changed on 09 sec 2015 for vasanthi mam request for only view the freezed report for banking section
		 */
				//reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/banking_section/BRS_Report_1_bs.jasper"));
		qry="SELECT p1.ACCOUNTING_UNIT_ID, " +
				"  unit.ACCOUNTING_UNIT_NAME, " +
				"  office.OFFICE_NAME, " +
				"  ACCOUNTING_FOR_OFFICE_ID, " +
				"  PASS_SHEET_YEAR, " +
				"  PASS_SHEET_MONTH, " +
				"  TWAD_OR_NON_TWAD, " +
				"  ACCOUNT_NO, " +
				"  p1.BANK_ID, " +
				"  (SELECT BANK_NAME FROM FAS_BANK_LIST Bname WHERE p1.BANK_ID=Bname.BANK_ID " +
				"  )AS BANK_NAME, " +
				"  (SELECT Branch_Name " +
				"  FROM Fas_Mst_Bank_Branches Branch " +
				"  WHERE P1.Bank_Id=Branch.Bank_Id " +
				"  AND P1.Branch_Id=Branch.Branch_Id " +
				"  )   AS Branch_Name, " +
				"  S1  AS Ob_Part1, " +
				"  S2  AS Cr, " +
				"  S3  AS Total1, " +
				"  S4  AS Dr, " +
				"  S5  AS Total2, " +
				"  S5a AS Amt_T2_A, " +
				"  S5c AS Amt_2t, " +
				"  S6  AS total_t2 " +
				" FROM FAS_BRS_PART1 p1, " +
				"  FAS_MST_ACCT_UNITS unit, " +
				"  COM_MST_OFFICES office " +
				" WHERE p1.accounting_unit_id     = " +cboAcc_UnitCode+ 
		        " AND p1.accounting_for_office_id   =" +cboOffice_code+ 
				" AND PASS_SHEET_YEAR            = " +cboCashBook_Year+ 
				" AND PASS_SHEET_MONTH           = " +cboCashBook_Month+ 
				" AND ACCOUNT_NO                 = " +cmbBankAccNo+ 
				" AND p1.ACCOUNTING_UNIT_ID      =unit.ACCOUNTING_UNIT_ID " +
				" AND P1.Accounting_For_Office_Id=Office.Office_Id"
;
			
			reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/banking_section/BRS_Report_1_bsfzd.jasper"));	
			
			map.put("sql", qry);
			
			if (!reportFile.exists())
				throw new JRRuntimeException(
						"File J not found. The report design must be compiled first.");

			JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
			map.put("accNo", cmbBankAccNo);
			 map.put("UnitName", UnitName);
			 map.put("cbyear", cboCashBook_Year);
			 map.put("month", month);
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);
		
			String rtype = "PDF";
			System.out.println(rtype);
			 if (rtype.equalsIgnoreCase("PDF")) {
				System.out.println(rtype);
				byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
				System.out.println("Length  " + buf.length);
				response.setContentType("application/pdf");
				response.setContentLength(buf.length);
				response.setHeader("Content-Disposition",
						"attachment;filename=\"BRS_Part1_BS.pdf\"");
				OutputStream out = response.getOutputStream();
				out.write(buf, 0, buf.length);
				out.close();
			}
			
		} catch (Exception ex) {
			String connectMsg = "Could not create the report "
					+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
			System.out.println(connectMsg);
		}
		}
		else if(command.equalsIgnoreCase("f_brs")){

			int insertCount=0;
			int bankid=0,branchid=0;
			double s5c=0.00;
			int unitcode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int offCode=Integer.parseInt(request.getParameter("cmbOffice_code"));
			int passYear=Integer.parseInt(request.getParameter("cboCashBook_Year"));
			int passMonth=Integer.parseInt(request.getParameter("cboCashBook_Month"));
			long accNo=Long.parseLong(request.getParameter("cmbBankAccNo"));
			
			 try
			 {
				 pss2=con.prepareStatement("SELECT BANK_ID,BRANCH_ID FROM FAS_MST_BANK_BALANCE " +
				 		"WHERE ACCOUNTING_UNIT_ID="+unitcode+" AND BANK_AC_NO          ="+accNo);
				 res2=pss2.executeQuery();
				 if(res2.next())
				 {
					bankid=res2.getInt("BANK_ID");
					branchid=res2.getInt("BRANCH_ID");
				 }
			 }
			 catch(Exception ee)
			 {
				 System.out.println(ee);
			 }
			 //sum of amt_2t
			 try
			 {
				 PreparedStatement ps_s=con.prepareStatement(qry);
	               ResultSet rs_s=ps_s.executeQuery(); 
	               while(rs_s.next())
	               {
	            	   s5c+=rs_s.getDouble("amt_2t");
	               }
			 }
			 catch(Exception e2)
			 {
				 System.out.println(e2);
			 }
			 System.out.println("s5c::::"+s5c);
                try{
               PreparedStatement pss=con.prepareStatement(qry);
               ResultSet rss=pss.executeQuery();
	               if(rss.next())
	               {
	            	   
	            	   pre_st=con.prepareStatement("delete from FAS_BRS_PART1 where ACCOUNTING_UNIT_ID="+unitcode+" and " +
	            	   		"ACCOUNTING_FOR_OFFICE_ID="+offCode+" and PASS_SHEET_YEAR="+passYear+" and PASS_SHEET_MONTH="+passMonth+" and ACCOUNT_NO="+accNo);
	            	   int clr=pre_st.executeUpdate();
	            	   System.out.println("insert starts"+clr);
	            	   insertCount++;
	            	   
	            	   pss1=con.prepareStatement("insert into FAS_BRS_PART1 (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," +
	            	   		"PASS_SHEET_YEAR,PASS_SHEET_MONTH,ACCOUNT_NO,S1,S2,S3,S4,S5,S5A,S5C,S6," +
	            	   		"UPDATED_BY_USER_ID,UPDATED_DATE,BANK_ID,BRANCH_ID) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
	            	   pss1.setInt(1,unitcode);
	            	   pss1.setInt(2,offCode);
	            	   pss1.setInt(3,passYear);
	            	   pss1.setInt(4,passMonth);
	            	   pss1.setLong(5,accNo);
	            	   pss1.setDouble(6,rss.getDouble("OB_PART1"));
	            	   pss1.setDouble(7,rss.getDouble("cr"));
	            	   pss1.setDouble(8,rss.getDouble("total1"));
	            	   pss1.setDouble(9,rss.getDouble("dr"));
	            	   pss1.setDouble(10,rss.getDouble("total2"));//s5
	            	   
	            	   pss1.setDouble(11,rss.getDouble("amt_t2_A"));
	            	//   pss1.setDouble(12,rss.getDouble("amt_2T_clos_bal"));
	            	   pss1.setDouble(12,s5c);
	            	   pss1.setDouble(13,rss.getDouble("total_t2"));
	            	   pss1.setString(14,update_user);
	                   pss1.setTimestamp(15,ts);
	                   pss1.setInt(16,bankid);
	            	   pss1.setInt(17,branchid);
	                   int jk=pss1.executeUpdate();
	                   System.out.println("value jk:::"+jk);
	            	
	            	   
	               }
	               if(insertCount>0)
	               {
	            	   con.commit();
						con.setAutoCommit(true);
						sendMessage(response,"Records Inserted Successfully  ","ok");
	               }
	               else
	               {
	            	   con.rollback();
						con.setAutoCommit(true);
	            	   sendMessage(response,"Records Not Inserted into Part-1 ","ok");   
	               }
	            
	               
                }
                catch(Exception ee)
                {
                	
                	System.out.println("exception in insertion::::"+ee);
                	
					sendMessage(response,"Records Not Inserted ............ "+ ee, "ok");
                }
              
		
		}
	
	}
	private void sendMessage(HttpServletResponse response, String msg,
			String bType) {
		try {
			String url = "org/Library/jsps/MessengerOkBack.jsp?message=" + msg
					+ "&button=" + bType;
			response.sendRedirect(url);
		} catch (IOException e) {
			e.printStackTrace();
		}
	}

}
