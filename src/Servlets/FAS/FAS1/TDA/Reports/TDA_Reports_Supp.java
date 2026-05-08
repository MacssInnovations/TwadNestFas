package Servlets.FAS.FAS1.TDA.Reports;

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
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class TDA_Reports_Supp
 */
public class TDA_Reports_Supp extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/html; charset=windows-1252";   
    public TDA_Reports_Supp() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	 	response.setContentType(CONTENT_TYPE);
        String strCommand="",queryString1="";
        Connection con=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        Calendar c;
        int cmbAcc_UnitCode=0,cmbOffice_code=0,cashYear=0,cashMonth=0,cashMonthTo=0,cashYearTo=0,cashYearFrom=0,cashMonthFrom=0,supp_no=0;
        Date challanDate=null;
        String tpaType="",Name1="",advType="",tpaTypeCB="";
        HttpSession session=request.getSession(false);
        String reportName="",com_report="",status="",queryString="",s="";
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
              LoadDriver load = new LoadDriver();
              con = load.getConnection();
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
        
        try{
        	cashMonth=Integer.parseInt(request.getParameter("txtCB_Month"));
        	cashYearFrom=Integer.parseInt(request.getParameter("txtCB_Year_from"));
        	cashMonthFrom=Integer.parseInt(request.getParameter("txtCB_Month_from"));
        	cashYearTo=Integer.parseInt(request.getParameter("txtCB_Year_to"));
        	cashMonthTo=Integer.parseInt(request.getParameter("txtCB_Month_to"));
        	supp_no=Integer.parseInt(request.getParameter("supp_no"));
        	
        }catch(NumberFormatException e){
        	System.out.println("exception"+e );
        }
        Map<Integer,String> mapMonth = new LinkedHashMap<Integer, String>();
        mapMonth.put(1, "January");
        mapMonth.put(2, "February");
        mapMonth.put(3, "March");
        mapMonth.put(4, "April");
        mapMonth.put(5, "May");
        mapMonth.put(6, "June");
        mapMonth.put(7, "July");
        mapMonth.put(8, "August");
        mapMonth.put(9, "September");
        mapMonth.put(10, "October");
        mapMonth.put(11, "November");
        mapMonth.put(12, "December");	        
        tpaType=request.getParameter("proformatype");
           
            if(tpaType.equalsIgnoreCase("DR")) {
                com_report=request.getParameter("reportname1");
              
                System.out.println("com_report*************************"+com_report);
            }
            else if(tpaType.equalsIgnoreCase("CR")) {
                com_report=request.getParameter("reportname2");
                System.out.println("com_report*************************"+com_report);
            }
        else if(tpaType.equalsIgnoreCase("allType")) {
            com_report=request.getParameter("reportname3");
            System.out.println("com_report*************************"+com_report);
        }
     //   System.out.println("reeeeeeeeeeeeee atlast"+com_report);
       if(com_report.equalsIgnoreCase("TDAOriginated") || com_report.equalsIgnoreCase("TCAOriginated") ||com_report.equalsIgnoreCase("paymentReport") ||com_report.equalsIgnoreCase("receiptReport") ||com_report.equalsIgnoreCase("journalReport")) 
       {
        //   System.out.println("reportName::::::::::::::::::::::::"+reportName);
           if(com_report.equalsIgnoreCase("TDAOriginated")) {
               tpaType="TDAO";
               Name1="TDA Raised Report";
               advType="J";
           }
           else if(com_report.equalsIgnoreCase("TCAOriginated")) {
               tpaType="TCAO";
               Name1="TCA Raised Report";
               advType="J";
           }
           else if(com_report.equalsIgnoreCase("paymentReport")) {
               tpaType="TDACB";
               Name1="TDA Posted From Payment Voucher";
                   advType="P";
           }
           else if(com_report.equalsIgnoreCase("receiptReport")) {
               tpaType="TCACB";
               Name1="TCA Posted From Receipt Voucher";
               advType="R";
           }
           else if(com_report.equalsIgnoreCase("journalReport")) {
               tpaType="TCACB";
               Name1="TCA Posted From Journal Voucher";
               advType="J";
           }
           queryString="SELECT to_char(a.VOUCHER_DATE,'dd/MM/yyyy') AS VOUCHER_DATE, "+
           				"a.ACCOUNTING_UNIT_ID AS ACCOUNTING_UNIT_ID, "+
           				"a.TRF_ACCOUNTING_UNIT_ID AS TRF_ACCOUNTING_UNIT_ID, "+
           				"a.voucher_no AS voucher_no, "+
           				"a.ORGINATING_JVR_NO AS ORGINATING_JVR_NO, "+
           				"to_char(a.ORGINATING_JVR_DATE,'dd/MM/yyyy') AS ORGINATING_JVR_DATE, "+
           				"a.particulars AS particulars, "+
           				"a.total_amount AS total_amount, " +
           				"a.SUPPLEMENT_NO AS SUPPLEMENT_NO ,"+
			   			"b.ACCOUNTING_UNIT_NAME AS orgin_unit, "+
			   			"c.accounting_unit_name AS accepting_unit "+
			   			" FROM "+
			   			"(SELECT VOUCHER_DATE, "+
			   			"ACCOUNTING_UNIT_ID, "+
			   			"TRF_ACCOUNTING_UNIT_ID, "+
			   			"voucher_no, "+
			   			"ORGINATING_JVR_NO, "+
			   			"ORGINATING_JVR_DATE," +
			   			"SUPPLEMENT_NO, "+
			   			"particulars, "+
			   			"total_amount "+
			   			"FROM FAS_TDA_TCA_RAISED_MST "+	        
			   			" WHERE status                  ='L' "+
			   			"AND ACCOUNTING_UNIT_ID        ='"+cmbAcc_UnitCode+"'"+
			   			"AND accounting_for_office_id  ='"+cmbOffice_code+"'"+  
			   			"AND TDA_OR_TCA                ='"+tpaType+"'"+
			   			"AND ADVICE_TYPE               ='"+advType+"'" +
			   			" AND SUPPLEMENT_NO= "+supp_no ;
			   if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
			   	queryString+=" AND cashbook_month            ='"+cashMonth+"'"+
			   	  			" AND cashbook_year             ='"+cashYear+"'";
			   }else{				   				   	
			   	queryString+=" AND to_date(cashbook_month "+
			   				 " ||'-'|| + cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
			   				 " || '-' "+
			   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
			   				 " AND to_date( '"+cashMonthTo+"' "+
			   				 " ||'-' "+
			   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
			   }
			   queryString+=")a "+
			   				" LEFT OUTER JOIN FAS_MST_ACCT_UNITS b "+
			   				" ON a.ACCOUNTING_UNIT_ID=b.accounting_unit_id "+
			   				" LEFT OUTER JOIN FAS_MST_ACCT_UNITS c "+
			   				" ON c.ACCOUNTING_UNIT_ID=a.TRF_ACCOUNTING_UNIT_ID";
        try
        {	        
        	Map map=null;
        	map = new HashMap();
            map.put("unitid",cmbAcc_UnitCode);
            map.put("officeid",cmbOffice_code);
            map.put("cashyear",cashYear);
            map.put("cashmonth",cashMonth);
            map.put("tdatype",tpaType);
            map.put("monthinwords",mapMonth.get(cashMonth));
            map.put("reportName",Name1);
            map.put("adviceType",advType);	
            map.put("supp_no", supp_no);
            //JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map,con);
            s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/supp_march/TDA_Raised_Supp.jrxml");
    		//output=request.getParameter("fileType");
    		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
    		System.out.println(queryString);
    		JRDesignQuery query=new JRDesignQuery();
    		query.setText(queryString);
    		jasperDesign.setQuery(query);
    		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
    		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, con);
    		ByteArrayOutputStream bout=new ByteArrayOutputStream();
            
           
	        String rtype="PDF";// request.getParameter("cmbReportType");
	        rtype=request.getParameter("txtoption");
	        if (rtype.equalsIgnoreCase("HTML"))   
	        {
	                    response.setContentType("text/html");
	                    
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Raised-Supp.html\"");
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
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Raised-Supp.pdf\"");
	                    OutputStream out = response.getOutputStream();
	                    out.write(buf, 0, buf.length);
	                    out.close();
	        }
	        else if (rtype.equalsIgnoreCase("EXCEL"))   
	        {
	        
	                response.setContentType("application/vnd.ms-excel");
	                 response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Raised-Supp.xls\"");
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
	                 response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Raised-Supp.txt\"");
	                 
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
        ex.printStackTrace();
        //sendMessage(response,"The Challan Report Creation failed","ok");
        }
       }
       else if(com_report.equalsIgnoreCase("TDAAccepted") || com_report.equalsIgnoreCase("TCAAccepted")
    		    ||com_report.equalsIgnoreCase("TDAAcceptedother") || com_report.equalsIgnoreCase("TCAAcceptedother"))
       {
           if(com_report.equalsIgnoreCase("TDAAccepted")) {
        	   queryString1=" AND mst.TRF_ACCOUNTING_UNIT_ID      ='"+cmbAcc_UnitCode+"'" +
        	   				" AND mst.ACCEPTING_JVR_NO    IS NOT NULL ";
               tpaType="TDAA";
               Name1="TDA Accepted by you";
               advType="J";
               status="Y";
           } else if(com_report.equalsIgnoreCase("TDAAcceptedother")) {
        	   queryString1=" AND mst.ACCOUNTING_UNIT_ID      ='"+cmbAcc_UnitCode+"'"+
        	   				"  AND mst.ACCEPTANCE_STATUS ='Y' ";
               tpaType="TDAA";
               Name1="TDA Accepted Report by Others";
               advType="J";
               status="Y";
           } else if(com_report.equalsIgnoreCase("TCAAccepted")) {
        	   queryString1=" AND mst.TRF_ACCOUNTING_UNIT_ID      ='"+cmbAcc_UnitCode+"'" +
   							" AND mst.ACCEPTING_JVR_NO IS NOT NULL ";
               tpaType="TCAA";
               Name1="TCA Accepted by you";
               advType="J";
               status="Y";
           } else if(com_report.equalsIgnoreCase("TCAAcceptedother")) {
        	   queryString1=" AND mst.ACCOUNTING_UNIT_ID      ='"+cmbAcc_UnitCode+"'"+
        	   				" AND mst.ACCEPTANCE_STATUS ='Y' ";
               tpaType="TCAA";
               Name1="TCA Accepted by Others";
               advType="J";
               status="Y";
           }
          
        try {
       
            //reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_Accepted.jasper"));
        	s="";
        	queryString="";	        	
			        	queryString = "SELECT TO_CHAR(a.accepting_date,'dd/MM/yyyy') AS accepting_date, " +
			        	"  a.ACCOUNTING_UNIT_ID                        AS ACCOUNTING_UNIT_ID, " +
			        	"  a.TRF_ACCOUNTING_UNIT_ID                    AS TRF_ACCOUNTING_UNIT_ID, " +
			        	"  a.accepting_slno                            AS accepting_slno, " +
			        	"  a.ORGINATING_JVR_NO                         AS ORGINATING_JVR_NO, " +
			        	"  TO_CHAR(a.ORGINATING_JVR_DATE,'dd/MM/yyyy') AS ORGINATING_JVR_DATE, " +
			        	"  a.ACCEPTING_JVR_NO                          AS ACCEPTING_JVR_NO, " +
			        	"  TO_CHAR(a.ACCEPTING_JVR_DATE,'dd/MM/yyyy')  AS ACCEPTING_JVR_DATE, " +
			        	"  a.voucher_no                                AS voucher_no, " +
			        	"  TO_CHAR(a.voucher_date,'dd/MM/yyyy')        AS voucher_date, " +
			        	"  a.particulars                               AS particulars, " +
			        	" a.supplement_no AS supplement_no , " +
			        	"  a.TOTAL_AMOUNT                              AS TOTAL_AMOUNT, " +
			        	"  b.ACCOUNTING_UNIT_NAME                      AS orgin_unit, " +
			        	"  c.accounting_unit_name                      AS accepting_unit " +
			        	"FROM " +
			        	"  (SELECT mst.VOUCHER_DATE AS accepting_date, " +
			        	"    mst.ACCOUNTING_UNIT_ID, " +
			        	"    mst.TRF_ACCOUNTING_UNIT_ID, " +
			        	"    mst.voucher_no AS accepting_slno, " +
			        	"    mst.ORGINATING_JVR_NO, " +
			        	"    mst.ORGINATING_JVR_DATE, " +
			        	"    mst.ACCEPTING_JVR_NO, " +
			        	"    mst.ACCEPTING_JVR_DATE, " +
			        	"    mst.supplement_no, " +
			        	"    mst1.voucher_no, " +
			        	"    mst1.voucher_date, " +
			        	"    mst.particulars, " +
			        	"    mst.TOTAL_AMOUNT " +
			        	"  FROM FAS_TDA_TCA_RAISED_MST mst, " +
			        	"    FAS_TDA_TCA_RAISED_MST mst1 " +
			        	"  WHERE mst.voucher_no      =mst1.accepting_slno " +
			        	"  AND mst.accounting_unit_id=mst1.trf_accounting_unit_id " +
			        	"  AND mst.status            ='L' "+
			        	queryString1 ;				        	
			        	if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
	       				   	queryString+="AND mst.cashbook_month            ='"+cashMonth+"'"+
	       				   	  			"AND mst.cashbook_year            ='"+cashYear+"'";
	       				   }else{		       				   	
	       					queryString+=" AND to_date(mst.cashbook_month "+
						   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
						   				 " || '-' "+
						   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
						   				 " AND to_date( '"+cashMonthTo+"' "+
						   				 " ||'-' "+
						   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
	       				}
			        	queryString +="  AND mst.TDA_OR_TCA        ='"+tpaType+"' " +
			        	"  AND mst.ADVICE_TYPE       ='"+advType+"' " +
			        	" AND mst.supplement_no ='"+supp_no+"'" +				        	
			        	"  )a " +
			        	"LEFT OUTER JOIN FAS_MST_ACCT_UNITS b " +
			        	"ON a.ACCOUNTING_UNIT_ID=b.accounting_unit_id " +
			        	"LEFT OUTER JOIN FAS_MST_ACCT_UNITS c " +
			        	"ON c.ACCOUNTING_UNIT_ID=a.ACCOUNTING_UNIT_ID";
        	Map map=null;
        	map = new HashMap();
            map.put("unitid",cmbAcc_UnitCode);
            map.put("officeid",cmbOffice_code);
            map.put("cashyear",cashYear);
            map.put("cashmonth",cashMonth);
            map.put("tdatype",tpaType);
            map.put("monthinwords",mapMonth.get(cashMonth));
            map.put("reportName",Name1);
            map.put("tpaTypeCB",tpaTypeCB);
            map.put("supp_no", supp_no);
            
            s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/supp_march/TDA_Accepted_Supp.jrxml");
      		//output=request.getParameter("fileType");
      		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
      		System.out.println(queryString);
      		JRDesignQuery query=new JRDesignQuery();
      		query.setText(queryString);
      		jasperDesign.setQuery(query);
      		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
      		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, con);
      		ByteArrayOutputStream bout=new ByteArrayOutputStream();
        String rtype="PDF";// request.getParameter("cmbReportType");
        rtype=request.getParameter("txtoption");
        System.out.println(rtype);
        if (rtype.equalsIgnoreCase("HTML"))   
        {
                    response.setContentType("text/html");
                    
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Pending-Supp.html\"");
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
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Pending-Supp.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
        }
        else if (rtype.equalsIgnoreCase("EXCEL"))   
        {
        
                response.setContentType("application/vnd.ms-excel");
                 response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Pending-Supp.xls\"");
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
                 response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Accepted-Supp.txt\"");
                 
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
           else if(com_report.equalsIgnoreCase("TDARejected") ||com_report.equalsIgnoreCase("TDARejectedother")
        		   ||com_report.equalsIgnoreCase("TCARejected") ||com_report.equalsIgnoreCase("TCARejectedother")) {
           
               if(com_report.equalsIgnoreCase("TDARejected")) {
            	   queryString1="AND mst.TRF_Accounting_Unit_Id='"+cmbAcc_UnitCode+"' ";
                   tpaType="TDAO";
                   tpaTypeCB="TDACB";
                   Name1="TDA Rejected by you";
               }else if(com_report.equalsIgnoreCase("TDARejectedother")){
            	   queryString1="AND mst.Accounting_Unit_Id='"+cmbAcc_UnitCode+"' ";
            	   tpaType="TDAO";
                   tpaTypeCB="TDACB";
                   Name1="TDA Rejected Others";
            	   
               } else if(com_report.equalsIgnoreCase("TCARejected")) {
            	   queryString1="AND mst.TRF_Accounting_Unit_Id='"+cmbAcc_UnitCode+"' ";
                   tpaType="TCAO";
                   tpaTypeCB="TCACB";
                   Name1="TCA Rejected Report";
               } else if(com_report.equalsIgnoreCase("TCARejectedother")) {
            	   queryString1="AND mst.Accounting_Unit_Id='"+cmbAcc_UnitCode+"' ";
                   tpaType="TCAO";
                   tpaTypeCB="TCACB";
                   Name1="TCA Rejected Report";
               }
               try {             	   
            	   Map map=null;
            	   map = new HashMap();
                   map.put("unitid",cmbAcc_UnitCode);
                   map.put("officeid",cmbOffice_code);
                   map.put("cashyear",cashYear);
                   map.put("cashmonth",cashMonth);
                   map.put("tdatype",tpaType);
                   map.put("monthinwords",mapMonth.get(cashMonth));
                   map.put("reportName",Name1);
                   map.put("tdacbtype",tpaTypeCB);
                   map.put("supp_no", supp_no);
                   s="";
                   queryString="";
                   queryString="SELECT TO_CHAR(mst.VOUCHER_DATE,'dd/MM/yyyy') AS VOUCHER_DATE, " +
                   "  mst.voucher_no                              AS voucher_no, " +
                   "  mst.TDA_OR_TCA                              AS TDA_OR_TCA, " +
                   "  mst.ACCOUNTING_UNIT_ID                      AS ACCOUNTING_UNIT_ID, " +
                   "  (SELECT uu.accounting_unit_name " +
                   "  FROM fas_mst_acct_units uu " +
                   "  WHERE uu.accounting_unit_id=mst.ACCOUNTING_UNIT_ID " +
                   "  )                          AS orgunitname, " +
                   "  mst.TRF_ACCOUNTING_UNIT_ID AS TRF_ACCOUNTING_UNIT_ID, " +
                   "  (SELECT uu.accounting_unit_name " +
                   "  FROM fas_mst_acct_units uu " +
                   "  WHERE uu.accounting_unit_id=mst.TRF_ACCOUNTING_UNIT_ID " +
                   "  )                                             AS trfunitname, " +
                   "  mst.ACCEPTING_SLNO                            AS ACCEPTING_SLNO, " +
                   "  TO_CHAR(mst.ACCEPTING_DATE,'dd/MM/yyyy')      AS ACCEPTING_DATE, " +
                   "  mst.ORGINATING_JVR_NO                         AS ORGINATING_JVR_NO, " +
                   "  TO_CHAR(mst.ORGINATING_JVR_DATE,'dd/MM/yyyy') AS ORGINATING_JVR_DATE, " +
                   "  mst.particulars                               AS particulars, " +
                   "  mst.supplement_no AS supplement_no," +
                   "  mst.TOTAL_AMOUNT                              AS TOTAL_AMOUNT " +
                   "FROM FAS_TDA_TCA_RAISED_MST mst " +
                   "WHERE mst.status          ='L' ";
                   if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
   				   	queryString+="AND mst.cashbook_month            ='"+cashMonth+"'"+
   				   	  			"AND mst.cashbook_year            ='"+cashYear+"'";
   				   }else{
   				   	/*queryString+="AND mst.cashbook_month BETWEEN('"+cashMonthFrom+"') AND ('"+cashMonthTo+"')"+
   				   	  			"AND mst.cashbook_year BETWEEN('"+cashYearFrom+"') AND ('"+cashYearTo+"')";*/
       				 queryString+=" AND to_date(mst.cashbook_month "+
				   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
				   				 " || '-' "+
				   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
				   				 " AND to_date( '"+cashMonthTo+"' "+
				   				 " ||'-' "+
				   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
   				   }
                   queryString+="AND (mst.TDA_OR_TCA       ='"+tpaType+"' " +
                   "OR mst.tda_or_tca         ='"+tpaTypeCB+"') " +
                   queryString1+
                   "AND mst.ACCEPTANCE_STATUS IN ('N','R') " +
                   " AND mst.supplement_no='"+supp_no+"'" +
                   "ORDER BY mst.voucher_no";
                   s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/supp_march/TDA_Rejected_Office_Supp.jrxml");
              		//output=request.getParameter("fileType");
              		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
              		System.out.println(queryString);
              		JRDesignQuery query=new JRDesignQuery();
              		query.setText(queryString);
              		jasperDesign.setQuery(query);
              		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
              		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, con);
              		ByteArrayOutputStream bout=new ByteArrayOutputStream();
                   
                   String rtype="PDF";// request.getParameter("cmbReportType");
                   rtype=request.getParameter("txtoption");
                   System.out.println(rtype);
                   if (rtype.equalsIgnoreCase("HTML"))   
       	        {
       	                    response.setContentType("text/html");
       	                    
       	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Rejected-Supp.html\"");
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
       	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Rejected-Supp.pdf\"");
       	                    OutputStream out = response.getOutputStream();
       	                    out.write(buf, 0, buf.length);
       	                    out.close();
       	        }
       	        else if (rtype.equalsIgnoreCase("EXCEL"))   
       	        {
       	        
       	                response.setContentType("application/vnd.ms-excel");
       	                 response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Rejected-Supp.xls\"");
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
       	                 response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Rejected-Supp.txt\"");
       	                 
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
               catch (Exception ex) {
                   String connectMsg = "Could not create the report " + ex.getMessage();//+ " uu " +  ex.getLocalizedMessage();
                   System.out.println(connectMsg);
                   //sendMessage(response,"The Challan Report Creation failed","ok");
               }
               
           }
       
       else if(com_report.equalsIgnoreCase("TDAPending") || com_report.equalsIgnoreCase("TCAPending")
    		   || com_report.equalsIgnoreCase("TDAPendingothers") || com_report.equalsIgnoreCase("TCAPendingother")) {
    	   
           if(com_report.equalsIgnoreCase("TDAPending")) {	        	   
        	   queryString1="AND TRF_Accounting_Unit_Id='"+cmbAcc_UnitCode+"' ";
               tpaType="TDAO";
               tpaTypeCB="TDACB";
               Name1="TDA Pending with you";
           } else if(com_report.equalsIgnoreCase("TDAPendingothers")) {
        	   queryString1="AND Accounting_Unit_Id='"+cmbAcc_UnitCode+"' ";
               tpaType="TDAO";
               tpaTypeCB="TDACB";
               Name1="TDA Pending with Others";
           } else if(com_report.equalsIgnoreCase("TCAPending")) {
        	   queryString1="AND TRF_Accounting_Unit_Id='"+cmbAcc_UnitCode+"' ";
               tpaType="TCAO";
               tpaTypeCB="TCACB";
               Name1="TCA Pending with you";
           } else if(com_report.equalsIgnoreCase("TCAPendingother")) {
        	   queryString1="AND Accounting_Unit_Id='"+cmbAcc_UnitCode+"' ";
               tpaType="TCAO";
               tpaTypeCB="TCACB";
               Name1="TCA Pending with Others";
           }
           try {	       
            //reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_Pending.jasper"));	        
        	s="";
        	queryString="";
        	queryString="SELECT TO_CHAR(a.VOUCHER_DATE,'dd/MM/yyyy') AS VOUCHER_DATE, " +
        				"  a.VOUCHER_NO 							 AS VOUCHER_NO ,"+
			        	"  a.ACCOUNTING_UNIT_ID                      AS ACCOUNTING_UNIT_ID, " +
			        	"  a.TRF_ACCOUNTING_UNIT_ID                  AS TRF_ACCOUNTING_UNIT_ID, " +
			        	"  a.particulars                             AS particulars, " +
			        	"  a.total_amount                            AS total_amount, " +
			        	"  a.REASON_FOR_NON_ACCEPT                   AS REASON_FOR_NON_ACCEPT, " +
			        	"  a.status                                  AS status, " +
			        	" a.supplement_no AS supplement_no," +
			        	"  b.ACCOUNTING_UNIT_NAME                    AS orgin_unit, " +
			        	"  c.accounting_unit_name                    AS accepting_unit " +
			        	"FROM " +
			        	"  (SELECT VOUCHER_DATE, " +
			        	" 	 VOUCHER_NO , "+
			        	"    ACCOUNTING_UNIT_ID, " +
			        	"    TRF_ACCOUNTING_UNIT_ID, " +
			        	"    particulars, " +
			        	"    total_amount, " +
			        	" supplement_no," +
			        	"    REASON_FOR_NON_ACCEPT, " +
			        	"    DECODE(acceptance_status,NULL,'P',acceptance_status) AS status " +
			        	//"    -- mst.accept_voucher_date " +
			        	"  FROM FAS_TDA_TCA_RAISED_MST " +
			        	"  WHERE status               ='L' " +
			        	 " AND supplement_no='"+supp_no+"'" +
			        	queryString1 ;
			        	//"  AND TRF_ACCOUNTING_UNIT_ID ='"+cmbAcc_UnitCode+"' ";
			        	if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
	       				   	queryString+="AND cashbook_month            ='"+cashMonth+"'"+
	       				   	  			"AND cashbook_year            ='"+cashYear+"'";
	       				   }else{		       				   	
	       					queryString+=" AND to_date(cashbook_month "+
						   				 " ||'-'|| + cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
						   				 " || '-' "+
						   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
						   				 " AND to_date( '"+cashMonthTo+"' "+
						   				 " ||'-' "+
						   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
	       				}
			        	queryString+="  AND (TDA_OR_TCA            ='"+tpaType+"' " +
			        	"  OR TDA_OR_TCA              ='"+tpaTypeCB+"') " +
			        	"  AND acceptance_status     IS NULL " +
			        	"  )a " +
			        	"LEFT OUTER JOIN FAS_MST_ACCT_UNITS b " +
			        	"ON a.ACCOUNTING_UNIT_ID=b.accounting_unit_id " +
			        	"LEFT OUTER JOIN FAS_MST_ACCT_UNITS c " +
			        	"ON c.ACCOUNTING_UNIT_ID=a.TRF_ACCOUNTING_UNIT_ID";
        	Map map=null;
        	map = new HashMap();
            map.put("unitid",cmbAcc_UnitCode);
            map.put("officeid",cmbOffice_code);
            map.put("cashyear",cashYear);
            map.put("cashmonth",cashMonth);
            map.put("tdatype",tpaType);
            map.put("monthinwords",mapMonth.get(cashMonth));
            map.put("reportName",Name1);
            map.put("tpaTypeCB",tpaTypeCB);
            map.put("supp_no", supp_no);
            
            s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/supp_march/TDA_Pending_Supp.jrxml");
      		//output=request.getParameter("fileType");
      		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
      		System.out.println(queryString);
      		JRDesignQuery query=new JRDesignQuery();
      		query.setText(queryString);
      		jasperDesign.setQuery(query);
      		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
      		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, con);
      		ByteArrayOutputStream bout=new ByteArrayOutputStream();
        String rtype="PDF";// request.getParameter("cmbReportType");
        rtype=request.getParameter("txtoption");
        System.out.println(rtype);
        if (rtype.equalsIgnoreCase("HTML")) {
                    response.setContentType("text/html");
                    
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Pending-Supp.html\"");
                    PrintWriter out = response.getWriter();
                    JRHtmlExporter exporter = new JRHtmlExporter();
                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
                    exporter.exportReport();
                    out.flush();
                    out.close();
        } else if (rtype.equalsIgnoreCase("PDF")) {
        			System.out.println(rtype+"...inside PDF");
                    byte buf[] = 
                      JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Pending-Supp.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
        } else if (rtype.equalsIgnoreCase("EXCEL")) {
        
        		 	response.setContentType("application/vnd.ms-excel");
        		 	response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Pending-Supp.xls\"");
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
                 response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Accepted-Supp.txt\"");
                 
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
        
        } catch (Exception ex) {
	        String connectMsg = "Could not create the report " + ex.getMessage();//+ " uu " +  ex.getLocalizedMessage();
	        System.out.println(connectMsg);
	        //sendMessage(response,"The Challan Report Creation failed","ok");
        }
       }
           
         else if(com_report.equalsIgnoreCase("TDASuspensePending") ||com_report.equalsIgnoreCase("TCASuspensePending"))
         {
            if(com_report.equalsIgnoreCase("TDASuspensePending")) {
               tpaType="TDAO";
               tpaTypeCB="TDACB";
                Name1="TDA Suspense Heads Clearance Pending";
               
            
            }
            else if(com_report.equalsIgnoreCase("TCASuspensePending")) {
              tpaType="TCAO";
              tpaTypeCB="TCACB";
             Name1="TCA Suspense Heads Clearance Pending";
              
            
            }
          try
          {
                                                  
        	  //reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_Resp_pending_Office.jasper"));
         
          
          
          s="";
          queryString="";
          queryString="SELECT TO_CHAR(a.VOUCHER_DATE,'dd/MM/yyyy')   AS VOUCHER_DATE, " +
	          "  a.VOUCHER_NO                                AS VOUCHER_NO, " +
	          "  a.TDA_OR_TCA                                AS TDA_OR_TCA, " +
	          "  a.ACCOUNTING_UNIT_ID                        AS ACCOUNTING_UNIT_ID, " +
	          "  a.TRF_ACCOUNTING_UNIT_ID                    AS TRF_ACCOUNTING_UNIT_ID, " +
	          "  a.acceptedName                              AS acceptedName, " +
	          "  a.TOTAL_AMOUNT                              AS TOTAL_AMOUNT, " +
	          "  a.ORGINATING_JVR_NO                         AS ORGINATING_JVR_NO, " +
	          "  TO_CHAR(a.ORGINATING_JVR_DATE,'dd/MM/yyyy') AS ORGINATING_JVR_DATE, " +
	          "  a.ACCEPTING_JVR_NO                          AS ACCEPTING_JVR_NO, " +
	          "  TO_CHAR(a.ACCEPTING_JVR_DATE,'dd/MM/yyyy')  AS ACCEPTING_JVR_DATE, " +
	          "  a.STATUS                                    AS STATUS, " +
	          "   a.supplement_no  AS  supplement_no," +
	          "  a.PARTICULARS                               AS PARTICULARS, " +
	          "  b.ACCOUNTING_UNIT_NAME                      AS orginatedUnit " +
	          " FROM " +
	          "  (SELECT mst.VOUCHER_DATE, " +
	          "    mst.VOUCHER_NO, " +
	          "    mst.TDA_OR_TCA, " +
	          "    mst.ACCOUNTING_UNIT_ID, " +
	          "    mst.TRF_ACCOUNTING_UNIT_ID, " +
	          "    (SELECT ACCOUNTING_UNIT_NAME " +
	          "    FROM FAS_MST_ACCT_UNITS unit " +
	          "    WHERE unit.ACCOUNTING_UNIT_ID=mst.TRF_ACCOUNTING_UNIT_ID " +
	          "    )AS acceptedName, " +
	          "    mst.TOTAL_AMOUNT, " +
	          "    mst.ORGINATING_JVR_NO, " +
	          "    mst.ORGINATING_JVR_DATE, " +
	          "    mst1.ACCEPTING_JVR_NO, " +
	          "    mst1.ACCEPTING_JVR_DATE, " +
	          "    mst.STATUS, " +
	          "    mst.PARTICULARS," +
	          "    mst.supplement_no " +
	          "  FROM FAS_TDA_TCA_RAISED_MST mst, " +
	          "    FAS_TDA_TCA_RAISED_MST mst1 " +
	          "  WHERE mst.TRF_ACCOUNTING_UNIT_ID =mst1.ACCOUNTING_UNIT_ID " +
	          "  AND mst.ACCEPTING_SLNO           =mst1.VOUCHER_NO " +
	          "  AND mst.ORGINATING_JVR_NO       IS NOT NULL " +
	          "  AND mst.orginating_jvr_date     IS NOT NULL " +
	          "  AND mst1.ACCEPTING_JVR_NO       IS NOT NULL " +
	          "  AND mst1.accepting_jvr_date     IS NOT NULL " +
	          "  AND mst.RESPONDING_JVR_NO       IS NULL " +
	          "  AND mst.RESPONDING_JVR_DATE     IS NULL " +
	          "  AND mst.supplement_no='"+supp_no+"'" +
	          "  AND mst.STATUS                   ='L' " ;
          if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
				   	queryString+="AND mst.cashbook_month            ='"+cashMonth+"'"+
				   	  			"AND mst.cashbook_year            ='"+cashYear+"'";
				   }else{
					  queryString+=" AND to_date(mst.cashbook_month "+
		   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
		   				 " || '-' "+
		   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
		   				 " AND to_date( '"+cashMonthTo+"' "+
		   				 " ||'-' "+
		   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
			  }
          queryString+="  AND mst.ACCOUNTING_FOR_OFFICE_ID ='"+cmbOffice_code+"' " +
	          "  AND (mst.TDA_OR_TCA              ='"+tpaType+"' " +
	          "  OR mst.TDA_OR_TCA                ='"+tpaTypeCB+"') " +	          
	          "  )a " +
	          "LEFT OUTER JOIN " +
	          "  (SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS " +
	          "  )b " +
	          "ON a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID " +
	          "ORDER BY a.voucher_no";
          Map map=null;
          map = new HashMap();
          
              map.put("unitid",cmbAcc_UnitCode);
              map.put("officeid",cmbOffice_code);
              map.put("cashyear",cashYear);
              map.put("cashmonth",cashMonth);
              map.put("monthinwords",mapMonth.get(cashMonth));
              map.put("reportName",Name1);
              map.put("tpaType1",tpaType);
              map.put("tpaTypeCB",tpaTypeCB);
              map.put("supp_no", supp_no);
              
              s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/supp_march/TDA_Resp_pending_Office_Supp.jrxml");
          		//output=request.getParameter("fileType");
          		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
          		System.out.println(queryString);
          		JRDesignQuery query=new JRDesignQuery();
          		query.setText(queryString);
          		jasperDesign.setQuery(query);
          		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
          		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, con);
          		ByteArrayOutputStream bout=new ByteArrayOutputStream();
          String rtype="PDF";// request.getParameter("cmbReportType");
          rtype=request.getParameter("txtoption");
          System.out.println(rtype);
          if (rtype.equalsIgnoreCase("HTML"))   
          {
                      response.setContentType("text/html");
                      
                      response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Pending-Supp.html\"");
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
                      response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Pending-Supp.pdf\"");
                      OutputStream out = response.getOutputStream();
                      out.write(buf, 0, buf.length);
                      out.close();
          }
          else if (rtype.equalsIgnoreCase("EXCEL"))   
          {
          
                  response.setContentType("application/vnd.ms-excel");
                   response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Pending-Supp.xls\"");
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
                   response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Accepted-Supp.txt\"");
                   
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
          ex.printStackTrace();
          //sendMessage(response,"The Challan Report Creation failed","ok");
          }
         }
           else if(com_report.equalsIgnoreCase("TDASuspenseCleared") ||com_report.equalsIgnoreCase("TCASuspenseCleared"))
           {
                  if(com_report.equalsIgnoreCase("TDASuspenseCleared")) {
                     tpaType="TDAO";
                     tpaTypeCB="TDACB";
                      Name1="TDA Suspense Heads Cleared";
                   
                  }
                  else if(com_report.equalsIgnoreCase("TCASuspenseCleared")) {
                    tpaType="TCAO";
                    tpaTypeCB="TCACB";
                   Name1="TCA Suspense Heads Clearaed";
                  
                  }
            try
            {
            	s="";
            	queryString="";
            	queryString="SELECT TO_CHAR(a.VOUCHER_DATE,'dd/MM/yyyy')   AS VOUCHER_DATE, " +
		          "  a.VOUCHER_NO                                AS VOUCHER_NO, " +
		          "  a.TDA_OR_TCA                                AS TDA_OR_TCA, " +
		          "  a.ACCOUNTING_UNIT_ID                        AS ACCOUNTING_UNIT_ID, " +
		          "  a.TRF_ACCOUNTING_UNIT_ID                    AS TRF_ACCOUNTING_UNIT_ID, " +
		          "  a.acceptedName                              AS acceptedName, " +
		          "  a.TOTAL_AMOUNT                              AS TOTAL_AMOUNT, " +
		          "  a.ORGINATING_JVR_NO                         AS ORGINATING_JVR_NO, " +
		          "  TO_CHAR(a.ORGINATING_JVR_DATE,'dd/MM/yyyy') AS ORGINATING_JVR_DATE, " +
		          "  a.ACCEPTING_JVR_NO                          AS ACCEPTING_JVR_NO, " +
		          "  TO_CHAR(a.ACCEPTING_JVR_DATE,'dd/MM/yyyy')  AS ACCEPTING_JVR_DATE, " +
		          "  a.RESPONDING_JVR_NO                          AS RESPONDING_JVR_NO, " +
		          "  TO_CHAR(a.RESPONDING_JVR_DATE,'dd/MM/yyyy')  AS RESPONDING_JVR_DATE, " +
		          "  a.STATUS                                    AS STATUS, " +
		          "  a.PARTICULARS                               AS PARTICULARS," +
		          "  a.supplement_no AS supplement_no, " +
		          "  b.ACCOUNTING_UNIT_NAME                      AS orginatedUnit " +
		          " FROM " +
		          "  (SELECT mst.VOUCHER_DATE, " +
		          "    mst.VOUCHER_NO, " +
		          "    mst.TDA_OR_TCA, " +
		          "    mst.ACCOUNTING_UNIT_ID, " +
		          "    mst.TRF_ACCOUNTING_UNIT_ID, " +
		          "    (SELECT ACCOUNTING_UNIT_NAME " +
		          "    FROM FAS_MST_ACCT_UNITS unit " +
		          "    WHERE unit.ACCOUNTING_UNIT_ID=mst.TRF_ACCOUNTING_UNIT_ID " +
		          "    )AS acceptedName, " +
		          "    mst.TOTAL_AMOUNT, " +
		          "    mst.ORGINATING_JVR_NO, " +
		          "    mst.ORGINATING_JVR_DATE, " +
		          "    mst.RESPONDING_JVR_NO, " +
		          "    mst.RESPONDING_JVR_DATE, " +
		          "    mst1.ACCEPTING_JVR_NO, " +
		          "    mst1.ACCEPTING_JVR_DATE, " +
		          "    mst.STATUS, " +
		          "  mst.supplement_no," +
		          "    mst.PARTICULARS " +
		          "  FROM FAS_TDA_TCA_RAISED_MST mst, " +
		          "    FAS_TDA_TCA_RAISED_MST mst1 " +
		          "  WHERE mst.TRF_ACCOUNTING_UNIT_ID =mst1.ACCOUNTING_UNIT_ID " +
		          "  AND mst.ACCEPTING_SLNO           =mst1.VOUCHER_NO " +
		          "  AND mst.ORGINATING_JVR_NO       IS NOT NULL " +
		          "  AND mst.orginating_jvr_date     IS NOT NULL " +
		          "  AND mst1.ACCEPTING_JVR_NO       IS NOT NULL " +
		          "  AND mst1.accepting_jvr_date     IS NOT NULL " +
		          "  AND mst.RESPONDING_JVR_NO       IS NOT NULL " +
		          "  AND mst.RESPONDING_JVR_DATE     IS NOT NULL " +
		          "  AND mst.supplement_no='"+supp_no+"'" +
		          "  AND mst.STATUS                   ='L' " ;
	          if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
 				   	queryString+="AND EXTRACT(MONTH FROM mst.RESPONDING_JVR_DATE)='"+cashMonth+"'"+
 				   	  			"AND  EXTRACT(YEAR FROM mst.RESPONDING_JVR_DATE)='"+cashYear+"'";
 				   }else{
 				   /*	queryString+="AND EXTRACT(MONTH FROM mst.RESPONDING_JVR_DATE) BETWEEN('"+cashMonthFrom+"') AND ('"+cashMonthTo+"')"+
 				   	  			"AND EXTRACT(YEAR FROM mst.RESPONDING_JVR_DATE) BETWEEN('"+cashYearFrom+"') AND ('"+cashYearTo+"')";*/
 				   	
 				   queryString+=" AND to_date(EXTRACT(MONTH FROM mst.RESPONDING_JVR_DATE) "+
				   				 " ||'-'|| + EXTRACT(YEAR FROM mst.RESPONDING_JVR_DATE), 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
				   				 " || '-' "+
				   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
				   				 " AND to_date( '"+cashMonthTo+"' "+
				   				 " ||'-' "+
				   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
 			  }
	          queryString+="  AND mst.ACCOUNTING_FOR_OFFICE_ID ='"+cmbOffice_code+"' " +
		          "  AND (mst.TDA_OR_TCA              ='"+tpaType+"' " +
		          "  OR mst.TDA_OR_TCA                ='"+tpaTypeCB+"') " +	          
		          "  )a " +
		          "LEFT OUTER JOIN " +
		          "  (SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS " +
		          "  )b " +
		          "ON a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID " +
		          "ORDER BY a.voucher_no";
	            Map map=null;
	            map = new HashMap();
            
                map.put("unitid",cmbAcc_UnitCode);
                map.put("officeid",cmbOffice_code);
                map.put("cashyear",cashYear);
                map.put("cashmonth",cashMonth);
                map.put("monthinwords",mapMonth.get(cashMonth));
                map.put("reportName",Name1);
                map.put("tpaType1",tpaType);
                map.put("tpaTypeCB",tpaTypeCB);
                map.put("supp_no", supp_no);
                
                
                s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/supp_march/TDA_Resp_Cleared_Office_Supp.jrxml");
          		//output=request.getParameter("fileType");
          		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
          		System.out.println(queryString);
          		JRDesignQuery query=new JRDesignQuery();
          		query.setText(queryString);
          		jasperDesign.setQuery(query);
          		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
          		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, con);
          		ByteArrayOutputStream bout=new ByteArrayOutputStream();
	            String rtype="PDF";// request.getParameter("cmbReportType");
	            rtype=request.getParameter("txtoption");
	            System.out.println(rtype);
	            if (rtype.equalsIgnoreCase("HTML"))   
	            {
	                        response.setContentType("text/html");
	                        
	                        response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Pending-Supp.html\"");
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
	                        response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Pending-Supp.pdf\"");
	                        OutputStream out = response.getOutputStream();
	                        out.write(buf, 0, buf.length);
	                        out.close();
	            }
	            else if (rtype.equalsIgnoreCase("EXCEL"))   
	            {
	            
	                    response.setContentType("application/vnd.ms-excel");
	                     response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Pending-Supp.xls\"");
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
	                     response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Accepted-Supp.txt\"");
	                     
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
       else if(com_report.equalsIgnoreCase("bothTdaTca") ){
               Name1="TDA ALL";
           
       // File reportFile=null;
        try{
        	String finYear="";
        	if(cashMonth<4){
        		finYear=(cashYear-1)+"-"+(cashYear);
        	}else{
        		finYear=(cashYear)+"-"+(cashYear+1);
        	}
            //s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_ALL.jrxml");
      		//output=request.getParameter("fileType");
            s="";
	        queryString="";
	        queryString="SELECT TO_CHAR(a.VOUCHER_DATE,'dd/MM/yyyy')   AS VOUCHER_DATE, " +
	          "  a.VOUCHER_NO                                AS VOUCHER_NO, " +
	         // "  a.TDA_OR_TCA                                AS TDA_OR_TCA, " +
	          "  a.ACCOUNTING_UNIT_ID                        AS ACCOUNTING_UNIT_ID, " +
	          "  a.TRF_ACCOUNTING_UNIT_ID                    AS TRF_ACCOUNTING_UNIT_ID, " +
	         // "  a.acceptedName                              AS acceptedName, " +
	          "  a.TOTAL_AMOUNT                              AS TOTAL_AMOUNT, " +
	          "  a.ORGINATING_JVR_NO                         AS ORGINATING_JVR_NO, " +
	          "  TO_CHAR(a.ORGINATING_JVR_DATE,'dd/MM/yyyy') AS ORGINATING_JVR_DATE, " +
	          "  a.ACCEPTING_JVR_NO                          AS ACCEPTING_JVR_NO, " +
	          "  TO_CHAR(a.ACCEPTING_JVR_DATE,'dd/MM/yyyy')  AS ACCEPTING_JVR_DATE, " +
	          "  a.RESPONDING_JVR_NO                         AS RESPONDING_JVR_NO, " +
	          "  TO_CHAR(a.RESPONDING_JVR_DATE,'dd/MM/yyyy') AS RESPONDING_JVR_DATE, " +
	          "  a.respond_status							 AS STATUS, " +
	          " a.supplement_no AS supplement_no," +
	         // "  a.PARTICULARS                               AS PARTICULARS, " +
	          "  b.ACCOUNTING_UNIT_NAME 					 AS orgin_unit, " +
	          "  c.accounting_unit_name 					 AS accepting_unit " +
	          " FROM " +
	          "  (SELECT mst.VOUCHER_DATE, " +
	          "    mst.ACCOUNTING_UNIT_ID, " +
	          "    mst.TRF_ACCOUNTING_UNIT_ID, " +
	          "    mst.voucher_no, " +
	          "    mst.ORGINATING_JVR_NO, " +
	          "    mst.ORGINATING_JVR_DATE, " +
	          "    mst1.ACCEPTING_JVR_NO, " +
	          "    mst1.ACCEPTING_JVR_DATE, " +
	          "    mst.RESPONDING_JVR_NO, " +
	          "    mst.RESPONDING_JVR_DATE," +
	          " mst.supplement_no ,  " +
	          "    mst.total_amount, " +
	          "    CASE " +
	          "      WHEN (mst.acceptance_status ='Y' " +
	          "      AND (mst.RESPONDING_JVR_NO IS NULL " +
	          "      OR mst.RESPONDING_JVR_NO    =0)) " +
	          "      THEN 'Pending' " +
	          "      WHEN mst.acceptance_status='N' " +
	          "      THEN 'Rejected' " +
	          "      ELSE 'cleared' " +
	          "    END AS respond_status " +
	          "  FROM FAS_TDA_TCA_RAISED_MST mst, " +
	          "    FAS_TDA_TCA_RAISED_MST mst1 " +
	          "  WHERE mst.TRF_ACCOUNTING_UNIT_ID=mst1.ACCOUNTING_UNIT_ID " +
	          "  AND mst.ACCEPTING_SLNO          =mst1.VOUCHER_NO " +
	          "  AND mst.status                  ='L' " +
	          "  AND mst.supplement_no='"+supp_no+"'" +
	          "  AND mst.ACCOUNTING_UNIT_ID      ='"+cmbAcc_UnitCode+"'"+
	          "  AND mst.accounting_for_office_id='"+cmbOffice_code+"'";
	          if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
				   	queryString+="AND mst.cashbook_month            ='"+cashMonth+"'"+
				   	  			"AND mst.cashbook_year            ='"+cashYear+"'";
				   }else{
					  queryString+=" AND to_date(mst.cashbook_month "+
			   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
			   				 " || '-' "+
			   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
			   				 " AND to_date( '"+cashMonthTo+"' "+
			   				 " ||'-' "+
			   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
			  }
	          queryString+="  )a " +
		          "LEFT OUTER JOIN " +
		          "  (SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS " +
		          "  )b " +
		          " ON a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID " +
		          " LEFT OUTER JOIN FAS_MST_ACCT_UNITS c " +
		          " ON c.ACCOUNTING_UNIT_ID=a.TRF_ACCOUNTING_UNIT_ID "+
		          " ORDER BY a.voucher_no";
	          	Map map=new HashMap();
	            map.put("unitid",cmbAcc_UnitCode);
	            map.put("officeid",cmbOffice_code);
	            map.put("cashyear",cashYear);
	            map.put("cashmonth",cashMonth);
	            map.put("tdatype",tpaType);
	            map.put("monthinwords",mapMonth.get(cashMonth));
	            map.put("finyear",finYear);
	            map.put("reportName",Name1);	
	            map.put("supp_no", supp_no);
	            
	            s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/supp_march/TDA_ALL_Supp.jrxml");
	          		//output=request.getParameter("fileType");
	          		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
	          		System.out.println(queryString);
	          		JRDesignQuery query=new JRDesignQuery();
	          		query.setText(queryString);
	          		jasperDesign.setQuery(query);
	          		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
	          		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, con);
	          		ByteArrayOutputStream bout=new ByteArrayOutputStream();
	          String rtype="PDF";// request.getParameter("cmbReportType");
	          rtype=request.getParameter("txtoption");
	          System.out.println(rtype);
        if (rtype.equalsIgnoreCase("HTML"))   
        {
                    response.setContentType("text/html");
                    
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-All-Supp.html\"");
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
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-All-Supp.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
        }
        else if (rtype.equalsIgnoreCase("EXCEL"))   
        {
        
                response.setContentType("application/vnd.ms-excel");
                 response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-All-Supp.xls\"");
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
                 response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-All-Supp.txt\"");
                 
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
        catch (Exception ex){
        	String connectMsg = "Could not create the report " + ex.getMessage();//+ " uu " +  ex.getLocalizedMessage();
        	System.out.println(connectMsg);
        	//sendMessage(response,"The Challan Report Creation failed","ok");
        }
       }  
       
       
        System.out.println("TDA successfully printed");
	}

}
