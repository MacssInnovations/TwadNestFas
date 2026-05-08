
package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.awt.print.PrinterJob;

import java.io.IOException;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import java.util.*;

import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.JasperReport;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.io.*;

import java.text.SimpleDateFormat;

import javax.print.DocFlavor;

import javax.print.PrintService;

import net.sf.jasperreports.engine.JasperPrintManager;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;

import javax.print.attribute.PrintServiceAttributeSet;

import javax.print.attribute.standard.Copies;
import javax.print.attribute.standard.JobName;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;

import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
import net.sf.jasperreports.view.JasperViewer;


public class SubLedgerReport_SJV extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
    Connection connection = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        doGet(request, response);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {


        /**
       *  Session Checking
       */

        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        /**
       *  Variables Declaration
       */

        String opt = "";
        response.setContentType(CONTENT_TYPE);


        /**
         * Retrieving Configuration Values
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
        } catch (Exception ex) {
            String connectMsg =
                "Could not create the connection" + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }


        /**
        * Report Calling
        */

        System.out.println("Welcome to subledger");
        File reportFile = null;

        try {


            /** Get Cash Book Month and Year */
            String txtCB_Year = request.getParameter("txtCB_Year");
            String txtCB_Month = request.getParameter("txtCB_Month");

            /** Get Accounting Unit Id */
            String cmbAcc_UnitCode = request.getParameter("cmbAcc_UnitCode");

            /** Get Accounting Office id */
            String cmbOffice_code = request.getParameter("cmbOffice_code");

            /** Get Supplement Number */
            String supplement_no = request.getParameter("txtsupplement_no");
            System.out.println("supplement_no :: " + supplement_no);
            System.out.println("accounting unit id is:" + cmbAcc_UnitCode);
            System.out.println("office code is:" + cmbOffice_code);
            System.out.println("Cashbook Year:" + txtCB_Year);
            System.out.println("Cashbook Month:" + txtCB_Month);
            int accountingunit = 0, accountingoffice = 0, year = 0, month =
                0, suppl_no = 0;
            try {

                /** Convert Acciunting Unid Id and Office Id from String to Integer */
                accountingunit = Integer.parseInt(cmbAcc_UnitCode);
                accountingoffice = Integer.parseInt(cmbOffice_code);


                /** CONVERT CASH BOOK MONTH AND YEAR FROM STRING TO INTEGER */
                year = Integer.parseInt(txtCB_Year);
                month = Integer.parseInt(txtCB_Month);
                suppl_no = Integer.parseInt(supplement_no);
            } catch (Exception e) {
                System.out.println("Err in convertion :: " + e.getMessage());
            }

            /** Convert months in numbers to words for from cash book month */
            String monthInWords = "";
            if (month == 1)
                monthInWords = "January";
            else if (month == 2)
                monthInWords = "February";
            else if (month == 3)
                monthInWords = "March";
            else if (month == 4)
                monthInWords = "April";
            else if (month == 5)
                monthInWords = "May";
            else if (month == 6)
                monthInWords = "June";
            else if (month == 7)
                monthInWords = "July";
            else if (month == 8)
                monthInWords = "August";
            else if (month == 9)
                monthInWords = "September";
            else if (month == 10)
                monthInWords = "October";
            else if (month == 11)
                monthInWords = "November";
            else if (month == 12)
                monthInWords = "December";

            PreparedStatement ps = null;
            ResultSet rs = null;
            String UnitName = "", OfficeName = "";
            ps =
  connection.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
            ps.setInt(1, accountingunit);
            rs = ps.executeQuery();
            if (rs.next()) {
                UnitName = rs.getString("ACCOUNTING_UNIT_NAME");
                System.out.println("UnitName :: " + UnitName);
            }

            ps =
  connection.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
            ps.setInt(1, accountingoffice);
            rs = ps.executeQuery();
            if (rs.next()) {
                OfficeName = rs.getString("OFFICE_NAME");
                System.out.println("OfficeName" + OfficeName);
            }


            reportFile =
                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/GeneralLedger_UnitWiseModule/SJV_SubLedgerReport.jasper"));
            System.out.println("reportFile :: " + reportFile);

            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            System.out.println("opt::" + opt);

            Map map = new HashMap();
            map.put("accountingunitid", accountingunit);
            map.put("accountofficeid", accountingoffice);
            map.put("cashbookmonth", month);
            map.put("cashbookyear", year);
            map.put("monthvalue", monthInWords);
            map.put("supplementno", suppl_no);
            map.put("UnitName", UnitName);
            map.put("OfficeName", OfficeName);

            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);

            byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
            response.setContentType("application/pdf");
            response.setContentLength(buf.length);
            response.setHeader("Content-Disposition",
                               "attachment;filename=\"GeneralLedgerReport.pdf\"");
            OutputStream out = response.getOutputStream();
            out.write(buf, 0, buf.length);
            out.close();

        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }

    }
}
