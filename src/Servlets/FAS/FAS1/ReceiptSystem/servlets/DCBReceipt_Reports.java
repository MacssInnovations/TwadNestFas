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
import Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver;

/**
 * Servlet implementation class DCBReceipt_Reports
 */
public class DCBReceipt_Reports extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private String CONTENT_TYPE = "text/xml; charset=windows-1252";
    /**
     * @see HttpServlet#HttpServlet()
     */
    public DCBReceipt_Reports() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	
		String strCommand = "";
        Connection connection = null;
        ResultSet resultSet = null,rs1=null;
        PreparedStatement preparedStatement = null,ps1=null;
        String xml = "",sql="";
        int count = 0;
        PrintWriter out = response.getWriter();
        HttpSession session = request.getSession(false);
        try {

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
            LoadDriver load = new LoadDriver();
            connection = load.getConnection();
            strCommand = request.getParameter("command");
        } catch (Exception e) {
        	e.printStackTrace();
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }
	
		if(strCommand.equalsIgnoreCase("subCode1")) {
        	response.setContentType(CONTENT_TYPE);
    		int cmbSL_type = 0,cmbOffice_code=0,cmbAcc_UnitCode=0;
            int y = 0;
            xml = "<response><command>subCode1</command>";
            try {
                cmbSL_type =
                        Integer.parseInt(request.getParameter("cmbSL_type"));
            } catch (Exception e) {
                System.out.println("error get SL_type");
            }            
            System.out.println("SL_TYPE " + cmbSL_type + "NN");
            try {
            	cmbOffice_code =
                        Integer.parseInt(request.getParameter("cmbOffice_code"));
            } catch (Exception e) {
                System.out.println("error get cmbOffice_code");
            }
            try {
            	cmbAcc_UnitCode =Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception e) {
                System.out.println("error get cmbAcc_UnitCode");
            }
            
            xml = xml + "<cmbSL_type>" + cmbSL_type + "</cmbSL_type>";
           try
           {
             if (cmbSL_type == 14) // Beneficiaries
            {
                try {
                   PreparedStatement ps =connection.prepareStatement("SELECT BENEFICIARY_SNO AS sl_code,BENEFICIARY_NAME as sl_code_desc From Pms_Dcb_Mst_Beneficiary Where office_id = ? order by BENEFICIARY_NAME");


                    ps.setInt(1, cmbOffice_code);
                  ResultSet  rs = ps.executeQuery();
                    while (rs.next()) {
                        xml =
						 xml + "<cid>" + rs.getInt("sl_code") + "</cid><cname>" +
						   rs.getString("sl_code_desc") + "</cname>";
                        y++;
                    }
                    if (y != 0) {
                        xml = xml + "<flag>success</flag>";
                    } else {
                        xml = xml + "<flag>failure</flag>";
                    }
                    System.out.println(xml);
                    ps.close();
                    rs.close();
                } catch (Exception e) {
                    System.out.println("catch..HERE.in load emp code." + e);
                    xml = xml + "<flag>failure</flag>";
                }

            }
            else {
                System.out.println("no match found");
                xml = xml + "<flag>failure</flag>";
            }
            
           }
           catch(Exception e)
           {
        	   System.out.println("Exception is==subcode1==>"+e);
           }
           xml+="</response>";
           out.println(xml);
			System.out.println("Xml is===========>"+xml);
//	        out.flush();
	        out.close();
        }
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
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

