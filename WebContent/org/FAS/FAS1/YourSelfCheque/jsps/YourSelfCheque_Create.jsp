<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

 
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>


<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <meta http-equiv="cache-control" content="no-cache">
    <title>YourSelf Cheque Create</title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
   
    
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>  
    
    <script type="text/javascript" src="../scripts/Common_ReceiptType.js"></script>    
    <script type="text/javascript" src="../scripts/YourSelfCheque_Create.js"></script>    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Cheque_Number_Check_forPAY.js"></script>
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
            document.YourSelfChequeForm.txtCrea_date.value=day+"/"+month+"/"+year;
            
            //call_date(document.YourSelfChequeForm.txtCrea_date);
            
          //  setTimeout(' LoadChequeNumber()',900);
           
            
            
            
         }
        
     
</script>
  </head>
  <body onload="clrForm();loadDate();foc();LoadAccountingUnitID('LIST_ALL_UNITS');" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">YourSelf Cheque Create</font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="YourSelfChequeForm" id="YourSelfChequeForm" method="POST"
                  action="../../../../../YourSelfCheque_Create.kv?Command=Add"
                  onsubmit="return checkNull()">
  
  
  
 
      <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
          <h2 class="tab" >General </h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="0" width="100%">
              
              
              <tr class="table">
                <td>
                  <div align="left" >
                  	  Accounting Unit Code  <font color="#ff2121">*</font>
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
                    Accounting For Office Code <font color="#ff2121">*</font>
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
                    Date <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCrea_date" id="txtCrea_date" tabindex="3" 
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"  
                          
                           onblur="return checkdt(this);"> 
                        <!--    onblur="call_date(this);"  -->                           
                          
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.YourSelfChequeForm.txtCrea_date,0);"
                         alt="Show Calendar"></img> 
                  
                  </div>
                </td>
              </tr>              
              
              
              <tr class="table">
                <td>
                  <div align="left">
                     Voucher Number  <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left" >
                    <input type="hidden" name="txtReceipt_No" id="txtReceipt_No"  
                    style="background-color: #ececec"  readonly="readonly" size="6" maxlength="5"/> (System Generated)
                  </div>
                </td>
              </tr>
              
              
              <tr class="table">
                <td>
                  <div align="left">Bank Account Number  <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                      <select size="1" name="cmbBankAccNo" id="cmbBankAccNo"  >
                         <option value="">--Select Bank Account Number --</option>
                      </select> 
                      <input type="button" name="Go" id="Go" value="Go" onclick="LoadBankAccountNumber()"/>                    
                  </div>
                </td>
              </tr>
                                          
          <!--     
              <tr class="table">
                <td>
                  <div align="left">Bank Name</div>
                </td>
                <td>
                  <div align="left">                     
                    <input type="text" name="txtBankName" tabindex="9" id="txtBankName" />
                  </div>
                </td>
              </tr>              
          -->    
              
              <tr class="table">
                <td>
                  <div align="left">
                     Whether DD is obtained from Bank <font color="#ff2121">*</font>                  
                  </div>
                </td>
                <td>
                  <div align="left">                  
                    <input type="radio" name="txtDDObtained" id="txtDDObtained" 
                             value="Y" checked="checked"/>YES &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtDDObtained" id="txtDDObtained" 
                           value="N" onchange="Details_Optional()" />NO &nbsp;&nbsp;&nbsp;&nbsp;            
                  </div>
                </td>
              </tr>
              
              
              
              
             <tr class="table">
                <td width="40%">
                  <div align="left">
                    Cheque Date  <font color="#ff2121">*</font>
                  </div>                  
                </td>
                <td width="60%">
                  <div align="left">
                    <input type="text" name="txtChequeDate" id="txtChequeDate" tabindex="3" 
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"  />                           
	                       <!--    onblur="call_date(this);"  -->                        
                          
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.YourSelfChequeForm.txtChequeDate,0);"
                         alt="Show Calendar"></img> 
                  </div>
                </td>
              </tr>        
               
               
              <tr class="table">
                <td width="40%">
                  <div align="left">
                    Cheque Number  <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                    
                  <div align="left">
                    <input type="text" name="txtChequeNo" maxlength="10" onkeypress="return numbersonly(event)" 
                    onblur="check_dd_cheque_two(),chequeRange();"
                           id="txtChequeNo" size="11"/>
                  </div>
                </td>
              </tr>
              
              
              
              <tr class="table">
                <td>
                  <div align="left">
                     Cheque Amount  <font color="#ff2121">*</font> 
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtChequeAmt" id="txtChequeAmt"  rows="4" onkeypress="return numbersonly1(event,this)" ></input>
                  </div>
                </td>
              </tr>
              
              
              
             <tr class="table">
                <td>
                  <div align="left">
                      Ref.No.                   
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtRefNo" tabindex="9"  id="txtRefNo" onkeypress="return numbersonly1(event,this)" />
                  </div>
                </td>
              </tr>
              
              
              
              <tr class="table">
                <td>
                  <div align="left">
                     Ref.Date 
                    
                  </div>
                </td>
                <td>
                  <div align="left">                  
                    <input type="text" name="txtRefDate" id="txtRefDate" tabindex="3" 
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"  />                           
	                       <!--    onblur="call_date(this);"  -->                         
                          
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.YourSelfChequeForm.txtRefDate,0);"
                         alt="Show Calendar"></img> 
                         
                  </div>
                </td>
              </tr>
              
              
              
             <tr class="table">
                <td>
                  <div align="left" >
                    Remarks   
                  </div>
                </td>
                <td>
                  <div align="left">                     
                     <textarea rows="4"  tabindex="8" cols="50" id="txtRemarks" name="txtRemarks"></textarea>
                     <!--  onkeypress="return check_leng(this.value,'remarks');"  -->  
                     
                  </div>
                </td>
              </tr> 
              

            </table>
          </div>
        </div>
        
        
        
        
        
        <input type='hidden' name='RecordCount' id='RecordCount' value='0' /> 
        
        
        
        
        
        
         
        <div class="tab-page" id="gd" >
          <h2 class="tab" > Details</h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="0" width="100%">
            
               
               <tr class="table">
                <td>
                  <div align="left">
                    DD Number 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">                   
                    <input type="text" name="txtDDNumber" id="txtDDNumber" maxlength="17" size="18" onkeypress="return numbersonly1(event,this)" />                   
                  </div>
                </td>
              </tr>
             
               
               <tr class="table">
                <td>
                  <div align="left">
                   DD Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtDDDate" id="txtDDDate" tabindex="3" 
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"  />                           
                        <!--    onblur="call_date(this);"  -->                           
                          
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.YourSelfChequeForm.txtDDDate,0);"
                         alt="Show Calendar"></img> 
                  
                  </div>
                </td>
              </tr>
              
              
              <tr class="table">
                <td width="40%">
                  <div align="left">DD Amount <font color="#ff2121">*</font> </div>
                </td>
                <td width="60%">
                  <div align="left">
                       <input type="text" name="txtDDAmount" onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);"
                              id="txtDDAmount" maxlength="17" size="18"/>
                   
                  </div>
                </td>
              </tr>
              
              
              
              <tr class="table">
                <td width="40%">                   
                  <div align="left">
                     DD in Favour of  <font color="#ff2121">*</font>
                  </div>
                </td>
                <td width="60%">
					<div align="left">                      
                      <textarea rows="4" 
                                tabindex="8" cols="50" id="txtDDFavourOf" name="txtDDFavourOf"></textarea>
                                
                                <!--onkeypress="return check_leng(this.value,'favour');"  -->
                    </div>            
           	    </td>
              </tr>              
              
             
             
              <tr class="table">
                <td>
                  <div align="left">
                      Commission Charge  
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCommissionCharge" onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);"
                           id="txtCommissionCharge" maxlength="17" size="18"/>
                  </div>
                </td>
              </tr>
              
              
               <tr class="tdTitle">
                        <td colspan="2" height="23">
                         <div align="center">
                            <table border="0">
                          <tr><td>
                          <input type="button" name="cmdadd" id="cmdadd" 
                                 value="ADD" onclick="ADD_GRID()" style="display:block"/></td>
                          <td>
                          <input type="button" name="cmdupdate" value="UPDATE"
                                 id="cmdupdate" onclick="update_GRID()"
                                 style="display:none"/></td>
                          <td><input type="button" name="cmddelete" value="DELETE"
                                 id="cmddelete" onclick="delete_GRID()"
                                 disabled="disabled"/></td>
                          <td><input type="button" name="cmdclear" value="CLEAR ALL"
                                 id="cmdclear" onclick="clearall()"/></td>
                        </tr>
                        </table>
                        </div>
                        </td>
                      </tr>
                    </table>
                  </div>
                  <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="0" width="100%">
                      
                      <tr class="table">
                        <th>Select</th>
                        <th>DD Number</th>
                        <th>DD Date</th>
                        <th>DD Amount </th>
                        <th>DD in Favour of </th>                        
                        <th>Commission Charge</th>                        
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
               <input type="button" name="butCan" id="butCan" value="CANCEL" onclick="clrForm();"/>
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="EXIT" onclick="exit();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </form></body>
</html>