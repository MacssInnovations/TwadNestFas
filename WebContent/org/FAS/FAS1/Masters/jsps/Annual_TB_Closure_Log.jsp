<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>UnFreeze Annual_TB_Closure</title>
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<link rel=StyleSheet href="css/Sample3.css" type="text/css">
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
<script type="text/javascript"
	src="../../../../../org/Library/scripts/checkDate.js"></script>
<script type="text/javascript"
	src="../../../../Library/scripts/comJS.js"></script>
<script src="../scripts/Annual_TB_Closure_Log.js" type="text/javascript"> </script>

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
    		if (month < 4) {
    			year1 = year - 1;
    		} else {
    			year1 = year + 1;
    		}

    		if (month < 4) {
    			financialyear = year1 + "-" + year;
    			financialyear1 = (year1-1) + "-" + (year-1);
    		} else {
    			financialyear = year + "-" + year1;
    			financialyear1 = (year-1) + "-" + (year1-1);
    		}
    		for(var k=0;k<2;k++)
    		{
    			if(k==0)
    			{
    				var se = document.getElementById("cmbFinancial_Year");
    		  		var op = document.createElement("OPTION");
    		  		op.value = financialyear1;
    		  		var txt = document.createTextNode(financialyear1);
    		  		op.appendChild(txt);
    		  		se.appendChild(op);
    			}else if(k==1)
    			{
    				var se = document.getElementById("cmbFinancial_Year");
    		  		var op = document.createElement("OPTION");
    		  		op.value = financialyear;
    		  		var txt = document.createTextNode(financialyear);
    		  		op.appendChild(txt);
    		  		se.appendChild(op);
    		  		
    			}                           
    		}    
    		document.getElementById("cmbFinancial_Year").value=financialyear;           
         }
      </script>
</head>
<%String s=request.getContextPath(); %>
<%System.out.println(s); %>
<body class="table" onLoad="loadyear_month();">
<form name="frmAnnual_TB_Closure_Log" method="post"
	action="AnnualTBClosureLog" id="frmAnnual_TB_Closure_Log">

<table width="930" border="1" align="center">
	<tr>
		<td colspan="2" class="tdH">
		<div align="center">UnFreeze Annual TB Closure</div>
		</td>
	</tr>
	<tr class="table">
		<td width="327">
		<div align="left">Financial Year<font color="#ff2121">*</font></div>
		</td>
		<td width="587">
		<div align="left"><select name="cmbFinancial_Year"
			id="cmbFinancial_Year">
		</select></div>
		</td>
	</tr>
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><input type="button" name="butSub"
			id="butSub" value="SUBMIT" onClick="saveFunc('<%=s %>');" />
		&nbsp;&nbsp;&nbsp; <input type="button" name="btnCan" id="btnCan"
			value="EXIT" onClick="exitfun();" /></div>
		</td>
	</tr>
</table>

</form>
</body>
</html>