package Servlets.FAS.FAS1.ReceiptSystem.Reports;

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

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;


public class SalesTax_Recovery_Report extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        /**
         * Variables Declarations
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


        /**
         * Set Content Type
         */
        response.setContentType(CONTENT_TYPE);


        /**
         *   Session Checking
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

        String userid = (String)session.getAttribute("UserId");
        System.out.println("session id is:" + userid);


        /**
         * Get Cashbook_month , Cashbook Year and Command ( Either Abstract or Summary )
         */
        int CashBook_Year =
            Integer.parseInt(request.getParameter("txtCB_Year"));
        int CashBook_Month =
            Integer.parseInt(request.getParameter("txtCB_Month"));
        String command = request.getParameter("Command");

        System.out.println("CashBook_Year---" + CashBook_Year);
        System.out.println("CashBook_Month--" + CashBook_Month);
        System.out.println("command --" + command);


        /**
         * Find Month Value
         */
        String monthInWords = "";
        if (CashBook_Month == 1)
            monthInWords = "January";
        else if (CashBook_Month == 2)
            monthInWords = "February";
        else if (CashBook_Month == 3)
            monthInWords = "March";
        else if (CashBook_Month == 4)
            monthInWords = "April";
        else if (CashBook_Month == 5)
            monthInWords = "May";
        else if (CashBook_Month == 6)
            monthInWords = "June";
        else if (CashBook_Month == 7)
            monthInWords = "July";
        else if (CashBook_Month == 8)
            monthInWords = "August";
        else if (CashBook_Month == 9)
            monthInWords = "September";
        else if (CashBook_Month == 10)
            monthInWords = "October";
        else if (CashBook_Month == 11)
            monthInWords = "November";
        else if (CashBook_Month == 12)
            monthInWords = "December";
        System.out.println("CashBook_Month..." + CashBook_Month);


        File reportFile = null;
        try {

            if (command.equalsIgnoreCase("Abstract")) {
                System.out.println("abs--->");
                reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/SalesTax_Recovery_Abstract.jasper"));
            } else if (command.equalsIgnoreCase("Summary")) {
                System.out.println("sum-->");
                reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/SalesTax_Recovery_Summary.jasper"));
            }

            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();
            map.put("cb_mon", CashBook_Month);
            map.put("cb_year", CashBook_Year);
            map.put("month_value", monthInWords);

            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);

            String rtype = "PDF";

            if (rtype.equalsIgnoreCase("PDF")) {

                System.out.println(rtype + "...inside PDF");

                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"GPFReport.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            }


        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage();
            String con_err = "Could not create the report " + ex;
            System.out.println(con_err);
            System.out.println(connectMsg);

        }


    }
}
