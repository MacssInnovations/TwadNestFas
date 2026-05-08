<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Civil_Budget_Allocation_Statement1_2_9_10</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
<script type="text/javascript"
	src="../../../../../org/Library/scripts/checkDate.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript"
	src="../scripts/Civil_Budget_Allocation_Statement1.js"></script>
</head>
<%
	String s = request.getContextPath();	
%>

<body
	onLoad="LoadAccountingUnitID('<%=s%>');LoadAccountingUnitID_OfficeID('<%=s%>');initialLoad();"
	bgcolor="#FFF9FF">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><font size="4">Civil Budget
		Allocation </font> ( Statement - 1,2,9,10 )</div>
		</td>
	</tr>
</table>

<form name="Civil_Budget_Allocation_Statement1"
	id="Civil_Budget_Allocation_Statement1" method="POST"
	action="../../../../../Civil_Budget_Allocation_Statement1"><input
	type='hidden' name='RecordCount' id='RecordCount' value='0' /> <input
	type='hidden' name='filter' id='filter' value='no' />
	<input type="hidden" name="T_Altd_Amt_By_HO" id="T_Altd_Amt_By_HO" value="0">
	<input type="hidden" name="T_Altd_Amt_By_RN" id="T_Altd_Amt_By_RN" value="0">
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr class="tdTitle1">
		<td colspan="2">
		<div align="left"><strong>General Details</strong></div>		</td>
	</tr>
	<tr class="table1">
		<td width="30%">
		<div align="left">Accounting Unit Code <font color="#ff2121">*</font>		</div>		</td>
		<td width="70%">
		  <div align="left">
		    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode">        
                         </select>
		  </div></td>
	</tr>
	<tr class="table1">
		<td>
		<div align="left">Accounting For Office Code <font
			color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left">
		  <select size="1" name="cmbOffice_code" id="cmbOffice_code" >
                          
                        </select>
		</div>		</td>
	</tr>

	<tr class="table1">
		<td>
		<div align="left">Budget Allocated to the Unit <font
			color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left">
		  <select size="1" name="cmbAcc_UnitCode1"
			id="cmbAcc_UnitCode1" tabindex="1" onChange="common_LoadOffice1(this.value);">
          </select>
		</div>		</td>
	</tr>
	<tr class="table1">
      <td><div align="left">Format Type  <font
			color="#ff2121">*</font></div></td>
	  <td><div align="left">
	    <label>
	    <select name="cmbFormat_Type" id="cmbFormat_Type">
		<option value="1">1</option>
				<option value="2">2</option>
						<option value="9">9</option>
								<option value="10">10</option>
	      </select>
	    </label>
	  </div></td>
	  </tr>
	<tr class="table1">
      <td><div align="left">Financial Year <font color="#ff2121">*</font></div></td>
	  <td><div align="left">
          <select name="cmbFinancialYear"
			id="cmbFinancialYear">
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

<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr class="tdTitle1">
		<td colspan="2">
		<div align="left"><strong>Details</strong></div>
		</td>
	</tr>
	<tr>
		<td colspan="2">

		<table id="mytable" cellspacing="3" cellpadding="2" border="0"
			width="100%">
			<tr class="tdH1" align="center" style="visibility:hidden" id="HO">
				<th width="5%">Sl No</th>
				<th width="29%">Head of Account</th>
				<th width="22%">Proposal Amount 
			      <label id="l6"
					name="l6"></label></th>
				<th width="17%">Amount to be Alloted 
			  <label id="l6" name="l6"></label></th>
				<th width="27%">Reason for Variation if any</th>
			</tr>
			<tbody id="grid_body" class="table1" align="Center" width="200%">
			</tbody>
		</table>


		<table id="mytable" cellspacing="3" cellpadding="2" border="0"
			width="100%">
			<tr class="tdH1" align="center" style="visibility:hidden" id="RN_CL">
				<th width="5%">Sl No</th>
				<th width="29%">Head of Account</th>
				<th width="29%">Total Amt Alloted By HO to Your RN Office</th>
				<th width="22%">Proposal Amount 
			      <label id="l6"
					name="l6"></label></th>
				<th width="17%">Amount to be Alloted 
			  <label id="l6" name="l6"></label></th>
				<th width="27%">Reason for Variation if any</th>
			</tr>
			<tbody id="grid_body1" class="table1" align="Center" width="200%">
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