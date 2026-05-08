package Servlets.FAS.FAS1.CivilBudget.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;

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
 * Servlet implementation class Annual_acc_Report
 */
public class GPF_Schedule4_4 extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
     private static final String CONTENT_TYPE = 
            "text/html; charset=windows-1252";
        Connection connection = null;
    public GPF_Schedule4_4() {
        super();
        // TODO Auto-generated constructor stub
    }

    
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
	    doGet(request, response);
	}
        
        
    protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
           
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

        ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
        Class.forName(strDriver.trim());
        connection =DriverManager.getConnection(ConnectionString, strdbusername.trim(),strdbpassword.trim());
        } catch (Exception ex) {
        String connectMsg ="Could not create the connection" + ex.getMessage() + " " + 
        ex.getLocalizedMessage();
        System.out.println(connectMsg);
        }
        JasperDesign jasperDesign = null;
        File reportFile=null;
        
        try
        {
        System.out.println("inside servlet>>>>>>>>>>>>>>>..");
        String txtCB_Year=request.getParameter("txtCB_Year");
        String txtCB_Month=request.getParameter("txtCB_Month");
        System.out.println("txtCB_Month>>>>"+txtCB_Month);
        
        
        String rtype= request.getParameter("txtoption");
      
        int yearfrom=Integer.parseInt(txtCB_Year);
        int monthfrom=Integer.parseInt(txtCB_Month);
      
        
        String monthInWords="";
        if(monthfrom==1)
            monthInWords="January";
        else if(monthfrom==2)
            monthInWords="February";
        else if(monthfrom==3)
            monthInWords="March";
        else if(monthfrom==4)
            monthInWords="April";
        else if(monthfrom==5)
            monthInWords="May";
        else if(monthfrom==6)
            monthInWords="June";
        else if(monthfrom==7)
            monthInWords="July";
        else if(monthfrom==8)
            monthInWords="August";
        else if(monthfrom==9)
            monthInWords="September";
        else if(monthfrom==10)
            monthInWords="October";
        else if(monthfrom==11)
            monthInWords="November";
        else if(monthfrom==12)
            monthInWords="December";
      
      String  qry="SELECT (fin.tot_contrib+fin.REG_RECandaDVANCE+fin.REG_Pensioner)AS total_contAndrec, " +
        "  fin.* " +
        "  FROM " +
        "  (SELECT A.*, " +
        "    B.*, " +
        "    c.*, " +
        "    (d.impoundedDr    +e.impoundedcr)    AS net_impound, " +
        "    (f.impounded2003Dr+g.impounded2003cr)AS net_impound2003, " +
        "    h.*, " +
        "    (a.REG_CONTR       +b.REG_RECandaDVANCE)                                                          AS tot_reg, " +
        "    ((f.impounded2003Dr+g.impounded2003cr)+c.REG_Pensioner)                                           AS tot_impound03, " +
        "    (a.REG_CONTR       +(d.impoundedDr +e.impoundedcr)+(f.impounded2003Dr+g.impounded2003cr)+h.Gpf_Da)AS tot_contrib, " +
        "    i.reg_Inter_allow, " +
        "    j.impound_Inter_allow, " +
        "    k.impound_Inter_allow03, " +
        "    (i.reg_Inter_allow+j.impound_Inter_allow+k.impound_Inter_allow03)AS net_inte_allowed, " +
        "    AA.*, " +
        "    (dd.imP_OpenBal_DR     +ee.imP_OpenBal_CR)     AS net_impound_OB, " +
        "    (ff.impounded_OB_2003Dr+gg.impounded_OB_2003cr)AS net_impound2003_ob, " +
        "   (aa.oB_Reg       +(dd.imP_OpenBal_DR +ee.imP_OpenBal_CR)+(ff.impounded_OB_2003Dr+gg.impounded_OB_2003cr)+hh.Gpf_Da_OB)AS tot_OB_contrib,"+
        "    hh.* " +
        "  FROM " +
        "    (SELECT SUM(CURRENT_MONTH_DEBIT-CURRENT_MONTH_CREDIT)AS REG_CONTR " +
        "    FROM FAS_TRIAL_BALANCE " +
        "    WHERE ACCOUNT_HEAD_CODE LIKE '%390302%' " +
        "    AND CASHBOOK_YEAR ="+yearfrom+
        "    AND CASHBOOK_MONTH="+monthfrom +
        "    )A, " +
        "    (SELECT SUM(DEBIT_OPENING_BALANCE-CREDIT_OPENING_BALANCE)AS oB_Reg " +
        "    FROM FAS_TRIAL_BALANCE " +
        "    WHERE ACCOUNT_HEAD_CODE LIKE '%390302%' " +
        "    AND CASHBOOK_YEAR ="+yearfrom+
        "    AND CASHBOOK_MONTH="+monthfrom +
        "    )AA, " +
        "    (SELECT SUM(DEBIT_OPENING_BALANCE-CREDIT_OPENING_BALANCE)AS imP_OpenBal_DR " +
        "    FROM FAS_TRIAL_BALANCE " +
        "    WHERE ACCOUNT_HEAD_CODE LIKE '%391002%' " +
        "    AND CASHBOOK_YEAR ="+yearfrom+
        "    AND CASHBOOK_MONTH="+monthfrom +
        "    )DD, " +
        "    (SELECT SUM(DEBIT_OPENING_BALANCE-CREDIT_OPENING_BALANCE)AS imP_OpenBal_CR " +
        "    FROM FAS_TRIAL_BALANCE " +
        "    WHERE ACCOUNT_HEAD_CODE LIKE '%391003%' " +
        "    AND CASHBOOK_YEAR ="+yearfrom+
        "    AND CASHBOOK_MONTH="+monthfrom +
        "    )eE, " +
        "    (SELECT SUM(DEBIT_OPENING_BALANCE-CREDIT_OPENING_BALANCE)AS impounded_OB_2003Dr " +
        "    FROM FAS_TRIAL_BALANCE " +
        "    WHERE ACCOUNT_HEAD_CODE LIKE '%391302%' " +
        "    AND CASHBOOK_YEAR ="+yearfrom+
        "    AND CASHBOOK_MONTH="+monthfrom +
        "    )ff, " +
        "    (SELECT SUM(DEBIT_OPENING_BALANCE-CREDIT_OPENING_BALANCE)AS impounded_OB_2003cr " +
        "    FROM FAS_TRIAL_BALANCE " +
        "    WHERE ACCOUNT_HEAD_CODE LIKE '%391303%' " +
        "    AND CASHBOOK_YEAR ="+yearfrom+
        "    AND CASHBOOK_MONTH="+monthfrom +
        "    )gg, " +
        "    (SELECT SUM(DEBIT_OPENING_BALANCE-CREDIT_OPENING_BALANCE)AS Gpf_Da_OB " +
        "    FROM FAS_TRIAL_BALANCE " +
        "    WHERE ACCOUNT_HEAD_CODE LIKE '%391503%' " +
        "    AND CASHBOOK_YEAR ="+yearfrom+
        "    AND CASHBOOK_MONTH="+monthfrom +
        "    )hh, " +
        "    (SELECT SUM(CURRENT_MONTH_DEBIT-CURRENT_MONTH_CREDIT)AS REG_RECandaDVANCE " +
        "    FROM FAS_TRIAL_BALANCE " +
        "    WHERE ACCOUNT_HEAD_CODE LIKE '%390303%' " +
        "    AND CASHBOOK_YEAR ="+yearfrom+
        "    AND CASHBOOK_MONTH="+monthfrom +
        "    )B, " +
        "    (SELECT SUM(CURRENT_MONTH_DEBIT-CURRENT_MONTH_CREDIT)AS REG_Pensioner " +
        "    FROM FAS_TRIAL_BALANCE " +
        "    WHERE ACCOUNT_HEAD_CODE LIKE '%391202%' " +
        "    AND CASHBOOK_YEAR ="+yearfrom+
        "    AND CASHBOOK_MONTH="+monthfrom +
        "    )C, " +
        "    (SELECT SUM(CURRENT_MONTH_DEBIT-CURRENT_MONTH_CREDIT)AS impoundedDr " +
        "    FROM FAS_TRIAL_BALANCE " +
        "    WHERE ACCOUNT_HEAD_CODE LIKE '%391002%' " +
        "    AND CASHBOOK_YEAR ="+yearfrom+
        "    AND CASHBOOK_MONTH="+monthfrom +
        "    )D, " +
        "    (SELECT SUM(CURRENT_MONTH_DEBIT-CURRENT_MONTH_CREDIT)AS impoundedcr " +
        "    FROM FAS_TRIAL_BALANCE " +
        "    WHERE ACCOUNT_HEAD_CODE LIKE '%391003%' " +
        "    AND CASHBOOK_YEAR ="+yearfrom+
        "    AND CASHBOOK_MONTH="+monthfrom +
        "    )e, " +
        "    (SELECT SUM(CURRENT_MONTH_DEBIT-CURRENT_MONTH_CREDIT)AS impounded2003Dr " +
        "    FROM FAS_TRIAL_BALANCE " +
        "    WHERE ACCOUNT_HEAD_CODE LIKE '%391302%' " +
        "    AND CASHBOOK_YEAR ="+yearfrom+
        "    AND CASHBOOK_MONTH="+monthfrom +
        "    )f, " +
        "    (SELECT SUM(CURRENT_MONTH_DEBIT-CURRENT_MONTH_CREDIT)AS impounded2003cr " +
        "    FROM FAS_TRIAL_BALANCE " +
        "    WHERE ACCOUNT_HEAD_CODE LIKE '%391303%' " +
        "    AND CASHBOOK_YEAR ="+yearfrom+
        "    AND CASHBOOK_MONTH="+monthfrom +
        "    )g, " +
        "    (SELECT SUM(CURRENT_MONTH_DEBIT-CURRENT_MONTH_CREDIT)AS Gpf_Da " +
        "    FROM FAS_TRIAL_BALANCE " +
        "    WHERE ACCOUNT_HEAD_CODE LIKE '%391503%' " +
        "    AND CASHBOOK_YEAR ="+yearfrom+
        "    AND CASHBOOK_MONTH="+monthfrom +
        "    )h, " +
        "    (SELECT SUM(CURRENT_MONTH_DEBIT-CURRENT_MONTH_CREDIT)AS reg_Inter_allow " +
        "    FROM FAS_TRIAL_BALANCE " +
        "    WHERE ACCOUNT_HEAD_CODE LIKE '%390304%' " +
        "    AND CASHBOOK_YEAR ="+yearfrom+
        "    AND CASHBOOK_MONTH="+monthfrom +
        "    )i, " +
        "    (SELECT SUM(CURRENT_MONTH_DEBIT-CURRENT_MONTH_CREDIT)AS impound_Inter_allow " +
        "    FROM FAS_TRIAL_BALANCE " +
        "    WHERE ACCOUNT_HEAD_CODE LIKE '%391304%' " +
        "    AND CASHBOOK_YEAR ="+yearfrom+
        "    AND CASHBOOK_MONTH="+monthfrom +
        "    )j, " +
        "    (SELECT SUM(CURRENT_MONTH_DEBIT-CURRENT_MONTH_CREDIT)AS impound_Inter_allow03 " +
        "    FROM FAS_TRIAL_BALANCE " +
        "    WHERE ACCOUNT_HEAD_CODE LIKE '%391305%' " +
        "    AND CASHBOOK_YEAR ="+yearfrom+
        "    AND CASHBOOK_MONTH="+monthfrom +
        "    )k " +
        "  )fin";
        System.out.println(" >>>  "+qry);
        reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/CivilBudget/jasper/schedule/schedula4.4.jasper")); 
          System.out.println("reportFile"+reportFile);   
        if (!reportFile.exists())
        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
        
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
        System.out.println("jasperReport"+jasperReport);
        Map map=new HashMap();
        map.put("monthvalue",monthInWords);
        map.put("qry",qry);
        map.put("month",monthfrom);
        map.put("year",yearfrom);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);            
        if (rtype.equalsIgnoreCase("HTML"))   
        {
                    response.setContentType("text/html");
                    response.setHeader ("Content-Disposition", "attachment;filename=\"Schedule4.4.html\"");
                    PrintWriter out = response.getWriter();
                    JRHtmlExporter exporter = new JRHtmlExporter();
                    exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,  false);
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
                    exporter.exportReport();
                    out.flush();
                    out.close();
        }
        else      if (rtype.equalsIgnoreCase("PDF"))   
        {
                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    response.setHeader ("Content-Disposition", "attachment;filename=\"Schedule4.4.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
        }
        else      if (rtype.equalsIgnoreCase("EXCEL"))   
        {

                        response.setContentType("application/vnd.ms-excel");
                    response.setHeader ("Content-Disposition", "attachment;filename=\"Schedule4.4.xls\"");
                    JRXlsExporter exporterXLS = new JRXlsExporter(); 
                    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT, jasperPrint); 
                 
                    ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
                    exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,xlsReport); 
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET, Boolean.FALSE); 
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE, Boolean.TRUE); 
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND, Boolean.FALSE); 
                    exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS, Boolean.TRUE); 
                    exporterXLS.exportReport(); 
                    byte []bytes;
                    bytes = xlsReport.toByteArray();
                    ServletOutputStream ouputStream = response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();

        }
        else      if (rtype.equalsIgnoreCase("TXT"))   
        {
        
                        response.setContentType("text/plain");
                        response.setHeader ("Content-Disposition", "attachment;filename=\"Schedule4.4.txt\"");
                             
                        JRTextExporter exporter = new JRTextExporter();
                        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
                        ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
                        exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,txtReport); 
                        exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH, new Integer(200));
                        exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT, new Integer(50));
                        exporter.exportReport(); 
                        
                    byte []bytes;
                    bytes = txtReport.toByteArray();
                    ServletOutputStream ouputStream = response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();

        }
        
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
