package Servlets.FAS.FAS1.Masters.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

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
import java.math.BigDecimal;
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

/**
 * Servlet implementation class A52Register_OBEntry_Abstract
 */
public class A52Register_OBEntry_Abstract extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE ="text/html; charset=windows-1252";
    Connection connection = null;

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
    /**
     * @see HttpServlet#HttpServlet()
     */
    public A52Register_OBEntry_Abstract() {
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
		// TODO Auto-generated method stub
		doGet(request, response);

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

        String selstr = "";
        String selspestr = "";
        String sel = "";
        String opt = "";
        response.setContentType(CONTENT_TYPE);
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
        JasperDesign jasperDesign = null;
        File reportFile = null;
        try {
        	int unit_rendered=0,officeiddd=0;
        	String units="";
            System.out.println("*****calling A52Register_OBEntry Abstract servlet*****");
            int AccUnitCode =
                Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
           // int AccOfficeCode =
             //   Integer.parseInt(request.getParameter("cmbOffice_code"));
            //int asset_majclass_code = Integer.parseInt(request.getParameter("cmbasset"));
            
          String check=request.getParameter("unit_rendered");
	        String check1=request.getParameter("allid");
	        String officelevel="";
	        int officeidd=0;
	        String circleQry="";
	        String unitName="";
	        
	        if(check1.equalsIgnoreCase("All"))
			{	
				//units="";
				//System.out.println("check..in if ."+units+check1);
				
	        	
	        	String ss="SELECT cc1.Accounting_Unit_Name  from fas_mst_acct_units cc1  where cc1.ACCOUNTING_UNIT_ID="+AccUnitCode;
	       	 PreparedStatement ps11=connection.prepareStatement(ss);
	           ResultSet rs11=ps11.executeQuery();
	           while(rs11.next()){
	        	   unitName=rs11.getString("Accounting_Unit_Name");
	           }
	        	
	        	String sqry="select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID="+AccUnitCode;
           	 PreparedStatement ps=connection.prepareStatement(sqry);
		           ResultSet rs=ps.executeQuery();
		           while(rs.next()){
		        	   officeidd=rs.getInt("ACCOUNTING_UNIT_OFFICE_ID");
		           }
           	//System.out.println("officeidd "+officeidd);
           	String selectQry="select office_level_id from com_mst_all_offices_view  where office_id="+officeidd;
		           PreparedStatement ps1=connection.prepareStatement(selectQry);
		           ResultSet rs1=ps1.executeQuery();
		           while(rs1.next()){
		        	   officelevel=rs1.getString("office_level_id");
		           }
		           System.out.println(" officelevel "+officelevel);
		           
		           if(officelevel.equalsIgnoreCase("CL")){
		           circleQry=" ACCOUNTING_UNIT_ID = "+AccUnitCode+" AND ";
		           
		           }
		           else if(officelevel.equalsIgnoreCase("HO")){
		        	   
			           circleQry=" ACCOUNTING_UNIT_ID = "+AccUnitCode+" AND ";
			           }else if(officelevel.equalsIgnoreCase("RN")){
			        	   
			           circleQry=" ACCOUNTING_UNIT_ID = "+AccUnitCode+" AND  ";
			           }
		           
		           else{
		        	   
		        	   circleQry=" accounting_unit_office_id="+officeidd+" AND ";
		           }
				 
			}
			else
			{	
				 String[] unitsoffice=check.split("-");
		           //unit_rendered=Integer.parseInt(check);      
		           unit_rendered=Integer.parseInt(unitsoffice[0]); 
		           officeiddd=Integer.parseInt(unitsoffice[1]);
		           

		        	String ss="SELECT cc1.Accounting_Unit_Name  from fas_mst_acct_units cc1  where cc1.ACCOUNTING_UNIT_OFFICE_ID="+officeiddd;
		       	 PreparedStatement ps11=connection.prepareStatement(ss);
		           ResultSet rs11=ps11.executeQuery();
		           while(rs11.next()){
		        	   unitName=rs11.getString("Accounting_Unit_Name");
		           }
		        	
		           
		           System.out.println("unit_rendered "+unit_rendered);
		           String sqry="select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID="+unit_rendered;
		           	 PreparedStatement ps=connection.prepareStatement(sqry);
				           ResultSet rs=ps.executeQuery();
				           while(rs.next()){
				        	   officeidd=rs.getInt("ACCOUNTING_UNIT_OFFICE_ID");
				           }
		           	//System.out.println("officeidd "+officeidd);
		           	String selectQry="select office_level_id from com_mst_all_offices_view  where office_id="+officeidd;
				           PreparedStatement ps1=connection.prepareStatement(selectQry);
				           ResultSet rs1=ps1.executeQuery();
				           while(rs1.next()){
				        	   officelevel=rs1.getString("office_level_id");
				           }
				           System.out.println(" officelevel "+officelevel);
				           if(officelevel.equalsIgnoreCase("CL")){
				        	   circleQry=" accounting_unit_office_id="+officeiddd+" AND ";
				           }
				           else if(officelevel.equalsIgnoreCase("HO")){
				        	   circleQry=" accounting_unit_office_id="+officeiddd+" AND ";
					           }else if(officelevel.equalsIgnoreCase("RN")){
					        	   circleQry=" accounting_unit_office_id="+officeiddd+" AND ";
					           }
				           
				           else{
				        	   circleQry=" accounting_unit_office_id="+officeidd+" AND ";
				           }
		           
		           
		          
		           
			}
            
            System.out.println("circleQry "+circleQry);
            System.out.println("unitName "+unitName);
            String finyear =request.getParameter("cmbFinancialYear");
            String rtype = request.getParameter("txtoption");
            System.out.println("cmbAcc_UnitCode******"+AccUnitCode+"finyear*******"+finyear);
           
           reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Masters/jasper/A52RegisterOBDataEntry_Abs.jasper"));
                  
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());


            Map map = new HashMap();

            map.put("accunitid", AccUnitCode);
            map.put("financialyear", finyear);
            map.put("circleQry",circleQry);
            map.put("unitName",unitName);
            //map.put("assetmajclass", asset_majclass_code);
         
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);


            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"A52RegisterOBEntryAbs.html\"");
                PrintWriter out = response.getWriter();
                JRHtmlExporter exporter = new JRHtmlExporter();
                exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
                                      false);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                      jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
                exporter.exportReport();
                out.flush();
                out.close();
            } else if (rtype.equalsIgnoreCase("PDF")) {
            	System.out.println("the option chosen is :::::"+rtype);
                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"A52RegisterOBEntryAbs.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                System.out.println("testing***"+jasperPrint);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"A52RegisterOBEntryAbs.xls\"");
                JRXlsExporter exporterXLS = new JRXlsExporter();
                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                                         jasperPrint);

                ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
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
                ServletOutputStream ouputStream = response.getOutputStream();
                ouputStream.write(bytes, 0, bytes.length);
                ouputStream.flush();
                ouputStream.close();

            } else if (rtype.equalsIgnoreCase("TXT")) {

                response.setContentType("text/plain");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"A52RegisterOBEntryAbs.txt\"");

                JRTextExporter exporter = new JRTextExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                      jasperPrint);
                ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                      txtReport);
                exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,
                                      new Integer(200));
                exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,
                                      new Integer(50));
                exporter.exportReport();

                byte[] bytes;
                bytes = txtReport.toByteArray();
                ServletOutputStream ouputStream = response.getOutputStream();
                ouputStream.write(bytes, 0, bytes.length);
                ouputStream.flush();
                ouputStream.close();

            }

        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }

	}

}
