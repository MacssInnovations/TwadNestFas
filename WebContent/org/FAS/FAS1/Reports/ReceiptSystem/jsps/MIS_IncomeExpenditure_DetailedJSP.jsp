<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <title>MIS_IncomeExpenditure_DetailedJSP</title>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script> 
  <script type="text/javascript" language="javascript">
    function loadyear_month()
    {
         var currentTime = new Date()
         var month = currentTime.getMonth() + 1
         var day = currentTime.getDate()
         var year = currentTime.getFullYear()

         fin_year_from="",fin_year_to="";
         var itemcombo=document.getElementById("fin_year");
         if(month<4)
            year=year-1;
         i=0;
         while(i<3)
         {
            fin_year_from=year;fin_year_to=year+1;
            var option=document.createElement("option");
            var text=document.createTextNode(fin_year_from+"-"+fin_year_to);
            option.setAttribute("value",fin_year_from+"-"+fin_year_to);
            option.appendChild(text);
            itemcombo.appendChild(option);
            year=year-1;i++;
        }
    }
   
    </script>
  </head>
  <body class="table" onload="loadyear_month();">
  <form action="../../../../../../MIS_IncomeExpenditure_DetailedServ" name="miscRep" method="post" onsubmit="return checknull()">
  <table cellspacing="2" cellpadding="3" border="1" width="100%">
  <tr>
        <td class="tdH" colspan="2"><center>Income and Expenditure (Detailed)</center></td>
  </tr>
  
   <tr align="left">
          <td class="table">
              Financial Year
            </td>
            <td>
                 <select name="fin_year"  id="fin_year" tabindex="4" >
                 <option value="">--Select--</option>
                 </select>
            </td>
    </tr>
  <tr class="tdH">
      <td colspan="2">
          <div align="center">
             <input type=submit value=Submit >
             <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="javascript:self.close()">
          </div>
      </td>
   </tr>
  </table>
  </form>
  </body>
</html>