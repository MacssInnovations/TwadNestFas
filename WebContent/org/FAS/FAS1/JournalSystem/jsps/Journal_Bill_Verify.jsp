<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Journal Bill Verification </title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"
            src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script language="javascript" type="text/javascript"
            src="../scripts/Journal_Bill_Verify.js"></script>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript"
            src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>
    <script type="text/javascript" language="javascript">
     function loadyear_month()
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
                       
            document.frmJournalBillVerify.txtCB_Year.value=year
            document.frmJournalBillVerify.txtCB_Month.value=month;        
            
            document.getElementById("butSub").disabled=false;   
      
          
     }
    </script>
  </head>
   <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body class="table"
        onload="call_clr();loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS')"><form name="frmJournalBillVerify"
                                                                                          id="frmJournalBillVerify"
                                                                                          method="POST"
                                                                                          action="../../../../../Journal_Bill_Verify.kv?Command=Add"
                                                                                          onsubmit="return checkNull()">
      <table cellspacing="2" cellpadding="3" width="100%" border="0">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Journal Bill Verification </strong>
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
                        tabindex="1" onchange="common_LoadOffice()"></select>
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
                        tabindex="2" ></select>
              </div>
            </td>
          </tr>
          <tr class="table">
          
          
           <td class="table">
          <div align="left">
              Cash Book Year &amp; Month
              </div>
            </td>
            <td>
             <div align="left">
          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
       
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
          
         <tr class="table">
         <td class="table">
          <div align="left">
               Date 
              </div>
            </td>
            <td>
             <div align="left">             
                  From 
                  <input type="text" name="txtfromdate" id="txtfromdate"
                         onkeypress="return  calins(event,this)"
                         onblur="return checkdt(this);"
                         onfocus="javascript:vDateType='3'" maxlength="10"></input>
                  <img src="../../../../../images/calendr3.gif"
                       onclick="showCalendarControl(document.frmJournalBillVerify.txtfromdate);"
                       alt="Show Calendar" height="24" width="19"></img>
                       
                  To 
                  
                  <input type="text" name="txttodate" id="txttodate"
                         onkeypress="return  calins(event,this)"
                         onblur="return checkdt(this);"
                         onfocus="javascript:vDateType='3'" maxlength="10"></input>
                  <img src="../../../../../images/calendr3.gif"
                       onclick="showCalendarControl(document.frmJournalBillVerify.txttodate);"
                       alt="Show Calendar"></img>                                      
           </div>
          </td>
          </tr>
          
            
          </tr>
        </table>
      </div>
      <table align="center" cellspacing="3" cellpadding="2" border="0"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="button" name="NotVerifyList" id="NotVerifyList"
                     value="Non Verify List"
                     onclick="doFunction('loadPendingJournals_NV','null');"/>
               
              <input type="button" name="VerifiedList" id="VerifiedList"
                     value="Verified List"
                     onclick="doFunction('loadPendingJournals_V','null');"/>
            </div>
          </td>
        </tr>
      </table>
      <table id="mytable" align="center" cellspacing="3" cellpadding="2"
             border="0" width="100%">
        <tr class="tdH">
          <th>Voucher Number</th>
          <th>Voucher Date</th>
          <th>Account Head Code</th>
          <th>CR / DR Indicator</th>
          <th>Total Amount</th>          
        </tr>
        <tbody id="grid_body" class="table" align="left">
        </tbody>
      </table>
      <table align="center" cellspacing="3" cellpadding="2" border="0"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="submit" name="butSub" id="butSub" value="SUBMIT"/>
               
              <input type="button" name="butCan" id="butCan" value="EXIT"
                     onclick="exit();"/>
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>