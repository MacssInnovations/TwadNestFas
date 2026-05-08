package Servlets.FAS.FAS1.BRS.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.math.BigDecimal;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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

import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class General_Sanction_Proceedings_Print
 */
public class BRS_Pass_Sheet_Tally extends HttpServlet {
	private String CONTENT_TYPE = "text/html; charset=windows-1252";
	
	public void init(ServletConfig config) throws ServletException {
		super.init(config);
	}

	public void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		Connection con = null;
		PreparedStatement ps=null,pss=null;
	    ResultSet rs=null,rss=null;
	    String UnitName="",OfficeName="";
	    BigDecimal dramt  = null,cramt=null;
	    double dramount=0.0,cramount=0.0;
	    String bankName="";
		 String opr_node="";
		 String branchName="";
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

		String modid="";
		 try {
			    
			    
			    ps=con.prepareStatement("SELECT BANK_AC_NO,AC_OPERATIONAL_MODE_ID FROM FAS_MST_BANK_BALANCE " +
			    		"WHERE ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+" AND BANK_AC_NO          ="+cmbBankAccNo);
			    rs=ps.executeQuery();
			    if(rs.next())
			         modid=rs.getString("AC_OPERATIONAL_MODE_ID");
			    
			    }
			    catch (SQLException e) {
			        System.out.println("SQL Exception -->"+e);
			    }
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
	  
		String month = null;
		if(cboCashBook_Month == 1){
			month = "January";
		}else if(cboCashBook_Month == 2){
			month = "February";
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
		String title="Pass Sheet Tally For the Month of "+month+"-"+cboCashBook_Year;
		File reportFile = null;
	//	joan changed on 27 Nov 2015
		//if(modid.equals("COL"))
		if(!modid.equals(""))
		{
		try {
//			System.out.println("calling servlet...");
//			reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/BRS_PassSheet_Tally.jasper"));
//			if (!reportFile.exists())
//				throw new JRRuntimeException(
//						"File J not found. The report design must be compiled first.");

			System.out.println("pass sheet");
			String rtype = "PDF";// request.getParameter("cmbReportType");
			System.out.println(rtype);
		int pre_month=0,pre_year=0;
			 if (rtype.equalsIgnoreCase("PDF"))
		       {
				 if(cboCashBook_Month==1)
				 {
					 pre_month=12;
					 pre_year=cboCashBook_Year-1;
				 }else{
					 pre_month=cboCashBook_Month-1;
					 pre_year=cboCashBook_Year;
				 }
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
		       
		       String qry="SELECT Dr_T1_Ft, " +
		    		   "  ob_part1, " +
		    		   "  PASS_BOOK_BALANCE_TYPE, " +
		    		   "  PASSBOOK_BALANCE, " +
		    		   "  dr_amount , " +
		    		   "  cr_amount, " +
		    		   "  CASE " +
		    		   "    WHEN Pass_Book_Balance_Type='CR' " +
		    		   "    THEN (Ob_Part1+Passbook_Balance+Trn_Cr_Amt)-(Dr_T1_Ft+Dr_Amount) " +
		    		   "    ELSE 0 " +
		    		   "  END AS credit_side, " +
		    		   "  CASE " +
		    		   "    WHEN Pass_Book_Balance_Type='DR' " +
		    		   "    THEN (Ob_Part1+Dr_T1_Ft+Dr_Amount)-(Passbook_Balance+Trn_Cr_Amt) " +
		    		   "    ELSE 0 " +
		    		   "  END AS debit_side, " +
		    		   "  BANK_ID, " +
		    		   "  bankname, " +
		    		   "  BRANCH_ID, " +
		    		   "  Branchname, " +
		    		   "  Trn_Dr_Amt, " +
		    		   "  CASE " +
		    		   "    WHEN Closing_Balance < 0 " +
		    		   "    THEN Closing_Balance " +
		    		   "    ELSE 0 " +
		    		   "  END AS Closing_Balance_Dr , " +
		    		   "  CASE " +
		    		   "    WHEN closing_balance > 0 " +
		    		   "    THEN closing_balance " +
		    		   "    ELSE 0 " +
		    		   "  END AS closing_balance_cr , " +
		    		   "  trn_cr_amt " +
		    		   " FROM " +
		    		   "  (SELECT a.dr_T1_ft, " +
		    		   "    b.PASSBOOK_BALANCE, " +
		    		   "    DECODE(C.Dr_Amount,NULL,0,C.Dr_Amount) AS Dr_Amount, " +
		    		   "    DECODE(c.cr_amount,NULL,0,c.cr_amount) AS cr_amount, " +
		    		   "    b.BANK_ID, " +
		    		   "    (SELECT n.bank_name FROM fas_mst_banks n WHERE n.bank_id=b.BANK_ID " +
		    		   "    )AS bankname, " +
		    		   "    b.BRANCH_ID, " +
		    		   "    (SELECT k.branch_name " +
		    		   "    FROM FAS_MST_BANK_BRANCHES k " +
		    		   "    WHERE k.bank_id=b.BANK_ID " +
		    		   "    AND k.branch_id=b.BRANCH_ID " +
		    		   "    )AS Branchname, " +
		    		   "    M.Ob_Part1, " +
		    		   "    M.Pass_Book_Balance_Type, " +
		    		   "    DECODE(D.TRN_DR_AMT,NULL,0,d.trn_dr_amt) as trn_dr_amt, "+
                       "    DECODE(d.trn_cr_amt,NULL,0,d.trn_cr_amt) as trn_cr_amt, " +
		    		   "    ddd.closing_balance " +
		    		   "  FROM " +
		    		   "    (SELECT ACCOUNTING_UNIT_ID AS acc_u_id_T1, " +
		    		   "      ACCOUNTING_FOR_OFFICE_ID AS acc_off_id_T1, " +
		    		   "      CASHBOOK_YEAR            AS csh_bk_yr_T1, " +
		    		   "      CASHBOOK_MONTH           AS csh_bk_mnth_T1, " +
		    		   "      Account_No               AS Acc_No_T1, " +
		    		   "      SUM(Dr_T1_Ft)            AS Dr_T1_Ft " +
		    		   "    FROM " +
		    		   "      (SELECT ACCOUNTING_UNIT_ID , " +
		    		   "        ACCOUNTING_FOR_OFFICE_ID, " +
		    		   "        CASHBOOK_YEAR , " +
		    		   "        CASHBOOK_MONTH , " +
		    		   "        ACCOUNT_NO , " +
		    		   "        CASE " +
		    		   "          WHEN Doc_Type IN ('P','FT from Office','FT from HO') " +
		    		   "          THEN Dr_Amount " +
		    		   "          WHEN Doc_Type IN ('IBT') " +
		    		   "          THEN CR_AMOUNT " +
		    		   "          ELSE 0 " +
		    		   "        END AS dr_T1_ft " +
		    		   "      FROM FAS_BRS_TRANSACTION " +
		    		   "      WHERE accounting_unit_id    = " +cboAcc_UnitCode+
		    		   "      AND Accounting_For_Office_Id= " +cboOffice_code+
		    		   "      AND Cashbook_Month          =  " +cboCashBook_Month+
		    		   "      AND Cashbook_Year           = " +cboCashBook_Year+
		    		   "      AND Account_No              = " +cmbBankAccNo+
		    		   "      ) " +
		    		   "    GROUP BY ACCOUNTING_UNIT_ID, " +
		    		   "      ACCOUNTING_FOR_OFFICE_ID, " +
		    		   "      CASHBOOK_YEAR, " +
		    		   "      CASHBOOK_MONTH, " +
		    		   "      Account_No " +
		    		   "    )a " +
		    		   "  FULL OUTER JOIN " +
		    		   "    (SELECT 0 Bank_Id, " +
		    		   "      0 BRANCH_ID, " +
		    		   "      SUM(Passbook_Balance) AS Passbook_Balance, " +
		    		   "      accounting_unit_id, " +
		    		   "      accounting_for_office_id, " +
		    		   "      cashbook_year, " +
		    		   "      Cashbook_Month, " +
		    		   "      Account_No " +
		    		   "    FROM " +
		    		   "      (SELECT " +
		    		   "        CASE " +
		    		   "          WHEN Doc_Type IN ('BR','CR','FR by Office','FR by HO') " +
		    		   "          THEN Cr_Amount " +
		    		   "          WHEN Doc_Type IN ('IBT') " +
		    		   "          THEN DR_AMOUNT " +
		    		   "          ELSE 0 " +
		    		   "        END AS Passbook_Balance, " +
		    		   "        accounting_unit_id, " +
		    		   "        accounting_for_office_id, " +
		    		   "        cashbook_year, " +
		    		   "        cashbook_month, " +
		    		   "        Account_No " +
		    		   "      FROM FAS_BRS_TRANSACTION " +
		    		   "      WHERE accounting_unit_id    = " +cboAcc_UnitCode+
		    		   "      AND Accounting_For_Office_Id= " +cboOffice_code+
		    		   "      AND Cashbook_Month          = " +cboCashBook_Month+
		    		   "      AND Cashbook_Year           =" +cboCashBook_Year+
		    		   "      AND Account_No              = " +cmbBankAccNo+
		    		   "      )b " +
		    		   "    GROUP BY accounting_unit_id, " +
		    		   "      accounting_for_office_id, " +
		    		   "      cashbook_year, " +
		    		   "      Cashbook_Month, " +
		    		   "      Account_No " +
		    		   "    ) b " +
		    		   "  ON a.acc_u_id_T1    =b.accounting_unit_id " +
		    		   "  AND a.acc_off_id_T1 =b.accounting_for_office_id " +
		    		   "  AND a.csh_bk_yr_T1  =b.cashbook_year " +
		    		   "  AND a.csh_bk_mnth_T1=b.cashbook_month " +
		    		   "  AND a.acc_no_T1     =b.ACCOUNT_NO " +
		    		   "  LEFT OUTER JOIN " +
		    		   "    (SELECT accounting_unit_id, " +
		    		   "      Accounting_For_Office_Id, " +
		    		   cboCashBook_Year+ "  as      Cashbook_Year, " +
		    		   cboCashBook_Month+ "   as    Cashbook_Month, " +
		    		   "      Account_No, " +
		    		   "      PASSBOOK_BALANCE AS ob_part1, " +
		    		   "      PASS_BOOK_BALANCE_TYPE " +
		    		   "    FROM FAS_BRS_MASTER " +
		    		   "    WHERE accounting_unit_id    = " +cboAcc_UnitCode+
		    		   "    AND Accounting_For_Office_Id= " +cboOffice_code+
		    		   "    AND Cashbook_Month          =  " +pre_month+
		    		   "    AND Cashbook_Year           = " +pre_year+
		    		   "    AND ACCOUNT_NO              = " +cmbBankAccNo+
		    		   "    )m " +
		    		   "  ON m.accounting_unit_id       =b.accounting_unit_id " +
		    		   "  AND m.accounting_for_office_id=b.accounting_for_office_id " +
		    		   "  AND m.cashbook_year           =b.cashbook_year " +
		    		   "  AND m.cashbook_month          =b.cashbook_month " +
		    		   "  AND m.account_no              =b.ACCOUNT_NO " +
		    		   "  LEFT OUTER JOIN " +
		    		   "    (SELECT DECODE(t.dr_amount,NULL,0,t.dr_amount)dr_amount, " +
		    		   "      DECODE(t.cr_amount,NULL,0,t.cr_amount)cr_amount, " +
		    		   "      t.auid_2T_5A, " +
		    		   "      t.aoid_2T_5A, " +
		    		   "      T.Cbm_2t_5a, " +
		    		   "      T.Cby_2t_5a, " +
		    		   "      T.Ano_2t_5a " +
		    	//	   "      --,m.ob_part1 " +
		    		   "    FROM " +
		    		   "      (SELECT SUM(dr_amount)     AS dr_amount, " +
		    		   "        SUM(cr_amount)           AS cr_amount, " +
		    		   "        accounting_unit_id       AS auid_2T_5A, " +
		    		   "        accounting_for_office_id AS aoid_2T_5A, " +
		    		   "        cashbook_month           AS cbm_2T_5A, " +
		    		   "        cashbook_year            AS cby_2T_5A, " +
		    		   "        ACCOUNT_NO               AS ano_2T_5A " +
		    		   "      FROM fas_brs_transaction " +
		    		   "      WHERE accounting_unit_id    =" +cboAcc_UnitCode+
		    		   "      AND Accounting_For_Office_Id= " +cboOffice_code+
		    		   "      AND Cashbook_Month          = " +cboCashBook_Month+
		    		   "      AND Cashbook_Year           = " +cboCashBook_Year+
		    		   "      AND ACCOUNT_NO              = " +cmbBankAccNo+
		    		   "      AND twad_or_non_twad        = 'NT' " +
		    		   "      GROUP BY accounting_unit_id, " +
		    		   "        accounting_for_office_id, " +
		    		   "        cashbook_month, " +
		    		   "        cashbook_year, " +
		    		   "        ACCOUNT_NO " +
		    		   "      ) t " +
		    		   "    )c " +
		    		   "  ON a.acc_u_id_T1    =c.auid_2T_5A " +
		    		   "  AND a.acc_off_id_T1 =c.aoid_2T_5A " +
		    		   "  AND a.csh_bk_yr_T1  =c.cby_2T_5A " +
		    		   "  AND a.csh_bk_mnth_T1=c.cbm_2T_5A " +
		    		   "  AND a.acc_no_T1     =c.ano_2T_5A " +
		    		   "  LEFT OUTER JOIN " +
		    		   "    (SELECT DECODE(SUM(t.dr_amount),NULL,0,SUM(t.dr_amount)) AS trn_dr_amt, " +
		    		   "      DECODE(SUM(t.cr_amount),NULL,0,SUM(t.cr_amount))       AS trn_cr_amt, " +
		    		   "      t.accounting_unit_id                                   AS auid, " +
		    		   "      t.accounting_for_office_id                             AS aoid, " +
		    		   "      t.cashbook_month                                       AS cbm, " +
		    		   "      t.cashbook_year                                        AS cby, " +
		    		   "      t.ACCOUNT_NO                                           AS ano " +
		    		   "    FROM fas_brs_transaction t " +
		    		   "    WHERE t.accounting_unit_id    =" +cboAcc_UnitCode+
		    		   "    AND T.Accounting_For_Office_Id= " +cboOffice_code+
		    		   "    AND t.Cashbook_Month          = " +cboCashBook_Month+
		    		   "    AND T.Cashbook_Year           = " +cboCashBook_Year+
		    		   "    AND t.ACCOUNT_NO              =  " +cmbBankAccNo+
		    		   "    AND t.twad_or_non_twad        = 'NT' " +
		    		   "    GROUP BY t.accounting_unit_id, " +
		    		   "      t.accounting_for_office_id, " +
		    		   "      t.cashbook_month, " +
		    		   "      t.cashbook_year, " +
		    		   "      t.ACCOUNT_NO " +
		    		   "    )d " +
		    		   "  ON a.acc_u_id_T1    =d.auid " +
		    		   "  AND a.acc_off_id_T1 =d.aoid " +
		    		   "  AND a.csh_bk_yr_T1  =d.cby " +
		    		   "  AND a.csh_bk_mnth_T1=d.cbm " +
		    		   "  AND A.Acc_No_T1     =D.Ano " +
		    		   "  LEFT OUTER JOIN " +
		    		   "    (SELECT DECODE(SUM(mm.PASSBOOK_BALANCE),NULL,0,SUM(mm.PASSBOOK_BALANCE)) AS closing_balance, " +
		    		   "      mm.accounting_unit_id                                                  AS auid, " +
		    		   "      mm.accounting_for_office_id                                            AS aoid, " +
		    		   "      mm.cashbook_month                                                      AS cbm, " +
		    		   "      mm.cashbook_year                                                       AS cby, " +
		    		   "      mm.Account_No                                                          AS Ano " +
		    		   "    FROM fas_brs_master mm " +
		    		   "    WHERE mm.accounting_unit_id    =" +cboAcc_UnitCode+
		    		   "    AND mm.Accounting_For_Office_Id= " +cboOffice_code+
		    		   "    AND mm.Cashbook_Month          = " +cboCashBook_Month+
		    		   "    AND mm.Cashbook_Year           = " +cboCashBook_Year+
		    		   "    AND mm.ACCOUNT_NO              =  " +cmbBankAccNo+
		    		   "    GROUP BY mm.accounting_unit_id, " +
		    		   "      Mm.Accounting_For_Office_Id, " +
		    		   "      mm.cashbook_month, " +
		    		   "      mm.cashbook_year, " +
		    		   "      mm.Account_No " +
		    		   "    )ddd " +
		    		   "  ON a.acc_u_id_T1    =ddd.auid " +
		    		   "  AND a.acc_off_id_T1 =ddd.aoid " +
		    		   "  AND a.csh_bk_yr_T1  =ddd.cby " +
		    		   "  AND A.Csh_Bk_Mnth_T1=Ddd.Cbm " +
		    		   "  AND A.Acc_No_T1     =Ddd.Ano " +
		    		   "  )";
		           
		        System.out.println("qry:::"+qry);
		       
		       String path = getServletContext().getRealPath("/WEB-INF/subReport/BRS_PassSheet_Tallyupdate.jasper");
		       String ctxpath = path.substring(0, path.lastIndexOf("BRS_PassSheet_Tallyupdate.jasper"));
		       Map map = null;
				map = new HashMap();

				map.put("UnitId", cboAcc_UnitCode);
				map.put("OfficeId", cboOffice_code);
				map.put("cbyear", cboCashBook_Year);
				map.put("cbmonth", cboCashBook_Month);
				map.put("accNo", cmbBankAccNo);
				map.put("month", month);
				map.put("qry", qry);
				map.put("UnitName", UnitName);
				map.put("title", title);
		        map.put("SUBREPORT_DIR", ctxpath);
		        map.put("bankName", bankName);
		        map.put("branchName", branchName);
		       
		       JasperPrint jasperPrint = JasperFillManager.fillReport(path, map, con);
		       
		       OutputStream outuputStream_1n = response.getOutputStream();
		       JRExporter exporter = null;
		       response.setContentType("application/pdf");
		       response.setHeader("Content-Disposition","attachment; filename=\"BRS_PASS_SHEET_COLLECTION.pdf\"");
		       exporter = new JRPdfExporter();
		       exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
		       exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream_1n);
		       exporter.exportReport();
		       outuputStream_1n.close();
		                             
		       
		        
		       }
		} catch (Exception ex) {
			String connectMsg = "Could not create the report "
					+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
			System.out.println(connectMsg);
		}
	}
		else if(modid.equals("OPR"))
		{
			int cyear=0;
			int cmonth=0;
			BigDecimal cl_pb_bigdecimal  = null;
			double closing_pb=0.0;
			try {
			    if(cboCashBook_Month==1)
			    {
			    	cyear=(cboCashBook_Year-1);
			    	cmonth=12;
			    }
			    else
			    {
			    	cyear=cboCashBook_Year;
			    	cmonth=(cboCashBook_Month-1);
			    	
			    }
			    
			    ps=con.prepareStatement("SELECT DECODE(PASSBOOK_BALANCE,NULL,0,PASSBOOK_BALANCE) AS PASSBOOK_BALANCE,"+
						      " BANK_ID,BRANCH_ID,"+
			    		" ACCOUNT_NO"+
			    		"   FROM FAS_BRS_MASTER"+
			    		"   WHERE accounting_unit_id    = "+cboAcc_UnitCode+
			    		"   AND accounting_for_office_id="+cboOffice_code+
			    		"   AND cashbook_month          ="+cmonth+
			    		"   AND cashbook_year           ="+cyear+
			    		"   AND ACCOUNT_NO              ="+cmbBankAccNo);
			//    ps.setInt(1,cboAcc_UnitCode);
			    rs=ps.executeQuery();
			    if(rs.next())
			    {
			         closing_pb=rs.getDouble("PASSBOOK_BALANCE");
			    cl_pb_bigdecimal = new BigDecimal(closing_pb);
			    }
			    else
			    {
			    	cl_pb_bigdecimal=new BigDecimal(0.00);
			    }
			   
			    }
			    catch (SQLException e) {
			        System.out.println("SQL Exception closing_pb-->"+e);
			    }

			try {
				
				reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/BRS_PassSheet_Tally_operation.jasper"));
				if (!reportFile.exists())
					throw new JRRuntimeException(
							"File J not found. The report design must be compiled first.");
				JasperReport jasperReport = (JasperReport) JRLoader
				.loadObject(reportFile.getPath());
				
				String rtype = "PDF";// request.getParameter("cmbReportType");
				
			
				 if (rtype.equalsIgnoreCase("PDF"))
			       {
			       
			       Map map = null;
					map = new HashMap();

					map.put("UnitId", cboAcc_UnitCode);
					map.put("OfficeId", cboOffice_code);
					map.put("cbyear", cboCashBook_Year);
					map.put("cbmonth", cboCashBook_Month);
					map.put("accNo", cmbBankAccNo);
					map.put("month", month);
					map.put("UnitName", UnitName);
					map.put("title", title);
					map.put("cl_pb_bigdecimal", cl_pb_bigdecimal);
					
			     
			       JasperPrint jasperPrint = JasperFillManager.fillReport(
							jasperReport, map, con);
			       OutputStream outuputStream_1n = response.getOutputStream();
			       JRExporter exporter = null;
			       response.setContentType("application/pdf");
			       response.setHeader("Content-Disposition","attachment; filename=\"BRS_PASS_SHEET_OPR.pdf\"");
			       exporter = new JRPdfExporter();
			       exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
			       exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,outuputStream_1n);
			       exporter.exportReport();
			       outuputStream_1n.close();
			                             
			       
			        
			       }
			} catch (Exception ex) {
				String connectMsg = "Could not create the report "
						+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
				System.out.println(connectMsg);
			}
		
		}
	}
	

}
