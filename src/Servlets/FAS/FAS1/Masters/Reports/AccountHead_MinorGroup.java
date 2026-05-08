package Servlets.FAS.FAS1.Masters.Reports;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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

public class AccountHead_MinorGroup extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);


    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        Connection connection = null;
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


        String cmblevel = "";
        String optview = "";
        String optdetail = "";
        String office = "";


        response.setContentType(CONTENT_TYPE);

        //JasperDesign jasperDesign = null;
        File reportFile = null;

        /*  list all user */
        try {
            System.out.println("list of all user in post:" +
                               request.getParameter("EmpSingleParam"));
            if (request.getParameter("EmpSingleParam") != null) {
                if (request.getParameter("EmpSingleParam").equalsIgnoreCase("AccountHeads_MinorGroup")) {
                    reportFile =
                            new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Masters/jasper/AccountHeads_MinorGroup.jasper"));

                    if (!reportFile.exists())
                        throw new JRRuntimeException("File J not found. The report design must be compiled first.");

                    JasperReport jasperReport =
                        (JasperReport)JRLoader.loadObject(reportFile.getPath());
                    Map map = null;
                    JasperPrint jasperPrint =
                        JasperFillManager.fillReport(jasperReport, map,
                                                     connection);
                    byte buf[] =
                        JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                    //response.setContentType("application/force-download");

                    //response.setHeader("Content-Disposition", "attachment;filename=\"OfficeDetail.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
                    return;

                } else if (request.getParameter("EmpSingleParam").equalsIgnoreCase("AccountHeads_MinorGroup")) {
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/ListofOtherUsers.jasper"));

                    if (!reportFile.exists())
                        throw new JRRuntimeException("File J not found. The report design must be compiled first.");

                    JasperReport jasperReport =
                        (JasperReport)JRLoader.loadObject(reportFile.getPath());
                    Map map = null;
                    JasperPrint jasperPrint =
                        JasperFillManager.fillReport(jasperReport, map,
                                                     connection);
                    byte buf[] =
                        JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                    //response.setContentType("application/force-download");
                    //response.setHeader("Content-Disposition", "attachment;filename=\"OfficeDetail.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
                    return;
                }

            }

        } catch (Exception e) {
            System.out.println("Error in List of other active users:" + e);
        }


    }


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection connection = null;
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
        ResultSet result = null;
        PreparedStatement ps = null;

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
        System.out.println("argument:" +
                           request.getParameter("EmpSingleParam"));
        if (request.getParameter("EmpSingleParam") != null) {
            System.out.println("list of EmpSingleParam");
            doPost(request, response);
        }

        System.out.println("Employee Detail Report GET");
        // response.setContentType("text/xml");
        //   response.setHeader("Cache-Control", "no-cache");
        //  PrintWriter out = response.getWriter();


    }


}
