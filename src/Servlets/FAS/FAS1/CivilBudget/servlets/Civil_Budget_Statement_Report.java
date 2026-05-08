package Servlets.FAS.FAS1.CivilBudget.servlets;



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
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import org.apache.poi.hssf.usermodel.HSSFRow;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
public class Civil_Budget_Statement_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Civil_Budget_Statement_Report() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String que="",que2="",todate="",dateString="";
		int cashYear=0,cashMonth=0,office_id=0;
		Calendar c; File reportFile = null;
		Connection con = null;
		try {
			HttpSession session = request.getSession(false);
			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");
				return;
			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		try {
			ResourceBundle rs1 = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
			String ConnectionString = "";

			String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
			String strdsn = rs1.getString("Config.DSN");
			String strhostname = rs1.getString("Config.HOST_NAME");
			String strportno = rs1.getString("Config.PORT_NUMBER");
			String strsid = rs1.getString("Config.SID");
			String strdbusername = rs1.getString("Config.USER_NAME");
			String strdbpassword = rs1.getString("Config.PASSWORD");
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
			Class.forName(strDriver.trim());
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);

		}
       String RegionId="";
		int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));			
		
        	RegionId= request.getParameter("txtRegionId");
            System.out.println("RegionId"+RegionId); 
        
         String fin_year=request.getParameter("cmbFinancialYear");
	    int Statement_no=Integer.parseInt(request.getParameter("cmbStatementName"));
	    System.out.println("no"+Statement_no);
	    String statement="";
	    String val="";
	    try{
	    	PreparedStatement ps=null;
	    	ResultSet rs=null;
	    	statement="select STATEMENT_DESC from FAS_STATEMENT_MASTER where STATEMENT_NO='"+Statement_no+"'";
ps=con.prepareStatement(statement);
rs=ps.executeQuery();
while (rs.next()) {
	val=rs.getString("STATEMENT_DESC");
                   }
           }
	catch (Exception e) {
			System.out.println(e);
		}
	    
	    
