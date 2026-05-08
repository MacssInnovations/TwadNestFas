<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache"></meta>
    <link href="../../../../../../css/Sample3.css" rel='stylesheet'
          media='screen'/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
    <script type="text/javascript"
            src="../scripts/ListOfReceiptAccountWise_Single.js"></script>
    <script type="text/javascript"
            src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script>
    <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
                function loadyear_month()
                 {
               
                 var today= new Date(); 
                 var day=today.getDate();
                 var month=today.getMonth();
                 month=month+1;
                 var year=today.getYear();
                 if(year < 1900) year += 1900;
               
                document.frmReport.txtCB_Year.value=year
                document.frmReport.txtCB_Month.value=month;
                
                 }
                 function RPType_Disp()
                 {
                 var Month=document.getElementById("txtCB_Month").value;
				 if(Month==3)
				 {
				 document.getElementById("rptype").style.display="block";
				 document.getElementById("All").style.display="block";
				 document.getElementById("Regular").style.display="block";
				 document.getElementById("Supp").style.display="block";
				 }
				 else
				 {
				 document.getElementById("rptype").style.display="none";
				 document.getElementById("All").style.display="none";
				 document.getElementById("Regular").style.display="none";
				 document.getElementById("Supp").style.display="none";
				 }                 
                 }
                 
   function ChooseReptype(id)
     {
        
         var dispsupnochosen1=document.getElementById("dispsupno1");
         var dispsupnochosen2=document.getElementById("dispsupno2");
    	if(id=="Supplement")
         {
                  
                 dispsupnochosen1.style.display="block";
                 dispsupnochosen2.style.display="block";
                 alert("Enter the Supplement Number");
		 }
         else
         {
                 dispsupnochosen1.style.display="none";
                 dispsupnochosen2.style.display="none";
                
         }
     }
                 
                 
    </script>
    <title>Transactions Listings-Account Head Wise</title>
  </head>
  <body class="table"
        onload="loadyear_month();LoadAccountingUnitID('FOR_LIST_1')">
        <form action="../../../../../../ListOfReceiptAccountWise_Single.con"
                                                                           name="frmReport"
                                                                           method="post"
                                                                           onsubmit="return nullcheck();">
     
      <table width="100%">
        <tr>
          <td class="tdH">
            <center>
              <b>Transactions Listings-Account Head Wise</b>
            </center>
          </td>
        </tr>
        <tr>
          <td>
            <table border="4" cellspacing="0" cellpadding="0" width="100%">
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
                            tabindex="1" onchange="common_LoadOffice(this.value);"></select>
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
              <tr>
                <td>
                  Account Head Code:
                  <font color="#ff2121">*</font>
                </td>
                <td colspan="4">
                  <input type="text" name="cmbAccHeadCode" size="8"
                         maxlength="8" onchange="doFunction('checkCode','null')"
                         onkeypress="return numbersonly(event)"></input>
                  <img src="../../../../../../images/c-lovi.gif" width="24"
                       height="24" alt="AccountHeadList"
                       onclick="AccHeadpopup();"></img>
                </td>
              </tr>
              <tr>
                <td>Account Head Name:</td>
                <td colspan="4">
                  <input type="text" name="txtaccountheadname" size="60"
                         disabled="disabled"></input>
                </td>
              </tr>
              <tr>
                <td class="table">
                  Cash Book Year &amp; Month&nbsp;&nbsp;
                  <font color="#ff2121">*</font>
                </td>
                <td>
                  <input type="text" name="txtCB_Year" id="txtCB_Year"
                         tabindex="3" maxlength="4" size="5"
                         onkeypress="return numbersonly(event)"></input>
                  <select name="txtCB_Month" id="txtCB_Month" tabindex="4" onchange="RPType_Disp();">
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
                </td>
              </tr>
              <tr>
              <td>
              <div align="left" id="rptype" style="display:none">Selection of Report Type</div>
              </td>
              <td>
              <div align="left" id="All" style="display:none">
              <input type="radio" id="rptsel" name="rptsel" value="all" onclick="ChooseReptype(this.value)" checked>All</div>
              <div align="left" id="Regular" style="display:none">
              <input type="radio" id="rptsel" name="rptsel" value="Regular" onclick="ChooseReptype(this.value)">Regular</div>
              <div align="left" id="Supp" style="display:none">
              <input type="radio" id="rptsel" name="rptsel" value="Supplement" onclick="ChooseReptype(this.value)">Supplement</div>
              </td>
              </tr>
              
              <tr align="left">
     
        <td class="table">
          <div align="left" id="dispsupno1" name="dispsupno1" style="display:none">
            Supplement Number 
            <font color="#ff2121">*</font>
          </div>
        </td>
        <td>
          <div id="dispsupno2" name="dispsupno2" style="display:none">
            <input type="text" name="supno" id="supno" value=0 size=2 >     
          </div>
          
        </td>
      </tr>     
              
              <tr>
                <td>
                  From Date:
                  <font color="#ff2121">*</font>
                </td>
                <td>
                  <input type="text" name="txtfromdate" id="txtfromdate"
                         onkeypress="return  calins(event,this)"
                         onblur="return checkdt(this);"
                         onfocus="javascript:vDateType='3'" maxlength="10"></input>
                  <img src="../../../../../../images/calendr3.gif"
                       onclick="showCalendarControl(document.frmReport.txtfromdate);"
                       alt="Show Calendar" height="24" width="19"></img>
                </td>
                <td>
                  To Date:
                  <font color="#ff2121">*</font>
                </td>
                <td>
                  <input type="text" name="txttodate" id="txttodate"
                         onkeypress="return  calins(event,this)"
                         onblur="return checkdt(this);"
                         onfocus="javascript:vDateType='3'" maxlength="10"></input>
                  <img src="../../../../../../images/calendr3.gif"
                       onclick="showCalendarControl(document.frmReport.txttodate);"
                       alt="Show Calendar"></img>
                </td>
              </tr>
              <tr>
                <td>Report Option:</td>
                <td colspan="3">
                  <input type="radio" name="txtoption" id="txtoption"
                         value="PDF" checked="checked"></input>
                  PDF
                  <input type="radio" name="txtoption" id="txtoption"
                         value="EXCEL"></input>
                  Excel
                  <input type="radio" name="txtoption" id="txtoption"
                         value="HTML"></input>
                  HTML
                </td>
              </tr>
              <tr>
                <td colspan="4" class="tdH" align="center">
                  <input type="submit" value="Submit"></input>
                  <input type="reset" value="Clear"></input>
                  <input type="button" value="Exit" onclick="closeWindow()"></input>
                </td>
              </tr>
            </table>
          </td>
        </tr>
      </table>
    </form></body>
</html>