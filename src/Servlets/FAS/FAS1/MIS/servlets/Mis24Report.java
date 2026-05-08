package Servlets.FAS.FAS1.MIS.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
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
public class Mis24Report extends HttpServlet {
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Mis24Report() {
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
		// TODO Auto-generated method stub
		Connection connection=null;
		PreparedStatement preparedStatement = null;
		ResultSet resultSet = null;
		int cashYear_from = 0;
		int cashMonth_from = 0;
		int cashYear_to = 0;
		int cashMonth_to = 0,supplimentNo=0;
		String strCommand="",isSjv="";
		String urbanrural="";
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
                String output="",sql="",xml = "";                
                strCommand = request.getParameter("command");
                if(strCommand.equalsIgnoreCase("majorGroup")){
                	response.setContentType(CONTENT_TYPE);
            		PrintWriter out = response.getWriter();
                	sql="select MAJOR_HEAD_CODE,MAJOR_HEAD_DESC from COM_MST_MAJOR_HEADS";
                	xml = "";
                	int count = 0;
                	try {
						preparedStatement = connection.prepareStatement(sql);
						resultSet = preparedStatement.executeQuery();
						xml = "<response>";
						while(resultSet.next()){
							xml +="<MAJOR_HEAD_CODE>"+resultSet.getString("MAJOR_HEAD_CODE")+"</MAJOR_HEAD_CODE>" +
								  "<MAJOR_HEAD_DESC>"+resultSet.getString("MAJOR_HEAD_DESC")+"</MAJOR_HEAD_DESC>";
							count++;
						}
						if(count>0){
							xml +="<status>success</status>";
						} else {
							xml +="<status>fail</status>";
						}
						xml += "</response>";
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						xml ="<response><status>fail</status></response>";
						e.printStackTrace();
						
					}
					out.write(xml);
	        		out.flush();
	        		out.close();	
                } else if(strCommand.equalsIgnoreCase("minorGroup")) {
                	response.setContentType(CONTENT_TYPE);
            		PrintWriter out = response.getWriter();
            		String majorCode = request.getParameter("majorCode");
                	sql="select MINOR_HEAD_CODE,MINOR_HEAD_DESC from COM_MST_MINOR_HEADS where MAJOR_HEAD_CODE=?";
                	xml = "";
                	int count = 0;
                	try {
						preparedStatement = connection.prepareStatement(sql);
						preparedStatement.setString(1, majorCode);
						resultSet = preparedStatement.executeQuery();
						xml = "<response>";
						while(resultSet.next()){
							xml +="<MINOR_HEAD_CODE>"+resultSet.getInt("MINOR_HEAD_CODE")+"</MINOR_HEAD_CODE>" +
								  "<MINOR_HEAD_DESC>"+resultSet.getString("MINOR_HEAD_DESC")+"</MINOR_HEAD_DESC>";
							count++;
						}
						if(count>0){
							xml +="<status>success</status>";
						} else {
							xml +="<status>fail</status>";
						}
						xml += "</response>";
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						xml ="<response><status>fail</status></response>";
						e.printStackTrace();
						
					}
					out.write(xml);
	        		out.flush();
	        		out.close();	
                }else if(strCommand.equalsIgnoreCase("minorGroup1")) {
                	response.setContentType(CONTENT_TYPE);
            		PrintWriter out = response.getWriter();
            		String majorCode = request.getParameter("majorCode");
                	sql="select GROUP_HEAD_CODE,GROUP_HEAD_DESC from FAS_MIS_GROUP_MASTER where MAJOR_HEAD_CODE=? and STATUS='L' and TYPE='MIS' order by GROUP_HEAD_CODE";
                	xml = "";
                	int count = 0;
                	try {
						preparedStatement = connection.prepareStatement(sql);
						preparedStatement.setString(1, majorCode);
						resultSet = preparedStatement.executeQuery();
						xml = "<response>";
						while(resultSet.next()){
							xml +="<MINOR_HEAD_CODE>"+resultSet.getInt("GROUP_HEAD_CODE")+"</MINOR_HEAD_CODE>" +
								  "<MINOR_HEAD_DESC>"+resultSet.getString("GROUP_HEAD_DESC")+"</MINOR_HEAD_DESC>";
							count++;
						}
						if(count>0){
							xml +="<status>success</status>";
						} else {
							xml +="<status>fail</status>";
						}
						xml += "</response>";
					} catch (SQLException e) {
						// TODO Auto-generated catch block
						xml ="<response><status>fail</status></response>";
						e.printStackTrace();
						
					}
					out.write(xml);
	        		out.flush();
	        		out.close();	
                } else if(strCommand.equalsIgnoreCase("report")) {
                	int cashMonthFrom = Integer.parseInt(request.getParameter("txtCB_Month_from"));
                	int cashYearFrom = Integer.parseInt(request.getParameter("txtCB_Year_from"));
                	int cashMonthTo = Integer.parseInt(request.getParameter("txtCB_Month_to"));
                	int cashYearTo = Integer.parseInt(request.getParameter("txtCB_Year_to"));
                	String majorCode = request.getParameter("majorGroupCode");
                	int minorCode = Integer.parseInt(request.getParameter("minorGroupCode"));
                	Map map = new HashMap();
                	Map<Integer,String> monthlist = new HashMap<Integer,String>();
                	monthlist.put(1,"January");
                	monthlist.put(2,"February");
                	monthlist.put(3,"March");
                	monthlist.put(4,"April");
                	monthlist.put(5,"May");
                	monthlist.put(6,"June");
                	monthlist.put(7,"July");
                	monthlist.put(8,"August");
                	monthlist.put(9,"September");
                	monthlist.put(10,"October");
                	monthlist.put(11,"November");
                	monthlist.put(12,"December");
                	map.put("year_from", cashYear_from);
                	map.put("year_to", cashYear_to);
            		map.put("month_from", monthlist.get(cashMonth_from));
            		map.put("month_to", monthlist.get(cashMonth_to));
            		String finYear="";
            		int cashYrFr = 0,cashYrTo=0,precashYrFr = 0,precashYrTo=0;
                	if(cashMonthFrom<4) {
                		cashYrFr = cashYearFrom-1;
                		cashYrTo= cashYearFrom;
                		precashYrFr = cashYrFr-1;
                		precashYrTo=cashYrFr;
                	} else {
                		cashYrFr = cashYearFrom;
                		cashYrTo= cashYearFrom+1;
                		precashYrFr = cashYearFrom-1;
                		precashYrTo=cashYearFrom;
                	}
                	map.put("finYearpre", (cashYearFrom-1)+"-"+(cashYearFrom));
                	map.put("finYearcur", (cashYearFrom)+"-"+(cashYearFrom+1));
                	try{
                		String s = "",myQry="";   
                		myQry = "SELECT t.ACCOUNT_HEAD_CODE AS ACCOUNT_HEAD_CODE, " +
                		"  pretot, " +
                		"  tot, " +
                		"  ACCOUNT_HEAD_DESC, " +
                		"  MINOR_HEAD_DESC "+
                		" FROM " +
                		"  (SELECT ACCOUNT_HEAD_CODE, " +
                		"    ACCOUNT_HEAD_DESC, " +
                		"    MINOR_HEAD_CODE " +
                		"  FROM COM_MST_ACCOUNT_HEADS " +
                		//"  WHERE MAJOR_HEAD_CODE='I' " +
                		//"  AND MINOR_HEAD_CODE  ='103' " +  changed on 18/01/2017 as the major and minor code fixed...
                		"  WHERE MAJOR_HEAD_CODE= '" +majorCode+ "' "+
                		"  AND MINOR_HEAD_CODE  = " +minorCode+
                		"  )t " +
                		"LEFT OUTER JOIN " +
                		"  (SELECT tot, " +
                		"    pretot, " +
                		"    a.ACCOUNT_HEAD_CODE " +
                		"  FROM " +
                		"    (SELECT ACCOUNT_HEAD_CODE, " +
                		"      (SUM(CREDIT_CLOSING_BALANCE)-SUM(DEBIT_CLOSING_BALANCE)) AS tot " +
                		"    FROM FAS_TRIAL_BALANCE " +
                		"    WHERE To_Date((Cashbook_Month " +
                		"      ||'-' " +
                		"      || Cashbook_Year),'mm-yyyy') BETWEEN To_Date('"+cashMonthFrom+"' " +
                		"      ||'-' " +
                		"      ||'"+cashYrFr+"','mm-yyyy') " +
                		"    AND to_date('"+cashMonthTo+"' " +
                		"      ||'-' " +
                		"      ||'"+cashYrTo+"','mm-yyyy') " +
                		"    GROUP BY ACCOUNT_HEAD_CODE " +
                		"    )a " +
                		"  LEFT OUTER JOIN " +
                		"    (SELECT ACCOUNT_HEAD_CODE, " +
                		"      (SUM(CREDIT_CLOSING_BALANCE)-SUM(DEBIT_CLOSING_BALANCE)) AS pretot " +
                		"    FROM FAS_TRIAL_BALANCE " +
                		"    WHERE To_Date((Cashbook_Month " +
                		"      ||'-' " +
                		"      || Cashbook_Year),'mm-yyyy') BETWEEN To_Date('"+cashMonthFrom+"' " +
                		"      ||'-' " +
                		"      ||'"+precashYrFr+"','mm-yyyy') " +
                		"    AND to_date('"+cashMonthTo+"' " +
                		"      ||'-' " +
                		"      ||'"+precashYrTo+"','mm-yyyy') " +
                		"    GROUP BY ACCOUNT_HEAD_CODE " +
                		"    )b " +
                		"  ON a.ACCOUNT_HEAD_CODE    =b.ACCOUNT_HEAD_CODE " +
                		"  )t2 ON t.ACCOUNT_HEAD_CODE=t2.ACCOUNT_HEAD_CODE " +
                		"  LEFT OUTER JOIN "+
                		"  ( SELECT MINOR_HEAD_CODE,MINOR_HEAD_DESC FROM COM_MST_MINOR_HEADS "+
                		"  )t3 "+
                		" ON t.MINOR_HEAD_CODE=t3.MINOR_HEAD_CODE "+
                		"ORDER BY t.ACCOUNT_HEAD_CODE";
                		s=request.getRealPath("/org/FAS/FAS1/MIS/jaspers/Mis24Report.jrxml");
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
    			            	response.setHeader ("Content-Disposition", "attachment;filename=\"Mis24Report.pdf\"");
    			            	os.write(JasperManager.printReportToPdf(jasperPrint));
    			            	os.close();
    	            	}else if(output.equalsIgnoreCase("excel")){
    	            			response.setContentType("application/vnd.ms-excel");
    	            			response.setHeader ("Content-Disposition", "attachment;filename=\"Mis24Report.xls\"");
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
    		            		 response.setHeader ("Content-Disposition", "attachment;filename=\"Mis24Report.html\"");
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
                
	}

}
