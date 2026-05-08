package Servlets.FAS.FAS1.AdjustmentMemoNew;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;
import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.util.Calendar;
import java.util.Date;
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

/**
 * Servlet implementation class Adj_Memo_SingleReceipt_Creation
 */
public class SingleUnit_multipleReceipt_Report1 extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

	protected void doGet(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
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
			// sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

		}

		
		/*@NK on 15jul2020*/
		String cmnd="";
		String xml="";
		String emp_name="";
		 PrintWriter pw=response.getWriter();
		int emp_id=0;
		 ResultSet rs2=null;
         PreparedStatement  ps2=null;
		 try
         {
                   cmnd =  request.getParameter("command");     
                   System.out.println("Command passed via the button pressed : " + cmnd);
                   
         }
           catch(Exception e3)
           {
             e3.printStackTrace();
           }
		 if(cmnd.equalsIgnoreCase("loadempdetails")) 
         {
			 xml="<response>";
               emp_id=Integer.parseInt(request.getParameter("emp_id"));
               
             xml=xml+"<command>loadempdetails</command>";
             try
             {  
            	 int count=0;
            	 String sqlCategory = ""
            			 + "SELECT * "
            			 + "FROM "
            			 + "  (SELECT * "
            			 + "  FROM "
            			 + "    (SELECT 'Service Particulars' AS Category, "
            			 + "      employee_id, "
            			 + "      designation_id, "
            			 + "      office_id, "
            			 + "      office_dept_id, "
            			 + "      employee_status_id, "
            			 + "      process_flow_status_id "
            			 + "    FROM hrm_emp_service_data "
            			 + "    WHERE employee_id         = "+emp_id
            			 + "    AND process_flow_status_id='FR' "
            			 + "    ) "
            			 + "  UNION "
            			 + "  SELECT 'Current Posting' AS Category, "
            			 + "    employee_id, "
            			 + "    designation_id, "
            			 + "    office_id, "
            			 + "    department_id AS office_dept_id, "
            			 + "    employee_status_id, "
            			 + "    process_flow_status_id "
            			 + "  FROM hrm_emp_current_posting "
            			 + "  WHERE employee_id= "+emp_id
            			 + "  ) "
            			 + "ORDER BY category DESC";
            	 System.out.println("sql is : " + sqlCategory);
            	 ps2 = con.prepareStatement(sqlCategory);
                 rs2=ps2.executeQuery();
                 while(rs2.next())
                 {
                	int officeId=rs2.getInt("office_id");
                	if( officeId==5000)
                	{
                		count++;
                	}
                     
                 }
                   rs2.close();
                   ps2.close();
                   System.out.println("count is : " + count);
            	 if( count>0)
             {
            	 
             
                         String sqlload="select a.EMPLOYEE_ID,a.OFFICE_ID,b.employee_name, c.OFFICE_SHORT_NAME,d.DESIGNATION from "+
                                              "   ( "+
                                              "     select EMPLOYEE_ID,OFFICE_ID,DESIGNATION_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=? and EMPLOYEE_STATUS_ID='WKG' "+
                                              "   )a left outer join "+
                                               "  ( "+
                                              "     select  EMPLOYEE_ID,EMPLOYEE_INITIAL||' '||EMPLOYEE_NAME as employee_name from HRM_MST_EMPLOYEES "+
                                              "   )b on a.EMPLOYEE_ID=b.EMPLOYEE_ID left outer join "+
                                             "    ( "+
                                             "      select OFFICE_ID,OFFICE_SHORT_NAME from COM_MST_OFFICES "+
                                             "    )c on a.OFFICE_ID=c.OFFICE_ID left outer join "+
                                              "   ( "+
                                             "      select DESIGNATION_ID,DESIGNATION from HRM_MST_DESIGNATIONS "+
                                              "   )d on a.DESIGNATION_ID=d.DESIGNATION_ID"              ;
                         ps2 = con.prepareStatement(sqlload);
                         ps2.setInt(1,emp_id);
                         rs2=ps2.executeQuery();
                         if(rs2.next())
                         {
                             emp_name=rs2.getString("employee_name");
                             xml=xml+"<desig_name>"+rs2.getString("DESIGNATION")+"</desig_name>";
                             xml=xml+"<OFFICE_ID>"+rs2.getString("OFFICE_ID")+"</OFFICE_ID>";
                             xml=xml+"<office_name>"+rs2.getString("OFFICE_SHORT_NAME")+"</office_name>";                                       
                             xml=xml+"<emp_name>"+emp_name+"</emp_name>";
                             xml=xml+"<flag>success</flag>";
                         }
                         else
                         xml=xml+"<flag>nodata</flag>";
             }
            	 else
            		 xml=xml+"<flag>nodata</flag>";
              } 
               catch(Exception e)
               {
                                 xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                 
                                 System.out.println(e);
                }
             xml=xml+"</response>";
             System.out.println("xml is : " + xml);
             pw.write(xml);
             pw.flush();
             pw.close();
       }
		
