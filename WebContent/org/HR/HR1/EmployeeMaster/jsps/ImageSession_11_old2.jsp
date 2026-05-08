<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" import="java.sql.*,java.util.ResourceBundle,java.io.*"%>

<%
  
  Connection connection=null;
  PreparedStatement ps=null;
  ResultSet results=null;
   ResourceBundle rs=null;
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

           // ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
				ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
             Class.forName(strDriver.trim());
             connection=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
  }
  catch(Exception e)
  {
    System.out.println("Exception in connection...."+e);
  }
  
  
  %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Employee Session</title>
  </head>
  <body>
  <form>
  <%
  //HttpSession session=request.getSession(false);
  String Command=request.getParameter("Command");
  System.out.println("Command is"+Command);
  if(Command.equals("init"))
  {
  String Emp=request.getParameter("empidp");
  //session.setAttribute("Emp",Emp);
  response.sendRedirect("../jsps/ImageLoad11.jsp");
  }
  else if(Command.equals("destroy"))
  {
  //session.removeAttribute("Emp");
  String message="Action Image Uploading was Canceled Successfully.<br>";
  String url="../../../../Library/jsps/Messenger.jsp?message=" + message + "&button=ok";
  response.sendRedirect(url);
  }
   else if(Command.equals("delete"))
  {
  
  try
            {
                    // System.out.println("In Delete :Image session:"+session);
                     //if(session.getAttribute("Emp")!=null)
                   //  {
                           // String s=(String)session.getAttribute("Emp");
                            //int empid=Integer.parseInt(s);
                            String Emp=request.getParameter("empidp");
                            int empid=Integer.parseInt(Emp);
                            System.out.println(empid);
                            ps=connection.prepareStatement("select EMP_PHOTO_FILE_NAME from HRM_EMP_ADDL_PHOTO_TMP where EMPLOYEE_ID=?" );
                            ps.setInt(1,empid);
                            results=ps.executeQuery();
                            if(results.next())
                            {
                                        String fileName=results.getString("EMP_PHOTO_FILE_NAME");
                                        File file=new File(getServletConfig().getServletContext().getRealPath(rs.getString("Config.EMPLOYEE_PHOTOS_PATH_VIEW"))+"/"+fileName);
                                        System.out.println(file.getPath());
                                        try
                                        {
                                        file.delete();
                                        }catch(Exception e){System.out.println("Error1:"+e);}
                                        try
                                        {
                                                 ps=connection.prepareStatement("delete from HRM_EMP_ADDL_PHOTO_TMP where EMPLOYEE_ID=?" );
                                                ps.setInt(1,empid);
                                                ps.executeUpdate();
                                        }catch(Exception e){System.out.println("Error2:"+e);}
                                        
                                
                            }
                             else
                            {
                            System.out.println("employee id is null");
                            }
                            results.close();
                            ps.close();
                    //}
                   /* else
                    {
                            System.out.println("employee id is null");
                    }*/
            }
            catch(Exception e)
            {
                System.out.println("Error "+e);
            }
      
  
  //session.removeAttribute("Emp");
  String message="Image was Deleted Successfully.<br>";
  String url="../../../../Library/jsps/Messenger.jsp?message=" + message + "&button=ok";
  response.sendRedirect(url);
  }
  %>
  </form>
  </body>
</html>
