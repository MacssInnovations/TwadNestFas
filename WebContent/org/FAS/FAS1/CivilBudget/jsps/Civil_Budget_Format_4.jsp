<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Civil_Budget_Format_4</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
<script type="text/javascript" src="../scripts/Civil_Budget_Format_4.js"></script>
</head>
<%
	String s = request.getContextPath();
%>

<body onLoad="LoadAccountingUnitID('LIST_ALL_UNITS');initialLoad();"
	bgcolor="#FFF9FF">
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr class="tdH1">
		<td width="957" colspan="2">
		<div align="center">
		<p><font size="4">Civil Budget - Statement Showing the
		Pension Payment to Pensioners and Family Pensioners </font></p>
		<p><font size="4">( Seperate Statements for Pensioners and
		Family Pensioners ) </font>( Format - 4 )</p>
		</div>
		<div id="imgfld" style="position: absolute; top: 354px; visibility: hidden; 
		left: 378px; width: 212px; height: 6px;" left=100 top=100>
		<input type="image" name="img1" id="img1" src="../../../../../images/Loading.gif" 
		height="200"></div>	
		</td>
	</tr>
</table>
<form name="frmCivil_Budget_Format_4" id="frmCivil_Budget_Format_4"
	method="POST" action="../../../../../Civil_Budget_Format_4"><input
	type='hidden' name='RecordCount' id='RecordCount' value='0' /> <input
	type='hidden' name='filter' id='filter' value='no' />
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">
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
		</select> <input type="button" name="butGo" id="butGo" value="Go"
			onClick="load_hrm('<%=s%>')" />&nbsp;&nbsp;&nbsp; <input
			type="button" name="butView" id="butView" value="View for Update"
			onClick="LoadData('<%=s%>')" /></div>
		</td>
	</tr>
	<tr class="table1">
		<td colspan="2">
		<div align="center"></div>
		</td>
	</tr>
</table>
</div>

<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr class="tdTitle1">
		<td colspan="2">
		<div align="left"><strong>Details</strong></div>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<div id="grid" style="display: block" width="200%">
		<table id="mytable" cellspacing="3" cellpadding="2" border="0"
			width="100%">
			<tr class="tdH1" align="center">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td colspan="4"><strong>Superannuation</strong></td>
				<td>&nbsp;</td>
				<td colspan="3"><strong>Voluntary Retirement </strong></td>
				<td colspan="3"><strong>Superannuation</strong></td>
				<td colspan="3"><strong>Voluntary Retirement </strong></td>
			</tr>
			<tr class="tdH1" align="center">
				<td></td>
				<td></td>
				<td></td>
				<td></td>
				<td>&nbsp;</td>
				<td colspan="3"><strong>Amount Paid upto Nov </strong></td>
				<td>&nbsp;</td>
				<td colspan="3"><strong>Amount Paid upto Nov</strong></td>
				<td colspan="3"><strong>If Not Paid,Anticipated amount
				in this Year and Next Year </strong></td>
				<td colspan="3"><strong>If Not Paid,Anticipated amount
				in this Year and Next Year </strong></td>
			</tr>
			<tr class="tdH1" align="center">
				<td width="2%"><strong>Sl.No</strong></td>
				<td width="4%"><strong>PPO NO </strong></td>
				<td width="8%"><strong>Name of Employee </strong></td>
				<td width="5%"><strong>Designation </strong></td>
				<td width="8%"><strong>Date of Retirement</strong></td>
				<td width="8%"><strong>Encashment of L.S </strong></td>
				<td width="9%"><strong>Commutation of Pension </strong></td>
				<td width="7%"><strong>Gratuity</strong></td>
				<td width="9%"><strong>Date of Retirement</strong></td>
				<td width="8%"><strong>Encashment of L.S </strong></td>
				<td width="9%"><strong>Commutation of Pension </strong></td>
				<td width="7%"><strong>Gratuity</strong></td>
				<td width="8%"><strong>Encashment of L.S </strong></td>
				<td width="9%"><strong>Commutation of Pension </strong></td>
				<td width="7%"><strong>Gratuity</strong></td>
				<td width="8%"><strong>Encashment of L.S </strong></td>
				<td width="9%"><strong>Commutation of Pension </strong></td>
				<td width="7%"><strong>Gratuity</strong></td>

			</tr>
			<!--  <tr class="table1">
				<th><input type="button" name="Add" id="Add" value="Add"
					onclick="AddRow()" /></th>
				<th><div align="left">
				  <input type="button" name="Clear" id="Clear" value="Delete"
					onclick="ClearRow();" />
			    </div></th>
			</tr>     -->
			<tbody id="grid_body" class="table1" align="Center" width="200%">
			</tbody>
		</table>
		</div>
		</td>
	</tr>
</table>

<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">
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