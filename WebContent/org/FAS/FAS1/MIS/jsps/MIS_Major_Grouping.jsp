<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MIS_Major_Grouping</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script src="../scripts/MIS_Major_Grouping.js" type="text/javascript"> </script>
</head>
<%
	String s = request.getContextPath();
%>

<body onLoad="initialLoad('<%=s%>');" bgcolor="#FFF9FF">
<form name="frmMIS_Major_Grouping" method="post"
	action="MIS_Major_Grouping" id="frmMIS_Major_Grouping">
<table width="930" border="1" align="center">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><strong>MIS Grouping Master</strong></div>		</td>
	</tr>
	<tr class="table">
		<td width="388"><div align="left"> Major Head Code <font color="#ff2121">*</font></div></td>
		<td width="526"><div align="left">
		  <select name="cmbBudgetGroupMajor" id="cmbBudgetGroupMajor" onchange="initialLoad('<%=s%>');">
            <option value="">--- Select ---</option>
          </select>
		</div>
		</td>
	</tr>
	<tr class="table">
		<td><div align="left">Group Head Code <font color="#ff2121">  *</font></div></td>
		<td><div align="left">
		  <input type="text"
			name="txtGroup_Head_Code" id="txtGroup_Head_Code" maxlength="15"
			disabled="disabled" />
( Auto Generated Field )</div></td>
	</tr>
	<tr class="table">
      <td><div align="left">Group Head Description <font color="#ff2121">*</font></div></td>
	  <td><div align="left"><input type="text"
			name="txtGroup_Head_Desc" id="txtGroup_Head_Desc" maxlength="75"/>
          </div>
	  </td>
    </tr>
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><input type="button" name="onsubmit"
			value="ADD" id="onsubmit" onClick="add('<%=s%>');" /> <input
			type="button" name="ondelete" value="DELETE" id="ondelete"
			onClick="deletee('<%=s%>');" disabled="disabled" /> <input
			type="button" name="onupdate" value="UPDATE" id="onupdate"
			onClick="update('<%=s%>');" disabled="disabled" /> <input
			type="button" name="onrefresh" value="CLEAR ALL" id="onrefresh"
			onClick="refresh1('<%=s%>');" /> <input type="button" name="onexit"
			value="EXIT" id="onexit" onClick="exitfun();" /></div>		</td>
	</tr>
</table>

<p>&nbsp;</p>
<div id=div2 style="overflow: scroll; absolute; height:440px; width:94% align:center ">
<table id="Existing" cellspacing="2" cellpadding="3" border="1"
	width="100%" align="center" class="table">
	<tr class="tdH">
		<td align="center" colspan="18"><b>EXISTING DETAILS </b></td>
	</tr>
	<tr class="tdH">
		<th width="14%">Select</th>
		<th width="15%">Major Head Code</th>
		<th width="20%">Group Head Code</th>		
		<th width="51%">Group Head Description</th>		
	</tr>
	<tbody id="tblList" align="center">
	</tbody>
</table>
</div>
</form>
</body>
</html>