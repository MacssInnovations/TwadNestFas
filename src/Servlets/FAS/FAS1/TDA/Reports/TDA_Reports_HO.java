package Servlets.FAS.FAS1.TDA.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;
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
 * Servlet implementation class TDA_Reports_HO
 */
public class TDA_Reports_HO extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/html; charset=windows-1252";        
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TDA_Reports_HO() {
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
        int cmbAcc_UnitCode=0,cmbOffice_code=0,cashYear=0,cashMonth=0,cashYearFrom=0,cashYearTo=0,cashMonthFrom=0,cashMonthTo=0;
        Date challanDate=null;
        String tpaType="",Name1="",com_report="",advType="",status="",tpaTypeCB="",tdacbtype="",s="";
        String queryString1 = "";
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
        	LoadDriver load = new LoadDriver();
        	con = load.getConnection();
        } catch(Exception e) {
                     System.out.println("Exception in opening connection :"+e);
        }
        try {
        
            strCommand=request.getParameter("Command");
            System.out.println("assign..here command..."+strCommand);
        }
        
        catch(Exception e) 
        {
            System.out.println("Exception in assigning..."+e);
        }
        
        
        String displayOrder=request.getParameter("displayingOrder");
        
        
        if(displayOrder.equalsIgnoreCase("RW"))
        {
        	cmbOffice_code=Integer.parseInt(request.getParameter("txtRegionId"));
        	
        }else if(displayOrder.equalsIgnoreCase("OW"))
        {
        	cmbOffice_code=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        }
        
        
        
        
        try{cashYear=Integer.parseInt(request.getParameter("txtCB_Year"));}
        catch(NumberFormatException e){System.out.println("exception"+e );}
        System.out.println("cashYear "+cashYear);
        
        try{
        	cashMonth=Integer.parseInt(request.getParameter("txtCB_Month"));
        	cashYearFrom=Integer.parseInt(request.getParameter("txtCB_Year_from"));
        	cashYearTo=Integer.parseInt(request.getParameter("txtCB_Year_to"));
        	cashMonthFrom=Integer.parseInt(request.getParameter("txtCB_Month_from"));
        	cashMonthTo=Integer.parseInt(request.getParameter("txtCB_Month_to"));
        }catch(NumberFormatException e){
        	System.out.println("exception"+e );
        }
        System.out.println("cashMonth "+cashMonth);      
        
     
        System.out.println("displayOrder"+displayOrder);
        System.out.println("cmbOffice_code"+cmbOffice_code);
        System.out.println("cashYear"+cashYear);
        System.out.println("cashMonth"+cashMonth);
    
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
      String queryString="";
       tpaType=request.getParameter("proformatype");
       
       if(tpaType.equalsIgnoreCase("DR")) {
           com_report=request.getParameter("reportname1");
          // tpaType;
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
       System.out.println("reeeeeeeeeeeeee atlast"+com_report);
        
       if(com_report.equalsIgnoreCase("TDAOriginated") || com_report.equalsIgnoreCase("TCAOriginated") ||com_report.equalsIgnoreCase("paymentReport") ||com_report.equalsIgnoreCase("receiptReport") ||com_report.equalsIgnoreCase("journalReport")) 
       {
//       if(com_report.equalsIgnoreCase("TDARaisedReport")) {
//           tpaType="TDAO";
//           Name1="TDA Raised Report";
//       }
//       else if(com_report.equalsIgnoreCase("TCARaisedReport")) {
//           tpaType="TCAO";
//           Name1="TCA Raised Report";
//       }

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

        try
        {
        	Map map = new HashMap();
	        if(displayOrder.equalsIgnoreCase("RW")) {
	        queryString = "";
	        s = "";
	        queryString = "SELECT TO_CHAR(a.VOUCHER_DATE,'dd/MM/yyyy')   AS VOUCHER_DATE, " +
	    	"  a.ACCOUNTING_UNIT_ID                        AS ACCOUNTING_UNIT_ID, " +
	    	"  a.TRF_ACCOUNTING_UNIT_ID                    AS TRF_ACCOUNTING_UNIT_ID, " +
	    	"  a.voucher_no                                AS voucher_no, " +
	    	"  a.ORGINATING_JVR_NO                         AS ORGINATING_JVR_NO, " +
	    	"  TO_CHAR(a.ORGINATING_JVR_DATE,'dd/MM/yyyy') AS ORGINATING_JVR_DATE, " +
	    	"  a.particulars                               AS particulars, " +
	    	"  a.total_amount                              AS total_amount, " +
	    	"  b.ACCOUNTING_UNIT_NAME                      AS orgin_unit, " +
	    	"  c.accounting_unit_name                      AS accepting_unit " +
	    	"FROM " +
	    	"  (SELECT mst.VOUCHER_DATE, " +
	    	"    mst.ACCOUNTING_UNIT_ID, " +
	    	"    mst.TRF_ACCOUNTING_UNIT_ID, " +
	    	"    mst.voucher_no, " +
	    	"    mst.ORGINATING_JVR_NO, " +
	    	"    mst.ORGINATING_JVR_DATE, " +
	    	"    mst.particulars, " +
	    	"    mst.total_amount, " +
	    	"    MST.TDA_OR_TCA " +
	    	"  FROM FAS_TDA_TCA_RAISED_MST mst " +
	    	"  WHERE mst.status                ='L'" +
	    	"  AND mst.accounting_for_office_id IN " +
	    	"    (SELECT office_id FROM COM_MST_ALL_OFFICES_VIEW WHERE region_office_id ='"+cmbOffice_code+"'" +
	    	"    ) " ;
	        if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
			   	queryString+="AND mst.cashbook_month		='"+cashMonth+"'"+
			   	  			"AND mst.cashbook_year 			='"+cashYear+"'";
			   	map.put("monthinwords","For the "+cashYear+" "+mapMonth.get(cashMonth));
			   }else{				   				   	
			   	queryString+=" AND to_date(mst.cashbook_month"+
			   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
			   				 " || '-' "+
			   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
			   				 " AND to_date( '"+cashMonthTo+"' "+
			   				 " ||'-' "+
			   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
			   	map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+" "+cashYearFrom+" To "+mapMonth.get(cashMonthTo)+" "+cashYearTo);
			   }
	        	queryString +="  AND mst.tda_or_tca  ='"+tpaType+"'" +
					    	"  AND mst.ADVICE_TYPE ='"+advType+"'"+
					    	" AND mst.ACCEPTANCE_STATUS       ='N'"+
					    	"  )a " +
					    	"LEFT OUTER JOIN FAS_MST_ACCT_UNITS b " +
					    	"ON a.ACCOUNTING_UNIT_ID=b.accounting_unit_id " +
					    	"LEFT OUTER JOIN FAS_MST_ACCT_UNITS c " +
					    	"ON c.ACCOUNTING_UNIT_ID=a.TRF_ACCOUNTING_UNIT_ID";
							s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_Raised_Region.jrxml");
	        } else if(displayOrder.equalsIgnoreCase("OW")){        	
	        	queryString="";
	        	s="";
	        	queryString = "SELECT TO_CHAR(a.VOUCHER_DATE,'dd/MM/yyyy')   AS VOUCHER_DATE, " +
	        	"  a.ACCOUNTING_UNIT_ID                        AS ACCOUNTING_UNIT_ID, " +
	        	"  a.TRF_ACCOUNTING_UNIT_ID                    AS TRF_ACCOUNTING_UNIT_ID, " +
	        	"  a.voucher_no                                AS voucher_no, " +
	        	"  a.ORGINATING_JVR_NO                         AS ORGINATING_JVR_NO, " +
	        	"  TO_CHAR(a.ORGINATING_JVR_DATE,'dd/MM/yyyy') AS ORGINATING_JVR_DATE, " +
	        	"  a.particulars                               AS particulars, " +
	        	"  a.total_amount                              AS total_amount, " +
	        	"  b.ACCOUNTING_UNIT_NAME                      AS orgin_unit, " +
	        	"  c.accounting_unit_name                      AS accepting_unit " +
	        	"FROM " +
	        	"  (SELECT mst.VOUCHER_DATE, " +
	        	"    mst.ACCOUNTING_UNIT_ID, " +
	        	"    mst.TRF_ACCOUNTING_UNIT_ID, " +
	        	"    mst.voucher_no, " +
	        	"    mst.ORGINATING_JVR_NO, " +
	        	"    mst.ORGINATING_JVR_DATE, " +
	        	"    mst.particulars, " +
	        	"    mst.total_amount, " +
	        	"    MST.TDA_OR_TCA " +
	        	"  FROM FAS_TDA_TCA_RAISED_MST mst " +
	        	"  WHERE mst.status                ='L' " +
	        	"  AND mst.ACCOUNTING_UNIT_ID ='"+cmbOffice_code+"'";
	        	if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
				   	queryString+="AND mst.cashbook_month		='"+cashMonth+"'"+
				   	  			"AND mst.cashbook_year 			='"+cashYear+"'";
				   	map.put("monthinwords","For the "+cashYear+" "+mapMonth.get(cashMonth));
				   }else{				   				   	
				   	queryString+=" AND to_date(mst.cashbook_month"+
				   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
				   				 " || '-' "+
				   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
				   				 " AND to_date( '"+cashMonthTo+"' "+
				   				 " ||'-' "+
				   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
				   	map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+" "+cashYearFrom+" To "+mapMonth.get(cashMonthTo)+" "+cashYearTo);
				   }
	        	
	        	queryString +="  AND mst.tda_or_tca  ='"+tpaType+"'" +
				        	"  AND mst.ADVICE_TYPE ='"+advType+"'"+
				        	"  )a " +
				        	"LEFT OUTER JOIN FAS_MST_ACCT_UNITS b " +
				        	"ON a.ACCOUNTING_UNIT_ID=b.accounting_unit_id " +
				        	"LEFT OUTER JOIN FAS_MST_ACCT_UNITS c " +
				        	"ON c.ACCOUNTING_UNIT_ID=a.TRF_ACCOUNTING_UNIT_ID";
	        	s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_Raised_Office.jrxml");
	        } else if(displayOrder.equalsIgnoreCase("ALL")){
	        		queryString="";
	        		s="";
	        		queryString="SELECT TO_CHAR(a.VOUCHER_DATE,'dd/MM/yyyy')   AS VOUCHER_DATE, " +
	        		"  a.ACCOUNTING_UNIT_ID                        AS ACCOUNTING_UNIT_ID, " +
	        		"  a.TRF_ACCOUNTING_UNIT_ID                    AS TRF_ACCOUNTING_UNIT_ID, " +
	        		"  a.voucher_no                                AS voucher_no, " +
	        		"  a.ORGINATING_JVR_NO                         AS ORGINATING_JVR_NO, " +
	        		"  TO_CHAR(a.ORGINATING_JVR_DATE,'dd/MM/yyyy') AS ORGINATING_JVR_DATE, " +
	        		"  a.particulars                               AS particulars, " +
	        		"  a.total_amount                              AS total_amount, " +
	        		"  b.ACCOUNTING_UNIT_NAME                      AS orgin_unit, " +
	        		"  c.accounting_unit_name                      AS accepting_unit " +
	        		"FROM " +
	        		"  (SELECT mst.VOUCHER_DATE, " +
	        		"    mst.ACCOUNTING_UNIT_ID, " +
	        		"    mst.TRF_ACCOUNTING_UNIT_ID, " +
	        		"    mst.voucher_no, " +
	        		"    mst.ORGINATING_JVR_NO, " +
	        		"    mst.ORGINATING_JVR_DATE, " +
	        		"    mst.particulars, " +
	        		"    mst.total_amount " +
	        		"  FROM FAS_TDA_TCA_RAISED_MST mst " +
	        		"  WHERE mst.status       ='L' ";
	        		if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
					   	queryString+="AND mst.cashbook_month		='"+cashMonth+"'"+
					   	  			"AND mst.cashbook_year 			='"+cashYear+"'";
					   			map.put("monthinwords","For the "+cashYear+" "+mapMonth.get(cashMonth));
					   }else{				   				   	
					   	queryString+=" AND to_date(mst.cashbook_month"+
					   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
					   				 " || '-' "+
					   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
					   				 " AND to_date( '"+cashMonthTo+"' "+
					   				 " ||'-' "+
					   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
					   			map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+" "+cashYearFrom+" To "+mapMonth.get(cashMonthTo)+" "+cashYearTo);
					   }
	        		queryString+="  AND mst.tda_or_tca     ='"+tpaType+"'" +
	        					"  AND mst.ADVICE_TYPE    ='"+advType+"' " +
				        		"  )a " +
				        		"LEFT OUTER JOIN FAS_MST_ACCT_UNITS b " +
				        		"ON a.ACCOUNTING_UNIT_ID=b.accounting_unit_id " +
				        		"LEFT OUTER JOIN FAS_MST_ACCT_UNITS c " +
				        		"ON c.ACCOUNTING_UNIT_ID=a.TRF_ACCOUNTING_UNIT_ID " +
				        		"ORDER BY a.voucher_no";
	        		s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_Raised_All.jrxml");
	        	}   
            map.put("unitid",cmbAcc_UnitCode);
            map.put("officeid",cmbOffice_code);
            map.put("cashyear",cashYear);
            map.put("cashmonth",cashMonth);
            map.put("tdatype",tpaType);
           // map.put("monthinwords",mapMonth.get(cashMonth));
            map.put("reportName",Name1);
            map.put("adviceType",advType);
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
                    
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Raised.html\"");
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
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Raised.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
        }
        else if (rtype.equalsIgnoreCase("EXCEL"))   
        {
        
                response.setContentType("application/vnd.ms-excel");
                 response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Raised.xls\"");
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
                 response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Raised.txt\"");
                 
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
         else if(com_report.equalsIgnoreCase("TDAAccepted") || com_report.equalsIgnoreCase("TCAAccepted") 
        		 || com_report.equalsIgnoreCase("TDAAcceptedother") || com_report.equalsIgnoreCase("TCAAcceptedother")) {
             if(com_report.equalsIgnoreCase("TDAAccepted")) {
            	 queryString1 ="  AND mst.ACCOUNTING_UNIT_ID ='"+cmbOffice_code+"'"+
            			 "  AND mst.ACCEPTANCE_STATUS ='Y' "+
				  			   " AND mst.ACCEPTING_JVR_NO IS NOT NULL ";
                 tpaType="TDAA";
                 tdacbtype="TDACB";
                 Name1="TDA Accepted";
                 status="Y";
          
             }else if(com_report.equalsIgnoreCase("TDAAcceptedother")) {
            	 queryString1 ="  AND mst.TRF_ACCOUNTING_UNIT_ID='"+cmbOffice_code+"'"+
				  "  AND mst.ACCEPTANCE_STATUS ='Y' "+
				  "  AND mst.ACCEPTING_JVR_NO IS NOT NULL ";
                 tpaType="TDAA";
                 tdacbtype="TDACB";
                 Name1="TDA Accepted by Others";
                 status="Y";
          
             }else if(com_report.equalsIgnoreCase("TCAAccepted")) {
            	 queryString1 ="  AND mst.ACCOUNTING_UNIT_ID ='"+cmbOffice_code+"'"+
				  			   " AND mst.ACCEPTING_JVR_NO IS NOT NULL ";
                 tpaType="TCAA";
                 tdacbtype="TCACB";
                 Name1="TCA Accepted by you";
                 status="Y";
            
             }else if(com_report.equalsIgnoreCase("TCAAcceptedother")) {
            	 queryString1 ="  AND mst.TRF_ACCOUNTING_UNIT_ID='"+cmbOffice_code+"'"+
				  "  AND mst.ACCEPTANCE_STATUS ='Y' "+
				  "  AND mst.ACCEPTING_JVR_NO IS NOT NULL ";
                 tpaType="TCAA";
                 tdacbtype="TCACB";
                 Name1="TCA Accepted by Others";
                 status="Y";
            
             }
          
        try {
            Map map = new HashMap();
            System.out.println("calling servlet... tpaType "+tpaType+" "+advType+" cmbOffice_code "+cmbOffice_code);
            if(displayOrder.equalsIgnoreCase("RW")) {
            queryString = "";
            s = "";
//            queryString = "SELECT TO_CHAR(a.VOUCHER_DATE,'dd/MM/yyyy')   AS VOUCHER_DATE, " +
//        	"  a.ACCOUNTING_UNIT_ID                        AS ACCOUNTING_UNIT_ID, " +
//        	"  a.TRF_ACCOUNTING_UNIT_ID                    AS TRF_ACCOUNTING_UNIT_ID, " +
//        	"  a.voucher_no                                AS voucher_no, " +
//        	"  a.ORGINATING_JVR_NO                         AS ORGINATING_JVR_NO, " +
//        	"  TO_CHAR(a.ORGINATING_JVR_DATE,'dd/MM/yyyy') AS ORGINATING_JVR_DATE, " +
//        	"  a.particulars                               AS particulars, " +
//        	"  a.total_amount                              AS total_amount, " +
//        	"  b.ACCOUNTING_UNIT_NAME                      AS orgin_unit, " +
//        	"  c.accounting_unit_name                      AS accepting_unit " +
//        	"FROM " +
//        	"  (SELECT mst.VOUCHER_DATE, " +
//        	"    mst.ACCOUNTING_UNIT_ID, " +
//        	"    mst.TRF_ACCOUNTING_UNIT_ID, " +
//        	"    mst.voucher_no, " +
//        	"    mst.ORGINATING_JVR_NO, " +
//        	"    mst.ORGINATING_JVR_DATE, " +
//        	"    mst.particulars, " +
//        	"    mst.total_amount, " +
//        	"    MST.TDA_OR_TCA " +
//        	"  FROM FAS_TDA_TCA_RAISED_MST mst " +
//        	"  WHERE mst.status                ='L'" +
//        	"  AND mst.accounting_for_office_id IN " +
//        	"    (SELECT office_id FROM COM_MST_ALL_OFFICES_VIEW WHERE region_office_id ='"+cmbOffice_code+"'" +
//        	"    ) " ;
            
            queryString="SELECT a.*, "+
            		" b.ACCOUNTING_UNIT_NAME AS orgin_unit," +
            		" c.accounting_unit_name AS accepting_unit" +
            		" FROM " +
            		" (SELECT mst.voucher_no," +
            		" TO_CHAR(mst.VOUCHER_DATE,'dd/MM/yyyy')   AS VOUCHER_DATE, "+
            		" mst.TDA_OR_TCA, "+
            		" mst.ACCOUNTING_UNIT_ID, "+
            		" mst.TRF_ACCOUNTING_UNIT_ID, "+
            		" mst.ORGINATING_JVR_NO, "+
            		" mst.ORGINATING_JVR_DATE, " +
            		" mst1.ACCEPTING_JVR_NO, " +
            		" mst1.ACCEPTING_JVR_DATE, " +
            		" mst.accepting_slno, " +
            		" mst.accepting_date, " +
            		" mst.particulars, " +
            		" mst.TOTAL_AMOUNT " +
            		" FROM FAS_TDA_TCA_RAISED_MST mst,FAS_TDA_TCA_RAISED_MST mst1 " +    
            		" WHERE  mst.TRF_ACCOUNTING_UNIT_ID=mst1.ACCOUNTING_UNIT_ID " + 
            		" AND mst.ACCEPTING_SLNO=mst1.VOUCHER_NO "+ 
            		" and mst.status                ='L' " + 
            		" AND mst.accounting_for_office_id in (select office_id from COM_MST_ALL_OFFICES_VIEW  where region_office_id ="+cmbOffice_code+" )" ; 
  
 
            
            if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
    		   	queryString+="AND mst.cashbook_month		='"+cashMonth+"'"+
    		   	  			"AND mst.cashbook_year 			='"+cashYear+"'";
    		   	map.put("monthinwords","For the "+cashYear+" "+mapMonth.get(cashMonth));
    		   }else{				   				   	
    		   	queryString+=" AND to_date(mst.cashbook_month"+
    		   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
    		   				 " || '-' "+
    		   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
    		   				 " AND to_date( '"+cashMonthTo+"' "+
    		   				 " ||'-' "+
    		   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
    		   	map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+" "+cashYearFrom+" To "+mapMonth.get(cashMonthTo)+" "+cashYearTo);
    		   }
//            	queryString +="  AND mst.tda_or_tca  ='"+tpaType+"'" +    				    	
//    				    	"  )a " +
            	queryString +=" AND (mst.TDA_OR_TCA  ='"+tpaType+"' " +
            			" OR mst.tda_or_tca               ='"+tdacbtype+"') " +
            			" AND mst.ACCEPTANCE_STATUS ='"+status+"' " +
            			" )a "+
    				    	"LEFT OUTER JOIN FAS_MST_ACCT_UNITS b " +
    				    	"ON a.ACCOUNTING_UNIT_ID=b.accounting_unit_id " +
    				    	"LEFT OUTER JOIN FAS_MST_ACCT_UNITS c " +
    				    	"ON c.ACCOUNTING_UNIT_ID=a.TRF_ACCOUNTING_UNIT_ID";
    						s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_Accepted_Region.jrxml");
            } else if(displayOrder.equalsIgnoreCase("OW")){           	
            	queryString="";
            	s="";
            	queryString = "SELECT TO_CHAR(a.VOUCHER_DATE,'dd/MM/yyyy')   AS VOUCHER_DATE, " +
            	"  a.ACCOUNTING_UNIT_ID                        AS ACCOUNTING_UNIT_ID, " +
            	"  a.TRF_ACCOUNTING_UNIT_ID                    AS TRF_ACCOUNTING_UNIT_ID, " +
            	"  a.voucher_no                                AS voucher_no, " +
            	"  a.ACCEPTING_SLNO                            AS ACCEPTING_SLNO, " +
            	"  TO_CHAR(a.ACCEPTING_DATE,'dd/MM/yyyy') 	   AS ACCEPTING_DATE, " +
            	"  a.ACCEPTING_JVR_NO                          AS ACCEPTING_JVR_NO, " +
            	"  TO_CHAR(a.ACCEPTING_JVR_DATE,'dd/MM/yyyy')  AS ACCEPTING_JVR_DATE, " +
            	"  a.particulars                               AS particulars, " +
            	"  a.total_amount                              AS total_amount, " +
            	"  a.TDA_OR_TCA                                AS TDA_OR_TCA, " +
            	"  b.ACCOUNTING_UNIT_NAME                      AS orgin_unit, " +
            	"  c.accounting_unit_name                      AS accepting_unit " +
            	"FROM " +
            	"  (SELECT mst.VOUCHER_DATE, " +
            	"    mst.ACCOUNTING_UNIT_ID, " +
            	"    mst.TRF_ACCOUNTING_UNIT_ID, " +
            	"    mst.voucher_no, " +
            	"    mst.ACCEPTING_SLNO, " +
            	"    mst.ACCEPTING_DATE, " +
            	"    mst.particulars, " +
            	"    mst.total_amount, " +
            	"    mst.ACCEPTING_JVR_NO, " +
            	"    mst.ACCEPTING_JVR_DATE, " +
            	"    MST.TDA_OR_TCA " +
            	"  FROM FAS_TDA_TCA_RAISED_MST mst " +
            	"  WHERE mst.status                ='L' " + queryString1;
            	
            	if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
    			   	queryString+="AND mst.cashbook_month		='"+cashMonth+"'"+
    			   	  			"AND mst.cashbook_year 			='"+cashYear+"'";
    			   	map.put("monthinwords","For the "+cashYear+" "+mapMonth.get(cashMonth));
    			   }else{				   				   	
    			   	queryString+=" AND to_date(mst.cashbook_month"+
    			   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
    			   				 " || '-' "+
    			   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
    			   				 " AND to_date( '"+cashMonthTo+"' "+
    			   				 " ||'-' "+
    			   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
    			   	map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+" "+cashYearFrom+" To "+mapMonth.get(cashMonthTo)+" "+cashYearTo);
    			   }
            	
            	queryString +="  AND mst.tda_or_tca  ='"+tpaType+"'" +    			        	
    			        	"  )a " +
    			        	"LEFT OUTER JOIN FAS_MST_ACCT_UNITS b " +
    			        	"ON a.ACCOUNTING_UNIT_ID=b.accounting_unit_id " +
    			        	"LEFT OUTER JOIN FAS_MST_ACCT_UNITS c " +
    			        	"ON c.ACCOUNTING_UNIT_ID=a.TRF_ACCOUNTING_UNIT_ID";
            	s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_Accepted_Office.jrxml");
            } else if(displayOrder.equalsIgnoreCase("ALL")){            	
            		queryString="";
            		s="";
            		queryString="SELECT TO_CHAR(a.VOUCHER_DATE,'dd/MM/yyyy')   AS VOUCHER_DATE, " +
            		"  a.ACCOUNTING_UNIT_ID                        AS ACCOUNTING_UNIT_ID, " +
            		"  a.TRF_ACCOUNTING_UNIT_ID                    AS TRF_ACCOUNTING_UNIT_ID, " +
            		"  a.voucher_no                                AS voucher_no, " +
            		"  a.TDA_OR_TCA                                AS TDA_OR_TCA, " +
            		"  a.ACCEPTING_SLNO 						   AS ACCEPTING_SLNO, " +
            		"  TO_CHAR(a.ACCEPTING_DATE,'dd/MM/yyyy') 	   AS ACCEPTING_DATE, " +
            		"  a.ORGINATING_JVR_NO                         AS ORGINATING_JVR_NO, " +
            		"  TO_CHAR(a.ORGINATING_JVR_DATE,'dd/MM/yyyy') AS ORGINATING_JVR_DATE, " +
            		"  a.ACCEPTING_JVR_NO 						   AS ACCEPTING_JVR_NO, " +
            		"  TO_CHAR(a.ACCEPTING_JVR_DATE,'dd/MM/yyyy')  AS ACCEPTING_JVR_DATE, " +
            		"  a.particulars                               AS particulars, " +
            		"  a.total_amount                              AS total_amount, " +
            		"  b.ACCOUNTING_UNIT_NAME                      AS orgin_unit, " +
            		"  c.accounting_unit_name                      AS accepting_unit " +
            		"FROM " +
            		"  (SELECT mst.VOUCHER_DATE, " +
            		"    mst.voucher_no, " +
            		"    mst.TDA_OR_TCA, " +
            		"    mst.ACCOUNTING_UNIT_ID, " +
            		"    mst.TRF_ACCOUNTING_UNIT_ID, " +
            		"    mst.ACCEPTING_SLNO, " +
            		"    mst.ACCEPTING_DATE, " +
            		"    mst.ORGINATING_JVR_NO, " +
            		"    mst.ORGINATING_JVR_DATE, " +
            		"    mst1.ACCEPTING_JVR_NO, " +
            		"    mst1.ACCEPTING_JVR_DATE, " +
            		"    mst.particulars, " +
            		"    mst.TOTAL_AMOUNT " +
            		"  FROM FAS_TDA_TCA_RAISED_MST mst, " +
            		"    FAS_TDA_TCA_RAISED_MST mst1 " +
            		"  WHERE mst.TRF_ACCOUNTING_UNIT_ID=mst1.ACCOUNTING_UNIT_ID " +
            		"  AND mst.ACCEPTING_SLNO          =mst1.VOUCHER_NO " +
            		"  AND mst.status                  ='L' " ;
            		if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
    				   	queryString+="AND mst.cashbook_month		='"+cashMonth+"'"+
    				   	  			"AND mst.cashbook_year 			='"+cashYear+"'";
    				   			map.put("monthinwords","For the "+cashYear+" "+mapMonth.get(cashMonth));
    				   }else{				   				   	
    				   	queryString+=" AND to_date(mst.cashbook_month"+
    				   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
    				   				 " || '-' "+
    				   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
    				   				 " AND to_date( '"+cashMonthTo+"' "+
    				   				 " ||'-' "+
    				   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
    				   			map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+" "+cashYearFrom+" To "+mapMonth.get(cashMonthTo)+" "+cashYearTo);
    				   }
            		queryString+=" AND (mst.TDA_OR_TCA             ='"+tpaType+"' " +
            					" OR mst.tda_or_tca               ='"+tdacbtype+"') " +
            					" AND mst.ADVICE_TYPE ='"+advType+"' " +
            					" AND mst.ACCEPTANCE_STATUS ='"+status+"' " +
            					" ORDER BY mst.voucher_no "+ 
    			        		" )a " +
    			        		"LEFT OUTER JOIN FAS_MST_ACCT_UNITS b " +
    			        		"ON a.ACCOUNTING_UNIT_ID=b.accounting_unit_id " +
    			        		"LEFT OUTER JOIN FAS_MST_ACCT_UNITS c " +
    			        		"ON c.ACCOUNTING_UNIT_ID=a.TRF_ACCOUNTING_UNIT_ID " +
    			        		"ORDER BY a.voucher_no";
            		s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_Accepted_All.jrxml");
            	}  
	            map.put("unitid",cmbAcc_UnitCode);
	            map.put("officeid",cmbOffice_code);
	            map.put("cashyear",cashYear);
	            map.put("cashmonth",cashMonth);
	            map.put("tdatype",tpaType);
	            map.put("reportName",Name1);
	            map.put("accStatus",status);
	            map.put("tdacbtype",tdacbtype);
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
        if (rtype.equalsIgnoreCase("HTML")) {
                    response.setContentType("text/html");
                    
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Accepted.html\"");
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
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Accepted.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
        }
        else if (rtype.equalsIgnoreCase("EXCEL"))   
        {
        
                response.setContentType("application/vnd.ms-excel");
                 response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Accepted.xls\"");
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
                 response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Accepted.txt\"");
                 
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
       
         else if(com_report.equalsIgnoreCase("TDARejected") ||com_report.equalsIgnoreCase("TCARejected")
        		  ||com_report.equalsIgnoreCase("TDARejectedother") ||com_report.equalsIgnoreCase("TCARejectedother")){
        	 
                 if(com_report.equalsIgnoreCase("TDARejected")) {
                	 queryString1="AND TRF_ACCOUNTING_UNIT_ID='"+cmbOffice_code+"' ";
                     tpaType="TDAO";
                     tdacbtype="TDACB";
                     Name1="TDA Rejected by you";
                 } else if(com_report.equalsIgnoreCase("TDARejectedother")) {
                	 queryString1="AND ACCOUNTING_UNIT_ID='"+cmbOffice_code+"' ";
                     tpaType="TDAO";
                     tdacbtype="TDACB";
                     Name1="TDA Rejected by Others";
                 } else if(com_report.equalsIgnoreCase("TCARejected")) {
                	 queryString1="AND TRF_ACCOUNTING_UNIT_ID='"+cmbOffice_code+"' ";
                     tpaType="TCAO";
                     tdacbtype="TCACB";
                     Name1="TCA Rejected by you";
                 } else if(com_report.equalsIgnoreCase("TCARejectedother")) {
                	 queryString1="AND ACCOUNTING_UNIT_ID='"+cmbOffice_code+"' ";
                     tpaType="TCAO";
                     tdacbtype="TCACB";
                     Name1="TCA Rejected by Others";
                  
                 }
             try {
                 Map map = new HashMap();
                 System.out.println("calling servlet... tpaType "+tpaType+" "+advType+" cmbOffice_code "+cmbOffice_code);
                 if(displayOrder.equalsIgnoreCase("RW")) {
                 queryString = "";
                 s = "";
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
          		"  mst.TOTAL_AMOUNT                              AS TOTAL_AMOUNT " +
          		"FROM FAS_TDA_TCA_RAISED_MST mst " +
          		"WHERE mst.status          ='L' ";
          		if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
  				   	queryString+="AND mst.cashbook_month		='"+cashMonth+"'"+
  				   	  			"AND mst.cashbook_year 			='"+cashYear+"'";
  				   			map.put("monthinwords","For the "+cashYear+" "+mapMonth.get(cashMonth));
  				   }else{				   				   	
  				   	queryString+=" AND to_date(mst.cashbook_month"+
  				   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
  				   				 " || '-' "+
  				   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
  				   				 " AND to_date( '"+cashMonthTo+"' "+
  				   				 " ||'-' "+
  				   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
  				   			map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+" "+cashYearFrom+" To "+mapMonth.get(cashMonthTo)+" "+cashYearTo);
  				   }
          		queryString+="AND (mst.TDA_OR_TCA      ='"+tpaType+"'"+
			                 	 "OR mst.tda_or_tca         ='"+tdacbtype+"') " +
			                 	"AND mst.accounting_for_office_id IN " +
			                 	"  (SELECT office_id " +
			                 	"  FROM COM_MST_ALL_OFFICES_VIEW " +
			                 	"  WHERE region_office_id = '"+cmbOffice_code+"'" +
			                 	"  ) " +
			                 	 "AND mst.ACCEPTANCE_STATUS ='N' " +
			                 	 "ORDER BY mst.voucher_no";
         			s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_Rejected_Region.jrxml");
                 } else if(displayOrder.equalsIgnoreCase("OW")){
                 	queryString="";
                 	s="";
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
             		"  mst.TOTAL_AMOUNT                              AS TOTAL_AMOUNT " +
             		"FROM FAS_TDA_TCA_RAISED_MST mst " +
             		"WHERE mst.status          ='L' ";
             		if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
     				   	queryString+="AND mst.cashbook_month		='"+cashMonth+"'"+
     				   	  			"AND mst.cashbook_year 			='"+cashYear+"'";
     				   			map.put("monthinwords","For the "+cashYear+" "+mapMonth.get(cashMonth));
     				   }else{				   				   	
     				   	queryString+=" AND to_date(mst.cashbook_month"+
     				   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
     				   				 " || '-' "+
     				   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
     				   				 " AND to_date( '"+cashMonthTo+"' "+
     				   				 " ||'-' "+
     				   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
     				   			map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+" "+cashYearFrom+" To "+mapMonth.get(cashMonthTo)+" "+cashYearTo);
     				   }
             		queryString+="AND (mst.TDA_OR_TCA      ='"+tpaType+"'"+
			                 	 "OR mst.tda_or_tca         ='"+tdacbtype+"') " +
			                 	  queryString1+
			                 	 "AND mst.ACCEPTANCE_STATUS IN ('N','R') " +
			                 	 "ORDER BY mst.voucher_no";
                 	s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_Rejected_Office.jrxml");
                 } else if(displayOrder.equalsIgnoreCase("ALL")){            	
                 		queryString="";
                 		s="";
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
                 		"  mst.TOTAL_AMOUNT                              AS TOTAL_AMOUNT " +
                 		"FROM FAS_TDA_TCA_RAISED_MST mst " +
                 		"WHERE mst.status          ='L' ";
                 		if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
         				   	queryString+="AND mst.cashbook_month		='"+cashMonth+"'"+
         				   	  			"AND mst.cashbook_year 			='"+cashYear+"'";
         				   			map.put("monthinwords","For the "+cashYear+" "+mapMonth.get(cashMonth));
         				   }else{				   				   	
         				   	queryString+=" AND to_date(mst.cashbook_month"+
         				   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
         				   				 " || '-' "+
         				   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
         				   				 " AND to_date( '"+cashMonthTo+"' "+
         				   				 " ||'-' "+
         				   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
         				   			map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+" "+cashYearFrom+" To "+mapMonth.get(cashMonthTo)+" "+cashYearTo);
         				   }
                 		queryString+="AND (mst.TDA_OR_TCA      ='"+tpaType+"'"+
				                 	 "OR mst.tda_or_tca         ='"+tdacbtype+"') " +
				                 	 "AND mst.ACCEPTANCE_STATUS ='N' " +
				                 	 "ORDER BY mst.voucher_no";
                 		s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_Rejected_All.jrxml");
                 	}   
     	            map.put("unitid",cmbAcc_UnitCode);
     	            map.put("officeid",cmbOffice_code);
     	            map.put("cashyear",cashYear);
     	            map.put("cashmonth",cashMonth);
     	            map.put("tdatype",tpaType);
     	            map.put("reportName",Name1);
     	            map.put("accStatus",status);
     	            map.put("tdacbtype",tdacbtype);
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
                         
                         response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Rejected.html\"");
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
                         response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Rejected.pdf\"");
                         OutputStream out = response.getOutputStream();
                         out.write(buf, 0, buf.length);
                         out.close();
             }
             else if (rtype.equalsIgnoreCase("EXCEL"))   
             {
             
                     response.setContentType("application/vnd.ms-excel");
                      response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Rejected.xls\"");
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
                      response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Rejected.txt\"");
                      
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
       
           else if(com_report.equalsIgnoreCase("TDAPending") || com_report.equalsIgnoreCase("TCAPending")
        		    || com_report.equalsIgnoreCase("TDAPendingothers") || com_report.equalsIgnoreCase("TCAPendingother")){
               if(com_report.equalsIgnoreCase("TDAPending")) {
            	   queryString1="AND TRF_ACCOUNTING_UNIT_ID='"+cmbOffice_code+"' ";
                   tpaType="TDAO";
                   tdacbtype="TDACB";
                   Name1="TDA Pending with you";
               } else if(com_report.equalsIgnoreCase("TDAPendingothers")) {
            	   queryString1="AND ACCOUNTING_UNIT_ID='"+cmbOffice_code+"' ";
                   tpaType="TDAO";
                   tdacbtype="TDACB";
                   Name1="TDA Pending with Others";
               } else if(com_report.equalsIgnoreCase("TCAPending")) {
            	   queryString1="AND TRF_ACCOUNTING_UNIT_ID='"+cmbOffice_code+"' ";
                   tpaType="TCAO";
                   tdacbtype="TCACB";
                   Name1="TCA Pending with you";
               } else if(com_report.equalsIgnoreCase("TCAPendingother")) {
            	   queryString1="AND ACCOUNTING_UNIT_ID='"+cmbOffice_code+"' ";
                   tpaType="TCAO";
                   tdacbtype="TCACB";
                   Name1="TCA Pending with Others";
               }
        try {    
            Map map = new HashMap();
            if(displayOrder.equalsIgnoreCase("RW")) {
                queryString = "";
                s = "";
                queryString="SELECT TO_CHAR(a.VOUCHER_DATE,'dd/MM/yyyy') AS VOUCHER_DATE, " +
        		"  a.voucher_no                              AS voucher_no, " +
        		"  a.ACCOUNTING_UNIT_ID                      AS ACCOUNTING_UNIT_ID, " +
        		"  a.TRF_ACCOUNTING_UNIT_ID                  AS TRF_ACCOUNTING_UNIT_ID, " +
        		"  a.particulars                             AS particulars, " +
        		"  a.total_amount                            AS total_amount, " +
        		"  a.TDA_OR_TCA                              AS TDA_OR_TCA, " +
        		"  a.REASON_FOR_NON_ACCEPT                   AS REASON_FOR_NON_ACCEPT, " +
        		"  a.status                                  AS status, " +
        		"  b.ACCOUNTING_UNIT_NAME                    AS orgin_unit, " +
        		"  c.accounting_unit_name                    AS accepting_unit " +
        		" FROM " +
        		"  (SELECT mst.VOUCHER_DATE, " +
        		"    mst.voucher_no, " +
        		"    mst.ACCOUNTING_UNIT_ID, " +
        		"    mst.TRF_ACCOUNTING_UNIT_ID, " +
        		"    mst.particulars, " +
        		"    mst.total_amount, " +
        		"    mst.TDA_OR_TCA, " +
        		"    mst.REASON_FOR_NON_ACCEPT, " +
        		"    DECODE(mst.acceptance_status,NULL,'P',mst.acceptance_status) AS status " +
        		"  FROM FAS_TDA_TCA_RAISED_MST mst " +
        		"  WHERE mst.status           ='L' "+
                "  AND mst.accounting_for_office_id IN " +
                "    (SELECT office_id " +
                "    FROM COM_MST_ALL_OFFICES_VIEW " +
                "    WHERE region_office_id ='"+cmbOffice_code+"'" +
                "    ) " ;
        		if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
				   	queryString+="AND mst.cashbook_month		='"+cashMonth+"'"+
				   	  			"AND mst.cashbook_year 			='"+cashYear+"'";
				   			map.put("monthinwords","For the "+cashYear+" "+mapMonth.get(cashMonth));
				   }else{				   				   	
				   	queryString+=" AND to_date(mst.cashbook_month"+
				   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
				   				 " || '-' "+
				   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
				   				 " AND to_date( '"+cashMonthTo+"' "+
				   				 " ||'-' "+
				   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
				   			map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+" "+cashYearFrom+" To "+mapMonth.get(cashMonthTo)+" "+cashYearTo);
				   }
        		queryString+="  AND (mst.TDA_OR_TCA        ='"+tpaType+"' " +
        		"  OR mst.TDA_OR_TCA          ='"+tdacbtype+"') " +
        		"  AND mst.accounting_for_office_id='"+cmbOffice_code+"'"+
        		"  AND mst.acceptance_status IS NULL " +
        		"  )a " +
        		"LEFT OUTER JOIN FAS_MST_ACCT_UNITS b " +
        		"ON a.ACCOUNTING_UNIT_ID=b.accounting_unit_id " +
        		"LEFT OUTER JOIN FAS_MST_ACCT_UNITS c " +
        		"ON c.ACCOUNTING_UNIT_ID=a.TRF_ACCOUNTING_UNIT_ID " +
        		"ORDER BY a.voucher_no";
        			s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_Pending_Region.jrxml");
                } else if(displayOrder.equalsIgnoreCase("OW")){
                	queryString="";
                	s="";
                	queryString="SELECT TO_CHAR(a.VOUCHER_DATE,'dd/MM/yyyy') AS VOUCHER_DATE, " +
            		"  a.voucher_no                              AS voucher_no, " +
            		"  a.ACCOUNTING_UNIT_ID                      AS ACCOUNTING_UNIT_ID, " +
            		"  a.TRF_ACCOUNTING_UNIT_ID                  AS TRF_ACCOUNTING_UNIT_ID, " +
            		"  a.particulars                             AS particulars, " +
            		"  a.total_amount                            AS total_amount, " +
            		"  a.TDA_OR_TCA                              AS TDA_OR_TCA, " +
            		"  a.REASON_FOR_NON_ACCEPT                   AS REASON_FOR_NON_ACCEPT, " +
            		"  a.status                                  AS status, " +
            		"  b.ACCOUNTING_UNIT_NAME                    AS orgin_unit, " +
            		"  c.accounting_unit_name                    AS accepting_unit " +
            		" FROM " +
            		"  (SELECT mst.VOUCHER_DATE, " +
            		"    mst.voucher_no, " +
            		"    mst.ACCOUNTING_UNIT_ID, " +
            		"    mst.TRF_ACCOUNTING_UNIT_ID, " +
            		"    mst.particulars, " +
            		"    mst.total_amount, " +
            		"    mst.TDA_OR_TCA, " +
            		"    mst.REASON_FOR_NON_ACCEPT, " +
            		"    DECODE(mst.acceptance_status,NULL,'P',mst.acceptance_status) AS status " +
            		"  FROM FAS_TDA_TCA_RAISED_MST mst " +
            		"  WHERE mst.status           ='L' ";
            		if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
    				   	queryString+="AND mst.cashbook_month		='"+cashMonth+"'"+
    				   	  			"AND mst.cashbook_year 			='"+cashYear+"'";
    				   			map.put("monthinwords","For the "+cashYear+" "+mapMonth.get(cashMonth));
    				   }else{				   				   	
    				   	queryString+=" AND to_date(mst.cashbook_month"+
    				   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
    				   				 " || '-' "+
    				   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
    				   				 " AND to_date( '"+cashMonthTo+"' "+
    				   				 " ||'-' "+
    				   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
    				   			map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+" "+cashYearFrom+" To "+mapMonth.get(cashMonthTo)+" "+cashYearTo);
    				   }
            		queryString+="  AND (mst.TDA_OR_TCA        ='"+tpaType+"' " +
            		"  OR mst.TDA_OR_TCA          ='"+tdacbtype+"') " +
            		queryString1 +
            		"  AND mst.acceptance_status IS NULL " +
            		"  )a " +
            		"LEFT OUTER JOIN FAS_MST_ACCT_UNITS b " +
            		"ON a.ACCOUNTING_UNIT_ID=b.accounting_unit_id " +
            		"LEFT OUTER JOIN FAS_MST_ACCT_UNITS c " +
            		"ON c.ACCOUNTING_UNIT_ID=a.TRF_ACCOUNTING_UNIT_ID " +
            		"ORDER BY a.voucher_no";
                	s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_Pending.jrxml");
                } else if(displayOrder.equalsIgnoreCase("ALL")){ 
                		queryString="";
                		s="";
                		queryString="SELECT TO_CHAR(a.VOUCHER_DATE,'dd/MM/yyyy') AS VOUCHER_DATE, " +
                		"  a.voucher_no                              AS voucher_no, " +
                		"  a.ACCOUNTING_UNIT_ID                      AS ACCOUNTING_UNIT_ID, " +
                		"  a.TRF_ACCOUNTING_UNIT_ID                  AS TRF_ACCOUNTING_UNIT_ID, " +
                		"  a.particulars                             AS particulars, " +
                		"  a.total_amount                            AS total_amount, " +
                		"  a.TDA_OR_TCA                              AS TDA_OR_TCA, " +
                		"  a.REASON_FOR_NON_ACCEPT                   AS REASON_FOR_NON_ACCEPT, " +
                		"  a.status                                  AS status, " +
                		"  b.ACCOUNTING_UNIT_NAME                    AS orgin_unit, " +
                		"  c.accounting_unit_name                    AS accepting_unit " +
                		" FROM " +
                		"  (SELECT mst.VOUCHER_DATE, " +
                		"    mst.voucher_no, " +
                		"    mst.ACCOUNTING_UNIT_ID, " +
                		"    mst.TRF_ACCOUNTING_UNIT_ID, " +
                		"    mst.particulars, " +
                		"    mst.total_amount, " +
                		"    mst.TDA_OR_TCA, " +
                		"    mst.REASON_FOR_NON_ACCEPT, " +
                		"    DECODE(mst.acceptance_status,NULL,'P',mst.acceptance_status) AS status " +
                		"  FROM FAS_TDA_TCA_RAISED_MST mst " +
                		"  WHERE mst.status           ='L' ";
                		if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
        				   	queryString+="AND mst.cashbook_month		='"+cashMonth+"'"+
        				   	  			"AND mst.cashbook_year 			='"+cashYear+"'";
        				   			map.put("monthinwords","For the "+cashYear+" "+mapMonth.get(cashMonth));
        				   }else{				   				   	
        				   	queryString+=" AND to_date(mst.cashbook_month"+
        				   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
        				   				 " || '-' "+
        				   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
        				   				 " AND to_date( '"+cashMonthTo+"' "+
        				   				 " ||'-' "+
        				   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
        				   			map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+" "+cashYearFrom+" To "+mapMonth.get(cashMonthTo)+" "+cashYearTo);
        				   }
                		queryString+="  AND (mst.TDA_OR_TCA        ='"+tpaType+"' " +
                		"  OR mst.TDA_OR_TCA          ='"+tdacbtype+"') " +
                		"  AND mst.acceptance_status IS NULL " +
                		"  )a " +
                		"LEFT OUTER JOIN FAS_MST_ACCT_UNITS b " +
                		"ON a.ACCOUNTING_UNIT_ID=b.accounting_unit_id " +
                		"LEFT OUTER JOIN FAS_MST_ACCT_UNITS c " +
                		"ON c.ACCOUNTING_UNIT_ID=a.TRF_ACCOUNTING_UNIT_ID " +
                		"ORDER BY a.voucher_no";             		
                		s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_Pending_All.jrxml");
                	}   
    	            map.put("unitid",cmbAcc_UnitCode);
    	            map.put("officeid",cmbOffice_code);
    	            map.put("cashyear",cashYear);
    	            map.put("cashmonth",cashMonth);
    	            map.put("tdatype",tpaType);
    	            map.put("reportName",Name1);
    	            map.put("accStatus",status);
    	            map.put("tdacbtype",tdacbtype);
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
                    
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Pending.html\"");
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
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Pending.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
        }
        else if (rtype.equalsIgnoreCase("EXCEL"))   
        {
        
                response.setContentType("application/vnd.ms-excel");
                 response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Pending.xls\"");
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
                 response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Accepted.txt\"");
                 
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
      
          try {
              Map map = new HashMap();
              if(displayOrder.equalsIgnoreCase("RW")) {
                  queryString = "";
                  s = "";
                  queryString="SELECT TO_CHAR(a.VOUCHER_DATE,'dd/MM/yyyy')   AS VOUCHER_DATE, " +
            		"  a.voucher_no                                AS voucher_no, " +
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
            		"  a.PARTICULARS                               AS PARTICULARS, " +
            		"  b.ACCOUNTING_UNIT_NAME                      AS orginatedUnit " +
            		" FROM " +
            		"  (SELECT mst.VOUCHER_DATE, " +
            		"    mst.voucher_no, " +
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
            		"    mst.PARTICULARS " +
            		"  FROM FAS_TDA_TCA_RAISED_MST mst, " +
            		"    FAS_TDA_TCA_RAISED_MST mst1 " +
            		"  WHERE mst.TRF_ACCOUNTING_UNIT_ID=mst1.ACCOUNTING_UNIT_ID " +
            		"  AND mst.ACCEPTING_SLNO          =mst1.VOUCHER_NO " +
            		"  AND mst.ORGINATING_JVR_NO      IS NOT NULL " +
            		"  AND mst.orginating_jvr_date    IS NOT NULL " +
            		"  AND mst1.ACCEPTING_JVR_NO      IS NOT NULL " +
            		"  AND mst1.accepting_jvr_date    IS NOT NULL " +
            		"  AND (mst.RESPONDING_JVR_NO      IS NULL OR mst.responding_jvr_no =0)" +
            		"  AND mst.RESPONDING_JVR_DATE    IS NULL " +
            		"  AND mst.STATUS                  ='L' ";
            		if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
    				   	queryString+="AND mst.cashbook_month		='"+cashMonth+"'"+
    				   	  			"AND mst.cashbook_year 			='"+cashYear+"'";
    				   			map.put("monthinwords","For the "+cashYear+" "+mapMonth.get(cashMonth));
    				   }else{				   				   	
    				   	queryString+=" AND to_date(mst.cashbook_month"+
    				   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
    				   				 " || '-' "+
    				   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
    				   				 " AND to_date( '"+cashMonthTo+"' "+
    				   				 " ||'-' "+
    				   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
    				   			map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+" "+cashYearFrom+" To "+mapMonth.get(cashMonthTo)+" "+cashYearTo);
    				   }
            		queryString+="  AND (mst.TDA_OR_TCA             ='"+tpaType+"' " +
            		"  OR mst.TDA_OR_TCA               ='"+tpaTypeCB+"') " +
            		"  AND mst.ACCOUNTING_FOR_OFFICE_ID IN " +
            		"    (SELECT office_id " +
            		"    FROM COM_MST_ALL_OFFICES_VIEW " +
            		"    WHERE region_office_id ='"+cmbOffice_code+"'"+
            		"    ) " +
            		"  )a " +
            		" LEFT OUTER JOIN " +
            		"  (SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS " +
            		"  )b " +
            		"ON a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID " +
            		"ORDER BY a.voucher_no";
          			s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_Resp_pending_Region.jrxml");
                  } else if(displayOrder.equalsIgnoreCase("OW")){ 
                  	queryString="";
                  	s="";
                  	queryString="SELECT TO_CHAR(a.VOUCHER_DATE,'dd/MM/yyyy')   AS VOUCHER_DATE, " +
              		"  a.voucher_no                                AS voucher_no, " +
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
              		"  a.PARTICULARS                               AS PARTICULARS, " +
              		"  b.ACCOUNTING_UNIT_NAME                      AS orginatedUnit " +
              		" FROM " +
              		"  (SELECT mst.VOUCHER_DATE, " +
              		"    mst.voucher_no, " +
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
              		"    mst.PARTICULARS " +
              		"  FROM FAS_TDA_TCA_RAISED_MST mst, " +
              		"    FAS_TDA_TCA_RAISED_MST mst1 " +
              		"  WHERE mst.TRF_ACCOUNTING_UNIT_ID=mst1.ACCOUNTING_UNIT_ID " +
              		"  AND mst.ACCEPTING_SLNO          =mst1.VOUCHER_NO " +
              		"  AND mst.ORGINATING_JVR_NO      IS NOT NULL " +
              		"  AND mst.orginating_jvr_date    IS NOT NULL " +
              		"  AND mst1.ACCEPTING_JVR_NO      IS NOT NULL " +
              		"  AND mst1.accepting_jvr_date    IS NOT NULL " +
              		"  AND ( mst.RESPONDING_JVR_NO      IS NULL OR mst.responding_jvr_no = 0 ) " +
              		"  AND mst.RESPONDING_JVR_DATE    IS NULL " +
              		"  AND mst.STATUS                  ='L' ";
              		if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
      				   	queryString+="AND mst.cashbook_month		='"+cashMonth+"'"+
      				   	  			"AND mst.cashbook_year 			='"+cashYear+"'";
      				   			map.put("monthinwords","For the "+cashYear+" "+mapMonth.get(cashMonth));
      				   }else{				   				   	
      				   	queryString+=" AND to_date(mst.cashbook_month"+
      				   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
      				   				 " || '-' "+
      				   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
      				   				 " AND to_date( '"+cashMonthTo+"' "+
      				   				 " ||'-' "+
      				   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
      				   			map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+" "+cashYearFrom+" To "+mapMonth.get(cashMonthTo)+" "+cashYearTo);
      				   }
              		queryString+="  AND (mst.TDA_OR_TCA             ='"+tpaType+"' " +
              		"  OR mst.TDA_OR_TCA               ='"+tpaTypeCB+"') " + 
              		"  AND mst.accounting_for_office_id='"+cmbOffice_code+"'"+
              		"  )a " +
              		" LEFT OUTER JOIN " +
              		"  (SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS " +
              		"  )b " +
              		"ON a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID " +
              		"ORDER BY a.voucher_no";
                  	s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_Resp_pending_Office.jrxml");
                  } else if(displayOrder.equalsIgnoreCase("ALL")){ 
                  		queryString="";
                  		s="";
                  		queryString="SELECT TO_CHAR(a.VOUCHER_DATE,'dd/MM/yyyy')   AS VOUCHER_DATE, " +
                  		"  a.voucher_no                                AS voucher_no, " +
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
                  		"  a.PARTICULARS                               AS PARTICULARS, " +
                  		"  b.ACCOUNTING_UNIT_NAME                      AS orginatedUnit " +
                  		"FROM " +
                  		"  (SELECT mst.VOUCHER_DATE, " +
                  		"    mst.voucher_no, " +
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
                  		"    mst.PARTICULARS " +
                  		"  FROM FAS_TDA_TCA_RAISED_MST mst, " +
                  		"    FAS_TDA_TCA_RAISED_MST mst1 " +
                  		"  WHERE mst.TRF_ACCOUNTING_UNIT_ID=mst1.ACCOUNTING_UNIT_ID " +
                  		"  AND mst.ACCEPTING_SLNO          =mst1.VOUCHER_NO " +
                  		"  AND mst.ORGINATING_JVR_NO      IS NOT NULL " +
                  		"  AND mst.orginating_jvr_date    IS NOT NULL " +
                  		"  AND mst1.ACCEPTING_JVR_NO      IS NOT NULL " +
                  		"  AND mst1.accepting_jvr_date    IS NOT NULL " +
                  		"  AND (mst.RESPONDING_JVR_NO      IS NULL OR mst.responding_jvr_no = 0 )" +
                  		"  AND mst.RESPONDING_JVR_DATE    IS NULL " +
                  		"  AND mst.STATUS                  ='L' ";
                  		if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
          				   	queryString+="AND mst.cashbook_month		='"+cashMonth+"'"+
          				   	  			"AND mst.cashbook_year 			='"+cashYear+"'";
          				   			map.put("monthinwords","For the "+cashYear+" "+mapMonth.get(cashMonth));
          				   }else{				   				   	
          				   	queryString+=" AND to_date(mst.cashbook_month"+
          				   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
          				   				 " || '-' "+
          				   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
          				   				 " AND to_date( '"+cashMonthTo+"' "+
          				   				 " ||'-' "+
          				   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
          				   			map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+" "+cashYearFrom+" To "+mapMonth.get(cashMonthTo)+" "+cashYearTo);
          				   }
                  		queryString+="  AND (mst.TDA_OR_TCA             ='"+tpaType+"' " +
                  		"  OR mst.TDA_OR_TCA               ='"+tpaTypeCB+"') " +                  		
                  		"  )a " +
                  		" LEFT OUTER JOIN " +
                  		"  (SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS " +
                  		"  )b " +
                  		"ON a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID " +
                  		"ORDER BY a.voucher_no";           		
                  		s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_Responding_Report.jrxml");
                  	}   
      	            map.put("unitid",cmbAcc_UnitCode);
      	            map.put("officeid",cmbOffice_code);
      	            map.put("cashyear",cashYear);
      	            map.put("cashmonth",cashMonth);
      	            map.put("tdatype",tpaType);
      	            map.put("reportName",Name1);
      	            map.put("accStatus",status);
      	            map.put("tdacbtype",tdacbtype);
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
                      
                      response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Pending.html\"");
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
                      response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Pending.pdf\"");
                      OutputStream out = response.getOutputStream();
                      out.write(buf, 0, buf.length);
                      out.close();
          }
          else if (rtype.equalsIgnoreCase("EXCEL"))   
          {
          
                  response.setContentType("application/vnd.ms-excel");
                   response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Pending.xls\"");
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
                   response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Accepted.txt\"");
                   
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
               Name1="TCA Suspense Heads Cleared";
                
              
              }
           
               try {
                   Map map = new HashMap();
                   if(displayOrder.equalsIgnoreCase("RW")) {
                       queryString = "";
                       s = "";
                       queryString = "SELECT TO_CHAR(a.VOUCHER_DATE,'dd/MM/yyyy')   AS VOUCHER_DATE, " +
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
                  		"  a.RESPONDING_JVR_NO                         AS RESPONDING_JVR_NO, " +
                  		"  TO_CHAR(a.RESPONDING_JVR_DATE,'dd/MM/yyyy') AS RESPONDING_JVR_DATE, " +
                  		"  a.STATUS                                    AS STATUS, " +
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
                  		"    mst.RESPONDING_JVR_NO, " +
                  		"    mst.RESPONDING_JVR_DATE, " +
                  		"    mst.STATUS, " +
                  		"    mst.PARTICULARS " +
                  		"  FROM FAS_TDA_TCA_RAISED_MST mst, " +
                  		"    FAS_TDA_TCA_RAISED_MST mst1 " +
                  		"  WHERE mst.TRF_ACCOUNTING_UNIT_ID               =mst1.ACCOUNTING_UNIT_ID " +
                  		"  AND mst.ACCEPTING_SLNO                         =mst1.VOUCHER_NO " +
                  		"  AND mst.ORGINATING_JVR_NO                     IS NOT NULL " +
                  		"  AND mst.orginating_jvr_date                   IS NOT NULL " +
                  		"  AND mst1.ACCEPTING_JVR_NO                     IS NOT NULL " +
                  		"  AND mst1.accepting_jvr_date                   IS NOT NULL " +
                  		"  AND mst.RESPONDING_JVR_NO                     IS NOT NULL " +
                  		"  AND mst.RESPONDING_JVR_DATE                   IS NOT NULL " +
                  		"  AND mst.STATUS                                 ='L' " +
                  		"  AND mst.ACCOUNTING_FOR_OFFICE_ID IN " +
                  		"    (SELECT office_id " +
                  		"    FROM COM_MST_ALL_OFFICES_VIEW " +
                  		"    WHERE region_office_id ='"+cmbOffice_code+"' " +
                  		"    ) " +
                  		"  AND (mst.TDA_OR_TCA                            ='"+tpaType+"' " +
                  		"  OR mst.TDA_OR_TCA                              ='"+tpaTypeCB+"') ";
                  		if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
          				   	queryString+="AND EXTRACT(MONTH FROM mst.RESPONDING_JVR_DATE) ='"+cashMonth+"'"+
          				   	  			"AND EXTRACT(YEAR FROM mst.RESPONDING_JVR_DATE) ='"+cashYear+"'";
          				   			map.put("monthinwords","For the "+cashYear+" "+mapMonth.get(cashMonth));
          				   }else{				   				   	
          				   	queryString+=" AND to_date(EXTRACT(MONTH FROM mst.RESPONDING_JVR_DATE)"+
          				   				 " ||'-'|| + EXTRACT(YEAR FROM mst.RESPONDING_JVR_DATE), 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
          				   				 " || '-' "+
          				   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
          				   				 " AND to_date( '"+cashMonthTo+"' "+
          				   				 " ||'-' "+
          				   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
          				   			map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+" "+cashYearFrom+" To "+mapMonth.get(cashMonthTo)+" "+cashYearTo);
          				   }
                  		queryString+="  )a " +
                  		"LEFT OUTER JOIN " +
                  		"  (SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS " +
                  		"  )b " +
                  		"ON a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID " +
                  		"ORDER BY a.VOUCHER_NO";
               			s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_Resp_Cleared_region.jrxml");
                       } else if(displayOrder.equalsIgnoreCase("OW")){ 
                       	queryString="";
                       	s="";
                       	queryString = "SELECT TO_CHAR(a.VOUCHER_DATE,'dd/MM/yyyy')   AS VOUCHER_DATE, " +
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
                   		"  a.RESPONDING_JVR_NO                         AS RESPONDING_JVR_NO, " +
                   		"  TO_CHAR(a.RESPONDING_JVR_DATE,'dd/MM/yyyy') AS RESPONDING_JVR_DATE, " +
                   		"  a.STATUS                                    AS STATUS, " +
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
                   		"    mst.RESPONDING_JVR_NO, " +
                   		"    mst.RESPONDING_JVR_DATE, " +
                   		"    mst.STATUS, " +
                   		"    mst.PARTICULARS " +
                   		"  FROM FAS_TDA_TCA_RAISED_MST mst, " +
                   		"    FAS_TDA_TCA_RAISED_MST mst1 " +
                   		"  WHERE mst.TRF_ACCOUNTING_UNIT_ID               =mst1.ACCOUNTING_UNIT_ID " +
                   		"  AND mst.ACCEPTING_SLNO                         =mst1.VOUCHER_NO " +
                   		"  AND mst.ORGINATING_JVR_NO                     IS NOT NULL " +
                   		"  AND mst.orginating_jvr_date                   IS NOT NULL " +
                   		"  AND mst1.ACCEPTING_JVR_NO                     IS NOT NULL " +
                   		"  AND mst1.accepting_jvr_date                   IS NOT NULL " +
                   		"  AND mst.RESPONDING_JVR_NO                     IS NOT NULL " +
                   		"  AND mst.RESPONDING_JVR_DATE                   IS NOT NULL " +
                   		"  AND mst.STATUS                                 ='L' " +
                   		"  AND mst.accounting_for_office_id='"+cmbOffice_code+"'"+
                   		"  AND (mst.TDA_OR_TCA                            ='"+tpaType+"' " +
                   		"  OR mst.TDA_OR_TCA                              ='"+tpaTypeCB+"') ";
                   		if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
           				   	queryString+="AND EXTRACT(MONTH FROM mst.RESPONDING_JVR_DATE) ='"+cashMonth+"'"+
           				   	  			"AND EXTRACT(YEAR FROM mst.RESPONDING_JVR_DATE) ='"+cashYear+"'";
           				   			map.put("monthinwords","For the "+cashYear+" "+mapMonth.get(cashMonth));
           				   }else{				   				   	
           				   	queryString+=" AND to_date(EXTRACT(MONTH FROM mst.RESPONDING_JVR_DATE)"+
           				   				 " ||'-'|| + EXTRACT(YEAR FROM mst.RESPONDING_JVR_DATE), 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
           				   				 " || '-' "+
           				   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
           				   				 " AND to_date( '"+cashMonthTo+"' "+
           				   				 " ||'-' "+
           				   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
           				   			map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+" "+cashYearFrom+" To "+mapMonth.get(cashMonthTo)+" "+cashYearTo);
           				   }
                   		queryString+="  )a " +
                   		"LEFT OUTER JOIN " +
                   		"  (SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS " +
                   		"  )b " +
                   		"ON a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID " +
                   		"ORDER BY a.VOUCHER_NO";
                       	s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_Resp_Cleared_Office.jrxml");
                       } else if(displayOrder.equalsIgnoreCase("ALL")){ 
                       		queryString="";
                       		s="";                       		
                       		queryString = "SELECT TO_CHAR(a.VOUCHER_DATE,'dd/MM/yyyy')   AS VOUCHER_DATE, " +
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
                       		"  a.RESPONDING_JVR_NO                         AS RESPONDING_JVR_NO, " +
                       		"  TO_CHAR(a.RESPONDING_JVR_DATE,'dd/MM/yyyy') AS RESPONDING_JVR_DATE, " +
                       		"  a.STATUS                                    AS STATUS, " +
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
                       		"    mst.RESPONDING_JVR_NO, " +
                       		"    mst.RESPONDING_JVR_DATE, " +
                       		"    mst.STATUS, " +
                       		"    mst.PARTICULARS " +
                       		"  FROM FAS_TDA_TCA_RAISED_MST mst, " +
                       		"    FAS_TDA_TCA_RAISED_MST mst1 " +
                       		"  WHERE mst.TRF_ACCOUNTING_UNIT_ID               =mst1.ACCOUNTING_UNIT_ID " +
                       		"  AND mst.ACCEPTING_SLNO                         =mst1.VOUCHER_NO " +
                       		"  AND mst.ORGINATING_JVR_NO                     IS NOT NULL " +
                       		"  AND mst.orginating_jvr_date                   IS NOT NULL " +
                       		"  AND mst1.ACCEPTING_JVR_NO                     IS NOT NULL " +
                       		"  AND mst1.accepting_jvr_date                   IS NOT NULL " +
                       		"  AND mst.RESPONDING_JVR_NO                     IS NOT NULL " +
                       		"  AND mst.RESPONDING_JVR_DATE                   IS NOT NULL " +
                       		"  AND mst.STATUS                                 ='L' " +
                       		"  AND (mst.TDA_OR_TCA                            ='"+tpaType+"' " +
                       		"  OR mst.TDA_OR_TCA                              ='"+tpaTypeCB+"') ";
                       		if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
               				   	queryString+="AND EXTRACT(MONTH FROM mst.RESPONDING_JVR_DATE) ='"+cashMonth+"'"+
               				   	  			"AND EXTRACT(YEAR FROM mst.RESPONDING_JVR_DATE) ='"+cashYear+"'";
               				   			map.put("monthinwords","For the "+cashYear+" "+mapMonth.get(cashMonth));
               				   }else{				   				   	
               				   	queryString+=" AND to_date(EXTRACT(MONTH FROM mst.RESPONDING_JVR_DATE)"+
               				   				 " ||'-'|| + EXTRACT(YEAR FROM mst.RESPONDING_JVR_DATE), 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
               				   				 " || '-' "+
               				   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
               				   				 " AND to_date( '"+cashMonthTo+"' "+
               				   				 " ||'-' "+
               				   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
               				   			map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+" "+cashYearFrom+" To "+mapMonth.get(cashMonthTo)+" "+cashYearTo);
               				   }
                       		queryString+="  )a " +
                       		"LEFT OUTER JOIN " +
                       		"  (SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS " +
                       		"  )b " +
                       		"ON a.ACCOUNTING_UNIT_ID=b.ACCOUNTING_UNIT_ID " +
                       		"ORDER BY a.VOUCHER_NO";
                       		s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_Responding_all_cleared.jrxml");
                       	}   
           	            map.put("unitid",cmbAcc_UnitCode);
           	            map.put("officeid",cmbOffice_code);
           	            map.put("cashyear",cashYear);
           	            map.put("cashmonth",cashMonth);
           	            map.put("tdatype",tpaType);
           	            map.put("reportName",Name1);
           	            map.put("accStatus",status);
           	            map.put("tdacbtype",tdacbtype);
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
                           
                           response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Pending.html\"");
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
                           response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Pending.pdf\"");
                           OutputStream out = response.getOutputStream();
                           out.write(buf, 0, buf.length);
                           out.close();
               }
               else if (rtype.equalsIgnoreCase("EXCEL"))   
               {
               
                       response.setContentType("application/vnd.ms-excel");
                        response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Pending.xls\"");
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
                        response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Accepted.txt\"");
                        
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
           
       else if(com_report.equalsIgnoreCase("bothTdaTca") )
       {
               Name1="TDA/TCA ALL";
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
           
            Map map = new HashMap();
            if(displayOrder.equalsIgnoreCase("RW")) {
                queryString = "";
                s = "";
                queryString="SELECT TO_CHAR(a.VOUCHER_DATE,'dd/MM/yyyy')   AS VOUCHER_DATE, " +
			        		"  a.ACCOUNTING_UNIT_ID                        AS ACCOUNTING_UNIT_ID, " +
			        		"  a.TRF_ACCOUNTING_UNIT_ID                    AS TRF_ACCOUNTING_UNIT_ID, " +
			        		"  a.voucher_no                                AS voucher_no, " +
			        		"  a.ORGINATING_JVR_NO                         AS ORGINATING_JVR_NO, " +
			        		"  TO_CHAR(a.ORGINATING_JVR_DATE,'dd/MM/yyyy') AS ORGINATING_JVR_DATE, " +
			        		"  a.ACCEPTING_JVR_NO                          AS ACCEPTING_JVR_NO, " +
			        		"  TO_CHAR(a.ACCEPTING_JVR_DATE,'dd/MM/yyyy')  AS ACCEPTING_JVR_DATE, " +
			        		"  a.RESPONDING_JVR_NO                         AS RESPONDING_JVR_NO, " +
			        		"  TO_CHAR(a.RESPONDING_JVR_DATE,'dd/MM/yyyy') AS RESPONDING_JVR_DATE, " +
			        		"  a.total_amount                              AS total_amount, " +
			        		"  a.TDA_OR_TCA                                AS TDA_OR_TCA, " +
			        		"  a.respond_status                            AS respond_status, " +
			        		"  b.ACCOUNTING_UNIT_NAME                      AS orgin_unit, " +
			        		"  c.accounting_unit_name                      AS accepting_unit " +
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
			        		"    mst.RESPONDING_JVR_DATE, " +
			        		"    mst.total_amount, " +
			        		"    mst.TDA_OR_TCA, " +
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
			        		"  AND mst.accounting_for_office_id IN " +
			        		"    (SELECT office_id " +
			        		"    FROM COM_MST_ALL_OFFICES_VIEW " +
			        		"    WHERE region_office_id ='"+cmbOffice_code+"' " +
			        		"    ) " +
			        		"  AND mst.status                  ='L' ";
                if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
				   	queryString+="AND mst.cashbook_month		='"+cashMonth+"'"+
				   	  			"AND mst.cashbook_year 			='"+cashYear+"'";
				   			map.put("monthinwords","For the "+cashYear+" "+mapMonth.get(cashMonth));
				   }else{				   				   	
				   	queryString+=" AND to_date(mst.cashbook_month"+
				   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
				   				 " || '-' "+
				   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
				   				 " AND to_date( '"+cashMonthTo+"' "+
				   				 " ||'-' "+
				   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
				   			map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+" "+cashYearFrom+" To "+mapMonth.get(cashMonthTo)+" "+cashYearTo);
				   }
        		queryString+="  )a " +
	                		"LEFT OUTER JOIN FAS_MST_ACCT_UNITS b " +
	                		"ON a.ACCOUNTING_UNIT_ID=b.accounting_unit_id " +
	                		"LEFT OUTER JOIN FAS_MST_ACCT_UNITS c " +
	                		"ON c.ACCOUNTING_UNIT_ID=a.TRF_ACCOUNTING_UNIT_ID";
        			s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_ALL_Region.jrxml");
                } else if(displayOrder.equalsIgnoreCase("OW")){ 
                	queryString="";
                	s="";
                	String quer="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID="+cmbOffice_code;
                    ps=con.prepareStatement(quer);
                    rs=ps.executeQuery();
                    while(rs.next()) {
                        cmbOffice_code=rs.getInt("ACCOUNTING_UNIT_OFFICE_ID");
                        cmbAcc_UnitCode=rs.getInt("ACCOUNTING_UNIT_ID");
                    }                	
                    queryString="SELECT TO_CHAR(a.VOUCHER_DATE,'dd/MM/yyyy')   AS VOUCHER_DATE, " +
			            		"  a.ACCOUNTING_UNIT_ID                        AS ACCOUNTING_UNIT_ID, " +
			            		"  a.TRF_ACCOUNTING_UNIT_ID                    AS TRF_ACCOUNTING_UNIT_ID, " +
			            		"  a.voucher_no                                AS voucher_no, " +
			            		"  a.ORGINATING_JVR_NO                         AS ORGINATING_JVR_NO, " +
			            		"  TO_CHAR(a.ORGINATING_JVR_DATE,'dd/MM/yyyy') AS ORGINATING_JVR_DATE, " +
			            		"  a.ACCEPTING_JVR_NO                          AS ACCEPTING_JVR_NO, " +
			            		"  TO_CHAR(a.ACCEPTING_JVR_DATE,'dd/MM/yyyy')  AS ACCEPTING_JVR_DATE, " +
			            		"  a.RESPONDING_JVR_NO                         AS RESPONDING_JVR_NO, " +
			            		"  TO_CHAR(a.RESPONDING_JVR_DATE,'dd/MM/yyyy') AS RESPONDING_JVR_DATE, " +
			            		"  a.total_amount                              AS total_amount, " +
			            		"  a.TDA_OR_TCA                                AS TDA_OR_TCA, " +
			            		"  a.respond_status                            AS respond_status, " +
			            		"  b.ACCOUNTING_UNIT_NAME                      AS orgin_unit, " +
			            		"  c.accounting_unit_name                      AS accepting_unit " +
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
			            		"    mst.RESPONDING_JVR_DATE, " +
			            		"    mst.total_amount, " +
			            		"    mst.TDA_OR_TCA, " +
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
			            		"  AND mst.accounting_for_office_id='"+cmbOffice_code+"'"+
			            		"  AND mst.status                  ='L' ";                    
                    if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
    				   	queryString+="AND mst.cashbook_month		='"+cashMonth+"'"+
    				   	  			"AND mst.cashbook_year 			='"+cashYear+"'";
    				   			map.put("monthinwords","For the "+cashYear+" "+mapMonth.get(cashMonth));
    				   }else{				   				   	
    				   	queryString+=" AND to_date(mst.cashbook_month"+
    				   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
    				   				 " || '-' "+
    				   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
    				   				 " AND to_date( '"+cashMonthTo+"' "+
    				   				 " ||'-' "+
    				   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
    				   			map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+" "+cashYearFrom+" To "+mapMonth.get(cashMonthTo)+" "+cashYearTo);
    				   }
            		queryString+="  )a " +
		                		"LEFT OUTER JOIN FAS_MST_ACCT_UNITS b " +
		                		"ON a.ACCOUNTING_UNIT_ID=b.accounting_unit_id " +
		                		"LEFT OUTER JOIN FAS_MST_ACCT_UNITS c " +
		                		"ON c.ACCOUNTING_UNIT_ID=a.TRF_ACCOUNTING_UNIT_ID";
                	s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_ALL.jrxml");
                } else if(displayOrder.equalsIgnoreCase("ALL")){ 
                		queryString="";
                		s="";             		
                		queryString="SELECT TO_CHAR(a.VOUCHER_DATE,'dd/MM/yyyy')   AS VOUCHER_DATE, " +
			                		"  a.ACCOUNTING_UNIT_ID                        AS ACCOUNTING_UNIT_ID, " +
			                		"  a.TRF_ACCOUNTING_UNIT_ID                    AS TRF_ACCOUNTING_UNIT_ID, " +
			                		"  a.voucher_no                                AS voucher_no, " +
			                		"  a.ORGINATING_JVR_NO                         AS ORGINATING_JVR_NO, " +
			                		"  TO_CHAR(a.ORGINATING_JVR_DATE,'dd/MM/yyyy') AS ORGINATING_JVR_DATE, " +
			                		"  a.ACCEPTING_JVR_NO                          AS ACCEPTING_JVR_NO, " +
			                		"  TO_CHAR(a.ACCEPTING_JVR_DATE,'dd/MM/yyyy')  AS ACCEPTING_JVR_DATE, " +
			                		"  a.RESPONDING_JVR_NO                         AS RESPONDING_JVR_NO, " +
			                		"  TO_CHAR(a.RESPONDING_JVR_DATE,'dd/MM/yyyy') AS RESPONDING_JVR_DATE, " +
			                		"  a.total_amount                              AS total_amount, " +
			                		"  a.TDA_OR_TCA                                AS TDA_OR_TCA, " +
			                		"  a.respond_status                            AS respond_status, " +
			                		"  b.ACCOUNTING_UNIT_NAME                      AS orgin_unit, " +
			                		"  c.accounting_unit_name                      AS accepting_unit " +
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
			                		"    mst.RESPONDING_JVR_DATE, " +
			                		"    mst.total_amount, " +
			                		"    mst.TDA_OR_TCA, " +
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
			                		"  AND mst.status                  ='L' ";
                		if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
                			if(cashMonth>3){
                				map.put("finyear",cashYear+"-"+""+(cashYear+1));
                			}else if(cashMonth<4){
                				map.put("finyear",(cashYear-1)+"-"+""+cashYear);
                			}
        				   	queryString+="AND mst.cashbook_month		='"+cashMonth+"'"+
        				   	  			"AND mst.cashbook_year 			='"+cashYear+"'";
        				   			map.put("monthinwords","For the "+cashYear+" "+mapMonth.get(cashMonth));
        				   }else{				   				   	
        				   	queryString+=" AND to_date(mst.cashbook_month"+
        				   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
        				   				 " || '-' "+
        				   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
        				   				 " AND to_date( '"+cashMonthTo+"' "+
        				   				 " ||'-' "+
        				   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
        				   			map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+" "+cashYearFrom+" To "+mapMonth.get(cashMonthTo)+" "+cashYearTo);
        				   	if(cashMonthFrom>3){
                        			map.put("finyear",cashYearFrom+"-"+""+(cashYearFrom+1));
                        	}else if(cashMonthFrom<4){
                        			map.put("finyear",(cashYearFrom-1)+"-"+""+cashYearFrom);
                        	}
        				   }
                		queryString+="  )a " +
			                		"LEFT OUTER JOIN FAS_MST_ACCT_UNITS b " +
			                		"ON a.ACCOUNTING_UNIT_ID=b.accounting_unit_id " +
			                		"LEFT OUTER JOIN FAS_MST_ACCT_UNITS c " +
			                		"ON c.ACCOUNTING_UNIT_ID=a.TRF_ACCOUNTING_UNIT_ID";
                		s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_ALL_All.jrxml");
                	}   
    	            map.put("unitid",cmbAcc_UnitCode);
    	            map.put("officeid",cmbOffice_code);
    	            map.put("cashyear",cashYear);
    	            map.put("cashmonth",cashMonth);
    	            map.put("tdatype",tpaType);
    	            map.put("reportName",Name1);
    	            map.put("accStatus",status);
    	            map.put("tdacbtype",tdacbtype);
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
                    
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-All.html\"");
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
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-All.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
        }
        else if (rtype.equalsIgnoreCase("EXCEL"))   
        {
        
                response.setContentType("application/vnd.ms-excel");
                 response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-All.xls\"");
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
                 response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-All.txt\"");
                 
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
       else if(com_report.equalsIgnoreCase("TDAnotRelated") || com_report.equalsIgnoreCase("TCAnotRelated") )
	   {
    	   if(com_report.equalsIgnoreCase("TDAnotRelated")) {
          	 queryString1="AND TRF_ACCOUNTING_UNIT_ID='"+cmbOffice_code+"' ";
               tpaType="TDAO";
               tdacbtype="TDACB";
               Name1="TDA Not Related";
           }  else if(com_report.equalsIgnoreCase("TCAnotRelated")) {
          	 queryString1="AND TRF_ACCOUNTING_UNIT_ID='"+cmbOffice_code+"' ";
               tpaType="TCAO";
               tdacbtype="TCACB";
               Name1="TCA Not Related";
           } 
       try {
           Map map = new HashMap();
           System.out.println("calling servlet... tpaType "+tpaType+" "+advType+" cmbOffice_code "+cmbOffice_code);
           if(displayOrder.equalsIgnoreCase("RW")) {
           queryString = "";
           s = "";
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
    		"  mst.TOTAL_AMOUNT                              AS TOTAL_AMOUNT " +
    		"FROM FAS_TDA_TCA_RAISED_MST mst " +
    		"WHERE mst.status          ='L' ";
    		if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
			   	queryString+="AND mst.cashbook_month		='"+cashMonth+"'"+
			   	  			"AND mst.cashbook_year 			='"+cashYear+"'";
			   			map.put("monthinwords","For the "+cashYear+" "+mapMonth.get(cashMonth));
			   }else{				   				   	
			   	queryString+=" AND to_date(mst.cashbook_month"+
			   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
			   				 " || '-' "+
			   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
			   				 " AND to_date( '"+cashMonthTo+"' "+
			   				 " ||'-' "+
			   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
			   			map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+" "+cashYearFrom+" To "+mapMonth.get(cashMonthTo)+" "+cashYearTo);
			   }
    		queryString+="AND (mst.TDA_OR_TCA      ='"+tpaType+"'"+
		                 	 "OR mst.tda_or_tca         ='"+tdacbtype+"') " +
		                 	"AND mst.accounting_for_office_id IN " +
		                 	"  (SELECT office_id " +
		                 	"  FROM COM_MST_ALL_OFFICES_VIEW " +
		                 	"  WHERE region_office_id = '"+cmbOffice_code+"'" +
		                 	"  ) " +
		                 	 "AND mst.ACCEPTANCE_STATUS ='N' " +
		                 	" AND mst.REASON_FOR_NON_ACCEPT=1 " +
		                 	 "ORDER BY mst.voucher_no";
   			s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_Rejected_Region.jrxml");
           }
           map.put("unitid",cmbAcc_UnitCode);
            map.put("officeid",cmbOffice_code);
            map.put("cashyear",cashYear);
            map.put("cashmonth",cashMonth);
            map.put("tdatype",tpaType);
            map.put("reportName",Name1);
            map.put("accStatus",status);
            map.put("tdacbtype",tdacbtype);
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
                
                response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Rejected.html\"");
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
                response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Rejected.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
    }
    else if (rtype.equalsIgnoreCase("EXCEL"))   
    {
    
            response.setContentType("application/vnd.ms-excel");
             response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Rejected.xls\"");
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
             response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Rejected.txt\"");
             
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
       
       else if(com_report.equalsIgnoreCase("TDARJVNotCreated") || com_report.equalsIgnoreCase("TCARJVNotCreated") )
	   {
    	   if(com_report.equalsIgnoreCase("TDARJVNotCreated")) {
          	 queryString1="AND TRF_ACCOUNTING_UNIT_ID='"+cmbOffice_code+"' ";
               tpaType="TDAO";
               tdacbtype="TDACB";
               Name1="TDA RJV Not Created";
           }  else if(com_report.equalsIgnoreCase("TCARJVNotCreated")) {
          	 queryString1="AND TRF_ACCOUNTING_UNIT_ID='"+cmbOffice_code+"' ";
               tpaType="TCAO";
               tdacbtype="TCACB";
               Name1="TCA RJV Not Created";
           } 
       try {
           Map map = new HashMap();
           System.out.println("calling servlet... tpaType "+tpaType+" "+advType+" cmbOffice_code "+cmbOffice_code);
           if(displayOrder.equalsIgnoreCase("RW")) {
           queryString = "";
           s = "";
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
    		"  mst.TOTAL_AMOUNT                              AS TOTAL_AMOUNT " +
    		"FROM FAS_TDA_TCA_RAISED_MST mst " +
    		"WHERE mst.status          ='L' ";
    		if(request.getParameter("month_year").equalsIgnoreCase("particular_cb")){
			   	queryString+="AND mst.cashbook_month		='"+cashMonth+"'"+
			   	  			"AND mst.cashbook_year 			='"+cashYear+"'";
			   			map.put("monthinwords","For the "+cashYear+" "+mapMonth.get(cashMonth));
			   }else{				   				   	
			   	queryString+=" AND to_date(mst.cashbook_month"+
			   				 " ||'-'|| + mst.cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+cashMonthFrom+"' "+
			   				 " || '-' "+
			   				 " || '"+cashYearFrom+"', 'mm-yyyy') "+
			   				 " AND to_date( '"+cashMonthTo+"' "+
			   				 " ||'-' "+
			   				 " || '"+cashYearTo+"' , 'mm-yyyy') ";
			   			map.put("monthinwords","From "+mapMonth.get(cashMonthFrom)+" "+cashYearFrom+" To "+mapMonth.get(cashMonthTo)+" "+cashYearTo);
			   }
    		queryString+="AND (mst.TDA_OR_TCA      ='"+tpaType+"'"+
		                 	 "OR mst.tda_or_tca         ='"+tdacbtype+"') " +
		                 	"AND mst.accounting_for_office_id IN " +
		                 	"  (SELECT office_id " +
		                 	"  FROM COM_MST_ALL_OFFICES_VIEW " +
		                 	"  WHERE region_office_id = '"+cmbOffice_code+"'" +
		                 	"  ) " +
		                 	 "AND mst.ACCEPTANCE_STATUS ='R' " +
		                 	" AND mst.REASON_FOR_NON_ACCEPT=1 " +
		                 	" AND RESPONDING_JVR_NO IS NULL " +
		                 	" AND RESPONDING_JVR_DATE is null " +
		                 	 "ORDER BY mst.voucher_no";
   			s=request.getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/TDA_Rejected_Region.jrxml");
           }
           map.put("unitid",cmbAcc_UnitCode);
            map.put("officeid",cmbOffice_code);
            map.put("cashyear",cashYear);
            map.put("cashmonth",cashMonth);
            map.put("tdatype",tpaType);
            map.put("reportName",Name1);
            map.put("accStatus",status);
            map.put("tdacbtype",tdacbtype);
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
                
                response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Rejected.html\"");
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
                response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Rejected.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
    }
    else if (rtype.equalsIgnoreCase("EXCEL"))   
    {
    
            response.setContentType("application/vnd.ms-excel");
             response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Rejected.xls\"");
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
             response.setHeader ("Content-Disposition", "attachment;filename=\"TDA-Rejected.txt\"");
             
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
       
        System.out.println("TDA successfully printed");
    }

	

}
