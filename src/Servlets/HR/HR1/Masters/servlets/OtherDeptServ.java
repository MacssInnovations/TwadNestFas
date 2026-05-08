package Servlets.HR.HR1.Masters.servlets;


import java.io.IOException;
import java.io.PrintWriter;

import java.sql.*;

import java.util.*;

import javax.servlet.*;
import javax.servlet.http.*;


public class OtherDeptServ extends HttpServlet {
    private static final String CONTENT_TYPE =
        "text/html; charset=windows-1252";


    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {

        Connection con = null;
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
            System.out.println("Exception in connection..." + e);
        }
        ResultSet rs = null;
        PreparedStatement ps = null;

        try {
            HttpSession session = request.getSession(false);
            if (session == null) {
                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
            }

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }
        response.setContentType(CONTENT_TYPE);

        String strcode = "";
        String strname = "";
        String xml = "";
        String strCommand = "";
        String strsname = "";
        String add1 = "", add2 = "", city = "", phone = "";
        String addphone = "", fax = "", email = "", headofdept = "", std = "";
        int pincode = 0;
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();

        try {

            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);
            strcode = request.getParameter("txtOtherDeptid");
            strname = request.getParameter("txtOtherDept");
            System.out.println("assign Name....::" + strname);

            strsname = request.getParameter("txtOtherDeptShortName");
            System.out.println("::short::" + strsname);
            add1 = request.getParameter("txtAddress1");
            add2 = request.getParameter("txtAddress2");
            city = request.getParameter("txtCity");
            std = request.getParameter("txtStd").trim();
            phone = request.getParameter("txtPhone");
            addphone = request.getParameter("txtAPhone");
            fax = request.getParameter("txtFax");
            email = request.getParameter("txtEmail");
            headofdept = request.getParameter("txtHeadOfDept");


            try {
                if (request.getParameter("txtPincode") != null)
                    pincode =
                            Integer.parseInt(request.getParameter("txtPincode").trim());
            } catch (NumberFormatException e) {
                System.out.println("number exception::" + e);
                pincode = 0;
            }

