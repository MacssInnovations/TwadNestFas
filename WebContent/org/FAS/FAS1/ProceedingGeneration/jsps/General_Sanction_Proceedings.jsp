<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>General_Sanction_Proceedings</title>
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />

<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
	
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    <script type="text/javascript"
            src="../../../../HR/HR1/OfficeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript"
            src="../../../../Library/scripts/checkDate.js"></script>
             <script type="text/javascript" src="../../../../Library/scripts/comJS.js"></script>
<script src="../scripts/General_Sanction_Proceedings.js" type="text/javascript"> </script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>

<script type="text/javascript" language="javascript">
    function loadyear_month()
    {
	        var today= new Date(); 	        
	        var year=today.getYear();
	        if(year < 1900) year += 1900;	
	        document.frm_General_Sanction_Proceedings.cboCashBook_Year.value=year	       
    }
</script>
</head>

<%String s=request.getContextPath(); %>
<%System.out.println(s); %>
<body onload="LoadAccountingUnitID('LIST_ALL_UNITS');loadyear_month();">

<table cellspacing="2" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><strong>General Sanction
		Proceedings</strong></div>
		</td>
	</tr>
</table>

<form name="frm_General_Sanction_Proceedings"
	id="frm_General_Sanction_Proceedings" method="POST"
	action="General_Sanction_Proceedings">

<table cellspacing="1" cellpadding="2" border="1" width="100%">

	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong></strong></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Accounting Unit Code <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><select size="1" name="cmbAcc_UnitCode"
			id="cmbAcc_UnitCode">

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
			id="cmbOffice_code">

		</select></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">CashBook Year<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="cboCashBook_Year"
			id="cboCashBook_Year"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">CashBook Month<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="cboCashBook_Month"
			id="cboCashBook_Month" onchange="SanctionProceedingNo('<%=s %>')">
			<option value="s">---Select---</option>
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
		<div align="left">Sanction Proceeding No<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><select size="1" name="cboSanctionProceedingNo"
			id="cboSanctionProceedingNo" onchange="SanctionProceedingDate('<%=s %>')">
			<option value="s">---Select---</option>
		</select></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Sanction Proceeding Date <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtSanctionProceedingDate" maxlength="10" size="10"
                   id="txtSanctionProceedingDate" onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);" onblur="return checkdt1(this);"/>  
                   <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frm_General_Sanction_Proceedings.txtSanctionProceedingDate);"
                         alt="Show Calendar"></img></div>
		</td>
	</tr>
		<tr class="table">
		<td>
		<div align="left">Sanctioning Authority & Sanctioned By<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text"
			name="txtSanctioningAuthority" id="txtSanctioningAuthority"
			maxlength="10" size="31"><input type="text"
			name="txtSanctionedBy" id="txtSanctionedBy"
			maxlength="10" size="31"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Total Sanctioned Amount<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text"
			name="txtTotalSanctionedAmount" id="txtTotalSanctionedAmount"
			maxlength="10" size="11"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Presiding Officer<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtPresidingOfficer"
			id="txtPresidingOfficer" maxlength="25" size="11"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Prefix<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtPrefix"
			id="txtPrefix" maxlength="30" size="11"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Suffix<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtSuffix"
			id="txtSuffix" maxlength="10" size="11"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Presiding Officer's Designation<font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text"
			name="txtPresidingOfficerDesignation"
			id="txtPresidingOfficerDesignation" maxlength="30" size="11"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Header<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><textarea name="mtxtHeader" id="mtxtHeader"
			cols="50" rows="2"></textarea></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Subject<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><textarea name="mtxtSubject" id="mtxtSubject"
			cols="50" rows="2"></textarea></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Reference<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><textarea name="mtxtReference"
			id="mtxtReference" cols="50" rows="4"></textarea></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Body Of the Proceeding<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><textarea name="mtxtBodyOftheProceeding"
			id="mtxtBodyOftheProceeding" cols="50" rows="4"></textarea></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Proceeding to be Addressed to<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><textarea name="mtxtProceedingtobeAddressedTo"
			id="mtxtProceedingtobeAddressedTo" cols="50" rows="4"></textarea></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Copy to<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><textarea name="mtxtCopyTo" id="mtxtCopyTo"
			cols="50" rows="4"></textarea></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Additional Para-1<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><textarea name="mtxtAdditionalPara1"
			id="mtxtAdditionalPara1" cols="50" rows="4"></textarea></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Additional Para-2</div>
		</td>
		<td>
		<div align="left"><textarea name="mtxtAdditionalPara2"
			id="mtxtAdditionalPara2" cols="50" rows="4"></textarea></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Signed By With Designation<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><textarea name="mtxtSignedByWithDesignation"
			id="mtxtSignedByWithDesignation" cols="50" rows="2"></textarea></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Remarks</div>
		</td>
		<td>
		<div align="left"><textarea name="mtxtRemarks" id="mtxtRemarks"
			cols="50" rows="2"></textarea></div>
		</td>
	</tr>
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><input type="button" name="butSub"
			id="butSub" value="SUBMIT" onClick="saveFunc('<%=s %>');" />
		&nbsp;&nbsp;&nbsp; <input type="button" name="butCan" id="butCan"
			value="CANCEL" onclick="refresh();" /> &nbsp;&nbsp;&nbsp; <input
			type="button" name="btnCan" id="btnCan" value="EXIT"
			onClick="exitfun('<%=s %>');" /></div>
		</td>
	</tr>
</table>
</form>
</body>
</html>