package Servlets.PMS.PMS1.DCB.reports;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;

import java.sql.Connection;
import java.sql.DriverManager;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

/**
 * Servlet implementation class Pms_Dcb_District_Division_Mapping
 */
public class Pms_Dcb_District_Division_Mapping extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    private static final long serialVersionUID = 1L;
    private Connection connection;

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Pms_Dcb_District_Division_Mapping() {
        super();
        //  Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
                                                              IOException {
        //  Auto-generated method stub

        System.out.println("-------------------------------------doGet--------------------------");
        response.setContentType(CONTENT_TYPE);
        // PrintWriter out = response.getWriter();
        System.out.println("Testing demo");
        doPost(request, response);
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException,
                                                               IOException {
        System.out.println("-------------------------------------doPost--------------------------");
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
            path =
getServletContext().getRealPath("/WEB-INF/ReportSrc/pms_division_district_report.jasper");
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(path, parameters, connection);
            System.out.println("Report is Created from Jasper Print...");

            OutputStream outuputStream = response.getOutputStream();

            JRExporter exporter = null;

            response.setContentType("application/pdf");
            response.setHeader("Content-Disposition",
                               "attachment; filename=\"pms_division_district_report.pdf\"");
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
