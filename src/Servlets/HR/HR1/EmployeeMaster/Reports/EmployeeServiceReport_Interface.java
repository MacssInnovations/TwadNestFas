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

public class EmployeeServiceReport_Interface extends HttpServlet {
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

            ConnectionString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();

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
        String rank = "";
        String order = "";
        String order_desc = "", order_name = "";
        Map map = null;
        try {

            System.out.println("inside employee service details report");

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
            System.out.println("Office Selected:" + officeselected);

            oflevel = request.getParameter("rad_off");
            System.out.println("office level new..." + oflevel);


            hier = request.getParameter("allhier");
            System.out.println("include off hier:" + hier);

            rank = request.getParameter("rank");
            System.out.println("rank..." + rank);

            order = request.getParameter("order");
            System.out.println("order..." + order);

            order_desc = request.getParameter("order_desc");
            System.out.println("order_desc" + order_desc);


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


            if (oflevel.equalsIgnoreCase("HO")) { //Head Office


                if (hier != null) {
                    System.out.println("report 2.2");

                    try {
                        ps =
  connection.prepareStatement("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW order by off_order");
                        ResultSet results = null;
                        results = ps.executeQuery();
                        int i = 0;
                        while (results.next()) {
                            offids += results.getInt("office_id") + ",";
                            i++;
                        }
                        // System.out.println(offids);
                        if (i != 0) {
                            offids = offids.substring(0, offids.length() - 1);
                            System.out.println("office id in ho..." + offids);
                        }

                    } catch (Exception e) {
                        System.out.println("error in all:" + e);
                    }


                }

                else //if(hier==null)
                {
                    System.out.println("report 2.1");
                    offids = String.valueOf(oid);
                    System.out.println("inside ho report 2.1..." + offids);
                }

            }

            //start for region
            else if (oflevel.equalsIgnoreCase("RG")) //Regin  Office
            {

                System.out.println("inside region");
                System.out.println("office level..." + officelevel);
                hier = request.getParameter("allhier");
                System.out.println("include off hier-1..." + hier);


                //if(hier.equalsIgnoreCase("include"))  // Region All
                if (hier != null) {
                    System.out.println("report 3.1");
                    if (officelevel.equalsIgnoreCase("HO")) {
                        try {
                            ps =
  connection.prepareStatement("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('RN','CL','DN','SD') and region_office_id in (" +
                              officeselected + ") order by off_order");
                            ResultSet results = null;
                            results = ps.executeQuery();
                            int i = 0;
                            oid = 0;
                            System.out.println("office id:" + offids);
                            while (results.next()) {
                                offids += results.getInt("office_id") + ",";
                                //offids=results.getInt("office_id")+",";
                                i++;
                            }
                            System.out.println("outside i" + offids);
                            if (i != 0) {
                                //offids+=oid;
                                offids =
                                        offids.substring(0, offids.length() - 1);
                                System.out.println("in i..." + offids);
                                //offids=oid;
                            } else {
                                offids = String.valueOf(oid);
                            }


                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }

                    }

                    else if (officelevel.equalsIgnoreCase("RN")) {
                        //offids=String.valueOf(oid);
                        try {
                            ps =
  connection.prepareStatement("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('RN','CL','DN','SD') and region_office_id in (" +
                              officeselected + ") order by off_order");
                            ResultSet results = null;
                            results = ps.executeQuery();
                            int i = 0;
                            oid = 0;
                            System.out.println("office id:" + offids);
                            while (results.next()) {
                                offids += results.getInt("office_id") + ",";
                                //offids=results.getInt("office_id")+",";
                                i++;
                            }
                            System.out.println("outside i" + offids);
                            if (i != 0) {
                                //offids+=oid;
                                offids =
                                        offids.substring(0, offids.length() - 1);
                                System.out.println("in i..." + offids);
                                //offids=oid;
                            } else {
                                offids = String.valueOf(oid);
                            }


                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }
                    }

                }

                else if (hier == null) // Region Specific
                {

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

                    }

                    else if (officelevel.equalsIgnoreCase("RN")) {
                        offids = String.valueOf(oid);
                    }


                }

            }
            //end for region

