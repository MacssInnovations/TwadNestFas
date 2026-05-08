package Servlets.PMS.PMS1.DCB.reports;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;


public class TariffReport extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);


    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;
        //PreparedStatement ps=null;
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
//                    ":" + strsid.trim();.
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());
            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }
        response.setContentType(CONTENT_TYPE);
        String strCommand = "";
        String xml = "";


        String ben = null;
        int count = 2;
        int month = java.util.Calendar.getInstance().get(Calendar.MONTH);
        int year = java.util.Calendar.getInstance().get(Calendar.YEAR);

        int oid = 0;

        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        String userid = (String)session.getAttribute("UserId");
        System.out.println("Session id is:" + userid);


        /*******************************************************************
         *                 Command parameters
         *******************************************************************/
        try {
            strCommand = request.getParameter("action");
            System.out.println("strCommand : " + strCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }

        /******************************************************************/


        /*******************************************************************
         *                 Other parameters
         *******************************************************************/
        try {
            ben = request.getParameter("ben");
            System.out.println("ben : " + ben);
        } catch (Exception e) {
            System.out.println("Exception fetching ben ===> " + e);
        }

        /******************************************************************/


        /*
         * Execute the task adhered to the requested command
         */

        if (strCommand.equalsIgnoreCase("Report")) {
            System.out.println("\n*************\nReport\n**************\n");

            /*******************************************************************
             *                 Tariff parameters
             *******************************************************************/

            try {
                ben = request.getParameter("ben");
                System.out.println("ben : " + ben);
            } catch (Exception e) {
                e.printStackTrace();
            }

            /******************************************************************/


            try {
                File reportFile = null;
                String path =
                    getServletContext().getRealPath("/WEB-INF/ReportSrc/Tariff.jasper");
                reportFile = new File(path);
                String ctxpath =
                    path.substring(0, path.lastIndexOf("Tariff.jasper"));
                if (!reportFile.exists())
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");

                JasperReport jasperReport =
                    (JasperReport)JRLoader.loadObject(reportFile.getPath());
                Map map = new HashMap();
                map.put("ctxpath", ctxpath);
                map.put("ben", ben);

                JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperReport, map,
                                                 connection);

                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);

                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"Tariff.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf);
                System.out.println("Report generated..");
            } catch (Exception ex) {
                String connectMsg =
                    "Could not create the report " + ex.getMessage() + " " +
                    ex.getLocalizedMessage();
                System.out.println(connectMsg);
            }

        }


    }

}

