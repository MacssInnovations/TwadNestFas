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
	src="../scripts/push_pass_sheet_transaction_to_brs.js"></script>
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
	onload="LoadAccountingUnitID('LIST_ALL_UNITS');">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center">
		<strong>Push Pass Sheet Transaction to BRS</strong></div>
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
			onchange="loadyear_month();Bank_Branch_Namee1(this.value);">
			<option value="">-- Select Bank A/C No ---</option>
		</select> <input type="button" name="Go" id="Go" value="Go"
			onclick="LoadBankAccountNumber()" />
			
			<input type="hidden" name="txtOprMode" id="txtOprMode" tabindex="5" style="background-color: #ececec" readonly="readonly" size="50" />
			</div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">CashBookYear & Month</div>
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

<!--  <table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr class="table">
		<td>

		<div id="grid" style="display: block">
		<table id="mytable" cellspacing="3" cellpadding="2" border="0"
			width="100%">
			<tr class="table">				
				<th width="2%">Year/Month</th>
				<th width="5%">Entry Date </th>
				<th width="3%"> Twad Type </th>
				<th width="8%">Remit Date</th>
				<th width="5%">WithDrawlDt</th>
				<th width="5%">Challan_No</th>
				<th width="7%">Chq_No</th>
				<th width="15%">Transaction Type</th>
				<th width="8%">CR Amt </th>
				<th width="8%">DR Amt</th>
							
			</tr>			
			<tbody id="grid_body_TWAD" class="table" align="left">
			</tbody>

		</table>
		</div>

		</td>
	</tr>
</table>-->

<!-- ------------------------- Buttons Part --------------------------------  -->

<div align="center">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td>
		<div align="center"><input type="button" name="butSub" id="butSub" value="SUBMIT"  onClick="doo('<%=s%>');" />
			&nbsp;&nbsp;&nbsp; 
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