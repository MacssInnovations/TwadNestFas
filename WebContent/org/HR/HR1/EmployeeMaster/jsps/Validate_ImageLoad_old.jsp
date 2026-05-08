<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.ResourceBundle,java.io.*"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
  </head>
  <body>
  
  <% 
  System.out.println("Show");
  
  
   /* session=request.getSession(false);
    UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
    System.out.println("user id::"+empProfile.getEmployeeId());
    int empid=empProfile.getEmployeeId();*/
  
    String updatedby=(String)session.getAttribute("UserId");
    long lng=System.currentTimeMillis();
    Timestamp ts=new Timestamp(lng);
  
  String Emp=request.getParameter("empidp");
  int EmpI=Integer.parseInt(Emp);
  System.out.println("Parameter value is:"+Emp);
  System.out.println("***********************************************");
  
                             Connection connection=null;
                             PreparedStatement ps=null;
                             PreparedStatement ps1=null;
                             PreparedStatement ps2=null;
                             PreparedStatement ps3=null;
                             ResultSet results=null;   
                             ResultSet results1=null;   
                             ResultSet results2=null; 
                             ResultSet results5=null;
  
   
    
    
              ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
          
             
                    try
                    {
                             System.out.println("In");
                            

                            try
                            {
                                  System.out.println("In Class");
                                   
    String ConnectionString="";
   
    String strDriver=rs.getString("Config.DATA_BASE_DRIVER");
    String strdsn=rs.getString("Config.DSN");
    String strhostname=rs.getString("Config.HOST_NAME");
    String strportno=rs.getString("Config.PORT_NUMBER");
    String strsid=rs.getString("Config.SID");
    String strdbusername=rs.getString("Config.USER_NAME");
    String strdbpassword=rs.getString("Config.PASSWORD");
      
    //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
     Class.forName(strDriver.trim());
     connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());

                            }catch(Exception e)
                            {
                              System.out.println("The Exception in Connection :"+e);
                            }
                            
                            try
                            {
                            System.out.println("In Query");
                           // System.out.println("FileName is:"+fileName);
                            //fileName=fileName;
                            
                              String sql1="select Employee_id from HRM_EMP_ADDL_DETAILS where employee_id=?";
                              ps1=connection.prepareStatement(sql1);
                               ps1.setString(1,Emp);
                                results1=ps1.executeQuery();
                                int i=0;
                                while(results1.next())
                                {
                                   i++;
                                }
                                String sql3="select emp_photo_file_name from HRM_EMP_ADDL_PHOTO_TMP where employee_id=?";
                                ps3=connection.prepareStatement(sql3);
                                ps3.setString(1,Emp);
                                results5=ps3.executeQuery();
                                if(results5.next())
                                    {
                                    String fileName=results5.getString("emp_photo_file_name");
                                    if(i==0)
                                    {
                                
                                
                                   System.out.println("Emp Id is:" + Emp);
                                  String sql2="insert into HRM_EMP_ADDL_Details(employee_id,EMP_PHOTO_FILE_NAME,UPDATED_BY_USER_ID,UPDATED_DATE) values(?,?,?,?)";
                                  ps2=connection.prepareStatement(sql2);
                                  ps2.setString(1,Emp);
                                  ps2.setString(2,fileName);
                                  ps2.setString(3,updatedby);
                                  ps2.setTimestamp(4,ts);
                                  //ps2.setString(5,"CR");
                                  ps2.executeUpdate();
                                  }
                              
                              else
                              {
                                  String sql="update HRM_EMP_ADDL_PHOTO_TMP  set EMP_PHOTO_FILE_NAME=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,PROCESS_FLOW_STATUS_ID=? where Employee_Id=?";
                              ps=connection.prepareStatement(sql);
                              System.out.println("In Prepare");
                              ps.setString(1,fileName);
                              ps.setString(2,updatedby);
                              ps.setTimestamp(3,ts);
                              ps.setString(4,"FR");
                              ps.setString(5,Emp);
                              ps.executeUpdate();
                                  
                              }
                              }
                            }catch(Exception e)
                            {
                              System.out.println("The Exception in PreparedStatement:"+e);
                            }
                            
      
  
                    }catch(Exception e)
                    {
                    System.out.println("The Exception is:"+e);
                    }
              //System.out.println("OK...........");
            
              
              %>

           
    
    
<SCRIPT language="javascript">
 // history.back(1)
  alert('Your Photo has been Successfully Validated');
  self.close();
  </SCRIPT>

    <%

session.removeAttribute("Emp");

 String message="Your Photo has been Validated<br>";
 String url="../../../../Library/jsps/Messenger.jsp?message=" + message + "&button=ok";
 response.sendRedirect(url);
  
  

   
%>

  </body>
</html>
