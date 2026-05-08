<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page
	import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>


<html>
<head>
<meta http-equiv="Content-Type"
	content="text/html; charset=windows-1252" />
<meta http-equiv="cache-control" content="no-cache">
<title>Financial Accounting System</title>

<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript"
	src="../../../../../org/Library/scripts/checkDate.js"></script>
<script type="text/javascript"
	src="../../../../Security/scripts/tabpane.js"></script>
<script type="text/javascript"
	src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>


<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script>

<script type="text/javascript" src="../scripts/phone_bill_MainForm.js"></script>
<script type="text/javascript" src="../scripts/load_phoneNo_list.js"></script>
<script type="text/javascript" src="../scripts/phone_bill_list_js.js"></script>

<script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
<script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/CalendarControl.js"></script>

<script type="text/javascript" language="javascript">
       function loadyear_month()
       {
             var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             var year=today.getFullYear();
             var cur_Date = day + '/'+ month + '/'+ year;
             if(year < 1900) year += 1900;

            document.frmPhoneBillMainForm.txtBillDate.value= cur_Date;
            document.frmPhoneBillMainForm.txtDate.value= cur_Date;
            document.frmPhoneBillMainForm.txtBillYear.value= year;
            document.frmPhoneBillMainForm.txtBillMonth.value= month;

            
                      // alert(cur_Date);
           // document.frmBRSMainForm.txtCB_Year.value=year;
            //document.frmBRSMainForm.txtCB_Month.value=month;
      }
    
    </script>


</head>

<body onload=" LoadAccountingUnitID('LIST_ALL_UNITS');loadyear_month();setTimeout('load_Phone_Number()',2700);"
	bgcolor="rgb(255,255,225)">

<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Phone Bill</font></div>
		</td>
	</tr>
</table>


<!--<form name="frmPhoneBillMainForm" id="frmPhoneBillMainForm"
	method="POST" action="../../../../../BRS_MainForm.kv"
	onsubmit="checkNull()">

-->

<form name="frmPhoneBillMainForm" id="frmPhoneBillMainForm" action="../../../../../phone_bill_servlet?command=Add" method="POST">


<div class="tab-pane" id="tab-pane-1"><!-- ------------------------- General Part I --------------------------------  -->

<div class="tab-page">
<h2 class="tab">General</h2>

<div align="center">



<table cellspacing="1" cellpadding="2" border="0" width="100%">

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
		<div align="left"><select size="1" name="cmbAcc_UnitCode"
			id="cmbAcc_UnitCode" tabindex="1"
			onchange="common_LoadOffice(this.value);setTimeout('load_Phone_Number()',1800);clearGridList()">
			
			<!--<select size="1" name="cmbAcc_UnitCode"
			id="cmbAcc_UnitCode" tabindex="1"
			onchange="common_LoadOffice(this.value);setTimeout('load_Phone_Number()',900);setTimeout('clearGridList()',900)">
			
		--> </select></div>

		</td>
	</tr>

	<!-- common_LoadOffice(this.value) 	-->

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
<!-- <tr class="table">
		<td>
		<div align="left">Bill Number</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtBillNo" id="txtBillNo" tabindex="5"></input></div>
		</td>
	</tr>    --> 
<tr class="table">
		<td>
		<div align="left">Bill Date</div>
		</td>
		<td>
		<div align="left">
		<input type="text" name="txtBillDate" id="txtBillDate" tabindex="3" size="15"></input>
		<img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.frmPhoneBillMainForm.txtBillDate);"  alt="Show Calendar"></img>
		<input type="hidden" name="txtBillNo" id="txtBillNo" tabindex="5" />
		</div>
		</td>
	</tr>




	<tr class="table">
		<td>
		<div align="left">Total Bill Amount</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtTotalBillAmount"
			id="txtTotalBillAmount" onkeypress="return numbersonly1(event,this)" tabindex="5" size="30" /></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Remarks</div>
		</td>
		<td>
		<div align="left"><textarea name="txtGeneralRemarks"
			id="txtGeneralRemarks" COLS="40"></textarea></div>
			<input type="hidden" id = "hdnMessage" name = "hdnMessage"></input>
		</td>
	</tr>
</table>

</div>
</div>



<!--  ------------------------- TWAD  Transactions Part II -------------------------------- -->


<div class="tab-page">
<h2 class="tab">Details</h2>

<div align="center">



