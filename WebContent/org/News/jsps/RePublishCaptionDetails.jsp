<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>



<html>
  <head>
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>RePublishing Caption Details</title> 
    
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent',''); 
                    window.close(); 
                    var w=window.open(window.location.href,"_self");
                    w.close();
                    window.opener.focus();
                }
                function toExit()
                {
                  //window.close();
                var w=window.open(window.location.href,"_self");
                w.close();
                }
    </script>
    <link href='../../../css/Sample3.css' rel='stylesheet' media='screen'/>
    
  </head>
  <body class="table">  
  <form name="frmCreateNews" method="POST" action="../../../ServletRePublishCaptionDetails?command=Republish">
      <table width="100%" align="center">
        <tr>
            <td class="tdH">
                <center><b>RePublishing Caption Details</b></center>
            </td>
        </tr>
        <tr>
            <td>
                <table cellspacing="3" cellpadding="1" width="100%">
                <tr>
                  <td width="36%">
                  Would You Like to RePublish the News :               
                  </td>
                  <td width="64%">       
                    <input type="submit" value="Yes" name="submit">
                    <input type="button" value="No" onclick="closeWindow()">
                  </td>
         </tr>
         </table>
    </form>
  </body>
</html>