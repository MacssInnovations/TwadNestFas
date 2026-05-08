
package Servlets.HR.HR1.EmployeeMaster.Reports;

import Servlets.Security.classes.UserProfile;

import java.io.ByteArrayOutputStream;
import java.io.File;
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

import javax.servlet.*;
import javax.servlet.http.*;

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

public class ListAllScopeofOffices extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doPost(HttpServletRequest request,
                       HttpServletResponse response) throws ServletException,
                                                            IOException {
        String desigval = "", newdesigval = "";

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
        response.setContentType(CONTENT_TYPE);
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

        // JasperDesign jasperDesign = null;

        java.util.Date d = new java.util.Date();
        String curFinYear = "";
        int month = d.getMonth();
        int sysYear = d.getYear() + 1900;
        if (month > 3) {
            System.out.println("current year is ::::::::::::::::" + sysYear);
            curFinYear = sysYear + "-" + (sysYear + 1);
            System.out.println("cur Financial year is:::::::::::::" +
                               curFinYear);
        } else {
            System.out.println("current year is ???????/" + sysYear);
            curFinYear = (sysYear - 1) + "-" + sysYear;
            System.out.println("cur Financial year is:::::::::::::" +
                               curFinYear);
        }
        File reportFile = null;

        String offlevel = "";
        String office = "";
        String officetype = "";
        String officeselected = "";
        String designationlevel = "";
        String designation = "";
        String outputtype = "";
        String ordertype = "";
        String oflevel = "";
        String hier = "";
        PreparedStatement psnew = null;
        ResultSet rsnew = null;
        String postrankid = "";
        String includeho = "";
        String include = "";
        String audi = "";
        String lab = "";
        String offidsreg = "";
        Map map = null;
        try {

            System.out.println("inside employee details report");

            offlevel = request.getParameter("offlevel");
            designationlevel = request.getParameter("designationlevel");
            outputtype = request.getParameter("outputtype");
            ordertype = request.getParameter("ordertype");

            System.out.println("Office Level:" + offlevel);
            System.out.println("Designation  Level:" + designationlevel);
            System.out.println("Output Type:" + outputtype);
            System.out.println("Order Type:" + ordertype);

            designation = request.getParameter("designation");
            System.out.println("Designation  Level:" + designation);

            office = request.getParameter("office");
            System.out.println("Office Range Combo:" + office);

            officetype = request.getParameter("officetype");
            System.out.println("Office Type Option:" + officetype);

            officeselected = request.getParameter("officeselected");
            System.out.println("Office Selected:---------------------office>" +
                               officeselected);

            oflevel = request.getParameter("rad_off");
            System.out.println("office level new..." + oflevel);

            /*   includeho=request.getParameter("includeho");
        System.out.println("include ho is ====="+includeho);
        if(includeho==null) {
            includeho="unchecked";
            System.out.println("unchecked.....");
        }*/

            hier = request.getParameter("allhier");
            System.out.println("include off hier..." + hier);

            audi = request.getParameter("audi");
            System.out.println("include off audi..." + audi);

            lab = request.getParameter("lab");
            System.out.println("include off hier..." + lab);

            if (hier == null) {
                hier = "unchecked";
                System.out.println("hier is unchecked.....");
            }

            if (audi == null) {
                audi = "unchecked";
                System.out.println("audi is unchecked.....");
            }

            if (lab == null) {
                lab = "unchecked";
                System.out.println("lab is unchecked.....");
            }
        } catch (Exception e) {
            System.out.println("Assigning Error:" + e);
        }

        try {
            System.out.println("calling Employee Detail servlet");


            /*************************************************************************/
            /*  to get the office level  */
            HttpSession session = request.getSession(false);
            UserProfile empProfile =
                (UserProfile)session.getAttribute("UserProfile");

            System.out.println("user id::" + empProfile.getEmployeeId());
            int empid = empProfile.getEmployeeId();
            int oid = 0;
            String deptid = "", officelevel = "";

            PreparedStatement ps = null;
            PreparedStatement ps1 = null;


            try {

                ps =
  connection.prepareStatement("select b.OFFICE_LEVEL_ID,a.office_id from hrm_emp_current_posting a " +
                              " inner join com_mst_offices b on b.office_id=a.office_id " +
                              " where a.employee_id=?");
                ps.setInt(1, empid);
                ResultSet results = ps.executeQuery();
                System.out.println("Employee id:" + empid);

                if (results.next()) {
                    officelevel = results.getString("OFFICE_LEVEL_ID");
                    oid = results.getInt("office_id");
                }
                results.close();
                ps.close();
                /* other office  */
                String profile = (String)session.getAttribute("profile");
                if (profile.equalsIgnoreCase("other")) {
                    officelevel = "HO";
                    ps =
  connection.prepareStatement("select office_id from com_mst_offices where  office_level_id=? ");
                    ps.setString(1, officelevel);
                    results = ps.executeQuery();
                    if (results.next()) {
                        oid = results.getInt("office_id");
                    }
                }
                /* other office  */
                System.out.println("office id:" + oid);
                System.out.println("dept id:" + deptid);

            } catch (Exception e) {
                System.out.println(e);
            }

            /*  to get the office level  */


            // order by  Office / Designation
            String offids = "";

            // else //specific offcies
            // {
            if (oflevel.equalsIgnoreCase("HO")) { //Head Office
                System.out.println("report 2.1");
                offids = String.valueOf(oid);
                System.out.println("inside ho report 2.1..." + offids);

            } else if (oflevel.equalsIgnoreCase("RG")) { //Regin  Office

                System.out.println("inside region");
                System.out.println("office level..." + officelevel);
                System.out.println("hier value is " + hier);
                System.out.println("include ho is ================" +
                                   includeho);
                // if(hier.equalsIgnoreCase("checked")|includeho.equalsIgnoreCase("checked"))  // Region All
                if (hier.equalsIgnoreCase("checked") &&
                    audi.equalsIgnoreCase("checked")) // Region All
                {
                    System.out.println("hier is checked and audi is checked");
                    System.out.println("report 3.1");
                    if (officelevel.equalsIgnoreCase("HO")) {
                        try {
                            ps =
  connection.prepareStatement("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('RN','AW') and region_office_id in (" +
                              officeselected + ") order by off_order");
                            ResultSet results = null;
                            results = ps.executeQuery();
                            int i = 0;
                            //oid=5000;
                            System.out.println("office id:" + offids);
                            while (results.next()) {
                                offids += results.getInt("office_id") + ",";
                                //offids=results.getInt("office_id")+",";
                                i++;
                            }
                            System.out.println("outside i " + offids);
                            if (i != 0) {
                                offids += oid;
                                offids = offids.substring(0, offids.length());
                                System.out.println("in i..." + offids);
                            } else {
                                offids = String.valueOf(oid);
                            }


                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }

                    } else if (officelevel.equalsIgnoreCase("RN")) {
                        offids = String.valueOf(oid);
                    }

                } else if (hier.equalsIgnoreCase("checked") &&
                           audi.equalsIgnoreCase("unchecked")) // Region All
                {
                    System.out.println("hier is checked and audi is uncheckd");
                    System.out.println("report 3.1");
                    if (officelevel.equalsIgnoreCase("HO")) {
                        try {
                            ps =
  connection.prepareStatement("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('RN') and region_office_id in (" +
                              officeselected + ") order by off_order");
                            ResultSet results = null;
                            results = ps.executeQuery();
                            int i = 0;
                            //oid=5000;
                            System.out.println("office id:" + offids);
                            while (results.next()) {
                                offids += results.getInt("office_id") + ",";
                                //offids=results.getInt("office_id")+",";
                                i++;
                            }
                            System.out.println("outside i " + offids);
                            if (i != 0) {
                                offids += oid;
                                offids = offids.substring(0, offids.length());
                                System.out.println("in i..." + offids);
                            } else {
                                offids = String.valueOf(oid);
                            }


                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }

                    } else if (officelevel.equalsIgnoreCase("RN")) {
                        offids = String.valueOf(oid);
                    }
                }

                else if (hier.equalsIgnoreCase("unchecked") &&
                         audi.equalsIgnoreCase("checked")) // Region All
                {
                    System.out.println("hier is unchecked and audi is checked");

                    if (officelevel.equalsIgnoreCase("HO")) {
                        try {
                            ps =
  connection.prepareStatement("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('RN','AW') and region_office_id in (" +
                              officeselected + ") order by off_order");
                            ResultSet results = null;
                            results = ps.executeQuery();
                            int i = 0;
                            //oid=5000;
                            System.out.println("office id:" + offids);
                            while (results.next()) {
                                offids += results.getInt("office_id") + ",";
                                //offids=results.getInt("office_id")+",";
                                i++;
                            }
                            System.out.println("outside i " + offids);
                            if (i != 0) {

                                offids =
                                        offids.substring(0, offids.length() - 1);
                                System.out.println("in i..." + offids);
                            } else {
                                offids = String.valueOf(oid);
                            }


                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }

                    } else if (officelevel.equalsIgnoreCase("RN")) {
                        offids = String.valueOf(oid);
                    }


                }

                else if (hier.equalsIgnoreCase("unchecked") &&
                         audi.equalsIgnoreCase("unchecked")) {
                    System.out.println("hier is unchecked and audi is unchecked");
                    System.out.println("inside specific region");


                    System.out.println("inside specific region");
                    System.out.println("report 4.1");
                    if (officelevel.equalsIgnoreCase("HO")) {
                        try {
                            // from this it is real
                            //offids=officeselected+","+oid;
                            offids = officeselected;
                            System.out.println("region selected..." + offids);

                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }

                    } else if (officelevel.equalsIgnoreCase("RN")) {
                        offids = String.valueOf(oid);
                    }


                }

            }

            else if (oflevel.equalsIgnoreCase("CR")) { //Circle  Office

                //else // Circle Specific
                System.out.println("the officeselected is" + officeselected);
                if (hier.equalsIgnoreCase("checked") &&
                    lab.equalsIgnoreCase("checked")) {
                    System.out.println("report 6.1");
                    if (officelevel.equalsIgnoreCase("HO")) {
                        try {
                            Statement st = connection.createStatement();
                            ResultSet rs = null;
                            rs =
  st.executeQuery("select distinct REGION_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('RN','CL','LB') and OFFICE_ID in (" +
                  officeselected + ")");
                            int j = 0;
                            while (rs.next()) {
                                offids += rs.getInt("REGION_OFFICE_ID") + ",";
                                System.out.println("offids " + offids);
                                j++;
                            }
                            try {
                                rs =
  st.executeQuery("select distinct lab_office_id from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('LB') and circle_OFFICE_ID in (" +
                  officeselected + ")");
                                System.out.println("select distinct lab_office_id from com_mst_all_offices_view where office_level_id in('LB') and circle_office_id in (" +
                                                   officeselected + ")");
                                int i = 0;
                                while (rs.next()) {
                                    offidsreg +=
                                            rs.getInt("LAB_OFFICE_ID") + ",";

                                    System.out.println("offids lab issssss" +
                                                       offidsreg);
                                    i++;
                                }
                            } catch (Exception e) {
                                System.out.println("error");
                            }
                            offids += officeselected + "," + offidsreg + oid;
                            System.out.println("the office id isss" +
                                               offids); //from this it is real

                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }

                    } else if (officelevel.equalsIgnoreCase("RN")) {
                        try {
                            offids =
                                    officeselected + "," + oid; //from this it is real

                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }
                    } else if (officelevel.equalsIgnoreCase("CL")) {
                        offids = String.valueOf(oid);
                    }


                }

                else if (hier.equalsIgnoreCase("checked") &&
                         lab.equalsIgnoreCase("unchecked")) {
                    System.out.println("report 6.1");
                    if (officelevel.equalsIgnoreCase("HO")) {
                        try {

                            Statement st = connection.createStatement();
                            ResultSet rs = null;
                            rs =
  st.executeQuery("select distinct REGION_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('RN','CL') and OFFICE_ID in (" +
                  officeselected + ")");
                            int j = 0;
                            while (rs.next()) {
                                offids += rs.getInt("REGION_OFFICE_ID") + ",";
                                j++;
                            }
                            offids +=
                                    officeselected + "," + oid; //from this it is real
                            // offids+=officeselected;
                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }

                    } else if (officelevel.equalsIgnoreCase("RN")) {
                        try {
                            offids =
                                    officeselected + "," + oid; //from this it is real
                            // offids=officeselected;
                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }
                    } else if (officelevel.equalsIgnoreCase("CL")) {
                        offids = String.valueOf(oid);
                    }


                }

                else if (hier.equalsIgnoreCase("unchecked") &&
                         lab.equalsIgnoreCase("checked")) {
                    System.out.println("report 6.1 else");
                    if (officelevel.equalsIgnoreCase("HO")) {
                        try {

                            Statement st = connection.createStatement();
                            ResultSet rs = null;
                            rs =
  st.executeQuery("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('CL','LB') and circle_office_id in (" +
                  officeselected + ") order by off_order");
                            int j = 0;
                            while (rs.next()) {
                                offids += rs.getInt("OFFICE_ID") + ",";
                                j++;
                            }
                            //offids+=officeselected+","+oid;  from this it is real
                            offids += officeselected;
                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }

                    } else if (officelevel.equalsIgnoreCase("RN")) {
                        try {
                            // offids=officeselected+","+oid; from this it is real
                            offids = officeselected;
                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }
                    } else if (officelevel.equalsIgnoreCase("CL")) {
                        offids = String.valueOf(oid);
                    }

                }

                else if (hier.equalsIgnoreCase("unchecked") &&
                         lab.equalsIgnoreCase("unchecked")) {
                    System.out.println("report 6.1 else");
                    if (officelevel.equalsIgnoreCase("HO")) {
                        try {

                            Statement st = connection.createStatement();
                            ResultSet rs = null;
                            rs =
  st.executeQuery("select distinct REGION_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID in (" +
                  officeselected + ")");
                            int j = 0;
                            while (rs.next()) {
                                offids += rs.getInt("REGION_OFFICE_ID") + ",";
                                j++;
                            }
                            //offids+=officeselected+","+oid;  from this it is real
                            offids += officeselected;
                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }

                    } else if (officelevel.equalsIgnoreCase("RN")) {
                        try {
                            // offids=officeselected+","+oid; from this it is real
                            offids = officeselected;
                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }
                    } else if (officelevel.equalsIgnoreCase("CL")) {
                        offids = String.valueOf(oid);
                    }

                }

            }

            else if (oflevel.equalsIgnoreCase("AW")) { //Audit  Office

                //else // Circle Specific
                if (hier.equalsIgnoreCase("checked")) {
                    System.out.println("report 1000.1");
                    if (officelevel.equalsIgnoreCase("HO")) {
                        try {

                            Statement st = connection.createStatement();
                            ResultSet rs = null;
                            rs =
  st.executeQuery("select distinct REGION_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('RN','AW') and OFFICE_ID in (" +
                  officeselected + ")");
                            int j = 0;
                            while (rs.next()) {
                                offids += rs.getInt("REGION_OFFICE_ID") + ",";
                                j++;
                            }
                            offids +=
                                    officeselected + "," + oid; //from this it is real
                            // offids+=officeselected;
                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }

                    } else if (officelevel.equalsIgnoreCase("RN")) {
                        try {
                            offids =
                                    officeselected + "," + oid; //from this it is real
                            // offids=officeselected;
                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }
                    } else if (officelevel.equalsIgnoreCase("AW")) {
                        offids = String.valueOf(oid);
                    }


                } else {
                    System.out.println("report 11111.1 else");
                    if (officelevel.equalsIgnoreCase("HO")) {
                        try {

                            Statement st = connection.createStatement();
                            ResultSet rs = null;
                            rs =
  st.executeQuery("select distinct REGION_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID in (" +
                  officeselected + ")");
                            int j = 0;
                            while (rs.next()) {
                                offids += rs.getInt("REGION_OFFICE_ID") + ",";
                                j++;
                            }
                            //offids+=officeselected+","+oid;  from this it is real
                            offids += officeselected;
                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }

                    } else if (officelevel.equalsIgnoreCase("RN")) {
                        try {
                            // offids=officeselected+","+oid; from this it is real
                            offids = officeselected;
                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }
                    } else if (officelevel.equalsIgnoreCase("AW")) {
                        offids = String.valueOf(oid);
                    }

                }

            }

            //////////////////////End Of Audit Wing///////////////////////////////

            else if (oflevel.equalsIgnoreCase("DV")) { //Division  Office

                //  else // Division Specific
                if (hier.equalsIgnoreCase("checked")) {
                    System.out.println("report is 8.1");


                    if (officelevel.equalsIgnoreCase("HO")) {
                        try {

                            Statement st = connection.createStatement();
                            ResultSet rs = null;
                            rs =
  st.executeQuery("select distinct CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID in (" +
                  officeselected + ")");
                            int i = 0;
                            while (rs.next()) {
                                offids += rs.getInt("CIRCLE_OFFICE_ID") + ",";
                                i++;
                            }
                            if (i != 0) {
                                st = connection.createStatement();
                                rs = null;
                                String tempoffids =
                                    offids.substring(0, offids.length() - 1);
                                rs =
  st.executeQuery("select distinct REGION_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID in (" +
                  tempoffids + ")");
                                int k = 0;
                                while (rs.next()) {
                                    offids +=
                                            rs.getInt("REGION_OFFICE_ID") + ",";
                                    k++;
                                }
                                offids +=
                                        officeselected + "," + oid; //from this it is real
                                // offids+=officeselected;
                            } else {
                                offids +=
                                        officeselected + "," + oid; //from this it is real
                                // offids+=officeselected;
                            }
                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }

                    } else if (officelevel.equalsIgnoreCase("RN")) {
                        try {

                            Statement st = connection.createStatement();
                            ResultSet rs = null;
                            rs =
  st.executeQuery("select distinct CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID in (" +
                  officeselected + ")");
                            int i = 0;
                            while (rs.next()) {
                                offids += rs.getInt("CIRCLE_OFFICE_ID") + ",";
                                i++;
                            }
                            offids +=
                                    officeselected + "," + oid; // from this it is real

                            // offids+=officeselected;
                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }
                    } else if (officelevel.equalsIgnoreCase("CL")) {
                        offids =
                                officeselected + "," + oid; // from this it is real
                        // offids=officeselected;
                    } else if (officelevel.equalsIgnoreCase("DN")) {
                        offids = String.valueOf(oid);
                    }

                    // }


                } else {

                    System.out.println("report is 8.1 else");

                    // String agg=request.getParameter("aggid");


                    if (officelevel.equalsIgnoreCase("HO")) {
                        try {

                            Statement st = connection.createStatement();
                            ResultSet rs = null;
                            rs =
  st.executeQuery("select distinct CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID in (" +
                  officeselected + ")");
                            int i = 0;
                            while (rs.next()) {
                                offids += rs.getInt("CIRCLE_OFFICE_ID") + ",";
                                i++;
                            }
                            if (i != 0) {
                                st = connection.createStatement();
                                rs = null;
                                String tempoffids =
                                    offids.substring(0, offids.length() - 1);
                                rs =
  st.executeQuery("select distinct REGION_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID in (" +
                  tempoffids + ")");
                                int k = 0;
                                while (rs.next()) {
                                    offids +=
                                            rs.getInt("REGION_OFFICE_ID") + ",";
                                    k++;
                                }
                                //  offids+=officeselected+","+oid; from this it is real
                                offids += officeselected;
                            } else {
                                //   offids+=officeselected+","+oid;  from this it is real
                                offids += officeselected;
                            }
                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }

                    } else if (officelevel.equalsIgnoreCase("RN")) {
                        try {

                            Statement st = connection.createStatement();
                            ResultSet rs = null;
                            rs =
  st.executeQuery("select distinct CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID in (" +
                  officeselected + ")");
                            int i = 0;
                            while (rs.next()) {
                                offids += rs.getInt("CIRCLE_OFFICE_ID") + ",";
                                i++;
                            }
                            //   offids+=officeselected+","+oid; from this it is real

                            offids += officeselected;
                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }
                    } else if (officelevel.equalsIgnoreCase("CL")) {
                        //  offids=officeselected+","+oid;  from this it is real
                        offids = officeselected;
                    } else if (officelevel.equalsIgnoreCase("DN")) {
                        System.out.println("here it comes");
                        offids = String.valueOf(oid);
                    }

                    //  }


                }


            }

            else if (oflevel.equalsIgnoreCase("LB")) { //Lab  Office

                //  else // Lab Specific
                if (hier.equalsIgnoreCase("checked")) {
                    System.out.println("report is 12.1");


                    if (officelevel.equalsIgnoreCase("HO")) {
                        try {

                            Statement st = connection.createStatement();
                            ResultSet rs = null;
                            rs =
  st.executeQuery("select distinct CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID in (" +
                  officeselected + ")");
                            int i = 0;
                            while (rs.next()) {
                                offids += rs.getInt("CIRCLE_OFFICE_ID") + ",";
                                i++;
                            }
                            if (i != 0) {
                                st = connection.createStatement();
                                rs = null;
                                String tempoffids =
                                    offids.substring(0, offids.length() - 1);
                                rs =
  st.executeQuery("select distinct REGION_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID in (" +
                  tempoffids + ")");
                                int k = 0;
                                while (rs.next()) {
                                    offids +=
                                            rs.getInt("REGION_OFFICE_ID") + ",";
                                    k++;
                                }
                                offids +=
                                        officeselected + "," + oid; //from this it is real
                                // offids+=officeselected;
                            } else {
                                offids +=
                                        officeselected + "," + oid; //from this it is real
                                // offids+=officeselected;
                            }
                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }

                    } else if (officelevel.equalsIgnoreCase("RN")) {
                        try {

                            Statement st = connection.createStatement();
                            ResultSet rs = null;
                            rs =
  st.executeQuery("select distinct CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID in (" +
                  officeselected + ")");
                            int i = 0;
                            while (rs.next()) {
                                offids += rs.getInt("CIRCLE_OFFICE_ID") + ",";
                                i++;
                            }
                            offids +=
                                    officeselected + "," + oid; // from this it is real

                            // offids+=officeselected;
                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }
                    } else if (officelevel.equalsIgnoreCase("CL")) {
                        offids =
                                officeselected + "," + oid; // from this it is real
                        // offids=officeselected;
                    } else if (officelevel.equalsIgnoreCase("LB")) {
                        offids = String.valueOf(oid);
                    }

                    // }


                } else {

                    System.out.println("report is 13.1 else");

                    // String agg=request.getParameter("aggid");


                    if (officelevel.equalsIgnoreCase("HO")) {
                        try {

                            Statement st = connection.createStatement();
                            ResultSet rs = null;
                            rs =
  st.executeQuery("select distinct CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID in (" +
                  officeselected + ")");
                            int i = 0;
                            while (rs.next()) {
                                offids += rs.getInt("CIRCLE_OFFICE_ID") + ",";
                                i++;
                            }
                            if (i != 0) {
                                st = connection.createStatement();
                                rs = null;
                                String tempoffids =
                                    offids.substring(0, offids.length() - 1);
                                rs =
  st.executeQuery("select distinct REGION_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID in (" +
                  tempoffids + ")");
                                int k = 0;
                                while (rs.next()) {
                                    offids +=
                                            rs.getInt("REGION_OFFICE_ID") + ",";
                                    k++;
                                }
                                //  offids+=officeselected+","+oid; from this it is real
                                offids += officeselected;
                            } else {
                                //   offids+=officeselected+","+oid;  from this it is real
                                offids += officeselected;
                            }
                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }

                    } else if (officelevel.equalsIgnoreCase("RN")) {
                        try {

                            Statement st = connection.createStatement();
                            ResultSet rs = null;
                            rs =
  st.executeQuery("select distinct CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID in (" +
                  officeselected + ")");
                            int i = 0;
                            while (rs.next()) {
                                offids += rs.getInt("CIRCLE_OFFICE_ID") + ",";
                                i++;
                            }
                            //   offids+=officeselected+","+oid; from this it is real

                            offids += officeselected;
                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }
                    } else if (officelevel.equalsIgnoreCase("CL")) {
                        //  offids=officeselected+","+oid;  from this it is real
                        offids = officeselected;
                    } else if (officelevel.equalsIgnoreCase("LB")) {
                        System.out.println("here it comes");
                        offids = String.valueOf(oid);
                    }

                    //  }

                }


            }


            try {

                String optbase = request.getParameter("optselect");
                System.out.println("Option Selected:" + optbase);
                String optbase1 = request.getParameter("optsel"); //not all
                System.out.println("Option Selected:" + optbase1);
                if (optbase1 == null)
                    optbase = "notAll";
                else
                    optbase = "All";


                int destRank = 0;
                String postrankid1 = "";
                System.out.println("before testing--------");

                if (optbase.equalsIgnoreCase("notAll")) {
                    //destRank=Integer.parseInt(request.getParameter("cmbRank"));
                    String testval1[] =
                        (request.getParameterValues("chkrank"));
                    for (int i = 0; i < testval1.length; i++) {
                        desigval = desigval + testval1[i] + ",";
                        System.out.println("Test values1:-----" + testval1[i]);
                    }
                    newdesigval = desigval.substring(0, desigval.length() - 1);
                    System.out.println("new rank values:" + newdesigval);
                }

                else {
                    System.out.println("All other");
                }

                int intpos[];
                if (newdesigval.length() > 0) {
                    String strpost[] = newdesigval.split(",");
                    intpos = new int[strpost.length];
                    for (int i = 0; i < strpost.length; i++) {
                        intpos[i] = Integer.parseInt(strpost[i]);
                        System.out.println("values" + intpos[i]);
                    }

                    System.out.println("After checking--------");

                    try {
                        System.out.println("inside try for converting post rak id");
                        for (int j = 0; j < intpos.length; j++) {
                            psnew =
connection.prepareStatement("select distinct post_rank_id from  hrm_mst_designations where designation_id in(?)");
                            psnew.setInt(1, intpos[j]);
                            rsnew = psnew.executeQuery();
                            if (rsnew.next()) {
                                postrankid =
                                        postrankid + rsnew.getString("post_rank_id") +
                                        ",";
                                //System.out.println(postrankid);
                            }
                        }
                    } catch (Exception e) {
                        System.out.println("err in getting post rank id");
                    }


                    postrankid1 =
                            postrankid.substring(0, postrankid.length() - 1);

                    System.out.println(postrankid1 + "ch------------------");

                    //if(optbase.equalsIgnoreCase("Rank")||optbase.equalsIgnoreCase("Cadr"))
                    if (optbase.equalsIgnoreCase("notAll")) {
                        postrankid1 = newdesigval;
                        System.out.println("id is 0000000000: " + postrankid1);
                    }

                }
                //  System.out.println("testing---------");
                map = new HashMap();
                System.out.println("Designation / Rank :" + destRank);
                System.out.println("Office Selected:" + officeselected);
                System.out.println("new designation values" + newdesigval);
                System.out.println("offices " + offids);
                System.out.println("the office level is" + oflevel);

                if (oflevel.equalsIgnoreCase("HO")) {

                    if (optbase.equalsIgnoreCase("notAll")) {
                        System.out.println("onside ho");
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionCirDivAll_New_agg100.jasper"));
                        officeselected = "5000";
                        System.out.println(reportFile);
                    } else {
                        reportFile =
                                new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionCirDivAll_New_agg1.jasper"));
                        officeselected = "5000";
                    }

                } else if (oflevel.equalsIgnoreCase("RG")) {
                    if (optbase.equalsIgnoreCase("notAll")) {
                        if (hier.equalsIgnoreCase("checked") &&
                            audi.equalsIgnoreCase("unchecked")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionCirDiv_New_agg6.jasper"));
                            System.out.println("reportFile region" +
                                               reportFile);
                            System.out.println("111111111111111111");
                        } else if (hier.equalsIgnoreCase("unchecked") &&
                                   audi.equalsIgnoreCase("unchecked")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionAudit_New.jasper"));
                            System.out.println("reportFile region" +
                                               reportFile);
                            System.out.println("22222222222222222");
                        } else if (hier.equalsIgnoreCase("unchecked") &&
                                   audi.equalsIgnoreCase("checked")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionAudit_New.jasper"));
                            System.out.println("reportFile region" +
                                               reportFile);
                            System.out.println("33333333333333");
                        }

                        else if (hier.equalsIgnoreCase("checked") &&
                                 audi.equalsIgnoreCase("checked")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionCirDiv_New_agg5.jasper"));
                            System.out.println("reportFile region" +
                                               reportFile);
                            System.out.println("44444444444444444444444");
                        }
                    } else {
                        if (hier.equalsIgnoreCase("checked") &&
                            audi.equalsIgnoreCase("unchecked")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionCirDiv_New_agg11.jasper"));
                            System.out.println("5555555555555555555555555");
                        } else if (hier.equalsIgnoreCase("unchecked") &&
                                   audi.equalsIgnoreCase("unchecked")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionCirDiv_New_agg12.jasper"));
                            System.out.println("666666666666666666666666666666");
                        }

                        else if (hier.equalsIgnoreCase("unchecked") &&
                                 audi.equalsIgnoreCase("checked")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionAudit_New2.jasper"));
                            System.out.println("reportFile region" +
                                               reportFile);
                            System.out.println("7777777777777777777777777");
                        }

                        else if (hier.equalsIgnoreCase("checked") &&
                                 audi.equalsIgnoreCase("checked")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionAudit_New2.jasper"));
                            System.out.println("reportFile region" +
                                               reportFile);
                            System.out.println("8888888888888888888888888888888888888");
                        }
                    }

                } else if (oflevel.equalsIgnoreCase("CR")) {
                    if (optbase.equalsIgnoreCase("notAll")) {
                        if (hier.equalsIgnoreCase("checked") &&
                            lab.equalsIgnoreCase("unchecked")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionCirDiv_New_agg3.jasper"));
                            System.out.println("reportFile circle" +
                                               reportFile);
                        } else if (hier.equalsIgnoreCase("unchecked") &&
                                   lab.equalsIgnoreCase("unchecked")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionCirDiv_New_agg4.jasper"));
                            System.out.println("reportFile circle" +
                                               reportFile);
                        } else if (hier.equalsIgnoreCase("unchecked") &&
                                   lab.equalsIgnoreCase("checked")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionLab_New.jasper"));
                            System.out.println("reportFile circle" +
                                               reportFile);
                        } else if (hier.equalsIgnoreCase("checked") &&
                                   lab.equalsIgnoreCase("checked")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionLab_New.jasper"));
                            System.out.println("reportFile circle" +
                                               reportFile);
                        }

                    } else {
                        if (hier.equalsIgnoreCase("checked") &&
                            lab.equalsIgnoreCase("unchecked")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionCirDiv_New_agg7.jasper"));
                        } else if (hier.equalsIgnoreCase("unchecked") &&
                                   lab.equalsIgnoreCase("unchecked")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionCirDiv_New_agg8.jasper"));
                        } else if (hier.equalsIgnoreCase("unchecked") &&
                                   lab.equalsIgnoreCase("checked")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionLab_New2.jasper"));
                            System.out.println("reportFile circle" +
                                               reportFile);
                        } else if (hier.equalsIgnoreCase("checked") &&
                                   lab.equalsIgnoreCase("checked")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionLab_New2.jasper"));
                            System.out.println("reportFile circle" +
                                               reportFile);
                        }
                    }
                }


                else if (oflevel.equalsIgnoreCase("AW")) {
                    if (optbase.equalsIgnoreCase("notAll")) {
                        if (hier.equalsIgnoreCase("checked")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionAudit_New.jasper"));
                            System.out.println("reportFile circle" +
                                               reportFile);
                            System.out.println("999999999999999999999999999999999999");
                        } else {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionAudit_New1.jasper"));
                            System.out.println("reportFile circle" +
                                               reportFile);
                            System.out.println("10000000000000000000000000");
                        }
                    } else {
                        if (hier.equalsIgnoreCase("checked")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionAudit_New2.jasper"));
                            System.out.println("110000000000000000000000");
                        } else {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionAudit_New3.jasper"));
                            System.out.println("120000000000000000000000000000000000");
                        }
                    }
                }

                else if (oflevel.equalsIgnoreCase("DV")) {
                    //if(optbase.equalsIgnoreCase("Dest")||optbase.equalsIgnoreCase("Rank")||optbase.equalsIgnoreCase("Cadr"))
                    if (optbase.equalsIgnoreCase("notAll")) {
                        if (hier.equalsIgnoreCase("checked")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionCirDiv_New_agg1.jasper"));
                            System.out.println("reportFile division" +
                                               reportFile);
                            System.out.println("130000000000000000000000000000000000");
                        } else {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionCirNew_agg1.jasper"));
                            System.out.println("reportFile division" +
                                               reportFile);
                            System.out.println("140000000000000000000000000000000000");
                        }
                    } else {
                        if (hier.equalsIgnoreCase("checked")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionCirDiv_New_agg9.jasper"));
                            System.out.println("150000000000000000000000000000000000");
                        } else {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionCirNew_agg10.jasper"));
                            System.out.println("160000000000000000000000000000000000");
                        }
                    }
                }

                //the report has to be done.... by priya
                else if (oflevel.equalsIgnoreCase("LB")) {
                    //if(optbase.equalsIgnoreCase("Dest")||optbase.equalsIgnoreCase("Rank")||optbase.equalsIgnoreCase("Cadr"))
                    if (optbase.equalsIgnoreCase("notAll")) {
                        if (hier.equalsIgnoreCase("checked")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionLab_New.jasper"));
                            System.out.println("170000000000000000000000000000000000");
                            System.out.println("reportFile division" +
                                               reportFile);
                        } else {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionLab_New1.jasper"));
                            System.out.println("reportFile division" +
                                               reportFile);
                            System.out.println("180000000000000000000000000000000000");
                        }
                    } else {
                        if (hier.equalsIgnoreCase("checked")) {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionLab_New2.jasper"));
                            System.out.println("190000000000000000000000000000000000");
                        } else {
                            reportFile =
                                    new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Vacancy_Position_Summary_HoRegionLab_New3.jasper"));
                            System.out.println("20000000.0000000000000000000000000000000000");
                        }
                    }
                }


                JasperReport jasperReport =
                    (JasperReport)JRLoader.loadObject(reportFile.getPath());

                System.out.println("offices " + offids);


                if (oflevel.equalsIgnoreCase("HO")) {
                    System.out.println("the office level is HO");
                    if (hier.equalsIgnoreCase("checked")) {
                        System.out.println("hier is checked" + offids);
                        if (optbase.equalsIgnoreCase("notAll")) {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("des_id", postrankid1);
                            map.put("finyear", curFinYear);
                        } else {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("finyear", curFinYear);
                        }
                    }

                    else if (hier.equalsIgnoreCase("unchecked")) {
                        System.out.println("hier is unchecked" + offids);
                        if (optbase.equalsIgnoreCase("notAll")) {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("des_id", postrankid1);
                            map.put("finyear", curFinYear);
                        } else {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("finyear", curFinYear);
                        }
                    } else {
                        if (optbase.equalsIgnoreCase("notAll")) {
                            System.out.println("othter");
                            System.out.println("office ids:" + offids);
                            System.out.println("desig ids:" + postrankid1);
                            //reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Emp_ValidationSummary_New.jasper"));
                            map.put("off_id", offids);
                            map.put("des_id", postrankid1);
                            map.put("finyear", curFinYear);
                        } else {
                            System.out.println("othter");
                            System.out.println("office ids:" + offids);
                            System.out.println("desig ids:" + postrankid1);
                            //reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Emp_ValidationSummary_New.jasper"));
                            map.put("off_id", offids);
                            map.put("finyear", curFinYear);
                        }
                    }
                }

                else if (oflevel.equalsIgnoreCase("RG")) {
                    System.out.println("the office level is region");
                    if (hier.equalsIgnoreCase("checked") &&
                        audi.equalsIgnoreCase("checked")) {
                        System.out.println("hier, audi is checked" + offids);
                        if (optbase.equalsIgnoreCase("notAll")) {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("des_id", postrankid1);
                            map.put("finyear", curFinYear);
                        } else {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("finyear", curFinYear);
                        }
                    }

                    else if (hier.equalsIgnoreCase("checked") &&
                             audi.equalsIgnoreCase("unchecked")) {
                        System.out.println("hier is checked and audi is unchecked" +
                                           offids);
                        if (optbase.equalsIgnoreCase("notAll")) {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("des_id", postrankid1);
                            map.put("finyear", curFinYear);
                        } else {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("finyear", curFinYear);
                        }
                    }

                    else if (hier.equalsIgnoreCase("unchecked") &&
                             audi.equalsIgnoreCase("checked")) {
                        System.out.println(" hier unchecked and audi is  checked" +
                                           offids);
                        if (optbase.equalsIgnoreCase("notAll")) {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("des_id", postrankid1);
                            map.put("finyear", curFinYear);
                        } else {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("finyear", curFinYear);
                        }
                    }

                    else if (hier.equalsIgnoreCase("unchecked") &&
                             audi.equalsIgnoreCase("unchecked")) {
                        System.out.println("both hier and audi is unchecked" +
                                           offids);
                        if (optbase.equalsIgnoreCase("notAll")) {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("des_id", postrankid1);
                            map.put("finyear", curFinYear);
                        } else {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("finyear", curFinYear);
                        }
                    } else {
                        if (optbase.equalsIgnoreCase("notAll")) {
                            System.out.println("othter");
                            System.out.println("office ids:" + offids);
                            System.out.println("desig ids:" + postrankid1);
                            //reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Emp_ValidationSummary_New.jasper"));
                            map.put("off_id", offids);
                            map.put("des_id", postrankid1);
                            map.put("finyear", curFinYear);
                        } else {
                            System.out.println("othter");
                            System.out.println("office ids:" + offids);
                            System.out.println("desig ids:" + postrankid1);
                            //reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Emp_ValidationSummary_New.jasper"));
                            map.put("off_id", offids);
                            map.put("finyear", curFinYear);
                        }
                    }
                }

                else if (oflevel.equalsIgnoreCase("CR")) {
                    System.out.println("The office level is circle");
                    if (hier.equalsIgnoreCase("checked") &&
                        lab.equalsIgnoreCase("checked")) {
                        System.out.println("hier, lab is checked" + offids);
                        if (optbase.equalsIgnoreCase("notAll")) {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("des_id", postrankid1);
                            map.put("finyear", curFinYear);
                        } else {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("finyear", curFinYear);
                        }
                    }

                    else if (hier.equalsIgnoreCase("checked") &&
                             lab.equalsIgnoreCase("unchecked")) {
                        System.out.println("hier is checked and lab is unchecked" +
                                           offids);
                        if (optbase.equalsIgnoreCase("notAll")) {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("des_id", postrankid1);
                            map.put("finyear", curFinYear);
                        } else {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("finyear", curFinYear);
                        }
                    }

                    else if (hier.equalsIgnoreCase("unchecked") &&
                             lab.equalsIgnoreCase("checked")) {
                        System.out.println(" hier unchecked and lab is  checked" +
                                           offids);
                        if (optbase.equalsIgnoreCase("notAll")) {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("des_id", postrankid1);
                            map.put("finyear", curFinYear);
                        } else {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("finyear", curFinYear);
                        }
                    }

                    else if (hier.equalsIgnoreCase("unchecked") &&
                             lab.equalsIgnoreCase("unchecked")) {
                        System.out.println("both hier and lab is unchecked" +
                                           offids);
                        if (optbase.equalsIgnoreCase("notAll")) {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("des_id", postrankid1);
                            map.put("finyear", curFinYear);
                        } else {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("finyear", curFinYear);
                        }
                    } else {
                        if (optbase.equalsIgnoreCase("notAll")) {
                            System.out.println("othter");
                            System.out.println("office ids:" + offids);
                            System.out.println("desig ids:" + postrankid1);
                            //reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Emp_ValidationSummary_New.jasper"));
                            map.put("off_id", offids);
                            map.put("des_id", postrankid1);
                            map.put("finyear", curFinYear);
                        } else {
                            System.out.println("othter");
                            System.out.println("office ids:" + offids);
                            System.out.println("desig ids:" + postrankid1);
                            //reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Emp_ValidationSummary_New.jasper"));
                            map.put("off_id", offids);
                            map.put("finyear", curFinYear);
                        }
                    }

                }

                else if (oflevel.equalsIgnoreCase("AW")) {
                    System.out.println("the office level is Audit wing");

                    if (hier.equalsIgnoreCase("checked")) {
                        System.out.println("hier is checked" + offids);
                        if (optbase.equalsIgnoreCase("notAll")) {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("des_id", postrankid1);
                            map.put("finyear", curFinYear);
                        } else {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("finyear", curFinYear);
                        }
                    }

                    else if (hier.equalsIgnoreCase("unchecked")) {
                        System.out.println(" hier  is unchecked" + offids);
                        if (optbase.equalsIgnoreCase("notAll")) {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("des_id", postrankid1);
                            map.put("finyear", curFinYear);
                        } else {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("finyear", curFinYear);
                        }
                    } else {
                        if (optbase.equalsIgnoreCase("notAll")) {
                            System.out.println("othter");
                            System.out.println("office ids:" + offids);
                            System.out.println("desig ids:" + postrankid1);
                            //reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Emp_ValidationSummary_New.jasper"));
                            map.put("off_id", offids);
                            map.put("des_id", postrankid1);
                            map.put("finyear", curFinYear);
                        } else {
                            System.out.println("othter");
                            System.out.println("office ids:" + offids);
                            System.out.println("desig ids:" + postrankid1);
                            //reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Emp_ValidationSummary_New.jasper"));
                            map.put("off_id", offids);
                            map.put("finyear", curFinYear);
                        }
                    }

                }

                else if (oflevel.equalsIgnoreCase("LB")) {
                    System.out.println("the office level is Lab");

                    if (hier.equalsIgnoreCase("checked")) {
                        System.out.println("hier is checked" + offids);
                        if (optbase.equalsIgnoreCase("notAll")) {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("des_id", postrankid1);
                            map.put("finyear", curFinYear);
                        } else {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("finyear", curFinYear);
                        }
                    }

                    else if (hier.equalsIgnoreCase("unchecked")) {
                        System.out.println(" hier  is unchecked" + offids);
                        if (optbase.equalsIgnoreCase("notAll")) {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("des_id", postrankid1);
                            map.put("finyear", curFinYear);
                        } else {
                            System.out.println("offids" + offids);
                            map.put("off_id", offids);
                            map.put("finyear", curFinYear);
                        }
                    }

                    else {
                        if (optbase.equalsIgnoreCase("notAll")) {
                            System.out.println("othter");
                            System.out.println("office ids:" + offids);
                            System.out.println("desig ids:" + postrankid1);
                            //reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Emp_ValidationSummary_New.jasper"));
                            map.put("off_id", offids);
                            map.put("des_id", postrankid1);
                            map.put("finyear", curFinYear);
                        } else {
                            System.out.println("othter");
                            System.out.println("office ids:" + offids);
                            System.out.println("desig ids:" + postrankid1);
                            //reportFile = new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/Emp_ValidationSummary_New.jasper"));
                            map.put("off_id", offids);
                            map.put("finyear", curFinYear);
                        }
                    }
                }


                JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperReport, map,
                                                 connection);


                if (!reportFile.exists())
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");


                String rtype = request.getParameter("outputtype");
                if (rtype.equalsIgnoreCase("HTML")) {
                    response.setContentType("text/html");
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Vacancy_Position_Summary_Report.html\"");
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
                    exporter.setParameter(JRExporterParameter.OUTPUT_WRITER,
                                          out);
                    exporter.exportReport();
                    out.flush();
                    out.close();
                } else if (rtype.equalsIgnoreCase("PDF")) {
                    System.out.println("pdf generated");

                    byte buf[] =
                        JasperExportManager.exportReportToPdf(jasperPrint);
                    response.setContentType("application/pdf");
                    response.setContentLength(buf.length);
                    // response.setHeader("content-disposition", "inline;filename=OpenActionItems.pdf");
                    //response.setContentType("application/force-download");

                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Vacancy_Position_Summary_Report.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
                } else if (rtype.equalsIgnoreCase("EXCEL")) {

                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Vacancy_Position_Summary_Report.xls\"");
                    JRXlsExporter exporterXLS = new JRXlsExporter();
                    exporterXLS.setParameter(JRXlsExporterParameter.JASPER_PRINT,
                                             jasperPrint);

                    ByteArrayOutputStream xlsReport =
                        new ByteArrayOutputStream();
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
                    ServletOutputStream ouputStream =
                        response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();

                } else if (rtype.equalsIgnoreCase("TXT")) {

                    response.setContentType("text/plain");
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Vacancy_Position_Summary_Report.txt\"");

                    JRTextExporter exporter = new JRTextExporter();
                    exporter.setParameter(JRExporterParameter.JASPER_PRINT,
                                          jasperPrint);
                    ByteArrayOutputStream txtReport =
                        new ByteArrayOutputStream();
                    exporter.setParameter(JRExporterParameter.OUTPUT_STREAM,
                                          txtReport);
                    exporter.setParameter(JRTextExporterParameter.CHARACTER_WIDTH,
                                          new Integer(200));
                    exporter.setParameter(JRTextExporterParameter.CHARACTER_HEIGHT,
                                          new Integer(50));
                    exporter.exportReport();

                    byte[] bytes;
                    bytes = txtReport.toByteArray();
                    ServletOutputStream ouputStream =
                        response.getOutputStream();
                    ouputStream.write(bytes, 0, bytes.length);
                    ouputStream.flush();
                    ouputStream.close();

                }

            } catch (Exception e) {
                System.out.println(e.getMessage());
            }


        } catch (Exception ex) {
            String connectMsg =
                "Could not create the report " + ex.getMessage() + " " +
                ex.getLocalizedMessage();
            System.out.println(connectMsg);
        }


    }


    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {


        Connection connection = null;
        PreparedStatement ps1 = null;
        Statement s = null;
        ResultSet result1 = null;
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
        ResultSet result = null;
        PreparedStatement ps = null;

        System.out.println("Employee Detail Report GET");
        response.setContentType("text/html");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String strCommand = "", optview = "";

        try {
            strCommand = request.getParameter("OLevel");
            System.out.println("Command....." + strCommand);
            optview = request.getParameter("optview");
            System.out.println("optview....." + optview);

        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
        String html = "";
        if (strCommand.equalsIgnoreCase("region")) {


            /*  to get the office level  */
            HttpSession session = request.getSession(false);
            UserProfile empProfile =
                (UserProfile)session.getAttribute("UserProfile");

            System.out.println("user id::" + empProfile.getEmployeeId());
            int empid = empProfile.getEmployeeId();
            int oid = 0;
            String deptid = "", offlevel = "";

            try {

                ps =
  connection.prepareStatement("select b.OFFICE_LEVEL_ID,a.office_id from hrm_emp_current_posting a " +
                              " inner join com_mst_offices b on b.office_id=a.office_id " +
                              " where a.employee_id=?");
                ps.setInt(1, empid);
                ResultSet results = ps.executeQuery();
                if (results.next()) {
                    offlevel = results.getString("OFFICE_LEVEL_ID");
                    oid = results.getInt("office_id");
                }
                results.close();
                ps.close();
                /* other office  */
                String profile = (String)session.getAttribute("profile");
                if (profile.equalsIgnoreCase("other")) {
                    offlevel = "HO";
                    ps =
  connection.prepareStatement("select office_id from com_mst_offices where  office_level_id=? ");
                    ps.setString(1, offlevel);
                    results = ps.executeQuery();
                    if (results.next()) {
                        oid = results.getInt("office_id");
                    }
                }
                /* other office  */
                System.out.println("office id:" + oid);
                System.out.println("dept id:" + deptid);

            } catch (Exception e) {
                System.out.println(e);
            }

            /*  to get the office level  */

            /* find the specific Office */


            try {
                if (offlevel.equalsIgnoreCase("CL")) {
                    PreparedStatement psc =
                        connection.prepareStatement("select CONTROLLING_OFFICE_ID from COM_OFFICE_CONTROL where OFFICE_ID=?");
                    psc.setInt(1, oid);
                    ResultSet rsc = psc.executeQuery();
                    if (rsc.next()) {
                        oid = rsc.getInt("CONTROLLING_OFFICE_ID");
                    }
                } else if (offlevel.equalsIgnoreCase("DN")) {
                    PreparedStatement psd =
                        connection.prepareStatement("select CONTROLLING_OFFICE_ID from COM_OFFICE_CONTROL where OFFICE_ID=?");
                    psd.setInt(1, oid);
                    ResultSet rsd = psd.executeQuery();
                    if (rsd.next()) {
                        int officecl = rsd.getInt("CONTROLLING_OFFICE_ID");
                        PreparedStatement psc =
                            connection.prepareStatement("select CONTROLLING_OFFICE_ID from COM_OFFICE_CONTROL where OFFICE_ID=?");
                        psc.setInt(1, officecl);
                        ResultSet rsc = psc.executeQuery();
                        if (rsc.next()) {
                            oid = rsc.getInt("CONTROLLING_OFFICE_ID");
                        }
                    }
                }
            } catch (Exception e) {
                System.out.println("error in find specific region :" + e);
            }


            System.out.println("region office Id:" + oid);

            /* end of find the specific office  region*/


            System.out.println("region");
            System.out.println("Command::" + strCommand);
            try {
                //String sql="select  REGION_OFFICE_ID,REGION_OFFICE_NAME from COM_MST_REGIONS_HVIEW  ";
                if (offlevel.equalsIgnoreCase("HO")) {
                    String sql =
                        "select  REGION_OFFICE_ID,REGION_OFFICE_NAME from COM_MST_REGIONS_HVIEW ";
                    ps = connection.prepareStatement(sql);
                    result = ps.executeQuery();
                } else {
                    String sql =
                        "select  REGION_OFFICE_ID,REGION_OFFICE_NAME from COM_MST_REGIONS_HVIEW where REGION_OFFICE_ID=? ";
                    ps = connection.prepareStatement(sql);
                    ps.setInt(1, oid);
                    result = ps.executeQuery();
                }

                int count = 0;
                html =
"<table cellpadding=0 cellspacing=0 border=0 width='100%'>";
                html =
html + "<tr ><td colspan='2'><a href='javascript:regionselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:regionclose()'>Close</a></td></tr>";
                boolean bool = false;
                while (result.next()) {
                    if (bool = !bool) {

                        html =
html + "<tr bgcolor=\"pink\"><td><input type='checkbox' name='chkregion' value='" +
 result.getInt("REGION_OFFICE_ID") + "'  ></td>";
                        html =
html + "<td>" + result.getString("REGION_OFFICE_NAME") + "</td></tr>";
                    } else {

                        html =
html + "<tr ><td><input type='checkbox' name='chkregion' value='" +
 result.getInt("REGION_OFFICE_ID") + "'></td>";
                        html =
html + "<td>" + result.getString("REGION_OFFICE_NAME") + "</td></tr>";
                    }
                    count++;
                }

                if (count == 0) {
                    html = "There is no Region";
                }

                html = html + "</table>";

            } catch (Exception e) {
                System.out.println("Region selection error " + e);
                html = "There is no Region";

            }

        } else if (strCommand.equalsIgnoreCase("circle")) {

            System.out.println("circle");
            System.out.println("Command::" + strCommand);


            /*  to get the office level  */
            HttpSession session = request.getSession(false);
            UserProfile empProfile =
                (UserProfile)session.getAttribute("UserProfile");

            System.out.println("user id::" + empProfile.getEmployeeId());
            int empid = empProfile.getEmployeeId();
            int oid = 0;
            String deptid = "", offlevel = "";

            try {

                ps =
  connection.prepareStatement("select b.OFFICE_LEVEL_ID,a.office_id from hrm_emp_current_posting a " +
                              " inner join com_mst_offices b on b.office_id=a.office_id " +
                              " where a.employee_id=?");
                ps.setInt(1, empid);
                ResultSet results = ps.executeQuery();
                if (results.next()) {
                    offlevel = results.getString("OFFICE_LEVEL_ID");
                    oid = results.getInt("office_id");
                }
                results.close();
                ps.close();
                /* other office  */
                String profile = (String)session.getAttribute("profile");
                if (profile.equalsIgnoreCase("other")) {
                    offlevel = "HO";
                    ps =
  connection.prepareStatement("select office_id from com_mst_offices where  office_level_id=? ");
                    ps.setString(1, offlevel);
                    results = ps.executeQuery();
                    if (results.next()) {
                        oid = results.getInt("office_id");
                    }
                }
                /* other office  */
                System.out.println("office id:" + oid);
                System.out.println("dept id:" + deptid);

            } catch (Exception e) {
                System.out.println(e);
            }

            /*  to get the office level  */

            /* find the specific Office */


            try {
                if (offlevel.equalsIgnoreCase("DN")) {
                    PreparedStatement psd =
                        connection.prepareStatement("select CONTROLLING_OFFICE_ID from COM_OFFICE_CONTROL where OFFICE_ID=?");
                    psd.setInt(1, oid);
                    ResultSet rsd = psd.executeQuery();
                    if (rsd.next()) {
                        oid = rsd.getInt("CONTROLLING_OFFICE_ID");

                    }
                }
            } catch (Exception e) {
                System.out.println("error in find specific region :" + e);
            }


            System.out.println("circle office Id:" + oid);

            /* end of find the specific office  region*/


            try {
                String options = request.getParameter("regions");
                System.out.println("Region selected:" + options);
                if (offlevel.equalsIgnoreCase("HO") ||
                    offlevel.equalsIgnoreCase("RN")) {

                    String sql =
                        "select  CIRCLE_OFFICE_ID,CIRCLE_OFFICE_NAME from COM_MST_CIRCLES_HVIEW where REGION_OFFICE_ID in (" +
                        options + ") order by circle_office_name";
                    System.out.println(sql);
                    Statement st = connection.createStatement();
                    result = st.executeQuery(sql);
                } else {
                    String sql =
                        "select  CIRCLE_OFFICE_ID,CIRCLE_OFFICE_NAME from COM_MST_CIRCLES_HVIEW where CIRCLE_OFFICE_ID =" +
                        oid + " order by circle_office_name";
                    System.out.println(sql);
                    Statement st = connection.createStatement();
                    result = st.executeQuery(sql);

                }
                int count = 0;
                html =
"<table cellpadding=0 cellspacing=0 border=0  width='100%'>";
                html =
html + "<tr ><td colspan='2'><a href='javascript:circleselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:circleclose()'>Close</a></td></tr>";
                boolean bool = false;
                while (result.next()) {
                    if (bool = !bool) {
                        html =
html + "<tr bgcolor=\"pink\" ><td ><input type='checkbox' name='chkcircle' value='" +
 result.getInt("CIRCLE_OFFICE_ID") + "'  ></td>";
                        html =
html + "<td >" + result.getString("CIRCLE_OFFICE_NAME") + "</td></tr>";
                    } else {
                        html =
html + "<tr ><td ><input type='checkbox' name='chkcircle' value='" +
 result.getInt("CIRCLE_OFFICE_ID") + "'></td>";
                        html =
html + "<td >" + result.getString("CIRCLE_OFFICE_NAME") + "</td></tr>";
                    }
                    count++;
                }
                html = html + "</table>";

                if (count == 0) {
                    html = "There is no Circle";
                }
                /*
        else {
            if(offlevel.equalsIgnoreCase("HO") || offlevel.equalsIgnoreCase("RN")){
                    html=html+"<tr ><td colspan='2'><a href='javascript:circleselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:circleclose()'>Close</a></td></tr>";
            }
            else {
                html=html+"<tr ><td colspan='2'><a href='javascript:circleclose()'>Close</a></td></tr>";
            }
            html=html+"</tbody></table>";

        }*/

                html = html + "</tbody></table>";

            } catch (Exception e) {
                System.out.println("Circle selection error " + e);
                html = "There is no Circle";

            }
        }

        else if (strCommand.equalsIgnoreCase("audit")) {

            System.out.println("audit");
            System.out.println("Command::" + strCommand);


            /*  to get the office level  */
            HttpSession session = request.getSession(false);
            UserProfile empProfile =
                (UserProfile)session.getAttribute("UserProfile");

            System.out.println("user id::" + empProfile.getEmployeeId());
            int empid = empProfile.getEmployeeId();
            int oid = 0;
            String deptid = "", offlevel = "";

            try {

                ps =
  connection.prepareStatement("select b.OFFICE_LEVEL_ID,a.office_id from hrm_emp_current_posting a " +
                              " inner join com_mst_offices b on b.office_id=a.office_id " +
                              " where a.employee_id=?");
                ps.setInt(1, empid);
                ResultSet results = ps.executeQuery();
                if (results.next()) {
                    offlevel = results.getString("OFFICE_LEVEL_ID");
                    oid = results.getInt("office_id");
                }
                results.close();
                ps.close();
                /* other office  */
                String profile = (String)session.getAttribute("profile");
                if (profile.equalsIgnoreCase("other")) {
                    offlevel = "HO";
                    ps =
  connection.prepareStatement("select office_id from com_mst_offices where  office_level_id=? ");
                    ps.setString(1, offlevel);
                    results = ps.executeQuery();
                    if (results.next()) {
                        oid = results.getInt("office_id");
                    }
                }
                /* other office  */
                System.out.println("office id:" + oid);
                System.out.println("dept id:" + deptid);

            } catch (Exception e) {
                System.out.println(e);
            }

            /*  to get the office level  */

            /* find the specific Office */


            try {
                if (offlevel.equalsIgnoreCase("DN")) {
                    PreparedStatement psd =
                        connection.prepareStatement("select CONTROLLING_OFFICE_ID from COM_OFFICE_CONTROL where OFFICE_ID=?");
                    psd.setInt(1, oid);
                    ResultSet rsd = psd.executeQuery();
                    if (rsd.next()) {
                        oid = rsd.getInt("CONTROLLING_OFFICE_ID");

                    }
                }
            } catch (Exception e) {
                System.out.println("error in find specific region :" + e);
            }


            System.out.println("audit office Id:" + oid);

            /* end of find the specific office  region*/


            try {
                String options = request.getParameter("regions");
                System.out.println("Region selected:" + options);
                if (offlevel.equalsIgnoreCase("HO") ||
                    offlevel.equalsIgnoreCase("RN")) {

                    String sql =
                        "select  AUDITWING_OFFICE_ID,AUDITWING_OFFICE_NAME from COM_MST_AUDIT_WING_HVIEW where REGION_OFFICE_ID in (" +
                        options + ")";
                    System.out.println(sql);
                    Statement st = connection.createStatement();
                    result = st.executeQuery(sql);
                } else {
                    String sql =
                        "select  AUDITWING_OFFICE_ID,AUDITWING_OFFICE_NAME from COM_MST_AUDIT_WING_HVIEW where AUDITWING_OFFICE_ID =" +
                        oid;
                    System.out.println(sql);
                    Statement st = connection.createStatement();
                    result = st.executeQuery(sql);

                }
                int count = 0;
                html =
"<table cellpadding=0 cellspacing=0 border=0  width='100%'>";
                html =
html + "<tr ><td colspan='2'><a href='javascript:auditselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:auditclose()'>Close</a></td></tr>";
                boolean bool = false;
                while (result.next()) {
                    if (bool = !bool) {
                        html =
html + "<tr bgcolor=\"pink\" ><td ><input type='checkbox' name='chkaudit' value='" +
 result.getInt("AUDITWING_OFFICE_ID") + "' ></td>";
                        html =
html + "<td >" + result.getString("AUDITWING_OFFICE_NAME") + "</td></tr>";
                    } else {
                        html =
html + "<tr ><td ><input type='checkbox' name='chkaudit' value='" +
 result.getInt("AUDITWING_OFFICE_ID") + "'></td>";
                        html =
html + "<td >" + result.getString("AUDITWING_OFFICE_NAME") + "</td></tr>";
                    }
                    count++;
                }
                html = html + "</table>";

                if (count == 0) {
                    html = "There is no audit";
                }
                /*
            else {
                if(offlevel.equalsIgnoreCase("HO") || offlevel.equalsIgnoreCase("RN")){
                        html=html+"<tr ><td colspan='2'><a href='javascript:circleselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:circleclose()'>Close</a></td></tr>";
                }
                else {
                    html=html+"<tr ><td colspan='2'><a href='javascript:circleclose()'>Close</a></td></tr>";
                }
                html=html+"</tbody></table>";

            }*/

                html = html + "</tbody></table>";

            } catch (Exception e) {
                System.out.println("audit selection error " + e);
                html = "There is no audit";

            }
        }

        else if (strCommand.equalsIgnoreCase("division")) {

            System.out.println("division");
            System.out.println("Command::" + strCommand);
            /*  to get the office level  */
            HttpSession session = request.getSession(false);
            UserProfile empProfile =
                (UserProfile)session.getAttribute("UserProfile");

            System.out.println("user id::" + empProfile.getEmployeeId());
            int empid = empProfile.getEmployeeId();
            int oid = 0;
            String deptid = "", offlevel = "";

            try {

                ps =
  connection.prepareStatement("select b.OFFICE_LEVEL_ID,a.office_id from hrm_emp_current_posting a " +
                              " inner join com_mst_offices b on b.office_id=a.office_id " +
                              " where a.employee_id=?");
                ps.setInt(1, empid);
                ResultSet results = ps.executeQuery();
                if (results.next()) {
                    offlevel = results.getString("OFFICE_LEVEL_ID");
                    oid = results.getInt("office_id");
                }
                results.close();
                ps.close();
                /* other office  */
                String profile = (String)session.getAttribute("profile");
                if (profile.equalsIgnoreCase("other")) {
                    offlevel = "HO";
                    ps =
  connection.prepareStatement("select office_id from com_mst_offices where  office_level_id=? ");
                    ps.setString(1, offlevel);
                    results = ps.executeQuery();
                    if (results.next()) {
                        oid = results.getInt("office_id");
                    }
                }
                /* other office  */
                System.out.println("office id:" + oid);
                System.out.println("dept id:" + deptid);

            } catch (Exception e) {
                System.out.println(e);
            }

            /*  to get the office level  */

            /* find the specific Office */


            System.out.println("division office Id:" + oid);

            /* end of find the specific office  region*/


            try {
                String options = request.getParameter("circles");
                String oftyp = request.getParameter("offtype");
                String reg = request.getParameter("regions");

                System.out.println("circle selected:" + options);
                System.out.println("office type selected..." + oftyp);
                System.out.println("region selected...." + reg);
                if (offlevel.equalsIgnoreCase("HO")) {
                    //   String   sql="select  DIVISION_OFFICE_ID,DIVISION_OFFICE_NAME from COM_MST_DIVISIONS_HVIEW where  CIRCLE_OFFICE_ID in ("+options+")";


                    String sql =
                        " select DIVISION_OFFICE_ID,DIVISION_OFFICE_NAME,primary_work_id from " +
                        " (" +
                        " select DIVISION_OFFICE_ID,DIVISION_OFFICE_NAME,circle_office_id,REGION_OFFICE_ID from COM_MST_DIVISIONS_HVIEW" +
                        " where REGION_OFFICE_ID in (" + reg +
                        ") and CIRCLE_OFFICE_ID in (" + options + ") " +
                        " ) a" + " left outer join" + " (" +
                        " select office_id ,primary_work_id from com_mst_offices" +
                        " ) b" + " on a.division_office_id=b.office_id" +
                        " where primary_work_id in (" + oftyp + ")";

                    System.out.println(sql);
                    /*
                  ps1=connection.prepareStatement(sql);
                  ps1.setString(1,options);
                  ps1.setString(2,oftyp);
                  result=ps1.executeQuery();
                  */

                    Statement st = connection.createStatement();
                    result = st.executeQuery(sql);

                } else if (offlevel.equalsIgnoreCase("RN")) {


                    String sql =
                        " select DIVISION_OFFICE_ID,DIVISION_OFFICE_NAME,primary_work_id from " +
                        " (" +
                        " select DIVISION_OFFICE_ID,DIVISION_OFFICE_NAME,circle_office_id,REGION_OFFICE_ID from COM_MST_DIVISIONS_HVIEW" +
                        " where REGION_OFFICE_ID in (" + oid +
                        ") and CIRCLE_OFFICE_ID in (" + options + ") " +
                        " ) a" + " left outer join" + " (" +
                        " select office_id ,primary_work_id from com_mst_offices" +
                        " ) b" + " on a.division_office_id=b.office_id" +
                        " where primary_work_id in (" + oftyp + ")";

                    System.out.println(sql);
                    /*
                ps1=connection.prepareStatement(sql);
                ps1.setString(1,options);
                ps1.setString(2,oftyp);
                result=ps1.executeQuery();
                */

                    Statement st = connection.createStatement();
                    result = st.executeQuery(sql);
                } else if (offlevel.equalsIgnoreCase("CL")) {
                    String sql =
                        " select DIVISION_OFFICE_ID,DIVISION_OFFICE_NAME,primary_work_id from " +
                        " (" +
                        " select DIVISION_OFFICE_ID,DIVISION_OFFICE_NAME,circle_office_id,REGION_OFFICE_ID from COM_MST_DIVISIONS_HVIEW" +
                        " where CIRCLE_OFFICE_ID in (" + options + ") " +
                        " ) a" + " left outer join" + " (" +
                        " select office_id ,primary_work_id from com_mst_offices" +
                        " ) b" + " on a.division_office_id=b.office_id" +
                        " where primary_work_id in (" + oftyp + ")";

                    System.out.println(sql);
                    /*
                ps1=connection.prepareStatement(sql);
                ps1.setString(1,options);
                ps1.setString(2,oftyp);
                result=ps1.executeQuery();
                */

                    Statement st = connection.createStatement();
                    result = st.executeQuery(sql);
                }


                else {
                    String sql =
                        "select  DIVISION_OFFICE_ID,DIVISION_OFFICE_NAME from COM_MST_DIVISIONS_HVIEW where  DIVISION_OFFICE_ID =" +
                        oid;
                    Statement st = connection.createStatement();
                    result = st.executeQuery(sql);
                }


                int count = 0;
                html =
"<table cellpadding=0 cellspacing=0 border=0  width='100%'>";
                html =
html + "<tr ><td colspan='2'><a href='javascript:divisionselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:divisionclose()'>Close</a></td></tr>";
                boolean bool = false;
                while (result.next()) {
                    if (bool = !bool) {
                        html =
html + "<tr bgcolor=\"pink\" ><td ><input type='checkbox' name='chkdivision' value='" +
 result.getInt("DIVISION_OFFICE_ID") + "' ></td>";
                        html =
html + "<td >" + result.getString("DIVISION_OFFICE_NAME") + "</td></tr>";
                    } else {
                        html =
html + "<tr ><td ><input type='checkbox' name='chkdivision' value='" +
 result.getInt("DIVISION_OFFICE_ID") + "' ></td>";
                        html =
html + "<td >" + result.getString("DIVISION_OFFICE_NAME") + "</td></tr>";
                    }
                    count++;
                }
                html = html + "</table>";

                if (count == 0) {
                    html = "There is no Division";
                }
                /*
            else {
                if(offlevel.equalsIgnoreCase("HO") || offlevel.equalsIgnoreCase("RN")  || offlevel.equalsIgnoreCase("CL")){
                html=html+"<tr ><td colspan='2'><a href='javascript:divisionselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:divisionclose()'>Close</a></td></tr>";
                }
                else{
                    html=html+"<tr ><td colspan='2'><a href='javascript:divisionclose()'>Close</a></td></tr>";
                }
                html=html+"</tbody></table>";

            }*/

                html = html + "</tbody></table>";

            } catch (Exception e) {
                System.out.println("Division selection error " + e);
                html = "There is no Division";

            }

        }

        else if (strCommand.equalsIgnoreCase("lab")) {

            System.out.println("lab");
            System.out.println("Command::" + strCommand);
            /*  to get the office level  */
            HttpSession session = request.getSession(false);
            UserProfile empProfile =
                (UserProfile)session.getAttribute("UserProfile");

            System.out.println("user id::" + empProfile.getEmployeeId());
            int empid = empProfile.getEmployeeId();
            System.out.println("The employee id is" + empid);
            int oid = 0;
            String deptid = "", offlevel = "";

            try {

                ps =
  connection.prepareStatement("select b.OFFICE_LEVEL_ID,a.office_id from hrm_emp_current_posting a " +
                              " inner join com_mst_offices b on b.office_id=a.office_id " +
                              " where a.employee_id=?");
                ps.setInt(1, empid);
                ResultSet results = ps.executeQuery();
                if (results.next()) {
                    offlevel = results.getString("OFFICE_LEVEL_ID");
                    oid = results.getInt("office_id");
                    System.out.println("The office id and the office level is" +
                                       oid + "," + offlevel);
                }
                results.close();
                ps.close();
                /* other office  */
                String profile = (String)session.getAttribute("profile");
                if (profile.equalsIgnoreCase("other")) {
                    offlevel = "HO";
                    ps =
  connection.prepareStatement("select office_id from com_mst_offices where  office_level_id=? ");
                    ps.setString(1, offlevel);
                    results = ps.executeQuery();
                    if (results.next()) {
                        oid = results.getInt("office_id");
                    }
                }
                /* other office  */
                System.out.println("office id:" + oid);
                System.out.println("dept id:" + deptid);

            } catch (Exception e) {
                System.out.println(e);
            }

            /*  to get the office level  */

            /* find the specific Office */


            System.out.println("lab office Id:" + oid);

            /* end of find the specific office  region*/


            try {
                String options = request.getParameter("circles");
                String reg = request.getParameter("regions");

                System.out.println("circle selected:" + options);
                System.out.println("region selected...." + reg);
                if (offlevel.equalsIgnoreCase("HO")) {
                    //   String   sql="select  DIVISION_OFFICE_ID,DIVISION_OFFICE_NAME from COM_MST_DIVISIONS_HVIEW where  CIRCLE_OFFICE_ID in ("+options+")";


                    String sql =
                        " SELECT LAB_OFFICE_ID,LAB_OFFICE_NAME,REGION_OFFICE_ID,CIRCLE_OFFICE_ID FROM COM_MST_LABS_HVIEW WHERE REGION_OFFICE_ID IN (" +
                        reg + ") AND CIRCLE_OFFICE_ID IN (" + options + ")";

                    System.out.println(sql);
                    /*
                      ps1=connection.prepareStatement(sql);
                      ps1.setString(1,options);
                      ps1.setString(2,oftyp);
                      result=ps1.executeQuery();
                      */

                    Statement st = connection.createStatement();
                    result = st.executeQuery(sql);

                } else if (offlevel.equalsIgnoreCase("RN")) {


                    String sql =
                        " SELECT LAB_OFFICE_ID,LAB_OFFICE_NAME,REGION_OFFICE_ID,CIRCLE_OFFICE_ID FROM COM_MST_LABS_HVIEW WHERE REGION_OFFICE_ID IN (" +
                        reg + ") AND CIRCLE_OFFICE_ID IN (" + options + ")";

                    System.out.println(sql);
                    /*
                    ps1=connection.prepareStatement(sql);
                    ps1.setString(1,options);
                    ps1.setString(2,oftyp);
                    result=ps1.executeQuery();
                    */

                    Statement st = connection.createStatement();
                    result = st.executeQuery(sql);
                } else if (offlevel.equalsIgnoreCase("CL")) {
                    String sql =
                        " SELECT LAB_OFFICE_ID,LAB_OFFICE_NAME,REGION_OFFICE_ID,CIRCLE_OFFICE_ID FROM COM_MST_LABS_HVIEW WHERE REGION_OFFICE_ID IN (" +
                        reg + ") AND CIRCLE_OFFICE_ID IN (" + options + ")";

                    System.out.println(sql);
                    /*
                    ps1=connection.prepareStatement(sql);
                    ps1.setString(1,options);
                    ps1.setString(2,oftyp);
                    result=ps1.executeQuery();
                    */

                    Statement st = connection.createStatement();
                    result = st.executeQuery(sql);
                }


                else {
                    String sql =
                        "select  LAB_OFFICE_ID,LAB_OFFICE_NAME from COM_MST_LABS_HVIEW where  LAB_OFFICE_ID =" +
                        oid;
                    Statement st = connection.createStatement();
                    result = st.executeQuery(sql);
                }


                int count = 0;
                html =
"<table cellpadding=0 cellspacing=0 border=0  width='100%'>";
                html =
html + "<tr ><td colspan='2'><a href='javascript:labselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:labclose()'>Close</a></td></tr>";
                boolean bool = false;
                while (result.next()) {
                    if (bool = !bool) {
                        html =
html + "<tr bgcolor=\"pink\" ><td ><input type='checkbox' name='chklab' value='" +
 result.getInt("LAB_OFFICE_ID") + "' ></td>";
                        html =
html + "<td >" + result.getString("LAB_OFFICE_NAME") + "</td></tr>";
                    } else {
                        html =
html + "<tr ><td ><input type='checkbox' name='chklab' value='" +
 result.getInt("LAB_OFFICE_ID") + "' ></td>";
                        html =
html + "<td >" + result.getString("LAB_OFFICE_NAME") + "</td></tr>";
                    }
                    count++;
                }
                html = html + "</table>";

                if (count == 0) {
                    html = "There is no Labs";
                }
                /*
                else {
                    if(offlevel.equalsIgnoreCase("HO") || offlevel.equalsIgnoreCase("RN")  || offlevel.equalsIgnoreCase("CL")){
                    html=html+"<tr ><td colspan='2'><a href='javascript:divisionselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:divisionclose()'>Close</a></td></tr>";
                    }
                    else{
                        html=html+"<tr ><td colspan='2'><a href='javascript:divisionclose()'>Close</a></td></tr>";
                    }
                    html=html+"</tbody></table>";

                }*/

                html = html + "</tbody></table>";

            } catch (Exception e) {
                System.out.println("Labs selection error " + e);
                html = "There is no Labs";

            }

        }

        else if (strCommand.equalsIgnoreCase("offtype")) {


            System.out.println("office type.......");
            System.out.println("Command::" + strCommand);
            try {
                //String sql="select  REGION_OFFICE_ID,REGION_OFFICE_NAME from COM_MST_REGIONS_HVIEW  ";
                System.out.println("before select statement");
                /*
            if(strCommand.equalsIgnoreCase("offtype"))
            {*/
                System.out.println("inside offtype");
                String sql = "select * from COM_MST_WORK_NATURE";
                System.out.println(sql);
                ps1 = connection.prepareStatement(sql);
                //s=connection.createStatement();
                result1 = ps1.executeQuery(sql);
                System.out.println(result1);


                // }
                /*
            else
            {
              String sql="select  REGION_OFFICE_ID,REGION_OFFICE_NAME from COM_MST_REGIONS_HVIEW where REGION_OFFICE_ID=? ";
              ps=connection.prepareStatement(sql );
              ps.setInt(1,oid);
              result=ps.executeQuery();
            }
            */
                int count = 0;
                System.out.println(count);
                html =
"<table cellpadding=0 cellspacing=0 border=0 width='100%'>";
                html =
html + "<tr ><td colspan='2'><a href='javascript:offtypeselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:offtypeclose()'>Close</a></td></tr>";
                System.out.println(html);

                boolean bool = false;
                System.out.println(bool);
                System.out.println(result1);
                while (result1.next()) {

                    if (bool = !bool) {

                        html =
html + "<tr bgcolor=\"pink\"><td><input type='checkbox' name='chkofftype' value='" +
 result1.getString("WORK_NATURE_ID") + "' onclick='oftypeonchange()' ></td>";
                        html =
html + "<td>" + result1.getString("WORK_NATURE_DESC") + "</td></tr>";
                        System.out.println(html);

                    }

                    else {
                        System.out.println("inside else part");
                        html =
html + "<tr ><td><input type='checkbox' name='chkofftype' value='" +
 result1.getString("WORK_NATURE_ID") + "' onclick='oftypeonchange()'></td>";
                        html =
html + "<td>" + result1.getString("WORK_NATURE_DESC") + "</td></tr>";
                    }
                    count++;
                }
                // html=html+"</table>";

                if (count == 0) {
                    html = "There is no Office Type";
                }
                /*
            else {
            if(strCommand.equalsIgnoreCase("offtype"))
            {
            html=html+"<tr ><td colspan='2'><a href='javascript:offtypeselectAll()'>Select All</a>&nbsp;&nbsp;&nbsp;<a href='javascript:offtypeclose()'>Close</a></td></tr>";
            }
            else {
                html=html+"<tr ><td colspan='2'><a href='javascript:offtypeclose()'>Close</a></td></tr>";
            }
            html=html+"</tbody></table>";

            }*/

                html = html + "</tbody></table>";
            }


            catch (Exception e) {
                System.out.println("Office Type selection error " +
                                   e.getMessage());
                html = "There is no Office Type";

            }

        }


        System.out.println("Html:" + html);
        out.println(html);


    }


}
