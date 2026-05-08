<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Barred_Cheque_Cleared_List</title>
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
	src="../scripts/Barred_Cheque_Cleared_List.js"></script>

<script type="text/javascript" language="javascript">
       function loadyear_month()
       {
             var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             var year=today.getYear();
             if(year < 1900) year += 1900;
                        
            document.frmBarred_Cheque_Cleared_List.txtCB_Year.value=year;
            document.frmBarred_Cheque_Cleared_List.txtCB_Month.value=month;
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
		System - Barred Cheque Cleared List </font></div>
		</td>
	</tr>
</table>

<form name="frmBarred_Cheque_Cleared_List"
	id="frmBarred_Cheque_Cleared_List" method="POST"
	action="Barred_Cheque_Cleared_List">
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
		<div align="left">Cash Book Year &amp; Month<font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtCB_Year"
			id="txtCB_Year" tabindex="3" maxlength="4" size="5"
			onKeyPress="return numbersonly1(event,this)"><select
			name="txtCB_Month" id="txtCB_Month" tabindex="4">
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
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<tr class="tdH">
		<td colspan="2">
		<div align="center">&nbsp;&nbsp;&nbsp; <input type="button"
			name="butList" id="butList" value="LIST" onClick="list('<%=s%>');" />
		&nbsp;&nbsp; <input type="button" name="butCan" id="butCan"
			value="CANCEL" onClick="refresh();" />&nbsp;&nbsp;&nbsp; <input
			type="button" name="butCan" id="butCan" value="EXIT"
			onClick="exitfun();" /> &nbsp;&nbsp;&nbsp;</div>
		</td>
	</tr>
</table>
</div>
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr>
		<td colspan="2" class="table">
		<div id="List Details">

		<table id="Existing" cellspacing="1" cellpadding="2" border="1"
			width="100%">
			<tr class="tdTitle">
				<td colspan="16">
				<div align="left"><font size="4">Barred Cheque Cleared
				List</font></div>
				</td>
			</tr>
			<tr>

				<th>
				<div align="center">Sl.No</div>
				</th>

				<th>
				<div align="center">Doc Type</div>
				</th>

				<th>
				<div align="center">Cheque No</div>
				</th>

				<th>
				<div align="center">Doc No</div>
				</th>

				<th>
				<div align="center">Doc Date</div>
				</th>

				<th>
				<div align="center">Cheque Date</div>
				</th>

				<th>
				<div align="center">Cheque Amount</div>
				</th>

				<th>
				<div align="center">Cheque Valid Upto</div>
				</th>

				<th>
				<div align="center">Followup Action</div>
				</th>
				
				<th>
				<div align="center">Cleared Date</div>
				</th>

			</tr>
			<tbody id="tblList" align="center">
			</tbody>
		</table>

		</div>

		</td>
	</tr>
</table>
</div>

</form>
</body>
</html>