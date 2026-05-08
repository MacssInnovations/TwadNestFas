<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Insert title here</title>
  <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
       <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
         <link href="../../../../../css/label.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../scripts/cellcreate.js"></script>
         
</head>

<body onload="year('year','0'),month('month','0')">
<table align=center width="55%" border=0 cellspacing="0" cellpadding="5" height="40">
<tr class="tdH" >
<td  align=center colspan=3>
	DCB Setting 
</td>
</tr>
	<tr>
			<td>
					Opening Balance<br>Month & Year Change 
					 
			</td>
			<td colspan=2>
					<select id="yearmonthenable" name="yearmonthenable">
					<option value=0>Select </option>
					<option value=1>Not Allowed</option>
					<option value=2>Allowed</option>
					</select> 
			</td>
	</tr>
<tr>
<td>
					Year& Month 
			</td>
<td>    <select id="year" name="year"  style="width:80pt"></select> <select id="month" name="month"  style="width:80pt"></select> </tD>
</tr>
	<tr>
			<td>
					Pumping Return 
			</td>
			
    
    <td>
					<select id="yearmonthenable" name="yearmonthenable">
					<option value=0>Select </option>
					<option value=1>Not Freeze</option>
					<option value=2>Freeze</option>
					</select> 
			</td>
		</tr>
		<tr>
			<td>
					Demand 
			</td>
			
             <td>
					<select id="yearmonthenable" name="yearmonthenable">
					<option value=0>Select </option>
					<option value=1>Not Freeze</option>
					<option value=2>Freeze</option>
					</select> 
			</td>
		</tr>
		<tr>
			<td>
					Journal Preparation 
			</td>
			
             <td>
					<select id="yearmonthenable" name="yearmonthenable">
					<option value=0>Select </option>
					<option value=1>Not Freeze</option>
					<option value=2>Freeze</option>
					</select> 
			</td>
		</tr> 
		<tr class="tdH" >
			<td colspan=2 align=center>
					 <input type="button" id="" value="Submit"></input>
			</td>
		</tr>
		
</table>
   <input type=hidden id="pr_status" name="pr_status" value="0"> 

</body>
</html>