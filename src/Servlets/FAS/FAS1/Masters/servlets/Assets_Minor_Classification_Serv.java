package Servlets.FAS.FAS1.Masters.servlets;

import java.io.IOException;
import java.io.PrintWriter;
import java.sql.*;
import java.util.*;
import javax.servlet.*;
import javax.servlet.http.*;

public class Assets_Minor_Classification_Serv extends HttpServlet 
{
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
    

    public void init(ServletConfig config) throws ServletException {
        super.init(config);
    }

    public void doGet(HttpServletRequest request,HttpServletResponse response) throws ServletException, IOException
    {
    	System.out.println("\n\n*******************************************************\n");
        Connection connection=null;
        ResultSet result=null;
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
  
           }
          catch(Exception e)
          {
             System.out.println("Exception in openeing connection:"+e);
          }
        response.setContentType(CONTENT_TYPE);
        String strCommand = ""; 
        String xml="<response>";
        int asset_major_class_code = 0;
        int asset_minor_class_code = 0; 
        String asset_minor_class_desc = null;
        
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
         System.out.println("session id is:"+userid);
        
        
        response.setContentType("text/xml");
        PrintWriter pw=response.getWriter();    
        response.setHeader("Cache-Control","no-cache");
        try
        {
          strCommand = request.getParameter("command");      
        }
        catch(Exception e)
        {
          e.printStackTrace();
        }
                
        try
        {
        	asset_major_class_code= Integer.parseInt(request.getParameter("asset_major_class_code"));
        	System.out.println("asset_major_class_code : " + asset_major_class_code);
          
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'asset_major_class_code' parameter ==> "+ e);
        }        

        try
        {
        	asset_minor_class_code= Integer.parseInt(request.getParameter("asset_minor_class_code"));
        	System.out.println("asset_minor_class_code : " + asset_minor_class_code);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'asset_minor_class_code' parameter ==> "+ e);
        }        

        try
        {
        	asset_minor_class_desc= request.getParameter("asset_minor_class_desc");
        	System.out.println("asset_minor_class_desc : " + asset_minor_class_desc);
        }
        catch(Exception e)
        { 
            System.out.println("Exception getting 'asset_minor_class_desc' parameter ==> "+ e);
        }        

        
        
        
        if(strCommand.equalsIgnoreCase("Delete"))
        {
            xml+="<command>Delete</command>";
            try
            {
                PreparedStatement pstmt = connection.prepareStatement("UPDATE fas_asset_minor_classification " +
                														"SET status                   ='C' " +
                														"WHERE asset_major_class_code = ? " +
                														"AND asset_minor_class_code   = ?");
                System.out.println(pstmt);
                pstmt.setInt(1,asset_major_class_code);
                pstmt.setInt(2,asset_minor_class_code);
                pstmt.executeUpdate();
                xml=xml+"<flag>success</flag>" +
                		"<asset_major_class_code>"+asset_major_class_code+"</asset_major_class_code>" +
                		"<asset_minor_class_code>"+asset_minor_class_code+"</asset_minor_class_code>";
                pstmt.close();
            }
            catch(SQLException e) {
                System.out.println("Exception executing 'Delete Query'");
            	xml=xml+"<flag>failure</flag>";
            }
        }

        
        
        else if(strCommand.equalsIgnoreCase("Update"))
        {
            xml+="<command>Update</command>";
            try
            {
                PreparedStatement pstmt = connection.prepareStatement("UPDATE fas_asset_minor_classification " +
																		"SET asset_minor_class_desc = ?, " +
																		"  updated_by_user_id = ?, " +
																		"  updated_date = systimestamp " +
																		"WHERE asset_major_class_code = ? " +
																		" AND asset_minor_class_code = ?");
                System.out.println(pstmt);
                pstmt.setString(1,asset_minor_class_desc);
                pstmt.setString(2,userid);
                pstmt.setInt(3,asset_major_class_code);
                pstmt.setInt(4,asset_minor_class_code);
                pstmt.executeUpdate();
                xml=xml+"<flag>success</flag>" +
		        		"<asset_major_class_code>"+asset_major_class_code+"</asset_major_class_code>" +
		        		"<asset_minor_class_code>"+asset_minor_class_code+"</asset_minor_class_code>" +
						"<asset_minor_class_desc>"+asset_minor_class_desc+"</asset_minor_class_desc>";
                pstmt.close();
            }
            catch(SQLException e) {
                System.out.println("Exception executing 'Update Query'");
            	xml=xml+"<flag>failure</flag>";
            }
        }
        
        
        
