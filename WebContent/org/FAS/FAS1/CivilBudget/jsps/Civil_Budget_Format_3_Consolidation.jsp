<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Civil_Budget_Format_3_Consolidation</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
<script type="text/javascript"
	src="../scripts/Civil_Budget_Format_3_Consolidation.js"></script>
</head>
<%
	String s = request.getContextPath();
%>

<body onLoad="LoadAccountingUnitID('LIST_ALL_UNITS');initialLoad();"
	bgcolor="#FFF9FF">
<table cellspacing="1" cellpadding="2" border="0" width="102%">
	<tr class="tdH1">
		<td width="957" colspan="2">
		<div align="center"><font size="4">Civil Budget - Number Statement of Employee on Roll </font>( Format - 3 Consolidate )</div>
		</td>
	</tr>
</table>
<form name="frmCivil_Budget_Format_3_Consolidation" id="frmCivil_Budget_Format_3_Consolidation"
	method="POST" action="../../../../../Civil_Budget_Format_3_Consolidation"><input
	type='hidden' name='RecordCount' id='RecordCount' value='0' /> <input
	type='hidden' name='filter' id='filter' value='no' />
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="102%">
	<tr class="tdTitle1">
		<td colspan="2">
		<div align="left"><strong>General Details</strong></div>
		</td>
	</tr>

	<tr class="table1">
		<td>
		<div align="left">Accounting Unit Code <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><select size="1" name="cmbAcc_UnitCode"
			id="cmbAcc_UnitCode" tabindex="1"
			onchange="common_LoadOffice(this.value);">
		</select></div>
		</td>
	</tr>
	<tr class="table1">
		<td>
		<div align="left">Accounting For Office Code <font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="cmbOffice_code"
			id="cmbOffice_code" tabindex="2">
		</select></div>
		</td>
	</tr>
	<tr class="table1">
		<td width="30%">
		<div align="left">Financial Year <font color="#ff2121">*</font></div>
		</td>
		<td width="70%">
		<div align="left"><select name="cmbFinancialYear"
			id="cmbFinancialYear">
		</select>
		  <input type="button" id="butGo" name="butGo" value="GO"
			onClick="initialLoade();"> 
		  <input type="button" name="butView" id="butView"
			value="View for Update" onClick="LoadData('<%=s%>')" /></div>
		</td>
	</tr>
	<tr class="table1">
		<td colspan="2">
		<div align="center"></div>
		</td>
	</tr>
</table>
</div>

<table cellspacing="1" cellpadding="2" border="0" widtd="251%">
	<tr class="tdTitle1">
		<td colspan="2">
		<div align="left"><strong>Details</strong></div>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<div id="grid" style="display: block" widtd="200%">
		<table id="mytable" cellspacing="3" cellpadding="2" border="0"
			widtd="200%">
			<tr class="tdH1" align="center">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td colspan="2"><strong>As on Begining of the Year </strong> </td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td colspan="2"><strong>Pay After Increment </strong></td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>
				<td>&nbsp;</td>		
				<td>&nbsp;</td>	
				<td>&nbsp;</td>		
			</tr>
			<tr class="tdH1" align="center">
				<td widtd="2%"><strong>Select</strong></td>
				<td widtd="3%"><strong>Name of Category </strong></td>
				<td widtd="4%"><strong>Time Scale of Pay (Specify O.G/S.G/Spl.Gr</strong></td>
				<td widtd="3%"><strong>No of Sanctioned Posts</strong></td>
				<td widtd="3%"><strong>No of Incumbents in Roll</strong></td>
				<td widtd="3%"><strong>No of Vacant Posts</strong></td>
				<td widtd="4%"><strong>Basic Pay </strong></td>
				<td widtd="6%"><strong>50% B.P </strong></td>
				<td widtd="4%"><strong>Increment Date</strong></td>
				<td widtd="4%"><strong>Increment Amount </strong></td>
				<td widtd="4%"><strong>Basic Pay </strong></td>
				<td widtd="6%"><strong>50% B.P </strong></td>
				<td widtd="17%"><strong>Basic Pay After Increment </strong></td>
				<td widtd="43"><strong>Total Basic Pay plus 50% Dearness pay for 12 Months During the Year </strong></td>
				<td widtd="64"><strong>D.A @ 32% on Col.No.13 </strong></td>
				<td widtd="75"><strong>Other Allowances for 12 Months </strong></td>
				<td widtd="62"><strong>Total (10+11+12) </strong></td>
				<td widtd="62"><strong>Date of Retirement During the Year</strong></td>
				
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