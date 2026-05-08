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

import net.sf.jasperreports.engine.util.JRLoader;


public class fundtransferUnitsServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
	Connection connection = null;

	public fundtransferUnitsServlet() {
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
			String qry1="SELECT ACCOUNTING_UNIT_ID, " +
					"  ACCOUNTING_UNIT_NAME, " +
					"  officeid, " +
					"  acntcode, " +
					"  SUM(unitamt)  AS unitamt, " +
					"  SUM(bankmamt) AS bankmamt, " +
					"  SUM(remngamt) AS remngamt, " +
					"  bank_id, " +
					"  bank_name " +
					" FROM " +
					"  (SELECT a.year, " +
					"    a.officeid, " +
					"    a.vrno, " +
					"    a.month, " +
					"    a.acntcode, " +
					"    a.amt1                     AS unitamt, " +
					"    b.amt2                     AS bankmamt, " +
					"    NVL(a.amt1,0)-NVL(b.amt2,0)AS remngamt, " +
					"    bank.bank_id, " +
					"    bank.bank_name " +
					"  FROM " +
					"    (SELECT m.cashbook_year   AS YEAR, " +
					"      m.cashbook_month        AS MONTH, " +
					"      m.ho_bank_id            AS bankid, " +
					"      t.account_head_code     AS acntcode, " +
					"      t.voucher_no            AS vrno, " +
					"      SUM(t.amount)           AS amt1, " +
					"      t.transfer_to_office_id AS officeid " +
					"    FROM FAS_FUND_TRF_FROM_HO_MASTER m " +
					"    INNER JOIN FAS_FUND_TRF_FROM_HO_TRN t " +
					"    ON m.cashbook_month =t.cashbook_month " +
					"    AND m.cashbook_year =t.cashbook_year " +
					"    AND m.voucher_no    =t.voucher_no " +
					//"      --  AND m.ho_bank_id              =t.office_bank_id " +
					//"      --  AND m.date_of_transfer        =t.cheque_dd_date " +
					"    AND m.accounting_unit_id      =t.accounting_unit_id " +
					"    AND m.accounting_for_office_id=t.accounting_for_office_id " +
					"    AND To_Date((m.cashbook_month " +
					"      || '-' " +
					"      || m.cashbook_year),'mm-yyyy') BETWEEN To_Date(04 " +
					"      || '-' " +
					"      ||"+from_year+",'mm-yyyy') " +
					"    AND To_Date(03 " +
					"      ||'-' " +
					"      ||"+to_year+",'mm-yyyy') " +
					"    AND m.transfer_status ='L' " +
					"    GROUP BY m.cashbook_year, " +
					"      m.cashbook_month, " +
					"      m.ho_bank_id, " +
					"      t.account_head_code, " +
					"      t.voucher_no, " +
					"      t.transfer_to_office_id " +
					"    )a " +
					"  LEFT OUTER JOIN " +
					"    (SELECT m.cashbook_month AS month1, " +
					"      m.cashbook_year        AS year1, " +
					"      tn.voucher_no          AS vrno1, " +
					"      SUM(h.total_amount)    AS amt2, " +
					"      h.ho_bank_id, " +
					"      tn.account_head_code       AS acntcode, " +
					"      h.ACCOUNTING_FOR_OFFICE_ID AS officeid1 " +
					"    FROM FAS_FUND_TRF_FROM_HO_MASTER m " +
					"    INNER JOIN FAS_FUND_TRF_FROM_HO_TRN tn " +
					"    ON m.cashbook_month           =tn.cashbook_month " +
					"    AND m.cashbook_year           =tn.cashbook_year " +
					"    AND m.voucher_no              =tn.voucher_no " +
					"    AND m.accounting_unit_id      =tn.accounting_unit_id " +
					"    AND m.accounting_for_office_id=tn.accounting_for_office_id " +
					"    INNER JOIN FAS_FUND_RECEIPT_BY_OFFICE h " +
					"    ON h.trf_voucher_no           =tn.voucher_no " +
					"    AND h.TRF_VOUCHER_DATE        =m.DATE_OF_TRANSFER " +
					"    AND H.ACCOUNTING_FOR_OFFICE_ID=tn.transfer_to_office_id " +
					"    AND tn.sl_no                  =h.trf_sl_no " +
					"    AND To_Date((h.cashbook_month " +
					"      || '-' " +
					"      || h.cashbook_year),'mm-yyyy') BETWEEN To_Date(04 " +
					"      || '-' " +
					"      ||"+from_year1+",'mm-yyyy') " +
					"    AND To_Date(03 " +
					"      ||'-' " +
					"      ||"+to_year1+",'mm-yyyy') " +
					"    AND tn.auto_status   ='Y' " +
					"    AND h.receipt_status ='L' " +
					"    GROUP BY h.cashbook_month, " +
					"      h.cashbook_year, " +
					"      tn.voucher_no, " +
					"      h.ho_bank_id, " +
					"      tn.account_head_code, " +
					"      h.ACCOUNTING_FOR_OFFICE_ID " +
					"    )b ON b.month1 =a.month " +
					"  AND b.year1      =a.year " +
					"  AND b.vrno1      =a.vrno " +
					"  AND b.officeid1  =a.officeid " +
					"  AND b.ho_bank_id =a.bankid " +
					"  AND b.acntcode   =a.acntcode " +
					"  INNER JOIN Fas_Mst_Banks Bank " +
					"  ON Bank.Bank_Id= b.ho_bank_id " +
					"  )xx " +
					"  INNER JOIN FAS_MST_ACCT_UNITS u " +
					" ON u.ACCOUNTING_UNIT_OFFICE_ID=officeid " +
					" GROUP BY ACCOUNTING_UNIT_ID, " +
					"  ACCOUNTING_UNIT_NAME, " +
					"  officeid, " +
					"  acntcode, " +
					"  bank_id, " +
					"  bank_name " +
					" ORDER BY bank_id, " +
					"  ACCOUNTING_UNIT_ID, " +
					"  acntcode";
			
			
		
				reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Queries/jasper/fundtransUnits.jasper")); 
				System.out.println("reportFile"+reportFile);   
				System.out.println("qry"+qry1);   
				
			

			if (!reportFile.exists())
				throw new JRRuntimeException("File J not found. The report design must be compiled first.");

			JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
			System.out.println("jasperReport"+jasperReport);
			Map map=new HashMap();
		
				map.put("Heading",Heading);
				map.put("sql",qry1);
			
			

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
