package Servlets.FAS.FAS1.Masters.servlets;

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
 * Servlet implementation class A52_Qty_Push_to_Num_Abstract
 */
public class A52_Qty_Push_to_Num_Abstract extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";      
    /**
     * @see HttpServlet#HttpServlet()
     */
    public A52_Qty_Push_to_Num_Abstract() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {


        Connection connection=null;
        Statement statement=null;
        ResultSet result=null;
        ResultSet result1=null,rs1=null,rss1=null,rss2=null;
        PreparedStatement pers=null;
   	    PreparedStatement ps=null,ps1=null;
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
        int unit_id = 0;
        int office_id = 0;
        int assetmajor=0;
        int assetminor=0;
	   	String financial_year = null;
	   	int asset_code = 0; 
	   	String PARTICULARS = "";
	   	
	   	
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
        System.out.println("Session id is    a52 push...:"+userid);
        
        
        response.setContentType("text/xml");
        PrintWriter pw=response.getWriter();    
        response.setHeader("Cache-Control","no-cache");
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
        try
        {
        	strCommand = request.getParameter("command");     
        	System.out.println("strCommand : a52 value edit " + strCommand);
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
        try
        {
        	unit_id = Integer.parseInt(request.getParameter("unit_id"));
        	System.out.println("accounting_unit_id : " + unit_id);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'accounting_unit_id' parameter ===> " + e);
        }        
        
     /*   try
        {
        	office_id = Integer.parseInt(request.getParameter("office_id"));
        	System.out.println("accounting_unit_office_id : " + office_id);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'accounting_unit_office_id' parameter ===> " + e);
        } */           
        try
        {
        	financial_year = request.getParameter("financial_year");
        	System.out.println("financial_year : " + financial_year);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'financial_year' parameter ===> " + e);
        }     
        
    
        try
        {
        	assetmajor = Integer.parseInt(request.getParameter("assetmajor"));
        	System.out.println("assetmajor : " + assetmajor);
        }
        catch(Exception e)
        { 
            System.out.println("IGNORABLE Exception getting 'assetmajor' parameter ===> " + e);
        }     
      
     
        if(strCommand.equals("GoGet"))
        { 
        	int count=0;
        	System.out.println("\n*************\nGo Get\n**************\n");
            xml="<response><command>GoGet</command>";
            try 
            {
             System.out.println("goget");
             
             String qry=" select  "+
             " asset_major_class_code,accounting_unit_id,unitname,asset_major_class_desc, "+
             " (open_bal_qty+reciepts_year_qty-issues_year_qty) as aval_qty, "+
             " (n_open_bal_qty+n_reciepts_year_qty-n_issues_year_qty) as aval_crt_qty, "+
             " (opening_bal_value+reciepts_yr_value-issues_yr_value) as aval_value, "+
             " (n_opening_bal_value+n_reciepts_yr_value-n_issues_yr_value) as aval_crt_value "+
             " from  "+
             " (SELECT asset_major_class_code,ACCOUNTING_UNIT_ID, "+
             " (SELECT cc.Accounting_Unit_Name "+
             "  FROM fas_mst_acct_units cc "+
             " WHERE CC.ACCOUNTING_UNIT_ID=A.ACCOUNTING_UNIT_ID "+
             " )AS unitname, "+
             " (SELECT bb.ASSET_MAJOR_CLASS_DESC "+
             "   FROM FAS_MST_ASSETS_CLASS bb "+
             "  WHERE BB.ASSET_MAJOR_CLASS_CODE=A.ASSET_MAJOR_CLASS_CODE "+
             "  )AS asset_MAJOR_CLASS_DESC, "+
             " decode(SUM(OPEN_BAL_QTY),null,0,SUM(OPEN_BAL_QTY))AS OPEN_BAL_QTY, "+
             " decode(SUM(OPENING_BAL_VALUE),null,0,SUM(OPENING_BAL_VALUE))AS OPENING_BAL_VALUE, "+
             " decode(SUM(RECIEPTS_YEAR_QTY),null,0,SUM(RECIEPTS_YEAR_QTY))AS RECIEPTS_YEAR_QTY, "+
             " decode(SUM(RECIEPTS_YR_VALUE),null,0,SUM(RECIEPTS_YR_VALUE))AS RECIEPTS_YR_VALUE, "+
             " decode(SUM(ISSUES_YEAR_QTY),null,0,SUM(ISSUES_YEAR_QTY))AS ISSUES_YEAR_QTY, "+
             " decode(sum(issues_yr_value),null,0,sum(issues_yr_value)) as issues_yr_value, "+

             " decode(sum(n_open_bal_qty),null,0,sum(n_open_bal_qty))as n_open_bal_qty,"+
             " decode(sum(n_opening_bal_value),null,0,sum(n_opening_bal_value))as n_opening_bal_value, "+
             " decode(sum(n_reciepts_year_qty),null,0,sum(n_reciepts_year_qty))as n_reciepts_year_qty, "+
             " decode(sum(n_reciepts_yr_value),null,0,sum(n_reciepts_yr_value))as n_reciepts_yr_value, "+
             " decode(sum(n_issues_year_qty),null,0,sum(n_issues_year_qty))as n_issues_year_qty, "+
             " decode(SUM(N_ISSUES_YR_VALUE),null,0,SUM(N_ISSUES_YR_VALUE)) AS N_ISSUES_YR_VALUE, "+

             " decode(SUM(DEP_PREV_YEAR),null,0,SUM(DEP_PREV_YEAR))AS DEP_PREV_YEAR, "+
             " decode(SUM(DEPRE_REC_AC),null,0,SUM(DEPRE_REC_AC))AS DEPRE_REC_AC, "+
             " decode(SUM(DEPRE_ALLOWED_YR),null,0,SUM(DEPRE_ALLOWED_YR))AS DEPRE_ALLOWED_YR, "+
             " decode(SUM(DEPRE_TR_AC),null,0,SUM(DEPRE_TR_AC))AS DEPRE_TR_AC, "+
             " decode(SUM(DEPRE_UPTO_DATE),null,0,SUM(DEPRE_UPTO_DATE))AS DEPRE_UPTO_DATE, "+
             " DECODE(SUM(NET_DEPRE_COST),NULL,0,SUM(NET_DEPRE_COST))AS NET_DEPRE_COST, "+
             " DECODE(SUM(APP_PRE_YR),NULL,0,SUM(APP_PRE_YR))AS APP_PRE_YR, "+
             " DECODE(SUM(APP_GRANT_RECIEVED),NULL,0,SUM(APP_GRANT_RECIEVED))AS APP_GRANT_RECIEVED, "+
             " DECODE(SUM(APPRO_DURING_YR),NULL,0,SUM(APPRO_DURING_YR))AS APPRO_DURING_YR, "+
             " DECODE(SUM(APP_GRANT_TR),NULL,0,SUM(APP_GRANT_TR))AS APP_GRANT_TR, "+
             " decode(SUM(APP_GRANT_UPTODATE),null,0,SUM(APP_GRANT_UPTODATE))AS APP_GRANT_UPTODATE "+
             " FROM FAS_A52_REGISTER a "+
             " where accounting_unit_id = "+ unit_id +
             " AND FINANCIAL_YEAR = '"+ financial_year +"'" +
             " group by asset_major_class_code, ACCOUNTING_UNIT_ID"+
             " order by ASSET_MAJOR_CLASS_CODE)";
             
             
             System.out.println(qry);
             result = statement.executeQuery(qry);
             
           try
             {  
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(result.next())
                 { 
                	 
                	 valExists = "Yes";
                	 xml += "<asset_major_class_code>" + result.getInt("asset_major_class_code") + "</asset_major_class_code>";
                	 xml=xml+"<accounting_unit_id>" + result.getInt("accounting_unit_id") + "</accounting_unit_id>";
                	 xml=xml+"<unitname>" + result.getString("unitname").trim() + "</unitname>";
                	 xml=xml+"<asset_major_class_desc><![CDATA[" + result.getString("asset_major_class_desc").trim() + "]]></asset_major_class_desc>";
                     xml=xml+"<aval_qty>" + result.getInt("aval_qty") + "</aval_qty>";
                     xml=xml+"<aval_value>" + result.getInt("aval_value") + "</aval_value>";
                     xml=xml+"<aval_crt_qty>" + result.getInt("aval_crt_qty") + "</aval_crt_qty>";
                     xml=xml+"<aval_crt_value>" + result.getInt("aval_crt_value") +"</aval_crt_value>";
                	 count++;
                 }

                 xml =xml+ "<exists>"+valExists+"</exists>";
                 xml =xml+ "<count>"+count+"</count>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting values from DB - Go GET: " + e);
             }
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in Get ---> "+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        } 
       
        else if(strCommand.equals("loadMajor"))
        { 
        	//System.out.println("\n*************\nloadMajor\n**************\n");
            xml="<response><command>loadMajor</command>";
            try 
            {
             result = statement.executeQuery("select ASSET_MAJOR_CLASS_CODE,ASSET_MAJOR_CLASS_DESC from FAS_MST_ASSETS_CLASS order by ASSET_MAJOR_CLASS_CODE");
             try
             {
            	 xml=xml+"<flag>success</flag>";
            	 String valExists = "No";
                 while(result.next())
                 { 
                	 valExists = "Yes";
                	 xml += "<ASSET_MAJOR_CLASS_CODE>" + result.getInt("ASSET_MAJOR_CLASS_CODE") + "</ASSET_MAJOR_CLASS_CODE>";
                	 xml += "<ASSET_MAJOR_CLASS_DESC><![CDATA[" + result.getString("ASSET_MAJOR_CLASS_DESC") + "]]></ASSET_MAJOR_CLASS_DESC>";
                 }

                 xml += "<exists>"+valExists+"</exists>";
             }catch(Exception e)
             {
            	 System.out.println("Exception in getting values from ASSET_MAJOR_CLASS_CODE: " + e);
             }
             
             result.close();
             //response.setHeader("cache-control","no-cache");
             
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in Get"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }  
      
        else if (strCommand.equalsIgnoreCase("checkStatus")) {
        	int c1=0,c2=0,c3=0;
            xml = "<response><command>checkStatus</command>";
            System.out.println("checkStatus in a52 push to num ");
            try {
            	
            	/*String[] divyear=cmbFinancialYear.split("-");
            	String newyear=(Integer.parseInt(divyear[0])-1)+"-"+(Integer.parseInt(divyear[1])-1);
            	System.out.println("new year  "+newyear);*/
            	int cricleID=0;
            	String selectCircleID="select CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID=?";
            	  ps =  connection.prepareStatement(selectCircleID);
                  ps.setInt(1, office_id);
                  rss1=ps.executeQuery();
                  while(rss1.next()){
                	  cricleID=rss1.getInt("CIRCLE_OFFICE_ID");	  
                  }
                //  System.out.println("circle Id "+cricleID);
            	String searchquery="select A52_STATUS_VAL from FAS_A52_AA52_VERIFY_STATUS where ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR='"+financial_year+"' ";//and finanical_year='"+cmbFinancialYear+"' 
                ps1 =  connection.prepareStatement(searchquery);
                ps1.setInt(1, cricleID);
               // ps1.setString(2, newyear);
                rss2=ps1.executeQuery();
               // System.out.println(" searchquery  "+searchquery);
                if(rss2.next()){
    				xml = xml + "<flag>freezeCricle</flag>";
    				System.out.println("freezeCricle   ");
    				
                }else{
                	xml = xml + "<flag>notfreezeCricle</flag>";
                } 

            } catch (SQLException e) {
                e.printStackTrace();
                xml = xml + "<flag>failure</flag>";
            }
            xml=xml+"</response>";
           
        }
        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {



		
		Connection connection=null;
      
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
        JasperDesign jasperDesign = null;
        File reportFile = null;
        try {
            System.out.println("*****calling A52 Push Num Abstract servlet*****");
            int AccUnitCode =
                Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
           // int AccOfficeCode =
             //   Integer.parseInt(request.getParameter("cmbOffice_code"));
            //int asset_majclass_code = Integer.parseInt(request.getParameter("cmbasset"));
            String finyear =request.getParameter("cmbFinancialYear");
           // String rtype = request.getParameter("txtoption");
            String rtype = "PDF";
            System.out.println("cmbAcc_UnitCode******"+AccUnitCode+"finyear*******"+finyear);
           
           reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Masters/jasper/A52_Qty_Push_Num_Abs.jasper"));
                  
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());


            Map map = new HashMap();

            map.put("accunitid", AccUnitCode);
            map.put("financialyear", finyear);
            //map.put("assetmajclass", asset_majclass_code);
         
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);


            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"A52RegisterOBEntryAbs.html\"");
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
                                   "attachment;filename=\"A52RegisterOBEntryAbs.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                System.out.println("testing***"+jasperPrint);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"A52RegisterOBEntryAbs.xls\"");
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
                                   "attachment;filename=\"A52RegisterOBEntryAbs.txt\"");

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
        
        /*
          Statement statement=null;
        ResultSet result=null,rss1=null,rss3=null;
        PreparedStatement ps1=null,ps3=null;
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
              
           ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim();

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

        
        response.setContentType(CONTENT_TYPE);
        String strCommand = ""; 
        int unit_id = 0;
        int office_id = 0;
        int assetmajor=0;
	   	String financial_year = null;

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
        	System.out.println("strCommand : post " + strCommand);
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
        int up=0;
        try{
       
           if(strCommand.equalsIgnoreCase("updateTotally")){
        	 
        	   connection.setAutoCommit(false);
        	   try
               {
               	unit_id = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
               }
               catch(Exception e)
               { 
                   System.out.println("IGNORABLE Exception getting 'accounting_unit_id' parameter ===> " + e);
               }        
               
               try
               {
               	office_id = Integer.parseInt(request.getParameter("cmbOffice_code"));
               }
               catch(Exception e)
               { 
                   System.out.println("IGNORABLE Exception getting 'accounting_unit_office_id' parameter ===> " + e);
               } 
               try
               {
            	   assetmajor = Integer.parseInt(request.getParameter("cmbmajorasset"));
               }
               catch(Exception e)
               { 
                   System.out.println("IGNORABLE Exception getting 'major' parameter ===> " + e);
               } 
               
               try
               {
            	   financial_year =request.getParameter("cmbFinancialYear");
               }
               catch(Exception e)
               { 
                   System.out.println("IGNORABLE Exception getting 'financial_year' parameter ===> " + e);
               } 
                
                             
        	String[] sno=request.getParameterValues("sno");
        	String[] asset_major_class_code=request.getParameterValues("asset_major_class_code");
        	String[] accounting_unit_id=request.getParameterValues("accounting_unit_id");
        	String[] unitname=request.getParameterValues("unitname");
        	String[] asset_major_class_desc=request.getParameterValues("asset_major_class_desc");
        	String[] aval_qty=request.getParameterValues("aval_qty");
        	String[] aval_value=request.getParameterValues("aval_value");
        	String[] aval_crt_qty=request.getParameterValues("aval_crt_qty");
        	String[] aval_crt_value=request.getParameterValues("aval_crt_value");
        	
        	
        	String[] N_OPENING_BAL_VALUE=request.getParameterValues("N_OPENING_BAL_VALUE");
        	String[] N_RECIEPTS_YR_VALUE=request.getParameterValues("N_RECIEPTS_YR_VALUE");
        	String[] N_ISSUES_YR_VALUE=request.getParameterValues("N_ISSUES_YR_VALUE");
        	String[] OPEN_BAL_QTY=request.getParameterValues("OPEN_BAL_QTY");
        	String[] RECIEPTS_YEAR_QTY=request.getParameterValues("RECIEPTS_YEAR_QTY");
        	String[] ISSUES_YEAR_QTY=request.getParameterValues("ISSUES_YEAR_QTY");
        	//String[] officeN=request.getParameterValues("offName");
        	
        	java.sql.Date DATE_OF_ENTRY1=new java.sql.Date(ts.getTime());
		//	System.out.println("DATE_OF_ENTRY --->  "+DATE_OF_ENTRY1);
        	
        	int ss=asset_major_class_code.length;
        	int cc=0;
            System.out.println("assetcode.length =="+ss);
        	for(int ii=0;ii<ss;ii++){
        		//System.out.println("------------------"+ii+"-----------------");
        		
        		String officelevel="";
            	String units="";
                           
                int cricleID=0;
                int circleoff=0;
                String selectQry="select office_level_id from com_mst_all_offices_view  where office_id="+office_id;
		           PreparedStatement ps2=connection.prepareStatement(selectQry);
		           ResultSet rs=ps2.executeQuery();
		           while(rs.next()){
		        	   officelevel=rs.getString("office_level_id");
		           }
		         //  System.out.println(" officelevel "+officelevel);
		           if(officelevel.equalsIgnoreCase("RN")){
		        	   cricleID=unit_id;
		        	  }else {
		        	   String selectCircleID="select CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID=?";
		            	  ps1 =  connection.prepareStatement(selectCircleID);
		                  ps1.setInt(1, office_id);
		                  rss1=ps1.executeQuery();
		                  while(rss1.next()){
		                	  circleoff=rss1.getInt("CIRCLE_OFFICE_ID");	  
		                  } 
		                  String selectCircleID1="select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?";
		            	  ps3 =  connection.prepareStatement(selectCircleID1);
		                  ps3.setInt(1, circleoff);
		                  rss3=ps3.executeQuery();
		                  while(rss3.next()){
		                	  cricleID=rss3.getInt("ACCOUNTING_UNIT_ID");	  
		                  }
		                 
		           } 
                  System.out.println("cricleID  "+cricleID);
                  
                  
                  
                  
        		  int assetno=Integer.parseInt(assetcode[ii]);
                  int aval_qty1=Integer.parseInt(aval_qty[ii]);
                  int crt_aval_qty1=Integer.parseInt(crt_aval_qty[ii]);
                  String PARTICULARS2=PARTICULARS1[ii];
                  int minorcode1=Integer.parseInt(minorcode[ii]);
                  int offco=Integer.parseInt(offcode[ii]);
                  String ooffNa=offName[ii];   		                	
        		            		   try 
        		                       {        		            			   
        		   String sqlinsert="insert into FAS_ASSETS_NUM_OB (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,DATE_OF_ENTRY,ASSET_MAJOR_CLASS_CODE,ASSET_CODE,QTY_AVL_ASON_DATE,PHYSICAL_LOCATION_CODE,STATUS,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE,FINANCIAL_YEAR,QTY_AVAILABLE,CORRECTQTY) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)";        		            			   
    		   	   PreparedStatement ps = connection.prepareStatement(sqlinsert); 
                     ps.setInt(1,cricleID);
                     ps.setInt(2,offco);
                     ps.setDate(3,DATE_OF_ENTRY1);
                     ps.setInt(4,assetmajor);
                     ps.setInt(5,assetno);
                     ps.setInt(6,aval_qty1);
                     ps.setString(7,ooffNa);
                     ps.setString(8,"Y");
                     ps.setString(9,PARTICULARS2);
                     ps.setString(10,userid);
                     ps.setTimestamp(11,ts);
                     ps.setString(12,financial_year);  
                     ps.setInt(13,aval_qty1);
                     ps.setInt(14,crt_aval_qty1);
                     up=ps.executeUpdate();
                     
                     System.out.println("up ..."+up);
                     cc++;
                     
 				} catch (Exception e) {
 					System.out.println("exception......in update calll send message "+e);
					
					connection.rollback();
                     sendMessage(response,"Failed to update Data","ok");  
				}
 				//System.out.println("count of records updated  "+cc);
 				//System.out.println(" cc "+cc+"  ssss "+ss);
				         
                }
        	if(cc==ss)
			{
				connection.commit();
				sendMessage(response,"Records inserted successfully ","ok");
			}
			else
			{
				//System.out.println(" inside    else  ");
				connection.rollback();	
				sendMessage(response,"Record Not Insert  ","ok");
			}
  
        	}
        	
        }catch(Exception ss){
        	System.out.println("SQL exception   "+ss);
        	
        }*/
	}
	/*private void sendMessage(HttpServletResponse response,String msg,String bType)
	 {
	 	try
	 	{
	 		String url="org/Library/jsps/MessengerOkBack.jsp?message=" + msg + "&button=" + bType;
	 		response.sendRedirect(url);
	 	}
	 	catch(Exception e)
	 	{
	 		System.out.println("error in messenger"+e);
	 	}
 }*/

}
