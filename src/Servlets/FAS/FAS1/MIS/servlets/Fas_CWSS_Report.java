package Servlets.FAS.FAS1.MIS.servlets;

import java.io.ByteArrayOutputStream;
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
import java.sql.Types;
import java.text.DateFormat;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.lowagie.text.RomanList;

import jxl.biff.Type;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class Annual_acc_Report
 */
public class Fas_CWSS_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
     private static  String CONTENT_TYPE = 
            "text/xml; charset=windows-1252";
        Connection connection = null;
    public Fas_CWSS_Report() {
        super();
        // TODO Auto-generated constructor stub
    }

    
        
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	//System.out.println("Servlet Page ... ");
    	CallableStatement stmt=null; 
    	Connection connection=null;
    	PreparedStatement ps=null;
    	ResultSet rset=null;
    	//PrintWriter out=response.getWriter();
        try
        {
                
        HttpSession session=request.getSession(false);
        if(session==null)
        {
            System.out.println(request.getContextPath()+"/index.jsp");
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        
        }
        System.out.println(session);
            
        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        }
        
        String opt="",Report_For="";
        int errcode=0;
        response.setContentType(CONTENT_TYPE);
        try
        {
        ResourceBundle rs = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
        String ConnectionString = "";
        
        String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
        String strdsn = rs.getString("Config.DSN");
        String strhostname = rs.getString("Config.HOST_NAME");
        String strportno = rs.getString("Config.PORT_NUMBER");
        String strsid = rs.getString("Config.SID");
        String strdbusername = rs.getString("Config.USER_NAME");
        String strdbpassword = rs.getString("Config.PASSWORD");
        ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection        Class.forName(strDriver.trim());
        connection =DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
        } catch (Exception ex) {
        String connectMsg ="Could not create the connection" + ex.getMessage() + " " + 
        ex.getLocalizedMessage();
        //System.out.println(connectMsg);
        }
        JasperDesign jasperDesign = null;
        File reportFile=null;
        int from_year=0;
        String head="";
        int To_year=0;
        int from_Month=0;
        int To_Month=0,sup_no=0,sup_no1=0,unit_id=0;
        String  command="",fin_year="",sup_qry="",sup_query="",f_year="",Str_qry="",xml="";
        String fin_FY="",fin_TY="",f_mnth="",t_mnth="",qry="",unit_qry="";
       int chk_cons=0;
        String year[]=null,a[]=null,b[]=null; 
         command=request.getParameter("cmd");
        String Re_Type="",NRD_type="";
        String liveFY=request.getParameter("liveFY");
        String yaer=liveFY.split("-")[0]+"-"+liveFY.split("-")[1].substring(2);
         Re_Type=request.getParameter("rad_R");
         String  head1="WATER CHARGES COLLECTED AND EXPENDITURE INCURRED ON BOARD OWNED W.S.S FOR  "+yaer+" (ACCRUAL BASIS)";
          head="Schedule 1.2.1";
	     try{   


	    	 System.out.println(Re_Type+">>> .... "+qry);
	    	try{
	    		if(Re_Type.equalsIgnoreCase("EXCEL")){
		    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/twad_TB/fas_WSS_Report_cp.jasper"));	    			
	    		}else{
	    			  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/twad_TB/fas_WSS_Report.jasper"));
	    		}
	    		  if (!reportFile.exists())
	    		        throw new JRRuntimeException("File J not found. The report design must be compiled first.");            
	    		    
	    		    JasperReport jasperReport =(JasperReport)JRLoader.loadObject(reportFile.getPath());
	    		    
	    		    Map map = null;
	    		    map = new HashMap();
	    		    map.put("year", liveFY);
	    		    map.put("head",head);
	    		    map.put("head1", head1);
	    		 //   System.out.println("Map va;ue  ... "+map);
	    		    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);   
	    		  //
	    			//
	    			
	    	        if (Re_Type.equalsIgnoreCase("HTML"))   
	    	        {
	    	            System.out.println("HTML :::::::::::");
	    	                    response.setContentType("text/html");
	    	                    response.setHeader ("Content-Disposition", "attachment;filename=\"Water Charge collected Report.html\"");
	    	                    PrintWriter out = response.getWriter();
	    	                    JRHtmlExporter exporter = new JRHtmlExporter();
	    	                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
	    	                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	    	                    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
	    	                    exporter.exportReport();
	    	                    out.flush();
	    	                    out.close();
	    	        }   else   if (Re_Type.equalsIgnoreCase("EXCEL"))   
	    	        {
	    	        	System.out.println("Excel .... ");
	    	                        response.setContentType("application/vnd.ms-excel");
	    	                    response.setHeader ("Content-Disposition", "attachment;filename=\"Water Charge collected Report.xls\"");
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
	    	        else if (Re_Type.equalsIgnoreCase("PDF"))   
	    	        {
	    	                System.out.println("PDF:::::::::::");
	    	                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
	    	                    response.setContentType("application/pdf");
	    	                    response.setContentLength(buf.length);
	    	                    response.setHeader ("Content-Disposition", "attachment;filename=\"Water Charge collected Report.pdf\"");
	    	                    OutputStream out = response.getOutputStream();
	    	                    out.write(buf, 0, buf.length);
	    	                    out.close();
	    	        }
	    		}  catch (Exception ex) {
	    		    String connectMsg ="Could not create the report " + ex.getMessage(); 
	    		    String con_err ="Could not create the report " + ex; 
	    		  //  System.out.println(con_err);
	    		    System.out.println(connectMsg);
	    		}
	      }
	  
	  
	  catch (Exception e) 
      {
         			e.printStackTrace();
		}
         
      
      	
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        
    	//System.out.println("Servlet Page ... ");
    	CallableStatement stmt=null; 
    	Connection connection=null;
    	PreparedStatement ps=null;
    	ResultSet rset=null;
    	//PrintWriter out=response.getWriter();
        try
        {
                
        HttpSession session=request.getSession(false);
        if(session==null)
        {
            System.out.println(request.getContextPath()+"/index.jsp");
            response.sendRedirect(request.getContextPath()+"/index.jsp");
        
        }
        System.out.println(session);
            
        }catch(Exception e)
        {
        System.out.println("Redirect Error :"+e);
        }
        
        String opt="",Report_For="";
        int errcode=0;
        response.setContentType(CONTENT_TYPE);
        try
        {
        ResourceBundle rs = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
        String ConnectionString = "";
        
        String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
        String strdsn = rs.getString("Config.DSN");
        String strhostname = rs.getString("Config.HOST_NAME");
        String strportno = rs.getString("Config.PORT_NUMBER");
        String strsid = rs.getString("Config.SID");
        String strdbusername = rs.getString("Config.USER_NAME");
        String strdbpassword = rs.getString("Config.PASSWORD");
        ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection        Class.forName(strDriver.trim());
        connection =DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
        } catch (Exception ex) {
        String connectMsg ="Could not create the connection" + ex.getMessage() + " " + 
        ex.getLocalizedMessage();
        //System.out.println(connectMsg);
        }
        JasperDesign jasperDesign = null;
        File reportFile=null;
        int from_year=0;
        String head="";
        int To_year=0;
        int from_Month=0;
        int To_Month=0,sup_no=0,sup_no1=0,unit_id=0;
        String  command="",fin_year="",sup_qry="",sup_query="",f_year="",Str_qry="",xml="";
        String fin_FY="",fin_TY="",f_mnth="",t_mnth="",qry="",unit_qry="";
       int chk_cons=0;
        String year[]=null,a[]=null,b[]=null; 
         command=request.getParameter("cmd");
        String Re_Type="",NRD_type="";
        String liveFY=request.getParameter("liveFY");
        String yaer=liveFY.split("-")[0]+"-"+liveFY.split("-")[1].substring(2);
         Re_Type=request.getParameter("rad_R");
         String  head1="Demand Raised For Charges And Expenditure Incurred On CWSS In "+yaer;
          head="Schedule 1.2.2";
	     try{   


	    	 System.out.println(Re_Type+">>> .... "+qry);
	    	try{
	    		if(Re_Type.equalsIgnoreCase("EXCEL")){
		    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/twad_TB/fas_CWSS_Report_cp.jasper"));	    			
	    		}else{
	    			  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/twad_TB/fas_CWSS_Report.jasper"));
	    		}
	    		  if (!reportFile.exists())
	    		        throw new JRRuntimeException("File J not found. The report design must be compiled first.");            
	    		    
	    		    JasperReport jasperReport =(JasperReport)JRLoader.loadObject(reportFile.getPath());
	    		    
	    		    Map map = null;
	    		    map = new HashMap();
	    		    map.put("year", liveFY);
	    		    map.put("head",head);
	    		    map.put("head1", head1);
	    		 //   System.out.println("Map va;ue  ... "+map);
	    		    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);   
	    		  //
	    			//
	    			
	    	        if (Re_Type.equalsIgnoreCase("HTML"))   
	    	        {
	    	            System.out.println("HTML :::::::::::");
	    	                    response.setContentType("text/html");
	    	                    response.setHeader ("Content-Disposition", "attachment;filename=\"Water Charge Raised Cwss Report.html\"");
	    	                    PrintWriter out = response.getWriter();
	    	                    JRHtmlExporter exporter = new JRHtmlExporter();
	    	                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
	    	                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	    	                    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
	    	                    exporter.exportReport();
	    	                    out.flush();
	    	                    out.close();
	    	        }   else   if (Re_Type.equalsIgnoreCase("EXCEL"))   
	    	        {
	    	        	System.out.println("Excel .... ");
	    	                        response.setContentType("application/vnd.ms-excel");
	    	                    response.setHeader ("Content-Disposition", "attachment;filename=\"Water Charge Raised Cwss Report.xls\"");
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
	    	        else if (Re_Type.equalsIgnoreCase("PDF"))   
	    	        {
	    	                System.out.println("PDF:::::::::::");
	    	                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
	    	                    response.setContentType("application/pdf");
	    	                    response.setContentLength(buf.length);
	    	                    response.setHeader ("Content-Disposition", "attachment;filename=\"Water Charge Raised Cwss Report.pdf\"");
	    	                    OutputStream out = response.getOutputStream();
	    	                    out.write(buf, 0, buf.length);
	    	                    out.close();
	    	        }
	    		}  catch (Exception ex) {
	    		    String connectMsg ="Could not create the report " + ex.getMessage(); 
	    		    String con_err ="Could not create the report " + ex; 
	    		  //  System.out.println(con_err);
	    		    System.out.println(connectMsg);
	    		}
	      }
	  
	  
	  catch (Exception e) 
      {
         			e.printStackTrace();
		}
	}

}