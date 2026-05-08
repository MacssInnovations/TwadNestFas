<!DOCTYPE HTML PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN"
"http://www.w3.org/TR/html4/loose.dtd">
<%@ page session="false"  contentType="text/html;charset=windows-1252"%>
<%@ page import="java.sql.*,java.util.*,Servlets.Security.classes.UserProfile"%>
<%@ include file="//org/Security/jsps/Check_SessionJSPF.jspf" %>
<%@ include file="//org/Security/jsps/IntialLoad.jsp"%>

 <html>
  <head>
     <meta http-equiv="Content-Type" content="text/html; charset=windows-1252"/>    
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" no-store, no-cache, must-revalidate" >
   <META HTTP-EQUIV="CACHE-CONTROL" CONTENT=" pre-check=0, post-check=0, max-age=0" >
    <title>Self-Cheque System</title>
     <link href="../../../../../css/Sample3.css" rel="stylesheet"  media="screen"/>
    <link href="../../../../../css/CalendarControl.css" rel="stylesheet" media="screen"/>
    <link href='../../../../../css/Fas_Account.css' rel='stylesheet' media='screen'/>
    
    <script type="text/javascript" src="<%=request.getContextPath()%>/org/Library/scripts/comJS.js"></script>
    <script type="text/javascript"  src="../../../../../org/Library/scripts/checkDate.js"></script>
    <script type="text/javascript" src="../scripts/selfCheque_popup.js"></script> 
    <script type="text/javascript" src="../scripts/selfCheque_func.js"></script> 
    
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/ReceiptSystem/scripts/Common_ReceiptType.js"></script>
    <script type="text/javascript" src="../scripts/SelfCheque_Edit.js"></script>
    <script type="text/javascript" src="../scripts/SelfCheque_Edit_load_Vou.js"></script> 
    <script type="text/javascript"   src="../../../../Security/scripts/tabpane.js">          </script>
    <script type="text/javascript" src="../../../../../org/FAS/FAS1/CalendarCtrl.js"></script> 
   
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
            document.frmSelfCheque_Edit.txtCrea_date.value=day+"/"+month+"/"+year;
            doFunction_voucher('load_Voucher_No','null');
      }
    
</script>
  </head>
   <%
