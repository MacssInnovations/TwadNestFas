package Servlets.FAS.FAS1.NRDWP.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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

/**
 * Servlet implementation class ReportList
 */
public class ReportList extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ReportList() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("ReportList servlet call ");
            Connection connection = null;
            Statement statement = null;
            CallableStatement cs = null;

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
            // PrintWriter out = response.getWriter();
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

            String cmb_unit_name="";
            String Account_unit_Code = request.getParameter("cmbAcc_UnitCode");
            
            try{
            	String qry="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID="+Account_unit_Code;
            PreparedStatement p=connection.prepareStatement(qry);
            ResultSet r=p.executeQuery();
            while(r.next()){
            	cmb_unit_name=r.getString("ACCOUNTING_UNIT_NAME");
            }
            	
            }
            catch(Exception e){
            	e.printStackTrace();
            }
            String CashBook_Year = request.getParameter("txtCB_Year");
            String CashBook_Month = request.getParameter("txtCB_Month");


            int AccountUnitCode = 0;
            int OfficeCode = 0;
            int CashBookYear = 0;
            int CashBookMonth = 0;

            String update_user = (String)session.getAttribute("UserId");
            try {
                AccountUnitCode = Integer.parseInt(Account_unit_Code);
                //  OfficeCode=Integer.parseInt(Office_Code);
                CashBookYear = Integer.parseInt(CashBook_Year);
                CashBookMonth = Integer.parseInt(CashBook_Month);


            } catch (Exception e) {
                System.out.println("Exception in Converting Integer:" + e);
            }
            //This Two Variables for calculateting cashbookmonth and year

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

String cmbDocType=request.getParameter("cmbDocType");



        try {

            File reportFile = null;
            try {
                System.out.println("calling servlet..."+cmbDocType);
                if(cmbDocType.equalsIgnoreCase("J")){
                reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ReportList_jour.jasper"));
                }  if(cmbDocType.equalsIgnoreCase("R")){
                reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ReportList_Rec.jasper"));
                }  if(cmbDocType.equalsIgnoreCase("P")){
                reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/ReportList_Pay.jasper"));
                }
                
                if (!reportFile.exists())
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                System.out.println("from ...");
                JasperReport jasperReport =
                    (JasperReport)JRLoader.loadObject(reportFile.getPath());
                Map map = null;
                map = new HashMap();

          

                map.put("to_cashbookyear", CashBookYear);
                map.put("to_cashbookmonth", CashBookMonth);

                map.put("accountingunitid", AccountUnitCode);
                map.put("cmb_unit_name", cmb_unit_name);
       

                map.put("monthInWords", monthInWords);


                JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperReport, map,
                                                 connection);

               // System.out.println("upto");
                String rtype = "PDF"; // request.getParameter("cmbReportType");
                System.out.println(rtype);
                if (rtype.equalsIgnoreCase("HTML")) {
                    response.setContentType("text/html");

                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Challan.html\"");
                    PrintWriter out = response.getWriter();
                    JRHtmlExporter exporter = new JRHtmlExporter();
                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
                                          false);
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                          jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER,
                                          out);
                    exporter.exportReport();
                    out.flush();
                    out.close();
                } else if (rtype.equalsIgnoreCase("PDF")) {
                  //  System.out.println(rtype + "...inside PDF");
                    byte buf[] =
                        JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    //response.setHeader ("Content-Disposition", "attachment;filename=\"Challan.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
                } else if (rtype.equalsIgnoreCase("EXCEL")) {

                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Challan.xls\"");
                    JRXlsExporter exporterXLS = new JRXlsExporter();
                    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                                             jasperPrint);

                    ByteArrayOutputStream xlsReport =
                        new ByteArrayOutputStream();
                    exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
                                             xlsReport);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
                                             Boolean.FALSE);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,
                                             Boolean.TRUE);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
                                             Boolean.FALSE);
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                                             Boolean.TRUE);
                    exporterXLS.exportReport();
                    byte[] bytes;
                    bytes = xlsReport.toByteArray();
                    ServletOutputStream ouputStream =
                        response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();

                } else if (rtype.equalsIgnoreCase("TXT")) {

                    response.setContentType("text/plain");
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Challan.txt\"");

                    JRTextExporter exporter = new JRTextExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                          jasperPrint);
                    ByteArrayOutputStream txtReport =
                        new ByteArrayOutputStream();
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                          txtReport);
                    exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,
                                          new Integer(200));
                    exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,
                                          new Integer(50));
                    exporter.exportReport();

                    byte[] bytes;
                    bytes = txtReport.toByteArray();
                    ServletOutputStream ouputStream =
                        response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();

                }

            } catch (Exception ex) {
                String connectMsg =
                    "Could not create the report " + ex.getMessage(); //+ " uu " +  ex.getLocalizedMessage();
                System.out.println(connectMsg);
                //sendMessage(response,"The Challan Report Creation failed","ok");
           
            }
            //////////////////---------------------------- End -----------------
          //  System.out.println("here after PDF");
            //sendMessage(response,"The Trial Balance done successfully","ok");
           // System.out.println("after send message");


        } catch (Exception e) {
            System.out.println("Exception in Main:" + e);
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println("catch:" + e1);
            }
            String msg = "Trial Balance Has failed to Update";
            msg = msg + "<br><br>";
         

        }
		
		
		
	

}
}
