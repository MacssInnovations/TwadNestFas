package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ResourceBundle;

public class AssetListServ extends HttpServlet {
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
       
       
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
        response.setContentType(CONTENT_TYPE);
            PrintWriter out = response.getWriter();
             Connection con=null;
            
               
                 ResultSet rss=null;
                 PreparedStatement pss=null,ps=null;  
                 ResultSet rss1=null,rs=null;
                 PreparedStatement pss1=null;
                    try
                    {
                        HttpSession session=request.getSession(false);
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

                       // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
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
            String ClasAss="";
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
            String OrigCost="";
            String CurrVal="";
            String Rem="";
            int strAsscode=0;             
            int assType=0;
           float DepRate=0;
            String AsOndate="",Status="";
            String AssTypedesc="";
            String AssTypeCode="";
            //int Acc_UnitCode=0;
            
        int strAssetCode=0;
        response.setContentType("text/xml");
        response.setHeader("Cache-Control","no-cache");
       System.out.println(".........................Asset listing servlet started......................");
        
        
        
            strCommand=request.getParameter("command");
            System.out.println("assign....."+strCommand);
        try {
            Acc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            }
            catch(Exception e) {
                System.out.println("exception in account for office code...."+e);
            }
            
            try {
                acOffId=Integer.parseInt(request.getParameter("cmbOffice_code"));
                }
                catch(Exception e) {
                    System.out.println("exception in account for office code...."+e);
                }
                System.out.println("Acc_UnitCode"+Acc_UnitCode);
           System.out.println("office code.."+acOffId);
            FinYear=request.getParameter("txtFinYear");
            System.out.println("finicial year...."+FinYear);
        
        if(strCommand.equalsIgnoreCase("fetch")) 
        {
           
            
            try {
                strAssetCode=Integer.parseInt(request.getParameter("assetcode"));
                System.out.println("assign....."+strAssetCode);
            }
            catch(Exception ae) {
                System.out.println("first exception...."+ae);
            }
                
            xml="<response><command>fetch</command>";
            //String sql="insert into TEST_STATE values(?,?)";
            System.out.println("inside fetch command");
            try {
                pss=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,FINANCIAL_YEAR,ASSET_TYPE_CODE,ASSET_CLASS_CODE,ASSET_CODE,ALIAS_CODE,OWNER_CODE,DONATING_AGENCY_NAME,ASSET_DESCRIPTION,YEAR_OF_PURCHASE,MONTH_OF_PURCHASE,FUEL_TYPE_USED,LOCATION_CODE_OF_VEHICLE_INUSE,ORIGINAL_COST,CURRENT_VALUE,REMARKS,UPDATED_BY_USER_ID,UPDATED_DATE,to_char(AS_ON_DATE,'DD/MM/YYYY') as asOnDate,DEPRECIATION_RATE,STATUS  from COM_MST_ASSETS_SL where ASSET_CODE=? and  ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
                pss.setInt(1,strAssetCode);
                pss.setInt(2,Acc_UnitCode);
                pss.setInt(3,acOffId);
                pss.setString(4,FinYear); 
                rss=pss.executeQuery();
                if(rss.next()) {
                    DepRate=rss.getFloat("DEPRECIATION_RATE");
                    System.out.println("Asset depreciation rate is*****************"+DepRate);
                    
                    Acc_UnitCode=rss.getInt("ACCOUNTING_UNIT_ID");
                    System.out.println("accounting unit id is*****************"+Acc_UnitCode);
                    
                    acOffId=rss.getInt("ACCOUNTING_FOR_OFFICE_ID");
                    System.out.println("Accounting for office code is*****************"+acOffId);
                    
                    FinYear=rss.getString("FINANCIAL_YEAR");
                    System.out.println("financial year is*****************"+FinYear);
                    
                    AssTypeCode=rss.getString("ASSET_TYPE_CODE");
                    System.out.println("Asset type code is*****************"+AssTypeCode);
                    
                    ClasAss=rss.getString("ASSET_CLASS_CODE");
                    System.out.println("Asset class code is*****************"+ClasAss);
                    Status=rss.getString("STATUS");
                   
                    try {
                          
                        pss1=con.prepareStatement("select ASSET_TYPE_DESC from COM_MST_ASSETS_TYPE where ASSET_TYPE_CODE=?");
                        pss1.setString(1,AssTypeCode);
                        rss1=pss1.executeQuery();
                        while(rss1.next()) 
                        {
                            AssTypedesc=rss1.getString("ASSET_TYPE_DESC");
                            xml=xml+"<AssTypedesc>"+AssTypedesc+"</AssTypedesc>";
                        }
                       
                    }
                    catch(Exception qw) {
                        System.out.println("exception in fetching asset type description for asset code"+qw);
                    }
                    
                    AssCode=rss.getInt("ASSET_CODE");
                    System.out.println("Asset type ASSET_CODE is*****************"+AssCode);
                    
                    AliasCode=rss.getString("ALIAS_CODE");
                    System.out.println("Asset type ALIAS_CODE is*****************"+AliasCode);
                    
                    Owner=rss.getString("OWNER_CODE");
                    System.out.println("Asset type OWNER_CODE is*****************"+Owner);
                    
                    DenName=rss.getString("DONATING_AGENCY_NAME");
                    System.out.println("Asset type DONATING_AGENCY_NAME is*****************"+DenName);
                    
                    DesAsset=rss.getString("ASSET_DESCRIPTION");
                    System.out.println("Asset type ASSET_DESCRIPTION is*****************"+DesAsset);
                    
                    PurYear=rss.getInt("YEAR_OF_PURCHASE");
                    System.out.println("Asset YEAR_OF_PURCHASE code is*****************"+PurYear);
                    
                    PurMonth=rss.getInt("MONTH_OF_PURCHASE");
                    System.out.println("Asset type MONTH_OF_PURCHASE is*****************"+PurMonth);
                    
                    Fuel=rss.getString("FUEL_TYPE_USED");
                    System.out.println("Asset type code FUEL_TYPE_USED is*****************"+Fuel);
                    
                    Office=rss.getInt("LOCATION_CODE_OF_VEHICLE_INUSE");
                    System.out.println(rss.getInt("LOCATION_CODE_OF_VEHICLE_INUSE"));
                    System.out.println("Asset type LOCATION_CODE_OF_VEHICLE_INUSE is*****************"+Office);
                    
                    OrigCost=rss.getString("ORIGINAL_COST").trim();
                    System.out.println("Asset ORIGINAL_COST code is*****************"+OrigCost);
                    
                    CurrVal=rss.getString("CURRENT_VALUE").trim();
                    System.out.println("Asset CURRENT_VALUE code is*****************"+CurrVal);
                    
                    Rem=rss.getString("REMARKS");
                    System.out.println("Asset type REMARKS is*****************"+Rem);
                    
                    /*String[] sd;
                    sd=rss.getDate("AS_ON_DATE").toString().split("-");
                    AsOndate=sd[2]+"/"+sd[1]+"/"+sd[0];*/
                     AsOndate=rss.getString("asOnDate");
                    System.out.println("first date......."+AsOndate);
                }
                xml=xml+"<flag>success</flag><DepRate>"+DepRate+"</DepRate><AsOndate>"+AsOndate+"</AsOndate><Acc_UnitCode>"+Acc_UnitCode+"</Acc_UnitCode><FinYear>"+FinYear+"</FinYear><AssTypeCode>"+AssTypeCode+"</AssTypeCode><AssTypedesc>"+AssTypedesc+"</AssTypedesc><ClasAss>"+ClasAss+"</ClasAss><AssCode>"+AssCode+"</AssCode><AliasCode>"+AliasCode+"</AliasCode><Owner>"+Owner+"</Owner><DenName>"+DenName+"</DenName><DesAsset>"+DesAsset+"</DesAsset><PurYear>"+PurYear+"</PurYear><PurMonth>"+PurMonth+"</PurMonth><Fuel>"+Fuel+"</Fuel><Office>"+Office+"</Office><OrigCost>"+OrigCost+"</OrigCost><CurrVal>"+CurrVal+"</CurrVal><Rem>"+Rem+"</Rem><acOffId>"+acOffId+"</acOffId><status>"+Status+"</status>";
               // System.out.println("hi"+rss.getInt("LOCATION_CODE_OF_VEHICLE_INUSE"));
                if(rss.getInt("LOCATION_CODE_OF_VEHICLE_INUSE")!=0)
                {
                    System.out.println("here");
                    ps=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?" );
                    ps.setInt(1,rss.getInt("LOCATION_CODE_OF_VEHICLE_INUSE"));
                    rs=ps.executeQuery();
                    if(rs.next()) 
                      {
                      System.out.println("do");
                      xml=xml+"<oname>"+rs.getString("OFFICE_NAME")+"</oname>";
                      rs.close();
                      ps.close();
                      }
                    else
                      xml=xml+"<oname>null</oname>";
                } 
                else
                  xml=xml+"<oname>null</oname>";
                  
                rss.close();
                pss.close();
            }
            catch(Exception que) {
                xml=xml+"<flag>failure</flag>";
                System.out.println("exception in fetching aset details");
            }
            xml=xml+"</response>";
        }
        
           
            else if(strCommand.equalsIgnoreCase("AssetClass")) 
            {
                    xml=xml+"<response><command>AssetClass</command>";
                    String AssetClass=request.getParameter("comClasAss");
                       
                    int i=0;
                    try
                    {
                    pss=con.prepareStatement("select ASSET_CODE,ASSET_DESCRIPTION,MONTH_OF_PURCHASE,YEAR_OF_PURCHASE,LOCATION_CODE_OF_VEHICLE_INUSE,ORIGINAL_COST,CURRENT_VALUE,STATUS from COM_MST_ASSETS_SL where ASSET_CLASS_CODE=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
                    pss.setString(1,AssetClass);
                    pss.setInt(2,Acc_UnitCode);
                    pss.setInt(3,acOffId);
                    pss.setString(4,FinYear);    
                    rss=pss.executeQuery();
                    while(rss.next()) 
                    {
                        String AssetCode=rss.getString("ASSET_CODE");
                        String ASSET_DESC =rss.getString("ASSET_DESCRIPTION");
                        int month=rss.getInt("MONTH_OF_PURCHASE");
                        int year=rss.getInt("YEAR_OF_PURCHASE");
                        Status=rss.getString("STATUS");
                        String Location="";//rss.getInt("LOCATION_CODE_OF_VEHICLE_INUSE");
                        ps=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                        ps.setInt(1,rss.getInt("LOCATION_CODE_OF_VEHICLE_INUSE"));
                        rs=ps.executeQuery();
                        if(rs.next())
                            Location=rs.getString("OFFICE_NAME");
                        else
                            Location="null";
                        String Original_cost=rss.getString("ORIGINAL_COST").trim();
                        String curr_val=rss.getString("CURRENT_VALUE").trim();
                        xml=xml+"<flag>success</flag><AssetCode>"+AssetCode+"</AssetCode><month>"+month+"</month><year>"+year+"</year><Location>"+Location+"</Location><status>"+Status+"</status>";
                        xml=xml+"<Original_cost>"+Original_cost+"</Original_cost><curr_val>"+curr_val+"</curr_val>";
                        xml=xml+"<asset_desc>"+ASSET_DESC+"</asset_desc>";
                        i=i+1;
                    }
                    if(i==0) {
                        xml=xml+"<flag>failure</flag>";
                    }
                    }
                    catch(Exception qe) 
                    {
                        xml=xml+"<flag>failure</flag>";
                    System.out.println("exception in fetching asset details depending upon asset class code...."+qe);
                    }
                        xml=xml+"</response>";   
            }
            
            ///////////////////depending upon ownership
            
            
            else if(strCommand.equalsIgnoreCase("ownership"))
            {
               
            
            xml=xml+"<response><command>AssetClass</command>";
            String owner=request.getParameter("comOwnerShip");
            int i=0;
            try
            {
            pss=con.prepareStatement("select ASSET_CODE,ASSET_DESCRIPTION,MONTH_OF_PURCHASE,YEAR_OF_PURCHASE,LOCATION_CODE_OF_VEHICLE_INUSE,ORIGINAL_COST,CURRENT_VALUE,STATUS from COM_MST_ASSETS_SL where OWNER_CODE=? and ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
          
                pss.setString(1,owner);
                pss.setInt(2,Acc_UnitCode);
                pss.setInt(3,acOffId);
                pss.setString(4,FinYear);   
                
            rss=pss.executeQuery();
            while(rss.next())
            {
                String AssetCode=rss.getString("ASSET_CODE");
                String ASSET_DESC =rss.getString("ASSET_DESCRIPTION");
                Status=rss.getString("STATUS");
                int month=rss.getInt("MONTH_OF_PURCHASE");
                int year=rss.getInt("YEAR_OF_PURCHASE");
                String Location="";//rss.getInt("LOCATION_CODE_OF_VEHICLE_INUSE");
                ps=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                ps.setInt(1,rss.getInt("LOCATION_CODE_OF_VEHICLE_INUSE"));
                rs=ps.executeQuery();
                if(rs.next())
                    Location=rs.getString("OFFICE_NAME");
                else
                    Location="null";
                String Original_cost=rss.getString("ORIGINAL_COST").trim();
                String curr_val=rss.getString("CURRENT_VALUE").trim();
                xml=xml+"<flag>success</flag><AssetCode>"+AssetCode+"</AssetCode><month>"+month+"</month><year>"+year+"</year><Location>"+Location+"</Location><status>"+Status+"</status>";
                xml=xml+"<Original_cost>"+Original_cost+"</Original_cost><curr_val>"+curr_val+"</curr_val>";
                xml=xml+"<asset_desc>"+ASSET_DESC+"</asset_desc>";
                i=i+1;
            }
            if(i==0) {
                xml=xml+"<flag>failure</flag>";
            }
            }
            catch(Exception qe) {
                xml=xml+"<flag>failure</flag>";
            System.out.println("exception in fetching asset details depending upon asset class code...."+qe);
            }
                xml=xml+"</response>";   
            }
            
            ///////////////depending upon financial year
         /*   else if(strCommand.equalsIgnoreCase("FinanYr")) 
            {
                Acc_UnitCode=Integer.parseInt(request.getParameter("txtacID"));
                
            xml=xml+"<response><command>AssetClass</command>";
            String FinanYr=request.getParameter("txtFinanYr");
            int i=0;
            try
            {
            pss=con.prepareStatement("select * from COM_MST_ASSETS_SL where FINANCIAL_YEAR=? and ACCOUNTING_UNIT_ID=?");
            pss.setString(1,FinanYr);
            pss.setInt(2,Acc_UnitCode);
            rss=pss.executeQuery();
            while(rss.next()) {
                String AssetCode=rss.getString("ASSET_CODE");
                int month=rss.getInt("MONTH_OF_PURCHASE");
                int year=rss.getInt("YEAR_OF_PURCHASE");
                int Location=rss.getInt("LOCATION_CODE_OF_VEHICLE_INUSE");
                float Original_cost=rss.getDouble("ORIGINAL_COST");
                float curr_val=rss.getDouble("CURRENT_VALUE");
                xml=xml+"<flag>success</flag><AssetCode>"+AssetCode+"</AssetCode><month>"+month+"</month><year>"+year+"</year><Location>"+Location+"</Location>";
                xml=xml+"<Original_cost>"+Original_cost+"</Original_cost><curr_val>"+curr_val+"</curr_val>";
                i=i+1;
            }
            if(i==0) {
                xml=xml+"<flag>failure</flag>";
            }
            }
            catch(Exception qe) {
                xml=xml+"<flag>failure</flag>";
            System.out.println("exception in fetching asset details depending upon asset class code...."+qe);
            }
                xml=xml+"</response>";   
            }
            
            */
            ///////ALL............................
             
             else if(strCommand.equalsIgnoreCase("All")) 
             {
             xml=xml+"<response><command>AssetClass</command>";
               
             int i=0;
             try
             {
             pss=con.prepareStatement("select ASSET_CODE,ASSET_DESCRIPTION,MONTH_OF_PURCHASE,YEAR_OF_PURCHASE,LOCATION_CODE_OF_VEHICLE_INUSE,ORIGINAL_COST,CURRENT_VALUE,STATUS from COM_MST_ASSETS_SL where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=?");
             pss.setInt(1,Acc_UnitCode);
             pss.setInt(2,acOffId);
             pss.setString(3,FinYear);   
             rss=pss.executeQuery();
             while(rss.next()) 
             {
                 String AssetCode=rss.getString("ASSET_CODE");
                 String ASSET_DESC =rss.getString("ASSET_DESCRIPTION");
                 int month=rss.getInt("MONTH_OF_PURCHASE");
                 int year=rss.getInt("YEAR_OF_PURCHASE");
                 Status=rss.getString("STATUS");
                 String Location="";//rss.getInt("LOCATION_CODE_OF_VEHICLE_INUSE");
                 System.out.println("here..1");
                 ps=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                 ps.setInt(1,rss.getInt("LOCATION_CODE_OF_VEHICLE_INUSE"));
                 rs=ps.executeQuery();
                 
                 if(rs.next())
                     Location=rs.getString("OFFICE_NAME");
                 else
                     Location="null";
                 System.out.println("here..2");                     
                 String Original_cost=rss.getString("ORIGINAL_COST").trim();
                 String curr_val=rss.getString("CURRENT_VALUE").trim();
                 xml=xml+"<flag>success</flag><AssetCode>"+AssetCode+"</AssetCode><month>"+month+"</month><year>"+year+"</year><Location>"+Location+"</Location><status>"+Status+"</status>";
                 xml=xml+"<Original_cost>"+Original_cost+"</Original_cost><curr_val>"+curr_val+"</curr_val>";
                 xml=xml+"<asset_desc>"+ASSET_DESC+"</asset_desc>";
                 i=i+1;
             }
             if(i==0) 
             {
                 xml=xml+"<flag>failure</flag>";
             }
             }
             catch(Exception qe) {
                 xml=xml+"<flag>failure</flag>";
             System.out.println("exception in fetching asset details depending upon asset class code...."+qe);
             }
                 xml=xml+"</response>";   
             }
            else if(strCommand.equalsIgnoreCase("AllOwnerShip")) 
            {
            xml=xml+"<response><command>AssetClass</command>";
              
            int i=0;
            try
            {
            pss=con.prepareStatement("select ASSET_CODE,ASSET_DESCRIPTION,MONTH_OF_PURCHASE,YEAR_OF_PURCHASE,LOCATION_CODE_OF_VEHICLE_INUSE,ORIGINAL_COST,CURRENT_VALUE,STATUS from COM_MST_ASSETS_SL where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and FINANCIAL_YEAR=? and OWNER_CODE in('B','H','D')");
            pss.setInt(1,Acc_UnitCode);
            pss.setInt(2,acOffId);
            pss.setString(3,FinYear);   
            rss=pss.executeQuery();
            while(rss.next()) 
            {
                String AssetCode=rss.getString("ASSET_CODE");
                String ASSET_DESC =rss.getString("ASSET_DESCRIPTION");
                Status=rss.getString("STATUS");
                int month=rss.getInt("MONTH_OF_PURCHASE");
                int year=rss.getInt("YEAR_OF_PURCHASE");
                String Location="";//rss.getInt("LOCATION_CODE_OF_VEHICLE_INUSE");
                System.out.println("here..1");
                ps=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?");
                ps.setInt(1,rss.getInt("LOCATION_CODE_OF_VEHICLE_INUSE"));
                rs=ps.executeQuery();
                
                if(rs.next())
                    Location=rs.getString("OFFICE_NAME");
                else
                    Location="null";
                System.out.println("here..2");                     
                String Original_cost=rss.getString("ORIGINAL_COST").trim();
                String curr_val=rss.getString("CURRENT_VALUE").trim();
                xml=xml+"<flag>success</flag><AssetCode>"+AssetCode+"</AssetCode><month>"+month+"</month><year>"+year+"</year><Location>"+Location+"</Location><status>"+Status+"</status>";
                xml=xml+"<Original_cost>"+Original_cost+"</Original_cost><curr_val>"+curr_val+"</curr_val>";
                xml=xml+"<asset_desc>"+ASSET_DESC+"</asset_desc>";
                i=i+1;
            }
            if(i==0) 
            {
                xml=xml+"<flag>failure</flag>";
            }
            }
            catch(Exception qe) {
                xml=xml+"<flag>failure</flag>";
            System.out.println("exception in fetching asset details depending upon asset class code...."+qe);
            }
                xml=xml+"</response>";   
            }
            
            
            
            
            
            System.out.println("xml is:"+xml);
            out.write(xml);
            out.flush();
            out.close();
        
        }
        
        
    }

