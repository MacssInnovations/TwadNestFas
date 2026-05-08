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

public class Cash_and_Bank_Collection_Report1 extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {


        System.out.println("Cash and Bank Collection stated...................>>>>");

        /**
        * Variables Connection
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
         * Get Parameters
         */

        /** Get User ID */
        String userid = (String)session.getAttribute("UserId");
        System.out.println("session id is:" + userid);

        /** Get Accounting Unit ID */
        String Account_unit_Code = request.getParameter("cmbAcc_UnitCode");

        /** Get Accounting Office Code */
        String Office_Code = request.getParameter("cmbOffice_code");

        /** Get Cashbook Year */
        String CashBook_Year = request.getParameter("txtCB_Year");

        /** Get Cashbook Month */
        String CashBook_Month = request.getParameter("txtCB_Month");


        System.out.println("Account_Unit_Code is:" + Account_unit_Code);
        System.out.println("Office_Code is:" + Office_Code);
        System.out.println("CashBook_Year is:" + CashBook_Year);
        System.out.println("CashBook Month is:" + CashBook_Month);


        /**
         * Variables Declaration
         */

        int AccountUnitCode = 0;
        int OfficeCode = 0;
        int CashBookYear = 0;
        int CashBookMonth = 0;

        /**
         * Convert String Data into Integer Data
         */
        try {
            AccountUnitCode = Integer.parseInt(Account_unit_Code);
            OfficeCode = Integer.parseInt(Office_Code);
            CashBookYear = Integer.parseInt(CashBook_Year);
            CashBookMonth = Integer.parseInt(CashBook_Month);


            System.out.println("Account_Unit_Code After is:" +
                               AccountUnitCode);
            System.out.println("Office_Code After is:" + OfficeCode);
            System.out.println("CashBook_Year After is:" + CashBookYear);
            System.out.println("CashBook Month After is:" + CashBookMonth);


        } catch (Exception e) {
            System.out.println("Exception in Converting Integer:" + e);
        }


        /**
         *  Finding Cashbook Month
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
           * Report Generation
           */

        File reportFile = null;
        try {

            System.out.println("calling servlet..." + monthInWords);

            reportFile =
                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Cash_Bank_Collection_Report_forHO.jasper"));
            System.out.println(reportFile);
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            System.out.println("from ...");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());

            /** Passing Parameters */
            Map map = null;
            map = new HashMap();
            map.put("cashbookyear", CashBookYear);
            map.put("cashbookmonth", CashBookMonth);
            map.put("accountingunitid", AccountUnitCode);
            map.put("accountingofficeid", OfficeCode);
            map.put("monthvalue", monthInWords);

            System.out.println("cashbook year -->" + CashBookYear);
            System.out.println("CashBookMonth -->" + CashBookMonth);
            System.out.println("AccountUnitCode -->" + AccountUnitCode);
            System.out.println("OfficeCode -->" + OfficeCode);
            System.out.println("monthInWords" + monthInWords);


            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);
            String rtype = "PDF";


            /**
             * Generate PDF Report
             */
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

