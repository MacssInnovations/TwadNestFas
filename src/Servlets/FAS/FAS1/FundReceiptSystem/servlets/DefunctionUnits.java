package Servlets.FAS.FAS1.FundReceiptSystem.servlets;

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
import java.sql.SQLException;

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
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class DefunctionUnits extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    Connection connection = null;

   
    public DefunctionUnits() {
        super();
       
    }

    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	
		 doGet(request, response);
	}
	
    protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
    	
    	System.out.println("test Servlet");
   	
	try
    {
		System.out.println("this is starting::::::::::");
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
	
      //  Connection con=
    String opt="",orgUnitName="",hcode="",heading="",heading_one="";
    Calendar c;
    Date txtCrea_date=null;
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
	int cmbOffice_code=0,cashYear=0,cashMonth=0,off_id=0;
	String type_prf=null,txtoption=null,officeCode="";
	
    try 
    {
      
        	
        /*	 try{cashYear=Integer.parseInt(request.getParameter("year_val"));}
             catch(NumberFormatException e){System.out.println("exception"+e );}
            
             cashMonth=Integer.parseInt(request.getParameter("month_val"));
        */
             String[] sd = request.getParameter("txtCrea_date").split("/");
             c =
    new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                          Integer.parseInt(sd[0]));
             java.util.Date d = c.getTime();
             txtCrea_date = new Date(d.getTime());
             System.out.println("txtCrea_date " + txtCrea_date);
             txtoption=request.getParameter("txtoption");//PDF OR EXCEL
        /*     System.out.println("cashYear"+cashYear);
             System.out.println("cashMonth"+cashMonth);*/
             System.out.println("txtoption"+txtoption);
                
      /*  String monthInWords="";
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
        */
	        	 reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/FundReceiptSystem/jasper/fun_defunctionReport.jasper"));
	        	 System.out.println("TCA_List_All_rw");
	        
       
       
         System.out.println("reportFile"+reportFile);   
        if (!reportFile.exists())
        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
        
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
       System.out.println("jasperReport"+jasperReport);
        Map map=new HashMap();
    
      
        		/*map.put("year",cashYear);     
	        	 map.put("month",cashMonth);
	             
	             map.put("monthinwords",monthInWords);*/
	             map.put("date_val",txtCrea_date);
	            /* map.put("heading",heading);*/
        	
        	
        
       
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);    
        
        System.out.println("jasperPrint:"+jasperPrint);
        
            if (txtoption.equalsIgnoreCase("PDF"))   
            {
            		System.out.println("PDF:::::::::::");
                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TPA_REGIONWISE_ALL.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
        	}
	        else if (txtoption.equalsIgnoreCase("EXCEL"))   
	        {

                	response.setContentType("application/vnd.ms-excel");
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TPA_REGIONWISE_ALL.xls\"");
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