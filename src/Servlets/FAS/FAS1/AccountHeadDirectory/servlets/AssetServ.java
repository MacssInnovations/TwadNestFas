package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ResourceBundle;

public class AssetServ extends HttpServlet 
{
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
  
       
    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
        
             
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
        response.setContentType(CONTENT_TYPE);
            PrintWriter out = response.getWriter();
            
             Connection con=null;
             ResultSet rs=null;
             PreparedStatement ps=null;
             ResultSet rs1=null;
             PreparedStatement ps1=null;
             ResultSet rss=null;
             PreparedStatement pss=null;    
            HttpSession session=request.getSession(false);
                     try
                    {
                      
                        if(session==null)
                        {
                            System.out.println(request.getContextPath()+"/index.jsp");
                            response.sendRedirect(request.getContextPath()+"/index.jsp");
                            return;
                        }
                        System.out.println(session);
                            
                    }catch(Exception e)
                    {
                    System.out.println("Redirect Error :"+e);
                    }
                    
            
                 try
                   {
                   
                        ResourceBundle rb=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                        String ConnectionString="";

                        String strDriver=rb.getString("Config.DATA_BASE_DRIVER");
                        String strdsn=rb.getString("Config.DSN");
                        String strhostname=rb.getString("Config.HOST_NAME");
                        String strportno=rb.getString("Config.PORT_NUMBER");
                        String strsid=rb.getString("Config.SID");
                        String strdbusername=rb.getString("Config.USER_NAME");
                        String strdbpassword=rb.getString("Config.PASSWORD");

                        //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                        ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
                        Class.forName(strDriver.trim());
                        con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
                   }
                   catch(Exception e)
                   {
                     System.out.println("Exception in connection...."+e);
                   }
                   
        int Acc_UnitCode=0;
        int acOffId=0;
        int ClasAss=0;
        int AssCode=0;
        String AliasCode="";
        String Owner="";
        String DenName="";
        String DesAsset="";
        int PurYear=0;
        int PurMonth=0;
        String xml="";
        String strCommand="";
        String Fuel="";
        int Office=0;
        String FinYear="";
        double OrigCost=0;
        double CurrVal=0;
        String Rem="";
        int strAsscode=0;  
        String strDate="";
        String userId="x";
            String status="";
        float depRate=0;
            int b=0;
            String asstypecode="";
            String strasonDate="";
        response.setContentType("text/xml");
        response.setHeader("Cache-Control","no-cache");
            String update_user=(String)session.getAttribute("UserId");
            long l=System.currentTimeMillis();
            Timestamp ts=new Timestamp(l);
        
        try {
        
            strCommand=request.getParameter("Command");
            System.out.println("assign....."+strCommand);
            strAsscode=Integer.parseInt(request.getParameter("txtAssCode"));
            System.out.println("assign....."+strAsscode);
        }
        catch(Exception ae) {
            System.out.println("first exception...."+ae);
        }
            try
            {
            Acc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            }
            
            catch(Exception e) {
                System.out.println("Exception in assigning..."+e);
            }
            System.out.println("assign acc id....."+Acc_UnitCode);
            try
            {
            acOffId=Integer.parseInt(request.getParameter("comOffCode"));
            }
            
            catch(Exception e) {
                System.out.println("Exception in assigning..."+e);
            }
            System.out.println("assign acc for office id...."+acOffId);
            //strsuppcode=Integer.parseInt(request.getParameter("txtSuppId"));
           // System.out.println("assign....."+strsuppcode);
            try
            {
            ClasAss=Integer.parseInt(request.getParameter("comClasAss"));
            }           
            catch(Exception e) {
                System.out.println("Exception in assigning..."+e);
            }
            System.out.println("assign classification of assst...."+ClasAss);
            //AssCode=Integer.parseInt(request.getParameter("txtAssCode"));
          //  System.out.println("assign....."+AssCode);
            try{
                AliasCode=request.getParameter("txtAliasCode");    
            }
            catch(Exception e) {
                System.out.println("Exception in assigning..."+e);
            }
            System.out.println("assign alias code....."+AliasCode);
            Owner=request.getParameter("comOwner");
            System.out.println("assign owner code....."+Owner);
            FinYear=request.getParameter("txtFinYear");
            System.out.println("finicial year...."+FinYear);
            DenName=request.getParameter("txtDenName");
            System.out.println("name...."+DenName);
            DesAsset=request.getParameter("txtDesAsset");
            System.out.println("descriprtion of asset....."+DesAsset);
            status=request.getParameter("status");

