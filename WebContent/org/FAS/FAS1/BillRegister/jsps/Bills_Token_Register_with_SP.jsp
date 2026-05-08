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

<script src="../scripts/Bills_Token_Register_with_SP.js"
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
	
		document.frm_BillTokenRegisterEntry_WithProceeding.Proceeding_Month.value=month;
	
}
function cb_month_year1(id)
{
   var particular=document.getElementById("particular");
   var more=document.getElementById("more");
       
  if(id=="particular_cb")
  {
     particular.style.display="block";
     more.style.display="none";
  }
  if(id=="more_cb")
  {
    more.style.display="block";
    particular.style.display="none";
  }
  if(id=="more_cb_monthwise")
  {
    more.style.display="block";
    particular.style.display="none";
  }
}
function clr()
{
	document.getElementById("cboBillMajorType").value=0;
	document.getElementById("cboBillMinorType").value=0;
	document.getElementById("cboBillSubType").value=0;
	document.getElementById("txtProceedingNo").value=0;
	}
</script>

</head>

<%String s=request.getContextPath(); %>
<%
 Calendar cal=Calendar.getInstance();
    int year=cal.get(Calendar.YEAR);
    
    int month = cal.get(Calendar.MONTH) + 1;
     
       int intyear = cal.get(Calendar.YEAR);%>
<body onload="LoadAccountingUnitID('LIST_ALL_UNITS');initialLoad('<%=s %>');loadDate('<%=s %>');">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Bills-Token Register Entry-With Sanction Proceeding</font></div>
		</td>
	</tr>
</table>

