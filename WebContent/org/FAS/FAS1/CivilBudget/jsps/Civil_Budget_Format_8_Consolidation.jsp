<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Civil_Budget_Format_8_Consolidation</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
<script type="text/javascript"
	src="../scripts/Civil_Budget_Format_8_Consolidation.js"></script>
</head>
<%
	String s = request.getContextPath();
%>

<body onLoad="LoadAccountingUnitID('LIST_ALL_UNITS');initialLoad();"
	bgcolor="#FFF9FF">
<table cellspacing="1" cellpadding="2" border="0" width="102%">
	<tr class="tdH1">
		<td width="957" colspan="2">
		<div align="center">
		  <p><font size="4">Civil Budget - CWSS Scheemes Under Maintenance </font></p>
		  <p><font size="4">( Seperate Statement for each scheme in respect of Board Owned 5 Shcmes</font></p>
		  <p><font size="4"> has to be Finished and for CWSS Single Statement is enough ) </font>( Format - 8 Consolidation )</p>
		</div>
		</td>
	</tr>
</table>
<form name="frmCivil_Budget_Format_8_Consolidation" id="frmCivil_Budget_Format_8_Consolidation"
	method="POST" action="../../../../../Civil_Budget_Format_8_Consolidation"><input
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
			onClick="initialLoad1();"> 
		  <input type="button" name="butView" id="butView"
			value="View for Update" onClick="LoadData('<%=s%>')" /></div>		</td>
	</tr>
	<tr class="table1">
		<td colspan="2">
		<div align="center"></div>		</td>
	</tr>
</table>
</div>

<table width="997" border="0" cellpadding="2" cellspacing="1" widtd="251%">
	<tr class="tdTitle1">
		<td width="991" colspan="2">
		<div align="left"><strong>Details</strong></div>	  </td>
	</tr>
	<tr>
		<td colspan="2">
		<div id="grid" style="display: block" widtd="200%">
		  <table width="1003" border="0" cellpadding="2" cellspacing="3" id="mytable"
			widtd="200%">
            <tr class="tdH1" align="center">
              <td width="61"></td>
              <td width="246"></td>
              <td colspan="3"><strong>Current Year</strong> </td>
              <td width="131"></td>
            </tr>
            <tr class="tdH1" align="center">
              <td widtd="2%"><strong>Sl.No</strong></td>
              <td widtd="3%"><strong>Nature of Expendidure </strong></td>
              <td width="148" widtd="3%"><strong>Upto Nov CY </strong></td>
              <td width="314" widtd="4%"><strong>Anticipated Dec to End of CY </strong></td>
              <td width="58" widtd="6%"><strong>Total</strong></td>
              <td widtd="4%"><strong>Next Year </strong></td>
            </tr>           
            <tbody id="grid_body" class="table1" align="Center" widtd="200%">
            </tbody>
          </table>
		</div>		</td>
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