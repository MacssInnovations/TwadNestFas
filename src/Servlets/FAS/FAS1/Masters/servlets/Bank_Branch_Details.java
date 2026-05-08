package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.CallableStatement;
import java.sql.Connection;
import java.sql.Date;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;

import java.sql.Timestamp;

import java.sql.Types;

import java.util.Calendar;
import java.util.GregorianCalendar;
import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

import sun.util.calendar.Gregorian;

public class Bank_Branch_Details extends HttpServlet {
    private static final String CONTENT_TYPE = 
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, 
                                                           IOException {

        HttpSession session = request.getSession(false);
        Connection connection = null;
        //System.out.println(max_branch_id);
        int max_branch_id = 0;
        System.out.println(max_branch_id);
        String branch_city = "";
        try {

            if (session == null) {

                System.out.println(request.getContextPath() + "/index.jsp");
                response.sendRedirect(request.getContextPath() + "/index.jsp");
                return;
            }
            System.out.println(session);

        } catch (Exception e) {
            System.out.println("Redirect Error :" + e);
        }


        Connection con = null;
        ResultSet rs = null;
        ResultSet rs2 = null;
        ResultSet rs3 = null;
        PreparedStatement ps = null;
        PreparedStatement ps2 = null;
        PreparedStatement ps1 = null;
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control", "no-cache");
        PrintWriter out = response.getWriter();
        String strCommand = "";
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
            con = DriverManager.getConnection(ConnectionString, strdbusername.trim(), 
                            strdbpassword.trim());
        } catch (Exception e) {
            System.out.println("Exception in opening connection :" + e);

        }

        try {
            strCommand = request.getParameter("Command");
            System.out.println("assign..here command..." + strCommand);

        }

        catch (Exception e) {
            System.out.println("Exception in assigning..." + e);
        }

        int cmbBankId = 0, BranchId = 0;
        int cmbDistrict = 0;
        int txtMic_Code = 0, txtPhone = 0, txtFax = 0;
        int district_code = 0;
        String status = null;
        String txtOffice_Name = "", txtOffice_Address1 = 
            "", txtOffice_Address2 = "", txtOffice_City = "";
        String txtRemarks = "";


        Calendar c;

        //String update_user="x";
        String update_user = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);

        try {
            cmbBankId = Integer.parseInt(request.getParameter("cmbBankId"));
        } catch (Exception e) {
            System.out.println("Exception to catch bank id ");
        }

        try {
            cmbDistrict = 
                    Integer.parseInt(request.getParameter("cmbDistrict"));
        } catch (Exception e) {
            System.out.println("Exception to catch cmbDistrict ");
        }

        try {
            BranchId = Integer.parseInt(request.getParameter("BranchId"));
        } catch (Exception e) {
            System.out.println("Exception to catch BranchId ");
        }

        try {
            txtOffice_Name = request.getParameter("txtBranch_Name");
        } catch (Exception e) {
            System.out.println("txtBranch_Name" + txtOffice_Name);
        }
        txtOffice_Name = request.getParameter("txtOffice_Name");
        txtOffice_Address1 = request.getParameter("txtOffice_Address1");
        txtOffice_Address2 = request.getParameter("txtOffice_Address2");
        txtOffice_City = request.getParameter("txtOffice_City");
        txtRemarks = request.getParameter("txtRemarks");

        try {
            txtMic_Code = 
                    Integer.parseInt(request.getParameter("txtMic_Code"));
        } catch (Exception e) {
            System.out.println("Exception to catch txtMic_Code ");
        }

        try {
            txtPhone = Integer.parseInt(request.getParameter("txtPhone"));
        } catch (Exception e) {
            System.out.println("Exception to catch txtPhoneNo ");
        }

        try {
            txtFax = Integer.parseInt(request.getParameter("txtFax"));
        } catch (Exception e) {
            System.out.println("Exception to catch txtFaxNo ");
        }
        try {
            status = request.getParameter("status");
        } catch (Exception e) {
            System.out.println("Exception to catch status ");
        }


        if (strCommand.equalsIgnoreCase("Add")) {

            System.out.println("dfjdkfjdfjdf" + max_branch_id);
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Add</command>";
            System.out.println("add");
            System.out.println(cmbBankId);


            try {
                ps2 = con.prepareStatement("SELECT nvl(max(branch_id),0) AS br_id FROM FAS_MST_BANK_BRANCHES where BANK_ID=?");
                ps2.setInt(1, cmbBankId);
                rs2 = ps2.executeQuery();

                while (rs2.next()) {
                    max_branch_id = rs2.getInt("br_id");
                }
                if (max_branch_id == 0) {
                    max_branch_id = 101;
                } else {
                    max_branch_id++;
                }

                System.out.println("Maximum Brach ID is ==" + max_branch_id);

                ps2.close();
                rs2.close();

            } catch (Exception e) {
                System.out.println("Failed to Fetch Maximum Branch ID " + e);
            }

            

            try {

                ps =  con.prepareStatement("insert into FAS_MST_BANK_BRANCHES(BANK_ID,BRANCH_ID,BRANCH_NAME,BRANCH_ADDRESS1," + 
                      "BRANCH_ADDRESS2,CITY_TOWN_NAME,DISTRICT_CODE,MICR_CODE,REMARKS,PHONE,FAX,STATUS,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");

                ps.setInt(1, cmbBankId);
                ps.setInt(2, max_branch_id);
                ps.setString(3, txtOffice_Name);
                ps.setString(4, txtOffice_Address1);
                ps.setString(5, txtOffice_Address2);
                ps.setString(6, txtOffice_City);
                ps.setInt(7, cmbDistrict);
                ps.setInt(8, txtMic_Code);
                ps.setString(9, txtRemarks);
                ps.setInt(10, txtPhone);
                ps.setInt(11, txtFax);
                ps.setString(12,status);
                ps.setString(13,update_user);
                ps.setTimestamp(14,ts);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                System.out.println("here is ok");


            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("Update")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Update</command>";
            

            try {
                ps =  con.prepareStatement("update FAS_MST_BANK_BRANCHES set BRANCH_NAME=?,BRANCH_ADDRESS1=?,BRANCH_ADDRESS2=?," + 
                      "CITY_TOWN_NAME=?,DISTRICT_CODE=?,MICR_CODE=?,REMARKS=?,PHONE=?,FAX=?,STATUS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? " + 
                      "where BANK_ID=? and BRANCH_ID=?");


                ps.setString(1, txtOffice_Name);
                ps.setString(2, txtOffice_Address1);
                ps.setString(3, txtOffice_Address2);
                ps.setString(4, txtOffice_City);
                ps.setInt(5, cmbDistrict);
                ps.setInt(6, txtMic_Code);
                ps.setString(7, txtRemarks);
                ps.setInt(8, txtPhone);
                ps.setInt(9, txtFax);
                ps.setString(10,status);
                ps.setString(11,update_user);
                ps.setTimestamp(12,ts);
                ps.setInt(13, cmbBankId);
                ps.setInt(14, BranchId);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
            }

            catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
           
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        } else if (strCommand.equalsIgnoreCase("Cancel")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Cancel</command>";

            try {
                ps = con.prepareStatement("UPDATE FAS_MST_BANK_BRANCHES SET STATUS='C',UPDATED_BY_USER_ID=?,UPDATED_DATE=? " + 
                      " where BANK_ID=? and BRANCH_ID=?");


                ps.setString(1,update_user);
                ps.setTimestamp(2,ts);
                ps.setInt(3, cmbBankId);
                ps.setInt(4, BranchId);
               
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
            } catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }
    }
}
