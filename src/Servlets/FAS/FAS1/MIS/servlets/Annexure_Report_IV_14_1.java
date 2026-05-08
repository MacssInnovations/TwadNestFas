package Servlets.FAS.FAS1.MIS.servlets;

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
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class Annexure_Report_IV_14_1
 */
public class Annexure_Report_IV_14_1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static  String CONTENT_TYPE = 
            "text/xml; charset=windows-1252";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Annexure_Report_IV_14_1() {
        super();
       
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
	     
    	System.out.println("Servlet Page ... ");
    
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
        String Sup_no="",head="",sub_qry="",txtFromCB_Year="",txtToCB_Year,cmd="",mnth="";
       
        cmd=request.getParameter("Command");
        if(cmd.equalsIgnoreCase("REPORT"))
        {
        	System.out.println("welcome to Annexure IV 14 to 1 Reports");
        }
        int txtsupplement_no=0,txtCB_Year=0,txtCB_Month=0,txtSection=0;
        String hid_val=request.getParameter("hid_val");
        Sup_no=request.getParameter("txtsupplement_no");
        System.out.println("Sup_no >>> "+Sup_no);
        txtSection=Integer.parseInt(request.getParameter("txtSection"));
        if(hid_val.equalsIgnoreCase("MonthWise")){
        	 txtCB_Year=Integer.parseInt(request.getParameter("txtCB_Year"));
             txtCB_Month=Integer.parseInt(request.getParameter("txtCB_Month"));
        	// txtsupplement_no=Integer.parseInt(request.getParameter("txtsupplement_no"));
        	 if(Sup_no=="0"|| Sup_no==null || Sup_no=="")
        		 txtsupplement_no=0;
        	 else
        		 txtsupplement_no=Integer.parseInt(Sup_no);
        	 sub_qry="aa.Cashbook_Year="+txtCB_Year+"  and aa.Cashbook_Month="+txtCB_Month+" and  aa.sup_no<="+txtsupplement_no;
        	if(txtCB_Month==1)mnth="Jan";
        	else if(txtCB_Month==2)mnth="Feb";
        	else if(txtCB_Month==3)mnth="Mar";
        	else if(txtCB_Month==4)mnth="Apr";
        	else if(txtCB_Month==5)mnth="May";
        	else if(txtCB_Month==6)mnth="Jun";
        	else if(txtCB_Month==7)mnth="July";
        	else if(txtCB_Month==8)mnth="Aug";
        	else if(txtCB_Month==9)mnth="Sep";
        	else if(txtCB_Month==10)mnth="Oct";
        	else if(txtCB_Month==11)mnth="Nov";
        	else if(txtCB_Month==12)mnth="Dec";
        	
        	 head=" for the Month of "+mnth+" "+txtCB_Year;
        }else if(hid_val.equalsIgnoreCase("DateWise")){
        	txtFromCB_Year=request.getParameter("txtFromCB_Year");
        	txtToCB_Year=request.getParameter("txtToCB_Year");
        	  txtSection=Integer.parseInt(request.getParameter("txtSection"));
        	 if(Sup_no=="0"|| Sup_no==null || Sup_no=="")
        		 txtsupplement_no=0;
        	 else
        		 txtsupplement_no=Integer.parseInt(Sup_no);
        	 sub_qry=" map.STATEMENT_NO=2 "+
        	  " AND  To_Date((aa.Cashbook_Month "+
        			" ||'-' "+
        			" || aa.Cashbook_Year),'mm-yyyy') BETWEEN To_Date( "+txtFromCB_Year.split("/")[1]+
        			" ||'-' "+
        			" ||"+txtFromCB_Year.split("/")[2]+",'mm-yyyy') "+
        			" AND to_date( "+txtToCB_Year.split("/")[1]+
        			" ||'-' "+
        			" ||"+txtToCB_Year.split("/")[2]+",'mm-yyyy') and  aa.sup_no="+txtsupplement_no;
        	
        		head="";
        	
        }else
        {
        	System.out.println("Error  ..... ");
        }
        JasperDesign jasperDesign = null;
        File reportFile=null;
        int from_year=0;
         String Re_Type="";
	     try{  
	    	 if(txtSection==2){
		    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/Annexure_IV(14).jasper"));
	    	 }else if(txtSection==3){
	    		 reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/Annexure_IV(13).jasper"));
	    	  }else if(txtSection==12){
	    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/Annexure_IV(12).jasper")); 
	    	  }else if(txtSection==13){
	    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/Annexure_IV(11).jasper"));
	    	  }
	    	  else if(txtSection==14){
	    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/Annexure_IV(10).jasper"));
	    	  }
	    		  if (!reportFile.exists())
	    		        throw new JRRuntimeException("File J not found. The report design must be compiled first.");            
	    		    JasperReport jasperReport =(JasperReport)JRLoader.loadObject(reportFile.getPath());
	    		    Map map = null;
	    		    map = new HashMap();
	    		    map.put("sub_qry",sub_qry);
	    		    map.put("Statement",txtSection);
	    		    map.put("head",head);
	    		   System.out.println(map);
	    		    JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);   
	    		     Re_Type="PDF";
	    	        if (Re_Type.equalsIgnoreCase("HTML"))   
	    	        {
	    	            System.out.println("HTML :::::::::::");
	    	                    response.setContentType("text/html");
	    	                    response.setHeader ("Content-Disposition", "attachment;filename=\"Annexure IV (14) Report.html\"");
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
	    	                    response.setHeader ("Content-Disposition", "attachment;filename=\"Annexure IV (14) Report.xls\"");
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
	    	                    response.setHeader ("Content-Disposition", "attachment;filename=\"Annexure VII (14) Report.pdf\"");
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

}
