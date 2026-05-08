<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BRS_Start_Month_and_Year</title>
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
<script type="text/javascript"
	src="../../../../../org/Library/scripts/checkDate.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
<script type="text/javascript"
	src="../../../../Security/scripts/tabpane.js"></script>

<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
<script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script>

<script type="text/javascript" src="../scripts/BRS_Start_ Month_and_Year.js"></script>

<script type="text/javascript" language="javascript">
       function loadyear_month()
       {
            /* var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             var year=today.getYear();
             if(year < 1900) year += 1900;
                        
            document.frmBRS_Start_Month_and_Year.txtCB_Year.value=year;
            document.frmBRS_Start_Month_and_Year.txtCB_Month.value=month; */

           // setTimeout('loadStart_year()',900);
           //setTimeout('LoadBankAccountNumber()',900);
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
                    <div align="center"><font size="4">Bank Reconciliation System - BRS Start Month and year</font>                    </div>
		</td>
	</tr>
</table>

<form name="frmBRS_Start_Month_and_Year" id="frmBRS_Start_Month_and_Year" method="POST" action="BRS_Start_Month_and_Year">
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
                                <div align="left">
                                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onChange="common_LoadOffice(this.value);LoadBankAccountNumber();">
                                    </select>
                                </div>
                            </td>
                        </tr>
                	<tr class="table">
                            <td>
                                <div align="left">Accounting For Office Code <font color="#ff2121">*</font>
                                </div>
                            </td>
                            <td>
                              <div align="left">
                                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
                                    </select>
                                   
                                </div>
                            </td>
                	</tr>
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
								</div>
							</td>
						</tr>
                	<tr class="table">
                            <td>
                                <div align="left">Cash Book Year &amp; Month<font color="#ff2121">*</font></div>
                            </td>
                            <td>
                                <div align="left">
                                        <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3" maxlength="4" size="5" onKeyPress="return numbersonly1(event,this)">
                                        </input> 
                                        <select name="txtCB_Month" id="txtCB_Month" tabindex="4">
                                          <option value="">--Select--</option>
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
                                        </select>
                                         
                                </div>
                            </td>
                        </tr>
						 <tr class="tdH">
                            <td colspan="2">
                                <div align="center">
                                    <input type="button" name="onsubmit1" value="SUBMIT" id="onsubmit1" onClick="Add('<%=s%>');" />&nbsp;&nbsp;&nbsp; 
                                 
                                    <input type="button" name="butCan" id="butCan" value="CANCEL" onClick="refresh1();" /> &nbsp;&nbsp;&nbsp; 
                                    <input type="button" name="butCan" id="butCan" value="EXIT" onClick="exitfun();" />
                                </div>
                            </td>
                        </tr> 
                    </table>
                </div>
				</form>
</body>
</html>