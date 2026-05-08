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


public class Assets_Major_Classification_Serv extends HttpServlet 
{
    private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";
  
    

    public void init(ServletConfig config) throws ServletException
    {
        super.init(config);
    }

    public void doGet(HttpServletRequest request, 
                      HttpServletResponse response) throws ServletException, IOException 
    {
    	System.out.println("list servlet:::::::");
    	PrintWriter pw = response.getWriter();
    	response.setContentType(CONTENT_TYPE);
        String strCommand = "",updatedby="";         
        String xml="",flag="";
        Timestamp ts=null;
        Connection con=null;
        Statement st=null;
        ResultSet rs,rs1=null;
        PreparedStatement ps=null;
      //  int count=0;
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
       
          	response.setContentType("text/xml");
          	response.setHeader("Cache-Control","no-cache");
          	HttpSession session=request.getSession(false);
            session =request.getSession(false);
            updatedby=(String)session.getAttribute("UserId");
            long l=System.currentTimeMillis();
            ts=new Timestamp(l);
            System.out.println(updatedby);
            System.out.println(ts);
            int count=0;
       
            strCommand = request.getParameter("command");      
            System.out.println("command====================>"+strCommand);
           // int ass_code = Integer.parseInt(request.getParameter("ass_code"));
            String ass_desc = request.getParameter("ass_desc");
            String ass_type = request.getParameter("ass_type");
            
            String major_class = request.getParameter("major_class");
            String folio_maintained=request.getParameter("folio_maintained");
            String mc_applicable = request.getParameter("mc_applicable");
            String ass_depreciable = request.getParameter("ass_depreciable");            
            xml="<response>";
            if(strCommand.equalsIgnoreCase("Add"))
            {
            	int alios_code = Integer.parseInt(request.getParameter("alios_code"));
            	xml=xml+"<command>Add</command>";
                System.out.println("inside add");
                try
                { 
		            	 rs=st.executeQuery("select asset_major_class_code from FAS_MST_ASSETS_CLASS group by asset_major_class_code having asset_major_class_code=(select max(asset_major_class_code) from FAS_MST_ASSETS_CLASS)");
		            	 System.out.println("select asset_major_class_code from FAS_MST_ASSETS_CLASS group by asset_major_class_code having asset_major_class_code=(select max(asset_major_class_code) from FAS_MST_ASSETS_CLASS)");
		                 if(rs.next()) 
		                 {
		                    count = rs.getInt(1);                                                   
		                 }
		                 count=count+1;       
		                 System.out.println("count:"+count);
		            	 String sql="insert into FAS_MST_ASSETS_CLASS(asset_major_class_code,asset_major_class_desc,asset_type_code,alias_code,major_class_asper_manual,individual_folio_maintained,minor_class_applicable,is_depreciable,updated_by_user_id,updated_date,status) values(?,?,?,?,?,?,?,?,?,?,'L')";
		                 ps=con.prepareStatement(sql);
		                 ps.setInt(1,count);
		                 System.out.println("asset_major_class_code :"+count);
		                 ps.setString(2,ass_desc);
		                 System.out.println("asset_major_class_desc :"+ass_desc);
		                 ps.setString(3,ass_type);
		                 System.out.println("asset_type :"+ass_type);
		                 ps.setInt(4,alios_code);
		                 System.out.println("alios_code :"+alios_code);
		                 ps.setString(5,major_class);         
		                 System.out.println("major_class :"+major_class);
		                 ps.setString(6,folio_maintained);
		                 System.out.println("folio_maintained :"+folio_maintained);
		                 ps.setString(7,mc_applicable);
		                 System.out.println("mc_applicable :"+mc_applicable);
		                 ps.setString(8,ass_depreciable);              
		                 System.out.println("ass_depreciable :"+ass_depreciable);		                 
		                 ps.setString(9,updatedby);
		                 System.out.println("updatedby :"+updatedby);
		                 ps.setTimestamp(10,ts);
		                 System.out.println("ts :"+ts);
		                 int num=ps.executeUpdate();
		                 if(num>0) 
		                 {
		                      System.out.println("Inserted Successfully");		                      
		                      xml=xml+"<flag>Success</flag>";
		                 }
		                 else
		                 {
		                     System.out.println("insertion failure");
		                     xml=xml+"<flag>failure</flag>";
		                 }
                }
                catch(Exception e)
                {
                	System.out.println("Err in insertion:"+e.getMessage());
                }
            }
            else if(strCommand.equalsIgnoreCase("Update"))
            {
            	System.out.println("inside update");
            	xml=xml+"<command>Update</command>";
            	count=Integer.parseInt(request.getParameter("ass_code"));
            	int alios_code = Integer.parseInt(request.getParameter("alios_code"));
                System.out.println("inside add");
                try
                { 
		            	    
		            	 String sql="update FAS_MST_ASSETS_CLASS set asset_major_class_desc=?,asset_type_code=?,alias_code=?,major_class_asper_manual=?,individual_folio_maintained=?,minor_class_applicable=?,is_depreciable=?,updated_by_user_id=?,updated_date=? where asset_major_class_code=?";
		                 ps=con.prepareStatement(sql);		                 
		                 ps.setString(1,ass_desc);
		                 ps.setString(2,ass_type);
		                 ps.setInt(3,alios_code);
		                 ps.setString(4,major_class);                
		                 ps.setString(5,folio_maintained);
		                 ps.setString(6,mc_applicable);
		                 ps.setString(7,ass_depreciable);                                                                                                  		                 
		                 ps.setString(8,updatedby);
		                 ps.setTimestamp(9,ts);
		                 ps.setInt(10,count);
		                 int num=ps.executeUpdate();
		                 if(num>0) 
		                 {
		                      System.out.println("Updated Successfully");		                      
		                      xml=xml+"<flag>Success</flag>";
		                 }
		                 else
		                 {
		                     System.out.println("Failed to Update");
		                     xml=xml+"<flag>failure</flag>";
		                 }
                }
                catch(Exception e)
                {
                	System.out.println("Err in insertion:"+e.getMessage());
                }
            }
            else if(strCommand.equalsIgnoreCase("Delete"))
            {
            	xml=xml+"<command>Delete</command>";
            	count=Integer.parseInt(request.getParameter("ass_code"));
                System.out.println("inside delete");
                try
                { 
		            	    
		            	 String sql="update FAS_MST_ASSETS_CLASS set status='C' where asset_major_class_code=?";
		                 ps=con.prepareStatement(sql);		                 
		                 ps.setInt(1,count);		                
		                 int num=ps.executeUpdate();
		                 if(num>0) 
		                 {
		                      System.out.println("Deleted Successfully");		                      
		                      xml=xml+"<flag>Success</flag>";
		                 }
		                 else
		                 {
		                     System.out.println("Failed to Delete");
		                     xml=xml+"<flag>failure</flag>";
		                 }
                }
                catch(Exception e)
                {
                	System.out.println("Err in insertion:"+e.getMessage());
                }
            }
            if(strCommand.equalsIgnoreCase("Load"))
            { response.setContentType("text/xml");
              response.setHeader("Cache-Control","no-cache");
            	String class_code=request.getParameter("ass_code"); 
            	System.out.println("class count:"+class_code);
            	xml=xml+"<command>Load</command>";
                System.out.println("inside Load");
                try
                { 		            	      
		            	System.out.println("inside try"); 
                		String sql="select asset_major_class_code,trim(asset_major_class_desc) as class_desc,asset_type_code,alias_code,decode(major_class_asper_manual,null,'-',major_class_asper_manual) as major_class,individual_folio_maintained,minor_class_applicable,is_depreciable,status from FAS_MST_ASSETS_CLASS ";
		            	 		if(class_code!=null)  // For LoadRecord Funtion
		            	 				sql=sql+" where asset_major_class_code="+Integer.parseInt(class_code);
		            	 	    sql=sql+" order by asset_major_class_code ";		//For LoadRecord as well as Load Function               
		                 System.out.println("SQL:"+sql);
		            	 rs=st.executeQuery(sql);
		                 while(rs.next()){
		                     // count++;		                	 
		                      xml=xml+"<leng>";                                          
		                	  xml=xml+"<class_code>"+rs.getInt("asset_major_class_code")+"</class_code>";                  
		                	  xml=xml+"<class_desc><![CDATA["+rs.getString("class_desc")+"]]></class_desc>";
		                	  xml=xml+"<asset_type>"+rs.getString("asset_type_code")+"</asset_type>";
		                	  xml=xml+"<alias_code>"+rs.getString("alias_code")+"</alias_code>";
		                	  xml=xml+"<major_class>"+rs.getString("major_class")+"</major_class>";
		                	  xml=xml+"<individual_folio>"+rs.getString("individual_folio_maintained")+"</individual_folio>";
		                	  xml=xml+"<minor_class>"+rs.getString("minor_class_applicable")+"</minor_class>";
		                	  xml=xml+"<depreciable>"+rs.getString("is_depreciable")+"</depreciable>";
		                	  xml=xml+"<status>"+rs.getString("status")+"</status>";
                                          xml=xml+"<count>"+count+"</count>";
		                     xml=xml+"</leng>";
		                     count++;                                     
		                 }
                    System.out.println("count"+count);
		                 if(count>0)
		                	 xml=xml+"<flag>Success</flag>";
		                 else
		                	 xml=xml+"<flag>failure</flag>";
                }
                catch(Exception e)
                {
                	System.out.println("Err in loading record:"+e.getMessage());
                }
            }
            xml=xml+"</response>";
            System.out.println("xml: "+xml);   
            pw.println(xml);
	}
                
                        
}


