<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>SanctionProceeding-SinglePayeeFirm</title>

<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />

<script type="text/javascript"
	src="../../../../../org/FAS/FAS1/CalendarCtrl_forChequeDate.js"></script>
<script type="text/javascript"
	src="../../../../../org/Library/scripts/checkDate.js"></script>
 <script type="text/javascript" src="../../../../Library/scripts/comJS.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>

<script type="text/javascript"
	src="../../../../Security/scripts/tabpane.js"></script>

<script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script>
         <script type="text/javascript" src=".../../../../ReceiptSystem/scripts/Common_ReceiptType.js"></script>
       <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script>
       <script type="text/javascript"src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>     

<script src="../scripts/SanctionProceeding_SinglePayeeFirm.js"
	type="text/javascript"> </script>


</head>
<%String s=request.getContextPath(); %>
<%System.out.println(s); %>
<body
	onload="LoadAccountingUnitID('LIST_ALL_UNITS'),initialLoad('<%=s %>');">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">General Sanction
		Proceedings (Single Payee-Firm)</font></div>
		</td>
	</tr>
</table>



<form name="frm_SanctionProceedingSinglePayeeFirm"
	id="frm_SanctionProceedingSinglePayeeFirm" method="POST"
	action="SanctionProceedingSinglePayeeFirm"><input type="hidden"
	name="cmbMas_SL_type" id="cmbMas_SL_type" value="7"
	onchange="doFunction('Load_MasterSL_Code',this.value);" />
<div class="tab-pane" id="tab-pane-1"><!-- 1st Tab General Starts -->
<div class="tab-page">
<h2 class="tab">General</h2>

<div align="center">
<table cellspacing="1" cellpadding="2" border="1" width="100%">

	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong>General</strong></div>
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
		<div align="left">Payment<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="radio" name="rdoPayment"
			id="rdoPayment" value="R" checked="checked"><label>Regular</label><input
			type="radio" name="rdoPayment" id="rdoPayment" value="A"><label>Advance</label></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Bill Major Type <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><select size="1" name="cboBillMajorType"
			id="cboBillMajorType" onChange="getBillMinorType('<%=s %>');">
			<option value="s">---Select---</option>
		</select></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Bill Minor Type <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><select size="1" name="cboBillMinorType"
			id="cboBillMinorType" onChange="getBillsubType('<%=s %>');">
			<option value="s">---Select---</option>
		</select></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Bill Sub Type <font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="cboBillSubType"
			id="cboBillSubType">
			<option value="s">---Select---</option>
		</select></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Payee Type<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="cboPayeeType"
			id="cboPayeeType" maxlength="18"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Ref.No <font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtRefNo"
			id="txtRefNo" maxlength="15" onKeyPress="return numbersonly1(event,this)"/></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Ref.Date<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtRefDate"
			id="txtRefDate" maxlength="10" size="11"
			onfocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);" />
		<img src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frm_SanctionProceedingSinglePayeeFirm.txtRefDate);"
			alt="Show Calendar"></img></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Sanctioning Authority<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><select size="1"
			name="cboSanctioningAuthority" id="cboSanctioningAuthority">
			<option value="s">---Select---</option>
		</select></div>
		</td>
	</tr>
	<tr class="table">
		<td width="40%">
		<div align="left">Sanctioned By<font color="#ff2121">*</font></div>
		</td>
		<td width="60%">
		<table align="left">
			<tr align="left">
				<td>
				<div align="left"><select size="1" name="cmbMas_SL_Code"
					id="cmbMas_SL_Code">


				</select></div>
				</td>
				<td>
				<div align="left" id="offlist_div_master" style="display: none">

				<img src="../../../../../images/c-lovi.gif" width="20" height="20"
					alt="OfficeList" onClick="jobpopup_master();"></img> <input
					type="text" name="txtOfficeID_mas" id="txtOfficeID_mas"
					maxlength="4" size="5" onBlur="mas_office(this.value);" /></div>
				<div align="left" id="emplist_div_master"><img
					src="../../../../../images/c-lovi.gif" width="20" height="20"
					alt="empList" onClick="employee_popup_master();"></img> <input
					type="text" name="txtEmpID_mas" id="txtEmpID_mas" maxlength="6"
					size="6" onBlur="mas_employee(this.value);" /></div>
				<input type="hidden" name="cmbSL_type" id="cmbSL_type" /> <input
					type="hidden" name="cmbSL_Code" id="cmbSL_Code" /></td>

			</tr>
		</table>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Estimate Sanction Number<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><select size="1"
			name="cboEstimateSanctionNumber" id="cboEstimateSanctionNumber" onChange="getEstimateSanctionDate('<%=s %>');">
			<option value="s">---Select---</option>
		</select></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Estimate Sanction Date <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text"
			name="txtEstimateSanctionDate" id="txtEstimateSanctionDate"
			maxlength="10" size="11" onFocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);" onBlur="call_date(this);" />
		<img src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frm_SanctionProceedingSinglePayeeFirm.txtEstimateSanctionDate,1);"
			alt="Show Calendar"></img></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Sanction Proceeding Date <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text"
			name="txtSanctionProceedingDate" id="txtSanctionProceedingDate"
			maxlength="10" size="11" onFocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);" onBlur="call_date(this);" />
		<img src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frm_SanctionProceedingSinglePayeeFirm.txtSanctionProceedingDate,1);"
			alt="Show Calendar"></img></div>
		</td>
	</tr>
