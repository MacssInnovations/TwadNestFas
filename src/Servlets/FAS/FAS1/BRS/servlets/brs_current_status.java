package Servlets.FAS.FAS1.BRS.servlets;
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

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.util.JRLoader;

public class brs_current_status extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);


    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        doPost(request, response);

    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        System.out.println("Welcome to Servlet");
        String asondate = request.getParameter("asondate");
        System.out.println(" RType ::: " + asondate);
        Connection connection = null;
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
String sub_qry="",type_valu="";

        
        String online_status=request.getParameter("online_status");
        String type_report=request.getParameter("type_report");
    	String head="",sub_heading="";
        if(type_report.equals("ALL")){
        	sub_qry="";
        //	String head="",sub_heading="";
        }else{
        
        	type_valu=request.getParameter("sel");
        	if(type_valu.equals("Main")||type_valu.equals("Support")){
        		 if(online_status.equals("ALL")|| (online_status.equals("F")))
        	sub_qry=" where ac_operational_mode_id like '%"+type_valu+"%' ";	
        		
        		 else
        			 sub_qry=" and ac_operational_mode_id like '%"+type_valu+"%' ";	
        		 
        		 
        	}else{
        		 if(online_status.equals("ALL")|| (online_status.equals("F")))
        		sub_qry=" where ac_operational_mode_id like '"+type_valu+"' ";
        		 else
        			 sub_qry=" and ac_operational_mode_id like '"+type_valu+"' ";
        	}
        }
    
        response.setContentType(CONTENT_TYPE);
        File reportFile = null;
        JasperPrint jasperPrint = null;
        JasperDesign jasperDesign = null;
        try {
            if(online_status.equals("ALL"))
            {
            reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/brs_started_list_for_super_user_all.jasper"));
            }
            else if(online_status.equals("F"))
            {
                reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/brs_started_list_for_super_user_freezed.jasper"));
            }
            else if(online_status.equals("S"))
            {
                reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/brs_started_list_for_super_user_start_notFreeze.jasper"));
            }
            else if(online_status.equals("NS"))
            {
                reportFile =new File(getServletContext().getRealPath("/org/FAS/FAS1/BRS/jaspers/brs_started_list_for_super_user_notstarted.jasper"));
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
