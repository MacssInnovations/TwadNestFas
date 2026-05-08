<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>MTC70_Register_Updated_By_Treasury_Section1</title>
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
<script src="../scripts/MTC70_Register_Updated_By_Treasury_Section1.js"
	type="text/javascript"> </script>
	
  <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script> 

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
       
       if(day<=9 && day>=1)
       day="0"+day;
       if(month<=9 && month>=1)
       month="0"+month;
       var year=today.getYear();
       if(year < 1900) year += 1900;
       var monthArray =new Array("January", "February", "March", 
                 "April", "May", "June", "July", "August",
                 "September", "October", "November", "December");
           	      
            document.frm_MTC70Register_Updated_By_Treasury_Section.txtCB_Year.value=year;
           
          //  document.frm_MTC70Register_Updated_By_Treasury_Section.txtMTCUpdatedDate.value =day+"/"+month+"/"+year;  
            
      }
    </script>
</head>

<%String s=request.getContextPath(); %>
<%System.out.println(s); %>

<body onLoad="LoadAccountingUnitID('LIST_ALL_UNITS'),loadyear_month(),initialLoad('<%=s %>');">

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
<input type="hidden" name="ori_unit" id="ori_unit" >
<input type="hidden" name="ori_office" id="ori_office" >

<div align="center">
<table cellspacing="1" cellpadding="2" border="1" width="100%">
	<tr class="table">
		<td>
		<div align="left">Accounting Unit Code <font color="#ff2121">*</font>		</div>		</td>
		<td>
		<div align="left"><select size="1" name="cmbAcc_UnitCode"
			id="cmbAcc_UnitCode" tabindex="1"  onChange="common_LoadOffice(this.value);">

		</select></div>		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Accounting For Office Code <font
			color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><select size="1" name="cmbOffice_code"
			id="cmbOffice_code" tabindex="2">

		</select></div>		</td>
	</tr>
	<tr class="table">
      <td><div align="left">Bill Year<font color="#ff2121">*</font></div></td>
	  <td><div align="left">
          <input type="text" name="txtCB_Year" size="4"
			id="txtCB_Year" maxlength="4"
			onkeypress="return numbersonly1(event,this)">
      </div></td>
	  </tr>
	<tr class="table">
      <td><div align="left">Bill Month<font color="#ff2121">*</font></div></td>
	  <td><div align="left">
          <select name="txtCB_Month" id="txtCB_Month"  onchange="getBillNo('<%=s%>');">
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
          </select>
      </div></td>
	  </tr>
	  <tr class="table">
      <td><div align="left">Bill No</div></td>
	  <td><div align="left">
          <select name="cboBillNo" id="cboBillNo"
			onchange="getBillDetails('<%=s%>');">
            <option value="s">--- Select ---</option>
          </select>
      </div></td>
	  </tr>
	<tr class="table">
      <td><div align="left">Bill Major Type <font color="#ff2121">*</font> </div></td>
	  <td><div align="left">
	    <select size="1" name="cboBillMajorType"
			id="cboBillMajorType" disabled="disabled">
            <option value="s">---Select---</option>
          </select>
	    </div></td>
	  </tr>
	
	<tr class="table">
      <td><div align="left">Bill Date</div></td>
	  <td><div align="left">
	    <input type="text" name="txtBillDate" readonly="readonly" class="light"
			id="txtBillDate">
	    
	  </div>	    </td>
	  </tr>
	  <tr class="table">
      <td><div align="left">MTC Date</div></td>
	  <td><div align="left">
	    <input type="text" name="txtmtcdate" readonly="readonly" class="light"
			id="txtmtcdate">
	    
	  </div>	    </td>
	  </tr>
	<tr class="table">
      <td><div align="left">Total Sanction Amount</div></td>
	  <td><div align="left">
          <input type="text" name="txtTotalSanctionAmount"  readonly="readonly" class="light"
			id="txtTotalSanctionAmount"
			onkeypress="return numbersonly1(event,this)">
      </div></td>
	  </tr>
	<tr class="table">
      <td><div align="left">Total Deduction Amount</div></td>
	  <td><div align="left">
          <input type="text" name="txtTotalDeductionAmount"  readonly="readonly" class="light"
			id="txtTotalDeductionAmount"
			onkeypress="return numbersonly1(event,this)">
      </div></td>
	  </tr>
	<tr class="table">
      <td><div align="left">Net Amount</div></td>
	  <td><div align="left">
          <input type="text" name="txtNetAmount" id="txtNetAmount"  readonly="readonly" class="light"
			onkeypress="return numbersonly1(event,this)">
      </div></td>
	  </tr>
	<tr class="table">
      <td><div align="left">Debit Account Head Code <font color="#ff2121">*</font></div></td>
	  <td><div align="left">
	    <input type="text" name="txtAcc_HeadCode"  readonly="readonly" class="light"
			id="txtAcc_HeadCode" maxlength="6"
			onkeypress="return numbersonly(event)"/>
	    </div></td>
	  <div style="display: none">
	    <select name="cmbSL_type"
			id="cmbSL_type">
	      </select>
        <select name="cmbSL_Code" id="cmbSL_Code">
        </select>
      </div>
	  </tr>
	<tr class="table">
      <td><div align="left">Payee Type<font color="#ff2121">*</font></div></td>
	  <td><div align="left">
	    <input type="text" name="txtPayeeType"  readonly="readonly" class="light"
			id="txtPayeeType" maxlength="15" />
	    </div></td>
	  </tr>
	<tr class="table">
      <td><div align="left">Payee Code<font color="#ff2121">*</font></div></td>
	  <td><div align="left">
	    <input type="text" name="txtPayeeCode"  readonly="readonly" class="light"
			id="txtPayeeCode" maxlength="15"
			onkeypress="return numbersonly1(event,this)" />
	    </div></td>
	  </tr>
	<tr class="table">
		<td>
		<div align="left">Pass Order Amount  <font color="#ff2121">*</font>		</div>		</td>
		<td><input type="text" name="txtPassOrderAmount"  readonly="readonly" class="light"
			id="txtPassOrderAmount" maxlength="15"
			onkeypress="return numbersonly1(event,this)" /></td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Checked &amp; Passed on Date  <font color="#ff2121">*</font>		</div>		</td>
		<td>
		<div align="left">
		  <input type="text" name="txtChecked_Passed_Date" maxlength="10"
			size="10" id="txtChecked_Passed_Date" onFocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);" onblur="checkpassdt();"
			onBlur="return checkdt1(this);" />  <img src="../../../../../images/calendr3.gif"
		                         onclick="showCalendarControl(document.frm_MTC70Register_Updated_By_Treasury_Section.txtChecked_Passed_Date);"
		                         alt="Show Calendar"></img>
         </div>		</td>
	</tr>
