package Servlets.FAS.FAS1.MIS.servlets;

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
public class fas_journal_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
     private static final String CONTENT_TYPE = 
            "text/html; charset=windows-1252";
        Connection connection = null;
    public fas_journal_Report() {
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
       /* String txtCB_Year=request.getParameter("txtCB_Year");
        String txtCB_Month=request.getParameter("txtCB_Month");*/
        String DisMode ="0",qry_Str="";
        int BAnk_Sel=0;
        long AcNo_text=0;
        String rtype = request.getParameter("txtoption");
        int Re_type=Integer.parseInt(request.getParameter("Re_type"));
        if(Re_type==1)
        {
        	   DisMode = request.getParameter("DisMode");
        	   qry_Str="";
        }else if(Re_type==2)
        {
        	BAnk_Sel=Integer.parseInt(request.getParameter("BAnk_Sel"));
        	qry_Str=" and a.bid="+BAnk_Sel;
        }else if(Re_type==3)
        {
        	AcNo_text=Long.parseLong(request.getParameter("AcNo_text"));
        	qry_Str=" and a.acno="+AcNo_text;
        }

        String txtFrom_date=request.getParameter("txtFrom_date");
        String txtTo_date=request.getParameter("txtTo_date");
       // System.out.println();
        if(txtFrom_date=="")txtFrom_date="0";
        if(txtTo_date=="")txtTo_date="0";
        String[] Frm_date=txtFrom_date.split("/");
        String[] To_date=txtFrom_date.split("/");
        
        System.out.println("txtFrom_date >>>> "+txtFrom_date);
        System.out.println("txtTo_date >>>> "+txtTo_date);
       String YEar_val=request.getParameter("txtCB_Year");
       String MOnth_val=request.getParameter("txtCB_Month");     
        if(YEar_val=="")YEar_val="0";
        if(MOnth_val=="")MOnth_val="0";
        int txtCB_Year =Integer.parseInt(YEar_val);
        int txtCB_Month=Integer.parseInt(MOnth_val);
        System.out.println("txtCB_Month>>>>"+txtCB_Month);
        String monthInWords="";
        if(txtCB_Month==1)
            monthInWords="January";
        else if(txtCB_Month==2)
            monthInWords="February";
        else if(txtCB_Month==3)
            monthInWords="March";
        else if(txtCB_Month==4)
            monthInWords="April";
        else if(txtCB_Month==5)
            monthInWords="May";
        else if(txtCB_Month==6)
            monthInWords="June";
        else if(txtCB_Month==7)
            monthInWords="July";
        else if(txtCB_Month==8)
            monthInWords="August";
        else if(txtCB_Month==9)
            monthInWords="September";
        else if(txtCB_Month==10)
            monthInWords="October";
        else if(txtCB_Month==11)
            monthInWords="November";
        else if(txtCB_Month==12)
            monthInWords="December";
        String sub_qry="",head="",monthInWords_1="",monthInWords_2="";
   String Hid_text=request.getParameter("Hid_text");
   if(Hid_text.equalsIgnoreCase("DateWise")){
	   if(Integer.parseInt(Frm_date[1])==1)
           monthInWords_1="January";
       else if(Integer.parseInt(Frm_date[1])==2)
           monthInWords_1="February";
       else if(Integer.parseInt(Frm_date[1])==3)
           monthInWords_1="March";
       else if(Integer.parseInt(Frm_date[1])==4)
           monthInWords_1="April";
       else if(Integer.parseInt(Frm_date[1])==5)
           monthInWords_1="May";
       else if(Integer.parseInt(Frm_date[1])==6)
           monthInWords_1="June";
       else if(Integer.parseInt(Frm_date[1])==7)
           monthInWords_1="July";
       else if(Integer.parseInt(Frm_date[1])==8)
           monthInWords_1="August";
       else if(Integer.parseInt(Frm_date[1])==9)
           monthInWords_1="September";
       else if(Integer.parseInt(Frm_date[1])==10)
           monthInWords_1="October";
       else if(Integer.parseInt(Frm_date[1])==11)
           monthInWords_1="November";
       else if(Integer.parseInt(Frm_date[1])==12)
           monthInWords_1="December";
	   if(Integer.parseInt(To_date[1])==1)
           monthInWords_2="January";
       else if(Integer.parseInt(To_date[1])==2)
           monthInWords_2="February";
       else if(Integer.parseInt(To_date[1])==3)
           monthInWords_2="March";
       else if(Integer.parseInt(To_date[1])==4)
           monthInWords_2="April";
       else if(Integer.parseInt(To_date[1])==5)
           monthInWords_2="May";
       else if(Integer.parseInt(To_date[1])==6)
           monthInWords_2="June";
       else if(Integer.parseInt(To_date[1])==7)
           monthInWords_2="July";
       else if(Integer.parseInt(To_date[1])==8)
           monthInWords_2="August";
       else if(Integer.parseInt(To_date[1])==9)
           monthInWords_2="September";
       else if(Integer.parseInt(To_date[1])==10)
           monthInWords_2="October";
       else if(Integer.parseInt(To_date[1])==11)
           monthInWords_2="November";
       else if(Integer.parseInt(To_date[1])==12)
           monthInWords_2="December";
	   
				sub_qry = "To_Date((a.Cashbook_Month " + "||'-' "
						+ "|| a.Cashbook_Year),'mm-yyyy') BETWEEN To_Date( "+Frm_date[1]
						+ "||'-' " + "||"+Frm_date[2]+",'mm-yyyy') " + "AND to_date( "+To_date[1]
						+ "||'-' " + " ||"+To_date[2]+",'mm-yyyy')";
				head="CASH BOOK FOR THE PERIOD FROM "+monthInWords_1+" - "+Frm_date[2]+" TO "+monthInWords_2+" - "+To_date[2];
   }else if(Hid_text.equalsIgnoreCase("MonthWise")){
	   
	   sub_qry = " a.Cashbook_Month="+txtCB_Month+" and a.Cashbook_Year="+txtCB_Year;
	   head="CASH BOOK FOR THE MONTH OF "+monthInWords+" - "+txtCB_Year;
   }
System.out.println("sub_qry  >>> "+sub_qry);
        if(Re_type==1){
        	System.out.println("Re_type >?>> "+Re_type);
                    if (DisMode.equalsIgnoreCase("Bank")) {
                     
                                reportFile =
                                        new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/twad_CB/fas_cashBook_Data_BAnk.jasper"));
                            
                    } else if (DisMode.equalsIgnoreCase("AccNo")) {
                       
                                reportFile =
                                        new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/twad_CB/fas_cashBook_Data_AcNo.jasper"));
                    }          
                    
        }   else if(Re_type==2){
             
                    reportFile =
                            new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/twad_CB/fas_cashBook_Data_BankWise.jasper"));
           }else if(Re_type==3){
        	
                    reportFile =
                            new File(getServletContext().getRealPath("/org/FAS/FAS1/MIS/jaspers/twad_CB/fas_cashBook_Data_AcNoWise.jasper"));
                       }
          System.out.println("reportFile"+reportFile);   
        if (!reportFile.exists())
        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
        
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
        System.out.println("jasperReport"+jasperReport);
        Map map=new HashMap();    
        map.put("txtCB_Month",txtCB_Month);
        map.put("txtCB_Year",txtCB_Year);        
        map.put("monthvalue",monthInWords);  
        map.put("sub_qry", sub_qry);
        
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);            
        if (rtype.equalsIgnoreCase("HTML"))   
        {
                    response.setContentType("text/html");
                    response.setHeader ("Content-Disposition", "attachment;filename=\"CashBook_Details.html\"");
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
                System.out.println("PDF:::::::::::");
                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    response.setHeader ("Content-Disposition", "attachment;filename=\"CashBook_Details.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
        }
        else      if (rtype.equalsIgnoreCase("EXCEL"))   
        {

                        response.setContentType("application/vnd.ms-excel");
                    response.setHeader ("Content-Disposition", "attachment;filename=\"TWAD_A88.xls\"");
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
                        response.setHeader ("Content-Disposition", "attachment;filename=\"CashBook_Details.txt\"");
                             
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
