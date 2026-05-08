package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.awt.print.PrinterJob;

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


public class SubLedgerReportServlet extends HttpServlet {
    private static final String CONTENT_TYPE = 
        "text/html; charset=windows-1252";
    Connection connection = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request, 
                       HttpServletResponse response) throws ServletException, 
                                                            IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException,  
                                                           IOException {
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
        
        
      
        String opt="";
        response.setContentType(CONTENT_TYPE);
        try {


            ResourceBundle rs = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
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
        JasperDesign jasperDesign = null;
        File reportFile=null;
        
        try {
                System.out.println("calling servlet");
                String txtCB_Year=request.getParameter("txtCB_Year");
                String txtCB_Month=request.getParameter("txtCB_Month");
                
                String txtCB_Year_to=request.getParameter("txtCB_Year_to");
                String txtCB_Month_to=request.getParameter("txtCB_Month_to");
                           
                String rtype= request.getParameter("txtoption");
                String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
                String cmbOffice_code=request.getParameter("cmbOffice_code");
               
                System.out.println("accounting unit id is:"+cmbAcc_UnitCode);
                System.out.println("office code is:"+cmbOffice_code);
                //System.out.println("cmbMas_SL_type Value is:"+cmbMas_SL_type);
                System.out.println("Cashbook Year:"+txtCB_Year);
                System.out.println("Cashbook Month:"+txtCB_Month);
                int accountingunit=Integer.parseInt(cmbAcc_UnitCode);
                int accountingoffice=Integer.parseInt(cmbOffice_code);
                //int accountheadcode=0;
                
            int year=Integer.parseInt(txtCB_Year);
            int month=Integer.parseInt(txtCB_Month);
            
            int year_to=Integer.parseInt(txtCB_Year_to);
            int month_to=Integer.parseInt(txtCB_Month_to);
            
            
            //int subledgertypecode=0;
           /* if(cmbMas_SL_type!=null) {
                subledgertypecode=Integer.parseInt(cmbMas_SL_type);
                System.out.println("subledgertypecode is:"+subledgertypecode);
            }*/
            //String Specifictype=request.getParameter("SpecificAHC");
             //String cmbAccHeadCode=request.getParameter("txtAcc_HeadCode");
             //String cmbMas_SL_type=request.getParameter("cmbMas_SL_type");
             //System.out.println("cmbAccHeadCode is:"+cmbAccHeadCode);
             
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
            
                 
                   
               /* if(cmbAccHeadCode!=null)
                {
                    try { accountheadcode=Integer.parseInt(cmbAccHeadCode);}catch(Exception e){accountheadcode=0;}
                }
                */
                
          
            reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/SubLedgerReport.jasper")); 
                 
            if (!reportFile.exists())
            throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            
            JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
            

            System.out.println("opt::" + opt);
         //   JasperReport jasperReport =     JasperCompileManager.compileReport(jasperDesign);
         
         PreparedStatement ps=null;
         ResultSet rs=null;
         String UnitName="",OfficeName="";
                ps=connection.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
                ps.setInt(1,accountingunit);
                rs=ps.executeQuery();
                if(rs.next())
                     UnitName=rs.getString("ACCOUNTING_UNIT_NAME");
                
                ps=connection.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                ps.setInt(1,accountingoffice);
                rs=ps.executeQuery();
                if(rs.next())
                     OfficeName=rs.getString("OFFICE_NAME");
                    
      
            Map map=new HashMap();
            map.put("accountingunitid",accountingunit);
            map.put("accountofficeid",accountingoffice);
            map.put("cashbookmonth",month);
            map.put("cashbookyear",year);
            map.put("monthvalue",monthInWords);
            map.put("UnitName",UnitName);
            map.put("OfficeName",OfficeName);
            map.put("cashbookmonth_to",month_to);
            map.put("cashbookyear_to",year_to);
            map.put("monthvalue_to",monthInWords_to);
            if(month==month_to&&year==year_to){
            	map.put("caption","For the Month  "+monthInWords+"  "+year);
            }else{
            	map.put("caption","For the Period From  "+monthInWords+"  "+year+" To  "+monthInWords_to+"  "+year_to);
            }
            
           //map.put("accountheadcode",accountheadcode);
           //map.put("subledgertypecode",subledgertypecode);
           
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);
            
            if (rtype.equalsIgnoreCase("HTML"))   
            {
                        response.setContentType("text/html");
                        response.setHeader ("Content-Disposition", "attachment;filename=\"SubLedgerReport.html\"");
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
            else      if (rtype.equalsIgnoreCase("PDF"))   
            {
                        byte buf[] = 
                          JasperExportManager.exportReportToPdf(jasperPrint);
                        response.setContentType("application/pdf");
                        response.setContentLength(buf.length);
                       // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                       //response.setContentType("application/force-download");
                    
                        response.setHeader ("Content-Disposition", "attachment;filename=\"SubLedgerReport.pdf\"");
                        OutputStream out = response.getOutputStream();
                        out.write(buf, 0, buf.length);
                        out.close();
            }else if (rtype.equalsIgnoreCase("EXCEL")){
            	String sql = "";
            		   sql = "SELECT * " +
            		   "FROM " +
            		   "  (SELECT trim(s.ACCOUNTING_UNIT_ID)    AS ACCOUNTING_UNIT_ID , " +
            		   "    trim(s.ACCOUNTING_FOR_OFFICE_ID )   AS ACCOUNTING_FOR_OFFICE_ID, " +
            		   "    trim(s.FINANCIAL_YEAR)              AS FINANCIAL_YEAR, " +
            		   "    s.YEAR                              AS YEAR, " +
            		   "    s.MONTH                             AS MONTH, " +
            		   "    (s.ACCOUNT_HEAD_CODE)               AS ACCOUNT_HEAD_CODE, " +
            		   "    trim(s.SUB_LEDGER_TYPE_CODE)        AS SUB_LEDGER_TYPE_CODE, " +
            		   "    trim(s.SUB_LEDGER_CODE)             AS SUB_LEDGER_CODE, " +
            		   "    trim(v.SUB_LEDGER_TYPE_DESC)        AS SUB_LEDGER_TYPE_DESC, " +
            		   "    (s.MONTH_OPENING_BALANCE)           AS MONTH_OPENING_BALANCE , " +
            		   "    trim(s.MONTH_OPENING_BAL_DR_CR_IND) AS MONTH_OPENING_BAL_DR_CR_IND, " +
            		   "    (s.MONTH_CLOSING_BALANCE)           AS MONTH_CLOSING_BALANCE, " +
            		   "    trim(s.MONTH_CLOSING_BAL_DR_CR_IND) AS MONTH_CLOSING_BAL_DR_CR_IND, " +
            		   "    trim(s.vouType)                     AS vouType, " +
            		   "    trim(s.vouNO)                       AS vouNO, " +
            		   "    (s.vouDate)                         AS vouDate, " +
            		   "    trim(s.BILL_NO)                     AS BILL_NO, " +
            		   "    (s.BILL_DATE)                       AS BILL_DATE, " +
            		   "    trim(s.AGREEMENT_NO)                AS AGREEMENT_NO, " +
            		   "    trim(s.PARTICULARS)                 AS PARTICULARS, " +
            		   "    (s.CREDIT_AMOUNT)                   AS CREDIT_AMOUNT, " +
            		   "    (s.DEBIT_AMOUNT)                    AS DEBIT_AMOUNT, " +
            		   "    trim(h.ACCOUNT_HEAD_DESC)           AS ACCOUNT_HEAD_DESC " +
            		   "  FROM " +
            		   "    (SELECT m.ACCOUNTING_UNIT_ID, " +
            		   "      m.ACCOUNTING_FOR_OFFICE_ID, " +
            		   "      m.FINANCIAL_YEAR, " +
            		   "      m.YEAR, " +
            		   "      m.MONTH, " +
            		   "      m.ACCOUNT_HEAD_CODE, " +
            		   "      m.SUB_LEDGER_TYPE_CODE, " +
            		   "      m.SUB_LEDGER_CODE, " +
            		   "      m.MONTH_OPENING_BALANCE, " +
            		   "      m.MONTH_OPENING_BAL_DR_CR_IND, " +
            		   "      m.CURRENT_MONTH_DEBIT, " +
            		   "      m.CURRENT_MONTH_CREDIT, " +
            		   "      m.MONTH_CLOSING_BALANCE, " +
            		   "      m.MONTH_CLOSING_BAL_DR_CR_IND, " +
            		   "      t.CR_VOUCHER_TYPE " +
            		   "      || t.DR_VOUCHER_TYPE AS vouType, " +
            		   "      t.CR_VOUCHER_NO " +
            		   "      || t.DR_VOUCHER_NO AS vouNO, " +
            		   "      to_date(t.CR_VOUCHER_DATE " +
            		   "      || t.DR_VOUCHER_DATE) AS vouDate, " +
            		   "      t.BILL_NO, " +
            		   "      t.BILL_DATE, " +
            		   "      t.AGREEMENT_NO, " +
            		   "      trim( t.PARTICULARS ) AS PARTICULARS , " +
            		   "      t.CREDIT_AMOUNT, " +
            		   "      t.DEBIT_AMOUNT " +
            		   "    FROM FAS_SUB_LEDGER_MASTER m, " +
            		   "      FAS_SUB_LEDGER_TRANSACTION t " +
            		   "    WHERE m.ACCOUNTING_UNIT_ID    =t.ACCOUNTING_UNIT_ID " +
            		   "    AND m.ACCOUNTING_FOR_OFFICE_ID=t.ACCOUNTING_FOR_OFFICE_ID " +
            		   "    AND m.FINANCIAL_YEAR          =t.FINANCIAL_YEAR " +
            		   "    AND m.YEAR                    =t.YEAR " +
            		   "    AND m.MONTH                   =t.MONTH " +
            		   "    AND m.ACCOUNT_HEAD_CODE       =t.ACCOUNT_HEAD_CODE " +
            		   "    AND m.SUB_LEDGER_TYPE_CODE    =t.SUB_LEDGER_TYPE_CODE " +
            		   "    AND m.SUB_LEDGER_CODE         =t.SUB_LEDGER_CODE " +
            		   "    ) s, " +
            		   "    COM_MST_SL_TYPES v, " +
            		   "    COM_MST_ACCOUNT_HEADS h " +
            		   "  WHERE s.SUB_LEDGER_TYPE_CODE  =v.SUB_LEDGER_TYPE_CODE " +
            		   "  AND s.ACCOUNT_HEAD_CODE       =h.ACCOUNT_HEAD_CODE " +
            		   "  AND s.ACCOUNTING_UNIT_ID      ='"+accountingunit+"' " +
            		   "  AND s.ACCOUNTING_FOR_OFFICE_ID='"+accountingoffice+"' " +
            		   "  AND to_date(s.month " +
            		   "    ||'-' " +
            		   "    ||s.year,'mm-yyyy') BETWEEN to_date('"+month+"'" +
            		   "    || '-' " +
            		   "    || '"+year+"' ,'mm-yyyy' ) " +
            		   "  AND to_date( '"+month_to+"' " +
            		   "    || '-' " +
            		   "    || '"+year_to+"' ,'mm-yyyy') " +
            		   "  ) x " +
            		   "LEFT OUTER JOIN " +
            		   "  (SELECT trim(sl_type) AS sl_type, " +
            		   "    trim(sl_code)       AS sl_code, " +
            		   "    trim( sl_codename ) AS SLCODE_NAME " +
            		   "  FROM SL_TYPE_CODE_NAME_VIEW " +
            		   "  ) y " +
            		   "ON x.SUB_LEDGER_TYPE_CODE = y.SL_TYPE " +
            		   "AND x.SUB_LEDGER_CODE     =y.SL_CODE " +
            		   "ORDER BY SUB_LEDGER_TYPE_CODE, " +
            		   "  SUB_LEDGER_CODE, " +
            		   "  ACCOUNT_HEAD_CODE, " +
            		   "  YEAR, " +
            		   "  MONTH, " +
            		   "  vouDate, " +
            		   "  vouType, " +
            		   "  vouNO";
            		   response.setContentType("application/vnd.ms-excel");
            		   response.setHeader ("Content-Disposition", "attachment;filename=\"SubLedgerReport.csv\"");
            		   ExcelConverter excel = new ExcelConverter();
            		   //String path = getServletContext().getRealPath("/WEB-INF/Excel/SubLedgerReport.csv");
            		   String path = "c:\\SubLedgerReport.csv";
               		   excel.writeInCsvFormat(sql,path);
               		   
            }else if (rtype.equalsIgnoreCase("TXT")){
            
                response.setContentType("text/plain");
                response.setHeader ("Content-Disposition", "attachment;filename=\"SubLedgerReport.txt\"");
                     
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
     
        } catch (Exception ex) {
            String connectMsg = 
                "Could not create the report " + ex.getMessage() + " " + 
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }


    }
}
