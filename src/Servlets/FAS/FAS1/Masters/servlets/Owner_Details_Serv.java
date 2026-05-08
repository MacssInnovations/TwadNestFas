package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Owner_Details_Serv extends HttpServlet 
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
        int accounting_unit_id=0;
        int accounting_unit_office_id=0;
        int owner_code=0;
        String owner_type = null;
        String owner_desc = null;
        
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
        	accounting_unit_id= Integer.parseInt(request.getParameter("accounting_unit_id"));
        	System.out.println("accounting_unit_id : " + accounting_unit_id);
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching accounting_unit_id ===> " + e);
        }        
        
        try
        {
        	accounting_unit_office_id = Integer.parseInt(request.getParameter("accounting_unit_office_id"));
            System.out.println("accounting_unit_office_id : "+ accounting_unit_office_id);
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching accounting_unit_office_id ===> " + e);
        }
        
        try
        {
        	owner_code = Integer.parseInt(request.getParameter("owner_code"));
            System.out.println("owner_code : "+ owner_code);
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching owner_code ===> " + e);
        }
        
        try
        {
        	owner_type = request.getParameter("owner_type");
            System.out.println("owner_type : "+ owner_type);
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching owner_type ===> " + e);
        }
        
        try
        {
        	owner_desc = request.getParameter("owner_desc");
            System.out.println("owner_desc : "+ owner_desc);
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching owner_desc ===> " + e);
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
             String sqlDelete = "UPDATE fas_owner_details " +
								"SET status= 'C', " +								
								"  updated_date = systimestamp " +
								"WHERE accounting_unit_id = ? " +
								" AND accounting_unit_office_id = ? " +
             					"AND OWNER_CODE=?";
		   	 PreparedStatement ps = connection.prepareStatement(sqlDelete);
		   	 ps.setInt(1, accounting_unit_id);
		   	 ps.setInt(2, accounting_unit_office_id);
		   	 ps.setInt(3, owner_code);
		   	 ps.executeUpdate();
             xml=xml+"<flag>success</flag>";
             xml=xml+"<accounting_unit_id>" + accounting_unit_id + "</accounting_unit_id>";
             xml=xml+"<accounting_unit_office_id>" + accounting_unit_office_id + "</accounting_unit_office_id>";
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
             String sqlUpdate = "UPDATE fas_owner_details " +
								"SET owner_type = ?, " +
								"  owner_desc = ?, " +
								"  updated_by_userid = ?, " +
								"  updated_date = systimestamp " +
								"WHERE accounting_unit_id = ? " +
								" AND accounting_unit_office_id = ? " +
								" AND owner_code = ?";
		   	 PreparedStatement ps = connection.prepareStatement(sqlUpdate);
		   	 
		   	 ps.setString(1,owner_type);
		   	 ps.setString(2,owner_desc);
		   	 ps.setString(3,userid);
		   	 ps.setInt(4,accounting_unit_id);
		   	 ps.setInt(5,accounting_unit_office_id);
		   	 ps.setInt(6,owner_code);
		   	
		   	 ps.executeUpdate();
             xml=xml+"<flag>success</flag>";
             xml=xml+"<accounting_unit_id>" + accounting_unit_id + "</accounting_unit_id>";
             xml=xml+"<accounting_unit_office_id>" + accounting_unit_office_id + "</accounting_unit_office_id>";
             xml=xml+"<owner_code>" + owner_code + "</owner_code>";
             xml=xml+"<owner_type>" + owner_type + "</owner_type>";
             xml=xml+"<owner_desc>" + owner_desc + "</owner_desc>";
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
             result = statement.executeQuery("SELECT MAX(owner_code) AS owner_code " +
											"FROM fas_owner_details " +
											"WHERE accounting_unit_id = " + accounting_unit_id +
											" AND accounting_unit_office_id = " + accounting_unit_office_id);
		             try
		             {
		                 while(result.next())
		                 { 
		                	 owner_code=result.getInt("owner_code");
		                	 System.out.println("owner_code : " + owner_code);
		                 }
		                 owner_code++;
		             }catch(Exception e)
		             {
		            	 System.out.println("Exception in fetching the max(owner_code) from resultset: " + e);
		             }
		             result.close();
            }catch(Exception e)
            {
            	System.out.println("Exception executing MAX_owner_code Query");
            }
             
            try 
            {
             String sqlAdd = "INSERT " +
							"INTO fas_owner_details(accounting_unit_id,   accounting_unit_office_id,   owner_code,   owner_type,   owner_desc,   updated_by_userid,   updated_date, status) " +
							"VALUES(?,?,?,?,?,?,systimestamp,'L')";
		   	 PreparedStatement ps = connection.prepareStatement(sqlAdd);
		   	 ps.setInt(1, accounting_unit_id);
		   	 ps.setInt(2, accounting_unit_office_id);
		   	 ps.setFloat(3, owner_code);
		   	 ps.setString(4, owner_type);
		   	 ps.setString(5, owner_desc);
		   	 ps.setString(6, userid);
		   	 ps.executeUpdate();
             xml=xml+"<flag>success</flag>";
             xml=xml+"<accounting_unit_id>" + accounting_unit_id + "</accounting_unit_id>";
             xml=xml+"<accounting_unit_office_id>" + accounting_unit_office_id + "</accounting_unit_office_id>";
             xml=xml+"<owner_code>" + owner_code + "</owner_code>";
             xml=xml+"<owner_type>" + owner_type + "</owner_type>";
             xml=xml+"<owner_desc>" + owner_desc + "</owner_desc>";
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
             result = statement.executeQuery("SELECT owner_code, " +
											"  owner_type, " +
											"  owner_desc, " +
											"  status " +
											"FROM fas_owner_details " +
											"WHERE accounting_unit_id = " + accounting_unit_id +
											" AND accounting_unit_office_id = " + accounting_unit_office_id +
											" ORDER BY owner_code");
             try
             {
            	 xml=xml+"<flag>success</flag>";
                 while(result.next())
                 { 
                	 owner_code=result.getInt("owner_code");
                	 owner_type=result.getString("owner_type");
                	 owner_desc=result.getString("owner_desc");
                	 
                	 xml=xml+ "<accounting_unit_id>" + accounting_unit_id + "</accounting_unit_id><accounting_unit_office_id>" + accounting_unit_office_id + "</accounting_unit_office_id><owner_code>" + owner_code + "</owner_code><owner_type>" + owner_type + "</owner_type><owner_desc>" + owner_desc + "</owner_desc>" +
                	 		"<status>"+result.getString("status")+"</status>";
                	 /*System.out.println("\n------------------------------\naccounting_unit_id: "+accounting_unit_id+"\naccounting_unit_office_id: "+accounting_unit_office_id+"\nowner_code: "+owner_code+"\nowner_type: "+owner_type+"\nowner_desc: "+owner_desc+"\n\n------------------------------\n");*/
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
