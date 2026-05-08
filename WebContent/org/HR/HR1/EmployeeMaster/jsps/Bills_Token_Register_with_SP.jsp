<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile,Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bills_Token_Register_with_SP</title>

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
	src="../../../../Security/scripts/tabpane.js"></script>


<script src="../scripts/Bills_Token_Register_with_SP.js"
	type="text/javascript"> </script>
<script type="text/javascript">
function loadDate()
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
		document.getElementById("txtBillDate").value=day+"/"+month+"/"+year;
}
</script>
</head>

<%String s=request.getContextPath(); %>
<%
 Calendar cal=Calendar.getInstance();
    int year=cal.get(Calendar.YEAR);
    
    int month = cal.get(Calendar.MONTH) + 1;
      //int day = cal.get(Calendar.DATE);
     // int intmonth = cal1.get(Calendar.MONTH) + 1;
       int intyear = cal.get(Calendar.YEAR);%>
<body
	onload="LoadAccountingUnitID('LIST_ALL_UNITS');initialLoad('<%=s %>');loadDate();selectMonth1('<%=intyear%>')">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Bills-Token Register Entry-With Sanction Proceeding</font></div>
		</td>
	</tr>
</table>


<form name="frm_BillTokenRegisterEntry_WithProceeding"
	id="frm_BillTokenRegisterEntry_WithProceeding" method="GET"
	action="../../../../../Bills_Token_Register_with_SP">
        
        <input type="hidden"
	name="cmbMas_SL_type" id="cmbMas_SL_type" value="7"
	onchange="doFunction('Load_MasterSL_Code',this.value);" />
