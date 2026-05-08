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

import Servlets.FAS.FAS1.CommonControls.servlets.Com_CashBook1;
import Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_DETAILS;
import Servlets.FAS.FAS1.ReceiptSystem.servlets.SL_TYPE_CODE_NAME_GENERAL;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class Accounts_Data
 */
public class Accounts_Data extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
	Connection connection = null;

	/**
	 * @see HttpServlet#HttpServlet()
	 */
	public Accounts_Data() {
		super();
		// TODO Auto-generated constructor stub
	}

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse
	 *      response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

        /**
       * Session Checking
       */

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


        /**
        * Varialbes Declaration
        */

        Connection con = null;
        ResultSet rs = null, rs2 = null, rs3 = null, rs4 = null;
        PreparedStatement ps = null, ps2 = null, ps3 = null, ps4 = null;
        String xml = "";
        String strCommand = "";
        String from_year = "",to_year = "";
      

        
        /**
        *  Database Connection
        */

        try {
            ResourceBundle rs1 =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
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
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);
            //sendMessage(response,"probably Failed to Establish connection to the database server.. due to "+e,"ok");

        }


        /**
         * Content Type Setting
         */

        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        /*PrintWriter out = response.getWriter();*/


        /**
      *  Get Command
      */

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        
        /** Get from_year */

        try {
        	from_year =request.getParameter("from_year");
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("from_year " + from_year);


        /** Get to_year */
       
        try {
        	to_year =request.getParameter("to_year");
        } catch (NumberFormatException e) {
            System.out.println("exception" + e);
        }
        System.out.println("to_year " + to_year);


        /**
       *  Load Voucher Number and Corresponding Details
       */

        if (strCommand.equalsIgnoreCase("load_Account_Head")) {

            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
          
            xml = "<response><command>load_Account_Head</command>";

            try {
            	String headcodeQuery = ""
            			+ "SELECT account_head_code "
            			+ "FROM "
            			+ "  (SELECT DISTINCT account_head_code "
            			+ "  FROM "
            			+ "    (SELECT DISTINCT ftbr.account_head_code "
            			+ "    FROM FAS_TRIAL_BALANCE FTBR "
            			+ "    WHERE (ftbr.cashbook_year      =? "
            			+ "    AND ftbr.cashbook_month       >=4 "
            			+ "    AND FTBR.CASHBOOK_MONTH       <=12 "
            			+ "    AND FTBR.CURRENT_MONTH_CREDIT! =0 "
            			+ "    AND FTBR.CURRENT_MONTH_DEBIT!  =0) "
            			+ "    OR (ftbr.cashbook_year         =? "
            			+ "    AND ftbr.cashbook_month       >=1 "
            			+ "    AND FTBR.CASHBOOK_MONTH       <=3 "
            			+ "    AND FTBR.ACCOUNT_HEAD_CODE    != 0 "
            			+ "    AND (FTBR.CURRENT_MONTH_CREDIT!=0 "
            			+ "    OR ftbr.current_month_debit!   =0)) "
            			+ "    UNION ALL "
            			+ "    SELECT DISTINCT ftbs.account_head_code "
            			+ "    FROM FAS_TRIAL_BALANCE_SUPPLEMENT FTBS "
            			+ "    WHERE FTBS.CASHBOOK_YEAR       =? "
            			+ "    AND (FTBS.CURRENT_MONTH_CREDIT!=0 "
            			+ "    OR FTBS.CURRENT_MONTH_DEBIT!   =0) "
            			+ "    ) X "
            			+ "  ) y "
            			+ "WHERE account_head_code NOT IN "
            			+ "  (SELECT HOA "
            			+ "  FROM FAS_HO_ANNUALGROUPING "
            			+ "  WHERE finyearstart=? "
            			+ "  AND finyearend    =? "
            			+ "  )";


            	System.out.println("headcodeQuery::"+headcodeQuery);
                ps =
  con.prepareStatement(headcodeQuery);
             
               
                
                ps.setString(1, from_year);
                ps.setString(2, to_year);
                ps.setString(3, to_year);
                ps.setString(4, from_year);
                ps.setString(5, to_year);
               
                
                rs = ps.executeQuery();

                int count = 0;
                while (rs.next()) {
                    xml =
 xml + "<account_head_code>" + rs.getInt("account_head_code") + "</account_head_code>";
                    count++;
                }
                if (count == 0)
                {
                    xml = xml + "<flag>success</flag>";
                                        
                }
                else
                    xml = xml + "<flag>failure</flag>";
                System.out.println("count  " + count);
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load VOUCHER." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            PrintWriter out = response.getWriter();
            out.println(xml);

        }
        
        
        
		if (strCommand.equalsIgnoreCase("Abstract")) {

			JasperDesign jasperDesign = null;
			File reportFile = null;
			String command = "", fin_year = "";
			String year[] = null;
			String cont_path = "";
			String Re_Type = request.getParameter("btnRdo");
			System.out.println(Re_Type);
			fin_year = request.getParameter("txtfin_year");
			System.out.println("fin_year >>>>" + fin_year);
			year = fin_year.split("-");
			/*
			 * f_year=(Integer.parseInt(year[0])-1)+"-"+(Integer.parseInt(year[1
			 * ])-1 ); System.out.println(" Ref_year >>>  "+f_year);
			 */

			try {
				// cont_path=request.getRequestURL().toString();

				reportFile = new File(getServletContext()
						.getRealPath("/org/FAS/FAS1/MIS/jaspers/twadFas/Accountheadgroupfinal.jasper"));
				String path = getServletContext()
						.getRealPath("/org/FAS/FAS1/MIS/jaspers/twadFas/Accountheadgroupfinal.jasper");
				String ctxpath = path.substring(0, path.lastIndexOf("Accountheadgroupfinal.jasper"));
				System.out.println("ctxpath..... " + ctxpath);
				System.out.println(" Report File >>>  " + reportFile);
				/* String head="INCOME AND EXPENDITURE STATEMENT"; */

				if (!reportFile.exists())
					throw new JRRuntimeException("File J not found. The report design must be compiled first.");
				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
				Map map = null;
				map = new HashMap();
				map.put("frm_year", year[0]);
				map.put("to_year", year[1]);

				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);
				if (Re_Type.equalsIgnoreCase("HTML")) {
					response.setContentType("text/html");
					response.setHeader("Content-Disposition", "attachment;filename=\"AssetConsolidate.html\"");
					PrintWriter out = response.getWriter();
					JRHtmlExporter exporter = new JRHtmlExporter();
					exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, false);
					exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
					exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
					exporter.exportReport();
					out.flush();
					out.close();
				} else if (Re_Type.equalsIgnoreCase("PDF")) {
					byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
					response.setContentType("application/pdf");
					response.setContentLength(buf.length);
					response.setHeader("Content-Disposition", "attachment;filename=\"AccountsAbstract.pdf\"");
					OutputStream out = response.getOutputStream();
					out.write(buf, 0, buf.length);
					out.close();
				} else if (Re_Type.equalsIgnoreCase("EXCEL")) {
					// response.sendRedirect("org/FAS/FAS1/MIS/jsps/TS_FAS.jsp");
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition", "attachment;filename=\"AssetConsolidate.xls\"");
					JRXlsExporter exporterXLS = new JRXlsExporter();
					exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);

					ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
					exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, xlsReport);
					exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
					exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
					exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
					exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
					exporterXLS.exportReport();
					byte[] bytes;
					bytes = xlsReport.toByteArray();
					ServletOutputStream ouputStream = response.getOutputStream();
					ouputStream.write(bytes, 0, bytes.length);
					ouputStream.flush();
					ouputStream.close();
				}
			} catch (Exception ex) {
				String connectMsg = "Could not create the report " + ex.getMessage();
				String con_err = "Could not create the report " + ex;
				System.out.println(con_err);
				System.out.println(connectMsg);
			}
		}
        

	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse
	 *      response) Developed by Nandakumar 24Oct2019
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {

		// System.out.println("Servlet Page ... ");
		CallableStatement stmt = null;
		Connection connection = null;

		String xml = "";
		String strCommand = "";
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

			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
			Class.forName(strDriver.trim());
			connection = DriverManager.getConnection(ConnectionString, strdbusername.trim(), strdbpassword.trim());
		} catch (Exception ex) {
			String connectMsg = "Could not create the connection" + ex.getMessage() + " " + ex.getLocalizedMessage();
			System.out.println(connectMsg);
		}

		/**
		 * GET COMMAND
		 */

		try {

			strCommand = request.getParameter("Command");
			System.out.println("assign..here command..." + strCommand);
		} catch (Exception e) {
			System.out.println("Exception in assigning..." + e);
		}

		if (strCommand.equalsIgnoreCase("Abstract")) {

			JasperDesign jasperDesign = null;
			File reportFile = null;
			String command = "", fin_year = "";
			String year[] = null;
			String cont_path = "";
			String Re_Type = request.getParameter("btnRdo");
			System.out.println(Re_Type);
			fin_year = request.getParameter("txtfin_year");
			System.out.println("fin_year >>>>" + fin_year);
			year = fin_year.split("-");
			/*
			 * f_year=(Integer.parseInt(year[0])-1)+"-"+(Integer.parseInt(year[1
			 * ])-1 ); System.out.println(" Ref_year >>>  "+f_year);
			 */

			try {
				
				
				
				if (Re_Type.equalsIgnoreCase("PDF")) {
					// cont_path=request.getRequestURL().toString();

					reportFile = new File(getServletContext()
							.getRealPath("/org/FAS/FAS1/MIS/jaspers/twadFas/Accountheadgroupfinal.jasper"));
					String path = getServletContext()
							.getRealPath("/org/FAS/FAS1/MIS/jaspers/twadFas/Accountheadgroupfinal.jasper");
					String ctxpath = path.substring(0, path.lastIndexOf("Accountheadgroupfinal.jasper"));
					System.out.println("ctxpath..... " + ctxpath);
					System.out.println(" Report File >>>  " + reportFile);
					/* String head="INCOME AND EXPENDITURE STATEMENT"; */
				}
				
				else if (Re_Type.equalsIgnoreCase("EXCEL")) {
					// cont_path=request.getRequestURL().toString();

					reportFile = new File(getServletContext()
							.getRealPath("/org/FAS/FAS1/MIS/jaspers/twadFas/AccountheadgroupExcelfinal.jasper"));
					String path = getServletContext()
							.getRealPath("/org/FAS/FAS1/MIS/jaspers/twadFas/AccountheadgroupExcelfinal.jasper");
					String ctxpath = path.substring(0, path.lastIndexOf("AccountheadgroupExcelfinal.jasper"));
					System.out.println("ctxpath..... " + ctxpath);
					System.out.println(" Report File >>>  " + reportFile);
					/* String head="INCOME AND EXPENDITURE STATEMENT"; */
				}
				

				if (!reportFile.exists())
					throw new JRRuntimeException("File J not found. The report design must be compiled first.");
				JasperReport jasperReport = (JasperReport) JRLoader.loadObject(reportFile.getPath());
				Map map = null;
				map = new HashMap();
				map.put("frm_year", year[0]);
				map.put("to_year", year[1]);

				JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);
				if (Re_Type.equalsIgnoreCase("HTML")) {
					response.setContentType("text/html");
					response.setHeader("Content-Disposition", "attachment;filename=\"AssetConsolidate.html\"");
					PrintWriter out = response.getWriter();
					JRHtmlExporter exporter = new JRHtmlExporter();
					exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN, false);
					exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
					exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
					exporter.exportReport();
					out.flush();
					out.close();
				} else if (Re_Type.equalsIgnoreCase("PDF")) {
					byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
					response.setContentType("application/pdf");
					response.setContentLength(buf.length);
					response.setHeader("Content-Disposition", "attachment;filename=\"AccountsAbstract.pdf\"");
					OutputStream out = response.getOutputStream();
					out.write(buf, 0, buf.length);
					out.close();
				} else if (Re_Type.equalsIgnoreCase("EXCEL")) {
					// response.sendRedirect("org/FAS/FAS1/MIS/jsps/TS_FAS.jsp");
					response.setContentType("application/vnd.ms-excel");
					response.setHeader("Content-Disposition", "attachment;filename=\"AccountsAbstract.xls\"");
					JRXlsExporter exporterXLS = new JRXlsExporter();
					exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint);

					ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
					exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM, xlsReport);
					exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE);
					exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE);
					exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE);
					exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE);
					exporterXLS.exportReport();
					byte[] bytes;
					bytes = xlsReport.toByteArray();
					ServletOutputStream ouputStream = response.getOutputStream();
					ouputStream.write(bytes, 0, bytes.length);
					ouputStream.flush();
					ouputStream.close();
				}
			} catch (Exception ex) {
				String connectMsg = "Could not create the report " + ex.getMessage();
				String con_err = "Could not create the report " + ex;
				System.out.println(con_err);
				System.out.println(connectMsg);
			}
		}
	}

}
