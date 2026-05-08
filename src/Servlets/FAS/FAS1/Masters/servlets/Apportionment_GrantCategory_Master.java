package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Apportionment_GrantCategory_Master extends HttpServlet 
{
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
    

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        

    }

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
        Connection connection=null;
        Statement statement=null;
        ResultSet result=null;
        //PreparedStatement ps=null;
        try
                  {
                       ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                         String ConnectionString="";
                        
                         String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
                         String strdsn=rs.getString("Config.DSN");
                         String strhostname=rs.getString("Config.HOST_NAME");
                         String strportno=rs.getString("Config.PORT_NUMBER");
                         String strsid=rs.getString("Config.SID");
                         String strdbusername=rs.getString("Config.USER_NAME");
                         String strdbpassword=rs.getString("Config.PASSWORD");
                           
                         ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection

                          Class.forName(strDriver.trim());
                          connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
              try
              {
                statement=connection.createStatement();
                connection.clearWarnings();
              }
              catch(SQLException e)
              {
                  System.out.println("Exception in creating statement:"+e);
              }          
           }
          catch(Exception e)
          {
             System.out.println("Exception in openeing connection:"+e);
          }
        response.setContentType(CONTENT_TYPE);
        String strCommand = ""; 
        String xml="";
        int ApportCode=0;
        String ApportDesc="";
        
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
         String userid=(String)session.getAttribute("UserId");
         System.out.println("Session id is:"+userid);
        
        
        response.setContentType("text/xml");
        PrintWriter pw=response.getWriter();    
        response.setHeader("Cache-Control","no-cache");
        
        try
        {
        	strCommand = request.getParameter("command");     
        	System.out.println("strCommand : " + strCommand);
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
 
        try
        {
        	ApportCode= Integer.parseInt(request.getParameter("ApportGrantCatCode"));
        	System.out.println("ApportCode : " + ApportCode);
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching ApportGrantCatCode ===> " + e);
        }        
        
        try
        {
        	ApportDesc = request.getParameter("ApportGrantCatDesc");
            System.out.println("ApportDesc : "+ ApportDesc);
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching ApportGrantCatDesc ===> " + e);
        }

        
        /*
         * Execute the task adhering to the requested command
         */
        
        if(strCommand.equalsIgnoreCase("Delete"))
        {
        	System.out.println("\n*************\nDelete\n**************\n");
            xml="<response><command>Delete</command>";
            try 
            {
             String sqlDelete = "UPDATE fas_apport_category_mst " +
								"SET status = 'C', " +								
								"  updated_date = systimestamp " +
             					"WHERE apportion_grant_cate_code = ?";
		   	 PreparedStatement ps = connection.prepareStatement(sqlDelete);
		   	 ps.setInt(1, ApportCode);
		   	 ps.executeUpdate();
             xml=xml+"<flag>success</flag>";
             xml=xml+"<ApportCode>" + ApportCode + "</ApportCode>";
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in Deleting record ===> "+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
                
        else if(strCommand.equalsIgnoreCase("Update"))
        {
        	System.out.println("\n*************\nUpdate\n**************\n");
            xml="<response><command>Update</command>";
            try 
            {
             String sqlUpdate = "UPDATE fas_apport_category_mst " +
								"SET apportion_grant_category = ?, " +
								"  updated_by_userid = ?, " +
								"  updated_date = systimestamp " +
								"WHERE apportion_grant_cate_code = ?";
		   	 PreparedStatement ps = connection.prepareStatement(sqlUpdate);
		   	 ps.setString(1,ApportDesc);
		   	 ps.setString(2,userid);
		   	 ps.setInt(3,ApportCode);
		   	 ps.executeUpdate();
             xml=xml+"<flag>success</flag>";
             xml=xml+"<ApportCode>" + ApportCode + "</ApportCode>";
             xml=xml+"<ApportDesc>" + ApportDesc + "</ApportDesc>";
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in Updating record ===> "+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
                
        else if(strCommand.equalsIgnoreCase("Add"))
        {
        	System.out.println("\n*************\nAdd\n**************\n");
            xml="<response><command>Add</command>";

            try 
            {
             String sqlAdd_SubQry = "SELECT MAX(apportion_grant_cate_code) as maxApportCode " +
             						"FROM fas_apport_category_mst";
		   	 PreparedStatement ps = connection.prepareStatement(sqlAdd_SubQry);
		   	 result = ps.executeQuery();
		   	 while(result.next())
		   	 {
		   		ApportCode = result.getInt("maxApportCode");
		   		if(ApportCode > 0)
		   		{
		   			ApportCode++;
		   		}
		   		else
		   		{
		   			ApportCode = 1;
		   		}
		   	 }
		   	 System.out.println("NEW ApportCode : "+ApportCode);
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in fetching record ===> "+e1);
            }

            
            try 
            {
             String sqlAdd = "INSERT INTO fas_apport_category_mst(apportion_grant_cate_code,apportion_grant_category,updated_by_userid,updated_date,status) VALUES(?,?,?,systimestamp,'L')";
		   	 PreparedStatement ps = connection.prepareStatement(sqlAdd);
		   	 ps.setInt(1, ApportCode);
		   	 ps.setString(2, ApportDesc);
		   	 ps.setString(3, userid);
		   	 ps.executeUpdate();
             xml=xml+"<flag>success</flag>";
             xml=xml+"<ApportCode>" + ApportCode + "</ApportCode>";
             xml=xml+"<ApportDesc>" + ApportDesc + "</ApportDesc>";
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in Adding record ===> "+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
        else if(strCommand.equals("Get"))
        { 
        	System.out.println("\n*************\nGet\n**************\n");
            xml="<response><command>Get</command>";
            try 
            {
             result = statement.executeQuery("SELECT apportion_grant_cate_code," +
                        					   	"apportion_grant_category, " +
                        					   	"status " +
                        					   	"FROM fas_apport_category_mst " +
            									"ORDER BY apportion_grant_cate_code"
            								  );
             try
             {
            	 xml=xml+"<flag>success</flag>";
                 while(result.next())
                 { 
                   ApportCode=result.getInt("apportion_grant_cate_code");
                   ApportDesc=result.getString("apportion_grant_category");
                   xml=xml+ "<ApportCode>" + ApportCode + "</ApportCode><ApportDesc>" + ApportDesc + "</ApportDesc>" +
                   		"<status>"+result.getString("status")+"</status>";
                   System.out.println("\n------------------------------\nApportCode: "+ApportCode+"\nApportDesc: "+ApportDesc+"\n------------------------------\n");
                 }
             }catch(Exception e)
             {
            	 System.out.println("Exception in the getting values OF GET: " + e);
             }
             result.close();
             //response.setHeader("cache-control","no-cache");
            }
            catch(Exception e1)
            {
            	System.out.println("Exception is in Get"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }  
                
                System.out.println("xml is : " + xml);
                pw.write(xml);
                pw.flush();
                pw.close();
        
    }
}