<div align="center">
<table cellspacing="1" cellpadding="2" border="1" width="100%">	
	<tr class="table">
            <td>
                <div align="left">Accounting Unit Code <font color="#ff2121">*</font> </div>
            </td>
            <td><div align="left">
                <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onChange="common_LoadOffice(this.value);">
                </select>
                </div>
            </td>
        </tr>
	<tr class="table">
		<td>
                    <div align="left">Accounting For Office Code <font
                            color="#ff2121">*</font>
                    </div>		
                </td>
		<td>
                    <div align="left">
                    <select size="1" name="cmbOffice_code"
                            id="cmbOffice_code" onChange="common_LoadOffice(this.value);">
                    </select>
                    </div>		
                </td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Cashbook  Year & Month</div>		</td>
		<td>
		<div align="left"><input type="text" name="cash_year" size="4"
			id="cash_year" maxlength="4" value="<%=intyear %>"
			onkeypress="return numbersonly1(event,this)" /><select size="1" name="cash_month"
			id="cash_month">
			<option value="s">---Select---</option>
		</select></div>	</td>
	</tr>
	
	
	
	
	<tr class="table">
		<td>
                    <div align="left">Bill Major Type <font color="#ff2121">*</font>		
                    </div>		
                </td>
		<td>
		<div align="left">
                <select size="1" name="cboBillMajorType"
			id="cboBillMajorType" onChange="getBillMinorType('<%=s %>');">
			<option value="0">---Select---</option>
		</select>
                </div>		
                </td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Bill Minor Type <font color="#ff2121">*</font>		</div>		</td>
		<td>
		<div align="left"><select size="1" name="cboBillMinorType"
			id="cboBillMinorType" onChange="getBillsubType('<%=s %>');">
			<option value="0">---Select---</option>
		</select></div>		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Bill Sub Type <font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left">
		<select size="1" name="cboBillSubType" id="cboBillSubType" onchange="loadProceedingNo('<%=s%>');">
			<option value="0">---Select---</option>
		</select></div>		</td>
	</tr>
	<tr class="table" style="display:none;">
		<td>
		<div align="left">Bill No <font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><input type="text" name="txtBillNo"
			id="txtBillNo" maxlength="15"
			onkeypress="return numbersonly1(event,this)" readonly="true"/> (System Generated)</div>		</td>
	</tr>



	<tr class="table">
		<td>
		<div align="left">Bill Date<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><input type="text" name="txtBillDate" maxlength="10" size="10"
                   id="txtBillDate" onFocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);" onBlur="return checkdt1(this);"/>
                   <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frm_BillTokenRegisterEntry_WithProceeding.txtBillDate);"
                         alt="Show Calendar"></img></div>		</td>
	</tr>
	<tr class="table">
      <td><div align="left">Proceeding No <font color="#ff2121">*</font></div></td>
	  <td><div align="left">
	  <select name="txtProceedingNo" id="txtProceedingNo" onchange="loadprocDetails('<%=s%>');">
	  <option value="">Select Proceeding No</option>
	  </select>
	   <!-- <input type="text" name="txtProceedingNo" id="txtProceedingNo" maxlength="10" onkeypress="return numbersonly1(event,this)"/>  --> 
	    </div></td>
	  </tr>
	  
	  <tr class="table">
      <td><div align="left">Sanction ID<font color="#ff2121">*</font></div></td>
	  <td><div align="left">
	  <input type="text" name="sanc_id" id="sanc_id" />
	
	 
	   <!-- <input type="text" name="txtProceedingNo" id="txtProceedingNo" maxlength="10" onkeypress="return numbersonly1(event,this)"/>  --> 
	    </div></td>
	  </tr>
	  
	  
	  
	<tr class="table">
      <td><div align="left">Proceeding Received on Date<font color="#ff2121">*</font></div></td>
	  <td><div align="left">
	    <input type="text" name="txtProceedingDate" maxlength="10" size="10"
                   id="txtProceedingDate" onFocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);" onBlur="return checkdt1(this);"/>
       <!--     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frm_BillTokenRegisterEntry_WithProceeding.txtProceedingDate);"
                         alt="Show Calendar"></img>   -->
                         </div>
                         </td>
	  </tr>
          <tr class="table">
		<td>
		<div align="left">Payee Type<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left">
		<select name="txtPayeeType" id="txtPayeeType">
		<option value="">Select</option>
		</select>
		<!--   <input type="text" name="txtPayeeType" id="txtPayeeType" maxlength="15" />  -->
		</div>		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Payee Code<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><input type="text" name="txtPayeeCode"
			id="txtPayeeCode" maxlength="20" size="25"
			onkeypress="return numbersonly1(event,this)" /></div>		</td>
	</tr>
        <tr class="table">
		<td>
		<div align="left">Total Sanctioned Amount<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><input type="text" name="txtTotalSanctionedAmount"
			id="txtTotalSanctionedAmount" maxlength="15" size="10"
			onkeypress="return numbersonly1(event,this)" /></div>		</td>
		</tr>
        <tr class="table">
		<td>
		<div align="left">Total Bill Amount<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><input type="text" name="txtTotalBillAmount"
			id="txtTotalBillAmount" maxlength="15" size="10"
			onkeypress="return numbersonly1(event,this)" /></div>		</td>
	</tr>
        <!--
	<tr class="table">
            <td><div align="left">Deducted  Amount<font color="#ff2121">*</font></div></td>
            <td><div align="left">
                <input type="text" name="txtDeductedAmount"
                            id="txtDeductedAmount" maxlength="15"
                            onkeypress="return numbersonly1(event,this)" />
                </div>
            </td>
	  </tr>-->
          <tr class="table">
            <td><div align="left">Payable To<font color="#ff2121">*</font></div></td>
            <td><div align="left">
                <input type="text" name="txtPayableTo" size="25"
                            id="txtPayableTo" maxlength="15"
                            onkeypress="return numbersonly1(event,this)" />
                </div>
            </td>
	  </tr>
      <tr class="table">
      <td><div align="left">Is MTC 70 Register Entry Required ? <font color="#ff2121">*</font></div></td>
	  <td><div align="left">
                    <label>
                        <input name="rdoMTC_70_Register" id="rdoMTC_70_Register" type="radio" value="Y" checked="checked">
                            Yes	    
                    </label>
                        <input name="rdoMTC_70_Register" id="rdoMTC_70_Register" type="radio" value="N">
                            No
            </div>
      </td>
      </tr>
	<tr class="table">
		<td width="40%">
		<div align="left">Bill Processing Done By<font color="#ff2121">*</font></div>		</td>
		<td width="60%">
		<table align="left">
			<tr align="left">
				<td>
				<div align="left"><select size="1" name="cmbMas_SL_Code"
					id="cmbMas_SL_Code"></select></div>				</td>
				<td>
				<div align="left" id="offlist_div_master" style="display: none">

				<img src="../../../../../images/c-lovi.gif" width="20" height="20"
					alt="OfficeList" onClick="jobpopup_master();"></img> <input
					type="text" name="txtOfficeID_mas" id="txtOfficeID_mas"
					maxlength="4" size="5" onBlur="mas_office(this.value);" /></div>
				<div align="left" id="emplist_div_master"><img
					src="../../../../../images/c-lovi.gif" width="20" height="20"
					alt="empList" onClick="employee_popup_master();"></img> 
                                <input type="text" name="txtEmpID_mas" id="txtEmpID_mas" maxlength="6"
					size="6" onBlur="mas_employee(this.value);"  onchange="getOffice('<%=s %>');"/></div>
				<input type="hidden" name="cmbSL_type" id="cmbSL_type" /> 
                                <input type="hidden" name="cmbSL_Code" id="cmbSL_Code" /></td>
			</tr>
		</table>		</td>
	</tr>
        <!--
          <tr class="table">
      <td><div align="left">Name &amp; Designation</div></td>
	  <td><div align="left">
	    <input type="text" name="txtName_Designation"
			id="txtName_Designation" size="70" disabled="disabled"/>
	    </div></td>
	  </tr>
	-->
	
	<tr class="table">
		<td>
		<div align="left">Office<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><select size="1" name="cboOffice"
			id="cboOffice">
			<option value="s">---Select---</option>
		</select></div>		</td>
	</tr>
	
	<tr class="table" style="display:none">
		<td>
		<div align="left">AccountHeadCode & Desc <font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left">
		<select name="txtAcc_HeadCode"	id="txtAcc_HeadCode" onchange="loadAccountHeadDesc('<%=s %>');">
		<option value="0">Select</option>
		</select>
		<!--  <input type="text" name="txtAcc_HeadCode"	id="txtAcc_HeadCode" maxlength="6"
			onkeypress="return numbersonly(event)" onChange="sixdigit();" onblur="doFunction('checkCode','null'),calculateBudget('<%=s %>');" size="9" />
			<img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="AccountHeadList" onClick="AccHeadpopup();"></img> 
			 -->
			<input type="text" name="txtAcc_HeadDesc" readonly="readonly"
			id="txtAcc_HeadDesc" style="background-color: #ececec"
			maxlength="150" size="50" /></div>	<div align="left"><input type="text" name="acc_code"
			id="acc_code" style="display: none"
			onkeypress="return numbersonly1(event,this)" /> </div>			</td>
		<div style="display: none">
                <select name="cmbSL_type" id="cmbSL_type"></select> <select name="cmbSL_Code" id="cmbSL_Code"></select>
		</div>
	</tr> 
	<tr class="tdH">
	<td colspan="2"><div align="center"><font size="4">Budget Amount Details</font></div></td>
	</tr>
	
	
	<tr>	
	<td  colspan="2">
	<table id="acc_id" border="1" width="100%">
	<tr><th  style="font-size: small;" >
	<div align="center">ACCOUNT HEAD CODE</div>
	
	</th>
	<th style="font-size: small;" >
	<div align="center">ACCOUNT HEAD DESCRIPTION</div>
	</th>
	
	
	<th style="font-size: small;" >
	<div align="center">SANCTION AMOUNT</div>
	</th>
	
	<th style="font-size: small;" >
	<div align="center">BUDGET PROVISION</div>
	</th>
	
	<th style="font-size: small;" >
	<div align="center">BUDGET SO FAR SPENT</div>
	</th>
	
	<th style="font-size: small;" >
	<div align="center">Ref No</div>
	</th>
	
	<th style="font-size: small;" >
	<div align="center">Ref Date</div>
	</th>
	

	</tr>
	</table>
		</td>
		
		
	</tr>
	
	
	
	
