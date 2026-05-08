<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Banks Balance Update Report</title>
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


<script type="text/javascript" src="../scripts/Clearance.js"></script>
 <script type="text/javascript">
window.onload=function(){

document.getElementById("cmbAcc_UnitCode").style.display='none';
}
</script>
<script type="text/javascript" language="javascript">
       function loadyear_month()
       {
             var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             var year=today.getYear();
             if(year < 1900) year += 1900;
                        
            document.BRSfreezedReport.txtCB_Year.value=year;
            document.BRSfreezedReport.txtCB_Month.value=month;
      }
       function disableunit()
       {
       	//alert("sdfsf")
       document.getElementById("cmbAcc_UnitCode").selectedIndex = 0;
       document.getElementById("cmbAcc_UnitCode").style.display='none';
       
       }
       function enableunit()
       {
       	//alert("eee")
       //document.getElementById("cmbAcc_UnitCode").selectedIndex = 0;
       document.getElementById("cmbAcc_UnitCode").style.display='block';
       
       }
       function fun(val)
       {
    	  if(val=="modewise")
    		  {
    		  document.getElementById("head_").style.display='block';
    		  document.getElementById("mode").style.display='block';
    		  }else{
    			  document.getElementById("head_").style.display='none';
    			  document.getElementById("mode").style.display='none';
    		  }
       }
    </script>
    
</head>
<%
	String s = request.getContextPath();
%>
<%
	System.out.println(s);
%>
<body onload="loadyear_month(); disableunit();LoadAccountingUnitID('LIST_ALL_UNITS');">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Bank Balance Update Report </font></div>
		</td>
	</tr>
</table>


<form name="BRSfreezedReport" id="BRSfreezedReport" method="POST"  action ="../../../../../BankBalanceReport">
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">

	





	 <tr class="table">
        <td>
		<div align="left">ReportOption <font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<input type="radio" id="officewise" name="officewise" value="Officewise" checked="checked" onclick="fun(this.value)" >Office Wise
                <input type="radio" id="officewise" name="officewise" value="modewise" onclick="fun(this.value)">Mode Of OperationWise
		</td>
        </tr>



<tr class="table">
        <td >
		<div align="left" id="head_" style="display: none;">Mode Of Operaion <font
			color="#ff2121">*</font></div>
		</td>
		<td>
	
		<select size="1" name="mode" 
			id="mode" tabindex="1" style="display: none;">
			<option value="all">All</option>
		<option value="COL">	Collection Account</option>
<option value="OPR">	Operation Account</option>
<option value="FDW">	Full Deposit Work</option>
<option value="SCH">	Scheme Account</option>
<option value="OPR-NRDWP-Main">	NRDWP-Main Activity</option>
<option value="OPR-NRDWP-Support">	NRDWP-Support Activity</option>
<option value="OPR-NRDWP-Calamity">	OPR-NRDWP-Calamity</option>
<option value="NRDWP-WQM-SP">	NRDWP-WQM-SP</option>

		</select>
		</td>
        </tr>




<tr class="table">
        <td>
		<div align="left">Accounting Unit Code <font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<input type="radio" id="unitcode" name="unitcode" value="All" checked="checked" onclick="disableunit();">All
                <input type="radio" id="unitcode" name="unitcode" value="single" onclick="enableunit()">Single Unit
		
		
		<select size="1" name="cmbAcc_UnitCode"
			id="cmbAcc_UnitCode" tabindex="1"
			onchange="common_LoadOffice(this.value);">
		</select>
		</td>
        </tr>


	

	<tr class="table">
		<td>
		<div align="left">Pass Sheet Year &amp; Month</div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtCB_Year"
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



	<!-- <tr class="table">
		<td>
		<div align="left">Accounting Unit Code <font color="#ff2121">*</font>
		</div>
		</td>
		
	</tr> -->

<tr class="table">
		<td>
		<div align="left">Report Option</div>
		</td>
		<td>
		<div align="left">
			<input type="radio" id="rep_opt" name="rep_opt" value="PDF" checked="checked" >PDF
                <input type="radio" id="rep_opt" name="rep_opt" value="EXCEL" >EXCEL
	
		
		</div>
		</td>
	</tr>

	
	
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><input type="submit" name="onsubmit1" value="View Report" id="onsubmit1" />
	
		<!--  <input type="button" name="butCan" id="butCan" value="CANCEL" onclick="refresh();" />  -->
		 <input type="button" name="butCan" id="butCan" value="EXIT" onclick="exitfun();" /></div>
		</td>
	</tr>
	
	
</table>
</div></form>
</body>
</html>