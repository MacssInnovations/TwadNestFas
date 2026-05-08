<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Sanction_estimate_popuplist</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" language="javascript" src="../scripts/sanctionestimatelist.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
          <script type="text/javascript">
          function exit()
          {
          self.close();
          }
          </script>
  </head>
  <body class="table" onload="loadestimatelist();">
  
  <form name="sanction_estimate_popuplist" method="" >
            <%
                     int sanc_est_no1=Integer.parseInt(request.getParameter("sanc_est_no"));
                    out.print("<input type=hidden name=sanc_est_no id=sanc_est_no value='"+sanc_est_no1+"'>");
             %>
  
              <table cellspacing="2" cellpadding="3" width="100%">
                <tr class="tdH">
                  <td colspan="2">
                    <div align="center">
                      <strong>Sanction Estimate List Details</strong>
                    </div>
                  </td>
                </tr>
              </table>
              
              <table id="mytable" align="center" cellspacing="3" cellpadding="2"
                     border="1" width="100%">
                <tr class="tdH">
                        <th>Serial No</th>
                        <th>Asset code</th>
                        <th>Asset Description</th>
                        <th>Sanctioned Amount</th>
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
                                 onclick="window.close()"></input>
                        </div>
                      </td>
                    </tr>
                  </table>
                  
    </form>
</body>
</html>