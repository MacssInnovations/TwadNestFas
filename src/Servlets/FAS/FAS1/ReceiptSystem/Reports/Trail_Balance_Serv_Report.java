package Servlets.FAS.FAS1.ReceiptSystem.Reports;

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

public class Trail_Balance_Serv_Report extends HttpServlet {
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
        int from_year=0,from_month=0,to_year=0,to_month=0;
        String m="",m1="";
        
        try{
        	from_year=Integer.parseInt(request.getParameter("txtCB_Year_From"));
        	from_month=Integer.parseInt(request.getParameter("txtCB_Month_From"));
        	to_year=Integer.parseInt(request.getParameter("txtCB_Year_To"));
        	to_month=Integer.parseInt(request.getParameter("txtCB_Month_To"));
            
        	System.out.println("from_year >>> "+from_year);
        	 System.out.println("from_month >>> "+from_month);
         	System.out.println("to_year >>> "+to_year);
         	System.out.println("txtCB_Month_To >>> "+to_month);
        }catch (Exception e) {
			System.out.println(e);
		}
        if (from_month == 01) {
			m = "JAN";
		} else if (from_month == 02) {
			m = "FEB";
		} else if (from_month == 03) {
			m = "MAR";
		} else if (from_month == 04) {
			m = "APR";
		} else if (from_month == 05) {
			m = "MAY";
		} else if (from_month == 06) {
			m = "JUN";
		} else if (from_month == 07) {
			m = "JUL";
		} else if (from_month == 8) {
			m = "AUG";
		} else if (from_month == 9) {
			m = "SEP";
		} else if (from_month == 10) {
			m = "OCT";
		} else if (from_month == 11) {
			m = "NOV";
		} else if (from_month == 12) {
			m = "DEC";
		}

		if (to_month == 01) {
			m1 = "JAN";
		} else if (to_month == 02) {
			m1 = "FEB";
		} else if (to_month == 03) {
			m1 = "MAR";
		} else if (to_month == 04) {
			m1 = "APR";
		} else if (to_month == 05) {
			m1 = "MAY";
		} else if (to_month == 06) {
			m1 = "JUN";
		} else if (to_month == 07) {
			m1 = "JUL";
		} else if (to_month == 8) {
			m1 = "AUG";
		} else if (to_month == 9) {
			m1 = "SEP";
		} else if (to_month == 10) {
			m1 = "OCT";
		} else if (to_month == 11) {
			m1 = "NOV";
		} else if (to_month == 12) {
			m1 = "DEC";
		}
        /**
            *  iReport Calling
            */
        File reportFile = null;
        try {
        	
        		if(to_month==3)
        		{
                    System.out.println("Report for Full Fin Year");
        			reportFile =
                            new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Trail_Balance_detail_Report.jasper"));
        		}
        		else if (to_month!=3)
        		{
        			System.out.println("Report for not Full Fin year"); 
        			reportFile =
                             new File(getServletContext().getRealPath("/org/FAS/FAS1/Reports/ReceiptSystem/jasper/Trail_Balance_detail_Report1.jasper"));
        		}
   
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            System.out.println("from ...");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();
          
            map.put("from_year",from_year );
            map.put("from_month", from_month);
            map.put("to_year", to_year);
            map.put("to_month", to_month);
            map.put("m", m);
            map.put("m1", m1);

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
