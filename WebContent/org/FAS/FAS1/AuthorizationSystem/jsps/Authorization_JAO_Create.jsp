<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Authorization System</title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/> 
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/PaymentSystem/scripts/Common_PaymentType.js"></script>
    <script type="text/javascript" src="../scripts/Authorization_JAO_Create.js" ></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>  
    
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script> 
   
    
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
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
            document.frmAuthorization_JAO_Create.txtCrea_date.value=day+"/"+month+"/"+year;
            
       }
        
    
</script>
  </head>
  <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
<%//in onload accounting unit id loading function changed on 18-01-2018 in old function LoadAccountingUnitID('BOTH_UNITS_AND_OFFICES') New Function is LoadAccountingUnitID_Create('LIST_ALL_UNITS')%>
  <body onload="call_clr();loadDate();foc();LoadAccountingUnitID_Create('LIST_ALL_UNITS')" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Authorization Details </font>
          </div>
        </td>
      </tr>
    </table>
    <form name="frmAuthorization_JAO_Create" id="frmAuthorization_JAO_Create" method="POST"
                  action="../../../../../Authorization_JAO_Create.view?Command=Add"
                  onsubmit="return checkNull()">
               
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
               <tr class="table">
                <td width="40%">
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td width="60%">
                  <div align="left">                    
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice_TPA(this.value);" >
                    
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
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2" >                      
                     
                    </select>
                  </div>
                </td>
              </tr>
             
            <tr class="table">
                <td width="40%">
                  <div align="left">
                    Sub-System type 
                <font color="#ff2121">
                  *
                </font>
              </div>
                </td>
                <td width="60%">
                  <div align="left">
                    <!--  <select size="1" name="cmbSubSystemType" id="cmbSubSystemType" tabindex="3" onchange="doFunction('load_Voucher_No','null');">-->
                     
                     
                     <select size="1" name="cmbSubSystemType" id="cmbSubSystemType" tabindex="3" onchange="doFunction('load_Voucher_No','null');">
                     <option value="">--Select Sub-System Type--</option>
                     <option value="CR">Cash Receipt</option>
                     <option value="BR">Bank Receipt</option>
                      <option value="ADM">Adjustment Memo Raised</option>  
                      <option value="ADM_A">Adjustment Memo Acceptance</option>
                     <option value="CRM"> Cash Remittance Cancel </option> 
                     <option value="BRM"> Bank Remittance Cancel </option> 
                     <option value="FRM"> Fund Remittance Cancel </option> 
                     <option value="CHQD">Cheque Dishonour</option>
                     <option value="CHQC">Cheque Cancellation</option>
                     <option value="CR_S">Cash Receipt Reclassification</option>
                     <option value="BR_S">Bank Receipt Reclassification</option>
                     <option value="BPP">Payment For Adjustment Memo</option>           
                     <option value="BPP">Pending Bills Payment</option>
                     <option value="BPF">Final Heads</option>
                     <option value="NP">Nil Payment</option>
                     <option value="FTH">Fund Transfer ( by HO )</option>
                     <option value="FTO">Fund Transfer ( by Office )</option>
                     <option value="FRH">Fund Receipt ( by HO )</option>
                     <option value="FRO">Fund Receipt ( by Office )</option>
                     <option value="YC">YourSelf Cheque </option>
                     <option value="IBT">Inter-Bank Transfer </option>
                     <option value="SC">Self-Cheque </option>
                     <option value="DCB">DCB Bank Receipt</option>
                     <option value="DCC">DCB Cash Receipt</option>
                     <!-- Other Receipt Joan Added on 31 oct 2014 -->
                       <option value="ODR">Other Receipt</option>
                     <!--Imprest added on 04/03/2011 Dhana -->
                     <option value="IMP">Imprest Payment</option>
                     <option value="IMPR">Imprest Receipt</option>
                     <option value="TMP">Temp.Adv Payment</option>
                     <option value="TMPR">Temp.Adv Receipt</option>
                      <!-- added on  11/03/2011-->
                     <option value="TDAO">TDA Originating</option>
                     <option value="TCAO">TCA Originating</option>
                     <option value="TDAOB">TDA Originating Before JVR</option>
                     <option value="TCAOB">TCA Originating Before JVR</option>
                     <option value="TDAA">TDA Accepting</option>
                     <option value="TCAA">TCA Accepting</option>
                     <option value="TCAAB">TCA Accepting Before JVR</option>
                     <option value="TDAAB">TDA Accepting Before JVR</option>
                     <option value="TDAR">TDA Suspense Head Clearence</option>
                     <option value="TCAR">TCA Suspense Head Clearence</option>
                      <!-- added on  30/03/2012-->
                      <option value="TPAOC">TPA CR Originating</option>
                     <option value="TPAOD">TPA DR Originating</option>
                     
                     <option value="TPAAC">TPA CR Accepting</option>
                     <option value="TPAAD">TPA DR Accepting</option>
                     <!-- added on 22-11-2017  -->
                     <option value="TPAOCV">TPA CR Verification</option>
                     <option value="TPAODV">TPA DR Verification</option>
                     
                      <option value="TPAOCA">TPA CR Audit Verification</option>
                      <option value="TPAODA">TPA DR Audit Verification</option>
                     
                     <!-- 
                     
                     
                      <option value="TDAOS">TDA Originating(Supplement)</option>
                     <option value="TCAOS">TCA Originating(Supplement)</option>
                     <option value="TDAAS">TDA Accepting(Supplement)</option>
                     <option value="TCAAS">TCA Accepting(Supplement)</option>
                     <option value="TCAABS">TCA Accepting Before JVR(Supplement)</option>
                     <option value="TDAABS">TDA Accepting Before JVR(Supplement)</option>
                     <option value="TDARS">TDA Suspense Head Clearence(Supplement)</option>
                     <option value="TCARS">TCA Suspense Head Clearence(Supplement)</option>
                      Modified on 29-05-2017 -->
                     
                    </select>
                  </div>
                </td>
              </tr>
			<tr class="table">
                <td width="40%">
                  <div align="left">
                    Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td width="60%">
                  <div align="left">
                    <input type="text" name="txtCrea_date" id="txtCrea_date" tabindex="4" 
                           maxlength="10" size="11"  readonly="readonly"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onchange="call_adj_memo();"/>
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmAuthorization_JAO_Create.txtCrea_date,1);"
                         alt="Show Calendar"></img> 
                  </div>
                </td>
            </tr>
            
          <tr class="table">
                <td width="40%">
                  <div align="left">
                    Voucher Number 
                <font color="#ff2121">
                  *
                </font>
              </div>
                </td>
                <td width="60%">
                  <div align="left">
                    <select size="1" name="txtVoucher_No" id="txtVoucher_No" tabindex="5" onchange="doFunction('load_Voucher_details','null');">
                      <option value="">--Select Voucher Number--</option>
                    </select>
                  </div>
                  <div align="left" id="sldiv" name="sldiv" style="display:none">
                    <select size="1" name="txtSerialNo" id="txtSerialNo" tabindex="5" >
                      <option value="">--Select Serial Number--</option>
                    </select>
                  </div>
                </td>
              </tr>
            <tr class="table"  >
                <td width="40%">
                  <div align="left">
                   Received /Paid /journal type/office name
                  </div>
                </td>
                <td width="60%">
                  <div align="left">
                    <input type="text" name="com_value"  style="background-color: #ececec" readonly="readonly"
                           id="com_value" size="75"/>
                  </div>
                </td>
              </tr>
                    <tr class="table" >
                <td width="40%">
                  <div align="left">
                 Total Amount 
                    
                  </div>
                </td>
                <td width="60%">
                  <div align="left">
                    
                    <input type="text" name="amt"  style="background-color: #ececec" readonly="readonly"
                           id="amt" size="16"/>
                           
                  </div>
                </td>
              </tr>
            <tr class="table">
                <td width="40%">
                  <div align="left">
                    Authorized To
                <font color="#ff2121"> *</font>
              </div>
                </td>
                <td width="60%">
                 <div align="left" id="modifyauthID">
                  <div align="left">
                  
                   <span id="H_Modify" >
                      <input type="radio" id="radAuth_MC" name="radAuth_MC" value="M" checked="checked"/> Modify
                   </span>
                    </div>
                    <div align="left" id="cancelauthID">
                   <span id="H_Cancel">
                      <input type="radio" id="radAuth_MC" name="radAuth_MC" value="C" /> Cancel
                   </span>
                   </div>
                  </div>
                </td>
              </tr>    

              <tr class="table">
                <td width="40%">
                  <div align="left">
                    Reference Number 
              </div>
                </td>
                <td width="60%">
                  <div align="left">
                    <input type="text" name="txtReferNO_edit"
                           id="txtReferNO_edit" maxlength="15" size="16"/>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td width="40%">
                  <div align="left">
                    Reference Date 
              </div>
                </td>
                <td width="60%">
                  <div align="left">
                    <input type="text" name="txtReferDate_edit"
                           id="txtReferDate_edit" maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmAuthorization_JAO_Create.txtReferDate_edit);"
                         alt="Show Calendar"></img>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td width="40%">
                  <div align="left">
                    Remarks 
                <font color="#ff2121">
                  *
                </font>
              </div>
                </td>
                <td width="60%">
                  <div align="left">
                    <textarea name="txtRemak_edit" id="txtRemak_edit" cols="60"  onkeypress="return check_leng(this.value);"
                              rows="3"></textarea>
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
    </form></body>
</html>        