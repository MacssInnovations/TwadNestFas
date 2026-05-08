package Servlets.FAS.FAS1.BRS.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class BRS_Reason_Catalogue extends HttpServlet {
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

   
    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

   
   
   public void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException 
    {
      
      
      /**
       * Session Checking 
       */
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
       
              
        /**
         * Variables Declaration 
         */
         
        Connection con=null;        
        PreparedStatement ps=null;
        PreparedStatement ps2=null; 
        ResultSet rs2=null;
        
        response.setContentType(CONTENT_TYPE);
        response.setHeader("Cache-Control","no-cache");
        PrintWriter out = response.getWriter();
        
        String strCommand="";
        
        
        /**
         * Database Connection 
         */
        
        try 
        {
                               ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                               String ConnectionString="";

                               String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
                               String strdsn=rs1.getString("Config.DSN");
                               String strhostname=rs1.getString("Config.HOST_NAME");
                               String strportno=rs1.getString("Config.PORT_NUMBER");
                               String strsid=rs1.getString("Config.SID");
                               String strdbusername=rs1.getString("Config.USER_NAME");
                               String strdbpassword=rs1.getString("Config.PASSWORD");
                               ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
                               Class.forName(strDriver.trim());
                               con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
        }
        catch(Exception e)
          {
             System.out.println("Exception in opening connection :"+e);
             
          }
 
 
 
        /**
         * Get Command Parameter 
         */
        
        try 
        {
            strCommand=request.getParameter("Command");
            System.out.println("assign..here command..."+strCommand);
        }
        
        catch(Exception e) 
        {
            System.out.println("Exception in assigning..."+e);
        }
        
 
 
        /**
         * Variables Declaration 
         */
       
       
        int txtReasonCode = 0 ;
        String txtReasonShortDesc = "";
        String txtReasonDesc = "";
        
        String update_user=(String)session.getAttribute("UserId");
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
        
        /**
         * Get Parameters 
         */
         
        /** Get Accounting Unit ID */    
        try{
        txtReasonCode=Integer.parseInt(request.getParameter("txtReasonCode"));
        }catch(Exception e){System.out.println("Exception to Read Reason Code ");}
        
        /** Get Accounting Office ID */
        try{
        txtReasonShortDesc=request.getParameter("txtReasonShortDesc");
        }catch(Exception e){System.out.println("Exception to Read Reason short Description");}
        
        /** Get Sub Ledger Type */
        try{
        txtReasonDesc=request.getParameter("txtReasonDesc");
        }catch(Exception e){System.out.println("Exception to Read Reason Description");}
        
        
        
//~~~~~~~~~~~~~~~~~~~~~~~~ Here It does some Database Operations such as add , delete , update, etc., ~~~~~~~~~~~~~~~~~~~~~~
        
         if(strCommand.equalsIgnoreCase("Add")) 
         {
             String CONTENT_TYPE = "text/xml; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
             String xml="";             
             int max_code=0;
             
             xml="<response><command>Add</command>";             
             
                
             
              try {
              
                  String sql="" +
                  "select                                                                                   \n" + 
                  "  case when ( select  max(reason_code) from fas_brs_reason_catalogue ) is null then      \n" + 
                  "     1                                                                                   \n" + 
                  "  else                                                                                   \n" + 
                  "    ( select  max(reason_code) from fas_brs_reason_catalogue ) + 1                       \n" + 
                  "  end as max_code                                                                        \n" + 
                  "                                                                                \n";
              
                  ps2=con.prepareStatement(sql);
                  rs2=ps2.executeQuery();
                  while (rs2.next()) {
                      max_code = rs2.getInt("max_code");
                  }
                  
                  ps=con.prepareStatement("insert into FAS_BRS_REASON_CATALOGUE (reason_code, reason_short_desc, reason_desc, updated_by_user_id, updated_date) values(?,?,?,?,?)  ");
                  ps.setInt(1,max_code);
                  ps.setString(2,txtReasonShortDesc);
                  ps.setString(3,txtReasonDesc);                  
                  ps.setString(4,update_user);
                  ps.setTimestamp(5,ts);                     
                  
                  ps.executeUpdate();
                     xml=xml+"<flag>success</flag>";
                 }
                catch(Exception e)
                {
                   xml=xml+"<flag>failure</flag>";
                   System.out.println(e);
                }
            xml=xml+"</response>";
            System.out.println(xml);
            out.println(xml);
         }
          
         else if(strCommand.equalsIgnoreCase("Update")) 
         {
            String CONTENT_TYPE = "text/xml; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
             String xml="";
              xml="<response><command>Update</command>";
           
             try {
             
                     ps=con.prepareStatement("update FAS_BRS_REASON_CATALOGUE set reason_short_desc=? , reason_desc=? , updated_by_user_id=?, updated_date = ?  where reason_code=? " );
                     ps.setString(1,txtReasonShortDesc);
                     ps.setString(2,txtReasonDesc);                  
                     ps.setString(3,update_user);
                     ps.setTimestamp(4,ts);                                          
                     ps.setInt(5,txtReasonCode);                     
                     
                  ps.executeUpdate();
                  
                 xml=xml+"<flag>success</flag>";
                 }
                catch(Exception e)
                {
                System.out.println("catch..HERE.in load head code."+e);
                xml=xml+"<flag>failure</flag>";
                }
            xml=xml+"</response>";
            System.out.println(xml);
            out.println(xml); 
         }
        else if(strCommand.equalsIgnoreCase("Delete")) 
        {
             String CONTENT_TYPE = "text/xml; charset=windows-1252";
             response.setContentType(CONTENT_TYPE);
             String xml="";
              xml="<response><command>Delete</command>";
           
             try {             
                  ps=con.prepareStatement("delete from  FAS_BRS_REASON_CATALOGUE  where reason_code=? " );               
                  ps.setInt(1,txtReasonCode);                                   
                  ps.executeUpdate();
                     xml=xml+"<flag>success</flag>";
                 }
                catch(Exception e)
                {
                System.out.println("catch..HERE.in load head code."+e);
                xml=xml+"<flag>failure</flag>";
                }
            xml=xml+"</response>";
            System.out.println(xml);
            out.println(xml);    
         }
         
    }
}

