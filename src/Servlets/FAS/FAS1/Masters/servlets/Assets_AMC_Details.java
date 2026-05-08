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

public class Assets_AMC_Details extends HttpServlet {
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
        int cmbAcc_UnitCode = 0,cmbOffice_code=0,agr_no=0;
        
        String txtOffice_Name = "", txtOffice_Address1 = "", cmbFinancialYear = "", txtreg_no = "";
        String txtRemarks = "",agrdate="";
        String txtmake="",man="",cubic="",tank="",road="",curdate="",amcfromdate="",amctodate="";
        Date txt_date = null,txt_amcfromdate=null,txt_amctodate=null,Agrmt_date=null;
        Calendar c;
        double Amc_Amount=0; 
        
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
        try {
        	Amc_Amount = Integer.parseInt(request.getParameter("txtamc_amount"));
        	
        } catch (Exception e) {
            System.out.println("Exception to catch Amc_Amount ");
        }
        try {
        	agr_no = Integer.parseInt(request.getParameter("txtagr_no"));
        	
        } catch (Exception e) {
            System.out.println("Exception to catch txtagr_no id ");
        } 
       
        txtRemarks = request.getParameter("txtRemarks");
       
      
       
        amcfromdate=request.getParameter("Amcfromdate");
        amctodate =request.getParameter("Amctodate");
        agrdate=request.getParameter("Agrdate");
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
           
            System.out.println("cmbFinancialYear " + cmbFinancialYear); 
         
            System.out.println("txtRemarks " + txtRemarks); 
         
            System.out.println("amcfromdate " + amcfromdate); 
            System.out.println("amctodate" + amctodate); 
            System.out.println("agrdate " + agrdate); 
            System.out.println("curdate " + curdate);
            System.out.println("cmbasset"+cmbasset);
            System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
            System.out.println("cmbOffice_code"+cmbOffice_code);
            System.out.println("update_user"+update_user);
            System.out.println("ts"+ts);

            try {

                ps =  con.prepareStatement("insert into FAS_ASSET_AMC_DETAILS(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,ASSET_CODE," + 
                      "ENTER_DATE,FINANCIAL_YEAR,AMC_COMP_NAME,AMC_PERIOD_FROM,AMC_PERIOD_TO,AMC_AMOUNT,AGREEMENT_NO,AGREEMENT_DATE,"+
                      "REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
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
                ps.setString(5, cmbFinancialYear);
                ps.setString(6, txtOffice_Name);
                if(amcfromdate.equals(""))
                    ps.setNull(7,Types.DATE);
                else
                {
                    String[] sd3 =amcfromdate.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd3[2]), Integer.parseInt(sd3[1]) - 1,Integer.parseInt(sd3[0]));
                    java.util.Date d3 = c.getTime();
                    txt_amcfromdate = new Date(d3.getTime());
                    System.out.println("amcfromdate " + txt_amcfromdate);
                    ps.setDate(7, txt_amcfromdate);  
                } 
                if(amctodate.equals(""))
                    ps.setNull(8,Types.DATE);
                else
                {
                    String[] sd3 =amctodate.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd3[2]), Integer.parseInt(sd3[1]) - 1,Integer.parseInt(sd3[0]));
                    java.util.Date d3 = c.getTime();
                    txt_amctodate = new Date(d3.getTime());
                    System.out.println("amctodate " + txt_amctodate);
                    ps.setDate(8, txt_amctodate);  
                } 
                ps.setDouble(9,Amc_Amount);
                ps.setInt(10, agr_no);
                if(agrdate.equals(""))
                    ps.setNull(11,Types.DATE);
                else
                {
                    String[] sd3 =agrdate.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd3[2]), Integer.parseInt(sd3[1]) - 1,Integer.parseInt(sd3[0]));
                    java.util.Date d3 = c.getTime();
                    Agrmt_date = new Date(d3.getTime());
                    System.out.println("agreementdate " + Agrmt_date);
                    ps.setDate(11, Agrmt_date);  
                } 
                
                
                ps.setString(12, txtRemarks);
                
               
                ps.setString(13, update_user);
                ps.setTimestamp(14, ts);
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

            try {
                ps =  con.prepareStatement("update FAS_ASSET_AMC_DETAILS set ENTER_DATE=?,AMC_COMP_NAME=?,AMC_PERIOD_FROM=?,AMC_PERIOD_TO=?,AMC_AMOUNT=?,AGREEMENT_NO=?,AGREEMENT_DATE=?,"+
                      "REMARKS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and ASSET_CODE=? and FINANCIAL_YEAR=? ");
               
                
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
               
                ps.setString(2, txtOffice_Name);
                if(amcfromdate.equals(""))
                    ps.setNull(3,Types.DATE);
                else
                {
                    String[] sd3 =amcfromdate.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd3[2]), Integer.parseInt(sd3[1]) - 1,Integer.parseInt(sd3[0]));
                    java.util.Date d3 = c.getTime();
                    txt_amcfromdate = new Date(d3.getTime());
                    System.out.println("amcfromdate " + txt_amcfromdate);
                    ps.setDate(3, txt_amcfromdate);  
                } 
                if(amctodate.equals(""))
                    ps.setNull(4,Types.DATE);
                else
                {
                    String[] sd3 =amctodate.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd3[2]), Integer.parseInt(sd3[1]) - 1,Integer.parseInt(sd3[0]));
                    java.util.Date d3 = c.getTime();
                    txt_amctodate = new Date(d3.getTime());
                    System.out.println("amctodate " + txt_amctodate);
                    ps.setDate(4, txt_amctodate);  
                } 
                ps.setDouble(5,Amc_Amount);
                ps.setInt(6, agr_no);
                if(agrdate.equals(""))
                    ps.setNull(7,Types.DATE);
                else
                {
                    String[] sd3 =agrdate.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd3[2]), Integer.parseInt(sd3[1]) - 1,Integer.parseInt(sd3[0]));
                    java.util.Date d3 = c.getTime();
                    Agrmt_date = new Date(d3.getTime());
                    System.out.println("agrdate " + Agrmt_date);
                    ps.setDate(7, Agrmt_date);  
                } 
                
                
                ps.setString(8, txtRemarks);
                
               
                ps.setString(9, update_user);
                ps.setTimestamp(10, ts);
                ps.setInt(11, cmbAcc_UnitCode);
                ps.setInt(12, cmbOffice_code);
                ps.setInt(13, cmbasset);
                ps.setString(14, cmbFinancialYear);
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                System.out.println("here is ok");
             
 
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
                ps = con.prepareStatement("delete from  FAS_ASSET_AMC_DETAILS " + 
                      " where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and ASSET_CODE=? and FINANCIAL_YEAR=?");



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
