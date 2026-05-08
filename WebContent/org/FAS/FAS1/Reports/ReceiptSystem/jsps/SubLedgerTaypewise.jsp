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
    <title>Sub Ledger Report(TypeWise)</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"
            src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script language="javascript" type="text/javascript"
            src="../scripts/subLedgerTypewise.js"></script>
            
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
    <script type="text/javascript"
            src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>  
             <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript" 
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>
       
    <script type="text/javascript" language="javascript">
     function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.subLedgertypewise.txtCB_Year.value=year;
        document.subLedgertypewise.txtCB_Month.value=month;
        
       /** Load To Cash Book Month and To Cash Book Year during Form Load */
        document.subLedgertypewise.txtCB_Year_to.value=year;
        document.subLedgertypewise.txtCB_Month_to.value=month;
        LoadAccountingUnitID('LIST_ALL_UNITS');
        
         }
    </script>
    
    <script type="text/javascript">
    
    function acchead_Code() {
		//alert("Welcome");
	     var subCode=document.getElementById('subLedgerType').value;
	    // alert("subCode=======inside_radio button====>"+subCode);
	          
	     if (subCode == 10) {
	             
	               document.getElementById("dataRow").style.display="block";
	        	     }
	     else
	         {
	         
	         document.getElementById("dataRow").style.display="none";
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
  <body class="table" onload="clear_subcode();loadyear_month();callServer('subType');">
  <form name="subLedgertypewise" method="POST" action="../../../../../../subtypecombo.report?command=report" onsubmit="checkNull();" >
      <table cellspacing="2" cellpadding="3" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>Sub Ledger Report(TypeWise)</strong>
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
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"
                            tabindex="1" onchange="clear_subcode();common_LoadOffice(this.value);"></select>
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
            <td>
              <div align="left">
                Sub Ledger Type
                <font color="#ff2121">*</font>
              </div>
            </td>
            <td>
              <div align="left">
                <select size="1" name="subLedgerType" id="subLedgerType" onchange="acchead_Code();" >
                	<option value="select">Select</option>
                </select>
              </div>
            </td>
          </tr>
          
          <tr align="left">
            <td class="table">
              <div align="left">Cash Book Year &amp; Month</div>
            </td>
            <td>
              <div align="left">
                From 
                <input type="text" name="txtCB_Year" id="txtCB_Year"
                       tabindex="3" maxlength="4" size="5"
                       onkeypress="return numbersonly(event)" onchange="callServer('subCode1');" ></input>
                 
                <select name="txtCB_Month" id="txtCB_Month" tabindex="4" onchange="callServer('subCode1');">
                  <!--<option value="">select the Month</option>-->
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
                <input type="text" name="txtCB_Year_to" id="txtCB_Year_to"
                       tabindex="3" maxlength="4" size="5"
                       onkeypress="return numbersonly(event)" onchange="callServer('subCode1');"></input>
                 
                <select name="txtCB_Month_to" id="txtCB_Month_to" tabindex="4" onchange="callServer('subCode1');">
                  <!--<option value="">select the Month</option>-->
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
          <td align="left">Select Sub Ledger Code <font color="#ff2121">*</font></td>
          <td>
          <input type=radio id="SpecificSL_A" name="SpecificSL" value="All" checked="checked" onclick="sub_LedgerCode(this.value);clear_subcode();"> All SL Code
          <input type=radio id="SpecificSL_S" name="SpecificSL" value="Specific" onclick="sub_LedgerCode(this.value);callServer('subCode1');">Specific  SL Code
          
          
          <div align="left" id="sl_code" name="sl_code" style="display:none">
                <select size="1" name="subLedgerCode" id="subLedgerCode" >
                	<option value="select">Select</option>
                </select>
              </div>
          </td>
          </tr>
                 
                <tr>
          <td >
          <div id="dataRow" style="display:none">
          <input type=radio id="allhead" name="acchead" value="AllHead " checked > All Head of Accounts
          <input type=radio id="schemehead" name="acchead" value="SchemeHeads" >Only Scheme Execution Heads
          </div>
          </td>
         
          </tr>  
          <tbody id="grid_body" class="table" align="left" >
                       
                       </tbody>
        </table>
      </div>
      <tr>
        <td align="left">Report Option:</td>
        <td colspan="3" align="left">
          <input type="radio" name="fileType" id="txtoption1" value="PDF" checked="checked"></input>
          PDF
          <input type="radio" name="fileType" id="txtoption2" value="EXCEL"></input>
          Excel
          <input type="radio" name="fileType" id="txtoption3" value="HTML"></input>
          HTML
        </td>
      </tr>
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