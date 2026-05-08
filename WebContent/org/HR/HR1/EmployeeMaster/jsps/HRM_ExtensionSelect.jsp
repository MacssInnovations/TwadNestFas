<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*"%>
<%
  Connection con=null;
  Statement st=null;
  ResultSet rs=null,rs1=null;
  %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen">
    <title>HRM_ExtensionSelect</title>
  <script language="javascript" src="../scripts/HRM_ExtensionSelect.js">        
  </script>
  </head>
  <body>
  <form name=LeaveSelection>
  <div align=center>
  <table border=1 width="90%">
  <tr class="tdH">
  <td colspan=2 align=center height="40">
              <strong>
                   Leave Extension Form
              </strong> </td>
  </tr>
  <tr class="table">
  <td height="35" align="left">Employee Id & Name</td>
  <td height="35" align="left"><input type="text" name=eid id=eid size="10" onchange="empdet()">
  <input type="text" name=ename id=ename size="25" disabled=true>
  </td>
  </tr>
  </table>
  <table name="mytable" id="mytable" border="1" width="90%">
            <tr class="tdH">
              <th>Select</th>
              <th>Leave Requested Id</th>
              <th>From Date</th>
              <th>To Date</th>
              <th>Type of Leave</th>
              <th>Date Applied</th>
            </tr>
            <tbody name="tb" id="tb" class="table">
            </tbody>
            <tr class="tdH">
            <td colspan="6" align="center"> <input type="Button" name="submit" value="Submit" onclick="btsubmit()">
            <input type="Button" name="cancel" value="cancel" onclick="javascript:self.close();">
            </td>
 </table>
 </div>
 </form>
</body>
</html>