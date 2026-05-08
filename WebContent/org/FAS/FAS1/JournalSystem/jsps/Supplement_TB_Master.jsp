<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">

<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
     <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Supplement TB Master</title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/> 
    <link href='css/Fas_Account.css' rel='stylesheet' media='screen'/>
      
    <script type="text/javascript" src="../../../../Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    
    <!--
    <script type="text/javascript" src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    -->
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/CalendarControl.js"></script> 
    
        
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script> 
    <script type="text/javascript" src="../scripts/Supplement_TB_Master.js"></script>
    
    <script type="text/javascript" language="javascript">
         function foc()
         {
         }
    </script>
    
 </head>
 
  <body onload="Date_Assign();YearLoad();foc();Load_Supplement_No();" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Supplement TB Details </font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmSupplementTB" id="frmSupplementTB" action="#">
                 
                 
    
             <table cellspacing="1" cellpadding="2" border="0" width="100%">
             
             
              <tr class="table">
                <td>
                  <div align="left">
                      Supplement No.                     
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtSupplement_No" id="txtSupplement_No"  readonly />                  
                  </div>
                </td>
              </tr>
          
                   
              <tr class="table">
                <td>
                  <div align="left">
                     Date                    
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtDate" id="txtDate" value="" readonly />                  
                  </div>
                </td>
              </tr>              
              
              <tr class="table">
                <td>
                  <div align="left">
                   Cash Book Year 
                   </div>
                </td>
                <td>
                  <div align="left">
                   
                    <select id="txtCashbook_Year" name="txtCashbook_Year"  onchange="YearChange()">
                    
                    </select>
                  
                  <!--
                     <input type="text" name="txtCashbook_Year" id="txtCashbook_Year" value="2008"  />                                         
                   --> 
                     
                     
                  </div>
                </td>
              </tr>


              <tr class="table">
                <td>
                  <div align="left">
                     Cash Book Month
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCashbook_Month" id="txtCashbook_Month" value="3" readonly />                    
                    
                  </div>
                </td>
              </tr>
              
              
               <tr class="table">
                <td>
                  <div align="left">
                    Supplement Closure Date 
                  
                   </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtSuppl_Closure_date" id="txtSuppl_Closure_date"  tabindex="8"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           
                           onblur="return checkdt(this);"/>
                           
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmSupplementTB.txtSuppl_Closure_date);"
                         alt="Show Calendar"></img>
                  </div>
                </td>
              </tr>  
              
              
              
             <tr class="table">
                <td>
                  <div align="left">Status</div>
                </td>
                <td>
                  <div align="left">
                      <input type="radio" name="txtStatus" id="txtStatus" value="L" checked /> Live 
                      <input type="radio" name="txtStatus" id="txtStatus" value="C" /> Closed 
                  </div>
                </td>
              </tr>
              
              
              
             <tr class="table">
                <td>
                  <div align="left">Remarks </div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtRemarks" id="txtRemarks" cols="50" tabindex="10" onkeypress="return check_leng(this.value);"
                              rows="2"></textarea>
                  </div>
                </td>
              </tr>
              
              
              
             <tr class="tdH">
              <td colspan="2">
                <div align="center">
                <table >
                 <tr>
                    <td>
                    <input type="button" name="cmdAdd" value="ADD" id="cmdAdd" onclick="doFunction_Supplement_TB('Add','null')" tabindex="20"/>
                     </td>
                     <td>
                    <input type="button" name="cmdUpdate" value="FREEZE" id="cmdFreeze" style="display:none" onclick="doFunction_Supplement_TB('Freeze','null')" tabindex="30"/>
                     </td>
                    <td>
                    <input type="button" name="cmdDelete" value="UNFREEZE" id="cmdUnfreeze" style="display:none" onclick="doFunction_Supplement_TB('Unfreeze','null')" tabindex="40"/>
                     </td>
                     <td>
                    <input type="button" name="cmdClear" value="CLEAR" id="cmdClear" onclick="ClearAll()" tabindex="50"/>
                     </td>
                     <td>
                    <input type="button" name="cmdList" value="LIST" id="cmdList" onclick="List()" tabindex="60"/>
                     </td>
                       <td>
                     <input type="button" id="Exit" name="Exit" value="EXIT" onclick="exit()" tabindex="70"/>
                     </td>
                 </tr>
                </table>
                </div>
              </td>
            </tr>
         </table>
        
      
    </form></body>
</html>