            try{
                PurYear=Integer.parseInt(request.getParameter("txtPurchaseYear"));    
            }
            catch(Exception e) {
                System.out.println("exception"+e);
            }
            System.out.println("assign purchase yer...."+PurYear);
            
         
            
            try{
                PurMonth=Integer.parseInt(request.getParameter("txtPurchaseMonth"));
            }
            catch(Exception e) {
                System.out.println("Exception in assigning..."+e);
            }
            
            System.out.println("assign purchase month"+PurMonth);
            Fuel=request.getParameter("comFuel");
            System.out.println("assign fuel...."+Fuel);
            try{
                Office=Integer.parseInt(request.getParameter("txtlocation"));
            }
            catch(Exception e) {
                System.out.println("Exception in assigning..."+e);
            }
            
            System.out.println("assign office....."+Office);
            try
            {
            OrigCost=Double.parseDouble(request.getParameter("txtOrigCost"));
            }
            catch(Exception e) {
                System.out.println("Exception in assigning..."+e);
            }
            System.out.println("assign original cost...."+OrigCost);
          try
           {
            CurrVal=Double.parseDouble(request.getParameter("txtCurrVal"));
            }
            catch(Exception e) {
                System.out.println("Exception in assigning..."+e);
            }
            System.out.println("assign of date value"+CurrVal);
            Rem=request.getParameter("txtRem");
            System.out.println("assign remarks...."+Rem);
            try
            {
            depRate=Float.parseFloat(request.getParameter("txtPercDep"));
            }
            catch(Exception e) {
                System.out.println("Exception in assigning..."+e);
            }
            System.out.println("assign precentage of dep"+depRate);
            strasonDate=request.getParameter("txtDate");
            System.out.println("assign date...."+strasonDate);
            
            System.out.println("assign of date value"+userId);
            
       
        
        
        
