package Servlets.FAS.FAS1.BRS.servlets;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.sql.Connection;
import java.sql.DriverManager;
import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class FAs_Brs_Report
 */
public class FAs_Brs_Report extends HttpServlet {
	private static final long serialVersionUID = 1L;
       
    /**
     * @see HttpServlet#HttpServlet()
     */
    public FAs_Brs_Report() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		System.out.println("welcome to servlet FAs_Brs_Report.java");
		Connection con=null;
		try{
			String con_srt="";
		ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");

        String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
        String strdsn = rs1.getString("Config.DSN");
        String strhostname = rs1.getString("Config.HOST_NAME");
        String strportno = rs1.getString("Config.PORT_NUMBER");
        String strsid = rs1.getString("Config.SID");
        String strdbusername = rs1.getString("Config.USER_NAME");
        String strdbpassword = rs1.getString("Config.PASSWORD");
        Class.forName(strDriver);
        con_srt=strdsn+"@"+strhostname+":"+strportno+":"+strsid;
        con=DriverManager.getConnection(con_srt, strdbusername, strdbpassword);
		}catch (Exception e) {
			e.printStackTrace();
		}
		System.out.println(con);
		File reportFile=null;
		try{
		reportFile=new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/BrsReport_New.jasper"))	;
		if(!reportFile.exists()){
			throw new JRRuntimeException("RunTime Error");
		}
	//	JasperReport jasperreport=(JasperReport) JRLoader.loadObject(reportFile.getPath());
	//	JasperFillManager fillmanager=
		Map m=new HashMap();
		JasperReport jr=(JasperReport) JRLoader.loadObject(reportFile.getPath());
		JasperPrint jp=JasperFillManager.fillReport(jr, m,con);
		
	      
    	
        byte buf[] =
            JasperExportManager.exportReportToPdf(jp);
        response.setContentType("application/pdf");
        response.setContentLength(buf.length);
        response.setHeader("Content-Disposition",
                           "attachment;filename=\"BRS Report.pdf\"");
        OutputStream out = response.getOutputStream();
        out.write(buf, 0, buf.length);
        System.out.println("testing***"+jp);
        out.close();
    
		}catch (Exception e) {
			e.printStackTrace();
		}
		
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		doGet(request, response);
	}

}
