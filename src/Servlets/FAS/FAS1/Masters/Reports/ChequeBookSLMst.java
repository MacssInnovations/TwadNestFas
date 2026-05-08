package Servlets.FAS.FAS1.Masters.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

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

public class ChequeBookSLMst extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -4242700395015527807L;
	private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    Connection connection = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,
                                                            IOException {
    	System.out.println("fileType "+request.getParameter("txtoption"));
		Connection connection=null;
		int unitCode = 0;
		int officeCode = 0;		
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
                    output=request.getParameter("txtoption");
                    unitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
                    officeCode = Integer.parseInt(request.getParameter("cmbOffice_code"));
                }catch(Exception e) {
                    System.out.println("Exception in assigning..."+e);
                }
                try{
            		String s = "",myQry="";           	
            		myQry = "SELECT a.ACCOUNTING_UNIT_ID  AS ACCOUNTING_UNIT_ID, " +
            		"  a.ACCOUNTING_FOR_OFFICE_ID AS ACCOUNTING_FOR_OFFICE_ID, " +
            		"  a.ACCOUNT_NO               AS ACCOUNT_NO, " +
            		"  a.CHEQUE_BOOK_CODE         AS CHEQUE_BOOK_CODE, " +
            		"  a.NO_OF_LEAVES             AS NO_OF_LEAVES, " +
            		"  a.VERIFIED_BY              AS VERIFIED_BY, " +
            		"  a.START_LEAF_NO            AS START_LEAF_NO, " +
            		"  a.END_LEAF_NO              AS END_LEAF_NO, " +
            		"  a.STATUS                   AS STATUS, " +
            		"  b.BANK_NAME                AS BANK_NAME, " +
            		"  c.BRANCH_NAME              AS BRANCH_NAME, " +
            		"  d.AC_OPERATIONAL_MODE_ID   AS AC_OPERATIONAL_MODE_ID, " +
            		"  e.EMPLOYEENAME             AS EMPLOYEENAME, " +
            		"  f.ACCOUNTING_UNIT_NAME     AS ACCOUNTING_UNIT_NAME " +
            		"FROM " +
            		"  (SELECT ACCOUNTING_UNIT_ID, " +
            		"    ACCOUNTING_FOR_OFFICE_ID, " +
            		"    BANK_ID, " +
            		"    BRANCH_ID, " +
            		"    ACCOUNT_NO, " +
            		"    CHEQUE_BOOK_CODE, " +
            		"    NO_OF_LEAVES, " +
            		"    VERIFIED_BY, " +
            		"    START_LEAF_NO, " +
            		"    END_LEAF_NO, " +
            		"    STATUS " +
            		"  FROM COM_MST_CHEQUE_BOOKS_SL " +
            		"  WHERE ACCOUNTING_UNIT_ID    ='"+unitCode+"' " +
            		"  AND ACCOUNTING_FOR_OFFICE_ID='"+officeCode+"' " +
            		"  )a " +
            		"LEFT OUTER JOIN " +
            		"  ( SELECT BANK_ID, BANK_NAME FROM FAS_MST_BANKS " +
            		"  )b " +
            		"ON a.BANK_ID=b.BANK_ID " +
            		"LEFT OUTER JOIN " +
            		//"  ( SELECT BANK_ID, BRANCH_ID, BRANCH_NAME FROM FAS_BANK_BRANCH " +
            		"  ( SELECT BANK_ID, BRANCH_ID, BRANCH_NAME FROM FAS_MST_BANK_BRANCHES " +
            		"  )c " +
            		"ON a.BANK_ID   =c.BANK_ID " +
            		"AND a.BRANCH_ID=c.BRANCH_ID " +
            		"LEFT OUTER JOIN " +
            		"  (SELECT ACCOUNTING_UNIT_ID, " +
            		"    BANK_ID, " +
            		"    BRANCH_ID, " +
            		"    BANK_AC_NO, " +
            		"    AC_OPERATIONAL_MODE_ID " +
            		"  FROM FAS_MST_BANK_BALANCE " +
            		"  )d " +
            		"ON a.ACCOUNTING_UNIT_ID=d.ACCOUNTING_UNIT_ID " +
            		"AND a.BANK_ID          =d.BANK_ID " +
            		"AND a.BRANCH_ID        =d.BRANCH_ID " +
            		"AND a.ACCOUNT_NO       =d.BANK_AC_NO " +
            		"LEFT OUTER JOIN " +
            		"  (SELECT EMPLOYEE_ID, " +
            		"    EMPLOYEE_NAME " +
            		"    ||'.' " +
            		"    ||EMPLOYEE_INITIAL AS EMPLOYEENAME " +
            		"  FROM HRM_MST_EMPLOYEES " +
            		"  )e " +
            		"ON a.VERIFIED_BY=e.EMPLOYEE_ID " +
            		"LEFT OUTER JOIN " +
            		"  ( SELECT ACCOUNTING_UNIT_NAME,ACCOUNTING_UNIT_ID FROM FAS_MST_ACCT_UNITS " +
            		"  )f " +
            		"ON a.ACCOUNTING_UNIT_ID=f.ACCOUNTING_UNIT_ID";
            		s=request.getRealPath("/org/FAS/FAS1/Reports/Masters/jasper/ChequBookSL.jrxml");
            		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
            		System.out.println(myQry);
            		JRDesignQuery query=new JRDesignQuery();
            		query.setText(myQry);
            		jasperDesign.setQuery(query);
            		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
            		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, null, connection);
            		ByteArrayOutputStream bout=new ByteArrayOutputStream();
	            	if(output.equalsIgnoreCase("pdf")){
			            	OutputStream os=response.getOutputStream();
			            	response.setContentType("application/pdf");
			            	response.setHeader ("Content-Disposition", "attachment;filename=\"ChequBookSL.pdf\"");
			            	os.write(JasperManager.printReportToPdf(jasperPrint));
			            	os.close();
	            	}else if(output.equalsIgnoreCase("excel")){
	            			response.setContentType("application/vnd.ms-excel");
	            			response.setHeader ("Content-Disposition", "attachment;filename=\"ChequBookSL.xls\"");
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
		            		 response.setHeader ("Content-Disposition", "attachment;filename=\"ChequBookSL.html\"");
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
            	catch(Exception e){
            		e.printStackTrace();
            	}
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

    }
}
