<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<script type="text/javascript" src="../script/Training_Chart_Programs.js"></script>
<script type="text/javascript" src="../script/google_api.js"></script>


 <script type="text/javascript">
      google.load('visualization', '1.0', {'packages':['corechart']});
      google.setOnLoadCallback(drawChart);
      function drawChart() {
    	  Training_chart_subprograms();
      }
    </script>
    
    <link href="../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
<link href="../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>

<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
</head>


<%java.text.DateFormat df = new java.text.SimpleDateFormat("dd/MM/yyyy"); 
String regionSno="";
String regionName="";
String finYear="";
try
{
	regionSno=request.getParameter("regionSno");
	regionName=request.getParameter("regionName");
	finYear=request.getParameter("year");
	
	System.out.println("region no : "+regionSno+"   year  "+finYear);
}
catch(Exception e){}
%>



<body>
<form action="">

<input type="hidden" id="train_id" value="<%=regionSno %>">
<input type="hidden" id="year" value="<%=finYear %>">



<table  cellpadding="2" cellspacing="2" align="center" width="60%" style="border:1px solid #006699"  border="0">

<tr>
<td align="center" colspan="4" class="tdH">
Training  Program details :
</td>


</tr>


<tr>
<td colspan="3" align="center"></td>
<td colspan="1" align="center">

<a href="Training_Chart_Program.jsp?regionSno=<%=regionSno%>&year=<%=finYear %>"><font color="blue"> >>Previous</font></a>
<div align="right"><img src="../../../../images1/printIcon.jpg" onclick="window.print();"  style="height : 35px; width : 35px;"/></div>
</td>

</tr>





<tr>


<td colspan="2">
<div id="vacance_bar_programs"></div>
</td>


<td colspan="2">
<div id="vacance_bar_participates" ></div>
</td>



</tr>

<tr bgcolor="#006699" style="height:30px;">
<td align="center">
<font color="#FFF"><b>Programme Name</b></font>
</td>



<td align="center">
<font color="#FFF"><b>Total No Of Training Programmes</b></font>
</td>


<td align="center">
<font color="#FFF"><b>Training Centre</b></font>
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