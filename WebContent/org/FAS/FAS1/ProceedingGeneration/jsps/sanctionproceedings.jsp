<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Sanction Proceedings</title>

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
   
     <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
   
    
    <script type="text/javascript"   src="../scripts/SanctionProceeding.js"></script>
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
             document.frmSanctionProceedings.txtRefDate.value=day+"/"+month+"/"+year;
             document.frmSanctionProceedings.txtRefDate1.value=day+"/"+month+"/"+year;
             document.frmSanctionProceedings.cmbCashBookYear.value=year;
           
             document.frmSanctionProceedings.cmbCashBookMonth.value=month;

        }
</script>
    
</head>
<body onload="loadDate(),LoadAccountingUnitID('LIST_ALL_UNITS'),loadmajortype(),loaddes();">
<table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Sanction Proceedings </font>
          </div>
        </td>
      </tr>
    </table>
<form name="frmSanctionProceedings" id="frmSanctionProceedings">
 
 
                    
 <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
          <h2 class="tab" >General </h2>
           
          <div align="center">
 <table width="100%" cellspacing="2" border="1">
     <tr class="table">
      <td colspan="2" class="tdH"><div align="center"><strong>Sanction Proceedings</strong> </div></td>
    </tr>
                        <tr class="table">
                                    <td width="257">Accounting Unit Name<span class="style1">* </span></td>
                                    <td width="291"> <select name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);"> </select></td>             
                        </tr>
                        <tr class="table">
                          <td>Accounting Unit Office Name<span class="style1">* </span> </td>
                          <td><select name="cmbOffice_code" id="cmbOffice_code"></select></td>
                        </tr>
                        
                          <tr class="table">
                                    <td width="257">Year<span class="style1">* </span></td>
                                    <td width="291"> <select name="cmbCashBookYear" id="cmbCashBookYear" style="width:153px">
                                    <option value="s">--Select--</option>
                                    <option value="2009">2009</option>
                                    <option value="2010">2010</option>
                                    </select></td>             
                        </tr>
                        
                        <tr class="table">
                                    <td width="257">Month<span class="style1">* </span></td>
                                    <td width="291"> <select name="cmbCashBookMonth" id="cmbCashBookMonth">
                                    <option value="s">--Select--</option>
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
                          <td>Ref.No<span class="style1">* </span> </td>
                          <td><input type="text" name="txtRefNo" id="txtRefNo" maxlength="20" onkeypress="return numbersonly(event);"></input></td>
                        </tr>
                          <tr class="table">
                          <td>Ref.Date<span class="style1">* </span> </td>
                          <td><input type="text" name="txtRefDate" id="txtRefDate"  maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="call_date(this);"/>
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmSanctionProceedings.txtRefDate);"
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
                          <td>Sanction Proceeding Date<span class="style1">* </span> </td>
                          <td><input type="text" name="txtSanctionProceedingDate" id="txtSanctionProceedingDate" maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                          />
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmSanctionProceedings.txtSanctionProceedingDate);"
                         alt="Show Calendar"></img> 
                        </tr>
                   <tr class="table">
                          <td>Total Sanction Amount<span class="style1">* </span> </td>
                          <td><input type="text" name="txtSanctionAmount" id="txtSanctionAmount" maxlength="20" onkeypress="return limit_amt(this,event);"></input></td>
                        </tr>
                        
                         <tr class="table">
                          <td>Sanction Amount Common to All Payee<span class="style1">* </span> </td>
                            <td> <input type="radio" name="r3" id="r3" value="Y" Checked="checked">Yes <input type="radio" name="r3" id="r3" value="N"/>No
                          
                          </td>
                        </tr>
                       
                        <tr class="table">
                          <td>If Yes Amount<span class="style1">* </span> </td>
                          <td><input type="text" name="txtSanctionAmountCommon" id="txtSanctionAmountCommon" maxlength="20" onkeypress="return limit_amt(this,event);"></input></td>
                        </tr>
                       
                       <tr class="table">
                          <td>Recovery From Salary Or Pension<span class="style1">* </span> </td>
                            <td> <input type="radio" name="r4" id="r4" value="Y" >Yes <input type="radio" name="r4" id="r4" value="N"  Checked="checked"/>No
                          
                          </td>
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
                          <td>Payment Amount After This Bill<span class="style1">* </span> </td>
                          <td><input type="text" name="txtAmount" id="txtAmount" maxlength="20" readonly="readonly" onkeypress="return limit_amt(this,event);"></input></td>
                        </tr>
                        
                         <tr class="table">
                          <td> Amount Deducted For This Bill<span class="style1">* </span> </td>
                          <td><input type="text" name="txtAmountDeduct" id="txtAmountDeduct" maxlength="20"  onkeypress="return limit_amt(this,event);"></input></td>
                        </tr>
                         <tr class="table">
                          <td>Accounting unit in which the payment to be made<span class="style1">* </span> </td>
                          <td><input type="text" name="txtMade" id="txtMade" maxlength="20"></input></td>
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
  </div>

 
    <div class="tab-page" id="gd" >
          <h2 class="tab" > Details</h2>
           
          <div align="center">
  <table  width="100%" border="1">
  <tr class="table">
      <td colspan="2" class="tdH"><div align="center"><strong> Details</strong> </div></td>
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
                        <input type="text" name="txtEmpID_mas1"  id="txtEmpID_mas1"  maxlength="6" size="6" onchange="emp_sanction_approvedby();" onkeypress="return numbersonly(event)"/>
                  </div> 
                  
                
                </td>
             </tr>
                
                
            
             
                    
                            <tr class="table"> 
                          <td>Approve?<span class="style1">* </span> </td>
                          <td> <input type="radio" name="r2" id="r2" value="Regular" Checked="checked">Regular <input type="radio" name="r2" id="r2" value="Advance"/>Advance
                          
                          </td>
                        </tr>
             
             
                          <tr class="table">
                          <td>Sanction Amount<span class="style1">* </span> </td>
                          <td><input type="text" name="txtSanctionAount1" id="txtSanctionAount1" maxlength="20" onkeypress="return limit_amt(this,event);"></input>   </tr>
                        
                              <tr class="table">
                          <td>Ref No<span class="style1">* </span> </td>
                          <td><input type="text" name="txtRefNo1" id="txtRefNo1" maxlength="20" onkeypress="return numbersonly(event);"></input></td>
                        </tr>
                          
                          
                          <tr class="table">
                          <td>Ref Date<span class="style1">* </span> </td>
                          <td><input type="text" name="txtRefDate1" id="txtRefDate1"maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                          />
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmSanctionProceedings.txtRefDate1);"
                         alt="Show Calendar"></img> 
                        </tr>
                        
                        
                     
                       <tr class="table">
                          <td>Total Instalments<span class="style1">* </span> </td>
                          <td><input type="text" name="txtinstalment" id="txtinstalment" maxlength="20" onkeypress="return numbersonly(event);"></input></td>
                        </tr>      
                    
                     <tr class="table">
                          <td>Recovery Start Month<span class="style1">* </span> </td>
                          <td><input type="text" name="txtstartMonth" id="txtstartMonth" maxlength="20" ></input></td>
                        </tr>      
                        <tr class="table">
                          <td>Residual Amount<span class="style1">* </span> </td>
                          <td><input type="text" name="txtresidualAmount" id="txtresidualAmount" maxlength="20" onkeypress="return numbersonly(event);"></input></td>
                        </tr>   
                         
                           <tr class="table">
                          <td>Residual Amount Deduction Instl No<span class="style1">* </span> </td>
                          <td><input type="text" name="txtDeductionAmount" id="txtDeductionAmount" maxlength="20" onkeypress="return numbersonly(event);"></input></td>
                        </tr>  
                         
                         
                           <tr class="table">
                          <td>Particulars<span class="style1">* </span> </td>
                          <td> <textarea rows="3" cols="25" name="mtxtRemarks1" id="mtxtRemarks1"></textarea>                          </td>
                        </tr>
                        
                        
                        
                   
                        
                        
                         <tr class="table">
                                   
                                    <td colspan="2" class="tdH">
                                            <div align="center">
                                              <input type="button" name="onsubmit1" value="Submit" id="onsubmit1" onClick=" add1();" />  
                                              <input type="button" name="onUpdate" value="Update" id="onUpdate" onClick="update1();" />  
                                              <input type="button" name="Delete1" value="Delete" id="Delete1" onClick="delete_GRID();" />   
                                              <input type="button" name="onexit" value="EXIT" id="onexit" onClick="exitfun();" />                                   
                                            </div></td> 
                        </tr>
  
  
  </table>
    </div>
       
     <div align="center" style="display:block" id="div1">
                <table cellspacing="3" cellpadding="2" border="1" width="100%" align="center" >
                        <tr class="table">
                                    <td align="center" class="tdH"> 
                                            <b>EXISTING DETAILS </b>
                                    </td>
                        </tr>
                </table>
                <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
                        <tr class="table">
                                    
                                     <th width="5%">Edit</th>
                                     <th width="12%">payee Type</th>
                                      <th width="12%">payee Code</th>
                                     <th width="12%">payee Name</th>
                                      <th width="14%">Approval</th>
                                     <th width="12%">Sanction Amount</th>
                                     <th width="12%">Ref No</th>
                                     <th width="12%">Ref Date</th>
                                      <th width="12%">Total Instalments</th>
                                     <th width="12%">Recovery Start Month</th>
                                     <th width="12%">Residual Amount</th>
                                     <th width="12%">Residual Amount Deduction Instl No</th>
                                      <th width="12%">Particulars</th>
                                          
                        </tr>
                <tbody id="tblList" align="center">
                 </tbody>
  </table> 
       
 

  </div>
 </div>
 </div>
</form>
</body>
</html>