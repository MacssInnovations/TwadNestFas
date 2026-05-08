<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Statement Sub Group Master</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script src="../scripts/statement_Sub_Group_Master.js" type="text/javascript"> </script>
</head>
<%
	String s = request.getContextPath();
%>

<body onLoad="statementname('<%=s%>'),initialLoad('<%=s%>');" bgcolor="#FFF9FF">
<form name="statementSubGroupMaster" method="post" action="statementSubGroupMaster"
	id="statementSubGroupMaster">
<table width="930" border="1" align="center">
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><strong>Statement Sub Group Master</strong></div>
		<input type="hidden" name="strpath" id="strpath" value="<%=s%>">		</td>
	</tr>
	<tr class="table1">
      <td>Statement Name<font color="#ff2121">*</font></td>
	  <td><label>
	  <select name="cmbStatementName" id="cmbStatementName" onchange="secondload('<%=s%>'),callGroupName('<%=s%>')">
	  <option value="">---Select---</option>
	    </select>
	  </label></td>
    </tr>
    <tr class="table1">
      <td>Statement Group Name<font color="#ff2121">*</font></td>
	  <td><label>
	  <select name="statementGroupName" id="statementGroupName" onchange="secondload('<%=s%>');">
	  	<option value="">---Select---</option>
	  </select>
	  </label></td>
    </tr>
	<tr class="table1">
		<td width="348">Statement Sub Group No</td>
		<td width="383"><label> <input type="text"
			name="statementSubGroupNo" id="statementSubGroupNo" maxlength="15" size="17" disabled="disabled"/> </label></td>
	</tr>
	<tr class="table1">
		<td>Statement Sub Group Desc<font color="#ff2121">*</font></td>
		<td><input type="text" name="statementSubGroupDesc" id="statementSubGroupDesc" size="70"/></td>
	</tr>
	<tr class="table1">
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><input type="button" name="onsubmit"
			value="ADD" id="onsubmit" onClick="add('<%=s%>');" />  <input
			type="button" name="onupdate" value="UPDATE" id="onupdate"
			onClick="update('<%=s%>');" disabled="disabled" /><input
			type="button" name="ondelete" value="CANCEL" id="ondelete"
			onClick="deleteeee('<%=s%>');" disabled="disabled" /> <input
			type="button" name="onrefresh" value="CLEAR ALL" id="onrefresh"
			onClick="clearAll();" /> <input type="button" name="onexit"
			value="EXIT" id="onexit" onClick="exitfun();" /></div>		</td>
	</tr>
</table>

<p>&nbsp;</p>
<table id="Existing" cellspacing="2" cellpadding="3" border="1"
	width="98%" align="center" class="table1">
	<tr class="tdH1">
		<td colspan="5"><b>EXISTING DETAILS </b></td>
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
		<th width="17%">Statement Name</th>		
		<th width="28%">Statement Group</th>
		<th width="15%">Statement Sub Group No</th>
		<th width="17%">Statement Sub Group Desc</th>	
		<th width="10%">Status</th>
	</tr>
	<tbody id="tblList" align="center">
	</tbody>
	<tr>
         	<td colspan="6" class="tdH">
         	<center>Pages          
          	<select name="cmbpage" id="cmbpage" onchange="firstLoad();" ></select></center>
        	</td>
     	</tr>
</table>

</form>
</body>
</html>