<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Sanction Proceeding System</title>
      
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
     
     <script language="javascript" type="text/javascript">
                function closeWindow()
                {                
                    window.open('','_parent','');                
                    window.close(); 
                    window.opener.focus();
                }
    </script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
    
    
  <!--  <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>
    <script type="text/javascript" src="../scripts/Common_ReceiptType.js"></script> -->
    
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/UnitwiseOffice.js"></script>  
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
    <script type="text/javascript"   src="../scripts/sanction_proceed_master.js"></script>
   <!-- <script type="text/javascript"   src="../scripts/AccHeadCode.js"></script>-->
  </head>
      
      <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');LoadBill_Majortype();callSancAuthList();" bgcolor="rgb(255,255,225)">
  <!-- //////////////////////Sanction Master ///////////////////////////////////-->
        <table cellspacing="1" cellpadding="3" width="100%" >
            <tr class="tdH">
                <td colspan="2">
                    <div align="center">
                        <font size="4">General Sanction Proceedings(Single Payee)</font>
                    </div>
                </td>
            </tr>
        </table>
    
  <form name="formsanc_proceed" id="formsanc_proceed_General" method="POST"
                  action="../../../../../sanction_proceed_master?Command=Add" onsubmit="return checkfields();">
    <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
            <h2 class="tab" >General </h2>
           
                <div align="center">
                    <table cellspacing="1" cellpadding="2" border="1" width="100%">
                        <tr class="tdTitle">
                             <td colspan="2">
                                <div align="left">
                                    <strong>General Details</strong>
                                </div>
                            </td>
                        </tr>
                        <tr class="table">
                            <td width="27%">
                                <div align="left">
                                    Accounting Unit Code 
                                        <font color="#ff2121">*</font>
                                </div>
                            </td>
                            <td width="73%">
                                <div align="left">
                                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);">
                                    </select>
                                </div>
                            </td>
                        </tr>
                        <tr class="table">
                            <td width="27%">
                                <div align="left">
                                    Accounting For Office Code
                                        <font color="#ff2121">*</font>
                                </div>
                            </td>
                            <td width="73%">
                                <div align="left">
                                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2" >
                  
                                    </select>   
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
                    <table cellspacing="1" cellpadding="3" border="1" width="100%">
                    <tr align="left" class="table"> 
                        <td width="27%">
                            <div>
                                Bill Major Type
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <select size="1" name="txtbill_majr_code" id="txtbill_majr_code" tabindex="5" onchange="loadMinorType()">
                                    <option value=0>select bill major type</option>
                                </select>
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table"> 
                        <td width="27%">
                            <div>
                                Bill Minor Type
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <select size="1" name="txtbill_minr_code" id="txtbill_minr_code" tabindex="6" onchange="loadSubType()">
                                    <option value=0>select bill minor type</option>
                                </select>
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Bill Sub Type
                            </div>
                        </td>
                 
                        <td width="73%">
                            <div align="left">
                                <select size="1" name="txtbill_sub_code" id="txtbill_sub_code" tabindex="7">
                                    <option value=0>select bill sub type</option>                     
                                </select>
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Payee Type
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <input type="radio" name="rad_payee_type" id="rad_payee_type"
		                           value="E" checked="checked" />Employee
		                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                                <input type="radio" name="rad_payee_type" id="rad_payee_type"
		                           value="U" />Privileged Users &nbsp;&nbsp;&nbsp;&nbsp; 
                                <input type="radio" name="rad_payee_type" id="rad_payee_type"
		                           value="P" />Pensioner &nbsp;&nbsp;&nbsp;&nbsp; 
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Payment Type for Employee/Privileged Users
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <input type="text" name="txt_payment_type" id="txt_payment_type" size="11" maxlength="10" tabindex="8" class="disab"/>&nbsp;
                                &nbsp;<input type="button" name="but_payment_type" id="but_payment_type" value="Payment Type" onclick="loadpayment_type();"/>
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Payee code
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <input type="text" name="txt_payee_code"  id="txt_payee_code" size="7" maxlength="6" onchange="emp_payee_code();"/>     
                                    <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="emp_popup_payee();"/>
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Payee Name & Designation
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <input type="text" name="txtpayee_namedesig" id="txtpayee_namedesig" size="45" maxlength="40" class="disab"/>
                            </div>
                        </td>
                    </tr> 
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Sanctioning Authority
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <!--<input type="text" name="txtsanc_auth" id="txtsanc_auth" size="6" maxlength="6"/>-->
                                <select size="1" name="cmb_sanc_auth" id="cmb_sanc_auth" tabindex="12" > 
                                <!--onchange="Loadsanctioned_by();">-->
                                    <option value=0>Select Sanctioning Authority</option>
                                </select>
                            </div>
                        </td>
                    </tr>      
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Sanctioned By
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <input type="text" name="txtsanc_by"  id="txtsanc_by" size="7" maxlength="6" onchange="emp_sanc_by();"/>     
                                    <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="emp_popup_sancBy();"/>
                                <!--<select size="1" name="cmb_sanc_by" id="cmb_sanc_by" tabindex="12" onchange="loadsanctiondetails();">
                                    <option value=0>Select Sanctioned By</option>
                                </select>-->
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Name & Designation
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <input type="text" name="txtname_desig" id="txtname_desig" size="45" maxlength="40" readonly="readonly" class="disab" />
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Office
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <input type="text" name="txt_office" id="txt_office" size="25" maxlength="20" readonly="readonly" class="disab"/>
                            </div>
                        </td>
                    </tr>
                 
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left" style=display:none>
                                Sanction Proceeding No
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left" style=display:none>
                                <input type="hidden" name="txt_sancpro_no" id="txt_sancpro_no" size="6" maxlength="6"/>(Auto Generated)
                            </div>
                        </td>
                    </tr>
                    
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Sanction Proceeding Date
                                <font color="#ff2121">*</font>
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">    
                                <input type="text" name="txt_sancpro_date" id="txt_sancpro_date"  tabindex="9"
                                    maxlength="10" size="11"
                                    onfocus="javascript:vDateType='3';"
                                    onkeypress="return calins(event,this);"/>
                     
                                <img src="../../../../../images/calendr3.gif"
                                     onclick="showCalendarControl(document.formsanc_proceed.txt_sancpro_date,0);"
                                     alt="Show Calendar">
                                </img>

                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Sanction Date
                                <font color="#ff2121">*</font>
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <input type="text" name="txt_sanc_date" id="txt_sanc_date"  tabindex="10"
                                    maxlength="10" size="11"
                                    onfocus="javascript:vDateType='3';"
                                    onkeypress="return calins(event,this);"
                                    onblur="datefun(txt_sancpro_date.value,this.value)"/>
                     
                                <img src="../../../../../images/calendr3.gif"
                                     onclick="showCalendarControl(document.formsanc_proceed.txt_sanc_date,0);"
                                     alt="Show Calendar">
                                </img>
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Total Sanctioned Amount (in Rs.)
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <input type="text" name="txt_sanc_amt" id="txt_sanc_amt" size="10" maxlength="10" onkeypress="return numbersonly(event);" readonly="readonly" class="disab"/>
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Remarks
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                               <textarea name="txt_GeneralRemarks" id="txt_GeneralRemarks" cols="50" tabindex="11" onkeypress="return check_leng('remarks',this.value);"
                                    rows="4"></textarea>
                            </div>
                        </td>
                    </tr>     
                </table>
            </div>    
    
            <div class="tab-page" id="gd" >
                <h2 class="tab" > Details</h2>
                    <div align="center">
           
                        <table cellspacing="1" cellpadding="2" border="1" width="100%">
                            <tr>
                                <td colspan="2">
                                    <div id="sanc_det_disp" >
                        
                                        <table cellspacing="1" cellpadding="2" border="1"
                                            width="100%">
                                            <tr class="tdTitle">
                                                <td colspan="2">
                                                    <div align="left">
                                                        <strong>Invoice Details</strong>
                                                    </div>
                                                </td>
                                            </tr>
                                        </table>
                                        
                                       <table cellspacing="1" cellpadding="2" border="1"
                                            width="100%">
                                           <tr align="left" class="table">
                                                <td width="27%">
                                                    <div align="left">
                                                        Invoice No.
                                                        <font color="#ff2121">*</font>
                                                    </div>
                                                </td>
                                                <td width="73%">
                                                    <div align="left">
                                                            <select size="1" name="cmb_invoice_no" id="cmb_invoice_no" onchange="LoadInvDetails();">
                                                                <option value=0>Select Invoice No</option>
                                                            </select>&nbsp;&nbsp;&nbsp;
                                                            <input type="button" name="but_invoice_no" id="but_invoice_no" value="GO" onclick="LoadInvNo();"/>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr align="left" class="table">
                                                <td width="27%">
                                                    <div align="left">
                                                        Invoice Date
                                                    </div>
                                                </td>
                                                <td width="73%">
                                                    <div align="left">
                                                        <input type="text" name="txt_invoice_date" id="txt_invoice_date" size="11" maxlength="10" readonly/>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr align="left" class="table">
                                                <td width="27%">
                                                    <div align="left">
                                                        Particulars
                                                    </div>
                                                </td>
                                                <td width="73%">
                                                    <div align="left">
                                                        <input type="text" name="txt_particulars" id="txt_particulars" size="30" maxlength="25" readonly/>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr align="left" class="table">
                                                <td width="27%">
                                                    <div align="left">
                                                        Invoice Amount
                                                    </div>
                                                </td>
                                                <td width="73%">
                                                    <div align="left">
                                                        <input type="text" name="txt_invoice_amt" id="txt_invoice_amt" size="10" maxlength="9" readonly />
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr align="left" class="table">
                                                <td width="27%">
                                                    <div align="left">
                                                        Sanctioned Amount
                                                        <font color="#ff2121">*</font>
                                                    </div>
                                                </td>
                                                <td width="73%">
                                                    <div align="left">
                                                        <input type="text" name="txt_sanctioned_amt" id="txt_sanctioned_amt" size="10" maxlength="9"  onkeypress="return numbersonly(event)"/>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr align="left" class="table">
                                                <td width="27%">
                                                    <div align="left">
                                                        Deduted Amount
                                                        <font color="#ff2121">*</font>
                                                    </div>
                                                </td>
                                                <td width="73%">
                                                    <div align="left">
                                                        <input type="text" name="txt_deducted_amt" id="txt_deducted_amt"  size="10" maxlength="10"  onkeypress="return numbersonly(event)"/>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr align="left" class="table">
                                                <td width="27%">
                                                    <div align="left">
                                                        Net Amount
                                                        <font color="#ff2121">*</font>
                                                    </div>
                                                </td>
                                                <td width="73%">
                                                    <div align="left">
                                                        <input type="text" name="txt_net_amt" id="txt_net_amt" size="10" maxlength="10" onfocus="calcnet_amt()" onkeypress="return numbersonly(event)"/>
                                                    </div>
                                                </td>
                                            </tr>
                                           <tr align="left" class="table">
                                                <td width="27%">
                                                    <div align="left">
                                                        A/C Head
                                                    </div>
                                                </td>
                                                <td width="73%">
                                                    <div align="left">
                                                        <input type="text" name="txtAcc_HeadCode" 
                                                               id="txtAcc_HeadCode" maxlength="8" class="disab"
                                                                onkeypress="return numbersonly(event)"
                                                                onchange="sixdigit('val');" size="7"/>&nbsp;&nbsp;&nbsp;&nbsp;
                                                        <input type="text" name="txtAcc_HeadDesc" readonly="readonly" 
                                                               id="txtAcc_HeadDesc" style="background-color: #ececec"  maxlength="125" size="70"/>
                                                    </div>
                                                </td>
                                            </tr>
                                            
             
                                            <tr align="left" class="table">
                                                <td width="27%">
                                                    <div align="left">
                                                        Payable to
                                                        <font color="#ff2121">*</font>
                                                    </div>
                                                </td>
                                                <td width="73%">
                                                    <div align="left">
                                                        <input type="text" name="txt_Payable_to" id="txt_Payable_to"  size="7" maxlength="6" onchange="emp_payableTo();"/>
                                                        <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="emp_popup_payableTo();"/>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr align="left" class="table">
                                                <td width="27%">
                                                    <div align="left">
                                                        Budge Provided
                                                        <font color="#ff2121">*</font>
                                                    </div>
                                                </td>
                                                <td width="73%">
                                                    <div align="left">
                                                        <input type="text" name="txt_Budget_Provided" id="txt_Budget_Provided" size="10" maxlength="10" onkeypress="return numbersonly(event)"/>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr align="left" class="table">
                                                <td width="27%">
                                                    <div align="left">
                                                        Budge so far spent
                                                        <font color="#ff2121">*</font>
                                                    </div>
                                                </td>
                                                <td width="73%">
                                                    <div align="left">
                                                        <input type="text" name="txt_Budget_sofar_spent" id="txt_Budget_sofar_spent" size="10" maxlength="10" onkeypress="return numbersonly(event)"/>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr align="left" class="table">
                                                <td width="27%">
                                                    <div align="left">
                                                        Amount Deducted for this bill
                                                        <font color="#ff2121">*</font>
                                                    </div>
                                                </td>
                                                <td width="73%">
                                                    <div align="left">
                                                        <input type="text" name="txt_amt_deducted_bill" id="txt_amt_deducted_bill" size="10" maxlength="10" onfocus="calbugetspent()" onkeypress="return numbersonly(event)"/>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr align="left" class="table">
                                                <td width="27%">
                                                    <div align="left">
                                                        Balance Amount after this bill
                                                        <font color="#ff2121">*</font>
                                                    </div>
                                                </td>
                                                <td width="73%">
                                                    <div align="left">
                                                        <input type="text" name="txt_bal_amt_after_bill" id="txt_bal_amt_after_bill" size="10" maxlength="10" onfocus="calcbal_amt()" onkeypress="return numbersonly(event)"/>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr align="left" class="table">
                                                <td width="27%">
                                                    <div align="left">
                                                        Remarks
                                                    </div>
                                                </td>
                                                <td width="73%">
                                                    <div align="left">
                                                        <textarea name="txt_DetailsRemarks" id="txt_DetailsRemarks" cols="50" tabindex="12" onkeypress="return check_leng('remarks',this.value);"
                                                                rows="4"></textarea>
                                                    </div>
                                                </td>
                                            </tr>
                                            <tr align="center" class="tdTitle">
                                                <td colspan="2" height="23">
                                                    <div align="center">
                                                        <table border="0">
                                                            <tr>
                                                                <td>
                                                                    <input type="button" name="cmdadd" id="cmdadd"
                                                                            value="ADD" onclick="ADD_GRID()" style="display:block"/>
                                                                </td>
                                                                <td>
                                                                    <input type="button" name="cmdupdate" value="UPDATE"
                                                                        id="cmdupdate" onclick="update_GRID()"
                                                                        style="display:none"/>
                                                                </td>
                                                                <td>
                                                                    <input type="button" name="cmddelete" value="DELETE"
                                                                        id="cmddelete" onclick="delete_GRID()"
                                                                        disabled="disabled"/>
                                                                </td>
                                                                <td>
                                                                    <input type="button" name="cmdclear" value="CLEAR ALL"
                                                                        id="cmdclear" onclick="clear_main_fields();"/>
                                                                </td>
                                                            </tr>
                                                        </table>
                                                    </div>
                                                </td>
                                              </tr>
                                        </table> 
                                        
                                            <div id="grid" style="display:block">
                                                <table id="mytable" cellspacing="3" cellpadding="2"
                                                        border="1" width="100%">
                                                    <tr class="table">
                                                        <th>Select</th>
                                                        <th>Invoice No.</th>
                                                        <th>Invoice Date</th>
                                                        <th>Particulars</th>
                                                        <th>Invoice Amount </th>
                                                        <th>Sanctioned Amount</th>
                                                        <th>Deduted Amount</th>
                                                        <th>Net Amount</th>                       
                                                        <th>A/C Head</th>
                                                        <th>A/C Head Description</th>
                                                        <th>Payable to</th>
                                                        <th>Budge Provided</th>
                                                        <th>Budge so far spent</th>
                                                        <th>Amount Deducted for this bill</th>
                                                        <th>Balance Amount after this bill</th>
                                                        <th>Remarks</th>
                                                    </tr>
                                                        <tbody id="grid_body" class="table" align="left" >
                                                        </tbody>
                                            </table>
                                        </div>           
                                    </div>
                                </td>
                            </tr>
                        </table>
                    </div>
                    
            </div>
                <div align="center">
                    <table cellspacing="1" cellpadding="3" width="100%">
                        <tr class="tdH">
                            <td>
                                <div align="center">
                                    <input type="submit" name="butSub" id="butSub" value="SUBMIT" />
                                            &nbsp;&nbsp;&nbsp;
                                    <input type="button" name="butCancel" id="butCancel" value="CANCEL" 
                                            onclick="clrForm();"/>
                                            &nbsp;&nbsp;&nbsp; 
                                    <input type="button" name="butList" id="butList" value="EXIT"
                                            onclick="exitmethod();"/>
                                </div>
                            </td>
                        </tr>
                    </table>
                </div>
    </div>
   </form>
 </body>
</html>