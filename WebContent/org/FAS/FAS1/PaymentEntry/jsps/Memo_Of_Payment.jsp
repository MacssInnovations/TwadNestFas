<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<%@page import="java.sql.PreparedStatement"%>
<%@page import="java.sql.Connection"%>
<%@page import="java.sql.ResultSet"%>
<%@ page import="java.sql.Date" %>

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Memo of Payment</title>
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />

<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
	 <script type="text/javascript" src="../../../../Library/scripts/comJS.js"></script>
<script type="text/javascript"
	src="../../../../../org/Library/scripts/checkDate.js"></script>
<script src="../scripts/Memo_Of_Payment.js" type="text/javascript"> </script>

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


<script type="text/javascript" language="javascript">
    function loadyear()
    {
	        var today= new Date(); 
	        var day=today.getDate();
	        var month=today.getMonth();
	        month=month+1;
	        var year=today.getYear();
	        if(year < 1900) year += 1900;	
	        document.frm_Memo_Of_Payment.cboCashBook_Year.value=year;
	        document.frm_Memo_Of_Payment.hid_mon.value=month;
	        document.frm_Memo_Of_Payment.hid_year.value=year;
	        
    }
    function chkMonth(mn){
    	if(document.frm_Memo_Of_Payment.cboCashBook_Year.value > document.frm_Memo_Of_Payment.hid_year.value){
    		alert('Enter Correct Year ');	
    	}else if(document.frm_Memo_Of_Payment.cboCashBook_Year.value == document.frm_Memo_Of_Payment.hid_year.value){
    	if(parseInt(mn)>document.frm_Memo_Of_Payment.hid_mon.value){
    		alert('Enter Correct Month ');
    	}
    	}
    }
    </script>
</head>
<%
	String s = request.getContextPath();
%>
 
<body
	onload="LoadAccountingUnitID('LIST_ALL_UNITS'),loadyear()">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Memo Of Payment</font></div>
		</td>
	</tr>
</table>



<form name="frm_Memo_Of_Payment" id="frm_Memo_Of_Payment" method="POST"
	action="MemoOfPayment">
	<input type="hidden" id="hid_id" name="hid_id" value="">
	<input type="hidden" id="hid_mon" name="hid_mon" value="">
	<input type="hidden" id="hid_year" name="hid_year" value="">
