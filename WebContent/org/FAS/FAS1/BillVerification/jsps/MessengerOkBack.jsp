<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Messenger</title>
    <link  href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
     <script type="text/javascript" language="javascript">
  
    </script>
     <script type="text/javascript" language="javascript">
     function calJSP(jspname)
     {    
      //alert("calJSP---:"+jspname);  
      winemp= window.open(jspname,"calJSP","status=1,height=1100,width=1100,resizable=YES, scrollbars=yes");	
	  winemp.moveTo(0,0);  
	  winemp.focus();
	  self.close();
     }
     function calJSP1(jspname)
     {
         //alert("calJSP1---:"+jspname);
         var flag = jspname;        
    	 calJSP(flag);
     }
    </script>
  </head>
  <%
    String message=request.getParameter("message");
    String button=request.getParameter("button");    
    String jspname=request.getParameter("jspname");
  %>
  <body class="table1">  
  
  <div id="divMessage" class="tdH1"><center><h2>Message</h2></center></div>
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
        System.out.println("Exception is in message ok:"+e);
    }
      %>
      <%=message%>
     
    </b></P>
  </div>
  <br><br>
  <div id="divbottom" class="tdH1">
 <center>
 <%
 try
 {
	 %>
	 
	        
     <input type="button" onClick="window.open('','_parent','');window.close();" value="Back to Menu"/>
     &nbsp;&nbsp;&nbsp;
     <input type="button" value="Back to Form" onClick="calJSP('<%=jspname%>');" />
     <%
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
