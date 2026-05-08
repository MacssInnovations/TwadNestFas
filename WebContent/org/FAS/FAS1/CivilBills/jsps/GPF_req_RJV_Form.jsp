<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<title>GPF RJV Request</title>
<link href='../../../../../css/Fas_Account.css' rel='stylesheet'
	media='screen' />

<link href="../../../../../css/CalendarControl.css" rel="stylesheet"
	media="screen" />
<link href="../../../../../css/Sample3.css" rel="stylesheet"
	media="screen" />
	     <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script>

    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js">          </script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>
       <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/JournalSystem/scripts/Journal_Bill_Create.js"></script> 
<script type="text/javascript" src="../scripts/GPF_req_RJV_Form.js"></script>
<script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
<script src="<%=request.getContextPath()%>/org/FAS/FAS1/BillRegister/scripts/Bills_Token_Register_without_SP.js" 	type="text/javascript"> </script>
    <script type="text/javascript" src="<%=request.getContextPath()%>../org/FAS/FAS1/JournalSystem/scripts/Rectificational_Journal.js"></script>
<script src="<%=request.getContextPath()%>/org/FAS/FAS1/BillRegister/scripts/Bills_Token_Register_without_SP_GPF.js"
	type="text/javascript"> </script>
	  
    
<script type="text/javascript">

function loadDate(path)
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
		document.getElementById("txtVrDate").value=day+"/"+month+"/"+year;
		document.getElementById("txtCB_Year").value=year;
	
		document.frmGPF_RJV_Req.txtCB_Month.value=month;
	
}
 function checkmonth(val){
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
	var yr=	document.getElementById("txtCB_Year").value;
	if(yr==year)
		{
		if(val>month)
			{
			alert('Month Not allowed');
			document.frmGPF_RJV_Req.txtCB_Month.value=month;
			
			}
		}
	
}
 
 function checkrelmonth(val){
		var yr=	document.getElementById("txtCB_Year").value;
		var mn=	document.getElementById("txtCB_Month").value;
		var rel_yr=	document.getElementById("txtrel_Year").value;
		if(rel_yr==yr)
			{
			if(val>mn)
				{
				alert('Relative Month Not allowed');
				document.frmGPF_RJV_Req.txtrel_Month.value="";
				}
			}
		else if(rel_yr > yr){
			alert('Relative Year Not allowed');
			document.frmGPF_RJV_Req.txtrel_Year.value="";
		}
	}
 function checkrelyr(val){
		var yr=	document.getElementById("txtCB_Year").value;
	 if(val > yr){
			alert('Relative Year Not allowed');
			document.frmGPF_RJV_Req.txtrel_Year.value="";
		} 
 }

function chkdate1(dte){
	
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
			var to_dtae=day+"/"+month+"/"+year;
	
	var mnth=document.frmGPF_RJV_Req.txtCB_Month.value;
	var yr=document.frmGPF_RJV_Req.txtCB_Year.value;
	var str=dte.split('/');
	if(str[2]==yr)
	{
	if(str[1]!=mnth)
		
			{
			alert('Date Should be with in selected Cashbook Month & Year ... ');
			document.frmGPF_RJV_Req.txtVrDate.value=to_dtae;
			}
			
	}else
	{
		alert('Date Should be with in selected Cashbook Month & Year ... ');
		document.frmGPF_RJV_Req.txtVrDate.value=to_dtae;
		}

	
	
	
}

</script>
</head>
<% String s=request.getContextPath(); %>
<body onload="loadDate('<%=s %>');LoadAccountingUnitID('LIST_ALL_UNITS');">


<table cellspacing="1" cellpadding="3" width="100%">
	<tr class="tdH">
		<td colspan="2">
		<div align="center"><font size="4">GPF RJV Request</font></div>
		</td>
	</tr>
