package Servlets.FAS.FAS1.ReceiptSystem.servlets;

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
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

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

/**
 * Servlet implementation class DCBReceipt_Reports_HO
 */
public class DCBReceipt_Reports_HO extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String CONTENT_TYPE = "text/xml; charset=windows-1252";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DCBReceipt_Reports_HO() {
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
		
		

		// TODO Auto-generated method stub
		Connection connection = null;
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
        try {


            ResourceBundle rs = ResourceBundle.getBundle("Servlets.Security.servlets.Config");
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
        JasperDesign jasperDesign = null;
        File reportFile=null;
        
        
        
        
//        if (strCommand.equalsIgnoreCase("report")){
        	String myQry = "",add_con="",dateString="",dateString1="";
        	String sd[] = null;
            java.util.Date d = null;
            java.util.Date d1 = null;
            System.out.println("here i come");
            Date txtfrom_date = null, txtto_date = null;
            Calendar c = null;
            int Unit=0;
            String uname="";
            String unitcode="";
            String heading="";
            int cnt=0;
            
        	        	
        	
        	int cmbOffice_code =Integer.parseInt(request.getParameter("cmbOffice_code"));
        	System.out.println("cmbOffice_code==>"+cmbOffice_code);
        	
        	String fromdate = request.getParameter("txtfromdate");
        	System.out.println("fromdate==>"+fromdate);
        	
        	String todate = request.getParameter("txttodate");
        	System.out.println("todate==>"+todate);
        	
        	
        	
        	if (!fromdate.equalsIgnoreCase("") &&
                    !todate.equalsIgnoreCase("")) {
                    sd = request.getParameter("txtfromdate").split("/");
                    c =
       new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                             Integer.parseInt(sd[0]));
                    d = c.getTime();
                    txtfrom_date = new Date(d.getTime());
                    System.out.println("txtfromdate " + txtfrom_date);

                    sd = request.getParameter("txttodate").split("/");
                    c =
       new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                             Integer.parseInt(sd[0]));
                    d = c.getTime();
                    txtto_date = new Date(d.getTime());
                    System.out.println("txttodate " + txtto_date);

                    System.out.println("fromdate" + txtfrom_date);
                    System.out.println("todate" + txtto_date);
                    
                    
                    
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
                    
                    
                }
        	try
        	{
//        	String sql1="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode;
//        	PreparedStatement ps1 =connection.prepareStatement(sql1);
//        	ResultSet  rs1 = ps1.executeQuery();
//            while (rs1.next()) {
//            	Unit=rs1.getInt("ACCOUNTING_UNIT_ID");
//            	uname=rs1.getString("ACCOUNTING_UNIT_NAME");
//            }
        	int vrno=0,chequeno=0,amount=0;
        	String vdate="",cheqdate="";
        	

        	unitcode=Unit +"-"+uname;
        	heading="Unitwise Abstract of DCB Receipts for the period from " + dateString + " to " + dateString1;

        	
//        		myQry=" SELECT M.ACCOUNTING_UNIT_ID,m.RECEIPT_NO, " + 
//        				" ACC.ACCOUNTING_UNIT_NAME, " +
//        				"  m.RECEIPT_DATE," + 
//        				"  m.TOTAL_AMOUNT," + 
//        				"  M.SUB_LEDGER_CODE," + 
//        				"  SL.SL_CODENAME " +
//        				"  FROM FAS_RECEIPT_MASTER m, " + 
//        				"  SL_TYPE_CODE_NAME_VIEW_M sl, " +
//        				" FAS_MST_ACCT_UNITS acc " +
//        				" WHERE " +
//        				" M.SUB_LEDGER_TYPE_CODE=SL.SL_TYPE " +
//        				" AND M.SUB_LEDGER_CODE =SL.SL_CODE "+
//        				" and M.ACCOUNTING_UNIT_ID=ACC.ACCOUNTING_UNIT_ID " +
//        				" AND M.RECEIPT_DATE BETWEEN '"+ dateString +"' AND '"+ dateString1 +"'" + 
//        				" AND m.SUB_LEDGER_TYPE_CODE=14" + 
//        				" and M.RECEIPT_STATUS='L' ORDER BY M.ACCOUNTING_UNIT_ID,M.RECEIPT_NO, " + 
//        				"  m.RECEIPT_DATE" ;
        		
        		myQry=" SELECT M.ACCOUNTING_UNIT_ID, " +
        				" ACC.ACCOUNTING_UNIT_NAME, " +
        				" SUM(M.TOTAL_AMOUNT) AS tot_amt " +
        				" FROM FAS_RECEIPT_MASTER m, " +
        				" FAS_MST_ACCT_UNITS acc " +
        				" WHERE M.ACCOUNTING_UNIT_ID =ACC.ACCOUNTING_UNIT_ID " +
        				" AND M.RECEIPT_DATE BETWEEN '"+ dateString +"' AND '"+ dateString1 +"'" + 
        				" AND m.SUB_LEDGER_TYPE_CODE=14 " +
        				" AND M.RECEIPT_STATUS      ='L' " +
        				" GROUP BY M.ACCOUNTING_UNIT_ID, ACC.ACCOUNTING_UNIT_NAME " +
        				" ORDER BY M.ACCOUNTING_UNIT_ID, " +
        				" ACC.ACCOUNTING_UNIT_NAME ";
        		
        	}catch(Exception e)
        	{
        		System.out.println("SQL---->"+e);
        	}
        	System.out.println("myQry=========>"+myQry);
        	
        	
        	
        	try{
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
            	map.put("fromdate", txtfrom_date);
            	map.put("todate", txtto_date);
        		map.put("unitname", unitcode);
        		map.put("heading", heading);
        		map.put("qry", myQry);
        		map.put("cnt", cnt);
        		map.put("Unit", Unit);

        		String s="";
       		
        				s=request.getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/DCBReceipt_ReportAll_HO.jrxml");	
        			
        	System.out.println("Jasper:"+s);
    		String output=request.getParameter("fileType");
    		jasperDesign = JasperManager.loadXmlDesign(s);
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
	            	response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report.pdf\"");
	            	os.write(JasperManager.printReportToPdf(jasperPrint));
	            	os.close();
        	}else if(output.equalsIgnoreCase("excel")){
        			response.setContentType("application/vnd.ms-excel");
        			response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report.xls\"");
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
            		 response.setHeader ("Content-Disposition", "attachment;filename=\"Summary_Report.html\"");
            		 PrintWriter out = response.getWriter();
            		 JRHtmlExporter exporter = new JRHtmlExporter();            		
            		 exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
            		 exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
            		 exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
            		 exporter.exportReport();
            		 out.flush();
            		 out.close();
        		}
        	}catch (Exception e) {
				// TODO: handle exception
			}
//        }
        
	
		
	}

}
