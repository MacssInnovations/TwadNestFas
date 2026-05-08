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
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

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
 * Servlet implementation class Yearwise_Summary_Report
 */
public class Yearwise_Summary_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE =
	        "text/html; charset=windows-1252";  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Yearwise_Summary_Report() {
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
	
		System.out.println("Welcome to Yearwise_Summary_Rpt Servlet!............");
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

        String opt = "";
        Connection connection = null;
        response.setContentType(CONTENT_TYPE);
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
       
        String fin_year=request.getParameter("fin_year");
        System.out.println("fin_year===>"+fin_year);
        String rtype = request.getParameter("txtoption");
        System.out.println("rtype===>"+rtype);
        
        String sql="SELECT accounting_unit_id                            AS Unitid,\n" + 
        		"  SUM(current_month_debit)                           AS current_month_debit,\n" + 
        		"  SUM(current_month_credit)                          AS current_month_credit ,\n" + 
        		"  SUM(current_month_debit)-SUM(current_month_credit) AS net\n" + 
        		"FROM\n" + 
        		"  (SELECT accounting_unit_id,\n" + 
        		"    SUM(current_month_credit)                     AS current_month_credit,\n" + 
        		"    SUM(current_month_debit)                      AS current_month_debit,\n" + 
        		"    SUM(current_month_debit-current_month_credit) AS net_tb\n" + 
        		"  FROM fas_trial_balance\n" + 
        		"  WHERE To_Date((Cashbook_Month\n" + 
        		"    ||'-'\n" + 
        		"    || Cashbook_Year),'mm-yyyy') BETWEEN To_Date(4\n" + 
        		"    ||'-'\n" + 
        		"    ||"+fin_year.split("-")[0]+",'mm-yyyy')\n" + 
        		"  AND to_date(3\n" + 
        		"    ||'-'\n" + 
        		"    ||"+fin_year.split("-")[1]+",'mm-yyyy')\n" + 
        		"  GROUP BY accounting_unit_id\n" + 
        		"  UNION ALL\n" + 
        		"  SELECT accounting_unit_id,\n" + 
        		"    SUM(current_month_credit)                     AS current_month_credit,\n" + 
        		"    SUM(current_month_debit)                      AS current_month_debit,\n" + 
        		"    SUM(current_month_debit-current_month_credit) AS net_tb\n" + 
        		"  FROM fas_trial_balance_supplement\n" + 
        		"  WHERE To_Date((Cashbook_Month\n" + 
        		"    ||'-'\n" + 
        		"    || Cashbook_Year),'mm-yyyy') BETWEEN To_Date(4\n" + 
        		"    ||'-'\n" + 
        		"    ||"+fin_year.split("-")[0]+",'mm-yyyy')\n" + 
        		"  AND to_date(3\n" + 
        		"    ||'-'\n" + 
        		"    ||"+fin_year.split("-")[1]+",'mm-yyyy')\n" + 
        		"  GROUP BY accounting_unit_id\n" + 
        		"  ) as opt1 \n" + 
        		"GROUP BY accounting_unit_id\n" + 
        		"ORDER BY unitid";
        reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Yearwise_Summary_Rpt.jasper"));
        System.out.println("reportFile:" + reportFile);
        if (!reportFile.exists())
        	throw new JRRuntimeException("File J not found. The report design must be compiled first.");
        JasperReport jasperReport =(JasperReport)JRLoader.loadObject(reportFile.getPath());
        
        Map map = new HashMap();
        map.put("from_year",fin_year.split("-")[0]);
        map.put("to_year",fin_year.split("-")[1]);
        map.put("fin_year", fin_year.split("-")[0]+"-"+fin_year.split("-")[1].substring(2));
        map.put("qry",sql);
               
        System.out.println(map);
        JasperPrint jasperPrint =JasperFillManager.fillReport(jasperReport, map, connection);     
        if (rtype.equalsIgnoreCase("HTML")) {
            response.setContentType("text/html");
            response.setHeader("Content-Disposition",
                               "attachment;filename=\"TB_Summary_Yearwise.html\"");
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
                               "attachment;filename=\"TB_Summary_Yearwise.pdf\"");
            OutputStream out = response.getOutputStream();
            out.write(buf, 0, buf.length);
            out.close();
        } else if (rtype.equalsIgnoreCase("EXCEL")) {

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                               "attachment;filename=\"TB_Summary_Yearwise.xls\"");
            
            HSSFWorkbook hwb=new HSSFWorkbook();
       		HSSFSheet sheet =  hwb.createSheet("new sheet");
       		
       		HSSFRow rowhead=   sheet.createRow((short)0);
       		rowhead.createCell((short) 0).setCellValue("UnitId");
       		rowhead.createCell((short) 1).setCellValue("Current_month_debit");
       		rowhead.createCell((short) 2).setCellValue("Current_month_credit");
       		rowhead.createCell((short) 3).setCellValue("Net");
       		ServletOutputStream fileOut=null;
       		System.out.println("ss::::"+sql);
            PreparedStatement ps2=connection.prepareStatement(sql);
           ResultSet rs2=ps2.executeQuery();
           int j=1;
            while(rs2.next())
            {
         	  
        		HSSFRow row=   sheet.createRow((int)j);
        	
        		row.createCell((short) 0).setCellValue(rs2.getInt("Unitid"));
        		row.createCell((short) 1).setCellValue(rs2.getLong("current_month_debit"));
        		row.createCell((short) 2).setCellValue(rs2.getLong("current_month_credit"));
        		row.createCell((short) 3).setCellValue(rs2.getLong("net"));
        		
        		
        		
        		j++;
        	 }
            fileOut = response.getOutputStream();
            hwb.write(fileOut);
       		fileOut.close();
        		
       		
       		
       		
//            JRXlsExporter exporterXLS = new JRXlsExporter();
//            exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
//                                     jasperPrint);
//
//            ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
//            exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
//                                     xlsReport);
//            exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
//                                     Boolean.FALSE);
//            exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,
//                                     Boolean.TRUE);
//            exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
//                                     Boolean.FALSE);
//            exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
//                                     Boolean.TRUE);
//            exporterXLS.exportReport();
//            byte[] bytes;
//            bytes = xlsReport.toByteArray();
//            ServletOutputStream ouputStream = response.getOutputStream();
//            ouputStream.write(bytes, 0, bytes.length);
//            ouputStream.flush();
//            ouputStream.close();

        } else if (rtype.equalsIgnoreCase("TXT")) {

            response.setContentType("text/plain");
            response.setHeader("Content-Disposition",
                               "attachment;filename=\"ViewReceipt_Count.txt\"");

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
            
        }
        catch(Exception e)
        {
        	System.out.println("Major try block Exception is===>"+e);
        }
        
	
	}

}
