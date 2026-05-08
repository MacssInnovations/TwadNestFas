<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>MTC 70 Register Entry</title>

<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />

<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
<script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
     <script type="text/javascript" src="../../../../Library/scripts/comJS.js"></script>
<script src="../scripts/MTC_70_Register_Entry.js" type="text/javascript"> </script>

 <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>

<script type="text/javascript"
	src="../../../../Security/scripts/tabpane.js"></script>
	
  <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script> 

    <script type="text/javascript" language="javascript">
    function loadyear_month()
    {
	        var today= new Date(); 
	        var day=today.getDate();
	        var month=today.getMonth();
	        month=month+1;
	        var year=today.getYear();
	        if(year < 1900) year += 1900;	
	        document.frm_MTC_70_Register_Entry.cboCashBook_Year.value=year
	       // document.frm_MTC_70_Register_Entry.cboCashBook_Month.value=month;
        
    }
    </script>
<!-- to avoid future date the above script used-->
</head>
<%String s=request.getContextPath(); %>
<%System.out.println(s); %>
<body onload="LoadAccountingUnitID('LIST_ALL_UNITS'),loadyear_month(),initialLoad('<%=s %>');">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">MTC 70 Register Entry</font>
		</div>
		</td>
	</tr>
</table>



<form name="frm_MTC_70_Register_Entry" id="frm_MTC_70_Register_Entry"
	method="POST" action="MTC70Register_Entry">
<input type="hidden" name="cmbMas_SL_type" id="cmbMas_SL_type" value="7" onchange="doFunction('Load_MasterSL_Code',this.value);"/>
<div class="tab-pane" id="tab-pane-1"><!-- 1st Tab General Starts -->
<div class="tab-page">
<h2 class="tab">General</h2>

<div align="center">
<table cellspacing="1" cellpadding="2" border="1" width="100%">

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
		<div align="left"><select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);">

		</select></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Accounting For Office Code <font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="cmbOffice_code" id="cmbOffice_code" >

		</select></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">CashBook Year<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="cboCashBook_Year"
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
			id="cboCashBook_Month" onChange="forPassOrderNo('<%=s %>');">
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
<input type="hidden" name="txtMTCRegisterNO"
			id="txtMTCRegisterNO" >
	<tr class="table">
		<td>
		<div align="left">MTC Entry Date <font color="#ff2121">*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtMTCEntryDate"
			id="txtMTCEntryDate" tabindex="4" maxlength="10" size="11"
			onfocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);" onblur="call_date(this);" />
		<img src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frm_MTC_70_Register_Entry.txtMTCEntryDate,1);"
			alt="Show Calendar"></img></div>
		</td>
	</tr>

<tr class="table">
                <td width="40%">
                  <div align="left">EnteredBy<font color="#ff2121">*</font> </div>
                </td>
              <td width="60%">
            <table align="left">
             <tr align="left">
             <td>
                  <div align="left">
                        <select size="1" name="cmbMas_SL_Code" id="cmbMas_SL_Code">
                                
                          
                        </select>
                        
                  </div>
              </td>
              <td>
                  <div align="left" id="offlist_div_master"  style="display:none">
                            
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList" onclick="jobpopup_master();"></img>
                            <input type="text" name="txtOfficeID_mas" id="txtOfficeID_mas" maxlength="4" size="5"  onblur="mas_office(this.value);" />
                          </div>
                   <div align="left" id="emplist_div_master"  >
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="employee_popup_master();"></img>
                            <input type="text" name="txtEmpID_mas" id="txtEmpID_mas" maxlength="6" size="6"  onblur="mas_employee(this.value);" />
                          </div>
                 <input type="hidden" name="cmbSL_type" id="cmbSL_type"/>
                  <input type="hidden" name="cmbSL_Code" id="cmbSL_Code"/>
               </td>
             
            </tr></table></td></tr>

	<tr class="table">
		<td>
		<div align="left">Pass Order No <font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="cboPassOrderNo"
			id="cboPassOrderNo" onChange="forPassOrderDate('<%=s %>');">
<option value="s">---Select---</option>
		</select></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Pass Order Date <font color="#ff2121" >*</font>
		</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtPassOrderDate"
			id="txtPassOrderDate" maxlength="10" /></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Ref.No <font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtRefNo"
			id="txtRefNo" maxlength="15" onkeypress="return numbersonly1(event,this)"/></div>
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
			onkeypress="return calins(event,this);" onblur="call_date(this);" />
		<img src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frm_MTC_70_Register_Entry.txtRefDate,1);"
			alt="Show Calendar"></img></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Approved By</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtApprovedBy"
			id="txtApprovedBy" onkeypress="return numbersonly1(event,this)"></div>
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
			id="butSub" value="SUBMIT" onClick="saveFunc('<%=s %>');" /> &nbsp;&nbsp;&nbsp; 
			
			<input type="button"
			name="butCan" id="butCan" value="CANCEL" onclick="refresh();" />
		&nbsp;&nbsp;&nbsp; 
		
		<input type="button" name="btnCan" id="btnCan"
			value="EXIT" onClick="exitfun('<%=s %>');" /></div>
		</td>
	</tr>

</table>
</div>
</div>
<!-- 2st General Tab Ends --> <!-- 3rd Detail Tab Starts -->
<input type="hidden" name="h1" id="h1" value="h1"></input>
<input type="hidden" name="h2" id="h2" value="h2"></input>
<input type="hidden" name="h3" id="h3" value="h3"></input>
<input type="hidden" name="h4" id="h4" value="h4"></input>
<input type="hidden" name="h5" id="h5" value="h5"></input>
<input type="hidden" name="h6" id="h6" value="h6"></input>

<div class="tab-page" id="gd">
<h2 class="tab">Details</h2>

<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr>
		<td colspan="2">
		<div id="ProceedingDetails">

<table id="Existing" cellspacing="1" cellpadding="2" border="1"
	width="100%">
				<tr class="tdTitle">
				<td colspan="8">
				<div align="left"><strong> Proceeding Details</strong></div>
				</td>
			</tr>
			
	<tr class="table">
				<th>
				<div align="center">Proceeding Order No</div>
				</th>

				<th>
				<div align="center">Proceeding Order Date</div>
				</th>

				<th>
				<div align="center">Bill No</div>
				</th>

				<th>
				<div align="center">Bill Date</div>
				</th>
				
				<th>
				<div align="center">Bill Amount</div>
				</th>
				
				<th>
				<div align="center">Remarks</div>
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
</div>
<!-- 2rd Detail tab ends --></div>

</form>
</body>
</html>