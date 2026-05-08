<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ResourceBundle,java.io.*,java.sql.*"%>
<html>
  <head>
  <link href="../../../../../css/SamplePhotocss.css" rel="stylesheet" media="screen"/>
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
  System.out.println("Inside");
     String CONTENT_TYPE = "text/html; charset=windows-1252";
          ResourceBundle rs=null;
        Connection connection=null;
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
        String capid=request.getParameter("captionid");
        String slno=request.getParameter("attachslno");
        int cid=Integer.parseInt(capid);
        int aslno=Integer.parseInt(slno);
       System.out.println("caption is is//////////////////  "+cid+"   ");
      response.setContentType(CONTENT_TYPE);
      try
      {
        stmt= connection.createStatement();
        String query = "SELECT FILE_CONTENT,File_Name FROM COM_CAPTION_ATTACH WHERE CAPTION_ID="+cid+ " and ATTACH_SLNO="+aslno;
       //  String query = "SELECT FILE_NAME FROM COM_CAPTION_ATTACH WHERE FILE_NAME IS NOT NULL and CAPTION_ID="+cid+ " and ATTACH_SLNO=2";
        System.out.println(query);
      
       // int cnt= rset.getFetchSize();
        //System.out.println("count="+cnt);
        rset = stmt.executeQuery(query);
          //ResultSetMetaData rst=rset.getMetaData();
          //int x=rst.getColumnCount();
          //System.out.println("coloum count is :"+x);
          try {
        
        
        while(rset.next()) { 
        //System.out.println("test2");
        
        Blob ablob = rset.getBlob("FILE_CONTENT");
        String filename=rset.getString("File_Name");
        System.out.println("file Name is:"+filename);
        
        
        String ext1 = filename.substring(filename.length()-3,filename.length());
        String ext2 = ext1.toLowerCase();
       
       /* if((ext2 != 'doc')||(ext2!='xls')||(ext2!='pdf')) 
         {
                System.out.println("You selected a ."+ext2+" file; Please select a pdf,xls or doc  file instead!");
         }
        else*/
         if(ext2.equals("pdf")) 
          {
            response.setContentType("application/pdf");
          }
         if(ext2.equals("doc")) 
          {
            response.setContentType("application/msword");
          }
          if(ext2.equals("xls")) 
          {
            response.setContentType("application/x-msexcel");
          } 
          
            
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