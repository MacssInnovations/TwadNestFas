<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>WQS_MiscellaneousJSP</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/WQS_MiscellaneousJS.js"></script>
  </head>
  <body onload="callServer('Get','null')">
  <form name="MisCategory">
  <% 
         HttpSession session=request.getSession(false);
         UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
         System.out.println("user id::"+empProfile.getEmployeeId());
    %>
   <table cellspacing="2" cellpadding="3" border="1" width="100%"
             align="center">
       <tr>
            <td colspan="2" class="tdH" align="center"><b>Miscellaneous Category</b></td>         
       </tr> 
        <tr>
            <td class="table" width="50%">Miscellaneous Code</td>
            <td class="table" width="50%">
            <input type=text name="mcode" id="mcode" maxlength="3" onkeyup="capitalise()" onblur="checkdup()"></input>
            </td>
        </tr>  
        
        <tr>
            <td class="table" align="left" width="49%">Miscellaneous Specification</td>
            <td class="table" align="left" width="51%">
                <input type="text" name="mspec" id="mspec"></input>
            </td>
        </tr>
        <tr>
          <td colspan="2" class="table" align="center">
            <input type="button" name="CmdAdd" value="Add" id="CmdAdd" onclick="callServer('Add','null')"/>
            <input type="button" name="CmdDelete" value="Delete" id="CmdDelete" onclick="callServer('Delete','null')" disabled/>
            <input type="button" name="CmdUpdate" value="Update" id="CmdUpdate" onclick="callServer('Update','null')" disabled/>
            <input type="button" name="CmdClear" value="Clear" id="CmdClear" onclick="clearAll()"/>
          </td>
        </tr>
        
        <tr class="table">
          <td colspan="2">&nbsp;</td>
        </tr>
        
        <tr class="tdH">
          <td colspan="2" align="center">
            <input type="button" name="exit" value="  Exit  " id="exit" onclick="javascript:self.close();"/>
          </td>
        </tr>
    </table>
    <table cellspacing="3" cellpadding="2" border="1" width="100%"
             align="center" >
                  <tr class="tdTitle"><td colspan="3">Existing Details</td></tr>
    </table>
      <table id="Existing" border="1" width="100%"
             align="center">
        <tr class="tdH">
          <th>
            Select
          </th>
         <th>
            Miscellaneous Code
          </th>        
          <th>
            Miscellaneous Specification
          </th>
        </tr>
        <tbody id="tblList" class="table">
        </tbody>
    </table>   
  </form>
  </body>
</html>