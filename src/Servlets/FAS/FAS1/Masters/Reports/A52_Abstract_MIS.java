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
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;
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
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class A52_Abstract_MIS
 */
public class A52_Abstract_MIS extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE ="text/html; charset=windows-1252";
    Connection connection = null;  
    /**
     * @see HttpServlet#HttpServlet()
     */
    public A52_Abstract_MIS() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
        Connection connection=null;
        Statement statement=null;
        ResultSet result=null;
        ResultSet result1=null;
        PreparedStatement pers=null;
   	 PreparedStatement ps=null;
       // int up=0;
        try
        {
    	   ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
           String ConnectionString="";

           String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
           String strdsn=rs.getString("Config.DSN");
           String strhostname=rs.getString("Config.HOST_NAME");
           String strportno=rs.getString("Config.PORT_NUMBER");
           String strsid=rs.getString("Config.SID");
           String strdbusername=rs.getString("Config.USER_NAME");
           String strdbpassword=rs.getString("Config.PASSWORD");
              
           ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

           Class.forName(strDriver.trim());
           connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
           try
           {
        	   statement=connection.createStatement();
               connection.clearWarnings();
           }
           catch(SQLException e)
           {
               System.out.println("Exception in creating statement:"+e);
           }
        }
        catch(Exception e)
        {
           System.out.println("Exception in openeing connection:"+e);
        }
        response.setContentType(CONTENT_TYPE);
        String strCommand = ""; 
        String xml="";

	   
	   	String financial_year = null;

	   	String remarks = "";
	   	
	   	
        HttpSession session=request.getSession(false);
        try
        {
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
        
        
        String userid=(String)session.getAttribute("UserId");
        System.out.println("Session id is:"+userid);
        
        
        response.setContentType("text/xml");
        PrintWriter pw=response.getWriter();    
        response.setHeader("Cache-Control","no-cache");
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
        try
        {
        	strCommand = request.getParameter("command");     
        	System.out.println("strCommand : " + strCommand);
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
     
        
        try
        {
        	financial_year = request.getParameter("financial_year");
        	System.out.println("financial_year : " + financial_year);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'financial_year' parameter ===> " + e);
        }     
        
         
      
     
        if(strCommand.equals("GoGet"))
        { 
        	int count=0;
        	System.out.println("\n*************\nGo Get\n**************\n");
            xml="<response><command>GoGet</command>";
            try 
            {
            
                System.out.println("goget");
               String selectQry="select asset_major_class_desc,asset_major_class_code,closing_qty,closing_value,   "+
               " bookvalue,depre_allowed_yr,(closing_value-depre_allowed_yr)as balance from   "+
               " (select  asset_major_class_desc,  asset_major_class_code,  "+
               " (n_open_bal_qty+n_reciepts_year_qty-n_issues_year_qty)as closing_qty,   "+
               " (n_opening_bal_value+n_reciepts_yr_value-n_issues_yr_value)as closing_value,   "+
               " net_depre_cost as bookvalue,depre_allowed_yr   "+
               " from   "+
               " (SELECT asset_major_class_code,   "+
               " (SELECT bb.ASSET_MAJOR_CLASS_DESC   "+
               "  FROM FAS_MST_ASSETS_CLASS bb   "+
               " WHERE BB.ASSET_MAJOR_CLASS_CODE=A.ASSET_MAJOR_CLASS_CODE   "+
               " )AS asset_MAJOR_CLASS_DESC,   "+
               " decode(SUM(OPEN_BAL_QTY),null,0,SUM(OPEN_BAL_QTY))AS OPEN_BAL_QTY,   "+
               "  decode(SUM(OPENING_BAL_VALUE),null,0,SUM(OPENING_BAL_VALUE))AS OPENING_BAL_VALUE,   "+
               " decode(SUM(RECIEPTS_YEAR_QTY),null,0,SUM(RECIEPTS_YEAR_QTY))AS RECIEPTS_YEAR_QTY,   "+
               "  decode(SUM(RECIEPTS_YR_VALUE),null,0,SUM(RECIEPTS_YR_VALUE))AS RECIEPTS_YR_VALUE,   "+
               " decode(SUM(ISSUES_YEAR_QTY),null,0,SUM(ISSUES_YEAR_QTY))AS ISSUES_YEAR_QTY,   "+
               " decode(sum(issues_yr_value),null,0,sum(issues_yr_value)) as issues_yr_value,   "+
               " decode(sum(n_open_bal_qty),null,0,sum(n_open_bal_qty))as n_open_bal_qty,   "+
               " decode(sum(n_opening_bal_value),null,0,sum(n_opening_bal_value))as n_opening_bal_value,   "+
               " decode(sum(n_reciepts_year_qty),null,0,sum(n_reciepts_year_qty))as n_reciepts_year_qty,   "+
               " decode(sum(n_reciepts_yr_value),null,0,sum(n_reciepts_yr_value))as n_reciepts_yr_value,   "+
               " decode(sum(n_issues_year_qty),null,0,sum(n_issues_year_qty))as n_issues_year_qty,   "+
               " decode(SUM(N_ISSUES_YR_VALUE),null,0,SUM(N_ISSUES_YR_VALUE)) AS N_ISSUES_YR_VALUE,   "+
               " decode(SUM(DEP_PREV_YEAR),null,0,SUM(DEP_PREV_YEAR))AS DEP_PREV_YEAR,   "+
               "  decode(SUM(DEPRE_REC_AC),null,0,SUM(DEPRE_REC_AC))AS DEPRE_REC_AC,   "+
               " decode(SUM(DEPRE_ALLOWED_YR),null,0,SUM(DEPRE_ALLOWED_YR))AS DEPRE_ALLOWED_YR,  "+
               " decode(SUM(DEPRE_TR_AC),null,0,SUM(DEPRE_TR_AC))AS DEPRE_TR_AC,  "+
               "  decode(SUM(DEPRE_UPTO_DATE),null,0,SUM(DEPRE_UPTO_DATE))AS DEPRE_UPTO_DATE,   "+
               " DECODE(SUM(NET_DEPRE_COST),NULL,0,SUM(NET_DEPRE_COST))AS NET_DEPRE_COST,   "+
               " DECODE(SUM(APP_PRE_YR),NULL,0,SUM(APP_PRE_YR))AS APP_PRE_YR,   "+
               " DECODE(SUM(APP_GRANT_RECIEVED),NULL,0,SUM(APP_GRANT_RECIEVED))AS APP_GRANT_RECIEVED,  "+ 
               " DECODE(SUM(APPRO_DURING_YR),NULL,0,SUM(APPRO_DURING_YR))AS APPRO_DURING_YR,   "+
               " DECODE(SUM(APP_GRANT_TR),NULL,0,SUM(APP_GRANT_TR))AS APP_GRANT_TR,   "+
               " decode(SUM(APP_GRANT_UPTODATE),null,0,SUM(APP_GRANT_UPTODATE))AS APP_GRANT_UPTODATE  "+ 
               " from fas_a52_register a   "+
               " WHERE FINANCIAL_YEAR = '"+financial_year +"' "+
               " group by asset_major_class_code  "+
               " order by ASSET_MAJOR_CLASS_CODE)) ";
             /* String selectQry=" select  asset_major_class_desc,  "+
" (n_open_bal_qty+n_reciepts_year_qty-n_issues_year_qty)as closing_qty, "+
"  (n_opening_bal_value+n_reciepts_yr_value-n_issues_yr_value)as closing_value, "+
" NET_DEPRE_COST as bookvalue,DEPRE_ALLOWED_YR "+
" from "+

" (SELECT asset_major_class_code, "+
" (SELECT bb.ASSET_MAJOR_CLASS_DESC "+
"  FROM FAS_MST_ASSETS_CLASS bb "+
"  WHERE BB.ASSET_MAJOR_CLASS_CODE=A.ASSET_MAJOR_CLASS_CODE "+
"  )AS asset_MAJOR_CLASS_DESC, "+
" decode(SUM(OPEN_BAL_QTY),null,0,SUM(OPEN_BAL_QTY))AS OPEN_BAL_QTY, "+
" decode(SUM(OPENING_BAL_VALUE),null,0,SUM(OPENING_BAL_VALUE))AS OPENING_BAL_VALUE, "+
" decode(SUM(RECIEPTS_YEAR_QTY),null,0,SUM(RECIEPTS_YEAR_QTY))AS RECIEPTS_YEAR_QTY, "+
" decode(SUM(RECIEPTS_YR_VALUE),null,0,SUM(RECIEPTS_YR_VALUE))AS RECIEPTS_YR_VALUE, "+
" decode(SUM(ISSUES_YEAR_QTY),null,0,SUM(ISSUES_YEAR_QTY))AS ISSUES_YEAR_QTY, "+
" decode(sum(issues_yr_value),null,0,sum(issues_yr_value)) as issues_yr_value, "+
" decode(sum(n_open_bal_qty),null,0,sum(n_open_bal_qty))as n_open_bal_qty, "+
" decode(sum(n_opening_bal_value),null,0,sum(n_opening_bal_value))as n_opening_bal_value, "+
" decode(sum(n_reciepts_year_qty),null,0,sum(n_reciepts_year_qty))as n_reciepts_year_qty, "+
" decode(sum(n_reciepts_yr_value),null,0,sum(n_reciepts_yr_value))as n_reciepts_yr_value, "+
" decode(sum(n_issues_year_qty),null,0,sum(n_issues_year_qty))as n_issues_year_qty, "+
" decode(SUM(N_ISSUES_YR_VALUE),null,0,SUM(N_ISSUES_YR_VALUE)) AS N_ISSUES_YR_VALUE, "+
" decode(SUM(DEP_PREV_YEAR),null,0,SUM(DEP_PREV_YEAR))AS DEP_PREV_YEAR, "+
" decode(SUM(DEPRE_REC_AC),null,0,SUM(DEPRE_REC_AC))AS DEPRE_REC_AC, "+
" decode(SUM(DEPRE_ALLOWED_YR),null,0,SUM(DEPRE_ALLOWED_YR))AS DEPRE_ALLOWED_YR,"+
" decode(SUM(DEPRE_TR_AC),null,0,SUM(DEPRE_TR_AC))AS DEPRE_TR_AC,"+
" decode(SUM(DEPRE_UPTO_DATE),null,0,SUM(DEPRE_UPTO_DATE))AS DEPRE_UPTO_DATE, "+
" DECODE(SUM(NET_DEPRE_COST),NULL,0,SUM(NET_DEPRE_COST))AS NET_DEPRE_COST, "+
" DECODE(SUM(APP_PRE_YR),NULL,0,SUM(APP_PRE_YR))AS APP_PRE_YR, "+
" DECODE(SUM(APP_GRANT_RECIEVED),NULL,0,SUM(APP_GRANT_RECIEVED))AS APP_GRANT_RECIEVED, "+
" DECODE(SUM(APPRO_DURING_YR),NULL,0,SUM(APPRO_DURING_YR))AS APPRO_DURING_YR, "+
" DECODE(SUM(APP_GRANT_TR),NULL,0,SUM(APP_GRANT_TR))AS APP_GRANT_TR, "+
" decode(SUM(APP_GRANT_UPTODATE),null,0,SUM(APP_GRANT_UPTODATE))AS APP_GRANT_UPTODATE "+
" from fas_a52_register a "+
" WHERE FINANCIAL_YEAR = '"+financial_year +"' "+
" group by asset_major_class_code"+
" order by ASSET_MAJOR_CLASS_CODE)";*/
              System.out.println(selectQry); 	   
		        	    result = statement.executeQuery(selectQry);

           try
             {
        	   System.out.println("inside try");
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(result.next())
                 { 
                	 valExists = "Yes";
                	 xml += "<asset_major_class_desc>" + result.getString("asset_major_class_desc").trim()+ "</asset_major_class_desc>";
                	 xml=xml+"<closing_qty>" + result.getInt("closing_qty") + "</closing_qty>";
                	 xml=xml+"<closing_value>" + result.getInt("closing_value") + "</closing_value>";
                	 xml += "<bookvalue>" + result.getInt("bookvalue") + "</bookvalue>"; 
                     xml=xml+"<DEPRE_ALLOWED_YR>" + result.getInt("DEPRE_ALLOWED_YR") + "</DEPRE_ALLOWED_YR>";
                     xml=xml+"<asset_major_class_code>" + result.getInt("asset_major_class_code") + "</asset_major_class_code>";
                	 xml += "<balance>" + result.getInt("balance") + "</balance>"; 
                	 xml += "<financial_year>" + financial_year + "</financial_year>"; 
                	 count++;
                 }

                 xml =xml+ "<exists>"+valExists+"</exists>";
                 xml =xml+ "<count>"+count+"</count>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting values from DB - Go GET: " + e);
             }
             
            // result.close();
             //response.setHeader("cache-control","no-cache");
             
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in Get"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
            System.out.println("xml is : " + xml);
            pw.write(xml);
            pw.flush();
            pw.close();
        } 
       
       
       
	}

	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

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


	            ResourceBundle rs =ResourceBundle.getBundle("Servlets.Security.servlets.Config");
	            String ConnectionString = "";

	            String strDriver = rs.getString("Config.DATA_BASE_DRIVER");
	            String strdsn = rs.getString("Config.DSN");
	            String strhostname = rs.getString("Config.HOST_NAME");
	            String strportno = rs.getString("Config.PORT_NUMBER");
	            String strsid = rs.getString("Config.SID");
	            String strdbusername = rs.getString("Config.USER_NAME");
	            String strdbpassword = rs.getString("Config.PASSWORD");

	            ConnectionString =
	                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
	                    ":" + strsid.trim();

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

			

			Servlets.Security.classes.UserProfile empProfile = (Servlets.Security.classes.UserProfile) session
					.getAttribute("UserProfile");
			int empid = empProfile.getEmployeeId();
			String empName = empProfile.getEmployeeName();
			long l = System.currentTimeMillis();
			Timestamp ts = new Timestamp(l);
			JasperDesign jasperDesign = null;
	        File reportFile = null;
	        try {
	     
	            int AccUnitCode=0;
		            int asset_majclass_code=0 ;
		            String finyear="";
		            String rtype="" ;
		          String units="",assetsWise="";
		          String officelevel="";
		          String circleQry="";
		          int officeidd=0;
	            	int offcid=0,unitiid=0;
	            	String cmd="";
	            	int uniteidd=0;
	            	 int AccOfficeCode=0;
	            try{  	
	           cmd=request.getParameter("command");
	            AccUnitCode =Integer.parseInt(request.getParameter("unitid"));
	           AccOfficeCode =Integer.parseInt(request.getParameter("officeid"));
	            asset_majclass_code = Integer.parseInt(request.getParameter("assetcode"));
	            finyear =request.getParameter("finyr");
	           rtype = "PDF";
	 System.out.println("AccUnitCode "+AccUnitCode+" asset_majclass_code "+asset_majclass_code+" finyear "+finyear+" rtype "+rtype);
	              }catch(Exception e){
	            	System.out.println("input get from jsp---"+e);
	            	
	            }
	              if(cmd.equalsIgnoreCase("ReportDetail")){
	            	 
	              try{
	
	            	  String sqry11="select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID="+AccOfficeCode;
		            	 PreparedStatement ps11=connection.prepareStatement(sqry11);
				           ResultSet rs11=ps11.executeQuery();
				           while(rs11.next()){
				        	   uniteidd=rs11.getInt("ACCOUNTING_UNIT_ID");
				           }
	            	            	
	            	String sqry="select ACCOUNTING_UNIT_OFFICE_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID="+uniteidd;
	            	 PreparedStatement ps=connection.prepareStatement(sqry);
			           ResultSet rs=ps.executeQuery();
			           while(rs.next()){
			        	   officeidd=rs.getInt("ACCOUNTING_UNIT_OFFICE_ID");
			           }
			           if(officeidd==0){
			        	   officeidd=AccOfficeCode;
			           }
	            	System.out.println("officeidd "+officeidd);
	            	String selectQry="select office_level_id from com_mst_all_offices_view  where office_id="+officeidd;
			           PreparedStatement ps1=connection.prepareStatement(selectQry);
			           ResultSet rs1=ps1.executeQuery();
			           while(rs1.next()){
			        	   officelevel=rs1.getString("office_level_id");
			           }
			           System.out.println(" officelevel "+officelevel);
			           /*if(officelevel.equalsIgnoreCase("CL")){
			           circleQry=" AND c.ACCT_RENDERING_UNIT_ID       ="+AccUnitCode;
			           }else if(officelevel.equalsIgnoreCase("RN")){
			           circleQry=" AND c.ACCT_RENDERING_UNIT_ID       ="+AccUnitCode;
			           }else if(officelevel.equalsIgnoreCase("HO")){
			           circleQry=" AND c.ACCT_RENDERING_UNIT_ID       ="+AccUnitCode;
			           }
			           else{
			           System.out.println("else part ");
			           String findcir="select  CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID="+officeidd;
			           
			            PreparedStatement ps2=connection.prepareStatement(findcir);
			           ResultSet rs2=ps2.executeQuery();
			           while(rs2.next()){
			        	   offcid=rs2.getInt("CIRCLE_OFFICE_ID");  
			           } 
			           System.out.println("offcid  "+offcid);
			           String findunitid="select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID ="+offcid;
			            PreparedStatement ps3=connection.prepareStatement(findunitid);
			           ResultSet rs3=ps3.executeQuery();
			           while(rs3.next()){
			        	   unitiid=rs3.getInt("ACCOUNTING_UNIT_ID");   
			        	   
			           } 
			           System.out.println("unitiid  "+unitiid);
			           
			                  circleQry=" AND c.ACCT_RENDERING_UNIT_ID       ="+unitiid;
			                  //units=" AND c.ACCT_UNIT_ID_RENDERED_FOR="+unitiid+" and a.accounting_unit_office_id="+officeidd;
//here unit means....in circle clicking all units display under circle...
			                 // units=" and a.accounting_unit_office_id="+officeidd;
			            
			           }*/
			           circleQry=" AND c.ACCT_RENDERING_UNIT_ID       ="+AccUnitCode;
			           units=" and a.accounting_unit_office_id="+officeidd;
			          
		            System.out.println("uniteidd "+uniteidd+"cmbAcc_UnitCode**"+AccUnitCode+"**asset_majclass_code"+asset_majclass_code+"finyear* "+finyear);
	            	}catch(Exception e){
	            	System.out.println("error in split "+e);
	            }
					
				   //units=" AND c.ACCT_UNIT_ID_RENDERED_FOR="+unit_rendered+" and a.accounting_unit_office_id="+officeiddd;
	            	assetsWise="  AND asset_major_class_code ="+asset_majclass_code;      
					
					
					   reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Masters/jasper/A52_Abstract_MIS_Detail.jasper"));
	          
	            if (!reportFile.exists())
	                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
	            JasperReport jasperReport =
	                (JasperReport)JRLoader.loadObject(reportFile.getPath());
	           // input get from

	            Map map = new HashMap();
	
				System.out.println("units=="+units);
				System.out.println("assetsWise=="+assetsWise);
				 System.out.println("circleQry  "+circleQry);
				 
				 
	            map.put("financialyear", finyear);
	            map.put("accunitid", uniteidd);
	            map.put("assetmajclass", asset_majclass_code);
	            map.put("querp", units);
	            map.put("circleQry",circleQry);	           
	           map.put("queryAsset", assetsWise);
	         
	            JasperPrint jasperPrint =
	                JasperFillManager.fillReport(jasperReport, map, connection);


	            if (rtype.equalsIgnoreCase("HTML")) {
	                response.setContentType("text/html");
	                response.setHeader("Content-Disposition",
	                                   "attachment;filename=\"A52Abstrat_Mis.html\"");
	                PrintWriter out = response.getWriter();
	                JRHtmlExporter exporter = new JRHtmlExporter();
	                exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
	                                      false);
	                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
	                                      jasperPrint);
	                exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
	                exporter.exportReport();
	                out.flush();
	                out.close();
	            } else if (rtype.equalsIgnoreCase("PDF")) {
	            	System.out.println("the option chosen is :::::"+rtype);
	                byte buf[] =
	                    JasperExportManager.exportReportToPdf(jasperPrint);
	                response.setContentType("application/pdf");
	                response.setContentLength(buf.length);
	                response.setHeader("Content-Disposition",
	                                   "attachment;filename=\"A52Abstrat_Mis.pdf\"");
	                OutputStream out = response.getOutputStream();
	                out.write(buf, 0, buf.length);
	                System.out.println("testing***"+jasperPrint);
	                out.close();
	            } else if (rtype.equalsIgnoreCase("EXCEL")) {

	                response.setContentType("application/vnd.ms-excel");
	                response.setHeader("Content-Disposition",
	                                   "attachment;filename=\"A52Abstrat_Mis.xls\"");
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
	                                   "attachment;filename=\"A52Abstrat_Mis.txt\"");

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
	              }//----command end 
	              
	              
	        } catch (Exception ex) {
	            String connectMsg =
	                "Could not create the report " + ex.getMessage() + " " +
	                ex.getLocalizedMessage();
	            System.out.println(connectMsg);
	        }
	     
	}

}
