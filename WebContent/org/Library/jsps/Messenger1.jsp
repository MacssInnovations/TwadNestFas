<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Messenger</title>
    <link  href="../../../css/Sample3.css" rel="stylesheet" media="screen"/>
  </head>
  <body class="table">  
  <%
    String message=request.getParameter("message");
    String button=request.getParameter("button");
  %>
  <div id="divMessage" class="tdH"><center><h2>Message</h2></center></div>
  <br>
  <br>
  <div id="divdetails"  style="font-size:small; font-style:normal;">
    <P><b>
     <%     
     try
     {
      StringBuffer test=new StringBuffer(message);
      test.replace(message.indexOf("*"),message.indexOf("*")+1,"&");
      message=test.toString();
    }
    catch(Exception e)
    {
        System.out.println("Exception is:"+e);
    }
      %>
      <%=message%>
     
    </b></P>
  </div>
  <br><br>
  <div id="divbottom" class="tdH">
 <center>
 <%
 try
 {
 if(button.equalsIgnoreCase("ok"))
 {
 //out.print("window('','_parent','')");
 out.print("<input type=\"button\" onClick=\"window.open('../../HR/HR1/EmployeeMaster/jsps/New_Update_Emp_Photo.jsp','','');window.close();\" value=\"  Ok  \"/>");
 }
 else
 {
 out.print("<input type=\"button\" onClick=\"history.go(-1)\" value=\"Back\"/>");
 }
 }
 catch(Exception e)
 {
  out.print("<input type=\"button\" onClick=\"history.go(-1)\" value=\"Back\"/>");
 }
 %>
 </center>
  </div>  
  </body>
</html>
