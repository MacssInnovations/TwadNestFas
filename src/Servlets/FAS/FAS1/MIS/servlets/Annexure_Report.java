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
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class Annexure_Report
 */
public class Annexure_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static  String CONTENT_TYPE = 
            "text/xml; charset=windows-1252";
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Annexure_Report() {
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
        String S="";
        cmd=request.getParameter("Command");
        int txtsupplement_no=0,txtCB_Year=0,txtCB_Month=0,txtSection=0;
        String hid_val=request.getParameter("hid_val");
        String Re_Type=request.getParameter("repformat");;
        txtSection=Integer.parseInt(request.getParameter("txtSection"));
        if(hid_val.equalsIgnoreCase("MonthWise")){
        	 
              Sup_no=request.getParameter("txtsupplement_no");
              System.out.println("Sup_no >>> "+Sup_no);
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
       /* 	txtFromCB_Year=request.getParameter("txtFromCB_Year");
        	txtToCB_Year=request.getParameter("txtToCB_Year");*/
        	txtFromCB_Year=request.getParameter("fromMonth");
        	txtToCB_Year=request.getParameter("toMonth");
        	String[] f=txtFromCB_Year.split("-");
        	int Month_value=0;
        	int Month_To=0;
        	
        		if(f[1].equalsIgnoreCase("JAN"))Month_value=1;
        		else if(f[1].equalsIgnoreCase("FEB"))Month_value=2;
        		else if(f[1].equalsIgnoreCase("MAR"))Month_value=3;
        		else if(f[1].equalsIgnoreCase("APR"))Month_value=4;
        		else if(f[1].equalsIgnoreCase("MAY"))Month_value=5;
        		else if(f[1].equalsIgnoreCase("JUN"))Month_value=6;
        		else if(f[1].equalsIgnoreCase("JUL"))Month_value=7;
        		else if(f[1].equalsIgnoreCase("AUG"))Month_value=8;
        		else if(f[1].equalsIgnoreCase("SEP"))Month_value=9;
        		else if(f[1].equalsIgnoreCase("OCT"))Month_value=10;
        		else if(f[1].equalsIgnoreCase("NOV"))Month_value=11;
        		else 
        			Month_value=12;
        		
        		if(txtToCB_Year.split("-")[1].equalsIgnoreCase("JAN"))Month_To=1;
        		else if(txtToCB_Year.split("-")[1].equalsIgnoreCase("FEB"))Month_To=2;
        		else if(txtToCB_Year.split("-")[1].equalsIgnoreCase("MAR"))Month_To=3;
        		else if(txtToCB_Year.split("-")[1].equalsIgnoreCase("APR"))Month_To=4;
        		else if(txtToCB_Year.split("-")[1].equalsIgnoreCase("MAY"))Month_To=5;
        		else if(txtToCB_Year.split("-")[1].equalsIgnoreCase("JUN"))Month_To=6;
        		else if(txtToCB_Year.split("-")[1].equalsIgnoreCase("JUL"))Month_To=7;
        		else if(txtToCB_Year.split("-")[1].equalsIgnoreCase("AUG"))Month_To=8;
        		else if(txtToCB_Year.split("-")[1].equalsIgnoreCase("SEP"))Month_To=9;
        		else if(txtToCB_Year.split("-")[1].equalsIgnoreCase("OCT"))Month_To=10;
        		else if(txtToCB_Year.split("-")[1].equalsIgnoreCase("NOV"))Month_To=11;
        		else 
        			Month_value=12;
        			
        	System.out.println("Month_value >>> "+Month_value);
        	System.out.println("f[0] >>> "+f[0]);
        	
        	 /// txtSection=Integer.parseInt(request.getParameter("txtSection"));
        	if(!f[0].equalsIgnoreCase("01"))
        	{
        	 if(f[0].equalsIgnoreCase("01")){
        		 txtsupplement_no=0;}
        	 else{
        		   S = f[0].substring(1, f[0].length() - 1);
        	 System.out.println("S >>> "+S);
        		 txtsupplement_no=Integer.parseInt(S);
        	 }
        	 }else{
        		 if(txtToCB_Year.split("-")[0].equalsIgnoreCase("01")){
            		 txtsupplement_no=0;}
            	 else{
            		 //  S = txtToCB_Year.split("-")[0].substring(1, txtToCB_Year.split("-")[0].length() - 1);
            //	 System.out.println("S >>> "+S+" mmmmmmmmm  "+S.substring(1, txtToCB_Year.split("-")[0].length() - 1)+"  >   "+txtToCB_Year.split("-")[0]);
            		
            		 txtsupplement_no=Integer.parseInt(txtToCB_Year.split("-")[0].substring(1));
            		 
            		 //txtsupplement_no=Integer.parseInt(S);
            	 } 
        	 }
        	 
        		 System.out.println("txtsupplement_no >>> "+txtsupplement_no);
        		 //f[0].substring(1, f[0].length() - 1);
        		 //substring(1, f[0].length()-1));
        	 sub_qry="To_Date((aa.Cashbook_Month "+
        			" ||'-' "+
        			" || aa.Cashbook_Year),'mm-yyyy') BETWEEN To_Date( "+Month_value+
        			" ||'-' "+
        			" ||"+f[2]+",'mm-yyyy') "+
        			" AND to_date( "+Month_To+
        			" ||'-' "+
        			" ||"+txtToCB_Year.split("-")[2]+",'mm-yyyy') and  aa.sup_no="+txtsupplement_no;
        	
        		head="";
             	
        }else
        {
        	System.out.println("Error  ..... ");
        }
        JasperDesign jasperDesign = null;
        File reportFile=null;
        int from_year=0;
        // String Re_Type="";/*Comment on 10july2019*/
	     try{  
	    	 if((txtSection==3)&& (Re_Type.equalsIgnoreCase("PDF"))){
		    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/Annexure_IVb.jasper"));
	    	 }
	    	 /*@NK include on 13sep2019*/
	    	 else if((txtSection==3)&& (Re_Type.equalsIgnoreCase("EXCEL"))){
	    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/ExcelAnnexure_IVb.jasper"));
   	          }
	    	 /*@NK include on 13sep2019*/
	    	 else if((txtSection==4)&& (Re_Type.equalsIgnoreCase("PDF"))){
	    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/ExcelAnnexure-IV.jasper"));
   	 }
	    	 /*@NK include on 16sep2019*/
 		else if((txtSection==4)&& (Re_Type.equalsIgnoreCase("EXCEL"))){
		    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/ExcelAnnexure-IV.jasper"));
	    	 }
	    	 /*@NK include on 16sep2019*/
		else if((txtSection==19) &&  (Re_Type.equalsIgnoreCase("PDF"))){
	    		 reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/Annexure_IVa.jasper"));
	    	  }
	    	 /*@NK include on 16sep2019*/
		else if((txtSection==19) &&  (Re_Type.equalsIgnoreCase("EXCEL"))){
   		 reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/ExcelAnnexure_IVa.jasper"));
   	  }
	    	 /*@NK include on 16sep2019*/
		else if((txtSection==6) &&  (Re_Type.equalsIgnoreCase("PDF"))){
   		 reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/Annexure-I.jasper"));
   	  }
	    	 /*@NK include on 12sep2019*/
		else if((txtSection==6) &&  (Re_Type.equalsIgnoreCase("EXCEL"))){
	   		 reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/ExcelAnnexure-I.jasper"));
	   	  }
	     	 /*@NK include on 12sep2019*/
		else if((txtSection==7)&&(Re_Type.equalsIgnoreCase("PDF"))){
   		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/Annexure-I-A.jasper")); 
   	  }
	    	 /*@NK include on 13sep2019*/
		else if((txtSection==7)&&(Re_Type.equalsIgnoreCase("EXCEL"))){
	   		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/ExcelAnnexure-I-A.jasper")); 
	   	  }
	    	 /*@NK include on 13sep2019*/
		else if((txtSection==8)&&(Re_Type.equalsIgnoreCase("PDF"))){
   		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/Annexure-I-B.jasper"));
   	  }
	    	 /*@NK include on 13sep2019*/
		else if((txtSection==8)&&(Re_Type.equalsIgnoreCase("EXCEL"))){
	   		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/ExcelAnnexure-I-B.jasper"));
	   	  }
	    	 /*@NK include on 13sep2019*/
		else if((txtSection==9)&&(Re_Type.equalsIgnoreCase("PDF"))){
   		 reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/Annexure-II.jasper"));
   	  }
	    	 /*@NK include on 16sep2019*/
		else if((txtSection==9)&&(Re_Type.equalsIgnoreCase("EXCEL"))){
	   		 reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/ExcelAnnexure-II.jasper"));
	   	  }
	    	 /*@NK include on 16sep2019*/
		else if((txtSection==10)&&(Re_Type.equalsIgnoreCase("PDF"))){
   		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/AnnexureReport-II-A.jasper")); 
   	  }
	    	 /*@NK include on 16sep2019*/
		else if((txtSection==10)&&(Re_Type.equalsIgnoreCase("EXCEL"))){
	   		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/ExcelAnnexureReport-II-A.jasper")); 
	   	  }
	    	 /*@NK include on 16sep2019*/
		else if((txtSection==11)&&(Re_Type.equalsIgnoreCase("PDF"))){
   		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/AnnexureREport-II-B.jasper"));
   	  }
	    	 /*@NK include on 13sep2019*/
   	else if((txtSection==11)&&(Re_Type.equalsIgnoreCase("EXCEL"))){
 		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/ExcelAnnexureREport-II-B.jasper"));
 	  }
	    	 /*@NK include on 13sep2019*/
   	  else if((txtSection==24)&&(Re_Type.equalsIgnoreCase("PDF"))){
   		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/AnnexureRep-III.jasper"));
   	  }
	    	 /*@NK include on 16sep2019*/
   	  else if((txtSection==24)&&(Re_Type.equalsIgnoreCase("EXCEL"))){
   		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/ExcelAnnexureRep-III.jasper"));
   	  }
	    	 /*@NK include on 16sep2019*/
   	  else if((txtSection==25)&&(Re_Type.equalsIgnoreCase("PDF"))){
   		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/AnnexureReport-III-A.jasper"));
   	  }
	    	 /*@NK include on 16sep2019*/
   	else if((txtSection==25)&&(Re_Type.equalsIgnoreCase("EXCEL"))){
 		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/ExcelAnnexureReport-III-A.jasper"));
 	  }
	    	 /*@NK include on 16sep2019*/
   	  
   	  else if((txtSection==26)&&(Re_Type.equalsIgnoreCase("PDF"))){
   		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/AnnexureReport-IIIB.jasper"));
   	  }
	    	 /*@NK include on 13sep2019*/
   	else if((txtSection==26)&&(Re_Type.equalsIgnoreCase("EXCEL"))){
 		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/ExcelAnnexureReport-IIIB.jasper"));
 	  }
	    	 /*@NK include on 13sep2019*/
   	  else if(txtSection==17){
   		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/AnnexureVI.jasper"));
   	  }else if(txtSection==12){
   		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/AnnexureVIII_A.jasper"));
   	  }else if(txtSection==2){
   		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/AnnexureVIII_B.jasper"));
   	  } else if((txtSection==18)&&(Re_Type.equalsIgnoreCase("PDF"))){
   		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/AnnexureReport-v.jasper"));
   	  } 
	    	 /*@NK include on 16sep2019*/
   	else if((txtSection==18)&&(Re_Type.equalsIgnoreCase("EXCEL"))){
 		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/ExcelAnnexureReport-v.jasper"));
 	  } 
	    	 /*@NK include on 16sep2019*/
	    	 
   	  else if(txtSection==14){
   		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/Annexure_VII_A.jasper"));
   	  }else if(txtSection==13){
   		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/Annexure_VII_B.jasper"));
   	  }
		/*else if(txtSection==12){
	    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/Annexure_IV(12).jasper")); 
	    	  }else if(txtSection==13){
	    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/Annexure_IV(11).jasper"));
	    	  }
	    	  else if(txtSection==14){
	    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/Annexure_IV(10).jasper"));
	    	  }
	    	  else if(txtSection==17){
	    		  txtSection=3;
	    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/Annexure_IV(7).jasper"));
	    	  }
	    	  else if(txtSection==18){
	    		  txtSection=3;
	    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/Annexure_IV(6).jasper"));
	    	  }
	    	  else if(txtSection==20){
	    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/Annexure_IV(4).jasper"));
	    	  }
	    	  else if(txtSection==21){
	    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/Annexure_IV(3).jasper"));
	    	  }
	    	  else if(txtSection==22){
	    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/Annexure_IV(2).jasper"));
	    	  }
	    	  else if(txtSection==23){
	    		  txtSection=20;
	    		  reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/Annexure/Annexure_IV(1).jasper"));
	    	  }*/
			
    	 
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
	    		     /*Re_Type="PDF";*//*Comment on 10july2019*/
	    	        if (Re_Type.equalsIgnoreCase("HTML"))   
	    	        {
	    	            System.out.println("HTML :::::::::::");
	    	                    response.setContentType("text/html");
	    	                    response.setHeader ("Content-Disposition", "attachment;filename=\"Annexure VII (14) Report.html\"");
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
	    	                    response.setHeader ("Content-Disposition", "attachment;filename=\"Annexure VII (14) Report.xls\"");
	    	                    JRXlsExporter exporterXLS = new JRXlsExporter(); 
	    	                    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
	    	                    ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
	    	                    exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
	    	                    exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
	    	                    exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
	    	                    exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
	    	                    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
	    	                    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_COLUMNS, Boolean.TRUE);
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
	    	                    //JRDesignQuery.PROPERTY_TEXT(" ");
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
