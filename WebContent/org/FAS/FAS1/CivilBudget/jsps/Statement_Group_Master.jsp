<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Statement_Group_Master</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script src="../scripts/Statement_Group_Master.js" type="text/javascript"> </script>
</head>
<%
	String s = request.getContextPath();
      System.out.println("path in jsp"+s);
%>

<body onLoad="statementname('<%=s%>'),initialLoad('<%=s%>');" bgcolor="#FFF9FF">
<form name="frmStatement_Group_Master" method="post" action="Statement_Group_Master"
	id="frmStatement_Group_Master">
<table width="930" border="1" align="center">
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><strong>Statement  Group Master</strong></div>
		<input type="hidden" name="strpath" id="strpath" value="<%=s%>">
	   </td>
	</tr>
	<tr class="table1">
      <td>Statement Name<font color="#ff2121">*</font></td>
	  <td><label>
	  <select name="cmbStatementName" id="cmbStatementName" onchange="secondload('<%=s%>');">
	  <option value="">---Select---</option>
	    </select>
	  </label></td>
    </tr>
	<tr class="table1">
		<td width="348">Statement Group No<font color="#ff2121">*</font></td>
		<td width="383"><label> <input type="text"
			name="txtStatementGroupNo" id="txtStatementGroupNo" maxlength="15" size="17" disabled="disabled"/> </label></td>
	</tr>
	<tr class="table1">
		<td>Statement Group Desc<font color="#ff2121">*</font></td>
		<td><input type="text" name="txtStatementGroupDesc"
			id="txtStatementGroupDesc" size="70"/></td>
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
		<td  colspan="3"><b>EXISTING DETAILS </b></td>
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
		<th width="17%" id="sname">Statement Name</th>
		<th width="17%">Statement Group No</th>
		<th width="28%">Statement Group Desc</th>	
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