
package Servlets.HR.HR1.EmployeeMaster.Reports;

import java.io.*;

import java.sql.*;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.export.*;
import net.sf.jasperreports.engine.util.JRLoader;

public class ListofEmployees_Proceed_New extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType("text/xml; charset=UTF-8");
        PrintWriter out = response.getWriter();
        Connection con = null;
        ResultSet resultset = null;
        PreparedStatement ps = null;
        ResultSet resultset1 = null;
        PreparedStatement ps1 = null;
        ResultSet rsnew = null;
        PreparedStatement psnew = null;
        java.sql.CallableStatement cs = null;
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println((new StringBuilder()).append("Exception in connection...").append(e).toString());
        }
        int cnt = 0;
        String command = request.getParameter("Command");
        String proNo = request.getParameter("prono");
        int offid = Integer.parseInt(request.getParameter("officeid"));
        System.out.println((new StringBuilder()).append("proceeding no is").append(proNo).toString());
        System.out.println((new StringBuilder()).append("Office id is").append(offid).toString());
        String xml = "<response>";
        if (command.equalsIgnoreCase("loadEmployee")) {
            xml =
 (new StringBuilder()).append(xml).append("<command>loadEmployee</command>").toString();
            try {
                System.out.println("before query");
                ps =
  con.prepareStatement("select distinct(employee_id),empname,designation,relieval_reason_desc, Proceeding_no,proceeding_date,subject,sub_reference,copyto,addl_para_one,addl_para_2,presiding_officer, presiding_officer_desig,PROCESS_FLOW_STATUS_ID from ( select employee_id,office_id,Relieval_Reason_id,PROCESS_FLOW_STATUS_ID,  Proceeding_no from hrm_emp_relieval_details where proceeding_no=? and office_id=? and process_flow_status_id!='FR' )a left outer join ( select Proceeding_no as prono,office_id as offid,Proceeding_Date,subject,sub_reference,copyto,addl_para_one,addl_para_2, presiding_officer,presiding_officer_desig from hrm_emp_relieval_proceed_new where proceeding_no=? and office_id=? )b on a.Proceeding_no=b.prono  and a.office_id=b.offid left outer join ( select employee_id as empid1,(employee_prefix||'.'||employee_name||'.'||employee_initial) as empname from hrm_mst_employees )c on a.employee_id=c.empid1 left outer join  ( select relieval_reason_id as reasonid,relieval_reason_desc from hrm_mst_relieval_reasons )d on a.relieval_reason_id=d.reasonid left outer join ( select employee_id as empid2,designation_id from hrm_emp_current_posting  )e on a.employee_id=e.empid2 left outer join ( select designation_id as desigid,designation from hrm_mst_designations )f on e.designation_id=f.desigid");
                ps.setString(1, proNo);
                ps.setInt(2, offid);
                ps.setString(3, proNo);
                ps.setInt(4, offid);
                System.out.println("before executing query");
                resultset = ps.executeQuery();
                System.out.println("After executing query");
                while (resultset.next()) {
                    xml =
 (new StringBuilder()).append(xml).append("<pro_no>").append(resultset.getString("PROCEEDING_NO")).append("</pro_no><ref>").append(resultset.getString("SUB_REFERENCE")).append("</ref><sub>").append(resultset.getString("SUBJECT")).append("</sub><copyto>").append(resultset.getString("COPYTO")).append("</copyto>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<pr_off>").append(resultset.getString("PRESIDING_OFFICER")).append("</pr_off><pr_desig>").append(resultset.getString("PRESIDING_OFFICER_DESIG")).append("</pr_desig><addlparaone>").append(resultset.getString("ADDL_PARA_ONE")).append("</addlparaone><addlparatwo>").append(resultset.getString("ADDL_PARA_2")).append("</addlparatwo>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<pr_date>").append(resultset.getDate("proceeding_date")).append("</pr_date>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<details>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<empid>").append(resultset.getString("EMPLOYEE_ID")).append("</empid>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<empname>").append(resultset.getString("empname")).append("</empname>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<empdesig>").append(resultset.getString("designation")).append("</empdesig>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<emprelstatus>").append(resultset.getString("relieval_reason_desc")).append("</emprelstatus>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<processflow>").append(resultset.getString("PROCESS_FLOW_STATUS_ID")).append("</processflow>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("</details>").toString();
                    cnt++;
                }
                if (cnt != 0)
                    xml =
 (new StringBuilder()).append(xml).append("<flag>success</flag>").toString();
                else
                    xml =
 (new StringBuilder()).append(xml).append("<flag>failure</flag>").toString();
            } catch (Exception e) {
                System.out.println((new StringBuilder()).append("Error in getting the employee details ").append(e).toString());
            }
            xml =
 (new StringBuilder()).append(xml).append("</response>").toString();
            System.out.println((new StringBuilder()).append("xml is :").append(xml).toString());
            out.println(xml);
        } else if (command.equalsIgnoreCase("save")) {
            String sub = null;
            String ref = null;
            String addlParaOne = null;
            String addlParaTwo = null;
            String preno = null;
            String predateparam = null;
            String preoff = null;
            String predesig = null;
            String copyto = null;
            java.util.Date predate = null;
            preno = request.getParameter("txtPNo");
            predateparam = request.getParameter("txtPDat");
            preoff = request.getParameter("txtPO");
            predesig = request.getParameter("txtPODesig");
            copyto = request.getParameter("txtCopy");
            sub = request.getParameter("txtSubject");
            ref = request.getParameter("txtRef");
            addlParaOne = request.getParameter("txtPara1");
            addlParaTwo = request.getParameter("txtPara2");
            try {
                ps =
  con.prepareStatement("insert into hrm_emp_relieval_proceed_new(proceeding_no,proceeding_date,subject,sub_reference,addl_para_one,addl_para_2,copyto, presiding_officer,presiding_officer_desig) values(?,?,?,?,?,?,?,?,?)");
                ps.setString(1, preno);
                ps.setString(2, predateparam);
                ps.setString(2, sub);
                ps.setString(3, ref);
                ps.setString(4, addlParaOne);
                ps.setString(5, addlParaTwo);
                ps.setString(6, copyto);
                ps.setString(7, preoff);
                ps.setString(8, predesig);
                int x = ps.executeUpdate();
                System.out.println("Records are inserterd Successfully...");
            } catch (Exception e) {
                System.out.println((new StringBuilder()).append("Error in updating data ").append(e).toString());
            }
        }
        out.close();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        System.out.println("Report Generation");
        Connection con = null;
        String Proc_Status = "";
        PreparedStatement psnew = null;
        ResultSet rsnew = null;
        PreparedStatement pst = null;
        ResultSet rst = null;
        PreparedStatement pst1 = null;
        ResultSet rst1 = null;
        int eid = 0;
        int txtRel_SLNO = 0;
        int txtOffId = 0;
        String txtEmployeeid = "";
        String predateparam = null;
        String sub = null;
        String ref = null;
        String addlParaOne = null;
        String addlParaTwo = null;
        String preno = null;
        String preoff = null;
        String predesig = null;
        String copyto = null;
        java.util.Date predate = null;
        try {
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println((new StringBuilder()).append("Exception in openeing connection :").append(e).toString());
        }
        String prono = request.getParameter("cmbProceed");
        int offid = Integer.parseInt(request.getParameter("txtOffId"));
        String empidsnew = request.getParameter("txtseloff");
        String empids = "";
        try {
            psnew =
con.prepareStatement("select employee_id,PROCESS_FLOW_STATUS_ID from HRM_EMP_RELIEVAL_DETAILS where proceeding_no=? and office_id=?");
            System.out.println((new StringBuilder()).append("select employee_id,PROCESS_FLOW_STATUS_ID from HRM_EMP_RELIEVAL_DETAILS where proceeding_no=").append(prono).append("and office_id=").append(offid).toString());
            psnew.setString(1, prono);
            psnew.setInt(2, offid);
            for (rsnew = psnew.executeQuery(); rsnew.next();
                 System.out.println(txtEmployeeid)) {
                Proc_Status = rsnew.getString("PROCESS_FLOW_STATUS_ID");
                txtEmployeeid =
                        (new StringBuilder()).append(txtEmployeeid).append(rsnew.getInt("Employee_id")).append(",").toString();
            }

            System.out.println((new StringBuilder()).append("empid is -----------").append(txtEmployeeid).toString());
            empids = txtEmployeeid.substring(0, txtEmployeeid.length() - 1);
            System.out.println((new StringBuilder()).append("empids are   :::::::").append(empids).toString());
        } catch (Exception e) {
            System.out.println((new StringBuilder()).append("Error in retrieving process flow status id").append(e).toString());
        }
        int cnt = 0;
        boolean flag = false;
        preno = request.getParameter("cmbProceed");
        predateparam = request.getParameter("txtPDat");
        java.sql.Date dateto = null;
        System.out.println("before converting date");
        String dateString1 = predateparam;
        SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
        java.util.Date d1 = null;
        try {
            d1 = dateFormat1.parse(predateparam.trim());
        } catch (ParseException e) {
            d1 = null;
            System.out.println("error in parsing date");
        }
        dateFormat1.applyPattern("yyyy-MM-dd");
        dateString1 = dateFormat1.format(d1);
        dateto = java.sql.Date.valueOf(dateString1);
        System.out.println((new StringBuilder()).append("Date value is :").append(dateto).toString());
        preoff = request.getParameter("txtPO");
        predesig = request.getParameter("txtPODesig");
        copyto = request.getParameter("txtCopy");
        sub = request.getParameter("txtSubject");
        ref = request.getParameter("txtRef");
        addlParaOne = request.getParameter("txtPara1");
        addlParaTwo = request.getParameter("txtPara2");
        try {
            pst1 =
con.prepareStatement("select * from hrm_emp_relieval_proceed_new where proceeding_no=? and office_id=?");
            pst1.setString(1, preno);
            pst1.setInt(2, offid);
            rst1 = pst1.executeQuery();
            if (rst1.next())
                flag = true;
            else
                flag = false;
        } catch (Exception e) {
            System.out.println((new StringBuilder()).append("throwing an err").append(e).toString());
        }
        System.out.println((new StringBuilder()).append("flag value is         ::").append(flag).toString());
        if (flag)
            try {
                pst =
 con.prepareStatement("update hrm_emp_relieval_proceed_new set proceeding_date=?, presiding_officer=?,presiding_officer_desig=?,subject=?,sub_reference=?,copyto=?,addl_para_one=?, addl_para_2=? where proceeding_no=? and office_id=?");
                pst.setDate(1, dateto);
                pst.setString(2, preoff);
                pst.setString(3, predesig);
                pst.setString(4, sub);
                pst.setString(5, ref);
                pst.setString(6, copyto);
                pst.setString(7, addlParaOne);
                pst.setString(8, addlParaTwo);
                pst.setString(9, preno);
                pst.setInt(10, offid);
                int y = pst.executeUpdate();
            } catch (Exception e) {
                System.out.println((new StringBuilder()).append("Error while updating ").append(e).toString());
            }
        else
            try {
                pst =
 con.prepareStatement("insert into hrm_emp_relieval_proceed_new(proceeding_date, presiding_officer,presiding_officer_desig,subject,sub_reference,copyto,addl_para_one, addl_para_2,proceeding_no,office_id) values(?,?,?,?,?,?,?,?,?,?)");
                pst.setDate(1, dateto);
                pst.setString(2, preoff);
                pst.setString(3, predesig);
                pst.setString(4, sub);
                pst.setString(5, ref);
                pst.setString(6, copyto);
                pst.setString(7, addlParaOne);
                pst.setString(8, addlParaTwo);
                pst.setString(9, preno);
                pst.setInt(10, offid);
                int y = pst.executeUpdate();
            } catch (Exception e) {
                System.out.println((new StringBuilder()).append("Error while inserting ").append(e).toString());
            }
        File reportFile = null;
        try {
            System.out.println("calling servlet");
            reportFile =
                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Relieval_Proceeding_Report_New.jasper"));
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();
            map.put("empid", empidsnew);
            map.put("subject", sub);
            map.put("refer", ref);
            map.put("paraone", addlParaOne);
            map.put("paratwo", addlParaTwo);
            map.put("preno", preno);
            map.put("predate", predateparam);
            map.put("predesig", predesig);
            map.put("preoff", preoff);
            map.put("copyto", copyto);
            net.sf.jasperreports.engine.JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, con);
            String rtype = request.getParameter("RType");
            rtype = "PDF";
            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ListOfDesignation.html\"");
                PrintWriter out = response.getWriter();
                JRHtmlExporter exporter = new JRHtmlExporter();
                exporter.setParameter(JRHtmlExporterParameter.IS_USING_IMAGES_TO_ALIGN,
                                      Boolean.valueOf(false));
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
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ListOfDesignation.pdf\"");
                OutputStream outs = response.getOutputStream();
                outs.write(buf, 0, buf.length);
                outs.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ListOfDesignation.xls\"");
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
                byte bytes[] = xlsReport.toByteArray();
                ServletOutputStream ouputStream = response.getOutputStream();
                ouputStream.write(bytes, 0, bytes.length);
                ouputStream.flush();
                ouputStream.close();
            } else if (rtype.equalsIgnoreCase("TXT")) {
                response.setContentType("text/plain");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"ListOfDesignation.txt\"");
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
                byte bytes[] = txtReport.toByteArray();
                ServletOutputStream ouputStream = response.getOutputStream();
                ouputStream.write(bytes, 0, bytes.length);
                ouputStream.flush();
                ouputStream.close();
            }
        } catch (Exception ex) {
            String connectMsg =
                (new StringBuilder()).append("Could not create the report ").append(ex.getMessage()).append(" ").append(ex.getLocalizedMessage()).toString();
            System.out.println(connectMsg);
        }
    }

    private void sendMessage(HttpServletResponse response, String msg,
                             String bType) {
        try {
            String url =
                (new StringBuilder()).append("org/Library/jsps/Messenger.jsp?message=").append(msg).append("&button=").append(bType).toString();
            response.sendRedirect(url);
        } catch (IOException e) {
            System.out.println(e);
        }
    }


    private static final String CONTENT_TYPE = "text/xml; charset=UTF-8";

}
