package Servlets.FAS.FAS1.FundReceiptSystem.Reports;

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
 * Servlet implementation class Fund_Transfer_Reconciliation_atHo
 */
public class Fund_Transfer_Reconciliation_atHo extends HttpServlet {
    private static final long serialVersionUID = 1L;
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    /**
     * @see HttpServlet#HttpServlet()
     */
    public Fund_Transfer_Reconciliation_atHo() {
        super();
        // TODO Auto-generated constructor stub
    }

    /**
     * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doGet(HttpServletRequest request,
                         HttpServletResponse response) throws ServletException,
                                                              IOException {
      
      System.out.println("ttttttttttttttttttttttttt");
        PrintWriter out = response.getWriter();
        response.setHeader("cache-control","no-cache");
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        
        HttpSession session=request.getSession(false);
        try
        {            
              if(session==null)
              {
                       System.out.println(request.getContextPath()+"/index.jsp");
                       response.sendRedirect(request.getContextPath()+"/index.jsp");
                       return;
              }
              System.out.println(session);
                 
        }catch(Exception e)
        {
              System.out.println("Redirect Error :"+e);
        }
        
        
        /**
         * Variables Declaration 
        */                        
        Connection con=null;
        PreparedStatement ps2=null;        
        ResultSet rs2=null;
        String sql="";
                        
        /**
         * Database Connection 
        */
        try
        {
              ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
              String ConnectionString="";
              String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
              String strdsn=rs1.getString("Config.DSN");
              String strhostname=rs1.getString("Config.HOST_NAME");
              String strportno=rs1.getString("Config.PORT_NUMBER");
              String strsid=rs1.getString("Config.SID");
              String strdbusername=rs1.getString("Config.USER_NAME");
              String strdbpassword=rs1.getString("Config.PASSWORD");
              ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection              Class.forName(strDriver.trim());
              con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
        }
        catch(Exception e)
        {
                  System.out.println("Exception in opening connection :"+e);
        }
        
        
        int count=0,cmbAcc_UnitCode=0;
        String xml=null,cmd="",option="";
        
        /** Get Employee ID */
        try{cmd=request.getParameter("command");}
        catch(Exception e){System.out.println(e);}
        
       
        try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
        catch(Exception e){System.out.println(e);}
        
