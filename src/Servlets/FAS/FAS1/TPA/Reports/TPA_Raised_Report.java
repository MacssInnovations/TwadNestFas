package Servlets.FAS.FAS1.TPA.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.GregorianCalendar;
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
 * Servlet implementation class TPA_Raised_Report
 */
public class TPA_Raised_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/html; charset=windows-1252"; 
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TPA_Raised_Report() {
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
		 response.setContentType(CONTENT_TYPE);
	        String strCommand="";
	        Connection con=null;
	        PreparedStatement ps=null;
	        ResultSet rs=null;
	        Calendar c;
	        int cmbAcc_UnitCode=0,cmbOffice_code=0,cashYear=0,cashMonth=0;
	        Date challanDate=null;
	        String tpaType="";
	        HttpSession session=request.getSession(false);
	        try
	        {
	            
	            if(session==null)
	            {
	                System.out.println(request.getContextPath()+"/index.jsp");
	                response.sendRedirect(request.getContextPath()+"/index.jsp");
	                return;
	            }
	            System.out.println(session);
	                
	        }catch(Exception e)
	        {
	            System.out.println("Redirect Error :"+e);
	        } 
	        
	        try {
	                               ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
	                               String ConnectionString="";

	                               String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
	                               String strdsn=rs1.getString("Config.DSN");
	                               String strhostname=rs1.getString("Config.HOST_NAME");
	                               String strportno=rs1.getString("Config.PORT_NUMBER");
	                               String strsid=rs1.getString("Config.SID");
	                               String strdbusername=rs1.getString("Config.USER_NAME");
	                               String strdbpassword=rs1.getString("Config.PASSWORD");
	                               ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	                               Class.forName(strDriver.trim());
	                               con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	              }
	              catch(Exception e)
	                  {
	                     System.out.println("Exception in opening connection :"+e);
	                     //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

	                  }
	        try {
	        
	            strCommand=request.getParameter("Command");
	            System.out.println("assign..here command..."+strCommand);
	        }
	        
	        catch(Exception e) 
	        {
	            System.out.println("Exception in assigning..."+e);
	        }
	        
	        try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
	        catch(NumberFormatException e){System.out.println("exception"+e );}
	        System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
	        
	        try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
	        catch(NumberFormatException e){System.out.println("exception"+e );}
	        System.out.println("cmbOffice_code "+cmbOffice_code);
	        
	        try{cashYear=Integer.parseInt(request.getParameter("txtCB_Year"));}
	        catch(NumberFormatException e){System.out.println("exception"+e );}
	        System.out.println("cashYear "+cashYear);
	        
	        try{cashMonth=Integer.parseInt(request.getParameter("txtCB_Month"));}
	        catch(NumberFormatException e){System.out.println("exception"+e );}
	        System.out.println("cashMonth "+cashMonth);      
	        
	        tpaType=request.getParameter("proformatype");
	        
	        String reportName=request.getParameter("reportname");
	        
	        String monthInWords="";
	        if(cashMonth==1)
	            monthInWords="January";
	        else if(cashMonth==2)
	            monthInWords="February";
	        else if(cashMonth==3)
	            monthInWords="March";
	        else if(cashMonth==4)
	            monthInWords="April";
	        else if(cashMonth==5)
	            monthInWords="May";
	        else if(cashMonth==6)
	            monthInWords="June";
	        else if(cashMonth==7)
	            monthInWords="July";
	        else if(cashMonth==8)
	            monthInWords="August";
	        else if(cashMonth==9)
	            monthInWords="September";
	        else if(cashMonth==10)
	        	monthInWords="October";
	        else if(cashMonth==11)
	            monthInWords="November";
	        else if(cashMonth==12)
	            monthInWords="December";
	        
	        
	       if(reportName.equalsIgnoreCase("RaisedReport")) 
	       {
	        File reportFile=null;
	        try
	        {
	        System.out.println("calling servlet...");
	                                                    // if condition used to identify the specific jasper file for (Cach/Bank)
	       
	            reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TPA/jasper/TPA_Raised.jasper"));
	      
	        
	        if (!reportFile.exists())
	        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	        
	        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
	        Map map=null;
	        map = new HashMap();
	        
	            map.put("unitid",cmbAcc_UnitCode);
	            map.put("officeid",cmbOffice_code);
	            map.put("cashyear",cashYear);
	            map.put("cashmonth",cashMonth);
	            map.put("tpatype",tpaType);
	            map.put("monthwords",monthInWords);
	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map,con);
	            
	            System.out.println("upto");
	        String rtype="PDF";// request.getParameter("cmbReportType");
	        rtype=request.getParameter("txtoption");
	        System.out.println(rtype);
	        if (rtype.equalsIgnoreCase("HTML"))   
	        {
	                    response.setContentType("text/html");
	                    
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TPA-Raised.html\"");
	                    PrintWriter out = response.getWriter();
	                    JRHtmlExporter exporter = new JRHtmlExporter();
	                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
	                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	                    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
	                    exporter.exportReport();
	                    out.flush();
	                    out.close();
	        }
	        else if (rtype.equalsIgnoreCase("PDF"))   
	        {       System.out.println(rtype+"...inside PDF");
	                    byte buf[] = 
	                      JasperExportManager.exportReportToPdf(jasperPrint);
	                    response.setContentType("application/pdf");
	                    response.setContentLength(buf.length);
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TPA-Raised.pdf\"");
	                    OutputStream out = response.getOutputStream();
	                    out.write(buf, 0, buf.length);
	                    out.close();
	        }
	        else if (rtype.equalsIgnoreCase("EXCEL"))   
	        {
	        
	                response.setContentType("application/vnd.ms-excel");
	                 response.setHeader ("Content-Disposition", "attachment;filename=\"TPA-Raised.xls\"");
	                 JRXlsExporter exporterXLS = new JRXlsExporter(); 
	                 exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
	                 
	                ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
	                 exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
	                 exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
	                 exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
	                 exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
	                 exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
	                 exporterXLS.exportReport(); 
	                 byte []bytes;
	                bytes = xlsReport.toByteArray();
	                ServletOutputStream ouputStream = response.getOutputStream();
	                ouputStream.write(bytes, 0, bytes.length);
	                ouputStream.flush();
	                ouputStream.close();
	        
	        }
	        else if (rtype.equalsIgnoreCase("TXT"))   
	        {
	        
	                response.setContentType("text/plain");
	                 response.setHeader ("Content-Disposition", "attachment;filename=\"TPA-Raised.txt\"");
	                 
	            JRTextExporter exporter = new JRTextExporter();
	            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	            ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
	            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,txtReport); 
	            exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Integer(200));
	            exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Integer(50));
	            exporter.exportReport(); 
	            
	                 byte []bytes;
	                bytes = txtReport.toByteArray();
	                ServletOutputStream ouputStream = response.getOutputStream();
	                ouputStream.write(bytes, 0, bytes.length);
	                ouputStream.flush();
	                ouputStream.close();
	        
	        }
	        
	        } 
	        catch (Exception ex) 
	        {
	        String connectMsg = "Could not create the report " + ex.getMessage();//+ " uu " +  ex.getLocalizedMessage();
	        System.out.println(connectMsg);
	        //sendMessage(response,"The Challan Report Creation failed","ok");
	        }
	       }
	       else if(reportName.equalsIgnoreCase("AcceptedReport")) 
	       {
	        File reportFile=null;
	        try
	        {
	        System.out.println("calling servlet...");
	                                                    // if condition used to identify the specific jasper file for (Cach/Bank)
	       
	            reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TPA/jasper/TPA_Accepted_List.jasper"));
	      
	        
	        if (!reportFile.exists())
	        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	        
	        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
	        Map map=null;
	        map = new HashMap();
	        
	            map.put("unitid",cmbAcc_UnitCode);
	            map.put("officeid",cmbOffice_code);
	            map.put("cashyear",cashYear);
	            map.put("cashmonth",cashMonth);
	            map.put("tpatype",tpaType);
	            map.put("monthwords",monthInWords);
	            
	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map,con);
	            
	            System.out.println("upto");
	        String rtype="PDF";// request.getParameter("cmbReportType");
	        rtype=request.getParameter("txtoption");
	        System.out.println(rtype);
	        if (rtype.equalsIgnoreCase("HTML"))   
	        {
	                    response.setContentType("text/html");
	                    
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TPA-Accepted.html\"");
	                    PrintWriter out = response.getWriter();
	                    JRHtmlExporter exporter = new JRHtmlExporter();
	                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
	                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	                    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
	                    exporter.exportReport();
	                    out.flush();
	                    out.close();
	        }
	        else if (rtype.equalsIgnoreCase("PDF"))   
	        {       System.out.println(rtype+"...inside PDF");
	                    byte buf[] = 
	                      JasperExportManager.exportReportToPdf(jasperPrint);
	                    response.setContentType("application/pdf");
	                    response.setContentLength(buf.length);
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TPA-Accepted.pdf\"");
	                    OutputStream out = response.getOutputStream();
	                    out.write(buf, 0, buf.length);
	                    out.close();
	        }
	        else if (rtype.equalsIgnoreCase("EXCEL"))   
	        {
	        
	                response.setContentType("application/vnd.ms-excel");
	                 response.setHeader ("Content-Disposition", "attachment;filename=\"TPA-Accepted.xls\"");
	                 JRXlsExporter exporterXLS = new JRXlsExporter(); 
	                 exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
	                 
	                ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
	                 exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
	                 exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
	                 exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
	                 exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
	                 exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
	                 exporterXLS.exportReport(); 
	                 byte []bytes;
	                bytes = xlsReport.toByteArray();
	                ServletOutputStream ouputStream = response.getOutputStream();
	                ouputStream.write(bytes, 0, bytes.length);
	                ouputStream.flush();
	                ouputStream.close();
	        
	        }
	        else if (rtype.equalsIgnoreCase("TXT"))   
	        {
	        
	                response.setContentType("text/plain");
	                 response.setHeader ("Content-Disposition", "attachment;filename=\"TPA-Accepted.txt\"");
	                 
	            JRTextExporter exporter = new JRTextExporter();
	            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	            ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
	            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,txtReport); 
	            exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Integer(200));
	            exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Integer(50));
	            exporter.exportReport(); 
	            
	                 byte []bytes;
	                bytes = txtReport.toByteArray();
	                ServletOutputStream ouputStream = response.getOutputStream();
	                ouputStream.write(bytes, 0, bytes.length);
	                ouputStream.flush();
	                ouputStream.close();
	        
	        }
	        
	        } 
	        catch (Exception ex) 
	        {
	        String connectMsg = "Could not create the report " + ex.getMessage();//+ " uu " +  ex.getLocalizedMessage();
	        System.out.println(connectMsg);
	        //sendMessage(response,"The Challan Report Creation failed","ok");
	        }
	       }
	       
	       else if(reportName.equalsIgnoreCase("PendingReport")) 
	       {
	        File reportFile=null;
	        try
	        {
	        System.out.println("calling servlet...");
	                                                    // if condition used to identify the specific jasper file for (Cach/Bank)
	       
	            reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TPA/jasper/TPA_Pending_List.jasper"));
	      
	        
	        if (!reportFile.exists())
	        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	        
	        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
	        Map map=null;
	        map = new HashMap();
	        
	            map.put("unitid",cmbAcc_UnitCode);
	            map.put("officeid",cmbOffice_code);
	            map.put("cashyear",cashYear);
	            map.put("cashmonth",cashMonth);
	            map.put("tpatype",tpaType);
	            map.put("monthwords",monthInWords);
	            
	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map,con);
	            
	            System.out.println("upto");
	        String rtype="PDF";// request.getParameter("cmbReportType");
	        rtype=request.getParameter("txtoption");
	        System.out.println(rtype);
	        if (rtype.equalsIgnoreCase("HTML"))   
	        {
	                    response.setContentType("text/html");
	                    
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TPA-Pending.html\"");
	                    PrintWriter out = response.getWriter();
	                    JRHtmlExporter exporter = new JRHtmlExporter();
	                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
	                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	                    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
	                    exporter.exportReport();
	                    out.flush();
	                    out.close();
	        }
	        else if (rtype.equalsIgnoreCase("PDF"))   
	        {       System.out.println(rtype+"...inside PDF");
	                    byte buf[] = 
	                      JasperExportManager.exportReportToPdf(jasperPrint);
	                    response.setContentType("application/pdf");
	                    response.setContentLength(buf.length);
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TPA-Pending.pdf\"");
	                    OutputStream out = response.getOutputStream();
	                    out.write(buf, 0, buf.length);
	                    out.close();
	        }
	        else if (rtype.equalsIgnoreCase("EXCEL"))   
	        {
	        
	                response.setContentType("application/vnd.ms-excel");
	                 response.setHeader ("Content-Disposition", "attachment;filename=\"TPA-Pending.xls\"");
	                 JRXlsExporter exporterXLS = new JRXlsExporter(); 
	                 exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
	                 
	                ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
	                 exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
	                 exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
	                 exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
	                 exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
	                 exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
	                 exporterXLS.exportReport(); 
	                 byte []bytes;
	                bytes = xlsReport.toByteArray();
	                ServletOutputStream ouputStream = response.getOutputStream();
	                ouputStream.write(bytes, 0, bytes.length);
	                ouputStream.flush();
	                ouputStream.close();
	        
	        }
	        else if (rtype.equalsIgnoreCase("TXT"))   
	        {
	        
	                response.setContentType("text/plain");
	                 response.setHeader ("Content-Disposition", "attachment;filename=\"TPA-Accepted.txt\"");
	                 
	            JRTextExporter exporter = new JRTextExporter();
	            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	            ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
	            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,txtReport); 
	            exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Integer(200));
	            exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Integer(50));
	            exporter.exportReport(); 
	            
	                 byte []bytes;
	                bytes = txtReport.toByteArray();
	                ServletOutputStream ouputStream = response.getOutputStream();
	                ouputStream.write(bytes, 0, bytes.length);
	                ouputStream.flush();
	                ouputStream.close();
	        
	        }
	        
	        } 
	        catch (Exception ex) 
	        {
	        String connectMsg = "Could not create the report " + ex.getMessage();//+ " uu " +  ex.getLocalizedMessage();
	        System.out.println(connectMsg);
	        //sendMessage(response,"The Challan Report Creation failed","ok");
	        }
	       }
	       else if(reportName.equalsIgnoreCase("TPAALL")) 
	       {
	        File reportFile=null;
	        try
	        {
	        	
	        	String finYear="";
	        	
	        	if(cashMonth<4)
	        	{
	        		finYear=(cashYear-1)+"-"+(cashYear);
	        	}else{
	        		finYear=(cashYear)+"-"+(cashYear+1);
	        	}
	        	
	        System.out.println("calling servlet...");
	                                                    // if condition used to identify the specific jasper file for (Cach/Bank)
	       
	            reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TPA/jasper/TPA_ALL.jasper"));
	      
	        
	        if (!reportFile.exists())
	        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	        
	        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
	        Map map=null;
	        map = new HashMap();
	        
	            map.put("unitid",cmbAcc_UnitCode);
	            map.put("officeid",cmbOffice_code);
	            map.put("cashyear",cashYear);
	            map.put("cashmonth",cashMonth);
	            map.put("tpatype",tpaType);
	            map.put("monthwords",monthInWords);
	            map.put("finyear",finYear);
	            
	            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map,con);
	            
	            System.out.println("upto");
	        String rtype="PDF";// request.getParameter("cmbReportType");
	        rtype=request.getParameter("txtoption");
	        System.out.println(rtype);
	        if (rtype.equalsIgnoreCase("HTML"))   
	        {
	                    response.setContentType("text/html");
	                    
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TPA-All.html\"");
	                    PrintWriter out = response.getWriter();
	                    JRHtmlExporter exporter = new JRHtmlExporter();
	                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
	                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	                    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
	                    exporter.exportReport();
	                    out.flush();
	                    out.close();
	        }
	        else if (rtype.equalsIgnoreCase("PDF"))   
	        {       System.out.println(rtype+"...inside PDF");
	                    byte buf[] = 
	                      JasperExportManager.exportReportToPdf(jasperPrint);
	                    response.setContentType("application/pdf");
	                    response.setContentLength(buf.length);
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TPA-All.pdf\"");
	                    OutputStream out = response.getOutputStream();
	                    out.write(buf, 0, buf.length);
	                    out.close();
	        }
	        else if (rtype.equalsIgnoreCase("EXCEL"))   
	        {
	        
	                response.setContentType("application/vnd.ms-excel");
	                 response.setHeader ("Content-Disposition", "attachment;filename=\"TPA-All.xls\"");
	                 JRXlsExporter exporterXLS = new JRXlsExporter(); 
	                 exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
	                 
	                ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
	                 exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
	                 exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
	                 exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
	                 exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
	                 exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
	                 exporterXLS.exportReport(); 
	                 byte []bytes;
	                bytes = xlsReport.toByteArray();
	                ServletOutputStream ouputStream = response.getOutputStream();
	                ouputStream.write(bytes, 0, bytes.length);
	                ouputStream.flush();
	                ouputStream.close();
	        
	        }
	        else if (rtype.equalsIgnoreCase("TXT"))   
	        {
	        
	                response.setContentType("text/plain");
	                 response.setHeader ("Content-Disposition", "attachment;filename=\"TPA-All.txt\"");
	                 
	            JRTextExporter exporter = new JRTextExporter();
	            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	            ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
	            exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,txtReport); 
	            exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Integer(200));
	            exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Integer(50));
	            exporter.exportReport(); 
	            
	                 byte []bytes;
	                bytes = txtReport.toByteArray();
	                ServletOutputStream ouputStream = response.getOutputStream();
	                ouputStream.write(bytes, 0, bytes.length);
	                ouputStream.flush();
	                ouputStream.close();
	        
	        }
	        
	        } 
	        catch (Exception ex) 
	        {
	        String connectMsg = "Could not create the report " + ex.getMessage();//+ " uu " +  ex.getLocalizedMessage();
	        System.out.println(connectMsg);
	        //sendMessage(response,"The Challan Report Creation failed","ok");
	        }
	       }
	       
	       
	        System.out.println("TPA successfully printed");
	    }
	

}
