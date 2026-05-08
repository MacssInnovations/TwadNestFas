<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BRS_OB_Create</title>
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

<script type="text/javascript" src="../scripts/BRS_OB_Create.js"></script>
<script type="text/javascript" language="javascript">
       function loadyear_month()
       {
             var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             var year=today.getYear();
             if(year < 1900) year += 1900;
               
             setTimeout('LoadBankAccountNumber()',900);    
          // setTimeout('brs_ob_status()',900);
         //    setTimeout('cashbookYear_mon()',900);
      }
    
    </script>
</head>
<%
	String s = request.getContextPath();
%>

<body onload="LoadAccountingUnitID('LIST_ALL_UNITS');loadyear_month();">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Bank Reconciliation System</font><strong> - BRS OB Create</strong> </div>
		</td>
	</tr>
</table>


<form name="frmBRS_OB_Create" id="frmBRS_OB_Create" method="POST"
	action="../../../../../BRS_OB_Create" onSubmit="return checkNull()"><input
	type="hidden" name="filterFlag" id="filterFlag" value="no">
	<input type="hidden" name="filterFlag1" id="filterFlag1" value="no">
	<input type="hidden" name="filterFlag2" id="filterFlag2" value="no"> 
<div class="tab-pane" id="tab-pane-1"><!-- ------------------------- General Part I --------------------------------  -->

<div class="tab-page">
<h2 class="tab">General</h2>

<div align="center">



<table cellspacing="1" cellpadding="2" border="0" width="100%">

	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong>General Details</strong>
		<div id="imgfld"
			style="position: absolute; top: 354px; visibility: hidden; left: 378px; width: 212px; height: 6px;"
			left=100 top=100><input type="image" name="img1" id="img1"
			src="../../../../../images/Loading.gif" height="200"></div>
		</div>
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
			onchange="common_LoadOffice(this.value);LoadBankAccountNumber();">
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
		<div align="left">
		<select name="cmbBankAccNo" id="cmbBankAccNo" onchange="cashbookYear_mon();Bank_Branch_Namee1(this.value);">
		<!--  onchange="Bank_Branch_Namee1(this.value);brs_ob_status();"> -->
			<option value="">-- Select Bank A/C No ---</option>
		</select> 
		<input type="hidden"
			name="txtOprMode" id="txtOprMode" tabindex="5"
			style="background-color: #ececec" readonly="readonly" size="50" /></div>
		</td>
	</tr>
	<div align="left" style="visibility: hidden"><input type="hidden"
		name="txtBankID" id="txtBankID" tabindex="5"
		style="background-color: #ececec" readonly="readonly" size="50" /> <input
		type="hidden" name="txtBranchID" id="txtBranchID" size="50"
		style="background-color: #ececec" readonly="readonly" /></div>
	<tr class="table">
		<td>
		<div align="left">Cash Book Year &amp; Month</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtCB_Year" onChange="LoadMonthYear('<%=s%>');"
			id="txtCB_Year" tabindex="3" maxlength="4" size="5"
			onkeypress="return numbersonly1(event,this)"></input> <select
			name="txtCB_Month" id="txtCB_Month" tabindex="4" onChange="LoadMonthYear('<%=s%>');">
			<option value="s">--- Select ---
			</option>
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

	

	<!-- <tr class="table">
		<td>
		<div align="left">Balance as per Pass Book</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtPBBalance"
			id="txtPBBalance" size="10" /></div>
		</td>
	</tr> -->
</table>
<table cellspacing="1" cellpadding="2" border="0" width="100%">

	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong>Search</strong></div>		</td>
	</tr>


	<tr class="table">
		<td>
		<div align="left">All</div>		</td>
		<td>
		<div align="left"><input type="text" name="txtPBBalance"
			id="txtPBBalance" value="------ All ------" size="8"
			style="background-color: #ececec" readonly="readonly" /> <input
			type="button" name="ChequeNoWise" value="Show" id="ChequeNoWise"
			onclick="doFunction_brs('LoadTWADTransactions','1','NORMAL' );" />
			&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		  <input type="button" name="List" id="List" value="View And Update"
			onClick="LoadGrid()" />
		</div>		</td>
	</tr>
	</table>
