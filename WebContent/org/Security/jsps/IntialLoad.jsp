<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page import="java.sql.Date" %>
<%@ page trimDirectiveWhitespaces="true" %>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
</head>
<body>
 <%
 System.out.println("intial loaddddddddddddddddddd");
  Connection con=null;
  ResultSet rs=null,rs2=null,rsbank=null;
  PreparedStatement ps=null,ps2=null,psbank=null,Sub_psbank=null,ps1=null;
  PreparedStatement ps_new=null;
  ResultSet results=null,Sub_rsbank=null;
  ResultSet results1=null,rs3=null;
  ResultSet results2=null,rs_new=null;
   try
  {
  
             ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
            String ConnectionString="";

            String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
            String strdsn=rs1.getString("Config.DSN");
            String strhostname=rs1.getString("Config.HOST_NAME");
            String strportno=rs1.getString("Config.PORT_NUMBER");
            String strsid=rs1.getString("Config.SID");
            String strdbusername=rs1.getString("Config.USER_NAME");
            String strdbpassword=rs1.getString("Config.PASSWORD");
            ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;

             Class.forName(strDriver.trim());
             con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  long l=System.currentTimeMillis();
	Timestamp ts=new Timestamp(l);                      
	 Date ctdate = new java.sql.Date(ts.getTime()); 
 
  %>
      <% 
        HttpSession session=request.getSession(false);
         UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
     System.out.println("user id::"+empProfile.getEmployeeId());
     int empid=empProfile.getEmployeeId();
    //int empid=9315;
    int  oid=0;
    String oname="";
     int AC_HEAD_CODE=0;
    int bankID=0,branchID=0;
    long bankAccNo=0;
    String bk_br_city="";
    //String mod_id="MF005",CR_DR="CR";
 
    try
    {
           
           
            // ps=con.prepareStatement("select OFFICE_ID from HRM_EMP_CURRENT_POSTING where EMPLOYEE_ID=?" );
            ps=con.prepareStatement(" SELECT "+
            		"  CASE "+
            		 "   When Old_Office_Id   Is Not Null "+
            		  "  AND DATE_ALLOWED_UPTO>=? "+
            		    " THEN OLD_OFFICE_ID "+
            		    " ELSE Office_Id "+
            		  " END AS OFFICE_ID "+
            		" FROM "+
            		  " (SELECT Office_Id, "+
            		    " OLD_OFFICE_ID, "+
            		    " DATE_ALLOWED_UPTO "+
            		  " From Hrm_Emp_Current_Posting "+
            		  " Where Employee_Id=? )as ps" );
            System.out.println("empid:::"+empid);
            System.out.println("ctdate====>"+ctdate);
            ps.setDate(1, ctdate);
            ps.setInt(2,empid);
            
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oid=results.getInt("OFFICE_ID");
                 }
            results.close();
            ps.close();
            ps=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=?" );
            ps.setInt(1,oid);
            results=ps.executeQuery();
                 if(results.next()) 
                 {
                    oname=results.getString("OFFICE_NAME");
                    System.out.println("oname................"+oname);
                  }
            
                 
    }
    catch(Exception e)
    {
        System.out.println("Exception"+e);
    }
    finally
    {
    	results.close();
        ps.close();
    }
    
   %>
</body>
</html>