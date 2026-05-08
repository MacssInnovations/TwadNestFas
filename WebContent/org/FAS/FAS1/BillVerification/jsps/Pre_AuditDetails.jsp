<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Pre_Audit Details</title>

  <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>

<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>

 <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

<script type="text/javascript"  src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"  src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript" src="../js/Pre_AuditDetails.js"></script> 
     <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
     <script type="text/javascript" src=".../../../../ReceiptSystem/scripts/Common_ReceiptType.js"></script>
    
    
  
    <script type="text/javascript" language="javascript">
         function foc()
         {
         
         }
         function loadDate()
         {
             var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             
             if(day<=9 && day>=1)
             day="0"+day;
             if(month<=9 && month>=1)
             month="0"+month;
             var year=today.getYear();
             if(year < 1900) year += 1900;
             var monthArray =new Array("January", "February", "March", 
                       "April", "May", "June", "July", "August",
                       "September", "October", "November", "December");
            document.frmPre_AuditDetails.txtDateOfReceipt.value=day+"/"+month+"/"+year;
            document.frmPre_AuditDetails.txtDate.value=day+"/"+month+"/"+year;
            document.frmPre_AuditDetails.cmbCashBookYear.value=year;
            document.frmPre_AuditDetails.cmbCashBookMonth.value=month;
          
            
           // call_date(document.frmPassOrderApproval.txtCrea_date);

        }
</script>
    
</head>
<body onload="loadDate(),LoadAccountingUnitID('LIST_ALL_UNITS');">
<table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Pre_Audit Details Entry </font>
          </div>
        </td>
      </tr>
    </table>
