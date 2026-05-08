package Servlets.HR.HR1.EmployeeMaster.servlets;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;
import Servlets.Security.classes.UserProfile;

import java.io.File;
import java.io.IOException;
import java.io.PrintWriter;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class ResetPasswordServ_FAS extends HttpServlet {

    public void init(ServletConfig config) throws ServletException {
        super.init(config);

    }

    public void doGet(HttpServletRequest request,
                      HttpServletResponse response) throws ServletException,
                                                           IOException {
        Connection con = null;
        try {

            File f = new File("");
            f.deleteOnExit();


            LoadDriver driver=new LoadDriver();
        	con=driver.getConnection();
        } catch (Exception e) {
            System.out.println("Exception in connection..." + e);
        }
        ResultSet rs = null;
        PreparedStatement ps = null;
        PrintWriter out = response.getWriter();
        response.setContentType("text/xml");
        response.setHeader("Cache-Control", "no-cache");
        String userid = null, newpass = "", strCommand = null;
        String strPassword = null;
        HttpSession session = request.getSession(false);
        String loginuserid = "", OFFICE_ID = "", loginOFFICE_ID =
            "", officelevel = "", region_office = "", NEW_region_office = "";
        int empid = 0, flag = 0;
        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign....." + strCommand);
            userid = request.getParameter("txtUserId");
            System.out.println("assign..... userid::" + userid);
            newpass = request.getParameter("txtnewpass");
            System.out.println("assign..... new pass::" + newpass);
            loginuserid = (String)session.getAttribute("UserId");
            System.out.println("loginuseridn..... new pass:>>>>>>>>>>>:" +
                               loginuserid);
        } catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }
        String xml = null;
        response.setHeader("Cache-Control", "no-cache");
		
        
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
        System.out.println("Login userid..." + updatedby);
        if (strCommand.equalsIgnoreCase("test")) {
            xml = "<response><command>test</command>";
            try {

                byte[] b = newpass.getBytes();


                try {
                    MessageDigest algorithm = MessageDigest.getInstance("MD5");
                    algorithm.reset();
                    algorithm.update(b);
                    byte messageDigest[] = algorithm.digest();
                    System.out.println("actual encrypt::" + messageDigest);
                    StringBuffer hexString = new StringBuffer();
                    for (int i = 0; i < messageDigest.length; i++) {
                        hexString.append(Integer.toHexString(0xFF &
                                                             messageDigest[i]));
                    }

                    strPassword = new String(hexString);
                } catch (NoSuchAlgorithmException nsae) {
                    System.out.println("Second MD5::" + nsae);
                }
                System.out.println("userid.equals(newpass)" +
                                   userid.equals(newpass));
                if (userid.equals(newpass)) {
                    ps =
  con.prepareStatement("update  SEC_MST_USERS set USER_PASSWORD=?,CHANGE_PASSWORD='1',UPDATED_BY_USER_ID='"+updatedby+"',UPDATED_DATE=SYSTIMESTAMP  where USER_ID=?");
                    ps.setString(1, strPassword);
                    ps.setString(2, userid);
                    ps.executeUpdate();
                    System.out.println("second check ok");
                } else {
                    ps =
  con.prepareStatement("update  SEC_MST_USERS set USER_PASSWORD=?,CHANGE_PASSWORD='0',UPDATED_BY_USER_ID='"+updatedby+"',UPDATED_DATE=SYSTIMESTAMP   where USER_ID=?");
                    ps.setString(1, strPassword);
                    ps.setString(2, userid);
                    ps.executeUpdate();
                    System.out.println("second check ok2");
                }

                xml = xml + "<flag>success</flag>";


            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
            }

        }

        else if (strCommand.equalsIgnoreCase("Login")) {
            xml = "<response><command>Login</command>";
            try {


                ps =
  con.prepareStatement("Select Aa.Office_Id As Office_Id,Office_Level_Id,Region_Office_Id From(" +
                       "Select Office_Id From Hrm_Emp_Current_Posting  Where Employee_Id In (Select Employee_Id From Sec_Mst_Users Where User_Id=?))Aa Left Outer Join (Select Office_Id,Office_Level_Id,Region_Office_Id From Com_Mst_All_Offices_View)Bb " +
                       " On Bb.Office_Id=Aa.Office_Id");
                ps.setString(1, loginuserid);
                rs = ps.executeQuery();
                if (rs.next()) {
                    officelevel = rs.getString("Office_Level_Id");
                    region_office = rs.getString("Region_Office_Id");
                    System.out.println("Login_region_office " + region_office);
                }
            } catch (Exception e) {
                System.out.println("catch........" + e);
            }
            String Employee_Id = "";
            try {


                ps =
  con.prepareStatement("select USER_ID,Employee_Id from   SEC_MST_USERS  where USER_ID=?");
                ps.setString(1, userid);
                rs = ps.executeQuery();
                if (rs.next()) {
                    Employee_Id = rs.getString("Employee_Id");
                    xml = xml + "<flag>success</flag>";
                    try {
                        if (officelevel == null)
                            officelevel = "";
                        System.out.println("officelevel " + officelevel);
                        if (officelevel.equalsIgnoreCase("HO")) {
                            flag = 1;
                        } else {


                            ps =
  con.prepareStatement("Select Aa.Office_Id As Office_Id,Office_Level_Id,Region_Office_Id From(" +
                       "Select Office_Id From Hrm_Emp_Current_Posting  Where Employee_Id=?)Aa Left Outer Join (Select Office_Id,Office_Level_Id,Region_Office_Id From Com_Mst_All_Offices_View)Bb " +
                       " On Bb.Office_Id=Aa.Office_Id");
                            ps.setString(1, Employee_Id);
                            rs = ps.executeQuery();
                            if (rs.next()) {
                                System.out.println(region_office +
                                                   " region_office " +
                                                   rs.getString("Region_Office_Id"));
                                NEW_region_office =
                                        rs.getString("Region_Office_Id");

                            }
                            if (NEW_region_office == null)
                                NEW_region_office = "";
                            if (NEW_region_office.equals(region_office)) {
                                flag = 1;
                            }
                        }
                        if (flag == 0) {
                            xml += "<value>failed</value>";
                        } else {
                            xml += "<value>success</value>";
                        }
                    } catch (Exception e) {
                        System.out.println("catch........" + e);
                    }
                } else {
                    xml = xml + "<flag>failure</flag>";
                    xml += "<value>failed</value>";
                }


            }

            catch (Exception e) {

                System.out.println("catch........" + e);
                xml = xml + "<flag>failure</flag>";
                xml += "<value>failed</value>";
            }
        }
        xml = xml + "</response>";
        out.println(xml);
        System.out.println(xml);


    }
}
