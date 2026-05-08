package Servlets.HR.HR1.EmployeeMaster.Reports;

import Servlets.Security.classes.UserProfile;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.io.PrintWriter;

import javax.servlet.*;
import javax.servlet.http.*;

import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.GregorianCalendar;
import java.util.ResourceBundle;
import java.util.Calendar;
import java.util.HashMap;

import java.util.Map;

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


public class Seniority_listServ_Rep extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        response.setContentType(CONTENT_TYPE);
        PrintWriter out = response.getWriter();

        System.out.println("inside servlet");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null, rs1 = null;
        PreparedStatement ps8 = null, ps7 = null;
        ResultSet rs8 = null;

        try {

            ResourceBundle rb =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rb.getString("Config.DSN");
            String strhostname = rb.getString("Config.HOST_NAME");
            String strportno = rb.getString("Config.PORT_NUMBER");
            String strsid = rb.getString("Config.SID");
            String strdbusername = rb.getString("Config.USER_NAME");
            String strdbpassword = rb.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection...." + e);
        }

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

        String updatedby = (String)session.getAttribute("UserId");
        System.out.println("user id..." + updatedby);

        long l = System.currentTimeMillis();
        java.sql.Timestamp ts = new java.sql.Timestamp(l);

        String command = request.getParameter("Command");
        System.out.println("command.." + command);


        String xml = "";


        if (command.equalsIgnoreCase("loademp")) {
            int eid = Integer.parseInt(request.getParameter("txtEmployeeid"));
            System.out.println("employee id.." + eid);

            int cadr = Integer.parseInt(request.getParameter("cad"));
            System.out.println("cadre id..." + cadr);

            int sgroup = Integer.parseInt(request.getParameter("sgroup"));
            System.out.println("sgroup id..." + sgroup);

            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);

            xml = "<response><command>loademp</command>";
            try {

                UserProfile up = null;
                up = (UserProfile)session.getAttribute("UserProfile");
                boolean flag = true;
                ps =
  con.prepareStatement("SELECT EMPLOYEE_ID FROM HRM_MST_EMPLOYEES WHERE EMPLOYEE_ID=?");
                ps.setInt(1, eid);
                rs = ps.executeQuery();
                if (!rs.next()) {
                    xml = xml + "<flag>failure</flag>";
                    flag = false;
                } else {
                    ps7 =
 con.prepareStatement("select cadre_id,employee_id from hrm_emp_seniority where cadre_id=? and employee_id=?");
                    ps7.setInt(1, cadr);
                    ps7.setInt(2, eid);
                    rs1 = ps7.executeQuery();
                    if (rs1.next()) {
                        flag = false;
                        xml = xml + "<flag>available</flag>";
                    } else {
                        ps8 =
 con.prepareStatement("select employee_id,designation_id from hrm_emp_current_posting " +
                      " where designation_id in (select designation_id from hrm_mst_designations " +
                      " where service_group_id=?) and employee_id=?");

                        ps8.setInt(1, sgroup);
                        ps8.setInt(2, eid);

                        rs8 = ps8.executeQuery();

                        if (!rs8.next()) {
                            xml = xml + "<flag>failure0</flag>";
                            flag = false;
                        } else {
                            flag = true;
                        }
                    }
                }
                if (flag) {
                    ps =
  con.prepareStatement("select EMPLOYEE_STATUS_ID from HRM_EMP_CURRENT_POSTING where  EMPLOYEE_ID=? ");
                    ps.setInt(1, eid);
                    rs = ps.executeQuery();
                    if (!rs.next()) {
                        xml = xml + "<flag>failure1</flag>";
                    } else {
                        System.out.println("inside employee status id");

                        if (rs.getString("EMPLOYEE_STATUS_ID") != null) {

                            ps =
  con.prepareStatement("select EMPLOYEE_ID from HRM_EMP_RELIEVAL_DETAILS where  EMPLOYEE_ID=? and (PROCESS_FLOW_STATUS_ID='CR' or PROCESS_FLOW_STATUS_ID='MD')");
                            ps.setInt(1, eid);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                xml = xml + "<flag>failure2</flag>";
                            } else {

                                ps =
  con.prepareStatement("select e.EMPLOYEE_ID,e.date_of_birth,e.EMPLOYEE_NAME ||decode(e.EMPLOYEE_INITIAL,null,' ','.'||e.EMPLOYEE_INITIAL) as  EMPLOYEE_NAME ,d.DESIGNATION,f.office_name,c.designation_id,c.employee_status_id, g.employee_status_desc from HRM_MST_EMPLOYEES e,HRM_EMP_CURRENT_POSTING c,HRM_MST_DESIGNATIONS d,com_mst_offices f, hrm_mst_employee_status g where c.DESIGNATION_ID=d.DESIGNATION_ID and e.EMPLOYEE_ID=c.EMPLOYEE_ID AND c.office_id=f.office_id and c.employee_status_id=g.employee_status_id and e.EMPLOYEE_ID=? ");
                                ps.setInt(1, eid);
                                //ps.setInt(2,offid);
                                rs = ps.executeQuery();
                                if (rs.next()) {


                                    xml =
 xml + "<flag>success</flag><eid>" + eid + "</eid><ename>" +
   rs.getString("EMPLOYEE_NAME") + "</ename><desig>" +
   rs.getString("DESIGNATION") + "</desig><curr_post>" +
   rs.getString("office_name") + "</curr_post><desig_id>" +
   rs.getInt("designation_id") + "</desig_id>";
                                    xml =
 xml + "<dob>" + (rs.getDate("date_of_birth")).getYear() + "</dob><status>" +
   rs.getString("employee_status_desc") + "</status>";


                                    /***************  16-08-2007    ***********************/


                                    ps =
  con.prepareStatement("select DATE_EFFECTIVE_FROM,DATE_EFFECTIVE_FROM_SESSION  from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?");

                                    ps.setInt(1, eid);

                                    rs = ps.executeQuery();
                                    String maxfromdate = "";
                                    String maxsession = "";
                                    if (rs.next()) {
                                        if (rs.getDate("DATE_EFFECTIVE_FROM") !=
                                            null) {
                                            maxfromdate =
                                                    new SimpleDateFormat("dd/MM/yyyy").format(rs.getDate("DATE_EFFECTIVE_FROM"));
                                            System.out.println("max from date :" +
                                                               rs.getDate("DATE_EFFECTIVE_FROM"));
                                        } else {

                                            maxfromdate = "empty";
                                        }
                                        if (rs.getString("DATE_EFFECTIVE_FROM_SESSION") !=
                                            null) {
                                            maxsession =
                                                    rs.getString("DATE_EFFECTIVE_FROM_SESSION");
                                        } else {
                                            maxsession = "FN";
                                        }
                                    }
                                    xml =
 xml + "<maxfromdate>" + maxfromdate + "</maxfromdate><maxsession>" +
   maxsession + "</maxsession>";


                                    /********************************************************/

                                } else
                                    xml = xml + "<flag>failure</flag>";

                            }
                        }


                    }
                } else {
                    xml = xml + "<flag>failure3</flag>";
                }
            } catch (Exception e) {
                System.out.println("catch..HERE.in load emp." + e);
                xml = xml + "<flag>failure</flag>";
            }


            xml = xml + "</response>";
            System.out.println("xml is..." + xml);
            out.println(xml);

        }

        else if (command.equalsIgnoreCase("office")) {
            System.out.println("inside office...");
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>office</command>";
            System.out.println(xml);
            try {
                int oid = 0;
                String oname = "";
                try {
                    oid = Integer.parseInt(request.getParameter("oid"));
                } catch (Exception e) {
                    System.out.println(e);
                }

                System.out.println("office id..." + oid);

                ps =
  con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                ps.setInt(1, oid);
                rs = ps.executeQuery();
                if (rs.next())
                    xml =
 xml + "<flag>success</flag><oid>" + oid + "</oid><oname>" +
   rs.getString("OFFICE_NAME") + "</oname>";
                else
                    xml = xml + "<flag>failure</flag><oid>" + oid + "</oid>";

                System.out.println(xml);
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load office." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
        }

        else if (command.equalsIgnoreCase("getDet")) {

            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>getDet</command>";

            int cad_id = Integer.parseInt(request.getParameter("cad_id"));
            System.out.println("cad_id..." + cad_id);


            try {
                String sql =
                    "select a.cadre_id,a.employee_id,a.seniority_no,a.sanction_order_no,a.sanction_order_date,a.amended_new,a.remarks, " +
                    " b.employee_name || '.' || b.employee_initial as employee_name " +
                    " from HRM_EMP_SENIORITY a " +
                    " left outer join hrm_mst_employees b on a.employee_id=b.employee_id " +
                    " where CADRE_ID=? order by a.seniority_no";
                ps = con.prepareStatement(sql);
                ps.setInt(1, cad_id);


                rs = ps.executeQuery();
                int i = 0;

                while (rs.next()) {
                    //xml=xml+"<flag>success</flag>";
                    /*
               xml=xml+"<details><employee_id>"+rs.getInt("employee_id")+"</employee_id><employee_name>"+rs.getString("employee_name")+"</employee_name><cad_id>"+rs.getInt("cadre_id")+"</cad_id>";
               xml=xml+"<sen_no>"+rs.getInt("seniority_no")+"</sen_no><san_ord_no>"+rs.getString("sanction_order_no")+"</san_ord_no><san_ord_date>"+rs.getString("sanction_order_date")+"</san_ord_date>";
               xml=xml+"<amend>"+rs.getString("amended_new")+"</amend><remarks>"+rs.getString("remarks")+"</remarks></details>";
               */
                    String od = "";
                    if (rs.getString("sanction_order_date") != null) {
                        String[] sod =
                            rs.getDate("sanction_order_date").toString().split("-");
                        od = sod[2] + "/" + sod[1] + "/" + sod[0];
                    } else {
                        od = "";
                    }


                    xml =
 xml + "<details><employee_id>" + rs.getInt("employee_id") +
   "</employee_id><employee_name>" + rs.getString("employee_name") +
   "</employee_name><cad_id>" + rs.getInt("cadre_id") + "</cad_id>";
                    xml =
 xml + "<sen_no>" + rs.getInt("seniority_no") + "</sen_no><san_ord_no>" +
   rs.getString("sanction_order_no") + "</san_ord_no><san_ord_date>" + od +
   "</san_ord_date>";
                    xml =
 xml + "<amend>" + rs.getString("amended_new") + "</amend><remarks>" +
   rs.getString("remarks") + "</remarks></details>";

                    i++;
                }

                if (i == 0) {
                    xml = xml + "<flag>failurea</flag>";
                } else {
                    xml = xml + "<flag>success</flag>";
                }
            } catch (Exception e) {
                System.out.println(e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);

        }

        else if (command.equalsIgnoreCase("Update")) {
            System.out.println("inside update...");
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>update</command>";
            System.out.println(xml);
            try {

                Date san_date = null;
                Calendar c;

                int cadre_id =
                    Integer.parseInt(request.getParameter("txtcad_id"));
                System.out.println("cadre id..." + cadre_id);

                int emp_id =
                    Integer.parseInt(request.getParameter("txtEmpid"));
                System.out.println("employee id..." + emp_id);

                int senior_no =
                    Integer.parseInt(request.getParameter("txtseni_no"));
                System.out.println("seniority no..." + senior_no);

                String s_order_type = request.getParameter("txtSen_ord_typ");
                System.out.println("order type..." + s_order_type);

                String san_ord_no = request.getParameter("txtSan_ordno");
                System.out.println("sanction order no..." + san_ord_no);

                String san_ord_dat = request.getParameter("txtSan_orddat");
                System.out.println("proc date...." + san_ord_dat);

                if (san_ord_dat.equals(null) || san_ord_dat.equals("")) {
                    san_date = null;
                } else {
                    String[] sd =
                        request.getParameter("txtSan_orddat").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    san_date = new Date(d.getTime());
                }

                System.out.println("sanction date..." + san_date);

                String rem = request.getParameter("txtRem");
                System.out.println("rem..." + rem);


                String sql =
                    "update HRM_EMP_SENIORITY set SENIORITY_NO=?,SANCTION_ORDER_NO=?,SANCTION_ORDER_DATE=?,AMENDED_NEW=?,REMARKS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where CADRE_ID=? and EMPLOYEE_ID=?";

                ps = con.prepareStatement(sql);
                ps.setInt(1, senior_no);
                System.out.println("senior_no" + senior_no);
                ps.setString(2, san_ord_no);
                System.out.println("san_ord_no" + san_ord_no);
                ps.setDate(3, san_date);
                System.out.println("san_date" + san_date);
                ps.setString(4, s_order_type);
                System.out.println("s_order_type" + s_order_type);
                ps.setString(5, rem);
                System.out.println("rem" + rem);
                ps.setString(6, updatedby);
                System.out.println("updatedby" + updatedby);
                ps.setTimestamp(7, ts);
                System.out.println("ts" + ts);
                ps.setInt(8, cadre_id);
                System.out.println("cadre_id" + cadre_id);
                ps.setInt(9, emp_id);
                System.out.println("emp_id" + emp_id);


                int z = ps.executeUpdate();

                if (z > 0) {
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }

                System.out.println(xml);
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load office." +
                                   e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
        }

        else if (command.equalsIgnoreCase("Delete")) {
            System.out.println("inside delete...");
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>delete</command>";
            System.out.println(xml);
            try {
                int empid = 0, cad_id = 0;


                empid =
Integer.parseInt(request.getParameter("txtEmployeeid"));
                cad_id = Integer.parseInt(request.getParameter("Cadreid"));


                System.out.println("employee id.." + empid);
                System.out.println("cad_id..." + cad_id);


                String sql =
                    "delete from HRM_EMP_SENIORITY where CADRE_ID=? and EMPLOYEE_ID=?";

                ps = con.prepareStatement(sql);
                ps.setInt(1, cad_id);
                ps.setInt(2, empid);

                int z = ps.executeUpdate();

                if (z > 0) {
                    xml = xml + "<flag>success</flag>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }

                System.out.println(xml);
                ps.close();
                rs.close();
            } catch (Exception e) {
                System.out.println("catch..HERE.in load office." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            out.println(xml);
        }

        else if (command.equalsIgnoreCase("Desig")) {

            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            xml = "<response><command>Desig</command>";

            int desig_id =
                Integer.parseInt(request.getParameter("txtDesigid"));
            System.out.println("desig_id..." + desig_id);


            try {

                ps =
  con.prepareStatement("select DESIGNATION from HRM_MST_DESIGNATIONS where DESIGNATION_ID=?");
                ps.setInt(1, desig_id);
                rs = ps.executeQuery();


                if (rs.next()) {
                    xml =
 xml + "<flag>success</flag><designame>" + rs.getString("DESIGNATION") +
   "</designame>";
                } else {
                    xml = xml + "<flag>failure</flag>";
                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);

        }

        else if (command.equalsIgnoreCase("SerGroup")) {
            System.out.println("inside service group");
            xml = "<response><command>SerGroup</command>";
            System.out.println(xml);
            try {
                String strdes = request.getParameter("cmbdes");
                int des = Integer.parseInt(strdes);

                System.out.println("id.." + des);

                ps =
  con.prepareStatement("select SERVICE_GROUP_ID from HRM_MST_DESIGNATIONS  where DESIGNATION_ID=?");
                ps.setInt(1, des);
                rs = ps.executeQuery();
                if (rs.next()) {
                    xml =
 xml + "<flag>success</flag><sid>" + rs.getInt(1) + "</sid>";
                    System.out.println(xml);
                } else {
                    xml = xml + "<flag>failure</flag>";
                }

            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);


        }

        else if (command.equalsIgnoreCase("SGroup")) {
            System.out.println("inside sgroup");
            xml = "<response>";
            try {
                int sgroup =
                    Integer.parseInt(request.getParameter("cmbsgroup"));

                System.out.println("sgroup::" + sgroup);
                ps =
  con.prepareStatement("select distinct cadre_id,cadre_name from " + " ( " +
                       " select cadre_id,service_group_id from hrm_mst_designations where service_group_id=? " +
                       " ) a " + " left outer join " + " ( " +
                       " select cadre_id as cad_id,cadre_name from hrm_mst_cadre " +
                       " ) b " + " on a.cadre_id=b.cad_id " +
                       " order by cadre_name");
                ps.setInt(1, sgroup);
                rs = ps.executeQuery();
                int count = 0;
                xml = xml + "<flag>success</flag>";
                while (rs.next()) {

                    xml =
 xml + "<option><id>" + rs.getInt("cadre_id") + "</id><name>" +
   rs.getString("cadre_name") + "</name></option>";
                    count++;
                }
                System.out.println("xml..." + xml);
                System.out.println("count::" + count);
                if (count == 0)
                    xml = "<response><flag>failure</flag>";
                else
                    xml = xml + "<flag>success</flag>";

            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = "<response><flag>failure</flag>";
            }

            xml = xml + "</response>";
            out.println(xml);

        }

        else if (command.equalsIgnoreCase("Add")) {

            xml = "<response><command>Add</command>";
            try {
                Date san_date = null;
                Calendar c;

                int cadre_id =
                    Integer.parseInt(request.getParameter("txtcad_id"));
                System.out.println("cadre id..." + cadre_id);

                int emp_id =
                    Integer.parseInt(request.getParameter("txtEmpid"));
                System.out.println("employee id..." + emp_id);

                int senior_no =
                    Integer.parseInt(request.getParameter("txtseni_no"));
                System.out.println("seniority no..." + senior_no);

                String order_type = request.getParameter("txtSen_ord_typ");
                System.out.println("order type..." + order_type);

                String san_ord_no = request.getParameter("txtSan_ordno");
                System.out.println("sanction order no..." + san_ord_no);

                String san_ord_dat = request.getParameter("txtSan_orddat");
                System.out.println("proc date...." + san_ord_dat);

                if (san_ord_dat.equals(null) || san_ord_dat.equals("")) {
                    san_date = null;
                } else {
                    String[] sd =
                        request.getParameter("txtSan_orddat").split("/");
                    c =
   new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1,
                         Integer.parseInt(sd[0]));
                    java.util.Date d = c.getTime();
                    san_date = new Date(d.getTime());
                }

                System.out.println("sanction date..." + san_date);

                String rem = request.getParameter("txtRem");
                System.out.println("rem..." + rem);

                PreparedStatement ps_a = null;
                ResultSet rs_a = null;

                ps_a =
con.prepareStatement("insert into HRM_EMP_SENIORITY(CADRE_ID,EMPLOYEE_ID,SENIORITY_NO,SANCTION_ORDER_NO,SANCTION_ORDER_DATE,AMENDED_NEW,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?)");

                ps_a.setInt(1, cadre_id);
                ps_a.setInt(2, emp_id);
                ps_a.setInt(3, senior_no);
                ps_a.setString(4, san_ord_no);
                ps_a.setDate(5, san_date);
                ps_a.setString(6, order_type);
                ps_a.setString(7, rem);
                ps_a.setString(8, updatedby);
                ps_a.setTimestamp(9, ts);

                int q = ps_a.executeUpdate();

                if (q > 0) {
                    xml = xml + "<flag>success</flag>";
                } else {

                }


            } catch (SQLException e) {

                System.out.println(e.getMessage());
                xml = xml + "<flag>failure</flag>";

            }

            xml = xml + "</response>";
            System.out.println("xml..." + xml);
            out.println(xml);

        }


        out.close();
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        response.setContentType(CONTENT_TYPE);
        System.out.println("inside post");

        Connection con = null;
        PreparedStatement ps = null;
        ResultSet rs = null;
        JasperPrint jasperPrint = null;
        try {

            ResourceBundle rb =
                ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString = "";

            String strDriver = rb.getString("Config.DATA_BASE_DRIVER");
            String strdsn = rb.getString("Config.DSN");
            String strhostname = rb.getString("Config.HOST_NAME");
            String strportno = rb.getString("Config.PORT_NUMBER");
            String strsid = rb.getString("Config.SID");
            String strdbusername = rb.getString("Config.USER_NAME");
            String strdbpassword = rb.getString("Config.PASSWORD");

            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

            Class.forName(strDriver.trim());
            con =
 DriverManager.getConnection(ConnectionString, strdbusername.trim(),
                             strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in connection...." + e);
        }

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


        String service_group = request.getParameter("service_group");
        System.out.println("service_group.." + service_group);
        String cad_id = request.getParameter("cad_id");
        System.out.println("cad_id.." + cad_id);
        System.out.println("service_group:" + service_group +
                           "          cad_id:" + cad_id);
        File reportFile = null;
        Map map = null;
        try {
            reportFile =
                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Seniority_List_Rep.jasper"));
            System.out.println("after path");
            if (!reportFile.exists()) {
                System.out.println("does not exsist");
                throw new JRRuntimeException("File J not found. The report design must be compiled first.");
            }
            System.out.println(JRLoader.loadObject(reportFile.getPath()));
            JasperReport jasperReport =
                (JasperReport)JRLoader.loadObject(reportFile.getPath());
            map = new HashMap();
            map.put("service_group", service_group);
            map.put("cad_id", cad_id);
            jasperPrint = JasperFillManager.fillReport(jasperReport, map, con);


            byte buf[] = JasperExportManager.exportReportToPdf(jasperPrint);
            response.setContentType("application/pdf");
            response.setContentLength(buf.length);
            // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
            //response.setContentType("application/force-download");

            response.setHeader("Content-Disposition",
                               "attachment;filename=\"Seniority List.pdf\"");
            OutputStream out = response.getOutputStream();
            out.write(buf, 0, buf.length);
            out.close();

        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }


    }

}
