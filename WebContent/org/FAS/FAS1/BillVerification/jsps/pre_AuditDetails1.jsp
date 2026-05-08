<%@ page language="java" contentType="text/html; charset=ISO-8859-1"
    pageEncoding="ISO-8859-1"%>
  
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ page import="Servlets.FAS.FAS1.CivilBills.servlets.LoadDriver" %>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf"%>

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
  <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>

<script type="text/javascript"  src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"  src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript" src="../scripts/Pre_AuditDetails1.js"></script> 
     <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
     <script type="text/javascript" src=".../../../../ReceiptSystem/scripts/Common_ReceiptType.js"></script>
     <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>
    
     <!--  <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl_forChequeDate.js"></script>  -->
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    
  
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
            document.frmPre_AuditDetails.txtAuditDate.value=day+"/"+month+"/"+year;
            document.frmPre_AuditDetails.cboCashBook_Year.value=year;
            
           // call_date(document.frmPassOrderApproval.txtCrea_date);

        }
</script>
   
    <% String s=request.getContextPath(); %> 
    <% 
  		  HttpSession session1 = request.getSession();
  		
  		  UserProfile empProfile=(UserProfile)session1.getAttribute("UserProfile");      
  		
  		  int empid=empProfile.getEmployeeId();
  		  System.out.println("empid"+empid);
  		  String user_id=(String)session1.getAttribute("UserId");
  		  System.out.println("---------user_id--------"+user_id);
  		  %>
<%System.out.println(s); %>
<%System.out.println(user_id); %>
</head>
<body onload="loadDate(),LoadAccountingUnitID('LIST_ALL_UNITS');loadDetails('<%=s %>');loadEmpid('<%=s %>',<%= empid%>);loadEmpid1('<%=s %>',<%= empid%>);">
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

 <input type="hidden" name="ori_unit" id="ori_unit" >
