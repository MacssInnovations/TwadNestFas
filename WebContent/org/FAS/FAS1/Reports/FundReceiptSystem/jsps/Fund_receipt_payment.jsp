<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
<head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <meta http-equiv="cache-control" content="no-cache">
    <title>Fund Receipt</title>
     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
     <script type="text/javascript" src="../../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID_4Rpt.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office_4Rpt.js"></script>
    
    
    <script language="javascript" type="text/javascript" src="../scripts/.js"></script>
     <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
          media="screen"/>
    <link href="../../../../../../css/Sample3.css" rel="stylesheet"
          media="screen"/>
   
      <link href='../../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
      <script type="text/javascript" src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/Reports/Unitwise_Office_Report.js"></script> 
     <script language="javascript" type="text/javascript"
            src="../scripts/FundReceiptPayment.js"></script>
            
    <script type="text/javascript" language="javascript">
   
     function loadyear_month()
        {
       
         var today= new Date();
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.frmfundRecReport1.txtCB_Year.value=year;
        document.frmfundRecReport1.txtCB_Month.value=month;
                
       
     
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
<%
	System.out.println(s);
%>
<body onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS')">
<form name="frmfundRecReport1" id="frmfundRecReport1" method="post" >
<table cellspacing="2" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
              <strong>Fund Receipt And Payment</strong>
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
           
 <tr align="left">
          <td class="table"> From Date &amp; To Date<font color="#ff2121">*</font></td>
        
             
                  <td> 
                  
                    <div align="left" id="bydate">
                  <input type="text" name="txtFrom_date" id="txtFrom_date"  tabindex="6"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                    <img src="../../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmfundRecReport1.txtFrom_date);"
                         alt="Show Calendar"></img>
           
                    <input type="text" name="txtTo_date" id="txtTo_date"  tabindex="7"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                     
                     <img src="../../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmfundRecReport1.txtTo_date);"
                         alt="Show Calendar"></img>
                         <input type="button" name="submitbtn" value="GO" id="submitbtn" tabindex="30" onclick="Report_date()"/>
           </div>
          </td> 
       
        </tr>
              <tr class="table">
                  <td class="table">
			         <div align="left">
			             Cash Book Year &amp; Month&nbsp;&nbsp;<strong>:</strong>			                       
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
			          <input type="button" name="submitbtn1" value="GO" id="submitbtn1" tabindex="30" onclick="Report1()"/>
			      	</div>
			      </td>
             </tr>
            
              </table>
        </div>
        <table align="center"  cellspacing="3" cellpadding="2" border="1" width="100%" >            
      	<tr class="tdH">
      	<td>
          <div align="center">
         
       <input type="button" id="cmdcancel" name="cancel" value="Exit" onclick="closeWindow()"/>
      </div>
      </td>
      </tr>
      
      </table>
</form>

</body>
</html>