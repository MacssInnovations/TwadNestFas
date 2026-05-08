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
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
   
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script>
   
   <script type="text/javascript" src="../scripts/BRS_MainForm_Edit.js"></script>
     <script type="text/javascript" src="../scripts/BRS_MainForm.js"></script> 
   
    <script type="text/javascript" language="javascript">
       function loadyear_month()
       {
             var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             var year=today.getYear();
             if(year < 1900) year += 1900;
                        
            document.frmBRSMainForm.txtCB_Year.value=year;
            document.frmBRSMainForm.txtCB_Month.value=month;
       }    
    </script>
    
    
  </head>
  <%
	String s1 = request.getContextPath();
%>
  <body onload="loadyear_month();LoadAccountingUnitID('LIST_ALL_UNITS');setTimeout( 'LoadBankAccountNumber()' ,3000) " >
  <table cellspacing="1" cellpadding="3" width="108%" >
      <tr class="tdH">
        <td colspan="3">
          <div align="center">
            <font size="4">Bank Reconciliation System - Edit</font>
          </div>
        </td>
      </tr>
  </table>
    
    
      <form name="frmBRSMainForm" id="frmBRSMainForm" method="POST"  
                 action="../../../../../BRS_MainForm_Edit.kv"  onsubmit="return checkNull()" >
       
        <div class="tab-pane" id="tab-pane-1">
        
      
        <!-- ------------------------- General Part I --------------------------------  -->
        
        <div class="tab-page">
          <h2 class="tab" >General </h2>
           
          <div align="center">
          
          
          
            <table cellspacing="1" cellpadding="2" border="0" width="100%">              	
            
              <tr class="tdTitle">
                <td colspan="3">
                  <div align="left">
                    <strong>General Details</strong> 
                    <div id="imgfld" style="position: absolute; top: 354px; visibility: hidden; left: 378px; width: 212px; height: 6px;"
			left=100 top=100><input type="image" name="img1" id="img1"
			src="../../../../../images/Loading.gif" height="200"></div>
                                     </div>                </td>
              </tr>
              
              
              
              <tr class="table">
                <td>
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>                  </div>                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);LoadBankAccountNumber();" >
                    </select>
                  </div>                </td>
              </tr>
              
              
              
              <tr class="table">
                <td>
                  <div align="left">
                    Accounting For Office Code
                    <font color="#ff2121">*</font>                  </div>                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2" >                      
                    </select>
                  </div>                </td>
              </tr>
             <!--   
               <tr class="table" >
                <td>
                  <div align="left">
                     Bank A/C No. <font color="#ff2121">*</font>                  </div>                </td>
                <td>
                  <div align="left">
                     <select name="cmbBankAccNo" id="cmbBankAccNo" 
                             onchange="doFunction_brs('LoadPassAmt','1');" >
                      <option value="">-- Select Bank A/C No ---</option>
                     </select>   
                     
                  
                     <input type="hidden" name="txtOprMode" id="txtOprMode"  tabindex="5"  
                          style="background-color: #ececec"  readonly="readonly"  size="50"/>
                     
                       
                     <input type="hidden" name="txtBankID" id="txtBankID"  tabindex="5"  
                          style="background-color: #ececec"  readonly="readonly"  size="50"/>
                  </div>                </td>
              </tr>
               <tr class="table">
                <td>
                  <div align="left">Cash Book Year &amp; Month <font color="#ff2121">*</font> </div>                </td>
                <td>
                  <div align="left">
                   <input type="text" name="txtCB_Year"  id="txtCB_Year" tabindex="3"
                     maxlength="4" size="5" onkeypress="return numbersonly1(event,this)"></input>
               
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
                  </div>                </td>
              </tr>
             --><tr class="table">
		<td>
		<div align="left">Bank A/C No.<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select name="cmbBankAccNo" id="cmbBankAccNo" onchange="Bank_Branch_Namee1(this.value);LoadMonthYear('<%=s1%>');">
                <!--  onchange="closeBRS('<%=s1%>');"> -->
              
			<option value="">-- Select Bank A/C No ---</option>
		</select>
		<input type="button" name="goo" id="goo" value="Go" onclick="LoadBankAccountNumber();"></input>
			<input type="hidden" name="txtOprMode" id="txtOprMode" tabindex="5" style="background-color: #ececec" readonly="readonly" size="50" />
			</div>
		</td>
	</tr> 
             <tr class="table">
		<td>
		<div align="left">Cash Book Year &amp; Month<font
			color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="txtCB_Year" readonly="readonly"
			id="txtCB_Year" tabindex="3" maxlength="4" size="5"
			onkeypress="return numbersonly1(event,this)"></input> 
			<select name="txtCB_Month" id="txtCB_Month" tabindex="4" readonly="readonly"
			onchange="LoadMonthYear();checkStatus('<%=s1%>');countCheck('<%=s1%>');">
			<option value="s">--- Select ---</option>
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
		</select></div>
		</td>
	</tr>
              
             
           
              
              
              
             
              
               <tr class="table" style="visibility: hidden;">
                <td>
                  <div align="left">
                     Balance as per Pass Book                  </div>                </td>
                <td>
                  <div align="left">      
                  <input type="hidden" readonly name="pbReadonly" id="pbReadonly" size="15" />            
                    <input type="hidden" name="txtPBBalance" id="txtPBBalance" size="10" />
                    <input type="hidden" name="txtBranchID"  id="txtBranchID" 
                     size="50"  style="background-color: #ececec" readonly="readonly" />
                  </div>                </td>
              </tr> 
              
              <tr class="table">
                <td colspan="2">
                  <div align="left"><center>
              <input type="button"  id="but" name="but" value="Go" onclick="doFunction_brs_edit('LoadTWADTransactions','1');">
         </center> </div></td></tr>
            </table>
          </div>
        </div>
         
      <input type='hidden' name='RecordCount' id='RecordCount' value='0' />
      <input type='hidden' name='RecordCountNT' id='RecordCountNT' value='0' />  
      
         
      <!-- ------------------------- TWAD  Transactions Part II --------------------------------  -->
         
        <div class="tab-page" id="gd" >
          <h2 class="tab" > Cash Book Transactions </h2>
           
          <div align="center">
           
           
            <table id="mytable" cellspacing="0" cellpadding="0"   border="0" >
               <tbody id="grid_body_TotTrans" class="table" align="left" >
               </tbody>                       
            </table>
            
             
          
          
            <table cellspacing="1" cellpadding="2" border="0" width="100%">
              <tr>
                <td colspan="2">
                 
                   <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="0" width="100%">
                      <tr class="table">
					    <th > Found in Passbook </th>
                        <th > Remittance Date </th>
                        <th > Withdrawal Date </th>
                        <th > Challan / Voucher No </th>
                        <th >Cheque / DD No</th>
                        <th >CR Amount </th>
                        <th >DR Amount </th>                        
                        <th >Entry Found in Pass Book ?  </th>
                        <th >Pass Book Date </th>
                        <th >Amount in Pass Book </th>
                        <th >Difference </th>
                        <th >Reason for Difference </th>
                        <th >Follow-up Action Required ?  </th>
                        <th >Is it a clearance entry based on follow-up ?</th>
                      </tr>
                        <tbody id="grid_body_TWAD" class="table" align="left" >
                       </tbody>
                       
                    </table>
                  </div>
                  
                </td>
              </tr>
            </table>
            
            
            
          </div>
            
        </div>
        
        
        
        
        <!-- -------------------------Non TWAD  Transactions Part III --------------------------------  -->
        
        
        <div class="tab-page" id="gd" >
          <h2 class="tab" >Not Remitted/Withdrawn but shown in the pass sheet</h2>           
          <div align="center">                  
                  
            <table cellspacing="1" cellpadding="2" border="0"  width="100%">

             <tr class="table">
                <td>
                  <div id="grid" style="display:block">
                    <table id="mytable_details" cellspacing="3" cellpadding="2"
                           border="0" width="100%">
                      <tr class="table">
					  <th > Delete </th>
                        <th> Pass Book Date </th>
                        <th >Particulars</th>
                        <th >Cheque No.</th>
                        <th >Details</th>
                        <th >CR Amount </th>
                        <th >DR Amount</th>
                        <th >Type of Transaction</th>
                        <th >Follow-up Action Required ?  </th>
                        <th >Is it a clearance entry based on follow-up ?</th>
                       <th >To Be Journalized</th>
                      </tr>
                       <tbody id="grid_body_NONTWAD" class="table" align="left" >
                       </tbody>
                    </table>
                  </div>
                </td>
              </tr>
                       
         </table>
        </div>
      </div>
      
              
     </div>
      
      
      <br>
      
      <!-- ------------------------- Buttons Part --------------------------------  -->
      
      <div align="center">
        <table cellspacing="1" cellpadding="4" width="108%">
          <tr class="tdH">
            <td>
              <div align="center">
                <input type="SUBMIT" name="butSub" id="butSub" value="SUBMIT" onClick="return dateValidation1();"/>
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="clrForm();"/>
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="exit();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
      
      
      
    </form>
  </body>
</html>