<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
 <%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>
    <meta http-equiv="cache-control" content="no-cache">
    <title>Bank Reconciliation System </title>
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<script type="text/javascript"
	src="../../../../../org/HR/HR1/EmployeeMaster/scripts/CalendarControl.js"></script>
<script type="text/javascript"
	src="../../../../../org/Library/scripts/checkDate.js"></script>
	    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>    
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
   
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Bank_Account_Number_Loading.js"></script>
   
    <script type="text/javascript" src="../scripts/BRS_MainForm_Create_Pre_Mnth.js"></script>
   
    <script type="text/javascript" language="javascript">
     function loadyear_month1_test()
       {
    	setTimeout('loadyear_month1()',900);

         //   document.frmBRS_MainForm_Create_Pre_Mnth.txtCB_Year.value=year;
         //   document.frmBRS_MainForm_Create_Pre_Mnth.txtCB_Month.value=month;
      }
     function chkAcc_no()
     {
        var Acc_text= document.getElementById("cmbBankAccNo").options[document.getElementById("cmbBankAccNo").selectedIndex].text;
       document.getElementById("hidAccNo").value= Acc_text;
     }
    </script>
    
</head>
  <body onLoad="LoadAccountingUnitID('LIST_ALL_UNITS');clears();" >
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Bank Reconciliation System</font>
          </div>
        </td>
      </tr>
    </table>
    
    
    <form name="frmBRS_MainForm_Create_Pre_Mnth" id="frmBRS_MainForm_Create_Pre_Mnth" method="POST"  
                 action="../../../../../BRS_MainForm_Create_Pre_Mnth.kv" onSubmit="return checkNull()" >
                 <input type="hidden" id="hidMonth" name="hidMonth" value="">
                  <input type="hidden" id="hidYear" name="hidYear" value="">
                   <input type="hidden" id="hidAccNo" name="hidAccNo" value="">
				 <div id="imgfld" style="position:absolute; top: 382px; visibility:hidden; left: 392px; width: 212px; height: 6px;" left=100 top=100>
  <input type="image" name="img1" id="img1" src="../../../../../images/Loading.gif" height="200"></div>
       
        <div class="tab-pane" id="tab-pane-1">
      
        <!-- ------------------------- General Part I --------------------------------  -->
        
        <div class="tab-page">
          <h2 class="tab" >General </h2>
           
          <div align="center">
          
        
          
            <table cellspacing="1" cellpadding="2" border="0" width="100%">              	
            
              <tr class="tdTitle">
                <td colspan="2">
                  <div align="left">
                    <strong>General Details</strong>                  </div>                </td>
              </tr>
              
              
              
              <tr class="table">
                <td>
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>                  </div>                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onChange="common_LoadOffice(this.value);" >
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
              
              <tr class="table" >
                <td>
                  <div align="left">
                     Bank A/C No.                  </div>                </td>
                <td>
                  <div align="left">
                     <select name="cmbBankAccNo" id="cmbBankAccNo" onChange="Bank_Branch_Namee1(this.value),LoadMonthYear();chkAcc_no();">
                      <option value="">-- Select Bank A/C No ---</option>
                     </select>   
                     <input type="button" name="Go" id="Go" value="Go" onClick="LoadBankAccountNumber()"/> 
                     
                     <input type="hidden" name="txtOprMode" id="txtOprMode"  tabindex="5"  
                          style="background-color: #ececec"  readonly="readonly"  size="50"/>
                  </div>                </td>
              </tr>
           
            
                  <div align="left" style="visibility:hidden">
                    <input type="hidden" name="txtBankID" id="txtBankID"  tabindex="5"  
                          style="background-color: #ececec"  readonly="readonly"  size="50"/>

                    <input type="hidden" name="txtBranchID"  id="txtBranchID" 
                     size="50"  style="background-color: #ececec" readonly="readonly" />
                  </div> 
              
              <tr class="table">
                <td>
                  <div align="left">Upto CashBook Year & Month</div>                </td>
                <td>
                  <div align="left">
                   <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"
                     maxlength="4" size="5" onchange="loadmonth_again();"
                     onkeypress="return numbersonly1(event,this)"></input>
               
            	  <select name="txtCB_Month" id="txtCB_Month" tabindex="4" onchange="loadmonth_again();">
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
           
               <div id="imgfld" style="position: absolute; top: 354px; visibility: hidden; left: 378px; width: 212px; height: 6px;"
			left=100 top=100><input type="image" name="img1" id="img1"
			src="../../../../../images/Loading.gif" height="200"></div>
              
               
				  
              <!-- <tr class="table">
                <td>
                  <div align="left">
                     Balance as per Pass Book                  </div>                </td>
                <td>
                  <div align="left">
                  
                    <input type="text" name="txtPBBalance" id="txtPBBalance" size="10" />    
                  </div>                </td>
              </tr> -->
            </table>           
              
              <table cellspacing="1" cellpadding="2" border="0" width="100%">              	
              
              <tr class="tdTitle">
                <td colspan="2">
                  <div align="left">
                    <strong>Search</strong>                  </div>                </td>
              </tr>
              <tr class="table">
                <td width="44%"><div align="left"> Records Of UnAcknowledged Entries </div></td>
                <td width="56%"><div align="left">
                    <input type="text" name="txtPBBalance" id="txtPBBalance" value="------ All ------" size="8" style="background-color: #ececec" readonly="readonly" />
                    <input type="button" name="CashbookYrMnth" value="Go" id="CashbookYrMnth" 
                     onClick="doFunction_brs('loadmnYEr'),doFunction_brs('CashbookYrMnth');" />
                </div></td>
              </tr>
              
              
        <!-- dhana     <tr class="table">
                <td>
                  <div align="left">Records Of Past Six Month from Current Month &amp; Year </div>                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtPBBalance" id="txtPBBalance" value="------ All ------" size="8" style="background-color: #ececec" readonly="readonly" />    
                     <input type="button" name="LastSixMonth" value="Go" id="LastSixMonth" 
                     onclick="doFunction_brs('LastSixMonth');" />
                  </div>                </td>
              </tr>  -->
            </table>
          </div>
        </div>
         
      <input type='hidden' name='RecordCount' id='RecordCount' value='0' />              
      <!-- ------------------------- TWAD  Transactions Part II --------------------------------  -->
         
        <div class="tab-page" id="gd" >
          <h2 class="tab" > Cash Book Transactions </h2>
           
          <div align="center">
            <table id="mytable" cellspacing="0" cellpadding="0"   border="0" >
               <tbody id="grid_body_TotTrans" class="table" align="left" >
               </tbody>                       
            </table>
            <table cellspacing="1" cellpadding="2" border="0" width="100%">
			<tr class="tdTitle">
		<td colspan="2">
		  <div align="left"><strong>No of Records   :</strong>
		   <input name="txtNoofRecords" type="text" id="txtNoofRecords" size="5" readonly="true">
		  </div></td>
	</tr>
              <tr>
                <td colspan="2">
                 
                   <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="0" width="107%">
                      <tr class="table">
                      <th width="2%" ><font size=2> Sl No </font> </th>
                        <th width="8%" ><font size=2> Remittance Date </font> </th>
                        <th width="8%" ><font size=2> Withdrawal Date </font> </th>
                        <th width="14%" ><font size=2> Challan / Voucher No </font> </th>
                        <th width="11%" ><font size=2>Cheque / DD No</font></th>
                        <th width="7%" ><font size=2>CR Amount </font> </th>
                        <th width="7%" ><font size=2>DR Amount </font></th>                        
                        <th width="5%" ><font size=2>Entry Found in Pass Book? </font> </th>
                        <th width="6%" ><font size=2>PassBook Date </font></th>
                        <th width="6%" ><font size=2>Amount in Pass Book </font></th>
                        <th width="7%" ><font size=2>Diff</font> </th>
                        <th width="7%" ><font size=2>Reason for Difference</font> </th>
                        <th width="4%" ><font size=2>Follow-up Action Required? </font> </th>
                        <th width="7%" ><font size=2>Is it a clearance entry based on follow-up?</font></th>
                        
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
        </div>
        <br>
      
      
      
      <!-- ------------------------- Buttons Part --------------------------------  -->
      
      <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
                <input type="SUBMIT" name="butSub" id="butSub" value="SUBMIT"  />
                 &nbsp;&nbsp;&nbsp; 
              <!-- <input type="SUBMIT" name="butSub" id="butSub" value="SUBMIT" onClick="return dateValidation1();"/>
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="clrForm();"/>
                 &nbsp;&nbsp;&nbsp; 
               -->  
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