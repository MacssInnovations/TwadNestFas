<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BRS List</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />

<script type="text/javascript"
	src="../../../../Library/scripts/comJS.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
<script type="text/javascript"
	src="../../../../Security/scripts/tabpane.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script>
<script type="text/javascript" src="../scripts/BRS_List.js"></script>

<script type="text/javascript" language="javascript">
       function loadyear_month()
       {
             var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             var year=today.getYear();
             if(year < 1900) year += 1900;
                        
            document.frmBRSList.txtCB_Year.value=year;
            document.frmBRSList.txtCB_Month.value=month;
            document.frmBRSList.txtCB_Year_one.value=year;
      }
    
    </script>
</head>
<%
	String s = request.getContextPath();
%>
<%
	System.out.println(s);
%>
<body onLoad="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS');">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Bank Reconciliation
		System - BRS List</font></div>
                <div id="imgfld"
			style="position: absolute; top: 354px; visibility: hidden; left: 378px; width: 212px; height: 6px;"
			left=100 top=100><input type="image" name="img1" id="img1"
			src="../../../../../images/Loading.gif" height="200"></div>
		</div>
		</td>
	</tr>
</table>


<form name="frmBRSList" id="frmBRSList" method="POST" action="BRS_List">

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
			onchange="common_LoadOffice(this.value);">
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
		<div align="left">Type <font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<input type="radio" id="yearid" name="yearid" value="p" checked onclick="changeTab();">PassSheet Year
                <input type="radio" id="yearid" name="yearid" value="c" onclick="changeTab();">cashbook Year
		</td>
        </tr>


	<tr class="table">
		<td>
		<div align="left" id="passyear">Pass Sheet Year &amp; Month</div>
		</td>
		<td>
		<div align="left" id="passmonth"><input type="text" name="txtCB_Year"
			id="txtCB_Year" tabindex="3" maxlength="4" size="5"
			onkeypress="return numbersonly1(event,this)"></input> <select
			name="txtCB_Month" id="txtCB_Month" tabindex="4">
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
		<div align="left" id="cashyear" style="display:none">Cash Book Year &amp; Month</div>
		</td>
		<td>
		<div align="left" id="cashmonth" style="display:none"><input type="text" name="txtCB_Year_one"
			id="txtCB_Year_one" tabindex="3" maxlength="4" size="5"
			onkeypress="return numbersonly1(event,this)"></input> <select
			name="txtCB_Month_one" id="txtCB_Month_one" tabindex="4">
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
		<div align="left">Bank A/C No.</div>
		</td>
		<td>
		<div align="left"><select name="cmbBankAccNo" id="cmbBankAccNo">
			<option value="s">-- Select Bank A/C No ---</option>
		</select> <input type="button" name="Go" id="Go" value="Go"
			onclick="LoadBankAccountNumber()" /> <input type="hidden"
			name="txtOprMode" id="txtOprMode" tabindex="5"
			style="background-color: #ececec" readonly="readonly" size="50" /></div>
		</td>
	</tr>
	<div align="left" style="visibility: hidden"><input type="hidden"
		name="txtBankID" id="txtBankID" tabindex="5"
		style="background-color: #ececec" readonly="readonly" size="50" /> <input
		type="hidden" name="txtBranchID" id="txtBranchID" size="50"
		style="background-color: #ececec" readonly="readonly" /></div>
	<tr class="tdH">
		<td colspan="4">
		<input type="hidden" id="typelist" name="typelist" value="">
		<div align="center" id="passdividlist" style="display:block"><input type="button" name="onsubmit1"
			value="LIST Acknowledged For CashBook MM/YY =PassShett MM/YY" id="onsubmit1" onClick="setListType('list1'),ListAll('<%=s%>');" />
                         <input type="button" name="onsubmit1"
			value="LIST of Previous CashBook MM/YY Acknowledged During PassSheet MM/YY" id="onsubmit1" onClick="setListType('list2'),ListAll_acknowledged('<%=s%>');" />
                        <input type="button" name="onsubmit1"
			value="LIST Acknowledged For the PassSheet Year and Month" id="onsubmit1" onClick="setListType('list3'),ListAllPassSheet('<%=s%>');" />
                      
                    </div>
                    <div align="center" id="cashdividlist" style="display:none"><input type="button" name="onsubmit2"
			value="LIST Of Acknowledged Transaction For CashBook MM/YY" id="onsubmit1" onClick="ListAllCash('<%=s%>');" />
                        </div>
                    <div align="center">
		 <input type="button" name="butCan" id="butCan"
			value="CANCEL" onClick="refresh();" /> &nbsp;&nbsp;&nbsp; <input
			type="button" name="butCan" id="butCan" value="EXIT"
			onclick="exitfun();" />			
			</div>
		</td>
	</tr>
