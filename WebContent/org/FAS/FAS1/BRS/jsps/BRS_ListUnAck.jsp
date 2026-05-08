<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
	pageEncoding="ISO-8859-1"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>BRS List UnAcknowledged</title>
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
<script type="text/javascript" src="../scripts/BRS_ListUnAck.js"></script>

<script type="text/javascript" language="javascript">
       function loadyear_month()
       {
             var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             var year=today.getYear();
             if(year < 1900) year += 1900;
                        
            document.frmBRSListUnAck.txtCB_Year.value=year;
            document.frmBRSListUnAck.txtCB_Month.value=month;
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
                    <div align="center"><font size="4">Bank Reconciliation System - BRS List - UnAcknowledged</font>
                    </div>
		</td>
	</tr>
</table>

<form name="frmBRSListUnAck" id="frmBRSListUnAck" method="POST" action="BRS_List_UnAck">
    <div class="tab-pane" id="tab-pane-1"><!-- ................. General Part I ........................  -->
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
                                <div align="left">
                                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onChange="common_LoadOffice(this.value);">
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
                                <div align="left">Cash Book Year &amp; Month</div>
                            </td>
                            <td>
                                <div align="left">
                                        <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3" maxlength="4" size="5" onKeyPress="return numbersonly1(event,this)">
                                        </input> 
                                        <select name="txtCB_Month" id="txtCB_Month" tabindex="4">
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
                	<tr class="table">
                            <td>
                                <div align="left">Bank A/C No.</div>
                            </td>
                            <td>
                                <div align="left">
                                    <select name="cmbBankAccNo" id="cmbBankAccNo">
                                        <option value="s">-- Select Bank A/C No ---</option>
                                    </select> 
                                    <input type="button" name="Go" id="Go" value="Go" onClick="LoadBankAccountNumber()" /> 
                                    <input type="hidden" name="txtOprMode" id="txtOprMode" tabindex="5" style="background-color: #ececec" readonly="readonly" size="50" />
                                    <input type="hidden" name="txtBankID" id="txtBankID" tabindex="5" style="background-color: #ececec" readonly="readonly" size="50" /> 
                                    <input type="hidden" name="txtBranchID" id="txtBranchID" size="50" style="background-color: #ececec" readonly="readonly" />
                                </div>
                            </td>
                        </tr>
                        <tr class="tdH">
                            <td colspan="2">
                                <div align="center">
                                    <input type="button" name="onsubmit1" value="List of Transactions Issued During the Cashbook Year & Month and UnAcknowledged" id="onsubmit1" onClick="refresh();ListAll('<%=s%>');" />&nbsp;&nbsp;&nbsp; 
                                  <!--    <input type="button" name="onsubmit1" value="LIST UnAcknowledged For the PassSheet Year & Month" id="onsubmit1" onClick="refresh();ListAll('<%=s%>');" />&nbsp;&nbsp;&nbsp;   --> 
                                    <input type="button" name="onsubmit1" value="List of Transactions Issued Upto the Cashbook Year & Month and UnAcknowledged" id="onsubmit1" onClick="refresh();ListAll_prev('<%=s%>');" />&nbsp;&nbsp;&nbsp; 
                                    </div>
                                    <div align="center">
                                    <input type="button" name="butCan" id="butCan" value="CANCEL" onClick="refresh();" /> &nbsp;&nbsp;&nbsp; 
                                    <input type="button" name="butCan" id="butCan" value="EXIT" onClick="exitfun();" />
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
                <div align="left">                   
                      <!--  <table id="mytable" cellspacing="3" cellpadding="2" border="1">
                                <tbody id="grid_body_TotTrans" class="table" align="left">
                                </tbody>
                        </table> -->
                        <table cellspacing="1" cellpadding="2" border="0" width="100%">
						<tr class="tdTitle">
                            <td colspan="2">
                                <div align="left"><strong>List Details</strong></div>
                            </td>
                        </tr>
						<tr class="tdH">
		<td colspan="2">
		  <div align="left"><strong>No of Records   :</strong>
		   <input name="txtNoofRecords" type="text" id="txtNoofRecords" size="5" readonly="true">
		  </div></td>
	</tr>
                            <tr>
                                <td colspan="2">
                                        <div id="grid" style="display: block">
                                            <table id="mytable" cellspacing="3" cellpadding="2" border="1" width="100%">
                                                <tr class="table" align="center">
                                                    <th width="8%"><font size=2>Serial Number </font></th>
                                                    <th width="8%"><font size=2>Remittance Date </font></th>
                                                    <th width="8%"><font size=2>Withdrawal Date </font></th>
                                                    <th width="14%"><font size=2>Challan / Voucher No </font></th>
                                                    <th width="11%"><font size=2>Cheque / DD No</font></th>
                                                    <th width="7%"><font size=2>CR Amount </font></th>
													<th width="7%"><font size=2>DR Amount </font></th>                    
                                                </tr>
                                                    <tbody id="grid_body" class="table" align="Center">
                                                    </tbody>
                                                    <tr>
														<td>
															<input type="button" name="print" value="PRINT" id="print" onClick="printAll('<%=s%>');" />
														</td>
													</tr>
                                            </table>
                                            <div id="crdrdiv1" style="width: 100%; visibility: hidden">
												<div style="left: 77%; position: absolute; width: 10.5%;">
													<input type="text" name="crAmount" id="crAmount" style="width: 100%;" disabled="disabled">
												</div>
												<div style="left: 88%; position: absolute; width: 11%;">
													<input type="text" name="drAmount" id="drAmount" style="width: 100%;" disabled="disabled">
												</div>			
											</div>
                                        </div>
                                </td>
                            </tr>
                        </table>
                </div>
        </div>

    </div>
    <br>
</form>
</body> 
</html>