<div class="tab-pane" id="tab-pane-1"><!-- 1st Tab General Starts -->
<div class="tab-page">
<h2 class="tab">General</h2>
<input type="hidden" name="slNo" id="slNo">
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
			id="cmbAcc_UnitCode" onChange="common_LoadOffice(this.value);">

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
		<div align="left">Computer Sanction Proceeding With/Without<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left">
		<input type="radio" name="sancidwith" id="sancidwith" value="Y" checked="checked" onclick="clearone(),blockSanNo();">
		<label>With Sanction Proceeding</label>
		<input type="radio" name="sancidwith" id="sancidwith" value="N" onclick="clearone(),blockSanNo();">
		<label>Without Sanction Proceeding</label>
		<input type="radio" name="sancidwith" id="sancidwith" value="NG" onclick="clearone(),blockSanNo();">
		<label>Without Sanction Proceeding for GPF</label>
		</div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Bill Year<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="cboCashBook_Year" size="10" onchange="refresh_year();"
			id="cboCashBook_Year" onKeyPress="return numbersonly1(event,this)"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Bill Month<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="cboCashBook_Month"
			id="cboCashBook_Month" onblur="chkMonth(this);" onChange="forBillNo('<%=s%>');">
			<option value="">---Select---</option>
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
		<div align="left">Bill No <font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="cboBillNo"
			id="cboBillNo" onChange="getDetails('<%=s%>');">
			<option value="s">---Select---</option>
		</select></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Bill Major Type<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left">
		<input type="hidden" name="txtBillMajorType" class="light" id="txtBillMajorType" maxlength="18" size="35">
		<input type="text" class="light" name="majorDesc" id="majorDesc" maxlength="18" readonly="readonly" size="35">
		</div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Bill Date<font color="#ff2121">*</font></div>
		</td>
		<td> 
		<div align="left"><input type="text" name="txtBillDate" class="light" onblur="checksan();" readonly="readonly"
		id="txtBillDate" maxlength="18" size="11" onFocus="javascript:vDateType='3';"
		                           onkeypress="return calins(event,this);" onBlur="return checkdt1(this);"/>
		</div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Sanction Proceeding Order No<font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left">
		 <div id="head_div1" name="head_div1" >
		 <input type="text" readonly="readonly" class="light" name="txtSanctionProceedingNo" id="txtSanctionProceedingNo" maxlength="25" size="20"></div></div>
		<input type="text"  readonly="readonly" class="light" name="txtSanctionProceedingid" id="txtSanctionProceedingid" maxlength="18" size="30">
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Sanction Proceeding Date<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" class="light" onblur="checksan();" 
			name="txtSanctionProceedingDate" id="txtSanctionProceedingDate" readonly="readonly"
			maxlength="18" size="11" onFocus="javascript:vDateType='3';"
		                           onkeypress="return calins(event,this);" onBlur="return checkdt1(this);"/></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Sanctioned Amount<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtSanctionedAmount" class="light" readonly="readonly"
			id="txtSanctionedAmount" maxlength="18" size="11" onKeyPress="return numbersonly1(event,this)"></div>
		</td>
	<tr class="table">
		<td>
		<div align="left">Credit Account Head<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtDebitAccountHead" class="light"  readonly="readonly" id="txtDebitAccountHead" maxlength="15" size="35" /></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Sub Ledger Type<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><select size="1" name="cmbMas_SL_type"
			id="cmbMas_SL_type">
			
			<option value="s">---Select---</option>
		</select></div>
		</td>
	</tr>
	<tr class="table">
		<td width="40%">
		<div align="left">Sub-Ledger Code</div>
		</td>
		<td width="60%">
		<table align="left">
			<tr align="left">
				<td>
				<div align="left"><select size="1" name="cmbMas_SL_Code"
					id="cmbMas_SL_Code" onChange="loadName_Mas(this);">

					<option value="s">--Select Code--</option>
				</select></div>
				</td>
				<td>
				<div align="left" id="offlist_div_master" style="display: none">
				<img src="../../../../../images/c-lovi.gif" width="20" height="20"
					alt="OfficeList" onClick="jobpopup_master();"></img> <input
					type="text" name="txtOfficeID_mas" id="txtOfficeID_mas"
					maxlength="4" size="5" onBlur="mas_office(this.value);" /></div>
				<div align="left" id="emplist_div_master" style="display: none">
				<img src="../../../../../images/c-lovi.gif" width="20" height="20"
					alt="empList" onClick="employee_popup_master();"></img> <input
					type="text" name="txtEmpID_mas" id="txtEmpID_mas" maxlength="5"
					size="5" onBlur="mas_employee(this.value);" /></div>
				</td>

			</tr>
		</table>
		</td>
	</tr>


	<tr class="table">
		<td>
		<div align="left">Bill Amount<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtDebitAmount" class="light" readonly="readonly"
			id="txtDebitAmount" maxlength="10" size="11" onKeyPress="return numbersonly1(event,this)"/></div>
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
		<div align="left">Account Head Code <font color="#ff2121">*</font>
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
			maxlength="125" size="50" /></div>
		</td>
	</tr>
	<tr class="table">
		<td width="40%">
		<div align="left">Sub-Ledger Type <font color="#ff2121">*</font>
		</div>
		</td>
		<td width="60%">
		<div align="left"><select size="1" name="cmbSL_type"
			id="cmbSL_type" onChange="doFunction('Load_SL_Code',this.value);">
			<option value="s">--Select Type--</option>

		</select></div>
		</td>
	</tr>
	<tr class="table">
		<td width="40%">
		<div align="left">Sub-Ledger Code <font color="#ff2121">*</font>
		</div>
		</td>
		<td width="60%">
		<table align="left">
			<tr align="left">
				<td>
				<div align="left"><select size="1" name="cmbSL_Code"
					id="cmbSL_Code">

					<option value="s">--Select Code--</option>
				</select></div>
				</td>
				<td>
				<div align="left" id="offlist_div_trans" style="display: none">
				<img src="../../../../../images/c-lovi.gif" width="20" height="20"
					alt="OfficeList" onClick="jobpopup_trans();"></img> <input
					type="text" name="txtOfficeID_trs" id="txtOfficeID_trs"
					maxlength="4" size="5" onBlur="trs_office(this.value);" /></div>
				<div align="left" id="emplist_div_trans" style="display: none">
				<img src="../../../../../images/c-lovi.gif" width="20" height="20"
					alt="empList" onClick="employee_popup_trans();"></img> <input
					type="text" name="txtEmpID_trs" id="txtEmpID_trs" maxlength="5"
					size="5" onBlur="trs_employee(this.value);" /></div>
				</td>

			</tr>
		</table>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Invoice No <font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtInvoiceNo"
			id="txtInvoiceNo" maxlength="18" size="11"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Invoice Date<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtInvoiceDate" onblur="return call_date(this);"  onfocus="javascript:vDateType='3';"
			id="txtInvoiceDate" maxlength="18" size="11"   onkeypress="return calins(event,this);"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Invoice Amount<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtInvoiceAmount"
			id="txtInvoiceAmount" maxlength="18" size="11" onKeyPress="return numbersonly1(event,this)"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Agreement No <font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtAgreementNo"
			id="txtAgreementNo" maxlength="18" size="11"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Agreement Date<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtAgreementDate" onkeypress="return calins(event,this);" onblur="return call_date(this);"  onfocus="javascript:vDateType='3';"
			id="txtAgreementDate" maxlength="18" size="11"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Agreement Amount<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtAgreementAmount"
			id="txtAgreementAmount" maxlength="18" size="11" onKeyPress="return numbersonly1(event,this)"></div>
		</td>
	</tr>
	
	<tr class="table">
		<td>
		<div align="left">CR DR Indicator<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" class="light" name="txtCRDRIndicator" readonly
			id="txtCRDRIndicator" maxlength="18" size="11" value="CREDIT" /></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Amount<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtAmount"
			id="txtAmount" maxlength="18" size="11" onKeyPress="return numbersonly1(event,this)"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">If First Party<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="radio" name="rdoIfFirstParty" id="rdoIfFirstParty" value="Y" checked="checked" onclick="changedr();"><label>Yes</label><input
			type="radio" name="rdoIfFirstParty" id="rdoIfFirstParty" value="N" onclick="changecr();"><label>No</label></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Payee Type<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtPayeeType"
			id="txtPayeeType" maxlength="18" size="11"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Payee Code<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtPayeeCode"
			id="txtPayeeCode" size="11" onKeyPress="return numbersonly1(event,this)"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Remarks</div>
		</td>
		<td>
		<div align="left"><textarea name="mtxtRemarks1"
			id="mtxtRemarks1" cols="50" rows="4"></textarea></div>
		</td>
	</tr>
	<tr class="tdTitle">
		<td colspan="2" height="23">
		<div align="center" id="gridbtnid" name="gridbtnid"><input type="button" name="onsubmit"
			value="ADD" id="onsubmit" onClick="add('<%=s%>');" /> <input
			type="button" name="ondelete" value="DELETE" id="ondelete"
			onClick="deletee('<%=s%>');" disabled="disabled" /> <input
			type="button" name="onupdate" value="UPDATE" id="onupdate"
			onClick="update_new();" disabled="disabled" /> 
			<input type="button" name="onrefresh" value="CLEAR ALL" id="onrefresh"
			onClick="refresh2();" /> <input type="button" name="onexit"
			value="EXIT" id="onexit" onClick="exitfun();" /></div>
		</td>
	</tr>
