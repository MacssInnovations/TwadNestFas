package Servlets.FAS.FAS1.BRS.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
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
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
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
 * Servlet implementation class General_Sanction_Proceedings_Print
 */
public class BRSReport1 extends HttpServlet {
	private String CONTENT_TYPE = "text/xml; charset=windows-1252";
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		
		
		
		
		
		String xml=null;
		Connection con = null;
		 PreparedStatement ps=null,ps_l=null;
		    ResultSet rs=null,rs_l=null;
		    Calendar c;
		    String last_date_one="",bankName="",branchName="";
		    java.util.Date last_date_two=null;
		    int jk=0;
	    String UnitName="",OfficeName="",smonth="",totalyear="";
	    
		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");
				return;
			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
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
		HttpSession session=request.getSession(false);
        try
        {
            
            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp");
                response.sendRedirect(request.getContextPath()+"/index.jsp");
                return;
            }
            System.out.println(session);
                
        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        } 
		 String update_user=(String)session.getAttribute("UserId");
         long l=System.currentTimeMillis();
         Timestamp ts=new Timestamp(l);	
         String opr_node="";
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
		int cboCashBook_Year1=0,cboCashBook_Month1=0;
		File reportFile = null;
		String qry="";
		Map map = null;
		map = new HashMap();
		map.put("opr_node",opr_node);
		try {
			System.out.println("calling servlet...");
			//changes done in the sql_rep query on 23/03/2016
			//changed query on 18/07/2016
			//changed query on 25/08/2016 SSS total1,total2,total_t2
			String sql_rep="";
			if(cboAcc_UnitCode==3)
			{
				sql_rep="SELECT rownum AS rowno1,\n" + 
			            "  OFFICE_NAME,\n" + 
			            "  OB_PART1,\n" + 
			            "  cr,\n"+
			            "  cr_final,jrnl_dr,jrnl_cr,\n" + 
			            "  total1,\n" + 
			            "  dr,\n" + 
			            "  total2,\n" + 
			            "  amt_t2_A,\n" + 
			            "  amt_2T_clos_bal,\n" + 
			            "'"+bankName+ "' as  BANK_NAME,\n" + 
			            "'"+branchName+  "' as  BRANCH_NAME,\n" + 
			            "  t2_sl_no,\n" + 
			            "  DECODE(amt_2t,NULL,0,amt_2t)AS amt_2t,\n" + 
			            "  trans_desc,\n" + 
			            "  total_t2\n" +
			            "FROM\n" + 
			            "  (SELECT rownum AS rowno1,\n" + 
			            "    OFFICE_NAME,\n" + 
			            "    OB_PART1,\n" + 
			          //  "    cr,cr_final,"+
			            "    jrnl_dr,jrnl_cr,\n" + 
			          //  "    total1,\n" + 
			            "    dr,\n" + 
			           // "    total2,\n" + 
			            "    amt_t2_A,\n" + 
			            "    amt_2T_clos_bal,\n" + 
			            "    BANK_NAME,\n" + 
			            "    BRANCH_NAME,\n" + 
			            "    t2_sl_no,\n" + 
			            "    amt_2t,\n" + 
			            "    trans_desc,\n" + 
			            "    (DECODE(cr,NULL,0,cr)) as cr,\n"+
			            "    (DECODE(cr_final,NULL,0,cr_final)) as cr_final,\n"+
			            "    (DECODE(total1,NULL,0,total1)) as total1,\n" +
			            "    (DECODE(total2,NULL,0,total2)) as total2,\n" +
			            "    (DECODE(total,NULL,0,total)+amt_t2_A+amt_2T_clos_bal) AS total_t2\n" + 
			            "  FROM\n" + 
			            "    (SELECT OFFICE_NAME,\n" + 
			            "      acc_u_id_T,\n" + 
			            "      acc_off_id_T,\n" + 
			            "      csh_bk_yr_T,\n" + 
			            "      csh_bk_mnth_T,\n" + 
			            "      acc_no_T,\n" + 
			            "      OB_PART1,\n" + 
			            "      cr,cr_final,jrnl_dr,jrnl_cr,\n" + 
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
			            "        DECODE(cr,NULL,0,cr)             AS cr,cr_final,jrnl_dr,jrnl_cr,\n" + 
			            "        DECODE(total1,NULL,0,total1)     AS total1,\n" + 
			            "        DECODE(dr,NULL,0,dr)             AS dr,\n" + 
			            "        DECODE(total2,NULL,0,total2)     AS total2\n" + 
			            "      FROM\n" + 
			            "        (SELECT OFFICE_NAME,\n" + 
			            " 			acc_u_id as acc_u_id_T,\n" + 
			            "            acc_off_id as acc_off_id_T,\n" + 
			            "            csh_bk_yr as csh_bk_yr_T,\n" + 
			            "            csh_bk_mnth as csh_bk_mnth_T,\n" + 
			            "            acc_no as acc_no_T,\n" + 
			          /*  "          acc_u_id_T,\n" +  changes done in the query above on 23/03/2016
			            "          acc_off_id_T,\n" + 
			            "          csh_bk_yr_T,\n" + 
			            "          csh_bk_mnth_T,\n" + 
			            "          acc_no_T,\n" + */
			            "          OB_PART1,\n" + 
			            "          decode(cr,null,0,cr)as cr,cr_final,jrnl_dr,jrnl_cr,\n" + 
			            "          (OB_PART1+decode(cr,null,0,cr)) AS total1,\n" + 
			            "          dr,\n" + 
			            "          ((OB_PART1+decode(cr,null,0,cr))-decode(dr,null,0,dr)) AS total2\n" + 
			            "        FROM\n" + 
			            "          (SELECT " +
			            " 			acc_u_id,\n" + 
			            "            acc_off_id,\n" + 
			            "            csh_bk_yr,\n" + 
			            "            csh_bk_mnth,\n" + 
			            "            acc_no,\n" + 
			            "          sum( OB_PART1) as OB_PART1 ,\n" + 
			            "            OFFICE_NAME\n" + 
			            "          FROM\n" ;
			            if(cboAcc_UnitCode==5)
			            {
			           	 cboCashBook_Year1=0;
			           	 cboCashBook_Month1=0;		
							if (cboCashBook_Month == 1) {
								cboCashBook_Month1 = 12;
								cboCashBook_Year1 = cboCashBook_Year - 1;
							} else {
								cboCashBook_Month1 = cboCashBook_Month - 1;
								cboCashBook_Year1 = cboCashBook_Year;
							}
			            	
			            
			            	sql_rep=sql_rep+  "            (SELECT ACCOUNTING_UNIT_ID AS acc_u_id,\n" + 
			                        "              ACCOUNTING_FOR_OFFICE_ID AS acc_off_id,\n" + 
			                        cboCashBook_Year+    "                          AS csh_bk_yr,\n" + 
			                        cboCashBook_Month+    "                  AS csh_bk_mnth,\n" + 
			                        "              ACCOUNT_NO               AS acc_no,\n" + 
			                        "           S5 as   OB_PART1\n" + 
			                        "            FROM FAS_BRS_PART1\n" + 
			                        "            WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
			                        "            AND ACCOUNTING_FOR_OFFICE_ID=" +cboOffice_code+ 
			                             "            AND PASS_SHEET_MONTH          = " +cboCashBook_Month1+ 
			            "            AND PASS_SHEET_YEAR           =" +cboCashBook_Year1+ 
			                        "            AND ACCOUNT_NO              = " +cmbBankAccNo+
			                       "         Union All\n" + 
			                      "          SELECT "+cboAcc_UnitCode+" AS acc_u_id,\n" + 
			                      cboOffice_code+ "               As Acc_Off_Id,\n" + 
			                     cboCashBook_Year+    "                          AS csh_bk_yr,\n" + 
			                     cboCashBook_Month+    "                  AS csh_bk_mnth,\n" + 
			                     cmbBankAccNo+  "                           As Acc_No,\n" + 
			                      "             0                      As Ob_Part1\n" + 
			                         
			                      "             \n " ;
			                       
			                        
			                        
			                  //      "            AND ob_type                 ='T'\n" ; 	
			            }else{
			            	sql_rep=sql_rep+  "            (SELECT ACCOUNTING_UNIT_ID AS acc_u_id,\n" + 
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
			            "            AND ob_type                 ='T'\n" ; 
			            }
			            sql_rep=sql_rep+ "    )        AUID1\n" + 
			            "          LEFT OUTER JOIN\n" + 
			            "            (SELECT OFFICE_ID,OFFICE_NAME FROM COM_MST_OFFICES\n" + 
			            "            )AUID2\n" + 
			            "          ON AUID1.acc_off_id = AUID2.OFFICE_ID\n" +
			           
			            	 "     group by acc_u_id,\n" + 
			            			    "         acc_off_id,\n" + 
			            			    "         csh_bk_yr,\n" + 
			            			    "          Csh_Bk_Mnth,\n" + 
			            			    "           Acc_No,OFFICE_NAME "+
			            
			            "          )X\n" + 
			            "        LEFT OUTER JOIN\n" + 
			            "          (SELECT  "+cboAcc_UnitCode+" as acc_u_id_T,\n" + 
			                      +cboOffice_code+  " as  acc_off_id_T,\n" + 
			                      cboCashBook_Year+   "    as        csh_bk_yr_T,\n" + 
			                      cboCashBook_Month+    "        as    csh_bk_mnth_T,\n" + 
			                      cmbBankAccNo+    "       as     acc_no_T,\n" + 
			            "            cr_T1 AS cr,\n" + 
			            "            dr_T1 AS dr,cr_final,jrnl_dr,jrnl_cr\n" + 
			            "          FROM\n" + 
			            "            (\n" + 
			            "            \n" + 
			            "            SELECT a.acc_u_id_T,\n" + 
			            "              a.acc_off_id_T,\n" + 
			            "              a.csh_bk_yr_T,\n" + 
			            "              a.csh_bk_mnth_T,\n" + 
			            "              a.acc_no_T,\n" + 
			            "              decode(jrnl_cr,null,0,jrnl_cr)as jrnl_cr,\n" + 
			            "              decode(a.cramt,null,0,a.cramt)  AS cr_final,\n" + 
			            "              decode(jrnl_dr,null,0,jrnl_dr) AS jrnl_dr,\n" + 
			           /* 
			            * Joan changed on 19 Aug 15
			            * 
			            * "              (decode(jrnl_cr,null,0,jrnl_cr)+decode(a.cramt,null,0,a.cramt)+decode(jrnl_dr,null,0,jrnl_dr))AS cr_T1\n" +*/
			            "              (decode(jrnl_dr,null,0,jrnl_dr)+decode(a.cramt,null,0,a.cramt))-decode(jrnl_cr,null,0,jrnl_cr)AS cr_T1\n" + 
			            "              \n" + 
			            "            FROM\n" + 
			            "              (\n" + 
			            "              select acc_u_id_T,acc_off_id_T,csh_bk_yr_T,csh_bk_mnth_T,sum(cramt)as cramt,"+cmbBankAccNo+" as acc_no_T\n" + 
			            "              from\n" + 
			            "              (SELECT ACCOUNTING_UNIT_ID  AS acc_u_id_T,\n" + 
			            "                ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T,\n" + 
			            "                CASHBOOK_YEAR            AS csh_bk_yr_T,\n" + 
			            "                CASHBOOK_MONTH           AS csh_bk_mnth_T,\n" + 
			            "                ACCOUNT_NO               AS acc_no_T,\n" + 
			            "                SUM(TOTAL_AMOUNT)           AS cramt,CREATED_BY_MODULE\n" + 
			            "              FROM FAS_RECEIPT_MASTER\n" + 
			            "              WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
			            "              AND accounting_for_office_id=" +cboOffice_code+ 
			            "              AND cashbook_month          = " +cboCashBook_Month+ 
			            "              AND cashbook_year           =" +cboCashBook_Year+ 
			            "              and ACCOUNT_NO=" +cmbBankAccNo+ 
			            "		and RECEIPT_STATUS='L'\n" + 
			            "              and CREATED_BY_MODULE!='SC' and remittance_in_curr_month='Y' \n" + 
			            "              GROUP BY ACCOUNTING_UNIT_ID,\n" + 
			            "                ACCOUNTING_FOR_OFFICE_ID,\n" + 
			            "                CASHBOOK_YEAR,\n" + 
			            "                CASHBOOK_MONTH,\n" + 
			            "                ACCOUNT_NO,CREATED_BY_MODULE\n" + 
					    "        UNION ALL\n" +
					    "        SELECT ACCOUNTING_UNIT_ID AS acc_u_id_T,\n" +
					    "         ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T,\n" +
					    "        extract(year from challan_date)            AS csh_bk_yr_T,\n" +
					    "       extract(month from challan_date)           AS csh_bk_mnth_T,\n" +
					    " 		ACCOUNT_NO               AS acc_no_T,\n" +
			            " 		SUM(TOTAL_AMOUNT)        AS cramt,\n" +
			            "         CREATED_BY_MODULE\n" +
			            "       FROM FAS_RECEIPT_MASTER\n" +
			            "       WHERE accounting_unit_id    = " +cboAcc_UnitCode+
			            "       AND accounting_for_office_id=" +cboOffice_code+
			            "        AND remittance_in_curr_month='N'\n" +
			            "       and extract(year from challan_date)=" +cboCashBook_Year+
			            "       and extract(month from challan_date)=" +cboCashBook_Month+
			            "       AND ACCOUNT_NO              =" +cmbBankAccNo+
			            "       AND RECEIPT_STATUS          ='L'\n" +
			            "       AND CREATED_BY_MODULE!      ='SC'\n" +
			            "       GROUP BY ACCOUNTING_UNIT_ID,\n" +
			            "         ACCOUNTING_FOR_OFFICE_ID,\n" +
			            "         extract(year from challan_date),\n" +
			            "         extract(month from challan_date),\n" +
			            "         ACCOUNT_NO,\n" +
			            "         CREATED_BY_MODULE\n" +
			            "                union all\n" + 
			            "                SELECT ACCOUNTING_UNIT_ID  AS acc_u_id_T,\n" + 
			            "                ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T,\n" + 
			            "                CASHBOOK_YEAR            AS csh_bk_yr_T,\n" + 
			            "                CASHBOOK_MONTH           AS csh_bk_mnth_T,\n" + 
			            "                ACCOUNT_NO               AS acc_no_T,\n" + 
			            "                SUM(TOTAL_AMOUNT)           AS cramt,CREATED_BY_MODULE\n" + 
			            "              FROM FAS_RECEIPT_MASTER\n" + 
			            "              WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
			            "              AND accounting_for_office_id=" +cboOffice_code+ 
			            "              AND cashbook_month          = " +cboCashBook_Month+ 
			            "              AND cashbook_year           =" +cboCashBook_Year+ 
			            "              and ACCOUNT_NO=0\n" + 
			            "		and RECEIPT_STATUS='L'\n" + 
			            "              and CREATED_BY_MODULE!='SC' and remittance_in_curr_month='Y' \n" + 
			            "              GROUP BY ACCOUNTING_UNIT_ID,\n" + 
			            "                ACCOUNTING_FOR_OFFICE_ID,\n" + 
			            "                CASHBOOK_YEAR,\n" + 
			            "                CASHBOOK_MONTH,\n" + 
			            "                ACCOUNT_NO,CREATED_BY_MODULE\n" + 
			            "                  union all\n" + 
			            "                  SELECT ACCOUNTING_UNIT_ID  AS acc_u_id_T,\n" + 
			            "                   ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T,\n" + 
			            "                   CASHBOOK_YEAR            AS csh_bk_yr_T,\n" + 
			            "                   CASHBOOK_MONTH           AS csh_bk_mnth_T,\n" + 
			            "                    TO_ACCOUNT_NO               AS acc_no_T,\n" + 
			            "                     SUM(TOTAL_AMOUNT)        AS cramt,\n" + 
			            "                    '' as   CREATED_BY_MODULE\n" + 
			            "                    FROM FAS_INTER_BANK_TRF_AT_HO\n" + 
			            "                    WHERE accounting_unit_id      = " +cboAcc_UnitCode+ 
			            "                    AND accounting_for_office_id=" +cboOffice_code+ 
			            "                    AND cashbook_month             = " +cboCashBook_Month+ 
			            "                    AND cashbook_year           =" +cboCashBook_Year+ 
			            "                    AND TO_ACCOUNT_NO              =" +cmbBankAccNo+
			            "                   AND TRANSFER_STATUS          ='L'    \n" +         
			            "                  GROUP BY ACCOUNTING_UNIT_ID,\n" + 
			            "                     ACCOUNTING_FOR_OFFICE_ID,\n" + 
			            "                     CASHBOOK_YEAR,\n" + 
			            "                     CASHBOOK_MONTH,\n" + 
			            "                     TO_ACCOUNT_NO\n" +   
			            "         ) group by acc_u_id_T,acc_off_id_T,csh_bk_yr_T,csh_bk_mnth_T    )a\n" + 
			            "            FULL OUTER JOIN\n" + 
			            "              (\n" + 
			            "                SELECT m.ACCOUNTING_UNIT_ID  AS acc_u_id_NT,\n" + 
			            "                  m.ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_NT,\n" + 
			            "                  m.CASHBOOK_YEAR            AS csh_bk_yr_NT,\n" + 
			            "                  m.CASHBOOK_MONTH           AS csh_bk_mnth_NT,\n" + 
			            "                  t.sub_ledger_code               AS acc_no_NT,\n" + 
			            "                  SUM(t.AMOUNT)           AS jrnl_cr\n" + 
			            "                FROM FAS_JOURNAL_MASTER m,FAS_JOURNAL_TRANSACTION t\n" + 
			            "                WHERE m.accounting_unit_id=t.accounting_unit_id\n" + 
			            "                and m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID\n" + 
			            "                and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR\n" + 
			            "                and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH\n" + 
			            "                and m.VOUCHER_NO=t.VOUCHER_NO\n" + 
			            "                and m.journal_status='L'\n" + 
			            "                and m.accounting_unit_id    = " +cboAcc_UnitCode+ 
			            "                AND m.accounting_for_office_id=" +cboOffice_code+ 
			            "                AND m.cashbook_month          = " +cboCashBook_Month+ 
			            "                AND m.cashbook_year           =" +cboCashBook_Year+ 
			            "                and t.sub_ledger_code=" +cmbBankAccNo+ 
			            "                and t.CR_DR_INDICATOR='CR'\n" + 
			            "                GROUP BY m.ACCOUNTING_UNIT_ID,\n" + 
			            "                  m.ACCOUNTING_FOR_OFFICE_ID,\n" + 
			            "                  m.CASHBOOK_YEAR,\n" + 
			            "                  m.CASHBOOK_MONTH,\n" + 
			            "                  t.sub_ledger_code\n" + 
			            "              )b\n" + 
			            "            ON a.acc_u_id_T    =b.acc_u_id_NT\n" + 
			            "            AND a.acc_off_id_T =b.acc_off_id_NT\n" + 
			            "            AND a.csh_bk_yr_T  =b.csh_bk_yr_NT\n" + 
			            "            AND a.csh_bk_mnth_T=b.csh_bk_mnth_NT\n" + 
			            "            AND a.acc_no_T     =b.acc_no_NT\n" + 
			            "           \n" + 
			            "            FULL OUTER JOIN\n" + 
			            "              (SELECT m.ACCOUNTING_UNIT_ID  AS acc_u_id_NT,\n" + 
			            "                  m.ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_NT,\n" + 
			            "                  m.CASHBOOK_YEAR            AS csh_bk_yr_NT,\n" + 
			            "                  m.CASHBOOK_MONTH           AS csh_bk_mnth_NT,\n" + 
			            "                  t.sub_ledger_code               AS acc_no_NT,\n" + 
			            "                  SUM(t.AMOUNT)           AS jrnl_dr\n" + 
			            "                FROM FAS_JOURNAL_MASTER m,FAS_JOURNAL_TRANSACTION t\n" + 
			            "                WHERE m.accounting_unit_id=t.accounting_unit_id\n" + 
			            "                and m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID\n" + 
			            "                and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR\n" + 
			            "                and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH\n" + 
			            "                and m.VOUCHER_NO=t.VOUCHER_NO\n" + 
			            "                and m.journal_status='L'\n" + 
			            "                and m.accounting_unit_id    = " +cboAcc_UnitCode+ 
			            "                AND m.accounting_for_office_id=" +cboOffice_code+ 
			            "                AND m.cashbook_month          = " +cboCashBook_Month+ 
			            "                AND m.cashbook_year           =" +cboCashBook_Year+ 
			            "                and t.sub_ledger_code=" +cmbBankAccNo+ 
			            "                and t.CR_DR_INDICATOR='DR'\n" + 
			            "                GROUP BY m.ACCOUNTING_UNIT_ID,\n" + 
			            "                  m.ACCOUNTING_FOR_OFFICE_ID,\n" + 
			            "                  m.CASHBOOK_YEAR,\n" + 
			            "                  m.CASHBOOK_MONTH,\n" + 
			            "                  t.sub_ledger_code\n" + 
			            "              )d\n" + 
			            "            ON a.acc_u_id_T    =d.acc_u_id_NT\n" + 
			            "            AND a.acc_off_id_T =d.acc_off_id_NT\n" + 
			            "            AND a.csh_bk_yr_T  =d.csh_bk_yr_NT\n" + 
			            "            AND a.csh_bk_mnth_T=d.csh_bk_mnth_NT\n" + 
			            "            AND a.acc_no_T     =d.acc_no_NT\n" + 
			            "            )a\n" + 
			        //    "          LEFT OUTER JOIN\n" + 
			        "          full OUTER JOIN\n" + 
			            "            (\n" + 
			            "\n" + 
			            "            select sum(dr_T1)as dr_T1,acc_u_id_T1,acc_off_id_T1,csh_bk_yr_T1,csh_bk_mnth_T1,acc_no_T1 from\n" + 
			            "(SELECT ACCOUNTING_UNIT_ID AS acc_u_id_T1,\n" + 
			            "              ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T1,\n" + 
			            "              CASHBOOK_YEAR            AS csh_bk_yr_T1,\n" + 
			            "              CASHBOOK_MONTH           AS csh_bk_mnth_T1,\n" + 
			            "              ACCOUNT_NO               AS acc_no_T1,\n" + 
			            "              SUM(DR_AMOUNT)           AS dr_T1\n" + 
			            "            FROM FAS_BRS_TRANSACTION\n" + 
			            "            WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
			            "            AND accounting_for_office_id=" +cboOffice_code+ 
			            "            AND cashbook_month          =" +cboCashBook_Month+ 
			            "            AND cashbook_year           =" +cboCashBook_Year+ 
			            "            AND ACCOUNT_NO              =" +cmbBankAccNo+ 
			            "            AND doc_type LIKE 'FT%'\n" + 
			            "            GROUP BY ACCOUNTING_UNIT_ID,\n" + 
			            "              ACCOUNTING_FOR_OFFICE_ID,\n" + 
			            "              CASHBOOK_YEAR,\n" + 
			            "              CASHBOOK_MONTH,\n" + 
			            "              ACCOUNT_NO\n" + 
			            "              union all\n" + 
			            "              SELECT ACCOUNTING_UNIT_ID AS acc_u_id_T1,\n" + 
			            "              ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T1,\n" + 
			            "              CASHBOOK_YEAR            AS csh_bk_yr_T1,\n" + 
			            "              CASHBOOK_MONTH           AS csh_bk_mnth_T1,\n" + 
			            "              ACCOUNT_NO               AS acc_no_T1,\n" + 
			            "              SUM(DR_AMOUNT)           AS dr_T1\n" + 
			            "            FROM fas_brs_transaction_noentry\n" + 
			            "            WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
			            "            AND accounting_for_office_id=" +cboOffice_code+ 
			            "            AND cashbook_month          =" +cboCashBook_Month+ 
			            "            AND cashbook_year           =" +cboCashBook_Year+ 
			            "            AND ACCOUNT_NO              =" +cmbBankAccNo+ 
			            "            AND doc_type LIKE 'FT%'\n" + 
			            "            GROUP BY ACCOUNTING_UNIT_ID,\n" + 
			            "              ACCOUNTING_FOR_OFFICE_ID,\n" + 
			            "              CASHBOOK_YEAR,\n" + 
			            "              CASHBOOK_MONTH,\n" + 
			            "              ACCOUNT_NO)group by acc_u_id_T1,acc_off_id_T1,csh_bk_yr_T1,csh_bk_mnth_T1,acc_no_T1\n" + 
			            "		)c\n" + 
			            "          ON A.acc_u_id_T     =c.acc_u_id_T1\n" + 
			            "          AND A.acc_off_id_T  = c.acc_off_id_T1\n" + 
			            "          AND A.csh_bk_yr_T   = c.csh_bk_yr_T1\n" + 
			            "          AND A.csh_bk_mnth_T = c.csh_bk_mnth_T1\n" + 
			            "          AND A.acc_no_T      = c.acc_no_T1\n" + 
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
			            "           SELECT ( SUM(cr_amount) - SUM(dr_amount) ) AS amt_t2_A\n" + 
			            "    FROM FAS_BRS_TRANSACTION_NOENTRY\n" + 
			            "    WHERE accounting_unit_id               =" +cboAcc_UnitCode+ 
			            "    AND accounting_for_office_id           =" +cboOffice_code+ 
			            "   AND ((cashbook_year                     <"+cboCashBook_Year+" and cashbook_month<=12) or (cashbook_year="+cboCashBook_Year+" and cashbook_month<="+cboCashBook_Month+"))\n" + 
			            "    AND ACCOUNT_NO                         = " +cmbBankAccNo+ 
			            "    AND TWAD_OR_NON_TWAD                   ='T'\n" + 
			            "    AND doc_type                          IN ('CR', 'BR','FR by HO', 'FR by Office')\n" + 
			            "    UNION ALL\n" + 
			            "    SELECT ( SUM(cr_amount) - SUM(dr_amount) ) AS amt_t2_A\n" + 
			            "    FROM FAS_BRS_TRANSACTION\n" + 
			            "    WHERE accounting_unit_id                =" +cboAcc_UnitCode+ 
			            "    AND Accounting_For_Office_Id            =" +cboOffice_code+ 
			            "    and PASSBOOK_DATE>('"+totalyear+"')\n" + 
			            "    and (01||'-'||Cashbook_Month||'-'||cashbook_year)<=('"+totalyear+"')\n" + 
			            "    AND ((cashbook_year                     <"+cboCashBook_Year+" and cashbook_month<=12) or (cashbook_year="+cboCashBook_Year+" and cashbook_month<="+cboCashBook_Month+"))\n" + 
			            "    AND Account_No                          =" +cmbBankAccNo+ 
			            "    AND Twad_Or_Non_Twad                    ='T'\n" + 
			            "    AND doc_type                           IN ('CR', 'BR','FR by HO', 'FR by Office')   \n" + 
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
			            "        trans_desc,\n" + 
			            "	"+cboCashBook_Year+" as cbyear,\n" + 
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
			            "          AND twad_or_non_twad            = 'NT'\n" + 
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
			            "          AND twad_or_non_twad            = 'NT'\n" + 
			            "          AND (CLEARED_BASED_ON_FOLLOWUP='Y' " +
			           //changed the condition by adding the is it clearing entry and date <= on 06/10/2016 sss pjt rajapalayam
			            "		and  IS_IT_CLEARING_ENTRY ='Y' and clearence_date <= ('"+totalyear+"'))\n" + 
			            "          \n" + 
			            "         AND transaction_type in(4,8,12)"+
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
			            "      AND accounting_for_office_id    =" +cboOffice_code+ 
			            "      AND ((cashbook_year             <" +cboCashBook_Year+ 
			            "      AND cashbook_month             <=12)\n" + 
			            "      OR (cashbook_year               =" +cboCashBook_Year+ 
			            "      AND cashbook_month             <="+cboCashBook_Month+"))\n" + 
			            "      AND ACCOUNT_NO                  = " +cmbBankAccNo+ 
			            "      AND twad_or_non_twad            = 'NT'\n" + 
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
			            "      AND twad_or_non_twad            = 'NT'\n" + 
			            "     AND (CLEARED_BASED_ON_FOLLOWUP='Y' " +
			            "	  and  IS_IT_CLEARING_ENTRY ='Y' and clearence_date <=('"+totalyear+"'))\n" + 
			            "      \n" + 
			            " AND transaction_type in(4,8,12)" +
			            "      GROUP BY transaction_type,\n" + 
			            "        accounting_unit_id,\n" + 
			            "        accounting_for_office_id,\n" + 
			            "        cashbook_year,\n" + 
			            "        ACCOUNT_NO\n" + 
			            "        )t2_c\n" + 
			            "LEFT OUTER JOIN\n" + 
			            "  ( SELECT trans_code,trans_short_desc as trans_desc FROM fas_brs_transaction_type\n" + 
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
			}
			else
			{
			
             sql_rep="SELECT rownum AS rowno1,\n" + 
            "  OFFICE_NAME,\n" + 
            "  OB_PART1,\n" + 
            "  cr,\n"+
            "  cr_final,jrnl_dr,jrnl_cr,\n" + 
            "  total1,\n" + 
            "  dr,\n" + 
            "  total2,\n" + 
            "  amt_t2_A,\n" + 
            "  amt_2T_clos_bal,\n" + 
            "'"+bankName+ "' as  BANK_NAME,\n" + 
            "'"+branchName+  "' as  BRANCH_NAME,\n" + 
            "  t2_sl_no,\n" + 
            "  DECODE(amt_2t,NULL,0,amt_2t)AS amt_2t,\n" + 
            "  trans_desc,\n" + 
            "  total_t2\n" +
            "FROM\n" + 
            "  (SELECT rownum AS rowno1,\n" + 
            "    OFFICE_NAME,\n" + 
            "    OB_PART1,\n" + 
          //  "    cr,cr_final,"+
            "    jrnl_dr,jrnl_cr,\n" + 
          //  "    total1,\n" + 
            "    dr,\n" + 
           // "    total2,\n" + 
            "    amt_t2_A,\n" + 
            "    amt_2T_clos_bal,\n" + 
            "    BANK_NAME,\n" + 
            "    BRANCH_NAME,\n" + 
            "    t2_sl_no,\n" + 
            "    amt_2t,\n" + 
            "    trans_desc,\n" + 
            "    (DECODE(cr,NULL,0,cr)) as cr,\n"+
            "    (DECODE(cr_final,NULL,0,cr_final)) as cr_final,\n"+
            "    (DECODE(total1,NULL,0,total1)) as total1,\n" +
            "    (DECODE(total2,NULL,0,total2)) as total2,\n" +
            "    (DECODE(total,NULL,0,total)+amt_t2_A+amt_2T_clos_bal) AS total_t2\n" + 
            "  FROM\n" + 
            "    (SELECT OFFICE_NAME,\n" + 
            "      acc_u_id_T,\n" + 
            "      acc_off_id_T,\n" + 
            "      csh_bk_yr_T,\n" + 
            "      csh_bk_mnth_T,\n" + 
            "      acc_no_T,\n" + 
            "      OB_PART1,\n" + 
            "      cr,cr_final,jrnl_dr,jrnl_cr,\n" + 
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
            "        DECODE(cr,NULL,0,cr)             AS cr,cr_final,jrnl_dr,jrnl_cr,\n" + 
            "        DECODE(total1,NULL,0,total1)     AS total1,\n" + 
            "        DECODE(dr,NULL,0,dr)             AS dr,\n" + 
            "        DECODE(total2,NULL,0,total2)     AS total2\n" + 
            "      FROM\n" + 
            "        (SELECT OFFICE_NAME,\n" + 
            " 			acc_u_id as acc_u_id_T,\n" + 
            "            acc_off_id as acc_off_id_T,\n" + 
            "            csh_bk_yr as csh_bk_yr_T,\n" + 
            "            csh_bk_mnth as csh_bk_mnth_T,\n" + 
            "            acc_no as acc_no_T,\n" + 
          /*  "          acc_u_id_T,\n" +  changes done in the query above on 23/03/2016
            "          acc_off_id_T,\n" + 
            "          csh_bk_yr_T,\n" + 
            "          csh_bk_mnth_T,\n" + 
            "          acc_no_T,\n" + */
            "          OB_PART1,\n" + 
            "          decode(cr,null,0,cr)as cr,cr_final,jrnl_dr,jrnl_cr,\n" + 
            "          (OB_PART1+decode(cr,null,0,cr)) AS total1,\n" + 
            "          dr,\n" + 
            "          ((OB_PART1+decode(cr,null,0,cr))-decode(dr,null,0,dr)) AS total2\n" + 
            "        FROM\n" + 
            "          (SELECT " +
            " 			acc_u_id,\n" + 
            "            acc_off_id,\n" + 
            "            csh_bk_yr,\n" + 
            "            csh_bk_mnth,\n" + 
            "            acc_no,\n" + 
            "          sum( OB_PART1) as OB_PART1 ,\n" + 
            "            OFFICE_NAME\n" + 
            "          FROM\n" ;
            if(cboAcc_UnitCode==5)
            {
           	 cboCashBook_Year1=0;
           	 cboCashBook_Month1=0;		
				if (cboCashBook_Month == 1) {
					cboCashBook_Month1 = 12;
					cboCashBook_Year1 = cboCashBook_Year - 1;
				} else {
					cboCashBook_Month1 = cboCashBook_Month - 1;
					cboCashBook_Year1 = cboCashBook_Year;
				}
            	
            
            	sql_rep=sql_rep+  "            (SELECT ACCOUNTING_UNIT_ID AS acc_u_id,\n" + 
                        "              ACCOUNTING_FOR_OFFICE_ID AS acc_off_id,\n" + 
                        cboCashBook_Year+    "                          AS csh_bk_yr,\n" + 
                        cboCashBook_Month+    "                  AS csh_bk_mnth,\n" + 
                        "              ACCOUNT_NO               AS acc_no,\n" + 
                        "           S5 as   OB_PART1\n" + 
                        "            FROM FAS_BRS_PART1\n" + 
                        "            WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
                        "            AND ACCOUNTING_FOR_OFFICE_ID=" +cboOffice_code+ 
                             "            AND PASS_SHEET_MONTH          = " +cboCashBook_Month1+ 
            "            AND PASS_SHEET_YEAR           =" +cboCashBook_Year1+ 
                        "            AND ACCOUNT_NO              = " +cmbBankAccNo+
                       "         Union All\n" + 
                      "          SELECT "+cboAcc_UnitCode+" AS acc_u_id,\n" + 
                      cboOffice_code+ "               As Acc_Off_Id,\n" + 
                     cboCashBook_Year+    "                          AS csh_bk_yr,\n" + 
                     cboCashBook_Month+    "                  AS csh_bk_mnth,\n" + 
                     cmbBankAccNo+  "                           As Acc_No,\n" + 
                      "             0                      As Ob_Part1\n" + 
                         
                      "             \n " ;
                       
                        
                        
                  //      "            AND ob_type                 ='T'\n" ; 	
            }else{
            	sql_rep=sql_rep+  "            (SELECT ACCOUNTING_UNIT_ID AS acc_u_id,\n" + 
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
            "            AND ob_type                 ='T'\n" ; 
            }
            sql_rep=sql_rep+ "    )        AUID1\n" + 
            "          LEFT OUTER JOIN\n" + 
            "            (SELECT OFFICE_ID,OFFICE_NAME FROM COM_MST_OFFICES\n" + 
            "            )AUID2\n" + 
            "          ON AUID1.acc_off_id = AUID2.OFFICE_ID\n" +
           
            	 "     group by acc_u_id,\n" + 
            			    "         acc_off_id,\n" + 
            			    "         csh_bk_yr,\n" + 
            			    "          Csh_Bk_Mnth,\n" + 
            			    "           Acc_No,OFFICE_NAME "+
            
            "          )X\n" + 
            "        LEFT OUTER JOIN\n" + 
            "          (SELECT  "+cboAcc_UnitCode+" as acc_u_id_T,\n" + 
                      +cboOffice_code+  " as  acc_off_id_T,\n" + 
                      cboCashBook_Year+   "    as        csh_bk_yr_T,\n" + 
                      cboCashBook_Month+    "        as    csh_bk_mnth_T,\n" + 
                      cmbBankAccNo+    "       as     acc_no_T,\n" + 
            "            cr_T1 AS cr,\n" + 
            "            dr_T1 AS dr,cr_final,jrnl_dr,jrnl_cr\n" + 
            "          FROM\n" + 
            "            (\n" + 
            "            \n" + 
            "            SELECT a.acc_u_id_T,\n" + 
            "              a.acc_off_id_T,\n" + 
            "              a.csh_bk_yr_T,\n" + 
            "              a.csh_bk_mnth_T,\n" + 
            "              a.acc_no_T,\n" + 
            "              decode(jrnl_cr,null,0,jrnl_cr)as jrnl_cr,\n" + 
            "              decode(a.cramt,null,0,a.cramt)  AS cr_final,\n" + 
            "              decode(jrnl_dr,null,0,jrnl_dr) AS jrnl_dr,\n" + 
           /* 
            * Joan changed on 19 Aug 15
            * 
            * "              (decode(jrnl_cr,null,0,jrnl_cr)+decode(a.cramt,null,0,a.cramt)+decode(jrnl_dr,null,0,jrnl_dr))AS cr_T1\n" +*/
            "              (decode(jrnl_dr,null,0,jrnl_dr)+decode(a.cramt,null,0,a.cramt))-decode(jrnl_cr,null,0,jrnl_cr)AS cr_T1\n" + 
            "              \n" + 
            "            FROM\n" + 
            "              (\n" + 
            "              select acc_u_id_T,acc_off_id_T,csh_bk_yr_T,csh_bk_mnth_T,sum(cramt)as cramt,"+cmbBankAccNo+" as acc_no_T\n" + 
            "              from\n" + 
            "              (SELECT ACCOUNTING_UNIT_ID  AS acc_u_id_T,\n" + 
            "                ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T,\n" + 
            "                CASHBOOK_YEAR            AS csh_bk_yr_T,\n" + 
            "                CASHBOOK_MONTH           AS csh_bk_mnth_T,\n" + 
            "                ACCOUNT_NO               AS acc_no_T,\n" + 
            "                SUM(TOTAL_AMOUNT)           AS cramt,CREATED_BY_MODULE\n" + 
            "              FROM FAS_RECEIPT_MASTER\n" + 
            "              WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
            "              AND accounting_for_office_id=" +cboOffice_code+ 
            "              AND cashbook_month          = " +cboCashBook_Month+ 
            "              AND cashbook_year           =" +cboCashBook_Year+ 
            "              and ACCOUNT_NO=" +cmbBankAccNo+ 
            "		and RECEIPT_STATUS='L'\n" + 
            "              and CREATED_BY_MODULE!='SC' and remittance_in_curr_month='Y' \n" + 
            "              GROUP BY ACCOUNTING_UNIT_ID,\n" + 
            "                ACCOUNTING_FOR_OFFICE_ID,\n" + 
            "                CASHBOOK_YEAR,\n" + 
            "                CASHBOOK_MONTH,\n" + 
            "                ACCOUNT_NO,CREATED_BY_MODULE\n" + 
		    "        UNION ALL\n" +
		    "        SELECT ACCOUNTING_UNIT_ID AS acc_u_id_T,\n" +
		    "         ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T,\n" +
		    "        extract(year from challan_date)            AS csh_bk_yr_T,\n" +
		    "       extract(month from challan_date)           AS csh_bk_mnth_T,\n" +
		    " 		ACCOUNT_NO               AS acc_no_T,\n" +
            " 		SUM(TOTAL_AMOUNT)        AS cramt,\n" +
            "         CREATED_BY_MODULE\n" +
            "       FROM FAS_RECEIPT_MASTER\n" +
            "       WHERE accounting_unit_id    = " +cboAcc_UnitCode+
            "       AND accounting_for_office_id=" +cboOffice_code+
            "        AND remittance_in_curr_month='N'\n" +
            "       and extract(year from challan_date)=" +cboCashBook_Year+
            "       and extract(month from challan_date)=" +cboCashBook_Month+
            "       AND ACCOUNT_NO              =" +cmbBankAccNo+
            "       AND RECEIPT_STATUS          ='L'\n" +
            "       AND CREATED_BY_MODULE!      =='SC'\n" +
            "       GROUP BY ACCOUNTING_UNIT_ID,\n" +
            "         ACCOUNTING_FOR_OFFICE_ID,\n" +
            "         extract(year from challan_date),\n" +
            "         extract(month from challan_date),\n" +
            "         ACCOUNT_NO,\n" +
            "         CREATED_BY_MODULE\n" +
            "                union all\n" + 
            "                SELECT ACCOUNTING_UNIT_ID  AS acc_u_id_T,\n" + 
            "                ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T,\n" + 
            "                CASHBOOK_YEAR            AS csh_bk_yr_T,\n" + 
            "                CASHBOOK_MONTH           AS csh_bk_mnth_T,\n" + 
            "                ACCOUNT_NO               AS acc_no_T,\n" + 
            "                SUM(TOTAL_AMOUNT)           AS cramt,CREATED_BY_MODULE\n" + 
            "              FROM FAS_RECEIPT_MASTER\n" + 
            "              WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
            "              AND accounting_for_office_id=" +cboOffice_code+ 
            "              AND cashbook_month          = " +cboCashBook_Month+ 
            "              AND cashbook_year           =" +cboCashBook_Year+ 
            "              and ACCOUNT_NO=0\n" + 
            "		and RECEIPT_STATUS='L'\n" + 
            "              and CREATED_BY_MODULE!='SC' and remittance_in_curr_month='Y' \n" + 
            "              GROUP BY ACCOUNTING_UNIT_ID,\n" + 
            "                ACCOUNTING_FOR_OFFICE_ID,\n" + 
            "                CASHBOOK_YEAR,\n" + 
            "                CASHBOOK_MONTH,\n" + 
            "                ACCOUNT_NO,CREATED_BY_MODULE\n" + 
            "                  union all\n" + 
            "                  SELECT ACCOUNTING_UNIT_ID  AS acc_u_id_T,\n" + 
            "                   ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T,\n" + 
            "                   CASHBOOK_YEAR            AS csh_bk_yr_T,\n" + 
            "                   CASHBOOK_MONTH           AS csh_bk_mnth_T,\n" + 
            "                    TO_ACCOUNT_NO               AS acc_no_T,\n" + 
            "                     SUM(TOTAL_AMOUNT)        AS cramt,\n" + 
            "                    '' as   CREATED_BY_MODULE\n" + 
            "                    FROM FAS_INTER_BANK_TRF_AT_HO\n" + 
            "                    WHERE accounting_unit_id      = " +cboAcc_UnitCode+ 
            "                    AND accounting_for_office_id=" +cboOffice_code+ 
            "                    AND cashbook_month             = " +cboCashBook_Month+ 
            "                    AND cashbook_year           =" +cboCashBook_Year+ 
            "                    AND TO_ACCOUNT_NO              =" +cmbBankAccNo+
            "                   AND TRANSFER_STATUS          ='L'    \n" +         
            "                  GROUP BY ACCOUNTING_UNIT_ID,\n" + 
            "                     ACCOUNTING_FOR_OFFICE_ID,\n" + 
            "                     CASHBOOK_YEAR,\n" + 
            "                     CASHBOOK_MONTH,\n" + 
            "                     TO_ACCOUNT_NO\n" +   
            "         ) group by acc_u_id_T,acc_off_id_T,csh_bk_yr_T,csh_bk_mnth_T    )a\n" + 
            "            FULL OUTER JOIN\n" + 
            "              (\n" + 
            "                SELECT m.ACCOUNTING_UNIT_ID  AS acc_u_id_NT,\n" + 
            "                  m.ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_NT,\n" + 
            "                  m.CASHBOOK_YEAR            AS csh_bk_yr_NT,\n" + 
            "                  m.CASHBOOK_MONTH           AS csh_bk_mnth_NT,\n" + 
            "                  t.sub_ledger_code               AS acc_no_NT,\n" + 
            "                  SUM(t.AMOUNT)           AS jrnl_cr\n" + 
            "                FROM FAS_JOURNAL_MASTER m,FAS_JOURNAL_TRANSACTION t\n" + 
            "                WHERE m.accounting_unit_id=t.accounting_unit_id\n" + 
            "                and m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID\n" + 
            "                and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR\n" + 
            "                and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH\n" + 
            "                and m.VOUCHER_NO=t.VOUCHER_NO\n" + 
            "                and m.journal_status='L'\n" + 
            "                and m.accounting_unit_id    = " +cboAcc_UnitCode+ 
            "                AND m.accounting_for_office_id=" +cboOffice_code+ 
            "                AND m.cashbook_month          = " +cboCashBook_Month+ 
            "                AND m.cashbook_year           =" +cboCashBook_Year+ 
            "                and t.sub_ledger_code=" +cmbBankAccNo+ 
            "                and t.CR_DR_INDICATOR='CR'\n" + 
            "                GROUP BY m.ACCOUNTING_UNIT_ID,\n" + 
            "                  m.ACCOUNTING_FOR_OFFICE_ID,\n" + 
            "                  m.CASHBOOK_YEAR,\n" + 
            "                  m.CASHBOOK_MONTH,\n" + 
            "                  t.sub_ledger_code\n" + 
            "              )b\n" + 
            "            ON a.acc_u_id_T    =b.acc_u_id_NT\n" + 
            "            AND a.acc_off_id_T =b.acc_off_id_NT\n" + 
            "            AND a.csh_bk_yr_T  =b.csh_bk_yr_NT\n" + 
            "            AND a.csh_bk_mnth_T=b.csh_bk_mnth_NT\n" + 
            "            AND a.acc_no_T     =b.acc_no_NT\n" + 
            "           \n" + 
            "            FULL OUTER JOIN\n" + 
            "              (SELECT m.ACCOUNTING_UNIT_ID  AS acc_u_id_NT,\n" + 
            "                  m.ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_NT,\n" + 
            "                  m.CASHBOOK_YEAR            AS csh_bk_yr_NT,\n" + 
            "                  m.CASHBOOK_MONTH           AS csh_bk_mnth_NT,\n" + 
            "                  t.sub_ledger_code               AS acc_no_NT,\n" + 
            "                  SUM(t.AMOUNT)           AS jrnl_dr\n" + 
            "                FROM FAS_JOURNAL_MASTER m,FAS_JOURNAL_TRANSACTION t\n" + 
            "                WHERE m.accounting_unit_id=t.accounting_unit_id\n" + 
            "                and m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID\n" + 
            "                and m.CASHBOOK_YEAR=t.CASHBOOK_YEAR\n" + 
            "                and m.CASHBOOK_MONTH=t.CASHBOOK_MONTH\n" + 
            "                and m.VOUCHER_NO=t.VOUCHER_NO\n" + 
            "                and m.journal_status='L'\n" + 
            "                and m.accounting_unit_id    = " +cboAcc_UnitCode+ 
            "                AND m.accounting_for_office_id=" +cboOffice_code+ 
            "                AND m.cashbook_month          = " +cboCashBook_Month+ 
            "                AND m.cashbook_year           =" +cboCashBook_Year+ 
            "                and t.sub_ledger_code=" +cmbBankAccNo+ 
            "                and t.CR_DR_INDICATOR='DR'\n" + 
            "                GROUP BY m.ACCOUNTING_UNIT_ID,\n" + 
            "                  m.ACCOUNTING_FOR_OFFICE_ID,\n" + 
            "                  m.CASHBOOK_YEAR,\n" + 
            "                  m.CASHBOOK_MONTH,\n" + 
            "                  t.sub_ledger_code\n" + 
            "              )d\n" + 
            "            ON a.acc_u_id_T    =d.acc_u_id_NT\n" + 
            "            AND a.acc_off_id_T =d.acc_off_id_NT\n" + 
            "            AND a.csh_bk_yr_T  =d.csh_bk_yr_NT\n" + 
            "            AND a.csh_bk_mnth_T=d.csh_bk_mnth_NT\n" + 
            "            AND a.acc_no_T     =d.acc_no_NT\n" + 
            "            )a\n" + 
        //    "          LEFT OUTER JOIN\n" + 
        "          full OUTER JOIN\n" + 
            "            (\n" + 
            "\n" + 
            "            select sum(dr_T1)as dr_T1,acc_u_id_T1,acc_off_id_T1,csh_bk_yr_T1,csh_bk_mnth_T1,acc_no_T1 from\n" + 
            "(SELECT ACCOUNTING_UNIT_ID AS acc_u_id_T1,\n" + 
            "              ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T1,\n" + 
            "              CASHBOOK_YEAR            AS csh_bk_yr_T1,\n" + 
            "              CASHBOOK_MONTH           AS csh_bk_mnth_T1,\n" + 
            "              ACCOUNT_NO               AS acc_no_T1,\n" + 
            "              SUM(DR_AMOUNT)           AS dr_T1\n" + 
            "            FROM FAS_BRS_TRANSACTION\n" + 
            "            WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
            "            AND accounting_for_office_id=" +cboOffice_code+ 
            "            AND cashbook_month          =" +cboCashBook_Month+ 
            "            AND cashbook_year           =" +cboCashBook_Year+ 
            "            AND ACCOUNT_NO              =" +cmbBankAccNo+ 
            "            AND doc_type LIKE 'FT%'\n" + 
            "            GROUP BY ACCOUNTING_UNIT_ID,\n" + 
            "              ACCOUNTING_FOR_OFFICE_ID,\n" + 
            "              CASHBOOK_YEAR,\n" + 
            "              CASHBOOK_MONTH,\n" + 
            "              ACCOUNT_NO\n" + 
            "              union all\n" + 
            "              SELECT ACCOUNTING_UNIT_ID AS acc_u_id_T1,\n" + 
            "              ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T1,\n" + 
            "              CASHBOOK_YEAR            AS csh_bk_yr_T1,\n" + 
            "              CASHBOOK_MONTH           AS csh_bk_mnth_T1,\n" + 
            "              ACCOUNT_NO               AS acc_no_T1,\n" + 
            "              SUM(DR_AMOUNT)           AS dr_T1\n" + 
            "            FROM fas_brs_transaction_noentry\n" + 
            "            WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
            "            AND accounting_for_office_id=" +cboOffice_code+ 
            "            AND cashbook_month          =" +cboCashBook_Month+ 
            "            AND cashbook_year           =" +cboCashBook_Year+ 
            "            AND ACCOUNT_NO              =" +cmbBankAccNo+ 
            "            AND doc_type LIKE 'FT%'\n" + 
            "            GROUP BY ACCOUNTING_UNIT_ID,\n" + 
            "              ACCOUNTING_FOR_OFFICE_ID,\n" + 
            "              CASHBOOK_YEAR,\n" + 
            "              CASHBOOK_MONTH,\n" + 
            "              ACCOUNT_NO)group by acc_u_id_T1,acc_off_id_T1,csh_bk_yr_T1,csh_bk_mnth_T1,acc_no_T1\n" + 
            "		)c\n" + 
            "          ON A.acc_u_id_T     =c.acc_u_id_T1\n" + 
            "          AND A.acc_off_id_T  = c.acc_off_id_T1\n" + 
            "          AND A.csh_bk_yr_T   = c.csh_bk_yr_T1\n" + 
            "          AND A.csh_bk_mnth_T = c.csh_bk_mnth_T1\n" + 
            "          AND A.acc_no_T      = c.acc_no_T1\n" + 
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
            "           SELECT ( SUM(cr_amount) - SUM(dr_amount) ) AS amt_t2_A\n" + 
            "    FROM FAS_BRS_TRANSACTION_NOENTRY\n" + 
            "    WHERE accounting_unit_id               =" +cboAcc_UnitCode+ 
            "    AND accounting_for_office_id           =" +cboOffice_code+ 
            "   AND ((cashbook_year                     <"+cboCashBook_Year+" and cashbook_month<=12) or (cashbook_year="+cboCashBook_Year+" and cashbook_month<="+cboCashBook_Month+"))\n" + 
            "    AND ACCOUNT_NO                         = " +cmbBankAccNo+ 
            "    AND TWAD_OR_NON_TWAD                   ='T'\n" + 
            "    AND doc_type                          IN ('CR', 'BR','FR by HO', 'FR by Office')\n" + 
            "    UNION ALL\n" + 
            "    SELECT ( SUM(cr_amount) - SUM(dr_amount) ) AS amt_t2_A\n" + 
            "    FROM FAS_BRS_TRANSACTION\n" + 
            "    WHERE accounting_unit_id                =" +cboAcc_UnitCode+ 
            "    AND Accounting_For_Office_Id            =" +cboOffice_code+ 
            "    and PASSBOOK_DATE>('"+totalyear+"')\n" + 
            "    and (01||'-'||Cashbook_Month||'-'||cashbook_year)<=('"+totalyear+"')\n" + 
            "    AND ((cashbook_year                     <"+cboCashBook_Year+" and cashbook_month<=12) or (cashbook_year="+cboCashBook_Year+" and cashbook_month<="+cboCashBook_Month+"))\n" + 
            "    AND Account_No                          =" +cmbBankAccNo+ 
            "    AND Twad_Or_Non_Twad                    ='T'\n" + 
            "    AND doc_type                           IN ('CR', 'BR','FR by HO', 'FR by Office')   \n" + 
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
            "        trans_desc,\n" + 
            "	"+cboCashBook_Year+" as cbyear,\n" + 
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
            "          AND twad_or_non_twad            = 'NT'\n" + 
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
            "          AND twad_or_non_twad            = 'NT'\n" + 
            "          AND (CLEARED_BASED_ON_FOLLOWUP='Y' " +
           //changed the condition by adding the is it clearing entry and date <= on 06/10/2016 sss pjt rajapalayam
            "		and  IS_IT_CLEARING_ENTRY ='Y' and clearence_date <= ('"+totalyear+"'))\n" + 
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
            "      AND accounting_for_office_id    =" +cboOffice_code+ 
            "      AND ((cashbook_year             <" +cboCashBook_Year+ 
            "      AND cashbook_month             <=12)\n" + 
            "      OR (cashbook_year               =" +cboCashBook_Year+ 
            "      AND cashbook_month             <="+cboCashBook_Month+"))\n" + 
            "      AND ACCOUNT_NO                  = " +cmbBankAccNo+ 
            "      AND twad_or_non_twad            = 'NT'\n" + 
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
            "      AND twad_or_non_twad            = 'NT'\n" + 
            "     AND (CLEARED_BASED_ON_FOLLOWUP='Y' " +
            "	  and  IS_IT_CLEARING_ENTRY ='Y' and clearence_date <=('"+totalyear+"'))\n" + 
            "      \n" + 
            "      GROUP BY transaction_type,\n" + 
            "        accounting_unit_id,\n" + 
            "        accounting_for_office_id,\n" + 
            "        cashbook_year,\n" + 
            "        ACCOUNT_NO\n" + 
            "        )t2_c\n" + 
            "LEFT OUTER JOIN\n" + 
            "  ( SELECT trans_code,trans_short_desc as trans_desc FROM fas_brs_transaction_type\n" + 
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
		}
			System.out.println("sql_rep::;"+sql_rep);
			if(command.equalsIgnoreCase("Freezed_brs")){
				qry="SELECT p1.ACCOUNTING_UNIT_ID, " +
				"  unit.ACCOUNTING_UNIT_NAME, " +
				"  office.OFFICE_NAME, " +
				"  ACCOUNTING_FOR_OFFICE_ID, " +
				"  PASS_SHEET_YEAR, " +
				"  PASS_SHEET_MONTH, " +
				"  TWAD_OR_NON_TWAD, " +
				"  ACCOUNT_NO, " +
				"  p1.BANK_ID, " +
 				" (select BANK_NAME from FAS_BANK_LIST Bname where p1.BANK_ID=Bname.BANK_ID )as BANK_NAME,"+  
                " (select BRANCH_NAME from FAS_MST_BANK_BRANCHES branch where p1.BANK_ID=branch.BANK_ID and p1.BRANCH_ID=branch.BRANCH_ID)as BRANCH_NAME,"+
				"  S1, " +
				"  S2, " +
				"  S3, " +
				"  S4, " +
				"  S5, " +
				"  S5A, " +
				"  S5B, " +
				"  S5C, " +
				"  S6 " +
				" FROM FAS_BRS_PART1 p1, " +
				"  FAS_MST_ACCT_UNITS unit, " +
				"  COM_MST_OFFICES office " +
				" WHERE p1.ACCOUNTING_UNIT_ID    = " +cboAcc_UnitCode+
				" AND ACCOUNTING_FOR_OFFICE_ID   = " +cboOffice_code+
				" AND PASS_SHEET_YEAR            = " +cboCashBook_Year+
				" AND PASS_SHEET_MONTH           = " +cboCashBook_Month+
				" AND ACCOUNT_NO                 = " +cmbBankAccNo+
				" AND p1.ACCOUNTING_UNIT_ID      =unit.ACCOUNTING_UNIT_ID " +
				" AND p1.ACCOUNTING_FOR_OFFICE_ID=office.OFFICE_ID " ;
				
				//System.out.println(">>>> "+qry);

				reportFile = new File(getServletContext().getRealPath(
				"/org/FAS/FAS1/BRS/jaspers/Part_Jasper/BRS_Report_1_Report.jasper"));
				map.put("sql", qry);
				
				if (!reportFile.exists())
					throw new JRRuntimeException(
							"File J not found. The report design must be compiled first.");

				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());
				//System.out.println("last_date_two::::"+last_date_two);

				map.put("UnitId", cboAcc_UnitCode);
				map.put("OfficeId", cboOffice_code);
				map.put("cbyear", cboCashBook_Year);
				map.put("cbmonth", cboCashBook_Month);
				map.put("accNo", cmbBankAccNo);
				map.put("month", month);
				map.put("bankName", bankName);
				map.put("branchName", branchName);
	            map.put("UnitName", UnitName);
	            map.put("last_date_one", totalyear);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						jasperReport, map, con);
				System.out.println("upto");
				String rtype = "PDF";// request.getParameter("cmbReportType");
				System.out.println(rtype);
				 if (rtype.equalsIgnoreCase("PDF")) {
					System.out.println(rtype);
					byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
					System.out.println("Length  " + buf.length);
					response.setContentType("application/pdf");
					response.setContentLength(buf.length);
				

					response.setHeader("Content-Disposition",
							"attachment;filename=\"BRS_Part1.pdf\"");
					OutputStream out = response.getOutputStream();
					out.write(buf, 0, buf.length);
					out.close();
				}
				
			}
			else if(command.equalsIgnoreCase("printFunc")){
				try{
				//System.out.println("dhana::::");
			reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/BRS_Report_1.jasper"));
			map.put("sql", sql_rep);
			
			if (!reportFile.exists())
				throw new JRRuntimeException(
						"File J not found. The report design must be compiled first.");

			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObject(reportFile.getPath());
			//System.out.println("last_date_two::::"+last_date_two);

			map.put("UnitId", cboAcc_UnitCode);
			map.put("OfficeId", cboOffice_code);
			map.put("cbyear", cboCashBook_Year);
			map.put("cbmonth", cboCashBook_Month);
			map.put("accNo", cmbBankAccNo);
			map.put("month", month);
            map.put("UnitName","( "+cboAcc_UnitCode+" ) "+ UnitName);
            map.put("last_date_one", totalyear);
            
		    
			
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, map, con);
			System.out.println("upto");
			String rtype = "PDF";// request.getParameter("cmbReportType");
			System.out.println(rtype);
			 if (rtype.equalsIgnoreCase("PDF")) {
				System.out.println(rtype);
				byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
				System.out.println("Length  " + buf.length);
				response.setContentType("application/pdf");
				response.setContentLength(buf.length);
				

				response.setHeader("Content-Disposition",
						"attachment;filename=\"BRS_Part1.pdf\"");
				OutputStream out = response.getOutputStream();
				out.write(buf, 0, buf.length);
				out.close();
			}
			}
			 catch (Exception ex) {
					String connectMsg = "Could not create the report "
							+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
					System.out.println(connectMsg);
				}
			
			}
			
			else if(command.equalsIgnoreCase("f_brs_check"))
			{
				response.setContentType(CONTENT_TYPE);
				xml="<response><command>f_brs_check</command>";
				
				PrintWriter output = response.getWriter();
				String mode_id="";
				int insertCount=0,bank_id=0,branch_id=0;
			  double amt_2t_sum=0.0;
				try{
					PreparedStatement psta=con.prepareStatement(sql_rep);
		               ResultSet rset=psta.executeQuery();
			               while(rset.next())
			               {
			            	   amt_2t_sum+=rset.getDouble("amt_2t");
			               }
				}
				catch(Exception e4)
				{
				System.out.println("excep in sum::::"+e4);	
				}
				try{
					String ac="SELECT bank_ac_no,AC_OPERATIONAL_MODE_ID,bank_id,branch_id From Fas_Mst_Bank_Balance WHERE accounting_unit_id = "+cboAcc_UnitCode+" and status='Y' and bank_ac_no="+cmbBankAccNo;
					PreparedStatement pss=con.prepareStatement(ac);
					ResultSet rss=pss.executeQuery();
					if(rss.next())
					{
						mode_id=rss.getString("AC_OPERATIONAL_MODE_ID");//opr
						bank_id=rss.getInt("bank_id");//bank_id
						branch_id=rss.getInt("branch_id");//branch_id
					}
				}
				catch (Exception e) {
					System.out.println("Error in mode_id -->" + e);
				}
				int checkfre=0;
				 try{
		               PreparedStatement pss=con.prepareStatement(sql_rep);
		               ResultSet rss=pss.executeQuery();
		               //Lakshmi 6Nov13
		               
			               if(rss.next())
			               {
			            	   
			            	   
					            	  /* PreparedStatement psta=con.prepareStatement("delete from FAS_BRS_PART1 where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code+" and PASS_SHEET_YEAR="+cboCashBook_Year+" and PASS_SHEET_MONTH="+cboCashBook_Month+" and ACCOUNT_NO="+cmbBankAccNo);
					            	   insertCount= psta.executeUpdate();*/
			            	   PreparedStatement psta=con.prepareStatement("select 'X' from FAS_BRS_PART1 where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code+" and PASS_SHEET_YEAR="+cboCashBook_Year+" and PASS_SHEET_MONTH="+cboCashBook_Month+" and ACCOUNT_NO="+cmbBankAccNo);
			            	  ResultSet rsss= psta.executeQuery();
					            	   //if(insertCount>0)
			            	   if(rsss.next()){
			            		   checkfre=1;
			            	   }
					               if(checkfre==1)
					               {
					            	   con.commit();
										con.setAutoCommit(true);
										//sendMessage(response,"Already Part-1 Frozen  ","ok");
							              xml=xml+"<flag>Already_Frozen</flag>";

					               }
					               else
					               {
							              xml=xml+"<flag>success</flag>";

					               }
			            	
			            	   
			               }
			              
			            
			               
		                }
		                catch(Exception ee)
		                {
		                	System.out.println("exception in fetching query::::"+ee);
		                }
				 xml = xml + "</response>";
					output.println(xml); 
					System.out.println("xml=====>"+xml);
			}
			else if(command.equalsIgnoreCase("f_brs"))
			{
				
				response.setContentType(CONTENT_TYPE);
				PrintWriter output = response.getWriter();
				String mode_id="";
				int insertCount=0,bank_id=0,branch_id=0;
			  double amt_2t_sum=0.0;
				try{
					PreparedStatement psta=con.prepareStatement(sql_rep);
		               ResultSet rset=psta.executeQuery();
			               while(rset.next())
			               {
			            	   amt_2t_sum+=rset.getDouble("amt_2t");
			               }
				}
				catch(Exception e4)
				{
				System.out.println("excep in sum::::"+e4);	
				}
				try{
					String ac="SELECT bank_ac_no,AC_OPERATIONAL_MODE_ID,bank_id,branch_id From Fas_Mst_Bank_Balance WHERE accounting_unit_id = "+cboAcc_UnitCode+" and status='Y' and bank_ac_no="+cmbBankAccNo;
					PreparedStatement pss=con.prepareStatement(ac);
					ResultSet rss=pss.executeQuery();
					if(rss.next())
					{
						mode_id=rss.getString("AC_OPERATIONAL_MODE_ID");//opr
						bank_id=rss.getInt("bank_id");//bank_id
						branch_id=rss.getInt("branch_id");//branch_id
					}
				}
				catch (Exception e) {
					System.out.println("Error in mode_id -->" + e);
				}
				int checkfre=0;
				 try{
		               PreparedStatement pss=con.prepareStatement(sql_rep);
		               ResultSet rss=pss.executeQuery();
		               //Lakshmi 6Nov13
		               
			               if(rss.next())
			               {
			            	   
			            	   
					            	  /* PreparedStatement psta=con.prepareStatement("delete from FAS_BRS_PART1 where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code+" and PASS_SHEET_YEAR="+cboCashBook_Year+" and PASS_SHEET_MONTH="+cboCashBook_Month+" and ACCOUNT_NO="+cmbBankAccNo);
					            	   insertCount= psta.executeUpdate();*/
			            	   PreparedStatement psta=con.prepareStatement("select 'X' from FAS_BRS_PART1 where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cboOffice_code+" and PASS_SHEET_YEAR="+cboCashBook_Year+" and PASS_SHEET_MONTH="+cboCashBook_Month+" and ACCOUNT_NO="+cmbBankAccNo);
			            	  ResultSet rsss= psta.executeQuery();
					            	   //if(insertCount>0)
			            	   if(rsss.next()){
			            		   checkfre=1;
			            	   }else{
					            	 PreparedStatement  pss1=con.prepareStatement("insert into FAS_BRS_PART1(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," +
					            	   		"PASS_SHEET_YEAR,PASS_SHEET_MONTH,ACCOUNT_NO,S1,S2,S3,S4,S5,S5A,S5B,S5C,S6," +
					            	   		"UPDATED_BY_USER_ID,UPDATED_DATE,BANK_ID,BRANCH_ID) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
					            	   pss1.setInt(1,cboAcc_UnitCode);
					            	   pss1.setInt(2,cboOffice_code);
					            	   pss1.setInt(3,cboCashBook_Year);
					            	   pss1.setInt(4,cboCashBook_Month);
					            	   pss1.setLong(5,cmbBankAccNo);
					            	   pss1.setDouble(6,rss.getDouble("OB_PART1"));
					            	   pss1.setDouble(7,rss.getDouble("cr"));
					            	   pss1.setDouble(8,rss.getDouble("total1"));
					            	   pss1.setDouble(9,rss.getDouble("dr"));
					            	   pss1.setDouble(10,rss.getDouble("total2"));//s5
					            	   pss1.setDouble(11,rss.getDouble("amt_t2_A"));
					            	   pss1.setDouble(12,rss.getDouble("amt_2T_clos_bal"));
					            	   pss1.setDouble(13,amt_2t_sum);//amt_2t
					            	   pss1.setDouble(14,rss.getDouble("total_t2"));
					            	   pss1.setString(15,update_user);
					                   pss1.setTimestamp(16,ts);
					                   pss1.setInt(17,bank_id);
					                   pss1.setInt(18,branch_id);
					                   jk=pss1.executeUpdate();
					               
					                   System.out.println("value jk:::"+jk);
					                   if(jk>0)
						               {
//						            	   con.commit();
//											con.setAutoCommit(true);
											//sendMessage(response,"Records Inserted Successfully  ","ok");
								              xml="<response><command><flag>success</flag></command>";

						               }
						               else
						               {
						            	   con.rollback();
											con.setAutoCommit(true);
						            	  // sendMessage(response,"Records Not Inserted into Part-1 ","ok"); 
								              xml="<response><command><flag>failure</flag></command>";

						               }
			               }
					               if(checkfre==1)
					               {
					            	   con.commit();
										con.setAutoCommit(true);
										//sendMessage(response,"Already Part-1 Frozen  ","ok");
							              xml="<response><command><flag>Already_Frozen</flag></command>";

					               }
			            	
			            	   
			               }
			              
			            
			               
		                }
		                catch(Exception ee)
		                {
		                	System.out.println("exception in fetching query::::"+ee);
		                }
				 xml = xml + "</response>";
					output.println(xml); 
					System.out.println("xml=====>"+xml);
			}
			
			
		} catch (Exception ex) {
			System.out.println("exception in insertion::::"+ex);
        	
			sendMessage(response,"Records Not Inserted ............ "+ ex, "ok");
		}
	}
	public void doGet(HttpServletRequest request, HttpServletResponse response)
	throws ServletException, IOException {
		  PrintWriter out = response.getWriter();
	      response.setHeader("cache-control","no-cache");
	      String CONTENT_TYPE = "text/xml; charset=windows-1252";
	      response.setContentType(CONTENT_TYPE);
		System.out.println("comes for get method:");
		//PrintWriter out = response.getWriter();
		Connection con = null;
		
		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");
				return;
			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
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
		  HttpSession session=request.getSession(false);
	        try
	        {
	            
	            if(session==null)
	            {
	                System.out.println(request.getContextPath()+"/index.jsp");
	                response.sendRedirect(request.getContextPath()+"/index.jsp");
	                return;
	            }
	            System.out.println(session);
	                
	        }catch(Exception e)
	        {
	        System.out.println("Redirect Error :"+e);
	        } 
		 String update_user=(String)session.getAttribute("UserId");
         long l=System.currentTimeMillis();
         Timestamp ts=new Timestamp(l);	
        PreparedStatement ps2=null;   
    	PreparedStatement pss1=null;
        ResultSet rs2=null,rs_one=null;
        String cmd="",xml=null;
        int count=0;
        int cmbAcc_UnitCode=0;
    	try
      	{
      		cmd=request.getParameter("command");
      		cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
      		
      	}catch (Exception e) {
			e.printStackTrace();
		}
      	System.out.println("cmd---------"+cmd);
		if(cmd.equalsIgnoreCase("LoadBankAccountNumber"))
        {
			
        		String sql =
        	  	"       select *             													\n"+   						
        	  	"		from 																	\n"+	
        	  	"		(																		\n"+	
        	  	"			select																\n"+
        	  	"				bank_id,														\n"+
        	  	"				BRANCH_ID,														\n"+
        	  	"				bank_ac_no, 													\n"+
        	  	"				AC_OPERATIONAL_MODE_ID,                                         \n"+
        	  	"				trim(AC_OPERATIONAL_MODE_ID)||'-'||trim(bank_ac_no) as acc_no			    \n"+  
        	  	"			from																\n"+
        	  	"				fas_mst_bank_balance												\n"+
        	  	"			where																\n"+		
        	  	"				accounting_unit_id = ? and AC_OPERATIONAL_MODE_ID in('COL','SCH')    	\n"+
        	  	"		)X																		\n"+			
        	  	"		left outer join															\n"+
        	  	"		(																		\n"+		 
        	  	"				select bank_id as y_bank_id ,trim(BANK_NAME) as y_bank_name from fas_bank_list	\n"+	
        	  	"		)Y																		\n"+
        	  	"    on 																		\n"+
        	  	"      X.bank_id  = Y.y_bank_id													\n"+
        	  	"    left outer join 															\n"+
        	  	"    (																			\n"+
        	  	"      select  BANK_ID as z_bank_id, BRANCH_ID as z_BRANCH_ID ,trim(BRANCH_NAME) as z_BRANCH_NAME from fas_mst_bank_branches	\n"+                   
        	  	"    )Z                                    										\n"+
        	  	"	 on  																		\n"+
        	  	"      X.bank_id  = Z.z_bank_id  and											\n"+ 
        	  	"      X.BRANCH_ID = Z.z_branch_id	 order by bank_id,bank_ac_no,AC_OPERATIONAL_MODE_ID	\n"+
        	  	" 																			      ";
        		
       //   System.out.println("sql:"+sql);
	            try
	            {
		              ps2=con.prepareStatement(sql);
		              ps2.setInt(1,cmbAcc_UnitCode);
		              rs2=ps2.executeQuery();
		           
		              
		              xml="<response><command>LoadBankAccountNumber</command>";
		              
		              /** Count How many Records are available  */
		              while (rs2.next()) 
		              {
		                 xml=xml+ "<acc_no>"+ rs2.getString("bank_ac_no") +"</acc_no>";	 
		                 xml=xml+ "<bank_id>"+ rs2.getString("bank_id") +"</bank_id>";  
		                 xml=xml+ "<branch_id>"+ rs2.getString("branch_id") +"</branch_id>";                 
		                 xml=xml+ "<opr_mode>"+ rs2.getString("AC_OPERATIONAL_MODE_ID") +"</opr_mode>";                 
		               //  xml=xml+ "<acc_desc>"+ rs2.getString("acc_no") +"-"+ rs2.getString("y_bank_name") +"</acc_desc>";
		                 xml=xml+ "<acc_desc>"+ rs2.getString("AC_OPERATIONAL_MODE_ID")+"-"+rs2.getString("bank_ac_no")+"-"+rs2.getString("y_bank_name") +"</acc_desc>";
		                 xml=xml+ "<bank_name>"+ rs2.getString("y_bank_name") +"</bank_name>";      			                 
		                 xml=xml+ "<branch_name>"+ rs2.getString("z_BRANCH_NAME") +"</branch_name>";
		                 count++;
		              }
		              
		              if(count==0) {
		                  xml=xml+"<flag>NoData</flag>";
		              }
		              else{                
		                  xml=xml+"<flag>success</flag>";
		              }              
	           }
		       catch(Exception e) 
		       {
		              System.out.println("Exception in assigning..."+e);
		              xml=xml+"<flag>failure</flag>";
		       }
		       xml = xml + "</response>";
				System.out.println(xml);
				out.println(xml);
     }
     else if(cmd.equalsIgnoreCase("checkPB"))
		{
    	 int pb=0;
    	 int unitcode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int offCode=Integer.parseInt(request.getParameter("cmbOffice_code"));
			int passYear=Integer.parseInt(request.getParameter("cboCashBook_Year"));
			int passMonth=Integer.parseInt(request.getParameter("cboCashBook_Month"));
			long accNo=Long.parseLong(request.getParameter("cmbBankAccNo"));
			
 		String sql ="SELECT coalesce(PASSBOOK_BALANCE,NULL,0,PASSBOOK_BALANCE) AS pb "+
					"	FROM FAS_BRS_MASTER"+
					"	WHERE accounting_unit_id    = "+unitcode+
					"	AND accounting_for_office_id="+offCode+
					"	AND cashbook_month          = "+passMonth+
					"	AND cashbook_year           ="+passYear+
					"	AND ACCOUNT_NO              = "+accNo;
 		
//   System.out.println("sql:"+sql);
	         try
	         {
	              ps2=con.prepareStatement(sql);
	              rs2=ps2.executeQuery();
	              xml="<response><command>checkPB</command>";
	             
	              while (rs2.next()) 
	              {
	                pb=rs2.getInt("pb"); 
	                count++;
	              }
	              
	              if(count==0) {
	            	  xml=xml+"<flag>NoData</flag>";
	              }
	              else{      
	            	  if(pb==0)
	            	  {
	            		  xml=xml+"<flag>zero</flag>";
	            	  }
	            	  else
	                  xml=xml+"<flag>success</flag>";
	              }              
	         }
	       catch(Exception e) 
	       {
	              System.out.println("Exception in assigning..."+e);
	              xml=xml+"<flag>failure</flag>";
	       }
	       xml = xml + "</response>";
			System.out.println(xml);
			out.println(xml);

		}
		else if(cmd.equalsIgnoreCase("chkRec"))
		{
			  xml="<response><command>chkRec</command>";
			int isRecord=0;
			int unitcode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int offCode=Integer.parseInt(request.getParameter("cmbOffice_code"));
			int passYear=Integer.parseInt(request.getParameter("cboCashBook_Year"));
			int passMonth=Integer.parseInt(request.getParameter("cboCashBook_Month"));
			long accNo=Long.parseLong(request.getParameter("cmbBankAccNo"));
			
			String ss="SELECT COUNT(*) as count_one "+
					" FROM Fas_Brs_Part1 "+
					" WHERE Accounting_Unit_Id    = "+unitcode+
					" AND Accounting_For_Office_Id="+offCode+
					" AND Pass_Sheet_Year         ="+passYear+
					" AND Pass_Sheet_Month        ="+passMonth+
					" AND ACCOUNT_NO              ="+accNo;
			System.out.println("ss:"+ss);
			try{
				
				  xml=xml+"<flag>success</flag>";
			PreparedStatement ps_one=con.prepareStatement(ss);
			rs_one=ps_one.executeQuery();
			while(rs_one.next())
			{
				isRecord=rs_one.getInt("count_one");
			}
			if(isRecord>0)
			{
				  xml=xml+"<proceed>stop</proceed>";
			}
			else
			{
				  xml=xml+"<proceed>start</proceed>";
			}
				
			}
			 catch(Exception e) 
		       {
		              System.out.println("Exception in onload..."+e);
		              xml=xml+"<flag>failure</flag>";
		       }
			
			  xml = xml + "</response>";
				System.out.println(xml);
				out.println(xml);
		}
		/* else if(cmd.equalsIgnoreCase("f_brs"))
		{
			int insertCount=0;
			int unitcode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int offCode=Integer.parseInt(request.getParameter("cmbOffice_code"));
			int passYear=Integer.parseInt(request.getParameter("cboCashBook_Year"));
			int passMonth=Integer.parseInt(request.getParameter("cboCashBook_Month"));
			long accNo=Long.parseLong(request.getParameter("cmbBankAccNo"));
			
			  xml="<response><command>free_brs</command>";
				
              String sql="SELECT rownum AS rowno1,\n" + 
              "  OFFICE_NAME,\n" + 
              "  OB_PART1,\n" + 
              "  cr,cr_final,jrnl_dr,jrnl_cr,\n" + 
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
              "    cr,cr_final,jrnl_dr,jrnl_cr,\n" + 
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
              "    (DECODE(total,NULL,0,total)+amt_t2_A+amt_2T_clos_bal) AS total_t2\n" + 
              "  FROM\n" + 
              "    (SELECT OFFICE_NAME,\n" + 
              "      acc_u_id_T,\n" + 
              "      acc_off_id_T,\n" + 
              "      csh_bk_yr_T,\n" + 
              "      csh_bk_mnth_T,\n" + 
              "      acc_no_T,\n" + 
              "      OB_PART1,\n" + 
              "      cr,cr_final,jrnl_dr,jrnl_cr,\n" + 
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
              "        DECODE(cr,NULL,0,cr)             AS cr,cr_final,jrnl_dr,jrnl_cr,\n" + 
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
              "          cr,cr_final,jrnl_dr,jrnl_cr,\n" + 
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
              "            WHERE accounting_unit_id    = " +unitcode+ 
              "            AND accounting_for_office_id=" +offCode+ 
              "            AND cashbook_month          = " +passMonth+ 
              "            AND cashbook_year           =" +passYear+ 
              "            AND ACCOUNT_NO              = " +accNo+
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
              "            cr_T1 AS cr,\n" + 
              "            dr_T1 AS dr,cr_final,jrnl_dr,jrnl_cr\n" + 
              "          FROM\n" + 
              "            (\n" + 
              "            SELECT a.acc_u_id_T,\n" + 
              "              a.acc_off_id_T,\n" + 
              "              a.csh_bk_yr_T,\n" + 
              "              a.csh_bk_mnth_T,\n" + 
              "              a.acc_no_T,\n" + 
              "              decode(e.jrnl_cr,null,0,e.jrnl_cr)as jrnl_cr,\n" + 
              "              (DECODE(a.cramt,NULL,0,a.cramt)      +DECODE(c.cr_notrn,NULL,0,cr_notrn))                                                    AS cr_final,\n" + 
              "              (DECODE(b.jour_amt,NULL,0,b.jour_amt)+DECODE(d.jrnl_notrn,NULL,0,d.jrnl_notrn))                                              AS jrnl_dr,\n" + 
              "              (DECODE(a.cramt,NULL,0,a.cramt)+DECODE(b.jour_amt,NULL,0,b.jour_amt)+DECODE(c.cr_notrn,NULL,0,cr_notrn)+DECODE(d.jrnl_notrn,NULL,0,d.jrnl_notrn)+decode(e.jrnl_cr,null,0,e.jrnl_cr))AS cr_T1\n" + 
              "              \n" + 
              "            FROM\n" + 
              "              (SELECT ACCOUNTING_UNIT_ID AS acc_u_id_T,\n" + 
              "                ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T,\n" + 
              "                CASHBOOK_YEAR            AS csh_bk_yr_T,\n" + 
              "                CASHBOOK_MONTH           AS csh_bk_mnth_T,\n" + 
              "                ACCOUNT_NO               AS acc_no_T,\n" + 
              "                SUM(CR_AMOUNT)           AS cramt\n" + 
              "              FROM FAS_BRS_TRANSACTION\n" + 
              "              WHERE accounting_unit_id    = " +unitcode+ 
              "              AND accounting_for_office_id=" +offCode+ 
              "              AND cashbook_month          = " +passMonth+ 
              "              AND cashbook_year           =" +passYear+ 
              "              AND ACCOUNT_NO              = " +accNo+
              "              AND doc_type                !='J'\n" + 
              "              GROUP BY ACCOUNTING_UNIT_ID,\n" + 
              "                ACCOUNTING_FOR_OFFICE_ID,\n" + 
              "                CASHBOOK_YEAR,\n" + 
              "                CASHBOOK_MONTH,\n" + 
              "                ACCOUNT_NO\n" + 
              "              )a\n" + 
              "            FULL OUTER JOIN\n" + 
              "              (SELECT ACCOUNTING_UNIT_ID AS acc_u_id_NT,\n" + 
              "                ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_NT,\n" + 
              "                CASHBOOK_YEAR            AS csh_bk_yr_NT,\n" + 
              "                CASHBOOK_MONTH           AS csh_bk_mnth_NT,\n" + 
              "                ACCOUNT_NO               AS acc_no_NT,\n" + 
              "                SUM(DR_AMOUNT)           AS jour_amt\n" + 
              "              FROM FAS_BRS_TRANSACTION\n" + 
              "              WHERE accounting_unit_id    = " +unitcode+ 
              "              AND accounting_for_office_id=" +offCode+ 
              "              AND cashbook_month          =" +passMonth+ 
              "              AND cashbook_year           =" +passYear+ 
              "              AND ACCOUNT_NO              = " +accNo+
              "              AND doc_type                ='J'\n" + 
              "              GROUP BY ACCOUNTING_UNIT_ID,\n" + 
              "                ACCOUNTING_FOR_OFFICE_ID,\n" + 
              "                CASHBOOK_YEAR,\n" + 
              "                Cashbook_Month,\n" + 
              "                ACCOUNT_NO\n" + 
              "              )b\n" + 
              "            ON a.acc_u_id_T    =b.acc_u_id_NT\n" + 
              "            AND a.acc_off_id_T =b.acc_off_id_NT\n" + 
              "            AND a.csh_bk_yr_T  =b.csh_bk_yr_NT\n" + 
              "            AND a.csh_bk_mnth_T=b.csh_bk_mnth_NT\n" + 
              "            AND a.acc_no_T     =b.acc_no_NT\n" + 
              "            FULL OUTER JOIN\n" + 
              "              (SELECT ACCOUNTING_UNIT_ID                     AS acc_u_id_T,\n" + 
              "                ACCOUNTING_FOR_OFFICE_ID                     AS acc_off_id_T,\n" + 
              "                CASHBOOK_YEAR                                AS csh_bk_yr_T,\n" + 
              "                CASHBOOK_MONTH                               AS csh_bk_mnth_T,\n" + 
              "                ACCOUNT_NO                                   AS acc_no_T,\n" + 
              "                DECODE(SUM(CR_AMOUNT),NULL,0,SUM(CR_AMOUNT)) AS cr_notrn\n" + 
              "              FROM FAS_BRS_TRANSACTION_NOENTRY\n" + 
              "              WHERE accounting_unit_id    = " +unitcode+ 
              "              AND accounting_for_office_id=" +offCode+ 
              "              AND cashbook_month          = " +passMonth+ 
              "              AND cashbook_year           =" +passYear+ 
              "              AND ACCOUNT_NO              = " +accNo+
              "               AND doc_type                !='J'\n" + 
              "              GROUP BY ACCOUNTING_UNIT_ID,\n" + 
              "                ACCOUNTING_FOR_OFFICE_ID,\n" + 
              "                CASHBOOK_YEAR,\n" + 
              "                CASHBOOK_MONTH,\n" + 
              "                ACCOUNT_NO\n" + 
              "              )c\n" + 
              "            ON a.acc_u_id_T    =c.acc_u_id_T\n" + 
              "            AND a.acc_off_id_T =c.acc_off_id_T\n" + 
              "            AND a.csh_bk_yr_T  =c.csh_bk_yr_T\n" + 
              "            AND a.csh_bk_mnth_T=c.csh_bk_mnth_T\n" + 
              "            AND a.acc_no_T     =c.acc_no_T\n" + 
              "            FULL OUTER JOIN\n" + 
              "              (SELECT ACCOUNTING_UNIT_ID AS acc_u_id_NT,\n" + 
              "                ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_NT,\n" + 
              "                CASHBOOK_YEAR            AS csh_bk_yr_NT,\n" + 
              "                CASHBOOK_MONTH           AS csh_bk_mnth_NT,\n" + 
              "                ACCOUNT_NO               AS acc_no_NT,\n" + 
              "                SUM(DR_AMOUNT)           AS jrnl_notrn\n" + 
              "              FROM FAS_BRS_TRANSACTION_NOENTRY\n" + 
              "              WHERE accounting_unit_id    = " +unitcode+ 
              "              AND accounting_for_office_id=" +offCode+ 
              "              AND cashbook_month          =" +passMonth+ 
              "              AND cashbook_year           =" +passYear+ 
              "              AND ACCOUNT_NO              = " +accNo+
              "              AND doc_type                ='J'\n" + 
              "              GROUP BY ACCOUNTING_UNIT_ID,\n" + 
              "                ACCOUNTING_FOR_OFFICE_ID,\n" + 
              "                CASHBOOK_YEAR,\n" + 
              "                Cashbook_Month,\n" + 
              "                ACCOUNT_NO\n" + 
              "              )d\n" + 
              "            ON a.acc_u_id_T    =d.acc_u_id_NT\n" + 
              "            AND a.acc_off_id_T =d.acc_off_id_NT\n" + 
              "            AND a.csh_bk_yr_T  =d.csh_bk_yr_NT\n" + 
              "            AND a.csh_bk_mnth_T=d.csh_bk_mnth_NT\n" + 
              "            AND a.acc_no_T     =d.acc_no_NT\n" + 
              "            full outer join\n" + 
              "            (select acc_u_id_T,acc_off_id_T,csh_bk_yr_T,csh_bk_mnth_T,acc_no_T,sum(cramt)as jrnl_cr\n" + 
              "from\n" + 
              "(SELECT ACCOUNTING_UNIT_ID AS acc_u_id_T,\n" + 
              "                ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T,\n" + 
              "                CASHBOOK_YEAR            AS csh_bk_yr_T,\n" + 
              "                CASHBOOK_MONTH           AS csh_bk_mnth_T,\n" + 
              "                ACCOUNT_NO               AS acc_no_T,\n" + 
              "                SUM(CR_AMOUNT)           AS cramt\n" + 
              "              FROM FAS_BRS_TRANSACTION\n" + 
              "              WHERE accounting_unit_id    = " +unitcode+ 
              "              AND accounting_for_office_id=" +offCode+ 
              "              AND cashbook_month          = " +passMonth+ 
              "              AND cashbook_year           =" +passYear+ 
              "              AND ACCOUNT_NO              = " +accNo+
              "              and doc_type='J'\n" + 
              "              GROUP BY ACCOUNTING_UNIT_ID,\n" + 
              "                ACCOUNTING_FOR_OFFICE_ID,\n" + 
              "                CASHBOOK_YEAR,\n" + 
              "                CASHBOOK_MONTH,\n" + 
              "                ACCOUNT_NO\n" + 
              "              union all\n" + 
              "                SELECT ACCOUNTING_UNIT_ID                     AS acc_u_id_T,\n" + 
              "                ACCOUNTING_FOR_OFFICE_ID                     AS acc_off_id_T,\n" + 
              "                CASHBOOK_YEAR                                AS csh_bk_yr_T,\n" + 
              "                CASHBOOK_MONTH                               AS csh_bk_mnth_T,\n" + 
              "                ACCOUNT_NO                                   AS acc_no_T,\n" + 
              "                DECODE(SUM(CR_AMOUNT),NULL,0,SUM(CR_AMOUNT)) AS cr_notrn\n" + 
              "              FROM FAS_BRS_TRANSACTION_NOENTRY\n" + 
              "              WHERE accounting_unit_id    = " +unitcode+ 
              "              AND accounting_for_office_id=" +offCode+ 
              "              AND cashbook_month          = " +passMonth+ 
              "              AND cashbook_year           =" +passYear+ 
              "              AND ACCOUNT_NO              = " +accNo+
              "               and doc_type='J'\n" + 
              "              GROUP BY ACCOUNTING_UNIT_ID,\n" + 
              "                ACCOUNTING_FOR_OFFICE_ID,\n" + 
              "                CASHBOOK_YEAR,\n" + 
              "                CASHBOOK_MONTH,\n" + 
              "                ACCOUNT_NO)\n" + 
              "                group by acc_u_id_T,acc_off_id_T,csh_bk_yr_T,csh_bk_mnth_T,acc_no_T)e\n" + 
              "                   ON a.acc_u_id_T    =e.acc_u_id_T\n" + 
              "            AND a.acc_off_id_T =e.acc_off_id_T\n" + 
              "            AND a.csh_bk_yr_T  =e.csh_bk_yr_T\n" + 
              "            AND a.csh_bk_mnth_T=e.csh_bk_mnth_T\n" + 
              "            AND a.acc_no_T     =e.acc_no_T\n" + 
              "            )a\n" + 
              "          LEFT OUTER JOIN\n" + 
              "            (SELECT ACCOUNTING_UNIT_ID AS acc_u_id_T1,\n" + 
              "              ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T1,\n" + 
              "              CASHBOOK_YEAR            AS csh_bk_yr_T1,\n" + 
              "              CASHBOOK_MONTH           AS csh_bk_mnth_T1,\n" + 
              "              ACCOUNT_NO               AS acc_no_T1,\n" + 
              "              SUM(DR_AMOUNT)           AS dr_T1\n" + 
              "            FROM FAS_BRS_TRANSACTION\n" + 
              "            WHERE accounting_unit_id    = " +unitcode+ 
              "            AND accounting_for_office_id=" +offCode+ 
              "            AND cashbook_month          = " +passMonth+ 
              "            AND cashbook_year           =" +passYear+ 
              "            AND ACCOUNT_NO              = " +accNo+
              "            AND doc_type LIKE 'FT%'\n" + 
              "            GROUP BY ACCOUNTING_UNIT_ID,\n" + 
              "              ACCOUNTING_FOR_OFFICE_ID,\n" + 
              "              CASHBOOK_YEAR,\n" + 
              "              CASHBOOK_MONTH,\n" + 
              "              ACCOUNT_NO\n" + 
              "            )c\n" + 
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
              "            (SELECT ( SUM(cr_amount) - SUM(dr_amount) ) AS amt_t2_A\n" + 
              "            FROM FAS_BRS_TRANSACTION_NOENTRY\n" + 
              "            WHERE accounting_unit_id               =" +unitcode+ 
              "            AND accounting_for_office_id           =" +offCode+ 
              "            AND (cashbook_month                   <= " +passMonth+ 
              "            OR extract (MONTH FROM PASSBOOK_DATE )<="+passMonth+")\n" + 
              "            AND cashbook_year                     <=" +passYear+ 
              "            AND ACCOUNT_NO                         = " +accNo+
              "            AND TWAD_OR_NON_TWAD                   ='T'\n" + 
              "            AND doc_type                          IN ('CR', 'BR','FR by HO', 'FR by Office')\n" + 
              "            UNION ALL\n" + 
              "            SELECT ( SUM(cr_amount) - SUM(dr_amount) ) AS amt_t2_A\n" + 
              "            FROM FAS_BRS_TRANSACTION\n" + 
              "            WHERE accounting_unit_id                =" +unitcode+ 
              "            AND Accounting_For_Office_Id            =" +offCode+ 
              "            AND (Cashbook_Month                     = " +passMonth+ 
              "            AND extract (MONTH FROM PASSBOOK_DATE )!="+passMonth+")\n" + 
              "            AND cashbook_year                      <=" +passYear+ 
              "            AND Account_No                          = " +accNo+
              "            AND Twad_Or_Non_Twad                    ='T'\n" + 
              "            AND doc_type                           IN ('CR', 'BR','FR by HO', 'FR by Office')\n" + 
              "            )\n" + 
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
              "          WHERE accounting_unit_id    = " +unitcode+ 
              "          AND accounting_for_office_id=" +offCode+ 
              "          AND cashbook_month          = " +passMonth+ 
              "          AND cashbook_year           =" +passYear+ 
              "          AND ACCOUNT_NO              = " +accNo+
              "          )sss\n" + 
              "        LEFT OUTER JOIN\n" + 
              "          (SELECT BRANCH_ID AS brnch_id,\n" + 
              "            BANK_ID         AS bnk_id,\n" + 
              "            BRANCH_NAME\n" + 
              "          FROM fas_mst_bank_branches\n" + 
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
              "        trans_desc,\n" + 
              "        auid_2T_5A,\n" + 
              "        aoid_2T_5A,\n" + 
              "        cbm_2T_5A,\n" + 
              "        cby_2T_5A,\n" + 
              "        ano_2T_5A\n" + 
              "      FROM\n" + 
              "        (SELECT DECODE( (SUM(dr_amount) - SUM(cr_amount)),NULL,0,(SUM(dr_amount) -SUM(cr_amount)) ) AS amt_2t,\n" + 
              "          transaction_type,\n" + 
              "          accounting_unit_id       AS auid_2T_5A,\n" + 
              "          accounting_for_office_id AS aoid_2T_5A,\n" + 
              "          cashbook_month           AS cbm_2T_5A,\n" + 
              "          cashbook_year            AS cby_2T_5A,\n" + 
              "          ACCOUNT_NO               AS ano_2T_5A\n" + 
              "        FROM fas_brs_transaction\n" + 
              "        WHERE accounting_unit_id    = " +unitcode+ 
              "        AND accounting_for_office_id=" +offCode+ 
              "        AND Cashbook_Year          <=" +passYear+ 
              "        AND ACCOUNT_NO              =" +accNo+ 
              "        AND twad_or_non_twad        = 'NT'\n" + 
              "        GROUP BY transaction_type,\n" + 
              "          accounting_unit_id,\n" + 
              "          accounting_for_office_id,\n" + 
              "          cashbook_month,\n" + 
              "          cashbook_year,\n" + 
              "          ACCOUNT_NO\n" + 
              "        )t2_c\n" + 
              "      LEFT OUTER JOIN\n" + 
              "        ( SELECT trans_code,trans_desc FROM fas_brs_transaction_type\n" + 
              "        )t2_d\n" + 
              "      ON t2_c.transaction_type = t2_d.trans_code\n" + 
              "      )YYY,\n" + 
              "      (SELECT SUM(amt_2t) AS total\n" + 
              "      FROM\n" + 
              "        (SELECT rownum AS t2_sl_no,\n" + 
              "          amt_2t,\n" + 
              "          trans_desc,\n" + 
              "          auid_2T_5A,\n" + 
              "          aoid_2T_5A,\n" + 
              "          cbm_2T_5A,\n" + 
              "          cby_2T_5A,\n" + 
              "          ano_2T_5A\n" + 
              "        FROM\n" + 
              "          (SELECT DECODE( (SUM(dr_amount) - SUM(cr_amount)),NULL,0,(SUM(dr_amount) -SUM(cr_amount)) ) AS amt_2t,\n" + 
              "            transaction_type,\n" + 
              "            accounting_unit_id       AS auid_2T_5A,\n" + 
              "            accounting_for_office_id AS aoid_2T_5A,\n" + 
              "            cashbook_month           AS cbm_2T_5A,\n" + 
              "            cashbook_year            AS cby_2T_5A,\n" + 
              "            ACCOUNT_NO               AS ano_2T_5A\n" + 
              "          FROM fas_brs_transaction\n" + 
              "          WHERE accounting_unit_id    = " +unitcode+ 
              "          AND accounting_for_office_id=" +offCode+ 
              "          AND Cashbook_Year          <=" +passYear+ 
              "          AND ACCOUNT_NO              = " +accNo+
              "          AND twad_or_non_twad        = 'NT'\n" + 
              "          GROUP BY transaction_type,\n" + 
              "            accounting_unit_id,\n" + 
              "            accounting_for_office_id,\n" + 
              "            cashbook_month,\n" + 
              "            cashbook_year,\n" + 
              "            ACCOUNT_NO\n" + 
              "          )t2_c\n" + 
              "        LEFT OUTER JOIN\n" + 
              "          ( SELECT trans_code,trans_desc FROM fas_brs_transaction_type\n" + 
              "          )t2_d\n" + 
              "        ON t2_c.transaction_type = t2_d.trans_code\n" + 
              "        )\n" + 
              "      )XXX\n" + 
              "    )BBB ON AAA.acc_u_id_T = BBB.auid_2T_5A\n" + 
              "  AND AAA.acc_off_id_T     = BBB.aoid_2T_5A\n" + 
              "  AND AAA.csh_bk_yr_T      = BBB.cby_2T_5A\n" + 
              "  AND\n" + 
              "    -- AAA.csh_bk_mnth_T = BBB.cbm_2T_5A and\n" + 
              "    AAA.acc_no_T = BBB.ano_2T_5A\n" + 
              "  )\n" + 
              "WHERE amt_t2_A IS NOT NULL\n" + 
              "ORDER BY t2_sl_no";
			  
			 
                
                System.out.println("sql::::"+sql);
                try{
               PreparedStatement pss=con.prepareStatement(sql);
               ResultSet rss=pss.executeQuery();
	               while(rss.next())
	               {
	            	   System.out.println("insert starts");
	            	   insertCount++;
	            	   pss1=con.prepareStatement("insert into FAS_BRS_PART1 (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," +
	            	   		"PASS_SHEET_YEAR,PASS_SHEET_MONTH,ACCOUNT_NO,S1,S2,S3,S4,S5,S5A,S5B,S5C,S6," +
	            	   		"UPDATED_BY_USER_ID,UPDATED_DATE) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
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
	            	   pss1.setDouble(12,rss.getDouble("amt_2T_clos_bal"));
	            	   pss1.setDouble(13,rss.getDouble("amt_2t"));
	            	   pss1.setDouble(14,rss.getDouble("total_t2"));
	            	   pss1.setString(15,update_user);
	                   pss1.setTimestamp(16,ts);
	                   int jk=pss1.executeUpdate();
	                   System.out.println("value jk:::"+jk);
	            	 //  pss1.setInt(15,rss.getInt("total2"));
	            	   
	               }
	               if(insertCount>0)
	               {
	            	   xml=xml+"<flag>success</flag>";
	               }
	               else
	               {
	            	   xml=xml+"<flag>failure</flag>";   
	               }
	            
	               
                }
                catch(Exception ee)
                {
                	System.out.println("exception in fetching query::::"+ee);
                }
                xml = xml + "</response>";
            	System.out.println(xml);
				out.println(xml); 
		}  */
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
