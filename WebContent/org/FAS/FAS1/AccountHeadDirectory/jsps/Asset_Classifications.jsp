<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>Asset Classifications</title>
     <link href="../../../../../css/Sample3.css" rel='stylesheet' media='screen'/>
     <script language="javascript" src="../scripts/Asset_Classifications.js" type="text/javascript">
    </script>
  </head>
  <body onload="loadfun()">
  <form name="classification">  
    <table id="Exsisting" border="1" width="100%"
             align="center">
        <tr class="tdH">
                <th width="5%">
                    Select
                </th>
                <th width="5%">
                    Asset Major Class Code
                </th>
                 <th width="20%">
                    Asset Major Class Description
                </th>
                <th width="20%">
                    Asset Type Code
                </th>
                <th width="20%">
                    Alias Code
                </th>
                <th width="20%">
                    Major Class Asper Manual
                </th>
                <th width="20%">
                    Individual Folio Maintained
                </th>
                <th width="20%">
                    Minor Class Applicable
                </th>
                <th width="20%">
                    Is Depreciable
                </th>
                <th width="20%">
                    Status
                </th>
                
               
        </tr>
        <tbody id="tb" class="table">
        </tbody>
        </table>  
        <table border="1" width="100%"
             align="center">
        <tr class="tdH" >
        <td align="center" colspan="7">
        <input type="button" name="cancel" id="cancel" value="Cancel" onclick="javascript:window.close()">
        </td>
        </tr>
      </table>
      </form>
      </body>
</html>