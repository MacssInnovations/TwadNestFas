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
   
       
   
  </head>
   <%
	String s = request.getContextPath();
%>  
  <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');clearAll()">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Bank Reconciliation System</font>
          </div>
        </td>
      </tr>
    </table>
    
    
    <form name="frmBRSTwad" id="frmBRSTwad" >
       
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
              <tr class="table" >
                <td>
                  <div align="left">
                     Bank A/C No. <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                     <select name="cmbBankAccNo" id="cmbBankAccNo" onchange="callMonth();Bank_Branch_Name();loadopr_col();" >
                      <option value="">-- Select Bank A/C No ---</option>
                     </select>   
                     <input type="button" name="Go" id="Go" value="Go" onclick="LoadBankAccountNumber()"/> 
                     
                     <input type="hidden" name="txtOprMode" id="txtOprMode"  tabindex="5"  
                          style="background-color: #ececec"  readonly="readonly"  size="50"/>
                     
                       
                  </div>
                </td>
              </tr>
              <tr class="table">
	            <td>
	              <div align="left">Cash Book Year &amp; Month <font color="#ff2121">*</font></div>
	            </td>
	            <td>
	              <div align="left">
	                <input type="text" name="txtCB_Year" id="txtCB_Year" onChange="LoadMonthYear('<%=s%>');"
	                       tabindex="3" maxlength="4" size="5"
	                       onkeypress="return numbersonly(event)"></input>	                 
	                <select name="txtCB_Month" id="txtCB_Month" onchange="clearAll_test();LoadMonthYear('<%=s%>');" tabindex="4">
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
	          
               <tr class="table">
                <td>
                  <div align="left">
                     Bank Name 
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtBankName" id="txtBankName"  tabindex="6"  
                          style="background-color: #ececec"  readonly="readonly"  size="30"/>
                   
                    <input type="hidden" name="txtBankID" id="txtBankID"    
                          style="background-color: #ececec"  readonly="readonly"  size="30"/>
                                             
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">
                     Branch Name  
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtBranchName"  id="txtBranchName" 
                     size="35"  style="background-color: #ececec" readonly="readonly" />
                     
                    <input type="hidden" name="txtBranchID"  id="txtBranchID" 
                     size="35"  style="background-color: #ececec" readonly="readonly" />
                     
                  </div>
                </td>
              </tr>
              
             <tr class="table">
                <td>
                  <div align="left" style="display:none">
                     Operation A/c Code
                  </div>
                </td>
                <td>
                  <div align="left" style="display:none"> 
                    <input type="text" name="txtOprCode"  id="txtOprCode" 
                     size="6"  style="background-color: #ececec" readonly="readonly" />
                     
                  </div>
                </td>
              </tr>  
              
              <tr class="table">
                <td>
                  <div align="left">
                   Part1_OB
                  </div>
                </td>
                <td>
                  <div align="left">
                   <input type="text" name="OB1"
                          id="OB1" maxlength="17" size="18"/>
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                 <td>
                   <div align="left">
                    Part2A_OB
                   </div>
                 </td>
                 <td>
                   <div align="left">
                    <input type="text" name="OB2" 
                           id="OB2" maxlength="17" size="18"/>
                   </div>
                 </td>
              </tr>
              
               <tr class="table">
                 <td>
                   <div align="left">
                    Part2B_OB
                   </div>
                 </td>
                 <td>
                   <div align="left">
                    <input type="text" name="OB3" 
                           id="OB3" maxlength="17" size="18"/>
                   </div>
                 </td>
              </tr>
        </table>
        <div align="center">
         <table cellspacing="1" cellpadding="3" width="100%">
           <tr class="tdH">
            <td>
              <div align="center" id="firstid" display="block:display">
                <input type="button" name="butAdd" id="butAdd" value="Add" onclick="callServer('add')" />
                <input type="button" name="butUpd" id="butUpd" value="Update" onclick="callServer('update')" disabled="disabled"/>
                <input type="button" name="butDel" id="butDel" value="Delete" onclick="callServer('delete')"  disabled="disabled"/>
               <!-- <input type="button" name="butList" id="butList" value="List" onclick="ListAll()"/>  -->
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