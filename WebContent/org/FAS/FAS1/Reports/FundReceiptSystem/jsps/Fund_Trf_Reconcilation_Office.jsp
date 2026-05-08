<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<html>
  <head>
   <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <link href="../../../../../../css/Sample3.css" rel='stylesheet'
          media='screen'/>
    <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript" src="../scripts/CalendarControl.js"></script>
    <script type="text/javascript" src="../scripts/ListOfReceipt.js"></script>
    <script type="text/javascript"
            src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script>
        
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script> 
            
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
                 
                 function yr_check()
                 {
                 var txtCB_Year=document.getElementById("txtCB_Year").value;
                 //alert("CB_Year---->"+txtCB_Year);
                 if(txtCB_Year<2015)
                 {
                // alert("less than 2015");
                
                
                 document.forms[0].sub.disabled=true;
                 return false;
                 }  
                 else
                 {
                 document.forms[0].sub.disabled=false;
                 }              
                 }
    </script>
    <title>Fund Transfer Reconciliation at Office</title>
  </head>
  <body class="table"
        onload="loadyear_month();yr_check();LoadAccountingUnitID('FOR_LIST_1')">
        <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
        <form action="../../../../../../Fund_Transfer_Reconciliation_atOffice" name="frmReport"  method="post">
   <input type="hidden" id="hid" name="hid" value="FTR_Office">
   
      <table width="100%">
        <tr>
          <td class="tdH">
            <center>
              <b>Fund Transfer Reconciliation at Office</b>
            </center>
          </td>
        </tr>
     
            </table>
               <br>
          <table width="100%">
              <tr>
                <td class="table">
                  Cash Book Year &amp; Month&nbsp;&nbsp;
                  <font color="#ff2121">*</font>
                </td>
                <td colspan="3">
                  <input type="text" name="txtCB_Year" id="txtCB_Year"
                         tabindex="3" maxlength="4" size="5"
                         onkeypress="return numbersonly(event)" onchange="yr_check()" onblur="yr_check()"></input>
                   <select name="txtCB_Month" id="txtCB_Month" tabindex="4">
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
                        <td align="left">
                           Select Type <font color="#ff2121">*</font>
                        </td>
                        <td colspan="3" align="left">
                            <input type=radio name="seletype" id="seletype" value="accounted" checked>Accounted
                            <input type=radio name="seletype" id="seletype" value="notaccounted">Not Accounted
                           
                        </td>
                        
                    </tr>
                   
              <tr>
                <td>Report Option:  <font color="#ff2121">*</font></td>
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
              </table>
              <br>
                <table width="100%">
              <tr>
                <td colspan="4" class="tdH" align="center">
                  <input type="submit" value="Submit" name="sub"></input>
                  <input type="reset" value="Clear"></input>
                  <input type="button" value="Exit" onclick="closeWindow()"></input>
                </td>
              </tr>
            </table>
       
    </form></body>
</html>