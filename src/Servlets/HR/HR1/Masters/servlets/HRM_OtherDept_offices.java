package Servlets.HR.HR1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class HRM_OtherDept_offices extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/xml; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection con = null;
        ResultSet rs = null;
        CallableStatement cs = null;
        PreparedStatement ps = null;
        String xml = "";
        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
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
            System.out.println("Exception in connection..." + e);
        }
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String strCommand = "";
        String dept_id = "";
        int deptOff_id = 0, pin = 0;
        String deptOff_Name = "", deptOff_SName = "", addr1 = "", addr2 = "";
        String city = "", phone = "", phone1 = "", add_phone = "", fax =
            "", email = "", HoOffice = "";

        try {

            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);
            dept_id = request.getParameter("cmbOtherDept_Id");
            System.out.println("deptid" + dept_id + "UU");
            System.out.println("assign.....");
            deptOff_id =
                    0; //Integer.parseInt(request.getParameter("txtOtherDeptOff_Id"));
            deptOff_Name = request.getParameter("txtOtherDeptOff_Name");
            deptOff_SName = request.getParameter("txtOtherDeptOff_SName");
            addr1 = request.getParameter("txtAddress1");
            addr2 = request.getParameter("txtAddress2");
            city = request.getParameter("txtCity");
            try {
                pin = Integer.parseInt(request.getParameter("txtPincode"));
            } catch (Exception e) {
            }
            phone = request.getParameter("txtStd");
            phone1 = request.getParameter("txtPhone1");
            add_phone = request.getParameter("txtAPhone");
            fax = request.getParameter("txtFax");
            email = request.getParameter("txtEmail");
            HoOffice = request.getParameter("txtHeadOfDept");
            System.out.println("assign..ended...");
        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
        if (strCommand.equalsIgnoreCase("Add")) {
            xml = "<response><command>Add</command>";
            int id = 0;

            try {
                cs =
  con.prepareCall("{call HRM_OTHERDEPOFF_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                cs.setString(1, dept_id);
                cs.setInt(2, deptOff_id);
                cs.setString(3, deptOff_Name);
                cs.setString(4, deptOff_SName);
                cs.setString(5, addr1);
                cs.setString(6, addr2);
                cs.setString(7, city);
                cs.setInt(8, pin);
                cs.setString(9, phone);
                cs.setString(10, phone1);
                cs.setString(11, add_phone);
                cs.setString(12, fax);
                cs.setString(13, email);
                cs.setString(14, HoOffice);
                cs.setString(16, "insert");
                cs.registerOutParameter(2, java.sql.Types.NUMERIC);
                cs.registerOutParameter(15, java.sql.Types.NUMERIC);
                cs.execute();

                int errcode = cs.getInt(15);
                id = cs.getInt(2);
                System.out.println(id);
                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    xml = xml + "<flag>failure</flag>";
                } else
                    xml = xml + "<num>" + id + "</num><flag>success</flag>";
            } catch (Exception e) {
                System.out.println("insert exception  :" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        } else if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";
            deptOff_id =
                    Integer.parseInt(request.getParameter("txtOtherDeptOff_Id"));
            try {
                ps =
  con.prepareStatement("delete from HRM_MST_OTHER_DEPT_OFFICES where OTHER_DEPT_ID=? and OTHER_DEPT_OFFICE_ID=?");
                ps.setString(1, dept_id);
                ps.setInt(2, deptOff_id);
                String del_rowid = dept_id + "-" + deptOff_id;
                ps.executeUpdate();
                xml = xml + "<flag>success</flag><scd>" + del_rowid + "</scd>";
            } catch (Exception e) {
                System.out.println("catch...." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        } else if (strCommand.equalsIgnoreCase("loaddept")) {
            System.out.println("inside command....");
            xml = "<response><command>loaddept</command>";
            //deptOff_id=Integer.parseInt(request.getParameter("txtOtherDeptOff_Id"));
            try {
                System.out.println("inside try....");
                if (!dept_id.equalsIgnoreCase("")) {
                    System.out.println("deptid is not empty");
                    ps =
  con.prepareStatement("select off.OTHER_DEPT_ID,dept.OTHER_DEPT_NAME,off.OTHER_DEPT_OFFICE_ID," +
                       "off.OTHER_DEPT_OFFICE_NAME,off.OTHER_DEPT_OFFICE_SHORT_NAME,off.ADDRESS1, off.ADDRESS2,off.CITY_TOWN," +
                       "off.PINCODE,off.STD_CODE, off.PHONE_NO,off.ADDL_PHONE_NOS,off.FAX_NO,off.E_MAIL,off.HEAD_OF_OFFICE " +
                       "from HRM_MST_OTHER_DEPTS dept,HRM_MST_OTHER_DEPT_OFFICES off where off.OTHER_DEPT_ID=dept.OTHER_DEPT_ID and dept.OTHER_DEPT_ID=? ORDER BY dept.OTHER_DEPT_NAME,off.OTHER_DEPT_OFFICE_ID");
                    ps.setString(1, dept_id);
                } else if (dept_id.equalsIgnoreCase("")) {
                    System.out.println("deptid is empty");
                    ps =
  con.prepareStatement("select off.OTHER_DEPT_ID,dept.OTHER_DEPT_NAME,off.OTHER_DEPT_OFFICE_ID," +
                       "off.OTHER_DEPT_OFFICE_NAME,off.OTHER_DEPT_OFFICE_SHORT_NAME,off.ADDRESS1, off.ADDRESS2,off.CITY_TOWN," +
                       "off.PINCODE,off.STD_CODE, off.PHONE_NO,off.ADDL_PHONE_NOS,off.FAX_NO,off.E_MAIL,off.HEAD_OF_OFFICE " +
                       "from HRM_MST_OTHER_DEPTS dept,HRM_MST_OTHER_DEPT_OFFICES off where off.OTHER_DEPT_ID=dept.OTHER_DEPT_ID ORDER BY dept.OTHER_DEPT_NAME,off.OTHER_DEPT_OFFICE_ID");
                }
                rs = ps.executeQuery();
                xml = xml + "<flag>success</flag>";
                while (rs.next()) {
                    xml =
 xml + "<depid>" + rs.getString("OTHER_DEPT_ID") + "</depid>";
                    xml =
 xml + "<depname>" + rs.getString("OTHER_DEPT_NAME") + "</depname>";
                    xml =
 xml + "<offid>" + (rs.getInt("OTHER_DEPT_OFFICE_ID") == 0 ? "Not Available" :
                    rs.getString("OTHER_DEPT_OFFICE_ID")) + "</offid>";
                    xml =
 xml + "<offname>" + (rs.getString("OTHER_DEPT_OFFICE_NAME") == null ?
                      "Not Available" :
                      rs.getString("OTHER_DEPT_OFFICE_NAME")) + "</offname>";
                    xml =
 xml + "<offsname>" + (rs.getString("OTHER_DEPT_OFFICE_SHORT_NAME") == null ?
                       "Not Available" :
                       rs.getString("OTHER_DEPT_OFFICE_SHORT_NAME")) +
   "</offsname>";
                    xml =
 xml + "<add1>" + (rs.getString("ADDRESS1") == null ? "Not Available" :
                   rs.getString("ADDRESS1")) + "</add1>";
                    xml =
 xml + "<add2>" + (rs.getString("ADDRESS2") == null ? "Not Available" :
                   rs.getString("ADDRESS2")) + "</add2><";
                    xml =
 xml + "town>" + (rs.getString("CITY_TOWN") == null ? "Not Available" :
                  rs.getString("CITY_TOWN")) + "</town>";
                    xml =
 xml + "<pinc>" + (rs.getInt("PINCODE") == 0 ? "Not Available" :
                   rs.getString("PINCODE")) + "</pinc>";
                    xml =
 xml + "<stdc>" + (rs.getString("STD_CODE") == null ? "Not Available" :
                   rs.getString("STD_CODE")) + "</stdc>";
                    xml =
 xml + "<pho>" + (rs.getString("PHONE_NO") == null ? "Not Available" :
                  rs.getString("PHONE_NO")) + "</pho>";
                    xml =
 xml + "<apho>" + (rs.getString("ADDL_PHONE_NOS") == null ? "Not Available" :
                   rs.getString("ADDL_PHONE_NOS")) + "</apho>";
                    xml =
 xml + "<faxno>" + (rs.getString("FAX_NO") == null ? "Not Available" :
                    rs.getString("FAX_NO")) + "</faxno>";
                    xml =
 xml + "<mail>" + (rs.getString("E_MAIL") == null ? "Not Available" :
                   rs.getString("E_MAIL")) + "</mail>";
                    xml =
 xml + "<hof>" + (rs.getString("HEAD_OF_OFFICE") == null ? "Not Available" :
                  rs.getString("HEAD_OF_OFFICE")) + "</hof>";
                }
            } catch (Exception e) {
                System.out.println("catch..in..loaddept::" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Update")) {
            xml = "<response><command>Update</command>";
            System.out.println("yy");
            try {
                deptOff_id =
                        Integer.parseInt(request.getParameter("txtOtherDeptOff_Id"));
                cs =
  con.prepareCall("{call HRM_OTHERDEPOFF_PROC(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");
                cs.setString(1, dept_id);
                cs.setInt(2, deptOff_id);
                cs.setString(3, deptOff_Name);
                cs.setString(4, deptOff_SName);
                cs.setString(5, addr1);
                cs.setString(6, addr2);
                cs.setString(7, city);
                cs.setInt(8, pin);
                cs.setString(9, phone);
                cs.setString(10, phone1);
                cs.setString(11, add_phone);
                cs.setString(12, fax);
                cs.setString(13, email);
                cs.setString(14, HoOffice);
                cs.setString(16, "update");
                cs.registerOutParameter(2, java.sql.Types.NUMERIC);
                cs.registerOutParameter(15, java.sql.Types.NUMERIC);
                cs.execute();

                int errcode = cs.getInt(15);
                int id = cs.getInt(2);
                System.out.println(id);
                System.out.println("SQLCODE:::" + errcode);
                if (errcode != 0) {
                    xml = xml + "<flag>failure</flag>";
                } else
                    xml = xml + "<flag>success</flag>";
            } catch (Exception e) {
                System.out.println("update exception  :" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }
        System.out.println(xml);
        out.println(xml);
    }

}

