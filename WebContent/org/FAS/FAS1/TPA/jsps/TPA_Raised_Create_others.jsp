<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd"> 
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<html> 
  <head>
    <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>     
    <meta http-equiv="cache-control" content="no-cache">
    <title>Transfer Proforma Accounting System (Credit / Debit)-Others </title>
    
    <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    
  <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Popup_XMLreq_SL.js"></script> 
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Com_Function_SL_Case.js"></script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CommonControls/scripts/Sub_Ledger_Type_Applicable.js"></script> 
    
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js"></script>
       
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_Unit_ID.js"></script>
    <script type="text/javascript"
            src="<%=request.getContextPath()%>/org/FAS/FAS1/CommonControls/scripts/Common_Load_Accounting_office.js"></script>
    
    
    <script type="text/javascript" src="../scripts/Common_ReceiptType.js"></script>
    
    <script type="text/javascript" src="../scripts/TPA_Raised_Create_others.js"></script>
       <!-- <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> -->
    
   
      
            <script type="text/javascript" src="../scripts/CalenderControl_ParticularMnt.js"></script>
      
    <!-- to avoid future date the above script used-->
    <script type="text/javascript" language="javascript">
        
         function loadDate()
         {
             var today= new Date(); 
             var day=today.getDate();
             var month=today.getMonth();
             month=month+1;
             var mon;
             mon=month;
             if(day<=9 && day>=1)
             day="0"+day;
             if(month<=9 && month>=1)
                
             month="0"+month;
             var year=today.getYear();
             if(year < 1900) year += 1900;
             var monthArray =new Array("January", "February", "March", 
                       "April", "May", "June", "July", "August",
                       "September", "October", "November", "December");
            document.frm_TPA_Raised_Create_others.Voucher_Date.value=day+"/"+month+"/"+year;
            document.frm_TPA_Raised_Create_others.txtCB_Year.value=year;
            document.frm_TPA_Raised_Create_others.txtCB_Month.value=mon;
         }
         
