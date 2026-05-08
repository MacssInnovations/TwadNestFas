<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bills_Token_Register_without_SP Edit</title>

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
          <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script> 

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

<script src="../scripts/Bills_Token_Register_without_SP.js"
	type="text/javascript"> </script>


</head>
<%String s=request.getContextPath(); %>
<%System.out.println(s); %>
<body
	onload="LoadAccountingUnitID('LIST_ALL_UNITS'),initialLoad('<%=s%>'),loadPayeeType('<%=s %>');">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Bills-Token Register Edit-Without System generated Sanction Proceeding</font></div>
		</td>
	</tr>
</table>



<form name="frm_BillTokenRegisterEntry_WithoutProceeding"
	id="frm_BillTokenRegisterEntry_WithoutProceeding" method="POST"
	action="BillTokenRegisterEntry_WithoutProceeding">
	<!--<input type="hidden" id="hid_sanc" name="hid_sanc" ></input>  
	--><input type="hidden"
	name="cmbMas_SL_type" id="cmbMas_SL_type" value="7"
	onchange="doFunction('Load_MasterSL_Code',this.value);" />
<div align="center">
<table cellspacing="1" cellpadding="2" border="1" width="100%">	
	<tr class="table">
      <td><div align="left">Accounting Unit Code <font color="#ff2121">*</font> </div></td>
	  <td><div align="left">
	    <select size="1" name="cmbAcc_UnitCode"
			id="cmbAcc_UnitCode" onChange="common_LoadOffice(this.value);">
          </select>
	    </div></td>
	  </tr>
	<tr class="table">
		<td>
		<div align="left">Accounting For Office Code <font
			color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><select size="1" name="cmbOffice_code"
			id="cmbOffice_code" />

		</select></div>		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Bill Major Type <font color="#ff2121">*</font>		</div>		</td>
		<td>
		<div align="left"><select size="1" name="cboBillMajorType"
			id="cboBillMajorType" onChange="getBillMinorType('<%=s %>');">
			<option value="s">---Select---</option>
		</select></div>		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Bill Minor Type <font color="#ff2121">*</font>		</div>		</td>
		<td>
		<div align="left"> <select size="1" name="cboBillMinorType"
			id="cboBillMinorType" onChange="getBillsubType('<%=s %>');checkinvoicee(),loadAccHead();">
			 
			<option value="s">---Select---</option>
		</select></div>		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Bill Sub Type <font color="#ff2121">*</font></div>	
		 <input type="hidden" id="bill_sub_type_applica" name="bill_sub_type_applica" ></input>	</td>
		<td>
		<div align="left"><select size="1" name="cboBillSubType"
			id="cboBillSubType" onchange="getBillNo('<%=s%>');checkinvoicee();" onblur="checkinvoicee(),loadAccHead()">
			<option value="s">---Select---</option>
		</select></div>		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Bill No <font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left">
			
				<select size="1" name="txtBillNo"
			id="txtBillNo" onchange="getBillDetails('<%=s%>');" >
			<option value="s">---Select---</option>
		</select>
			</div></td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Bill Date<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><input type="text" name="txtBillDate" maxlength="10" size="10" onblur="checksan();"
                   id="txtBillDate" onFocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);" onBlur="return checkdt1(this);"/>
                   <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frm_BillTokenRegisterEntry_WithoutProceeding.txtBillDate);"
                         alt="Show Calendar"></img></div>		</td>
	</tr>
	<tr class="table">
      <td><div align="left">Manual Proceeding No <font color="#ff2121">*</font></div></td>
	  <td><div align="left">
	    <input type="text" name="txtManualProceedingNo"
			id="txtManualProceedingNo" maxlength="15"
			/>
	    </div></td>
	  </tr>
	<tr class="table">
      <td><div align="left">Manual Proceeding Date<font color="#ff2121">*</font></div></td>
	  <td><div align="left">
	    <input type="text" name="txtManualProceedingDate" maxlength="10" size="10"
                   id="txtManualProceedingDate" onFocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);" onBlur="return checkdt1(this);"/>
          <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frm_BillTokenRegisterEntry_WithoutProceeding.txtManualProceedingDate);"
                         alt="Show Calendar"></img></div></td>
	  </tr>
	  	<tr class="table">
		<td>
		<div align="left">No of Invoices<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><input type="text" name="txtNoofInvoices"
			id="txtNoofInvoices" maxlength="15"
			onkeypress="return numbersonly1(event,this)" /></div>	
			
			<input type="hidden" name="checkFlag" id="checkFlag"/>	</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Date of Last Invoice Received<font
			color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><input type="text"
			name="txtInvoiceReceivedDate" id="txtInvoiceReceivedDate"
			maxlength="10" size="11" onFocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);" /> <img
			src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frm_BillTokenRegisterEntry_WithoutProceeding.txtInvoiceReceivedDate);"
			alt="Show Calendar"></img></div>		</td>
	</tr>

	<tr class="table">
      <td><div align="left">Is MTC 70 Register Entry Required ? <font color="#ff2121">*</font></div></td>
	  <td><div align="left">
	    <label>
	    <input name="rdoMTC_70_Register" id="rdoMTC_70_Register" type="radio" value="Y" checked="checked">
	    Yes	    </label>
	    <input name="rdoMTC_70_Register" id="rdoMTC_70_Register" type="radio" value="N">
	   No</div></td>
	  </tr>
	<tr class="table">
		<td>
		<div align="left">Total Sanction Amount<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><input type="text" name="txtTotalSanctionAmount"
			id="txtTotalSanctionAmount" maxlength="15"
			onkeypress="return numbersonly1(event,this)" /></div>		</td>
	</tr>
	<tr class="table">
      <td><div align="left">Deducted  Amount<font color="#ff2121">*</font></div></td>
	  <td><div align="left">
	    <input type="text" name="txtDeductedAmount"
			id="txtDeductedAmount" maxlength="15"
			onkeypress="return numbersonly1(event,this)" onblur="calbillAmt();"/>
	    </div></td>
	  </tr>
	  <tr class="table">
		<td>
		<div align="left">Total Bill Amount<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><input type="text" name="txtTotalBillAmount"
			id="txtTotalBillAmount" maxlength="15"
			onkeypress="return numbersonly1(event,this)" readonly="readonly" /></div>		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">AccountHeadCode & Desc <font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left">
		<select name="txtAcc_HeadCode" id="txtAcc_HeadCode" onblur="calculateBudget('<%=s %>'),loadAccDesc();">
				<option value="">Select</option>
				</select><!--
		
		<input type="text" name="txtAcc_HeadCode"
			id="txtAcc_HeadCode" maxlength="6"
			onkeypress="return numbersonly(event)" onChange="sixdigit();"
			onblur="doFunction('checkCode','null'),calculateBudget('<%=s %>');" size="9" /> <img
			src="../../../../../images/c-lovi.gif" width="20" height="20"
			alt="AccountHeadList" onClick="AccHeadpopup();"></img>--> <input
			type="text" name="txtAcc_HeadDesc" readonly="readonly"
			id="txtAcc_HeadDesc" style="background-color: #ececec"
			maxlength="125" size="50" onblur="calculateBudget('<%=s %>')" /></div>		</td>
		<!--<div style="display: none"> <select name="cmbSL_type"
			id="cmbSL_type"></select><select name="cmbSL_Code" id="cmbSL_Code"></select>
		</div>
	--></tr>
	<tr class="table">
		<td>
		<div align="left">Payee Type<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left">
		<select name="txtPayeeType" id="txtPayeeType" onblur="loadPayeeCode_Desc();"><!-- < onblur="loadPayeeCode_Desc();"> -->
				<option value="">Select</option>
				</select>
		<!--<input type="text" name="txtPayeeType"
			id="txtPayeeType" maxlength="15" />-->	</div>	
			</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Payee Code<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left" id="one">
		<select name="txtPayeeCodeLoad" id="txtPayeeCodeLoad" onblur="loadPayyCp();" onchange="loadPayyCp();"> 
				<option value="">--Select--</option>
				</select>
		
		<input type="text" name="txtPayeeCode"
			id="txtPayeeCode" maxlength="15" size="7"  onkeypress="return numbersonly1(event,this)" onchange="callpd()" onblur="callpd()"/>
			<select name="payname_desig" id="payname_desig">
			
			</select>
		</div>	<div align="left" id="two" style="display:none">
		<select name="txtPayeeCodeLoad_two" id="txtPayeeCodeLoad_two" onblur="loadPayyCp();" onchange="loadPayyCp();">
				<option value="">Select</option>
				</select>
		</div>	</td>
	</tr>
	<tr class="table">
		<td width="40%">
		<div align="left">Bill Processing Done By<font color="#ff2121">*</font></div>		</td>
		<td width="60%">
		<table align="left">
			<tr align="left">
				<td>
				<div align="left"><select size="1" name="cmbMas_SL_Code"
					id="cmbMas_SL_Code">


				</select></div>				</td>
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
					size="6" onBlur="mas_employee(this.value);"  onchange="getOffice('<%=s %>');"/></div>
				<input type="hidden" name="cmbSL_type" id="cmbSL_type" /> <input
					type="hidden" name="cmbSL_Code" id="cmbSL_Code" /></td>
			</tr>
		</table>		</td>
	</tr>
	<!-- 
	<tr class="table">
      <td><div align="left">Name &amp; Designation <font color="#ff2121">*</font></div></td>
	  <td><div align="left">
	    <input type="text" name="txtName_Designation"
			id="txtName_Designation" size="70"/>
	    </div></td>
	  </tr>
	 -->
	
	<!--<tr class="table">
		<td>
		<div align="left">Office<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><select size="1" name="cboOffice"
			id="cboOffice">
			<option value="s">---Select---</option>
		</select></div>		</td>
	</tr>
	--><tr class="table">
		<td>
		<div align="left">Budget Provision<font color="#ff2121">*</font>		</div>		</td>
		<td>
		<div align="left"><input type="text" name="txtBudgetProvision"
			id="txtBudgetProvision" maxlength="18" size="11"
			onkeypress="return numbersonly1(event,this)" readonly="readonly"></div>		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Budget so far Spent<font color="#ff2121">*</font>		</div>		</td>
		<td>
		<div align="left"><input type="text" name="txtBudgetSpent"
			id="txtBudgetSpent" maxlength="18" size="11"
			onkeypress="return numbersonly1(event,this)" readonly="true"></div>		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Ref.No <font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><input type="text" name="txtRefNo"
			id="txtRefNo" maxlength="15"
			onkeypress="return numbersonly1(event,this)" /></div>		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Ref.Date<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><input type="text" name="txtRefDate"
			id="txtRefDate" maxlength="10" size="11"
			onfocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);" /> <img
			src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frm_BillTokenRegisterEntry_WithoutProceeding.txtRefDate);"
			alt="Show Calendar"></img></div>		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Remarks</div>		</td>
		<td>
		<div align="left"><textarea name="mtxtRemarks" id="mtxtRemarks"
			cols="50" rows="4"></textarea></div>		</td>
	</tr>
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><input
			type="button" name="onupdate" value="EDIT" id="onupdate"
			onClick="Edit_fun('<%=s%>');" /> <input
			type="button" name="onrefresh" value="CLEAR ALL" id="onrefresh"
			onClick="refresh();" /><input type="button"
			name="onexit" value="EXIT" id="onexit" onClick="exitfun('<%=s%>');" />
		</div>		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>