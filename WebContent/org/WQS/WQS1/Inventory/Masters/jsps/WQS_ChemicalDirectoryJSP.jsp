<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=UTF-8"/>
    <title>WQS_ChemicalDirectoryJSP</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js">
    </script>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/WQS_ChemicalDirectoryJS.js">
    //document.getElementsById("ltype").focus();
    </script>
  </head>
  <body onload="loading()">
  <form name="ChemDirFrm" action="">
   <%
    HttpSession session=request.getSession(false);
    UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
      
    System.out.println("user id::"+empProfile.getEmployeeId());
    int empid=empProfile.getEmployeeId();
  %>
    <table cellspacing="3" cellpadding="2" border="1" width="90%"
             align="center">
        
        <tr class="tdH">
          <td colspan="2" align="center">
            <font face="Times New Roman">
              <strong>Chemical Directory</strong>
            </font></td>
          
        </tr>
        <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong> Chemical Code</strong>
            </font></td>
          <td width="54%">
            <input type="text" name="id" maxlength="3" size="10" id="id" onkeyup="capitalise()" onblur="checkdup()"/>
          </td>
        </tr>
        <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong> Chemical Description</strong>
            </font></td>
          <td width="54%">
            <input type="text" name="desc" maxlength="60" size="25" id="desc"/>
          </td>
        </tr>
        <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong> Chemical Formula</strong>
            </font></td>
          <td width="54%">
            <input type="text" name="frmla" maxlength="40" size="25" id="frmla"/>
          </td>
        </tr>
       
        <tr class="table">
          <td width="46%">
            <font face="Times New Roman">
              <strong> Priority Flag</strong>
            </font></td>
          <td width="54%">
            <input type="text" name="pflag" id="pflag" maxlength="1" size="1" onblur="checkNum(this.value)"></input>
          </td>
        </tr>   
        <tr class="table">
          <td colspan="2" align="center" height="36">
            <input type="button"  value="  Add  " onclick="added()" id="add"/>
            <input type="button" value="  Update" onclick="upd()" id="update" />
            <input type="button" value="  Delete  " onclick="del()" id="delet" />
            <input type="button" value="  Clear  " onclick="clr()"/>
          </td>
          
        </tr>
        <tr class="table">
          <td colspan="2">&nbsp;</td>
          
        </tr>
        <tr class="tdH">
          <td colspan="2" align="center">
            <input type="button" name="exit" value="  Exit  " id="exit" onclick="close_win()"/>
            
          </td>
         
        </tr>
      </table>
           <table align="center" border="1" width="90%">
      <tr class="tdTitle"><td colspan="6">Existing Details</td></tr>
      <tr class="tdH">
      <th width="20%">Edit</th><th width="20%">Chemical Code</th><th width="20%">Chemical Description</th>
      <th width="20%">Chemical Formula</th><th width="20%">Priority Flag</th>
      </tr>
      <tbody id="tb" class="table">
      </tbody>
      </table>
    </form>
  </body>
</html>