        else if(strCommand.equalsIgnoreCase("Add"))
        {
            xml+="<command>Add</command>";
            try
            {
            	String maxMinorCode = "SELECT MAX(asset_minor_class_code) AS maxcode " +
										"FROM fas_asset_minor_classification " +
										"WHERE asset_major_class_code = " + asset_major_class_code;
            	System.out.println("maxMinorCode query ==> " + maxMinorCode);
                PreparedStatement pstmt = connection.prepareStatement(maxMinorCode);
                System.out.println(pstmt);
               	result = pstmt.executeQuery();
                if(result.next())
                {
                	asset_minor_class_code = result.getInt("maxcode");
                	if(asset_minor_class_code>0)
                	{	
                		asset_minor_class_code++;
                	}
                	else
                	{
                		asset_minor_class_code = 1;
                	}
                }
	            pstmt.close();
            }
            catch(SQLException e) {
                System.out.println("Exception fetching 'maxMinorCode'");
            }

            try
            {
                PreparedStatement pstmt = connection.prepareStatement(" INSERT " +
								                		"INTO fas_asset_minor_classification" +
								                		"	(asset_major_class_code,   asset_minor_class_code,  " +
								                		"	 asset_minor_class_desc,   updated_by_user_id,  " +
								                		"	 updated_date,status) " +
														"VALUES(?,?,?,?,systimestamp,'L')");
                System.out.println(pstmt);
                pstmt.setInt(1,asset_major_class_code);
                pstmt.setInt(2,asset_minor_class_code);
                pstmt.setString(3,asset_minor_class_desc);
                pstmt.setString(4,userid);
                pstmt.executeUpdate();
                xml=xml+"<flag>success</flag>" +
                		"<asset_major_class_code>"+asset_major_class_code+"</asset_major_class_code>" +
                		"<asset_minor_class_code>"+asset_minor_class_code+"</asset_minor_class_code>" +
        				"<asset_minor_class_desc>"+asset_minor_class_desc+"</asset_minor_class_desc>"+
        				"<status>"+"LIVE"+"</status>";
                pstmt.close();
            }
            catch(SQLException e) {
                System.out.println("Exception executing 'Add Query'");
            	xml=xml+"<flag>failure</flag>";
            }
        }
        
        
        
        else if(strCommand.equals("Get"))
        { 
        	System.out.println("GET\n---------------------\n");
        	xml+="<command>Get</command>";
            try
            {
            	
            	String whrMjrCode = "WHERE a.asset_major_class_code ";
            	if(asset_major_class_code > 0)
            	{
            		whrMjrCode+= "= " + asset_major_class_code;
            	}
            	else
            	{
            		whrMjrCode+= "is not null";
            	}
            	System.out.println("whrMjrCode ==> " + whrMjrCode);
            	
                PreparedStatement pstmt = connection.prepareStatement("SELECT a.asset_major_class_code, " +
											"  asset_major_class_desc, " +
											"  asset_minor_class_code, " +
											"  asset_minor_class_desc, " +
											"	a.status as status "+
											"FROM fas_asset_minor_classification a join FAS_MST_ASSETS_CLASS b " +
											"on a.asset_major_class_code = b.asset_major_class_code " +
											whrMjrCode +
											" ORDER BY asset_major_class_desc, asset_minor_class_code");
                System.out.println(pstmt);
                result = pstmt.executeQuery();
                xml=xml+"<flag>success</flag>";
                while(result.next())
                {
                	xml+="<Minor_leng>";
                   xml+="<asset_major_class_code>"+result.getInt("asset_major_class_code")+"</asset_major_class_code>" +
                		"<asset_major_class_desc><![CDATA["+result.getString("asset_major_class_desc")+"]]></asset_major_class_desc>" +
                		"<asset_minor_class_code>"+result.getInt("asset_minor_class_code")+"</asset_minor_class_code>" +
        				"<asset_minor_class_desc><![CDATA["+result.getString("asset_minor_class_desc")+"]]></asset_minor_class_desc>" +
        				"<status><![CDATA["+result.getString("status")+"]]></status>";
                   xml+="</Minor_leng>";
                }
	            pstmt.close();
            }
            catch(SQLException e) {
                System.out.println("Exception executing 'Get Query'");
            	xml=xml+"<flag>failure</flag>";
            }
        }
        
        
        
        else if(strCommand.equals("LoadMajorClass"))
        {
        	xml+="<command>LoadMajorClass</command>";
        	try
        	{
        		PreparedStatement pstmt = connection.prepareStatement("SELECT asset_major_class_code, " +
																		"  asset_major_class_desc " +
																		"FROM FAS_MST_ASSETS_CLASS where status='L'");
        		System.out.println(pstmt);
        		result = pstmt.executeQuery();
	            xml=xml+"<flag>success</flag>";
	            while(result.next())
	            {
	            	xml+="<asset_major_class_code>"+result.getInt("asset_major_class_code")+"</asset_major_class_code>" +
	        			 "<asset_major_class_desc><![CDATA["+result.getString("asset_major_class_desc")+"]]></asset_major_class_desc>";
	            }
	            pstmt.close();
	        }
        	catch(SQLException e) {
        		System.out.println("Exception executing 'LoadMajorClass Query'");
        		xml=xml+"<flag>failure</flag>";
        	}
        } 



        xml=xml+"</response>";
        System.out.println("xml is : " + xml);
        pw.write(xml);
        pw.flush();
        pw.close();
        System.out.println("\n*******************************************************\n\n");
    }
}
