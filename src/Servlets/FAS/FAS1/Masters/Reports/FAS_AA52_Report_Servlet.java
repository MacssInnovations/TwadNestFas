package Servlets.FAS.FAS1.Masters.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;
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

public class FAS_AA52_Report_Servlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    Connection connection = null;

   
    public FAS_AA52_Report_Servlet() {
        super();
       
    }
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }
	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

    	PrintWriter out = response.getWriter();
    	   response.setHeader("cache-control","no-cache");
 	      String CONTENT_TYPE = "text/xml; charset=windows-1252";
 	      response.setContentType(CONTENT_TYPE);
 	      
    	PreparedStatement ps = null;
    	ResultSet results;
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

        String selstr = "";
        String selspestr = "";
        String sel = "";
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
        String strCommand = "",unitid="";
		String xml = "";

		HttpSession session = request.getSession(false);

		try {

			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}
		String userid = (String) session.getAttribute("UserId");
		System.out.println("User Id is:" + userid);
		try {
			if (session == null) {
				System.out.println(request.getContextPath() + "/index.jsp");
				response.sendRedirect(request.getContextPath() + "/index.jsp");

			}
			System.out.println(session);

		} catch (Exception e) {
			System.out.println("Redirect Error :" + e);
		}

		try {
			strCommand = request.getParameter("command");
			System.out.println("strCommand:-" + strCommand);
		} catch (Exception e) {
			e.printStackTrace();
		}

		try {
			unitid = request.getParameter("unitid");
			System.out.println("unitid:-" + unitid);
		} catch (Exception e) {
			e.printStackTrace();
		}
		
		Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
				.getAttribute("UserProfile");
	//	int empid = empProfile.getEmployeeId();
	//	String empName = empProfile.getEmployeeName();
		long l = System.currentTimeMillis();
		Timestamp ts = new Timestamp(l);
		
		if (strCommand.equalsIgnoreCase("loadUnitRendering")) {
			//System.out.println("inside loadUnitRendering...");
			int count=0;
			xml = "<response><command>unitLoad</command>";
			
			try {
				//ps = connection.prepareStatement("select distinct ACCT_UNIT_ID_RENDERED_FOR,(select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=ACCT_UNIT_ID_RENDERED_FOR)as unit_name from FAS_ASSET_VAL_AC_RENDER_UNITS where ACCT_RENDERING_UNIT_ID='"+unitid+"'");
				ps = connection.prepareStatement("SELECT ACCT_UNIT_ID_RENDERED_FOR,RENDERING_UNIT_OFFICE_ID,(SELECT OFFICE_NAME FROM COM_MST_OFFICES WHERE OFFICE_ID=RENDERING_UNIT_OFFICE_ID)AS unit_name from fas_asset_val_ac_render_units WHERE ACCT_RENDERING_UNIT_ID='"+unitid+"'");
				results = ps.executeQuery();
				while (results.next()) {
					xml = xml + "<unit_No>"+ results.getString("ACCT_UNIT_ID_RENDERED_FOR")+"-"+results.getString("RENDERING_UNIT_OFFICE_ID")+ "</unit_No>";
					xml=xml+"<desc>"+results.getString("unit_name").trim()+"("+results.getString("RENDERING_UNIT_OFFICE_ID")+")"+"</desc>";
					count++;
				
				}
				xml = xml + "<count>"+count+"</count>";
				if(count==0){
					xml = xml + "<flag>failure</flag>";
				}else{
					xml = xml + "<flag>success</flag>";
					System.out.println("count"+count);

					
				}
								
			} catch (Exception e) {
				xml = xml + "<flag>failure</flag>";
				e.printStackTrace();
			}
			
		}
		
        
		xml = xml+"</response>";
		out.write(xml);
		System.out.println(xml);
	}
	
protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
	try
    {
		System.out.println("this is starting::::::::::FAS_AA52_Report_Servlet");
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
	
    try 
    {
       
       
    	 String cmbAcc_UnitCode=request.getParameter("cmbAcc_UnitCode");
         String cmbOffice_code=request.getParameter("cmbOffice_code");
         
         String cmbFinancialYear=request.getParameter("cmbFinancialYear"); 
         String cmbasset=request.getParameter("cmbasset"); 
       
        String rtype= request.getParameter("txtoption");
        String  assetwise=request.getParameter("allasset");
        
        int accountingunit=Integer.parseInt(cmbAcc_UnitCode);
        int accountingoffice=Integer.parseInt(cmbOffice_code);
       // int yearfrom=Integer.parseInt(cmbFinancialYear);
        int cmbassetcode=Integer.parseInt(cmbasset);

        String check1=null;
        String units="",assetsWise="";
        int unit_rendered=0;
        String check="";
        int officeiddd=0;   
        String officelevel="",officelevel2="",officelevel1="";
        String circleQry="";
        int officeidd=0;
      	int offcid=0,unitiid=0;
         check=request.getParameter("unit_rendered");
         check1=request.getParameter("allid");
         System.out.println("accountingunit  "+accountingunit +" accountingoffice "+accountingoffice+" cmbassetcode "+cmbassetcode);
         try{
         	
         	//and c.acct_rendering_unit_id       =368
         	
         /*	String sqry="select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID="+AccUnitCode;
         	 PreparedStatement ps=connection.prepareStatement(sqry);
		           ResultSet rs=ps.executeQuery();
		           while(rs.next()){
		        	   officeidd=rs.getInt("ACCOUNTING_UNIT_OFFICE_ID");
		           }*/
         	//System.out.println("officeidd "+officeidd);
         	String selectQry="select office_level_id from FAS_NEW_VIEW  where office_id="+accountingoffice;
		           PreparedStatement ps1=connection.prepareStatement(selectQry);
		           ResultSet rs1=ps1.executeQuery();
		           while(rs1.next()){
		        	   officelevel=rs1.getString("office_level_id");
		           }
		          // System.out.println(" officelevel "+officelevel);
		           if(officelevel.equalsIgnoreCase("CL")){
		           circleQry=" AND c.ACCT_RENDERING_UNIT_ID       ="+accountingunit;
		           }
		           else if(officelevel.equalsIgnoreCase("RN")){
		           circleQry=" AND c.ACCT_RENDERING_UNIT_ID       ="+accountingunit;
		           }
		           else{
		           //System.out.println("else part ");
		          /* String findcir="select  CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID="+accountingoffice;
		           
		            PreparedStatement ps2=connection.prepareStatement(findcir);
		           ResultSet rs2=ps2.executeQuery();
		           while(rs2.next()){
		        	   offcid=rs2.getInt("CIRCLE_OFFICE_ID");  
		           } 
		           String findunitid="select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID ="+offcid;
		            PreparedStatement ps3=connection.prepareStatement(findunitid);
		           ResultSet rs3=ps3.executeQuery();
		           while(rs3.next()){
		        	   unitiid=rs3.getInt("ACCOUNTING_UNIT_ID");   
		        	   
		           } */
		        	   
		        	   String findunitid="select ACCT_RENDERING_UNIT_ID from FAS_ASSET_VAL_AC_RENDER_UNITS where ACCT_UNIT_ID_RENDERED_FOR ="+accountingunit;
			            PreparedStatement ps3=connection.prepareStatement(findunitid);
			           ResultSet rs3=ps3.executeQuery();
			           while(rs3.next()){
			        	   unitiid=rs3.getInt("ACCT_RENDERING_UNIT_ID");   
			        	   
			           } 
		        	   
		        	   
		           
		           ///finding circle id for unit.....
		            circleQry=" AND c.ACCT_RENDERING_UNIT_ID       ="+unitiid;
		            

 				    String selectQry2="select office_level_id from FAS_NEW_VIEW  where office_id="+accountingoffice;
			           PreparedStatement ps12=connection.prepareStatement(selectQry2);
			           ResultSet rs12=ps12.executeQuery();
			           while(rs12.next()){
			        	   officelevel1=rs12.getString("office_level_id");
			           }
			           System.out.println(" officelevel1 "+officelevel1);
			           if(officelevel1.equalsIgnoreCase("DN")){
			        	  
			        	   units=" and a.accounting_unit_office_id in (select office_id from FAS_NEW_VIEW  where division_office_id="+accountingoffice+")"  ;
			        	   
			           }else{
			        	 
			        	   units=" AND c.ACCT_UNIT_ID_RENDERED_FOR="+unitiid+" and a.accounting_unit_office_id="+accountingoffice;
			        	  // units="";
			           }
			         //  System.out.println("units  "+units);   
		            
		           }
		           
		          	           
         	}catch(Exception e){
         	System.out.println("error in split "+e);
         }
         
         	  if(check1.equalsIgnoreCase("All"))
      		{	
      			//units="";

      		}
      		else
      		{					
      			//units=" AND c.ACCT_UNIT_ID_RENDERED_FOR="+unit_rendered;
      			//System.out.println("check...in else "+units+check1);
      			String[] unitsoffice=check.split("-");
      	           //unit_rendered=Integer.parseInt(check);      
      	           unit_rendered=Integer.parseInt(unitsoffice[0]); 
      	           officeiddd=Integer.parseInt(unitsoffice[1]);
      	          // System.out.println("unit_rendered==="+unit_rendered+"  officeiddd--- "+officeiddd);
      	        
      	           String selectQry="select office_level_id from FAS_NEW_VIEW  where office_id="+officeiddd;
      	           PreparedStatement ps=connection.prepareStatement(selectQry);
      	           ResultSet rs=ps.executeQuery();
      	           while(rs.next()){
      	        	   officelevel2=rs.getString("office_level_id");
      	           }
      	          // System.out.println(" officelevel2 "+officelevel2);
      	           if(officelevel2.equalsIgnoreCase("DN")){
      	        	   //newQry=" and a.accounting_unit_office_id in (select office_id from com_mst_all_offices_view  where division_office_id="+officeiddd+")"  ;
      	        	   units=" and a.accounting_unit_office_id in (select office_id from FAS_NEW_VIEW  where division_office_id="+officeiddd+")"  ;
      	        	   
      	           }else{
      	        	  // newQry=" AND c.ACCT_UNIT_ID_RENDERED_FOR="+unit_rendered+" and a.accounting_unit_office_id="+officeiddd;
      	        	   units=" AND c.ACCT_UNIT_ID_RENDERED_FOR="+unit_rendered+" and a.accounting_unit_office_id="+officeiddd;
      	           }
      		}
             
             if(assetwise.equalsIgnoreCase("All"))
      		{	
      			assetsWise="";
      			//System.out.println("check..in if ."+units+check1);
      		}
      		else
      		{					
      			assetsWise="  AND asset_major_class_code ="+cmbassetcode;
      			//System.out.println("check...in else "+units+check1);
      		}
             
             
        
        reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/TDA/jasper/AA52OB_DATA_REPORT.jasper")); 
         // System.out.println("reportFile"+reportFile); 
          //unit_rendered=Integer.parseInt(check);   
        if (!reportFile.exists())
        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
        
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
        
      // System.out.println("jasperReport"+jasperReport);
     
       System.out.println(" units "+units );
       System.out.println(" circleQry "+circleQry );
       System.out.println(" assetsWise --> "+assetsWise );
       
       
        Map map=new HashMap();
        map.put("accountingunitid",accountingunit);
        map.put("accountofficeid",accountingoffice);
        map.put("cmbassetcode",cmbassetcode);
        map.put("cmbFinancialYear",cmbFinancialYear);        
        map.put("querp", units);
        map.put("circleQry",circleQry);	           
        map.put("queryAsset", assetsWise);
      
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);            
        if (rtype.equalsIgnoreCase("HTML"))   
        {
                   response.setContentType("text/html");
                    response.setHeader ("Content-Disposition", "attachment;filename=\"AA52OB_DATA_REPORT.html\"");
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
                    response.setHeader ("Content-Disposition", "attachment;filename=\"AA52OB_DATA_REPORT.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
        }
        else if (rtype.equalsIgnoreCase("EXCEL"))   
        {

                	response.setContentType("application/vnd.ms-excel");
                    response.setHeader ("Content-Disposition", "attachment;filename=\"AA52OB_DATA_REPORT.xls\"");
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
	                response.setHeader ("Content-Disposition", "attachment;filename=\"AA52OB_DATA_REPORT.txt\"");
	                     
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


}
