package Servlets.FAS.FAS1.MIS.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import rex.olap.mdxparse.parser;
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
public class Trail_balance_Data extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
     private static final String CONTENT_TYPE = 
            "text/html; charset=windows-1252";
        Connection connection = null;
    public Trail_balance_Data() {
        super();
        // TODO Auto-generated constructor stub
    }

    
        
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        
    	CallableStatement stmt=null; 
    	Connection connection=null;
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
        
        String opt="";
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
        System.out.println(connectMsg);
        }
        JasperDesign jasperDesign = null;
        File reportFile=null;
        String  command="",fin_year="",f_year="",cont_path="";
        String year[]=null;    
    	   String Re_Type=request.getParameter("btnRdo");
    	   System.out.println(Re_Type);
    	   fin_year=request.getParameter("txtfin_year");
    	   year=fin_year.split("-");
    	   f_year=year[0]+"-"+year[1].substring(2);	   
      	
	  try {
		  stmt=connection.prepareCall("call FAS_CONSOLIDATE_ASSET(?)");
	               stmt.setString(1,fin_year);
	               stmt.execute();
	    	try{
	    		cont_path=request.getRequestURL().toString();
	    		cont_path="/org/FAS/FAS1/MIS/jaspers/twadFas/";
	    		System.out.println("cont_path..... "+cont_path);
	    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/twadFas/Asset_Consolidsate_Report.jasper"));
	    		 
	    		  System.out.println(" Report File >>>  "+reportFile); 
	    		  if (!reportFile.exists())
	    		        throw new JRRuntimeException("File J not found. The report design must be compiled first.");            
	    		    
	    		    JasperReport jasperReport =(JasperReport)JRLoader.loadObject(reportFile.getPath());
	    		    
	    		    Map map = null;
	    		    map = new HashMap();
	    		    map.put("Cb_FromYear", year[0]);
	    		    map.put("Cb_ToYear", year[1]);
	    		    map.put("Fin_year", fin_year);
	    		    
	    		  //  System.out.println("Map va;ue  ... "+map);
	    		    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);
	    		    
	    		    if (Re_Type.equalsIgnoreCase("HTML"))   
	    	        {
	    	           // System.out.println("HTML :::::::::::");
	    	                    response.setContentType("text/html");
	    	                    response.setHeader ("Content-Disposition", "attachment;filename=\"AssetConsolidate.html\"");
	    	                    PrintWriter out = response.getWriter();
	    	                    JRHtmlExporter exporter = new JRHtmlExporter();
	    	                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
	    	                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	    	                    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
	    	                    exporter.exportReport();
	    	                    out.flush();
	    	                    out.close();
	    	        }
	    	        else if (Re_Type.equalsIgnoreCase("PDF"))   
	    	        {
	    	               // System.out.println("PDF:::::::::::");
	    	                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
	    	                    response.setContentType("application/pdf");
	    	                    response.setContentLength(buf.length);
	    	                    response.setHeader ("Content-Disposition", "attachment;filename=\"AssetConsolidate.pdf\"");
	    	                    OutputStream out = response.getOutputStream();
	    	                    out.write(buf, 0, buf.length);
	    	                    out.close();
	    	        }else if(Re_Type.equalsIgnoreCase("EXCEL"))
	    	        {
	    	        //	response.sendRedirect("");
	    	        	//response.sendRedirect("org/FAS/FAS1/MIS/jsps/TS_FAS.jsp");
	    	        	response.setContentType("application/vnd.ms-excel");
	                    response.setHeader("Content-Disposition",
	                                       "attachment;filename=\"AssetConsolidate.xls\"");
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
	    	        }
	    		}  catch (Exception ex) {
	    		    String connectMsg ="Could not create the report " + ex.getMessage(); 
	    		    String con_err ="Could not create the report " + ex; 
	    		    System.out.println(con_err);
	    		    System.out.println(connectMsg);
	    		}
	      }
	  catch (Exception e) {
  		try {
			connection.rollback();
		} catch (SQLException e1) {
			
			e1.printStackTrace();
		}
           System.out.println("Exception  >> "+e);
  	}
	}	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		   
    	//System.out.println("Servlet Page ... ");
    	CallableStatement stmt=null; 
    	Connection connection=null;
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
        
        String opt="";
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

        ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
        Class.forName(strDriver.trim());
        connection =DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
        } catch (Exception ex) {
        String connectMsg ="Could not create the connection" + ex.getMessage() + " " + 
        ex.getLocalizedMessage();
        System.out.println(connectMsg);
        }
        JasperDesign jasperDesign = null;
        File reportFile=null;
        String  command="",fin_year="",f_year="";
        String year[]=null;   
        String cont_path="";
    	   String Re_Type=request.getParameter("btnRdo");
    	   System.out.println(Re_Type);
    	   fin_year=request.getParameter("txtfin_year");
    	   System.out.println("fin_year >>>>"+fin_year);
    	   year=fin_year.split("-");
    	   f_year=(Integer.parseInt(year[0])-1)+"-"+(Integer.parseInt(year[1])-1);
    		 System.out.println(" Ref_year >>>  "+f_year); 
    		 
    		    	try{
    		    	//	cont_path=request.getRequestURL().toString();
    		    		
	    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/twadFas/INCOME-EXPND_REPORT.jasper"));
	    		  String path = getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/twadFas/INCOME-EXPND_REPORT.jasper");
	    		  String ctxpath = path.substring(0, path.lastIndexOf("INCOME-EXPND_REPORT.jasper"));  
	    		  System.out.println("ctxpath..... "+ctxpath);
	    		  System.out.println(" Report File >>>  "+reportFile); 
	    	     String  head="INCOME AND EXPENDITURE STATEMENT";

	    		  if (!reportFile.exists())
	    		        throw new JRRuntimeException("File J not found. The report design must be compiled first.");            
	    		    JasperReport jasperReport =(JasperReport)JRLoader.loadObject(reportFile.getPath());
	    		    Map map = null;
	    		    map = new HashMap();
	    		    map.put("Cb_FromYear", year[0]);
	    		    map.put("Cb_ToYear", year[1]);
	    		    map.put("cur_year", fin_year);
	    		    map.put("pre_year", f_year);
	    		    map.put("cont_path", ctxpath);
	    		    //add parameter code for display the heading 21/02/2017
	    		    map.put("head", head);

	    		    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);
	    		    if (Re_Type.equalsIgnoreCase("HTML"))   
	    	        {
	    	                    response.setContentType("text/html");
	    	                    response.setHeader ("Content-Disposition", "attachment;filename=\"AssetConsolidate.html\"");
	    	                    PrintWriter out = response.getWriter();
	    	                    JRHtmlExporter exporter = new JRHtmlExporter();
	    	                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
	    	                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	    	                    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
	    	                    exporter.exportReport();
	    	                    out.flush();
	    	                    out.close();
	    	        }
	    	        else if (Re_Type.equalsIgnoreCase("PDF"))   
	    	        {
	    	                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
	    	                    response.setContentType("application/pdf");
	    	                    response.setContentLength(buf.length);
	    	                    response.setHeader ("Content-Disposition", "attachment;filename=\"AssetConsolidate.pdf\"");
	    	                    OutputStream out = response.getOutputStream();
	    	                    out.write(buf, 0, buf.length);
	    	                    out.close();
	    	        }else if(Re_Type.equalsIgnoreCase("EXCEL"))
	    	        {
	    	        	//response.sendRedirect("org/FAS/FAS1/MIS/jsps/TS_FAS.jsp");
	    	        	response.setContentType("application/vnd.ms-excel");
	                    response.setHeader("Content-Disposition",
	                                       "attachment;filename=\"AssetConsolidate.xls\"");
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
	    	        }
	    		}  catch (Exception ex) {
	    		    String connectMsg ="Could not create the report " + ex.getMessage(); 
	    		    String con_err ="Could not create the report " + ex; 
	    		    System.out.println(con_err);
	    		    System.out.println(connectMsg);
	    		}
	}

}