/*@NK on 15jul2020*/


	}

	protected void doPost(HttpServletRequest request,
			HttpServletResponse response) throws ServletException, IOException {
		Connection con = null;
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
			// sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

		}

		String strCommand=null;
		try {

			strCommand = request.getParameter("Command");
			System.out.println("assign..here command..." + strCommand);

		}

		catch (Exception e) {
			System.out.println("Exception in assigning..." + e);
		}

		// -----------------------------------------------------------------------------------------------
		Calendar c;
		Date txtCrea_date = null;
		int txtCash_Month_hid = 0, txtCash_year = 0;

		
		// changes here

		int memo_advice_No1 = 0;

		if (strCommand.equalsIgnoreCase("REPORT")) {

			String[] sd = request.getParameter("txtCrea_date").split("/");
			c = new GregorianCalendar(Integer.parseInt(sd[2]), Integer
					.parseInt(sd[1]) - 1, Integer.parseInt(sd[0]));
			java.util.Date d = c.getTime();
			txtCrea_date = new Date(d.getTime());
			// System.out.println("txtCrea_date "+txtCrea_date);

			System.out.println("b4 getting month and year");
			try {
				txtCash_year = Integer.parseInt(sd[2]);
			} catch (Exception e) {
				System.out.println("exception" + e);
			}
			// System.out.println("txtCash_year "+txtCash_year);

			try {
				txtCash_Month_hid = Integer.parseInt(sd[1]);
			} catch (Exception e) {
				System.out.println("exception" + e);
			}
			// System.out.println("txtCash_Month_hid "+txtCash_Month_hid);
			memo_advice_No1 = Integer.parseInt(request.getParameter("cmbvocharNo"));
			//int serialno = Integer.parseInt(request.getParameter("serialNo"));
			int cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
			int office_id = Integer.parseInt(request.getParameter("office_id"));
			// System.out.println("enter into report function***************************");
			String rpttype=request.getParameter("rpttype");
			/*NK ON 02SEP20*/
			String signAutName=request.getParameter("signAutName");
			String signAutDesg=request.getParameter("signAutDesg");
			System.out.println("signAutName===>"+signAutName);
			System.out.println("signAutDesg===>"+signAutDesg);
			/*NK ON 02SEP20*/
			System.out.println("rpttype===>"+rpttype);

			String monthInWords = "";
			if (txtCash_Month_hid == 1)
				monthInWords = "January";
			else if (txtCash_Month_hid == 2)
				monthInWords = "February";
			else if (txtCash_Month_hid == 3)
				monthInWords = "March";
			else if (txtCash_Month_hid == 4)
				monthInWords = "April";
			else if (txtCash_Month_hid == 5)
				monthInWords = "May";
			else if (txtCash_Month_hid == 6)
				monthInWords = "June";
			else if (txtCash_Month_hid == 7)
				monthInWords = "July";
			else if (txtCash_Month_hid == 8)
				monthInWords = "August";
			else if (txtCash_Month_hid == 9)
				monthInWords = "September";
			else if (txtCash_Month_hid == 10)
				monthInWords = "October";
			else if (txtCash_Month_hid == 11)
				monthInWords = "November";
			else if (txtCash_Month_hid == 12)
				monthInWords = "December";
			
			String mn=monthInWords+"-"+txtCash_year;
		File reportFile = null;
		String path ="";
		String ctxpath="";
		try {
			
			if(rpttype.equalsIgnoreCase("CREDIT ADVICE REPORT")){
			reportFile = new File(getServletContext().getRealPath(
					"/org/FAS/FAS1/AdjustmentMemoNew/Reports/singleUnit_MultipleReceiptReport_New.jasper"));
			 path = getServletContext().getRealPath("/org/FAS/FAS1/AdjustmentMemoNew/Reports/singleUnit_MultipleReceiptReport_New.jasper");
			 ctxpath = path.substring(0, path.lastIndexOf("singleUnit_MultipleReceiptReport_New.jasper"));
			}else{
				reportFile = new File(getServletContext().getRealPath(
						"/org/FAS/FAS1/AdjustmentMemoNew/Reports/singleUnit_MultipleReceiptReport_New_Debit.jasper"));
				 path = getServletContext().getRealPath("/org/FAS/FAS1/AdjustmentMemoNew/Reports/singleUnit_MultipleReceiptReport_New_Debit.jasper");
				 ctxpath = path.substring(0, path.lastIndexOf("singleUnit_MultipleReceiptReport_New_Debit.jasper"));
			}
			
		    System.out.println("reportFile==>"+reportFile);
		    System.out.println("path==>"+path);
		    System.out.println("ctxpath==>"+ctxpath);
			
			
			if (!reportFile.exists())
				throw new JRRuntimeException(
						"File J not found. The report design must be compiled first.");

			
			
			
			JasperReport jasperReport = (JasperReport) JRLoader
					.loadObject(reportFile.getPath());
			Map map = null;
			map = new HashMap();
			System.out.println("cashbookyear==>"+txtCash_year);
			System.out.println("cashbookmonth==>"+txtCash_Month_hid);
			System.out.println("voucherNo==>"+memo_advice_No1);
			System.out.println("cmbAcc_UnitCode==>"+office_id);
			System.out.println("txtCrea_date==>"+txtCrea_date);
			
			map.put("cashbookyear", txtCash_year);
			map.put("cashbookmonth", txtCash_Month_hid);
			map.put("monthinwords", mn);
			map.put("monthinwords_New", monthInWords);
			map.put("voucherNo", memo_advice_No1);
		//	map.put("serialno", serialno);
			map.put("cmbAcc_UnitCode", office_id);
			map.put("txtCrea_date",txtCrea_date);	
			map.put("SUBREPORT_DIR", ctxpath);
			/*NK ON 02SEP20*/
			map.put("signAutName", signAutName);
			map.put("signAutDesg", signAutDesg);
			/*NK ON 02SEP20*/
			JasperPrint jasperPrint = JasperFillManager.fillReport(
					jasperReport, map, con);
			
			String rtype = "PDF";// request.getParameter("cmbReportType");
			System.out.println(rtype);
			if (rtype.equalsIgnoreCase("PDF")) {
				System.out.println(rtype);
				byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
				System.out.println("Length  " + buf.length);
				response.setContentType("application/pdf");
				response.setContentLength(buf.length);
				// response.setHeader("content-disposition",
				// "inline;filename=OpenActionItems.pdf");
				// response.setContentType("application/force-download");

				response.setHeader("Content-Disposition",
						"attachment;filename=\"Adjustment Memo.pdf\"");
				OutputStream out = response.getOutputStream();
				out.write(buf, 0, buf.length);
				out.close();
			} 
		} catch (Exception ex) {
			ex.printStackTrace();
			String connectMsg = "Could not create the report "
					+ ex.getMessage() + " uu " + ex.getLocalizedMessage();
			System.out.println(connectMsg);
		}


	}
		
		
		
		
}
}