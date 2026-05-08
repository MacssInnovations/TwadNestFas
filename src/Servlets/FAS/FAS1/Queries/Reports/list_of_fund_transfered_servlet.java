package Servlets.FAS.FAS1.Queries.Reports;

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
 * Servlet implementation class list_of_fund_transfered_servlet
 */
public class list_of_fund_transfered_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252"; 
	 Connection connection = null;
    public list_of_fund_transfered_servlet() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 doGet(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("list dhana::::");
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
        try 
        {
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
            connection =DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
        } catch (Exception ex) {
            String connectMsg ="Could not create the connection" + ex.getMessage() + " " + 
            ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }
        JasperDesign jasperDesign = null;
        File reportFile=null;
       
        try 
        {
           
            String txtCB_Year=request.getParameter("txtCB_Year");
            String txtCB_Month=request.getParameter("txtCB_Month");
                       
            String rtype= request.getParameter("txtoption");
            String hid_Reort=request.getParameter("ReportType");
            System.out.println("hid_Reort >> "+hid_Reort);
                
            int yearfrom=Integer.parseInt(txtCB_Year);
            int month=Integer.parseInt(txtCB_Month);
            String monthInWords = "";
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
       
            String qry="",head="";
            if(hid_Reort.equalsIgnoreCase("Office")){
            	/*qry="SELECT b.Accounting_Unit_Id, " +
            			"  a.* " +
            			" FROM " +
            			"  (SELECT T.Transfer_To_Office_Id, " +
            			"    (SELECT O.Office_Name " +
            			"    FROM Com_Mst_Offices O " +
        			"    WHERE O.Office_Id=T.Transfer_To_Office_Id " +
            			"    )AS Offname, " +
            			"    t.Cashbook_Year, " +
            			"    t.Cashbook_Month, " +
            			"    T.Voucher_No, " +
            			"    m.DATE_OF_TRANSFER, " +
            			"    T.Cheque_Or_Dd, " +
            			"    T.Cheque_Dd_No, " +
            			"    T.Cheque_Dd_Date, " +
            			"    t.AMOUNT, " +
            			"    t.Particulars, " +
            			"    T.Auto_Status, " +
            			"    t.Verify " +
            			"  FROM Fas_Fund_Trf_From_Ho_Trn T, " +
            			"    Fas_Fund_Trf_From_Ho_Master M " +
            			"  WHERE M.Accounting_Unit_Id    =T.Accounting_Unit_Id " +
            			"  AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " +
            			"  AND M.Cashbook_Year           =T.Cashbook_Year " +
            			"  AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH " +
            			"  AND M.Voucher_No              =T.Voucher_No " +
            			"  AND M.Cashbook_Year           = " +txtCB_Year+
            			"  AND M.Cashbook_Month          = " +txtCB_Month+
            			"  AND m.transfer_status         ='L' " +
            			"  AND T.Transfer_To_Office_Id!  =5000 " +
            			"  ORDER BY Transfer_To_Office_Id, " +
            			"    M.Date_Of_Transfer " +
            			"  )a " +
            			" LEFT OUTER JOIN " +
            			"  (SELECT Accounting_Unit_Id, " +
            			"    Accounting_Unit_Name, " +
            			"    Accounting_Unit_Office_Id " +
            			"  FROM Fas_Mst_Acct_Units " +
            			"  )B " +
            			" ON A.Transfer_To_Office_Id=B.Accounting_Unit_Office_Id " +
            			" ORDER BY Accounting_Unit_Id, " +
            			"  DATE_OF_TRANSFER";*/
            			//Joan Modified on 05 Jan 2015 
            	qry=" SELECT T.TRANSFER_TO_OFFICE_ID, " +
            			"  CASE " +
            			"    WHEN T.TRANSFER_TO_OFFICE_ID <> 5000 " +
            			"    THEN " +
            			"      (SELECT ACCOUNTING_UNIT_ID " +
            			"      FROM FAS_MST_ACCT_UNITS b " +
            			"      WHERE t.TRANSFER_TO_OFFICE_ID=B.ACCOUNTING_UNIT_OFFICE_ID " +
            			"      ) " +
            			"    WHEN T.TRANSFER_TO_OFFICE_ID = 5000 " +
            			"    THEN " +
            			"      (SELECT ACCOUNTING_UNIT_ID " +
            			"      FROM FAS_MST_ACCT_UNITS b " +
            			"      WHERE T.TRANSFERED_TO_HO_UNIT_ID=B.ACCOUNTING_UNIT_ID " +
            			"      ) " +
            			"  END AS ACCOUNTING_UNIT_ID, " +
            			"  (SELECT O.Office_Name " +
            			"  FROM Com_Mst_Offices O " +
            			"  WHERE O.Office_Id=T.Transfer_To_Office_Id " +
            			"  )AS Offname, " +
            			"  t.Cashbook_Year, " +
            			"  t.Cashbook_Month, " +
            			"  T.Voucher_No, " +
            			"  m.DATE_OF_TRANSFER, " +
            			"  T.Cheque_Or_Dd, " +
            			"  T.Cheque_Dd_No, " +
            			"  T.Cheque_Dd_Date, " +
            			"  t.AMOUNT, " +
            			"  t.Particulars, " +
            			"  T.AUTO_STATUS, " +
            			"  t.Verify, " +
            			"  t.TRANSFERED_TO_HO_UNIT_ID " +
            			" FROM Fas_Fund_Trf_From_Ho_Trn T, " +
            			"  Fas_Fund_Trf_From_Ho_Master M " +
            			" WHERE M.Accounting_Unit_Id    =T.Accounting_Unit_Id " +
            			" AND M.ACCOUNTING_FOR_OFFICE_ID=T.ACCOUNTING_FOR_OFFICE_ID " +
            			" AND M.Cashbook_Year           =T.Cashbook_Year " +
            			" AND M.CASHBOOK_MONTH          =T.CASHBOOK_MONTH " +
            			" AND M.VOUCHER_NO              =T.VOUCHER_NO " +
            			" AND M.CASHBOOK_YEAR           = " +txtCB_Year+
            			" AND M.Cashbook_Month          = " +txtCB_Month+
            			" AND M.TRANSFER_STATUS         ='L' " +
            			" ORDER BY Transfer_To_Office_Id, " +
            			"  M.DATE_OF_TRANSFER" ;		
            	
            	head="Fund Transfer From Banking Section For  "+yearfrom+" - "+monthInWords;
            }else {
            	head="Fund Receipt By Banking Unit For  "+yearfrom+" - "+monthInWords;
				/*qry="SELECT t.ACCOUNTING_UNIT_ID , " +
						"  t.ACCOUNTING_FOR_OFFICE_ID as TRANSFER_TO_OFFICE_ID, " +
						"  (Select O.Office_Name From Com_Mst_Offices O Where O.Office_Id=T.ACCOUNTING_FOR_OFFICE_ID)As Offname, " +
						"  t.Cashbook_Year, " +
						"  t.Cashbook_Month, " +
						"  T.Voucher_No, " +
						"  T.DATE_OF_TRANSFER, " +
						"  T.Cheque_Or_Dd, " +
						"  T.Cheque_Dd_No, " +
						"  T.Cheque_Dd_Date, " +
						"  t.total_AMOUNT as AMOUNT, " +
						"  t.Particulars, " +
						"  h.Auto_Status, " +
						"  t.Verify " +
						" FROM FAS_FUND_TRF_FROM_OFFICE T, " +
						"  FAS_FUND_RECEIPT_BY_HO h " +
						" WHERE t.accounting_for_office_id=h.received_from_office_id " +
						" AND t.cashbook_month            =h.Cashbook_Month " +
						" AND t.cashbook_year             =h.Cashbook_Year " +
						" AND t.voucher_no                =h.trf_voucher_no " +
						" AND t.date_of_transfer          =h.trf_voucher_date " +
						" AND H.Cashbook_Year             = " +txtCB_Year+
						" AND H.Cashbook_Month            = " +txtCB_Month+
					//	" AND h.Auto_Status               ='Y' " +
						" AND T.transfer_status           ='L' AND h.receipt_status           ='L' " +
						" ORDER BY t.ACCOUNTING_UNIT_ID, " +
						"  T.Date_Of_Transfer";*/
            	
            	qry="SELECT t.ACCOUNTING_UNIT_ID , " +
						"  t.ACCOUNTING_FOR_OFFICE_ID as TRANSFER_TO_OFFICE_ID, " +
						"  (Select O.Office_Name From Com_Mst_Offices O Where O.Office_Id=T.ACCOUNTING_FOR_OFFICE_ID)As Offname, " +
						"  t.Cashbook_Year, " +
						"  t.Cashbook_Month, " +
						"  T.Voucher_No, " +
						"  T.DATE_OF_TRANSFER, " +
						"  T.Cheque_Or_Dd, " +
						"  T.Cheque_Dd_No, " +
						"  T.Cheque_Dd_Date, " +
						"  t.total_AMOUNT as AMOUNT, " +
						"  t.Particulars, " +
						" coalesce(T.AUTO_STATUS,null,'N',T.AUTO_STATUS) as AUTO_STATUS, "+
						"  t.Verify " +
						" FROM FAS_FUND_TRF_FROM_OFFICE T " +
						" WHERE "+
						"  T.Cashbook_Year             = " +txtCB_Year+
						" AND T.Cashbook_Month            = " +txtCB_Month+
					//	" AND h.Auto_Status               ='Y' " +
						" AND T.transfer_status           ='L'  " +
						" ORDER BY t.ACCOUNTING_UNIT_ID, " +
						"  T.Date_Of_Transfer";

			}
            System.out.println("qry ::: "+qry);
            
            reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/FundTransferSystem/jasper/banking_section_trf_units.jasper")); 
                 
            if (!reportFile.exists())
            throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            
            JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
           
            Map map=new HashMap();
            map.put("qry",qry);    
            map.put("Head",head);        
            	map.put("year",yearfrom);        
                map.put("month",month);
	            map.put("monthValue",monthInWords);
           
     
         
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);            
            if (rtype.equalsIgnoreCase("HTML"))   
            {
                        response.setContentType("text/html");
                        response.setHeader ("Content-Disposition", "attachment;filename=\"ListOfFundTransferedOffices.html\"");
                        PrintWriter out = response.getWriter();
                        JRHtmlExporter exporter = new JRHtmlExporter();
                       
                        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
                        exporter.exportReport();
                        out.flush();
                        out.close();
            }
            else      if (rtype.equalsIgnoreCase("PDF"))   
            {
                        byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
                        response.setContentType("application/pdf");
                        response.setContentLength(buf.length);
                                         
                        response.setHeader ("Content-Disposition", "attachment;filename=\"ListOfFundTransferedOffices.pdf\"");
                        OutputStream out = response.getOutputStream();
                        out.write(buf, 0, buf.length);
                        out.close();
            }
            else      if (rtype.equalsIgnoreCase("EXCEL"))   
            {
    
                    	response.setContentType("application/vnd.ms-excel");
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"ListOfFundTransferedOffices.xls\"");
	                    JRXlsExporter exporterXLS = new JRXlsExporter(); 
	                    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
                     
                        ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
                        exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
                        exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
                        exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
	                    exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
	                    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
	                    exporterXLS.exportReport(); 
	                    byte []bytes;
	                    bytes = xlsReport.toByteArray();
	                    ServletOutputStream ouputStream = response.getOutputStream();
	                    ouputStream.write(bytes, 0, bytes.length);
	                    ouputStream.flush();
	                    ouputStream.close();

            }
            else      if (rtype.equalsIgnoreCase("TXT"))   
            {
            
		                response.setContentType("text/plain");
		                response.setHeader ("Content-Disposition", "attachment;filename=\"ListOfFundTransferedOffices.txt\"");
		                     
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
            String connectMsg = 
            "Could not create the report " + ex.getMessage() + " " + 
            ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }
	}

}
