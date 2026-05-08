package Servlets.HR.HR1.EmployeeMaster.servlets;

import java.io.IOException;
import java.io.PrintWriter;
//import net.sf.jasperreports.engine.*;
import javax.servlet.*;
import javax.servlet.http.*;

//import net.sf.jasperreports.engine.design.JasperDesign;


public class Servlet1 extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        System.out.println("servlet called");
        out.println("<html>");
        try {
            /*JasperDesign jasperDesign
            = JasperManager.loadXmlDesign("MyReport.xml");
        JasperReport jasperReport
            = JasperManager.compileReport(jasperDesign);*/
        } catch (Exception e) {
            System.out.println("hai");
        }
        out.println("<head><title>Servlet1</title></head>");
        out.println("<body>");
        out.println("<p>The servlet has received a POST. This is the reply.</p>");
        out.println("</body></html>");
        out.close();
    }
}
