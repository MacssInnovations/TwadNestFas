<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ page import="java.sql.Date"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>Opening Balance General Ledger Account</title>
    
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
      <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
       <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
      
      <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

<script type="text/javascript"  src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"  src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    
    
    
     <script type="text/javascript"   src="../scripts/Openingbalance.js"></script>
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
             

        }
</script>
  </head>
  <body onload="loadDate(),LoadAccountingUnitID('LIST_ALL_UNITS')" ,bgcolor="rgb(255,255,225)">
  <form name="OpeningBalForm" id="OpeningBalForm" >
 
 

 
      <table cellspacing="3" cellpadding="2" border="1" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              Opening Balance For General Ledger A/c Heads System
            </div></td>
        </tr>
    <tr class="table">
                                    <td width="257">Accounting Unit Name<span class="style1">* </span></td>
                                    <td width="291"> <select name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" disabled="disabled"></select></td>             
                        </tr>
                        <tr class="table">
                          <td>Accounting Unit Office Name<span class="style1">* </span> </td>
                          <td><select name="cmbOffice_code" id="cmbOffice_code" disabled="disabled"></select></td>
                        </tr>
        <tr class="table">
          <td>Financial Year
                <font color="#ff2121">
                  *
                </font>
          
          </td>
          <td>
           <!-- <input type="text" name="txtFinanYr" maxlength="9" size="9"
                   id="txtFinanYr"/>-->
            <select name="txtFinanYr" id="txtFinanYr" size="1">
            <option value="2009-2010">2009-2010</option>
            </select>
          </td>
        </tr>
        <tr align="left">
          <td class="table">
          <div align="left">
              Cash Book Year &amp; Month
              </div>
            </td>
            <td class="table">
             <div align="left">
          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  value=2010 readonly=readonly >
        
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
          <option value="3">March</option>
          </select>
           </div>
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
                            onblur="doFunction('checkCode','null'), checkExists() "  size="9"/>
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
          <td>Upto Date Debit Balance
            <font color="#ff2121">
              <font color="#ff2121">
                <font color="#ff2121">
                  *
                </font>
              </font>
            </font>
          </td>
          <td>
            <input type="text" name="txtDebit" maxlength="16" size="16"
                   id="txtDebit" onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);"/>
                   </td>
        </tr>
        <tr class="table">
          <td>Upto Date Credit Balance
            <font color="#ff2121">
              <font color="#ff2121">
                <font color="#ff2121">
                  *
                </font>
              </font>
            </font>
          </td>
          <td>
            <input type="text" name="txtCredit" maxlength="16" size="16"   onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);"
                   id="txtCredit" />
                  
                
          </td>
        </tr>
   
       
       
        <tr  class="table">
            <td>Last Update Date</td>
            <td>
                DR<input type="text" name="txtDrLUpdate" maxlength="10" size="10"
                   id="txtDrLUpdate" onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"/>
                   <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.OpeningBalForm.txtDrLUpdate);"
                         alt="Show Calendar"></img>
                 CR<input type="text" name="txtCrLUpdate" maxlength="10" size="10"
                   id="txtCrLUpdate" onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"/> 
                   <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.OpeningBalForm.txtCrLUpdate);"
                         alt="Show Calendar"></img>
            </td>
        </tr>
       
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
            <table>
            <tr><td>
              <input type="button" name="onsubmit" value="ADD" id="onsubmit" onclick="add()"/>
              </td><td>
              <input type="button" name="onupdate" value="UPDATE" disabled="disabled"  id="onupdate" onclick="update()"/>
               </td><td>
              <input type="button" name="ondelete" value="DELETE" id="ondelete" disabled="disabled" onclick="delete1()"/>
               </td><td>
              <input type="button" name="cmdCancel" value="EXIT" id="cmdCancel" onclick="exitfun()"/>
               </td><td>
              <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="list()"/>
               </td><td>
               <input type="button" name="cmdClear" value="CLEARALL" id="cmdClear" onclick="refresh()"/>
               </td></tr>
               </table>
            </div></td>
        </tr>
       
      </table>
    </form></body>
</html>