<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <title>DCB Receipt Reports</title>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"
            src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script language="javascript" type="text/javascript"
            src="../scripts/DCBReceipt_Reports.js"></script>
            
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
  <%
	String s = request.getContextPath();
  %>
  <body class="table" onload="loadyear_month();">
  <form name="DCBReceipt_Report" method="POST" action="../../../../../../DCBReceipt_Reports_HO"  onsubmit="return nullcheck()">
      <table cellspacing="2" cellpadding="3" width="100%">
        <tr class="tdH">
          <td colspan="2">
            <div align="center">
              <strong>DCB Receipts Report</strong>
            </div>
          </td>
        </tr>
      </table>
      <div align="center">
        <table cellspacing="1" cellpadding="2" border="1" width="100%">
        
          <tr  class="table">
                <td >
                  <div align="left">
                    Accounting For Office Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td >
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
                    <option value="5000">Head Office, Chennai</option>
                    </select>
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
                <select size="1" name="subLedgerType" id="subLedgerType">
                	<option value="14">DCB Beneficiary</option>
                </select>
              </div>
            </td>
          </tr>
          
          <tr>
                <td>From Date</td>
                <td>
                  <input type="text" name="txtfromdate" id="txtfromdate"
                         onkeypress="return  calins(event,this)" 
                         onblur="return checkdt(this);"
                         onfocus="javascript:vDateType='3'" maxlength="10"></input>
                  <img src="../../../../../../images/calendr3.gif"
                       onclick="showCalendarControl(document.DCBReceipt_Report.txtfromdate);"
                       alt="Show Calendar" height="24" width="19"></img>
                </td>
                <td>To Date</td>
                <td>
                  <input type="text" name="txttodate" id="txttodate"
                         onkeypress="return  calins(event,this)"
                         onblur="return checkdt(this);"
                         onfocus="javascript:vDateType='3'" maxlength="10"></input>
                  <img src="../../../../../../images/calendr3.gif"
                       onclick="showCalendarControl(document.DCBReceipt_Report.txttodate);"
                       alt="Show Calendar"></img>
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