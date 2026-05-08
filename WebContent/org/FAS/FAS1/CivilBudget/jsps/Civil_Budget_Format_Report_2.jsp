<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Civil Budget Report Format</title>
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
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script>
<script type="text/javascript" src="../scripts/Civil_Budget_Format_1.js"></script>

<script type="text/javascript" language="javascript">
      function loadyear_month()
         {
    	  //document.getElementById("cmbFinancialYear").length=1;
    	  var today = new Date();
  		var day = today.getDate();
  		var month = today.getMonth();
  		month = month + 1;
  		var year = today.getYear();
  		//alert(year);
  		var year1 = 0;
  		var financialyear0=0;
  		var financialyear = 0;
  		var financialyear1 = 0;
  		var financialyear2=0;
  		var fin1=0;
  		var fin2=0;
  		
  		if (year < 1900)
  			year += 1900;
  		if (month < 4) {
  			year1 = year - 1;
  		} else {
  			year1 = year + 1;
  		}

  		if (month < 4) {
  			financialyear0 = (year1+1) + "-" + (year+1);
  			financialyear = year1 + "-" + year;
  			financialyear1 = (year1-1) + "-" + (year-1);
  			financialyear2=(year1-2) + "-" + (year-2);

  			var ssyr1=financialyear0.substring(0,5);
  			var ssyr2=financialyear0.substring(7,9);
  				fin1=ssyr1+ssyr2;
  				
  			var ssyr3=financialyear.substring(0,5);
  			var ssyr4=financialyear.substring(7,9);
  				fin2=ssyr3+ssyr4;
  			

  			
  			document.getElementById("l1").value=(year1-2)+"-"+(year-2);
  			document.getElementById("l2").value=(year1-1)+"-"+(year-1);
  			document.getElementById("l3").value=(year1-1);
  			document.getElementById("l4").value=(year1-1)+"-"+(year-1);
  			document.getElementById("l5").value=(year1-1);
  			document.getElementById("l6").value=year1+"-"+year;
  		
  		} else {
  			financialyear0 = (year+1) + "-" + (year1+1);
  			financialyear = year + "-" + year1;
  			financialyear1 = (year-1) + "-" + (year1-1);
  			financialyear2=(year-2) + "-" + (year1-2);

  			var ssyr1=financialyear0.substring(0,5);
  			var ssyr2=financialyear0.substring(7,9);
  				fin1=ssyr1+ssyr2;
  				
  			var ssyr3=financialyear.substring(0,5);
  			var ssyr4=financialyear.substring(7,9);
  				fin2=ssyr3+ssyr4;

  			
  			document.getElementById("l1").value=(year-1)+"-"+(year1-1);
  			document.getElementById("l2").value=year+"-"+year1;
  			document.getElementById("l3").value=year+"-"+year1;
  			document.getElementById("l4").value=year+"-"+year1;
  			document.getElementById("l5").value=year+"-"+year1;
  			document.getElementById("l6").value=(year+1)+"-"+(year1+1);
  		
  		}
  		
  		//alert("financialyear::: "+financialyear);
  		//+"financialyear1::: "+financialyear1+"financialyear2::: "+financialyear2);
  		
  		for(var k=1;k<2;k++)
  		{
  			//if(k==0)
  			//{
  				//var se = document.getElementById("cmbFinancialYear");
  		  		//var op = document.createElement("OPTION");
  		  		//op.value = financialyear2;
  		  		//var txt = document.createTextNode(financialyear2);
  		  		//op.appendChild(txt);
  		  		//se.appendChild(op);
  			//}
  			//else
  			 if(k==1)
  			{
  				var se = document.getElementById("cmbFinancialYear");
  		  		var op = document.createElement("OPTION");
  		  		op.value = fin1;
  		  		var txt = document.createTextNode(fin1);
  		  		op.appendChild(txt);
  		  		se.appendChild(op);
  			}
   			//else if(k==2)
  			//{
  			//	var se = document.getElementById("cmbFinancialYear");
  		  	//	var op = document.createElement("OPTION");
  		  	//	op.value = financialyear;
  		  	//	var txt = document.createTextNode(financialyear);
  		  	//	op.appendChild(txt);
  		  	//	se.appendChild(op);
  		  		
  			//}                           
  		}   
  		var year_vl=year+1; 
  		document.getElementById("cmbFinancialYear").value=fin1; 
  		var er=financialyear.split('-');//alert(er);
  		var y1 = er[0];
  		var y2 = er[1];//alert("y1   >> "+y1+"y2   >> "+y1);
  		document.getElementById("hid").value=y1; 
  		document.getElementById("hid1").value=y2; 
  			
    
      
         }
      </script>

