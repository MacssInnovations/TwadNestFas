package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.SQLException;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver;

import java.sql.*;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.Map;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JRDesignQuery;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class TrialBalanceSummaryFormat2 extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = -7612196366306701598L;
	private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doPost(HttpServletRequest request,HttpServletResponse response) throws ServletException,
                                                            IOException {


        /**
         * Variables Declaration
         */
        Connection connection = null;


        /**
         * Database Connection
         */
        try {
            LoadDriver load = new LoadDriver();
            connection = load.getConnection();
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }


        /**
         * Session Checking
         */
        response.setContentType(CONTENT_TYPE);
        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        /**
        *  Get Parameters
        */

        /** Get User ID */
        String userid = (String)session.getAttribute("UserId");

        /** Get Accounting Unit ID */
        String Account_unit_Code = request.getParameter("cmbAcc_UnitCode");

        /** Get From Cashbook Year */
        String from_CashBook_Year = request.getParameter("from_txtCB_Year");

        /** Get From Cashbook Month */
        String from_CashBook_Month = request.getParameter("from_txtCB_Month");

        /** Get To Cashbook Year */
        String to_CashBook_Year = request.getParameter("to_txtCB_Year");

        /** Get To Cashbook Month */
        String to_CashBook_Month = request.getParameter("to_txtCB_Month");


        /** Variables Declaration */
        int AccountUnitCode = 0;
        int OfficeCode = 0;
        int from_CashBookYear = 0;
        int from_CashBookMonth = 0;
        int to_CashBookYear = 0;
        int to_CashBookMonth = 0;


        /** Convert String data into Integer Data */
        try {
            AccountUnitCode = Integer.parseInt(Account_unit_Code);

            from_CashBookYear = Integer.parseInt(from_CashBook_Year);
            from_CashBookMonth = Integer.parseInt(from_CashBook_Month);
            to_CashBookYear = Integer.parseInt(to_CashBook_Year);
            to_CashBookMonth = Integer.parseInt(to_CashBook_Month);


            System.out.println("Account_Unit_Code After is:" +
                               AccountUnitCode);
            System.out.println("Office_Code After is:" + OfficeCode);
            System.out.println("CashBook_Year After is:" + from_CashBookYear);
            System.out.println("CashBook Month After is:" +
                               from_CashBookMonth);


        } catch (Exception e) {
            System.out.println("Exception in Converting Integer:" + e);
        }

        Map<Integer,String> monthMap = new LinkedHashMap<Integer, String>();
        monthMap.put(1, "January");
        monthMap.put(2, "February");
        monthMap.put(3, "March");
        monthMap.put(4, "April");
        monthMap.put(5, "May");
        monthMap.put(6, "June");
        monthMap.put(7, "July");
        monthMap.put(8, "August");
        monthMap.put(9, "September");
        monthMap.put(10, "October");
        monthMap.put(11, "November");
        monthMap.put(12, "December");

        /**
         *  Report Generation
         */

        try {
 
                String s="",myQry="";

                Map map = null;
                map = new HashMap();
                map.put("from_cashbookyear", from_CashBookYear);
                map.put("from_cashbookmonth", from_CashBookMonth);
                map.put("to_cashbookyear", to_CashBookYear);
                map.put("to_cashbookmonth", to_CashBookMonth);
                map.put("accountingunitid", AccountUnitCode);
                map.put("from_monthvalue", monthMap.get(from_CashBookMonth));
                map.put("to_monthvalue", monthMap.get(to_CashBookMonth));
                myQry="SELECT ACCOUNTING_UNIT_ID,ACCOUNT_HEAD_CODE,janDR,janCR,febDR,febCR,marDR,marCR,aprDR,aprCR, " +
                "  mayDR,mayCR,junDR,junCR,julDR,julCR,augDR,augCR,sepDR,sepCR,octDR,octCR,novDR,novCR,decDR,decCR, " +
                "  debitsup1,creditsup1,debitsup2,creditsup2,account_head_desc,accounting_unit_name, " +
                "  (janDR+febDR+marDR+aprDR+mayDR+junDR+julDR+augDR+sepDR+octDR+novDR+decDR+debitsup1+debitsup2)   AS netDR, " +
                "  (janCR+febCR+marCR+aprCR+mayCR+junCR+julCR+augCR+sepCR+octCR+novCR+decCR+creditsup1+creditsup2) AS netCR " +
                "FROM " +
                "  (SELECT ACCOUNTING_UNIT_ID,ACCOUNT_HEAD_CODE,MAX(janDR) AS janDR,MAX(janCR) AS janCR,MAX(febDR) AS febDR, " +
                "    MAX(febCR) AS febCR,MAX(marDR) AS marDR,MAX(marCR) AS marCR,MAX(aprDR) AS aprDR,MAX(aprCR) AS aprCR,MAX(mayDR) AS mayDR, " +
                "    MAX(mayCR) AS mayCR,MAX(junDR) AS junDR,MAX(junCR) AS junCR,MAX(julDR) AS julDR,MAX(julCR) AS julCR,MAX(augDR) AS augDR, " +
                "    MAX(augCR) AS augCR,MAX(sepDR) AS sepDR,MAX(sepCR) AS sepCR,MAX(octDR) AS octDR,MAX(octCR) AS octCR,MAX(novDR) AS novDR, " +
                "    MAX(novCR) AS novCR,MAX(decDR) AS decDR,MAX(decCR) AS decCR,coalesce(MAX(debitsup1),0) AS debitsup1, coalesce(MAX(creditsup1),0) AS creditsup1, " +
                "    coalesce(MAX(debitsup2),0) AS debitsup2,coalesce(MAX(creditsup2),0) AS creditsup2,account_head_desc,accounting_unit_name " +
                "  FROM " +
                "    (SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,ACCOUNT_HEAD_CODE, " +
                "      CASE WHEN(CASHBOOK_MONTH='1') THEN coalesce(SUM(CURRENT_MONTH_DEBIT),0) ELSE 0 END AS janDR, " +
                "      CASE WHEN(CASHBOOK_MONTH='1') THEN coalesce(SUM(CURRENT_MONTH_CREDIT),0) ELSE 0 END AS janCR, " +
                "      CASE WHEN(CASHBOOK_MONTH='2') THEN coalesce(SUM(CURRENT_MONTH_DEBIT),0) ELSE 0 END AS febDR, " +
                "      CASE WHEN(CASHBOOK_MONTH='2') THEN coalesce(SUM(CURRENT_MONTH_CREDIT),0) ELSE 0 END AS febCR, " +
                "      CASE WHEN(CASHBOOK_MONTH='3') THEN coalesce(SUM(CURRENT_MONTH_DEBIT),0) ELSE 0 END AS marDR, " +
                "      CASE WHEN(CASHBOOK_MONTH='3') THEN coalesce(SUM(CURRENT_MONTH_CREDIT),0) ELSE 0 END AS marCR, " +
                "      CASE WHEN(CASHBOOK_MONTH='4') THEN coalesce(SUM(CURRENT_MONTH_DEBIT),0) ELSE 0 END AS aprDR, " +
                "      CASE WHEN(CASHBOOK_MONTH='4') THEN coalesce(SUM(CURRENT_MONTH_CREDIT),0) ELSE 0 END AS aprCR, " +
                "      CASE WHEN(CASHBOOK_MONTH='5') THEN coalesce(SUM(CURRENT_MONTH_DEBIT),0) ELSE 0 END AS mayDR, " +
                "      CASE WHEN(CASHBOOK_MONTH='5') THEN coalesce(SUM(CURRENT_MONTH_CREDIT),0) ELSE 0 END AS mayCR, " +
                "      CASE WHEN(CASHBOOK_MONTH='6') THEN coalesce(SUM(CURRENT_MONTH_DEBIT),0) ELSE 0 END AS junDR, " +
                "      CASE WHEN(CASHBOOK_MONTH='6') THEN coalesce(SUM(CURRENT_MONTH_CREDIT),0) ELSE 0 END AS junCR, " +
                "      CASE WHEN(CASHBOOK_MONTH='7') THEN coalesce(SUM(CURRENT_MONTH_DEBIT),0) ELSE 0 END AS julDR, " +
                "      CASE WHEN(CASHBOOK_MONTH='7') THEN coalesce(SUM(CURRENT_MONTH_CREDIT),0) ELSE 0 END AS julCR, " +
                "      CASE WHEN(CASHBOOK_MONTH='8') THEN coalesce(SUM(CURRENT_MONTH_DEBIT),0) ELSE 0 END AS augDR, " +
                "      CASE WHEN(CASHBOOK_MONTH='8') THEN coalesce(SUM(CURRENT_MONTH_CREDIT),0) ELSE 0 END AS augCR, " +
                "      CASE WHEN(CASHBOOK_MONTH='9') THEN coalesce(SUM(CURRENT_MONTH_DEBIT),0) ELSE 0 END AS sepDR, " +
                "      CASE WHEN(CASHBOOK_MONTH='9') THEN coalesce(SUM(CURRENT_MONTH_CREDIT),0) ELSE 0 END AS sepCR, " +
                "      CASE WHEN(CASHBOOK_MONTH='10') THEN coalesce(SUM(CURRENT_MONTH_DEBIT),0) ELSE 0 END AS octDR, " +
                "      CASE WHEN(CASHBOOK_MONTH='10') THEN coalesce(SUM(CURRENT_MONTH_CREDIT),0) ELSE 0 END AS octCR, " +
                "      CASE WHEN(CASHBOOK_MONTH='11') THEN coalesce(SUM(CURRENT_MONTH_DEBIT),0) ELSE 0 END AS novDR, " +
                "      CASE WHEN(CASHBOOK_MONTH='11') THEN coalesce(SUM(CURRENT_MONTH_CREDIT),0) ELSE 0 END AS novCR, " +
                "      CASE WHEN(CASHBOOK_MONTH='12') THEN coalesce(SUM(CURRENT_MONTH_DEBIT),0) ELSE 0 END AS decDR, " +
                "      CASE WHEN(CASHBOOK_MONTH='12') THEN coalesce(SUM(CURRENT_MONTH_CREDIT),0) ELSE 0 END AS decCR " +
                "  FROM FAS_TRIAL_BALANCE "+
                " WHERE ACCOUNTING_UNIT_ID ='"+AccountUnitCode+"' "+
                " AND to_date(cashbook_month "+
                 " ||'-'|| + cashbook_year, 'mm-yyyy') BETWEEN to_date( '"+from_CashBookMonth+"' "+
                 " || '-' "+
                 " || '"+from_CashBookYear+"', 'mm-yyyy') "+
               " AND to_date( '"+to_CashBookMonth+"' "+
                 " ||'-' "+
                 " || '"+to_CashBookYear+"' , 'mm-yyyy') "+
                "    GROUP BY ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,ACCOUNT_HEAD_CODE,CASHBOOK_MONTH " +
                "    ORDER BY ACCOUNT_HEAD_CODE " +
                "    )a " +
                "  LEFT OUTER JOIN " +
                "    (SELECT account_head_desc,account_head_code AS achcode " +
                "    FROM com_mst_account_heads " +
                "    )b " +
                "  ON a.ACCOUNT_HEAD_CODE=b.achcode " +
                "  LEFT OUTER JOIN " +
                "    (SELECT accounting_unit_id AS aunitid,accounting_unit_name " +
                "    FROM fas_mst_acct_units " +
                "    )c " +
                "  ON a.ACCOUNTING_UNIT_ID=c.aunitid " +
                "  LEFT OUTER JOIN " +
                "    (SELECT ACCOUNTING_UNIT_ID AS unitid,ACCOUNTING_FOR_OFFICE_ID AS officeid,ACCOUNT_HEAD_CODE        AS headcode, " +
                "      CASE WHEN(SUPPLEMENT_NO='1') THEN coalesce(SUM(CURRENT_MONTH_DEBIT),0) ELSE 0 END AS debitsup1, " +
                "      CASE WHEN(SUPPLEMENT_NO='2') THEN coalesce(SUM(CURRENT_MONTH_DEBIT),0) ELSE 0 END AS debitsup2, " +
                "      CASE WHEN(SUPPLEMENT_NO='1') THEN coalesce(SUM(CURRENT_MONTH_CREDIT),0) ELSE 0 END AS creditsup1, " +
                "      CASE WHEN(SUPPLEMENT_NO='2') THEN coalesce(SUM(CURRENT_MONTH_CREDIT),0) ELSE 0 END AS creditsup2 " +
                "    FROM FAS_TRIAL_BALANCE_SUPPLEMENT " +
                "    GROUP BY ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,ACCOUNT_HEAD_CODE,SUPPLEMENT_NO " +
                "    )d " +
                "  ON a.ACCOUNTING_UNIT_ID       =d.unitid " +
                "  AND a.ACCOUNTING_FOR_OFFICE_ID=d.officeid " +
                "  AND a.ACCOUNT_HEAD_CODE       =d.headcode " +
                "  GROUP BY ACCOUNTING_UNIT_ID,ACCOUNT_HEAD_CODE,account_head_desc,accounting_unit_name " +
                "  ORDER BY ACCOUNT_HEAD_CODE " +
                "  ) as opt1";
                s=request.getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Trial_balance_summary_format2.jrxml");
        		String output="pdf";
        		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
        		System.out.println("apsk86"+myQry);
        		JRDesignQuery query=new JRDesignQuery();
        		query.setText(myQry);
        		jasperDesign.setQuery(query);
        		JasperReport jasperReport = JasperManager.compileReport(jasperDesign);
        		JasperPrint jasperPrint = JasperManager.fillReport(jasperReport, map, connection);
        		ByteArrayOutputStream bout=new ByteArrayOutputStream();
            	if(output.equalsIgnoreCase("pdf")){
		            	OutputStream os=response.getOutputStream();
		            	response.setContentType("application/pdf");
		            	response.setHeader ("Content-Disposition", "attachment;filename=\"Trial_balance_summary_format2.pdf\"");
		            	os.write(JasperManager.printReportToPdf(jasperPrint));
		            	os.close();
            	}else if(output.equalsIgnoreCase("excel")){
            			response.setContentType("application/vnd.ms-excel");
            			response.setHeader ("Content-Disposition", "attachment;filename=\"Trial_balance_summary_format2.xls\"");
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
	            		 response.setHeader ("Content-Disposition", "attachment;filename=\"Trial_balance_summary_format2.html\"");
	            		 PrintWriter out = response.getWriter();
	            		 JRHtmlExporter exporter = new JRHtmlExporter();            		
	            		 exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
	            		 exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
	            		 exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
	            		 exporter.exportReport();
	            		 out.flush();
	            		 out.close();
            		}

            


        } catch (Exception e) {
          e.printStackTrace();
            try {
                connection.rollback();
            } catch (SQLException e1) {
                System.out.println("catch:" + e1);
            }
            String msg = "Trial Balance Has failed to Update";
            msg = msg + "<br><br>";
            sendMessage(response, msg, "ok");

        }
    }


    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                "org/Library/jsps/Messenger.jsp?message=" + msg + "&button=" +
                bType;
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println("ERROR");
        }
    }


}
