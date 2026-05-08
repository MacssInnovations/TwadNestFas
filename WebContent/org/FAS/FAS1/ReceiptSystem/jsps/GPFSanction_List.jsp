<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>GPF sanction List</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
 <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
            
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Load_Accounting_office.js"></script>
    <script type="text/javascript" src="../scripts/GPFSanction_List.js"></script>
<script type="text/javascript" language="javascript">
      function loadyear_month()
         {    	  
    	  var today = new Date();
  		var day = today.getDate();
  		var month = today.getMonth();
  		month = month + 1;
  		var year = today.getYear();
  		var year1 = 0;
  		var financialyear = 0;
  		var financialyear1 = 0;
  		if (year < 1900)
  			year += 1900;
  		document.getElementById("txtCB_Year").value=year;          
         }
      </script>
</head>
<%
	String s = request.getContextPath();
	System.out.println("s");
%>

<!--<body onLoad="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS');billMajor();"
	bgcolor="#FFF9FF">
-->

<body onLoad="loadyear_month();billMajor();" bgcolor="#FFF9FF">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH1">
		<td colspan="2">
		<div align="center"><font size="4">List of Sanction Proceeding </font></div>
		</td>
	</tr>
</table>

<form name="frmGPFSanction" id="frmGPFSanction"
	method="POST" action="">
	<input type="hidden" id="radio_Val" name="radio_Val" value="Type" />
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">
	
	<!--<tr class="table1">
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
	<tr class="table1">
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
	
             
               --><tr class="table1">
		<td>
		<div align="left">Report Type  <font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left">
<input type="radio" id="radbtn_Off" value="All" name="All" onclick="ChngeAll();" />All
<input type="radio" id="radbtn_Off" value="Type" name="Type"  onclick="ChngeType();" checked />Type



</div>
		</td>
	</tr>
               
            
               
               <tr align="left" >
           			<td class="table1">
              			<div align="left">
                 			Cash Book Year &amp; Month <font color="#ff2121">*</font>
              			</div>
           			</td>
          			<td class="table1">        				       
		        
          		
          		<input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
          		<select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
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
          
         
          </td>
        </tr>
        
        <tr class="table1">
		<td>
		<div align="left">Bill Major Type<font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="cmbBill_Major"
			id="cmbBill_Major" tabindex="2" onchange="billMinor();">
		</select></div>
		</td>
	</tr>
	       <tr class="table1">
		<td>
		<div align="left">Bill Minor Type<font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="cmbBill_Minor"
			id="cmbBill_Minor" tabindex="2" onchange="subType();">
		<option value="">--Select--</option>
		</select>
		
		</div>
		</td>
	</tr>
	 <tr class="table1">
		<td>
		<div align="left">Bill Sub Type<font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="cmbSub_Type"
			id="cmbSub_Type" tabindex="2">
		<option value="">--Select--</option>
		</select>
		
		</div>
		</td>
	</tr>
        
        <tr class="table">
        <td align="left">Report Option<font color="#ff2121">*</font></td>
        <td colspan="3" align="left">
          <input type="radio" name="txtoption" id="txtoption" value="PDF"
                 checked="checked"></input>
          PDF
          <input type="radio" name="txtoption" id="txtoption" value="EXCEL"></input>
          Excel
          <input type="radio" name="txtoption" id="txtoption" value="HTML"></input>
          HTML
        </td>
      </tr>
        
	</table>
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH1">
		<td colspan="2">
		<div align="center">
		<input type="button" id="btnSub" value="Submit" onclick="ReportGPF();">
		</div>
		</td>
	</tr>
</table>
	</div>
	</form>

</body>
</html>