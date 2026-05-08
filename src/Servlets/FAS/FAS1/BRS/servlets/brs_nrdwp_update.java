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
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;

/**
 * Servlet implementation class brs_nrdwp_update
 */
public class brs_nrdwp_update extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE =
	        "text/html; charset=windows-1252";   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public brs_nrdwp_update() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		
        Connection connection = null;String sub_qry="",type_valu="";
    	response.setContentType(CONTENT_TYPE);
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


        String head="",sub_heading="",sub_q="";
        System.out.println("Welcome to Servlet");
        String rtype = request.getParameter("RType");
        System.out.println(" RType ::: " + rtype);
        String asondate = request.getParameter("asondate");
        System.out.println(" RType ::: " + asondate);
        String type_report=request.getParameter("type_report");
        if(type_report.equals("ALL")){
        	//sub_qry="";
        }else{
        
        	type_valu=request.getParameter("sel");
        	if(type_valu.equals("Main")){
        		 sub_heading="OPR_NRDWP_MAIN";
        	sub_qry=" AND BALNCE.AC_OPERATIONAL_MODE_ID LIKE '%"+type_valu+"%' ";
        	sub_q=" AC_OPERATIONAL_MODE_ID LIKE '%"+type_valu+"%'  ";
        	}
        		 else if(type_valu.equals("Support")){
        			 sub_heading="OPR_NRDWP_SUPPORT";
        			 sub_qry=" AND BALNCE.AC_OPERATIONAL_MODE_ID LIKE '%"+type_valu+"%' ";	
        			 sub_q=" AC_OPERATIONAL_MODE_ID LIKE '%"+type_valu+"%'  ";
        		 }
        		 else if(type_valu.equals("FDW")){
        			 sub_heading="FULL DEPOSIT WORK";
        			 sub_qry=" AND BALNCE.AC_OPERATIONAL_MODE_ID LIKE '%"+type_valu+"%' ";	
        			 sub_q=" AC_OPERATIONAL_MODE_ID LIKE '%"+type_valu+"%'  ";
        		 }
        	}
      //  SUBREPORT_DIR 
        String rep=getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/");
 		  rep=rep+"/";
        head=" BANK RECONCIALAIATION ONLINE PROGRESS STATUS REPORT AS ON "+asondate;
    File reportFile = null;
    JasperPrint jasperPrint = null;
    JasperDesign jasperDesign = null;
    try {
        if(type_report.equals("ALL")){
        	//reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/BRS_NRDWP_All.jasper"));
        	reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/BRS_NRDWP_MainNew.jasper"));
        	
        }
        else{
        	reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/BRS_NRDWP_Main.jasper"));
        }
            
            if (!reportFile.exists()) {
                System.out.println("does not exsist");
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            }
            System.out.println("Report Availble");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();
       
            map.put("sel",sub_qry);
            map.put("sub_q",sub_q);
            map.put("SUBREPORT_DIR",rep);
            map.put("head",head);
            map.put("s_head",sub_heading);
            jasperPrint =
                    JasperFillManager.fillReport(jasperReport, map, connection);

            byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
            System.out.println("Length  " + buf.length);
            response.setContentType("application/pdf");
            response.setContentLength(buf.length);
            // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
            //response.setContentType("application/force-download");

            response.setHeader("Content-Disposition",
                               "attachment;filename=\"BRS_ALL.pdf\"");
            OutputStream out = response.getOutputStream();
            out.write(buf, 0, buf.length);
            out.close();
            return;


        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage() + " " +
                ex.getStackTrace();
            ex.printStackTrace();
            System.out.println(connectMsg);
        }
    }

}

