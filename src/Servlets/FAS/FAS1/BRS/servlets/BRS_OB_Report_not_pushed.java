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
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lowagie.tools.plugins.Txt2Pdf;

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
 * Servlet implementation class BRS_OB_Report_not_pushed
 */
public class BRS_OB_Report_not_pushed extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public BRS_OB_Report_not_pushed() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		System.out.println("not pushed::::");
		
		Connection con = null;
	String qu=null;
	String heading="";
	    String UnitName="",OfficeName="",y_bank_name="";
	    String z_BRANCH_NAME="";
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
		String command=request.getParameter("command");
		int cboAcc_UnitCode = Integer.parseInt(request
				.getParameter("cmbAcc_UnitCode"));
		int cboOffice_code = Integer.parseInt(request
				.getParameter("cmbOffice_code"));
		int cboCashBook_Year = Integer.parseInt(request
				.getParameter("txtCB_Year"));
		int cboCashBook_Month = Integer.parseInt(request
				.getParameter("txtCB_Month"));
		long cmbBankAccNo =Long.parseLong(request
				.getParameter("cmbBankAccNo"));
		String acno=request.getParameter("cmbBankAccNo");
		System.out.println("acno:::"+acno);
		String hid=request.getParameter("old");
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
			 
			    PreparedStatement ps=null;
			    ResultSet rs=null;
			    
			    ps=con.prepareStatement("SELECT * "+
					"	FROM "+
			    		"	  (SELECT bank_id, "+
			    		"	BRANCH_ID, "+
			    		"	bank_ac_no, "+
			    		"	AC_OPERATIONAL_MODE_ID, "+
			    		"	trim(AC_OPERATIONAL_MODE_ID) "+
			    		"	||'-' "+
			    		"	||trim(bank_ac_no) AS acc_no "+
			    		"	From Fas_mst_Bank_Balance "+
			    		"	WHERE accounting_unit_id  = ? "+
			    		"	AND AC_OPERATIONAL_MODE_ID='COL' "+
			    		"	)X "+
			    		"	LEFT OUTER JOIN "+
			    		"	(SELECT bank_id   AS y_bank_id ,trim(bank_name) AS y_bank_name FROM fas_bank_list "+
						"  )Y "+
			    		" ON X.bank_id = Y.y_bank_id "+
			    		" LEFT OUTER JOIN "+
			    		"  (SELECT BANK_ID     AS z_bank_id,BRANCH_ID         AS z_BRANCH_ID , "+
						"    trim(BRANCH_NAME) AS z_BRANCH_NAME FROM fas_mst_bank_branches "+
						"  )Z "+
						" ON X.bank_id    = Z.z_bank_id "+
						" AND X.BRANCH_ID = Z.z_branch_id");
			    ps.setInt(1,cboAcc_UnitCode);
			    rs=ps.executeQuery();
			    if(rs.next())
			    	y_bank_name="Bank Name :  "+rs.getString("y_bank_name");
			    if(rs.getString("z_BRANCH_NAME")==null)
			    {
			    	z_BRANCH_NAME="Branch Name :  -";
			    }
			    else{
			    z_BRANCH_NAME="Branch Name :  "+rs.getString("z_BRANCH_NAME");
			    }
			   
			    }
			    catch (SQLException e) {
			        System.out.println("SQL Exception -->"+e);
			    }
		
		
	    try {
	    PreparedStatement ps=null,prs=null;
	    ResultSet rs=null;
	    
	    ps=con.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
	    ps.setInt(1,cboAcc_UnitCode);
	    rs=ps.executeQuery();
	    if(rs.next())
	         UnitName=rs.getString("ACCOUNTING_UNIT_NAME");
	    
	   
	    }
	    catch (SQLException e) {
	        System.out.println("SQL Exception -->"+e);
	    }
	    if(command.equalsIgnoreCase("one_fivea")){
	    System.out.println("if loop:");
	    PreparedStatement prs=null;
	    ResultSet resu=null;
	    String smonth="",totalyear=null;
	    try{
	    	String h="select to_char(last_day(to_date(date1, 'dd-mm-yy')),'dd-mm-yyyy')ls_date  from "+
            " (select distinct ('01'||'-'||CASHBOOK_MONTH||'-'||CASHBOOK_YEAR)date1 "+
            " from FAS_BRS_TRANSACTION where CASHBOOK_YEAR="+cboCashBook_Year+" and " +
            "CASHBOOK_MONTH="+cboCashBook_Month+" and accounting_unit_id="+cboAcc_UnitCode+")";
	    //	System.out.println(h);
	    	prs=con.prepareStatement(h);
	    	 resu=prs.executeQuery();
			    if(resu.next())
			    {
			      String last_date_one =resu.getString("ls_date");
			//    System.out.println("last_date_one::"+last_date_one);
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
			   // System.out.println("totalyear:::"+totalyear);
			    }
	    }
	    catch(Exception ee)
	    {
	    	
	    }
	    
	    try {
		   
		
	    	
	    	 qu="SELECT a.ACCOUNTING_UNIT_ID,\n" + 
	            "  (SELECT u.ACCOUNTING_UNIT_NAME\n" + 
	            "  FROM fas_mst_acct_units u\n" + 
	            "  WHERE u.ACCOUNTING_UNIT_ID=a.ACCOUNTING_UNIT_ID\n" + 
	            "  )AS unit_name,\n" + 
	            "  Cashbook_Year,\n" + 
	            "  cashbook_month,\n" + 
	            "  ENTRY_DATE,\n" + 
	            "  CASE\n" + 
	            "    WHEN TWAD_OR_NON_TWAD='T'\n" + 
	            "    THEN 'TWAD'\n" + 
	            "    ELSE 'NON-TWAD'\n" + 
	            "  END AS TWAD_OR_NON_TWAD,\n" + 
	            "  (SELECT TRANS_DESC\n" + 
	            "  FROM FAS_BRS_TRANSACTION_TYPE t\n" + 
	            "  WHERE t.TRANS_CODE=a.Transaction_Type\n" + 
	            "  )AS trn_desc,\n" + 
	            "  CHEQUE_DD_NO,\n" + 
	            "  CR_AMOUNT,\n" + 
	            "  DR_AMOUNT,\n" + 
	            "  ACCOUNT_NO,\n" + 
	            "  DOC_DATE,\n" + 
	            "  DOC_NO,\n" + 
	            "  DOC_TYPE,\n" + 
	            "  PASSBOOK_DATE\n" +
	            "FROM Fas_Brs_Transaction_Noentry a\n" + 
	            "WHERE Accounting_Unit_Id    =" +cboAcc_UnitCode+ 
	            "AND Accounting_For_Office_Id=" +cboOffice_code+ 
	            "AND ((cashbook_year         <" +cboCashBook_Year+ 
	            "AND cashbook_month         <=12)\n" + 
	            "OR (cashbook_year           ="+cboCashBook_Year+ 
	            "AND cashbook_month         <="+cboCashBook_Month+"))\n" + 
	            "AND ACCOUNT_NO              = " +cmbBankAccNo+ 
	            "AND TWAD_OR_NON_TWAD        ='T'\n" + 
	            "AND doc_type               IN ('CR', 'BR','FR by HO', 'FR by Office')\n" + 
	            "union all\n" + 
	            " SELECT a.ACCOUNTING_UNIT_ID, "+
	            //(01||'-'||Cashbook_Month||'-'||cashbook_year)as cy_cb,PASSBOOK_DATE,
	            "  (SELECT u.ACCOUNTING_UNIT_NAME  "+
	            " FROM fas_mst_acct_units u "+
	            " WHERE u.ACCOUNTING_UNIT_ID=a.ACCOUNTING_UNIT_ID "+
	            " )AS unit_name, "+
	            " Cashbook_Year, "+
	            " cashbook_month, "+
	            " ENTRY_DATE, "+
	            " CASE "+
	            "   WHEN TWAD_OR_NON_TWAD='T' "+
	            "   THEN 'TWAD' "+
	            "   ELSE 'NON-TWAD' "+
	            " END AS TWAD_OR_NON_TWAD, "+
	            " (SELECT TRANS_DESC "+
	            " FROM FAS_BRS_TRANSACTION_TYPE t "+
	            " WHERE t.TRANS_CODE=a.Transaction_Type "+
	            " )AS trn_desc, "+
	            " CHEQUE_DD_NO, "+
	            " CR_AMOUNT,DR_AMOUNT,ACCOUNT_NO, "+
	            " DOC_DATE,DOC_NO,DOC_TYPE,PASSBOOK_DATE " + 
	            " FROM FAS_BRS_TRANSACTION a "+
	            " WHERE accounting_unit_id                = "+cboAcc_UnitCode+
	            " AND Accounting_For_Office_Id            = "+cboOffice_code+
	         //   " and PASSBOOK_DATE>'31-dec-2010' "+
	            " and PASSBOOK_DATE>('"+totalyear+"')"+ 
	          //  "  and (01||'-'||Cashbook_Month||'-'||cashbook_year)<='31-dec-2010' "+
	            "  and (01||'-'||Cashbook_Month||'-'||cashbook_year)<=('"+totalyear+"') "+
	           " AND ((cashbook_year                     <"+cboCashBook_Year+" and cashbook_month<=12) or (cashbook_year="+cboCashBook_Year+" and cashbook_month<="+cboCashBook_Month+")) "+
	            //"  AND Cashbook_Month                      = "+cboCashBook_Month+
	           // "  AND cashbook_year                       = "+cboCashBook_Year+
	            "  AND Account_No                          = "+cmbBankAccNo+
	            " AND Twad_Or_Non_Twad                    ='T' "+
	            "  AND doc_type                           IN ('CR', 'BR','FR by HO', 'FR by Office') "+
	            "  ORDER BY Doc_Date, "+
	            "   CHEQUE_DD_NO ";
	    	
		    heading="Part-1 5a BreakUp";
	    }
		    catch (Exception e) {
		        System.out.println("Exception -->"+e);
		    }

	    }
	    else if(command.equalsIgnoreCase("not_pushed")){
	    	String twadtye=request.getParameter("twadtype");
	    	if(twadtye.equals("T")){
            qu="SELECT a.ACCOUNTING_UNIT_ID,\n" + 
            "(select u.ACCOUNTING_UNIT_NAME from fas_mst_acct_units u where u.ACCOUNTING_UNIT_ID=a.ACCOUNTING_UNIT_ID)as unit_name,\n" + 
            "  ENTRY_DATE,\n" + 
            "  SL_NO,\n" + 
            "  TWAD_OR_NON_TWAD,\n" + 
            "  CHEQUE_DD_NO,\n" + 
            "  CR_AMOUNT,\n" + 
            "  DR_AMOUNT,\n" + 
            "  ACCOUNT_NO,\n" + 
            "  DOC_DATE,\n" + 
            "  DOC_NO,\n" + 
            "  DOC_TYPE\n" + 
            " From Fas_Brs_Ob_Transaction a\n" + 
            " Where Accounting_Unit_Id    =" +cboAcc_UnitCode+
            " And Accounting_For_Office_Id=" +cboOffice_code+
            " And Cashbook_Year           =" +cboCashBook_Year+  
            " And Cashbook_Month          =" +cboCashBook_Month+ 
          /*  "  AND (( Cashbook_Year           =" +cboCashBook_Year+  
 " AND Cashbook_Month          <=" +cboCashBook_Month+" ) "+
 " or Cashbook_Year           < " +cboCashBook_Year+  " ) "+*/
            " AND ACCOUNT_NO              =" +cmbBankAccNo;
           
            heading="BRS OB Report For Twad";
	    	}
            else
            {
            	qu="SELECT a.ACCOUNTING_UNIT_ID, "+
            	 " (SELECT u.ACCOUNTING_UNIT_NAME "+
            	" FROM fas_mst_acct_units u "+
            	"  WHERE u.ACCOUNTING_UNIT_ID=a.ACCOUNTING_UNIT_ID "+
            	"   )AS unit_name, "+
            	"  PASS_BOOK_DATE, "+
            	"  OB_TYPE,  "+
            	"   CHEQUE_NO, "+
            	"   CR_AMOUNT, "+
            	"  DR_AMOUNT, "+
            	"   ACCOUNT_NO, "+
            	"   OB_PART1, "+
            	" OB_PART2A, "+
            	" OB_PART2B "+
            	" FROM FAS_BRS_OB a "+
            	" WHERE Accounting_Unit_Id    = "+cboAcc_UnitCode+
            	" AND Accounting_For_Office_Id= "+cboOffice_code+
            	" AND Cashbook_Year           = "+ cboCashBook_Year+
            	" AND Cashbook_Month          = "+ cboCashBook_Month+
            	" AND ACCOUNT_NO              = " +cmbBankAccNo+
            	" and OB_TYPE='NT' ";
            	  heading="BRS OB Report For Non-Twad";
            }
            
	    }

	    else if(command.equalsIgnoreCase("both_five")){
	  //for both (five a and b) Part-2a reports breakup
	    	String totalyear="";
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
	    	
	    	String breakup_type=request.getParameter("breakup_type");
	    if(breakup_type.equalsIgnoreCase("5a"))
	    {
	    	   qu="SELECT Cashbook_Year,\n" + 
	           "  CASHBOOK_MONTH,\n" + 
	           "  a.ACCOUNTING_UNIT_ID,\n" + 
	           "  (SELECT u.ACCOUNTING_UNIT_NAME\n" + 
	           "  FROM fas_mst_acct_units u\n" + 
	           "  WHERE u.ACCOUNTING_UNIT_ID=a.ACCOUNTING_UNIT_ID\n" + 
	           "  )AS unit_name,\n" + 
	           "  ENTRY_DATE,\n" + 
	           "  CASE\n" + 
	           "    WHEN Twad_Or_Non_Twad='T'\n" + 
	           "    THEN 'TWAD'\n" + 
	           "    ELSE 'NON-TWAD'\n" + 
	           "  END AS Twad_Or_Non_Twad,\n" + 
	      /*     "  (SELECT TRANS_DESC\n" + 
	           "  FROM FAS_BRS_TRANSACTION_TYPE t\n" + 
	           "  WHERE t.TRANS_CODE=a.Transaction_Type\n" + 
	           "  )AS trn_desc,\n" + */
	           "  CASE\n" + 
	           "   WHEN A.TRANSACTION_TYPE IS NOT NULL\n" + 
	           "   AND A.TRANSACTION_TYPE NOT LIKE 'NA'\n" + 
	           "   THEN\n" + 
	           "     (SELECT TRANS_DESC\n" + 
	           "    FROM FAS_BRS_TRANSACTION_TYPE t\n" + 
	           "  WHERE T.TRANS_CODE=to_number(A.TRANSACTION_TYPE)\n" + 
	           "   )\n" + 
	           "    ELSE '-'  END AS trn_desc,\n" + 
	           "  CHEQUE_DD_NO,\n" + 
	           "  CR_AMOUNT,\n" + 
	           "  DR_AMOUNT,\n" + 
	           "  ACCOUNT_NO,\n" + 
	           "  DOC_DATE,\n" + 
	           "  DOC_NO,\n" + 
	           "  Doc_Type,\n" + 
	           "  PASSBOOK_DATE\n" +
	            " FROM FAS_BRS_TRANSACTION_NOENTRY a\n" + 
	           " WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
	           " AND Accounting_For_Office_Id=" +cboOffice_code+ 
	           " AND ((cashbook_year                     <"+cboCashBook_Year+" and cashbook_month<=12) or (cashbook_year="+cboCashBook_Year+" and cashbook_month<="+cboCashBook_Month+"))\n" + 
	           " AND ACCOUNT_NO              = " +cmbBankAccNo+ 
	           " AND doc_type                ='P'\n" + 
	           " UNION ALL\n" + 
	           " SELECT Cashbook_Year,\n" + 
	           "  CASHBOOK_MONTH,\n" + 
	           "  a.ACCOUNTING_UNIT_ID,\n" + 
	           "  (SELECT u.ACCOUNTING_UNIT_NAME\n" + 
	           "  FROM fas_mst_acct_units u\n" + 
	           "  WHERE u.ACCOUNTING_UNIT_ID=a.ACCOUNTING_UNIT_ID\n" + 
	           "  )AS unit_name,\n" + 
	           "  ENTRY_DATE,\n" + 
	           "  CASE\n" + 
	           "    WHEN Twad_Or_Non_Twad='T'\n" + 
	           "    THEN 'TWAD'\n" + 
	           "    ELSE 'NON-TWAD'\n" + 
	           "  END AS Twad_Or_Non_Twad,\n" + 
	       /*    "  (SELECT TRANS_DESC\n" + 
	           "  FROM FAS_BRS_TRANSACTION_TYPE t\n" + 
	           "  WHERE t.TRANS_CODE=a.Transaction_Type\n" + 
	           "  )AS trn_desc,\n" + */
	           "  CASE\n" + 
	           "   WHEN A.TRANSACTION_TYPE IS NOT NULL\n" + 
	           "   AND A.TRANSACTION_TYPE NOT LIKE 'NA'\n" + 
	           "   THEN\n" + 
	           "     (SELECT TRANS_DESC\n" + 
	           "    FROM FAS_BRS_TRANSACTION_TYPE t\n" + 
	           "  WHERE T.TRANS_CODE=to_number(A.TRANSACTION_TYPE)\n" + 
	           "   )\n" + 
	           "    ELSE '-'  END AS trn_desc,\n" + 
	           "  CHEQUE_DD_NO,\n" + 
	           "  CR_AMOUNT AS DR_AMOUNT,\n" + 
	           "  DR_AMOUNT AS CR_AMOUNT,\n" + 
	           "  ACCOUNT_NO,\n" + 
	           "  DOC_DATE,\n" + 
	           "  DOC_NO,\n" + 
	           "  Doc_Type,\n" + 
	           "  PASSBOOK_DATE\n" + 
	           " FROM FAS_BRS_TRANSACTION_NOENTRY a\n" + 
	           " WHERE accounting_unit_id    = " +cboAcc_UnitCode+ 
	           " AND Accounting_For_Office_Id=" +cboOffice_code+ 
	           " AND ((cashbook_year                     <"+cboCashBook_Year+" and cashbook_month<=12) or (cashbook_year="+cboCashBook_Year+" and cashbook_month<="+cboCashBook_Month+"))\n" + 
	           " AND ACCOUNT_NO              =" +cmbBankAccNo+ 
	        //  "AND doc_type                ='SC'\n" + 
	         "  AND doc_type in('SC','IBT') \n"+
	           " UNION ALL\n" + 
	           " SELECT Cashbook_Year,\n" + 
	           "  CASHBOOK_MONTH,\n" + 
	           "  a.ACCOUNTING_UNIT_ID,\n" + 
	           "  (SELECT u.ACCOUNTING_UNIT_NAME\n" + 
	           "  FROM fas_mst_acct_units u\n" + 
	           "  WHERE u.ACCOUNTING_UNIT_ID=a.ACCOUNTING_UNIT_ID\n" + 
	           "  )AS unit_name,\n" + 
	           "  ENTRY_DATE,\n" + 
	           "  CASE\n" + 
	           "    WHEN Twad_Or_Non_Twad='T'\n" + 
	           "    THEN 'TWAD'\n" + 
	           "    ELSE 'NON-TWAD'\n" + 
	           "  END AS Twad_Or_Non_Twad,\n" + 
	        /*   "  (SELECT TRANS_DESC\n" + 
	           "  FROM FAS_BRS_TRANSACTION_TYPE t\n" + 
	           "  WHERE t.TRANS_CODE=a.Transaction_Type\n" + 
	           "  )AS trn_desc,\n" + */
	           "  CASE\n" + 
	           "   WHEN A.TRANSACTION_TYPE IS NOT NULL\n" + 
	           "   AND A.TRANSACTION_TYPE NOT LIKE 'NA'\n" + 
	           "   THEN\n" + 
	           "     (SELECT TRANS_DESC\n" + 
	           "    FROM FAS_BRS_TRANSACTION_TYPE t\n" + 
	           "  WHERE T.TRANS_CODE=to_number(A.TRANSACTION_TYPE)\n" + 
	           "   )\n" + 
	           "    ELSE '-'  END AS trn_desc,\n" + 
	           "  CHEQUE_DD_NO,\n" + 
	           "  CR_AMOUNT,\n" + 
	           "  DR_AMOUNT,\n" + 
	           "  ACCOUNT_NO,\n" + 
	           "  DOC_DATE,\n" + 
	           "  DOC_NO,\n" + 
	           "  Doc_Type,\n" + 
	           " PASSBOOK_DATE\n" +
	           " FROM Fas_Brs_Transaction A\n" + 
	           " WHERE accounting_unit_id              =" +cboAcc_UnitCode+ 
	           " AND Accounting_For_Office_Id          =" +cboOffice_code+ 
	           " and PASSBOOK_DATE>('"+totalyear+"')\n" + 
	           "  and (01||'-'||Cashbook_Month||'-'||cashbook_year)<=('"+totalyear+"')\n" + 
	           "  AND ((cashbook_year                     <"+cboCashBook_Year+" and cashbook_month<=12) or (cashbook_year="+cboCashBook_Year+" and cashbook_month<="+cboCashBook_Month+"))\n" + 
	           " AND Account_No                        =" +cmbBankAccNo+ 
	           " AND doc_type                         IN('P')\n" + 
	           " UNION ALL\n" + 
	           " SELECT Cashbook_Year,\n" + 
	           "  CASHBOOK_MONTH,\n" + 
	           "  a.ACCOUNTING_UNIT_ID,\n" + 
	           "  (SELECT u.ACCOUNTING_UNIT_NAME\n" + 
	           "  FROM fas_mst_acct_units u\n" + 
	           "  WHERE u.ACCOUNTING_UNIT_ID=a.ACCOUNTING_UNIT_ID\n" + 
	           "  )AS unit_name,\n" + 
	           "  ENTRY_DATE,\n" + 
	           "  CASE\n" + 
	           "    WHEN Twad_Or_Non_Twad='T'\n" + 
	           "    THEN 'TWAD'\n" + 
	           "    ELSE 'NON-TWAD'\n" + 
	           "  END AS Twad_Or_Non_Twad,\n" + 
	        /*   "  (SELECT TRANS_DESC\n" + 
	           "  FROM FAS_BRS_TRANSACTION_TYPE t\n" + 
	           "  WHERE t.TRANS_CODE=a.Transaction_Type\n" + 
	           "  )AS trn_desc,\n" + */
	           "  CASE\n" + 
	           "   WHEN A.TRANSACTION_TYPE IS NOT NULL\n" + 
	           "   AND A.TRANSACTION_TYPE NOT LIKE 'NA'\n" + 
	           "   THEN\n" + 
	           "     (SELECT TRANS_DESC\n" + 
	           "    FROM FAS_BRS_TRANSACTION_TYPE t\n" + 
	           "  WHERE T.TRANS_CODE=to_number(A.TRANSACTION_TYPE)\n" + 
	           "   )\n" + 
	           "    ELSE '-'  END AS trn_desc,\n" + 
	           "  CHEQUE_DD_NO,\n" + 
	           "  CR_AMOUNT ,\n" + 
	           "  DR_AMOUNT ,\n" + 
	           "  ACCOUNT_NO,\n" + 
	           "  DOC_DATE,\n" + 
	           "  DOC_NO,\n" + 
	           "  Doc_Type,\n" + 
	           "  PASSBOOK_DATE\n" +
	           " FROM Fas_Brs_Transaction A\n" + 
	           " WHERE accounting_unit_id              = " +cboAcc_UnitCode+ 
	           " AND Accounting_For_Office_Id          =" +cboOffice_code+ 
	           " AND Account_No                        =" +cmbBankAccNo+ 
	           " and PASSBOOK_DATE>('"+totalyear+"')\n" + 
	           " and (01||'-'||Cashbook_Month||'-'||cashbook_year)<=('"+totalyear+"')\n" + 
	           " AND ((cashbook_year                     <"+cboCashBook_Year+" and cashbook_month<=12) or (cashbook_year="+cboCashBook_Year+" and cashbook_month<="+cboCashBook_Month+"))\n" + 
	           " AND doc_type                         IN('SC','IBT')"; 
	    	
	    	 heading="Part-2 5a BreakUp";
	    }
	    else if(breakup_type.equalsIgnoreCase("5b"))
	    {
	           qu="SELECT b.Accounting_Unit_Id,\n" + 
	           "  (SELECT u.ACCOUNTING_UNIT_NAME\n" + 
	           "  FROM fas_mst_acct_units u\n" + 
	           "  WHERE U.Accounting_Unit_Id=b.Accounting_Unit_Id\n" + 
	           "  )                        AS unit_name,\n" + 
	           "  Accounting_For_Office_Id AS Acc_Off_Id5,\n" + 
	           "  Cashbook_Year,\n" + 
	           "  Cashbook_Month,\n" + 
	           "  Account_No AS Acc_No5,\n" + 
	           "  ENTRY_DATE,\n" + 
	           "  CASE\n" + 
	           "    WHEN Twad_Or_Non_Twad='T'\n" + 
	           "    THEN 'TWAD'\n" + 
	           "    ELSE 'NON-TWAD'\n" + 
	           "  END AS Twad_Or_Non_Twad,\n" + 
	           "  (SELECT TRANS_DESC\n" + 
	           "  FROM FAS_BRS_TRANSACTION_TYPE t\n" + 
	           "  WHERE t.TRANS_CODE=b.Transaction_Type\n" + 
	           "  )AS trn_desc,\n" + 
	           "  CHEQUE_DD_NO,\n" + 
	           "  CR_AMOUNT,\n" + 
	           "  DR_AMOUNT,\n" + 
	           "  ACCOUNT_NO,\n" + 
	           "  DOC_DATE,\n" + 
	           "  Doc_No,\n" + 
	           "  Doc_Type,\n" + 
	           " PASSBOOK_DATE\n" +
	           " FROM FAS_BRS_TRANSACTION b\n" + 
	           " WHERE accounting_unit_id              = " +cboAcc_UnitCode+ 
	           " AND Accounting_For_Office_Id          = " +cboOffice_code+ 
	           " AND (extract(YEAR FROM PASSBOOK_DATE) < " +cboCashBook_Year+ 
	           " OR (Extract(YEAR FROM Passbook_Date)  = " +cboCashBook_Year+ 
	           " AND extract(MONTH FROM PASSBOOK_DATE)<="+cboCashBook_Month+"))\n" + 
	           " AND ACCOUNT_NO                        = " +cmbBankAccNo+ 
	           " AND Twad_Or_Non_Twad                  ='NT'\n" + 
	          // " AND TRANSACTION_TYPE                 IN(3,23,12)\n" + 
	           " AND TRANSACTION_TYPE                  IN(1,3,7,23,12,26,6,8,25)\n" +
	           " AND (CLEARED_BASED_ON_FOLLOWUP IS NULL OR CLEARED_BASED_ON_FOLLOWUP    ='N')\n" + 
	           " union all\n" + 
	           " SELECT b.Accounting_Unit_Id,\n" + 
	           "  (SELECT u.ACCOUNTING_UNIT_NAME\n" + 
	           "  FROM fas_mst_acct_units u\n" + 
	           "  WHERE U.Accounting_Unit_Id=b.Accounting_Unit_Id\n" + 
	           "  )                        AS unit_name,\n" + 
	           "  Accounting_For_Office_Id AS Acc_Off_Id5,\n" + 
	           "  Cashbook_Year,\n" + 
	           "  Cashbook_Month,\n" + 
	           "  Account_No AS Acc_No5,\n" + 
	           "  ENTRY_DATE,\n" + 
	           "  CASE\n" + 
	           "    WHEN Twad_Or_Non_Twad='T'\n" + 
	           "    THEN 'TWAD'\n" + 
	           "    ELSE 'NON-TWAD'\n" + 
	           "  END AS Twad_Or_Non_Twad,\n" + 
	           "  (SELECT TRANS_DESC\n" + 
	           "  FROM FAS_BRS_TRANSACTION_TYPE t\n" + 
	           "  WHERE t.TRANS_CODE=b.Transaction_Type\n" + 
	           "  )AS trn_desc,\n" + 
	           "  CHEQUE_DD_NO,\n" + 
	           "  CR_AMOUNT,\n" + 
	           "  DR_AMOUNT,\n" + 
	           "  ACCOUNT_NO,\n" + 
	           "  DOC_DATE,\n" + 
	           "  Doc_No,\n" + 
	           "  Doc_Type,\n" + 
	           " PASSBOOK_DATE\n" +
	           " FROM FAS_BRS_TRANSACTION b\n" + 
	           " WHERE accounting_unit_id              = " +cboAcc_UnitCode+ 
	           " AND Accounting_For_Office_Id          = " +cboOffice_code+ 
	           " AND (extract(YEAR FROM PASSBOOK_DATE) < " +cboCashBook_Year+ 
	           " OR (Extract(YEAR FROM Passbook_Date)  = " +cboCashBook_Year+ 
	           " AND extract(MONTH FROM PASSBOOK_DATE)<="+cboCashBook_Month+"))\n" + 
	           " AND ACCOUNT_NO                        = " +cmbBankAccNo+ 
	           " AND Twad_Or_Non_Twad                  ='NT'\n" + 
	         // " AND TRANSACTION_TYPE                 IN(3,23,12)\n" + 
	           " AND TRANSACTION_TYPE                  IN(1,3,7,23,12,26,6,8,25)\n" +
	           " AND (CLEARED_BASED_ON_FOLLOWUP='Y' AND clearence_date            >'"+totalyear+"')";  
	    	 heading="Part-2 5b BreakUp";
	    }
	}
	    System.out.println("part-2 5b::::"+qu);
		File reportFile = null;
		try {
			
			if(command.equalsIgnoreCase("not_pushed")){
				String type=request.getParameter("twadtype");
				if(type.equals("T"))
				{
					reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/brs_ob_not_pushed.jasper"));
				}
				else
				{
					reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/BRS_OB_NT_Report.jasper"));
				}
			}
			else
			{
				if(hid.equalsIgnoreCase("old")){
				System.out.println("five a,b servlet...");
				reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/brs_ob_not_pushed_fivea.jasper"));}
				else{
				 	 heading="Part-1 5c BreakUp";
					reportFile=	new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/Breakup_5c.jasper"));
				}
			
			
			}
			if (!reportFile.exists())
				throw new JRRuntimeException("File J not found. The report design must be compiled first.");

			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObject(reportFile.getPath());
			System.out.println("reportFile "+reportFile);
			Map map = null;
			map = new HashMap();
			if(command.equalsIgnoreCase("not_pushed")){
			map.put("query", qu);
			map.put("heading", heading);
			}else if(hid.equals("new")){
				String yr="For The Month "+month+"-"+cboCashBook_Year;
				
				map.put("unit_id", cboAcc_UnitCode);
				map.put("office_id", cboOffice_code);
				map.put("month",cboCashBook_Month);
				map.put("month_1", yr);
				map.put("year", cboCashBook_Year);
				map.put("AccNo",cmbBankAccNo);
				map.put("y_bank_name", y_bank_name);
				map.put("z_BRANCH_NAME",z_BRANCH_NAME);
			}
			else
			{
				String yr="For The Month "+month+"-"+cboCashBook_Year;
				map.put("query", qu);
				map.put("heading", heading);
				map.put("month",yr);
				map.put("y_bank_name", y_bank_name);
				map.put("z_BRANCH_NAME",z_BRANCH_NAME);
				
				
				
				
			}
			System.out.println("map :: "+map);
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, map, con);
			System.out.println("upto");
			String rtype = "PDF";// request.getParameter("cmbReportType");
			System.out.println(rtype);
			if (rtype.equalsIgnoreCase("HTML")) {
				response.setContentType("text/html");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"BRS_Report.html\"");
				PrintWriter out = response.getWriter();
				JRHtmlExporter exporter = new JRHtmlExporter();
				exporter
						.setParameter(
								JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
								false);
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
				exporter.exportReport();
				out.flush();
				out.close();
			} else if (rtype.equalsIgnoreCase("PDF")) {
				System.out.println(rtype);
				byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
				System.out.println("Length  " + buf.length);
				response.setContentType("application/pdf");
				response.setContentLength(buf.length);
					response.setHeader("Content-Disposition",
						"attachment;filename=\"BRS_Report.pdf\"");
				OutputStream out = response.getOutputStream();
				out.write(buf, 0, buf.length);
				out.close();
			} else if (rtype.equalsIgnoreCase("EXCEL")) {

				response.setContentType("application/vnd.ms-excel");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"Receipt.xls\"");
				JRXlsExporter exporterXLS = new JRXlsExporter();
				exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
						jasperPrint);

				ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
				exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
						xlsReport);
				exporterXLS.setParameter(
						JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
						Boolean.FALSE);
				exporterXLS.setParameter(
						JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,
						Boolean.TRUE);
				exporterXLS.setParameter(
						JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
						Boolean.FALSE);
				exporterXLS
						.setParameter(
								JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
								Boolean.TRUE);
				exporterXLS.exportReport();
				byte[] bytes;
				bytes = xlsReport.toByteArray();
				ServletOutputStream ouputStream = response.getOutputStream();
				ouputStream.write(bytes, 0, bytes.length);
				ouputStream.flush();
				ouputStream.close();

			} else if (rtype.equalsIgnoreCase("TXT")) {

				response.setContentType("text/plain");
				response.setHeader("Content-Disposition",
						"attachment;filename=\"Receipt.txt\"");

				JRTextExporter exporter = new JRTextExporter();
				exporter.setParameter(JRExporterParameter.JASPER_PRINT,
						jasperPrint);
				ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
				exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
						txtReport);
				exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,
						new Integer(200));
				exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,
						new Integer(50));
				exporter.exportReport();

				byte[] bytes;
				bytes = txtReport.toByteArray();
				ServletOutputStream ouputStream = response.getOutputStream();
				ouputStream.write(bytes, 0, bytes.length);
				ouputStream.flush();
				ouputStream.close();

			}
		} catch (Exception ex) {
			String connectMsg = "Could not create the report "
					+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
			System.out.println(connectMsg);
		}
	}

}