<%-- 	
	<tr class="table">
		<td>
		<div align="left">Budget Provision<font color="#ff2121">*</font>		</div>		</td>
		<td>
		<div align="left"><input type="text" name="txtBudgetProvision" readonly="readonly" 
			id="txtBudgetProvision" maxlength="18" size="11"
			onkeypress="return numbersonly1(event,this)" ></div>		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Budget so far Spent<font color="#ff2121">*</font>		</div>		</td>
		<td>
<input type="text" name="txtBudgetSpent" readonly="readonly" id="txtBudgetSpent" maxlength="18" size="11"
			onkeypress="return numbersonly1(event,this)" >
			<input type="text" size="10" name="budAvail" id="budAvail" readonly="readonly" style="color:red">	
				</td>
			
	</tr>
	
	--%>
	
	<tr class="table" style="display:none">
		<td>
		<div align="left">Ref.No <font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><input type="text" name="txtRefNo" 
			id="txtRefNo" maxlength="15"
			onkeypress="return numbersonly1(event,this)" /></div>		</td>
	</tr>

	<tr class="table" style="display:none">
		<td>
		<div align="left">Ref.Date<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><input type="text" name="txtRefDate" 
			id="txtRefDate" maxlength="10" size="11"
			onfocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);" /> <img
			src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frm_BillTokenRegisterEntry_WithProceeding.txtRefDate);"
			alt="Show Calendar" ></img></div>		</td>
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
		<div align="center"><input type="button" name="onsubmit"
			value="UPDATE" id="onsubmit" onClick="update('<%=s%>');" />   
			
			<input
			type="submit" name="Print" value="PRINT" id="Print" disabled="disabled"/>
			Report Type<select name="cmbReportType" id="cmbReportType" style="width: 82px"><option value="PDF" selected="selected">PDF</option>
                              <option value="EXCEL">EXCEL</option>
                           
                         
                    
                            </select>
			
			 <input
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