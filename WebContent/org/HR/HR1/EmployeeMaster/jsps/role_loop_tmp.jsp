<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,java.security.MessageDigest,java.security.NoSuchAlgorithmException"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Office Conversion</title>
    <style type="text/css">
      body {
      background-color: #ffffff; 
}
      a:link { color: #002173; }
    </style>
  </head>
  <body>
  
<%
  
  Connection connection=null;
  PreparedStatement ps=null,ps1=null;
  ResultSet results=null;
  ResultSet results1=null;
  ResultSet results2=null;
  
  
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

            //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
				ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
             Class.forName(strDriver.trim());
             connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  
  %>
  <%
  try
    {
           int empid=0;
            ps=connection.prepareStatement("select emp_id from  twad_data_nr_roles" );
            results=ps.executeQuery();
                 while(results.next()) 
                 {
                    empid=results.getInt("emp_id");
                    //out.println(empid);
                    String LoginId="twad"+empid;
                   
                     String txtConfirmPassword=LoginId;
                    
                             byte []b=txtConfirmPassword.getBytes();
                         try{
                             MessageDigest algorithm = MessageDigest.getInstance("MD5");
                             algorithm.reset();
                             algorithm.update(b);
                             byte messageDigest[] = algorithm.digest();
                             System.out.println("actual encrypt::"+messageDigest);                            
                             StringBuffer hexString = new StringBuffer();
                             for (int i=0;i<messageDigest.length;i++) {
                                  hexString.append(Integer.toHexString(0xFF & messageDigest[i]));
                             }
                             txtConfirmPassword=new String(hexString);
                             }catch(NoSuchAlgorithmException nsae){
                            System.out.println(nsae); 
                         }
                        
                        System.out.println("employee id:"+empid);
                        ps1=connection.prepareStatement("insert into SEC_MST_USERS1(USER_ID,USER_PASSWORD,EMPLOYEE_ID,LOGIN_ENABLED,change_password) values(?,?,?,'1',?)");
                        ps1.setString(1,LoginId);
                        ps1.setString(2,txtConfirmPassword);
                        ps1.setInt(3,empid);
                      //  ps1.executeUpdate();
                       
                      
                       out.println("<br>"+LoginId+"\t"+txtConfirmPassword+"\t"+empid);
                 
                 }
    }
    catch(Exception e)
    {
            System.out.println("error:"+e);
    }
  %>
  
  </body>
</html>