<tr class="table">
                <td width="40%">
                  <div align="left">Checked &amp; Passed By<font color="#ff2121">*</font> </div>                </td>
              <td width="60%">
            <table align="left">
             <tr align="left">
             <td>
                  <div align="left">
                        <select size="1" name="cmbMas_SL_Code" id="cmbMas_SL_Code">
                                
                          <option value="">--Select Code--</option>
                        </select>
                  </div>              </td>
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
                  <input type="hidden" name="cmbSL_Code" id="cmbSL_Code"/>               </td>
            </tr></table></td></tr>

	<tr class="table">
      <td><div align="left">MTC Updated Date</div></td>
	  <td><div align="left">
          <input type="text" name="txtMTCUpdatedDate" maxlength="10"
			size="10" id="txtMTCUpdatedDate" onFocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);" onblur="checkmtc();"
			onBlur="return checkdt1(this);" />
			  <img src="../../../../../images/calendr3.gif"
		                         onclick="showCalendarControl(document.frm_MTC70Register_Updated_By_Treasury_Section.txtMTCUpdatedDate);"
		                         alt="Show Calendar"></img>
          </div></td>
	  </tr>

	<tr class="table">
		<td>
		<div align="left">Sent to Pre-Audit on<font color="#ff2121">*</font>		</div>		</td>
		<td>
		<div align="left"><input type="text" name="SenttoPreAuditon"
			id="SenttoPreAuditon" maxlength="10" size="11"
			onfocus="javascript:vDateType='3';" onblur="checkaudit();"
			onkeypress="return calins(event,this);" onBlur="call_date(this);" />
			  <img src="../../../../../images/calendr3.gif"
		                         onclick="showCalendarControl(document.frm_MTC70Register_Updated_By_Treasury_Section.SenttoPreAuditon);"
		                         alt="Show Calendar"></img>
		</div>		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Remarks</div>		</td>
		<td>
		<div align="left"><textarea name="mtxtRemarks" id="mtxtRemarks"
			cols="50" tabindex="11" rows="4"></textarea></div>		</td>
	</tr>

	<tr class="tdH">
		<td colspan="2">
		<div align="center"><input type="button" name="butSub"
			id="butSub" value="SUBMIT" onClick="saveFunc('<%=s %>');" />
		&nbsp;&nbsp;&nbsp; <input type="button" name="butCan" id="butCan"
			value="CANCEL" onClick="refresh();" /> &nbsp;&nbsp;&nbsp; <input
			type="button" name="btnCan" id="btnCan" value="EXIT"
			onClick="exitfun('<%=s %>');" /></div>		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>