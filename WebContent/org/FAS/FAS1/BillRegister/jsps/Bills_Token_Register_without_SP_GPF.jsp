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

  <script type="text/javascript" src="../../../../HR/HR1/OfficeMaster/scripts/CalendarControl.js"></script> 
  <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script> 

<script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>
<script type="text/javascript" src="../../../../Security/scripts/tabpane.js"></script>
<script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>

<script src="../scripts/Bills_Token_Register_without_SP_GPF.js"
	type="text/javascript"> </script>
<script type="text/javascript">
function loadDate(path)
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
		document.getElementById("Proceeding_Year").value=year;
	
		document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.Proceeding_Month.value=month;
	
}
</script>
</head>

<%String s=request.getContextPath(); %>
<%
 Calendar cal=Calendar.getInstance();
    int year=cal.get(Calendar.YEAR);
    
    int month = cal.get(Calendar.MONTH) + 1;
     
       int intyear = cal.get(Calendar.YEAR);%>
<body onload="LoadAccountingUnitID('LIST_ALL_UNITS');initialLoad('<%=s %>');loadDate('<%=s %>');loadPayeeType('<%=s %>');">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Bills-Token Register Entry-Without Sanction Proceeding for GPF</font></div>
		</td>
	</tr>
</table>

<form name="frm_BillTokenRegisterEntry_WithoutProceeding_GPF" id="frm_BillTokenRegisterEntry_WithoutProceeding_GPF"
 method="POST" onsubmit="return checkNull()"  action="../../../../../Bills_Token_Register_without_SP_GPF?command=saveFunc">
 <input type="hidden"
	name="cmbMas_SL_type" id="cmbMas_SL_type" value="7"
	onchange="doFunction('Load_MasterSL_Code',this.value);" />
	<input type="hidden" value="" id="mtxtRemarks" name="mtxtRemarks">
    <input type="hidden" id="hid_sanc" name="hid_sanc" ></input>  
      <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
          <h2 class="tab" > General </h2>
            <div id="imgfld" style="position: absolute; top: 354px; visibility: hidden; left: 378px; width: 212px; height: 6px;"
			left=100 top=100><input type="image" name="img1" id="img1"
			src="../../../../../images/Loading.gif" height="200"></div>
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
			                            id="cmbOffice_code" >
			                    </select>
			                    </div>		
			                </td>
				</tr>
              <tr class="table">
		<td>
		<div align="left">Proceeding Year<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="Proceeding_Year" size="10"
			id="Proceeding_Year" onKeyPress="return numbersonly1(event,this)"></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Proceeding Month<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="Proceeding_Month"
			id="Proceeding_Month" onChange="forBillNo('<%=s%>');">
			<option value="s">---Select---</option>
			<option value="01">January</option>
			<option value="02">February</option>
			<option value="03">March</option>
			<option value="04">April</option>
			<option value="05">May</option>
			<option value="06">June</option>
			<option value="07">July</option>
			<option value="08">August</option>
			<option value="09">September</option>
			<option value="10">October</option>
			<option value="11">November</option>
			<option value="12">December</option>
		</select></div>
		</td>
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
						id="cboBillMinorType" onChange="getBillsubType('<%=s %>'),loadAccHead();">
						<option value="0">---Select---</option>
					</select></div>		</td>
				</tr>
				<tr class="table">
					<td>
					<div align="left">Bill Sub Type <font color="#ff2121">*</font></div>		</td>
					<td>
					<div align="left">
					<select size="1" name="cboBillSubType" id="cboBillSubType" onchange="loadAccHead(),totAmt();">
						<option value="0">---Select---</option>
					</select></div>		</td>
				</tr> 
              <tr class="table">
		<td>
		<div align="left">Bill No <font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><input type="hidden" name="txtBillNo"
			id="txtBillNo"  readonly="readonly"/></div>	(System Generated)	</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Bill Date<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><input type="text" name="txtBillDate" STYLE="background-color:#E4F470"
		 maxlength="10" size="10" onblur="checksan();"
                   id="txtBillDate" onFocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);" onBlur="return checkdt1(this);"/>
                   <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtBillDate);"
                         alt="Show Calendar"></img></div>		</td>
	</tr>
	<tr class="table">
      <td><div align="left">Manual Proceeding No <font color="#ff2121">*</font></div></td>
	  <td><div align="left">
	    <input type="text" name="txtManualProceedingNo"
			id="txtManualProceedingNo" maxlength="15" onblur="allnumericplusminus(this.value);"
			/><label style="color: red;">Enter Numbers Only</label>
	    </div></td> 
	    
	    
	    
	  </tr>
	<tr class="table">
      <td><div align="left">Manual Proceeding Date<font color="#ff2121">*</font></div></td>
	  <td><div align="left">
	    <input type="text" name="txtManualProceedingDate" maxlength="20" size="10" onblur="checksan();"
                   id="txtManualProceedingDate" onFocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);" onBlur="return checkdt1(this);"/>
          <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtManualProceedingDate);"
                         alt="Show Calendar"></img></div></td>
	  </tr>
	  	<!--<tr class="table">
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
			name="txtInvoiceReceivedDate" id="txtInvoiceReceivedDate" onblur="checkinvdate();"
			maxlength="10" size="11" onFocus="javascript:vDateType='3';"
			onkeypress="return calins(event,this);" /> <img
			src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtInvoiceReceivedDate);"
			alt="Show Calendar"></img></div>		</td>
	</tr>

	--><tr class="table">
      <td><div align="left">Is MTC 70 Register Entry Required ? <font color="#ff2121">*</font></div></td>
	  <td><div align="left">
	    <label>
	    <input name="rdoMTC_70_Register" id="rdoMTC_70_Register" type="radio" value="Y" checked="checked" readonly="readonly">
	    Yes	    </label>
	    <input name="rdoMTC_70_Register" id="rdoMTC_70_Register" type="radio" value="N" readonly="readonly">
	   No</div></td>
	  </tr>
	<tr class="table">
		<td>
		<div align="left">Total Sanction Amount<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><input type="text" name="txtTotalSanctionAmount"
			id="txtTotalSanctionAmount" maxlength="15"
			onkeypress="return numbersonly1(event,this)" onblur="calbillAmt();" onchange="calbillAmt();"/></div>		</td>
	</tr>
	<!--<tr class="table">
      <td><div align="left">Deducted  Amount<font color="#ff2121">*</font></div></td>
	  <td><div align="left">
	    <input type="text" name="txtDeductedAmount"
			id="txtDeductedAmount" maxlength="15"
			onkeypress="return numbersonly1(event,this)" onblur="calbillAmt();"/>
	    </div></td>
	  </tr>-->
	  <tr class="table">
		<td>
		<div align="left">Total Bill Amount<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><input type="text" name="txtTotalBillAmount"
			id="txtTotalBillAmount" maxlength="15"
			onkeypress="return numbersonly1(event,this)" readonly="readonly" /></div>		</td>
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
	</tr><!--
			<tr class="table" style="visibility: hidden;">
				<td>
				<div align="left">Remarks</div>		</td>
				<td>
				<div align="left">
				<textarea name="mtxtRemarks" id="mtxtRemarks"
					cols="50" rows="4"></textarea></div>		</td>
			</tr>
            --></table>
          </div>
        </div>        
        <div class="tab-page" id="gd" >
          <h2 class="tab" >Details</h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr>
                <td colspan="2">
                  <div id="sub_ledge_dis" >
                    <table cellspacing="1" cellpadding="2" border="1"
                           width="100%">
                      <tr class="tdTitle">
                        <td colspan="2">
                          <div align="left">
                            <strong> Details</strong>
                          </div>
                        </td>
                      </tr>
                    <tr class="table">
		<td>
		<div align="left">AccountHeadCode & Desc <font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left">
		<select name="txtAcc_HeadCode" id="txtAcc_HeadCode" onblur="loadAccDesc();" onchange="loadAccDesc();">
				<option value="">Select</option>
				</select> <input
			type="text" name="txtAcc_HeadDesc" readonly="readonly"
			id="txtAcc_HeadDesc" style="background-color: #ececec"
			maxlength="125" size="50"  /></div>		</td>
		<!--<div style="display: none"> <select name="cmbSL_type"
			id="cmbSL_type"></select><select name="cmbSL_Code" id="cmbSL_Code"></select>
		</div>
	--></tr>
		<tr class="table">
		<td>
		<div align="left">SL Type<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left">
		<select name="txtPayeeType" id="txtPayeeType" onblur="loadPayeeCode_Desc();">
				<option value="">Select</option>
				</select>
		<!--<input type="text" name="txtPayeeType"
			id="txtPayeeType" maxlength="15" />-->	</div>	</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">SL Code<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left" id="one">
		<select name="txtPayeeCodeLoad" id="txtPayeeCodeLoad" onblur="loadPayy();" onchange="loadPayy();">
				<option value="">Select</option>
				</select>
	
		</div>	
		<div align="left" id="two" style="display:none">
		<select name="txtPayeeCodeLoad_two" id="txtPayeeCodeLoad_two" onblur="loadPayy();" onchange="loadPayy();">
				<option value="">Select</option>
				</select>
		</div>	<input type="text" name="txtPayeeCode" id="txtPayeeCode" maxlength="15" size="7"  onkeypress="return numbersonly1(event,this)" onchange="callpd();"/>
	
			</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Amout<font color="#ff2121">*</font>		</div>		</td>
		<td>
		<div align="left"><input type="text" name="txtBudgetAmount"
			id="txtBudgetAmount" maxlength="18" size="11"
			onkeypress="return numbersonly1(event,this)" ></div>		</td>
	</tr>
	<!--<tr class="table">
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
			onclick="showCalendarControl(document.frm_BillTokenRegisterEntry_WithoutProceeding_GPF.txtRefDate);"
			alt="Show Calendar"></img></div>		</td>
	</tr>
             
           
            
             
              <tr class="table">
                <td>
                  <div align="left">Particulars</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtParticular" id="txtParticular" cols="70" onkeypress="return check_leng(this.value,'particulars');"
                              rows="3"></textarea>
                  </div>
                </td>
              </tr>
                      --><tr class="tdTitle">
                        <td colspan="2" height="23">
                         <div align="center">
                            <table border="0">
                          <tr><td>
                          <input type="button" name="cmdadd" id="cmdadd"
                                 value="ADD" onclick="ADD_GRID()"  style="display:block"/></td>
                          <td>
                          <input type="button" name="cmdupdate" value="UPDATE"
                                 id="cmdupdate" onclick=" update_GRID()"
                                 style="display:none"/></td>
                          <td><input type="button" name="cmddelete" value="DELETE"
                                 id="cmddelete" onclick="delete_GRID()"
                                 disabled="disabled"/></td>
                          <td><input type="button" name="cmdclear" value="CLEAR ALL"
                                 id="cmdclear" onclick="clearall()"/></td>
                        </tr>
                        </table>
                        </div>
                        </td>
                      </tr>
                    </table>
                  </div>
                  <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      
                      <tr class="tdTitle" class="table">
                        <th >Select</th>
                        <th >A/c Head Code</th>
                        <th>Pay Type</th>
                        <th>Pay Code</th>
                         <th > Amount</th><!--
                        <th >Budget Provision</th>
                        <th >Budget so far Spent</th>
                         <th >Ref.No</th>
                        <th >Ref.Date</th>
                        <th>Particulars</th>
                      --></tr>
                       <tbody id="grid_body" class="table" align="left" >
                       </tbody>
                    </table>
                  </div>
                </td>
              </tr>
            </table>
          </div>
        </div>
      </div>
      <br>
      <div align="center">

        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
                <input type="button" name="butSub" id="butSub" value="SUBMIT" onclick="funAddNew('<%=s %>');"/>
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="CANCEL" 
                       onclick="clrForm('cancel');"/>
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="javascript:self.close();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </form>
</body>
</html>