</table>
</div>
<div id="grid" style="display: block">
<table id="Existing" cellspacing="3" cellpadding="2" border="1"
	width="100%">
	<tr class="table">
		<th>Select</th>
		<th>A/c Head Code</th>
		<th>Sub Ledger Type</th>
		<th>Sub Ledger Code</th>
		<th>Invoice No</th>
		<th>Invoice Date</th>
		<th>Invoice Amount</th>
		<th>Agreement No</th>
		<th>Agreement Date</th>
		<th>Agreement Amount</th>
		
		<th>CR-DR Indicator</th>
		<th>Amount</th>
		<th>If First Party</th>
		
		<th>Payee Type</th>
		<th>Payee Code</th>
		<th>Remarks</th>
	</tr>
	<tbody id="tblList" class="table" align="left">
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
		<input type="button" name="onsubmit1" value="SUBMIT" id="onsubmit1" onClick="addMst('<%=s%>');" /> &nbsp;&nbsp;&nbsp;
		 <input type="button" name="butCan" id="butCan" value="CANCEL" onClick="refresh();" />	&nbsp;&nbsp;&nbsp; 
		 <input type="button" name="butCan" id="butCan" value="EXIT" onClick="exitfun();" /></div>
		</td>
          </tr>
        </table>
      </div>

</form>

</body>
</html>