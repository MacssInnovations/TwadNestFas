package Servlets.FAS.FAS1.MonthEnd.servlets;

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

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;
import Servlets.FAS.FAS1.CommonControls.servlets.Restricted_AccountHead;
import Servlets.Security.classes.UserProfile;

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

/**
 * Servlet implementation class control_statement
 */
public class control_statement extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
		    Connection connection = null;    
   
    public control_statement() {
        super();
        // TODO Auto-generated constructor stub
    }

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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
		
	    String opt="";
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

	        ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
	        Class.forName(strDriver.trim());
	        connection =DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
	    } catch (Exception ex) {
	        String connectMsg ="Could not create the connection" + ex.getMessage() + " " + 
	        ex.getLocalizedMessage();
	        System.out.println(connectMsg);
	    }
	    JasperDesign jasperDesign = null;
	    File reportFile=null;
		String accode="";
		String offname=null;
		int hc=0;
		String fin="",strQuery="";
	    try 
	    {
	        System.out.println("inside servlet>>>>>>>>>>>>>>>..");
	        String txtRegionId= request.getParameter("txtRegionId");
	        
	       
	        
	        String txtCB_Year_From=request.getParameter("txtCB_Year_From");
	        String txtCB_Year_To=request.getParameter("txtCB_Year_To");
	        
	        String txtCB_Month_From=request.getParameter("txtCB_Month_From");
	        String txtCB_Month_To=request.getParameter("txtCB_Month_To");
	        System.out.println("txtCB_Month>>>>"+txtCB_Month_To);
	        
	       
	        String rtype= request.getParameter("txtoption");
	        int regionId=Integer.parseInt(txtRegionId);
	        int yearfrom=Integer.parseInt(txtCB_Year_From);
	        int yearto=Integer.parseInt(txtCB_Year_To);
	        int monthfrom=Integer.parseInt(txtCB_Month_From);
	        int monthto=Integer.parseInt(txtCB_Month_To);
	        
	        int supno=Integer.parseInt(request.getParameter("txtsupplement_no"));
	        
	        String Acc_Head_Type=request.getParameter("Acc_Head_Type");
	        if(Acc_Head_Type.equals("A"))
	        {
	        	accode=" ";
	        	 fin="Month wise control figure during the Financial Year:"+ yearfrom+" - "+yearto;
	        }
	        else
	        {
	        	hc=Integer.parseInt(request.getParameter("txtAcc_HeadCode"));
	         accode=" and Account_Head_Code="+hc+" ";
	         fin="Month wise control figure for the head of account "+hc+" during the Financial Year:"+ yearfrom+" - "+yearto;
	        }
	        System.out.println("regionId::::::::::"+regionId);
	        if(regionId==5000)
	        {
	        	strQuery=" AND t.ACCOUNTING_UNIT_ID in(SELECT Accounting_Unit_Id "+
						 "   FROM Fas_Mst_Acct_Units "+
						 "  WHERE Accounting_Unit_Office_Id IN "+
						 "   (SELECT Office_Id"+
						 "  FROM Com_Mst_All_Offices_View "+
						 " Where Office_Id ="+regionId+ "))";	
	        }
	        else if(regionId==1)
	        {
	        	strQuery="";
	        }
	        else
	        {
	        	strQuery=" AND t.ACCOUNTING_UNIT_ID in(SELECT Accounting_Unit_Id "+
						 "   FROM Fas_Mst_Acct_Units "+
						 "  WHERE Accounting_Unit_Office_Id IN "+
						 "   (SELECT Office_Id"+
						 "  FROM Com_Mst_All_Offices_View "+
						 " Where Region_Office_Id ="+regionId+ "))";
	        }
	        System.out.println("acchead:::"+accode);
	        System.out.println("strQuery::::"+strQuery);
	        if(regionId!=1){
	        try {
	            PreparedStatement ps=null;
	            ResultSet rs=null;
	            String UnitName="",OfficeName="";
	            ps=connection.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
	            ps.setInt(1,regionId);
	            rs=ps.executeQuery();
	            if(rs.next()) {
	                offname=rs.getString("OFFICE_NAME");
	            }
	            }
	        catch(Exception e)
	        {
	        	System.out.println("e in getting region:"+e);
	        }
	        }
	        else
	        {
	        	offname="Entire State";
	        }
	        String monthInWords="";
	        if(monthfrom==1)
	            monthInWords="January";
	        else if(monthfrom==2)
	            monthInWords="February";
	        else if(monthfrom==3)
	            monthInWords="March";
	        else if(monthfrom==4)
	            monthInWords="April";
	        else if(monthfrom==5)
	            monthInWords="May";
	        else if(monthfrom==6)
	            monthInWords="June";
	        else if(monthfrom==7)
	            monthInWords="July";
	        else if(monthfrom==8)
	            monthInWords="August";
	        else if(monthfrom==9)
	            monthInWords="September";
	        else if(monthfrom==10)
	            monthInWords="October";
	        else if(monthfrom==11)
	            monthInWords="November";
	        else if(monthfrom==12)
	            monthInWords="December";
	   
	      
	        reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/MonthEnd/jasper/control_statement.jasper")); 
	          System.out.println("reportFile"+reportFile);   
	        if (!reportFile.exists())
	        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	        
	        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
	       System.out.println("jasperReport"+jasperReport);
  
	       Map map=new HashMap();
	       
	        map.put("regionId",regionId);
	       
	        map.put("yearfrom",yearfrom);
	        map.put("yearto",yearto);        
	        map.put("monthfrom",monthfrom);
	        map.put("monthto",monthto);     
	        map.put("acchead",accode);
	        map.put("supno",supno);
	        map.put("financialyear",fin);
	        map.put("offname",offname);
	        map.put("strQuery",strQuery);
	        
	        
	        
	      
	        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);            
	        if (rtype.equalsIgnoreCase("HTML"))   
	        {
	                    response.setContentType("text/html");
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TWAD_A88.html\"");
	                    PrintWriter out = response.getWriter();
	                    JRHtmlExporter exporter = new JRHtmlExporter();
	                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
	                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	                    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
	                    exporter.exportReport();
	                    out.flush();
	                    out.close();
	        }
	        else      if (rtype.equalsIgnoreCase("PDF"))   
	        {
	        	System.out.println("PDF:::::::::::");
	                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
	                    response.setContentType("application/pdf");
	                    response.setContentLength(buf.length);
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"control_figure.pdf\"");
	                    OutputStream out = response.getOutputStream();
	                    out.write(buf, 0, buf.length);
	                    out.close();
	        }
	        else      if (rtype.equalsIgnoreCase("EXCEL"))   
	        {

	                	response.setContentType("application/vnd.ms-excel");
	                    response.setHeader ("Content-Disposition", "attachment;filename=\"TWAD_A88.xls\"");
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
	        else      if (rtype.equalsIgnoreCase("TXT"))   
	        {
	        
		                response.setContentType("text/plain");
		                response.setHeader ("Content-Disposition", "attachment;filename=\"TWAD_A88.txt\"");
		                     
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
	        String connectMsg = 
	        "Could not create the report " + ex.getMessage() + " " + 
	        ex.getLocalizedMessage();
	        System.out.println(connectMsg);
	    }
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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


		        int employee_id = 0;

		        HttpSession session = request.getSession(false);
		        UserProfile empProfile =
		            (UserProfile)session.getAttribute("UserProfile");
		        System.out.println("user id::" + empProfile.getEmployeeId());
		        employee_id = empProfile.getEmployeeId();

		        Connection con = null;
		        ResultSet rs = null;
		        PreparedStatement ps = null;
		        //String xml="";
		        response.setContentType(CONTENT_TYPE);
		        response.setHeader("Cache-Control", "no-cache");
		        PrintWriter out = response.getWriter();
		        String strCommand = "";
		        try {
		            ResourceBundle rs1 =
		                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
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
		            con =
		 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
		                             strdbpassword.trim());
		        } catch (Exception e) {
		            System.out.println("Exception in opening connection :" + e);
		            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

		        }

		        try {
		            strCommand = request.getParameter("Command");
		           // System.out.println("assign..here command..." + strCommand);

		        }

		        catch (Exception e) {
		            System.out.println("Exception in assigning..." + e);
		        }
		        
		            
		        if (strCommand.equalsIgnoreCase("checkCode")) {

		            String CONTENT_TYPE = "text/xml; charset=windows-1252";
		            response.setContentType(CONTENT_TYPE);
		            String xml = "";
		            xml = "<response><command>" + strCommand + "</command>";
		            int txtAcc_HeadCode = 0;  
		            try {

		                txtAcc_HeadCode =
		                        Integer.parseInt(request.getParameter("txtAcc_HeadCode"));

		            } catch (Exception e) {
		                System.out.println("Exception to catch account head ");
		            }
		         
		          //  System.out.println("account head code--->  "+txtAcc_HeadCode);

		                try {
		                    ps = con.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and ACCOUNT_HEAD_CODE=?");
		                    ps.setInt(1, txtAcc_HeadCode);
		                    rs = ps.executeQuery();
		                    if (rs.next()) {
		                        xml =xml + "<flag>success</flag><hid>" + txtAcc_HeadCode + "</hid><hdesc>" +
							   rs.getString("ACCOUNT_HEAD_DESC") + "</hdesc>" ;
			
		                       
		                    } else {
		                        System.out.println("No record found");
		                        xml = xml + "<flag>failure</flag>";
		                    }


		                } catch (Exception e) {
		                    System.out.println("catch..HERE.in load head code." + e);
		                    xml = xml + "<flag>failure</flag>";
		                }
		            xml = xml + "</response>";
		            System.out.println(xml);
		            out.println(xml);
		        } 
		        
		      


	}

}