response.setHeader("Cache-Control","no-cache"); //HTTP 1.1
response.setHeader("Pragma","no-cache"); //HTTP 1.0
response.setDateHeader ("Expires", 0); //prevent caching at the proxy server
%>
  <body onload="call_clr();loadDate();foc()" bgcolor="rgb(255,255,225)">
  <table cellspacing="1" cellpadding="3" width="100%" >
      <tr class="tdH">
        <td colspan="2">
          <div align="center">
            <font size="4">Self Cheque System (Edit)</font>
          </div>
        </td>
      </tr>
    </table>
    
    <form name="frmSelfCheque_Edit" id="frmSelfCheque_Edit" method="POST" onsubmit="return checkNull()" 
             action="../../../../../SelfCheque_Edit.view?Command=Add"  >
     
      <div class="tab-pane" id="tab-pane-1">
        <div class="tab-page">
          <h2 class="tab" >Cheque Drawl </h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr class="tdTitle">
                <td colspan="2">
                  <div align="left">
                    <strong>Office Details</strong>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">Office&nbsp;Name</div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAcc_unitName"
                           id="txtAcc_unitName" value="<%=oname%>"
                           maxlength="60" size="60" readonly="readonly"
                           class="disab"/>
                           <input type="hidden" id="login_office" name="login_office" value="<%=oid%>"></input>
                  </div>
                </td>
              </tr>
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
                    <!--<input type="text" name="txtAcc_UnitCode"
                           id="txtAcc_UnitCode" maxlength="4" size="5"/>-->
                    <select size="1" name="cmbAcc_UnitCode" id="cmbAcc_UnitCode" tabindex="1">
                     <!-- <option value="0"> Select Account Unit </option>-->
                          <%
                          String mod_id="MF008",CR_DR="CR";
                      int unitid=0;
                      String unitname="";
                      try{
                        if(oid==5000)
                        {
                             //out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
                            //ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=?");
                            String getWing="select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME,OFFICE_WING_SINO from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_OFFICE_ID=? and OFFICE_WING_SINO=(select OFFICE_WING_SINO from hrm_emp_current_wing where employee_id=? and office_id=?)";
                            ps=con.prepareStatement(getWing);
                            ps.setInt(1,oid);
                            ps.setInt(2,empid);
                            ps.setInt(3,oid);
                            rs=ps.executeQuery();
                          
                              if(rs.next())
                              {
                              out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+">"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                              unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                              
                              System.out.println(".."+rs.getInt("ACCOUNTING_UNIT_ID"));
                              System.out.println(".."+rs.getString("ACCOUNTING_UNIT_NAME"));
                              System.out.println(".."+rs.getInt("OFFICE_WING_SINO"));
                              
                              }
                          System.out.println(oid+" "+oname);
                          ps.close();
                          rs.close();
                          }
                              else
                              {
                                ps=con.prepareStatement("select ACCOUNTING_UNIT_ID,ACCOUNTING_UNIT_NAME from FAS_MST_ACCT_UNITS where ACCOUNTING_UNIT_ID=(select ACCOUNTING_UNIT_ID from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_FOR_OFFICE_ID=?)");
                                ps.setInt(1,oid);
                                rs=ps.executeQuery();
                                  if(rs.next())
                                  {
                                  System.out.println(rs.getInt("ACCOUNTING_UNIT_ID"));
                                  System.out.println(rs.getString("ACCOUNTING_UNIT_NAME"));
                                  //out.println("<option value="+0+">"+"-- Select Account Unit --"+"</option>");
                                  out.println("<option value="+rs.getInt("ACCOUNTING_UNIT_ID")+" >"+rs.getString("ACCOUNTING_UNIT_NAME")+"</option>");
                                  unitid=rs.getInt("ACCOUNTING_UNIT_ID");
                                  }
                                  ps.close();
                                  rs.close();
                              }
                          }
                      catch(Exception e)
                        {
                            System.out.println(e);
                        }
                      %>
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
                    <select size="1" name="cmbOffice_code" id="cmbOffice_code" tabindex="2" >
                      
                      <%
                   System.out.println("here");
                   System.out.println(oid+"  " +oname);
                try
                {
                   if(oid==5000)
                    {
                        out.println("<option value="+oid+">"+"HEAD OFFICE"+"</option>");
                    }
                    else
                    {
                        ps=con.prepareStatement("select ACCOUNTING_UNIT_ID, ACCOUNTING_FOR_OFFICE_ID  from FAS_MST_ACCT_UNIT_OFFICES where ACCOUNTING_UNIT_ID=? order by ACCOUNTING_FOR_OFFICE_ID desc");
                        ps.setInt(1,unitid);
                        rs=ps.executeQuery();
                        //out.println("<option value="+oid+">"+oname+"</option>");
                        int countoffice=0;      // used to load the bank details for the first office of combo box
                        while(rs.next())
                        {
                            ps2=con.prepareStatement("select OFFICE_NAME from COM_MST_OFFICES where OFFICE_ID=? and OFFICE_STATUS_ID not in ('CL','RD')");
                            ps2.setInt(1,rs.getInt("ACCOUNTING_FOR_OFFICE_ID"));
                            rs2=ps2.executeQuery();
                            if(rs2.next())
                            {
                              out.println("<option value="+rs.getInt("ACCOUNTING_FOR_OFFICE_ID")+">"+rs2.getString("OFFICE_NAME")+"</option>");                           
                            }
                        }
                    }
                    
                } 
                catch(Exception e)
                {
                System.out.println("Exception in Office combo..."+e);
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
                <td>
                  <div align="left">
                    Date
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCrea_date" id="txtCrea_date" tabindex="3" 
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="call_date(this);"/>
                     <img src="../../../../../images/calendr3.gif"
                         onclick="showCalendarControl(document.frmSelfCheque_Edit.txtCrea_date,1);"
                         alt="Show Calendar"></img> 
                  </div>
                </td>
              </tr>
           
              
              <tr class="table" >
                <td>
                  <div align="left">
                    Voucher  Number  <!--It' going to be Receipt voucher number , but here it's needed to show the user -->
                                         
                  </div>
                </td>
                           <td>
                  <div align="left">
                    <select size="1" name="txtReceipt_No" id="txtReceipt_No"
                            tabindex="5"
                            onchange="doFunction_voucher('load_Voucher_Details','null');">    
                      <option value="">--Select Voucher Number--</option>
                     
                      
                    </select>
                  </div>
                </td>

              </tr>
              <%
                    
                /*    String sql_bank="select curr.BANK_ID,curr.BRANCH_ID,curr.BANK_AC_NO,curr.AC_HEAD_CODE,bk.BANK_NAME || '-' || br.BRANCH_NAME ||'-' || br.CITY_TOWN_NAME as bk_br_city "+
                    " from FAS_OFFICE_BANK_AC_CURRENT curr,FAS_MST_BANK_BRANCHES br ,FAS_MST_BANKS bk where curr.ACCOUNTING_UNIT_ID=? and curr.MODULE_ID=? and curr.CR_DR_TYPE=? "+
                    " and curr.SL_NO=1 and curr.BANK_ID=br.BANK_ID and curr.BRANCH_ID=br.BRANCH_ID and br.BANK_ID=bk.BANK_ID";
                    // here SL_NO=1 means that DEFAULT account number for that unit ..
                     System.out.println(sql_bank);
                     psbank=con.prepareStatement(sql_bank);
                     
                     psbank.setInt(1,unitid);
                     psbank.setString(2,mod_id);
                     psbank.setString(3,CR_DR);
                     rsbank=psbank.executeQuery();
                     
                    if(rsbank.next())
                    {
                    System.out.println("inside if");
                     bankID=rsbank.getInt("BANK_ID");
                     branchID=rsbank.getInt("BRANCH_ID");
                     bankAccNo=rsbank.getLong("BANK_AC_NO");
                     AC_HEAD_CODE=rsbank.getInt("AC_HEAD_CODE");
                     bk_br_city=rsbank.getString("bk_br_city");
                        System.out.println("bank details..."+bankID+" "+branchID+ " "+ bankAccNo+" "+bk_br_city+" "+AC_HEAD_CODE);
                    }
                    psbank.close();
                    rsbank.close();
                  */  
              %>
              <tr class="table">
                <td>
                  <div align="left">Operation A/c Code</div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCash_Acc_code" id="txtCash_Acc_code"  tabindex="5"  
                          style="background-color: #ececec"  readonly="readonly" maxlength="8" size="9"/>
                    <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountNumberList"
                             onclick="MainAccNopopup();"></img>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                   Bank Account Number
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtBankAccountNo"  onkeypress="return numbersonly(event)"    
                           id="txtBankAccountNo" maxlength="15"  size="15"  style="background-color: #ececec" readonly="readonly" />
                  
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
                    <input type="text" name="txtBankName" id="txtBankName" readonly="readonly"   
                    style="background-color: #ececec"  size="50" maxlength="50"/>
                   <input type="hidden" name="txtBankId" readonly="readonly"   
                           id="txtBankId" size="5" maxlength="5"/>
                   <input type="hidden" name="txtBranchId" readonly="readonly"  
                           id="txtBranchId" size="5" maxlength="5"/>
                  </div>
                </td>
              </tr>
              
              <tr class="table">
                <td>
                  <div align="left">
                    CR/DR Indicator
                   
                  </div>
                </td>
                <td>
                  <div align="left">
                  <input type="hidden" name="txtCR_DB"
                           id="txtCR_DB" value="CR" size="2"/>
                    <input type="text" name="txtCR_DB_desc"
                          style="background-color: #ececec"  readonly="readonly" id="txtCR_DB_desc" value="CREDIT" size="6"/>
                  </div>
                </td>
              </tr>
          
               <tr class="table">
                <td>
                  <div align="left">
                    Cheque Number & Date
                    <font color="#ff2121"> * </font>
                   </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtCheque_DD_NO" maxlength="10" onkeypress="return numbersonly(event)" onblur="check_dd_cheque()"
                           id="txtCheque_DD_NO" size="11"/>
                    <input type="text" name="txtCheque_DD_date" id="txtCheque_DD_date"  
                           maxlength="10" size="11"  
                           onfocus="javascript:vDateType='3';"
                           onkeypress="return calins(event,this);"
                           onblur="return checkdt(this);"/>
                  
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Total Amount (Rs. P.) 
                    <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAmount"  onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);" tabindex="6" 
                           id="txtAmount" maxlength="17" size="18"/>
                  </div>
                </td>
              </tr>
             <tr class="table">
                <td>
                  <div align="left">Purpose of drawl</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtRemarks" id="txtRemarks" cols="60"  onkeypress="return check_leng(this.value);" tabindex="9" 
                              rows="3"></textarea>
                  </div>
                </td>
              </tr>
            </table>
          </div>
        </div>
         
         <div class="tab-page" id="gd" >
          <h2 class="tab" > Voucher Details</h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr>
                <td colspan="2">
                  <div id="sub_ledge_dis" >
                  <table cellspacing="1" cellpadding="2" border="1"   width="100%">
             <tr class="tdTitle">
                <td colspan="2">
                  <div align="left">
                    <strong> General voucher part</strong>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Acq. Roll applicable
                  </div>
                </td>
                <td>
                  <div align="left">
                  <input type="radio" name="radAcq_roll_YN" id="radAcq_roll_YN" value="Y" checked="checked" onclick="enable_acq(this.value);" disabled="disabled" />YES
                  <input type="radio" name="radAcq_roll_YN" id="radAcq_roll_YN" value="N" onclick="enable_acq(this.value);" disabled="disabled"/>NO
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                    Acq.Roll. No
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtPayWise_AcqSL_No" id="txtPayWise_AcqSL_No" onkeypress="return numbersonly(event)" 
                     size="6" maxlength="5"   />
                    
                  </div>
                </td>
              </tr>
             <tr class="table">
                <td>
                  <div align="left">
                    Vr. No
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtPayWise_Voucher_No" id="txtPayWise_Voucher_No"   
                     size="6" maxlength="5"  readonly="readonly"
                           class="disab" />
                    (System Generated)
                    
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
                    <input type="text" name="txtPayWise_Amount" onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);"
                           id="txtPayWise_Amount" maxlength="17" size="18"/>
                    Cash A/c Head &nbsp;&nbsp;<input type="text" id="txtCashAccHead" name="txtCashAccHead" value="820101"  readonly="readonly"
                           class="disab"/>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">Remarks</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtPayWiseRemarks" id="txtPayWiseRemarks" cols="70"  onkeypress="return check_leng(this.value);"
                              rows="3"></textarea>
                  </div>
                </td>
       
                <tr class="tdTitle">
                        <td colspan="2" height="23">
                         <div align="center">
                            <table border="0">
                          <tr>
                          <td><input type="button" name="cmdadd_PAYWISE" id="cmdadd_PAYWISE"
                                 value="ADD" onclick="ADD_PAYWISE_GRID()" style="display:none"/></td>
                          <td>
                          <input type="button" name="cmdupdate_PAYWISE" value="UPDATE"
                                 id="cmdupdate_PAYWISE" onclick="update_PAYWISE_GRID()"
                                 style="display:none"/></td>
                          <td><input type="button" name="cmddelete_PAYWISE" value="DELETE"
                                 id="cmddelete_PAYWISE" onclick="delete_PAYWISE_GRID()" style="display:none"
                                 disabled="disabled"/></td>
                          <td><input type="button" name="cmdclear_PAYWISE" value="CLEAR ALL"
                                 id="cmdclear_PAYWISE" onclick="clearall_PAYWISE()"/></td>
                        </tr>
                        </table>
                        </div>
                        </td>
                      </tr>
                    </table>
                  </div>
                  <div id="grid" style="display:block">
                    <table id="mytable" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      <tr class="table">
                       <th > Select </th>
                        <th >Acq.  No</th>
                        <th >Vr. No</th>
                        <th >Amount</th>
                        <th >Remarks</th>
                      </tr>
                       <tbody id="grid_body_PAYWISE" class="table" align="left" >
                       </tbody>
                    </table>
                  </div>
                </td>
              </tr>
            </table>
          </div>
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr>
                <td colspan="2">
                  <div id="sub_ledge_dis" >
                  <table cellspacing="1" cellpadding="2" border="1"                           width="100%">
             <tr class="tdTitle">
                <td colspan="2">
                  <div align="left">
                    <strong> Details voucher part</strong>
                  </div>
                </td>
              </tr>
             <tr class="table">
                <td>
                  <div align="left">
                    Voucher  No
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtpayDetails_Voucher_No" id="txtpayDetails_Voucher_No"  size="6" maxlength="5"  onkeypress="return numbersonly(event)"/>
                  </div>
                </td>
              </tr>
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
                           id="txtAcc_HeadCode" maxlength="8"
                           onkeypress="return numbersonly(event)"
                            onchange="sixdigit();HeadCodeValidation();" 
                            onblur="doFunction('checkCode','null');loaddrawlPurpose(this.value);HeadCodeValidation();"  size="9"/>
                    <img src="../../../../../images/c-lovi.gif" width="20" 
                             height="20" alt="AccountHeadList"
                             onclick="AccHeadpopup();"></img>
                    <input type="text" name="txtAcc_HeadDesc" readonly="readonly" 
                           id="txtAcc_HeadDesc" style="background-color: #ececec"  maxlength="125" size="70"/>
                  </div>
                </td>
              </tr>
             <tr class="table">
                <td width="40%">
                  <div align="left">Sub-Ledger Type </div>
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
                  <div align="left">Sub-Ledger Code</div>
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
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" onclick="emp_servicepopup();"></img>
                            <input type="text" name="txtEmpID_trs" id="txtEmpID_trs" maxlength="5" size="5"  onblur="load_employee(this.value);" />
                 </div>
               </td>
             
            </tr>
             </table>
        </td>
              </tr>
             <tr class="table">
                <td>
                  <div align="left">Payment Type</div>
                </td>
                <td>
                   <div id="newId" align="left" style="display:block">
                     <input type="radio" id="payment_type" name="payment_type" checked="checked" onclick="checkRecoup()"> New</input>
                   </div>
			      <div id="recoupId" align="left" style="display:block">
                     <input type="radio" id="payment_type" name="payment_type" tabindex="24" onclick="checkRecoup()"> Recoup</input>			                     
                  </div>
                  
                </td>
             </tr>
          	 <tr class="table">
			                <td>
			                  <div align="left"> Purpose of drawl</div>
			                </td>
			                <td>
			                  <div align="left">
			                     <select id="drawl_purpose" name="drawl_purpose" tabindex="25">
			                     	<option value="">-Select--</option>			                     	
			                     </select>
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
                    <input type="text" name="txtPayWiseDetails_Amount" onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);"
                           id="txtPayWiseDetails_Amount" maxlength="17" size="18"/>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">Particulars</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtPayWise_DetailsParticular" id="txtPayWise_DetailsParticular" cols="70"  onkeypress="return chk_part_len(this.value);"
                              rows="3"></textarea>
                  </div>
                </td>
            </tr>
            <tr>
          	<td colspan="2">
         		     <div id="recoupDiv" style="display:none">
                			<table id="recoupTable" cellspacing="3" cellpadding="2"  border="1" width="100%">
               				  <tr>
		           		    <td colspan="5" class="tdH">
		           			 		Recoup Details 
		           		    </td>
		              </tr>	                  	                      
                      <tr class="table">
	                        <th >Select</th>                                               
	                        <th>Voucher No.</th>
	                        <th>Sl.No.</th>
	                        <th>Voucher Date</th>   					                        	                      
	                        <th>Amount</th>
                      </tr>
                      <tbody id="recoup_body" class="table" align="left" >
                      </tbody>					                    
                  		</table>
                 </div>
                </td>
            </tr>        
            <tr class="tdTitle">
                        <td colspan="2" height="23">
                         <div align="center">
                            <table border="0">
                          <tr><td>
                          <input type="button" name="cmdadd_PAYWISE_DETAILS" id="cmdadd_PAYWISE_DETAILS"
                                 value="ADD" onclick="ADD_PAYWISE_DETAILS_GRID()" style="display:block"/></td>
                          <td>
                          <input type="button" name="cmdupdate_PAYWISE_DETAILS" value="UPDATE"
                                 id="cmdupdate_PAYWISE_DETAILS" onclick="update_PAYWISE_DETAILS_GRID()"
                                 style="display:none"/></td>
                          <td><input type="button" name="cmddelete_PAYWISE_DETAILS" value="DELETE"
                                 id="cmddelete_PAYWISE_DETAILS" onclick="delete_PAYWISE_DETAILS_GRID()"
                                 disabled="disabled"/></td>
                          <td><input type="button" name="cmdclear_PAYWISE_DETAILS" value="CLEAR ALL"
                                 id="cmdclear_PAYWISE_DETAILS" onclick="clearall_PAYWISE_DETAILS()"/></td>
                        </tr>
                        </table>
                        </div>
                        </td>
                      </tr>
                    </table>
                  </div>
                  <div id="grid" style="display:block">
                    <table id="mytable_details" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      <tr class="table">
                        <th> Select </th>
                        <th >Vr.  No</th>
                        <th > A/c Head</th>
                        <th >SL type</th>
                        <th >SL Code</th>
                        <th >Amount</th>
                        <th >Particulars</th>
                        <th >Payment Type </th>
                        <th >Purpose of Drawl</th>
                        <th >Recouped Voucher No. </th>
                        <th >Recouped Voucher Date </th>
                       
                      </tr>
                       <tbody id="grid_body_PAYWISE_DETAILS" class="table" align="left" >
                       </tbody>
                    </table>
                  </div>
                </td>
              </tr>
            </table>
          </div>
          
        </div>
        <div class="tab-page" id="gd" >
          <h2 class="tab" > Acquittance Details</h2>
           
          <div align="center">
            <table cellspacing="1" cellpadding="2" border="1" width="100%">
              <tr>
                <td colspan="2">
                  <div id="sub_ledge_dis" >
                    <table cellspacing="1" cellpadding="2" border="1"
                           width="100%">
             <tr class="table">
                <td>
                  <div align="left">
                    Entry for Specific Employee or All
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="radio" name="radAllSpec" id="radAllSpec"  value="A"  onclick="makeReadonly(this.value)" /> ALL
                    <input type="radio" name="radAllSpec" id="radAllSpec"   value="S" checked="checked"  onclick="makeReadonly(this.value)"/> Specific
                  </div>
                </td>
              </tr>
               <tr class="table">
                <td>
                  <div align="left">
                    Acquittance Roll No.
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAcq_roll_No" id="txtAcq_roll_No"   size="6" maxlength="1"/>
                  </div>
                </td>
              </tr>
             <tr class="table">
                <td>
                  <div align="left">
                    Voucher  No.
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtAcq_Voucher_No" id="txtAcq_Voucher_No" onchange="acq_valid(this.value)" size="6" maxlength="5" />
                  </div>
                </td>
              </tr>
                  
                <tr class="table">
                <td>
                  <div align="left">
                            Office Code
                             <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtoffid" onblur="office_emp('loadoffice',this.value);"
                           id="txtoffid" maxlength="4"
                           onkeypress="return numbersonly(event)" 
                           size="6"/>
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="OfficeList" 
                            onclick="acq_office_popup();"></img>
                    <input type="text" id="txtOfficeName" name="txtOfficeName" size="75" readonly="readonly" style="background-color: #ececec" />
                  </div>
                  
                           
                  
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">
                            Employee Code
                             <font color="#ff2121">*</font>
                  </div>
                </td>
                <td>
                  <div align="left">
                    <input type="text" name="txtEmpID" onblur="office_emp('loadEmployee',this.value);"
                           id="txtEmpID" maxlength="5"
                           onkeypress="return numbersonly(event)" 
                           size="6"/>
                            <img src="../../../../../images/c-lovi.gif" width="20" height="20" alt="empList" 
                            onclick="acq_emp_popup();"></img>
                    <input type="text" id="txtempName" name="txtempName" size="75" readonly="readonly" style="background-color: #ececec" />
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
                    <input type="text" name="txtsub_Amount" onkeypress="return limit_amt(this,event);" onblur="valid_amt(this);"
                           id="txtsub_Amount" maxlength="17" size="18"/>
                  </div>
                </td>
              </tr>
              <tr class="table">
                <td>
                  <div align="left">Particulars</div>
                </td>
                <td>
                  <div align="left">
                    <textarea name="txtAuq_Particular" id="txtAuq_Particular" cols="70"  onkeypress="return chk_part_len(this.value);" value="2"
                              rows="3"></textarea>
                  </div>
                </td>
              </tr>
              <tr class="tdTitle">
                <td colspan="2" height="23">
                 <div align="center">
                    <table border="0">
                  <tr><td>
                  <input type="button" name="cmdadd_Acq" id="cmdadd_Acq"
                         value="ADD" onclick="ADD_GRID_Acq()" style="display:block"/></td>
                  <td>
                  <input type="button" name="cmdupdate_Acq" value="UPDATE"
                         id="cmdupdate_Acq" onclick="update_GRID_Acq()"
                         style="display:none"/></td>
                  <td><input type="button" name="cmddelete_Acq" value="DELETE"
                         id="cmddelete_Acq" onclick="delete_GRID_Acq()"
                         disabled="disabled"/></td>
                  <td><input type="button" name="cmdclear_Acq" value="CLEAR ALL"
                         id="cmdclear_Acq" onclick="clearall_Acq()"/></td>
                </tr>
                </table>
                </div>
                </td>
              </tr>
         </table>
                  </div>
                  <div id="grid" style="display:block">
                    <table id="mytable_Acq" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      <tr class="table">
                        <th >Select</th>
                        <th >Acq.roll No.</th>
                        <th >Vr. No</th>
                        <th >Office Code</th>
                        <th >Employee Code</th>
                        <th >Amount</th>
                        <th >Particulars </th>
                      </tr>
                       <tbody id="grid_body_Acq" class="table" align="left" >
                       </tbody>
                    </table>
                  </div>
                </td>
              </tr>
            </table>
          </div>
        </div>
         <!--<div class="tab-page" id="gd" >
          <h2 class="tab" > Acquittance Abstract Details</h2>
           <div id="grid" style="display:block">
                    <table id="mytable_Acq_abstract" cellspacing="3" cellpadding="2"
                           border="1" width="100%">
                      <tr class="table">
                        <th >Vr.SL. NO</th>
                        <th >Acquittance No.</th>
                        <th >Office Code</th>
                        <th >Amount</th>
                      </tr>
                       <tbody id="grid_body_Acq_abstract" class="table" align="left" >
                       </tbody>
                    </table>
                  </div>
         </div>  -->
      </div>
      <br>
      <div align="center">
        <table cellspacing="1" cellpadding="3" width="100%">
          <tr class="tdH">
            <td>
              <div align="center">
                <input type="SUBMIT" name="butSub" id="butSub" value="SUBMIT"/>
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
    </form></body>
</html>