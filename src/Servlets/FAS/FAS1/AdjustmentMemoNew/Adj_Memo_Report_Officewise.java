package Servlets.FAS.FAS1.AdjustmentMemoNew;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
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
 * Servlet implementation class Adj_Memo_Report_Officewise
 */
public class Adj_Memo_Report_Officewise extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/html; charset=windows-1252";   
    public Adj_Memo_Report_Officewise() {
        super();
      
    }

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("inside Adj_Memo_Report officewise  post");
		response.setContentType(CONTENT_TYPE);
        String strCommand="";
        Connection con=null;
        Connection connection = null;
        PreparedStatement ps=null;
        Calendar c;
        int cmbAcc_UnitCode=0,cmbOffice_code=0,cashYear=0,cashMonth=0,cashYearFrom=0,cashYearTo=0,cashMonthFrom=0,cashMonthTo=0;
        Date challanDate=null;

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
     
        
        JasperDesign jasperDesign = null;
        File reportFile = null;

        try {
                     
            String rtype = request.getParameter("txtoption");
            String advice_type="";
            String heading="";
          int accountingunit = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
          //  int accountingoffice = Integer.parseInt(request.getParameter("cmbOffice_code"));
        //    System.out.println("accounting unit id is:" + accountingunit);
         //   System.out.println("office code is:" + accountingoffice);
         
            try{
            	
            	cashYear=Integer.parseInt(request.getParameter("txtCB_Year"));
            	cashMonth=Integer.parseInt(request.getParameter("txtCB_Month"));
            	cashYearFrom=Integer.parseInt(request.getParameter("txtCB_Year_from"));
            	cashYearTo=Integer.parseInt(request.getParameter("txtCB_Year_to"));
            	cashMonthFrom=Integer.parseInt(request.getParameter("txtCB_Month_from"));
            	cashMonthTo=Integer.parseInt(request.getParameter("txtCB_Month_to"));
            	advice_type=request.getParameter("advice_type");
            }catch(NumberFormatException e){
            	System.out.println("exception in get date "+e );
            }

            Map<Integer,String> mapMonth = new LinkedHashMap<Integer, String>();
            mapMonth.put(1, "January");
            mapMonth.put(2, "February");
            mapMonth.put(3, "March");
            mapMonth.put(4, "April");
            mapMonth.put(5, "May");
            mapMonth.put(6, "June");
            mapMonth.put(7, "July");
            mapMonth.put(8, "August");
            mapMonth.put(9, "September");
            mapMonth.put(10, "October");
            mapMonth.put(11, "November");
            mapMonth.put(12, "December");

            String queryString="";
              
            if(advice_type.equalsIgnoreCase("CR"))
            {
            
            reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/AdjustmentMemoNew/Reports/Adj_Memo_Report_Officewise.jasper"));
            }
            else
            {
            	reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/AdjustmentMemoNew/Reports/Adj_Memo_Report_Officewise_DR.jasper"));	
            }
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = new HashMap();
            
            if(advice_type.equalsIgnoreCase("CR"))
            {
            heading="Credit Advice";
            queryString="select "+
            " fm.voucher_no, "+
            " TO_CHAR(fm.voucher_date,'dd/MM/yyyy') as voucherDate, "+
            " fm.total_amount, "+
            " fm.receipt_no,"+
            " fm.receipt_date, "+
            " ft.accept_voucher_no, "+
            " ft.accept_voucher_date, "+
            " ft.amount, "+
            " ft.remarks,"+
            " ft.for_accounting_unit_id, "+
            " ft.sl_no,"+
            " (select accounting_unit_name from fas_mst_acct_units where accounting_unit_id=fm.accounting_unit_id) as mainunitname, "+
            " (select accounting_unit_name from fas_mst_acct_units where accounting_unit_id=ft.for_accounting_unit_id) as forunitname,FT.ACCOUNT_HEAD_CODE "+
            " from fas_adjust_memo_mst fm inner join fas_adjust_memo_trn ft "+
            " on fm.accounting_unit_id=ft.accounting_unit_id "+
            " and fm.accounting_for_office_id=ft.accounting_for_office_id "+
            " and fm.voucher_no=ft.voucher_no "+
            " and fm.cashbook_month=ft.cashbook_month "+
            " and fm.cashbook_year=ft.cashbook_year where FT.ACCOUNT_HEAD_CODE=610101 and ";
           // " where fm.transfer_status='L' "+
        //    " --ft.for_accounting_unit_id=142 "+
          //  " --to_date(fm.cashbook_month ||'-'|| fm.cashbook_year, 'mm-yyyy') between to_date( '12' || '-' || '2012', 'mm-yyyy') and to_date( '12' ||'-' || '2012' , 'mm-yyyy')"+ 
          //  " --order by for_accounting_unit_id ";
            
            if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
			   	queryString+="fm.cashbook_month		="+cashMonth+
			   	  			"AND fm.cashbook_year 			="+cashYear+
			   	  			" and ft.for_accounting_unit_id="+accountingunit+
			   	  			" order by for_accounting_unit_id";
			   	map.put("monthinwords","For the Month "+cashYear+" "+mapMonth.get(cashMonth));
			   }else{				   				   	
			   	queryString+="  to_date(fm.cashbook_month"+
			   				 " ||'-'|| + fm.cashbook_year, 'mm-yyyy') BETWEEN to_date( "+cashMonthFrom+
			   				 " || '-' "+
			   				 " || "+cashYearFrom+", 'mm-yyyy') "+
			   				 " AND to_date( "+cashMonthTo+
			   				 " ||'-' "+
			   				 " || "+cashYearTo+" , 'mm-yyyy') "+
			   				"  and ft.for_accounting_unit_id="+accountingunit+
			   	  			"  order by for_accounting_unit_id";
			   	map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+"  "+cashYearFrom+"  To  "+mapMonth.get(cashMonthTo)+"  "+cashYearTo);
			   }
            }
            else
            {
            	heading="Debit Advice";
            
            	queryString="select "+
                        " fm.voucher_no, "+
                        " TO_CHAR(fm.voucher_date,'dd/MM/yyyy') as voucherDate, "+
                        " fm.total_amount, "+
                        " fm.receipt_no,"+
                        " fm.receipt_date, "+
                        " ft.accept_voucher_no, "+
                        " ft.accept_voucher_date, "+
                        " ft.amount, "+
                        " ft.remarks,"+
                        " ft.for_accounting_unit_id, "+
                        " ft.sl_no,"+
                        " (select accounting_unit_name from fas_mst_acct_units where accounting_unit_id=fm.accounting_unit_id) as mainunitname, "+
                        " (select accounting_unit_name from fas_mst_acct_units where accounting_unit_id=ft.for_accounting_unit_id) as forunitname,FT.ACCOUNT_HEAD_CODE "+
                        " from fas_adjust_memo_mst fm inner join fas_adjust_memo_trn ft "+
                        " on fm.accounting_unit_id=ft.accounting_unit_id "+
                        " and fm.accounting_for_office_id=ft.accounting_for_office_id "+
                        " and fm.voucher_no=ft.voucher_no "+
                        " and fm.cashbook_month=ft.cashbook_month "+
                        " and fm.cashbook_year=ft.cashbook_year where FT.ACCOUNT_HEAD_CODE=900201 and ";
                       // " where fm.transfer_status='L' "+
                       // " --ft.for_accounting_unit_id=142 "+
                       //  " --to_date(fm.cashbook_month ||'-'|| fm.cashbook_year, 'mm-yyyy') between to_date( '12' || '-' || '2012', 'mm-yyyy') and to_date( '12' ||'-' || '2012' , 'mm-yyyy')"+ 
                       //  " --order by for_accounting_unit_id ";
                        
                        if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
            			   	queryString+="fm.cashbook_month		="+cashMonth+
            			   	  			"AND fm.cashbook_year 			="+cashYear+
            			   	  			" and ft.for_accounting_unit_id="+accountingunit+
            			   	  			" order by for_accounting_unit_id";
            			   	map.put("monthinwords","For the Month "+cashYear+" "+mapMonth.get(cashMonth));
            			   }else{				   				   	
            			   	queryString+="  to_date(fm.cashbook_month"+
            			   				 " ||'-'|| + fm.cashbook_year, 'mm-yyyy') BETWEEN to_date( "+cashMonthFrom+
            			   				 " || '-' "+
            			   				 " || "+cashYearFrom+", 'mm-yyyy') "+
            			   				 " AND to_date( "+cashMonthTo+
            			   				 " ||'-' "+
            			   				 " || "+cashYearTo+" , 'mm-yyyy') "+
            			   				"  and ft.for_accounting_unit_id="+accountingunit+
            			   	  			"  order by for_accounting_unit_id";
            			   	map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+"  "+cashYearFrom+"  To  "+mapMonth.get(cashMonthTo)+"  "+cashYearTo);
            			   }
            	
            }
            System.out.println("Query--->"+queryString);
          //  System.out.println("opt::" + rtype);
            //   JasperReport jasperReport =     JasperCompileManager.compileReport(jasperDesign);
            
            //map.put("unitid",accountingunit);
          //  map.put("officeid",cmbOffice_code);
            map.put("cashyear",cashYear);
	        map.put("cashmonth",cashMonth);
            map.put("query",queryString);
          
            
          //  System.out.println("queryString  "+queryString);
            
           // System.out.println("map  "+map);
            JasperPrint jasperPrint =JasperFillManager.fillReport(jasperReport, map, connection);


            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"adj_memo_report_officewise.html\"");
                PrintWriter out = response.getWriter();
                JRHtmlExporter exporter = new JRHtmlExporter();

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
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"adj_memo_report_officewise.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"adj_memo_report_officewise.xls\"");
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
                                   "attachment;filename=\"adj_memo_report_officewise.txt\"");

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
