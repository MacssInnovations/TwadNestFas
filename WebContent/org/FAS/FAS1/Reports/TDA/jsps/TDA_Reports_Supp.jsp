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
    <title>TDA Raised Supplement Report</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"
            src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script language="javascript" type="text/javascript"
            src="../scripts/SubLedgerReport.js"></script>
            
            
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>
            
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <script type="text/javascript"
            src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script>
    <script type="text/javascript" language="javascript">
     
     function loadyear_month(){
    
      var today= new Date(); 
      var day=today.getDate();
      var month=today.getMonth();
      month=month+1;
      var year=today.getYear();
      if(year < 1900) year += 1900;
    
     /** Load Cash Book Month and Cash Book Year during Form Load */
     document.frmtdaraisedSuppReport.txtCB_Year.value=year;
     document.frmtdaraisedSuppReport.txtCB_Month.value=month;
             
     /** Load From Cash Book Month and From Cash Book Year during Form Load */
     document.frmtdaraisedSuppReport.txtCB_Year_from.value=year;
     document.frmtdaraisedSuppReport.txtCB_Month_from.value=month;
     
     /** Load To Cash Book Month and To Cash Book Year during Form Load */
     document.frmtdaraisedSuppReport.txtCB_Year_to.value=year;
     document.frmtdaraisedSuppReport.txtCB_Month_to.value=month;
     
   }
         function chooseReport()
         {
             if(document.getElementById("proformatype").value=="CR")
             {
                var t1=document.getElementById("tdablock");
                t1.style.display="none";
                var t2=document.getElementById("tcablock");
                t2.style.display="block";
                var t3=document.getElementById("allBlock");
                t3.style.display="none";
             }
             else if(document.getElementById("proformatype").value=="DR")
             {
                var t1=document.getElementById("tdablock");
                t1.style.display="block";
                var t2=document.getElementById("tcablock");
                t2.style.display="none";
                var t3=document.getElementById("allBlock");
                t3.style.display="none";
             }
             else if(document.getElementById("proformatype").value=="allType")
             {
               var t1=document.getElementById("tdablock");
                t1.style.display="none";
                var t2=document.getElementById("tcablock");
                t2.style.display="none";
                var t3=document.getElementById("allBlock");
                t3.style.display="block";
             }
         }
         function checkNull()
         {
                if(document.getElementById("proformatype").value=="")
                {
                alert("Choose Type TDA or TCA");
                return false;
                }
                if((document.frmtdaraisedSuppReport.month_year[0].checked==false)&&(document.frmtdaraisedSuppReport.month_year[1].checked==false))
                {
                    alert("Select CashBook Year & Month"); 
                    return false;
                }
                if(document.getElementById("proformatype").value=="DR")
                {
                    if(document.getElementById("reportname1").value=="")
                    {
                    alert("Choose Report Name for TDA");
                    return false;
                    }
                }
                else if(document.getElementById("proformatype").value=="CR")
                {
                    if(document.getElementById("reportname2").value=="")
                    {
                    alert("Choose Report Name for TCA");
                    return false;
                    }
                }
                
         }
         function cb_month_year(id)
         {
            var particular=document.getElementById("particular");
            var more=document.getElementById("more");
                
           if(id=="particular_cb")
           {
              particular.style.display="block";
              more.style.display="none";
           }
           if(id=="more_cb")
           {
             more.style.display="block";
             particular.style.display="none";
           }
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
  <body class="table" onload="loadyear_month();LoadAccountingUnitID('FOR_LIST_1');">
  <form name="frmtdaraisedSuppReport" method="POST"  action="../../../../../../TDA_Reports_Supp" onsubmit="return checkNull()">
  
      <table cellspacing="2" cellpadding="3" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>TDA Supplement Report</strong>
            </div>
          </td>
        </tr>
      </table>
      <div align="center">
        <table cellspacing="1" cellpadding="2" border="1" width="100%">
          <tr class="table">
            <td>
              <div align="left">
                Accounting Unit Code 
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);"></select>
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
          
           <tr class="table">
                <td width="30%">
                  <div align="left">
                    Type
                    <font color="#ff2121">*</font> 
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select name="proformatype" id="proformatype" onchange="chooseReport();">
                      <option value="">Type</option>
                     <option value="DR"> TDA</option>
                      <option value="CR">TCA</option>
                      <option value="allType">ALL</option>
                       </select>                                           
                  </div>
                </td>
              </tr>
          
          <tr align="left">
           <td class="table">
              <div align="left">
                 Cash Book Year &amp; Month <font color="#ff2121">*</font></td>
              </div>
           </td>
          <td>
         
          <input type="radio" name="month_year" id="month_year" value="particular_cb" onclick="cb_month_year(this.value)" >one Month 
          <input type="radio" name="month_year" id="month_year" value="more_cb" onclick="cb_month_year(this.value)"> More than one Month 
          
          <br><br>       
          
          <div id="particular" name="particular" style="display:none">
            
          Year 
          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
          Month 
          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" >
          <!--<option value="1">January</option>
          <option value="2">February</option>
          --><option value="3">March</option>
          <!--<option value="4">April</option>
          <option value="5">May</option>
          <option value="6">June</option>
          <option value="7">July</option>
          <option value="8">August</option>
          <option value="9">September</option>
          <option value="10">October</option>
          <option value="11">November</option>
          <option value="12">December</option>
          --></select>
          
          </div>
          
          <div id="more" name="more" style="display:none">
          
          From   
          <input type="text" name="txtCB_Year_from" id="txtCB_Year_from" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month_from"  id="txtCB_Month_from" tabindex="4" ><!--
          <option value="1">January</option>
          <option value="2">February</option>
          <option value="3">March</option>
          --><option value="4">April</option>
          <!--<option value="5">May</option>
          <option value="6">June</option>
          <option value="7">July</option>
          <option value="8">August</option>
          <option value="9">September</option>
          <option value="10">October</option>
          <option value="11">November</option>
          <option value="12">December</option>
          --></select>
          To  
          <input type="text" name="txtCB_Year_to" id="txtCB_Year_to" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month_to"  id="txtCB_Month_to" tabindex="4" ><!--
          <option value="1">January</option>
          <option value="2">February</option>
          --><option value="3">March</option>
          <!--<option value="4">April</option>
          <option value="5">May</option>
          <option value="6">June</option>
          <option value="7">July</option>
          <option value="8">August</option>
          <option value="9">September</option>
          <option value="10">October</option>
          <option value="11">November</option>
          <option value="12">December</option>
          --></select>
          </div>      
           
          </td>
        </tr>
          
          
          <tr class="table">
                <td width="30%">
                  <div align="left">
                   Report Name
                    <font color="#ff2121">*</font> 
                  </div>
                </td>
                <td>
                  <div align="left" id="tdablock" style="display:block">
                    <select name="reportname1" id="reportname1" >
                     <option value="">Report Name</option>
                     <option value="TDAOriginated"> TDA Originated</option>
                     <option value="paymentReport"> TDA Posted from Payment</option>
                     <option value="TDAAccepted">TDA Accepted by you</option>
                     <option value="TDAAcceptedother">TDA Accepted by Others</option>
                     <option value="TDARejected">TDA Rejected by you</option>
                     <option value="TDARejectedother">TDA Rejected by Others</option>
                     <option value="TDAPending">TDA Pending with you </option>
                     <option value="TDAPendingothers">TDA Pending with Others</option>
                     <option value="TDASuspensePending">TDA Suspense Head Pending</option>
                     <option value="TDASuspenseCleared">TDA Suspense Head Cleared</option>
                    </select>
                  </div>
                  <div align="left" id="tcablock" style="display:none">
                    <select name="reportname2" id="reportname2" >
                     <option value="">Report Name</option>
                     <option value="TCAOriginated"> TCA Originated</option>
                     <option value="receiptReport"> TCA Posted from Receipt</option>
                     <option value="journalReport"> TCA Posted from Journal</option>
                     <option value="TCAAccepted">TCA Accepted by you</option>
                     <option value="TCAAcceptedother">TCA Accepted by Others</option>
                     <option value="TCARejected">TCA Rejected by you</option>
                     <option value="TCARejectedother">TCA Rejected by Others</option>
                     <option value="TCAPending">TCA Pending with you</option>
                     <option value="TCAPendingother">TCA Pending with Others</option>
                     <option value="TCASuspensePending">TCA Suspense Head Pending</option>
                     <option value="TCASuspenseCleared">TCA Suspense Head Cleared</option>
                    </select>                                           
                  </div>
                 <div align="left" id="allBlock" style="display:none">
                  <select name="reportname3" id="reportname3" >
                 <option value="bothTdaTca">TDA/TCA</option>
                 </select>
                  </div>
                </td>
              </tr> 
                <tr class="table">
                <td width="30%">
                  <div align="left">
                   Supplement No
                    <font color="#ff2121">*</font> 
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="supp_no" id="supp_no" tabindex="3" value="1" maxlength="3" size="3" onkeypress="return numbersonly(event)"> 
                    
                                                           
                  </div>
                </td>
              </tr>

      <tr>
        <td align="left">Report Option:</td>
        <td colspan="3" align="left">
          <input type="radio" name="txtoption" id="txtoption" value="PDF"
                 checked="checked"></input>
          PDF
          <input type="radio" name="txtoption" id="txtoption" value="EXCEL"></input>
          Excel
          <input type="radio" name="txtoption" id="txtoption" value="HTML"></input>
          HTML
        </td>
      </tr>
      </table>
      </div>
      <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="submit" value="Submit"></input>
               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="closeWindow()"></input>
            </div>
          </td>
        </tr>
      </table>
    </form></body>
</html>