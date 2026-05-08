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

public class Sales_Tax_Return extends HttpServlet {
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

        String Account_unit_Code = request.getParameter("cmbAcc_UnitCode");
        String Office_Code = request.getParameter("cmbOffice_code");
        String CashBook_Year = request.getParameter("txtCB_Year");
        String CashBook_Month = request.getParameter("txtCB_Month");
        String AccHead_Code = request.getParameter("cmbAccHeadCode");


        System.out.println("Account_Unit_Code is:" + Account_unit_Code);
        System.out.println("Office_Code is:" + Office_Code);
        System.out.println("CashBook_Year is:" + CashBook_Year);
        System.out.println("CashBook Month is:" + CashBook_Month);
        //  System.out.println("Account Head is:"+AccHead_Code);
        //  System.out.println("Schedule Id is:"+Schedule);
        int AccountUnitCode = 0;
        int OfficeCode = 0;
        int CashBookYear = 0;
        int CashBookMonth = 0;
        int AccountHeadCode = 0;
        int recpno = 0;
        String doctype = "";
        double amt = 0;
        //java.sql.Date ReceiptDate=null;
        java.sql.Date VoucherDate = null;
        int receiptnonew = 0;
        int acchcode = 0;
        int recarr1[] = new int[100];
        int recarr2[] = new int[100];
        String remarks = "";
        String recno = "", recno1 = "";
        String receiptno = "", receiptno1 = "";
        int count = 0;
        int count1 = 0;
        StringBuffer sb = null;
        try {
            AccountUnitCode = Integer.parseInt(Account_unit_Code);
            OfficeCode = Integer.parseInt(Office_Code);
            CashBookYear = Integer.parseInt(CashBook_Year);
            CashBookMonth = Integer.parseInt(CashBook_Month);
            //AccountHeadCode=Integer.parseInt(AccHead_Code);

            System.out.println("Account_Unit_Code After is:" +
                               AccountUnitCode);
            System.out.println("Office_Code After is:" + OfficeCode);
            System.out.println("CashBook_Year After is:" + CashBookYear);
            System.out.println("CashBook Month After is:" + CashBookMonth);
            //System.out.println("Account Head Code is:"+AccountHeadCode);;

        } catch (Exception e) {
            System.out.println("Exception in Converting Integer:" + e);
        }
        String monthInWords = "";
        if (CashBookMonth == 1)
            monthInWords = "Jan";
        else if (CashBookMonth == 2)
            monthInWords = "Feb";
        else if (CashBookMonth == 3)
            monthInWords = "Mar";
        else if (CashBookMonth == 4)
            monthInWords = "Apr";
        else if (CashBookMonth == 5)
            monthInWords = "May";
        else if (CashBookMonth == 6)
            monthInWords = "Jun";
        else if (CashBookMonth == 7)
            monthInWords = "Jul";
        else if (CashBookMonth == 8)
            monthInWords = "Aug";
        else if (CashBookMonth == 9)
            monthInWords = "Sep";
        else if (CashBookMonth == 10)
            monthInWords = "Oct";
        else if (CashBookMonth == 11)
            monthInWords = "Nov";
        else if (CashBookMonth == 12)
            monthInWords = "Dec";
        File reportFile = null;
        /*
        try{
        String sqlquery="select receipt_no from fas_receipt_transaction where account_head_code " +
        "in (select account_head_code from fas_st_heads) and accounting_unit_id=? " +
        "and accounting_for_office_id=? and cashbook_year=? and cashbook_month=? order by receipt_no";


        ps=connection.prepareStatement(sqlquery);
        ps.setInt(1,AccountUnitCode);
        ps.setInt(2,OfficeCode);
        ps.setInt(3,CashBookYear);
        ps.setInt(4,CashBookMonth);
        results=ps.executeQuery();
        count=0;
        while(results.next()) {
            System.out.println("inside while");
            //recno=recno+results.getString("receipt_no")+",";
            recarr1[count]=Integer.parseInt(results.getString("receipt_no"));

            System.out.println("Receipt no is ::::::::::::"+recarr1[count]);
            count++;
            System.out.println("end of  while");
        }
            //receiptno=recno.substring(0,recno.length()-1);

             System.out.println("after while");
        }
        catch(Exception e) {
            System.out.println("no receipt no");
        }*/

        /*  try{

            System.out.println("!Account_Unit_Code After is:"+AccountUnitCode);
            System.out.println("!Office_Code After is:"+OfficeCode);
            System.out.println("!CashBook_Year After is:"+CashBookYear);
            System.out.println("!CashBook Month After is:"+CashBookMonth);
            String sqlquery1="select receipt_no from fas_receipt_transaction where account_head_code " +
            "in (550402) and accounting_unit_id=? " +
            "and accounting_for_office_id=? and cashbook_year=? and cashbook_month=? order by receipt_no ";
            ps1=connection.prepareStatement(sqlquery1);
            ps1.setInt(1,AccountUnitCode);
            ps1.setInt(2,OfficeCode);
            ps1.setInt(3,CashBookYear);
            ps1.setInt(4,CashBookMonth);
            results1=ps1.executeQuery();
            System.out.println("yes");
            count1=0;
            while(results1.next()) {
                System.out.println("aaaaa............."+results1.getString("receipt_no"));

                //recno1=recno1+results1.getString("receipt_no")+",";
                recarr2[count1]=Integer.parseInt(results1.getString("receipt_no"));
                System.out.println("Ans1:............."+recarr2[count1]);
                count1++;

            }
            System.out.println("Ans2:............."+recarr2[count1]);
            System.out.println("Ans3:............."+count1);
            System.out.println("no");

            }
            catch(Exception e) {
                System.out.println("Exception in second acc headcode..."+e);
            }

              sb=new StringBuffer();
            for(int i=0;i<=count;i++) {
                 for(int j=0;j<=count1;j++) {
                     System.out.println("1: "+recarr1[i]);
                     System.out.println("2: "+recarr2[j]);
                   if(recarr1[i]==recarr2[j]) {

                         sb.append(recarr1[i]);
                         System.out.println("comparing....");
                         sb.append(",");
                     System.out.println("sb is............."+sb.toString());
                     flag=true;
                     }

                   else  {
                   System.out.println("am here: no receipt no in the acchead550402");
                     //  System.out.println("sb is............."+sb.toString());
                   }

                 }
                 }
            str=sb.toString();


            System.out.println("Receipt no1 is ::::::::::::"+str);
            if(str.equals("")) {
             strnew="";
            }
            else
             strnew=str.substring(0,str.length()-1);*/

        // System.out.println("Receipt no1 is ::::::::::::"+strnew);
        //xml=xml+"<response>";

        // System.out.println("Checking -------"+receiptno.contains(receiptno1));
        //  if(flag)
        //  {
        //xml=xml+"<flag>success</flag>";
        //receiptno=receiptno1;
        //receiptnonew=Integer.parseInt(receiptno);
        //    reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/SalesTax_Return_report.jasper"));
        System.out.println("Receipt no is ::::::::::::" + strnew);
        try {
            System.out.println("calling servlet..." + monthInWords);
            reportFile =
                    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/SalesTax_Return_report.jasper"));

            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            System.out.println("from ...");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();
            map.put("cyear", CashBookYear);
            map.put("cmonth", CashBookMonth);
            map.put("accunitid", AccountUnitCode);
            //map.put("officecode",OfficeCode);
            map.put("monthinwords", monthInWords);
            //  map.put("recno",strnew);
            System.out.println("from .............");

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
                                   "attachment;filename=\"SalesTax_Return_report.pdf\"");
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
