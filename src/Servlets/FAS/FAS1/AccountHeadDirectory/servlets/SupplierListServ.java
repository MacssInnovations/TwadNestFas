package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;

import java.text.SimpleDateFormat;

import java.util.ResourceBundle;

import javax.servlet.*;
import javax.servlet.http.*;

public class SupplierListServ extends HttpServlet 
{
    
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
        PrintWriter out = response.getWriter();
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
        
       
        try
                {
                    HttpSession session=request.getSession(false);
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
        String strStart="";
        int stroffcode=0;
        int stroffnameren=0;
        int strsuppcode=0;
        String strsuppname="";
        String straddr="";
    String strphone="";
        String strfax="";
        String stremail="";
        String xml="";
        String sxml="";
        String strCommand="";
        String strchar="";
        String strAddr1="";
        String strCity="";
        String strDateReg="";
        String strDateLast="";
        String Status="";
            int b=0;
        
        response.setContentType("text/xml");
        response.setHeader("Cache-Control","no-cache");
        
        
        try {
        
            strCommand=request.getParameter("cmd");
            System.out.println("assign....."+strCommand);
            strchar=request.getParameter("scod");
            System.out.println("assign....."+strchar);
           
            strStart=strchar.toUpperCase();
            System.out.println("strStart...."+strStart);
        }
        catch(Exception ae) {
            System.out.println("first exception...."+ae);
        }
           
        if(strCommand.equalsIgnoreCase("list")) 
        {
            System.out.println("Inside List in listing... ");
            int a=0;
            int supplierID=0;
            int supplierName=0;
            String dateReg="";
            String datelast="";
             java.sql.Date date1=null;
            java.sql.Date date2=null;
            sxml="<response><command>list</command>";
            
            b=0;
            try {         
              int cmbAcc_UnitCode=0,cmbOffice_code=0;
                try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
                catch(NumberFormatException e){System.out.println("exception"+e );}
                System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
                
                try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
                catch(NumberFormatException e){System.out.println("exception"+e );}
                System.out.println("cmbOffice_code "+cmbOffice_code);
                
                ps=con.prepareStatement("SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,SUPPLIER_ID,SUPPLIER_NAME,SUPPLIER_ADDRESS,SUPPLIER_PHONE, SUPPLIER_FAX,SUPPLIER_EMAIL_ID ,to_char(DATE_OF_REGISTRATION,'DD/MM/YYYY') as DATE_OF_REG,to_char(DATE_OF_LAST_SUPPLY,'DD/MM/YYYY') as DATE_OF_LAST_SUP ,SUPPLIER_ADDRESS1 ,SUPPLIER_CITY, SUPPLIER_ALIAS_ID ,PINCODE  FROM COM_SUPPLIER_SL_MST where  ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and (SUPPLIER_NAME like ('"+strchar+"%') or SUPPLIER_NAME like ('"+strStart+"%')) order by SUPPLIER_ID");
              //  ps.setString(1,strchar);
                
                rs=ps.executeQuery();
                try
                {
                    xml=sxml+"<flag>success</flag>";
                while(rs.next())
                {
                    supplierID=rs.getInt("SUPPLIER_ID");
                    System.out.println("SUPPLIER_ID is%%%%%%%%%%%%%%%%%%%%%"+supplierID);
                    strsuppname=rs.getString("SUPPLIER_NAME");
                    System.out.println("SUPPLIER_NAME is "+strsuppname);
                    straddr=rs.getString("SUPPLIER_ADDRESS");
                    System.out.println("SUPPLIER_ADDRESS is "+straddr);
                    stremail=rs.getString("SUPPLIER_EMAIL_ID");
                    System.out.println("SUPPLIER_EMAIL_ID is "+stremail);
                    strphone=rs.getString("SUPPLIER_PHONE");
                    System.out.println("SUPPLIER_PHONE is "+strphone);
                    strfax=rs.getString("SUPPLIER_FAX");
                    System.out.println("SUPPLIER_FAX is********** "+strfax);
                    strAddr1=rs.getString("SUPPLIER_ADDRESS1");
                    System.out.println("SUPPLIER_ADDRESS1 is********** "+strfax);
                    strCity=rs.getString("SUPPLIER_CITY");
                    System.out.println("SUPPLIER_CITY is********** "+strfax);
                    Status=rs.getString("STATUS");
                    xml=xml+"<supplierID>"+supplierID+"</supplierID><supplierName>"+strsuppname+"</supplierName><supAddr>"+
                            straddr+"</supAddr><supPhone>"+strphone+"</supPhone><supFax>"+strfax+"</supFax><supemail>"+stremail+
                            "</supemail><supaddr1>"+strAddr1+"</supaddr1><supcity>"+strCity+
                            "</supcity><DOReg>"+rs.getString("DATE_OF_REG")+"</DOReg><DOsupply>"+rs.getString("DATE_OF_LAST_SUP")+"</DOsupply><status>"+Status+"</status>";
                    b++;
                    
                }
                }
                catch(Exception ae) {
                    System.out.println("Exception in while loop..."+ae);
                }
                                 
                if(b==0)
                {
                
                System.out.println("i...."+b);
                xml=sxml+"<flag>failure</flag>";
                }
                else
                {
                    System.out.println("i.."+b);
                   // xml=sxml+"<flag>success</flag><supplierID>"+supplierID+"</supplierID><supplierName>"+strsuppname+"</supplierName><supAddr>"+straddr+"</supAddr><supPhone>"+strphone+"</supPhone><supFax>"+strfax+"</supFax><supemail>"+stremail+"</supemail>";
                }
            
            }
            catch(Exception e) 
            {
                    xml=sxml+"<flag>failure</flag>";
                System.out.println("catch in load table..."+e);
                }
                            xml=xml+"</response>";
            }
        else if(strCommand.equalsIgnoreCase("listedit")) 
        {
            System.out.println("Inside List in listing... ");
            int a=0;
            int supplierID=0;
            int supplierName=0;
            String dateReg="";
            String datelast="";
             java.sql.Date date1=null;
            java.sql.Date date2=null;
            sxml="<response><command>listedit</command>";
            
            b=0;
            try {         
              int cmbAcc_UnitCode=0,cmbOffice_code=0;
                try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
                catch(NumberFormatException e){System.out.println("exception"+e );}
                System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
                
                try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
                catch(NumberFormatException e){System.out.println("exception"+e );}
                System.out.println("cmbOffice_code "+cmbOffice_code);
                
                try{supplierID=Integer.parseInt(strchar);}
                catch(NumberFormatException e){System.out.println("exception"+e );}
                System.out.println("supplierID "+supplierID);
                
                ps=con.prepareStatement("SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,SUPPLIER_ID,SUPPLIER_ALIAS_ID,SUPPLIER_NAME,SUPPLIER_ADDRESS,SUPPLIER_PHONE, SUPPLIER_FAX,SUPPLIER_EMAIL_ID ,to_char(DATE_OF_REGISTRATION,'DD/MM/YYYY') as DATE_OF_REG,to_char(DATE_OF_LAST_SUPPLY,'DD/MM/YYYY') as DATE_OF_LAST_SUP ,SUPPLIER_ADDRESS1 ,SUPPLIER_CITY, SUPPLIER_ALIAS_ID ,PINCODE,STATUS  FROM COM_SUPPLIER_SL_MST where ACCOUNTING_UNIT_ID="+cmbAcc_UnitCode+" and ACCOUNTING_FOR_OFFICE_ID="+cmbOffice_code+" and SUPPLIER_ID=? order by SUPPLIER_ID");
                ps.setInt(1,supplierID);
                
                rs=ps.executeQuery();
                try
                {
                    xml=sxml+"<flag>success</flag>";
                while(rs.next())
                {
                    supplierID=rs.getInt("SUPPLIER_ID");
                    System.out.println("SUPPLIER_ID is%%%%%%%%%%%%%%%%%%%%%"+supplierID);
                    strsuppname=rs.getString("SUPPLIER_NAME");
                    System.out.println("SUPPLIER_NAME is "+strsuppname);
                    straddr=rs.getString("SUPPLIER_ADDRESS");
                    System.out.println("SUPPLIER_ADDRESS is "+straddr);
                    stremail=rs.getString("SUPPLIER_EMAIL_ID");
                    System.out.println("SUPPLIER_EMAIL_ID is "+stremail);
                    strphone=rs.getString("SUPPLIER_PHONE");
                    System.out.println("SUPPLIER_PHONE is "+strphone);
                    strfax=rs.getString("SUPPLIER_FAX");
                    System.out.println("SUPPLIER_FAX is********** "+strfax);
                    strAddr1=rs.getString("SUPPLIER_ADDRESS1");
                    System.out.println("SUPPLIER_ADDRESS1 is********** "+strfax);
                    strCity=rs.getString("SUPPLIER_CITY");
                    System.out.println("SUPPLIER_CITY is********** "+strfax);
                    Status=rs.getString("STATUS");
                    xml=xml+"<supplierID>"+supplierID+"</supplierID><supplierName>"+strsuppname+"</supplierName><supplierAliasName>"+rs.getString("SUPPLIER_ALIAS_ID")+"</supplierAliasName><supAddr>"+
                            straddr+"</supAddr><pincode>"+rs.getInt("PINCODE")+"</pincode><supPhone>"+strphone+"</supPhone><supFax>"+strfax+"</supFax><supemail>"+stremail+
                            "</supemail><supaddr1>"+strAddr1+"</supaddr1><supcity>"+strCity+"</supcity><DOReg>"+rs.getString("DATE_OF_REG")+"</DOReg><DOsupply>"+rs.getString("DATE_OF_LAST_SUP")+"</DOsupply><status>"+Status+"</status>";
                    b++;
                    
                }
                }
                catch(Exception ae) {
                    System.out.println("Exception in while loop..."+ae);
                }
                                 
                if(b==0)
                {
                
                System.out.println("i...."+b);
                xml=sxml+"<flag>failure</flag>";
                }
                else
                {
                    System.out.println("i.."+b);
                   // xml=sxml+"<flag>success</flag><supplierID>"+supplierID+"</supplierID><supplierName>"+strsuppname+"</supplierName><supAddr>"+straddr+"</supAddr><supPhone>"+strphone+"</supPhone><supFax>"+strfax+"</supFax><supemail>"+stremail+"</supemail>";
                }
            
            }
            catch(Exception e) 
            {
                    xml=sxml+"<flag>failure</flag>";
                System.out.println("catch in load table..."+e);
                }
                            xml=xml+"</response>";
            }
                        
             else if(strCommand.equalsIgnoreCase("listAll")) 
             {
                 System.out.println("Insiden Year ");
                  int a=0;
                  int supplierID=0;
                   int supplierName=0;
                 String dateReg="";
                String datelast="";
             java.sql.Date date1=null;
            java.sql.Date date2=null;
            sxml="<response><command>listAll</command>";
                                
                                b=0;
                                try {
                                    int cmbAcc_UnitCode=0,cmbOffice_code=0;
                                      try{cmbAcc_UnitCode=Integer.parseInt(request.getParameter("cmbAcc_UnitCode"));}
                                      catch(NumberFormatException e){System.out.println("exception"+e );}
                                      System.out.println("cmbAcc_UnitCode "+cmbAcc_UnitCode);
                                      
                                      try{cmbOffice_code=Integer.parseInt(request.getParameter("cmbOffice_code"));}
                                      catch(NumberFormatException e){System.out.println("exception"+e );}
                                      System.out.println("cmbOffice_code "+cmbOffice_code);
                                    ps=con.prepareStatement("SELECT ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,SUPPLIER_ID,SUPPLIER_NAME,SUPPLIER_ADDRESS,SUPPLIER_PHONE, SUPPLIER_FAX,SUPPLIER_EMAIL_ID ,to_char(DATE_OF_REGISTRATION,'DD/MM/YYYY') as DATE_OF_REG,to_char(DATE_OF_LAST_SUPPLY,'DD/MM/YYYY') as DATE_OF_LAST_SUP ,SUPPLIER_ADDRESS1 ,SUPPLIER_CITY, SUPPLIER_ALIAS_ID ,PINCODE,STATUS  FROM COM_SUPPLIER_SL_MST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? order by SUPPLIER_ID");
                                    ps.setInt(1,cmbAcc_UnitCode);
                                    ps.setInt(2,cmbOffice_code);
                                    
                                    rs=ps.executeQuery();
                                    try
                                    {
                                        xml=sxml+"<flag>success</flag>";
                                    while(rs.next())
                                    {
                                             supplierID=rs.getInt("SUPPLIER_ID");
                                        System.out.println("SUPPLIER_ID is%%%%%%%%%%%%%%%%%%%%%"+supplierID);
                                             strsuppname=rs.getString("SUPPLIER_NAME");
                                        System.out.println("SUPPLIER_NAME is "+strsuppname);
                                        straddr=rs.getString("SUPPLIER_ADDRESS");
                                        System.out.println("SUPPLIER_ADDRESS is "+straddr);
                                        stremail=rs.getString("SUPPLIER_EMAIL_ID");
                                        System.out.println("SUPPLIER_EMAIL_ID is "+stremail);
                                        strphone=rs.getString("SUPPLIER_PHONE");
                                        System.out.println("SUPPLIER_PHONE is "+strphone);
                                        strfax=rs.getString("SUPPLIER_FAX");
                                        System.out.println("SUPPLIER_FAX is********** "+strfax);
                                        strAddr1=rs.getString("SUPPLIER_ADDRESS1");
                                        System.out.println("SUPPLIER_ADDRESS1 is********** "+strAddr1);
                                        strCity=rs.getString("SUPPLIER_CITY");
                                        System.out.println("SUPPLIER_CITY is********** "+strCity);
                                        Status=rs.getString("STATUS");
                                     xml=xml+"<supplierID>"+supplierID+"</supplierID><supplierName>"+
                                     strsuppname+"</supplierName><supAddr>"+straddr+"</supAddr><supPhone>"+
                                     strphone+"</supPhone><supFax>"+strfax+"</supFax><supemail>"+stremail+"</supemail><supaddr1>"+strAddr1+"</supaddr1><supcity>"+
                                     strCity+"</supcity><DOReg>"+rs.getString("DATE_OF_REG")+"</DOReg><DOsupply>"+rs.getString("DATE_OF_LAST_SUP")+"</DOsupply><status>"+Status+"</status>";
                                            b++;
                                        
                                    }
                                    }
                                    catch(Exception ae) {
                                        System.out.println("Exception in while loop..."+ae);
                                    }
                                                     
                                    if(b==0)
                                    {
                                    
                                    System.out.println("i...."+b);
                                    xml=sxml+"<flag>failure</flag>";
                                    }
                                    else
                                    {
                                        System.out.println("i.."+b);
                                       // xml=sxml+"<flag>success</flag><supplierID>"+supplierID+"</supplierID><supplierName>"+strsuppname+"</supplierName><supAddr>"+straddr+"</supAddr><supPhone>"+strphone+"</supPhone><supFax>"+strfax+"</supFax><supemail>"+stremail+"</supemail>";
                                    }
                                
                                }
                                catch(Exception e) 
                                {
                                        xml=sxml+"<flag>failure</flag>";
                                    System.out.println("catch in load table..."+e);
                                    }
                                                xml=xml+"</response>";
                    }
                            System.out.println("xml is:"+xml);
                            out.write(xml);
                            out.flush();
                            out.close();
                        
                        }
                        
                        
                    }