</table>
<form id="frmGPF_RJV_Req" name="frmGPF_RJV_Req" method="POST" action="../../../../../GPF_req_RJV_Form" onsubmit="return nullcheck1();">
 <input type="hidden" id="cmbMas_SL_type" name="cmbMas_SL_type" value=0>
  <input type="hidden" id="cmbMas_SL_Code" name="cmbMas_SL_Code" value=0>
 
 <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
          <h2 class="tab" > General </h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              
               <tr class="table">
		            <td>
		                <div align="left">Accounting Unit Code <font color="#ff2121">*</font> </div>
		            </td>
		            <td><div align="left">
		                <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onChange="common_LoadOffice(this.value);">
		                </select>
		                </div>
		            </td>
		        </tr>
				<tr class="table">
					<td>
			                    <div align="left">Accounting For Office Code <font
			                            color="#ff2121">*</font>
			                    </div>		
			                </td>
					<td>
			                    <div align="left">
			                    <select size="1" name="cmbOffice_code"
			                            id="cmbOffice_code">
			                    </select>
			                    </div>		
			                </td>
				</tr>
              <tr class="table">
					<td>
			                    <div align="left">CashbookYear & Month<font
			                            color="#ff2121">*</font>
			                    </div>		
			                </td>
					<td>
			                    <div align="left">
							  <input type="text" name="txtCB_Year" id="txtCB_Year" tabindex="3"  maxlength="4" size="5" >
					          <select name="txtCB_Month"  id="txtCB_Month" tabindex="4" onchange="checkmonth(this.value);check_db('<%=s %>');">
					          <option value="01">January</option>
					          <option value="02">February</option>
					          <option value="03">March</option>
					          <option value="04">April</option>
					          <option value="05">May</option>
					          <option value="06">June</option>
					          <option value="07">July</option>
					          <option value="08">August</option>
					          <option value="09">September</option>
					          <option value="10">October</option>
					          <option value="11">November</option>
					          <option value="12">December</option>
					          </select>
					       
			                    </div>		
			                </td>
				</tr>
				
			
		        
					<tr class="table">
				<td>
				<div align="left">Voucher Date<font color="#ff2121">*</font></div>		</td>
				<td>
				<div align="left"><input type="text" name="txtVrDate" maxlength="10" STYLE="background-color:#E4F470"  class="light" readonly="readonly" size="10" 
		                   id="txtVrDate" onFocus="javascript:vDateType='3';"
		                           onkeypress="return calins(event,this);" onBlur="chkdate1(this.value);return checkdt1(this);"/>
		                   <img src="../../../../../images/calendr3.gif"
		                         onclick="showCalendarControl(document.frmGPF_RJV_Req.txtVrDate);"
		                         alt="Show Calendar"  ></img></div>		</td>
			</tr>
			   <tr class="table">
					<td>
			                    <div align="left">Relative Year & Month<font
			                            color="#ff2121">*</font>
			                    </div>		
			                </td>
					<td>
			                    <div align="left">
							  <input type="text" name="txtrel_Year" id="txtrel_Year" tabindex="3" onblur="checkrelyr(this.value);" maxlength="4" size="5" >
					          <select name="txtrel_Month"  id="txtrel_Month" tabindex="4" onchange="checkrelmonth(this.value);check_db('<%=s %>');">
					           <option value="">--select--</option>
					          <option value="01">January</option>
					          <option value="02">February</option>
					          <option value="03">March</option>
					          <option value="04">April</option>
					          <option value="05">May</option>
					          <option value="06">June</option>
					          <option value="07">July</option>
					          <option value="08">August</option>
					          <option value="09">September</option>
					          <option value="10">October</option>
					          <option value="11">November</option>
					          <option value="12">December</option>
					          </select>
					       
			                    </div>		
			                </td>
				</tr>
		            
			<tr class="table">
				<td width="40%">
				<div align="left">Total Amount<font color="#ff2121">*</font></div>		</td>
				<td width="60%">
				
						<div align="left">  <input type="text" name="txtJnl_Amt" id="txtJnl_Amt" tabindex="3" value=""  maxlength="15" size="8" ></div>				</td>
					
						
					</tr>
					         
		            <tr class="table">
                        <td>
                          <div align="left">Option<font color="#ff2121">*</font></div>
                        </td>
                        <td>
                          <div align="left"   >
                           <select id="type_1" name="type_1" onchange="loadAccHeadnew('<%=s %>')">
                           <option value="">--Select--</option>
                           <option value="JR">Journal</option>
                           <option value="CR">Cash Receipt</option>
                           <option value="BR">Bank Receipt</option>
                           </select>
                          </div>
                        </td>
                      </tr> 
                        <tr class="table"  id="REc" name="REc"  >
                <td>
                  <div align="left" id="cap1" name="cap1" style="display: none;">Cash A/c Code <font color="#ff2121">*</font>
                  <br>    Received From
                    <font color="#ff2121">*</font>
                  </div>
                     <div align="left" id="cap2" name="cap2" style="display: none;" >Collection A/c Code <font color="#ff2121">*</font>
                     <br>  <br>  <br>   Received From
                    <font color="#ff2121">*</font></div>
                </td>
                <td>
                  <div   align="left" id="div_1" >
                    <input type="text" name="txtCash_Acc_code" id="txtCash_Acc_code" value="" style="display: none;"
                          style="background-color: #ececec"   readonly="readonly" maxlength="6" size="7"/>
                     <div   align="left" id="cap1_img"  style="display: none;">      <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountNumberList"
                             onclick="MainAccNopopupcs();"></img></div>
                          <div   align="left" id="cap2_img"  style="display: none;">       <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountNumberList"
                             onclick="MainAccNopopupnew();"></img></div>
                             
                            <div   align="left" id="cap3_img"  style="display: none;">           <input type="text" name="txtBankAccountNo"  onkeypress="return numbersonly(event)" value=0
                           id="txtBankAccountNo" maxlength="15"  size="15"  style="background-color: #ececec"  readonly="readonly" />
                   
                            <input type="text" name="txtBankName" id="txtBankName" value=""
                    style="background-color: #ececec"  readonly="readonly" size="50" maxlength="49"/>
                   <input type="hidden" name="txtBankId" value=""
                           id="txtBankId" size="5" maxlength="5"/>
                   <input type="hidden" name="txtBranchId"  value=""
                           id="txtBranchId" size="5" maxlength="5"/>  
                          <br> 
                 
                 
              
                
                  
                  
                 
              
                          </div>
                              <input style="display: none;" type="text" name="txtRecei_from" tabindex="9" onkeypress="return check_leng(this.value,'received_from');"
                           id="txtRecei_from"  size="50"/> 
                 
                  </div>
                </td>
              </tr>
                 
                      
				</table>	
		
          </div>
        </div>        
        <div class="tab-page" id="gd" >
          <h2 class="tab" >Details</h2>
           
          <div align="center" id="notgpfdetails" style="display:block">
         
         
         <table cellspacing="1" cellpadding="2" border="1" width="100%">
              
              <!--  <tr class="table">
		            <td>
		                <div align="left">Account Head <font color="#ff2121">*</font> </div>
		            </td>
		            <td><div align="left">  
		            <input type="text" name="txtHead_cde" id="txtHead_cde" tabindex="3"  maxlength="4" size="5" onkeypress="return numbersonly(event)">
		            </div></td>
		            </tr> -->
		            
		            <tr class="table">
                        <td>
                          <div align="left">
                            Account Head Code 
                            <font color="#ff2121">*</font>
                          </div>
                        </td>
                        <td>
                          <div align="left">
                            <input type="text" name="txtAcc_HeadCode"
                                   id="txtAcc_HeadCode" maxlength="6"
                                   onkeypress="return numbersonly(event)"
                                    onchange="sixdigit();" 
                                    onblur="doFunction('checkCode','null');" 
                                   size="9"/>
                             
                            <img src="../../../../../images/c-lovi.gif"
                                 width="20" height="20" alt="AccountHeadList"
                                 onclick="AccHeadpopup();"></img>
                             
                            <input type="text" name="txtAcc_HeadDesc"
                                   readonly="readonly" id="txtAcc_HeadDesc"
                                   style="background-color: #ececec"
                                   maxlength="125" size="70"/>
                          </div>
                        </td>
                      </tr>
		               <tr class="table">
		            <td>
		                <div align="left">CR / DR Type <font color="#ff2121">*</font> </div>
		            </td>
		            <td><div align="left">  
		           <input type="radio" id="txtcDr_Type" name="txtcDr_Type" value="CR" checked="checked" > CR
		            <input type="radio" id="txtcDr_Type" name="txtcDr_Type" value="DR"  > DR
		            </div></td>
		            </tr>
		            
		              <tr class="table">
                <td width="40%">
                  <div align="left">Sub-Ledger Type <font color="#ff2121">*</font> </div>
                </td>
                <td width="60%">
                  <div align="left">
                   <select size="1" name="cmbSL_type" id="cmbSL_type" onchange="doFunction('Load_SL_Code',this.value);">
                      <option value="">--Select Type--</option>
                     
                    </select>
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td width="40%">
                  <div align="left">Sub-Ledger Code  <font color="#ff2121">*</font> </div>
                </td>
              <td width="60%">
            <table align="left">
             <tr align="left">
             <td>
                  <div align="left">
                        <select size="1" name="cmbSL_Code" id="cmbSL_Code" >
                          <option value="">--Select Code--</option>
                        </select>
                  </div>
              </td>
              <td>
                  <div align="left" id="offlist_div_trans"  style="display:none">
                    <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList" onclick="jobpopup_trans();"></img>
                    <input type="text" name="txtOfficeID_trs" id="txtOfficeID_trs" maxlength="4" size="5"    onblur="trs_office(this.value);" />
                  </div>
                  <div align="left" id="emplist_div_trans"  style="display:none">
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="employee_popup_trans();"></img>
                            <input type="text" name="txtEmpID_trs" id="txtEmpID_trs" maxlength="5" size="5"  onblur="trs_employee(this.value);" />
                 </div>
               </td>
             
            </tr>
           </table>
        </td>
              </tr>    
		            
		            <!-- <tr class="table">
		<td>
		<div align="left">SL Type<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left">
		<select name="txtPayeeType" id="txtPayeeType" onblur="loadPayeeCode_Desc();">
				<option value="">Select</option>
				</select>
		<input type="text" name="txtPayeeType"
			id="txtPayeeType" maxlength="15" />	</div>	</td>
	</tr> -->
	<!-- <tr class="table">
		<td>
		<div align="left">SL Code<font color="#ff2121">*</font></div>		</td>
		<td>
		<div align="left" id="one">
		<select name="txtPayeeCodeLoad" id="txtPayeeCodeLoad" onblur="loadPayy();" onchange="loadPayy();">
				<option value="">Select</option>
				</select>
	
		</div>	
		<div align="left" id="two" style="display:none">
		<select name="txtPayeeCodeLoad_two" id="txtPayeeCodeLoad_two" onblur="loadPayy();" onchange="loadPayy();">
				<option value="">Select</option>
				</select>
		</div>	<input type="text" name="txtPayeeCode" id="txtPayeeCode" maxlength="15" size="7"  onkeypress="return numbersonly1(event,this)" onchange="callpd();"/>
	
			</td>
	</tr> -->
		             <tr class="table">
		            <td>
		                <div align="left">Amount <font color="#ff2121">*</font> </div>
		            </td>
		            <td><div align="left">  
		            <input type="text" name="txt_amt" id="txt_amt" tabindex="3" value="" maxlength="15" size="9" >
		            </div></td>
		            </tr>
		            
		               <tr class="table">
                <td>
                  <div align="left">
                     Adjustment Against Year
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="adjyear" id="adjyear" size="5"/>
                  </div>
                </td>
              </tr> 
               <tr class="table">
                <td>
                  <div align="left">
                     Adjustment Against Month
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                     <select name="adjmonth"  id="adjmonth" onchange="tohidedoc()">
       				 <option value="">--Select Month--</option>
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
                  <div align="left" id="since">
                     Doc.Type &amp; Doc No. (Since sep'2007)
                    <font color="#ff2121">*</font>
                  </div>
                  <div align="left" id="prior" style="display:none">
                   Doc.Type &amp; Doc No. (prior sep'2007)
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left" id="since2007">
                 <select name="paymentreceipt"  id="paymentreceipt" onchange="payreceipt()">
       			  <option value="">--Select--</option>
		          <option value="R">Receipt</option>
		          <option value="P">Payment</option>
		          <option value="J">Journal</option>
		          <option value="FR">Fund Receipt</option>
		      	 <option value="FT">Fund Transfer</option>
		      	  <option value="IBT">IBT</option>
		          </select>
		          <select name="receiptno"  id="receiptno"  onchange="payreceiptdetails();" >
       			<option value="">--Select--</option>
       			</select>
       			  <img src="../../../../../images/view_details_icon.png" width="20" 
                             height="20" alt="Details"
                             onclick="payreceiptdetails();"></img>
                  </div>
                <div align="left" id="prior2007" style="display:none">
                  <select name="paymentreceipt1"  id="paymentreceipt1"  >
       			 <option value="">--Select--</option>
		         <option value="R">Receipt</option>
		         <option value="P">Payment</option>
		          <option value="J">Journal</option>
		         <option value="FR">Fund Receipt</option>
		      	 <option value="FT">Fund Transfer</option>
		      	 <option value="IBT">IBT</option>
		          </select>
		            <input type="text" name="receiptno1" id="receiptno1"  />
                  </div>   
                  
                </td>
              </tr> 
               
		            
		            <tr class="table">
                        <td>
                          <div align="left">Particulars</div>
                        </td>
                        <td>
                          <div align="left">
                            <textarea name="txtParticular" id="txtParticular"
                                      cols="70"
                                      onkeypress="return chk_part_len(this.value);"
                                      rows="3"></textarea>
                          </div>
                        </td>
                      </tr> 
                      <tr class="table">
		            <td colspan="2">
		          <center>  <input type="button" value="Add" onclick="addGrid();"></center>
		        </td>
		            </tr> 
		            
		       
                  
		            
		            </table>
		           
         
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
            
              <tr>
                <td colspan="2">
                  
                  <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      
                          <tr class="tdH">
                         
                            <th>Account Head</th>
                            <th>CR / DR</th>
                              <th>Sl Type</th>
                            <th>Sl Code</th>
                               <th >Amount</th>         
                            <th >Particulars</th>   
                               <th >ADJ.Year</th>
                       <th >ADJ.Month</th>
                       <th >Doc.Type</th> 
                       <th >Doc.No</th>      
                          </tr>
                      
                       <tbody id="grid_body" name="grid_body" class="table" align="left" >
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
      <div align="center">

        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
                <input type="submit" name="butSub" id="butSub" value="SUBMIT" />
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="CANCEL" 
                       onclick="clrForm('cancel');"/>
                 &nbsp;&nbsp;&nbsp; 
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="javascript:self.close();"/>
              </div>
            </td>
          </tr>
        </table>
      </div>
</form>
</body>
</html>