</script>


 </head>
 <body onload="clr();loadDate();LoadAccountingUnitID('LIST_ALL_UNITS');setTimeout('loadTransferUnit()',500);" bgcolor="rgb(255,255,225)">
  <%
  
  Connection con=null;
  ResultSet rs=null,rs2=null,rsbank=null;
  PreparedStatement ps=null,ps2=null,psbank=null;
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
    <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Transfer Proforma Accounting System (Credit / Debit)Others </font>
          </div>
        </td>
      </tr>
    </table>
    
    
    <form name="frm_TPA_Raised_Create_others" id="frm_TPA_Raised_Create_others" method="POST" 
    action="../../../../../TPA_Raised_Create_others?Command=Add"
     onsubmit="return checkNull()">
   
      <div class="tab-pane" id="tab-pane-1">
        <!-- 1st Tab General Starts --> 
        <div class="tab-page">
          <h2 class="tab" >General </h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="0" width="100%">
           
                <tr class="tdTitle">
                <td colspan="2">
                  <div align="left">
                    <strong>General Details</strong>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Accounting Unit Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1" onchange="common_LoadOffice(this.value);loadTransferUnit();">
                  
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
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2">
                    </select>
                  </div>
                </td>
              </tr>
               <tr  class="table">
          <td >
         <div align="left">
             TPA For Year &amp; Month<font color="#ff2121">*</font>
             </div>
              </td>
              <td>
              <div align="left">
	          <input type="text" name="txtCB_Year" id="txtCB_Year"  maxlength="4" size="5" onkeypress="return numbersonly(event)" >
	          <select name="txtCB_Month"  id="txtCB_Month" >
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
                  <div align="left">
                     Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="Voucher_Date" id="Voucher_Date" tabindex="3" 
                           maxlength="10" size="11"  readonly="readonly"
                           onfocus="javascript:vDateType='3';" 
                           onkeypress="return calins(event,this);"
                           onblur="call_date(this);dateCheck(this);" />
                      <img src="../../../../../images/calendr3.gif" 
                      onclick="showCalendarControl(document.frm_TPA_Raised_Create_others.Voucher_Date,1);"
                         alt="Show Calendar" ></img>    
                         <!-- onclick="showCalendarControl(document.getElementById('Voucher_Date'));" -->
                             
                                    
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">
                    Transfer Proforma Advice Number <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left" >
                  (System Generated)
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">Transfer Proforma Type <font color="#ff2121">*</font> </div>
                </td>
                <td>
                  <div align="left">                  
                    <input id="Org_CR_DR" type="radio" value="CR"  name="Org_CR_DR" checked onclick="loadGL();"/> Credit     
                    <input id="Org_CR_DR" type="radio" value="DR" name="Org_CR_DR" onclick="loadGL();"/> Debit  
                    <input type="hidden" name="indicrdr" id="indicrdr">                               
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">Accounting Unit to which the accounts are transfered <font color="#ff2121">*</font> </div>
                </td>
                <td>
                  <div align="left">
                     <select name="TransferedID" id="TransferedID"  onchange="loadsltype()">
                        <option value="">select unit</option> 
                    </select>                   
                  </div>
                </td>
              </tr>
                
        
              <tr class="table">
                <td>
                  <div align="left">
                     Reason for Transfer <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                  <!--  <input type="text" id="Reason4Trf" size="6" value="Others" readonly name="Reason4Trf"/>-->
                  
                  <select name="Reason4Trf" id="Reason4Trf" >
                      <option value="Closure" >Closure </option>
                      <option value="Shift" >Shift </option>
                      <option value="Re-style" >Re-style </option>
                      <option value="Redeployment" >Redeployment </option>
                       <option value="Others" >Others</option>
                    </select>
                  
                  </div>
                </td>
              </tr>
              
               <tr class="table" >
               <td colspan="2">
               <div id="tohide" style="display:none"> 
               <table cellspacing="1" cellpadding="2" border="0" width="100%">
               <tr class="table">
                <td>
               
                  <div align="left">
                    Transfer Category <font color="#ff2121">*</font>
                  </div>
                </td>
                <td >
                  <div align="center">
                    <select name="transfercategory" id="transfercategory"  onchange="loadGlSlGrid()">
                     <option value="" >Select Category </option> 
                      <%ps=con.prepareStatement("select MINOR_HEAD_CODE,MINOR_HEAD_DESC from com_mst_minor_heads order by MINOR_HEAD_DESC");
                      rs=ps.executeQuery();
                      while(rs.next()){
                      %>
                      
                      
                      <option value="<%=rs.getInt("MINOR_HEAD_CODE") %>" ><%=rs.getString("MINOR_HEAD_DESC") %></option>
                     <%}
                      rs.close();
                      ps.close();
                      %>
                    </select>                   
                  </div>
                </td>
                </tr>
                </table>
                </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                     Amount 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="Amount" readonly="readonly" onkeypress="return limit_amt(this,event);" id="Amount" maxlength="17" size="18"/>
                    <textarea id="areaoly" readonly="readonly" style="color:red">Amount Will Be Displayed from SL Total</textarea>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">Particulars<font color="red">*</font></div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="GenParticulars" id="GenParticulars" cols="50"  
                              rows="4"></textarea>
                  </div>
                </td>
              </tr>
          
            </table>
          </div>
        </div>  <!-- 1st General Tab Ends --> 
         <!-- 2nd Detail Tab Starts --> 
         
         <div class="tab-page" id="gd2" >
          <h2 class="tab" >SL Details </h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
                 <tr class="table">
                <td>
                  <div align="left">
                    Account Head Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAcc_HeadCode" value="620101" disabled="disabled" onchange="checkTDA();"
                           id="txtAcc_HeadCode" maxlength="6"
                           onkeypress="return numbersonly(event)">
                           
                    <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountHeadList"
                             onclick="AccHeadpopup();"></img>
                    <input type="text" name="txtAcc_HeadDesc" value="TRANSFER PROFORMA CREDIT   -TRANSFER CREDIT" readonly="readonly" 
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
                    <input type="radio" name="rad_sub_CR_DR_sl" id="rad_sub_CR_DR_sl" checked="checked""
                          value="CR"/>Credit
                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="rad_sub_CR_DR_sl" id="rad_sub_CR_DR_sl" disabled="disabled" 
                           value="DR"/>Debit &nbsp;&nbsp;&nbsp;&nbsp; 
                    
                  </div>
                </td>
              </tr>
                       <tr class="table">
                <td width="40%">
                  <div align="left">Sub-Ledger Type  <font color="#ff2121">*</font> </div>
                </td>
                <td width="60%">
                  <div align="left">
                   <select size="1" name="cmbSL_type" id="cmbSL_type" onchange="doFunction('Load_SL_Code',this.value);">
                      <option value="">--Select Type--</option>
                     
                    </select>
                   <select size="1" name="cmbMas_SL_Code" id="cmbMas_SL_Code" tabindex="4" style="display:none" >
	                     </select>
	                     <select size="1" name="cmbMas_SL_type" id="cmbMas_SL_type" tabindex="4" style="display:none" >
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
                        
              <tr class="table">
                <td>
                  <div align="left">
                     SL Amount 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtsub_Amount_sl" onkeypress="return numbersonly(event)" onblur="fillAmount();"
                           id="txtsub_Amount_sl" maxlength="17" size="18"/>
                  </div>
                </td>
              </tr>
               
              <tr class="table">
                <td>
                  <div align="left">Particulars</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtParticular_sl" id="txtParticular_sl" cols="70" 
                              rows="3"></textarea>
                  </div>
                </td>
              </tr>
                      <tr class="tdTitle">
                        <td colspan="2" height="23">
                         <div align="center">
                            <table border="0">
                          <tr><td>
                          <input type="button" name="cmdadd_sl" id="cmdadd_sl"
                                 value="ADD" onclick="sl_add();" style="display:block"/></td>
                          <td>
                          <input type="button" name="cmdupdate_sl" value="UPDATE"
                                 id="cmdupdate_sl" onclick="sl_update()"
                                 style="display:none"/></td>
                          <td><input type="button" name="cmddelete_sl" value="DELETE"
                                 id="cmddelete_sl" onclick="delete_GRID()"
                                 disabled="disabled"/></td>
                          <td><input type="button" name="cmdclear_sl" value="CLEAR ALL"
                                 id="cmdclear_sl" onclick="clearall_sl()"/></td>
                        </tr>
                        </table>
                        </div>
                        <div id="grid_sl" style="display:block">
                    <table id="mytable_sl" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      
                          <tr class="tdH">
                            <th >Select</th> 
                            <th>Account Head</th>
                            <th >CR/DR</th>     
                            <th>Sub Ledger Type</th>
                            <th >Sub Ledger Code</th>                                                 
                            <th >SL Amount</th>                                                    
                            <th >Particulars </th>
                            
                          </tr>
                      
                       <tbody id="grid_body_sl" class="table" align="left" >
                       </tbody>
                    </table>
                  </div>
                        </td>
                      </tr>
                    </table>
          </div>
        </div>  
        
        <!-- 2nd Detail tab ends --> 
        
        
          <!-- 3rd Detail Tab Starts --> 
          
          <div class="tab-page" id="sl" >
          <h2 class="tab" > GL Details </h2>
           
          <table cellspacing="1" cellpadding="2" border="1" width="100%">
                 <tr class="table">
                <td>
                  <div align="left">
                    Account Head Code 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
               
                  <div align="left">
                    <input type="text" name="txtAcc_HeadCode_gl" onchange="loadAccDesc();"
                           id="txtAcc_HeadCode_gl" maxlength="6"
                           onkeypress="return numbersonly(event)">
                           
                    <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountHeadList"
                             onclick="AccHeadpopup();"></img>
                    <input type="text" name="txtAcc_HeadDesc_gl" readonly="readonly"
                           id="txtAcc_HeadDesc_gl" style="background-color: #ececec"  maxlength="125" size="70"/>
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
                    <input type="radio" name="rad_sub_CR_DR_gl" id="rad_sub_CR_DR_gl" disabled="disabled"
                            value="CR"/>Credit
                                                        &nbsp;&nbsp;&nbsp;&nbsp; 
                    <input type="radio" name="rad_sub_CR_DR_gl" id="rad_sub_CR_DR_gl" checked="checked"
                           value="DR"/>Debit &nbsp;&nbsp;&nbsp;&nbsp; 
                    
                  </div>
                </td>
              </tr>
               
                        
              <tr class="table">
                <td>
                  <div align="left">
                    GL Amount 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtsub_Amount_gl" onkeypress="return numbersonly(event)"
                           id="txtsub_Amount_gl" maxlength="17" size="18"/>
                  </div>
                </td>
              </tr>
               
             <!--   <tr class="table">
                <td>
                  <div align="left">Particulars</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtParticular_gl" id="txtParticular_gl" cols="70"  
                              rows="3"></textarea>
                  </div>
                </td>
              </tr>-->
                      <tr class="tdTitle">
                        <td colspan="2" height="23">
                         <div align="center">
                            <table border="0">
                          <tr><td>
                          <input type="button" name="cmdadd_gl" id="cmdadd_gl"
                                 value="ADD" onclick="gl_add()" style="display:block"/></td>
                          <td>
                          <input type="button" name="cmdupdate_gl" value="UPDATE"
                                 id="cmdupdate_gl" onclick="gl_update()"
                                 style="display:none"/></td>
                          <td><input type="button" name="cmddelete_gl" value="DELETE"
                                 id="cmddelete_gl" onclick="delete_GRID_gl()"
                                 disabled="disabled"/></td>
                          <td><input type="button" name="cmdclear_gl" value="CLEAR ALL"
                                 id="cmdclear_gl" onclick="clearall_gl()"/></td>
                        </tr>
                        </table>
                        </div>
                        <div id="grid_gl" style="display:block">
                    <table id="mytable_gl" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      
                          <tr class="tdH">
                            <th >Select</th> 
                            <th>Account Head</th>
                            <th >CR/DR</th>     
                            <th >Amount</th>                                                    
                         <!--    <th >Particulars </th> -->
                            
                          </tr>
                      
                       <tbody id="grid_body_gl" class="table" align="left" >
                       </tbody>
                    </table>
                  </div>
                        </td>
                      </tr>
                    </table>
        </div>
          
        
          <!-- 3th Detail tab ends --> 
        
      </div>  <!-- Main Tag -->
      
      
     
      
      <br>
      
    
      <div align="center">
        <table cellspacing="1" cellpadding="6" width="100%" >
          <tr class="tdH">
            <td >
              <div align="center">
             
                <input type="submit" name="butSub" id="butSub" value="SUBMIT"/>
                 &nbsp;&nbsp;&nbsp; 
               <input type="button" name="butCan" id="butCan" value="CANCEL"
                       onclick="clear_all();"/>
                 &nbsp;&nbsp;&nbsp; 
                 
                <input type="button" name="butCan" id="butCan" value="EXIT"
                       onclick="javascript:self.close();"/>
                     &nbsp;&nbsp;&nbsp;   
                 
                       
              </div>
            </td>
           
          </tr>
        </table>
      </div>
      
      
      
    </form></body>
</html>