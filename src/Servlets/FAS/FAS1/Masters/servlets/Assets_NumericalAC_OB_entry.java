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

public class Assets_NumericalAC_OB_entry extends HttpServlet {
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
        String xml = "";
        int cmbasset = 0, cmbmajorclass = 0;
        int cmbAcc_UnitCode = 0,cmbOffice_code=0,assetcode=0;
    //    int count=0;
        String txtOffice_Name = "";
        String txtRemarks = "",financial_year="",aliascode="";
        String quantitydate="",txtcheck="",curdate="";
        Date txtdate_entry = null,quantity_date=null;
        Calendar c;
        double Amc_Amount=0; 
          int recflag=0;

        
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
        txtOffice_Name = request.getParameter("txtOffice_Name");
        txtcheck=request.getParameter("txtcheck");
        System.out.println("txtcheck===="+txtcheck);
        System.out.println("txtOffice_Name ==="+txtOffice_Name);
        System.out.println("cmbmajorclass==="+cmbmajorclass);
      //  txtOffice_Address1 = request.getParameter("txtOffice_Address1");
       
     //   cmbFinancialYear = request.getParameter("cmbFinancialYear");
       
        try {
        	aliascode = request.getParameter("txtAsset_alias");
        	
        } catch (Exception e) {
            System.out.println("Exception to catch txtAsset_alias id ");
        } 
       
        txtRemarks = request.getParameter("txtRemarks");
        financial_year=request.getParameter("financial_year");
        curdate=request.getParameter("txtdate_entry");
        quantitydate=request.getParameter("quantity_date");
        String[] sd = request.getParameter("txtdate_entry").split("/");
        c = 
new GregorianCalendar(Integer.parseInt(sd[2]), Integer.parseInt(sd[1]) - 1, 
                    Integer.parseInt(sd[0]));
        java.util.Date d = c.getTime();
        txtdate_entry = new Date(d.getTime());
        System.out.println("txtdate_entry ===" + txtdate_entry); 
        
