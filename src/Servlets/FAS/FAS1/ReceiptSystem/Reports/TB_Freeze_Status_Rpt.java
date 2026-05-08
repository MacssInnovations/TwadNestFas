package Servlets.FAS.FAS1.ReceiptSystem.Reports;

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
 * Servlet implementation class TB_Freeze_Status_Rpt
 */
public class TB_Freeze_Status_Rpt extends HttpServlet {
	private static final String CONTENT_TYPE =
	        "text/html; charset=windows-1252";       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public TB_Freeze_Status_Rpt() {
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
		
		System.out.println("Welcome to TB_Freeze_Status_Rpt Servlet!............");
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
        Connection connection = null;
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
        int cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        System.out.println("cmbAcc_UnitCode===>"+cmbAcc_UnitCode);
        int cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));
        System.out.println("cmbOffice_code===>"+cmbOffice_code);
        String fin_year=request.getParameter("fin_year");
        System.out.println("fin_year===66>"+fin_year);
        String rtype = request.getParameter("txtoption");
        System.out.println("rtype===44>"+rtype);
//        String qry="SELECT aa.*,\n" + 
//        		"  bb.supplement_no\n" + 
//        		"FROM\n" + 
//        		"  (SELECT A.ACCOUNTING_UNIT_ID,\n" + 
//        		"    (SELECT u.ACCOUNTING_UNIT_NAME\n" + 
//        		"    FROM FAS_MST_ACCT_UNITS U\n" + 
//        		"    WHERE u.ACCOUNTING_UNIT_ID =A.ACCOUNTING_UNIT_ID\n" + 
//        		"    ) AS UNAME,\n" + 
//        		"    (SELECT office.ACCOUNTING_FOR_OFFICE_ID\n" + 
//        		"    FROM FAS_MST_ACCT_UNIT_OFFICES OFFICE\n" + 
//        		"    WHERE office.ACCOUNTING_UNIT_ID = A.ACCOUNTING_UNIT_ID\n" + 
//        		"    ) AS ACCOUNTING_FOR_OFFICE_ID,\n" + 
//        		"    (SELECT unm.OFFICE_NAME\n" + 
//        		"    FROM COM_MST_OFFICES unm\n" + 
//        		"    WHERE UNM.OFFICE_ID =\n" + 
//        		"      (SELECT office.ACCOUNTING_FOR_OFFICE_ID\n" + 
//        		"      FROM FAS_MST_ACCT_UNIT_OFFICES OFFICE\n" + 
//        		"      WHERE office.ACCOUNTING_UNIT_ID = A.ACCOUNTING_UNIT_ID\n" + 
//        		"      )\n" + 
//        		"    )AS offname ,\n" + 
//        		"    A.CASHBOOK_YEAR,\n" + 
//        		"    A.CASHBOOK_MONTH,\n" + 
//        		"    TO_CHAR(to_date(a.cashbook_month\n" + 
//        		"    ||'-'\n" + 
//        		"    ||A.CASHBOOK_YEAR,'mm-yyyy'),'Mon')MON_DES,\n" + 
//        		"    A.TB_STATUS,\n" + 
//        		"    A.TB_DATE,\n" + 
//        		"    a.UPDATED_BY_USER_ID\n" + 
//        		"  FROM FAS_TRIAL_BALANCE_STATUS A " +
//        		"WHERE a.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+ 
//        		"AND to_date(a.cashbook_month\n" + 
//        		"  ||'-'\n" + 
//        		"  ||a.cashbook_year,'mm-yyyy') BETWEEN to_date(4\n" + 
//        		"  ||'-'\n" + 
//        		"  ||"+fin_year.split("-")[0]+",'mm-yyyy')\n" + 
//        		"AND to_date(3\n" + 
//        		"  ||'-'\n" + 
//        		"  ||"+fin_year.split("-")[1]+",'mm-yyyy')\n" + 
//        		"  ORDER BY a.CASHBOOK_YEAR,\n" + 
//        		"  a.CASHBOOK_MONTH\n" + 
//        		"  )aa\n" + 
//        		"LEFT OUTER JOIN\n" + 
//        		"(SELECT A.ACCOUNTING_UNIT_ID,\n" + 
//        		"    (SELECT u.ACCOUNTING_UNIT_NAME\n" + 
//        		"    FROM FAS_MST_ACCT_UNITS U\n" + 
//        		"    WHERE u.ACCOUNTING_UNIT_ID =A.ACCOUNTING_UNIT_ID\n" + 
//        		"    ) AS UNAME,\n" + 
//        		"    (SELECT office.ACCOUNTING_FOR_OFFICE_ID\n" + 
//        		"    FROM FAS_MST_ACCT_UNIT_OFFICES OFFICE\n" + 
//        		"    WHERE office.ACCOUNTING_UNIT_ID = A.ACCOUNTING_UNIT_ID\n" + 
//        		"    ) AS ACCOUNTING_FOR_OFFICE_ID,\n" + 
//        		"    (SELECT unm.OFFICE_NAME\n" + 
//        		"    FROM COM_MST_OFFICES unm\n" + 
//        		"    WHERE UNM.OFFICE_ID =\n" + 
//        		"      (SELECT office.ACCOUNTING_FOR_OFFICE_ID\n" + 
//        		"      FROM FAS_MST_ACCT_UNIT_OFFICES OFFICE\n" + 
//        		"      WHERE office.ACCOUNTING_UNIT_ID = A.ACCOUNTING_UNIT_ID\n" + 
//        		"      )\n" + 
//        		"    )AS offname ,\n" + 
//        		"    A.CASHBOOK_YEAR,\n" + 
//        		"    A.CASHBOOK_MONTH,\n" + 
//        		"    TO_CHAR(to_date(a.cashbook_month\n" + 
//        		"    ||'-'\n" + 
//        		"    ||A.CASHBOOK_YEAR,'mm-yyyy'),'Mon')MON_DES,\n" + 
//        		"    A.TB_STATUS,\n" + 
//        		"    A.TB_DATE,\n" + 
//        		"    A.UPDATED_BY_USER_ID,"+ 
//        		"    a.SUPPLEMENT_NO\n" + 
//        		"FROM FAS_TRIAL_BALANCE_STATUS_SJV a\n" + 
//        		"WHERE a.ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+ 
//        		"AND (a.SUPPLEMENT_NO      =1\n" + 
//        		"OR a.SUPPLEMENT_NO        =2)\n" + 
//        		"AND to_date(a.cashbook_month\n" + 
//        		"  ||'-'\n" + 
//        		"  ||a.cashbook_year,'mm-yyyy') BETWEEN to_date(4\n" + 
//        		"  ||'-'\n" + 
//        		"  ||"+fin_year.split("-")[0]+",'mm-yyyy')\n" + 
//        		"AND to_date(3\n" + 
//        		"  ||'-'\n" + 
//        		"  ||"+fin_year.split("-")[1]+",'mm-yyyy')\n" + 
//        		"  ORDER BY a.CASHBOOK_YEAR,\n" + 
//        		"  a.CASHBOOK_MONTH\n" + 
//        		"  )BB\n" + 
//        		"  on aa.ACCOUNTING_UNIT_ID=bb.ACCOUNTING_UNIT_ID";
        
       // String fin_yr="select * from MST_MONTH";
        String Uname="",offname="";
        int offid=0;
        try
        {
        String query="SELECT ACCOUNTING_UNIT_NAME,ACCOUNTING_UNIT_ID FROM FAS_MST_ACCT_UNITS where  ACCOUNTING_UNIT_ID= "+cmbAcc_UnitCode ;
        PreparedStatement psnew =
                connection.prepareStatement(query);
        ResultSet rsnew = psnew.executeQuery();
        while (rsnew.next()) {
        	Uname = rsnew.getString("ACCOUNTING_UNIT_NAME");
          }
        
        String query_offname="SELECT OFFICE_NAME,office_id FROM com_mst_offices where office_id="+cmbOffice_code;
        PreparedStatement psnew1 =
                connection.prepareStatement(query_offname);
        ResultSet rsnew1 = psnew1.executeQuery();
        while (rsnew1.next()) {
        	offname = rsnew1.getString("OFFICE_NAME");
          }
        
        }
        catch(Exception e)
        {
        	System.out.println("Exception is=======>"+e);
        }
        
        
        String qry="SELECT * FROM(SELECT * FROM MST_MONTH)AA\n" + 
        		"full JOIN\n" + 
        		"(SELECT " +
        		"    A.CASHBOOK_YEAR,\n" + 
        		"    A.CASHBOOK_MONTH,\n" + 
        		"    TO_CHAR(to_date(a.cashbook_month\n" + 
        		"    ||'-'\n" + 
        		"    ||A.CASHBOOK_YEAR,'mm-yyyy'),'Mon')MON_DES,\n" + 
       // 		"     decode(A.TB_STATUS,'Y','Yes','-') as TB_Status,\n" + 
		 		"    case when A.TB_STATUS='Y' then 'Yes' else '-' end as TB_Status,\n" + 
		   
        		"    A.TB_DATE,\n" + 
      //  		" DECODE(A.NIL_TB_STATUS,'Y','Yes','-') AS NIL_TB_STATUS," +
        		"    case when A.NIL_TB_STATUS='Y' then 'Yes' else '-' end as NIL_TB_STATUS,\n" +
        		"    A.UPDATED_BY_USER_ID,\n" + 
        		"    '-' AS SUPPLEMENT_NO " +
        		"      FROM FAS_TRIAL_BALANCE_STATUS A\n" + 
        		"  WHERE a.ACCOUNTING_UNIT_ID=" +cmbAcc_UnitCode+  
        		"  AND to_date(a.cashbook_month\n" + 
        		"    ||'-'\n" + 
        		"    ||a.cashbook_year,'mm-yyyy') BETWEEN to_date(4\n" + 
        		"    ||'-'\n" + 
        //		"    ||"+fin_year.split("-")[0]+",'mm-yyyy')\n" + 
        		"    ||split_part('"+fin_year+"','-',1),'mm-yyyy') \n"+ 
        		"  AND to_date(3\n" + 
        		"    ||'-'\n" + 
        	//	"    ||"+fin_year.split("-")[1]+",'mm-yyyy')\n" + 
        		"   ||split_part('"+fin_year+"','-',2),'mm-yyyy') \n"+ 
        		"\n" + 
        		"UNION all\n" + 
        		"SELECT " +
        		"    A.CASHBOOK_YEAR,\n" + 
        		"    A.CASHBOOK_MONTH,\n" + 
        		"    TO_CHAR(to_date(a.cashbook_month\n" + 
        		"    ||'-'\n" + 
        		"    ||A.CASHBOOK_YEAR,'mm-yyyy'),'Mon')MON_DES,\n" + 
        		"   \n" + 
        	//	"    decode(A.TB_STATUS,'Y','Yes','-') as TB_Status,\n" +
   	 			"   case when A.TB_STATUS='Y' then 'Yes' else '-' end as TB_Status,\n"+ 
        		"    A.TB_DATE,\n" +
       // 		" DECODE(A.NIL_TB_STATUS,'Y','Yes','-') AS NIL_TB_STATUS," +
        		"  case when A.NIL_TB_STATUS='Y' then 'Yes' else '-' end as NIL_TB_STATUS,\n"+ 
		   
        		"    A.UPDATED_BY_USER_ID,\n" + 
        //		"    decode(A.SUPPLEMENT_NO,0,'0',A.SUPPLEMENT_NO) as SUPPLEMENT_NO " +
		  		"   case when (A.SUPPLEMENT_NO=0 ) then '0' else A.SUPPLEMENT_NO ::varchar end as SUPPLEMENT_NO \n"+
	        
        		"  FROM FAS_TRIAL_BALANCE_STATUS_SJV a\n" + 
        		"  WHERE a.ACCOUNTING_UNIT_ID=" +cmbAcc_UnitCode+  
        		"  AND (a.SUPPLEMENT_NO      =1\n" + 
        		"  OR a.SUPPLEMENT_NO        =2)\n" + 
        		"  AND to_date(a.cashbook_month\n" + 
        		"    ||'-'\n" + 
        		"    ||a.cashbook_year,'mm-yyyy') BETWEEN to_date(4\n" + 
        		"    ||'-'\n" + 
       // 		"    ||"+fin_year.split("-")[0]+",'mm-yyyy')\n" + 
        //		 " ||split_part(fin_year,'-',1),'mm-yyyy')\n"+  
        		 " ||split_part('"+fin_year+"','-',1),'mm-yyyy')\n"+  
        		"  AND to_date(3\n" + 
        		"    ||'-'\n" + 
    //    		"    ||"+fin_year.split("-")[1]+",'mm-yyyy')\n" + 
        		"||split_part('"+fin_year+"','-',2),'mm-yyyy') \n"+ 
            	
        		"      )BB\n" + 
        		"ON AA.MONTH_ID=BB.CASHBOOK_MONTH::varchar\n" + 
        		"order by aa.sno,supplement_no" ;
        
        
        reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/TB_Freeze_Status_Rpt.jasper"));
        System.out.println("reportFile:" + reportFile);
        if (!reportFile.exists())
        	throw new JRRuntimeException("File J not found. The report design must be compiled first.");
        JasperReport jasperReport =(JasperReport)JRLoader.loadObject(reportFile.getPath());
        
        Map map = new HashMap();
        map.put("from_year",fin_year.split("-")[0]);
        map.put("to_year",fin_year.split("-")[1]);
        map.put("unit_id",cmbAcc_UnitCode);
        map.put("fin_year", fin_year.split("-")[0]+"-"+fin_year.split("-")[1].substring(2));
        map.put("qry",qry);
        map.put("Uname",Uname);
        map.put("offname",offname);
        map.put("offid",cmbOffice_code);
        
        System.out.println(map);
        JasperPrint jasperPrint =JasperFillManager.fillReport(jasperReport, map, connection);     
        if (rtype.equalsIgnoreCase("HTML")) {
            response.setContentType("text/html");
            response.setHeader("Content-Disposition",
                               "attachment;filename=\"ViewReceipt_Count.html\"");
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
                               "attachment;filename=\"ViewReceipt_Count.pdf\"");
            OutputStream out = response.getOutputStream();
            out.write(buf, 0, buf.length);
            out.close();
        } else if (rtype.equalsIgnoreCase("EXCEL")) {

            response.setContentType("application/vnd.ms-excel");
            response.setHeader("Content-Disposition",
                               "attachment;filename=\"ViewReceipt_Count.xls\"");
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
                               "attachment;filename=\"ViewReceipt_Count.txt\"");

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
            
        }
        catch(Exception e)
        {
        	System.out.println("Major try block Exception is===>"+e);
        }
        
		
		
	}

}
