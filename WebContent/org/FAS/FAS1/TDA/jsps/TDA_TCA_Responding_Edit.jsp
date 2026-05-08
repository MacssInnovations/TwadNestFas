<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<html>
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>TDA/TCA Responding System</title>
    
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
    <script type="text/javascript" src="../scripts/TDA_TCA_Responding_Edit.js"></script>
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
                 document.TDA_TCA.txtCrea_date.value=day+"/"+month+"/"+year;  
               //  document.TDA_TCA.txtCB_Year.value=year;                
        }
       
</script>
  </head>
   <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body onload="loadDate();LoadAccountingUnitID('LIST_ALL_UNITS');clrForm('load');" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Transfer of Debit/Credit Advice (TDA/TCA) Responding System</font>
          </div>
        </td>
      </tr>
    </table>
   
  <form name="TDA_TCA" id="TDA_TCA" method="POST"
                  action="../../../../../TDA_TCA_Responding_Edit?Command=Edit" onsubmit="return checkNull()">
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
                        Suspense Head Clearance Date  <font color="#ff2121">*</font>
                      </div>
                    </td>
                    <td>
                      <div align="left">
                        <input type="text" name="txtCrea_date" id="txtCrea_date" 
                               maxlength="10" size="11"
                               onfocus="javascript:vDateType='3';"
                               onkeypress="return calins(event,this);"
                               onblur="return call_date(this);"/>
                         
                        <img src="../../../../../images/calendr3.gif"
                             onclick="showCalendarControl(document.TDA_TCA.txtCrea_date,1);"
                             alt="Show Calendar"></img>                        
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
                        <input type="radio" name=Journal_type id=Journal_type value="TDAR" onclick="doFunction_TDA('load_Voucher_No')"></input>TDA Suspense Head Clearance
                        <input type="radio" name=Journal_type id=Journal_type value="TCAR" onclick="doFunction_TDA('load_Voucher_No')"></input>TCA Suspense Head Clearance
                        
                      </div>
                    </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">
                    Voucher Number <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select type=text name="responded_slno" id="responded_slno" onchange="doFunction_TDA('load_Voucher_Details');changeLink()">  
                    	<option value="">select voucher</option>   
                    </select>  <a id="linkId" href="javascript:checkVoucherNo();" style="visibility:hidden">Voucher Details</a>             	
                  </div>
                </td>
              </tr>      
              
	          <tr class="table">
                <td>
                  <div align="left">
                    Originated Advise No. <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type=text name="originated_slno" id="originated_slno" >
                    	
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                   <td>
                     <div align="left">
                       Originated Advice Date                       
                     </div>
                   </td>
                   <td>
                     <div align="left">
                       <input type="text" name="originated_sldate" id="originated_sldate" 
                              maxlength="10" readonly="readonly"/>                   
                     </div> 
                   </td>
              </tr>   
              
              <tr class="table">
                <td>
                  <div align="left">
                    Originated Journal No.
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type=text name="originated_jvr_no" id="originated_jvr_no" size=5 readonly="readonly"><a id="linkId1" href="javascript:ShowDetails('originated_jvr');" style="visibility:hidden">Voucher Details</a>
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                   <td>
                     <div align="left">
                       Originated Journal Date 
                     </div>
                   </td>
                   <td>
                     <div align="left">
                       <input type="text" name="originated_jvr_date" id="originated_jvr_date" size=10 readonly="readonly"/>                   
                     </div> 
                   </td>
              </tr>  
                  
              
              <tr class="table">
                   <td>
                     <div align="left">
                       Accepted Advice No. 
                     </div>
                   </td>
                   <td>
                     <div align="left">
                       <input type="text" name="accepted_slno" id="accepted_slno" size=5 readonly="readonly"/><a id="linkId2" href="javascript:ShowDetails('accepted_slno');" style="visibility:hidden">Voucher Details</a>                   
                     </div> 
                   </td>
              </tr>  
              
               <tr class="table">
                   <td>
                     <div align="left">
                       Accepted Advice Date 
                     </div>
                   </td>
                   <td>
                     <div align="left">
                       <input type="text" name="accepted_sldate" id="accepted_sldate" size=10 readonly="readonly"/>                   
                     </div> 
                   </td>
              </tr>  
              
               <tr class="table">
                <td>
                  <div align="left">
                    Accepted Journal No.
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type=text name="accepted_jvr_no" id="accepted_jvr_no" size=5 readonly="readonly"><a id="linkId3" href="javascript:ShowDetails('accepted_jvr');" style="visibility:hidden">Voucher Details</a>
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                   <td>
                     <div align="left">
                       Accepted Journal Date 
                     </div>
                   </td>
                   <td>
                     <div align="left">
                       <input type="text" name="accepted_jvr_date" id="accepted_jvr_date" size=10 readonly="readonly"/>                   
                     </div> 
                   </td>
              </tr>  
              
              <tr class="table">
                   <td>
                     <div align="left">
                       Accepted Unit
                     </div>
                   </td>
                   <td>
                     <div align="left">
                       <input type="text" name="accepted_unit_id" id="accepted_unit_id" size=30 readonly="readonly"/>                   
                     </div> 
                   </td>
              </tr>  
              
              <tr class="table">
                   <td>
                     <div align="left">
                       Total Amount
                     </div>
                   </td>
                   <td>
                     <div align="left">
                       <input type="text" name="txtTotalAmt" id="txtTotalAmt" size=6 readonly="readonly"/>                   
                     </div> 
                   </td>
              </tr>  
              
               <tr class="table">
                   <td>
                     <div align="left">
                       Credit Account Head Code
                     </div>
                   </td>
                   <td>
                     <div align="left">
                       <input type="text" name="cr_accHead_code" id="cr_accHead_code" size=6 readonly="readonly"/>                   
                     </div> 
                   </td>
              </tr>  
                            
              <tr class="table">
                   <td>
                     <div align="left">
                       Debit Account Head Code
                     </div>
                   </td>
                   <td>
                     <div align="left">
                       <input type="text" name="dr_accHead_code" id="dr_accHead_code" size=6 readonly="readonly"/>                   
                     </div> 
                   </td>
              </tr>  
                           
             
              
              <tr class="table">
                    <td>
                      <div align="left">Particulars</div>
                    </td>
                    <td>
                      <div align="left">
                        <textarea name="txtRemarks" id="txtRemarks" cols="70" onkeypress="return check_leng(this.value);"
                                  rows="3" ></textarea>
                      </div>
                    </td>
              </tr> 
              
            </table>
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