</div>
</div>

<input type='hidden' name='RecordCount' id='RecordCount' value='0' /> <input
	type='hidden' name='RecordCountF' id='RecordCountF' value='0' /> <input
	type='hidden' name='RecordCountNT' id='RecordCountNT' value='0' /><input
	type='hidden' name='DeleteT' id='DeleteT' value='0' /><input
	type='hidden' name='DeleteNT' id='DeleteNT' value='0' />
	
	 <!-- ------------------------- TWAD  Transactions Part II --------------------------------  -->

<div class="tab-page" id="gd">
<h2 class="tab">Cash Book Transactions</h2>
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr class="table">
		<td>

		<div id="grid" style="display: block">
		<table id="mytable" cellspacing="3" cellpadding="2" border="0"
			width="100%">
			<tr class="table">				
				<th width="8%"><font size=2> Remittance Date </font></th>
				<th width="8%"><font size=2> Withdrawal Date </font></th>
				<th width="10%"><font size=2> Challan / Voucher No </font></th>
				<th width="9%"><font size=2>Cheque / DD No</font></th>
				<th width="8%"><font size=2>Doc No</font></th>
				<th width="8%"><font size=2>Doc Type</font></th>
				<th width="8%"><font size=2>Doc Date</font></th>
				<th width="8%"><font size=2>CR Amount </font></th>
				<th width="9%"><font size=2>DR Amount </font></th>
				<th width="17%"><font size=2>Entry Not Found in Pass Book? </font></th>					
			</tr>			
			<tbody id="grid_body_TWAD" class="table" align="left">
			</tbody>

		</table>
		</div>

		</td>
	</tr>
</table>



</div>

</div>




<!-- -------------------------Non TWAD  Transactions Part III --------------------------------  -->

<!--
<div class="tab-page" id="gd">
<h2 class="tab">Bank Transactions</h2>
<div align="center">

<table cellspacing="1" cellpadding="2" border="0" width="100%">

	<tr class="table">
		<td>
		<div id="grid" style="display: block">
		<table id="mytable_details" cellspacing="3" cellpadding="2" border="0"
			width="100%">
			<tr class="table">
				<th width="16%">Pass Book Date</th>
				<th width="11%">Particulars</th>
				<th width="12%">Cheque No.</th>
				<th width="8%">Details</th>
				<th width="12%">CR Amount</th>
				<th width="12%">DR Amount</th>
				<th width="23%">Type of Transaction</th>
				<th width="6%">Delete</th>					
			</tr>
			<tr class="table">
				<th><input type="button" name="Add" id="Add" value="Add"
					onclick="doFunction_brs('LoadNONTWADTransactions','0')" /></th>
			</tr>
			<tbody id="grid_body_NONTWAD" class="table" align="left">
			</tbody>
		</table>
		</div>
		</td>
	</tr>

</table>
</div>-->
<input type="button" name="Add" id="Add" value="Add"
					onclick="doFunction_brs('LoadNONTWADTransactions','0')" style="visibility:hidden"/>
<tbody id="grid_body_NONTWAD" class="table" align="left">
			</tbody>
</div>





</div>



<br>



<!-- ------------------------- Buttons Part --------------------------------  -->

<div align="center">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td>
		<div align="center" id="firstid" style="display:none"><input type="SUBMIT" name="butSub"
			id="butSub" value="SUBMIT" onClick="return dateValidation1();" />
		&nbsp;&nbsp;&nbsp; <!--
               <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="clrForm();"/>
                 &nbsp;&nbsp;&nbsp; 
               --> <input type="button" name="butCan" id="butCan"
			value="EXIT" onClick="exit();" /></div>
			<div align="center" id="secondid" style="display:block">
			 <input type="button" name="butCan" id="butCan" value="EXIT" onClick="exit();" /></div>
		</td>
	</tr>
</table>
</div>


</form>
</body>
</html>