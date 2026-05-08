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
import java.sql.ResultSet;
import java.sql.ResultSetMetaData;
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

import org.apache.poi.hssf.usermodel.HSSFCell;
import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver;

/**
 * Servlet implementation class Adj_Memo_Report
 */
public class Adj_Memo_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/html; charset=windows-1252";   
    public Adj_Memo_Report() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("inside Adj_Memo_Report   post");
		response.setContentType(CONTENT_TYPE);
        String strCommand="";
        Connection con=null;
        Connection connection = null;
        PreparedStatement ps=null;
       // ResultSet rs=null;
        Calendar c;
        int cmbAcc_UnitCode=0,cmbOffice_code=0,cashYear=0,cashMonth=0,cashYearFrom=0,cashYearTo=0,cashMonthFrom=0,cashMonthTo=0;
        Date challanDate=null;
       // String tpaType="",Name1="",com_report="",advType="",status="",tpaTypeCB="",tdacbtype="",s="";
      //  String queryString1 = "";
        String advice_type="";
        String status="";PreparedStatement ps2 = null;
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
        
       /* try {
        	LoadDriver load = new LoadDriver();
        	con = load.getConnection();
        } catch(Exception e) {
                     System.out.println("Exception in opening connection :"+e);
        }*/
      /*  try {
        
            strCommand=request.getParameter("Command");
            System.out.println("assign..here command..."+strCommand);
        }
        
        catch(Exception e) 
        {
            System.out.println("Exception in assigning..."+e);
        }*/
        
        JasperDesign jasperDesign = null;
        File reportFile = null;

        try {
           // System.out.println("calling servlet");

          //  String txtCB_Year = request.getParameter("txtCB_Year");
         //   String txtCB_Month = request.getParameter("txtCB_Month");
           
            String rtype = request.getParameter("txtoption");
        
            String heading="";
          //  String cmbAcc_UnitCode = request.getParameter("cmbAcc_UnitCode");
           // String cmbOffice_code = request.getParameter("cmbOffice_code");
        //    int listtype=Integer.parseInt(request.getParameter("listtype"));
        //    int grouptype=Integer.parseInt(request.getParameter("grouptype"));
        //    String penfamily=request.getParameter("penfamily");
            
         //   String monthvalue = "";
 
           
          //  System.out.println("accounting unit id is:" + cmbAcc_UnitCode);
           /* System.out.println("office code is:" + cmbOffice_code);


          //  int accountingunit = Integer.parseInt(cmbAcc_UnitCode);
            int accountingoffice = Integer.parseInt(cmbOffice_code);
*/
         
            try{
            	
            	cashYear=Integer.parseInt(request.getParameter("txtCB_Year"));
            	cashMonth=Integer.parseInt(request.getParameter("txtCB_Month"));
            	cashYearFrom=Integer.parseInt(request.getParameter("txtCB_Year_from"));
            	cashYearTo=Integer.parseInt(request.getParameter("txtCB_Year_to"));
            	cashMonthFrom=Integer.parseInt(request.getParameter("txtCB_Month_from"));
            	cashMonthTo=Integer.parseInt(request.getParameter("txtCB_Month_to"));
            	advice_type=request.getParameter("advice_type");
            	 status=request.getParameter("txt_status");
            	System.out.println(status);
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
            
          /*  
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
            monthInWords="December"; */
            String queryString="";
               
            if(advice_type.equalsIgnoreCase("CR"))
            {
            reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/AdjustmentMemoNew/Reports/Adj_Memo_Report.jasper"));
            }
            else
            {
            	reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/AdjustmentMemoNew/Reports/Adj_Memo_Report_DR.jasper"));	
            }
            
            System.out.println("reportFile--->"+reportFile);
            
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
            		            " fm.total_amount as r_amount, "+
            		            " fm.receipt_no,"+
            		            " fm.receipt_date, "+
            		            " ft.accept_voucher_no, "+
            		            " ft.accept_voucher_date, "+
            		            " ft.amount, "+
            		            " ft.remarks,"+
            		            " ft.for_accounting_unit_id, "+
            		            " ft.sl_no,fm.acceptance_status,"+
            		            " (select accounting_unit_name from fas_mst_acct_units where accounting_unit_id=fm.accounting_unit_id) as mainunitname, "+
            		            " (select accounting_unit_name from fas_mst_acct_units where accounting_unit_id=ft.for_accounting_unit_id) as forunitname,FT.ACCOUNT_HEAD_CODE "+
            		            " from fas_adjust_memo_mst fm inner join fas_adjust_memo_trn ft "+
            		            " on fm.accounting_unit_id=ft.accounting_unit_id "+
            		            " and fm.accounting_for_office_id=ft.accounting_for_office_id "+
            		            " and fm.voucher_no=ft.voucher_no "+
            		            " and fm.cashbook_month=ft.cashbook_month "+
            		            " and fm.cashbook_year=ft.cashbook_year where fm.MEMO_STATUS='L' and FT.ACCOUNT_HEAD_CODE=610101 and ";
            	
           
        
            
            if(request.getParameter("month_year").equalsIgnoreCase("particular_cb") && status.equalsIgnoreCase("Pending")){
			   	queryString+="fm.cashbook_month		="+cashMonth+
			   	  			"AND fm.cashbook_year 			="+cashYear +
			   	  			"and Ft.ACCEPTANCE_STATUS IS NULL and (ft.accept_voucher_no is null or ft.accept_voucher_no=0)"+
			   	  			" order by ft.for_accounting_unit_id,fm.voucher_no";
			   	map.put("monthinwords","Credit Advice For the Month "+mapMonth.get(cashMonth)+" "+cashYear);
			   }
            else {
            	if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
            	queryString+="fm.cashbook_month		="+cashMonth+
		   	  			"AND fm.cashbook_year 			="+cashYear +
		   	  			" order by ft.for_accounting_unit_id,fm.voucher_no";
		   	map.put("monthinwords","Credit Advice For the Month "+mapMonth.get(cashMonth)+" "+cashYear);
		   	
            }
            	}
            
             if (request.getParameter("month_year").equalsIgnoreCase("more_cb") && status.equalsIgnoreCase("Pending")){				   				   	
			   	queryString+="  to_date(fm.cashbook_month"+
			   				 " ||'-'|| + fm.cashbook_year, 'mm-yyyy') BETWEEN to_date( "+cashMonthFrom +
			   				 " || '-' "+
			   				 " || "+cashYearFrom+", 'mm-yyyy') "+
			   				 " AND to_date("+cashMonthTo+" "+
			   				 " ||'-' "+
			   				 " || "+cashYearTo+" , 'mm-yyyy') "+
			   				"AND Ft.ACCEPTANCE_STATUS IS NULL and (ft.accept_voucher_no is null or ft.accept_voucher_no=0) "+
			   				 "order by ft.for_accounting_unit_id,fm.voucher_no ";
			   	map.put("monthinwords","Credit Advice From "+mapMonth.get(cashMonthFrom)+"  "+cashYearFrom+" To  "+mapMonth.get(cashMonthTo)+"  "+cashYearTo);
			   }
             else {
            	 if (request.getParameter("month_year").equalsIgnoreCase("more_cb")) {
             
            	 queryString+="  to_date(fm.cashbook_month"+
		   				 " ||'-'|| + fm.cashbook_year, 'mm-yyyy') BETWEEN to_date( "+cashMonthFrom +
		   				 " || '-' "+
		   				 " || "+cashYearFrom+", 'mm-yyyy') "+
		   				 " AND to_date("+cashMonthTo+" "+
		   				 " ||'-' "+
		   				 " || "+cashYearTo+" , 'mm-yyyy') order by ft.for_accounting_unit_id,fm.voucher_no ";
		   	map.put("monthinwords","Credit Advice From "+mapMonth.get(cashMonthFrom)+"  "+cashYearFrom+" To  "+mapMonth.get(cashMonthTo)+"  "+cashYearTo);
             }
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
                        " and fm.cashbook_year=ft.cashbook_year where fm.MEMO_STATUS='L' and FT.ACCOUNT_HEAD_CODE=900201 and ";
                       // " where fm.transfer_status='L' "+
                    //    " --ft.for_accounting_unit_id=142 "+
                      //  " --to_date(fm.cashbook_month ||'-'|| fm.cashbook_year, 'mm-yyyy') between to_date( '12' || '-' || '2012', 'mm-yyyy') and to_date( '12' ||'-' || '2012' , 'mm-yyyy')"+ 
                      //  " --order by for_accounting_unit_id ";
                        
//                        if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
//            			   	queryString+="fm.cashbook_month		="+cashMonth+
//            			   	  			"AND fm.cashbook_year 			="+cashYear+
//            			   	  			" order by ft.for_accounting_unit_id,fm.voucher_no";
//            			   	map.put("monthinwords","Debit Advice For the Month "+mapMonth.get(cashMonth)+" "+cashYear);
//            			   }else{				   				   	
//            			   	queryString+="  to_date(fm.cashbook_month"+
//            			   				 " ||'-'|| + fm.cashbook_year, 'mm-yyyy') BETWEEN to_date( "+cashMonthFrom +
//            			   				 " || '-' "+
//            			   				 " || "+cashYearFrom+", 'mm-yyyy') "+
//            			   				 " AND to_date("+cashMonthTo+" "+
//            			   				 " ||'-' "+
//            			   				 " || "+cashYearTo+" , 'mm-yyyy') order by ft.for_accounting_unit_id,fm.voucher_no ";
//            			   	map.put("monthinwords","Debit Advice From "+mapMonth.get(cashMonthFrom)+"  "+cashYearFrom+" To  "+mapMonth.get(cashMonthTo)+"  "+cashYearTo);
//            			   }
            	  if(request.getParameter("month_year").equalsIgnoreCase("particular_cb") && status.equalsIgnoreCase("Pending")){
      			   	queryString+="fm.cashbook_month		="+cashMonth+
      			   	  			"AND fm.cashbook_year 			="+cashYear +
      			   	  			"and Ft.ACCEPTANCE_STATUS IS NULL and ft.accept_voucher_no is null"+
      			   	  			" order by ft.for_accounting_unit_id,fm.voucher_no";
      			   	map.put("monthinwords","Debit Advice For the Month "+mapMonth.get(cashMonth)+" "+cashYear);
      			   }
                  else {
                  	if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
                  	queryString+="fm.cashbook_month		="+cashMonth+
      		   	  			"AND fm.cashbook_year 			="+cashYear +
      		   	  			" order by ft.for_accounting_unit_id,fm.voucher_no";
      		   	map.put("monthinwords","Debit Advice For the Month "+mapMonth.get(cashMonth)+" "+cashYear);
      		   	
                  }
                  	}
                  
                   if (request.getParameter("month_year").equalsIgnoreCase("more_cb") && status.equalsIgnoreCase("Pending")){				   				   	
      			   	queryString+="  to_date(fm.cashbook_month"+
      			   				 " ||'-'|| + fm.cashbook_year, 'mm-yyyy') BETWEEN to_date( "+cashMonthFrom +
      			   				 " || '-' "+
      			   				 " || "+cashYearFrom+", 'mm-yyyy') "+
      			   				 " AND to_date("+cashMonthTo+" "+
      			   				 " ||'-' "+
      			   				 " || "+cashYearTo+" , 'mm-yyyy') "+
      			   				"AND Ft.ACCEPTANCE_STATUS IS NULL and ft.accept_voucher_no is null "+
      			   				 "order by ft.for_accounting_unit_id,fm.voucher_no ";
      			   	map.put("monthinwords","Debit Advice From "+mapMonth.get(cashMonthFrom)+"  "+cashYearFrom+" To  "+mapMonth.get(cashMonthTo)+"  "+cashYearTo);
      			   }
                   else {
                  	 if (request.getParameter("month_year").equalsIgnoreCase("more_cb")) {
                   
                  	 queryString+="  to_date(fm.cashbook_month"+
      		   				 " ||'-'|| + fm.cashbook_year, 'mm-yyyy') BETWEEN to_date( "+cashMonthFrom +
      		   				 " || '-' "+
      		   				 " || "+cashYearFrom+", 'mm-yyyy') "+
      		   				 " AND to_date("+cashMonthTo+" "+
      		   				 " ||'-' "+
      		   				 " || "+cashYearTo+" , 'mm-yyyy') order by ft.for_accounting_unit_id,fm.voucher_no ";
      		   	map.put("monthinwords","Debit Advice From "+mapMonth.get(cashMonthFrom)+"  "+cashYearFrom+" To  "+mapMonth.get(cashMonthTo)+"  "+cashYearTo);
                   }
                   }
            }
            
            
