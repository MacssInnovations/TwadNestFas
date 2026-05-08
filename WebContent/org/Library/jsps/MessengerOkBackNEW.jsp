<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Messenger</title>
    <link  href="../../../css/Sample3.css" rel="stylesheet" media="screen"/>
  
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/BillScrutiny/scripts/Bill_Scrutiny_Details.js"> 
  
    <script type="text/javascript">
    setTimeout('history.go(-1)', 5000);
    
    </script>
     <script type="text/javascript">
   
     
     </script>
  </head>
  <body class="table">  
  <%
    String message=request.getParameter("message");
    String button=request.getParameter("button");
    String message1="";
    %>
<%String s=request.getContextPath(); %>
<%System.out.println(s); %>

 
  <div id="divMessage" class="tdH"><center><h2>Message</h2></center></div>
  <br>
  <br>
  <div id="divdetails"  style="font-size:small; font-style:normal;">
    <P><b>
     <%     
     try
     {
      StringBuffer test=new StringBuffer(message);
      StringBuffer test1=new StringBuffer(message);
      test.replace(message.indexOf("*"),message.indexOf("*")+1,"&");
      message=test.toString();
      message1=test1.toString();
      
    }
    catch(Exception e)
    {
        System.out.println("Exception is:"+e);
    }
      %>
      <%=message.split("/")[1]%>
     
    </b></P>
  </div>
  <br><br>
  <div id="divbottom" class="tdH">
 <center>
 <%
 try
 {
    
     //out.print("window('','_parent','')");
     out.print("<input type=\"button\" onClick=\"window.open('','_parent','');window.close();\" value=\"  Back to Menu  \"/>");
     out.print("&nbsp;&nbsp;&nbsp");
    
  
     out.print("<input type=\"button\" onClick=\"onsub('"+s+"',"+message.split("/")[0]+")\" value=\"Back to Form\"/>");
 
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