//            ConnectionString = 
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + 
//                    ":" + strsid.trim();
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
            
        	int subLedgerType = Integer.parseInt(request.getParameter("subLedgerType"));
        	System.out.println("subLedgerType==>"+subLedgerType);
        	
        	String subLedgerCode =request.getParameter("subLedgerCode");
        	System.out.println("subLedgerCode==>"+subLedgerCode);
        	
        	int cmbAcc_UnitCode =Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        	System.out.println("cmbAcc_UnitCode==>"+cmbAcc_UnitCode);
        	
        	int cmbOffice_code =Integer.parseInt(request.getParameter("cmbOffice_code"));
        	System.out.println("cmbOffice_code==>"+cmbOffice_code);
        	
        	String fromdate = request.getParameter("txtfromdate");
        	System.out.println("fromdate==>"+fromdate);
        	
        	String todate = request.getParameter("txttodate");
        	System.out.println("todate==>"+todate);
        	
        	String SpecificSL =request.getParameter("SpecificSL");
        	System.out.println("SpecificSL==>"+SpecificSL);
        	
        	String incl_Vr =request.getParameter("incl_Vr");
        	System.out.println("incl_Vr==>"+incl_Vr);
        	
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
                   dateFormat.applyPattern("dd/MM/yyyy");
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
                   dateFormat1.applyPattern("dd/MM/yyyy");
                   dateString1 = dateFormat1.format(d1);
                    
                    
                }
        	try
        	{
        	String sql1="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME FROM FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode;
        	PreparedStatement ps1 =connection.prepareStatement(sql1);
        	ResultSet  rs1 = ps1.executeQuery();
            while (rs1.next()) {
            	Unit=rs1.getInt("ACCOUNTING_UNIT_ID");
            	uname=rs1.getString("ACCOUNTING_UNIT_NAME");
            }
        	int vrno=0,chequeno=0,amount=0;
        	String vdate="",cheqdate="";
        	

        	unitcode=Unit +"-"+uname;
        	heading="Details of DCB Receipts for the period from " + dateString + " to " + dateString1;
        	if(SpecificSL.equals("All"))
        	{
        		if(incl_Vr.equals("Y"))
        		{
        		myQry=" SELECT m.RECEIPT_NO, " + 
        				"  m.RECEIPT_DATE," + 
//        				"  T.CHEQUE_DD_NO," + 
//        				"  T.CHEQUE_DD_DATE," + 
        				"  m.TOTAL_AMOUNT," + 
        				"  M.SUB_LEDGER_CODE," + 
        				"  SL.SL_CODENAME " +
        				"  FROM FAS_RECEIPT_MASTER m, " + 
//        				"  FAS_RECEIPT_TRANSACTION T," + 
        				"  SL_TYPE_CODE_NAME_VIEW_M sl " +
        				" WHERE " +
        				" M.SUB_LEDGER_TYPE_CODE    =SL.SL_TYPE " +
        				" AND M.SUB_LEDGER_CODE =SL.SL_CODE "+
        				" and M.ACCOUNTING_UNIT_ID=" + cmbAcc_UnitCode +
        				" AND M.ACCOUNTING_FOR_OFFICE_ID=" + cmbOffice_code + 
        				" AND M.RECEIPT_DATE BETWEEN '"+ dateString +"' AND '"+ dateString1 +"'" + 
        				" AND m.SUB_LEDGER_TYPE_CODE=14" + 
        				" and M.RECEIPT_STATUS='L' order by m.RECEIPT_DATE" ;
        		
        		
        		
        		
        		}
        		else
        		{
        			myQry=" SELECT m.RECEIPT_NO, " + 
            				"  m.RECEIPT_DATE," + 
//            				"  T.CHEQUE_DD_NO," + 
//            				"  T.CHEQUE_DD_DATE," + 
            				"  m.TOTAL_AMOUNT," + 
            				"  M.SUB_LEDGER_CODE," + 
            				"  SL.SL_CODENAME " +
            				"  FROM FAS_RECEIPT_MASTER m, " + 
//            				"  FAS_RECEIPT_TRANSACTION T," + 
            				"  SL_TYPE_CODE_NAME_VIEW_M sl " +
            				" WHERE " +
            				" M.SUB_LEDGER_TYPE_CODE    =SL.SL_TYPE " +
            				" AND M.SUB_LEDGER_CODE =SL.SL_CODE "+
            				" and M.ACCOUNTING_UNIT_ID=" + cmbAcc_UnitCode +
            				" AND M.ACCOUNTING_FOR_OFFICE_ID=" + cmbOffice_code + 
            				" AND M.RECEIPT_DATE BETWEEN '"+ dateString +"' AND '"+ dateString1 +"'" + 
            				" AND m.SUB_LEDGER_TYPE_CODE=14" + 
            				" and M.RECEIPT_STATUS='L' order by m.RECEIPT_DATE" ;
        		}
        	}
        	else if(SpecificSL.equals("Specific"))
        	{
        		if(incl_Vr.equals("Y"))
        		{
        			myQry=" SELECT m.RECEIPT_NO, " + 
            				"  m.RECEIPT_DATE," + 
//            				"  T.CHEQUE_DD_NO," + 
//            				"  T.CHEQUE_DD_DATE," + 
            				"  m.TOTAL_AMOUNT," + 
            				"  M.SUB_LEDGER_CODE," + 
            				"  SL.SL_CODENAME " +
            				"  FROM FAS_RECEIPT_MASTER m, " + 
//            				"  FAS_RECEIPT_TRANSACTION T," + 
            				"  SL_TYPE_CODE_NAME_VIEW_M sl " +
            				" WHERE " +
            				" M.SUB_LEDGER_TYPE_CODE    =SL.SL_TYPE " +
            				" AND M.SUB_LEDGER_CODE =SL.SL_CODE "+
            				" and M.ACCOUNTING_UNIT_ID=" + cmbAcc_UnitCode +
            				" AND M.ACCOUNTING_FOR_OFFICE_ID=" + cmbOffice_code + 
            				" AND M.RECEIPT_DATE BETWEEN '"+ dateString +"' AND '"+ dateString1 +"'" + 
            				" AND m.SUB_LEDGER_TYPE_CODE=14" + 
            				" AND M.SUB_LEDGER_CODE=" + subLedgerCode +
            				" and M.RECEIPT_STATUS='L' order by m.RECEIPT_DATE" ;
        		}
        		else
        		{
        			myQry=" SELECT m.RECEIPT_NO, " + 
            				"  m.RECEIPT_DATE," + 
//            				"  T.CHEQUE_DD_NO," + 
//            				"  T.CHEQUE_DD_DATE," + 
            				"  m.TOTAL_AMOUNT," + 
            				"  M.SUB_LEDGER_CODE," + 
            				"  SL.SL_CODENAME " +
            				"  FROM FAS_RECEIPT_MASTER m, " + 
//            				"  FAS_RECEIPT_TRANSACTION T," + 
            				"  SL_TYPE_CODE_NAME_VIEW_M sl " +
            				" WHERE " +
            				" M.SUB_LEDGER_TYPE_CODE    =SL.SL_TYPE " +
            				" AND M.SUB_LEDGER_CODE =SL.SL_CODE "+
            				" and M.ACCOUNTING_UNIT_ID=" + cmbAcc_UnitCode +
            				" AND M.ACCOUNTING_FOR_OFFICE_ID=" + cmbOffice_code + 
            				" AND M.RECEIPT_DATE BETWEEN '"+ dateString +"' AND '"+ dateString1 +"'" + 
            				" AND m.SUB_LEDGER_TYPE_CODE=14" + 
            				" AND M.SUB_LEDGER_CODE=" + subLedgerCode +
            				" and M.RECEIPT_STATUS='L' order by m.RECEIPT_DATE" ;
        		}
        	}
        	
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
        		map.put("unitcode", unitcode);
        		map.put("heading", heading);
        		map.put("qry", myQry);
        		map.put("cnt", cnt);

        		String s="";
        		if(SpecificSL.equals("All"))
        		{
        			if(incl_Vr.equals("Y"))
            		{
        				s=request.getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/DCBReceipt_ReportAll_incVr.jrxml");	
            		}
        			else
        			{
        				s=request.getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/DCBReceipt_ReportAll.jrxml");	
        			}
        			
        		}
        		else if(SpecificSL.equals("Specific"))
        		{
        			if(incl_Vr.equals("Y"))
            		{
        				s=request.getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/DCBReceipt_Report_Specific_incVr.jrxml");	
            		}
        			else
        			{
        				s=request.getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/DCBReceipt_Report_Specific.jrxml");		
        			}
        		}

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
