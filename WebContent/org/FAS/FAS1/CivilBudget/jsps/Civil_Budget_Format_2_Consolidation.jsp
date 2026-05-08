<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Civil_Budget_Format_2_Consolidation</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
<script type="text/javascript" src="../scripts/Civil_Budget_Format_2_Consolidation.js"></script>
</head>
<%
	String s = request.getContextPath();
	System.out.println("s");
%>

<body onLoad="LoadAccountingUnitID('LIST_ALL_UNITS');initialLoad();"
	bgcolor="#FFF9FF">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><font size="4">Civil Budget - Revenue
		Account Income</font> ( Format - 2 Consolidate  ) </div>
		</td>
	</tr>
</table>

<form name="frmCivil_Budget_Format_2_Consolidation" id="frmCivil_Budget_Format_2_Consolidation"
	method="POST" action="../../../../../Civil_Budget_Format_2_Consolidation"><input
	type='hidden' name='RecordCount' id='RecordCount' value='0' /> <input
	type='hidden' name='filter' id='filter' value='no' />
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr class="tdTitle1">
		<td colspan="2">
		<div align="left"><strong>General Details</strong></div>		</td>
	</tr>

	<tr class="table1">
		<td>
		  <div align="left">Accounting Unit Code <font color="#ff2121">*</font>          </div></td>
		<td>
		  <div align="left">
		    <select size="1" name="cmbAcc_UnitCode"
			id="cmbAcc_UnitCode" tabindex="1"
			onchange="common_LoadOffice(this.value);">
	          </select>
        </div></td>
	</tr>
	<tr class="table1">
		<td>
		  <div align="left">Accounting For Office Code <font
			color="#ff2121">*</font></div></td>
		<td>
		  <div align="left">
		    <select size="1" name="cmbOffice_code"
			id="cmbOffice_code" tabindex="2">
	          </select>
        </div></td>
	</tr>
	<tr class="table1">
		<td width="30%"><div align="left">Financial Year <font color="#ff2121">*</font></div></td>
		<td width="70%"><div align="left">
		  <select name="cmbFinancialYear"
			id="cmbFinancialYear" onChange="ChangeYearDuration();">
		    </select> 
		  <input type="button" id="butGo" name="butGo" value="GO"
			onclick="initialLoad1();"> 
		  <input type="button"
			name="butView" id="butView" value="View for Update"
			onClick="LoadData('<%=s %>')" />
		  </div></td>
	</tr>
	<tr class="table1">
		<td colspan="2">
		<div align="center"></div>		</td>
	</tr>
</table>
</div>

<table cellspacing="1" cellpadding="2" border="0" width="149%">
	<tr class="tdTitle1">
		<td colspan="2">
		<div align="left"><strong>Details</strong></div>
		</td>
	</tr>
	<tr>
	  <td colspan="2">

		<table id="mytable" cellspacing="3" cellpadding="2" border="0"
			width="149%">
			<tr class="tdH1" align="center">
				<th width="1%">Sl No</th>
				<th width="8%">Head of Account</th>
				<th width="8%">Actuals for the Last Year<label id="l1" name="l1"></label></th>
				<th width="8%">BE for the Year<label id="l2" name="l2"></label></th>
				<th width="9%">Actuals for the Period Apr to Nov<label id="l3" name="l3"></label></th>
				<th width="9%">Anticipated for the Period Dec to Mar<label id="l4" name="l4"></label></th>
				<th width="5%">RE for the Year<label id="l5" name="l5"></label></th>
				<th width="7%">Variation between BE and RE</th>
				<th width="8%">Reason for Variation</th>
				<th width="5%">BE for Next Year<label id="l6" name="l6"></label></th>
				<th width="20%">Reason for Variation if any between RE for the
				Year and the next Year</th>
			</tr>
			<tbody id="grid_body" class="table1" align="Center" width="200%">
			</tbody>
		</table>

		</td>
	</tr>
</table>

<div align="center">
<table cellspacing="1" cellpadding="3" width="100%">
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