<tr class="table">
                <td>
                    <div align="left">
                       AccountHeadCode & Desc
                    </div>
                </td>
                  <td>
                  <div align="left"><input type="text" name="txtAcc_HeadCode"
			id="txtAcc_HeadCode" maxlength="6"
			onkeypress="return numbersonly(event)" onChange="sixdigit();"
			onblur="doFunction('checkCode','null');" size="9" /> <img
			src="../../../../../images/c-lovi.gif" width="20" height="20"
			alt="AccountHeadList" onClick="AccHeadpopup();"></img> <input
			type="text" name="txtAcc_HeadDesc" readonly="readonly"
			id="txtAcc_HeadDesc" style="background-color: #ececec"
			maxlength="125" size="50" /></div></td>
               <div style="display:none">
                                        <select name="cmbSL_type" id="cmbSL_type" ></select>
                                        <select name="cmbSL_Code" id="cmbSL_Code"></select>
                                       </div>
</tr>
	<tr class="table">
		<td>
		<div align="left">Total Sanctioned Amount<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text"
			name="txtTotalSanctionedAmount" id="txtTotalSanctionedAmount"
			maxlength="10" size="11" onKeyPress="return numbersonly1(event,this)"
			onblur="calculateBudget('<%=s %>');"></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Budget Provided<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtBudgetProvided"
			id="txtBudgetProvided" maxlength="18" size="11"
			onkeypress="return numbersonly1(event,this)" readonly="true"></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Budget so far Spent<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtBudgetSpent"
			id="txtBudgetSpent" maxlength="18" size="11"
			onkeypress="return numbersonly1(event,this)" readonly="true"></div>
		</td>
	</tr>


	<tr class="table">
		<td>
		<div align="left">Balance Amount<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtBalanceAmount"
			id="txtBalanceAmount" maxlength="18" size="11"
			onkeypress="return numbersonly1(event,this)" readonly="true"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Particulars</div>
		</td>
		<td>
		<div align="left"><textarea name="mtxtParticulars"
			id="mtxtParticulars" cols="50" rows="4"></textarea></div>
		</td>
	</tr>

	<tr class="tdH">
		<td colspan="2">
		<div align="center"><input type="button" name="butSub"
			id="butSub" value="SUBMIT" onClick="saveFunc('<%=s %>');" />
		&nbsp;&nbsp;&nbsp; <input type="button" name="butCan" id="butCan"
			value="CANCEL" onClick="refresh();" /> &nbsp;&nbsp;&nbsp; <input
			type="button" name="btnCan" id="btnCan" value="EXIT"
			onClick="exitfun('<%=s %>');" /></div>
		</td>
	</tr>

</table>
</div>
</div>
<!-- 2st General Tab Ends --> <!-- 3rd Detail Tab Starts -->
<div class="tab-page" id="gd">
<h2 class="tab">Details</h2>

<div align="center">
<table cellspacing="1" cellpadding="2" border="1" width="100%">

	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong>Details</strong></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Invoice No <font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtInvoiceNo"
			id="txtInvoiceNo" maxlength="18" size="11" onKeyPress="return numbersonly1(event,this)"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Invoice Date<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtInvoiceDate"
			id="txtInvoiceDate" maxlength="18" size="11"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Particulars</div>
		</td>
		<td>
		<div align="left"><textarea name="mtxtParticulars1"
			id="mtxtParticulars1" cols="50" rows="4"></textarea></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">M-Book Ref.No <font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtmBookRefNo"
			id="txtmBookRefNo" maxlength="15" onKeyPress="return numbersonly1(event,this)"/></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Ref.Date<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtmBookRefDate"
			id="txtmBookRefDate" maxlength="10" size="11"
			onfocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);" onBlur="call_date(this);" />
		<img src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frm_SanctionProceeding-SinglePayeeFirm.txtmBookRefDate,1);"
			alt="Show Calendar"></img></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Agreement No & Date<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtAgreementNo"
			id="txtAgreementNo" maxlength="18" size="11" onKeyPress="return numbersonly1(event,this)"><input
			type="text" name="txtAgreementDate" id="txtAgreementDate"
			maxlength="18" size="11"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Supplement Agreement No & Date<font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text"
			name="txtSupplementAgreementNo" id="txtSupplementAgreementNo"
			maxlength="18" size="11" onKeyPress="return numbersonly1(event,this)"><input type="text"
			name="txtSupplementAgreementDate" id="txtSupplementAgreementDate"
			maxlength="18" size="11"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Invoice Amount<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtInvoiceAmount"
			id="txtInvoiceAmount" maxlength="18" size="11"
			onkeypress="return numbersonly1(event,this)"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Sanc.Amount<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtSancAmount"
			id="txtSancAmount" maxlength="18" size="11"
			onkeypress="return numbersonly1(event,this)"></div>
		</td>
	</tr>
	<tr class="tdH">
		<td colspan="2">
		<div align="center">.</div>
		</td>
	</tr>
</table>
</div>
</div>
<!-- 2rd Detail tab ends --></div>

</form>
</body>
</html>