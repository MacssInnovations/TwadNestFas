<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sanction Proceedings For A period</title>

  <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>

<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>

 <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
  <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

<script type="text/javascript"  src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"  src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
   
    
   
    
    <script type="text/javascript"   src="../scripts/SanctionProceedingPeriod.js"></script>
     <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl_forChequeDate.js"></script> 
      <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
      
      
      
      
     <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
          <script type="text/javascript" src=".../../../../ReceiptSystem/scripts/Common_ReceiptType.js"></script>
        <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
        <script type="text/javascript"src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>
        
        
        
        
        
        
        
        
    <script type="text/javascript" language="javascript">
        
         function loadDate()
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
           // document.frmPre_AuditDetails.txtDateOfReceipt.value=day+"/"+month+"/"+year;
             document.frmSanctionProceedingsPeriod.txtRefDate.value=day+"/"+month+"/"+year;
             document.frmSanctionProceedingsPeriod.txtSanctionProceedingDate.value=day+"/"+month+"/"+year;
             document.frmSanctionProceedingsPeriod.txtSanctionProceedingFromDate.value=day+"/"+month+"/"+year;
             document.frmSanctionProceedingsPeriod.txtSanctionProceedingToDate.value=day+"/"+month+"/"+year;
               document.frmSanctionProceedingsPeriod.cmbCashBookYear.value=year;
           
             document.frmSanctionProceedingsPeriod.cmbCashBookMonth.value=month;

        }
</script>
    
</head>
<body onload="loadDate(),LoadAccountingUnitID('LIST_ALL_UNITS'),loadmajortype(),loaddes();">
<table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Sanction Proceedings For A period </font>
          </div>
        </td>
      </tr>
    </table>
