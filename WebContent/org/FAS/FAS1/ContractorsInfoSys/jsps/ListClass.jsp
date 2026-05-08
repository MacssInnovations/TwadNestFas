<%@ page contentType="text/html;charset=windows-1252"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252">
    <title>List All Classes</title>
   
    <script language="javascript">
    
          function LoadRecord()
          {
          
           var iframe=document.getElementsByTagName("iframe")[0];
              if(iframe)
              {
                  iframe.src="../../../../../ServletListClass.view?command=" +iframe;
              }
          
          }
    
    
    </script>
    <!--<link href="../../../../../css/green.css" rel="stylesheet" media="screen"/>-->
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
  </head>
  <body onload="LoadRecord()">
  <form name="form1">
  <iframe id="SelectingRecord" width="100%" height="500px" >
      </iframe>
      </form>
  </body>
</html>
