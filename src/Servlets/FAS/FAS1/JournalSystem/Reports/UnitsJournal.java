package Servlets.FAS.FAS1.JournalSystem.Reports;



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
public class UnitsJournal extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public UnitsJournal() {
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
        String displayOrder=request.getParameter("displayingOrder");
        System.out.println("displayOrder"+displayOrder);
        
        if(request.getParameter("month_year").equalsIgnoreCase("particular_cb"))
        {
        	System.out.println("test"+request.getParameter("month_year"));
        	 try{
        		 cashYear=Integer.parseInt(request.getParameter("txtCB_Year"));}
        	 
             catch(NumberFormatException e){System.out.println("exception"+e );}
            try{
             cashMonth=Integer.parseInt(request.getParameter("txtCB_Month"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
        	String monthInWords="";
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
	        System.out.println("test"+monthInWords);
			if(displayOrder.equalsIgnoreCase("ALL")){
				System.out.println("testing");
			try {
				System.out.println("calling servlet...");
				reportFile = new File(getServletContext().getRealPath(
						"org/FAS/FAS1/Reports/JournalSystem/jasper/UnitAllJournalReport.jasper"));
				System.out.println("upto"+reportFile);
				if (!reportFile.exists())
					throw new JRRuntimeException(
							"File J not found. The report design must be compiled first.");

				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());
				
				Map map = null;
				map = new HashMap();
				map.put("year", cashYear);
				map.put("month", cashMonth);
				map.put("monthInWords",monthInWords);
				
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						jasperReport, map, con);
				
					byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
					
					response.setContentType("application/pdf");
					response.setContentLength(buf.length);
					//System.out.println("test");
					response.setHeader("Content-Disposition",
							"attachment;filename=\"UnitJournal_Report.pdf\"");
					OutputStream out = response.getOutputStream();
					//System.out.println("testing");
					out.write(buf, 0, buf.length);
					out.close();
	} 
				
			 catch (Exception ex) {
				String connectMsg = "Could not create the report "
						+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
				System.out.println("connectMsg"+connectMsg);
			 }
			}
			
			
			
			else if(displayOrder.equalsIgnoreCase("RW") || displayOrder.equalsIgnoreCase("OW")){
				office_id=Integer.parseInt(request.getParameter("txtRegionId"));
	        	 try {
	        	        PreparedStatement ps=null;
	        	        ResultSet rs=null;
	        	       
	        	        ps=con.prepareStatement("select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
	        	        ps.setInt(1,office_id);
	        	        rs=ps.executeQuery();
	        	        if(rs.next()) {
	        	        	office_id=rs.getInt("ACCOUNTING_UNIT_OFFICE_ID");
	        	        }
	        	        }
	        	        catch (SQLException e) {
	        	            System.out.println("SQL Exception -->"+e);
	        	        }
				try {
					
					
					System.out.println("calling servlet...");
					if(displayOrder=="RW"){
					reportFile = new File(getServletContext().getRealPath(
							"org/FAS/FAS1/Reports/JournalSystem/jasper/UnitAllJournalReportRW.jasper"));}
					else
					{
						reportFile = new File(getServletContext().getRealPath(
					"org/FAS/FAS1/Reports/JournalSystem/jasper/UnitAllJournalReportOW.jasper"));}
					System.out.println("upto"+reportFile);
					if (!reportFile.exists())
						throw new JRRuntimeException(
								"File J not found. The report design must be compiled first.");

					JasperReport jasperReport = (JasperReport) JRLoader
							.loadObject(reportFile.getPath());
					
					Map map = null;
					map = new HashMap();
					map.put("year", cashYear);
					map.put("month", cashMonth);
					map.put("monthInWords",monthInWords);
					map.put("office_id",office_id);
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							jasperReport, map, con);
					
						byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
						
						response.setContentType("application/pdf");
						response.setContentLength(buf.length);
						//System.out.println("test");
						response.setHeader("Content-Disposition",
								"attachment;filename=\"UnitJournal_ReportRW.pdf\"");
						OutputStream out = response.getOutputStream();
						//System.out.println("testing");
						out.write(buf, 0, buf.length);
						out.close();
		} 
					
				 catch (Exception ex) {
					String connectMsg = "Could not create the report "
							+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
					System.out.println("connectMsg"+connectMsg);
				 }
				
			}
			
				
			
        	
		}
        else  if(request.getParameter("month_year").equalsIgnoreCase("more_cb"))
        {
        	int txtCB_Month_to=0,txtCB_Year_from=0,txtCB_Year_to=0,txtCB_Month_from=0;
        	
       	 try{txtCB_Year_from=Integer.parseInt(request.getParameter("txtCB_Year_from"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
            
            try{txtCB_Year_to=Integer.parseInt(request.getParameter("txtCB_Year_to"));}
            catch(NumberFormatException e){System.out.println("exception"+e );}
           
            txtCB_Month_from=Integer.parseInt(request.getParameter("txtCB_Month_from"));
            txtCB_Month_to=Integer.parseInt(request.getParameter("txtCB_Month_to"));
            
           
            
               
       String monthInWords="";
       if(txtCB_Month_from==1)
           monthInWords="January";
       else if(txtCB_Month_from==2)
           monthInWords="February";
       else if(txtCB_Month_from==3)
           monthInWords="March";
       else if(txtCB_Month_from==4)
           monthInWords="April";
       else if(txtCB_Month_from==5)
           monthInWords="May";
       else if(txtCB_Month_from==6)
           monthInWords="June";
       else if(txtCB_Month_from==7)
           monthInWords="July";
       else if(txtCB_Month_from==8)
           monthInWords="August";
       else if(txtCB_Month_from==9)
           monthInWords="September";
       else if(txtCB_Month_from==10)
           monthInWords="October";
       else if(txtCB_Month_from==11)
           monthInWords="November";
       else if(txtCB_Month_from==12)
           monthInWords="December";
       
       String monthInWords_to="";
       if(txtCB_Month_to==1)
    	   monthInWords_to="January";
       else if(txtCB_Month_to==2)
    	   monthInWords_to="February";
       else if(txtCB_Month_to==3)
    	   monthInWords_to="March";
       else if(txtCB_Month_to==4)
    	   monthInWords_to="April";
       else if(txtCB_Month_to==5)
    	   monthInWords_to="May";
       else if(txtCB_Month_to==6)
    	   monthInWords_to="June";
       else if(txtCB_Month_to==7)
    	   monthInWords_to="July";
       else if(txtCB_Month_to==8)
    	   monthInWords_to="August";
       else if(txtCB_Month_to==9)
    	   monthInWords_to="September";
       else if(txtCB_Month_to==10)
    	   monthInWords_to="October";
       else if(txtCB_Month_to==11)
    	   monthInWords_to="November";
       else if(txtCB_Month_to==12)
    	   monthInWords_to="December";
       
       String from_to="From "+monthInWords+" "+txtCB_Year_from+" to "+monthInWords_to+" "+txtCB_Year_to;
           System.out.println("from_to:::"+from_to);
           if(displayOrder.equalsIgnoreCase("ALL")){
				System.out.println("testing");
			try {
				System.out.println("calling servlet...");
				reportFile = new File(getServletContext().getRealPath(
						"org/FAS/FAS1/Reports/JournalSystem/jasper/UnitAllJournalReportDate.jasper"));
				System.out.println("upto"+reportFile);
				if (!reportFile.exists())
					throw new JRRuntimeException(
							"File J not found. The report design must be compiled first.");

				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());
				
				Map map = null;
				
				map=new HashMap();
			      System.out.println("displayOrder:for :::"+displayOrder);
			      
			      
			       			 map.put("txtCB_Year_from",txtCB_Year_from);     
				        	 map.put("txtCB_Year_to",txtCB_Year_to);
				        	 
				        	 map.put("txtCB_Month_from",txtCB_Month_from);     
				        	 map.put("txtCB_Month_to",txtCB_Month_to);
				               map.put("monthInWords_to", monthInWords_to);
				            
				            
				
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						jasperReport, map, con);
				
					byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
					
					response.setContentType("application/pdf");
					response.setContentLength(buf.length);
					//System.out.println("test");
					response.setHeader("Content-Disposition",
							"attachment;filename=\"UnitJournal_Report.pdf\"");
					OutputStream out = response.getOutputStream();
					//System.out.println("testing");
					out.write(buf, 0, buf.length);
					out.close();
	} 
				
			 catch (Exception ex) {
				String connectMsg = "Could not create the report "
						+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
				System.out.println("connectMsg"+connectMsg);
			 }
			}
			
			
			
			else if(displayOrder.equalsIgnoreCase("RW") || displayOrder.equalsIgnoreCase("OW")){
				office_id=Integer.parseInt(request.getParameter("txtRegionId"));
	        	 try {
	        	        PreparedStatement ps=null;
	        	        ResultSet rs=null;
	        	       
	        	        ps=con.prepareStatement("select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=?");
	        	        ps.setInt(1,office_id);
	        	        rs=ps.executeQuery();
	        	        if(rs.next()) {
	        	        	office_id=rs.getInt("ACCOUNTING_UNIT_OFFICE_ID");
	        	        }
	        	        }
	        	        catch (SQLException e) {
	        	            System.out.println("SQL Exception -->"+e);
	        	        }
				try {
					
					
					System.out.println("calling servlet...");
					if(displayOrder.equalsIgnoreCase("RW")){
					reportFile = new File(getServletContext().getRealPath(
							"org/FAS/FAS1/Reports/JournalSystem/jasper/UnitAllJournalReportDateRW.jasper"));}
					
					else{
						reportFile = new File(getServletContext().getRealPath(
					"org/FAS/FAS1/Reports/JournalSystem/jasper/UnitAllJournalReportDateOW.jasper"));}
					System.out.println("upto"+reportFile);
					if (!reportFile.exists())
						throw new JRRuntimeException(
								"File J not found. The report design must be compiled first.");

					JasperReport jasperReport = (JasperReport) JRLoader
							.loadObject(reportFile.getPath());
					
					Map map = null;
					map = new HashMap();
					map.put("year", cashYear);
					map.put("month", cashMonth);
					map.put("monthInWords",monthInWords);
					map.put("office_id",office_id);
					JasperPrint jasperPrint = JasperFillManager.fillReport(
							jasperReport, map, con);
					
						byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
						
						response.setContentType("application/pdf");
						response.setContentLength(buf.length);
						//System.out.println("test");
						response.setHeader("Content-Disposition",
								"attachment;filename=\"UnitJournal_ReportDateRW.pdf\"");
						OutputStream out = response.getOutputStream();
						//System.out.println("testing");
						out.write(buf, 0, buf.length);
						out.close();
		} 
					
				 catch (Exception ex) {
					String connectMsg = "Could not create the report "
							+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
					System.out.println("connectMsg"+connectMsg);
				 }
				
			}
			
				
			
       	
		}
	}
	}
