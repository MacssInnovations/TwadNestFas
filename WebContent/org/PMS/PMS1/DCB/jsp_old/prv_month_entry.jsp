<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
<head>
<script type="text/javascript" src="../scripts/Bill_Pumping.js"></script>
 <script type="text/javascript" src="../scripts/cellcreate.js"></script>

</head>
<%
String ben_value=request.getParameter("ben_value");
String month_value=request.getParameter("month_value");
String year_value=request.getParameter("year_value");
String msno=request.getParameter("msno");
%>
<body onload="data_show_pump('prv_show',3,1)" bgcolor="#E0FFFF">
<form name="sub">

<input type="hidden" id="month"  value="<%=month_value%>"/>
<input type="hidden" id="year"  value="<%=year_value%>"/>
 <input type="hidden" id="METER_SNO"  value="<%=msno%>"/>
<input type="hidden" id="BENEFICIARY_SNO"  value="<%=ben_value%>"/>
<table border=0 cellspacing=0 width=100%>
<tr>
		<td  align=center colspan=2>  Pumping Return - previouse Month  
		 
</tr>
<tr>
		<td width=30%>  Beneficiary </td>
		<td width=70% id="ben_value"> </td>  
</tr> 
<tr>
		<td>  Location   </td>
		<td id="location"> </td> 
</tr>
<tr>
		<td>  Metre Type   </td>
		<td id="metertype"> </td> 
</tr>

  	 
 <tr>
		<td> Meter Available   </td>
		<td id="Prv_Metre_Fix"> </td> 
</tr>	
<tr>
		<td> Meter Working  </td>
		<td id="Prv_Metre_Work"> </td> 
</tr>
		
<tr>
		<td> Opening Reading  </td>
		<td id="Prv_In_Read"> </td> 
</tr>		
<tr>
		<td> Closing Reading  </td>
		<td id="Prv_Cls_Read"> </td> 
</tr>		
<tr>
	<td>	No of Units </td>
	<td id="Prv_Qty_Cons"> </td> 	  
</tr>
</table>
</form>
</body>
</html>