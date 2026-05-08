<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BRS Part2-5a And 5b-Breakup</title>
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
<script type="text/javascript" src="../scripts/breakup_report_five_a_b.js"></script>

<script type="text/javascript" language="javascript">
      
    </script>
</head>
<%
	String s = request.getContextPath();
%>

<body 
	onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS');">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><strong>Part2-5a And 5b-Breakup</strong> </div>
		</td>
	</tr>
</table>


<form name="formbrs_ob_report" id="formbrs_ob_report" method="POST" onsubmit="return checkNull();"
	action="../../../../../BRS_OB_Report_not_pushed?command=both_five"> 
	<input type="hidden" id="old" name="old" value="old">

<table cellspacing="1" cellpadding="2" border="0" width="100%">

	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong>General Details</strong>
		
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
		<div align="left">Cash Book Year &amp; Month</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtCB_Year"
			id="txtCB_Year" tabindex="3" maxlength="4" size="5"
			onkeypress="return numbersonly1(event,this)"></input> <select
			name="txtCB_Month" id="txtCB_Month" tabindex="4" >
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

	<tr class="table">
		<td>
		<div align="left">Breakup Type<font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left">
		<select size="1" name="breakup_type" id="breakup_type" tabindex="2">
		<option value="5a">5a-Breakup For Part2</option>
		<option value="5b">5b-Breakup For Part2</option>
		</select></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Bank A/C No.</div>
		</td>
		<td>
		<div align="left"><select name="cmbBankAccNo" id="cmbBankAccNo">
			
			<option value="">-- Select Bank A/C No ---</option>
		</select> <input type="button" name="Go" id="Go" value="Go"
			onclick="LoadBankAccountNumber_opr()" />
		<input type="hidden"
			name="txtOprMode" id="txtOprMode" tabindex="5"
			style="background-color: #ececec" readonly="readonly" size="50" /></div>
		</td>
	</tr>




	
</table>






<br>



<!-- ------------------------- Buttons Part --------------------------------  -->

<div align="center">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td>
		<div align="center"><input type="SUBMIT" name="butSub"
			id="butSub" value="SUBMIT" />
		&nbsp;&nbsp;&nbsp; <!--
             
               --> <input type="button" name="butCan" id="butCan"
			value="EXIT" onClick="exit();" /></div>
		</td>
	</tr>
</table>
</div>


</form>
</body>
</html>