<form name="frmSanctionProceedingsPeriod" id="frmSanctionProceedingsPeriod">
 
 
                    
 
           
          <div align="center">
 <table width="100%" cellspacing="2" border="1">
     
    
                         <tr class="table">
                                    <td width="257">Accounting Unit Name<span class="style1">* </span></td>
                                    <td width="291"> <select name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"  readonly=readonly></select></td>             
                        </tr>
                        <tr class="table">
                          <td>Accounting Unit Office Name<span class="style1">* </span> </td>
                          <td><select name="cmbOffice_code" id="cmbOffice_code" readonly=readonly></select></td>
                        </tr>
                        
                          <tr class="table">
                                    <td width="257">Year<span class="style1">* </span></td>
                                    <td width="291"> <select name="cmbCashBookYear" id="cmbCashBookYear" style="width:153px">
                                    <option value="0">--Select--</option>
                                    <option value="2009">2009</option>
                                    <option value="2010">2010</option>
                                    </select></td>             
                        </tr>
                        
                        <tr class="table">
                                    <td width="257">Month<span class="style1">* </span></td>
                                    <td width="291"> <select name="cmbCashBookMonth" id="cmbCashBookMonth">
                                    <option value="0">--Select--</option>
                                     <option value="01">Jan</option>
                                     <option value="02">Feb</option>
                                     <option value="03">Mar</option>
                                     <option value="04">Apr</option>
                                     <option value="05">May</option>
                                     <option value="06">June</option>
                                     <option value="07">Jul</option>
                                     <option value="08">Aug</option>
                                     <option value="09">Sep</option>
                                     <option value="10">Oct</option>
                                     <option value="11">Nov</option>
                                     <option value="12">Dec</option>
                                    </select></td>             
                        </tr>
                       
                          <tr class="table">
                                    <td width="257">Bill Major Type<span class="style1">* </span></td>
                                    <td width="291"> <select name="cmbMajorType" id="cmbMajorType"  onchange="loadMinorType()">
                                    <option value="s">--Select--</option>
                                    </select></td>             
                        </tr>
                        
                        <tr class="table">
                                    <td width="257">Bill Minor Type<span class="style1">* </span></td>
                                    <td width="291"> <select name="cmbMinorType" id="cmbMinorType" onchange="loadsubType()" >
                                    <option value="s">--Select--</option>
                                      
                                    
                                    </select></td>             
                        </tr>
                       
                        <tr class="table">
                                    <td width="257">Bill Sub Type<span class="style1">* </span></td>
                                    <td width="291"> <select name="cmbBillSubType" id="cmbBillSubType">
                                    <option value="s">--Select--</option>
                                    </select></td>             
                        </tr>
                         <tr class="table"> 
                          <td>Payee Type<span class="style1">* </span> </td>
                          <td> <input type="radio" name="r1" id="r1" value="E" Checked="checked" >Employee <input type="radio" name="r1" id="r1" value="P"/>Pensioner
                          
                          </td>
                        </tr>
                             <tr class="table"  style="display='inline'" width="100%">
                <td>
                    <div>
                      Payee Code
                    </div>
                </td>
               <td>
                     
                
                   <div align="left" id="emplist_div_approvedby">
                    <input type=text name="txtPayeeName"  id="txtPayeeName"  maxlength="40" readonly="readonly"  />&nbsp;&nbsp; 
                        <img src="../../../../../images/c-lovi.gif"  height="20" alt="empList" onclick="emp_popup_sanction_approvedby();"/>&nbsp;&nbsp;  
                        <input type="text" name="txtEmpID_mas1"  id="txtEmpID_mas1"  maxlength="6" size="6" onchange="emp_sanction_approvedby()" onblur="CheckPayment();" onkeypress="return numbersonly(event)"/>
                  </div> 
                  
                
                </td>
             </tr>
                        <tr class="table">
                          <td>Ref.No<span class="style1">* </span> </td>
                          <td><input type="text" name="txtRefNo" id="txtRefNo"  maxlength="20" onkeypress="return numbersonly(event);"></input></td>
                        </tr>
                          <tr class="table">
                          <td>Ref.Date<span class="style1">* </span> </td>
                          <td><input type="text" name="txtRefDate" id="txtRefDate"  maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="call_date(this);"/>
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmSanctionProceedingsPeriod.txtRefDate);"
                         alt="Show Calendar"></img> 
                        </tr>
                        
                        <tr class="table">
                          <td>Sanction Proceeding Date<span class="style1">* </span> </td>
                          <td><input type="text" name="txtSanctionProceedingDate" id="txtSanctionProceedingDate" maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                          />
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmSanctionProceedingsPeriod.txtSanctionProceedingDate);"
                         alt="Show Calendar"></img> 
                        </tr>
                   
                       <tr class="table">
                <td>
                    <div align="left">
                      Sanctioning authority 
                    </div>
                </td>
                 <td width="291"> <select name="cmbSanctionAutrority" id="cmbSanctionAutrority">
                                    <option value="s">--Select--</option>
                                    </select></td>   
                
              </tr>
              <tr class="table">
                <td>
                    <div align="left">
                        Sanction By
                    </div>
                </td>
                <td>
                      
               
                   <div align="left" id="emplist_div_preparedby">
                    <input type="text" name="txtSanction_Estimate_PreparedBy"  id="txtSanction_Estimate_PreparedBy"  maxlength="40" readonly="readonly"/>&nbsp;&nbsp;  
                        <img src="../../../../../images/c-lovi.gif" height="20" alt="empList" onclick="emp_popup_sanction_preparedby();"/>&nbsp;&nbsp; 
                        <input type="text" name="txtEmpID_mas"  id="txtEmpID_mas"  maxlength="6" size="6" onchange="emp_sanction_preparedby();" onkeypress="return numbersonly(event)"/>  
                  </div>
                
               
                </td>
                
              </tr>
                 
                 
                   <tr class="table">
                          <td>Sanction Proceeding Appilcable From Date<span class="style1">* </span> </td>
                          <td><input type="text" name="txtSanctionProceedingFromDate" id="txtSanctionProceedingFromDate" maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                          />
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmSanctionProceedingsPeriod.txtSanctionProceedingFromDate);"
                         alt="Show Calendar"></img> 
                        </tr>
                        <tr class="table">
                          <td>Sanction Proceeding Appilcable To Date<span class="style1">* </span> </td>
                          <td><input type="text" name="txtSanctionProceedingToDate" id="txtSanctionProceedingToDate" maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                          />
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmSanctionProceedingsPeriod.txtSanctionProceedingToDate);"
                         alt="Show Calendar"></img> 
                        </tr>
                         <tr class="table">
                
                          <td>Payment Option<span class="style1">* </span> </td>
                          <td><select name="cmbPaymentOption" id="cmbPaymentOption" onchange="setVisible();">
                          <option value="s" >--Select--</option>
                          <option value="M" >Monthly</option>
                          <option value="Q" >Quartely</option>
                            <option value="H" >Half-Yearly</option>
                             <option value="A" >Annually</option>
                          </select> </td>
                        </tr>
                        <tr class="table">
                        <td>Payment Period1<span class="style1">* </span> </td>
                        <td><select name="txtPaymentPeriod1" id="txtPaymentPeriod1" >
                         <option value="0">--Select--</option>
                                     <option value="01">Jan</option>
                                     <option value="02">Feb</option>
                                     <option value="03">Mar</option>
                                     <option value="04">Apr</option>
                                     <option value="05">May</option>
                                     <option value="06">June</option>
                                     <option value="07">Jul</option>
                                     <option value="08">Aug</option>
                                     <option value="09">Sep</option>
                                     <option value="10">Oct</option>
                                     <option value="11">Nov</option>
                                     <option value="12">Dec</option>
                                    </select>
                        </td>
                        </tr>
                         <tr class="table">
                        <td>Payment Period2<span class="style1">* </span> </td>
                        <td>
                      <select name="txtPaymentPeriod2" id="txtPaymentPeriod2" >
                         <option value="0">--Select--</option>
                                     <option value="01">Jan</option>
                                     <option value="02">Feb</option>
                                     <option value="03">Mar</option>
                                     <option value="04">Apr</option>
                                     <option value="05">May</option>
                                     <option value="06">June</option>
                                     <option value="07">Jul</option>
                                     <option value="08">Aug</option>
                                     <option value="09">Sep</option>
                                     <option value="10">Oct</option>
                                     <option value="11">Nov</option>
                                     <option value="12">Dec</option>
                                    </select>
                        </td>
                        </tr>
                          <tr class="table">
                        <td>Payment Period3<span class="style1">* </span> </td>
                        <td>
                      <select name="txtPaymentPeriod3" id="txtPaymentPeriod3" >
                         <option value="0">--Select--</option>
                                     <option value="01">Jan</option>
                                     <option value="02">Feb</option>
                                     <option value="03">Mar</option>
                                     <option value="04">Apr</option>
                                     <option value="05">May</option>
                                     <option value="06">June</option>
                                     <option value="07">Jul</option>
                                     <option value="08">Aug</option>
                                     <option value="09">Sep</option>
                                     <option value="10">Oct</option>
                                     <option value="11">Nov</option>
                                     <option value="12">Dec</option>
                                    </select>
                        </td>
                        </tr>
                       
                         <tr class="table">
                        <td>Payment Period4<span class="style1">* </span> </td>
                         <td>
                      <select name="txtPaymentPeriod4" id="txtPaymentPeriod4" >
                         <option value="0">--Select--</option>
                                     <option value="01">Jan</option>
                                     <option value="02">Feb</option>
                                     <option value="03">Mar</option>
                                     <option value="04">Apr</option>
                                     <option value="05">May</option>
                                     <option value="06">June</option>
                                     <option value="07">Jul</option>
                                     <option value="08">Aug</option>
                                     <option value="09">Sep</option>
                                     <option value="10">Oct</option>
                                     <option value="11">Nov</option>
                                     <option value="12">Dec</option>
                                    </select>
                        </td>
                        </tr>
                       
                   <tr class="table">
                
                          <td>Sanction Amount Per Payment<span class="style1">* </span> </td>
                          <td><input type="text" name="txtSanctionAmount" id="txtSanctionAmount" maxlength="20" onkeypress="return limit_amt(this,event);"onblur="getFinalAmount();"></input></td>
                        </tr>
                       
                        <tr class="table">
                <td>
                    <div align="left">
                       AccountHeadCode & Des
                    </div>
                </td>
                  <td>
                  <div align="left">
                    <input type="text" name="txtAcc_HeadCode" 
                           id="txtAcc_HeadCode" maxlength="6" 
                            onkeypress="return numbersonly(event)"
                            onchange="sixdigit();" 
                            onblur="doFunction('checkCode','null'),getAmount()"  size="9"/>
                    <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountHeadList"
                             onclick="AccHeadpopup();"></img>
                    <input type="text" name="txtAcc_HeadDesc" readonly="readonly" 
                           id="txtAcc_HeadDesc" style="background-color: #ececec"/>
                           
                  </div>
               <div style="display:none">
                                        <select name="cmbSL_type" id="cmbSL_type" ></select>
                                        <select name="cmbSL_Code" id="cmbSL_Code"></select>
                                       </div>
