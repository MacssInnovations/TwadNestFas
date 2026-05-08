<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Format Master</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script src="../scripts/Format_Master.js" type="text/javascript"> </script>
</head>
<%
	String s = request.getContextPath();
%>

<body onLoad="initialLoad('<%=s%>');" bgcolor="#FFF9FF">
<form name="frmFormat_Master" method="post" action="Format_Master"
	id="frmFormat_Master">
<table width="930" border="1" align="center">
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><strong>Format Master</strong></div>
		</td>
	</tr>
	<tr class="table1">
		<td width="348">Format No<font color="#ff2121">*</font></td>
		<td width="383"><label> <input type="text"
			name="txtFormatNo" id="txtFormatNo" maxlength="15" /> </label></td>
	</tr>
	<tr class="table1">
		<td>Format Desc.-Main<font color="#ff2121">*</font></td>
		<td><input type="text" name="txtFormatDescMain"
			id="txtFormatDescMain" size="50"/></td>
	</tr>
	<tr class="table1">
		<td>
		<div align="left">Format Desc-Sub<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtFormatDescSub"
			id="txtFormatDescSub" size="50"/></div>
		</td>
	</tr>
	<tr class="table1">
		<td>
		<div align="left">Format Type (P/A)<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left">		
		  <input name="txtFormatType" type="radio" value="P" id="txtFormatType" checked="true">
		  <strong>Proposal</strong> 
		  <input name="txtFormatType" type="radio" value="A" id="txtFormatType">
		  <strong>Allocation</strong></div>
		</td>
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
			onClick="refresh();" /> <input type="button" name="onexit"
			value="EXIT" id="onexit" onClick="exitfun();" /></div>
		</td>
	</tr>
</table>

<p>&nbsp;</p>
<table id="Existing" cellspacing="2" cellpadding="3" border="1"
	width="97%" align="center" class="table1">
	<tr class="tdH1">
		<td align="center" colspan="18"><b>EXISTING DETAILS </b></td>
	</tr>
	<tr class="tdH1">
		<th width="11%">Select</th>
		<th width="17%">Format No</th>
		<th width="28%">Format Desc.-Main</th>
		<th width="21%">Format Desc-Sub</th>
		<th width="23%">Format Type (P/A)</th>

	</tr>
	<tbody id="tblList" align="center">
	</tbody>
</table>

</form>
</body>
</html>