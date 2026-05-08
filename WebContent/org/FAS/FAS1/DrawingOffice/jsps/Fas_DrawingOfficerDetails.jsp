<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>

<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=ISO-8859-1">
<title>Drawing Officer Details</title>
<script type="text/javascript" src="../scripts/Drawingofficerdetails.js"></script>
 <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
 
 <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
            
    
     <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script> 
 
     
    

<style type="text/css">
<!--
.style1 {color: #FF0000}
-->
</style>
</head>
<%String s=request.getContextPath(); %>
<body  onload="refresh();LoadAccountingUnitID('LIST_ALL_UNITS')">
<form name="FAS_DrawingOfficerDetails" id="FAS_DrawingOfficerDetails" method="post" action="../../../../../DrawingOfficerDetails">
  <input type="hidden" name="cmbMas_SL_type" id="cmbMas_SL_type" value="7"   onchange="doFunction('Load_MasterSL_Code',this.value);"/>
                    
 <table width="533" align="center" cellspacing="2" border="1">
     <tr class="table">
      <td colspan="2" class="tdH"><div align="center"><strong>Drawing Officer Details</strong> </div></td>
    </tr>
    <tr class="table">
      <td colspan="2">&nbsp;</td>
    </tr>
                        <tr class="table">
                                    <td width="257">Accounting Unit Name<span class="style1">* </span></td>
                                    <td width="291"> <select name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" style="width:300px" readonly=readonly  onChange="common_LoadOffice(this.value);"></select></td>             
                        </tr>
                        <tr class="table">
                          <td>Accounting Unit Office Name<span class="style1">* </span> </td>
                          <td><select name="cmbOffice_code" id="cmbOffice_code" style="width:300px" readonly=readonly></select></td>
                        </tr>
                        
                       <tr class="table">
                <td width="40%">
                  <div align="left">Current Drawing officer Name <font color="#ff2121">*</font> </div>
                </td>
              <td width="60%">
            <table align="left">
             <tr align="left">
             <td>
                  <div align="left">
                        <select size="1" name="cmbMas_SL_Code" id="cmbMas_SL_Code" >
                                
                          <option value="s">--Select Code--</option>
                        </select>
                        
                  </div>
              </td>
              <td>
                  <div align="left" id="offlist_div_master"  style="display:none">
                            
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList" onclick="jobpopup_master();"></img>
                            <input type="text" name="txtOfficeID_mas" id="txtOfficeID_mas" maxlength="4" size="5"  onblur="mas_office(this.value);" />
                          </div>
                   <div align="left" id="emplist_div_master"  >
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="employee_popup_master();"></img>
                            <input type="text" name="txtEmpID_mas" id="txtEmpID_mas" maxlength="6" size="6"  onblur="callemp('<%=s %>');" />
                          </div>
                 <input type="hidden" name="cmbSL_type" id="cmbSL_type"/>
                  <input type="hidden" name="cmbSL_Code" id="cmbSL_Code"/>
               </td>
             
            </tr>
           </table>
        </td>
              </tr>
              
               
              
              
              <tr class="table">
                          <td>Drawing Officer's Designation<span class="style1">* </span></td>
                           <td><select name="cboOfficerDesignation" id="cboOfficerDesignation" style="width:300px" >
                           <option value="s">---Select---</option>
                           </select></td>
                        </tr>
                    
                       
                        <tr class="table">
                          <td>Remarks<span class="style1">* </span> </td>
                          <td> <textarea rows="3" cols="18" name="mtxtRemarks" id="mtxtRemarks"></textarea>                          </td>
                        </tr>
                            <tr class="table">
      <td colspan="2" class="table">&nbsp;</td>
    </tr>
                         
                        <tr class="table">
                                   
                                    <td colspan="2">
                                            <div align="center">
                                              <input type="button" name="onsubmit" value="ADD" id="onsubmit" onClick="add();" />  
                                              <input type="button" name="oncancel" id="oncancel" value="CANCEL" disabled="disabled" onclick="Cancel();"/>
                                              <input type="button" name="ondelete" value="DELETE" id="ondelete" onClick="delete1();" disabled="disabled" /> 
                                              <input type="button" name="onupdate" value="UPDATE" id="onupdate" onClick="update();" disabled="disabled" /> 
                                              <input type="button" name="onlist" value="List" id="onlist" onClick="Load_DrawOff();" />  
                                              <input type="button" name="onexit" value="EXIT" id="onexit" onClick="exitfun();" />                                   
                                            </div></td> 
                        </tr>
  </table>
 
</form>
</body>
</html>