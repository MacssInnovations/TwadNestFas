package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Depreciation_Category_Master extends HttpServlet 
{
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
        

    }

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
        Connection connection=null;
        Statement statement=null;
        ResultSet result=null;
        ResultSet rs2=null;
        PreparedStatement ps=null;
        PreparedStatement ps2=null;
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
        String strCommand = "Get"; 
        String xml="";
        int DepreCode=0;
        String DepreDesc="";
        long l=System.currentTimeMillis();
        Timestamp ts=new Timestamp(l);
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
        	DepreCode= Integer.parseInt(request.getParameter("DepreciationCatCode"));
        	System.out.println("DepreCode : " + DepreCode);
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching DepreCatCode ===> " + e);
        }        
        
        try
        {
        	DepreDesc = request.getParameter("DepreciationCatDesc");
            System.out.println("DepreDesc : "+ DepreDesc);
        }
        catch(Exception e)
        { 
            System.out.println("Exception fetching DepreCatDesc ===> " + e);
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
             String sqlDelete = "UPDATE fas_depre_category_mst SET status='C'" +
             					"WHERE depreciation_cate_code = ?";
		   	  ps = connection.prepareStatement(sqlDelete);
		   	 ps.setInt(1, DepreCode);
		   	 ps.executeUpdate();
             xml=xml+"<flag>success</flag>";
             xml=xml+"<DepreciationCode>" + DepreCode + "</DepreciationCode>";
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
             String sqlUpdate = "UPDATE fas_depre_category_mst " +
								"SET depreciation_category = ?, " +  
								"  updated_by_userid = ?, " +  
								"  updated_date = systimestamp " +  
								"WHERE depreciation_cate_code = ?";
		   	 ps = connection.prepareStatement(sqlUpdate);
		   	 ps.setString(1,DepreDesc);
		   	 ps.setString(2,userid);
		   	 ps.setInt(3,DepreCode);
		   	 ps.executeUpdate();
             xml=xml+"<flag>success</flag>";
             xml=xml+"<DepreciationCode>" + DepreCode + "</DepreciationCode>";
             xml=xml+"<DepreciationDesc>" + DepreDesc + "</DepreciationDesc>";
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
        String CONTENT_TYPE = "text/xml; charset=windows-1252";
        response.setContentType(CONTENT_TYPE);
        
        xml = "<response><command>Add</command>";
        System.out.println("add");
        //System.out.println(cmbBankId);
         try 
                   {
                    String sqlAdd_SubQry = "SELECT MAX(depreciation_cate_code) as maxDepreCode " +  
                                                               "FROM fas_depre_category_mst";
                                 ps = connection.prepareStatement(sqlAdd_SubQry);
                                result = ps.executeQuery();
                                while(result.next())
                                {
                                       DepreCode = result.getInt("maxDepreCode");
                                       if(DepreCode > 0)
                                       {
                                               DepreCode++;
                                       }
                                       else
                                       {
                                               DepreCode = 1;
                                       }
                                }
                                System.out.println("NEW DepreCode : "+DepreCode);
                   }
                   catch(Exception e1)
                   {
                       System.out.println("Exception in fetching record ===> "+e1);
                   }

         int maxdepcode=0;
      /*  try {
            ps2 = connection.prepareStatement("SELECT nvl(max(depreciation_cate_code),0) AS maxDepreCode FROM fas_depre_category_mst ?");
            rs2 = ps2.executeQuery();

            while (rs2.next()) {
                maxdepcode  = rs2.getInt("maxDepreCode");
                System.out.println(maxdepcode);
            }
            if (maxdepcode == 0) {
                maxdepcode=1; 
            }
            else {
                maxdepcode++;
            }

            System.out.println("Maximum DepreCode is ==" + maxdepcode);
           
            ps2.close();
            rs2.close();

        } catch (Exception e) {
            System.out.println("Failed to Maximum DepreCode " + e);
        }*/

        

        try {

            ps =  connection.prepareStatement("insert into  fas_depre_category_mst(depreciation_cate_code,depreciation_category,updated_by_userid,updated_date,status) values(?,?,?,?,'L')");

            ps.setInt(1, DepreCode);
            ps.setString(2, DepreDesc);
            ps.setString(3, userid);
            ps.setTimestamp(4,ts);
            ps.executeUpdate();
            xml = xml + "<flag>success</flag>";
            xml=xml+"<DepreciationCode>" + DepreCode + "</DepreciationCode>";
            xml=xml+"<DepreciationDesc>" + DepreDesc + "</DepreciationDesc>";
            //System.out.println("here is ok");


        } catch (Exception e) {
            System.out.println("catch..HERE.in load head code." + e);
            xml = xml + "<flag>failure</flag>";
        }
        xml = xml + "</response>";
        System.out.println(xml);
       // out.println(xml);
        }
        else if(strCommand.equals("Get"))
        { 
        	System.out.println("\n*************\nGet\n**************\n");
            xml="<response><command>Get</command>";
            try 
            {
             result = statement.executeQuery("SELECT depreciation_cate_code,depreciation_category,status FROM fas_depre_category_mst " +  
						"ORDER BY depreciation_cate_code");
             try
             {
            	 xml=xml+"<flag>success</flag>";
                 while(result.next())
                 { 
                   DepreCode=result.getInt("depreciation_cate_code");
                   DepreDesc=result.getString("depreciation_category");
                   //System.out.println("status "+result.getString("status"));
                   xml=xml+ "<DepreciationCode>" + DepreCode + "</DepreciationCode><DepreciationDesc>" + DepreDesc + "</DepreciationDesc>";
                   xml+="<status>"+result.getString("status")+"</status>";
                   //xml +="<status>"+result.getString("status")+"</status>";
                   System.out.println("\n------------------------------\nDepreCode: "+DepreCode+"\nDepreDesc: "+DepreDesc+"\n------------------------------\n");
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
