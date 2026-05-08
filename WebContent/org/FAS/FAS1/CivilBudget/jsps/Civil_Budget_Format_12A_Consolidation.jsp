<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Civil_Budget_Format_12A_Consolidation</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
<script type="text/javascript"
	src="../scripts/Civil_Budget_Format_12A_Consolidation.js"></script>
</head>
<%
	String s = request.getContextPath();
%>

<body onLoad="LoadAccountingUnitID('LIST_ALL_UNITS');initialLoad();"
	bgcolor="#FFF9FF">
<table cellspacing="1" cellpadding="2" border="0" width="102%">
	<tr class="tdH1">
		<td width="957" colspan="2">
		<div align="center"><font size="4">Civil Budget - Recovery of Cost ( Workshops Division ) </font>( Format - 12A Consolidate  )</div>
		</td>
	</tr>
</table>
<form name="frmCivil_Budget_Format_12A_Consolidation" id="frmCivil_Budget_Format_12A_Consolidation"
	method="POST" action="../../../../../Civil_Budget_Format_12A_Consolidation"><input
	type='hidden' name='RecordCount' id='RecordCount' value='0' /> <input
	type='hidden' name='filter' id='filter' value='no' />
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="102%">
	<tr class="tdTitle1">
		<td colspan="2">
		<div align="left"><strong>General Details</strong></div>		</td>
	</tr>

	<tr class="table1">
		<td>
		<div align="left">Accounting Unit Code <font color="#ff2121">*</font>		</div>		</td>
		<td>
		<div align="left"><select size="1" name="cmbAcc_UnitCode"
			id="cmbAcc_UnitCode" tabindex="1"
			onchange="common_LoadOffice(this.value);">
		</select></div>		</td>
	</tr>
	<tr class="table1">
		<td>
		<div align="left">Accounting For Office Code <font
			color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><select size="1" name="cmbOffice_code"
			id="cmbOffice_code" tabindex="2">
		</select></div>		</td>
	</tr>
	<tr class="table1">
		<td width="30%">
		<div align="left">Financial Year <font color="#ff2121">*</font></div>		</td>
		<td width="70%">
		<div align="left"><select name="cmbFinancialYear"
			id="cmbFinancialYear">
		</select>
		  <input type="button" id="butGo" name="butGo" value="GO"
			onClick="initialLoade();">
		  <input type="button" name="butView" id="butView"
			value="View for Update" onClick="LoadData('<%=s%>')" /></div>		</td>
	</tr>
	<tr class="table1">
		<td colspan="2">
		<div align="center"></div>		</td>
	</tr>
</table>
</div>

<table width="1024" border="0" cellpadding="2" cellspacing="1" widtd="251%">
	<tr class="tdTitle1">
		<td width="1024" colspan="2">
		<div align="left"><strong>Details</strong></div>
	  </td>
	</tr>
	<tr>
		<td colspan="2">
		<div id="grid" style="display: block" widtd="200%">
		<table width="1024" border="0" cellpadding="2" cellspacing="3" id="mytable"
			widtd="200%">
			<tr class="tdH1" align="center">
				<td width="41"></td>
				<td width="57"></td>
				
				<td colspan="2"><strong>Pending as on Begining of the Year </strong> </td>
				<td colspan="4"><strong>TDA Raised </strong></td>
				<td colspan="4"><strong>TDA Accepted and Adjusted or Cheque Received </strong></td>
				<td colspan="3"><strong>Value of Jobs in Progress as on Begining of the Year  </strong></td>						
			</tr>
			<tr class="tdH1" align="center">
				<td widtd="2%"><strong>Select</strong></td>
				<td widtd="3%"><strong>Vehicles</strong></td>
				
				<td width="59" widtd="4%"><strong>No. 1a  </strong></td>
				<td width="89" widtd="6%"><strong>Value. 1b </strong></td>
				<td width="31" widtd="4%"><strong>No. 2a </strong></td>
				<td width="50" widtd="6%"><strong>Value. 2b </strong></td>
				<td width="31" widtd="4%"><strong>No. 3a </strong></td>
				<td width="50" widtd="6%"><strong>Value. 3b </strong></td>
				<td width="43" widtd="4%"><strong>No. 4a </strong></td>
				<td width="68" widtd="6%"><strong>Value. 4b </strong></td>
				<td width="43" widtd="4%"><strong>No. 5a </strong></td>
				<td width="68" widtd="6%"><strong>Value. 5b </strong></td>
				<td width="43" widtd="4%"><strong>No. 6a </strong></td>
				<td width="67" widtd="6%"><strong>Value. 6b </strong></td>
				<td width="176" widtd="4%"><strong>Expected Date 7</strong></td>				
			</tr>
			<tr class="table1">
				<th><input type="button" name="Add" id="Add" value="Add"
					onclick="AddRow()" /></th>
				<th><div align="left">
				  <input type="button" name="Clear" id="Clear" value="Delete"
					onclick="ClearRow();" />
			    </div></th>
			</tr>
			<tbody id="grid_body" class="table1" align="Center" widtd="200%">
			</tbody>
		</table>
		</div>
		</td>
	</tr>
</table>

<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="102%">
	<tr class="tdH1">
		<td>
		<div align="center"><input type="submit" name="butSub"
			id="butSub" value="SAVE" onClick="return funcSave();" /> &nbsp; <input
			type="submit" name="butUpdate" id="butUpdate" value="UPDATE"
			disabled="disabled" onClick="return funcUpdate();" /> &nbsp; <input
			type="submit" name="butDelete" id="butDelete" value="DELETE"
			disabled="disabled" onClick="return funcDelete();" /> &nbsp; <input
			type="button" name="butCan" id="butCan" value="CANCEL"
			onclick="clrForm();" /> &nbsp;&nbsp;&nbsp; <input type="button"
			name="butCan" id="butCan" value="EXIT" onClick="exitfun();" /></div>
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>