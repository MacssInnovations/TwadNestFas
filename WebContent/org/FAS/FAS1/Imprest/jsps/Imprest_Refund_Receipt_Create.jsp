<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>   
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
	<META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Imprest/Temporary Advance Refund Receipt</title>

    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
   
   
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script>
    <script type="text/javascript" src="../scripts/Imprest_Refund_Receipt_Create.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>    
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl_forChequeDate.js"></script>     
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Date_Check.js"></script>    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>
    
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Operation_AC_Heads.js"></script>    

    
    
    <script type="text/javascript" language="javascript">
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
              //   document.frm_Imp_Refund_Receipt.txtCrea_date.value=day+"/"+month+"/"+year;   

                 // Load Account Head Desc During Form Loading 
                 document.getElementById("txtAcc_HeadCode").value="820103";             
                // doFunction('checkCode','null');
                 doFunctionBLOCK('checkCode','null');
                 document.frm_Imp_Refund_Receipt.txtCB_Year.value=year;
                 //  document.frm_Imp_Refund_Receipt.txtCB_Month.value=month; 
                  call_date(document.frm_Imp_Refund_Receipt.txtCrea_date);                           
         }
         
</script>
</head>
<%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body onload="clrForm();setTimeout('loadDate()', 300);LoadAccountingUnitID_Create('LIST_ALL_UNITS');" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Imprest/Temporary Advance Refund Receipt Create</font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frm_Imp_Refund_Receipt" id="frm_Imp_Refund_Receipt" method="POST"
                  action="../../../../../Imprest_Refund_Receipt_Create.kv?Command=Add"
                  onsubmit="return checkNull()">
       
       
	       <%
	  
	      Connection con=null;
	      ResultSet rs=null,rs2=null;
	      PreparedStatement ps=null,ps2=null;
	      ResultSet results=null;
	      ResultSet results1=null;
	      ResultSet results2=null;
	      try
	      {
	      
	                ResourceBundle rs1=ResourceBundle.getBundle("Servlets.Security.servlets.Config");
	                String ConnectionString="";
	    
	                String strDriver=rs1.getString("Config.DATA_BASE_DRIVER");
	                String strdsn=rs1.getString("Config.DSN");
	                String strhostname=rs1.getString("Config.HOST_NAME");
	                String strportno=rs1.getString("Config.PORT_NUMBER");
	                String strsid=rs1.getString("Config.SID");
	                String strdbusername=rs1.getString("Config.USER_NAME");
	                String strdbpassword=rs1.getString("Config.PASSWORD");
	    
	                //ConnectionString = strdsn.trim() + "@" + strhostname.trim() + ":" + strportno.trim() + ":" +strsid.trim() ;
	    				ConnectionString = strdsn.trim() + "://" + strhostname.trim() + ":" + strportno.trim() + "/" +strsid.trim() ;    // Postgres DB  Connection
	                 Class.forName(strDriver.trim());
	                 con=DriverManager.getConnection(ConnectionString,strdbusername.trim(),strdbpassword.trim());
	      }
	      catch(Exception e)
	      {
	        System.out.println("Exception in connection...."+e);
	      }
	  %> 
                  
      <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
          <h2 class="tab" >General </h2>
           
          <div align="center">
          
          
            <table cellspacing="1" cellpadding="2" border="0" width="100%">
          
            
              
               <tr class="table">
                <td width="40%">
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td  width="60%">
                  <div align="left">
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onChange="common_LoadOffice_New(this.value);">
                 
                    </select>
                  </div>
                </td>
              </tr>
              
              
              
              <tr class="table">
                <td>
                  <div align="left">
                    Accounting For Office Code
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2"  >
                 
                    </select>
                  </div>
                </td>
              </tr>
              
              
	           <tr class="table">
	            <td>
	              <div align="left">Payment Year &amp; Month</div>
	            </td>
	            <td>
	              <div align="left">
	                <input type="text" name="txtCB_Year" id="txtCB_Year"
	                       tabindex="3" maxlength="4" size="5"
	                       onkeypress="return numbersonly(event)"></input>
	                 
	                <select name="txtCB_Month" id="txtCB_Month" tabindex="4" onchange="financialYear();">
	                  <option value="">-- Select Month -- </option>
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
	                 <input type="hidden" name="fin_year1" id="fin_year1"/>
                        <input type="hidden" name="fin_year2" id="fin_year2"/>
	              </div>
	            </td>
	          </tr>
                  
                  <tr class="table">
                    <td width="30%">
                      <div align="left">
                        Advance Type
                        <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <select name="cmbAdvance_type" id="cmbAdvance_type" tabindex="4" onchange="loadAccountHead();doFunction_ImpReceipt('LoadPayVoucherNo')">
                            <option value="I1" selected="selected" >Imprest-Payment</option>
	                        <option value="I2" >Imprest Payment-SelfCheque</option>
	                        <option value="T1">Temporary Adv.-Payment</option>
	                        <option value="T2">Temporary Adv.-SelfCheque</option>
                        </select>                     
                      </div>
                    </td>
                  </tr>
	          	          	
	          
	          <tr class="table">
       		    <td>
                  <div align="left">
                    Payment Voucher No.  <font color="#ff2121">*</font>
                  </div>
                </td>
                <td >
                  
                  <div align="left">
                    <select  name="cmbPayVocNo" id="cmbPayVocNo" tabindex="6" onchange="doFunction_ImpReceipt('LoadPayVoucherDetails')">
                      <option value="">--Select Voucher No--</option>
                    </select>
                  </div>
                  
                  <div align="left" style="display:none" id="PaymentDetails" >
                     <input type="button" value="Payment Details" onclick="ShowPaymentDetails()"  />
                  </div>
                  
                </td>               
             </tr>  
             
               
              
             <tr class="table">
                <td>
                  <div align="left">
                   Payment Voucher Date <font color="#ff2121">*</font>
                   </div>
                </td>
                <td>
                  <div align="left">
                  
                   <input type="text" name="txtPaymentVoc_Date" id="txtPaymentVoc_Date"  tabindex="7"
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"
                           readonly
                           />
                         
                  </div>
                </td>
              </tr>
             
             
              <tr class="table">
                <td>
                  <div align="left">
                   Amount Paid   <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAmtPaid"  onkeypress="return limit_amt(this,event);" tabindex="7" onblur="valid_amt(this);"
                           id="txtAmtPaid" maxlength="17" size="18" readonly/>
                  </div>
                </td>
              </tr>
             
              
              
              
              <tr class="table">
                <td>
                  <div align="left">
                    Receipt Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                   <input type="text" name="txtCrea_date" id="txtCrea_date" tabindex="3" 
                           maxlength="10" size="11"  readonly="readonly"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="call_date(this);dateCheck(this);" onchange="datefun();"/>
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frm_Imp_Refund_Receipt.txtCrea_date,1);"
                         alt="Show Calendar"></img> 
                    
                  </div>
                </td>
              </tr>
              
              
              
              
             
              <tr class="table">
                <td>
                  <div align="left">
                    Receipt Number
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="hidden" name="txtReceipt_No" id="txtReceipt_No"  
                    style="background-color: #ececec"  readonly="readonly" size="6" maxlength="5"/>(System Generated)
                  </div>
                </td>
              </tr>
             
             
              <tr class="table">
                <td>
                  <div align="left">
                    Mode of Receipt
                  </div>
                </td>
                <td>
                  <div align="left">
                      <input type="radio" id="txtAmtType" name="txtAmtType" value="CR" checked  onclick="grid_ImpReceipt(this.value)" /> Cash &nbsp;&nbsp;
                      <input type="radio" id="txtAmtType" name="txtAmtType" value="BR"  onclick="grid_ImpReceipt(this.value)" /> Bank
                  </div>
                </td>
              </tr>
             
             
              <tr class="table">
                <td>
                  <div align="left">Debit A/c Code</div>
                </td>
                <td>
                   <div align="left">
                    <div  id="grid_ImpReceipt_Torch_Cash" style="display:block">
                       <input type="text" name="txtCash_Acc_code_cash" id="txtCash_Acc_code_cash" value="820101"
	                           style="background-color: #ececec"  readonly="readonly" maxlength="8" size="8"/>
                    </div>
                    
                    <div id="grid_ImpReceipt_Torch_Bank" style="display:none">                      
                    
	                    <input type="text" name="txtCash_Acc_code" id="txtCash_Acc_code" style="background-color: #ececec"  readonly="readonly" maxlength="8" size="8"/>
                                   
	                    <img src="../../../../../images/c-lovi.gif" width="20" 
				              height="20" alt="AccountNumberList"
				              onclick="MainAccNopopup();"></img>		                     
	                </div>
	              </div>
	              
                </td>
              </tr>              
           </table>
              
              
              
             <div id="grid_ImpReceipt_Gen" style="display:none" >
                          
             <table cellspacing="1" cellpadding="2" border="0" width="100%">
              <tr class="table">
                <td width="40%">
                                
                  <div align="left"   >
                       Bank Account Number <font color="#ff2121">*</font>
                  </div>
                  
                </td>
                <td width="60%">
                  <div align="left">
                    <input type="text" name="txtBankAccountNo"  onkeypress="return numbersonly(event)" 
                           id="txtBankAccountNo" maxlength="15"  size="15"  style="background-color: #ececec"  readonly="readonly" />
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
                    <input type="text" name="txtBankName" id="txtBankName"
                    style="background-color: #ececec"  readonly="readonly" size="50" maxlength="49"/>
                   <input type="hidden" name="txtBankID" id="txtBankID"></input>
                    <input type="hidden" id="txtBranchID" name="txtBranchID"></input>
                  </div>                         
                </td>
              </tr>          
             </table>
             
              </div>
           
               
               
               
               
             <table cellspacing="1" cellpadding="2" border="0" width="100%">
              
             
             
              
              <tr class="table">
                <td width="40%">
                  <div align="left">
                    CR/DR Indicator
                  </div>
                </td>
                <td width="60%">
                  <div align="left">
                  <input type="hidden" name="txtCR_DB"
                           id="txtCR_DB" value="DR" size="2"/>
                    <input type="text" name="txtCR_DB_desc"
                          style="background-color: #ececec"  readonly="readonly" id="txtCR_DB_desc" value="DEBIT" size="6"/>
                  </div>
                </td>
              </tr>
              
              
              
               <tr class="table">
                <td width="40%">
                  <div align="left">Sub-Ledger Type</div>                  
                </td>
                <td width="60%">
                  <div align="left">
                    <select  name="cmbMas_SL_type" id="cmbMas_SL_type" tabindex="6"
                            onchange="doFunction('Load_MasterSL_Code',this.value);">
                      <option value="">--Select Type--</option>
                      <%
                        try
                        {
                        ps=con.prepareStatement("select SUB_LEDGER_TYPE_CODE,SUB_LEDGER_TYPE_DESC from COM_MST_SL_TYPES where SUB_LEDGER_TYPE_CODE=7 order by SUB_LEDGER_TYPE_DESC");
                        rs=ps.executeQuery();
                            while(rs.next())
                            {
                            out.println("<option value="+rs.getString("SUB_LEDGER_TYPE_CODE")+">"+rs.getString("SUB_LEDGER_TYPE_DESC")+"</option>");
                            }
                        }
                        catch(Exception e)
                        {
                        System.out.println("Exception in Reason combo..."+e);
                        }
                        finally
                        {
                        rs.close();
                        ps.close();
                        }   
                      %>
                    </select>
                  </div>
                </td>
              </tr>
              
              
              
              
              <tr class="table">
                <td width="40%">
                  <div align="left">Sub-Ledger Code</div>
                </td>
                <td width="60%">
                    <table align="left">
                     <tr align="left">
                     <td>
                         <div align="left">
                        <select size="1" name="cmbMas_SL_Code" id="cmbMas_SL_Code"  onchange="loadName_Mas(this);"   >
                                
                          <option value="">--Select Code--</option>
                        </select>
                  </div>
                      </td>
                      <td>
                          <div align="left" id="offlist_div_master"  style="display:none">
                            
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList" onclick="jobpopup_master();"></img>
                            <input type="text" name="txtOfficeID_mas" id="txtOfficeID_mas" maxlength="4" size="5"  onblur="mas_office(this.value);" />
                          </div>
                           <div align="left" id="emplist_div_master"  style="display:none">
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="employee_popup_master();"></img>
                            <input type="text" name="txtEmpID_mas" id="txtEmpID_mas" maxlength="5" size="5"  onblur="mas_employee(this.value);loadReceivedFrom();" />
                          </div>
                       </td>
                     
                    </tr>
                   </table>
                </td>
              </tr>
              
              
              <tr class="table">
                <td>
                  <div align="left">
                   Final Payment
                  </div>
                </td>
                <td>
                  <div align="left">
                      <input type="radio" id="finalPayment" name="finalPayment" value="Y"   onclick="checkTtlAmt()" /> Yes &nbsp;&nbsp;
                      <input type="radio" id="finalPayment" name="finalPayment" value="N" checked onclick="checkTtlAmt()" /> No
                  </div>
                </td>
              </tr>
              
              
              <tr class="table">
                <td>
                  <div align="left">Remarks</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtRemarks" id="txtRemarks" cols="50" tabindex="8"  onkeypress="return check_leng(this.value,'remarks');"
                              rows="4"></textarea>
                  </div>
                </td>
              </tr>
              
             <tr class="table">
                <td>
                  <div align="left">
                    Received From
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                    <div align="left">
                       <select name="txtRecei_from" id="txtRecei_from" >
                       </select>     
                  </div>
                </td>
              </tr>
              
              
              <tr class="table">
                <td>
                  <div align="left">
                    Total Amount Returned (Rs. P.) 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAmount"  onkeypress="return limit_amt(this,event);" tabindex="10" onblur="valid_amt(this.value);"
                           id="txtAmount" maxlength="17" size="18" onchange="checkWithPayment();"/><label class="cc"> (Amount Entered In All the Receipts Should Not Exceed this Total Amount)</label>
                  </div>
                </td>
              </tr>
              
            </table>
            
          </div>
        </div>
         
         
         
         
        <div class="tab-page" id="gd" >
          <h2 class="tab" > Details</h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="0" width="100%">
              <tr>
                <td colspan="2">
                  <div id="sub_ledge_dis" >
                  
                  
                    <table cellspacing="1" cellpadding="2" border="0" width="100%">
                     
                      
                      
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
                            onchange="sixdigit();"      onblur="doFunctionBLOCK('checkCode','null'); "  size="9"/> 
                        <!--     onblur="doFunction('checkCode','null');"  size="9"/> -->
                        
                        
                    
                    <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountHeadList"
                             onclick="AccHeadpopup();"></img>
                    <input type="text" name="txtAcc_HeadDesc" readonly="readonly" 
                           id="txtAcc_HeadDesc" style="background-color: #ececec"  maxlength="125" size="70"/>
                  </div>
                </td>
              </tr>
             
              
              
              <tr class="table">
                <td>
                  <div align="left">
                    CR/DR
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="rad_sub_CR_DR" id="rad_sub_CR_DR" 
                           checked="checked" value="CR"/>Credit
                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="rad_sub_CR_DR" id="rad_sub_CR_DR" 
                           value="DR"/>Debit &nbsp;&nbsp;&nbsp;&nbsp; 
                    
                  </div>
                </td>
              </tr>
              
                            
              
                      <tr class="table">
                        <td width="40%">
                          <div align="left">Sub-Ledger Type <font color="#ff2121">*</font>  </div>
                        </td>
                        <td width="60%">
                          <div align="left">
                           <select size="1" name="cmbSL_type" id="cmbSL_type" onchange="doFunction('Load_SL_Code',this.value)" >
                              <option value="">--Select Type--</option>
                           </select>
                          </div>
                        </td>
                      </tr>
                      
                      
                      
                      
                      
                      <tr class="table">
                        <td width="40%">
                          <div align="left">Sub-Ledger Code <font color="#ff2121">*</font>  </div>
                        </td>
                        <td width="60%">
                          <table align="left">
                                 <tr align="left">
                                 <td>
                                      <div align="left">
                                        <!--   <select size="1" name="cmbSL_Code" id="cmbSL_Code" onchange="Dip_AMT(this.value)" >  -->
                                             <select size="1" name="cmbSL_Code" id="cmbSL_Code" >
                                              <option value="">--Select Code--</option>
                                            </select>
                                      </div>
                                  </td>
                                  <td>
                                      <div align="left" id="offlist_div_trans"  style="display:none">
                                        <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList"  onclick="jobpopup_trans();"></img>
                                        <input type="text" name="txtOfficeID_trs" id="txtOfficeID_trs" maxlength="4" size="5"  onblur="trs_office(this.value);" />
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
                      
                      
            
             </table>
             
             
              
            <div id="grid_ImpReceipt_Det" style="display:none" >
                            
             <table cellspacing="1" cellpadding="2" border="0" width="100%">       
               <tr class="table">
                <td width="40%">
                  <div align="left">
                    Cheque/DD
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td width="60%">
                  <div align="left">
                    <input type="radio" name="txtCheque_DD" id="txtCheque_DD" value="C"/>Cheque &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtCheque_DD" id="txtCheque_DD"  checked="checked" value="D" />DD &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="txtCheque_DD" id="txtCheque_DD" value="E"/>ECS                   
                    
                  </div>
                </td>
              </tr>      
                
               <tr class="table">
                <td>
                  <div align="left" id="ecs_hide" >
                    Cheque/DD Number
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left" id="ecs_hide" >
                    <input type="text" name="txtCheque_DD_NO" maxlength="10" onkeypress="return numbersonly(event)"
                           id="txtCheque_DD_NO" size="11"/>
                  </div>
                </td>
              </tr>
              
               <tr class="table">
                <td>
                  <div align="left" id="ecs_hide" >
                    Cheque/DD Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                
                  <div align="left" id="ecs_hide" >
                  
                     <input type="text" name="txtCheque_DD_date" id="txtCheque_DD_date"  
                           maxlength="10" size="11"
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                           
                    <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frm_Imp_Refund_Receipt.txtCheque_DD_date,0);"
                         alt="Show Calendar">                         
                   </img>                  
                    
                    
                  </div>
                </td>
              </tr>
              
             <tr class="table">
                <td>
                  <div align="left">
                   Bank Name
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtBank_Name"
                           id="txtBank_Name" size="27" maxlength="25"/>
                  </div>
                </td>
              </tr>
              
               <tr class="table">
                <td>
                  <div align="left">
                    Drawee Branch
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtDraw_BR"
                           id="txtDraw_BR" size="27" maxlength="25"/>
                  </div>
                </td>
              </tr>
              
              
               <tr class="table">
                <td>
                  <div align="left">
                    Bank MICR code

                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtBank_M_Code" onkeypress="return numbersonly(event)"
                           id="txtBank_M_Code" size="20" maxlength="15"/>
                  </div>
                </td>
              </tr>
              
             </table>
            
             </div>
             
             
             
                 
             <table cellspacing="1" cellpadding="2" border="0" width="100%">
                                           
              <tr class="table">
                <td width="40%">
                  <div align="left">
                     Amount 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td width="60%">
                  <div align="left">
                    <input type="text" name="txtsub_Amount" onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);Receipt_Amt_Check(this.value)"
                           id="txtsub_Amount" maxlength="17" size="18" />
                  </div>
                </td>
              </tr>
              
              
              
              
              
              <tr class="table">
                <td>
                  <div align="left">Particulars</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtParticular" id="txtParticular" cols="70"  onkeypress="return check_leng(this.value,'particulars');"
                              rows="3"></textarea>
                  </div>
                </td>
              </tr>
                      
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
                <input type="submit" name="butSub" id="butSub" value="SUBMIT"/>
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