<input type="hidden" name="ori_office" id="ori_office" >
                    
 <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
          <h2 class="tab" >General </h2>
           
          <div align="center">
 <table width="100%" cellspacing="2" border="1">
     <tr class="table">
      <td colspan="2" class="tdH"><div align="center"><strong>Pre-AuditDetails</strong> </div></td>
    </tr>
  
    
                         <tr class="table">
								<td>
							    <div align="left">Accounting Unit Code <font color="#ff2121">*</font>	    </div></td>
								<td>
								  <div align="left">
								    <select size="1" name="cmbAcc_UnitCode"
									id="cmbAcc_UnitCode" tabindex="1"
									onchange="common_LoadOffice(this.value);">
							          </select>
						      </div></td>
						</tr>
                        <tr class="table">
                          <td>Accounting Unit Office Name<span class="style1">* </span> </td>
                          <td><select name="cmbOffice_code" id="cmbOffice_code" ></select></td>
                        </tr>
                        <tr class="table">
		<td>
		<div align="left">Bill Year<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><input type="text" name="cboCashBook_Year" id="cboCashBook_Year" size="5" onkeypress="return numbersonly(event,this)" maxlength="4">

		</div>
		</td>
	</tr>
	<tr class="table">
		<td>
		<div align="left">Bill Month<font color="#ff2121">*</font></div>
		</td>
		<td>
		<div align="left"><select size="1" name="cboCashBook_Month"
			id="cboCashBook_Month" onchange="LoadBillNo();">
			<option value="s">---Select---</option>
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
                        
                      <tr class="table">
                                    <td width="257">Bill No(SanctionId)<span class="style1">* </span></td>
                                    <td width="291"> <select name="cmbBillNO" id="cmbBillNO"  onchange="getBillDetails()">
                                       <option value="s">--Select BillNo--</option>
										
                                    </select>
                                    <input type="hidden" name="majorhidden" id="majorhidden"></input>
                                    </td>  
                                             
                        </tr>
                        <tr class="table">
                                    <td width="257">Bill Major Type<span class="style1">* </span></td>
                                    <td width="291"> <select name="cmbMajorType" id="cmbMajorType"  >
                                    <option value="s">--Select--</option>
                                    
                                    </select></td>             
                        </tr>
                            <tr class="table">
                          <td>Bill Date<span class="style1">* </span> </td>
                          <td><input type="text" name="txtBillDate" class="light" id="txtBillDate" readonly="readonly" ></input></td>
                        </tr>
                       <tr class="table">
                          <td>Pre Audit sent on Date<span class="style1">* </span> </td>
                          <td><input type="text" name="preauditsentondate" class="light" id="preauditsentondate" readonly="readonly" ></input></td>
                        </tr>
                            <tr class="table">
                          <td>Bill Amount<span class="style1">* </span> </td>
                          <td><input type="text" name="txtBillAmount" class="light" id="txtBillAmount"  readonly="readonly"></input></td>
                        </tr>
                         <tr class="table">
                          <td>Bill MinorType<span class="style1">* </span> </td>
                          <td>
                          <input type="hidden" name="txtBillMinorType" class="light" id="txtBillMinorType"  readonly="readonly">
                          </input>
                          <input type="text" name="minor_desc_id" class="light" id="minor_desc_id"  readonly="readonly">
                          </input>
                          </td>
                        </tr>
                         <tr class="table">
                          <td>Bill SubType<span class="style1">* </span> </td>
                          <td>
                          <input type="hidden" name="txtBillSubType" class="light" id="txtBillSubType"  readonly="readonly"></input>
                          <input type="text" name="sub_desc_id" class="light" id="sub_desc_id" size="50" readonly="readonly"></input>
                          </td>
                        </tr>
                         <tr class="table">
                          <td>Drawing Officer Approval Date<span class="style1">* </span> </td>
                          <td><input type="text" name="txtApprovalDate" class="light" id="txtApprovalDate"  readonly="readonly"></input></td>
                        </tr>
                        <tr class="table">
                          <td>Treasury Verification Date<span class="style1">* </span> </td>
                          <td><input type="text" name="txtVerificationDate" class="light" id="txtVerificationDate"  readonly="readonly"></input></td>
                        </tr>
                          <tr class="table">
                          <td>Date Of Receipt<span class="style1">* </span> </td>
                           
                          <td>    <div align="left"><input type="text" name="txtDateOfReceipt" id="txtDateOfReceipt"  maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';" onblur="testcall();"
                           onkeypress="return calins(event,this);"
                           />
                     <img src="../../../../../images/calendr3.gif"
		                         onclick="showCalendarControl(document.frmPre_AuditDetails.txtDateOfReceipt,1);"
		                         alt="Show Calendar"></img>
                  </div>
                </td>
                  
                        </tr>
                       <tr class="table">
                <td>
                    <div align="left">
                      Received By
                    </div>
                </td>
                <td>
                      
               
                   <div align="left" id="emplist_div_preparedby">
                    <input type="text" class="light" name="txtSanction_Estimate_PreparedBy"  id="txtSanction_Estimate_PreparedBy"  maxlength="40" readonly="readonly"/>&nbsp;&nbsp;  
                        <img src="../../../../../images/c-lovi.gif" height="20" alt="empList" onclick="emp_popup_sanction_preparedby();"/>&nbsp;&nbsp; 
                        <input type="text" name="txtEmpID_mas"  id="txtEmpID_mas"  maxlength="6" size="6" onchange="emp_sanction_preparedby();" onkeypress="return numbersonly(event)"/>  
                  </div>
                
               
                </td>
                
              </tr>
                <tr class="table">
                          <td>Pre_Audit Date<span class="style1">* </span> </td>
                          <td>   <div align="left"> <input type="text" onblur="checkmtc();" name="txtAuditDate" id="txtAuditDate"  maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           />
                            <img src="../../../../../images/calendr3.gif"
		                         onclick="showCalendarControl(document.frmPre_AuditDetails.txtAuditDate,1);"
		                         alt="Show Calendar"></img>
                    
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
                    <input type="text" class="light" name="txtSanction_Estimate_ApprovedBy"  id="txtSanction_Estimate_ApprovedBy"  maxlength="40" readonly="readonly"  />&nbsp;&nbsp; 
                        <img src="../../../../../images/c-lovi.gif"  height="20" alt="empList" onclick="emp_popup_sanction_approvedby();"/>&nbsp;&nbsp;  
                        <input type="text" name="txtEmpID_mas1"  id="txtEmpID_mas1"  maxlength="6" size="6" onchange="emp_sanction_approvedby();" onkeypress="return numbersonly(event)"/>
                  </div> 
                  
                
                </td>
              </tr>
                <tr class="table"> 
                          <td>Approve?<span class="style1">* </span> </td>
                          <td> <input type="radio" name="r1" id="r1" value="Y" Checked="checked">Yes <input type="radio" name="r1" id="r1" value="N"/>No
                          
                          </td>
                        </tr>
          
                        <tr class="table">
                          <td>Send to treasury section on<span class="style1">* </span> </td>
                          <td><div align="left"> <input type="text" onblur="checkaudit();" name="txtDate" id="txtDate"  maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                         /> <img src="../../../../../images/calendr3.gif"
		                         onclick="showCalendarControl(document.frmPre_AuditDetails.txtDate,1);"
		                         alt="Show Calendar"></img>
                     
                  </div>
                </td>
                        </tr>
                        <tr class="table">
                          <td>Remarks<span class="style1">* </span> </td>
                          <td> <textarea rows="3" cols="25" name="mtxtRemarks" id="mtxtRemarks"></textarea>                          </td>
                        </tr>
                                   
  </table>
  </div>
  </div>

 
    <div class="tab-page" id="gd" >
          <h2 class="tab" > Details</h2>
           
          <div align="center">
  
   
       
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
	                <th>Checklist Description</th>
	                <th>Mandate</th>
	                <th>Not Applicable</th>
	               <th>Select
                     <a href="javascript:selectAll('ALL');">All</a>
                 <a href="javascript:selectAll('UNSelect');">Unselect</a> 
              </th>                     
	              </tr>
                <tbody id="tblList" align="center">
                 </tbody>
  </table> 
       
  </div>

  </div>
 </div>
 <div align="center" id="testdiv" name="">
        <table cellspacing="1" cellpadding="3" width="100%">
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
</form>
</body>
</html>