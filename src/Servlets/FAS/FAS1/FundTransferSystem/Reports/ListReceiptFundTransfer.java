package Servlets.FAS.FAS1.FundTransferSystem.Reports;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

/**
 * Servlet implementation class Leave_type_Mst
 */
@SuppressWarnings("deprecation")
public class ListReceiptFundTransfer extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListReceiptFundTransfer() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		System.out.println("fileType "+request.getParameter("fileType"));
		Connection connection=null;
		int cashYear = 0;
		int cashMonth = 0;
		int preMonth = 0,prevyear=0;
		String prStr="";
        try{
        	LoadDriver driver = new LoadDriver();
  			connection = driver.getConnection();                           
        }catch(Exception e){
            System.out.println("Exception in connection..."+e);
        }   
        try{
        	HttpSession session=request.getSession(false);
            if(session==null){
            	response.sendRedirect(request.getContextPath()+"/index.jsp");
              }                  
            }catch(Exception e){
                System.out.println("Redirect Error :"+e);
             }                
                String output="";                
                try {
                    output=request.getParameter("output");
                    cashYear = Integer.parseInt(request.getParameter("txtCB_Year"));
                    cashMonth = Integer.parseInt(request.getParameter("txtCB_Month"));
                    if(cashMonth==1){
                    	prevyear=cashYear-1;
                    	System.out.println("prevyear::"+prevyear);
                    	preMonth=12;
                    }else{
                    	preMonth = cashMonth-1;
                    	prevyear=cashYear;
                    }                    
                }catch(Exception e) {
                    System.out.println("Exception in assigning..."+e);
                }
                try{
                	List<String> list = new ArrayList<String>();
                	Map<String, String> map = new HashMap<String, String>();
                	list.add("January");
                	list.add("February");
                	list.add("March");
                	list.add("April");
                	list.add("May");
                	list.add("June");
                	list.add("July");
                	list.add("August");
                	list.add("September");
                	list.add("October");
                	list.add("November");
                	list.add("December");
                	map.put("current", list.get(cashMonth-1));
                	map.put("previous", list.get(preMonth-1));
                	map.put("cashYear", request.getParameter("txtCB_Year"));
                    map.put("previousyear",prevyear+"");
            		String s = "",MyQry="";            		
            		MyQry="select a.*,b.F_Amt from(SELECT qq.ACCOUNTING_UNIT_ID AS ACCOUNTING_UNIT_ID, " +
            		"  qq.ACCOUNTING_FOR_OFFICE_ID AS ACCOUNTING_FOR_OFFICE_ID, " +
            		"  sa.ACCOUNTING_UNIT_NAME AS ACCOUNTING_UNIT_NAME, " +
            		"  ggg AS PRE_TOTRECEIPT, " +
            		"  dd AS TOTALRECEIPT, " +
            		"  (ggg+dd) AS TOT, " +
            		"  xxxx AS TOTALFUND" +
            		" FROM ( " +
            		"  (SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS " +
            		"  ) sa " +
            		"INNER JOIN " +
            		"  (SELECT ACCOUNTING_UNIT_ID, " +
            		"    ACCOUNTING_FOR_OFFICE_ID, " +
            		"    SUM(fhf) AS ggg, " +
            		"    SUM(dd)  AS dd , " +
            		"    SUM(yyy) AS xxxx " +
            		"  FROM " +
            		"    (SELECT ACCOUNTING_UNIT_ID, " +
            		"      ACCOUNTING_FOR_OFFICE_ID, " +
            		"      SUM(prev_amt)    AS fhf, " +
            		"      SUM(current_amt) AS dd, " +
            		"      SUM(full_amt)    AS yyy, " +
            		"      CREATED_BY_MODULE " +
            		"    FROM " +
            		"      (SELECT m.ACCOUNTING_UNIT_ID, " +
            		"        m.ACCOUNTING_FOR_OFFICE_ID, " +
            		"        ( " +
            		"        CASE " +
            		"          WHEN m.CASHBOOK_MONTH='"+preMonth+"' " +
            		"          AND m.CASHBOOK_YEAR  ='"+prevyear+"' " +
            		"          THEN SUM(m.TOTAL_AMOUNT) " +
            		"        END) AS prev_amt, " +
            		"        ( " +
            		"        CASE " +
            		"          WHEN m.CASHBOOK_MONTH='"+cashMonth+"' " +
            		"          AND m.CASHBOOK_YEAR  ='"+cashYear+"' " +
            		"          THEN SUM(m.TOTAL_AMOUNT) " +
            		"        END) AS current_amt, " +
            		"        ( " +
            		"        CASE " +
            		"          WHEN o.CASHBOOK_MONTH='"+cashMonth+"' " +
            		"          AND o.CASHBOOK_YEAR  ='"+cashYear+"' " +
            		"          THEN SUM(o.TOTAL_AMOUNT) " +
            		"        END) AS full_amt, " +
            		"        m.CASHBOOK_YEAR, " +
            		"        m.CASHBOOK_MONTH, " +
            		"        m.CREATED_BY_MODULE " +
            		"      FROM FAS_RECEIPT_MASTER m " +
            		"     left outer JOIN FAS_FUND_TRF_FROM_OFFICE o " +
            		"      ON m.accounting_unit_id  =o.accounting_unit_id " +
            		"      AND m.cashbook_year      =o.cashbook_year " +
            		"      AND m.cashbook_month     =o.cashbook_month " +
            		//"      WHERE m.CASHBOOK_YEAR    ='"+cashYear+"' " +
            		//"      AND m.CASHBOOK_MONTH    IN ('"+preMonth+"','"+cashMonth+"') " +
            		" Where (M.Cashbook_Year    ="+prevyear+" And M.Cashbook_Month="+preMonth+")"+
            	    "    or (M.Cashbook_Year    ="+cashYear+" and  m.CASHBOOK_MONTH ="+cashMonth+")"+
            		"      AND m.RECEIPT_STATUS     = 'L' " +
            		"      AND m.REMITTANCE_STATUS     = 'Y' " +
            		"      AND m.CREATED_BY_MODULE IN('CR','BR') " +
            		"      GROUP BY m.ACCOUNTING_UNIT_ID, " +
            		"        m.ACCOUNTING_FOR_OFFICE_ID, " +
            		"        m.CASHBOOK_YEAR, " +
            		"        m.CASHBOOK_MONTH, " +
            		"        m.CREATED_BY_MODULE , " +
            		"        o.CASHBOOK_YEAR, " +
            		"        o.cashbook_month " +
            		"      ) ss " +
            		"    GROUP BY ACCOUNTING_UNIT_ID, " +
            		"      ACCOUNTING_FOR_OFFICE_ID, " +
            		"      CREATED_BY_MODULE " +
            		"    ) rr " +
            		"  GROUP BY ACCOUNTING_UNIT_ID, " +
            		"    ACCOUNTING_FOR_OFFICE_ID " +
            		"  ) qq ON qq.ACCOUNTING_UNIT_ID=sa.ACCOUNTING_UNIT_ID ) "+
            		"  ORDER BY qq.ACCOUNTING_UNIT_ID)a" +
                        " Join\n" + 
                        " (Select Sum(Total_Amount)f_amt,ACCOUNTING_UNIT_ID From Fas_Fund_Trf_From_Office " +
                        " Where Transfer_Status='L' And Cashbook_Year="+cashYear+" And Cashbook_Month= " +cashMonth+" GROUP BY ACCOUNTING_UNIT_ID)b\n" + 
                        " on a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID";
                      //  System.out.println("MyQry::::"+MyQry);
            		s=request.getRealPath("/org/FAS/FAS1/Reports/FundTransferSystem/jasper/List_Receipt_Fund_Transfer.jrxml");
            		output=request.getParameter("fileType");
            		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
            		System.out.println(MyQry);
            		JRDesignQuery query=new JRDesignQuery();
            		query.setText(MyQry);
            		jasperDesign.setQuery(query);
            		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
            		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, connection);
            		ByteArrayOutputStream bout=new ByteArrayOutputStream();
	            	if(output.equalsIgnoreCase("pdf")){
			            	OutputStream os=response.getOutputStream();
			            	response.setContentType("application/pdf");
			            	response.setHeader ("Content-Disposition", "attachment;filename=\"List_Receipt_Fund_Transfer.pdf\"");
			            	os.write(JasperManager.printReportToPdf(jasperPrint));
			            	os.close();
	            	}else if(output.equalsIgnoreCase("excel")){
	            			response.setContentType("application/vnd.ms-excel");
	            			response.setHeader ("Content-Disposition", "attachment;filename=\"List_Receipt_Fund_Transfer.xls\"");
	            			OutputStream os=response.getOutputStream();
	            			JRXlsExporter exporterXLS = new JRXlsExporter(); 
	            			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
	            			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, bout);
	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	            			exporterXLS.exportReport();
	            		    byte[] buf=bout.toByteArray();
	            		    os.write(buf);
	            			os.close();
	            		}else if(output.equalsIgnoreCase("html")){            		
		            		 response.setContentType("text/html");
		            		 response.setHeader ("Content-Disposition", "attachment;filename=\"List_Receipt_Fund_Transfer.html\"");
		            		 PrintWriter out = response.getWriter();
		            		 JRHtmlExporter exporter = new JRHtmlExporter();            		
		            		 exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
		            		 exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		            		 exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
		            		 exporter.exportReport();
		            		 out.flush();
		            		 out.close();
	            		}
            	}
            	catch(Exception e)
            	{
            		e.printStackTrace();
            	}
	}

}