<form name="frm_BillTokenRegisterEntry_WithProceeding" id="frm_BillTokenRegisterEntry_WithProceeding"
 method="POST" onsubmit="return checkNull()"  action="../../../../../Bills_Token_Register_with_SP?command=saveFunc">
    <input type="hidden" id="hid_sanc" name="hid_sanc" />
  
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
			                            id="cmbOffice_code" onchange="setTimeout('dateCheck(document.cheque_memo.vochardate);',300)">
			                    </select>
			                    </div>		
			                </td>
				</tr>
				  <tr align="left">
           <td class="table">
              <div align="left">
                Option: <font color="#ff2121">*</font>
              </div>
           </td>
          <td>
         
          <input type="radio" checked="checked" name="Up_Month" id="Up_Month" value="particular_cb"  onclick=clr();>For the Month 
          <input type="radio" name="Up_Month" id="Up_Month" value="more_cb"  onclick=clr();> Up to Month 
          
        <%--   <br><br>       
          
          <div id="particular" name="particular" style="display:none">
            
          Proceeding Year
          <input type="text" name="Proceeding_Year" size="10"
			id="Proceeding_Year" onKeyPress="return numbersonly1(event,this)">
          Proceeding Month
          <select size="1" name="Proceeding_Month"
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
		</select>
          </div>
          
            <div id="more" name="more" style="display:none">
          
         
          </div>      --%>
           
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
			id="Proceeding_Month" onChange="">
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
	<%-- forBillNo('<%=s%>'); --%>
	   
                                  
          
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
						id="cboBillMinorType" onChange="getBillsubType('<%=s %>');mtc_hide();">
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
			  <td ><div align="left">
			  <input type="text" name="sanc_id" id="sanc_id" readonly="readonly" class="light"/>
			
			
			 
			   <!-- <input type="text" name="txtProceedingNo" id="txtProceedingNo" maxlength="10" onkeypress="return numbersonly1(event,this)"/>  --> 
			    </div></td>
			  </tr>
			  
			  
			  
			<tr class="table">
		      <td><div align="left">Sanction Proceeding Date<font color="#ff2121">*</font></div></td>
			  <td><div align="left">
			    <input type="text" name="txtProceedingDate" class="light" maxlength="10" size="10" readonly="readonly" onblur="checksan();" 
		                   id="txtProceedingDate" onFocus="javascript:vDateType='3';"
		                           onkeypress="return calins(event,this);" onBlur="return checkdt1(this);"/>
		       <!--     <img src="../../../../../images/calendr3.gif"
		                         onclick="showCalendarControl(document.frm_BillTokenRegisterEntry_WithProceeding.txtProceedingDate);"
		                         alt="Show Calendar"></img>   -->
		                         </div>
		                         </td>
			  </tr>
		          <!--<tr class="table">
				<td>
				<div align="left">Payee Type<font color="#ff2121">*</font></div>		</td>
				<td>
				<div align="left">
				<select name="txtPayeeType" id="txtPayeeType">
				<option value="">Select</option>
				</select>
				
				</div>		</td>
			</tr>
					<tr class="table">
						<td>
						<div align="left">Payee Code<font color="#ff2121">*</font></div>		</td>
						<td>
						<div align="left"><input type="text" class="light" name="txtPayeeCode" readonly="readonly"
							id="txtPayeeCode" maxlength="20" size="25"
							onkeypress="return numbersonly1(event,this)" /></div>		</td>
					</tr>
				        --><tr class="table">
						<td>
						<div align="left">Total Sanctioned Amount<font color="#ff2121">*</font></div>		</td>
						<td>
						<div align="left"><input type="text" class="light" name="txtTotalSanctionedAmount" readonly="readonly"
							id="txtTotalSanctionedAmount" maxlength="15" size="10"
							onkeypress="return numbersonly1(event,this)" /></div>		</td>
						</tr>
				        <tr class="table">
						<td>
						<div align="left">Total Bill Amount<font color="#ff2121">*</font></div>		</td>
						<td>
						<div align="left"><input type="text" class="light"  name="txtTotalBillAmount" readonly="readonly"
							id="txtTotalBillAmount" maxlength="15" size="10"
							onkeypress="return numbersonly1(event,this)" /></div>		</td>
					</tr> 
					<tr class="table">
				<td>
				<div align="left">Bill Date<font color="#ff2121">*</font></div>		</td>
				<td>
				<div align="left"><input type="text" name="txtBillDate" maxlength="10" STYLE="background-color:#E4F470"  class="light" readonly="readonly" size="10" onblur="checksan();"
		                   id="txtBillDate" onFocus="javascript:vDateType='3';"
		                           onkeypress="return calins(event,this);" onBlur="return checkdt1(this);"/>
		                   <img src="../../../../../images/calendr3.gif"
		                         onclick="showCalendarControl(document.frm_BillTokenRegisterEntry_WithProceeding.txtBillDate);"
		                         alt="Show Calendar"></img></div>		</td>
			</tr>
		              <tr class="table">
		            <td><div align="left">Payable To<font color="#ff2121">*</font></div></td>
		            <td><div align="left"> 
		                <input type="text" name="txtPayableTo" class="light" size="25" readonly="readonly"
		                            id="txtPayableTo" maxlength="15"
		                            onkeypress="return numbersonly1(event,this)" />
		                </div>
		            </td>
			  </tr>
		      <tr class="table">
		      <td><div align="left">Is MTC 70 Register Entry Required ? <font color="#ff2121">*</font></div></td>
			  <td>
			  <div align="left" id="yesid" style="display:block">
		                    
                <input name="rdoMTC_70_Register" id="rdoMTC_70_Register" type="radio" value="Y" checked="checked">
                    Yes	    
            </div >
            <div id="noid" style="display:block">
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
		                <input type="text" name="txtEmpID_mas" id="txtEmpID_mas" maxlength="6" size="6" onBlur="mas_employee(this.value);"  onchange="callemp('<%=s %>');"  onchange="getOffice('<%=s %>');"/></div>
						<input type="hidden" name="cmbSL_type" id="cmbSL_type" /> 
		                                <input type="hidden" name="cmbSL_Code" id="cmbSL_Code" /></td>
					</tr>
				</table>		</td>
			</tr>
			<tr class="table">
		<td>
		<div align="left">Office<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left"><select size="1" name="cboOffice"
			id="cboOffice">
			<option value="s">---Select---</option>
		</select></div>		</td>
			</tr>
			
			<tr class="table">
				<td>
				<div align="left">Remarks</div>		</td>
				<td>
				<div align="left"><textarea name="mtxtRemarks" id="mtxtRemarks"
					cols="50" rows="4"></textarea></div>		</td>
			</tr>
            </table>
          </div>
        </div>        
        <div class="tab-page" id="gd" >
          <h2 class="tab" >Details</h2>
           
          <div align="center" id="notgpfdetails" style="display:block">
         
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
            
              <tr>
                <td colspan="2">
                  
                  <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      
                          <tr class="tdH">
                            <th >Sl No</th> 
                            <th>Account Head</th>
                            <th >Amount</th>                
                            <th >Budget Provision (R.s)</th>     
                            <th>Budget so far spent(R.s)</th>
                            <th >Ref.No</th>                                                 
                            <th >Ref.Date</th>                                                    
                        
                           
                          </tr>
                      
                       <tbody id="grid_body" class="table" align="left" >
                       </tbody>
                    </table>
                  </div>
                </td>
              </tr>
            </table>
          </div>
          <div align="center" id="gpfdetails" style="display:none">
         
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
            
              <tr>
                <td colspan="2">
                  
                  <div id="grid1" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2" border="1" width="100%">
                      
                            <tr class="tdH">
                            <th >Sl No</th> 
                            <th>Account Head</th>
                            <th >Amount</th>                
                            <th>Payee Type</th>
                            <th >Payee Code</th>    
                          </tr>
                      
                       <tbody id="grid_body1" class="table" align="left" >
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