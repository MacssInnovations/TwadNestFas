package Servlets.PMS.PMS1.DCB.reports;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.PMS.PMS1.DCB.servlets.Controller;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class OpeningBalanceReport extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection connection = null;
        Statement statement = null;
        ResultSet result = null;
        // PreparedStatement ps=null;
        Controller obj = null;
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

//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
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
        String strCommand = "", mvalue = "";
        String flag = "DCB";
        String xml = "";
        int month = 0;
        int year = 0;
        // Office id & user id Start
        String Office_id = "0", Office_name = "";
        String userid = "0";
        HttpSession session = request.getSession(false);
        userid = (String)session.getAttribute("UserId");
        if (userid == null) {

            response.sendRedirect(request.getContextPath() + "/index.jsp");
        }
        try {

            Connection con = null;
            obj = new Controller();
            con = obj.con();
            Statement stmt = null;
            stmt = con.createStatement();
            Office_id =
                    obj.getValue("HRM_EMP_CURRENT_POSTING", "OFFICE_ID", "where EMPLOYEE_ID in ( select EMPLOYEE_ID	 from SEC_MST_USERS where USER_ID='" +
                                 userid + "')");

            Office_name =
                    obj.getValue("COM_MST_OFFICES", "OFFICE_NAME", "where OFFICE_ID=" +
                                 Office_id);

            //month = Integer.parseInt(obj.isNull(obj.getValue("PMS_DCB_SETTING",
            //	"MONTH", " where OFFICE_ID=" + Office_id), 1));


            month = Integer.parseInt(request.getParameter("month"));
            year =
Integer.parseInt(obj.isNull(obj.getValue("PMS_DCB_SETTING", "YEAR",
                                         "where OFFICE_ID=" + Office_id), 1));


            System.out.println("Check Month -------> " + month);
            mvalue = obj.month_val(Integer.toString(month));
            System.out.println("Check Month Value -------> " + mvalue);


            if (Office_name.equals(""))
                Office_name = "";
        } catch (Exception e) {
            //   handle exception
        }
        // Office id & user id End

        int ben = 0;
        int count = 2;

        int oid = 0;

        // HttpSession session=request.getSession(false);
        try {

            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");

            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        // String userid=(String)session.getAttribute("UserId");
        System.out.println("Session id is:" + userid);

        /*******************************************************************
		 * Command parameters
		 *******************************************************************/
        try {
            strCommand = request.getParameter("command");
            System.out.println("strCommand : " + strCommand);
        } catch (Exception e) {
            e.printStackTrace();
        }

        try {
            flag = request.getParameter("flag");
            System.out.println("flag : " + flag);
        } catch (Exception e) {
            e.printStackTrace();
        }
        /******************************************************************/

        /*******************************************************************
		 * Other parameters
		 *******************************************************************/
        try {
            count = Integer.parseInt(request.getParameter("count"));
            System.out.println("count : " + count);
        } catch (Exception e) {
            System.out.println("Exception fetching count ===> " + e);
        }

        try {
            month = Integer.parseInt(request.getParameter("month"));
            System.out.println("month : " + month);
        } catch (Exception e) {
            System.out.println("Exception fetching month ===> " + e);
        }

        try {
            year = Integer.parseInt(request.getParameter("year"));
            System.out.println("year : " + year);
        } catch (Exception e) {
            System.out.println("Exception fetching year ===> " + e);
        }

        try {
            ben = Integer.parseInt(request.getParameter("ben"));
            System.out.println("ben : " + ben);
        } catch (Exception e) {
            System.out.println("Exception fetching ben ===> " + e);
        }

        /******************************************************************/

        /*
		 * Execute the task adhered to the requested command
		 */

        /* Region and Division Wise Beneficiary  Opening Balance Report */
        File reportFile = null;
        String inp_month = "", inp_year = "";
        int report_office_id = 0;
        if (strCommand.equalsIgnoreCase("Region_Wise_Op")) {

            try {

                System.out.println("Report generated.. 1 ");
                try {
                    report_office_id =
                            Integer.parseInt(request.getParameter("report_office_id"));

                } catch (Exception e) {
                    System.out.println("Exception fetching year ===> " + e);
                }
                reportFile =
                        new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Ben_Ob_Rpt.jasper"));
                if (!reportFile.exists())
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");

                JasperReport jasperReport =
                    (JasperReport)JRLoader.loadObject(reportFile.getPath());
                System.out.println("Report generated.. 2 ");
                Map map1 = new HashMap();
                map1.put("office_id", report_office_id);
                map1.put("year", year);
                map1.put("month", month);
                map1.put("mvalue", mvalue);

                JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperReport, map1,
                                                 connection);

                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"OpeningBalanceDCB_All.pdf\"");
                System.out.println("Report generated.. 3 ");
                OutputStream out = response.getOutputStream();
                out.write(buf);
                // out.write(buf, 0, buf.length);
                // out.close();
                System.out.println("Report generated..");
            } catch (Exception e) {
                // TODO: handle exception
            }

        }

        /* Region and Division Wise Beneficiary  Opening Balance Report */


        if (strCommand.equalsIgnoreCase("Report")) {
            System.out.println("\n*************\nReport\n**************\n");
            /*
			 * Get the URL Path Information for Referential Parameter in the PDF
			 */

            try {


                if ("DCB".equalsIgnoreCase(flag)) {

                    inp_month = obj.setValue("month", request);
                    inp_year = obj.setValue("year", request);

                    if (inp_month.equalsIgnoreCase("") ||
                        inp_month.equalsIgnoreCase("0")) {
                        month = month;
                        year = year;
                    } else {
                        month = Integer.parseInt(inp_month);
                        year = Integer.parseInt(inp_year);

                    }

                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OpeningBalanceDCB_All.jasper"));

                } else {
                    reportFile =
                            new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OpeningBalanceInt.jasper"));
                }

                if (!reportFile.exists())
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");

                JasperReport jasperReport =
                    (JasperReport)JRLoader.loadObject(reportFile.getPath());

                Map map = new HashMap();
                map.put("office_id", Office_id);
                map.put("year", year);
                map.put("month", month);
                map.put("mvalue", mvalue);
                map.put("divname", Office_name);

                JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperReport, map,
                                                 connection);

                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                // response.setHeader("content-disposition",
                // "inline;filename=OpenActionItems.pdf");
                // response.setContentType("application/force-download");

                if ("DCB".equalsIgnoreCase(flag)) {
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"OpeningBalanceDCB_All.pdf\"");
                } else {
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"OpeningBalanceInt.pdf\"");
                }
                OutputStream out = response.getOutputStream();
                out.write(buf);
                // out.write(buf, 0, buf.length);
                // out.close();
                System.out.println("Report generated..");
            } catch (Exception ex) {
                String connectMsg =
                    "Could not create the report " + ex.getMessage() + " " +
                    ex.getLocalizedMessage();
                System.out.println(connectMsg);
            }

        } else if (strCommand.equals("Year")) {
            response.setContentType("text/xml");
            PrintWriter pw = response.getWriter();
            response.setHeader("Cache-Control", "no-cache");

            System.out.println("\n*************\nYear\n**************\n");
            xml = "<response><command>Year</command>";
            try {
                int yr = java.util.Calendar.getInstance().get(Calendar.YEAR);
                for (int i = count - 1; i >= 0; i--) {
                    xml += "<row>";
                    xml += "<year>" + (yr - i) + "</year>";
                    xml += "</row>";
                }
                xml += "<flag>success</flag>";
            } catch (Exception e)

            {
                System.out.println("Exception in generating Years List - " +
                                   e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";

            System.out.println("\nBENEFICIARY XML RESPONSE:");
            System.out.println("xml is : " + xml);
            pw.write(xml);
            pw.flush();
            pw.close();

        }

    }

}