        xml="<response>";
        if(cmd.equalsIgnoreCase("loadBankWise"))
        {
           
              xml=xml+"<command>loadBankWise</command>";

            String getbankAccNo="select b.BANK_AC_NO,c.BANK_SHORT_NAME || '-' || b.BANK_AC_TYPE_ID ||'-'|| b.BANK_AC_NO  as bankShow from FAS_OFFICE_BANK_AC_CURRENT b,FAS_MST_BANKS c "+
                                " where b.BANK_ID=c.BANK_ID and b.ACCOUNTING_UNIT_ID=? and MODULE_ID=? and CR_DR_TYPE=? and b.STATUS='Y' ";
            System.out.println(getbankAccNo);
         
              try
              {
                       ps2=con.prepareStatement(getbankAccNo);
                          ps2.setInt(1,cmbAcc_UnitCode);
                          ps2.setString(2,"MF009");        // Here we loading Bank receipt Account numbers only with indicator "DR",bcoz we r going to remit tghe amount based on it only.
                          ps2.setString(3,"DR");
                       rs2=ps2.executeQuery();                                 
                       while(rs2.next()) 
                       {
                               xml+= "<bankno>"+ rs2.getLong("BANK_AC_NO")+"</bankno>";  
                               xml+= "<bankShow>"+ rs2.getString("bankShow") +"</bankShow>";                                                                                 
                               count++;
                       }                                                
                       if(count==0)
                               xml+="<flag>NoData</flag>";                                                   
                       else               
                               xml+="<flag>success</flag>";
                                   
              }
              catch(Exception e) 
              {
                       System.out.println("Exception in loadBankWise..."+e);
                       xml+="<flag>"+e.getMessage()+"</flag>";
              }                      
        }
        xml=xml+"</response>";
        System.out.println(xml);
        out.println(xml);
        out.close();
      
      
    }

    /**
     * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
     */
    protected void doPost(HttpServletRequest request,
                          HttpServletResponse response) throws ServletException,
                                                               IOException {

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


        /**
       *  Variables Declaration
       */

        String selstr = "";
        String selspestr = "";
        String sel = "";
        String opt = "";
        response.setContentType(CONTENT_TYPE);
        Connection connection = null;

        /**
         * Retrieving Configuration Values
         */

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


        /**
        * Report Calling
        */

        JasperDesign jasperDesign = null;
        File reportFile = null;

        try {
            System.out.println("calling servlet");

            /** Get Cash Book Month and Year */
            String txtCB_Year = request.getParameter("txtCB_Year");
            String txtCB_Month = request.getParameter("txtCB_Month");


            /** Find Whether report should be either html or text or pdf */
            String rtype = request.getParameter("txtoption");

            String Specifictype = request.getParameter("seletype");


            /** Convert Acciunting Unid Id and Office Id from String to Integer */


            /** CONVERT CASH BOOK MONTH AND YEAR FROM STRING TO INTEGER */
            int year = Integer.parseInt(txtCB_Year);
            int month = Integer.parseInt(txtCB_Month);

            /** Convert months in numbers to words for from cash book month */
            String monthInWords = "";
            if (month == 1)
                monthInWords = "January";
            else if (month == 2)
                monthInWords = "February";
            else if (month == 3)
                monthInWords = "March";
            else if (month == 4)
                monthInWords = "April";
            else if (month == 5)
                monthInWords = "May";
            else if (month == 6)
                monthInWords = "June";
            else if (month == 7)
                monthInWords = "July";
            else if (month == 8)
                monthInWords = "August";
            else if (month == 9)
                monthInWords = "September";
            else if (month == 10)
                monthInWords = "October";
            else if (month == 11)
                monthInWords = "November";
            else if (month == 12)
                monthInWords = "December";

            int txtRegionId = 0;
            try {
                txtRegionId =
                        Integer.parseInt(request.getParameter("txtRegionId"));
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtRegionId " + txtRegionId);
            String displayingOrder = "";
            try {
                displayingOrder = request.getParameter("displayingOrder");
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("displayingOrder " + displayingOrder);

            /** Get Bank Account Number */
            String txtBankAccountNo = "";
            try {
                txtBankAccountNo = request.getParameter("txtBankAccountNo");
            } catch (Exception e) {
                System.out.println("exception" + e);
            }
            System.out.println("txtBankAccountNo " + txtBankAccountNo);

            String splCondition = "";
            String splConditionOne = "";

            String sql_Region_Wise = " ";
            String sql_Region_WiseOne = " ";

            if (txtRegionId == 5000) {
                sql_Region_Wise = " and ftrn.accounting_for_office_id  in (5000) ";
                sql_Region_WiseOne =
                        " and accounting_for_office_id  in (5000) ";
            } else {
                sql_Region_Wise =
                        "					                  " + "  and ftrn.TRANSFER_TO_OFFICE_ID in		                	\n" +
                        "   (							         	\n" +
                        "     select office_id						        \n" +
                        "      from COM_MST_ALL_OFFICES_VIEW			                \n" +
                        "      where region_office_id = " + txtRegionId +
                        ")                     \n" +
                        "									\n";


                sql_Region_WiseOne =
                        "					                  " + "  and TRANSFER_TO_OFFICE_ID in		                	\n" +
                        "   (							         	\n" +
                        "     select office_id						        \n" +
                        "      from COM_MST_ALL_OFFICES_VIEW			                \n" +
                        "      where region_office_id = " + txtRegionId +
                        ")                     \n" +
                        "									\n";

            }


            if (displayingOrder.equalsIgnoreCase("RW")) {
                if (txtRegionId == 101) {
                    /** Display All Units */
                    splCondition = "";
                    splConditionOne = "";
                } else {
                    /** Display Particular Region only */
                    splCondition = sql_Region_Wise;
                    splConditionOne = sql_Region_WiseOne;
                }
            }
            String sql_Bank_Wise =
                " and roff.HO_ACCOUNT_NO = " + txtBankAccountNo;
            /** Displaying in Bank wise */
            if (displayingOrder.equalsIgnoreCase("BW")) {
                splCondition = sql_Bank_Wise;
            }

            //  System.out.println("Condition"+splCondition)  ;


            String accounted =
                "select * from \n" + " (select ftrn.cashbook_month,ftrn.cashbook_year,ftrn.accounting_unit_id as unit_a,ftrn.accounting_for_office_id as office_a,ftrn.voucher_no,ftrn.transfer_to_office_id," +
                		" roff.ACCOUNTING_FOR_OFFICE_ID as office,ftrn.cheque_or_dd,ftrn.cheque_dd_no,ftrn.amount, ftrn.auto_status,roff.total_amount as total,roff.RECEIPT_DATE, \n" +
                " roff.OFFICE_BANK_ID,roff.OFFICE_ACCOUNT_NO,roff.TRF_VOUCHER_DATE from FAS_FUND_TRF_FROM_HO_TRN ftrn,FAS_FUND_RECEIPT_BY_OFFICE roff \n" +
                " where ftrn.TRANSFER_TO_OFFICE_ID=roff.ACCOUNTING_FOR_OFFICE_ID and ftrn.VOUCHER_NO=roff.TRF_VOUCHER_NO  and ftrn.CHEQUE_DD_NO=Roff.CHEQUE_DD_NO\n" +
                " and ftrn.cashbook_month=" + month +
                " and roff.RECEIPT_STATUS='L'and ftrn.cashbook_year=" + year +
                " and ftrn.amount=roff.total_amount " + splCondition +
                " order by voucher_no \n" + ")a \n" + " left outer join \n" +
                " (select voucher_no as vouch,DATE_OF_TRANSFER, cashbook_month,cashbook_year,accounting_unit_id,TRANSFER_STATUS \n" +
                " from \n" +
                " FAS_FUND_TRF_FROM_HO_MASTER where TRANSFER_STATUS='L' )b \n" +
                " on a.unit_a=b.accounting_unit_id and a.voucher_no=b.vouch and a.cashbook_month=b.cashbook_month and a.cashbook_year=b.cashbook_year and a.TRF_VOUCHER_DATE=b.DATE_OF_TRANSFER \n" +
                " left outer join \n" +
                " (select OFFICE_ID,OFFICE_NAME from \n" +
                " COM_MST_OFFICES )c \n" +
                " on a.transfer_to_office_id=c.OFFICE_ID \n" +
                " left outer join (select BANK_ID,BANK_SHORT_NAME from fas_mst_banks) \n" +
                "  on a.OFFICE_BANK_ID=BANK_ID order by a.voucher_no";


         /*   String notAccounted =" select * from \n" + " (select cashbook_year,cashbook_month, voucher_no,auto_status,transfer_to_office_id,cheque_or_dd,cheque_dd_no,amount ,null as RECEIPT_DATE,null as OFFICE_ACCOUNT_NO, null as BANK_SHORT_NAME  \n" +
                " from FAS_FUND_TRF_FROM_HO_TRN where cashbook_month=" +
                month + " and cashbook_year=" + year + "  " + splConditionOne +
                " \n" +
                " and transfer_to_office_id in (select accounting_for_office_id from FAS_FUND_RECEIPT_BY_OFFICE \n" +
                " where cashbook_month=" + month + " and cashbook_year=" +
                year + "  \n" +
                " ) and FAS_FUND_TRF_FROM_HO_TRN.amount not in (select total_amount from FAS_FUND_RECEIPT_BY_OFFICE \n" +
                " where cashbook_month=" + month + " and cashbook_year=" +
                year + " ))a \n" + " left outer join \n" +
                " (select voucher_no as vouch,DATE_OF_TRANSFER, cashbook_month,cashbook_year,accounting_unit_id,TRANSFER_STATUS \n" +
                " from  \n" +
                " FAS_FUND_TRF_FROM_HO_MASTER where TRANSFER_STATUS='L' and cashbook_month=" +
                month + " and cashbook_year=" + year + ")b \n" +
                " on a.voucher_no=b.vouch and a.cashbook_month=b.cashbook_month and a.cashbook_year=b.cashbook_year\n" +
                " left outer join \n" +
                " (select OFFICE_ID,OFFICE_NAME from \n" +
                " COM_MST_OFFICES )c \n" +
                " on a.transfer_to_office_id=c.OFFICE_ID order by a.voucher_no";  */
            
		String notAccounted ="SELECT * "+
					" FROM "+
					  "(SELECT  cashbook_year,cashbook_month,voucher_no,auto_status, "+
					  "  transfer_to_office_id,cheque_or_dd,cheque_dd_no,amount ,"+
					  "    NULL AS RECEIPT_DATE,NULL AS OFFICE_ACCOUNT_NO,NULL AS BANK_SHORT_NAME "+
					  " FROM FAS_FUND_TRF_FROM_HO_TRN  "+
					  " WHERE Cashbook_Month = "+month+
					  " 	And Cashbook_Year    ="+year+ "  " + splConditionOne +
					  " And Cheque_Dd_No Not In "+
					  " ( "+
					  " SELECT Cheque_Dd_No "+
					  "     FROM FAS_FUND_RECEIPT_BY_OFFICE "+
					  "     Where Cashbook_Month= "+month+
					  " And Cashbook_Year   = "+year+
					  "  and Receipt_Status='L'  and ACCOUNTING_FOR_OFFICE_ID=transfer_to_office_id "+
					  " ) and auto_status is null "+
					  " Union All "+
					  " Select Cashbook_Year,Cashbook_Month,Trf_Voucher_No As Voucher_No,Null As Auto_Status, "+
					  " Accounting_For_Office_Id As Transfer_To_Office_Id,Cheque_Or_Dd,Cheque_Dd_No,Total_Amount As Amount, "+
					  " NULL AS RECEIPT_DATE,Null As Office_Account_No,NULL AS BANK_SHORT_NAME  "+
					  " FROM FAS_FUND_RECEIPT_BY_OFFICE "+
					  " Where Cashbook_Month= "+month+
					  " And Cashbook_Year   = "+year+
					  //" And Receipt_Status='C' "+ joe Change 11Dec13
					  " And Receipt_Status='L' "+
					  " And Cheque_Dd_No Not In(Select A.Cheque_Dd_No "+
					  " From Fas_Fund_Receipt_By_Office A,Fas_Fund_Receipt_By_Office B "+
					  " Where A.Accounting_Unit_Id=B.Accounting_Unit_Id "+
					  " And A.Accounting_For_Office_Id=B.Accounting_For_Office_Id "+
					  " And A.Cheque_Dd_No=B.Cheque_Dd_No "+
					  " And A.TOTAL_AMOUNT=B.TOTAL_AMOUNT "+
					  " And A.Receipt_Status='C' "+
					  " and b.Receipt_Status='L' "+
					  " And A.Cashbook_Month       ="+month+
					  " And A.Cashbook_Year          ="+year+") "+
					  " 	)a "+
					  " LEFT OUTER JOIN "+
					  "  (SELECT voucher_no AS vouch, "+
					  "    DATE_OF_TRANSFER, "+
					  "    cashbook_month, "+
					  "    cashbook_year, "+
					  "    accounting_unit_id, "+
					  "    TRANSFER_STATUS "+
					  "   FROM FAS_FUND_TRF_FROM_HO_MASTER "+
					  "  WHERE TRANSFER_STATUS='L' "+
					  "  AND cashbook_month   = "+month+
					  "  AND cashbook_year    = "+year+
					  "   )b "+
					  " ON a.voucher_no     =b.vouch "+
					  " AND a.cashbook_month=b.cashbook_month "+
					  " AND a.cashbook_year =b.cashbook_year "+
					  " LEFT OUTER JOIN "+
					  "  (SELECT OFFICE_ID,OFFICE_NAME FROM Com_Mst_Offices "+
					  "   )C "+
					  " ON a.transfer_to_office_id=c.OFFICE_ID "+
					  " ORDER BY a.voucher_no";

            String queryString = "";
            String reportType = "";
            /**
                      *  Single Month + All A/C Head
                      */

            if (Specifictype.equalsIgnoreCase("accounted")) {
System.out.println("accounted::::"+accounted);

                reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/FundReceiptSystem/jasper/Fund_Transfer_Reconciliation_atHO.jasper"));
                queryString = accounted;
                reportType = "Accounted";

            }
            /**
                      * Single Month + Single A/C Head
                      */
            else {
            	System.out.println("notAccounted::::"+notAccounted);
                reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/FundReceiptSystem/jasper/Fund_Transfer_Reconciliation_atHO.jasper"));
                queryString = notAccounted;
                reportType = "Not Accounted";
            }


            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            System.out.println("opt::" + opt);
            Map map = new HashMap();
            map.put("querysql", queryString);
            map.put("cashmonth", month);
            map.put("cashyear", year);
            map.put("monthinwords", monthInWords);
            map.put("typereport", reportType);

            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);


            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"Reconciliation-HO.html\"");
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
                                   "attachment;filename=\"Reconciliation-HO.pdf\"");
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"Reconciliation-HO.xls\"");
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
                                   "attachment;filename=\"Reconciliation-HO.txt\"");

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

        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }

    }

}