        if (strCommand.equalsIgnoreCase("Add")) {

          
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
           
            xml = "<response><command>Add</command>";
            System.out.println("add");
            System.out.println("txtOffice_Name " + txtOffice_Name); 
            System.out.println("txtRemarks " + txtRemarks); 
            System.out.println("curdate " + curdate);
           
            System.out.println("cmbAcc_UnitCode"+cmbAcc_UnitCode);
            System.out.println("cmbOffice_code"+cmbOffice_code);
            System.out.println("update_user"+update_user);
            System.out.println("ts"+ts);
                               try 
                                        {
                                            String sqlsel="select decode(max(ASSET_CODE),null,0,max(ASSET_CODE))as ASSET_CODE from FAS_ASSETS_NUM_OB";    
                                            ps2=con.prepareStatement(sqlsel);
                                            System.out.println("max ASSET_CODE no..."+sqlsel);
                                            rs2=ps2.executeQuery();
                                            if(rs2.next())
                                            {
                                               assetcode=rs2.getInt("ASSET_CODE");
                                            }
                                               assetcode=assetcode+1;
                                                System.out.println("Maximum value of assetcode  is :"+assetcode);
                                                ps2.close();
                                                rs2.close();
                                        }
                                        catch(Exception e11)
                                        {
                                           System.out.println("Exception arised finding the maximum value **** :"+e11);
                                        } 
                               try 
                                        {
                                           String sqlselect="select * from FAS_ASSETS_NUM_OB where ASSET_CODE=?";
                                            ps2=con.prepareStatement(sqlselect);
                                            ps2.setInt(1,assetcode);
                                            rs2=ps2.executeQuery();
                                            if(rs2.next())
                                               {
                                                   recflag++;
                                               }
                                            ps2.close();
                                            
                                        }
                                       catch(Exception e11)
                                       {
                                          System.out.println("Exception arised finding duplicates **** :"+e11);
                                       }
                                   
                                   if(recflag>0) 
                                   {
                                      System.out.println("Record already exist"); 
                                      xml=xml+"<flag>record</flag>";
                                   }
          else 
              {


            try {

                ps =  con.prepareStatement("insert into FAS_ASSETS_NUM_OB(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,ASSET_CODE," + 
                      "DATE_OF_ENTRY,ASSET_MAJOR_CLASS_CODE,QTY_AVL_ASON_DATE,PHYSICAL_LOCATION_CODE,STATUS,REMARKS,"+
                      "UPDATED_BY_USER_ID,UPDATED_DATE,ALIAS_CODE,FINANCIAL_YEAR) values(?,?,?,?,?,?,?,?,?,?,?,?,?)");
                ps.setInt(1, cmbAcc_UnitCode);
                ps.setInt(2, cmbOffice_code);
                ps.setInt(3, assetcode);
                if(curdate.equals(""))
                    ps.setNull(4,Types.DATE);
                else
                {
                    String[] sd2 =curdate.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) - 1,Integer.parseInt(sd2[0]));
                    java.util.Date d2 = c.getTime();
                    txtdate_entry = new Date(d2.getTime());
                    System.out.println("date " + txtdate_entry);
                    ps.setDate(4, txtdate_entry);    
                }
                ps.setInt(5, cmbmajorclass);
                /*if(quantitydate.equals(""))
                    ps.setNull(6,Types.DATE);
                else
                {
                    String[] sd1 = quantitydate.split("/");
                    c = 
                    new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]) - 1,
                                Integer.parseInt(sd1[0]));
                    java.util.Date d1 = c.getTime();
                    quantity_date = new Date(d1.getTime());
                    System.out.println("quantity_date " + quantity_date); 
                    ps.setDate(6,quantity_date);
                }*/
                ps.setInt(6, Integer.parseInt(quantitydate));
                ps.setString(7, txtOffice_Name);
                ps.setString(8,txtcheck);
                ps.setString(9, txtRemarks);
                ps.setString(10, update_user);
                ps.setTimestamp(11, ts);
                ps.setString(12,aliascode);
                ps.setString(13,financial_year);
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
           }
        } 
        else if (strCommand.equalsIgnoreCase("Update")) 
        {
        	
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            
            xml = "<response>" ;
            xml=xml+"<command>Update</command>";
            System.out.println("inside update command");
            System.out.println("cmbasset===="+cmbasset);
            try {
                ps =  con.prepareStatement("update FAS_ASSETS_NUM_OB set DATE_OF_ENTRY=?,QTY_AVL_ASON_DATE=?,PHYSICAL_LOCATION_CODE=?,STATUS=?,REMARKS=?,"+
                                           "UPDATED_BY_USER_ID=?,UPDATED_DATE=?,ALIAS_CODE=?,ASSET_MAJOR_CLASS_CODE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and ASSET_CODE=? and FINANCIAL_YEAR=?");
               
                
                if(curdate.equals(""))
                    ps.setNull(1,Types.DATE);
                else
                {
                    String[] sd2 =curdate.split("/");
                    c = new GregorianCalendar(Integer.parseInt(sd2[2]), Integer.parseInt(sd2[1]) - 1,Integer.parseInt(sd2[0]));
                    java.util.Date d2 = c.getTime();
                    txtdate_entry = new Date(d2.getTime());
                    System.out.println("date " + txtdate_entry);
                    ps.setDate(1, txtdate_entry);    
                }
                /*if(quantitydate.equals(""))
                    ps.setNull(2,Types.DATE);
                else
                {
                    String[] sd1 = quantitydate.split("/");
                    c = 
                    new GregorianCalendar(Integer.parseInt(sd1[2]), Integer.parseInt(sd1[1]) - 1,
                                Integer.parseInt(sd1[0]));
                    java.util.Date d1 = c.getTime();
                    quantity_date = new Date(d1.getTime());
                    System.out.println("quantity_date " + quantity_date); 
                    ps.setDate(2,quantity_date);
                }*/
                ps.setInt(2, Integer.parseInt(quantitydate));
                ps.setString(3, txtOffice_Name);
                ps.setString(4,txtcheck);
                ps.setString(5, txtRemarks);
                ps.setString(6, update_user);
                ps.setTimestamp(7, ts);
                ps.setString(8,aliascode);
                ps.setInt(9, cmbmajorclass);
                ps.setInt(10, cmbAcc_UnitCode);
                ps.setInt(11, cmbOffice_code);
                ps.setInt(12, cmbasset);
                ps.setString(13,financial_year);
                ps.executeUpdate();
                xml = xml + "<flag1>success</flag1>";
                System.out.println("here is ok");
             
 
              }

            catch (Exception e) {
                e.printStackTrace();
                xml = xml + "<flag1>failure</flag1>";
            }
           
            xml = xml + "</response>";
            System.out.println(xml);
            out.println(xml);
        }  else if (strCommand.equalsIgnoreCase("Delete")) {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);
            
            xml = "<response><command>Delete</command>";

            try {
                ps = con.prepareStatement("delete from  FAS_ASSETS_NUM_OB " + 
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
        
     /*   if(strCommand.equalsIgnoreCase("loadAssetClass")) 
                            {   String xml = "";
                                xml=xml+"<command>loadAssetClass</command>";
                                try
                                    {             
                                        String sqlload="select ASSET_CLASS_CODE,ASSET_CLASS_DESC from COM_MST_ASSETS_CLASS order by asset_class_code";
                                        ps2 = con.prepareStatement(sqlload);
                                        rs2=ps2.executeQuery();
                                        
                                        while(rs2.next())
                                        {
                                            
                                            xml=xml+"<option><asset_class_code>"+rs2.getString("ASSET_CLASS_CODE")+"</asset_class_code>";
                                            xml=xml+"<asset_class_desc>"+rs2.getString("ASSET_CLASS_DESC")+"</asset_class_desc></option>";
                                            count++;
                                        }
                                        if(count>0)
                                        {
                                            xml=xml+"<flag>success</flag>"; 
                                        }
                                        else
                                        {
                                            xml=xml+"<flag>nodata</flag>";    
                                        }
                                         ps2.close();
                                         rs2.close();
                                     } //try close
                                      catch(Exception e)
                                      {
                                                        xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                                        System.out.println(e);
                                       }
                            }
                            else if(strCommand.equalsIgnoreCase("loadAssetmajClass")) 
                            {   String xml = "";
                                xml=xml+"<command>loadAssetmajClass</command>";
                                try
                                    {             
                                        int assetcode=Integer.parseInt(request.getParameter("assetcode1"));
                                        System.out.println("Asset code ......"+assetcode);
                                        String sqlload="select a.asset_class_code,b.asset_class_desc from" + 
                                        "   (select asset_class_code from com_mst_assets_sl where asset_code=?)a" + 
                                        "   left outer join" + 
                                        "   (select asset_class_code,asset_class_desc from com_mst_assets_class)b" + 
                                        "   on a.asset_class_code=b.asset_class_code";
                                        System.out.println("query ****"+sqlload);
                                        ps2 = con.prepareStatement(sqlload);
                                        ps2.setInt(1,assetcode);
                                        rs2=ps2.executeQuery();
                                        
                                        if(rs2.next())
                                        {
                                            xml=xml+"<asset_class_code>"+rs2.getString("ASSET_CLASS_CODE")+"</asset_class_code>";
                                            xml=xml+"<asset_class_desc>"+rs2.getString("ASSET_CLASS_DESC")+"</asset_class_desc>";
                                            count++;
                                        }
                                        if(count>0)
                                        {
                                            xml=xml+"<flag>success</flag>"; 
                                        }
                                        else
                                        {
                                            xml=xml+"<flag>nodata</flag>";    
                                        }
                                         ps2.close();
                                         rs2.close();
                                     } //try close
                                      catch(Exception e)
                                      {
                                                        xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                                        System.out.println(e);
                                       }
                            }
                            else if(strCommand.equalsIgnoreCase("loadAssetCode")) 
                            {   String xml = "";
                                xml=xml+"<command>loadAssetCode</command>";
                                System.out.println("cmbAcc_UnitCode"+cmbOffice_code);
                                System.out.println("cmbOffice_code"+cmbOffice_code);
                                try
                                {           
                                            String sqlload="select ASSET_CODE,asset_description from COM_MST_ASSETS_SL where accounting_unit_id=? and accounting_for_office_id=?" + 
                                            "and financial_year=?";
                                            ps2 = con.prepareStatement(sqlload);
                                            ps2.setInt(1,cmbAcc_UnitCode);
                                            ps2.setInt(2,cmbOffice_code);
                                            ps2.setString(3,financial_year);
                                            rs2=ps2.executeQuery();
                                            while(rs2.next())
                                            {
                                                xml=xml+"<option><asset_code>"+rs2.getString("ASSET_CODE")+"</asset_code>";
                                                xml=xml+"<asset_code_desc>"+rs2.getString("asset_description")+"</asset_code_desc></option>";
                                                count++;
                                            }
                                             if(count>0)
                                             {
                                                 xml=xml+"<flag>success</flag>"; 
                                             }
                                             else
                                             {
                                                 xml=xml+"<flag>nodata</flag>";    
                                             }
                                     ps2.close();
                                     rs2.close();
                                 } //try close
                                  catch(Exception e)
                                  {
                                                    xml=xml+"<flag>"+e.getMessage()+"</flag>";
                                                    System.out.println(e);
                                   }
                            }*/
    /*    if(strCommand.equalsIgnoreCase("Load_Asset_Code"))
        {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
            response.setContentType(CONTENT_TYPE);    
             String xml = "";
            xml="<response><command>Load_Asset_Code</command>";             
            int y=0;           
            System.out.println("insideloadcode");
            int majorclass=0;
            int subcode=0;
            
            try
              {
              majorclass=Integer.parseInt(request.getParameter("cmbmajorclass"));
              System.out.println("majorclass:"+majorclass);
               }
              catch(Exception que) {
                  System.out.println("Exception in assigning values in load command majorclass...."+que);
              }
        
        
                                           try
                                           {
                                           String subname="";
                                           ps=con.prepareStatement("select ASSET_CODE from FAS_ASSET_VAL_AC_DETAILS where ASSET_MAJOR_CLASS_CODE=? and ACCOUNTING_UNIT_ID=?");
                                           ps.setInt(1,majorclass); 
                                           ps.setInt(2,cmbAcc_UnitCode);
                                           rs=ps.executeQuery();
                                               if(rs.next())
                                               {
                                               xml=xml+"<cid>"+rs.getInt("ASSET_CODE")+
                                               "</cid><cname>"+rs.getInt("ASSET_CODE")+
                                               "</cname>";
                                               //y++;
                                              
                                                   xml=xml+"<flag>success</flag>";
                                               }
                                               else
                                                   xml=xml+"<flag>failure</flag>";
                                                   
                                               ps.close();
                                               rs.close();
                                               }
                                               
                                           
                                           catch(Exception e)
                                           {
                                               System.out.println("Finding subledgercode failed due to exception"+e);
                                               xml=xml+"<flag>failure</flag>";
                                           }                                           
            xml=xml+"</response>";           
            out.println(xml);    
            return;
         } */
    }
   
}
