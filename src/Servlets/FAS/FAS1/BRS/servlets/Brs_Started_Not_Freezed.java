package Servlets.FAS.FAS1.BRS.servlets;

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


public class Brs_Started_Not_Freezed extends HttpServlet {
	private static final long serialVersionUID = 1L;
	 private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
	 Connection connection = null;
       
    
    public Brs_Started_Not_Freezed() {
        super();
        // TODO Auto-generated constructor stub
    }

	
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		//  System.out.println("BRS_Started_not_Freezed_report::::");
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
    	String offqry="";
       // System.out.println("inside servlet>>>>>>>>>>>>>>>..");
      //  int unit_id=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        int Office_id=Integer.parseInt(request.getParameter("txtRegionId"));
      String txtCB_Year=request.getParameter("txtCB_Year");
        String txtCB_Month=request.getParameter("txtCB_Month");
      //  System.out.println("txtCB_Month>>>>"+txtCB_Month);
    
        int yearfrom=Integer.parseInt(txtCB_Year);
        int monthfrom=Integer.parseInt(txtCB_Month);
       if(Office_id==101){
    	   offqry="";
       }else{
    	  
    	   offqry="and b1.accounting_for_office_id= "+Office_id ;
       }
      
        /*  String monthInWords="";
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
            monthInWords="December";*/
   String heading="BRS Started But Not Freezed ";
			/*String qry = "SELECT CASHBOOK_YEAR, "
					+ "  ACCOUNTING_FOR_OFFICE_ID, "
					+ "  ACCOUNTING_UNIT_ID, "
					+ "  (SELECT accounting_unit_name "
					+ "  FROM fas_mst_acct_units "
					+ "  WHERE accounting_unit_id=mst.ACCOUNTING_UNIT_ID "
					+ "  )AS unit_name, "
					+ "  CASHBOOK_MONTH, "
					+ "  DECODE(CASHBOOK_MONTH,1,'Jan',2,'feb',3,'Mar',4,'Apr',5,'May',6,'June',7,'July',8,'Aug',9,'Sep',10,'Oct',11,'Nov',12,'Dec','--') AS MONTH "
					+ " FROM FAS_BRS_MASTER mst "
					+ " WHERE mst.ACCOUNTING_UNIT_ID NOT IN "
					+ "  (SELECT ACCOUNTING_UNIT_ID FROM FAS_BRS_MONTHLY_CLOSURE "
					+ "  ) " 
					//+ " AND MST.ACCOUNTING_UNIT_ID      = " + unit_id
					+ " AND mst.accounting_for_office_id= " + Office_id
					+ " ORDER BY MST.ACCOUNTING_UNIT_ID";*/
   
   
   String qry="select "+
   "  b1.cashbook_year,  "+
   " b1.account_no, "+
   "      b1.accounting_for_office_id, "+
   "      b1.ACCOUNTING_UNIT_ID, "+
   "     (SELECT accounting_unit_name "+
   "  	   FROM fas_mst_acct_units "+
   "  		   WHERE accounting_unit_id= b1.ACCOUNTING_UNIT_ID "+
   "  		   )as unit_name, "+
					   "  		    b1.cashbook_month, "+
					   "  		   decode( b1.cashbook_month,1,'Jan',2,'feb',3,'Mar',4,'Apr',5,'May',6,'June',7,'July',8,'Aug',9,'Sep',10,'Oct',11,'Nov',12,'Dec','--') as month "+ 
					   "  from brs_start_month_and_year b1 inner join fas_brs_master b2 "+ 
					   "  on b1.accounting_unit_id=b2.accounting_unit_id "+
					   "  and b1.accounting_for_office_id=b2.accounting_for_office_id "+
					   "  and b1.cashbook_year=b2.cashbook_year "+
					   "  and b1.cashbook_month=b2.cashbook_month "+
					   "  and b1.account_no=b2.account_no "+
					   "  left outer join fas_brs_monthly_closure b3  "+
					   "  on b1.accounting_unit_id=b3.accounting_unit_id"+
					   "  and b1.accounting_for_office_id=b3.accounting_for_office_id"+
					   "  and b1.cashbook_year=b3.cashbook_year"+
					   "  and b1.cashbook_month=b3.cashbook_month"+
					   "  and b1.account_no=b3.account_no"+

					   "  where b1.accounting_unit_id not in (select accounting_unit_id from fas_brs_monthly_closure)"+
					   "  "+offqry+
					   "  and b1.cashbook_year= "+yearfrom +
					   "  and b1.cashbook_month= "+monthfrom;
   
   
   
        reportFile = new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/Brs_Srt_NotFreezed_Report.jasper")); 
        //  System.out.println("reportFile"+reportFile);   
        if (!reportFile.exists())
        throw new JRRuntimeException("File J not found. The report design must be compiled first.");
        
        JasperReport jasperReport = (JasperReport)JRLoader.loadObject(reportFile.getPath());
      // System.out.println("jasperReport"+jasperReport);
        Map map=new HashMap();
        map.put("heading", heading);
        map.put("qry", qry);
     // System.out.println(qry);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, map, connection);            
       
       
        	//System.out.println("PDF:::::::::::");
                    byte buf[]=JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    response.setHeader ("Content-Disposition", "attachment;filename=\"BRS_Started_notFreezed_Report.pdf\"");
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
