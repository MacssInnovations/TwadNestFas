<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Budget_Group_Master</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script src="../scripts/Budget_Group_Master.js" type="text/javascript"> </script>
</head>
<%
	String s = request.getContextPath();
%>

<body onLoad="initialLoad('<%=s%>');" bgcolor="#FFF9FF">
<form name="frmBudgetGroupMaster" method="post"
	action="Budget_Group_Master" id="frmBudgetGroupMaster">
<table width="930" border="1" align="center">
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><strong>Budget Group Master</strong></div>
		</td>
	</tr>
	<tr class="table1">
		<td width="348">Budget Group Id <font color="#ff2121">*</font></td>
		<td width="383"><label> <input type="text"
			name="txtBudgetGroupId" id="txtBudgetGroupId" maxlength="15"
			onkeypress="return numbersonly1(event,this)" disabled="disabled" /> ( Auto Generated Field )</label></td>
	</tr>
	<tr class="table1">
		<td>Budget Group Major<font color="#ff2121">*</font></td>
		<td><input type="text" name="txtBudgetGroupMajor"
			id="txtBudgetGroupMajor" size="50"/></td>
	</tr>
	<tr class="table1">
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><input type="button" name="onsubmit"
			value="ADD" id="onsubmit" onClick="add('<%=s%>');" /> <input
			type="button" name="ondelete" value="DELETE" id="ondelete"
			onClick="deleteeee('<%=s%>');" disabled="disabled" /> <input
			type="button" name="onupdate" value="UPDATE" id="onupdate"
			onClick="update('<%=s%>');" disabled="disabled" /> <input
			type="button" name="onrefresh" value="CLEAR ALL" id="onrefresh"
			onClick="refresh1('<%=s%>');" /> <input type="button" name="onexit"
			value="EXIT" id="onexit" onClick="exitfun();" /></div>
		</td>
	</tr>
</table>

<p>&nbsp;</p>
<div id=div2 style="overflow: scroll; absolute; height:440px; width:94% align:center ">
<table id="Existing" cellspacing="2" cellpadding="3" border="1"
	width="97%" align="center" class="table1">
	<tr class="tdH1">
		<td align="center" colspan="18"><b>EXISTING DETAILS </b></td>
	</tr>
	<tr class="tdH1">
		<th width="11%">Select</th>
		<th width="17%">Budget Group Id</th>
		<th width="28%">Budget Group Major</th>		
	</tr>
	<tbody id="tblList" align="center">
	</tbody>
</table>
</div>
</form>
</body>
</html>