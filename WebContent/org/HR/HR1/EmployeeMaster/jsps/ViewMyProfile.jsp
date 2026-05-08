
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>View My Profile</title>
  </head>
  <body>
  <%
  try
  {
    UserProfile up=(UserProfile)session.getAttribute("UserProfile");
    int id=up.getEmployeeId();  
    String url="ViewProfile.jsp?txtEmpId=" + id ;
    System.out.println("jsp called : " + url);
    response.sendRedirect(url); 
  }
  catch(Exception e)
  {
    System.out.println("exception : " + e);
  }
  %>
  </body>
</html>