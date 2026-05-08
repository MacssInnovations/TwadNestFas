<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
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
  <body><%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.util.ResourceBundle,java.io.*"%>
<%@ page import="java.sql.*,java.util.*,java.util.regex.*,
Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <meta http-equiv="Cache-Control" content="No-Cache"/>
    <title>untitled</title>
  </head>
  <body>
  <% 
  String browsefile1=request.getParameter("empbrowse");
  System.out.println("BRwowse File!!"+browsefile1);
  System.out.println("show me");
// Pattern  
 
 
 String browsefile= browsefile1.replace("\\","/");
 System.out.println("BROWSE FILE IS ::::::::::::::::::::"+browsefile);
 String newstr="";
 String relpath="";
 String[] arr=new String[10];
 arr=browsefile.split("/");
        System.out.println("ARR is="+arr);
        for(int i=0;i<arr.length-1;i++) {
            newstr=newstr+"../";
        }
        
        relpath=newstr+arr[arr.length-1];
        System.out.println("New Strign is: "+relpath);
 
        
        
  System.out.println("Modified Browse File is *********"+browsefile);
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
                             PreparedStatement ps10=null;
                             
                             PreparedStatement ps11=null;
                             PreparedStatement ps2=null;
                             ResultSet results=null;   
                             ResultSet results1=null;   
                             ResultSet results10=null;
                             ResultSet results11=null;
                             ResultSet results2=null;   
                             PreparedStatement pst=null;
                             PreparedStatement pst1=null;
                             PreparedStatement pst11=null;
                             PreparedStatement pst10=null;
                             File bfile=null;
                             File bfile1=null;
                             File bfile10=null;
                             FileInputStream fis=null,fis10=null;
  ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
    response.setContentType("text/html");
    response.setHeader("Cache-control","no-cache");
   // String Emp=(String)session.getAttribute("Emp");
    String err = "";
    String fileName=null;
    String lastFileName = "";
    String newfile="";
    String contentType = request.getContentType();
    System.out.println("Content:"+contentType);
    String boundary = "";
    final int BOUNDARY_WORD_SIZE = "boundary=".length();
    if(contentType == null || !contentType.startsWith("multipart/form-data")) {
      err = "Ilegal ENCTYPE : must be multipart/form-data\n";
      err += "ENCTYPE set = " + contentType;
    }else{
      boundary = contentType.substring(contentType.indexOf("boundary=") + BOUNDARY_WORD_SIZE);
      boundary = "--" + boundary;
      try {
        
        javax.servlet.ServletInputStream sis = request.getInputStream();
        byte[] b = new byte[1024];
        int x=0;
        int state=0;
        String name=null,contentType2=null;
        java.io.FileOutputStream buffer = null;
        while((x=sis.readLine(b,0,1024))>-1) {
          String s = new String(b,0,x);
          if(s.startsWith(boundary)) {
            state = 0;
            //out.println("name="+name+"<br>");
            //out.println(fileName+"<br>");
 
            name = null;
            contentType2 = null;
            fileName = null;
 
 
          }else if(s.startsWith("Content-Disposition") && state==0) {
            state = 1;
            if(s.indexOf("filename=") == -1)
              name = s.substring(s.indexOf("name=") + "name=".length(),s.length()-2);
            else {
              name = s.substring(s.indexOf("name=") + "name=".length(),s.lastIndexOf(";"));
              fileName = s.substring(s.indexOf("filename=") + "filename=".length(),s.length()-2);
              newfile=fileName;
              System.out.println("File Name is .........................."+newfile);
              
              if(fileName.equals("\"\"")) {
                fileName = null;
              }else {
                String userAgent1 = request.getHeader("User-Agent");
                System.out.println("USER AGENT IS ..............."+userAgent1);
                String userSeparator1="/";  // default
                if (userAgent1.indexOf("Windows")!=-1)
                {
                System.out.println("Comes inside----------");
                  userSeparator1="\\";
                }  
                if (userAgent1.indexOf("Linux")!=-1)
                  userSeparator1="/";
                fileName = fileName.substring(fileName.lastIndexOf(userSeparator1)+1,fileName.length()-1);
                 //out.println("The FileName is:"+fileName);
                if(fileName.startsWith( "\""))
                  fileName = fileName.substring( 1);
                  System.out.println("The FileName is=======:"+fileName);
              }
            }
            name = name.substring(1,name.length()-1);
            if ((name.equals("EmpBrowse"))) {
              if (buffer!=null)
                buffer.close();
              lastFileName = fileName;
              int Indexdot=fileName.indexOf(".");
              String Extension=fileName.substring(Indexdot);
             // fileName="twad"+Emp+Extension;
              fileName="twad"+Emp+".jpg";
                  
              System.out.println("Name of picture file is:"+fileName);
              //ResourceBundle rs=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
              
             File file=new File(getServletConfig().getServletContext().getRealPath(rs.getString("Config.EMPLOYEE_PHOTOS_PATH_VIEW"))+"/"+fileName);
             System.out.println(file.getPath());
            
             buffer = new java.io.FileOutputStream(file);
              out.println(buffer);
              
              
              
              //  buffer = new java.io.FileOutputStream(path1+"\\images\\"+fileName);
              // Update Image in Database
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
    
    String strpathview=rs.getString("Config.EMPLOYEE_PHOTOS_PATH_VIEW");
      
    //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
			ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
     Class.forName(strDriver.trim());
     connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
           
                       /* PreparedStatement pst = connection.prepareStatement("insert into HRM_EMP_ADDL_PHOTO_NEW_TMP(employee_id,EMP_PHOTO_FILE_NAME,UPDATED_BY_USER_ID,UPDATED_DATE,PROCESS_FLOW_STATUS_ID,PHOTO) values(?,?,?,?,?,?)");
			File bfile = new File(browsefile);
			FileInputStream fis = new FileInputStream(bfile);
                        pst.setInt(1,EmpI);
                        pst.setString(2,fileName);
                        pst.setString(3,updatedby);
                        pst.setTimestamp(4,ts);
                        pst.setString(5,"CR");
			pst.setBinaryStream(6,fis,fis.available());
			
			int ii = pst.executeUpdate();
			if(ii!=0)
			{
				System.out.println("image has been inserted");
			}
			else
			{
				System.out.println("image is not inserted");
			}*/

                            }catch(Exception e)
                            {
                              System.out.println("The Exception in Connection :"+e);
                            }
                            
                            try
                            {
                            System.out.println("In Query");
                            System.out.println("FileName is:"+fileName);
                            fileName=fileName;
                           // String path=request.getContextPath()+rs.getString("Config.EMPLOYEE_PHOTOS_PATH_VIEW");
                           
                             String path=getServletConfig().getServletContext().getRealPath("images/EmpPhotos/"+fileName);
                             System.out.println("PATH!!!!!!!!!!!!!!!!!!!!"+path);
                             
                              String sql1="select Employee_id from HRM_EMP_ADDL_PHOTO_NEW_TMP where employee_id=?";
                              
                              ps1=connection.prepareStatement(sql1);
                               ps1.setInt(1,EmpI);
                                results1=ps1.executeQuery();
                                int i=0;
                                while(results1.next())
                                {
                                   i++;
                                }
                                if(i==0)
                                {
                                System.out.println("show me");
                                   System.out.println("Emp Id is:" + Emp);
                                /*  String sql2="insert into HRM_EMP_ADDL_PHOTO_TMP(employee_id,EMP_PHOTO_FILE_NAME,UPDATED_BY_USER_ID,UPDATED_DATE,PROCESS_FLOW_STATUS_ID) values(?,?,?,?,?)";
                                  ps2=connection.prepareStatement(sql2);
                                  ps2.setInt(1,EmpI);
                                  ps2.setString(2,fileName);
                                  ps2.setString(3,updatedby);
                                  ps2.setTimestamp(4,ts);
                                  ps2.setString(5,"CR");
                                  ps2.executeUpdate();*/
                                  
                        pst = connection.prepareStatement("insert into HRM_EMP_ADDL_PHOTO_NEW_TMP(employee_id,EMP_PHOTO_FILE_NAME,UPDATED_BY_USER_ID,UPDATED_DATE,PROCESS_FLOW_STATUS_ID,PHOTO) values(?,?,?,?,?,?)");
		       // bfile = new File(browsefile);
                       bfile = new File(path);
//bfile=new File(relpath);
String ss=bfile.getPath();
System.out.println("String path is**************"+ss);
//bfile.separatorChar('/');                       
                       
    //                   if (bfile.separatorChar != '/')
                        //bfile  = bfile.replace('/',File.separatorChar);
  //                      bfile.separatorChar='/';
                        

                        
                        System.out.println("This file is:*******"+bfile);
			fis = new FileInputStream(bfile);
                        pst.setInt(1,EmpI);
                        pst.setString(2,fileName);
                        pst.setString(3,updatedby);
                        pst.setTimestamp(4,ts);
                        pst.setString(5,"CR");
			pst.setBinaryStream(6,fis,fis.available());
			Thread.sleep(10000);
			int ii = pst.executeUpdate();
			if(ii!=0)
			{
				System.out.println("image has been inserted");
			}
			else
			{
				System.out.println("image is not inserted");
			}
                       
                                  
                              }
                              else
                              {
                              /*String sql="update HRM_EMP_ADDL_PHOTO_TMP  set EMP_PHOTO_FILE_NAME=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,PROCESS_FLOW_STATUS_ID=? where Employee_Id=?";
                              ps=connection.prepareStatement(sql);
                              System.out.println("In Prepare");
                              ps.setString(1,fileName);
                              ps.setString(2,updatedby);
                              ps.setTimestamp(3,ts);
                              ps.setString(4,"MD");
                              ps.setInt(5,EmpI);
                              ps.executeUpdate();*/
                            String sql="update HRM_EMP_ADDL_PHOTO_NEW_TMP set EMP_PHOTO_FILE_NAME=?,UPDATED_BY_USER_ID=?,UPDATED_DATE=?,PROCESS_FLOW_STATUS_ID=?,PHOTO=? where Employee_Id=?";
                              pst1=connection.prepareStatement(sql);
                              System.out.println("In Prepare");
                            // bfile = new File(browsefile);
                            bfile = new File(path);
//bfile=new File(relpath);

//if (bfile.separatorChar != '/')
                        //bfile  = bfile.replace('/',File.separatorChar);
                     //   bfile.separatorChar='/';
                              
                              System.out.println("This file is:*******"+bfile);
                              fis = new FileInputStream(bfile);
                              pst1.setString(1,fileName);
                              pst1.setString(2,updatedby);
                              pst1.setTimestamp(3,ts);
                              pst1.setString(4,"MD");
                              pst1.setBinaryStream(5,fis,fis.available());
                              pst1.setInt(6,EmpI);
                              Thread.sleep(10000);
                              int ii1=pst1.executeUpdate();  
                              if(ii1!=0)
                                {
                                        System.out.println("image has been modified");
                                }
                                else
                                {
                                        System.out.println("image is not modified");
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
              
              
              
              
              //End Coding
            }
          }else if(s.startsWith("Content-Type") && state==1) {
            state = 2;
            contentType2 = s.substring(s.indexOf(":")+2,s.length()-2);
          }else if(s.equals("\r\n") && state != 3) {
            state = 3;
          }else {
            if (name.equals("EmpBrowse"))
              buffer.write(b,0,x);
              System.out.println("Here only it stores the image into the corresponding location");
              
            /*if(name.equals("EmpImage"))
             buffer.write(b,0,x);*/
          }
        }
        sis.close();
        buffer.close();
      }catch(java.io.IOException e) {
      
        err = e.toString();
        
      }
    }
    boolean ok = err.equals("");
    if(!ok) {
      //out.println(err);
      System.out.println("First...........");
       String message=err+"<br>";
  String url="../../../../Library/jsps/Messenger_db1.jsp?message=" + message + "&button=ok";
  response.sendRedirect(url);
    }else{
    %>
<SCRIPT  type="text/javascript" language="javascript">
 // history.back(1)
  //alert('Your Photo has been Uploaded');
  self.close();
  </SCRIPT>
    <%
    System.out.println("employee id="+Emp);
   // response.sendRedirect("InsertImage.jsp?employeeid="+Emp);

    fileName="twad"+Emp+".jpg";
    try{
     System.out.println("In Query  Second-------");
                            System.out.println("FileName is:"+fileName);
                            fileName=fileName;
                        //   String path_second=request.getContextPath()+rs.getString("Config.EMPLOYEE_PHOTOS_PATH_VIEW");
                           
                             String path_second=getServletConfig().getServletContext().getRealPath("images/EmpPhotos/"+fileName);
                             System.out.println("PATH!!!!!!!!!!!!!!!!!!!!"+path_second);
    String sql10="select Employee_id from HRM_EMP_ADDL_PHOTO_NEW_TMP where employee_id=?";
                              
                              ps10=connection.prepareStatement(sql10);
                               ps10.setInt(1,EmpI);
                                results10=ps10.executeQuery();
                                int i=0;
                                while(results10.next())
                                {
                                   i++;
                                }
                                if(i==0)
                                {
                                System.out.println("show me second");
                                   System.out.println("Emp Id is:" + Emp);
                                                                  
                        pst10 = connection.prepareStatement("insert into HRM_EMP_ADDL_PHOTO_NEW_TMP(PHOTO) values(?)");
		       // bfile = new File(browsefile);
                       bfile10 = new File(path_second);
//bfile=new File(relpath);
String ss=bfile10.getPath();
System.out.println("String path is**************"+ss);
//bfile.separatorChar('/');                       
                       
    //                   if (bfile.separatorChar != '/')
                        //bfile  = bfile.replace('/',File.separatorChar);
  //                      bfile.separatorChar='/';
                        

                        
                        System.out.println("This file is:******* Second"+bfile10);
			fis10 = new FileInputStream(bfile10);
                       	pst10.setBinaryStream(1,fis10,fis10.available());
			Thread.sleep(10000);
			int ii = pst10.executeUpdate();
			if(ii!=0)
			{
				System.out.println("image has been inserted");
			}
			else
			{
				System.out.println("image is not inserted");
			}
                       
                                  
                              }
                              else
                              {
                             
                            String sql1="update HRM_EMP_ADDL_PHOTO_NEW_TMP set PHOTO=? where Employee_Id=?";
                              pst11=connection.prepareStatement(sql1);
                              System.out.println("In Prepare");
                            // bfile = new File(browsefile);
                            bfile10 = new File(path_second);
//bfile=new File(relpath);

//if (bfile.separatorChar != '/')
                        //bfile  = bfile.replace('/',File.separatorChar);
                     //   bfile.separatorChar='/';
                              
                              System.out.println("This file is:*******"+bfile10);
                              fis10 = new FileInputStream(bfile10);
                              pst11.setBinaryStream(1,fis10,fis10.available());
                              pst11.setInt(2,EmpI);
                              Thread.sleep(10000);
                              int ii1=pst11.executeUpdate();  
                              if(ii1!=0)
                                {
                                        System.out.println("image has been modified");
                                }
                                else
                                {
                                        System.out.println("image is not modified");
                                }    
                              }
                            }catch(Exception e)
                            {
                              System.out.println("The Exception in PreparedStatement:"+e);
                            }
                                
    
    
    
    
    
System.out.println("second...........");
session.removeAttribute("Emp");

 String message=err+"The Photo has been Uploaded<br>";
  String url="../../../../Library/jsps/Messenger_db1.jsp?message=" + message + "&button=ok";
  response.sendRedirect(url);
  
  //window.location.reload(false)

    }
    //out.println("done");
%>


  </body>


</html>
</body>
</html>