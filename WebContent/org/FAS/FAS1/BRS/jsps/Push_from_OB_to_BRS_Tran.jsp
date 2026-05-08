<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Push_from_OB_to_BRS_Tran</title>
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
<script type="text/javascript"
	src="../../../../../org/Library/scripts/checkDate.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript"
	src="../../../../Security/scripts/tabpane.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script>

<script type="text/javascript"
	src="../scripts/Push_from_OB_to_BRS_Tran.js"></script>
	<%
	String s = request.getContextPath();
%>
<%
	System.out.println(s);
%>
<script type="text/javascript" language="javascript">
       function loadyear_month()
       {
    	   setTimeout('LoadMonthYear()',900);
      }
    
    </script></head>

<body
	onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS');clr();">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Bank Reconciliation
		System</font> <strong>- Push from OB to BRS Transaction</strong></div>
		</td>
	</tr>
</table>

<form name="frmPush_from_OB_to_BRS_Tran"
	id="frmPush_from_OB_to_BRS_Tran" method="POST"
	action="Push_from_OB_to_BRS_Tran">
<div class="tab-pane" id="tab-pane-1"><!-- ------------------------- General Part I --------------------------------  -->

<div class="tab-page">
<div align="center">

<table cellspacing="1" cellpadding="2" border="0" width="100%">

	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong>.</strong></div>
		</td>
	</tr>

	<tr class="table">
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

	<tr class="table">
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
	<tr class="table">
		<td>
		<div align="left">Bank A/C No.</div>
		</td>
		<td>
		<div align="left"><select name="cmbBankAccNo" id="cmbBankAccNo"
			onchange="brs_ob_status();Bank_Branch_Namee1(this.value);">
			<option value="">-- Select Bank A/C No ---</option>
		</select> <input type="button" name="Go" id="Go" value="Go"
			onclick="LoadBankAccountNumber()" /></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">OB to be Pushed Upto & Incl. CashBookYear & Month</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtCB_Year" readonly="readonly" onChange="LoadMonthYear();"
			id="txtCB_Year" tabindex="3" maxlength="4" size="5"
			onkeypress="return numbersonly1(event,this)"></input> 
			<select name="txtCB_Month" id="txtCB_Month" tabindex="4" onChange="LoadMonthYear();">
			<option value="s">Select</option>
			<option value="1">January</option>
			<option value="2">February</option>
			<option value="3">March</option>
			<option value="4">April</option>
			<option value="5">May</option>
			<option value="6">June</option>
			<option value="7">July</option>
			<option value="8">August</option>
			<option value="9">September</option>
			<option value="10">October</option>
			<option value="11">November</option>
			<option value="12">December</option>
		</select></div>
		</td>
	</tr>

	

	<div align="left" style="visibility: hidden"><input type="hidden"
		name="txtBankID" id="txtBankID" tabindex="5"
		style="background-color: #ececec" readonly="readonly" size="50" /> <input
		type="hidden" name="txtBranchID" id="txtBranchID" size="50"
		style="background-color: #ececec" readonly="readonly" /></div>
</table>

</div>
</div>
</div>
<br>

<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr class="table">
		<td>

		<div id="grid" style="display: block">
		<table id="mytable" cellspacing="3" cellpadding="2" border="0"
			width="100%">
			<tr class="table">				
				<th>Year/Month</th>
				<th>Entry Date </th>
				<th > Twad Type </th>
				<th>Remittance Date</th>
				<th >WithDrawlDate</th>
				<th >Challan No</th>
				<th >Cheque No</th>
				<th >CR Amount </th>
				<th >DR Amount</th>
							
			</tr>			
			<tbody id="grid_body_TWAD" class="table" align="left">
			</tbody>

		</table>
		</div>

		</td>
	</tr>
</table>

<!-- ------------------------- Buttons Part --------------------------------  -->

<div align="center">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td>
		<div align="center" style="display:none" id="subdivid">
		<input type="button" name="butSub" id="butSub" value="SUBMIT"  onClick="doo('<%=s%>');" />
			&nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="refresh();"/>
		&nbsp;&nbsp;&nbsp; <input type="button" name="butCan" id="butCan"
			value="EXIT" onClick="exit();" /></div>
			<div align="center" style="display:block" id="exitdivid">
		       <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="refresh();"/>
		&nbsp;&nbsp;&nbsp; <input type="button" name="butCan" id="butCan"
			value="EXIT" onClick="exit();" /></div>
		</td>
	</tr>
</table>
</div>


</form>
</body>
</html>