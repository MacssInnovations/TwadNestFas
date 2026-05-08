<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page language="java" contentType="text/html; charset=ISO-8859-1" pageEncoding="ISO-8859-1"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="Cache-Control" content="No-Cache"/>
    <title>GPF_Withdrawl_Type</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"
            src="../../../../Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/Hrm_TransJoinJS_New.js"></script>
    <!-- <script type="text/javascript"       src="../../../../../org/Library/scripts/CalendarControl.js"></script>-->
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
    <script type="text/javascript" src="../scripts/selectOfficeAttached.js">  </script>
    <script type="text/javascript" src="../scripts/GPF_Withdrawl_Type.js">  </script>
  
    <style type="text/css">
.cal {font-family:verdana; font-size:12px;}
</style>
  </head>
  <body id="bodyid" onload="call('get');"><form name="Hrm_TransJoinForm"
                                                   id="Hrm_TransJoinForm">
     
      <div align="center">
        <table cellspacing="3" cellpadding="2" border="1" width="100%">
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <strong>GPF Withdrawal Type</strong>
              </div>
            </td>
          </tr>
          <tr class="tdH">
            <td colspan="2">
              <div align="left">Withdrawal Type Details</div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Withdrawal Id</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="w_id" id="w_id" maxlength="1"  size="2"  />
                   
              </div>
            </td>
          </tr>
          <tr class="table">
            <td>
              <div align="left">Withdrawal Description</div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="w_desc" id="w_desc" maxlength="30" size="30"  />
              </div>
            </td>
          </tr>
          
          <tr class="tdH">
            <td colspan="2">
              <div align="center">
                <input type="button" name="add" id="add" value="ADD"
                       onclick="call('Add')"/>
                 
                <input type="button" name="update" id="update"
                       onclick="call('Update');" value="UPDATE"/>
                 
                <input type="button" name="delete1" id="delete1" value="DELETE"
                       onclick="call('Delete')"/>
                 
                <input type="button" name="clear" id="clear" value="CLEAR ALL"
                       onclick="clear1();"/>
              </div>
            </td>
          </tr>
        </table>
         
        <table cellspacing="3" cellpadding="2" border="1" width="100%"
               align="center">
          <tr>
            <td class="table">Existing Details</td>
            
          </tr>
        </table>
        
         
        <table id="Existing" cellspacing="2" cellpadding="3" border="1"
               width="100%" align="center">
          <tr class="tdH">
            <th>Select</th>
             <th>Withdrawal Id</th>
            <th>Withdrawal Description</th>
             </tr>
             <tbody id="tlist" class="table">
          
          </tbody>
         
        </table>
                 <table id="Exit" cellspacing="2" cellpadding="3" border="1" width="100%"
               align="center">
          <tr>
            <td class="tdH">
              <div align="center">
                <input type="button" name="CmdExit" value="EXIT" id="CmdExit"
                       onclick="Exit()" align="middle"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>                                                                                                                                                                