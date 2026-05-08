package Servlets.FAS.FAS1.Imprest.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
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


public class Imprest_sanction_new_servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
	Connection connection = null;    
   
    public Imprest_sanction_new_servlet() {
        super();
        
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		 doGet(request, response);
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) 
	throws ServletException, IOException {
		System.out.println("servlettttttttttttttttttttttttt");
		String mode=null;
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
        
        try 
        {
           
            String txtCB_Year=request.getParameter("txtCB_Year");
            String txtCB_Month=request.getParameter("txtCB_Month");
                       
            String rtype= request.getParameter("txtoption");
            String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
            String cmbOffice_code=request.getParameter("cmbOffice_code");
            String Advance_Type=request.getParameter("cmbAdvance_type");
          
            int accountingunit=Integer.parseInt(cmbAcc_UnitCode);
            int accountingoffice=Integer.parseInt(cmbOffice_code);
                
            int yearfrom=Integer.parseInt(txtCB_Year);
            int monthfrom=Integer.parseInt(txtCB_Month);
            
            if(Advance_Type.equals("BPF"))
            {
             mode="Cheque";
            }
            else
            {
            	   mode="Cash";
            }
            
            reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Imprest/jasper/imp_sanction_new.jasper")); 
                 
            if (!reportFile.exists())
            throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            
            JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
           
            Map map=new HashMap();
            try {
                PreparedStatement ps=null;
                ResultSet rs=null;
                String UnitName="",OfficeName="";
                ps=connection.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
                ps.setInt(1,accountingunit);
                rs=ps.executeQuery();
                if(rs.next())
                     UnitName=rs.getString("ACCOUNTING_UNIT_NAME");
             
                map.put("accountingunitid",accountingunit);
	            map.put("accountofficeid",accountingoffice);
	            map.put("cashbookmonth",monthfrom);
	            map.put("cashbookyear",yearfrom);        
	            map.put("Advance_Type",Advance_Type);
	            map.put("UnitName",UnitName);
	            map.put("Mode",mode);
            }
            catch (SQLException e) {
                System.out.println("SQL Exception -->"+e);
            }
           
         
            JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);            
            if (rtype.equalsIgnoreCase("HTML"))   
            {
                        response.setContentType("text/html");
                        response.setHeader ("Content-Disposition", "attachment;filename=\"ImprestSanction.html\"");
                        PrintWriter out = response.getWriter();
                        JRHtmlExporter exporter = new JRHtmlExporter();
                       
                        exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                        exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
                        exporter.exportReport();
                        out.flush();
                        out.close();
            }
            else      if (rtype.equalsIgnoreCase("PDF"))   
            {
                        byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
                        response.setContentType("application/pdf");
                        response.setContentLength(buf.length);
                                         
                        response.setHeader ("Content-Disposition", "attachment;filename=\"ImprestSanction.pdf\"");
                        OutputStream out = response.getOutputStream();
                        out.write(buf, 0, buf.length);
                        out.close();
            }
            else      if (rtype.equalsIgnoreCase("EXCEL"))   
            {
    
                    	response.setContentType("application/vnd.ms-excel");
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"ImprestSanction.xls\"");
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
            else      if (rtype.equalsIgnoreCase("TXT"))   
            {
            
		                response.setContentType("text/plain");
		                response.setHeader ("Content-Disposition", "attachment;filename=\"ImprestSanction.txt\"");
		                     
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
            String connectMsg = 
            "Could not create the report " + ex.getMessage() + " " + 
            ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }
		
	}
}