</tr>

               <tr class="table">
                          <td>Budget provided<span class="style1">* </span> </td>
                          <td><input type="text" name="txtBudgetProvided" id="txtBudgetProvided" maxlength="30" readonly="readonly"  onkeypress="return limit_amt(this,event);"> </input></td>
                        </tr>
                       
                        <tr class="table">
                          <td>Budget So far Spent<span class="style1">* </span> </td>
                          <td><input type="text" name="txtBudgetSoFar" id="txtBudgetSoFar" maxlength="20" readonly="readonly" onkeypress="return limit_amt(this,event);"></input></td>
                        </tr>
                         <tr class="table">
                          <td>Accounting unit in which the payment to be made<span class="style1">* </span> </td>
                          <td><input type="text" name="txtMade" id="txtMade" maxlength="20"></input></td>
                        </tr>
                        <tr class="table">
                          <td>Total Sanction Amount For The Applicable Period<span class="style1">* </span> </td>
                          <td><input type="text" name="txtAmount" id="txtAmount" maxlength="20" readonly="readonly"   onkeypress="return limit_amt(this,event);"></input></td>
                        </tr>
                        
                        <tr class="table">
                          <td>Remarks<span class="style1">* </span> </td>
                          <td> <textarea rows="3" cols="25" name="mtxtRemarks" id="mtxtRemarks"></textarea>                          </td>
                        </tr>
                            <tr class="table">
      <td colspan="2" class="table">&nbsp;</td>
    </tr>
                         
                        <tr class="table">
                                   
                                    <td colspan="2" class="tdH">
                                            <div align="center">
                                              <input type="button" name="onsubmit" value="Submit" id="onsubmit"  onClick="add();"  />   
                                              <input type="button" name="onexit" value="EXIT" id="onexit" onClick="exitfun();" />                                   
                                            </div></td> 
                        </tr>
  </table>
  </div>
  

 
    
</form>
</body>
</html>