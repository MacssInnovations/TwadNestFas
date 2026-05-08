<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     <meta http-equiv="cache-control" content="no-cache">
    <title>General Ledger Posting</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>
    
    
    <script language="javascript" type="text/javascript" src="../scripts/GeneralLedgerReport.js"></script>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet" media="screen"/>
    <script type="text/javascript" src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script> 
    <script type="text/javascript" language="javascript">
     function loadyear_month()
        {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        /** Load Cash Book Month and Cash Book Year during Form Load */
        document.frmGeneralLedgerReport.txtCB_Year.value=year
        document.frmGeneralLedgerReport.txtCB_Month.value=month;
                
        /** Load From Cash Book Month and From Cash Book Year during Form Load */
        document.frmGeneralLedgerReport.txtCB_Year_from.value=year
        document.frmGeneralLedgerReport.txtCB_Month_from.value=month;
        
        /** Load To Cash Book Month and To Cash Book Year during Form Load */
        document.frmGeneralLedgerReport.txtCB_Year_to.value=year
        document.frmGeneralLedgerReport.txtCB_Month_to.value=month;
        
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
  <body class="table" onload="loadyear_month();LoadAccountingUnitID('FOR_LIST_1');setTimeout('show_effectivedetails()',600)" >
  <form name="frmGeneralLedgerReport" id="frmGeneralLedgerReport" method="POST" action="" onsubmit="return checknull()">
   
  <table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
              <strong>General Ledger Posting Report</strong>
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
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1"  onchange="common_LoadOffice(this.value);" >
                    
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
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2" onchange="show_effectivedetails()">
                 
                    </select>
                  </div>&nbsp;&nbsp;&nbsp;
                   <div  align="left">
                   Date of Formation <input type="text" name="starting_date" id="starting_date" value=""  disabled>&nbsp;&nbsp;&nbsp;&nbsp;
                   Effective from <input type="text" name="effective_from" id="effective_from" value="" disabled>
                  </div>
                  <div  align="left">
                   <input type="radio" name="all_office" id="all_office" value="all_office" checked onclick="call_AllOfiices(this.value)" >All Offices
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
          <input type="radio" name="month_year" id="month_year" value="more_cb_monthwise" onclick="cb_month_year(this.value)"> More than one Month(Month Wise)   
          <br><br>       
          
          <div id="particular" name="particular" style="display:none">
            
          Year 
          <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
          Month 
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
          
          <div id="more" name="more" style="display:none">
          
          From   
          <input type="text" name="txtCB_Year_from" id="txtCB_Year_from" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month_from"  id="txtCB_Month_from" tabindex="4" >
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
          <input type="text" name="txtCB_Year_to" id="txtCB_Year_to" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
         
          <select name="txtCB_Month_to"  id="txtCB_Month_to" tabindex="4" >
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
         <tr>
          <td align="left">Select Specific Account Head Code:<font color="#ff2121">*</font></td>
          <td align="left">
                            <input type=radio id="SpecificAHC" name="SpecificAHC" value="All" checked onclick="AccHead(this.value)"> All
                            <input type=radio id="SpecificAHC" name="SpecificAHC" value="Specific" onclick="AccHead(this.value)">Specific
                            <div id="acchead" name="acchead" style="display:none">
                            <input type="text" name="txtAcc_HeadCode" id="txtAcc_HeadCode" size=10 maxlength="8" onkeypress="return numbersonly(event)">
                            <img src="../../../../../../images/c-lovi.gif" alt="AccountHeadList" onclick="AccHeadpopup();"  height="24" width="24"></img>
                            </div>
                            
          </td>
          </tr>
        <tr>
                        <td align="left">
                            Report Option:
                        </td>
                        <td colspan="3" align="left">
                            <input type=radio name=txtoption id=txtoption value="PDF" checked>PDF
                            <input type=radio name=txtoption id=txtoption value="EXCEL">Excel
                            <input type=radio name=txtoption id=txtoption value="HTML">HTML
                        </td>
                        
                    </tr>
</table>
          </div>
          <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >
  
      <tr class="tdH">
      <td>
          <div align="center">
          <input type="button"  value="Submit" id="sum" name="sum" onclick="submit_form()" >
          <input type="button" id="cmdcancel" name="cancel" value="Exit" onclick="closeWindow()">
       </div>
      </td>
      </tr>
      
      </table>
      </form>
  </body>
</html>