int sta_no=0;
	   
	    System.out.println("sta_no"+sta_no);
	    System.out.println("fin_year"+fin_year);	 
	    String heading="REPORT FOR "+val;
	    String state_head="STATEMENT "+Statement_no;
	    
	    String qry="SELECT B.Accounting_For_Office_Id,(SELECT Office_Name "
			 +"  FROM Com_Mst_Offices S  WHERE Office_Level_Id    IN ('RN','HO') "
			  +" AND Office_Status_Id NOT IN('CL','RD','NC') AND S.Office_Id=B.Accounting_For_Office_Id "
			  +" )AS Office_Name,B.Statement_No,(SELECT Statement_Desc FROM Fas_Statement_Master M "
			  +" WHERE M.Statement_No='"+Statement_no+"' )AS State_Name,Financial_Year, "
			  +" B.Statment_Group_No,(SELECT R.Statement_Group_Desc FROM Fas_Statement_Group_Master R "
			 +" WHERE R.Statement_Name  ='"+Statement_no+"' AND R.Statement_Group_No=B.Statment_Group_No "
			 +" )AS Group_Desc,Budget_Type, Allocation_Date,REF_NO,Approved_By,Remarks,AMOUNT "
			+" FROM Fas_Budget_Allocation b WHERE Accounting_Unit_Id     = 888 " 
			+ " AND Accounting_For_Office_Id = "+RegionId
			+" AND Financial_Year           = '"+fin_year+"' "
			+" AND STATEMENT_NO             = "+Statement_no;
				System.out.println("testing");
			try {
				System.out.println("calling servlet...");
				reportFile = new File(getServletContext().getRealPath(
						"/org/FAS/FAS1/CivilBudget/jasper/Statement_Report_1.jasper"));
				System.out.println("upto"+reportFile);
				if (!reportFile.exists())
					throw new JRRuntimeException(
							"File J not found. The report design must be compiled first.");

				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());
				
				Map map = null;
				map = new HashMap();
				map.put("year", fin_year);
				map.put("qry",qry );
				map.put("heading", heading);
				map.put("state_head",state_head);
			System.out.println("test::::"+qry);
				
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						jasperReport, map, con);
				String rtype=request.getParameter("txtoption");
				
				if (rtype.equalsIgnoreCase("HTML")) {
					response.setContentType("text/html");
					response.setHeader("Content-Disposition",
							"attachment;filename=\"CivilBudget_Report.html\"");
					PrintWriter out = response.getWriter();
					JRHtmlExporter exporter = new JRHtmlExporter();
					// File f=new
					// File(getServletContext().getRealPath("/WEB-INF/Report/"));
					// exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,true);
					// exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,getServletContext().getRealPath("/WEB-INF/Report/"));
					// exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR,f);
					exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,false);
					exporter.setParameter(JRExporterParameter.JASPER_PRINT,jasperPrint);
					exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
					exporter.exportReport();
					out.flush();
					out.close();
				} else if (rtype.equalsIgnoreCase("PDF")) {

					byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
					
					response.setContentType("application/pdf");
					response.setContentLength(buf.length);
					//System.out.println("test");
					response.setHeader("Content-Disposition",
							"attachment;filename=\"CivilBudget_Report.pdf\"");
					OutputStream out = response.getOutputStream();
					//System.out.println("testing");
					out.write(buf, 0, buf.length);
					out.close();
				} else if (rtype.equalsIgnoreCase("EXCEL")) {        	   
	          	   System.out.println("test rtype"+rtype);             	   
	         	   try{
	        			response.setContentType("application/vnd.ms-excel");
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"CivilBudget_Report.xls\"");
	        		//String filename="c:/hello.xls" ;
	        		HSSFWorkbook hwb=new HSSFWorkbook();
	        		HSSFSheet sheet =  hwb.createSheet("new sheet");
	        		
	        		HSSFRow rowhead1=   sheet.createRow((short)0);
	        		 rowhead1.createCell((short) 4).setCellValue(state_head);
	        		 HSSFRow rowhead2=   sheet.createRow((short)1); 
	        		 rowhead2.createCell((short) 4).setCellValue(heading);
	        		 HSSFRow rowhead3=   sheet.createRow((short)2); 
	        		 rowhead3.createCell((short) 3).setCellValue("Financial Year");
	        		 rowhead3.createCell((short) 5).setCellValue(fin_year);
	        		/* HSSFRow rowhead4=   sheet.createRow((short)0); 
	        		 rowhead4.createCell((short) 2).setCellValue("Financial Year");
	        		// rowhead4.createCell((short) 5).setCellValue(fin_year);
	*/        		 
	        		HSSFRow rowhead=   sheet.createRow((short)3);
	        		//Lakshmi------
	        		
		    					 rowhead.createCell((short) 0).setCellValue("Sl.No");
	     	            		rowhead.createCell((short) 1).setCellValue("Heads of Account");
	     	            		rowhead.createCell((short) 2).setCellValue("Amount");
	          		ServletOutputStream fileOut=null;
	        		  System.out.println("test rtype >>>>>>>>>>"+qry);      
	            PreparedStatement ps2=con.prepareStatement(qry);
	          ResultSet rs2=ps2.executeQuery();
	          System.out.println("test excel repot type >>>>>>>>>>"+rs2);      
	           int j=4,jj=1;
	           double amt=0;
	            while(rs2.next())
	            {
	        		HSSFRow row=   sheet.createRow((short)j);
	        		row.createCell((short) 0).setCellValue(jj);
	        		row.createCell((short) 1).setCellValue(rs2.getString("GROUP_DESC"));
	        		row.createCell((short) 2).setCellValue(rs2.getDouble("AMOUNT"));   
	        		amt=amt+rs2.getDouble("AMOUNT");
	        		// kk++;
	        		 j++;
	        		 jj++;
	        	
	        	 }
	            HSSFRow rowlast=   sheet.createRow((short)j);
	            rowlast.createCell((short) 1).setCellValue("Total");
	            rowlast.createCell((short) 2).setCellValue(amt); 
	            fileOut = response.getOutputStream();
	            hwb.write(fileOut);
	       		fileOut.close();
	        		} catch ( Exception ex ) {
	        		    System.out.println(ex);

	        		}

	    		}
					
					
		} 
				/*
					byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
					
					response.setContentType("application/pdf");
					response.setContentLength(buf.length);
					//System.out.println("test");
					response.setHeader("Content-Disposition",
							"attachment;filename=\"CivilBudget_Report.pdf\"");
					OutputStream out = response.getOutputStream();
					//System.out.println("testing");
					out.write(buf, 0, buf.length);
					out.close();
	} */
				
			 catch (Exception ex) {
				String connectMsg = "Could not create the report "
						+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
				System.out.println("connectMsg"+connectMsg);
			 }
			}
			
			
}