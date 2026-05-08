package Servlets.FAS.FAS1.AccountHeadDirectory.servlets;

import java.io.IOException;
import java.io.PrintWriter;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.sql.Timestamp;

import java.util.ResourceBundle;

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;


public class Assets_Classification_AC_HeadsServ extends HttpServlet 
{
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
  
    

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
    	PrintWriter pw = response.getWriter();
    	response.setContentType(CONTENT_TYPE);
        String strCommand = "",updatedby="";         
        String xml="",flag="",sql="",set_flag="";
        Timestamp ts=null;
        Connection con=null;
        Statement st=null;
        ResultSet rs,rs1=null;
        PreparedStatement ps=null;
        int code=0;
        try
          {
             		ResourceBundle res=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
                    String ConnectionString="";

                    String strDriver=res.getString("Config.DATA_BASE_DRIVER");
                    String strdsn=res.getString("Config.DSN");
                    String strhostname=res.getString("Config.HOST_NAME");
                    String strportno=res.getString("Config.PORT_NUMBER");
                    String strsid=res.getString("Config.SID");
                    String strdbusername=res.getString("Config.USER_NAME");
                    String strdbpassword=res.getString("Config.PASSWORD");

                    //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
                    ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
                    Class.forName(strDriver.trim());
                    con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
              try
              {
                st=con.createStatement();
                con.clearWarnings();
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
          response.setContentType("text/xml");
          response.setHeader("Cache-Control","no-cache");
          HttpSession session=request.getSession(false);
          session =request.getSession(false);
          updatedby=(String)session.getAttribute("UserId");
          long l=System.currentTimeMillis();
          ts=new Timestamp(l);
          System.out.println(updatedby);
          System.out.println(ts);
       
       
          strCommand = request.getParameter("command");      
          System.out.println("command====================>"+strCommand);
          int class_code = Integer.parseInt(request.getParameter("class_code"));               
          xml="<response>";
          if(strCommand.equalsIgnoreCase("Add"))
          {
	            	int ac_head_code = Integer.parseInt(request.getParameter("ac_head_code"));        
	            	xml=xml+"<command>Add</command>";
	                System.out.println("inside add");
	                try
	                { 		            	 			            	 
			                 ps=con.prepareStatement("insert into fas_asset_ac_heads(asset_major_class_code,account_head_code,updated_by_user_id,updated_date) values(?,?,?,?)");	
			                 ps.setInt(1,class_code);
			                 ps.setInt(2,ac_head_code);
			                 ps.setString(3,updatedby);
			                 ps.setTimestamp(4,ts);
			                 int num=ps.executeUpdate();
			                 if(num>0) 
			                 {
			                      System.out.println("Inserted Successfully");		                      
			                      set_flag="success";
			                 }
			                 else
			                 {
			                     System.out.println("insertion failure");
			                     set_flag="failure";
			                 }
	                }
	                catch(Exception e)
	                {
	                	System.out.println("Err in insertion:"+e.getMessage());
	                	set_flag="failure";
	                }
          }
          else if(strCommand.equalsIgnoreCase("Update"))
          {
        	  		int ac_head_code = Integer.parseInt(request.getParameter("ac_head_code")); 
	            	xml=xml+"<command>Update</command>";            
	                System.out.println("inside update");
	                try
	                { 			            	    
			            	 sql="update fas_asset_ac_heads set account_head_code=?,updated_by_user_id=?,updated_date=? where asset_major_class_code=?";
			                 ps=con.prepareStatement(sql);	
			                 ps.setInt(1,ac_head_code);
			                 ps.setString(2,updatedby);
			                 ps.setTimestamp(3,ts);
			                 ps.setInt(4,class_code);			                
			                 int num=ps.executeUpdate();
			                 if(num>0) 
			                 {
			                      System.out.println("Updated Successfully");		                      
			                      set_flag="success";
			                 }
			                 else
			                 {
			                     System.out.println("Failed to Update");
			                     set_flag="failure";
			                 }
	                }
	                catch(Exception e)
	                {
	                	System.out.println("Err in insertion:"+e.getMessage());
	                	set_flag="failure";
	                }
          }
          else if(strCommand.equalsIgnoreCase("Delete"))
          {
        	  		int ac_head_code = Integer.parseInt(request.getParameter("ac_head_code")); 
        	  		xml=xml+"<command>Delete</command>";
	                System.out.println("inside delete");
	                try
	                { 			            	    
			            	 sql="delete from fas_asset_ac_heads where asset_major_class_code=? and account_head_code=?";
			                 ps=con.prepareStatement(sql);		                 
			                 ps.setInt(1,class_code);
			                 ps.setInt(2,ac_head_code);	
			                 int num=ps.executeUpdate();
			                 if(num>0) 
			                 {
			                      System.out.println("Deleted Successfully");		                      
			                      set_flag="success";
			                 }
			                 else
			                 {
			                     System.out.println("Failed to Delete");
			                     set_flag="failure";
			                 }
	                }
	                catch(Exception e)
	                {
	                	System.out.println("Err in insertion:"+e.getMessage());
	                	set_flag="failure";
	                }
          }
          if(strCommand.equalsIgnoreCase("LoadRecord"))
          {            		            	
	            	xml=xml+"<command>LoadRecord</command>";
	            	set_flag="success";
          }
          if(set_flag.equals("success"))
          {
	                System.out.println("inside Load");
	                try
	                { 		            	      				            	
		                		
				            	 sql="select c.ASSET_MAJOR_CLASS_DESC, a.account_head_code,b.account_head_desc from fas_asset_ac_heads a,com_mst_account_heads b,FAS_MST_ASSETS_CLASS c where a.account_head_code=b.account_head_code and a.ASSET_MAJOR_CLASS_CODE=c.ASSET_MAJOR_CLASS_CODE and a.asset_major_class_code="+class_code;		               
				                 System.out.println("SQL:"+sql);
				            	 rs=st.executeQuery(sql);
				                 while(rs.next()) 
				                 {
				                      code++;
				                      xml=xml+"<count>";
				                	  xml=xml+"<Major_Desc>"+rs.getString("ASSET_MAJOR_CLASS_DESC")+"</Major_Desc>";
				                	  xml=xml+"<head_code>"+rs.getInt("account_head_code")+"</head_code>";
				                	  xml=xml+"<head_desc>"+rs.getString("account_head_desc")+"</head_desc>";				                	
				                	  xml=xml+"</count>";
				                 }
				                 if(code>0)
				                	 xml=xml+"<flag>Success</flag>";
				                 else
				                	 xml=xml+"<flag>NoRecords</flag>";
	                }
	                catch(Exception e)
	                {
	                	System.out.println("Err in loading record:"+e.getMessage());
	                }
          }
          else
          {
            	xml=xml+"<flag>failure</flag>";
          }
          xml=xml+"</response>";
          System.out.println("xml: "+xml);   
          pw.println(xml);
	}
                
                        
}

