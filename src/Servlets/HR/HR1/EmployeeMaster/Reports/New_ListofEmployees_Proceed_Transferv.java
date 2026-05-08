package Servlets.HR.HR1.EmployeeMaster.Reports;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.text.ParseException;
import java.text.SimpleDateFormat;

import java.util.HashMap;
import java.util.Map;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JRRuntimeException;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRHtmlExporter;
import net.sf.jasperreports.engine.export.JRHtmlExporterParameter;
import net.sf.jasperreports.engine.export.JRTextExporter;
import net.sf.jasperreports.engine.export.JRTextExporterParameter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXlsExporterParameter;
import net.sf.jasperreports.engine.util.JRLoader;

public class New_ListofEmployees_Proceed_Transferv extends HttpServlet {

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
        ResultSet rset = null;
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println((new StringBuilder()).append("Exception in connection...").append(e).toString());
        }
        int order = 0;
        int cnt = 0;
        String command = request.getParameter("Command");
        //  String proNo = request.getParameter("prono");
        //System.out.println("proceeding number is...."+proNo);
        int offid = Integer.parseInt(request.getParameter("officeid"));
        order = Integer.parseInt(request.getParameter("order"));
        System.out.println(offid);
        // System.out.println((new StringBuilder()).append("proceeding no is").append(proNo).toString());
        System.out.println((new StringBuilder()).append("Office id is").append(offid).toString());
        String xml = "<response>";
        System.out.println("comd" + command);
        if (command.equalsIgnoreCase("loadEmployee")) {
            System.out.println("load empl");
            xml =
 (new StringBuilder()).append(xml).append("<command>loadEmployee</command>").toString();
            try {
                System.out.println(offid);
                System.out.println(order);
                System.out.println("before query");
                ps =
  con.prepareStatement("select a.transfer_issue_office_id,a.transfer_order_id,a.employee_id,a.to_which_office_id,decode(a.reposts_required,'Y','Yes','N','No') as reposts_required , \n" +
                       "                                               a.new_designation,o.reason_desc,decode(a.ta_da_eligible,'Y','Yes','N','No')as ta_da_eligible,b.proceeding_date,b.proceeding_no,\n" +
                       "                                               b.proceed_subject,b.proceed_reference,b.addl_para_1,b.addl_para_2,b.copy_to,b.presiding_officer,\n" +
                       "                                               b.presiding_officer_designation,b.signed,b.suffix,b.prefix,c.employee_name,d.trf_office_name,e.new_office_name,g.current_designation, \n" +
                       "                                               h.new_designation as newdesig  from \n" +
                       "                                              ( \n" +
                       "                                               select transfer_issue_office_id,transfer_order_id,employee_id,to_which_office_id,reposts_required,new_designation, \n" +
                       "                                               process_flow_status_id,reason_id,ta_da_eligible" +
                       "                                               from HRM_TRANSFER_DETAILS \n" +
                       "                                              where process_flow_status_id in ('CR','MD')\n" +
                       "                                               ) a\n" +
                       "                                               left outer join \n" +
                       "                                               ( \n" +
                       "                                               select transfer_issue_office_id as trf_off_id,transfer_order_id as trf_ord_id,proceeding_date,proceeding_no,proceed_subject,proceed_reference, \n" +
                       "                                               addl_para_1,addl_para_2,copy_to,prefix,presiding_officer,presiding_officer_designation,process_flow_status_id,signed_po as signed,suffix \n" +
                       "                                               from HRM_TRANSFER_ORDERS \n" +
                       "                                               where process_flow_status_id in ('CR','MD') \n" +
                       "                                               ) b \n" +
                       "                                              on a.transfer_issue_office_id=b.trf_off_id and a.transfer_order_id=b.trf_ord_id \n" +
                       "                                              left outer join \n" +
                       "                                               ( \n" +
                       "                                             select employee_id as emp_id, employee_prefix || '.' || employee_name || '.' || employee_initial as employee_name from \n" +
                       "                                             hrm_mst_employees \n" +
                       "                                             ) c \n" +
                       "                                              on a.employee_id=c.emp_id \n" +
                       "                                              left outer join \n" +
                       "                                              ( \n" +
                       "                                              select office_id,office_name as trf_office_name from com_mst_offices \n" +
                       "                                              where office_status_id not in ('CL','RD','NC')\n" +
                       "                                              ) d \n" +
                       "                                              on a.transfer_issue_office_id=d.office_id\n" +
                       "                                              left outer join  \n" +
                       "                                              ( \n" +
                       "                                             select office_id,office_name as new_office_name from com_mst_offices \n" +
                       "                                              where office_status_id not in ('CL','RD','NC') \n" +
                       "                                              ) e \n" +
                       "                                              on a.to_which_office_id=e.office_id \n" +
                       "                                              left outer join\n" +
                       "                                              ( \n" +
                       "                                              select employee_id as emp_id2, office_id as off_id2, designation_id as desig_id2 from hrm_emp_current_posting \n" +
                       "                                               where employee_status_id in 'WKG' and process_flow_status_id in 'FR' \n" +
                       "                                              ) f \n" +
                       "                                              on a.employee_id=f.emp_id2 \n" +
                       "                                              left outer join \n" +
                       "                                              ( \n" +
                       "                                             select designation_id as desig_id3, designation as current_designation from hrm_mst_designations \n" +
                       "                                              ) g \n" +
                       "                                              on f.desig_id2=g.desig_id3\n" +
                       "                                              left outer join \n" +
                       "                                              ( \n" +
                       "                                              select designation_id as desig_id4, designation as new_designation from hrm_mst_designations \n" +
                       "                                              ) h\n" +
                       "                                             on a.new_designation=h.desig_id4\n" +
                       "                                              left outer join\n" +
                       "                                             (\n" +
                       "                                             select posting_reason_id,posting_reason_desc as reason_desc from hrm_mst_posting_reason\n" +
                       "                                             )o\n" +
                       "                                             on a.reason_id=o.posting_reason_id\n" +
                       "                                             \n" +
                       "                                              where a.transfer_issue_office_id=? and a.transfer_order_id=?  ");

                System.out.println("offid..." + offid);
                System.out.println("order..." + order);
                ps.setInt(1, offid);
                ps.setInt(2, order);

                System.out.println("before executing query");
                resultset = ps.executeQuery();
                System.out.println("After executing query");
                while (resultset.next()) {
                    xml =
 (new StringBuilder()).append(xml).append("<pro_no>").append(resultset.getString("proceeding_no")).append("</pro_no><ref>").append(resultset.getString("proceed_reference")).append("</ref><sub>").append(resultset.getString("proceed_subject")).append("</sub><copyto>").append(resultset.getString("copy_to")).append("</copyto>").toString();
                    //System.out.println("The first xml is"+xml);
                    xml =
 (new StringBuilder()).append(xml).append("<pr_off>").append(resultset.getString("presiding_officer")).append("</pr_off><pr_desig>").append(resultset.getString("presiding_officer_designation")).append("</pr_desig><addlparaone>").append(resultset.getString("addl_para_1")).append("</addlparaone><addlparatwo>").append(resultset.getString("addl_para_2")).append("</addlparatwo>").toString();
                    //System.out.println("The 2 xml is"+xml);
                    xml =
 (new StringBuilder()).append(xml).append("<pr_date>").append(resultset.getDate("proceeding_date")).append("</pr_date>").toString();
                    //System.out.println("The 3 xml is"+xml);
                    xml =
 (new StringBuilder()).append(xml).append("<details>").toString();
                    //System.out.println("The 4 xml is"+xml);
                    xml =
 (new StringBuilder()).append(xml).append("<empid>").append(resultset.getString("employee_id")).append("</empid>").toString();
                    //System.out.println("The 5 xml is"+xml);
                    xml =
 (new StringBuilder()).append(xml).append("<empname>").append(resultset.getString("employee_name")).append("</empname>").toString();
                    //System.out.println("The 6 xml is"+xml);
                    xml =
 (new StringBuilder()).append(xml).append("<empdesig>").append(resultset.getString("newdesig")).append("</empdesig>").toString();
                    //System.out.println("The 7 xml is"+xml);
                    xml =
 (new StringBuilder()).append(xml).append("<emptrnfromoffice>").append(resultset.getString("trf_office_name")).append("</emptrnfromoffice>").toString();
                    //System.out.println("The 8 xml is"+xml);
                    xml =
 (new StringBuilder()).append(xml).append("<emptrntooffice>").append(resultset.getString("new_office_name")).append("</emptrntooffice>").toString();
                    //System.out.println("The 8 xml is"+xml);
                    xml =
 (new StringBuilder()).append(xml).append("<processflow>").append(resultset.getString("reposts_required")).append("</processflow>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<reason>").append(resultset.getString("reason_desc")).append("</reason>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<ta_da>").append(resultset.getString("ta_da_eligible")).append("</ta_da>").toString();

                    //System.out.println("The 9 xml is"+xml);
                    xml =
 (new StringBuilder()).append(xml).append("</details>").toString();
                    // System.out.println("The 10 xml is"+xml);
                    System.out.println("reason desc" +
                                       resultset.getString("reason_desc"));
                    cnt++;
                    //xml=xml+"<pro_no>"+
                    System.out.println("count" + cnt);
                    xml =
 (new StringBuilder()).append(xml).append("<sub>").append(resultset.getString("proceed_subject")).append("</sub>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<ref>").append(resultset.getString("proceed_reference")).append("</ref>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<pr_off>").append(resultset.getString("presiding_officer")).append("</pr_off>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<pr_desig>").append(resultset.getString("presiding_officer_designation")).append("</pr_desig>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<addlparaone>").append(resultset.getString("addl_para_1")).append("</addlparaone>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<addlparatwo>").append(resultset.getString("addl_para_2")).append("</addlparatwo>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<copy>").append(resultset.getString("copy_to")).append("</copy>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<pr_date>").append(resultset.getString("proceeding_date")).append("</pr_date>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<signed>").append(resultset.getString("signed")).append("</signed>").toString();
                    xml =
 (new StringBuilder()).append(xml).append("<prefix>").append(resultset.getString("prefix")).append("</prefix>").toString();
                    System.out.println("hello signed................." +
                                       resultset.getString("signed"));
                    xml =
 (new StringBuilder()).append(xml).append("<suffix>").append(resultset.getString("suffix")).append("</suffix>").toString();
                    //xml = (new StringBuilder()).append(xml).append("</details>").toString();
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
            System.out.println("xml is..." + xml);
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
        System.out.println("Report Generation new");
        Connection con = null;
        String Proc_Status = "";
        PreparedStatement psnew = null, ps = null;
        ResultSet rsnew = null;
        PreparedStatement pst = null;
        ResultSet rst = null;
        PreparedStatement pst1 = null;
        ResultSet rst1 = null, rset = null;
        int eid = 0;
        int txtRel_SLNO = 0;
        int txtOffId = 0;
        String txtEmployeeid = "";
        String predateparam = null;
        String sub = null;
        String ref = null;
        String addlParaOne = null;
        String addlParaTwo = null;
        // String preno = null;
        int order = 0;
        String preoff = null;
        String predesig = null;
        String copyto = null, addr = "";
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
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println((new StringBuilder()).append("Exception in openeing connection :").append(e).toString());
        }
        String prono = request.getParameter("cmbProceed");
        int offid = Integer.parseInt(request.getParameter("txtOffId"));
        String signed = request.getParameter("txtseloff");
        // String empids[] =empidsnew.split(",");
        // System.out.println("Empid lenght:"+empids.length);
        /*try
            {
                psnew = con.prepareStatement("select employee_id,PROCESS_FLOW_STATUS_ID from HRM_Transfer_order where proceeding_no=? and Transfer_issue_office_id=?");
              //  System.out.println((new StringBuilder()).append("select employee_id,PROCESS_FLOW_STATUS_ID from HRM_EMP_RELIEVAL_DETAILS where proceeding_no=").append(prono).append("and office_id=").append(offid).toString());
                psnew.setString(1, prono);
                psnew.setInt(2, offid);
                for(rsnew = psnew.executeQuery(); rsnew.next(); System.out.println(txtEmployeeid))
                {
                    Proc_Status = rsnew.getString("PROCESS_FLOW_STATUS_ID");
                    txtEmployeeid = (new StringBuilder()).append(txtEmployeeid).append(rsnew.getInt(0)).append(",").toString();
                    System.out.println(rsnew.getInt(0));
                }

                System.out.println((new StringBuilder()).append("empid is -----------").append(txtEmployeeid).toString());
                empids = txtEmployeeid.substring(0, txtEmployeeid.length() - 1);
                System.out.println((new StringBuilder()).append("empids are   :::::::").append(empids).toString());
            }
            catch(Exception e)
            {
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
            try
            {
                d1 = dateFormat1.parse(predateparam.trim());
            }
            catch(ParseException e)
            {
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
            try
            {
                pst1 = con.prepareStatement("select * from hrm_transfer_order where proceeding_no=? and office_id=?");
                pst1.setString(1, preno);
                pst1.setInt(2, offid);
                rst1 = pst1.executeQuery();
                if(rst1.next())
                    flag = true;
                else
                    flag = false;
            }
            catch(Exception e)
            {
                System.out.println((new StringBuilder()).append("throwing an err").append(e).toString());
            }
            System.out.println((new StringBuilder()).append("flag value is         ::").append(flag).toString());
         /*  if(flag)
                try
                {
                    pst = con.prepareStatement("update hrm_emp_relieval_proceed_new set proceeding_date=?, presiding_officer=?,presiding_officer_desig=?,subject=?,sub_reference=?,copyto=?,addl_para_one=?, addl_para_2=? where proceeding_no=? and office_id=?");
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
                }
                catch(Exception e)
                {
                    System.out.println((new StringBuilder()).append("Error while updating ").append(e).toString());
                }
            else
                try
                {
                    pst = con.prepareStatement("insert into hrm_emp_relieval_proceed_new(proceeding_date, presiding_officer,presiding_officer_desig,subject,sub_reference,copyto,addl_para_one, addl_para_2,proceeding_no,office_id) values(?,?,?,?,?,?,?,?,?,?)");
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
                }
                catch(Exception e)
                {
                    System.out.println((new StringBuilder()).append("Error while inserting ").append(e).toString());
                }v*/
        int cnt = 0;
        boolean flag = false;
        String forward, suffix;
        order = Integer.parseInt(request.getParameter("cmbProceed"));
        /*predateparam = request.getParameter("txtPDat");
           java.sql.Date dateto = null;
           System.out.println("before converting date");
           String dateString1 = predateparam;
           SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
           java.util.Date d1 = null;
           try
           {
               d1 = dateFormat1.parse(predateparam.trim());
           }
           catch(ParseException e)
           {
               d1 = null;
               System.out.println("error in parsing date");
           }
           dateFormat1.applyPattern("yyyy-MM-dd");
           dateString1 = dateFormat1.format(d1);
           dateto = java.sql.Date.valueOf(dateString1);
           System.out.println((new StringBuilder()).append("Date value is :").append(dateto).toString());*/
        preoff = request.getParameter("txtPO");
        String txtproc = request.getParameter("txtref");
        predesig = request.getParameter("txtPODesig");
        copyto = request.getParameter("txtCopy");
        String office = request.getParameter("txtOffName");
        String address = request.getParameter("address");
        sub = request.getParameter("txtSubject");
        ref = request.getParameter("txtRef");
        addlParaOne = request.getParameter("txtPara1");
        addlParaTwo = request.getParameter("txtPara2");
        offid = Integer.parseInt(request.getParameter("txtOffId"));
        String proceed = "Proceedings of " + predesig + "," + office;
        predateparam = request.getParameter("txtPDat");
        if (predateparam == null)
            predateparam = "";
        String no_date =
            "Proceeding No: " + txtproc + "   Date: " + predateparam;
        suffix = request.getParameter("suffix");
        String addrs = "", copyto1 = "";
        if (signed.equals("N"))
            forward = "/forwarded by order/";
        else
            forward = "";
        File reportFile = null;
        String officer = "", TO = "", copyTo = null, addl1 = "", addl2 =
            "", addl = "", prefix = "";
        try {
            //for(int j=0;j<empids.length;j++)
            //{
            //      int id=Integer.parseInt(empids[j]);
            ps =
  con.prepareStatement("select addr,old_office_name,old_designation_name from   \n" +
                       "(select transfer_issue_office_id as offid,Transfer_order_id as trans_id,individually_addressed as inaddress from hrm_transfer_orders where process_flow_Status_id in ('CR','MD') and individually_addressed='Y'   \n" +
                       ")a   \n" + "left outer join   \n" +
                       "(select transfer_issue_office_id,Transfer_order_id,employee_id as empid,to_which_office_id,new_designation from hrm_transfer_details where process_flow_Status_id in ('CR','MD')  )aa    \n" +
                       "on   aa.transfer_order_id=a.trans_id and aa.transfer_issue_office_id=a.offid  \n" +
                       "\n" + "left outer join    \n" + "(    \n" +
                       "select employee_id as emp_id2, office_id as off_id2, designation_id as desig_id2 from hrm_emp_current_posting   \n" +
                       "where employee_status_id in 'WKG' and process_flow_status_id in ('FR')  \n" +
                       ") f   \n" + "on aa.empid=f.emp_id2  \n" +
                       "left outer join   \n" + "(   \n" +
                       "select designation_id as desig_id4, designation as old_designation_name from hrm_mst_designations   \n" +
                       ") h   \n" + "on aa.new_designation=h.desig_id4   \n" +
                       "left outer join (    \n" +
                       "select employee_id as empid1,(employee_prefix||'.'||employee_name||'.'||employee_initial) as addr from hrm_mst_employees )c on aa.empid=c.empid1   \n" +
                       "left outer join    (   \n" +
                       "select office_id,office_name as old_office_name from com_mst_offices   \n" +
                       "where office_status_id not in ('CL','RD','NC')   \n" +
                       ") e   \n" + "on f.off_id2=e.office_id   \n" +
                       "where a.inaddress='Y' and a.offid=? and a.trans_id=?");
            //ps.setInt(1,id);
            ps.setInt(2, order);
            ps.setInt(1, offid);
            rset = ps.executeQuery();
            String offname, desig;
            TO = "To";


            while (rset.next()) {

                //        copyto=rset.getString("copyto");
                addr = rset.getString("addr");
                System.out.println("inaddress" + addr);
                //offname=rset.getString("old_office_name");
                //desig=rset.getString("old_designation_name");
                if (rset.getString("old_designation_name") != null) {
                    desig = rset.getString("old_designation_name");
                } else {
                    desig = " ";
                }
                if (rset.getString("old_office_name") != null) {
                    offname = rset.getString("old_office_name");
                } else {
                    offname = " ";
                }
                addr = addr + "," + desig + "," + offname;
                System.out.println(addr + "name");

                addrs = addrs + "\n" + addr;
                System.out.println("wat is " + addrs);
            }
            System.out.println("addrs-------------" + addrs);
            if (addrs == null || addrs == "")
                addrs = "\nThe Individuals";
            //}
            ps =
  con.prepareStatement("select addl_para_1,addl_para_2,copy_to,presiding_officer,suffix,prefix from  hrm_transfer_orders where transfer_issue_office_id=? and transfer_order_id=? and process_flow_Status_id in('CR','MD')");
            ps.setInt(1, offid);
            ps.setInt(2, order);
            rset = ps.executeQuery();

            if (rset.next()) {
                copyto = rset.getString("copy_to");
                addl1 = rset.getString("addl_para_1");
                addl2 = rset.getString("addl_para_2");
                prefix = rset.getString("prefix");
                if (prefix == null)
                    prefix = "";

                if (rset.getString("addl_para_1") != (null)) {
                    addl1 = rset.getString("addl_para_1");
                } else {
                    addl1 = "";
                }
                if (rset.getString("addl_para_2") != (null)) {
                    addl2 = rset.getString("addl_para_2");
                } else {
                    addl2 = "";
                }

                addl = addl1 + "\n\n" + addl2 + "\n";
                System.out.println("the address is..............." + addl);
                if (copyto != null)
                    //addrs=addrs.trim()+"\n"+copyto;
                    copyTo = "Copy To";
                officer = rset.getString("presiding_officer");
                if (suffix != null)
                    officer = prefix + " " + officer + " " + suffix;
                System.out.println("officer-------------" + officer);
            } //
            System.out.println("copy to" + copyto);
            String Pwhole = "";
            Pwhole = addl;
            //copyto1=TO+addrs+"\n\n"+copyTo+"\n\n"+copyto;
            copyto1 = TO + addrs + "\n\n" + copyTo + "\n" + copyto;
            System.out.println(copyto1);
            /* System.out.println("test");
                ps=con.prepareStatement("select * from(select to_char(employee_id) as copy,transfer_order_id,Transfer_issue_office_id from hrm_transfer_details where individually_addressed='Y' and process_flow_status_id in('CR','MD') and employee_id in(?) union select copy_to as copy,transfer_order_id,Transfer_issue_office_id from hrm_transfer_orders where process_flow_status_id in('CR','MD')) where transfer_issue_office_id=5000 and transfer_order_id=2  ");
                System.out.println("test1");
                System.out.println(empidsnew);
                 ps.setString(1,empidsnew);
                //ps.setInt(2,order);
                System.out.println("test2");
                rset=ps.executeQuery();
                System.out.println("test3");
                while(rset.next())
                {
                    addr=addr+rset.getString("copy");

                }*/
            System.out.println("vero" + addr);
            System.out.println("calling servlet");
            reportFile =
                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Transfer_Proceedingv2.jasper"));
            if (!reportFile.exists())
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            Map map = null;
            map = new HashMap();
            map.put("office", offid);
            map.put("copyto", copyto1);
            map.put("suffix", officer);
            map.put("order", order);
            map.put("pwhole", Pwhole);
            map.put("no_date", no_date);
            map.put("proceed", proceed);
            map.put("address", address);
            net.sf.jasperreports.engine.JasperPrint jasperPrint =
                JasperFillManager.fillReport(jasperReport, map, con);
            String rtype = request.getParameter("RType");
            rtype = "PDF";
            if (rtype.equalsIgnoreCase("HTML")) {
                response.setContentType("text/html");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"Transfer_Draft_Proceeding.html\"");
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
                                   "attachment;filename=\"Transfer_Draft_Proceeding.pdf\"");
                OutputStream outs = response.getOutputStream();
                outs.write(buf, 0, buf.length);
                outs.close();
            } else if (rtype.equalsIgnoreCase("EXCEL")) {
                response.setContentType("application/vnd.ms-excel");
                response.setHeader("Content-Disposition",
                                   "attachment;filename=\"Transfer_Draft_Proceeding.xls\"");
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
                                   "attachment;filename=\"Transfer_Draft_Proceeding.txt\"");
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
