<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">

<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Twad Outstanding</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />

<script type="text/javascript"
	src="../../../../Library/scripts/comJS.js"></script>
	 <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script>
<script type="text/javascript" src="../scripts/TwadScheme_outstanding.js"></script>

<script type="text/javascript" language="javascript">
       function loadyear_month()
       {
	   var year1;
             var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             var year=today.getYear();
             if(year < 1900) year += 1900;
             if(month>3)           
			 {
			 year1 = year+1
			 }else{
			 year1 = year-1
			 }
			 
			 if(month>3)           
			 {
			 document.frmMIS_Twad.txtCB_Year.value=year;
			document.frmMIS_Twad.txtCB_Year2.value=year1;
			 }else{
			document.frmMIS_Twad.txtCB_Year.value=year1;
			document.frmMIS_Twad.txtCB_Year2.value=year;
			 }
            
           
      }
         
    
    </script>
</head>

<%
	String s = request.getContextPath();

response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server

	System.out.println(s);
%>
<body onload="LoadAccountingUnitID('LIST_ALL_UNITS'),LoadProgram();">

<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">General Liability of the Board</font>
		</div>
		</td>
	</tr>
</table>


<form name="frmMIS_Twad" id="frmMIS_Twad" action="">

 <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page" id="General">
          <h2 class="tab">General</h2>
          
          
          <div align="center" >
           <table cellspacing="1" cellpadding="2" border="0" width="100%">
	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong>General Details</strong></div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Financial Year</div>
		</td>
		<td>
		<!--<select id="fin_year" name="fin_year">
			<option value="2006-2007">2006-07</option>
			<option value="2007-2008">2007-08</option>
			<option value="2008-2009">2008-09</option>
			<option value="2009-2010">2009-10</option>
			<option value="2010-2011">2010-11</option>
			<option value="2011-2012">2011-12</option>
			<option value="2012-2013" selected="selected">2012-13</option>
		</select>
		-->
		<label id="fin_year" name="fin_year" >2012-13</label>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Contractor Payments due(Liability)</div>
		</td>
		<td><input type="text" id="cont_amt" name="cont_amt"" >
		<input type="hidden" id="cont_id" name="cont_id" value="101" ></td>
		</tr>
		<tr class="table">
		<td>
		<div align="left">Supplier  Payments due(Liability)</div>
		</td>
		<td><input type="text" id="sup_amt" name="sup_amt" >
		<input type="hidden" id="sup_id" name="sup_id" value="102"></td>
		</tr>
		<tr class="table">
		<td>
		<div align="left">EMD Received before 31Mar2011 and still due</div>
		</td>
		<td><input type="text" id="EMD_amt" name="EMD_amt" >
		<input type="hidden" id="EMD_id" name="EMD_id" value="103"></td>
		</tr>
		<tr class="table">
		<td>
		<div align="left">SD Received before 31Mar2011 and still due</div>
		</td>
		<td><input type="text" id="SD_amt" name="SD_amt" >
		<input type="hidden" id="SD_id" name="SD_id" value="104"></td>
		</tr>
		<tr class="table">
		<td>
		<div align="left">Deposit received for Water Connection</div>
		</td>
		<td><input type="text" id="Dep_amt" name="Dep_amt" >
		<input type="hidden" id="Dep_id" name="Dep_id" value="105"></td>
		</tr>
		<tr class="table">
		<td>
		<div align="left">Advance Received for Water Charges</div>
		</td>
		<td><input type="text" id="Adv_amt" name="Adv_amt" >
		<input type="hidden" id="Adv_id" name="Adv_id" value="105"></td>
		</tr>
	<tr class="table">
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>

	<tr class="tdH">
		<td colspan="2">
		<div align="center"><input type="button" name="onsubmit1" value="SUBMIT" id="onsubmit1" onClick="printFunc('<%=s%>');" />
			
			 <input type="button" name="onsubmit1" value="PDF" id="onsubmit1"  onClick="printPDF('<%=s%>');" />
			
			<input type="button" name="butCan" id="butCan" value="EXIT" onclick="window.close();" />
			</div>
		</td>
	</tr>
</table>
</div>
</div>
  <div class="tab-page1"  id="Gen_Liab">
          <h2 class="tab">Liability Details </h2>
           
          <div align="center" >
          
           <table cellspacing="1" cellpadding="2" border="0" width="80%" id="lia_row">
	<tr class="tdTitle">
		<td colspan="7">
		<div align="left"><strong>General Liability Details</strong></div>
		</td>
	</tr>

	<tr class="table">
		<td>
		<div align="left">Program</div>
		</td>
		<td>
		<div align="left">Scheme</div>
		</td>
		<td>
		<div align="left">Received Amt</div>
		</td>
		<td>
		<div align="left">Exp Booked in A/c</div>
		</td>
		<td>
		<div align="left">Exp Actually paid</div>
		</td>
		<td>
		<div align="left">Balance under Liability</div>
		</td>
		<td>
		</td>
		</tr>
		
	<tr class="table">
		<td>
		<div align="left">
		<select id="pro_id" name="pro_id">
		<option value="">--Select--</option>
	</select></div>
		</td>
		<td>
		<div align="left"><input type="text" id="sch_id"/></div>
		</td>
		<td>
		<div align="left"><input type="text" id="AmtRec_id"/></div>
		</td>
		<td>
		<div align="left"><input type="text" id="ExpBook_id"/></div>
		</td>
		<td>
		<div align="left"><input type="text" id="ExpAct_id"/></div>
		</td>
		<td>
		<div align="left"><input type="text" id="Bal_id"/></div>
		</td>
		<td>
		<div align="left"><input type="hidden" id="hid" name="hid" value="1"/>
		<input type="button" id="save_id" value="save" onclick="Row_Adding();"/></div>
		</td>
		</tr>
	<tr class="table">
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	</table>
<table cellspacing="1" cellpadding="2" border="0" width="80%" id="lia_row1">
	<tr class="tdH">
		<td colspan="7">
		<div align="center"><input type="button" name="onsubmit1" value="SUBMIT" id="onsubmit1" onClick="printFunc('<%=s%>');" />
			
			 <input type="button" name="onsubmit1" value="PDF" id="onsubmit1"  onClick="printPDF('<%=s%>');" />
			
			<input type="button" name="butCan" id="butCan" value="EXIT" onclick="window.close();" />
			</div>
		</td>
	</tr>
</table>

</div>
</div></div>
</form>
</body>
</html>