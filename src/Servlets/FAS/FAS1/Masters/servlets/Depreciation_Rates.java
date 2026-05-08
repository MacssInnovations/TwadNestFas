package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Depreciation_Rates extends HttpServlet 
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
        ResultSet rset=null;
        Statement statement1=null;
      String desc_categry="";
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
              try
              {
                statement1=connection.createStatement();
                connection.clearWarnings();
              }
              catch(SQLException e1)
              {
                  System.out.println("Exception in creating statement:"+e1);
              }        
           }
          catch(Exception e)
          {
             System.out.println("Exception in openeing connection:"+e);
          }
        response.setContentType(CONTENT_TYPE);
        String strCommand = ""; 
        String xml="";
        int DepreciationCode=0;
        String FinYear="";
        float DepreciationRate=0;
        
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
        	DepreciationCode= Integer.parseInt(request.getParameter("DepreciationCatCode"));
        	System.out.println("DepreciationCode : " + DepreciationCode);
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching DepreciationCatCode ===> " + e);
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
        	DepreciationRate = Float.parseFloat(request.getParameter("DepreciationRate"));
        												// 1.12345
        	int tint = (int)DepreciationRate;              // 1
        	float tflt = DepreciationRate - tint;			// .12345
        	tflt = (int)(tflt*100);					// 12
        	DepreciationRate = tint + (float)(tflt/100);				// 
            
        	System.out.println("DepreciationRate : "+ DepreciationRate);
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching DepreciationRate 11 ===> " + e);
        }
        
        /*
         * Execute the task adhering to the requested command
         */
        
        if(strCommand.equalsIgnoreCase("Delete1"))
        {
        	System.out.println("\n*************\nDelete\n**************\n");
            xml="<response><command>Delete</command>";
            try 
            {
             String sqlDelete = "UPDATE fas_Depre_rates " +
								"SET status ='C', " +								
								"  updated_date = systimestamp " +
								"WHERE financial_year = ? " +
								" AND Depreciation_cate_code = ?";
		   	 PreparedStatement ps = connection.prepareStatement(sqlDelete);
		   	 ps.setString(1, FinYear);
		   	 ps.setInt(2, DepreciationCode);
		   	 ps.executeUpdate();
             xml=xml+"<flag>success</flag>";
             xml=xml+"<DepreciationCode>" + DepreciationCode + "</DepreciationCode>";
             xml=xml+"<FinancialYear>" + FinYear + "</FinancialYear>";
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in Deleting record ===> "+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
        }
        if(strCommand.equalsIgnoreCase("Delete"))
        {
        	System.out.println("\n*************\nDelete\n**************\n");
            xml="<response><command>Delete</command>";
            try 
            {
             String sqlDelete = "UPDATE fas_Depre_rates " +
								"SET status ='C', " +								
								"  updated_date = systimestamp " +
								"WHERE financial_year = ? " +
								" AND Depreciation_cate_code = ?";
		   	 PreparedStatement ps = connection.prepareStatement(sqlDelete);
		   	 ps.setString(1, FinYear);
		   	 ps.setInt(2, DepreciationCode);
		   	 ps.executeUpdate();
             xml=xml+"<flag>success</flag>";
             xml=xml+"<DepreciationCode>" + DepreciationCode + "</DepreciationCode>";
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
             String sqlUpdate = "UPDATE fas_Depre_rates " +
								"SET Depreciation_rate = ?, " +
								"  updated_by_userid = ?, " +
								"  updated_date = systimestamp " +
								"WHERE financial_year = ? " +
								" AND Depreciation_cate_code = ?";
		   	 PreparedStatement ps = connection.prepareStatement(sqlUpdate);
		   	 ps.setFloat(1,DepreciationRate);
		   	 ps.setString(2,userid);
		   	 ps.setString(3,FinYear);
		   	 ps.setInt(4,DepreciationCode);
		   	 ps.executeUpdate();
             xml=xml+"<flag>success</flag>";
             xml=xml+"<DepreciationCode>" + DepreciationCode + "</DepreciationCode>";
             xml=xml+"<FinancialYear>" + FinYear + "</FinancialYear>";
             xml=xml+"<DepreciationRate>" + DepreciationRate + "</DepreciationRate>";
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
             				 "INTO fas_Depre_rates" +
             				 "(Depreciation_cate_code,   financial_year,   Depreciation_rate,   " +
             				 "updated_by_userid,   updated_date, status) " +
							 "VALUES(?,?,?,?,systimestamp,'L')";
		   	 PreparedStatement ps = connection.prepareStatement(sqlAdd);
		   	 ps.setInt(1, DepreciationCode);
		   	 ps.setString(2, FinYear);
		   	 ps.setFloat(3, DepreciationRate);
		   	 ps.setString(4, userid);
		   	 ps.executeUpdate();
             xml=xml+"<flag>success</flag>";
             xml=xml+"<DepreciationCode>" + DepreciationCode + "</DepreciationCode>";
             xml=xml+"<FinancialYear>" + FinYear + "</FinancialYear>";
             xml=xml+"<DepreciationRate>" + DepreciationRate + "</DepreciationRate>";
            }
            catch(Exception e1)
            {
            	System.out.println("Exception in Adding record ===> "+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
            pw.println();
        }
      else if(strCommand.equals("Get"))
        { 
        	System.out.println("\n*************\nGet\n**************\n");
            xml="<response><command>Get</command>";
            try 
            {
            	String qry_res="SELECT financial_year, " +
				 "  Depreciation_cate_code, " +
				 "  Depreciation_rate, " +
				 "  status " +
				 "FROM fas_depre_rates " +
				 "WHERE financial_year = '"+ FinYear+"'";
             result = statement.executeQuery(qry_res);
            // System.out.println("result.......>"+qry_res);
             try
             {
            	 xml=xml+"<flag>success</flag>";
                 while(result.next())
                 { 
                	 
                	System.out.println("status "+result.getString("status")); 
                   DepreciationCode=result.getInt("Depreciation_cate_code");
                   FinYear=result.getString("financial_year");
                   DepreciationRate=result.getFloat("Depreciation_rate");
                   xml=xml+ "<DepreciationCode>" + DepreciationCode + "</DepreciationCode>" ;
                   xml=xml+"<FinancialYear>" + FinYear + "</FinancialYear>" ;
                   xml=xml+"<DepreciationRate>" + DepreciationRate + "</DepreciationRate>" ;
                   xml=xml+"<status>"+result.getString("status")+"</status>";
                  
                       
                        rset=statement1.executeQuery("SELECT depreciation_category FROM fas_depre_category_mst where Depreciation_cate_code="+DepreciationCode);
                // System.out.println("rset"+rset);
                    while(rset.next()) 
                  {
                	  desc_categry =rset.getString("depreciation_category");
                  } 
                  
                   xml=xml+"<DepreciationDesc>"+desc_categry+"</DepreciationDesc>"; 
                   }
             }
                   catch (Exception e)
{
					System.out.println(e);
				}	
                   result.close();
            }
             
           
          
            catch(Exception e1)
            {
            	System.out.println("Exception is in Get"+e1);
            	xml=xml+"<flag>failure</flag>";
            }
            xml=xml+"</response>";
            System.out.println(xml);
        }
                pw.write(xml);
                pw.flush();
                pw.close();
               System.out.println("java finished"); 
               
        
    }
}
