package Servlets.PMS.PMS1.DCB.servlets;

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
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.util.JRLoader;

public class BeneficiaryMetreReportPdf extends HttpServlet {
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
        int flag = 0;
        int divisionname = 0;
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
        try {
            BENEFICIARY_TYPE_ID_VAR =
                    Integer.parseInt(request.getParameter("Beneficiary_type"));
        } catch (Exception e) {
            System.out.println("Error");
        }
        try {
            SUB_DIV_ID_VAR =
                    Integer.parseInt(request.getParameter("SubDivision"));
        } catch (Exception e) {
            System.out.println("Error1");
        }
        try {
            BENEFICIARY_SNO_VAR =
                    Integer.parseInt(request.getParameter("Beneficiary_Name"));
        } catch (Exception e) {
            System.out.println("Error2");
        }
        try {
            divisionname =
                    Integer.parseInt(request.getParameter("divisionname"));
        } catch (Exception e) {
            System.out.println("divisionname");
        }

        OFFICE_ID_VAR = divisionname;
        System.out.println(BENEFICIARY_TYPE_ID_VAR);
        System.out.println(SUB_DIV_ID_VAR);
        System.out.println(BENEFICIARY_SNO_VAR);
        System.out.println(OFFICE_ID_VAR);
        System.out.println(divisionname);
        String action = request.getParameter("command");
        String reportvalue = request.getParameter("reportvalue");
        System.out.println("The Action = " + action);
        System.out.println(reportvalue);

        if (action.equalsIgnoreCase("cmdreport")) {
            /*
                 * Get the URL Path Information for Referential Parameter in the PDF
                 */

            Map parameters = new HashMap();
            //String qry = "select * from metre";
            //  parameters.put("BENEFICIARY_TYPE_ID",BENEFICIARY_TYPE_ID_VAR );
            //  parameters.put("SUB_DIV_ID",SUB_DIV_ID_VAR );
            //  parameters.put("BENEFICIARY_SNO",BENEFICIARY_SNO_VAR );
            //  parameters.put("OFFICE_ID",OFFICE_ID_VAR );

            String path = "";
            File reportFile = null;
            try {
                // path = getServletContext().getRealPath("/WEB-INF/ReportSrc/Beneficiary_Metre.jasper");
                // System.out.println("Report Path :: "+path);

                if (reportvalue.equals("1")) {
                    parameters.put("BENEFICIARY_TYPE_ID",
                                   BENEFICIARY_TYPE_ID_VAR);
                    parameters.put("OFFICE_ID", OFFICE_ID_VAR);
                    parameters.put("DIVISION_ID", OFFICE_ID_VAR);
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Beneficiary_Metre_Type_1.jasper"));
                }
                if (reportvalue.equals("2")) {
                    parameters.put("SUB_DIV_ID", SUB_DIV_ID_VAR);
                    parameters.put("OFFICE_ID", OFFICE_ID_VAR);
                    parameters.put("DIVISION_ID", OFFICE_ID_VAR);
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Beneficiary_Metre_Type_2.jasper"));
                }
                if (reportvalue.equals("3")) {
                    parameters.put("BENEFICIARY_TYPE_ID",
                                   BENEFICIARY_TYPE_ID_VAR);
                    parameters.put("SUB_DIV_ID", SUB_DIV_ID_VAR);
                    parameters.put("OFFICE_ID", OFFICE_ID_VAR);
                    parameters.put("DIVISION_ID", OFFICE_ID_VAR);
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Beneficiary_Metre_Type_3A.jasper"));
                }
                if (reportvalue.equals("4")) {
                    parameters.put("BENEFICIARY_TYPE_ID",
                                   BENEFICIARY_TYPE_ID_VAR);
                    parameters.put("BENEFICIARY_SNO", BENEFICIARY_SNO_VAR);
                    parameters.put("OFFICE_ID", OFFICE_ID_VAR);
                    parameters.put("DIVISION_ID", OFFICE_ID_VAR);
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Beneficiary_Metre_Type_4.jasper"));
                }
                if (reportvalue.equals("5")) {
                    parameters.put("BENEFICIARY_TYPE_ID",
                                   BENEFICIARY_TYPE_ID_VAR);
                    parameters.put("SUB_DIV_ID", SUB_DIV_ID_VAR);
                    parameters.put("BENEFICIARY_SNO", BENEFICIARY_SNO_VAR);
                    parameters.put("OFFICE_ID", OFFICE_ID_VAR);
                    parameters.put("DIVISION_ID", OFFICE_ID_VAR);
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Beneficiary_Metre_Type_4A.jasper"));
                }
                if (reportvalue.equals("6")) {
                    parameters.put("OFFICE_ID", OFFICE_ID_VAR);
                    parameters.put("DIVISION_ID", OFFICE_ID_VAR);
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Beneficiary_Metre_list.jasper"));
                }


                if (!reportFile.exists())
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                JasperReport jasperReport =
                    (JasperReport)JRLoader.loadObject(reportFile.getPath());
                jasperReport.setWhenNoDataType(jasperReport.WHEN_NO_DATA_TYPE_ALL_SECTIONS_NO_DETAIL);
                JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperReport, parameters,
                                                 connection);
                System.out.println("Report is Created from Jasper Print...");

                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);

                response.setContentType("application/pdf");

                response.setContentLength(buf.length);

                OutputStream out = response.getOutputStream();

                out.write(buf, 0, buf.length);
                System.out.println("buf" + buf.length);
                //out.close();
                flag = 1;
            } catch (JRException e) {
                throw new ServletException(e);
            }
        }
        /*  if(flag==1)
           {

                try {
                    System.out.println("sdsdsd");
                   response.sendRedirect("../../../../../org/PMS/PMS1/HabitationSurvey/jsps/Pms_Dcb_Beneficiary_Metre_Report_Pdf.jsp");

                }
                catch (Exception e)

                {
                    System.out.println("error in send message");
                }
           }*/


    }
}

