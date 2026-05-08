package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Apportionment_Rates extends HttpServlet 
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
        String FinYear="";
        float ApportRate=0;
        
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
        	FinYear = request.getParameter("FinancialYear");
            System.out.println("FinYear : "+ FinYear);
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching FinYear ===> " + e);
        }
        
        try
        {
        	ApportRate = Float.parseFloat(request.getParameter("ApportGrantRate"));
        												// 1.12345
        	int tint = (int)ApportRate;              // 1
        	float tflt = ApportRate - tint;			// .12345
        	tflt = (int)(tflt*100);					// 12
        	ApportRate = tint + (float)(tflt/100);				// 
            
        	System.out.println("ApportRate : "+ ApportRate);
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching FinYear ===> " + e);
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
             String sqlDelete = "UPDATE fas_apportionment_rates " +
								"SET status = 'C', " +				
								"  updated_date = systimestamp " +
								"WHERE financial_year = ? " +
								" AND apportion_grant_cate_code = ?";
		   	 PreparedStatement ps = connection.prepareStatement(sqlDelete);
		   	 ps.setString(1, FinYear);
		   	 ps.setInt(2, ApportCode);
		   	 ps.executeUpdate();
             xml=xml+"<flag>success</flag>";
             xml=xml+"<ApportCode>" + ApportCode + "</ApportCode>";
             xml=xml+"<FinancialYear>" + FinYear + "</FinancialYear>";
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
             String sqlUpdate = "UPDATE fas_apportionment_rates " +
								"SET apportionment_rate = ?, " +
								"  updated_by_userid = ?, " +
								"  updated_date = systimestamp " +
								"WHERE financial_year = ? " +
								" AND apportion_grant_cate_code = ?";
		   	 PreparedStatement ps = connection.prepareStatement(sqlUpdate);
		   	 ps.setFloat(1,ApportRate);
		   	 ps.setString(2,userid);
		   	 ps.setString(3,FinYear);
		   	 ps.setInt(4,ApportCode);
		   	 ps.executeUpdate();
             xml=xml+"<flag>success</flag>";
             xml=xml+"<ApportCode>" + ApportCode + "</ApportCode>";
             xml=xml+"<FinancialYear>" + FinYear + "</FinancialYear>";
             xml=xml+"<ApportRate>" + ApportRate + "</ApportRate>";
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
             String sqlAdd = " INSERT " +
             				 "INTO fas_apportionment_rates" +
             				 "(apportion_grant_cate_code,   financial_year,   apportionment_rate,   " +
             				 "updated_by_userid,   updated_date, status) " +
							 "VALUES(?,?,?,?,systimestamp,'L')";
		   	 PreparedStatement ps = connection.prepareStatement(sqlAdd);
		   	 ps.setInt(1, ApportCode);
		   	 ps.setString(2, FinYear);
		   	 ps.setFloat(3, ApportRate);
		   	 ps.setString(4, userid);
		   	 ps.executeUpdate();
             xml=xml+"<flag>success</flag>";
             xml=xml+"<ApportCode>" + ApportCode + "</ApportCode>";
             xml=xml+"<FinancialYear>" + FinYear + "</FinancialYear>";
             xml=xml+"<ApportRate>" + ApportRate + "</ApportRate>";
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
             result = statement.executeQuery("SELECT financial_year, " +
											 "  apportion_grant_cate_code, " +
											 "  apportionment_rate, " +
											 "  status " +
											 "FROM fas_apportionment_rates " +
											 "WHERE financial_year = '" + FinYear + "'");
             try
             {
            	 xml=xml+"<flag>success</flag>";
                 while(result.next())
                 { 
                   ApportCode=result.getInt("apportion_grant_cate_code");
                   FinYear=result.getString("financial_year");
                   ApportRate=result.getFloat("apportionment_rate");
                   xml=xml+ "<ApportCode>" + ApportCode + "</ApportCode><FinancialYear>" + FinYear + "</FinancialYear><ApportRate>" + ApportRate + "</ApportRate>" +
                   		"<status>"+result.getString("status")+"</status>";
                   System.out.println("\n------------------------------\nApportCode: "+ApportCode+"\nFinYear: "+FinYear+"\nApportRate: "+ApportRate+"\n------------------------------\n");
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