            System.out.println("assign..... strcode::" + strcode);
            //System.out.println("assign..... pin::"+pincode);
            System.out.println("assign..... std::" + std);
            System.out.println("assign..... code::" + strcode);

        } catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        if (strCommand.equalsIgnoreCase("Add")) {
            xml = "<response><command>Add</command>";
            //String sql="insert into TEST_STATE values(?,?)";
            try {
                System.out.println("adddddddd::" + pincode);
                CallableStatement cs =
                    con.prepareCall("{call HRM_MST_OTHERDEPTS (?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)}");

                cs.setString(2, "add");
                cs.setString(3, strcode);
                cs.setString(4, strname);

                cs.setString(5, strsname);
                cs.setString(6, add1);
                cs.setString(7, add2);
                cs.setString(8, city);
                cs.setInt(9, pincode);
                cs.setString(10, std);
                cs.setString(11, phone);
                cs.setString(12, addphone);
                cs.setString(13, fax);
                cs.setString(14, email);
                cs.setString(15, headofdept);

                cs.registerOutParameter(1, java.sql.Types.NUMERIC);
                //cs.registerOutParameter(3,java.sql.Types.NUMERIC);
                cs.execute();


                int errcode = cs.getInt(1);
                System.out.println("SQLCODE:::" + errcode);
                cs.close();

                if (errcode != 0) {
                    xml = xml + "<flag>failure</flag>";
                } else {
                    xml = xml + "<flag>success</flag>>";
                }
            } catch (Exception e) {

                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        else if (strCommand.equalsIgnoreCase("novali")) {
            String sxml = "<response><command>novali</command>";
            try {
                ps =
  con.prepareStatement("select * from HRM_MST_OTHER_DEPTS where OTHER_DEPT_ID=?");
                ps.setString(1, strcode);
                rs = ps.executeQuery();
                int i = 0;
                while (rs.next()) {
                    i++;
                }
                if (i == 0) {
                    xml = sxml + "<flag>success</flag>";
                } else {
                    xml = sxml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("catch...." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        else if (strCommand.equalsIgnoreCase("novalitype")) {
            String sxml = "<response><command>novalitype</command>";
            try {
                System.out.println("novali name:::" + strname);
                ps =
  con.prepareStatement("select * from HRM_MST_OTHER_DEPTS where OTHER_DEPT_NAME=?");
                ps.setString(1, strname);
                rs = ps.executeQuery();
                int i = 0;
                while (rs.next()) {
                    i++;
                    System.out.println("\nget no" + rs.getInt(1));
                }
                if (i == 0) {
                    xml = sxml + "<flag>success</flag>";
                } else {
                    xml = sxml + "<flag>failure</flag>";
                }
            } catch (Exception e) {
                System.out.println("type duplicate :catch...." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        else if (strCommand.equalsIgnoreCase("Delete")) {
            xml = "<response><command>Delete</command>";
            try {
                System.out.println("strcode::" + strcode);
                ps =
  con.prepareStatement("delete from HRM_MST_OTHER_DEPTS where OTHER_DEPT_ID=?");
                ps.setString(1, strcode);
                //ps=con.prepareStatement("delete from HRM_MST_OTHER_DEPTS where OTHER_DEPT_NAME=?");
                // ps.setString(1,strname);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag><scd>" + strcode + "</scd>";
            } catch (Exception e) {
                System.out.println("catch...." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }

        else if (strCommand.equalsIgnoreCase("Update")) {
            xml = "<response><command>Update</command>";

            try {


                /*      CallableStatement cs=con.prepareCall("{call HRM_MST_OTHERDEPTS (?,?,?,?,?,?,?,?,?,?,?,?,?,?)}" );
                cs.setInt(1,strcode);
                cs.setString(2,"update");
                System.out.println("up naem::"+strname);
                cs.setString(4,strname);

                cs.setString(5,strsname);
                cs.setString(6,add1);
                cs.setString(7,add2);
                cs.setString(8,city);
                cs.setLong(9,pincode);
                cs.setString(10,phone);
                cs.setString(11,addphone);
                cs.setString(12,fax);
                cs.setString(13,email);
                cs.setString(14,headofdept);

                cs.registerOutParameter(1,java.sql.Types.NUMERIC);
                cs.registerOutParameter(3,java.sql.Types.NUMERIC);
                cs.execute();

                int no=cs.getInt(3);
                int errcode=cs.getInt(1);
                System.out.println("SQLCODE:::"+errcode);
                cs.close();

                if(errcode!=0)
                {
                       xml=xml+"<flag>failure</flag>";
                }
                else
                {
                    xml=xml+"<flag>success</flag>";

                }   /**/


                ps =
  con.prepareStatement("update HRM_MST_OTHER_DEPTS set OTHER_DEPT_NAME=?,OTHER_DEPT_SHORT_NAME=?," +
                       "     ADDRESS1=?,ADDRESS2=?,CITY_TOWN=?,PINCODE=?,STD_CODE=?,PHONE_NO=?,ADDL_PHONE_NOS=?," +
                       "     FAX_NO=?,E_MAIL=?,HEAD_OF_DEPT=? where OTHER_DEPT_ID=?");

                System.out.println("std code:" + std);
                ps.setString(1, strname);
                ps.setString(2, strsname);
                ps.setString(3, add1);
                ps.setString(4, add2);
                ps.setString(5, city);
                ps.setInt(6, pincode);
                ps.setString(7, std);
                ps.setString(8, phone);
                ps.setString(9, addphone);
                ps.setString(10, fax);
                ps.setString(11, email);
                ps.setString(12, headofdept);
                ps.setString(13, strcode);

                ps.executeUpdate();
                xml = xml + "<flag>success</flag>"; /**/

            } catch (Exception e) {
                System.out.println("catch...." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
        }


        System.out.println("xml is:" + xml);
        out.write(xml);
        out.flush();
        out.close();
    }

    public static String Trim(String s) {

        while (s.charAt(0) == ' ')
            s = s.substring(1);
        while (s.charAt(s.length() - 1) == ' ')
            s = s.substring(0, s.length() - 1);
        return s;


    }


}
