package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.text.SimpleDateFormat;
import javax.servlet.*;
import javax.servlet.http.*;
import java.util.ResourceBundle;

public class SupplierServ extends HttpServlet 
{
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    

    public void init(ServletConfig config) throws ServletException 
    {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
        Connection con=null;
        ResultSet rs=null;
        PreparedStatement ps=null;
        ResultSet rss=null;
        PreparedStatement pss=null;
        ResultSet rs1=null;
        PreparedStatement ps1=null;
                
                
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
             
            response.setContentType(CONTENT_TYPE);
            PrintWriter out = response.getWriter();
            HttpSession session=request.getSession(false);
            try
              {
                    
                    if(session==null)
                    {
                        System.out.println(request.getContextPath()+"/index.jsp");
                        response.sendRedirect(request.getContextPath()+"/index.jsp");
                    
                    }
                    System.out.println(session);
                        
                }catch(Exception e)
                {
                System.out.println("Redirect Error :"+e);
                }
                
        int accunitcode=0;
        int stroffnameren=0;
        int strsuppcode=0;
        String strsuppname="";
        String straddr="";
        String straddr1="";
        String straddr2="";
        String strphone="";
        String strfax="";
        String stremail="";
        String xml="";
        String strCommand="";
        String strDateReg="";
        String strDateLast="";
        String SuppAliasId="";
        int Pincode=0;
        int b=1;
        String status="";
        response.setContentType("text/xml");
        response.setHeader("Cache-Control","no-cache");
       
            String update_user=(String)session.getAttribute("UserId");
            long l=System.currentTimeMillis();
            Timestamp ts=new Timestamp(l);
            
        try {
        
            strCommand=request.getParameter("Command");
            System.out.println("assign....."+strCommand);
            strsuppcode=Integer.parseInt(request.getParameter("txtSuppId"));
            System.out.println("assign....."+strsuppcode);
        }
        catch(Exception ae) {
            System.out.println("first exception...."+ae);
        }
            try
            {
            accunitcode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));
            System.out.println("assign....."+accunitcode);
            stroffnameren=Integer.parseInt(request.getParameter("comOffId"));
            System.out.println("assign...."+stroffnameren);
            //strsuppcode=Integer.parseInt(request.getParameter("txtSuppId"));
           // System.out.println("assign....."+strsuppcode);
            strsuppname=request.getParameter("txtSuppName");
            System.out.println("assign...."+strsuppname);
            straddr=request.getParameter("txtaddr");
            System.out.println("assign....."+straddr);
            straddr1=request.getParameter("txtaddr2");
            System.out.println("assign addr1....."+straddr1);
            straddr2=request.getParameter("txtcity");
            System.out.println("assign addr2....."+straddr2);
            strphone=request.getParameter("txtPhone");
            System.out.println("assign...."+strphone);
            strfax=request.getParameter("txtFax");
            System.out.println("assign....."+strfax);
            stremail=request.getParameter("txtEmail");
            System.out.println("assign...."+stremail);
            strDateReg=request.getParameter("txtDateReg");
            System.out.println("assign of date value"+strDateReg);
            strDateLast=request.getParameter("txtDateLastSupply");
            System.out.println("assign...."+strDateLast);
            status=request.getParameter("status");

            try{ 
            Pincode=Integer.parseInt(request.getParameter("txtPincode"));
            }
            catch(Exception e) {
                System.out.println("Exception in assigning..."+e);
            }
            System.out.println("assign...."+Pincode);
            