<form name="frmPre_AuditDetails" id="frmPre_AuditDetails">
 <input type="hidden" name="cmbMas_SL_type" id="cmbMas_SL_type" value="7" onchange="doFunction('Load_MasterSL_Code',this.value);"/>
 
                    
 <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
          <h2 class="tab" >General </h2>
           
          <div align="center">
 <table width="633" cellspacing="2" border="1">
     <tr class="table">
      <td colspan="2" class="tdH"><div align="center"><strong>Pre-AuditDetails</strong> </div></td>
    </tr>
    <tr class="table">
      <td colspan="2">&nbsp;</td>
    </tr>
    
                         <tr class="table">
                                    <td width="257">Accounting Unit Name<span class="style1">* </span></td>
                                    <td width="291"> <select name="cmbAcc_UnitCode" id="cmbAcc_UnitCode"  readonly=readonly></select></td>             
                        </tr>
                        <tr class="table">
                          <td>Accounting Unit Office Name<span class="style1">* </span> </td>
                          <td><select name="cmbOffice_code" id="cmbOffice_code" readonly=readonly></select></td>
                        </tr>
                          <tr class="table">
                                    <td width="257">Year<span class="style1">* </span></td>
                                    <td width="291"> <select name="cmbCashBookYear" id="cmbCashBookYear" style="width:153px">
                                    <option value="s">--Select--</option>
                                    <option value="2009">2009</option>
                                    <option value="2010">2010</option>
                                    </select></td>             
                        </tr>
                        
                        <tr class="table">
                                    <td width="257">Month<span class="style1">* </span></td>
                                    <td width="291"> <select name="cmbCashBookMonth" id="cmbCashBookMonth"  onchange="loadcombo()">
                                    <option value="s">--Select--</option>
                                     <option value="01">Jan</option>
                                     <option value="02">Feb</option>
                                     <option value="03">Mar</option>
                                     <option value="04">Apr</option>
                                     <option value="05">May</option>
                                     <option value="06">June</option>
                                     <option value="07">Jul</option>
                                     <option value="08">Aug</option>
                                     <option value="09">Sep</option>
                                     <option value="10">Oct</option>
                                     <option value="11">Nov</option>
                                     <option value="12">Dec</option>
                                    </select></td>             
                        </tr>
                       
                        <tr class="table">
                                    <td width="257">MTC Register NO<span class="style1">* </span></td>
                                    <td width="291"> <select name="cmbMtcRegisterNo" id="cmbMtcRegisterNo"  onchange="getDate();">
                                    <option value="s">--Select--</option>
                                    </select></td>             
                        </tr>
                        <tr class="table">
                          <td>MTC Register Date<span class="style1">* </span> </td>
                          <td><input type="text" name="txtRegisterDate" id="txtRegisterDate" readonly="readonly"></input></td>
                        </tr>
                          <tr class="table">
                          <td>Date Of Receipt<span class="style1">* </span> </td>
                          <td><input type="text" name="txtDateOfReceipt" id="txtDateOfReceipt" readonly="readonly"></input></td>
                        </tr>
                       <tr class="table">
                <td>
                    <div align="left">
                      Received By
                    </div>
                </td>
                <td>
                      
               
                   <div align="left" id="emplist_div_preparedby">
                    <input type="text" name="txtSanction_Estimate_PreparedBy"  id="txtSanction_Estimate_PreparedBy"  maxlength="40" readonly="readonly"/>&nbsp;&nbsp;  
                        <img src="../../../../../images/c-lovi.gif" height="20" alt="empList" onclick="emp_popup_sanction_preparedby();"/>&nbsp;&nbsp; 
                        <input type="text" name="txtEmpID_mas"  id="txtEmpID_mas"  maxlength="6" size="6" onchange="emp_sanction_preparedby();" onkeypress="return numbersonly(event)"/>  
                  </div>
                
               
                </td>
                
              </tr>
              <tr class="table">
                <td>
                    <div align="left">
                        Audited By
                    </div>
                </td>
                <td>
                     
                
                   <div align="left" id="emplist_div_approvedby">
                    <input type="text" name="txtSanction_Estimate_ApprovedBy"  id="txtSanction_Estimate_ApprovedBy"  maxlength="40" readonly="readonly"  />&nbsp;&nbsp; 
                        <img src="../../../../../images/c-lovi.gif"  height="20" alt="empList" onclick="emp_popup_sanction_approvedby();"/>&nbsp;&nbsp;  
                        <input type="text" name="txtEmpID_mas1"  id="txtEmpID_mas1"  maxlength="6" size="6" onchange="emp_sanction_approvedby();" onkeypress="return numbersonly(event)"/>
                  </div> 
                  
                
                </td>
              </tr>
          
                       
                        <tr class="table">
                          <td>Remarks<span class="style1">* </span> </td>
                          <td> <textarea rows="3" cols="25" name="mtxtRemarks" id="mtxtRemarks"></textarea>                          </td>
                        </tr>
                            <tr class="table">
      <td colspan="2" class="table">&nbsp;</td>
    </tr>
                         
                        <tr class="table">
                                   
                                    <td colspan="2" class="tdH">
                                            <div align="center">
                                              <input type="button" name="onsubmit" value="Submit" id="onsubmit" onClick="add();" />   
                                              <input type="button" name="onexit" value="EXIT" id="onexit" onClick="exitfun();" />                                   
                                            </div></td> 
                        </tr>
  </table>
  </div>
  </div>

 
    <div class="tab-page" id="gd" >
          <h2 class="tab" > Details</h2>
           
          <div align="center">
  <table  width="633" border="1">
  <tr class="table">
      <td colspan="2" class="tdH"><div align="center"><strong> Details</strong> </div></td>
    </tr>
    
    <tr class="table">
      <td colspan="2">&nbsp;</td>
    </tr>
                        <tr class="table">
                                    <td width="257">PassOrder No<span class="style1">* </span></td>
                                    <td width="291"> <select name="cmbPassOrderNO" id="cmbPassOrderNO" onchange="getDate1();">
                                       <option value="s">--Select Code--</option>
                                    </select></td>             
                        </tr>
                        
                            <tr class="table">
                          <td>PassOrder Date<span class="style1">* </span> </td>
                          <td><input type="text" name="txtPassOrderDate" id="txtPassOrderDate" readonly="readonly"></input></td>
                        </tr>
                           <tr class="table">
                                    <td width="257">Proceeding No<span class="style1">* </span></td>
                                    <td width="291"> <select name="cmdProceedinnNo" id="cmdProceedinnNo"  onchange="getDate2()">
                                       <option value="s">--Select Code--</option>
                                    </select></td>             
                        </tr>
                          <tr class="table">
                          <td>Proceeding Date<span class="style1">* </span> </td>
                          <td><input type="text" name="txtProceedingDate" id="txtProceedingDate" readonly="readonly"></input></td>
                        </tr>
                        
                           <tr class="table">
                                    <td width="257">Bill No<span class="style1">* </span></td>
                                    <td width="291"> <select name="cmbBillNO" id="cmbBillNO"  onchange="getDate3()">
                                       <option value="s">--Select Code--</option>
                                    </select></td>             
                        </tr>
                          <tr class="table">
                          <td>Bill Date<span class="style1">* </span> </td>
                          <td><input type="text" name="txtBillDate" id="txtBillDate" readonly="readonly"></input></td>
                        </tr>
                        
                            <tr class="table">
                          <td>Bill Amount<span class="style1">* </span> </td>
                          <td><input type="text" name="txtAmount" id="txtAmount" readonly="readonly"></input></td>
                        </tr>
                          
                     
                            <tr class="table"> 
                          <td>Approve?<span class="style1">* </span> </td>
                          <td> <input type="radio" name="r1" id="r1" value="Y" Checked="checked" onclick="caldiv()">Yes <input type="radio" name="r1" id="r1" value="N" onclick="caldiv()"/>No
                          
                          </td>
                        </tr>
                    
                       
                       
                       
                          <tr class="table" style="display='inline'" width="100%"> 
                        
                          <td width="245px"><div id="div1"> Condition Approval?<span class="style1">* </span></div> </td>
                      
                            
                          <td width="245px">  <div id="div2"> <input type="radio" name="r2" id="r2" value="Y"/>Yes <input type="radio" name="r2" id="r2" value="N"/>No
                             </div>
                          </td>
                       
                        </tr>
                         
                      
                    
                        
                           <tr class="table">
                          <td>If Conditional Approval give Particulars<span class="style1">* </span> </td>
                          <td> <textarea rows="3" cols="25" name="txtParticulars" id="txtParticulars" ></textarea></td>
                        </tr>
                        
                           <tr class="table">
                          <td>Date Of Approval/Rejection<span class="style1">* </span> </td>
                          <td><input type="text" name="txtDate" id="txtDate" readonly="readonly"></input></td>
                        </tr>
  
                         <tr class="table">
                          <td>NetAmount<span class="style1">* </span> </td>
                          <td><input type="text" name="txtNetAmount" id="txtNetAmount" maxlength="20" onkeypress="return limit_amt(this,event);"></input></td>
                        </tr>
                        
                        
                            <tr class="table">
                          <td>Liability Journal to be Created?<span class="style1">* </span> </td>
                          <td> <input type="radio" name="r3" id="r3" value="Y" Checked="checked"/>Yes <input type="radio" name="r3" id="r3" value="N"/>No
                          
                          </td>
                        </tr>
                        
                           <tr class="table">
                          <td>Remarks<span class="style1">* </span> </td>
                          <td> <textarea rows="3" cols="25" name="mtxtRemarks1" id="mtxtRemarks1"></textarea>                          </td>
                        </tr>
                         <tr class="table">
                                   
                                    <td colspan="2" class="tdH">
                                            <div align="center">
                                              <input type="button" name="onsubmit1" value="Submit" id="onsubmit1" onClick="add1();" />  
                                              <input type="button" name="onUpdate" value="Update" id="onUpdate" onClick="update1();" />  
                                              <input type="button" name="Delete1" value="Delete" id="Delete1" onClick="delete_GRID();" />   
                                              <input type="button" name="onexit" value="EXIT" id="onexit" onClick="exitfun();" />                                   
                                            </div></td> 
                        </tr>
  
  
  </table>
    </div>
       
     <div align="center" style="display:block" id="div1">
                <table cellspacing="3" cellpadding="2" border="1" width="100%" align="center" >
                        <tr class="table">
                                    <td align="center" class="tdH"> 
                                            <b>EXISTING DETAILS </b>
                                    </td>
                        </tr>
                </table>
                <table id="Existing" cellspacing="2" cellpadding="3" border="1" width="100%" align="center">
                        <tr class="table">
                                     <th width="5%">Select</th>
                                     <th width="5%">Edit</th>
                                     <th width="12%">passorder No</th>
                                     <th width="12%">passorder Date</th>
                                      <th width="14%">Proceeding No</th>
                                     <th width="14%">Proceeding Date</th>
                                     <th width="12%">Bill No</th>
                                     <th width="12%">Bill Date</th>
                                     <th width="12%">Bill Amount</th>
                                      <th width="12%">Approval</th>
                                     <th width="12%">Conditional Approval</th>
                                      <th width="12%">Particulars</th>
                                      <th width="12%">Date of Apprval</th>
                                        <th width="12%">NetAmount</th>
                                       <th width="12%">Journal Approval</th>
                                      <th width="12%">Remarks</th>       
                        </tr>
                <tbody id="tblList" align="center">
                 </tbody>
  </table> 
       
 

  </div>
 </div>
 </div>
</form>
</body>
</html>