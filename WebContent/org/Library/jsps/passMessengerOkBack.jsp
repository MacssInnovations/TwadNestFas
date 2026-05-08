<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>Messenger</title>
    <link  href="../../../css/Sample3.css" rel="stylesheet" media="screen"/>
  <script type="text/javascript">
  function MainAccNopopup()
  {
      Bank_popup_flag=true;
      if (winAcc_Bank_No && winAcc_Bank_No.open && !winAcc_Bank_No.closed) 
      {
         winAcc_Bank_No.resizeTo(500,500);
         winAcc_Bank_No.moveTo(250,250); 
         winAcc_Bank_No.focus();
      }
      else
      {
          winAcc_Bank_No=null;
      }
      //var Office_code=document.getElementById("cmbOffice_code").value;  
    
       
      winAcc_Bank_No= window.open("/twadFas/WebContent/org/FAS/FAS1/CivilBills/jsps/PassOrderPreparation_jsp.jsp","status=1,height=500,width=500,resizable=YES, scrollbars=yes"); 
      winAcc_Bank_No.moveTo(250,250);  
      winAcc_Bank_No.focus();
  }</script>
   
    <script type="text/javascript">
    setTimeout('history.go(-1)', 5000);
    
    
    
    </script>
    
   
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
    
     //out.print("window('','_parent','')");
     out.print("<input type=\"button\" onClick=\"window.open('','_parent','');window.close();\" value=\"  Back to Menu  \"/>");
     out.print("&nbsp;&nbsp;&nbsp");

  
	   out.print("<input type=\"button\" onClick=\"history.go(-1)\" value=\"Back to Form\"/>");
   
 }
 catch(Exception e)
 {
  out.print("<input type=\"button\" onClick=\"javascript:MainAccNopopup();\" value=\"Back\"/>");
 }
 %>
 </center>
  </div>  
  </body>
</html>
