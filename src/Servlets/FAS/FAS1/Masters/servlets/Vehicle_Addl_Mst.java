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

public class Vehicle_Addl_Mst extends HttpServlet {
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

        int cmbasset = 0, BranchId = 0;
        int cmbAcc_UnitCode = 0,cmbOffice_code=0;
        
        String txtOffice_Name = "", txtOffice_Address1 = "", cmbFinancialYear = "", txtreg_no = "";
        String txtRemarks = "";
        String txtmake="",man="",cubic="",tank="",road="",curdate="",regdate="";
        Date txt_date = null,txt_regstdate=null;
        Calendar c;

        
        String update_user = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);

        try {
        	cmbasset = Integer.parseInt(request.getParameter("cmbasset"));
        	
        } catch (Exception e) {
            System.out.println("Exception to catch asset id ");
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
       
        txtOffice_Address1 = request.getParameter("txtOffice_Address1");
       
        cmbFinancialYear = request.getParameter("cmbFinancialYear");
        
        txtreg_no = request.getParameter("txtreg_no");
       
        txtRemarks = request.getParameter("txtRemarks");
       
        txtmake=request.getParameter("txtmake");
        
        man=request.getParameter("txtman");
       
        cubic=request.getParameter("txtcubic");
        
       tank=request.getParameter("txttank");
       
        road=request.getParameter("txtroad");
       
        regdate=request.getParameter("txt_regstdate");
      
        curdate=request.getParameter("txt_date");
        
        String[] sd = request.getParameter("txt_date").split("/");
        c = 
new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1, 
                    Integer.parseInt(sd[0]));
        java.util.Date d = c.getTime();
        txt_date = new Date(d.getTime());
        System.out.println("txt_date " + txt_date); 

        String[] sd1 = request.getParameter("txt_regstdate").split("/");
        c = 
new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]) - 1, 
                    Integer.parseInt(sd1[0]));
        java.util.Date d1 = c.getTime();
        txt_regstdate = new Date(d1.getTime());
        System.out.println("txt_regstdate " + txt_regstdate); 


        if (strCommand.equalsIgnoreCase("Add")) {

          
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Add</command>";
            System.out.println("add");
            System.out.println("txtOffice_Name " + txtOffice_Name); 
            System.out.println("txtOffice_Address1" + txtOffice_Address1); 
            System.out.println("cmbFinancialYear " + cmbFinancialYear); 
            System.out.println("txtreg_no" + txtreg_no); 
            System.out.println("txtRemarks " + txtRemarks); 
            System.out.println("txtmake " + txtmake); 
            System.out.println("txtman " + man); 
            System.out.println("txtcubic " + cubic); 
            System.out.println("txttank " + tank); 
            System.out.println("txtroad" + road); 
            System.out.println("regdate " + regdate); 
            System.out.println("curdate " + curdate);
            System.out.println("cmbasset"+cmbasset);
            System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
            System.out.println("cmbOffice_code"+cmbOffice_code);
            System.out.println("update_user"+update_user);
            System.out.println("ts"+ts);

            try {

                ps =  con.prepareStatement("insert into FAS_VEHICLE_ADDL_MST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,ASSET_CODE," + 
                      "REGN_OFFICE,REGN_OFFICE_ADDRESS,FIN_YEAR,REGN_NO,MAKE,YEAR_OF_MANUFACTURE,CUBIC_CAPACITY,TANK_CAPACITY,ROAD_TAX_DETAILS,"+
                      "REMARKS,DATE_OF_ENTRY,REGN_DATE,UPDATED_BY_USERID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, cmbasset);
                ps.setString(4, txtOffice_Name);
                ps.setString(5, txtOffice_Address1);
                ps.setString(6, cmbFinancialYear);
                ps.setString(7, txtreg_no);
                ps.setString(8, txtmake);
                ps.setString(9, man);
                ps.setString(10, cubic);
                ps.setString(11, tank);
                ps.setString(12,road);
                ps.setString(13, txtRemarks);
                if(curdate.equals(""))
                    ps.setNull(14,Types.DATE);
                else
                {
                    String[] sd2 =curdate.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) - 1,Integer.parseInt(sd2[0]));
                    java.util.Date d2 = c.getTime();
                    txt_date = new Date(d2.getTime());
                    System.out.println("date " + txt_date);
                    ps.setDate(14, txt_date);    
                }
                if(regdate.equals(""))
                    ps.setNull(15,Types.DATE);
                else
                {
                    String[] sd3 =regdate.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd3[2]), Integer.parseInt(sd3[1]) - 1,Integer.parseInt(sd3[0]));
                    java.util.Date d3 = c.getTime();
                    txt_regstdate = new Date(d3.getTime());
                    System.out.println("reg_date " + txt_regstdate);
                    ps.setDate(15, txt_regstdate);  
                } 
                ps.setString(16, update_user);
                ps.setTimestamp(17, ts);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                System.out.println("here is ok");
             

            } catch (Exception e) {
                System.out.println("KKK----> " + e);
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

            try {
                ps =  con.prepareStatement("update FAS_VEHICLE_ADDL_MST set REGN_OFFICE=?,REGN_OFFICE_ADDRESS=?,FIN_YEAR=?,REGN_NO=?,MAKE=?,YEAR_OF_MANUFACTURE=?,CUBIC_CAPACITY=?,TANK_CAPACITY=?,ROAD_TAX_DETAILS=?,"+
                      "REMARKS=?,DATE_OF_ENTRY=?,REGN_DATE=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and ASSET_CODE=? ");

                ps.setInt(15, cmbAcc_UnitCode);
                ps.setInt(16, cmbOffice_code);
                ps.setInt(17, cmbasset);
                ps.setString(1, txtOffice_Name);
                ps.setString(2, txtOffice_Address1);
                ps.setString(3, cmbFinancialYear);
                ps.setString(4, txtreg_no);
                ps.setString(5, txtmake);
                ps.setString(6, man);
                ps.setString(7, cubic);
                ps.setString(8, tank);
                ps.setString(9,road);
                ps.setString(10, txtRemarks);
                if(curdate.equals(""))
                    ps.setNull(11,Types.DATE);
                else
                {
                    String[] sd2 =curdate.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) - 1,Integer.parseInt(sd2[0]));
                    java.util.Date d2 = c.getTime();
                    txt_date = new Date(d2.getTime());
                    System.out.println("date " + txt_date);
                    ps.setDate(11, txt_date);    
                }
                if(regdate.equals(""))
                    ps.setNull(12,Types.DATE);
                else
                {
                    String[] sd3 =regdate.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd3[2]), Integer.parseInt(sd3[1]) - 1,Integer.parseInt(sd3[0]));
                    java.util.Date d3 = c.getTime();
                    txt_regstdate = new Date(d3.getTime());
                    System.out.println("reg_date " + txt_regstdate);
                    ps.setDate(12, txt_regstdate);  
                } 
                ps.setString(13, update_user);
                ps.setTimestamp(14, ts);

                // ps.setString(8,update_user);
                // ps.setTimestamp(12,ts);
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
        }  else if (strCommand.equalsIgnoreCase("Delete")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Delete</command>";

            try {
                ps = con.prepareStatement("delete from  FAS_VEHICLE_ADDL_MST " + 
                      " where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and ASSET_CODE=? and FIN_YEAR=?");



                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, cmbasset);
                ps.setString(4,cmbFinancialYear);
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
