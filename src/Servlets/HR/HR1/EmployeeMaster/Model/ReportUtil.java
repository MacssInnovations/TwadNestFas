package Servlets.HR.HR1.EmployeeMaster.Model;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import java.sql.Connection;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletResponse;


import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class ReportUtil {
    public void GenerateReport(File JasperReport, Map Params,
                               String outputFormat, String FileName,
                               HttpServletResponse response) throws Exception {

        JasperReport jasperReport =
            (JasperReport)JRLoader.loadObject(JasperReport.getPath());
        LoadDriver ld;
        ld = new LoadDriver();
        Connection connection = ld.getConnection();
        byte[] output;
        System.out.println(Params);
        JasperPrint jasperPrint =
            JasperFillManager.fillReport(jasperReport, Params, connection);
        try {
            try {
                if (outputFormat.equalsIgnoreCase("PDF")) {
                    response.setContentType("application/pdf");
                    output =
                            JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"OfficeDetail.pdf\"");
                } else {
                    JRExporter exporter;

                    if (outputFormat.equalsIgnoreCase("HTML")) {
                        response.setContentType("text/html");
                        response.setHeader("Content-Disposition",
                                           "attachment;filename=\"OfficeDetail.html\"");
                        exporter = new JRHtmlExporter();
                    } else if (outputFormat.equalsIgnoreCase("EXCEL")) {
                        response.setContentType("application/vnd.ms-excel");
                        response.setHeader("Content-Disposition",
                                           "attachment;filename=\"OfficeDetail.xls\"");
                        exporter = new JRXlsExporter();
                    } else {
                        throw new ServletException("Unknown report format: " +
                                                   outputFormat);
                    }

                    output = exportReportToBytes(jasperPrint, exporter);
                }
            } catch (JRException e) {
                throw new ServletException(e.getMessage(), e);
            }
            response.setContentLength(output.length);

            ServletOutputStream ouputStream;
            try {

                ouputStream = response.getOutputStream();
                ouputStream.write(output);
                ouputStream.flush();
                ouputStream.close();
            } catch (IOException e) {

                throw new ServletException(e.getMessage(), e);
            }


        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }

    }

    private byte[] exportReportToBytes(JasperPrint jasperPrint,
                                       JRExporter exporter) throws JRException {
        byte[] output;
        ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();

        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM, xlsReport);

        exporter.exportReport();

        output = xlsReport.toByteArray();
        return output;
    }

    public void GenerateReportFromJrxml(JasperReport jasperReport, Map Params,
                                        String outputFormat, String FileName,
                                        HttpServletResponse response) throws Exception {

        //	 JasperReport jasperReport = (JasperReport)JRLoader.loadObject(JasperReport.getPath());
        LoadDriver ld;
        ld = new LoadDriver();
        Connection connection = ld.getConnection();
        byte[] output;
        System.out.println(Params);
        JasperPrint jasperPrint =
            JasperFillManager.fillReport(jasperReport, Params, connection);
        try {
            try {
                if (outputFormat.equalsIgnoreCase("PDF")) {
                    response.setContentType("application/pdf");
                    output =
                            JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"OfficeDetail.pdf\"");
                } else {
                    JRExporter exporter;

                    if (outputFormat.equalsIgnoreCase("HTML")) {
                        response.setContentType("text/html");
                        response.setHeader("Content-Disposition",
                                           "attachment;filename=\"OfficeDetail.html\"");
                        exporter = new JRHtmlExporter();
                    } else if (outputFormat.equalsIgnoreCase("EXCEL")) {
                        response.setContentType("application/vnd.ms-excel");
                        response.setHeader("Content-Disposition",
                                           "attachment;filename=\"OfficeDetail.xls\"");
                        exporter = new JRXlsExporter();
                    } else {
                        throw new ServletException("Unknown report format: " +
                                                   outputFormat);
                    }

                    output = exportReportToBytes(jasperPrint, exporter);
                }
            } catch (JRException e) {
                throw new ServletException(e.getMessage(), e);
            }
            response.setContentLength(output.length);

            ServletOutputStream ouputStream;
            try {

                ouputStream = response.getOutputStream();
                ouputStream.write(output);
                ouputStream.flush();
                ouputStream.close();
            } catch (IOException e) {

                throw new ServletException(e.getMessage(), e);
            }


        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }
    }
}
