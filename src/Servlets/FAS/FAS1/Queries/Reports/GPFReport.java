package Servlets.FAS.FAS1.Queries.Reports;

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


public class GPFReport extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {

        Connection connection = null;
        Statement statement = null;
        ResultSet results = null;
        ResultSet results1 = null;
        ResultSet results3 = null;
        ResultSet results4 = null;
        ResultSet results5 = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps6 = null;
        boolean flag = false;
        String str = "";
        String strnew = "";

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

        //int Account_unit_Code=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        //int Office_Code=Integer.parseInt(request.getParameter("cmbOffice_code"));
        int CashBook_Year =
            Integer.parseInt(request.getParameter("txtCB_Year"));
        int CashBook_Month =
            Integer.parseInt(request.getParameter("txtCB_Month"));
        //String AccHead_Code=request.getParameter("cmbAccHeadCode");


        //System.out.println("Account_Unit_Code is:"+Account_unit_Code);
        //System.out.println("Office_Code is:"+Office_Code);
        System.out.println("CashBook_Year is:" + CashBook_Year);
        System.out.println("CashBook CashBook_Month is:" + CashBook_Month);


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
            System.out.println("inside try...");
            reportFile =
                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Queries/jasper/GPFReport.jasper"));

            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            System.out.println("from ...");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();
            //map.put("acc_for_off_id",Office_Code);
            map.put("cashbookmonth", CashBook_Month);
            map.put("cashbookyear", CashBook_Year);
            map.put("monthvalue", monthInWords);


            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);

            System.out.println("upto");
            String rtype = "PDF"; // request.getParameter("cmbReportType");
            System.out.println(rtype);

            if (rtype.equalsIgnoreCase("PDF")) {
                System.out.println(rtype + "...inside PDF");

                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                //response.setContentType("application/force-download");

                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"Water_Charges_Collection_report.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            }


        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage(); //+ " uu " +  ex.getLocalizedMessage();
            System.out.println(connectMsg);
            //sendMessage(response,"The Challan Report Creation failed","ok");
        }
        /*  }
        else{
          receiptno="";
           receiptnonew=0;
            //xml=xml+"<flag>failure</flag>";
          //   reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/SalesTax_Return_report.jasper"));
        response.sendRedirect(request.getContextPath()+"/org/FAS/FAS1/Reports/ReceiptSystem/jsps/Sales_Tax_ReturnJSP.jsp?param=checknull");
         // PrintWriter out=response.getWriter();
          //out.println("<script type='text/javascript'> alert('No receipt no. for the corresponding Acc.Head')</script>");
           System.out.println("sales tax not available");
           //response.sendRedirect(request.getContextPath()+"/org/FAS/FAS1/Reports/ReceiptSystem/jsps/Sales_Tax_ReturnJSP.jsp?param=checknull");
        }*/


        //////////////////---------------------------- End -----------------
        System.out.println("here after PDF");
        //sendMessage(response,"The Trial Balance done successfully","ok");
        System.out.println("after send message");

        //response.sendRedirect(request.getContextPath()+"/org/FAS/FAS1/Reports/ReceiptSystem/jsps/Sales_Tax_ReturnJSP.jsp");

    }
}
