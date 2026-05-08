<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>BillTokenRegisterEntry_WithoutProceeding</title>

<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />

  <script type="text/javascript"
            src="../../../../HR/HR1/OfficeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript"
            src="../../../../Library/scripts/checkDate.js"></script>
             <script type="text/javascript" src="../../../../Library/scripts/comJS.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>

<script type="text/javascript"
	src="../../../../Security/scripts/tabpane.js"></script>

<script type="text/javascript"
	src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script>
<script type="text/javascript"
	src=".../../../../ReceiptSystem/scripts/Common_ReceiptType.js"></script>
<script type="text/javascript"
	src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script>
<script type="text/javascript"
	src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>

<script src="../scripts/BillTokenRegisterEntry_WithoutProceeding.js"
	type="text/javascript"> </script>


</head>
<%String s=request.getContextPath(); %>
<%System.out.println(s); %>
<body
	onload="LoadAccountingUnitID('LIST_ALL_UNITS'),initialLoad('<%=s %>');">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Bills-Token Register Entry-Without Proceeding</font></div>
		</td>
	</tr>
</table>



<form name="frm_BillTokenRegisterEntry_WithoutProceeding"
	id="frm_BillTokenRegisterEntry_WithoutProceeding" method="POST"
	action="BillTokenRegisterEntry_WithoutProceeding"><input type="hidden"
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
		<div align="left">Bill Major Type <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><select size="1" name="cboBillMajorType"
			id="cboBillMajorType" onchange="getBillMinorType('<%=s %>');">
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
			id="cboBillMinorType" onchange="getBillsubType('<%=s %>');">
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
		<div align="left">Bill No <font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtBillNo"
			id="txtBillNo" maxlength="15"
			onkeypress="return numbersonly1(event,this)" readonly="true"/></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Bill Date<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtBillDate" maxlength="10" size="10"
                   id="txtBillDate" onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);" onblur="return checkdt1(this);"/>
                   <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillDate);"
                         alt="Show Calendar"></img></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Invoice (s) Received on Date<font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text"
			name="txtInvoiceReceivedDate" id="txtInvoiceReceivedDate"
			maxlength="10" size="11" onfocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);" /> <img
			src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceReceivedDate);"
			alt="Show Calendar"></img></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">No of Invoices<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtNoofInvoices"
			id="txtNoofInvoices" maxlength="15"
			onkeypress="return numbersonly1(event,this)" /></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Total Bill Amount<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtTotalBillAmount"
			id="txtTotalBillAmount" maxlength="15"
			onkeypress="return numbersonly1(event,this)" /></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">AccountHeadCode & Desc</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtAcc_HeadCode"
			id="txtAcc_HeadCode" maxlength="6"
			onkeypress="return numbersonly(event)" onchange="sixdigit();"
			onblur="doFunction('checkCode','null'),calculateBudget('<%=s %>');" size="9" /> <img
			src="../../../../../images/c-lovi.gif" width="20" height="20"
			alt="AccountHeadList" onclick="AccHeadpopup();"></img> <input
			type="text" name="txtAcc_HeadDesc" readonly="readonly"
			id="txtAcc_HeadDesc" style="background-color: #ececec"
			maxlength="125" size="50" /></div>
		</td>
		<div style="display: none"><select name="cmbSL_type"
			id="cmbSL_type"></select> <select name="cmbSL_Code" id="cmbSL_Code"></select>
		</div>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Payee Type<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtPayeeType"
			id="txtPayeeType" maxlength="15" /></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Payee Code<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtPayeeCode"
			id="txtPayeeCode" maxlength="15"
			onkeypress="return numbersonly1(event,this)" /></div>
		</td>
	</tr>
	<tr class="table">
		<td width="40%">
		<div align="left">Bill Processing Done By<font color="#ff2121">*</font></div>
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
					alt="OfficeList" onclick="jobpopup_master();"></img> <input
					type="text" name="txtOfficeID_mas" id="txtOfficeID_mas"
					maxlength="4" size="5" onblur="mas_office(this.value);" /></div>
				<div align="left" id="emplist_div_master"><img
					src="../../../../../images/c-lovi.gif" width="20" height="20"
					alt="empList" onclick="employee_popup_master();"></img> <input
					type="text" name="txtEmpID_mas" id="txtEmpID_mas" maxlength="6"
					size="6" onblur="mas_employee(this.value);"  onchange="getOffice('<%=s %>');"/></div>
				<input type="hidden" name="cmbSL_type" id="cmbSL_type" /> <input
					type="hidden" name="cmbSL_Code" id="cmbSL_Code" /></td>

			</tr>
		</table>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Office<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="cboOffice"
			id="cboOffice">
			<option value="s">---Select---</option>
		</select></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Budget Provision<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtBudgetProvision"
			id="txtBudgetProvision" maxlength="18" size="11"
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
		<div align="left">Ref.No <font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtRefNo"
			id="txtRefNo" maxlength="15"
			onkeypress="return numbersonly1(event,this)" /></div>
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
			onkeypress="return calins(event,this);" /> <img
			src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frm_BillTokenRegisterEntry_WithoutProceeding.txtRefDate);"
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
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><input type="button" name="onsubmit"
			value="ADD" id="onsubmit" onClick="saveFunc('<%=s%>');" /> <input
			type="button" name="ondelete" value="DELETE" id="ondelete"
			onClick="deleteeee('<%=s%>');" disabled="disabled" /> <input
			type="button" name="onupdate" value="EDIT" id="onupdate"
			onClick="update('<%=s%>');" disabled="disabled" /> <input
			type="button" name="onlist" value="LIST" id="onlist"
			onClick="forList('<%=s%>');" /> <input
			type="button" name="onrefresh" value="CLEAR ALL" id="onrefresh"
			onClick="ClearAll('<%=s%>');" /><input type="button"
			name="onexit" value="EXIT" id="onexit" onClick="exitfun('<%=s%>');" />
		</div>
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
		<div align="left">Invoice Entry Option<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="radio"
			name="rdoInvoiceEntryOption" id="rdoInvoiceEntryOption" value="Entry" onclick="IVno('<%=s %>');"><label>Entry</label><input
			type="radio" name="rdoInvoiceEntryOption" id="rdoInvoiceEntryOption"
			value="List" onclick="IVno('<%=s %>');"><label>Select From List</label></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">If entry enter Invoice No <font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtInvoiceNo"
			id="txtInvoiceNo" maxlength="18" size="11"
			onkeypress="return numbersonly1(event,this)"><select size="1" name="txtIfSelectfromList"
			id="txtIfSelectfromList" onchange="InvoiceDetails('<%=s %>');">
			<option value="s">---Select---</option>
		</select></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Invoice Date<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtInvoiceDate"
			id="txtInvoiceDate" maxlength="10" size="11"
			onfocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);" /> <img
			src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceDate);"
			alt="Show Calendar"></img></div>
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
		<div align="left">Particulars</div>
		</td>
		<td>
		<div align="left"><textarea name="mtxtParticulars1"
			id="mtxtParticulars1" cols="50" rows="4"></textarea></div>
		</td>
	</tr>
</table>
</div>
</div>
</form>
</body>
</html>