package Servlets.PMS.PMS1.DCB.servlets;

/*import java.io.File;
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

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;*/
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;


public class Pms_Dcb_Beneficiary_Metre_List extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        // PrintWriter out = response.getWriter();
        System.out.println("Testing demo");
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        Connection connection = null;
        int BENEFICIARY_TYPE_ID_VAR = 0;
        int SUB_DIV_ID_VAR = 0;
        int BENEFICIARY_SNO_VAR = 0;
        int OFFICE_ID_VAR;
        /*
             * Get the Database Connection Object
             */
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
        /*  try {
                    path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Beneficiary_Metre_list.jasper");
                    System.out.println("Report Path :: "+path);
                    reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Beneficiary_Metre_list.jasper"));
                    if (!reportFile.exists()) throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                    JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
                    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, parameters, connection);
                    System.out.println("Report is Created from Jasper Print...");

                    byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
                }
                catch (JRException e)
                {
                    throw new ServletException(e);
                }*/
        try {
            path =
getServletContext().getRealPath("/WEB-INF/ReportSrc/Beneficiary_Metre_list.jasper");
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(path, parameters, connection);
            System.out.println("Report is Created from Jasper Print...");

            OutputStream outuputStream = response.getOutputStream();

            JRExporter exporter = null;

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition",
                               "attachment; filename=\"Beneficiary_Metre_list.pdf\"");
            exporter = new JRPdfExporter();
            exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                  jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                  outuputStream);
            exporter.exportReport();
            System.out.println("The File is Downloaded");
            outuputStream.close();
        } catch (JRException e) {
            throw new ServletException(e);
        }

    }

}