<table cellspacing="1" cellpadding="2" border="0" width="100%">

	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong>General Details</strong></div>
		</td>
	</tr>

	<!--  <tr class="table">
		<td>
		<div align="left">Bill Number</div>
		</td>
		<td>
		<div align="left"><input type="text" id="txtDetBillNo" name="txtDetBillNo"></input> 		</div>
		</td>
	</tr> -->

	<tr class="table">
		<td>
		<div align="left">Phone No. <font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select name="cmb_Phone_No" id="cmb_Phone_No"
			onchange="getPhoneNo_Details();" tabindex="1">
		</select></div>
		</td>
	</tr>


	<tr class="table">
		<td>
		<div align="left">Connection Type &amp; Purpose</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtConnectionType"
			id="txtConnectionType" tabindex="2" size="15" disabled="disabled"></input>
		<input type="text" name="txtPurpose" id="txtPurpose" tabindex="3"
			size="15" disabled="disabled"></input></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Service Provider Name &amp; Service Type</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtProviderName"
			id="txtProviderName" tabindex="4" disabled="disabled" /> <input
			type="text" name="txtProviderType" id="txtProviderType" tabindex="5"
			 disabled="disabled" /></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Custodian Name &amp; Designation</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtCustodianName"
			id="txtCustodianName" tabindex="6" disabled="disabled" /> <input
			type="text" name="txtDesignation" id="txtDesignation" tabindex="7"
			disabled="disabled" /></div>
		</td>
	</tr>
	
	

	<tr class="table">
		<td>
		<div align="left">Invoice Number &amp; Date</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtInvoiceNo"
			id="txtInvoiceNo" tabindex="8" /> <input type="text" name="txtDate"
			id="txtDate" tabindex="9" disabled="disabled" /> <img
			src="../../../../../images/calendr3.gif"
			onclick="showCalendarControl(document.frmPhoneBillMainForm.txtDate);"
			alt="Show Calendar"></img></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Bill Year &amp; Month</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtBillYear"
			id="txtBillYear" tabindex="10" disabled="disabled" /> <input
			type="text" name="txtBillMonth" id="txtBillMonth" tabindex="11"
			disabled="disabled" /></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Current Bill Amount</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtCurrentBillAmount"
			id="txtCurrentBillAmount"
			onkeypress="return numbersonly1(event,this)" tabindex="10" /></div>

		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Remarks</div>
		</td>
		<td>
		<div align="left"><textarea name="txtDetailRemarks"
			id="txtDetailRemarks" COLS="40"></textarea></div>
		</td>
	</tr>


	<tr class="tdTitle">
		<td colspan="2">
		<div align="center">
		<table border="0">
			<tr>
				<td><input type="button" name="cmdadd" id="cmdadd" value="ADD"
					onclick="ADD_GRID()" style="display: block" /></td> 			
					<!--<input type="button" name="cmdadd" id="cmdadd" value="ADD"
					onclick="ADD_GRID()" style="display: block" /> -->
					<!--<td><input type="button" name="cmdadd" id="cmdadd" value="ADD"
					onclick="ADD_GRID()" style="display: block" /></td> -->
				<td><input type="button" name="cmdupdate" value="UPDATE"
					id="cmdupdate" onclick="update_GRID()" disabled="disabled"/></td>
				<td><input type="button" name="cmddelete" value="DELETE"
					id="cmddelete" onclick="delete_GRID()" disabled="disabled" /></td>
				<td><input type="button" name="cmdclear" value="CLEAR ALL"
					id="cmdclear" onclick="clear_all_fields()" /></td>
			</tr>
		</table>
		</div>
		</td>
	</tr>
</table>
<div id="grid" style="display: block">
	<table id="mytable" cellspacing="3" cellpadding="2" border="1"
	width="100%">

	<tr class="table">
		<th>Select</th>
		<th>A/C Unit Code</th>
		<th>A/C Office Code</th>
		<th>Phone No</th>
		<th>Connection Type</th>
		<th>Purpose</th>
		<th>Service Provider</th>
		<th>Service Type</th>
		<th>Custodian Name</th>
		<th>Designation</th>
		<th>Invoice No</th>
		<th>Invoice Date</th>
		<th>Year</th>
		<th>Month</th>
		<th>Bill Amount</th>
		<th>Particulars</th>
	</tr>
	<tbody id="grid_body" class="table" align="left">
	</tbody>
</table>
</div>
</div>
</div>
</div>
<br>



<!-- ------------------------- Buttons Part --------------------------------  -->

 <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
                <input type="submit" name="butSub" id="butSub" onclick="return set_Message()" value="SUBMIT"/>
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="clrForm()"/>
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="btncancel();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>


</form>
</body>
</html>