</head>
<%
	String s = request.getContextPath();
%>
<%
	System.out.println(s);
%>
<body onLoad="LoadAccountingUnitID('LIST_ALL_UNITS');loadyear_month();">

<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Civil Budget Report Format</font> </div>
		</td>
	</tr>
</table>

	
<form	
	name="frmCivil_Budget_Format_1"
	id="frmCivil_Budget_Format_1"
	method="POST" action="../../../../../Civil_Budget_Format_Report_1">
	<input type="hidden" name="hid" id="hid"/><input type="hidden" name="l1" id="l1"/>
	<input type="hidden" name="l2" id="l2"/>
	<input type="hidden" name="l3" id="l3"/>
	<input type="hidden" name="l4" id="l4"/>
	<input type="hidden" name="l5" id="l5"/>
	<input type="hidden" name="l6" id="l6"/>
<input type="hidden" name="hid1" id="hid1"/>
<div align="center">
<table cellspacing="1" cellpadding="2" border="0" width="100%">

	<tr class="tdTitle">
		<td colspan="2">
		<div align="left"><strong>General Details</strong></div>
		</td>
	</tr>



	<tr class="table">
		<td width="42%">
		<div align="left">Accounting Unit Code <font color="#ff2121">*</font>
		</div>
		</td>
		<td width="58%">
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

		<td width="30%"><div align="left">Financial Year <font color="#ff2121">*</font></div></td>
		<td width="70%"><div align="left">
		  <select name="cmbFinancialYear"
			id="cmbFinancialYear" onChange="ChangeYearDuration();">
		    </select> 
		  <!--<input type="button" id="butGo" name="butGo" value="GO"
			onclick="initialLoad1();"> 
			-->
                <div id="rdo" style="display: none;">
                   <input type="radio" name="r1" checked="checked" value="Detail" />Detail 
                   <input type="radio" name="r1" value="Abstract"  />Abstract</div>
                   
                   <div id="rdo11" style="display: none;">
                   <input type="radio" name="r11" checked="checked" value="6a" />6A
                   <input type="radio" name="r11" value="6b"  />6B</div>
                   <!--  
                   onchange="initialLoad2();" 
          
		  --></div> </td></tr>
     <tr class="table">
        <td align="left">Report Option:</td>
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
	<tr class="table">
		<td>
		<div align="left">Type Of Format</div>
		</td>
		<td>
		<div align="left"><select size="1" name="format_type"  onchange="changerdo();"
			id="format_type" tabindex="2">
			<option value="0">--Select--</option>
			<option value="1">Format-1</option>
			<option value="2">Format-2</option>
			<option value="3">Format-3</option>
			<option value="4">Format-4</option>
			<option value="5">Format-5</option>
			<option value="6">Format-6</option>
			<option value="7">Format-7</option>
			<option value="8">Format-8</option>
			<option value="9">Format-9</option>
			<option value="10">Format-10</option>
			<option value="11">Format-11</option>
			<option value="12">Format-12</option>
			<option value="13">Format-12A</option>
			</select>
		</div>
		</td>
		</tr>

	<tr class="table">
		<td>&nbsp;</td>
		<td>&nbsp;</td>
	</tr>
	<div align="left" style="visibility: hidden"><input type="hidden"
		name="txtBankID" id="txtBankID" tabindex="5"
		style="background-color: #ececec" readonly="readonly" size="50" /> <input
		type="hidden" name="txtBranchID" id="txtBranchID" size="50"
		style="background-color: #ececec" readonly="readonly" /></div>
	<tr class="tdH">
		<td colspan="2">
		<div align="center">
		<input type="submit" value="SUBMIT" id="sub" />
				&nbsp;&nbsp;&nbsp; 
				<input type="button" name="butCan" id="butCan"
			value="CANCEL" onClick="refresh();" /> &nbsp;&nbsp;&nbsp; <input
			type="button" name="butCan1" id="butCan1" value="EXIT"
			onclick="exitfun();" /></div>
		</td>
	</tr>
</table>
</div>
</form>
</body>
</html>