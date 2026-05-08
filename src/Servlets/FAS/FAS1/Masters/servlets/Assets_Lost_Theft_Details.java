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

public class Assets_Lost_Theft_Details extends HttpServlet {
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

        int cmbasset = 0;
        int cmbAcc_UnitCode = 0,cmbOffice_code=0;
        
        String txtOffice_Name = "",  iscomp = "";
        String txtRemarks = "",compdetails="",curdate="";
        String phy_loc="";
        Date txt_date = null;
        Calendar c;
        
        
        String update_user = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);
        compdetails =request.getParameter("compdetails");
        try {
        	cmbasset = Integer.parseInt(request.getParameter("cmbasset"));
        	System.out.println("cmbasset " + cmbasset); 
        } catch (Exception e) {
            System.out.println("Exception to catch ASSET CODE================= ");
        }
       
        try {
        	cmbAcc_UnitCode = Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
        } catch (Exception e) {
            System.out.println("Exception to catch cmbAcc_UnitCode ");
        }
        try {
        	cmbOffice_code = Integer.parseInt(request.getParameter("cmbOffice_code"));
        } catch (Exception e) {
            System.out.println("Exception to catch cmbOffice_code ");
        }
        txtOffice_Name = request.getParameter("txtOffice_Name");
        System.out.println("txtOffice_Name "+txtOffice_Name);
       
       
        txtRemarks = request.getParameter("txtRemarks");
        phy_loc = request.getParameter("phy_loc");
      
        iscomp=request.getParameter("iscomp");
      String  comp_lodged_off=request.getParameter("comp_lodged_off");
     
        curdate=request.getParameter("txt_date");
        
        String[] sd = request.getParameter("txt_date").split("/");
        c = 
new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1, 
                    Integer.parseInt(sd[0]));
        java.util.Date d = c.getTime();
        txt_date = new Date(d.getTime());
        System.out.println("txt_date " + txt_date); 
        

        if (strCommand.equalsIgnoreCase("Add")) {

          
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Add</command>";
            System.out.println("add");
            System.out.println("txtOffice_Name " + txtOffice_Name); 
            System.out.println("txtRemarks " + txtRemarks); 
            System.out.println("curdate " + curdate);
            System.out.println("cmbasset"+cmbasset);
            System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
            System.out.println("cmbOffice_code"+cmbOffice_code);
            System.out.println("update_user"+update_user);
            System.out.println("ts"+ts);
            System.out.println("iscomp"+iscomp);
            System.out.println("compdetails"+compdetails);
            System.out.println("phy_loc"+phy_loc);
            try {

                ps =  con.prepareStatement("insert into FAS_ASSET_LOST_DETAILS(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,ASSET_CODE," + 
                      "DATE_OF_LOST,PHY_LOCATION,ISCOMP_LODGED,COMP_DETAILS,COMP_LODGED_OFFICE,REMARKS,"+
                      "UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?)");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, cmbasset);
                if(curdate.equals(""))
                    ps.setNull(4,Types.DATE);
                else
                {
                    String[] sd2 =curdate.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) - 1,Integer.parseInt(sd2[0]));
                    java.util.Date d2 = c.getTime();
                    txt_date = new Date(d2.getTime());
                    System.out.println("date " + txt_date);
                    ps.setDate(4, txt_date);    
                }
               
                ps.setString(5,phy_loc);
                ps.setString(6, iscomp);
                ps.setString(7,compdetails);
              //  ps.setString(8,comp_lodged_off);
                ps.setString(8, txtOffice_Name);
                ps.setString(9, txtRemarks);
                ps.setString(10, update_user);
                ps.setTimestamp(11, ts);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                System.out.println("here is ok");
             

            } catch (Exception e) {
                System.out.println("ggggggg----> " + e);
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
            System.out.println("inside update command");
           
            System.out.println("txtOffice_Name " + txtOffice_Name); 
            System.out.println("txtRemarks " + txtRemarks); 
            System.out.println("curdate " + curdate);
            System.out.println("cmbasset"+cmbasset);
            System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
            System.out.println("cmbOffice_code"+cmbOffice_code);
            System.out.println("update_user"+update_user);
            System.out.println("ts"+ts);
            System.out.println("iscomp"+iscomp);
            System.out.println("compdetails"+compdetails);
            System.out.println("phy_loc"+phy_loc);
            try {
                ps =  con.prepareStatement("update FAS_ASSET_LOST_DETAILS set DATE_OF_LOST=?,PHY_LOCATION=?,ISCOMP_LODGED=?,COMP_DETAILS=?,COMP_LODGED_OFFICE=?,REMARKS=?,"+
                                           "UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and ASSET_CODE=? ");
               
                
                if(curdate.equals(""))
                    ps.setNull(1,Types.DATE);
                else
                {
                    String[] sd2 =curdate.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) - 1,Integer.parseInt(sd2[0]));
                    java.util.Date d2 = c.getTime();
                    txt_date = new Date(d2.getTime());
                    System.out.println("date " + txt_date);
                    ps.setDate(1, txt_date);    
                }
               
                ps.setString(2,phy_loc);
                ps.setString(3, iscomp);
                ps.setString(4,compdetails);
              
                ps.setString(5, txtOffice_Name);
                ps.setString(6, txtRemarks);
                ps.setString(7, update_user);
                ps.setTimestamp(8, ts);
                ps.setInt(9, cmbAcc_UnitCode);
                ps.setInt(10, cmbOffice_code);
                ps.setInt(11, cmbasset);
                
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                System.out.println("here is ok");
             
            }

            catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
           
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }  else if (strCommand.equalsIgnoreCase("Delete")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Delete</command>";

            try {
                ps = con.prepareStatement("delete from  FAS_ASSET_LOST_DETAILS " + 
                      " where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and ASSET_CODE=?");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, cmbasset);
  
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
