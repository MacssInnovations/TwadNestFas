package Servlets.FAS.FAS1.FundReceiptSystem.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class Fund_Transfer_Reconciliation_atOffice
 */
public class Fund_Transfer_Reconciliation_atOffice extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Fund_Transfer_Reconciliation_atOffice() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
                                                              IOException {
        // TODO Auto-generated method stub
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException,
                                                               IOException {
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


        /**
	       *  Variables Declaration
	       */

        String selstr = "",query_Str="";
        String selspestr = "";
        String sel = "";
        String opt = "",txtBankAccountNo="",sub_String="";
        response.setContentType(CONTENT_TYPE);
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs1 = null;
        /**
	         * Retrieving Configuration Values
	         */

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


        /**
	        * Report Calling
	        */

        JasperDesign jasperDesign = null;
        File reportFile = null;
        int cmbAcc_UnitCode = 0, cmbOffice_code = 0;
        try {
            System.out.println("calling servlet");



            /** Get Cash Book Month and Year */
            String txtCB_Year = request.getParameter("txtCB_Year");
            String txtCB_Month = request.getParameter("txtCB_Month");


            /** Find Whether report should be either html or text or pdf */
            String rtype = request.getParameter("txtoption");

       

       String command=request.getParameter("hid");
       



            /** CONVERT CASH BOOK MONTH AND YEAR FROM STRING TO INTEGER */
            int year = Integer.parseInt(txtCB_Year);
            int month = Integer.parseInt(txtCB_Month);

            /** Convert months in numbers to words for from cash book month */
            String monthInWords = "",officeName="";
            if (month == 1)
                monthInWords = "January";
            else if (month == 2)
                monthInWords = "February";
            else if (month == 3)
                monthInWords = "March";
            else if (month == 4)
                monthInWords = "April";
            else if (month == 5)
                monthInWords = "May";
            else if (month == 6)
                monthInWords = "June";
            else if (month == 7)
                monthInWords = "July";
            else if (month == 8)
                monthInWords = "August";
            else if (month == 9)
                monthInWords = "September";
            else if (month == 10)
                monthInWords = "October";
            else if (month == 11)
                monthInWords = "November";
            else if (month == 12)
                monthInWords = "December";

        
            String accounted ="",notAccounted="";
            String Specifictype = "";
            
if(!command.equalsIgnoreCase("New") && !command.equalsIgnoreCase("Unit")&& !command.equalsIgnoreCase("FTR_HO")&& !command.equalsIgnoreCase("FTR_Office"))
{

     Specifictype = request.getParameter("seletype");
    /** Get Accounting Unit ID */
	
    try {
        cmbAcc_UnitCode =
                Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
    } catch (NumberFormatException e) {
        System.out.println("exception" + e);
    }
    System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);


    /** Get Accounting for Office ID */
    try {
        cmbOffice_code =
                Integer.parseInt(request.getParameter("cmbOffice_code"));
    } catch (NumberFormatException e) {
        System.out.println("exception" + e);
    }
    System.out.println("cmbOffice_code " + cmbOffice_code);
    
    
    String sql =
        "select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=" +
        cmbOffice_code;
    ps = connection.prepareStatement(sql);
    rs1 = ps.executeQuery();
    rs1.next();
     officeName = rs1.getString("OFFICE_NAME");

             accounted =
                "select * from \n" + " (select  office.accounting_for_office_id,office.DATE_OF_TRANSFER,office.VOUCHER_NO,office.TOTAL_AMOUNT,office.CHEQUE_OR_DD,office.CHEQUE_DD_NO,office.REMITTANCE_TYPE,ho.RECEIPT_DATE, ho.HO_ACCOUNT_NO, \n" +
                " ho.HO_BANK_ID from \n" +
                "FAS_FUND_TRF_FROM_OFFICE office,FAS_FUND_RECEIPT_BY_HO ho where \n" +
                "office.accounting_for_office_id=ho.RECEIVED_FROM_OFFICE_ID \n" +
                "and office.cashbook_month=ho.cashbook_month and office.cashbook_year=ho.cashbook_year and office.TOTAL_AMOUNT=ho.TOTAL_AMOUNT \n" +
                "and trim(office.REMITTANCE_TYPE)=trim(ho.RECEIPT_TYPE) \n" +
                "and office.accounting_unit_id=" + cmbAcc_UnitCode + " \n" +
                "and office.accounting_for_office_id=" + cmbOffice_code +
                " and office.cashbook_month=" + txtCB_Month +
                " and office.cashbook_year=" + txtCB_Year +
                " and office.transfer_status='L' \n" +
                "and ho.receipt_status='L')a \n" + "left outer join  \n" +
                "(select OFFICE_ID,OFFICE_NAME from \n" +
                "COM_MST_OFFICES )c \n" +
                "on a.accounting_for_office_id=c.OFFICE_ID " +
                " left outer join \n" +
                " (select BANK_ID,BANK_SHORT_NAME from fas_mst_banks)d \n" +
                " on a.HO_BANK_ID=d.BANK_ID \n" + " order by a.accounting_unit_id, a.voucher_no";


             notAccounted =
                //and office.accounting_for_office_id not in (select accounting_for_office_id from FAS_FUND_RECEIPT_BY_HO where cashbook_month=10 and cashbook_year=2010 )
                "select * from (select office.accounting_for_office_id,office.DATE_OF_TRANSFER,office.VOUCHER_NO,office.TOTAL_AMOUNT,office.CHEQUE_OR_DD,office.CHEQUE_DD_NO,"
           //     + "office.REMITTANCE_TYPE as REMITTANCE_TYPE1, "
           + " case	 WHEN TRIM(OFFICE.REMITTANCE_TYPE) LIKE 'C'       THEN 'Collection' "
           + "       WHEN TRIM(OFFICE.REMITTANCE_TYPE) LIKE 'U'       THEN 'Unspent' "
            	 + "        WHEN TRIM(OFFICE.REMITTANCE_TYPE) LIKE 'NM'       THEN 'Main-Unspent' "
            	     + "     WHEN TRIM(OFFICE.REMITTANCE_TYPE) LIKE 'NS'       THEN 'Support-Unspent' "
            	         + "  WHEN TRIM(OFFICE.REMITTANCE_TYPE) LIKE 'NC'       THEN 'Main-Calamity' "
            	            + "WHEN TRIM(OFFICE.REMITTANCE_TYPE) LIKE 'UNM'       THEN 'Main-Interest' "
            	       + "      WHEN TRIM(OFFICE.REMITTANCE_TYPE) LIKE 'UNS'       THEN 'Support-Interest' "
            	           + "   WHEN TRIM(OFFICE.REMITTANCE_TYPE) LIKE 'UNC'       THEN 'Calamity Interest' "
              
                + " end as REMITTANCE_TYPE,"
                + "null as RECEIPT_DATE,null as HO_ACCOUNT_NO, null as BANK_SHORT_NAME   \n" +
                "  from \n" +
                " FAS_FUND_TRF_FROM_OFFICE office where   office.cashbook_month=" +
                txtCB_Month + " and office.cashbook_year=" + txtCB_Year +
                " and office.transfer_status='L' \n" +
                " and office.total_amount  not in (select total_amount from FAS_FUND_RECEIPT_BY_HO where cashbook_month=" +
                txtCB_Month + " and cashbook_year=" + txtCB_Year +
                " and receipt_status='L' and RECEIVED_FROM_OFFICE_ID=" +
                cmbOffice_code + ") \n" +
                " and office.accounting_for_office_id=" + cmbOffice_code +
                " and office.accounting_unit_id=" + cmbAcc_UnitCode +
                " )a \n" + "left outer join  \n" +
                "(select OFFICE_ID,OFFICE_NAME from \n" +
                "COM_MST_OFFICES )c \n" +
                "on a.accounting_for_office_id=c.OFFICE_ID order by a.accounting_unit_id,a.voucher_no";
}else if(command.equalsIgnoreCase("New")){
     Specifictype = request.getParameter("seletype");
	accounted =
        "select * from \n" + " (select  office.accounting_for_office_id,office.DATE_OF_TRANSFER,office.VOUCHER_NO,office.TOTAL_AMOUNT,office.CHEQUE_OR_DD,office.CHEQUE_DD_NO,office.REMITTANCE_TYPE,ho.RECEIPT_DATE, ho.HO_ACCOUNT_NO, \n" +
        " ho.HO_BANK_ID from \n" +
        "FAS_FUND_TRF_FROM_OFFICE office,FAS_FUND_RECEIPT_BY_HO ho where \n" +
        "office.accounting_for_office_id=ho.RECEIVED_FROM_OFFICE_ID \n" +
        "and office.cashbook_month=ho.cashbook_month and office.cashbook_year=ho.cashbook_year and office.TOTAL_AMOUNT=ho.TOTAL_AMOUNT \n" +
        "and trim(office.REMITTANCE_TYPE)=trim(ho.RECEIPT_TYPE) \n" +
       // "and office.accounting_unit_id=" + cmbAcc_UnitCode + " \n" +
       // "and office.accounting_for_office_id=" + cmbOffice_code +
        " and office.cashbook_month=" + txtCB_Month +
        " and office.cashbook_year=" + txtCB_Year +
        " and office.transfer_status='L' \n" +
        "and ho.receipt_status='L')a \n" + "left outer join  \n" +
        "(select OFFICE_ID,OFFICE_NAME from \n" +
        "COM_MST_OFFICES )c \n" +
        "on a.accounting_for_office_id=c.OFFICE_ID " +
        " left outer join \n" +
        " (select BANK_ID,BANK_SHORT_NAME from fas_mst_banks)d \n" +
        " on a.HO_BANK_ID=d.BANK_ID \n" + " order a.accounting_unit_id, by a.voucher_no";


     notAccounted =
        //and office.accounting_for_office_id not in (select accounting_for_office_id from FAS_FUND_RECEIPT_BY_HO where cashbook_month=10 and cashbook_year=2010 )
        "select * from (select office.accounting_for_office_id,office.DATE_OF_TRANSFER,office.VOUCHER_NO,office.TOTAL_AMOUNT,office.CHEQUE_OR_DD,office.CHEQUE_DD_NO,"
    //    + "office.REMITTANCE_TYPE as REMITTANCE_TYPE1,"
    + " case	 WHEN TRIM(OFFICE.REMITTANCE_TYPE) LIKE 'C'       THEN 'Collection' "
    + "       WHEN TRIM(OFFICE.REMITTANCE_TYPE) LIKE 'U'       THEN 'Unspent' "
     	 + "        WHEN TRIM(OFFICE.REMITTANCE_TYPE) LIKE 'NM'       THEN 'Main-Unspent' "
     	     + "     WHEN TRIM(OFFICE.REMITTANCE_TYPE) LIKE 'NS'       THEN 'Support-Unspent' "
     	         + "  WHEN TRIM(OFFICE.REMITTANCE_TYPE) LIKE 'NC'       THEN 'Main-Calamity' "
     	            + "WHEN TRIM(OFFICE.REMITTANCE_TYPE) LIKE 'UNM'       THEN 'Main-Interest' "
     	       + "      WHEN TRIM(OFFICE.REMITTANCE_TYPE) LIKE 'UNS'       THEN 'Support-Interest' "
     	           + "   WHEN TRIM(OFFICE.REMITTANCE_TYPE) LIKE 'UNC'       THEN 'Calamity Interest' "
       
         + " end as REMITTANCE_TYPE,"
        + "null as RECEIPT_DATE,null as HO_ACCOUNT_NO, null as BANK_SHORT_NAME   \n" +
        "  from \n" +
        " FAS_FUND_TRF_FROM_OFFICE office where   office.cashbook_month=" +
        txtCB_Month + " and office.cashbook_year=" + txtCB_Year +
        " and office.transfer_status='L' \n" +
        " and office.total_amount  not in (select total_amount from FAS_FUND_RECEIPT_BY_HO where cashbook_month=" +
        txtCB_Month + " and cashbook_year=" + txtCB_Year +
        " and receipt_status='L' and RECEIVED_FROM_OFFICE_ID=office.accounting_for_office_id ) \n" +
     //   " and office.accounting_for_office_id=" + cmbOffice_code +
      //  " and office.accounting_unit_id=" + cmbAcc_UnitCode +
        " )a \n" + "left outer join  \n" +
        "(select OFFICE_ID,OFFICE_NAME from \n" +
        "COM_MST_OFFICES )c \n" +
        "on a.accounting_for_office_id=c.OFFICE_ID order by a.accounting_unit_id, a.voucher_no";

}else if(command.equalsIgnoreCase("Unit"))
{
	 try {
	        cmbAcc_UnitCode =
	                Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
	    } catch (NumberFormatException e) {
	        System.out.println("exception" + e);
	    }
	    System.out.println("cmbAcc_UnitCode " + cmbAcc_UnitCode);


	    /** Get Accounting for Office ID */
	    try {
	        cmbOffice_code =
	                Integer.parseInt(request.getParameter("cmbOffice_code"));
	    } catch (NumberFormatException e) {
	        System.out.println("exception" + e);
	    }
	    System.out.println("cmbOffice_code " + cmbOffice_code);
	    
	    query_Str="SELECT * " +
	    "FROM " +
	    "  (SELECT 'Accounted' AS type, " +
	    "    office.accounting_unit_id, " +
	    "    office.accounting_for_office_id, " +
	    "    office.DATE_OF_TRANSFER, " +
	    "    office.VOUCHER_NO, " +
	    "    office.TOTAL_AMOUNT, " +
	    "    office.CHEQUE_OR_DD, " +
	    "    office.CHEQUE_DD_NO, " +
	    "    office.REMITTANCE_TYPE, " +
	    "    ho.RECEIPT_DATE, " +
	    "    ho.HO_ACCOUNT_NO, " +
	    "    ho.HO_BANK_ID, " +
	    "    office.AUTO_STATUS " +
	    "  FROM FAS_FUND_TRF_FROM_OFFICE office, " +
	    "    FAS_FUND_RECEIPT_BY_HO ho " +
	    "  WHERE office.accounting_for_office_id=ho.RECEIVED_FROM_OFFICE_ID " +
	    "  AND office.cashbook_month            =ho.cashbook_month " +
	    "  AND office.cashbook_year             =ho.cashbook_year " +
	    "  AND office.TOTAL_AMOUNT              =ho.TOTAL_AMOUNT " +
	    "  AND trim(office.REMITTANCE_TYPE)           =trim(ho.RECEIPT_TYPE) " +
	    "  AND office.accounting_unit_id= " +cmbAcc_UnitCode+
	    "  AND office.accounting_for_office_id=  " +cmbOffice_code+
	    "  AND office.cashbook_month =  " +txtCB_Month+
	    "  AND office.cashbook_year  = " +txtCB_Year+
	    "  AND office.transfer_status='L' " +
	    "  AND ho.receipt_status     ='L' " +
	    "  UNION ALL " +
	    "  SELECT 'Not Accounted' AS type, " +
	    "    office.accounting_unit_id, " +
	    "    office.accounting_for_office_id, " +
	    "    office.DATE_OF_TRANSFER, " +
	    "    office.VOUCHER_NO, " +
	    "    office.TOTAL_AMOUNT, " +
	    "    office.CHEQUE_OR_DD, " +
	    "    office.CHEQUE_DD_NO, " +
	    "    office.REMITTANCE_TYPE, " +
	    "    NULL AS RECEIPT_DATE, " +
	    "    NULL AS HO_ACCOUNT_NO, " +
	    "    NULL AS BANK_SHORT_NAME, " +
	    "    office.AUTO_STATUS " +
	    "  FROM FAS_FUND_TRF_FROM_OFFICE office " +
	    "  WHERE office.cashbook_month  = " +txtCB_Month+
	    "  AND office.cashbook_year     =  " +txtCB_Year+
	    "  AND office.transfer_status   ='L' " +
	    "  AND office.total_amount NOT IN " +
	    "    (SELECT total_amount " +
	    "    FROM FAS_FUND_RECEIPT_BY_HO " +
	    "    WHERE cashbook_month       =" +txtCB_Month+
	    "    AND cashbook_year          = " +txtCB_Year+
	    "    AND receipt_status         ='L' " +
	    "    AND RECEIVED_FROM_OFFICE_ID=office.accounting_for_office_id " +
	    "    )   AND office.accounting_for_office_id=" +cmbAcc_UnitCode+
        " AND office.accounting_unit_id="+cmbOffice_code+
	    "  ) a " +
	    "LEFT OUTER JOIN " +
	    "  (SELECT OFFICE_ID,OFFICE_NAME FROM COM_MST_OFFICES " +
	    "  )c " +
	    "ON a.accounting_for_office_id=c.OFFICE_ID " +
	    "   left outer join(select ACCOUNTING_UNIT_ID unit_id, ACCOUNTING_UNIT_NAME from fas_mst_acct_units )u"+
	    "  on a.accounting_unit_id=u.unit_id "+
	    "LEFT OUTER JOIN " +
	    "  (SELECT BANK_ID,BANK_SHORT_NAME FROM fas_mst_banks " +
	    "  )d " +
	    "ON a.HO_BANK_ID=d.BANK_ID " +
	    "ORDER BY a.accounting_unit_id,a.voucher_no";
	    

	    
	
	
	
}else if(command.equalsIgnoreCase("FTR_HO")){
	 Specifictype = request.getParameter("seletype");
	 txtBankAccountNo=request.getParameter("txtBankAccountNo");
	 if(txtBankAccountNo.equalsIgnoreCase("All")){
		 sub_String="";
	 }else{
		 sub_String=" and office.HO_ACCOUNT_NO="+txtBankAccountNo;
	 }
	 if (Specifictype.equalsIgnoreCase("accounted")) {

		 accounted="SELECT *" + 
		 		"FROM" + 
		 		"  (SELECT 'Accounted' AS type," + 
		 		"    office.accounting_unit_id," + 
		 		"    office.accounting_for_office_id," + 
		 		"    office.DATE_OF_TRANSFER," + 
		 		"    office.VOUCHER_NO," + 
		 		"    office.TOTAL_AMOUNT," + 
		 		"    office.CHEQUE_OR_DD," + 
		 		"    office.CHEQUE_DD_NO," + 
		 		"    office.REMITTANCE_TYPE," + 
		 		"    ho.RECEIPT_DATE," + 
		 		"    office.HO_ACCOUNT_NO," + 
		 		"    office.HO_BANK_ID," + 
		 		"    office.AUTO_STATUS" + 
		 		"  FROM FAS_FUND_TRF_FROM_OFFICE office ," + 
		 		"    FAS_FUND_RECEIPT_BY_HO HO" + 
		 		"  WHERE office.accounting_for_office_id=ho.RECEIVED_FROM_OFFICE_ID" + 
		 		"  AND OFFICE.VOUCHER_NO                =HO.TRF_VOUCHER_NO" + 
		 		"  AND OFFICE.DATE_OF_TRANSFER          =HO.TRF_VOUCHER_DATE" + 
		 		"  AND office.cashbook_year             = " +txtCB_Year+
		 		"  AND office.cashbook_month            = " +txtCB_Month+sub_String+
		 		"  AND office.transfer_status           ='L'" + 
		 		"  AND Ho.Receipt_Status                ='L'" + 
		 		"  ) a" + 
		 		" LEFT OUTER JOIN" + 
		 		"  (SELECT OFFICE_ID,OFFICE_NAME FROM COM_MST_OFFICES" + 
		 		"  )c" + 
		 		" ON a.accounting_for_office_id=c.OFFICE_ID" + 
		 		" LEFT OUTER JOIN" + 
		 		"  (SELECT Accounting_Unit_Id Unit_Id," + 
		 		"    Accounting_Unit_Name" + 
		 		"  FROM Fas_Mst_Acct_Units" + 
		 		"  )U" + 
		 		" ON A.Accounting_Unit_Id=u.Unit_Id" + 
		 		" LEFT OUTER JOIN" + 
		 		"  (SELECT BANK_ID,BANK_SHORT_NAME FROM fas_mst_banks" + 
		 		"  )d" + 
		 		" ON A.Ho_Bank_Id=D.Bank_Id" + 
		 		" ORDER BY A.ACCOUNTING_UNIT_ID," + 
		 		"  a.voucher_no";
		 
		 
		 
//		 accounted="SELECT * " +
//		 "FROM " +
//		 "  (SELECT 'Accounted' AS type, " +
//		 "    office.accounting_unit_id, " +
//		 "    office.accounting_for_office_id, " +
//		 "    office.DATE_OF_TRANSFER, " +
//		 "    office.VOUCHER_NO, " +
//		 "    office.TOTAL_AMOUNT, " +
//		 "    office.CHEQUE_OR_DD, " +
//		 "    office.CHEQUE_DD_NO, " +
//		 "    office.REMITTANCE_TYPE, " +
//		 "    ho.RECEIPT_DATE, " +
//		 "    office.HO_ACCOUNT_NO, " +
//		 "    office.HO_BANK_ID, " +
//		 "    office.AUTO_STATUS " +
//		 "  FROM FAS_FUND_TRF_FROM_OFFICE office , " +
//		 "    FAS_FUND_RECEIPT_BY_HO ho " +
//		 "  WHERE office.accounting_for_office_id=ho.RECEIVED_FROM_OFFICE_ID " +
//		 "  AND office.cashbook_month            =ho.trf_cb_month " +
//		 "  AND office.cashbook_year             =ho.trf_cb_year " +
//		 "  AND office.TOTAL_AMOUNT              =ho.TOTAL_AMOUNT " +
//		 "  AND office.VOUCHER_NO                =HO.TRF_VOUCHER_NO " +
//		 "  AND office.cashbook_year             =" +txtCB_Year+
//		 "  AND office.cashbook_month            =" +txtCB_Month+sub_String+
//		 "  AND office.transfer_status           ='L' " +
//		 "  AND Ho.Receipt_Status                ='L' " +
//		 "  ) a " +
//		 "LEFT OUTER JOIN " +
//		 "  (SELECT OFFICE_ID,OFFICE_NAME FROM COM_MST_OFFICES " +
//		 "  )c " +
//		 "ON a.accounting_for_office_id=c.OFFICE_ID " +
//		 "LEFT OUTER JOIN " +
//		 "  (SELECT Accounting_Unit_Id Unit_Id, " +
//		 "    Accounting_Unit_Name " +
//		 "  FROM Fas_Mst_Acct_Units " +
//		 "  )U " +
//		 "ON A.Accounting_Unit_Id=u.Unit_Id " +
//		 "LEFT OUTER JOIN " +
//		 "  (SELECT BANK_ID,BANK_SHORT_NAME FROM fas_mst_banks " +
//		 "  )d " +
//		 "ON A.Ho_Bank_Id=D.Bank_Id " +
//		 "ORDER BY a.accounting_unit_id,a.voucher_no";
	 } else {
		 notAccounted="SELECT * " +
		 "FROM " +
		 "  (SELECT 'Not Accounted' AS type, " +
		 "    office.accounting_unit_id, " +
		 "    office.accounting_for_office_id, " +
		 "    office.DATE_OF_TRANSFER, " +
		 "    office.VOUCHER_NO, " +
		 "    office.TOTAL_AMOUNT, " +
		 "    office.CHEQUE_OR_DD, " +
		 "    office.CHEQUE_DD_NO, " +
		 "    office.REMITTANCE_TYPE, " +
		 "    NULL AS RECEIPT_DATE, " +
		 "    office.HO_ACCOUNT_NO, " +
		 "    office.Ho_Bank_Id, " +
		 "    office.AUTO_STATUS " +
		 "  FROM FAS_FUND_TRF_FROM_OFFICE office " +
		 "  WHERE office.cashbook_month  =  " +txtCB_Month+
		 "  AND office.cashbook_year     =  " +txtCB_Year+sub_String+
		 "  AND office.transfer_status   ='L' " +
		 "  AND (office.auto_status            ='N' " +
		 " OR office.auto_status             IS NULL) " +
		 
//		 "  AND office.total_amount NOT IN " +
//		 "    (SELECT total_amount " +
//		 "    FROM FAS_FUND_RECEIPT_BY_HO " +
//		 "    WHERE TRF_CB_MONTH         =  " +txtCB_Month+
//		 "    AND TRF_CB_YEAR            =  " +txtCB_Year+
//		 "    AND receipt_status         ='L' " +
//		 "    AND RECEIVED_FROM_OFFICE_ID=office.accounting_for_office_id " +
//		 "    ) " +
		 "  ) a " +
		 "LEFT OUTER JOIN " +
		 "  (SELECT OFFICE_ID,OFFICE_NAME FROM COM_MST_OFFICES " +
		 "  )c " +
		 "ON a.accounting_for_office_id=c.OFFICE_ID " +
		 "LEFT OUTER JOIN " +
		 "  (SELECT Accounting_Unit_Id Unit_Id, " +
		 "    Accounting_Unit_Name " +
		 "  FROM Fas_Mst_Acct_Units " +
		 "  )U " +
		 "ON A.Accounting_Unit_Id=u.Unit_Id " +
		 "LEFT OUTER JOIN " +
		 "  (SELECT BANK_ID,BANK_SHORT_NAME FROM fas_mst_banks " +
		 "  )d " +
		 "ON A.Ho_Bank_Id=D.Bank_Id " +
		 "ORDER BY a.accounting_unit_id,a.voucher_no";
	 }
}else if(command.equalsIgnoreCase("FTR_Office")){
	 Specifictype = request.getParameter("seletype");
	 if(Specifictype.equalsIgnoreCase("accounted")){
		
			 accounted="SELECT Trn.Transfer_To_Office_Id, " +
//			 "  (SELECT b.accounting_unit_name " +
//			 "  FROM Fas_Mst_Acct_Units b " +
//			 "  WHERE Trn.Transfer_To_Office_Id=B.Accounting_Unit_Office_Id " +
//			 "  )unit_name, " +                                                   // commended on 6-3-19
			 "  (SELECT O.Office_Name " +
			 "  FROM Com_Mst_Offices O " +
			 "  WHERE O.Office_Id=Trn.Transfer_To_Office_Id " +
			 "  )AS Offname, " +
			 "  trn.Cashbook_Year, " +
			 "  trn.Cashbook_Month, " +
			 "  trn.Voucher_No, " +
			 "  mas.DATE_OF_TRANSFER, " +
			 "  trn.Cheque_Or_Dd, " +
			 "  trn.Cheque_Dd_No, " +
			 "  trn.Cheque_Dd_Date, " +
			 "  trn.Amount, " +
			 "  trn.Particulars, " +
			 "  trn.Auto_Status, " +
			 "  trn.Verify, " +
			 "  trn.OFFICE_ACCOUNT_NO " +
			 "FROM Fas_Fund_Trf_From_Ho_Master Mas " +
			 "INNER JOIN Fas_Fund_Trf_From_Ho_Trn Trn " +
			 "ON Mas.Accounting_Unit_Id       =Trn.Accounting_Unit_Id " +
			 "AND Mas.Accounting_For_Office_Id=Trn.Accounting_For_Office_Id " +
			 "AND Mas.Cashbook_Year           =Trn.Cashbook_Year " +
			 "AND Mas.Cashbook_Month          =Trn.Cashbook_Month " +
			 "AND Mas.Voucher_No              =Trn.Voucher_No " +
			 "AND Mas.Transfer_Status         ='L' " +
			 "AND trn.auto_status             ='Y' " +
			// "AND Trn.Transfer_To_Office_Id!  =5000 " +                     // commended on 6-3-19
			 "AND Mas.Cashbook_Year           = " +txtCB_Year+
			 "AND Mas.Cashbook_Month          = " +txtCB_Month + " order by TRN.TRANSFER_TO_OFFICE_ID " ;
//			 "AND Trn.Amount                 IN " +
//			 "  (SELECT Total_Amount " +
//			 "  FROM Fas_Fund_Receipt_By_Office Office " +
//			 "  WHERE Office.Accounting_For_Office_Id=Trn.Transfer_To_Office_Id " +             // commended on 6-3-19
//			 "  AND Office.Cashbook_Year             =Mas.Cashbook_Year " +
//			 "  AND Office.Cashbook_Month            =Mas.Cashbook_Month " +
//			 "  AND Office.Total_Amount              =trn.Amount " +
//			 "  )";
		 } else {
			 notAccounted="SELECT Trn.Transfer_To_Office_Id, " +
//			 "  (SELECT b.accounting_unit_name " +
//			 "  FROM Fas_Mst_Acct_Units b " +
//			 "  WHERE Trn.Transfer_To_Office_Id=B.Accounting_Unit_Office_Id " +
//			 "  )unit_name, " +                                                                    // commended on 6-3-19
			 "  (SELECT O.Office_Name " +
			 "  FROM Com_Mst_Offices O " +
			 "  WHERE O.Office_Id=Trn.Transfer_To_Office_Id " +
			 "  )AS Offname, " +
			 "  trn.Cashbook_Year, " +
			 "  trn.Cashbook_Month, " +
			 "  trn.Voucher_No, " +
			 "  mas.DATE_OF_TRANSFER, " +
			 "  trn.Cheque_Or_Dd, " +
			 "  trn.Cheque_Dd_No, " +
			 "  trn.Cheque_Dd_Date, " +
			 "  trn.Amount, " +
			 "  trn.Particulars, " +
			 "  trn.Auto_Status, " +
			 "  trn.Verify, " +
			 "  trn.OFFICE_ACCOUNT_NO " +
			 "FROM Fas_Fund_Trf_From_Ho_Master Mas " +
			 "INNER JOIN Fas_Fund_Trf_From_Ho_Trn Trn " +
			 "ON Mas.Accounting_Unit_Id       =Trn.Accounting_Unit_Id " +
			 "AND Mas.Accounting_For_Office_Id=Trn.Accounting_For_Office_Id " +
			 "AND Mas.Cashbook_Year           =Trn.Cashbook_Year " +
			 "AND Mas.Cashbook_Month          =Trn.Cashbook_Month " +
			 "AND Mas.Voucher_No              =Trn.Voucher_No " +
			 "AND Mas.Transfer_Status         ='L' " +
			 "AND (trn.auto_status            ='N' " +
			 "OR trn.auto_status             IS NULL) " +
//			 "AND Trn.Transfer_To_Office_Id!  =5000 " +                                       // commended on 6-3-19
			 "AND Mas.Cashbook_Year           = " +txtCB_Year+
			 "AND Mas.Cashbook_Month          = " +txtCB_Month + " order by TRN.TRANSFER_TO_OFFICE_ID " ;
//			 "AND Trn.Amount NOT             IN " +
//			 "  (SELECT Total_Amount " +
//			 "  FROM Fas_Fund_Receipt_By_Office Office " +
//			 "  WHERE Office.Accounting_For_Office_Id=Trn.Transfer_To_Office_Id " +
//			 "  AND Office.Cashbook_Year             =Mas.Cashbook_Year " +
//			 "  AND Office.Cashbook_Month            =Mas.Cashbook_Month " +
//			 "  AND Office.Total_Amount              =trn.Amount " +                            // commended on 6-3-19
//			 "  )";
	 }
}

            String queryString = "";
            String reportType = "";
            String head="";
            /**
	                      *  Single Month + All A/C Head
	                      */

            if (Specifictype.equalsIgnoreCase("accounted")) {


               /* reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/FundReceiptSystem/jasper/Fund_Transfer_Reconciliation_atOffice.jasper"));
               */ 
            	queryString = accounted;
                reportType = "Accounted";

            }
            /**
	                      * Single Month + Single A/C Head
	                      */
            else {

               /* reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/FundReceiptSystem/jasper/Fund_Transfer_Reconciliation_atOffice.jasper"));
               */ 
            	queryString = notAccounted;
                reportType = "Not Accounted";
            }
            if(!command.equalsIgnoreCase("New")&& !command.equalsIgnoreCase("Unit") && !command.equalsIgnoreCase("FTR_HO")&& !command.equalsIgnoreCase("FTR_Office")){
            	reportFile =
                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/FundReceiptSystem/jasper/Fund_Transfer_Reconciliation_atOffice.jasper"));
            
            }else if(command.equalsIgnoreCase("New")){
            	reportFile =
                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/FundReceiptSystem/jasper/NewFund_Transfer_Reconciliation_atOffice.jasper"));
            }else if(command.equalsIgnoreCase("Unit")){
            	queryString=query_Str;
            	reportFile =
                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/FundReceiptSystem/jasper/NewFund_Transfer_Reconciliation_atUnit.jasper"));
            	reportType="";
            }else if(command.equalsIgnoreCase("FTR_HO")){
            	//queryString=query_Str;
            	head=" Fund Transfer Reconciliation at HO ";
            	reportFile =
                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/FundReceiptSystem/jasper/Fund_Trf_ReconciHO_Report.jasper"));
            }else if(command.equalsIgnoreCase("FTR_Office")){
            
            	head=" Fund Transfer Reconciliation at Office ";
            	reportFile =
                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/FundReceiptSystem/jasper/Fund_Trf_Reconciliation_Office.jasper"));
            }


System.out.println("reportfile"+reportFile);
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            
            Map map = new HashMap();
            map.put("sqlquery", queryString);
            map.put("cashmonth", month);
            map.put("cashyear", year);
            map.put("monthinwords", monthInWords);
            map.put("reporttype", reportType);
            map.put("officeName", officeName);
            map.put("heading", head);
            
            System.out.println("opt::" + map);
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);


            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"Reconciliation-Office.html\"");
                PrintWriter out = response.getWriter();
                JRHtmlExporter exporter = new JRHtmlExporter();
                // File f=new File(getServletContext().getRealPath("/WEB-INF/Report/"));
                //  exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,true);
                //  exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,getServletContext().getRealPath("/WEB-INF/Report/"));
                //  exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR,f);
                exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
                                      false);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                      jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
                exporter.exportReport();
                out.flush();
                out.close();
            } else if (rtype.equalsIgnoreCase("PDF")) {
                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                //response.setContentType("application/force-download");

                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"Reconciliation-Office.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"Reconciliation-Office.xls\"");
                JRXlsExporter exporterXLS = new JRXlsExporter();
                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                                         jasperPrint);

                ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
                                         xlsReport);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
                                         Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,
                                         Boolean.TRUE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
                                         Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
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
                                   "attachment;filename=\"Reconciliation-Office.txt\"");

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
            String connectMsg =
                "Could not create the report " + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }


    }

}
