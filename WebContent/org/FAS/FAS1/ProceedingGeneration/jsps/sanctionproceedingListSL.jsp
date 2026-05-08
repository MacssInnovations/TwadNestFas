<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>  
    <meta http-equiv="cache-control" content="no-cache">
    <title>Bank Receipt System</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
          <script type="text/javascript">
          function exit()
          {
          self.close();
          }
          </script>
          
          
          <script type="text/javascript"   src="../scripts/SanctionProceedingList.js"></script>
  </head>
  
  
  <%
    String s1=request.getParameter("cmbAcc_UnitCode");
    String s2=request.getParameter("cmbOffice_code");
    String s3=request.getParameter("yr");
    String s4=request.getParameter("mon");
    String s5=request.getParameter("sancNO");
    System.out.println("jspdsfsdfsdfsdfsdfsdfdsfsdfsdfsdf"+s1);
    System.out.println("jspdsfsdfsdfsdfsdfsdfdsfsdfsdfsdf"+s2);
%>
  <body class="table" onload="initialLoad('<%=s1%>','<%=s2%>','<%=s3%>','<%=s4%>','<%=s5%>');"><form name="SanctionProceedingList_SL" id="SanctionProceedingList_SL" method="POST">
     
      <table cellspacing="3" cellpadding="2" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>SanctionProceedingDetails</strong>
            </div>
          </td>
        </tr>
      </table>
      <table id="mytable" align="center" cellspacing="3" cellpadding="2"
             border="1" width="100%">
        <tr class="tdH">
          <th> Serial Number</th>
          <th>Payee_Type</th>
          <th>Payee_code</th>
          <th>Payment_Type</th>
          <th>Sanction_Amount</th>
          <th>Ref_NO</th>
          <th>Ref_DATE</th>
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
    </form></body>
</html>