System.out.println(queryString);
            map.put("cashyear",cashYear);
	        map.put("cashmonth",cashMonth);
            map.put("query",queryString);
           // map.put("heading",heading);

            
           // System.out.println("queryString  "+queryString);
            
           // System.out.println("map  "+map);
            JasperPrint jasperPrint =JasperFillManager.fillReport(jasperReport, map, connection);


            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"adj_memo_report.html\"");
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
                // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                //response.setContentType("application/force-download");

                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"adj_memo_report.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {
            	String str="";
            	ps2=connection.prepareStatement(queryString);
                
//                ps2.setInt(1,cashYear);
//                ps2.setInt(2,cashMonth);
             
//                ps2.setString(5,Sub_Jour);
               
                
          
                ResultSet rs1=ps2.executeQuery();
                if(status.equalsIgnoreCase("Pending")) {
                	str="AdjMemo_Pending_DATE";
                }
                else {
                	str="AdjMemo_All_DATE";
                }
            	generateExcelFile(rs1,response,str);
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"adj_memo_report.xls\"");
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
                                   "attachment;filename=\"adj_memo_report.txt\"");

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
	public static void generateExcelFile(ResultSet resultSet, HttpServletResponse response, String filename) {
		try {

			// Create a new workbook
			HSSFWorkbook workbook = new HSSFWorkbook();
			HSSFSheet sheet = workbook.createSheet("Sheet 1");
			
			// Create header row
			ResultSetMetaData metaData = resultSet.getMetaData();
			int columnCount = metaData.getColumnCount();
			HSSFRow headerRow = sheet.createRow(0);
			for (int i = 1; i <= columnCount; i++) {
				HSSFCell cell = headerRow.createCell((short) (i - 1));
				cell.setCellValue(metaData.getColumnName(i).toUpperCase());
			}

			// Create data rows
			int rowNum = 1;
			while (resultSet.next()) {
				HSSFRow row = sheet.createRow(rowNum++);
				for (int i = 1; i <= columnCount; i++) {
					HSSFCell cell = row.createCell((short) (i - 1));

					int columnType = metaData.getColumnType(i);

					// Handle different data types
					switch (columnType) {
					case 2:
						cell.setCellValue(resultSet.getLong(i));
						break;
					case java.sql.Types.DOUBLE:
						cell.setCellValue(resultSet.getDouble(i));
						break;
					default:
						// Default to treating other types as strings
						cell.setCellValue(resultSet.getString(i));
					}
				}

			}

			// Auto-size columns for better readability
			for (int i = 0; i < columnCount; i++) {
				sheet.autoSizeColumn((short) i);
			}

			response.setContentType("application/vnd.ms-excel");
			response.setHeader("Content-Disposition", "attachment; filename=" + filename + ".xls");
			
			workbook.write(response.getOutputStream());

			System.out.println("Excel file generated successfully");
			
					
		} catch (Exception e) {
			e.printStackTrace();
		}
	}
}
