package Servlets.FAS.FAS1.JournalSystem.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
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
import Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver;
/**
 * Servlet implementation class Pensioner_Report
 */
public class Pensioner_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;   
	 private static final String CONTENT_TYPE ="text/html; charset=windows-1252";
	    Connection connection = null;

	    public void init(ServletConfig config) throws ServletException {
	        super.init(config);
	    }
	    
    public Pensioner_Report() {
        super();
       
    }

	
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

        Connection connection = null;
        PreparedStatement ps = null,psss=null;
        ResultSet rs = null;
        String xml = "";
        int cmbOffice_code=0,listtype=0,grouptype=0,cmbAcc_UnitCode=0;
        String command=request.getParameter("command");
        System.out.println("command::::"+command);
        PrintWriter out = response.getWriter();
  	   response.setHeader("cache-control","no-cache");
 	      String CONTENT_TYPE = "text/xml; charset=windows-1252";
 	     // response.setContentType(CONTENT_TYPE);
       
        try {
            LoadDriver load = new LoadDriver();
            connection = load.getConnection();
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }
        response.setContentType(CONTENT_TYPE);
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        int count = 0;
        //int unitCode=Integer.parseInt(request.getParameter("unitCode"));
        if(command.equalsIgnoreCase("listtype"))
        {
        	xml = "<response>"; 
			xml=xml+"<command>listtype</command>";			
			try {
				ps = connection
						.prepareStatement("select CHK_LIST_ID,CHK_LIST_DESC from HRM_PEN_PAY_CHK_LIST_MASTER order by CHK_LIST_ID");
				
				rs = ps.executeQuery();
				while (rs.next()) {
					xml = xml + "<ListID>" + rs.getInt("CHK_LIST_ID")
							  + "</ListID>";
					xml = xml + "<ListDesc>" + rs.getString("CHK_LIST_DESC")
					          + "</ListDesc>";
					count++;
				}
				if(count>0)
				{
				xml = xml + "<flag3>success</flag3>";
				xml=xml+"<count>"+count+"</count>";
				}
				else
				{
					xml = xml + "<flag3>failure</flag3>";
				}
			} catch (Exception e) {
				xml = xml + "<flag3>failure</flag3>";
				e.printStackTrace();
			}
			xml=xml+"</response>";
			out.println(xml);
			System.out.println(xml);
        }else if(command.equalsIgnoreCase("listgroup"))
        {
        	
        	int officecode=Integer.parseInt(request.getParameter("cmbOffice_code"));
        	xml = "<response>"; 
			xml=xml+"<command>listgroup</command>";

			
			try {
				String sql="select GROUP_ID,GROUP_DESC from HRM_PEN_PAY_CHKLIST_GROUP where GROUP_OFFICE_ID="+officecode+" order by GROUP_ID";
				System.out.println("sql::::"+sql);
				ps = connection.prepareStatement(sql);
				
				rs = ps.executeQuery();
				while (rs.next()) {
					xml = xml + "<ListID>" + rs.getInt("GROUP_ID")
							  + "</ListID>";
					xml = xml + "<ListDesc>" + rs.getString("GROUP_DESC")
					          + "</ListDesc>";
					count++;
				}
				if(count>0)
				{
				xml = xml + "<flag3>Success1</flag3>";
				xml=xml+"<count>"+count+"</count>";
				}
				else
				{
					xml = xml + "<flag3>failure</flag3>";
				}
			} catch (Exception e) {
				xml = xml + "<flag3>failure</flag3>";
				e.printStackTrace();
			}
			xml=xml+"</response>";
			out.println(xml);
			System.out.println(xml);
        }else if(command.equalsIgnoreCase("loadofficeid"))
        {
        	
        	//int officecode=Integer.parseInt(request.getParameter("cmbOffice_code"));
        	xml = "<response>"; 
			xml=xml+"<command>loadofficeid</command>";

			
			try {
				String sql="select OFFICE_ID,OFFICE_NAME from COM_MST_OFFICES where PENSION_PAYMENT_OFFICE='Y' order by OFFICE_ID";
				System.out.println("sql::::"+sql);
				ps = connection.prepareStatement(sql);
				
				rs = ps.executeQuery();
				while (rs.next()) {
					xml = xml + "<OFFICE_ID>" + rs.getInt("OFFICE_ID")
							  + "</OFFICE_ID>";
					xml = xml + "<OFFICE_NAME>" + rs.getString("OFFICE_NAME")+"( "+rs.getInt("OFFICE_ID")+" )"
					          + "</OFFICE_NAME>";
					count++;
				}
				if(count>0)
				{
				xml = xml + "<flag3>Success</flag3>";
				xml=xml+"<count>"+count+"</count>";
				}
				else
				{
					xml = xml + "<flag3>failure</flag3>";
				}
			} catch (Exception e) {
				xml = xml + "<flag3>failure</flag3>";
				e.printStackTrace();
			}
			xml=xml+"</response>";
			out.println(xml);
			System.out.println(xml);
        }//
	}
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//System.out.println("post methosdd;;;");
		
		try {
	            HttpSession session = request.getSession(false);
	            if (session == null) {
	                System.out.println(request.getContextPath() + "/index.jsp");
	                response.sendRedirect(request.getContextPath() + "/index.jsp");

	            }
	            System.out.println(session);

	        } catch (Exception e) {
	            System.out.println("Redirect Error :" + e);
	        }

	        String opt = "";
	        response.setContentType(CONTENT_TYPE);
	        try {


	            ResourceBundle rs =
	                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
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
	            connection =
	                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
	                                                strdbpassword.trim());
	        } catch (Exception ex) {
	            String connectMsg =
	                "Could not create the connection" + ex.getMessage() + " " +
	                ex.getLocalizedMessage();
	            System.out.println(connectMsg);
	        }
	        
	        //reportView
	        String strCommand = request.getParameter("command");
	        System.out.println("strCommand "+strCommand);
	        if(strCommand.equalsIgnoreCase("reportView")){
	        JasperDesign jasperDesign = null;
	        File reportFile = null;

	        try {
	           // System.out.println("calling servlet");

	            String txtCB_Year = request.getParameter("txtCB_Year");
	            String txtCB_Month = request.getParameter("txtCB_Month");
	           
	            String rtype = request.getParameter("txtoption");
	           // String cmbAcc_UnitCode = request.getParameter("cmbAcc_UnitCode");
	            String cmbOffice_code = request.getParameter("cmbOffice_code");
	            int listtype=Integer.parseInt(request.getParameter("listtype"));
	            int grouptype=Integer.parseInt(request.getParameter("grouptype"));
	            String penfamily=request.getParameter("penfamily");
	            
	         //   String monthvalue = "";
	            System.out.println("Cashbook Year From :listtype " + txtCB_Year+" "+ listtype);
	            System.out.println("Cashbook Month: FRom " + txtCB_Month);
	            System.out.println("grouptype +penfamily " + grouptype+" "+ penfamily);
	           
	          //  System.out.println("accounting unit id is:" + cmbAcc_UnitCode);
	            System.out.println("office code is:" + cmbOffice_code);


	          //  int accountingunit = Integer.parseInt(cmbAcc_UnitCode);
	            int accountingoffice = Integer.parseInt(cmbOffice_code);

	            int year = Integer.parseInt(txtCB_Year);
	            int month = Integer.parseInt(txtCB_Month);
	            String monthInWords="";
	            if(month==1)
	            monthInWords="January";
	            else if(month==2)
	            monthInWords="February";
	            else if(month==3)
	            monthInWords="March";
	            else if(month==4)
	            monthInWords="April";
	            else if(month==5)
	            monthInWords="May";
	            else if(month==6)
	            monthInWords="June";
	            else if(month==7)
	            monthInWords="July";
	            else if(month==8)
	            monthInWords="August";
	            else if(month==9)
	            monthInWords="September";
	            else if(month==10)
	            monthInWords="October";
	            else if(month==11)
	            monthInWords="November";
	            else if(month==12)
	            monthInWords="December"; 
	                reportFile =
	                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/JournalSystem/jasper/pensioner_journal_report.jasper"));

	            if (!reportFile.exists())
	                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

	            JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());


	            System.out.println("opt::" + rtype);
	            //   JasperReport jasperReport =     JasperCompileManager.compileReport(jasperDesign);
	            Map map = new HashMap();
	            map.put("accountofficeid", accountingoffice);
	            //map.put("accountingunitid", accountingunit);
	            map.put("cashbookyear", year);
	            map.put("cashbookmonth", month);
	            map.put("listtype", listtype);
	            map.put("grouptype", grouptype);
	            map.put("penfamily", penfamily);
	            map.put("monthInWords", monthInWords);
	            
	            JasperPrint jasperPrint =JasperFillManager.fillReport(jasperReport, map, connection);


	            if (rtype.equalsIgnoreCase("HTML")) {
	                response.setContentType("text/html");
	                response.setHeader("Content-Disposition",
	                                   "attachment;filename=\"pensioner_Report1.html\"");
	                PrintWriter out = response.getWriter();
	                JRHtmlExporter exporter = new JRHtmlExporter();
	                // File f=new File(getServletContext().getRealPath("/WEB-INF/Report/"));
	                //  exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,true);
	                //  exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,getServletContext().getRealPath("/WEB-INF/Report/"));
	                //  exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR,f);
	                exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
	                                      false);
	                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
	                                      jasperPrint);
	                exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
	                exporter.exportReport();
	                out.flush();
	                out.close();
	            } else if (rtype.equalsIgnoreCase("PDF")) {
	                byte buf[] =
	                    JasperExportManager.exportReportToPdf(jasperPrint);
	                response.setContentType("application/pdf");
	                response.setContentLength(buf.length);
	                // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
	                //response.setContentType("application/force-download");

	                response.setHeader("Content-Disposition",
	                                   "attachment;filename=\"pensioner_Report1.pdf\"");
	                OutputStream out = response.getOutputStream();
	                out.write(buf, 0, buf.length);
	                out.close();
	            } else if (rtype.equalsIgnoreCase("EXCEL")) {

	                response.setContentType("application/vnd.ms-excel");
	                response.setHeader("Content-Disposition",
	                                   "attachment;filename=\"pensioner_Report1.xls\"");
	                JRXlsExporter exporterXLS = new JRXlsExporter();
	                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
	                                         jasperPrint);

	                ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
	                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
	                                         xlsReport);
	                exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
	                                         Boolean.FALSE);
	                exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,
	                                         Boolean.TRUE);
	                exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
	                                         Boolean.FALSE);
	                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
	                                         Boolean.TRUE);
	                exporterXLS.exportReport();
	                byte[] bytes;
	                bytes = xlsReport.toByteArray();
	                ServletOutputStream ouputStream = response.getOutputStream();
	                ouputStream.write(bytes, 0, bytes.length);
	                ouputStream.flush();
	                ouputStream.close();

	            } else if (rtype.equalsIgnoreCase("TXT")) {

	                response.setContentType("text/plain");
	                response.setHeader("Content-Disposition",
	                                   "attachment;filename=\"pensioner_Report1.txt\"");

	                JRTextExporter exporter = new JRTextExporter();
	                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
	                                      jasperPrint);
	                ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
	                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
	                                      txtReport);
	                exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,
	                                      new Integer(200));
	                exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,
	                                      new Integer(50));
	                exporter.exportReport();

	                byte[] bytes;
	                bytes = txtReport.toByteArray();
	                ServletOutputStream ouputStream = response.getOutputStream();
	                ouputStream.write(bytes, 0, bytes.length);
	                ouputStream.flush();
	                ouputStream.close();

	            }


	        } catch (Exception ex) {
	            String connectMsg =
	                "Could not create the report " + ex.getMessage() + " " +
	                ex.getLocalizedMessage();
	            System.out.println(connectMsg);
	        }
	        }


	}

}
