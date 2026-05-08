<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <meta http-equiv="cache-control" content="no-cache">
    <title> TPA (Credit/Debit) Accepting System </title>
    
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
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Cheque_Number_Check_forPAY.js"></script>		   		 				    		
    <script type="text/javascript" src="../scripts/TPA_Acceptance_Create.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>  
   
     <!-- to avoid future date the above script used-->
    <script type="text/javascript" language="javascript">
        function loadDate()
        {
        	 	 var today= new Date(); 
                 var day=today.getDate();
                 var month=today.getMonth();
                 month=month+1;
                 document.TPA.txtCB_Month.value=month;
                 if(day<=9 && day>=1)
                 day="0"+day;
                 if(month<=9 && month>=1)
                 month="0"+month;
                 var year=today.getYear();
                 if(year < 1900) year += 1900;
                 document.TPA.txtCreate_Date.value=day+"/"+month+"/"+year;   
                 document.TPA.txtCB_Year.value=year; 
                 //alert(month);                   
                 
        }
	</script>
  </head>
   
  <body onload="clrForm('load');loadDate();LoadAccountingUnitID('LIST_ALL_UNITS');" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">TPA (Credit/Debit) Accepting System</font>
          </div>
        </td>
      </tr>
    </table>
   
  <form name="TPA" id="TPA" method="POST"
                  action="../../../../../TPA_Acceptance_Create?Command=Add" onsubmit="return checkNull()">
      <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
          <h2 class="tab" > General </h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              
             <tr class="table">
                    <td>
                      <div align="left" >
                              Accounting Unit Code  <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);">        
                         </select>
                      </div>
                    </td>
             </tr>


             <tr class="table">
                    <td>
                      <div align="left">
                        Accounting For Office Code <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <select size="1" name="cmbOffice_code" id="cmbOffice_code" >
                          
                        </select>
                      </div>
                    </td>
             </tr>
             
              <tr class="table">
	            <td>
	              <div align="left">Cash Book Year &amp; Month <font color="#ff2121">*</font></div>
	            </td>
	            <td>
	              <div align="left">
	                <input type="text" name="txtCB_Year" id="txtCB_Year"
	                       tabindex="3" maxlength="4" size="5"
	                       onkeypress="return numbersonly(event)" onchange="resetMonth();"></input>	                 
	                <select name="txtCB_Month" id="txtCB_Month" tabindex="4" onchange="resetType();">
	                  <option value="">Select Month</option>
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
                  <div align="left">Transfer Proforma Type <font color="#ff2121">*</font> </div>
                </td>
                <td>
                  <div align="left">                  
                    <select id="Org_CR_DR" name="Org_CR_DR" onchange="loadVoucher()" >
                        <option value="">Select Type</option>
                    	<option value="CR">Credit</option> 
                    	<option value="DR">Debit</option>
                    </select>                                            
                  </div>
                </td>
              </tr>
             
              
             
             
	          <tr class="table">
                <td>
                  <div align="left">
                    Transfer Proforma Credit No. <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select name="originated_slno" id="originated_slno" onchange="loadVoucherDetails()">
                    	<option value="">--Select Voucher--</option>
                    	</select>
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                   <td>
                     <div align="left">
                        Transfer Proforma Credit/Debit Head 
                       <font color="#ff2121">*</font>
                     </div>
                   </td>
                   <td>
                     <div align="left">
                       <input type="text" name="txtAcc_HeadCode" id="txtAcc_HeadCode" 
                              maxlength="10" size="11"
                              onfocus="javascript:vDateType='3';" readonly="readonly"/>
                                             
                     </div> 
                   </td>
              </tr>   
              
               <tr class="table">
                <td>
                  <div align="left">
                     Transfer Unit  <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                   
                    	
                    	 <input type="text" name="tranferunitid" id="tranferunitid" size="35"  readonly="readonly"/>
                    	
                  </div>
                </td>
              </tr>
              
              
              <tr class="table">
                   <td>
                     <div align="left">
                        Reason for Transfer 
                       <font color="#ff2121">*</font>
                     </div>
                   </td>
                   <td>
                     <div align="left">
                       <input type="text" name="txtReason" id="txtReason" 
                              maxlength="10" size="11"
                              onfocus="javascript:vDateType='3';" readonly="readonly"
                       />
                       <input type="hidden" name="originated_date" id="originated_date"/>
                     </div> 
                   </td>
              </tr>   
              
              <tr class="table">
                   <td>
                     <div align="left">
                       Amount
                      
                     </div>
                   </td>
                   <td>
                     <div align="left">
                       <input type="text" name="amount" id="amount"  maxlength="10" size="11"  readonly="readonly"/>                                             
                     </div> 
                   </td>
              </tr>   
              
              <tr class="table">
                <td>
                  <div align="left">
                     SL Pushed Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="slpushed_Date" id="slpushed_Date" tabindex="3" 
                           maxlength="10" size="11"  readonly="readonly"
                           onfocus="javascript:vDateType='3';" onchange="resetType()";
                           onkeypress="return calins(event,this);"
                           onblur="call_date(this);"/>
                                        
                  </div>
                </td>
              </tr>
              
               <tr class="table">
                <td>
                  <div align="left">
                     Accepting Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCreate_Date" id="txtCreate_Date" tabindex="3" 
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';" onchange="resetType()";
                           onkeypress="return calins(event,this);"
                           onblur="call_date(this);datevalidation();dateCheck(this);"/>
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.TPA.txtCreate_Date,1);"
                         alt="Show Calendar"></img>                   
                  </div>
                </td>
              </tr>
              
               <tr class="table">
                    <td>
                      <div align="left">Particulars</div>
                    </td>
                    <td>
                      <div align="left">
                        <textarea name="particulars" id="particulars" cols="70" onkeypress="return check_leng(this.value);"
                                  rows="3" ></textarea>
                      </div>
                    </td>
              </tr> 
              
              <tr class="table">
                    <td>
                      <div align="left">Accept Transfer Proforma Credit ? <font color="#ff2121">*</font> </div>
                    </td>
                    <td>
                      <div align="left">
                       		<input type="radio" name="isAccept" id="isAccept" value="Y" checked onclick="checkStatus()"/> Yes <input type="radio" name="isAccept" id="isAccept" value="N" onclick="checkStatus()"/> No
                      </div>
                    </td>
              </tr>
              
              <tr class="table">
                    <td>
                      <div align="left">If No,the reason for not accepting </div>
                    </td>
                    <td>
                      <div align="left">
                        <textarea name="notAccepting" id="notAccepting" cols="70" onkeypress="return check_leng(this.value);"
                                  rows="3" ></textarea>
                      </div>
                    </td>
              </tr> 
              
            </table>
          </div>
        </div>        
        <div class="tab-page" id="gd" >
         <h2 class="tab" > Details</h2>
          <div align="center" >
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr>
                <td colspan="2">
                  <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      
                          <tr class="tdH">
                            <th>Account Head</th>    
                            <th>Sub Ledger Type</th>
                            <th>Sub Ledger Code for Org.journal</th>  
                            <th>Sub Ledger Code for Accep.journal</th> 
                            <th>CR/DR</th>                                                
                            <th>Amount</th>                                                    
                            <th>Particulars </th>
                           
                          </tr>
                       	  <tbody id="grid_body" class="table" align="left" >
                       	  </tbody>
                    </table>
                  </div>
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
    </form></body>
</html>