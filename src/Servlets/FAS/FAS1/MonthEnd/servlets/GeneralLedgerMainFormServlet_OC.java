package Servlets.FAS.FAS1.MonthEnd.servlets;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;


import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

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
import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;


public class GeneralLedgerMainFormServlet_OC extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        /**
         * Session Checking
         */

        HttpSession session = request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }


        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        /**
         * Get User ID
         */
        String userid = (String)session.getAttribute("UserId");
        System.out.println(userid);


        /**
         * Set Content Type
         */
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();

        /**
         * Variables Declaration
         */
        Connection con = null;
        PreparedStatement pss1 = null;
        PreparedStatement ps = null;
        PreparedStatement ps1 = null;
        ResultSet res = null;


        /**
         * Database Connection
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
        }


        /** Get Accounting Unit Id */
        String Account_unit_Code = request.getParameter("cmbAcc_UnitCode");
        /** Get Accounting Office Code */
        String Office_Code = request.getParameter("comOffCode");
        /** Get Cashbook Year */
        String CashBook_Year = request.getParameter("CashbookYear");
        /** Get Cashbook Month */
        String CashBook_Month = request.getParameter("CashbookMonth");


        System.out.println("Account_Unit_Code is:" + Account_unit_Code);
        System.out.println("Office_Code is:" + Office_Code);
        System.out.println("CashBook_Year is:" + CashBook_Year);
        System.out.println("CashBook Month is:" + CashBook_Month);


        String strCommand = "";
        String xml = "";
        int AccountUnitCode = 0;
        int OfficeCode = 0;
        int CashBookYear = 0;
        int CashBookMonth = 0;
        int cmbAcHeadCode = 0;
        double CloseBal = 0;
        String CloseBalInd = "";
        int CashbookYear = 0;
        int CashbookMonth = 0;
        int AccId = 0;
        int OffCode = 0;


        try {
            AccountUnitCode = Integer.parseInt(Account_unit_Code);
            OfficeCode = Integer.parseInt(Office_Code);
            CashBookYear = Integer.parseInt(CashBook_Year);
            CashBookMonth = Integer.parseInt(CashBook_Month);


            System.out.println("Account_Unit_Code After is:" +
                               AccountUnitCode);
            System.out.println("Office_Code After is:" + OfficeCode);
            System.out.println("CashBook_Year After is:" + CashBookYear);
            System.out.println("CashBook Month After is:" + CashBookMonth);

        } catch (Exception e) {
            System.out.println("Exception in Converting Integer:" + e);
        }


        /**
         * Get Command Parameter and Account Head Code
         */
        try {
            strCommand = request.getParameter("Command");
            cmbAcHeadCode =
                    Integer.parseInt(request.getParameter("cmbAcHeadCode"));
        } catch (Exception ae) {
            System.out.println("first exception...." + ae);
        }


        if (strCommand.equalsIgnoreCase("Add")) {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            xml = "<response><command>Add</command>";

            long l = System.currentTimeMillis();
            Timestamp ts = new Timestamp(l);

            CloseBalInd = request.getParameter("CloseBalInd");
            System.out.println("CloseBalInd is:" + CloseBalInd);


            try {
                AccId =
Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception que) {
                System.out.println("Exception in assigning values in add command...." +
                                   que);
            }


            try {
                OffCode = Integer.parseInt(request.getParameter("comOffCode"));
                System.out.println(OffCode);
            } catch (Exception que) {
                System.out.println("Exception in assigning values in add command...." +
                                   que);
            }


            try {
                cmbAcHeadCode =
                        Integer.parseInt(request.getParameter("cmbAcHeadCode"));
            } catch (Exception e) {
                System.out.println("Exception in checkcode");
            }


            try {
                CloseBal =
                        Double.parseDouble(request.getParameter("CloseBal"));
                System.out.println("CloseBal is:" + CloseBal);
            } catch (Exception ex1) {
                System.out.println("Exception in CloseBal:" + ex1);
            }


            try {
                CashbookYear =
                        Integer.parseInt(request.getParameter("CashbookYear"));
                System.out.println("CashbookYear is:" + CashbookYear);
            } catch (Exception ex1) {
                System.out.println("Exception in CashbookYear:" + ex1);
            }


            try {

                CashbookMonth =
                        Integer.parseInt(request.getParameter("CashbookMonth"));
                System.out.println("CashbookMonth is:" + CashbookMonth);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookMonth:" + ex1);
            }


            try {


                ps1 =
 con.prepareStatement("select account_head_code from FAS_GENERAL_LEDGER_CB where accounting_unit_id=? " +
                      " and account_head_code=? and year=? and month=? and accounting_for_office_id=?");
                ps1.setInt(1, AccId);
                ps1.setInt(2, cmbAcHeadCode);
                ps1.setInt(3, CashbookYear);
                ps1.setInt(4, CashbookMonth);
                ps1.setInt(5, OffCode);

                res = ps1.executeQuery();
                if (!res.next()) {
                    pss1 =
con.prepareStatement("insert into FAS_GENERAL_LEDGER_CB(ACCOUNT_HEAD_CODE,ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,UPDATED_BY_USER_ID,MONTH_CLOSING_BALANCE,MONTH_CLOSING_BAL_DR_CR_IND,YEAR,MONTH,UPDATED_DATE, CREATED_BY_MODULE ) values(?,?,?,?,?,?,?,?,?,?)");
                    pss1.setInt(1, cmbAcHeadCode);
                    pss1.setInt(2, AccId);
                    pss1.setInt(3, OffCode);
                    pss1.setString(4, userid);
                    pss1.setDouble(5, CloseBal);
                    pss1.setString(6, CloseBalInd);
                    pss1.setInt(7, CashbookYear);
                    pss1.setInt(8, CashbookMonth);
                    pss1.setTimestamp(9, ts);
                    pss1.setString(10, "M");

                    pss1.executeUpdate();
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>AlreadyExist</flag>";
                }
            } catch (Exception ex4) {
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        } else if (strCommand.equalsIgnoreCase("Update")) {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            xml = "<response><command>Update</command>";

            long l = System.currentTimeMillis();
            CloseBalInd = request.getParameter("CloseBalInd");
            System.out.println("CloseBalInd is:" + CloseBalInd);

            try {
                AccId =
Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception que) {
                System.out.println("Exception in assigning values in add command...." +
                                   que);
            }
            System.out.println(AccId);

            try {
                OffCode = Integer.parseInt(request.getParameter("comOffCode"));
                System.out.println(OffCode);
            } catch (Exception que) {
                System.out.println("Exception in assigning values in add command...." +
                                   que);
            }

            try {
                cmbAcHeadCode =
                        Integer.parseInt(request.getParameter("cmbAcHeadCode"));
            } catch (Exception e) {
                System.out.println("Exception in checkcode");
            }


            try {
                CloseBal =
                        Double.parseDouble(request.getParameter("CloseBal"));
                System.out.println("CloseBal is:" + CloseBal);
            } catch (Exception ex1) {
                System.out.println("Exception in CloseBal:" + ex1);
            }

            try {
                CashbookYear =
                        Integer.parseInt(request.getParameter("CashbookYear"));
                System.out.println("CashbookYear is:" + CashbookYear);
            } catch (Exception ex1) {
                System.out.println("Exception in CashbookYear:" + ex1);
            }

            try {
                CashbookMonth =
                        Integer.parseInt(request.getParameter("CashbookMonth"));
                System.out.println("CashbookMonth is:" + CashbookMonth);
            } catch (Exception ex1) {
                System.out.println("Exception in CashbookMonth:" + ex1);
            }

            long l1 = System.currentTimeMillis();
            Timestamp ts1 = new Timestamp(l1);

            try {

                pss1 =
con.prepareStatement("update FAS_GENERAL_LEDGER_CB set CREATED_BY_MODULE='M',YEAR=?,MONTH=?,MONTH_CLOSING_BALANCE=?,MONTH_CLOSING_BAL_DR_CR_IND=?,UPDATED_DATE=?,UPDATED_BY_USER_ID=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and ACCOUNT_HEAD_CODE=? and year=? and month=?");
                pss1.setInt(1, CashbookYear);
                pss1.setInt(2, CashbookMonth);
                pss1.setDouble(3, CloseBal);
                pss1.setString(4, CloseBalInd);
                pss1.setTimestamp(5, ts1);
                pss1.setString(6, userid);
                pss1.setInt(7, AccId);
                pss1.setInt(8, OffCode);
                pss1.setInt(9, cmbAcHeadCode);
                pss1.setInt(10, CashbookYear);
                pss1.setInt(11, CashbookMonth);

                pss1.executeUpdate();

                xml = xml + "<flag>success</flag>";
                pss1.close();
            }

            catch (Exception ex4) {
                System.out.println("Exception in update......" + ex4);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

        } else if (strCommand.equalsIgnoreCase("Delete")) {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            xml = "<response><command>Delete</command>";

            try {
                AccId =
Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            } catch (Exception que) {
                System.out.println("Exception in assigning values in add command...." +
                                   que);
            }
            System.out.println(AccId);

            try {
                OffCode = Integer.parseInt(request.getParameter("comOffCode"));
                System.out.println(OffCode);
            } catch (Exception que) {
                System.out.println("Exception in assigning values in add command...." +
                                   que);
            }
            try {

                CashbookYear =
                        Integer.parseInt(request.getParameter("CashbookYear"));
                System.out.println("CashbookYear is:" + CashbookYear);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookYear:" + ex1);
            }
            try {

                CashbookMonth =
                        Integer.parseInt(request.getParameter("CashbookMonth"));
                System.out.println("CashbookMonth is:" + CashbookMonth);

            } catch (Exception ex1) {
                System.out.println("Exception in CashbookMonth:" + ex1);
            }

            try {
                cmbAcHeadCode =
                        Integer.parseInt(request.getParameter("cmbAcHeadCode"));
            } catch (Exception e) {
                System.out.println("Exception in checkcode");
            }


            try {
                ps1 =
 con.prepareStatement("delete from FAS_GENERAL_LEDGER_CB where ACCOUNT_HEAD_CODE=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and year=? and month=?");
                ps1.setInt(1, cmbAcHeadCode);
                ps1.setInt(2, AccId);
                ps1.setInt(3, OffCode);
                ps1.setInt(4, CashbookYear);
                ps1.setInt(5, CashbookMonth);

                ps1.executeUpdate();
                xml = xml + "<flag>success</flag>";
                ps1.close();
            } catch (Exception ex2) {
                System.out.println("exception in add" + ex2);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        if (strCommand.equalsIgnoreCase("CheckGL")) {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            xml = "<response><command>CheckGL</command>";


            try {
                ps =
  con.prepareStatement("select GL_STATUS from FAS_GL_CB_STATUS where  ACCOUNTING_UNIT_ID=? and CASHBOOK_YEAR=? and CASHBOOK_MONTH=?");
                ps.setInt(1, AccountUnitCode);
                ps.setInt(2, CashBookYear);
                ps.setInt(3, CashBookMonth);

                res = ps.executeQuery();

                while (res.next()) {
                    String GL = res.getString("GL_STATUS");
                    System.out.println(GL);
                    if (GL.equalsIgnoreCase("Y")) {
                        xml = xml + "<flag>success</flag>";
                        System.out.println("Gl freezed.");
                    }
                }

            } catch (Exception e) {
                System.out.println("Exception in CheckGL......" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        if (strCommand.equalsIgnoreCase("HeadCode")) {
            response.setContentType("text/xml");
            response.setHeader("Cache-Control", "no-cache");
            String headcode = request.getParameter("txtAcc_Head_code");
            int headcodeno = Integer.parseInt(headcode);
            xml = "<response><command>HeadCode</command>";


            try {
                ps =
  con.prepareStatement("select ACCOUNT_HEAD_CODE,ACCOUNT_HEAD_DESC from COM_MST_ACCOUNT_HEADS where USAGE_STATUS='Y' and ACCOUNT_HEAD_CODE=?");
                ps.setInt(1, headcodeno);
                res = ps.executeQuery();
                if (res.next()) {
                    xml =
 xml + "<flag>success</flag><hid>" + headcodeno + "</hid><hdesc>" +
   res.getString("ACCOUNT_HEAD_DESC") + "</hdesc>";

                } else {
                    System.out.println("No record found");
                    xml = xml + "<flag>failure</flag>";
                }


            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";


        }


        out.write(xml);
        System.out.println("xml is:" + xml);
        out.close();
    }
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		Connection connection=null;
		int cashYear_from = 0;
		int cashMonth_from = 0;
		int cashYear_to = 0;
		int cashMonth_to = 0;
		String queryStr="";
		String strCommand="report";
        try{
        	LoadDriver driver = new LoadDriver();
  			connection = driver.getConnection();                           
        }catch(Exception e){
            System.out.println("Exception in connection..."+e);
        }   
        try{
        	HttpSession session=request.getSession(false);
            if(session==null){
            	response.sendRedirect(request.getContextPath()+"/index.jsp");
              }                  
            }catch(Exception e){
                System.out.println("Redirect Error :"+e);
             }                
            int year = Integer.parseInt(request.getParameter("txtCB_Year"));
        	int month = Integer.parseInt(request.getParameter("txtCB_Month"));
        	int accUnitId = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        	int officeId = Integer.parseInt(request.getParameter("cmbOffice_code"));
        	int acchead =0;
        	if(request.getParameter("cmbAcHeadCode")!=null && !request.getParameter("cmbAcHeadCode").equals("")){
        		acchead = Integer.parseInt(request.getParameter("cmbAcHeadCode"));
        	}    	
        	System.out.println("acchead "+acchead);
        	if(acchead!=0){
            	queryStr = "AND ACCOUNT_HEAD_CODE='"+acchead+"'";
            }
                if(strCommand.equalsIgnoreCase("report")){
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
                    	map.put("year", year);
                		map.put("month", month);
                		map.put("monthInWord", monthlist.get(month));
                		String s="",myQry="";
                		myQry="SELECT acc_head, " +
                		"  (SELECT account_head_desc " +
                		"  FROM com_mst_account_heads " +
                		"  WHERE account_head_code = acc_head " +
                		"  )                             AS acc_head_name, " +
                		"  a.month_closing_balance       AS month_closing_balance, " +
                		"  a.month_closing_bal_dr_cr_ind AS month_closing_bal_dr_cr_ind, " +
                		"  b.freeze_status               AS freeze_status, " +
                		"  c.ACCOUNTING_UNIT_NAME        AS ACCOUNTING_UNIT_NAME, " +
                		"  d.OFFICE_NAME                 AS OFFICE_NAME " +
                		"FROM " +
                		"  (SELECT ACCOUNTING_UNIT_ID, " +
                		"    ACCOUNTING_FOR_OFFICE_ID, " +
                		"    account_head_code AS acc_head, " +
                		"    month_closing_balance, " +
                		"    month_closing_bal_dr_cr_ind " +
                		"  FROM fas_general_ledger_cb " +
                		"  WHERE accounting_unit_id     = '"+accUnitId+"' " +
                		"  AND accounting_for_office_id = '"+officeId+"' " +    			
                		"  AND YEAR                     ='"+year+"' " +
                		"  AND MONTH                    = '"+month+"' " +
                			queryStr +
                		"  )a " +
                		"LEFT OUTER JOIN " +
                		"  (SELECT COUNT(gl_status) AS freeze_status, " +
                		"    ACCOUNTING_UNIT_ID, " +
                		"    ACCOUNTING_FOR_OFFICE_ID " +
                		"  FROM fas_gl_cb_status " +
                		"  WHERE accounting_unit_id='"+accUnitId+"' " +
                		"  AND cashbook_month      ='"+month+"' " +
                		"  AND cashbook_year       ='"+year+"' " +
                		"  AND gl_status           ='Y' " +
                		"  GROUP BY ACCOUNTING_UNIT_ID, " +
                		"    ACCOUNTING_FOR_OFFICE_ID " +
                		"  )b " +
                		"ON a.ACCOUNTING_UNIT_ID       =b.ACCOUNTING_UNIT_ID " +
                		"AND a.ACCOUNTING_FOR_OFFICE_ID=b.ACCOUNTING_FOR_OFFICE_ID " +
                		"INNER JOIN " +
                		"  ( SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME FROM fas_mst_acct_units " +
                		"  )c " +
                		"ON a.ACCOUNTING_UNIT_ID=c.ACCOUNTING_UNIT_ID " +
                		"INNER JOIN " +
                		"  ( SELECT OFFICE_ID,OFFICE_NAME FROM com_mst_offices " +
                		"  )d " +
                		"ON a.ACCOUNTING_FOR_OFFICE_ID=d.OFFICE_ID " +
                		"ORDER BY acc_head";
                		s=request.getRealPath("/org/FAS/FAS1/MonthEnd/reports/GeneralLedger.jrxml");
                		String output="pdf";
                		JasperDesign jasperDesign = JasperManager.loadXmlDesign(s);
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
    			            	response.setHeader ("Content-Disposition", "attachment;filename=\"GeneralLedger.pdf\"");
    			            	os.write(JasperManager.printReportToPdf(jasperPrint));
    			            	os.close();
    	            	}else if(output.equalsIgnoreCase("excel")){
    	            			response.setContentType("application/vnd.ms-excel");
    	            			response.setHeader ("Content-Disposition", "attachment;filename=\"GeneralLedger.xls\"");
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
    		            		 response.setHeader ("Content-Disposition", "attachment;filename=\"GeneralLedger.html\"");
    		            		 PrintWriter out = response.getWriter();
    		            		 JRHtmlExporter exporter = new JRHtmlExporter();            		
    		            		 exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
    		            		 exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
    		            		 exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
    		            		 exporter.exportReport();
    		            		 out.flush();
    		            		 out.close();
    	            		}
                	}
                	catch(Exception e){
                		e.printStackTrace();
                	}	
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


