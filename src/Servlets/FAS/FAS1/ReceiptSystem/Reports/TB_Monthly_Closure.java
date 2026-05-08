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


public class TB_Monthly_Closure extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {


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


        /** Get Cashbook Year */
        String CashBook_Year = request.getParameter("cbyear");


        /**
         * Variables Declaration
         */
        int CashBookYear = 0;


        /**
         * Convert String Data into Integer Data
         */
        try {
            CashBookYear = Integer.parseInt(CashBook_Year);
            System.out.println("CashBook_Year After is:" + CashBookYear);

        } catch (Exception e) {
            System.out.println("Exception in Converting Integer:" + e);
        }


        /**
           * Report Generation
           */

        File reportFile = null;
        try {

            reportFile =
                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TB_Monthly_Closure.jasper"));

            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            System.out.println("1");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());

            System.out.println("2");

            /** Passing Parameters */
            Map map = null;
            map = new HashMap();
            map.put("cashbookyear", CashBookYear);

            System.out.println("3");

            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);
            String rtype = "PDF";

            System.out.println("4");

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