            //start for circle
            else if (oflevel.equalsIgnoreCase("CR")) { //Circle  Office
                //if(hier.equalsIgnoreCase("include"))  // Circle All
                if (hier != null) {
                    System.out.println("report 5.1");
                    String options = request.getParameter("regions");
                    System.out.println("Region selected:" + options);


                    if (officelevel.equalsIgnoreCase("HO")) {
                        try {
                            ps =
  connection.prepareStatement("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('CL','DN','SD') and circle_office_id in (" +
                              officeselected + ") order by off_order");
                            ResultSet results = null;
                            results = ps.executeQuery();
                            int i = 0;
                            oid = 0;
                            while (results.next()) {
                                offids += results.getInt("office_id") + ",";
                                i++;
                            }
                            System.out.println("offids..." + offids);
                            if (i != 0) {

                                offids =
                                        offids.substring(0, offids.length() - 1);
                                System.out.println("in i circle..." + offids);

                                /*
		                                            Statement st=connection.createStatement();
		                                            ResultSet rs=null;
		                                            String tempoffids=offids.substring(0,offids.length()-1);
		                                            rs=st.executeQuery("select distinct REGION_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID in ("+tempoffids+")");
		                                            int j=0;
		                                            while(rs.next())
		                                            {
			                                               offids+=rs.getInt("REGION_OFFICE_ID")+",";
			                                               j++;
		                                            }
		                                            offids+=oid;
		
		                                           */

                            } else {
                                offids = String.valueOf(oid);
                            }


                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }

                    } else if (officelevel.equalsIgnoreCase("RN")) {
                        System.out.println("inside region");
                        officeselected =
                                request.getParameter("officeselected");
                        System.out.println("officeselected..." +
                                           officeselected);
                        try {
                            ps =
  connection.prepareStatement("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('CL','DN','SD') and circle_office_id in (" +
                              officeselected + ") order by off_order");
                            //ps.setInt(1,oid);
                            ResultSet results = null;
                            results = ps.executeQuery();
                            int i = 0;
                            oid = 0;
                            while (results.next()) {
                                offids += results.getInt("office_id") + ",";
                                i++;
                            }
                            // System.out.println(offids);
                            if (i != 0) {
                                offids += oid;
                            } else {
                                offids = String.valueOf(oid);
                            }

                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }
                    } else if (officelevel.equalsIgnoreCase("CL")) {
                        //offids=String.valueOf(oid);
                        try {
                            ps =
  connection.prepareStatement("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('CL','DN','SD') and circle_office_id in (" +
                              officeselected + ") order by off_order");
                            //ps.setInt(1,oid);
                            ResultSet results = null;
                            results = ps.executeQuery();
                            int i = 0;
                            oid = 0;
                            while (results.next()) {
                                offids += results.getInt("office_id") + ",";
                                i++;
                            }
                            // System.out.println(offids);
                            if (i != 0) {
                                offids += oid;
                            } else {
                                offids = String.valueOf(oid);
                            }

                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }
                    }

                } else if (hier == null) // Circle Specific
                {
                    System.out.println("report 6.1");
                    if (officelevel.equalsIgnoreCase("HO")) {
                        try {

                            Statement st = connection.createStatement();
                            ResultSet rs = null;
                            rs =
  st.executeQuery("select distinct CIRCLE_OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_ID in (" +
                  officeselected + ")");
                            int j = 0;
                            while (rs.next()) {
                                offids += rs.getInt("CIRCLE_OFFICE_ID") + ",";
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


            else if (oflevel.equalsIgnoreCase("DV")) {

                if (hier != null) {
                    System.out.println("report 7.1");

                    System.out.println("aggre  yes All");

                    if (officelevel.equalsIgnoreCase("HO")) {
                        System.out.println("ho");
                        try {
                            ps =
  connection.prepareStatement("select distinct OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID='DN' and OFFICE_ID in (" +
                              officeselected + ")");
                            ResultSet results = null;
                            results = ps.executeQuery();
                            int i = 0;
                            while (results.next()) {
                                offids += results.getInt("office_id") + ",";
                                i++;
                            }

                            if (i != 0) {


                                Statement st = connection.createStatement();
                                ResultSet rs = null;
                                String tempoffids =
                                    offids.substring(0, offids.length() - 1);

                                rs =
  st.executeQuery("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where DIVISION_OFFICE_ID in (" +
                  tempoffids +
                  ") and OFFICE_LEVEL_ID='SD' order by off_order");
                                while (rs.next()) {
                                    offids += rs.getInt("OFFICE_ID") + ",";

                                }


                                offids =
                                        offids.substring(0, offids.length() - 1);
                                System.out.println("in subdivision..." +
                                                   offids);

                            } else {
                                offids = String.valueOf(oid);
                            }


                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }

                    } else if (officelevel.equalsIgnoreCase("RN")) {
                        System.out.println("rn");
                        try {

                            ps =
  connection.prepareStatement("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('DN','SD') and  DIVISION_OFFICE_ID in (" +
                              officeselected + ") order by off_order");
                            ResultSet results = null;
                            results = ps.executeQuery();
                            int i = 0;
                            while (results.next()) {
                                offids += results.getInt("office_id") + ",";
                                i++;
                            }
                            if (i != 0) {
                                offids =
                                        offids.substring(0, offids.length() - 1);
                                System.out.println("in i division1...." +
                                                   offids);

                            } else {
                                offids = String.valueOf(oid);
                            }

                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }
                    } else if (officelevel.equalsIgnoreCase("CL")) {
                        System.out.println("cl");
                        try {

                            ps =
  connection.prepareStatement("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW where OFFICE_LEVEL_ID in ('DN','SD') and  DIVISION_OFFICE_ID in (" +
                              officeselected + ") order by off_order");
                            ResultSet results = null;
                            results = ps.executeQuery();
                            int i = 0;
                            while (results.next()) {
                                offids += results.getInt("office_id") + ",";
                                i++;
                            }
                            if (i != 0) {

                                offids =
                                        offids.substring(0, offids.length() - 1);
                                System.out.println("in circle division...." +
                                                   offids);
                            } else {
                                offids = String.valueOf(oid);
                            }
                        } catch (Exception e) {
                            System.out.println(e);
                        }
                    } else if (officelevel.equalsIgnoreCase("DN")) {
                        System.out.println("dn");

                        ps =
  connection.prepareStatement("select distinct OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where DIVISION_OFFICE_ID =? and OFFICE_LEVEL_ID='SD'");
                        ps.setInt(1, oid);
                        ResultSet rs = ps.executeQuery();
                        int i = 0;
                        while (rs.next()) {
                            offids += rs.getInt("OFFICE_ID") + ",";
                            i++;

                        }
                        if (i != 0) {
                            offids += oid;
                        } else {
                            offids = String.valueOf(oid);
                        }
                    }


                } else if (hier == null) {
                    System.out.println("report is 8.1");
                    if (officelevel.equalsIgnoreCase("HO")) {
                        try {

                            Statement st = connection.createStatement();
                            ResultSet rs = null;
                            rs =
  st.executeQuery("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW " +
                  " where" + " division_OFFICE_ID in (" + officeselected +
                  ")" + " and OFFICE_LEVEL_ID in ('DN') order by off_order");
                            int i = 0;
                            while (rs.next()) {
                                offids += rs.getInt("OFFICE_ID") + ",";
                                i++;
                            }
                            if (i != 0) {

                                offids =
                                        offids.substring(0, offids.length() - 1);
                                System.out.println("offids in divi..." +
                                                   offids);


                            } else {

                                st = connection.createStatement();
                                rs =
  st.executeQuery("select distinct OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where DIVISION_OFFICE_ID in (" +
                  officeselected + ") and OFFICE_LEVEL_ID='SD'");
                                while (rs.next()) {
                                    offids += rs.getInt("OFFICE_ID") + ",";

                                }
                                // offids+=officeselected+","+oid;  from this it is real
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
  st.executeQuery("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW " +
                  " where" + " division_OFFICE_ID in (" + officeselected +
                  ")" + " and OFFICE_LEVEL_ID in ('DN') order by off_order");
                            int i = 0;

                            while (rs.next()) {
                                offids += rs.getInt("OFFICE_ID") + ",";
                                i++;
                            }

                            if (i != 0) {

                                offids =
                                        offids.substring(0, offids.length() - 1);
                                System.out.println("offids in divi..." +
                                                   offids);
                            }

                            else {

                                st = connection.createStatement();
                                rs =
  st.executeQuery("select distinct OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where DIVISION_OFFICE_ID in (" +
                  officeselected + ") and OFFICE_LEVEL_ID='SD'");
                                while (rs.next()) {
                                    offids += rs.getInt("OFFICE_ID") + ",";

                                }
                                // offids+=officeselected+","+oid;  from this it is real
                                offids += officeselected;
                            }


                        } catch (Exception e) {
                            System.out.println("error in all:" + e);
                        }
                    } else if (officelevel.equalsIgnoreCase("CL")) {


                        Statement st = connection.createStatement();
                        ResultSet rs = null;

                        rs =
  st.executeQuery("select distinct OFFICE_ID,off_order from COM_MST_ALL_OFFICES_VIEW " +
                  " where" + " division_OFFICE_ID in (" + officeselected +
                  ")" + " and OFFICE_LEVEL_ID in ('DN') order by off_order");
                        int i = 0;

                        while (rs.next()) {
                            offids += rs.getInt("OFFICE_ID") + ",";
                            i++;
                        }

                        if (i != 0) {

                            offids = offids.substring(0, offids.length() - 1);
                            System.out.println("offids in divi..." + offids);
                        }

                        else {

                            st = connection.createStatement();
                            rs =
  st.executeQuery("select distinct OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where DIVISION_OFFICE_ID in (" +
                  officeselected + ") and OFFICE_LEVEL_ID='SD'");
                            while (rs.next()) {
                                offids += rs.getInt("OFFICE_ID") + ",";

                            }
                            // offids+=officeselected+","+oid;  from this it is real
                            offids += officeselected;
                        }


                    } else if (officelevel.equalsIgnoreCase("DN")) {

                        ps =
  connection.prepareStatement("select distinct OFFICE_ID from COM_MST_ALL_OFFICES_VIEW where DIVISION_OFFICE_ID =? and OFFICE_LEVEL_ID='SD'");
                        ps.setInt(1, oid);
                        ResultSet rs = ps.executeQuery();
                        int i = 0;
                        while (rs.next()) {
                            offids += rs.getInt("OFFICE_ID") + ",";
                            i++;

                        }
                        if (i != 0) {
                            offids += oid;
                        } else {
                            offids = String.valueOf(oid);
                        }
                    }


                }


            }


            String emp_id = "";

            try {
                ResultSet rs = null;
                Statement st = connection.createStatement();
                String sql =
                    "select employee_id from hrm_emp_current_posting where employee_status_id='WKG' ";
                if (rank.equals("AE"))
                    sql = sql + " and designation_id in(13,14,15)";
                else if (rank.equals("AEE"))
                    sql = sql + " and designation_id in(10,12,11)";
                else
                    sql = sql + " and designation_id in(7,8,9)";
                rs = st.executeQuery(sql);
                int i = 0;
                while (rs.next()) {
                    emp_id += rs.getInt("employee_id") + ",";
                    i++;
                }
                if (i != 0) {

                    emp_id = emp_id.substring(0, emp_id.length() - 1);
                    System.out.println("Employee Id..." + emp_id);
                }
            } catch (Exception e) {
                System.out.println("Err in rank selection:" + e.getMessage());
            }


            try {
                System.out.println(" Inside try");
                String optbase = request.getParameter("optselect");
                System.out.println("Option Selected:" + optbase);
                String optbase1 = request.getParameter("optselectgrp");
                System.out.println("Option Selected:" + optbase1);
                map = new HashMap();

                System.out.println("Office Selected:" + officeselected);
                System.out.println(" outside condition");
                reportFile =
                        new File(getServletContext().getRealPath("/WEB-INF/ReportSrc/EmployeeServiceReport_new.jasper"));
                System.out.println(" reportFile path:::" + reportFile);
                if (!reportFile.exists()) {
                    System.out.println("does not exsist");
                    throw new JRRuntimeException("File J not found. The report design must be compiled first.");
                }
                JasperReport jasperReport =
                    (JasperReport)JRLoader.loadObject(reportFile.getPath());

                if (hier != null) {
                    System.out.println("check");
                    System.out.println("offids" + offids);
                    map.put("offids", offids);

                } else if (hier == null) {

                    if (oflevel.equalsIgnoreCase("ho")) {
                        map.put("offids", offids);

                    }

                    else {
                        System.out.println("uncheck");
                        map.put("offids", officeselected);

                    }
                } else {
                    System.out.println("othter");
                    System.out.println("office ids:" + offids);
                    System.out.println("desig ids:" + newdesigval);
                    map.put("offids", offids);
                }
                System.out.println(" emp_id:::" + emp_id);
                map.put("emp_id", emp_id);
                System.out.println(" order:::" + order);
                if (order.equals("office"))
                    order_name =
                            " order by off_order  " + order_desc + ",rws_yr " +
                            order_desc + ",rws_months " + order_desc;
                else {
                    order = request.getParameter("service");
                    if (order.equals("RWS"))
                        order_name =
                                " order by rws_yr " + order_desc + ",rws_months  " +
                                order_desc;
                    else if (order.equals("Urban"))
                        order_name =
                                " order by urb_yr " + order_desc + ",urb_months  " +
                                order_desc;
                    else if (order.equals("Sewerage"))
                        order_name =
                                " order by sew_yr " + order_desc + ",sew_months  " +
                                order_desc;
                    else if (order.equals("Maintenance"))
                        order_name =
                                " order by main_yr " + order_desc + ",main_months  " +
                                order_desc;
                    else if (order.equals("PF"))
                        order_name =
                                " order by pf_yr " + order_desc + ",pf_months  " +
                                order_desc;
                    else if (order.equals("Office"))
                        order_name =
                                " order by off_yr " + order_desc + ",off_months  " +
                                order_desc;
                    else
                        order_name =
                                " order by dpn_yr " + order_desc + ",dpn_months  " +
                                order_desc;

                }


                map.put("order_type", order_name);
                map.put("sel_design", rank);
                System.out.println(" order_type:::" + order_name);


                JasperPrint jasperPrint =
                    JasperFillManager.fillReport(jasperReport, map,
                                                 connection);


                String rtype = request.getParameter("outputtype");
                if (rtype.equalsIgnoreCase("HTML")) {
                    response.setContentType("text/html");
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Newemployeedetail_office.html\"");
                    PrintWriter out = response.getWriter();
                    JRHtmlExporter exporter = new JRHtmlExporter();
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

                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Newemployeedetail_office.pdf\"");
                    OutputStream out = response.getOutputStream();
                    out.write(buf, 0, buf.length);
                    out.close();
                } else if (rtype.equalsIgnoreCase("EXCEL")) {

                    response.setContentType("application/vnd.ms-excel");
                    response.setHeader("Content-Disposition",
                                       "attachment;filename=\"Newemployeedetail_office.xls\"");
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
                                       "attachment;filename=\"Newemployeedetail_office.txt\"");

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

            ConnectionString =
                    strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() +
                    ":" + strsid.trim();

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

        } catch (Exception e) {
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
 result.getInt("REGION_OFFICE_ID") + "' ></td>";
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
                        options + ")";
                    System.out.println(sql);
                    Statement st = connection.createStatement();
                    result = st.executeQuery(sql);
                } else {
                    String sql =
                        "select  CIRCLE_OFFICE_ID,CIRCLE_OFFICE_NAME from COM_MST_CIRCLES_HVIEW where CIRCLE_OFFICE_ID =" +
                        oid;
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
 result.getInt("CIRCLE_OFFICE_ID") + "' ></td>";
                        html =
html + "<td >" + result.getString("CIRCLE_OFFICE_NAME") + "</td></tr>";
                    }
                    count++;
                }
                html = html + "</table>";

                if (count == 0) {
                    html = "There is no Circle";
                }

                html = html + "</tbody></table>";

            } catch (Exception e) {
                System.out.println("Circle selection error " + e);
                html = "There is no Circle";

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
                if (offlevel.equalsIgnoreCase("HO")) {

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

                html = html + "</tbody></table>";

            } catch (Exception e) {
                System.out.println("Division selection error " + e);
                html = "There is no Division";

            }

        }

        else if (strCommand.equalsIgnoreCase("offtype")) {

            System.out.println("office type.......");
            System.out.println("Command::" + strCommand);
            try {

                System.out.println("before select statement");
                System.out.println("inside offtype");
                String sql = "select * from COM_MST_WORK_NATURE";
                System.out.println(sql);
                ps1 = connection.prepareStatement(sql);
                result1 = ps1.executeQuery(sql);
                System.out.println(result1);


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


                if (count == 0) {
                    html = "There is no Office Type";
                }

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
