package Servlets.FAS.FAS1.FundTransferSystem.servlets;

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
import java.sql.SQLException;
import java.sql.Timestamp;
import java.text.ParseException;
import java.text.SimpleDateFormat;
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

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;
public class FundReceiptPayment extends HttpServlet {
	private static final long serialVersionUID = 1L;
    public FundReceiptPayment() {
        super();
    }
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
	{
		
		Connection con = null;
		String datefield="",que="",que2="",todate="",dateString="",fromdate="",dateString1="";
		 Calendar c;
		
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
		try {
			ResourceBundle rs1 = ResourceBundle
					.getBundle("Servlets.Security.servlets.Config");
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
			con = DriverManager.getConnection(ConnectionString, strdbusername
					.trim(), strdbpassword.trim());
		} catch (Exception e) {
			System.out.println("Exception in opening connection :" + e);

		}
		String strcommand=request.getParameter("command");
		long l = System.currentTimeMillis();
         Timestamp ts = new Timestamp(l);
		Date txtTo_date=null,txtFrom_date=null;
		int cboAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
		int cboOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
		  /** Get From Date */
		

        java.util.Date d = null;
        java.util.Date d1 = null;
        java.sql.Date FromDate = null;
        java.sql.Date ToDate = null;
        double Amount=0.0;
        try {
            todate = request.getParameter("txtTo_date");
            //System.out.println("fromdate***********"+fromdate);
        } catch (Exception e) {
            System.out.println(e);
        }
        try {
            fromdate = request.getParameter("txtFrom_date");
            //System.out.println("fromdate***********"+fromdate);
        } catch (Exception e) {
            System.out.println(e);
        }

        if (!fromdate.equalsIgnoreCase("") &&
                !todate.equalsIgnoreCase("")) {


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
                //ToDate = java.sql.Date.valueOf(dateString1);
                
                //System.out.println("Todate      :" + ToDate);
            }
       
         
        
         int txtCB_Year_to = Integer.parseInt(request
 				.getParameter("txtCB_Year_to"));
         
 		int txtCB_Month_to = Integer.parseInt(request
 				.getParameter("txtCB_Month_to"));
 		
 		String monthInWords="";
        if(txtCB_Month_to==1)
        monthInWords="January";
        else if(txtCB_Month_to==2)
        monthInWords="February";
        else if(txtCB_Month_to==3)
        monthInWords="March";
        else if(txtCB_Month_to==4)
        monthInWords="April";
        else if(txtCB_Month_to==5)
        monthInWords="May";
        else if(txtCB_Month_to==6)
        monthInWords="June";
        else if(txtCB_Month_to==7)
        monthInWords="July";
        else if(txtCB_Month_to==8)
        monthInWords="August";
        else if(txtCB_Month_to==9)
        monthInWords="September";
        else if(txtCB_Month_to==10)
        monthInWords="October";
        else if(txtCB_Month_to==11)
        monthInWords="November";
        else if(txtCB_Month_to==12)
        monthInWords="December";
 		
		String title="Report for Fund Receipt Payment ...";
		 String UnitName="";
			File reportFile = null;
			   try {
			        PreparedStatement ps=null;
			        ResultSet rs=null;
			        String OfficeName="";
			        ps=con.prepareStatement("Select Decode(Sum(Total_Amount),Null,0,Sum(Total_Amount))As Amount "+
			        		" FROM FAS_FUND_TRF_FROM_OFFICE WHERE ACCOUNTING_UNIT_ID=? And Cashbook_Year       =? AND Cashbook_Month=? and  REMITTANCE_TYPE='U' and TRANSFER_STATUS='L'");
			        ps.setInt(1,cboAcc_UnitCode);
			        ps.setInt(2,txtCB_Year_to);
			        ps.setInt(3,txtCB_Month_to);
			        
			        rs=ps.executeQuery();
			        if(rs.next()) {
			        	Amount=rs.getDouble("Amount");
			        }
			        }
			        catch (SQLException e) {
			            System.out.println("SQL Exception -->"+e);
			        }
			
			        
			        
			        
			        //Accounting unit name:
			        
			        try {
				        PreparedStatement ps1=null;
				        ResultSet rs1=null;
				        String OfficeName="";
				        ps1=con.prepareStatement("select ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=? ");
				        ps1.setInt(1,cboAcc_UnitCode);
				       
				        rs1=ps1.executeQuery();
				        if(rs1.next()) {
				        	UnitName=rs1.getString("ACCOUNTING_UNIT_NAME");
				        }
				        }
				        catch (SQLException e) {
				            System.out.println("SQL Exception -->"+e);
				        }
				        
				        //------------------------
			try {
				
									
				//System.out.println("calling servlet...");
				reportFile = new File(getServletContext().getRealPath(
						"org/FAS/FAS1/Reports/FundReceiptSystem/jasper/FundReceiptPayment.jasper"));
				
				if (!reportFile.exists())
					throw new JRRuntimeException(
							"File J not found. The report design must be compiled first.");

				JasperReport jasperReport = (JasperReport) JRLoader
						.loadObject(reportFile.getPath());
				Map map = null;
				map = new HashMap();
				if(strcommand.equalsIgnoreCase("Report_month")) 
				{
					datefield="Fund Receipt vs Payment For The Month "+monthInWords+" "+txtCB_Year_to;
					que2=" and CASHBOOK_YEAR="+txtCB_Year_to+" and CASHBOOK_MONTH="+txtCB_Month_to;
					que=" and m.CASHBOOK_YEAR="+txtCB_Year_to+" and m.CASHBOOK_MONTH="+txtCB_Month_to;
	
				}else if(strcommand.equalsIgnoreCase("Report_date")) 
				{
					datefield="Fund Receipt vs Payment For The Period From  "+dateString+"  To  "+dateString1;
					que2=" and RECEIPT_DATE between '" + dateString + "' and '" + dateString1 +"'";
					que=" and m.PAYMENT_DATE between '" + dateString + "' and '" + dateString1 +"'";
				/*	que2=" and RECEIPT_DATE between '01-Aug-2012' and '30-Aug-2012'";
					que=" and m.PAYMENT_DATE between '01-Aug-2012' and '30-Aug-2012'";
	*/
				}
				System.out.println("datefield...>"+datefield);
				map.put("unitid", cboAcc_UnitCode);
				map.put("officeid", cboOffice_code);
				map.put("heading",datefield);
				map.put("txtFrom_date", txtFrom_date);
				map.put("txtTo_date", txtTo_date);
				map.put("cb_year",txtCB_Year_to);
				map.put("cb_month",txtCB_Month_to);
				map.put("res_quer",que2);
				map.put("result_query",que);
				map.put("Amount_unspent",Amount);
				map.put("UnitName", UnitName);
				
				System.out.println("que2"+que2);
				System.out.println("upto"+que);
				
				
				
				JasperPrint jasperPrint = JasperFillManager.fillReport(
						jasperReport, map, con);
			//	System.out.println("upto");
				
			 
				
						
						byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
						System.out.println("Length  " + buf.length);
						response.setContentType("application/pdf");
						response.setContentLength(buf.length);
					
						response.setHeader("Content-Disposition",
								"attachment;filename=\"FundReceiptPayment.pdf\"");
						OutputStream out = response.getOutputStream();
						out.write(buf, 0, buf.length);
						out.close();
				
					
					
				
			} catch (Exception ex) {
				String connectMsg = "Could not create the report "
						+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
				System.out.println("connectMsg"+connectMsg);
			}
		}
}


