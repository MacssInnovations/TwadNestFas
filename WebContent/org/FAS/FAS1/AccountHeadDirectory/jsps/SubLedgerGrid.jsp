<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>untitled</title>
    <script language="javascript" type="text/javascript" src="../scripts/UpdateRecords.js"></script>
    <script language="javascript" type="text/javascript">
    function LoadGrid()
    {
      
      var ahc=document.ShowSL.txtahc.value;
      
      var url="UpdateServlet.view?Type=ShowSLTypes&AHC="+ahc;
      var iframe=document.getElementsByTagName("iframe")[0];
      if(iframe)
      {
         iframe.src=url;
      }  
    }   
    </script>
  </head>
  <body onload="LoadGrid()">
  <form name="ShowSL">
   <%! 
    int ahcode;
   %>
   <% 
    try
    {
    
   ahcode=Integer.parseInt(request.getParameter("AHC"));
   
 
    }
    catch(NumberFormatException nfe)
    {}
%>  
 <input type="HIDDEN" name="txtahc" value=<%=ahcode%>>
    <iframe id="SLGrid" width="100%" height="500px" >
    </iframe>
      
  
  </form>
  </body>
</html>
