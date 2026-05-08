<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Bill_Scrutiny</title>
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
 <script type="text/javascript" src="../../../../Library/scripts/comJS.js"></script>
<script src="../scripts/Bill_Scrutiny.js" type="text/javascript"> </script>
<script type="text/javascript"   src="../../../../Security/scripts/tabpane.js">          </script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
	
<script type="text/javascript" language="javascript">
    function loadyear_month()
    {
	        var today= new Date(); 
	        var day=today.getDate();
	        var month=today.getMonth();
	        month=month+1;
	        var year=today.getYear();
	        if(year < 1900) year += 1900;	
	        document.frm_Bill_Scrutiny.cboCashBook_Year.value=year
	       // document.frm_Bill_Scrutiny.cboCashBook_Month.value=month;
        
    }
</script>
</head>

<%String s=request.getContextPath(); %>
<%System.out.println(s); %>

<body
	onload="LoadAccountingUnitID('LIST_ALL_UNITS'),loadyear_month(),initialLoads('<%=s %>');">
<table cellspacing="2" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><strong>Bill_Scrutiny</strong></div>
		</td>
	</tr>
</table>

<form name="frm_Bill_Scrutiny" id="frm_Bill_Scrutiny" method="POST"
	action="Bill_Scrutiny">

 <div class="tab-pane" id="tab-pane-1">
                <div class="tab-page">
                <h2 class="tab" >General </h2>
                    <div align="center">
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
			id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);">

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
		<div align="left"><input type="text" name="cboCashBook_Year" onchange="initialLoads('<%=s %>');"
			id="cboCashBook_Year" onkeypress="return numbersonly1(event,this)" maxlength="4">

		</div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">CashBook Month<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="cboCashBook_Month"
			id="cboCashBook_Month" onchange="clearBillNo(),initialLoads('<%=s %>');loadDetails('<%=s %>');">
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
		<div align="left">Bill No <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><select size="1" name="cboBillNo"
			id="cboBillNo" onchange="getDetails('<%=s %>');">
			<option value="s">---Select---</option>
		</select></div>
		</td>
	</tr>
		<tr class="table">
		<td>
		<div align="left">Bill Date<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtBillDate"
			id="txtBillDate" maxlength="18" size="11" readonly="true"></div>
		</td>
	</tr>
		<tr class="table">
		<td>
		<div align="left">Sanc Amount<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtBillAmount"
			id="txtBillAmount" maxlength="18" size="11" readonly="true"></div>
		</td>
	</tr>
		<tr class="table">
		<td>
		<div align="left">Sanction Proceeding No<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtSanctionProceedingNo"
			id="txtSanctionProceedingNo" maxlength="18" size="11" readonly="true"></div>
		</td>
	</tr>
		<tr class="table">
		<td>
		<div align="left">Sanction Proceeding Date<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtSanctionProceedingDate"
			id="txtSanctionProceedingDate" maxlength="18" size="11" readonly="true"></div>
		</td>
	</tr>
		<tr class="table">
		<td>
		<div align="left">Account Head Code & Desc<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtAccountHeadCode"
			id="txtAccountHeadCode" maxlength="18" size="11" readonly="true"><input type="text" name="txtAccountHeadCodeDesc"
			id="txtAccountHeadCodeDesc" maxlength="100" size="35" readonly="true"></div>
		</td>
	</tr>
		<tr class="table">
		<td>
		<div align="left">Sub Ledger Type<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtSubLedgerType"
			id="txtSubLedgerType" maxlength="18" size="11" readonly="true"></div>
		</td>
	</tr>
		<tr class="table">
		<td>
		<div align="left">Sub Ledger Code<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtSubLedgerCode"
			id="txtSubLedgerCode" size="45" readonly="true"></div>
		</td>
	</tr>
		<tr class="table">
		<td>
		<div align="left">Deducted Amount<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtDeductedAmount" readonly="readonly"
			id="txtDeductedAmount" maxlength="18" size="11"  onkeypress="return numbersonly1(event,this)"></div>
		</td>
	</tr>
		<tr class="table">
		<td>
		<div align="left">Net Amount<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtNetAmount"
			id="txtNetAmount" maxlength="18" size="11" onkeypress="return numbersonly1(event,this)" readonly="true"></div>
		</td>
	</tr>
	    <tr class="table">
		<td>
		<div align="left">Scrutiny Done<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="radio" name="rdoScrutinyDone"
			id="rdoScrutinyDone" value="Y" checked="checked"><label>Yes</label><input type="radio" name="rdoScrutinyDone"
			id="rdoScrutinyDone" value="N"><label>No</label></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Scrutiny Date <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtScrutinyDate"
			id="txtScrutinyDate" tabindex="4" maxlength="10" size="11"
			onfocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);" onblur="call_date(this);" />
		<img src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frm_Bill_Scrutiny.txtScrutinyDate,1);"
			alt="Show Calendar"></img></div>
		</td>
	</tr>
		<tr class="table">
		<td>
		<div align="left">Remarks</div>
		</td>
		<td>
		<div align="left"><textarea name="mtxtRemarks" id="mtxtRemarks"
			cols="50" rows="4"></textarea></div>
		</td>
	</tr>
	</table>
	  </div>
                    </div>
                    
                    <div class="tab-page" id="gd" >
                    <h2 class="tab" >Scrutiny </h2>
                    <div align="center">
                    
                   <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="2" cellpadding="2" border="1" width="100%">
		                 <tr class="table">
		                <th>Checklist Description</th>
		                <th>Mandate</th>
		                <th>Not Applicable</th>
		                <th>Select</th>                       
		              </tr>
		              <tbody id="grid_body" class="table" align="left" >
		              </tbody>
		            </table>
		            </div>
                </div>
                </div>
                <br>
                 <div align="center">
                   <table cellspacing="1" cellpadding="3" width="100%">
                    
	<tr class="tdH">
		<td colspan="2">
		<div align="center">
		<input type="button" name="butview" id="butview" value="View Details" onClick="viewDetails('<%=s %>');" />&nbsp;&nbsp;&nbsp;
		 <input type="button" name="butSub" id="butSub" value="SUBMIT" onClick="saveFunc('<%=s %>');" />&nbsp;&nbsp;&nbsp;
		 <input type="button" name="butCan" id="butCan" value="CANCEL" onclick="refresh();" /> &nbsp;&nbsp;&nbsp;
		 <input type="button" name="btnCan" id="btnCan" value="EXIT" onClick="exitfun('<%=s %>');" /></div>
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>