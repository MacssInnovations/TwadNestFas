<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Cheque Number</title>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>   
    <script type="text/javascript" src="../scripts/cheque_No_details.js"></script>
    
    
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>   
    <script type="text/javascript" src="../scripts/CalendarControl_New.js"></script>    
    <script type="text/javascript" language="javascript">
         function foc()
         {
         }
         function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
         function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.chequeNumberMissing.txtCB_Year.value=year;
        document.chequeNumberMissing.txtCB_Month.value=month;
        
         }
</script>
  </head>  
  <body onload="loadyear_month();ClearAll();loadAccountingUnitID1('FOR_LIST_1');">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">
              <strong><strong>Cheque Number</strong></strong>
            </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="chequeNumberMissing" id="chequeNumberMissing" action="../../../../../Cheque_Number_details" method="post">
    
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
        
            <tr class="table">
                <td>
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="commonLoadOffice1(this.value);">
                   
                     </select>
                  </div>
                </td>
              </tr>
	          <tr class="table">
	            <td class="table">
	              <div align="left">
	                Accounting For Office Code 
	                <font color="#ff2121">*</font>
	              </div>
	            </td>
	            <td>
	              <div align="left">
	                <select size="1" name="cmbOffice_code" id="cmbOffice_code"
	                        tabindex="2"></select>
	              </div>
	            </td>
	          </tr>
              <tr class="table">
		          <td class="table">
			          <div align="left">
			              Select the Option :
			          </div>
		          </td>
	              <td>
	              		<input type="radio" name="month_year" id="month_year" value="particular_cb" onclick="cb_month_year(this.value)" > As on 
          				<input type="radio" name="month_year" id="month_year" value="more_cb" onclick="cb_month_year(this.value);"> For the Month 
          
          				<br><br>
          			<div align="left" id="particular" style="display:none">
          				<input type=text name=txtfromdate id=txtfromdate onkeypress="return  calins(event,this)" onblur="return checkdt(this);" onFocus="javascript:vDateType='3'" maxlength=10>
                        <img src="../../../../../images/calendr3.gif" onclick="showCalendarControl(document.chequeNumberMissing.txtfromdate);" alt="Show Calendar"
                                 height="24" width="19"></img>
          			</div>	       
          
		             <div align="left" id="more" style="display:none">
		          		Year<input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)" onblur="ClearAll()" >
		          		Month		         
			          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" onchange="ClearAll()">        
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
                  <div align="left">Bank Account Number
                   <font color="#ff2121">*</font>
                   </div>
                </td>
                <td>
                  <div align="left">
                    <select name="txtBankAccountNo" id="txtBankAccountNo" onchange="chequeRange()">  
                    <option>Select bank a/c number</option>                   
                    </select>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">Select Start & End Leaf No 
                   <font color="#ff2121">*</font>
                   </div>
                </td>
                <td>
                  <div align="left">
                    <select name="startEndLeaf" id="startEndLeaf" onchange="showMis()">  
                    	<option value="select">Select</option>
                    </select>
                  </div>
                </td>
              </tr>
              <tr class="tdH">
              <td colspan="2">
                <div align="center">
                 <table >
                    <tr>
                     <!--<td>
                        <input type="button" name="cmdAdd" value="Submit" id="cmdAdd" onclick="showMis()" />
                     </td>                     
                     --><td>
                        <input type="button" name="cmdClear" value="CLEAR" id="cmdClear" onclick="ClearAll()"/>
                     </td>
                     <td>
                        <input type="button" id="Exit" name="Exit" value="EXIT" onclick="exit()">
                     </td>
                  </tr>                  
                </table>                
                </div>
              </td>
            </tr>
         </table>
         <table id="mytable" cellspacing="3" cellpadding="2" border="1" width="100%" align="center">
		<tr class="tdH">
			<td colspan="3">Missing Cheque Number Details</td>
			<td colspan="1">
            	Page&nbsp;Size&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;<select name="cmbpagination" id="cmbpagination" onchange="changepagesize()">
                  <option value="5" >5</option>
                  <option value="10" selected="selected">10</option>
                  <option value="15">15</option>
                  <option value="20">20</option>
                </select>
          	</td>
		</tr>
		<tr>
			<th>Cheque Book Code</th>
			<th>Start Leaf No</th>			
			<th>End Leaf No</th>
			<th>Missing Cheque Number</th>		
 		</tr>
		<tbody id="tblList" class="table">
			<!-- 
          	-->
		</tbody>
 		<tr>
         	<td colspan="4" class="tdH">
         	<center>Pages          
          	<select name="cmbpage" id="cmbpage" onchange="changepage()"></select></center>
        	</td>
     	</tr>
	</table>
	<br>	
	<div id="div1" style="width: 100%; visibility: hidden">			
			<div style="left: 5%; position: absolute; width: 50%;">
				<label class="table">
					First Issue Cheque No :
				</label>					  
				<input type="text" name="startCheque" id="startCheque" disabled="disabled">				
			</div>
			<div style="left: 40%; position: absolute; width: 50%;">
				<label class="table">
					First Cheque Issue Date:
				</label>
				<input type="text" name="startDate" id="startDate" disabled="disabled">
			</div>			
	</div>
	<br>
	<br>
	<div id="div2" style="width: 100%; visibility: hidden">
			<div style="left: 5%; position: absolute; width: 50%;">
				Last Issue Cheque No :
				<input type="text" name="endCheque" id="endCheque" disabled="disabled">
			</div>
			<div style="left: 40%; position: absolute; width: 50%;">
				Last Cheque Issue Date : 
				<input type="text" name="endDate" id="endDate" disabled="disabled">
			</div>			
	</div>	
    </form>
  </body>
</html>