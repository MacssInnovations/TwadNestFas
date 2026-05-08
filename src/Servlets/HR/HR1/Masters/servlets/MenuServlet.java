package Servlets.HR.HR1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

import Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver;

public class MenuServlet extends HttpServlet 
{
    private static final String CONTENT_TYPE = "text/html; charset=windows-1252";
      private Connection connection=null;
      private Statement statement=null;
      private ResultSet results=null;
      private ResultSet results1=null;
      private PreparedStatement ps=null;
      private PreparedStatement ps1=null;


    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        try
                  {
        	LoadDriver driver=new LoadDriver();
        	connection=driver.getConnection();

              try
              {
                statement=connection.createStatement();
                connection.clearWarnings();
              }
              catch(SQLException e)
              {
                 // System.out.println("Exception in creating statement:"+e);
              }          
           }
          catch(Exception e)
          {
            // System.out.println("Exception in openeing connection:"+e);
          }
    }

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException 
    {
        String strCommand = ""; 
           String xml="";
           String xml1="";
           PrintWriter pw=response.getWriter();
           response.setContentType("text/xml");
           response.setHeader("cache-control","no-cache");
           HttpSession session=request.getSession(true);
           String userid=(String)(session.getAttribute("userid"));
        //   System.out.println("servlet called");
           
           String str="";
           
           try
           {
 	LoadDriver driver=new LoadDriver();
 	connection=driver.getConnection();

       try
       {
         statement=connection.createStatement();
         connection.clearWarnings();
       }
       catch(SQLException e)
       {
          // System.out.println("Exception in creating statement:"+e);
       }          
    }
   catch(Exception e)
   {
     // System.out.println("Exception in openeing connection:"+e);
   } 
        try
        {
            //HttpSession session=request.getSession(false);
            if(session==null)
            {
              //  System.out.println(request.getContextPath()+"/index.jsp");
                response.sendRedirect(request.getContextPath()+"/index.jsp");
            
            }
        //    System.out.println(session);
                
        }catch(Exception e)
        {
      //  System.out.println("Redirect Error :"+e);
        }
        
           
            try
           {
             str=request.getParameter("Type");

             
             System.out.println(str);
            
           }

           catch(Exception e)
           {
             e.printStackTrace();
           }
        if(str.equals("major"))
            {
                    try
                    {
                      String strmajor=request.getParameter("First");
                      String sql="select MINOR_SYSTEM_ID,MINOR_SYSTEM_DESC from SEC_MST_MINOR_SYSTEMS where MAJOR_SYSTEM_ID=?";
                    ps=connection.prepareStatement(sql);
                    ps.setString(1,strmajor);
                    results=ps.executeQuery();
                    while(results.next())
                    {
                      String temp=results.getString("MINOR_SYSTEM_ID");
                      xml=xml+"<option><desc>"+results.getString("MINOR_SYSTEM_DESC")+"</desc><id>"+temp+"</id></option>";
                    }
                   
                    } 
                    
                    catch(Exception e)
                    {
                //    System.out.println(e);
                    }
            }
            
            
        else if(str.equals("minor"))
           {
		           try
		           {
		        	   ps=null;
//		        	   ps.close();
		             String strmajor=request.getParameter("First");
		               String strminor=request.getParameter("Second");
		           String sql="SELECT SUB_SYSTEM_ID,SUB_SYSTEM_DESC FROM SEC_MST_SUBSYSTEMS WHERE trim(MAJOR_SYSTEM_ID)=? AND trim(MINOR_SYSTEM_ID)=?";
		        PreparedStatement   ps1=connection.prepareStatement(sql);
		        ps1.setString(1,strmajor.trim());
		        ps1.setString(2,strminor.trim());

		           results=ps1.executeQuery();
		           while(results.next())
		           {
		             String temp=results.getString("SUB_SYSTEM_ID");
		             System.out.println("temp : "+temp);
		             xml=xml+"<option><desc>"+results.getString("SUB_SYSTEM_DESC")+"</desc><id>"+temp+"</id></option>";
		             System.out.println(xml);
		           }
		          
		           } 
		           
		           catch(Exception e)
		           {
		           System.out.println(e);
		           }
           }
        else if(str.equals("menucat"))
           {
			           try
			           {
				             String strmajor=request.getParameter("First");
				               String strminor=request.getParameter("Second");
				               String strsub=request.getParameter("Third");
				           String sql1="select distinct(Menu_Category) from SEC_MST_INTRANET_MENUS where MAJOR_SYSTEM_ID=? and MINOR_SYSTEM_ID=? and SUB_SYSTEM_ID=? and Menu_Category is not null order by Menu_Category" ;
				           ps1=connection.prepareStatement(sql1);
				           ps1.setString(1,strmajor);
				           ps1.setString(2,strminor);
				           ps1.setString(3,strsub);
				           results1=ps1.executeQuery();
				           while(results1.next())
				           {
				             String temp=results1.getString("Menu_Category");
				             xml=xml+"<option><desc>"+results1.getString("Menu_Category")+"</desc><id>"+temp+"</id></option>";
				           }
			          
			           } 
			           
           catch(Exception e)
           {
        	   	System.out.println(e);
           }
           }
        
         

            
        pw.write("<select>"+ xml +"</select>");
         //   System.out.println("xml is" +xml);
         
           
    }
}
