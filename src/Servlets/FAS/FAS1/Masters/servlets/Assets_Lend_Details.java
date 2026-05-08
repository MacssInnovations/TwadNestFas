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

public class Assets_Lend_Details extends HttpServlet {
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
       // System.out.println(max_branch_id);
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

        int cmbasset = 0, cmbmajorclass = 0;
        int cmbAcc_UnitCode = 0,cmbOffice_code=0,agr_no=0;
        
        String txtOffice_Name = "", txtOffice_Address1 = "", cmbFinancialYear = "", txtreg_no = "";
        String txtRemarks = "",agrdate="";
        String txtmake="",phy_loc="",cubic="",tank="",road="",curdate="",amcfromdate="",amctodate="";
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
        	cmbmajorclass = Integer.parseInt(request.getParameter("cmbmajorclass"));
        	
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
        try{
        txtOffice_Name = request.getParameter("txtOffice_Name");}
        catch (Exception e) {
            System.out.println("Exception to catch txtOffice_Name");
        }
        try
        {
        txtOffice_Address1 = request.getParameter("txtOffice_Address1");
        }
        catch(Exception e){System.out.println("Exception to catch txtOffice_Address1");}
        try
        {
        cmbFinancialYear = request.getParameter("cmbFinancialYear");
        }
        catch(Exception e){System.out.println("Exception to catch cmbFinancialYear");}
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
       try{
        txtRemarks = request.getParameter("txtRemarks");}
       catch(Exception e){System.out.println("Exception in remarks***"+e);}
        try{phy_loc = request.getParameter("phy_loc");}catch(Exception e){System.out.println("Exception e**");}
        try{amcfromdate=request.getParameter("Amcfromdate");}catch(Exception e){System.out.println("Exception e++");}
        try{
        amctodate =request.getParameter("Amctodate");}catch(Exception e){System.out.println("Exception e--");}
        try{agrdate=request.getParameter("Agrdate");}catch(Exception e){System.out.println("Exception e1111");}
        try{curdate=request.getParameter("txt_date");}catch(Exception e){System.out.println("Exception e222");}
        try{
        String[] sd = request.getParameter("txt_date").split("/");
        
        c = 
new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1, 
                    Integer.parseInt(sd[0]));
        java.util.Date d = c.getTime();
        txt_date = new Date(d.getTime());
        }
        catch(Exception e){System.out.println("Exception e333");}
       // System.out.println("txt_date " + txt_date); 
        

        if (strCommand.equalsIgnoreCase("Add")) {

          
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            String xml = "";
            xml = "<response><command>Add</command>";
//            System.out.println("add");
//            System.out.println("txtOffice_Name " + txtOffice_Name); 
//            System.out.println("txtRemarks " + txtRemarks); 
//            System.out.println("curdate " + curdate);
//            System.out.println("cmbasset"+cmbasset);
//            System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
//            System.out.println("cmbOffice_code"+cmbOffice_code);
//            System.out.println("update_user"+update_user);
//            System.out.println("ts"+ts);

            try {

                ps =  con.prepareStatement("insert into FAS_ASSET_TEMP_LEND_OTHER(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,ASSET_CODE," + 
                      "DATE_OF_TRANSFER,ASSET_MAJOR_CLASS_CODE,PHY_LOCATION,TRANS_OFFICE,REASON_FOR_TRANSFER,"+
                      "UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?,?,?,?,?,?,?)");
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
                ps.setInt(5, cmbmajorclass);
                ps.setString(6,phy_loc);
                ps.setString(7, txtOffice_Name);
                ps.setString(8, txtRemarks);
                ps.setString(9, update_user);
                ps.setTimestamp(10, ts);
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
                ps =  con.prepareStatement("update FAS_ASSET_TEMP_LEND_OTHER set DATE_OF_TRANSFER=?,PHY_LOCATION=?,TRANS_OFFICE=?,REASON_FOR_TRANSFER=?,"+
                                           "UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and ASSET_CODE=? and ASSET_MAJOR_CLASS_CODE=?");
               
                
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
                ps.setString(3, txtOffice_Name);
                ps.setString(4, txtRemarks);
                ps.setString(5, update_user);
                ps.setTimestamp(6, ts);
                ps.setInt(7, cmbAcc_UnitCode);
                ps.setInt(8, cmbOffice_code);
                ps.setInt(9, cmbasset);
                ps.setInt(10, cmbmajorclass);
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
                ps = con.prepareStatement("delete from  FAS_ASSET_TEMP_LEND_OTHER " + 
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
        else if (strCommand.equalsIgnoreCase("loadassetcode")) {
        	//System.out.println("insideeeeeeeee");
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            int Acc_UnitCode=Integer.parseInt(request.getParameter("accunit_id"));
            int Office_code=Integer.parseInt(request.getParameter("accoff_id"));
            int Maj_class_code=Integer.parseInt(request.getParameter("majclasscode"));
            //System.out.println("tessssssssss"+Acc_UnitCode+"eeeeeeeee"+Office_code+"fffffffff"+Maj_class_code);
            String xml = "";
            xml = "<response><command>loadassetcode</command>";

            try {
                ps = con.prepareStatement("select asset_code,particulars as asset_code_desc from FAS_ASSET_VAL_AC_DETAILS"+
                							"	where accounting_unit_id =? and accounting_unit_office_id=?  "+
                                            " 	AND asset_major_class_code   =?");
                ps.setInt(1, Acc_UnitCode);
                ps.setInt(2, Office_code);
                ps.setInt(3, Maj_class_code);
  
                rs=ps.executeQuery();
                int count = 0;
                	
                    while(rs.next())
                    {
                        xml+="<option><assetid>"+rs.getInt("ASSET_CODE")+"</assetid>" +
                                         "<assetName><![CDATA["+rs.getString("asset_code_desc")+"]]></assetName></option>";
                    count++;
                    }
                        if (count == 0)
                            xml = xml + "<flag>nodata</flag>";
                    else
                        xml = xml + "<flag>success</flag>";
                    System.out.println("count  " + count);
             }
            catch (Exception e) {
                System.out.println("catch..HERE.in load head code." + e);
                xml = xml + "<flag>failure</flag>";
            }
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }
    }
}
