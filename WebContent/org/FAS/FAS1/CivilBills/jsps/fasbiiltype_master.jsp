<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache">
    <!--
    <link href="css/fas.css" rel="stylesheet"  media="screen"/>
     -->
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/> 
    <title>fasbiiltype_master</title>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" language="javascript" src="../scripts/fasbilltype_master.js"></script>
    
  </head>
  <body onload="callserver('get')"; class="table">
  
  
  <form name="fasbill_master_form" action="" method="GET">
   <!--table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              TWAD BOARD-INTEGRATED ONLINE SYSTEM -FINANACIAL ACCOUNTING SYSTEM
            </div></td>
        </tr-->
  <!--table border="1" cellpadding="2" cellspacing="3" align="center" width="75%"-->
  <table cellspacing="2" cellpadding="3" border="1" width="100%">
        <tr class="tdH">
          <td colspan="2" align="center">
            <div align="center">
              TWAD BOARD-INTEGRATED ONLINE SYSTEM -FINANACIAL ACCOUNTING SYSTEM
            </div></td>
        </tr>
  <tr class="tdH">
       <td colspan="2" align="center">
              <font size="4" >Bill Type Master (Major) </font>
       </td>
  </tr>
  <tr>
       <td>
                Bill Major Type Code
        </td>
        <td>
                <input type="text" name="bill_type_code"  id="bill_type_code" readonly size="3"/>(Auto generated)
        </td>
  <tr>
        <td>
                Bill Major Type Description
          </td>
          <td>
                <input type="text" name="bill_type_desc" id="bill_type_desc" maxlength="50" />
          </td>
  </tr>
<tr>
      <td>
              Remarks
      </td>
      <td>
              <textarea cols="45" rows="5" name="remarks" id="bill_remarks" onfocus='nullfieldcheck();'>
              </textarea>
       </td>
 </tr>
<tr>
        <td colspan="2" align="center">
                <input type="button" name="cmd_add" value="ADD" id="cmd_add" onclick="callserver('add')"/>
                <input type="button" name="cmd_update" value="UPDATE" id="cmd_update" 
                     onclick="callserver('update')" disabled="disabled"/>
                <input type="button" name="cmd_delete" value="DELETE" id="cmd_delete" 
                      onclick="callserver('delete')" disabled="disabled"/>
                <input type="button" name="cmd_clear" value="CLEAR" id="cmd_clear" 
                         onclick="callrefresh()"/>
                <input type="button" name="cmd_exit" value="EXIT" id="cmd_exit"
                        onclick="exitmethod()"/>
        </td>
</tr>

<tr class="tdH">
        <td colspan="2" align="center"><strong>Existing Details</strong></td>
<tr>
</table>
<table  align="center" border="1" cellpadding="2" cellspacing="3" width="100%" id="dyntable">
<tr>
      <th class="tdH"> Select</th>
      <th class="tdH"> Bill Major Type Code </th>
      <th class="tdH"> Bill Major Type Description</th>
      <th class="tdH"> Remarks </th>
</tr>
        <tbody id="dynbody" >
        </tbody>
</table>
</form>
</body>
</html>