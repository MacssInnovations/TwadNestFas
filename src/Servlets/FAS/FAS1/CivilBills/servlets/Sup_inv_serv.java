package Servlets.FAS.FAS1.CivilBills.servlets;

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

import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class Sup_inv_serv
 */
public class Sup_inv_serv extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";   
    /**
     * @see HttpServlet#HttpServlet()
     */
    public Sup_inv_serv() {
        super();
        // TODO Auto-generated constructor stub
    }

	/**
	 * @see HttpServlet#doGet(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
			response.setContentType(CONTENT_TYPE);
			response.setHeader("Cache-Control", "no-cache");
			
	        PrintWriter out = response.getWriter();
	        Connection con=null;
	        PreparedStatement ps,ps1,ps2,ps3;
	        Statement st=null;
	        ResultSet result=null,result1=null,result2=null,result3=null;
	        int eid=0,unitid=0,offid=0,major=0,count=0,minor=0,sub=0;
	        String cmd,xml="",checkDesc="",apply="",support="";
	        cmd=request.getParameter("command");
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
	          
	        HttpSession session=request.getSession(false);
	        String update_user=(String)session.getAttribute("UserId");
	        UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
	        eid=empProfile.getEmployeeId();
	        System.out.println("employee id:"+eid);
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
	         long l=System.currentTimeMillis();
	         Timestamp ts=new Timestamp(l);
	        
	         try{unitid= Integer.parseInt(request.getParameter("unitid"));} 
	         catch(Exception e1){System.out.println("Err in getting offid "+e1.getMessage()); }
	         System.out.println("unitid"+unitid);
	         try
	         {offid= Integer.parseInt(request.getParameter("offid")); }
	         catch(Exception e2){ System.out.println("Err in getting offid "+e2.getMessage()); }
	         System.out.println("offid"+offid); 
	        
	        if(cmd.equalsIgnoreCase("majorType"))
	         {
	        	 System.out.println("majorType");
	             xml="<response><command>major</command>"; 
	              try 
	                      {
	                              ps = con.prepareStatement("select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where STATUS='L' order by BILL_MAJOR_TYPE_CODE");
	                              result = ps.executeQuery();                                
	                              while(result.next()) 
	                              {
	                                  xml=xml+"<mastercode>"+result.getInt("BILL_MAJOR_TYPE_CODE")+"</mastercode>";
	                                  xml=xml+"<masterdesc>"+result.getString("BILL_MAJOR_TYPE_DESC")+"</masterdesc>";
	                                  count++;
	                              }
	                              if(count>0)
	                                  xml=xml+"<flag>success</flag>";
	                              else
	                                  xml=xml+"<flag>failure</flag>";
	                      }
	                catch(Exception e) 
	                      {
	                              System.out.println("Exception in masterdesc ===> "+e);   
	                              xml=xml+"<flag>failure</flag>";  
	                      }
	                  xml=xml+"</response>";
	          }
	         else if(cmd.equalsIgnoreCase("minorType")) 
	         {
	             xml="<response><command>minor</command>"; 
	             try 
	                     {
	                         major=Integer.parseInt(request.getParameter("major"));
	                         ps = con.prepareStatement("select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE="+major);
	                         result = ps.executeQuery();
	                         xml=xml+"<flag>success</flag>";
	                         while (result.next()) 
	                             {
	                                 xml=xml+"<minorcode>"+result.getInt("BILL_MINOR_TYPE_CODE")+"</minorcode>";
	                                 xml=xml+"<minordesc>"+result.getString("BILL_MINOR_TYPE_DESC")+"</minordesc>";
	                             }
	                     }
	               catch(Exception e) 
	                     {
	                             System.out.println("Exception in minor ===> "+e);   
	                             xml=xml+"<flag>failure</flag>";  
	                     }
	                 xml=xml+"</response>";
	         }
	         else if(cmd.equalsIgnoreCase("subType")) 
	            {
	        	System.out.println(":::::::::::::sub type::::::::::");
	                xml="<response><command>subb</command>"; 
	                try 
	                        {
	                	 		major=Integer.parseInt(request.getParameter("major"));
	                            minor=Integer.parseInt(request.getParameter("minor"));	
	                            System.out.println("minor"+minor);
	                            ps = con.prepareStatement("select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE="+major+" and BILL_MINOR_TYPE_CODE="+minor);
	                            System.out.println("ps"+ps);
	                            result = ps.executeQuery();
	                            xml=xml+"<flag>success</flag>";
	                            while (result.next()) 
	                                {
	                            	    System.out.println("subdesc"+result.getString("BILL_SUB_TYPE_DESC"));
	                                    xml=xml+"<subcode>"+result.getInt("BILL_SUB_TYPE_CODE")+"</subcode>";
	                                    xml=xml+"<subdesc>"+result.getString("BILL_SUB_TYPE_DESC")+"</subdesc>";
	                                }   
	                        }
	                  catch(Exception e) 
	                        {
	                                System.out.println("Exception in minor ===> "+e);   
	                                xml=xml+"<flag>failure</flag>";  
	                        }
	                    xml=xml+"</response>";
	            }
	         else if(cmd.equalsIgnoreCase("listminor")) 
	         {
	             xml="<response><command>listminor</command>"; 
	             try 
	                     {
	                         major=Integer.parseInt(request.getParameter("major"));
	                         minor= Integer.parseInt(request.getParameter("minor"));
	                         ps = con.prepareStatement("select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=?");
	                         ps.setInt(1,major);
	                         ps.setInt(2,minor);
	                         result = ps.executeQuery();
	                         xml=xml+"<flag>success</flag>";
	                         while (result.next()) 
	                             {
	                                 xml=xml+"<minorcode>"+result.getInt("BILL_MINOR_TYPE_CODE")+"</minorcode>";
	                                 xml=xml+"<minordesc>"+result.getString("BILL_MINOR_TYPE_DESC")+"</minordesc>";
	                            System.out.println("minorcode::"+xml);
	                             }
	                     }
	               catch(Exception e) 
	                     {
	                             System.out.println("Exception in minor ===> "+e);   
	                             xml=xml+"<flag>failure</flag>";  
	                     }
	                 xml=xml+"</response>";
	         }
	         else if(cmd.equalsIgnoreCase("listSub")) 
	         {
	             xml="<response><command>listSub</command>"; 
	             try 
	                     {
	                         major=Integer.parseInt(request.getParameter("major"));
	                         minor= Integer.parseInt(request.getParameter("minor"));
	                         sub=Integer.parseInt(request.getParameter("sub"));
	                         ps = con.prepareStatement("select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and BILL_SUB_TYPE_CODE=? ");
	                         ps.setInt(1,major);
	                         ps.setInt(2,minor);
	                         ps.setInt(3,sub);
	                         result = ps.executeQuery();
	                         xml=xml+"<flag>success</flag>";
	                         while (result.next()) 
	                             {
	                        	 xml=xml+"<subcode>"+result.getInt("BILL_SUB_TYPE_CODE")+"</subcode>";
                                 xml=xml+"<subdesc>"+result.getString("BILL_SUB_TYPE_DESC")+"</subdesc>";
                                
	                             }
	                     }
	               catch(Exception e) 
	                     {
	                             System.out.println("Exception in minor ===> "+e);   
	                             xml=xml+"<flag>failure</flag>";  
	                     }
	                 xml=xml+"</response>";
	         }
	         else if(cmd.equalsIgnoreCase("add")) 
	         {
	        	 xml="<response><command>add</command>";
	        	 try{ major= Integer.parseInt(request.getParameter("major"));}
		         catch(Exception e2){ System.out.println("Err in getting major type "+e2.getMessage()); }
		         System.out.println("major"+major);
		         try{ minor= Integer.parseInt(request.getParameter("minor"));}
		         catch(Exception e2){ System.out.println("Err in getting minor type "+e2.getMessage()); }
		         System.out.println("minor"+minor);
		         try{ sub= Integer.parseInt(request.getParameter("sub"));}
		         catch(Exception e2){ System.out.println("Err in getting major type "+e2.getMessage()); }
		         System.out.println("sub"+sub);
	        	 support=request.getParameter("support");
	        	 try 
	             {
	            	 ps = con.prepareStatement("insert into FAS_SUP_INV_BILL_TYPES(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,SUPPORTING_INVOICE,UPDATED_BY_USERID,UPDATED_DATE,STATUS)values(?,?,?,?,?,?,?,?,?)");
	                 ps.setInt(1,unitid);
	                 ps.setInt(2,offid);
	                 ps.setInt(3,major);
	                 ps.setInt(4,minor);
	                 ps.setInt(5,sub);
	                 ps.setString(6,support);
	                 ps.setInt(7, eid);
	                 ps.setTimestamp(8,ts);
	                 ps.setString(9,"L");
	                 int i1=ps.executeUpdate();  
	                 System.out.println("i1::"+i1);
	                 if(i1>=1)
	                 {
	                	 	xml=xml+"<flag>success</flag>";
	                	 	 xml=xml+"<display>";
		                	ps1=con.prepareStatement("select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where BILL_MAJOR_TYPE_CODE="+major);
		                    result1=ps1.executeQuery();
		                    if(result1.next()){
		                    	xml=xml+"<majorCode>" +result1.getInt("BILL_MAJOR_TYPE_CODE")+ "</majorCode>";
		                    	xml=xml+"<majorDesc>" +result1.getString("BILL_MAJOR_TYPE_DESC")+ "</majorDesc>";
		                    }
		                    else{
		                    	xml=xml+"<majorCode>" +"0"+ "</majorCode>";
		                    	xml=xml+"<majorDesc>" +"--"+ "</majorDesc>";
		                    }
		                    ps2 = con.prepareStatement("select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE="+major+" and BILL_MINOR_TYPE_CODE="+minor);
	                        result2 = ps2.executeQuery();	
	                        if(result2.next()){
	                        	xml=xml+"<minorCode>" +result2.getInt("BILL_MINOR_TYPE_CODE")+ "</minorCode>";
		                    	xml=xml+"<minorDesc>" +result2.getString("BILL_MINOR_TYPE_DESC")+ "</minorDesc>";
	                        }
		                    else{
		                    	xml=xml+"<minorCode>" +"0"+ "</minorCode>";
		                    	xml=xml+"<minorDesc>" +"--"+ "</minorDesc>";
		                    }
	                        ps3=con.prepareStatement("select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE="+major+" and BILL_MINOR_TYPE_CODE="+minor+" and BILL_SUB_TYPE_CODE="+sub);
	                        result3 = ps3.executeQuery();
	                        if(result3.next()){
		                    	xml=xml+"<subCode>" +result3.getInt("BILL_SUB_TYPE_CODE")+ "</subCode>";
		                    	xml=xml+"<subDesc>" +result3.getString("BILL_SUB_TYPE_DESC")+ "</subDesc>";
		                    }
		                    else{
		                    	xml=xml+"<subCode>" +"0"+ "</subCode>";
		                    	xml=xml+"<subDesc>" +"--"+ "</subDesc>";
		                    }
	                        xml=xml+"<support>" +support+ "</support>";
	                        xml=xml+"</display>";
	                        System.out.println("add ends");
	                   }
	                 else
	                    xml=xml+"<flag>failure</flag>";
	             }
	        	 catch(Exception e) 
	             {
	                 System.out.println("exception in add"+e);
	                 xml=xml+"<flag>failure</flag>";
	             }
	        	
	        	 xml=xml+"</response>";   
	         }
	        
	         else if(cmd.equalsIgnoreCase("updated"))
	         {
	        	 xml="<response><command>updated</command>"; 
	        	 System.out.println("upda");
	        	 try{ major= Integer.parseInt(request.getParameter("major"));}
		         catch(Exception e2){ System.out.println("Err in getting major type "+e2.getMessage()); }
		         System.out.println("major"+major);
		         try{ minor= Integer.parseInt(request.getParameter("minor"));}
		         catch(Exception e2){ System.out.println("Err in getting minor type "+e2.getMessage()); }
		         System.out.println("minor"+minor);
		         try{ sub= Integer.parseInt(request.getParameter("sub"));}
		         catch(Exception e2){ System.out.println("Err in getting major type "+e2.getMessage()); }
		         System.out.println("sub"+sub);
	        	 support=request.getParameter("support");
	             try {
	                 ps = con.prepareStatement("update FAS_SUP_INV_BILL_TYPES set SUPPORTING_INVOICE=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and BILL_SUB_TYPE_CODE=?");    
	                 ps.setString(1, support);
	                 ps.setInt(2,eid);
	                 ps.setTimestamp(3,ts);
	                 ps.setInt(4,unitid);
	                 ps.setInt(5,offid);
	                 ps.setInt(6,major);
	                 ps.setInt(7,minor);
	                 ps.setInt(8,sub);
	                 
	                 int i2=ps.executeUpdate(); 
	                 System.out.println("i2:::"+i2);
	                 if(i2>=1)
	                 {
	                	 xml=xml+"<flag>success</flag>";
	                	 xml=xml+"<display>"; 
		                	ps1=con.prepareStatement("select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where BILL_MAJOR_TYPE_CODE="+major);
		                    result1=ps1.executeQuery();
		                    if(result1.next()){
		                    	xml=xml+"<majorCode>" +result1.getInt("BILL_MAJOR_TYPE_CODE")+ "</majorCode>";
		                    	xml=xml+"<majorDesc>" +result1.getString("BILL_MAJOR_TYPE_DESC")+ "</majorDesc>";
		                    }
		                    else{
		                    	xml=xml+"<majorCode>" +"0"+ "</majorCode>";
		                    	xml=xml+"<majorDesc>" +"--"+ "</majorDesc>";
		                    }
		                    ps2 = con.prepareStatement("select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE="+major+" and BILL_MINOR_TYPE_CODE="+minor);
	                        result2 = ps2.executeQuery();	
	                        if(result2.next()){
	                        	xml=xml+"<minorCode>" +result2.getInt("BILL_MINOR_TYPE_CODE")+ "</minorCode>";
		                    	xml=xml+"<minorDesc>" +result2.getString("BILL_MINOR_TYPE_DESC")+ "</minorDesc>";
	                        }
		                    else{
		                    	xml=xml+"<minorCode>" +"0"+ "</minorCode>";
		                    	xml=xml+"<minorDesc>" +"--"+ "</minorDesc>";
		                    }
	                        ps3=con.prepareStatement("select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE="+major+" and BILL_MINOR_TYPE_CODE="+minor+" and BILL_SUB_TYPE_CODE="+sub);
	                        result3 = ps3.executeQuery();
	                        if(result3.next()){
		                    	xml=xml+"<subCode>" +result3.getInt("BILL_SUB_TYPE_CODE")+ "</subCode>";
		                    	xml=xml+"<subDesc>" +result3.getString("BILL_SUB_TYPE_DESC")+ "</subDesc>";
		                    }
		                    else{
		                    	xml=xml+"<subCode>" +"0"+ "</subCode>";
		                    	xml=xml+"<subDesc>" +"--"+ "</subDesc>";
		                    }
	                        xml=xml+"<support>" +support+ "</support>";
	                        xml=xml+"</display>";
	                        System.out.println("up::"+xml);
	                 }
	                 else
	                	 xml=xml+"<flag>failure</flag>"; 
	                 
	                 
	             }
	             catch(Exception e)
	             {
	                  System.out.println("exception in update is"+e);
	                  xml=xml+"<flag>failure</flag>";
	             }
	        	 
	        	 xml=xml+"</response>";      
	         }
	         else if(cmd.equalsIgnoreCase("deleted"))
	         {
	             xml="<response><command>deleted</command>";
	             try{ major= Integer.parseInt(request.getParameter("major"));}
		         catch(Exception e2){ System.out.println("Err in getting major type "+e2.getMessage()); }
		         System.out.println("major"+major);
		         try{ minor= Integer.parseInt(request.getParameter("minor"));}
		         catch(Exception e2){ System.out.println("Err in getting minor type "+e2.getMessage()); }
		         System.out.println("minor"+minor);
		         try{ sub= Integer.parseInt(request.getParameter("sub"));}
		         catch(Exception e2){ System.out.println("Err in getting major type "+e2.getMessage()); }
		         System.out.println("sub"+sub);
		             try
		             {
	                
		                 ps = con.prepareStatement("update FAS_SUP_INV_BILL_TYPES set STATUS='C' where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=? and BILL_SUB_TYPE_CODE=?");
		                 ps.setInt(1,unitid);
		                 ps.setInt(2,offid);
		                 ps.setInt(3,major);
		                 ps.setInt(4,minor);
		                 ps.setInt(5,sub);
		                 int ii=ps.executeUpdate();
		                 if(ii>=1)
		                 {
		                	 xml=xml+"<flag>success</flag>";
		                 }
		                 else
		                	 xml=xml+"<flag>failure</flag>"; 
	                 
	                 }
	                 catch(SQLException e) {
	                     
	                      System.err.println("error on delete function" + e.getMessage());
	                     xml=xml+"<flag>failure</flag>";
	                 }

	             xml=xml+"</response>";
	         } 
	        
	         else if(cmd.equalsIgnoreCase("Get"))
	         { 
	        	 System.out.println("gettt");
	        	 int mycount = 0;
		         xml="<response><command>Get</command>";		        
		             try 
		             {
		            	 System.out.println("inside gettt");
		            	 ps = con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,BILL_SUB_TYPE_CODE,SUPPORTING_INVOICE,STATUS from FAS_SUP_INV_BILL_TYPES ");	            	 
		            	 result = ps.executeQuery();                                
	                     while(result.next()) 
	                     {
	                    	 //xml=xml+"<flag>success</flag>";
	                    	 xml=xml+"<display>";
	                    	 ps1=con.prepareStatement("select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where BILL_MAJOR_TYPE_CODE="+result.getInt("BILL_MAJOR_TYPE_CODE"));
	 	                     result1=ps1.executeQuery();
	 	                    System.out.println("result1");
	 	                    if(result1.next()){
	 	                    	xml=xml+"<majorCode>" +result1.getInt("BILL_MAJOR_TYPE_CODE")+ "</majorCode>";
		                    	xml=xml+"<majorDesc>" +result1.getString("BILL_MAJOR_TYPE_DESC")+ "</majorDesc>";
	 	                    }
		                    else{
		                    	xml=xml+"<majorCode>" +"0"+ "</majorCode>";
		                    	xml=xml+"<majorDesc>" +"--"+ "</majorDesc>";
		                    }
	 	                    
		                    ps2 = con.prepareStatement("select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE="+result.getInt("BILL_MAJOR_TYPE_CODE")+" and BILL_MINOR_TYPE_CODE="+result.getInt("BILL_MINOR_TYPE_CODE"));
	                        result2 = ps2.executeQuery();
	                        System.out.println("result2");
	                        if(result2.next()){
	                        	xml=xml+"<minorCode>" +result2.getInt("BILL_MINOR_TYPE_CODE")+ "</minorCode>";
		                    	xml=xml+"<minorDesc>" +result2.getString("BILL_MINOR_TYPE_DESC")+ "</minorDesc>";
	                        }
		                    else{
		                    	xml=xml+"<minorCode>" +"0"+ "</minorCode>";
		                    	xml=xml+"<minorDesc>" +"--"+ "</minorDesc>";
		                    }
		                    	
	                        ps3=con.prepareStatement("select BILL_SUB_TYPE_CODE,BILL_SUB_TYPE_DESC from FAS_BILL_SUB_TYPES where BILL_MAJOR_TYPE_CODE="+result.getInt("BILL_MAJOR_TYPE_CODE")+" and BILL_MINOR_TYPE_CODE="+result.getInt("BILL_MINOR_TYPE_CODE")+" and BILL_SUB_TYPE_CODE="+result.getInt("BILL_SUB_TYPE_CODE"));
	                        result3 = ps3.executeQuery();
	                        if(result3.next()){
		                    	xml=xml+"<subCode>" +result3.getInt("BILL_SUB_TYPE_CODE")+ "</subCode>";
		                    	xml=xml+"<subDesc>" +result3.getString("BILL_SUB_TYPE_DESC")+ "</subDesc>";
		                    }
		                    else{
		                    	xml=xml+"<subCode>" +"0"+ "</subCode>";
		                    	xml=xml+"<subDesc>" +"--"+ "</subDesc>";
		                    }
	                         xml=xml+"<unitid>" +result.getInt("ACCOUNTING_UNIT_ID")+ "</unitid>";
	                         xml=xml+"<support>" +result.getString("SUPPORTING_INVOICE")+ "</support>";
	                         xml=xml+"<status>" +result.getString("STATUS")+ "</status>";
	                         xml=xml+"</display>";
	                         count++;
	                         mycount++;
	                         System.out.println("count:::"+count);
	                     }
	                     if(mycount==0){
	                    	 xml=xml+"<flag>failure</flag>";
	                     }else{
	                    	 xml=xml+"<flag>success</flag>";
	                     }
		             }
		             catch(Exception e1){
		                        System.out.println("Exception is in Get"+e1);
		                 		xml=xml+"<flag>failure</flag>";
		             }
		         xml=xml+"</response>";
	         }
	         System.out.println("xml::::"+xml);
	         out.println(xml);
	         out.close();
	}

}
