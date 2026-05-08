package Servlets.FAS.FAS1.Masters.servlets;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;

import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.SQLException;

import java.util.HashMap;
import java.util.Map;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

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

public class OtherDepartmentsDetails extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    /*public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException, IOException {response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();
        out.println("<html>");
        out.println("<head><title>OtherDepartmentsDetails</title></head>");
                out.println("<body>");
        Connection connection=null;
        PreparedStatement ps=null;
        ResultSet rs=null;
        String xml="";

        xml="<response><command>loadDetails</command>";
        try{
            String txtOffice_id=request.getParameter("txtOffice_id");
            System.out.println("inside.........");
            System.out.println(txtOffice_id);
            ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rs1.getString("Config.DSN");
            String strhostname=rs1.getString("Config.HOST_NAME");
            String strportno=rs1.getString("Config.PORT_NUMBER");
            String strsid=rs1.getString("Config.SID");
            String strdbusername=rs1.getString("Config.USER_NAME");
            String strdbpassword=rs1.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());


            System.out.println("connected");
            ps=connection.prepareStatement("select a.other_dept_id,a.other_dept_name,b.other_dept_office_id,b.other_dept_office_name from hrm_mst_other_depts a"+
                                            " inner join hrm_mst_other_dept_offices b on a.other_dept_id=b.other_dept_id where b.other_dept_office_id=?");
            ps.setString(1,txtOffice_id);
            rs=ps.executeQuery();
           int count=0;

            while(rs.next()) {
                    //xml=xml+"<result>ok</result>";
                    String s=rs.getString(1);
                    System.out.println(s);
                    System.out.println(rs.getString(2));
                    System.out.println(rs.getString(3));
                    System.out.println(rs.getString(4));
                    //System.out.println(rs.getString(5));
                    xml=xml+"<details>";
                    xml=xml+"<dept_id>"+rs.getString(1)+"</dept_id>";
                    xml=xml+"<dept_name>"+rs.getString(2)+"</dept_name>";
                    xml=xml+"<office_id>"+rs.getString(3)+"</office_id>";
                    xml=xml+"<office_name>"+rs.getString(4)+"</office_name>";
                    xml=xml+"</details>";
                    //xml=xml+"<result>ok</result>";
                    count++;
                }
                if(count>0)
                {
                    xml=xml+"<result>ok</result>";
                }
                else
                {
                    xml=xml+"<result>fail</result>";
                }

        }catch(Exception e) {
            //xml=xml+"<flag>Failure</flag>";
            xml=xml+"<result>fail</result>";
            System.out.println("Error in getting values");
        }
        //xml=xml+"<command></command>";
        xml=xml+"</response>";

        out.println("<p>The servlet has received a GET. This is the reply.</p>");
        out.println(xml);
        out.println("</body></html>");
        out.close();
    }*/


    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        //response.setContentType(CONTENT_TYPE);
        //PrintWriter out = response.getWriter();

        //response.setContentType(CONTENT_TYPE);
        //      PrintWriter out = response.getWriter();
        //    out.println("<html>");
        //  out.println("<head><title>OtherDepartmentsDetails</title></head>");
        //        out.println("<body>");
        Connection connection = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        String txtDept_id = "";

        //xml="<response><command>loadDetails</command>";*/
        try {
            txtDept_id = request.getParameter("cmbControllingLevel");
            System.out.println("inside.........");
            System.out.println(txtDept_id);
            ResourceBundle rs1 =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rs1.getString("Config.DSN");
            String strhostname = rs1.getString("Config.HOST_NAME");
            String strportno = rs1.getString("Config.PORT_NUMBER");
            String strsid = rs1.getString("Config.SID");
            String strdbusername = rs1.getString("Config.USER_NAME");
            String strdbpassword = rs1.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection


            Class.forName(strDriver.trim());

            connection =
                    DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                                                strdbpassword.trim());


            System.out.println("connected");
            ps =
  connection.prepareStatement("select a.other_dept_id,a.other_dept_name,b.other_dept_office_id,b.other_dept_office_name from hrm_mst_other_depts a" +
                              " inner join hrm_mst_other_dept_offices b on a.other_dept_id=b.other_dept_id where a.other_dept_id=?");
            ps.setString(1, txtDept_id);
            rs = ps.executeQuery();
        } catch (Exception e) {
            System.out.println("Error");
        }
        System.out.println("Helloooo");
        //String s=request.getParameter("hdet");
        //System.out.println(s);
        JasperDesign jasperDesign = null;
        File reportFile = null;
        //String txtDept_id=request.getParameter("cmbControllingLevel");
        try {
            System.out.println("calling servlet");
            reportFile =
                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/OtherOfficesReport.jasper"));
            System.out.println(getServletContext().getRealPath("/WEB-INF/ReportSrc/OtherOfficesReport.jasper"));
            if (!reportFile.exists())
                throw new JRRuntimeException("File is not found. The report design must be compiled first.");

            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            //System.out.println(reportFile.getPath()+"path");
            Map map = null;
            map = new HashMap();
            map.put("deptid", txtDept_id); //remember me
            // map.put("offids",5001);
            JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, connection);

            String rtype = request.getParameter("cmbReportType");
            System.out.println(rtype);
            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"OtherOfficesReport.html\"");
                PrintWriter out = response.getWriter();
                JRHtmlExporter exporter = new JRHtmlExporter();
                // File f=new File(getServletContext().getRealPath("/WEB-INF/Report/"));
                //  exporter.setParameter(JRHtmlExporterParameter.IS_OUTPUT_IMAGES_TO_DIR,true);
                //  exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR_NAME,getServletContext().getRealPath("/WEB-INF/Report/"));
                //  exporter.setParameter(JRHtmlExporterParameter.IMAGES_DIR,f);
                exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
                                      false);
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                      jasperPrint);
                exporter.setParameter(JRExporterParameter.OUTPUT_WRITER, out);
                exporter.exportReport();
                out.flush();
                out.close();
            } else if (rtype.equalsIgnoreCase("PDF")) {
                byte buf[] =
                    JasperExportManager.exportReportToPdf(jasperPrint);
                response.setContentType("application/pdf");
                response.setContentLength(buf.length);
                // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                //response.setContentType("application/force-download");

                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"OtherOfficesReport.pdf\"");
                OutputStream outs = response.getOutputStream();
                outs.write(buf, 0, buf.length);
                outs.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {

                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"OtherOfficesReport.xls\"");
                JRXlsExporter exporterXLS = new JRXlsExporter();
                exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                                         jasperPrint);

                ByteArrayOutputStream xlsReport = new ByteArrayOutputStream();
                exporterXLS.setParameter(JRXlsExporterParameter.OUTPUT_STREAM,
                                         xlsReport);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_ONE_PAGE_PER_SHEET,
                                         Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_AUTO_DETECT_CELL_TYPE,
                                         Boolean.TRUE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_WHITE_PAGE_BACKGROUND,
                                         Boolean.FALSE);
                exporterXLS.setParameter(JRXlsExporterParameter.IS_REMOVE_EMPTY_SPACE_BETWEEN_ROWS,
                                         Boolean.TRUE);
                exporterXLS.exportReport();
                byte[] bytes;
                bytes = xlsReport.toByteArray();
                ServletOutputStream ouputStream = response.getOutputStream();
                ouputStream.write(bytes, 0, bytes.length);
                ouputStream.flush();
                ouputStream.close();

            } else if (rtype.equalsIgnoreCase("TXT")) {

                response.setContentType("text/plain");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"OfficeDetail.txt\"");

                JRTextExporter exporter = new JRTextExporter();
                exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                      jasperPrint);
                ByteArrayOutputStream txtReport = new ByteArrayOutputStream();
                exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                      txtReport);
                exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,
                                      new Integer(200));
                exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,
                                      new Integer(50));
                exporter.exportReport();

                byte[] bytes;
                bytes = txtReport.toByteArray();
                ServletOutputStream ouputStream = response.getOutputStream();
                ouputStream.write(bytes, 0, bytes.length);
                ouputStream.flush();
                ouputStream.close();

            }


        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }

    }

}

