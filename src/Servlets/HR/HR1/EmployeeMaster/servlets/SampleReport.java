package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.JasperExportManager;

import java.sql.*;

import java.util.*;

import Servlets.Security.classes.UserProfile;

import net.sf.jasperreports.view.JasperViewer;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.JasperReport;

import java.io.OutputStream;


import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;

import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletContext;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.JRCsvExporter;
import net.sf.jasperreports.engine.export.JRCsvExporterParameter;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.util.JRLoader;

import java.io.*;


public class SampleReport extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);

        Connection connection = null;
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
        try {

            System.out.println("hello");
            JasperDesign jasperDesign =
                JRXmlLoader.load(getServletContext().getRealPath("\\WEB-INF\\ReportSrc") +
                                 "\\Employee.jrxml");
            JasperReport jasperReport =
                JasperCompileManager.compileReport(jasperDesign);
            Map map = new HashMap();
            map.put("descid", 14);
            Map imagesmap = new HashMap();

            /*
         * This Coding for getting Parameters from jsp and set in JasperPrint
                        //Map map=new HashMap();
                        //map.put("descid",14);
        */
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);

            //net.sf.jasperreports.engine.JasperExportManager exp = new net.sf.jasperreports.engine.JasperExportManager();
            PrintWriter out = response.getWriter();
            request.getSession(false).setAttribute("Images_map", imagesmap);

            response.setContentType("text/html");

            JRHtmlExporter exporter = new JRHtmlExporter();
            exporter.setParameter(JRHtmlExporterParameter.IMAGES_MAP,
                                  imagesmap);
            exporter.setParameter(JRHtmlExporterParameter.IMAGES_URI,
                                  getServletContext().getRealPath("WEB-INF\\Report\\"));

            exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,
                                  true);
            exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,
                                  getServletContext().getRealPath("WEB-INF\\Report\\"));
            exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
                                  false);
            exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                  jasperPrint);
            exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
            exporter.exportReport();
            out.close();
            //exp.exportReportToHtmlFile(jasperPrint,getServletContext().getRealPath("\\WEB-INF\\Report")+ "\\myhtml2.html");

        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }


        /* try {

                FileInputStream fis=new FileInputStream(getServletContext().getRealPath("\\WEB-INF\\Report")+ "\\myhtml2.html");
                if(fis==null) {
                     throw new Exception("File creation error!.");
                }
                byte[] b;
                File file=new File(getServletContext().getRealPath("\\WEB-INF\\Report")+ "\\myhtml2.html");
                b=new byte[(int)file.length()];
                fis.read(b);
                response.setContentType("text/html");
                ServletOutputStream sos=response.getOutputStream();
                sos.write(b);
                fis.close();
                //file.delete();

                sos.flush();
                sos.close();





             }
         catch (Exception ex) {
            String connectMsg =
                "Could not open  the report file " + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }*/


    }

}
