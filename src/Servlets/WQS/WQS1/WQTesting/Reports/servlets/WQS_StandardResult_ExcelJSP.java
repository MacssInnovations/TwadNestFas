package Servlets.WQS.WQS1.WQTesting.Reports.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.Statement;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

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

public class WQS_StandardResult_ExcelJSP extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        System.out.println("Welcome to servlet");
        Connection con = null;
        Statement stmt = null;
        JasperPrint jasperPrint = null;

        try {
            Class.forName("oracle.jdbc.OracleDriver");
            con =
 DriverManager.getConnection("jdbc:oracle:thin:@localhost:1521:xe", "wqlabs",
                             "wqlabs123");
            /* ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                       String ConnectionString="";

                       String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                       String strdsn=rs1.getString("Config.DSN");
                       String strhostname=rs1.getString("Config.HOST_NAME");
                       String strportno=rs1.getString("Config.PORT_NUMBER");
                       String strsid=rs1.getString("Config.SID");
                       String strdbusername=rs1.getString("Config.USER_NAME");
                       String strdbpassword=rs1.getString("Config.PASSWORD");

                       ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                       Class.forName(strDriver.trim());
                       con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                       try
                       {
                            stmt=con.createStatement();
                            con.clearWarnings();
                       }
                       catch(SQLException e)
                       {
                            System.out.println("Exception in creating statement:"+e);
                       }*/
        } catch (Exception e) {
            System.out.println("Exception in opening connection:" + e);
        }
        JasperDesign jasperDesign = null;
        File reportFile = null;
        try {
            reportFile =
                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_StandardResult_ExcelRep.jasper"));
            if (!reportFile.exists()) {
                System.out.println("does not exsist");
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            }
            System.out.println(JRLoader.loadObject(reportFile.getPath()));
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();
            jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                               "attachment;filename=\"ItemDetailsReport.xls\"");
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

        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }
    }
}
