<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>TDA/TCA Accepting System(Supplement)</title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script> 
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>  
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Cheque_Number_Check_forPAY.js"></script>		   		 				    		
    <script type="text/javascript" src="../scripts/TDA_Accepting_Cancel_Supp.js"></script>
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script>  
   
     <!-- to avoid future date the above script used-->
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
               //  document.TDA_TCA.txtCrea_date.value=day+"/"+month+"/"+year;  
                document.TDA_TCA.txtCrea_date.value="31"+"/"+"03"+"/"+year;  
                                 
        }
</script>
  </head>
   <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body onload="clrForm('load');loadDate();LoadAccountingUnitID('LIST_ALL_UNITS');Check_Supplement_No();" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Transfer of Debit/Credit Advice (TDA/TCA) Accepting System(Supplement)</font>
          </div>
        </td>
      </tr>
    </table>
   
  <form name="TDA_TCA" id="TDA_TCA" method="POST" 
                  action="../../../../../TDA_Accepting_Cancel_Supp?Command=Cancel" onsubmit="return checkNull()">
      <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
          <h2 class="tab" > General </h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              
             <tr class="table">
                    <td>
                      <div align="left" >
                              Accounting Unit Code  <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                         <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" onchange="common_LoadOffice(this.value);call_clr();">        
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
                        <select size="1" name="cmbOffice_code" id="cmbOffice_code" >
                          
                        </select>
                      </div>
                    </td>
             </tr>
             
             <tr class="table">
                    <td>
                      <div align="left">
                        Accepting Date  <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtCrea_date" id="txtCrea_date" 
                               maxlength="10" size="11"
                               onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);"
                               onblur="return call_date(this);" onchange="clearJournal()"/>
                         
                        <img src="../../../../../images/calendr3.gif"
                             onclick="showCalendarControl(document.TDA_TCA.txtCrea_date,1);"
                             alt="Show Calendar"></img>                        
                      </div> 
                    </td>
              </tr>     
             
             <tr align="left" class="table">
            <td class="table">
            <div align="left">Supplement Number<font color="#ff2121">*</font> 
            </div>
            </td>
            <td>
              <div align="left">
                <select name="supNo" id="supNo" >
              <!--      <option value="" >-- Select Suppl No. -- </option>  -->
                </select>
                
                </div>
            </td>
          </tr>  
             
             
             <tr class="table">
                    <td>
                      <div align="left">
                        Journal Type <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="radio" name=Journal_type id=Journal_type value="TDAA" onclick="doFunction_TDA('load_Voucher_No')"></input> TDA Accepting
                        <input type="radio" name=Journal_type id=Journal_type value="TCAA" onclick="doFunction_TDA('load_Voucher_No')"></input> TCA Accepting
                      </div>
                    </td>
              </tr>
                      
              <tr class="table">
                <td>
                  <div align="left">
                    Accepted Sl.No. <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select type=text name="accepted_slno" id="accepted_slno" onchange="changeLink();doFunction_TDA('load_Voucher_Details')">  
                    	<option value="">select voucher</option>   
                    </select>  <a id="linkId" href="javascript:checkVoucherNo();" style="visibility:hidden">Voucher Details</a>             	
                  </div>
                </td>
              </tr>      
	          
	          <tr class="table">
                <td>
                  <div align="left">
                    Originated Sl.No. <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type=text name="originated_slno" id="originated_slno" readonly="readonly">                    	
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                   <td>
                     <div align="left">
                       Date 
                       <font color="#ff2121">*</font>
                     </div>
                   </td>
                   <td>
                     <div align="left">
                       <input type="text" name="originated_date" id="originated_date" 
                              maxlength="10" size="11"
                              onfocus="javascript:vDateType='3';" readonly="readonly"
                       />
                                             
                     </div> 
                   </td>
              </tr>   
              
              <tr class="table">
                    <td>
                      <div align="left">Originated Unit</div>
                    </td>
                    <td>
                      <div align="left">
                      	 <input type="text"name="txtUnitName" id="txtUnitName" size=30 readonly="readonly"/>
                         <input type="hidden"name="txtUnitId" id="txtUnitId" size=5 readonly="readonly"/>
                         <input type="hidden"name="txtReason" id="txtReason" size=5 readonly="readonly"/>
                      </div>                  
                    </td>
              </tr>
              
             <tr class="table">
                    <td>
                      <div align="left">
                         Total Amount  <font color="#ff2121">*</font> 
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtTotalAmt" id="txtTotalAmt" size="16" readonly="readonly"></input>
                      </div>
                    </td>
             </tr> 
             
             <tr class="table">
                    <td>
                      <div align="left">Account Head Code</div>
                    </td>
                    <td>
                      <div align="left">
                         <input type="text" size=6 id="txtDebitHead" name="txtDebitHead" readonly="readonly"/>
                      </div>
                    </td>
              </tr>                              
              
              <tr class="table">
                    <td>
                      <div align="left">Particulars</div>
                    </td>
                    <td>
                      <div align="left">
                        <textarea name="txtRemarks" id="txtRemarks" cols="70"
                                  rows="3" ></textarea>
                      </div>
                    </td>
              </tr> 
              
            </table>
          </div>
        </div>        
        <div class="tab-page" id="gd" >
         <h2 class="tab" > Details</h2>
           
          <div align="center" >
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
            
              <tr>
                <td colspan="2">
                  
                  <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      
                          <tr class="tdH">
                            <th >Select</th> 
                            <th>Account Head</th>
                            <th >CR/DR</th>     
                            <th>Sub Ledger Type</th>
                            <th >Sub Ledger Code</th>                                                 
                            <th >Amount</th>                                                    
                            <th >Particulars </th>
                             <th >M Book No</th>                                                 
                            <th >M Book PageNo</th>                                                    
                            <th >M Book Date</th>
                          </tr>
                      
                       <tbody id="grid_body" class="table" align="left" >
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
                <input type="submit" name="butSub" id="butSub" value="SUBMIT"/>
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
    </form></body>
</html>