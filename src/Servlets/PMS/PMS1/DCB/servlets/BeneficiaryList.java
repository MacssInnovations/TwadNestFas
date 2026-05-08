package Servlets.PMS.PMS1.DCB.servlets;

import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Statement;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporter;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.export.JRPdfExporter;

import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class BeneficiaryList (Report)
 */
public class BeneficiaryList extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        System.out.println("\n=========================================\nDOGET\n==================================\n");
        doPost(request, response);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        System.out.println("\n=========================================\nDOPOSTT\n==================================\n");
        Connection connection = null;
        /*
         * Get the Database Connection Object
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
//            ConnectionString =
//                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
//                    ":" + strsid.trim();
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


        String action = null;

        String type = null;
        String dis = null;
        String blk = null;


        /******************************************************************************
         * 					ACTION PARAMETERS
         ******************************************************************************/
        try {
            action = request.getParameter("action");
            System.out.println("The Action = " + action);
        } catch (Exception e) {
            System.out.println("Exception getting action parameter ==> " + e);
        }
        /******************************************************************************/


        /******************************************************************************
         * 					OTHER PARAMETERS
         ******************************************************************************/
        try {
            type = request.getParameter("type");
            if ((type == null) || (type == "")) {
                type = "%";
            }
            System.out.println("type: " + type);
        } catch (Exception e) {
            System.out.println("Exception getting type parameter ==> " + e);
        }


        try {
            dis = request.getParameter("dis");
            if ((dis == null) || (dis == "")) {
                dis = "%";
            }
            System.out.println("dis: " + dis);
        } catch (Exception e) {
            System.out.println("Exception getting dis parameter ==> " + e);
        }


        try {
            blk = request.getParameter("blk");
            if ((blk == null) || (blk == "")) {
                blk = "%";
            }
            System.out.println("blk: " + blk);
        } catch (Exception e) {
            System.out.println("Exception getting blk parameter ==> " + e);
        }
        /******************************************************************************/


        PreparedStatement ps = null;
        ResultSet result = null;


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


        /******************************************************************************
         * 			DIVISION / OFFICE NAME
         ******************************************************************************/
        UserProfile empProfile =
            (UserProfile)session.getAttribute("UserProfile");
        System.out.println("user id::" + empProfile.getEmployeeId());

        int oid = 0;
        String oname = "";

        try {
            ps =
  connection.prepareStatement("SELECT " + "  OFFICE_ID, " + "	OFFICE_NAME " +
                              "FROM COM_MST_OFFICES " +
                              "WHERE OFFICE_ID = ( " +
                              "                    SELECT  " +
                              "                      OFFICE_ID  " +
                              "                    FROM HRM_EMP_CURRENT_POSTING " +
                              "                    WHERE EMPLOYEE_ID=? " +
                              "                  )");
            ps.setInt(1, empProfile.getEmployeeId());
            result = ps.executeQuery();
            if (result.next()) {
                oid = Integer.parseInt(result.getString("OFFICE_ID"));
                System.out.println("Office id - " + oid);

                oname = result.getString("OFFICE_NAME");
                System.out.println("Division - " + oname);
            }
            result.close();
            ps.close();
        } catch (Exception e) {
            System.out.println(e);
        }
        /*****************************************************************************/


        if (action.equalsIgnoreCase("ListAll")) {
            /*
             * Get the URL Path Information for Referential Parameter in the PDF
             */
            Map parameters = new HashMap();
            parameters.put("Div", oname);
            parameters.put("Off", oid);
            parameters.put("Type", type);
            parameters.put("Dis", dis);
            parameters.put("Blk", blk);
            String path = "";
            try {
                path =
getServletContext().getRealPath("/WEB-INF/ReportSrc/BeneficiaryList.jasper");
                JasperPrint jasperPrint =
                    JasperFillManager.fillReport(path, parameters, connection);
                System.out.println("Report is Created from Jasper Print...");

                OutputStream outuputStream = response.getOutputStream();

                JRExporter exporter = null;

                response.setContentType("application/pdf");
                response.setHeader("Content-Disposition",
                                   "attachment; filename=\"BeneficiaryList.pdf\"");
                exporter = new JRPdfExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                      jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                      outuputStream);
                exporter.exportReport();
                System.out.println("The File is Downloaded");
                outuputStream.close();
            } catch (JRException e) {
                throw new ServletException(e);
            }
        }
    }
}

