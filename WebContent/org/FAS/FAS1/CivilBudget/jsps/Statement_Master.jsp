<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Statement_Master</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script src="../scripts/Statement_Master.js" type="text/javascript"> </script>
</head>
<%
	String s = request.getContextPath();
%>

<body onLoad="initialLoad('<%=s%>');" bgcolor="#FFF9FF">
<form name="frmStatement_Master" method="post" action="Statement_Master"
	id="frmStatement_Master">
<table width="930" border="1" align="center">
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><strong>Statement  Master</strong></div>		</td>
	</tr>
	<tr class="table1">
		<td width="348">Statement No<font color="#ff2121">*</font></td>
		<td width="383"><label> <input type="text"
			name="txtStatementNo" id="txtStatementNo" maxlength="15" /> </label></td>
	</tr>
	<tr class="table1">
		<td>Statement Desc<font color="#ff2121">*</font></td>
		<td><input type="text" name="txtStatementDesc"
			id="txtStatementDesc" size="50"/></td>
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
			value="EXIT" id="onexit" onClick="exitfun();" /></div>		</td>
	</tr>
</table>

<p>&nbsp;</p>
<table id="Existing" cellspacing="2" cellpadding="3" border="1"
	width="97%" align="center" class="table1">
	<tr class="tdH1">
		<td colspan="2"><b>EXISTING DETAILS </b></td>
		<td colspan="1">
            	Page&nbsp;Size&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select name="cmbpagination" id="cmbpagination" onchange="changepagesize();">
                  <option value="5" >5</option>
                  <option value="10" selected="selected">10</option>
                  <option value="15">15</option>
                  <option value="20">20</option>
                </select>
          	</td>
	</tr>
	<tr class="tdH1">
		<th width="11%">Select</th>
		<th width="17%">Statement No</th>
		<th width="28%">Statement Desc</th>	
	</tr>
	<tbody id="tblList" align="center">
	</tbody>
	<tr>
         	<td colspan="5" class="tdH">
         	<center>Pages          
          	<select name="cmbpage" id="cmbpage" onchange="firstLoad();" ></select></center>
        	</td>
     	</tr>
</table>

</form>
</body>
</html>