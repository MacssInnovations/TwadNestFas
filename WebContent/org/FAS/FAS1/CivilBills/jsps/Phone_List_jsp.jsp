<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Phone Details</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" language="javascript" src="../scripts/phone_master_list_js.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
          <script type="text/javascript">
          function exit()
          {
          self.close();
          }
          </script>
  </head>
  <body class="table" onload="loadphoneDetails();">
  
  <form name="formPhone_List" method="">
            <%
                     int emp_id_sel=Integer.parseInt(request.getParameter("emp_id"));
                    out.print("<input type=hidden name=empl_id id=empl_id value='"+emp_id_sel+"'>");
             %>
  
              <table cellspacing="2" cellpadding="3" width="100%">
                <tr class="tdH">
                  <td colspan="2">
                    <div align="center">
                      <strong>Phone Details</strong>
                    </div>
                  </td>
                </tr>
              </table>
              
              <table id="mytable" align="center" cellspacing="3" cellpadding="2"
                     border="1" width="100%">
                <tr class="tdH">
                        <th>Serial Number</th>
                        <th>Purpose</th>
                        <th>Connection type</th>
                        <th>Usage Details</th>
                        <th>STD Code</th>
                        <th>Phone Number</th>
                        <th>Service Provider Name</th>
                        <th>Service Provider Type</th>
                        <th>Ceiling Type</th>
                        <th>Ceiling Limit Amount</th>
                        <th>Particulars</th>
                </tr>
                <tbody id="tbody" class="table">
                </tbody>
              </table>
              
                  <table align="center" cellspacing="3" cellpadding="2" border="1"
                         width="100%">
                    <tr class="tdH">
                      <td>
                        <div align="center">
                          <input type="button" id="cmdcancel" name="cancel" value="Exit"
                                 onclick="exit()"></input>
                        </div>
                      </td>
                    </tr>
                  </table>
                  
    </form>
</body>
</html>