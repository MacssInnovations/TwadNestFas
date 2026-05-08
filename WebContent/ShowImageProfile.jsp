<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ResourceBundle,java.io.*,java.sql.*"%>
<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252" />
<title>Office Conversion</title>
<style type="text/css">
body {
	background-color: #ffffff;
}

a:link {
	color: #002173;
}
</style>
</head>
<body>
<%
  System.out.println("Inside");
     String CONTENT_TYPE = "text/html; charset=windows-1252";
          ResourceBundle rs=null;
        Connection connection=null;
       // PrintWriter pw=response.getWriter();
              try
                {
                     rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
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
                        System.out.println("connection is:&&&&&&&&&&&&&&&&&"+ConnectionString);
                           
                 }
                catch(Exception e)
                {
                   System.out.println("Exception in openeing connection:"+e);
                }
        
        
         Statement stmt=null;
         ResultSet rset=null;
       // int cod=11263;
        String eid=request.getParameter("empid");
        int cod=Integer.parseInt(eid);
        System.out.println(cod);
      response.setContentType(CONTENT_TYPE);
      try
      {
        stmt= connection.createStatement();
        String sql = "SELECT photo FROM HRM_EMP_ADDL_PHOTO_NEW_TMP WHERE process_flow_status_id='FR' and employee_id=" + cod;
        //if(!cod.equals("")) {
        rset = stmt.executeQuery(sql);
        try {
        if(rset.next()) { System.out.println("test2");
            Blob ablob = rset.getBlob("photo");
            response.setContentType("image/gif");
            OutputStream outs = response.getOutputStream();
            long bloblen = ablob.length();
            int buffersize = (int)bloblen;
            int bytesRead = 0;
            byte b[] = new byte[buffersize];    
            InputStream is = ablob.getBinaryStream();
            while((bytesRead = is.read(b)) != -1)
            outs.write(b, 0, bytesRead);
            is.close();
            response.flushBuffer();

            }
        }
        catch(SQLException e) 
        {
          System.out.println("Error");              
        }
    }
    catch(Exception e) 
    {
    System.out.println("Error");     
    }
  %>

</body>
</html>