            SuppAliasId=request.getParameter("txtSuppAliasId");
            System.out.println("assign...."+SuppAliasId);
            
            
        }
        
        catch(Exception e) {
            System.out.println("Exception in assigning..."+e);
        }
        
        try{
            strsuppcode=Integer.parseInt(request.getParameter("txtSuppId"));
             System.out.println("assign....."+strsuppcode);
            
        }
        catch(Exception je){
        System.out.println("error in the fetching of the supplier id " + je);
        }
        
        if(strCommand.equalsIgnoreCase("Add")) 
        {
            //to format the date of reg
            
                java.sql.Date date1=null;
               java.sql.Date date2=null;

             
             SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                 try
                 {
                   java.util.Date d1=dateFormat1.parse(strDateReg);
                     dateFormat1.applyPattern("yyyy-MM-dd");
                   strDateReg=dateFormat1.format(d1);
                     date1=Date.valueOf(strDateReg);
                     System.out.println("date1 is...."+date1);
                 }catch(Exception e)
               {
                 e.printStackTrace();
               }
               
                
            //to format the date of last supply
            if(!strDateLast.equalsIgnoreCase(""))
            {
                SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
                try
                {
                    java.util.Date d2=dateFormat2.parse(strDateLast);
                    dateFormat2.applyPattern("yyyy-MM-dd");
                    strDateLast=dateFormat2.format(d2);
                    date2=Date.valueOf(strDateLast);
                    System.out.println("date2 is...."+date2);
                }catch(Exception e)
                {
                     e.printStackTrace();
                }
               
            }
            xml="<response><command>Add</command>";
           
             try {
                 ps1=con.prepareStatement("SELECT MAX(SUPPLIER_ID) AS b FROM COM_SUPPLIER_SL_MST");
                 rs1=ps1.executeQuery();
                 if(rs1.next())
                 {
                            System.out.println("this i sinside the while loop");
                             b=rs1.getInt("b");
                             b=b+1;
                             System.out.println("b is "+b);
                       
                 }
                 rs1.close();
                 ps1.close();
                 
             }
             catch(Exception e) 
             {
                 System.out.println("catch in x...."+e);
             }
             
            strsuppcode=b;
            
            try {
                ps=con.prepareStatement("insert into COM_SUPPLIER_SL_MST (ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,SUPPLIER_ID,SUPPLIER_NAME,SUPPLIER_ADDRESS,SUPPLIER_PHONE,SUPPLIER_FAX,SUPPLIER_EMAIL_ID,DATE_OF_REGISTRATION,DATE_OF_LAST_SUPPLY,SUPPLIER_ADDRESS1,SUPPLIER_CITY,SUPPLIER_ALIAS_ID,PINCODE,UPDATED_BY_USER_ID,UPDATED_DATE,STATUS) values(?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?,?)");
               
                ps.setInt(1,accunitcode);
                ps.setInt(2,stroffnameren);
                ps.setInt(3,strsuppcode);
                ps.setString(4,strsuppname);
                ps.setString(5,straddr);
                ps.setString(6,strphone);
                ps.setString(7,strfax);
                ps.setString(8,stremail);
                ps.setDate(9,date1);
                ps.setDate(10,date2);
                ps.setString(11,straddr1);
                ps.setString(12,straddr2);
                ps.setString(13,SuppAliasId);
                ps.setInt(14,Pincode);
                ps.setString(15,update_user);
                ps.setTimestamp(16,ts);
                ps.setString(17,status);
                ps.executeUpdate();
                xml=xml+"<flag>success</flag><GenId>"+b+"</GenId>";
                ps.close();
               
            }
            
            catch(Exception e) {
            
                 System.out.println("catch. in  adding...."+e);
                xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
        
            else if(strCommand.equalsIgnoreCase("Update")) 
            {
                xml="<response><command>Update</command>";
                
                try {
                    
                    //to format the date of reg
                    
                        java.sql.Date date1=null;
                       java.sql.Date date2=null;

                     
                     SimpleDateFormat dateFormat1 = new SimpleDateFormat("dd/MM/yyyy");
                         try
                         {
                           java.util.Date d1=dateFormat1.parse(strDateReg);
                             dateFormat1.applyPattern("yyyy-MM-dd");
                           strDateReg=dateFormat1.format(d1);
                             date1=Date.valueOf(strDateReg);
                             System.out.println("date1 is...."+date1);
                         }catch(Exception e)
                       {
                         e.printStackTrace();
                       }
                      
                    //to format the date of last supply
                    
                     if(!strDateLast.equalsIgnoreCase(""))
                     {
                         SimpleDateFormat dateFormat2 = new SimpleDateFormat("dd/MM/yyyy");
                         try
                         {
                             java.util.Date d2=dateFormat2.parse(strDateLast);
                             dateFormat2.applyPattern("yyyy-MM-dd");
                             strDateLast=dateFormat2.format(d2);
                             date2=Date.valueOf(strDateLast);
                             System.out.println("date2 is...."+date2);
                         }catch(Exception e)
                         {
                              e.printStackTrace();
                         }
                        
                     }
                    System.out.println("here");
                    pss=con.prepareStatement("update COM_SUPPLIER_SL_MST set ACCOUNTING_FOR_OFFICE_ID=?,SUPPLIER_NAME=?,SUPPLIER_ADDRESS=?" +
                    ",SUPPLIER_PHONE=?,SUPPLIER_FAX=?,SUPPLIER_EMAIL_ID=?,DATE_OF_REGISTRATION=?,DATE_OF_LAST_SUPPLY=?,SUPPLIER_ADDRESS1=?," +
                    "SUPPLIER_CITY=?,PINCODE=?,SUPPLIER_ALIAS_ID=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,STATUS=? where SUPPLIER_ID=?");
                   
                    pss.setInt(1,stroffnameren);
                    pss.setString(2,strsuppname);
                    pss.setString(3,straddr);
                    pss.setString(4,strphone);
                    pss.setString(5,strfax);
                    pss.setString(6,stremail);
                    pss.setDate(7,date1);
                    pss.setDate(8,date2);
                    pss.setString(9,straddr1);
                    pss.setString(10,straddr2);
                    pss.setInt(11,Pincode);
                    pss.setString(12,SuppAliasId);
                    pss.setString(13,update_user);
                    pss.setTimestamp(14,ts);
                    pss.setString(15,status);
                    pss.setInt(16,strsuppcode);
                    pss.executeUpdate();
                    xml=xml+"<flag>success</flag>";
                }
                catch(Exception e) {
                    System.out.println("catch...."+e);
                    xml=xml+"<flag>failure</flag>";
                }
                xml=xml+"</response>";
            }
            
            
            
            
            else if(strCommand.equalsIgnoreCase("Cancel")) 
            {
                xml="<response><command>Cancel</command>";
                try {
                    ps=con.prepareStatement("update COM_SUPPLIER_SL_MST set STATUS='C' where SUPPLIER_ID=?");
                    ps.setInt(1,strsuppcode);
                    ps.executeUpdate();
                    xml=xml+"<flag>success</flag><scd>"+strsuppcode+"</scd>";
                }
                catch(Exception e) {
                    System.out.println("catch...."+e);
                    xml=xml+"<flag>failure</flag>";
                }
                xml=xml+"</response>";
            }
            
        else if(strCommand.equalsIgnoreCase("geneId")) 
        {
        System.out.println("Insiden Year ");
            String sxml="<response><command>geneId</command>";
             b=10;
            try {
                ps=con.prepareStatement("SELECT MAX(SUPPLIER_ID) AS b FROM COM_SUPPLIER_SL_MST");
                
                
                rs=ps.executeQuery();
                if(rs.next())
                {
                            b=rs.getInt("b");
                            System.out.println("b is "+b);
                            
                }
                rs.close();
                ps.close();
                 int j=b;
                System.out.println("b is"+j);
                
                if(j==0)
                {
                j=1;
                System.out.println("i...."+j);
                xml=sxml+"<flag>first</flag><j>"+j+"</j>";
                }
                else
                {
                    j=j+1;
                    System.out.println("i.."+j);
                    xml=sxml+"<flag>incremented</flag><j>"+j+"</j>";
                }
            }
            catch(Exception e) {
                System.out.println("catch in x...."+e);
                xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
            }
              
            
        else if(strCommand.equalsIgnoreCase("loadtab")) 
        {
            System.out.println("Insiden Year ");
            int accId=0;
            int offId=0;
            String dateReg="";
            String datelast="";
             java.sql.Date date1=null;
            java.sql.Date date2=null;
            String sxml="<response><command>loadtab</command>";
            b=0;
            try {
                ps=con.prepareStatement("SELECT * FROM COM_SUPPLIER_SL_MST where SUPPLIER_ID=?");
                ps.setInt(1,strsuppcode);
                
                rs=ps.executeQuery();
                while(rs.next())
                {
                         accId=rs.getInt("ACCOUNTING_UNIT_ID");
                    System.out.println("b is%%%%%%%%%%%%%%%%%%%%%"+accId);
                         offId=rs.getInt("ACCOUNTING_FOR_OFFICE_ID");
                    System.out.println("b is "+offId);
                    
                    SuppAliasId=rs.getString("SUPPLIER_ALIAS_ID");
                    System.out.println("supplier alias id is "+SuppAliasId);
                    
                    Pincode=rs.getInt("PINCODE");
                    System.out.println("pincode is "+Pincode);
                    stremail=rs.getString("SUPPLIER_EMAIL_ID");
                        //dateReg=rs.getDate("DATE_OF_REGISTRATION");
                    System.out.println("b is "+dateReg);
                        // datelast=rs.getDate("DATE_OF_LAST_SUPPLY");
                    System.out.println("b is%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%%"+datelast);
                    b=1;
                    //to format the date of reg
                    
                        
                     String[] sd;
                     sd=rs.getDate("DATE_OF_REGISTRATION").toString().split("-");
                     dateReg=sd[2]+"/"+sd[1]+"/"+sd[0];
                    System.out.println("first date..."+dateReg);
                    String[] sd1;
                    sd1=rs.getDate("DATE_OF_LAST_SUPPLY").toString().split("-");
                    datelast=sd1[2]+"/"+sd1[1]+"/"+sd1[0];
                    

                }
                 rs.close();
                 ps.close();
                if(b==0)
                {
                
                //System.out.println("i...."+j);
                xml=sxml+"<flag>failure</flag>";
                }
                else
                {
                    //System.out.println("i.."+j);
                    xml=sxml+"<flag>success</flag><stremail>"+stremail+"</stremail><SuppAliasId>"+SuppAliasId+"</SuppAliasId><Pincode>"+Pincode+"</Pincode><accId>"+accId+"</accId><offId>"+offId+"</offId><dateReg>"+dateReg+"</dateReg><datelast>"+datelast+"</datelast>";
                }
            }
            catch(Exception e) {
                System.out.println("catch in load table..."+e);
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

