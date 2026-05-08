package Servlets.FAS.FAS1.Masters.servlets;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

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
import net.sf.jasperreports.engine.util.JRLoader;

public class Asset_6_serv extends HttpServlet {
    /**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        /**
         * Variables Declaration
         */
        Connection connection = null;
        Statement statement = null;
       
        /**
         * Database Connection
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
            try {
                statement = connection.createStatement();
                connection.clearWarnings();
            } catch (SQLException e) {
                System.out.println("Exception in creating statement:" + e);
            }
        } catch (Exception e) {
            System.out.println("Exception in openeing connection:" + e);
        }
        response.setContentType(CONTENT_TYPE);


        /**
         * Session Checking
         */
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
        int cmbAcc_UnitCode=0,major_code=0;
        String fin_year="";
        String rep_type="",command="";
        try{
        	 cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
         	command=request.getParameter("command");
        	major_code=Integer.parseInt(request.getParameter("major_cmb"));
        	if(major_code==0)major_code=0;
        	fin_year=request.getParameter("cmbFinancial_Year");
            rep_type=request.getParameter("rep_type");
            System.out.println("cmbAcc_UnitCode >>> "+cmbAcc_UnitCode);
        	System.out.println("major_cmb >>> "+major_code);
        	 System.out.println("fin_year >>> "+fin_year);
         	System.out.println("rep_type >>> "+rep_type);
         	System.out.println("command >>> "+command);
        }catch (Exception e) {
			System.out.println(e);
		}
        /**
            *  iReport Calling
            */
        File reportFile = null;
        try {
        	if(command.equalsIgnoreCase("A6")){
if(rep_type.equalsIgnoreCase("All")){
                reportFile =
                        new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Masters/jasper/A6/Asset(6)_All_Report.jasper"));
}
if(rep_type.equalsIgnoreCase("OneMajorType")){
    reportFile =
            new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Masters/jasper/A6/Asset(6)_Report.jasper"));
}
if(rep_type.equalsIgnoreCase("AllMajorType")){
    reportFile =
            new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Masters/jasper/A6/Asset(6)_All_Detail.jasper"));
}
        }
if(command.equalsIgnoreCase("A52")){
	if(rep_type.equalsIgnoreCase("All")){
        reportFile =
                new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Masters/jasper/A-52/A52_CENREG_Abstract.jasper"));
}
if(rep_type.equalsIgnoreCase("OneMajorType")){
reportFile =
    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Masters/jasper/A-52/A52_CENREG_one_Maj.jasper"));
}
if(rep_type.equalsIgnoreCase("AllMajorType")){
reportFile =
    new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/Masters/jasper/A-52/A52_CENREG_Detail.jasper"));
}
}
   
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            System.out.println("from ...");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();
          
            map.put("Unit_Code",cmbAcc_UnitCode );
            map.put("fin_year", fin_year);
            map.put("major", major_code);

            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);

            String rtype = "PDF";
            if (rtype.equalsIgnoreCase("PDF")) {
                System.out.println(rtype + "...inside PDF");
                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                OutputStream out = response.getOutputStream();
                out.write(buf, 0, buf.length);
                out.close();
            }

        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage();
            System.out.println(connectMsg);

        }

    }
}
