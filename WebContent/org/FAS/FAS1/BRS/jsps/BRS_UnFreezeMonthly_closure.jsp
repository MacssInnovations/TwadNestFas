<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false" contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>
<html>
   <head>
      <meta http-equiv="Content-Type"
            content="text/html; charset=windows-1252"/>
      <meta http-equiv="cache-control" content="no-cache"></meta>
      <title>BRS_UnFreezeMonthly_closure</title>
      <link href="../../../../../../css/CalendarControl.css" rel="stylesheet"
            media="screen"/>
      <link href="../../../../../css/Sample3.css" rel="stylesheet"
            media="screen"/>
      <script type="text/javascript"
              src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
      <script type="text/javascript"
              src="../../../../../../org/Library/scripts/checkDate.js"></script>
      <script language="javascript" type="text/javascript"
              src="../scripts/BRS_UnFreezeMonthly_closure.js"></script>
      <script type="text/javascript"
              src="../../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
              
      <script type="text/javascript"
              src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
      <script type="text/javascript"
              src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
          <script type="text/javascript"
	src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script>
    
      <script type="text/javascript" language="javascript">
      function loadyear_month()
         {
       
         var today= new Date(); 
         var day=today.getDate();
         var month=today.getMonth();
         month=month+1;
         var year=today.getYear();
         if(year < 1900) year += 1900;
       
        document.unfreezebrsclosure.txtCB_Year.value=year
        document.unfreezebrsclosure.txtCB_Month.value=month;
        
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
   <body class="table" onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS_ONLY')">
   <form id="unfreezebrsclosure" name="unfreezebrsclosure"  method="POST" action="" onsubmit="return confirmation('<%=s%>')">
         <% 
        HttpSession session=request.getSession(false);
        UserProfile empProfile=(UserProfile)session.getAttribute("UserProfile");
        System.out.println("user id::"+empProfile.getEmployeeId());
        int empid=empProfile.getEmployeeId();
        int  oid=0;
        String oname="";
        String FAS_SU="";
   
        if(session.getAttribute("FAS_SU")!=null && ((String)session.getAttribute("FAS_SU")).equalsIgnoreCase("YES"))
           FAS_SU="YES";
        else
           FAS_SU="NO";   
   %>
         <table cellspacing="2" cellpadding="3" width="100%">
            <tr class="tdH">
               <td colspan="2">
                  <div align="center">
                     <strong>BRS_UnFreezeMonthly_closure</strong>
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
                        <select size="1" name="cmbAcc_UnitCode"
                                id="cmbAcc_UnitCode" tabindex="1"></select>
                     </div>
                  </td>
               </tr>
               <tr align="left">
                  <td class="table">
                     <div align="left">Cash Book Year &amp; Month</div>
                  </td>
                  <td>
                     <div align="left">
                        <input type="text" name="txtCB_Year" id="txtCB_Year"
                               tabindex="3" maxlength="4" size="5"
                               onkeypress="return numbersonly(event)"></input>
                         
                        <select name="txtCB_Month" id="txtCB_Month"
                                tabindex="4">
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
               <tr class="table">
		<td>
		<div align="left">Bank A/C No.</div>
		</td>
		<td>
		<div align="left"><select name="cmbBankAccNo" id="cmbBankAccNo"
			onchange="Bank_Branch_Namee1(this.value);">
			<option value="t">-- Select Bank A/C No ---</option>
		</select> <input type="button" name="Go" id="Go" value="Go"
			onclick="LoadBankAccountNumber()" />
		<input type="hidden"
			name="txtOprMode" id="txtOprMode" tabindex="5"
			style="background-color: #ececec" readonly="readonly" size="50" /></div>
		</td>
	</tr>
            </table>
         </div>
         <div align="left" style="display:none">Trial Balance Status</div>
         <div align="left" style="display:none">
            <input type="radio" id="radTB_status" name="radTB_status" value="Y"
                   checked="checked"></input>
             Yes 
            <input type="radio" id="radTB_status" name="radTB_status" value="N"></input>
             No
         </div>
         <table align="center" cellspacing="3" cellpadding="2" border="1"
                width="100%">
            <tr class="tdH">
               <td>
                  <div align="center">
                     <input type="submit" value="Submit"></input>
                      
                     <input type="button" id="cmdcancel" name="cancel"
                            value="EXIT" onclick="closeWindow()"></input>
                  </div>
               </td>
            </tr>
         </table>
      </form></body>
</html>