</table>
</div>
</div>
<!-- ------------------------- TWAD  Transactions Part II --------------------------------  -->

<div class="tab-page" id="gd">
<h2 class="tab">Cash Book Transactions</h2>

<div align="center">


<table id="mytable" cellspacing="0" cellpadding="0" border="0">
	<tbody id="grid_body_TotTrans" class="table" align="left">
	</tbody>
</table>




<table cellspacing="1" cellpadding="2" border="0" width="100%">
<tr class="tdTitle">
		<td colspan="2">
		  <div align="left"><strong>No of Records   :</strong>
		   <input name="txtNoofRecords" type="text" id="txtNoofRecords" size="5" readonly="readonly">
		  </div></td>
	</tr>
	<tr>
		<td colspan="2">

		<div id="grid" style="display: block">
		<table id="mytable" cellspacing="3" cellpadding="2" border="0"
			width="107%">
			<tr class="table">
			<th width="2%" ><font size=2> Sl No </font> </th>
				<th width="8%"><font size=2> Remittance Date </font></th>
				<th width="8%"><font size=2> Withdrawal Date </font></th>
				<th width="14%"><font size=2> Challan/
				Voucher No </font></th>
				<th width="11%"><font size=2>Cheque / DD No</font></th>
				<th width="7%"><font size=2>CR Amount </font></th>
				<th width="7%"><font size=2>DR Amount </font></th>
				<th width="6%"><font size=2>Realised Date </font></th>
				<th width="6%"><font size=2>Amount in Pass Book </font></th>
				<th width="7%"><font size=2>Difference</font></th>
				<th width="7%"><font size=2>Reason for Difference</font></th>
				<th width="7%"><font size=2>Follow-up Action Required? </font></th>
				<th width="7%"><font size=2>Is it a clearance entry
				based on follow-up?</font></th>

			</tr>
			<tbody id="grid_body_TWAD" class="table" align="Center">
			</tbody>
			<tr>
				<td>
					<input type="button" name="print" value="Print Cash" id="print" onClick="printAll('<%=s%>','T');" />
				</td>
			</tr>			
		</table>		
		<div id="div1" style="width: 100%; visibility: hidden">
			<div style="left: 25%; position: absolute; width: 15%;">
				<input type="text" name="crAmount" id="crAmount" style="width: 100%;" disabled="disabled">
			</div>
			<div style="left: 41%; position: absolute; width: 15%;">
				<input type="text" name="drAmount" id="drAmount" style="width: 100%;" disabled="disabled">
			</div>			
		</div>
		</div>

		</td>
	</tr>
	<tr class="tdTitle">
		<td colspan="2">
		  <div align="left"><strong>.</strong></div></td>
	</tr>
</table>



</div>

</div>




<!-- -------------------------Non TWAD  Transactions Part III --------------------------------  -->


<div class="tab-page" id="gd_bank" >
<h2 class="tab">Bank Transactions </h2>
<div align="center" >

<table cellspacing="3" cellpadding="2" border="0" width="100%">
<tr class="tdTitle">
		<td colspan="2">
		  <div align="left"><strong>No of Records   :</strong>
		   <input name="txtNoofRecords_NT" type="text" id="txtNoofRecords_NT" size="5" readonly="readonly">
		  </div></td>
	</tr>
	<tr class="table">
		<td>
		<div id="grid" style="display: block">
		<table id="mytable_details" cellspacing="3" cellpadding="2" border="0"
			width="100%">
			<tr class="table">
				<th>Sl No</th>
				<th>Pass Book Date</th>
				<th>Particulars</th>
				<th>Cheque No.</th>
				<th>Details</th>
				<th>CR Amount</th>
				<th>DR Amount</th>
				<th>Type of Transaction</th>
				<th>Follow-up Action Required?</th>
				<th>Is it a clearance entry based on follow-up?</th>
			</tr>
			<tbody id="grid_body_NONTWAD" class="table" align="Center">
			</tbody>
			<tr>
				<td>
					<input type="button" name="print" value="Print Bank" id="print" onClick="printAll('<%=s%>','B');" />
				</td>
			</tr>			
		</table>
		<div id="div2" style="width: 100%; visibility: hidden">
			<div style="left: 25%; position: absolute; width: 15%;">
				<input type="text" name="crAmount_bank" id="crAmount_bank" style="width: 100%;" disabled="disabled">
			</div>
			<div style="left: 41%; position: absolute; width: 15%;">
				<input type="text" name="drAmount_bank" id="drAmount_bank" style="width: 100%;" disabled="disabled">
			</div>			
		</div>
		</div>
		</td>
	</tr>
<tr class="tdTitle">
		<td colspan="2">
		  <div align="left"><strong>.</strong></div></td>
	</tr>
</table>
</div>

</div>
</div>
<br>
</form>
</body>
</html>