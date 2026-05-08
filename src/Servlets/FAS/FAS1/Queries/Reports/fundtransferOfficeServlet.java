package Servlets.FAS.FAS1.Queries.Reports;

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


public class fundtransferOfficeServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
	Connection connection = null;

	public fundtransferOfficeServlet() {
		super();

	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		System.out.println("comes in FT_FR_report_servlet servlet *dhana");
		String update_user=null;
		int errcode=0;
		Timestamp ts=null;
		try
		{
			HttpSession session=request.getSession(false);
			if(session==null)
			{
				System.out.println(request.getContextPath()+"/index.jsp");
				response.sendRedirect(request.getContextPath()+"/index.jsp");
			}
			System.out.println(session);
			// changes here
			update_user=(String)session.getAttribute("UserId");
			long l=System.currentTimeMillis();
			ts=new Timestamp(l);                      

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
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection			Class.forName(strDriver.trim());
			connection =DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
		} catch (Exception ex) {
			String connectMsg ="Could not create the connection" + ex.getMessage() + " " + 
					ex.getLocalizedMessage();
			System.out.println(connectMsg);
		}
		JasperDesign jasperDesign = null;
		File reportFile=null;
		int prevYear=0;
		int nextYear=0;
		int next_year2=0;
		int next_year3=0;
		int pre_year1=0;
		int pre_year2=0;
		int pre_year3=0;
		try 
		{

			System.out.println("inside servlet>>>>>>>>>>>>>>>..");
			String txtCB_Year[]=request.getParameter("txtCB_Year").split("-");

			System.out.println("txtCB_Month>>>>"+txtCB_Year[0]);
			prevYear=Integer.parseInt(txtCB_Year[0]);
			nextYear=Integer.parseInt(txtCB_Year[1]);
int from_year=0,to_year=0;
int from_year1=0,to_year1=0;
String Heading="";

			String fin=request.getParameter("txtCB_Year");
			String report_Type=request.getParameter("type_report");
			System.out.println("report_Type>>>>"+report_Type);
			next_year2=prevYear+1;
			next_year3=nextYear+1;
			
			
			if(report_Type.equalsIgnoreCase("one")){
				from_year=prevYear;
				to_year=nextYear;
				from_year1=prevYear;
				to_year1=nextYear;
				Heading="Fund Transfer vs Fund Receipt For the Financial Year  "+prevYear+" - "+nextYear;
			}else if(report_Type.equalsIgnoreCase("two"))
			{
				from_year=prevYear;
				to_year=nextYear;
				from_year1=prevYear+1;
				to_year1=nextYear+1;
				Heading="Fund Transfer vs Fund Receipt For the Financial Year  "+prevYear+" - "+nextYear;
			}else{
				from_year=prevYear-1;
				to_year=nextYear-1;
				from_year1=prevYear;
				to_year1=nextYear;
				Heading="Fund Transfer vs Fund Receipt For the Financial Year  "+prevYear+" - "+nextYear;
			}
			String qry="SELECT a.accounting_unit_id, " +
					"  u.accounting_unit_name, " +
					"  a.office_id, " +
					"  a.ho_bank_id, " +
					"  b.bank_name, " +
					"  a.cr_account_head_code, " +
					"  SUM(a.amt1)                      AS ori_amount, " +
					"  SUM(b.amt1)                      AS Trnf_amount, " +
					"  SUM(NVL(a.amt1,0)-NVL(b.amt1,0)) AS remain_amount " +
					"FROM " +
					"  (SELECT NVL(o.cheque_dd_no,0) cheque_dd_no , " +
					"    o.CHEQUE_OR_DD, " +
					"    o.accounting_unit_id, " +
					"    O.ACCOUNTING_FOR_OFFICE_ID AS office_id, " +
					"    o.ho_bank_id, " +
					"    o.VOUCHER_NO, " +
					"    O.cashbook_year, " +
					"    O.cashbook_month, " +
					"    o.cr_account_head_code, " +
					"    o.cheque_dd_date, " +
					"    o.AUTO_STATUS, " +
					"    SUM(O.total_amount)AS amt1 " +
					"  FROM FAS_FUND_TRF_FROM_OFFICE o " +
					"  WHERE To_Date((cashbook_month " +
					"    || '-' " +
					"    || cashbook_year),'mm-yyyy') BETWEEN To_Date(04 " +
					"    || '-' " +
					"    ||"+from_year+",'mm-yyyy') " +
					"  AND To_Date(03 " +
					"    ||'-' " +
					"    ||"+to_year+",'mm-yyyy') " +
					"  AND o.transfer_status   ='L' " +
				//	"  AND o.accounting_unit_id=258 " +
					"  GROUP BY NVL(o.cheque_dd_no,0), " +
					"    o.CHEQUE_OR_DD, " +
					"    o.accounting_unit_id, " +
					"    O.ACCOUNTING_FOR_OFFICE_ID, " +
					"    o.ho_bank_id, " +
					"    o.VOUCHER_NO, " +
					"    O.cashbook_year, " +
					"    O.cashbook_month, " +
					"    o.cr_account_head_code, " +
					"    o.cheque_dd_date, " +
					"    o.AUTO_STATUS " +
					"  )a " +
					"LEFT OUTER JOIN " +
					"  (SELECT NVL(o.cheque_dd_no,0) cheque_dd_no , " +
					"    o.CHEQUE_OR_DD, " +
					"    o.accounting_unit_id, " +
					"    O.ACCOUNTING_FOR_OFFICE_ID AS office_id, " +
					"    o.ho_bank_id, " +
					"    o.VOUCHER_NO, " +
					"    O.cashbook_year, " +
					"    O.cashbook_month, " +
					"    o.cr_account_head_code, " +
					"    o.cheque_dd_date, " +
					"    o.AUTO_STATUS, " +
					"    SUM(O.total_amount)AS amt1 " +
					"  FROM FAS_FUND_TRF_FROM_OFFICE o, " +
					"    FAS_FUND_RECEIPT_BY_HO h " +
					"  WHERE o.ACCOUNTING_FOR_OFFICE_ID=h.received_from_office_id " +
					"  AND o.VOUCHER_NO                =TRF_VOUCHER_NO " +
					"  AND o.DATE_OF_TRANSFER          =H.TRF_VOUCHER_DATE " +
					"  AND To_Date((h.cashbook_month " +
					"    || '-' " +
					"    || h.cashbook_year),'mm-yyyy') BETWEEN To_Date(04 " +
					"    || '-' " +
					"    ||"+from_year1+",'mm-yyyy') " +
					"  AND To_Date(03 " +
					"    ||'-' " +
					"    ||"+to_year1+",'mm-yyyy') " +
					"  AND o.transfer_status   ='L' " +
				//	"  AND o.accounting_unit_id=258 " +
					"  GROUP BY NVL(o.cheque_dd_no,0), " +
					"    o.CHEQUE_OR_DD, " +
					"    o.accounting_unit_id, " +
					"    O.ACCOUNTING_FOR_OFFICE_ID, " +
					"    o.ho_bank_id, " +
					"    o.VOUCHER_NO, " +
					"    O.cashbook_year, " +
					"    O.cashbook_month, " +
					"    o.cr_account_head_code, " +
					"    o.cheque_dd_date, " +
					"    o.AUTO_STATUS " +
					"  )b " +
					"ON a.accounting_unit_id=b.accounting_unit_id " +
					"AND a.office_id        =b.office_id " +
					"AND a.cashbook_year    =b.cashbook_year " +
					"AND a.cashbook_month   =b.cashbook_month " +
					"AND a.VOUCHER_NO       =b.VOUCHER_NO " +
					"INNER JOIN FAS_MST_ACCT_UNITS u " +
					"ON a.accounting_unit_id=u.accounting_unit_id " +
					"INNER JOIN FAS_MST_BANKS b " +
					"ON a.ho_bank_id=b.bank_id " +
					"GROUP BY a.ho_bank_id, " +
					"  b.bank_name, " +
					"  a.accounting_unit_id, " +
					"  u.accounting_unit_name, " +
					"  a.office_id, " +
					"  a.cr_account_head_code    order by a.ho_bank_id, a.accounting_unit_id, a.cr_account_head_code " ;
			
			
		
				reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Queries/jasper/fundtransOffice.jasper")); 
				System.out.println("reportFile"+reportFile);   
				System.out.println("qry"+qry);   
				
			

			if (!reportFile.exists())
				throw new JRRuntimeException("File J not found. The report design must be compiled first.");

			JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
			System.out.println("jasperReport"+jasperReport);
			Map map=new HashMap();
		
				map.put("Heading",Heading);
				map.put("sql",qry);
			
			

			JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);            

			System.out.println("PDF:::::::::::");
			byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
			response.setContentType("application/pdf");
			response.setContentLength(buf.length);
			response.setHeader ("Content-Disposition", "attachment;filename=\"FT_FR.pdf\"");
			OutputStream out = response.getOutputStream();
			out.write(buf, 0, buf.length);
			out.close();
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
