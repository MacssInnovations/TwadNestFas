<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Adjustment Memo From Board</title>

 <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
  <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
 
 <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
  
            
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"  src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    
    
     
      <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl_forChequeDate.js"></script> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
     <script type="text/javascript" src="../js/Adjustment_Memo_Reject.js"></script>
       <script type="text/javascript" src="../js/LoadMemoDetails.js"></script>
     
<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>




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
            document.frmAdjustmentMemo_Reject.txtDate.value=day+"/"+month+"/"+year;
           // call_date(document.frmAdjustmentMemo_Reject.txtCrea_date);

        }
</script>
</head>
<body  onload="LoadAccountingUnitID('LIST_ALL_UNITS'),loadDate(),setTimeout('loadMemoNO_reject()',3000)" class="table">
<form name="frmAdjustmentMemo_Reject" id="frmAdjustmentMemo_Reject">
  
                    
 <table  cellspacing="2" border="1"  width="100%">
     <tr class="table">
      <td colspan="2" class="tdH"><div align="center"><strong>Adjustment Memo From Board(Reject)</strong> </div></td>
    </tr>
    <tr class="table">
      <td colspan="2">&nbsp;</td>
    </tr>
                        <tr class="table">
                                    <td width="257">Accounting Unit Name</td>
                                    <td width="291"> <select name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);"></select></td>
                                                 
                        </tr>
                        <tr class="table">
                          <td>Accounting Unit Office Name </td>
                          <td><select name="cmbOffice_code" id="cmbOffice_code"></select></td>
                        </tr>
                      
                     <tr class="table">
                          <td>Date</td>
                       <td>
                  <div align="left">
                    <input type="text" name="txtDate" id="txtDate" tabindex="3" 
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="loadMemoNO_reject(this);"/>
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmAdjustmentMemo_Reject.txtDate);"
                         alt="Show Calendar"></img> 
                  </div>
                </td>
                  
                        </tr>
                       <tr class="table">
                <td>
                  <div align="left">Memo Advice No<font color="#ff2121">*</font> </div>
                </td>
             <td>
                    <select id="cmbAdviceNO" name="cmbAdviceNO" onchange="loadMemoDetails_reject();">
                        <option value="s">--Select Code--</option>
                        </select>      </td>      </tr>
                         
                        
                 
            
            
          <tr class="table">
                <td>
                  <div align="left"> Authority Name</div>
                </td>
                <td>
                  <div align="left">
                     <textarea rows="3"  tabindex="5" cols="35" id="txtAuthority" name="txtAuthority" ></textarea>
                  </div>
                </td>
              </tr>   
               <tr class="table">
                <td>
                  <div align="left"> Authority Address</div>
                </td>
                <td>
                  <div align="left">
                     <textarea rows="3"  tabindex="5" cols="35" id="txtAuthorityaddress" name="txtAuthorityaddress" ></textarea>
                  </div>
                </td>
              </tr>   
          <tr class="table">
                          <td>Letter No</td>
                       <td>
                
                    <input type="text" name="txtLetterNO" id="txtLetterNO" readonly="readonly" ></input>
                  
                </td>
                  
                        </tr>
                        
                        <tr class="table">
                          <td>Letter Date</td>
                       <td>
                
                    <input type="text" name="txtLetterDate" id="txtLetterDate" readonly="readonly"></input>
                  
                </td>
                  
                        </tr>
                       
                        <tr class="table" style=" height : 74px;">
                          <td>Remarks/particulars</td>
                          <td style=" width : 309px;"> <textarea rows="3" cols="18" name="txtRemarks1" id="txtRemarks1" readonly="readonly" style=" width : 177px;"></textarea>                          </td>
                        </tr>
                       <tr class="table">
                          <td>Total Amount</td>
                       <td>
                
                    <input type="text" name="txtAmount" id="txtAmount" readonly="readonly"></input>
                  
                </td>
                  
                        </tr>
                        <tr class="table" style=" height : 74px;">
                          <td>Reason For Rejecting Memo Advice<span class="style1">* </span> </td>
                          <td style=" width : 309px;"> <textarea rows="3" cols="18" name="txtReject" id="txtReject" style=" width : 177px;"></textarea>                          </td>
                        </tr>
                            <tr class="table">
      <td colspan="2" class="table">&nbsp;</td>
    </tr>
                         
                        <tr class="table">
                                   
                                    <td colspan="2" class="tdH">
                                            <div align="center">
                                              <input type="button" name="onsubmit" value="SUBMIT" id="onsubmit" onClick="add();" />  
                                              <input type="button" name="onexit" value="EXIT" id="onexit" onClick="exitfun();" />  
                                              <input type="button" name="onCancel" value="Cancel" id="onCancel" onClick="refreash();" />                                 
                                            </div></td> 
                        </tr>
  </table>

 
</form>
</body>
</html>