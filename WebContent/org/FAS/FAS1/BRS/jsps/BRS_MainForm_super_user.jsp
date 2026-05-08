<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page
	import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>

<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252" />
<meta http-equiv="cache-control" content="no-cache">
<title>Bank Reconciliation System</title>
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

<script type="text/javascript" src="../scripts/BRS_MainForm_super_user.js"></script>

<script type="text/javascript" language="javascript">
       function loadyear_month()
       {
    	
    	
    	 // setTimeout('LoadBankAccountNumber()',900);
    	  
      }
    
    </script>
</head>
<%
	String s1 = request.getContextPath();
%>

<body onload="LoadAccountingUnitID('LIST_ALL_UNITS');loadyear_month();clr();">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Bank Reconciliation
		System</font></div>
		</td>
	</tr>
</table>
<form name="frmBRSMainForm" id="frmBRSMainForm" method="POST" 
action="../../../../../BRS_MainForm.kv" onsubmit="return checkNull()">
<input type="hidden" name="filterFlag" id="filterFlag" value="no">
<div class="tab-pane" id="tab-pane-1"><!-- ------------------------- General Part I --------------------------------  -->
<div class="tab-page">
<h2 class="tab">General</h2>
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong>General Details</strong>
		<div id="imgfld" style="position: absolute; top: 354px; visibility: hidden; left: 378px; width: 212px; height: 6px;"
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
		<div align="left">Bank A/C No.<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select name="cmbBankAccNo" id="cmbBankAccNo" onchange="Bank_Branch_Namee1(this.value);LoadMonthYear('<%=s1%>');">
                <!--  onchange="closeBRS('<%=s1%>');"> -->
              
			<option value="">-- Select Bank A/C No ---</option>
		</select>
		<input type="button" name="goo" id="goo" value="Go" onclick="LoadBankAccountNumber();"></input>
			<input type="hidden" name="txtOprMode" id="txtOprMode" tabindex="5" style="background-color: #ececec" readonly="readonly" size="50" />
			</div>
		</td>
	</tr>
	
	<tr class="table">
		<td>
		<div align="left">Cash Book Year &amp; Month<font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3" maxlength="4" size="5"
			onkeypress="return numbersonly1(event,this)"></input> 
			<select name="txtCB_Month" id="txtCB_Month" tabindex="4" >
			<option value="s">--- Select ---</option>
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

	<tr class="table">
		<td>
		<div align="left">Balance as per Pass Book<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left">
		<input type="text" name="txtPBBalance" id="txtPBBalance" size="10" />
		
		<font >
		(Prev.Month PassSheetBal+CurrMonth Rem-CurrMonth wd)
		</font>
		<input type="text" readonly name="pbReadonly" id="pbReadonly" size="15" />
		</div>
		</td>
	</tr>
</table>
<table cellspacing="1" cellpadding="2" border="0" width="100%">

	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong>Search</strong></div>
		</td>
	</tr>


	<tr class="table">
		<td>
		<div align="left">All</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtPBBalance"
			id="txtPBBalance" value="------ All ------" size="8"
			style="background-color: #ececec" readonly="readonly" /> <input
			type="button" name="ChequeNoWise" value="Go" id="ChequeNoWise"
			onclick="doFunction_brs('LoadTWADTransactions','1','NORMAL' );" /></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Cheque number</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtChequeNumber"
			id="txtChequeNumber" size="15" /> <input type="button"
			name="ChequeNoWise" value="Go" id="ChequeNoWise"
			onclick="doFunction_brs('LoadTWADTransactions','1','ChequeNoWise');" />
		</div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Doc Type</div>
		</td>
		<td>
		<div align="left"><select name="txtdoctype" id="txtdoctype">
			<option value="CR">Cash Receipt</option>
			<option value="BR">Bank Receipt</option>
			<option value="P">Payment</option>
			<option value="SC">Self Cheque</option>
			<option value="FT from HO">Fund Transfer from HO</option>
			<option value="FT from Office">Fund Transfer from Office</option>
			<option value="FR by HO">Fund Receipt by HO</option>
			<option value="FR by Office">Fund Receipt by Office</option>
		</select> <input type="button" name="DocTypeWise" value="Go" id="DocTypeWise"
			onclick="doFunction_brs('LoadTWADTransactions','1','DocTypeWise');" />
		</div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">From Date and To Date</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtFromDate"
			id="txtFromDate" tabindex="4" maxlength="10" size="11"
			onfocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);" onBlur="call_date(this);" />
		<img src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frmBRSMainForm.txtFromDate,1);"
			alt="Show Calendar"></img> <input type="text" name="txtToDate"
			id="txtToDate" tabindex="4" maxlength="10" size="11"
			onfocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);" onBlur="call_date(this);" />
		<img src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frmBRSMainForm.txtToDate,1);"
			alt="Show Calendar"></img> <input type="button" name="DateWise"
			value="Go" id="DateWise"
			onclick="doFunction_brs('LoadTWADTransactions','1','DateWise');" />
		</div>
		</td>
	</tr>
