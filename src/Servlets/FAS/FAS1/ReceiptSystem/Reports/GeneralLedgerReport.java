/**
 *                       Single Month + ( All A/C Head  or Single A/C Head )
 *                       single office or more than one office for a accounting unit
 */


package Servlets.FAS.FAS1.ReceiptSystem.Reports;

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

import javax.servlet.*;
import javax.servlet.http.*;

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


public class GeneralLedgerReport extends HttpServlet {
    private static final String CONTENT_TYPE ="text/html; charset=windows-1252";
    Connection connection = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException 
    {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
      /**
       *  Session Checking 
       */
       System.out.println("**********GeneralLedgerReport**********");
    	
    	String sql = "";
        try
        {
            HttpSession session=request.getSession(false);
            if(session==null)
            {
                System.out.println(request.getContextPath()+"/index.jsp");
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            
            }
            System.out.println(session);
                
        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        }
        
        
      /**
       *  Variables Declaration 
       */
        
        String selstr="";
        String selspestr="";
        String sel="";
        String opt="";
        response.setContentType(CONTENT_TYPE);
      
        
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
        File reportFile=null;
        PreparedStatement ps1= null,ps2=null;
        ResultSet rs11=null,rs22=null;
        String alloffice="";
        
        try {
                System.out.println("calling servlet*******testing***26Mar2012");
                
                /** Get Cash Book Month and Year */ 
                String txtCB_Year=request.getParameter("txtCB_Year");
                String txtCB_Month=request.getParameter("txtCB_Month");
                                          
                /** Find whether to display all or specific account heads */
                String Specifictype=request.getParameter("SpecificAHC");
                
                /** Find Whether report should be either html or text or pdf */
                String rtype= request.getParameter("txtoption");
                
                /** Get Accounting Unit Id */
                String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
                
                /** Get Accounting Office id */
                String cmbOffice_code=request.getParameter("cmbOffice_code");
                
                /** Getting the all office option */
               try
              {
                  alloffice=request.getParameter("all_office"); 
                 System.out.println("alloffice********"+alloffice);
               }
               catch(Exception e)
              {
            	   System.out.println("error while getting the all office option"+e);
              }
                /** Get Account Head */ 
                String cmbAccHeadCode=request.getParameter("txtAcc_HeadCode");
                
                
                System.out.println("cmbAccHeadCode is:"+cmbAccHeadCode);
                System.out.println("accounting unit id is:"+cmbAcc_UnitCode);
                System.out.println("office code is:"+cmbOffice_code);
                System.out.println("Specific Type Value is:"+Specifictype);
                System.out.println("Cashbook Year:"+txtCB_Year);
                System.out.println("Cashbook Month:"+txtCB_Month);
               System.out.println("alloffice:"+alloffice); 
               
               int ret_code=0;int accountingoffice1=0;
                String command="";
                String formation_date="",date_effective="";String acc_ofice_code="";
                String xml="";
                try
                {
                	command=request.getParameter("command");
                	System.out.println("command passed&&&&&&&&&&&&&"+command);
                }
                catch(Exception e)
                {
                	System.out.println("Exception in getting command:::"+command);
                	 e.printStackTrace();
                }
                if(command.equalsIgnoreCase("getofficedetails"))
                {
        			response.setContentType("text/xml");
        			PrintWriter pw = response.getWriter();
        			response.setHeader("Cache-Control", "no-cache");
                	
                	System.out.println("inside the getofficedetails"); 
                	acc_ofice_code=request.getParameter("office_id");
                	 accountingoffice1=Integer.parseInt(acc_ofice_code);
                	
                	xml=xml+"<response><command>getofficedetails</command>";
                	try
                	{
                	String sql1="SELECT TO_CHAR(date_of_formation,'dd-mm-yyyy') as date_of_formation,  " +
                			" CASE    " +
                			" WHEN status_effective_from IS NULL    " +
                			" THEN 'dd/mm/yyyy'    " +
                			" ELSE TO_CHAR(status_effective_from,'dd-mm-yyyy')  " +
                			" END AS status_effective_from" +
                			" from com_mst_offices WHERE OFFICE_ID="+accountingoffice1;
                	System.out.println("query for getting the office details"+sql1);
                	ps1=connection.prepareStatement(sql1);
                	rs11=ps1.executeQuery();
                	while(rs11.next())
                	{
                		formation_date=rs11.getString("date_of_formation");
                		date_effective=rs11.getString("status_effective_from");
                	}
                	xml = xml + "<flag>success</flag>"; 
                	xml = xml + "<formationdate>"+formation_date+"</formationdate>";
                	xml = xml + "<effectivefrom>"+date_effective+"</effectivefrom>";
                	}
                	catch (SQLException e) 
                	{
                        ret_code = e.getErrorCode();
                        System.err.println("ret_code ****" + e.getMessage());
                        xml = xml + "<flag>failure</flag>";
                    }
                	
                	xml = xml + "</response>";
                	 System.out.println("xml is : " + xml);
                     pw.write(xml);
                     pw.flush();
                     pw.close();
                }
                else if(command.equalsIgnoreCase("rep"))  
                {
		              System.out.println("coming here************************");
		                /** Convert Acciunting Unid Id and Office Id from String to Integer */ 
		             int accountingunit=Integer.parseInt(cmbAcc_UnitCode);
		             int accountingoffice=Integer.parseInt(cmbOffice_code);
		             int accountheadcode=0;
		   /** CONVERT CASH BOOK MONTH AND YEAR FROM STRING TO INTEGER */ 
             int year=Integer.parseInt(txtCB_Year);
             int month=Integer.parseInt(txtCB_Month);   
             
            /** Convert months in numbers to words for from cash book month */
            String monthInWords="";
            if(month==1)
            monthInWords="January";
            else if(month==2)
            monthInWords="February";
            else if(month==3)
            monthInWords="March";
            else if(month==4)
            monthInWords="April";
            else if(month==5)
            monthInWords="May";
            else if(month==6)
            monthInWords="June";
            else if(month==7)
            monthInWords="July";
            else if(month==8)
            monthInWords="August";
            else if(month==9)
            monthInWords="September";
            else if(month==10)
            monthInWords="October";
            else if(month==11)
            monthInWords="November";
            else if(month==12)
            monthInWords="December"; 
       
                
                    System.out.println("inside main");
                    if(cmbAccHeadCode!=null)
                    {
                        try 
                        { 
                           accountheadcode=Integer.parseInt(cmbAccHeadCode);
                          }
                        catch(Exception e)
                         {
                            accountheadcode=0;
                          }
                     }
                    System.out.println("sathya **** GeneralLedgerReport");
                    
                     /**
                      *  Single Month + All A/C Head 
                      */
                    Map map=new HashMap();
                     if(Specifictype.equalsIgnoreCase("All"))         
                     {
                            System.out.println("Report for all account Heads 4 single month");
                            if(accountingunit==5)  // For Head Office 
                            {
                                reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GeneralLedger_UnitWiseModule/GeneralLedgerReportAll_banking_main.jasper"));
                                System.out.println("reportFile==>"+reportFile);
                                sql = "SELECT cramt, " +
                                "  dramt, " +
                                "  ind, " +
                                "  recpno , " +
                                "  amt, " +
                                "  doctype, " +
                                "  type1, " +
                                "  date1, " +
                                "  particul, " +
                                "  acchead, " +
                                "  acc.ACCOUNT_HEAD_DESC, " +
                                "  TO_CHAR(glt.month_opening_balance) AS monthopen, " +
                                "  glt.MONTH_OPENING_BAL_DR_CR_IND    AS monthind , " +
                                "  TO_CHAR(glt.month_closing_balance) AS monthclose, " +
                                "  glt.MONTH_CLOSING_BAL_DR_CR_IND    AS monthind_close , " +
                                "  accUint.ACCOUNTING_UNIT_NAME       AS acc_unit_name, " +
                                "  Office.OFFICE_NAME                 AS off_name, " +
                                "  sl_type_code , " +
                                "  (SELECT sub_ledger_type_desc " +
                                "  FROM com_mst_sl_types " +
                                "  WHERE sub_ledger_type_code = sl_type_code " +
                                "  ) AS sl_type_code_desc, " +
                                "  sl_code, " +
                                "  -- Supplier 1 " +
                                "  CASE " +
                                "    WHEN sl_type_code = 1 " +
                                "    THEN " +
                                "      (SELECT SUPPLIER_NAME " +
                                "      FROM COM_SUPPLIER_SL_MST " +
                                "      WHERE ACCOUNTING_UNIT_ID    ='"+accountingunit+"' " +
                                "      AND ACCOUNTING_FOR_OFFICE_ID='"+accountingoffice+"' " +
                                "      AND supplier_id             = sl_code " +
                                "      ) " +
                                "    ELSE ( --- Firms 2 " +
                                "      CASE " +
                                "        WHEN sl_type_code=2 " +
                                "        THEN " +
                                "          (SELECT FIRMS_NAME " +
                                "          FROM COM_FIRMS_SL_MST " +
                                "          WHERE ACCOUNTING_UNIT_ID    ='"+accountingunit+"' " +
                                "          AND ACCOUNTING_FOR_OFFICE_ID='"+accountingoffice+"' " +
                                "          AND firms_id                =sl_code " +
                                "          ) " +
                                "        ELSE ( --- Assests 3 " +
                                "          CASE " +
                                "            WHEN sl_type_code='"+accountingunit+"' " +
                                "            THEN " +
                                "              (SELECT ASSET_DESCRIPTION " +
                                "              FROM COM_MST_ASSETS_SL " +
                                "              WHERE ACCOUNTING_UNIT_ID    ='"+accountingunit+"' " +
                                "              AND ACCOUNTING_FOR_OFFICE_ID='"+accountingoffice+"' " +
                                "              AND asset_code              = sl_code " +
                                "              ) " +
                                "            ELSE ( --- Offices 5 " +
                                "              CASE " +
                                "                WHEN sl_type_code = 5 " +
                                "                THEN " +
                                "                  (SELECT OFFICE_NAME " +
                                "                  FROM COM_MST_OFFICES " +
                                "                  WHERE OFFICE_ID=sl_code " +
                                "                  ) " +
                                "                ELSE ( -- Bank 6 " +
                                "                  CASE " +
                                "                    WHEN sl_type_code = 6 " +
                                "                    THEN " +
                                "                      (SELECT TO_CHAR(bank_ac_no) " +
                                "                      FROM fas_mst_bank_balance " +
                                "                      WHERE ACCOUNTING_UNIT_ID  ='"+accountingunit+"'" +
                                "                      AND BANK_AC_NO_ALIAS_CODE = sl_code " +
                                "                      ) " +
                                "                    ELSE ( -- Employees 7 " +
                                "                      CASE " +
                                "                        WHEN sl_type_code= 7 " +
                                "                        THEN " +
                                "                          (SELECT e.EMPLOYEE_NAME " +
                                "                            ||'.' " +
                                "                            ||e.EMPLOYEE_INITIAL " +
                                "                            ||'-' " +
                                "                            || d.DESIGNATION AS ENAME " +
                                "                          FROM HRM_MST_EMPLOYEES e, " +
                                "                            HRM_EMP_CURRENT_POSTING c, " +
                                "                            HRM_MST_DESIGNATIONS d " +
                                "                          WHERE c.DESIGNATION_ID=d.DESIGNATION_ID " +
                                "                          AND c.EMPLOYEE_ID     =e.EMPLOYEE_ID " +
                                "                          AND c.OFFICE_ID       ='"+accountingoffice+"' " +
                                "                          AND e.EMPLOYEE_ID     =sl_code " +
                                "                          ) " +
                                "                        ELSE ( " +
                                "                          -- OTHER DEPARTMENTS " +
                                "                          CASE " +
                                "                            WHEN sl_type_code= 9 " +
                                "                            THEN " +
                                "                              (SELECT dep.OTHER_DEPT_NAME " +
                                "                                || '-' " +
                                "                                || off.OTHER_DEPT_OFFICE_NAME AS OTHER_DEPT_OFF_NAME " +
                                "                              FROM HRM_MST_OTHER_DEPTS dep, " +
                                "                                HRM_MST_OTHER_DEPT_OFFICES OFF " +
                                "                              WHERE dep.OTHER_DEPT_ID           =off.OTHER_DEPT_ID " +
                                "                              AND off.other_dept_office_alias_id= sl_code " +
                                "                              ) " +
                                "                            ELSE ( " +
                                "                              -- PROJECT " +
                                "                              CASE " +
                                "                                WHEN sl_type_code=10 " +
                                "                                THEN " +
                                "                                  (SELECT PROJECT_NAME " +
                                "                                  FROM PMS_MST_PROJECTS_VIEW " +
                                "                                  WHERE OFFICE_ID='"+accountingoffice+"' " +
                                "                                  AND PROJECT_ID = sl_code " +
                                "                                  ) " +
                                "                                ELSE ( " +
                                "                                  -- CONTRACTORS " +
                                "                                  CASE " +
                                "                                    WHEN sl_type_code=11 " +
                                "                                    THEN " +
                                "                                      (SELECT CONTRACTOR_NAME " +
                                "                                      FROM PMS_MST_CONTRACTORS_VIEW " +
                                "                                      WHERE (OFFICE_ID='"+accountingoffice+"' " +
                                "                                      OR OFFICE_ID   IN " +
                                "                                        (SELECT REGION_OFFICE_ID FROM COM_MST_ALL_OFFICES_VIEW WHERE office_id='"+accountingoffice+"' " +
                                "                                        UNION ALL " +
                                "                                        SELECT CIRCLE_OFFICE_ID FROM COM_MST_ALL_OFFICES_VIEW WHERE office_id='"+accountingoffice+"' " +
                                "                                        )) " +
                                "                                      AND CONTRACTOR_ID= sl_code " +
                                "                                      ) " +
                                "                                  END ) " +
                                "                              END ) " +
                                "                          END ) " +
                                "                      END ) " +
                                "                  END ) " +
                                "              END ) " +
                                "          END ) " +
                                "      END ) " +
                                "  END AS sl_code_name " +
                                "FROM " +
                                "  ( " +
                                "  -- 1.  Receipt master and transaction CR Heads " +
                                "  SELECT a.account_head_code AS acchead, " +
                                "    a.cr_dr_indicator        AS ind, " +
                                "    a.receipt_no             AS recpno, " +
                                "    TO_CHAR(a.amount)        AS amt, " +
                                "    a.amount                 AS cramt, " +
                                "    0                        AS dramt, " +
                                "    'R'                      AS doctype, " +
                                "    NULL                     AS type1, " +
                                "    b.receipt_date           AS date1, " +
                                "    a.particulars            AS particul, " +
                                "    a.SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                                "    a.SUB_LEDGER_CODE        AS sl_code " +
                                "  FROM fas_receipt_transaction a, " +
                                "    fas_receipt_master b " +
                                "  WHERE a.accounting_unit_id    =b.accounting_unit_id " +
                                "  AND a.cashbook_year           =b.cashbook_year " +
                                "  AND a.cashbook_month          =b.cashbook_month " +
                                "  AND a.receipt_no              =b.receipt_no " +
                                "  AND a.accounting_for_office_id=b.ACCOUNTING_FOR_OFFICE_ID " +
                                "  AND b.receipt_status         <>'C' " +
                                "  AND a.accounting_unit_id      ='"+accountingunit+"' " +
                                "  AND a.accounting_for_office_id='"+accountingoffice+"' " +
                                "  AND a.cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND a.cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND a.cr_dr_indicator='CR' " +
                                "  UNION ALL " +
                                "  -- 2. Journal Master and Transaction  CR Heads " +
                                "  SELECT a.account_head_code AS acchead, " +
                                "    a.cr_dr_indicator        AS ind, " +
                                "    a.voucher_no             AS recpno, " +
                                "    TO_CHAR(a.amount)        AS amt, " +
                                "    a.amount                 AS cramt, " +
                                "    0                        AS dramt, " +
                                "    'J'                      AS doctype, " +
                                "    b.journal_type_code      AS type1, " +
                                "    b.voucher_date           AS date1, " +
                                "    a.particulars            AS particul, " +
                                "    a.SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                                "    a.SUB_LEDGER_CODE        AS sl_code " +
                                "  FROM fas_journal_transaction a, " +
                                "    fas_journal_master b " +
                                "  WHERE a.accounting_unit_id    =b.accounting_unit_id " +
                                "  AND a.cashbook_year           =b.cashbook_year " +
                                "  AND a.cashbook_month          =b.cashbook_month " +
                                "  AND a.voucher_no              =b.voucher_no " +
                                "  AND a.accounting_for_office_id=b.ACCOUNTING_FOR_OFFICE_ID " +
                                "  AND b.journal_status         <>'C' " +
                                "  AND a.accounting_unit_id      = '"+accountingunit+"' " +
                                "  AND a.accounting_for_office_id='"+accountingoffice+"' " +
                                "  AND a.cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND a.cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND a.cr_dr_indicator    ='CR' " +
                                "  AND b.created_by_module IN ('GJV','LJV') " +
                                "  UNION ALL " +
                                "  -- 3. Journal Master and Transaction  DR Heads " +
                                "  SELECT a.account_head_code AS acchead, " +
                                "    a.cr_dr_indicator        AS ind, " +
                                "    a.voucher_no             AS recpno, " +
                                "    TO_CHAR(a.amount)        AS amt, " +
                                "    0                        AS cramt, " +
                                "    a.amount                 AS dramt, " +
                                "    'J'                      AS doctype, " +
                                "    b.journal_type_code      AS type1, " +
                                "    b.voucher_date           AS date1, " +
                                "    a.particulars            AS particul, " +
                                "    a.SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                                "    a.SUB_LEDGER_CODE        AS sl_code " +
                                "  FROM fas_journal_transaction a, " +
                                "    fas_journal_master b " +
                                "  WHERE a.accounting_unit_id    =b.accounting_unit_id " +
                                "  AND a.cashbook_year           =b.cashbook_year " +
                                "  AND a.cashbook_month          =b.cashbook_month " +
                                "  AND a.voucher_no              =b.voucher_no " +
                                "  AND a.accounting_for_office_id=b.ACCOUNTING_FOR_OFFICE_ID " +
                                "  AND b.journal_status         <>'C' " +
                                "  AND a.accounting_unit_id      = '"+accountingunit+"' " +
                                "  AND a.accounting_for_office_id='"+accountingoffice+"' " +
                                "  AND a.cashbook_year BETWEEN ('"+year+"')AND ('"+year+"') " +
                                "  AND a.cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND a.cr_dr_indicator    ='DR' " +
                                "  AND b.created_by_module IN ('GJV','LJV') " +
                                "  UNION ALL " +
                                "  -- 4. Payment Master and Transaction -- DR Heads " +
                                "  SELECT a.account_head_code AS acchead, " +
                                "    a.cr_dr_indicator        AS ind, " +
                                "    a.voucher_no             AS recpno, " +
                                "    TO_CHAR(a.amount)        AS amt, " +
                                "    0                        AS cramt, " +
                                "    a.amount                 AS dramt, " +
                                "    'P'                      AS doctype, " +
                                "    NULL                     AS type1, " +
                                "    b.payment_date           AS date1, " +
                                "    a.particulars            AS particul, " +
                                "    a.SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                                "    a.SUB_LEDGER_CODE        AS sl_code " +
                                "  FROM fas_payment_transaction a, " +
                                "    fas_payment_master b " +
                                "  WHERE a.accounting_unit_id    =b.accounting_unit_id " +
                                "  AND a.cashbook_year           =b.cashbook_year " +
                                "  AND a.cashbook_month          =b.cashbook_month " +
                                "  AND a.voucher_no              =b.voucher_no " +
                                "  AND a.accounting_for_office_id=b.ACCOUNTING_FOR_OFFICE_ID " +
                                "  AND b.payment_status         <>'C' " +
                                "  AND a.accounting_unit_id      = '"+accountingunit+"' " +
                                "  AND a.accounting_for_office_id='"+accountingoffice+"' " +
                                "  AND a.cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND a.cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND a.cr_dr_indicator='DR' " +
                                "  UNION ALL " +
                                "  -- 5. Receipt Master -- DR Heads " +
                                "  SELECT account_head_code AS acchead, " +
                                "    cr_dr_indicator        AS ind, " +
                                "    receipt_no             AS recpno, " +
                                "    TO_CHAR(TOTAL_AMOUNT)  AS amt, " +
                                "    0                      AS cramt, " +
                                "    TOTAL_AMOUNT           AS dramt, " +
                                "    'R'                    AS doctype, " +
                                "    NULL                   AS type1, " +
                                "    receipt_date           AS date1, " +
                                "    remarks                AS particul, " +
                                "    SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                                "    SUB_LEDGER_CODE        AS sl_code " +
                                "  FROM fas_receipt_master " +
                                "  WHERE receipt_status       <>'C' " +
                                "  AND accounting_unit_id      ='"+accountingunit+"' " +
                                "  AND accounting_for_office_id='"+accountingoffice+"' " +
                                "  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND cr_dr_indicator='DR' " +
                                "  UNION ALL " +
                                "  -- 6. Payment Master -- CR Heads " +
                                "  SELECT account_head_code AS acchead, " +
                                "    cr_dr_indicator        AS ind, " +
                                "    voucher_no             AS recpno, " +
                                "    TO_CHAR(TOTAL_AMOUNT)  AS amt, " +
                                "    TOTAL_AMOUNT           AS cramt, " +
                                "    0                      AS dramt, " +
                                "    'P'                    AS doctype, " +
                                "    NULL                   AS type1, " +
                                "    PAYMENT_DATE           AS date1, " +
                                "    remarks                AS particul , " +
                                "    SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                                "    SUB_LEDGER_CODE        AS sl_code " +
                                "  FROM fas_payment_master " +
                                "  WHERE payment_status       <>'C' " +
                                "  AND accounting_unit_id      ='"+accountingunit+"' " +
                                "  AND accounting_for_office_id='"+accountingoffice+"' " +
                                "  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND cr_dr_indicator='CR' " +
                                "  UNION ALL " +
                                "  -- 7. Fund Receipt By HO -- CR Heads " +
                                "  SELECT a.cr_account_head_code AS acchead, " +
                                "    'CR'                        AS ind, " +
                                "    a.receipt_no                AS recpno, " +
                                "    TO_CHAR(a.TOTAL_AMOUNT)     AS amt, " +
                                "    a.TOTAL_AMOUNT              AS cramt, " +
                                "    0                           AS dramt, " +
                                "    'FR'                        AS doctype, " +
                                "    NULL                        AS type1, " +
                                "    a.receipt_date              AS date1, " +
                                "    a.PARTICULARS               AS particul, " +
                                "    0                           AS sl_type_code, " +
                                "    0                           AS sl_code " +
                                "  FROM fas_fund_receipt_by_ho a " +
                                "  WHERE a.receipt_status       <>'C' " +
                                "  AND a.accounting_unit_id      = '"+accountingunit+"' " +
                                "  AND a.accounting_for_office_id='"+accountingoffice+"' " +
                                "  AND a.cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND a.cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  UNION ALL " +
                                "  --- 8. Fund Receipt By HO -- DR Heads " +
                                "  SELECT dr_account_head_code AS acchead, " +
                                "    'DR'                      AS ind, " +
                                "    receipt_no                AS recpno, " +
                                "    TO_CHAR(TOTAL_AMOUNT)     AS amt, " +
                                "    0                         AS cramt, " +
                                "    TOTAL_AMOUNT              AS dramt, " +
                                "    'FR'                      AS doctype, " +
                                "    NULL                      AS type1, " +
                                "    receipt_date              AS date1, " +
                                "    PARTICULARS               AS particul, " +
                                "    0                         AS sl_type_code , " +
                                "    0                         AS sl_code " +
                                "  FROM fas_fund_receipt_by_ho " +
                                "  WHERE receipt_status       <>'C' " +
                                "  AND accounting_unit_id      ='"+accountingunit+"' " +
                                "  AND accounting_for_office_id='"+accountingoffice+"' " +
                                "  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  UNION ALL " +
                                "  -- 9. Fund Transfer from HO Master and Transaction -- DR Heads " +
                                "  SELECT a.account_head_code AS acchead, " +
                                "    a.cr_dr_indicator        AS ind, " +
                                "    a.voucher_no             AS recpno, " +
                                "    TO_CHAR(a.amount)        AS amt, " +
                                "    0                        AS cramt, " +
                                "    a.amount                 AS dramt, " +
                                "    'FT'                     AS doctype, " +
                                "    NULL                     AS type1, " +
                                "    b.DATE_OF_TRANSFER       AS date1, " +
                                "    a.particulars            AS particul, " +
                                "    0                        AS sl_type_code, " +
                                "    0                        AS sl_code " +
                                "  FROM fas_fund_trf_from_ho_trn a, " +
                                "    fas_fund_trf_from_ho_master b " +
                                "  WHERE a.accounting_unit_id    =b.accounting_unit_id " +
                                "  AND a.cashbook_year           =b.cashbook_year " +
                                "  AND a.cashbook_month          =b.cashbook_month " +
                                "  AND a.voucher_no              =b.voucher_no " +
                                "  AND a.accounting_for_office_id=b.ACCOUNTING_FOR_OFFICE_ID " +
                                "  AND b.TRANSFER_STATUS        <>'C' " +
                                "  AND a.accounting_unit_id      ='"+accountingunit+"' " +
                                "  AND a.accounting_for_office_id='"+accountingoffice+"' " +
                                "  AND a.cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND a.cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND a.cr_dr_indicator='DR' " +
                                "  UNION ALL " +
                                "  --- 10. Fund Transfer from HO Master -- CR Heads " +
                                "  SELECT account_head_code AS acchead, " +
                                "    cr_dr_indicator        AS ind, " +
                                "    voucher_no             AS recpno, " +
                                "    TO_CHAR(TOTAL_AMOUNT)  AS amt, " +
                                "    TOTAL_AMOUNT           AS cramt, " +
                                "    0                      AS dramt, " +
                                "    'FT'                   AS doctype, " +
                                "    NULL                   AS type1, " +
                                "    DATE_OF_TRANSFER       AS date1, " +
                                "    remarks                AS particul, " +
                                "    0                      AS sl_type_code, " +
                                "    0                      AS sl_code " +
                                "  FROM fas_fund_trf_from_ho_master " +
                                "  WHERE transfer_status      <>'C' " +
                                "  AND accounting_unit_id      ='"+accountingunit+"' " +
                                "  AND accounting_for_office_id='"+accountingoffice+"' " +
                                "  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND cr_dr_indicator='CR' " +
                                "  UNION ALL " +
                                "  --- 11. IBT at HO -- CR Heads " +
                                "  SELECT cr_account_head_code AS acchead, " +
                                "    'CR' ind, " +
                                "    voucher_no            AS recpno, " +
                                "    TO_CHAR(total_amount) AS amt, " +
                                "    total_amount          AS cramt, " +
                                "    0                     AS dramt, " +
                                "    'IBT'                 AS doctype, " +
                                "    NULL                  AS type1, " +
                                "    date_of_transfer      AS date1, " +
                                "    particulars           AS particul, " +
                                "    0                     AS sl_type_code, " +
                                "    0                     AS sl_code " +
                                "  FROM fas_inter_bank_trf_at_ho " +
                                "  WHERE transfer_status      <>'C' " +
                                "  AND accounting_unit_id      ='"+accountingunit+"' " +
                                "  AND accounting_for_office_id= '"+accountingoffice+"' " +
                                "  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND cr_account_head_code=820202 " +
                                "  UNION ALL " +
                                "  -- 12. IBT at HO --- DR Heads " +
                                "  SELECT dr_account_head_code AS acchead, " +
                                "    'DR' ind, " +
                                "    voucher_no            AS recpno, " +
                                "    TO_CHAR(total_amount) AS amt, " +
                                "    0                     AS cramt, " +
                                "    total_amount          AS dramt, " +
                                "    'IBT'                 AS doctype, " +
                                "    NULL                  AS type1, " +
                                "    date_of_transfer      AS date1, " +
                                "    particulars           AS particul, " +
                                "    0                     AS sl_type_code, " +
                                "    0                     AS sl_code " +
                                "  FROM fas_inter_bank_trf_at_ho " +
                                "  WHERE transfer_status      <>'C' " +
                                "  AND accounting_unit_id      ='"+accountingunit+"' " +
                                "  AND accounting_for_office_id= '"+accountingoffice+"' " +
                                "  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  ), " +
                                "  com_mst_account_heads acc, " +
                                "  fas_general_ledger glt, " +
                                "  COM_MST_OFFICES Office, " +
                                "  FAS_MST_ACCT_UNITS accUint " +
                                "WHERE acc.account_head_code     =acchead " +
                                "AND acchead                     =glt.account_head_code " +
                                "AND glt.accounting_unit_id      ='"+accountingunit+"' " +
                                "AND glt.accounting_for_office_id='"+accountingoffice+"' " +
                                "AND glt.year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "AND glt.month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "AND accUint.ACCOUNTING_UNIT_ID='"+accountingunit+"' " +
                                "AND Office.OFFICE_ID          ='"+accountingoffice+"' " +
                                "ORDER BY acchead, " +
                                "  date1, " +
                                "  doctype, " +
                                "  recpno";
                                System.out.println("testing&&&&&&&&&&&"+sql);
                            }                     
                            else  // For Other Offices
                            {
                            	//System.out.println("for other than acc units=5"+alloffice); 
                            	//try{
                            		if(alloffice==null)
	                            	 {
	                            		 System.out.println("old report all Head + single month");
	                            		 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GeneralLedger_UnitWiseModule/GeneralLedgerReportAll_office_wings_main.jasper"));
	                            		 System.out.println("reportFile==>"+reportFile);
	                            	 }    
                            		 else if(alloffice.equalsIgnoreCase("all_office"))
		                                 {
		                              	  
		                              	   	 System.out.println("this is the case for ALL acc head + single month + for more than one office for single acc unit");
//		                              	   	reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GeneralLedger_UnitWiseModule/GeneralLedgerReportAll_singleunit.jasper"));
			                              	   	reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GeneralLedger_UnitWiseModule/GeneralLedger.jasper"));

		                              	  System.out.println("reportFile==>"+reportFile);
		                                 }
		                            	
                            	  // }
//	                            	catch(Exception e)
//	                            	{
//	                            		System.out.println("Exception arised here *******"+e);
//	                            	}
                            	sql = "SELECT cramt, " +
                                "  dramt, " +
                                "  ind, " +
                                "  recpno, " +
                                "  amt, " +
                                "  doctype, " +
                                "  type1, " +
                                "  date1, " +
                                "  particul, " +
                                "  acchead, " +
                                "  acc.ACCOUNT_HEAD_DESC, " +
                                "  TO_CHAR(glt.month_opening_balance) AS monthopen, " +
                                "  glt.MONTH_OPENING_BAL_DR_CR_IND    AS monthind, " +
                                "  TO_CHAR(glt.month_closing_balance) AS monthclose, " +
                                "  glt.MONTH_CLOSING_BAL_DR_CR_IND    AS monthind_close, " +
                                "  accUint.ACCOUNTING_UNIT_NAME       AS acc_unit_name, " +
                                "  Office.OFFICE_NAME                 AS off_name , " +
                                "  sl_type_code , " +
                                "  (SELECT sub_ledger_type_desc " +
                                "  FROM com_mst_sl_types " +
                                "  WHERE sub_ledger_type_code = sl_type_code " +
                                "  ) AS sl_type_code_desc, " +
                                "  sl_code, " +
                               // "  -- Supplier 1 " +
                                "  CASE " +
                                "    WHEN sl_type_code = 1 " +
                                "    THEN " +
                                "      (SELECT SUPPLIER_NAME " +
                                "      FROM COM_SUPPLIER_SL_MST " +
                                "      WHERE ACCOUNTING_UNIT_ID    ='"+accountingunit+"' " +
                                "      AND ACCOUNTING_FOR_OFFICE_ID='"+accountingoffice+"' " +
                                "      AND supplier_id             = sl_code " +
                                "      ) " +
                                "    ELSE ( "+
                                //--- Firms 2 " +
                                "      CASE " +
                                "        WHEN sl_type_code=2 " +
                                "        THEN " +
                                "          (SELECT FIRMS_NAME " +
                                "          FROM COM_FIRMS_SL_MST " +
                                "          WHERE ACCOUNTING_UNIT_ID    ='"+accountingunit+"' " +
                                "          AND ACCOUNTING_FOR_OFFICE_ID='"+accountingoffice+"' " +
                                "          AND firms_id                =sl_code " +
                                "          ) " +
                                "        ELSE ( "+
                                //--- Assests 3 " +
                                "          CASE " +
                                "            WHEN sl_type_code=3 " +
                                "            THEN " +
                                "              (SELECT ASSET_DESCRIPTION " +
                                "              FROM COM_MST_ASSETS_SL " +
                                "              WHERE ACCOUNTING_UNIT_ID    ='"+accountingunit+"' " +
                                "              AND ACCOUNTING_FOR_OFFICE_ID='"+accountingoffice+"' " +
                                "              AND asset_code              = sl_code " +
                                "              ) " +
                                "            ELSE ( "+
                                //--- Offices 5 " +
                                "              CASE " +
                                "                WHEN sl_type_code = 5 " +
                                "                THEN " +
                                "                  (SELECT OFFICE_NAME " +
                                "                  FROM COM_MST_OFFICES " +
                                "                  WHERE OFFICE_ID=sl_code " +
                                "                  ) " +
                                "                ELSE ( "+
                                //-- Bank 6 " +
                                "                  CASE " +
                                "                    WHEN sl_type_code = 6 " +
                                "                    THEN " +
                                "                      (SELECT TO_CHAR(bank_ac_no) " +
                                "                      FROM fas_mst_bank_balance " +
                                "                      WHERE ACCOUNTING_UNIT_ID  ='"+accountingunit+"' " +
                                "                      AND BANK_AC_NO_ALIAS_CODE = sl_code " +
                                "                      ) " +
                                "                    ELSE ( "+
                                //-- Employees 7 " +
                                "                      CASE " +
                                "                        WHEN sl_type_code= 7 " +
                                "                        THEN " +
                                "                          (SELECT e.EMPLOYEE_NAME " +
                                "                            ||'.' " +
                                "                            ||e.EMPLOYEE_INITIAL " +
                                "                            ||'-' " +
                                "                            || d.DESIGNATION AS ENAME " +
                                "                          FROM HRM_MST_EMPLOYEES e, " +
                                "                            HRM_EMP_CURRENT_POSTING c, " +
                                "                            HRM_MST_DESIGNATIONS d " +
                                "                          WHERE c.DESIGNATION_ID=d.DESIGNATION_ID " +
                                "                          AND c.EMPLOYEE_ID     =e.EMPLOYEE_ID " +
                                "                          AND c.OFFICE_ID       ='"+accountingoffice+"' " +
                                "                          AND e.EMPLOYEE_ID     =sl_code " +
                                "                          ) " +
                                "                        ELSE ( " +
                             //   "                          -- OTHER DEPARTMENTS " +
                                "                          CASE " +
                                "                            WHEN sl_type_code= 9 " +
                                "                            THEN " +
                                "                              (SELECT dep.OTHER_DEPT_NAME " +
                                "                                || '-' " +
                                "                                || off.OTHER_DEPT_OFFICE_NAME AS OTHER_DEPT_OFF_NAME " +
                                "                              FROM HRM_MST_OTHER_DEPTS dep, " +
                                "                                HRM_MST_OTHER_DEPT_OFFICES OFF " +
                                "                              WHERE dep.OTHER_DEPT_ID           =off.OTHER_DEPT_ID " +
                                "                              AND off.other_dept_office_alias_id= sl_code " +
                                "                              ) " +
                                "                            ELSE ( " +
                             //   "                              -- PROJECT " +
                                "                              CASE " +
                                "                                WHEN sl_type_code=10 " +
                                "                                THEN " +
                                "                                  (SELECT PROJECT_NAME " +
                                "                                  FROM PMS_MST_PROJECTS_VIEW " +
                                "                                  WHERE OFFICE_ID='"+accountingoffice+"' " +
                                "                                  AND PROJECT_ID = sl_code " +
                                "                                  ) " +
                                "                                ELSE ( " +
                             //   "                                  -- CONTRACTORS " +
                                "                                  CASE " +
                                "                                    WHEN sl_type_code=11 " +
                                "                                    THEN " +
                                "                                      (SELECT CONTRACTOR_NAME " +
                                "                                      FROM PMS_MST_CONTRACTORS_VIEW " +
                                "                                      WHERE (OFFICE_ID='"+accountingoffice+"' " +
                                "                                      OR OFFICE_ID   IN " +
                                "                                        (SELECT REGION_OFFICE_ID " +
                                "                                        FROM COM_MST_ALL_OFFICES_VIEW " +
                                "                                        WHERE office_id='"+accountingoffice+"' " +
                                "                                        UNION ALL " +
                                "                                        SELECT CIRCLE_OFFICE_ID " +
                                "                                        FROM COM_MST_ALL_OFFICES_VIEW " +
                                "                                        WHERE office_id='"+accountingoffice+"' " +
                                "                                        )) " +
                                "                                      AND CONTRACTOR_ID= sl_code " +
                                "                                      ) " +
                                "                                  END ) " +
                                "                              END ) " +
                                "                          END ) " +
                                "                      END ) " +
                                "                  END ) " +
                                "              END ) " +
                                "          END ) " +
                                "      END ) " +
                                "  END AS sl_code_name " +
                                "FROM " +
                                "  ("+
                                //-- 1. Receipt Master and Transaction CR Heads " +
                                "  SELECT a.account_head_code AS acchead, " +
                                "    a.cr_dr_indicator        AS ind, " +
                                "    a.receipt_no             AS recpno , " +
                                "    TO_CHAR(a.amount)        AS amt, " +
                                "    a.amount                 AS cramt, " +
                                "    0                        AS dramt, " +
                                "    'R'                      AS doctype, " +
                                "    NULL                     AS type1, " +
                                "    b.receipt_date           AS date1, " +
                                "    a.particulars            AS particul, " +
                                "    a.SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                                "    a.SUB_LEDGER_CODE        AS sl_code " +
                                "  FROM fas_receipt_transaction a, " +
                                "    fas_receipt_master b " +
                                "  WHERE a.accounting_unit_id    =b.accounting_unit_id " +
                                "  AND a.cashbook_year           =b.cashbook_year " +
                                "  AND a.cashbook_month          =b.cashbook_month " +
                                "  AND a.receipt_no              =b.receipt_no " +
                                "  AND a.accounting_for_office_id=b.ACCOUNTING_FOR_OFFICE_ID " +
                                "  AND b.receipt_status         <>'C' " +
                                "  AND a.accounting_unit_id      = '"+accountingunit+"' " +
                                "  AND a.accounting_for_office_id= '"+accountingoffice+"' " +
                                "  AND a.cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND a.cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND a.cr_dr_indicator='CR' " +
                                "  UNION ALL " +
                              //  "  -- 2. Journal Master and Transaction , CR Heads , GJV and LJV " +
                                "  SELECT a.account_head_code AS acchead, " +
                                "    a.cr_dr_indicator        AS ind, " +
                                "    a.voucher_no             AS recpno, " +
                                "    TO_CHAR(a.amount)        AS amt, " +
                                "    a.amount                 AS cramt , " +
                                "    0                        AS dramt, " +
                                "    'J'                      AS doctype, " +
                                "    b.journal_type_code      AS type1, " +
                                "    b.voucher_date           AS date1, " +
                                "    a.particulars            AS particul, " +
                                "    a.SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                                "    a.SUB_LEDGER_CODE        AS sl_code " +
                                "  FROM fas_journal_transaction a, " +
                                "    fas_journal_master b " +
                                "  WHERE a.accounting_unit_id     =b.accounting_unit_id " +
                                "  AND a.cashbook_year            =b.cashbook_year " +
                                "  AND a.cashbook_month           =b.cashbook_month " +
                                "  AND a.voucher_no               =b.voucher_no " +
                                "  AND a.accounting_for_office_id =b.ACCOUNTING_FOR_OFFICE_ID " +
                                "  AND b.journal_status          <>'C' " +
                                "  AND a.accounting_unit_id       = '"+accountingunit+"' " +
                                "  AND a.accounting_for_office_id = '"+accountingoffice+"' " +
                                "  AND a.cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND a.cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND CR_DR_INDICATOR      ='CR' " +
                                "  AND b.created_by_module IN ('GJV','LJV') " +
                                "  UNION ALL " +
                            //    "  -- 3. Journal Master and Transaction , DR Heads , GJV and LJV " +
                                "  SELECT a.account_head_code AS acchead, " +
                                "    a.cr_dr_indicator        AS ind, " +
                                "    a.voucher_no             AS recpno, " +
                                "    TO_CHAR(a.amount)        AS amt, " +
                                "    0                        AS cramt, " +
                                "    a.amount                 AS dramt, " +
                                "    'J'                      AS doctype, " +
                                "    b.journal_type_code      AS type1, " +
                                "    b.voucher_date           AS date1, " +
                                "    a.particulars            AS particul, " +
                                "    a.SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                                "    a.SUB_LEDGER_CODE        AS sl_code " +
                                "  FROM fas_journal_transaction a, " +
                                "    fas_journal_master b " +
                                "  WHERE a.accounting_unit_id    =b.accounting_unit_id " +
                                "  AND a.cashbook_year           =b.cashbook_year " +
                                "  AND a.cashbook_month          =b.cashbook_month " +
                                "  AND a.voucher_no              =b.voucher_no " +
                                "  AND a.accounting_for_office_id=b.ACCOUNTING_FOR_OFFICE_ID " +
                                "  AND b.journal_status         <>'C' " +
                                "  AND a.accounting_unit_id      = '"+accountingunit+"' " +
                                "  AND a.accounting_for_office_id= '"+accountingoffice+"' " +
                                "  AND a.cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND a.cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND CR_DR_INDICATOR      ='DR' " +
                                "  AND b.created_by_module IN ('GJV','LJV') " +
                                "  UNION ALL " +
                              //  "  -- 4. Payment Master and Transaction , DR Heads " +
                                "  SELECT a.account_head_code AS acchead, " +
                                "    a.cr_dr_indicator        AS ind, " +
                                "    a.voucher_no             AS recpno, " +
                                "    TO_CHAR(a.amount)        AS amt, " +
                                "    0                        AS cramt, " +
                                "    a.amount                 AS dramt, " +
                                "    'P'                      AS doctype, " +
                                "    NULL                     AS type1, " +
                                "    b.payment_date           AS date1, " +
                                "    a.particulars            AS particul, " +
                                "    a.SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                                "    a.SUB_LEDGER_CODE        AS sl_code " +
                                "  FROM fas_payment_transaction a, " +
                                "    fas_payment_master b " +
                                "  WHERE a.accounting_unit_id     =b.accounting_unit_id " +
                                "  AND a.cashbook_year            =b.cashbook_year " +
                                "  AND a.cashbook_month           =b.cashbook_month " +
                                "  AND a.voucher_no               =b.voucher_no " +
                                "  AND a.accounting_for_office_id =b.ACCOUNTING_FOR_OFFICE_ID " +
                                "  AND b.payment_status          <>'C' " +
                                "  AND a.accounting_unit_id       = '"+accountingunit+"' " +
                                "  AND a.accounting_for_office_id = '"+accountingoffice+"' " +
                                "  AND a.cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND a.cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND a.cr_dr_indicator='DR' " +
                                "  UNION ALL " +
                               // "  -- 5. Receipt Master , DR Heads " +
                                "  SELECT account_head_code AS acchead, " +
                                "    cr_dr_indicator        AS ind, " +
                                "    receipt_no             AS recpno, " +
                                "    TO_CHAR(TOTAL_AMOUNT)  AS amt, " +
                                "    0                      AS cramt, " +
                                "    total_amount           AS dramt , " +
                                "    'R'                    AS doctype, " +
                                "    NULL                   AS type1, " +
                                "    receipt_date           AS date1, " +
                                "    remarks                AS particul, " +
                                "    SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                                "    SUB_LEDGER_CODE        AS sl_code " +
                                "  FROM fas_receipt_master " +
                                "  WHERE receipt_status       <>'C' " +
                                "  AND accounting_unit_id      = '"+accountingunit+"' " +
                                "  AND accounting_for_office_id= '"+accountingoffice+"' " +
                                "  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND cr_dr_indicator='DR' " +
                                "  UNION ALL " +
                              //  "  -- 6. Payment Master , CR Heads " +
                                "  SELECT account_head_code AS acchead, " +
                                "    cr_dr_indicator        AS ind, " +
                                "    voucher_no             AS recpno, " +
                                "    TO_CHAR(TOTAL_AMOUNT)  AS amt, " +
                                "    total_amount           AS cramt , " +
                                "    0                      AS dramt, " +
                                "    'P'                    AS doctype, " +
                                "    NULL                   AS type1, " +
                                "    PAYMENT_DATE           AS date1, " +
                                "    remarks                AS particul, " +
                                "    SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                                "    SUB_LEDGER_CODE        AS sl_code " +
                                "  FROM fas_payment_master " +
                                "  WHERE payment_status        <>'C' " +
                                "  AND accounting_unit_id       = '"+accountingunit+"' " +
                                "  AND accounting_for_office_id = '"+accountingoffice+"' " +
                                "  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND cr_dr_indicator='CR' " +
                                "  UNION ALL " +
                               // "  -- 7. Fund Receipt by Office , CR Heads " +
                                "  SELECT cr_account_head_code AS acchead, " +
                                "    'CR'                      AS ind, " +
                                "    receipt_no                AS recpno, " +
                                "    TO_CHAR(TOTAL_AMOUNT)     AS amt, " +
                                "    total_amount              AS cramt, " +
                                "    0                         AS dramt , " +
                                "    'FR'                      AS doctype, " +
                                "    NULL                      AS type1, " +
                                "    receipt_date              AS date1, " +
                                "    PARTICULARS               AS particul, " +
                                "    0                         AS sl_type_code, " +
                                "    0                         AS sl_code " +
                                "  FROM FAS_FUND_RECEIPT_BY_OFFICE " +
                                "  WHERE receipt_status        <>'C' " +
                                "  AND accounting_unit_id       = '"+accountingunit+"' " +
                                "  AND accounting_for_office_id = '"+accountingoffice+"' " +
                                "  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  UNION ALL " +
                              //  "  -- 8. Fund Receipt By Office , DR Heads " +
                                "  SELECT dr_account_head_code AS acchead, " +
                                "    'DR'                      AS ind, " +
                                "    receipt_no                AS recpno, " +
                                "    TO_CHAR(TOTAL_AMOUNT)     AS amt, " +
                                "    0                         AS cramt, " +
                                "    total_amount              AS dramt, " +
                                "    'FR'                      AS doctype, " +
                                "    NULL                      AS type1, " +
                                "    receipt_date              AS date1, " +
                                "    PARTICULARS               AS particul, " +
                                "    0                         AS sl_type_code, " +
                                "    0                         AS sl_code " +
                                "  FROM FAS_FUND_RECEIPT_BY_OFFICE " +
                                "  WHERE receipt_status        <>'C' " +
                                "  AND accounting_unit_id       = '"+accountingunit+"' " +
                                "  AND accounting_for_office_id = '"+accountingoffice+"' " +
                                "  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  UNION ALL " +
                              //  "  --  9. Fund Transfer from office , CR Heads " +
                                "  SELECT cr_account_head_code AS acchead, " +
                                "    'CR'                      AS ind, " +
                                "    voucher_no                AS recpno, " +
                                "    TO_CHAR(TOTAL_AMOUNT)     AS amt, " +
                                "    total_amount              AS cramt, " +
                                "    0                         AS dramt, " +
                                "    'FT'                      AS doctype, " +
                                "    NULL                      AS type1, " +
                                "    DATE_OF_TRANSFER          AS date1, " +
                                "    PARTICULARS               AS particul, " +
                                "    0                         AS sl_type_code, " +
                                "    0                         AS sl_code " +
                                "  FROM FAS_FUND_TRF_FROM_OFFICE " +
                                "  WHERE transfer_status       <>'C' " +
                                "  AND accounting_unit_id       = '"+accountingunit+"' " +
                                "  AND accounting_for_office_id = '"+accountingoffice+"' " +
                                "  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  UNION ALL " +
                              //  "  -- 10. Fund Transfer from Office , DR Heads " +
                                "  SELECT dr_account_head_code AS acchead, " +
                                "    'DR'                      AS ind, " +
                                "    voucher_no                AS recpno, " +
                                "    TO_CHAR(TOTAL_AMOUNT)     AS amt, " +
                                "    0                         AS cramt, " +
                                "    total_amount              AS dramt, " +
                                "    'FT'                      AS doctype, " +
                                "    NULL                      AS type1, " +
                                "    DATE_OF_TRANSFER          AS date1, " +
                                "    PARTICULARS               AS particul, " +
                                "    0                         AS sl_type_code, " +
                                "    0                         AS sl_code " +
                                "  FROM FAS_FUND_TRF_FROM_OFFICE " +
                                "  WHERE transfer_status       <>'C' " +
                                "  AND accounting_unit_id       = '"+accountingunit+"' " +
                                "  AND accounting_for_office_id = '"+accountingoffice+"' " +
                                "  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  UNION ALL " +
                               // "  -- 11. IBT, CR Heads " +
                                "  SELECT cr_account_head_code AS acchead, " +
                                "    'CR' ind, " +
                                "    voucher_no            AS recpno, " +
                                "    TO_CHAR(total_amount) AS amt, " +
                                "    total_amount          AS cramt, " +
                                "    0                     AS dramt, " +
                                "    'IBT'                 AS doctype, " +
                                "    NULL                  AS type1, " +
                                "    date_of_transfer      AS date1, " +
                                "    particulars           AS particul , " +
                                "    0                     AS sl_type_code, " +
                                "    0                     AS sl_code " +
                                "  FROM fas_inter_bank_trf_at_ho " +
                                "  WHERE transfer_status      <>'C' " +
                                "  AND accounting_unit_id      = '"+accountingunit+"' " +
                                "  AND accounting_for_office_id= '"+accountingoffice+"' " +
                                "  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  UNION ALL " +
                              //  "  -- 12. IBT , DR Heads " +
                                "  SELECT dr_account_head_code AS acchead, " +
                                "    'DR' ind, " +
                                "    voucher_no            AS recpno, " +
                                "    TO_CHAR(total_amount) AS amt, " +
                                "    0                     AS cramt, " +
                                "    total_amount          AS dramt, " +
                                "    'IBT'                 AS doctype, " +
                                "    NULL                  AS type1, " +
                                "    date_of_transfer      AS date1, " +
                                "    particulars           AS particul, " +
                                "    0                     AS sl_type_code, " +
                                "    0                     AS sl_code " +
                                "  FROM fas_inter_bank_trf_at_ho " +
                                "  WHERE transfer_status      <>'C' " +
                                "  AND accounting_unit_id      = '"+accountingunit+"' " +
                                "  AND accounting_for_office_id= '"+accountingoffice+"' " +
                                "  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  ), " +
                                "  com_mst_account_heads acc, " +
                                "  fas_general_ledger glt , " +
                                "  COM_MST_OFFICES Office, " +
                                "  FAS_MST_ACCT_UNITS accUint " +
                                "WHERE acc.account_head_code     =acchead " +
                                "AND acchead                     =glt.account_head_code " +
                                "AND glt.accounting_unit_id      = '"+accountingunit+"' " +
                                "AND glt.accounting_for_office_id='"+accountingoffice+"' " +
                                "AND glt.year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "AND glt.month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "AND accUint.ACCOUNTING_UNIT_ID= '"+accountingunit+"' " +
                                "AND Office.OFFICE_ID          = '"+accountingoffice+"' " +
                                "ORDER BY acchead, " +
                                "  date1, " +
                                "  doctype, " +
                                "  recpno";
                                System.out.println("testing **********"+sql);
//                            }
                          }
                     }
                     /**
                      * Single Month + Single A/C Head 
                      */
                     else
                     {
                         System.out.println("report for single accHead for single month");   
                    	 if(accountingunit==5)
                    	 {
                            	// For Head Office
                            	
                                sql="SELECT cramt, " +
                                "  dramt, " +
                                "  ind, " +
                                "  recpno , " +
                                "  amt, " +
                                "  doctype, " +
                                "  type1, " +
                                "  date1, " +
                                "  particul, " +
                                "  acc.ACCOUNT_HEAD_DESC, " +
                                "  TO_CHAR(glt.month_opening_balance) AS monthopen, " +
                                "  glt.MONTH_OPENING_BAL_DR_CR_IND    AS monthind , " +
                                "  TO_CHAR(glt.month_closing_balance) AS monthclose, " +
                                "  glt.MONTH_CLOSING_BAL_DR_CR_IND    AS monthind_close , " +
                                "  accUint.ACCOUNTING_UNIT_NAME       AS acc_unit_name, " +
                                "  Office.OFFICE_NAME                 AS off_name , " +
                                "  sl_type_code , " +
                                "  (SELECT sub_ledger_type_desc " +
                                "  FROM com_mst_sl_types " +
                                "  WHERE sub_ledger_type_code = sl_type_code " +
                                "  ) AS sl_type_code_desc, " +
                                "  sl_code, " +
                               // "  -- Supplier 1 " +
                                "  CASE " +
                                "    WHEN sl_type_code = 1 " +
                                "    THEN " +
                                "      (SELECT SUPPLIER_NAME " +
                                "      FROM COM_SUPPLIER_SL_MST " +
                                "      WHERE ACCOUNTING_UNIT_ID    ='"+accountingunit+"' " +
                                "      AND ACCOUNTING_FOR_OFFICE_ID='"+accountingoffice+"' " +
                                "      AND supplier_id             = sl_code " +
                                "      ) " +
                                "    ELSE ( " +
                              //  "--- Firms 2 " +
                                "      CASE " +
                                "        WHEN sl_type_code=2 " +
                                "        THEN " +
                                "          (SELECT FIRMS_NAME " +
                                "          FROM COM_FIRMS_SL_MST " +
                                "          WHERE ACCOUNTING_UNIT_ID    ='"+accountingunit+"' " +
                                "          AND ACCOUNTING_FOR_OFFICE_ID='"+accountingoffice+"' " +
                                "          AND firms_id                =sl_code " +
                                "          ) " +
                                "        ELSE ( " +
                               // "--- Assests 3 " +
                                "          CASE " +
                                "            WHEN sl_type_code=3 " +
                                "            THEN " +
                                "              (SELECT ASSET_DESCRIPTION " +
                                "              FROM COM_MST_ASSETS_SL " +
                                "              WHERE ACCOUNTING_UNIT_ID    ='"+accountingunit+"' " +
                                "              AND ACCOUNTING_FOR_OFFICE_ID='"+accountingoffice+"' " +
                                "              AND asset_code              = sl_code " +
                                "              ) " +
                                "            ELSE ( " +
                                //"--- Offices 5 " +
                                "              CASE " +
                                "                WHEN sl_type_code = 5 " +
                                "                THEN " +
                                "                  (SELECT OFFICE_NAME " +
                                "                  FROM COM_MST_OFFICES " +
                                "                  WHERE OFFICE_ID=sl_code " +
                                "                  ) " +
                                "                ELSE (" +
                                //" -- Bank 6 " +
                                "                  CASE " +
                                "                    WHEN sl_type_code = 6 " +
                                "                    THEN " +
                                "                      (SELECT TO_CHAR(bank_ac_no) " +
                                "                      FROM fas_mst_bank_balance " +
                                "                      WHERE ACCOUNTING_UNIT_ID  ='"+accountingunit+"' " +
                                "                      AND BANK_AC_NO_ALIAS_CODE = sl_code " +
                                "                      ) " +
                                "                    ELSE ( " +
                             //   "-- Employees 7 " +
                                "                      CASE " +
                                "                        WHEN sl_type_code= 7 " +
                                "                        THEN " +
                                "                          (SELECT e.EMPLOYEE_NAME " +
                                "                            ||'.' " +
                                "                            ||e.EMPLOYEE_INITIAL " +
                                "                            ||'-' " +
                                "                            || d.DESIGNATION AS ENAME " +
                                "                          FROM HRM_MST_EMPLOYEES e, " +
                                "                            HRM_EMP_CURRENT_POSTING c, " +
                                "                            HRM_MST_DESIGNATIONS d " +
                                "                          WHERE c.DESIGNATION_ID=d.DESIGNATION_ID " +
                                "                          AND c.EMPLOYEE_ID     =e.EMPLOYEE_ID " +
                                "                          AND c.OFFICE_ID       ='"+accountingoffice+"' " +
                                "                          AND e.EMPLOYEE_ID     =sl_code " +
                                "                          ) " +
                                "                        ELSE ( " +
                             //  "                          -- OTHER DEPARTMENTS " +
                                "                          CASE " +
                                "                            WHEN sl_type_code= 9 " +
                                "                            THEN " +
                                "                              (SELECT dep.OTHER_DEPT_NAME " +
                                "                                || '-' " +
                                "                                || off.OTHER_DEPT_OFFICE_NAME AS OTHER_DEPT_OFF_NAME " +
                                "                              FROM HRM_MST_OTHER_DEPTS dep, " +
                                "                                HRM_MST_OTHER_DEPT_OFFICES OFF " +
                                "                              WHERE dep.OTHER_DEPT_ID           =off.OTHER_DEPT_ID " +
                                "                              AND off.other_dept_office_alias_id= sl_code " +
                                "                              ) " +
                                "                            ELSE ( " +
                             //   "                              -- PROJECT " +
                                "                              CASE " +
                                "                                WHEN sl_type_code=10 " +
                                "                                THEN " +
                                "                                  (SELECT PROJECT_NAME " +
                                "                                  FROM PMS_MST_PROJECTS_VIEW " +
                                "                                  WHERE OFFICE_ID='"+accountingoffice+"' " +
                                "                                  AND PROJECT_ID = sl_code " +
                                "                                  ) " +
                                "                                ELSE ( " +
                              //  "                                  -- CONTRACTORS " +
                                "                                  CASE " +
                                "                                    WHEN sl_type_code=11 " +
                                "                                    THEN " +
                                "                                      (SELECT CONTRACTOR_NAME " +
                                "                                      FROM PMS_MST_CONTRACTORS_VIEW " +
                                "                                      WHERE (OFFICE_ID='"+accountingoffice+"' " +
                                "                                      OR OFFICE_ID   IN " +
                                "                                        (SELECT REGION_OFFICE_ID " +
                                "                                        FROM COM_MST_ALL_OFFICES_VIEW " +
                                "                                        WHERE office_id='"+accountingoffice+"' " +
                                "                                        UNION ALL " +
                                "                                        SELECT CIRCLE_OFFICE_ID " +
                                "                                        FROM COM_MST_ALL_OFFICES_VIEW " +
                                "                                        WHERE office_id='"+accountingoffice+"' " +
                                "                                        )) " +
                                "                                      AND CONTRACTOR_ID= sl_code " +
                                "                                      ) " +
                                "                                  END ) " +
                                "                              END ) " +
                                "                          END ) " +
                                "                      END ) " +
                                "                  END ) " +
                                "              END ) " +
                                "          END ) " +
                                "      END ) " +
                                "  END AS sl_code_name " +
                                "FROM " +
                                "  ( " +
                               // "  --1. Receipt Master and Transaction , CR Heads " +
                                "  SELECT a.account_head_code AS acchead, " +
                                "    a.cr_dr_indicator        AS ind, " +
                                "    a.receipt_no             AS recpno , " +
                                "    TO_CHAR(a.amount)        AS amt, " +
                                "    a.amount                 AS cramt, " +
                                "    0                        AS dramt, " +
                                "    'R'                      AS doctype, " +
                                "    NULL                     AS type1, " +
                                "    b.receipt_date           AS date1, " +
                                "    a.particulars            AS particul, " +
                                "    a.SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                                "    a.SUB_LEDGER_CODE        AS sl_code " +
                                "  FROM fas_receipt_transaction a, " +
                                "    fas_receipt_master b " +
                                "  WHERE a.accounting_unit_id     =b.accounting_unit_id " +
                                "  AND a.cashbook_year            =b.cashbook_year " +
                                "  AND a.cashbook_month           =b.cashbook_month " +
                                "  AND a.receipt_no               =b.receipt_no " +
                                "  AND a.accounting_for_office_id =b.ACCOUNTING_FOR_OFFICE_ID " +
                                "  AND b.receipt_status          <>'C' " +
                                "  AND a.accounting_unit_id       ='"+accountingunit+"' " +
                                "  AND a.accounting_for_office_id ='"+accountingoffice+"' " +
                                "  AND a.cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND a.cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND a.cr_dr_indicator  ='CR' " +
                                "  AND a.account_head_code= '"+accountheadcode+"' " +
                                "  UNION ALL " +
                              //  "  -- 2. Journal Master and Transaction , CR Heads , GJV and LJV " +
                                "  SELECT a.account_head_code AS acchead, " +
                                "    a.cr_dr_indicator        AS ind, " +
                                "    a.voucher_no             AS recpno, " +
                                "    TO_CHAR(a.amount)        AS amt, " +
                                "    a.amount                 AS cramt, " +
                                "    0                        AS dramt, " +
                                "    'J'                      AS doctype, " +
                                "    b.journal_type_code      AS type1, " +
                                "    b.voucher_date           AS date1, " +
                                "    a.particulars            AS particul, " +
                                "    a.SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                                "    a.SUB_LEDGER_CODE        AS sl_code " +
                                "  FROM fas_journal_transaction a, " +
                                "    fas_journal_master b " +
                                "  WHERE a.accounting_unit_id     =b.accounting_unit_id " +
                                "  AND a.cashbook_year            =b.cashbook_year " +
                                "  AND a.cashbook_month           =b.cashbook_month " +
                                "  AND a.voucher_no               =b.voucher_no " +
                                "  AND a.accounting_for_office_id =b.ACCOUNTING_FOR_OFFICE_ID " +
                                "  AND b.journal_status          <>'C' " +
                                "  AND a.accounting_unit_id       ='"+accountingunit+"' " +
                                "  AND a.accounting_for_office_id ='"+accountingoffice+"' " +
                                "  AND a.cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND a.cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND a.account_head_code  ='"+accountheadcode+"' " +
                                "  AND a.cr_dr_indicator    ='CR' " +
                                "  AND b.created_by_module IN ('GJV','LJV') " +
                                "  UNION ALL " +
                               // "  -- 3. Journal Master and Transaction, DR Heads , GJV and LJV " +
                                "  SELECT a.account_head_code AS acchead, " +
                                "    a.cr_dr_indicator        AS ind, " +
                                "    a.voucher_no             AS recpno, " +
                                "    TO_CHAR(a.amount)        AS amt, " +
                                "    0                        AS cramt, " +
                                "    a.amount                 AS dramt, " +
                                "    'J'                      AS doctype, " +
                                "    b.journal_type_code      AS type1, " +
                                "    b.voucher_date           AS date1, " +
                                "    a.particulars            AS particul, " +
                                "    a.SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                                "    a.SUB_LEDGER_CODE        AS sl_code " +
                                "  FROM fas_journal_transaction a, " +
                                "    fas_journal_master b " +
                                "  WHERE a.accounting_unit_id     =b.accounting_unit_id " +
                                "  AND a.cashbook_year            =b.cashbook_year " +
                                "  AND a.cashbook_month           =b.cashbook_month " +
                                "  AND a.voucher_no               =b.voucher_no " +
                                "  AND a.accounting_for_office_id =b.ACCOUNTING_FOR_OFFICE_ID " +
                                "  AND b.journal_status          <>'C' " +
                                "  AND a.accounting_unit_id       ='"+accountingunit+"' " +
                                "  AND a.accounting_for_office_id ='"+accountingoffice+"' " +
                                "  AND a.cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND a.cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND a.account_head_code  ='"+accountheadcode+"' " +
                                "  AND a.cr_dr_indicator    ='DR' " +
                                "  AND b.created_by_module IN ('GJV','LJV') " +
                                "  UNION ALL " +
                               // "  -- 4. Payment Master and Transaction , DR Heads " +
                                "  SELECT a.account_head_code AS acchead, " +
                                "    a.cr_dr_indicator        AS ind, " +
                                "    a.voucher_no             AS recpno, " +
                                "    TO_CHAR(a.amount)        AS amt, " +
                                "    0                        AS cramt, " +
                                "    a.amount                 AS dramt, " +
                                "    'P'                      AS doctype, " +
                                "    NULL                     AS type1, " +
                                "    b.payment_date           AS date1, " +
                                "    a.particulars            AS particul, " +
                                "    a.SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                                "    a.SUB_LEDGER_CODE        AS sl_code " +
                                "  FROM fas_payment_transaction a, " +
                                "    fas_payment_master b " +
                                "  WHERE a.accounting_unit_id     =b.accounting_unit_id " +
                                "  AND a.cashbook_year            =b.cashbook_year " +
                                "  AND a.cashbook_month           =b.cashbook_month " +
                                "  AND a.voucher_no               =b.voucher_no " +
                                "  AND a.accounting_for_office_id =b.ACCOUNTING_FOR_OFFICE_ID " +
                                "  AND b.payment_status          <>'C' " +
                                "  AND a.accounting_unit_id       ='"+accountingunit+"' " +
                                "  AND a.accounting_for_office_id ='"+accountingoffice+"' " +
                                "  AND a.cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND a.cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND a.cr_dr_indicator   ='DR' " +
                                "  AND a.account_head_code ='"+accountheadcode+"' " +
                                "  UNION ALL " +
                             //   "  --- 5. Receipt Master , DR Heads " +
                                "  SELECT account_head_code AS acchead, " +
                                "    cr_dr_indicator        AS ind, " +
                                "    receipt_no             AS recpno, " +
                                "    TO_CHAR(TOTAL_AMOUNT)  AS amt, " +
                                "    0                      AS cramt, " +
                                "    TOTAL_AMOUNT           AS dramt, " +
                                "    'R'                    AS doctype, " +
                                "    NULL                   AS type1, " +
                                "    receipt_date           AS date1, " +
                                "    remarks                AS particul, " +
                                "    SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                                "    SUB_LEDGER_CODE        AS sl_code " +
                                "  FROM fas_receipt_master " +
                                "  WHERE receipt_status        <>'C' " +
                                "  AND accounting_unit_id       ='"+accountingunit+"' " +
                                "  AND accounting_for_office_id ='"+accountingoffice+"' " +
                                "  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND account_head_code ='"+accountheadcode+"' " +
                                "  AND cr_dr_indicator   ='DR' " +
                                "  UNION ALL " +
                              //  "  -- 6. Payment Master , CR Heads " +
                                "  SELECT account_head_code AS acchead, " +
                                "    cr_dr_indicator        AS ind, " +
                                "    voucher_no             AS recpno, " +
                                "    TO_CHAR(TOTAL_AMOUNT)  AS amt, " +
                                "    TOTAL_AMOUNT           AS cramt, " +
                                "    0                      AS dramt, " +
                                "    'P'                    AS doctype, " +
                                "    NULL                   AS type1, " +
                                "    PAYMENT_DATE           AS date1, " +
                                "    remarks                AS particul, " +
                                "    SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                                "    SUB_LEDGER_CODE        AS sl_code " +
                                "  FROM fas_payment_master " +
                                "  WHERE payment_status        <>'C' " +
                                "  AND accounting_unit_id       ='"+accountingunit+"' " +
                                "  AND accounting_for_office_id ='"+accountingoffice+"' " +
                                "  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND account_head_code ='"+accountheadcode+"' " +
                                "  AND cr_dr_indicator   ='CR' " +
                                "  UNION ALL " +
                              //  "  -- 7. Fund Receipt By HO, CR Heads " +
                                "  SELECT cr_account_head_code AS acchead, " +
                                "    'CR'                      AS ind, " +
                                "    receipt_no                AS recpno, " +
                                "    TO_CHAR(TOTAL_AMOUNT)     AS amt, " +
                                "    total_amount              AS cramt, " +
                                "    0                         AS dramt, " +
                                "    'FR'                      AS doctype, " +
                                "    NULL                      AS type1, " +
                                "    receipt_date              AS date1, " +
                                "    PARTICULARS               AS particul, " +
                                "    0                         AS sl_type_code, " +
                                "    0                         AS sl_code " +
                                "  FROM fas_fund_receipt_by_ho " +
                                "  WHERE receipt_status        <>'C' " +
                                "  AND accounting_unit_id       ='"+accountingunit+"' " +
                                "  AND accounting_for_office_id ='"+accountingoffice+"' " +
                                "  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND cr_account_head_code ='"+accountheadcode+"' " +
                                "  UNION ALL " +
                             //   "  -- 8. Fund Receipt by HO , DR Heads " +
                                "  SELECT dr_account_head_code AS acchead, " +
                                "    'DR'                      AS ind, " +
                                "    receipt_no                AS recpno, " +
                                "    TO_CHAR(TOTAL_AMOUNT)     AS amt, " +
                                "    0                         AS cramt, " +
                                "    total_amount              AS dramt, " +
                                "    'FR'                      AS doctype, " +
                                "    NULL                      AS type1, " +
                                "    receipt_date              AS date1, " +
                                "    PARTICULARS               AS particul, " +
                                "    0                         AS sl_type_code, " +
                                "    0                         AS sl_code " +
                                "  FROM fas_fund_receipt_by_ho " +
                                "  WHERE receipt_status        <>'C' " +
                                "  AND accounting_unit_id       ='"+accountingunit+"' " +
                                "  AND accounting_for_office_id ='"+accountingoffice+"' " +
                                "  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND dr_account_head_code ='"+accountheadcode+"' " +
                                "  UNION ALL " +
                              //  "  -- 9. Fund Transfer from HO Master and Transaction , DR Heads " +
                                "  SELECT a.account_head_code AS acchead, " +
                                "    a.cr_dr_indicator        AS ind, " +
                                "    a.voucher_no             AS recpno , " +
                                "    TO_CHAR(a.amount)        AS amt, " +
                                "    0                        AS cramt, " +
                                "    a.amount                 AS dramt, " +
                                "    'FT'                     AS doctype, " +
                                "    NULL                     AS type1, " +
                                "    b.DATE_OF_TRANSFER       AS date1, " +
                                "    a.particulars            AS particul, " +
                                "    0                        AS sl_type_code, " +
                                "    0                        AS sl_code " +
                                "  FROM fas_fund_trf_from_ho_trn a, " +
                                "    fas_fund_trf_from_ho_master b " +
                                "  WHERE a.accounting_unit_id     =b.accounting_unit_id " +
                                "  AND a.cashbook_year            =b.cashbook_year " +
                                "  AND a.cashbook_month           =b.cashbook_month " +
                                "  AND a.voucher_no               =b.voucher_no " +
                                "  AND a.accounting_for_office_id =b.ACCOUNTING_FOR_OFFICE_ID " +
                                "  AND b.TRANSFER_STATUS         <>'C' " +
                                "  AND a.accounting_unit_id       ='"+accountingunit+"' " +
                                "  AND a.accounting_for_office_id ='"+accountingoffice+"' " +
                                "  AND a.cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND a.cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND a.account_head_code ='"+accountheadcode+"' " +
                                "  AND a.cr_dr_indicator   ='DR' " +
                                "  UNION ALL " +
                              //  "  -- 10. Fund Transfer from HO master , CR Heads " +
                                "  SELECT account_head_code AS acchead, " +
                                "    cr_dr_indicator        AS ind, " +
                                "    voucher_no             AS recpno, " +
                                "    TO_CHAR(TOTAL_AMOUNT)  AS amt, " +
                                "    TOTAL_AMOUNT           AS cramt, " +
                                "    0                      AS dramt, " +
                                "    'FT'                   AS doctype, " +
                                "    NULL                   AS type1, " +
                                "    DATE_OF_TRANSFER       AS date1, " +
                                "    remarks                AS particul, " +
                                "    0                      AS sl_type_code, " +
                                "    0                      AS sl_code " +
                                "  FROM fas_fund_trf_from_ho_master " +
                                "  WHERE transfer_status       <>'C' " +
                                "  AND accounting_unit_id       ='"+accountingunit+"' " +
                                "  AND accounting_for_office_id ='"+accountingoffice+"' " +
                                "  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND account_head_code ='"+accountheadcode+"' " +
                                "  AND cr_dr_indicator   ='CR' " +
                                "  UNION ALL " +
                             //   "  -- 11. IBT, CR Heads " +
                                "  SELECT cr_account_head_code AS acchead, " +
                                "    'CR' ind, " +
                                "    voucher_no            AS recpno, " +
                                "    TO_CHAR(total_amount) AS amt, " +
                                "    total_amount          AS cramt, " +
                                "    0                     AS dramt, " +
                                "    'IBT'                 AS doctype, " +
                                "    NULL                  AS type1, " +
                                "    date_of_transfer      AS date1, " +
                                "    particulars           AS particul, " +
                                "    0                     AS sl_type_code, " +
                                "    0                     AS sl_code " +
                                "  FROM fas_inter_bank_trf_at_ho " +
                                "  WHERE transfer_status      <>'C' " +
                                "  AND accounting_unit_id      = '"+accountingunit+"' " +
                                "  AND accounting_for_office_id= '"+accountingoffice+"' " +
                                "  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND cr_account_head_code ='"+accountheadcode+"' " +
                                "  UNION ALL " +
                             //   "  -- 12. IBT , DR Heads " +
                                "  SELECT dr_account_head_code AS acchead, " +
                                "    'DR' ind, " +
                                "    voucher_no            AS recpno, " +
                                "    TO_CHAR(total_amount) AS amt, " +
                                "    0                     AS cramt, " +
                                "    total_amount          AS dramt, " +
                                "    'IBT'                 AS doctype, " +
                                "    NULL                  AS type1, " +
                                "    date_of_transfer      AS date1, " +
                                "    particulars           AS particul, " +
                                "    0                     AS sl_type_code, " +
                                "    0                     AS sl_code " +
                                "  FROM fas_inter_bank_trf_at_ho " +
                                "  WHERE transfer_status      <>'C' " +
                                "  AND accounting_unit_id      = '"+accountingunit+"' " +
                                "  AND accounting_for_office_id= '"+accountingoffice+"' " +
                                "  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "  AND dr_account_head_code ='"+accountheadcode+"' " +
                                "  ), " +
                                "  com_mst_account_heads acc, " +
                                "  fas_general_ledger glt , " +
                                "  COM_MST_OFFICES Office, " +
                                "  FAS_MST_ACCT_UNITS accUint " +
                                "WHERE acc.account_head_code      =acchead " +
                                "AND acchead                      =glt.account_head_code " +
                                "AND glt.accounting_unit_id       ='"+accountingunit+"' " +
                                "AND glt.accounting_for_office_id ='"+accountingoffice+"' " +
                                "AND glt.year BETWEEN ('"+year+"') AND ('"+year+"') " +
                                "AND glt.month BETWEEN ('"+month+"') AND ('"+month+"') " +
                                "AND accUint.ACCOUNTING_UNIT_ID = '"+accountingunit+"' " +
                                "AND Office.OFFICE_ID           = '"+accountingoffice+"' " +
                                "ORDER BY date1, " +
                                "  doctype, " +
                                "  recpno";
                                System.out.println("testing +++++++++++++++"+sql);
                                reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GeneralLedger_UnitWiseModule/GeneralLedgerReport_banking_main.jasper"));
                                map.put("sql",sql);
                            }else
                            {
//                            	try
//                            	{
                            		// For Other Offices
	                               	 if(alloffice==null)
	                               	 {
	                               		 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GeneralLedger_UnitWiseModule/GeneralLedgerReport_office_wings_main.jasper"));
	                               		System.out.println("reportFile==>"+reportFile);
	                               	 
	                               	 }
	                               	 else if(alloffice.equalsIgnoreCase("all_office"))
	                                 {
	                              	  
	                              	   	 System.out.println("this is the case for single acc head + single month + for more than one office for single acc unit");
	                              	   	reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GeneralLedger_UnitWiseModule/GeneralLedgerReport_office_wings_main_moreofficesingleunit.jasper"));
	                                 System.out.println("reportFile==>"+reportFile);
	                                 
	                                 }
                            	
//                            	}
//                            	catch(Exception e)
//                            	{
//                            		System.out.println("Exception here:::"+e);
//                            	}
                            		 sql="SELECT cramt, " +
                            	"  dramt, " +
                            	"  ind, " +
                            	"  recpno , " +
                            	"  amt, " +
                            	"  doctype, " +
                            	"  type1, " +
                            	"  date1, " +
                            	"  particul, " +
                            	"  acc.ACCOUNT_HEAD_DESC, " +
                            	"  TO_CHAR(glt.month_opening_balance) AS monthopen, " +
                            	"  glt.MONTH_OPENING_BAL_DR_CR_IND    AS monthind , " +
                            	"  TO_CHAR(glt.month_closing_balance) AS monthclose, " +
                            	"  glt.MONTH_CLOSING_BAL_DR_CR_IND    AS monthind_close, " +
                            	"  accUint.ACCOUNTING_UNIT_NAME       AS acc_unit_name, " +
                            	"  Office.OFFICE_NAME                 AS off_name, " +
                            	"  sl_type_code , " +
                            	"  (SELECT sub_ledger_type_desc " +
                            	"  FROM com_mst_sl_types " +
                            	"  WHERE sub_ledger_type_code = sl_type_code " +
                            	"  ) AS sl_type_code_desc, " +
                            	"  sl_code, " +
                            	//"  -- Supplier 1 " +
                            	"  CASE " +
                            	"    WHEN sl_type_code = 1 " +
                            	"    THEN " +
                            	"      (SELECT SUPPLIER_NAME " +
                            	"      FROM COM_SUPPLIER_SL_MST " +
                            	"      WHERE ACCOUNTING_UNIT_ID    ='"+accountingunit+"' " +
                            	"      AND ACCOUNTING_FOR_OFFICE_ID='"+accountingoffice+"' " +
                            	"      AND supplier_id             = sl_code " +
                            	"      ) " +
                            	"    ELSE ( "+
                            	//--- Firms 2 " +
                            	"      CASE " +
                            	"        WHEN sl_type_code=2 " +
                            	"        THEN " +
                            	"          (SELECT FIRMS_NAME " +
                            	"          FROM COM_FIRMS_SL_MST " +
                            	"          WHERE ACCOUNTING_UNIT_ID    ='"+accountingunit+"' " +
                            	"          AND ACCOUNTING_FOR_OFFICE_ID='"+accountingoffice+"' " +
                            	"          AND firms_id                =sl_code " +
                            	"          ) " +
                            	"        ELSE ( "+
                            	//--- Assests 3 " +
                            	"          CASE " +
                            	"            WHEN sl_type_code=3 " +
                            	"            THEN " +
                            	"              (SELECT ASSET_DESCRIPTION " +
                            	"              FROM COM_MST_ASSETS_SL " +
                            	"              WHERE ACCOUNTING_UNIT_ID    ='"+accountingunit+"' " +
                            	"              AND ACCOUNTING_FOR_OFFICE_ID='"+accountingoffice+"' " +
                            	"              AND asset_code              = sl_code " +
                            	"              ) " +
                            	"            ELSE ( "+
                            	//--- Offices 5 " +
                            	"              CASE " +
                            	"                WHEN sl_type_code = 5 " +
                            	"                THEN " +
                            	"                  (SELECT OFFICE_NAME " +
                            	"                  FROM COM_MST_OFFICES " +
                            	"                  WHERE OFFICE_ID=sl_code " +
                            	"                  ) " +
                            	"                ELSE ( "+
                            	//-- Bank 6 " +
                            	"                  CASE " +
                            	"                    WHEN sl_type_code = 6 " +
                            	"                    THEN " +
                            	"                      (SELECT TO_CHAR(bank_ac_no) " +
                            	"                      FROM fas_mst_bank_balance " +
                            	"                      WHERE ACCOUNTING_UNIT_ID  ='"+accountingunit+"' " +
                            	"                      AND BANK_AC_NO_ALIAS_CODE = sl_code " +
                            	"                      ) " +
                            	"                    ELSE ( "+
                            	//-- Employees 7 " +
                            	"                      CASE " +
                            	"                        WHEN sl_type_code= 7 " +
                            	"                        THEN " +
                            	"                          (SELECT e.EMPLOYEE_NAME " +
                            	"                            ||'.' " +
                            	"                            ||e.EMPLOYEE_INITIAL " +
                            	"                            ||'-' " +
                            	"                            || d.DESIGNATION AS ENAME " +
                            	"                          FROM HRM_MST_EMPLOYEES e, " +
                            	"                            HRM_EMP_CURRENT_POSTING c, " +
                            	"                            HRM_MST_DESIGNATIONS d " +
                            	"                          WHERE c.DESIGNATION_ID=d.DESIGNATION_ID " +
                            	"                          AND c.EMPLOYEE_ID     =e.EMPLOYEE_ID " +
                            	"                          AND c.OFFICE_ID       ='"+accountingoffice+"' " +
                            	"                          AND e.EMPLOYEE_ID     =sl_code " +
                            	"                          ) " +
                            	"                        ELSE ( " +
                            	//"                          -- OTHER DEPARTMENTS " +
                            	"                          CASE " +
                            	"                            WHEN sl_type_code= 9 " +
                            	"                            THEN " +
                            	"                              (SELECT dep.OTHER_DEPT_NAME " +
                            	"                                || '-' " +
                            	"                                || off.OTHER_DEPT_OFFICE_NAME AS OTHER_DEPT_OFF_NAME " +
                            	"                              FROM HRM_MST_OTHER_DEPTS dep, " +
                            	"                                HRM_MST_OTHER_DEPT_OFFICES OFF " +
                            	"                              WHERE dep.OTHER_DEPT_ID           =off.OTHER_DEPT_ID " +
                            	"                              AND off.other_dept_office_alias_id= sl_code " +
                            	"                              ) " +
                            	"                            ELSE ( " +
                            	//"                              -- PROJECT " +
                            	"                              CASE " +
                            	"                                WHEN sl_type_code=10 " +
                            	"                                THEN " +
                            	"                                  (SELECT PROJECT_NAME " +
                            	"                                  FROM PMS_MST_PROJECTS_VIEW " +
                            	"                                  WHERE OFFICE_ID='"+accountingoffice+"' " +
                            	"                                  AND PROJECT_ID = sl_code " +
                            	"                                  ) " +
                            	"                                ELSE ( " +
                            	//"                                  -- CONTRACTORS " +
                            	"                                  CASE " +
                            	"                                    WHEN sl_type_code=11 " +
                            	"                                    THEN " +
                            	"                                      (SELECT CONTRACTOR_NAME " +
                            	"                                      FROM PMS_MST_CONTRACTORS_VIEW " +
                            	"                                      WHERE (OFFICE_ID='"+accountingoffice+"' " +
                            	"                                      OR OFFICE_ID   IN " +
                            	"                                        (SELECT REGION_OFFICE_ID " +
                            	"                                        FROM COM_MST_ALL_OFFICES_VIEW " +
                            	"                                        WHERE office_id='"+accountingoffice+"' " +
                            	"                                        UNION ALL " +
                            	"                                        SELECT CIRCLE_OFFICE_ID " +
                            	"                                        FROM COM_MST_ALL_OFFICES_VIEW " +
                            	"                                        WHERE office_id='"+accountingoffice+"' " +
                            	"                                        )) " +
                            	"                                      AND CONTRACTOR_ID= sl_code " +
                            	"                                      ) " +
                            	"                                  END ) " +
                            	"                              END ) " +
                            	"                          END ) " +
                            	"                      END ) " +
                            	"                  END ) " +
                            	"              END ) " +
                            	"          END ) " +
                            	"      END ) " +
                            	"  END AS sl_code_name " +
                            	"FROM " +
                            	"  ( " +
                            //	"  -- 1. Receipt Master and Transaction , CR Heads " +
                            	"  SELECT a.account_head_code AS acchead, " +
                            	"    a.cr_dr_indicator        AS ind, " +
                            	"    a.receipt_no             AS recpno , " +
                            	"    TO_CHAR(a.amount)        AS amt, " +
                            	"    a.amount                 AS cramt, " +
                            	"    0                        AS dramt, " +
                            	"    'R'                      AS doctype, " +
                            	"    NULL                     AS type1, " +
                            	"    b.receipt_date           AS date1, " +
                            	"    a.particulars            AS particul, " +
                            	"    a.SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                            	"    a.SUB_LEDGER_CODE        AS sl_code " +
                            	"  FROM fas_receipt_transaction a, " +
                            	"    fas_receipt_master b " +
                            	"  WHERE a.accounting_unit_id     =b.accounting_unit_id " +
                            	"  AND a.cashbook_year            =b.cashbook_year " +
                            	"  AND a.cashbook_month           =b.cashbook_month " +
                            	"  AND a.receipt_no               =b.receipt_no " +
                            	"  AND a.accounting_for_office_id =b.ACCOUNTING_FOR_OFFICE_ID " +
                            	"  AND b.receipt_status          <>'C' " +
                            	"  AND a.accounting_unit_id       ='"+accountingunit+"' " +
                            	"  AND a.accounting_for_office_id ='"+accountingoffice+"' " +
                            	"  AND a.cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                            	"  AND a.cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                            	"  AND a.cr_dr_indicator  ='CR' " +
                            	"  AND a.account_head_code= ($P{accountheadcode}) " +
                            	"  UNION ALL " +
                            	//"  -- 2. Journal Master and Transaction , CR Heads , GJV and LJV " +
                            	"  SELECT a.account_head_code AS acchead, " +
                            	"    a.cr_dr_indicator        AS ind, " +
                            	"    a.voucher_no             AS recpno, " +
                            	"    TO_CHAR(a.amount)        AS amt, " +
                            	"    a.amount                 AS cramt, " +
                            	"    0                        AS dramt, " +
                            	"    'J'                      AS doctype, " +
                            	"    b.journal_type_code      AS type1, " +
                            	"    b.voucher_date           AS date1, " +
                            	"    a.particulars            AS particul, " +
                            	"    a.SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                            	"    a.SUB_LEDGER_CODE        AS sl_code " +
                            	"  FROM fas_journal_transaction a, " +
                            	"    fas_journal_master b " +
                            	"  WHERE a.accounting_unit_id     =b.accounting_unit_id " +
                            	"  AND a.cashbook_year            =b.cashbook_year " +
                            	"  AND a.cashbook_month           =b.cashbook_month " +
                            	"  AND a.voucher_no               =b.voucher_no " +
                            	"  AND a.accounting_for_office_id =b.ACCOUNTING_FOR_OFFICE_ID " +
                            	"  AND b.journal_status          <>'C' " +
                            	"  AND a.accounting_unit_id       ='"+accountingunit+"' " +
                            	"  AND a.accounting_for_office_id ='"+accountingoffice+"' " +
                            	"  AND a.cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                            	"  AND a.cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                            	"  AND a.account_head_code  ='"+accountheadcode+"' " +
                            	"  AND a.cr_dr_indicator    ='CR' " +
                            	"  AND b.created_by_module IN ('GJV','LJV') " +
                            	"  UNION ALL " +
                            	//"  -- 3. Journal Master and Transaction, DR Heads , GJV and LJV " +
                            	"  SELECT a.account_head_code AS acchead, " +
                            	"    a.cr_dr_indicator        AS ind, " +
                            	"    a.voucher_no             AS recpno, " +
                            	"    TO_CHAR(a.amount)        AS amt, " +
                            	"    0                        AS cramt, " +
                            	"    a.amount                 AS dramt, " +
                            	"    'J'                      AS doctype, " +
                            	"    b.journal_type_code      AS type1, " +
                            	"    b.voucher_date           AS date1, " +
                            	"    a.particulars            AS particul, " +
                            	"    a.SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                            	"    a.SUB_LEDGER_CODE        AS sl_code " +
                            	"  FROM fas_journal_transaction a, " +
                            	"    fas_journal_master b " +
                            	"  WHERE a.accounting_unit_id     =b.accounting_unit_id " +
                            	"  AND a.cashbook_year            =b.cashbook_year " +
                            	"  AND a.cashbook_month           =b.cashbook_month " +
                            	"  AND a.voucher_no               =b.voucher_no " +
                            	"  AND a.accounting_for_office_id =b.ACCOUNTING_FOR_OFFICE_ID " +
                            	"  AND b.journal_status          <>'C' " +
                            	"  AND a.accounting_unit_id       ='"+accountingunit+"' " +
                            	"  AND a.accounting_for_office_id ='"+accountingoffice+"' " +
                            	"  AND a.cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                            	"  AND a.cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                            	"  AND a.account_head_code  ='"+accountheadcode+"' " +
                            	"  AND a.cr_dr_indicator    ='DR' " +
                            	"  AND b.created_by_module IN ('GJV','LJV') " +
                            	"  UNION ALL " +
                            	//"  -- 4. Payment Master and Transaction , DR Heads " +
                            	"  SELECT a.account_head_code AS acchead, " +
                            	"    a.cr_dr_indicator        AS ind, " +
                            	"    a.voucher_no             AS recpno, " +
                            	"    TO_CHAR(a.amount)        AS amt, " +
                            /*   
                             * joan change on 08 OCt2014
                             * 
                             * 	"    0                        AS cramt, " +
                            	"    a.amount                 AS dramt, " +*/
                            	 " case when  a.cr_dr_indicator ='CR' then   a.amount else 0  end as cramt, "+
                                 " case when  a.cr_dr_indicator ='DR' then   a.amount else 0  end as dramt, "+
                            	"    'P'                      AS doctype, " +
                            	"    NULL                     AS type1, " +
                            	"    b.payment_date           AS date1, " +
                            	"    a.particulars            AS particul, " +
                            	"    a.SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                            	"    a.SUB_LEDGER_CODE        AS sl_code " +
                            	"  FROM fas_payment_transaction a, " +
                            	"    fas_payment_master b " +
                            	"  WHERE a.accounting_unit_id     =b.accounting_unit_id " +
                            	"  AND a.cashbook_year            =b.cashbook_year " +
                            	"  AND a.cashbook_month           =b.cashbook_month " +
                            	"  AND a.voucher_no               =b.voucher_no " +
                            	"  AND a.accounting_for_office_id =b.ACCOUNTING_FOR_OFFICE_ID " +
                            	"  AND b.payment_status          <>'C' " +
                            	"  AND a.accounting_unit_id       ='"+accountingunit+"' " +
                            	"  AND a.accounting_for_office_id ='"+accountingoffice+"' " +
                            	"  AND a.cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                            	"  AND a.cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                            	"  AND a.cr_dr_indicator   ='DR' " +
                            	"  AND a.account_head_code ='"+accountheadcode+"' " +
                            	"  UNION ALL " +
                            	//"  -- 5. Recipt Master , DR Heads " +
                            	"  SELECT account_head_code AS acchead, " +
                            	"    cr_dr_indicator        AS ind, " +
                            	"    receipt_no             AS recpno, " +
                            	"    TO_CHAR(TOTAL_AMOUNT)  AS amt, " +
                            	"    0                      AS cramt, " +
                            	"    TOTAL_AMOUNT           AS dramt, " +
                            	"    'R'                    AS doctype, " +
                            	"    NULL                   AS type1, " +
                            	"    receipt_date           AS date1, " +
                            	"    remarks                AS particul , " +
                            	"    SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                            	"    SUB_LEDGER_CODE        AS sl_code " +
                            	"  FROM fas_receipt_master " +
                            	"  WHERE receipt_status        <>'C' " +
                            	"  AND accounting_unit_id       ='"+accountingunit+"' " +
                            	"  AND accounting_for_office_id ='"+accountingoffice+"' " +
                            	"  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                            	"  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                            	"  AND account_head_code ='"+accountheadcode+"' " +
                            	"  AND cr_dr_indicator   ='DR' " +
                            	"  UNION ALL " +
                            	//"  -- 6. Payment Master , CR Heads " +
                            	"  SELECT account_head_code AS acchead, " +
                            	"    cr_dr_indicator        AS ind, " +
                            	"    voucher_no             AS recpno, " +
                            	"    TO_CHAR(TOTAL_AMOUNT)  AS amt, " +
                            	"    TOTAL_AMOUNT           AS cramt, " +
                            	"    0                      AS dramt, " +
                            	"    'P'                    AS doctype, " +
                            	"    NULL                   AS type1, " +
                            	"    PAYMENT_DATE           AS date1, " +
                            	"    remarks                AS particul, " +
                            	"    SUB_LEDGER_TYPE_CODE   AS sl_type_code, " +
                            	"    SUB_LEDGER_CODE        AS sl_code " +
                            	"  FROM fas_payment_master " +
                            	"  WHERE payment_status        <>'C' " +
                            	"  AND accounting_unit_id       ='"+accountingunit+"' " +
                            	"  AND accounting_for_office_id ='"+accountingoffice+"' " +
                            	"  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                            	"  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                            	"  AND account_head_code ='"+accountheadcode+"' " +
                            	"  AND cr_dr_indicator   ='CR' " +
                            	"  UNION ALL " +
//                            	"  -- 7. Fund Receipt By HO , CR Heads " +
//                            	"  /* select " +
//                            	"  cr_account_head_code as acchead, " +
//                            	"  'CR' as ind, " +
//                            	"  receipt_no as recpno, " +
//                            	"  to_char(TOTAL_AMOUNT) as amt, " +
//                            	"  total_amount as cramt, " +
//                            	"  0 as dramt, " +
//                            	"  'FR' as doctype, " +
//                            	"  null as type1, " +
//                            	"  receipt_date as date1, " +
//                            	"  PARTICULARS as particul " +
//                            	"  from " +
//                            	"  fas_fund_receipt_by_ho " +
//                            	"  where " +
//                            	"  receipt_status<>'C' " +
//                            	"  and accounting_unit_id =189 " +
//                            	"  and accounting_for_office_id =5086 " +
//                            	"  and cashbook_year between 2008  and 2008 " +
//                            	"  and cashbook_month between 0 and 0 " +
//                            	"  and cr_account_head_code =100101 " +
//                            	"  union all " +
//                            	"  -- 8. Fund Receipt By Ho , DR Heads " +
//                            	"  select " +
//                            	"  dr_account_head_code as acchead, " +
//                            	"  'DR' as ind, " +
//                            	"  receipt_no as recpno, " +
//                            	"  to_char(TOTAL_AMOUNT) as amt, " +
//                            	"  0 as cramt, " +
//                            	"  total_amount as dramt, " +
//                            	"  'FR' as doctype, " +
//                            	"  null as type1, " +
//                            	"  receipt_date as date1, " +
//                            	"  PARTICULARS as particul " +
//                            	"  from " +
//                            	"  fas_fund_receipt_by_ho " +
//                            	"  where " +
//                            	"  receipt_status<>'C' " +
//                            	"  and accounting_unit_id =189 " +
//                            	"  and accounting_for_office_id =5086 " +
//                            	"  and cashbook_year between 2008  and 2008 " +
//                            	"  and cashbook_month between 0 and 0 " +
//                            	"  and dr_account_head_code =100101 " +
//                            	"  union  all " +
//                            	"  */ " +
//                            	"  -- 9. Fund Transfer from HO master and Transaciton , CR heads " +
//                            	"  SELECT a.account_head_code AS acchead, " +
//                            	"    a.cr_dr_indicator        AS ind, " +
//                            	"    a.voucher_no             AS recpno , " +
//                            	"    TO_CHAR(a.amount)        AS amt, " +
//                            	"    a.amount                 AS cramt, " +
//                            	"    0                        AS dramt, " +
//                            	"    'FT'                     AS doctype, " +
//                            	"    NULL                     AS type1, " +
//                            	"    b.DATE_OF_TRANSFER       AS date1, " +
//                            	"    a.particulars            AS particul, " +
//                            	"    0                        AS sl_type_code, " +
//                            	"    0                        AS sl_code " +
//                            	"  FROM fas_fund_trf_from_ho_trn a, " +
//                            	"    fas_fund_trf_from_ho_master b " +
//                            	"  WHERE a.accounting_unit_id     =b.accounting_unit_id " +
//                            	"  AND a.cashbook_year            =b.cashbook_year " +
//                            	"  AND a.cashbook_month           =b.cashbook_month " +
//                            	"  AND a.voucher_no               =b.voucher_no " +
//                            	"  AND a.accounting_for_office_id =b.ACCOUNTING_FOR_OFFICE_ID " +
//                            	"  AND b.TRANSFER_STATUS         <>'C' " +
//                            	"  AND a.accounting_unit_id       ='"+accountingunit+"' " +
//                            	"  AND a.accounting_for_office_id ='"+accountingoffice+"' " +
//                            	"  AND a.cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
//                            	"  AND a.cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
//                            	"  AND a.account_head_code ='"+accountheadcode+"' " +
//                            	"  AND a.cr_dr_indicator   ='CR' " +
//                            	"  UNION ALL " +
//                            	"  -- 10. Fund Transfer from HO Master " +
//                            	"  SELECT account_head_code AS acchead, " +
//                            	"    cr_dr_indicator        AS ind, " +
//                            	"    voucher_no             AS recpno, " +
//                            	"    TO_CHAR(TOTAL_AMOUNT)  AS amt, " +
//                            	"    0                      AS cramt, " +
//                            	"    TOTAL_AMOUNT           AS dramt, " +
//                            	"    'FT'                   AS doctype, " +
//                            	"    NULL                   AS type1, " +
//                            	"    DATE_OF_TRANSFER       AS date1, " +
//                            	"    remarks                AS particul, " +
//                            	"    0                      AS sl_type_code, " +
//                            	"    0                      AS sl_code " +
//                            	"  FROM fas_fund_trf_from_ho_master " +
//                            	"  WHERE transfer_status       <>'C' " +
//                            	"  AND accounting_unit_id       ='"+accountingunit+"' " +
//                            	"  AND accounting_for_office_id ='"+accountingoffice+"' " +
//                            	"  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
//                            	"  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
//                            	"  AND account_head_code ='"+accountheadcode+"' " +
//                            	"  AND cr_dr_indicator   ='DR' " +
//"  --  9. Fund Transfer from office , CR Heads " +
"  SELECT cr_account_head_code AS acchead, " +
"    'CR'                      AS ind, " +
"    voucher_no                AS recpno, " +
"    TO_CHAR(TOTAL_AMOUNT)     AS amt, " +
"    total_amount              AS cramt, " +
"    0                         AS dramt, " +
"    'FT'                      AS doctype, " +
"    NULL                      AS type1, " +
"    DATE_OF_TRANSFER          AS date1, " +
"    PARTICULARS               AS particul, " +
"    0                         AS sl_type_code, " +
"    0                         AS sl_code " +
"  FROM FAS_FUND_TRF_FROM_OFFICE " +
"  WHERE transfer_status       <>'C' " +
"  AND accounting_unit_id       = '"+accountingunit+"' " +
"  AND accounting_for_office_id = '"+accountingoffice+"' " +
"  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
"  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
"  AND cr_account_head_code ='"+accountheadcode+"' " +
"  UNION ALL " +
//"  -- 10. Fund Transfer from Office , DR Heads " +
"  SELECT dr_account_head_code AS acchead, " +
"    'DR'                      AS ind, " +
"    voucher_no                AS recpno, " +
"    TO_CHAR(TOTAL_AMOUNT)     AS amt, " +
"    0                         AS cramt, " +
"    total_amount              AS dramt, " +
"    'FT'                      AS doctype, " +
"    NULL                      AS type1, " +
"    DATE_OF_TRANSFER          AS date1, " +
"    PARTICULARS               AS particul, " +
"    0                         AS sl_type_code, " +
"    0                         AS sl_code " +
"  FROM FAS_FUND_TRF_FROM_OFFICE " +
"  WHERE transfer_status       <>'C' " +
"  AND accounting_unit_id       = '"+accountingunit+"' " +
"  AND accounting_for_office_id = '"+accountingoffice+"' " +
"  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
"  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
"  AND dr_account_head_code ='"+accountheadcode+"' " +
                            	
                            	
                            	"  UNION ALL " +
  //                          	"  -- 11. Fund Receipt by Office , CR heads " +
                            	"  SELECT cr_account_head_code AS acchead, " +
                            	"    'CR'                      AS ind, " +
                            	"    receipt_no                AS recpno, " +
                            	"    TO_CHAR(TOTAL_AMOUNT)     AS amt, " +
                            	"    total_amount              AS cramt, " +
                            	"    0                         AS dramt, " +
                            	"    'FR'                      AS doctype, " +
                            	"    NULL                      AS type1, " +
                            	"    receipt_date              AS date1, " +
                            	"    PARTICULARS               AS particul , " +
                            	"    0                         AS sl_type_code, " +
                            	"    0                         AS sl_code " +
                            	"  FROM FAS_FUND_RECEIPT_BY_OFFICE " +
                            	"  WHERE receipt_status        <>'C' " +
                            	"  AND accounting_unit_id       ='"+accountingunit+"' " +
                            	"  AND accounting_for_office_id ='"+accountingoffice+"' " +
                            	"  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                            	"  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                            	"  AND cr_account_head_code ='"+accountheadcode+"' " +
                            	"  UNION ALL " +
 //                           	"  --12.  Fund Receipt By Office , DR Heads " +
                            	"  SELECT dr_account_head_code AS acchead, " +
                            	"    'DR'                      AS ind, " +
                            	"    receipt_no                AS recpno, " +
                            	"    TO_CHAR(TOTAL_AMOUNT)     AS amt, " +
                            	"    0                         AS cramt, " +
                            	"    total_amount              AS dramt, " +
                            	"    'FR'                      AS doctype, " +
                            	"    NULL                      AS type1, " +
                            	"    receipt_date              AS date1, " +
                            	"    PARTICULARS               AS particul , " +
                            	"    0                         AS sl_type_code, " +
                            	"    0                         AS sl_code " +
                            	"  FROM FAS_FUND_RECEIPT_BY_OFFICE " +
                            	"  WHERE receipt_status        <>'C' " +
                            	"  AND accounting_unit_id       ='"+accountingunit+"' " +
                            	"  AND accounting_for_office_id ='"+accountingoffice+"' " +
                            	"  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                            	"  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                            	"  AND dr_account_head_code ='"+accountheadcode+"' " +
                            	"  UNION ALL " +
//                            	"  -- 13. IBT , CR heads " +
                            	"  SELECT cr_account_head_code AS acchead, " +
                            	"    'CR' ind, " +
                            	"    voucher_no            AS recpno, " +
                            	"    TO_CHAR(total_amount) AS amt, " +
                            	"    total_amount          AS cramt, " +
                            	"    0                     AS dramt, " +
                            	"    'IBT'                 AS doctype, " +
                            	"    NULL                  AS type1, " +
                            	"    date_of_transfer      AS date1, " +
                            	"    particulars           AS particul, " +
                            	"    0                     AS sl_type_code, " +
                            	"    0                     AS sl_code " +
                            	"  FROM fas_inter_bank_trf_at_ho " +
                            	"  WHERE transfer_status      <>'C' " +
                            	"  AND accounting_unit_id      = '"+accountingunit+"' " +
                            	"  AND accounting_for_office_id= '"+accountingoffice+"' " +
                            	"  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                            	"  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                            	"  AND cr_account_head_code ='"+accountheadcode+"' " +
                            	"  UNION ALL " +
  //                          	"  -- 14. IBT DR Heads " +
                            	"  SELECT dr_account_head_code AS acchead, " +
                            	"    'DR' ind, " +
                            	"    voucher_no            AS recpno, " +
                            	"    TO_CHAR(total_amount) AS amt, " +
                            	"    0                     AS cramt, " +
                            	"    total_amount          AS dramt, " +
                            	"    'IBT'                 AS doctype, " +
                            	"    NULL                  AS type1, " +
                            	"    date_of_transfer      AS date1, " +
                            	"    particulars           AS particul , " +
                            	"    0                     AS sl_type_code, " +
                            	"    0                     AS sl_code " +
                            	"  FROM fas_inter_bank_trf_at_ho " +
                            	"  WHERE transfer_status      <>'C' " +
                            	"  AND accounting_unit_id      = '"+accountingunit+"' " +
                            	"  AND accounting_for_office_id= '"+accountingoffice+"' " +
                            	"  AND cashbook_year BETWEEN ('"+year+"') AND ('"+year+"') " +
                            	"  AND cashbook_month BETWEEN ('"+month+"') AND ('"+month+"') " +
                            	"  AND dr_account_head_code ='"+accountheadcode+"' " +
                            	"  ), " +
                            	"  com_mst_account_heads acc, " +
                            	"  fas_general_ledger glt , " +
                            	"  COM_MST_OFFICES Office, " +
                            	"  FAS_MST_ACCT_UNITS accUint " +
                            	"WHERE acc.account_head_code      =acchead " +
                            	"AND acchead                      =glt.account_head_code " +
                            	"AND glt.accounting_unit_id       ='"+accountingunit+"' " +
                            	"AND glt.accounting_for_office_id ='"+accountingoffice+"' " +
                            	"AND glt.year BETWEEN ('"+year+"') AND ('"+year+"') " +
                            	"AND glt.month BETWEEN ('"+month+"') AND ('"+month+"') " +
                            	"AND accUint.ACCOUNTING_UNIT_ID = '"+accountingunit+"' " +
                            	"AND Office.OFFICE_ID           = '"+accountingoffice+"' " +
                            	"ORDER BY date1, " +
                            	"  doctype, " +
                            	"  recpno";
                            	 System.out.println("testing%%%%%%%%%%%%%%%%%"+sql);
                            
                          }
                                 
                     }
              
                
            if (!reportFile.exists())
            throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
            System.out.println("opt::" + rtype);
          
            map.put("accountingunitid",accountingunit);
            map.put("accountofficeid",accountingoffice);
            map.put("accountheadcode",accountheadcode);
            map.put("cashbookmonth",month);
            map.put("cashbookyear",year);
            map.put("monthvalue",monthInWords);
            
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);
            
            
            try{
            
            if (rtype.equalsIgnoreCase("HTML"))   
            {
                        response.setContentType("text/html");
                        response.setHeader ("Content-Disposition", "attachment;filename=\"GeneralLedgerReport.html\"");
                        PrintWriter out = response.getWriter();
                        JRHtmlExporter exporter = new JRHtmlExporter();
                        // File f=new File(getServletContext().getRealPath("/WEB-INF/Report/"));
                        //  exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,true);
                        //  exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,getServletContext().getRealPath("/WEB-INF/Report/"));
                        //  exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR,f);
                        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
                        exporter.exportReport();
                         out.flush();
                        out.close();
            }
           else if (rtype.equalsIgnoreCase("PDF"))   
            {
                        byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
                        response.setContentType("application/pdf");
                        response.setContentLength(buf.length);
                       // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                       //response.setContentType("application/force-download");
                    
                        System.out.println("hellllllllllllooooooooooo");
                        response.setHeader ("Content-Disposition", "attachment;filename=\"GeneralLedgerReport.pdf\"");
                        OutputStream out = response.getOutputStream();
                        out.write(buf, 0, buf.length);
                        out.close();
            }
           else if (rtype.equalsIgnoreCase("EXCEL"))   
            {
   System.out.println("Inside Excel*****");
//                     response.setContentType("application/vnd.ms-excel");
//                     response.setHeader ("Content-Disposition", "attachment;filename=\"GeneralLedgerReport.csv\"");
//                     ExcelConverter excel = new ExcelConverter();
//          		   	//String path = getServletContext().getRealPath("/WEB-INF/Excel/SubLedgerReport.csv");
//          		   	String path = "c:\\GeneralLedgerReport.csv";
//          		   	excel.writeInCsvFormat(sql,path);
        	   response.setContentType("application/vnd.ms-excel");
        	   System.out.println("1");
               response.setHeader("Content-Disposition",
                                  "attachment;filename=\"Receipt.xls\"");
               System.out.println("2");
               JRXlsExporter exporterXLS = new JRXlsExporter();
               System.out.println("1");
               exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                                        jasperPrint);
               System.out.println("3");
               ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
               System.out.println("4");
               exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
                                        xlsReport);
               System.out.println("5");
               exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
                                        Boolean.FALSE);
               System.out.println("6");
               exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,
                                        Boolean.TRUE);
               System.out.println("7");
               exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
                                        Boolean.FALSE);
               System.out.println("8");
               exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                                        Boolean.TRUE);
               System.out.println("9");
               exporterXLS.exportReport();
               System.out.println("10");
               byte[] bytes;
               System.out.println("11");
               bytes = xlsReport.toByteArray();
               System.out.println("12");
               ServletOutputStream ouputStream = response.getOutputStream();
               System.out.println("13");
               ouputStream.write(bytes, 0, bytes.length);
               System.out.println("14");
               ouputStream.flush();
               System.out.println("15");
               ouputStream.close();
               System.out.println("16");

            }
            else if (rtype.equalsIgnoreCase("TXT"))   
            {
            
                    response.setContentType("text/plain");
                    response.setHeader ("Content-Disposition", "attachment;filename=\"OfficeDetail.txt\"");
                     
                JRTextExporter exporter = new JRTextExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,txtReport); 
                exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Integer(200));
                exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Integer(50));
                exporter.exportReport(); 
                
                     byte []bytes;
                    bytes = txtReport.toByteArray();
                    ServletOutputStream ouputStream = response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();

            }
                }   catch(Exception e){
                	System.out.println(e);
                }
                }
      
        } catch (Exception ex) {
            String connectMsg = 
                "Could not create the report******** in GeneralLedgerReport.java" + ex.getMessage() + " " + 
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }

    }
}

