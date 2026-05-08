<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>MIS_WorksExpenditure_JSP</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script language="javascript" type="text/javascript" src="../scripts/MIS_WorksExpenditure_JS.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script> 
    <script type="text/javascript" language="javascript">
    function loadyear_month()
    {
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
         document.miscRep.txtCB_Year.value=year
         document.miscRep.txtCB_Month.value=month;
    }
    </script>
  </head>
  <body class="table" onload="loadyear_month();">
  <form action="../../../../../../MIS_WorksExpenditure_Serv" name="miscRep" method="post" onsubmit="return checknull()">
  <table cellspacing="2" cellpadding="3" border="1" width="100%">
  <tr>
        <td class="tdH" colspan="2"><center>Works Expenditure</center></td>
  </tr>
  
   <tr align="left">
          <td class="table">
          <div align="left">
              Cash Book Year &amp; Month
              </div>
            </td>
            <td>
             <div align="left">
          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
          <!--<option value="">select the Month</option>-->
          <option value="1">January</option>
          <option value="2">February</option>
          <option value="3">March</option>
          <option value="4">April</option>
          <option value="5">May</option>
          <option value="6">June</option>
          <option value="7">July</option>
          <option value="8">August</option>
          <option value="9">September</option>
          <option value="10">October</option>
          <option value="11">November</option>
          <option value="12">December</option>
          </select>
           </div>
          </td>
        </tr>
  
  <tr class="tdH">
      <td colspan="2">
          <div align="center">
         <input type=submit value=Submit >
         <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="javascript:self.close()">
         <!--<input type=button value="Clear" onclick="clear()">-->
      </div>
      </td>
      </tr>
  </table>
  </form>
  </body>
</html>