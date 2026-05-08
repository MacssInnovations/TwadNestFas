/**
 *            All Months + Single A/C Head 
 */

package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.awt.print.PrinterJob;

import java.io.File;
import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import java.util.*;

import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.JasperReport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;

import java.text.SimpleDateFormat;

import javax.print.DocFlavor;

import javax.print.PrintService;

import net.sf.jasperreports.engine.JasperPrintManager;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import javax.print.attribute.PrintServiceAttributeSet;

import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;

import Servlets.FAS.FAS1.CommonClass.ExcelConverter;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;

import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


public class GeneralLedgerReport_one extends HttpServlet {
    private static final String CONTENT_TYPE ="text/html; charset=windows-1252";
   

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,  HttpServletResponse response) throws ServletException, IOException 
    {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
      
    	System.out.println("###########GeneralLedgerReport_one##############");
    	
    	/**
       *  Session Checking 
       */
       String sql="";
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
        
        Connection con=null;
        PreparedStatement ps1=null;
        PreparedStatement ps2=null;
        PreparedStatement ps3=null;
        PreparedStatement ps4=null;
        PreparedStatement ps5=null;
        
        ResultSet rs2=null;
        ResultSet rs4=null;
        String alloffice="";
        
        response.setContentType(CONTENT_TYPE);
      
        
        /**
         * Retrieving Configuration Values 
         */
        
        try {


            ResourceBundle rs =ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";
            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs.getString("Config.DSN");
            String strhostname = rs.getString("Config.HOST_NAME");
            String strportno = rs.getString("Config.PORT_NUMBER");
            String strsid = rs.getString("Config.SID");
            String strdbusername = rs.getString("Config.USER_NAME");
            String strdbpassword = rs.getString("Config.PASSWORD");
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection            Class.forName(strDriver.trim());
            con =DriverManager.getConnection(ConnectionString, strdbusername.trim(), strdbpassword.trim());
            
        }
        catch (Exception ex) 
         {
            String connectMsg = "Could not create the connection" + ex.getMessage() + " " + ex.getLocalizedMessage();
            System.out.println(connectMsg);
         }
        
        
         
       /**
        *  Getting Parameters 
        */
        
        JasperDesign jasperDesign = null;
        File reportFile=null;
        
        try {
                System.out.println("calling servlet GeneralLedgerReport_one **** 23APR2012*****");
                
                
                /** Get From Cash Book Month and Year */
                String txtCB_Year_from=request.getParameter("txtCB_Year_from");
                String txtCB_Month_from=request.getParameter("txtCB_Month_from");
                
                /** Get To Cash Book Month and Year */
                String txtCB_Year_to=request.getParameter("txtCB_Year_to");
                String txtCB_Month_to=request.getParameter("txtCB_Month_to");
                
                /** Find whether to display all or specific account heads */
                String Specifictype=request.getParameter("SpecificAHC");
                
                /** Find Whether report should be either html or text or pdf */
                String rtype= request.getParameter("txtoption");
                
                /** Get Accounting Unit Id */
                String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
                
                /** Get Accounting Office id */
                String cmbOffice_code=request.getParameter("cmbOffice_code");
                
                /** Get Account Head */ 
                String cmbAccHeadCode=request.getParameter("txtAcc_HeadCode");
                
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
                
                
                System.out.println("cmbAccHeadCode is:"+cmbAccHeadCode);
                System.out.println("accounting unit id is:"+cmbAcc_UnitCode);
                System.out.println("office code is:"+cmbOffice_code);
                System.out.println("Specific Type Value is:"+Specifictype);
                System.out.println("alloffice:"+alloffice); 
                
                               
            /** Convert Acciunting Unid Id and Office Id from String to Integer */ 
             int accountingunit=Integer.parseInt(cmbAcc_UnitCode);
             int accountingoffice=Integer.parseInt(cmbOffice_code);
             int accountheadcode=0;
             
             
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
             
            
            /** Convert From Cash Book Month and Year from String to Integer */    
            int year_from=Integer.parseInt(txtCB_Year_from);
            int month_from=Integer.parseInt(txtCB_Month_from);
            
            /** Convert To Cash Book Month and Year from String to Integer */
             int year_to=Integer.parseInt(txtCB_Year_to);
             int month_to=Integer.parseInt(txtCB_Month_to);
             
       
                       
            /** Convert months in numbers to words for from cash book month */
            String monthInWords_from="";
            if(month_from==1)
            monthInWords_from="January";
            else if(month_from==2)
            monthInWords_from="February";
            else if(month_from==3)
            monthInWords_from="March";
            else if(month_from==4)
            monthInWords_from="April";
            else if(month_from==5)
            monthInWords_from="May";
            else if(month_from==6)
            monthInWords_from="June";
            else if(month_from==7)
            monthInWords_from="July";
            else if(month_from==8)
            monthInWords_from="August";
            else if(month_from==9)
            monthInWords_from="September";
            else if(month_from==10)
            monthInWords_from="October";
            else if(month_from==11)
            monthInWords_from="November";
            else if(month_from==12)
            monthInWords_from="December"; 

            /** Convert months in numbers to words for To Cash Book Month */
            String monthInWords_to="";
            if(month_to==1)
            monthInWords_to="January";
            else if(month_to==2)
            monthInWords_to="February";
            else if(month_to==3)
            monthInWords_to="March";
            else if(month_to==4)
            monthInWords_to="April";
            else if(month_to==5)
            monthInWords_to="May";
            else if(month_to==6)
            monthInWords_to="June";
            else if(month_to==7)
            monthInWords_to="July";
            else if(month_to==8)
            monthInWords_to="August";
            else if(month_to==9)
            monthInWords_to="September";
            else if(month_to==10)
            monthInWords_to="October";
            else if(month_to==11)
            monthInWords_to="November";
            else if(month_to==12)
            monthInWords_to="December"; 

              
              
            /**
             *  Truncate the table fas_general_ledger_report_tmp before inserting data into it 
             */
              
              try {
                  
                  ps2=con.prepareStatement("truncate table FAS_GENERAL_LEDGER_REPORT_TMP");
                  ps2.executeUpdate();
              }
              catch (Exception e) {
                  System.out.println("Can not truncate table fas_general_ledger_report_tmp "+e);
              }
              
           
           
            CallableStatement cs=null,cs1=null;
            
            if(alloffice==null)
            { 
            	//cs1=con.prepareCall("{call FAS_GL_ALLMONTH_SINGLEACCHEADN(?,?,?,?,?,?,?)}");     
            	
            	cs1=con.prepareCall("call FAS_GL_ALLMONTH_SINGLEACCHEAD1(?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric)");             
            	cs1.setInt(1,accountingunit);
            	//cs1.setInt(2,accountingoffice);
            	cs1.setInt(2,year_from);
            	cs1.setInt(3,year_to);            
            	cs1.setInt(4,month_from);            
            	cs1.setInt(5,month_to);            
            	cs1.setInt(6,accountheadcode);            
                cs1.registerOutParameter(7,java.sql.Types.NUMERIC);
                cs1.setNull(7, java.sql.Types.NUMERIC);
                cs1.execute();
                //int errcode1=cs1.getInt(7);
                int errcode1 = cs1.getBigDecimal(7).intValue();
                
                System.out.println("General Ledger Error Code in FAS_GL_ALLMONTH_SINGLEACCHEAD1--->"+errcode1);   
            }
            else if(alloffice!=null)
            {
            
            //	cs=con.prepareCall("{call FAS_GL_ALLMONTH_SINGLEACCHEAD(?,?,?,?,?,?,?,?)}");
            cs=con.prepareCall("call FAS_GL_ALLMONTH_SINGLEACCHEAD2(?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric,?::numeric)");             
            cs.setInt(1,accountingunit);
            cs.setInt(2,accountingoffice);
            cs.setInt(3,year_from);
            cs.setInt(4,year_to);            
            cs.setInt(5,month_from);            
            cs.setInt(6,month_to);            
            cs.setInt(7,accountheadcode);            
            cs.registerOutParameter(8,java.sql.Types.NUMERIC);
            cs.setNull(8, java.sql.Types.NUMERIC);
            cs.execute();
            //int errcode=cs.getInt(8);
            int errcode = cs.getBigDecimal(8).intValue();
            
            System.out.println("General Ledger Error Code --->"+errcode);   
            
            }
              
              
            int i=0;
                    
            /**
             *  Insert FAS_General_Ledger Data into fas_general_ledger_report_tmp table 
             */
                 
           /**
            *  Insert Month in words in fas_general_ledger_report_tmp table 
            */
             
             /**
             * Report Calling 
             */
             
                   System.out.println("inside main sss");
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
                   System.out.println("sathya **** GeneralLedgerReport_one::::::::::::::"+accountheadcode);
                    if(Specifictype.equalsIgnoreCase("All"))         
                    {
                       System.out.println("all Head codes");
                       if(accountingunit==5)
                       {
                          reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GeneralLedger_UnitWiseModule/GeneralLedgerReportAll_banking_main.jasper"));
                          System.out.println("reportFile===>"+reportFile);
                          
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
                          "  AND a.cashbook_year BETWEEN ('"+year_from+"') AND ('"+year_to+"') " +
                          "  AND a.cashbook_month BETWEEN ('"+month_from+"') AND ('"+month_to+"') " +
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
                          "  AND a.cashbook_year BETWEEN ('"+year_from+"') AND ('"+year_to+"') " +
                          "  AND a.cashbook_month BETWEEN ('"+month_from+"') AND ('"+month_to+"') " +
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
                          "  AND a.cashbook_year BETWEEN ('"+year_from+"')AND ('"+year_to+"') " +
                          "  AND a.cashbook_month BETWEEN ('"+month_from+"') AND ('"+month_to+"') " +
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
                          "  AND a.cashbook_year BETWEEN ('"+year_from+"') AND ('"+year_to+"') " +
                          "  AND a.cashbook_month BETWEEN ('"+month_from+"') AND ('"+month_to+"') " +
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
                          "  AND cashbook_year BETWEEN ('"+year_from+"') AND ('"+year_to+"') " +
                          "  AND cashbook_month BETWEEN ('"+month_from+"') AND ('"+month_to+"') " +
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
                          "  AND cashbook_year BETWEEN ('"+year_from+"') AND ('"+year_to+"') " +
                          "  AND cashbook_month BETWEEN ('"+month_from+"') AND ('"+month_to+"') " +
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
                          "  AND a.cashbook_year BETWEEN ('"+year_from+"') AND ('"+year_to+"') " +
                          "  AND a.cashbook_month BETWEEN ('"+month_from+"') AND ('"+month_to+"') " +
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
                          "  AND cashbook_year BETWEEN ('"+year_from+"') AND ('"+year_to+"') " +
                          "  AND cashbook_month BETWEEN ('"+month_from+"') AND ('"+month_to+"') " +
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
                          "  AND a.cashbook_year BETWEEN ('"+year_from+"') AND ('"+year_to+"') " +
                          "  AND a.cashbook_month BETWEEN ('"+month_from+"') AND ('"+month_to+"') " +
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
                          "  AND cashbook_year BETWEEN ('"+year_from+"') AND ('"+year_to+"') " +
                          "  AND cashbook_month BETWEEN ('"+month_from+"') AND ('"+month_to+"') " +
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
                          "  AND cashbook_year BETWEEN ('"+year_from+"') AND ('"+year_to+"') " +
                          "  AND cashbook_month BETWEEN ('"+month_from+"') AND ('"+month_to+"') " +
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
                          "  AND cashbook_year BETWEEN ('"+year_from+"') AND ('"+year_to+"') " +
                          "  AND cashbook_month BETWEEN ('"+month_from+"') AND ('"+month_to+"') " +
                          "  ), " +
                          "  com_mst_account_heads acc, " +
                          "  fas_general_ledger glt, " +
                          "  COM_MST_OFFICES Office, " +
                          "  FAS_MST_ACCT_UNITS accUint " +
                          "WHERE acc.account_head_code     =acchead " +
                          "AND acchead                     =glt.account_head_code " +
                          "AND glt.accounting_unit_id      ='"+accountingunit+"' " +
                          "AND glt.accounting_for_office_id='"+accountingoffice+"' " +
                          "AND glt.year BETWEEN ('"+year_from+"') AND ('"+year_to+"') " +
                          "AND glt.month BETWEEN ('"+month_from+"') AND ('"+month_to+"') " +
                          "AND accUint.ACCOUNTING_UNIT_ID='"+accountingunit+"' " +
                          "AND Office.OFFICE_ID          ='"+accountingoffice+"' " +
                          "ORDER BY acchead, " +
                          "  date1, " +
                          "  doctype, " +
                          "  recpno";
                       }
                       else
                       {
                    	   if(alloffice.equalsIgnoreCase("all_office"))
                           {
                        	  
                        	   	 System.out.println("this is the case for single acc head + more month + for more than one office for single acc unit");
                        	   	reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GeneralLedger_UnitWiseModule/GeneralLedgerReportAll_office_wings_main_moreofficesingleunit.jasper"));
                        	   	System.out.println("reportFile===>"+reportFile);
                           }
                    	   else
                    		   reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GeneralLedger_UnitWiseModule/GeneralLedgerReportAll_office_wings_main.jasper")); 
                    	   System.out.println("reportFile===>"+reportFile);
                       }
                    }
                    else
                    {
                       
                    	if(accountingunit==5)
                    	{
                           reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GeneralLedger_UnitWiseModule/GeneralLedgerReport_banking_main_from_to.jasper")); 
                           System.out.println("reportFile===>"+reportFile);
                    	}
                    	else
                       {
                    	   System.out.println("Single acc head for single office and more offices");
                    	   if(alloffice==null)
                    	   {
		                    	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GeneralLedger_UnitWiseModule/GeneralLedgerReport_banking_main_from_to.jasper"));
		                    	 System.out.println("reportFile===>"+reportFile);
                    	   }
                    	   else if(alloffice.equalsIgnoreCase("all_office"))
                           {
                        	  
                        	   	 System.out.println("this is the case for single acc head + more month + for more than one office for single acc unit");
                        	   	reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GeneralLedger_UnitWiseModule/GeneralLedgerReport_banking_main_from_to_moreofficesingleunit.jasper"));
                        	   	System.out.println("reportFile===>"+reportFile);
                           }
                    	   
                    	   sql="SELECT a.CR_DR_INDICATOR, " +
		                       "  a.RECEIPT_NO, " +
		                       "  a.AMOUNT, " +
		                       "  a.CR_AMOUNT, " +
		                       "  a.DR_AMOUNT, " +
		                       "  a.TYPE, " +
		                       "  TO_CHAR(a.DATE_G,'DD-MM-YYYY') AS gl_date , " +
		                       "  a.PARTICULARS, " +
		                       "  a.MONTH_OPENING_BALANCE, " +
		                       "  a.MONTH_OPENING_CR_DR_IND, " +
		                       "  a.ACCOUNT_HEAD_CODE, " +
		                       "  a.MONTH, " +
		                       "  a.DOCTYPE, " +
		                       "  a.YEAR, " +
		                       "  a.ACCOUNTING_UNIT_ID, " +
		                       "  a.ACCOUNTING_FOR_OFFICE_ID, " +
		                       "  a.CASHBOOK_YEAR, " +
		                       "  a.CASHBOOK_MONTH , " +
		                       "  a.MONTH_IN_WORDS, " +
		                       "  accunit.accounting_unit_name, " +
		                       "  office.office_name, " +
		                       "  acc.account_head_desc " +
		                       "FROM FAS_GENERAL_LEDGER_REPORT_TMP a , " +
		                       "  FAS_MST_ACCT_UNITS accunit, " +
		                       "  COM_MST_OFFICES office , " +
		                       "  COM_MST_ACCOUNT_HEADS acc " +
		                       "WHERE a.ACCOUNTING_UNIT_ID    ='"+accountingunit+"' " +
		                       "AND a.ACCOUNTING_FOR_OFFICE_ID='"+accountingoffice+"' " +
		                       "AND office.office_id          ='"+accountingoffice+"' " +
		                       "AND accunit.accounting_unit_id='"+accountingunit+"' " +
		                       "AND acc.account_head_code     = a.account_head_code " +
		                       "ORDER BY a.CASHBOOK_MONTH";
                    	   }
                       }

             
               
             if (!reportFile.exists())
             throw new JRRuntimeException("File J not found. The report design must be compiled first.");

             JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
            
             Map map=new HashMap();
             map.put("accountingunitid",accountingunit);
             map.put("accountofficeid",accountingoffice);
             map.put("accountheadcode",accountheadcode);
             map.put("cashbookyear_from",year_from);
             map.put("cashbookyear_to",year_to);
             map.put("monthvalue_from",monthInWords_from);
             map.put("monthvalue_to",monthInWords_to);
             
             JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);
                  
             if (rtype.equalsIgnoreCase("HTML"))
             {
                       response.setContentType("text/html");
                       response.setHeader ("Content-Disposition", "attachment;filename=\"GeneralLedgerReport.html\"");
                       PrintWriter out = response.getWriter();
                       JRHtmlExporter exporter = new JRHtmlExporter();
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
                       response.setHeader ("Content-Disposition", "attachment;filename=\"GeneralLedgerReport.pdf\"");
                       OutputStream out = response.getOutputStream();
                       out.write(buf, 0, buf.length);
                       out.close();
             }
             else if (rtype.equalsIgnoreCase("EXCEL"))
             {
                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader ("Content-Disposition", "attachment;filename=\"GeneralLedgerReport.csv\"");
                    ExcelConverter excel = new ExcelConverter();
          		   	//String path = getServletContext().getRealPath("/WEB-INF/Excel/SubLedgerReport.csv");
          		   	String path = "c:\\GeneralLedgerReport.csv";
          		   	excel.writeInCsvFormat(sql,path);

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
                
         }
         catch (Exception ex)
         {
            String connectMsg ="Could not create the report " + ex.getMessage() + " " + 
            ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }

    }
}
