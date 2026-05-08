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
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

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

public class A44ScheduleServlet extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        /**
         * Variable Declaration
         */
        Connection connection = null;
        Statement statement = null;

        /**
         * Database Connection
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


        /**
         * Session Checking
         */
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


        /**
         * Get Employee User ID
         */
        String userid = (String)session.getAttribute("UserId");


        /** Get Accounting Unit code */
        String Account_unit_Code = request.getParameter("cmbAcc_UnitCode");

        /** Get Accounting for Office Code */
        String Office_Code = request.getParameter("cmbOffice_code");

        /** Get Cashbook Year */
        String CashBook_Year = request.getParameter("txtCB_Year");

        /** Get Cashbook Month */
        String CashBook_Month = request.getParameter("txtCB_Month");

        /** Get Account Head Code */
        String txtAcc_HeadCode = request.getParameter("txtAcc_HeadCode");

        /** Print All the Data for Testing */
        System.out.println("Account_Unit_Code is:" + Account_unit_Code);
        System.out.println("Office_Code is:" + Office_Code);
        System.out.println("CashBook_Year is:" + CashBook_Year);
        System.out.println("CashBook Month is:" + CashBook_Month);
        System.out.println("Account Head is:" + txtAcc_HeadCode);
        
        /**
         * Variables Declarations
         */
        int AccountUnitCode = 0;
        int OfficeCode = 0;
        int CashBookYear = 0;
        int CashBookMonth = 0;
        int AccountHeadCode = 0;

        /**
         * Convert String Data into Integer Format
         */
        try {
            AccountUnitCode = Integer.parseInt(Account_unit_Code);
            OfficeCode = Integer.parseInt(Office_Code);
            CashBookYear = Integer.parseInt(CashBook_Year);
            CashBookMonth = Integer.parseInt(CashBook_Month);
            AccountHeadCode = Integer.parseInt(txtAcc_HeadCode);

        } catch (Exception e) {
            System.out.println("Exception in Converting Integer:" + e);
        }


        /**
         *  Find Month Value
         */
        String monthInWords = "";
        if (CashBookMonth == 1)
            monthInWords = "January";
        else if (CashBookMonth == 2)
            monthInWords = "February";
        else if (CashBookMonth == 3)
            monthInWords = "March";
        else if (CashBookMonth == 4)
            monthInWords = "April";
        else if (CashBookMonth == 5)
            monthInWords = "May";
        else if (CashBookMonth == 6)
            monthInWords = "June";
        else if (CashBookMonth == 7)
            monthInWords = "July";
        else if (CashBookMonth == 8)
            monthInWords = "August";
        else if (CashBookMonth == 9)
            monthInWords = "September";
        else if (CashBookMonth == 10)
            monthInWords = "October";
        else if (CashBookMonth == 11)
            monthInWords = "November";
        else if (CashBookMonth == 12)
            monthInWords = "December";


        /**
         * Report Type
         */
        String ReportType = null ,suppl_No="";
        if (AccountHeadCode == 0) {
            ReportType = "%";
        } else {
            ReportType = "" + AccountHeadCode + "";
        }


        /**
           * iReport Calling
           */

        File reportFile = null;
        try {

            int type = Integer.parseInt(request.getParameter("reporttype"));

            if (type == 1) {
                reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Schedules/A44/A44ScheduleReport.jasper"));
            } else if (type == 2) {
                reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Schedules/A44/A44ScheduleReport_NewPage.jasper"));
            } else if (type == 3) {
            	
            	String suppl_no1 = request.getParameter("supno");
            	
            	suppl_No = "and b.SUPPLEMENT_NO= " + suppl_no1;
            	System.out.println("suppl_no"+suppl_no1+"supp_no"+suppl_No);
            	
            	
                reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Schedules/A44/A44ScheduleReport_SJV.jasper"));
            }


            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            
            System.out.println("Report Type is :" + ReportType);
            
            Map map = null;
            map = new HashMap();
            map.put("cashbookyear", CashBookYear);
            map.put("cashbookmonth", CashBookMonth);
            map.put("accountingunitid", AccountUnitCode);
            map.put("accountofficeid", OfficeCode);
            map.put("monthvalue", monthInWords);
            map.put("accountheadcode", AccountHeadCode);
            map.put("reporttype", ReportType);
            map.put("suppl_No", suppl_No);
            
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);

            String rtype = "PDF";
            System.out.println(rtype);

            if (rtype.equalsIgnoreCase("PDF")) {
                System.out.println(rtype + "...inside PDF");
                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            }
        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage();
            System.out.println(connectMsg);
        }

    }
}
