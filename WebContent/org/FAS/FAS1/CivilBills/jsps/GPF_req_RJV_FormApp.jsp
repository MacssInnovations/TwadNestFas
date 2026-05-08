<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GPF RJV Request</title>
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
<script type="text/javascript" src="../scripts/GPF_req_RJV_Form.js"></script>
<script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
<script src="<%=request.getContextPath()%>/org/FAS/FAS1/BillRegister/scripts/Bills_Token_Register_without_SP.js" 	type="text/javascript"> </script>
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
	
		document.getElementById("txtCB_Year").value=year;
	
		document.frmGPF_RJV_Req1.txtCB_Month.value=month;
	
}


</script>
</head>
<% String s=request.getContextPath(); %>
<body onload="loadDate('<%=s %>');">
<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">GPF RJV Request</font></div>
		</td>
	</tr>
</table>
<form id="frmGPF_RJV_Req1" name="frmGPF_RJV_Req1" method="GET" action="../../../../../GPF_req_RJV_Form">

       <input type="hidden" name="Command" id="Command" value="Add" >
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              
               
              <tr class="table">
					<td>
			                    <div align="left">CashbookYear & Month<font
			                            color="#ff2121">*</font>
			                    </div>		
			                </td>
					<td>
			                    <div align="left">
							  <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" >
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
					       
			                    </div>		
			                </td>
				</tr>
				
			      
              <tr class="table">
					<td colspan="2">
			                    <div align="left">
			                   <center> <input type="button" id="btnGo" name="btnGo" value="Go" onclick="goclick('<%=s %>');">
			                 </center>   </div>		
			                </td>
		        
		            </table>
		           
          <table cellspacing="1" cellpadding="2" border="1" width="100%">
            
              <tr>
                <td colspan="2">
                  
                  <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      
                          <tr class="tdH">
                            <th>Approve ?</th>         
                            <th>Request No</th>
                            <th>Accounting Unit</th>
                               <th>Vr. Date</th>         
                                  <th>Amount</th>         
                            <th>Details</th>         
                          </tr>
                      
                       <tbody id="grid_body" name="grid_body" class="table" align="left" >
                       </tbody>
                    </table>
                  </div>
                </td>
              </tr>
            </table>
          
          </div>
       
     
     
      <br>
      <div align="center">

        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
                <input type="submit" name="butSub" id="butSub" value="SUBMIT" />
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