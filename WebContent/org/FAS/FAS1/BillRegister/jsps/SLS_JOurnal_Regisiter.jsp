<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile,Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>SLS Journal Push</title>

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
		//document.getElementById("txtBillDate").value=day+"/"+month+"/"+year;
		document.getElementById("Proceeding_Year").value=year;
		document.frm_BillTokenRegisterEntry_WithProceeding.Proceeding_Month.value=month;
	
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
		<div align="center"><font size="4">SLS Journal Push</font></div>
		</td>
	</tr>
</table>
<br>
<form name="frm_BillTokenRegisterEntry_WithProceeding" id="frm_BillTokenRegisterEntry_WithProceeding"
 method="POST" onsubmit="return checkNull()"  action="../../../../../Bills_Token_Register_with_SP?command=saveJour">
    <input type="hidden" id="hid_sanc" name="hid_sanc" ></input>  
    <input type="hidden" id="coutn_sanc" name="coutn_sanc" ></input>  
      <table cellspacing="1" cellpadding="2" border="1" width="100%">
              
               <tr class="tdH">
		            <td colspan="2" >
      
           General Details 
          </td>
          </tr>
          </table>
           
       
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
			id="Proceeding_Month" >
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
						id="cboBillMajorType">
						<option value="2">Miscellaneous</option>
					</select>
			                </div>		
			                </td>
				</tr>
				<tr class="table">
					<td>
					<div align="left">Bill Minor Type <font color="#ff2121">*</font>		</div>		</td>
					<td>
					<div align="left"><select size="1" name="cboBillMinorType"
						id="cboBillMinorType" >
							<option value="2">Payroll</option>
					</select></div>		</td>
				</tr>
				<tr class="table">
					<td>
					<div align="left">Bill Sub Type <font color="#ff2121">*</font></div>		</td>
					<td>
					<div align="left">
					<select size="1" name="cboBillSubType" id="cboBillSubType" onchange="loadProceedingNo1('<%=s%>','pro_no');" >
					<option value="0">--Select--</option>
					<option value="1">SLS</option>
					</select></div>		</td>
				</tr> 
				<tr class="table">
		      <td><div align="left">Proceeding No <font color="#ff2121">*</font></div></td>
			  <td><div align="left">
			  <select name="txtProceedingNo" id="txtProceedingNo" onchange="ProNo_Details('<%=s%>','ProNo_Details');">
			  <option value="">Select Proceeding No</option>
			  </select>
			   <!-- <input type="text" name="txtProceedingNo" id="txtProceedingNo" maxlength="10" onkeypress="return numbersonly1(event,this)"/>  --> 
			    </div></td>
			  </tr>
              
            </table>
            
        <div>
      <table id="Existing"  cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
          <th>S.No</th> 
       	  <th>Employee Name</th>
          <th>Account Head</th>
           <th align="right">DR Amount</th>
          <th align="right">CR Amount</th> 
         
          
       <!--     <th>select/deselect</th> -->   
          
          </tr>
           <tbody id="tblList" align="center" class="table">
	   </tbody>
	
	    <!--    <tbody id="totalvalue" align="center" class="table"> </tbody>   -->
	   <tr class="tdH">
	    <td colspan="3">
                  <div align="center" >
                   Total                   
                  </div>
                </td>
	   <td align="right">
	    <div>
                    <input type="text" name="crtotal" id="crtotal" tabindex="3"
                            size="11" readonly="readonly" align="right"/></div></td>
            <td align="right">
	    <div>
                    <input type="text" name="drtotal" id="drtotal" tabindex="3"
                           size="11" readonly="readonly"  align="right"/></div></td>                
                           
                           </tr>
                           
        </table>
      </div>
    
      <br>
      <div align="center">

        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
                <input type="submit" name="butSub" id="butSub" value="SUBMIT"/>
                <!--
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="CANCEL" 
                       onclick="clrForm('cancel');"/>
                 &nbsp;&nbsp;&nbsp; 
                -->
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