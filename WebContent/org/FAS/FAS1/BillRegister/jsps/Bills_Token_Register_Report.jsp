<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile,Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Bill Report</title>

<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />

  <script type="text/javascript" src="../../../../HR/HR1/OfficeMaster/scripts/CalendarControl.js"></script> 
  <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
  <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script> 

<script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>
<script type="text/javascript" src="../../../../Security/scripts/tabpane.js"></script>
<script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>

<script src="../scripts/Bills_Token_Register_Report_js.js"
	type="text/javascript"> </script>
<script type="text/javascript">
function loadDate(path)
{
var today= new Date(); 
var day=today.getDate();
var month=today.getMonth();
month=month+1;

if(day<=9 && day>=1)
day="0"+day;
if(month<=9 && month>=1)
month="0"+month;
var year=today.getYear();
if(year < 1900) year += 1900;
var monthArray =new Array("January", "February", "March", 
          "April", "May", "June", "July", "August",
          "September", "October", "November", "December");
		document.getElementById("txtBillDate").value=day+"/"+month+"/"+year;
		//alert("jjjj");
		//setTimeout('callemp('+path+')',900);
}function fun(val){
	document.getElementById("txtCB_Month").value="";
}

</script>
</head>

<%String s=request.getContextPath(); %>
<%
 Calendar cal=Calendar.getInstance();
    int year=cal.get(Calendar.YEAR);
    
    int month = cal.get(Calendar.MONTH) + 1;
      //int day = cal.get(Calendar.DATE);
     // int intmonth = cal1.get(Calendar.MONTH) + 1;
       int intyear = cal.get(Calendar.YEAR);%>
<body onload="LoadAccountingUnitID('LIST_ALL_UNITS');initialLoad('<%=s %>');loadDate('<%=s %>');">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Bills Token Register Entry-Report</font></div>
		</td>
	</tr>
</table>
<form name="bill_report" id="bill_report" method="POST" action="../../../../../Bills_Token_Register_Report_serv?command=saveFunc">
      <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
         
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              
               <tr class="table">
		            <td>
		                <div align="left">Accounting Unit Code <font color="#ff2121">*</font> </div>
		            </td>
		            <td><div align="left">
		                <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onblur="callemp();" onChange="common_LoadOffice(this.value);">
		                </select>
		                </div>
		            </td>
		        </tr>
				<tr class="table">
					<td>
			                    <div align="left">Accounting For Office Code <font
			                            color="#ff2121">*</font>
			                    </div>		
			                </td>
					<td>
			                    <div align="left">
			                    <select size="1" name="cmbOffice_code"
			                            id="cmbOffice_code" >
			                    </select>
			                    </div>		
			                </td>
				</tr>
				    <tr align="left">
            <td class="table">
              <div align="left">Computer Sanction Proceeding With/Without</div>
            </td>
            <td>
              <div align="left">
              <input type="radio" id="SanProYN" name="SanProYN" value="W" checked="checked" onclick="fun(this.value)">With Sanction Proceeding
                <input type="radio" id="SanProYN" name="SanProYN" value="WO"  onclick="fun(this.value)">Without Sanction Proceeding
              </div></td></tr>
              <tr align="left">
            <td class="table">
              <div align="left">Cash Book Year &amp; Month</div>
            </td>
            <td>
              <div align="left">
                From 
                <input type="text" name="txtCB_Year" id="txtCB_Year"
                       tabindex="3" maxlength="4" size="5"
                       onkeypress="return numbersonly(event)"></input>
                 
                <select name="txtCB_Month" id="txtCB_Month" tabindex="4" onchange="callbillno()">
                  <option value="">select the Month</option>
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
          	
          
          
			<tr align="left">
            <td class="table">
              <div align="left">Bill No</div>
            </td>
            <td>
            <select id="advnumber" name="advnumber" onchange="ChangesancNo();">
            <option value="0">select</option>
            <input type="text" name="sanctionid" id="sanctionid" readonly="readonly" size="25"></input>
            <input type="text" name="amtName1" id="amtName1" readonly="readonly" size="25"></input>
              
            </select>
            </td>
          </tr>	
            </table>
          </div>
        </div>        
        
      </div>
      <br>
      <div align="center">

        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
                <input type="submit" name="butSub" id="butSub" value="SUBMIT"/>
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="CANCEL" 
                       onclick="clrForm('cancel');"/>
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="javascript:self.close();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </form>
</body>
</html>