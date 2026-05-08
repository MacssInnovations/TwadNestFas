package Servlets.FAS.FAS1.ReceiptSystem.Reports;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

/**
 * Servlet implementation class Leave_type_Mst
 */
@SuppressWarnings("deprecation")
public class ListPBUpdatedUnits extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public ListPBUpdatedUnits() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		doPost(request,response);
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection connection=null;
		int startYear = 0 ;
		int endYear = 0;
		String financialYear="",strCommand="";
		Map<String,String> map = new HashMap<String,String>();
        try{
        	LoadDriver driver = new LoadDriver();
  			connection = driver.getConnection();                           
        }catch(Exception e){
            System.out.println("Exception in connection..."+e);
        }   
        try{
        	HttpSession session=request.getSession(false);
            if(session==null){
            	response.sendRedirect(request.getContextPath()+"/index.jsp");
              }                  
            }catch(Exception e){
                System.out.println("Redirect Error :"+e);
             }                
                String output="";                
                try {                    
                    strCommand = request.getParameter("command");
                    if(request.getParameter("financialYear")!=null){
	                    financialYear = request.getParameter("financialYear");
	                    output=request.getParameter("fileType");
	                    String[] finYear = financialYear.split("-"); 
	                    startYear = Integer.parseInt(finYear[0]);
	                    endYear = Integer.parseInt(finYear[1]);
                    }
                }catch(Exception e) {
                    System.out.println("Exception in assigning..."+e);
                }
                String freezetype = request.getParameter("freezetype");
                System.out.println("freezetype:::"+freezetype);
                if(strCommand.equalsIgnoreCase("listpb")){
                	if(freezetype.equalsIgnoreCase("F"))
            		{
                	try{
                		map.put("financialYear", financialYear);
                		String s = "",myQry="";            		
                /*		myQry="SELECT gl.ACCOUNTING_UNIT_ID           AS ACCOUNTING_UNIT_ID, " +
                		"  u.ACCOUNTING_UNIT_NAME                     AS ACCOUNTING_UNIT_NAME, " +
                		"  gl.ACCOUNTING_FOR_OFFICE_ID                AS ACCOUNTING_FOR_OFFICE_ID, " +
                		"  gl.CASHBOOK_YEAR                           AS CASHBOOK_YEAR, " +
                		"  gl.CASHBOOKMONTH                           AS CASHBOOKMONTH, " +
                		"  TO_CHAR(gl.GLPB_DATE,'dd/MM/yyyy')         AS GLPB_DATE, " +
                		"  TO_CHAR(gl.GL_PB_FREEZE_DATE,'dd/MM/yyyy') AS GL_PB_FREEZE_DATE " +
                		"FROM " +
                		"  ( SELECT DISTINCT ACCOUNTING_UNIT_ID, " +
                		"    ACCOUNTING_FOR_OFFICE_ID, " +
                		"    CASHBOOK_YEAR, " +
                		"    MAX(CASHBOOK_MONTH) AS CASHBOOKMONTH, " +
                		"    MAX(GL_PB_DATE)     AS GLPB_DATE, " +
                		"    GL_PB_FREEZE_DATE " +
                		"  FROM FAS_GL_PBSTATUS " +
                		"  WHERE " +
                	//	"CASHBOOK_YEAR BETWEEN ('"+startYear+"') AND ('"+endYear+"') " +
                		" To_Date((Cashbook_Month ||'-'|| Cashbook_Year),'mm-yyyy') "+ 
                		" Between To_Date(4||'-'||'"+startYear+"','mm-yyyy')  "+
                		" AND to_date(3||'-'||'"+endYear+"','mm-yyyy') "+
                		"  GROUP BY ACCOUNTING_UNIT_ID, " +
                		"    ACCOUNTING_FOR_OFFICE_ID, " +
                		"    CASHBOOK_YEAR, " +
                		"    GL_PB_FREEZE_DATE " +
                		"  )gl " +
                		"LEFT OUTER JOIN " +
                		"  ( SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS " +
                		"  )u " +
                		"ON gl.ACCOUNTING_UNIT_ID=u.ACCOUNTING_UNIT_ID " +
                		"ORDER BY gl.ACCOUNTING_UNIT_ID"; */
                		
              		
                		myQry="SELECT g.ACCOUNTING_UNIT_ID                 AS ACCOUNTING_UNIT_ID, "+
								"  U.Accounting_Unit_Name                     As Accounting_Unit_Name,"+
								"  g.CASHBOOK_YEAR                           AS CASHBOOK_YEAR, "+
                		" g.Month                           AS CASHBOOKMONTH, "+
                		" 	  To_Char(G.Glpb_Date,'dd/MM/yyyy')         As Glpb_Date, "+
                		" 	  To_Char(G.Gl_Pb_Freeze_Date,'dd/MM/yyyy') As Gl_Pb_Freeze_Date "+
                		" 	  from "+
                		" 	(Select Case When Countmn=1 Then 4 "+
                		" 	    When Countmn=2 Then 5 "+
                		" 	    When Countmn=3 Then 6 "+
                		" 	   When Countmn=4 Then 7 "+
                		" 	   When Countmn=5 Then 8 "+
                		" 	   When Countmn=6 Then 9 "+
                		" 	   When Countmn=7 Then 10 "+
                		" 	   When Countmn=8 Then 11 "+
                		" 	   When Countmn=9 Then 12 "+
                		" When Countmn=10 Then 1 "+
						" 	   When Countmn=11 Then 2 "+
                		" 		   When Countmn=12 Then 3 "+
                		" 	   End As Month, "+
                		" 	  Accounting_Unit_Id,Cashbook_Year,GLPB_DATE,GL_PB_FREEZE_DATE "+
                		" 		FROM "+
                		" 		  (Select Accounting_Unit_Id, "+
                		" 		    Count(Cashbook_Year)As Countmn, "+
                		" 		      Max(Cashbook_Year)As Cashbook_Year, "+
                		" 		      MAX(GL_PB_DATE)     AS GLPB_DATE,GL_PB_FREEZE_DATE "+
                		" 		  FROM Fas_Gl_Pbstatus "+
                		" 		  WHERE To_Date((Cashbook_Month ||'-' || Cashbook_Year),'mm-yyyy') BETWEEN To_Date(4 ||'-' ||'"+startYear+"','mm-yyyy') "+
                		" 		  AND to_date(3  ||'-'||'"+endYear+"','mm-yyyy')  and GL_PB_FREEZE_STATUS='Y'"+
                		" 		  GROUP BY Accounting_Unit_Id, GL_PB_FREEZE_DATE "+
                		" 		  ) "+
                		" 		  )G "+
                		" 		  LEFT OUTER JOIN "+
                		" 		  ( SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS "+
                		" 		  )U "+
                		" 		On G.Accounting_Unit_Id=U.Accounting_Unit_Id "+
                		" 		ORDER BY g.ACCOUNTING_UNIT_ID ";
                		
                		
                		
                		//System.out.println("myQry:::"+myQry);
                		s=request.getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/listofPBUpdatedUnitsF.jrxml");
                		output=request.getParameter("fileType");
                		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
                		System.out.println(myQry);
                		JRDesignQuery query=new JRDesignQuery();
                		query.setText(myQry);
                		jasperDesign.setQuery(query);
                		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
                		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, connection);
                		ByteArrayOutputStream bout=new ByteArrayOutputStream();
    	            	if(output.equalsIgnoreCase("pdf")){
    			            	OutputStream os=response.getOutputStream();
    			            	response.setContentType("application/pdf");
    			            	response.setHeader ("Content-Disposition", "attachment;filename=\"PBUpdatedUnits.pdf\"");
    			            	os.write(JasperManager.printReportToPdf(jasperPrint));
    			            	os.close();
    	            	}else if(output.equalsIgnoreCase("excel")){
    	            			response.setContentType("application/vnd.ms-excel");
    	            			response.setHeader ("Content-Disposition", "attachment;filename=\"PBUpdatedUnits.xls\"");
    	            			OutputStream os=response.getOutputStream();
    	            			JRXlsExporter exporterXLS = new JRXlsExporter(); 
    	            			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
    	            			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, bout);
    	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
    	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
    	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
    	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
    	            			exporterXLS.exportReport();
    	            		    byte[] buf=bout.toByteArray();
    	            		    os.write(buf);
    	            			os.close();
    	            		}else if(output.equalsIgnoreCase("html")){            		
    		            		 response.setContentType("text/html");
    		            		 response.setHeader ("Content-Disposition", "attachment;filename=\"PBUpdatedUnits.html\"");
    		            		 PrintWriter out = response.getWriter();
    		            		 JRHtmlExporter exporter = new JRHtmlExporter();            		
    		            		 exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
    		            		 exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
    		            		 exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
    		            		 exporter.exportReport();
    		            		 out.flush();
    		            		 out.close();
    	            		}
                	}
                	catch(Exception e){
                		e.printStackTrace();
                	}
            		}
                	else if(freezetype.equalsIgnoreCase("NF"))
            		{

                    	try{
                    		map.put("financialYear", financialYear);
                    		String s = "",myQry="";            		
                  
                    		myQry="SELECT g.ACCOUNTING_UNIT_ID                 AS ACCOUNTING_UNIT_ID, "+
    								"  U.Accounting_Unit_Name                     As Accounting_Unit_Name,"+
    								"  g.CASHBOOK_YEAR                           AS CASHBOOK_YEAR, "+
                    		" g.Month                           AS CASHBOOKMONTH, "+
                    		" 	  To_Char(G.Glpb_Date,'dd/MM/yyyy')         As Glpb_Date, "+
                    		" 	  To_Char(G.Gl_Pb_Freeze_Date,'dd/MM/yyyy') As Gl_Pb_Freeze_Date "+
                    		" 	  from "+
                    		" 	(Select Case When Countmn=1 Then 4 "+
                    		" 	    When Countmn=2 Then 5 "+
                    		" 	    When Countmn=3 Then 6 "+
                    		" 	   When Countmn=4 Then 7 "+
                    		" 	   When Countmn=5 Then 8 "+
                    		" 	   When Countmn=6 Then 9 "+
                    		" 	   When Countmn=7 Then 10 "+
                    		" 	   When Countmn=8 Then 11 "+
                    		" 	   When Countmn=9 Then 12 "+
                    		" When Countmn=10 Then 1 "+
    						" 	   When Countmn=11 Then 2 "+
                    		" 		   When Countmn=12 Then 3 "+
                    		" 	   End As Month, "+
                    		" 	  Accounting_Unit_Id,Cashbook_Year,GLPB_DATE,GL_PB_FREEZE_DATE "+
                    		" 		FROM "+
                    		" 		  (Select Accounting_Unit_Id, "+
                    		" 		    Count(Cashbook_Year)As Countmn, "+
                    		" 		      Max(Cashbook_Year)As Cashbook_Year, "+
                    		" 		      MAX(GL_PB_DATE)     AS GLPB_DATE,GL_PB_FREEZE_DATE "+
                    		" 		  FROM Fas_Gl_Pbstatus "+
                    		" 		  WHERE To_Date((Cashbook_Month ||'-' || Cashbook_Year),'mm-yyyy') BETWEEN To_Date(4 ||'-' ||'"+startYear+"','mm-yyyy') "+
                    		" 		  AND to_date(3  ||'-'||'"+endYear+"','mm-yyyy')  and GL_PB_FREEZE_STATUS is null "+
                    		" 		  GROUP BY Accounting_Unit_Id, GL_PB_FREEZE_DATE "+
                    		" 		  ) "+
                    		" 		  )G "+
                    		" 		  LEFT OUTER JOIN "+
                    		" 		  ( SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS "+
                    		" 		  )U "+
                    		" 		On G.Accounting_Unit_Id=U.Accounting_Unit_Id "+
                    		" 		ORDER BY g.ACCOUNTING_UNIT_ID ";
                    		
                    		
                    		
                    		//System.out.println("myQry:::"+myQry);
                    		s=request.getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/listofPBUpdatedUnitsNF.jrxml");
                    		output=request.getParameter("fileType");
                    		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
                    		System.out.println(myQry);
                    		JRDesignQuery query=new JRDesignQuery();
                    		query.setText(myQry);
                    		jasperDesign.setQuery(query);
                    		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
                    		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, connection);
                    		ByteArrayOutputStream bout=new ByteArrayOutputStream();
        	            	if(output.equalsIgnoreCase("pdf")){
        			            	OutputStream os=response.getOutputStream();
        			            	response.setContentType("application/pdf");
        			            	response.setHeader ("Content-Disposition", "attachment;filename=\"PBUpdatedUnits.pdf\"");
        			            	os.write(JasperManager.printReportToPdf(jasperPrint));
        			            	os.close();
        	            	}else if(output.equalsIgnoreCase("excel")){
        	            			response.setContentType("application/vnd.ms-excel");
        	            			response.setHeader ("Content-Disposition", "attachment;filename=\"PBUpdatedUnits.xls\"");
        	            			OutputStream os=response.getOutputStream();
        	            			JRXlsExporter exporterXLS = new JRXlsExporter(); 
        	            			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
        	            			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, bout);
        	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
        	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
        	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
        	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
        	            			exporterXLS.exportReport();
        	            		    byte[] buf=bout.toByteArray();
        	            		    os.write(buf);
        	            			os.close();
        	            		}else if(output.equalsIgnoreCase("html")){            		
        		            		 response.setContentType("text/html");
        		            		 response.setHeader ("Content-Disposition", "attachment;filename=\"PBUpdatedUnits.html\"");
        		            		 PrintWriter out = response.getWriter();
        		            		 JRHtmlExporter exporter = new JRHtmlExporter();            		
        		            		 exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
        		            		 exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        		            		 exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
        		            		 exporter.exportReport();
        		            		 out.flush();
        		            		 out.close();
        	            		}
                    	}
                    	catch(Exception e){
                    		e.printStackTrace();
                    	}
                		
                	}
               }else if(strCommand.equalsIgnoreCase("listsl")){
            	   if(freezetype.equalsIgnoreCase("F"))
           		{
            	try{
            		map.put("financialYear", financialYear);
            		String s = "",myQry="";            		
            	/*	myQry="SELECT sl.ACCOUNTING_UNIT_ID                 AS ACCOUNTING_UNIT_ID, " +
            		"  u.ACCOUNTING_UNIT_NAME                     AS ACCOUNTING_UNIT_NAME, " +
            		"  sl.ACCOUNTING_FOR_OFFICE_ID                AS ACCOUNTING_FOR_OFFICE_ID, " +
            		"  sl.CASHBOOK_YEAR                           AS CASHBOOK_YEAR, " +
            		"  sl.CASHBOOKMONTH                           AS CASHBOOKMONTH, " +
            		"  TO_CHAR(sl.SLPB_DATE,'dd/MM/yyyy')         AS SLPB_DATE, " +
            		"  TO_CHAR(sl.SL_PB_FREEZE_DATE,'dd/MM/yyyy') AS SL_PB_FREEZE_DATE " +
            		"FROM " +
            		"  ( SELECT DISTINCT ACCOUNTING_UNIT_ID, " +
            		"    ACCOUNTING_FOR_OFFICE_ID, " +
            		"    CASHBOOK_YEAR, " +
            		"    MAX(CASHBOOK_MONTH) AS CASHBOOKMONTH, " +
            		"    MAX(SL_PB_DATE)     AS SLPB_DATE, " +
            		"    SL_PB_FREEZE_DATE " +
            		"  FROM FAS_SL_PBSTATUS " +
            		"  WHERE " +
            		//"CASHBOOK_YEAR BETWEEN ('"+startYear+"') AND ('"+endYear+"') " +
            		" To_Date((Cashbook_Month ||'-'|| Cashbook_Year),'mm-yyyy') "+ 
            		" Between To_Date(4||'-'||'"+startYear+"','mm-yyyy')  "+
            		" AND to_date(3||'-'||'"+endYear+"','mm-yyyy') "+
            		"  GROUP BY ACCOUNTING_UNIT_ID, " +
            		"    ACCOUNTING_FOR_OFFICE_ID, " +
            		"    CASHBOOK_YEAR, " +
            		"    SL_PB_FREEZE_DATE " +
            		"  )sl " +
            		"LEFT OUTER JOIN " +
            		"  ( SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS " +
            		"  )u " +
            		"ON sl.ACCOUNTING_UNIT_ID=u.ACCOUNTING_UNIT_ID " +
            		"ORDER BY sl.ACCOUNTING_UNIT_ID";   */
            		
            		myQry="SELECT G.Accounting_Unit_Id                 AS Accounting_Unit_Id, "+
						"  U.Accounting_Unit_Name                    AS Accounting_Unit_Name, "+
						" G.Cashbook_Year                           AS Cashbook_Year,"+
						" G.Month                                   AS Cashbookmonth, "+
						" TO_CHAR(G.Slpb_Date,'dd/MM/yyyy')         AS Slpb_Date, "+
						" TO_CHAR(G.Sl_Pb_Freeze_Date,'dd/MM/yyyy') AS Sl_Pb_Freeze_Date "+
						" FROM "+
						" (SELECT "+
						"   CASE "+
						"     WHEN Countmn=1 "+
						"     THEN 4 "+
						"     WHEN Countmn=2 "+
						"     THEN 5 "+
						"     WHEN Countmn=3 "+
						"     THEN 6 "+
						"     WHEN Countmn=4 "+
						"     THEN 7 "+
						"     WHEN Countmn=5 "+
						"     THEN 8 "+
						"     WHEN Countmn=6 "+
						"     THEN 9 "+
						"     WHEN Countmn=7 "+
						"     THEN 10 "+
						"     WHEN Countmn=8 "+
						"     THEN 11 "+
						"     WHEN Countmn=9 "+
						"     THEN 12 "+
						"     WHEN Countmn=10 "+
						"     THEN 1 "+
						"     WHEN Countmn=11 "+
						"     THEN 2 "+
						"     WHEN Countmn=12 "+
						"     THEN 3 "+
						"   END AS MONTH, "+
						"   Accounting_Unit_Id, "+
						"   Cashbook_Year, "+
						"   Slpb_Date, "+
						"   Sl_Pb_Freeze_Date "+
						" FROM "+
						"   (SELECT Accounting_Unit_Id, "+
						"     COUNT(Cashbook_Year)AS Countmn, "+
						"     MAX(Cashbook_Year)  AS Cashbook_Year, "+
						"     MAX(SL_PB_DATE)     AS slpb_Date, "+
						"     SL_PB_FREEZE_DATE "+
						"   FROM FAS_SL_PBSTATUS "+
						"   WHERE To_Date((Cashbook_Month "+
						"     ||'-' "+
						"     || Cashbook_Year),'mm-yyyy') BETWEEN To_Date(4 "+
						"     ||'-' "+
						"     ||'"+startYear+"','mm-yyyy') "+
						"   AND To_Date(3 "+
						"     ||'-' "+
						"     ||'"+endYear+"','mm-yyyy') "+
						"   AND SL_PB_FREEZE_STATUS='Y' "+
						"   GROUP BY Accounting_Unit_Id, SL_PB_FREEZE_DATE "+
						"   ) "+
						" )G "+
						" LEFT OUTER JOIN "+
						" ( SELECT Accounting_Unit_Id,Accounting_Unit_Name FROM Fas_Mst_Acct_Units "+
						" )U "+
						" ON G.Accounting_Unit_Id=U.Accounting_Unit_Id "+
						" ORDER BY G.Accounting_Unit_Id	";
            		
            		s=request.getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/listofPBUpdatedUnitsF_SL.jrxml");
            		output=request.getParameter("fileType");
            		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
            		System.out.println(myQry);
            		JRDesignQuery query=new JRDesignQuery();
            		query.setText(myQry);
            		jasperDesign.setQuery(query);
            		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
            		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, connection);
            		ByteArrayOutputStream bout=new ByteArrayOutputStream();
	            	if(output.equalsIgnoreCase("pdf")){
			            	OutputStream os=response.getOutputStream();
			            	response.setContentType("application/pdf");
			            	response.setHeader ("Content-Disposition", "attachment;filename=\"PBUpdatedUnits.pdf\"");
			            	os.write(JasperManager.printReportToPdf(jasperPrint));
			            	os.close();
	            	}else if(output.equalsIgnoreCase("excel")){
	            			response.setContentType("application/vnd.ms-excel");
	            			response.setHeader ("Content-Disposition", "attachment;filename=\"PBUpdatedUnits.xls\"");
	            			OutputStream os=response.getOutputStream();
	            			JRXlsExporter exporterXLS = new JRXlsExporter(); 
	            			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
	            			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, bout);
	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
	            			exporterXLS.exportReport();
	            		    byte[] buf=bout.toByteArray();
	            		    os.write(buf);
	            			os.close();
	            		}else if(output.equalsIgnoreCase("html")){            		
		            		 response.setContentType("text/html");
		            		 response.setHeader ("Content-Disposition", "attachment;filename=\"PBUpdatedUnits.html\"");
		            		 PrintWriter out = response.getWriter();
		            		 JRHtmlExporter exporter = new JRHtmlExporter();            		
		            		 exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
		            		 exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
		            		 exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
		            		 exporter.exportReport();
		            		 out.flush();
		            		 out.close();
	            		}
            	}
            	catch(Exception e){
            		e.printStackTrace();
            	}
           		}
            	   else if(freezetype.equalsIgnoreCase("NF"))
              		{
                   	try{
                   		map.put("financialYear", financialYear);
                   		String s = "",myQry="";            		
                   		/*myQry="SELECT G.Accounting_Unit_Id                 AS Accounting_Unit_Id, "+
       						"  U.Accounting_Unit_Name                    AS Accounting_Unit_Name, "+
       						" G.Cashbook_Year                           AS Cashbook_Year,"+
       						" G.Month                                   AS Cashbookmonth, "+
       						" TO_CHAR(G.Slpb_Date,'dd/MM/yyyy')         AS Slpb_Date, "+
       						" TO_CHAR(G.Sl_Pb_Freeze_Date,'dd/MM/yyyy') AS Sl_Pb_Freeze_Date "+
       						" FROM "+
       						" (SELECT "+
       						"   CASE "+
       						"     WHEN Countmn=1 "+
       						"     THEN 4 "+
       						"     WHEN Countmn=2 "+
       						"     THEN 5 "+
       						"     WHEN Countmn=3 "+
       						"     THEN 6 "+
       						"     WHEN Countmn=4 "+
       						"     THEN 7 "+
       						"     WHEN Countmn=5 "+
       						"     THEN 8 "+
       						"     WHEN Countmn=6 "+
       						"     THEN 9 "+
       						"     WHEN Countmn=7 "+
       						"     THEN 10 "+
       						"     WHEN Countmn=8 "+
       						"     THEN 11 "+
       						"     WHEN Countmn=9 "+
       						"     THEN 12 "+
       						"     WHEN Countmn=10 "+
       						"     THEN 1 "+
       						"     WHEN Countmn=11 "+
       						"     THEN 2 "+
       						"     WHEN Countmn=12 "+
       						"     THEN 3 "+
       						"   END AS MONTH, "+
       						"   Accounting_Unit_Id, "+
       						"   Cashbook_Year, "+
       						"   Slpb_Date, "+
       						"   Sl_Pb_Freeze_Date "+
       						" FROM "+
       						"   (SELECT Accounting_Unit_Id, "+
       						"     COUNT(Cashbook_Year)AS Countmn, "+
       						"     MAX(Cashbook_Year)  AS Cashbook_Year, "+
       						"     MAX(SL_PB_DATE)     AS slpb_Date, "+
       						"     SL_PB_FREEZE_DATE "+
       						"   FROM FAS_SL_PBSTATUS "+
       						"   WHERE To_Date((Cashbook_Month "+
       						"     ||'-' "+
       						"     || Cashbook_Year),'mm-yyyy') BETWEEN To_Date(4 "+
       						"     ||'-' "+
       						"     ||'"+startYear+"','mm-yyyy') "+
       						"   AND To_Date(3 "+
       						"     ||'-' "+
       						"     ||'"+endYear+"','mm-yyyy') "+
       						"   AND SL_PB_FREEZE_STATUS is null "+
       						"   GROUP BY Accounting_Unit_Id, SL_PB_FREEZE_DATE "+
       						"   ) "+
       						" )G "+
       						" LEFT OUTER JOIN "+
       						" ( SELECT Accounting_Unit_Id,Accounting_Unit_Name FROM Fas_Mst_Acct_Units "+
       						" )U "+
       						" ON G.Accounting_Unit_Id=U.Accounting_Unit_Id "+
       						" ORDER BY G.Accounting_Unit_Id	";*/
                   		myQry="SELECT g.ACCOUNTING_UNIT_ID                 AS ACCOUNTING_UNIT_ID, "+
						"  U.Accounting_Unit_Name                     As Accounting_Unit_Name,"+
						"  g.CASHBOOK_YEAR                           AS CASHBOOK_YEAR, "+
						" g.Month                           AS CASHBOOKMONTH, "+
						" 	  To_Char(G.SL_PB_DATE,'dd/MM/yyyy')         As SLPB_DATE, "+
						" 	  To_Char(G.SL_PB_FREEZE_DATE,'dd/MM/yyyy') As SL_PB_FREEZE_DATE "+
						" 	  from "+
						" 	(Select Case When Countmn=1 Then 4 "+
						" 	    When Countmn=2 Then 5 "+
						" 	    When Countmn=3 Then 6 "+
						" 	   When Countmn=4 Then 7 "+
						" 	   When Countmn=5 Then 8 "+
						" 	   When Countmn=6 Then 9 "+
		        		" 	   When Countmn=7 Then 10 "+
		        		" 	   When Countmn=8 Then 11 "+
		        		" 	   When Countmn=9 Then 12 "+
		        		" When Countmn=10 Then 1 "+
						" 	   When Countmn=11 Then 2 "+
		        		" 		   When Countmn=12 Then 3 "+
		        		" 	   End As Month, "+
		        		" 	  Accounting_Unit_Id,Cashbook_Year,SL_PB_DATE,SL_PB_FREEZE_DATE "+
		        		" 		FROM "+
		        		" 		  (Select Accounting_Unit_Id, "+
		        		" 		    Count(Cashbook_Year)As Countmn, "+
		        		" 		      Max(Cashbook_Year)As Cashbook_Year, "+
		        		" 		      MAX(SL_PB_DATE)     AS SL_PB_DATE,SL_PB_FREEZE_DATE"+
		        		" 		  FROM FAS_SL_PBSTATUS "+
		        		" 		  WHERE To_Date((Cashbook_Month ||'-' || Cashbook_Year),'mm-yyyy') BETWEEN To_Date(4 ||'-' ||'"+startYear+"','mm-yyyy') "+
		        		" 		  AND to_date(3  ||'-'||'"+endYear+"','mm-yyyy')  and SL_PB_FREEZE_STATUS is null "+
		        		" 		  GROUP BY Accounting_Unit_Id, SL_PB_FREEZE_DATE "+
		        		" 		  ) "+
		        		" 		  )G "+
		        		" 		  LEFT OUTER JOIN "+
		        		" 		  ( SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS "+
		        		" 		  )U "+
		        		" 		On G.Accounting_Unit_Id=U.Accounting_Unit_Id "+
		        		" 		ORDER BY g.ACCOUNTING_UNIT_ID ";
                   		
                   		s=request.getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/listofPBUpdatedUnitsNF_SL.jrxml");
                   		output=request.getParameter("fileType");
                   		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
                   		System.out.println(myQry);
                   		JRDesignQuery query=new JRDesignQuery();
                   		query.setText(myQry);
                   		jasperDesign.setQuery(query);
                   		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
                   		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, connection);
                   		ByteArrayOutputStream bout=new ByteArrayOutputStream();
       	            	if(output.equalsIgnoreCase("pdf")){
       			            	OutputStream os=response.getOutputStream();
       			            	response.setContentType("application/pdf");
       			            	response.setHeader ("Content-Disposition", "attachment;filename=\"PBUpdatedUnits.pdf\"");
       			            	os.write(JasperManager.printReportToPdf(jasperPrint));
       			            	os.close();
       	            	}else if(output.equalsIgnoreCase("excel")){
       	            			response.setContentType("application/vnd.ms-excel");
       	            			response.setHeader ("Content-Disposition", "attachment;filename=\"PBUpdatedUnits.xls\"");
       	            			OutputStream os=response.getOutputStream();
       	            			JRXlsExporter exporterXLS = new JRXlsExporter(); 
       	            			exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);
       	            			exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, bout);
       	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.TRUE);
       	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
       	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
       	            			exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
       	            			exporterXLS.exportReport();
       	            		    byte[] buf=bout.toByteArray();
       	            		    os.write(buf);
       	            			os.close();
       	            		}else if(output.equalsIgnoreCase("html")){            		
       		            		 response.setContentType("text/html");
       		            		 response.setHeader ("Content-Disposition", "attachment;filename=\"PBUpdatedUnits.html\"");
       		            		 PrintWriter out = response.getWriter();
       		            		 JRHtmlExporter exporter = new JRHtmlExporter();            		
       		            		 exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
       		            		 exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
       		            		 exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
       		            		 exporter.exportReport();
       		            		 out.flush();
       		            		 out.close();
       	            		}
                   	}
                   	catch(Exception e){
                   		e.printStackTrace();
                   	}
                  		}
            } else if(strCommand.equalsIgnoreCase("notupdateid")){
            	try{
            		String s = "",myQry="";            		
            		myQry="SELECT ACCOUNTING_UNIT_ID, " +
            		"  ACCOUNTING_UNIT_NAME " +
            		"FROM FAS_MST_ACCT_UNITS " +
            		"WHERE ACCOUNTING_UNIT_ID NOT IN " +
            		"  (SELECT DISTINCT ACCOUNTING_UNIT_ID FROM FAS_GL_PBSTATUS " +
            		"  ) " +
            		"ORDER BY ACCOUNTING_UNIT_ID";
            		map.put("updat", "GL");
            		output="pdf";
            		s=request.getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/listofNotUpdatedUnits.jrxml");
            		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
            		JRDesignQuery query=new JRDesignQuery();
            		query.setText(myQry);
            		jasperDesign.setQuery(query);
            		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
            		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, connection);
	            	if(output.equalsIgnoreCase("pdf")){
			            	OutputStream os=response.getOutputStream();
			            	response.setContentType("application/pdf");
			            	response.setHeader ("Content-Disposition", "attachment;filename=\"NotUpdatedUnits.pdf\"");
			            	os.write(JasperManager.printReportToPdf(jasperPrint));
			            	os.close();
	            	}
            	}
            	catch(Exception e){
            		e.printStackTrace();
            	}
            }else if(strCommand.equalsIgnoreCase("slnotupdateid")){
            	try{
            		String s = "",myQry="";            		
            		myQry="SELECT ACCOUNTING_UNIT_ID, " +
            		"  ACCOUNTING_UNIT_NAME " +
            		"FROM FAS_MST_ACCT_UNITS " +
            		"WHERE ACCOUNTING_UNIT_ID NOT IN " +
            		"  (SELECT DISTINCT ACCOUNTING_UNIT_ID FROM FAS_SL_PBSTATUS " +
            		"  ) " +
            		"ORDER BY ACCOUNTING_UNIT_ID";
            		map.put("updat", "SL");
            		output="pdf";
            		s=request.getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/listofNotUpdatedUnits.jrxml");
            		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
            		JRDesignQuery query=new JRDesignQuery();
            		query.setText(myQry);
            		jasperDesign.setQuery(query);
            		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
            		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, connection);
	            	if(output.equalsIgnoreCase("pdf")){
			            	OutputStream os=response.getOutputStream();
			            	response.setContentType("application/pdf");
			            	response.setHeader ("Content-Disposition", "attachment;filename=\"NotUpdatedUnits.pdf\"");
			            	os.write(JasperManager.printReportToPdf(jasperPrint));
			            	os.close();
	            	}
            	}
            	catch(Exception e){
            		e.printStackTrace();
            	}
            }
	 	}

}

