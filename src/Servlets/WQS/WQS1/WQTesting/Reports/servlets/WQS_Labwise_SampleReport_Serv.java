package Servlets.WQS.WQS1.WQTesting.Reports.servlets;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.sql.Statement;

import java.util.Calendar;
import java.util.GregorianCalendar;
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
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;

public class WQS_Labwise_SampleReport_Serv extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        System.out.println("Welcome to servlet");
        Connection con = null;
        Statement stmt = null;
        JasperPrint jasperPrint = null;
        try {
            ResourceBundle rs1 =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs1.getString("Config.DSN");
            String strhostname = rs1.getString("Config.HOST_NAME");
            String strportno = rs1.getString("Config.PORT_NUMBER");
            String strsid = rs1.getString("Config.SID");
            String strdbusername = rs1.getString("Config.USER_NAME");
            String strdbpassword = rs1.getString("Config.PASSWORD");

//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
            try {
                stmt = con.createStatement();
                con.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in opening connection:" + e);
        }
        JasperDesign jasperDesign = null;
        File reportFile = null;
        try {
            String[] fd = request.getParameter("fdate").split("/");
            Calendar c =
                new GregorianCalendar(Integer.parseInt(fd[2]), Integer.parseInt(fd[1]) -
                                      1, Integer.parseInt(fd[0]));
            java.util.Date dtfrom = c.getTime();
            System.out.println("fdate=====>" + dtfrom);

            String[] td = request.getParameter("tdate").split("/");
            c =
   new GregorianCalendar(Integer.parseInt(td[2]), Integer.parseInt(td[1]) - 1,
                         Integer.parseInt(td[0]));
            java.util.Date dtto = c.getTime();
            System.out.println("tdate=====>" + dtto);
            String sql = "";
            String lab = request.getParameter("lab");
            System.out.println("lab code:" + lab);
            try {
                sql =
 "select lab_code,lab_desc,invoice_number,customer_type,total_samples,Report_due_date,final_result,count(samp) from " +
   "(select lab_code,invoice_number,customer_type,total_samples,Report_due_date,final_result from wqs_invoice_details " +
   ")a left outer join" +
   "(select distinct sample_number as samp,lab_code as labcode,invoice_number as innum from wqs_watersample_result " +
   ")b on a.lab_code=b.labcode and a.invoice_number=b.innum left outer join" +
   "(select lab_code as lab,lab_desc from wqs_mst_lab)c on a.lab_code=c.lab";
                if (!lab.equalsIgnoreCase("All")) {
                    System.out.println("inside if");
                    int labcode = Integer.parseInt(lab);
                    sql = sql + " where lab_code=" + labcode;
                } else
                    System.out.println("inside else");
                sql =
 sql + " group by lab_code,lab_desc,invoice_number,customer_type,total_samples,Report_due_date,final_result order by lab_desc";
            } catch (Exception e) {
                System.out.println("Err in sql:" + e.getMessage());
            }
            System.out.println("sql:" + sql);
            reportFile =
                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/WQS_Labwise_SampleReport.jasper"));
            System.out.println("after path");
            if (!reportFile.exists()) {
                System.out.println("does not exsist");
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            }
            System.out.println(JRLoader.loadObject(reportFile.getPath()));
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();
            map.put("reportquery", sql);
            jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);


            byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
            response.setContentType("application/pdf");
            response.setContentLength(buf.length);
            // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
            //response.setContentType("application/force-download");

            response.setHeader("Content-Disposition",
                               "attachment;filename=\"CustomerRep.pdf\"");
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
