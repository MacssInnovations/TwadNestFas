package Servlets.FAS.FAS1.MIS.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

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
 * Servlet implementation class Annual_acc_Report
 */
public class fas_RegWise_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
     private static final String CONTENT_TYPE = 
            "text/html; charset=windows-1252";
        Connection connection = null;
    public fas_RegWise_Report() {
        super();
        // TODO Auto-generated constructor stub
    }

    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {

		  
	    //	System.out.println("Servlet Page ... ");
	    	CallableStatement stmt=null; 
	    	Connection connection=null;
	    	PreparedStatement ps=null;
	    	ResultSet rset=null;
	    	PrintWriter out=response.getWriter();
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
	        ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection	        Class.forName(strDriver.trim());
	        connection =DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
	        } catch (Exception ex) {
	        String connectMsg ="Could not create the connection" + ex.getMessage() + " " + 
	        ex.getLocalizedMessage();
	        //System.out.println(connectMsg);
	        }
	       String command=request.getParameter("command");
	       int from_year=0;
	       int To_year=0;
	       int from_Month=0;
	       int To_Month=0;
	       String fin_year="",Type="",qry="",xml="";
	       Type=request.getParameter("Type");
	       if(command.equalsIgnoreCase("loadCmb"))
	       {
	    	   xml="<response><command>"+command+"</command>";
	    	   fin_year=request.getParameter("Fin_year");
	    	 //  System.out.println(fin_year+">>"+Type);
	    	//  xml+="<type>"+Type+"</type>";
	    	   if(Type.equalsIgnoreCase("TYL") || Type.equalsIgnoreCase("FYL")|| Type.equalsIgnoreCase("ConFYL"))
	    	   {
	    		   qry=" select distinct cmonth,CASHBOOK_YEAR, "+
	                   " case      when cashbook_month=4   "+
	    		       " then 'Apr '       ||cashbook_year     when cashbook_month=5     then 'May '       ||cashbook_year   "+
	    		       " when cashbook_month=6     then 'Jun '       ||cashbook_year     when cashbook_month=7     then 'Jul '       ||cashbook_year   "+
	    		       " when cashbook_month=8     then 'Aug '       ||cashbook_year     when cashbook_month=9     then 'Sep '       ||cashbook_year   "+
	    		       " when cashbook_month=10     then 'Oct '       ||cashbook_year     when cashbook_month=11     then 'Nov '       ||cashbook_year   "+
	    		       " when cashbook_month=12     then 'Dec '       ||cashbook_year     when cashbook_month=1     then 'Jan '       ||cashbook_year   "+
	    		       " when cashbook_month=2     then 'Feb '       ||cashbook_year     "+ 
	    		       " when cashbook_month=3  and sup_no=0   then 'Mar '       ||cashbook_year  "+
	    		       "  when Cashbook_Month=3  AND SUP_NO!=0   then 'Sup '       ||SUP_NO    "+                 
	    		       " end MONTH   "+
	    		       " FROM  FAS_REGTB_VIEW WHERE Fin_Year='"+fin_year+"' ORDER BY CMONTH";
	    	 
	    	   
	    	   
	    	   
	    	   
	    	   }else if(Type.equalsIgnoreCase("FYFz") || Type.equalsIgnoreCase("TYFz")||Type.equalsIgnoreCase("ConFYFz"))
	    	   {
	    		 qry = "select * from (SELECT DISTINCT Cashbook_Year,To_date(Cashbook_Month||'-'||Cashbook_Year,'MM-yyyy') CMonth,"+
	    			   "  CASE Cashbook_Month  WHEN 4  THEN 'Apr ' ||Cashbook_Year  WHEN 5 THEN 'May '||Cashbook_Year  WHEN 6 THEN 'Jun '"+
	    			   "  ||Cashbook_Year   WHEN 7   THEN 'Jul '  ||Cashbook_Year  WHEN 8 THEN 'Aug ' ||Cashbook_Year  WHEN 9  THEN 'Sep '"+
	    			   "  ||Cashbook_Year  WHEN 10  THEN 'Oct '  ||Cashbook_Year  WHEN 11  THEN 'Nov ' ||Cashbook_Year WHEN 12 THEN 'Dec '"+
	    			   "  ||Cashbook_Year   WHEN 1 THEN 'Jan '  ||Cashbook_Year  WHEN 2  THEN 'Feb ' ||Cashbook_Year  WHEN 3  THEN 'Mar '"+
	    			   "  ||Cashbook_Year  END MONTH, CASE  WHEN cashbook_month<=3  THEN (cashbook_year-1)  ||'-'  ||cashbook_year WHEN cashbook_month>=4"+
	    			   "  THEN cashbook_year ||'-' ||(Cashbook_Year+1)   END Fin_Year FROM FAS_TRIAL_BALANCE_CMP "+
	    			   " UNION ALL " +
	    			   " SELECT DISTINCT Cashbook_Year, " +
	    			   "  To_date(Cashbook_Month " +
	    			   "  ||'-' " +
	    			   "  ||Cashbook_Year,'MM-yyyy')+SUPPLEMENT_NO Cmonth, " +
	    			   "  'Sup ' " +
	    			   "  ||SUPPLEMENT_NO AS MONTH, " +
	    			   "  CASE " +
	    			   "    WHEN cashbook_month<=3 " +
	    			   "    THEN (cashbook_year-1) " +
	    			   "      ||'-' " +
	    			   "      ||cashbook_year " +
	    			   "    WHEN cashbook_month>=4 " +
	    			   "    THEN cashbook_year " +
	    			   "      ||'-' " +
	    			   "      ||(Cashbook_Year+1) " +
	    			   "  END Fin_Year " +
	    			   " FROM Fas_Trial_Balance_Supplement ) WHERE Fin_Year='"+fin_year+"'  ORDER BY Cashbook_Year, CMonth, MONTH";
	  
	    	   }
	    	System.out.println(qry);
	    		   try{    	  
	    	   
	    	   ps=connection.prepareStatement(qry);
	    	   rset=ps.executeQuery();
	    	   String ChequeDate="";
	    	   while(rset.next())
	    	   {
	    		   Date d_val=rset.getDate("cmonth");
	    			String Stringdate = d_val.toString();

					String[] ddd = Stringdate.split("-");

					int day = Integer.parseInt(ddd[2]);
					int month = Integer.parseInt(ddd[1]);
					int year = Integer.parseInt(ddd[0]);
					String month_Str="";
					if(month==1)month_Str="JAN";
					else if (month==2)month_Str="FEB";
					else if (month==3)month_Str="MAR";
					else if (month==4)month_Str="APR";
					else if (month==5)month_Str="MAY";
					else if (month==6)month_Str="JUN";
					else if (month==7)month_Str="JUL";
					else if (month==8)month_Str="AUG";
					else if (month==9)month_Str="SEP";
					else if (month==10)month_Str="OCT";
					else if (month==11)month_Str="NOV";
					else if (month==12)month_Str="DEC";
					if (day >= 10) {
						ChequeDate = (day + "-" + month_Str + "-" + year);  
					} else {
						ChequeDate = ("0"+day + "-" + month_Str + "-" + year);  
					}
	    		
	    		   xml+="<year>"+rset.getInt("CASHBOOK_YEAR")+"</year>";
	    		   xml+="<month>"+ChequeDate+"</month>";
	    		   xml+="<month_desc>"+rset.getString("MONTH")+"</month_desc>";
	    	   }
	    	   
	    	   if(Type.equalsIgnoreCase("ConFYL")){
	    	   xml+="<type>TYL</type><type1>FYL</type1>";
	    	   }else if(Type.equalsIgnoreCase("ConFYFz")){
	    		   xml+="<type1>FYFz</type1><type>TYFz</type>";
	    	   
	    	   }else
	    	   {
	    		   xml+="<type>"+Type+"</type>";  
	    	   }
	    		   }
	    	   catch (Exception e) {
	//System.out.println(e);
	    	   }
	    	   
	    	   
	       }xml+="</response>";
	    System.out.println(xml);
	       out.write(xml);
	       out.close();
	        
		
	    
	}
        
        
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
    	 System.out.println("do_GEt .... "); 
    	
         
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
        
        try
        {
      int cmb_REgion_Data=Integer.parseInt(request.getParameter("cmb_REgion_Data"));
      String sub_qry1="",head1="";
      if(cmb_REgion_Data==0){
    	  sub_qry1="";
    	  head1="";
      }else{
    	  sub_qry1=" and rv.regid="+cmb_REgion_Data;
    	  if(cmb_REgion_Data==6868)head1="Vellore Region";
    	  if(cmb_REgion_Data==6869)head1="Coimbatore Region";
    	  if(cmb_REgion_Data==6870)head1="Madurai Region";
    	  if(cmb_REgion_Data==6871)head1="Thanjavur Region";
    	  if(cmb_REgion_Data==6777)head1="Hogenakkal WS and  FM Project";
    	  
      }

        String liveFY=request.getParameter("liveFY");
        String liveTY=request.getParameter("liveTY");  
       String f_mnth=request.getParameter("fromMonth");
       String t_mnth=request.getParameter("toMonth");  
       String rtype=request.getParameter("rad_R");
       String sup_query="",head="",qry="";
       if(f_mnth.equalsIgnoreCase(t_mnth))
	   {
		  sup_query=   " AND cmonth          = '"+f_mnth+"' " +sub_qry1;
		 head= "Trial Balance Data for the Month "+ f_mnth.split("-")[1]+" - "+f_mnth.split("-")[2];
	   } if(!f_mnth.equalsIgnoreCase(t_mnth))
	   {
		   sup_query= " AND cmonth   between '"+f_mnth+"' and '"+t_mnth+"'   "+sub_qry1 ;
		   head= "Trial Balance Data for the Period From  "+ f_mnth.split("-")[1]+" - "+f_mnth.split("-")[2]+" To "+t_mnth.split("-")[1]+" - "+t_mnth.split("-")[2];
	   }
	   if (rtype.equalsIgnoreCase("EXCEL")){
	   reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/twad_TB/FAs_regWise_rep_cp.jasper"));
	   }else{
	   reportFile =  new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/twad_TB/FAs_regWise_rep.jasper"));
	   }
       System.out.println("sup_query"+sup_query);  
       System.out.println("reportFile"+reportFile);   
       
        if (!reportFile.exists())
        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
        
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
        System.out.println("jasperReport"+jasperReport);
        Map map=new HashMap();    
        map.put("head",head);
        map.put("sup_query",sup_query); 
        map.put("head1", head1);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);            
        if (rtype.equalsIgnoreCase("HTML"))   
        {
                    response.setContentType("text/html");
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TrialBalance.html\"");
                    PrintWriter out = response.getWriter();
                    JRHtmlExporter exporter = new JRHtmlExporter();
                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
                    exporter.exportReport();
                    out.flush();
                    out.close();
        }
        else  if (rtype.equalsIgnoreCase("PDF"))   
        {
                System.out.println("PDF:::::::::::");
                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TrialBalance.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
        }
        else   if (rtype.equalsIgnoreCase("EXCEL"))   
        {
        	System.out.println("Excel .... ");
                        response.setContentType("application/vnd.ms-excel");
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TrialBalance.xls\"");
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
        else   if (rtype.equalsIgnoreCase("TXT"))   
        {
                        response.setContentType("text/plain");
                        response.setHeader ("Content-Disposition", "attachment;filename=\"TrialBalance.txt\"");
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
