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

public class Insurance_Details extends HttpServlet {
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
        int cmbAcc_UnitCode = 0,cmbOffice_code=0,policy_no=0;
        
        String txtOffice_Name = "", txtOffice_Address1 = "", cmbFinancialYear = "", instype = "",insfavour="";
        String txtRemarks = "",Comn_Date="",iss_address="";
        String txtmake="",man="",cubic="",tank="",road="",curdate="",expdate="",rendate="";
        Date txt_date = null,date_of_comnce=null,expiry_date=null,renewal_date=null;
        Calendar c;
        double ins_amount=0,pre_amount=0,other_charges=0; 
        
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
        	ins_amount = Double.parseDouble(request.getParameter("ins_amount"));
        	
        } catch (Exception e) {
            System.out.println("Exception to catch Amc_Amount ");
        }
        try {
        	pre_amount = Double.parseDouble(request.getParameter("txtpre_amount"));
        	
        } catch (Exception e) {
            System.out.println("Exception to catch Amc_Amount ");
        }
        try {
        	other_charges = Double.parseDouble(request.getParameter("other_charges"));
        	
        } catch (Exception e) {
            System.out.println("Exception to catch other_charges ");
        }
        try {
        	policy_no = Integer.parseInt(request.getParameter("policy_no"));
        	
        } catch (Exception e) {
            System.out.println("Exception to catch txtagr_no id ");
        } 
       
        txtRemarks = request.getParameter("txtRemarks");
        instype = request.getParameter("type_insurance");
        insfavour=request.getParameter("infavour");
        
       
        Comn_Date=request.getParameter("cmc_date");
        expdate =request.getParameter("expiry_date");
        rendate=request.getParameter("renewal_date");
        curdate=request.getParameter("txt_date");
        iss_address=request.getParameter("iss_address");
   
        System.out.println("iss_address" + iss_address); 

        if (strCommand.equalsIgnoreCase("Add")) {

          
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Add</command>";
            System.out.println("add");
            System.out.println("txtOffice_Name " + txtOffice_Name); 
           
            System.out.println("cmbFinancialYear " + cmbFinancialYear); 
         
            System.out.println("txtRemarks " + txtRemarks); 
         
            System.out.println("cmc_date " + Comn_Date); 
            System.out.println("expdate" + expdate); 
            System.out.println("agrdate " + rendate); 
            System.out.println("curdate " + curdate);
            System.out.println("cmbasset"+cmbasset);
            System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
            System.out.println("cmbOffice_code"+cmbOffice_code);
            System.out.println("update_user"+update_user);
            System.out.println("ts"+ts);

            try {

                ps =  con.prepareStatement("insert into FAS_INSURANCE_DETAILS(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,ASSET_CODE," + 
                      "ENTER_DATE,FINANCIAL_YEAR,INS_COMP_NAME,INS_TYPE,INS_FAVOUR_OF,POLICY_NO,DATE_COMNCE,EXPIRY_DATE,INS_AMOUNT,"+
                      "PREMIUM_AMOUNT,OTHER_CHARGES,RENEWAL_DATE,ISS_OFF_ADDRESS,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
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
                ps.setString(7,instype);
                ps.setString(8, insfavour);
                ps.setInt(9,policy_no);
                
                if(Comn_Date.equals(""))
                    ps.setNull(10,Types.DATE);
                else
                {
                    String[] sd3 =Comn_Date.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd3[2]), Integer.parseInt(sd3[1]) - 1,Integer.parseInt(sd3[0]));
                    java.util.Date d3 = c.getTime();
                    date_of_comnce = new Date(d3.getTime());
                    System.out.println("date_of_comnce " + date_of_comnce);
                    ps.setDate(10, date_of_comnce);  
                } 
                if(expdate.equals(""))
                    ps.setNull(11,Types.DATE);
                else
                {
                    String[] sd3 =expdate.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd3[2]), Integer.parseInt(sd3[1]) - 1,Integer.parseInt(sd3[0]));
                    java.util.Date d3 = c.getTime();
                    expiry_date = new Date(d3.getTime());
                    System.out.println("expiry_date " + expiry_date);
                    ps.setDate(11, expiry_date);  
                } 
                ps.setDouble(12,ins_amount);
                ps.setDouble(13, pre_amount);
                ps.setDouble(14, other_charges);
                if(rendate.equals(""))
                    ps.setNull(15,Types.DATE);
                else
                {
                    String[] sd3 =rendate.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd3[2]), Integer.parseInt(sd3[1]) - 1,Integer.parseInt(sd3[0]));
                    java.util.Date d3 = c.getTime();
                    renewal_date = new Date(d3.getTime());
                    System.out.println("agreementdate " + renewal_date);
                    ps.setDate(15, renewal_date);  
                } 
                
                ps.setString(16,iss_address);
                ps.setString(17, txtRemarks);
                
               
                ps.setString(18, update_user);
                ps.setTimestamp(19, ts);
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
            
            System.out.println("cmbFinancialYear " + cmbFinancialYear); 
         
            System.out.println("txtRemarks " + txtRemarks); 
         
            System.out.println("cmc_date " + Comn_Date); 
            System.out.println("expdate" + expdate); 
            System.out.println("agrdate " + rendate); 
            System.out.println("curdate " + curdate);
            System.out.println("cmbasset"+cmbasset);
            System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
            System.out.println("cmbOffice_code"+cmbOffice_code);
            System.out.println("update_user"+update_user);
            System.out.println("ts"+ts);
            try {
                ps =  con.prepareStatement("update FAS_INSURANCE_DETAILS set ENTER_DATE=?,INS_COMP_NAME=?,INS_TYPE=?,INS_FAVOUR_OF=?,POLICY_NO=?,DATE_COMNCE=?,EXPIRY_DATE=?,INS_AMOUNT=?,"+
                      "PREMIUM_AMOUNT=?,OTHER_CHARGES=?,RENEWAL_DATE=?,ISS_OFF_ADDRESS=?,REMARKS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and ASSET_CODE=? ");
               
                
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
                ps.setString(3,instype);
                ps.setString(4,insfavour);
                ps.setInt(5, policy_no);
                if(Comn_Date.equals(""))
                    ps.setNull(6,Types.DATE);
                else
                {
                    String[] sd3 =Comn_Date.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd3[2]), Integer.parseInt(sd3[1]) - 1,Integer.parseInt(sd3[0]));
                    java.util.Date d3 = c.getTime();
                    date_of_comnce = new Date(d3.getTime());
                    System.out.println("date_of_comnce " + date_of_comnce);
                    ps.setDate(6,date_of_comnce);  
                } 
                if(expdate.equals(""))
                    ps.setNull(7,Types.DATE);
                else
                {
                    String[] sd3 =expdate.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd3[2]), Integer.parseInt(sd3[1]) - 1,Integer.parseInt(sd3[0]));
                    java.util.Date d3 = c.getTime();
                    expiry_date = new Date(d3.getTime());
                    System.out.println("expiry_date " + expiry_date);
                    ps.setDate(7, expiry_date);  
                } 
                ps.setDouble(8,ins_amount);
                ps.setDouble(9, pre_amount);
                ps.setDouble(10, other_charges);
                if(rendate.equals(""))
                    ps.setNull(11,Types.DATE);
                else
                {
                    String[] sd3 =rendate.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd3[2]), Integer.parseInt(sd3[1]) - 1,Integer.parseInt(sd3[0]));
                    java.util.Date d3 = c.getTime();
                    renewal_date = new Date(d3.getTime());
                    System.out.println("agreementdate " + renewal_date);
                    ps.setDate(11, renewal_date);  
                } 
                
                ps.setString(12, iss_address);
                ps.setString(13, txtRemarks);
                
               
                ps.setString(14, update_user);
                ps.setTimestamp(15, ts);
               
                ps.setInt(16, cmbAcc_UnitCode);
                ps.setInt(17, cmbOffice_code);
                
                ps.setString(18, cmbFinancialYear);
                ps.setInt(19, cmbasset);
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
            System.out.println(cmbAcc_UnitCode);
            System.out.println(cmbOffice_code);
            System.out.println(cmbasset);
            System.out.println(cmbFinancialYear);
          
            
            try {
                ps = con.prepareStatement("delete from  FAS_INSURANCE_DETAILS " + 
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
