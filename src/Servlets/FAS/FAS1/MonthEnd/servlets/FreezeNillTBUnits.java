package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

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

public class FreezeNillTBUnits extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {


        /**
         * Database Connection
         */


        Connection con = null;
        Statement statement = null;
        ResultSet rst = null, results = null;
        File reportFile = null;

        String strType = "";
        String xml = "<response>";
        String accname = null, officename = null;
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
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
            try {
                statement = con.createStatement();
                con.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing con:" + e);
        }


        /**
         * Content Type Setting
         */

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
         * Variables Declaration
         */

        int txtCB_Year = 0, txtCB_Month = 0, cmbAcc_UnitCode = 0, OfficeCode =
            0, achcode = 0, count = 0;
        txtCB_Year = Integer.parseInt(request.getParameter("txtCB_Year"));

        txtCB_Month = Integer.parseInt(request.getParameter("txtCB_Month"));

        System.out.println("cashbookmont1:" + txtCB_Month);
        System.out.println("cashbookyear1:" + txtCB_Year);

        String monthInWords = "";
        if (txtCB_Month == 1)
            monthInWords = "January";
        else if (txtCB_Month == 2)
            monthInWords = "February";
        else if (txtCB_Month == 3)
            monthInWords = "March";
        else if (txtCB_Month == 4)
            monthInWords = "April";
        else if (txtCB_Month == 5)
            monthInWords = "May";
        else if (txtCB_Month == 6)
            monthInWords = "June";
        else if (txtCB_Month == 7)
            monthInWords = "July";
        else if (txtCB_Month == 8)
            monthInWords = "August";
        else if (txtCB_Month == 9)
            monthInWords = "September";
        else if (txtCB_Month == 10)
            monthInWords = "October";
        else if (txtCB_Month == 11)
            monthInWords = "November";
        else if (txtCB_Month == 12)
            monthInWords = "December";


        try {
            System.out.println("here coming");

            reportFile =
                    new File(getServletContext().getRealPath("/org/FAS/FAS1/MonthEnd/reports/FreezeNillTBUnits.jasper"));
            System.out.println("reportFile:" + reportFile);
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());


            Map map = new HashMap();
            map.put("cashbookyear", txtCB_Year);
            map.put("cashbookmonth", txtCB_Month);
            map.put("monthinwords", monthInWords);
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, con);
            byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
            response.setContentType("application/pdf");
            response.setContentLength(buf.length);

            response.setHeader("Content-Disposition",
                               "attachment;filename=\"FreezeNillTBUnits.pdf\"");
            OutputStream out1 = response.getOutputStream();
            out1.write(buf, 0, buf.length);
            out1.close();

        } catch (Exception e) {
            System.out.println(e);
        }

    }


}
