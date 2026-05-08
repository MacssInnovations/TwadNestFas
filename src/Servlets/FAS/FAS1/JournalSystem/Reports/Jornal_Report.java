package Servlets.FAS.FAS1.JournalSystem.Reports;



import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
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
public class Jornal_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public Jornal_Report() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		String que="",que2="",todate="",dateString="",fromdate="",dateString1="";
		Calendar c;
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
		String strcommand=request.getParameter("command");
		 

		java.util.Date d = null;
        java.util.Date d1 = null;
        java.sql.Date FromDate = null;
        java.sql.Date ToDate = null;
	        double Amount=0.0;
	        try {
	            todate = request.getParameter("txtTo_date");
	            //System.out.println("fromdate***********"+fromdate);
	        } catch (Exception e) {
	            System.out.println(e);
	        }
	        try {
	            fromdate = request.getParameter("txtFrom_date");
	            //System.out.println("fromdate***********"+fromdate);
	        } catch (Exception e) {
	            System.out.println(e);
	        }

	        if (!fromdate.equalsIgnoreCase("") &&
	                !todate.equalsIgnoreCase("")) {


	                System.out.println("before converting from date");
	                 dateString = fromdate;
	                SimpleDateFormat dateFormat =
	                    new SimpleDateFormat("dd/MM/yyyy");

	                try {
						d = dateFormat.parse(fromdate.trim());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                System.out.println("util date is:" + d);
	                dateFormat.applyPattern("dd-MMM-yy");
	                dateString = dateFormat.format(d);
	                System.out.println("dateString "+dateString);
	                //FromDate = java.sql.Date.valueOf(dateString);
	                //System.out.println("FromDate "+FromDate );


	                System.out.println("before converting  to date");
	                 dateString1 = todate;
	                SimpleDateFormat dateFormat1 =
	                    new SimpleDateFormat("dd/MM/yyyy");

	                try {
						d1 = dateFormat1.parse(todate.trim());
					} catch (ParseException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
	                dateFormat1.applyPattern("dd-MMM-yy");
	                dateString1 = dateFormat1.format(d1);
	                //ToDate = java.sql.Date.valueOf(dateString1);
	                
	                //System.out.println("Todate      :" + ToDate);
	            }
	       
	         
	        System.out.println("tesat");
	         int txtCB_Year_to = Integer.parseInt(request.getParameter("Year"));
	         
	 		int txtCB_Month_to = Integer.parseInt(request.getParameter("Month"));
	 		
	 		String monthInWords="";
	        if(txtCB_Month_to==1)
	        monthInWords="January";
	        else if(txtCB_Month_to==2)
	        monthInWords="February";
	        else if(txtCB_Month_to==3)
	        monthInWords="March";
	        else if(txtCB_Month_to==4)
	        monthInWords="April";
	        else if(txtCB_Month_to==5)
	        monthInWords="May";
	        else if(txtCB_Month_to==6)
	        monthInWords="June";
	        else if(txtCB_Month_to==7)
	        monthInWords="July";
	        else if(txtCB_Month_to==8)
	        monthInWords="August";
	        else if(txtCB_Month_to==9)
	        monthInWords="September";
	        else if(txtCB_Month_to==10)
	        monthInWords="October";
	        else if(txtCB_Month_to==11)
	        monthInWords="November";
	        else if(txtCB_Month_to==12)
	        monthInWords="December";
		int cboAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		int cboOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
     /*  String[] sd = request.getParameter("txtTo_date").split("/");
        c =new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,Integer.parseInt(sd[0]));
        java.util.Date d = c.getTime();
        txtTo_date = new Date(d.getTime());
        String[] sd1 = request.getParameter("txtFrom_date").split("/");
        c =new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]) - 1,Integer.parseInt(sd1[0]));
        java.util.Date d1 = c.getTime();
        txtFrom_date = new Date(d1.getTime());
        */
		System.out.println("TESTING ::::"+cboAcc_UnitCode+";;;;;cboAcc_UnitCode"+cboOffice_code);
      
       // String sl_type=request.getParameter("sl_type");
        if(strcommand.equalsIgnoreCase("Jornal_Report_month")) 
		{
		
			que2=" and m.CASHBOOK_YEAR="+txtCB_Year_to+" and m.CASHBOOK_MONTH="+txtCB_Month_to;
			

		}else if(strcommand.equalsIgnoreCase("Jornal_Report_date")) 
		{
			
			que2=" and M.Voucher_Date between '" + dateString + "' and '" + dateString1 +"'";
		
			//que=" and m.PAYMENT_DATE between '" + dateString + "' and '" + dateString1 +"'";
		
		}
        
        File reportFile = null;
			
			
			try {
				
									
				System.out.println("calling servlet...");
				reportFile = new File(getServletContext().getRealPath(
						"org/FAS/FAS1/Reports/JournalSystem/jasper/FASJournalReport.jasper"));
				System.out.println("upto"+reportFile);
				if (!reportFile.exists())
					throw new JRRuntimeException(
							"File J not found. The report design must be compiled first.");

				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());
				System.out.println("file report testing :::"+cboAcc_UnitCode);
				Map map = null;
				map = new HashMap();
				map.put("unitid", cboAcc_UnitCode);
				map.put("officeid", cboOffice_code);
				map.put("res_quer",que2);
				System.out.println("que2:::"+que2);
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						jasperReport, map, con);
				System.out.println("que2:::"+que2);
				System.out.println("testing :::"+cboAcc_UnitCode);
				
				
					byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
					//System.out.println("Length  " + buf.length);
					response.setContentType("application/pdf");
					response.setContentLength(buf.length);
					//System.out.println("test");
					response.setHeader("Content-Disposition",
							"attachment;filename=\"Journal_Report.pdf\"");
					OutputStream out = response.getOutputStream();
					//System.out.println("testing");
					out.write(buf, 0, buf.length);
					out.close();
			
				
				 
				
			} catch (Exception ex) {
				String connectMsg = "Could not create the report "
						+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
				System.out.println("connectMsg"+connectMsg);
			}
		}
	

}