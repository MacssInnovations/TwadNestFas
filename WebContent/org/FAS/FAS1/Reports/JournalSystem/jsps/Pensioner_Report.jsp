<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
    <title>View Pension Details</title>
     
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
   <script type="text/javascript"
            src="../../../../../../org/Library/scripts/checkDate.js"></script>   
             <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>   
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>
    
     <script type="text/javascript" src="../scripts/Pensioner_Report.js"></script>  
    <script type="text/javascript" language="javascript">
     function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.frmpensionerreport.txtCB_Year.value=year;
        document.frmpensionerreport.txtCB_Month.value=month;
       
         }
    </script>
  </head>
   <%
	String s = request.getContextPath();
%>
  <body onload="loadofficeid('<%=s%>');loadyear_month();listtype('<%=s%>');">
   <form name="frmpensionerreport" id="frmpensionerreport" method="POST" action="../../../../../../Pensioner_Report.view?command=reportView" >
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">View Pension Details</font>
          </div>
        </td>
      </tr>
    </table>
    
   
            <table cellspacing="1" cellpadding="2" border="1" width="100%"><!--
              <tr class="table">
                <td>
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td class="table">
                  <div align="left">
                    <input type="text" name="txtAcc_UnitCode"
                           id="txtAcc_UnitCode" maxlength="4" size="5"/>
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);">
                     
                      </select>
                  </div>
                </td>
              </tr>
              --><tr  class="table">
                <td >
                  <div align="left">
                    Accounting For Office Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td >
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
                    </select>
                  </div>
                </td>
              </tr>
              <tr  class="table">
                <td >  
                  <div align="left">
                    Cashbook Month &amp; Year
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td >
                  <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
		          <select name="txtCB_Month" id="txtCB_Month" tabindex="4" >          
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
              <tr  class="table">
              		<td >
              		<div align="left">
              		Check List Type<font color="#ff2121">*</font></div> </td>
              		<td><select name="listtype" id="listtype" onblur="listgroup('<%=s%>');">
                    <option value="">--select--</option>
                     </select>
              		</td>          		
              </tr>
              <tr class="table">
              <td ><div align="left">
              		Check List Group<font color="#ff2121">*</font></div> </td>
              <td>
              
              <select  name="grouptype" id="grouptype">
                    <option value="">--select--</option>
              </select>
              </td>
              </tr>
              <tr class="table">
              <td ><div align="left">
              		Pensioner/Family<font color="#ff2121">*</font></div>
              </td>
              <td>
               <select  name="penfamily" id="penfamily">
                    <option value="">--select--</option>
                    <option value="P">P</option>
                    <option value="F">F</option>
              </select>
              </td></tr>
                <tr class="table">
            <td >Report Option:</td>
            <td >
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
            
         <table align="center" cellspacing="3" cellpadding="2" border="1"
             width="100%">
        <tr class="tdH">
          <td>
            <div align="center">
              <input type="submit" value="Submit" onclick="return checkNull();"></input>
               
              <input type="button" id="cmdcancel" name="cancel" value="EXIT"
                     onclick="btncancel()"></input>
            </div>
          </td>
        </tr>
      </table>
      
    </form>
    </body>
</html>