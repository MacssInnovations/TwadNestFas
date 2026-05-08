<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page contentType="text/html;charset=windows-1252"%>
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>

<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
    <meta http-equiv="cache-control" content="no-cache">
    <title>Phone Certificate system</title>
      
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
     <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/UnitwiseOffice.js"></script>  
    <script type="text/javascript"   src="../scripts/phonecertificate.js"></script>
    <script type="text/javascript"   src="../../ProceedingGeneration/scripts/sanction_proceed_master.js"></script>
    
  
  </head>
      <body onload="LoadAccountingUnitID('LIST_ALL_UNITS');LoadBill_Majortype();" bgcolor="rgb(255,255,225)">
  
        <table cellspacing="1" cellpadding="3" width="100%" >
            <tr class="tdH">
                <td colspan="2">
                    <div align="center">
                        <font size="4">Phone Ceritficate</font>
                    </div>
                </td>
            </tr>
        </table>
    
  <form name="formphone_certificate" id="formphone_certificate" method="POST"
                  action="" >
     <div align="left">
        <table cellspacing="1" cellpadding="2" border="1" width="100%">
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
     <div align="left">
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
                    <div align="left" style="display:none">
                        Phone Certificate Number
                    </div>
                </td>
                <td width="73%">
                    <div align="left" style="display:none">
                        <input type="hidden" name="txtphone_certi_no" id="txtphone_certi_no" tabindex="7"/>
                            
                    </div>
                </td>
            </tr>
            <tr align="left" class="table">
                <td width="27%">
                    <div align="left">
                        Phone Number
                    </div>
                </td>
                <td width="73%">
                    <div align="left">
                            <select size="1" name="cmbphone_no" id="cmbphone_no" tabindex="7"  onChange="loadPhoneDetails();clearfields();">
                            <option value=0>select phone number</option>                     
                        </select>
                        <input type="button" name="but_phone_no" id="but_phone_no" value="GO" onclick="LoadPhoneNo();"/>
                    </div>
                </td>
            </tr>
            <tr align="left" class="table">
                <td width="27%">
                    <div align="left">
                        Custodian Name & Designation
                    </div>
                </td>
                <td width="73%">
                    <div align="left">
                        <input type="text" name="txtcustname_desig" id="txtcustname_desig" size="51" maxlength="50" tabindex="8" class="disab" readonly="readonly"/>&nbsp;
                    </div>
                </td>
            </tr>
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Connection Type
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <input type="text" name="txtconnection_type"  id="txtconnection_type" size="21" maxlength="20" class="disab" readonly="readonly"/>     
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Purpose
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <input type="text" name="txtpurpose" id="txtpurpose" size="21" maxlength="20" class="disab" readonly="readonly"/>
                            </div>
                        </td>
                    </tr> 
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Bill Month & Year
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <select size="1" name="txtbill_month" id="txtbill_month" tabindex="7">
                                    <option value=1>January</option>
                                    <option value=2>February</option>
                                    <option value=3>March</option>
                                    <option value=4>April</option>
                                    <option value=5>May</option>
                                    <option value=6>June</option>
                                    <option value=7>July</option>
                                    <option value=8>August</option>
                                    <option value=9>September</option>
                                    <option value=10>October</option>
                                    <option value=11>November</option>
                                    <option value=12>December</option>
                                </select>
                                &nbsp;&nbsp;&nbsp;&nbsp;
                                <input type="text" name="txtbill_year" id="txtbill_year" size="4" maxlength="4"/>
                            </div>
                        </td>
                    </tr>      
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Invoice Number
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                    <select size="1" name="cmb_invoice_no" id="cmb_invoice_no" >
                                        <option value=0>Select Invoice No</option>
                                    </select>&nbsp;&nbsp;&nbsp;
                                    <input type="button" name="but_invoice_no" id="but_invoice_no" value="GO" onclick="LoadInvNo();"/>
                            </div>
                        </td>
                    </tr>
                    <tr align="left" class="table">
                        <td width="27%">
                            <div align="left">
                                Certificate Text
                            </div>
                        </td>
                        <td width="73%">
                            <div align="left">
                                <textarea name="txt_CertificateText" id="txt_CertificateText" cols="50" tabindex="11" onkeypress="return check_leng('remarks',this.value);" rows="4"></textarea>
                            </div>
                        </td>
                    </tr>
                </table>
            </div>    
            <div align="center">
                <table cellspacing="1" cellpadding="3" width="100%">
                  <tr class="tdH">
                    <td>
                      <div align="center">
                         <input type="button" name="butAdd" id="butAdd" value="ADD" onclick="callphonecertiAdd();"/> 
                            &nbsp;
                         <input type="button" name="butUpdate" id="butUpdate" value="UPDATE" disabled="disabled" onclick="callphonecertiUpdate();"/>
                            &nbsp;
                         <input type="button" name="butDelete" id="butDelete" value="CANCEL" disabled="disabled" onclick="callphonecertiDelete();"/>
                            &nbsp;
                         <input type="button" name="butList" id="butList" value="LIST" onclick="callphonecertiList();"/>
                            &nbsp;      
                         <input type="button" name="butClear" value="CLEARALL" id="butClear" onclick="clearAll();" /> 
                            &nbsp;
                            <input type="button" name="butExit" id="butExit" value="EXIT" onclick="self.close();"/>
                      </div>
                    </td>
                  </tr>
                </table>
            </div>                   
   </form>
 </body>
</html>