package Servlets.PMS.PMS1.DCB.reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import java.sql.Connection;
import java.sql.DriverManager;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import Servlets.PMS.PMS1.DCB.servlets.Controller;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class Pms_Dcb_Ledger_Report
 */
public class Pms_Dcb_Ledger_Report extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Pms_Dcb_Ledger_Report() {
        super();
        //   Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
                                                              IOException {
        //   Auto-generated method stub
        response.setContentType(CONTENT_TYPE);
        // PrintWriter out = response.getWriter();
        System.out.println("Testing demo");
        // doPost(request, response);
        Connection connection = null;
        Controller obj = new Controller();

        System.out.println("Sdsdsd");
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
//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
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
        Map parameters = new HashMap();
        String path = "";
        File reportFile = null;
        try {

            System.out.println("Report is Created from Jasper Print...");

            path =
getServletContext().getRealPath("/WEB-INF/ReportSrc/DCB_RPT.jasper");
            parameters.put("year", obj.setValue("year", request));
            parameters.put("month", obj.setValue("month", request));
            System.out.println(path);
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(path, parameters, connection);
            System.out.println("Report is Created from Jasper Print...");
            int option = Integer.parseInt(obj.setValue("option", request));
            OutputStream outuputStream = response.getOutputStream();


            if (option == 1) {
                JRExporter exporter = null;


                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition",
                                   "attachment; filename=\"LedgerReport1.pdf\"");
                exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                      jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                      outuputStream);
                exporter.exportReport();
                System.out.println("The File is Downloaded");
                outuputStream.close();


            }

            else {
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"LedgerReport1.xls\"");
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


            }

        } catch (JRException e) {
            throw new ServletException(e);
        }
    }
    //9245700285

    /**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
    /*protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException
	{
		//   Auto-generated method stub
		Connection connection = null;
		System.out.println("Sdsdsd");
		 try
		 {
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
             ConnectionString =
                     strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                     ":" + strsid.trim();
             Class.forName(strDriver.trim());
             connection =
                     DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                 strdbpassword.trim());
         } catch (Exception ex)
         {
             String connectMsg =
                 "Could not create the connection" + ex.getMessage() + " " +
                 ex.getLocalizedMessage();
             System.out.println(connectMsg);
         }
         Map parameters = new HashMap();
         String path = "";
         File reportFile=null;
         try {
        	
        	
        	
        	 String path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Ob.jasper");
             JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, con);
             System.out.println("Report is Created from Jasper Print...");

             OutputStream outuputStream = response.getOutputStream();

             JRExporter exporter = null;

             response.setContentType("application/pdf");
             response.setHeader("Content-Disposition",
                                "attachment; filename=\"Ob.pdf\"");
             exporter = new JRPdfExporter();
             exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                   jasperPrint);
             exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                   outuputStream);
             exporter.exportReport();
             System.out.println("The File is Downloaded");
             outuputStream.close();
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
        	
          /*   path = getServletContext().getRealPath("/WEB-INF/ReportSrc/dcbaug20eve.jasper");
             JasperPrint jasperPrint = JasperFillManager.fillReport(path, parameters, connection);
             System.out.println("Report is Created from Jasper Print...");

             OutputStream outuputStream = response.getOutputStream();

             JRExporter exporter = null;

             response.setContentType("application/pdf");
             response.setHeader("Content-Disposition",
                                "attachment; filename=\"dcbaug20eve.pdf\"");
             exporter = new JRPdfExporter();
             exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                   jasperPrint);
             exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                   outuputStream);
             exporter.exportReport();
             System.out.println("The File is Downloaded");
             outuputStream.close();
         }
         catch (JRException e)
         {
             throw new ServletException(e);
         }*/
    /*try
         {
         reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/dcbaug20eve.jasper"));
         if (!reportFile.exists()) throw new JRRuntimeException("File J not found. The report design must be compiled first.");
         JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
          jasperReport.setWhenNoDataType(jasperReport.WHEN_NO_DATA_TYPE_ALL_SECTIONS_NO_DETAIL);
         JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
         System.out.println("Report is Created from Jasper Print...");
         System.out.println("path"+reportFile);
         byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);

         response.setContentType("application/pdf");

         response.setContentLength(buf.length);

         OutputStream out = response.getOutputStream();

         out.write(buf, 0, buf.length);

         System.out.println("The File is Downloaded");
        //out.close();
         }
         catch (JRException e)
         {
        	   System.out.println(e);
             throw new ServletException(e);

         }
	}*/

}
