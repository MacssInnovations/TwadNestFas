<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Budget_Heads_Master</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script src="../scripts/Budget_Heads_Master.js" type="text/javascript"> </script>
</head>
<%
	String s = request.getContextPath();
%>

<body onLoad="initialLoad('<%=s%>');" bgcolor="#FFF9FF">
<form name="frmBudget_Heads_Master" method="post" action="Budget_Heads_Master"
	id="frmBudget_Heads_Master">
<input type="hidden" name="txtBudgetGroupMinor" id="txtBudgetGroupMinor" size="50">
<table width="930" border="1" align="center">
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><strong> Budget Heads Master</strong></div>		</td>
	</tr>
	<tr class="table1">
		<td width="348">Format No<font color="#ff2121">*</font></td>
		<td width="383"><label>
		  <select name="cmbFormatNo" id="cmbFormatNo" onchange="loadBudgetGp(this.value);">
		  <option value="s">--- Select ---</option>
	    </select>
		</label></td>
	</tr>
	<tr class="table1">
      <td>Budget Group Major<font color="#ff2121">*</font></td>
	  <td><label>
	  <select name="txtBudgetGroupMajor" id="txtBudgetGroupMajor" onchange="secLoad();">
	  <option value="">--- Select ---</option>
	    </select>
	  </label>
	  <input type="hidden" name="txtBudgetDescMain" id="txtBudgetDescMain" value="0">
	  <input type="hidden" name="txtBudgetDescSub" id="txtBudgetDescSub" value="0"></td>
    </tr>
	<tr class="table1">
      <td><div align="left">Can exceed Budget?<font color="#ff2121">*</font></div></td>
	  <td><div align="left">
	    <label></label>
	  
	    <input name="txtCanExceedBudget" type="radio" value="Y" id="txtCanExceedBudget" checked="checked">
        <strong>Yes</strong>
        <input name="txtCanExceedBudget" type="radio" value="N" id="txtCanExceedBudget">
       
        <strong>No</strong>	  
        <input name="txtCanExceedBudget" type="radio" value="NA" id="txtCanExceedBudget">
	    <strong>Not Applicable</strong></div></td>
    </tr>
	<tr class="table1">
      <td><div align="left">Can be Re-appropriated?<font color="#ff2121">*</font></div></td>
	  <td><div align="left">
	   
	    <input name="txtCanbeReAppropriated" type="radio" value="Y" id="txtCanbeReAppropriated" checked="checked">
	    <strong>Yes</strong>
	    <input name="txtCanbeReAppropriated" type="radio" value="N" id="txtCanbeReAppropriated">
      
	    <strong>No</strong> 
	    <input name="txtCanbeReAppropriated" type="radio" value="NA" id="txtCanbeReAppropriated">
	    <strong>Not Applicable</strong></div></td>
    </tr>
	<tr class="table1">
		<td>
		<div align="left">Can be ratified?<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left">
	
		  <input name="txtCanbeRatified" type="radio" value="Y" id="txtCanbeRatified" checked="checked">
		  <strong>Yes</strong>
		  <input name="txtCanbeRatified" type="radio" value="N" id="txtCanbeRatified">
         
		  <strong>No</strong> 
		  <input name="txtCanbeRatified" type="radio" value="NA" id="txtCanbeRatified">
		  <strong>Not Applicable</strong></div></td>
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
<div id=div2 style="overflow: scroll; absolute; height:320px; width:94% align:center ">
<table id="Existing" cellspacing="2" cellpadding="3" border="1"
	width="97%" align="center" class="table1">
	<tr class="tdH1">
		<td align="center" colspan="18"><b>EXISTING DETAILS </b></td>
	</tr>
	<tr class="tdH1">
		<th width="6%">Select</th>
		<th width="7%">Format No</th>		
		<th width="17%">Budget Group Major</th>	
		<th width="10%">Can exceed Budget?</th>
		<th width="11%">Can be Re-appropriated?</th>
		<th width="7%">Can be ratified?</th>

	</tr>
	<tbody id="tblList" align="center">
	</tbody>
</table>
</div>
</form>
</body>
</html>