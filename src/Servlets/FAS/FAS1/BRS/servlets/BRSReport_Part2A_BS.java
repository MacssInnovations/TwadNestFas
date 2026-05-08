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
import java.sql.Timestamp;
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
 * Servlet implementation class BRSReport_Part2A_BS
 */
public class BRSReport_Part2A_BS extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String CONTENT_TYPE = "text/html; charset=windows-1252";
    
    public BRSReport_Part2A_BS() {
        super();
       
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		Connection con = null;
        Double four_bAmount=0.00,four_cAmount=0.00,excess_db=0.00,cheq_passAmt=0.00;
	    BigDecimal ii  = null,i2=null,i3=null,cheq_Amt=null;
	    BigDecimal total_i=null;
	    String UnitName=null;
                String totalyear="";
          int count_test=0,exce_test=0,count_tesst=0,exce_t=0;
          PreparedStatement ps=null;
  	    ResultSet rs=null;
  	    
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
		
		int cboAcc_UnitCode = Integer.parseInt(request
				.getParameter("cmbAcc_UnitCode"));
		int cboOffice_code = Integer.parseInt(request
				.getParameter("cmbOffice_code"));
		int cboCashBook_Year = Integer.parseInt(request
				.getParameter("cboCashBook_Year"));
		int cboCashBook_Month = Integer.parseInt(request
				.getParameter("cboCashBook_Month"));
		long cmbBankAccNo =Long.parseLong(request
				.getParameter("cmbBankAccNo"));
		String cmd=request.getParameter("command");
		String mode=request.getParameter("mode");
		
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
		    
		    
		 PreparedStatement  ps_l=con.prepareStatement("SELECT to_char(last_day(to_date(date1, 'dd-mm-yy')),'dd-mm-yyyy')ls_date "+
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
		    
		  ResultSet  rs_l=ps_l.executeQuery();
		    if(rs_l.next())
		    {
		    String  last_date_one =rs_l.getString("ls_date");
		    System.out.println("last_date_one::"+last_date_one);
		    String[] splto=last_date_one.split("-");
		  String smonth="";
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
                
	    try {
	    
	    
        String sql="select acc_u_id5,acc_off_id5,Acc_No5,sum(four_b)as four_b from\n" + 
        "(SELECT ACCOUNTING_UNIT_ID  AS acc_u_id5,\n" + 
        "  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5,\n" + 
        "  Account_No               AS Acc_No5,\n" + 
        "  SUM(DR_AMOUNT)           AS four_b\n" + 
        " FROM FAS_BRS_TRANSACTION\n" + 
        " WHERE accounting_unit_id               = " + cboAcc_UnitCode+
        " AND accounting_for_office_id           = " + cboOffice_code+
        " AND ((extract(YEAR FROM PASSBOOK_DATE) <" + cboCashBook_Year+
        " AND extract(MONTH FROM PASSBOOK_DATE) <=12)\n" + 
        " OR (extract(YEAR FROM PASSBOOK_DATE)   =" + cboCashBook_Year+
        " AND extract(MONTH FROM PASSBOOK_DATE) <="+cboCashBook_Month+"))\n" + 
        " AND ACCOUNT_NO                         = " + cmbBankAccNo+
        " AND Twad_Or_Non_Twad                   ='NT'\n" + 
        " AND TRANSACTION_TYPE                  IN(3,23,12,6)\n" + 
        " AND (CLEARED_BASED_ON_FOLLOWUP IS NULL OR CLEARED_BASED_ON_FOLLOWUP    ='N')\n" + 
        " GROUP BY ACCOUNTING_UNIT_ID,\n" + 
        "  ACCOUNTING_FOR_OFFICE_ID,\n" + 
        "  Account_No\n" + 
        "  union all\n" + 
        "  SELECT ACCOUNTING_UNIT_ID  AS acc_u_id5,\n" + 
        "  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5,\n" + 
        "  Account_No               AS Acc_No5,\n" + 
        "  SUM(DR_AMOUNT)           AS four_b\n" + 
        " FROM FAS_BRS_TRANSACTION\n" + 
        " WHERE accounting_unit_id               = " + cboAcc_UnitCode+
        " AND accounting_for_office_id           = " + cboOffice_code+
        " AND ((extract(YEAR FROM PASSBOOK_DATE) <" + cboCashBook_Year+
        " AND extract(MONTH FROM PASSBOOK_DATE) <=12)\n" + 
        " OR (extract(YEAR FROM PASSBOOK_DATE)   =" + cboCashBook_Year+
        " AND extract(MONTH FROM PASSBOOK_DATE) <="+cboCashBook_Month+"))\n" + 
        " AND ACCOUNT_NO                         = " + cmbBankAccNo+
        " AND Twad_Or_Non_Twad                   ='NT'\n" + 
        " AND TRANSACTION_TYPE                  IN(3,23,12,6)\n" + 
        " AND (CLEARED_BASED_ON_FOLLOWUP='Y' AND clearence_date            >('"+totalyear+"'))\n" + 
        " GROUP BY ACCOUNTING_UNIT_ID,\n" + 
        "  ACCOUNTING_FOR_OFFICE_ID,\n" + 
        "  Account_No)\n" + 
        "  group by acc_u_id5,acc_off_id5,Acc_No5";
	    
	    System.out.println("four_b sql:"+sql);
	    ps=con.prepareStatement(sql);
	    rs=ps.executeQuery();
	    if(rs.next()){
	    
	         four_bAmount=rs.getDouble("four_b");
	     	System.out.println("four_bAmount:::"+four_bAmount);
	        i2 = new BigDecimal(four_bAmount);
                count_test++;
            }
	   if(count_test==0) {
               i2=new BigDecimal(0.00);
           }
	    
	    }
	    catch (SQLException e) {
	        System.out.println("SQL Exception -->"+e);
	    }
          //  System.out.println("four_bAmount:::"+four_bAmount);
           System.out.println("i2:::::::"+i2); 
	    try {
	    PreparedStatement pss=null;
	    ResultSet rss=null;
	    
	    
        String ss="select acc_u_id5,acc_off_id5,csh_bk_yr5,csh_bk_mnth5,Acc_No5,sum(four_c)as four_c from\n" + 
        "(SELECT ACCOUNTING_UNIT_ID  AS acc_u_id5,\n" + 
        "  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5,\n" + 
        "  CASHBOOK_YEAR            AS csh_bk_yr5,\n" + 
        "  CASHBOOK_MONTH           AS csh_bk_mnth5,\n" + 
        "  Account_No               AS Acc_No5,\n" + 
        "  SUM(DR_AMOUNT)           AS four_c\n" + 
        " FROM FAS_BRS_TRANSACTION\n" + 
        " WHERE accounting_unit_id              = " +cboAcc_UnitCode+ 
        " AND accounting_for_office_id          = " +cboOffice_code+ 
        " AND extract(YEAR FROM PASSBOOK_DATE)  = " +cboCashBook_Year+ 
        " AND ACCOUNT_NO                        = " +cmbBankAccNo+ 
        " AND extract(MONTH FROM PASSBOOK_DATE) = " +cboCashBook_Month+ 
        " AND Twad_Or_Non_Twad                  ='NT'\n" + 
        " AND TRANSACTION_TYPE                 IN(3,23,12)\n" + 
        " AND (CLEARED_BASED_ON_FOLLOWUP IS NULL OR CLEARED_BASED_ON_FOLLOWUP    ='N')\n" + 
        " GROUP BY ACCOUNTING_UNIT_ID,\n" + 
        "  ACCOUNTING_FOR_OFFICE_ID,\n" + 
        "  CASHBOOK_YEAR,\n" + 
        "  CASHBOOK_MONTH,\n" + 
        "  Account_No\n" + 
        "  union all\n" + 
        "  SELECT ACCOUNTING_UNIT_ID  AS acc_u_id5,\n" + 
        "  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5,\n" + 
        "  CASHBOOK_YEAR            AS csh_bk_yr5,\n" + 
        "  CASHBOOK_MONTH           AS csh_bk_mnth5,\n" + 
        "  Account_No               AS Acc_No5,\n" + 
        "  SUM(DR_AMOUNT)           AS four_c\n" + 
        " FROM FAS_BRS_TRANSACTION\n" + 
        " WHERE accounting_unit_id              = " +cboAcc_UnitCode+ 
        " AND accounting_for_office_id          = " +cboOffice_code+ 
        " AND extract(YEAR FROM PASSBOOK_DATE)  = " +cboCashBook_Year+ 
        " AND ACCOUNT_NO                        = " +cmbBankAccNo+ 
        " AND extract(MONTH FROM PASSBOOK_DATE) = " +cboCashBook_Month+ 
        " AND Twad_Or_Non_Twad                  ='NT'\n" + 
        " AND TRANSACTION_TYPE                 IN(3,23,12)\n" + 
        " AND (CLEARED_BASED_ON_FOLLOWUP='Y' AND clearence_date            >('"+totalyear+"'))\n" + 
        " GROUP BY ACCOUNTING_UNIT_ID,\n" + 
        "  ACCOUNTING_FOR_OFFICE_ID,\n" + 
        "  CASHBOOK_YEAR,\n" + 
        "  CASHBOOK_MONTH,\n" + 
        "  Account_No)\n" + 
        "  group by acc_u_id5,acc_off_id5,csh_bk_yr5,csh_bk_mnth5,Acc_No5";
	    //System.out.println("ss:::"+ss);
	    pss=con.prepareStatement(ss);
	   
	    rss=pss.executeQuery();
	    if(rss.next())
            {
                  four_cAmount=rss.getDouble("four_c");
               //   System.out.println("four_cAmount::::"+four_cAmount);
	        ii = new BigDecimal(four_cAmount);
	      //  System.out.println("four_cAmount:afetr:::"+ii);
                count_tesst++; 
            }
	        if(count_tesst==0) {
	        //	 System.out.println("ii no::::"+ii);
	            ii=new BigDecimal(0.00);
	            
	        }
	    }            
	    catch (SQLException e) {
	        System.out.println("SQL Exception -->"+e);
	    }
	    
	 //   System.out.println("ii"+ii);
	  
	   // System.out.println("total_i:"+total_i);
	    //Lakshmi 3Dec13
	    try {
		    PreparedStatement pps=null;
		    ResultSet ress=null;
		    String cheq_cash="";
		    
		    if (mode.equalsIgnoreCase("SCH"))
		    { 
             cheq_cash=/*joan changed on 05 Aug 2015*/
            		 //"select sum(DR_AMOUNT)  as PASSBOOK_BALANCE from FAS_BRS_TRANSACTION where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+ 
            		
            		 
            		 "select sum(AMOUNT_IN_PASSBOOK)  as PASSBOOK_BALANCE from FAS_BRS_TRANSACTION where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+
            		 " AND ACCOUNTING_FOR_OFFICE_ID= " +cboOffice_code+ 
				" AND  CASHBOOK_YEAR= "+cboCashBook_Year+ 
				" AND  CASHBOOK_MONTH= "+cboCashBook_Month+ 
				" AND  ACCOUNT_NO = " +cmbBankAccNo;
		    }        	
		    else{
		    	 cheq_cash="select PASSBOOK_BALANCE from FAS_BRS_MASTER where ACCOUNTING_UNIT_ID="+cboAcc_UnitCode+ 
		 				" AND ACCOUNTING_FOR_OFFICE_ID= " +cboOffice_code+ 
		 				" AND  CASHBOOK_YEAR= "+cboCashBook_Year+ 
		 				" AND  CASHBOOK_MONTH= "+cboCashBook_Month+ 
		 				" AND  ACCOUNT_NO = " +cmbBankAccNo;
		    }
            		    
		    System.out.println("cheq_cash:"+cheq_cash);
		    pps=con.prepareStatement(cheq_cash);
		    ress=pps.executeQuery();
		    if(ress.next()){
		    
		    	cheq_passAmt=ress.getDouble("PASSBOOK_BALANCE");
		    	System.out.println("cheq_passAmt:::"+cheq_passAmt);
		    	cheq_Amt = new BigDecimal(cheq_passAmt);
		        exce_t++;
	            }
		 if(exce_t==0) {
			 cheq_Amt=new BigDecimal(0.00);
	           }
		    
		    }
		    catch (SQLException e) {
		        System.out.println("SQL Exception -->"+e);
		    }
		    
		    try {
			    PreparedStatement pps=null;
			    ResultSet ress=null;
			    
	            String ne="select acc_u_id5,acc_off_id5,Acc_No5,sum(four_bs) as four_bs from\n" + 
	            "(SELECT ACCOUNTING_UNIT_ID  AS acc_u_id5,\n" + 
	            "  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5,\n" + 
	            "  Account_No               AS Acc_No5,\n" + 
	            "  SUM(DR_AMOUNT)           AS four_bs\n" + 
	            " FROM FAS_BRS_TRANSACTION\n" + 
	            " WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
	            " AND accounting_for_office_id= " +cboOffice_code+ 
	            " AND cashbook_year           = " +cboCashBook_Year+ 
	            " AND ACCOUNT_NO              = " +cmbBankAccNo+ 
	            " AND cashbook_month          = " +cboCashBook_Month+ 
	            " AND Twad_Or_Non_Twad        ='NT'\n" + 
	            " AND (CLEARED_BASED_ON_FOLLOWUP IS NULL OR CLEARED_BASED_ON_FOLLOWUP    ='N')\n" + 
	            " AND TRANSACTION_TYPE       IN(6)\n" + 
	            " GROUP BY ACCOUNTING_UNIT_ID,\n" + 
	            "  ACCOUNTING_FOR_OFFICE_ID,\n" + 
	            "  Account_No\n" + 
	            "  union all\n" + 
	            "  SELECT ACCOUNTING_UNIT_ID  AS acc_u_id5,\n" + 
	            "  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5,\n" + 
	            "  Account_No               AS Acc_No5,\n" + 
	            "  SUM(DR_AMOUNT)           AS four_bs\n" + 
	            " FROM FAS_BRS_TRANSACTION\n" + 
	            " WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
	            " AND accounting_for_office_id= " +cboOffice_code+ 
	            " AND cashbook_year           = " +cboCashBook_Year+ 
	            " AND ACCOUNT_NO              = " +cmbBankAccNo+ 
	            " AND cashbook_month          = " +cboCashBook_Month+ 
	            " AND Twad_Or_Non_Twad        ='NT'\n" + 
	            " AND (CLEARED_BASED_ON_FOLLOWUP='Y' AND clearence_date            >('"+totalyear+"'))\n" + 
	            " AND TRANSACTION_TYPE       IN(6)\n" + 
	            " GROUP BY ACCOUNTING_UNIT_ID,\n" + 
	            "  ACCOUNTING_FOR_OFFICE_ID,\n" + 
	            "  Account_No)\n" + 
	            "  group by acc_u_id5,acc_off_id5,Acc_No5";
			    
			   // System.out.println("ne:"+ne);
			    pps=con.prepareStatement(ne);
			    ress=pps.executeQuery();
			    if(ress.next()){
			    
			         excess_db=ress.getDouble("four_bs");
			    // 	System.out.println("excess_db:::"+excess_db);
			        i3 = new BigDecimal(excess_db);
		                exce_test++;
		            }
			   if(exce_test==0) {
		               i3=new BigDecimal(0.00);
		           }
			    
			    }
			    catch (SQLException e) {
			        System.out.println("SQL Exception -->"+e);
			    }
		    
		    
		  
			String qry="SELECT DECODE(OFFICE_NAME,NULL,'-',OFFICE_NAME) AS OFFICE_NAME,\n" + 
            "  DECODE(BANK_NAME,NULL,'-',BANK_NAME)          AS BANK_NAME,\n" + 
            "  DECODE(BRANCH_NAME,NULL,'-',BRANCH_NAME)      AS BRANCH_NAME,\n" + 
            "  acc_u_id,\n" + 
            "  acc_off_id,\n" + 
            "  csh_bk_yr,\n" + 
            "  csh_bk_mnth,\n" + 
            "  acc_no,\n" + 
            "  DECODE(OB_PART2A,NULL,0,OB_PART2A) AS A_1,\n" + 
            "  A_2a,\n" + 
            "  A_2b,\n" + 
            "  A_2c,\n" + 
            "  A_2d,\n" + 
            "  A_2e,\n" + 
           " ((DECODE(OB_PART2A,NULL,0,OB_PART2A)+A_2E)-"+cheq_passAmt+") AS CLOSING_BAL, ";
            if(mode.equalsIgnoreCase("SCH")){
            	qry=qry+   "  ((DECODE(OB_PART2A,NULL,0,OB_PART2A)+A_2E)-"+cheq_passAmt+")-("+i2+"-A_6A) as TOT, ";
            }else{
            	qry=qry+   " (A_6A-"+i2+")-((DECODE(OB_PART2A,NULL,0,OB_PART2A)+A_2E)-"+cheq_passAmt+") AS TOT, ";
            }
            qry=qry+   "  (DECODE(OB_PART2A,NULL,0,OB_PART2A)+A_2e) AS A_3,\n" + 
          //  "  Pay_Amt,\n" + 
            " DECODE(Pay_Amt,NULL,0,Pay_Amt)AS Pay_Amt, \n"+
            "  DECODE(Sc_Amt,NULL,0,Sc_Amt)AS Sc_Amt,\n" + 
           // "  A_4,\n" + //Lakshmi 3Dec13
            " DECODE(A_4,NULL,0,A_4)AS A_4, \n"+
            "  ( (DECODE(Ob_Part2a,NULL,0,Ob_Part2a)+A_2e)-A_4) AS A_5,\n" + 
            "  A_6a,\n" + 
            "  A_6b ,\n" + 
            "  A_6c,\n" + 
            "  PASSBOOK_BALANCE\n" + 
            " FROM (\n" + 
            "  (SELECT Acc_U_Id6,\n" + 
            "    Acc_Off_Id6,\n" + 
            "    Csh_Bk_Yr6,\n" + 
            "    Csh_Bk_Mnth6,\n" + 
            "    Acc_No6,\n" + 
            "    Ob_Part2a,\n" + 
            "    OFFICE_NAME,\n" + 
            "    BANK_NAME,\n" + 
            "    BRANCH_NAME,\n" + 
            "    PASSBOOK_BALANCE\n" + 
            "  FROM\n" + 
            "    (SELECT rownum AS slno1,\n" + 
            "      acc_u_id6,\n" + 
            "      acc_off_id6,\n" + 
            "      csh_bk_yr6,\n" + 
            "      csh_bk_mnth6,\n" + 
            "      acc_no6,\n" + 
            "      OB_PART2A,\n" + 
            "      OFFICE_NAME\n" + 
            "    FROM\n" + 
            "      (SELECT ACCOUNTING_UNIT_ID AS acc_u_id6,\n" + 
            "        ACCOUNTING_FOR_OFFICE_ID AS acc_off_id6,\n" + 
            "        CASHBOOK_YEAR            AS csh_bk_yr6,\n" + 
            "        CASHBOOK_MONTH           AS csh_bk_mnth6,\n" + 
            "        ACCOUNT_NO               AS acc_no6,\n" + 
            "        OB_PART2A\n" + 
            "      FROM FAS_BRS_OB\n" + 
            "      WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
            "      AND accounting_for_office_id= " +cboOffice_code+ 
            "      AND cashbook_month          = " +cboCashBook_Month+ 
            "      AND cashbook_year           =" +cboCashBook_Year+ 
            "      AND ACCOUNT_NO              = " +cmbBankAccNo+ 
            "      )AUID1\n" + 
            "    LEFT OUTER JOIN\n" + 
            "      (SELECT OFFICE_ID,OFFICE_NAME FROM COM_MST_OFFICES\n" + 
            "      )AUID2\n" + 
            "    ON AUID1.acc_off_id6 = AUID2.OFFICE_ID\n" + 
            "    ) t1\n" + 
            "  LEFT OUTER JOIN\n" + 
            "    (SELECT Slno2,\n" + 
            "      Bank_Name,\n" + 
            "      Branch_Name,\n" + 
            "      PASSBOOK_BALANCE\n" + 
            "    FROM\n" + 
            "      (SELECT rownum AS slno2,\n" + 
            "        BANK_ID,\n" + 
            "        BRANCH_ID,\n" + 
            "        PASSBOOK_BALANCE\n" + 
            "      FROM FAS_BRS_MASTER\n" + 
            "      WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
            "      AND accounting_for_office_id= " +cboOffice_code+ 
            "      AND cashbook_month          = " +cboCashBook_Month+ 
            "      AND cashbook_year           =" +cboCashBook_Year+ 
            "      AND ACCOUNT_NO              = " +cmbBankAccNo+ 
            "      )sss\n" + 
            "    LEFT OUTER JOIN\n" + 
            "      (SELECT BRANCH_ID AS brnch_id,\n" + 
            "        BANK_ID         AS bnk_id,\n" + 
            "        BRANCH_NAME\n" + 
            "      FROM FAS_MST_BANK_BRANCHES\n" + 
            "      )c\n" + 
            "    ON sss.BANK_ID    = c.bnk_id\n" + 
            "    AND sss.BRANCH_ID = c.brnch_id\n" + 
            "    LEFT OUTER JOIN\n" + 
            "      (SELECT BANK_ID AS bnk_id1,BANK_NAME FROM FAS_BANK_LIST\n" + 
            "      )d\n" + 
            "    ON sss.BANK_ID  = d.bnk_id1\n" + 
            "    )t2 ON t1.slno1 = t2.slno2\n" + 
            "  )XX\n" + 
            "LEFT OUTER JOIN\n" + 
            "  (SELECT acc_u_id,\n" + 
            "    acc_off_id,\n" + 
            "    csh_bk_yr,\n" + 
            "    csh_bk_mnth,\n" + 
            "    acc_no,\n" + 
            "    A_2a,\n" + 
            "    A_2b,\n" + 
            "    A_2c,\n" + 
            "    A_2d,\n" + 
            "    (DECODE(A_2c,NULL,0,A_2c) - DECODE(A_2d,NULL,0,A_2d)) AS A_2e,\n" + 
            "    Pay_Amt,\n" + 
            "    Sc_Amt,\n" + 
            "    A_4,\n" + 
            "    DECODE(A_6a,NULL,0,A_6a)                             AS A_6a,\n" + 
            "    DECODE(A_6b,NULL,0,A_6b)                             AS A_6b ,\n" + 
            "    (DECODE(A_6a,NULL,0,A_6a) + DECODE(A_6b,NULL,0,A_6b))AS A_6c\n" + 
            "  FROM\n" + 
            "    (SELECT acc_u_id,\n" + 
            "      acc_off_id,\n" + 
            "      csh_bk_yr,\n" + 
            "      csh_bk_mnth,\n" + 
            "      acc_no,\n" + 
            "      A_2a,\n" + 
            "      DECODE(A_2b,NULL,0,A_2b)         AS A_2b,\n" + 
            "      A_2a+( DECODE(A_2b,NULL,0,A_2b)) AS A_2c,\n" + 
            "      DECODE(A_2d,NULL,0,A_2d)         AS A_2d\n" + 
            "    FROM\n" + 
            "      (SELECT acc_u_id,\n" + 
            "        acc_off_id,\n" + 
            "        csh_bk_yr,\n" + 
            "        csh_bk_mnth,\n" + 
            "        acc_no,\n" + 
            "        SUM(A_2a) AS A_2a\n" + 
            "      FROM\n" + 
            "        (SELECT acc_u_id,\n" + 
            "          acc_off_id,\n" + 
            "          csh_bk_yr,\n" + 
            "          csh_bk_mnth,\n" + 
            "          acc_no,\n" + 
            "          SUM(A_2a) AS A_2a\n" + 
            "        FROM\n" + 
            "          (SELECT ACCOUNTING_UNIT_ID AS acc_u_id,\n" + 
            "            ACCOUNTING_FOR_OFFICE_ID AS acc_off_id,\n" + 
            "            CASHBOOK_YEAR            AS csh_bk_yr,\n" + 
            "            CASHBOOK_MONTH           AS csh_bk_mnth,\n" + 
            "            Account_No               AS Acc_No,\n" + 
            "            SUM(total_amount)        AS A_2a\n" + 
            "          FROM FAS_payment_master\n" + 
            "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
            "          AND accounting_for_office_id=" +cboOffice_code+ 
            "          AND cashbook_month          = " +cboCashBook_Month+ 
            "          AND Cashbook_Year           =" +cboCashBook_Year+ 
            "          AND ACCOUNT_NO              = " +cmbBankAccNo+ 
            "          AND Payment_Status          ='L'\n" + 
            "          GROUP BY Accounting_Unit_Id,\n" + 
            "            Accounting_For_Office_Id,\n" + 
            "            Cashbook_Year,\n" + 
            "            Cashbook_Month,\n" + 
            "            Account_No\n" + 
            "          UNION ALL\n" + 
            "          SELECT ACCOUNTING_UNIT_ID  AS acc_u_id,\n" + 
            "            ACCOUNTING_FOR_OFFICE_ID AS acc_off_id,\n" + 
            "            CASHBOOK_YEAR            AS csh_bk_yr,\n" + 
            "            CASHBOOK_MONTH           AS csh_bk_mnth,\n" + 
            "            Account_No               AS Acc_No,\n" + 
            "            SUM(Total_Amount)        AS A_2a\n" + 
            "          FROM FAS_receipt_master\n" + 
            "          WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
            "          AND accounting_for_office_id=" +cboOffice_code+ 
            "          AND cashbook_month          = " +cboCashBook_Month+ 
            "          AND Cashbook_Year           =" +cboCashBook_Year+ 
            "          AND Account_No              = " +cmbBankAccNo+ 
            "          AND receipt_Status          ='L'\n" + 
            "          AND CREATED_BY_MODULE       ='SC'\n" + 
            "          GROUP BY Accounting_Unit_Id,\n" + 
            "            Accounting_For_Office_Id,\n" + 
            "            Cashbook_Year,\n" + 
            "            Cashbook_Month,\n" + 
            "            Account_No\n" + 
            " union all\n" + 
            " SELECT ACCOUNTING_UNIT_ID as acc_u_id,ACCOUNTING_FOR_OFFICE_ID as acc_off_id,\n" + 
            "         CASHBOOK_YEAR as csh_bk_yr, CASHBOOK_MONTH as csh_bk_mnth,\n" + 
            " "+cmbBankAccNo+" as Acc_No,sum(TOTAL_AMOUNT)as A_2a\n" + 
            "        FROM FAS_INTER_BANK_TRF_AT_HO\n" + 
            "        WHERE ACCOUNTING_UNIT_ID     = " +cboAcc_UnitCode+ 
            "        AND ACCOUNTING_FOR_OFFICE_ID =" +cboOffice_code+ 
            "        AND CASHBOOK_MONTH           =" +cboCashBook_Month+ 
            "        AND CASHBOOK_YEAR            = " +cboCashBook_Year+ 
            "        AND (FROM_ACCOUNT_NO            = "+cmbBankAccNo+")"+
            //Lakshmi 29Nov13
           // " or TO_ACCOUNT_NO="+cmbBankAccNo+")\n" + 
            "        AND TRANSFER_STATUS          ='L'\n" + 
            "        group by ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CASHBOOK_MONTH,CASHBOOK_YEAR\n" + 
            "          )\n" + 
            "        GROUP BY acc_u_id,\n" + 
            "          acc_off_id,\n" + 
            "          csh_bk_yr,\n" + 
            "          Csh_Bk_Mnth,\n" + 
            "          acc_no\n" + 
            "        )\n" + 
            "      GROUP BY acc_u_id,\n" + 
            "        acc_off_id,\n" + 
            "        csh_bk_yr,\n" + 
            "        csh_bk_mnth,\n" + 
            "        acc_no\n" + 
            "      )a\n" + 
            "    LEFT OUTER JOIN\n" + 
            "      (\n" + 
            "      SELECT t.ACCOUNTING_UNIT_ID AS acc_u_id1,\n" + 
            "        t.ACCOUNTING_FOR_OFFICE_ID AS acc_off_id1,\n" + 
            "        t.CASHBOOK_YEAR            AS csh_bk_yr1,\n" + 
            "        t.CASHBOOK_MONTH           AS csh_bk_mnth1,\n" + 
            "        t.sub_ledger_code               AS acc_no1,\n" + 
            "        SUM(t.AMOUNT)           AS A_2b\n" + 
            "      FROM FAS_journal_master m,fas_journal_transaction t\n" + 
            "      WHERE m.accounting_unit_id    = t.accounting_unit_id\n" + 
            "      AND m.accounting_for_office_id= t.accounting_for_office_id\n" + 
            "      AND m.cashbook_month          =  t.cashbook_month\n" + 
            "      AND m.cashbook_year           = t.cashbook_year\n" + 
            "      AND m.voucher_no           = t.voucher_no\n" + 
            "      and m.journal_status='L'\n" + 
            "      and t.cr_dr_indicator='CR'\n" + 
            "      and m.accounting_unit_id    = " +cboAcc_UnitCode+ 
            "      AND m.accounting_for_office_id= " +cboOffice_code+ 
            "      AND m.cashbook_month          =  " +cboCashBook_Month+ 
            "      AND m.cashbook_year           = " +cboCashBook_Year+ 
            "      AND t.sub_ledger_code            = " +cmbBankAccNo+ 
            "      and t.sub_ledger_type_code=6     \n" + 
            "      GROUP BY t.ACCOUNTING_UNIT_ID,\n" + 
            "        t.ACCOUNTING_FOR_OFFICE_ID,\n" + 
            "        t.CASHBOOK_YEAR,\n" + 
            "        t.CASHBOOK_MONTH,\n" + 
            "        t.sub_ledger_code\n" + 
            "      )b\n" + 
            "    ON a.acc_u_id     =b.acc_u_id1\n" + 
            "    AND a.acc_off_id  = b.acc_off_id1\n" + 
            "    AND a.csh_bk_yr   = b.csh_bk_yr1\n" + 
            "    AND a.csh_bk_mnth = b.csh_bk_mnth1\n" + 
            "    AND a.acc_no      = b.acc_no1\n" + 
            "    LEFT OUTER JOIN\n" + 
            "      (SELECT Acc_U_Id2,\n" + 
            "        Acc_Off_Id2,\n" + 
            "        Csh_Bk_Yr2,\n" + 
            "        Csh_Bk_Mnth2,\n" + 
            "        Acc_No2,\n" + 
            "        SUM(A_2d)AS A_2d\n" + 
            "      FROM\n" + 
            "        (\n" + 
            "        SELECT t.ACCOUNTING_UNIT_ID AS acc_u_id2,\n" + 
            "        t.ACCOUNTING_FOR_OFFICE_ID AS acc_off_id2,\n" + 
            "        t.CASHBOOK_YEAR            AS csh_bk_yr2,\n" + 
            "        t.CASHBOOK_MONTH           AS csh_bk_mnth2,\n" + 
            "        t.sub_ledger_code               AS acc_no2,\n" + 
            "        SUM(t.AMOUNT)           AS A_2d\n" + 
            "      FROM FAS_journal_master m,fas_journal_transaction t\n" + 
            "      WHERE m.accounting_unit_id    = t.accounting_unit_id\n" + 
            "      AND m.accounting_for_office_id= t.accounting_for_office_id\n" + 
            "      AND m.cashbook_month          =  t.cashbook_month\n" + 
            "      AND m.cashbook_year           = t.cashbook_year\n" + 
            "      AND m.voucher_no           = t.voucher_no\n" + 
            "      and m.journal_status='L'\n" + 
            "      and t.cr_dr_indicator='DR'\n" + 
            "      and m.accounting_unit_id    = " +cboAcc_UnitCode+ 
            "      AND m.accounting_for_office_id= " +cboOffice_code+ 
            "      AND m.cashbook_month          =" +cboCashBook_Month+ 
            "      AND m.cashbook_year           = " +cboCashBook_Year+ 
            "      AND t.sub_ledger_code            =" +cmbBankAccNo+ 
            "      and t.sub_ledger_type_code=6     \n" + 
            "      GROUP BY t.ACCOUNTING_UNIT_ID,\n" + 
            "        t.ACCOUNTING_FOR_OFFICE_ID,\n" + 
            "        t.CASHBOOK_YEAR,\n" + 
            "        t.CASHBOOK_MONTH,\n" + 
            "        t.sub_ledger_code\n" + 
            "        )\n" + 
            "      GROUP BY Acc_U_Id2,\n" + 
            "        Acc_Off_Id2,\n" + 
            "        Csh_Bk_Yr2,\n" + 
            "        Csh_Bk_Mnth2,\n" + 
            "        Acc_No2\n" + 
            "      )c\n" + 
            "    ON a.acc_u_id     =c.acc_u_id2\n" + 
            "    AND a.acc_off_id  = c.acc_off_id2\n" + 
            "    AND a.csh_bk_yr   = c.csh_bk_yr2\n" + 
            "    AND a.csh_bk_mnth = c.csh_bk_mnth2\n" + 
            "    AND a.acc_no      = c.acc_no2\n" + 
            "    )x\n" + 
            "  LEFT OUTER JOIN\n" + 
            "    (SELECT acc_u_id3,\n" + 
            "      acc_off_id3,\n" + 
            "      csh_bk_yr3,\n" + 
            "      "+cboCashBook_Month+" AS csh_bk_mnth3,\n" + 
            "      acc_no3,\n" + 
            "      Pay_Amt,\n" + 
            "      Sc_Amt,\n" + 
            "      A_4\n" + 
            "    FROM\n" + 
            "      (SELECT A.Acc_U_Id3,\n" + 
            "        A.Acc_Off_Id3,\n" + 
            "        A.Csh_Bk_Yr3,\n" + 
            "        A.Acc_No3,\n" + 
            "        A.Pay_Amt,\n" + 
            "        Sc_Amt,\n" + 
            "        (A.Pay_Amt+DECODE(Sc_Amt,NULL,0,Sc_Amt)) AS a_4\n" + 
            "      FROM\n" + 
            "        (SELECT ACCOUNTING_UNIT_ID                     AS acc_u_id3,\n" + 
            "          ACCOUNTING_FOR_OFFICE_ID                     AS acc_off_id3,\n" + 
            "          CASHBOOK_YEAR                                AS csh_bk_yr3,\n" + 
            "          Account_No                                   AS Acc_No3,\n" + 
            "          DECODE(SUM(DR_AMOUNT),NULL,0,SUM(DR_AMOUNT)) AS pay_amt\n" + 
            "        FROM FAS_BRS_TRANSACTION\n" + 
            "        WHERE accounting_unit_id             = " +cboAcc_UnitCode+ 
            "        AND accounting_for_office_id         =" +cboOffice_code+ 
            "        AND extract(YEAR FROM PASSBOOK_DATE) =" +cboCashBook_Year+ 
            "        AND ACCOUNT_NO                       = " +cmbBankAccNo+ 
            "        AND extract(MONTH FROM PASSBOOK_DATE)=" +cboCashBook_Month+ 
            "        AND TWAD_OR_NON_TWAD                 ='T'\n" + 
            "        AND doc_type                        IN ('P')\n" + 
            "        GROUP BY ACCOUNTING_UNIT_ID,\n" + 
            "          ACCOUNTING_FOR_OFFICE_ID,\n" + 
            "          Cashbook_Year,\n" + 
            "          Account_No\n" + 
            "        )A\n" + 
            "      LEFT OUTER JOIN\n" + 
            "        (SELECT ACCOUNTING_UNIT_ID                     AS acc_u_id3,\n" + 
            "          ACCOUNTING_FOR_OFFICE_ID                     AS acc_off_id3,\n" + 
            "          CASHBOOK_YEAR                                AS csh_bk_yr3,\n" + 
            "          Account_No                                   AS Acc_No3,\n" + 
            "          DECODE(SUM(CR_AMOUNT),NULL,0,SUM(CR_AMOUNT)) AS sc_amt\n" + 
            "        FROM FAS_BRS_TRANSACTION\n" + 
            "        WHERE accounting_unit_id             = " +cboAcc_UnitCode+ 
            "        AND accounting_for_office_id         =" +cboOffice_code+ 
            "        AND extract(YEAR FROM PASSBOOK_DATE) =" +cboCashBook_Year+ 
            "        AND ACCOUNT_NO                       = " +cmbBankAccNo+ 
            "        AND extract(MONTH FROM PASSBOOK_DATE)=" +cboCashBook_Month+ 
            "        AND TWAD_OR_NON_TWAD                 ='T'\n" + 
            "        AND doc_type                        IN ('SC')\n" + 
            "        GROUP BY ACCOUNTING_UNIT_ID,\n" + 
            "          ACCOUNTING_FOR_OFFICE_ID,\n" + 
            "          CASHBOOK_YEAR,\n" + 
            "          Cashbook_Month,\n" + 
            "          Account_No\n" + 
            "        )B\n" + 
            "      ON A.Acc_U_Id3   =B.Acc_U_Id3\n" + 
            "      AND A.acc_off_id3=B.acc_off_id3\n" + 
            "      AND A.Csh_Bk_Yr3 =B.Csh_Bk_Yr3\n" + 
            "      AND A.Acc_No3    =B.Acc_No3\n" + 
            "      )\n" + 
            "    )y ON x.acc_u_id =y.acc_u_id3\n" + 
            "  AND x.acc_off_id   = y.acc_off_id3\n" + 
            "  AND x.csh_bk_yr    = y.csh_bk_yr3\n" + 
            "  AND x.csh_bk_mnth  = y.csh_bk_mnth3\n" + 
            "  AND x.acc_no       = y.acc_no3\n" + 
            "  LEFT OUTER JOIN\n" + 
            "    (SELECT acc_u_id4,\n" + 
            "      acc_off_id4,\n" + 
            "      Acc_No4,\n" + 
            "      SUM(A_6a) AS A_6a\n" + 
            "    FROM (\n" + 
            "      (\n" + 
            "      SELECT acc_u_id4,\n" + 
            "  acc_off_id4,\n" + 
            "  acc_no4,\n" + 
            "  SUM(A_6a) AS A_6a\n" + 
            " FROM\n" + 
            "  (SELECT ACCOUNTING_UNIT_ID AS acc_u_id4,\n" + 
            "    ACCOUNTING_FOR_OFFICE_ID AS acc_off_id4,\n" + 
            "    CASHBOOK_YEAR            AS csh_bk_yr4,\n" + 
            "    CASHBOOK_MONTH           AS csh_bk_mnth4,\n" + 
            "    ACCOUNT_NO               AS acc_no4,\n" + 
            "    SUM(DR_AMOUNT)           AS A_6a\n" + 
            "  FROM FAS_BRS_TRANSACTION_NOENTRY\n" + 
            "  WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
            "  AND Accounting_For_Office_Id=" +cboOffice_code+ 
            " AND ((cashbook_year                     <"+cboCashBook_Year+" and cashbook_month<=12) or (cashbook_year="+cboCashBook_Year+" and cashbook_month<="+cboCashBook_Month+"))\n" + 
            "  AND ACCOUNT_NO              = " +cmbBankAccNo+ 
            "  AND doc_type                ='P'\n" + 
            "  GROUP BY ACCOUNTING_UNIT_ID,\n" + 
            "    ACCOUNTING_FOR_OFFICE_ID,\n" + 
            "    CASHBOOK_YEAR,\n" + 
            "    CASHBOOK_MONTH,\n" + 
            "    ACCOUNT_NO\n" + 
            "  UNION ALL\n" + 
            "  SELECT ACCOUNTING_UNIT_ID  AS acc_u_id4,\n" + 
            "    ACCOUNTING_FOR_OFFICE_ID AS acc_off_id4,\n" + 
            "    CASHBOOK_YEAR            AS csh_bk_yr4,\n" + 
            "    CASHBOOK_MONTH           AS csh_bk_mnth4,\n" + 
            "    ACCOUNT_NO               AS acc_no4,\n" + 
            "    SUM(CR_AMOUNT)           AS A_6a\n" + 
            "  FROM FAS_BRS_TRANSACTION_NOENTRY\n" + 
            "  WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
            "  AND Accounting_For_Office_Id=" +cboOffice_code+ 
            "  AND ((cashbook_year                     <"+cboCashBook_Year+" and cashbook_month<=12) or " +
            " (cashbook_year="+cboCashBook_Year+" and cashbook_month<="+cboCashBook_Month+"))\n" + 
            "  AND ACCOUNT_NO              = " +cmbBankAccNo+ 
            "  AND doc_type                ='SC'\n" + 
            "  GROUP BY ACCOUNTING_UNIT_ID,\n" + 
            "    ACCOUNTING_FOR_OFFICE_ID,\n" + 
            "    CASHBOOK_YEAR,\n" + 
            "    CASHBOOK_MONTH,\n" + 
            "    ACCOUNT_NO\n" + 
            "  union all\n" + 
            "  SELECT ACCOUNTING_UNIT_ID  AS acc_u_id4,\n" + 
            "    ACCOUNTING_FOR_OFFICE_ID AS acc_off_id4,\n" + 
            "    CASHBOOK_YEAR            AS csh_bk_yr4,\n" + 
            "    CASHBOOK_MONTH           AS csh_bk_mnth4,\n" + 
            "    ACCOUNT_NO               AS acc_no4,\n" + 
            "    SUM(DR_AMOUNT)           AS A_6a\n" + 
            "  FROM FAS_BRS_TRANSACTION\n" + 
            "  WHERE accounting_unit_id             =" +cboAcc_UnitCode+ 
            "  AND Accounting_For_Office_Id         =" +cboOffice_code+ 
            "  and PASSBOOK_DATE>('"+totalyear+"')\n" + 
            "  and (01||'-'||Cashbook_Month||'-'||cashbook_year)<=('"+totalyear+"')\n" + 
            "  AND ((cashbook_year                     <"+cboCashBook_Year+" and cashbook_month<=12) or (cashbook_year="+cboCashBook_Year+" and cashbook_month<="+cboCashBook_Month+"))\n" + 
            "  AND Account_No                       =" +cmbBankAccNo+ 
            "  AND doc_type                        IN ('P')\n" + 
            "  GROUP BY ACCOUNTING_UNIT_ID,\n" + 
            "    ACCOUNTING_FOR_OFFICE_ID,\n" + 
            "    CASHBOOK_YEAR,\n" + 
            "    CASHBOOK_MONTH,\n" + 
            "    ACCOUNT_NO\n" + 
            "  UNION ALL\n" + 
            "  SELECT ACCOUNTING_UNIT_ID  AS acc_u_id4,\n" + 
            "    ACCOUNTING_FOR_OFFICE_ID AS acc_off_id4,\n" + 
            "    CASHBOOK_YEAR            AS csh_bk_yr4,\n" + 
            "    CASHBOOK_MONTH           AS csh_bk_mnth4,\n" + 
            "    ACCOUNT_NO               AS acc_no4,\n" + 
            "    SUM(CR_AMOUNT)           AS A_6a\n" + 
            "  FROM FAS_BRS_TRANSACTION\n" + 
            "  WHERE accounting_unit_id             =" +cboAcc_UnitCode+ 
            "  AND Accounting_For_Office_Id         =" +cboOffice_code+ 
            "   and PASSBOOK_DATE>('"+totalyear+"')\n" + 
            "  and (01||'-'||Cashbook_Month||'-'||cashbook_year)<=('"+totalyear+"')\n" + 
            "  AND ((cashbook_year                     <"+cboCashBook_Year+" and cashbook_month<=12) " +
            " or (cashbook_year="+cboCashBook_Year+" and cashbook_month<="+cboCashBook_Month+"))\n" + 
            "  AND Account_No                       =" +cmbBankAccNo+ 
            "  AND doc_type                        IN ('SC')\n" + 
            "  GROUP BY ACCOUNTING_UNIT_ID,\n" + 
            "    ACCOUNTING_FOR_OFFICE_ID,\n" + 
            "    CASHBOOK_YEAR,\n" + 
            "    CASHBOOK_MONTH,\n" + 
            "    ACCOUNT_NO\n" + 
            "  )\n" + 
            " GROUP BY acc_u_id4,\n" + 
            "  Acc_Off_Id4,\n" + 
            "  acc_no4\n" + 
            "      )\n" + 
            "     )\n" + 
            "    GROUP BY Acc_U_Id4,\n" + 
            "      Acc_Off_Id4,\n" + 
            "      Acc_No4\n" + 
            "    )g\n" + 
            "  ON x.acc_u_id    =g.acc_u_id4\n" + 
            "  AND x.acc_off_id = g.acc_off_id4\n" + 
            "  AND x.acc_no     = g.acc_no4\n" + 
            "  LEFT OUTER JOIN\n" + 
            "    (SELECT ACCOUNTING_UNIT_ID AS acc_u_id5,\n" + 
            "      ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5,\n" + 
            "      CASHBOOK_YEAR            AS csh_bk_yr5,\n" + 
            "      CASHBOOK_MONTH           AS csh_bk_mnth5,\n" + 
            "      ACCOUNT_NO               AS acc_no5,\n" + 
            "      SUM(CR_AMOUNT-DR_AMOUNT) AS A_6b\n" + 
            "    FROM FAS_BRS_TRANSACTION\n" + 
            "    WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
            "    AND accounting_for_office_id=" +cboOffice_code+ 
            "    AND cashbook_year           =" +cboCashBook_Year+ 
            "    AND ACCOUNT_NO              = " +cmbBankAccNo+ 
            "    AND cashbook_month          = " +cboCashBook_Month+ 
            "    AND TWAD_OR_NON_TWAD        ='NT'\n" + 
            "    GROUP BY ACCOUNTING_UNIT_ID,\n" + 
            "      ACCOUNTING_FOR_OFFICE_ID,\n" + 
            "      CASHBOOK_YEAR,\n" + 
            "      CASHBOOK_MONTH,\n" + 
            "      ACCOUNT_NO\n" + 
            "    )g1\n" + 
            "  ON x.acc_u_id       =g1.acc_u_id5\n" + 
            "  AND x.acc_off_id    = g1.acc_off_id5\n" + 
            "  AND x.csh_bk_yr     = g1.csh_bk_yr5\n" + 
            "  AND x.csh_bk_mnth   = g1.csh_bk_mnth5\n" + 
            "  AND x.acc_no        = g1.acc_no5\n" + 
            "  )YY ON XX.acc_u_id6 = YY.acc_u_id\n" + 
            " AND XX.acc_off_id6    = YY.acc_off_id\n" + 
            " AND XX.csh_bk_yr6     = YY.csh_bk_yr\n" + 
            " AND XX.Csh_Bk_Mnth6   = YY.Csh_Bk_Mnth\n" + 
            " AND XX.acc_no6        = YY.acc_no)";
			
			System.out.println("qry::::"+qry);
			
		    if(cmd.equalsIgnoreCase("printFunc")){
		  //  System.out.println("dhanaaaaaaaaaaaaaaaaaaaa");
			try {
				
				 File reportFile = null;
					Map map = null;
					map = new HashMap();
		    	
				reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/banking_section/BRS_Report_2_BS.jasper"));
				map.put("sql", qry);
				if (!reportFile.exists())
				throw new JRRuntimeException("File J not found. The report design must be compiled first.");

			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObject(reportFile.getPath());
			

			map.put("UnitId", cboAcc_UnitCode);
			map.put("OfficeId", cboOffice_code);
			map.put("cbyear", cboCashBook_Year);
			map.put("cbmonth", cboCashBook_Month);
			map.put("accNo", cmbBankAccNo);
			map.put("month", month);
            map.put("four_b", i2);//total_i//i2
            map.put("four_c", ii);
            map.put("UnitName",UnitName);
            map.put("excess_db",i3);
            map.put("last_date_one", totalyear);
            map.put("cheq_passAmt", cheq_Amt);
            
		  //  System.out.println("four_c "+ii);
		   // System.out.println("excess_db "+excess_db);
		    
		   
		    
			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);
			System.out.println("upto");
			String rtype = "PDF";// request.getParameter("cmbReportType");
			//System.out.println(rtype);
			if (rtype.equalsIgnoreCase("PDF")) {
				System.out.println(rtype);
				byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
				System.out.println("Length  " + buf.length);
				response.setContentType("application/pdf");
				response.setContentLength(buf.length);
				response.setHeader("Content-Disposition",
						"attachment;filename=\"BRS_Part2_BS.pdf\"");
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
		    else if(cmd.equalsIgnoreCase("f_brs"))
			{
				int insertCount=0;
			//	double four_bAmount=0.0;
				double four_bs=0.0;
				int unitcode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
				int offCode=Integer.parseInt(request.getParameter("cmbOffice_code"));
				int passYear=Integer.parseInt(request.getParameter("cboCashBook_Year"));
				int passMonth=Integer.parseInt(request.getParameter("cboCashBook_Month"));
				long accNo=Long.parseLong(request.getParameter("cmbBankAccNo"));
				int exc_count=0,c_count=0;
				
					try{
				  ps=con.prepareStatement("SELECT ACCOUNTING_UNIT_ID  AS acc_u_id5,\n" + 
						    "  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5,\n" + 
						    "  Account_No               As Acc_No5,\n" + 
						    "  SUM(DR_AMOUNT) as four_b\n" + 
						    " FROM FAS_BRS_TRANSACTION\n" + 
						    " WHERE accounting_unit_id    = " + unitcode+
						    " AND accounting_for_office_id=" + offCode+" AND " +
						    " ACCOUNT_NO              = " + accNo+
						    " and (extract(YEAR FROM PASSBOOK_DATE) <"+passYear+" Or (Extract(Year From Passbook_Date)  ="+passYear+" AND extract(MONTH FROM PASSBOOK_DATE)<="+passMonth+")) "+
						    " And Twad_Or_Non_Twad        ='NT'\n" + 
						    " and TRANSACTION_TYPE in(3,23,12)\n" + 
						    " GROUP BY ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID,Account_No");
						    rs=ps.executeQuery();
						    if(rs.next()){
						         four_bAmount = rs.getDouble("four_b");
						           
					            }
					}
					catch(Exception ee)
					{
						System.out.println("exception in four_b"+ee);
					}
					 try {
						    ps=con.prepareStatement("SELECT ACCOUNTING_UNIT_ID  AS acc_u_id5,\n" + 
						    "  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5,\n" + 
						    "  CASHBOOK_YEAR            AS csh_bk_yr5,\n" + 
						    "  CASHBOOK_MONTH           AS csh_bk_mnth5,\n" + 
						    "  Account_No               As Acc_No5,\n" + 
						    "  SUM(DR_AMOUNT) as four_c\n" + 
						    " FROM FAS_BRS_TRANSACTION\n" + 
						    " WHERE accounting_unit_id    = " + unitcode+
						    " AND accounting_for_office_id=" + offCode+" AND extract(year from PASSBOOK_DATE)           ="+ passYear+
						    " AND ACCOUNT_NO              = " + accNo+
						    " AND extract(month from PASSBOOK_DATE)          = " +passMonth+
						    " And Twad_Or_Non_Twad        ='NT'\n" + 
						    " and TRANSACTION_TYPE in(3,23,12)\n" + 
						    " GROUP BY ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID, CASHBOOK_YEAR, CASHBOOK_MONTH, Account_No");
						    rs=ps.executeQuery();
						    if(rs.next())
					            {
						    	c_count++;
					                  four_cAmount=rs.getDouble("four_c");
						        }
						    if(c_count==0)
						    {
						    	four_cAmount=0.0;
						    }
						        
						    }            
						    catch (SQLException e) {
						        System.out.println("SQL Exception -->"+e);
						    }
						
						//excess
						 try {
							   
							    String sql="SELECT ACCOUNTING_UNIT_ID  AS acc_u_id5,\n" + 
							    "  ACCOUNTING_FOR_OFFICE_ID AS acc_off_id5,\n" + 
							    "  Account_No               As Acc_No5,\n" + 
							    "  SUM(DR_AMOUNT) as four_bs\n" + 
							    " FROM FAS_BRS_TRANSACTION\n" + 
							    " WHERE accounting_unit_id    = " + unitcode+
							    " AND accounting_for_office_id=" + offCode+" AND " +
							    " cashbook_year           ="+ passYear+" AND " +
							    "ACCOUNT_NO              = " + accNo+
							    " AND cashbook_month          = " +passMonth+
							    " And Twad_Or_Non_Twad        ='NT'\n" + 
							    " and TRANSACTION_TYPE in(6)\n" + 
							    " GROUP BY ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID,Account_No";
							    
							    System.out.println("sql:"+sql);
							    ps=con.prepareStatement(sql);
							    rs=ps.executeQuery();
							    if(rs.next()){
							    exc_count++;
							    	four_bs=rs.getDouble("four_bs");
							     	System.out.println("four_bs:::"+four_bs);
							     
						            }
							   if(exc_count==0)
							   {
								   four_bs=0.0;
							   }
							    
							    }
							    catch (SQLException e) {
							        System.out.println("SQL Exception -->"+e);
							    }
					            
			             
	              
	                try{
	               PreparedStatement pss=con.prepareStatement(qry);
	               ResultSet rss=pss.executeQuery();
		               while(rss.next())
		               {
		            	   System.out.println("a4:::"+rss.getDouble("A_4"));
		            	   System.out.println("four_bs:::"+four_bs);
		            	   System.out.println("four_cAmount:::"+four_cAmount);
		            	   System.out.println("a3:::"+rss.getDouble("A_3"));
		            	   
		            	   
		            	   Double closing_bal=(rss.getDouble("A_3"))-(rss.getDouble("A_4")+four_bs+four_cAmount);
		            	   
		            	   System.out.println("insert starts:"+closing_bal);
		            	   insertCount++;
		            	  PreparedStatement pss1=con.prepareStatement("insert into FAS_BRS_PART_2A (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID," +
		            	   		"PASS_SHEET_YEAR,PASS_SHEET_MONTH,ACCOUNT_NO,S1,S2A,S2B,S2C,S2D,S2E,S3,S4A,S4B,S4C,S5A,S5B," +
		            	   		"UPDATED_BY_USER_ID,UPDATED_DATE,s5) values (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
		            	   pss1.setInt(1,unitcode);
		            	   pss1.setInt(2,offCode);
		            	   pss1.setInt(3,passYear);
		            	   pss1.setInt(4,passMonth);
		            	   pss1.setLong(5,accNo);
		            	   pss1.setDouble(6,rss.getDouble("A_1"));
		            	   pss1.setDouble(7,rss.getDouble("A_2a"));
		            	   pss1.setDouble(8,rss.getDouble("A_2b"));
		            	   pss1.setDouble(9,rss.getDouble("A_2c"));
		            	   pss1.setDouble(10,rss.getDouble("A_2d"));
		            	   
		            	   pss1.setDouble(11,rss.getDouble("A_2e"));
		            	   pss1.setDouble(12,rss.getDouble("A_3"));
		            	   pss1.setDouble(13,rss.getDouble("A_4"));//s4a
		            	   pss1.setDouble(14,four_bs);
		            	   pss1.setDouble(15,four_cAmount);
		            	   pss1.setDouble(16,rss.getDouble("A_6a"));
		            	   pss1.setDouble(17,four_bAmount);
		            	//   double total_four=four_cAmount+four_bAmount;
		            	//   pss1.setDouble(17,total_four);
		            	   //s5b+s4c=four_bAmount+four_cAmount
		            	   pss1.setString(18,update_user);
		                   pss1.setTimestamp(19,ts);
		                   pss1.setDouble(20,closing_bal);
		                   int jk=pss1.executeUpdate();
		                   System.out.println("value jk:::"+jk);
		            	 //  pss1.setInt(15,rss.getInt("total2"));
		            	   
		               }
		               if(insertCount>0)
		               {
		            	   System.out.println("inserted");
		            	   con.commit();
							con.setAutoCommit(true);
							sendMessage(response,"Records Inserted Successfully  ","ok");
		               }
		               else
		               {
		            	   con.rollback();
							con.setAutoCommit(true);
		            	   sendMessage(response,"Records Not Inserted into Part-2A ","ok");   
		               }
		            
		               
	                }
	                catch(Exception ee)
	                {
	                	System.out.println("exception in fetching query::::"+ee);
	                }
	               
			}
		    
		   
		    
	}
	private void sendMessage(HttpServletResponse response, String msg,
			String bType) {
		try {
			String url = "org/Library/jsps/MessengerOkBack.jsp?message=" + msg
					+ "&button=" + bType;
			response.sendRedirect(url);
			return;
		} catch (IOException e) {
			e.printStackTrace();
		}
	}
}