</table>
</div>
</div>
<input type='hidden' name='RecordCount' id='RecordCount' value='0' /> <input
	type='hidden' name='RecordCountF' id='RecordCountF' value='0' /> <input
	type='hidden' name='RecordCountNT' id='RecordCountNT' value='0' /> <!-- ------------------------- TWAD  Transactions Part II --------------------------------  -->
<div class="tab-page" id="gd">
<h2 class="tab">Cash Book Transactions</h2>
<div align="center">
<table id="mytable" cellspacing="0" cellpadding="0" border="0">
	<tbody id="grid_body_TotTrans" class="table" align="left">
	</tbody>
</table>
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong>No of Records :</strong> <input
			name="txtNoofRecords" type="text" id="txtNoofRecords" size="5"
			readonly="true">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<strong>Voucher Monitoring Amount(Total)</strong> 
		<input name="vmAmount" type="text" id="vmAmount" size="10" readonly="true">
		&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;
		<strong>Enter Cheque / DD No :</strong> <input type="text"
			name="txtSearch" id="txtSearch"> <input type="button"
			name="btnSearch" id="btnSearch" value="Search" onclick="search();">
		</div>
		</td>
	</tr>
	<tr>
		<td colspan="2">
		<div id="grid" style="display: block">
		<table id="mytable" cellspacing="3" cellpadding="2" border="0"
			width="107%">
			<tr class="table">
				<th width="2%"><font size=2> Sl No </font></th>
				<th width="8%"><font size=2> Remittance Date </font></th>
				<th width="8%"><font size=2> Withdrawal Date </font></th>
				<th width="14%"><font size=2> Challan / Voucher No </font></th>
				<th width="11%"><font size=2>Cheque / DD No</font></th>
				<th width="7%"><font size=2>CR Amount </font></th>
				<th width="7%"><font size=2>DR Amount </font></th>
				<th width="5%"><font size=2>Entry Found in Pass Book? </font></th>
				<th width="6%"><font size=2>Pass Book Date </font></th>
				<th width="6%"><font size=2>Amount in Pass Book </font></th>
				<th width="7%"><font size=2>Difference</font></th>
				<th width="7%"><font size=2>Reason for Difference</font></th>
				<th width="7%"><font size=2>Follow-up Action Required? </font></th>
				<th width="7%"><font size=2>Is it a clearing Entry for Excess/short debit or credit</font></th>
			</tr>
			<tbody id="grid_body_TWAD" class="table" align="left">
			</tbody>
		</table>
		<div id="div1" style="width: 100%; visibility: hidden">
			<div style="left: 25%; position: absolute; width: 15%;">
				<input type="text" name="crAmount" id="crAmount" style="width: 100%;" disabled="disabled">
			</div>
			<div style="left: 41%; position: absolute; width: 15%;">
				<input type="text" name="drAmount" id="drAmount" style="width: 100%;" disabled="disabled">
			</div>			
		</div>
		</div>
		</td>
	</tr>
</table>
</div>
</div>
<!-- -------------------------Non TWAD  Transactions Part III --------------------------------  -->
<!--<div class="tab-page" id="gd">
<h2 class="tab">Bank Transactions</h2>
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">

	<tr class="table">
		<td>
		<div id="grid" style="display: block">
		<table id="mytable_details" cellspacing="3" cellpadding="2" border="0"
			width="100%">
			<tr class="table">
				<th>Pass Book Date</th>
				<th>Particulars</th>
				<th>Cheque No.</th>
				<th>Cheque Details</th>
				<th>CR Amount</th>
				<th>DR Amount</th>
				<th>Type of Transaction</th>
				<th>Follow-up Action Required ?</th>
				<th>Is it a clearing Entry for Excess/short debit or credit</th>
				<th>To Be Journalized</th>
			</tr>
			<tr class="table">
				<th><input type="button" name="Add" id="Add" value="Add" 
					onclick="doFunction_brs('LoadNONTWADTransactions','0')" /></th>
				<th><input type="button" name="Clear" id="Clear" value="Clear"
					onclick="ClearRow();" /></th>
			</tr>
			<tbody id="grid_body_NONTWAD" class="table" align="left">
			</tbody>
		</table>
		</div>
		</td>
	</tr>
</table>
</div>
</div>
--></div>
<br>
<!-- ------------------------- Buttons Part --------------------------------  -->
<div align="center">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td>
		<div align="center"><input type="SUBMIT" name="butSub"
			id="butSub" value="SUBMIT" onClick="return dateValidation1();" />
		&nbsp;&nbsp;&nbsp; <!--
               <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="clrForm();"/>
                 &nbsp;&nbsp;&nbsp; 
               --> <input type="button" name="butCan" id="butCan"
			value="EXIT" onclick="exit();" /></div>
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>