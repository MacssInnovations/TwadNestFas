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

public class Assets_Rigs_Vehicles extends HttpServlet {
    private static final String CONTENT_TYPE = 
        "text/xml; charset=windows-1252";

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, 
                                                           IOException {
       System.out.println("inside servelet");
        HttpSession session = request.getSession(false);
     //   Connection connection = null;
        //System.out.println(max_branch_id);
       // int max_branch_id = 0;
        //System.out.println(max_branch_id);
        //String branch_city = "";
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

        int cmbassetrigs = 0, cmbassetvehicle = 0;
        int cmbAcc_UnitCode = 0,cmbOffice_code=0;
    //    int count=0;
       
        String financial_year="";
       
        Calendar c;
        double Amc_Amount=0; 
          int recflag=0;

        
        String update_user = (String)session.getAttribute("UserId");
        long l = System.currentTimeMillis();
        Timestamp ts = new Timestamp(l);

        try {
        	cmbassetrigs = Integer.parseInt(request.getParameter("cmbassetrigs"));
        	
        } catch (Exception e) {
            System.out.println("Exception to catch cmbassetrigs ");
        }
        try {
        	cmbassetvehicle = Integer.parseInt(request.getParameter("cmbassetvehicle"));
        	
        } catch (Exception e) {
            System.out.println("Exception to catch cmbassetvehicle ");
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
       
        
        System.out.println("cmbassetvehicle"+cmbassetvehicle);
     
        
       
      
        financial_year=request.getParameter("financial_year");
       
        
        if (strCommand.equalsIgnoreCase("Add")) {

          
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Add</command>";
            System.out.println("add");
            
            System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
            System.out.println("cmbOffice_code"+cmbOffice_code);
            System.out.println("update_user"+update_user);
            System.out.println("ts"+ts);
                            
                                  
         
              {
                
               try
               {
                           ps1=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,ASSET_CODE_OF_RIG," + 
                                                    "ASSET_CODE_OF_VEHICLE,FINANCIAL_YEAR from FAS_ASSETS_RIGS_AND_VEHICLES "+
                                                     " where ACCOUNTING_UNIT_ID=? AND ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and ASSET_CODE_OF_RIG=?");
                                  ps1.setInt(1,cmbAcc_UnitCode);
                                  ps1.setInt(2, cmbOffice_code);
                                  ps1.setString(3,financial_year);
                                  ps1.setInt(4,cmbassetrigs);
                           rs2=ps1.executeQuery();
                           if(!rs2.next())   

            try {

                ps =  con.prepareStatement("insert into FAS_ASSETS_RIGS_AND_VEHICLES(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,ASSET_CODE_OF_RIG," + 
                      "ASSET_CODE_OF_VEHICLE,FINANCIAL_YEAR,UPDATED_BY_USER_ID,UPDATED_DATE,STATUS) values(?,?,?,?,?,?,?,?)");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, cmbassetrigs);
                ps.setInt(4, cmbassetvehicle);
                ps.setString(5,financial_year);
                ps.setString(6, update_user);
                ps.setTimestamp(7, ts);
                ps.setString(8, "L");
                
                ps.executeUpdate();
                xml = xml + "<flag>success</flag>";
                System.out.println("here is ok");
             

            } catch (Exception e) {
                System.out.println("ggggggg----> " + e);
                xml = xml + "<flag>failure</flag>";
            }
               else
               {
                   System.out.println("This is Else Loop");
                   xml=xml+"<flag>AlreadyExist</flag>";
               }
               
              // xml=xml+"</response>";
               }catch(Exception e){
               System.out.println("Exception in select:"+e);
               }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
           }
        } else if (strCommand.equalsIgnoreCase("Update")) {
        	
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Update</command>";
            System.out.println("inside update command");
            System.out.println("cmbassetrigs"+cmbassetrigs);
            try {
                ps =  con.prepareStatement("update FAS_ASSETS_RIGS_AND_VEHICLES set ASSET_CODE_OF_RIG=?,ASSET_CODE_OF_VEHICLE=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?"+
                                           " where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
               
                
               
                ps.setInt(1,cmbassetrigs);
                ps.setInt(2, cmbassetvehicle);
                ps.setString(3, update_user);
                ps.setTimestamp(4, ts);
                ps.setInt(5, cmbAcc_UnitCode);
                ps.setInt(6, cmbOffice_code);
                ps.setString(7, financial_year);
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
            int assetRig = Integer.parseInt(request.getParameter("assetRig"));
            xml = "<response><command>Delete</command>";
            try {
                ps = con.prepareStatement("update FAS_ASSETS_RIGS_AND_VEHICLES set STATUS='C'" + 
                      " where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and ASSET_CODE_OF_RIG=?");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setString(3, financial_year);
                ps.setInt(4, assetRig);
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
