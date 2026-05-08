<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <meta http-equiv="cache-control" content="no-cache">
    <title>BRS Auto Journal </title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />

 <script type="text/javascript"
            src="../../../../HR/HR1/OfficeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript"
            src="../../../../Library/scripts/checkDate.js"></script>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    
    <script type="text/javascript" src="../scripts/BRS_Auto_Journal.js" ></script>
    <script type="text/javascript" src="../../../../Security/scripts/tabpane.js"></script>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/Library/scripts/checkDate.js"></script>
 
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script>
   
    <script type="text/javascript" language="javascript">
       function loadyear_month()
       {
             var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             var year=today.getYear();
             if(year < 1900) year += 1900;
                        
            document.frmAuthorizationBRS.txtCB_Year.value=year;
            document.frmAuthorizationBRS.txtCB_Month.value=month;

            if(day<=9 && day>=1)
                day="0"+day;
                if(month<=9 && month>=1)
               	  month="0"+month;
                var year=today.getYear();
                if(year < 1900) year += 1900;
                var monthArray =new Array("January", "February", "March", 
                          "April", "May", "June", "July", "August",
                          "September", "October", "November", "December");
               document.frmAuthorizationBRS.txtCrea_date.value=day+"/"+month+"/"+year;

            
      }
    
    </script>
    
  </head>
  <body  onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS');" >
   
   
   
   
   
    <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">BRS Auto Journal </font>
          </div>
        </td>
      </tr>
    </table>
    
    
    
    <form name="frmAuthorizationBRS" id="frmAuthorizationBRS" method="POST"
                  action="../../../../../BRS_Auto_Journal.kv?Command=Add"
                  onsubmit="return checkNull()">
       
       <div align="center">
       
        
            <input type="hidden" name="txtTotTrans" id="txtTotTrans" value="0" />              
           
            <table cellspacing="1" cellpadding="2" border="0" width="100%">
               <tr class="table">
                <td width="40%">
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td width="60%">
                  <div align="left">
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);" >
                   
                    </select>
                  </div>
                </td>
              </tr>
              
              
              
              
              <tr class="table">
                <td width="40%">
                  <div align="left">
                    Accounting For Office Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td width="60%">
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2" onchange=" doFunction('load_Voucher_No','null');" >
                   
                    </select>
                  </div>
                </td>
              </tr>
              
              
              
              
             <tr class="table">
                <td width="40%">
                  <div align="left">
                    Cashbook Year & Month  <font color="#ff2121">*</font>
                  </div>
                </td>
                <td width="60%">
                   <div align="left">
	                  <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"
	                     maxlength="4" size="5"
	                     onkeypress="return numbersonly(event)"></input>
	               
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
		<div align="left">Bank A/C No.<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select name="cmbBankAccNo" id="cmbBankAccNo"
			onchange="Bank_Branch_Namee1(this.value);">
			<option value="">-- Select Bank A/C No ---</option>
		</select> <input type="button" name="Go" id="Go" value="Go"
			onclick="LoadBankAccountNumber()" /> <input type="hidden"
			name="txtOprMode" id="txtOprMode" tabindex="5"
			style="background-color: #ececec" readonly="readonly" size="50" /></div>
		</td>
	</tr>




	<div align="left" style="visibility: hidden"><input type="hidden"
		name="txtBankID" id="txtBankID" tabindex="5"
		style="background-color: #ececec" readonly="readonly" size="50" /> <input
		type="hidden" name="txtBranchID" id="txtBranchID" size="50"
		style="background-color: #ececec" readonly="readonly" /></div>
             <tr class="table">
                <td>
                  <div align="left">
                    Journal Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCrea_date" id="txtCrea_date" tabindex="3" 
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="call_date(this);"/>
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmAuthorizationBRS.txtCrea_date,1);"
                         alt="Show Calendar"></img>                   
                  </div>
                </td>
              </tr>
              
              
               <tr class="table">
                <td width="40%">
              
                </td>
                <td width="60%">
                  <div align="left">
                    <input type="button" name="List" id="List" value="List" onclick="doFunction('Load_TransDetails')" />
                  </div>
                </td>
              </tr>
              
              
            </table>
          </div>
      
      
          <table cellspacing="1" cellpadding="2" border="0" width="100%">
               <tr>
                 <td colspan="2">
                   <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2" border="0" width="100%">
                      <tr class="table">
                        <th > Select</th>
                        <th > PassBook Date </th>
                        <th > Particulars </th>                        
                        <th > Cheque / DD No</th>
                        <th > CR Amount </th>
                        <th > DR Amount </th>
                        <th > CR Head Code</th>
                        <th > DR Head Code</th>
                       </tr>                       
                       <tbody id="grid_body_NONTWAD" class="table" align="left" >
                       </tbody>                       
                    </table>
                  </div>
                </td>
              </tr>
            </table>
          
  
      <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
               <input type="submit" name="butSub" id="butSub" value="SUBMIT"/>
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="clrForm();"/>
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="exit();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
      
      
    </form>   
   </body>
</html>