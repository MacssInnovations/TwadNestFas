<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache">
    <title>Bank Reconciliation System </title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>         
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
   
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript" src="../scripts/BRS_Twad.js"></script>   
    <script type="text/javascript" language="javascript">
       function loadyear_month()
       {
             var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             var year=today.getYear();
             if(year < 1900) year += 1900;
                        
            document.frmBRSTwad_OBList.txtCB_Year_from.value=year;
            document.frmBRSTwad_OBList.txtCB_Month_from.value=month;            
            
            document.frmBRSTwad_OBList.txtCB_Year_to.value=year;
            document.frmBRSTwad_OBList.txtCB_Month_to.value=month;
      }    
    </script>    
  </head>
  <%
	String s = request.getContextPath();
%>
  <body onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS');clearAll()">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Bank Reconciliation System</font>
          </div>
        </td>
      </tr>
    </table>
    
    
    <form name="frmBRSTwad_OBList" id="frmBRSTwad_OBList" action="../../../../../BRS_Twad?command=PDF" method=post >
    <table cellspacing="1" cellpadding="2" border="1" width="100%">
              
               <tr class="table">
                <td>
                  <div align="left" >
                  	  Accounting Unit Code  <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                     <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);cleartb();LoadBankAccountNumber();">
    
                     </select>
                  </div>
                </td>
              </tr>


              <tr class="table">
                <td>
                  <div align="left">
                    Accounting For Office Code <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
                      
                    </select>
                  </div>
                </td>
              </tr>
              
              <tr class="table">
	            <td>
	              <div align="left">Cash Book Year &amp; Month <font color="#ff2121">*</font></div>
	            </td>
	            <td>
	              <div align="left">
	              From
	                <input type="text" name="txtCB_Year_from" id="txtCB_Year_from" onChange="LoadMonthYear('<%=s%>');"
	                       tabindex="3" maxlength="4" size="5"
	                       onkeypress="return numbersonly(event)"></input>	                 
	                <select name="txtCB_Month_from" id="txtCB_Month_from" onchange="LoadMonthYear('<%=s%>');" tabindex="4">
	                  <option value="">Select Month</option>
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
	               <input type="text" name="txtCB_Year_to" id="txtCB_Year_to" onChange="LoadMonthYear('<%=s%>');"
	                       tabindex="3" maxlength="4" size="5"
	                       onkeypress="return numbersonly(event)"></input>	                 
	                <select name="txtCB_Month_to" id="txtCB_Month_to" onchange="LoadMonthYear('<%=s%>');" tabindex="4">
	                  <option value="">Select Month</option>
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
	          
	           <tr class="table" >
                <td>
                  <div align="left">
                     Bank A/C No. <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                     <select name="cmbBankAccNo" id="cmbBankAccNo" onchange="Bank_Branch_Name(this.value);loadOtherDetails();loadopr_col();" >
                      <option value="">-- Select Bank A/C No ---</option>
                     </select>   
                     <input type="button" name="Go" id="Go" value="Go" onclick="LoadBankAccountNumber()"/> 
                     
                     <input type="hidden" name="txtOprMode" id="txtOprMode"  tabindex="5"  
                          style="background-color: #ececec"  readonly="readonly"  size="50"/>
                     
                       
                  </div>
                </td>
              </tr>
        </table>
        <div align="center">
         <table cellspacing="1" cellpadding="3" width="100%">
           <tr class="tdH">
            <td>
              <div align="center">
                <input type="button" name="sublist" id="sublist" value="PDF" onclick="listPDF()"/>
                <input type="button" name="butList" id="butList" value="List" onclick="ListAll_ob()"/>  
                <input type="button" name="butCan" id="butCan" value="CANCEL" onclick="clearAll();"/>
                <input type="button" name="butExit" id="butExit" value="EXIT"
                       onclick="javascript:self.close();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
    </form>
  </body>
</html>