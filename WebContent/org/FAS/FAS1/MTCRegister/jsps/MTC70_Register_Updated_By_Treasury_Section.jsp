<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>Insert title here</title>
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
<script src="../scripts/MTC70Register_Updated_By_Treasury_Section.js"
	type="text/javascript"> </script>
	
  <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script> 

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
	
</head>

<%String s=request.getContextPath(); %>
<%System.out.println(s); %>

<body onLoad="LoadAccountingUnitID('LIST_ALL_UNITS'),initialLoads('<%=s %>');">

<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">MTC70Register Updated
		By Treasury Section</font></div>
		</td>
	</tr>
</table>
<form name="frm_MTC70Register_Updated_By_Treasury_Section"
	id="frm_MTC70Register_Updated_By_Treasury_Section" method="POST"
	action="MTC70Register_Updated_By_Treasury_Section">

<input type="hidden" name="cmbMas_SL_type" id="cmbMas_SL_type" value="7" onChange="doFunction('Load_MasterSL_Code',this.value);"/>

<div align="center">
<table cellspacing="1" cellpadding="2" border="1" width="100%">
	<tr class="table">
		<td>
		<div align="left">Accounting Unit Code <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><select size="1" name="cmbAcc_UnitCode"
			id="cmbAcc_UnitCode" tabindex="1"  onChange="common_LoadOffice(this.value);">

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
		<div align="left">MTC Register No <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><select size="1" name="MTCRegisterNo"
			id="MTCRegisterNo" onChange="getMTCRegisterDate('<%=s %>');">
<option value="s">---Select---</option>
		</select></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">MTC Entry Date <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtMTCEntryDate"
			id="txtMTCEntryDate" maxlength="18" size="11" readonly="true"></div>
		</td>
	</tr>
<tr class="table">
                <td width="40%">
                  <div align="left">Received By<font color="#ff2121">*</font> </div>
                </td>
              <td width="60%">
            <table align="left">
             <tr align="left">
             <td>
                  <div align="left">
                        <select size="1" name="cmbMas_SL_Code" id="cmbMas_SL_Code">
                                
                          <option value="">--Select Code--</option>
                        </select>
                        
                  </div>
              </td>
              <td>
                  <div align="left" id="offlist_div_master"  style="display:none">
                            
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList" onClick="jobpopup_master();"></img>
                            <input type="text" name="txtOfficeID_mas" id="txtOfficeID_mas" maxlength="4" size="5"  onblur="mas_office(this.value);" />
                          </div>
                   <div align="left" id="emplist_div_master"  >
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onClick="employee_popup_master();"></img>
                            <input type="text" name="txtEmpID_mas" id="txtEmpID_mas" maxlength="6" size="6"  onblur="mas_employee(this.value);" />
                          </div>
                 <input type="hidden" name="cmbSL_type" id="cmbSL_type"/>
                  <input type="hidden" name="cmbSL_Code" id="cmbSL_Code"/>
               </td>
             
            </tr></table></td></tr>

	<tr class="table">
		<td>
		<div align="left">ReceivedOn<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="ReceivedOn"
			id="ReceivedOn" maxlength="10" size="11"
			onfocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);" onBlur="call_date(this);" />
		<img src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frm_MTC70Register_Updated_By_Treasury_Section.ReceivedOn,1);"
			alt="Show Calendar"></img></div>
		</td>
	</tr>
		<tr class="table">
		<td>
		<div align="left">Updated On<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtUpdatedOn"
			id="txtUpdatedOn" maxlength="10" size="11"
			onfocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);" onBlur="call_date(this);" />
		<img src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frm_MTC70Register_Updated_By_Treasury_Section.txtUpdatedOn,1);"
			alt="Show Calendar"></img></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Updated By</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtUpdatedBy"
			id="txtUpdatedBy"></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Sent to Pre-Audit on<font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="SenttoPreAuditon"
			id="SenttoPreAuditon" maxlength="10" size="11"
			onfocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);" onBlur="call_date(this);" />
		<img src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frm_MTC70Register_Updated_By_Treasury_Section.SenttoPreAuditon,1);"
			alt="Show Calendar"></img></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Remarks</div>
		</td>
		<td>
		<div align="left"><textarea name="mtxtRemarks" id="mtxtRemarks"
			cols="50" tabindex="11" rows="4"></textarea></div>
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
</form>
</body>
</html>