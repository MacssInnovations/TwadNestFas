<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <title>Sub Ledger Summary Report</title>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    
    <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script language="javascript" type="text/javascript" src="../scripts/SubLedgerMaster.js"></script>            
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
 
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>          
          
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>  
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript" src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Load_Accounting_office.js"></script>
                            
    <script type="text/javascript" language="javascript">
    function loadyear_month()
    {       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
         document.frmGeneralLedgerSystem.txtCB_Year_From.value=year
         document.frmGeneralLedgerSystem.txtCB_Month_From.value=month;
        
         document.frmGeneralLedgerSystem.txtCB_Year_To.value=year
         document.frmGeneralLedgerSystem.txtCB_Month_To.value=month;
        
        
     }
    </script>
    
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
    
    
  </head>
  
  <body class="table" onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS')"><form name="frmGeneralLedgerSystem"
                                                      method="POST"
                                                      action="../../../../../SL_Summary.kv"
                                                      onsubmit="return checknull()">
                                                      
      <table cellspacing="2" cellpadding="3" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Sub Ledger Summary Report</strong>
            </div>
          </td>
        </tr>
      </table>
      <div align="center">
        <table cellspacing="1" cellpadding="2" border="0" width="100%">
          <tr class="table">
            <td>
              <div align="left">
                Accounting Unit Code 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
                        tabindex="1" onchange="LoadOffice(this.value);"></select>
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
                <select size="1" name="cmbOffice_code" id="cmbOffice_code"
                        tabindex="2"></select>
              </div>
            </td>
          </tr>
          
         
          <tr align="left">
            <td class="table">
              <div align="left">Cash Book Year &amp; Month  <font color="#ff2121">*</font> </div>
             
            </td>
            <td>
              <div align="left">
                From 
                <input type="text" name="txtCB_Year_From" id="txtCB_Year_From"
                       tabindex="3" maxlength="4" size="5"
                       onkeypress="return numbersonly(event)"></input>
                 
                <select name="txtCB_Month_From" id="txtCB_Month_From" tabindex="4">                  
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
                 To 
                <input type="text" name="txtCB_Year_To" id="txtCB_Year_To"
                       tabindex="3" maxlength="4" size="5"
                       onkeypress="return numbersonly(event)"></input>
                 
                <select name="txtCB_Month_To" id="txtCB_Month_To" tabindex="4">                
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
          <tr align="left">
            <td class="table">
              <div align="left"> Account Head Code Type  <font color="#ff2121">*</font> </div> 
            </td>
            <td>
              <div align="left">
                 <input type="radio" name="Acc_Head_Type" id="Acc_Head_Type_A" value="A" checked /> All 
                 <input type="radio" name="Acc_Head_Type" id="Acc_Head_Type_S" value="S" /> Sinlge                  
              </div>
            </td>
          </tr>
          
         <tr align="left">
            <td class="table">
              <div align="left">Account Head Code  <font color="#ff2121">*</font></div>
             
            </td>
            <td>
              <div align="left">
                 <input type="text" name="txtAcc_HeadCode" 
                           id="txtAcc_HeadCode" maxlength="6" 
                            onkeypress="return numbersonly(event)"
                            onchange="sixdigit();" 
                            onblur="doFunction('checkCode','null')"  size="9"/>
                    <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountHeadList"
                             onclick="AccHeadpopup();"></img>
                    <input type="text" name="txtAcc_HeadDesc" readonly="readonly" 
                           id="txtAcc_HeadDesc" style="background-color: #ececec"  maxlength="125" size="70"/>
                           
                    <div style="display:none">
                           <select id="cmbSL_type" name="cmbSL_type" >
                             <option value="">-- </option>
                           </select>
                           <select id="cmbSL_Code" name="cmbSL_Code" >
                             <option value="">-- </option>
                           </select>
                    </div>
                           
              </div>
            </td>
          </tr>
       
          <tr align="left">
           <td class="table">
            <div align="left">Supplement Number <font color="#ff2121">*</font> </div>
            </td>
            <td>
              <div align="left">
                <input type="text" name="txtsupplement_no" id="txtsupplement_no"
                       tabindex="3" maxlength="4" size="5"
                       onkeypress="return numbersonly(event)"></input>                 
                </div>
            </td>
          </tr>                             
       
          
        </table>
      </div>
      
      
      <table align="center" cellspacing="3" cellpadding="2" border="0"
             width="100%">
        <tr >
          <td class="tdH">
            <div align="center">
              <input type="submit" value="Submit"></input>
               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
          </td>
        </tr>
      </table>
         
    </form>
  </body>
</html>