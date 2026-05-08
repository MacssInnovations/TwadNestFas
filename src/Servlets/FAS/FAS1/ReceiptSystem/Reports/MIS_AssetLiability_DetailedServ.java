package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
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

public class MIS_AssetLiability_DetailedServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        Connection connection = null;
        Statement statement = null;
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

        String CashBook_Year = request.getParameter("fin_year");
        String fin_year[] = CashBook_Year.split("-");
        int yr = 0, mn = 0, month = 0, chk_year = 0;
        try {
            ResultSet rs =
                statement.executeQuery("select extract (year from now()) as year,extract (month from now())as month ");
            if (rs.next()) {
                yr = rs.getInt("year");
                mn = rs.getInt("month");
            }
            chk_year = Integer.parseInt(fin_year[1]);
            if (Integer.parseInt(fin_year[1]) < yr)
                month = 3;
            else if (Integer.parseInt(fin_year[1]) == yr && mn > 3)
                month = 3;
            else {
                rs.close();
                if (mn > 3 && mn <= 12) {
                    chk_year = Integer.parseInt(fin_year[0]);
                } else if (mn >= 1 && mn <= 3) {
                    chk_year = Integer.parseInt(fin_year[1]);
                }
                rs =
  statement.executeQuery("select max(cashbook_month) as month from fas_trial_balance where cashbook_year=" +
                         chk_year);
                if (rs.next()) {
                    month = rs.getInt("month");
                }
            }
        } catch (Exception e) {
            System.out.println("Err in date selection:" + e.getMessage());
        }
        System.out.println("Financial year:" + chk_year);
        System.out.println("Financial month:" + month);

        Map map = null;
        map = new HashMap();
        File reportFile = null;
        try {
            System.out.println("inside try...");
            reportFile =
                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/MIS_Group_DetailedReport.jasper"));

            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            System.out.println("from ...");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            map.put("fin_year", chk_year);
            System.out.println("fin_year   " + chk_year);
            map.put("fin_month", month);
            System.out.println("fin_month   " + month);
            map.put("financial_year", CashBook_Year);
            System.out.println("financial_year   " + CashBook_Year);
            String major_head_code = "'A'" + "," + "'L'";
            map.put("major_head_code", major_head_code);
            System.out.println("major_head_code   " + major_head_code);
            System.out.println("report file path:" + reportFile);
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);

            byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
            response.setContentType("application/pdf");
            response.setContentLength(buf.length);
            response.setHeader("Content-Disposition",
                               "attachment;filename=\"Asset_Liability_Detailed.pdf\"");
            OutputStream out = response.getOutputStream();
            out.write(buf, 0, buf.length);
            out.close();
        } catch (Exception ex) {
            //ex.printStackTrace();
            String connectMsg =
                "Could not create the report " + ex.getMessage(); //+ " uu " +  ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }
        System.out.println("here after PDF");
        System.out.println("after send message");

    }
}
