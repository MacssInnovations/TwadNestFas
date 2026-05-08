<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
    <%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile,java.text.DateFormatSymbols,Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="../script/Training_Chart_Programs.js"></script>
<script type="text/javascript" src="../script/google_api.js"></script>


 <script type="text/javascript">
      google.load('visualization', '1.0', {'packages':['corechart']});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
    	  training_programs();
      }
    </script>
    
    <link href="../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
<link href="../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Training Programs</title>


<%  
int year=0,finyear=0;
String regionSno="";
try{
 year = Calendar.getInstance().get(Calendar.YEAR); 
regionSno=request.getParameter("regionSno");
finyear=Integer.parseInt(request.getParameter("year"));
}
catch(Exception e)
{

}
%>
</head>
<body>
<form action="">



<table  cellpadding="2" cellspacing="2" width="60%"  border="0" align="center" style="border:1px solid #006699">

<tr>
<td align="center" colspan="4" class="tdH">
Training Program details :
</td>
</tr>

<tr>
<td align="center"   >
Select  Year :
</td>

<td align="left" >
<select id="year" name="year" onchange="training_programs();">
<%for(int i=0;i<5;i++){
	if(year==Calendar.getInstance().get(Calendar.YEAR))
	{
	%>
	<option value="<%=year %>" selected="selected"><%=year %></option>
	<% 
	}
	else
	{ %>
		<option value="<%=year %>" ><%=year %></option>
		
		<%
	}
	year--;
	%>
	<% 
}
%>
</select>
</td>

<td colspan="2" >

<div align="center"><a href="Training_Chart_Sub_Programs.jsp?regionSno=<%=regionSno %>&year=<%=finyear %>"><font color="blue"> >>Next</font></a></div>

<div align="right"><img src="../../../../images1/printIcon.jpg" onclick="window.print();"  style="height : 35px; width : 35px;"/></div>
</td>

</tr>



<tr>
<td colspan="4">
<div  id="showimage" >
<table >

<tr>


<td colspan="2">
<div id="vacance_bar_programs"></div>
</td>

<td colspan="2">
<div id="vacance_bar_participates" ></div>
</td>
</tr>

</table>
</div>
</td>

</tr>

<tr bgcolor="#006699" style="height:30px;">
<td align="center">
<font color="#FFF" style=""><b>Training Centre Name</b></font>
</td>



<td align="center">
<font color="#FFF"><b>Total No Of Training programmes</b></font>

</td>


<td align="center">
<font color="#FFF"><b>Training Centre ID</b></font>
</td>

<td align="center">
<font color="#FFF"><b>Total No Of Participants</b></font>
</td>


</tr>



<tbody id="vacance_deatils" style="width: 200px"></tbody>




</table>


</form>
</body>
</html>