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

import javax.servlet.ServletConfig;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import Servlets.Security.classes.UserProfile;

/**
 * Servlet implementation class BillScrutiny_serv
 */
public class BillScrutiny_serv extends HttpServlet {
	private static final long serialVersionUID = 1L;
	private static final String CONTENT_TYPE = "text/xml; charset=windows-1252";

          
    /**
     * @see HttpServlet#HttpServlet()
     */
    
	protected void doGet(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
		response.setContentType(CONTENT_TYPE);
	//	String CONTENT_TYPE = "text/xml";
		response.setHeader("Cache-Control", "no-cache");
		
        PrintWriter out = response.getWriter();
        Connection con=null;
        PreparedStatement ps,ps1,ps2;
        Statement st=null;
        ResultSet result=null,result1=null,result2=null;
        int eid=0,unitid=0,offid=0,major=0,count=0,minor=0,checkcode=0;
        String cmd,xml="",checkDesc="",apply="",mand="";
        
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
         
         if(cmd.equalsIgnoreCase("add")) 
         {
        	 xml="<response><command>add</command>";
        	 
             checkDesc=request.getParameter("checkDesc");
             major= Integer.parseInt(request.getParameter("major2"));
             minor= Integer.parseInt(request.getParameter("minor"));
             System.out.println("minor"+minor);
             mand=request.getParameter("mand");
             apply=request.getParameter("apply");
             int chkcode=0;
             String sql;
            try
             {
            	//changed on 18-11-2017
//                      sql="SELECT CHECK_CODE FROM FAS_COM_BILL_SCRUTINY_CHKLST GROUP BY CHECK_CODE HAVING CHECK_CODE =(select max(CHECK_CODE) from FAS_COM_BILL_SCRUTINY_CHKLST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=?)";
                sql="SELECT CHECK_CODE FROM FAS_COM_BILL_SCRUTINY_CHKLST GROUP BY CHECK_CODE HAVING CHECK_CODE =(select max(CHECK_CODE) from FAS_COM_BILL_SCRUTINY_CHKLST)";

                      ps=con.prepareStatement(sql);
                     // ps.setInt(1,unitid);
                      //ps.setInt(,offid);
                       result=ps.executeQuery();
                      if(result.next())
                      {
                    	  chkcode = result.getInt(1);                                              
                      }
                      chkcode=chkcode+1;
                      result.close();
             }                  
             catch(Exception e){System.out.println("exception"+e );}
             System.out.println("chkcode "+chkcode); 
             try 
             {
            	 
            	//changed on 18-11-2017
            	 
//            	 ps = con.prepareStatement("insert into FAS_COM_BILL_SCRUTINY_CHKLST(ACCOUNTING_UNIT_ID,ACCOUNTING_FOR_OFFICE_ID,CHECK_CODE,CHECK_DESC,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,MANDATE,NOT_APPLICABLE,STATUS,UPDATED_BY_USERID,UPDATED_DATE)values(?,?,?,?,?,?,?,?,?,?,?)");
            	 ps = con.prepareStatement("insert into FAS_COM_BILL_SCRUTINY_CHKLST(CHECK_CODE,CHECK_DESC,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,MANDATE,NOT_APPLICABLE,STATUS,UPDATED_BY_USERID,UPDATED_DATE)values(?,?,?,?,?,?,?,?,?)");

//            	 ps.setInt(1,unitid);
//                 ps.setInt(2,offid);
                 ps.setInt(1,chkcode);
                 ps.setString(2,checkDesc);
                 ps.setInt(3,major);
                 ps.setInt(4,minor);
                 ps.setString(5, mand);
                 ps.setString(6, apply);
                 ps.setString(7, "L");
                 ps.setString(8,update_user);
                 ps.setTimestamp(9,ts);
                 int i1=ps.executeUpdate();  
                 
                 if(i1>0)
                 {
                	 	xml=xml+"<flag>success</flag>";
                	    System.out.println("<<major>>"+major);
                	 	System.out.println("<<minor>>"+minor);
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
                        xml=xml+"<checkcode>" +chkcode+ "</checkcode>";
                        xml=xml+"<checkdesc>" +checkDesc+ "</checkdesc>";
                        xml=xml+"<mandate>" +mand+ "</mandate>"; 
                        xml=xml+"<notapply>" +apply+ "</notapply>";

                 }
                 else
                    xml=xml+"<flag>failure</flag>";
             }
             catch(Exception e) 
             {
                 e.printStackTrace();
                 xml=xml+"<flag>failure</flag>";
             }
         xml=xml+"</response>";             
             
         }
         else if(cmd.equalsIgnoreCase("updated"))
         {
        	 xml="<response><command>updated</command>"; 
        	 System.out.println("upda");
        	 checkcode= Integer.parseInt(request.getParameter("checkcode"));
        	 System.out.println("checkcode"+checkcode);
        	 checkDesc=request.getParameter("checkDesc");
        	 System.out.println("checkDesc"+checkDesc);
             major= Integer.parseInt(request.getParameter("major2"));
             System.out.println("major"+major);
             minor= Integer.parseInt(request.getParameter("minor"));
            System.out.println("minor"+minor);
             mand=request.getParameter("mand");
             apply=request.getParameter("apply");
             try {
            	 //changed on 18-11-2017
//                 ps = con.prepareStatement("update FAS_COM_BILL_SCRUTINY_CHKLST set CHECK_DESC=?,BILL_MAJOR_TYPE_CODE=?,BILL_MINOR_TYPE_CODE=?,MANDATE=?,NOT_APPLICABLE=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CHECK_CODE=? ");    
               
                 ps = con.prepareStatement("update FAS_COM_BILL_SCRUTINY_CHKLST set CHECK_DESC=?,BILL_MAJOR_TYPE_CODE=?,BILL_MINOR_TYPE_CODE=?,MANDATE=?,NOT_APPLICABLE=?,UPDATED_BY_USERID=?,UPDATED_DATE=? where  CHECK_CODE=? ");    

            	 ps.setString(1, checkDesc);
                 ps.setInt(2,major);
                 ps.setInt(3,minor);
                 ps.setString(4, mand);
                 ps.setString(5, apply);
                 ps.setString(6,update_user);
                 ps.setTimestamp(7,ts);
//                 ps.setInt(8,unitid);
//                 ps.setInt(9,offid);
                 ps.setInt(8,checkcode);
                
                 int i2=ps.executeUpdate(); 
                 if(i2>=1)
                 {
	                	 xml=xml+"<flag>success</flag>";
	             	    System.out.println("<<major>>"+major);
	             	 	System.out.println("<<minor>>"+minor);
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
	                     xml=xml+"<checkcode>" +checkcode+ "</checkcode>";
	                     xml=xml+"<checkdesc>" +checkDesc+ "</checkdesc>";
	                     xml=xml+"<mandate>" +mand+ "</mandate>"; 
	                     xml=xml+"<notapply>" +apply+ "</notapply>";
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
             checkcode= Integer.parseInt(request.getParameter("checkcode"));
	             try
	             {
                
	                 //ps = con.prepareStatement("delete from FAS_BILL_SCRUTINY_CHECKLIST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CHECK_CODE=? ");
	            	 
	            	 //changed on 18-11-2017
//	                 ps = con.prepareStatement("update FAS_COM_BILL_SCRUTINY_CHKLST set STATUS='C' where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? and CHECK_CODE=? ");
	                 ps = con.prepareStatement("update FAS_COM_BILL_SCRUTINY_CHKLST set STATUS='C' where CHECK_CODE=? ");

//	            	 ps.setInt(1,unitid);
//	                 ps.setInt(2,offid);
	                 ps.setInt(1,checkcode);
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
	         xml="<response><command>Get</command>";
	             try 
	             {
	            	 System.out.println("inside gettt");
	            	// ps = con.prepareStatement("select CHECK_CODE,CHECK_DESC,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,MANDATE,NOT_APPLICABLE from FAS_BILL_SCRUTINY_CHECKLIST where ACCOUNTING_UNIT_ID=? and ACCOUNTING_FOR_OFFICE_ID=? ");
	            	 ps = con.prepareStatement("select CHECK_CODE,CHECK_DESC,BILL_MAJOR_TYPE_CODE,BILL_MINOR_TYPE_CODE,MANDATE,NOT_APPLICABLE,STATUS from FAS_COM_BILL_SCRUTINY_CHKLST order by CHECK_CODE");
//	            	 ps.setInt(1,unitid);
//	                 ps.setInt(2,offid);
	            	 result = ps.executeQuery();                                
                     while(result.next()) 
                     {
                    	
                    	 xml=xml+"<flag>success</flag>";
                    	 ps1=con.prepareStatement("select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where BILL_MAJOR_TYPE_CODE="+result.getInt("BILL_MAJOR_TYPE_CODE"));
 	                     result1=ps1.executeQuery();
 	                    System.out.println("result1");
 	                    if(result1.next()){
 	                    	xml=xml+"<majorCode>" +result1.getInt("BILL_MAJOR_TYPE_CODE")+ "</majorCode>";
	                    	xml=xml+"<majorDesc>" +result1.getString("BILL_MAJOR_TYPE_DESC")+ "</majorDesc>";
 	                    }
	                    else
	                    	xml=xml+"<majorDesc>" +"--"+ "</majorDesc>";
	                    //ps2 = con.prepareStatement("select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE="+result.getInt("BILL_MAJOR_TYPE_CODE")+" and BILL_MINOR_TYPE_CODE="+result.getInt("BILL_MINOR_TYPE_CODE"));
	                    ps2 = con.prepareStatement("select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MINOR_TYPE_CODE="+result.getInt("BILL_MINOR_TYPE_CODE"));
                        result2 = ps2.executeQuery();
                        System.out.println("result2");
                        if(result2.next()){
                        	xml=xml+"<minorCode>" +result2.getInt("BILL_MINOR_TYPE_CODE")+ "</minorCode>";
	                    	xml=xml+"<minorDesc>" +result2.getString("BILL_MINOR_TYPE_DESC")+ "</minorDesc>";
                        }
	                    else
	                    	xml=xml+"<minorDesc>" +"--"+ "</minorDesc>";
                    	 System.out.println("after>>>>>>>>>>");
                    	 xml=xml+"<checkcode>" +result.getInt("CHECK_CODE")+ "</checkcode>";
                         xml=xml+"<checkdesc>" +result.getString("CHECK_DESC")+ "</checkdesc>";
                         xml=xml+"<mandate>" +result.getString("MANDATE")+ "</mandate>"; 
                         xml=xml+"<notapply>" +result.getString("NOT_APPLICABLE")+ "</notapply>";
                         xml=xml+"<status>" +result.getString("STATUS")+ "</status>";
                         count++;
                         System.out.println("count:::"+count);
                     }
	             }
	             catch(Exception e1){
	                        System.out.println("Exception is in Get"+e1);
	                 		xml=xml+"<flag>failure</flag>";
	             }
	         xml=xml+"</response>";
         }
         else if(cmd.equalsIgnoreCase("majorType"))
         {
        	 System.out.println("majorType");
             xml="<response><command>major</command>"; 
              try 
                      {
                              ps = con.prepareStatement("select BILL_MAJOR_TYPE_CODE,BILL_MAJOR_TYPE_DESC from FAS_BILL_MAJOR_TYPES where STATUS='L'");
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
                         major=Integer.parseInt(request.getParameter("major2"));
                         ps = con.prepareStatement("select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE="+major);
                         result = ps.executeQuery();
                         xml=xml+"<flag>success</flag>";
                         while (result.next()) 
                             {
                                 xml=xml+"<minorcode>"+result.getString("BILL_MINOR_TYPE_CODE")+"</minorcode>";
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
         else if(cmd.equalsIgnoreCase("listminor")) 
         {
             xml="<response><command>listminor</command>"; 
             try 
                     {
                         major=Integer.parseInt(request.getParameter("major2"));
                         minor= Integer.parseInt(request.getParameter("minor"));
                         ps = con.prepareStatement("select BILL_MINOR_TYPE_CODE,BILL_MINOR_TYPE_DESC from FAS_BILL_MINOR_TYPES_MST where BILL_MAJOR_TYPE_CODE=? and BILL_MINOR_TYPE_CODE=?");
                         ps.setInt(1,major);
                         ps.setInt(2,minor);
                         result = ps.executeQuery();
                         xml=xml+"<flag>success</flag>";
                         while (result.next()) 
                             {
                                 xml=xml+"<minorcode>"+result.getString("BILL_MINOR_TYPE_CODE")+"</minorcode>";
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
         System.out.println("xml::::"+xml);
         out.println(xml);
         out.close();
        
	}

	/**
	 * @see HttpServlet#doPost(HttpServletRequest request, HttpServletResponse response)
	 */
	protected void doPost(HttpServletRequest request, HttpServletResponse response) throws ServletException, IOException {
		// TODO Auto-generated method stub
	}

}
