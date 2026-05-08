<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
    <%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile,Servlets.HR.HR1.EmployeeMaster.Model.LoadDriver"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Cancel Bills</title>

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

<script src="../scripts/Cancel_civil_bills.js"
	type="text/javascript"> </script>
<script type="text/javascript">
function loadyear_month()
{
        var today= new Date(); 
        var day=today.getDate();
        var month1=today.getMonth();
        month1=month1+1;
        
        if(day<=9 && day>=1)
        day="0"+day;
        if(month1<=9 && month1>=1)
        month1="0"+month1;
        var year=today.getYear();
        if(year < 1900) year += 1900;            
       document.frmmemo_list.txtCB_Year.value=year;
       document.frmmemo_list.txtCB_Month.value=month1;
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
<body onload="LoadAccountingUnitID('LIST_ALL_UNITS');loadyear_month();">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">Cancel Bills</font></div>
		</td>
	</tr>
</table>
<form name="frmmemo_list" id="frmmemo_list" method="POST"
 action="../../../../../Cancel_civil_bills?Command=Add" onsubmit="cancel_live();">
     
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
			                            id="cmbOffice_code">
			                    </select>
			                    </div>		
			                </td>
				</tr>
				
				<tr class="table">
		             <td class="table">
		                <div align="left">
		                        Options<font color="#ff2121">*</font>
		              </div>
		              </td>
		              <td colspan="2">
		               <div align="left">
		             <select name="Bills_list"  id="Bills_list" onchange="clearalllist();">
					          <option value="1">Bill Token Register Entry(with Proceedings)</option>
					          <option value="2">Bill Token Register Entry(without Proceedings)</option>
					          <option value="3">Memo of Payment Entry</option>
					          <option value="4">Bill Scrutiny</option>
					          <option value="5">Pass Order Preparation</option>
					          <option value="6">Pass Order Approval Details</option>
					          <option value="7">MTC-70 Register Entry</option>
					          <option value="8">MTC-70 Register Update by Treasury Section</option>
					          <option value="9">Pre-Audit Approval</option>
					          <option value="10">Cheque Memo from Original Bills</option>
					          <option value="11">Cheque Memo from Liability Journal</option>
					         <!--  <option value="12">Create SLS Journal</option>  -->
					          </select>
                      </div>
		              </td>
              </tr>
			
              <tr class="table">
					<td>
			                    <div align="left">CashbookYear & Month<font
			                            color="#ff2121">*</font>
			                    </div>		
			                </td>
					<td>
			                    <div align="left">
							  <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
					          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4">
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
					          <input type="BUTTON" value="GO" name="ByMonth" id="ByMonth"  tabindex="5" onclick="doFunction('searchByMonth','null')"/>
			                    </div>		
			                </td>
				</tr>
				
            </table>
          </div>
        </div>        
        
    
      <br>
      <div align="center">
        <div id="firstDiv" >
     <table id="mytable" align="center"  cellspacing="3" 
         cellpadding="2" border="1" width="100%">
          <tr class="tdH" id="firstid">
           <th>
              Select
            </th>
            <th id="bid">
              Bill No
            </th>
             <th id="bdid">
			Bill Date
            </th>            
            <th id="sanid">
            Sanction No
            </th>
             <th id="sanamtid">
            Sanc Amount
            </th>
              <th id="statusid">
           Status
            </th>
          </tr>
          
          <tbody id="tbody" class="table">          
          </tbody>
          
        </table>
        <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
            
      <tr class="tdH">
      <td>
          <div align="center">
           <input type="submit" id="cancelbtn" name="cancelbtn" value="Submit">
         <input type="button" id="exit" name="exit" value="EXIT" onclick="btncancel()">
      </div>
      </td>
      </tr>
      
      </table>
        </div>
      </div>
    </form>
</body>
</html>