<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<%@ include file="popuplocal.jsp"%>
<html>
<head>
 <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
     <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
<title>Trial Balance Details</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen' />
<link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen" />
<script type="text/javascript"   src="../../../../Security/scripts/tabpane.js">          </script>
	<script type="text/javascript" src="../scripts/Fas_NRDWPREp_Details.js"></script>
	<script type="text/javascript">
	  function loadyear_month()
      {
    
      var today= new Date(); 
      var day=today.getDate();
      var month=today.getMonth();
      month=month+1;
      var year=today.getYear();
      if(year < 1900) year += 1900;
      
     document.frmTB_Details.txtCB_Year.value=year;
     document.frmTB_Details.txtCB_Month.value=month;
     
      }
function butChk(val)
{
if(val=='Monthly')
	{
	document.getElementById("motnDiv").style.display="block";
	document.getElementById("motndetDiv").style.display="block";
	document.getElementById("divDetFin").style.display="block";
	document.getElementById("divFin").style.display="block";
	document.getElementById("hid").value="Monthly";
	}
else if(val=='yearly')
	{	
	document.getElementById("divDetFin").style.display="block";
	document.getElementById("divFin").style.display="block";
	document.getElementById("motnDiv").style.display="none";
	document.getElementById("motndetDiv").style.display="none";
	document.getElementById("hid").value="yearly";
	}
}
</script>
</head>
 <%
	response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
	response.setHeader("Pragma","no-cache"); //HTTP 1.0
	response.setDateHeader ("Expires", 0);
%>
<body bgcolor="#FFF9FF" onload="LoadAccountingUnitID('LIST_ALL_UNITS');loadyear_month();">
<form name="frmTB_Details" id="frmTB_Details"  method="GET" action="../../../../../NRDWP_Report" >
 <input type="hidden" id="cmd" name="cmd" value="Data">
 <table width="100%" border="1" align="center">
        <tr class="tdH">
          <td>
            <div align="center">Trial Balance Details</div></td>
            </tr>
            </table>
              <div class="tab-pane" id="tab-pane-1">          
           <table width="100%" border="0" align="center">          
          <tr class="tdH" >
          <td colspan="2">General Details</td></tr>
	<tr class="table">
	<td><div align="left">CashBook Month</div></td>
	<td  colspan="">   <select name="txtCB_Month" id="txtCB_Month" tabindex="4">
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
                <option value="12">December</option></select>
	</td>
	<tr class="table">
	<td><div align="left">CashBook Year</div></td>
	<td  colspan=""> <input id="txtCB_Year" name="txtCB_Year" tabindex="4" value="">
	</td>
	 
	</tr>
	<tr class="table">
	<td width="10%"><div align="left">Report Type</div></td>
	<td width="10%" colspan="2">
	<!--<input type="radio" id="rad_R1" name="rad_R" value="PDF_R">PDF
	<input type="radio" id="rad_R2" name="rad_R" value="HTML_R">HTML
	-->
	<input type="radio" id="rad_R" name="rad_R" value="EXCEL_R" checked="checked">EXCEL	
	
	</td>

	</tr>
</table>
</div>
  <table width="100%" border="1" align="center">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="submit" value="Submit"></input>               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT" onclick="window.close();"></input>
            </div>
          </td>
        </tr>
      </table>

  

</form>
</body>
</html>