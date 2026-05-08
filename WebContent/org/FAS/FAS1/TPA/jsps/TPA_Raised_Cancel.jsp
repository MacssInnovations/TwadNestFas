<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd"> 
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html> 
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <meta http-equiv="cache-control" content="no-cache">
    <title>Transfer Proforma Accounting System (Credit / Debit) </title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>  
    

    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    
    
    <script type="text/javascript" src="../scripts/Common_ReceiptType.js"></script>
    
    <script type="text/javascript" src="../scripts/TPA_Raised_Cancel.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
      
    <!-- to avoid future date the above script used-->
    <script type="text/javascript" language="javascript">
         function foc()
         {
         }
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
            document.frm_TPA_Raised_Cancel.Voucher_Date.value=day+"/"+month+"/"+year;
          
            
         }
</script>


 </head>
 <body onload="clrForm('load');loadDate();foc();LoadAccountingUnitID('LIST_ALL_UNITS');loadAccountHead();loadTransferUnit();" bgcolor="rgb(255,255,225)">
  
    <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Transfer Proforma Accounting System (Credit / Debit) </font>
          </div>
        </td>
      </tr>
    </table>
    
    
    <form name="frm_TPA_Raised_Cancel" id="frm_TPA_Raised_Cancel" method="POST" action="../../../../../TPA_Raised_Cancel?Command=Cancel" onsubmit="return checkNull()">
   
      <div class="tab-pane" id="tab-pane-1">
        <!-- 1st Tab General Starts --> 
        <div class="tab-page">
          <h2 class="tab" >General </h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="0" width="100%">
           
                <tr class="tdTitle">
                <td colspan="2">
                  <div align="left">
                    <strong>General Details</strong>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);">
                    </select>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Accounting For Office Code
                    <font color="#ff2121">*</font>
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
                  <div align="left">
                     Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="Voucher_Date" id="Voucher_Date" tabindex="3" 
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';" onkeypress="return calins(event,this);"
                           onblur="call_date(this);" onchange="resetType();"/>
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frm_TPA_Raised_Cancel.Voucher_Date,1);"
                         alt="Show Calendar"></img>                   
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">Transfer Proforma Type <font color="#ff2121">*</font> </div>
                </td>
                <td>
                  <div align="left">                  
                    <input id="Org_CR_DR" type="radio" value="CR" checked="checked" name="Org_CR_DR" onclick="doFunction_voucher('load_Voucher_No')"/>Credit     
                    <input id="Org_CR_DR" type="radio" value="DR" name="Org_CR_DR" onclick="doFunction_voucher('load_Voucher_No')"/>Debit                                
                  </div>
                </td>
              </tr>
              
               <tr class="table">
                <td>
                  <div align="left">
                    Transfer Proforma Advice Number <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left" >
                  	<select name="adviceNumber" id="adviceNumber" onchange="doFunction_voucher('load_Voucher_Details')">
                  		<option value="">Select Advice No</option>
                  	</select>
                  </div>
                </td>
              </tr>
              
              
              <tr class="table">
                <td>
                  <div align="left">Accounting Unit to which the accounts are transfered <font color="#ff2121">*</font> </div>
                </td>
                <td>
                  <div align="left">
                     <select name="TransferedID" id="TransferedID" >
                      <option value="" >-- Select Accounting Unit ID --  </option>                    
                    </select>                   
                  </div>
                </td>
              </tr>
                
        
              <tr class="table">
                <td>
                  <div align="left">
                     Reason for Transfer <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select name="Reason4Trf" id="Reason4Trf" >
                      <option value="" >Select Reason </option>
                      <option value="Closure" >Closure </option>
                      <option value="Shift" >Shift </option>
                      <option value="Re-style" >Re-style </option>
                      <option value="Redeployment" >Redeployment </option>
                      <option value="Others" >Others</option>
                    </select>                   
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">Particulars</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="GenParticulars" id="GenParticulars" cols="50" tabindex="8" onkeypress="return check_leng(this.value,'remarks');"
                              rows="4"></textarea>
                  </div>
                </td>
              </tr>
          
            </table>
          </div>
        </div>  <!-- 1st General Tab Ends --> 
         
         
         
        <!-- Second TPA Tab Starts --> 
        
        <div class="tab-page" id="gd" >
          <h2 class="tab" > TPA </h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="0" width="100%">
              <tr>
                <td colspan="2">
                  <div id="sub_ledge_dis" >
              
                    <table cellspacing="1" cellpadding="2" border="0" width="100%">
                      <tr class="tdTitle">
                        <td colspan="2">
                          <div align="left">
                            <strong> Details</strong>
                          </div>
                        </td>
                      </tr>
              
               <tr class="table">
                <td>
                  <div align="left">
                    Account Head Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAcc_HeadCode" 
                           id="txtAcc_HeadCode" maxlength="6" 
                            size="9" readonly="readonly"/>                   
                    <input type="text" name="txtAcc_HeadDesc" readonly="readonly" 
                           id="txtAcc_HeadDesc" style="background-color: #ececec"  maxlength="125" size="70"/>
                  </div>
                </td>
              </tr>
             
             
             <tr class="table">
                <td width="40%">
                  <div align="left">
                            SubLedger Type
                          </div>
                </td>
                <td width="60%">
                  <div align="left">
                    <select size="1" name="cmbSL_type" id="cmbSL_type" onchange="doFunction('Load_SL_Code',this.value);">
                      <option value="">--Select SL Type--</option>
                     
                    </select>
                  </div>
                  <div align="left" style="display:none">
                    <select size="1" name="cmbMas_SL_type" id="cmbMas_SL_type" >
                      <option value="">--Select Type--</option>			                     
                    </select>
                  </div>
                </td>
              </tr>
             
              <tr class="table">
                <td>
                  <div align="left">
                      SubLedger Code
                  </div>
                </td>
                <td>
                 <table align="left">
                                 <tr align="left">
                                 <td>
                  <div align="left">
                        <select size="1" name="cmbSL_Code" id="cmbSL_Code" onchange="doFunction('Load_SL_Code',this.value);" >
                      			<option value="">--Select SL Code --</option>
                    	</select>            
                  </div>
                  <div align="left" style="display:none">
	                    <select  name="cmbMas_SL_Code" id="cmbMas_SL_Code" >
	                      		<option value="">--Select Type--</option>
	                     </select>
	              </div>
              </td>
             <td>
                  <div align="left" id="offlist_div_trans"  style="display:none">
                         <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList" onclick="jobpopup_trans();"></img>
                         <input type="text" name="txtOfficeID_trs" id="txtOfficeID_trs" maxlength="4" size="5"    onblur="trs_office(this.value);" />
                         <input type="hidden" name="txtOfficeID_mas" id="txtOfficeID_mas" maxlength="4" size="5"    />
                  </div>
                  <div align="left" id="emplist_div_trans"  style="display:none">
                         <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="employee_popup_trans();"></img>
                         <input type="text" name="txtEmpID_trs" id="txtEmpID_trs" maxlength="5" size="5"  onchange="trs_employee(this.value);" />                                                                         
                 </div>
                </td>		
                
                                </tr>
                          </table>
                        </td>
                      </tr>
              
              
              
              <tr class="table">
                <td>
                  <div align="left">
                     Amount 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="Amount" readonly="readonly" onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);account_head_code()"
                           id="Amount" maxlength="17" size="18"/>
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">
                    CR/ DR <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="Indi_CR_DR" id="Indi_CR_DR" 
                           checked="checked" value="CR"/>Credit &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="Indi_CR_DR" id="Indi_CR_DR" 
                           value="DR"/>Debit  &nbsp;&nbsp;&nbsp;&nbsp;    
                  </div>
                </td>
              </tr>
              
              
              <tr class="table">
                <td>
                  <div align="left">
                    Particulars 
                          </div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="DetParticular" id="DetParticular" cols="70" onkeypress="return check_leng(this.value,'particulars');"
                              rows="3"></textarea>
                  </div>
                </td>
              </tr>
              
               </table>
             </div>
                  
                </td>
              </tr>
            </table>
          </div>
        </div>  <!-- 2nd TPA tab ends --> 
        
        
        
        
        
           <!-- 3rd Detail Tab Starts --> 
        
        <div class="tab-page" id="gd" >
          <h2 class="tab" > SL Details </h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="0" width="100%">
              <tr>
                <td colspan="2">
                  <div id="sub_ledge_dis" >
              
              
              
                  <table cellspacing="1" cellpadding="2" border="0" width="100%">
                    
                  <tr class="tdTitle">
                    <td colspan="6">
                      <div align="left">
                        <strong> Details</strong>
                      </div>
                    </td>
                  </tr>
               
              
              
               <tr class="table">
               
                <th>
                  <div align="left">
                    Account Head Code & Decs                   
                  </div>
                </th>
                
                <th>
                  <div align="left">
                     SL Type
                  </div>
                </th>
               
                <th>
                  <div align="left">
                     SL Code
                  </div>
                </th>
               
                <th>
                  <div align="left">
                    CR/DR                  
                  </div>
                </th>
                
                <th>
                  <div align="left">
                    Amount
                  </div>
                </th>
               
               <th>
                  <div align="left">
                    Particulars
                  </div>
                </th>
               
               
              </tr>
              <tbody id="grid_body" class="table" align="left" >
              </tbody>
               </table>
             </div>
                  
                </td>
              </tr>
            </table>
          </div>
        </div>  <!-- 3rd Detail tab ends --> 
        
         <!-- 4th Detail Tab Starts --> 
         <div class="tab-page" id="gdgl" >
          <h2 class="tab" > GL Details </h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="0" width="100%">
              <tr>
                <td colspan="2">
                  <div id="sub_ledge_dis_GL" >
              
              
              
                  <table cellspacing="1" cellpadding="2" border="0" width="100%">
                    
                  <tr class="tdTitle">
                    <td colspan="6">
                      <div align="left">
                        <strong> Details</strong>
                      </div>
                    </td>
                  </tr>
               
              
              
               <tr class="table">
               
                <th>
                  <div align="left">
                    Account Head Code & Decs                   
                  </div>
                </th>
                
                <th>
                  <div align="left">
                     SL Type
                  </div>
                </th>
               
                <th>
                  <div align="left">
                     SL Code
                  </div>
                </th>
               
                <th>
                  <div align="left">
                    CR/DR                  
                  </div>
                </th>
                
                <th>
                  <div align="left">
                    Amount
                  </div>
                </th>
               
               <th>
                  <div align="left">
                    Particulars
                  </div>
                </th>
               
               
              </tr>
              <tbody id="gl_grid_body" class="table" align="left" >
              </tbody>
               </table>
             </div>
                  
                </td>
              </tr>
            </table>
          </div>
        </div>  <!-- 4th Detail tab ends --> 
        
        
      </div>  <!-- Main Tag -->
      
      
      
      
      
      
      
      <br>
      
      
      
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
                       onclick="javascript:self.close();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
      
      
      
    </form></body>
</html>