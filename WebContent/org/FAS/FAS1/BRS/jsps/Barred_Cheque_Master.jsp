<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Barred_Cheque_Master</title>
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

<script type="text/javascript" src="../scripts/Barred_Cheque_Master.js"></script>

<script type="text/javascript" language="javascript">
       function loadyear_month()
       {
             var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             var year=today.getYear();
             if(year < 1900) year += 1900;
                        
            document.frmBarred_Cheque_Master.txtCB_Year.value=year;
          //  document.frmBarred_Cheque_Master.txtCB_Month.value=month;
      }
    
    </script>

</head>
<%
	String s = request.getContextPath();
%>
<%
	System.out.println(s);
%>
<body onLoad="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS');">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Bank Reconciliation
		System - Barred Cheque Master</font></div>
		</td>
	</tr>
</table>

<form name="frmBarred_Cheque_Master" id="frmBarred_Cheque_Master"
	method="POST" action="BRS_Barred_Cheque_Master">
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong>General Details</strong></div>
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
			onChange="common_LoadOffice(this.value);">
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
		</select> <label></label></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Bank A/C No.<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select name="cmbBankAccNo" id="cmbBankAccNo">
			<option value="">-- Select Bank A/C No ---</option>
		</select> <input type="button" name="Go" id="Go" value="Go"
			onclick="LoadBankAccountNumber()" /></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Cash Book Year &amp; Month<font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtCB_Year" onChange="LoadMonthYear('<%=s%>');"
			id="txtCB_Year" tabindex="3" maxlength="4" size="5"
			onKeyPress="return numbersonly1(event,this)">
			<select name="txtCB_Month" id="txtCB_Month" tabindex="4" onChange="LoadMonthYear('<%=s%>');">
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
	
	<tr class="table">
		<td>Doc Type <font color="#ff2121">*</font></td>
		<td><label> <select name="cmbDoc_Type" id="cmbDoc_Type">
			<option value="s">--- Select ---</option>
			<option value="Receipt">Receipt</option>
			<option value="Payment">Payment</option>
		</select> </label></td>
	</tr>
	<tr class="table">
		<td>Cheque No <font color="#ff2121">*</font></td>
		<td><label> <input type="text" name="cmbCheque_No"
			id="cmbCheque_No" onBlur="LoadDocNo('<%=s%>');"
			onKeyPress="return numbersonly1(event,this)"> </label></td>
	</tr>
	<tr class="table">
		<td>Doc No</td>
		<td>
                <div id="comboid">
                <label> <select name="txtDoc_No" id="txtDoc_No"
			onChange="LoadChequeNoDetails('<%=s%>');">
			<option value="s">--- Select ---</option>
		</select>
               
                </label>
                </div>
                <div id="textid"  style="display:none">
                <label> 
               <input type="text" id="txtDoc_No" name="txtDoc_No"></input>
                </label>
                </div>
                </td>
	</tr>
	<tr class="table">
		<td>Doc Date</td>
		<td><label> <input type="text" name="txtDoc_Date" onblur="cashbook_ck();" onkeypress="return calins(event,this);"
			id="txtDoc_Date" size="11"> </label></td>
	</tr>
	<tr class="table">
		<td>Cheque Date</td>
		<td><input type="text" name="txtCheque_Date " id="txtCheque_Date"
			size="11"></td>
	</tr>
	<tr class="table">
		<td>Cheque Amount</td>
		<td><input type="text" name="txtCheque_Amount "
			id="txtCheque_Amount"></td>
	</tr>
	<tr class="table">
		<td>Cheque Valid Upto <font color="#ff2121">*</font></td>
		<td><input type="text" name="txtCheque_Valid_Upto" readonly
			id="txtCheque_Valid_Upto" onFocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);" maxlength="10" size="11"
			onBlur="call_date(this);"> <img
			src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frmBarred_Cheque_Master.txtCheque_Valid_Upto,1);"
			alt="Show Calendar"></td>
	</tr>
	<tr class="table">
		<td>Followup Action</td>
		<td><label> <textarea name="mtxtFollowup_Action"
			id="mtxtFollowup_Action"></textarea> </label></td>
	</tr>
	<tr class="table">
		<td>Cleared Entries</td>
		<td>Yes
		  <label>
		  <input name="txtCleared_Entries" type="radio" id="txtCleared_Entries" value="Y">
		  </label>
		  No
          <input name="txtCleared_Entries" type="radio" id="txtCleared_Entries" value="N"></td>
	</tr>
	<tr class="table">
		<td>Cleared Date</td>
		<td><label></label>
		<input type="text" name="txtCleared_Date"
			id="txtCleared_Date" onFocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);" maxlength="10" size="11"
			onBlur="call_date(this);"> <img
			src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frmBarred_Cheque_Master.txtCleared_Date,1);"
			alt="Show Calendar"></td>
	</tr>
	<tr class="table">
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><input type="button" name="onsubmit1"
			value="SUBMIT" id="onsubmit1" onClick="Add('<%=s%>');" />
		&nbsp;&nbsp;&nbsp; <input type="button" name="butUpdate"
			id="butUpdate" value="UPDATE" onClick="Update('<%=s%>');"
			disabled="disabled" /> &nbsp;&nbsp;&nbsp; <input type="button"
			name="butDelete" id="butDelete" value="DELETE"
			onClick="Delete('<%=s%>');" disabled="disabled" />
		&nbsp;&nbsp;&nbsp; <input type="button" name="butCan" id="butCan"
			value="CANCEL" onClick="refresh();" />&nbsp;&nbsp;&nbsp; <input
			type="button" name="butCan" id="butCan" value="EXIT"
			onClick="exitfun();" /> &nbsp;&nbsp;&nbsp; <input type="button"
			name="butList" id="butList" value="LIST" onClick="list();" /></div>
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>