<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BRS RESET ALL TRANSACTIONS</title>
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />


<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript"
	src="../../../../Security/scripts/tabpane.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/BRS/scripts/BRS_Reset_All_Transactions.js"></script>
	
<script>

function submitfunc()
{
	
	//alert("To check the confirmation");
	var bank_ac_no = document.getElementById("cmbBankAccNo").value;
	if(bank_ac_no == "")
		{
		alert("select the account no to reset");
		}
	else
		{
	var response = confirm(" Do you confirm to reset all BRS Transactions for the Account No :: "+bank_ac_no);
	if(response)
		{
			var url = "../../../../../BRS_Reset_All_Transactions";
			document.getElementById("frmBRS_Reset_all_Trn").action = url;
			document.getElementById("frmBRS_Reset_all_Trn").method = "POST";
			document.getElementById("frmBRS_Reset_all_Trn").submit(); 
		}
		}
	}

function exitfun(path) {
	window.close();
}
</script>
</head>
<%
	String s = request.getContextPath();
%>
<%
	System.out.println(s);
%>
<body onLoad="LoadAccountingUnitID('LIST_ALL_UNITS');">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
                    <div align="center"><font size="4">Bank Reconciliation System - BRS Reset All Transactions</font>                    </div>
		</td>
	</tr>
</table>

<form name="frmBRS_Reset_all_Trn" id="frmBRS_Reset_all_Trn" >
                <div align="center">
                    <table cellspacing="1" cellpadding="2" border="0" width="100%">
                	
                	<tr class="table">
                            <td>
                                <div align="left">Accounting Unit Code <font color="#ff2121">*</font>
                                </div>
                            </td>
                            <td>
                                <div align="left">
                                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" ">
                                    </select>
                                </div>
                            </td>
                        </tr>
                	<!-- updated by B.Sathya on 20/08/2014 -->
                	<tr class="table">
							<td>
							<div align="left">Bank A/C No.<font color="#ff2121">*</font></div>
							</td>
							<td>
							<div align="left"><select name="cmbBankAccNo" id="cmbBankAccNo" onchange="loadStart_year();">
					           <option value="">-- Select Bank A/C No ---</option>
							</select>
							<input type="button" name="goo" id="goo" value="Go" onclick="LoadBankAccountNumber();"></input>
							<input type="hidden" name="txtOprMode" id="txtOprMode" tabindex="5" style="background-color: #ececec" readonly="readonly" size="50" />
							<input type="button" name="show" id="show" value="Show" onclick="LoadBRSDetails();"></input>
								</div>
							</td>
						</tr>
                	   
						<table cellspacing="3" cellpadding="2" border="0" width="100%">
                	    <tr class="table">
						<td>
						<div id="grid" style="display: block">
						<table id="mytable" cellspacing="3" cellpadding="2" border="0" width="100%">
							<tr class="tdTitle">
								<th>Sl No</th>
								<th>Accounting Unit Id & Name</th>
								<th>Account No</th>
								<th>Start Year</th>
								<th>Start Month</th>
						    </tr>
						    <tbody id="grid_body" class="table" align="Center">
							</tbody>
						</table>
				        </div>
		                </td>
		             
	                    </tr>
						
						</table>
            	
						 <tr class="tdH">
                            <td colspan="2">
                                <div align="center">
                                    <input type="button" name="sub" value="RESET ALL TRANSACTIONS" id="sub"  onclick="submitfunc()"/>&nbsp;&nbsp;&nbsp; 
                                 
                                    <input type="button" name="butCan" id="butCan" value="EXIT" onClick="exitfun();" />
                                </div>
                            </td>
                        </tr> 
                    </table>
                </div>
				</form>
</body>
</html>