        if(strCommand.equalsIgnoreCase("Add"))
        {
            java.sql.Date date1=null;
            java.sql.Date date2=null;

            
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
             try
             {
               java.util.Date d1=dateFormat1.parse(strasonDate);
                 dateFormat1.applyPattern("yyyy-MM-dd");
               strasonDate=dateFormat1.format(d1);

             }catch(Exception e)
            {
             e.printStackTrace();
            }
            date1=Date.valueOf(strasonDate);
            System.out.println("date1 is...."+date1);
        
            xml="<response><command>Add</command>";
            System.out.println("inside add command");
            try {
                pss=con.prepareStatement("select ASSET_TYPE_CODE from COM_MST_ASSETS_CLASS where ASSET_CLASS_CODE=?");
                pss.setInt(1,ClasAss);
                rss=pss.executeQuery();
                while(rss.next()) {
                    asstypecode=rss.getString("ASSET_TYPE_CODE");
                    System.out.println("Asset type code is*****************"+asstypecode);
                }
            }
            catch(Exception que) {
                System.out.println("exception in fetching aset type code");
            }
          
             try {
                 ps1=con.prepareStatement("SELECT MAX(ASSET_CODE) AS b FROM COM_MST_ASSETS_SL");
                 rs1=ps1.executeQuery();
                 if(rs1.next())
                 {
                            System.out.println("this i sinside the while loop");
                             b=rs1.getInt("b");
                             b=b+1;
                             System.out.println("b is "+b);
                       
                 }
                 else
                 b=1;
             }
             catch(Exception e) {
                 System.out.println("catch in x...."+e);
                 
             }
             
            strAsscode=b;
            System.out.println("assetttttttt codeeeeeeee is"+strAsscode);
            
            
            try {
                ps=con.prepareStatement("insert into COM_MST_ASSETS_SL (ASSET_CODE,ASSET_TYPE_CODE,ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,ASSET_CLASS_CODE,ALIAS_CODE,OWNER_CODE,DONATING_AGENCY_NAME,ASSET_DESCRIPTION,YEAR_OF_PURCHASE,MONTH_OF_PURCHASE,FUEL_TYPE_USED,LOCATION_CODE_OF_VEHICLE_INUSE,ORIGINAL_COST,CURRENT_VALUE,REMARKS,DEPRECIATION_RATE,AS_ON_DATE,UPDATED_BY_USER_ID,UPDATED_DATE,STATUS) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
                System.out.println(ps);
                ps.setInt(1,strAsscode);
                ps.setString(2,asstypecode);
                ps.setInt(3,Acc_UnitCode);
                ps.setInt(4,acOffId);
                ps.setString(5,FinYear);
                ps.setInt(6,ClasAss);
                ps.setString(7,AliasCode);
                ps.setString(8,Owner);
                ps.setString(9,DenName);
                ps.setString(10,DesAsset);
                ps.setInt(11,PurYear);
                ps.setInt(12,PurMonth);
                ps.setString(13,Fuel);
                ps.setInt(14,Office);
                ps.setDouble(15,OrigCost);
                ps.setDouble(16,CurrVal);
                ps.setString(17,Rem);
                ps.setFloat(18,depRate);
                ps.setDate(19,date1);
                ps.setString(20,update_user);
                ps.setTimestamp(21,ts);
                ps.setString(22,status);
                ps.executeUpdate();
                xml=xml+"<flag>success</flag><strAsscode>"+strAsscode+"</strAsscode>";
               
               
            }
            catch(Exception e) {
            
                 System.out.println("catch. in  adding...."+e);
                xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
        else if(strCommand.equalsIgnoreCase("load_AssetClassification")) 
        {
            xml="<response><command>load_AssetClassification</command>";
            String txtAssTypeCode="";
            txtAssTypeCode=request.getParameter("txtAssTypeCode");
            System.out.println("assign...."+txtAssTypeCode);
           try 
           {
            pss=con.prepareStatement("select ASSET_CLASS_CODE,ASSET_CLASS_DESC from COM_MST_ASSETS_CLASS where ASSET_TYPE_CODE=?");
            pss.setString(1,txtAssTypeCode);
            rss=pss.executeQuery();
            int j=0;
            while(rss.next()) 
            {
                xml=xml+"<AssClassCode>"+rss.getInt("ASSET_CLASS_CODE")+"</AssClassCode>";
                xml=xml+"<AssClassDesc>"+rss.getString("ASSET_CLASS_DESC")+"</AssClassDesc>";
                j++;
            }
            if(j==0)
            {
                xml=xml+"<flag>failure</flag>";
            }
            else
            {
                xml=xml+"<flag>success</flag>";
            }
           }
            catch(Exception que) 
            {
            xml=xml+"<flag>failure</flag>";
            System.out.println("exception in fetching asset type code.........");
            }
            xml=xml+"</response>";    
        }
        else if(strCommand.equalsIgnoreCase("Depreciation_assetType")) 
        {
            
                xml="<response><command>Depreciation_assetType</command>";
                ClasAss=Integer.parseInt(request.getParameter("comClasAss"));
                System.out.println("assign...."+ClasAss);
               String assType="";
               int i=0;
                int j=0;
                try {
                    
                    pss=con.prepareStatement("select DEPRECIATION_RATE from COM_MST_DEPRECIATION_RATES where ASSET_CLASS_CODE=? and FINANCIAL_YEAR=?");
                    pss.setInt(1,ClasAss);
                    pss.setString(2,FinYear);
                    rss=pss.executeQuery();
                    if(rss.next()) 
                    {
                        float dep=rss.getFloat("DEPRECIATION_RATE");
                         xml=xml+"<flag>success</flag><dep>"+dep+"</dep>";
                        i=i+1;
                        rss.close();
                        pss.close();
                    }
                    
                        if(i==0)
                        {
                            xml=xml+"<flag>failure</flag>";
                        }
                    }
                    
                    catch(Exception que) 
                    {
                    xml=xml+"<flag>failure</flag>";
                    System.out.println("exception in fetching aset type code");
                    }
                    /*
                        try 
                        {
                            pss=con.prepareStatement("select a.ASSET_TYPE_CODE,b.ASSET_TYPE_DESC from COM_MST_ASSETS_CLASS a,COM_MST_ASSETS_TYPE b where ASSET_CLASS_CODE=? and a.ASSET_TYPE_CODE=b.ASSET_TYPE_CODE");
                            pss.setInt(1,ClasAss);
                            rss=pss.executeQuery();
                            if(rss.next()) 
                            {
                                assType=rss.getString("ASSET_TYPE_CODE");
                                String assTypeDesc=rss.getString("ASSET_TYPE_DESC");
                                xml=xml+"<flag1>success</flag1><assType>"+assType+"</assType><assTypeDesc>"+assTypeDesc+"</assTypeDesc>";
                                j=j+1;
                            }
                            if(j==0) 
                            {
                                xml=xml+"<flag1>failure</flag1>";
                            }
                        }
                        catch(Exception se) 
                        {
                            xml=xml+"<flag1>failure</flag1>";
                            System.out.println("exception in asset type code...."+se);
                        }
                    */
                xml=xml+"</response>";    
            }        
        
                    
       else if(strCommand.equalsIgnoreCase("Update"))
       {
            System.out.println("inside update command....Assigning start");
          
       
            java.sql.Date date1=null;
            java.sql.Date date2=null;

            
            SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
             try
             {
               java.util.Date d1=dateFormat1.parse(strasonDate);
                 dateFormat1.applyPattern("yyyy-MM-dd");
               strasonDate=dateFormat1.format(d1);

             }catch(Exception e)
            {
             e.printStackTrace();
            }
            date1=Date.valueOf(strasonDate);
            System.out.println("date1 is...."+date1);
       
       
       
       
       
        System.out.println("inside update command...");
            xml="<response><command>Update</command>";
            try {
                pss=con.prepareStatement("select ASSET_TYPE_CODE from COM_MST_ASSETS_CLASS where ASSET_CLASS_CODE=?");
                pss.setInt(1,ClasAss);
                rss=pss.executeQuery();
                while(rss.next()) {
                    asstypecode=rss.getString("ASSET_TYPE_CODE");
                    System.out.println("Asset type code is*****************"+asstypecode);
                }
                rss.close();
                pss.close();
            }
            catch(Exception que) {
                System.out.println("exception in fetching aset type code");
            }
            
            
            
            try {
                ps=con.prepareStatement("update COM_MST_ASSETS_SL set ASSET_TYPE_CODE=?,ASSET_CLASS_CODE=?," +
                                        "ALIAS_CODE=?,OWNER_CODE=?,DONATING_AGENCY_NAME=?," +
                                        "ASSET_DESCRIPTION=?,YEAR_OF_PURCHASE=?,MONTH_OF_PURCHASE=?,FUEL_TYPE_USED=?," +
                                        "LOCATION_CODE_OF_VEHICLE_INUSE=?,ORIGINAL_COST=?,CURRENT_VALUE=?,REMARKS=?,UPDATED_BY_USER_ID=?," +
                                        "DEPRECIATION_RATE=?,AS_ON_DATE=?,UPDATED_DATE=?,STATUS=? where ASSET_CODE=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
                System.out.println("************************");
               
                ps.setString(1,asstypecode);
                System.out.println(asstypecode);
                
                ps.setInt(2,ClasAss);
                System.out.println(ClasAss);
                
                ps.setString(3,AliasCode);
                System.out.println(AliasCode);
                
                ps.setString(4,Owner);
                System.out.println(Owner);
                
                ps.setString(5,DenName);
                System.out.println(DenName);
                
                ps.setString(6,DesAsset);
                System.out.println(DesAsset);
                
                ps.setInt(7,PurYear);
                System.out.println(PurYear);
                
                ps.setInt(8,PurMonth);
                System.out.println(PurMonth);
                
                ps.setString(9,Fuel);
                System.out.println(Fuel);
                
                ps.setInt(10,Office);
                System.out.println(Office);
                
                ps.setDouble(11,OrigCost);
                System.out.println(OrigCost);
                
                ps.setDouble(12,CurrVal);
                System.out.println(CurrVal);
                
                ps.setString(13,Rem);
                System.out.println(Rem);
                
                ps.setString(14,update_user);
                System.out.println(update_user);
                
                ps.setFloat(15,depRate);
                System.out.println(depRate);
                
                ps.setDate(16,date1);
                System.out.println(date1);
                
                ps.setTimestamp(17,ts);
                System.out.println(strAsscode);
                ps.setString(18,status);
                ps.setInt(19,strAsscode);
                ps.setInt(20,Acc_UnitCode);
                ps.setInt(21,acOffId);
                ps.setString(22,FinYear);
               
                System.out.println(acOffId);
                
                
                System.out.println(FinYear);
                
                
                System.out.println("************************");
                
                ps.executeUpdate();
                xml=xml+"<flag>success</flag>";
            }
            
            catch(Exception e)
            {
                System.out.println("catch...."+e);
                xml=xml+"<flag>failure</flag>";
            }
            
            xml=xml+"</response>";
        }
        
        
        
        
        else if(strCommand.equalsIgnoreCase("Cancel"))
        {
            xml="<response><command>Delete</command>";
            try {
                ps=con.prepareStatement("update COM_MST_ASSETS_SL set STATUS=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=? where ASSET_CODE=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
                ps.setString(1,"C");
                ps.setString(2,update_user);
                ps.setTimestamp(3,ts);
                ps.setInt(4,strAsscode);
                ps.setInt(5,Acc_UnitCode);
                ps.setInt(6,acOffId);
                ps.setString(7,FinYear);
                ps.executeUpdate();
                xml=xml+"<flag>success</flag><assId>"+strAsscode+"</assId>";
            }
            catch(Exception e) {
                System.out.println("catch...."+e);
                xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
            else if(strCommand.equalsIgnoreCase("office"))
            {
                String CONTENT_TYPE = "text/xml; charset=windows-1252";
                response.setContentType(CONTENT_TYPE);
                xml="<response><command>office</command>";
                try {
                    int oid=0;
                    String oname="";
                    try{oid=Integer.parseInt(request.getParameter("oid"));}catch(Exception e){}
                    ps=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?" );
                    ps.setInt(1,oid);
                    rs=ps.executeQuery();
                    if(rs.next()) 
                      {
                      xml=xml+"<flag>success</flag><oid>"+oid+"</oid><oname>"+rs.getString("OFFICE_NAME")+"</oname>";
                      }
                    else
                      xml=xml+"<flag>failure</flag><oid>"+oid+"</oid>";
                }
                catch(Exception e)
                {
                    System.out.println("catch..HERE.in load office."+e);
                    xml=xml+"<flag>failure</flag>";
                }
                xml=xml+"</response>";
            }
            System.out.println("xml is:"+xml);
            out.write(xml);
            out.flush();
            out.close();
        
        }
        
        
    }

/*
 *         else if(strCommand.equalsIgnoreCase("owner")) {
            
            acOffId=Integer.parseInt(request.getParameter("comOffCode"));
            System.out.println("assign. office id..."+acOffId);
            String owner_desc="";
       
            try {
                
                pss=con.prepareStatement("select * from COM_MST_OWNERSHIP where ACCOUNTING_FOR_OFFICE_ID=?");
                pss.setInt(1,acOffId);
                rss=pss.executeQuery();
                while(rss.next()) {
                
                    int owner_code=rss.getInt("OWNER_CODE");
                     owner_desc=rss.getString("OWNER_DESC");
                     xml=xml+"<option><owner_code>"+owner_code+"</owner_code><owner_desc>"+owner_desc+"</owner_desc></option>";
                    
                }
                    xml="<select>"+xml+"</select>";
                }
                catch(Exception que) {
                xml=xml+"<response>failure</response>";
                System.out.println("exception in fetching aset type code");
                }
                
        }
        
        
            else if(strCommand.equalsIgnoreCase("fetchowner")) {
                xml="<response><command>fetchowner</command>";
                AssCode=Integer.parseInt(request.getParameter("txtAssCode"));
                System.out.println("assign...."+AssCode);
                String owner_desc="";
            
                try {
                    
                    pss=con.prepareStatement("select * from COM_MST_ASSETS_SL where ASSET_CODE=?");
                    pss.setInt(1,AssCode);
                    rss=pss.executeQuery();
                    while(rss.next()) {
                    
                        int owner_code=rss.getInt("OWNER_CODE");
                         
                         xml=xml+"<flag>success</flag><owner_code>"+owner_code+"</owner_code>";
                        
                    }
                        
                    }
                    catch(Exception que) {
                        xml=xml+"<flag>failure</flag>";
                    System.out.println("exception in fetching aset type code");
                    }
                xml=xml+"</response>";     
            }
 */